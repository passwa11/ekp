package com.landray.kmss.sys.filestore.location.service;

import com.google.api.client.http.HttpMethods;
import com.landray.kmss.sys.attachment.util.SysAttCryptUtil;
import com.landray.kmss.sys.filestore.location.interfaces.ISysFileLocationDirectService;
import com.landray.kmss.sys.filestore.location.model.SysFileSignature;
import com.landray.kmss.sys.filestore.location.model.SysFileSignatureRequest;
import com.landray.kmss.sys.log.util.ua.Browser;
import com.landray.kmss.sys.log.util.ua.UserAgent;
import com.landray.kmss.util.DbUtils;
import com.landray.kmss.util.StringUtil;

import java.util.Map;

public abstract class AbstractSysFileLocationDirectService
		implements ISysFileLocationDirectService {

	@Override
	public String getMethodName() {
		return HttpMethods.POST;
	}

	@Override
	public String getDeleteUrl(String fdPath) throws Exception {
		return null;
	}

	@Override
	public String getFileVal() {
		return "file";
	}
	
	/**
	 * 是否支持直连，由子类实现
	 * @return
	 */
	protected abstract Boolean isSupportDirect();

	/**
	 * 是否支持服务直连, 默认不支持<br/>
	 * @return
	 */
	@Override
    public Boolean isSupportServiceDirect(){
		return false;
	}
	
	/**
	 * 是否支持直连，IE9及以下版本不支持cos跨域上传，上传判断直连支持时要传User-Agent头
	 * 下载时无跨域限制，可以给userAgent传空串
	 * @param userAgent 浏览器标识
	 * @return
	 */
	@Override
	public Boolean isSupportDirect(String userAgent){
		
		//IE9及9以下版本的浏览器不支持cors跨域，不使用直连上传
		UserAgent ua = new UserAgent(userAgent);
		Browser browser = ua.getBrowser();
		if(browser == Browser.IE9 || browser == Browser.IE8 || browser == Browser.IE7
				|| browser == Browser.IE6 || browser == Browser.IE5_5 || browser == Browser.IE5){
			return false;
		}
		return isSupportDirect();
	}
	
	@Override
	public String getUploadUrl(String userAgent) throws Exception{
		if(!isSupportDirect(userAgent)){
			return "/sys/attachment/uploaderServlet?gettype=upload&format=json";
		}
		return getUploadUrl();
	}
	
	/**
	 * 上传链接，由子类实现
	 * @return
	 * @throws Exception
	 */
	protected abstract String getUploadUrl() throws Exception;

	@Override
	public SysFileSignature getSignature(SysFileSignatureRequest signatureRequest) throws Exception {
		if(isSupportDirect(signatureRequest.getUserAgent())){
			return createSignature(signatureRequest);
		}
		SysFileSignature signature = new SysFileSignature();

		Map<String, String> datas = signature.getHeader();

		// 以下是从SysAttMainAction中拷贝过来的
		long expire = DbUtils.getDbTime().getTime();

		String fileMd5 = signatureRequest.getFileMd5();
		String fileSize = signatureRequest.getFileSize();
		String fdAttMainId = signatureRequest.getAttId();

		String fileInfo = "&md5="
				+ (StringUtil.isNotNull(fileMd5) ? fileMd5 : "") + "&filesize="
				+ (StringUtil.isNotNull(fileSize) ? fileSize : "") + "&time="
				+ expire;

		// 将附件id加入userkey中，做附件更新用
		if (StringUtil.isNotNull(fdAttMainId)) {
			fileInfo += "&fdAttMainId=" + fdAttMainId;
		}

		fileInfo = SysAttCryptUtil.encrypt(fileInfo);

		datas.put("userkey", fileInfo);

		return signature;
	}

	protected abstract SysFileSignature createSignature(SysFileSignatureRequest signatureRequest)
			throws Exception;

}
