package com.landray.kmss.sys.oms.temp;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.util.StringUtil;

/**
 * 同步日志
 * @author yuLiang
 * @date 2020年5月9日
 */
public class SyncLog{
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SyncLog.class);

	public static final String SPACE_RTF = "&nbsp;";
	public static final String SPACE_TEXTAREA = "\t";
	public static final String NEWLINE_CHAR_RTF = "<br/>";
	public static final String NEWLINE_CHAR_TEXTAREA = "\r\n";
	public static String NEWLINE_CHAR = NEWLINE_CHAR_TEXTAREA;
	public static String SPACE_CHAR = SPACE_TEXTAREA;
	public static final String ERROR_KEY = "[ERROR]";
	private SyncLogSummary summary;
	private String fdLogError;
	private String fdLogDetail;
	//日志是否写入数据库
	private boolean isWriteToDb = true;
	public void resetSummary() {
		this.summary = new SyncLogSummary();
	}

	public SyncLogSummary getSummary() {
		return summary;
	}

	public void setSummary(SyncLogSummary summary) {
		this.summary = summary;
	}

	/* ***********************日志 添加缩进************************ */
	private String addSpace(int spaceNum, String str) {
		if(SPACE_CHAR.equals(SPACE_TEXTAREA)) {
            spaceNum /= 4;
        }
		for (int i = 0; i < spaceNum; i++) {
            str = SPACE_CHAR + str;
        }
		return str;
	}

	public void info(int spaceNum,String str, Object... params) {
		str = addSpace(spaceNum, str);
		info(str, params);
	}
	
	public String format(int spaceNum, String str, Object... params) {
		str = addSpace(spaceNum, str);
		return format(str, params);
	}
	public void warn(int spaceNum, String str, Object... params) {
		str = addSpace(spaceNum, str);
		warn(str, params);
	}

	public void warn(int spaceNum, String str, Throwable e, Object... params) {
		str = addSpace(spaceNum, str);
		warn(str, e, params);
	}

	public void error(int spaceNum, String str, Object... params) {
		str = addSpace(spaceNum, str);
		error(str, params);
	}

	public void error(int spaceNum, String str, Throwable e, Object... params) {
		str = addSpace(spaceNum, str);
		error(str, e, params);
	}
	
	/* ***********************日志************************ */

	public static String formatStr(String str, Object... params) {
		if (params != null && params.length > 0) {
			String val = null;
			for (int i = 0; i < params.length; i++) {
				if (params[i] != null) {
					val = params[i].toString();
				} else {
					val = "";
				}
				str = str.replace("{" + i + "}", val);
			}
		}
		return str;
	}
	
	public String format(String str, Object... params) {
		if (params != null && params.length > 0) {
			String val = null;
			for (int i = 0; i < params.length; i++) {
				if (params[i] != null) {
					val = params[i].toString();
				} else {
					val = "";
				}
				str = str.replace("{" + i + "}", val);
			}
		}
		return str;
	}

	public void info(String str, Object... params) {
		str = format(str, params);
		logger.warn(str);
		if(isWriteToDb) {
			addDetail(str, false);
		}
	}

	/**
	 * 添加信息到logDetail及logError中
	 */
	public void globalInfo(String str, Object... params) {
		str = format(str, params);
		logger.info(str);
		addError(str);
		addDetail(str, false);
	}
	
	public void warn(String str, Object... params) {
		if(StringUtil.isNull(str)) {
            return;
        }
		
		str = format(str, params);
		logger.warn(str);
		addError(str);
		addDetail(str, true);
	}
	
	public void warn(String str, Throwable e, Object... params) {
		str = format(str, params);
		logger.error(str, e);
		str = str + getExceptionString(e);
		addError(str);
		addDetail(str, true);
	}

	public void error(String str, Object... params) {
		if(StringUtil.isNull(str)) {
            return;
        }
		
		str = format(str, params);
		logger.error(str);
		addError(str);
		addDetail(str, true);
	}

	public void error(Throwable e) {
		String str = getExceptionString(e);
		logger.error(str);
		addError(str);
		addDetail(str, true);
	}

	public void error(String str, Throwable e, Object... params) {
		str = format(str, params);
		logger.error(str, e);
		str = str + getExceptionString(e);
		addError(str);
		addDetail(str, true);
	}

	private void addError(String str) {
		if (fdLogError == null) {
            fdLogError = "";
        }
		fdLogError += str + NEWLINE_CHAR;
	}
	
	private void addDetail(String str, boolean error) {
		if (error) {
            str = ERROR_KEY + str;
        }
		if (fdLogDetail == null) {
            fdLogDetail = "";
        }
		fdLogDetail += str + NEWLINE_CHAR;
	}
	
	/**
	 * 获取错误的详细信息
	 * KmssException用于页面错误，故仅显示错误提示的部分内容
	 * 
	 * @param e
	 * @return
	 */
	public static String getExceptionString(Throwable e) {
		if(e instanceof KmssException){
			return e.getMessage();
		}else{
			StringBuffer rtnVal = new StringBuffer();
			appendExceptionString(e, rtnVal, 0);
			return NEWLINE_CHAR + rtnVal.toString();
		}
	}

	private static void appendExceptionString(Throwable e, StringBuffer rtnVal,
			int time) {
		String strMsg = e.toString();
		if (!StringUtil.isNull(strMsg)) {
			rtnVal.append(strMsg);
			rtnVal.append(NEWLINE_CHAR + SPACE_CHAR);
		}
		StackTraceElement[] ste = e.getStackTrace();
		if (ste.length > 0) {
			rtnVal.append(ste[0].toString());
			for (int i = 1; i < ste.length; i++) {
				rtnVal.append(NEWLINE_CHAR + SPACE_CHAR);
				rtnVal.append(ste[i].toString());
			}
		}
		if (e.getCause() != null) {
			if (time > 10) {
				rtnVal.append(NEWLINE_CHAR + "Caused by:" + e.getCause());
			} else {
				rtnVal.append(NEWLINE_CHAR + "Caused by:");
				appendExceptionString(e.getCause(), rtnVal, time + 1);
			}
		}
	}
	
	
	public String getFdLogError() {
		return fdLogError;
	}

	public void setFdLogError(String fdLogError) {
		this.fdLogError = fdLogError;
	}

	public String getFdLogDetail() {
		return fdLogDetail;
	}

	public void setFdLogDetail(String fdLogDetail) {
		this.fdLogDetail = fdLogDetail;
	}


	class SyncLogSummary {
		private static final String UNIT = "个";
		private static final String SPLIT = "，";
		private static final String END = "；";

		private String name;

		private Map<String, Integer> deptLogs;
		private Map<String, Integer> userLogs;

		public SyncLogSummary() {
			deptLogs = new ConcurrentHashMap<String, Integer>();
			userLogs = new ConcurrentHashMap<String, Integer>();
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void addDeptSummary(String type, int number) {
			AtomicInteger atoInt = new AtomicInteger(0);
			int val = 0;
			synchronized(this){
				if (deptLogs.containsKey(type)) {
					atoInt = new AtomicInteger(deptLogs.get(type));
				}
				val = atoInt.addAndGet(number);
			}
			
			deptLogs.put(type, val);
		}

		public void addUserSummary(String type, int number) {
			AtomicInteger atoInt = new AtomicInteger(0);
			int val = 0;
			synchronized(this){
				if (userLogs.containsKey(type)) {
					atoInt = new AtomicInteger(userLogs.get(type));
				}
				val = atoInt.addAndGet(number);
			}
			userLogs.put(type, val);
		}

		@Override
		public String toString() {
			StringBuilder result = new StringBuilder();
			result.append(getName()+":");
			Set<String> deptKeySet = deptLogs.keySet();
			for (String key : deptKeySet) {
				result.append(key);
				result.append("部门");
				result.append(deptLogs.get(key));
				result.append(UNIT);
				result.append(SPLIT);
			}
			Set<String> userKeySet = userLogs.keySet();
			for (String key : userKeySet) {
				result.append(key);
				result.append("人员");
				result.append(userLogs.get(key));
				result.append(UNIT);
				result.append(SPLIT);
			}
			if (result.toString().endsWith(SPLIT)) {
				result = new StringBuilder(result.substring(0, result.length() - 1));
			}
			result.append(END);
			return result.toString();
		}

		public Map<String,Integer> getErrOrWarnNum(){
			Object addDeptErr = deptLogs.get("新增异常");
			Object updDeptErr = deptLogs.get("更新异常");
			Object delDeptErr = deptLogs.get("删除异常");
			Object addUserErr = userLogs.get("新增异常");
			Object updUserErr = userLogs.get("更新异常");
			Object delUserErr = userLogs.get("删除异常");
			Map<String, Integer> err = new HashMap<String, Integer>();
			err.put("addDeptErr", addDeptErr == null?0:(Integer)addDeptErr);
			err.put("updDeptErr", updDeptErr == null?0:(Integer)updDeptErr);
			err.put("delDeptErr", delDeptErr == null?0:(Integer)delDeptErr);
			err.put("addUserErr", addUserErr == null?0:(Integer)addUserErr);
			err.put("updUserErr", updUserErr == null?0:(Integer)updUserErr);
			err.put("delUserErr", delUserErr == null?0:(Integer)delUserErr);
			return err;
		}
		
		
	}


	public boolean isWriteToDb() {
		return isWriteToDb;
	}

	public void setWriteToDb(boolean isWriteToDb) {
		this.isWriteToDb = isWriteToDb;
	}
	
	
}
