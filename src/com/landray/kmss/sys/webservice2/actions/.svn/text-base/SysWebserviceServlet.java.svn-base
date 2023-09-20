package com.landray.kmss.sys.webservice2.actions;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;
import org.slf4j.Logger;

import com.landray.kmss.sys.webservice2.interfaces.ResponseWrapper;
import com.landray.kmss.sys.webservice2.service.ISysWebserviceMainService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class SysWebserviceServlet extends CXFNonSpringServlet {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysWebserviceServlet.class);

	/**
	 * 扩展CXF，支持不加载SPRING的方式启动
	 */
	@Override
	protected void loadBus(ServletConfig servletConfig) {
		super.loadBus(servletConfig);

		Bus bus = this.getBus();
		BusFactory.setDefaultBus(bus);

		ISysWebserviceMainService sysWebserviceMainService = (ISysWebserviceMainService) SpringBeanUtil
				.getBean("sysWebserviceMainService");

		// 设置总线
		sysWebserviceMainService.setBus(bus);

		// 启动所有数据库中的服务
		sysWebserviceMainService.startAllServices();

	}

	@Override
    protected void invoke(HttpServletRequest request,
                          HttpServletResponse response)
			throws ServletException {
		String scheme = request.getScheme();
		String queryString = request.getQueryString();
		String method = request.getMethod();
		if ("https".equals(scheme) && StringUtil.isNotNull(queryString)
				&& queryString.endsWith("wsdl")
				&& "get".equalsIgnoreCase(method)) {
			invokeHttps(request, response);
		} else {
			super.invoke(request, response);
		}
	}

	protected void invokeHttps(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException {
		ResponseWrapper responseWrapper = null;
		try {
			responseWrapper = new ResponseWrapper(
					(HttpServletResponse) response);
		} catch (IOException e1) {
			logger.error(e1.getMessage(), e1);
		}

		if (responseWrapper != null) {
			super.invoke(request, responseWrapper);
			try {
				// String contentType = responseWrapper.getContentType();
				byte[] content = responseWrapper.getResponseData();
				String str = new String(content);
				logger.debug(str);
				str = StringUtils.replace(str, "location=\"http://",
						"location=\"https://");
				content = str.getBytes();

				OutputStream out = response.getOutputStream();
				out.write(content);
				out.flush();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

		} else {
			super.invoke(request, response);
		}
	}
}
