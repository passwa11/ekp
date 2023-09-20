<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="sysPortalMapTpl" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('sys-portal:sysPortalMapTpl.fdName')}" />
        <list:data-column col="docCreator.name" title="${lfn:message('sys-portal:sysPortalMapTpl.docCreator')}" escape="false">
            <c:out value="${sysPortalMapTpl.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${sysPortalMapTpl.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('sys-portal:sysPortalMapTpl.docCreateTime')}">
            <kmss:showDate value="${sysPortalMapTpl.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column headerClass="width80" col="operations" title="${ lfn:message('list.operation') }" escape="false">
        	<div class="conf_show_more_w">
        		<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/portal/sys_portal_map_tpl/sysPortalMapTpl.do?method=edit&fdId=${sysPortalMapTpl.fdId}" requestMethod="GET">
						<a class="btn_txt" href="javascript:edit('${sysPortalMapTpl.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/portal/sys_portal_map_tpl/sysPortalMapTpl.do?method=delete&fdId=${sysPortalMapTpl.fdId}" requestMethod="POST">
						<a class="btn_txt" href="javascript:deleteById('${sysPortalMapTpl.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
   			</div>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
