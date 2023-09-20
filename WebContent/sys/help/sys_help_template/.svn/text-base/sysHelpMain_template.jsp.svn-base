<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include
		pathFixed='yes' 
		ref="slide.view"
		leftWidth="282"
		leftShow ="yes"
		leftBar="yes"
		showPraise="yes">
    <template:replace name="head">
		<link rel="stylesheet" href="${LUI_ContextPath}/sys/help/sys_help_template/css/template.css" type="text/css" />
		<link rel="stylesheet" href="${LUI_ContextPath}/sys/help/sys_help_template/css/style.min.css" type="text/css" />
        <script src="${LUI_ContextPath}/sys/help/sys_help_template/js/jstree.min.js"></script>
    </template:replace>
    <template:replace name="title">
        <c:out value="${docSubject}" />
    </template:replace>
    <template:replace name="toolbar">
    	<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="1">
			<ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
    	</ui:toolbar>
    </template:replace>
    <template:replace name="barLeft">
		<div class="treeContent" id="treeContent">
			<div id="tree"></div>
		</div>
    </template:replace>
    
    <template:replace name="content">
		<div id="docContent">
			${docContent}
		</div>
		
		<div id="showFloat" class="showFloat" onclick="controlIndex()"></div>
		<div class="floatBox" id="floatBox">
			<i class="btn_close" onclick="controlIndex()"></i>
			<div class="title">
				【${lfn:message('sys-help:sysHelpTemplate.index')}】
			</div>
			<div class="content" id="indexContent">
			</div>
		</div>
		
		<%@ include file="/sys/help/sys_help_template/sysHelpMain_template_js.jsp"%>
    </template:replace>
</template:include>



