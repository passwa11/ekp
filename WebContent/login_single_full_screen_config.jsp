<%-- {"login_logo_position":"logoPositionOnLogoHide","single_full_screen_logo":"single_full_screen_logo.png","login_form_align":"loginFormOnRight","login_form_background_color":"blackBackground","title_image":"favicon.ico","single_full_screen_footerInfo_CN":"Copyright © 2001-2019 蓝凌软件 版权所有","loginBtn_text_CN":"登录","title_CN":"登录系统"} --%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	JSONObject loginConfig = new JSONObject();
	loginConfig.put("login_logo_position", "logoPositionOnLogoShow");
	loginConfig.put("single_full_screen_logo", "single_full_screen_logo.png");
	loginConfig.put("login_form_align", "loginFormOnRight");
	loginConfig.put("login_form_background_color", "blackBackground");
	loginConfig.put("title_image", "favicon.ico");
	loginConfig.put("single_full_screen_footerInfo_CN", "Copyright © 2001-2019 蓝凌软件 版权所有");
	loginConfig.put("loginBtn_text_CN", "登录");
	loginConfig.put("title_CN", "登录系统");
    request.setAttribute("config", loginConfig);
    request.getSession().setAttribute("title_image", loginConfig.get("title_image"));
%>