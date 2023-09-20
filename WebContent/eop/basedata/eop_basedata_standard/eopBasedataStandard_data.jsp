<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataStandard" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
		<list:data-column col="fdCompanyName" title="${lfn:message('eop-basedata:eopBasedataStandard.fdCompanyList')}" >
        	<c:forEach items="${eopBasedataStandard.fdCompanyList}" var="fdCompany" varStatus="status">
        		<c:if test="${status.index!=0}">;</c:if>
        		${fdCompany.fdName}
        	</c:forEach>
        </list:data-column>
		<list:data-column col="fdPerson.name" title="${lfn:message('eop-basedata:eopBasedataStandard.fdPerson')}" escape="false">
            <c:out value="${eopBasedataStandard.fdPerson.fdName}" />
        </list:data-column>
        <list:data-column col="fdPerson.id" escape="false">
            <c:out value="${eopBasedataStandard.fdPerson.fdId}" />
        </list:data-column>
        <list:data-column col="fdLevel.name" title="${lfn:message('eop-basedata:eopBasedataStandard.fdLevel')}" escape="false">
            <c:out value="${eopBasedataStandard.fdLevel.fdName}" />
        </list:data-column>
        <list:data-column col="fdLevel.id" escape="false">
            <c:out value="${eopBasedataStandard.fdLevel.fdId}" />
        </list:data-column>
        <list:data-column col="fdArea.name" title="${lfn:message('eop-basedata:eopBasedataStandard.fdArea')}" escape="false">
            <c:out value="${eopBasedataStandard.fdArea.fdArea}" />
        </list:data-column>
        <list:data-column col="fdArea.id" escape="false">
            <c:out value="${eopBasedataStandard.fdArea.fdId}" />
        </list:data-column>
        <list:data-column col="fdVehicle.name" title="${lfn:message('eop-basedata:eopBasedataStandard.fdVehicle')}" escape="false">
            <c:out value="${eopBasedataStandard.fdVehicle.fdName}" />
        </list:data-column>
        <list:data-column col="fdVehicle.id" escape="false">
            <c:out value="${eopBasedataStandard.fdVehicle.fdId}" />
        </list:data-column>
        <list:data-column col="fdBerth.name" title="${lfn:message('eop-basedata:eopBasedataStandard.fdBerth')}" escape="false">
            <c:out value="${eopBasedataStandard.fdBerth.fdName}" />
        </list:data-column>
        <list:data-column col="fdBerth.id" escape="false">
            <c:out value="${eopBasedataStandard.fdBerth.fdId}" />
        </list:data-column>
        <list:data-column col="fdItem.name" title="${lfn:message('eop-basedata:eopBasedataStandard.fdItem')}" escape="false">
            <c:out value="${eopBasedataStandard.fdItem.fdName}" />
        </list:data-column>
        <list:data-column col="fdItem.id" escape="false">
            <c:out value="${eopBasedataStandard.fdItem.fdId}" />
        </list:data-column>
        <list:data-column property="fdMoney" title="${lfn:message('eop-basedata:eopBasedataStandard.fdMoney')}" />
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('eop-basedata:eopBasedataStandard.fdIsAvailable')}">
            <sunbor:enumsShow value="${eopBasedataStandard.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${eopBasedataStandard.fdIsAvailable}" />
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('eop-basedata:eopBasedataStandard.docCreator')}" escape="false">
            <c:out value="${eopBasedataStandard.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${eopBasedataStandard.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('eop-basedata:eopBasedataStandard.docCreateTime')}">
            <kmss:showDate value="${eopBasedataStandard.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/eop/basedata/eop_basedata_standard/eopBasedataStandard.do?method=add">
						<!-- 编辑 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.edit('${eopBasedataStandard.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/eop/basedata/eop_basedata_standard/eopBasedataStandard.do?method=deleteall">
						<!-- 删除 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.deleteDoc('${eopBasedataStandard.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
