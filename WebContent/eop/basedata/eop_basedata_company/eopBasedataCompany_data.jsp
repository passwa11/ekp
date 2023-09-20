<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<list:data>
    <list:data-columns var="eopBasedataCompany" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataCompany.fdName')}" />
        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataCompany.fdCode')}" />
        <list:data-column property="fdIden" title="${lfn:message('eop-basedata:eopBasedataCompany.fdIden')}" />
        <list:data-column col="fdBudgetCurrency.name" title="${lfn:message('eop-basedata:eopBasedataCompany.fdBudgetCurrency')}" escape="false">
            <c:out value="${eopBasedataCompany.fdBudgetCurrency.fdName}" />
        </list:data-column>
        <list:data-column col="fdBudgetCurrency.id" escape="false">
            <c:out value="${eopBasedataCompany.fdBudgetCurrency.fdId}" />
        </list:data-column>
        <list:data-column col="fdAccountCurrency.name" title="${lfn:message('eop-basedata:eopBasedataCompany.fdAccountCurrency')}" escape="false">
            <c:out value="${eopBasedataCompany.fdAccountCurrency.fdName}" />
        </list:data-column>
        <list:data-column col="fdAccountCurrency.id" escape="false">
            <c:out value="${eopBasedataCompany.fdAccountCurrency.fdId}" />
        </list:data-column>
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('eop-basedata:eopBasedataCompany.fdIsAvailable')}">
            <sunbor:enumsShow value="${eopBasedataCompany.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${eopBasedataCompany.fdIsAvailable}" />
        </list:data-column>
        <list:data-column property="fdJoinSystem" title="${lfn:message('eop-basedata:eopBasedataCompany.fdJoinSystem')}" />
        <list:data-column col="docCreator.name" title="${lfn:message('eop-basedata:eopBasedataCompany.docCreator')}" escape="false">
            <c:out value="${eopBasedataCompany.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${eopBasedataCompany.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('eop-basedata:eopBasedataCompany.docCreateTime')}">
            <kmss:showDate value="${eopBasedataCompany.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <fssc:switchOn property="fdCompanyGroup">
        <list:data-column col="fdGroup.name" title="${lfn:message('eop-basedata:eopBasedataCompany.fdGroup')}" escape="false">
            <c:out value="${eopBasedataCompany.fdGroup.fdName}" />
        </list:data-column>
        <list:data-column col="fdGroup.id" escape="false">
            <c:out value="${eopBasedataCompany.fdGroup.fdId}" />
        </list:data-column>
        </fssc:switchOn>
        <list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/eop/basedata/eop_basedata_company/eopBasedataCompany.do?method=add">
						<!-- 编辑 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.edit('${eopBasedataCompany.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/eop/basedata/eop_basedata_company/eopBasedataCompany.do?method=deleteall">
						<!-- 删除 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.deleteDoc('${eopBasedataCompany.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
