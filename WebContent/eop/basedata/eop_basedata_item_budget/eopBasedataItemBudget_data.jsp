<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataItemBudget" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        <list:data-column col="fdCompanyName" title="${lfn:message('eop-basedata:eopBasedataItemBudget.fdCompanyList')}" >
        	<c:forEach items="${eopBasedataItemBudget.fdCompanyList}" var="fdCompany" varStatus="status">
        		<c:if test="${status.index!=0}">;</c:if>
        		${fdCompany.fdName}
        	</c:forEach>
        </list:data-column>
		<list:data-column col="fdItems" title="${lfn:message('eop-basedata:eopBasedataItemBudget.fdItems') }">
            <c:forEach items="${eopBasedataItemBudget.fdItems }" var="item">
            	${item.fdName};
            </c:forEach>
        </list:data-column>
        <list:data-column col="fdOrgs" title="${lfn:message('eop-basedata:eopBasedataItemBudget.fdOrgs') }">
            <c:forEach items="${eopBasedataItemBudget.fdOrgs }" var="item">
            	${item.fdName};
            </c:forEach>
        </list:data-column>
        <list:data-column col="fdCategory" title="${lfn:message('eop-basedata:eopBasedataItemBudget.fdCategory') }">
            ${eopBasedataItemBudget.fdCategory.fdName }
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${eopBasedataItemBudget.fdIsAvailable}" />
        </list:data-column>
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('eop-basedata:eopBasedataProvince.fdIsAvailable')}">
            <sunbor:enumsShow value="${eopBasedataItemBudget.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/eop/basedata/eop_basedata_item_budget/eopBasedataItemBudget.do?method=add">
						<!-- 编辑 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.edit('${eopBasedataItemBudget.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/eop/basedata/eop_basedata_item_budget/eopBasedataItemBudget.do?method=deleteall">
						<!-- 删除 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.deleteDoc('${eopBasedataItemBudget.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
