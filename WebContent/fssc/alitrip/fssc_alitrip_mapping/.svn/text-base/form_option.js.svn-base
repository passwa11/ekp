
var formOption = {
    formName: 'fsscAlitripMappingForm',
    modelName: 'com.landray.kmss.fssc.alitrip.model.FsscAlitripMapping'

    ,
    dialogs: {
        fssc_alitrip_model_fdmodel: {
            modelName: 'com.landray.kmss.fssc.alitrip.model.FsscAlitripModel',
            sourceUrl: '/fssc/alitrip/fssc_alitrip_model/fsscAlitripModelData.do?method=fdmodel'
        },
        fssc_alitrip_select_fdmodel: {
            modelName: 'com.landray.kmss.fssc.alitrip.model.FsscAlitripModel',
            sourceUrl: '/fssc/alitrip/fssc_alitrip_model/fsscAlitripModelData.do?method=fdModel'
        }
    },
    dialogLinks: [],
    valueLinks: [],
    attrLinks: [],
    optionLinks: [],
    linkValidates: [],
    detailTables: [],
    dataType: {}
};

var FS_changgeModel = function(rtnData){
	 if(rtnData && rtnData.length > 0){
         var obj = rtnData[0];
         var fdModelName = obj["fdModelName"];
         fdModelCate = obj["fdCategoryName"];
         fdKey= obj["fdKey"];
         if(!(typeof fdModelCate == "undefined" || fdModelCate == null || fdModelCate == "" )){
        	 $("#_xform_fdCateId").show();
         }else{
        	 $("#_xform_fdCateId").hide();
         }
         $("input[name=fdModelName]").val(fdModelName);
         $("input[name=fdModelCate]").val(fdModelCate);
         $("input[name=fdKey]").val(fdKey);
	 }else{
		 $("input[name=fdModelName]").val("");
         $("input[name=fdModelCate]").val("");
         $("input[name=fdKey]").val("");
         $("#_xform_fdCateId").hide();
	 }
}

/*function selectFormula(id,name){
    var fdVoucherModelConfigId = $("input[name='fdCateId']").val();
    var fdVoucherModelConfigModelName = $("input[name='fdModelName']").val();
    if(!(fdVoucherModelConfigId && fdVoucherModelConfigModelName)){
        seajs.use(['lui/dialog'], function(dialog){
            dialog.alert(messageInfo["fssc-voucher:fsscVoucherRuleConfig.fdVoucherModelConfig.null"]);
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
    Formula_Dialog(id, name, Formula_GetVarInfoByModelName(fdVoucherModelConfigModelName),'String');
}*/
