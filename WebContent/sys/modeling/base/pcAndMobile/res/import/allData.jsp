<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<list:data>
    <!-- 表格数据 -->
    <list:data-columns var="modelingAllViewsForm" list="${queryPage.list }">
        <list:data-column property="fdId"/>
        <list:data-column property="fdList" title="列表/查看"/>
        <list:data-column col="fdTypeIcon" title="${lfn:message('sys-modeling-base:portal.model')}" escape="false" style="width: 100px;">
            <link type="text/css" rel="stylesheet"
                  href="${LUI_ContextPath}/sys/modeling/base/resources/css/pcAndMobile.css?s_cache=${LUI_Cache}"/>
            <span class="propertySpanFdType" style="display: none">${modelingAllViewsForm.fdType}</span>
            <div style="width: 100%;text-align: center;padding:0px 20px">
            <c:if test="${modelingAllViewsForm.fdType!=1}">
                <span class="modeling-pam-pc-icon"></span>
            </c:if>
            <c:if test="${modelingAllViewsForm.fdType!=0}">
                <span class="modeling-pam-mobile-icon"></span>
            </c:if>
            </div>
        </list:data-column>
        <list:data-column property="fdName" title="${lfn:message('sys-modeling-base:modelingPcAndMobileView.fdName')}"/>
        <list:data-column property="modelMainId" title="modelMainId"/>
        <list:data-column property="modelMainName" title="${lfn:message('sys-modeling-base:portal.business.module')}"/>
        <list:data-column property="docCreateTime" title="${lfn:message('sys-modeling-base:modelingPcAndMobileView.docCreateTime')}"/>
        <list:data-column property="docCreator" title="${lfn:message('sys-modeling-base:modelingBusiness.docCreator')}"/>
    </list:data-columns>

    <!-- 分页 -->
    <list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }"
                      totalSize="${queryPage.totalrows }"/>
</list:data>