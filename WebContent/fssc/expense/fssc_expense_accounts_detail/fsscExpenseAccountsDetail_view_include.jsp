<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<ui:content title="${lfn:message('fssc-expense:table.fsscExpenseAccounts') }" expand="true">
<table class="tb_normal" width="100%" id="TABLE_DocList_fdAccountsList_Form" align="center">
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
            	<xform:text property="fdAccountsList_Form[${vstatus.index}].fdIsTransfer" showStatus="noShow"/>
            	<fssc:checkVersion version="false">
	        	<xform:text property="fdAccountsList_Form[${vstatus.index}].fdExchangeRate" showStatus="noShow"/>
	        	<xform:text property="fdAccountsList_Form[${vstatus.index}].fdCurrencyId" showStatus="noShow"/>
	        	</fssc:checkVersion>
	        	<div id="_xform_fdAccountsList_Form[${vstatus.index}].fdPayWay" _xform_type="text">
	        	<c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine!='true' }">
	              <xform:dialog subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdPayWay') }" required="true" propertyId="fdAccountsList_Form[${vstatus.index}].fdPayWayId" propertyName="fdAccountsList_Form[${vstatus.index}].fdPayWayName" style="width:90%;">
	                  FSSC_SelectPayWay(${vstatus.index});
	              </xform:dialog>
	              <xform:text property="fdAccountsList_Form[${vstatus.index}].fdPayWayId" showStatus="noShow"></xform:text>
	            </c:if>
	            <c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine=='true' }">
	            	<xform:dialog subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdPayWay') }" showStatus="edit" required="true" propertyId="fdAccountsList_Form[${vstatus.index}].fdPayWayId" propertyName="fdAccountsList_Form[${vstatus.index}].fdPayWayName" style="width:90%;">
	                  FSSC_SelectPayWay(${vstatus.index});
	              </xform:dialog>
	            </c:if>
	        	</div>
	        </td>
	        <td align="center">
	        	<div id="_xform_fdAccountsList_Form[${vstatus.index}].fdBank" _xform_type="text">
	        	<c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine=='true' }">
	              <xform:dialog subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdBank') }" showStatus="edit" propertyId="fdAccountsList_Form[${vstatus.index}].fdBankId" propertyName="fdAccountsList_Form[${vstatus.index}].fdPayBankName" style="width:90%;">
	                  FSSC_SelectPayBank();
	              </xform:dialog>
	            </c:if>
	            <c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine!='true' }">
	              ${fdAccountsList_FormItem.fdPayBankName }
	              <xform:text property="fdAccountsList_Form[${vstatus.index}].fdBankId" showStatus="noShow"></xform:text>
	              <xform:text property="fdAccountsList_Form[${vstatus.index}].fdBankName" showStatus="noShow"></xform:text>
	            </c:if>
	        	</div>
	        </td>
	        <fssc:checkVersion version="true">
	        <td align="center">
	        	<div id="_xform_fdAccountsList_Form[${vstatus.index}].fdCurrency" _xform_type="text">
	        	  <c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine=='true' }">
	              <xform:dialog subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdCurrency') }" showStatus="edit" required="true" propertyId="fdAccountsList_Form[${vstatus.index}].fdCurrencyId" propertyName="fdAccountsList_Form[${vstatus.index}].fdCurrencyName" style="width:90%;">
	                  FSSC_SelectPayCurrency(${vstatus.index});
	              </xform:dialog>
	              </c:if>
	              <c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine!='true' }">
	              	<input type="hidden" name="fdAccountsList_Form[${vstatus.index}].fdCurrencyId" value="${fdAccountsList_FormItem.fdCurrencyId }"/>
	              		${fdAccountsList_FormItem.fdCurrencyName }
	              </c:if>
	              <input type="hidden" name="fdAccountsList_Form[${vstatus.index}].fdExchangeRate" value="${fdAccountsList_FormItem.fdExchangeRate }"/>
	        	</div>
	        </td>
	        </fssc:checkVersion>
            <td align="center">
                <div id="_xform_fdAccountsList_Form[${vstatus.index}].fdAccountId" _xform_type="dialog">
	                <c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine!='true' }">
	               		<input name="fdAccountsList_Form[${vstatus.index}].fdAccountId" type="hidden" value="${fdAccountsList_FormItem.fdAccountId }">
	               		${fdAccountsList_FormItem.fdAccountName }
	               		<xform:text property="fdAccountsList_Form[${vstatus.index}].fdAccountName" showStatus="noShow"></xform:text>
	               	</c:if>
	               	<c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine=='true' }">
               		<div class="inputselectsgl"  style="width:90%;" >
	                	<input name="fdAccountsList_Form[${vstatus.index}].fdAccountId" value="" type="hidden" value="${fdAccountsList_FormItem.fdAccountId }">
	                	<div class="input">
	                		<input subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdAccountName') }" name="fdAccountsList_Form[${vstatus.index}].fdAccountName" value="${fdAccountsList_FormItem.fdAccountName }">
	                	</div>
	                	<div class="selectitem" onclick="FSSC_SelectAccount(${vstatus.index});"></div>
	                </div>
	                <span class="txtstrong vat_${vstatus.index}">*</span>
	                </c:if>
            	</div>
            </td>
            <td align="center">
                <div id="_xform_fdAccountsList_Form[${vstatus.index}].fdBankName" _xform_type="text">
                	<c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine=='true' }">
                    	<xform:text property="fdAccountsList_Form[${vstatus.index}].fdBankName" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdBankName')}" style="width:85%;" />
                		<span class="txtstrong vat_${vstatus.index}">*</span>
                	</c:if>
                	<c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine!='true' }">
                    	<xform:text property="fdAccountsList_Form[${vstatus.index}].fdBankName" required="true" subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdBankName')}" validators=" maxLength(400)" style="width:85%;" />
                	</c:if>
                </div>
            </td>
            <td align="center">
                <div id="_xform_fdAccountsList_Form[${vstatus.index}].fdBankAccount" _xform_type="text">
                <c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine=='true' }">
                    <xform:text property="fdAccountsList_Form[${vstatus.index}].fdBankAccount" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdBankAccount')}" style="width:85%;" />
                	<span class="txtstrong vat_${vstatus.index}">*</span>
                </c:if>
                <c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine!='true' }">
                    <xform:text property="fdAccountsList_Form[${vstatus.index}].fdBankAccount" showStatus="view" required="true" subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdBankAccount')}" validators=" maxLength(200)" style="width:85%;" />
                	<xform:text property="fdAccountsList_Form[${vstatus.index}].fdBankAccount" showStatus="noShow"></xform:text>
                </c:if>
                </div>
            </td>
            <fssc:checkUseBank fdBank="BOC">
            	<td align="center">
	             <div id="_xform_fdAccountsList_Form[${vstatus.index}].fdBankAccountNo" _xform_type="text">
	                <c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine=='true' }">
                    	<xform:text property="fdAccountsList_Form[${vstatus.index}].fdBankAccountNo" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdBankAccountNo')}" style="width:85%;" />
	                	<span class="txtstrong vat_${vstatus.index}">*</span>
	                </c:if>
	                <c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine!='true' }">
	                    <xform:text property="fdAccountsList_Form[${vstatus.index}].fdBankAccountNo" showStatus="view" required="true" subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdBankAccountNo')}" validators=" maxLength(200)" style="width:85%;" />
	                </c:if>
	              </div>
                </td>
            </fssc:checkUseBank>
           <fssc:checkUseBank fdBank="CMB">
           		<td align="center">
			 		<div id="_xform_fdAccountsList_Form[${vstatus.index}].fdAccountAreaCode" _xform_type="dialog">
			 			<c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine!='true'}">
		                    <xform:dialog propertyId="fdAccountsList_Form[${vstatus.index}].fdAccountAreaCode" propertyName="fdAccountsList_Form[${vstatus.index}].fdAccountAreaName" showStatus="view" style="width:95%;">
		                        FSSC_SelectAccountArea();
		                    </xform:dialog>
	                    </c:if>
	                  	<c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine=='true'}">
	                    	<xform:dialog propertyId="fdAccountsList_Form[${vstatus.index}].fdAccountAreaCode" propertyName="fdAccountsList_Form[${vstatus.index}].fdAccountAreaName" subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdAccountAreaName')}" showStatus="edit" style="width:90%;">
		                        FSSC_SelectAccountArea();
		                    </xform:dialog>
		                    <span class="txtstrong vat_${vstatus.index}">*</span>
	                    </c:if>
	                </div>
               	</td>
            </fssc:checkUseBank>
			<fssc:checkUseBank fdBank="CBS">
				<td align="center">
					<div id="_xform_fdAccountsList_Form[${vstatus.index}].fdAccountAreaCode" _xform_type="dialog">
						<c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine!='true'}">
							<xform:dialog propertyId="fdAccountsList_Form[${vstatus.index}].fdAccountAreaCode" propertyName="fdAccountsList_Form[${vstatus.index}].fdAccountAreaName" showStatus="view" style="width:95%;">
								FSSC_SelectCbsAccountArea();
							</xform:dialog>
						</c:if>
						<c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine=='true'}">
							<xform:dialog propertyId="fdAccountsList_Form[${vstatus.index}].fdAccountAreaCode" propertyName="fdAccountsList_Form[${vstatus.index}].fdAccountAreaName" subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdAccountAreaName')}" showStatus="edit" style="width:90%;">
								FSSC_SelectCbsAccountArea();
							</xform:dialog>
							<span class="txtstrong vat_${vstatus.index}">*</span>
						</c:if>
					</div>
				</td>
			</fssc:checkUseBank>
			<fssc:checkUseBank fdBank="CMInt">
				<td align="center">
					<div id="_xform_fdAccountsList_Form[${vstatus.index}].fdAccountAreaCode" _xform_type="dialog">
						<c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine!='true'}">
							<xform:dialog propertyId="fdAccountsList_Form[${vstatus.index}].fdAccountAreaCode" propertyName="fdAccountsList_Form[${vstatus.index}].fdAccountAreaName" showStatus="view" style="width:95%;">
								FSSC_SelectCmbIntAccountArea();
							</xform:dialog>
						</c:if>
						<c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine=='true'}">
							<xform:dialog propertyId="fdAccountsList_Form[${vstatus.index}].fdAccountAreaCode" propertyName="fdAccountsList_Form[${vstatus.index}].fdAccountAreaName" subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdAccountAreaName')}" showStatus="edit" style="width:90%;">
								FSSC_SelectCmbIntAccountArea();
							</xform:dialog>
							<span class="txtstrong vat_${vstatus.index}">*</span>
						</c:if>
					</div>
				</td>
			</fssc:checkUseBank>
            <td align="center">
                <div id="_xform_fdAccountsList_Form[${vstatus.index}].fdMoney" _xform_type="text">
                <c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine=='true' }">
                	<xform:text property="fdAccountsList_Form[${vstatus.index}].fdMoney" required="true" validators="currency-dollar min(0)" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseAccounts.fdMoney')}" style="width:85%;" />
                </c:if>
                <c:if test="${fsscExpenseMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.examine!='true' }">
                    <kmss:showNumber value="${fdAccountsList_FormItem.fdMoney }" pattern="0.00"/>
                	<html:hidden property="fdAccountsList_Form[${vstatus.index}].fdMoney" value="${fdAccountsList_FormItem.fdMoney }"/>
                </c:if>
                </div>
            </td>
        </tr>
    </c:forEach>
</table>
<script>
  Com_IncludeFile("doclist.js");
  DocList_Info.push('TABLE_DocList_fdAccountsList_Form');
  DocListFunc_Init();
</script>
</ui:content>
<script>
    seajs.use(['lui/dialog'],function(dialog){
    	//收款明细选择币种
    	window.FSSC_SelectPayCurrency = function(){
    		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
    		var existCurrency = [];
    		$("#TABLE_DocList_fdDetailList_Form [name$=fdCurrencyId]").each(function(){
    			if(!existCurrency.contains(this.value)){
    				existCurrency.push(this.value);
    			}
    		});
    		var fdCompanyId = $("[name='fdCompanyId']").val();
    		var fdPayCurrencyType = "fdPayCurrencyType";
    		dialogSelect(false,'eop_basedata_currency_fdCurrency','fdAccountsList_Form[*].fdCurrencyId','fdAccountsList_Form[*].fdCurrencyName',null,{existCurrency:existCurrency.join(';'),fdCompanyId:fdCompanyId,fdPayCurrencyType:fdPayCurrencyType},function(rtn){
    			if(rtn&&rtn.length>0){
    				var fdCurrencyId = rtn[0].fdId,
        			fdCompanyId = $("[name='fdCompanyId']").val(),
        			data = new KMSSData();
	    			$("[name='fdAccountsList_Form["+index+"].fdExchangeRate']").val("");
	        		data = data.AddBeanData('eopBasedataExchangeRateService&type=getRateByCurrency&authCurrent=true&fdCurrencyId='+fdCurrencyId+'&fdCompanyId='+fdCompanyId).GetHashMapArray();
	    			if(data.length>0){
	    				$("[name='fdAccountsList_Form["+index+"].fdExchangeRate']").val(data[0].fdExchangeRate)
	    			}
    			}
    		});
    	}
    	//收款明细选择收款人账户
    	window.FSSC_SelectAccount = function(){
    		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
    		dialogSelect(false,'eop_basedata_account_fdAccount','fdAccountsList_Form[*].fdAccountId','fdAccountsList_Form[*].fdAccountName',null,{fdPersonId:$("input[name='fdClaimantId']").val()},function(rtn){
    			if(rtn){
    				$("[name='fdAccountsList_Form["+index+"].fdBankName']").val(rtn[0]['fdBankName']);
    				$("[name='fdAccountsList_Form["+index+"].fdBankAccount']").val(rtn[0]['fdBankAccount']);
    				$("[name='fdAccountsList_Form["+index+"].fdBankAccountNo']").val(rtn[0]['fdBankNo']);
					$("[name='fdAccountsList_Form["+index+"].fdAccountAreaName']").val(rtn[0]['fdAccountArea']);
    			}
    		});
    	}
    	//收款明细选择付款方式
    	window.FSSC_SelectPayWay = function(){
    		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
    		var fdCompanyId = $("[name=fdCompanyId]").val();
    		if(!fdCompanyId){
    			dialog.alert(lang['message.tip.pleaseSelectCompany']);
    			return;
    		}
    		dialogSelect(false,'eop_basedata_pay_way_getPayWay','fdAccountsList_Form[*].fdPayWayId','fdAccountsList_Form[*].fdPayWayName',null,{fdCompanyId:fdCompanyId},function(rtn){
    			if(rtn&&rtn.length>0){
    				$("[name='fdAccountsList_Form["+index+"].fdBankId']").val(rtn[0]['fdDefaultPayBank.fdId']);
    				$("[name='fdAccountsList_Form["+index+"].fdPayBankName']").val((rtn[0]['fdDefaultPayBank.fdBankName']||'')+(rtn[0]['fdDefaultPayBank.fdBankAccount']||''));
    				$("[name='fdAccountsList_Form["+index+"].fdIsTransfer']").val(rtn[0]['fdIsTransfer']);
    				initFS_GetFdIsTransfer(index, rtn[0]['fdIsTransfer']);
    			}
    		});
    	}
    	//收款明细选择付款银行
    	window.FSSC_SelectPayBank = function(){
    		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
    		var fdCompanyId = $("[name=fdCompanyId]").val();
    		if(!fdCompanyId){
    			dialog.alert(lang['tips.pleaseSelectCompany']);
    			return;
    		}
    		dialogSelect(false,'eop_basedata_pay_bank_fdBank','fdAccountsList_Form[*].fdBankId','fdAccountsList_Form[*].fdPayBankName',null,{fdCompanyId:fdCompanyId},function(data){
    			if(data&&data.length>0){
    				$("[name='fdAccountsList_Form["+index+"].fdPayBankName']").val(data[0]['fdBankName']+data[0]['fdBankAccount']);
    			}
    		});
    	}
    	//收款明细选择收款账户归属地区
    	window.FSSC_SelectAccountArea = function(){
    		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
    		 dialogSelect(false,'fssc_cmb_city_code','fdAccountsList_Form[*].fdAccountAreaCode','fdAccountsList_Form[*].fdAccountAreaName',null,null,function(rtn){
    			 if(rtn){
    					$("[name='fdAccountsList_Form["+index+"].fdAccountAreaCode']").val(rtn[0]['fdProvincialCode']);
    					$("[name='fdAccountsList_Form["+index+"].fdAccountAreaName']").val(rtn[0]['fdCity']);
    				}
    		 });
    	}

		//收款明细选择收款账户归属地区
		window.FSSC_SelectCbsAccountArea = function(){
			var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
			dialogSelect(false,'fssc_cbs_city','fdAccountsList_Form[*].fdAccountAreaCode','fdAccountsList_Form[*].fdAccountAreaName',null,null,function(rtn){
				if(rtn){
					$("[name='fdAccountsList_Form["+index+"].fdAccountAreaCode']").val(rtn[0]['fdProvince']);
					$("[name='fdAccountsList_Form["+index+"].fdAccountAreaName']").val(rtn[0]['fdCity']);
				}
			});
		}

		//收款明细选择收款账户归属地区
		window.FSSC_SelectCmbIntAccountArea = function(){
			var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
			dialogSelect(false,'fssc_cmbint_city_code','fdAccountsList_Form[*].fdAccountAreaCode','fdAccountsList_Form[*].fdAccountAreaName',null,null,function(rtn){
				if(rtn){
					$("[name='fdAccountsList_Form["+index+"].fdAccountAreaCode']").val(rtn[0]['fdProvincialCode']);
					$("[name='fdAccountsList_Form["+index+"].fdAccountAreaName']").val(rtn[0]['fdCity']);
				}
			});
		}
    	
    	function initFS_GetFdIsTransfer(index, fdIsTransfer){
    		if(fdIsTransfer =='false'){	//收款账户信息非必填
    			$("[name='fdAccountsList_Form["+index+"].fdAccountName']").attr("validate","");
    			$("[name='fdAccountsList_Form["+index+"].fdBankName']").attr("validate","maxLength(400) checkNull");
    			$("[name='fdAccountsList_Form["+index+"].fdBankAccount']").attr("validate","maxLength(200) checkNull");
    			$("[name='fdAccountsList_Form["+index+"].fdBankAccountNo']").attr("validate","maxLength(400) checkNull");
    			$("[name='fdAccountsList_Form["+index+"].fdAccountAreaName']").attr("validate","");
    			$(".vat_"+index).hide();
    		}else{	//收款账户信息必填
    			$("[name='fdAccountsList_Form["+index+"].fdAccountName']").attr("validate","required");
    			$("[name='fdAccountsList_Form["+index+"].fdBankName']").attr("validate","required maxLength(400) checkNull");
    			$("[name='fdAccountsList_Form["+index+"].fdBankAccount']").attr("validate","required maxLength(200) checkNull");
    			$("[name='fdAccountsList_Form["+index+"].fdBankAccountNo']").attr("validate","required maxLength(400) checkNull");
    			$("[name='fdAccountsList_Form["+index+"].fdAccountAreaName']").attr("validate","required");
    			$(".vat_"+index).show();
    		}
    	}
    })
</script>
<br/>