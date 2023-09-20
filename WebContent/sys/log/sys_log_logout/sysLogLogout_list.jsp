<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysLogLogout" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 操作时间 -->
		<list:data-column headerClass="width150" col="fdCreateTime" title="${ lfn:message('sys-log:sysLogLogout.fdCreateTime') }">
			<kmss:showDate type="datetime" value="${sysLogLogout.fdCreateTime}"/>
		</list:data-column>
		<!-- IP地址 -->
		<list:data-column headerClass="width150" property="fdIp" title="${ lfn:message('sys-log:sysLogLogout.fdIp') }">
		</list:data-column>
		<!-- 浏览器 -->
		<list:data-column headerClass="width150" property="fdBrowser" title="${ lfn:message('sys-log:sysLogLogout.fdBrowser') }">
		</list:data-column>
		<!-- 设备 -->
		<list:data-column headerClass="width100" property="fdEquipment" title="${ lfn:message('sys-log:sysLogLogout.fdEquipment') }">
		</list:data-column>
		<!-- 操作者 -->
		<list:data-column headerClass="width100" property="fdOperator" title="${ lfn:message('sys-log:sysLogLogout.fdOperator') }">
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width100" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/log/sys_logout_info/sysLogLogout.do?method=deleteall" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:del('${HtmlParam.isBak}','${sysLogLogout.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>