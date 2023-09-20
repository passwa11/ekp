
/**
 * 业务空间-样式布局组件
 */
define(function(require, exports, module) {
    var $ = require('lui/jquery'),
        base = require('lui/base'),
        viewPortlet = require('sys/modeling/base/space/res/js/viewPortlet');
        source = require('lui/data/source');
    render = require('lui/view/render');
    var topic = require("lui/topic");
    var dialog = require("lui/dialog");
    var modelingLang = require("lang!sys-modeling-base");
    var IncStyleGenerator = base.Container.extend({
        initProps: function ($super, cfg) {
            $super(cfg);
            this.panelStyle = $("." + this.config.mainClass, this.element);
            this.storeData = this.config.storeData;
            this.viewContainer = $(".panel-portlet-main");
            this.wgtPortlet = "";
        },

        startup : function($super, cfg) {
            $super(cfg);
        },
        doRender : function($super, cfg) {
            $super(cfg);
        },
        draw : function($super, cfg){
            var self = this;
            //构建样式布局和左侧预览容器
            var rightStyleHtml= this.getRightStyleHtml();
            var leftHtml = this.getLeftHtml();
            $(".model-edit-left").append(this.getLeftHtml());
            this.panelStyle.append(rightStyleHtml);
            this.addEvent();
            if(this.storeData){
                this.initByStoreData(this.storeData);
            }
        },
        addEvent : function(){
            var self = this;
            //添加容器弹框事件
            this.addDropdownBox();

            // 切换背景类型
            this.changeStyleBg();

            //新建PC首页时，初始化选中模板1事件
            this.buildCheckTemp1();

            this.calcWidth();
            //选择模板点击事件
            this.changeTemplateTypeEvent();

            //选择图片填充方式
            $('.modelAppSpaceContainerfdFillStyle  ul>li').click(function(e){
                var filltyle = $(this).attr("data-filltyle-select");
                $(this).parents('.modelAppSpaceContainerfdFillStyle').children('input').val(filltyle);
            });

            // 增加容器按钮
            $(".modelAppSpaceTempPrevieRowsAddContainer").click(function(){
                $(".modelAppSpaceMask").show();
                $(".modelAppSpaceContainerSettingPop").show();
            });

            //容器取消按钮渲染并绑定事件
            var $close = $("<div class='modelAppSpaceContainerSettingPopBtnCancel'>"+modelingLang["modeling.Cancel"]+"</div>").appendTo($(".modelAppSpaceContainerSettingPopFooter"));
            $close.on("click",function(){
                self.closeModelAppSpacePop();
            });

            //容器添加按钮渲染并绑定事件
            var $add = $("<div class='modelAppSpaceContainerSettingPopBtnConfirm'>"+modelingLang["modeling.button.ok"]+"</div>").appendTo($(".modelAppSpaceContainerSettingPopFooter"));
            $add.on("click",function(){
                self.createAppSpaceTemp(null);
            });

            //弹出框左上角关闭
            var $cancel = $(".modelAppSpaceContainerSettingPopHeader i");
            $cancel.on("click",function(){
                self.closeModelAppSpacePop();
            });

            //背景颜色
            this.drawStyleColorSelect();

            //选择图片
            var $selectImg=$(".modelAppSpaceStyleUploadBtn");
            var $checkedImg=$(".modelAppSpaceStyleUploadedBtn");
            $selectImg.on("click",function(){
                self.selectMaterial();
            });
            $checkedImg.on("click",function(){
                self.selectMaterial();
            });
            // 控制上传图片,和删除
            this.styleUploadMask();
            //点击容器事件
            this.clickRowsItem();

            // 增加部件按钮
            this.addRowsItemBtn();
        },
        //添加容器弹框事件
        addDropdownBox : function(){
            // 添加容器弹框中列数下拉列表
            $('.muiPerformanceDropdownBox').click(function(e){
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
            $('.muiPerformanceDropdownBoxList>ul>li').click(function(e){
                e?e.stopPropagation():event.cancelBubble = true;
                $(this).parents('.muiPerformanceDropdownBox').children('span').text($(this).text());
                $(this).parents('.muiPerformanceDropdownBox').find('.muiPerformanceDropdownBoxList').hide();
                $(this).parents('.muiPerformanceDropdownBox').removeClass('active');
            });
            // 下拉列表点击外部或者按下ESC后列表隐藏
            $(document).click(function(){
                $('.muiPerformanceDropdownBoxList').hide();
                $(this).find('.watermark_configuration_list_pop').hide();
                $('.muiPerformanceDropdownBox').removeClass('active');
                $(".watermark_configuration_alpha_bar,.watermark_configuration_scale_box,.watermark_configuration_alpha_bar").addClass("hide");
            }).keyup(function(e){
                var key =  e.which || e.keyCode;;
                if(key == 27){
                    $('.muiPerformanceDropdownBoxList').hide();
                    $(this).find('.watermark_configuration_list_pop').hide();
                    $('.muiPerformanceDropdownBox').removeClass('active');
                }
            });
            $('.muiHrArchivesUploadAccessoryText>p').mouseenter(function(){
                $(this).parent('.muiHrArchivesUploadAccessoryText').append("<div class='muiHrArchivesUploadAccessoryHover'></div>")
                var txt=$(this).text();
                $(this).siblings('.muiHrArchivesUploadAccessoryHover').text(txt).show();
            }).mouseleave(function(){
                $(this).siblings('.muiHrArchivesUploadAccessoryHover').remove();
            });
            // 获取容器比例
            $(".modelAppSpaceTempPrevieRowsItem[data-flex]").each(function(){
                var flexVal=$(this).attr("data-flex");
                $(this).css("flex",flexVal)
            })
            // 设置容器列数
            $('.modelAppSpaceContainerSettingRows ul>li').click(function(e){
                var rowsVal = $(this).attr("data-rows");
                $(this).parents('.modelAppSpaceContainerSettingRows').children('span').attr("data-rows-value",rowsVal);
                $(".modelAppSpaceContainerSettingRatioContent>div").hide().slice(0,rowsVal).show();

            });
        },
        changeStyleBg : function(){
            $(".modelAppSpaceStyleBgContent input").click(function(){
                if($("#onColor").is(":checked")){
                    $(".modelAppSpaceStyleBgOnPicture").hide();
                    $(".modelAppSpaceStyleBgOnColor").show();
                }else{
                    $(".modelAppSpaceStyleBgOnPicture").show();
                    $(".modelAppSpaceStyleBgOnColor").hide();
                }
            })
        },
        buildCheckTemp1 : function(){
            if(JSON.stringify(this.storeData) == "{}"){
                $(document).ready(function(){
                    $(".modelAppSpaceStyleBgContent input")[0].click();
                    setTimeout(function(){
                        $('.modelAppSpaceTemplateListItem_01').click();
                        var fdTemplateType = $("input[name='fdTemplateType']").val();
                        $(".modelAppSpaceTempPreviewType[data-template="+fdTemplateType+"]").find(".modelAppSpaceTempPrevieRows:first").find(".modelAppSpaceTempPrevieRowsItem:first").addClass("active");
                    },100);
                })
            }
        },
        //点击容器事件
        clickRowsItem : function(){
            var self = this;
            var isPass = true;
            $('.modelAppSpaceTempPrevieRowsItem').click(function(e){
                $(".modelAppSpaceTempPrevieRowsItem").each(function(){
                    if($(this).hasClass("active")){
                        if(self.wgtPortlet &&  $(".panel-portlet-main").children().length>0 ){
                            isPass = self.wgtPortlet.submitChecked();
                            if(isPass == false){
                                dialog.confirm(modelingLang["modelingAppSpace.verification.fail"],function(value){
                                    return;
                                });
                                return;
                            }
                            $(this).find(".fdPartsConfig").val(JSON.stringify(self.wgtPortlet.getKeyData()));
                            $(".panel-portlet-main").empty();
                            $(".panel-portlet-header").show();
                        }
                        $(this).removeClass("active");
                        //当切换容器时，当前容器为数据部件且为门户类型时，未选中状态时占位图变换为灰色
                        self.switchContainerBackground( $(this), $(this).find(".fdPartsConfig").val(),false);
                    }
                });
                if(isPass){
                    var activePartConfigVal=$(this).find(".fdPartsConfig").val();
                    if(activePartConfigVal){
                        var fdParts =$.parseJSON(activePartConfigVal);
                        for(var i = 0; i < fdParts.length; i++){
                            if(fdParts[i]){
                                var fdType = fdParts[i].fdType;
                                self.createViewPortlet(fdType,fdParts[i]);
                            }
                        }
                    }
                    $(this).addClass("active");
                    //选中门户部件的数据类型特殊操作，显示选中状态下的占位图
                    self.switchContainerBackground( $(this), activePartConfigVal,true);
                    $(this).css("background_color" ,"rgba(66,133,244,0.10)");
                }
                $(".model-edit-view-bar").find("[resPanel-bar-mark='content']").trigger($.Event("click"));
            });
        },

        changeTemplateTypeEvent : function(){
            $(".modelAppSpaceTemplateList>li").click(function(){
                if(!$(this).hasClass("active")){
                    $(this).addClass("active").siblings().removeClass("active");
                }
                var tempNum= $(this).attr("data-template-select");
                $(".modelAppSpaceTempPreviewType[data-template="+tempNum+"]").show().siblings().hide();
                $("input[name='fdTemplateType']").val($(this).find("input[type=hidden]").val());
                //默认选中该模板的第一个容器
                $(".modelAppSpaceTempPrevieRowsItem").each(function(){
                    if($(this).hasClass("active")){
                        $(this).removeClass("active");
                    }
                })
                var fdTemplateType = $("input[name='fdTemplateType']").val();
                $(".modelAppSpaceTempPreviewType[data-template="+fdTemplateType+"]").find(".modelAppSpaceTempPrevieRows:first").find(".modelAppSpaceTempPrevieRowsItem:first").addClass("active")
                //如果是自定义 则另外处理
                if(fdTemplateType == "07"){
                    $(".modelAppSpaceTempPreviewType[data-template="+fdTemplateType+"]").find(".modelAppSpaceTempPrevieRows.custom:first").find(".modelAppSpaceTempPrevieRowsItem:first").addClass("active");
                }
            })
        },
        drawStyleColorSelect : function(){
            $(".fill_style_color_div").attr("data-color-mark-id","fillStyleColorSelectValue");
            if (window.SpectrumColorPicker) {
                window.SpectrumColorPicker.init("fillStyleColorSelectValue");
                window.SpectrumColorPicker.setColor("fillStyleColorSelectValue","#F0F0F0");
            }
        },
        // 增加部件按钮
        addRowsItemBtn : function(){
            var self = this;
            var isPass = true;
            $(".modelAppSpaceTempPrevieRowsItemBtn").on("click", function(e){
                e?e.stopPropagation():event.cancelBubble = true;
                $(".modelAppSpaceTempPrevieOptionList").remove();
                //点击容器中添加部件按钮时，同时给当前容器添加active
                $(".modelAppSpaceTempPrevieRowsItem").each(function(){
                    if($(this).hasClass("active")){
                        if(self.wgtPortlet){
                            //当右侧元素存在时，校验
                            if($(".panel-portlet-main").children().length>0){
                                isPass = self.wgtPortlet.submitChecked();
                                if(isPass == false){
                                    dialog.confirm(modelingLang["modelingAppSpace.verification.fail"],function(value){
                                        return;
                                    });
                                    return;
                                }
                            }
                            $(this).find(".fdPartsConfig").val(JSON.stringify(self.wgtPortlet.getKeyData()));
                            $(".panel-portlet-main").empty();
                            $(".panel-portlet-header").show();
                        }
                        $(this).removeClass("active");
                    }
                });
                //给需要新增的容器配置清空
                $(this).parent(".modelAppSpaceTempPrevieRowsItem").find(".fdPartsConfig").val("");
                if(isPass) {
                    if ($(this).parent().find(".portletUl").find(".modelAppSpaceTempPrevieOptionList").length == 0) {
                        $(this).parent().find(".portletUl").append('<ul class="modelAppSpaceTempPrevieOptionList"><li data-part-select="0">'+modelingLang["modelingAppSpace.styleTextPart"]+'</li><li data-part-select="1">'+modelingLang["modelingAppSpace.stylePictureParts"]+'</li><li data-part-select="2">'+modelingLang["modelingAppSpace.styleDataComponent"]+'</li></ul>');
                        //选择部件点击事件
                        $(".modelAppSpaceTempPrevieOptionList li").click(function () {
                            var fdType = $(this).attr("data-part-select");
                            $(this).parents(".modelAppSpaceTempPrevieRowsItem").find(".modelAppSpaceTempPrevieRowsItemBtn").remove();
                            var part = {};
                            self.createViewPortlet(fdType, part);
                            if (fdType == "2") {
                                //如果选择了数据部件则给当前所选容器添加默认为数字的占位图
                                $(this).parents(".modelAppSpaceTempPrevieRowsItem").css("background", "url('../base/space/res/images/pure-number.png') no-repeat center");
                                $(this).parents(".modelAppSpaceTempPrevieRowsItem").css("background-size", "contain");
                            }
                        });
                    }
                    $(this).parent(".modelAppSpaceTempPrevieRowsItem ").addClass("active");
                    $(".model-edit-view-bar").find("[resPanel-bar-mark='content']").trigger($.Event("click"));
                }
            });
            $(document).click(function(){
                $(".modelAppSpaceTempPrevieOptionList").remove();
                $(".sp-container").addClass("sp-hidden");
            });
        },
        createViewPortlet : function(fdType,parts){
            var self = this;
            $(".panel-portlet-main").empty();
            $(".panel-portlet-header").show();
            var viewPortletEle ={
                fdPortletType: fdType,
                storeDataConfig:parts
            };
            var viewWgt = new viewPortlet.ViewPortlet(viewPortletEle);
            viewWgt.startup();
            viewWgt.draw();
            self.wgtPortlet=viewWgt;
            $(".panel-portlet-header").hide();

        },
        calcWidth : function(){
            var docWidth = document.body.scrollWidth;
            var docHeight = document.body.scrollHeight;
            //设置最外层容器高度
            $(".model-edit").css("height", docHeight);
            $(".modelAppSpaceMask").css("width",docWidth).css("height",docHeight);
        },
        createAppSpaceTemp : function(rowsProportion){
            var self = this;
            var curIndex ;
            var rowArr = [];
            if(rowsProportion){
                rowArr=rowsProportion;
            }else{
                $(".modelAppSpaceContainerSettingRatioContent>div").each(function(){
                    if(!$(this).is(':hidden')){
                        let curVal = $(this).find("input").val();
                        rowArr.push(curVal);
                    }
                })
            }
            // 获取当前选中的模板
            $(".modelAppSpaceTemplateList>li").each(function(){
                var rowTemp = "<div class='modelAppSpaceTempPrevieRows custom'></div>";
                if($(this).hasClass('active')){
                    curIndex = $(this).attr("data-template-select");

                    $(".modelAppSpaceTempPreviewType[data-template="+curIndex+"]").find(".modelAppSpaceTempPrevieRows:last").after(rowTemp);
                    for(var i=0;i<rowArr.length;i++){
                        // 根据所填比例在当前模板生产新的容器
                        $(".modelAppSpaceTempPreviewType[data-template="+curIndex+"]").find(".custom:last").append('<div class="modelAppSpaceTempPrevieRowsItem" style="height: 240px;flex: '+rowArr[i]+'" data-flex ="'+rowArr[i]+'"><div class="modelAppSpaceTempPrevieBtnDelete">'+modelingLang["modeling.page.delete"]+'</div><input type="hidden" class="fdPartsConfig" value=""><span class="modelAppSpaceTempPrevieRowsItemBtn"><i></i>'+modelingLang["modelingAppSpace.styleAddPart"]+'</span><span class="portletUl"></span></div>')
                    }
                    //给动态添加的自定义容器绑定点击事件
                    //在绑定点击事件之前先删除绑定事件
                    var isPass = true;
                    $(".modelAppSpaceTempPrevieRows.custom").find(".modelAppSpaceTempPrevieRowsItem").unbind("click");
                    $(".modelAppSpaceTempPrevieRows.custom").find(".modelAppSpaceTempPrevieRowsItem").click(function(e){
                        e?e.stopPropagation():event.cancelBubble = true;
                        $(".modelAppSpaceTempPrevieRowsItem").each(function(){
                            if($(this).hasClass("active")){
                                if(self.wgtPortlet &&  $(".panel-portlet-main").children().length>0 ){
                                    isPass = self.wgtPortlet.submitChecked();
                                    if(isPass == false){
                                        dialog.confirm(modelingLang["modelingAppSpace.verification.fail"],function(value){
                                            return;
                                        });
                                        return;
                                    }
                                    $(this).find(".fdPartsConfig").val(JSON.stringify(self.wgtPortlet.getKeyData()));
                                    $(".panel-portlet-main").empty();
                                    $(".panel-portlet-header").show();
                                }
                                $(this).removeClass("active");
                                //当切换容器时，当前容器为数据部件且为门户类型时，未选中状态时占位图变换为灰色
                                self.switchContainerBackground( $(this), $(this).find(".fdPartsConfig").val(),false);
                            }
                        });
                        if(isPass){
                            var activePartConfigVal=$(this).find(".fdPartsConfig").val();
                            if(activePartConfigVal){
                                var fdParts =$.parseJSON(activePartConfigVal);
                                for(var i = 0; i < fdParts.length; i++){
                                    if(fdParts[i]){
                                        var fdType = fdParts[i].fdType;
                                        self.createViewPortlet(fdType,fdParts[i]);
                                    }
                                }
                            }
                            $(".modelAppSpaceTempPrevieOptionList").remove();
                            $(this).addClass("active");
                            //选中门户部件的数据类型特殊操作，显示选中状态下的占位图
                            self.switchContainerBackground( $(this), activePartConfigVal,true);
                            $(this).css("background_color" ,"rgba(66,133,244,0.10)");
                            $(".model-edit-view-bar").find("[resPanel-bar-mark='content']").trigger($.Event("click"));
                        }
                    });
                    // 移动时显示删除按钮
                    $(".modelAppSpaceTempPrevieRows.custom>div").on("mouseenter",function(){
                        $(this).find(".modelAppSpaceTempPrevieBtnDelete").show()

                    }).on("mouseleave",function(){
                        $(this).find(".modelAppSpaceTempPrevieBtnDelete").hide()
                    });

                    $(".modelAppSpaceTempPrevieBtnDelete").on("mouseenter",function(e){
                        e?e.stopPropagation():event.cancelBubble = true;
                    });
                    $(".modelAppSpaceTempPrevieBtnDelete").unbind("click");
                    $(".modelAppSpaceTempPrevieBtnDelete").on("click",function(e){
                        e?e.stopPropagation():event.cancelBubble = true;
                        var delThis = $(this);
                        dialog.confirm(modelingLang["modelingAppSpace.checkDelete.tips"], function (value) {
                            if (value === true) {
                                // 只有一个部件时删除整行
                                if(delThis.parents('.modelAppSpaceTempPrevieRows').children().length <= 1){
                                    delThis.parents('.modelAppSpaceTempPrevieRows').remove();
                                }else{
                                    delThis.parent('.modelAppSpaceTempPrevieRowsItem').remove();
                                }
                                $(".panel-portlet-main").empty();
                                $(".panel-portlet-header").show();
                                $(".model-edit-view-bar").find("[resPanel-bar-mark='basic']").trigger($.Event("click"));
                            }
                        });
                    });
                    $(".modelAppSpaceTempPrevieRows.custom").find(".modelAppSpaceTempPrevieRowsItemBtn").on("click", function(e){
                        e?e.stopPropagation():event.cancelBubble = true;
                        $(".modelAppSpaceTempPrevieOptionList").remove();
                        //点击增加部件按钮给当前容器 选中
                        $(".modelAppSpaceTempPrevieRowsItem").each(function(){
                            if($(this).hasClass("active")){
                                if(self.wgtPortlet){
                                    //当右侧元素存在时，校验
                                    if($(".panel-portlet-main").children().length>0) {
                                        isPass = self.wgtPortlet.submitChecked();
                                        if (isPass == false) {
                                            dialog.confirm(modelingLang["modelingAppSpace.verification.fail"], function (value) {
                                                return;
                                            });
                                            return;
                                        }
                                    }
                                    $(this).find(".fdPartsConfig").val(JSON.stringify(self.wgtPortlet.getKeyData()));
                                    $(".panel-portlet-main").empty();
                                    $(".panel-portlet-header").show();

                                }
                                $(this).removeClass("active");
                            }
                        });
                        if(isPass){
                            //给需要新增的容器配置清空
                            $(this).parent(".modelAppSpaceTempPrevieRowsItem").find(".fdPartsConfig").val("");
                            if($(this).parent().find(".portletUl").find(".modelAppSpaceTempPrevieOptionList").length==0){
                                $(this).parent().find(".portletUl").append('<ul class="modelAppSpaceTempPrevieOptionList"><li data-part-select="0">'+modelingLang["modelingAppSpace.styleTextPart"]+'</li><li data-part-select="1">'+modelingLang["modelingAppSpace.stylePictureParts"]+'</li><li data-part-select="2">'+modelingLang["modelingAppSpace.styleDataComponent"]+'</li></ul>');
                                //选择部件点击事件
                                $(".modelAppSpaceTempPrevieOptionList li").click(function(){
                                    var fdType = $(this).attr("data-part-select");
                                    $(this).parents(".modelAppSpaceTempPrevieRowsItem").find(".modelAppSpaceTempPrevieRowsItemBtn").remove();
                                    var part = {};
                                    self.createViewPortlet(fdType,part);
                                    if(fdType == "2"){
                                        //如果选择了数据部件则给当前所选容器添加默认为数字的占位图
                                        $(this).parents(".modelAppSpaceTempPrevieRowsItem").css("background","url('../base/space/res/images/pure-number.png') no-repeat center");
                                        $(this).parents(".modelAppSpaceTempPrevieRowsItem").css("background-size","contain");
                                    }
                                    $(".modelAppSpaceTempPrevieOptionList").remove();
                                })
                            }
                            $(this).parent(".modelAppSpaceTempPrevieRowsItem").addClass("active");
                            $(".model-edit-view-bar").find("[resPanel-bar-mark='content']").trigger($.Event("click"));
                        }
                    });
                }
            })
            this.closeModelAppSpacePop();
            var fdTemplateType = $("input[name='fdTemplateType']").val();
            if(fdTemplateType == "07"){
                $(".modelAppSpaceTempPreviewType[data-template="+fdTemplateType+"]").find(".modelAppSpaceTempPrevieRows.custom:first").find(".modelAppSpaceTempPrevieRowsItem:first").addClass("active");
            };
        },
        // 关闭弹窗
        closeModelAppSpacePop :function(){
            $(".modelAppSpaceMask").hide();
            $(".modelAppSpaceContainerSettingPop").hide();
        },
        // 切换部件类型的占位图
        switchContainerBackground :function($This,fdPartsConfig , isActive){
            var self = this;
            if(fdPartsConfig){
                var  activePart = $.parseJSON(fdPartsConfig);
                var dataPortlet =  activePart[0].dataPortlet || {};
                var displayType = dataPortlet.fdDisplayType || 0;
                if(displayType == "7"){
                    var portletType  = dataPortlet.fdPortletConfig.portletType;
                    self.toSetPortletStyle($This, portletType , isActive);
                }
            }
        },

        selectMaterial :function(){
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
                        $(".modelAppSpaceStyleUploadBtn").hide();
                        $(".modelAppSpaceStyleCheckedImginfo").show();
                        $(".modelAppSpaceStyleCheckedImginfo img").attr("src",url);
                        $(".modelAppSpaceStyleUploadBtnBox input").val(fdAttrId);
                    }
                }
            }).show();
        },
        styleUploadMask : function(){
            $(".modelAppSpaceStyleUploadedBtn").mouseenter(function(){
                if($(this).find(".modelAppSpaceStyleUploadMask").is(':hidden')){
                    $(this).find(".modelAppSpaceStyleUploadMask").show();
                }
            }).mouseleave(function(){
                $(this).find(".modelAppSpaceStyleUploadMask").hide();
            });
            $('.modelAppSpaceStyleUploadedDel').click(function(e){
                e?e.stopPropagation():event.cancelBubble = true;
                $(".modelAppSpaceStyleUploadBtn").show();
                $(".modelAppSpaceStyleCheckedImginfo").hide();
                $(".modelAppSpaceStyleCheckedImginfo img").attr("src","");
                $(".modelAppSpaceStyleUploadBtnBox input").val("");
            });
        },
        getKeyData : function(){
            var keyData = {};
            var fdStyle = {};
            fdStyle.fdBackgroundType = this.element.find("[name='StyleBg']:checked").val();
            fdStyle.fdFillColor=this.element.find(".fill_style_color_div").find("input[type='hidden']").val();
            var fdImage ={};
            fdImage.fdUrlId= this.element.find(".modelAppSpaceStyleUploadBtnBox").find("input").val();
            fdImage.fdFillStyle=$(".modelAppSpaceContainerfdFillStyle").children('input').val();
            fdStyle.fdImage =fdImage;
            fdStyle.fdTemplateType=$("input[name='fdTemplateType']").val();
            var fdRows = [];
            //设置行参数
            var dataTemplate=$("input[name='fdTemplateType']").val();
            var $previeRows = $(".modelAppSpaceTempPreviewType[data-template="+dataTemplate+"]").find(".modelAppSpaceTempPrevieRows");
            //如果是自定义模板时，则去掉第一行模板行。
            if(fdStyle.fdTemplateType == "07"){
                $previeRows = $(".modelAppSpaceTempPreviewType[data-template="+dataTemplate+"]").find(".modelAppSpaceTempPrevieRows:gt(0)");
            }
            $previeRows.each(function(){
                //行
                var row = {};
                //容器数
                var fdContainers = [];
                $(this).find(".modelAppSpaceTempPrevieRowsItem").each(function(){
                    var rqJson={};
                    var fdProportion = $(this).attr("data-flex");
                    //容器比例
                    rqJson.fdProportion =fdProportion;
                    //容器部件
                    var partConfigVal=$(this).find(".fdPartsConfig").val();
                    var fdParts=[];
                    if(partConfigVal){
                        fdParts=$.parseJSON(partConfigVal);
                    }
                    rqJson.fdParts = fdParts
                    fdContainers.push(rqJson);
                });
                row.fdContainers = fdContainers;
                fdRows.push(row);
            })

            keyData.fdStyle = fdStyle;
            keyData.fdRows = fdRows;
            return keyData;
        },
        // 获取右侧部件数据
        getRightKeyData :function(){
            var self = this;
            $(".modelAppSpaceTempPrevieRowsItem").each(function(){
                if($(this).hasClass("active")){
                    if(self.wgtPortlet &&  $(".panel-portlet-main").children().length>0 ){
                        $(this).find(".fdPartsConfig").val(JSON.stringify(self.wgtPortlet.getKeyData()));
                    }
                }
            })
        },
        //数据部件联动
        toSetDataPortletStyle : function (fdDisplayHeight,portletType){
            var self = this;
            if (fdDisplayHeight) {
                $(".modelAppSpaceTempPrevieRowsItem").each(function(){
                    if($(this).hasClass("active")){
                        $(this).css("height",fdDisplayHeight);
                        self.toSetPortletStyle($(this), portletType ,true);
                        $(this).css("background-size","contain");
                    }
                })
            }
        },
        //文本实现联动
        toSetTextStyle : function (textKeyData){
            var self = this;
            if (JSON.stringify(textKeyData) == "{}") {
                return;
            }
            $(".modelAppSpaceTempPrevieRowsItem").each(function(){
                if($(this).hasClass("active")){
                    var $THIS =$(this);
                    self.toSetTextValue(textKeyData , $THIS);
                }
            })
        },
        //初始化文本左侧预览
        toSetTextValue : function (textKeyData,$THIS){
            //背景
            var fdBackgroundType=textKeyData.fdBackgroundType;
            var fdFillColor=textKeyData.fdFillColor;
            var fdUrlId=textKeyData.fdImgBackground.fdUrlId;
            var url =Com_Parameter.ContextPath + "sys/attachment/sys_att_main/sysAttMain.do?method=download&open=1&fdId="+fdUrlId;
            var fdFillStyle=textKeyData.fdImgBackground.fdFillStyle;
            var fillStyle= "contain";
            if(fdFillStyle){
                switch (fdFillStyle) {
                    case "1":
                        fillStyle = "cover";
                        break;
                    case "2":
                        fillStyle = "contain";
                        break;
                    case "3":
                        fillStyle = "100% 100%";
                        break;
                    default:
                        break;
                }
            }
            var fdTextContent=textKeyData.fdTextContent;
            var textFace=textKeyData.fdTextSetting.textFace;
            var textFaceName=textKeyData.fdTextSetting.textFaceName;
            var textSizeName=textKeyData.fdTextSetting.textSizeName;
            var textSize=textKeyData.fdTextSetting.textSize;
            var textColor=textKeyData.fdTextSetting.textColor;
            var textBold=textKeyData.fdTextSetting.textBold;
            var textStyle=textKeyData.fdTextSetting.textStyle;
            var textDecoration=textKeyData.fdTextSetting.textDecoration;
            var textAlign=textKeyData.fdTextSetting.textAlign;
            var isOpenLink=textKeyData.isOpenLink;
            var fdLinkUrl=textKeyData.fdLinkUrl;
            var fdDisplayHeight = textKeyData.fdDisplayHeight;
            var fdOverstep = textKeyData.fdOverstep;
            var style= "color:"+textColor+";font-style:"+textStyle+";font-weight:"+textBold+";text-align:"+textAlign+";text-decoration:"+textDecoration+";font-family:"+textFaceName+";font-size:"+textSize+";width:98%;heigth:98%;";
            $THIS.find(".space-text-content").remove();
            $THIS.append('<div class="space-text-content" style="'+style+'">'+fdTextContent+'</div>');
            $THIS.css("height",fdDisplayHeight);
            if(fdOverstep){
                $THIS.css("overflow","auto");
            }else{
                $THIS.css("overflow","unset");
            }
            if(fdBackgroundType == "0"){
                $THIS.css("background","");
                let cssText = $THIS.attr("style") + ";background: "+fdFillColor+" !important;";
                $THIS.css("cssText", cssText);
            }else{
                if(fdUrlId){
                    var backgroundValue="url('"+url+"') no-repeat center";
                    $THIS.css("background",backgroundValue);
                    $THIS.css("background-size",fillStyle);
                }else{
                    $THIS.css("background","");
                }
            }


        },
        //图片实现联动
        toSetImageStyle : function (imageKeyData){
            var self = this;
            if (JSON.stringify(imageKeyData) == "{}") {
                return;
            }
            $(".modelAppSpaceTempPrevieRowsItem").each(function(){
                if($(this).hasClass("active")){
                    var $THIS =$(this);
                    self.toSetImageValue(imageKeyData , $THIS);
                }
            })
        },
        //初始化图片左侧预览
        toSetImageValue : function (imageKeyData,$THIS){
            //背景
            var fdFillColor=imageKeyData.fdFillColor;
            var fdUrlId=imageKeyData.fdImgContent.fdUrlId;
            var url =Com_Parameter.ContextPath + "sys/attachment/sys_att_main/sysAttMain.do?method=download&open=1&fdId="+fdUrlId;
            var fdFillStyle=imageKeyData.fdImgContent.fdFillStyle;
            var fillStyle= "contain";
            if(fdFillStyle){
                switch (fdFillStyle) {
                    case "1":
                        fillStyle = "cover";
                        break;
                    case "2":
                        fillStyle = "contain";
                        break;
                    case "3":
                        fillStyle = "100% 100%";
                        break;
                    default:
                        break;
                }
            }
            var fdDisplayHeight = imageKeyData.fdDisplayHeight;
            if(fdUrlId){
                var backgroundValue="url('"+url+"') no-repeat center"
                $THIS.css("background",backgroundValue);
                $THIS.css("background-size",fillStyle);
            }else{
                $THIS.css("background","");
            }
            $THIS.css("height",fdDisplayHeight);
            let cssText = $THIS.attr("style") + ";background-color: "+fdFillColor+" !important;";
            $THIS.css("cssText", cssText);
            $THIS.find(".space-text-content").remove();
        },
        submitChecked : function(){
            var self = this;
            if(self.wgtPortlet){
                var isPass = self.wgtPortlet.submitChecked();
                return isPass;
            }
        },
        //切换部件
        changePortletRight : function(fdType,part){
            var self = this;
            //获取当前被选中的容器
            var rowsThisItem = "";
            $(".modelAppSpaceTempPrevieRowsItem").each(function(){
                if($(this).hasClass("active")){
                    rowsThisItem =$(this);
                }
            });
            dialog.confirm(modelingLang['modelingAppSpace.changeProtletTis'], function (value) {
                if (value === true) {
                    if(rowsThisItem){
                        rowsThisItem.find(".fdPartsConfig").val("");
                        self.createViewPortlet(fdType,part);
                        if(fdType == "2"){
                            //如果选择了数据部件则给当前所选容器添加默认为数字的占位图
                            rowsThisItem.css("background","url('../base/space/res/images/pure-number.png') no-repeat center");
                            rowsThisItem.css("background-size","contain");
                        }else{
                            rowsThisItem.css("background-color","rgba(66,133,244,0.10)");
                            rowsThisItem.css("background","");
                        }
                        rowsThisItem.find(".space-text-content").remove();
                        rowsThisItem.css("height","240px");
                    }
                }
            });
        },
        toSetPortletStyle: function($THIS,portletType ,isActive){
            var rowsItemWidth =$THIS.width() + 2;
            var rowsItemHeight =$THIS.height() + 2;
            if(portletType == "1"){ //简单列表
                if(rowsItemWidth < 104 || rowsItemHeight < 32 ){ //小尺寸
                    if(isActive){
                        $THIS.css("background","url('../base/space/res/images/simplelist-small.png') no-repeat center");
                    }else{
                        $THIS.css("background","url('../base/space/res/images/simplelist-small-unchecked.png') no-repeat center");
                    }
                }else{
                    if(isActive){
                        $THIS.css("background","url('../base/space/res/images/simplelist-big.png') no-repeat center");
                    }else{
                        $THIS.css("background","url('../base/space/res/images/simplelist-big-unchecked.png') no-repeat center");
                    }
                }
            }else if(portletType == "2"){ //图文摘要
                if(rowsItemWidth < 104 || rowsItemHeight < 60 ){ //小尺寸
                    if(isActive){
                        $THIS.css("background","url('../base/space/res/images/imgAndText-small.png') no-repeat center");
                    }else{
                        $THIS.css("background","url('../base/space/res/images/imgAndText-small-unchecked.png') no-repeat center");
                    }
                }else{
                    if(isActive){
                        $THIS.css("background","url('../base/space/res/images/imgAndText-big.png') no-repeat center");
                    }else{
                        $THIS.css("background","url('../base/space/res/images/imgAndText-big-unchecked.png') no-repeat center");
                    }
                }
            }else if(portletType == "3"){ //图片宫格
                if(rowsItemWidth < 70|| rowsItemHeight < 78 ){ //小尺寸
                    if(isActive){
                        $THIS.css("background","url('../base/space/res/images/picturegrid-small.png') no-repeat center");
                    }else{
                        $THIS.css("background","url('../base/space/res/images/picturegrid-small-unchecked.png') no-repeat center");
                    }
                }else{
                    if(isActive){
                        $THIS.css("background","url('../base/space/res/images/picturegrid-big.png') no-repeat center");
                    }else{
                        $THIS.css("background","url('../base/space/res/images/picturegrid-big-unchecked.png') no-repeat center");
                    }
                }
            }else if(portletType == "4"){ //幻灯片
                if(rowsItemWidth < 70 || rowsItemHeight < 70 ){ //小尺寸
                    if(isActive){
                        $THIS.css("background","url('../base/space/res/images/pictureslide-small.png') no-repeat center");
                    }else{
                        $THIS.css("background","url('../base/space/res/images/pictureslide-small-unchecked.png') no-repeat center");
                    }
                }else{
                    if(isActive){
                        $THIS.css("background","url('../base/space/res/images/pictureslide-big.png') no-repeat center");
                    }else{
                        $THIS.css("background","url('../base/space/res/images/pictureslide-big-unchecked.png') no-repeat center");
                    }
                }
            }else if(portletType == "5"){ //列表视图
                if(rowsItemWidth < 104 || rowsItemHeight < 60 ){ //小尺寸
                    if(isActive){
                        $THIS.css("background","url('../base/space/res/images/listview-small.png') no-repeat center");
                    }else{
                        $THIS.css("background","url('../base/space/res/images/listview-small-unchecked.png') no-repeat center");
                    }
                }else{
                    if(isActive){
                        $THIS.css("background","url('../base/space/res/images/listview-big.png') no-repeat center");
                    }else{
                        $THIS.css("background","url('../base/space/res/images/listview-big-unchecked.png') no-repeat center");
                    }
                }
            }else if(portletType == "6"){ //时间轴列表
                if(rowsItemWidth < 104 || rowsItemHeight < 60 ){ //小尺寸
                    if(isActive){
                        $THIS.css("background","url('../base/space/res/images/timeaxis-small.png') no-repeat center");
                    }else{
                        $THIS.css("background","url('../base/space/res/images/timeaxis-small-unchecked.png') no-repeat center");
                    }
                }else{
                    if(isActive){
                        $THIS.css("background","url('../base/space/res/images/timeaxis-big.png') no-repeat center");
                    }else{
                        $THIS.css("background","url('../base/space/res/images/timeaxis-big-unchecked.png') no-repeat center");
                    }
                }
            }
            $THIS.css("background-size","contain");
        },
        initByStoreData : function(storeData){
            var self = this;
            if(JSON.stringify(storeData) == "{}"){
                return;
            }
            var style = storeData.fdStyle;
            if(style.fdBackgroundType == "0"){
                $(".modelAppSpaceStyleBgOnPicture").hide();
                $(".modelAppSpaceStyleBgOnColor").show();
                this.element.find("[name='StyleBg'][value='0']").attr("checked",true);
                window.SpectrumColorPicker.setColor("fillStyleColorSelectValue",style.fdFillColor);
            }else{
                $(".modelAppSpaceStyleBgOnPicture").show();
                $(".modelAppSpaceStyleBgOnColor").hide();
                this.element.find("[name='StyleBg'][value='1']").attr("checked",true);
                var fdImage=style.fdImage;
                if(fdImage.fdUrlId){
                    var url =  Com_Parameter.ContextPath + "sys/attachment/sys_att_main/sysAttMain.do?method=download&open=1&fdId="+fdImage.fdUrlId;
                    $(".modelAppSpaceStyleUploadBtn").hide();
                    $(".modelAppSpaceStyleCheckedImginfo").show();
                    $(".modelAppSpaceStyleCheckedImginfo img").attr("src",url);
                    $(".modelAppSpaceStyleUploadBtnBox input").val(fdImage.fdUrlId);
                }else{
                    $(".modelAppSpaceStyleUploadBtn").show();
                    $(".modelAppSpaceStyleCheckedImginfo").hide();
                }
                var fdFillStyle=fdImage.fdFillStyle;
                var selectText=this.element.find("[data-filltyle-select='"+fdFillStyle+"']").text();
                this.element.find('.modelAppSpaceContainerfdFillStyle').children('span').text(selectText);
                this.element.find('.modelAppSpaceContainerfdFillStyle').children('input').val(fdFillStyle);
            }
            //回显对应的模板类型添加active选中
            this.element.find("[name='fdTemplateType']").val(style.fdTemplateType);
            var $temp = this.element.find("[data-template-select='"+style.fdTemplateType+"']");
            if(!$temp.hasClass("active")){
                $temp.addClass("active").siblings().removeClass("active");
            }
            //模板的行数大小
            var tempRowSize = $(".modelAppSpaceTempPreviewType[data-template="+style.fdTemplateType+"]").find(".modelAppSpaceTempPrevieRows").length;
            $(".modelAppSpaceTempPreviewType[data-template="+style.fdTemplateType+"]").show().siblings().hide();
            //构建行以及每行的容器以及高度
            var fdRows = storeData.fdRows;
            for(var i = 0; i < fdRows.length; i++){
                var rowArr = []; //容器比例数组
                var fdContainers = fdRows[i].fdContainers;

                for(var j = 0; j < fdContainers.length; j++){
                    var fdProportion = fdContainers[j].fdProportion;
                    rowArr.push(fdProportion);
                }
                //判断是否是自定义模板
                if (style.fdTemplateType == "07"){
                    self.createAppSpaceTemp(rowArr);
                }else{
                    //判断数据库中保存的行数fdRows是否大于模板的行数大小，超出部分重新构建
                    if(i>tempRowSize-1){
                        self.createAppSpaceTemp(rowArr);
                    }
                }
                //回写行和容器部件配置内容
                var $row=$(".modelAppSpaceTempPreviewType[data-template="+style.fdTemplateType+"]").find(".modelAppSpaceTempPrevieRows:eq("+i+")");
                if (style.fdTemplateType == "07"){
                    $row=$(".modelAppSpaceTempPreviewType[data-template="+style.fdTemplateType+"]").find(".modelAppSpaceTempPrevieRows:eq("+(i+1)+")");
                }
                for(var k = 0; k < fdContainers.length; k++){
                    var fdParts=fdContainers[k].fdParts;
                    if(fdParts.length>0){
                        $row.children(".modelAppSpaceTempPrevieRowsItem:eq("+k+")").find(".fdPartsConfig").val(JSON.stringify(fdParts));
                        $row.children(".modelAppSpaceTempPrevieRowsItem:eq("+k+")").find(".modelAppSpaceTempPrevieRowsItemBtn").remove();
                        //获取容器是否是文本部件，如果是则初始化预览图
                        var textPortlet = fdParts[0].textPortlet || {};
                        if (JSON.stringify(textPortlet) != "{}") {
                            self.toSetTextValue( textPortlet, $row.children(".modelAppSpaceTempPrevieRowsItem:eq("+k+")"));
                        }
                        //获取容器是否是图片部件，如果是则初始化预览图
                        var picturePortlet = fdParts[0].picturePortlet || {};
                        if (JSON.stringify(picturePortlet) != "{}") {
                            self.toSetImageValue(picturePortlet, $row.children(".modelAppSpaceTempPrevieRowsItem:eq("+k+")"));
                        }
                        //获取容器部件是否是数据部件，如果是则初始化显示类型占位图
                        var dataPortlet =  fdParts[0].dataPortlet || {};
                        var displayType = dataPortlet.fdDisplayType || 0;
                        //初始化左侧容器占位图
                        switch(displayType){
                            case "1":
                                //数字
                                $row.children(".modelAppSpaceTempPrevieRowsItem:eq("+k+")").css("background","url('../base/space/res/images/pure-number.png') no-repeat center");
                                $row.children(".modelAppSpaceTempPrevieRowsItem:eq("+k+")").css("background-size","contain");
                                $row.children(".modelAppSpaceTempPrevieRowsItem:eq("+k+")").css("height",""+dataPortlet.fdDisplayHeight+"");
                                break;
                            case "2":
                                //图表
                                $row.children(".modelAppSpaceTempPrevieRowsItem:eq("+k+")").css("background","url('../base/space/res/images/chart-zw.png') no-repeat center");
                                $row.children(".modelAppSpaceTempPrevieRowsItem:eq("+k+")").css("background-size","contain");
                                $row.children(".modelAppSpaceTempPrevieRowsItem:eq("+k+")").css("height",""+dataPortlet.fdDisplayHeight+"");
                                break;
                            case "3":
                                //列表视图
                                $row.children(".modelAppSpaceTempPrevieRowsItem:eq("+k+")").css("background","url('../base/space/res/images/list-zw.png') no-repeat center");
                                $row.children(".modelAppSpaceTempPrevieRowsItem:eq("+k+")").css("background-size","contain");
                                $row.children(".modelAppSpaceTempPrevieRowsItem:eq("+k+")").css("height",""+dataPortlet.fdDisplayHeight+"");
                                break;
                            case "4":
                                //日历视图
                                $row.children(".modelAppSpaceTempPrevieRowsItem:eq("+k+")").css("background","url('../base/space/res/images/calendar-zw.png') no-repeat center");
                                $row.children(".modelAppSpaceTempPrevieRowsItem:eq("+k+")").css("background-size","contain");
                                $row.children(".modelAppSpaceTempPrevieRowsItem:eq("+k+")").css("height",""+dataPortlet.fdDisplayHeight+"");
                                break;
                            case "5":
                                //甘特视图
                                $row.children(".modelAppSpaceTempPrevieRowsItem:eq("+k+")").css("background","url('../base/space/res/images/gantt-chart.png') no-repeat center");
                                $row.children(".modelAppSpaceTempPrevieRowsItem:eq("+k+")").css("background-size","contain");
                                $row.children(".modelAppSpaceTempPrevieRowsItem:eq("+k+")").css("height",""+dataPortlet.fdDisplayHeight+"");
                                break;
                            case "6":
                                //资源面板视图
                                $row.children(".modelAppSpaceTempPrevieRowsItem:eq("+k+")").css("background","url('../base/space/res/images/resource-panel.png') no-repeat center");
                                $row.children(".modelAppSpaceTempPrevieRowsItem:eq("+k+")").css("background-size","contain");
                                $row.children(".modelAppSpaceTempPrevieRowsItem:eq("+k+")").css("height",""+dataPortlet.fdDisplayHeight+"");
                                break;
                            case "7":
                                //门户部件
                                $row.children(".modelAppSpaceTempPrevieRowsItem:eq("+k+")").css("height",""+dataPortlet.fdDisplayHeight+"");
                                //获取当前容器的宽度加上边框2px
                                if(dataPortlet.fdPortletConfig.portletType){
                                    //门户占位图
                                    self.toSetPortletStyle($row.children(".modelAppSpaceTempPrevieRowsItem:eq("+k+")") , dataPortlet.fdPortletConfig.portletType ,false);
                                    $row.children(".modelAppSpaceTempPrevieRowsItem:eq("+k+")").css("background-size","contain");
                                }else{
                                    $row.children(".modelAppSpaceTempPrevieRowsItem:eq("+k+")").css("background", "url('../base/space/res/images/no-portlet.png') no-repeat center");
                                    $row.children(".modelAppSpaceTempPrevieRowsItem:eq("+k+")").css("background-size", "contain");
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
            //获取第一行第一个容器
            var $firstRQ = "";
            //如果是自定义 则另外获取
            if(style.fdTemplateType == "07"){
                $firstRQ=$(".modelAppSpaceTempPreviewType[data-template="+style.fdTemplateType+"]").find(".modelAppSpaceTempPrevieRows.custom:first").find(".modelAppSpaceTempPrevieRowsItem:first");
            }else{
                $firstRQ=$(".modelAppSpaceTempPreviewType[data-template="+style.fdTemplateType+"]").find(".modelAppSpaceTempPrevieRows:first").find(".modelAppSpaceTempPrevieRowsItem:first");
            }
            var firstConfig= $firstRQ.find(".fdPartsConfig").val();
            //如果第一行第一个容器没有设置部件则初始化显示基本设置,否则显示部件设置
            $firstRQ.click();
            if(!firstConfig){
                $(".model-edit-view-bar").find("[resPanel-bar-mark='basic']").trigger($.Event("click"));
            }
        },
        getRightStyleHtml : function(){
            var html = "<div class='modelAppSpaceTemplateRightBox'>";
            html = '<div class="model-tab-table-slide-wrap">';
            html += '<div class="modelAppSpaceStyleInlineRow">';
            html += '<div class="modelAppSpaceStyleTitleType">'+modelingLang["modelingAppSpace.styleBackground"]+'</div>';
            html += '<div class="modelAppSpaceStyleBgContent">';
            html += '<label><input type="radio" name="StyleBg" id="onColor" value="0" checked="">'+modelingLang["modelingAppSpace.styleColour"]+'</label>';
            html += '<label><input type="radio" name="StyleBg" id="onPicture" value="1">'+modelingLang["modelingAppSpace.stylePicture"]+'</label>';
            html += '</div></div>';
            html += '<div class="modelAppSpaceStyleBgOnColor">';
            html += '<div class="modelAppSpaceStyleTitleType">'+modelingLang["modelingAppSpace.styleFillColor"]+'</div>';
            html += '<div class="modelAppSpaceStylePallet fill_style_color_div">' +
                '<div class="colorColorDiv" name="fill_color"><div data-lui-mark="colorColor"></div></div></div>';
            html += '</div>';
            html += ' <div class="modelAppSpaceStyleBgOnPicture">';
            html += '<div class="modelAppSpaceStyleTitleType">'+modelingLang["modelingAppSpace.styleUploadPictures"]+'</div>';
            html += ' <div class="modelAppSpaceStyleUploadBtnBox">';
            html += ' <div class="modelAppSpaceStyleUploadBtn">';
            html += ' <i class="modelAppSpaceIcon modelAppSpaceStyleAdd"></i>';
            html += ' </div><div class="modelAppSpaceStyleCheckedImginfo" style="display: none;"><div class="modelAppSpaceStyleUploadedBtn"> <img src="" style="width:100%;height:100%;" alt=""><input type="hidden" id="imgId" value=""><div class="modelAppSpaceStyleUploadMask"><span>'+modelingLang["enums.behavior_type.3"]+'</span></div></div><div class="modelAppSpaceStyleUploadedDel"></div></div></div>';
            html += ' <span class="modelAppSpacePorletsUploadTips">'+modelingLang["modelingAppSpace.styleUploadTips"]+'</span>';
            html += ' <div class="modelAppSpaceStyleInlineRow">';
            html += ' <div class="modelAppSpaceStyleTitleType">'+modelingLang["modelingAppSpace.styleFillType"]+'</div>';
            html += ' <div class="modelAppSpaceContainerfdFillStyle muiPerformanceDropdownBox">';
            html += ' <span>'+modelingLang["modelingAppSpace.styleCenterFill"]+'</span><input type="hidden" value="1">';
            html += ' <i class="muiPerformanceDropdownBoxIcon"></i>';
            html += ' <div class="muiPerformanceDropdownBoxList">';
            html += ' <ul>';
            html += ' <li data-fillTyle-select="1">'+modelingLang["modelingAppSpace.styleCenterFill"]+'</li><li data-fillTyle-select="2">'+modelingLang["modelingAppSpace.styleCenter"]+'</li><li data-fillTyle-select="3">'+modelingLang["modelingAppSpace.styleStretchFill"]+'</li>';
            html += ' </div></div></div></div>';
            html += ' <div class="modelAppSpaceStyleInlineRow">';
            html += ' <div class="modelAppSpaceStyleTitleType">'+modelingLang["modelingAppSpace.styleTemplateSettings"]+'</div></div>';
            html += ' <div class="modelAppSpaceTemplateListContent">';
            html += ' <ul class="modelAppSpaceTemplateList">';
            html += ' <li class="modelAppSpaceTemplateListItem_01" data-template-select="01"><i></i><span>'+modelingLang["modelingAppSpace.styleTemplateOne"]+'</span><input type="hidden" value="01"></li>';
            html += ' <li class="modelAppSpaceTemplateListItem_02" data-template-select="02"><i></i><span>'+modelingLang["modelingAppSpace.styleTemplateTwo"]+'</span><input type="hidden" value="02"></li>';
            html += ' <li class="modelAppSpaceTemplateListItem_03" data-template-select="03"><i></i><span>'+modelingLang["modelingAppSpace.styleTemplateThree"]+'</span><input type="hidden" value="03"></li>';
            html += ' <li class="modelAppSpaceTemplateListItem_04" data-template-select="04"><i></i><span>'+modelingLang["modelingAppSpace.styleTemplateFour"]+'</span><input type="hidden" value="04"></li>';
            html += ' <li class="modelAppSpaceTemplateListItem_05" data-template-select="05"><i></i><span>'+modelingLang["modelingAppSpace.styleTemplateFive"]+'</span><input type="hidden" value="05"></li>';
            html += ' <li class="modelAppSpaceTemplateListItem_06" data-template-select="06"><i></i><span>'+modelingLang["modelingAppSpace.styleTemplateSix"]+'</span><input type="hidden" value="06"></li>';
            html += ' <li class="modelAppSpaceTemplateListItem_07" data-template-select="07"><i></i><span>'+modelingLang["modelingAppSpace.styleTemplateCustom"]+'</span><input type="hidden" value="07"></li>';
            html += ' </ul><input type="hidden" name="fdTemplateType" value="">';
            html += '</div></div>';
            return html;
        },
        getLeftHtml : function(){
            var html = "<div class='modelAppSpaceTemplateLeftBoxContent'><div class=\"modelAppSpaceTempPreviewType\" data-template=\"01\">\n" +
                "                        <div class=\"modelAppSpaceTempPrevieRows\">\n" +
                "                            <div class=\"modelAppSpaceTempPrevieRowsItem\" style=\"height: 240px;\" data-flex=\"1\">\n" +
                "                                <input type=\"hidden\" class=\"fdPartsConfig\" value=\"\"><span class=\"modelAppSpaceTempPrevieRowsItemBtn\"><i></i>"+modelingLang["modelingAppSpace.styleAddPart"]+"</span><span class='portletUl'></span>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <div class=\"modelAppSpaceTempPrevieRows\">\n" +
                "                            <div class=\"modelAppSpaceTempPrevieRowsItem\" style=\"height: 240px;\" data-flex=\"1\">\n" +
                "                                <input type=\"hidden\" class=\"fdPartsConfig\" value=\"\"><span class=\"modelAppSpaceTempPrevieRowsItemBtn\"><i></i>"+modelingLang["modelingAppSpace.styleAddPart"]+"</span><span class='portletUl'></span>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <div class=\"modelAppSpaceTempPrevieRows\">\n" +
                "                            <div class=\"modelAppSpaceTempPrevieRowsItem\" style=\"height: 240px;\" data-flex=\"1\">\n" +
                "                                <input type=\"hidden\" class=\"fdPartsConfig\" value=\"\"><span class=\"modelAppSpaceTempPrevieRowsItemBtn\"><i></i>"+modelingLang["modelingAppSpace.styleAddPart"]+"</span><span class='portletUl'></span>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <div class=\"modelAppSpaceTempPrevieBtnRows\">\n" +
                "                            <div class=\"modelAppSpaceTempPrevieRowsAddContainer\">\n" +
                "                                "+modelingLang["modelingAppSpace.styleAddContainer"]+"\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                    <div class=\"modelAppSpaceTempPreviewType\" data-template=\"02\">\n" +
                "                        <div class=\"modelAppSpaceTempPrevieRows\">\n" +
                "                            <div class=\"modelAppSpaceTempPrevieRowsItem\" style=\"height: 240px;\" data-flex=\"1\">\n" +
                "                                <input type=\"hidden\" class=\"fdPartsConfig\" value=\"\"><span class=\"modelAppSpaceTempPrevieRowsItemBtn\"><i></i>"+modelingLang["modelingAppSpace.styleAddPart"]+"</span><span class='portletUl'></span>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <div class=\"modelAppSpaceTempPrevieRows\">\n" +
                "                            <div class=\"modelAppSpaceTempPrevieRowsItem\" style=\"height: 240px;\" data-flex=\"1\">\n" +
                "                                <input type=\"hidden\" class=\"fdPartsConfig\" value=\"\"><span class=\"modelAppSpaceTempPrevieRowsItemBtn\"><i></i>"+modelingLang["modelingAppSpace.styleAddPart"]+"</span><span class='portletUl'></span>\n" +
                "                            </div>\n" +
                "                            <div class=\"modelAppSpaceTempPrevieRowsItem\" style=\"height: 240px;\" data-flex=\"1\">\n" +
                "                                <input type=\"hidden\" class=\"fdPartsConfig\" value=\"\"><span class=\"modelAppSpaceTempPrevieRowsItemBtn\"><i></i>"+modelingLang["modelingAppSpace.styleAddPart"]+"</span><span class='portletUl'></span>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <div class=\"modelAppSpaceTempPrevieRows\">\n" +
                "                            <div class=\"modelAppSpaceTempPrevieRowsItem\" style=\"height: 240px;\" data-flex=\"1\">\n" +
                "                                <input type=\"hidden\" class=\"fdPartsConfig\" value=\"\"><span class=\"modelAppSpaceTempPrevieRowsItemBtn\"><i></i>"+modelingLang["modelingAppSpace.styleAddPart"]+"</span><span class='portletUl'></span>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <div class=\"modelAppSpaceTempPrevieBtnRows\">\n" +
                "                            <div class=\"modelAppSpaceTempPrevieRowsAddContainer\">\n" +
                "                                "+modelingLang["modelingAppSpace.styleAddContainer"]+"\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                    <div class=\"modelAppSpaceTempPreviewType\" data-template=\"03\">\n" +
                "                        <div class=\"modelAppSpaceTempPrevieRows\">\n" +
                "                            <div class=\"modelAppSpaceTempPrevieRowsItem\" style=\"height: 240px;\" data-flex=\"1\">\n" +
                "                                <input type=\"hidden\" class=\"fdPartsConfig\" value=\"\"><span class=\"modelAppSpaceTempPrevieRowsItemBtn\"><i></i>"+modelingLang["modelingAppSpace.styleAddPart"]+"</span><span class='portletUl'></span>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <div class=\"modelAppSpaceTempPrevieRows\">\n" +
                "                            <div class=\"modelAppSpaceTempPrevieRowsItem\" style=\"height: 240px;\" data-flex=\"1\">\n" +
                "                                <input type=\"hidden\" class=\"fdPartsConfig\" value=\"\"><span class=\"modelAppSpaceTempPrevieRowsItemBtn\"><i></i>"+modelingLang["modelingAppSpace.styleAddPart"]+"</span><span class='portletUl'></span>\n" +
                "                            </div>\n" +
                "                            <div class=\"modelAppSpaceTempPrevieRowsItem\" style=\"height: 240px;\" data-flex=\"3\">\n" +
                "                                <input type=\"hidden\" class=\"fdPartsConfig\" value=\"\"><span class=\"modelAppSpaceTempPrevieRowsItemBtn\"><i></i>"+modelingLang["modelingAppSpace.styleAddPart"]+"</span><span class='portletUl'></span>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <div class=\"modelAppSpaceTempPrevieRows\">\n" +
                "                            <div class=\"modelAppSpaceTempPrevieRowsItem\" style=\"height: 240px;\" data-flex=\"1\">\n" +
                "                                <input type=\"hidden\" class=\"fdPartsConfig\" value=\"\"><span class=\"modelAppSpaceTempPrevieRowsItemBtn\"><i></i>"+modelingLang["modelingAppSpace.styleAddPart"]+"</span><span class='portletUl'></span>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <div class=\"modelAppSpaceTempPrevieBtnRows\">\n" +
                "                            <div class=\"modelAppSpaceTempPrevieRowsAddContainer\">\n" +
                "                                "+modelingLang["modelingAppSpace.styleAddContainer"]+"\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                    <div class=\"modelAppSpaceTempPreviewType\" data-template=\"04\">\n" +
                "                        <div class=\"modelAppSpaceTempPrevieRows\">\n" +
                "                            <div class=\"modelAppSpaceTempPrevieRowsItem\" style=\"height: 240px;\" data-flex=\"1\">\n" +
                "                                <input type=\"hidden\" class=\"fdPartsConfig\" value=\"\"><span class=\"modelAppSpaceTempPrevieRowsItemBtn\"><i></i>"+modelingLang["modelingAppSpace.styleAddPart"]+"</span><span class='portletUl'></span>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <div class=\"modelAppSpaceTempPrevieRows\">\n" +
                "                            <div class=\"modelAppSpaceTempPrevieRowsItem\" style=\"height: 240px;\" data-flex=\"3\">\n" +
                "                                <input type=\"hidden\" class=\"fdPartsConfig\" value=\"\"><span class=\"modelAppSpaceTempPrevieRowsItemBtn\"><i></i>"+modelingLang["modelingAppSpace.styleAddPart"]+"</span><span class='portletUl'></span>\n" +
                "                            </div>\n" +
                "                            <div class=\"modelAppSpaceTempPrevieRowsItem\" style=\"height: 240px;\" data-flex=\"1\">\n" +
                "                                <input type=\"hidden\" class=\"fdPartsConfig\" value=\"\"><span class=\"modelAppSpaceTempPrevieRowsItemBtn\"><i></i>"+modelingLang["modelingAppSpace.styleAddPart"]+"</span><span class='portletUl'></span>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <div class=\"modelAppSpaceTempPrevieRows\">\n" +
                "                            <div class=\"modelAppSpaceTempPrevieRowsItem\" style=\"height: 240px;\" data-flex=\"1\">\n" +
                "                                <input type=\"hidden\" class=\"fdPartsConfig\" value=\"\"><span class=\"modelAppSpaceTempPrevieRowsItemBtn\"><i></i>"+modelingLang["modelingAppSpace.styleAddPart"]+"</span><span class='portletUl'></span>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <div class=\"modelAppSpaceTempPrevieBtnRows\">\n" +
                "                            <div class=\"modelAppSpaceTempPrevieRowsAddContainer\">\n" +
                "                                "+modelingLang["modelingAppSpace.styleAddContainer"]+"\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                    <div class=\"modelAppSpaceTempPreviewType\" data-template=\"05\">\n" +
                "                        <div class=\"modelAppSpaceTempPrevieRows\">\n" +
                "                            <div class=\"modelAppSpaceTempPrevieRowsItem\" style=\"height: 240px;\" data-flex=\"1\">\n" +
                "                                <input type=\"hidden\" class=\"fdPartsConfig\" value=\"\"><span class=\"modelAppSpaceTempPrevieRowsItemBtn\"><i></i>"+modelingLang["modelingAppSpace.styleAddPart"]+"</span><span class='portletUl'></span>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <div class=\"modelAppSpaceTempPrevieRows\">\n" +
                "                            <div class=\"modelAppSpaceTempPrevieRowsItem\" style=\"height: 240px;\" data-flex=\"1\">\n" +
                "                                <input type=\"hidden\" class=\"fdPartsConfig\" value=\"\"><span class=\"modelAppSpaceTempPrevieRowsItemBtn\"><i></i>"+modelingLang["modelingAppSpace.styleAddPart"]+"</span><span class='portletUl'></span>\n" +
                "                            </div>\n" +
                "                            <div class=\"modelAppSpaceTempPrevieRowsItem\" style=\"height: 240px;\" data-flex=\"1\">\n" +
                "                                <input type=\"hidden\" class=\"fdPartsConfig\" value=\"\"><span class=\"modelAppSpaceTempPrevieRowsItemBtn\"><i></i>"+modelingLang["modelingAppSpace.styleAddPart"]+"</span><span class='portletUl'></span>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <div class=\"modelAppSpaceTempPrevieRows\">\n" +
                "                            <div class=\"modelAppSpaceTempPrevieRowsItem\" style=\"height: 240px;\" data-flex=\"1\">\n" +
                "                                <input type=\"hidden\" class=\"fdPartsConfig\" value=\"\"><span class=\"modelAppSpaceTempPrevieRowsItemBtn\"><i></i>"+modelingLang["modelingAppSpace.styleAddPart"]+"</span><span class='portletUl'></span>\n" +
                "                            </div>\n" +
                "                            <div class=\"modelAppSpaceTempPrevieRowsItem\" style=\"height: 240px;\" data-flex=\"1\">\n" +
                "                                <input type=\"hidden\" class=\"fdPartsConfig\" value=\"\"><span class=\"modelAppSpaceTempPrevieRowsItemBtn\"><i></i>"+modelingLang["modelingAppSpace.styleAddPart"]+"</span><span class='portletUl'></span>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <div class=\"modelAppSpaceTempPrevieRows\">\n" +
                "                            <div class=\"modelAppSpaceTempPrevieRowsItem\" style=\"height: 240px;\" data-flex=\"1\">\n" +
                "                                <input type=\"hidden\" class=\"fdPartsConfig\" value=\"\"><span class=\"modelAppSpaceTempPrevieRowsItemBtn\"><i></i>"+modelingLang["modelingAppSpace.styleAddPart"]+"</span><span class='portletUl'></span>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <div class=\"modelAppSpaceTempPrevieBtnRows\">\n" +
                "                            <div class=\"modelAppSpaceTempPrevieRowsAddContainer\">\n" +
                "                                "+modelingLang["modelingAppSpace.styleAddContainer"]+"\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                    <div class=\"modelAppSpaceTempPreviewType\" data-template=\"06\">\n" +
                "                        <div class=\"modelAppSpaceTempPrevieRows\">\n" +
                "                            <div class=\"modelAppSpaceTempPrevieRowsItem\" style=\"height: 240px;\" data-flex=\"1\">\n" +
                "                                <input type=\"hidden\" class=\"fdPartsConfig\" value=\"\"><span class=\"modelAppSpaceTempPrevieRowsItemBtn\"><i></i>"+modelingLang["modelingAppSpace.styleAddPart"]+"</span><span class='portletUl'></span>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <div class=\"modelAppSpaceTempPrevieRows\">\n" +
                "                            <div class=\"modelAppSpaceTempPrevieRowsItem\" style=\"height: 240px;\" data-flex=\"1\">\n" +
                "                                <input type=\"hidden\" class=\"fdPartsConfig\" value=\"\"><span class=\"modelAppSpaceTempPrevieRowsItemBtn\"><i></i>"+modelingLang["modelingAppSpace.styleAddPart"]+"</span><span class='portletUl'></span>\n" +
                "                            </div>\n" +
                "                            <div class=\"modelAppSpaceTempPrevieRowsItem\" style=\"height: 240px;\" data-flex=\"2\">\n" +
                "                                <input type=\"hidden\" class=\"fdPartsConfig\" value=\"\"><span class=\"modelAppSpaceTempPrevieRowsItemBtn\"><i></i>"+modelingLang["modelingAppSpace.styleAddPart"]+"</span><span class='portletUl'></span>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <div class=\"modelAppSpaceTempPrevieRows\">\n" +
                "                            <div class=\"modelAppSpaceTempPrevieRowsItem\" style=\"height: 240px;\" data-flex=\"1\">\n" +
                "                                <input type=\"hidden\" class=\"fdPartsConfig\" value=\"\"><span class=\"modelAppSpaceTempPrevieRowsItemBtn\"><i></i>"+modelingLang["modelingAppSpace.styleAddPart"]+"</span><span class='portletUl'></span>\n" +
                "                            </div>\n" +
                "                            <div class=\"modelAppSpaceTempPrevieRowsItem\" style=\"height: 240px;\" data-flex=\"1\">\n" +
                "                                <input type=\"hidden\" class=\"fdPartsConfig\" value=\"\"><span class=\"modelAppSpaceTempPrevieRowsItemBtn\"><i></i>"+modelingLang["modelingAppSpace.styleAddPart"]+"</span><span class='portletUl'></span>\n" +
                "                            </div>\n" +
                "                            <div class=\"modelAppSpaceTempPrevieRowsItem\" style=\"height: 240px;\" data-flex=\"1\">\n" +
                "                                <input type=\"hidden\" class=\"fdPartsConfig\" value=\"\"><span class=\"modelAppSpaceTempPrevieRowsItemBtn\"><i></i>"+modelingLang["modelingAppSpace.styleAddPart"]+"</span><span class='portletUl'></span>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <div class=\"modelAppSpaceTempPrevieRows\">\n" +
                "                            <div class=\"modelAppSpaceTempPrevieRowsItem\" style=\"height: 240px;\" data-flex=\"1\">\n" +
                "                                <input type=\"hidden\" class=\"fdPartsConfig\" value=\"\"><span class=\"modelAppSpaceTempPrevieRowsItemBtn\"><i></i>"+modelingLang["modelingAppSpace.styleAddPart"]+"</span><span class='portletUl'></span>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                        <div class=\"modelAppSpaceTempPrevieBtnRows\">\n" +
                "                            <div class=\"modelAppSpaceTempPrevieRowsAddContainer\">\n" +
                "                                "+modelingLang["modelingAppSpace.styleAddContainer"]+"\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                    <div class=\"modelAppSpaceTempPreviewType\" data-template=\"07\">\n" +
                "                        <div class=\"modelAppSpaceTempPrevieRows\">\n" +
                "                        </div>\n" +
                "                        <div class=\"modelAppSpaceTempPrevieBtnRows\">\n" +
                "                            <div class=\"modelAppSpaceTempPrevieRowsAddContainer\">\n" +
                "                                "+modelingLang["modelingAppSpace.styleAddContainer"]+"\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </div></div><div class=\"modelAppSpaceMask\"></div>\n" +
                "    <div class=\"modelAppSpaceContainerSettingPop\">\n" +
                "        <div class=\"modelAppSpaceContainerSettingPopHeader\">\n" +
                "            <p>"+modelingLang["modelingAppSpace.styleContainerSettings"]+"</p>\n" +
                "            <i></i>\n" +
                "        </div>\n" +
                "        <div class=\"modelAppSpaceContainerSettingPopContent\">\n" +
                "            <div class=\"modelAppSpaceStyleInlineRow\">\n" +
                "                <div class=\"modelAppSpaceContainerSettingPopTitle\">\n" +
                "                    "+modelingLang["modelingAppSpace.styleContainerColumns"]+"\n" +
                "                </div>\n" +
                "                <div class=\"modelAppSpaceContainerSettingRows muiPerformanceDropdownBox\">\n" +
                "                    <span data-rows-value=\"1\">"+modelingLang["modelingAppSpace.styleColumn1"]+"</span>\n" +
                "                    <i class=\"muiPerformanceDropdownBoxIcon\"></i>\n" +
                "                    <div class=\"muiPerformanceDropdownBoxList\">\n" +
                "                        <ul>\n" +
                "                            <li data-rows=\"1\">"+modelingLang["modelingAppSpace.styleColumn1"]+"</li>\n" +
                "                            <li data-rows=\"2\">"+modelingLang["modelingAppSpace.styleColumn2"]+"</li>\n" +
                "                            <li data-rows=\"3\">"+modelingLang["modelingAppSpace.styleColumn3"]+"</li>\n" +
                "                            <li data-rows=\"4\">"+modelingLang["modelingAppSpace.styleColumn4"]+"</li>\n" +
                "                            <li data-rows=\"5\">"+modelingLang["modelingAppSpace.styleColumn5"]+"</li>\n" +
                "                        </ul>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div class=\"modelAppSpaceStyleInlineRow\">\n" +
                "                <div class=\"modelAppSpaceContainerSettingPopTitle\">\n" +
                "                    "+modelingLang["modelingAppSpace.styleWidthRatio"]+"\n" +
                "                </div>\n" +
                "                <div class=\"modelAppSpaceContainerSettingRatioContent\">\n" +
                "                    <div class=\"modelAppSpaceContainerSettingRatioItem\">\n" +
                "                        <input type=\"text\" oninput=\"value=value.replace(/[^\\d]/g,'')\" value=\"1\">\n" +
                "                    </div>\n" +
                "                    <div class=\"modelAppSpaceContainerSettingRatioItem\">\n" +
                "                        <span>:</span>\n" +
                "                        <input type=\"text\" oninput=\"value=value.replace(/[^\\d]/g,'')\" value=\"1\">\n" +
                "                    </div>\n" +
                "                    <div class=\"modelAppSpaceContainerSettingRatioItem\">\n" +
                "                        <span>:</span>\n" +
                "                        <input type=\"text\" oninput=\"value=value.replace(/[^\\d]/g,'')\" value=\"1\">\n" +
                "                    </div>\n" +
                "                    <div class=\"modelAppSpaceContainerSettingRatioItem\">\n" +
                "                        <span>:</span>\n" +
                "                        <input type=\"text\" oninput=\"value=value.replace(/[^\\d]/g,'')\" value=\"1\">\n" +
                "                    </div>\n" +
                "                    <div class=\"modelAppSpaceContainerSettingRatioItem\">\n" +
                "                        <span>:</span>\n" +
                "                        <input type=\"text\" oninput=\"value=value.replace(/[^\\d]/g,'')\" value=\"1\">\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div class=\"modelAppSpaceContainerSettingPopFooter\"></div>\n" +
                "    </div>";
            return html;
        }
    });

    exports.IncStyleGenerator = IncStyleGenerator;
})