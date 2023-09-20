<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="sysUnitDataCenterUnit" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdUnitCode" title="${lfn:message('sys-unit:sysUnitDataCenterUnit.fdUnitCode')}" />

        <list:data-column col="fdIsAvailable" title="${lfn:message('sys-unit:sysUnitDataCenterUnit.fdIsAvailable')}">
            <sunbor:enumsShow value="${sysUnitDataCenterUnit.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="docCreator" title="${lfn:message('sys-unit:sysUnitDataCenterUnit.docCreator')}">
            <c:out value="${sysUnitDataCenterUnit.docCreator.fdName}"/>
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('sys-unit:sysUnitDataCenterUnit.docCreateTime')}">
            <kmss:showDate value="${sysUnitDataCenterUnit.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="operations" title="${ lfn:message('list.operation') }" escape="false">
        	<div class="conf_show_more_w">
        		<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/unit/sys_unit_data_center/sysUnitDataCenterUnit.do?method=edit&fdId=${sysUnitDataCenterUnit.fdId}">
        				<a class="btn_txt" href="javascript:edit('${sysUnitDataCenterUnit.fdId}')">${lfn:message('button.edit')}</a>
        			</kmss:auth>
        		</div>
        	</div>
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
