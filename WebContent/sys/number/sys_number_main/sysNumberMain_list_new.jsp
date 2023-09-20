<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.sunbor.web.tag.Page"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.sys.number.model.SysNumberMain"%>

<list:data>
	<list:data-columns var="sysNumberMain" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column  property="fdName" title="${ lfn:message('sys-number:sysNumberMain.fdName') }" style="text-align:left;min-width:180px">
		</list:data-column>
		<list:data-column headerClass="width30" property="fdOrder" title="${ lfn:message('sys-number:sysNumberMain.fdOrder') }">
		</list:data-column>
		<list:data-column headerClass="width80" col="fdDefaultFlag" title="${ lfn:message('sys-number:sysNumberMain.fdDefaultFlag') }" escape="false">
		    <c:if test="${sysNumberMain.fdDefaultFlag=='0' }">
				<img src='<c:url value="/sys/profile/resource/images/profile_list_status_y.png"/>'>
			</c:if>
			<c:if test="${sysNumberMain.fdDefaultFlag=='1' }">
				
			</c:if>
		</list:data-column>
		<list:data-column headerClass="width200" property="docCreator.fdName" title="${ lfn:message('sys-number:sysNumberMain.docCreator') }">
		</list:data-column>
		<list:data-column headerClass="width140" property="docCreateTime" title="${ lfn:message('sys-number:sysNumberMain.docCreateTime') }">
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/number/sys_number_main/sysNumberMain.do?method=edit&fdId=${sysNumberMain.fdId}&modelName=${sysNumberMain.fdModelName }" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${sysNumberMain.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/number/sys_number_main/sysNumberMain.do?method=delete&fdId=${sysNumberMain.fdId}&modelName=${sysNumberMain.fdModelName }" requestMethod="POST">
						<!-- 禁用 -->
						<a class="btn_txt" href="javascript:deleteAll('${sysNumberMain.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>