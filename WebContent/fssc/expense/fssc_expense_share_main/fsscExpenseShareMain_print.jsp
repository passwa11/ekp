<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet" href="<c:url value="/eop/basedata/resource/style/print.css"/>" />
<title>${lfn:message('button.print') }</title>
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
<style media="print" type="text/css">
	#S_OperationBar{display:none !important;}
</style>
<div id="optBarDiv">
	<%--打印 --%>
	<c:if test="${fsscExpenseMainForm.docStatus != '10'}">
	    <input type="button" value="${lfn:message('button.print') }" onclick="javascript:print();">
	</c:if>
	<input type="button" value="${lfn:message('button.close') }" onclick="Com_CloseWindow();">
</div>
<center>

<div class='lui_form_title_frame'>
    <div class='lui_form_subject'>
       <p class="txttitle">${lfn:message('fssc-expense:table.fsscExpenseShareMain')}</p>
    </div>
    <%--条形码--%>
	<div id="barcodeTarget" style="float:right;margin-right:40px;margin-top: -20px;" ></div>
</div>
      <table class="tb_normal" style="width:95%">
          <tr>
          	<td class="td_normal_title" width="16.6%">
                  ${lfn:message('fssc-expense:fsscExpenseShareMain.docSubject')}
              </td>
              <td colspan="3">
                  <div id="_xform_docSubject" _xform_type="address">
                      <xform:text property="docSubject" style="width:95%"></xform:text>
                  </div>
              </td>
              <td class="td_normal_title" width="16.6%">
                  ${lfn:message('fssc-expense:fsscExpenseShareMain.fdNumber')}
              </td>
              <td>
                  <div id="_xform_fdNumber" _xform_type="text">
                  <c:if test="${empty fsscExpenseShareMainForm.fdNumber}">自动生成</c:if>
                  <c:if test="${not empty fsscExpenseShareMainForm.fdNumber}">${fsscExpenseShareMainForm.fdNumber}</c:if>
                  </div>
              </td>
          </tr>
          <tr>
              <td class="td_normal_title" width="16.6%">
                  ${lfn:message('fssc-expense:fsscExpenseShareMain.fdOperator')}
              </td>
              <td width="16.6%">
                  <div id="_xform_fdOperatorId" _xform_type="address">
                      <xform:address propertyId="fdOperatorId" propertyName="fdOperatorName" orgType="ORG_TYPE_PERSON" showStatus="readOnly" required="true" subject="${lfn:message('fssc-expense:fsscExpenseShareMain.fdOperator')}" style="width:95%;" />
                  </div>
              </td>
              <td class="td_normal_title" width="16.6%">
                  ${lfn:message('fssc-expense:fsscExpenseShareMain.fdOperatorDept')}
              </td>
              <td width="16.6%">
                  <div id="_xform_fdOperatorDeptId" _xform_type="address">
                      <xform:address propertyId="fdOperatorDeptId" propertyName="fdOperatorDeptName" orgType="ORG_TYPE_DEPT" showStatus="readOnly" required="true" subject="${lfn:message('fssc-expense:fsscExpenseShareMain.fdOperator')}" style="width:95%;" />
                  </div>
              </td>
              <td class="td_normal_title" width="16.6%">
                  ${lfn:message('fssc-expense:fsscExpenseShareMain.fdOperateDate')}
              </td>
              <td width="16.6%">
                  <div id="_xform_docCreateTime">
                      <xform:datetime property="fdOperateDate" showStatus="view"/>
                  </div>
              </td>
          </tr>
		  <tr>
			  <td class="td_normal_title" width="16.6%">
				  ${lfn:message('fssc-expense:fsscExpenseShareMain.fdModelName')}
			  </td>
			  <td colspan="5" width="83.0%">
				  <div id="_xform_fdModelId" _xform_type="dialog">
					  <xform:dialog required="true" propertyName="fdModelName" propertyId="fdModelId" subject="${lfn:message('fssc-expense:fsscExpenseShareMain.fdModelName') }" style="width:95%;">
						  dialogSelect(false,'fssc_expense_main_getExpenseMain','fdModelId','fdModelName',null,{docTemplateId:'${docTemplate.fdId}'},FSSC_AfterExpenseMainSelected);
					  </xform:dialog>
				  </div>
			  </td>
		  </tr>
          <tr>
              <td class="td_normal_title" width="16.6%">
                  ${lfn:message('fssc-expense:fsscExpenseShareMain.fdDescription')}
              </td>
              <td colspan="5" width="83.0%">
                  <div id="_xform_fdContent" _xform_type="textarea">
                      <xform:textarea property="fdDescription" showStatus="view" style="width:95%;height:50px;" />
                  </div>
              </td>
          </tr>
          <tr>
          	<td colspan="6">${lfn:message('fssc-expense:table.fsscExpenseDetail') }</td>
          </tr>
          <tr>
          	<td colspan="6">
          		<table class="tb_normal" width="100%" id="TABLE_EXPENSE">
          			<tr>
          				<td class="td_normal_title" align="center">${lfn:message('page.serial') }</td>
          				<td class="td_normal_title" align="center">${lfn:message('fssc-expense:fsscExpenseDetail.fdCompany') }</td>
          				<td class="td_normal_title" align="center">${lfn:message('fssc-expense:fsscExpenseDetail.fdCostCenter') }</td>
          				<td class="td_normal_title" align="center">${lfn:message('fssc-expense:fsscExpenseDetail.fdExpenseItem') }</td>
          				<td class="td_normal_title" align="center">${lfn:message('fssc-expense:fsscExpenseShareDetail.fdRealUser') }</td>
          				<td class="td_normal_title" align="center">${lfn:message('fssc-expense:fsscExpenseDetail.fdHappenDate') }</td>
          				<td class="td_normal_title" align="center">${lfn:message('fssc-expense:fsscExpenseDetail.fdApplyMoney') }</td>
          				<td class="td_normal_title" align="center">${lfn:message('fssc-expense:fsscExpenseDetail.fdCurrency') }</td>
          				<td class="td_normal_title" align="center">${lfn:message('fssc-expense:fsscExpenseDetail.fdStandardMoney') }</td>
          				<td class="td_normal_title" align="center">${lfn:message('fssc-expense:fsscExpenseDetail.fdUse') }</td>
          			</tr>
          		</table>
          	</td>
          </tr>
          <tr>
          	<td colspan="6">${lfn:message('fssc-expense:fsscExpenseShareMain.fdDetailList') }</td>
          </tr>
          <tr>
          	<td colspan="6">
          		<table class="tb_normal" width="100%" id="TABLE_DocList_fdDetailList_Form" align="center" tbdraggable="true">
				    <tr align="center" class="tr_normal_title">
				        <td style="width:40px;">
				            ${lfn:message('page.serial')}
				        </td>
				        <td width="15%">
				            ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdCompany')}
				        </td>
				        <td width="13%">
				            ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdCostCenter')}
				        </td>
				        <td width="12%">
				            ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdExpenseItem')}
				        </td>
				        <td width="10%">
				            ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdHappenDate')}
				        </td>
				        <td width="10%">
				            ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdMoney')}
				        </td>
				        <td width="10%">
				            ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdCurrency')}
				        </td>
				        <td width="10%">
				            ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdStandardMoney')}
				        </td>
				        <td width="15%">
				            ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdRemark')}
				        </td>
				    </tr>
				    <c:forEach items="${fsscExpenseShareMainForm.fdDetailList_Form}" var="fdDetailList_FormItem" varStatus="vstatus">
				        <tr KMSS_IsContentRow="1">
				            <td align="center">
				                ${vstatus.index+1}
				            </td>
				            <td align="center">
					            <input type="hidden" name="fdDetailList_Form[${vstatus.index }].fdId" value="" disabled="true" />
					            <xform:dialog propertyName="fdDetailList_Form[${vstatus.index }].fdCompanyName" required="true" propertyId="fdDetailList_Form[!{index}].fdCompanyId" showStatus="view" subject="${lfn:message('fssc-expense:fsscExpenseShareDetail.fdCompany')}" validators=" maxLength(200)" style="width:85%;" >
					            	FSSC_SelectInvoiceCompany(${vstatus.index });
					            </xform:dialog>
					        </td>
					        <td align="center">
					            <div id="_xform_fdDetailList_Form[${vstatus.index }].fdCostCenterId" _xform_type="dialog">
					                <xform:dialog propertyId="fdDetailList_Form[${vstatus.index }].fdCostCenterId" propertyName="fdDetailList_Form[${vstatus.index }].fdCostCenterName" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdCostCenter')}" style="width:90%;">
					                    FSSC_SelectCostCenter(!{index});
					                </xform:dialog>
					            </div>
					        </td>
					        <td align="center">
					            ${fdDetailList_FormItem.fdExpenseItemName }
					        </td>
					        <td align="center">
					            ${fdDetailList_FormItem.fdHappenDate }
					        </td>
					        <td align="center">
					            <div id="_xform_fdDetailList_Form[${vstatus.index }].fdMoney" _xform_type="text" class="vat${vstatus.index }">
					                <kmss:showNumber value="${fdDetailList_FormItem.fdMoney }" pattern="0.00"/>
					            </div>
					        </td>
					        <td align="center">
					            <div id="_xform_fdDetailList_Form[${vstatus.index }].fdCurrency" _xform_type="text" class="vat${vstatus.index }">
					                <xform:text property="fdDetailList_Form[${vstatus.index }].fdCurrencyName" subject="${lfn:message('fssc-expense:fsscExpenseShareDetail.fdCurrency') }" showStatus="view" style="width:85%;" />
					            </div>
					        </td>
					        <td align="center">
					            <div id="_xform_fdDetailList_Form[${vstatus.index }].fdStandardMoney" _xform_type="text" class="vat${vstatus.index }">
					                <kmss:showNumber value="${fdDetailList_FormItem.fdStandardMoney }" pattern="0.00"/>
					            </div>
					        </td>
					        <td align="center">
					            <div id="_xform_fdDetailList_Form[${vstatus.index }].fdRemark" _xform_type="text" class="vat${vstatus.index }">
					                <xform:text property="fdDetailList_Form[${vstatus.index }].fdRemark" subject="${lfn:message('fssc-expense:fsscExpenseShareDetail.fdRemark') }" showStatus="view" style="width:80%;" />
					            </div>
					        </td>
				        </tr>
				    </c:forEach>
				</table>
          	</td>
          </tr>
          <tr>
			<td class="td_normal_title"  colspan="6">
				<bean:message bundle="sys-lbpmservice" key="lbpmProcess.history.description.show" />
			</td>
		</tr>
		
		<tr>
			<td colspan="6">
				<c:import url="/sys/workflow/include/sysWfProcess_log.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="fsscExpenseShareMainForm" />
				</c:import>
			</td>
		</tr>
      </table>
</center>
<html:hidden property="fdShareType" value="${fdShareType}" />
<script type="text/javascript">
	Com_IncludeFile("jquery.js");
	Com_AddEventListener(window,'load',function(){
		FSSC_AfterExpenseMainSelected([{fdId:'${fsscExpenseShareMainForm.fdModelId}'}]);
	})
	window.FSSC_AfterExpenseMainSelected = function(rtn){
		var fdShareType= $("[name=fdShareType]").val();
		if(!rtn){
			return;
		}
		$.post(
				'${LUI_ContextPath}/fssc/expense/fssc_expense_main/fsscExpenseMainData.do?method=getExpenseDetail',
				{fdMainId:rtn[0].fdId, fdShareType:fdShareType},
				function(data){
					$("#TABLE_EXPENSE tr:eq(0)").after(data);
				}
		);
	}
</script>
<%-- 条形码公共页面 --%>
<c:import url="/eop/basedata/resource/jsp/barcode.jsp" charEncoding="UTF-8">
	<c:param name="docNumber">${fsscExpenseShareMainForm.fdNumber }</c:param>
</c:import>
<%@ include file="/resource/jsp/view_down.jsp"%>
