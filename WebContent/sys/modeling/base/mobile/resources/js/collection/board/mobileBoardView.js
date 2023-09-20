/**
 * pc列表视图的视图组件
 */
define(function(require, exports, module) {

    var $ = require('lui/jquery'),
        base = require('lui/base'),
        str = require('lui/util/str'),
        dialog = require('lui/dialog'),
        source = require('lui/data/source'),
        render = require('lui/view/render'),
        topic = require('lui/topic'),
        collectionBaseView = require('sys/modeling/base/mobile/resources/js/collectionBaseView'),
        displayCssSet = require('sys/modeling/base/listview/config/js/displayCssSet'),
        dropdownGenerator = require('sys/modeling/base/mobile/resources/js/dropdownGenerator'),
        fieldGenerator = require('sys/modeling/base/mobile/resources/js/collection/board/fieldGenerator'),
        groupCustomGenerator = require('sys/modeling/base/mobile/resources/js/collection/board/groupCustomGenerator'),
        operateGenerator = require('sys/modeling/base/mobile/resources/js/operateGenerator');
    var modelingLang = require("lang!sys-modeling-base");
    var MobileBoardView = collectionBaseView["CollectionBaseView"].extend({

        initProps: function($super, cfg) {
            $super(cfg);
            this.id = "mobile";
            this.channel = "mobile";
            this.key = "fdMobileCfg";
            this.storeData = cfg.data || {};
            this.isRefreshSelectBlock = false;
        },

        startup : function($super, cfg) {
            this.setRender(new render.Template({
                src : "/sys/modeling/base/views/collection/board/mobileViewRender.html#",
                parent : this
            }));
            this.render.startup();
            $super(cfg);
            topic.subscribe("switchRightContentView",this.switchRightContentView, this);
            topic.channel(this.channel).subscribe("groupCustom.delete",this.deleteGroupCustom, this);
        },

        switchRightContentView: function(data) {
            if (data && data.key === this.channel && !this.isRefreshSelectBlock) {
                this.renderSummaryBlock();
                this.renderConditionBlock();
                this.isRefreshSelectBlock = true;
            }
        },

        doRender : function($super, cfg) {
            $super(cfg);
            //摘要项是否显示
            var fdSummaryFlag = this.element.find("[name='fd_" + this.channel + "_fdSummaryFlag']").val();
            var radioObj = this.element.find("[name='" + this.channel + "_summaryflag']")[0];
            if(fdSummaryFlag == "1"){
                $(radioObj).attr("checked",true);
            }
            var self = this;
            this.element.find("[name='" + this.channel + "_summaryflag']").each(function (index, obj){
                $(obj).on("click",function() {
                    var value =$(this).is(":checked")?"1":"0";
                    var curVal = $("[name='fd_" + self.channel + "_fdSummaryFlag']",self.element).val();
                    if(curVal == value){
                        return;
                    }
                    $("[name='fd_" + self.channel + "_fdSummaryFlag']",self.element).val(value);
                    //刷新预览
                    topic.publish("preview.refresh", {"key": self.channel});
                });
            });

            topic.publish("mobile_view_load_finish");
        },

        reDrawByPc : function(pcData) {
            this.storeData = pcData;
            if (pcData.fdCondition) {
                var mobileCondition = [];
                for (var i = 0; i < pcData.fdCondition.length; i++) {
                    var conditionInfo = pcData.fdCondition[i];
                    if(conditionInfo.field ==="LbpmExpecterLog_fdHandler" || conditionInfo.field === "LbpmExpecterLog_fdNode"){
                        continue;
                    }
                    var type  = this.getFieldInfoByField(conditionInfo.field);
                    if (type) {
                        conditionInfo.type = type;
                    }
                    mobileCondition.push(conditionInfo);
                }
                pcData.fdCondition = mobileCondition;
            }
            if (pcData.fdSummary) {
                var mobileSummary = [];
                for (var i = 0; i < pcData.fdSummary.length; i++) {
                    var summaryInfo = pcData.fdSummary[i];
                    if(summaryInfo.field ==="LbpmExpecterLog_fdHandler" || summaryInfo.field === "LbpmExpecterLog_fdNode"){
                        continue;
                    }
                    mobileSummary.push(summaryInfo);
                }
                pcData.fdSummary = mobileSummary;
            }
            if (pcData.fdOperation) {
                //导入PC的操作按钮时要过滤掉移动端不支持的按钮
                var operations =  listviewOption.baseInfo.operations;
                var newOperation =[];
                for (var i = 0; i < pcData.fdOperation.length; i++) {
                    var conditionInfo = pcData.fdOperation[i];
                    if (conditionInfo.fdName =="批量删除"||conditionInfo.fdName =="批量打印"||
                        conditionInfo.fdName =="导出"||conditionInfo.fdName =="导入") {
                        continue;
                    }
                    var canPush = true;
                    for (var j = 0; j < operations.length; j++) {
                        var operation = operations[j];
                        if(operation && conditionInfo.fdId === operation[0]){
                            if(operation.length > 2 && operation[2] == "1"){
                                canPush = false;
                            }
                            break;
                        }
                    }
                    if (canPush) {
                        newOperation.push(conditionInfo);
                    }
                }
                pcData.fdOperation = newOperation;
            }
            this.onDataLoad(pcData);
        },

        getFieldInfoByField : function (field) {
            var fieldInfos = listviewOption.baseInfo.fieldInfos;
            if(field === "docCreator"){
                field = "docCreator.fdName";
            }
            for (var i = 0; i < fieldInfos.length; i++) {
                var fieldInfo = fieldInfos[i];
                if (fieldInfo.field === field) {
                    return fieldInfo.type;
                }
            }
            return null;
        },

        renderBlock : function($super) {
            $super();
            var renderData = this.storeData;
            var $operate = this.element.find("[data-table-type='operate']");
            //操作
            if (renderData.fdOperation && renderData.fdOperation.length) {
                if (renderData.fdOperation.length > 0) {
                    $operate.parent().show();
                }
                var fdOperationIds = [];
                var fdOperationNames = [];
                this.operateCollection =[];
                for (var i = 0; i < renderData.fdOperation.length; i++) {
                    var operateInfo = renderData.fdOperation[i];
                    fdOperationIds.push(operateInfo.fdId);
                    fdOperationNames.push(operateInfo.fdName);
                    var operateWgt = new operateGenerator({container: $operate, parent: this, data: operateInfo,channel: this.channel});
                    operateWgt.startup();
                    operateWgt.draw();
                    this.operateCollection.push(operateWgt);
                }
                if (fdOperationIds.length > 0 && fdOperationNames.length > 0) {
                    $(this.element).find("[name='mobileOperationIds']").val(fdOperationIds.join(";"));
                    $(this.element).find("[name='mobileOperationNames']").val(fdOperationNames.join(";"));
                }
                this.sortable("operate");
            }
            this.renderSummaryBlock();
        },

        renderSummaryBlock: function() {
            var renderData = this.storeData;
            //摘要项
            if (renderData.fdSummary && renderData.fdSummary.length > 0) {
                //回调
                var $dom = this.element.find("[name='fd_" + this.channel + "_fdSummary']").closest(".multiSelectDialog");
                var textDatas = [];
                if(renderData.fdSummaryText) {
                    textDatas =  renderData.fdSummaryText.split(";");
                }
                this.createSelectItem($dom, renderData.fdSummary, textDatas);
            }
        },

        renderCustom : function() {
            //渲染显示样式
            //this.renderDisplayCssSet();
            //渲染标题
//            this.renderSubject();
            //渲染分组字段
            this.renderGroup();
            //渲染封面图片
            this.renderCoverImg();
        },

        //初始化显示样式组件
        renderDisplayCssSet: function() {
            var data = JSON.stringify(this.storeData.fdDisplaySet);
            var fields = JSON.stringify(listviewOption.baseInfo.fieldInfos);
            var displayCssSetCfg = {
                operationEle:$("#fdDisplayCssSetTr", this.element),
                contentEle:$("#fdDisplayCssSetContent", this.element),
                storeData:data,
                allField:fields,
                displayType:"board",
                channel:this.channel
            };
            this.displayCssSetIns = new displayCssSet.DisplayCssSet(displayCssSetCfg);

            var texts = this.storeData.fdSummaryText;
            var summaryTexts = [];
            if (texts) {
                texts = texts.split(";");
                for (var i = 0; i < texts.length; i++) {
                    var fdSummary = {};
                    fdSummary.text = texts[i];
                    summaryTexts.push(fdSummary);
                }
            }
            if(typeof this.storeData.fdSummary != "undefined" && this.storeData.fdSummary != ""){
                var modelDictData = listviewOption.baseInfo.modelDict;
                var allFieldData = listviewOption.baseInfo.fieldInfos;
                var fields = this.storeData.fdSummary;
                var data = {};
                var text = [];
                for(var i = 0;i < summaryTexts.length;i++){
                    text.push(summaryTexts[i].text);
                }
                data.selected = this.doAdaptorOldData(modelDictData,allFieldData,fields);
                data.text = text;
                //显示项样式改变事件
                //topic.channel(this.channel).publish("modeling.selectDisplay.change",{'thisObj':null,'data':data});
            }
        },

        //兼容旧数据，补全多选框的枚举数据
        doAdaptorOldData : function(modelDictData,allFieldData,fields){
            var objArr = [];
            for(var n = 0;n < fields.length;n++){
                for(var m = 0;m < allFieldData.length;m++){
                    if(fields[n].field == allFieldData[m].field.split(".")[0]){
                        objArr.push(allFieldData[m]);
                    }
                }
            }
            for(var i = 0;i < objArr.length;i++){
                for(var j = 0;j < modelDictData.length;j++){
                    if(modelDictData[j].field == objArr[i].field.split(".")[0] && modelDictData[j].hasOwnProperty("enumValues")){
                        objArr[i].enumValues = modelDictData[j].enumValues;
                    }
                }
            }
            return objArr;
        },

        renderSubject: function(){
            var data = JSON.stringify(this.storeData.fdSubject);
            var fdSubjectCfg = {
                container:$(".fdSubjectTr", this.element),
                data:data,
                channel:this.channel,
                name:"fdSubject",
                required:"true"
            };
            this.fdSubjectGenertor = new dropdownGenerator.DropdownGenerator(fdSubjectCfg);
        },

        renderCoverImg: function(){
            var data = JSON.stringify(this.storeData.fdCoverImg);
            var fdCoverImgCfg = {
                container:$(".fdCoverImgTr", this.element),
                data:data,
                channel:this.channel,
                name:"fdCoverImg",
                required:"false"
            };
            this.fdCoverImgGenertor = new dropdownGenerator.DropdownGenerator(fdCoverImgCfg);
            var $showCoverImg = $(".fdCoverImgTr", this.element);
            var self =this;
            $showCoverImg.find("input[name*=fdShowCoverImg]").each(function (index, obj){
                $(obj).on("click",function() {
                    var value =$(this).attr("value");
                    var curVal = $("[name='fd_" + self.channel + "_ShowCoverImg']",$showCoverImg).val();
                    if(curVal == value){
                        return;
                    }
                    if(value == "1"){
                        self.fdCoverImgGenertor.showElement();
                    }else{
                        self.fdCoverImgGenertor.hideElement();
                    }

                    $("[name='fd_" + self.channel + "_ShowCoverImg']",$showCoverImg).val(value);
                    //刷新预览
                    topic.publish("preview.refresh", {"key": self.channel});
                });
            });
            //封面图片是否显示
            var fdShowCoverImg = $("[name='fd_" + self.channel + "_ShowCoverImg']",$showCoverImg).val();
            if(fdShowCoverImg=="1"){
                this.element.find("[name='fd_" + self.channel + "_fdShowCoverImg'][value=1]").attr("checked",true);
                self.fdCoverImgGenertor.showElement();
            }else{
                this.element.find("[name='fd_" + self.channel + "_fdShowCoverImg'][value=0]").attr("checked",true);
                self.fdCoverImgGenertor.hideElement();
            }
        },

        renderGroup: function(){
            var data = this.storeData.fdGroup;
            this.renderGroupFields();
            this.renderGroupCustom();
            var self = this;
            var groupType = data && data.groupType || 0;
            this.element.find(".board-list-view-group-container .board-list-view-group-type>ul li").each(function () {
                var value = $(this).data("value");
                if(groupType == value){
                    $(this).addClass("active");
                    if(value == 0){
                        self.element.find(".board-list-view-group-filed-content").show();
                        self.element.find(".board-list-view-group-custom-content").hide();
                    }else if(value == 1){
                        self.element.find(".board-list-view-group-filed-content").hide();
                        self.element.find(".board-list-view-group-custom-content").show();
                    }
                }
            })
            this.element.find(".board-list-view-group-container .board-list-view-group-type>ul li").click(function (){
                var value = $(this).data("value");
                $(this).addClass("active");
                self.element.find(".board-list-view-group-container .board-list-view-group-type>ul li").not(this).removeClass("active");
                if(value == 0){
                    self.element.find(".board-list-view-group-filed-content").show();
                    self.element.find(".board-list-view-group-custom-content").hide();
                }else if(value == 1){
                    self.element.find(".board-list-view-group-filed-content").hide();
                    self.element.find(".board-list-view-group-custom-content").show();
                }
                if(self._reminder){
                    self._reminder.hide();
                }
                //刷新预览
                topic.publish("preview.refresh", {"key": self.channel});
            })
        },

        // 分组字段
        renderGroupFields: function() {
            var self = this;
            self.groupCollection=[];
            if (this.storeData.fdGroup && this.storeData.fdGroup.fieldCfg){
                var datas = this.storeData.fdGroup.fieldCfg;
                var $fieldTable = self.element.find(".board-list-view-group-field-table");
                for (var i = 0; i < datas.length; i++) {
                    var data = datas[i];
                    var groupWgt = new fieldGenerator({
                        container: $fieldTable,
                        storeData: data,
                        parent: self,
                        channel: self.channel
                    });
                    groupWgt.startup();
                    groupWgt.draw();
                    self.groupCollection.push(groupWgt);
                }
            }

            this.element.find(".board-list-view-group-field-create").on("mouseenter",function(){
                var $modelDataCreate = $(this);
                var $table = $modelDataCreate.closest("tr").find(".board-list-view-group-field-table");
                $table.parent().show();
                if(0 >= $modelDataCreate.find(".group_field_item").length){
                    var type = $table.attr("data-table-type");
                    if (type === "groupField") {
                        var $groupFieldItem = $("<div class='group_field_item'></div>");
                        var $groupFieldItemUl = $("<ul></ul>");
                        var options = self.filterSubTableField(listviewOption.baseInfo.fieldInfos);
                        for (var i = 0; i < options.length; i++) {
                            var option = options[i];
                            if(option.businessType == "inputRadio" || option.businessType == "select"){
                                var $groupFieldItemLi = $("<li name="+option.field+" title="+option.text+" >" + option.text + "</li>");
                                $groupFieldItemLi.data("value",option);
                                $groupFieldItemLi.on("click", function () {
                                    var field = $(this).attr("name");
                                    if (self.checkIsExist(field)){
                                        //隐藏
                                        $modelDataCreate.find(".group_field_item").css({
                                            "border":"0",
                                            "height":"0"
                                        });
                                        return false;
                                    }
                                    var groupWgt = new fieldGenerator({
                                        container: $table,
                                        field: field,
                                        parent: self,
                                        channel: self.channel,
                                        fieldInfos:listviewOption.baseInfo.fieldInfos
                                    });
                                    groupWgt.startup();
                                    groupWgt.draw();
                                    self.groupCollection.push(groupWgt);
                                    //隐藏
                                    $modelDataCreate.find(".group_field_item").css({
                                        "border":"0",
                                        "height":"0"
                                    });
                                    //刷新预览
                                    topic.publish("preview.refresh", {"key": self.channel});
                                    return false;
                                });
                                $groupFieldItemUl.append($groupFieldItemLi);
                            }
                        }
                        $groupFieldItem.append($groupFieldItemUl);
                        $(this).append($groupFieldItem);
                    }
                }
                var length = $modelDataCreate.find(".group_field_item ul li").length;
                if(0 >= length){
                    var $li= $("<li>"+modelingLang['listview.no.data']+"</li>");
                    $modelDataCreate.find(".group_field_item ul").append($li);
                }
                $modelDataCreate.find(".group_field_item").css({
                    "border":"1px solid #DDDDDD",
                    "height":"240px",
                    "display":"block"
                });
            });

            this.element.find(".board-list-view-group-field-create").on("mouseleave",function(){
                $(this).find(".group_field_item").css({
                    "display":"none"
                });
            });

        },

        // 自定义分组
        renderGroupCustom: function() {
            var self = this;
            self.groupCustomCollection=[];
            if (this.storeData.fdGroup && this.storeData.fdGroup.customCfg){
                var datas = this.storeData.fdGroup.customCfg;
                var $table = this.element.find(".board-list-view-group-container .model-edit-view-oper-content-table");
                for (var i = 0; i < datas.length; i++) {
                    var data = datas[i];
                    var groupWgt = new groupCustomGenerator({
                        container: $table,
                        data:data,
                        parent: self,
                        channel: self.channel
                    });
                    groupWgt.startup();
                    groupWgt.draw();
                    self.groupCustomCollection.push(groupWgt);
                    self.sortable("groupCustom");
                }
            }
            this.element.find(".board-list-view-group-custom-create").on("click",function(){
                var $table = $(this).closest("tr").find(".model-edit-view-oper-content-table");
                $table.parent().show();
                var type = $table.attr("data-table-type");
                if (type === "groupCustom") {
                    var groupWgt = new groupCustomGenerator({
                        container: $table,
                        parent: self,
                        channel: self.channel
                    });
                    groupWgt.startup();
                    groupWgt.draw();
                    self.groupCustomCollection.push(groupWgt);
                    self.sortable("groupCustom");
                    //刷新预览
                    topic.publish("preview.refresh", {"key": self.channel});
                }
            });

        },

        checkIsExist:function(field) {
            for (var i = 0; i < this.groupCollection.length; i++) {
                var groupWgt = this.groupCollection[i];
                if (groupWgt.field == field){
                    return true;
                }
            }
            return false;
        },

        /**
         * 将组件从容器中移除，并更新其它组件的索引
         * @param argu {wgt:wgt} 要删除的组件
         */
        deleteGroupCustom : function(argu){
            for(var i = 0;i < this.groupCustomCollection.length;i++){
                if(argu.wgt === this.groupCustomCollection[i]){
                    this.groupCustomCollection.splice(i,1);
                    break;
                }
            }
            this.refreshGroupCustomIndex();
        },

        refreshGroupCustomIndex : function() {
            $("#fd_" + this.channel + "_groupCustom").find(".sortItem").each(function(index, item){
                $(item).attr("index", index);
                $(item).find("[data-lui-position]").each(function(i,Obj){
                    $(Obj).attr("data-lui-position", "fdGroup-" + index);
                });
            });
        },

        /**
         * 拖动重新排序组件数据跟刷新对应的index
         * @param evt
         */
        refreshWhenSort : function($super,evt) {
            $super(evt);
            var sortItem = evt.item;
            var context = $(sortItem).closest("[data-table-type]");
            var type = context.attr("data-table-type");
            var srcWgts = [];
            if (type === "groupCustom") {
                srcWgts = this.groupCustomCollection;
            }
            var targetWgts = [];
            context.find(".sortItem").each(function (index, obj){
                var oldIndex = parseInt($(obj).attr("index"));
                targetWgts.push(srcWgts[oldIndex]);
            });
            if (type === "groupCustom") {
                this.refreshGroupCustomIndex();
                this.groupCustomCollection = targetWgts;
            }
        },

        getKeyData : function($super, cfg){
            var keyData = $super(cfg);

            delete keyData.fdDisplay;
            delete keyData.fdDisplayText;

            //摘要项
            keyData.fdSummary =  $.parseJSON(this.element.find("input[name$='fd_"+this.channel+"_fdSummary']").val() || "[]");
            keyData.fdSummaryText = this.element.find("input[name*='fdSummaryText']").val();

            //是否显示摘要项
            keyData.fdSummaryFlag = this.element.find("input[name$='fd_" + this.channel + "_fdSummaryFlag']" ).val();

            //显示样式
            if (this.displayCssSetIns && typeof this.displayCssSetIns.getKeyData != "undefined") {
                keyData.fdDisplaySet = this.displayCssSetIns.getKeyData();
            }

            //操作项
            keyData.fdOperation = [];
            for(var i = 0;i < this.operateCollection.length;i++){
                var operateWgt = this.operateCollection[i];
                keyData.fdOperation.push(operateWgt.getKeyData());
            }

            keyData.fdSubject = {};
            //标题
            if (this.fdSubjectGenertor && typeof this.fdSubjectGenertor.getKeyData != "undefined") {
                keyData.fdSubject = this.fdSubjectGenertor.getKeyData();
            }

            //是否显示封面图片
            keyData.fdShowCoverImg = this.element.find("input[name='fd_" + this.channel + "_ShowCoverImg']" ).val();

            keyData.fdCoverImg={};
            //封面图片
            if (this.fdCoverImgGenertor && typeof this.fdCoverImgGenertor.getKeyData != "undefined") {
                keyData.fdCoverImg = this.fdCoverImgGenertor.getKeyData();
            }

            keyData.fdPageSetting = "1";

            // 显示项由标题字段和摘要显示项组成
            var fdDisplay = [];
            $.extend(fdDisplay, keyData.fdSummary);
            var hasSubject = false;
            var hasImg = false;
            for(var i = 0;i < fdDisplay.length;i++){
                if(fdDisplay[i].field === keyData.fdSubject.field){
                    hasSubject = true;
                    fdDisplay[i].busType = "subject";	// 业务类型
                }else if(fdDisplay[i].field === keyData.fdCoverImg.field) {
                    hasImg = true;
                    fdDisplay[i].busType = "coverImg";	// 业务类型
                }else{
                    fdDisplay[i].busType = "summary";
                }
            }
            if(!hasSubject){
                fdDisplay.push($.extend({busType:"subject"},keyData.fdSubject));
            }
            if(!hasImg && keyData.fdCoverImg.field){
                fdDisplay.push($.extend({busType:"coverImg"},keyData.fdCoverImg));
            }
            keyData.fdDisplay = fdDisplay;

            //分组
            keyData.fdGroup = {};
            keyData.fdGroup.groupType = this.element.find(".board-list-view-group-container .board-list-view-group-type>ul li.active").data("value");
            keyData.fdGroup.fieldCfg = [];
            if(keyData.fdGroup.groupType == "0"){
                for(var i = 0;i < this.groupCollection.length;i++){
                    var groupWgt = this.groupCollection[i];
                    keyData.fdGroup.fieldCfg.push(groupWgt.getKeyData());
                }
            }
            keyData.fdGroup.customCfg = [];
            if(keyData.fdGroup.groupType == "1"){
                for(var i = 0;i < this.groupCustomCollection.length;i++){
                    var groupWgt = this.groupCustomCollection[i];
                    keyData.fdGroup.customCfg.push(groupWgt.getKeyData());
                }
            }

            return keyData;
        },

        validate : function(){
            var isPass = true;
            if(this.operateCollection){
                for(var i = 0;i < this.operateCollection.length;i++){
                    var operateWgt = this.operateCollection[i];
                    if (!operateWgt.validate()) {
                        isPass = false;
                    }
                }
            }
            var groupType = this.element.find(".board-list-view-group-container .board-list-view-group-type>ul li.active").data("value");
            var failMsg = modelingLang['listview.boardView.validate.groupType'];
            var elem = this.element.find(".board-list-view-group-container .board-list-view-group-type");
            elem.attr(KMSSValidation.ValidateConfig.attribute, "group_type_validate_" +this.channel);
            var defaultOpt = { where: 'beforeend'};
            this._reminder = new Reminder(elem,failMsg,"",defaultOpt);
            if ((groupType != "0" && groupType != "1") || (groupType == "0" && this.groupCollection.length<=0) ||
                (groupType == "1" && this.groupCustomCollection.length<=0)) {
                isPass = false;
                this._reminder.show()
            } else {
                this._reminder.hide()
            }
            return isPass;
        },
        getModelingLang :function (){
            return modelingLang;
        }
    });

    exports.MobileBoardView = MobileBoardView;

})