<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataAccount" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataAccount.fdName')}" />
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('eop-basedata:eopBasedataAccount.fdIsAvailable')}">
            <sunbor:enumsShow value="${eopBasedataAccount.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${eopBasedataAccount.fdIsAvailable}" />
        </list:data-column>
        <list:data-column property="fdBankName" title="${lfn:message('eop-basedata:eopBasedataAccount.fdBankName')}" />
        <list:data-column property="fdBankAccount" title="${lfn:message('eop-basedata:eopBasedataAccount.fdBankAccount')}" />
        <list:data-column col="fdPerson.name" title="${lfn:message('eop-basedata:eopBasedataAccount.fdPerson')}" escape="false">
            <c:out value="${eopBasedataAccount.fdPerson.fdName}" />
        </list:data-column>
        <list:data-column col="fdPerson.id" escape="false">
            <c:out value="${eopBasedataAccount.fdPerson.fdId}" />
        </list:data-column>
        <list:data-column col="fdIsDefault.name" title="${lfn:message('eop-basedata:eopBasedataAccount.fdIsDefault')}">
            <sunbor:enumsShow value="${eopBasedataAccount.fdIsDefault}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsDefault">
            <c:out value="${eopBasedataAccount.fdIsDefault}" />
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('eop-basedata:eopBasedataAccount.docCreator')}" escape="false">
            <c:out value="${eopBasedataAccount.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${eopBasedataAccount.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('eop-basedata:eopBasedataAccount.docCreateTime')}">
            <kmss:showDate value="${eopBasedataAccount.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/eop/basedata/eop_basedata_account/eopBasedataAccount.do?method=add">
						<!-- 编辑 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.edit('${eopBasedataAccount.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/eop/basedata/eop_basedata_account/eopBasedataAccount.do?method=deleteall">
						<!-- 删除 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.deleteDoc('${eopBasedataAccount.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
