<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="emolumentWelfare" list="${queryPage.list }" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="personInfoId">
			${emolumentWelfare.fdPersonInfo.fdId}
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<!-- 姓名（账号）-->
		<list:data-column headerClass="width100" col="nameAccount" title="${ lfn:message('hr-staff:hrStaffPersonExperience.nameAccount') }">
		    ${emolumentWelfare.fdPersonInfo.nameAccount}
		</list:data-column>
		<!--工资账户名-->
		<list:data-column headerClass="width120" property="fdPayrollName" title="${ lfn:message('hr-staff:hrStaffEmolumentWelfare.fdPayrollName') }"> 
		</list:data-column>
		<!--工资银行-->
		<list:data-column headerClass="width120" property="fdPayrollBank" title="${ lfn:message('hr-staff:hrStaffEmolumentWelfare.fdPayrollBank') }"> 
		</list:data-column>
		<!--工资账号-->
		<list:data-column headerClass="width120" property="fdPayrollAccount" title="${ lfn:message('hr-staff:hrStaffEmolumentWelfare.fdPayrollAccount') }"> 
		</list:data-column>
		<!--公积金账户-->
		<list:data-column headerClass="width120" property="fdSurplusAccount" title="${ lfn:message('hr-staff:hrStaffEmolumentWelfare.fdSurplusAccount') }"> 
		</list:data-column>
		<!--社保号码-->
		<list:data-column headerClass="width120" property="fdSocialSecurityNumber" title="${ lfn:message('hr-staff:hrStaffEmolumentWelfare.fdSocialSecurityNumber') }"> 
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width80" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<!-- 编辑 -->
					<a class="btn_txt" href="javascript:edit('${emolumentWelfare.fdId}')">${ lfn:message('button.edit') }</a>
					<!-- 删除 -->
					<a class="btn_txt" href="javascript:_delete('${emolumentWelfare.fdId}')">${ lfn:message('button.delete') }</a>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>