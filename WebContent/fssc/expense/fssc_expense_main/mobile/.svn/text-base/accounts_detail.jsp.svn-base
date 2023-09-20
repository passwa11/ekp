<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<div data-dojo-type="mui/table/ScrollableHContainer" >
	<div data-dojo-type="mui/table/ScrollableHView" style="margin-top:20px;" id="dt_wrap_accounts_hview">
		    <table class="muiNormal" id="TABLE_DocList_fdAccountsList_Form" width="100%" border="0" cellspacing="0" cellpadding="0">
		         <tr align="center" class="tr_normal_title">
			        <td width="4%">
			            ${lfn:message('page.serial')}
			        </td>
			        <td width="12%">
			            ${lfn:message('fssc-expense:fsscExpenseAccounts.fdPayWay')}
			        </td>
			        <td width="12%">
			            ${lfn:message('fssc-expense:fsscExpenseAccounts.fdBank')}
			        </td>
			        <fssc:checkVersion version="true">
			        <td width="10%">
			            ${lfn:message('fssc-expense:fsscExpenseDetail.fdCurrency')}/${lfn:message('fssc-expense:fsscExpenseDetail.fdExchangeRate')}
			        </td>
			        </fssc:checkVersion>
			        <td width="10%">
			            ${lfn:message('fssc-expense:fsscExpenseAccounts.fdAccountName')}
			        </td>
			        <td width="12%">
			            ${lfn:message('fssc-expense:fsscExpenseAccounts.fdBankName')}
			        </td>
			        <td width="12%">
			            ${lfn:message('fssc-expense:fsscExpenseAccounts.fdBankAccount')}
			        </td>
			        <fssc:checkUseBank fdBank="BOC">
				        <td width="10%">
				            ${lfn:message('fssc-expense:fsscExpenseAccounts.fdBankAccountNo')}
				        </td>
			        </fssc:checkUseBank>
			          <fssc:checkUseBank fdBank="CMB,CBS,CMInt">
				        <td width="10%">
				            ${lfn:message('fssc-expense:fsscExpenseAccounts.fdAccountAreaName')}
				        </td>
			        </fssc:checkUseBank>
			        <td width="12%">
			            ${lfn:message('fssc-expense:fsscExpenseAccounts.fdMoney')}
			        </td>
			    </tr>
			    <tr KMSS_IsReferRow="1" style="display:none;">
			    </tr>
			    <c:forEach items="${fsscExpenseMainForm.fdAccountsList_Form}" var="fdAccountsList_FormItem" varStatus="vstatus">
			        <tr KMSS_IsContentRow="1">
			            <td align="center">
			                ${vstatus.index+1}
			            </td>
			            <td align="center">
			            	<input type="hidden" name="fdAccountsList_Form[${vstatus.index}].fdId" value="${fdAccountsList_FormItem.fdId }">
			            	<fssc:checkVersion version="false">
				        	<xform:text property="fdAccountsList_Form[${vstatus.index}].fdExchangeRate" showStatus="noShow"/>
				        	<xform:text property="fdAccountsList_Form[${vstatus.index}].fdCurrencyId" showStatus="noShow"/>
				        	</fssc:checkVersion>
				        	<div id="_xform_fdAccountsList_Form[${vstatus.index}].fdPayWay" _xform_type="text">
				            	<xform:dialog subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdPayWay') }" showStatus="view" required="true" propertyId="fdAccountsList_Form[${vstatus.index}].fdPayWayId" propertyName="fdAccountsList_Form[${vstatus.index}].fdPayWayName" style="width:90%;">
				                  FSSC_SelectPayWay(${vstatus.index});
				              </xform:dialog>
				        	</div>
				        </td>
				        <td align="center">
				        	<div id="_xform_fdAccountsList_Form[${vstatus.index}].fdBank" _xform_type="text">
				              ${fdAccountsList_FormItem.fdPayBankName }
				        	</div>
				        </td>
				        <fssc:checkVersion version="true">
				        <td align="center">
				        	<div id="_xform_fdAccountsList_Form[${vstatus.index}].fdCurrency" _xform_type="text">
				              	<input type="hidden" name="fdAccountsList_Form[${vstatus.index}].fdCurrencyId" value="${fdAccountsList_FormItem.fdCurrencyId }"/>
				              		${fdAccountsList_FormItem.fdCurrencyName }
				              <input type="hidden" name="fdAccountsList_Form[${vstatus.index}].fdExchangeRate" value="${fdAccountsList_FormItem.fdExchangeRate }"/>
				        	</div>
				        </td>
				        </fssc:checkVersion>
			            <td align="center">
			                <div id="_xform_fdAccountsList_Form[${vstatus.index}].fdAccountId" _xform_type="dialog">
			               		<input name="fdAccountsList_Form[${vstatus.index}].fdAccountId" type="hidden" value="${fdAccountsList_FormItem.fdAccountId }">
			               		${fdAccountsList_FormItem.fdAccountName }
			            	</div>
			            </td>
			            <td align="center">
			                <div id="_xform_fdAccountsList_Form[${vstatus.index}].fdBankName" _xform_type="text">
			                    	<xform:text property="fdAccountsList_Form[${vstatus.index}].fdBankName" required="true" subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdBankName')}" validators=" maxLength(400)" style="width:85%;" />
			                </div>
			            </td>
			            <td align="center">
			                <div id="_xform_fdAccountsList_Form[${vstatus.index}].fdBankAccount" _xform_type="text">
			                    <xform:text property="fdAccountsList_Form[${vstatus.index}].fdBankAccount" showStatus="view" required="true" subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdBankAccount')}" validators=" maxLength(200)" style="width:85%;" />
			                </div>
			            </td>
			            <fssc:checkUseBank fdBank="BOC">
			            	<td align="center">
				             <div id="_xform_fdAccountsList_Form[${vstatus.index}].fdBankAccountNo" _xform_type="text">
				                    <xform:text property="fdAccountsList_Form[${vstatus.index}].fdBankAccountNo" showStatus="view" required="true" subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdBankAccountNo')}" validators=" maxLength(200)" style="width:85%;" />
				              </div>
			                </td>
			            </fssc:checkUseBank>
			           <fssc:checkUseBank fdBank="CMB,CBS,CMInt">
							 	<td  align="center">
			                       <input name="fdAccountsList_Form[${vstatus.index}].fdAccountAreaCode" value="${fdAccountsList_FormItem.fdAccountAreaCode }" type="hidden"/>
			                    	<xform:text property="fdAccountsList_Form[${vstatus.index}].fdAccountAreaName" showStatus="view" required="true" subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdAccountAreaName')}" />
			                 	</td>
			            </fssc:checkUseBank>
			            <td align="center">
			                <div id="_xform_fdAccountsList_Form[${vstatus.index}].fdMoney" _xform_type="text">
			                    <kmss:showNumber value="${fdAccountsList_FormItem.fdMoney }" pattern="0.00"/>
			                	<html:hidden property="fdAccountsList_Form[${vstatus.index}].fdMoney" value="${fdAccountsList_FormItem.fdMoney }"/>
			                </div>
			            </td>
			        </tr>
			    </c:forEach>
            </table>
		<input type="hidden" name="fdAccountsList_Flag" value="1">
	</div>
</div>


