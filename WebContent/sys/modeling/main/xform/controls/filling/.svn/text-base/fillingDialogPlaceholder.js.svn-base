/**
 * 业务填充
 */
define(function (require, exports, module) {
    var $ = require("lui/jquery");
    var base = require("lui/base");
    var dialog = require('lui/dialog');
    var source = require("lui/data/source");
    var topic = require("lui/topic");
    var dialogPlaceholderWgt = require("sys/modeling/main/xform/controls/placeholder/dialogPlaceholderWgt");
    var relationUtil = require("sys/modeling/main/xform/controls/placeholder/relationUtil");

    /**************************业务填充单选*****************************/
    var FillingDialogPlaceholder = dialogPlaceholderWgt.DialogPlaceholder.extend({
        dialogUrl: "/sys/modeling/main/xform/controls/filling/dialog.jsp?multi=false",
        cfgInfo: {},                         //当前关系的配置信息
        targetOnlyContainDetail: false,     //传出参数目标全是明细表字段
        targetIndex: 0,                    //当前目标的下标
        isSingle : false,
        _currentTriggerNotVal : false,
        _hasSetValue:false,                 //是否有回填值

        initProps : function($super,cfg) {
            $super(cfg);
            var topicId = this.parent.config.controlId;
            var controlIdWithIndex = this.parent.controlIdWithIndex;
            if(topicId.indexOf(".") > -1){
                topicId = topicId.substr(topicId.lastIndexOf(".")+1);
            }
            var index;
            if (controlIdWithIndex && controlIdWithIndex.indexOf(".") > -1) {
                //controlIdWithIndex格式：xx.index.xx
                index = controlIdWithIndex.split(".")[1];
            }
            if(index){
                topicId = topicId +"."+index;
                var bindDom = this.parent.config.bindDom;
                var bindDomArr = bindDom.split(".");
                this.parent.config.bindDom = bindDomArr[0]+"."+index+"."+bindDomArr[1];
            }
            topic.channel(topicId).unsubscribe("modeling.filling.change"+topicId);
            topic.channel(topicId).subscribe("modeling.filling.change"+topicId,this.execFillingChange,this);
        },
        execFillingChange:function(arg1){
            var obj = arg1.obj;
            var evenObj = arg1.evenObj;
            var button = evenObj.closest("xformflag").find(".lui-placeholder-button");
            //正在填充时,不需要再次触发
            //#170142 isAfterSelect：是否是回填字段触发值改变事件，false则需要触发业务填充，true则不触发业务填充
            if ($(obj).closest("xformflag").attr("onfilling") != "true" && isAfterSelect === false) {
                this._currentTriggerNotVal =  $(obj).val() == ""? true : false;
                if(button){
                    this.execFilling();
                }
                this._currentTriggerNotVal = false;
                isAfterSelect = false;
            }
        },
        //业务填充值改变事件，单个控件触发
        execFilling: function (clear,isSingle, flagid) {
            if(clear){
                this._currentTriggerNotVal = clear;
            }
            _modelingFillingNum++;     //触发控件中绑定的填充控件数量，全局变量，控制什么时候弹框
            var self = this;
            self.isSingle = isSingle;
            if(flagid && flagid.indexOf(".") > -1){
                //明细表的话把id的行数过滤掉
                flagid = XForm_FormatControlIdWithNoIndex(flagid);
            }
            //根据表单id和当前触发的填充控件的id，拿到当前触发控件的填充关系，可能多条
            if(self.parent.config){
                $.ajax({
                    url: Com_Parameter.ContextPath + "sys/modeling/main/modelingAppXFormMain.do?method=executeQueryFilling&fdAppModelId=" + self.parent.config.fdModelId + "&widgetId=" + self.parent.config.controlId,
                    type: 'post',
                    async: false,
                    success: function (json) {
                        self.fillingDispatcher(json, isSingle, flagid)
                    },
                    error: function () {
                        if(window.console){
                            console.warn("获取业务填充控件（"+self.parent.config.controlId+"）的业务关系错误，请检查")
                        }
                    }
                });
            }
        },
        fillingDispatcher: function (resultJson, isSingle, flagid) {
            var bindDom = this.parent.config.bindDom;
            var controlId = this.parent.config.controlId;
            var self = this;
            var resultInfo = JSON.parse(resultJson); //当前触发的填充控件的所有填充关系信息
            var autoCount = 0;//自动填充的控件数
            for (var i = 0; i < resultInfo.length; i++) {
                var resultCfg = resultInfo[i].cfgInfo;
                //检查目标是否全是明细表
                self.targetOnlyContainDetail = self.checkTargetDetail(resultCfg);
                //转换当前填充控件的业务关系的配置信息，包括传出参数等，转换为前端需要的数据格式
                var cfgInfo = relationUtil.formatFillingData(resultCfg);
                //拿转换格式后的数据进行判断，是否来源全是主表，若是，则在只填充明细表的时候，一条主表对应一行明细表
                self.sourceOnlyContainMain = self.checkSourceNotDetail(resultCfg);
                var relationId = resultInfo[i].relationId;  //当前填充控件的业务关系的modelId
                _modelingFillingCfgInfo[relationId] = {     //_modelingFillingCfgInfo全局变量，存储当前关系的信息
                    "widgetId": controlId,
                    "cfgInfo": cfgInfo,
                    "formDatas": self.findFormValueByCfg(self.getCfgInfo()),
                    "fdAppModelId": self.parent.config.fdModelId,
                    "currentWgt": self,
                    "resultInfo": resultInfo[i]
                }
                if (resultInfo[i].page.totalSize == 0) {
                    if (window.console) {
                        console.warn("业务填充控件（" + controlId + "）触发完未找到记录进行填充，检查后台设置的查询条件");
                    }
                    continue;
                }
                var formData = this.findFormValueByCfg(resultCfg);
                var widgetData = this.checkIncDataOne(formData);
                var incOnlyOne = false;
                var index = widgetData.length === resultInfo.length ? i : 0;
                if(widgetData.length == 1 && widgetData[index].page.totalSize == "1") {
                    incOnlyOne =  true;
                }
                // 以明细表为查询主体，填充主表，即使主表是一条明细表多条，此属于配置问题，也要弹框处理
                if(this._currentTriggerNotVal){
                    //#165914 有执行过回填，才清空回填字段值
                    if(this._hasSetValue){
                        //触发控件值为空，清空
                        self.cfgInfo = cfgInfo;
                        self.setValueAutoFilling(widgetData[index]);
                        //清空之后，重新设置为默认值
                        this._hasSetValue = false;
                    }
                    autoCount++;
                }else if (widgetData[index].page.totalSize == 1 || self.targetOnlyContainDetail || incOnlyOne) {
                    self.cfgInfo = cfgInfo;
                    if (widgetData[index].page.totalSize == 1) {
                        //当前关系中查找到的记录是一条，自动填充
                        self.setValueAutoFilling(widgetData[index]);
                        autoCount++;
                    } else {
                        //或者目标字段中全部都是明细表字段，自动填充多条明细表
                        //这里pageSize是明细表的数量，但是填充的时候是根据主表的数量来循环填充，因为后面填充的时候明细表是循环填充的
                        var cols = widgetData[index].columns;
                        //#165980 回填明细表，将多行数据合并一起回填
                        self.setDetailValueAutoFilling(widgetData[index],cols[0].data);
                        autoCount++;
                        self.targetIndex = 0;
                    }
                }else{
                    //当前关系中查找到的记录是多条，预弹框处理
                    // （多条关系中，有一条需要弹框都要打开弹框，弹框中列出所有的关系，即使已经自动填充过的也列出来）
                    _openDialogFlag = true;
                }
                isAfterSelect = false;
            }
            //如果所有的触发控件都自动填充了，则初始化控件数
            if(autoCount === resultInfo.length){
                _modelingFillingNum = 0;
            }
            //_modelingFillingMap[bindDom].length拿到当前触发的控件绑定的填充控件数量，
            //_modelingFillingNum 已经触发的填充控件数量记录，若两个相等，则表示当前的触发控件已经触发到最后一个控件，可以打开弹框了。
            if (!this._currentTriggerNotVal && _openDialogFlag && (_modelingFillingMap[bindDom].length == _modelingFillingNum /*|| isSingle*/)) { //isSingle是手动触发填充控件的标志
                //弹出弹框
                self.targetIndex = 0;       //传出参数中目标选择的全是明细表再弹出弹框之前就已经填充了，此处需要重置明细表直接填充的下标
                self.openDialog(flagid);   //flagid,当前手动触发的控件的id
            }
        },
        checkIncDataOne : function(formData) {
            var self = this;
            var widgetData = [];
            if(!formData){
                return widgetData;
            }
            var ins = encodeURI(JSON.stringify(formData));
            $.ajax({
                url: Com_Parameter.ContextPath + "sys/modeling/main/modelingAppXFormMain.do?method=executeQueryFilling&fdAppModelId=" + self.parent.config.fdModelId + "&widgetId=" + self.parent.config.controlId+"&ins="+ins,
                type: 'post',
                async: false,
                success: function (json) {
                    if(json != "" && json != "{}"){
                        var dataJson = JSON.parse(json);
                        widgetData = dataJson;
                    }
                },
                error: function () {
                    if(window.console){
                        console.warn("获取业务填充控件（"+self.parent.config.controlId+"）的业务关系数据错误，请检查")
                    }
                    isAfterSelect = false;
                }
            });
            return widgetData;
        },
        addFillingFlagOnDom: function (){
            var bindDom = this.parent.config.bindDom;
            if (/-fd(\w+)/g.test(bindDom)) {
                // 控件id不会含有-的，只要有-证明是手动添加上去的
                bindDom = bindDom.substring(0,bindDom.indexOf("-fd"));
            }
            $("[flagid='"+bindDom+"']").attr("onFilling","true");
            isAfterSelect = true;
        },
        removeFillingFlagOnDom : function(){
            var bindDom = this.parent.config.bindDom;
            if (/-fd(\w+)/g.test(bindDom)) {
                // 控件id不会含有-的，只要有-证明是手动添加上去的
                bindDom = bindDom.substring(0,bindDom.indexOf("-fd"));
            }
            $("[flagid='"+bindDom+"']").attr("onFilling","false");
            isAfterSelect = false;
        },
        checkTargetDetail: function (resultCfg) {
            var targetOnlyContainDetail = true;
            var output = resultCfg.outputs;
            var fields = output.fields;
            for (var f in fields) {
                var targetArr = fields[f].target;
                for (var j = 0; j < targetArr.length; j++) {
                    if (targetArr[j].controlId.indexOf(".") <= -1) {
                        //目标字段中有主表字段
                        targetOnlyContainDetail = false;
                        break;
                    }
                }
            }
            return targetOnlyContainDetail;
        },
        checkSourceNotDetail: function (resultCfg) {
            var output = resultCfg.outputs;
            var fields = output.fields;     //输出到主表
            var details = output.details;   //输出到明细表
            if (JSON.stringify(fields.sourceDetails) == "{}" && JSON.stringify(details.sourceDetails) == "{}") {
                return true;
            }
            return false;
        },
        setValueAutoFilling: function (resultInfo, index) {
            var rtnObj = {};
            if (!index) {
                index = 0;
            }
            var cols = resultInfo.columns;
            for (var i = 0; i < cols.length; i++) {
                var key = cols[i].name;
                var keyObj = {
                    value: cols[i].data[index] || cols[i].data[0],
                    type: cols[i].type,
                };
                rtnObj[key] = keyObj;
            }
            this.setValueWhenClose(rtnObj);
        },
        setDetailValueAutoFilling: function (resultInfo,colData) {
            var rtnObj = [];
            var cols = resultInfo.columns;
            for (var i = 0; i < colData.length; i++) {
                var obj = {};
                for (var j = 0; j < cols.length; j++) {
                    var key = cols[j].name;
                    var keyObj = {
                        value: cols[j].data[i],
                        type: cols[j].type,
                    };
                    obj[key] = keyObj;
                }
                rtnObj.push(obj);
            }

            this.setValueWhenClose(rtnObj);
        },
        openDialog: function (flagid) {
            var self = this;
            var bindDom = this.parent.config.bindDom;
            // 根据配置信息获取表单数据
            var url = self.dialogUrl;
            var height = document.documentElement.clientHeight * 0.7;
            var width = document.documentElement.clientWidth * 0.8;
            self.$dialog = dialog.iframe(url, "选择记录", function (rtn) {
                if (rtn) {
                    self.setValueWhenCloseFilling(_modelingFillingMap[bindDom], rtn.fillingSelect, rtn.fillingRelationMap);
                }
                _modelingFillingNum = 0; //全局变量恢复初始值
            }, {
                width: width,
                height: height,
                params: {
                    fillingCfgInfos: _modelingFillingCfgInfo,
                    fillingMapArr: _modelingFillingMap[bindDom],
                    flagid: flagid,
                    modelId: self.parent.config.fdModelId,
                    bindDom: self.parent.config.bindDom
                }
            });
        },
        setValueWhenCloseFilling: function (fillingArr, rtn, fillingRelationMap) {
            var self = this;
            if (rtn == null) {
                return;
            }
            for (var r in rtn) {
                var key = fillingRelationMap[r];
                self.cfgInfo = relationUtil.formatFillingData(_modelingFillingCfgInfo[r].cfgInfo);
                self.setValueWhenClose(rtn[r], _modelingFillingCfgInfo[r].currentWgt);
            }
        },
        setValueWhenClose: function (rawRtn, wgt) {
            if (!rawRtn) {
                return;
            }
            if (!wgt) {
                wgt = this;
            }
            // 兼容非多选框的返回值
            if (rawRtn.length === undefined) {
                rawRtn = [rawRtn];
            }
            this._hasSetValue = true;
            wgt.lastRawRtn = rawRtn;
            var isInDetail = wgt.controlId.indexOf(".") > -1;
            if (!isInDetail) {
                this.setValueWhenCloseForWgt(wgt, rawRtn)
            } else {
                //#127170多选时，若在明细表内
                for (let i = 0; i < rawRtn.length; i++) {
                    var rawItem = [rawRtn[i]];
                    if (i == 0) {
                        this.setValueWhenCloseForWgt(wgt, rawItem);
                        continue;
                    }
                    var controlArray = wgt.controlId.split(".");
                    var tableId = "TABLE_DL_" + controlArray[0];
                    var newRow = DocList_AddRow(tableId, null);
                    var newWgt = $(newRow).find("xformflag[flagtype='filling'][flagid^='" + controlArray[0] + "'][flagid$='" + controlArray[1] + "']");
                    var wgtId = newWgt.find(".modelingPlaceholder").attr("id");
                    wgtId = parseInt(wgtId.substring(7));
                    var currentWgt = LUI('lui-id-'+wgtId).placeholderWgt;
                    var controlIdWithIndex = LUI('lui-id-'+wgtId).controlIdWithIndex;
                    var count = 3;
                    //#165980 循环最近的三个组件，并判断是否是相同的controlId
                    while(!currentWgt && count > 0){
                        wgtId--;
                        if(LUI('lui-id-'+wgtId) && LUI('lui-id-'+wgtId).controlIdWithIndex === controlIdWithIndex){
                            currentWgt = LUI('lui-id-'+wgtId).placeholderWgt;
                        }
                        count--;
                    }
                    this.setValueWhenCloseForWgt(currentWgt, rawItem);
                }
            }
        },
        setValueWhenCloseForWgt: function (wgt, rawRtn) {
            //给触发控件增加当前正在填充的标志，避免回填的控件也是触发控件产生死循环
            this.addFillingFlagOnDom();
            var cfg = this.cfgInfo;
            var outputCfgs = cfg["outputs"];

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
            //填充完毕把正在填充的标志移除
            this.removeFillingFlagOnDom();
        },
        // 明显表行填充模式
        changeDetailsByFillType: function (cfgs) {
            // 1|覆盖行数据（清空+追加）
            if (this.cfgInfo.hasOwnProperty('outExtend') && this.cfgInfo.outExtend === '1') {
                if (this.targetOnlyContainDetail && this.targetIndex != 0) {
                    //业务填充如果目标字段全是明细表，那需要自动填充多行，第一条遵循后台配置的追加或覆盖，其他的都是追加
                    return;
                }
                var sourceCommon = cfgs["sourceCommon"];
                var sourceDetails = cfgs["sourceDetails"];
                //数据来源是主表
                for (var key in sourceCommon) {
                    this.overwriteRow(sourceCommon);
                }
                //数据来源是明细表
                for (var key in sourceDetails) {
                    var sources = cfgs["sourceDetails"][key];
                    this.overwriteRow(sources, sourceCommon);
                }
            }
        },
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
                                if(window.console)
                                console.log("【业务填充控件】不支持明细表内的业务关联控件给其他明细表赋值");
                            }
                        }
                    } else {
                        if(window.console)
                        console.log("【业务填充控件】对话框行返回信息里面不包含\"" + key + "\"的值!");
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
                                    var targetId = targetInfo["controlId"];
                                    targetId = targetId.replace(".", "." + this.detailIndex + ".");
                                    var values = rtn[key]["value"];
                                    var val = "";
                                    // 如果填充的值是数组，则把数据合并，以;号分开
                                    if (values.length) {
                                        var targetWgt = GetXFormFieldById(targetId);
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
                                        val = this._mergeDataInDetail(values, sourceControlId, targetInfo.type,isMix);
                                    }
                                    if (targetInfo.type.indexOf("Attachment") > -1) {
                                        // #97081  附件特殊处理-case 4.1 关联控件在明细表内 ：明细To明细
                                        this.setAttTargetDetail_inDetail(targetId, targetInfo.passiveModelName, rtn["fdId"]["value"], val.split(";"))
                                        continue;
                                    }
                                    this._fillControlVal(targetId, val, targetInfo.type);
                                } else {
                                    if(window.console)
                                        console.log("【业务填充控件】不支持明细表内的业务关联控件给其他明细表赋值");
                                }
                            }
                        }
                    } else {
                        if(window.console)
                        console.log("【业务填充控件】对话框行返回信息里面不包含\"" + key + "\"的值!");
                    }
                }
            } else {
                //明显表行填充模式
                this.changeDetailsByFillType(cfgs);
                if (this._currentTriggerNotVal && this.cfgInfo.hasOwnProperty('outExtend') && this.cfgInfo.outExtend === '1') {
                    //当前填充绑定的触发控件值为空，并且明细表填充模式为覆盖，则整个明细表清空
                   return;
                }
                // 数据来源为非明细表数据,添加行,赋值
                for (var i = 0; i < rawRtn.length; i++) {
                    var singleRtn = rawRtn[i];
                    this.sourceCommon4Filling = this.addDetailsTableRow4Filling(cfgs["sourceCommon"], singleRtn);
                    if (!this._currentTriggerNotVal && (!this.targetOnlyContainDetail || this.sourceOnlyContainMain)) {
                        //判断传出目标如果是只有明细表，则再次判断数据来源是否只是主表，
                        //若是则新增明细表，若来源含有明细表，则不新增主表记录的明细表，明细表几条就新增几条
                        this.addDetailsTableRow(cfgs["sourceCommon"], singleRtn);
                    }
                    // 数据来源为明细表数据
                    for (var key in cfgs["sourceDetails"]) {
                        if (singleRtn.hasOwnProperty(key)) {
                            this.setDetailsTableFieldValues(cfgs["sourceDetails"][key], singleRtn[key]["value"], singleRtn["fdId"]["value"]);
                        }
                    }
                }
            }
        },
        addDetailsTableRow4Filling: function (sources, rowValues, rowFdId) {
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
                            if(window.console)
                            console.log("【业务关联控件】对话框行返回信息里面不包含输出控件ID\"" + sourceId + "\"的值!");
                        }
                    }
                    var targetId = targetInfo["controlId"];
                    var targetIdArr = targetId.split(".");
                    // 仅支持“明细表id.控件id”结构
                    if (targetIdArr.length === 2) {
                        var tableId = "TABLE_DL_" + targetIdArr[0];
                        if (!fieldsVal[tableId]) {
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
                                    if (typeof (mainValue) === "undefined") {
                                        id = value.id;
                                        name = value.name;
                                    }
                                    if (mainValue) {
                                        id = mainValue.id;
                                        name = mainValue.name;
                                    }
                                } else {
                                    id = name = value;
                                    if(window.console)
                                    console.log("【业务关联控件】目标控件ID(" + targetIdArr[1] + ")是地址本，但由于填充的值只有一个，故只能id和name都填充同样的值!");
                                }
                                fieldsVal[tableId]["extendDataFormInfo.value(" + targetIdArr[0] + ".!{index}." + targetIdArr[1] + ".id)"] = id;
                                fieldsVal[tableId]["extendDataFormInfo.value(" + targetIdArr[0] + ".!{index}." + targetIdArr[1] + ".name)"] = name;
                            } else {
                                var controlVal = value;
                                if(controlVal){
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
                            }
                        } else {
                            if(window.console)
                            console.log("【业务填充控件】对话框行返回信息里面不包含输出控件ID\"" + targetId + "\"的值!");
                        }
                    } else {
                        if(window.console)
                        console.log("【业务填充控件】输出控件ID(" + targetId + ")结构异常，不符合\"明细表ID.控件ID\"结构！");
                    }
                }
            }

            return fieldsVal;
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
                            if(window.console)
                            console.log("【业务填充控件】对话框行返回信息里面不包含输出控件ID\"" + sourceId + "\"的值!");
                        }
                    }
                    var targetId = targetInfo["controlId"];
                    var targetIdArr = targetId.split(".");
                    // 仅支持“明细表id.控件id”结构
                    if (targetIdArr.length === 2) {
                        var tableId = "TABLE_DL_" + targetIdArr[0];
                        if (!fieldsVal[tableId]) {
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
                                    if (typeof (mainValue) === "undefined") {
                                        id = value.id;
                                        name = value.name;
                                    }
                                    if (mainValue) {
                                        id = mainValue.id;
                                        name = mainValue.name;
                                    }
                                } else {
                                    id = name = value;
                                    if(window.console)
                                    console.log("【业务填充控件】目标控件ID(" + targetIdArr[1] + ")是地址本，但由于填充的值只有一个，故只能id和name都填充同样的值!");
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
                            if(window.console)
                            console.log("【业务填充控件】对话框行返回信息里面不包含输出控件ID\"" + targetId + "\"的值!");
                        }
                    } else {
                        if(window.console)
                        console.log("【业务填充控件】输出控件ID(" + targetId + ")结构异常，不符合\"明细表ID.控件ID\"结构！");
                    }
                }
            }

            if (fieldsVal) {
                for (var tableId in fieldsVal) {
                    var fieldsObj = {};
                    if (this.sourceCommon4Filling) {
                        $.extend(fieldsObj, this.sourceCommon4Filling[tableId], fieldsVal[tableId]);
                    } else {
                        fieldsObj = fieldsVal[tableId];
                    }
                    // 这里有可能是新增一行空的一行，看后续需求是否需要做为空判断
                    var row = DocList_AddRow(tableId, null, fieldsObj);
                    // #97081  附件特殊处理-case 5（4.2+3.2）  关联控件在明细表外 ：目标为明细
                    this.setAttTargetDetail_addRow(tableId, sources, rowFdId, rowValues, row)
                    this.doExtendOpera(row, fieldsVal[tableId],tableId);
                }
            }
        },
        _fillControlVal: function (controlId, value, type) {
            var self = this;
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
                if(self._currentTriggerNotVal){
                    //清空
                    id = "";
                    name = "";
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
                $($form(controlId + ".id").target.element[0]).trigger("change");
            } else {
                if(self._currentTriggerNotVal){
                    //清空
                    value = "";
                }
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

    });

    /**************************业务填充多选*****************************/
    var FillingMultiDialogPlaceholder = FillingDialogPlaceholder.extend({

        dialogUrl: "/sys/modeling/main/xform/controls/filling/dialog.jsp?multi=true",

    });

    exports.FillingDialogPlaceholder = FillingDialogPlaceholder;
    exports.FillingMultiDialogPlaceholder = FillingMultiDialogPlaceholder;
})