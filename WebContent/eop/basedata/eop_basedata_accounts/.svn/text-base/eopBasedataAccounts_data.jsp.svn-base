<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataAccounts" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataAccounts.fdName')}" />
        <list:data-column col="fdCompanyName" title="${lfn:message('eop-basedata:eopBasedataAccounts.fdCompanyList')}" >
        	<c:forEach items="${eopBasedataAccounts.fdCompanyList}" var="fdCompany" varStatus="status">
        		<c:if test="${status.index!=0}">;</c:if>
        		${fdCompany.fdName}
        	</c:forEach>
        </list:data-column>
        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataAccounts.fdCode')}" />
        <list:data-column col="fdParent.name" title="${lfn:message('eop-basedata:eopBasedataAccounts.fdParent')}" escape="false">
            <c:out value="${eopBasedataAccounts.fdParent.fdName}" />
        </list:data-column>
        <list:data-column col="fdParent.id" escape="false">
            <c:out value="${eopBasedataAccounts.fdParent.fdId}" />
        </list:data-column>
        <list:data-column col="fdCostItem.name" title="${lfn:message('eop-basedata:eopBasedataAccounts.fdCostItem')}">
            <c:set value="${ fn:split(eopBasedataAccounts.fdCostItem, ';') }" var="array"></c:set>
        	<c:forEach items="${array}" var="fdCostItem" varStatus="status">
        		<c:if test="${status.index!=0}">;</c:if>
        		<sunbor:enumsShow value="${fdCostItem}" enumsType="eop_basedata_cost_item" />
        	</c:forEach>
        </list:data-column>
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('eop-basedata:eopBasedataAccounts.fdIsAvailable')}">
            <sunbor:enumsShow value="${eopBasedataAccounts.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${eopBasedataAccounts.fdIsAvailable}" />
        </list:data-column>
        <list:data-column col="fdCostItem">
            <c:out value="${eopBasedataAccounts.fdCostItem}" />
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('eop-basedata:eopBasedataAccounts.docCreator')}" escape="false">
            <c:out value="${eopBasedataAccounts.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${eopBasedataAccounts.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('eop-basedata:eopBasedataAccounts.docCreateTime')}">
            <kmss:showDate value="${eopBasedataAccounts.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/eop/basedata/eop_basedata_accounts/eopBasedataAccounts.do?method=add">
						<!-- 编辑 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.edit('${eopBasedataAccounts.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/eop/basedata/eop_basedata_accounts/eopBasedataAccounts.do?method=deleteall">
						<!-- 删除 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.deleteDoc('${eopBasedataAccounts.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
