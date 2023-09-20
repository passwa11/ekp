<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<ui:content title="${lfn:message('fssc-expense:table.fsscExpenseAccounts') }" expand="true">
<table class="tb_normal" width="100%" id="TABLE_DocList_fdAccountsList_Form" align="center" tbdraggable="true">
    <tr align="center" class="tr_normal_title">
        <td width="5%">
            ${lfn:message('page.serial')}
        </td>
        <td width="12%">
            ${lfn:message('fssc-expense:fsscExpenseAccounts.fdPayWay')}
        </td>
        <fssc:checkVersion version="true">
        <td width="12%">
            ${lfn:message('fssc-expense:fsscExpenseDetail.fdCurrency')}/${lfn:message('fssc-expense:fsscExpenseDetail.fdExchangeRate')}
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
        <fssc:checkUseBank fdBank="BOC">
	        <td width="12%">
	            ${lfn:message('fssc-expense:fsscExpenseAccounts.fdBankAccountNo')}
	        </td>
        </fssc:checkUseBank>
        <fssc:checkUseBank fdBank="CMB,CBS,CMInt">
	        <td width="12%">
	            ${lfn:message('fssc-expense:fsscExpenseAccounts.fdAccountAreaName')}
	        </td>
        </fssc:checkUseBank>
        <td width="12%">
            ${lfn:message('fssc-expense:fsscExpenseAccounts.fdMoney')}
        </td>
        <td width="5%">
        	<img src="${KMSS_Parameter_StylePath}icons/add.gif" border="0" style="cursor:pointer;" onclick="DocList_AddRow();FSSC_SetDefaultCurrency()"/>
        </td>
    </tr>
    <tr KMSS_IsReferRow="1" style="display:none;">
        <td align="center" KMSS_IsRowIndex="1">
            !{index}
        </td>
        <td align="center">
        	<div id="_xform_fdAccountsList_Form[!{index}].fdPayWay" _xform_type="text">
              <xform:dialog subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdPayWay') }" required="true" propertyId="fdAccountsList_Form[!{index}].fdPayWayId" propertyName="fdAccountsList_Form[!{index}].fdPayWayName" showStatus="edit" style="width:90%;">
                  FSSC_SelectPayWay(!{index});
              </xform:dialog>
        	</div>
        	<xform:text property="fdAccountsList_Form[!{index}].fdId" showStatus="noShow"/>
        	<xform:text property="fdAccountsList_Form[!{index}].fdBankId" showStatus="noShow"/>
        	<xform:text property="fdAccountsList_Form[!{index}].fdIsTransfer" showStatus="noShow"/>
        	<fssc:checkVersion version="false">
	        <xform:text property="fdAccountsList_Form[!{index}].fdExchangeRate" showStatus="noShow"/>
        	<xform:text property="fdAccountsList_Form[!{index}].fdCurrencyId" showStatus="noShow"/>
        	</fssc:checkVersion>
        </td>
        <fssc:checkVersion version="true">
        <td align="center">
        	<div id="_xform_fdAccountsList_Form[!{index}].fdCurrency" _xform_type="text">
              <xform:dialog subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdCurrency') }" required="true" propertyId="fdAccountsList_Form[!{index}].fdCurrencyId" propertyName="fdAccountsList_Form[!{index}].fdCurrencyName" showStatus="edit" style="width:90%;">
                  FSSC_SelectPayCurrency(!{index});
              </xform:dialog>
              <xform:text property="fdAccountsList_Form[!{index}].fdExchangeRate" showStatus="noShow"/>
        	</div>
        </td>
        </fssc:checkVersion>
        <td align="center"> 
            <div id="_xform_fdAccountsList_Form[!{index}].fdAccountId" _xform_type="dialog">
                <div class="inputselectsgl"  style="width:90%;" >
                	<input name="fdAccountsList_Form[!{index}].fdAccountId" value="" type="hidden">
                	<div class="input">
                		<input subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdAccountName') }" name="fdAccountsList_Form[!{index}].fdAccountName"  >
                	</div>
                	<div class="selectitem" onclick="FSSC_SelectAccount(!{index});"></div>
                </div>
                <span class="txtstrong vat_!{index}">*</span>
            </div>
        </td>
        <td align="center">
            <div id="_xform_fdAccountsList_Form[!{index}].fdBankName" _xform_type="text">
                <xform:text property="fdAccountsList_Form[!{index}].fdBankName" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdBankName')}" style="width:85%;" />
            	<span class="txtstrong vat_!{index}">*</span>
            </div>
        </td>
        <td align="center">
            <div id="_xform_fdAccountsList_Form[!{index}].fdBankAccount" _xform_type="text">
                <xform:text property="fdAccountsList_Form[!{index}].fdBankAccount" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdBankAccount')}" style="width:85%;" />
            	<span class="txtstrong vat_!{index}">*</span>
            </div>
        </td>
         <fssc:checkUseBank fdBank="BOC"> 
         	<td align="center">
         		<div id="_xform_fdAccountsList_Form[!{index}].fdBankAccountNo" _xform_type="text">
                	<xform:text property="fdAccountsList_Form[!{index}].fdBankAccountNo" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdBankAccountNo')}" style="width:85%;" />
            		<span class="txtstrong vat_!{index}">*</span>
            	</div>
            </td>
         </fssc:checkUseBank>
         <fssc:checkUseBank fdBank="CMB">
         	 <td align="center">
		 		 <div id="_xform_fdAccountsList_Form[!{index}].fdAccountAreaCode" _xform_type="text">
		             <xform:dialog propertyId="fdAccountsList_Form[!{index}].fdAccountAreaCode" propertyName="fdAccountsList_Form[!{index}].fdAccountAreaName" subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdAccountAreaName')}" showStatus="edit" style="width:90%;">
		                 FSSC_SelectAccountArea();
		             </xform:dialog>
		             <span class="txtstrong vat_!{index}">*</span>
		         </div>
             </td>
          </fssc:checkUseBank>
        <fssc:checkUseBank fdBank="CBS">
            <td align="center">
                <div id="_xform_fdAccountsList_Form[!{index}].fdAccountAreaCode" _xform_type="text">
                    <xform:dialog propertyId="fdAccountsList_Form[!{index}].fdAccountAreaCode" propertyName="fdAccountsList_Form[!{index}].fdAccountAreaName" subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdAccountAreaName')}" showStatus="edit" style="width:90%;">
                        FSSC_SelectCbsAccountArea();
                    </xform:dialog>
                    <span class="txtstrong vat_!{index}">*</span>
                </div>
            </td>
        </fssc:checkUseBank>
        <fssc:checkUseBank fdBank="CMInt">
            <td align="center">
                <div id="_xform_fdAccountsList_Form[!{index}].fdAccountAreaCode" _xform_type="text">
                    <xform:dialog propertyId="fdAccountsList_Form[!{index}].fdAccountAreaCode" propertyName="fdAccountsList_Form[!{index}].fdAccountAreaName" subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdAccountAreaName')}" showStatus="edit" style="width:90%;">
                        FSSC_SelectCmbIntAccountArea();
                    </xform:dialog>
                    <span class="txtstrong vat_!{index}">*</span>
                </div>
            </td>
        </fssc:checkUseBank>
        <td align="center">
            <div id="_xform_fdAccountsList_Form[!{index}].fdMoney" _xform_type="text">
                <xform:text property="fdAccountsList_Form[!{index}].fdMoney" required="true" validators="currency-dollar min(0)" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdMoney')}" style="width:85%;" />
            </div>
        </td>
        <td align="center">
            <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
                <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
            </a>
        </td>
    </tr>
    <c:forEach items="${fsscExpenseMainForm.fdAccountsList_Form}" var="fdAccountsList_FormItem" varStatus="vstatus">
        <tr KMSS_IsContentRow="1">
            <td align="center">
                ${vstatus.index+1}
            </td>
            <td align="center">
	        	<div id="_xform_fdAccountsList_Form[${vstatus.index}].fdPayWay" _xform_type="text">
	              <xform:dialog subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdPayWay') }" required="true" propertyId="fdAccountsList_Form[${vstatus.index}].fdPayWayId" propertyName="fdAccountsList_Form[${vstatus.index}].fdPayWayName" showStatus="edit" style="width:90%;">
	                  FSSC_SelectPayWay(${vstatus.index});
	              </xform:dialog>
	              <xform:text property="fdAccountsList_Form[${vstatus.index}].fdId" showStatus="noShow"/>
	              <xform:text property="fdAccountsList_Form[${vstatus.index}].fdBankId" showStatus="noShow"/>
	              <xform:text property="fdAccountsList_Form[${vstatus.index}].fdIsTransfer" showStatus="noShow"/>
	        	</div>
	        	<fssc:checkVersion version="false">
	        	<xform:text property="fdAccountsList_Form[${vstatus.index}].fdExchangeRate" showStatus="noShow"/>
	        	<xform:text property="fdAccountsList_Form[${vstatus.index}].fdCurrencyId" showStatus="noShow"/>
	        	</fssc:checkVersion>
	        </td>
	        <fssc:checkVersion version="true">
	        <td align="center">
	        	<div id="_xform_fdAccountsList_Form[${vstatus.index}].fdCurrency" _xform_type="text">
	              <xform:dialog subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdCurrency') }" required="true" propertyId="fdAccountsList_Form[${vstatus.index}].fdCurrencyId" propertyName="fdAccountsList_Form[${vstatus.index}].fdCurrencyName" showStatus="edit" style="width:90%;">
	                  FSSC_SelectPayCurrency(${vstatus.index});
	              </xform:dialog>
	              <xform:text property="fdAccountsList_Form[${vstatus.index}].fdExchangeRate" showStatus="noShow"/>
	        	</div>
	        </td>
	        </fssc:checkVersion>
            <td align="center">
                <div id="_xform_fdAccountsList_Form[${vstatus.index}].fdAccountId" _xform_type="dialog">
	                <div class="inputselectsgl"  style="width:90%;" >
	                	<input name="fdAccountsList_Form[${vstatus.index}].fdAccountId" value="" type="hidden" value="${fdAccountsList_FormItem.fdAccountId }">
	                	<div class="input">
	                		<input subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdAccountName') }" name="fdAccountsList_Form[${vstatus.index}].fdAccountName" value="${fdAccountsList_FormItem.fdAccountName }">
	                	</div>
	                	<div class="selectitem" onclick="FSSC_SelectAccount(${vstatus.index});"></div>
	                </div>
                    <span class="txtstrong vat_${vstatus.index}">*</span>
            	</div>
            </td>
            <td align="center">
                <div id="_xform_fdAccountsList_Form[${vstatus.index}].fdBankName" _xform_type="text">
                    <xform:text property="fdAccountsList_Form[${vstatus.index}].fdBankName" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdBankName')}" style="width:85%;" />
                	<span class="txtstrong vat_${vstatus.index}">*</span>
                </div>
            </td>
            <td align="center">
                <div id="_xform_fdAccountsList_Form[${vstatus.index}].fdBankAccount" _xform_type="text">
                    <xform:text property="fdAccountsList_Form[${vstatus.index}].fdBankAccount" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdBankAccount')}" style="width:85%;" />
                	<span class="txtstrong vat_${vstatus.index}">*</span>
                </div>
            </td>
              <fssc:checkUseBank fdBank="BOC">
             	 <td align="center">
                	<div id="_xform_fdAccountsList_Form[${vstatus.index}].fdBankAccountNo" _xform_type="text">
                    	<xform:text property="fdAccountsList_Form[${vstatus.index}].fdBankAccountNo" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdBankAccountNo')}" style="width:85%;" />
                		<span class="txtstrong vat_${vstatus.index}">*</span>
                	</div>
            	</td>
             </fssc:checkUseBank>
             <fssc:checkUseBank fdBank="CMB">
             	<td align="center">
			 		<div id="_xform_fdAccountsList_Form[${vstatus.index}].fdAccountAreaCode" _xform_type="dialog">
	                    <xform:dialog propertyId="fdAccountsList_Form[${vstatus.index}].fdAccountAreaCode" propertyName="fdAccountsList_Form[${vstatus.index}].fdAccountAreaName" subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdAccountAreaName')}" showStatus="edit" style="width:90%;">
	                        FSSC_SelectAccountArea();
	                    </xform:dialog>
	                    <span class="txtstrong vat_${vstatus.index}">*</span>
	                </div>
                </td>
            </fssc:checkUseBank>
            <fssc:checkUseBank fdBank="CBS">
                <td align="center">
                    <div id="_xform_fdAccountsList_Form[${vstatus.index}].fdAccountAreaCode" _xform_type="dialog">
                        <xform:dialog propertyId="fdAccountsList_Form[${vstatus.index}].fdAccountAreaCode" propertyName="fdAccountsList_Form[${vstatus.index}].fdAccountAreaName" subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdAccountAreaName')}" showStatus="edit" style="width:90%;">
                            FSSC_SelectCbsAccountArea();
                        </xform:dialog>
                        <span class="txtstrong vat_${vstatus.index}">*</span>
                    </div>
                </td>
            </fssc:checkUseBank>
            <fssc:checkUseBank fdBank="CMInt">
                <td align="center">
                    <div id="_xform_fdAccountsList_Form[${vstatus.index}].fdAccountAreaCode" _xform_type="dialog">
                        <xform:dialog propertyId="fdAccountsList_Form[${vstatus.index}].fdAccountAreaCode" propertyName="fdAccountsList_Form[${vstatus.index}].fdAccountAreaName" subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdAccountAreaName')}" showStatus="edit" style="width:90%;">
                            FSSC_SelectCmbIntAccountArea();
                        </xform:dialog>
                        <span class="txtstrong vat_${vstatus.index}">*</span>
                    </div>
                </td>
            </fssc:checkUseBank>
            <td align="center">
                <div id="_xform_fdAccountsList_Form[${vstatus.index}].fdMoney" _xform_type="text">
                   <input type="text" name="fdAccountsList_Form[${vstatus.index}].fdMoney" value="<kmss:showNumber value="${fdAccountsList_FormItem.fdMoney }" pattern="0.00"/>" class="inputsgl" validate="required currency-dollar min(0)"  subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdMoney')}"  style="width:85%;"/>
                	<span class="txtstrong">*</span>
                </div>
            </td>
            <td align="center">
                <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
                    <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
                </a>
            </td>
        </tr>
    </c:forEach>
</table>
</ui:content>
<br/>