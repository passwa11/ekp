<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/mobile/jsp/ajax-accept.jsp" %>
<template:include ref="mobile.edit" compatibleMode="true">
	<template:replace name="content">
			<form name="sysWfProcessForm" method="POST" action="${LUI_ContextPath}/sys/lbpmservice/support/lbpm_process/lbpmProcess.do">
				<div>
					<div data-dojo-type="mui/view/DocScrollableView" 
						data-dojo-mixins="mui/form/_ValidateMixin" id="scrollView">
						<div class="muiFlowInfoW muiFormContent">
							<input name="docStatus" value="${HtmlParam.status}" type="hidden" />
							<input name="modelFlag" value="fsscLoanMain" type="hidden" />
						</div>
						<c:import url="/sys/lbpmservice/mobile/import/edit.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="fsscLoanMainForm" />
							<c:param name="fdKey" value="fsscLoanMain" />
							<c:param name="viewName" value="lbpmView" />
							<c:param name="backTo" value="scrollView" />
							<c:param name="onClickSubmitButton" value="review_submit();" />
						</c:import>
					</div>
					<script type="text/javascript">
					require(["fssc/mobile/resource/js/ajax-form!fsscExpenseMainForm"]);
					function review_submit(){
						var docStatus = $("input[name='docStatus']").val();
						$.ajax({
							url:Com_Parameter.ContextPath+'fssc/loan/fssc_loan_mobile/fsscLoanMobile.do?method=saveOrUpdateBill',
							data:{params:JSON.stringify({docStatus:docStatus,fdBillId:"${fdBillId}"})},
							dataType:'json',
							async:false,
							success:function(rtn){
								if(rtn.status&&!rtn.status){
									pass = false;
									require(['dijit/registry','dojo/ready','mui/dialog/Tip'],
											function(registry,ready,Tip){
										Tip.tip({text:'保存单据失败：'+rtn.message, icon:'mui mui-wrong'});
									});
									return false;
								}
							}
						})
						Com_Submit(document.forms[0],'update');
					}
					</script>
				</div>
			</form>
	</template:replace>
</template:include>
