<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.hr.organization.model.HrOrganizationSyncSetting"%>
<%@ page import="com.landray.kmss.hr.organization.constant.HrOrgConstant"%>
<%
	HrOrganizationSyncSetting syncSetting = new HrOrganizationSyncSetting();
	request.setAttribute("hrToEkpEnable", syncSetting.getHrToEkpEnable());
	
	String url = request.getParameter("parent");
	url = "/sys/organization/sys_org_org/sysOrgOrg.do?"+(url==null?"":"parent="+url+"&");
	pageContext.setAttribute("actionUrl", url);
%>

<template:include ref="config.profile.list">
	<template:replace name="title">${ lfn:message('sys-organization:sysOrgElement.org') }</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" href="${LUI_ContextPath}/hr/organization/resource/css/staffingLevel.css" />
		<link rel="stylesheet" href="${LUI_ContextPath}/hr/organization/resource/css/comonReset.css" />
	</template:replace>
	<template:replace name="content">
		<list:criteria id="criteria1">
			<list:cri-ref key="fdName" ref="criterion.sys.docSubject" 
			title='${lfn:message("hr-organization:hrOrganizationElement.fdName")}' style="width: 200px;"></list:cri-ref>
			<list:cri-criterion title="" key="fdOrgType">
				<list:box-select>
					<list:item-select>
							<ui:source type="Static">
								[
									{text:'${ lfn:message('hr-organization:enums.org_type.1') }', value:1},
									{text:'${ lfn:message('hr-organization:enums.org_type.2') }', value:2},
								]
						</ui:source>
					</list:item-select>
				</list:box-select> 
			</list:cri-criterion>
		</list:criteria>
		<div style="height:16px;background-color:#f8f8f8;"></div>
		<div class="hr_list_box">
			<div class="lui_list_operation">
				<div class="lui_list_operation_order_btn">
					<list:selectall></list:selectall>
				</div>		
				<div class="hr_list_operation_box">
					<div style="display: inline-block;vertical-align: middle;">
						<ui:toolbar id="Btntoolbar" count="5">
							<kmss:auth requestURL="/hr/organization/hr_organization_element/hrOrganizationElement.do?method=add">
								<c:if test="${hrToEkpEnable }">
									<c:if test="${param.available != '0'}">
										<ui:button text="${lfn:message('hr-organization:hr.organization.info.stopOrg.batch')}" onclick="invalidated(true)" order="1" ></ui:button>
									</c:if>
									<!-- 快速排序 -->
									<c:import url="/sys/profile/common/change_order_num.jsp" charEncoding="UTF-8">
										<c:param name="modelName" value="com.landray.kmss.hr.organization.model.HrOrganizationElement"></c:param>
										<c:param name="property" value="fdOrder"></c:param>
										<c:param name="column" value="3"></c:param>
									</c:import>
								</c:if>
								<ui:button text="${lfn:message('button.export') }" onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.hr.organization.model.HrOrganizationElement')" order="4"></ui:button>
							</kmss:auth>
						</ui:toolbar>
					</div>
				</div>
			</div>
			<ui:fixed elem=".lui_list_operation"></ui:fixed>
			<!-- 内容列表 -->
			<list:listview id="subordinate_list_view">
				<ui:source type="AjaxJson">
					{url:'/hr/organization/hr_organization_element/hrOrganizationElement.do?method=elementList&available=${JsParam.available}&all=${JsParam.all}&fdParentId=${param.fdParentId }&parent=${JsParam.parent}&suborType=12'}
				</ui:source>
				<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
					rowHref="">
					<list:col-checkbox></list:col-checkbox>
					<list:col-auto props="fdName,fdOrder,fdOrgType,fdNum,fdCompilationNum,fdCreateTime,operations"></list:col-auto>
				</list:colTable>
			</list:listview>
			<br>
		 	<list:paging/>
	 	</div>
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish("list.refresh");
				});
		 	});
		 	 var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'grade',
                modelName: 'com.landray.kmss.hr.organization.model.HrOrganizationElement',
                templateName: '',
                basePath: '/hr/organization/hr_organization_element/hrOrganizationElement.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("hr-organization:treeModel.alert.templateAlert")}',
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
	 	</script>
		<script type="text/javascript" src="${LUI_ContextPath}/hr/organization/resource/js/tree/orgInfo.js?s_cache=${LUI_Cache}"></script>
		<script type="text/javascript" src="${LUI_ContextPath}/hr/organization/resource/js/list.js?s_cache=${LUI_Cache}"></script>	 	
	</template:replace>
</template:include>
