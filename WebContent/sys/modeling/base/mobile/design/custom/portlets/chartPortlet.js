define(function(require, exports, module) {
    var $ = require('lui/jquery'),
        base = require('lui/base'),
        render = require('lui/view/render');
    var topic = require("lui/topic");
    var dialog = require("lui/dialog");
    var modelingLang = require("lang!sys-modeling-base");
    var basePortlet = require("sys/modeling/base/mobile/design/custom/basePortlet");
    var listAttrBase = require("sys/modeling/base/mobile/design/custom/portlets/listAttrBase");

    var ChartPortlet = basePortlet.BasePortlet.extend({
        initProps: function ($super, cfg) {
            $super(cfg);
            this.config = cfg;
            this.parent=cfg.parent;
            this.tmpData = {
                attr : {
                    title:{
                        isHide:"1"
                    },
                    listViews : {
                        validate : {},	// 校验器
                        value : []
                    }
                }
            };
            this.data = $.extend(this.tmpData,cfg.data || {});
        },
        startup : function($super, cfg) {
            this.setRender(new render.Template({
                src : "/sys/modeling/base/mobile/design/custom/portlets/chartViewRender.html#",
                parent : this
            }));
            this.render.startup();
            $super(cfg);
        },
        doRender: function($super, cfg){
            var self = this;
            $super(cfg);
            this.element.appendTo(this.viewContainer).addClass("panel-tab-main-view");
            var attrConfig = {parent : this,widgetKey:"chart",area:this.element.find(".chartListView")};
            if(this.data.attr.listViews.value.length > 0){
                attrConfig.data = this.data.attr.listViews.value[0];
            }
            this.listAttr = new listAttrBase.ListAttrBase(attrConfig);
            this.listAttr.startup();
            this.listAttr.draw();
            //事件
            this.addEvent();
            this.initByStoreData(this.data);
            if(this.parent.wgtPortlet === this){
                this.element.siblings().hide();
            }else{
                this.element.hide();
            }
        },

        triggerActiveWgt:function($super) {
            $super();
        },

        draw : function($super, cfg){
            $super(cfg);
            this.render.get(this.data);
        },
        addEvent : function($super){
            var self = this;
            $super();
        },
        //获取多语言资源
        getModelingLang :function (){
            return modelingLang;
        },
        validate:function($super) {
            var isPass = $super();
            return isPass;
        },

        getKeyData : function() {
            var keyData = {
                attr : {
                    title:{},
                    listViews : {
                        validate : {},	// 校验器
                        value : []
                    }
                }
            };
            keyData.attr.title.value =  this.element.find("[name='fdTitle']").val();
            keyData.attr.title.isHide =  this.element.find("[name='fdTitleIsHide']").is(":checked") ? "1" : "0";
            keyData.attr.listViews.value.push(this.listAttr.getKeyData());
            return keyData;
        },
        initByStoreData : function(storeData) {
            if (JSON.stringify(storeData) == "{}") {
                return;
            }
            this.element.find("[name='fdTitle']").val(storeData.attr.title.value);
            if(storeData.attr.title.isHide === "1"){
                this.element.find("[name='fdTitleIsHide']").attr("checked","checked");
            }else{
                this.element.find("[name='fdTitleIsHide']").removeAttr("checked");
            }
            this.setLeftShow();
        },
        //实现左右互联
        setLeftShow : function() {
            var $currentPreviewWgt = this.parent.element.find("[data-id='"+this.randomName+"']");
            var data = this.getKeyData();
            var $title = $currentPreviewWgt.find(".modelAppSpaceWidgetDemoTypeTitle");
            if(data.attr.title.isHide === "1"){
                $title.hide();
            }else{
                $title.text(data.attr.title.value);
                $title.show();
            }
            if(data.attr.listViews.value.length > 0){
                $currentPreviewWgt.find(".modelAppSpaceWidgetDemoTypeCover")
                    .css("background","url('../mobile/design/images/chart-default.png') no-repeat center center")
                    .css("background-size","contain");
            }

        },
        destroy : function($super) {
            this.listAttr.destroy();
            $super();
        }
    });
    module.exports = ChartPortlet;
});