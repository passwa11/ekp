<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysLogLogin" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 操作时间 -->
		<list:data-column headerClass="width120" col="fdCreateTime" title="${ lfn:message('sys-log:sysLogLogin.fdCreateTime') }">
			<kmss:showDate type="datetime" value="${sysLogLogin.fdCreateTime}"/>
		</list:data-column>
		<!-- IP地址 -->
		<list:data-column headerClass="width120" property="fdIp" title="${ lfn:message('sys-log:sysLogLogin.fdIp') }">
		</list:data-column>
		<!-- 地点 -->
		<list:data-column headerClass="width100" property="fdLocation" title="${ lfn:message('sys-log:sysLogLogin.fdLocation') }">
		</list:data-column>
		<!-- 浏览器 -->
		<list:data-column headerClass="width100" property="fdBrowser" title="${ lfn:message('sys-log:sysLogLogin.fdBrowser') }">
		</list:data-column>
		<!-- 设备 -->
		<list:data-column headerClass="width100" property="fdEquipment" title="${ lfn:message('sys-log:sysLogLogin.fdEquipment') }">
		</list:data-column>
		<!-- 操作者 -->
		<list:data-column headerClass="width100" property="fdOperator" title="${ lfn:message('sys-log:sysLogLogin.fdOperator') }">
		</list:data-column>
		<!-- 验证方式 -->
		<list:data-column headerClass="width100" col="fdVerification" title="${ lfn:message('sys-log:sysLogLogin.fdVerification') }">
			<sunbor:enumsShow value="${sysLogLogin.fdVerification}" enumsType="sys_log_login_verification" />
		</list:data-column>
		<!-- 状态 -->
		<list:data-column headerClass="width100" col="fdType" title="${ lfn:message('sys-log:sysLogLogin.fdType') }">
			<sunbor:enumsShow value="${sysLogLogin.fdType}" enumsType="sys_log_login_type" />
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width100" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/log/sys_login_info/sysLogLogin.do?method=deleteall" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:del('${HtmlParam.isBak}','${sysLogLogin.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>