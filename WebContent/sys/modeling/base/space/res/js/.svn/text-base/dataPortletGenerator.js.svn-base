/**
 * 业务空间-数据部件-门户类组件
 */
define(function(require, exports, module) {
    var $ = require('lui/jquery'),
        base = require('lui/base');
    var topic = require("lui/topic");
    var dialog = require("lui/dialog");
    var modelingLang = require("lang!sys-modeling-base");

    var VIEWHTMLTEMP = "<div class=\"modelAppSpacePorletsListRow\">\n" +
        "                <div class=\"modelAppSpacePorletsTitleType\">\n" +
        "                    "+modelingLang["modelingAppSpace.data.source"]+"\n" +
        "                </div>\n" +
        "            </div>\n" +
        "            <div class=\"muiPerformanceDropdownBox select_model\">\n" +
        "                <input type=\"hidden\" name=\"fdApplicationId\">\n" +
        "                <input type=\"hidden\" name=\"fdModelId\">\n" +
        "                <div class=\"item-content-element-choose\">\n" +
        "                    <p class=\"item-content-element-label\"></p>\n" +
        "                    <i></i>\n" +
        "                </div>\n" +
        "            </div>\n" +
        "            <div class=\"modelAppSpacePorletsListRow\">\n" +
        "                <div class=\"modelAppSpacePorletsTitleType\">\n" +
        "                    "+modelingLang["table.modelingPortletCfg"]+"\n" +
        "                </div>\n" +
        "            </div>\n" +
        "            <div class=\"muiPerformanceDropdownBox\">\n" +
        "                <input type=\"text\" placeholder=\""+modelingLang["modeling.page.choose"]+"\" readonly=\"true\">\n" +
        "                <input type=\"hidden\" name=\"viewId\" value=\"\">\n" +
        "                <input type=\"hidden\" name=\"viewName\" value=\"\">\n" +
        "                <input type=\"hidden\" name=\"format\" value=\"\">\n" +
        "                <input type=\"hidden\" name=\"portletType\" value=\"\">\n" +
        "                <input type=\"hidden\" name=\"ref\" value=\"\">\n" +
        "                <i class=\"muiPerformanceDropdownBoxIcon\"></i>\n" +
        "                <div class=\"muiPerformanceDropdownBoxList\" style=\"display: none;\">\n" +
        "                </div>\n" +
        "            </div>" +
        "            <div class=\"modelAppSpacePorletsInlineRow\">\n" +
        "               <div class=\"modelAppSpacePorletsTitleType\">\n" +
        "                   "+modelingLang["modelingAppSpace.documents.size"]+"\n" +
        "                </div>\n" +
        "            </div>" +
        "            <div class=\"rowSize\" style=\"margin-bottom: 8px\">\n" +
        "               <input type=\"text\" name=\"rowSize\" value='6' class=\"modelAppSpacePorletsInput\">\n" +
        "            </div>" +
        "            <div class=\"modelAppSpacePorletsInlineRow picturePositionClass\" style='display: none'>\n" +
        "               <div class=\"modelAppSpacePorletsTitleType\">\n" +
        "                   "+modelingLang["modelingAppSpace.picture.location"]+"\n" +
        "               </div>\n" +
        "               <div class=\"modelAppSpacePorletPicturePosition\">\n" +
        "                   <label><input type=\"radio\" name=\"varPosition\" value=\"0\" checked=\"\">"+modelingLang["modelingAppSpace.position.left"]+"</label>\n" +
        "                   <label><input type=\"radio\" name=\"varPosition\" value=\"1\">"+modelingLang["modelingAppSpace.position.right"]+"</label>\n" +
        "               </div>\n" +
        "           </div>" +
        "           <div class='modelAppSpacePorletPictureGrid' style='display: none'>" +
        "               <div class=\"modelAppSpacePorletsInlineRow\">\n" +
        "                   <div class=\"modelAppSpacePorletsTitleType\">\n" +
        "                       "+modelingLang["modelingAppSpace.resolving.power"]+"\n" +
        "                    </div>\n" +
        "               </div>" +
        "               <div class=\"modelAppSpacePorletVarSize\">\n" +
        "                   <input type=\"text\" name=\"varSizeWidth\" value='100' class=\"modelAppSpaceVarSizeInput\">\n" +
        "                   <span>*</span>" +
        "                   <input type=\"text\" name=\"varSizeHeight\" value='100' class=\"modelAppSpaceVarSizeInput\">\n" +
        "               </div>" +
        "               <div class=\"modelAppSpacePorletsInlineRow\">\n" +
        "                   <div class=\"modelAppSpacePorletsTitleType\">\n" +
        "                       "+modelingLang["modelingAppSpace.columns.size"]+"\n" +
        "                    </div>\n" +
        "               </div>" +
        "               <div style=\"margin-bottom: 8px\">\n" +
        "                   <input type=\"text\" name=\"varColumn\" value='5' class=\"modelAppSpacePorletsInput\">\n" +
        "               </div>" +
        "           </div>";

    var DataPortletGenerator = base.DataView.extend({

        initProps: function ($super, cfg) {
            $super(cfg);
            this.config = cfg;
            this.parent = cfg.parent;
            this.element = cfg.container;
            this.displayType = cfg.displayType;
            this.storeData = cfg.viewPorletCfg;
        },
        startup : function($super, cfg) {
            $super(cfg);
        },
        doRender: function($super, cfg){
            $super(cfg);
        },
        draw : function($super, cfg){
            this.element.append($(VIEWHTMLTEMP));
            if(this.storeData){
                this.initByStoreData(this.storeData);
            }
            //数据源
            this.selectModelEvent();
            //下拉框
            this.addSelectEvent();
            //校验
            this.checkEvent();
        },
        addSelectEvent : function(){
            var self = this;
            this.element.find('.muiPerformanceDropdownBox').click(function(e){
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
            this.element.find('.muiHrArchivesUploadAccessoryText>p').mouseenter(function(){
                $(this).parent('.muiHrArchivesUploadAccessoryText').append("<div class='muiHrArchivesUploadAccessoryHover'></div>")
                var txt=$(this).text();
                $(this).siblings('.muiHrArchivesUploadAccessoryHover').text(txt).show();
            }).mouseleave(function(){
                $(this).siblings('.muiHrArchivesUploadAccessoryHover').remove();
            })
        },
        selectModelEvent : function(){
            var self = this;
            var curAppId = this.ApplicationId || "";
            this.element.find(".select_model").find(".item-content-element-choose").click(function () {
                var resetTabSource = function(rtn){
                    $(this).closest(".modelAppSpacePorletsTypeContent").find(".muiPerformanceDropdownBox").find("input[type='text']").text("");
                    if(rtn && rtn.data){
                        var names = [];
                        var appInfo = rtn.data["1"];
                        names.push(appInfo.name);
                        self.element.find("[name*='fdApplicationId']").val(appInfo.id);
                        curAppId = appInfo.id;
                        var modelInfo = rtn.data["2"];
                        names.push(modelInfo.name);
                        var $modelDom =  self.element.find("[name*='fdModelId']");
                        $modelDom.val(modelInfo.id);
                        self.element.find(".select_model .item-content-element-label").html(names.join(" / "));
                        self.getViewOption(appInfo.id,modelInfo.id);
                    }
                }
                var params = [{
                    "index" : "1",
                    "text" : modelingLang['table.modelingApplication'],
                    "sourceUrl" : "/sys/modeling/base/modelingApplication.do?method=getAllAppInfos&c.eq.fdValid=true",
                    "renderSrc" : "/sys/modeling/base/resources/js/dialog/step/appRender.html",
                    "curAppId" : curAppId || "",
                    "viewWgt" : "AppView"
                }, "modelViewInfo"];
                self.openStepDialog(params, resetTabSource);
            })
        },
        getViewOption: function(appId,modelId){
            var self = this;
            var url = Com_Parameter.ContextPath+"sys/modeling/base/modelingAppSpace.do?method=findViewInfos&fdApplicationId=" + appId +"&fdModelId="+modelId+"&viewType="+this.displayType;
            $.ajax({
                url: url,
                type: "get",
                async: false,
                cache: false,
                success: function (data) {
                    self.renderSelectOption(data);
                }
            });
        },
        renderSelectOption: function(data){
            var self = this;
            var html = "<ul>";
            if(data.length == 0){
                html += "<li>"+modelingLang["modelingAppSpace.data.source.tips"]+"</li>";
                self.changeContainerBack();
            }
            for(var i = 0;i < data.length; i++){
                var obj = data[i];
                html += "<li value='"+obj.fdId +"' data-format-select='"+obj.format+"' data-portletType-select='"+obj.portletType+"' data-ref-select='"+obj.ref+"'>"+obj.fdName+"</li>";
            }
            html += "</ul>";
            this.element.find(".muiPerformanceDropdownBoxList").html(html);
            this.element.find(".muiPerformanceDropdownBox input[type='text']").val("");
            self.element.find('.muiPerformanceDropdownBoxList>ul>li').off("click").on("click",function(e){
                e?e.stopPropagation():event.cancelBubble = true;
                $(this).parents('.muiPerformanceDropdownBox').children('span').text($(this).text());
                $(this).parents('.muiPerformanceDropdownBox').children("input[type='text']").val($(this).text());
                $(this).parents('.muiPerformanceDropdownBox').find('.muiPerformanceDropdownBoxList').hide();
                $(this).parents('.muiPerformanceDropdownBox').removeClass('active');
                $(this).parents('.muiPerformanceDropdownBox').find("[name='viewId']").val($(this).attr("value"));
                $(this).parents('.muiPerformanceDropdownBox').find("[name='viewName']").val($(this).text());
                $(this).parents('.muiPerformanceDropdownBox').find("[name='format']").val($(this).attr("data-format-select"));
                $(this).parents('.muiPerformanceDropdownBox').find("[name='portletType']").val($(this).attr("data-portletType-select"));
                $(this).parents('.muiPerformanceDropdownBox').find("[name='ref']").val($(this).attr("data-ref-select"));
                var portletType = $(this).attr("data-portletType-select");
                if(portletType == "2"){ //图文摘要
                    $(".picturePositionClass").show();
                    $(".modelAppSpacePorletPictureGrid").hide();
                }else if(portletType == "3"){ //图片宫格
                    $(".modelAppSpacePorletPictureGrid").show();
                    $(".picturePositionClass").hide();
                }else{ //其他
                    $(".picturePositionClass").hide();
                    $(".modelAppSpacePorletPictureGrid").hide();
                }
                self.changeContainerBack(portletType);
            });
        },
        openStepDialog : function(viewInfos, cb){
            viewInfos = viewInfos || ["appViewInfo","modelViewInfo"];
            var url = "/sys/modeling/base/resources/js/dialog/step/stepDialog.jsp";
            dialog.iframe(url,modelingLang['behavior.select'],function(rtn){
                if(cb){
                    cb(rtn);
                }
            },{
                width:900,
                height:500,
                close:true,
                params : {
                    viewInfos : viewInfos
                }
            });
        },
        checkEvent : function(){
            var self = this;
            //文档数校验
            this.element.find("[name='rowSize']").on("blur",function(){
                self.rowSizeCheckEvent(this);
            });
            //分辨率校验
            this.element.find("[name='varSizeWidth']").on("blur",function(){
                self.varSizeWidthAndHeightCheckEvent(this);
            });
            this.element.find("[name='varSizeHeight']").on("blur",function(){
                self.varSizeWidthAndHeightCheckEvent(this);
            });
            //列数校验
            this.element.find("[name='varColumn']").on("blur",function(){
                self.varColumnCheckEvent(this);
            });
        },
        rowSizeCheckEvent : function(e){
            $(e).closest("div").find(".validation-container").remove();
            var rowSize = $(e).val();
            if (rowSize){
                if(!/^[1-9]+[0-9]*]*$/.test(rowSize)) {
                    $(e).closest("div").append($("<div class='validation-container'><div class=\"validation-advice\" _reminder=\"true\">" +
                        "<table class=\"validation-table\"><tbody><tr><td>" +
                        "<div class=\"lui_icon_s lui_icon_s_icon_validator\"></div></td>" +
                        "<td class=\"validation-advice-msg\"><span class=\"validation-advice-title\">"+modelingLang['modelingAppSpace.documents.size.tips']+"</span>" +
                        "</td></tr></tbody></table></div></div>"));
                    return false;
                }
            }
            return true;
        },
        varSizeWidthAndHeightCheckEvent : function(e){
            $(e).closest(".modelAppSpacePorletVarSize").find(".validation-container").remove();
            var varSizeWidth = this.element.find("[name='varSizeWidth']").val();
            var varSizeHeight = this.element.find("[name='varSizeHeight']").val();
            if (varSizeWidth && varSizeHeight){
                if(!/^[1-9]+[0-9]*]*$/.test(varSizeWidth) || !/^[1-9]+[0-9]*]*$/.test(varSizeHeight)) {
                    $(e).closest(".modelAppSpacePorletVarSize").append($("<div class='validation-container'><div class=\"validation-advice\" _reminder=\"true\">" +
                        "<table class=\"validation-table\"><tbody><tr><td>" +
                        "<div class=\"lui_icon_s lui_icon_s_icon_validator\"></div></td>" +
                        "<td class=\"validation-advice-msg\"><span class=\"validation-advice-title\">"+modelingLang['modelingAppSpace.resolving.power.tips']+"</span>" +
                        "</td></tr></tbody></table></div></div>"));
                    return false;
                }
            }
            return true;
        },
        varColumnCheckEvent : function(e){
            $(e).closest("div").find(".validation-container").remove();
            var varColumn = $(e).val();
            if (varColumn){
                if(!/^[1-9]+[0-9]*]*$/.test(varColumn)) {
                    $(e).closest("div").append($("<div class='validation-container'><div class=\"validation-advice\" _reminder=\"true\">" +
                        "<table class=\"validation-table\"><tbody><tr><td>" +
                        "<div class=\"lui_icon_s lui_icon_s_icon_validator\"></div></td>" +
                        "<td class=\"validation-advice-msg\"><span class=\"validation-advice-title\">"+modelingLang['modelingAppSpace.columns.size.tips']+"</span>" +
                        "</td></tr></tbody></table></div></div>"));
                    return false;
                }
            }
            return true;
        },
        submitChecked : function(){
            var self = this;
            var isPass = true;
            var rowSizeCheck = this.rowSizeCheckEvent("[name='rowSize']");
            var varSizeWidthCheck = this.varSizeWidthAndHeightCheckEvent("[name='varSizeWidth']");
            var varColumnCheck = this.varColumnCheckEvent("[name='varColumn']");
            if(rowSizeCheck == false || varSizeWidthCheck == false || varColumnCheck == false){
                isPass = false;
            }
            return isPass;
        },
        getKeyData : function(){
            var keyData = {};
            keyData.fdApplicationId = this.element.find("[name='fdApplicationId']").val();
            keyData.fdModelId = this.element.find("[name='fdModelId']").val();
            keyData.fdModelName = this.element.find(".select_model .item-content-element-label").text();
            keyData.value = this.element.find("[name='viewId']").val();
            keyData.name = this.element.find("[name='viewName']").val();
            keyData.format = this.element.find("[name='format']").val();
            keyData.portletType = this.element.find("[name='portletType']").val();
            keyData.ref = this.element.find("[name='ref']").val();
            keyData.rowSize = this.element.find("[name='rowSize']").val() || "6";
            keyData.varPosition= this.element.find("[name='varPosition']:checked").val();
            keyData.varSizeWidth = this.element.find("[name='varSizeWidth']").val() || "100";
            keyData.varSizeHeight = this.element.find("[name='varSizeHeight']").val() || "100";
            keyData.varColumn = this.element.find("[name='varColumn']").val() || "5";
            return keyData;
        },
        initByStoreData : function(viewPorletCfg){
            if(JSON.stringify(viewPorletCfg) == "{}"){
                return;
            }
           this.ApplicationId = viewPorletCfg.fdApplicationId;
           this.element.find(".select_model .item-content-element-label").text(viewPorletCfg.fdModelName);
           this.element.find("[name='fdApplicationId']").val(viewPorletCfg.fdApplicationId);
           this.element.find("[name='fdModelId']").val(viewPorletCfg.fdModelId);
           this.getViewOption(viewPorletCfg.fdApplicationId,viewPorletCfg.fdModelId);
           this.element.find(".muiPerformanceDropdownBox input[type='text']").val(viewPorletCfg.name);
           this.element.find("[name='viewId']").val(viewPorletCfg.value);
           this.element.find("[name='viewName']").val(viewPorletCfg.name);
           this.element.find("[name='format']").val(viewPorletCfg.format);
           this.element.find("[name='portletType']").val(viewPorletCfg.portletType);
           //不同的部件类型显示不同的元素配置
            if(viewPorletCfg.portletType == "2"){ //图文摘要
                $(".picturePositionClass").show();
                $(".modelAppSpacePorletPictureGrid").hide();
            }else if(viewPorletCfg.portletType == "3"){ //图片宫格
                $(".modelAppSpacePorletPictureGrid").show();
                $(".picturePositionClass").hide();
            }else{ //其他
                $(".picturePositionClass").hide();
                $(".modelAppSpacePorletPictureGrid").hide();
            }
           this.element.find("[name='ref']").val(viewPorletCfg.ref);
           this.element.find("[name='rowSize']").val(viewPorletCfg.rowSize);
           this.element.find("[name='varSizeWidth']").val(viewPorletCfg.varSizeWidth);
           this.element.find("[name='varSizeHeight']").val(viewPorletCfg.varSizeHeight);
           this.element.find("[name='varColumn']").val(viewPorletCfg.varColumn);
           if(viewPorletCfg.varPosition == "0"){
               this.element.find("[name='varPosition'][value='0']").attr("checked",true);
           }else{
               this.element.find("[name='varPosition'][value='1']").attr("checked",true);
           }
        },
        changeContainerBack : function(portletType){
            var self = this;
            //获取当前被选中的容器
            var rowsItem = "";
            var rowsItemWidth = "";
            var rowsItemHeight = "";
            $(".modelAppSpaceTempPrevieRowsItem").each(function(){
                if($(this).hasClass("active")){
                    rowsItem =$(this);
                    rowsItemWidth = getComputedStyle(this).width;
                    rowsItemWidth = rowsItemWidth.substring(0,rowsItemWidth.length - 2);
                    rowsItemHeight = getComputedStyle(this).height;
                    rowsItemHeight = rowsItemHeight.substring(0,rowsItemHeight.length - 2);
                }
            });
            if(rowsItem){
                if (portletType) {
                    if(portletType == "1"){ //简单列表
                        if(rowsItemWidth < 104 || rowsItemHeight < 32 ){ //小尺寸
                            rowsItem.css("background","url('../base/space/res/images/simplelist-small.png') no-repeat center");
                        }else{
                            rowsItem.css("background","url('../base/space/res/images/simplelist-big.png') no-repeat center");
                        }
                    }else if(portletType == "2"){ //图文摘要
                        if(rowsItemWidth < 104 || rowsItemHeight < 60 ){ //小尺寸
                            rowsItem.css("background","url('../base/space/res/images/imgAndText-small.png') no-repeat center");
                        }else{
                            rowsItem.css("background","url('../base/space/res/images/imgAndText-big.png') no-repeat center");
                        }
                    }else if(portletType == "3"){ //图片宫格
                        if(rowsItemWidth < 70|| rowsItemHeight < 78 ){ //小尺寸
                            rowsItem.css("background","url('../base/space/res/images/picturegrid-small.png') no-repeat center");
                        }else{
                            rowsItem.css("background","url('../base/space/res/images/picturegrid-big.png') no-repeat center");
                        }
                    }else if(portletType == "4"){ //幻灯片
                        if(rowsItemWidth < 70 || rowsItemHeight < 70 ){ //小尺寸
                            rowsItem.css("background","url('../base/space/res/images/pictureslide-small.png') no-repeat center");
                        }else{
                            rowsItem.css("background","url('../base/space/res/images/pictureslide-big.png') no-repeat center");
                        }
                    }else if(portletType == "5"){ //列表视图
                        if(rowsItemWidth < 104 || rowsItemHeight < 60 ){ //小尺寸
                            rowsItem.css("background","url('../base/space/res/images/listview-small.png') no-repeat center");
                        }else{
                            rowsItem.css("background","url('../base/space/res/images/listview-big.png') no-repeat center");
                        }
                    }else if(portletType == "6"){ //时间轴列表
                        if(rowsItemWidth < 104 || rowsItemHeight < 60 ){ //小尺寸
                            rowsItem.css("background","url('../base/space/res/images/timeaxis-small.png') no-repeat center");
                        }else{
                            rowsItem.css("background","url('../base/space/res/images/timeaxis-big.png') no-repeat center");
                        }
                    }
                    rowsItem.css("background-size","contain");
                } else {
                    rowsItem.css("background", "url('../base/space/res/images/no-portlet.png') no-repeat center");
                    rowsItem.css("background-size", "contain");
                }
            }
        }
    });

    exports.DataPortletGenerator = DataPortletGenerator;
})