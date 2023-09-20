package com.landray.kmss.common.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import com.fasterxml.jackson.databind.JavaType;
import com.landray.kmss.sys.log.util.SystemLogHelper;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.RestResponse;
import com.landray.kmss.web.annotation.DataTemplateUrl;
import com.landray.kmss.web.springmvc.convert.KmssHttpMessageNotReadableException;
import com.landray.kmss.web.util.RestResponseUtils;


/**
 * 对所有@Controller标记的bean做异常处理，并封装成 {@link RestResponse}，
 * 被springControllerExceptionHandlerExceptionResolver调用
 * 注意，它是众多@ControllerAdviceBean中最后一个被调用的
 * @author 陈进科
 * @since 1.0  2018年12月20日
 *
 */
@ControllerAdvice
public class SpringControllerResponseEntityExceptionHandler extends ResponseEntityExceptionHandler implements Ordered{

	private boolean showExceptionDetail = false;
	@Override
	protected final ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		if(logger.isWarnEnabled()) {
			logger.warn("Occurs exception: " + ex.toString(),ex);
		}
		if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
			request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
		}
		if(body instanceof RestResponse) {
			ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(body, headers, status);
			SystemLogHelper.setExceptionResponse(responseEntity);
			return responseEntity;
		}else {
			RestResponse<Object> defaultResult = getDefaultResult(ex, headers, status, request);
			if(body!=null) {
				defaultResult.setData(body);
			}
			ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(defaultResult, headers, status);
			SystemLogHelper.setExceptionResponse(responseEntity);
			return responseEntity;
		}
	}
	
	/**
	 * 意外异常，返回500状态码，
	 * 理论上来说，当业务异常被抛出的时候，不占用HttpStatus状态码
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler({
	    KmssApiException.class,
		Exception.class
	})
	public final ResponseEntity<Object> handleUnknownException(Exception ex, WebRequest request) {
		HttpHeaders headers = new HttpHeaders();
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		if(ex instanceof HttpStatusCodeException){//这个可能由服务间调用链导致，直接透传statuscode
		    HttpStatusCodeException hsce = (HttpStatusCodeException)ex;
		    status = hsce.getStatusCode();
		    headers = hsce.getResponseHeaders();
		}
		return handleExceptionInternal(ex,null,headers,status,request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Object obj = null;
		if(ex instanceof KmssHttpMessageNotReadableException) {
			Object additionalInfo = ((KmssHttpMessageNotReadableException)ex).getAdditionalInfo();
			if(additionalInfo instanceof JavaType) {
				JavaType type = (JavaType)additionalInfo;
				RestResponse<?> ae = getDefaultResult(ex, headers, status, request);
				Class<?> rawClass = type.getRawClass();
				DataTemplateUrl findAnnotation = AnnotationUtils.findAnnotation(rawClass, DataTemplateUrl.class);
				if(findAnnotation!=null) {
					String url = (String)AnnotationUtils.getValue(findAnnotation);
					String contextPath = request.getContextPath();
					url = contextPath+url;
					if(StringUtil.isNotNull(url)) {
						ae.setSuggestion("get body template from: "+url);
					}
				}else{
					if(logger.isWarnEnabled()) {
						logger.warn("No DataTemplateUrl annotation be found for "+rawClass+", "
								+ "please set it or use a custom type.");
					}
				}
				return handleExceptionInternal(ex, ae, headers, status, request);
			}
		}
		return handleExceptionInternal(ex, obj, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<MediaType> mediaTypes = ex.getSupportedMediaTypes();
		RestResponse<?> ae = getDefaultResult(ex, headers, status, request);
		if (!CollectionUtils.isEmpty(mediaTypes)) {
			headers.setAccept(mediaTypes);
		}
		ae.setSuggestion("The 'Content-Type' should be one of "+String.valueOf(mediaTypes));
		return handleExceptionInternal(ex, ae, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		pageNotFoundLogger.warn(ex.getMessage());
		RestResponse<?> ae = getDefaultResult(ex, headers, status, request);
		Set<HttpMethod> supportedMethods = ex.getSupportedHttpMethods();
		if (!supportedMethods.isEmpty()) {
			headers.setAllow(supportedMethods);
		}
		ae.setSuggestion("The http method should be one of "+String.valueOf(supportedMethods));
		return handleExceptionInternal(ex, null, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		RestResponse<?> ae = getDefaultResult(ex, headers, status, request);
		ae.setSuggestion("The 'Accept' shoud be one of "+String.valueOf(ex.getSupportedMediaTypes()));
		return handleExceptionInternal(ex, ae, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return handleExceptionInternal(ex, null, headers, status, request);
	}
	
	/**
	 * 构造默认的消息体部
	 * @param ex
	 * @param body
	 * @param headers
	 * @param status
	 * @param request
	 * @return
	 */
	protected RestResponse<Object> getDefaultResult(Exception ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String msg = ex.getMessage();
		String code = RestResponseUtils.getCodeByHttpStatus(status);
		if(ex instanceof KmssApiException){
		    KmssApiException kae = (KmssApiException)ex;
		    code = kae.getCode();
		    msg = RestResponseUtils.getMsgByCode(code);
		}
		
		if(showExceptionDetail) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw,true);
			try {
				ex.printStackTrace(pw);
				msg = (sw.toString());
			}catch(Exception e){
			    //should not happen
			    logger.error(e.toString());
			}finally {
				IOUtils.closeQuietly(sw);
				IOUtils.closeQuietly(pw);
			}
		}
		RestResponse<Object> ae = RestResponse.error(code,msg);
		return ae;
	}
	
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
