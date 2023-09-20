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
    "sys/modeling/main/xform/controls/placeholder/mobile/Util"
    , "sys/modeling/main/xform/controls/filling/mobile/dialog/DialogMixin",
    "sys/modeling/main/xform/controls/filling/mobile/FillingEventBase",
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
    DialogMixin,
    FillingEventBase,
    registry
) {
    var _fillworking = false;
    var dialogFilling = [];
    var isAfterSelect = false;
    var claz = declare("sys.modeling.main.xform.controls.filling.mobile.FillingEvent", [FillingEventBase], {
        //事件控件
        name: null,

        //入参
        inputParams: null,

        //出参
        outputParams: null,

        // 标题id，兼容选择框
        textId: null,

        // 每一选项的上下文
        data: null,

        queryNavUrl: "/sys/modeling/main/modelingAppXFormMain.do?method=getMobileNavInfo&fieldName=!{fieldName}&fdAppModelId=!{fdAppModelId}",

        queryDataUrl: "/sys/modeling/main/modelingAppXFormMain.do?method=executeQuery&pageno=!{currentPage}&rowsize=15",

        key: null,

        fillType: null,

        _inDetailTable: false,

        fieldName : null,
        fdAppModelId : null,
        envInfo: {},
        dataDialog:null,
        order:0,

        postCreate: function() {
            this.inherited(arguments);
        },

        _execClick: function (obj,key,evt) {
            if (key) {
                this.key = key;
                var controlId = obj.bindDom;
                var wgtName = obj.controlId;
                if(controlId.indexOf(".")>-1 && wgtName.indexOf(".")<0){
                    wgtName = controlId.split(".")[0] +"."+obj.controlId;
                }
                var wgtValue = placeholderUtil.getFormValueById(controlId);
                this.execEvent(wgtName,wgtValue);
            }
        },

        execDone: function () {
            this.defer(function () {
                _fillworking = false;
                if (this.process) {
                    this.process.hide();
                }
                if(dialogFilling.length > 0){
                    var wgt = dialogFilling.shift();
                    if(wgt.dialog){
                        wgt.dialog.execEvent(wgt.wgtName,wgt.wgtValue);
                    }
                }
            }, 300);
        },
        execSelectDone: function () {
            this.defer(function () {
                _fillworking = false;
                if (this.process) {
                    this.process.hide();
                }
            }, 300);
        },

        execEvent: function (wgtName,wgtValue) {
            if (_fillworking) {
                //不是回填字段触发值改变事件，则需要继续弹框
                if(!isAfterSelect){
                    var wgt ={};
                    wgt.dialog = this;
                    wgt.wgtName = wgtName;
                    wgt.wgtValue = wgtValue;
                    dialogFilling.push(wgt);
                }
                return;
            }

            _fillworking = true;
            this.process = Tip.processing().show();

            var search = "";
            // 获取传入参数
            var formDatas ={};
            for(var key in this.envInfo){
                var relationCfg = this.envInfo[key];
                for(var cfgKey in relationCfg){
                    var cfg = relationCfg[cfgKey];
                    if(!wgtValue || wgtValue.length<=0){
                        //#165914 有执行过回填，才清空回填字段值
                        if(this._hasSetValue){
                            //触发控件值为空，清空
                            var outputCfgs = cfg["outputs"];
                            //清空回填字段值
                            this.fillTargetControlsEmptyVal(outputCfgs["fields"]);
                            this.fillTargetControlsEmptyValInDetail(outputCfgs["details"]);
                            //清空之后，重新设置为默认值
                            this._hasSetValue = false;
                        }
                    }
                    var formValue = placeholderUtil.findFormValueByCfg(relationCfg[cfgKey]);
                    for (var formKey in formValue) {
                        if(!formDatas.hasOwnProperty(formKey)){
                            formDatas[formKey] = formValue[formKey];
                        }
                    }
                }
            }
            if(!wgtValue || wgtValue.length<=0){
                this.execDone();
                return;
            }

            this.insFormDatas = formDatas;
            var url = this.queryNavUrl + "&updateCfg=true";
            this.fieldName = this.bindDom;
            url = util.urlResolver(url, {fieldName: this.bindDom,fdAppModelId: this.fdAppModelId});
            var self = this;
            request
                .post(util.formatUrl(url), {data: {ins : JSON.stringify(formDatas),search:search,widgetId:wgtName}, handleAs: "json"})
                .then(
                    function (json) {
                        if(!json || !json.tabInfo){
                            self.execDone();
                        }
                        var showDialog = false;
                        isAfterSelect = true;
                        for (var i = 0; i < json.tabInfo.length; i++) {
                            var navInfo = json.tabInfo[i];
                            if(navInfo.showDialog){
                                showDialog = true;
                                continue;
                            }
                            if(navInfo.datas && navInfo.datas.columns){
                                var rowLength=0;
                                if(navInfo.datas.columns.length > 0 && navInfo.datas.columns[0].data){
                                    rowLength = navInfo.datas.columns[0].data.length;
                                }
                                var rows =[];
                                for (var j = 0; j < rowLength; j++) {
                                    var info = self.getRowInfo(navInfo.datas.columns,j);
                                    rows.push(info);
                                }
                                isAfterSelect = true;
                                self._fillDataInfo_modeling(self,rows,navInfo.relationId,navInfo.widgetId);
                                isAfterSelect = false;
                            }
                        }
                        isAfterSelect = false;
                        if(showDialog){
                            self.ins = JSON.stringify(formDatas);
                            if(self.dataDialog == null){
                                self.dataDialog = new DialogMixin();
                            }

                            self.dataDialog.ins = self.ins.replace(/\"/g,"&quot;");
                            self.dataDialog.templURL= "sys/modeling/main/xform/controls/filling/mobile/filling_mul.jsp";
                            self.dataDialog.fieldName = self.fieldName;
                            self.dataDialog.fdAppModelId = self.fdAppModelId;
                            self.dataDialog.widgetId = wgtName||"";
                            self.dataDialog.controlId = self.controlId || "";
                            self.dataDialog.key = self.key;
                            self.dataDialog.envInfo = self.envInfo;
                            self.dataDialog.afterSelect=function (srcObj,evt){
                                self.afterSelection(srcObj,evt);
                            }
                            self.dataDialog.startup();
                            self.dataDialog._selectCate();
                            self.execSelectDone();
                            return;
                        }

                        self.execDone();
                    },
                    function () {
                        self.execDone();
                        Tip.fail({text: Msg["mui.eventbase.errorMsg"]});
                    }
                );
        },
        afterSelection:function (srcObj,evt){
            var rows = evt.rows;
            _fillworking = true;
            isAfterSelect = true;
            for(var controlId in rows){
                var row = rows[controlId];
                for(var relationId in row){
                    this._fillDataInfo_modeling(srcObj,row[relationId],relationId,controlId);
                }
            }
            if(this.dataDialog){
                this.dataDialog.destroy();
            }
            isAfterSelect = false;
            this.execDone();
        }

    });
    return claz;
});
