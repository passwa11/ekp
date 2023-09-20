/**
 * pc列表视图的视图组件
 */
define(function(require, exports, module) {

    var $ = require('lui/jquery'),
        base = require('lui/base'),
        str = require('lui/util/str'),
        dialog = require('lui/dialog'),
        render = require('lui/view/render'),
        topic = require('lui/topic'),
        collectionView = require('sys/modeling/base/mobile/resources/js/collectionView'),
        displayCssSet = require('sys/modeling/base/listview/config/js/displayCssSet'),
        footStatistics = require('sys/modeling/base/mobile/resources/js/footStatistics'),
        pagingSetGenerator = require('sys/modeling/base/mobile/resources/js/pagingSetGenerator'),
        operateGenerator = require('sys/modeling/base/mobile/resources/js/operateGenerator');
    var modelingLang = require("lang!sys-modeling-base");
    var PcView = collectionView["CollectionView"].extend({

        initProps: function($super, cfg) {
            $super(cfg);
            this.id = "pc";
            this.channel = "pc";
            this.key = "fdPcCfg";
            this.storeData = cfg.data || {};
            this.sysModelingOperationJson = cfg.sysModelingOperationJson;
        },

        startup : function($super, cfg) {
            this.setRender(new render.Template({
                src : "/sys/modeling/base/views/collection/pcViewRender.html#",
                parent : this
            }));
            this.render.startup();
            $super(cfg);
            topic.subscribe("switchRightContentView",this.switchRightContentView, this);
        },

        switchRightContentView: function(data) {
            if (data && data.key === this.channel && !this.isRefreshSelectBlock) {
                this.renderDisplayBlock();
                this.renderConditionBlock();
                this.isRefreshSelectBlock = true;
            }
        },

        doRender: function($super, cfg){
            $super(cfg);
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
                for (var i = 0; i < renderData.fdOperation.length; i++) {
                    var operateInfo = renderData.fdOperation[i];
                    fdOperationIds.push(operateInfo.fdId);
                    fdOperationNames.push(operateInfo.fdName);
                    for(var j =0; j < this.sysModelingOperationJson.length;j++) {
                       var operation = this.sysModelingOperationJson[j];
                       if(operateInfo.fdId == operation.fdId){
                           operateInfo.fdDefType= operation.fdDefType;
                           operateInfo.fdOperationScenario= operation.fdOperationScenario;
                           break;
                       }
                    }
                    var operateWgt = new operateGenerator({container: $operate, parent: this, data: operateInfo,channel: this.channel});
                    operateWgt.startup();
                    operateWgt.draw();
                    this.operateCollection.push(operateWgt);
                }
                if (fdOperationIds.length > 0 && fdOperationNames.length > 0) {
                    $(this.element).find("[name='operationIds']").val(fdOperationIds.join(";"));
                    $(this.element).find("[name='operationNames']").val(fdOperationNames.join(";"));
                }
                this.sortable("operate");
            }
            this.renderDisplayBlock();
            //分页器
            this.renderPagingSet();
        },

        renderDisplayBlock: function() {
            var renderData = this.storeData;
            //显示
            if (renderData.fdDisplay && renderData.fdDisplay.length > 0) {
                //回调
                var $dom = this.element.find("[name='fd_" + this.channel + "_fdDisplay']").closest(".multiSelectDialog");
                var textDatas = [];
                if(renderData.fdDisplayText) {
                    textDatas =  renderData.fdDisplayText.split(";");
                }
                this.createSelectItem($dom, renderData.fdDisplay, textDatas);
            }
        },

        renderCustom : function() {
            this.renderDisplayCssSet();
            this.renderPagingSet();
            this.renderFootStatistics();
        },

        renderPagingSet : function() {
            this.pagingSet = new pagingSetGenerator({container:this.element,parent:this, channel: this.channel,data: this.storeData.fdPageSetting});
            this.pagingSet.startup();
            this.pagingSet.draw();
        },

        //初始化显示样式组件
        renderDisplayCssSet: function() {
            var data = JSON.stringify(this.storeData.fdDisplaySet);
            var fields = JSON.stringify(listviewOption.baseInfo.fieldInfos);
            var displayCssSetCfg = {
                operationEle:$("#fdDisplayCssSetTr", this.element),
                contentEle:$("#fdDisplayCssSetContent", this.element),
                storeData:data,
                allField:fields
            };
            this.displayCssSetIns = new displayCssSet.DisplayCssSet(displayCssSetCfg);

            var texts = this.storeData.fdDisplayText;
            var displayTexts = [];
            if (texts) {
                texts = texts.split(";");
                for (var i = 0; i < texts.length; i++) {
                    var fdDisplay = {};
                    fdDisplay.text = texts[i];
                    displayTexts.push(fdDisplay);
                }
            }
            if(typeof this.storeData.fdDisplay != "undefined" && this.storeData.fdDisplay != ""){
                var modelDictData = listviewOption.baseInfo.modelDict;
                var allFieldData = listviewOption.baseInfo.fieldInfos;
                var fields = this.storeData.fdDisplay;
                var data = {};
                var text = [];
                for(var i = 0;i < displayTexts.length;i++){
                    text.push(displayTexts[i].text);
                }
                data.selected = this.doAdaptorOldData(modelDictData,allFieldData,fields);
                data.text = text;
                //显示项样式改变事件
                topic.publish("modeling.selectDisplay.change",{'thisObj':null,'data':data});
            }
        },

        //初始化底部统计组件
        renderFootStatistics: function() {
            var data = JSON.stringify(this.storeData.fdFootStatistics);
            var fields = JSON.stringify(listviewOption.baseInfo.fieldInfos);
            var footStatisticsCfg = {
                operationEle:$("#fdFootStatisticsTr", this.element),
                contentEle:$("#fdFootStatisticsContent", this.element),
                storeData:data,
                allField:fields,
                channel:this.channel

            };
            this.footStatisticsIns = new footStatistics.FootStatistics(footStatisticsCfg);

            var texts = this.storeData.fdDisplayText;
            var displayTexts = [];
            if (texts) {
                texts = texts.split(";");
                for (var i = 0; i < texts.length; i++) {
                    var fdDisplay = {};
                    fdDisplay.text = texts[i];
                    displayTexts.push(fdDisplay);
                }
            }
            if(typeof this.storeData.fdDisplay != "undefined" && this.storeData.fdDisplay != ""){
                var modelDictData = listviewOption.baseInfo.modelDict;
                var allFieldData = listviewOption.baseInfo.fieldInfos;
                var fields = this.storeData.fdDisplay;
                var data = {};
                var text = [];
                for(var i = 0;i < displayTexts.length;i++){
                    text.push(displayTexts[i].text);
                }
                data.selected = this.doAdaptorOldData(modelDictData,allFieldData,fields);
                data.text = text;
                //显示项样式改变事件
                topic.publish("modeling.selectDisplay.change",{'thisObj':null,'data':data});
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

        getKeyData : function($super, cfg){
            var keyData = $super(cfg);
            delete keyData.fdSummary;
            delete keyData.fdSummaryText;

            //显示项
            keyData.fdDisplay =  $.parseJSON(this.element.find("input[name*='fdDisplay']").val() || "[]");
            keyData.fdDisplayText = this.element.find("input[name*='fdDisplayText']").val();

            //操作项
            keyData.fdOperation = [];
            for(var i = 0;i < this.operateCollection.length;i++){
                var operateWgt = this.operateCollection[i];
                keyData.fdOperation.push(operateWgt.getKeyData());
            }

            //显示样式
            if (typeof this.displayCssSetIns.getKeyData != "undefined") {
                keyData.fdDisplaySet = this.displayCssSetIns.getKeyData();
            }

            //底部数据统计
            if (this.footStatisticsIns && typeof this.footStatisticsIns.getKeyData != "undefined") {
                keyData.fdFootStatistics = this.footStatisticsIns.getKeyData();
            }
            //分页设置
            if(typeof this.pagingSet != "undefined") {
                keyData.fdPageSetting = this.pagingSet.getKeyData();
            }

            return keyData;
        },

        validate : function(){
            var isPass = true;
            for(var i = 0;i < this.operateCollection.length;i++){
                var operateWgt = this.operateCollection[i];
                if (!operateWgt.validate()) {
                    isPass = false;
                }
            }
            return isPass;
        },
        getModelingLang :function (){
            return modelingLang;
        }
    });
    exports.PcView = PcView;
})