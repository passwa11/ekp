/**
 * 其他节点设置
 */
define(function(require, exports, module) {

    var $ = require('lui/jquery'),
        topic = require('lui/topic'),
        base = require('lui/base'),
        otherNodeSetting = require('sys/modeling/base/views/business/res/otherNodeSetting'),
        treeViewOrderGenerator = require('sys/modeling/base/views/tree/res/treeViewOrderGenerator'),
        incParamsGenerator = require('sys/modeling/base/views/tree/res/incParamsGenerator'),
        dialog = require('lui/dialog');
    var modelingLang = require("lang!sys-modeling-base");
    var OtherNodeSetting4Tree = otherNodeSetting.extend({
        initProps: function($super, cfg) {
            $super(cfg);
            this.orderCollection = [];
            this.incParamsCollection = [];
            this.drawTreeView();
        },
        drawTreeView : function(){
            var self = this;
            //上级节点
            self.buildPreModel4Tree();
            //匹配关系
            self.buildMatchRelation();
            //视图穿透
            self.renderView();
            topic.channel("modelingMindMapOtherNode").subscribe("targetSoureData.change",this.changePreSelect,this);
        },
        buildOrderBlock : function(modelId){
            var self = this;
            var modelId = modelId;
            self.clearOrder();
            self.getWidgetData(modelId);
            self.container.find(".orderBlock-table").find(".model-data-create").off("click").on("click",function(){
                var $table = $(this).closest("tr").next().find(".model-edit-view-oper-content-table");
                $table.parent().show();
                var type = $table.attr("data-table-type");
                if(type === "order"){
                    var orderWgt = new treeViewOrderGenerator({container:$table,parent:self,option:self.fieldOptions});
                    orderWgt.startup();
                    orderWgt.draw();
                    self.orderCollection.push(orderWgt);
                }
            });
        },
        clearOrder : function(){
            var self = this;
            for(var i = 0;i < this.orderCollection.length;i++){
                var wiget = this.orderCollection[i];
                this.orderCollection.splice(i,1);
                wiget.destroy();
                i--;
            }
        },
        /*getWidgetData :function(modelId){
        //抽离到父级组件中，otherNodeSetting.js
            var self = this;
            var url = Com_Parameter.ContextPath + "sys/modeling/base/modeling.do?method=getModelWidget&modelId=" + modelId;
            $.ajax({
                url: url,
                type: "get",
                async: false,
                success: function (data, status) {
                    if(data){
                        window.whereFieldGenerator.modelDict(JSON.parse(data).data);
                    }
                }
            });
        },*/
        clearView : function(){
            var self = this;
            for (var i = 0; i < this.incParamsCollection.length; i++) {
                var wiget = this.incParamsCollection[i];
                this.incParamsCollection.splice(i, 1);
                wiget.destroy();
                i--;
            }
            self.container.find(".model-mind-map-view").find(".incparams").remove();
            self.container.find(".model-mind-map-view .item-content").find(".listViewText").text("");
            self.container.find(".model-mind-map-view .item-content").find(".listViewText").attr("title","");
            self.container.find("[name='fdTreeNoteViewAppId']").val("");
            self.container.find("[name='fdTreeNoteViewModelId']").val("");
            self.container.find("[name='fdTreeNoteViewFdId']").val("");
            self.container.find("[name='fdTreeNoteViewModelName']").val("");
            self.container.find("[name='fdTreeNoteViewType']").val("");
            self.container.find("[name='fdTreeNodeViewIncParamsSubTableId']").val("");
        },
        renderView : function(){
            var self = this;
            //视图穿透的切换事件
            self.container.find(".choice-tree-note-view").find("li").off("click").on("click",function () {
                var type = $(this).attr("name");
                if(type == "view"){
                    $(this).addClass("active");
                    $(this).siblings().removeClass("active");
                    self.container.find(".model-mind-map-view").css("display","block");
                    self.container.find(".model-mind-map-link").css("display","none");
                }else if(type == "link"){
                    $(this).addClass("active");
                    $(this).siblings().removeClass("active");
                    self.container.find(".model-mind-map-view").css("display","none");
                    self.container.find(".model-mind-map-link").css("display","block");
                }
            })
            //视图穿透——选择视图点击事件
            self.container.find(".model-mind-map-view").find(".listVieElement").off("click").on("click",function(){
                var url = "/sys/modeling/base/resources/js/dialog/leftNav/leftNavDialog.jsp?isTodo=false";
                var appId = listviewOption.flowInfo.appId;          //appId在treeView_edit.jsp页面定义
                dialog.iframe(url,modelingLang['modelingTreeView.select.tree.node.view'],function(rtn){
                    if(rtn && rtn.data){
                        self.container.find(".listViewText").text(rtn.data.text);
                        self.container.find(".listViewText").attr("title",rtn.data.text);
                        self.container.find(".listViewText").css("color","#333333");
                        if(rtn.data.text){
                            let regex = /\((.+?)\)/g;
                            let options = (rtn.data.text).match(regex);
                            switch (options[0]) {
                                case "(思维导图)":
                                    self.container.find("[name='fdTreeNoteViewType']").val("mindMap");
                                    break;
                                case "(甘特图)":
                                    self.container.find("[name='fdTreeNoteViewType']").val("gantt");
                                    break;
                                case "(资源面板)":
                                    self.container.find("[name='fdTreeNoteViewType']").val("resPanel");
                                    break;
                                case "(旧版列表)":
                                    self.container.find("[name='fdTreeNoteViewType']").val("listView");
                                    break;
                                case "(日历视图)":
                                    self.container.find("[name='fdTreeNoteViewType']").val("calendar");
                                    break;
                                default:
                                    //普通列表
                                    self.container.find("[name='fdTreeNoteViewType']").val("collection");
                            }
                        }
                        self.container.find("[name='fdTreeNoteViewAppId']").val(rtn.appId);
                        self.container.find("[name='fdTreeNoteViewModelId']").val(rtn.leftValue);
                        self.container.find("[name='fdTreeNoteViewFdId']").val(rtn.data.value);
                        self.container.find("[name='fdTreeNoteViewModelName']").val(rtn.data.text);
                        if(rtn.data.value && self.dataSourceId){
                            //入参
                            self.container.find(".otherNodeView .model-mind-map-view").find(".incparams").remove();
                            var incParamsWgt = new incParamsGenerator({container:self.container.find(".otherNodeView .model-mind-map-view"),parent:self,targetModelData:self.targetModelData,modelId:self.dataSourceId});
                            incParamsWgt.onFdListviewChange(rtn.data.value);
                            self.incParamsCollection.push(incParamsWgt);
                        }
                    }
                },{
                    width : 800,
                    height : 500,
                    params : {
                        "cateBean" : "modelingAppModelService",
                        "fdAppId" : appId,
                        "dataBean" : "modelingTreeViewService&modelId=!{value}"
                    }
                });
            });
            //视图穿透——自定义链接
            self.container.find(".model-mind-map-link").find("[name='fdLink']").on("blur",function () {
                const linkRe = /^(?:http(s)?:\/\/)?[\w.-]+(?:\.[\w\.-]+)+[\w\-\._~:/?#[\]@!\$&'\(\)\*\+,;=.]+$/
                if($(this).val() != "" && !linkRe.test($(this).val())){
                    self.container.find(".validation-container").remove();
                    //超长提示框
                    self.container.find(".model-mind-map-link").append($("<div class='validation-container'><div class=\"validation-advice\" _reminder=\"true\">" +
                        "<table class=\"validation-table\"><tbody><tr><td>" +
                        "<div class=\"lui_icon_s lui_icon_s_icon_validator\"></div></td>" +
                        "<td class=\"validation-advice-msg\"><span class=\"validation-advice-title\">"+modelingLang['modelingAppViewtab.fdLinkParams']+"</span>" +
                        modelingLang['modelingTreeView.format.error.re-enter']+"</td></tr></tbody></table></div></div>"));
                }
            });
            self.container.find(".model-mind-map-link").find("[name='fdLink']").on("focus",function () {
                $(this).closest(".model-mind-map-link").find(".validation-container").remove();
            });
        },
        buildPreModel4Tree : function(){
            var self = this;
            var html = "";
            for(var i = 0;i < this.parent.parent.otherTreeNodeCollection.length;i++){
                var model = this.parent.parent.otherTreeNodeCollection[i];
                if(self.itemIndex == (i+1)){
                    continue;
                }
                // html += "<option value='"+model.value+"' data-index='"+(i+1)+"'>"+model.name+"(节点"+(i+1)+")</option>";
                html += "<option value='"+model.value+"' data-index='"+(i+1)+"'>"+model.name+"</option>";
            }
            this.container.find(".tree-view-select").append(html);
            //事件
            this.container.find(".tree-view-select").off("change").on("change",function(){
                $(this).closest(".tree-view-note-select").find(".validation-container").remove();
                self.initPreNodeModelInfo($(this).val());
                self.buildMatchRelation();
            })
        },
        /**
         * 树节点目标表单改变时，更新其他树节点的上级节点和回显的显示值
         * @param rtn
         */
        changePreSelect : function(rtn){
            var self = this;
            //更新上级节点的下拉框
            $(".tree-view-select option").each(function () {
                if($(this).attr("data-index") == rtn.index + 1){
                    $(this).val(rtn.value);
                    // $(this).text(rtn.name+"(节点"+(rtn.index + 1)+")");
                    $(this).text(rtn.name);
                }
                var select = $(this).attr("selected");
            })
            //上级节点选中的节点更改了目标表单，也要更新上级节点的匹配条件
            $(".tree-view-select option:selected").each(function () {
                var self = this;
                if($(this).attr("data-index") == rtn.index + 1){
                    var url =  Com_Parameter.ContextPath + "sys/modeling/base/modeling.do?method=getModelWidget&modelId=" + rtn.value;
                    $.ajax({
                        url: url,
                        type: "get",
                        async: false,
                        success: function (data, status) {
                            if (data) {
                                var preData = JSON.parse(data).data;
                                $(self).closest("tr").find(".match-relation .target-model-field-option").html("<option value=''>"+modelingLang['modeling.page.choose']+"</option>");
                                var preHtml = "";
                                for(var key in preData){
                                    preHtml += "<option value='"+key+"'>"+preData[key].label+"</option>";
                                }
                                $(self).closest("tr").find(".match-relation .target-model-field-option").append(preHtml);
                            }
                        }
                    });
                }
            })
            //更新树节点回显文本
            $(".other-node-re-show .content-tabs li").each(function () {
                if($(this).attr("data-index") == rtn.index + 1){
                    // $(this).text(rtn.name+"(节点"+(rtn.index + 1)+")");
                    $(this).text(rtn.name);
                }
            })
        },
        initPreNodeModelInfo : function (modelId) {
            var self = this;
            var url =  Com_Parameter.ContextPath + "sys/modeling/base/modeling.do?method=getModelWidget&modelId=" + modelId;
            $.ajax({
                url: url,
                type: "get",
                async: false,
                success: function (data, status) {
                    if (data) {
                        self.drawPreModelField(JSON.parse(data).data);
                        self.buildMatchRelation();
                    }
                }
            });
        },
        drawPreModelField:function(predata){
            var self = this;
            if(predata){
                //当前节点
                if(!$.isEmptyObject(self.targetModelData)){
                    self.container.find(".cur-model-field-option").removeAttr("disabled",true);
                    self.container.find(".cur-model-field-option").removeClass("no-target-field-model");
                    self.container.find(".cur-model-field-option").find("option").eq(0).text(modelingLang['modeling.page.choose']);
                }
                //上级节点
                self.container.find(".target-model-field-option").removeAttr("disabled");
                self.container.find(".target-model-field-option").removeClass("no-target-field-model");
                self.container.find(".target-model-field-option").html("<option value=''>"+modelingLang['modeling.page.choose']+"</option>");
                var preHtml = "";
                for(var key in predata){
                    preHtml += "<option value='"+key+"'>"+predata[key].label+"</option>";
                }
                self.container.find(".target-model-field-option").append(preHtml);
            }else{
                //置灰上级节点
                self.container.find(".target-model-field-option").attr("disabled",true);
                self.container.find(".target-model-field-option").addClass("no-target-field-model");
                self.container.find(".target-model-field-option").find("option").eq(0).text(modelingLang['modelingTreeView.superior.node.form.field']);
                //将当前节点置灰
                self.container.find(".cur-model-field-option").attr("disabled",true);
                self.container.find(".cur-model-field-option").addClass("no-target-field-model");
                self.container.find(".cur-model-field-option").find("option").eq(0).text(modelingLang['modelingTreeView.this.node.form.field']);
            }
        },
        buildMatchRelation : function(){
            var self = this;
            var preNode = self.container.find(".tree-view-select option:selected").attr("value");
            if(preNode && preNode != "0"){
                //目标表单字段
                var data =  self.targetModelData;
                if(!$.isEmptyObject(data)){
                    self.container.find(".cur-model-field-option").removeAttr("disabled");
                    self.container.find(".cur-model-field-option").removeClass("no-cur-field-model");
                    self.container.find(".cur-model-field-option").html("");
                    var html = "<option value=''>"+modelingLang['modeling.page.choose']+"</option>";
                    for(var key in data){
                        html += "<option value='"+key+"'>"+data[key].label+"</option>";
                    }
                    self.container.find(".cur-model-field-option").append(html);
                }
            }
        },
        bindEvent: function () {
            var self = this;
            //树形视图返回按钮
            this.container.find(".returnNodeCfg4Tree").off("click").on("click",function () {
                //校验上级节点
                var preValue = $(this).closest(".nodeSettingTable").find(".tree-view-select option:selected").attr("value");
                if(preValue == ""){
                    $(".validation-container").remove();
                    $(this).closest(".nodeSettingTable").find(".tree-view-note-select").append($("<div class='validation-container'><div class=\"validation-advice\" _reminder=\"true\">" +
                        "<table class=\"validation-table\"><tbody><tr><td>" +
                        "<div class=\"lui_icon_s lui_icon_s_icon_validator\"></div></td>" +
                        "<td class=\"validation-advice-msg\"><span class=\"validation-advice-title\">"+modelingLang['modelingTreeView.superior.node']+"</span>" +
                        modelingLang['kmReviewMain.notNull']+"</td></tr></tbody></table></div></div>"));
                    return;
                }else{
                    //校验匹配字段
                    if(preValue != "0"){
                        //选择其他节点时，需要设置匹配关系
                        var curValue = $(this).closest(".nodeSettingTable").find(".cur-model-field-option option:selected").attr("value");
                        var preValue = $(this).closest(".nodeSettingTable").find(".target-model-field-option option:selected").attr("value");
                        if(!curValue || !preValue){
                            dialog.alert(modelingLang['modelingMindMap.node.relationship.cannot.null']);
                            return;
                        }
                    }
                }
                var width = $("#mindMapEdit").width()+8;
                var height = $("#mindMapEdit").height();
                self.container.css("width",width+"px");
                self.container.css("height",height+"px");
                var fdTargetModelName = $(this).closest(".nodeSettingTable").find("[name='fdTargetModelName']").val();
                var fdPreNode = $(this).closest(".nodeSettingTable").find(".tree-view-select option:selected").text();
                var preNodeIndex = $(this).closest(".nodeSettingTable").find(".tree-view-select option:selected").attr("data-index");
                self.container.css("display","none");
                self.parent.doReShow4Tree(fdPreNode,fdTargetModelName,preNodeIndex);
                //树形视图点击节点的返回，将头部切换栏显示
                $(".model-edit-view-bar").show();
            });
        },
        getKeyData : function (evt){
            var self = this;
            var fdOtherNode = {};
            //当前节点、数据来源
            fdOtherNode.fdTargetModelId = self.container.find("[name='fdTargetModelId']").val();
            fdOtherNode.fdTargetModelName = self.container.find("[name='fdTargetModelName']").val();
            fdOtherNode.fdIndex = self.itemIndex + "";
            //上级节点
            fdOtherNode.fdPreModelId = self.container.find(".tree-view-select option:selected").attr("value");
            fdOtherNode.fdPreModelName = self.container.find(".tree-view-select option:selected").text();
            fdOtherNode.fdPreIndex = self.container.find(".tree-view-select option:selected").attr("data-index");
            //匹配关系
            fdOtherNode.fdCurModelField = self.container.find(".cur-model-field-option option:checked").val();
            fdOtherNode.fdCurModelFieldName =  self.container.find(".cur-model-field-option option:checked").text();
            fdOtherNode.fdTargetModelField =  self.container.find(".target-model-field-option option:checked").val();
            fdOtherNode.fdTargetModelFieldName = self.container.find(".target-model-field-option option:checked").text();
            fdOtherNode.fdMatchType = "=";
            //显示字段
            fdOtherNode.fdNodeName = self.container.find("input[type='hidden'][name*='displayField']").val();
            fdOtherNode.fdNodeNameText = self.container.find("input[type='text'][name*='displayField_name']").val();
            //视图穿透
            fdOtherNode.fdTreeViewType = self.container.find(".choice-tree-note-view ul li.active").attr("value");
            fdOtherNode.fdTreeViewAppId = self.container.find("[name='fdTreeNoteViewAppId']").val();
            fdOtherNode.fdTreeViewModelType = self.container.find("[name='fdTreeNoteViewType']").val();
            fdOtherNode.fdTreeViewModelId = self.container.find("[name='fdTreeNoteViewModelId']").val();
            fdOtherNode.fdTreeViewFdId = self.container.find("[name='fdTreeNoteViewFdId']").val();
            fdOtherNode.fdTreeViewModelName = self.container.find("[name='fdTreeNoteViewModelName']").val();
            fdOtherNode.fdTreeViewLink = self.container.find("[name='fdLink']").val();
            //查询条件
            fdOtherNode.fdWhereBlock = [];
            for(var i = 0;i < this.sysWhereCollection.length;i++){
                var where = this.sysWhereCollection[i].getKeyData();
                where.whereBlockType = self.container.find("[name*='sys-filter-whereType']:checked").val();
                fdOtherNode.fdWhereBlock.push(where);
            }
            for(var i = 0;i < this.whereCollection.length;i++){
                var where = this.whereCollection[i].getKeyData();
                where.whereBlockType = self.container.find("[name*='data-filter-whereType']:checked").val();
                fdOtherNode.fdWhereBlock.push(where);
            }
            //排序字段
            fdOtherNode.fdOrder = [];
            for(var i = 0;i < this.orderCollection.length;i++){
                var orderWgt = this.orderCollection[i];
                fdOtherNode.fdOrder.push(orderWgt.getKeyData());
            }
            //入参
            fdOtherNode.fdTreeViewListViewIncParams = [];
            for(var i = 0;i < this.incParamsCollection.length;i++){
                var incParamsWgt = this.incParamsCollection[i];
                fdOtherNode.fdTreeViewListViewIncParams = incParamsWgt.getKeyData();
            }
            return fdOtherNode;

        },
        initByStoreData: function (config) {
            var self = this;
            //目标表单
            self.container.find("[name='fdTargetModelId']").val(config.fdTargetModelId);
            self.initTargetInfo(config.fdTargetModelId, self.parent.itemIndex);
            self.container.find("[name='fdTargetModelName']").val(config.fdTargetModelName);
            var targetModelHtml = "<span class=\"com_subject\">" + config.fdTargetModelName + "</span>";
            self.container.find(".target-form-content .selectedItem").html(targetModelHtml)
            //上级节点
            self.container.find(".tree-view-select option[data-index="+config.fdPreIndex+"]").prop("selected",true);
            self.initPreNodeModelInfo(config.fdPreModelId);
            //目标表单字段
            var curHtml = "option[value='"+config.fdCurModelField+"']";
            self.container.find(".cur-model-field-option").find(curHtml).attr("selected",true);
            //上级表单字段
            var tarHtml = "option[value='"+config.fdTargetModelField+"']";
            self.container.find(".target-model-field-option").find(tarHtml).attr("selected",true);
            //显示字段
            var formulaHtml = "[name='" + self.itemIndex + "_displayField']";
            self.container.find(".display-field").find(formulaHtml).val(config.fdNodeName);
            var formulaText = "[name='" + self.itemIndex + "_displayField_name']";
            self.container.find(".display-field").find(formulaText).val(config.fdNodeNameText);
            //视图穿透
            self.container.find(".choice-tree-note-view ul li[value='"+config.fdTreeViewType+"']").trigger("click");
            self.container.find("[name='fdTreeNoteViewAppId']").val(config.fdTreeViewAppId);
            self.container.find("[name='fdTreeNoteViewModelId']").val(config.fdTreeViewModelId);
            self.container.find("[name='fdTreeNoteViewFdId']").val(config.fdTreeViewFdId);
            self.container.find("[name='fdTreeNoteViewModelName']").val(config.fdTreeViewModelName);
            self.container.find("[name='fdTreeNoteViewType']").val(config.fdTreeViewModelType);
            self.container.find(".listViewText").text(config.fdTreeViewModelName);
            self.container.find(".listViewText").attr("title",config.fdTreeViewModelName);
            self.container.find(".listViewText").css("color","#333333");
            self.container.find("[name='fdLink']").val(config.fdTreeViewLink);
            //查询条件
            self.doInitWhere(config.fdWhereBlock,config.fdTargetModelId);
            //排序字段
            var $order = self.container.find("[data-table-type='order']");
            self.getWidgetData(config.fdTargetModelId);
            if(config.fdOrder && config.fdOrder.length){
                if(config.fdOrder.length > 0){
                    $order.parent().show();
                }
                for(var i = 0;i < config.fdOrder.length;i++){
                    var orderInfo = config.fdOrder[i];
                    var orderWgt = new treeViewOrderGenerator({container:$order,parent:this, channel: this.channel, data:orderInfo,option:this.fieldOptions});
                    orderWgt.startup();
                    orderWgt.draw();
                    self.orderCollection.push(orderWgt);
                }
            }
            //入参
            if(config.fdTreeViewListViewIncParams && config.fdTreeViewFdId && config.fdTreeViewModelId){
                var orderInfo = config.fdTreeViewListViewIncParams[i];
                var orderWgt = new incParamsGenerator({container:self.container.find(".model-mind-map-view"),parent:self,targetModelData:self.targetModelData,modelId:config.fdTargetModelId});
                orderWgt.onFdListviewChange(config.fdTreeViewFdId,config.fdTreeViewListViewIncParams);
                self.incParamsCollection.push(orderWgt);
            }
        }
    });
    module.exports = OtherNodeSetting4Tree;
})