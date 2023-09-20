<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataPayWay" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataPayWay.fdCode')}" />
        <kmss:ifModuleExist path="/fssc/common">
        <list:data-column col="fdCompanyName" title="${lfn:message('eop-basedata:eopBasedataPayWay.fdCompanyList')}" >
        	<c:forEach items="${eopBasedataPayWay.fdCompanyList}" var="fdCompany" varStatus="status">
        		<c:if test="${status.index!=0}">;</c:if>
        		${fdCompany.fdName}
        	</c:forEach>
        </list:data-column>
        </kmss:ifModuleExist>
        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataPayWay.fdName')}" />
        <list:data-column col="fdStatus.name" title="${lfn:message('eop-basedata:eopBasedataPayWay.fdStatus')}">
            <sunbor:enumsShow value="${eopBasedataPayWay.fdStatus}" enumsType="eop_basedata_mate_status" />
        </list:data-column>
        <list:data-column col="fdStatus">
            <c:out value="${eopBasedataPayWay.fdStatus}" />
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('eop-basedata:eopBasedataPayWay.docCreator')}" escape="false">
            <c:out value="${eopBasedataPayWay.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${eopBasedataPayWay.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('eop-basedata:eopBasedataPayWay.docCreateTime')}">
            <kmss:showDate value="${eopBasedataPayWay.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/eop/basedata/eop_basedata_pay_way/eopBasedataPayWay.do?method=add">
						<!-- 编辑 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.edit('${eopBasedataPayWay.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/eop/basedata/eop_basedata_pay_way/eopBasedataPayWay.do?method=deleteall">
						<!-- 删除 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.deleteDoc('${eopBasedataPayWay.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
