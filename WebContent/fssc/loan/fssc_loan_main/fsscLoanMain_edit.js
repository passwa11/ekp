var oldFdLoanPersonId,oldFdLoanPersonName;
seajs.use(['lui/jquery','lui/dialog','lui/dialog_common','lui/util/str','lang!fssc-loan'], function($, dialog, dialogCommon,strutil,lang){
Com_AddEventListener(window,'load',function(){
//		var formValiduteObj = $KMSSValidation(document.forms[formOption.formName]);

        setTimeout(function(){
            oldFdLoanPersonId = $("input[name='fdLoanPersonId']").val();
            oldFdLoanPersonName = $("input[name='fdLoanPersonName']").val();
        },100);
        setTimeout(function(){
       	 if(formInitData['fdIsRequiredFee']=='true'){
            	//设置事前申请必填
            	$("[name='fdFeeMainName']").attr("validate","required");
            	$("[name='fdFeeMainId']").parent().parent().find("span").attr("style","float:right;");
            	$(".tempClass").attr("style","display:block;");
            }
       	 initDocSubject();//初始化标题
//         initSAP();//初始SAP字段
         initFdRemind();//初始化提示信息
         initFS_GetFdIsTransfer();//初始化收款账户信息是否必填
       },600);
	});

	//借款提交时，校验借款如果关联事前，借款金额不允许超出事前申请总金额
	Com_Parameter.event.submit.push(function(){
		var fdFeeId=$("[name='fdFeeMainId']").val();
		var fdStandardMoney=$("input[name='fdStandardMoney']").val();  //本次借款金额
		//无事前不作校验
		if(!fdFeeId){
			return true;
		}
		var pass = true;
		$.ajax({
			url:Com_Parameter.ContextPath + 'fssc/loan/fssc_loan_main/fsscLoanMain.do?method=checkLoanMoney&fdFeeId='+fdFeeId,
			async:false,
			success:function(rtn){
				rtn = JSON.parse(rtn);
				if(rtn.result=='1'){//正确返回结果
					if(rtn.rtnMoney-fdStandardMoney<0){//返回金额小于借款金额，说明不允许提交
						dialog.alert(lang['message.feeMoney.less.loanMoney']);
						pass=false;
					}
				}
			}
		});
		return pass;
	});
});

function mySubmit(docStatus, method_, isDraft){
	if(docStatus == '20'){//提交
        //借款控制
        var fdPersonId = $("input[name='fdLoanPersonId']").val();
        var fdLoanCategoryId = $("input[name='docTemplateId']").val();
        var fdLoanMoney = $("input[name='fdLoanMoney']").val();
        if(!fdPersonId || !fdLoanCategoryId || fdLoanMoney == '' || fdLoanMoney <=0){
            //满足条件，清空借款提示
            $("input[name='fdRemind']").val("");
            $("#fdRemindSpan").html("");
            return submitForm(docStatus, method_, isDraft);
		}else if($("[name='checkVersion']").val()=="true"){
            var data = new KMSSData();
			var results = data.AddBeanData("fsscLoanMainService&authCurrent=true&flag=loanControl&fdPersonId="+fdPersonId+"&fdLoanCategoryId="+fdLoanCategoryId+"&fdLoanMoney="+fdLoanMoney).GetHashMapArray();
			if(results.length>0){
				var isTrue = results[0].isTrue;//是否满足条件 true满足条件，false不满足条件
				if(isTrue == "false"){
					var fdForbid = results[0].fdForbid;//刚柔控 1刚控，2揉控
					seajs.use(['lui/dialog'], function(dialog){
						if(fdForbid == "1"){//刚控不允许提交
							dialog.alert(results[0].errorMessage);
						}else{//揉控，弹出确认框
							dialog.confirm(results[0].errorMessage,function(value){
								if(value==true){
                                    $("input[name='fdRemind']").val(results[0].errorMessage);
                                    $("#fdRemindSpan").html(results[0].errorMessage);

                                    initFdRemind();//初始化提示信息
									submitForm(docStatus, method_, isDraft);
								}
							});
						}
					});
				}else{
				    //满足条件，清空借款提示
                    $("input[name='fdRemind']").val("");
                    $("#fdRemindSpan").html("");
					submitForm(docStatus, method_, isDraft);
				}
			}else{
                //满足条件，清空借款提示
                $("input[name='fdRemind']").val("");
                $("#fdRemindSpan").html("");
				submitForm(docStatus, method_, isDraft);
			}
		}else{
			submitForm(docStatus, method_, isDraft);
		}
	}else{
		submitForm(docStatus, method_, isDraft);
	}
}

var oldFdCompanyId;
//选择公司回调函数
function selectFdCompanyNameCallback(rtnData){
	if(oldFdCompanyId != $("input[name='fdCompanyId']").val()){//公司改动
		$("input[name='fdCostCenterId']").val("");//成本中心
		$("input[name='fdCostCenterName']").val("");
		$("input[name='fdFeeMainId']").val("");//关联事前
		$("input[name='fdFeeMainName']").val("");
		$("input[name='fdBasePayWayId']").val("");//付款方式
		$("input[name='fdBasePayWayName']").val("");
		$("input[name='fdBaseProjectId']").val("");
		$("input[name='fdBaseProjectName']").val("");
		$("input[name='fdBaseWbsId']").val("");
		$("input[name='fdBaseWbsName']").val("");
		$("input[name='fdBaseInnerOrderId']").val("");
		$("input[name='fdBaseInnerOrderName']").val("");
		$("input[name='fdBaseProjectAccountingId']").val("");
		$("input[name='fdBaseProjectAccountingName']").val("");
		if(rtnData && rtnData.length > 0){
			var obj = rtnData[0];
			$("input[name='fdBaseCurrencyId']").val(obj["fdAccountCurrency.id"]);//本位币
		}
		window.FSSC_ReloadCostCenter();	//加载默认的成本中心
		window.FSSC_ReloadPayway();  //加载默认的付款方式
        window.FSSC_ReloadStandardCurrency();//加载默认的币种
	}
}

//加载默认的成本中心
window.FSSC_ReloadCostCenter = function(){
	var fdCompanyId = $("[name=fdCompanyId]").val();
	//重新带出成本中心
	var data = new KMSSData();
	data.AddBeanData("eopBasedataCostCenterService&authCurrent=true&flag=defaultCostCenter&fdCompanyId="+fdCompanyId+"&fdPersonId="+$("[name=fdLoanPersonId]").val());
	data = data.GetHashMapArray();
	if(data.length>0){
		$("[name=fdCostCenterId]").val(data[0].id);
		$("[name=fdCostCenterName]").val(data[0].name);
	}
}

//加载默认的付款方式
window.FSSC_ReloadPayway = function(){
	var fdCompanyId = $("[name=fdCompanyId]").val();
	var data = new KMSSData();
	data.AddBeanData("eopBasedataPayWayService&type=default&fdCompanyId="+fdCompanyId);
	data = data.GetHashMapArray();
	if(data.length>0){
		$("input[name='fdBasePayWayId']").val(data[0].value);
		$("input[name='fdBasePayWayName']").val(data[0].text);
		$("input[name='fdBankId']").val(data[0].fdBankId);
		$("input[name='fdIsTransfer']").val(data[0].fdIsTransfer);
		initFS_GetFdIsTransfer();
	}
}
//加载默认的币种
window.FSSC_ReloadStandardCurrency = function () {
	var fdCompanyId = $("[name=fdCompanyId]").val();
	var data = new KMSSData();
        data.AddBeanData("eopBasedataCompanyService&type=getStandardCurrencyInfo&authCurrency=true&fdCompanyId=" + fdCompanyId);
        data = data.GetHashMapArray();
        if (data.length > 0) {
            $("input[name='fdBaseCurrencyId']").val(data[0].fdCurrencyId);
            $("input[name='fdBaseCurrencyName']").val(data[0].fdCurrencyName);
            $("input[name='fdExchangeRate']").val(data[0].fdExchangeRate);
			FSSC_ChangeStandardMoney();
        }else{
			$("input[name='fdBaseCurrencyId']").val("");
			$("input[name='fdBaseCurrencyName']").val("");
			$("input[name='fdExchangeRate']").val("");
			$("input[name='fdStandardMoney']").val("");
		}


}

//计算本币金额
window.FSSC_ChangeStandardMoney= function(){
	var fdApplyMoney = $("[name='fdApplyMoney']").val();
	var fdExchangeRate= $('input[name="fdExchangeRate"]').val();
	if(fdApplyMoney&&fdExchangeRate){
		fdStandardMoney = multiPoint(fdApplyMoney,fdExchangeRate);
		$("[name='fdStandardMoney']").val(fdStandardMoney);
	}
}

/*//选择员工账号回调函数
function selectFdAccPayeeNameCallback(rtnData){
	if(rtnData && rtnData.length > 0){
		var obj = rtnData[0];
		$("input[name='fdPayeeAccount']").val(obj.fdBankAccount);//开户行账号
		$("input[name='fdPayeeBank']").val(obj.fdBankName);//开户行
		$("input[name='fdAccountAreaName']").val(obj.fdAccountArea);//开户地
		$("input[name='fdBankAccountNo']").val(obj.fdBankNo);//银联号
	}
}*/
//选择账户归属城市回调函数
function  selectFdAccountAreaCallback(rtnData){
	if(rtnData && rtnData.length > 0){
		var obj = rtnData[0];
		$("input[name='fdAccountAreaCode']").val(obj.fdProvincial);//开户行账号
		$("input[name='fdAccountAreaName']").val(obj.fdCity);//开户行
	}
}
//选择账户归属城市回调函数
function  selectFdAccountAreaCbsCallback(rtnData){
	if(rtnData && rtnData.length > 0){
		var obj = rtnData[0];
		$("input[name='fdAccountAreaCode']").val(obj.fdProvince);
		$("input[name='fdAccountAreaName']").val(obj.fdCity);
	}
}

//选择账户归属城市回调函数
function  selectFdAccountAreaCmbIntCallback(rtnData){
	if(rtnData && rtnData.length > 0){
		var obj = rtnData[0];
		$("input[name='fdAccountAreaCode']").val(obj.fdProvincial);//开户行账号
		$("input[name='fdAccountAreaName']").val(obj.fdCity);//开户行
	}
}

//选择付款方式回调函数
function afterSelectPayWay(rtnData){
	if(rtnData && rtnData.length > 0){
		var obj = rtnData[0];
		var fdIsTransfer = obj.fdIsTransfer;
		$("[name='fdBankId']").val(rtnData[0]['fdDefaultPayBank.fdId']);
		$("[name='fdBankName']").val((rtnData[0]['fdDefaultPayBank.fdBankName']||'')+(rtnData[0]['fdDefaultPayBank.fdBankAccount']||''));
		$("input[name='fdIsTransfer']").val(fdIsTransfer);
		initFS_GetFdIsTransfer();
	}
}
	
//预计还款日期不能早于填单日期
function onChangFdExpectedDate() {
	var docCreateTime = $('input[name="docCreateTime"]').val();
	var fdExpectedDate = $('input[name="fdExpectedDate"]').val();
	var time1 = new Date(docCreateTime.replace(/\-/gi, "/")).getTime();
	var time2 = new Date(fdExpectedDate.replace(/\-/gi, "/")).getTime();
	if (time1 != null && time1 != "" && time2 != null
			&& time2 != "") {
		if (time2 < time1) {
			seajs.use(['lui/jquery','lui/dialog'], function($, dialog){
				dialog.alert(messageInfo["fssc-loan:fsscLoanMain.fdExpectedDate.IsEarly"]);
				$('input[name="fdExpectedDate"]').val("");
			});
		}
	}
}
function initFdRemind(){
    var fdRemind = $("input[name='fdRemind']").val();
    if(fdRemind){
        $(".fdRemind").show();
    }else{
        $(".fdRemind").hide();
    }
}
function initDocSubject(){
    var docTemplateSubjectType = $("input[name='docTemplateSubjectType']").val();
    var validateStr = $("input[name='docSubject']").attr("validate");
    if(!validateStr){
        validateStr = "";
    }
    if(docTemplateSubjectType*1 == 2){//自动生成
        if($("input[name='docSubject']").val() == ""){
            $("input[name='docSubject']").val(messageInfo["fssc-loan:module.docSubject.message"]);
        }
        $("input[name='docSubject']").attr("readOnly", "readOnly");
        $("#docSubjectSp").hide();
        $("input[name='docSubject']").attr("validate", validateStr.replace("required", ""));
    }else{
        $("#docSubjectSp").show();
        $("input[name='docSubject']").attr("validate", validateStr + " required");
    }

}
function initSAP(){
    var fdIsChechedSAP = $("input[name='fdIsChechedSAP']").val();
    var fdExtendFields = $("input[name='fdExtendFields']").val();
    var fdIsProject  = $("input[name='fdIsProject']").val();
    if(fdIsProject=='true' && fdIsChechedSAP=="true" ){
    	if(fdExtendFields.indexOf("2;1")>-1 || fdExtendFields.indexOf("1;2")>-1){
    		 $(".fdBaseProjectContent").attr("colspan", "1");
             $(".fdBaseWbs").show();
             $(".fdBaseInnerOrder").show();
    	}else if(fdExtendFields .indexOf("1")>-1){
    	 	 $(".fdBaseProjectContent").attr("colspan", "3");
             $(".fdBaseWbs").show();
             $(".fdBaseInnerOrder").hide();
    	}else if(fdExtendFields .indexOf("2")>-1){
    		$(".fdBaseProjectContent").attr("colspan", "3");
       	 	$(".fdBaseWbs").hide();
       	 	$(".fdBaseInnerOrder").show();
    	}else{
    	    $(".fdBaseProjectContent").attr("colspan", "5");
	        $(".fdBaseWbs").hide();
	        $(".fdBaseInnerOrder").hide();
    	}
    }else if(fdIsProject=='false' && fdIsChechedSAP=="true"){
    	if(fdExtendFields.indexOf("2;1")>-1 || fdExtendFields.indexOf("1;2")>-1){
            $(".fdBaseWbs").show();
            $(".fdBaseInnerOrder").show();
            $("#fdBaseInnerOrders").after("<td colspan='2'></td>");
	   	}else if(fdExtendFields .indexOf("1")>-1){
	        $(".fdBaseWbs").show();
	        $(".fdBaseInnerOrder").hide();
	        $("#fdBaseInnerOrders").after("<td colspan='4'></td>");
	   	}else if(fdExtendFields .indexOf("2")>-1){
	      	$(".fdBaseWbs").hide();
	      	$(".fdBaseInnerOrder").show();
	        $("#fdBaseInnerOrders").after("<td colspan='4'></td>");
	   	}else{
		    $(".fdBaseWbs").hide();
		    $(".fdBaseInnerOrder").hide();
	   	}
    }else{
    	 $(".fdBaseWbs").hide();
         $(".fdBaseInnerOrder").hide();
    }
}

function initFS_GetFdIsTransfer(){
	var fdIsTransfer = $("input[name='fdIsTransfer']").val();
	if(fdIsTransfer =='false'){	//收款账户信息非必填
		$('input[name="fdAccPayeeName"]').attr("validate","");
		$('input[name="fdPayeeBank"]').attr("validate","checkNull");
		$('input[name="fdPayeeAccount"]').attr("validate","checkNull");
		$('input[name="fdBankAccountNo"]').attr("validate","checkNull");
		$('input[name="fdAccountAreaName"]').attr("validate","maxLength(200)");
		$(".vat").hide();
	}else{	//收款账户信息必填
		$('input[name="fdAccPayeeName"]').attr("validate","required");
		$('input[name="fdPayeeBank"]').attr("validate","required checkNull");
		$('input[name="fdPayeeAccount"]').attr("validate","required checkNull");
		$('input[name="fdBankAccountNo"]').attr("validate","required checkNull");
		$('input[name="fdAccountAreaName"]').attr("validate","required maxLength(200)");
		$(".vat").show();
	}
}

//切换项目清空wbs
window.afterSelectProject = function(rtn){
	if(rtn){
		$("[name=fdBaseWbsId]").val("");
		$("[name=fdBaseWbsName]").val("");
	}
}


//选择币种
window.FSSC_SelectCurrency = function () {
    var fdCompanyId = $("[name='fdCompanyId']").val();
    if (fdCompanyId == '') {
		seajs.use(['lui/jquery', 'lui/dialog'], function ($, dialog) {
			dialog.alert(messageInfo["fssc-loan:message.fsscLoanMain.fdCompany.isNull"]);
		});

        return;
    }
    dialogSelect(false, 'eop_basedata_currency_fdCurrency', 'fdBaseCurrencyId', 'fdBaseCurrencyName', null, {fdCompanyId: fdCompanyId}, function (rtn) {
        if (!rtn || rtn.length == 0) {
            $("[name='fdExchangeRate']").val("");
			$('input[name="fdStandardMoney"]').val("");
            return;
        }
        data = new KMSSData();
        var fdCurrencyId = rtn[0].fdId;
        data = data.AddBeanData('eopBasedataExchangeRateService&authCurrent=true&type=getRateByCurrency&fdCurrencyId=' + fdCurrencyId + '&fdCompanyId=' + fdCompanyId).GetHashMapArray();
        if (data.length > 0 && data[0].fdExchangeRate != null) {
            $('input[name="fdExchangeRate"]').val(data[0].fdExchangeRate);
			FSSC_ChangeStandardMoney();
        } else {
            seajs.use(['lui/jquery', 'lui/dialog'], function ($, dialog) {
                dialog.alert(messageInfo['fssc-loan:tips.exchangeRateNotExist']);
            });
			$('input[name="fdBaseCurrencyId"]').val("");
			$('input[name="fdBaseCurrencyName"]').val("");
			$('input[name="fdStandardMoney"]').val("");
            $("[name=fdExchangeRate]").val("");
        }
    });
}



//异步加载部门信息
function onValueChangeFdLoanPerson(){
    var fdLoanPersonId = $('input[name=fdLoanPersonId]').val();
    var fdLoanCategoryId = $("input[name='docTemplateId']").val();
    // 清空关联事前申请值
	$("[name=fdFeeMainId]").val("");
	$("[name=fdFeeMainName]").val("");

    $.ajax({
        url: Com_Parameter.ContextPath+"fssc/loan/fssc_loan_main/fsscLoanMain.do?method=loadPersonInfo&fdPersonId="+fdLoanPersonId+"&fdLoanCategoryId="+fdLoanCategoryId,
        dataType:"json",
        success: function(data){
			$('input[name="fdLoanDeptId"]').val(data['fdDeptId']);
			$('input[name="fdLoanDeptName"]').val(data['fdDeptName']);
            $('input[name="fdCompanyId"]').val(data['fdCompanyId']);
            $('input[name="fdCompanyName"]').val(data['fdCompanyName']);
            $('input[name="fdBaseCurrencyId"]').val(data['fdBaseCurrencyId']);
            $('input[name="fdBaseCurrencyName"]').val(data['fdBaseCurrencyName']);
            $('input[name="fdCostCenterId"]').val(data['fdCostCenterId']);
            $('input[name="fdCostCenterName"]').val(data['fdCostCenterName']);
            $('input[name="fdBasePayWayId"]').val(data['fdBasePaywayId']);
            $('input[name="fdBasePayWayName"]').val(data['fdBasePaywayName']);
            $('input[name="fdAccPayeeName"]').val(data['fdBaseAccountName']);
            $('input[name="fdPayeeAccount"]').val(data['fdBaseAccountBankAccount']);
            $('input[name="fdPayeeBank"]').val(data['fdBaseAccountBankName']);
			$('input[name="fdAccountAreaName"]').val(data['fdBaseAccountAreaName']);
			$('input[name="fdBankAccountNo"]').val(data['fdBaseBankAccountNo']);
            $('input[name="fdTotalLoanMoney"]').val(formatFloat(data['fdTotalLoanMoney'],2));
            $('#fdTotalLoanMoney').html(formatFloat(data['fdTotalLoanMoney'],2));
            $('input[name="fdTotalRepaymentMoney"]').val(formatFloat(data['fdTotalRepaymentMoney'],2));
            $('#fdTotalRepaymentMoney').html(formatFloat(data['fdTotalRepaymentMoney'],2));
            $('input[name="fdTotalNotRepaymentMoney"]').val(formatFloat(data['fdTotalNotRepaymentMoney'],2));
            $('#fdTotalNotRepaymentMoney').html(formatFloat(data['fdTotalNotRepaymentMoney'],2));
            window.FSSC_ReloadPayway()
        }
    });
    
    if(fdLoanPersonId=='' || fdLoanPersonId == oldFdLoanPersonId){
    	 oldFdLoanPersonId = fdLoanPersonId;
    	 oldFdLoanPersonName = fdLoanPersonName;
        return;
    }
    //添加可冲销者
    var fdOffsetterIds = $("input[name='fdOffsetterIds']").val();
    var fdOffsetterNames = $("input[name='fdOffsetterNames']").val();
    var fdLoanPersonName = $("input[name='fdLoanPersonName']").val();
    var docCreatorId = $("input[name='docCreatorId']").val();
    if(fdOffsetterIds.indexOf(fdLoanPersonId) < 0){//如果不含有新借款人
        if(fdOffsetterIds.indexOf(oldFdLoanPersonId) >= 0 && docCreatorId != oldFdLoanPersonId){//如果久借款人还在，且不是创建人就替换成新借款人
            fdOffsetterIds = fdOffsetterIds.replace(oldFdLoanPersonId, fdLoanPersonId);
            fdOffsetterNames = fdOffsetterNames.replace(oldFdLoanPersonName, fdLoanPersonName);
            $("#_xform_fdOffsetterIds li").find("input").each(function () {
                if($(this).val() == oldFdLoanPersonId){
                    $(this).parent().html($(this).parent().html().replace(oldFdLoanPersonName, fdLoanPersonName));
                    $(this).val(fdLoanPersonId);
                }
            });
            $("input[name='fdOffsetterIds']").val(fdOffsetterIds);
            $("input[name='fdOffsetterNames']").val(fdOffsetterNames);
        }else{
            if(fdOffsetterIds.length>0){
                fdOffsetterIds += ";";
                fdOffsetterNames += ";";
            }
            if(fdOffsetterIds.indexOf(fdLoanPersonId) < 0){
                fdOffsetterIds += fdLoanPersonId+";"
                fdOffsetterNames += fdLoanPersonName+";"
            }
            if(fdOffsetterIds.length>0){
                fdOffsetterIds = fdOffsetterIds.substring(0, fdOffsetterIds.length-1);
                fdOffsetterNames = fdOffsetterNames.substring(0, fdOffsetterNames.length-1);
                $("input[name='fdOffsetterIds']").val(fdOffsetterIds);
                $("input[name='fdOffsetterNames']").val(fdOffsetterNames);
                var addressInput = $("[xform-name='mf_fdOffsetterNames']")[0];
                var addressValues = new Array();
                addressValues.push({id:fdLoanPersonId,name:fdLoanPersonName});
                newAddressAdd(addressInput,addressValues);
            }
        }
    }else if(fdOffsetterIds.indexOf(oldFdLoanPersonId) >= 0 && docCreatorId != oldFdLoanPersonId){//如果含有新借款人,且含有久借款人，且久借款人不是创建人就删除久借款人
        if(fdOffsetterIds.indexOf(";"+oldFdLoanPersonId) >= 0){
            fdOffsetterIds = fdOffsetterIds.replace(";"+oldFdLoanPersonId, "");
            fdOffsetterNames = fdOffsetterNames.replace(";"+oldFdLoanPersonName, "");
        }else{
            fdOffsetterIds = fdOffsetterIds.replace(oldFdLoanPersonId, "");
            fdOffsetterNames = fdOffsetterNames.replace(oldFdLoanPersonName, "");
        }
        $("#_xform_fdOffsetterIds li").find("input").each(function () {
            if($(this).val() == oldFdLoanPersonId){
                $(this).parent().remove();
            }
        });
        $("input[name='fdOffsetterIds']").val(fdOffsetterIds);
        $("input[name='fdOffsetterNames']").val(fdOffsetterNames);
    }
    oldFdLoanPersonId = fdLoanPersonId;
    oldFdLoanPersonName = fdLoanPersonName;
}

//收款明细选择收款人账户
window.FSSC_SelectAccount = function(){
	dialogSelect(false,'eop_basedata_account_fdAccount','fdAccPayeeId','fdAccPayeeName',null,null,function(rtnData){
		if(rtnData && rtnData.length > 0){
			var obj = rtnData[0];
			$("input[name='fdPayeeAccount']").val(obj.fdBankAccount);//开户行账号
			$("input[name='fdPayeeBank']").val(obj.fdBankName);//开户行
			$("input[name='fdAccountAreaName']").val(obj.fdAccountArea);//开户地
			$("input[name='fdBankAccountNo']").val(obj.fdBankNo);//银联号
		}
	});
}

// 给“关联事前申请” 赋值
window.FSSC_AfterFeeMainSelected = function(data){
	if(data&&data.length>0){
		var ids = [],names=[];
		for(var i=0;i<data.length;i++){
			ids.push(data[i].fdId);
			names.push(data[i].docSubject);
		}
		$("[name=fdFeeMainId]").val(ids.join(";"))
		$("[name=fdFeeMainName]").val(names.join(";"))
	}
}
