package com.landray.kmss.sys.webservice2.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.log.forms.SysLogSystemForm;
import com.landray.kmss.sys.log.service.ISysLogSystemService;
import com.landray.kmss.sys.log.util.LogConstant.LogSystemType;
import com.landray.kmss.sys.webservice2.constant.SysWsConstant;
import com.landray.kmss.sys.webservice2.exception.SysWsException;
import com.landray.kmss.sys.webservice2.model.SysWebserviceLog;
import com.landray.kmss.sys.webservice2.service.ISysWebserviceLogService;
import com.landray.kmss.sys.webservice2.util.SysWsUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

/**
 * WebService消息异常处理
 * 
 */
public class ErrorInterceptor extends AbstractPhaseInterceptor<SoapMessage> {
	
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

	private Logger logger = LoggerFactory.getLogger(getClass());

	public ErrorInterceptor() {
		super(Phase.RECEIVE);
	}

	@Override
	public void handleMessage(SoapMessage message) throws Fault {

	}

	/**
	 * 记录出错信息
	 */
	@Override
	public void handleFault(SoapMessage message) {
		Exception exception = message.getContent(Exception.class);

		// 如果异常为空或者为非法用户、非法IP地址和访问受限时，不继续处理
		if (exception == null || exception.getCause() instanceof SysWsException) {
			return;
		}

		SOAPMessage soapMessage = message.getContent(SOAPMessage.class);
		if (soapMessage == null) {
			new SAAJInInterceptor().handleMessage(message);
			soapMessage = message.getContent(SOAPMessage.class);
		}

		try {
			if (soapMessage != null) {
				updateLog(soapMessage, exception);
			}
			// 记录日志文件
			logger.error(ResourceUtil
					.getString("sysWs.errMsg.service.exception",
							"sys-webservice2"), exception);
		} catch (Exception e) {
			logger.error("webservice日志写入错误", e);
			throw new Fault(e);
		}

	}

	/**
	 * 记录数据库日志
	 * 
	 * @param soapMessage
	 * @param exception
	 * @throws Exception
	 */
	private void updateLog(SOAPMessage soapMessage, Exception exception)
			throws Exception {
		SOAPHeader head = soapMessage.getSOAPHeader();
		String instanceId = SysWsUtil.getTextContent(head, "tns:instanceId");

		if (StringUtil.isNotNull(instanceId)) {
			SysWebserviceLog sysWsLog = getSysWebserviceLogService().findServiceLog(instanceId);
			String fdErrorMsg = getStackTrace(exception);
			// 记录elastic日志
			if (getSysLogSystemService() != null
					&& "true".equals(ResourceUtil.getKmssConfigString("log.openLogService"))) {
				SysLogSystemForm dto = new SysLogSystemForm();
				dto.setFdId(sysWsLog.getFdId());
				dto.setFdDesc(fdErrorMsg);
				dto.setFdSuccess(StringUtil.getIntFromString(SysWsConstant.SERVICE_EXCEPTION, -1));
				dto.setFdType(LogSystemType.WEBSERVICE.getVal());
				getSysLogSystemService().update(dto);
			}
			// 记录数据库日志
			sysWsLog.setFdErrorMsg(fdErrorMsg);
			sysWsLog.setFdExecResult(SysWsConstant.SERVICE_EXCEPTION);
			getSysWebserviceLogService().update(sysWsLog);
		}
	}

	/**
	 * 获取异常的堆栈信息
	 * 
	 * @param t
	 * @return
	 */
	private String getStackTrace(Throwable t) {
		String exceptionStack = null;

		if (t != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);

			try {
				t.printStackTrace(pw);
				exceptionStack = sw.toString();
			} finally {
				try {
					sw.close();
					pw.close();
				} catch (IOException e) {
				}
			}
		}

		return exceptionStack;
	}

}
