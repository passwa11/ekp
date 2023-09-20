DocList_Info.push('TABLE_DocList_fdInvoiceListTemp_Form');
seajs.use(['lui/dialog','lang!fssc-expense'],function(dialog,lang){
	LUI.ready(function() {
		$(".tempTB").attr("style","width:100%;  margin: 0px auto;");
	});
	//发票明细切换是否增值税发票，是则设置为可抵扣
	window.FSSC_ChangeIsVat = function(v,e){
		var tr = DocListFunc_GetParentByTagName("TR");
		var index = DocListFunc_GetParentByTagName('TR',e).rowIndex-1;
		displayVat(tr,index);
	}
	window.displayVat=function(tr,index){
		if(index==null||isNaN(index)){
    		index = tr.rowIndex - 1;
		}
		var fdInvoiceType = $("[name='fdInvoiceListTemp_Form["+index+"].fdInvoiceType']").val();
		if("10100"==fdInvoiceType||"30100"==fdInvoiceType){
			$(tr).find(".vat").find(".txtstrong").show();
			$(tr).find(".vat").find("input[type=text]").each(function(){
				var validate = $(this).attr("validate")||'';
				if(validate.indexOf('required')==-1){
					validate += ' required';
				}
				$(this).attr("validate",validate);
			});
		}else{
			$(tr).find(".vat").find(".txtstrong").hide();
			$(tr).find(".vat").parent().find(".validation-advice").hide();
			$(tr).find(".vat").find("input[type=text]").each(function(){
				var validate = $(this).attr("validate")||'';
					validate = validate.replace(/required/g,'');
				$(this).attr("validate",validate);
			});
		}
	}
	
	//选择发票
	window.FSSC_SelectInvoice = function(){
		DocList_TableInfo['TABLE_DocList_fdInvoiceListTemp_Form'].firstIndex = 1;
		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
		dialogSelect(false,'fssc_ledger_fdInvoice','fdInvoiceListTemp_Form[*].fdInvoiceNumberId','fdInvoiceListTemp_Form[*].fdInvoiceNumber',null,{type:'examine',fdUseStatus:'0'},function(rtn){
			if(rtn){
				$("[name='fdInvoiceListTemp_Form["+index+"].fdInvoiceNumber']").val(rtn[0]['fdInvoiceNumber']);
				if(rtn[0]['fdInvoiceCode']){
					$("[name='fdInvoiceListTemp_Form["+index+"].fdInvoiceCode']").val(rtn[0]['fdInvoiceCode']);
				}
				getInvoiceInfo(rtn[0]['fdInvoiceNumber'],rtn[0]['fdInvoiceCode'],index);
			}
		});
	}
	//选择发票
	window.FSSC_Invoice = function(){
		DocList_TableInfo['TABLE_DocList_fdInvoiceListTemp_Form'].firstIndex = 1;
		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
		var fdInvoiceNumber=$("[name='fdInvoiceListTemp_Form["+index+"].fdInvoiceNumber']").val();
		var fdInvoiceCode=$("[name='fdInvoiceListTemp_Form["+index+"].fdInvoiceCode']").val();
		getInvoiceInfo(fdInvoiceNumber,fdInvoiceCode,index);
	}
	
	/**
	 * 根据发票号码获取发票信息
	 */
	window.getInvoiceInfo=function(fdInvoiceNumber,fdInvoiceCode,index){
		var fdCompanyId=$("[name='fdCompanyId']").val();
		//通过发票单号自动带出对应的信息
		var data = new KMSSData();
		data.AddBeanData("fsscExpenseDataService&type=getInvoiceInfoByCode&authCurrent=true&fdInvoiceNumber="+fdInvoiceNumber+"&fdInvoiceCode="+(fdInvoiceCode?fdInvoiceCode:""));
		var rtnVal = data.GetHashMapArray();
		if(rtnVal.length>0){
			$("[name='fdInvoiceListTemp_Form["+index+"].fdInvoiceCode']").val("");
			$("[name='fdInvoiceListTemp_Form["+index+"].fdInvoiceDate']").val("");
			$("[name='fdInvoiceListTemp_Form["+index+"].fdInvoiceMoney']").val("");
			$("[name='fdInvoiceListTemp_Form["+index+"].fdTax']").val("");
			$("[name='fdInvoiceListTemp_Form["+index+"].fdTaxMoney']").val("");
			$("[name='fdInvoiceListTemp_Form["+index+"].fdNoTaxMoney']").val("");
			$("[name='fdInvoiceListTemp_Form["+index+"].fdCheckCode']").val("");
			$("[name='fdInvoiceListTemp_Form["+index+"].fdInvoiceCode']").val(rtnVal[0].fdInvoiceCode);
			$("[name='fdInvoiceListTemp_Form["+index+"].fdInvoiceDate']").val(rtnVal[0].fdInvoiceDate);
			$("[name='fdInvoiceListTemp_Form["+index+"].fdInvoiceType']").val(rtnVal[0].type);
			$("[name='fdInvoiceListTemp_Form["+index+"].fdCheckCode']").val(rtnVal[0].fdCheckCode);
			$("[name='fdInvoiceListTemp_Form["+index+"].fdTaxNumber']").val(rtnVal[0].fdPurchaserTaxNo);
			$("[name='fdInvoiceListTemp_Form["+index+"].fdPurchName']").val(rtnVal[0].fdPurchaserName);
			$("[name='fdInvoiceListTemp_Form["+index+"].fdCompanyId']").val(fdCompanyId);
			if(rtnVal[0].fdJshj){
				$("[name='fdInvoiceListTemp_Form["+index+"].fdInvoiceMoney']").val(formatFloat(rtnVal[0].fdJshj,2));
			}
			if(rtnVal[0].fdSl){
				if(isNaN(formatFloat(parseFloat(rtnVal[0].fdSl),2))){ //税率非数字
					$("[name='fdInvoiceListTemp_Form["+index+"].fdTax']").val(0);
				}else{
					$("[name='fdInvoiceListTemp_Form["+index+"].fdTax']").val(formatFloat(parseFloat(rtnVal[0].fdSl),2));
				}
			}
			if(rtnVal[0].fdTotalTax){
				$("[name='fdInvoiceListTemp_Form["+index+"].fdTaxMoney']").val(formatFloat(rtnVal[0].fdTotalTax,2));
			}
			if(rtnVal[0].fdTotalTax){
				$("[name='fdInvoiceListTemp_Form["+index+"].fdNoTaxMoney']").val(formatFloat(rtnVal[0].fdTotalTax,2));
			}
			if(rtnVal[0].fdJshj&&rtnVal[0].fdTotalTax){
				$("[name='fdInvoiceListTemp_Form["+index+"].fdNoTaxMoney']").val(subPoint(rtnVal[0].fdJshj,rtnVal[0].fdTotalTax));
			}
		}
	}
	window.FSSC_GetTaxMoney = function(obj,e){
		if(e){
			obj = e;
		}
		var index = DocListFunc_GetParentByTagName('TR',obj).rowIndex-1;
		var rate = $("[name='fdInvoiceListTemp_Form["+index+"].fdTax']").val()*1;
		//计算税额、不含税额
		var fdInvoiceMoney = $("[name='fdInvoiceListTemp_Form["+index+"].fdInvoiceMoney']").val()*1;
		var fdNonDeductMoney = $("[name='fdInvoiceListTemp_Form["+index+"].fdNonDeductMoney']").val(); //不可抵扣金额
		if(!fdNonDeductMoney){
			fdNonDeductMoney=0;
		}
		var fdTaxMoney=numSub(fdInvoiceMoney,fdNonDeductMoney);  //可抵扣总金额
		rate=divPoint(rate,100);
		fdTaxMoney = multiPoint(divPoint(fdTaxMoney,numAdd(rate,1.00)),rate);  //税额
		//不含税额=发票金额/(1+税率)
		var fdNotTaxMoney = numDiv(fdTaxMoney,numAdd(1,numDiv(rate,100)));
		fdNotTaxMoney = parseFloat(fdNotTaxMoney).toFixed(2);
		$("[name='fdInvoiceListTemp_Form["+index+"].fdTaxMoney']").val(fdTaxMoney);
		if(rate&&rate>0){
			$("[name='fdInvoiceListTemp_Form["+index+"].fdNoTaxMoney']").val(subPoint(numSub(fdInvoiceMoney,fdNonDeductMoney),fdTaxMoney));
		}else{
			$("[name='fdInvoiceListTemp_Form["+index+"].fdNoTaxMoney']").val(subPoint(fdInvoiceMoney,fdTaxMoney));
		}
	}
	//弹框校验发票是否重复
	Com_Parameter.event["submit"].push(function(){ 
		var flag=true;
		var length=$("#TABLE_DocList_fdInvoiceListTemp_Form [name$=fdInvoiceNumber]").length;
		var number=[],invoiceAttId={};
		if(length>0){
			for(var i=0;i<length;i++){
				var fdInvoiceNumber=$("[name='fdInvoiceListTemp_Form["+i+"].fdInvoiceNumber']").val();
				var fdInvoiceCode=$("[name='fdInvoiceListTemp_Form["+i+"].fdInvoiceCode']").val();
				var fdInvoiceDocId=$("[name='fdInvoiceListTemp_Form["+i+"].fdInvoiceDocId']").val();  //对应的发票ID
				if((fdInvoiceNumber+fdInvoiceCode)&&number.indexOf(fdInvoiceNumber+";"+fdInvoiceCode)>-1){
					if(fdInvoiceDocId&&invoiceAttId[fdInvoiceNumber+";"+fdInvoiceCode]!=fdInvoiceDocId){ //判断是否来源同一附件，同一附件认为是多税率发票
						dialog.alert(lang['tips.invoice.repeat']);
						return false;
					}
				}else{
					number.push(fdInvoiceNumber+";"+fdInvoiceCode);
					invoiceAttId[fdInvoiceNumber+";"+fdInvoiceCode]=fdInvoiceDocId;
				}
			}
		}
	 	return flag;
	 });
});
