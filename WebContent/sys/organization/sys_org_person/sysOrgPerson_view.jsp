<%@ page import="com.landray.kmss.sys.organization.util.SysOrgEcoUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="com.landray.kmss.sys.organization.forms.SysOrgPersonForm"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<script>
Com_IncludeFile("data.js");
function confirm_invalidated(){
	var msg = confirm("<bean:message bundle="sys-organization" key="organization.invalidated.comfirm"/>");
	return msg;
}
<c:if test="${'true' eq unlock && 'true' eq sysOrgPersonForm.fdIsAvailable}">
function confirm_unLock(){
	var msg = confirm("<bean:message bundle="sys-organization" key="organization.unLock.comfirm"/>");
	return msg;
}
</c:if>
function confirm_resume(fdId){
	var data = new KMSSData();
	data.AddBeanData('orgPreDeptPostList&fdId='+fdId);
	var datas = data.Parse();
	var val = datas.GetHashMapArray();
	var deptName = val[0]["deptName"];
	var postNames = val[0]["postNames"];
	if (typeof(deptName) == "undefined" || deptName == "undefined")
		deptName = "";
	if (typeof(postNames) == "undefined" || postNames == "undefined")
		postNames = "";
	var msg = confirm("<bean:message bundle='sys-organization' key='sysOrgPerson.resumption.confirm' arg0='${sysOrgPersonForm.fdName}' arg1='"+ deptName +"' arg2='"+ postNames +"'/>");
	return msg;
}
</script>
<div id="optBarDiv">
<% if(!com.landray.kmss.sys.organization.util.SysOrgUtil.isAnonymousOrEveryOne((com.landray.kmss.sys.organization.forms.SysOrgPersonForm)request.getAttribute("sysOrgPersonForm"))) { %>
	<kmss:auth
		requestURL="/sys/organization/sys_org_person/chgPersonInfo.do?method=chgPwd&fdId=${sysOrgPersonForm.fdId}"
		requestMethod="GET">
		<c:if test="${'true' eq unlock && 'true' eq sysOrgPersonForm.fdIsAvailable}">
		<input type="button" value="<bean:message bundle="sys-organization" key="sysOrgPerson.unLock" />"
			onClick="if(!confirm_unLock())return;Com_OpenWindow('sysOrgPerson.do?method=savePersonUnLock&fdId=<bean:write name="sysOrgPersonForm" property="fdId" />','_self');">
		</c:if>
		<c:if test="${'true' eq sysOrgPersonForm.fdIsAvailable}">
		<input type="button"
			value="<bean:message bundle="sys-organization" key="sysOrgPerson.button.changePassword"/>"
			onClick="Com_OpenWindow('chgPersonInfo.do?method=chgPwd&fdId=<bean:write name="sysOrgPersonForm" property="fdId" />','_self');">
		</c:if>
	</kmss:auth>
	<kmss:auth
	requestURL="/sys/organization/sys_org_person/sysOrgPerson.do?method=resume&fdId=${sysOrgPersonForm.fdId}"
	requestMethod="GET">
	<c:if test="${'false' eq sysOrgPersonForm.fdIsAvailable}">
	<input type="button" value="<bean:message bundle="sys-organization" key="sysOrgPerson.button.resumption" />"
		onClick="if(!confirm_resume('<bean:write name="sysOrgPersonForm" property="fdId" />'))return;Com_OpenWindow('sysOrgPerson.do?method=resume&fdId=<bean:write name="sysOrgPersonForm" property="fdId" />','_self');">
	</c:if>
	</kmss:auth>	
	<kmss:auth
	requestURL="/sys/organization/sys_org_person/sysOrgPerson.do?method=edit&fdId=${sysOrgPersonForm.fdId}"
	requestMethod="GET">
		<input type="button" value="<bean:message key="button.edit"/>" onClick="Com_OpenWindow('sysOrgPerson.do?method=edit&fdId=<bean:write name="sysOrgPersonForm" property="fdId" />','_self');">
		<% if (SysOrgEcoUtil.IS_ENABLED_ECO) { %>
		<input type="button" value="${ lfn:message('sys-organization:sys.org.operations.toOutPerson') }" onClick="transformOut('${sysOrgPersonForm.fdId}','${sysOrgPersonForm.fdName}')">
		<% } %>
	</kmss:auth>
	<c:if test="${sysOrgPersonForm.fdIsAvailable}">
	<kmss:auth
		requestURL="/sys/organization/sys_org_person/sysOrgPerson.do?method=invalidated&fdId=${sysOrgPersonForm.fdId}"
		requestMethod="GET">
		<input type="button" value="<bean:message bundle="sys-organization" key="organization.invalidated" />"
			onClick="if(!confirm_invalidated())return;Com_OpenWindow('sysOrgPerson.do?method=invalidated&fdId=<bean:write name="sysOrgPersonForm" property="fdId" />','_self');">
	</kmss:auth>
	</c:if>
<% } %>
<input type="button" value="<bean:message key="button.close"/>"
	onClick="Com_CloseWindow();"></div>
<p class="txttitle"><bean:message bundle="sys-organization"
	key="sysOrgElement.person" /></p>
<center>

<table id="Label_Tabel" width="95%">
	<tr LKS_LabelName="<bean:message bundle="sys-organization" key="sysOrgElement.baseInfo"/>">
		<c:if test="${'false' eq sysOrgPersonForm.fdIsAvailable}">
			<bean:message bundle="sys-organization" key="sysOrgPerson.leave.alert"/>
		</c:if>
		<td>
			<table class="tb_normal" width=95%>
				<% if(com.landray.kmss.sys.organization.util.SysOrgUtil.isAnonymousOrEveryOne((com.landray.kmss.sys.organization.forms.SysOrgPersonForm)request.getAttribute("sysOrgPersonForm"))) { %>
					<tr>
						<td width=15% class="td_normal_title"><bean:message
							bundle="sys-organization" key="sysOrgPerson.fdName" /></td>
						<td width=35%><bean:write name="sysOrgPersonForm"
							property="fdName" /></td>
						<td width=15% class="td_normal_title"><bean:message
							bundle="sys-organization" key="sysOrgPerson.fdLoginName" /></td>
						<td width=35%><bean:write name="sysOrgPersonForm"
							property="fdLoginName" /></td>
					</tr>
					<tr>
						<td width=15% class="td_normal_title"><bean:message
							bundle="sys-organization" key="sysOrgPerson.fdMemo" /></td>
						<td colspan=3>
						<c:choose>
							<c:when test="${sysOrgPersonForm.anonymous}">
								<bean:message bundle="sys-organization" key="org.person.anonymous.fdMemo" />
							</c:when>
							<c:otherwise>
								<bean:message bundle="sys-organization" key="org.person.everyOne.fdMemo" />
							</c:otherwise>
						</c:choose>
						</td>
					</tr>
				<% } else { %>
				<c:if test="${personImportType=='outer'}">
					<c:import url="${personExtendFormUrl}" charEncoding="UTF-8" />
				</c:if>
				<c:if test="${personImportType!='outer'}">
					<c:import
						url="/sys/organization/sys_org_person/sysOrgPerson_commonView.jsp"
						charEncoding="UTF-8" />
				</c:if>
				<% } %>
			</table>
		</td>
	</tr>
	<tr LKS_LabelName="<bean:message bundle="sys-organization" key="sysOrgElement.logInfo"/>">
		<td>
			<iframe name="IFRAME" src='<c:url value="/sys/organization/sys_log_organization/index.jsp?fdId=${sysOrgPersonForm.fdId}" />' frameBorder=0 width="100%"> 
			</iframe>
		</td>
	</tr>
</table>
<script type="text/javascript">
	Com_IncludeFile("dialog.js");
	window.openDialogTree =function(id){
		// 选择外部组织
		Dialog_Tree(false, null, null, null, "sysOrgElementExternalService&type=cate&parent=!{value}", "${ lfn:message('sys-organization:sysOrgEco.name') }", null, function(result) {
			if(result && result.data && result.data.length > 0) {
				window.doAction(id,result.data[0].id);
			}
		}, null, null, null, "${ lfn:message('sys-organization:sysOrgElementExternal.selectOrg') }");
	};
	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		window.doAction = function (id, parentId) {
			var url = '<c:url value="/sys/organization/sys_org_element_external/sysOrgElementExternalPerson.do?method=transformOut"/>';
			window.del_load = dialog.loading();
			$.ajax({
				url: url,
				type: 'POST',
				data: {'fdId': id, 'parentId': parentId},
				dataType: 'json',
				error: function (data) {
					if (window.del_load != null) {
						window.del_load.hide();
					}
					if (data.responseJSON.message && data.responseJSON.message.length > 0)
						dialog.alert(data.responseJSON.message[0].msg);
				},
				success: function (data) {
					if (window.del_load != null) {
						window.del_load.hide();
					}
					Com_OpenWindow("${LUI_ContextPath}/resource/jsp/success.jsp", "_self");
				}
			});
		};
		//转外部人员
		window.transformOut = function (id, fdName) {
			dialog.confirm(fdName + '<bean:message bundle="sys-organization" key="sys.org.operations.toOutPerson.tip"/>', function (value) {
				if (value == true) {
					window.openDialogTree(id);
				}
			});
		}
	});
</script>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>