<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataExchangeRate" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column> 
		<list:data-column col="fdCompanyName" title="${lfn:message('eop-basedata:eopBasedataExchangeRate.fdCompanyList')}" >
        	<c:forEach items="${eopBasedataExchangeRate.fdCompanyList}" var="fdCompany" varStatus="status">
        		<c:if test="${status.index!=0}">;</c:if>
        		${fdCompany.fdName}
        	</c:forEach>
        </list:data-column>
        <list:data-column col="fdSourceCurrency.name" title="${lfn:message('eop-basedata:eopBasedataExchangeRate.fdSourceCurrency')}" escape="false">
            <c:out value="${eopBasedataExchangeRate.fdSourceCurrency.fdName}" />
        </list:data-column>
        <list:data-column col="fdSourceCurrency.id" escape="false">
            <c:out value="${eopBasedataExchangeRate.fdSourceCurrency.fdId}" />
        </list:data-column>
        <list:data-column col="fdTargetCurrency.name" title="${lfn:message('eop-basedata:eopBasedataExchangeRate.fdTargetCurrency')}" escape="false">
            <c:out value="${eopBasedataExchangeRate.fdTargetCurrency.fdName}" />
        </list:data-column>
        <list:data-column col="fdTargetCurrency.id" escape="false">
            <c:out value="${eopBasedataExchangeRate.fdTargetCurrency.fdId}" />
        </list:data-column>
        <list:data-column col="fdRate" title="${lfn:message('eop-basedata:eopBasedataExchangeRate.fdRate')}" >
        	<kmss:showNumber value="${eopBasedataExchangeRate.fdRate }" pattern="0.0#####"></kmss:showNumber>
        </list:data-column>
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('eop-basedata:eopBasedataExchangeRate.fdIsAvailable')}">
            <sunbor:enumsShow value="${eopBasedataExchangeRate.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <fssc:switchOn property="fdRateEnabled">
        <list:data-column col="fdType.name" title="${lfn:message('eop-basedata:eopBasedataExchangeRate.fdType')}">
            <sunbor:enumsShow value="${eopBasedataExchangeRate.fdType}" enumsType="eop_basedata_exchange_rate_type" />
        </list:data-column>
        </fssc:switchOn>
        <list:data-column col="fdType">
            <c:out value="${eopBasedataExchangeRate.fdType}" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${eopBasedataExchangeRate.fdIsAvailable}" />
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('eop-basedata:eopBasedataExchangeRate.docCreator')}" escape="false">
            <c:out value="${eopBasedataExchangeRate.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${eopBasedataExchangeRate.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('eop-basedata:eopBasedataExchangeRate.docCreateTime')}">
            <kmss:showDate value="${eopBasedataExchangeRate.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/eop/basedata/eop_basedata_exchange_rate/eopBasedataExchangeRate.do?method=add">
						<!-- 编辑 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.edit('${eopBasedataExchangeRate.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/eop/basedata/eop_basedata_exchange_rate/eopBasedataExchangeRate.do?method=deleteall">
						<!-- 删除 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.deleteDoc('${eopBasedataExchangeRate.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
