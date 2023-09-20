<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="<c:url value="/eop/basedata/resource/css/print.css"/>" />
<title>${lfn:message('fssc-cashier:table.fsscCashierPaymentDetail') }</title>
<style type="text/css">
	.tb_normal TD{
		border:1px #000 solid;
	}
	.tb_normal{
		border:1px #000 solid;
	}
	.tb_noborder{
		border:0px;
	}
	.tb_noborder TD{
		border:0px;
	}
	table td {
		color: #000;
	}
	.tb_normal > tbody > tr > td{
		border: 1px #000 solid !important;
	}
</style>

<center>
	<div class='lui_form_title_frame'>
		<div class='lui_form_subject'>
			<p class="txttitle"><c:out value="${form.docSubject}" /></p>
		</div>
	</div>

		<!--出纳付款单明细-->
		<c:if test="${not empty fsscCashierPaymentDetailList }">
			<kmss:ifModuleExist path="/fssc/cashier/">
				<table width='95%'  class="tb_normal" >
					<tr>
						<td colspan="12" class="td_normal_title">${lfn:message('fssc-cashier:table.fsscCashierPaymentDetail') }</td>
					</tr>
					<tr>
						<td colspan="12" width="100%">
							<table class="tb_normal" width="100%" align="center">
								<tr align="center" class="tr_normal_title">
									<td style="width:40px;">
											${lfn:message('page.serial')}
									</td>
									<td>
											${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdCompany')}
									</td>
									<td>
											${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdBasePayBank')}
									</td>
									<td>
											${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdBasePayWay')}
									</td>
									<td>
											${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdBaseCurrency')}
									</td>
									<td>
											${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdRate')}
									</td>
									<td>
											${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdPayeeName')}
									</td>
									<td>
											${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdPayeeAccount')}
									</td>
									<td>
											${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdPayeeBankName')}
									</td>
									<td>
											${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdPaymentMoney')}
									</td>
									<td>
											${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdStatus')}
									</td>
									<td>
											${lfn:message('fssc-cashier:fsscCashierPaymentDetail.fdPlanPaymentDate')}
									</td>
								</tr><c:forEach items="${fsscCashierPaymentDetailList}" var="fsscCashierPaymentDetail" varStatus="vstatus">
								<tr align="center" class="docList">
									<td style="width:40px;">
											${vstatus.index+1}
									</td>
									<td class="docList"  align="center">
											${fsscCashierPaymentDetail.fdCompany.fdName}
									</td>
									<td class="docList"  align="center">
											${fsscCashierPaymentDetail.fdBasePayBank.fdBankName}
									</td>
									<td class="docList"  align="center">
											${fsscCashierPaymentDetail.fdBasePayWay.fdName}
									</td>
									<td class="docList"  align="center">
											${fsscCashierPaymentDetail.fdBaseCurrency.fdName}
									</td>
									<td class="docList"  align="center">
											${fsscCashierPaymentDetail.fdRate}
									</td>
									<td class="docList"  align="center">
											${fsscCashierPaymentDetail.fdPayeeName}
									</td>
									<td class="docList"  align="center">
											${fsscCashierPaymentDetail.fdPayeeAccount}
									</td>
									<td class="docList"  align="center">
											${fsscCashierPaymentDetail.fdPayeeBankName}
									</td>
									<td class="docList"  align="center">
											${fsscCashierPaymentDetail.fdPaymentMoney}
									</td>
									<td class="docList"  align="center">
										<sunbor:enumsShow enumsType="fssc_cashier_fd_status" value="${fsscCashierPaymentDetail.fdStatus}"></sunbor:enumsShow>
									</td>
									<td class="docList"  align="center">
										<fmt:formatDate value="${fsscCashierPaymentDetail.fdPlanPaymentDate}" pattern='yyyy-MM-dd HH:mm' /></td>
								</tr>
							</c:forEach>
							</table></td></tr>
				</table>
			</kmss:ifModuleExist>
		</c:if>
	</table>

</center>
<script type="text/javascript">
	Com_IncludeFile("jquery.js");
	Com_IncludeFile("document.js", 'style/default/doc/');
</script>
<%@ include file="/resource/jsp/view_down.jsp"%>
