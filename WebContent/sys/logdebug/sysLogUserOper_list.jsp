<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysLogUserOper" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 操作时间 -->
		<list:data-column headerClass="width100" property="fdCreateTime" title="${ lfn:message('sys-log:sysLogUserOper.fdCreateTime') }">
		</list:data-column>
		<!-- IP地址 -->
		<list:data-column headerClass="width100" property="fdIp" title="${ lfn:message('sys-log:sysLogUserOper.fdIp') }">
		</list:data-column>
		<!-- 操作者 -->
		<list:data-column headerClass="width100" property="fdOperator" title="${ lfn:message('sys-log:sysLogUserOper.fdOperator') }">
		</list:data-column>
		<!-- 操作 -->
		<list:data-column headerClass="width100" property="fdEventType" title="${ lfn:message('sys-log:sysLogUserOper.fdEventType') }">
		</list:data-column>
		<!-- 模块中文名 -->
		<list:data-column headerClass="width100" property="fdModelDesc" title="${ lfn:message('sys-log:sysLogUserOper.fdModelDesc') }">
		</list:data-column>
		<!-- 结果 -->
		<list:data-column headerClass="width80" col="fdSuccess" title="${ lfn:message('sys-log:sysLogUserOper.fdSuccess') }">
			<sunbor:enumsShow value="${sysLogUserOper.fdSuccess}" enumsType="sysLogUserOper_enum_fdSuccess" />
		</list:data-column>
		<!-- 状态 -->
		<list:data-column headerClass="width80" col="fdStatus" title="${ lfn:message('sys-log:sysLogUserOper.fdStatus') }">
			<sunbor:enumsShow value="${sysLogUserOper.fdStatus}" enumsType="sysLogUserOper_enum_fdStatus" />
		</list:data-column>
		
		<kmss:auth requestURL="/sys/log/sys_log_user_oper/sysLogUserOper.do?method=audit&listMethod=${param.method }" requestMethod="POST">
			<!-- 其它操作 -->
			<list:data-column headerClass="width100" col="operations" title="${ lfn:message('list.operation') }" escape="false">
				<!--操作按钮 开始-->
				<div class="conf_show_more_w">
					<div class="conf_btn_edit">
						<!-- 审计 -->
						<a class="btn_txt" href="javascript:audit('${sysLogUserOper.fdId}')">${lfn:message('sys-log:sysLogUserOper.button.audit')}</a>
					</div>
				</div>
				<!--操作按钮 结束-->
			</list:data-column>
		</kmss:auth>
		
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>