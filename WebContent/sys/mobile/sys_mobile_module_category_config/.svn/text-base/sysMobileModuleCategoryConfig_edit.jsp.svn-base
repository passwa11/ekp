<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.edit">
	<template:replace name="title">
		<bean:message bundle="sys-mobile" key="sysMobile.pda.module.setting"/>
	</template:replace>
	<template:replace name="content">
<html:form action="/sys/mobile/sys_mobile_module_category_config/sysMobileModuleCategoryConfig.do" method="POST">
	<html:hidden property="fdId" />
	<html:hidden property="method_GET" />
	<html:hidden property="fdModelName" value="${HtmlParam.fdModelName }" />
	<script>
	Com_IncludeFile("dialog.js|doclist.js");
	</script>
	<script>
	DocList_Info.push("sys_mobile_module_category_config");
	</script>
	<table class="tb_normal" width="98%" style="margin-top: 50px;" id="sys_mobile_module_category_config">
		<tr class="tr_normal_title">
			<td width="35px;"><bean:message bundle="sys-mobile" key="sysMobileModuleCategoryConfig.serial"/></td>
			<td width="300px;"><bean:message bundle="sys-mobile" key="sysMobileModuleCategoryConfig.fdName"/></td>
			<td>URL</td>
			<td style="width:120px;"><bean:message bundle="sys-mobile" key="sysMobileModuleCategoryConfig.count"/></td>
			<td width="120px;"><a href="#" onclick="DocList_AddRow();return false;"><bean:message bundle="sys-mobile" key="sysMobileModuleCategoryConfig.button.add"/></a></td>
		</tr>
		<tr KMSS_IsReferRow="1" style="display:none">
			<td KMSS_IsRowIndex="1">!{index}</td>
			<td>
				<xform:text property="fdCateDetails[!{index}].fdName" required="true" style="width:70%"></xform:text>
			</td>
			<td>
				<xform:text property="fdCateDetails[!{index}].fdUrl" required="true" style="width:95%"></xform:text>
			</td>
			<td>
				<xform:checkbox property="fdCateDetails[!{index}].fdIsNavCount" dataType="Boolean" showStatus="edit">
					<xform:simpleDataSource value="true"><bean:message bundle="sys-mobile" key="sysMobileModuleCategoryConfig.tab.number"/></xform:simpleDataSource>
				</xform:checkbox>
			</td>
			<td>
				<a href="#" onclick="DocList_DeleteRow();"><bean:message bundle="sys-mobile" key="sysMobileModuleCategoryConfig.button.delete"/></a>
				<a href="#" onclick="DocList_MoveRow(-1);"><bean:message bundle="sys-mobile" key="sysMobileModuleCategoryConfig.button.moveup"/></a>
				<a href="#" onclick="DocList_MoveRow(1);"><bean:message bundle="sys-mobile" key="sysMobileModuleCategoryConfig.button.movedown"/></a>
			</td>
		</tr>
		<c:forEach items="${sysMobileModuleCategoryConfigForm.fdCateDetails }" var="detail" varStatus="vstatus">
		<tr KMSS_IsContentRow="1">
			<td>${vstatus.index+1}</td>
			<td>
				<xform:text property="fdCateDetails[${vstatus.index}].fdName" required="true" style="width:70%"></xform:text>
			</td>
			<td>
				<xform:text property="fdCateDetails[${vstatus.index}].fdUrl" required="true" style="width:95%"></xform:text>
			</td>
			<td>
				<xform:checkbox property="fdCateDetails[${vstatus.index}].fdIsNavCount" dataType="Boolean" showStatus="eidt">
					<xform:simpleDataSource value="true"><bean:message bundle="sys-mobile" key="sysMobileModuleCategoryConfig.tab.number"/></xform:simpleDataSource>
				</xform:checkbox>
			</td>
			<td>
				<a href="#" onclick="DocList_DeleteRow();"><bean:message bundle="sys-mobile" key="sysMobileModuleCategoryConfig.button.delete"/></a>
				<a href="#" onclick="DocList_MoveRow(-1);"><bean:message bundle="sys-mobile" key="sysMobileModuleCategoryConfig.button.moveup"/></a>
				<a href="#" onclick="DocList_MoveRow(1);"><bean:message bundle="sys-mobile" key="sysMobileModuleCategoryConfig.button.movedown"/></a>
			</td>
		</tr>
		</c:forEach>
	</table>
	<div style="margin-bottom: 10px;margin-top:25px;text-align: center">
	   <ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.sysMobileModuleCategoryConfigForm, 'update');" order="2" ></ui:button>
	</div>
	<div style="text-align: center;margin: 5px auto 0 auto;">
	<img width="250" src="<c:url value='/sys/mobile/resource/images/module_cate_cfg.png'/>">
	</div>
</html:form>
	<script>
			$KMSSValidation(document.forms['sysMobileModuleCategoryConfigForm']);
	</script>
		
	</template:replace>
</template:include>
