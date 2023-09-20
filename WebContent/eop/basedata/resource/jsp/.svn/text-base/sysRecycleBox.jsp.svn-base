<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include  ref="default.simple" rwd="true">
	<template:replace name="body">
	<div style="width:100%;">
	  <ui:tabpanel layout="sys.ui.tabpanel.list">
		 <ui:content title="${ lfn:message('sys-recycle:module.sys.recycle') }">
		 	 <ui:iframe id="recycle_review" src="${LUI_ContextPath }/sys/recycle/import/sysRecycle_index.jsp?modelName=${HtmlParam.modelName}"></ui:iframe>
		  </ui:content>
		</ui:tabpanel>
	  </div> 
	</template:replace>
</template:include>
