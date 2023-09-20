<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataBudgetScheme" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataBudgetScheme.fdName')}" />
        <list:data-column col="fdDimension.name" title="${lfn:message('eop-basedata:eopBasedataBudgetScheme.fdDimension')}">
        	<c:set value="${ fn:split(eopBasedataBudgetScheme.fdDimension, ';') }" var="array"></c:set>
        	<c:forEach items="${array}" var="fdDimension" varStatus="status">
        		<c:if test="${status.index!=0}">+</c:if>
        		<sunbor:enumsShow value="${fdDimension}" enumsType="eop_basedata_budget_dimension" />
        	</c:forEach>
        </list:data-column>
        <list:data-column col="fdDimension">
            <c:out value="${eopBasedataBudgetScheme.fdDimension}" />
        </list:data-column>
        <list:data-column col="fdType.name" title="${lfn:message('eop-basedata:eopBasedataBudgetScheme.fdType')}">
            <sunbor:enumsShow value="${eopBasedataBudgetScheme.fdType}" enumsType="eop_basedata_dimension_type" />
        </list:data-column>
        <list:data-column col="fdType">
            <c:out value="${eopBasedataBudgetScheme.fdType}" />
        </list:data-column>
       <%--  <list:data-column col="fdTarget.name" title="${lfn:message('eop-basedata:eopBasedataBudgetScheme.fdTarget')}">
            <sunbor:enumsShow value="${eopBasedataBudgetScheme.fdTarget}" enumsType="eop_basedata_dimension_target" />
        </list:data-column>
        <list:data-column col="fdTarget">
            <c:out value="${eopBasedataBudgetScheme.fdTarget}" />
        </list:data-column> --%>
        <list:data-column col="fdPeriod.name" title="${lfn:message('eop-basedata:eopBasedataBudgetScheme.fdPeriod')}">
            <c:set value="${ fn:split(eopBasedataBudgetScheme.fdPeriod, ';') }" var="array"></c:set>
        	<c:forEach items="${array}" var="fdPeriod" varStatus="status">
        		<c:if test="${status.index!=0}">;</c:if>
        		<sunbor:enumsShow value="${fdPeriod}" enumsType="eop_basedata_budget_period" />
        	</c:forEach>
        </list:data-column>
        <list:data-column col="fdPeriod">
            <c:out value="${eopBasedataBudgetScheme.fdPeriod}" />
        </list:data-column>
        <list:data-column col="fdCode" title="${lfn:message('eop-basedata:eopBasedataBudgetScheme.fdCode')}">
            <c:out value="${eopBasedataBudgetScheme.fdCode}" />
        </list:data-column>
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('eop-basedata:eopBasedataBudgetScheme.fdIsAvailable')}">
            <sunbor:enumsShow value="${eopBasedataBudgetScheme.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${eopBasedataBudgetScheme.fdIsAvailable}" />
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('eop-basedata:eopBasedataBudgetScheme.docCreator')}" escape="false">
            <c:out value="${eopBasedataBudgetScheme.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${eopBasedataBudgetScheme.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('eop-basedata:eopBasedataBudgetScheme.docCreateTime')}">
            <kmss:showDate value="${eopBasedataBudgetScheme.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/eop/basedata/eop_basedata_budget_scheme/eopBasedataBudgetScheme.do?method=add">
						<!-- 编辑 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.edit('${eopBasedataBudgetScheme.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/eop/basedata/eop_basedata_budget_scheme/eopBasedataBudgetScheme.do?method=deleteall">
						<!-- 删除 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.deleteDoc('${eopBasedataBudgetScheme.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
