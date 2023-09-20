package com.landray.kmss.sys.filestore.location.service;

import com.landray.kmss.sys.filestore.location.interfaces.ISysFileLocationDirectService;
import com.landray.kmss.sys.filestore.location.log.model.SysFileLocationLog;
import com.landray.kmss.sys.filestore.location.log.service.SysFileLocationLogWriter;
import com.landray.kmss.sys.filestore.location.model.SysFileSignature;
import com.landray.kmss.sys.filestore.location.model.SysFileSignatureRequest;
import org.slf4j.Logger;

public class SysFileLocationDirectService
		implements ISysFileLocationDirectService {

	private static Logger logger =
			org.slf4j.LoggerFactory.getLogger(SysFileLocationDirectService.class);

	/**
	 * 具体的流直连类
	 */
	private ISysFileLocationDirectService directService;

	public SysFileLocationDirectService(
			ISysFileLocationDirectService directService) {

		super();
		this.directService = directService;

	}

	@Override
	public String getDownloadUrl(final String fdPath,String fileName) throws Exception {

		final String downloadUrl;

		try {
			downloadUrl = directService.getDownloadUrl(fdPath,fileName);
		} catch (Exception e) {
			logger.error("获取下载链接报错：", e);
			throw e;
		}

		new SysFileLocationLogWriter() {

			@Override
			public void bulidLog(SysFileLocationLog log) {

				log.setFdName("获取下载链接");
				log.setFdReq(fdPath);
				log.setFdResp(downloadUrl);
			}
		}.write();

		return downloadUrl;
	}

	@Override
	public String getDeleteUrl(final String fdPath) throws Exception {

		final String deleteUrl;

		try {
			deleteUrl = directService.getDeleteUrl(fdPath);
		} catch (Exception e) {
			logger.error("获取删除链接报错：", e);
			throw e;
		}

		new SysFileLocationLogWriter() {

			@Override
			public void bulidLog(SysFileLocationLog log) {

				log.setFdName("获取删除链接");
				log.setFdReq(fdPath);
				log.setFdResp(deleteUrl);
			}
		}.write();

		return deleteUrl;

	}

	@Override
	public String getUploadUrl(String userAgent) throws Exception {
		return directService.getUploadUrl(userAgent);
	}

	@Override
	public String getMethodName() {
		return directService.getMethodName();
	}

	@Override
	public SysFileSignature getSignature(
			final SysFileSignatureRequest signatureRequest) throws Exception {

		final SysFileSignature signature;

		try {
			signature = directService.getSignature(signatureRequest);
		} catch (Exception e) {
			logger.error("获取签名报错：", e);
			throw e;
		}

		new SysFileLocationLogWriter() {

			@Override
			public void bulidLog(SysFileLocationLog log) {

				log.setFdName("获取签名");
				log.setFdReq(signatureRequest.toString());
				log.setFdResp(signature.toString());
			}
		}.write();

		return signature;
	}

	@Override
	public String getFileVal() {
		return directService.getFileVal();
	}

	@Override
	public Boolean isSupportDirect(String userAgent) {
		return directService.isSupportDirect(userAgent);
	}

	@Override
	public Boolean isSupportServiceDirect() {
		return directService.isSupportServiceDirect();
	}

}
