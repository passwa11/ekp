define(function(require, exports, module) {
    var $ = require('lui/jquery'),
        render = require('lui/view/render');
    var env = require("lui/util/env");
    var dialog = require("lui/dialog");
    var modelingLang = require("lang!sys-modeling-base");
    var basePortlet = require("sys/modeling/base/mobile/design/custom/basePortlet");
    var multiListAttr = require("sys/modeling/base/mobile/design/custom/portlets/multiListAttr");

    var ListViewWithIconPortlet = basePortlet.BasePortlet.extend({
        initProps: function ($super, cfg) {
            $super(cfg);
            this.config = cfg;
            this.parent=cfg.parent;
            this.listAttrWgts = [];
            this.isCount = false;
            this.isIcon = true;
            this.isMulti = true;
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
            this.data.uuId = this.randomName;
        },
        startup : function($super, cfg) {
            this.setRender(new render.Template({
                src : "/sys/modeling/base/mobile/design/custom/portlets/listViewWithIconViewRender.html#",
                parent : this
            }));
            this.render.startup();
            $super(cfg);
        },
        doRender: function($super, cfg){
            var self = this;
            $super(cfg);
            this.element.appendTo(this.viewContainer).addClass("panel-tab-main-view");
            //事件
            this.addEvent();
            this.initByStoreData(this.data);
            if(this.parent.wgtPortlet === this){
                this.element.siblings().hide();
            }else{
                this.element.hide();
            }
        },

        draw : function($super, cfg){
            $super(cfg);
            this.render.get(this.data);
        },
        addEvent : function($super){
            var self = this;
            this.element.find(".field_add").off("click").on("click",function (){
                var listAttr = new multiListAttr.MultiListAttr({
                    parent : self,
                    widgetKey:"listView",
                    area:self.element.find(".mobileAttrPanel"),
                    isCount:self.isCount,
                    isIcon:self.isIcon,
                    isMulti:true,
                    data:{}
                });
                listAttr.startup();
                listAttr.draw();
            })
            $super();
        },

        addWgt:function(wgt) {
            this.listAttrWgts.push(wgt);
            this.setLeftShow();
        },

        deleteWgt:function(wgt) {
            for (var i = 0; i < this.listAttrWgts.length; i++) {
                if (this.listAttrWgts[i] === wgt) {
                    this.listAttrWgts.splice(i, 1);
                    break;
                }
            }
            this.setLeftShow();
        },

        destroy : function($super) {
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
            for (var i = 0; i < this.listAttrWgts.length; i++) {
                keyData.attr.listViews.value.push(this.listAttrWgts[i].getKeyData());
            }
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
            if(storeData.attr.listViews.value.length > 0){
                for (var i = 0; i < storeData.attr.listViews.value.length; i++) {
                    var listAttr = new multiListAttr.MultiListAttr({
                        parent : this,
                        widgetKey:"listView",
                        area:this.element.find(".mobileAttrPanel"),
                        isCount:this.isCount,
                        isIcon:this.isIcon,
                        isMulti:this.isMulti,
                        data:storeData.attr.listViews.value[i]
                    });
                    listAttr.startup();
                    listAttr.draw();
                }
            }else{
                var listAttr = new multiListAttr.MultiListAttr({
                    parent : this,
                    widgetKey:"listView",
                    area:this.element.find(".mobileAttrPanel"),
                    isCount:this.isCount,
                    isIcon:this.isIcon,
                    isMulti:this.isMulti,
                    data: {}
                });
                listAttr.startup();
                listAttr.draw();
            }
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
            $currentPreviewWgt.find(".modelAppSpaceWidgetDemoTypeEnter").remove();
            for (var i = 0; i < data.attr.listViews.value.length;i++) {
                var wgtValue = data.attr.listViews.value[i];
                var $listViewHtml = $('<div class="modelAppSpaceWidgetDemoTypeEnter">\n' +
                    '                                </div>');
                var $icon = $("<i></i>").addClass("mui");
                if(wgtValue.iconType === "4"){
                    var url = env.fn.formatUrl(wgtValue.icon);
                    $icon.css("background","url(" + url + ")  no-repeat center").css("background-size","contain");
                }else{
                    $icon.addClass(wgtValue.icon);
                }
                $("<strong>快捷入口1</strong>").text(wgtValue.title || modelingLang["modeling.Undefined"]).appendTo($listViewHtml);
                $listViewHtml.append('<span class="modelAppSpaceWidgetDemoIconLinkN"></span>');
                $currentPreviewWgt.append($listViewHtml);
            }
        }
    });
    module.exports = ListViewWithIconPortlet;
});