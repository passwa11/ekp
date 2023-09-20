<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script>
	function confirm_delete(msg){
		var del = confirm("<bean:message key='page.comfirmDelete'/>");
		return del;
	}
</script>
<kmss:windowTitle moduleKey="sys-lbpmext-businessauth:table.lbpmext.businessAuth" subjectKey="sys-lbpmext-businessauth:lbpmext.businessAuth.set" subject="${lbpmExtBusinessAuthForm.fdName}" />
<div id="optBarDiv">
	<kmss:auth requestURL="/sys/lbpmext/businessauth/lbpmBusinessAuth.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
		<input type="button" value="<bean:message key="button.edit"/>"
			onClick="Com_OpenWindow('lbpmBusinessAuth.do?method=edit&fdId=<bean:write name="lbpmExtBusinessAuthForm" property="fdId" />','_self');">
	</kmss:auth>
	<kmss:auth requestURL="/sys/lbpmext/businessauth/lbpmBusinessAuth.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
		<input type="button" value="<bean:message key="button.delete"/>"
			onClick="if(!confirm_delete())return;Com_OpenWindow('lbpmBusinessAuth.do?method=delete&fdId=<bean:write name="lbpmExtBusinessAuthForm" property="fdId" />','_self');">
	</kmss:auth>
	<input type="button" value="<bean:message key="button.close"/>" onClick="Com_CloseWindow();">
</div>
<p class="txttitle"><bean:message key='table.lbpmext.businessAuth' bundle='sys-lbpmext-businessauth' /></p>
<center>
<table id="Label_Tabel" width=95%>
	<tr LKS_LabelName="条目信息">
		<td>
			<table class="tb_normal" width=100%>
				<tr>
					<td width=15% class="td_normal_title">
						<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuth.fdCategroy"/>
					</td>
					<td width=85% colspan="3">
						<bean:write name="lbpmExtBusinessAuthForm" property="fdCategoryName"/>
					</td>
				</tr>
				<tr>
					<td width=15% class="td_normal_title">
						<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuth.fdName"/>
					</td>
					<td width=85% colspan="3">
						<bean:write name="lbpmExtBusinessAuthForm" property="fdName"/>
					</td>
				</tr>
				<tr>
					<td width=15% class="td_normal_title">
						<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuth.fdNumber"/>
					</td>
					<td width=85% colspan="3">
						<bean:write name="lbpmExtBusinessAuthForm" property="fdNumber"/>
					</td>
				</tr>
				<tr>
					<td width=15% class="td_normal_title">
						<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuth.fdIsNotDelegate"/>
					</td>
					<td width=85%>
						<xform:checkbox property="fdIsNotDelegate">
						   	<xform:simpleDataSource value="true">&nbsp;</xform:simpleDataSource>
						</xform:checkbox>
					</td>
				</tr>
				<tr>
					<td width=15% class="td_normal_title">
						<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuth.fdType"/>
					</td>
					<td width=85%>
						<xform:radio property="fdType">
						   	<xform:customizeDataSource className="com.landray.kmss.sys.lbpmext.businessauth.service.spring.LbpmExtBusinessAuthTypeDataSource"></xform:customizeDataSource>
						</xform:radio>
					</td>
				</tr>
				<!-- 可维护者 -->
				<tr>
					<td class="td_normal_title" width=15%><bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuth.authEditors"/></td>
					<td width=85% colspan="3">
					  <kmss:showText value="${lbpmExtBusinessAuthForm.authEditorNames}"/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr LKS_LabelName="授权记录">
		<td>
			<iframe name="IFRAME" src='<c:url value="/sys/lbpmext/businessauth/lbpmext_businessauth_detail/index.jsp?fdId=${lbpmExtBusinessAuthForm.fdId}" />' frameBorder=0 width="100%"> 
			</iframe>
		</td>
	</tr>
</table>

</center>
<%@ include file="/resource/jsp/view_down.jsp"%>