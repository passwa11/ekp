package com.landray.kmss.sys.webservice2.interceptor;

import java.io.OutputStream;
import java.util.Map;
import org.slf4j.Logger;

import org.apache.cxf.interceptor.AbstractLoggingInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CacheAndWriteOutputStream;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.io.CachedOutputStreamCallback;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;

import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.log.forms.SysLogSystemForm;
import com.landray.kmss.sys.log.service.ISysLogSystemService;
import com.landray.kmss.sys.webservice2.model.SysWebserviceLog;
import com.landray.kmss.sys.webservice2.service.ISysWebserviceLogService;
import com.landray.kmss.sys.webservice2.thread.SysWebserviceLogThreadLocal;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class CallOutInterceptor extends AbstractLoggingInterceptor {
	private ISysWebserviceLogService sysWebserviceLogService = null;
	private ISysLogSystemService sysLogSystemService = null;
	private ISysAppConfigService sysAppConfigService = null;

	public ISysWebserviceLogService getSysWebserviceLogService() {
		if (sysWebserviceLogService == null) {
            sysWebserviceLogService = (ISysWebserviceLogService) SpringBeanUtil
                    .getBean("sysWebserviceLogService");
        }
		return sysWebserviceLogService;
	}

	public ISysLogSystemService getSysLogSystemService() {
		if (sysLogSystemService == null) {
            sysLogSystemService = (ISysLogSystemService) SpringBeanUtil
                    .getBean("sysLogSystemService");
        }
		return sysLogSystemService;
	}

	public ISysAppConfigService getSysAppConfigService() {
		if (sysAppConfigService == null) {
			sysAppConfigService = (ISysAppConfigService) SpringBeanUtil
					.getBean("sysAppConfigService");
		}
		return sysAppConfigService;
	}

	public CallOutInterceptor() {
		super(Phase.PRE_STREAM);
	}

	@Override
	public void handleMessage(Message message) throws Fault {
		OutputStream os = (OutputStream) message.getContent(OutputStream.class);
		if (os == null) {
			return;
		}
		CacheAndWriteOutputStream newOut = new CacheAndWriteOutputStream(os);
		message.setContent(OutputStream.class, newOut);
		newOut.registerCallback(new LoggingCallback(message));
	}

	class LoggingCallback implements CachedOutputStreamCallback {
		private final Message message;

		public LoggingCallback(Message msg) {
			message = msg;
		}

		@Override
		public void onFlush(CachedOutputStream cos) {
		}

		@Override
		public void onClose(CachedOutputStream cos) {
			StringBuilder payload = new StringBuilder();
			String fdResponseMsg = null;
			String instanceId = SysWebserviceLogThreadLocal.getId();
			String encoding = (String) message.get(Message.ENCODING);
			String ct = (String) message.get("Content-Type");
			try {
				// todo
				writePayload(payload, cos, encoding, ct,false);
				//// 判断后台配置是否为记录详细数据
				Map map = getSysAppConfigService().findByKey(
						"com.landray.kmss.sys.webservice2.model.SysWebserviceLogConfig");
				if ("1".equals(map.get("dataType"))) {
					// 获取请求的报文
					fdResponseMsg = payload.toString();
					// 更新日志
					updateLog(instanceId, fdResponseMsg);
				}
			} catch (Exception ex) {
			}
		}
	}
	/**
	 * 更新数据库日志
	 * 
	 * @param soapMessage
	 * @param exception
	 * @throws Exception
	 */
	private void updateLog(String instanceId, String fdResponseMsg)
			throws Exception {
		if (StringUtil.isNotNull(instanceId)) {
			SysWebserviceLog sysWsLog = (SysWebserviceLog) getSysWebserviceLogService()
					.findByPrimaryKey(instanceId, null, true);
			// 记录
			if (getSysLogSystemService() != null
					&& "true".equals(ResourceUtil
							.getKmssConfigString("log.openLogService"))) {
				SysLogSystemForm dto = new SysLogSystemForm();
				dto.setFdId(sysWsLog.getFdId());
				if (StringUtil.isNotNull(fdResponseMsg)) {
					dto.setFdResponseMsg(fdResponseMsg);
				}
				String requestString = SysWebserviceLogThreadLocal
						.getRequestString();
				System.out.println("requestString2:" + requestString);
				if (StringUtil.isNotNull(requestString)) {
					dto.setFdRequestMsg(requestString);
				}
				getSysLogSystemService().update(dto);
			}
			if (StringUtil.isNotNull(fdResponseMsg)) {
				sysWsLog.setFdResponseMsg(fdResponseMsg);
			}
			// 记录数据日志
			getSysWebserviceLogService().update(sysWsLog);
			SysWebserviceLogThreadLocal.setRequestString(null);
		}
	}

	@Override
	protected java.util.logging.Logger getLogger() {
		return null;
	}

}
