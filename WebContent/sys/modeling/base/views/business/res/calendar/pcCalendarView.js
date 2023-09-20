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
        collectionBaseView = require('sys/modeling/base/mobile/resources/js/collectionBaseView'),
        calendarDisplayCssSet = require('sys/modeling/base/views/business/res/calendar/calendarDisplayCssSet'),
        dropdownGenerator = require('sys/modeling/base/mobile/resources/js/dropdownGenerator'),
        operateGenerator = require('sys/modeling/base/mobile/resources/js/operateGenerator'),
        summaryFieldGenerator = require('sys/modeling/base/views/business/res/calendar/summaryFieldGenerator');
    //公式定义器
    var formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder");

    var PcCalendarView = collectionBaseView["CollectionBaseView"].extend({

        initProps: function($super, cfg) {
            $super(cfg);
            this.id = "pc";
            this.channel = "pc";
            this.key = "fdPcCfg";
            this.storeData = cfg.data || {};
            this.xformId = cfg.xformId || "";
        },

        startup : function($super, cfg) {
            this.setRender(new render.Template({
                src : "/sys/modeling/base/views/business/res/calendar/pcViewRender.html#",
                parent : this
            }));
            this.render.startup();
            $super(cfg);
            topic.subscribe("switchRightContentView",this.switchRightContentView, this);
            topic.channel(this.channel).subscribe("modeling.fdCalendarShowField.click",this.builderTitleDisplayCss,this);
        },

        switchRightContentView: function(data) {
            if (data && data.key === this.channel && !this.isRefreshSelectBlock) {
                this.renderConditionBlock();
                this.isRefreshSelectBlock = true;
            }
        },

        doRender: function($super, cfg){
            $super(cfg);
        },

        renderBlock : function($super) {
            $super();
            // var renderData = this.storeData;
            // var $operate = this.element.find("[data-table-type='operate']");
            // //操作
            // if (renderData.fdOperation && renderData.fdOperation.length) {
            //     if (renderData.fdOperation.length > 0) {
            //         $operate.parent().show();
            //     }
            //     var fdOperationIds = [];
            //     var fdOperationNames = [];
            //     for (var i = 0; i < renderData.fdOperation.length; i++) {
            //         var operateInfo = renderData.fdOperation[i];
            //         fdOperationIds.push(operateInfo.fdId);
            //         fdOperationNames.push(operateInfo.fdName);
            //         var operateWgt = new operateGenerator({container: $operate, parent: this, data: operateInfo,channel: this.channel});
            //         operateWgt.startup();
            //         operateWgt.draw();
            //         this.operateCollection.push(operateWgt);
            //     }
            //     if (fdOperationIds.length > 0 && fdOperationNames.length > 0) {
            //         $(this.element).find("[name='operationIds']").val(fdOperationIds.join(";"));
            //         $(this.element).find("[name='operationNames']").val(fdOperationNames.join(";"));
            //     }
            //     this.sortable("operate");
            // }

        },

        renderSummary: function() {
            var renderData = this.storeData;
            //摘要项
            var $summary = this.element.find("[data-table-type='summary']");
            this.summaryGenerator = new summaryFieldGenerator.SummaryFieldGenerator({container: $summary, parent: this, data: renderData.fdSummary,channel: this.channel,xformId:this.xformId});
            this.summaryGenerator.startup();
            this.summaryGenerator.draw();
            var self = this;
            this.element.find(".summaryTr .model-data-preview").on("click",function(e) {
                e.stopPropagation();
                var showMode = self.element.find(".view_flag_radio").attr("view-flag-radio-value");
                if (showMode && showMode === "0") {
                    $('.model-source-calendar-week .preview-relative').toggleClass("active");
                    var summaryPreview = $(".modeling-pam-content-pc-left .model-source-calendar-week .model-source-table-day-title");
                    var left = summaryPreview.width()/2;
                    var top = summaryPreview.height()/2;
                    $('.model-source-calendar-week .preview-relative').css("top",top+"px").css("left",left+"px");
                }else {
                    $('.model-source-calendar-year .preview-relative').toggleClass("active");
                    var summaryPreview = $(".modeling-pam-content-pc-left .model-source-calendar-year .model-source-table-day-title");
                    var left = summaryPreview.width()/2;
                    var top = summaryPreview.height()*1.5;
                    $('.model-source-calendar-year .preview-relative').css("top",top+"px").css("left",left+"px");
                }

            })
            $(window).on("click",function() {
                $('.preview-relative').removeClass("active");
            })
        },

        renderCustom : function() {
            //渲染显示样式
            this.renderDisplayCssSet();
            //渲染标题
            this.renderCalendarTitle();

            //日历显示字段
            this.renderCalendarShowField();
            //时间字段
            this.renderDateField();

            //默认显示周期
            this.renderShowMode();

            //渲染摘要
            this.renderSummary();
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
                displayType:"calendar",
                channel:this.channel
            };
            this.displayCssSetIns = new calendarDisplayCssSet.CalendarDisplayCssSet(displayCssSetCfg);

            if(typeof this.storeData.fdCalendarShowField != "undefined" && this.storeData.fdCalendarShowField != "{}"){
                var modelDictData = listviewOption.baseInfo.modelDict;
                var allFieldData = listviewOption.baseInfo.fieldInfos;
                var fields=[];
                fields.push(this.storeData.fdCalendarShowField);
                var data = {};
                var text = [];
                text.push(this.storeData.fdCalendarShowField.text);
                data.selected = this.doAdaptorOldData(modelDictData,allFieldData,fields);
                data.text = text;
                //显示项样式改变事件
                topic.channel(this.channel).publish("modeling.selectDisplay.change",{'thisObj':null,'data':data});
            }
            var self = this;
            this.element.find(".displaycss_create").on("click",function() {
                self.displayCssSetIns.displayCssSet(0);
            })
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

        renderCalendarTitle: function(){
            //#1  图表标题
            //去除变量
            var tableTitle = formulaBuilder.get_style2("fdCalendarTitle", "String", null, null, function () {
                return [];
            });
            this.element.find(".model-calendar-title").append(tableTitle);
            var data = this.storeData.fdCalendarTitle;
            if(data){
                this.element.find("[name=fdCalendarTitle]").val(data.value);
                this.element.find("[name=fdCalendarTitle_name]").val(data.text);
            }
        },

        renderSubject:function (){

        },

        renderCalendarShowField: function(){
            var data = JSON.stringify(this.storeData.fdCalendarShowField);
            var fdCalendarShowFieldGenertorCfg = {
                container:$(".fdCalendarShowFieldTr", this.element),
                data:data,
                channel:this.channel,
                name:"fdCalendarShowField",
                required:"true"
            };
            this.fdCalendarShowFieldGenertor = new dropdownGenerator.DropdownGenerator(fdCalendarShowFieldGenertorCfg);
        },

        renderDateField: function(){
            var data = JSON.stringify(this.storeData.fdDateField);
            var fdDateFieldCfg = {
                container:$(".fdDateFieldTr", this.element),
                data:data,
                channel:this.channel,
                name:"fdDateField",
                required:"true"
            };
            this.fdDateFieldGenertor = new dropdownGenerator.DropdownGenerator(fdDateFieldCfg);
        },

        renderShowMode: function () {
            var self = this;
            this.element.find(".view_flag_radio").each(function (i, radio) {
                var $radio = $(radio);
                $(radio).find("i").removeClass("view_flag_yes");
                $radio.find(".view_flag_radio_item").each(function (i, radioItem) {
                    //默认值
                    var val_o = $(radioItem).attr("view-flag-radio-value");
                    var radioVal_o = $(radio).attr("view-flag-radio-value");
                    if (val_o === radioVal_o) {
                        $(radioItem).find("i").addClass("view_flag_yes");
                    }
                    //点击事件绑定
                    $(radioItem).on("click", function () {
                        $(radio).find("i").removeClass("view_flag_yes");
                        var val_t = $(this).attr("view-flag-radio-value");
                        var radioVal_t = $(radio).attr("view-flag-radio-value");
                        //值不改变
                        $(this).find("i").addClass("view_flag_yes");
                        if (val_t === radioVal_t) {
                            return
                        }
                        if (val_t == "0") {
                            $(".model-source-calendar-year").removeClass("active");
                            $(".model-source-calendar-week").addClass("active");
                        }else{
                            $(".model-source-calendar-year").addClass("active");
                            $(".model-source-calendar-week").removeClass("active");
                        }

                        //值改变
                        $(radio).attr("view-flag-radio-value", val_t);
                        //刷新预览
                        topic.publish("preview.refresh", {key: self.channel});
                        topic.publish("switchSelectPosition",{'dom':this, key: self.channel});
                    })
                })
            })
        },

        builderTitleDisplayCss:function(){
            var data = {};
            var fdCalendarShowField = this.fdCalendarShowFieldGenertor.getKeyData();
            var modelDictData = listviewOption.baseInfo.modelDict;
            var allFieldData = listviewOption.baseInfo.fieldInfos;
            var fields=[];
            fields.push(fdCalendarShowField);
            data.selected = this.doAdaptorOldData(modelDictData,allFieldData,fields);
            var text = [];
            text.push(fdCalendarShowField.text);
            data.text = text;
            //显示项样式改变事件
            if(this.channel){
                topic.channel(this.channel).publish("modeling.selectDisplay.change",{'thisObj':this,'data':data});
            }else{
                topic.publish("modeling.selectDisplay.change",{'thisObj':this,'data':data});
            }
            //刷新预览
            topic.publish("preview.refresh", {key: this.channel});
            topic.publish("switchSelectPosition",{'dom':this, key: this.channel});
        },

        getKeyData : function($super, cfg){
            var keyData = $super(cfg);
            delete keyData.fdDisplay;
            delete keyData.fdDisplayText;

            //摘要项
            keyData.fdSummary =  [];

            if(this.summaryGenerator && typeof this.summaryGenerator.getKeyData != "undefined"){
                keyData.fdSummary = this.summaryGenerator.getKeyData();
            }

            keyData.fdCalendarTitle = {};
            keyData.fdCalendarTitle.value = this.element.find("input[name='fdCalendarTitle']").val();
            keyData.fdCalendarTitle.text = this.element.find("input[name='fdCalendarTitle_name']").val();

            //操作项
            keyData.fdOperation = [];
            for(var i = 0;i < this.operateCollection.length;i++){
                var operateWgt = this.operateCollection[i];
                keyData.fdOperation.push(operateWgt.getKeyData());
            }

            //显示样式
            if (this.displayCssSetIns && typeof this.displayCssSetIns.getKeyData != "undefined") {
                keyData.fdDisplaySet = this.displayCssSetIns.getKeyData();
            }

            keyData.fdCalendarShowField = {};
            //日历显示字段
            if (this.fdCalendarShowFieldGenertor && typeof this.fdCalendarShowFieldGenertor.getKeyData != "undefined") {
                keyData.fdCalendarShowField = this.fdCalendarShowFieldGenertor.getKeyData();
            }

            keyData.fdDateField = {};
            //时间字段
            if (this.fdDateFieldGenertor && typeof this.fdDateFieldGenertor.getKeyData != "undefined") {
                keyData.fdDateField = this.fdDateFieldGenertor.getKeyData();
            }

            //默认周期
            keyData.showMode = this.element.find(".view_flag_radio").attr("view-flag-radio-value") || "1";

            return keyData;
        }
    });
    exports.PcCalendarView = PcCalendarView;
})