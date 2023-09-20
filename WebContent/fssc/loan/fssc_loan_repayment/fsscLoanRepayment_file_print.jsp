<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/resource/jsp/view_top.jsp"%>
<link rel="stylesheet" href="<c:url value="/eop/basedata/resource/css/print.css"/>" />
<title>
${lfn:message('fssc-loan:fsscLoanMain.print')}
</title>
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
</style>
<c:if test="${param.method=='print'}">
<div id="optBarDiv">
    <%--打印 --%>
    <c:if test="${fsscLoanRepaymentForm.docStatus != '10'}">
        <input type="button"
               value="<bean:message key="button.print"/>"
               onclick="javascript:print();">
    </c:if>
    <input type="button"
           value="<bean:message key="button.close"/>"
           onclick="Com_CloseWindow();">
</div>
    </c:if>

<center>
 <table width=75%>
		<tr>
				<td>
					<table  width="100%" style="table-layout:fixed; word-break: break-all; word-wrap: break-word;">
						<tr>
							<td width="25%">
							</td>
							<td width="50%" >
								<p class="txttitle">
									${fsscLoanRepaymentForm.docTemplateName}
								</p>
							</td>
							<td width="25%" >
								<%--条形码--%>
								<div id="barcodeTarget" style="margin-top: 45px;" ></div>
							</td>
						</tr>
					</table>
					<br/>
					<table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-loan:fsscLoanRepayment.docSubject')}
                    </td>
                    <td colspan="5" width="83.0%">
                            <%-- 标题  style="width:45%;text-align:center;font-size:25px;" --%>
                        <xform:text property="docSubject" style="width:95%;" />
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-loan:fsscLoanRepayment.fdRepaymentPerson')}
                    </td>
                    <td width="16.6%">
                        <%-- 还款人--%>
                        <div id="_xform_fdRepaymentPersonId" _xform_type="address">
                            <xform:address propertyId="fdRepaymentPersonId" propertyName="fdRepaymentPersonName" orgType="ORG_TYPE_PERSON" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-loan:fsscLoanRepayment.fdRepaymentDept')}
                    </td>
                    <td width="16.6%">
                        <%-- 还款人部门--%>
                        <div id="_xform_fdRepaymentDeptId" _xform_type="address">
                            <xform:address propertyId="fdRepaymentDeptId" propertyName="fdRepaymentDeptName" orgType="ORG_TYPE_ORGORDEPT" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-loan:fsscLoanRepayment.docCreateTime')}
                    </td>
                    <td width="16.6%">
                        <%-- 创建时间--%>
                        <div id="_xform_docCreateTime" _xform_type="datetime">
                            <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-loan:fsscLoanRepayment.fdLoanMain')}
                    </td>
                    <td width="16.6%">
                        <%-- 借款单--%>
                        <div id="_xform_fdLoanMainId" _xform_type="dialog">
                           ${fsscLoanRepaymentForm.fdLoanMainName}
                        </div>
                    </td>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-loan:fsscLoanRepayment.fdCanOffsetMoney')}
                    </td>
                    <td width="16.6%">
                        <%-- 未冲销金额--%>
                        <div id="_xform_fdCanOffsetMoney" _xform_type="text">
                            <kmss:showNumber value="${fsscLoanRepaymentForm.fdCanOffsetMoney}" pattern="##0.00"></kmss:showNumber>
                        </div>
                    </td>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-loan:fsscLoanRepayment.fdRepaymentMoney')}
                    </td>
                    <td width="16.6%">
                        <%-- 还款金额--%>
                        <div id="_xform_fdRepaymentMoney" _xform_type="text">
                            <kmss:showNumber value="${fsscLoanRepaymentForm.fdRepaymentMoney}" pattern="##0.00"></kmss:showNumber>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-loan:fsscLoanRepayment.fdBasePayWay')}
                    </td>
                    <td width="16.6%">
                        <%-- 付款方式--%>
                        <div id="_xform_fdBasePayWayId" _xform_type="dialog">
                            <xform:dialog propertyId="fdBasePayWayId" propertyName="fdBasePayWayName" showStatus="view" style="width:95%;">
                                dialogSelect(false,'eop_basedata_pay_way_getPayWay','fdBasePayWayId','fdBasePayWayName');
                            </xform:dialog>
                        </div>
                    </td>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-loan:fsscLoanRepayment.fdPaymentAccount')}
                    </td>
                    <td colspan="3" width="49.8%">
                        <%-- 收款账号--%>
                        <div id="_xform_fdPaymentAccount" _xform_type="text">
                            <xform:text property="fdPaymentAccount" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-loan:fsscLoanRepayment.fdReason')}
                    </td>
                    <td colspan="5" width="83.0%">
                            <%-- 还款说明--%>
                        <div id="_xform_fdReason" _xform_type="textarea">
                            <xform:textarea property="fdReason" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                </tr>

                     </table>
                </td></tr>

<c:if test="${saveApproval||param.method=='print'}">

<tr>  <td>
				<c:import url="/sys/workflow/include/sysWfProcess_log.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="fsscLoanRepaymentForm" />
				</c:import>
</td></tr>

</c:if>
     <tr><td>
				<table class="tb_normal" width=100%>
					<tr>
						<!-- 借款人签名  -->
						<td class="td_normal_title" width=12%>
							<bean:message bundle="fssc-loan" key="fsscLoanMain.fdLoanPerson.write"/>
						</td><td width="43%">
						<!-- 打印日期  -->
						<td class="td_normal_title" width=12%>
							<bean:message bundle="fssc-loan" key="fsscLoanMain.fdPrintTime"/>
						</td><td width="16%">
						<c:out value="${fdPrintTime}" />
					</td>
					</tr>
				</table>
			
		</td>
	   </tr>
</table>

</center>
<script type="text/javascript">
Com_IncludeFile("jquery.js");
Com_IncludeFile("document.js", 'style/default/doc/');
</script>
<script type="text/javascript" src="<c:url value="/eop/basedata/resource/js/jquery-barcode.js"/>"></script>
<script>
	function confirmDelete(msg){
	var del = confirm("<bean:message key="page.comfirmDelete"/>");
	return del;
}
	$(document).ready(function(){
		$("#barcodeTarget").barcode("${fsscLoanRepaymentForm.docNumber}", "code128",{barWidth:1, barHeight:40});
	});
</script>
<%@ include file="/resource/jsp/view_down.jsp"%>
