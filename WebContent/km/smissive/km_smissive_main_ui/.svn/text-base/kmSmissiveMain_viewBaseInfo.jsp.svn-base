<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<ui:content title="${lfn:message('km-smissive:kmSmissiveMain.label.baseinfo')}" titleicon="lui-fm-icon-2">
	<table class="tb_normal lui-fm-noneBorderTable" width=100%>
		<tr>
			<td class="tr_normal_title" width=30%><bean:message
					bundle="km-smissive" key="kmSmissiveMain.docAuthorId" /></td>
			<td><c:out
					value="${kmSmissiveMainForm.docAuthorName }"></c:out></td>
		</tr>
		<tr>
			<td class="tr_normal_title" width=30%><bean:message
					bundle="km-smissive" key="kmSmissiveMain.docDeptId" /></td>
			<td><c:out
					value="${kmSmissiveMainForm.docDeptName }"></c:out></td>
		</tr>
		<tr>
			<td class="tr_normal_title" width=30%><bean:message
					bundle="km-smissive" key="kmSmissiveMain.docCreateTime" /></td>
			<td><c:out
					value="${kmSmissiveMainForm.docCreateTime }"></c:out></td>
		</tr>
		<tr>
			<td class="tr_normal_title" width=30%><bean:message
					bundle="km-smissive" key="kmSmissiveMain.docStatus" /></td>
			<td><sunbor:enumsShow value="${kmSmissiveMainForm.docStatus}" enumsType="common_status" /></td>
		</tr>
		<tr>
			<td class="tr_normal_title" width=30%><bean:message
					bundle="km-smissive" key="kmSmissiveMain.fdFileNo" /></td>
			<td><c:choose>
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
		<c:import url="/sys/authorization/sys_auth_area/sysAuthArea_field.jsp" charEncoding="UTF-8">
               <c:param name="id" value="${kmSmissiveMainForm.authAreaId}"/>
               <c:param name="rightModel" value="true"/>
           </c:import> 
	</table>
</ui:content>