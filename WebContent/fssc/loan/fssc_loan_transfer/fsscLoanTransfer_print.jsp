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
<div id="optBarDiv">
	<%--打印 --%>
	<c:if test="${fsscLoanTransferForm.docStatus != '10'}">
	    <input type="button"
			value="<bean:message key="button.print"/>"
			onclick="javascript:print();">
	</c:if>
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>
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
									  ${ lfn:message('fssc-loan:table.fsscLoanTransfer') }
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
                        ${lfn:message('fssc-loan:fsscLoanTransfer.docSubject')}
                    </td>
                    <td colspan="5" width="83.0%">
                            <%-- 标题  --%>
                        <xform:text property="docSubject" style="width:95%;" />
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-loan:fsscLoanTransfer.fdTurnOut')}
                    </td>
                    <td width="16.6%">
                        <%-- 转出申请人--%>
                        <div id="_xform_fdTurnOutId" _xform_type="address">
                            <xform:address propertyId="fdTurnOutId" propertyName="fdTurnOutName" orgType="ORG_TYPE_PERSON" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-loan:fsscLoanTransfer.fdTurnOutDept')}
                    </td>
                    <td width="16.6%">
                        <%-- 转出人部门--%>
                        <div id="_xform_fdTurnOutDeptId" _xform_type="address">
                            <xform:address propertyId="fdTurnOutDeptId" propertyName="fdTurnOutDeptName" orgType="ORG_TYPE_ORGORDEPT" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-loan:fsscLoanTransfer.docCreateTime')}
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
                        ${lfn:message('fssc-loan:fsscLoanTransfer.fdLoanMain')}
                    </td>
                    <td width="16.6%">
                        <%-- 借款单--%>
                        <div id="_xform_fdLoanMainId" _xform_type="dialog">
                            <a target="_blank" href="<c:url value="/fssc/loan/fssc_loan_main/fsscLoanMain.do?method=view&fdId="/>${fsscLoanTransferForm.fdLoanMainId}">${fsscLoanTransferForm.fdLoanMainName}</a>
                        </div>
                    </td>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-loan:fsscLoanTransfer.fdCanOffsetMoney')}
                    </td>
                    <td width="16.6%">
                        <%-- 未冲销金额--%>
                        <div id="_xform_fdCanOffsetMoney" _xform_type="text">
                            <kmss:showNumber value="${fsscLoanTransferForm.fdCanOffsetMoney}" pattern="###,##0.00"></kmss:showNumber>
                        </div>
                    </td>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-loan:fsscLoanTransfer.fdTransferMoney')}
                    </td>
                    <td width="16.6%">
                        <%-- 转出金额--%>
                        <div id="_xform_fdTransferMoney" _xform_type="text">
                            <kmss:showNumber value="${fsscLoanTransferForm.fdTransferMoney}" pattern="###,##0.00"></kmss:showNumber>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-loan:fsscLoanTransfer.fdReceive')}
                    </td>
                    <td width="16.6%">
                        <%-- 接收人--%>
                        <div id="_xform_fdReceiveId" _xform_type="address">
                            <xform:address propertyId="fdReceiveId" propertyName="fdReceiveName" orgType="ORG_TYPE_PERSON" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-loan:fsscLoanTransfer.fdReceiveDept')}
                    </td>
                    <td width="16.6%">
                        <%-- 接收人部门--%>
                        <div id="_xform_fdReceiveDeptId" _xform_type="address">
                            <xform:address propertyId="fdReceiveDeptId" propertyName="fdReceiveDeptName" orgType="ORG_TYPE_ORGORDEPT" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-loan:fsscLoanTransfer.fdReceiveCostCenter')}
                    </td>
                    <td width="16.6%">
                        <%-- 接收人所属成本中心--%>
                        <div id="_xform_fdReceiveCostCenterId" _xform_type="dialog">
                            <xform:dialog propertyId="fdReceiveCostCenterId" propertyName="fdReceiveCostCenterName" showStatus="view" style="width:95%;">
                                dialogSelect(false,'eop_basedata_cost_center_selectCostCenter','fdReceiveCostCenterId','fdReceiveCostCenterName');
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-loan:fsscLoanTransfer.fdReason')}
                    </td>
                    <td colspan="5" width="83.0%">
                        <%-- 说明--%>
                        <div id="_xform_fdReason" _xform_type="textarea">
                            <xform:textarea property="fdReason" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="16.6%">
                            ${lfn:message('fssc-loan:module.attachment')}
                    </td>
                    <td colspan="5" width="83.0%">
                        <c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
                            <c:param name="fdKey" value="attachment"/>
                            <c:param name="formBeanName" value="fsscLoanTransferForm"/>
                        </c:import>
                    </td>
                </tr>
            </table>
	     	   </td>
	     </tr>
	     <tr><td>&nbsp;</td>
		</tr>
		<tr><td>
				<c:import url="/sys/workflow/include/sysWfProcess_log.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="fsscLoanTransferForm" />
				</c:import>
				<table class="tb_normal" width=100%>
					<tr>
						<!-- 借款人签名  -->
						<td class="td_normal_title" width=12%>
							<bean:message bundle="fssc-loan" key="fsscLoanMain.fdLoanPerson.write"/>
						</td><td width="43%">
					</td>
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
</script>
<script type="text/javascript" src="<c:url value="/eop/basedata/resource/js/jquery-barcode.js"/>"></script>
<script>
	function confirmDelete(msg){
	var del = confirm("<bean:message key="page.comfirmDelete"/>");
	return del;
}
	$(document).ready(function(){
		$("#barcodeTarget").barcode("${fsscLoanTransferForm.docNumber}", "code128",{barWidth:1, barHeight:40});
	});
</script>
<%@ include file="/resource/jsp/view_down.jsp"%>
