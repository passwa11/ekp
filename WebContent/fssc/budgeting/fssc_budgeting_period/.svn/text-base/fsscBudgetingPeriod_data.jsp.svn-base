<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscBudgetingPeriod" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('fssc-budgeting:fsscBudgetingPeriod.fdName')}" />
        <list:data-column property="fdStartPeriod" title="${lfn:message('fssc-budgeting:fsscBudgetingPeriod.fdStartPeriod')}" />
        <list:data-column property="fdEndPeriod" title="${lfn:message('fssc-budgeting:fsscBudgetingPeriod.fdEndPeriod')}" />
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('fssc-budgeting:fsscBudgetingPeriod.fdIsAvailable')}">
            <sunbor:enumsShow value="${fsscBudgetingPeriod.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${fsscBudgetingPeriod.fdIsAvailable}" />
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('fssc-budgeting:fsscBudgetingPeriod.docCreator')}" escape="false">
            <c:out value="${fsscBudgetingPeriod.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${fsscBudgetingPeriod.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('fssc-budgeting:fsscBudgetingPeriod.docCreateTime')}">
            <kmss:showDate value="${fsscBudgetingPeriod.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/fssc/budgeting/fssc_budgeting_period/fsscBudgetingPeriod.do?method=add">
						<!-- 编辑 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.edit('${fsscBudgetingPeriod.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/fssc/budgeting/fssc_budgeting_period/fsscBudgetingPeriod.do?method=deleteall">
						<!-- 删除 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.deleteDoc('${fsscBudgetingPeriod.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
