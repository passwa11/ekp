<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<style type="text/css">
.grayColor{
		color:gray;
}
.noborder{
	border-collapse:collapse;
	border: 0px #FFF solid;
	background-color: #FFFFFF;
}
.noborder td{
	border-collapse:collapse;
	border: 0px #FFF solid;
	padding: 5px 5px 0 0;
}
#nav li {     
	 list-style-type: none;    
	 padding: 2px 5px;  
} 
.flowNoLabel{
	 width:150px;text-align: right;padding-right:5px;
}
</style>

<script type="text/javascript">
	Com_IncludeFile("doclist.js|dialog.js|calendar.js|optbar.js|jquery.js|json2.js|formula.js");
</script>
<script type="text/javascript">
	function confirmDelete(msg){
		var del = confirm("<bean:message key="page.comfirmDelete"/>");
		return del;
	}
	function confirmUpdateInvalidation(msg){
		var del = confirm("<bean:message key="sys-authentication:sysTokenInfo.confirmUpdateInvalidation"/>");
		return del;
	}
</script>
<html:form action="/sys/token/sys_token_info/sysTokenInfo.do">
	<div id="optBarDiv">
		<c:if test="${sysTokenInfoForm.fdInvalidation=='0' }">
			<input type="button"
				   value="<bean:message key="sys-authentication:button.invalidation"/>"
				   onclick="if(!confirmUpdateInvalidation())return;Com_OpenWindow('sysTokenInfo.do?method=updateInvalidation&fdId=${JsParam.fdId}','_self');">
		</c:if>
		<input type="button"
			   value="<bean:message key="button.delete"/>"
			   onclick="if(!confirmDelete())return;Com_OpenWindow('sysTokenInfo.do?method=delete&fdId=${JsParam.fdId}','_self');">
		<input type="button"
			   value="<bean:message key="button.close"/>"
			   onclick="Com_CloseWindow();">
	</div>

	<c:if test="${JsParam['isCustom']!='1'}">
		<p class="txttitle"><bean:message bundle="sys-authentication" key="table.sysTokenInfo"/></p>
	</c:if>
<center>
	<table id="Label_Tabel" width="95%">
		<tr LKS_LabelName="${ lfn:message('sys-authentication:sysTokenInfo.token') }">
			<td>
				<table width="100%" class="tb_normal">
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-authentication" key="sysTokenInfo.fdValue"/>
						</td><td width="35%">
							<xform:text property="fdValue" style="width:45%" required="true"/>
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-authentication" key="sysTokenInfo.fdInvalidation"/>
						</td><td width="35%">
							<c:if test="${sysTokenInfoForm.fdInvalidation=='0' }">
									<bean:message key="sys-authentication:sysTokenInfo.invalidation"/>
							</c:if>
							<c:if test="${sysTokenInfoForm.fdInvalidation=='1' }">
									<bean:message key="sys-authentication:sysTokenInfo.effective"/>
							</c:if>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-authentication" key="sysTokenInfo.fdVisitCount"/>
						</td>
						<td colspan="3"><bean:write name="sysTokenInfoForm" property="fdVisitCount" /></td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-authentication" key="sysTokenInfo.fdVisitMethod"/>
						</td>
						<td colspan="3"><bean:write name="sysTokenInfoForm" property="fdVisitMethod" /></td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-authentication" key="sysTokenInfo.fdVisitEndPeriod"/>
						</td>
						<td colspan="3"><bean:write name="sysTokenInfoForm" property="fdVisitEndPeriod" /></td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-authentication" key="sysTokenInfo.fdTargetUrl"/>
						</td>
						<td colspan="3"><bean:write name="sysTokenInfoForm" property="fdTargetUrl" /></td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-authentication" key="sysTokenInfo.fdVisitMaxCount"/>
						</td>
						<td colspan="3"><bean:write name="sysTokenInfoForm" property="fdVisitMaxCount" /></td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-authentication" key="sysTokenInfo.fdModelName"/>
						</td>
						<td colspan="3"><bean:write name="sysTokenInfoForm" property="fdModelName" /></td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-authentication" key="sysTokenInfo.fdModelId"/>
						</td>
						<td colspan="3"><bean:write name="sysTokenInfoForm" property="fdModelId" /></td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-authentication" key="sysTokenInfo.fdVisitFrequencyCount"/>
						</td>
						<td colspan="3">
							<table>
								<c:forEach items="${sysTokenInfoForm.fdVisitFrequencyLimitList}" var="visitFrequencyLimit" varStatus="vstatus">
									<tr>
										<td colspan="3">
											<c:if test="${visitFrequencyLimit.fdVisitFrequencyType!='0' }">
												${visitFrequencyLimit.fdVisitFrequencyCount}
												<c:if test="${visitFrequencyLimit.fdVisitFrequencyType=='1' }">
													<bean:message key="sys-authentication:sysTokenInfo.fdVisitFrequencyType.minute"/>
												</c:if>
												<c:if test="${visitFrequencyLimit.fdVisitFrequencyType=='2' }">
													<bean:message key="sys-authentication:sysTokenInfo.fdVisitFrequencyType.hour"/>
												</c:if>
												<c:if test="${visitFrequencyLimit.fdVisitFrequencyType=='3' }">
													<bean:message key="sys-authentication:sysTokenInfo.fdVisitFrequencyType.day"/>
												</c:if>
											</c:if>
										</td>
									</tr>
								</c:forEach>
							</table>
						</td>


					</tr>
<%--					<c:forEach items="${queryPage.list}" var="componentLockerMain" varStatus="vstatus">--%>
<%--					<tr>--%>
<%--						<td class="td_normal_title" width=15%>--%>
<%--							<bean:message bundle="sys-authentication" key="sysTokenInfo.fdVisitFrequencyType"/>--%>
<%--						</td>--%>
<%--						<td colspan="3">--%>
<%--							<c:if test="${sysTokenInfoForm.fdVisitFrequencyType=='1' }">--%>
<%--								<bean:message key="sys-authentication:sysTokenInfo.fdVisitFrequencyType.minute"/>--%>
<%--							</c:if>--%>
<%--							<c:if test="${sysTokenInfoForm.fdVisitFrequencyType=='2' }">--%>
<%--								<bean:message key="sys-authentication:sysTokenInfo.fdVisitFrequencyType.hour"/>--%>
<%--							</c:if>--%>
<%--							<c:if test="${sysTokenInfoForm.fdVisitFrequencyType=='3' }">--%>
<%--								<bean:message key="sys-authentication:sysTokenInfo.fdVisitFrequencyType.day"/>--%>
<%--							</c:if>--%>
<%--					</tr>--%>
<%--					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-authentication" key="sysTokenInfo.fdVisitFrequencyCount"/>
						</td>
						<td colspan="3"><bean:write name="sysTokenInfoForm" property="fdVisitFrequencyCount" /></td>
					</tr>--%>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-authentication" key="sysTokenInfo.fdVisitFailMsgService"/>
						</td>
						<td colspan="3"><bean:write name="sysTokenInfoForm" property="fdVisitFailMsgService" /></td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-authentication" key="sysTokenInfo.fdVisitFailMsgMethod"/>
						</td>
						<td colspan="3"><bean:write name="sysTokenInfoForm" property="fdVisitFailMsgMethod" /></td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-authentication" key="sysTokenInfo.docCreator"/>
						</td><td width="35%">
							<xform:text property="docCreatorName" style="width:45%" showStatus="view"/>
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-authentication" key="sysTokenInfo.docCreateTime"/>
						</td><td width="35%">
							<xform:text property="docCreateTime" style="width:45%" showStatus="view"/>
						</td>
					</tr>
<%--					<tr>--%>
<%--						<td class="td_normal_title" width=15%>--%>
<%--							<bean:message bundle="sys-authentication" key="sysTokenInfo.docAlteror"/>--%>
<%--						</td><td width="35%">--%>
<%--							<xform:text property="docAlterorName" style="width:45%" showStatus="view"/>--%>
<%--						</td>--%>
<%--						<td class="td_normal_title" width=15%>--%>
<%--							<bean:message bundle="sys-authentication" key="sysTokenInfo.docAlterTime"/>--%>
<%--						</td><td width="35%">--%>
<%--							<xform:text property="docAlterTime" style="width:45%" showStatus="view"/>--%>
<%--						</td>--%>
<%--					</tr>--%>
			</table>
		</td>
	</tr>
</table>

</center>

<html:hidden property="fdId" />
<html:hidden property="method_GET" />

<%--<jsp:include page="/sys/number/include/commonScript.jsp"></jsp:include>--%>

<script type="text/javascript">

//入口函数
$(function(){
	init();
});

</script>
</html:form>
<%@ include file="/resource/jsp/view_down.jsp"%>