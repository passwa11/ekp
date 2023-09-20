<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<ui:content title="基本信息" titleicon="lui-fm-icon-2">
	<!-- 草稿状态的文档默认选中基本信息页签 -->
	<c:if test="${hrRatifyChangeForm.docStatus=='10'}">
		<script>
			LUI.ready(function() {
				setTimeout(function() {
					$("i.lui-fm-icon-2").closest(
							".lui_tabpanel_vertical_icon_navs_item_l").click();
				}, 200);
			});
		</script>
	</c:if>
	<table class="tb_normal lui-fm-noneBorderTable" width="100%">
		<tr>
			<td class="tr_normal_title" width="30%">
	        	<bean:message bundle="hr-ratify" key="hrRatifyMain.docSubject"/>
	     	</td>
            <td>
                <%-- 标题--%>
                <c:out value="${hrRatifyChangeForm.docSubject }"></c:out>
            </td>
		</tr>
        <tr>
        	<td class="tr_normal_title" width="30%">
        		<bean:message bundle="hr-ratify" key="hrRatifyMKeyword.docKeyword"/>
        	</td>
        	<td>
        		<c:out value="${hrRatifyChangeForm.fdKeywordNames }"></c:out>
        	</td>
        </tr>
        <tr>
            <td class="tr_normal_title" width="30%">
                <bean:message bundle="hr-ratify" key="hrRatifyMain.docTemplate"/>
            </td>
            <td>
                <%-- 分类模板--%>
                <c:out value="${ hrRatifyChangeForm.docTemplateName}"/>
            </td>
        </tr>
        <tr>
            <td class="tr_normal_title" width="30%">
                <bean:message bundle="hr-ratify" key="hrRatifyMain.docCreator"/>
            </td>
            <td>
                <%-- 创建人--%>
                <c:out value="${hrRatifyChangeForm.docCreatorName }"></c:out>
            </td>
        </tr>
        <tr>
            <td class="tr_normal_title" width="30%">
                <bean:message bundle="hr-ratify" key="hrRatifyMain.docNumber"/>
            </td>
            <td>
                <%-- 编号--%>
                <c:out value="${hrRatifyChangeForm.docNumber }"></c:out>
            </td>
        </tr>
        <tr>
            <td class="tr_normal_title" width="30%">
                <bean:message bundle="hr-ratify" key="hrRatifyMain.fdDepartment"/>
            </td>
            <td>
                <%-- 部门--%>
                <c:out value="${hrRatifyChangeForm.fdDepartmentName }"></c:out>
            </td>
        </tr>
        <tr>   
            <td class="tr_normal_title" width="30%">
                <bean:message bundle="hr-ratify" key="hrRatifyMain.docCreateTime"/>
            </td>
            <td>
                <%-- 创建时间--%>
                <c:out value="${hrRatifyChangeForm.docCreateTime }"></c:out>
            </td>
        </tr>
        <tr>
            <td class="tr_normal_title" width="30%">
                <bean:message bundle="hr-ratify" key="hrRatifyMain.docPublishTime"/>
            </td>
            <td>
                <%-- 结束时间--%>
                <c:out value="${hrRatifyChangeForm.docPublishTime }"></c:out>
            </td>
        </tr>
        <tr>
            <td class="tr_normal_title" width="30%">
                <bean:message bundle="hr-ratify" key="hrRatifyMain.fdFeedback"/>
            </td>
            <td>
                <%-- 实施反馈人--%>
                <c:out value="${hrRatifyChangeForm.fdFeedbackNames }"></c:out>
            </td>
        </tr>
	</table>
</ui:content>