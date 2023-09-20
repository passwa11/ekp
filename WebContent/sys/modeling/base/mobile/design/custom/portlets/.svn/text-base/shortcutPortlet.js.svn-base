/**
 * 快捷方式部件
 */
define(function(require, exports, module) {
    var $ = require('lui/jquery'),
        base = require('lui/base'),
        render = require('lui/view/render');
    var env = require("lui/util/env");
    var topic = require("lui/topic");
    var dialog = require("lui/dialog");
    var modelingLang = require("lang!sys-modeling-base");
    var basePortlet = require("sys/modeling/base/mobile/design/custom/basePortlet");
    var multiListAttr = require("sys/modeling/base/mobile/design/custom/portlets/multiListAttr");

    var ShortcutPortlet =  basePortlet.BasePortlet.extend({
        initProps: function ($super, cfg) {
            $super(cfg);
            this.config = cfg;
            this.container = cfg.container;
            this.shortcutType = cfg.type;
            this.parent=cfg.parent;
            this.listAttrWgts = [];
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
            var self = this;
            if(this.shortcutType == "shortcutSlideIcon"){
                //滑动切换快捷
                this.setRender(new render.Template({
                    src : "/sys/modeling/base/mobile/design/custom/portlets/slideShortcutRender.html#",
                    parent : this
                }));
            }else{
                this.setRender(new render.Template({
                    src : "/sys/modeling/base/mobile/design/custom/portlets/defaultShortcutRender.html#",
                    parent : this
                }));
            }
            this.render.startup();
            $super(cfg);
            //更新预览
            topic.subscribe("modeling.SpectrumColorPicker.change",function(obj){
                self.setLeftShow();
            });
        },
        doRender: function($super, cfg){
            var self = this;
            $super(cfg);
            this.element.appendTo(this.viewContainer).addClass("panel-tab-main-view");
            //画颜色选择器
            this.drawColorSelect();
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
        drawColorSelect : function(){
            var self = this;
            this.element.find(".shortcut_number_color_div").attr("data-color-mark-id","numberColorSelectValue"+"_"+self.randomName);
            this.element.find(".shortcut_text_color_div").attr("data-color-mark-id","textColorSelectValue"+"_"+self.randomName);
            this.element.find(".shortcut_background_color_div").attr("data-color-mark-id","backgroundColorSelectValue"+"_"+self.randomName);
            if (window.SpectrumColorPicker) {
                window.SpectrumColorPicker.init("numberColorSelectValue"+"_"+self.randomName);
                window.SpectrumColorPicker.init("textColorSelectValue"+"_"+self.randomName);
                window.SpectrumColorPicker.init("backgroundColorSelectValue"+"_"+self.randomName);
                var numberInitColor = "#000000";
                var textInitColor = "#FFFFFF";
                var backgroundInitColor = "#ffffff";
                if(this.shortcutType == "shortcutOneRow"){
                    numberInitColor = "#FFFFFF";
                    backgroundInitColor = "#4285f4";
                }else if(this.shortcutType == "shortcutDoubleRow"){
                    numberInitColor = "#4285f4";
                    textInitColor = "#333333";
                }
                window.SpectrumColorPicker.setColor("numberColorSelectValue"+"_"+self.randomName,numberInitColor);
                window.SpectrumColorPicker.setColor("textColorSelectValue"+"_"+self.randomName,textInitColor);
                window.SpectrumColorPicker.setColor("backgroundColorSelectValue"+"_"+self.randomName,backgroundInitColor);
            }
        },
        addEvent : function($super){
            var self = this;
            //标题
            // this.textTitleEvent();
            //部件新增事件
            var key = "listView";
            var isIcon = true;
            var isMulti = true;
            if(self.shortcutType != "shortcutSlideIcon"){
                key = "defaultShortcut";
                isIcon = false;
            }
            if(self.shortcutType == "shortcutSlideIcon"){
                //滑动图标是单页签
                isMulti = false;
            }
            this.element.find(".field_add").off("click").on("click",function (){
                var listAttr = new multiListAttr.MultiListAttr({
                    parent : self,
                    widgetKey:key,
                    area:self.element.find(".mobileAttrPanel"),
                    isCount:false,
                    isIcon:isIcon,
                    isMulti:isMulti,
                    data:{}
                });
                listAttr.startup();
                listAttr.draw();
            });
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
        //获取多语言资源
        getModelingLang :function (){
            return modelingLang;
        },
        validate:function($super) {
            var isPass = $super();

            return isPass;
        },
        initByStoreData : function(storeData) {
            var self = this;
            if (JSON.stringify(storeData) == "{}") {
                return;
            }
            this.element.find("[name='fdTitle']").val(storeData.attr.title.value);
            if(storeData.attr.title.isHide === "1"){
                this.element.find("[name='fdTitleIsHide']").attr("checked","checked");
            }else{
                this.element.find("[name='fdTitleIsHide']").removeAttr("checked");
            }
            var attrs = storeData.attr.listViews.value;
            var key = "shortcutSlideIcon";
            var isIcon = true;
            if(self.shortcutType != "shortcutSlideIcon"){
                key = "defaultShortcut";
                isIcon = false;
            }
            var isMulti = true;
            if(self.shortcutType == "shortcutSlideIcon"){
                //滑动图标是单页签
                isMulti = false;
            }
            if(attrs.length > 0) {
                for (var i = 0; i < attrs.length; i++) {
                    var listAttr = new multiListAttr.MultiListAttr({
                        parent : self,
                        widgetKey:key,
                        area:self.element.find(".mobileAttrPanel"),
                        isCount:false,
                        isIcon:isIcon,
                        isMulti:isMulti,
                        data:attrs[i]
                    });
                    listAttr.startup();
                    listAttr.draw();
                }
            }else{
                var listAttr = new multiListAttr.MultiListAttr({
                    parent : self,
                    widgetKey:key,
                    area:self.element.find(".mobileAttrPanel"),
                    isCount:false,
                    isIcon:isIcon,
                    isMulti:isMulti,
                    data:{}
                });
                listAttr.startup();
                listAttr.draw();
            }
            if (window.SpectrumColorPicker) {
                window.SpectrumColorPicker.setColor("numberColorSelectValue"+"_"+self.randomName,storeData.numberColor);
                window.SpectrumColorPicker.setColor("textColorSelectValue"+"_"+self.randomName,storeData.textColor);
                window.SpectrumColorPicker.setColor("backgroundColorSelectValue"+"_"+self.randomName,storeData.backgroundColor);
            }
            self.setLeftShow();
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
            keyData.numberColor =  this.element.find(".shortcut_number_color_div").find("input[type='hidden']").val();
            keyData.textColor =  this.element.find(".shortcut_text_color_div").find("input[type='hidden']").val();
            keyData.backgroundColor =  this.element.find(".shortcut_background_color_div").find("input[type='hidden']").val();
            return keyData;
        },
        initSwiper : function(){
            //已经存在滑块控件时，则不需要继续初始化，否则会导致无法控件滑动
            if(this.swiper){
                return;
            }
            var self = this;
            if(this.shortcutType == "shortcutSlideIcon"){
                   this.swiper = new Swiper(".mportal-swiper"+"_"+self.randomName, {
                       calculateHeight: true,
                       // paginationClickable:true,
                       pagination: '.mportal-pagination',
                       shortSwipes:true,
                       longSwipesRatio:0.1,
                       moveStartThreshold : 0
               });
            }else{
                this.swiper = new Swiper(".mportalList-swiper"+"_"+self.randomName, {
                    calculateHeight : true,
                    // paginationClickable:true,
                    pagination: '.mportalList-pagination',
                    shortSwipes:true,
                    longSwipesRatio:0.1,
                    moveStartThreshold : 0
                });
            }
        },
        erase: function() {
            if(this.swiper){
                this.swiper.removeAllSlides();	// 移除所有子元素
                this.isDrawed = false;
            }
        },
        sliceArrByNum : function(data , num){
            var result = [];
            for (var i = 0; i < data.length; i += num) {
                result.push(data.slice(i, i + num));
            }
            return result;
        },
        // 创建滑块
        createSlide : function(items,width,data){
            var slideHtml = "";
            //var width = parseFloat(100/items.length) + "%";
            var width = width;
            for(var i = 0;i < items.length;i++){
                slideHtml += this.getItemHtml(items[i], width,data);
            }
            if(slideHtml){
                this.swiper.appendSlide(slideHtml,"swiper-slide");
            }
        },
        getItemHtml : function(item, width,data){
            var itemHtml = "";
            if(this.shortcutType == "shortcutOneRow"){
                var num = "5";
                itemHtml += "<div class='mportalList-slide' style='width:"+ width +"'>" +
                    "<p style='color: "+data.numberColor+"'>"+ num +"</p>" +
                    "<span style='color:"+data.textColor+"'>"+ (item.title || modelingLang["modeling.Undefined"]) +"</span>" +
                    "</div>";
                return itemHtml;
            }else{
                itemHtml += "<div class='mportal-slide' style='width:"+ width +"'>" +
                    "<div class='mportal-slide-wrap'>" +
                    "<div class='mportal-slide-icon'>";
                if(item.iconType === '4'){
                    var url = env.fn.formatUrl(item.icon);
                    itemHtml += "<i class='mui' style='width: 24px;height: 24px;background: url("+url+")  no-repeat center;background-size:contain'></i>";
                }else{
                    itemHtml += "<i class='mui "+ (item.icon || 'mui-docRecord') +" '></i>";
                }
                itemHtml += "</div>" +
                    "<p style='color:"+data.textColor+"'>"+ (item.title || modelingLang["modeling.Undefined"]) +"</p>" +
                    "</div>" +
                    "</div>";
            }
            return itemHtml;
        },
        setLeft4Default: function($wgt,data){
            // 初始化swiper
            this.initSwiper();
            $wgt.find(".swiper-container").css("background-color",data.backgroundColor);
            var items = data.attr.listViews.value || [];
            this.swiper.removeAllSlides();	// 移除所有子元素
            var sliceArr = this.sliceArrByNum(items, 3);	// 分割数组，每3个元素为一组
            // 当有超过一屏时，出现分页
            if(sliceArr.length > 1){
                $wgt.find(".slide-pagination").addClass("active");
            }else{
                $wgt.find(".slide-pagination").removeClass("active");
            }
            for(var i = 0;i < sliceArr.length;i++){
                this.createSlide(sliceArr[i],"33%",data);
            }
            //重新初始化，计算分页
            this.swiper.reInit(true);
        },
        setLeft4Slide: function($wgt,data){
            // 初始化swiper
            this.initSwiper();
            var items = data.attr.listViews.value || [];
            this.swiper.removeAllSlides();	// 移除所有子元素
            var sliceArr = this.sliceArrByNum(items, 5);	// 分割数组，每5个元素为一组
            // 当有超过一屏时，出现分页
            if(sliceArr.length > 1){
                $wgt.find(".slide-pagination").addClass("active");
            }else{
                $wgt.find(".slide-pagination").removeClass("active");
            }
            for(var i = 0;i < sliceArr.length;i++){
                this.createSlide(sliceArr[i],"20%",data);
            }
            //重新初始化，计算分页
            this.swiper.reInit(true);
        },
        getDoubleRowItemHtml : function(item, width,data){
            //var num = Math.round(Math.random()*1000);
            var num = "5";
            var itemHtml = "<div class='mportal-collapse-item'";
            if(width){
                itemHtml += " style='width:"+ width +"'";
            }
            itemHtml += ">";
            itemHtml += "<div class='mportal-collapse-item-wrap'>";
            itemHtml += "<div style='color: "+data.numberColor+"'>" + num + "</div>";
            itemHtml += "<p style='color:"+data.textColor+"'>"+ (item.title || modelingLang["modeling.Undefined"]) +"</p>";
            itemHtml += "</div>";
            itemHtml += "</div>";
            return itemHtml;
        },
        drawContent : function($wgt,data) {
            $wgt.find(".mportal-collapse").empty();
            var staticsHtml = "";
            var items = data.attr.listViews.value || [];
            // 当方块大于fixNum个时，出现伸缩
            var fixNum = 6;
            var topSize = items.length > fixNum ? fixNum : items.length;
            staticsHtml += "<div class='mportal-collapse shrink' style='background-color: "+data.backgroundColor+"'>";
            staticsHtml += "<div class='mportal-collapse-wrap top'>";
            var width = topSize < 3 ? (parseInt(100 / topSize) + "%") : "";
            for (var i = 0; i < topSize; i++) {
                staticsHtml += this.getDoubleRowItemHtml(items[i], width,data);
            }
            staticsHtml += "</div>";
            // 出现伸展按钮
            if (items.length > fixNum) {
                staticsHtml += "<div class='mportal-collapse-wrap bottom'>";
                for (var i = fixNum; i < items.length; i++) {
                    staticsHtml += this.getDoubleRowItemHtml(items[i],"",data);
                }
                staticsHtml += "</div>";
                // 伸展按钮
                staticsHtml += '<div class="mportal-collapse-btn"></div>';
            }
            staticsHtml += "</div>";
            $wgt.append(staticsHtml);
            /************* 添加事件 start ************/
            var self = this;
            $wgt.find(".mportal-collapse-btn").on("click", function (e) {
                e.stopPropagation()
                $wgt.find('.mportal-collapse-wrap.bottom').slideToggle();
                $wgt.find('.mportal-collapse').toggleClass('shrink');
            });
        },
        //实现左右互联
        setLeftShow : function() {
            var self = this;
            var $currentPreviewWgt = this.parent.element.find("[data-id='"+this.randomName+"']");
            var data = this.getKeyData();
            var $title = $currentPreviewWgt.find(".modelAppSpaceWidgetDemoTypeTitle");
            if(data.attr.title.isHide === "1"){
                $title.hide();
            }else{
                $title.text(data.attr.title.value);
                $title.show();
            }
            $currentPreviewWgt.find('.modelingAppSpaceShortcutNumberDiv').css("background-color",data.backgroundColor);
            $currentPreviewWgt.find(".mportal-swiper").addClass("mportal-swiper_"+self.randomName);
            $currentPreviewWgt.find(".mportalList-swiper").addClass("mportalList-swiper_"+self.randomName);
            if(this.shortcutType == "shortcutSlideIcon"){
                self.setLeft4Slide($currentPreviewWgt,data);
            }else if(this.shortcutType == "shortcutDoubleRow"){
                this.drawContent($currentPreviewWgt,data);
            }else if(this.shortcutType == "shortcutOneRow"){
                self.setLeft4Default($currentPreviewWgt,data);
            }
        }
    })
    module.exports = ShortcutPortlet;
})
