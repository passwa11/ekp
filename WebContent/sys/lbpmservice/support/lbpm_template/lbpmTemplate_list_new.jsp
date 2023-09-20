<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="lbpmTemplate" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column  property="fdName" title="${ lfn:message('sys-lbpmservice-support:lbpmTemplate.fdName') }" style="text-align:left;min-width:180px">
		</list:data-column>
		<list:data-column headerClass="width80" col="fdIsDefault" title="${ lfn:message('sys-lbpmservice-support:lbpmTemplate.fdIsDefault') }" escape="false">
		    <c:if test="${lbpmTemplate.fdIsDefault}">
				<img src='<c:url value="/sys/profile/resource/images/profile_list_status_y.png"/>'>
			</c:if>
		</list:data-column>
		<list:data-column headerClass="width100" property="fdCreator.fdName" title="${ lfn:message('sys-lbpmservice-support:lbpmTemplate.fdCreator') }">
		</list:data-column>
		<list:data-column headerClass="width140" property="fdCreateTime" title="${ lfn:message('sys-lbpmservice-support:lbpmTemplate.fdCreateTime') }">
		</list:data-column>
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/lbpmservice/support/lbpm_template/lbpmTemplate.do?method=edit&fdId=${lbpmTemplate.fdId}&fdModelName=${lbpmTemplate.fdModelName}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${lbpmTemplate.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/lbpmservice/support/lbpm_template/lbpmTemplate.do?method=delete&fdId=${lbpmTemplate.fdId}&fdModelName=${lbpmTemplate.fdModelName}" requestMethod="POST">
						<!-- 禁用 -->
						<a class="btn_txt" href="javascript:deleteAll('${lbpmTemplate.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>