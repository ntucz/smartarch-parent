package com.smartarch.platform.log.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import com.smartarch.platform.log.api.IConst;
import com.smartarch.platform.log.bean.LogMessage;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class RecordLogThread {
	// 队列最大长度
	private static final int MAX_COUNT = 100000;
	private static final int BULK_SIZE = 128;
	private static RecordLogThread instance = null;
	// 入库队列
	private ArrayBlockingQueue<LogMessage> queue = new ArrayBlockingQueue<>(MAX_COUNT);

	private InsertLogHandler handler = null;

	private RecordLogThread() {
		handler = new InsertLogHandler();
	}

	public static synchronized RecordLogThread getInstance() {
		if (instance == null) {
			instance = new RecordLogThread();
		}
		return instance;
	}

	public void start() {
		Thread operateThread = new Thread(new WriteLogTask(), "RecordLogThread-operate");
		operateThread.setDaemon(true);
		operateThread.start();
	}

	public boolean addLog(LogMessage logMessage) {
		if (logMessage == null) {
			log.error("logMessage is null");
			return false;
		}
		// 队列满，立即返回false
		boolean isOffered = queue.offer(logMessage);
		if (!isOffered) {
			log.error("The log operate queue is full!");
		}
		Thread.yield();
		return isOffered;
	}

	private List<LogMessage> handleRequest(List<LogMessage> list) {
		if (list == null || list.isEmpty()) {
			return list;
		}
		try {
			handler.insertLog(list);
			return null;
		} catch (Exception e) {
			log.error("insert log error:{}", e);
			return list;
		}
	}

	private void handleFailedList(List<LogMessage> fails) {
		if (fails == null) {
			return;
		}

		fails.forEach(e -> back2Queue(e));
	}

	private void back2Queue(LogMessage logMessage) {
		int retry = logMessage.getRetry();
		if (retry < 5) {
			logMessage.setRetry(retry + 1);
			addLog(logMessage);
		} else {
			log.warn("log:{} send failed and has exceeded 5 times. it will not send again.", logMessage);
		}
	}

	private void sleep() {
		try {
			Thread.sleep(IConst.DEFAULT_RECONNECTION_DELAY_TIME);
		} catch (InterruptedException e) {
			log.info("sleep failed.", e);
		}
	}

	private class WriteLogTask implements Runnable {

		List<LogMessage> list = new ArrayList<>(BULK_SIZE);
		private volatile boolean running = true;

		public void run() {
			while (running) {
				try {
					if (!handler.available()) {
						sleep();
						continue;
					}
					LogMessage req = queue.take();
					if (req != null) {
						list.add(req);
					}

					if (queue.size() == 0 || list.size() == BULK_SIZE) {
						List<LogMessage> fails = handleRequest(list);
						handleFailedList(fails);
						list.clear();

					}
				} catch (Throwable e) {
					log.error("write log task fail.", e);
					list.clear();
				}
			}
		}

		void setRunning(boolean running) {
			this.running = running;
		}
	}

}
