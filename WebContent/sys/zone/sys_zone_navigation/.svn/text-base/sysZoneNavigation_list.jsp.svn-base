<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<script>Com_IncludeFile("jquery.js");</script>
<script src="<c:url value="/sys/person/resource/utils.js" />"></script>
<template:include file="/sys/profile/resource/template/list.jsp">
 <template:replace name="toolbar">
 <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
   <ui:button text="${ lfn:message('sys-zone:status.started') }" onclick="updateZoneNavStatus(2);" order="1">
   </ui:button>
   <ui:button text="${ lfn:message('sys-zone:status.stoped') }" onclick="PersonOnUpdateStatus(1);" order="2">
   </ui:button>
   <ui:button text="${ lfn:message('button.add') }"  order="3" onclick="Com_OpenWindow('${LUI_ContextPath }/sys/zone/sys_zone_navigation/sysZoneNavigation.do?method=add', '_blank');" >
   </ui:button>
   <ui:button text="${ lfn:message('button.deleteall') }" onclick="PersonOnDeleteAll();" order="4">
   </ui:button>
    
	</ui:toolbar>
	</template:replace>
    <template:replace name="content">
	
    <html:form action="/sys/zone/sys_zone_navigation/sysZoneNavigation.do">
         <list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/sys/zone/sys_zone_navigation/sysZoneNavigation.do?method=list'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable"
				rowHref="/sys/zone/sys_zone_navigation/sysZoneNavigation.do?method=edit&fdId=!{fdId}" name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdName,fdStatus,fdShowType,docCreator,docCreateTime,docAlteror,docAlterTime"></list:col-auto>
			</list:colTable>
			<ui:event topic="list.loaded">
				Dropdown.init();
			</ui:event>
		</list:listview>
		<!-- 分页 -->
	 	<list:paging/>
	 	</html:form>
  </template:replace>
</template:include>
<script>

function updateZoneNavStatus(status) {
	
	if (!PersonCheckSelect()) {
		return;
	}
	
	var selects = []; 
	var showType = "";
	$("input[name='List_Selected']:checked").each(
		function(index, item) {
			var _type = $("[name='showType_" + item.value + "']")[0].value;
			showType += ";" + _type;
			selects.push({"value" : item.value , "showType" : _type})
		});
	
	if(selects.length > 2 || (selects.length == 2 && selects[0].showType == selects[1].showType)) {
		alert("同一设备类型只能有一个导航启用！");
		return;
	} else {
		$('<input type="hidden" name="changeShowType" value="' + showType +'">').appendTo(document.forms[0]);
	}
	PersonOnUpdateStatus(status);
}
</script>

<%@ include file="/resource/jsp/list_down.jsp"%>
