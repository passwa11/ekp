<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="sysUnitDataCenter" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdAppkey" title="${lfn:message('sys-unit:sysUnitDataCenter.fdAppkey')}" />
        <list:data-column property="fdName" title="${lfn:message('sys-unit:sysUnitDataCenter.fdName')}" />
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('sys-unit:sysUnitDataCenter.fdIsAvailable')}">
            <sunbor:enumsShow value="${sysUnitDataCenter.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${sysUnitDataCenter.fdIsAvailable}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('sys-unit:sysUnitDataCenter.docCreateTime')}">
            <kmss:showDate value="${sysUnitDataCenter.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="operations" title="${ lfn:message('list.operation') }" escape="false">
        	<div class="conf_show_more_w">
        		<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/unit/sys_unit_data_center/sysUnitDataCenter.do?method=edit&fdId=${sysUnitDataCenter.fdId}">
        				<a class="btn_txt" href="javascript:edit('${sysUnitDataCenter.fdId}')">${lfn:message('button.edit')}</a>
        			</kmss:auth>
        		</div>
        	</div>
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
