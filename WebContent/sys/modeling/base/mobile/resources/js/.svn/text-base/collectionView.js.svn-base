/**
 * 列表视图的视图组件基类
 */
define(function(require, exports, module) {

    var $ = require('lui/jquery'),
        base = require('lui/base'),
        str = require('lui/util/str'),
        dialog = require('lui/dialog'),
        render = require('lui/view/render'),
        topic = require('lui/topic'),
        view = require('sys/modeling/base/mobile/resources/js/view'),
        collectionViewsStatisticsGenerator = require('sys/modeling/base/mobile/resources/js/collectionViewsStatisticsGenerator'),
        collectionViewsOrderGenerator = require('sys/modeling/base/mobile/resources/js/collectionViewsOrderGenerator'),
        operateGenerator = require('sys/modeling/base/mobile/resources/js/operateGenerator');
    var modelingLang = require("lang!sys-modeling-base");
    var CollectionView = view["View"].extend({

        initProps: function($super, cfg) {
            $super(cfg);
            this.container = cfg.container || null;
            this.storeData = cfg.data || {};
            this.statisticsCollection = [];
            this.orderCollection = [];
            this.operateCollection = [];
            this.displayCssSetIns = {};
            this.isRefreshSelectBlock = false;
        },

        startup : function($super, cfg) {
            $super(cfg);
            topic.channel(this.channel).subscribe("order.delete",this.deleteOrder, this);
            topic.channel(this.channel).subscribe("operate.delete", this.deleteOperate, this);
            topic.channel(this.channel).subscribe("statistics.delete", this.deleteStatistics, this);
            topic.channel(this.channel).subscribe("modeling.fdSubject.click", this.hideDropDownSelect, this);
        },

        hideDropDownSelect: function() {
            $(".select_view", this.element).each(function(index, obj){
                if ($(obj).attr("class").indexOf("hide")  < 0) {
                    $(obj).addClass("hide");
                }
            });
        },

        doRender: function($super, cfg){
            this.statisticsCollection = [];
            this.orderCollection = [];
            $super(cfg);
            this.renderSelectDialog();
            this.renderStatistics();
            this.renderOrder();
            this.renderOperate();
            this.renderCustom();
            topic.publish("preview.refresh", {"key": this.channel});
        },

        renderCustom : function() {
        },

        // 统计项
        renderStatistics: function() {
            var self = this;
            this.element.find(".model-data-create").on("mouseenter",function(){
                var $modelDataCreate = $(this);
                var $table = $modelDataCreate.closest("tr").next().find(".model-edit-view-oper-content-table");
                $table.parent().show();
                if(0 >= $modelDataCreate.find(".statistics_field_item").length){
                    var type = $table.attr("data-table-type");
                    if (type === "statistics") {
                        var $statisticsFieldItem = $("<div class='statistics_field_item'></div>");
                        var $statisticsFieldItemUl = $("<ul></ul>");
                        var options = self.filterSubTableField(listviewOption.baseInfo.modelDict);
                        for (var i = 0; i < options.length; i++) {
                            var option = options[i];
                            var $statisticsFieldItemLi = $("<li name="+option.field+" title="+option.fieldText+" >" + option.fieldText + "</li>");
                            $statisticsFieldItemLi.on("click", function () {
                                var statisticsWgt = new collectionViewsStatisticsGenerator({
                                    container: $table,
                                    field: $(this).attr("name"),
                                    parent: self,
                                    channel: self.channel
                                });
                                statisticsWgt.startup();
                                statisticsWgt.draw();
                                self.statisticsCollection.push(statisticsWgt);
                                //隐藏
                                $modelDataCreate.find(".statistics_field_item").css({
                                    "border":"0",
                                    "height":"0"
                                });
                                //刷新预览
                                topic.publish("preview.refresh", {"key": self.channel});
                                topic.publish("data-create-finish", {"table": $table, "key": self.channel});
                                self.sortable("statistics");
                                return false;
                            });
                            $statisticsFieldItemUl.append($statisticsFieldItemLi);
                        }
                        $statisticsFieldItem.append($statisticsFieldItemUl);
                        $(this).append($statisticsFieldItem);
                    }
                }

                $modelDataCreate.find(".statistics_field_item").css({
                    "border":"1px solid #DDDDDD",
                    "height":"240px"
                });
            });

            this.element.find(".model-data-create").on("mouseleave",function(){
                $(this).find(".statistics_field_item").css({
                    "border":"0",
                    "height":"0"
                });
            });

        },

        // 排序设置
        renderOrder: function() {
            var self = this;
            this.element.find(".model-data-create").on("click",function(){
                var $table = $(this).closest("tr").next().find(".model-edit-view-oper-content-table");
                $table.parent().show();
                var type = $table.attr("data-table-type");
                if(type === "order"){
                    var orderWgt = new collectionViewsOrderGenerator({container:$table,parent:self, channel: self.channel});
                    orderWgt.startup();
                    orderWgt.draw();
                    self.orderCollection.push(orderWgt);
                    //刷新预览
                    topic.publish("preview.refresh", {"key": self.channel});
                    topic.publish("data-create-finish",{"table":$table, "key": self.channel});
                    self.sortable("order");
                    return false;
                }
            });
        },

        // 操作设置
        renderOperate: function() {
            var self = this;
            this.element.find(".operationTr .model-data-create").on("click",function(){
                /*var $table = $(this).closest("tr").next().find(".model-edit-view-oper-content-table");
                var operate = new operateGenerator({container:$table,parent:self,channel: self.channel});
                operate.startup();
                operate.draw();
                self.operateCollection.push(operate);
                //刷新预览
                self.sortable("operate");
                topic.publish("preview.refresh", {"key": self.channel});
                topic.publish("data-create-finish",{"table":$table, "key": self.channel});*/

                //业务操作多选
                self.operationDialog();
            });
        },

        //业务操作弹框
        operationDialog: function(){
            console.log(" this.channel", this.channel);
            var isMobile = this.channel ==="mobile"?true : false;
            var $operate = this.container.find("#fd_pc_operate");
            if(isMobile){
                $operate = this.container.find("#fd_mobile_operate");
            }
            //业务操作弹框回调
            var self = this;
            var action = function(rtnData){
                for(var i = 0;i < rtnData.length;i++){
                    var fdId = rtnData[i].fdId;
                    var fdName = rtnData[i].fdName;
                    var fdDefType = rtnData[i].fdDefType;
                    var operateInfo = {
                        "fdId":fdId,
                        "fdName":fdName,
                        "fdDefType":fdDefType
                    };
                    var operateWgt = new operateGenerator({container: $operate, parent: self, data: operateInfo,channel: self.channel});
                    operateWgt.startup();
                    operateWgt.draw();
                    self.operateCollection.push(operateWgt);
                    self.sortable("operate");
                    //隐藏域赋值
                    self.hideBlockValue(isMobile,fdId,fdName);
                }
                topic.channel(self.channel).publish("field.change",{dom:self.lastSelect, type:self.ownerType, wgt:self, parent: self.parent, text: fdName});
                topic.publish("preview.refresh", {key: self.channel});
                $operate.parent(".model-panel-table-base").show();
            }
            //已选值
            var exceptValue = $("[name='operationIds']").val() || "";
            if (isMobile){
                exceptValue =$("[name='mobileOperationIds']").val() || "";
            }
            var currentValue = "";
            if(currentValue){
                exceptValue = exceptValue.replace(currentValue+";","");
                exceptValue = exceptValue.replace(currentValue,"");
            }
            this.dialogSelect(true,'sys_modeling_operation_selectListviewOperation','listOperationIds','listOperationNames', action, exceptValue,isMobile);
        },

        hideBlockValue : function(isMobile,fdId,fdName){
            var listOperationIdLasts = $("[name='operationIds']").val() || "";
            var listOperationNameLasts = $("[name='operationNames']").val() || "";
            if(isMobile){
                listOperationIdLasts = $("[name='mobileOperationIds']").val() || "";
                listOperationNameLasts = $("[name='mobileOperationNames']").val() || "";
            }
            var oprIdArr = listOperationIdLasts.split(";");
            var fdId = fdId;
            var fdName = fdName;
            if(fdId && listOperationIdLasts.indexOf(fdId) == -1 ){
                if(listOperationIdLasts.lastIndexOf(";") != listOperationIdLasts.length-1){
                    listOperationIdLasts += ";" + fdId + ";";
                    listOperationNameLasts += ";" + fdName + ";";
                }else{
                    listOperationIdLasts += fdId + ";";
                    listOperationNameLasts += fdName + ";";
                }
            }
            if(isMobile){
                $("[name='mobileOperationIds']").val(listOperationIdLasts);
                $("[name='mobileOperationNames']").val(listOperationNameLasts);
            }else {
                $("[name='operationIds']").val(listOperationIdLasts);
                $("[name='operationNames']").val(listOperationNameLasts);
            }
        },

        /**
         * 业务操作对话框
         *
         * exceptValue 需要排除的值，格式为字符串id;id
         */
        dialogSelect: function(mul, key, idField, nameField, action, exceptValue,isMobile){
            var rowIndex;
            var self = this;
            if(idField.indexOf('*')>-1 && window.DocListFunc_GetParentByTagName){
                var tr=DocListFunc_GetParentByTagName('TR');
                var tb= DocListFunc_GetParentByTagName("TABLE");
                var tbInfo = DocList_TableInfo[tb.id];
                rowIndex=tr.rowIndex-tbInfo.firstIndex;
            }
            var dialogCfg = listviewOption.dialogs[key];
            if(dialogCfg){
                var params='';
                params+='&isMobile=' + isMobile;
                params=encodeURI(params);
                var tempUrl = 'sys/modeling/base/resources/jsp/dialog_select_template.jsp?dialogType=opener&modelName=' + dialogCfg.modelName + '&_key=dialog_collection_' + idField + '&exceptValue='+exceptValue;
                if(mul==true){
                    tempUrl += '&mulSelect=true';
                }else{
                    tempUrl += '&mulSelect=false';
                }
                var dialog = new KMSSDialog(mul,true);
                dialog.URL = Com_Parameter.ContextPath + tempUrl;
                var source = dialogCfg.sourceUrl;
                var propKey = '__dialog_collection_' + idField + '_dataSource';
                dialog[propKey] = function(){
                    if(idField.indexOf('*')>-1){
                        var initField=idField.replace('*',rowIndex);
                        return {url:source+params,init:document.getElementsByName(initField)[0].value};
                    }else{
                        return {url:source+params,init:""};
                    }
                };
                window[propKey] = dialog[propKey];
                propKey =  'dialog_collection_' + idField;
                dialog[propKey] = function(rtnInfo){
                    if(rtnInfo==null) return;
                    var datas = rtnInfo.data;
                    var rtnDatas=[],ids=[],names=[];
                    for(var i=0;i<datas.length;i++){
                        var rowData = domain.toJSON(datas[i]);
                        rtnDatas.push(rowData);
                        ids.push($.trim(rowData[rtnInfo.idField]));
                        names.push($.trim(rowData[rtnInfo.nameField]));
                    }
                    if(idField.indexOf('*')>-1){
                        //明细表
                        $form(idField,idField.replace("*",rowIndex)).val(ids.join(";"));
                        $form(nameField,idField.replace("*",rowIndex)).val(names.join(";"));
                    }else{
                        //主表
                        /*self.idObj.val(ids.join(";"));
                        self.nameObj.val(names.join(";"));*/
                    }
                    if(action){
                        action(rtnDatas);
                    }
                };
                domain.register(propKey,dialog[propKey]);
                dialog.Show(800,550);
            }
        },

        sortable : function(type) {
            var list = $("#fd_" + this.id + "_" + type)[0];
            var self = this;
            Sortable.create(list,{
                sort: true,
                scroll: true,
                touchStartThreshold: 0,
                swapThreshold: 1,
                animation: 150,
                handle:".sortableIcon",
                draggable: ".sortItem",
                onStart: function(evt) {
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
        refreshWhenSort : function(evt) {
            var sortItem = evt.item;
            var context = $(sortItem).closest("[data-table-type]");
            var type = context.attr("data-table-type");
            var srcWgts = [];
            if (type === "order") {
                srcWgts = this.orderCollection;
            } else if (type === "operate") {
                srcWgts = this.operateCollection;
            } else if (type === "statistics") {
                srcWgts = this.statisticsCollection;
            }
            var targetWgts = [];
            context.find(".sortItem").each(function (index, obj){
                var oldIndex = parseInt($(obj).attr("index"));
                targetWgts.push(srcWgts[oldIndex]);
            });
            if (type === "order") {
                this.refreshOrderIndex();
                this.orderCollection = targetWgts;
            } else if (type === "operate") {
                this.refreshOperateIndex();
                this.operateCollection = targetWgts;
            } else if (type === "statistics") {
                this.refreshStatisticsIndex();
                this.statisticsCollection = targetWgts;
            }
        },

        renderConditionBlock: function() {
            var renderData = this.storeData;
            //筛选项
            if (renderData.fdCondition && renderData.fdCondition.length > 0) {
                //回调
                var $dom = this.element.find("[name='fd_" + this.channel + "_fdCondition']").closest(".multiSelectDialog");
                var textDatas = [];
                if(renderData.fdConditionText) {
                    textDatas =  renderData.fdConditionText.split(";");
                }
                this.createSelectItem($dom, renderData.fdCondition, textDatas);
            }
        },

        renderStatisticsBlock: function() {
            var renderData = this.storeData;
            //统计项
            var self = this;
            var $statistics = this.element.find("[data-table-type='statistics']");
            if(renderData.fdStatistics && renderData.fdStatistics.length){
                if(renderData.fdStatistics.length > 0){
                    $statistics.parent().show();
                }
                var $table =$statistics;
                var type = $table.attr("data-table-type");
                if (type === "statistics") {
                    var $statisticsFieldItem = $("<div class='statistics_field_item'></div>");
                    var $statisticsFieldItemUl = $("<ul></ul>");
                    var options = self.filterSubTableField(listviewOption.baseInfo.modelDict);
                    for (var i = 0; i < options.length; i++) {
                        var option = options[i];
                        var $statisticsFieldItemLi = $("<li name="+option.field+">" + option.fieldText + "</li>");
                        $statisticsFieldItemLi.on("click", function () {
                            var statisticsWgt = new collectionViewsStatisticsGenerator({
                                container: $table,
                                field: $(this).attr("name"),
                                parent: self,
                                channel: self.channel
                            });
                            statisticsWgt.startup();
                            statisticsWgt.draw();
                            self.statisticsCollection.push(statisticsWgt);
                            //隐藏
                            $statisticsFieldItem.css("display", "none")
                            //刷新预览
                            topic.publish("preview.refresh", {"mode": self.channel});
                            topic.publish("data-create-finish", {"table": $table, "mode": self.channel});
                            self.sortable("statistics");
                            return false;
                        });
                        $statisticsFieldItemUl.append($statisticsFieldItemLi);
                    }
                    $statisticsFieldItem.append($statisticsFieldItemUl)
                    $table.append($statisticsFieldItem)
                }
                $table.find(".statistics_field_item").css("display","none");
                for(var i = 0;i < renderData.fdStatistics.length;i++){
                    var statisticsInfo = renderData.fdStatistics[i];
                    var statisticsWgt = new collectionViewsStatisticsGenerator({
                        container: $statistics,
                        data:statisticsInfo,
                        field: statisticsInfo.field,
                        parent: self,
                        channel: self.channel
                    });
                    statisticsWgt.startup();
                    statisticsWgt.draw();
                    this.statisticsCollection.push(statisticsWgt);
                }
                this.sortable("statistics");
            }
        },

        renderBlock : function($super){
            $super();
            var renderData = this.storeData;
            this.renderStatisticsBlock();
            this.renderConditionBlock();
            //排序项
            var $order = this.element.find("[data-table-type='order']");
            if(renderData.fdOrder && renderData.fdOrder.length){
                if(renderData.fdOrder.length > 0){
                    $order.parent().show();
                }
                for(var i = 0;i < renderData.fdOrder.length;i++){
                    var orderInfo = renderData.fdOrder[i];
                    var orderWgt = new collectionViewsOrderGenerator({container:$order,parent:this, channel: this.channel, data:orderInfo});
                    orderWgt.startup();
                    orderWgt.draw();
                    this.orderCollection.push(orderWgt);
                }
                this.sortable("order");
            }
        },

        // 显示项/筛选项
        renderSelectDialog: function(){
            var self = this;
            this.element.find(".multiSelectDialog").on("click",function(){
                Com_EventStopPropagation();
                // 找到当前元素的值
                topic.publish("switchSelectPosition",{'dom':this, key: self.channel});
                var $dom = $(this);
                var curVal = $dom.find("input[type='hidden']").val() || "";

                var fieldInfos = self.getFieldInfos();
                var isDisplay = false;
                if($dom.attr("data-lui-position") === "fdDisplay"){
                    fieldInfos = self.filterSubTableField(fieldInfos);
                    isDisplay = true;
                }

                var mobileDialogFlag = $dom.attr("data-lui-position") || "";
                var url = "/sys/modeling/base/listview/config/dialog.jsp?";
                if (self.channel=="pc") {
                    if (isDisplay) {
                        var attachments = listviewOption.attachments || [];
                        for (var i = 0; i < attachments.length ; i++) {
                            //过滤明细表附件
                            if(attachments[i].name.indexOf(".") != -1){
                                continue;
                            }
                            var fieldinfo = {
                                field: attachments[i].name,
                                text:attachments[i].label,
                                type:attachments[i].businessType
                            };
                            fieldInfos.push(fieldinfo)
                        }
                        url += "type=display";
                    } else {
                        url += "type=condition";
                    }
                } else {
                    url += "type=normal&mobileDialogFlag="+mobileDialogFlag;
                }
                var modelDict = JSON.stringify(listviewOption.baseInfo.modelDict);
                dialog.iframe(url,modelingLang['modelingLang'],function(data){
                    if(!data){
                        return;
                    }
                    //创建者选中回显
                    data = data.replace("docCreator.fdName","docCreator");
                    data = $.parseJSON(data);
                    var selectedDatas = data.selected;
                    var textDatas = data.text;
                    // 补充text
                    if(selectedDatas.length === textDatas.length){
                        for(var i = 0;i < selectedDatas.length;i++){
                            selectedDatas[i].text = textDatas[i];
                        }
                    }
                    //回调
                    self.createSelectItem($dom, selectedDatas, textDatas);
                    //显示项样式改变事件
                    if($dom.attr("data-lui-position") === "fdDisplay") {
                        topic.publish("modeling.selectDisplay.change",{'thisObj':$dom,'data':data});
                    }
                    //刷新预览
                    topic.publish("preview.refresh", {key: self.channel});
                    topic.publish("switchSelectPosition",{'dom':$dom[0], key: self.channel});
                },{
                    width : 720,
                    height : 530,
                    params : {
                        selected : curVal,
                        allField : fieldInfos,
                        modelDict: modelDict,
                    }
                });
            });
        },

        createSelectItem : function($dom, selectedDatas, textDatas) {
            // 跟pc端的列表视图页面，保持一致
            $dom.find("input[type='hidden']").val(JSON.stringify(selectedDatas));
            $dom.find("input[type='text']").val(textDatas.join(";"));
            $dom.find(".selectedItem").empty();
            var outerWidth = $dom.outerWidth();
            for (var i = 0; i < selectedDatas.length; i++) {
                var item = selectedDatas[i];
                $dom.find(".selectedItem").append("<label class='selectedField' field='" + item.field + "'>" + item.text + "<i class='delIcon'></i></label>");
                //图标宽度：23, 共标签宽度62
                if (outerWidth  < (23 + 62 + this.calculateWidth($dom))) {
                    $dom.find("label[field='" + item.field + "']").remove();
                    break;
                }
            }
            this.createDropdownSelect($dom, $dom.find(".selectedItem")[0], selectedDatas, textDatas);
            this.bindDelClick($dom, selectedDatas, textDatas);
        },

        calculateWidth : function($dom) {
            var width = 0;
            $dom.find(".selectedField").each(function(index, obj){
                width += $(obj).outerWidth() + 4;
            });
            return width;
        },

        bindDelClick: function($dom, selectedDatas, textDatas) {
            //移除选项
            var self = this;
            $(".delIcon", $dom).click(function(){
                if ($(this).prop("tagName") != "I") return;
                Com_EventStopPropagation();
                var removeItem = $(this).parent();
                var removeField = removeItem.attr("field");
                for (var i = 0; i < selectedDatas.length; i++) {
                    if (selectedDatas[i].field === removeField) {
                        selectedDatas.splice(i, 1);
                        textDatas.splice(i, 1);
                        $dom.find("input[type='hidden']").val(JSON.stringify(selectedDatas));
                        $dom.find("input[type='text']").val(textDatas.join(";"));
                        removeItem.remove();
                        $dom.find(".selectedItem").empty();
                        var outerWidth = $dom.outerWidth();
                        //显示项样式改变事件
                        if($dom.attr("data-lui-position") === "fdDisplay") {
                            var data = {"selected": selectedDatas, "text": textDatas};
                            topic.publish("modeling.selectDisplay.change",{'thisObj':$dom[0],'data':data});
                        }
                        topic.publish("preview.refresh", {key: self.channel});
                        if (selectedDatas.length > 0) {
                            for (var j = 0; j < selectedDatas.length; j++) {
                                var item = selectedDatas[j];
                                $dom.find(".selectedItem").append("<label class='selectedField' field='" + item.field + "'>" + item.text + "<i class='delIcon'></i></label>");
                                //图标宽度：23, 共标签宽度62
                                if (outerWidth  < (23 + 62 + self.calculateWidth($dom))) {
                                    $dom.find("label[field='" + item.field + "']").remove();
                                    break;
                                }
                            }
                            self.createDropdownSelect($dom, $dom.find(".selectedItem")[0], selectedDatas, textDatas);
                            return;
                        } else {
                            $dom.find(".dropdownSelectedField ").remove();
                            $dom.find(".select_view ").remove();
                        }
                        break;
                    }
                }
            });
        },

        createDropdownSelect:  function($dom, context, selectedDatas, textDatas) {
            $(context).find(".dropdownSelectedField").remove();
            $dom.find(".select_view").remove();
            if (selectedDatas.length > 0) {
                var $total = $("<label class='dropdownSelectedField'>共" + selectedDatas.length + "</label>");
                $(context).append($total);
                var dropdownSelect = $('<div class="select_view hide">' +
                    '<div class="select_content_view" style="">' +
                    '<div class="select_search_view">' +
                    '<input class="select_keyword" name="keyword" placeholder="搜索">' +
                    '</div><ul class="select_result_view"></ul></div></div></div');
                $dom.append(dropdownSelect);
                for (var i = 0; i < selectedDatas.length; i++) {
                    var item = selectedDatas[i];
                    dropdownSelect.find(".select_result_view").append("<li class='dropDownSelectField' field='" + item.field + "' text='" + item.text + "'>" + item.text + "<i class='delIcon'></i></li>");
                }
                var self = this;
                var hideDropDownSelect = function() {
                    $(".select_view", self.element).each(function(index, obj){
                        if ($(obj).attr("class").indexOf("hide")  < 0) {
                            $(obj).addClass("hide");
                        }
                    });
                }

                //选项下拉
                $(context).find(".dropdownSelectedField").click(function(){
                    Com_EventStopPropagation();
                    var select = $dom.find(".select_view");
                    var originalClass = select.attr("class");
                    hideDropDownSelect();
                    select.attr("class", originalClass);
                    if (select.attr("class").indexOf("hide") > -1) {
                        select.removeClass("hide");
                        $total.addClass("showSelect");
                        select.css("width", $dom.width());
                    } else {
                        select.addClass("hide");
                        $total.removeClass("showSelect");
                    }
                });
                $(document).click(function (){
                    hideDropDownSelect();
                });
                $dom.find(".select_view").on("click", function(){
                    Com_EventStopPropagation();
                });
                this.bindDelClick($dom, selectedDatas, textDatas);

                this.bindSearch($dom);
            }
        },

        hideDropDownSelect: function() {
            $(".select_view", this.element).each(function(index, obj){
                if ($(obj).attr("class").indexOf("hide")  < 0) {
                    $(obj).addClass("hide");
                }
            });
        },

        bindSearch : function($dom) {
            var self = this;
            $dom.find(".select_keyword").on("keydown",function(e){
                if(e.keyCode==13){
                    self.keyword = $(this).val();
                    self.search($dom);
                }
            });
        },

        search : function ($dom) {
            let self = this;
            if(self.keyword){
                var hasResult = false;
                $dom.find("li.dropDownSelectField").each(function(index, obj){
                    var fieldText = $(obj).attr("text").toUpperCase();
                    var keywordText = self.keyword.toUpperCase();
                    if (fieldText.indexOf(keywordText) > -1) {
                        hasResult = true;
                    } else {
                        $(obj).hide();
                    }
                });
            } else {
                $dom.find("li.dropDownSelectField").each(function(index, obj){
                    $(obj).show();
                });
            }
            $dom.find('.select_result_view').scrollTop(0);
        },

        refreshOrderIndex : function() {
            $("#fd_" + this.channel + "_order").find(".sortItem").each(function(index, item){
                $(item).attr("index", index);
                $(item).find("[data-lui-position]").each(function(i,Obj){
                    $(Obj).attr("data-lui-position", "fdOrderBy-" + index);
                });
            });
        },

        refreshOperateIndex : function() {
            $("#fd_" + this.channel + "_operate").find(".sortItem").each(function(index, item){
                $(item).attr("index", index);
                $(item).find("[data-lui-position]").each(function(i,Obj){
                    $(Obj).attr("data-lui-position", "fdOperation-" + index);
                });
            });
        },

        refreshStatisticsIndex : function() {
            $("#fd_" + this.channel + "_statistics").find(".sortItem").each(function(index, item){
                $(item).attr("index", index);
                $(item).find("[data-lui-position]").each(function(i,Obj){
                    $(Obj).attr("data-lui-position", "fdStatistics-" + index);
                });
            });
        },

        /**
         * 将组件从容器中移除，并更新其它组件的索引
         * @param argu {wgt:wgt} 要删除的组件
         */
        deleteOrder : function(argu){
            for(var i = 0;i < this.orderCollection.length;i++){
                if(argu.wgt === this.orderCollection[i]){
                    this.orderCollection.splice(i,1);
                    break;
                }
            }
            this.refreshOrderIndex();
        },

        //操作项删除
        deleteOperate : function(argu){
            for(var i = 0;i < this.operateCollection.length;i++){
                if(argu.wgt === this.operateCollection[i]){
                    this.operateCollection.splice(i,1);
                    break;
                }
            }
            this.refreshOperateIndex();
            var operationIds = [];
            var operationNames = [];
            this.element.find("[name='operationId']").each(function(index, obj){
                var id = $(obj).val();
                var name = $(obj).closest(".inputselectsgl").find("[name='operationName']").val();
                if (id && name) {
                    operationIds.push(id);
                    operationNames.push(name);
                }
            });
            this.element.find('[name="operationIds"]').val(operationIds.join(";"));
            this.element.find('[name="operationNames"]').val(operationNames.join(";"));
            //移动
            this.element.find('[name="mobileOperationIds"]').val(operationIds.join(";"));
            this.element.find('[name="mobileOperationNames"]').val(operationNames.join(";"));
        },

        deleteStatistics : function(argu){
            for(var i = 0;i < this.statisticsCollection.length;i++){
                if(argu.wgt === this.statisticsCollection[i]){
                    this.statisticsCollection.splice(i,1);
                    break;
                }
            }
            this.refreshStatisticsIndex();
        },

        getKeyData : function($super, cfg){
            var keyData = {};

            //统计项
            keyData.fdStatistics = [];
            for(var i = 0;i < this.statisticsCollection.length;i++){
                var statisticsWgt = this.statisticsCollection[i];
                keyData.fdStatistics.push(statisticsWgt.getKeyData());
            }
            //筛选项
            keyData.fdCondition =  $.parseJSON(this.element.find("input[name*='fdCondition']").val() || "[]");
            var textArr =[];
            for (var i = 0;i<keyData.fdCondition.length;i++){
                var fdCondition = keyData.fdCondition[i];
                textArr[i] = fdCondition.text;
            }
            var text =  textArr.join(";");
            this.element.find("input[name*='fdConditionText']").val(text);
            keyData.fdConditionText = this.element.find("input[name*='fdConditionText']").val() || "0";

            //排序项
            keyData.fdOrder = [];
            for(var i = 0;i < this.orderCollection.length;i++){
                var orderWgt = this.orderCollection[i];
                keyData.fdOrder.push(orderWgt.getKeyData());
            }
            //视图穿透
            var fdViewId = "";
            keyData.fdViewFlag = this.element.find("input[name*='fd_" + this.id +  "_fdViewFlag']").val();
            if(keyData.fdViewFlag === '1'){
                fdViewId = this.element.find("[name*='fd_" + this.id +  "_fdViewId']").val();
            }
            keyData.fdViewId = fdViewId;
            return keyData;
        }
    });
    exports.CollectionView = CollectionView;
})