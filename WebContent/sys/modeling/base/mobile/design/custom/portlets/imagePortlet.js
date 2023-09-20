define(function(require, exports, module) {
    var $ = require('lui/jquery'),
        base = require('lui/base'),
        render = require('lui/view/render');
    var topic = require("lui/topic");
    var dialog = require("lui/dialog");
    var modelingLang = require("lang!sys-modeling-base");
    var basePortlet = require("sys/modeling/base/mobile/design/custom/basePortlet");

    var ImagePortlet = basePortlet.BasePortlet.extend({
        initProps: function ($super, cfg) {
            $super(cfg);
            this.config = cfg;
            this.parent=cfg.parent;
            this.data = cfg.data || {};
        },
        startup : function($super, cfg) {
            this.setRender(new render.Template({
                src : "/sys/modeling/base/mobile/design/custom/portlets/imageViewRender.html#",
                parent : this
            }));
            this.render.startup();
            $super(cfg);
            var self = this;
            //更新预览
            topic.subscribe("modeling.SpectrumColorPicker.change",function(obj){
                if(obj.valueName == "textFillColorSelectValue"+self.randomName){
                    self.setLeftShow();
                }
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
            this.element.find(".text_fill_color_div").attr("data-color-mark-id","textFillColorSelectValue"+this.randomName);
            if (window.SpectrumColorPicker) {
                window.SpectrumColorPicker.init("textFillColorSelectValue"+this.randomName);
                window.SpectrumColorPicker.setColor("textFillColorSelectValue"+this.randomName,"#FFFFFF");
            }
        },
        addEvent : function($super){
            var self = this;
            //背景填充方式
            this.backgroundEvent();

            //选择图片
            var $selectImg=this.element.find(".modelAppSpacePorletsImageUploadBtn");
            var $checkedImg=this.element.find(".modelAppSpacePorletsImageUploadedBtn");
            $selectImg.off("click").on("click",function(){
                self.selectMaterial();
            });
            $checkedImg.off("click").on("click",function(){
                self.selectMaterial();
            });
            // 控制上传图片,和删除
            this.imageUploadMask();

            //下拉框列表点击事件
            this.element.find("[name='fdFillType_"+self.randomName+"']").off("click").on("click",function(e){
                e?e.stopPropagation():event.cancelBubble = true;
                self.setLeftShow();
            });
            this.addValidateElements(this.element.find(".modelAppSpacePorletsImageUploadedBtn").find("input")[0],"required");
            $super();
        },
        //获取多语言资源
        getModelingLang :function (){
            return modelingLang;
        },
        backgroundEvent : function(){
            var self = this;
            //下拉框列表点击事件
            this.element.find('.modelAppSpacePorletsInlineRow .muiPerformanceDropdownBoxList>ul>li').off("click").on("click",function(e){
                e?e.stopPropagation():event.cancelBubble = true;
                self.selectShowText(this);
                self.setLeftShow();
            });
        },
        selectShowText : function(li){
            $(li).parents('.muiPerformanceDropdownBox').children('span').text($(li).text().trim());
            $(li).parents('.muiPerformanceDropdownBox').children("input[type='text']").val($(li).text().trim());
            $(li).parents('.muiPerformanceDropdownBox').find('.muiPerformanceDropdownBoxList').hide();
            $(li).parents('.muiPerformanceDropdownBox').removeClass('active');
        },
        imageUploadMask : function(){
            var self = this;
            self.element.find(".modelAppSpacePorletsImageUploadedBtn").mouseenter(function(){
                if($(this).find(".modelAppSpacePorletsImageUploadMask").is(':hidden')){
                    $(this).find(".modelAppSpacePorletsImageUploadMask").show();
                }
            }).mouseleave(function(){
                $(this).find(".modelAppSpacePorletsImageUploadMask").hide();
            });
            self.element.find('.modelAppSpacePorletsImageUploadedDel').click(function(e){
                e?e.stopPropagation():event.cancelBubble = true;
                self.element.find(".modelAppSpacePorletsImageUploadBtnBox").show();
                self.element.find(".modelAppSpacePorletsImageUploadedBtnBox").hide();
                self.element.find(".modelAppSpacePorletsImageUploadedBtn img").attr("src","");
                self.element.find(".modelAppSpacePorletsImageUploadedBtn input").val("");
                self.validateElement(self.element.find(".modelAppSpacePorletsImageUploadedBtn input")[0]);
                self.setLeftShow();
            });
        },
        selectMaterial :function(){
            var self = this;
            dialog.build({
                config : {
                    width : 800,
                    height : 600,
                    title :  modelingLang["modelingAppSpace.chooseMaterial"],
                    content : {
                        type : "iframe",
                        url : "/sys/modeling/base/modelingMaterialMain.do?method=selectMaterial"
                    }
                },
                callback : function(value, dia) {
                    if(value==null){
                        return ;
                    }
                    var url = value.url;
                    url= Com_Parameter.ContextPath + url.substr(1);
                    var fdAttrId=value.fdAttrId;
                    if(url && fdAttrId){
                        self.element.find(".modelAppSpacePorletsImageUploadBtnBox").hide();
                        self.element.find(".modelAppSpacePorletsImageUploadedBtnBox").show();
                        self.element.find(".modelAppSpacePorletsImageUploadedBtn img").attr("src",url);
                        self.element.find(".modelAppSpacePorletsImageUploadedBtn input").val(fdAttrId);
                        self.element.find("[name='imgId']").closest(".modelAppSpaceImageBgOnPicture").find(".validation-container").remove();
                        self.validateElement(self.element.find(".modelAppSpacePorletsImageUploadedBtn input")[0]);
                        self.setLeftShow();
                    }
                }
            }).show();
        },
        validate:function($super) {
            var isPass = $super();

            return isPass;
        },

        getKeyData : function() {
            var keyData = {};
            keyData.title = {};
            keyData.title.value =  this.element.find("[name='fdTitle']").val();
            keyData.title.isHide =  this.element.find("[name='fdTitleIsHide']").is(":checked") ? "1" : "0";
            keyData.fdImageUrlId= this.element.find(".modelAppSpacePorletsImageUploadedBtn").find("input").val();
            keyData.fdFillType =  this.element.find("[name='fdFillType_"+this.randomName+"']:checked").val();
            keyData.fdFillColor =  this.element.find(".text_fill_color_div").find("input[type='hidden']").val();
            return keyData;
        },
        initByStoreData : function(storeData) {
            if (JSON.stringify(storeData) == "{}") {
                return;
            }
            this.element.find("[name='fdTitle']").val(storeData.title.value);
            if(storeData.title.isHide === "1"){
                this.element.find("[name='fdTitleIsHide']").attr("checked","checked");
            }else{
                this.element.find("[name='fdTitleIsHide']").removeAttr("checked");
            }
            var url =Com_Parameter.ContextPath + "sys/attachment/sys_att_main/sysAttMain.do?method=download&open=1&fdId="+storeData.fdImageUrlId;
            this.element.find(".modelAppSpacePorletsImageUploadBtnBox").hide();
            this.element.find(".modelAppSpacePorletsImageUploadedBtnBox").show();
            this.element.find(".modelAppSpacePorletsImageUploadedBtn img").attr("src",url);
            this.element.find(".modelAppSpacePorletsImageUploadedBtn input").val(storeData.fdImageUrlId);
            this.element.find("[name='imgId']").closest(".modelAppSpaceImageBgOnPicture").find(".validation-container").remove();

            this.element.find("[name='fdFillType_"+this.randomName+"']").each(function() {
                if($(this).val() == storeData.fdFillType){
                    $(this).prop("checked",true);
                }else{
                    $(this).removeAttr("checked");
                }
            })
            window.SpectrumColorPicker.setColor("textFillColorSelectValue"+this.randomName,storeData.fdFillColor);
            this.setLeftShow();
        },
        //实现左右互联
        setLeftShow : function() {
            var $currentPreviewWgt = this.parent.element.find("[data-id='"+this.randomName+"']");
            var data = this.getKeyData();
            var $title = $currentPreviewWgt.find(".modelAppSpaceWidgetDemoTypeTitle");
            if(data.title.isHide === "1"){
                $title.hide();
            }else{
                $title.text(data.title.value);
                $title.show();
            }
            var $image = $currentPreviewWgt.find(".modelAppSpaceWidgetDemoTypeImg");
            var fdUrlId=data.fdImageUrlId;
            var url =Com_Parameter.ContextPath + "sys/attachment/sys_att_main/sysAttMain.do?method=download&open=1&fdId="+fdUrlId;
            var fdFillStyle=data.fdFillType;
            var fillStyle= "contain";
            if(fdFillStyle){
                switch (fdFillStyle) {
                    case "0":
                        fillStyle = "contain";
                        break;
                    case "1":
                        fillStyle = "cover";
                        break;
                    case "2":
                        fillStyle = "100% 100%";
                        break;
                    default:
                        break;
                }
            }
            if(fdUrlId){
                var backgroundValue="url('"+url+"') no-repeat center"
                $image.css("background",backgroundValue);
                $image.css("background-size",fillStyle);
            }else{
                $image.css("background","");
            }
            $image.css("background-color",data.fdFillColor)
        },
        destroy : function($super) {
            this.removeValidateElements(this.element.find(".modelAppSpacePorletsImageUploadedBtn").find("input")[0],"required");
            $super();
        }
    });
    module.exports = ImagePortlet;
});