package com.landray.kmss.sys.filestore.location.log.service.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.filestore.location.log.model.SysFileLocationLog;
import com.landray.kmss.sys.filestore.location.log.service.ISysFileLocationLogService;

public class SysFileLocationLogByLog4jService
		implements ISysFileLocationLogService {

	private String SPILT_CHAR = "|";

	private static Logger logger =
			org.slf4j.LoggerFactory.getLogger(SysFileLocationLogByLog4jService.class);

	@Override
	public void write(SysFileLocationLog log) throws Exception {

		logger.debug(format(log));

	}

	private String format(SysFileLocationLog log) {

		StringBuilder builder = new StringBuilder();
		builder.append("用户：").append(log.getFdLoginName()).append(SPILT_CHAR)
				.append("时间：").append(log.getFdNow()).append(SPILT_CHAR)
				.append("操作名：").append(log.getFdName()).append(SPILT_CHAR)
				.append("传入参数：").append(log.getFdReq()).append(SPILT_CHAR)
				.append("返回结果：").append(log.getFdResp());
		return builder.toString();
	}

	@Override
	public Boolean isEnable() {
		return logger.isDebugEnabled();
	}
}
