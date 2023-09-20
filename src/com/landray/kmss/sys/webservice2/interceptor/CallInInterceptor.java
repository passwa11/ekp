package com.landray.kmss.sys.webservice2.interceptor;

import java.io.InputStream;
import java.util.Map;
import org.slf4j.Logger;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingMessage;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;

import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.log.service.ISysLogSystemService;
import com.landray.kmss.sys.webservice2.service.ISysWebserviceLogService;
import com.landray.kmss.sys.webservice2.thread.SysWebserviceLogThreadLocal;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class CallInInterceptor extends LoggingInInterceptor {
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

	public CallInInterceptor() {
		super(Phase.PRE_STREAM);
	}

	@Override
	public void handleMessage(Message message)
			throws Fault {
		logging(message);
	}

	protected void logging(Message message)
			throws Fault {

		try {
			Map map = getSysAppConfigService().findByKey(
					"com.landray.kmss.sys.webservice2.model.SysWebserviceLogConfig");
			if (!"1".equals(map.get("dataType"))) {
				return;
			}
			LoggingMessage buffer = new LoggingMessage(
					"", "");
			String encoding = (String) message.get(Message.ENCODING);
			String ct = (String) message.get("Content-Type");

			InputStream is = (InputStream) message
					.getContent(InputStream.class);
			if (is != null) {
				CachedOutputStream bos = null;
				try {
					bos = new CachedOutputStream();
					IOUtils.copy(is, bos);

					bos.flush();
					is.close();
					message.setContent(InputStream.class, bos.getInputStream());
					writePayload(buffer.getPayload(), bos, encoding, ct,false);

					bos.close();
				} catch (Exception e) {
					throw new Fault(e);
				} finally {
					try {
						bos.close();
					} catch (Exception e1) {

					}
				}
			}
			String requestString = buffer.toString();
			if (StringUtil.isNotNull(requestString)
					&& requestString.contains("Payload: ")) {
				requestString = requestString
						.substring(requestString.indexOf("Payload: ") + 9);
			}
			// System.out.println("requestString1:" + requestString);
			SysWebserviceLogThreadLocal.setRequestString(requestString);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}


	@Override
	protected java.util.logging.Logger getLogger() {
		return null;
	}

}
