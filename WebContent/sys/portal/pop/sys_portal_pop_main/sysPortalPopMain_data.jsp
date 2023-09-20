<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="sysPortalPopMain" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="docSubject" title="${lfn:message('sys-portal:sysPortalPopMain.docSubject')}" />
        <list:data-column col="fdCategory.name" title="${lfn:message('sys-portal:sysPortalPopMain.fdCategory')}" escape="false">
            <c:out value="${sysPortalPopMain.fdCategory.fdName}" />
        </list:data-column>
        <list:data-column col="fdCategory.id" escape="false">
            <c:out value="${sysPortalPopMain.fdCategory.fdId}" />
        </list:data-column>
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('sys-portal:sysPortalPopMain.fdIsAvailable')}">
            <sunbor:enumsShow value="${sysPortalPopMain.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${sysPortalPopMain.fdIsAvailable}" />
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('sys-portal:sysPortalPopMain.docCreator')}" escape="false">
            <c:out value="${sysPortalPopMain.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${sysPortalPopMain.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('sys-portal:sysPortalPopMain.docCreateTime')}">
            <kmss:showDate value="${sysPortalPopMain.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        
		<!-- 其它操作 -->
		<list:data-column headerClass="width80" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/portal/pop/sys_portal_pop_main/sysPortalPopMain.do?method=edit&fdId=${sysPortalPopMain.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:editPop('${sysPortalPopMain.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/portal/pop/sys_portal_pop_main/sysPortalPopMain.do?method=delete&fdId=${sysPortalPopMain.fdId}" requestMethod="GET">
					    <!-- 删除-->
						<a class="btn_txt" href="javascript:deletePop('${sysPortalPopMain.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth> 
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
        
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
