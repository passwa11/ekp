<%@ page language="java" pageEncoding="UTF-8" import="com.landray.kmss.sys.zone.forms.SysZonePersonInfoForm"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit">
	<template:replace name="title">
		 ${lfn:message('sys-zone:sysZonePerson.modifyPhoto') } - ${name}
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<ui:button text="${ lfn:message('button.close') }" onclick="closeWin();"></ui:button>
		</ui:toolbar>
	</template:replace>
<template:replace name="content">
<div style="margin-top: 30px;"> 
<html:form action="/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do" styleId="sysZonePersonInfoForm">
  <ui:panel layout="sys.ui.panel.light" scroll="false" toggle="false">
	 <!-- 参数fdEditMode: add(新建文档时) edit(编辑编辑文档时) view(非新建编辑文档时进行上传裁剪)-->
	 <ui:content title="${lfn:message('sys-zone:sysZonePersonImg.upload')}">
 		<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit_crop.jsp" charEncoding="UTF-8">
          <c:param name="fdKey" value="personPic"/>
          <c:param name="fdModelName" value="com.landray.kmss.sys.zone.model.SysZonePersonInfo"/>
          <c:param name="fdModelId" value="${fdId}"/>
          <c:param name="fdEditMode" value="view"/>
        </c:import>
	</ui:content>
 </ui:panel>
</html:form>
</div>
<script>
	function closeWin(){
		try {
			var r = confirm('<bean:message key="crop.page.closeWindow" bundle="sys-zone"/>');
			if(r){
				top.opener = top;
				top.open("", "_self");
				top.close();
			}
		} catch (e) {
		}
	}
</script> 
</template:replace>
</template:include>