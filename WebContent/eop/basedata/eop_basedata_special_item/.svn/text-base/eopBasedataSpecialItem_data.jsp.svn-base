<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataSpecialItem" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column property="fdDescription" title="${lfn:message('eop-basedata:eopBasedataSpecialItem.fdDescription') }"/>
        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataSpecialItem.fdCode') }"/>
        <list:data-column col="fdCompany" title="${lfn:message('eop-basedata:eopBasedataSpecialItem.fdCompany') }">
        	${eopBasedataSpecialItem.fdCompany.fdName}
        </list:data-column>
        <list:data-column col="docCreator" title="${lfn:message('eop-basedata:eopBasedataSpecialItem.docCreator') }">
        	${eopBasedataSpecialItem.docCreator.fdName}
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('eop-basedata:eopBasedataSpecialItem.docCreateTime') }">
        	${eopBasedataSpecialItem.docCreateTime}
        </list:data-column>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/eop/basedata/eop_basedata_special_item/eopBasedataSpecialItem.do?method=add">
						<!-- 编辑 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.edit('${eopBasedataSpecialItem.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/eop/basedata/eop_basedata_special_item/eopBasedataSpecialItem.do?method=deleteall">
						<!-- 删除 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.deleteDoc('${eopBasedataSpecialItem.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
