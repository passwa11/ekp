<%-- {"single_logo":"single_logo.png","login_logo_position":"logoPositionOnLogoHide","single_login_bg":"img_login.jpg","loginBtn_bgColor":"#4285f4","loginBtn_bgColor_hover":"#2577E2","loginBtn_font_color":"#ffffff","login_form_align":"loginFormOnCenter","login_form_background_color":"whiteBackground","title_image":"favicon.ico","single_footerInfo_CN":"蓝凌软件 版权所有","single_logo_text_CN":"","loginBtn_text_CN":"登录","login_form_title_CN":"欢迎登录","title_CN":"登录系统"} --%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	JSONObject loginConfig = new JSONObject();
	loginConfig.put("single_logo", "single_logo.png");
	loginConfig.put("login_logo_position", "logoPositionOnForm");
	loginConfig.put("single_login_bg", "img_login.jpg");
	loginConfig.put("loginBtn_bgColor", "#4285f4");
	loginConfig.put("loginBtn_bgColor_hover", "#2577E2");
	loginConfig.put("loginBtn_font_color", "#ffffff");
	loginConfig.put("login_form_align", "loginFormOnCenter");
	loginConfig.put("login_form_background_color", "whiteBackground");
	loginConfig.put("title_image", "favicon.ico");
	loginConfig.put("single_footerInfo_CN", "蓝凌软件 版权所有");
	loginConfig.put("single_logo_text_CN", "");
	loginConfig.put("loginBtn_text_CN", "登录");
	loginConfig.put("login_form_title_CN", "欢迎登录");
	loginConfig.put("title_CN", "登录系统");
	loginConfig.put("login_title_show", "loginTitleShow");
    request.setAttribute("config", loginConfig);
    request.getSession().setAttribute("title_image", loginConfig.get("title_image"));
%>