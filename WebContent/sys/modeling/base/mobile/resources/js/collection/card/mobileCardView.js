/**
 * 移动卡片视图的视图组件
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
        dropdownGenerator = require('sys/modeling/base/mobile/resources/js/dropdownGenerator'),
        operateGenerator = require('sys/modeling/base/mobile/resources/js/operateGenerator');
    var modelingLang = require("lang!sys-modeling-base");
    var MobileCardView = collectionBaseView["CollectionBaseView"].extend({

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
                src : "/sys/modeling/base/views/collection/card/mobileViewRender.html#",
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

            var fdSummaryFlag = this.element.find("[name='fd_"+ this.channel +"_summaryFlag']").val();
            var radioObj = $("#" + this.channel + "_summaryflag");
            this.setClass(radioObj, fdSummaryFlag);
            var self = this;
            radioObj.find("[name='summaryflag']").each(function (index, obj){
                $(obj).on("click",function() {
                    var value =$(this).attr("value");
                    var curVal = $("[name='fd_"+ self.channel +"_summaryFlag']",self.element).val();
                    if(curVal == value){
                        return;
                    }
                    var radioObj = $(this).parents(".view_flag_radio")[0];
                    self.setClass(radioObj, value);
                    $("[name='fd_"+ self.channel +"_summaryFlag']",self.element).val(value);
                });
            });

            var radioObj = $("#" + this.channel + "_columnset");
            var fdColumnNum = this.element.find("[name='fd_"+ this.channel +"_columnSetting']").val();
           var selecedObj =  $(radioObj).find("li[value='"+fdColumnNum+"']");
            if(!$(selecedObj).hasClass('selected')){
                $(selecedObj).addClass('selected').siblings().removeClass('selected');
            }
            var self = this;
            radioObj.find("[name='columnset']").each(function (index, obj){
                $(obj).on("click",function() {
                    var value =$(this).attr("value");
                    var curVal = $("[name='fd_"+ self.channel +"_columnSetting']",self.element).val();
                    if(curVal == value){
                        return;
                    }
                    if(!$(obj).hasClass('selected')){
                        $(obj).addClass('selected').siblings().removeClass('selected');
                    }
                    $("[name='fd_"+ self.channel +"_columnSetting']",self.element).val(value);
                    var $table = $(this).closest("tr").next().find(".model-edit-view-oper-content-table");
                    topic.publish("preview.refresh", {"key": self.channel});
                    topic.publish("data-create-finish",{"table":$table, "key": self.channel});
                    var context = $("#modeling-pam-content-mobile");
                    window.changeRightContentView("design");
                    $("[data-lui-position]", context).removeClass("active");
                    $("[data-lui-position='fdDisplay']", context).addClass("active");
                });
            });

            $('#modeling-pam-content-mobile .boardCardConfigTitleIcon').on('click',function(){
                if($(this).parents('.boardCardConfig').hasClass('slideUp')){
                    $(this).parents('.boardCardConfig').removeClass('slideUp')
                }else{
                    $(this).parents('.boardCardConfig').addClass('slideUp')
                }
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
                    var fieldInfo  = this.getFieldInfoByField(conditionInfo.field);
                    if (fieldInfo) {
                        mobileCondition.push(fieldInfo);
                    }
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
            this.storeData.fdColumnNum = '1';
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

            //标题
            //this.renderSubject();

            //渲染封面图片
            this.renderCoverImg();
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
            $showCoverImg.find("input[name='fd_" + this.channel + "_fdShowCoverImg']").each(function (index, obj){
                $(obj).on("click",function() {
                    var value =$(this).attr("value");
                    var curVal = $("[name='fdShowCoverImgM']",$showCoverImg).val();
                    if(curVal == value){
                        return;
                    }
                    if(value == "1"){
                        self.fdCoverImgGenertor.showElement();
                    }else{
                        self.fdCoverImgGenertor.hideElement();
                    }

                    $("[name='fdShowCoverImgM']",$showCoverImg).val(value);
                    topic.publish("preview.refresh", {"key": self.channel});
                    var context = $("#modeling-pam-content-mobile");
                    window.changeRightContentView("design");
                    $("[data-lui-position]", context).removeClass("active");
                    $("[data-lui-position='fdDisplay']", context).addClass("active");
                });
            });
            //封面图片是否显示
            var fdShowCoverImg = $("[name='fdShowCoverImgM']",$showCoverImg).val();
            if(fdShowCoverImg=="1"){
                this.element.find("[name='fd_mobile_fdShowCoverImg'][value=1]").attr("checked",true);
                self.fdCoverImgGenertor.showElement();
            }else{
                this.element.find("[name='fd_mobile_fdShowCoverImg'][value=0]").attr("checked",true);
                self.fdCoverImgGenertor.hideElement();
            }
        },

        setClass: function(radioObj, value) {
            if(value == 1){
                $(radioObj).find(".view_flag_radio_no i").removeClass("view_flag_yes");
                $(radioObj).find(".view_flag_radio_yes i").addClass("view_flag_yes");
            }else if(value == 0){
                $(radioObj).find(".view_flag_radio_no i").addClass("view_flag_yes");
                $(radioObj).find(".view_flag_radio_yes i").removeClass("view_flag_yes");
            }else if(value == 2){
                $(radioObj).find(".view_flag_radio_no i").addClass("view_flag_yes");
                $(radioObj).find(".view_flag_radio_yes i").removeClass("view_flag_yes");
            }
        },

        getKeyData : function($super, cfg){
            var keyData = $super(cfg);
            //标题
            if (typeof this.fdSubjectGenertor.getKeyData != "undefined") {
                keyData.fdSubject = this.fdSubjectGenertor.getKeyData();
            }

            //每行显示个数
            keyData.fdColumnNum = this.element.find("[name='fd_"+ this.channel +"_columnSetting']").val();

            //是否显示封面图片
            keyData.fdShowCoverImg = this.element.find("input[name='fdShowCoverImgM']" ).val();

            keyData.fdCoverImg={};
            //封面图片
            if (typeof this.fdCoverImgGenertor.getKeyData != "undefined") {
                keyData.fdCoverImg = this.fdCoverImgGenertor.getKeyData();
            }

            //操作项
            keyData.fdOperation = [];
            for(var i = 0;i < this.operateCollection.length;i++){
                var operateWgt = this.operateCollection[i];
                keyData.fdOperation.push(operateWgt.getKeyData());
            }

            //摘要项
            keyData.fdSummary =  $.parseJSON(this.element.find("input[name*='fdSummary']").val() || "[]");
            keyData.fdSummaryText = this.element.find("input[name*='fdSummaryText']").val();

            //是否显示摘要项
            keyData.fdSummaryFlag = this.element.find("[name='fd_"+ this.channel +"_summaryFlag']").val();

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
            return isPass;
        },
        getModelingLang :function (){
            return modelingLang;
        }
    });

    exports.MobileCardView = MobileCardView;

})