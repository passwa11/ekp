<%-- {"login_logo_position":"logoPositionOnLogoShow","simplicity_logo":"simplicity_logo.png","loginBtn_bgColor":"#2a88f6","loginBtn_bgColor_hover":"#2460cc","loginBtn_font_color":"#ffffff","login_form_align":"loginFormOnRight","title_image":"favicon.ico","login_title_show":"loginTitleShow","simplicity_footerInfo_CN":"Copyright © 2001-2019 蓝凌软件 版权所有","simplicity_footerInfo_US":"","login_form_title_CN":"登录系统","login_form_title_US":"","loginBtn_text_CN":"登录","loginBtn_text_US":"","title_CN":"登录系统","title_US":"","simplicity_head_links":[],"isLoginTitleActive":false} --%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ page import="com.landray.kmss.util.DateUtil" %>
<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String __year = DateUtil.convertDateToString(new Date(), "yyyy");
	JSONObject loginConfig = new JSONObject();
	loginConfig.put("login_logo_position", "logoPositionOnForm");
	loginConfig.put("login_login_height", "20");
	loginConfig.put("simplicity_logo", "simplicity_logo.png");
	loginConfig.put("loginBtn_bgColor", "#6081FB");
	loginConfig.put("loginBtn_bgColor_hover", "#5674e1");
	loginConfig.put("loginBtn_font_color", "#ffffff");
	loginConfig.put("login_form_align", "loginFormOnRight");
	loginConfig.put("title_image", "favicon.ico");
	loginConfig.put("login_title_show", "loginTitleShow");
	loginConfig.put("simplicity_footerInfo_CN", "Copyright © 2001-" + __year + " 蓝凌软件 版权所有");
	loginConfig.put("simplicity_footerInfo_US", "");
	loginConfig.put("login_form_title_CN", "登录系统");
	loginConfig.put("login_form_title_US", "");
	loginConfig.put("loginBtn_text_CN", "登录");
	loginConfig.put("loginBtn_text_US", "");
	loginConfig.put("title_CN", "登录系统");
	loginConfig.put("title_US", "");
	JSONArray links = new JSONArray();
	loginConfig.put("simplicity_head_links", links);
	loginConfig.put("isLoginTitleActive", "false");
	request.setAttribute("config", loginConfig);
	request.getSession().setAttribute("title_image", loginConfig.get("title_image"));
%>