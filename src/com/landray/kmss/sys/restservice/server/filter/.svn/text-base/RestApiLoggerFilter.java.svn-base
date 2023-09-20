package com.landray.kmss.sys.restservice.server.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.landray.kmss.sys.log.common.service.IBaseSystemLogService;
import com.landray.kmss.sys.log.util.LogConstant.LogSystemType;
import com.landray.kmss.sys.log.util.SystemLogHelper;
import com.landray.kmss.sys.log.util.http.stream.ResponseCopier;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * 用于RestApi的日志
 * @author yanmj
 */
public class RestApiLoggerFilter extends OncePerRequestFilter {
	private IBaseSystemLogService restApiLogService;

	public IBaseSystemLogService getRestApiLogService() {
		if (restApiLogService == null) {
            restApiLogService = (IBaseSystemLogService) SpringBeanUtil.getBean("restApiLogService");
        }
		return restApiLogService;
	}
	
	private long streamMaxSize = -1;

	public void setStreamMaxSize(long streamMaxSize) {
		this.streamMaxSize = streamMaxSize;
	}

	@Override
	protected final void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		SystemLogHelper.initRequest(request, getRestApiLogService(), LogSystemType.RESTSERVICE.getVal());
		ResponseCopier responseCopier = null;
		try {
			responseCopier = new ResponseCopier(response, streamMaxSize);
			filterChain.doFilter(request, responseCopier);
		} catch (Exception e) {
			logger.error(e.toString());
			throw e;
		} finally {
			try{
				if(responseCopier != null) {
                    SystemLogHelper.initResponse(responseCopier);
                }
			}catch (Exception e2) {
				logger.error(e2);
			}finally {
				// 保存日志
				SystemLogHelper.saveLog(getRestApiLogService());
			}
		}
	}
}
