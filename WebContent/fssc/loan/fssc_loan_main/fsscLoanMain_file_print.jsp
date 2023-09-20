<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link rel="stylesheet" href="<c:url value="/eop/basedata/resource/css/print.css"/>" />
<title>

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
	<c:if test="${fsscLoanMainForm.docStatus != '10'}">
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
	<table width='75%'  >
		<!--基本信息-->
		<tr>
			<td>
				<table  width="100%" style="table-layout:fixed; word-break: break-all; word-wrap: break-word;">
					<tr>
						<td width="25%">
						</td>
						<td width="50%" >
							<p class="txttitle">
								${fsscLoanMainForm.docTemplateName}
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
							${lfn:message('fssc-loan:fsscLoanMain.docSubject')}
						</td>
						<td colspan="5" width="83.0%">
							<%-- 标题  style="width:45%;text-align:center;font-size:25px;" --%>
							<xform:text property="docSubject" style="width:95%;" showStatus="view" />
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="16.6%">
							${lfn:message('fssc-loan:fsscLoanMain.fdLoanPerson')}
						</td>
						<td width="16.6%">
							<%-- 借款人--%>
							<div id="_xform_fdLoanPersonId" _xform_type="address">
								<xform:address propertyId="fdLoanPersonId" propertyName="fdLoanPersonName" orgType="ORG_TYPE_PERSON" showStatus="view" style="width:95%;" />
							</div>
						</td>
						<td class="td_normal_title" width="16.6%">
							${lfn:message('fssc-loan:fsscLoanMain.fdLoanDept')}
						</td>
						<td width="16.6%">
							<%-- 借款人部门--%>
							<div id="_xform_fdLoanDeptId" _xform_type="address">
								<xform:address propertyId="fdLoanDeptId" propertyName="fdLoanDeptName" orgType="ORG_TYPE_ORGORDEPT" showStatus="view" style="width:95%;" />
							</div>
						</td>
						<td class="td_normal_title" width="16.6%">
							${lfn:message('fssc-loan:fsscLoanMain.docCreateTime')}
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
							${lfn:message('fssc-loan:fsscLoanMain.fdCompany')}
						</td>
						<td width="16.6%">
							<%-- 费用归属公司--%>
							<div id="_xform_fdCompanyId" _xform_type="dialog">
								<xform:dialog propertyId="fdCompanyId" propertyName="fdCompanyName" showStatus="view" style="width:95%;">
									dialogSelect(false,'eop_basedata_company_fdCompany','fdCompanyId','fdCompanyName');
								</xform:dialog>
							</div>
						</td>
						<td class="td_normal_title" width="16.6%">
							${lfn:message('fssc-loan:fsscLoanMain.fdCostCenter')}
						</td>
						<td width="16.6%">
							<%-- 费用承担部门--%>
							<div id="_xform_fdCostCenterId" _xform_type="dialog">
								<xform:dialog propertyId="fdCostCenterId" propertyName="fdCostCenterName" showStatus="view" style="width:95%;">
									dialogSelect(false,'eop_basedata_cost_center_selectCostCenter','fdCostCenterId','fdCostCenterName');
								</xform:dialog>
							</div>
						</td>
						<td class="td_normal_title" width="16.6%">
							${lfn:message('fssc-loan:fsscLoanMain.fdExpectedDate')}
						</td>
						<td width="16.6%">
							<%-- 预计还款日期--%>
							<div id="_xform_fdExpectedDate" _xform_type="datetime">
								<xform:datetime property="fdExpectedDate" showStatus="view" style="width:95%;" />
							</div>
						</td>

					</tr>
					<c:if test="${fdIsProject==true||fn:indexOf(fdExtendFields,'1')>-1||fn:indexOf(fdExtendFields,'2')>-1}">
						<tr class="extendFields">
							<c:if test="${fdIsProject==true}">
								<td class="td_normal_title" width="16.6%">
										${lfn:message('fssc-loan:fsscLoanMain.fdBaseProject')}
								</td>
								<td width="16.6%">
										<%-- 项目--%>
									<div id="_xform_fdBaseProjectId" _xform_type="dialog">
										<xform:dialog propertyId="fdBaseProjectId" propertyName="fdBaseProjectName" showStatus="view" subject="${lfn:message('fssc-loan:fsscLoanMain.fdBaseProject')}" style="width:95%;">
											dialogSelect(false,'eop_basedata_project_project','fdBaseProjectId','fdBaseProjectName',null,{'fdCompanyId':$('[name=fdCompanyId]').val()});
										</xform:dialog>
									</div>
								</td>
							</c:if>
							<c:if test="${fn:contains(fdExtendFields,'1')}">
								<td class="td_normal_title fdBaseWbs" width="16.6%" >
										${lfn:message('fssc-loan:fsscLoanMain.fdBaseWbs')}
								</td>
								<td width="16.6%">
										<%-- WBS号--%>
									<div id="_xform_fdBaseWbsId" _xform_type="dialog">
										<xform:dialog propertyId="fdBaseWbsId" propertyName="fdBaseWbsName" showStatus="view" subject="${lfn:message('fssc-loan:fsscLoanMain.fdBaseWbs')}" style="width:95%;">
											dialogSelect(false,'eop_basedata_wbs_fdWbs','fdBaseWbsId','fdBaseWbsName',null,{'fdCompanyId':$('[name=fdCompanyId]').val(),'selectType':'person'});
										</xform:dialog>
									</div>
								</td>
							</c:if>
							<c:if test="${fn:contains(fdExtendFields,'2')}">
								<td class="td_normal_title fdBaseInnerOrder" width="16.6%">
										${lfn:message('fssc-loan:fsscLoanMain.fdBaseInnerOrder')}
								</td>
								<td width="16.6%" >
										<%-- 内部订单--%>
									<div id="_xform_fdBaseInnerOrderId" _xform_type="dialog">
										<xform:dialog propertyId="fdBaseInnerOrderId" propertyName="fdBaseInnerOrderName" showStatus="view" subject="${lfn:message('fssc-loan:fsscLoanMain.fdBaseInnerOrder')}" style="width:95%;">
											dialogSelect(false,'eop_basedata_inner_order_fdInnerOrder','fdBaseInnerOrderId','fdBaseInnerOrderName',null,{'fdCompanyId':$('[name=fdCompanyId]').val(),'selectType':'person'});
										</xform:dialog>
									</div>
								</td>
							</c:if>
						</tr>
					</c:if>
					<c:if test="${fn:indexOf(fdExtendFields,'3')>-1 }">
						<tr>
							<td class="td_normal_title" width="16.6%">
									${lfn:message('fssc-loan:fsscLoanMain.fdBaseProjectAccounting')}
							</td>
							<td colspan="5" width="83.0%">
								<div id="_xform_fdBaseProjectAccountingId" _xform_type="dialog">
									<xform:dialog propertyName="fdBaseProjectAccountingName" propertyId="fdBaseProjectAccountingId" style="width:95%;" required="true" subject="${lfn:message('fssc-loan:fsscLoanMain.fdBaseProjectAccounting')}">
										dialogSelect(false,'eop_basedata_project_project','fdBaseProjectAccountingId','fdBaseProjectAccountingName',null,{'fdCompanyId':$('[name=fdCompanyId]').val(),'fdProjectType':2});
									</xform:dialog>
								</div>
							</td>
						</tr>
					</c:if>
					<tr>
						<td class="td_normal_title" width="16.6%">
							${lfn:message('fssc-loan:fsscLoanMain.fdLoanMoney')}
						</td>
						<td width="16.6%">
							<%-- 借款金额--%>
							<div id="_xform_fdLoanMoney" _xform_type="text">
								<kmss:showNumber value="${fsscLoanMainForm.fdLoanMoney}" pattern="###,##0.00"></kmss:showNumber><br/>
								<xform:text property="fdLoanMoney" showStatus="noShow"></xform:text>
								<div id="fdLoanUpperMoney"></div>
							</div>
						</td>

						<td class="td_normal_title" width="16.6%">
							${lfn:message('fssc-loan:fsscLoanMain.fdBaseCurrency')}
						</td>
						<td width="16.6%">
							<%-- 币种--%>
							<div id="_xform_fdOffsetterIds" _xform_type="address">
								<xform:dialog propertyId="fdBaseCurrencyId" propertyName="fdBaseCurrencyName" showStatus="view" subject="${lfn:message('fssc-loan:fsscLoanMain.fdBaseCurrency')}" style="width:95%;">
								</xform:dialog>
							</div>
						</td>
						<td class="td_normal_title" width="16.6%">
							${lfn:message('fssc-loan:fsscLoanMain.fdStandardMoney')}
						</td>
						<td width="16.6%">
							<%-- 本币金额--%>
							<div id="_xform_fdStandardMoney" _xform_type="address">
								<xform:text property="fdStandardMoney"  showStatus="view" />
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="16.6%">
							${lfn:message('fssc-loan:fsscLoanMain.fdTotalLoanMoney')}
						</td>
						<td width="16.6%">
							<%-- 累计借款金额--%>
							<div id="_xform_fdTotalLoanMoney" _xform_type="text">
								<kmss:showNumber value="${fsscLoanMainForm.fdTotalLoanMoney}" pattern="###,##0.00"></kmss:showNumber>
							</div>
						</td>
						<td class="td_normal_title" width="16.6%">
							${lfn:message('fssc-loan:fsscLoanMain.fdTotalRepaymentMoney')}
						</td>
						<td width="16.6%">
							<%-- 累计已还款金额--%>
							<div id="_xform_fdTotalRepaymentMoney" _xform_type="text">
								<kmss:showNumber value="${fsscLoanMainForm.fdTotalRepaymentMoney}" pattern="###,##0.00"></kmss:showNumber>
							</div>
						</td>
						<td class="td_normal_title" width="16.6%">
							${lfn:message('fssc-loan:fsscLoanMain.fdTotalNotRepaymentMoney')}
						</td>
						<td width="16.6%">
							<%-- 累计未还款金额--%>
							<div id="_xform_fdTotalNotRepaymentMoney" _xform_type="text">
								<kmss:showNumber value="${fsscLoanMainForm.fdTotalNotRepaymentMoney}" pattern="###,##0.00"></kmss:showNumber>
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="16.6%">
							${lfn:message('fssc-loan:fsscLoanMain.fdFeeMainName')}
						</td>
						<td colspan="5" width="83.0%">
							<%-- 关联事前申请--%>
							<div id="_xform_fdOffsetterIds" _xform_type="address">
								<xform:address propertyId="fdFeeMainId" propertyName="fdFeeMainName" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="view" subject="${lfn:message('fssc-loan:fsscLoanMain.fdFeeMainName')}" style="width:95%;" />
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="16.6%">
							${lfn:message('fssc-loan:fsscLoanMain.fdOffsetters')}
						</td>
						<td colspan="5" width="83.0%">
							<%-- 可冲销者--%>
							<div id="_xform_fdOffsetterIds" _xform_type="address">
								<xform:address propertyId="fdOffsetterIds" propertyName="fdOffsetterNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="view" subject="${lfn:message('fssc-loan:fsscLoanMain.fdOffsetters')}" style="width:95%;" />
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="16.6%">
							${lfn:message('fssc-loan:fsscLoanMain.fdReason')}
						</td>
						<td colspan="5" width="83.0%">
							<%-- 借款事由--%>
							<div id="_xform_fdReason" _xform_type="textarea">
								<xform:textarea property="fdReason" showStatus="view" style="width:95%;" />
							</div>
						</td>
					</tr>
				</table>
				<table class="tb_normal" width="100%">
					<tr>
						<td class="td_normal_title" width="12.5%">
							${lfn:message('fssc-loan:fsscLoanMain.fdBasePayWay')}
						</td>
						<td width="12.5%">
							<%-- 付款方式--%>
							<div id="_xform_fdBasePayWayId" _xform_type="dialog">
								<xform:dialog propertyId="fdBasePayWayId" propertyName="fdBasePayWayName" showStatus="view" style="width:95%;">
									dialogSelect(false,'eop_basedata_pay_way_getPayWay','fdBasePayWayId','fdBasePayWayName');
								</xform:dialog>
							</div>
						</td>
						<td class="td_normal_title" width="12.5%">
							${lfn:message('fssc-loan:fsscLoanMain.fdAccPayeeName')}
						</td>
						<td width="12.5%">
							<%-- 收款账户名--%>
							<div id="_xform_fdAccPayeeName" _xform_type="text">
								<xform:text property="fdAccPayeeName" showStatus="view" style="width:95%;" />
							</div>
						</td>
						<td class="td_normal_title" width="12.5%">
							${lfn:message('fssc-loan:fsscLoanMain.fdPayeeAccount')}
						</td>
						<td width="12.5%">
							<%-- 收款人账户--%>
							<div id="_xform_fdPayeeAccount" _xform_type="text">
								<xform:text property="fdPayeeAccount" showStatus="view" style="width:95%;" />
							</div>
						</td>
						<td class="td_normal_title" width="12.5%">
							${lfn:message('fssc-loan:fsscLoanMain.fdPayeeBank')}
						</td>
						<td width="12.5%">
							<%-- 收款人开户行--%>
							<div id="_xform_fdPayeeBank" _xform_type="text">
								<xform:text property="fdPayeeBank" showStatus="view" style="width:95%;" />
							</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>

		<tr><td>&nbsp;</td>
		</tr>
		<!--自定义表单-->
		<c:if test="${fsscLoanMainForm.docUseXform == 'true' || empty fsscLoanMainForm.docUseXform}">
			<tr>
				<td colspan="6">
					<c:import url="/sys/xform/include/sysForm_view.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="fsscLoanMainForm" />
						<c:param name="fdKey" value="fsscLoanMain" />
						<c:param name="useTab" value="false" />
					</c:import>
				</td>
			</tr>
		</c:if>
		<tr><td>&nbsp;</td>
		</tr>
		<tr><td>
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



		<!-- 交单记录-->
		<c:if test="${not empty presList }">
		<tr>
			<td>
				<table width='100%'  class="tb_normal" >
			<tr>
				<td colspan="12" class="td_normal_title">${lfn:message('fssc-pres:table.fsscPresMain') }</td>
			</tr>
			<tr>
				<td colspan="12" width="100%">
					<table class="tb_normal" width="100%" align="center">
						<tr>
							<td align="center">${lfn:message('fssc-pres:fsscPresMain.fdNumber')}</td>
							<td align="center">${lfn:message('fssc-pres:fsscPresMain.fdName')}</td>
							<td align="center">${lfn:message('fssc-pres:fsscPresMain.fdType')}</td>
							<td align="center">${lfn:message('fssc-pres:fsscPresMain.fdDesc')}</td>
							<td align="center">${lfn:message('fssc-pres:fsscPresMain.docCreateTime')}</td>
							<td align="center">${lfn:message('fssc-pres:fsscPresMain.docCreator')}</td>
						</tr>
						<c:forEach items="${presList}" var="pres" varStatus="vstatus">
							<tr>
								<td align="center">${pres.fdNumber}</td>
								<td align="center">${pres.fdName}</td>
								<td align="center">
									<sunbor:enumsShow enumsType="fssc_pres_type" value="${pres.fdType }"></sunbor:enumsShow>
								</td>
								<td align="center">${pres.fdDesc}</td>
								<td align="center">${pres.docCreateTime}</td>
								<td align="center">${pres.docCreatorName}</td>
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
				</table>
			</td>
		</tr>
		</c:if>

		<c:if test="${saveApproval||param.method=='print'}">
			<tr>
				<td  width="100%">
					<table class="tb_normal" width="75%" align="center">
						<c:import url="/sys/workflow/include/sysWfProcess_log.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="fsscLoanMainForm" />
						</c:import>
					</table>
				</td>
			</tr>
		</c:if>
	</table>
</center>
<script type="text/javascript">
	Com_IncludeFile("jquery.js");
	Com_IncludeFile("document.js", 'style/default/doc/');
</script>
<script type="text/javascript" src="<c:url value="/eop/basedata/resource/js/jquery-barcode.js"/>"></script>
<script>
	Com_IncludeFile("Number.js", "${LUI_ContextPath}/fssc/common/resource/js/", 'js', true);
	function confirmDelete(msg){
		var del = confirm("<bean:message key="page.comfirmDelete"/>");
		return del;
	}
	$(document).ready(function(){
		$("#barcodeTarget").barcode("${fsscLoanMainForm.docNumber}", "code128",{barWidth:1, barHeight:40});
		var loanMoney=$("input[name='fdLoanMoney']").val();
		$("#fdLoanUpperMoney").html(FSSC_MenoyToUppercase(loanMoney));
	});
	window.onload = function(){
		var extendFields = $(".extendFields>td").length;
		$(".extendFields>td").eq(extendFields-1).attr("colspan",1+6-extendFields);
	}
</script>
<%@ include file="/resource/jsp/view_down.jsp"%>
