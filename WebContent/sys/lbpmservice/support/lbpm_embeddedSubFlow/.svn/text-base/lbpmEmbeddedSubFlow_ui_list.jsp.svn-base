<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="lbpmEmbeddedSubFlow" list="${queryPage.list }">
		<%--ID--%>
		<list:data-column property="fdId"></list:data-column>
		
		<list:data-column headerClass="width30" property="fdOrder" title="${ lfn:message('model.fdOrder') }"></list:data-column>
		
		<%--标题--%>
		<list:data-column col="fdName" title="${ lfn:message('sys-lbpmservice-support:lbpmEmbeddedSubFlow.fdName') }" style="text-align:center" escape="false">
			<span class="com_subject"><c:out value="${lbpmEmbeddedSubFlow.fdName}" /></span>
		</list:data-column>
		<list:data-column headerClass="width80" col="fdIsAvailable" title="${ lfn:message('sys-lbpmservice-support:lbpmEmbeddedSubFlow.state') }" escape="false">
		    <c:if test="${lbpmEmbeddedSubFlow.fdIsAvailable}">
				<bean:message bundle="sys-lbpmservice-support" key="lbpmEmbeddedSubFlow.fdIsAvailable.true" />
			</c:if>
			<c:if test="${!lbpmEmbeddedSubFlow.fdIsAvailable}">
				<bean:message bundle="sys-lbpmservice-support" key="lbpmEmbeddedSubFlow.fdIsAvailable.false" />
			</c:if>
		</list:data-column>
		<!-- 创建人 -->
		<list:data-column headerClass="width80" property="fdCreator.fdName" title="${ lfn:message('model.fdCreator') }">
		</list:data-column>
		<!-- 创建时间 -->
		<list:data-column headerClass="width120" property="fdCreateTime" title="${ lfn:message('model.fdCreateTime') }">
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width100" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/lbpmservice/support/lbpmEmbeddedSubFlow.do?method=edit&fdId=${lbpmEmbeddedSubFlow.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${lbpmEmbeddedSubFlow.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/lbpmservice/support/lbpmEmbeddedSubFlow.do?method=delete&fdId=${lbpmEmbeddedSubFlow.fdId}" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:delDoc('${lbpmEmbeddedSubFlow.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>
