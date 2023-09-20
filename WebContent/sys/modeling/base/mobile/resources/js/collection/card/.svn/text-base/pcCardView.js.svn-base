/**
 * pc卡片视图的视图组件
 */
define(function(require, exports, module) {

    var $ = require('lui/jquery'),
        base = require('lui/base'),
        str = require('lui/util/str'),
        dialog = require('lui/dialog'),
        render = require('lui/view/render'),
        topic = require('lui/topic'),
        collectionBaseView = require('sys/modeling/base/mobile/resources/js/collectionBaseView'),
        displayCssSet = require('sys/modeling/base/listview/config/js/displayCssSet'),
        pagingSetGenerator = require('sys/modeling/base/mobile/resources/js/pagingSetGenerator'),
        columnSetGenerator = require('sys/modeling/base/mobile/resources/js/collection/card/columnSetGenerator'),
        dropdownGenerator = require('sys/modeling/base/mobile/resources/js/dropdownGenerator'),
        operateGenerator = require('sys/modeling/base/mobile/resources/js/operateGenerator');
    var modelingLang = require("lang!sys-modeling-base");
    var PcCardView = collectionBaseView["CollectionBaseView"].extend({

        initProps: function($super, cfg) {
            $super(cfg);
            this.id = "pc";
            this.channel = "pc";
            this.key = "fdPcCfg";
            this.storeData = cfg.data || {};
            this.fdType = cfg.fdType || 0;
            this.sysModelingOperationJson = cfg.sysModelingOperationJson;
        },

        startup : function($super, cfg) {

            this.setRender(new render.Template({
                src : "/sys/modeling/base/views/collection/card/pcViewRender.html#",
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
            //摘要项是否显示
            var fdSummaryFlag = this.element.find("[name='fdSummaryFlag']").val();
            var radioObj = $("#" + this.channel + "_summaryflag");
            this.setClass(radioObj, fdSummaryFlag);
            var self = this;
            this.element.find("[name='summaryflag']").each(function (index, obj){
                $(obj).on("click",function() {
                    var value =$(this).attr("value");
                    var curVal = $("[name='fdSummaryFlag']",self.element).val();
                    if(curVal == value){
                        return;
                    }
                    var radioObj = $(this).parents(".view_flag_radio")[0];
                    self.setClass(radioObj, value);
                    $("[name='fdSummaryFlag']",self.element).val(value);
                });
            });
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

        },

        renderCustom : function() {
            //渲染显示样式
            this.renderDisplayCssSet();
            //分页器
            this.renderPagingSet();
            //卡片展示个数
            this.renderColumnSet();
            //标题
            this.renderSubject();
            //封面
            this.renderCoverImg();
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
                    var curVal = $("[name='fdShowCoverImg']",$showCoverImg).val();
                    if(curVal == value){
                        return;
                    }
                    if(value == "1"){
                        self.fdCoverImgGenertor.showElement();
                    }else{
                        self.fdCoverImgGenertor.hideElement();
                    }

                    $("[name='fdShowCoverImg']",$showCoverImg).val(value);
                    topic.publish("preview.refresh", {"key": self.channel});
                    var context = $("#modeling-pam-content-pc");
                    window.changeRightContentView("design");
                    $("[data-lui-position]", context).removeClass("active");
                    $("[data-lui-position='fdDisplay']", context).addClass("active");
                });
            });
            //封面图片是否显示
            var fdShowCoverImg = $("[name='fdShowCoverImg']",$showCoverImg).val();
            if(fdShowCoverImg=="1"){
                this.element.find("[name='fd_pc_fdShowCoverImg'][value=1]").attr("checked",true);
                self.fdCoverImgGenertor.showElement();
            }else{
                this.element.find("[name='fd_pc_fdShowCoverImg'][value=0]").attr("checked",true);
                self.fdCoverImgGenertor.hideElement();
            }

            $('#modeling-pam-content-pc .boardCardConfigTitleIcon').on('click',function(){
                if($(this).parents('.boardCardConfig').hasClass('slideUp')){
                    $(this).parents('.boardCardConfig').removeClass('slideUp')
                }else{
                    $(this).parents('.boardCardConfig').addClass('slideUp')
                }
            });
        },

        renderDisplayBlock: function() {
            var renderData = this.storeData;
            //显示
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

        renderPagingSet : function() {
            this.pagingSet = new pagingSetGenerator({container:this.element,parent:this, channel: this.channel,data: this.storeData.fdPageSetting});
            this.pagingSet.startup();
            this.pagingSet.draw();
        },

        renderColumnSet : function (){
         this.columnSet   = new columnSetGenerator({container:this.element,parent:this, channel: this.channel,data: this.storeData.fdColumnNum});
         this.columnSet.startup();
         this.columnSet.draw();

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
                topic.channel(this.channel).publish("modeling.selectDisplay.change",{'thisObj':null,'data':data});
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

        setClass: function(radioObj, value) {
            if(value == 1){
                $(radioObj).find(".view_flag_radio_no i").removeClass("view_flag_yes");
                $(radioObj).find(".view_flag_radio_yes i").addClass("view_flag_yes");
            }else if(value == 0){
                $(radioObj).find(".view_flag_radio_no i").addClass("view_flag_yes");
                $(radioObj).find(".view_flag_radio_yes i").removeClass("view_flag_yes");
            }
        },

        getKeyData : function($super, cfg){
            var keyData = $super(cfg);
            delete keyData.fdDisplay;
            delete keyData.fdDisplayText;

            //摘要项
            keyData.fdSummary =  $.parseJSON(this.element.find("input[name*='fdSummary']").val() || "[]");
            keyData.fdSummaryText = this.element.find("input[name*='fdSummaryText']").val();

            //是否显示摘要项
            keyData.fdSummaryFlag = this.element.find("input[name*='fdSummaryFlag']" ).val();

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
            keyData.fdPageSetting = "1";
            //分页设置
            if(typeof this.pagingSet != "undefined") {
                keyData.fdPageSetting = this.pagingSet.getKeyData() || '1';
            }
            //卡片展示设置
            if(typeof this.columnSet != "undefined") {
                keyData.fdColumnNum = this.columnSet.getKeyData() || '4';
            }

            //是否显示封面图片
            keyData.fdShowCoverImg = this.element.find("input[name='fdShowCoverImg']" ).val();

            keyData.fdCoverImg = {};
            //封面图片
            if (this.fdCoverImgGenertor && typeof this.fdCoverImgGenertor.getKeyData != "undefined") {
                keyData.fdCoverImg = this.fdCoverImgGenertor.getKeyData();
            }

            keyData.fdSubject = {};
            //标题
            if (this.fdSubjectGenertor && typeof this.fdSubjectGenertor.getKeyData != "undefined") {
                keyData.fdSubject = this.fdSubjectGenertor.getKeyData();
            }

            // 显示项由标题字段和摘要显示项组成
            var fdDisplay = [];
            $.extend(fdDisplay, keyData.fdSummary);
            var hasSubject = false;
            for(var i = 0;i < fdDisplay.length;i++){
                if(fdDisplay[i].field === keyData.fdSubject.field){
                    hasSubject = true;
                    fdDisplay[i].busType = "subject";	// 业务类型
                }else{
                    fdDisplay[i].busType = "summary";
                }
            }
            if(!hasSubject){
                fdDisplay.push($.extend({busType:"subject"},keyData.fdSubject));
            }
            keyData.fdDisplay = fdDisplay;

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
    exports.PcCardView = PcCardView;
})