<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="com.alibaba.fastjson.JSONArray"%>
<%@ page import="com.alibaba.fastjson.JSONObject"%>
<%@ page import="com.landray.kmss.sys.anonym.context.AnonymContext"%>
<%@ page import="com.landray.kmss.sys.anonym.constants.SysAnonymConstant"%>
<%@ page import="com.landray.kmss.common.model.IBaseModel"%>
<%@ page import="com.landray.kmss.sys.anonym.model.SysAnonymCommon"%>
<%@ page import="com.landray.kmss.sys.ui.plugin.SysUiPluginUtil"%>
<%@ page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@ page import="com.landray.kmss.sys.ui.xml.model.*"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="image/jpeg; charset=UTF-8" pageEncoding="UTF-8"%>

<c:import var="data" url="http://192.168.4.167:8080/ekp_v15/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=16f3c4f1c75f2fb6e61b46d48048ee9d&open=1"/>

<c:out value="${data}"/>


