<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.km.smissive.util.KmSmissiveConfigUtil"%>
<%@page import="com.landray.kmss.km.smissive.forms.KmSmissiveMainForm"%>
<%@page import="com.landray.kmss.sys.attachment.model.SysAttMain"%>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%@page import="com.landray.kmss.sys.number.util.NumberResourceUtil"%>
<ui:content
	title="${lfn:message('km-smissive:kmSmissiveMain.label.baseinfo')}"
	expand="true">
	<table class="tb_normal" width=100%>
		<tr>
			<td class="td_normal_title" width=15%><bean:message
					bundle="km-smissive" key="kmSmissiveMain.docSubject" /></td>
			<td width=85% colspan="3"><c:out
					value="${kmSmissiveMainForm.docSubject }"></c:out></td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%><bean:message
					bundle="km-smissive" key="kmSmissiveMain.docAuthorId" /></td>
			<td width=35%><c:out
					value="${kmSmissiveMainForm.docAuthorName }"></c:out></td>
			<td class="td_normal_title" width=15%><bean:message
					bundle="km-smissive" key="kmSmissiveMain.docCreateTime" /></td>
			<td width=35%><c:out
					value="${kmSmissiveMainForm.docCreateTime }"></c:out></td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%><bean:message
					bundle="km-smissive" key="kmSmissiveMain.fdUrgency" /></td>
			<td width=35%><sunbor:enumsShow
					value="${kmSmissiveMainForm.fdUrgency}"
					enumsType="km_smissive_urgency" bundle="km-smissive" /></td>
			<td class="td_normal_title" width=15%><bean:message
					bundle="km-smissive" key="kmSmissiveMain.fdSecret" /></td>
			<td width=35%><sunbor:enumsShow
					value="${kmSmissiveMainForm.fdSecret}"
					enumsType="km_smissive_secret" bundle="km-smissive" /></td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%><bean:message
					bundle="km-smissive" key="kmSmissiveMain.fdTemplateId" /></td>
			<td width=35%><c:out
					value="${kmSmissiveMainForm.fdTemplateName }"></c:out></td>
			<td class="td_normal_title" width=15%><bean:message
					bundle="km-smissive" key="kmSmissiveMain.fdFileNo" /></td>
			<td width=35%><c:choose>
					<c:when
						test="${kmSmissiveMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.modifyDocNum =='true'}">
						<input type="hidden" name="fdFileNo"
							value="${kmSmissiveMainForm.fdFileNo}" />
						<span id="docnum"> <c:if
								test="${not empty kmSmissiveMainForm.fdFileNo}">
								<c:out value="${kmSmissiveMainForm.fdFileNo}" />
							</c:if>
						</span>
					</c:when>
					<c:otherwise>
						<c:if test="${not empty kmSmissiveMainForm.fdFileNo}">
							<c:out value="${kmSmissiveMainForm.fdFileNo}" />
						</c:if>
						<c:if test="${empty kmSmissiveMainForm.fdFileNo}">
							<bean:message bundle="km-smissive"
								key="kmSmissiveMain.fdFileNo.describe" />
						</c:if>
					</c:otherwise>
				</c:choose></td>
		</tr>
		<%-- 所属场所 --%>
		<c:import
			url="/sys/authorization/sys_auth_area/sysAuthArea_field.jsp"
			charEncoding="UTF-8">
			<c:param name="id" value="${kmSmissiveMainForm.authAreaId}" />
		</c:import>
		<tr>
			<td class="td_normal_title" width=15%><bean:message
					bundle="km-smissive" key="kmSmissiveMain.fdMainDeptId" /></td>
			<td width=35%><c:out
					value="${kmSmissiveMainForm.fdMainDeptName }"></c:out></td>
			<td class="td_normal_title" width=15%><bean:message
					bundle="km-smissive" key="kmSmissiveMain.docDeptId" /></td>
			<td width=35%><c:out
					value="${kmSmissiveMainForm.docDeptName }"></c:out></td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%><bean:message
					bundle="km-smissive" key="kmSmissiveMain.fdSendDeptId" /></td>
			<td width=35%><c:out
					value="${kmSmissiveMainForm.fdSendDeptNames }"></c:out></td>
			<td class="td_normal_title" width=15%><bean:message
					bundle="km-smissive" key="kmSmissiveMain.fdCopyDeptId" /></td>
			<td width=35%><c:out
					value="${kmSmissiveMainForm.fdCopyDeptNames }"></c:out></td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%><bean:message
					bundle="km-smissive" key="kmSmissiveMain.fdIssuerId" /></td>
			<td width=35%><c:out
					value="${kmSmissiveMainForm.fdIssuerName }"></c:out></td>
			<td class="td_normal_title" width=15%><bean:message
					bundle="km-smissive" key="kmSmissiveMain.docCreatorId" /></td>
			<td width=35%><c:out
					value="${kmSmissiveMainForm.docCreatorName }"></c:out></td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%><bean:message
					bundle="km-smissive" key="kmSmissiveMainProperty.fdPropertyId" />
			</td>
			<td width=35% colspan="3"><c:out
					value="${kmSmissiveMainForm.docPropertyNames }"></c:out></td>
		</tr>
		<!-- 标签机制 -->
		<c:import url="/sys/tag/include/sysTagMain_view.jsp"
			charEncoding="UTF-8">
			<c:param name="formName" value="kmSmissiveMainForm" />
			<c:param name="fdKey" value="smissiveDoc" />
		</c:import>
		<!-- 标签机制 -->
	</table>
</ui:content>
<c:choose>
	<c:when test="${param.approveModel eq 'right'}">
		<c:choose>
			<c:when test="${kmSmissiveMainForm.fdFlowFlag == 'false'}">
				<c:choose>
					<c:when test="${kmSmissiveMainForm.docStatus>='30' || kmSmissiveMainForm.docStatus=='00' || kmSmissiveMainForm.docStatus=='10' }">
						<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="kmSmissiveMainForm" />
							<c:param name="fdKey" value="smissiveDoc" />
							<c:param name="showHistoryOpers" value="true" />
							<c:param name="isExpand" value="true" />
							<c:param name="approveType" value="right" />
							<c:param name="needInitLbpm" value="true" />
						</c:import>
					</c:when>
					<c:otherwise>
						<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="kmSmissiveMainForm" />
							<c:param name="fdKey" value="smissiveDoc" />
							<c:param name="showHistoryOpers" value="true" />
							<c:param name="isExpand" value="true" />
							<c:param name="approveType" value="right" />
						</c:import>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<kmss:auth requestURL="/km/smissive/km_smissive_main/kmSmissiveMain.do?method=viewWfLog&fdId=${param.fdId}"  requestMethod="GET">
					<c:choose>
						<c:when test="${kmSmissiveMainForm.docStatus>='30' || kmSmissiveMainForm.docStatus=='00' || kmSmissiveMainForm.docStatus=='10'}">
							<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
								<c:param name="formName" value="kmSmissiveMainForm" />
								<c:param name="fdKey" value="smissiveDoc" />
								<c:param name="showHistoryOpers" value="true" />
								<c:param name="isExpand" value="true" />
								<c:param name="approveType" value="right" />
								<c:param name="needInitLbpm" value="true" />
							</c:import>
						</c:when>
						<c:otherwise>
							<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
								<c:param name="formName" value="kmSmissiveMainForm" />
								<c:param name="fdKey" value="smissiveDoc" />
								<c:param name="showHistoryOpers" value="true" />
								<c:param name="isExpand" value="true" />
								<c:param name="approveType" value="right" />
							</c:import>
						</c:otherwise>
					</c:choose>
				</kmss:auth>
			</c:otherwise>
		</c:choose>	
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${kmSmissiveMainForm.fdFlowFlag == 'false'}">
				<%-- 流程机制 --%>
				<c:import url="/sys/workflow/import/sysWfProcess_view.jsp"
					charEncoding="UTF-8">
					<c:param name="formName" value="kmSmissiveMainForm" />
					<c:param name="fdKey" value="smissiveDoc" />
				</c:import>
			</c:when>
			<c:otherwise>
				<kmss:auth requestURL="/km/smissive/km_smissive_main/kmSmissiveMain.do?method=viewWfLog&fdId=${param.fdId}"  requestMethod="GET">
					<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="kmSmissiveMainForm" />
						<c:param name="fdKey" value="smissiveDoc" />
					</c:import>
				</kmss:auth>
			</c:otherwise>
		</c:choose>	
	</c:otherwise>
</c:choose>
<%--以下代码为嵌入阅读机制--%>
<c:import url="/sys/readlog/import/sysReadLog_view.jsp"
	charEncoding="UTF-8">
	<c:param name="formName" value="kmSmissiveMainForm" />
</c:import>
<%--以上代码为嵌入阅读机制--%>
<c:import url="/sys/circulation/import/sysCirculationMain_view.jsp"
	charEncoding="UTF-8">
	<c:param name="formName" value="kmSmissiveMainForm" />
</c:import>
<%--发布机制开始--%>
<c:import url="/sys/news/import/sysNewsPublishMain_view.jsp"
	charEncoding="UTF-8">
	<c:param name="formName" value="kmSmissiveMainForm" />
</c:import>
<%--发布机制结束--%>
<c:import url="/sys/right/import/right_view.jsp" charEncoding="UTF-8">
	<c:param name="formName" value="kmSmissiveMainForm" />
	<c:param name="moduleModelName"
		value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
</c:import>
<%--以下代码为嵌入收藏机制--%>
<c:import url="/sys/bookmark/import/bookmark_bar.jsp"
	charEncoding="UTF-8">
	<c:param name="fdSubject" value="${kmSmissiveMainForm.docSubject}" />
	<c:param name="fdModelId" value="${kmSmissiveMainForm.fdId}" />
	<c:param name="fdModelName"
		value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
</c:import>
<%--以上代码为嵌入收藏机制--%>
<kmss:ifModuleExist path="/tools/datatransfer/">
	<c:import
		url="/tools/datatransfer/import/toolsDatatransfer_old_data.jsp"
		charEncoding="UTF-8">
		<c:param name="fdModelId" value="${kmSmissiveMainForm.fdId}" />
		<c:param name="fdModelName"
			value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
	</c:import>
</kmss:ifModuleExist>
<!-- 督办 -->
<kmss:ifModuleExist path="/km/supervise/">
	<c:import url="/km/supervise/import/kmSuperviseMain_edit.jsp"
		charEncoding="UTF-8">
		<c:param name="fdModelId" value="${kmSmissiveMainForm.fdId}" />
		<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
		<c:param name="norecodeLayout" value="${norecodeLayout}" />
	</c:import>
</kmss:ifModuleExist>
