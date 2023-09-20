package com.landray.kmss.common.module.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 模块解耦中心异常处理类
 *
 * @author 严明镜
 * @version 1.0 2021年02月19日
 */
public class ExceptionUtil {

	private static final Logger log = LoggerFactory.getLogger(ExceptionUtil.class);

	public static void handleException(String message, Exception t, WarnLevel level) {
		if (WarnLevel.ERROR.equals(level)) {
			throwRuntimeException(message, t);
		} else if (WarnLevel.WARN.equals(level)) {
			printException(message, t);
		} else {
			if(log.isDebugEnabled()) {
				log.debug(message, t);
			}
		}
	}

	public static void throwRuntimeException(String message, Exception t) throws RuntimeException {
		log.error(message, t);
		throw new RuntimeException(t);
	}

	public static void printException(String message, Exception t) {
		log.warn(message, t);
	}
}
