<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="kmArchivesDense" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        
		<list:data-column headerClass="width30" property="fdOrder" title="${lfn:message('km-archives:kmArchivesDense.fdOrder')}" />
        <list:data-column property="fdName" title="${lfn:message('km-archives:kmArchivesDense.fdName')}" />
        <list:data-column col="docCreator.fdName" title="${lfn:message('km-archives:kmArchivesDense.docCreator')}" escape="false">
            <c:out value="${kmArchivesDense.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.fdId" escape="false">
            <c:out value="${kmArchivesDense.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('km-archives:kmArchivesDense.docCreateTime')}">
            <kmss:showDate value="${kmArchivesDense.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <!-- 其它操作 -->
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/km/archives/km_archives_dense/kmArchivesDense.do?method=edit&fdId=${kmArchivesDense.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:editDoc('${kmArchivesDense.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/km/archives/km_archives_dense/kmArchivesDense.do?method=delete&fdId=${kmArchivesDense.fdId}" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:deleteDoc('${kmArchivesDense.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
