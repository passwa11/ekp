Com_AddEventListener(window,'load',function(){
	seajs.use(['lui/jquery','lui/dialog','lui/dialog_common','lui/util/str'], function($, dialog, dialogCommon,strutil){
//		var formValiduteObj = $KMSSValidation(document.forms[formOption.formName]);

        initDocSubject();//初始化标题

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
	});
});

function mySubmit(docStatus, method_, isDraft){
    if(docStatus == '20'){//提交
        if(!validateFdRepaymentMoney()){
            return false;
        }
    }
	submitForm(docStatus, method_, isDraft);
}

var oldFdLoanMainId;

//选择借款单回调函数
function selectfdLoanMainNameCallback(rtnData){
    if(oldFdLoanMainId != $("input[name='fdLoanMainId']").val()) {//借款单改动
        $("input[name='fdBasePayWayId']").val("");//付款方式
        $("input[name='fdBasePayWayName']").val("");
    }
    if (rtnData && rtnData.length > 0) {
        var obj = rtnData[0];
        $("input[name='fdLoanMainCompanyId']").val(obj["fdCompany.fdId"]);//公司id
        getFdCanOffsetMoney(obj["fdId"]);
        // #127609 给“付款方式” 赋值
        assignmentFdBasePayWayName(obj["fdId"]);
    }
}

function assignmentFdBasePayWayName(obj) {
    var fdLoanMainId = obj;
    $.ajax({
        url: Com_Parameter.ContextPath+'fssc/loan/fssc_loan_repayment/fsscLoanRepayment.do?method=getFdBasePayWayName',
        data: {fdId: fdLoanMainId},
        async: false,
        type: 'POST',
        dataType: 'json',
        success: function(data){
            if(data){
                //付款方式
                $("input[name='fdBasePayWayId']").val(data['payWayId']);
                $("input[name='fdBasePayWayName']").val(data['payWayName']);
            }
        }
    });
}

//获取借款单未冲销金额
function getFdCanOffsetMoney(fdLoanMainId){
    var fdCanOffsetMoney = 0;
    var docStatus=$("[name='docStatus']").val();
    var data = new KMSSData();
    var fdModelId=Com_GetUrlParameter(window.location.href, "fdId");
    var results = data.AddBeanData("fsscLoanMainService&authCurrent=true&flag=getCanUseMoney&fdLoanMainId=" + fdLoanMainId+"&fdModelId="+fdModelId).GetHashMapArray();
    if (results.length > 0&&results[0].fdMoney) {
        if(docStatus!='20'&&docStatus!='30'){
            $("input[name='fdCanOffsetMoney']").val(formatScientificToNum(results[0].fdMoney,2));//未冲销金额
        }
        fdCanOffsetMoney = results[0].fdMoney;
    }
    return fdCanOffsetMoney;
}

//判断借款单是否不为空
function fdLoanMainIdIsNotNull(){
    var isBoolean = false;
    if($("input[name='fdLoanMainId']").val()){
        isBoolean = true;
    }else{
        seajs.use(['lui/dialog'], function(dialog){
            dialog.alert(messageInfo["fssc-loan:fsscLoanRepayment.fdLoanMainId.is.null"]);
        });
    }
    return isBoolean;
}

function onValueChangeFdRepaymentMoney(){
    if(!fdLoanMainIdIsNotNull()){
        $("input[name='fdRepaymentMoney']").val("");
        return;
	}
}
//校验还款金额是否超借款金额
function validateFdRepaymentMoney(){
    var isBoolean = true;
    var fdRepaymentMoney = $("input[name='fdRepaymentMoney']").val();
    var fdLoanMainId = $("input[name='fdLoanMainId']").val();
    if(fdRepaymentMoney == "" || fdLoanMainId == ""){
        return isBoolean;
    }
    var fdCanOffsetMoney = getFdCanOffsetMoney(fdLoanMainId);//获取未冲销金额
    if(fdCanOffsetMoney*1 < fdRepaymentMoney*1){
        seajs.use(['lui/dialog'], function(dialog){
            dialog.alert(messageInfo["fssc-loan:fsscLoanRepayment.fdRepaymentMoney.message"].replace("%money%", fdCanOffsetMoney));
        });
        isBoolean = false;
    }
    return isBoolean;
}
