<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataStandardScheme" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
		<list:data-column col="fdCompanyName" title="${lfn:message('eop-basedata:eopBasedataStandardScheme.fdCompanyList')}" >
        	<c:forEach items="${eopBasedataStandardScheme.fdCompanyList}" var="fdCompany" varStatus="status">
        		<c:if test="${status.index!=0}">;</c:if>
        		${fdCompany.fdName}
        	</c:forEach>
        </list:data-column>
        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataStandardScheme.fdName')}" />
        <list:data-column col="fdDimension.name" title="${lfn:message('eop-basedata:eopBasedataStandardScheme.fdDimension')}">
        	<c:set value="${ fn:split(eopBasedataStandardScheme.fdDimension, ';') }" var="array"></c:set>
        	<c:forEach items="${array}" var="fdDimension" varStatus="status">
        		<c:if test="${status.index!=0}">+</c:if>
        		<sunbor:enumsShow value="${fdDimension}" enumsType="eop_basedata_standard_dimension" />
        	</c:forEach>
        </list:data-column>
        <list:data-column col="fdDimension">
            <c:out value="${eopBasedataStandardScheme.fdDimension}" />
        </list:data-column>
        <list:data-column col="fdItems.name" title="${lfn:message('eop-basedata:eopBasedataStandardScheme.fdItems')}" escape="false">
           <c:forEach items="${eopBasedataStandardScheme.fdItems}" var="fdItem" varStatus="status">
        		<c:if test="${status.index!=0}">;</c:if>
        		<c:out value="${fdItem.fdName}"></c:out>
        	</c:forEach>
        </list:data-column>
        <list:data-column col="fdType.name" title="${lfn:message('eop-basedata:eopBasedataStandardScheme.fdType')}">
            <sunbor:enumsShow value="${eopBasedataStandardScheme.fdType}" enumsType="eop_basedata_standard_type" />
        </list:data-column>
        <list:data-column col="fdType">
            <c:out value="${eopBasedataStandardScheme.fdType}" />
        </list:data-column>
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('eop-basedata:eopBasedataStandardScheme.fdIsAvailable')}">
            <sunbor:enumsShow value="${eopBasedataStandardScheme.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${eopBasedataStandardScheme.fdIsAvailable}" />
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('eop-basedata:eopBasedataStandardScheme.docCreator')}" escape="false">
            <c:out value="${eopBasedataStandardScheme.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${eopBasedataStandardScheme.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('eop-basedata:eopBasedataStandardScheme.docCreateTime')}">
            <kmss:showDate value="${eopBasedataStandardScheme.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/eop/basedata/eop_basedata_standard_scheme/eopBasedataStandardScheme.do?method=add">
						<!-- 编辑 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.edit('${eopBasedataStandardScheme.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/eop/basedata/eop_basedata_standard_scheme/eopBasedataStandardScheme.do?method=deleteall">
						<!-- 删除 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.deleteDoc('${eopBasedataStandardScheme.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
