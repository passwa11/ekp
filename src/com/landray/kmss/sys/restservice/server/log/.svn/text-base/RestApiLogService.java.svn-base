package com.landray.kmss.sys.restservice.server.log;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.dict.SysDictSimpleProperty;
import com.landray.kmss.sys.log.common.model.BaseSystemLog;
import com.landray.kmss.sys.log.common.service.IBaseSystemLogService;
import com.landray.kmss.sys.log.util.ParseObjUtil;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerLog;
import com.landray.kmss.sys.restservice.server.service.ISysRestserviceServerLogService;

public class RestApiLogService extends BaseServiceImp implements IBaseSystemLogService {
	private final static Logger logger = org.slf4j.LoggerFactory.getLogger(RestApiLogService.class);

	private ISysRestserviceServerLogService sysRestserviceServerLogService;
	private ISysAppConfigService sysAppConfigService;

	public void setSysRestserviceServerLogService(ISysRestserviceServerLogService sysRestserviceServerLogService) {
		this.sysRestserviceServerLogService = sysRestserviceServerLogService;
	}

	public void setSysAppConfigService(ISysAppConfigService sysAppConfigService) {
		this.sysAppConfigService = sysAppConfigService;
	}

	private static final String CONFIG_KEY = "com.landray.kmss.sys.restservice.server.model.SysRestserviceServerLogConfig";
	private static final String CONFIG_DATATYPE = "dataType";

	public boolean isSaveHeaderInfo() {
		return "1".equals(getDataType()) || "2".equals(getDataType());
	}

	public boolean isSaveBodyInfo() {
		return "2".equals(getDataType());
	}

	@SuppressWarnings("rawtypes")
	private String getDataType() {
		try {
			Map map = sysAppConfigService.findByKey(CONFIG_KEY);
			return map.get(CONFIG_DATATYPE) != null ? map.get(CONFIG_DATATYPE).toString() : null;
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return null;
	}

	@Override
	public String saveLog(IBaseModel model) {
		try {
			return sysRestserviceServerLogService.add(model);
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return null;
	}

	@Override
	public IBaseModel convertModel(BaseSystemLog log) {
		SysRestserviceServerLog sysRsLog = new SysRestserviceServerLog();
		sysRsLog.setFdId(log.getFdId());
		sysRsLog.setFdName(log.getFdSubject());
		sysRsLog.setFdServiceName(log.getFdServiceBean());
		sysRsLog.setFdUserName(log.getFdUserName());
		sysRsLog.setFdClientIp(log.getFdClientIp());
		sysRsLog.setFdOriginUri(log.getFdOriginUri());
		sysRsLog.setFdStartTime(log.getFdStartTime());
		sysRsLog.setFdEndTime(log.getFdEndTime());

		Integer fdSuccess = log.getFdSuccess();
		if(fdSuccess != null){
			sysRsLog.setFdExecResult(String.valueOf(fdSuccess));
		}

		// 运行时长
		Long fdTaskDuration = log.getFdTaskDuration();
		if(fdTaskDuration != null){
			sysRsLog.setFdRunTime(fdTaskDuration/1000);
			sysRsLog.setFdRunTimeMillis(fdTaskDuration);
		}

		if (isSaveHeaderInfo()) {
			String fdRequestHeader = getFit(sysRsLog, "fdRequestHeader", log.getFdRequestHeader(), 1000);
			sysRsLog.setFdRequestHeader(fdRequestHeader);
			String fdResponseHeader = getFit(sysRsLog, "fdResponseHeader", log.getFdResponseHeader(), 1000);
			sysRsLog.setFdResponseHeader(fdResponseHeader);
		}
		if (isSaveBodyInfo()) {
			sysRsLog.setFdRequestMsg(log.getFdRequestMsg());
			sysRsLog.setFdResponseMsg(log.getFdResponseMsg());
		}
		sysRsLog.setFdErrorMsg(log.getFdDesc());
		return sysRsLog;
	}

	/**
	 * 根据数据字典获取字段适应长度的值
	 * @param sysRsLog
	 * @param filedName
	 * @param value
	 * @param defaultLength
	 * @return
	 */
	private String getFit(SysRestserviceServerLog sysRsLog, String filedName, String value, int defaultLength) {
		SysDictModel classDict = ParseObjUtil.getModelDict(sysRsLog);
		SysDictCommonProperty fieldDict = ParseObjUtil.getFieldDict(filedName, classDict);
		int length = defaultLength;
		if (fieldDict instanceof SysDictSimpleProperty) {
			length = ((SysDictSimpleProperty) fieldDict).getLength();
		}
		if (value != null && value.length() > length) {
			value = value.substring(0, length - 3) + "...";
		}
		return value;
	}

}
