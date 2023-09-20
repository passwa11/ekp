
var formOption = {
    formName: 'fsscCtripFiledMappingForm',
    modelName: 'com.landray.kmss.fssc.ctrip.model.FsscCtripFiledMapping',
    templateName: '',
    subjectField: 'fdAirLine',
    dialogs: {
    	fssc_ctrip_model_fdmodel: {
            modelName: 'com.landray.kmss.fssc.ctrip.model.FsscCtripModel',
            sourceUrl: '/fssc/ctrip/fssc_ctrip_model/fsscCtripModelData.do?method=fdModel'
        },
        fssc_ctrip_select_fdTempate: {
            modelName: 'com.landray.kmss.fssc.ctrip.model.FsscCtripModel',
            sourceUrl: '/fssc/ctrip/fssc_ctrip_model/fsscCtripModelData.do?method=selectTemplate'
        }
    },
    dialogLinks: [],
    valueLinks: [],
    attrLinks: [],
    optionLinks: [],
    linkValidates: [],
    detailTables: [],
    dataType: {},
    detailNotNullProp: {
        fdTrainDetail_Form: {
            text: [],
            textarea: []
        },
        fdCarQuickDetail_Form: {
            text: [],
            textarea: []
        },
        fdHotelDetail_Form: {
            text: [],
            textarea: []
        }
    }
};

var FS_changgeModel = function(rtnData){
	 if(rtnData && rtnData.length > 0){
        var obj = rtnData[0];
        var fdModelName = obj["fdModelName"];
        fdModelCate = obj["fdCategoryName"];
        fdKey= obj["fdKey"];
        if(fdModelCate){
       	 $("#_xform_fdCateId").show();
        }else{
       	 $("#_xform_fdCateId").hide();
        }
        $("input[name=fdMainModelName]").val(fdModelName);
        $("input[name=fdTemplateModelName]").val(fdModelCate);
        $("input[name=fdKey]").val(fdKey);
	 }else{
		 $("input[name=fdModelName]").val("");
        $("input[name=fdModelCate]").val("");
        $("input[name=fdKey]").val("");
        $("#_xform_fdCateId").hide();
	 }
}