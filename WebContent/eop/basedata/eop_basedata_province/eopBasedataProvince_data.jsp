<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataProvince" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataProvince.fdName')}" />
        <list:data-column col="fdCompanyName" title="${lfn:message('eop-basedata:eopBasedataProvince.fdCompanyList')}" >
        	<c:forEach items="${eopBasedataProvince.fdCompanyList}" var="fdCompany" varStatus="status">
        		<c:if test="${status.index!=0}">;</c:if>
        		${fdCompany.fdName}
        	</c:forEach>
        </list:data-column>
        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataProvince.fdCode')}" />
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('eop-basedata:eopBasedataProvince.fdIsAvailable')}">
            <sunbor:enumsShow value="${eopBasedataProvince.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${eopBasedataProvince.fdIsAvailable}" />
        </list:data-column>
        <list:data-column col="fdCountry.name" title="${lfn:message('eop-basedata:eopBasedataProvince.fdCountry')}" escape="false">
            <c:out value="${eopBasedataProvince.fdCountry.fdName}" />
        </list:data-column>
        <list:data-column col="fdCountry.id" escape="false">
            <c:out value="${eopBasedataProvince.fdCountry.fdId}" />
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('eop-basedata:eopBasedataProvince.docCreator')}" escape="false">
            <c:out value="${eopBasedataProvince.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${eopBasedataProvince.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('eop-basedata:eopBasedataProvince.docCreateTime')}">
            <kmss:showDate value="${eopBasedataProvince.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="docAlteror.name" title="${lfn:message('eop-basedata:eopBasedataProvince.docAlteror')}" escape="false">
            <c:out value="${eopBasedataProvince.docAlteror.fdName}" />
        </list:data-column>
        <list:data-column col="docAlteror.id" escape="false">
            <c:out value="${eopBasedataProvince.docAlteror.fdId}" />
        </list:data-column>
        <list:data-column col="docAlterTime" title="${lfn:message('eop-basedata:eopBasedataProvince.docAlterTime')}">
            <kmss:showDate value="${eopBasedataProvince.docAlterTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/eop/basedata/eop_basedata_province/eopBasedataProvince.do?method=add">
						<!-- 编辑 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.edit('${eopBasedataProvince.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/eop/basedata/eop_basedata_province/eopBasedataProvince.do?method=deleteall">
						<!-- 删除 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.deleteDoc('${eopBasedataProvince.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
