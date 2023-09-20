define(function(require, exports, module) {
    var $ = require('lui/jquery'),
        base = require('lui/base'),
        render = require('lui/view/render');
    var topic = require("lui/topic");
    var dialog = require("lui/dialog");
    var modelingLang = require("lang!sys-modeling-base");
    var basePortlet = require("sys/modeling/base/mobile/design/custom/basePortlet");
    var TextPortlet = basePortlet.BasePortlet.extend({
        initProps: function ($super, cfg) {
            $super(cfg);
            this.config = cfg;
            this.parent=cfg.parent;
            this.data = cfg.data || {};
        },
        startup : function($super, cfg) {
            this.setRender(new render.Template({
                src : "/sys/modeling/base/mobile/design/custom/portlets/textViewRender.html#",
                parent : this
            }));
            this.render.startup();
            $super(cfg);
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
            this.element.find(".text_color_div").attr("data-color-mark-id","textColorSelectValue"+this.randomName);
            this.element.find(".text_fill_color_div").attr("data-color-mark-id","textFillColorSelectValue"+this.randomName);
            if (window.SpectrumColorPicker) {
                window.SpectrumColorPicker.init("textColorSelectValue"+this.randomName);
                window.SpectrumColorPicker.init("textFillColorSelectValue"+this.randomName);

                window.SpectrumColorPicker.setColor("textColorSelectValue"+this.randomName,"#000000");
                window.SpectrumColorPicker.setColor("textFillColorSelectValue"+this.randomName,"#FFFFFF");
            }
        },
        addEvent : function($super){
            var self = this;
            //标题
            // this.textTitleEvent();
            //字体型号
            this.textStyleEvent();
            //字体格式
            this.textSettingEvent();
            //文本内容
            this.textContentEvent();
            //背景填充方式
            this.backgroundEvent();
            //更新预览
            topic.subscribe("modeling.SpectrumColorPicker.change",function(obj){
                if(obj.valueName && obj.valueName.indexOf(self.randomName)>-1){
                    self.setLeftShow();
                }
            });

            $super();
        },
        //获取多语言资源
        getModelingLang :function (){
            return modelingLang;
        },
        textContentEvent : function(){
            var self = this;
            this.element.find(".textContent>textarea").on('input propertychange blur', function(){
                // 中文字符长度为3
                var newvalue = $(this).val().replace(/[^\x00-\xff]/g, "***");
                if(newvalue>500){
                    self.validateElement(this);
                }
                self.element.find(".modelAppSpacePorletsTextContentLength").text(newvalue.length);
                self.setLeftShow();
            });
            this.element.find("[name='textContentType_"+self.randomName+"']").off("click").on("click",function() {
                if(this.value == 0){
                    self.element.find(".system-content").show();
                    self.element.find(".textarea-div").hide();
                    self.element.find(".textarea-div .textContent>textarea").attr("data-type","hidden");
                    self.removeValidateElements(self.element.find(".textarea-div .textContent>textarea")[0]);
                    self.validateElement(self.element.find(".textContent>textarea")[0]);
                    self.setLeftShow();
                }else{
                    self.element.find(".system-content").hide();
                    self.element.find(".textarea-div").show();
                    self.element.find(".textarea-div .textContent>textarea").attr("data-type","validate");
                    self.addValidateElements(self.element.find(".textarea-div .textContent>textarea")[0],"required");
                    self.element.find(".textContent>textarea").trigger($.Event("blur"));
                }
            })
            self.element.find('.textContentDiv .muiPerformanceDropdownBoxList>ul>li').off("click").on("click",function(e){
                e?e.stopPropagation():event.cancelBubble = true;
                self.selectShowText(this);
                var value = $(this).find("[type='hidden']").val();
                $(this).parents('.muiPerformanceDropdownBox').find("[name='systemContent']").val(value);
                self.setLeftShow();
            });
            this.addValidateElements(this.element.find(".textContent>textarea")[0],"required");
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
        backgroundEvent : function(){
            var self = this;
            //下拉框列表点击事件
            this.element.find('.modelAppSpacePorletsInlineRow .muiPerformanceDropdownBoxList>ul>li').off("click").on("click",function(e){
                e?e.stopPropagation():event.cancelBubble = true;
                self.selectShowText(this);
            });
        },
        selectShowText : function(li){
            $(li).parents('.muiPerformanceDropdownBox').children('span').text($(li).text().trim());
            $(li).parents('.muiPerformanceDropdownBox').children("input[type='text']").val($(li).text().trim());
            $(li).parents('.muiPerformanceDropdownBox').find('.muiPerformanceDropdownBoxList').hide();
            $(li).parents('.muiPerformanceDropdownBox').removeClass('active');
        },
        selectDropdown : function(){
            var self = this;
            // 下拉列表
            this.element.find('.muiPerformanceDropdownBoxText').click(function(e){
                e?e.stopPropagation():event.cancelBubble = true;
                if($(this).find('.muiPerformanceDropdownBoxListText').css('display') == 'none'&&$(this).hasClass('active')==false){
                    self.element.find('.muiPerformanceDropdownBoxText').removeClass('active');
                    $(this).addClass('active');
                    self.element.find('.muiPerformanceDropdownBoxListText').hide();
                    $(this).find('.muiPerformanceDropdownBoxListText').show();
                }else{
                    $(this).removeClass('active');
                    $(this).find('.muiPerformanceDropdownBoxListText').hide();
                }
            });
            self.element.find('.muiPerformanceDropdownBoxListText>ul>li').click(function(e){
                e?e.stopPropagation():event.cancelBubble = true;
                $(this).parents('.muiPerformanceDropdownBoxText').children('span').text($(this).text());
                $(this).parents('.muiPerformanceDropdownBoxText').children('input').val($(this).text());
                $(this).parents('.muiPerformanceDropdownBoxText').find('.muiPerformanceDropdownBoxListText').hide();
                $(this).parents('.muiPerformanceDropdownBoxText').removeClass('active');
            });
            // 下拉列表点击外部或者按下ESC后列表隐藏
            $(document).click(function(){
                self.element.find('.muiPerformanceDropdownBoxListText').hide();
                $(this).find('.watermark_configuration_list_pop').hide();
                self.element.find('.muiPerformanceDropdownBoxText').removeClass('active');
                self.element.find(".watermark_configuration_alpha_bar,.watermark_configuration_scale_box,.watermark_configuration_alpha_bar").addClass("hide");
            }).keyup(function(e){
                var key =  e.which || e.keyCode;;
                if(key == 27){
                    self.element.find('.muiPerformanceDropdownBoxListText').hide();
                    $(this).find('.watermark_configuration_list_pop').hide();
                    self.element.find('.muiPerformanceDropdownBoxText').removeClass('active');
                }
            });
            //选择填充方式
            self.element.find('.muiPerformanceDropdownBoxText  ul>li').click(function(e){
                var filltyle = $(this).attr("data-text-select");
                $(this).parents('.muiPerformanceDropdownBoxText').children('input').val(filltyle);
                self.setLeftShow();
            });
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
            keyData.content={};
            keyData.content.type = this.element.find("[name='textContentType_"+this.randomName+"']:checked").val();
            if (keyData.content.type == "0") {
                keyData.content.text = this.element.find("[name=systemContentPlaceHolder]").val();
                keyData.content.value = this.element.find("[name=systemContent]").val();
            }else{
                keyData.content.text = this.element.find(".textContent textarea").val();
            }
            keyData.fdFillColor =  this.element.find(".text_fill_color_div").find("input[type='hidden']").val();
            keyData.fdTextSetting = {};
            keyData.fdTextSetting.textFace = this.element.find("[name='textStyle']").val() || "0";
            keyData.fdTextSetting.textFaceName = this.element.find(".textStyleDiv input[type='text']").val();
            keyData.fdTextSetting.textSizeName = this.element.find(".textSizeDiv input[type='text']").val();
            keyData.fdTextSetting.textSize = this.element.find("[name='textSize']").val() || "14";
            keyData.fdTextSetting.textSize +="px";
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
            if(storeData.content.type == "0"){
                this.element.find("[name=systemContent]").val(storeData.content.value);
                this.element.find("[name='textContentType_"+this.randomName+"'][value='0']").prop("checked",true);
                this.element.find("[name='textContentType_"+this.randomName+"'][value='0']").trigger($.Event("click"));
            }else{
                this.element.find(".textContent textarea").val(storeData.content.text);
                this.element.find("[name='textContentType_"+this.randomName+"'][value='1']").prop("checked",true);
                this.element.find("[name='textContentType_"+this.randomName+"'][value='1']").trigger($.Event("click"));
            }
            var fdTextSetting = storeData.fdTextSetting;
            this.element.find("[name='textStyle']").val(fdTextSetting.textFace);
            this.element.find(".textStyleDiv input[type='text']").val(fdTextSetting.textFaceName);
            this.element.find("[name='textSize']").val(fdTextSetting.textSize.replace("px",""));
            this.element.find(".textSizeDiv input[type='text']").val(fdTextSetting.textSizeName);
            window.SpectrumColorPicker.setColor("textColorSelectValue"+this.randomName,fdTextSetting.textColor);
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
            var $text = $currentPreviewWgt.find(".modelAppSpaceWidgetDemoTypeText");
            $text.empty();

            $text.text(data.content.text);
            $text.css("color",data.fdTextSetting.textColor)
                .css("font-style",data.fdTextSetting.textStyle)
                .css("font-weight",data.fdTextSetting.textBold)
                .css("text-align",data.fdTextSetting.textAlign)
                .css("text-decoration",data.fdTextSetting.textDecoration)
                .css("font-family",data.fdTextSetting.textFaceName)
                .css("font-size",data.fdTextSetting.textSize)
                .css("background",data.fdFillColor)
        },
        destroy : function($super) {
            this.removeValidateElements(this.element.find(".textContent>textarea")[0],"required");
            $super();
        }
    });
    module.exports = TextPortlet;
});