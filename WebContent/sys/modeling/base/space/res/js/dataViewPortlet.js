/**
 * 业务空间-数据部件-视图类组件
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
        "                    "+modelingLang["sysModelingOperation.fdView"]+"\n" +
        "                </div>\n" +
        "            </div>\n" +
        "            <div class=\"muiPerformanceDropdownBox\">\n" +
        "                <input type=\"text\" placeholder=\""+modelingLang["modeling.page.choose"]+"\" readonly=\"true\">\n" +
        "                <input type=\"hidden\" name=\"viewId\" value=\"\">\n" +
        "                <input type=\"hidden\" name=\"viewName\" value=\"\">\n" +
        "                <i class=\"muiPerformanceDropdownBoxIcon\"></i>\n" +
        "                <div class=\"muiPerformanceDropdownBoxList\" style=\"display: none;\">\n" +
        "                </div>\n" +
        "            </div>";

    var DataViewPortlet = base.DataView.extend({

        initProps: function ($super, cfg) {
            $super(cfg);
            this.config = cfg;
            this.parent = cfg.parent;
            this.element = cfg.container;
            this.displayType = cfg.displayType;
            this.storeData = cfg.viewStoreData;
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
            }
            for(var i = 0;i < data.length; i++){
                var obj = data[i];
                html += "<li value='"+obj.fdId +"'>"+obj.fdName+"</li>";
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
        submitChecked : function(){
            return true;
        },
        getKeyData : function(){
            var keyData = {};
            keyData.fdApplicationId = this.element.find("[name='fdApplicationId']").val();
            keyData.fdModelId = this.element.find("[name='fdModelId']").val();
            keyData.fdModelName = this.element.find(".select_model .item-content-element-label").text();
            keyData.value = this.element.find("[name='viewId']").val();
            keyData.name = this.element.find("[name='viewName']").val();
            return keyData;
        },
        initByStoreData : function(fdViewConfig){
            if(JSON.stringify(fdViewConfig) == "{}"){
                return;
            }
           this.ApplicationId = fdViewConfig.fdApplicationId;
           this.element.find(".select_model .item-content-element-label").text(fdViewConfig.fdModelName);
           this.element.find("[name='fdApplicationId']").val(fdViewConfig.fdApplicationId);
           this.element.find("[name='fdModelId']").val(fdViewConfig.fdModelId);
           this.getViewOption(fdViewConfig.fdApplicationId,fdViewConfig.fdModelId);
           this.element.find(".muiPerformanceDropdownBox input[type='text']").val(fdViewConfig.name);
           this.element.find("[name='viewId']").val(fdViewConfig.value);
           this.element.find("[name='viewName']").val(fdViewConfig.name);
        }
    });

    exports.DataViewPortlet = DataViewPortlet;
})