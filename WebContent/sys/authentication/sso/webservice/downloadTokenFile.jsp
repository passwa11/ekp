<%@page import="java.io.* "%>
<%@page import="com.landray.kmss.sys.authentication.sso.GetTokenUserName"%>
<%@ page import="com.landray.kmss.sys.config.action.SysConfigAdminUtil" %>
<%@ page import="org.apache.commons.io.IOUtils" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%
	InputStream oriFileStream = null;
	InputStream decryedFileStream = null;
	try {

		String tokenPath = GetTokenUserName.getTokenConfigPath();
		//System.out.println();
		tokenPath = tokenPath.replace("lib", "KmssConfig");
		File downFile = new File(tokenPath);
		oriFileStream = new FileInputStream(downFile);
		try {
			decryedFileStream = SysConfigAdminUtil.doPropertiesDecrypt(oriFileStream);
		}catch (Exception e1){
			e1.printStackTrace();
			decryedFileStream = new FileInputStream(downFile);
		}
		//byte[] temp = org.apache.commons.io.FileUtils.readFileToByteArray(downFile);
		byte[] temp = 	IOUtils.toByteArray(decryedFileStream);
		response.reset();
		response.addHeader("Content-Disposition",
				"attachment;filename=LRToken");
		response.addHeader("Content-Length", "" + temp.length);
		response.setContentType("application/octet-stream");
		OutputStream os = response.getOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(os);
		bos.write(temp);
		bos.flush();
		bos.close();
		os.close();

		response.flushBuffer();
	} catch (Exception ex) {
		out.println("应用系统SSO配置downloadManual时发生错误,"+ ex.getMessage());
	} finally {
		if(oriFileStream!=null){
			oriFileStream.close();
		}
		if(decryedFileStream!=null){
			decryedFileStream.close();
		}
	}

%>