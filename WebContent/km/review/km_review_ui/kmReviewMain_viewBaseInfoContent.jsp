<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!-- 草稿状态的文档默认选中基本信息页签 -->
<c:if test="${kmReviewMainForm.docStatus=='10'}">
	<ui:event event="layoutDone">
		$("i.lui-fm-icon-2").closest(".lui_tabpanel_vertical_icon_navs_item_l").click();
    </ui:event>
</c:if>
<ui:content title="${lfn:message('km-review:kmReviewDocumentLableName.baseInfo') }" titleicon="lui-fm-icon-2">
	<table class="tb_normal lui-fm-noneBorderTable" width=100%>
		<!--主题-->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-review" key="kmReviewMain.docSubject" />
			</td>
			<td>
				<c:out value="${ kmReviewMainForm.docSubject}"></c:out>
			</td>
		</tr>
		<!--关键字-->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-review" key="kmReviewKeyword.fdKeyword" />
			</td>
			<td>
				<c:out value="${ kmReviewMainForm.fdKeywordNames}"></c:out>
			</td>
		</tr>
		<!--模板名称-->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-review" key="kmReviewTemplate.fdName" />
			</td>
			<td>
				<c:out value="${ kmReviewMainForm.fdTemplateName}"></c:out>
			</td>
		</tr>
		<!--申请人-->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-review" key="kmReviewMain.docCreatorName" />
			</td>
			<td>
				<xform:text property="docCreatorId" showStatus="noShow"/> 
				<c:out value="${ kmReviewMainForm.docCreatorName}"></c:out>
			</td>
		</tr>
		<!-- 申请单编号 -->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-review" key="kmReviewMain.fdNumber" />
			</td>
			<td>
				<c:out value="${ kmReviewMainForm.fdNumber}"></c:out>
			</td>
		</tr>
		<!--部门-->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-review" key="kmReviewMain.department" />
			</td>
			<td>
				<c:out value="${ kmReviewMainForm.fdDepartmentName}"></c:out>
			</td>
		</tr>
		<!--创建时间-->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-review" key="kmReviewMain.docCreateTime" />
			</td>
			<td>
				<c:out value="${ kmReviewMainForm.docCreateTime}"></c:out>
			</td>
		</tr>
		<!--状态-->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-review" key="kmReviewMain.docStatus" />
			</td>
			<td>
				<c:if test="${kmReviewMainForm.docStatus=='00'}">
					<bean:message bundle="km-review" key="status.discard"/>
				</c:if>
				<c:if test="${kmReviewMainForm.docStatus=='10'}">
					<bean:message bundle="km-review" key="status.draft"/>
				</c:if>
				<c:if test="${kmReviewMainForm.docStatus=='11'}">
					<bean:message bundle="km-review" key="status.refuse"/>
				</c:if>
				<c:if test="${kmReviewMainForm.docStatus=='20'}">
					<bean:message bundle="km-review" key="status.append"/>
				</c:if>
				<c:if test="${kmReviewMainForm.docStatus=='30'}">
					<bean:message bundle="km-review" key="status.publish"/>
				</c:if>
				<c:if test="${kmReviewMainForm.docStatus=='31'}">
					<bean:message bundle="km-review" key="status.feedback" />
				</c:if>
			</td>
		</tr>
		<!--结束时间-->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-review" key="kmReviewMain.docPublishTime" />
			</td>
			<td>
				<c:out value="${ kmReviewMainForm.docPublishTime}"></c:out>
			</td>
		</tr>
		<!--实施反馈人-->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-review" key="table.kmReviewFeedback" />
			</td>
			<td>
				<c:out value="${ kmReviewMainForm.fdFeedbackNames}"></c:out>
			</td>
		</tr>
		<%-- 所属场所 --%>
		<c:import url="/sys/authorization/sys_auth_area/sysAuthArea_field.jsp" charEncoding="UTF-8">
               <c:param name="id" value="${kmReviewMainForm.authAreaId}"/>
               <c:param name="rightModel" value="true"/>
           </c:import> 
		<!--其他属性-->
		<tr>
			<td class="tr_normal_title" width=30%>
				<bean:message bundle="km-review" key="table.sysCategoryProperty" />
			</td>
			<td>
				<c:out value="${ kmReviewMainForm.docPropertyNames}"></c:out>
			</td>
		</tr>
	</table>
</ui:content>