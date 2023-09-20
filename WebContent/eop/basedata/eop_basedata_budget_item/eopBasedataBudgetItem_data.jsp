<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataBudgetItem" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataBudgetItem.fdName')}" />
        <list:data-column col="fdCompanyName" title="${lfn:message('eop-basedata:eopBasedataBudgetItem.fdCompanyList')}" >
        	<c:forEach items="${eopBasedataBudgetItem.fdCompanyList}" var="fdCompany" varStatus="status">
        		<c:if test="${status.index!=0}">;</c:if>
        		${fdCompany.fdName}
        	</c:forEach>
        </list:data-column>
        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataBudgetItem.fdCode')}" />
        <list:data-column col="fdParent.name" title="${lfn:message('eop-basedata:eopBasedataBudgetItem.fdParent')}" escape="false">
            <c:out value="${eopBasedataBudgetItem.fdParent.fdName}" />
        </list:data-column>
        <list:data-column col="fdParent.id" escape="false">
            <c:out value="${eopBasedataBudgetItem.fdParent.fdId}" />
        </list:data-column>
         <list:data-column col="fdIsAvailable.name" title="${lfn:message('eop-basedata:eopBasedataBudgetItem.fdIsAvailable')}">
            <sunbor:enumsShow value="${eopBasedataBudgetItem.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${eopBasedataBudgetItem.fdIsAvailable}" />
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('eop-basedata:eopBasedataBudgetItem.docCreator')}" escape="false">
            <c:out value="${eopBasedataBudgetItem.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${eopBasedataBudgetItem.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('eop-basedata:eopBasedataBudgetItem.docCreateTime')}">
            <kmss:showDate value="${eopBasedataBudgetItem.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/eop/basedata/eop_basedata_budget_item/eopBasedataBudgetItem.do?method=add">
						<!-- 编辑 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.edit('${eopBasedataBudgetItem.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/eop/basedata/eop_basedata_budget_item/eopBasedataBudgetItem.do?method=deleteall">
						<!-- 删除 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.deleteDoc('${eopBasedataBudgetItem.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>