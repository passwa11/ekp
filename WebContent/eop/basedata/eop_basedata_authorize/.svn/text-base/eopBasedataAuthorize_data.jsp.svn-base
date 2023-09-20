<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataAuthorize" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="fdAuthorizedBy.name" title="${lfn:message('eop-basedata:eopBasedataAuthorize.fdAuthorizedBy')}" escape="false">
            <c:out value="${eopBasedataAuthorize.fdAuthorizedBy.fdName}" />
        </list:data-column>
        <list:data-column col="fdAuthorizedBy.id" escape="false">
            <c:out value="${eopBasedataAuthorize.fdAuthorizedBy.fdId}" />
        </list:data-column>
        <list:data-column col="fdToOrg.name" title="${lfn:message('eop-basedata:eopBasedataAuthorize.fdToOrg')}" escape="false">
        	<c:set var="toPersonName"></c:set>
            <c:forEach items="${eopBasedataAuthorize.fdToOrg}" var="org">
            	<c:set var="toPersonName" value="${toPersonName}${org.fdName};"></c:set>
            </c:forEach>
            <c:if test="${not empty  toPersonName}">
            	<c:set var="toPersonName" value="${fn:substring(toPersonName, 0, fn:length(toPersonName)-1)}"></c:set>
            </c:if>
            ${toPersonName}
        </list:data-column>
        <list:data-column property="fdDesc" title="${lfn:message('eop-basedata:eopBasedataAuthorize.fdDesc')}" />
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('eop-basedata:eopBasedataAuthorize.fdIsAvailable')}">
            <sunbor:enumsShow value="${eopBasedataAuthorize.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${eopBasedataAuthorize.fdIsAvailable}" />
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('eop-basedata:eopBasedataAuthorize.docCreator')}" escape="false">
            <c:out value="${eopBasedataAuthorize.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${eopBasedataAuthorize.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('eop-basedata:eopBasedataAuthorize.docCreateTime')}">
            <kmss:showDate value="${eopBasedataAuthorize.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/eop/basedata/eop_basedata_authorize/eopBasedataAuthorize.do?method=add">
						<!-- 编辑 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.edit('${eopBasedataAuthorize.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/eop/basedata/eop_basedata_authorize/eopBasedataAuthorize.do?method=deleteall">
						<!-- 删除 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.deleteDoc('${eopBasedataAuthorize.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
