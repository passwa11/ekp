<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataItemAccount" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
		<list:data-column col="fdCompanyName" title="${lfn:message('eop-basedata:eopBasedataItemAccount.fdCompanyList')}" >
        	<c:forEach items="${eopBasedataItemAccount.fdCompanyList}" var="fdCompany" varStatus="status">
        		<c:if test="${status.index!=0}">;</c:if>
        		${fdCompany.fdName}
        	</c:forEach>
        </list:data-column>
        <list:data-column col="fdExpenseItem.name" title="${lfn:message('eop-basedata:eopBasedataItemAccount.fdExpenseItem')}" escape="false">
            <c:out value="${eopBasedataItemAccount.fdExpenseItem.fdName}" />
        </list:data-column>
        <list:data-column col="fdExpenseItem.id" escape="false">
            <c:out value="${eopBasedataItemAccount.fdExpenseItem.fdId}" />
        </list:data-column>
        <list:data-column col="fdAmortize.name" title="${lfn:message('eop-basedata:eopBasedataItemAccount.fdAmortize')}" escape="false">
            <c:out value="${eopBasedataItemAccount.fdAmortize.fdName}" />
        </list:data-column>
        <list:data-column col="fdAmortize.id" escape="false">
            <c:out value="${eopBasedataItemAccount.fdAmortize.fdId}" />
        </list:data-column>
        <list:data-column col="fdAccruals.name" title="${lfn:message('eop-basedata:eopBasedataItemAccount.fdAccruals')}" escape="false">
            <c:out value="${eopBasedataItemAccount.fdAccruals.fdName}" />
        </list:data-column>
        <list:data-column col="fdAccruals.id" escape="false">
            <c:out value="${eopBasedataItemAccount.fdAccruals.fdId}" />
        </list:data-column>
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('eop-basedata:eopBasedataItemAccount.fdIsAvailable')}">
            <sunbor:enumsShow value="${eopBasedataItemAccount.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${eopBasedataItemAccount.fdIsAvailable}" />
        </list:data-column>
        <list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/eop/basedata/eop_basedata_item_account/eopBasedataItemAccount.do?method=add">
						<!-- 编辑 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.edit('${eopBasedataItemAccount.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/eop/basedata/eop_basedata_item_account/eopBasedataItemAccount.do?method=deleteall">
						<!-- 删除 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.deleteDoc('${eopBasedataItemAccount.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
