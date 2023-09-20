<%@ page language="java" pageEncoding="UTF-8" import="com.landray.kmss.sys.zone.forms.SysZonePersonInfoForm"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="person.cfg">
	<template:replace name="title">
		<template:super/> - ${lfn:message('sys-zone:sysZonePersonInfo') }
	</template:replace>
	   	<template:replace name="head">
  	<script>
   			seajs.use(['theme!module']);
   	</script>
   	</template:replace>
<template:replace name="content">
<div class="lui_personInfo_photo">
<html:form action="/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do" styleId="sysZonePersonInfoForm">
<ui:tabpanel  layout="sys.ui.tabpanel.list">
	 <!-- 参数fdEditMode: add(新建文档时) edit(编辑编辑文档时) view(非新建编辑文档时进行上传裁剪)-->
	 <ui:content title="${lfn:message('sys-zone:sysZonePersonImg.upload')}">
 		<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit_crop.jsp" charEncoding="UTF-8">
          <c:param name="fdKey" value="personPic"/>
          <c:param name="fdModelName" value="com.landray.kmss.sys.zone.model.SysZonePersonInfo"/>
          <c:param name="fdModelId" value="${fdId}"/>
          <c:param name="fdEditMode" value="view"/>
          <c:param name="totalWidth" value="98%"/>
        </c:import>
	</ui:content>
</ui:tabpanel>
</html:form> 
</div>
</template:replace>
</template:include>