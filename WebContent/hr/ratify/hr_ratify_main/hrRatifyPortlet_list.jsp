<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="hrRatifyMain" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column col="fdId">
        	<c:out value="${hrRatifyMain[0] }"></c:out>
        </list:data-column>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        <list:data-column col="docSubject" title="${lfn:message('hr-ratify:hrRatifyMain.docSubject')}" escape="false">
        	<a class="com_subject" onclick="Com_OpenNewWindow(this)" data-href="/hr/ratify/hr_ratify_main/hrRatifyMain.do?method=view4Main&fdId=${hrRatifyMain[0] }"  target="_blank"><c:out value="${hrRatifyMain[1] }"></c:out></span>
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('hr-ratify:hrRatifyMain.docCreator')}" escape="false">
            <c:out value="${hrRatifyMain[3]}" />
        </list:data-column>

        <list:data-column col="docCreateTime" title="${lfn:message('hr-ratify:hrRatifyMain.docCreateTime')}">
            <kmss:showDate value="${hrRatifyMain[5]}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
    
</list:data>
