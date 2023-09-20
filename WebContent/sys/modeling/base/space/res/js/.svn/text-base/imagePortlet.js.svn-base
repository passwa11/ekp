define(function(require, exports, module) {
    var $ = require('lui/jquery'),
        base = require('lui/base'),
        source = require('lui/data/source'),
    render = require('lui/view/render');
    var topic = require("lui/topic");
    var dialog = require("lui/dialog");
    var modelingLang = require("lang!sys-modeling-base");

    var ImagePortlet = base.DataView.extend({
        initProps: function ($super, cfg) {
            $super(cfg);
            this.config = cfg;
            this.container = cfg.container;
            this.picturePortlet = cfg.parent.picturePortlet;
        },
        startup : function($super, cfg) {
            this.setRender(new render.Template({
                src : "/sys/modeling/base/space/imageViewRender.html#",
                parent : this
            }));
            this.render.startup();
            $super(cfg);
        },
        doRender: function($super, cfg){
            $super(cfg);
            this.element.appendTo(this.container).addClass("panel-tab-main-view");
            //画颜色选择器
            this.drawImageColorSelect();
            //事件
            this.addEvent();
            //初始化
            if(this.picturePortlet){
                this.initByStoreData(this.picturePortlet);
            }
        },
        //获取多语言资源
        getModelingLang :function (){
            return modelingLang;
        },
        draw : function($super, cfg){
            this.render.get({});
        },
        drawImageColorSelect : function(){
            var self = this;
            $(".image_color_div").attr("data-color-mark-id","imageFillColorSelectValue");
            if (window.SpectrumColorPicker) {
                window.SpectrumColorPicker.init("imageFillColorSelectValue");
                window.SpectrumColorPicker.setColor("imageFillColorSelectValue","#FFFFFF");
            }
            //更新预览
           /* topic.subscribe("modeling.SpectrumColorPicker.change",function(self){
                self.setLeftShow();
            });

            topic.subscribe("modeling.SpectrumColorPicker.init",function(self){
                self.setLeftShow();
            });*/
        },
        addEvent : function(){
            var self = this;
            //图片下拉列表
            this.selectDropdown();

            //选择图片
            var $selectImg=$(".modelAppSpacePorletsImageUploadBtn");
            var $checkedImg=$(".modelAppSpacePorletsImageUploadedBtn");
            $selectImg.off("click").on("click",function(){
                self.selectMaterial();
            });
            $checkedImg.off("click").on("click",function(){
                self.selectMaterial();
            });
            // 控制上传图片,和删除
            this.imageUploadMask();
            // 切换部件
            this.changeProtlet();
            //开启链接
            this.openLinkEvent();
        },
        openLinkEvent : function(){
            var self = this;
            this.element.find("input[type='checkbox'][name='enableLink']").off("click").on("click",function(){
                var isOpen = $(this).is(':checked');
                self.linkDisable(isOpen);
            });
            this.element.find("[name='fdLink']").on("blur",function(){
                self.linkCheckEvent(this);
            });
            this.element.find("[name='fdDisplayHeight']").on('input propertychange', function(){
                self.setLeftShow();
            });
        },
        linkDisable : function(isOpen){
            var self = this;
            if(!isOpen){
                //链接输入框置灰
                self.element.find("[name='fdLink']").attr("disabled",true);
                self.element.find("[name='fdLink']").addClass("no-input-link");
                self.element.find(".linkContent .validation-container").remove();
            }else{
                self.element.find("[name='fdLink']").attr("disabled",false);
                self.element.find("[name='fdLink']").removeClass("no-input-link");
                self.element.find(".linkContent .validation-container").remove();
            }
        },
        linkCheckEvent : function(e){
            $(e).closest("div").find(".validation-container").remove();
            if(!this.element.find("input[type='checkbox'][name='enableLink']").is(':checked') || !$(e).val()){
                return true;
            }
            var pattern = /^\/[a-zA-Z0-9\w-./?%&=]+/;
            var isPass = pattern.test($(e).val());
            if(!isPass || $(e).val().length >= 1000 ){
                $(e).closest("div").append($("<div class='validation-container'><div class=\"validation-advice\" _reminder=\"true\">" +
                    "<table class=\"validation-table\"><tbody><tr><td>" +
                    "<div class=\"lui_icon_s lui_icon_s_icon_validator\"></div></td>" +
                    "<td class=\"validation-advice-msg\"><span class=\"validation-advice-title\">"+modelingLang['modelingAppViewtab.linkAddress']+" </span>" +
                    modelingLang['view.enter.valid.url']+
                    "</td></tr></tbody></table></div></div>"));
                return false;
            }
            return true;
        },
        imageUploadMask : function(){
            var self = this;
            $(".modelAppSpacePorletsImageUploadedBtn").mouseenter(function(){
                if($(this).find(".modelAppSpacePorletsImageUploadMask").is(':hidden')){
                    $(this).find(".modelAppSpacePorletsImageUploadMask").show();
                }
            }).mouseleave(function(){
                $(this).find(".modelAppSpacePorletsImageUploadMask").hide();
            });
            $('.modelAppSpacePorletsImageUploadedDel').click(function(e){
                e?e.stopPropagation():event.cancelBubble = true;
                $(".modelAppSpacePorletsImageUploadBtnBox").show();
                $(".modelAppSpacePorletsImageUploadedBtnBox").hide();
                $(".modelAppSpacePorletsImageUploadedBtn img").attr("src","");
                $(".modelAppSpacePorletsImageUploadedBtn input").val("");
                self.setLeftShow();
            });
        },
        changeProtlet : function(){
            var self = this;
            $(".modelAppSpaceIcon.modelAppSpacePorletsTitleIcon").off("click").on("click", function(e){
                $(".modelAppSpaceTempTextOptionList").remove();
                if($(this).parent().find(".changePorlet").find(".modelAppSpaceTempTextOptionList").length==0){
                    $(this).parent().find(".changePorlet").append('<ul class="modelAppSpaceTempTextOptionList"><li data-change-select="0">'+modelingLang["modelingAppSpace.styleTextPart"]+'</li><li data-change-select="2">'+modelingLang["modelingAppSpace.styleDataComponent"]+'</li></ul>');
                    //选择部件点击事件
                    $(".modelAppSpaceTempTextOptionList li").click(function(){
                        var fdType = $(this).attr("data-change-select");
                        var part = {};
                        LUI("incStyleGenerator").changePortletRight(fdType,part);
                    });
                }
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
                        $(".modelAppSpacePorletsImageUploadBtnBox").hide();
                        $(".modelAppSpacePorletsImageUploadedBtnBox").show();
                        $(".modelAppSpacePorletsImageUploadedBtn img").attr("src",url);
                        $(".modelAppSpacePorletsImageUploadedBtn input").val(fdAttrId);
                        $("[name='imgId']").closest(".modelAppSpaceImageBgOnPicture").find(".validation-container").remove();
                        self.setLeftShow();
                    }
                }
            }).show();
        },
        selectDropdown : function(){
            var self = this;
            // 下拉列表
            $('.muiPerformanceDropdownBoxImg').click(function(e){
                e?e.stopPropagation():event.cancelBubble = true;
                if($(this).find('.muiPerformanceDropdownBoxListImg').css('display') == 'none'&&$(this).hasClass('active')==false){
                    $('.muiPerformanceDropdownBoxImg').removeClass('active');
                    $(this).addClass('active');
                    $('.muiPerformanceDropdownBoxListImg').hide();
                    $(this).find('.muiPerformanceDropdownBoxListImg').show();
                }else{
                    $(this).removeClass('active');
                    $(this).find('.muiPerformanceDropdownBoxListImg').hide();
                }
            });
            $('.muiPerformanceDropdownBoxListImg>ul>li').click(function(e){
                e?e.stopPropagation():event.cancelBubble = true;
                $(this).parents('.muiPerformanceDropdownBoxImg').children('span').text($(this).text());
                $(this).parents('.muiPerformanceDropdownBoxImg').children('input').val($(this).text());
                $(this).parents('.muiPerformanceDropdownBoxImg').find('.muiPerformanceDropdownBoxListImg').hide();
                $(this).parents('.muiPerformanceDropdownBoxImg').removeClass('active');
            });
            // 下拉列表点击外部或者按下ESC后列表隐藏
            $(document).click(function(){
                $('.muiPerformanceDropdownBoxListImg').hide();
                $(this).find('.watermark_configuration_list_pop').hide();
                $('.muiPerformanceDropdownBoxImg').removeClass('active');
                $(".watermark_configuration_alpha_bar,.watermark_configuration_scale_box,.watermark_configuration_alpha_bar").addClass("hide");
            }).keyup(function(e){
                var key =  e.which || e.keyCode;;
                if(key == 27){
                    $('.muiPerformanceDropdownBoxListImg').hide();
                    $(this).find('.watermark_configuration_list_pop').hide();
                    $('.muiPerformanceDropdownBoxImg').removeClass('active');
                }
            });
            //选择图片填充方式
            $('.muiPerformanceDropdownBoxImg  ul>li').click(function(e){
                var filltyle = $(this).attr("data-image-select");
                $(this).parents('.muiPerformanceDropdownBoxImg').children('input').val(filltyle);
                self.setLeftShow();
            });
        },
        submitChecked : function(){
            var self = this;
            var isPass = true;
            var linkUrl = this.linkCheckEvent("[name='fdLink']");
            if(linkUrl == false){
                isPass = false;
            }
            var imgId = this.element.find("[name='imgId']").val();
             $("[name='imgId']").closest(".modelAppSpaceImageBgOnPicture").find(".validation-container").remove();
            if(!imgId){
                isPass = false;
                $("[name='imgId']").closest(".modelAppSpaceImageBgOnPicture").append($("<div class='validation-container'><div class=\"validation-advice\" _reminder=\"true\">" +
                    "<table class=\"validation-table\"><tbody><tr><td>" +
                    "<div class=\"lui_icon_s lui_icon_s_icon_validator\"></div></td>" +
                    "<td class=\"validation-advice-msg\"><span class=\"validation-advice-title\">"+modelingLang['modelingAppSpace.pictureContent']+" </span>" +
                    modelingLang['modelingAppSpace.pictureNotNull']+
                    "</td></tr></tbody></table></div></div>"));
            }
            return isPass;
        },
        getKeyData : function() {
            var keyData = {};
            keyData.fdFillColor =  this.element.find(".image_color_div").find("input[type='hidden']").val();
            keyData.fdImgContent = {};
            keyData.fdImgContent.fdUrlId= this.element.find(".modelAppSpacePorletsImageUploadedBtn").find("input").val();
            keyData.fdImgContent.fdFillStyle = $(".muiPerformanceDropdownBoxImg").children('input').val();
            keyData.isOpenLink = this.element.find("input[type='checkbox'][name='enableLink']").is(':checked');
            keyData.fdLinkUrl = this.element.find("[name='fdLink']").val();
            keyData.fdDisplayHeight = this.element.find("[name='fdDisplayHeight']").val() + "px";
            keyData.fdOverstep = this.element.find("input[type='checkbox'][name='fdOverstep']").is(':checked');
            return keyData;
        },
        initByStoreData : function(storeData) {
            if (JSON.stringify(storeData) === "{}") {
                return;
            }
            window.SpectrumColorPicker.setColor("imageFillColorSelectValue",storeData.fdFillColor);
            var fdImage=storeData.fdImgContent;
            if(fdImage.fdUrlId){
                var url =Com_Parameter.ContextPath + "sys/attachment/sys_att_main/sysAttMain.do?method=download&open=1&fdId="+fdImage.fdUrlId;
                $(".modelAppSpacePorletsImageUploadBtnBox").hide();
                $(".modelAppSpacePorletsImageUploadedBtnBox").show();
                $(".modelAppSpacePorletsImageUploadedBtn img").attr("src",url);
                $(".modelAppSpacePorletsImageUploadedBtn input").val(fdImage.fdUrlId);
            }else{
                $(".modelAppSpacePorletsImageUploadBtnBox").show();
                $(".modelAppSpacePorletsImageUploadedBtnBox").hide();
            }
            //图片填充形式
            var fdFillStyle=fdImage.fdFillStyle;
            var selectText=this.element.find("[data-image-select='"+fdFillStyle+"']").text();
            this.element.find('.muiPerformanceDropdownBoxImg').children('span').text(selectText);
            this.element.find('.muiPerformanceDropdownBoxImg').children('input').val(fdFillStyle);

            if(storeData.isOpenLink){
                this.element.find("[name='enableLink']").prop("checked",true);
            }else{
                this.element.find("[name='enableLink']").prop("checked",false);
            }
            this.linkDisable(storeData.isOpenLink);
            this.element.find("[name='fdLink']").val(storeData.fdLinkUrl);
            this.element.find("[name='fdDisplayHeight']").val(storeData.fdDisplayHeight.split("px")[0]);
            if(storeData.fdOverstep){
                this.element.find("[name='fdOverstep']").prop("checked",true);
            }else{
                this.element.find("[name='fdOverstep']").prop("checked",false);
            }
        },
        //实现左右互联
        setLeftShow : function() {
            LUI("incStyleGenerator").toSetImageStyle(this.getKeyData());
        }
    });
    exports.ImagePortlet = ImagePortlet;
});