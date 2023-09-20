<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataItemField" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="fdCompany.name" title="${lfn:message('eop-basedata:eopBasedataItemField.fdCompany')}" escape="false">
            <c:out value="${eopBasedataItemField.fdCompany.fdName}" />
        </list:data-column>
        <list:data-column col="fdCompany.id" escape="false">
            <c:out value="${eopBasedataItemField.fdCompany.fdId}" />
        </list:data-column>
        <list:data-column col="fdItems.name" title="${lfn:message('eop-basedata:eopBasedataItemField.fdItems')}" escape="false">
           <c:forEach items="${eopBasedataItemField.fdItems}" var="fdItem" varStatus="status">
        		<c:if test="${status.index!=0}">;</c:if>
        		<c:out value="${fdItem.fdName}"></c:out>
        	</c:forEach>
        </list:data-column>
        <list:data-column col="fdFields.name" title="${lfn:message('eop-basedata:eopBasedataItemField.fdFields')}">
            <c:set value="${ fn:split(eopBasedataItemField.fdFields, ';') }" var="array"></c:set>
        	<c:forEach items="${array}" var="fdField" varStatus="status">
        		<c:if test="${status.index!=0}">;</c:if>
        		<sunbor:enumsShow value="${fdField}" enumsType="eop_basedata_item_field" />
        	</c:forEach>
        </list:data-column>
        <list:data-column col="fdFields">
            <c:out value="${eopBasedataItemField.fdFields}" />
        </list:data-column>
        <list:data-column property="fdFieldOne" title="${lfn:message('eop-basedata:eopBasedataItemField.fdFieldOne')}" />
        <list:data-column property="fdFieldTwo" title="${lfn:message('eop-basedata:eopBasedataItemField.fdFieldTwo')}" />
        <list:data-column property="fdFiledThree" title="${lfn:message('eop-basedata:eopBasedataItemField.fdFiledThree')}" />
        <list:data-column property="fdFieldFour" title="${lfn:message('eop-basedata:eopBasedataItemField.fdFieldFour')}" />
        <list:data-column property="fdFieldFive" title="${lfn:message('eop-basedata:eopBasedataItemField.fdFieldFive')}" />
        <list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/eop/basedata/eop_basedata_item_field/eopBasedataItemField.do?method=add">
						<!-- 编辑 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.edit('${eopBasedataItemField.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/eop/basedata/eop_basedata_item_field/eopBasedataItemField.do?method=deleteall">
						<!-- 删除 -->
						<a class="btn_txt" style="color:#2574ad" href="javascript:window.deleteDoc('${eopBasedataItemField.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
