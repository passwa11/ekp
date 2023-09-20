<%-- {"login_logo_position":"logoPositionOnLogoHide","single_random_logo":"single_random_logo.png","loginBtn_bgColor":"#2a88f6","loginBtn_bgColor_hover":"#2460cc","loginBtn_font_color":"#ffffff","login_form_align":"loginFormOnCenter","title_image":"favicon.ico","single_random_footerInfo_CN":"Copyright © 2001-2019 蓝凌软件 版权所有","loginBtn_text_CN":"登录","title_CN":"登录系统","single_random_head_links":[]} --%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	JSONObject loginConfig = new JSONObject();
	loginConfig.put("login_logo_position", "logoPositionOnLogoShow");
	loginConfig.put("single_random_logo", "single_random_logo.png");
	loginConfig.put("loginBtn_bgColor", "#2a88f6");
	loginConfig.put("loginBtn_bgColor_hover", "#2460cc");
	loginConfig.put("loginBtn_font_color", "#ffffff");
	loginConfig.put("login_form_align", "loginFormOnCenter");
	loginConfig.put("title_image", "favicon.ico");
	loginConfig.put("single_random_footerInfo_CN", "Copyright © 2001-2019 蓝凌软件 版权所有");
	loginConfig.put("loginBtn_text_CN", "登录");
	loginConfig.put("title_CN", "登录系统");
	loginConfig.put("login_form_title_CN", "登录系统");
	loginConfig.put("login_title_show", "loginTitleShow");
	JSONArray links = new JSONArray();
	loginConfig.put("single_random_head_links", links);
	request.setAttribute("config", loginConfig);
	request.getSession().setAttribute("title_image", loginConfig.get("title_image"));
%>