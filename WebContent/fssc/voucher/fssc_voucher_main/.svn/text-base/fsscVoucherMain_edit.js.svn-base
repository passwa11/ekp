Com_AddEventListener(window,'load',function(){
	seajs.use(['lui/jquery','lui/dialog','lui/dialog_common','lui/util/str'], function($, dialog, dialogCommon,strutil){
//		var formValiduteObj = $KMSSValidation(document.forms[formOption.formName]);

        initSAP();//初始SAP字段
	});
});

function initSAP(){
    var fdIsChechedSAP = $("input[name='fdIsChechedSAP']").val();
    if(fdIsChechedSAP=="SAP"){//对接的财务系统是SAP
        $(".fdBaseSupplierContent").attr("colspan", "1");
        $(".fdBaseWbs").show();
        $(".fdBaseInnerOrder").show();
    }else{
        $(".fdBaseSupplierContent").attr("colspan", "5");
        $(".fdBaseWbs").hide();
        $(".fdBaseInnerOrder").hide();
    }

}

/*******************************************************************************
 * 提交方法
 */
function mySubmit(form, method) {
    var tbInfo = DocList_TableInfo['TABLE_DocList'];
    if (!validateEvidence(tbInfo)) {//校验借贷金额是否相等
        return false;
    }
    if (!validateTotalMoney(tbInfo)) {//校验金额是否和单据总金额相等
        return false;
    }
    Com_Submit(form, method);
}

/*******************************************************************************
 * 校验借贷金额是否相等
 */
function validateEvidence(tbInfo) {
    var fdBorrowTotal = 0.00; // 借
    var fdLoanTotal = 0.00; // 贷
    for (var i = 0; i < tbInfo.lastIndex - 1; i++) {
        var tempMoney = $('input[name="fdDetail_Form[' + i + '].fdMoney"]').val()* 1;// 金额
        var evidenceType = $('input[name="fdDetail_Form[' + i + '].fdType"]').val();//借贷
        if ('2' == evidenceType) {//贷
            fdBorrowTotal = numAdd(fdBorrowTotal, tempMoney);
        }else{
            fdLoanTotal= numAdd(fdLoanTotal, tempMoney);
        }
    }
    if(fdLoanTotal > fdBorrowTotal || fdLoanTotal < fdBorrowTotal){
        seajs.use(['lui/dialog'], function(dialog){
            dialog.alert(messageInfo["fssc-voucher:errors.borrowingAmount.equation"].replace("%fdLoanTotal%", parseFloat(fdLoanTotal.toFixed(2))).replace("%fdBorrowTotal%", parseFloat(fdBorrowTotal.toFixed(2))));
        });
        return false;
    }
    return true;
}

/*******************************************************************************
 * 校验金额是否和单据总金额相等
 */
function validateTotalMoney(tbInfo) {
    var fdModelMoney = $('input[name="fdModelMoney"]').val()* 1;// 单据总金额
    if(fdModelMoney == ""){
        return true;
    }
    var fdBorrowTotal = 0.00; // 贷
    for (var i = 0; i < tbInfo.lastIndex - 1; i++) {
        var tempMoney = $('input[name="fdDetail_Form[' + i + '].fdMoney"]').val()* 1;// 金额
        var evidenceType = $('input[name="fdDetail_Form[' + i + '].fdType"]').val();//借贷
        if ('2' == evidenceType) {//贷
            fdBorrowTotal = parseFloat(fdBorrowTotal)+parseFloat(tempMoney);
        }
    }
    if (parseFloat(fdModelMoney.toFixed(2)) != parseFloat(fdBorrowTotal.toFixed(2))) {
        seajs.use(['lui/dialog'], function(dialog){
            dialog.alert(messageInfo["fssc-voucher:errors.validateTotalMoney.equation"].replace("%fdVoucherTotal%", parseFloat(fdBorrowTotal.toFixed(2))).replace("%fdModelTotal%", parseFloat(fdModelMoney.toFixed(2))));
        });
        return false;
    }
    return true;
}

var oldFdCompanyId;
var oldFdCompanyName;
//选择公司回调函数
function selectFdCompanyNameCallback(rtnData){
    if(oldFdCompanyId != $("input[name='fdCompanyId']").val()){//公司改动
        if(rtnData && rtnData.length > 0){
            var obj = rtnData[0];
            if(oldFdCompanyId != ""){
                seajs.use(['lui/dialog'], function(dialog){
                    dialog.confirm(messageInfo["fssc-voucher:fsscVoucherMain.deleteAll.detail"],function(value){
                        if(value==true){
                            $("input[name='fdCompanyCode']").val(obj["fdCode"]);//公司编号
                            $("input[name='fdBaseCurrencyId']").val(obj["fdAccountCurrency.id"]);//本位币
                            $("input[name='fdBaseCurrencyName']").val(obj["fdAccountCurrency.name"]);//
                            $("input[name='fdIsChechedSAP']").val(obj["fdJoinSystem"]);
                            initSAP();//初始SAP字段
                            deteleAllDetail();//清空明细
                        }else{
                            $("input[name='fdCompanyId']").val(oldFdCompanyId);
                            $("input[name='fdCompanyName']").val(oldFdCompanyName);
                        }
                    });
                });
            }else{
                $("input[name='fdCompanyCode']").val(obj["fdCode"]);//公司编号
                $("input[name='fdBaseCurrencyId']").val(obj["fdAccountCurrency.id"]);//本位币
                $("input[name='fdBaseCurrencyName']").val(obj["fdAccountCurrency.name"]);//
                $("input[name='fdIsChechedSAP']").val(obj["fdJoinSystem"]);
                initSAP();//初始SAP字段
            }
        }
    }
}

//清空明细
function deteleAllDetail(){
    var length = document.getElementById("TABLE_DocList").rows.length-1;
    if(length > 0 ){
        //清除所有明细行
        for(var i=0;i<length;i++){
            DocList_DeleteRow($("#TABLE_DocList").find("tr").eq(1)[0]);
        }
    }
}

/*******************************************************************************
 * 根据凭证日期修改会计期间和年度
 */
function onChangFdVoucherDate(){
    var fdDate = $('input[name="fdVoucherDate"]').val();
    if(fdDate.length>7){
        var startDate = new Date(fdDate.replace(/-/g,"/"));
        var year = startDate.getFullYear();
        var periodTemp = startDate.getMonth()+1;
        var period = Number(periodTemp);
        $('input[name="fdAccountingYear"]').val(year);
        if(periodTemp < 10){
            $('input[name="fdPeriod"]').val("0"+period);
        }else{
            $('input[name="fdPeriod"]').val(period);
        }
    }
}
