<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="modelingAppSpace" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        <list:data-column col="fdName"  title="${lfn:message('sys-modeling-base:modelingAppSpace.fdName')}">
            <c:out value="${modelingAppSpace.fdName}" />
        </list:data-column>

        <list:data-column col="docCreator.name" title="${lfn:message('sys-modeling-base:modelingAppNav.docCreator')}" escape="false">
            <c:out value="${modelingAppSpace.docCreator.fdName}" />
        </list:data-column>

        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${modelingAppSpace.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('sys-modeling-base:modelingAppNav.docCreateTime')}">
            <kmss:showDate value="${modelingAppSpace.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <!-- 其它操作 -->
        <list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
            <!--操作按钮 开始-->
            <div class="conf_show_more_w">
                <div class="conf_btn_edit">
                    <!-- 编辑 -->
                    <a class="btn_txt" href="javascript:edit('${modelingAppSpace.fdId}')">${lfn:message('button.edit')}</a>
                    <!-- 删除 -->
                    <a class="btn_txt" href="javascript:deletePc('${modelingAppSpace.fdId}')">${lfn:message('button.delete')}</a>
                </div>
            </div>
            <!--操作按钮 结束-->
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
