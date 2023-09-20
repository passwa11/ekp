Com_AddEventListener(window,'load',function(){
	seajs.use(['lui/jquery','lui/dialog','lui/dialog_common','lui/util/str'], function($, dialog, dialogCommon,strutil){
//		var formValiduteObj = $KMSSValidation(document.forms[formOption.formName]);
        init();

        function init(){
            var fdCategoryModelName = $("input[name='fdCategoryModelName']").val();
            if(fdCategoryModelName){
                $("#_xform_fdCategoryId").show();
            }
            var mainPropertys = new Array('fdCompany','fdBaseCurrency');
            for(var i=0;i<mainPropertys.length;i++){
                onChangFlag(null, $("input[name='"+mainPropertys[i]+"Flag']"));
            }
            var detailPropertys = new Array('fdBasePayWay','fdBasePayBank','fdBaseCurrency');
            var length = document.getElementById("TABLE_DocList_fdDetail_Form").rows.length-2;
            for(var i=0;i<length;i++){
                for(var j=0;j<detailPropertys.length;j++){
                    onChangFlag(null, $("input[name='fdDetail_Form["+i+"]."+detailPropertys[j]+"Flag']"), i);
                }
            }
        }
	});
});

function selectFormula(id,name){
    var fdCashierModelConfigId = $("input[name='fdCashierModelConfigId']").val();
    var fdCashierModelConfigModelName = $("input[name='fdCashierModelConfigModelName']").val();
    if(!(fdCashierModelConfigId && fdCashierModelConfigModelName)){
        seajs.use(['lui/dialog'], function(dialog){
            dialog.alert(messageInfo["fssc-cashier:fsscCashierRuleConfig.fdCashierModelConfig.null"]);
        });
        return;
    }
    if(id.indexOf('*')>-1 && window.DocListFunc_GetParentByTagName){//明细
        var rowIndex;
        var tr=DocListFunc_GetParentByTagName('TR');
        var tb= DocListFunc_GetParentByTagName("TABLE");
        var tbInfo = DocList_TableInfo[tb.id];
        rowIndex=tr.rowIndex-tbInfo.firstIndex;
        id = id.replace("*", rowIndex);
        name = name.replace("*", rowIndex);
    }
    Formula_Dialog(id, name, Formula_GetVarInfoByModelName(fdCashierModelConfigModelName),'String');
}
//选择或公式定义器
function onChangFlag(value,obj,rowIndex){
    var name = $(obj).attr("name").replace("Flag", "");
    if(name.indexOf('fdDetail_Form[')>-1 && window.DocListFunc_GetParentByTagName){//明细
        if(rowIndex == null){
            var tr=DocListFunc_GetParentByTagName('TR');
            var tb= DocListFunc_GetParentByTagName("TABLE");
            var tbInfo = DocList_TableInfo[tb.id];
            rowIndex=tr.rowIndex-tbInfo.firstIndex;
        }
        name = name.replace("*", rowIndex);
        var flag = $("input[name='"+name+"Flag']:checked").val();
        if(flag*1 == 1){//选择
            $("[id='_xform_"+name+"Id']").show();
            $("[id='_xform_"+name+"Formula']").hide();

            $("input[name='"+name+"Formula']").val("");
            $("input[name='"+name+"Text']").val("");
        }else if(flag*1 == 2){//公式定义器
            $("[id='_xform_"+name+"Id']").hide();
            $("[id='_xform_"+name+"Formula']").show();

            $("input[name='"+name+"Id']").val("");
            $("input[name='"+name+"Name']").val("");
        }
    }else{
        var flag = $("input[name='"+name+"Flag']:checked").val();
        if(flag*1 == 1){//选择
            $("[id='_xform_"+name+"Id']").show();
            $("[id='_xform_"+name+"Formula']").hide();

            $("input[name='"+name+"Formula']").val("");
            $("input[name='"+name+"Text']").val("");

            $("input[name='"+name+"Text']").attr("validate", "");
            $("input[name='"+name+"Name']").attr("validate", "required");
        }else if(flag*1 == 2){//公式定义器
            $("[id='_xform_"+name+"Id']").hide();
            $("[id='_xform_"+name+"Formula']").show();

            $("input[name='"+name+"Id']").val("");
            $("input[name='"+name+"Name']").val("");

            $("input[name='"+name+"Name']").attr("validate", "");
            $("input[name='"+name+"Text']").attr("validate", "required");
        }
    }
}

//选择凭证模板设置回调函数
var oldFdCashierModelConfigId;
var oldFdCashierModelConfigName;
function selectFdCashierModelConfigNameCallback(rtnData){
    if(oldFdCashierModelConfigId != $("input[name='fdCashierModelConfigId']").val()){//出纳模板改动
        if(rtnData && rtnData.length > 0){
            var obj = rtnData[0];
            console.log(obj);
            if(oldFdCashierModelConfigId != '' ){
                seajs.use(['lui/dialog'], function(dialog){
                    dialog.confirm(messageInfo["fssc-cashier:fsscCashierRuleConfig.fdCashierModelConfig.change"],function(value){
                        if(value==true){
                            $("input[name='fdCashierModelConfigModelName']").val(obj["fdModelName"]);
                            var fdCategoryName = obj["fdCategoryName"];
                            if(fdCategoryName){
                                $("#_xform_fdCategoryId").show();
                                $("input[name='fdCategoryModelName']").val(fdCategoryName);
                                $("input[name='fdCategoryId']").val("");
                                $("input[name='fdCategoryName']").val("");
                            }else{
                                $("#_xform_fdCategoryId").hide();
                                $("input[name='fdCategoryId']").val("");
                                $("input[name='fdCategoryModelName']").val("");
                                $("input[name='fdCategoryName']").val("");
                            }
                            deteleMain();//清空主表信息
                            deteleAllDetail();//清空明细
                        }else{
                            $("input[name='fdCashierModelConfigId']").val(oldFdCashierModelConfigId);
                            $("input[name='fdCashierModelConfigName']").val(oldFdCashierModelConfigName);
                        }
                    });
                });
            }else{
                $("input[name='fdCashierModelConfigModelName']").val(obj["fdModelName"]);
                var fdCategoryName = obj["fdCategoryName"];
                if(fdCategoryName){
                    $("#_xform_fdCategoryId").show();
                    $("input[name='fdCategoryModelName']").val(fdCategoryName);
                    $("input[name='fdCategoryId']").val("");
                    $("input[name='fdCategoryName']").val("");
                }else{
                    $("#_xform_fdCategoryId").hide();
                    $("input[name='fdCategoryId']").val("");
                    $("input[name='fdCategoryModelName']").val("");
                    $("input[name='fdCategoryName']").val("");
                }
                deteleMain();//清空主表信息
                deteleAllDetail();//清空明细
            }
        }else{
            $("input[name='fdCashierModelConfigModelName']").val("")

            $("#_xform_fdCategoryId").hide();
            $("input[name='fdCategoryId']").val("");
            $("input[name='fdCategoryModelName']").val("");
            $("input[name='fdCategoryName']").val("");
        }
    }
}

//清空主表信息
function deteleMain(){
    $("input[name='fdRuleFormula']").val("");//生成规则
    $("input[name='fdRuleText']").val("");
    $("input[name='fdModelNumberFormula']").val("");//来源单据编号
    $("input[name='fdModelNumberText']").val("");
    $("input[name='fdCompanyFlag']").removeAttr('checked');//公司
    $("input[name='fdCompanyFormula']").val("");
    $("input[name='fdCompanyText']").val("");
    $("input[name='fdCompanyId']").val("");
    $("input[name='fdCompanyName']").val("");
    $("#_xform_fdCompanyId").hide();
    $("#_xform_fdCompanyFormula").hide();
    $("input[name='fdBaseCurrencyFlag']").removeAttr('checked');//货币
    $("input[name='fdBaseCurrencyFormula']").val("");
    $("input[name='fdBaseCurrencyText']").val("");
    $("input[name='fdBaseCurrencyId']").val("");
    $("input[name='fdBaseCurrencyName']").val("");
    $("#_xform_fdBaseCurrencyId").hide();
    $("#_xform_fdBaseCurrencyFormula").hide();
}

//清空明细
function deteleAllDetail(){
    var length = document.getElementById("TABLE_DocList_fdDetail_Form").rows.length-2;
    if(length > 0 ){
        //清除所有明细行
        for(var i=0;i<length;i++){
            DocList_DeleteRow($("#TABLE_DocList_fdDetail_Form").find("tr").eq(1)[0]);
        }
    }
}
