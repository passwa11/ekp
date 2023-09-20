<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<link rel="stylesheet" href="<c:url value="/eop/basedata/resource/css/print.css"/>" />
<title>${lfn:message('button.print')}-${lfn:message('fssc-expense:msg.sticky.note')}</title>
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
            <p class="txttitle"><c:out value="${fsscExpenseMainForm.docTemplateName}" /></p>
         </div>
         <%--条形码--%>
   		<div id="barcodeTarget" style="float:right;margin-right:40px;margin-top: -20px;" ></div>
     </div>
      <table class="tb_normal" style="width:95%">
				<tr>
				    <!-- 主题  -->
					<td class="td_normal_title" width=12%>
						${lfn:message('fssc-expense:fsscExpenseMain.docSubject')}${lfn:message('fssc-expense:msg.sticky.note')}
					</td>
					<td width="15%" colspan="3">
						<xform:text property="docSubject" style="width:85%" />
					</td>
				    <!-- 单据编号 -->
					<td class="td_normal_title" width=12%>
						${lfn:message('fssc-expense:fsscExpenseMain.docNumber')}
					</td><td width="15%">
                        <xform:text property="docNumber" style="width:85%" />
				    </td>
				</tr>  
				<tr>
					 <!-- 报销人 -->
					<td class="td_normal_title" width=12%>
						${lfn:message('fssc-expense:fsscExpenseMain.fdClaimant')}
					</td><td width="15%">
						<c:out value="${fsscExpenseMain.fdClaimant.fdName}" />
					</td>
					<td class="td_normal_title" width=12%>
						${lfn:message('fssc-expense:fsscExpenseMain.fdClaimantNumber')}
					</td>
					<td>
						<c:out value="${fsscExpenseMain.fdClaimant.fdNo }"></c:out>
					</td>
					<!-- 报销人部门 -->
					<td class="td_normal_title" width=12%>
						${lfn:message('fssc-expense:fsscExpenseMain.fdClaimantDept')}
					</td><td width="15%">
						<c:out value="${fsscExpenseMain.fdClaimantDept.fdName}" />
					</td>
					
				</tr> 
				<tr>
					<!-- 记帐公司  -->
					<td class="td_normal_title" width=12%>
						${lfn:message('fssc-expense:fsscExpenseMain.fdCompany')}
					</td><td width="15%">
						<c:out value="${fsscExpenseMain.fdCompany.fdName}" />(<c:out value="${fsscExpenseMain.fdCompany.fdCode}" />)
					</td>
					<!-- 归属部门  -->
					<td class="td_normal_title" width=12%>
						${lfn:message('fssc-expense:fsscExpenseMain.fdCostCenter')}
					</td><td width="15%" >
						<c:out value="${fsscExpenseMain.fdCostCenter.fdName}" />
					</td>
					<!-- 归属项目  -->
					<td class="td_normal_title" width=12%>
						${lfn:message('fssc-expense:fsscExpenseMain.fdProject')}
					</td><td width="15%" >
						<c:out value="${fsscExpenseMain.fdProject.fdName}" />
					</td>
				</tr>
				<tr>
				    <!-- 合计申请金额(本币)  -->
					<td class="td_normal_title" width=12%>
						${lfn:message('fssc-expense:fsscExpenseMain.fdTotalStandaryMoney') }
					</td><td width="15%" nowrap="nowrap">
					    <kmss:showNumber value="${fsscExpenseMain.fdTotalStandaryMoney}" pattern="###,##0.00"></kmss:showNumber><br/>
						<xform:text property="fdTotalStandaryMoney" showStatus="noShow"></xform:text>
                        <div id="fdTotalStandaryUpperMoney"></div>
					</td>
					<!-- 核准金额合计(本币)   -->
					<td class="td_normal_title" width=12%>
						${lfn:message('fssc-expense:fsscExpenseMain.fdTotalApprovedMoney') }
					</td>
					<td width="15%">
						 <kmss:showNumber value="${fsscExpenseMain.fdTotalApprovedMoney}" pattern="###,##0.00"></kmss:showNumber> <br/>
						 <xform:text property="fdTotalApprovedMoney" showStatus="noShow"></xform:text>
               	 		 <div id="fdTotalApprovedUpperMoney"></div>
					</td>
					  <!-- 填单时间  -->
					  <td class="td_normal_title" width=12%>
						  ${lfn:message('fssc-expense:fsscExpenseMain.docCreateTime')}
					  </td>
					  <td width="15%">
						  <c:out value="${fsscExpenseMainForm.docCreateTime}"></c:out>
					  </td>
				  </tr>
				<tr class="td_normal_title accountDetail" >
						<td  colspan="6">${lfn:message('fssc-expense:table.fsscExpenseAccounts')}</td>
				</tr>
				<tr class="accountDetail">
					<td  colspan="6" width="100%" id="account_detail_of_message">
						<table class="tb_normal" width="100%" id="TABLE_DocList_fdAccountsList_Form" align="center">
						    <tr align="center" class="tr_normal_title">
						        <td width="5%">
						            ${lfn:message('page.serial')}
						        </td>
						        <fssc:checkVersion version="true">
						        <td width="12%">
						            ${lfn:message('fssc-expense:fsscExpenseAccounts.fdCurrency')}
						        </td>
						        </fssc:checkVersion>
						        <td width="12%">
						            ${lfn:message('fssc-expense:fsscExpenseAccounts.fdAccountName')}
						        </td>
						        <td width="12%">
						            ${lfn:message('fssc-expense:fsscExpenseAccounts.fdBankName')}
						        </td>
						        <td width="12%">
						            ${lfn:message('fssc-expense:fsscExpenseAccounts.fdBankAccount')}
						        </td>
						        <td width="12%">
						            ${lfn:message('fssc-expense:fsscExpenseAccounts.fdMoney')}
						        </td>
						    </tr>
						    <c:forEach items="${fsscExpenseMainForm.fdAccountsList_Form}" var="fdAccountsList_FormItem" varStatus="vstatus">
						        <tr KMSS_IsContentRow="1">
						            <td align="center">
						                ${vstatus.index+1}
						            </td>
						            <fssc:checkVersion version="true">
							        <td align="center">
						        	  	${fdAccountsList_FormItem.fdCurrencyName }
							        </td>
							        </fssc:checkVersion>
						            <td align="center">
						                ${fdAccountsList_FormItem.fdAccountName }
						            </td>
						            <td align="center">
					                	${fdAccountsList_FormItem.fdBankName }
						            </td>
						            <td align="center">
						                ${fdAccountsList_FormItem.fdBankAccount }
						            </td>
						            <td align="center">
						                <kmss:showNumber value="${fdAccountsList_FormItem.fdMoney }" pattern="###,##0.00"></kmss:showNumber> 
						            </td>
						        </tr>
						    </c:forEach>
						</table>
					</td>
				</tr>
				<tr>
				    <!-- 报销人签名  -->
					<td class="td_normal_title" width=12%>
						${lfn:message('fssc-expense:fsscExpenseMain.fdClaimant.signName')}
					</td>
					<td width="15%" nowrap="nowrap" colspan="2">
					    
					</td>
					<!-- 打印日期  -->
					<td class="td_normal_title" width=12%>
						${lfn:message('fssc-expense:fsscExpenseMain.print.dateTime')}
					</td>
					<td width="15%" colspan="2">
						<c:out value="${fdPrintTime}"></c:out>
					</td>
				</tr>
		</table>
</center>
<script type="text/javascript">
	Com_IncludeFile("jquery.js");
	Com_IncludeFile("Number.js", "${LUI_ContextPath}/fssc/common/resource/js/", 'js', true);
	$(document).ready(function(){
		var money=$("input[name='fdTotalStandaryMoney']").val();
		$("#fdTotalStandaryUpperMoney").html(FSSC_MenoyToUppercase(money));
		money=$("input[name='fdTotalApprovedMoney']").val();
		$("#fdTotalApprovedUpperMoney").html(FSSC_MenoyToUppercase(money));
	});
</script>
<%-- 条形码公共页面 --%>
<c:import url="/eop/basedata/resource/jsp/barcode.jsp" charEncoding="UTF-8">
	<c:param name="docNumber">${fsscExpenseMainForm.docNumber }</c:param>
</c:import>
<%@ include file="/resource/jsp/view_down.jsp"%>
