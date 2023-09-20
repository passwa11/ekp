<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ page import="com.landray.kmss.hr.organization.model.HrOrganizationSyncSetting"%>
<%
	HrOrganizationSyncSetting syncSetting = new HrOrganizationSyncSetting();
	request.setAttribute("hrToEkpEnable", syncSetting.getHrToEkpEnable());
	
	String url = request.getParameter("parent");
	url = "/sys/organization/sys_org_org/sysOrgOrg.do?"+(url==null?"":"parent="+url+"&");
	pageContext.setAttribute("actionUrl", url);
%>
<template:include ref="default.simple" spa="true">
	<template:replace name="head">
		<link rel="stylesheet" href="${LUI_ContextPath}/hr/organization/resource/css/index.css" />
		<link rel="stylesheet" href="${LUI_ContextPath}/hr/organization/resource/css/tree.css" />
		<link rel="stylesheet" href="${LUI_ContextPath}/hr/organization/resource/css/tabpage.css" />
		<style>
			.lui-component[data-lui-type='lui/panel!Content']{
				padding:0!important;
			}
			.lui_tabpanel_list_navs_c{
				background-color:unset!important;
			}
			body .lui_tabpanel_list_navs_item_selected{
				border:none!important;
			}
		</style>	
		<script type="text/javascript">
			seajs.use(['theme!form']);
			Com_Parameter.CloseInfo="<bean:message key="message.closeWindow"/>";
			Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js|data.js", null, "js");
		</script>
		<script src="${LUI_ContextPath}/hr/organization/resource/js/tree/ie.js"></script>
		<script src="${LUI_ContextPath}/hr/organization/resource/js/tree/tabPage.js"></script>
		<script type="text/javascript" src="${LUI_ContextPath}/hr/organization/resource/js/orgChart.js?s_cache=${LUI_Cache}"></script>	
	</template:replace>
    <template:replace name="body">
    	<ui:tabpanel  layout="sys.ui.tabpanel.list" id="tabpanel">
	    	<ui:content title="${lfn:message('hr-organization:py.ZuZhiJiaGouWei') }">
    	<input type="hidden" id="ishrToEkpEnable" value="${hrToEkpEnable }"/>
		<div id="staffLevel_content">
			<div id="staffLevel_silde">
				<div id="hr-organization-search">
					<input type="text" placeholder="<bean:message key="hrOrganizationElement.please.input.fdName" bundle="hr-organization"/>" name="" value=""/>
					<span class='hrorgsearch'></span>
				</div>
				<div id="hr-organization-cont"></div>
			</div>
			<div id="staffLevel_body">
<%-- 				<ui:tabpanel>
					<ui:layout ref="sys.ui.tabpanel.simple"></ui:layout>
						<ui:content title="组织信息">
							<iframe height="490" class="lui_widget_iframe hr_org_info_iframe" frameborder="no" border="0" id="org-info" src="${LUI_ContextPath }/hr/organization/hr_organization_tree/orgInfo/orgInfo.jsp"></iframe>
						</ui:content>
						<ui:content title="下级组织">
							<iframe height="490" class="lui_widget_iframe hr_org_info_iframe" frameborder="no" border="0" id="org-subordinate-staff" src="${LUI_ContextPath }/hr/organization/hr_organization_tree/subordinateStaff/list.jsp?fdParentId="></iframe>
						</ui:content>
						<kmss:authShow roles="ROLE_HRSTAFF_DEFAULT">
							<ui:content title="员工">
								<iframe height="910" class="lui_widget_iframe hr_org_info_iframe" frameborder="no" border="0" id="org-person" src="${LUI_ContextPath }/hr/organization/hr_organization_tree/person/list.jsp?fdParentId="></iframe>
							</ui:content>
						</kmss:authShow>
				</ui:tabpanel> --%>
				<div id="tablePage"></div>
				<kmss:auth requestURL="/hr/organization/hr_organization_element/hrOrganizationElement.do?method=add">
					<input type="hidden" id="autoAddOrg" value="true"/>
				</kmss:auth>
			</div>
		</div>
		<div id="namex" style="display: none;"></div>
		<script src="${LUI_ContextPath}/hr/organization/resource/js/tree/index.js"></script>
		</ui:content>
		</ui:tabpanel>
    </template:replace>
</template:include>