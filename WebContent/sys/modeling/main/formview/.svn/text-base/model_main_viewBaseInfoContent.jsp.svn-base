<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!-- 草稿状态的文档默认选中基本信息页签 -->
<c:if test="${modelingAppModelMainForm.docStatus=='10'}">
    <ui:event event="layoutDone">
        $("i.lui-fm-icon-2").closest(".lui_tabpanel_vertical_icon_navs_item_l").click();
    </ui:event>
</c:if>
<ui:content title="${lfn:message('sys-modeling-main:modelMainDocumentLabelName.baseInfo')}" titleicon="lui-fm-icon-2">
    <table class="tb_normal lui-fm-noneBorderTable" width=100%>
        <!--主题-->
        <tr>
            <td class="tr_normal_title" width=30%>
                <bean:message bundle="sys-modeling-main" key="modelMain.docSubject" />
            </td>
            <td>
                <c:out value="${ modelingAppModelMainForm.docSubject}"></c:out>
            </td>
        </tr>
        <!--关键字-->

        <!--模板名称-->

        <!--申请人-->
        <tr>
            <td class="tr_normal_title" width=30%>
                <bean:message bundle="sys-modeling-main" key="modelMain.docCreatorName" />
            </td>
            <td>
                <xform:text property="docCreatorId" showStatus="noShow"/>
                <c:out value="${ modelingAppModelMainForm.docCreatorName}"></c:out>
            </td>
        </tr>
        <!-- 申请单编号 -->

        <!--部门-->

        <!--创建时间-->
        <tr>
            <td class="tr_normal_title" width=30%>
                <bean:message bundle="sys-modeling-main" key="modelMain.docCreateTime" />
            </td>
            <td>
                <c:out value="${ modelingAppModelMainForm.docCreateTime}"></c:out>
            </td>
        </tr>
        <!--状态-->
        <tr>
            <td class="tr_normal_title" width=30%>
                <bean:message bundle="sys-modeling-main" key="modelMain.docStatus" />
            </td>
            <td>
                <c:if test="${modelingAppModelMainForm.docStatus=='00'}">
                    <bean:message bundle="sys-modeling-main" key="status.discard"/>
                </c:if>
                <c:if test="${modelingAppModelMainForm.docStatus=='10'}">
                    <bean:message bundle="sys-modeling-main" key="status.draft"/>
                </c:if>
                <c:if test="${modelingAppModelMainForm.docStatus=='11'}">
                    <bean:message bundle="sys-modeling-main" key="status.refuse"/>
                </c:if>
                <c:if test="${modelingAppModelMainForm.docStatus=='20'}">
                    <bean:message bundle="sys-modeling-main" key="status.append"/>
                </c:if>
                <c:if test="${modelingAppModelMainForm.docStatus=='30'}">
                    <bean:message bundle="sys-modeling-main" key="status.publish"/>
                </c:if>
                <c:if test="${modelingAppModelMainForm.docStatus=='31'}">
                    <bean:message bundle="sys-modeling-main" key="status.feedback" />
                </c:if>
            </td>
        </tr>
        <!--结束时间-->

        <!--实施反馈人-->
            <%-- 所属场所 --%>

        <!--其他属性-->
    </table>
</ui:content>