/**
 * 其他节点设置
 */
define(function (require, exports, module) {

    var $ = require('lui/jquery'),
        topic = require('lui/topic'),
        base = require('lui/base'),
        formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder"),
        whereGenerator = require('sys/modeling/base/resources/js/views/collection/whereGenerator'),
        preNodeSetting = require('sys/modeling/base/views/business/res/preNodeSetting'),
        dialog = require('lui/dialog');
    var modelingLang = require("lang!sys-modeling-base");
    var OtherNodeSetting = base.Component.extend({
        initProps: function ($super, cfg) {
            $super(cfg);
            this.container = cfg.container;
            this.itemIndex = cfg.itemIndex;
            this.config = cfg.config;
            this.parent = cfg.parent;
            this.sysWhereCollection = [];
            this.whereCollection = [];
            this.preNodeSettingCollection = [];
            this.targetModelData = {};
            this.dataSourceId = "";
            this.nodeSettingId = parseInt(((new Date().getTime()+Math.random())*10000)).toString(16);
        },

        draw: function ($super, cfg) {
            $super(cfg);
            var self = this;
            //目标表单
            this.doRenderTargetModel();
            //查询条件
            // this.doRenderWhereBlock();
            //上级节点
            this.doRenderParentNode();
            //视图穿透
            this.doRenderView();
            //返回
            this.bindEvent();
            this.container.css("display", "inline-block");
            this.container.find(".nodeSetting").removeAttr("style");

            //连接根节点点击事件
            this.container.find("[name='fdIsConnectRoot']").on("click", function () {
                if ($(this).prop("checked") == true) {
                    self.container.find(".connect-root-config").css("display", "block");
                } else {
                    self.container.find(".connect-root-config").css("display", "none");
                }
            });

            if (this.config) {
                this.initByStoreData(this.config);
            }
            topic.subscribe("modeling.mindMap.delWhereTr", function (evt) {
                self.delWhereTr(evt);
            });
        },
        doConnectRoot: function (xformId, index) {
            var self = this;
            this.container.find(".no-target-model").css("display", "none");
            var $DisplayContainer = this.container.find(".connect-root-config");
            $DisplayContainer.find(".target-form-content").removeClass("no-target-model-temp");
            $DisplayContainer.html("");
            $DisplayContainer.append(formulaBuilder.get_style1(index + "_connectRootFormula", "Boolean", null, null, xformId));
        },
        doRenderView: function () {
            var self = this;
            var $otherNodeView = this.container.find(".otherNodeView");
            //视图穿透的开关
            $otherNodeView.find("[type='checkbox']").off("click").on("click", function () {
                var value = $otherNodeView.find("[name='fdOtherNodeViewEnable']").val();
                if (value == "0") {
                    $otherNodeView.find("[name='fdOtherNodeViewEnable']").val(1);
                    self.container.find(".mind-map-show-select").removeAttr("style");
                    if ($.isEmptyObject(self.targetModelData)) {
                        $(".mind-map-show-select").find(".fdOtherNodeView").append("<option value=''>"+modelingLang['listview.default.view']+"</option>");
                        $(".mind-map-show-select").find(".fdOtherNodeView").attr("disabled", "disabled");
                    }
                } else {
                    $otherNodeView.find("[name='fdOtherNodeViewEnable']").val(0);
                    $(".mind-map-show-select").css("display", "none");
                }
            })
        },
        getViews: function (modelId) {
            var self = this;
            var url = Com_Parameter.ContextPath + "sys/modeling/base/mindMap.do?method=getViewsByModel&modelId=" + modelId;
            $.ajax({
                url: url,
                type: "get",
                async: false,
                success: function (data) {
                    if (data) {
                        self.doRenderViewOption(data);
                    }
                }
            });
        },
        getViewFlowInfo : function(modelId){
            self = this;
            var url = Com_Parameter.ContextPath + "sys/modeling/base/mindMap.do?method=getIsFlowEnableByModel&modelId=" + modelId;
            $.ajax({
                url: url,
                type: "get",
                async: false,
                success: function (data) {
                    if (data && typeof (data.isFlow) != "undefined") {
                        //流程信息
                        self.container.find(".otherNodeView").find("[name='fdOtherNodeViewFlow']").val(data.isFlow);
                    }
                }
            });
        },
        doRenderViewOption: function (data) {
            var $select = this.container.find(".fdOtherNodeView");
            $select.html("<option value=''>"+modelingLang['listview.default.view']+"</option>");
            var html = "";
            for (var i = 0; i < data.length; i++) {
                html += "<option value='" + data[i].fdId + "'>" + data[i].fdName + "</option>";
            }
            $select.append(html);

        },
        doRenderParentNode: function () {
            var self = this;
            var $preNodeContainer = this.container.find(".connect-parent-node");
            $preNodeContainer.find(".model-data-create").off("click").on("click", function () {
                self.drawPreNode($preNodeContainer);
            });
        },
        drawPreNode: function ($preNodeContainer, config) {
            var self = this;
            var $table = $preNodeContainer.find(".pre-root-content");
            $table.attr("id", "preNode" + self.itemIndex);
            var cfg = {
                container: $table,
                parent: self,
                config: config,
                otherNodeIdCollection: self.parent.parent.otherNodeIdCollection,
                preNodeSettingCollection: self.preNodeSettingCollection
            };
            self.preNodeSetting = new preNodeSetting.PreNodeSetting(cfg);
            self.preNodeSetting.startup();
            self.preNodeSetting.draw();
            self.preNodeSettingCollection.push(self.preNodeSetting);
            //交互上不需要拖动
            // self.sortable("preNode"+self.itemIndex);
        },
        getWidgetData: function (modelId) {
            var self = this;
            var url = Com_Parameter.ContextPath + "sys/modeling/base/modeling.do?method=getModelWidget&modelId=" + modelId;
            $.ajax({
                url: url,
                type: "get",
                async: false,
                success: function (data, status) {
                    if (data) {
                        self.fieldOptions = window.whereFieldGenerator.modelDict(JSON.parse(data).data);
                    }
                }
            });
        },
        clearWhere: function () {
            var self = this;
            for (var i = 0; i < this.whereCollection.length; i++) {
                var wiget = this.whereCollection[i];
                this.whereCollection.splice(i, 1);
                topic.channel("modeling").unsubscribe("field.change", wiget.fieldChange, wiget);
                wiget.destroy();
                i--;
            }

            for (var i = 0; i < this.sysWhereCollection.length; i++) {
                var wiget = this.sysWhereCollection[i];
                this.sysWhereCollection.splice(i, 1);
                topic.channel("modeling").unsubscribe("field.change", wiget.fieldChange, wiget);
                wiget.destroy();
                i--;
            }
        },
        doRenderWhereBlock: function (modelId) {
            var self = this;
            var $whereBlock = this.container.find(".whereBlock-table");
            $whereBlock.find(".data-filter-whereType").attr("name", "data-filter-whereType" + this.itemIndex);
            $whereBlock.find(".sys-filter-whereType").attr("name", "sys-filter-whereType" + this.itemIndex);
            //自定义查询或内置查询
            $whereBlock.find(".common-data-filter-whereType li").on("click", function () {
                $(this).siblings().removeClass("active");
                $(this).addClass("active");
                var authValue = $(this).attr("value");
                var $whereType = $(this).closest(".common-data-filter-whereType");
                if (authValue === "1") {
                    //内置
                    $whereType.siblings(".data-filter-content").css("display", "none");
                    $whereType.siblings(".data-filter-content-sys").css("display", "block");
                    //触发查询条件的内置和自定义的事件
                    $whereType.siblings(".data-filter-content-sys").find("[data-where-value='1']").trigger("click");
                } else {
                    //自定义
                    $whereType.siblings(".data-filter-content").css("display", "block");
                    $whereType.siblings(".data-filter-content-sys").css("display", "none");
                }
            });
            //新增
            $whereBlock.find(".model-data-create").off("click").on("click", function () {
                var targetModelId = $(this).closest(".nodeSettingTable").find("[name='fdTargetModelId']").val();
                self.getWidgetData(targetModelId);
                var whereType = $(this).siblings(".common-data-filter-whereType").find("li.active").attr("value");
                if (whereType === "1") {
                    var contentContainer = $(this).siblings(".data-filter-content-sys");
                    var whereWgt = new whereGenerator({
                        container: contentContainer.find("table"),
                        parent: self,
                        wheretype: "sys_query"
                    });
                    whereWgt.startup();
                    whereWgt.draw();
                    //内置查询
                    self.sysWhereCollection.push(whereWgt);
                } else {
                    var contentContainer = $(this).siblings(".data-filter-content");
                    var whereWgt = new whereGenerator({
                        container: contentContainer.find("table"),
                        parent: self,
                        wheretype: "custom_query"
                    });
                    whereWgt.startup();
                    whereWgt.draw();
                    //自定义查询
                    self.whereCollection.push(whereWgt);
                }
            });
            this.container.find(".common-data-filter-whereType li.active").trigger("click");
        },
        getWidgetData :function(modelId){
            var self = this;
            var url = Com_Parameter.ContextPath + "sys/modeling/base/modeling.do?method=getModelWidget&modelId=" + modelId;
            $.ajax({
                url: url,
                type: "get",
                async: false,
                success: function (data, status) {
                    if(data){
                        self.fieldOptions = window.whereFieldGenerator.modelDict(JSON.parse(data).data);
                    }
                }
            });
        },
        doInitWhere: function (whereConfig,targetModelId) {
            if(!whereConfig){
                return;
            }
            var self = this;
            var commonCfgArr = whereConfig;
            for (var i = 0; i < commonCfgArr.length; i++) {
                var whereObj = commonCfgArr[i];
                var whereType = whereObj.whereType;
                var whereBlockType = whereObj.whereBlockType;
                if(targetModelId){
                    self.getWidgetData(targetModelId);
                }
                if (whereType == "0") {
                    if (whereBlockType == "0") {
                        //满足所有
                        this.container.find("input[name*='data-filter-whereType'][value='0']").prop("checked", true);
                    } else {
                        //满足任一
                        this.container.find("input[name*='data-filter-whereType'][value='1']").prop("checked", true);
                    }
                    //自定义
                    var $table = this.container.find(".data-filter-content").find("table");
                    var whereWgt = new whereGenerator({
                        container: $table,
                        parent: this,
                        data: whereObj,
                        wheretype: "custom_query"
                    });
                    whereWgt.startup();
                    whereWgt.draw();

                    self.whereCollection.push(whereWgt);
                } else {
                    if (whereBlockType == "0") {
                        //满足所有
                        this.container.find("input[name*='sys-filter-whereType'][value='0']").prop("checked", true);
                    } else {
                        //满足任一
                        this.container.find("input[name*='sys-filter-whereType'][value='1']").prop("checked", true);
                    }
                    //内置
                    var $table = this.container.find(".data-filter-content-sys").find("table");
                    var whereWgt = new whereGenerator({
                        container: $table,
                        parent: this,
                        data: whereObj,
                        wheretype: "sys_query"
                    });
                    whereWgt.startup();
                    whereWgt.draw();

                    self.sysWhereCollection.push(whereWgt);
                }
            }
        },
        doRenderDisplay: function (xformId, index) {
            var self = this;
            this.container.find(".no-target-model").css("display", "none");
            var $DisplayContainer = this.container.find(".display-field");
            $DisplayContainer.removeClass("no-target-model-temp");
            $DisplayContainer.html("");
            $DisplayContainer.append(formulaBuilder.get_style1(index + "_displayField", "String", null, null, xformId));
        },
        doRenderTargetModel: function () {
            var self = this;
            this.container.find(".targetModel").off("click").on("click", function () {
                var $dom = $(this);
                var appId = "";     //跨应用，不需要传id
                var index = $dom.closest(".otherItem").index();         //当前节点的index
                dialog.iframe("/sys/modeling/base/relation/import/model_select.jsp?appId=" + appId, modelingLang['behavior.select.form'],
                    function (value) {
                        if (value) {
                            // if (self.parent.parent.parent.type != "treeView") {
                            //     if (value.fdId != self.targetModelId &&
                            //         self.parent.parent.otherNodeIdCollection.indexOf(value.fdId) > -1) {
                            //         dialog.alert("所选表单已有节点内容配置，请返回设置或重新选择");
                            //         return;
                            //     }
                            // }
                            //初始化目标表单数据
                            self.initTargetInfo(value.fdId, index);
                            //初始化目标表单数据成功之后再回显
                            $dom.find(".selectedItem").html(value.fdName);
                            var text = $dom.find(".selectedItem").find(".com_subject").text();
                            //更新树形视图 —— 上级节点的下拉框数据
                            if(self.parent.parent.otherTreeNodeCollection[index]){
                                self.parent.parent.otherTreeNodeCollection[index].value = value.fdId;
                                self.parent.parent.otherTreeNodeCollection[index].name = text;
                            }else{
                                self.parent.parent.otherTreeNodeCollection.push({"value":value.fdId,"name":text});
                            }
                            topic.channel("modelingMindMapOtherNode").publish("targetSoureData.change", {
                                "index":index,
                                "value":value.fdId,
                                "name":text
                            })
                            $dom.find("[name='fdTargetModelId']").val(value.fdId);
                            $dom.find("[name='fdTargetModelName']").val(text);
                        }
                    }, {
                        width: 1010,
                        height: 600
                    });
            });
        },
        initTargetInfo: function (modelId, index) {
            var self = this;
            var url = Com_Parameter.ContextPath + "sys/modeling/base/modeling.do?method=getModelWidget&modelId=" + modelId;
            $.ajax({
                url: url,
                type: "get",
                async: false,
                success: function (data, status) {
                    if (data && JSON.parse(data).code == "200") {
                        topic.channel("modelingMindMapOtherNode").publish("targetSoureData.load", {
                            "data": JSON.parse(data),
                            "modelId": modelId,
                            "index": index
                        });
                        self.targetModelData = JSON.parse(data).data;
                        self.dataSourceId = modelId;
                        self.doConnectRoot(JSON.parse(data).xformId, self.itemIndex);
                        self.doRenderDisplay(JSON.parse(data).xformId, self.itemIndex);
                        self.getViews(modelId);
                        self.getViewFlowInfo(modelId);
                        //查询条件
                        self.clearWhere();
                        self.getWidgetData(modelId);
                        self.doRenderWhereBlock(modelId);
                        //树形视图——排序字段
                        if(self.parent.parent.parent.type == "treeView"){
                            self.buildMatchRelation();
                            self.buildOrderBlock(modelId);
                            self.clearView();
                        }
                    }else{
                        dialog.alert(modelingLang['modelingMindMap.target.for.data.error'])
                    }
                }
            });
        },
        bindEvent: function () {
            var self = this;
            //返回
            this.container.find(".returnNodeCfg").off("click").on("click", function () {
                var isRoot = $(this).closest(".nodeSettingTable").find("[name='fdIsConnectRoot']:checked").val();
                var fdTargetModelName = $(this).closest(".nodeSettingTable").find("[name='fdTargetModelName']").val();
                var fdPreNode = [];
                self.doClearPreNode();
                var targetModelId = self.container.find("[name='fdTargetModelId']").val();
                //#144518未发版的旧数据问题：保存的数据如果有检验不通过的话会导致页面问题
                var width = $("#mindMapEdit").width()+8;
                var height = $("#mindMapEdit").height();
                self.container.css("width",width+"px");
                self.container.css("height",height+"px");
                //#144518未发版的旧数据问题：保存的数据如果有检验不通过的话会导致页面问题
                if (!targetModelId) {
                    dialog.alert(modelingLang['modelingMindMap.select.target.form']);
                    return;
                }
                self.targetModelId = targetModelId;
                self.targetModelName = fdTargetModelName;
                if (isRoot != "1") {
                    //没连接根节点，必须新增一个上级
                    if (self.preNodeSettingCollection.length == 0) {
                        dialog.alert(modelingLang['modelingMindMap.root.node.notConnected']);
                        return;
                    }
                }
                for (var i = 0; i < self.preNodeSettingCollection.length; i++) {
                    var data = self.preNodeSettingCollection[i].getKeyData();
                    if (data.fdPreModelId == "" && isRoot != "1") {
                        dialog.alert(modelingLang['modelingMindMap.root.node.notConnected']);
                        return;
                    }
                    fdPreNode.push(data);
                }
                if (!self.parent.verifyLoop()) {
                    dialog.alert(modelingLang['modelingMindMap.current.nodes.configured.loops'])
                    return;
                }

                self.container.css("display", "none");
                self.parent.doReShow(fdPreNode, isRoot, fdTargetModelName);
            })
        },
        doClearPreNode: function () {
            var self = this;
            for (var i = 0; i < self.preNodeSettingCollection.length; i++) {
                var wgt = self.preNodeSettingCollection[i];
                if (self.preNodeSettingCollection[i].getKeyData().fdPreModelId == "") {
                    self.preNodeSettingCollection.splice(i, 1);
                    i--;
                    wgt.destroy();
                }
            }
        },
        sortable: function (type) {
            var list = $("#" + type)[0];
            var self = this;
            Sortable.create(list, {
                sort: true,
                scroll: true,
                touchStartThreshold: 0,
                swapThreshold: 1,
                animation: 150,
                handle: ".sortableIcon",
                draggable: ".sortItem",
                onStart: function (evt) {
                    console.log(evt);
                },
                onEnd: function (evt) {
                    self.refreshWhenSort(evt);
                    topic.publish("preview.refresh", {key: self.channel});
                }
            });
        },
        /**
         * 拖动重新排序组件数据跟刷新对应的index
         * @param evt
         */
        refreshWhenSort: function (evt) {
            var sortItem = evt.item;
            var context = $(sortItem).closest("[data-table-type]");
            var type = context.attr("data-table-type");
            var srcWgts = [];
            if (type === "preNode") {
                srcWgts = this.preNodeSetting;
            }
            var targetWgts = [];
            context.find(".sortItem").each(function (index, obj) {
                var oldIndex = parseInt($(obj).attr("index"));
                targetWgts.push(srcWgts[oldIndex]);
            });
            if (type === "otherNode") {
                this.refreshOrderIndex();
                this.preNodeSetting = targetWgts;
            }
        },
        refreshOrderIndex: function () {
            $("#otherNode").find(".sortItem").each(function (index, item) {
                $(item).attr("index", index);
                $(item).find("[data-lui-position]").each(function (i, Obj) {
                    $(Obj).attr("data-lui-position", "fdParentNode-" + index);
                });
            });
        },
        delWhereTr: function (evt) {
            var self = this;
            if (evt) {
                $dom = evt.dom;
                type = evt.type;
            }
            if (type == 'where') {
                var $tb = $dom.closest("table");
                var $tr = $dom.closest("tr");
                var curIndex = $($tr).index();
                var whereType = $($tb).attr("name");
                if (whereType == "custom_query") {
                    //自定义
                    var wgt = this.whereCollection[curIndex];
                    this.whereCollection.splice(curIndex, 1);
                    topic.channel("modeling").unsubscribe("field.change", wgt.fieldChange, wgt);
                    wgt.destroy();
                    return;
                } else if (whereType == "sys_query") {
                    //内置
                    var wgt = this.sysWhereCollection[curIndex];
                    this.sysWhereCollection.splice(curIndex, 1);
                    topic.channel("modeling").unsubscribe("field.change", wgt.fieldChange, wgt);
                    wgt.destroy();
                    return;
                }
            }
            // $tr.remove();
        },
        getKeyData: function (evt) {
            var self = this;
            var fdOtherNode = {};
            fdOtherNode.nodeSettingId = this.nodeSettingId;
            fdOtherNode.fdTargetModelId = self.container.find("[name='fdTargetModelId']").val();
            fdOtherNode.fdTargetModelName = self.container.find("[name='fdTargetModelName']").val();
            fdOtherNode.fdIsConnectRoot = self.container.find("[name='fdIsConnectRoot']:checked").val();
            fdOtherNode.connectRootFormula = self.container.find("input[type='hidden'][name*='connectRootFormula']").val();
            fdOtherNode.connectRootFormulaText = self.container.find("input[type='text'][name*='connectRootFormula_name']").val();
            fdOtherNode.fdPreNode = [];
            for (var i = 0; i < this.preNodeSettingCollection.length; i++) {
                fdOtherNode.fdPreNode.push(this.preNodeSettingCollection[i].getKeyData());
            }
            fdOtherNode.fdNodeName = self.container.find("input[type='hidden'][name*='displayField']").val();
            fdOtherNode.fdNodeNameText = self.container.find("input[type='text'][name*='displayField_name']").val();
            fdOtherNode.fdOtherNodeViewEnable = self.container.find("[name='fdOtherNodeViewEnable']").val();
            fdOtherNode.fdOtherNodeView = self.container.find("#fdOtherNodeView option:selected").val();
            fdOtherNode.isFlow = self.container.find("[name='fdOtherNodeViewFlow']").val();
            fdOtherNode.fdWhereBlock = [];
            for (var i = 0; i < this.sysWhereCollection.length; i++) {
                var where = this.sysWhereCollection[i].getKeyData();
                where.whereBlockType = self.container.find("[name*='sys-filter-whereType']:checked").val();
                fdOtherNode.fdWhereBlock.push(where);
            }
            for (var i = 0; i < this.whereCollection.length; i++) {
                var where = this.whereCollection[i].getKeyData();
                where.whereBlockType = self.container.find("[name*='data-filter-whereType']:checked").val();
                fdOtherNode.fdWhereBlock.push(where);
            }
            return fdOtherNode;
        },
        initByStoreData: function (config) {
            var self = this;
            if(config.nodeSettingId){
                this.nodeSettingId = config.nodeSettingId;
            }else{
                this.nodeSettingId = config.fdTargetModelId;
            }
            //目标表单
            self.container.find("[name='fdTargetModelId']").val(config.fdTargetModelId);
            self.initTargetInfo(config.fdTargetModelId, self.itemIndex);
            self.container.find("[name='fdTargetModelName']").val(config.fdTargetModelName);
            var targetModelHtml = "<span class=\"com_subject\">" + config.fdTargetModelName + "</span>";
            self.container.find(".target-form-content .selectedItem").html(targetModelHtml)
            //连接根
            var checkBox = "[name='fdIsConnectRoot'][value='" + config.fdIsConnectRoot + "']";
            // self.container.find(checkBox).prop("checked",true);
            self.container.find(checkBox).trigger("click");

            //连接根节点配置
            var RootformulaHtml = "[name='" + self.itemIndex + "_connectRootFormula']";
            self.container.find(".connect-root-config").find(RootformulaHtml).val(config.connectRootFormula);
            var RootformulaText = "[name='" + self.itemIndex + "_connectRootFormula_name']";
            self.container.find(".connect-root-config").find(RootformulaText).val(config.connectRootFormulaText);
            //上级
            var $preNodeContainer = self.container.find(".connect-parent-node");
            var preData = config.fdPreNode;
            for (var i = 0; i < preData.length; i++) {
                var cfg = preData[i];
                this.drawPreNode($preNodeContainer, cfg);
            }
            //显示字段
            var formulaHtml = "[name='" + self.itemIndex + "_displayField']";
            self.container.find(".display-field").find(formulaHtml).val(config.fdNodeName);
            var formulaText = "[name='" + self.itemIndex + "_displayField_name']";
            self.container.find(".display-field").find(formulaText).val(config.fdNodeNameText);

            //穿透
            if (self.container.find("[name='fdOtherNodeViewEnable']").val() != config.fdOtherNodeViewEnable) {
                self.container.find(".other-node-view").find("[type='checkbox']").trigger("click");
                var curHtml = "option[value='" + config.fdOtherNodeView + "']";
                self.container.find(".fdOtherNodeView").find(curHtml).attr("selected", true);
            }
            //查询条件
            self.doInitWhere(config.fdWhereBlock,config.fdTargetModelId);

        }
    });
    //数据过滤——删除行
    window.delTr = function (dom, type) {
        topic.publish("modeling.mindMap.delWhereTr", {dom: dom, type: type});
    };
    window.whereFieldGenerator = {
        modelDict: function (widgets) {
            var dlgWidgets = [];
            // var widgets = listviewOption.widgets;
            for (var key in widgets) {
                if (widgets[key].type.indexOf("Attachment") > -1) {
                    //附件过滤
                    continue
                }
                var dlgItem = {
                    "fieldText": widgets[key].label,
                    "field": key,
                    "businessType":widgets[key].businessType
                };
                if (widgets[key].enumValues) {
                    dlgItem.fieldType = "enum";
                    dlgItem.enumValues = [];
                    var enumValStrArr = widgets[key].enumValues.split(";");
                    for (var i = 0; i < enumValStrArr.length; i++) {
                        var enumVal = enumValStrArr[i].split("|");
                        dlgItem.enumValues.push({
                            fieldEnumLabel: enumVal[0],
                            fieldEnumValue: enumVal[1]
                        })
                    }
                } else {
                    dlgItem.fieldType = widgets[key].type;
                }
                dlgWidgets.push(dlgItem);
            }
            if (!listviewOption.hasOwnProperty('baseInfo')) {
                listviewOption.baseInfo = {};
            }
            listviewOption.baseInfo.modelDict = dlgWidgets;
            listviewOption.baseInfo.fieldInfos = dlgWidgets;
            return dlgWidgets;
        }
    };
    window.triggleSelectdatetime = function (event, dom, type, name) {
        var input = $(dom).find("input[name='" + name + "']");
        if (type == "DateTime") {
            selectDateTime(event, input);
        } else if (type == "Date") {
            selectDate(event, input);
        } else {
            selectTime(event, input);
        }
    };
    module.exports = OtherNodeSetting;
})