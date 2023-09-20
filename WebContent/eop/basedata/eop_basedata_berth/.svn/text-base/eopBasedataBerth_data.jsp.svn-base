<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataBerth" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
		<list:data-column col="fdCompanyName" title="${lfn:message('eop-basedata:eopBasedataBerth.fdCompanyList')}" >
        	<c:forEach items="${eopBasedataBerth.fdCompanyList}" var="fdCompany" varStatus="status">
        		<c:if test="${status.index!=0}">;</c:if>
        		${fdCompany.fdName}
        	</c:forEach>
        </list:data-column>
        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataBerth.fdName')}" />
        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataBerth.fdCode')}" />
        <list:data-column col="fdLevel.name" title="${lfn:message('eop-basedata:eopBasedataBerth.fdLevel')}">
            <sunbor:enumsShow value="${eopBasedataBerth.fdLevel}" enumsType="eop_basedata_berth_level" />
        </list:data-column>
        <list:data-column col="fdLevel">
            <c:out value="${eopBasedataBerth.fdLevel}" />
        </list:data-column>
        <list:data-column col="fdVehicle.name" title="${lfn:message('eop-basedata:eopBasedataBerth.fdVehicle')}" escape="false">
            <c:out value="${eopBasedataBerth.fdVehicle.fdName}" />
        </list:data-column>
        <list:data-column col="fdVehicle.id" escape="false">
            <c:out value="${eopBasedataBerth.fdVehicle.fdId}" />
        </list:data-column>
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('eop-basedata:eopBasedataBerth.fdIsAvailable')}">
            <sunbor:enumsShow value="${eopBasedataBerth.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${eopBasedataBerth.fdIsAvailable}" />
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('eop-basedata:eopBasedataBerth.docCreator')}" escape="false">
            <c:out value="${eopBasedataBerth.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${eopBasedataBerth.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('eop-basedata:eopBasedataBerth.docCreateTime')}">
            <kmss:showDate value="${eopBasedataBerth.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/eop/basedata/eop_basedata_berth/eopBasedataBerth.do?method=add">
						<!-- 编辑 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.edit('${eopBasedataBerth.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/eop/basedata/eop_basedata_berth/eopBasedataBerth.do?method=deleteall">
						<!-- 删除 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.deleteDoc('${eopBasedataBerth.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
