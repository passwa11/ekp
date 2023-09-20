<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysLogOrganization" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 操作时间 -->
		<list:data-column headerClass="width120" col="fdCreateTime" title="${ lfn:message('sys-log:sysLogApp.fdCreateTime') }">
			<kmss:showDate type="datetime" value="${sysLogOrganization.fdCreateTime}"/>
		</list:data-column>
		<!-- IP地址 -->
		<list:data-column headerClass="width100" property="fdIp" title="${ lfn:message('sys-log:sysLogApp.fdIp') }">
		</list:data-column>
		<!-- 浏览器 -->
		<list:data-column headerClass="width100" property="fdBrowser" title="${ lfn:message('sys-log:sysLogOrganization.fdBrowser') }">
		</list:data-column>
		<!-- 设备 -->
		<list:data-column headerClass="width100" property="fdEquipment" title="${ lfn:message('sys-log:sysLogOrganization.fdEquipment') }">
		</list:data-column>
		<!-- 操作者 -->
		<list:data-column headerClass="width100" property="fdOperator" title="${ lfn:message('sys-log:sysLogApp.fdOperator') }">
		</list:data-column>
		<!-- 操作 -->
		<list:data-column headerClass="width100" col="fdParaMethod" title="${ lfn:message('sys-log:sysLogApp.fdParaMethod') }">
			<% try{ %>
				<bean:message key="button.${sysLogOrganization.fdParaMethod}"/>
			<% }catch(Exception e){ %>
					<% try{ %>
						<bean:message bundle="sys-log" key="sysLogOrganization.${sysLogOrganization.fdParaMethod}"/>
					<%}catch(Exception ex){ %>
						<bean:write name="sysLogOrganization" property="fdParaMethod"/>
					<% } %>
			<% } %>
		</list:data-column>
		<!-- 操作记录 -->
		<list:data-column headerClass="width300" property="fdDetails" title="${ lfn:message('sys-log:sysLogOrganization.fdDetails') }" style="text-align: left;">
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width100" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/log/sys_log_organization/sysLogOrganization.do?method=deleteall" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:del('${sysLogOrganization.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>