/**
 *
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var dialog = require('lui/dialog');
    var source = require("lui/data/source");
    var placeholderBaseWgts = require("sys/modeling/main/xform/controls/placeholder/placeholderBaseWgts");
    var relationUtil = require("sys/modeling/main/xform/controls/placeholder/relationUtil");

    var extraDealControl=["xform_select","xform_checkbox"];
    var DialogPlaceholder = placeholderBaseWgts.CustomPlaceholder.extend({

            dialogUrl: "/sys/modeling/main/xform/controls/placeholder/dialog/dialog.jsp?multi=false",

            defalutNull: "",	// 为null，默认显示值

            doRender: function () {
                this.html();
            },

            html: function () {
                var $rootNode = $("<div class='lui-placeholder'>");
                //this.domNode.append($rootNode);
                this.txtShowNode = $("<div class='lui-placeholder-textshow'>");
                $rootNode.append(this.txtShowNode);
                var width_let = this.maxWidth - 51;
                if (width_let > 0)
                    this.txtShowNode.width(width_let);
                else
                    this.txtShowNode.hide();

                var $oper = $("<div class='lui-placeholder-oper'>");
                // 添加按钮
                this.buttonNode = $("<span class='lui-placeholder-button'>");
                if(this.config.fillingType == "filling"){
                    var bindDom = this.parent.config.bindDom;
                    var myId = this.parent.config.controlId;
                    if(!_modelingFillingMap[bindDom]){
                        _modelingFillingMap[bindDom] = [];
                        _modelingFillingMap[bindDom].push(myId);
                    }else{
                        _modelingFillingMap[bindDom].push(myId);
                    }
                    this.buttonNode.html("填充");
                    this.buttonNode.css({"border": "1px solid #d5d5d5",
                    "width": "60px","text-align":"center","height":"28px","line-height":"28px",
                        "border-radius":"4px",
                    "display": "block"})
                }else{
                    this.buttonNode.html("选择");
                }
                $oper.append(this.buttonNode);
                $rootNode.append($oper);

                /************* 设置必填 start *****************/
                if (this.required === "true") {
                    $rootNode.append("<span class=txtstrong>*</span>");
                }
                /************* 设置必填 end *****************/
                var self = this;
                this.buttonNode.on('click', function (event,arg1) {
                    if(self.config.fillingType == "filling"){
                        if(!arg1){
                            var _currentTriggerNotVal = false;
                            var flagid = $(this).closest("xformflag").attr("flagid");
                            var binddom = $(this).closest("xformflag").find("[mytype='filling']").attr("binddom");
                            if(binddom.indexOf(".") > -1){
                                //明细表中，binddom没办法加上当前行数的序号，因此这里需要手动获取加上
                                //另外，填充控件配置上要求放置在当前触发控件的后面，因此可以通过拿myid来获取当前的明细表行数
                                var index = XForm_GetIndexInDetailByDom($(this));
                                var binddomArr = binddom.split(".");
                                binddom = binddomArr[0]+"."+index+"."+binddomArr[1];
                            }
                            var type = $("[name*='extendDataFormInfo.value("+binddom+")']").attr("type");
                            var changeValue;
                            if(type == "radio"){
                                changeValue = $("[name*='extendDataFormInfo.value("+binddom+")']:checked").val();
                            }else if(type == "checkbox"){
                                //多选框有隐藏域
                                changeValue = $("input[type='hidden'][name*='extendDataFormInfo.value("+binddom+")']").val();
                            }else{
                                changeValue = $("[name*='extendDataFormInfo.value("+binddom+")']").val();
                            }
                            //#162298 兼容地址本控件取值
                            if(!changeValue){
                                changeValue = self.getFormValueById(binddom);
                            }
                            if(!changeValue || changeValue == "" || changeValue.length == 0){
                                _currentTriggerNotVal =  true ;
                            }
                            self.execFilling(_currentTriggerNotVal,true,flagid);
                        }/*else{
                            self.execFilling();
                        }*/
                    }else{
                        self.openDialog();
                    }
                });

                this.setValue(this.textNode.val(), this.inputNode.val(), true);

                this.emit("html", $rootNode);
            },

            openDialog: function () {
                var self = this;
                // 根据配置信息获取表单数据
                var formDatas = self.findFormValueByCfg(self.getCfgInfo())
                var url = self.dialogUrl;
                var height = document.documentElement.clientHeight * 0.78;
                var width = document.documentElement.clientWidth * 0.78;
                self.$dialog = dialog.iframe(url, "选择记录", function (rtn) {
                    self.setValueWhenClose(rtn);
                }, {
                    width: width,
                    height: height,
                    params: {
                        formDatas: formDatas,
                        widgetId: self.parent.config.controlId,
                        fdAppModelId: self.parent.config.fdModelId,
                        cfgInfo: self.getCfgInfo() || {}
                    }
                });
            },

            // rawRtn : [{docSubject:{value:xxxx},docCreatorId:{value:xxxx}}]
            setValueWhenClose: function (rawRtn) {
                if (!rawRtn) {
                    return;
                }
                // 兼容非多选框的返回值
                if (rawRtn.length === undefined) {
                    rawRtn = [rawRtn];
                }
                this.lastRawRtn = rawRtn;
                var isInDetail = this.controlId.indexOf(".") > -1;
                if (!isInDetail) {
                    this.setValueWhenCloseForWgt(this, rawRtn)
                } else {
                    //#127170多选时，若在明细表内
                    for (let i = 0; i < rawRtn.length; i++) {
                        var rawItem = [rawRtn[i]];
                        if (i == 0) {
                            this.setValueWhenCloseForWgt(this, rawItem);
                            continue;
                        }
                        var controlArray = this.controlId.split(".");
                        var tableId = "TABLE_DL_" + controlArray[0];
                        var newRow = DocList_AddRow(tableId, null);
                        var newWgt = $(newRow).find("xformflag[flagtype='placeholder'][flagid^='" + controlArray[0] + "'][flagid$='" + controlArray[1] + "']");
                        var wgtId = newWgt.find(".modelingPlaceholder").attr("id");
                        this.setValueWhenCloseForWgt( LUI(wgtId).placeholderWgt, rawItem);
                    }
                }
            },
            setValueWhenCloseForWgt: function (wgt, rawRtn) {
                var cfg = wgt.getCfgInfo();
                var outputCfgs = cfg["outputs"];

                wgt.outputCfgs = outputCfgs;
                wgt.outputCfgs = outputCfgs;
                wgt.isInDetail = wgt.controlId.indexOf(".") > -1;
                if (wgt.isInDetail) {
                    wgt.detailIndex = XForm_GetIndexInDetailByDom(wgt.parent.element);
                }
                /*************** 设置传出参数 start ******************/
                    //为非明细行合并数据
                var rtn_let = wgt.margeDataForNonDetail(rawRtn);
                // 目标控件为非明细表
                wgt.fillTargetControlsVal(rtn_let, outputCfgs["fields"]);
                // 目标控件为明细表
                wgt.fillTargetControlsValInDetail(rtn_let, outputCfgs["details"], rawRtn);
                /*************** 设置传出参数 end ******************/
                // 根据配置（表达式）设置当前控件值
                if(!this.config.fillingType){
                    wgt.setCurrentControlVal(rawRtn, wgt.getCfgInfo());
                }
            },


            // 给非明细表的控件赋值
            // rtn : 弹出框选择的行信息，cfgs ：后台配置的信息
            fillTargetControlsVal: function (rtn, cfgs) {
                //todo 需要先清空附件
                // for (var key in cfgs["sourceCommon"]) {
                //     if (cfgs["sourceCommon"][key].type.indexOf("Attachment") > -1) {
                //         var swfobj;
                //         if (top.window)
                //             swfobj = top.window.Attachment_ObjectInfo[key];
                //         else
                //             swfobj = window.Attachment_ObjectInfo[key];
                //         console.log("swfobj::", key, swfobj.fileList)
                //
                //
                //         for (var i = 0; i < swfobj.fileList.length; i++) {
                //             swfobj.delFileList(swfobj.fileList[i].fdId)
                //         }
                //         console.log("-----swfobj::", key, swfobj.fileList)
                //     }
                // }
                // 数据来源为非明细表数据
                for (var key in cfgs["sourceCommon"]) {
                    if (rtn.hasOwnProperty(key)) {
                        var targets = cfgs["sourceCommon"][key].target || [];
                        for (var i = 0; i < targets.length; i++) {
                            var targetInfo = targets[i];
                            var targetId = targetInfo["controlId"];
                            // #97081  附件特殊处理-case 1 ：主表To主表
                            if (targetInfo["type"].indexOf("Attachment") > -1) {
                                this.setAttTargetMain(targetInfo, rtn["fdId"]["value"], rtn[key]["value"])
                                continue;
                            }
                            targetId = targetId.replace(".", "." + this.detailIndex + ".");
                            this._fillControlVal(targetId, rtn[key]["value"], targetInfo["type"]);
                        }
                    } else {
                        console.log("【业务关联控件】对话框行返回信息里面不包含\"" + key + "\"的值!");
                    }
                }
                // 数据来源为明细表数据
                for (var key in cfgs["sourceDetails"]) {
                    if (rtn.hasOwnProperty(key)) {
                        var sourceDetails = cfgs["sourceDetails"][key];
                        for (var sourceControlId in sourceDetails) {
                            var targets = sourceDetails[sourceControlId].target || [];
                            for (var i = 0; i < targets.length; i++) {
                                var targetInfo = targets[i];
                                var values = rtn[key]["value"];
                                var val = "";
                                // 如果填充的值是数组，则把数据合并，以;号分开
                                if (values.length) {
                                    var targetWgt = GetXFormFieldById(targetInfo["controlId"]);
                                    if(targetWgt.length){
                                        //取类型只需拿第一个就行
                                        targetWgt = targetWgt[0];
                                    }
                                    var flagtype = $(targetWgt).closest("xformflag").attr("flagtype");
                                    var isMix = false;
                                    if(flagtype == "xform_radio" || flagtype == "xform_datetime"
                                        || flagtype == "xform_select" || flagtype == "radio"
                                        || flagtype == "select"){
                                        //单值类型的控件不做合并，做覆盖处理
                                        isMix = true;
                                    }
                                    if(flagtype == "xform_address"){
                                        //地址本，判断多选还是单选
                                        isMix = $(targetWgt).closest("xformflag").find("input[xform-type='newAddress']").attr("data-ismulti");
                                    }
                                    val = this._mergeDataInDetail(values, sourceControlId, targetInfo.type,isMix);
                                }
                                if (targetInfo.type.indexOf("Attachment") > -1) {
                                    // #97081  附件特殊处理-case2  ：明细表数据To主表
                                    this.setAttTargetMain(targetInfo, rtn["fdId"]["value"], val.split(";"))
                                    continue;
                                }

                                this._fillControlVal(targetInfo["controlId"], val, targetInfo.type);
                            }
                        }
                    } else {
                        console.log("【业务关联控件】对话框行返回信息里面不包含\"" + key + "\"的值!");
                    }
                }
            }
            ,

            // 给明细表的控件赋值
            // rtn : 弹出框选择的行信息，cfgs ：后台配置的信息
            fillTargetControlsValInDetail: function (rtn, cfgs, rawRtn) {
                //控件在明细表内
                if (this.isInDetail) {
                    // 数据来源为非明细表数据
                    for (var key in cfgs["sourceCommon"]) {
                        if (this.isInDetail) {
                            var targets = cfgs["sourceCommon"][key].target || [];
                            for (var i = 0; i < targets.length; i++) {
                                var targetInfo = targets[i];
                                // 只允许给同明细表的同行数据赋值
                                if (this.controlId.split(".")[0] === targetInfo.controlId.split(".")[0]) {
                                    var targetId = targetInfo["controlId"];
                                    targetId = targetId.replace(".", "." + this.detailIndex + ".");
                                    if (targetInfo.type.indexOf("Attachment") > -1) {
                                        // #97081  附件特殊处理-case 3.1  关联控件在明细表内：主表To明细
                                        this.setAttTargetDetail_inDetail(targetId, targetInfo.passiveModelName, rtn["fdId"]["value"], rtn[key]["value"])
                                        continue;
                                    }
                                    this._fillControlVal(targetId, rtn[key]["value"], targetInfo["type"]);
                                } else {
                                    console.log("【业务关联控件】不支持明细表内的业务关联控件给其他明细表赋值");
                                }
                            }
                        } else {
                            console.log("【业务关联控件】对话框行返回信息里面不包含\"" + key + "\"的值!");
                        }
                    }

                    // 数据来源为明细表数据
                    for (var key in cfgs["sourceDetails"]) {
                        if (rtn.hasOwnProperty(key)) {
                            var sourceDetails = cfgs["sourceDetails"][key];
                            for (var sourceControlId in sourceDetails) {
                                var targets = sourceDetails[sourceControlId].target || [];
                                for (var i = 0; i < targets.length; i++) {
                                    var targetInfo = targets[i];
                                    // 只允许给同明细表的同行数据赋值
                                    if (this.controlId.split(".")[0] === targetInfo.controlId.split(".")[0]) {
                                        var values = rtn[key]["value"];
                                        var val = "";
                                        // 如果填充的值是数组，则把数据合并，以;号分开
                                        if (values.length) {
                                            val = this._mergeDataInDetail(values, sourceControlId, targetInfo.type);
                                        }
                                        var targetId = targetInfo["controlId"];
                                        targetId = targetId.replace(".", "." + this.detailIndex + ".");
                                        if (targetInfo.type.indexOf("Attachment") > -1) {
                                            // #97081  附件特殊处理-case 4.1 关联控件在明细表内 ：明细To明细
                                            this.setAttTargetDetail_inDetail(targetId, targetInfo.passiveModelName, rtn["fdId"]["value"], val.split(";"))
                                            continue;
                                        }
                                        this._fillControlVal(targetId, val, targetInfo.type);
                                    } else {
                                        console.log("【业务关联控件】不支持明细表内的业务关联控件给其他明细表赋值");
                                    }
                                }
                            }
                        } else {
                            console.log("【业务关联控件】对话框行返回信息里面不包含\"" + key + "\"的值!");
                        }
                    }
                }else {
                    //明显表行填充模式
                    this.changeDetailsByFillType(cfgs);
                    // 数据来源为非明细表数据或者多行明细表数据,添加行,赋值
                    for (var i = 0; i < rawRtn.length; i++) {
                        var singleRtn = rawRtn[i];
                        if(!$.isEmptyObject(cfgs["sourceCommon"])&& !$.isEmptyObject(cfgs["sourceDetails"])){
                            //数据来源是主表和明细表结合
                            this.setDetailsValuesCommonAndDetail(cfgs,singleRtn);
                        }else if (!$.isEmptyObject(cfgs["sourceCommon"])){
                            //数据来源是主表
                            this.addDetailsTableRow(cfgs["sourceCommon"], singleRtn);
                        }else {
                            // 数据来源为明细表数据
                            for (var key in cfgs["sourceDetails"]) {
                                if (singleRtn.hasOwnProperty(key)) {
                                    this.setDetailsTableFieldValues(cfgs["sourceDetails"][key], singleRtn[key]["value"], singleRtn["fdId"]["value"]);
                                }
                            }
                        }
                    }
                }
            }
            ,

            //为非明细行合并数据
            margeDataForNonDetail: function (rowInfos) {
                // 把所有行的数据进行合并返回
                var rs = {};
                for (var i = 0; i < rowInfos.length; i++) {
                    var info = rowInfos[i];
                    for (var key in info) {
                        if(!info[key] || !info[key].type){
                            continue;
                        }
                        //取类型
                        var targetWgt = GetXFormFieldById(key);
                        if(targetWgt.length){
                            //取类型只需拿第一个就行
                            targetWgt = targetWgt[0];
                        }
                        var flagtype = $(targetWgt).closest("xformflag").attr("flagtype");
                        var isMix = false;
                        if(flagtype == "xform_radio" || flagtype == "xform_datetime"
                            || flagtype == "xform_select" || flagtype == "radio"
                            || flagtype == "select" || flagtype == "xform_relation_radio"
                            || flagtype == "xform_relation_select"){
                            //单值类型的控件不做合并，做覆盖处理
                            isMix = true;
                        }
                        if(flagtype == "xform_address"){
                            //地址本，判断多选还是单选
                            isMix = $(targetWgt).closest("xformflag").find("input[xform-type='newAddress']").attr("data-ismulti");
                        }
                        // 多行记录的明细表，把所有行都放置在一起
                        if (info[key].type === "detail") {
                            if (!rs.hasOwnProperty(key)) {
                                rs[key] = {value: [], type: info[key].type};
                            }
                            if(isMix){
                                rs[key].value = info[key].value;
                            }else{
                                rs[key].value = rs[key].value.concat(info[key].value);
                            }
                        } else if (info[key].type.indexOf("com.landray.kmss.sys.organization") > -1) {
                            // 地址本
                            var hasPre = true;
                            var val = info[key].value || {};
                            if (!rs.hasOwnProperty(key)) {
                                rs[key] = {value: {id: "", name: ""}, type: info[key].type};
                                hasPre = false;
                            }
                            if(isMix){
                                //单选地址本做覆盖
                                rs[key].value.id = val.id || "";
                                rs[key].value.name = val.name || "";
                            }else{
                                rs[key].value.id += ";" + (val.id || "");
                                rs[key].value.name += ";" + (val.name || "");
                                if (!hasPre) {
                                    rs[key].value.id = rs[key].value.id.substring(1);
                                    rs[key].value.name = rs[key].value.name.substring(1);
                                }
                            }
                        } else {
                            var hasPre = true;
                            if (!rs.hasOwnProperty(key)) {
                                rs[key] = {value: "", type: info[key].type};
                                hasPre = false;
                            }
                            var val = info[key].value || this.defalutNull;
                            if (val === undefined && info[key].value !== undefined) {
                                val = info[key].value;
                            }
                            // 由于返回的类型没法区分是否是是枚举类型，只能通过值的类型来区分
                            if (typeof (val) === "object" && val.hasOwnProperty("value")) {
                                val = val["value"];
                            }
                            if(isMix){
                                rs[key].value = val;
                            }else{
                                rs[key].value += ";" + val;
                                if (!hasPre) {
                                    rs[key].value = rs[key].value.substring(1);
                                }
                            }
                        }
                    }
                }
                return rs;
            }
            ,

            // 合并明细表多行的数据
            _mergeDataInDetail: function (valueArr, sourceId, targetType,isMix) {
                var val = null;
                for (var i = 0; i < valueArr.length; i++) {
                    var valueInfo = valueArr[i];
                    if (valueInfo.hasOwnProperty(sourceId)) {
                        var sourceVal = valueInfo[sourceId];
                        if (targetType.indexOf("com.landray.kmss.sys.organization") > -1) {
                            // 地址本
                            val = val || {};
                            if ($.isEmptyObject(val)) {
                                val = {id: "", name: ""};
                            }
                            //#138623 明细表地址本可能前几行为空值，拼接时不应该将逗号进行拼接
                            if(val.id && val.id.length > 0){
                                if(isMix){
                                    //#153537 单选地址本不做合并，做覆盖
                                    val.id = (sourceVal.id || "");
                                    val.name = (sourceVal.name || "");
                                }else{
                                    val.id += ";" + (sourceVal.id || "");
                                    val.name += ";" + (sourceVal.name || "");
                                }
                            }else{
                                val.id = (sourceVal.id || "");
                                val.name = (sourceVal.name || "");
                            }
                        } else {
                            var hasPre = true;
                            val = val || "";
                            if (!val) {
                                val = "";
                                hasPre = false;
                            }
                            var tempVal = sourceVal || "";
                            if (typeof (tempVal) === "object" && tempVal.hasOwnProperty("value")) {
                                tempVal = tempVal["value"];
                            }
                            if(isMix){
                                //单值类型控件，不做合并，做覆盖处理
                                val = tempVal;
                            }else{
                                val += ";" + tempVal;
                            }
                            if (!hasPre && !isMix) {
                                val = val.substring(1);
                            }
                        }
                    }
                }
                return val;
            }
            ,

            _fillControlVal: function (controlId, value, type) {
                if (type.indexOf("com.landray.kmss.sys.organization") > -1) {
                    // 目标是地址本
                    var id = "";
                    var name = "";
                    if (typeof (value) === "object") {
                        id = value.id;
                        name = value.name;
                    } else {
                        id = name = value;
                    }
                    $form(controlId + ".id").val(id);
                    $form(controlId + ".name").val(name);
                    if (typeof __xformDispatch != "undefined") {
                        if ($form(controlId + ".id").target.element) {
                            var changeVals = [id, name];
                            var idEle = $form(controlId + ".id").target.element[0];
                            var nameEle = $form(controlId + ".name").target.element[0];
                            var changeEles = [idEle, nameEle];
                            __xformDispatch(changeVals, changeEles);
                        }
                    }
                    if ($form(controlId + ".name").target.element) {
                        var nameEle = $form(controlId + ".name").target.element[0];
                        if (nameEle.type === "hidden" && nameEle.name && nameEle.name.indexOf(".name") > 0) {
                            $($form(controlId + ".name").target.element[0]).parent().find(".addressName").text(nameEle.value);
                        }
                    }
                    //#171610 【日常缺陷】【项目反馈】【低代码平台-修复】地址本隐藏以后，关联控件显示值受影响 未做非空判断
                    if($form(controlId + ".id").target.element){
                        $($form(controlId + ".id").target.element[0]).trigger("change");
                    }
                } else {
                    var controlVal = value;
                    //普通字段
                    if (typeof (controlVal) === "object" && controlVal.hasOwnProperty("value")) {
                        controlVal = controlVal.value;
                        //枚举有更深的一层(由于返回的类型没法区分是否是是枚举类型，只能通过值的类型来区分)
                        if (typeof (controlVal) === "object" && controlVal.hasOwnProperty("value")) {
                            controlVal = controlVal.value;
                        }
                    }
                    SetXFormFieldValueById_ext(controlId, controlVal);
                    if (typeof __xformDispatch != "undefined") {
                        var wgtObj  = GetXFormFieldById_ext(controlId, true);
                        if(wgtObj.length != 0){
                            __xformDispatch(controlVal, wgtObj);
                        }
                    }
                    if ($form(controlId).target && $form(controlId).target.element && $form(controlId).target.element.length > 0) {
                        $($form(controlId).target.element[0]).trigger("change");
                    }
                }
            }
            ,
        // 明显表行填充模式
        changeDetailsByFillType :function (cfgs){
            // 1|覆盖行数据（清空+追加）
            if (this.getCfgInfo().hasOwnProperty('outExtend') && this.getCfgInfo().outExtend === '1') {
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
                    var tableIds = [];
                    //#114624
                      for (var sourceId in sources) {
                        var tableId = "TABLE_DL_" + sourceId;
                        $('#' + tableId + " > tbody > tr:not([type])").each(function () {
                            DocList_DeleteRow_ClearLast(this);
                        });
                    }

                      for (var sourceId in sources) {
                     	var targetInfos = sources[sourceId]["target"];
                     	// 一行数据有可能传出到多个目标控件
                     	for (var j = 0;j < targetInfos.length;j++) {
                     		var targetId = targetInfos[j]["controlId"];
                     		var targetIdArr = targetId.split(".");
                     		// 仅支持“明细表id.控件id”结构
                     		if (targetIdArr.length === 2) {
                     			var tableId = "TABLE_DL_" + targetIdArr[0];
                     			if ($.inArray(tableId, tableIds) === -1) {
                     				tableIds.push(tableId);
                     				$('#' + tableId).find("tbody > tr:not([type])").each(function () {
                     					DocList_DeleteRow_ClearLast(this);
                     				});
                     			}
                     		}
                     	}
                     }
                }
            ,

            // 源明细表给目标控件填充值
            setDetailsTableFieldValues: function (sources, values, fdId) {
                for (var i = 0; i < values.length; i++) {
                    this.addDetailsTableRow(sources, values[i], fdId);
                }
            }
            ,
         //源明细表和主表混合给目标明细表控件赋值
         setDetailsValuesCommonAndDetail: function (cfgs,values){
            //把源数据是主表字段的值单独取出来，后面会每个明细表的每一行的值单独和主表字段的值合并，结合成一条新的明细行
            var sourceCommonValues = {};
            for (var sourceId in cfgs["sourceCommon"]) {
                if (values.hasOwnProperty(sourceId)){
                    sourceCommonValues[sourceId] = values[sourceId];
                }
            }
            // 数据来源为明细表数据
            //循环每个明细表
            for (var key in cfgs["sourceDetails"]) {
                if (values.hasOwnProperty(key)) {
                    var cfgTotal = {};
                    var valuesTotal = {};
                    var sourceDetails = cfgs["sourceDetails"][key];
                    //多明细表情况下，每个明细表字段和主表字段结合赋值，明细表之间没有关联，定义空对象，使用两次合并对象就是避免数据乱套
                    Object.assign(cfgTotal,cfgs["sourceCommon"]);
                    Object.assign(cfgTotal,sourceDetails);
                    var detailValues =  values[key]["value"];
                    //获取明细表的每一行数据
                    for (var i = 0; i < detailValues.length; i++) {
                        Object.assign(valuesTotal,sourceCommonValues);
                        Object.assign(valuesTotal,detailValues[i]);
                        this.addDetailsTableRow(cfgTotal, valuesTotal, values["fdId"]["value"]);
                    }
                }
            }

        },

            addDetailsTableRow: function (sources, rowValues, rowFdId) {
                var fieldsVal = {};
                // 遍历每一行需要输出控件配置信息
                for (var sourceId in sources) {
                    var targetInfos = sources[sourceId]["target"];
                    // 一行数据有可能传出到多个目标控件
                    for (var j = 0; j < targetInfos.length; j++) {
                        var targetInfo = targetInfos[j];
                        var value = rowValues;
                        // 明细表外的控件给明细表内的控件赋值时，才有sourceId
                        if (typeof (value) === "object") {
                            if (value.hasOwnProperty(sourceId)) {
                                value = value[sourceId];
                            } else {
                                value = "";
                                console.log("【业务关联控件】对话框行返回信息里面不包含输出控件ID\"" + sourceId + "\"的值!");
                            }
                        }
                        var targetId = targetInfo["controlId"];
                        var targetIdArr = targetId.split(".");
                        // 仅支持“明细表id.控件id”结构
                        if (targetIdArr.length === 2) {
                            var tableId = "TABLE_DL_" + targetIdArr[0];
                            if(!fieldsVal[tableId]){
                                fieldsVal[tableId] = {};
                            }
                            if (value) {
                                if (targetInfo["type"].indexOf("Attachment") > -1) {
                                    //附件后面特殊处理
                                    continue;
                                } else if (targetInfo["type"].indexOf("com.landray.kmss.sys.organization") > -1) {
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
                                    } else {
                                        id = name = value;
                                        console.log("【业务关联控件】目标控件ID(" + targetIdArr[1] + ")是地址本，但由于填充的值只有一个，故只能id和name都填充同样的值!");
                                    }
                                    fieldsVal[tableId]["extendDataFormInfo.value(" + targetIdArr[0] + ".!{index}." + targetIdArr[1] + ".id)"] = id;
                                    fieldsVal[tableId]["extendDataFormInfo.value(" + targetIdArr[0] + ".!{index}." + targetIdArr[1] + ".name)"] = name;
                                } else {
                                    var controlVal = value;
                                    //普通字段
                                    if (typeof (controlVal) === "object" && controlVal.hasOwnProperty("value")) {
                                        controlVal = controlVal.value;
                                        //枚举有更深的一层(由于返回的类型没法区分是否是是枚举类型，只能通过值的类型来区分)
                                        if (typeof (controlVal) === "object" && controlVal.hasOwnProperty("value")) {
                                            controlVal = controlVal.value;
                                        }
                                    }
                                    fieldsVal[tableId]["extendDataFormInfo.value(" + targetIdArr[0] + ".!{index}." + targetIdArr[1] + ")"] = controlVal;
                                }
                            } else {
                                console.log("【业务关联控件】对话框行返回信息里面不包含输出控件ID\"" + targetId + "\"的值!");
                            }
                        } else {
                            console.log("【业务关联控件】输出控件ID(" + targetId + ")结构异常，不符合\"明细表ID.控件ID\"结构！");
                        }
                    }
                }

                if (fieldsVal) {
                    for (var tableId in fieldsVal) {
                        // 这里有可能是新增一行空的一行，看后续需求是否需要做为空判断
                        var row = DocList_AddRow(tableId, null, fieldsVal[tableId]);
                        // #97081  附件特殊处理-case 5（4.2+3.2）  关联控件在明细表外 ：目标为明细
                        this.setAttTargetDetail_addRow(tableId, sources, rowFdId, rowValues, row)
                        this.doExtendOpera(row, fieldsVal[tableId],tableId);
                    }
                }
            }
            ,
            getRowLastIndex: function (tableId) {
                var rowIndx = null;
                var optTB = document.getElementById(tableId);
                var tbInfo = DocList_TableInfo[optTB.id];
                return tbInfo.lastIndex
            },
            getRowIndex: function (row,tableId) {
                var optTB = document.getElementById(tableId);
                var tbInfo = DocList_TableInfo[optTB.id];
                var rowIndex = Com_ArrayGetIndex(optTB.rows, row);
                rowIndex = rowIndex - tbInfo.firstIndex;
                return rowIndex;
            },
            doExtendOpera: function (row, fieldsVal,tableId) {
                //获取新增行的rowIndex
                var rowIndex = this.getRowIndex(row,tableId);
                for (var fieldName in fieldsVal) {
                    if (/\.(\w+)\)/g.test(fieldName)) {
                        var group = /\.(\w+)\)/g.exec(fieldName);
                        if (group && group.length > 1) {
                            var $xformFlag = $(row).find("xformflag[flagid*='" + group[1] + "']");
                            if (extraDealControl.indexOf($xformFlag.attr("flagtype"))>-1) {
                                XForm_ExtraDealControl($xformFlag, fieldsVal[fieldName]);
                            }
                            //#170407 触发明细表字段值改变事件
                            if (typeof __xformDispatch != "undefined") {
                                var fieldCode = DocListFunc_ReplaceIndex(fieldName, rowIndex);
                                var wgtObj  = document.getElementsByName(fieldCode);
                                if(wgtObj.length != 0){
                                    __xformDispatch(fieldsVal[fieldName], wgtObj);
                                }
                            }
                        }
                    }
                }
            }
            ,

            setCurrentControlVal: function (values, cfg) {
                var text = [];
                var value = [];
                var expression = cfg["showTxt"]["expression"];
                if (values && values.length) {
                    for (var i = 0; i < values.length; i++) {
                        var info = values[i];
                        text.push(this.transValByExp(info, expression));
                        value.push(info[this.CONST.RECORDID]["value"]);
                    }
                }
                var valStr = text.join(";") || "";
                if (valStr == "") {
                    dialog.alert("[温馨提示]当前返回值为空，保存后不能进行字段穿透");
                }
                this.setValue(text.join("&&"), value.join(";"));
            }
            ,

            setValue: function (text, value, isInit) {
                this.txtShowNode.html(text);
                this.textNode.val(text);
                //#171144 【服务问题单】业务建模中数据填充控件的业务触发控件和传入参数都选择业务关联控件时，无法及时进行传参，需要再手动选择
                this.inputNode.val(value);
                if (!isInit) {
                    this.textNode.trigger("change"); // fire the change event
                }
                if (!isInit) {
                    this.inputNode.trigger("change"); // fire the change event
                }
                this.__setShowText();
            }
            ,

            __setShowText: function () {
                this.txtShowNode.html("");
                var valStr = this.textNode.val() || "";
                var textArr = valStr.split("&&");
                var valArr = (this.inputNode.val() || "").split(";");
                var html = [];
                html.push("<ol class='mf_list'>")
                for (var i = 0; i < valArr.length; i++) {
                    //#167406 防止html代码注入
                    var $li = $("<li  class='mf_item' __id='" + valArr[i] + "'>");
                    $li.text(textArr[i]).append("<a style='margin-left: 5px' href='#' class='mf_remove'>X</a>");
                    html.push($li[0].outerHTML);
                }
                html.push("</ol>");
                this.txtShowNode.append(html.join(""));
                var self = this;

                this.txtShowNode.find("li.mf_item").mouseover(function () {
                    $(this).addClass("mf_highlighted");
                }).mouseout(function () {
                    $(this).removeClass("mf_highlighted");
                });

                this.txtShowNode.find(".mf_remove").click(function () {
                    var lastRawRtn = self.lastRawRtn;
                    var cfg = self.getCfgInfo();
                    var outputCfgs = cfg["outputs"];

                    var id = $(this).closest("li").attr("__id");
                    $(this).closest("li").remove();

                    var textArr = (self.textNode.val() || "").split(";");
                    var valArr = (self.inputNode.val() || "").split(";");
                    var newVal = [];
                    var newText = [];
                    var rawRtn = [];
                    for (var i = 0; i < valArr.length; i++) {
                        if (id !== valArr[i]) {
                            newVal.push(valArr[i]);
                            newText.push(textArr[i]);
                            if (lastRawRtn && lastRawRtn.length > i)
                                rawRtn.push(lastRawRtn[i]);
                        }
                    }
                    self.textNode.val(newText.join(";"));
                    self.inputNode.val(newVal.join(";"));
                    if (lastRawRtn && lastRawRtn.length > 0) {
                        //使为空时全部置空
                        if (rawRtn.length < 1)
                            rawRtn = self.setEmptyRawRtn(lastRawRtn, outputCfgs["fields"]);
                        //为非明细行合并数据
                        var rtn = self.margeDataForNonDetail(rawRtn);
                        // 目标控件为非明细表
                        self.fillTargetControlsVal(rtn, outputCfgs["fields"]);
                        self.lastRawRtn = rawRtn;
                    }
                });

            }
            ,

            setEmptyRawRtn: function (lastRawRtn, cfgs) {
                if (!lastRawRtn || lastRawRtn.length < 1)
                    return [];
                var rawRtnEmpty = lastRawRtn[0];

                function setEmptyRawRtnValue(sources) {
                    for (var key in sources) {
                        if (rawRtnEmpty[key].type.indexOf("com.landray.kmss.sys.organization") > -1) {
                            rawRtnEmpty[key].value.id = "";
                            rawRtnEmpty[key].value.name = "";
                        } else if (typeof (rawRtnEmpty[key].value) === 'object') {
                            rawRtnEmpty[key].value.value = "";
                        } else {
                            rawRtnEmpty[key].value = "";
                        }
                    }
                }

                setEmptyRawRtnValue(cfgs["sourceCommon"]);
                setEmptyRawRtnValue(cfgs["sourceDetails"]);
                return [rawRtnEmpty];
            },
            /*************** #97081 业务传出支持附件 start******************/
            //--------------------
            /**
             * #126178 通过业务关系设置携带的图片后，再上传图片，
             * @param controlId 控件Id
             * @private
             */
            _clearAttValue: function (controlId) {
                console.debug("_clearAttValue controlId==", controlId);
                var swfobj;
                if (top.window)
                    swfobj = top.window.Attachment_ObjectInfo[controlId];
                else
                    swfobj = window.Attachment_ObjectInfo[controlId];
                console.debug("swfobj==", swfobj);
                //删除成功后，会从swfobj 的fileList中将其剔除，在外部遍历注意调整循环变量，避免遍历不完整
                var deleteAttMain = []
                if(swfobj.fileList){
                    for (var i = 0; i < swfobj.fileList.length; i++) {
                        deleteAttMain.push(swfobj.fileList[i])
                    }
                    for (var i = 0; i < deleteAttMain.length; i++) {
                        console.debug("del ==", deleteAttMain[i].fdId);
                        swfobj.deleteAttMainInfo(deleteAttMain[i]);
                    }
                    //#171228 删除附件后重新渲染附件控件
                    swfobj.showed = false;
                    swfobj.initMode = false;
                    swfobj.btnIntial = false;
                    swfobj.show();
                }else{
                    if (window.console){
                        console.info("业务传出附件_clearAttValue方法中window.Attachment_ObjectInfo不存在对应控件：", swfobj);
                    }
                }
            },
            /**
             * 设置界面上的附件值
             * @param data 服务器返回对象
             * @private
             */
            _setAttValue: function (data, callBackParam) {
                var attArr = data;
                for (var k = 0; k < attArr.length; k++) {
                    var attItem = attArr[k];
                    var newKey = attItem["newKey"];
                    var swfobj;
                    if (top.window)
                        swfobj = top.window.Attachment_ObjectInfo[newKey];
                    else
                        swfobj = window.Attachment_ObjectInfo[newKey];
                    swfobj.addDoc(attItem["fileName"], attItem["fdId"], true, attItem["fileType"], attItem["fileSize"], attItem["fileKey"], 0, true);
                    swfobj.showed = false;
                    swfobj.btnIntial = false;
                    swfobj.initMode = true;
                    swfobj.canPrint = false;
                    swfobj.canDownload = false;
                    swfobj.canRead = true;
                    swfobj.canCopy = false;
                    swfobj.canEdit = false;
                    if (!callBackParam || !callBackParam.noShow) {
                        //#164400 明细表附件上传成功后不显示，需要将initMode设置为true
                        swfobj.initMode = true;
                        //#169967 如果initMode设置为true,showed为false,并调用show方法，需要将附件控件的事件先off,否则会监听多次事件
                        swfobj.off();
                        //#164400 业务关联多行数据回填明细表，附件重复显示，去掉延迟，由附件机制判断
                        swfobj.show();
                    }
                    var attIds = "";
                    for (var i = 0; i < swfobj.fileList.length; i++) {
                        if (attIds == "") {
                            attIds += swfobj.fileList[i].fdId;
                        } else {
                            attIds += ";" + swfobj.fileList[i].fdId;
                        }
                    }
                    console.debug("_setAttValue:id:", attIds)
                    var $ele = $("[name='attachmentForms." + newKey + ".attachmentIds']");
                    if ($ele.length > 0) {
                        $($ele[0]).val(attIds)
                    }
                }
            },
            /**
             * 执行附件设置
             * @param fdModelId
             * @param fdModelName
             * @param newKey
             * @param oldKeys
             */
            _setAtt: function (fdModelId, fdModelName, newKey, oldKeys, callBackParam) {
                if (!fdModelId || !fdModelName || !newKey) {
                    console.info("_setAtt 参数不能为空 fdModelId, fdModelName, newKey：", fdModelId, fdModelName, newKey)
                    return
                }
                var attArray = [];
                for (var i = 0; i < oldKeys.length; i++) {
                    attArray.push({
                        "exsitAttIds": "",
                        "newKey": newKey,
                        "fdModelId": fdModelId,
                        "fdModelName": fdModelName,
                        "oldKey": oldKeys[i]
                    });
                }
                //需要先清空原来的数据
                this._clearAttValue(newKey);
                console.debug("_setAtt::", attArray)
                relationUtil.getAttachmentClone(attArray, this._setAttValue, callBackParam);
            },
            /**
             * 目标是主表
             * @param targetInfo
             * @param fdModelId
             * @param value
             */
            setAttTargetMain: function (targetInfo, fdModelId, value) {
                console.debug("setAttTargetMain::", targetInfo, fdModelId, value)
                if (!value) {
                    return;
                }
                var oldKeys = (typeof (value) === "object" && value.length) ? value : [value];
                var newKey = targetInfo.controlId;
                var fdModelName = targetInfo.passiveModelName;
                this._setAtt(fdModelId, fdModelName, newKey, oldKeys);
            },
            /**
             * 设置
             * @param tableId
             * @param sources
             * @param rowValues
             * @param row
             */
            setAttTargetDetail_addRow: function (tableId, sources, rowFdId, rowValues, row) {
                console.debug("setAttTargetDetail_addRow::", tableId, sources, rowValues, row)
                var rowIdx = this.getRowLastIndex(tableId);
                if (rowIdx > 0) {
                    rowIdx -= 2;
                }
                for (var sourceId in sources) {
                    var targetInfos = sources[sourceId]["target"];
                    // 一行数据有可能传出到多个目标控件
                    for (var j = 0; j < targetInfos.length; j++) {
                        var targetInfo = targetInfos[j];
                        var rowValue = rowValues;
                        if (targetInfo["type"].indexOf("Attachment") > -1) {
                            // 明细表外的控件给明细表内的控件赋值时，才有sourceId
                            if (typeof (rowValues) === "object") {
                                if (rowValues.hasOwnProperty("fdId") && !rowFdId) {
                                    //主表传值
                                    rowFdId = rowValues["fdId"]["value"];
                                }
                                if (rowValues.hasOwnProperty(sourceId)) {
                                    rowValue = rowValues[sourceId];
                                    if (!rowValue){
                                        rowValue = "";
                                    }
                                    if (rowValue.hasOwnProperty("value")) {
                                        rowValue = rowValue["value"];
                                    }
                                } else {
                                    rowValues = "";
                                    console.log("【业务关联控件】对话框行返回信息里面不包含输出控件ID\"" + sourceId + "\"的值!");
                                }
                            }
                            // console.log("sourceId::",sourceId," targetInfo",targetInfo,"  rowValues",rowValue," rowValues" )
                            this.setAttTargetDetail(targetInfo, rowFdId, rowValue, row, rowIdx)
                            continue;
                        }
                    }
                }
            },
            /**
             * 设置目标为明细表
             * @param tableId
             * @param sources
             * @param value
             * @param row
             * @param rowIndex
             */
            setAttTargetDetail: function (targetInfo, fdModelId, value, row, rowIndex) {
                console.debug("setAttTargetDetail::", targetInfo, fdModelId, value, row, rowIndex)
                if (typeof (value) === "object" && value.length) {
                    return;
                }
                var oldKeys = [value];
                var newKey = "";
                //获取新增行的附件key
                var targetControlId = targetInfo["controlId"];
                var targetIdArr = targetControlId.split(".");
                if (targetIdArr && targetIdArr.length > 1) {
                    var targetControlId = targetIdArr[0] + "." + rowIndex + "." + targetIdArr[1];
                    var $xformFlag = $(row).find("xformflag[flagid$='" + targetIdArr[1] + "']");
                    var $xformAtt = $xformFlag.find("input[name='extendDataFormInfo.value(" + targetControlId + ")']");
                    newKey = $xformAtt.val();
                }
                var fdModelName = targetInfo.passiveModelName;
                this._setAtt(fdModelId, fdModelName, newKey, oldKeys, {"noShow": true});
            },
            /**
             * 设置目标为明细表（明细表内关联控件）
             * @param targetControlId
             * @param fdModelName
             * @param fdModelId
             * @param value
             */
            setAttTargetDetail_inDetail: function (targetControlId, fdModelName, fdModelId, value) {
                console.debug("setAttTargetDetail_inDetail::", targetControlId, fdModelName, fdModelId, value)
                var oldKeys = [value];
                if (typeof (value) === "object" && value.length) {
                    //接受明细表传值
                    oldKeys = value;
                }

                var newKey = "";
                var $xformAtt = $("input[name='extendDataFormInfo.value(" + targetControlId + ")']");
                newKey = $xformAtt.val();
                this._setAtt(fdModelId, fdModelName, newKey, oldKeys, {"noShow": false});
            },

            /*************** #97081 业务传出支持附件 end******************/
            updateTextView: function () {
                this.txtShowNode.html(this.textNode.val());
            }

        })
    ;

    // 多选选择框
    var MultiDialogPlaceholder = DialogPlaceholder.extend({

        dialogUrl: "/sys/modeling/main/xform/controls/placeholder/dialog/dialog.jsp?multi=true",

    });

    exports.DialogPlaceholder = DialogPlaceholder;
    exports.MultiDialogPlaceholder = MultiDialogPlaceholder;

})