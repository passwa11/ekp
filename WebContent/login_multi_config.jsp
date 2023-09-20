<%-- {"login_logo_position":"logoPositionOnLogoHide","multi_logo":"multi_logo.png","multi_section1_bg":"login_bg_01.jpg","multi_section2_bg":"login_bg_02.jpg","multi_section3_bg":"login_bg_03.jpg","loginBtn_bgColor":"#4285f4","loginBtn_bgColor_hover":"#346ac3","loginBtn_font_color":"#ffffff","login_form_align":"loginFormOnRight","title_image":"favicon.ico","login_form_title_CN":"登录系统","loginBtn_text_CN":"登录","title_CN":"登录系统"} --%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	JSONObject loginConfig = new JSONObject();
	loginConfig.put("login_logo_position", "logoPositionOnLogoShow");
	loginConfig.put("multi_logo", "multi_logo.png");
	loginConfig.put("multi_section1_bg", "login_bg_01.jpg");
	loginConfig.put("multi_section2_bg", "login_bg_02.jpg");
	loginConfig.put("multi_section3_bg", "login_bg_03.jpg");
	loginConfig.put("loginBtn_bgColor", "#4285f4");
	loginConfig.put("loginBtn_bgColor_hover", "#346ac3");
	loginConfig.put("loginBtn_font_color", "#ffffff");
	loginConfig.put("login_form_align", "loginFormOnRight");
	loginConfig.put("title_image", "favicon.ico");
	loginConfig.put("login_form_title_CN", "登录系统");
	loginConfig.put("loginBtn_text_CN", "登录");
	loginConfig.put("title_CN", "登录系统");
	loginConfig.put("login_title_show", "loginTitleShow");
    request.setAttribute("config", loginConfig);
    request.getSession().setAttribute("title_image", loginConfig.get("title_image"));
%>