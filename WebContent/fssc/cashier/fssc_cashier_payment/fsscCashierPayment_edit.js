function mySubmit(form, method_){
	if(!validateMoney()){//校验付款单明细付款金额之和是否等于付款单付款总金额
		return false;
	}
    Com_Submit(form, method_)
}

Com_AddEventListener(window,'load',function(){
	var len = $("#TABLE_DocList_fdDetail_Form tr").length-3;
	console.log(len);
	for(var i=0;i<len;i++){
		var fdPayWayId = $("[name='fdDetail_Form["+i+"].fdBasePayWayId']").val();
		var data = new KMSSData();
		data.UseCache=false;
		data.AddBeanData("eopBasedataPayWayService&type=isTransfer&ids="+fdPayWayId);
		data = data.GetHashMapArray();
		if(data.length>0){
			$("[name='fdDetail_Form["+i+"].fdIsTransfer']").val(data[0].fdIsTransfer);
			initFS_GetFdIsTransfer(i,data[0].fdIsTransfer);//初始化收款账户信息是否必填
			initFS_GetFdCstlchn(i);
		}
		hideOrShow(i);
	}
});

function initFS_GetFdIsTransfer(index, fdIsTransfer){
	if(fdIsTransfer =='false'){	//收款账户信息非必填
		$("[name='fdDetail_Form["+index+"].fdPayeeName']").attr("validate","maxLength(200)");
		$("[name='fdDetail_Form["+index+"].fdPayeeAccount']").attr("validate","maxLength(200) checkNull");
		$("[name='fdDetail_Form["+index+"].fdPayeeBankName']").attr("validate","maxLength(200) checkNull");
		$("[name='fdDetail_Form["+index+"].fdPayeeBankNo']").attr("validate","maxLength(400) checkNull");
		$("[name='fdDetail_Form["+index+"].fdPayeeCity']").attr("validate","");
		$(".vat_"+index).hide();
	}else{	//收款账户信息必填
		$("[name='fdDetail_Form["+index+"].fdPayeeName']").attr("validate","required maxLength(200)");
		$("[name='fdDetail_Form["+index+"].fdPayeeAccount']").attr("validate","required maxLength(200) checkNull");
		$("[name='fdDetail_Form["+index+"].fdPayeeBankName']").attr("validate","required maxLength(200) checkNull");
		$("[name='fdDetail_Form["+index+"].fdPayeeBankNo']").attr("validate","required maxLength(400) checkNull");
		$("[name='fdDetail_Form["+index+"].fdPayeeCity']").attr("validate","required");
		$(".vat_"+index).show();
	}
}

//复制上一行的付款類型，币种
function  FSSC_AddCashierDeteil(){
	DocList_AddRow('TABLE_DocList_fdDetail_Form');
	var index = $("#TABLE_DocList_fdDetail_Form > tbody > tr").length-3;
	if(index>0){
		$("[name='fdDetail_Form["+index+"].fdPaymentType']").val($("[name='fdDetail_Form["+(index-1)+"].fdPaymentType']").val());
		$("[name='fdDetail_Form["+index+"].fdPayeeCity']").val($("[name='fdDetail_Form["+(index-1)+"].fdPayeeCity']").val());
		$("[name='fdDetail_Form["+index+"].fdPayeeCityCode']").val($("[name='fdDetail_Form["+(index-1)+"].fdPayeeCityCode']").val());
		$("[name='fdDetail_Form["+index+"].fdSkipFlag']").val($("[name='fdDetail_Form["+(index-1)+"].fdSkipFlag']").val());
		$("[name='fdDetail_Form["+index+"].fdBaseCurrencyId']").val($("[name='fdDetail_Form["+(index-1)+"].fdBaseCurrencyId']").val());
		$("[name='fdDetail_Form["+index+"].fdBaseCurrencyName']").val($("[name='fdDetail_Form["+(index-1)+"].fdBaseCurrencyName']").val());
		$("[name='fdDetail_Form["+index+"].fdRate']").val($("[name='fdDetail_Form["+(index-1)+"].fdRate']").val());
		var fdPaymentType = $("[name='fdDetail_Form["+(index-1)+"].fdPaymentType']").val();
		if(fdPaymentType=='1'){
		}else{
			$("[name='fdDetail_Form["+index+"].fdBaseCurrencyId']").attr("readonly","readonly");
			$("[name='fdDetail_Form["+index+"].fdBaseCurrencyName']").attr("readonly","readonly");
			$("[name='fdDetail_Form["+index+"].fdRate']").attr("readonly","readonly");	
		}
		initFS_GetFdIsTransfer(index, true);//初始化收款账户信息是否必填
	}
}

//复制
function  FSSC_CopyRowDeteil(){
	DocList_AddRow('TABLE_DocList_fdDetail_Form');
	var index = $("#TABLE_DocList_fdDetail_Form > tbody > tr").length-3;
	if(index>0){
		$("[name='fdDetail_Form["+index+"].fdBasePayWayId']").val($("[name='fdDetail_Form["+(index-1)+"].fdBasePayWayId']").val());
		$("[name='fdDetail_Form["+index+"].fdBasePayWayName']").val($("[name='fdDetail_Form["+(index-1)+"].fdBasePayWayName']").val());
		$("[name='fdDetail_Form["+index+"].fdIsTransfer']").val($("[name='fdDetail_Form["+(index-1)+"].fdIsTransfer']").val());
		$("[name='fdDetail_Form["+index+"].fdBasePayBankId']").val($("[name='fdDetail_Form["+(index-1)+"].fdBasePayBankId']").val());
		$("[name='fdDetail_Form["+index+"].fdBasePayBankName']").val($("[name='fdDetail_Form["+(index-1)+"].fdBasePayBankName']").val());
		$("[name='fdDetail_Form["+index+"].fdPayeeName']").val($("[name='fdDetail_Form["+(index-1)+"].fdPayeeName']").val());
		$("[name='fdDetail_Form["+index+"].fdPayeeAccount']").val($("[name='fdDetail_Form["+(index-1)+"].fdPayeeAccount']").val());	
		$("[name='fdDetail_Form["+index+"].fdPayeeBankName']").val($("[name='fdDetail_Form["+(index-1)+"].fdPayeeBankName']").val());	
		$("[name='fdDetail_Form["+index+"].fdPaymentMoney']").val($("[name='fdDetail_Form["+(index-1)+"].fdPaymentMoney']").val());		
		$("[name='fdDetail_Form["+index+"].fdSkipFlag']").val($("[name='fdDetail_Form["+(index-1)+"].fdSkipFlag']").val());		
		$("[name='fdDetail_Form["+index+"].fdPaymentType']").val($("[name='fdDetail_Form["+(index-1)+"].fdPaymentType']").val());
		$("[name='fdDetail_Form["+index+"].fdPayeeCity']").val($("[name='fdDetail_Form["+(index-1)+"].fdPayeeCity']").val());
		$("[name='fdDetail_Form["+index+"].fdPayeeCityCode']").val($("[name='fdDetail_Form["+(index-1)+"].fdPayeeCityCode']").val());
		$("[name='fdDetail_Form["+index+"].fdSkipFlag']").val($("[name='fdDetail_Form["+(index-1)+"].fdSkipFlag']").val());
		$("[name='fdDetail_Form["+index+"].fdBaseCurrencyId']").val($("[name='fdDetail_Form["+(index-1)+"].fdBaseCurrencyId']").val());
		$("[name='fdDetail_Form["+index+"].fdBaseCurrencyName']").val($("[name='fdDetail_Form["+(index-1)+"].fdBaseCurrencyName']").val());
		$("[name='fdDetail_Form["+index+"].fdRate']").val($("[name='fdDetail_Form["+(index-1)+"].fdRate']").val());
		var fdPaymentType = $("[name='fdDetail_Form["+(index-1)+"].fdPaymentType']").val();
		if(fdPaymentType=='1'){
		}else{
			$("[name='fdDetail_Form["+index+"].fdBaseCurrencyId']").attr("readonly","readonly");
			$("[name='fdDetail_Form["+index+"].fdBaseCurrencyName']").attr("readonly","readonly");
			$("[name='fdDetail_Form["+index+"].fdRate']").attr("readonly","readonly");	
		}
		var fdIsTransfer = $("[name='fdDetail_Form["+index+"].fdIsTransfer']").val();
		initFS_GetFdIsTransfer(index, fdIsTransfer);//初始化收款账户信息是否必填
	}
}



//校验付款单明细付款金额之和是否等于付款单付款总金额
function validateMoney(){
	var isBoolean = true;
    var mainPaymentMoney = $("input[name='fdPaymentMoney']").val();
    var detailPaymentMoney = 0;
    var length = document.getElementById("TABLE_DocList_fdDetail_Form").rows.length-2;
    for(var i=0;i<length;i++){
        var fdPaymentMoney = $("input[name='fdDetail_Form["+i+"].fdPaymentMoney']").val();
        var fdRate = $("input[name='fdDetail_Form["+i+"].fdRate']").val();
        var fdPaymentType = $("[name='fdDetail_Form["+i+"].fdPaymentType']").val();
        if("0"==fdPaymentType){
        	 detailPaymentMoney = numAdd(detailPaymentMoney,fdPaymentMoney);
        }else{
        	 detailPaymentMoney = numAdd(detailPaymentMoney, multiPoint(fdPaymentMoney, fdRate));
        }
    }
    
	if(mainPaymentMoney*1 > detailPaymentMoney*1 || mainPaymentMoney*1 < detailPaymentMoney*1){
        seajs.use(['lui/jquery','lui/dialog'], function($, dialog){
            dialog.alert(messageInfo["fssc-cashier:fsscCashierPayment.fdPaymentMoney.message"].replace("%money%", mainPaymentMoney));
        });
        isBoolean = false;
	}
	return isBoolean;
}

function initFS_GetFdCstlchn(index){
	var fdSkipFlag = $("[name='fdDetail_Form["+index+"].fdSkipFlag']").val();
	if("Y" == fdSkipFlag){
		$("#req_fdCstlchn_"+index).hide();
	}else{
		$("#req_fdCstlchn_"+index).show();
	}
}

seajs.use(['lui/dialog','lang!fssc-cashier'],function(dialog,lang){
	//选择货币
	window.selectCurrency = function(){
		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
		var fdCompanyId = $("input[name='fdCompanyId']").val();
		if(!fdCompanyId){
			dialog.alert(messageInfo["fssc-cashier:fsscCashierPayment.fdPaymentMoney.message"]);
			return false;
		}
		var fdPaymentType= $("[name='fdDetail_Form["+index+"].fdPaymentType']").val();
		if(fdPaymentType=='1'){
			dialogSelect(false,'eop_basedata_currency_fdCurrency','fdDetail_Form[*].fdBaseCurrencyId','fdDetail_Form[*].fdBaseCurrencyName',null,{'fdCompanyId':fdCompanyId,'fdModelCurrencyIds':$('[name=fdModelCurrencyIds]').val()},function(rtn){
				if(rtn){
					var fdCurrencyId = rtn[0].fdId, data = new KMSSData();
					$("input[name='fdDetail_Form["+index+"].fdRate']").val("");
					data = data.AddBeanData('eopBasedataExchangeRateService&type=getRateByCurrency&fdCurrencyId='+fdCurrencyId+'&fdCompanyId='+fdCompanyId).GetHashMapArray();
					if(data.length>0){
						$("[name='fdDetail_Form["+index+"].fdRate']").val(data[0].fdExchangeRate);
					}
				}
			});
		}else{
			dialogSelect(false,'eop_basedata_currency_fdCurrency','fdDetail_Form[*].fdBaseCurrencyId','fdDetail_Form[*].fdBaseCurrencyName',null,{'fdCompanyId':fdCompanyId,'fdPaymentCurrencyId':$('[name=fdModelCurrencyIds]').val()},function(rtn){
				if(rtn){
					var fdCurrencyId = rtn[0].fdId, data = new KMSSData();
					$("input[name='fdDetail_Form["+index+"].fdRate']").val("");
					data = data.AddBeanData('eopBasedataExchangeRateService&type=getRateByCurrency&fdCurrencyId='+fdCurrencyId+'&fdCompanyId='+fdCompanyId).GetHashMapArray();
					if(data.length>0){
						$("[name='fdDetail_Form["+index+"].fdRate']").val(data[0].fdExchangeRate);
					}
				}
			});
		}

	}
//选择付款方式
	window.selectPayway = function(){
		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
		var fdCompanyId = $("input[name='fdCompanyId']").val();
		if(!fdCompanyId){
			dialog.alert(messageInfo["fssc-cashier:fsscCashierPayment.fdPaymentMoney.message"]);
			return false;
		}
		dialogSelect(false,'eop_basedata_pay_way_fdPayWay','fdDetail_Form[*].fdBasePayWayId','fdDetail_Form[*].fdBasePayWayName',null,{'fdCompanyId':fdCompanyId},function(rtn){
			if(rtn){
				$("[name='fdDetail_Form["+index+"].fdBasePayBankId']").val(rtn[0]["fdDefaultPayBank.fdId"]);
				$("[name='fdDetail_Form["+index+"].fdBasePayBankName']").val(rtn[0]["fdDefaultPayBank.fdBankName"]);
				$("[name='fdDetail_Form["+index+"].fdIsTransfer']").val(rtn[0]["fdIsTransfer"]);
				initFS_GetFdIsTransfer(index, rtn[0]['fdIsTransfer']);
			}
		});
	}

//选择付款银行
	window.selectPayBank=function(){
		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
		var fdCompanyId=$('[name=fdCompanyId]').val()
		dialogSelect(false,'eop_basedata_pay_bank_fdBank','fdDetail_Form['+index+'].fdBasePayBankId','fdDetail_Form['+index+'].fdBasePayBankName', null, {fdCompanyId:fdCompanyId},function(rtn){
			if(rtn){
				$("[name='fdDetail_Form["+index+"].fdBasePayBankName']").val(rtn[0].fdAccountName+"("+rtn[0].fdBankName+")");
			}
		});

	}

//选择账户归属地区
	window.FSSC_SelectPayeeCity = function(){
		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
		dialogSelect(false,'fssc_cmb_city_code','fdDetail_Form[*].fdPayeeCityCode','fdDetail_Form[*].fdPayeeCity',null,null,function(rtn){
			if(rtn){
				$("[name='fdDetail_Form["+index+"].fdPayeeCityCode']").val(rtn[0]['fdProvincialCode']);
				$("[name='fdDetail_Form["+index+"].fdPayeeCity']").val(rtn[0]['fdCity']);
			}
		});
	}
//选择账户归属地区(CBS)
	window.FSSC_SelectCbsPayeeCity = function(){
		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
		dialogSelect(false,'fssc_cbs_city','fdDetail_Form[*].fdPayeeCityCode','fdDetail_Form[*].fdPayeeCity',null,null,function(rtn){
			if(rtn){
				$("[name='fdDetail_Form["+index+"].fdPayeeCityCode']").val(rtn[0]['fdProvince']);
				$("[name='fdDetail_Form["+index+"].fdPayeeCity']").val(rtn[0]['fdCity']);
			}
		});
	}

//选择账户归属地区(CmbInt)
	window.FSSC_SelectCmbIntPayeeCity = function(){
		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
		dialogSelect(false,'fssc_cmbint_city_code','fdDetail_Form[*].fdPayeeCityCode','fdDetail_Form[*].fdPayeeCity',null,null,function(rtn){
			if(rtn){
				$("[name='fdDetail_Form["+index+"].fdPayeeCityCode']").val(rtn[0]['fdProvince']);
				$("[name='fdDetail_Form["+index+"].fdPayeeCity']").val(rtn[0]['fdCity']);
			}
		});
	}

	window.FSSC_ChangeSkipFlag = function(){
		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
		var fdSkipFlag = $("[name='fdDetail_Form["+index+"].fdSkipFlag']").val();
		if("Y" == fdSkipFlag){
			$("#req_fdCstlchn_"+index).hide();
			$("[id='_xform_fdDetail_Form["+index+"].fdCStlchn']").show();
			$("[id='_xform_fdDetail_Form["+index+"].fdCStlchn1']").hide();
		}else{
			$("#req_fdCstlchn_"+index).show();
			$("[id='_xform_fdDetail_Form["+index+"].fdCStlchn']").hide();
			$("[id='_xform_fdDetail_Form["+index+"].fdCStlchn1']").show();
		}
	};

	window.FSSC_DataChangeSkipFlag = function(){
		var index = DocListFunc_GetParentByTagName('TR').rowIndex-1;
		hideOrShow(index);
	};
	//根据同行跨行
	window.hideOrShow=function(index) {
		var fdSkipFlag = $("[name='fdDetail_Form[" + index + "].fdSkipFlag']").val();
		var validates = $("[name='fdDetail_Form[" + index + "].fdCStlchn']").attr("validate");
		if ("Y" == fdSkipFlag) {
			$("[id='_xform_fdDetail_Form[" + index + "].fdCStlchn']").find("span").hide();  //div上的ID，16会跟随索引刷新
			if (validates) {
				$("[name='fdDetail_Form[" + index + "].fdCStlchn']").attr("validate", validates.replace("required", ""));
			}
		} else {
			$("[id='_xform_fdDetail_Form[" + index + "].fdCStlchn']").find("span").show();  //div上的ID，16会跟随索引刷新
			$("[name='fdDetail_Form[" + index + "].fdCStlchn']").attr("validate", (validates || "") + " required");
		}
	}
	//发起付款操作
	window.payment = function(){
     	var fdIds = new Array()
		 $.each($("input[name='DocList_Selected']:checked"),function(index){
	       var fdId= $(this).val();
	       fdIds[index] = fdId;
	    });
		if(fdIds.length==0){
			dialog.alert(lang['message.selected.empty']);
			return false;
		}else{
			
			$.ajax({
				url:Com_Parameter.ContextPath + 'fssc/cashier/fssc_cashier_payment_detail/fsscCashierPaymentDetail.do?method=getIsPayment',
				data:{params:fdIds.join(",")},
				async:false,
				success:function(rtn){
					rtn = JSON.parse(rtn);
					if(rtn.result=='false'){
						dialog.alert(rtn.massege);
						return false;
					}else{
						paymentToCmb(fdIds);
					}
				}
			});
		}
	}
	
	window.paymentToCmb = function(fdIds){
		$.ajax({
			url:Com_Parameter.ContextPath + 'fssc/cashier/fssc_cashier_payment_detail/fsscCashierPaymentDetail.do?method=paymentToBank',
			data:{params:fdIds.join(",")},
			async:false,
			success:function(rtn){
				rtn = JSON.parse(rtn);
				if(rtn.result=='false'){
					dialog.alert(rtn.massege);
					return false;
				}else{
					dialog.alert(rtn.massege,function(){window.location.reload();});
				}
			}
		});
		
	}
	
	//查询付款状态
	window.queryPayment = function(){
		var fdId = $("input[name='fdId']").val();
		$.ajax({
			url:Com_Parameter.ContextPath + 'fssc/cashier/fssc_cashier_payment_detail/fsscCashierPaymentDetail.do?method=queryPayment',
			data:{params:fdId},
			async:false,
			success:function(rtn){
				rtn = JSON.parse(rtn);
				if(rtn.result=='false'){
					dialog.alert(rtn.massege);
					return false;
				}else{
					dialog.alert(rtn.massege,function(){window.location.reload();});
				}
			}
		});
	}
});





