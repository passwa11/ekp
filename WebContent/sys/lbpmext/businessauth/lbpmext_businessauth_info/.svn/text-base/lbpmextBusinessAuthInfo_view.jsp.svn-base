<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script>
	function confirm_delete(msg){
		var del = confirm("<bean:message key='page.comfirmDelete'/>");
		return del;
	}
</script>
<kmss:windowTitle moduleKey="sys-lbpmext-businessauth:table.lbpmext.businessAuthInfo" subjectKey="sys-lbpmext-businessauth:lbpmext.businessAuthInfo.set"/>
<div id="optBarDiv">
	<kmss:auth requestURL="/sys/lbpmext/businessauth/lbpmBusinessAuthInfo.do?method=add" requestMethod="GET">
		<input type="button" value="<bean:message key="button.copy"/>"
			onClick="Com_OpenWindow('lbpmBusinessAuthInfo.do?method=clone&cloneModelId=<bean:write name="lbpmExtBusinessAuthInfoForm" property="fdId" />','_target');">
	</kmss:auth>
	<kmss:auth requestURL="/sys/lbpmext/businessauth/lbpmBusinessAuthInfo.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
		<input type="button" value="<bean:message key="button.edit"/>"
			onClick="Com_OpenWindow('lbpmBusinessAuthInfo.do?method=edit&fdId=<bean:write name="lbpmExtBusinessAuthInfoForm" property="fdId" />','_self');">
	</kmss:auth>
	<kmss:auth requestURL="/sys/lbpmext/businessauth/lbpmBusinessAuthInfo.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
		<input type="button" value="<bean:message key="button.delete"/>"
			onClick="if(!confirm_delete())return;Com_OpenWindow('lbpmBusinessAuthInfo.do?method=delete&fdId=<bean:write name="lbpmExtBusinessAuthInfoForm" property="fdId" />','_self');">
	</kmss:auth>
	<input type="button" value="<bean:message key="button.close"/>" onClick="Com_CloseWindow();">
</div>
<p class="txttitle"><bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfo"/></p>
<center>
<table class="tb_normal" width=95%>
	<tr>
		<!-- 被授权人 -->
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfo.fdAuthorizedPerson"/>
		</td>
		<td width=35%>
			<xform:address propertyId="fdAuthorizedPersonId" propertyName="fdAuthorizedPersonName" orgType="ORG_TYPE_PERSON"
				style="width:150px">
			</xform:address>
		</td>
		<!-- 被授权岗位 -->
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfo.fdAuthorizedPost"/>
		</td>
		<td width=35%>
			<xform:address propertyId="fdAuthorizedPostId" propertyName="fdAuthorizedPostName" orgType="ORG_TYPE_POST"
				style="width:150px">
			</xform:address>
		</td>
	</tr>
	<tr>
		<!-- 开始时间 -->
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfo.fdStartTime"/>
		</td>
		<td width=35%>
			<html:text property="fdStartTime" readonly="true" />
		</td>
		
		<!-- 结束时间 -->
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfo.fdEndTime"/>
		</td>
		<td width=35%>
			<html:text property="fdEndTime" readonly="true" />
		</td>
	</tr>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfo.fdCategroy"/>
		</td>
		<td width=35%>
			<bean:write name="lbpmExtBusinessAuthInfoForm" property="fdCategoryName"/>
		</td>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfo.status"/>
		</td>
		<td width=35%>
			<xform:radio property="docStatus" showStatus="view">
				<xform:enumsDataSource enumsType="authInfo_docStatus"></xform:enumsDataSource>
			</xform:radio>
		</td>
	</tr>
	<!-- 条目编号开始 -->
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfo.authorizationCode"/>
		</td>
		<td width=85% colspan="3">
			<bean:write name="lbpmExtBusinessAuthInfoForm" property="fdNumber"/>
		</td>
	</tr>
	<!-- 条目编号结束 -->
	<!-- 可维护者 -->
	<tr>
		<td class="td_normal_title" width=15%><bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfo.authEditors"/></td>
		<td width=85% colspan="3">
		  <kmss:showText value="${lbpmExtBusinessAuthInfoForm.authEditorNames}"/>
		</td>
	</tr>
	<%--管辖范围--%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfo.fdScope"/>
		</td>
		<td width=85% colspan="3">
			<bean:write property="fdScope" name="lbpmExtBusinessAuthInfoForm" />
		</td>
	</tr>
	<%--描述--%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfo.fdDesc"/>
		</td>
		<td width=85% colspan="3">
			<bean:write property="fdDesc" name="lbpmExtBusinessAuthInfoForm" />
		</td>
	</tr>
	<tr>
		<td colspan="4">
			<div>
				<div>
					<table class="tb_normal" width=100% id="TABLE_DocList_Details" align="center" style="table-layout:fixed;" frame=void>
						<tr>
							<td align="center">
								<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthDetail.fdAuthorizer"/>
							</td>
							<!-- 分类 -->
							<td align="center">
								<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthCate"/>
							</td>
							<td align="center">
								<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthDetail.fdAuth"/>
							</td>
							<td align="center">
								<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthDetail.fdNumber"/>
							</td>
							<td align="center">
								<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthDetail.fdStartTime"/>
							</td align="center">
							<td align="center">
								<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthDetail.fdEndTime"/>
							</td>
							<td align="center">
								<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthDetail.fdType"/>
							</td>
						</tr>
						<c:forEach items="${lbpmExtBusinessAuthInfoForm.fdAuthDetails}" var="item" varStatus="vstatus">
							<tr KMSS_IsContentRow="1">
								<td align="center">
									<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdAuthorizerId" value="<c:out value='${item.fdAuthorizerId}'/>"/>
									<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdAuthorizerName" value="<c:out value='${item.fdAuthorizerName}'/>"/>
									<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdAuthorizedPersonId" value="<c:out value='${item.fdAuthorizedPersonId}'/>" />
									<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdAuthorizedPersonName" value="<c:out value='${item.fdAuthorizedPersonName}'/>" />
									<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdAuthorizedPostId" value="<c:out value='${item.fdAuthorizedPostId}'/>" />
									<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdAuthorizedPostName" value="<c:out value='${item.fdAuthorizedPostName}'/>" />
									<span class="fdAuthorizerName"><c:out value='${item.fdAuthorizerName}'/></span>
									<!-- 授权人 -->
								</td>
								<!--  条目分类名 -->
								<td align="center">
									<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdCateName" value="<c:out value='${item.fdCateName}'/>"/>
									<span class="fdCateName"><c:out value='${item.fdCateName}'/></span>
								</td>
								<td align="center">
									<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdAuthId" value="<c:out value='${item.fdAuthId}'/>" />
									<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdAuthName" value="<c:out value='${item.fdAuthName}'/>"/>
									<span class="fdAuthName"><c:out value='${item.fdAuthName}'/></span>
								</td>
								<td align="center">
									<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdNumber" value="<c:out value='${item.fdNumber}'/>"/>
									<span class="fdNumber"><c:out value='${item.fdNumber}'/></span>
								</td>
								<td align="center">
									<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdStartTime" value="<c:out value='${item.fdStartTime}'/>" class="inputread_normal" readonly="readonly"/>
									<span class="fdStartTime"><c:out value='${item.fdStartTime}'/></span>
								</td>
								<td align="center">
									<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdEndTime" value="<c:out value='${item.fdEndTime}'/>" class="inputread_normal" readonly="readonly"/>
									<span class="fdEndTime"><c:out value='${item.fdEndTime}'/></span>
								</td>
								<td align="center">
									<div class="fdLimitInfo">
										<input type="radio" checked="checked" readonly="readonly"><span class="fdType"><c:out value="${item.fdTypeName}"/></span><span class="limitRange" style="display:${item.fdType==3?'none':''}">&nbsp;<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthDetail.fdLimit"/>&nbsp;<span class="fdMinLimit"><c:out value='${item.fdMinLimit}'/></span>~<span class="fdLimit"><c:out value='${item.fdLimit}'/></span></span>
									</div>
									<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdType" value="<c:out value='${item.fdType}'/>" />
									<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdLimit" value="<c:out value='${item.fdLimit}'/>" />
									<input type="hidden" name="fdAuthDetails[${vstatus.index}].fdMinLimit" value="<c:out value='${item.fdMinLimit}'/>" />
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>