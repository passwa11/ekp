
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%><%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.util.FileMimeTypeUtil"%>
<%
	JSONObject json = new JSONObject();
	try {
		long expires = 7 * 24 * 60 * 60;
		long lastModified = (System.currentTimeMillis() / 1000l) * 1000l;//去除毫秒
		response.addDateHeader("Last-Modified", lastModified);
		response
				.addDateHeader("Expires", lastModified + expires * 1000);
		response.addHeader("Cache-Control", "max-age=" + expires);

		String fileNames = request.getParameter("extFileNames");
		String[] extFileNames = fileNames.split(",");
		if (extFileNames != null && extFileNames.length > 0) {
			List<String> rtnType = new ArrayList<String>(); 
			for (int i = 0; i < extFileNames.length; i++) {
				String fileName = extFileNames[i];
				if (StringUtil.isNotNull(fileName)) {
					if (fileName.indexOf(".") == -1) {
						fileName = "." + fileName;
					}
					String typeStr = FileMimeTypeUtil.getContentType(fileName);
					if(!rtnType.contains(typeStr) && StringUtil.isNotNull(typeStr)){
						rtnType.add(typeStr);
					} 
				}
			}
			if (!rtnType.isEmpty()) {
				json.put("status", 1);
				json.put("message", rtnType);
			} else {
				json.put("status", 0);
				json.put("message", "无有效的文件名");
			}
		} else {
			json.put("status", 0);
			json.put("message", "文件名为空");
		}
	} catch (Exception e) {
		json.put("status", 0);
		json.put("message", "解析报错");
	}
	out.print(json.toString());
%>