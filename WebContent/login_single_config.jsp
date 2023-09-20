<%-- {"login_logo_position":"logoPositionOnLogoHide","single_logo":"single_logo.png","single_login_bg":"img_login.jpg","loginBtn_font_color":"#fff","login_form_align":"loginFormOnRight","title_image":"favicon.ico","single_footerInfo_CN":"蓝凌软件 版权所有","single_logo_text_CN":"知识管理与协同领导品牌","loginBtn_text_CN":"登录","title_CN":"登录系统"} --%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	JSONObject loginConfig = new JSONObject();
	loginConfig.put("login_logo_position", "logoPositionOnLogoShow");
	loginConfig.put("single_logo", "single_logo.png");
	loginConfig.put("single_login_bg", "img_login.jpg");
	loginConfig.put("loginBtn_font_color", "#fff");
	loginConfig.put("login_form_align", "loginFormOnRight");
	loginConfig.put("title_image", "favicon.ico");
	loginConfig.put("single_footerInfo_CN", "蓝凌软件 版权所有");
	loginConfig.put("single_logo_text_CN", "知识管理与协同领导品牌");
	loginConfig.put("loginBtn_text_CN", "登录");
	loginConfig.put("title_CN", "登录系统");
	loginConfig.put("login_form_title_CN", "登录系统");
	loginConfig.put("login_title_show", "loginTitleShow");
	loginConfig.put("single_im_icon", "icon_KK.png");
	loginConfig.put("single_kms_icon", "icon_KMS.png");
	loginConfig.put("single_ekp_icon", "icon_EKP.png");
	loginConfig.put("single_im_CN", "企业移动社交平台");
	loginConfig.put("single_kms_CN", "KMS知识管理平台");
	loginConfig.put("single_ekp_CN", "EKP协同办公平台");
	
    request.setAttribute("config", loginConfig);
    request.getSession().setAttribute("title_image", loginConfig.get("title_image"));
%>