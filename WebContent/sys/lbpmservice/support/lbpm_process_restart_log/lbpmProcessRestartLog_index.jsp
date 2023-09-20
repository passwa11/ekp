<%@page import="com.landray.kmss.framework.hibernate.extend.SqlPartitionConfig"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.simple">
	<template:replace name="body">
	<list:listview id="lbpmProcessRestartLogTable">
		<ui:source type="AjaxJson">
				{url:'/sys/lbpmservice/support/LbpmProcessRestartLogAction.do?method=listPage&processId=${JsParam.fdModelId}&docStatus=${JsParam.docStatus}'}
		</ui:source>
		<list:colTable isDefault="true" layout="sys.ui.listview.columntable">
			<list:col-auto props=""></list:col-auto> 
		</list:colTable>
		<list:paging></list:paging>
	</list:listview>
	<script type="text/javascript">
	function initialPage(){
		try {
			var arguObj = document.getElementById("lbpmProcessRestartLogTable");
			if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
				var height = arguObj.offsetHeight + 0;
				if(height>0)
					window.frameElement.style.height = height + "px";
			}
			setTimeout(initialPage, 200);
		} catch(e) {
		}
	}
	Com_AddEventListener(window, "load", initialPage);
	</script>
	</template:replace>
</template:include>