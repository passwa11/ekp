package com.landray.kmss.sys.webservice2.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;

import com.landray.kmss.framework.service.plugin.Plugin;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

import com.landray.kmss.sys.log.forms.SysLogSystemForm;
import com.landray.kmss.sys.log.service.ISysLogSystemService;
import com.landray.kmss.sys.log.util.LogConstant.LogSystemType;
import com.landray.kmss.sys.webservice2.constant.SysWsConstant;
import com.landray.kmss.sys.webservice2.model.SysWebserviceLog;
import com.landray.kmss.sys.webservice2.service.ISysWebserviceLogService;
import com.landray.kmss.sys.webservice2.util.SysWsUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 在调用服务后记录信息
 * 
 */
public class CallAfterInterceptor extends AbstractPhaseInterceptor<SoapMessage> {
	
	private ISysWebserviceLogService sysWebserviceLogService = null;
	private ISysLogSystemService sysLogSystemService = null;

	public ISysWebserviceLogService getSysWebserviceLogService() {
		if (sysWebserviceLogService == null) {
            sysWebserviceLogService = (ISysWebserviceLogService) SpringBeanUtil.getBean("sysWebserviceLogService");
        }
		return sysWebserviceLogService;
	}

	public ISysLogSystemService getSysLogSystemService() {
		if (sysLogSystemService == null) {
            sysLogSystemService = (ISysLogSystemService) SpringBeanUtil.getBean("sysLogSystemService");
        }
		return sysLogSystemService;
	}

	public CallAfterInterceptor() {
		super(Phase.POST_INVOKE);
	}

	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		HttpServletRequest request = Plugin.currentRequest();
		if(request!=null && request.getQueryString()!=null){
			if("get".equalsIgnoreCase(request.getMethod()) && request.getQueryString().endsWith("wsdl")){
				return;
			}
		}
		SOAPMessage soapMessage = message.getContent(SOAPMessage.class);
		if (soapMessage == null) {
			new SAAJInInterceptor().handleMessage(message);
			soapMessage = message.getContent(SOAPMessage.class);
		}

		try {
			updateLog(soapMessage);
		} catch (Exception e) {
			throw new Fault(e);
		}
	}

	/**
	 * 更新数据库日志
	 * 
	 * @param soapMessage
	 * @param exception
	 * @throws Exception
	 */
	private void updateLog(SOAPMessage soapMessage) throws Exception {
		SOAPHeader head = soapMessage.getSOAPHeader();
		String instanceId = SysWsUtil.getTextContent(head, "tns:instanceId");

		if (StringUtil.isNotNull(instanceId)) {
			SysWebserviceLog sysWsLog = (SysWebserviceLog) getSysWebserviceLogService().findByPrimaryKey(instanceId, null, true);

			// 计算运行时长
			long startTimeInMillis = sysWsLog.getFdStartTime().getTime();
			Date now = new Date();
			long endTimeInMillis = now.getTime();
			long runTime = (endTimeInMillis - startTimeInMillis) / 1000;
			long runTimeMillis = endTimeInMillis - startTimeInMillis;
			
			// 记录
			if (getSysLogSystemService() != null
					&& "true".equals(ResourceUtil.getKmssConfigString("log.openLogService"))) {
				SysLogSystemForm dto = new SysLogSystemForm();
				dto.setFdId(sysWsLog.getFdId());
				dto.setFdEndTime(now);
				dto.setFdTaskDuration(endTimeInMillis - startTimeInMillis);
				dto.setFdSuccess(StringUtil.getIntFromString(SysWsConstant.EXEC_SUCCESS, -1));
				dto.setFdType(LogSystemType.WEBSERVICE.getVal());
				getSysLogSystemService().update(dto);
			}
			// 记录数据日志
			sysWsLog.setFdEndTime(now);
			sysWsLog.setFdRunTime(runTime);
			sysWsLog.setFdRunTimeMillis(runTimeMillis);
			sysWsLog.setFdExecResult(SysWsConstant.EXEC_SUCCESS);
			getSysWebserviceLogService().update(sysWsLog);

		}
	}

}
