<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataVehicle" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
		<list:data-column col="fdCompanyName" title="${lfn:message('eop-basedata:eopBasedataVehicle.fdCompanyList')}" >
        	<c:forEach items="${eopBasedataVehicle.fdCompanyList}" var="fdCompany" varStatus="status">
        		<c:if test="${status.index!=0}">;</c:if>
        		${fdCompany.fdName}
        	</c:forEach>
        </list:data-column>
        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataVehicle.fdName')}" />
        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataVehicle.fdCode')}" />
        <list:data-column col="fdLevel.name" title="${lfn:message('eop-basedata:eopBasedataVehicle.fdLevel')}">
            <sunbor:enumsShow value="${eopBasedataVehicle.fdLevel}" enumsType="eop_basedata_berth_level" />
        </list:data-column>
        <list:data-column col="fdLevel">
            <c:out value="${eopBasedataVehicle.fdLevel}" />
        </list:data-column>
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('eop-basedata:eopBasedataVehicle.fdIsAvailable')}">
            <sunbor:enumsShow value="${eopBasedataVehicle.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${eopBasedataVehicle.fdIsAvailable}" />
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('eop-basedata:eopBasedataVehicle.docCreator')}" escape="false">
            <c:out value="${eopBasedataVehicle.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${eopBasedataVehicle.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('eop-basedata:eopBasedataVehicle.docCreateTime')}">
            <kmss:showDate value="${eopBasedataVehicle.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/eop/basedata/eop_basedata_vehicle/eopBasedataVehicle.do?method=add">
						<!-- 编辑 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.edit('${eopBasedataVehicle.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/eop/basedata/eop_basedata_vehicle/eopBasedataVehicle.do?method=deleteall">
						<!-- 删除 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.deleteDoc('${eopBasedataVehicle.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
