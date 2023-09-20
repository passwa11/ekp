<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataSupplier" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        <kmss:ifModuleExist path="/fssc/common">
        <list:data-column col="fdCompanyName" title="${lfn:message('eop-basedata:eopBasedataSupplier.fdCompanyList')}" >
        	<c:forEach items="${eopBasedataSupplier.fdCompanyList}" var="fdCompany" varStatus="status">
        		<c:if test="${status.index!=0}">;</c:if>
        		${fdCompany.fdName}
        	</c:forEach>
        </list:data-column>
        </kmss:ifModuleExist>
        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataSupplier.fdCode')}" />
        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataSupplier.fdName')}" />
        <list:data-column property="contactName" title="${lfn:message('eop-basedata:eopBasedataContact.fdName')}" />
        <list:data-column property="contactPhone" title="${lfn:message('eop-basedata:eopBasedataContact.fdPhone')}" />
        <list:data-column property="contactEmail" title="${lfn:message('eop-basedata:eopBasedataContact.fdEmail')}" />
        <list:data-column property="fdAbbreviation" title="${lfn:message('eop-basedata:eopBasedataSupplier.fdAbbreviation')}" />
        <list:data-column property="fdIsAvailable" title="${lfn:message('eop-basedata:eopBasedataSupplier.fdIsAvailable')}" />
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('eop-basedata:eopBasedataSupplier.fdIsAvailable')}">
        	<sunbor:enumsShow enumsType="common_yesno" value="${eopBasedataSupplier.fdIsAvailable}"></sunbor:enumsShow>
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('eop-basedata:eopBasedataSupplier.docCreateTime')}">
            <kmss:showDate value="${eopBasedataSupplier.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/eop/basedata/eop_basedata_supplier/eopBasedataSupplier.do?method=add">
						<!-- 编辑 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.edit('${eopBasedataSupplier.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/eop/basedata/eop_basedata_supplier/eopBasedataSupplier.do?method=deleteall">
						<!-- 删除 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.deleteDoc('${eopBasedataSupplier.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
