package com.landray.kmss.km.signature.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class RequsetUtil {

	public String getRequsetString(HttpServletRequest request, String name)
			throws FileUploadException, UnsupportedEncodingException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List items = upload.parseRequest(request);
		Map param = new HashMap();
		for (Object object : items) {
			FileItem fileItem = (FileItem) object;
			if (fileItem.isFormField()) {
				param.put(fileItem.getFieldName(), fileItem.getString("gb2312"));// 如果你页面编码是utf-8的
			}
		}
		return (String) param.get(name);
	}

	/*
	 * public byte[] getRequsetByte(HttpServletRequest request, String name)
	 * throws FileUploadException, UnsupportedEncodingException {
	 * DiskFileItemFactory factory = new DiskFileItemFactory();
	 * ServletFileUpload upload = new ServletFileUpload(factory); List items =
	 * upload.parseRequest(request); Map param = new HashMap(); for (Object
	 * object : items) { FileItem fileItem = (FileItem) object; if
	 * (fileItem.isFormField()) { param.put(fileItem.getFieldName(),
	 * fileItem.getString("gb2312"));// 如果你页面编码是utf-8的 } } return (String)
	 * param.get(name); }
	 */

}
