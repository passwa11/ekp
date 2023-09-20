<%@page import="com.landray.kmss.sys.recycle.util.SysRecycleUtil"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String admin = request.getParameter("isAdmin");
	if("true".equals(admin)){
	%>
		<%-- 显示透明遮罩层，查看原文档时，不能做任何点击操作 --%>
		<div id="__mask__" style="width:100%; position:fixed; top:0px; background-color:#000; opacity:0.01;filter: alpha(opacity=0.1); text-align:center; z-index:99999;"></div>
		
		<script type="text/javascript" src='<c:url value="/resource/js/jquery.js"/>'></script>
		<script type="text/javascript">
			// 设置全屏高度
			$("#__mask__").height($(document).height());
			function removeToolbar() {
				// 移除工具栏的按钮
				var toolbar = $(document).find("[data-lui-parentid=toolbar]");
				if(toolbar)
					toolbar.remove();
				toolbar = $(document).find(".lui_toolbar_content");
				if(toolbar)
					toolbar.remove();
			}
			LUI.ready(function(){
				removeToolbar();
				// 有些页面可能会出现JS报错而无法移除toolbar，这里设置一个定时器，0.1秒后再移动一次
				setTimeout("removeToolbar()", 100);
			});
		</script>
	<%
		return;
	}
	String formBeanName = request.getParameter("formBeanName");
	Object formBean = request.getAttribute(formBeanName);
	if(formBean == null) {
		return;
	}
	Class modelClass = (Class)PropertyUtils.getProperty(formBean, "modelClass");
	String modelName = modelClass.getName();
	boolean softDeleteEnable = SysRecycleUtil.isEnableSoftDelete(modelName);
	request.setAttribute("softDeleteEnable", softDeleteEnable);
	String fdId = (String)PropertyUtils.getProperty(formBean, "fdId");
	if(fdId == null) {
		return;
	}
	Integer docDeleteFlag = (Integer)PropertyUtils.getProperty(formBean, "docDeleteFlag");
	if(docDeleteFlag != null && docDeleteFlag == 1) {
	%>
	<script type="text/javascript">
  	 	window.location.href = "<c:url value='/sys/recycle/sys_recycle_doc/sysRecycle.do?method=view' />"+"&modelName=<%=modelName%>&modelId=<%=fdId%>";
	</script>
	<%
	}
	
%>