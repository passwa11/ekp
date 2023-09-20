package com.landray.kmss.sys.webservice2.thread;

public class SysWebserviceLogThreadLocal {

	private static ThreadLocal<String> logId = new ThreadLocal<>();

	private static ThreadLocal<String> username = new ThreadLocal<>();

	private static ThreadLocal<String> requestString = new ThreadLocal<>();

	public static String getId() {
		return logId.get();
	}

	public static void setId(String id) {
		logId.set(id);
	}

	public static String getUsername() {
		return username.get();
	}

	public static void setUsername(String un) {
		username.set(un);
	}

	public static String getRequestString() {
		return requestString.get();
	}

	public static void setRequestString(String requestStr) {
		requestString.set(requestStr);
	}

}
