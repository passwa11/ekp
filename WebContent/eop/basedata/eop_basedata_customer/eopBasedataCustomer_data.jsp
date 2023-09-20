<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataCustomer" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataCustomer.fdCode')}" />
        <list:data-column col="fdCompanyName" title="${lfn:message('eop-basedata:eopBasedataCustomer.fdCompanyList')}" >
        	<c:forEach items="${eopBasedataCustomer.fdCompanyList}" var="fdCompany" varStatus="status">
        		<c:if test="${status.index!=0}">;</c:if>
        		${fdCompany.fdName}
        	</c:forEach>
        </list:data-column>
        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataCustomer.fdName')}" />
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('eop-basedata:eopBasedataCustomer.fdIsAvailable')}">
            <sunbor:enumsShow value="${eopBasedataCustomer.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${eopBasedataCustomer.fdIsAvailable}" />
        </list:data-column>
        <list:data-column property="fdAbbreviation" title="${lfn:message('eop-basedata:eopBasedataCustomer.fdAbbreviation')}" />
        <list:data-column property="fdTaxNo" title="${lfn:message('eop-basedata:eopBasedataCustomer.fdTaxNo')}" />
        <list:data-column property="fdUser.fdName" title="${lfn:message('eop-basedata:eopBasedataCustomer.fdUser')}" />
        <list:data-column col="docCreateTime" title="${lfn:message('eop-basedata:eopBasedataCustomer.docCreateTime')}">
            <kmss:showDate value="${eopBasedataCustomer.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/eop/basedata/eop_basedata_customer/eopBasedataCustomer.do?method=add">
						<!-- 编辑 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.edit('${eopBasedataCustomer.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/eop/basedata/eop_basedata_customer/eopBasedataCustomer.do?method=deleteall">
						<!-- 删除 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.deleteDoc('${eopBasedataCustomer.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
