<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="modelingFormModifiedLog" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId"/>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        <list:data-column col="docCreator.name"
                          title="${lfn:message('sys-modeling-base:modelingFormModifiedLog.docCreator')}" escape="false">
            <c:out value="${modelingFormModifiedLog.docCreator.fdName}"/>
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${modelingFormModifiedLog.docCreator.fdId}"/>
        </list:data-column>
        <list:data-column col="docCreateTime"
                          title="${lfn:message('sys-modeling-base:modelingFormModifiedLog.docCreateTime')}">
            <kmss:showDate value="${modelingFormModifiedLog.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="modelMain.fdName" title="${lfn:message('sys-modeling-base:modelingFormModifiedLog.modelMain')}">
            <c:out value="${modelingFormModifiedLog.modelMain.fdName}"/>
        </list:data-column>
<%--        <list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }"--%>
<%--                          escape="false">--%>
<%--            <!--操作按钮 开始-->--%>
<%--            <div class="conf_show_more_w">--%>
<%--                <div class="conf_btn_edit">--%>
<%--                    <!-- 编辑 -->--%>
<%--                    <a class="btn_txt"--%>
<%--                       href="javascript:toView('${sysModelingOperation.fdId}')">${lfn:message('button.edit')}</a>--%>
<%--                    <c:if test="${ sysModelingOperation.fdType ne '0' }">--%>
<%--                        <a class="btn_txt"--%>
<%--                           href="javascript:del('${sysModelingOperation.fdId}')">${lfn:message('button.delete')}</a>--%>
<%--                    </c:if>--%>


<%--                </div>--%>
<%--            </div>--%>
<%--            <!--操作按钮 结束-->--%>
<%--        </list:data-column>--%>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}"/>
</list:data>
