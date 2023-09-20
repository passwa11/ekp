<%@ page import="com.landray.kmss.sys.mobile.util.MobileUtil" %>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    int type = MobileUtil.getClientType(request);
    String url = request.getParameter("url");
    if(StringUtil.isNull(url))
        return;
    if(MobileUtil.THIRD_WEIXIN==type || MobileUtil.DING_ANDRIOD ==type ){
        //微信端和钉钉安卓端无法下载，需要跳出外部浏览器下载，而在其端内先返回一个文件流，使得第一次伪下载
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename="+java.net.URLEncoder.encode("download", "UTF-8"));
        return;
    }else{
        response.sendRedirect(StringUtil.formatUrl(url));
    }
%>