define([
    "dojo/_base/declare",
    "dojo/_base/array",
    "sys/xform/mobile/controls/xformUtil",
    "dojo/request",
    "dojo/query",
    "mui/util",
    "mui/dialog/Tip",
    "mui/form/_GroupBase",
    "dojo/_base/lang",
    "mui/i18n/i18n!sys-xform-base",
    "sys/modeling/main/xform/controls/placeholder/mobile/Util",
    "dijit/registry"
], function (
    declare,
    array,
    xUtil,
    request,
    query,
    util,
    Tip,
    _GroupBase,
    lang,
    Msg,
    placeholderUtil,
    registry
) {
    var claz = declare("sys.modeling.main.xform.controls.filling.mobile.FillingEventBase", null, {
        //事件控件
        name: null,

        //入参
        inputParams: null,

        //出参
        outputParams: null,

        funKey: null,

        fillType: null,

        _inDetailTable: false,

        fieldName : null,
        fdAppModelId : null,
        envInfo: {},
        _hasSetValue:false,//是否有回填值

        getCfgInfo : function(relationId,widgetId){
            var relationInfo = this.envInfo[relationId];
            if (relationInfo) {
                return relationInfo[widgetId];
            }
            return {};
        },

        postCreate: function() {
            this.inherited(arguments);

        },

        // 回填值
        // rawRtn : [{docSubject:{value:xxxx},docCreatorId:{value:xxxx}}]
        _fillDataInfo_modeling : function(srcObj, rawRtn,relationId,widgetId) {
            this.inherited(arguments);
            if (rawRtn) {
                if (this.key == srcObj.key) {
                    if(!rawRtn){
                        return;
                    }
                    this._hasSetValue = true;
                    this.isInDetail = this.bindDom.indexOf(".") > -1 ? true : false;
                    if (!this.isInDetail) {
                        this.setValueWhenCloseForWgt(this, rawRtn,relationId,widgetId);
                    }else {
                        var cfg = this.getCfgInfo(relationId,widgetId);
                        var outputCfgs = cfg["outputs"];
                        var hasDetail = true;
                        //判断回填字段是否包含明细表字段
                        if(outputCfgs["details"]){
                            var comm = JSON.stringify(outputCfgs["details"]["sourceCommon"]);
                            var dts = JSON.stringify(outputCfgs["details"]["sourceDetails"]);
                            if(comm == "{}" && dts == "{}"){
                                hasDetail = false;
                            }
                        }
                        //#127170多选时，若在明细表内
                        for (let i = 0; i < rawRtn.length; i++) {
                            var rawItem = [rawRtn[i]];
                            if (i == 0) {
                                this.setValueWhenCloseForDetailWgt(this, rawItem,relationId,widgetId,rawRtn);
                                if(hasDetail){
                                    continue;
                                }else{
                                    //如果回填字段不包含明细表字段，则只需回填一次
                                    break;
                                }
                            }
                            //获取新增行的this指向
                            var name = xUtil.parseXformName(this);
                            var controlArray = name.split(".");
                            var newRow = window["detail_" + controlArray[0] + "_add"];
                            var newDom = $(newRow).find("xformflag[_xform_type='filling'][id^='_xform_extendDataFormInfo.value(" + controlArray[0] + "'][id$='" + controlArray[1] + ")']");
                            var newWgt_xform = newDom[newDom.length-1];
                            var newWgt = $(newWgt_xform).find(".muiFillingWrap ");
                            var newWgtId = $(newWgt).attr("id");
                            this.setValueWhenCloseForDetailWgt(registry.byId(newWgtId), rawItem,relationId,widgetId);
                        }
                    }
                }
            }

        },

        setValueWhenCloseForWgt: function (wgt, rawRtn,relationId,widgetId) {
            var cfg = wgt.getCfgInfo(relationId,widgetId);
            var outputCfgs = cfg["outputs"];

            wgt.outputCfgs = outputCfgs;
            wgt.isInDetail = wgt.bindDom.indexOf(".") > -1;
            if (wgt.isInDetail) {
                wgt.detailIndex = xUtil.getCalculationRowNo(wgt.name);
            }
            /*************** 设置传出参数 start ******************/
                //为非明细行合并数据
            var rtn_let = wgt.margeDataForNonDetail(rawRtn);
            // 目标控件为非明细表
            wgt.fillTargetControlsVal(rtn_let, outputCfgs["fields"]);
            // 目标控件为明细表
            wgt.fillTargetControlsValInDetail(rtn_let, outputCfgs["details"], rawRtn,relationId,widgetId);
            /*************** 设置传出参数 end ******************/
        },
        setValueWhenCloseForDetailWgt: function (wgt, rawRtn,relationId,widgetId,rowsRtn) {
            var cfg = wgt.getCfgInfo(relationId,widgetId);
            var outputCfgs = cfg["outputs"];

            wgt.outputCfgs = outputCfgs;
            wgt.isInDetail = wgt.bindDom.indexOf(".") > -1;
            if (wgt.isInDetail) {
                wgt.detailIndex = xUtil.getCalculationRowNo(wgt.name);
            }
            /*************** 设置传出参数 start ******************/
            //为非明细行合并数据
            if(rowsRtn){
                var rtn_let = wgt.margeDataForNonDetail(rowsRtn);
                // 目标控件为非明细表
                wgt.fillTargetControlsVal(rtn_let, outputCfgs["fields"]);
            }
            var rtn_row = wgt.margeDataForNonDetail(rawRtn);
            // 目标控件为明细表
            wgt.fillTargetControlsValInDetail(rtn_row, outputCfgs["details"], rawRtn,relationId,widgetId);
            /*************** 设置传出参数 end ******************/
        },
        //为非明细行合并数据
        margeDataForNonDetail : function(rowInfos){
            // 把所有行的数据进行合并返回
            let rs = {};
            for(var i = 0;i < rowInfos.length;i++){
                var info = rowInfos[i];
                for(var key in info){
                    // 多行记录的明细表，把所有行都放置在一起
                    if(info[key].type === "detail"){
                        if(!rs.hasOwnProperty(key)){
                            rs[key] = {value:[], type : info[key].type};
                        }
                        rs[key].value = rs[key].value.concat(info[key].value);
                    }else if(info[key].type.indexOf("com.landray.kmss.sys.organization") > -1){
                        // 地址本
                        var val = info[key].value || {};
                        if(!rs.hasOwnProperty(key)){
                            rs[key] = {value : {id : "",name : ""}, type : info[key].type};
                        }
                        if(rs[key].value.id && rs[key].value.name){
                            if(val.id && val.name){
                                rs[key].value.id += ";" + (val.id || "");
                                rs[key].value.name += ";" + (val.name || "");
                            }
                        }else{
                            rs[key].value.id = (val.id || "");
                            rs[key].value.name = (val.name || "");
                        }
                    }else{
                        if(!rs.hasOwnProperty(key)){
                            rs[key] = {value:"", type : info[key].type};
                        }
                        var val = info[key].value || this.defalutNull;
                        if (val === undefined && info[key].value !== undefined){
                            val = info[key].value;
                        }
                        // 由于返回的类型没法区分是否是是枚举类型，只能通过值的类型来区分
                        if(typeof(val) === "object" && val.hasOwnProperty("value")){
                            val = val["value"];
                        }
                        if(rs[key].value){
                            if(val){
                                rs[key].value += ";" + val;
                            }
                        }else{
                            rs[key].value = val;
                        }
                    }
                }
            }
            return rs;
        },

        // 给非明细表的控件赋值
        // rtn : 弹出框选择的行信息，cfgs ：后台配置的信息
        fillTargetControlsVal : function(rtn, cfgs){
            // 数据来源为非明细表数据
            for(var key in cfgs["sourceCommon"]){
                if(rtn.hasOwnProperty(key)){
                    var targets = cfgs["sourceCommon"][key].target || [];
                    for(var i = 0;i < targets.length;i++){
                        var targetInfo = targets[i];
                        var targetId = targetInfo["controlId"];
                        targetId = targetId.replace(".", "." + this.detailIndex + ".");
                        this._fillControlVal(targetId, rtn[key]["value"], targetInfo["type"]);
                    }
                }else{
                    console.log("【业务填充控件】对话框行返回信息里面不包含\""+ key +"\"的值!");
                }
            }
            // 数据来源为明细表数据
            for(var key in cfgs["sourceDetails"]){
                if(rtn.hasOwnProperty(key)){
                    var sourceDetails = cfgs["sourceDetails"][key];
                    for(var sourceControlId in sourceDetails){
                        var targets = sourceDetails[sourceControlId].target || [];
                        for(var i = 0;i < targets.length;i++){
                            var targetInfo = targets[i];
                            var values = rtn[key]["value"];
                            var val = "";
                            // 如果填充的值是数组，则把数据合并，以;号分开
                            if(values.length){
                                val = this._mergeDataInDetail(values, sourceControlId, targetInfo.type);
                            }
                            this._fillControlVal(targetInfo["controlId"], val ,targetInfo.type);
                        }
                    }
                }else{
                    console.log("【业务填充控件】对话框行返回信息里面不包含\""+ key +"\"的值!");
                }
            }
        },

        // 给明细表的控件赋值
        // rtn : 弹出框选择的行信息，cfgs ：后台配置的信息
        fillTargetControlsValInDetail : function(rtn, cfgs, rawRtn,relationId,widgetId){
            if(this.isInDetail){
                // 数据来源为非明细表数据
                for(var key in cfgs["sourceCommon"]){
                    if(rtn.hasOwnProperty(key)){
                        var targets = cfgs["sourceCommon"][key].target || [];
                        for(var i = 0;i < targets.length;i++){
                            var targetInfo= targets[i];
                            // 只允许给同明细表的同行数据赋值
                            if(this.bindDom.split(".")[0] === targetInfo.controlId.split(".")[0]){
                                var targetId = targetInfo["controlId"];
                                targetId = targetId.replace(".", "." + this.detailIndex + ".");
                                this._fillControlVal(targetId, rtn[key]["value"], targetInfo["type"]);
                            }else{
                                console.log("【业务填充控件】不支持明细表内的业务填充控件给其他明细表赋值");
                            }
                        }
                    }else{
                        console.log("【业务填充控件】对话框行返回信息里面不包含\""+ key +"\"的值!");
                    }
                }

                // 数据来源为明细表数据
                for(var key in cfgs["sourceDetails"]){
                    if(rtn.hasOwnProperty(key)){
                        var sourceDetails = cfgs["sourceDetails"][key];
                        for(var sourceControlId in sourceDetails){
                            var targets = sourceDetails[sourceControlId].target || [];
                            for(var i = 0;i < targets.length;i++){
                                var targetInfo = targets[i];
                                // 只允许给同明细表的同行数据赋值
                                if(this.bindDom.split(".")[0] === targetInfo.controlId.split(".")[0]){
                                    var values = rtn[key]["value"];
                                    var val = "";
                                    // 如果填充的值是数组，则把数据合并，以;号分开
                                    if(values.length){
                                        val = this._mergeDataInDetail(values, sourceControlId, targetInfo.type);
                                    }
                                    var targetId = targetInfo["controlId"];
                                    targetId = targetId.replace(".", "." + this.detailIndex + ".");
                                    this._fillControlVal(targetId, val ,targetInfo.type);
                                }else{
                                    console.log("【业务填充控件】不支持明细表内的业务填充控件给其他明细表赋值");
                                }
                            }
                        }
                    }else{
                        console.log("【业务填充控件】对话框行返回信息里面不包含\""+ key +"\"的值!");
                    }
                }

            }else{
                //明显表行填充模式
                this.changeDetailsByFillType(cfgs,relationId,widgetId);

                // 数据来源为非明细表数据,添加行,赋值
                for (let i=0; i < rawRtn.length; i++) {
                    let singleRtn = rawRtn[i];
                    //数据来源是否有明细表数据，有：false,无：false
                    var flag = true;
                    // 数据来源为明细表数据
                    for (var key in cfgs["sourceDetails"]) {
                        if (singleRtn.hasOwnProperty(key)) {
                            flag = false;
                            this.setDetailsTableFieldValues(cfgs["sourceDetails"][key], singleRtn[key]["value"],cfgs["sourceCommon"], singleRtn);
                        }
                    }
                    if (flag) {
                        this.addDetailsTableRow(cfgs["sourceCommon"], singleRtn);
                    }
                }
            }

        },
        // 明显表行填充模式
        changeDetailsByFillType :function (cfgs,relationId,widgetId){
            // 1|覆盖行数据（清空+追加）
            if (this.getCfgInfo(relationId,widgetId).hasOwnProperty('outExtend') && this.getCfgInfo(relationId,widgetId).outExtend === '1') {
                var sourceCommon  = cfgs["sourceCommon"];
                var sourceDetails = cfgs["sourceDetails"];
                //数据来源是主表
                for (var key in sourceCommon) {
                    this.overwriteRow(sourceCommon);
                }
                //数据来源是明细表
                for (var key in sourceDetails) {
                    var sources = cfgs["sourceDetails"][key];
                    this.overwriteRow(sources);
                }
            }
        }
        ,
        overwriteRow: function (sources) {
            let tableIds = [];
            for (var sourceId in sources) {
                var targetInfos = sources[sourceId]["target"];
                // 一行数据有可能传出到多个目标控件
                for (var j = 0;j < targetInfos.length;j++) {
                    let targetId = targetInfos[j]["controlId"];
                    let targetIdArr = targetId.split(".");
                    // 仅支持“明细表id.控件id”结构
                    if (targetIdArr.length === 2) {
                        var detailTableId = targetIdArr[0];
                        if ($.inArray(detailTableId, tableIds) === -1) {
                            tableIds.push(detailTableId);
                            var tdArray = query(".detail_wrap_td", "TABLE_DL_" + detailTableId);
                            if (tdArray.length == 0) {
                                tdArray = query("td[kmss_isrowindex]", "TABLE_DL_" + detailTableId);
                            }
                            if (tdArray.length > 0) {
                                array.map(tdArray, function (tdObj) {
                                    if (window['detail_' + detailTableId + '_delRow'])
                                        window['detail_' + detailTableId + '_delRow'](tdObj.parentNode);
                                });
                            }
                        }
                    }
                }
            }
        }
        ,

        // 合并明细表多行的数据
        _mergeDataInDetail : function(valueArr, sourceId, targetType){
            var val = null;
            for(var i = 0;i < valueArr.length;i++){
                var valueInfo = valueArr[i];
                if(valueInfo.hasOwnProperty(sourceId)){
                    var sourceVal = valueInfo[sourceId];
                    if(targetType.indexOf("com.landray.kmss.sys.organization") > -1){
                        // 地址本
                        var hasPre  = true;
                        val = val || {};
                        if(JSON.stringify(val) === '{}'){
                            val = {id : "",name : ""};
                            hasPre = false;
                        }
                        if(val.id && val.name){
                            if(sourceVal.id && sourceVal.name){
                                val.id += ";" + (sourceVal.id || "");
                                val.name += ";" + (sourceVal.name || "");
                            }
                        }else{
                            val.id = (sourceVal.id || "");
                            val.name = (sourceVal.name || "");
                        }
                    }else{
                        val = val || "";
                        var tempVal = sourceVal || "";
                        if(typeof(tempVal) === "object" && tempVal.hasOwnProperty("value")){
                            tempVal = tempVal["value"];
                        }
                        if(val){
                            if(tempVal){
                                val += ";" + tempVal;
                            }
                        }else{
                            val = tempVal;
                        }
                    }
                }
            }
            return val;
        },

        _fillControlVal : function(controlId, value, type){
            if(type.indexOf("com.landray.kmss.sys.organization") > -1){
                // 目标是地址本
                var id = "";
                var name = "";
                if(type.indexOf("[]")<0){
                    //地址本单选时，多值拼接，则取最后一个
                    if(typeof(value) === "object" && value){
                        id = value.id.substring(value.id.lastIndexOf(";")+1,value.id.length);
                        name = value.name.substring(value.name.lastIndexOf(";")+1,value.name.length);
                    }else{
                        id = name = value.substring(value.lastIndexOf(";")+1,value.length);
                    }
                }else{
                    if(typeof(value) === "object" && value){
                        id = value.id;
                        name = value.name;
                    }else{
                        id = name = value;
                    }
                }

                $form(controlId + ".id").val(id);
                $form(controlId + ".name").val(name);
            }else{
                var controlVal = value;
                // 由于返回的类型没法区分是否是是枚举类型，只能通过值的类型来区分
                if(typeof(controlVal) === "object" && controlVal && controlVal.hasOwnProperty("value")){
                    controlVal = controlVal["value"];
                }
                placeholderUtil.setXformWidgetValuesById(controlId,controlVal);
                // SetXFormFieldValueById_ext(controlId,controlVal);
            }
        },

        // 源明细表给目标控件填充值
        setDetailsTableFieldValues : function(sources, values,sourceCommon,singleRtn){
            for(var i = 0;i < values.length;i++){
                this.addDetailsTableRow(sources, values[i],sourceCommon,singleRtn);
            }
        },

        addDetailsTableRow : function(sources, rowValues,sourceCommon,singleRtn){
            var fieldsVal = {};
            var tableId = this.formatFieldsVal(sourceCommon,singleRtn,fieldsVal);
            var detailTableId = this.formatFieldsVal(sources,rowValues,fieldsVal);
            if(detailTableId){
                tableId = detailTableId;
            }
            if(tableId){
                // 这里有可能是新增一行空的一行，看后续需求是否需要做为空判断
                console.log("addrow",'detail_' + tableId + '_addRow');
                if(window['detail_' + tableId + '_addRow']){
                    window['detail_' + tableId + '_addRow'](function(rowTR){
                        for(var prop in fieldsVal){
                            var widgtId = prop.replace(/!{index}/g,rowTR.sectionRowIndex-1);
                            xUtil.setXformWidgetValues(xUtil.getXformWidget(rowTR,widgtId.replace(/\.name/gi,".id")), fieldsVal[prop],widgtId);
                        }
                    });
                }
            }
        },
        formatFieldsVal : function(sources, rowValues,fieldsVal){
            var tableId = "";
            // 遍历每一行需要输出控件配置信息
            for(var sourceId in sources){
                var targetInfos = sources[sourceId]["target"];
                // 一行数据有可能传出到多个目标控件
                for(var j = 0;j < targetInfos.length;j++){
                    var targetInfo = targetInfos[j];
                    var value = rowValues;
                    // 明细表外的控件给明细表内的控件赋值时，没有sourceId
                    if(typeof(value) === "object"){
                        if(value.hasOwnProperty(sourceId)){
                            value = value[sourceId];
                        }else{
                            value = "";
                            console.log("【业务填充控件】对话框行返回信息里面不包含输出控件ID\""+ sourceId +"\"的值!");
                        }
                    }
                    var targetId = targetInfo["controlId"];
                    var targetIdArr = targetId.split(".");
                    // 仅支持“明细表id.控件id”结构
                    if(targetIdArr.length === 2){
                        tableId = targetIdArr[0];
                        if(value){
                            if(targetInfo["type"].indexOf("com.landray.kmss.sys.organization") > -1){
                                // 目标是地址本
                                var id = "";
                                var name = "";
                                if (typeof (value) === "object") {
                                    //#133709 地址本源数据来自主表时多一层value对象
                                    var mainValue = value.value;
                                    if ( typeof (mainValue) ==="undefined"){
                                        id = value.id;
                                        name = value.name;
                                    }
                                    if (mainValue){
                                        id = mainValue.id;
                                        name = mainValue.name;
                                    }
                                }else{
                                    id = name = value;
                                    console.log("【业务填充控件】目标控件ID("+ targetIdArr[1] +")是地址本，但由于填充的值只有一个，故只能id和name都填充同样的值!");
                                }
                                fieldsVal["extendDataFormInfo.value(" + targetIdArr[0] + ".!{index}."+ targetIdArr[1] +".id)"] = id;
                                fieldsVal["extendDataFormInfo.value(" + targetIdArr[0] + ".!{index}."+ targetIdArr[1] +".name)"] = name;
                            }else{
                                var controlVal = value;
                                // 由于返回的类型没法区分是否是是枚举类型，只能通过值的类型来区分
                                if(typeof(controlVal) === "object" && controlVal.hasOwnProperty("value")){
                                    controlVal = controlVal["value"];
                                    //由于返回的类型没法区分是否是是枚举类型，只能通过值的类型来区分,value值可能还是object对象
                                    if(typeof(controlVal) === "object" && controlVal.hasOwnProperty("value")){
                                        controlVal = controlVal["value"];
                                    }
                                }
                                fieldsVal["extendDataFormInfo.value(" + targetIdArr[0] + ".!{index}."+ targetIdArr[1] +")"] = controlVal;
                            }
                        }else{
                            console.log("【业务填充控件】对话框行返回信息里面不包含输出控件ID\""+ targetId +"\"的值!");
                        }
                    }else{
                        console.log("【业务填充控件】输出控件ID("+ targetId +")结构异常，不符合\"明细表ID.控件ID\"结构！");
                    }
                }
            }
            return tableId;
        },
        getRowInfo : function(columns,rowIndex){
            // 行信息
            var rowInfo = {};
            for (var i = 0; i < columns.length; i ++) {
                var col = columns[i];
                rowInfo[col.name] = {value:col.data[rowIndex]};
                // 明细表
                rowInfo[col.name].type = col.type;
                if(col.type === "detail"){
                    // 列定义
                    rowInfo[col.name].columns = col.columns;
                }
            }
            return rowInfo;
        },

        // rtn : 弹出框选择的行信息，cfgs ：后台配置的信息
        fillTargetControlsEmptyVal : function(cfgs){
            // 数据来源为非明细表数据
            for(var key in cfgs["sourceCommon"]){
                var targets = cfgs["sourceCommon"][key].target || [];
                for(var i = 0;i < targets.length;i++){
                    var targetInfo = targets[i];
                    var targetId = targetInfo["controlId"];
                    targetId = targetId.replace(".", "." + this.detailIndex + ".");
                    this._fillControlVal(targetId, "", targetInfo["type"]);
                }
            }
            // 数据来源为明细表数据
            for(var key in cfgs["sourceDetails"]){
                var sourceDetails = cfgs["sourceDetails"][key];
                for(var sourceControlId in sourceDetails){
                    var targets = sourceDetails[sourceControlId].target || [];
                    for(var i = 0;i < targets.length;i++){
                        var targetInfo = targets[i];
                        this._fillControlVal(targetInfo["controlId"], "" ,targetInfo.type);
                    }
                }
            }
        },
        // 给明细表的控件赋值
        // rtn : 弹出框选择的行信息，cfgs ：后台配置的信息
        fillTargetControlsEmptyValInDetail : function(cfgs, rawRtn,relationId,widgetId){
            if(this.isInDetail){
                // 数据来源为非明细表数据
                for(var key in cfgs["sourceCommon"]){
                    var targets = cfgs["sourceCommon"][key].target || [];
                    for(var i = 0;i < targets.length;i++){
                        var targetInfo= targets[i];
                        // 只允许给同明细表的同行数据赋值
                        if(this.bindDom.split(".")[0] === targetInfo.controlId.split(".")[0]){
                            var targetId = targetInfo["controlId"];
                            targetId = targetId.replace(".", "." + this.detailIndex + ".");
                            this._fillControlVal(targetId, "", targetInfo["type"]);
                        }else{
                            console.log("【业务填充控件】不支持明细表内的业务填充控件给其他明细表赋值");
                        }
                    }
                }

                // 数据来源为明细表数据
                for(var key in cfgs["sourceDetails"]){
                    var sourceDetails = cfgs["sourceDetails"][key];
                    for(var sourceControlId in sourceDetails){
                        var targets = sourceDetails[sourceControlId].target || [];
                        for(var i = 0;i < targets.length;i++){
                            var targetInfo = targets[i];
                            // 只允许给同明细表的同行数据赋值
                            if(this.bindDom.split(".")[0] === targetInfo.controlId.split(".")[0]){
                                var val = "";
                                var targetId = targetInfo["controlId"];
                                targetId = targetId.replace(".", "." + this.detailIndex + ".");
                                this._fillControlVal(targetId, val ,targetInfo.type);
                            }else{
                                console.log("【业务填充控件】不支持明细表内的业务填充控件给其他明细表赋值");
                            }
                        }
                    }
                }
            }else{
                //明显表行填充模式
                this.changeDetailsByFillType(cfgs,relationId,widgetId);
            }

        },
    });
    return claz;
});
