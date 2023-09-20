<%@page import="java.util.ArrayList"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.io.File"%>
<%@page import="com.landray.kmss.sys.config.loader.ConfigLocationsUtil"%>
<%@page import="java.util.Date"%>
<%@page import="com.landray.kmss.util.DateUtil"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="java.util.Locale"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	// 2018-01-01 12:12:12
	String dateStr = request.getParameter("date");
	List<JSONObject> fileList = new ArrayList<JSONObject>();
	if(StringUtil.isNotNull(dateStr)) {
		Locale locale = ResourceUtil.getLocaleByUser();
		Calendar cal = Calendar.getInstance();
		String type = dateStr.length() <= 10 ? DateUtil.TYPE_DATE : DateUtil.TYPE_DATETIME;
		Date date = DateUtil.convertStringToDate(dateStr, type, locale);
		String path = ConfigLocationsUtil.getWebContentPath();
		File root = new File(path);
		getModifyFiles(root,date.getTime(),fileList,locale,path,cal);
	}
	request.setAttribute("fileList", fileList);
%>
<%! 
public void getModifyFiles(File file,long date,List<JSONObject> fileList,Locale locale,String basePath,Calendar cal) {
	if(file.isDirectory()) {
		File[] fs = file.listFiles();
		for(File f : fs) {
			getModifyFiles(f,date,fileList,locale,basePath,cal);
		}
	} else {
		long modify = file.lastModified();
		if(modify > date) {
			JSONObject json = new JSONObject();
			cal.setTimeInMillis(modify);
			Date fileDate = cal.getTime();
			String lastModifyStr = DateUtil.convertDateToString(fileDate, DateUtil.TYPE_DATETIME, locale);
			json.put("path", file.getAbsolutePath().substring(basePath.length()));
			json.put("lastTime", lastModifyStr);
			fileList.add(json);
		}
	}
}
%>
<!doctype html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit" />
<head>
	<title>获取更新文件列表</title>
	<style>
		.file-table {
			width:100%;
		}
	</style>
</head>
<body>
	<table class="file-table">
		<thead>
			<tr>
				<th>文件路径</th>
				<th style="width:200px;" align="center">最后修改时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="file" items="${fileList }">
				<tr>
					<td>${file.path }</td>
					<td align="center">${file.lastTime }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
