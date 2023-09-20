<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataInputTax" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
		<list:data-column col="fdCompanyName" title="${lfn:message('eop-basedata:eopBasedataInputTax.fdCompanyList')}" >
        	<c:forEach items="${eopBasedataInputTax.fdCompanyList}" var="fdCompany" varStatus="status">
        		<c:if test="${status.index!=0}">;</c:if>
        		${fdCompany.fdName}
        	</c:forEach>
        </list:data-column>
        <list:data-column col="fdItem.name" title="${lfn:message('eop-basedata:eopBasedataInputTax.fdItem')}" escape="false">
            <c:out value="${eopBasedataInputTax.fdItem.fdName}" />
        </list:data-column>
        <list:data-column col="fdItem.id" escape="false">
            <c:out value="${eopBasedataInputTax.fdItem.fdId}" />
        </list:data-column>
        <list:data-column col="fdIsInputTax.name" title="${lfn:message('eop-basedata:eopBasedataInputTax.fdIsInputTax')}">
            <sunbor:enumsShow value="${eopBasedataInputTax.fdIsInputTax}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsInputTax">
            <c:out value="${eopBasedataInputTax.fdIsInputTax}" />
        </list:data-column>
        <list:data-column property="fdTaxRate" title="${lfn:message('eop-basedata:eopBasedataInputTax.fdTaxRate')}" />
        <list:data-column col="fdAccount.name" title="${lfn:message('eop-basedata:eopBasedataInputTax.fdAccount')}" escape="false">
            <c:out value="${eopBasedataInputTax.fdAccount.fdName}" />
        </list:data-column>
        <list:data-column col="fdAccount.id" escape="false">
            <c:out value="${eopBasedataInputTax.fdAccount.fdId}" />
        </list:data-column>
        <list:data-column col="docAlterTime" title="${lfn:message('eop-basedata:eopBasedataInputTax.docAlterTime')}">
            <kmss:showDate value="${eopBasedataInputTax.docAlterTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/eop/basedata/eop_basedata_input_tax/eopBasedataInputTax.do?method=add">
						<!-- 编辑 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.edit('${eopBasedataInputTax.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/eop/basedata/eop_basedata_input_tax/eopBasedataInputTax.do?method=deleteall">
						<!-- 删除 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.deleteDoc('${eopBasedataInputTax.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
