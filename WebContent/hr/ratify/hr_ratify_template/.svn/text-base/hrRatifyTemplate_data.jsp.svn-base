<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="hrRatifyTemplate" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdOrder" title="${lfn:message('hr-ratify:hrRatifyTemplate.fdOrder')}" />
        <list:data-column property="fdName" title="${lfn:message('hr-ratify:hrRatifyTemplate.fdName')}" />
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('hr-ratify:hrRatifyTemplate.fdIsAvailable')}">
            <sunbor:enumsShow value="${hrRatifyTemplate.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${hrRatifyTemplate.fdIsAvailable}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('hr-ratify:hrRatifyTemplate.docCreateTime')}">
            <kmss:showDate value="${hrRatifyTemplate.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <!-- 其它操作 -->
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
				 	<c:if test="${hrRatifyTemplate.fdIsAvailable}">
				 		<kmss:authShow roles="ROLE_HRRATIFY_CREATE">
							<!-- 新建流程 -->
							<a class="btn_txt" href="javascript:add('${hrRatifyTemplate.fdId}', '${hrRatifyTemplate.fdType }')">${lfn:message('hr-ratify:button.opt.create')}</a>
						</kmss:authShow>
				  	</c:if>
					<kmss:auth requestURL="/hr/ratify/hr_ratify_template/hrRatifyTemplate.do?method=edit&fdId=${hrRatifyTemplate.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${hrRatifyTemplate.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:authShow roles="ROLE_HRRATIFY_BACKSTAGE_SETTING">
						<!-- 搜索设置 -->
						<a class="btn_txt" href="javascript:setSearch('${hrRatifyTemplate.fdId}','${hrRatifyTemplate.fdName}','${hrRatifyTemplate.fdTempKey}')">${lfn:message('hr-ratify:button.opt.searchSet')}</a>
					</kmss:authShow>
					<kmss:auth requestURL="/hr/ratify/hr_ratify_template/hrRatifyTemplate.do?method=delete&fdId=${hrRatifyTemplate.fdId}" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:deleteDoc('${hrRatifyTemplate.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
