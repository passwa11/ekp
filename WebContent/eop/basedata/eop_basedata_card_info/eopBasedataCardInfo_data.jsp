<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataCardInfo" list="${queryPage.list}" varIndex="status">
       <list:data-column property="fdId" />
        <list:data-column col="index">
          ${status+1}
       </list:data-column>
        <list:data-column property="fdCorNum" title="${lfn:message('eop-basedata:eopBasedataCardInfo.fdCorNum')}" />
        <list:data-column property="fdActNum" title="${lfn:message('eop-basedata:eopBasedataCardInfo.fdActNum')}" />
        <list:data-column property="fdAcctNbr" title="${lfn:message('eop-basedata:eopBasedataCardInfo.fdAcctNbr')}" />
        <list:data-column property="fdCardNumber" title="${lfn:message('eop-basedata:eopBasedataCardInfo.fdCardNumber')}" />
        <list:data-column col="fdHolder.name" title="${lfn:message('eop-basedata:eopBasedataCardInfo.fdHolder')}">
            <c:out value="${eopBasedataCardInfo.fdHolder.fdName}" />
        </list:data-column>
        <list:data-column col="fdHolder.id" escape="false">
            <c:out value="${eopBasedataCardInfo.fdHolder.fdId}" />
        </list:data-column>
        <list:data-column property="fdHolderEngName" title="${lfn:message('eop-basedata:eopBasedataCardInfo.fdHolderEngName')}"/>
        <list:data-column property="fdActivationDate" title="${lfn:message('eop-basedata:eopBasedataCardInfo.fdActivationDate')}"/>
        <list:data-column col="docCreateTime" title="${lfn:message('eop-basedata:eopBasedataCardInfo.docCreateTime')}">
            <kmss:showDate value="${eopBasedataCardInfo.docCreateTime}" type="datetime"/>
        </list:data-column>
        <list:data-column property="fdCancelDate" title="${lfn:message('eop-basedata:eopBasedataCardInfo.fdCancelDate')}"/>
        <list:data-column col="docCreator.name" title="${lfn:message('eop-basedata:eopBasedataCardInfo.docCreator')}">
            <c:out value="${eopBasedataCardInfo.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${eopBasedataCardInfo.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('eop-basedata:eopBasedataCardInfo.fdIsAvailable')}">
            <sunbor:enumsShow value="${eopBasedataCardInfo.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable.id">
            <c:out value="${eopBasedataCardInfo.fdIsAvailable}" />
        </list:data-column>
        <list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/eop/basedata/eop_basedata_card_info/eopBasedataCardInfo.do?method=add">
						<!-- 编辑 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.edit('${eopBasedataCardInfo.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/eop/basedata/eop_basedata_card_info/eopBasedataCardInfo.do?method=deleteall">
						<!-- 删除 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.deleteDoc('${eopBasedataCardInfo.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>--%>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
