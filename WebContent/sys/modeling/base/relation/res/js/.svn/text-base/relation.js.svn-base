/**
 * 页面全局对象
 */
define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var base = require("lui/base");
    var dialog = require('lui/dialog');
    //公式定义器
    var formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder");
    //自定义参数
    var sortRecordGenerator = require("sys/modeling/base/relation/res/js/sortRecordGenerator");
    var outRecordGenerator = require("sys/modeling/base/relation/res/js/outRecordGenerator");
    var whereRecordGenerator = require("sys/modeling/base/relation/res/js/whereRecordGenerator");
    var throughRecordGenerator = require("sys/modeling/base/relation/res/js/throughRecordGenerator");
    var sourceRecordGenerator = require("sys/modeling/base/relation/res/js/sourceRecordGenerator");
    var modelingLang = require("lang!sys-modeling-base");

    var Relation = base.Container.extend({
        initProps: function ($super, cfg) {
            $super(cfg);
            //初始化1
            formulaBuilder.initFieldList(cfg.xformId);
            this.element = cfg.container;
            this.widgets = this.config.widgets;
            this.build()

        },
        build: function () {
            this.fdOutSortEle = new sortRecordGenerator.SortRecordGenerator({
                fieldName: "fdOutSort",
                pcfg: this.config
            });
            this.fdOutParamEle = new outRecordGenerator.OutRecordGenerator({
                fieldName: "fdOutParam",
                pcfg: this.config
            });
            this.fdInWhereEle = new whereRecordGenerator.WhereRecordGenerator({
                fieldName: "fdInWhere",
                pcfg: this.config,
                widgets_docStatus: this.config.widgets_docStatus
            });
            this.fdThroughEle = new throughRecordGenerator.ThroughRecordGenerator({
                fieldName: "fdThrough",
                pcfg: this.config
            });
            this.fdListThroughEle = new throughRecordGenerator.ThroughRecordGenerator({
                fieldName: "fdListThrough",
                pcfg: this.config
            });
            this.fdSourceTypeEle = new sourceRecordGenerator.SourceRecordGenerator({
                fieldName: "fdSourceType",
                pcfg: this.config,
                parent:this
            });
            this.bindEvent();
            this.initByStoreData();
        },
        bindEvent: function () {
            var self = this;
            var $ele = this.element;
            //弹窗
            $ele.find("[mdlng-rltn-prprty-type='dialog']").each(function (idx, dom) {
                $(dom).on("click", function () {
                    var $e = $(this);
                    self.onDialog($e)
                })
            })
        },
        onDialog: function ($ele) {
            var self = this;
            var fieldName = $ele.attr("mdlng-rltn-prprty-value");
            var fieldValue = self.element.find("[mdlng-rltn-data=\"" + fieldName + "\"]").val();
            var sourceType = self.element.find("[name=\"fdSourceType\"]:checked").val();
            var targetDetail = self.element.find("[name=detailChecked]:checked").val();
            var dialogParams = {
                type: fieldName,
                data: self.widgets.passive,
                oldData: fieldValue,
                multi: (fieldName === "fdReturn"),
                ext: {},
                isDetail:sourceType === "1",
                targetDetail:targetDetail
            };
            //筛选项新增展示样式
            if(fieldName == "fdOutSearch"){
            	dialogParams.ext.searchNumber = self.element.find("[mdlng-rltn-data='fdOutSearchNumber']").val();
            }
            var url = fieldName === "fdReturn" ? "/sys/modeling/base/relation/import/relation_return.jsp" : "/sys/modeling/base/relation/import/relation_proSelect.jsp";
            //#129651 改标签标题
            var title = fieldName ==="fdReturn" ? modelingLang['relation.return.value.setting'] : modelingLang['relation.filter.settings'];
            if ( fieldName ==="fdOutSelect"){
            	title = modelingLang['relation.display.value.setting'];
            }
            dialog.iframe(url, title, function (value) {
                if (value == null)
                    return;
                var v = JSON.stringify(value);
                self.element.find("[mdlng-rltn-data=\"" + fieldName + "\"]").val(v);
                var fun = "_" + fieldName;
                self[fun]();
            }, {
                width: 545,
                height: 650,
                params: dialogParams
            });
        },
        showEles: function (propertys) {
            for (var key in propertys) {
                if(propertys[key] === "fdSourceType"){
                    this.fdSourceTypeEle.showElement();
                }else{
                    this.element.find("[mdlng-rltn-property='" + propertys[key] + "']").show();
                }
            }
        },
        hideEles: function (propertys) {
            for (var key in propertys) {
                if(propertys[key] === "fdSourceType"){
                    this.fdSourceTypeEle.hideElement();
                }else{
                    this.element.find("[mdlng-rltn-property='" + propertys[key] + "']").hide();
                }
            }
        },
        refreshWhere:function(v){
            //#119425 非弹出框形式屏蔽查询条件入参
            this.fdInWhereEle.filterWhereByStyleEq2=false;
            if (v != "0" && v != "1") {
                this.fdInWhereEle.filterWhereByStyleEq2=true;
            }
            this.fdInWhereEle.refreshWhereByStyleEq2()
        },
        startup: function ($super, cfg) {
            $super(cfg);
        },
        //获取传到后台去的参数方法
        getKeyData: function () {
            var $ele = this.element;
            var fdOutSortData = this.fdOutSortEle.getKeyData();
            var fosd = JSON.stringify(fdOutSortData);
            $ele.find("[mdlng-rltn-data=\"fdOutSort\"]").val(fosd);

            var fdOutParamData = this.fdOutParamEle.getKeyData();
            var fopd = JSON.stringify(fdOutParamData);
            $ele.find("[mdlng-rltn-data=\"fdOutParam\"]").val(fopd);


            var fdInWhereData = this.fdInWhereEle.getKeyData();
            var fiwd = JSON.stringify(fdInWhereData);
            $ele.find("[mdlng-rltn-data=\"fdInWhere\"]").val(fiwd);

            //穿透
            var fdThroughData = this.fdThroughEle.getKeyData();
            console.debug("fdThroughData", fdThroughData)
            this.appendValToHide("fdIsThrough", fdThroughData.isThrough);
            if (fdThroughData.isThrough) {
                //清除原始数据影响
                this.appendValToHide(this.fdThroughEle.feildName + "ViewId", "");
                this.appendValToHide(this.fdThroughEle.feildName + "PamViewId", "");
                var viewSet = fdThroughData.viewSet;
                if (viewSet) {
                    this.appendValToHide(this.fdThroughEle.feildName + "ViewDef", "false");
                    if (viewSet) {
                        //默认
                        if (viewSet.def) {
                            this.appendValToHide(this.fdThroughEle.feildName + "ViewDef", viewSet.def);
                        } else if (viewSet.type && viewSet.type == "2") {
                            //PcandMobile
                            this.appendValToHide(this.fdThroughEle.feildName + "PamViewId", viewSet.id);
                        } else {
                            this.appendValToHide(this.fdThroughEle.feildName + "ViewId", viewSet.id);
                        }
                    }
                    //入参
                    // var incval = JSON.stringify(throughJson.view);
                    // appendValToHide("fdThroughInc", incval);
                }
            }

            var fdListThroughData = this.fdListThroughEle.getKeyData();
            console.debug("fdListThroughData", fdListThroughData);
            this.appendValToHide("fdIsListThrough", fdListThroughData.isThrough);
            if (fdListThroughData.isThrough) {
                //清除原始数据影响
                this.appendValToHide(this.fdListThroughEle.feildName + "ViewId", "");
                this.appendValToHide(this.fdListThroughEle.feildName + "PamViewId", "");
                var viewSet = fdListThroughData.viewSet;
                if (viewSet) {
                    //默认
                    this.appendValToHide(this.fdListThroughEle.feildName + "ViewDef", "false");
                    if (viewSet.def) {
                        this.appendValToHide(this.fdListThroughEle.feildName + "ViewDef", viewSet.def);
                    } else if (viewSet.type && viewSet.type == "2") {
                        //PcandMobile
                        this.appendValToHide(this.fdListThroughEle.feildName + "PamViewId", viewSet.id);
                    } else {
                        this.appendValToHide(this.fdListThroughEle.feildName + "ViewId", viewSet.id);
                    }
                }
            }

            //数据来源+明细表查询条件
            var fdSourceTypeData = this.fdSourceTypeEle.getKeyData();
            console.debug("fdSourceTypeData", fdSourceTypeData)
            var fstd = JSON.stringify(fdSourceTypeData.detail);
            $ele.find("[mdlng-rltn-data=\"fdDetailWhere\"]").val(fstd);

        },
        appendValToHide: function (name, value) {
            console.debug("appendValToHide", name, value);
            if ($("input[name='" + name + "']").length > 0) {
                var $ele = $("input[name='" + name + "']");
                $ele.val(value);
            } else {
                var $ele = $("<input type='hidden'/>");
                $ele.attr("name", name);
                $ele.val(value);
                this.element.append($ele);
            }

        },
        initByStoreData: function () {
            var self = this;
            var $ele = this.element;
            //弹窗
            $ele.find("[mdlng-rltn-prprty-type='dialog']").each(function (idx, dom) {
                var fieldName = $(dom).attr("mdlng-rltn-prprty-value");
                var fun = "_" + fieldName;
                self[fun]();
            })
            var fdShowType = $ele.find("[name=fdShowType]:checked").val();
            self.refreshWhere(fdShowType);
            if (fdShowType == "0" || fdShowType == "1") {
                self.showEles(["fdListThrough", "fdOutSelect", "fdOutSearch", "fdOutParam", "fdOutExtend","fdSourceType"]);
            } else {
                self.hideEles(["fdListThrough", "fdOutSelect", "fdOutSearch", "fdOutExtend", "fdOutParam","fdSourceType"]);
            }
            //
            this._fdOutSort();
            this._fdOutParam();
            this._fdInWhere();
            this._fdThrough();
            this._fdListThrough();
            this._fdSourceTypeEle();

        },
        //伪双向绑定，
        dialogCallbackShowTxt: function (filedName) {
            var $ele = this.element;
            var vs = $ele.find("[mdlng-rltn-data=\"" + filedName + "\"]").val();
            if (!vs) {
                return;
            }
            try {
                var value = JSON.parse(vs);
                var str = "";
                for (var i in value) {
                    var v = value[i]
                    if (v.label) {
                        if(filedName == "fdOutSearch" && v.businessType == "textarea"){
                            continue;
                        }
                        if (v.type === "_inputType") {
                            str += v.label;
                        } else {
                            if (filedName === "fdReturn") {
                                str += "【" + v.label + "】";
                            } else {
                                str += v.label + ";";
                            }
                        }
                    }
                }
                var $p = $("<p></p>");
                $p.append(str);
                $ele.find("[mdlng-rltn-prprty-value=\"" + filedName + "\"]").html($p);
            } catch (e) {
                console.error("dialogCallbackShowTxt", filedName, e);
            }

        },
        _fdReturn: function () {
            this.dialogCallbackShowTxt("fdReturn");
        },
        _fdOutSearch: function () {
        	if(this.element.find("[mdlng-rltn-data='fdOutSearch']").val()){
        		var param = $.parseJSON(this.element.find("[mdlng-rltn-data='fdOutSearch']").val());
	        	if(param.paramArray){
	        		this.element.find("[mdlng-rltn-data='fdOutSearch']").val(JSON.stringify(param.paramArray));
	            	this.element.find("[mdlng-rltn-data='fdOutSearchNumber']").val(param.searchNumber);
	        	}
        	}
            this.dialogCallbackShowTxt("fdOutSearch");
        },
        _fdOutSelect: function () {
            this.dialogCallbackShowTxt("fdOutSelect");
        },
        _fdOutParam: function () {
            var $ele = this.element;
            var vs = $ele.find("[mdlng-rltn-data=\"fdOutParam\"]").val();
            if (vs && vs.length > 0) {
                var value = JSON.parse(vs);
                this.fdOutParamEle.initByStoreData(value);
            }
        },
        _fdOutSort: function () {
            var $ele = this.element;
            var vs = $ele.find("[mdlng-rltn-data=\"fdOutSort\"]").val();
            if (vs && vs.length > 0) {
                var value = JSON.parse(vs);
                this.fdOutSortEle.initByStoreData(value);
            }
        },
        _fdInWhere: function () {
            var $ele = this.element;
            var vs = $ele.find("[mdlng-rltn-data=\"fdInWhere\"]").val();
            if (vs && vs.length > 0) {
                var value = JSON.parse(vs);
                this.fdInWhereEle.initByStoreData(value);
            }
        },
        _fdThrough: function () {
            var $ele = this.element;
            var vs = $ele.find("[mdlng-rltn-data=\"fdThrough\"]").val();
            if (vs && vs.length > 0) {
                var value = JSON.parse(vs);
                this.fdThroughEle.initByStoreData(value);
            }
        },
        _fdListThrough: function () {
            var $ele = this.element;
            var vs = $ele.find("[mdlng-rltn-data=\"fdListThrough\"]").val();
            if (vs && vs.length > 0) {
                var value = JSON.parse(vs);
                this.fdListThroughEle.initByStoreData(value);
            }
        },
        _fdSourceTypeEle: function () {
            var $ele = this.element;
            var vs = $ele.find("[name=\"fdSourceType\"]:checked").val();
            if (vs && vs == "1") {
                this.fdSourceTypeEle.initByStoreData(vs);
            }
        }


    });

    exports.Relation = Relation;
});
