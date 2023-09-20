package com.landray.kmss.code.upgrade.utils;

import org.slf4j.Logger;

public class ProcessBar {
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	private int count = 0;

	private int process = 0;

	private int barsize = 100;

	private int cursize = 0;

	private boolean finish = false;

	public void addJob(int count) {
		this.count += count;
	}

	public int getJob() {
		return count;
	}

	public void start() {
		for (int i = 0; i < barsize; i++) {
			System.out.print("_");
		}
		logger.info("");
	}

	public void doJob(int p) {
		process += p;
		int size = (int) Math.round(process * barsize * 1.0 / count);
		for (; cursize < size && cursize < barsize; cursize++) {
			System.out.print("|");
		}
		if (process >= count) {
			if (!finish) {
				finish = true;
				logger.info("\r\n");
			}
		}
	}

	public boolean isFinish() {
		return finish;
	}
}
