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
        collectionView = require('sys/modeling/base/mobile/resources/js/collectionView'),
        dropdownGenerator = require('sys/modeling/base/mobile/resources/js/dropdownGenerator'),
        operateGenerator = require('sys/modeling/base/mobile/resources/js/operateGenerator');
    var modelingLang = require("lang!sys-modeling-base");
    var MobileView = collectionView["CollectionView"].extend({

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
                src : "/sys/modeling/base/views/collection/mobileViewRender.html#",
                parent : this
            }));
            this.render.startup();
            $super(cfg);
            topic.subscribe("switchRightContentView",this.switchRightContentView, this);
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
            topic.publish("mobile_view_load_finish");
        },

        reDrawByPc : function(pcData) {
            this.storeData = pcData;
            if (pcData.fdDisplayText) {
                //pc第一个显示项作为移动标题,其余作为摘要项
                var fdDisplay = [].concat(pcData.fdDisplay);
                var firstDisplayField = fdDisplay.shift();
                //var fdSubjectArr = pcData.fdDisplayText.split(";");
                this.storeData.fdSubject = firstDisplayField;
                //fdSubjectArr.shift();
                var mobileSummary = [];
                var subjectInfo =[];
                for (var i = 0; i < fdDisplay.length; i++) {
                    var summaryInfo = fdDisplay[i];
                    // #170584 列表视图PC导入移动端，摘要项不支持附件上传，图片上传，页面仍显示 去掉附件类型的字段
                    if(summaryInfo.field ==="LbpmExpecterLog_fdHandler" || summaryInfo.field === "LbpmExpecterLog_fdNode" || summaryInfo.type === "attachments" ){
                        continue;
                    }
                    mobileSummary.push(summaryInfo);
                    subjectInfo.push(summaryInfo.text);
                }
                this.storeData.fdSummary = mobileSummary;
                this.storeData.fdSummaryText = subjectInfo.join(";");
            }
            if (pcData.fdCondition) {
                for (var i = 0; i < pcData.fdCondition.length; i++) {
                    var conditionInfo = pcData.fdCondition[i];
                    var fieldInfo  = this.getFieldInfoByField(conditionInfo.field);
                    if (fieldInfo) {
                        pcData.fdCondition[i] = fieldInfo;
                    }
                }
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
            var eqField = field;
            if(eqField == 'docCreator'){
                eqField ='docCreator.fdName';
            }
            var fieldInfos = listviewOption.baseInfo.fieldInfos;
            for (var i = 0; i < fieldInfos.length; i++) {
                var fieldInfo = JSON.parse(JSON.stringify(fieldInfos[i]));
                if (fieldInfo.field === eqField) {
                    fieldInfo.field = field;
                    return fieldInfo;
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
                //重复导入PC配置会一直添加导致重复
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
            //渲染标题
            //this.renderSubject();
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

        getKeyData : function($super, cfg){
            var keyData = $super(cfg);
            // var $subject = this.element.find("[name='fd_mobile_fdSubject']");
            // var $divOption = $subject.closest(".model-mask-panel-table-select").find("[option-value='"+ $subject.val() +"']");
            // keyData.fdSubject = {};
            // keyData.fdSubject.text = $divOption.html();
            // keyData.fdSubject.field = $subject.val();
            // keyData.fdSubject.type = $divOption.attr("data-field-type");

            //标题
            if (typeof this.fdSubjectGenertor.getKeyData != "undefined") {
                keyData.fdSubject = this.fdSubjectGenertor.getKeyData();
            }

            //摘要项
            keyData.fdSummary =  $.parseJSON(this.element.find("input[name*='fdSummary']").val() || "[]");
            keyData.fdSummaryText = this.element.find("input[name*='fdSummaryText']").val();
            keyData.fdSummaryTitleShow = this.element.find("input[name*='fdSummaryTitleShow']").val();

            // 显示项由标题字段和摘要显示项组成
            var fdDisplay = [];
            $.extend(fdDisplay, keyData.fdSummary);
            for(var i = 0;i < fdDisplay.length;i++){
                fdDisplay[i].busType = "summary";
            }
            keyData.fdDisplay = fdDisplay;
            keyData.fdDisplay.push(keyData.fdSubject);

            //操作项
            keyData.fdOperation = [];
            for(var i = 0;i < this.operateCollection.length;i++){
                var operateWgt = this.operateCollection[i];
                keyData.fdOperation.push(operateWgt.getKeyData());
            }

            //是否直接穿透
            keyData.fdDirectlyThrouth = this.element.find("input[name*='fd_" + this.id +  "_fdDirectlyThrouth']:checked").val();
            return keyData;
        },

        validate : function() {
            var isPass = true;
            if(this.operateCollection){
                for(var i = 0;i < this.operateCollection.length;i++){
                    var operateWgt = this.operateCollection[i];
                    if (!operateWgt.validate()) {
                        isPass = false;
                    }
                }
            }
            return isPass;
        },
        getModelingLang :function (){
            return modelingLang;
        }
    });

    exports.MobileView = MobileView;

})