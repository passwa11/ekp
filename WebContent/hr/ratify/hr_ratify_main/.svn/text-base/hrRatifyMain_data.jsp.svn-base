<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="hrRatifyMain" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column col="fdId">
        	<c:out value="${hrRatifyMain[0] }"></c:out>
        </list:data-column>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="docSubject" title="${lfn:message('hr-ratify:hrRatifyMain.docSubject')}" escape="false">
        	<span class="com_subject"><c:out value="${hrRatifyMain[1] }"></c:out></span>
        </list:data-column>
        <list:data-column col="docNumber" title="${lfn:message('hr-ratify:hrRatifyMain.docNumber')}">
        	<c:out value="${hrRatifyMain[2] }"></c:out>
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('hr-ratify:hrRatifyMain.docCreator')}" escape="false">
            <c:out value="${hrRatifyMain[3]}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${hrRatifyMain[4]}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('hr-ratify:hrRatifyMain.docCreateTime')}">
            <kmss:showDate value="${hrRatifyMain[5]}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="docPublishTime" title="${lfn:message('hr-ratify:hrRatifyMain.docPublishTime')}">
            <kmss:showDate value="${hrRatifyMain[6]}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="docStatus.name" title="${lfn:message('hr-ratify:hrRatifyMain.docStatus')}">
            <sunbor:enumsShow value="${hrRatifyMain[7]}" enumsType="hr_ratify_doc_status" />
        </list:data-column>
        <list:data-column col="docStatus">
            <c:out value="${hrRatifyMain[7]}" />
        </list:data-column>
        <list:data-column col="subclassModelname">
        	<c:out value="${hrRatifyMain[8]}" />
        </list:data-column>
        <!-- lbpm_main -->
        <list:data-column col="lbpm_main_listcolumn_node" title="${lfn:message('hr-ratify:lbpm.currentNode') }" escape="false">
            <kmss:showWfPropertyValues var="nodevalue" idValue="${hrRatifyMain[0]}" propertyName="nodeName" />
            <div title="${nodevalue}">
                <c:out value="${nodevalue}"></c:out>
            </div>
        </list:data-column>
        <!-- lbpm_main -->
        <list:data-column col="lbpm_main_listcolumn_handler" title="${lfn:message('hr-ratify:lbpm.currentHandler') }" escape="false">
            <kmss:showWfPropertyValues var="handlerValue" idValue="${hrRatifyMain[0]}" propertyName="handlerName" />
            <div style="font-weight:bold;" title="${handlerValue}">
                <c:out value="${handlerValue}"></c:out>
            </div>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
