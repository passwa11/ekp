define(function(require, exports, module) {
    var $ = require('lui/jquery'),
        base = require('lui/base'),
        source = require('lui/data/source'),
        render = require('lui/view/render');
    var topic = require("lui/topic");
    var dialog = require("lui/dialog");
    var modelingLang = require("lang!sys-modeling-base");

    var $KMSSValidation = $GetKMSSDefaultValidation();

    var TextPortlet = base.DataView.extend({
        initProps: function ($super, cfg) {
            $super(cfg);
            this.config = cfg;
            this.container = cfg.container;
            this.textPortlet = cfg.parent.textPortlet;
            this.incStyle=cfg.parent;
        },
        startup : function($super, cfg) {
            this.setRender(new render.Template({
                src : "/sys/modeling/base/space/textViewRender.html#",
                parent : this
            }));
            this.render.startup();
            $super(cfg);
        },
        doRender: function($super, cfg){
            var self = this;
            $super(cfg);
            this.element.appendTo(this.container).addClass("panel-tab-main-view");
            //画颜色选择器
            this.drawColorSelect();
            //事件
            this.addEvent();
            //初始化
            if(this.textPortlet){
                this.initByStoreData(this.textPortlet);
            }
        },
        draw : function($super, cfg){
            this.render.get({});
        },
        drawColorSelect : function(){
            var self = this;
            $(".text_color_div").attr("data-color-mark-id","textColorSelectValue");
            $(".text_fill_color_div").attr("data-color-mark-id","textFillColorSelectValue");
            if (window.SpectrumColorPicker) {
                window.SpectrumColorPicker.init("textColorSelectValue");
                window.SpectrumColorPicker.init("textFillColorSelectValue");

                window.SpectrumColorPicker.setColor("textColorSelectValue","#000000");
                window.SpectrumColorPicker.setColor("textFillColorSelectValue","#FFFFFF");
                /* //更新预览
                 topic.subscribe("modeling.SpectrumColorPicker.change",function(obj){
                     self.setLeftShow();
                 });

                 topic.subscribe("modeling.SpectrumColorPicker.init",function(){
                     self.setLeftShow();
                 });*/
            }
        },
        addEvent : function(){
            var self = this;
            //背景
            this.changeBackgroundEvent();
            //图片下拉列表
            this.selectDropdown();
            //选择图片
            var $selectImg=$(".modelAppSpaceTextUploadBtn");
            var $checkedImg=$(".modelAppSpacePorletsTextUploadedBtn");
            $selectImg.off("click").on("click",function(){
                self.selectMaterial();
            });
            $checkedImg.off("click").on("click",function(){
                self.selectMaterial();
            });
            // 控制上传图片,和删除
            this.textUploadMask();
            //字体型号
            this.textStyleEvent();
            //字体格式
            this.textSettingEvent();
            //文本内容
            this.textContentEvent();
            //开启链接
            this.openLinkEvent();
            //背景图片填充方式
            this.backgroundEvent();
            /* //点击调色板事件
             this.clickColorSelete();*/
            // 切换部件
            this.changeProtlet();
        },
        //获取多语言资源
        getModelingLang :function (){
            return modelingLang;
        },
        openLinkEvent : function(){
            var self = this;
            this.element.find("input[type='checkbox'][name='enableLink']").off("click").on("click",function(){
                var isOpen = $(this).is(':checked');
                self.linkDisable(isOpen);
            });
            this.element.find("[name='fdLink']").on("blur",function(){
                self.linkCheckEvent(this);
            })
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
        backgroundEvent : function(){
            var self = this;
            //图片居中填满等下拉框列表点击事件
            this.element.find('.modelAppSpacePorletsInlineRow .muiPerformanceDropdownBoxList>ul>li').off("click").on("click",function(e){
                e?e.stopPropagation():event.cancelBubble = true;
                self.selectShowText(this);
            });
        },
        changeProtlet : function(){
            var self = this;
            $(".modelAppSpaceIcon.modelAppSpacePorletsTitleIcon").off("click").on("click", function(e){
                $(".modelAppSpaceTempTextOptionList").remove();
                if($(this).parent().find(".changePorlet").find(".modelAppSpaceTempTextOptionList").length==0){
                    $(this).parent().find(".changePorlet").append('<ul class="modelAppSpaceTempTextOptionList"><li data-change-select="1">'+modelingLang["modelingAppSpace.stylePictureParts"]+'</li><li data-change-select="2">'+modelingLang["modelingAppSpace.styleDataComponent"]+'</li></ul>');
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
                        $(".modelAppSpaceTextUploadBtn").hide();
                        $(".modelAppSpaceTextCheckedImginfo").show();
                        $(".modelAppSpaceTextCheckedImginfo img").attr("src",url);
                        $(".modelAppSpaceTextUploadBtnBox input").val(fdAttrId);
                        self.setLeftShow();
                    }
                }
            }).show();
        },
        textUploadMask : function(){
            var self = this;
            $(".modelAppSpacePorletsTextUploadedBtn").mouseenter(function(){
                if($(this).find(".modelAppSpacePorletsTextUploadMask").is(':hidden')){
                    $(this).find(".modelAppSpacePorletsTextUploadMask").show();
                }
            }).mouseleave(function(){
                $(this).find(".modelAppSpacePorletsTextUploadMask").hide();
            });
            $('.modelAppSpacePorletsTextUploadedDel').click(function(e){
                e?e.stopPropagation():event.cancelBubble = true;
                $(".modelAppSpaceTextUploadBtn").show();
                $(".modelAppSpaceTextCheckedImginfo").hide();
                $(".modelAppSpaceTextCheckedImginfo img").attr("src","");
                $(".modelAppSpaceTextUploadBtnBox input").val("");
                self.setLeftShow();
            });
        },
        textContentEvent : function(){
            var self = this;
            this.element.find(".textContent>textarea").on('input propertychange', function(){
                $(this).closest(".textarea-div").find(".validation-container").remove();
                if($(this).val().length >= 500) {
                    $(this).val($(this).val().substring(0, 500));
                }
                self.element.find(".modelAppSpacePorletsTextContentLength").text($(this).val().length);
                self.setLeftShow();
            });
            this.element.find("[name='fdDisplayHeight']").on('input propertychange', function(){
                self.setLeftShow();
            });
            $("#setOverflow").on('click',function () {
                self.setLeftShow();
            })
        },
        textContentChecked: function(e,required,str1,str2){
            var self = this;
            $(e).closest(".textarea-div").find(".validation-container").remove();
            if($(e).val().length > 500) {
                $(e).closest(".textarea-div").append($("<div class='validation-container'><div class=\"validation-advice\" _reminder=\"true\">" +
                    "<table class=\"validation-table\"><tbody><tr><td>" +
                    "<div class=\"lui_icon_s lui_icon_s_icon_validator\"></div></td>" +
                    "<td class=\"validation-advice-msg\"><span class=\"validation-advice-title\">" + str1 + "</span>" +
                    " " + str2+ "</td></tr></tbody></table></div></div>"));
                return false;
            }
            return true;
        },
        textSettingEvent : function(){
            var self = this;
            this.element.find(".modelAppSpacePorletsTextOption").click(function(){
                if(!$(this).hasClass("active")){
                    $(this).addClass("active").siblings().removeClass("active")
                }else{
                    $(this).removeClass("active");
                }
                self.setLeftShow();
            })
        },
        textStyleEvent : function(){
            var self = this;
            //下拉框文本点击事件
            self.element.find('.muiPerformanceDropdownBox').click(function(e){
                e?e.stopPropagation():event.cancelBubble = true;
                if($(this).find('.muiPerformanceDropdownBoxList').css('display') == 'none'&&$(this).hasClass('active')==false){
                    $('.muiPerformanceDropdownBox').removeClass('active');
                    $(this).addClass('active');
                    $('.muiPerformanceDropdownBoxList').hide();
                    $(this).find('.muiPerformanceDropdownBoxList').show();
                }else{
                    $(this).removeClass('active');
                    $(this).find('.muiPerformanceDropdownBoxList').hide();
                }
            })
            //下拉框列表点击事件
            self.element.find('.textStyleDiv .muiPerformanceDropdownBoxList>ul>li').off("click").on("click",function(e){
                e?e.stopPropagation():event.cancelBubble = true;
                self.selectShowText(this);
                var value = $(this).find("[type='hidden']").val();
                $(this).parents('.muiPerformanceDropdownBox').find("[name='textStyle']").val(value);
                self.setLeftShow();
            });
            self.element.find('.textSizeDiv .muiPerformanceDropdownBoxList>ul>li').off("click").on("click",function(e){
                e?e.stopPropagation():event.cancelBubble = true;
                self.selectShowText(this);
                var value = $(this).find("[type='hidden']").val();
                $(this).parents('.muiPerformanceDropdownBox').find("[name='textSize']").val(value);
                self.setLeftShow();
            });
        },
        selectShowText : function(li){
            $(li).parents('.muiPerformanceDropdownBox').children('span').text($(li).text().trim());
            $(li).parents('.muiPerformanceDropdownBox').children("input[type='text']").val($(li).text().trim());
            $(li).parents('.muiPerformanceDropdownBox').find('.muiPerformanceDropdownBoxList').hide();
            $(li).parents('.muiPerformanceDropdownBox').removeClass('active');
        },
        changeBackgroundEvent : function(){
            var self = this;
            this.element.find(".modelAppSpacePorletsBgContent input").click(function(){
                if($(this).val() == "0"){
                    $(".modelAppSpacePorletsBgOnPicture").hide();
                    $(".modelAppSpacePorletsBgOnColor").show();
                }else{
                    $(".modelAppSpacePorletsBgOnPicture").show();
                    $(".modelAppSpacePorletsBgOnColor").hide();
                }
                self.setLeftShow();
            })
        },
        selectDropdown : function(){
            var self = this;
            // 下拉列表
            $('.muiPerformanceDropdownBoxText').click(function(e){
                e?e.stopPropagation():event.cancelBubble = true;
                if($(this).find('.muiPerformanceDropdownBoxListText').css('display') == 'none'&&$(this).hasClass('active')==false){
                    $('.muiPerformanceDropdownBoxText').removeClass('active');
                    $(this).addClass('active');
                    $('.muiPerformanceDropdownBoxListText').hide();
                    $(this).find('.muiPerformanceDropdownBoxListText').show();
                }else{
                    $(this).removeClass('active');
                    $(this).find('.muiPerformanceDropdownBoxListText').hide();
                }
            });
            $('.muiPerformanceDropdownBoxListText>ul>li').click(function(e){
                e?e.stopPropagation():event.cancelBubble = true;
                $(this).parents('.muiPerformanceDropdownBoxText').children('span').text($(this).text());
                $(this).parents('.muiPerformanceDropdownBoxText').children('input').val($(this).text());
                $(this).parents('.muiPerformanceDropdownBoxText').find('.muiPerformanceDropdownBoxListText').hide();
                $(this).parents('.muiPerformanceDropdownBoxText').removeClass('active');
            });
            // 下拉列表点击外部或者按下ESC后列表隐藏
            $(document).click(function(){
                $('.muiPerformanceDropdownBoxListText').hide();
                $(this).find('.watermark_configuration_list_pop').hide();
                $('.muiPerformanceDropdownBoxText').removeClass('active');
                $(".watermark_configuration_alpha_bar,.watermark_configuration_scale_box,.watermark_configuration_alpha_bar").addClass("hide");
            }).keyup(function(e){
                var key =  e.which || e.keyCode;;
                if(key == 27){
                    $('.muiPerformanceDropdownBoxListText').hide();
                    $(this).find('.watermark_configuration_list_pop').hide();
                    $('.muiPerformanceDropdownBoxText').removeClass('active');
                }
            });
            //选择图片填充方式
            $('.muiPerformanceDropdownBoxText  ul>li').click(function(e){
                var filltyle = $(this).attr("data-text-select");
                $(this).parents('.muiPerformanceDropdownBoxText').children('input').val(filltyle);
                self.setLeftShow();
            });
        },
        submitChecked : function(){
            var self = this;
            var isPass = true;
            var required = $KMSSValidation.getValidator("maxLength(500)");
            var contentLength = this.textContentChecked(".textContent>textarea",required,modelingLang["modelingAppSpace.textContent"],modelingLang["modelingAppSpace.textContentTips"]);
            var linkUrl = this.linkCheckEvent("[name='fdLink']");
            if(contentLength == false || linkUrl == false){
                isPass = false;
            }
            return isPass;
        },
        getKeyData : function() {
            var keyData = {};
            keyData.fdBackgroundType =  this.element.find("[name='PorletsBg']:checked").val();
            keyData.fdFillColor =  this.element.find(".text_fill_color_div").find("input[type='hidden']").val();
            keyData.fdImgBackground = {};
            keyData.fdImgBackground.fdUrlId=this.element.find(".modelAppSpaceTextUploadBtnBox").find("input").val();
            keyData.fdImgBackground.fdFillStyle=$(".muiPerformanceDropdownBoxText").children('input').val();
            keyData.fdTextContent = this.element.find(".textContent textarea").val();
            keyData.fdTextSetting = {};
            keyData.fdTextSetting.textFace = this.element.find("[name='textStyle']").val() || "0";
            keyData.fdTextSetting.textFaceName = this.element.find(".textStyleDiv input[type='text']").val();
            keyData.fdTextSetting.textSizeName = this.element.find(".textSizeDiv input[type='text']").val();
            keyData.fdTextSetting.textSize = this.element.find("[name='textSize']").val() || "14";
            keyData.fdTextSetting.textColor =  this.element.find(".text_color_div").find("input[type='hidden']").val();
            this.element.find(".modelAppSpacePorletsTextOptionList .active").each(function(){
                var value = $(this).find("input[type='hidden']").val();
                switch (value) {
                    case "bold":
                        keyData.fdTextSetting.textBold = value;
                        break;
                    case "italic":
                        keyData.fdTextSetting.textStyle = value;
                        break;
                    case "underline":
                        keyData.fdTextSetting.textDecoration = value;
                        break;
                    case "left":
                    case "center":
                    case "right":
                        keyData.fdTextSetting.textAlign = value;
                        break;
                    default:
                        break;
                }
            });
            keyData.isOpenLink = this.element.find("input[type='checkbox'][name='enableLink']").is(':checked');
            keyData.fdLinkUrl = this.element.find("[name='fdLink']").val();
            keyData.fdDisplayHeight = this.element.find("[name='fdDisplayHeight']").val() + "px";
            keyData.fdOverstep = this.element.find("input[type='checkbox'][name='fdOverstep']").is(':checked');
            return keyData;
        },
        initByStoreData : function(storeData) {
            if (JSON.stringify(storeData) == "{}") {
                return;
            }
            if(storeData.fdBackgroundType == "0"){
                //背景颜色
                $(".modelAppSpacePorletsBgOnColor").show();
                $(".modelAppSpacePorletsBgOnPicture").hide();
                this.element.find("[name='PorletsBg'][value='0']").attr("checked",true);
                window.SpectrumColorPicker.setColor("textFillColorSelectValue",storeData.fdFillColor);
            }else{
                $(".modelAppSpacePorletsBgOnColor").hide();
                $(".modelAppSpacePorletsBgOnPicture").show();
                this.element.find("[name='PorletsBg'][value='1']").attr("checked",true);
                var fdImgBackground=storeData.fdImgBackground;
                //背景图片
                if(fdImgBackground.fdUrlId){
                    var url =Com_Parameter.ContextPath + "sys/attachment/sys_att_main/sysAttMain.do?method=download&open=1&fdId="+fdImgBackground.fdUrlId;
                    $(".modelAppSpaceTextUploadBtn").hide();
                    $(".modelAppSpaceTextCheckedImginfo").show();
                    $(".modelAppSpaceTextCheckedImginfo img").attr("src",url);
                    $(".modelAppSpaceTextUploadBtnBox input").val(fdImgBackground.fdUrlId);
                }else{
                    $(".modelAppSpaceTextUploadBtn").show();
                    $(".modelAppSpaceTextCheckedImginfo").hide();
                }
                //图片填充形式
                var fdFillStyle=fdImgBackground.fdFillStyle;
                var selectText=this.element.find("[data-text-select='"+fdFillStyle+"']").text();
                this.element.find('.muiPerformanceDropdownBoxText').children('span').text(selectText);
                this.element.find('.muiPerformanceDropdownBoxText').children('input').val(fdFillStyle);
            }
            this.element.find(".textContent textarea").val(storeData.fdTextContent);
            this.element.find(".textContent>textarea").trigger('propertychange');
            var fdTextSetting = storeData.fdTextSetting;
            this.element.find("[name='textStyle']").val(fdTextSetting.textFace);
            this.element.find(".textStyleDiv input[type='text']").val(fdTextSetting.textFaceName);
            this.element.find("[name='textSize']").val(fdTextSetting.textSize);
            this.element.find(".textSizeDiv input[type='text']").val(fdTextSetting.textSizeName);
            window.SpectrumColorPicker.setColor("textColorSelectValue",fdTextSetting.textColor);
            if(fdTextSetting.textBold){
                this.element.find(".bold").addClass("active");
            }
            if(fdTextSetting.textDecoration){
                this.element.find(".underline").addClass("active");
            }
            if(fdTextSetting.textStyle){
                this.element.find(".ital").addClass("active");
            }
            if(fdTextSetting.textAlign){
                this.element.find(".textAlignUl").find("."+fdTextSetting.textAlign).addClass("active");
            }
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
            this.setLeftShow();
        },
        //实现左右互联
        setLeftShow : function() {
            LUI("incStyleGenerator").toSetTextStyle(this.getKeyData());
        }
    });
    exports.TextPortlet = TextPortlet;
});