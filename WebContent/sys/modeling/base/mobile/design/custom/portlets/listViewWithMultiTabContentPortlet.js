define(function(require, exports, module) {
    var $ = require('lui/jquery'),
        render = require('lui/view/render');
    var dialog = require("lui/dialog");
    var modelingLang = require("lang!sys-modeling-base");
    var basePortlet = require("sys/modeling/base/mobile/design/custom/basePortlet");
    var multiListAttr = require("sys/modeling/base/mobile/design/custom/portlets/multiListAttr");

    var ListViewWithMultiTabContentPortlet = basePortlet.BasePortlet.extend({
        initProps: function ($super, cfg) {
            $super(cfg);
            this.config = cfg;
            this.parent=cfg.parent;
            this.listAttrWgts = [];
            this.isCount = false;
            this.isIcon = false;
            this.isMulti = false;
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
                src : "/sys/modeling/base/mobile/design/custom/portlets/listViewWithMultiTabContentViewRender.html#",
                parent : this
            }));
            this.render.startup();
            $super(cfg);
        },
        doRender: function($super, cfg){
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
                    widgetKey:"listViewWithMultiTabContent",
                    area:self.element.find(".mobileAttrPanel"),
                    isCount:self.isCount,
                    isIcon:self.isIcon,
                    isMulti:self.isMulti,
                    data:{}
                });
                listAttr.startup();
                listAttr.draw();
            })
            this.addValidateElements(this.element.find("[name=fdContentNumber]")[0],"number integer digits min(1) max(15)");
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
            keyData.attr.fixNumber = this.element.find("[name='fdContentNumber']").val();
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
            this.element.find("[name='fdContentNumber']").val(storeData.attr.fixNumber);
            if(storeData.attr.listViews.value.length > 0){
                for (var i = 0; i < storeData.attr.listViews.value.length; i++) {
                    var listAttr = new multiListAttr.MultiListAttr({
                        parent : this,
                        widgetKey:"listViewWithMultiTabContent",
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
                    widgetKey:"listViewWithMultiTabContent",
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
            var $signBox = $currentPreviewWgt.find(".modelAppSpaceWidgetDemoTypeSignBox");
            if(data.attr.title.isHide == "1"){
                $signBox.find(".modelAppSpaceWidgetDemoTypeSignTitle>span").hide();
            }else{
                $signBox.find(".modelAppSpaceWidgetDemoTypeSignTitle>span").show();
                $signBox.find(".modelAppSpaceWidgetDemoTypeSignTitle>span").text(data.attr.title.value);
            }
            var $signTitle = $signBox.find(".modelAppSpaceWidgetDemoTypeSignTitle");
            var $signTitleTab = $signBox.find(".modelAppSpaceWidgetDemoTypeSignTitle>ul");
            var $signContent = $signBox.find(".modelAppSpaceWidgetDemoTypeSignContent");
            $signTitleTab.empty();
            $signContent.empty();
            for (var i = 0; i < data.attr.listViews.value.length;i++) {
                var wgtValue = data.attr.listViews.value[i];
                var $listViewHtml = $('<li></li>');
                var $signContentHtml = $(this.getSignContentHtml());
                if(i === 0){
                    $listViewHtml.addClass("active");
                    $signContentHtml.show();
                }else{
                    $signContentHtml.hide();
                }
                $listViewHtml.text(wgtValue.title || modelingLang["modeling.Undefined"]);
                $signTitleTab.append($listViewHtml);
                $signContent.append($signContentHtml);
            }
            if($signTitle.width() < 162 + $signTitleTab.width()){
                $signTitleTab.css("float","left");
                $signBox.find(".modelAppSpaceWidgetDemoTypeSignTitle>span").css("width","100%");
            }else{
                $signTitleTab.css("float","right");
                $signBox.find(".modelAppSpaceWidgetDemoTypeSignTitle>span").css("width","162px");
            }
            // 页签切换
            $currentPreviewWgt.find(".modelAppSpaceWidgetDemoTypeSignTitle>ul>li").on('click',function(e){
                e.preventDefault();
                e.stopPropagation();
                var idx = $(this).index();
                if(!$(this).hasClass('active')){
                    $(this).addClass('active').siblings().removeClass('active');
                }
                $currentPreviewWgt.find(".modelAppSpaceWidgetDemoTypeSignContentItem").eq(idx).show();
                $currentPreviewWgt.find(".modelAppSpaceWidgetDemoTypeSignContentItem").eq(idx).siblings().hide();
            })
        },

        getSignContentHtml:function() {
            return '                                    <div class="modelAppSpaceWidgetDemoTypeSignContentItem">\n' +
                '                                        <ul>\n' +
                '                                            <li>\n' +
                '                                                <div class="modelAppSpaceWidgetDemoTypeSignItemTitle">\n' +
                '                                                    2019公司年中财务报告\n' +
                '                                                </div>\n' +
                '                                                <div class="modelAppSpaceWidgetDemoTypeSignItemInfo">\n' +
                '                                                    <i>2019-09-02</i>\n' +
                '                                                    <span>32观看</span>\n' +
                '                                                </div>\n' +
                '                                            </li>\n' +
                '                                        </ul>\n' +
                '                                    </div>\n';
        }
    });
    module.exports = ListViewWithMultiTabContentPortlet;
});