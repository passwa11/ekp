function mySubmit(docStatus, method_, isDraft){
    if(docStatus == '20'){//提交
        if(!isExist20RepaymentData()){//校验借款单是否存在在途的还款
            return false;
        }
        if(!validateFdTransferMoney()){//校验转移金额是否超借款金额
            return false;
        }
        if(!validateFdReceive()){//校验接受人不能跨公司
        	return false;
        }
        
    }

	submitForm(docStatus, method_, isDraft);
}



//选择借款单回调函数
function selectfdLoanMainNameCallback(rtnData){
    if (rtnData && rtnData.length > 0) {
        var obj = rtnData[0];
        $("input[name='fdLoanMainCompanyId']").val(obj["fdCompany.fdId"]);//公司id
        getFdCanOffsetMoney(obj["fdId"]);
    }
}

//获取借款单未冲销金额
function getFdCanOffsetMoney(fdLoanMainId){
    var fdCanOffsetMoney = 0;
    var data = new KMSSData();
    var results = data.AddBeanData("fsscLoanMainService&authCurrent=true&flag=getCanUseMoney&fdLoanMainId=" + fdLoanMainId).GetHashMapArray();
    if (results.length > 0&&results[0].fdMoney) {
        $("input[name='fdCanOffsetMoney']").val(formatScientificToNum(results[0].fdMoney,2));//未冲销金额
        $("input[name='fdTransferMoney']").val(formatScientificToNum(results[0].fdMoney,2));//转出金额
        fdCanOffsetMoney = results[0].fdMoney;
    }
    return fdCanOffsetMoney;
}

//校验借款单是否存在在途的还款
function isExist20RepaymentData(){
    var isBoolean = true;
    var fdLoanMainId = $("input[name='fdLoanMainId']").val();
    var data = new KMSSData();
    var results = data.AddBeanData("fsscLoanMainService&authCurrent=true&flag=isExist20RepaymentData&fdLoanMainId=" + fdLoanMainId).GetHashMapArray();
    if (results.length > 0) {
        if(results[0].isBoolean == "true"){
            seajs.use(['lui/dialog'], function(dialog){
                dialog.alert(messageInfo["fssc-loan:fsscLoanTransfer.isExist20RepaymentData.message"]);
            });
            isBoolean = false;
        }
    }
    return isBoolean;
}

//校验接受人不能跨公司
function validateFdReceive(){
    var isBoolean = true;
    var fdReceiveId = $('input[name=fdReceiveId]').val();
    var fdLoanMainId = $('input[name=fdLoanMainId]').val();
    var data = new KMSSData();
    var results = data.AddBeanData("fsscLoanMainService&authCurrent=true&flag=checkFdReceive&fdReceiveId="+fdReceiveId+"&fdLoanMainId="+fdLoanMainId).GetHashMapArray();
    if (results.length > 0) {
        if(results[0].isBoolean == "true"){
            seajs.use(['lui/dialog'], function(dialog){
                dialog.alert(messageInfo["fssc-loan:fsscLoanTransfer.isvalidateFdReceive.message"]);
            });
            isBoolean = false;
        }
    }
    return isBoolean;
}

//校验转移金额是否超借款金额
function validateFdTransferMoney(){
    var isBoolean = true;
    var fdTransferMoney = $("input[name='fdTransferMoney']").val();
    var fdLoanMainId = $("input[name='fdLoanMainId']").val();
    if(fdTransferMoney == "" || fdLoanMainId == ""){
        return isBoolean;
    }
    var fdCanOffsetMoney = getFdCanOffsetMoney(fdLoanMainId);//获取未冲销金额
    if(fdCanOffsetMoney*1 < fdTransferMoney*1){
        seajs.use(['lui/dialog'], function(dialog){
            dialog.alert(messageInfo["fssc-loan:fsscLoanTransfer.fdTransferMoney.message"].replace("%money%", fdCanOffsetMoney));
        });
        isBoolean = false;
    }
    return isBoolean;
}


//异步加载部门信息
function onValueChangeFdReceive(){
    var fdReceiveId = $('input[name=fdReceiveId]').val();
    if(fdReceiveId==''){
        return;
    }
    $.ajax({
        url: Com_Parameter.ContextPath+"fssc/loan/fssc_loan_main/fsscLoanMain.do?method=loadPersonInfo&fdPersonId="+fdReceiveId,
        dataType:"json",
        success: function(data){
            // 清空值
            emptyNewAddress("fdReceiveDeptName",null,null,false);
            if(data['fdDeptId']){
                var fdDeptId = data['fdDeptId'];
                var fdDeptName = data['fdDeptName'];
                //赋值
                $('input[name="fdReceiveDeptId"]').val(fdDeptId);
                $('input[name="fdReceiveDeptName"]').val(fdDeptName);
                var addressInput = $("[xform-name='mf_fdReceiveDeptName']")[0];
                var addressValues = new Array();
                addressValues.push({id:fdDeptId,name:fdDeptName});
                newAddressAdd(addressInput,addressValues);
            }else{
                $('input[name="fdReceiveDeptId"]').val("");
                $('input[name="fdReceiveDeptName"]').val("");
            }
            if(data['fdCostCenterId']){
                $('input[name="fdReceiveCostCenterId"]').val(data['fdCostCenterId']);
                $('input[name="fdReceiveCostCenterName"]').val(data['fdCostCenterName']);
            }else{
                $('input[name="fdReceiveCostCenterId"]').val('');
                $('input[name="fdReceiveCostCenterName"]').val('');
            }
        }
    });
}
