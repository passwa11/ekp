<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdWeixinChatDataTemp" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdSeq" title="${lfn:message('third-weixin:thirdWeixinChatDataTemp.fdSeq')}" />
        <list:data-column property="fdMsgId" title="${lfn:message('third-weixin:thirdWeixinChatDataTemp.fdMsgId')}" />
        <list:data-column property="fdEncryptRandomKey" title="${lfn:message('third-weixin:thirdWeixinChatDataTemp.fdEncryptRandomKey')}" />
        <list:data-column col="fdHandleStatus.name" title="${lfn:message('third-weixin:thirdWeixinChatDataTemp.fdHandleStatus')}">
            <sunbor:enumsShow value="${thirdWeixinChatDataTemp.fdHandleStatus}" enumsType="third_weixin_chat_data_handle" />
        </list:data-column>
        <list:data-column col="fdHandleStatus">
            <c:out value="${thirdWeixinChatDataTemp.fdHandleStatus}" />
        </list:data-column>
        <list:data-column property="fdErrTimes" title="${lfn:message('third-weixin:thirdWeixinChatDataTemp.fdErrTimes')}" />
        <list:data-column col="docCreateTime" title="${lfn:message('third-weixin:thirdWeixinChatDataTemp.docCreateTime')}">
            <kmss:showDate value="${thirdWeixinChatDataTemp.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="docAlterTime" title="${lfn:message('third-weixin:thirdWeixinChatDataTemp.docAlterTime')}">
            <kmss:showDate value="${thirdWeixinChatDataTemp.docAlterTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
