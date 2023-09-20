<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="payroll" list="${queryPage.list }" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<!--推送消息名称 -->
		<list:data-column property="fdMessageName" title="${ lfn:message('hr-staff:hrStaffPayrollIssuance.fdmessageName') }">
		</list:data-column>
		<!--发放时间 -->
		<list:data-column headerClass="width80" property="fdCreateTime" title="${ lfn:message('hr-staff:hrStaffPayrollIssuance.fdCreateTime') }">
		</list:data-column>
		<!--发放人-->
		<list:data-column headerClass="width80" property="fdCreator.fdName" title="${ lfn:message('hr-staff:hrStaffPayrollIssuance.fdCreator') }">
		</list:data-column>
		<!--发放消息-->
		<list:data-column headerClass="width210" property="fdResultMseeage" title="${ lfn:message('hr-staff:hrStaffPayrollIssuance.resultMseeage') }"> 
		{messages}
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width80" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>