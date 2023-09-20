<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<table class="tb_normal" width="100%" id="TABLE_DocList_fdDetailList_Form" align="center" tbdraggable="true">
    <tr align="center" class="tr_normal_title">
        <td style="width:20px;"></td>
        <td style="width:40px;">
            ${lfn:message('page.serial')}
        </td>
        <td width="10%">
            ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdCompany')}
        </td>
        <td width="12%">
            ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdCostCenter')}
        </td>
        <td width="10%">
            ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdProject')}
        </td>
        <td width="10%">
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
        <td width="12%">
            ${lfn:message('fssc-expense:fsscExpenseShareDetail.fdRemark')}
        </td>
        <td style="width:80px;">
        </td>
    </tr>
    <tr KMSS_IsReferRow="1" style="display:none;">
        <td align="center">
            <input type='checkbox' name='DocList_Selected' />
        </td>
        <td align="center" KMSS_IsRowIndex="1">
            !{index}
        </td>
        <td align="center">
            <input type="hidden" name="fdDetailList_Form[!{index}].fdId" value=""/>
            <xform:dialog propertyName="fdDetailList_Form[!{index}].fdCompanyName" required="true" propertyId="fdDetailList_Form[!{index}].fdCompanyId" showStatus="readOnly" subject="${lfn:message('fssc-expense:fsscExpenseShareDetail.fdCompany')}" validators=" maxLength(200)" style="width:85%;" >
            	FSSC_SelectInvoiceCompany(!{index});
            </xform:dialog>
        </td>
        <td align="center">
            <div id="_xform_fdDetailList_Form[!{index}].fdCostCenterId" _xform_type="dialog">
                <xform:dialog propertyId="fdDetailList_Form[!{index}].fdCostCenterId" propertyName="fdDetailList_Form[!{index}].fdCostCenterName" required="true" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseShareDetail.fdCostCenter')}" style="width:90%;">
                    FSSC_SelectCostCenter(!{index});
                </xform:dialog>
            </div>
        </td>
        <td align="center">
            <div id="_xform_fdDetailList_Form[!{index}].fdProjectId" _xform_type="dialog">
                <xform:dialog propertyId="fdDetailList_Form[!{index}].fdProjectId" propertyName="fdDetailList_Form[!{index}].fdProjectName" required="false" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseShareDetail.fdProject')}" style="width:90%;">
                    FSSC_SelectProject(!{index});
                </xform:dialog>
            </div>
        </td>
        <td align="center">
            <div id="_xform_fdDetailList_Form[!{index}].fdExpenseItem" _xform_type="text">
                <xform:dialog propertyName="fdDetailList_Form[!{index}].fdExpenseItemName" required="true" propertyId="fdDetailList_Form[!{index}].fdExpenseItemId" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseShareDetail.fdExpenseItem')}" validators=" maxLength(200)" style="width:85%;" >
                	FSSC_SelectInvoiceType(!{index});
                </xform:dialog>
            </div>
        </td>
        <td align="center">
            <div id="_xform_fdDetailList_Form[!{index}].fdHappenDate" _xform_type="text" class="vat!{index}">
                <xform:datetime required="true" dateTimeType="date" property="fdDetailList_Form[!{index}].fdHappenDate" subject="${lfn:message('fssc-expense:fsscExpenseShareDetail.fdHappenDate') }" showStatus="edit" style="width:85%;" />
            </div>
        </td>
        <td align="center">
            <div id="_xform_fdDetailList_Form[!{index}].fdMoney" _xform_type="text" class="vat!{index}">
                <xform:text required="true" property="fdDetailList_Form[!{index}].fdMoney" validators="currency-dollar" subject="${lfn:message('fssc-expense:fsscExpenseShareDetail.fdMoney') }" showStatus="edit" style="width:85%;" onValueChange="FSSC_ChangeMoney"/>
            </div>
        </td>
        <td align="center">
            <div id="_xform_fdDetailList_Form[!{index}].fdCurrency" _xform_type="text" class="vat!{index}">
                <xform:dialog propertyId="fdDetailList_Form[!{index}].fdCurrencyId" required="true" propertyName="fdDetailList_Form[!{index}].fdCurrencyName" showStatus="readOnly" style="width:85%;" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdCurrency')}">
                    FSSC_SelectCurrency(!{index});
                </xform:dialog>
                <xform:text property="fdDetailList_Form[!{index}].fdRate" showStatus="noShow" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdExchangeRate')}" style="width:85%;" onValueChange="FSSC_ChangeMoney"/>
            </div>
        </td>
        <td align="center">
            <div id="_xform_fdDetailList_Form[!{index}].fdStandardMoney" _xform_type="text" class="vat!{index}">
                <xform:text property="fdDetailList_Form[!{index}].fdStandardMoney" showStatus="readOnly" subject="${lfn:message('fssc-expense:fsscExpenseShareDetail.fdStandardMoney') }" style="width:80%;" />
            </div>
        </td>
        <td align="center">
            <div id="_xform_fdDetailList_Form[!{index}].fdRemark" _xform_type="text" class="vat!{index}">
                <xform:text property="fdDetailList_Form[!{index}].fdRemark" subject="${lfn:message('fssc-expense:fsscExpenseShareDetail.fdRemark') }" showStatus="edit" style="width:80%;" />
            </div>
        </td>
        <td align="center">
            <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
                <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
            </a>
        </td>
    </tr>
    <c:forEach items="${fsscExpenseShareMainForm.fdDetailList_Form}" var="fdDetailList_FormItem" varStatus="vstatus">
        <tr KMSS_IsContentRow="1">
            <td align="center">
                <input type="checkbox" name="DocList_Selected" />
            </td>
            <td align="center">
                ${vstatus.index+1}
            </td>
            <td align="center">
	            <input type="hidden" name="fdDetailList_Form[${vstatus.index }].fdId" value="${fdDetailList_FormItem.fdId }" />
	            <xform:dialog propertyName="fdDetailList_Form[${vstatus.index }].fdCompanyName" required="true" propertyId="fdDetailList_Form[${vstatus.index }].fdCompanyId" showStatus="readOnly" subject="${lfn:message('fssc-expense:fsscExpenseShareDetail.fdCompany')}" validators=" maxLength(200)" style="width:85%;" >
	            	FSSC_SelectInvoiceCompany(${vstatus.index });
	            </xform:dialog>
	        </td>
	        <td align="center">
	            <div id="_xform_fdDetailList_Form[${vstatus.index }].fdCostCenterId" _xform_type="dialog">
	                <xform:dialog propertyId="fdDetailList_Form[${vstatus.index }].fdCostCenterId" propertyName="fdDetailList_Form[${vstatus.index }].fdCostCenterName" required="true" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdCostCenter')}" style="width:90%;">
	                    FSSC_SelectCostCenter(${vstatus.index });
	                </xform:dialog>
	            </div>
	        </td>
	        <td align="center">
	            <div id="_xform_fdDetailList_Form[!{index}].fdProjectId" _xform_type="dialog">
	                <xform:dialog propertyId="fdDetailList_Form[${vstatus.index }].fdProjectId" propertyName="fdDetailList_Form[${vstatus.index }].fdProjectName" required="false" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseShareDetail.fdProject')}" style="width:90%;">
	                    FSSC_SelectProject(${vstatus.index });
	                </xform:dialog>
	            </div>
	        </td>
	        <td align="center">
	            <div id="_xform_fdDetailList_Form[${vstatus.index }].fdExpenseItem" _xform_type="text">
	                <xform:dialog propertyName="fdDetailList_Form[${vstatus.index }].fdExpenseItemName" required="true" propertyId="fdDetailList_Form[${vstatus.index }].fdExpenseItemId" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseShareDetail.fdExpenseItem')}" validators=" maxLength(200)" style="width:85%;" >
	                	FSSC_SelectInvoiceType(${vstatus.index });
	                </xform:dialog>
	            </div>
	        </td>
	        <td align="center">
	            <div id="_xform_fdDetailList_Form[${vstatus.index }].fdHappenDate" _xform_type="text" class="vat${vstatus.index }">
	                <xform:datetime required="true" dateTimeType="date" property="fdDetailList_Form[${vstatus.index }].fdHappenDate" subject="${lfn:message('fssc-expense:fsscExpenseShareDetail.fdHappenDate') }" showStatus="edit" style="width:85%;" />
	            </div>
	        </td>
	        <td align="center">
	            <div id="_xform_fdDetailList_Form[${vstatus.index }].fdMoney" _xform_type="text" class="vat${vstatus.index }">
	                <xform:text required="true" property="fdDetailList_Form[${vstatus.index }].fdMoney" validators="currency-dollar" subject="${lfn:message('fssc-expense:fsscExpenseShareDetail.fdMoney') }" value="${fdDetailList_FormItem.fdMoney }" showStatus="edit" style="width:85%;" onValueChange="FSSC_ChangeMoney"/>
	            </div>
	        </td>
	        <td align="center">
	            <div id="_xform_fdDetailList_Form[${vstatus.index }].fdCurrency" _xform_type="text" class="vat${vstatus.index }">
	                <xform:dialog propertyId="fdDetailList_Form[${vstatus.index }].fdCurrencyId" required="true" propertyName="fdDetailList_Form[${vstatus.index }].fdCurrencyName" showStatus="readOnly" style="width:85%;" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdCurrency')}">
	                    FSSC_SelectCurrency(${vstatus.index });
	                </xform:dialog>
	                <xform:text property="fdDetailList_Form[${vstatus.index }].fdRate" showStatus="noShow" required="true" subject="${lfn:message('fssc-expense:fsscExpenseDetail.fdExchangeRate')}" style="width:85%;" onValueChange="FSSC_ChangeMoney"/>
	            </div>
	        </td>
	        <td align="center">
	            <div id="_xform_fdDetailList_Form[${vstatus.index }].fdStandardMoney" _xform_type="text" class="vat${vstatus.index }">
	                <xform:text property="fdDetailList_Form[${vstatus.index }].fdStandardMoney" showStatus="readOnly" subject="${lfn:message('fssc-expense:fsscExpenseShareDetail.fdStandardMoney') }" style="width:80%;"  value="${fdDetailList_FormItem.fdStandardMoney }"/>
	            </div>
	        </td>
	        <td align="center">
	            <div id="_xform_fdDetailList_Form[${vstatus.index }].fdRemark" _xform_type="text" class="vat${vstatus.index }">
	                <xform:text property="fdDetailList_Form[${vstatus.index }].fdRemark" subject="${lfn:message('fssc-expense:fsscExpenseShareDetail.fdRemark') }" showStatus="edit" style="width:80%;"/>
	            </div>
	        </td>
            <td align="center">
                <a href="javascript:void(0);" onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}">
                    <img src="${KMSS_Parameter_StylePath}icons/icon_del.png" border="0" />
                </a>
            </td>
        </tr>
    </c:forEach>
    <tr type="optRow" class="tr_normal_opt" invalidrow="true">
        <td colspan="12">
            <a href="javascript:void(0);" onclick="FSSC_AddInitDetail(true)">
                <img src="${KMSS_Parameter_StylePath}icons/icon_add.png" border="0" />${lfn:message('doclist.add')}
            </a>
            &nbsp;
            <a href="javascript:void(0);" onclick="DocList_MoveRowBySelect(-1);">
                <img src="${KMSS_Parameter_StylePath}icons/icon_up.png" border="0" />${lfn:message('doclist.moveup')}
            </a>
            &nbsp;
            <a href="javascript:void(0);" onclick="DocList_MoveRowBySelect(1);">
                <img src="${KMSS_Parameter_StylePath}icons/icon_down.png" border="0" />${lfn:message('doclist.movedown')}
            </a>
            &nbsp;
        </td>
    </tr>
</table>
<input type="hidden" name="fdDetailList_Flag" value="1">
<script>
  Com_IncludeFile("doclist.js");
</script>
<script>
	DocList_Info.push('TABLE_DocList_fdDetailList_Form');
    seajs.use(['lui/dialog','lang!fssc-expense'],function(dialog,lang){
    	window.FSSC_SelectInvoiceType = function(index){
    		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
    		var fdCompanyId = $("[name='fdDetailList_Form["+index+"].fdCompanyId']").val();
    		if(!fdCompanyId){
    			dialog.alert("${lfn:message('fssc-expense:tips.pleaseSelectCompany')}");
    			return false;
    		}
    		dialogSelect(false,'eop_basedata_expense_item_fdParent','fdDetailList_Form[*].fdExpenseItemId','fdDetailList_Form[*].fdExpenseItemName',null,{fdCompanyId:fdCompanyId,fdNotId:'fdNotId'});
    	}
    	window.FSSC_SelectInvoiceCompany = function(index){
    		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
    		window.curIndex = index;
    		dialogSelect(false,'eop_basedata_company_fdCompany','fdDetailList_Form[*].fdCompanyId','fdDetailList_Form[*].fdCompanyName',null,null,null);
    	}
    	window.FSSC_SelectCostCenter = function(index){
    		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
    		var fdCompanyId = $("[name='fdDetailList_Form["+index+"].fdCompanyId']").val();
    		if(!fdCompanyId){
    			dialog.alert("${lfn:message('fssc-expense:tips.pleaseSelectCompany')}");
    			return false;
    		}
    		dialogSelect(false,'eop_basedata_cost_center_selectCostCenter','fdDetailList_Form[*].fdCostCenterId','fdDetailList_Form[*].fdCostCenterName',null,{fdCompanyId:fdCompanyId,fdNotId:'fdNotId'});
    	}
    	window.FSSC_AfterExpenseMainSelected = function(rtn,addDetail){
            var fdShareType= $("[name=fdShareType]").val();
            if(!rtn){
                return;
            }
            $.post(
                '${LUI_ContextPath}/fssc/expense/fssc_expense_main/fsscExpenseMainData.do?method=getExpenseDetail',
                {fdMainId:rtn[0].fdId, fdShareType:fdShareType},
                function(data){
                    $("#TABLE_EXPENSE tr:gt(0)").remove();
                    $("#TABLE_EXPENSE tr:eq(0)").after(data);
                    //如果重新选择后的报销单和原报销单不同，清空分摊明细
                    if($("[name=fdModelId]").val()!='${fsscExpenseShareMainForm.fdModelId}'){
                        FSSC_AddInitDetail();
                    }
                }
            );
    	}
    	window.FSSC_AddInitDetail = function(add){
    		if(!add){
    			$("#TABLE_DocList_fdDetailList_Form tr:gt(0)").each(function(){
        			DocList_DeleteRow(this);
        		});
    		}
    		var row = DocList_AddRow('TABLE_DocList_fdDetailList_Form');
    		var exp = $("#TABLE_EXPENSE tr:eq(1)");
    		$(row).find("[name$=fdCurrencyId]").val(exp.find("[name$=fdCurrencyId]").val());
    		$(row).find("[name$=fdCurrencyName]").val(exp.find("[name$=fdCurrencyName]").val());
    		$(row).find("[name$=fdRate]").val(exp.find("[name$=fdExchangeRate]").val());
    		$(row).find("[name$=fdCompanyId]").val(exp.find("[name$=fdCompanyId]").val());
    		$(row).find("[name$=fdCompanyName]").val(exp.find("[name$=fdCompanyName]").val());
    	}
    	//修改报销金额
    	window.FSSC_ChangeMoney = function(v,e,index){
    		if(index==null||isNaN(index)){
    			index = DocListFunc_GetParentByTagName("TR");
        		index = index.rowIndex - 1;
    		}
    		var fdApplyMoney = $("[name='fdDetailList_Form["+index+"].fdMoney']").val();
    		var fdExchangeRate = $("[name='fdDetailList_Form["+index+"].fdRate']").val();
    		if(!fdApplyMoney||isNaN(fdApplyMoney)||!fdExchangeRate||isNaN(fdExchangeRate)){
    			return;
    		}
    		var fdStandardMoney = multiPoint(fdApplyMoney,fdExchangeRate);
    		$("[name='fdDetailList_Form["+index+"].fdStandardMoney']").val(fdStandardMoney);
    	}
    	window.FSSC_SelectCurrency = function(index){
    		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
    		var fdCompanyId = $("[name='fdDetailList_Form["+index+"].fdCompanyId']").val();
    		if(!fdCompanyId){
    			dialog.alert("${lfn:message('fssc-expense:tips.pleaseSelectCompany')}");
    			return false;
    		}
    		dialogSelect(false,'eop_basedata_currency_fdCurrency','fdDetailList_Form[*].fdCurrencyId','fdDetailList_Form[*].fdCurrencyName',null,null,function(rtn){
    			var fdCurrencyId = rtn[0].fdId,
	    			data = new KMSSData();
    			$("[name='fdDetailList_Form["+index+"].fdRate']").val("");
	    		data = data.AddBeanData('eopBasedataExchangeRateService&type=getRateByCurrency&fdCurrencyId='+fdCurrencyId+'&fdCompanyId='+fdCompanyId).GetHashMapArray();
    			if(data.length>0){
    				$("[name='fdDetailList_Form["+index+"].fdRate']").val(data[0].rate)
    				FSSC_ChangeMoney(null,null,index);
    			}
    		});
    	}
    	//初始化币种及汇率
    	window.FSSC_InitCurrencyAndRate = function(index){
    		var fdCompanyId = $("[name='fdDetailList_Form["+window.curIndex+"].fdCompanyId']").val();
    		if(!fdCompanyId){
    			return;
    		}
    		var data = new KMSSData();
    		data.AddBeanData("fsscExpenseDataService&type=getDefaultCurrency&fdCompanyId="+fdCompanyId);
    		data = data.GetHashMapArray();
    		if(data.length>0){
    			$("[name='fdDetailList_Form["+window.curIndex+"].fdCurrencyId']").val(data[0].fdCurrencyId);
    			$("[name='fdDetailList_Form["+window.curIndex+"].fdCurrencyName']").val(data[0].fdCurrencyName);
    			$("[name='fdDetailList_Form["+window.curIndex+"].fdRate']").val(data[0].fdRate);
    			FSSC_ChangeMoney(null,null,window.curIndex);
    		}
    		//清空成本中心、费用类型
    		$("[name='fdDetailList_Form["+window.curIndex+"].fdCostCenterId']").val("");
    		$("[name='fdDetailList_Form["+window.curIndex+"].fdCostCenterName']").val("");
    		$("[name='fdDetailList_Form["+window.curIndex+"].fdExpenseItemId']").val("");
    		$("[name='fdDetailList_Form["+window.curIndex+"].fdExpenseItemName']").val("");
    	}
    	window.FSSC_InitCurrency = function(){
    		var row = $('#TABLE_DocList_fdDetailList_Form tr');
    		row = row[row.length-2];
    		var exp = $("#TABLE_EXPENSE tr:eq(1)");
    		$(row).find("[name$=fdCurrencyId]").val(exp.find("[name$=fdCurrencyId]").val());
    		$(row).find("[name$=fdCurrencyName]").val(exp.find("[name$=fdCurrencyName]").val());
    		$(row).find("[name$=fdRate]").val(exp.find("[name$=fdExchangeRate]").val());
    	}
    	
    	window.FSSC_SelectProject = function(){
    		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
    		var fdCompanyId = $("[name='fdDetailList_Form["+index+"].fdCompanyId']").val();
    		if(!fdCompanyId){
    			dialog.alert("${lfn:message('fssc-expense:tips.pleaseSelectCompany')}");
    			return false;
    		}
    		dialogSelect(false,'eop_basedata_project_project','fdDetailList_Form[*].fdProjectId','fdDetailList_Form[*].fdProjectName',null,{fdCompanyId:fdCompanyId,fdNotId:'fdNotId','fdProjectType':'1'});
    	}
    	//提交时校验金额是否相等
    	Com_Parameter.event.submit.push(function(){
    		if($("[name=docStatus]").val()=='10'){
    			return true;
    		}
    		var expenseMoney = 0,shareMoney = 0;
    		$("#TABLE_EXPENSE tr:gt(0)").each(function(){
    			var input = $(this).find("[name$=fdApprovedStandardMoney]");
    			if(input.length==0){
    				return;
    			}
    			expenseMoney = numAdd(expenseMoney,input.val()*1);
    		});
    		$("#TABLE_DocList_fdDetailList_Form tr:gt(0)").each(function(){
    			var input = $(this).find("[name$=fdMoney]");
    			if(input.length==0){
    				return;
    			}
    			var rate = $(this).find("[name$=fdRate]");
    			shareMoney = numAdd(shareMoney,numMulti(input.val(),rate.val()));
    		});
    		if(expenseMoney!=shareMoney){
    			dialog.alert(lang['tips.share.moneyNotEqual']);
    			return false;
    		}
    		return true;
    	});
    	$(function(){
            FSSC_AfterExpenseMainSelected([{fdId:'${fsscExpenseShareMainForm.fdModelId}'}]);
    	})
    })
</script>
<br/>
