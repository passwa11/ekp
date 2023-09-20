<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.hr.staff.model.HrStaffContractType"%>
<%@ page import="com.landray.kmss.sys.print.interfaces.ISysPrintMainCoreService"%>
<%@ page import="com.landray.kmss.util.SpringBeanUtil"%>
<list:data>
<list:data-columns var="hrStaffContractType" list="${queryPage.list}">
		<list:data-column property="fdId"/>
		<list:data-column property="fdOrder" title="${ lfn:message('hr-staff:hrStaffContractType.fdOrder') }">
		</list:data-column>
		<list:data-column property="fdName" title="${ lfn:message('hr-staff:hrStaffContractType.fdName') }">
		</list:data-column>
		<list:data-column col="fdPrintTemplate" title="是否启用">
			<%
				HrStaffContractType contractType = (HrStaffContractType)pageContext.getAttribute("hrStaffContractType");
				ISysPrintMainCoreService sysPrintMainCoreService = (ISysPrintMainCoreService)SpringBeanUtil.getBean("sysPrintMainCoreService");
				if(sysPrintMainCoreService.isEnablePrintTemplate(contractType, null, request)){
					out.print("是");
				}else{
					out.print("否");
				}
			%>
		</list:data-column>
		<list:data-column headerClass="width180" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!-- 操作列 -->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<!-- 编辑 -->
					<a class="btn_txt" href="javascript:edit('${hrStaffContractType.fdId}')">${lfn:message('button.edit')}</a>
					<!-- 删除 -->
					<a class="btn_txt" href="javascript:deleteAll('${hrStaffContractType.fdId}')">
						<bean:message key="button.delete"/>						
					</a>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>					
	</list:data-columns>	
	
	<list:data-paging page="${ queryPage }"></list:data-paging>
</list:data>