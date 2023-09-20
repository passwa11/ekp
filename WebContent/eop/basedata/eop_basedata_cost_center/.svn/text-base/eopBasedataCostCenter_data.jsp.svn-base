<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataCostCenter" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataCostCenter.fdName')}" />
        <list:data-column col="fdCompanyName" title="${lfn:message('eop-basedata:eopBasedataCostCenter.fdCompanyList')}" >
        	<c:forEach items="${eopBasedataCostCenter.fdCompanyList}" var="fdCompany" varStatus="status">
        		<c:if test="${status.index!=0}">;</c:if>
        		${fdCompany.fdName}
        	</c:forEach>
        </list:data-column>
        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataCostCenter.fdCode')}" />
        <list:data-column col="fdIsGroup.name" title="${lfn:message('eop-basedata:eopBasedataCostCenter.fdIsGroup')}">
            <sunbor:enumsShow value="${eopBasedataCostCenter.fdIsGroup}" enumsType="eop_basedata_cost_type" />
        </list:data-column>
        <list:data-column col="fdIsGroup">
            <c:out value="${eopBasedataCostCenter.fdIsGroup}" />
        </list:data-column>
        <list:data-column col="fdType.name" title="${lfn:message('eop-basedata:eopBasedataCostCenter.fdType')}" escape="false">
            <c:out value="${eopBasedataCostCenter.fdType.fdName}" />
        </list:data-column>
        <list:data-column col="fdType.id" escape="false">
            <c:out value="${eopBasedataCostCenter.fdType.fdId}" />
        </list:data-column>
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('eop-basedata:eopBasedataCostCenter.fdIsAvailable')}">
            <sunbor:enumsShow value="${eopBasedataCostCenter.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdOrder" title="${lfn:message('eop-basedata:eopBasedataCostCenter.fdOrder')}">
            <c:out value="${eopBasedataCostCenter.fdOrder}" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${eopBasedataCostCenter.fdIsAvailable}" />
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('eop-basedata:eopBasedataCostCenter.docCreator')}" escape="false">
            <c:out value="${eopBasedataCostCenter.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${eopBasedataCostCenter.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('eop-basedata:eopBasedataCostCenter.docCreateTime')}">
            <kmss:showDate value="${eopBasedataCostCenter.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/eop/basedata/eop_basedata_cost_center/eopBasedataCostCenter.do?method=add">
						<!-- 编辑 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.edit('${eopBasedataCostCenter.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/eop/basedata/eop_basedata_cost_center/eopBasedataCostCenter.do?method=deleteall">
						<!-- 删除 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.deleteDoc('${eopBasedataCostCenter.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
