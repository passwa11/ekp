/**
 * 排序生成器
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var dialog = require('lui/dialog');
    var formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder");
    var viewIncRecordGenerator = require("sys/modeling/base/relation/res/js/viewIncRecordGenerator");
    var modelingLang = require("lang!sys-modeling-base");
    var OperationView = base.Component.extend({
        _pamType: {
            "0": modelingLang['sysform.PC'],
            "1": modelingLang['sysform.mobile'],
            "2": modelingLang['operation.PC.mobile'],
            "3": modelingLang['operation.collection.view'],
            "4": modelingLang['operation.new.view']
        },
        _viewCfg: {
            "0": {
                "name": modelingLang['sysModelingOperation.fdListView'],
                "autoOptions": true,
                "inc": "ajax",
                "getGenerator": function (_this) {
                    return new viewIncRecordGenerator.ListViewIncRecordGenerator({parent: _this});
                }
            },
            "1": {
                "name": modelingLang['enums.operation_view.1'],
                "defOption": modelingLang['sysModelingOperation.fdViewDef'],
                "autoOptions": true,
                "inc": "ajax",
                "getGenerator": function (_this) {
                    return new viewIncRecordGenerator.ViewIncRecordGenerator({parent: _this});
                }
            },
            "2": {
                "name": modelingLang['enums.operation_view.2'],
                "defOption": modelingLang['enums.operation_view.2'],
                "inc": "create",
                "getGenerator": function (_this) {
                    return new viewIncRecordGenerator.NewViewIncRecordGenerator({parent: _this});
                }
            }
        },
        //lines,
        initProps: function ($super, cfg) {
            $super(cfg);
            // console.log("OperationView", cfg)
            this.pcfg = cfg.pcfg;
            //初始化容器
            this.key = "fd_view_inc";
            this.element = cfg.container;
            this.valElement = cfg.container.find("[mdlng-prtn-data-group=\"fdView\"]");
            this.showElement = cfg.container.find("[mdlng-prtn-prprty-value=\"fdView\"]");
            this.viewTypeEle = this.showElement.find("[mdlng-prtn-prprty-value=\"fdView_type\"]");
            this.viewViewEle = this.showElement.find("[mdlng-prtn-prprty-value=\"fdView_view\"]");
            this.viewIncEle = this.showElement.find("[mdlng-prtn-prprty-value=\"fdView_inc\"]");
            this.$newViewAddType = this.showElement.find("#_xform_fdNewViewAddType");
            this.$newViewAddType.find("input[type=radio][name=fdNewViewAddType][value=0]").attr("checked",'checked');
            this.$newViewAddTypeTitle = this.element.find("#_xform_fdNewViewAddTypeTitle");
            this.$newViewAddTypeTitle.closest(".model-mask-panel-table-com").hide();
            this.viewTypeEle.hide();
            this.viewViewEle.hide();
            this.viewIncEle.hide();
            //初始化参数
            this.prop = {
                "fdViewDef": this.valElement.find("[name='fdViewDef']").val(),
                "fdViewType": this.valElement.find("[name='fdViewType']").val(),
                "viewModelId": this.valElement.find("[name='viewModelId']").val(),
                "viewModelName": this.valElement.find("[name='viewModelName']").val(),
                "fdViewId": this.valElement.find("[name='fdViewId']").val(),
                "fdListViewId": this.valElement.find("[name='fdListViewId']").val(),
                "fdViewName": this.valElement.find("[name='fdViewName']").val(),
                "fdListViewName": this.valElement.find("[name='fdListViewName']").val(),
                "fdInParam": this.valElement.find("fdInParam").val()
            };
            //  console.log("OperationView", this)
            this.incParamMap = {};
            this.modelWidgets = {},
                //其他
                this.bindEvent();
        },
        bindEvent: function () {
            var self = this;
            var $ele = this.showElement;
            $ele.find("[mdlng-prtn-prprty-type=\"dialog\"]").each(function (idx, dom) {
                $(dom).on("click", function () {
                    var $e = $(this);
                    self.selectFdViewModel($e);
                })
            });
            self.viewTypeEle.find(".model-mask-panel-table-option").find("div").each(function (idx, dom) {
                $(dom).on("click", function () {
                    var val = $(this).attr("option-value");
                    self.selectFdViewType(val);
                    self.showOrHideAddType();
                });
            });


        },
        selectFdViewModel: function ($e) {
            var self = this;
            var modelId = self.viewModelId;
            var appId = self.pcfg.fdAppId;
            dialog.iframe("/sys/modeling/base/relation/import/model_select.jsp?appId=" + appId, modelingLang["behavior.select.form"], function (value) {
                if (value) {
                    //值没有改变，返回
                    if (modelId == value.fdId)
                        return;
                    var $p = $("<p></p>");
                    $p.append(value.fdName);
                    $e.html($p);
                    self.setFdViewModel(value);
                    self.showOrHideAddType();
                }
            }, {
                width: 1010,
                height: 600
            });
        },
        setFdViewModel: function (val) {
            // console.log("setFdViewModel", val)
            this.viewModelId = val.fdId;
            this.valElement.find("[name='viewModelId']").val(val.fdId);
            this.viewModelName = val.fdName;
            this.valElement.find("[name='viewModelName']").val(val.fdName);
            this.showElement.find(".viewModelNameTip").hide();

            this.buildFdViewType();
            this.selectFdViewType("0");
            this.getModelWidgets();
        },
        selectFdViewType: function (val) {
            this.prop.fdViewType = val;
            this.valElement.find("[name='fdViewType']").val(val);
            this.buildFdView(val)
        },
        selectFdView: function (val, name, pamType) {
            var type = this.prop.fdViewType;
            //清空历史数据
            this.valElement.find("[name='fdViewDef']").val(false);
            this.valElement.find("[name='fdViewId']").val("");
            this.valElement.find("[name='fdListViewId']").val("");
            this.valElement.find("[name='fdPamListViewId']").val("");
            this.valElement.find("[name='fdMobileListViewId']").val("");
            this.valElement.find("[name='fdPamViewId']").val("");
            this.valElement.find("[name='fdPcAndMobileType']").val(pamType);
            this.valElement.find("[name='fdViewType']").val(type);
            this.valElement.find("[name='fdCollectionViewId']").val("");
            this.valElement.find("[name='fdCollectionViewName']").val("");
            if (val === "_def") {
                this.prop.fdViewDef = true;
                this.valElement.find("[name='fdViewDef']").val(true);
            } else {
                if (type === "0") {
                    //列表
                    this.prop.fdListViewId = val;
                    this.prop.fdListViewName = name;
                    if (pamType == "2") {
                        //pam模式
                        this.valElement.find("[name='fdPamListViewId']").val(val);
                        this.valElement.find("[name='fdPamListViewName']").val(name);
                    } else if (pamType == "1") {
                        //移动
                        this.valElement.find("[name='fdMobileListViewId']").val(val);
                        this.valElement.find("[name='fdMobileListViewName']").val(name);
                    }else if(pamType == "3"){
                        //新版列表视图
                        this.valElement.find("[name='fdCollectionViewId']").val(val);
                        this.valElement.find("[name='fdCollectionViewName']").val(name);
                    } else {
                        //pc，默认
                        this.valElement.find("[name='fdListViewId']").val(val);
                        this.valElement.find("[name='fdListViewName']").val(name);
                    }

                } else if (type === "1") {
                    //查看
                    this.prop.fdViewId = val;
                    this.prop.fdViewName = name;
                    if (pamType == "2") {
                        //pam模式
                        this.valElement.find("[name='fdPamViewId']").val(val);
                        this.valElement.find("[name='fdPamViewName']").val(name);
                    } else {
                        //移动，pc，默认
                        this.valElement.find("[name='fdViewId']").val(val);
                        this.valElement.find("[name='fdViewName']").val(name);
                    }
                }
            }
            this.buildFdViewInc(type, val,pamType);
        },
        buildFdViewType: function () {
            var $ele = this.viewTypeEle.show();
            $ele.find(".model-mask-panel-table-select-val").attr("option-value", 0);
            $ele.find(".model-mask-panel-table-select-val").html(modelingLang['table.modelingCollectionView']);
        },
        buildFdView: function (type) {
            var self = this;
            var $ele = self.viewViewEle.show();
            $ele.find(".model-mask-panel-table-select-val").html(modelingLang['relation.please.choose']);
            $ele.find(".model-mask-panel-table-select-val").attr("table-select-val", "");
            var $select = $ele.find(".model-mask-panel-table-option");
            $select.empty();

            var viewCfg = self._viewCfg;
            if (viewCfg[type] && viewCfg[type].defOption) {
                var $defOp = $(" <div  option-value=\"_def\" option-value-pam=\"_def\"></div>");
                $defOp.html(viewCfg[type].defOption);
                $defOp.on("click", function () {
                    var $p = self.viewViewEle.find(".model-mask-panel-table-select-val");
                    var val = $(this).attr("option-value");
                    var pamType = $(this).attr("option-value-pam");
                    var name = $(this).html();
                    $p.attr("table-select-val", val);
                    $p.html($(this).html());
                    self.selectFdView(val, name, pamType);
                    self.showOrHideAddType();
                });
                $select.append($defOp);
            }
            this.valElement.find("[name='fdCollectionViewId']").val("");
            this.valElement.find("[name='fdCollectionViewName']").val("");
            if (type && self.viewModelId && type != "2") {
                var u = Com_Parameter.ContextPath + "sys/modeling/base/sysModelingOperation.do?method=selectModelViewList";
                u = u + "&modelId=" + self.viewModelId;
                u = u + "&viewType=" + type;
                $.ajax({
                    url: u,
                    method: 'GET',
                    async: false
                }).success(function (resultStr) {
                    var result = JSON.parse(resultStr);
                    var sourceData = result.data;
                    for (var idx in  sourceData) {
                        var obj = sourceData[idx];
                        var $option = $(" <div option-value-pam='" + obj.fdType + "' option-value=\"" + obj.fdId + "\"></div>");
                        var name = "【" + self._pamType[obj.fdType] + "】" + obj.fdName;
                        $option.html(name);
                        $option.on("click", function () {
                            var $p = self.viewViewEle.find(".model-mask-panel-table-select-val");
                            var val = $(this).attr("option-value");
                            var pamType = $(this).attr("option-value-pam");
                            var name = $(this).html();
                            $p.attr("table-select-val", val);
                            $p.html($(this).html());
                            self.selectFdView(val, name, pamType);
                        });
                        $select.append($option);
                    }
                });
            }
        },
        buildFdViewInc: function (type, viewId,pamType) {
            var self = this;

            this.viewIncEle.find("tbody").empty();
            var $createEle = this.viewIncEle.find(".model-mask-panel-table-create");
            $createEle.hide();
            //#1
            self.viewIncEle.find("thead").find("td").each(function (idx, dom) {
                if ($(dom).html() === modelingLang['modelingAppViewopers.fdOperator']) {
                    $(dom).show();
                }
                if ($(dom).html() === modelingLang['modelingAppViewopers.fdOperation']) {
                    $(dom).hide();
                }
            });
            self.viewInc = null;
            if (type === "0") {
                //列表
                var vcfg = {
                    container: self.viewIncEle.find("tbody"),
                    viewId: viewId,
                    pamType:pamType,
                    pcfg: self.config
                };
                self.viewInc = new viewIncRecordGenerator.ListViewIncRecordGenerator(vcfg);
            } else if (type === "1") {
                //查看
                var vcfg = {
                    container: self.viewIncEle.find("tbody"),
                    viewId: viewId,
                    pcfg: self.config
                };
                self.viewInc = new viewIncRecordGenerator.ViewIncRecordGenerator(vcfg);
            } else if (type === "2") {
                //新建
                self.viewIncEle.find("thead").find("td").each(function (idx, dom) {
                    if ($(dom).html() === modelingLang['modelingAppViewopers.fdOperator']) {
                        $(dom).hide();
                    }
                    if ($(dom).html() === modelingLang['modelingAppViewopers.fdOperation']) {
                        $(dom).show();
                    }
                });
                var vcfg = {
                    container: self.viewIncEle.find("tbody"),
                    viewId: viewId,
                    createBtn: $createEle,
                    widgets: self.getModelWidgets(),
                    pcfg: self.config
                };
                $createEle.show();
                self.viewInc = new viewIncRecordGenerator.NewViewIncRecordGenerator(vcfg);

            }
            // 没有入参的情况下 应屏蔽这些标题栏 #153530
            //自定义业务操作，选择新建视图时，缺少了字段赋值配置项 #155500
            if(type === "2"){
                this.viewIncEle.show();
            }else{
                if(self.viewInc){
                    if( 0 < self.viewInc.getKeyData().length){
                        this.viewIncEle.show();
                    }else{
                        this.viewIncEle.hide();
                    }
                }
            }
            self.showOrHideAddType()
        },

        showOrHideAddType:function (){
            //判断如果是新建视图，操作位置为列表视图且勾选操作数据为【是】，显示新建方式
            var type = this.prop.fdViewType;
            var $p = this.viewViewEle.find(".model-mask-panel-table-select-val");
            var selectVal = $p.attr("table-select-val");
            var fdViewLocation=$("[name='fdViewLocation']:checked").val();
            var fdListViewIdValid=$("[name='fdListViewIdValid']:checked").val();
            if(selectVal== "_def" && type === "2" && fdViewLocation == '0' && fdListViewIdValid == '1'){
                this.$newViewAddTypeTitle.closest(".model-mask-panel-table-com").show();
            }else{
                this.$newViewAddTypeTitle.closest(".model-mask-panel-table-com").hide();
            }
        },
        getModelWidgets: function (modelId) {
            var self = this;
            if (!modelId) {
                modelId = self.viewModelId;
            }
            if (!modelId) {
                return
            }
            if (self.modelWidgets[modelId]) {
                return self.modelWidgets[modelId];
            } else {
                var url = Com_Parameter.ContextPath + "sys/modeling/base/modeling.do?method=getModelWidget&modelId=" + modelId;
                $.ajax({
                    url: url,
                    method: 'GET',
                    async: false
                }).success(function (resultStr) {
                    var result = JSON.parse(resultStr);
                    self.modelWidgets[modelId] = result.data;
                    return self.modelWidgets[modelId];
                })
            }
        },
        getInc: function (inParam) {
            var inc = null;
            if (this.viewInc) {
                inc = this.viewInc.getKeyData();
            }
            inParam.viewModelName = this.prop.viewModelName
            inParam.viewModelId = this.prop.viewModelId;
            var viewSet = {
                    "type": this.prop.fdViewType,
                    "id": this.prop.fdViewId,
                    "name": this.prop.fdViewName,
                    "def": false
                }
            ;
            if (this.prop.fdViewDef === true || this.prop.fdViewDef === "1") {
                viewSet.def = true
            } else {
                if (this.prop.fdViewType === "0") {
                    viewSet.id = this.prop.fdListViewId;
                    viewSet.name = this.prop.fdListViewName;
                }
            }
            inParam.viewSet = viewSet;
            if (inc) {
                if (this.prop.fdViewType === "2") {
                    inParam.viewNew = inc;
                } else {
                    inParam.view = inc;
                }
            } else {
                inParam.view = [];
                inParam.viewNew = [];
            }
            var newViewAddType = this.$newViewAddType.find("[name='fdNewViewAddType']:checked").val();
            inParam.newViewAddType =  newViewAddType;
            return inParam;
        },
        getKeyData: function () {

        }
        ,
        show: function () {
            this.element.show();
        }
        ,
        hide: function () {
            this.element.hide();
        }
        ,
        initByStoreData: function () {
            try {
                var self = this;
                var $inputs = this.valElement.find("input[type='hidden']");
                var p = {};
                $inputs.each(function (idx, e) {
                    var key = $(e).attr("name");
                    var val = $(e).val();
                    p[key] = val;
                });
                //#1 viewModel
                var $p = $("<p></p>");
                $p.append(p.viewModelName);
                self.showElement.find("[mdlng-prtn-prprty-value=\"fdView_model\"]").html($p);
                self.setFdViewModel({"fdId": p.viewModelId, "fdName": p.viewModelName})
                //#2 viewType
                self.selectFdViewType(p.fdViewType);
                var $typeOperation = self.showElement.find("[option-value=\"" + p.fdViewType + "\"]");
                var $select = $typeOperation.parent().parent();
                if (p.fdViewType) {
                    var $p = $select.find(".model-mask-panel-table-select-val");
                    $p.attr("table-select-val", p.fdViewType);
                    $p.html($typeOperation.html());
                }
                //#3 id
                var viewId = "";
                if (p.fdViewDef === "true" || p.fdViewType === "2") {
                    //默认 or 新建
                    viewId = "_def"
                } else {
                    if (p.fdViewType === "0") {
                        //列表
                        if (p.fdPcAndMobileType == "2") {
                            //pam
                            viewId = p.fdPamListViewId
                        } else if (p.fdPcAndMobileType == "1") {
                            //移动
                            viewId = p.fdMobileListViewId
                        }else if(p.fdPcAndMobileType == "3"){
                            //新版列表视图
                            viewId = p.fdCollectionViewId;
                        } else {
                            viewId = p.fdListViewId
                        }
                    } else {
                        //查看
                        if (p.fdPcAndMobileType == "2") {
                            //pam
                            viewId = p.fdPamViewId;
                        } else {
                            viewId = p.fdViewId
                        }

                    }
                }
                //设置Id
                var $thisOptions = null;
                self.viewViewEle.find(".model-mask-panel-table-option").find("div").each(function (idx, dom) {
                    if ($(dom).attr("option-value") === viewId) {
                        $thisOptions = $(dom);
                    }
                });
                // 绘制入参
                if ($thisOptions) {
                    var $p = self.viewViewEle.find(".model-mask-panel-table-select-val");
                    var val = $thisOptions.attr("option-value");
                    var pamType = $thisOptions.attr("option-value-pam");
                    var name = $thisOptions.html();
                    $p.attr("table-select-val", val);
                    $p.html($thisOptions.html());
                    self.selectFdView(val, name, pamType);
                }

                var incJson = JSON.parse(p.viewInc);
                if(self.viewInc){
                    self.viewInc.initByStoreData(incJson);

                    var newViewAddType = incJson.newViewAddType;
                    if(!newViewAddType){
                        //兼容旧数据，默认为单独新建
                        newViewAddType = "0";
                    }
                    self.$newViewAddType.find("input[type=radio][name=fdNewViewAddType][value="+newViewAddType+"]").attr("checked",'checked');
                }

            } catch
                (e) {
                console.error("数据初始化异常，", e)
            }

        }

    });

    exports.OperationView = OperationView;
});
