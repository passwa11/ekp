/**
 * 业务空间-数据部件组件
 */
define(function(require, exports, module) {
    var $ = require('lui/jquery'),
        base = require('lui/base'),
        source = require('lui/data/source'),
        statisticsRuleSettingWhere = require('sys/modeling/base/mobile/resources/js/statisticsRuleSettingWhere'),
        dataViewPortlet = require('sys/modeling/base/space/res/js/dataViewPortlet'),
        dataPortletGenerator= require('sys/modeling/base/space/res/js/dataPortletGenerator');
    render = require('lui/view/render');
    var topic = require("lui/topic");
    var dialog = require("lui/dialog");
    var modelingLang = require("lang!sys-modeling-base");

    var pointCountErrTip = '<div class="validation-advice" id="pointCountErrTip" _reminder="true">' +
        '<table class="validation-table">' +
        '<tbody>' +
        '<tr>' +
        '<td><div class="lui_icon_s lui_icon_s_icon_validator"></div></td>' +
        '<td class="validation-advice-msg"><span class="validation-advice-title">'+modelingLang['listview.top.statistics.show.point.count.errTip']+'</span></td>' +
        '</tr>' +
        '</tbody>' +
        '</table>' +
        '</div>';

    var DataPortlet = base.DataView.extend({
        initProps: function ($super, cfg) {
            $super(cfg);
            this.config = cfg;
            this.container = cfg.container;
            this.dataPortlet = cfg.parent.dataPortlet;
            this.statisticsRuleSettingWhereWgts = [];
        },
        startup : function($super, cfg) {
           this.setRender(new render.Template({
                src : "/sys/modeling/base/space/dataViewRender.html#",
                parent : this
            }));
            this.render.startup();
            $super(cfg);
        },
        doRender: function($super, cfg){
            $super(cfg);
            this.element.appendTo(this.container).addClass("panel-tab-main-view");
            //画颜色选择器
            this.drawColorSelect();
            this.addEvent();
            //初始化
            if(this.dataPortlet){
                this.initByStoreData(this.dataPortlet);
            }
        },
        draw : function($super, cfg){
            this.render.get({});
        },
        //获取多语言资源
        getModelingLang :function (){
            return modelingLang;
        },
        numberRuleSettingEvent : function(){
           var self = this;
           //设置按钮点击事件
           this.element.find(".statistics_rule_button").off("click").on("click",function () {
                var $statisticsItem =  $(this).parent('.item-content');
                var x = $statisticsItem.offset().top;
                var y = $statisticsItem.offset().left;
                if(0 < $statisticsItem.find('.modelAppSpacePorletsListDataSettingPop').length){
                    var $openRuleSetting = $statisticsItem.find('.modelAppSpacePorletsListDataSettingPop');
                    if ($openRuleSetting.css("display") == "block") {
                        $openRuleSetting.css("display","none");
                    }else{
                        $openRuleSetting.css({
                            "top": x - 145,
                            "left": y - 550
                        });
                        $openRuleSetting.attr("scrollTop",$('.model-edit-view-content').scrollTop());
                        $openRuleSetting.attr("top", x - 145);
                        //根据字段的类型，渲染执行方式下拉框
                        self.renderWhereOper();
                        $openRuleSetting.css("display","block");
                    }
                }
            });
            //绑定右边内容区滚动事件，滚动时查询框跟随上下移动
            $('.model-edit-view-content').unbind('scroll').bind('scroll', function(){
                var scrollTop = $(this).scrollTop();
                var $openRuleSetting = $(".modelAppSpacePorletsListDataSettingPop");
                if(0<$openRuleSetting.length){
                    $openRuleSetting.each(function () {
                        var firstScrollTop =  $(this).attr("scrollTop");
                        var top = $(this).attr("top");
                        $(this).css({
                            "top": top - scrollTop + Number(firstScrollTop),
                        });
                    })
                }
                var $openRuleSettingToRight = $(".open_rule_setting_to_right");
                if(0<$openRuleSettingToRight.length){
                    $openRuleSettingToRight.each(function () {
                        var firstScrollTop =  $(this).attr("scrollTop");
                        var top = $(this).attr("top");
                        $(this).css({
                            "top": top - scrollTop + Number(firstScrollTop),
                        });
                    })
                }
            })
            //查询条件规则下拉框
            self.element.find('.numberWhere .muiPerformanceDropdownBoxList>ul>li').off("click").on("click",function(e){
                e?e.stopPropagation():event.cancelBubble = true;
                self.selectShowText(this);
                var value = $(this).find("[type='hidden']").val();
                $(this).parents('.muiPerformanceDropdownBox').find("[name='whereRule']").val(value);
            });
            //查询条件新增
            var $whereContent = this.element.find(".open_rule_setting_where_content");
            self.element.find(".open_rule_setting_where_create").off("click").on("click",function(){
                var $whereRule = $("<div class='open_rule_setting_where_rule'></div>");
                var rowIndex = $whereContent.find(".open_rule_setting_where_rule").length+1;
                $whereRule.attr("index", rowIndex - 1);
                var statisticsRuleSettingWhereWgt = new statisticsRuleSettingWhere({container: $whereRule,storeData:{},modelDict: self.numberModelDict || {},spaceType:"space",parent:self});
                statisticsRuleSettingWhereWgt.draw();
                self.statisticsRuleSettingWhereWgts.push(statisticsRuleSettingWhereWgt);
                $whereContent.append($whereRule);
            });

            //取消
           this.element.find(".modelAppSpaceContainerSettingPopBtnCancel").off("click").on("click",function () {
                self.element.find(".modelAppSpacePorletsListDataSettingPop").css("display","none");
           })
            //确定
           this.element.find(".modelAppSpaceContainerSettingPopBtnConfirm").off("click").on("click",function () {
               self.element.find(".modelAppSpacePorletsListDataSettingPop").css("display","none");
           })
        },
        renderWhereOper : function(){
            var self = this;
            this.element.find(".whereOper .muiPerformanceDropdownBoxList>ul").html("<li><span>"+modelingLang['modelingAppSpace.set.count']+"</span></li>");
            var fieldType = this.element.find("[name='fdFieldType']").val();
            if(fieldType == "Double" || fieldType == "Integer" || fieldType == "BigDecimal"){
                var html = "<li><span>"+modelingLang['listview.data.sum']+"</span><input type='hidden' value='1'></li>";
                html += "<li><span>"+modelingLang['listview.data.average']+"</span><input type='hidden' value='2'></li>";
                html += "<li><span>"+modelingLang['listview.find.the.maximum']+"</span><input type='hidden' value='4'></li>";
                html += "<li><span>"+modelingLang['listview.find.the.minimum']+"</span><input type='hidden' value='3'></li>";
                this.element.find(".whereOper .muiPerformanceDropdownBoxList>ul>li").after(html);
                //查询条件执行操作下拉框
                self.element.find('.whereOper .muiPerformanceDropdownBoxList>ul>li').off("click").on("click",function(e){
                    e?e.stopPropagation():event.cancelBubble = true;
                    self.selectShowText(this);
                    var value = $(this).find("[type='hidden']").val();
                    $(this).parents('.muiPerformanceDropdownBox').find("[name='whereOper']").val(value);
                });
            }
        },
        drawColorSelect : function(){
            $(".title_color_div").attr("data-color-mark-id","titleColorSelectValue");
            $(".fill_color_div").attr("data-color-mark-id","fillColorSelectValue");
            if (window.SpectrumColorPicker) {
                window.SpectrumColorPicker.init("titleColorSelectValue");
                window.SpectrumColorPicker.init("fillColorSelectValue");

                window.SpectrumColorPicker.setColor("titleColorSelectValue","#000000");
                window.SpectrumColorPicker.setColor("fillColorSelectValue","#FFFFFF");

               /* seajs.use(['lui/jquery','lui/topic'],function($,topic) {
                    topic.publish("modeling.SpectrumColorPicker.init");
                })*/
            }
        },
        addEvent : function(){
          var self = this;
          //标题
          this.addPortletTitleEvent();
          //背景
          this.changeBackgroundEvent();
          //图片下拉列表
          this.selectDropdown();
          //选择图片
          var $selectImg=$(".modelAppSpacePorletsUploadBtn");
          var $checkedImg=$(".modelAppSpacePorletsDataUploadedBtn");
          $selectImg.off("click").on("click",function(){
             self.selectMaterial();
          });
          $checkedImg.off("click").on("click",function(){
             self.selectMaterial();
          });
          // 控制上传图片,和删除
          this.dataUploadMask();
          //显示类型
          this.changeDisplayTypeEvent();
          //下拉框
          this.selectEvent();
          //数字-数据源
          this.numberModel();
            // 切换部件
          this.changeProtlet();
            //开启链接
            this.openLinkEvent();
            //数字-小数
            this.numberNumUnit();
        },
        numberNumUnit : function(){
            var self = this;
            var $showPointCountErrTip = $(pointCountErrTip);
            this.element.find("[name='pointCount']").on("blur",function () {
                var pointCount = Number($(this).val());
                if (isNaN(pointCount)){
                    $showPointCountErrTip.css("display","");
                    return;
                }else if(pointCount < 0 || pointCount >8){
                    $showPointCountErrTip.css("display","");
                    return;
                }
                $showPointCountErrTip.css("display","none");
            })
            this.element.find("[name='pointCount']").bind("keyup",function() {
                var pointCount = Number($(this).val());
                if (isNaN(pointCount)){
                    $showPointCountErrTip.css("display","");
                    return;
                }else if(pointCount < 0 || pointCount >8){
                    $showPointCountErrTip.css("display","");
                    return;
                }
                $showPointCountErrTip.css("display","none");
            })
            self.element.find("[name='pointCount']").closest("div").after($showPointCountErrTip);
            this.element.find("[name='pointCount']").trigger("blur");
        },
        openLinkEvent : function(){
            var self = this;
            this.element.find("input[type='checkbox'][name='enableLink']").off("click").on("click",function(){
                var isOpen = $(this).is(':checked');
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
            });
            this.element.find("[name='fdLink']").on("blur",function(){
                self.linkCheckEvent(this);
            })
            this.element.find("[name='fdDisplayHeight']").on('input propertychange', function(){
                self.setLeftShow();
            });
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
        changeProtlet : function(){
            var self = this;
            $(".modelAppSpaceIcon.modelAppSpacePorletsTitleIcon").off("click").on("click", function(e){
                $(".modelAppSpaceTempTextOptionList").remove();
                if($(this).parent().find(".changePorlet").find(".modelAppSpaceTempTextOptionList").length==0){
                    $(this).parent().find(".changePorlet").append('<ul class="modelAppSpaceTempTextOptionList"><li data-change-select="0">'+modelingLang["modelingAppSpace.styleTextPart"]+'</li><li data-change-select="1">'+modelingLang["modelingAppSpace.stylePictureParts"]+'</li></ul>');
                    //选择部件点击事件
                    $(".modelAppSpaceTempTextOptionList li").click(function(){
                        var fdType = $(this).attr("data-change-select");
                        var part = {};
                        LUI("incStyleGenerator").changePortletRight(fdType,part);
                    });
                }
            });
        },
        numberModel : function(){
          var self = this;
            var curAppId = this.ApplicationId || "";
            this.element.find(".numberSelectModel").find(".item-content-element-choose").click(function () {
                var resetTabSource = function(rtn){
                    if(rtn && rtn.data){
                        var names = [];
                        var appInfo = rtn.data["1"];
                        names.push(appInfo.name);
                        self.element.find("[name*='fdNumberApplicationId']").val(appInfo.id);
                        curAppId = appInfo.id;
                        var modelInfo = rtn.data["2"];
                        names.push(modelInfo.name);
                        var $modelDom =  self.element.find("[name*='fdNumberModelId']");
                        $modelDom.val(modelInfo.id);
                        self.element.find(".numberSelectModel .item-content-element-label").html(names.join(" / "));
                        self.getFieldOption(appInfo.id,modelInfo.id);
                        self.element.find(".statistics_rule_button").addClass("statistics_rule_button_enable");
                        //数字查询规则
                        self.numberRuleSettingEvent();
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
                $(this).closest(".modelAppSpacePorletsTypeContent").find(".muiPerformanceDropdownBox").find("input[type='text']").text("");
            })
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
        getFieldOption : function(appId,modelId){
            var self = this;
            var url = Com_Parameter.ContextPath+"sys/modeling/base/modelingAppSpace.do?method=findFieldInfos&fdApplicationId=" + appId +"&fdModelId="+modelId;
            $.ajax({
                url: url,
                type: "get",
                async: false,
                cache: false,
                success: function (data) {
                    self.numberModelDict = data;
                    self.renderNumberSelectOption(data);
                }
            });
        },
        renderNumberSelectOption: function(data){
            var self = this;
            this.element.find(".numberField").html("");
            var html = "<ul>";
            for(var i = 0;i < data.length; i++){
                var obj = data[i];
                if(obj.isSubTableField == "true"){
                    //数字统计过滤明细表字段
                    continue;
                }
                html += "<li value='"+obj.field +"'><span>"+obj.fieldText+"</span><input type='hidden' name='numberFieldType' value='"+obj.fieldType+"'></li>";
            }
            html += "</ul>";
            this.element.find(".numberField").html(html);
            this.element.find(".number .muiPerformanceDropdownBox input[type='text']").val("");
            self.element.find('.number .muiPerformanceDropdownBoxList>ul>li').off("click").on("click",function(e){
                e?e.stopPropagation():event.cancelBubble = true;
                self.selectShowText(this);
                $(this).parents('.muiPerformanceDropdownBox').find("[name='fdFieldId']").val($(this).attr("value"));
                $(this).parents('.muiPerformanceDropdownBox').find("[name='fdFieldType']").val($(this).find("[name='numberFieldType']").val());
            });
            //数字显示类型单位下拉框列表点击事件
            self.element.find('.numUtilValueBox .muiPerformanceDropdownBoxList>ul>li').on("click",function(e){
                e?e.stopPropagation():event.cancelBubble = true;
                var value = $(this).find("[type='hidden']").val();
                $(this).parents('.numUtilValueBox').find("[name='numUnitVal']").val(value);
            });
        },
        selectEvent: function(){
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
            //图表类型下拉框列表点击事件
            self.element.find('.modelAppSpacePorletsChartType .muiPerformanceDropdownBoxList>ul>li').off("click").on("click",function(e){
                e?e.stopPropagation():event.cancelBubble = true;
                self.selectShowText(this);
                var value = $(this).find("[type='hidden']").val();
                $(this).parents('.muiPerformanceDropdownBox').find("[name='nodeType']").val(value);
                self.doRenderChartOption(value);
            });

            //图片居中填满等下拉框列表点击事件
            self.element.find('.modelAppSpacePorletsInlineRow .muiPerformanceDropdownBoxList>ul>li').off("click").on("click",function(e){
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
        doRenderChartOption : function(value){
            var chartType = "";
            switch (value) {
                case "0":
                    //自定义数据
                    chartType = "custom";
                    break;
                case "1":
                    //统计图表
                    chartType = "chart";
                    break;
                case "2":
                    //统计列表
                    chartType = "table";
                    break;
                case "3":
                    //统计图表集
                    chartType = "chartset";
                    break;
            }
            this.getChartViewOption(chartType)
        },
        getChartViewOption: function(chartType){
            var self = this;
            var appId = listviewOption.fdApplicationId;        //jsp页面全局变量
            var url = Com_Parameter.ContextPath+"sys/modeling/base/modelingAppSpace.do?method=findViewInfos&fdApplicationId=" + appId +"&fdModelId="+""+"&viewType="+chartType;
            $.ajax({
                url: url,
                type: "get",
                async: false,
                cache: false,
                success: function (data) {
                    self.renderChartSelectOption(data);
                }
            });
        },
        renderChartSelectOption : function(data){
            var self = this;
            var html = "<ul>";
            if(data.length == 0){
                html += "<li>"+modelingLang['modelingAppSpace.data.source.tips']+"</li>";
            }
            for(var i = 0;i < data.length; i++){
                var obj = data[i];
                html += "<li value='"+obj.value +"'>"+obj.text+"</li>";
            }
            html += "</ul>";
            self.element.find(".modelingSpaceChartData .muiPerformanceDropdownBoxList").html(html);
            self.element.find(".modelingSpaceChartData input[type='text']").val("");
            //图表数据点击事件
            self.element.find('.modelingSpaceChartData .muiPerformanceDropdownBoxList>ul>li').off("click").on("click",function(e){
                e?e.stopPropagation():event.cancelBubble = true;
                self.selectShowText(this);
                $(this).parents('.muiPerformanceDropdownBox').find("[name='chartId']").val($(this).attr("value"));
                // $(this).parents('.muiPerformanceDropdownBox').find("[name='chartName']").val($(this).text());
            });
        },
        changeDisplayTypeEvent : function(){
            var self = this;
            var displayType = self.fdDisplayType ||  "1";
            this.element.find(".modelAppSpacePorletsDisplayListUl>li").click(function(fdViewConfig){
                if(!$(this).hasClass("active")){
                    $(this).addClass("active").siblings().removeClass("active")
                }
                if($(this).hasClass("modelAppSpacePorletsDisplayItemNum")){
                    $(".modelAppSpacePorletsTypeContent").hide();
                    $(".modelAppSpacePorletsDefaultContent").show();
                    $(".isOverflowDomByPortlet").hide();
                }else if($(this).hasClass("modelAppSpacePorletsDisplayItemChart")){
                    $(".modelAppSpacePorletsTypeContent").hide();
                    $(".modelAppSpacePorletsChartContent").show();
                    $(".isOverflowDomByPortlet").hide();
                }else if($(this).hasClass("modelAppSpacePorletsDisplayItemPorlets")){
                    self.element.find(".modelAppSpacePorletsNumContent").empty();
                    //门户部件形式用组件的形式画
                    var viewPorletCfg = {};
                    //门户显示类型
                    var type = $(this).find("input[type=hidden]").val();
                    if(self.fdDisplayType == type){
                        viewPorletCfg = self.viewPorletCfg;
                    }
                    self.dataPortletGeneratorWgt =new dataPortletGenerator.DataPortletGenerator({parent:self,
                        container:self.element.find(".modelAppSpacePorletsNumContent"),
                        displayType : type,
                        viewPorletCfg:viewPorletCfg});
                    self.dataPortletGeneratorWgt.startup();
                    self.dataPortletGeneratorWgt.draw();
                    $(".modelAppSpacePorletsTypeContent").hide();
                    $(".modelAppSpacePorletsNumContent").show();
                    $(".isOverflowDomByPortlet").show();
                    $(".isOverflowDomByPortlet input").prop("checked", true);
                }else{
                    self.element.find(".modelAppSpacePorletsNumContent").empty();
                    //视图类型，共用一块模板，用组件的形式画
                    var type = $(this).find("input[type=hidden]").val();
                    var viewStoreData = {};
                    if(self.fdDisplayType == type){
                        viewStoreData = self.viewStoreData;
                    }
                    self.dataViewPortletWgt = new dataViewPortlet.DataViewPortlet({parent:self,
                            container:self.element.find(".modelAppSpacePorletsNumContent"),
                        displayType : type,
                        viewStoreData:viewStoreData});
                    self.dataViewPortletWgt.startup();
                    self.dataViewPortletWgt.draw();
                    $(".modelAppSpacePorletsTypeContent").hide();
                    $(".modelAppSpacePorletsNumContent").show();
                    $(".isOverflowDomByPortlet").hide();
                }
                displayType = $(this).find("input[type=hidden]").val();
                self.element.find("[name='display_type']").val(displayType);
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
                    //切换显示类型时更换左侧容器占位图
                    switch(displayType){
                        case "1":
                            //列表视图
                            rowsItem.css("background","url('../base/space/res/images/pure-number.png') no-repeat center");
                            rowsItem.css("background-size","contain");
                            break;
                        case "2":
                            //列表视图
                            rowsItem.css("background","url('../base/space/res/images/chart-zw.png') no-repeat center");
                            rowsItem.css("background-size","contain");
                            break;
                        case "3":
                            //列表视图
                            rowsItem.css("background","url('../base/space/res/images/list-zw.png') no-repeat center");
                            rowsItem.css("background-size","contain");
                            break;
                        case "4":
                            //日历视图
                            rowsItem.css("background","url('../base/space/res/images/calendar-zw.png') no-repeat center");
                            rowsItem.css("background-size","contain");
                            break;
                        case "5":
                            //甘特视图
                            rowsItem.css("background","url('../base/space/res/images/gantt-chart.png') no-repeat center");
                            rowsItem.css("background-size","contain");
                            break;
                        case "6":
                            //资源面板视图
                            rowsItem.css("background","url('../base/space/res/images/resource-panel.png') no-repeat center");
                            rowsItem.css("background-size","contain");
                            break;
                        case "7":
                            //门户部件
                            var dataPortletGeneratorData = {};
                            if(self.dataPortletGeneratorWgt) {
                                dataPortletGeneratorData = self.dataPortletGeneratorWgt.getKeyData();
                                if (dataPortletGeneratorData.portletType) {
                                    if(dataPortletGeneratorData.portletType == "1"){ //简单列表
                                        if(rowsItemWidth < 104 || rowsItemHeight < 32 ){ //小尺寸
                                            rowsItem.css("background","url('../base/space/res/images/simplelist-small.png') no-repeat center");
                                        }else{
                                            rowsItem.css("background","url('../base/space/res/images/simplelist-big.png') no-repeat center");
                                        }
                                    }else if(dataPortletGeneratorData.portletType == "2"){ //图文摘要
                                        if(rowsItemWidth < 104 || rowsItemHeight < 60 ){ //小尺寸
                                            rowsItem.css("background","url('../base/space/res/images/imgAndText-small.png') no-repeat center");
                                        }else{
                                            rowsItem.css("background","url('../base/space/res/images/imgAndText-big.png') no-repeat center");
                                        }
                                    }else if(dataPortletGeneratorData.portletType == "3"){ //图片宫格
                                        if(rowsItemWidth < 70|| rowsItemHeight < 78 ){ //小尺寸
                                            rowsItem.css("background","url('../base/space/res/images/picturegrid-small.png') no-repeat center");
                                        }else{
                                            rowsItem.css("background","url('../base/space/res/images/picturegrid-big.png') no-repeat center");
                                        }
                                    }else if(dataPortletGeneratorData.portletType == "4"){ //幻灯片
                                        if(rowsItemWidth < 70 || rowsItemHeight < 70 ){ //小尺寸
                                            rowsItem.css("background","url('../base/space/res/images/pictureslide-small.png') no-repeat center");
                                        }else{
                                            rowsItem.css("background","url('../base/space/res/images/pictureslide-big.png') no-repeat center");
                                        }
                                    }else if(dataPortletGeneratorData.portletType == "5"){ //列表视图
                                        if(rowsItemWidth < 104 || rowsItemHeight < 60 ){ //小尺寸
                                            rowsItem.css("background","url('../base/space/res/images/listview-small.png') no-repeat center");
                                        }else{
                                            rowsItem.css("background","url('../base/space/res/images/listview-big.png') no-repeat center");
                                        }
                                    }else if(dataPortletGeneratorData.portletType == "6"){ //时间轴列表
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
                            break;
                        default:
                            break;
                    }
                }
            });
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
            });
        },
        selectDropdown : function(){
            var self = this;
            // 下拉列表
            $('.muiPerformanceDropdownBoxData').click(function(e){
                e?e.stopPropagation():event.cancelBubble = true;
                if($(this).find('.muiPerformanceDropdownBoxListData').css('display') == 'none'&&$(this).hasClass('active')==false){
                    $('.muiPerformanceDropdownBoxData').removeClass('active');
                    $(this).addClass('active');
                    $('.muiPerformanceDropdownBoxListData').hide();
                    $(this).find('.muiPerformanceDropdownBoxListData').show();
                }else{
                    $(this).removeClass('active');
                    $(this).find('.muiPerformanceDropdownBoxListData').hide();
                }
            });
            $('.muiPerformanceDropdownBoxListData>ul>li').click(function(e){
                e?e.stopPropagation():event.cancelBubble = true;
                $(this).parents('.muiPerformanceDropdownBoxData').children('span').text($(this).text());
                $(this).parents('.muiPerformanceDropdownBoxData').children('input').val($(this).text());
                $(this).parents('.muiPerformanceDropdownBoxData').find('.muiPerformanceDropdownBoxListData').hide();
                $(this).parents('.muiPerformanceDropdownBoxData').removeClass('active');
            });
            // 下拉列表点击外部或者按下ESC后列表隐藏
            $(document).click(function(){
                $('.muiPerformanceDropdownBoxListData').hide();
                $(this).find('.watermark_configuration_list_pop').hide();
                $('.muiPerformanceDropdownBoxData').removeClass('active');
                $(".watermark_configuration_alpha_bar,.watermark_configuration_scale_box,.watermark_configuration_alpha_bar").addClass("hide");
            }).keyup(function(e){
                var key =  e.which || e.keyCode;;
                if(key == 27){
                    $('.muiPerformanceDropdownBoxListData').hide();
                    $(this).find('.watermark_configuration_list_pop').hide();
                    $('.muiPerformanceDropdownBoxData').removeClass('active');
                }
            });
            //选择图片填充方式
            $('.muiPerformanceDropdownBoxData  ul>li').click(function(e){
                var filltyle = $(this).attr("data-data-select");
                $(this).parents('.muiPerformanceDropdownBoxData').children('input').val(filltyle);
            });
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
                        $(".modelAppSpacePorletsUploadBtn").hide();
                        $(".modelAppSpacePorletsCheckedImginfo").show();
                        $(".modelAppSpacePorletsCheckedImginfo img").attr("src",url);
                        $(".modelAppSpacePorletsUploadBtnBox input").val(fdAttrId);
                    }
                }
            }).show();
        },
        dataUploadMask : function(){
            $(".modelAppSpacePorletsDataUploadedBtn").mouseenter(function(){
                if($(this).find(".modelAppSpacePorletsDataUploadMask").is(':hidden')){
                    $(this).find(".modelAppSpacePorletsDataUploadMask").show();
                }
            }).mouseleave(function(){
                $(this).find(".modelAppSpacePorletsDataUploadMask").hide();
            });
            $('.modelAppSpacePorletsDataUploadedDel').click(function(e){
                e?e.stopPropagation():event.cancelBubble = true;
                $(".modelAppSpacePorletsUploadBtn").show();
                $(".modelAppSpacePorletsCheckedImginfo").hide();
                $(".modelAppSpacePorletsCheckedImginfo img").attr("src","");
                $(".modelAppSpacePorletsUploadBtnBox input").val("");
            });
        },
        addPortletTitleEvent :function(){
            var self = this;
            this.element.find(".modelAppSpacePorletsTitleNameContent>input").on('input propertychange', function(){
                self.element.find(".modelAppSpacePorletsTitleNameLength").text($(this).val().length);
            });
        },
        saveWhereRule : function(){
            var self = this;
            var whereRuleArr = [];
            for(var i = 0;i < self.statisticsRuleSettingWhereWgts.length; i++){
                var wgt = self.statisticsRuleSettingWhereWgts[i];
                var obj = wgt.getKeyData();
                whereRuleArr.push(obj);
            }
            return whereRuleArr;
        },
        submitChecked : function(){
            var self = this;
            //链接校验
            var linkUrl = this.linkCheckEvent("[name='fdLink']");
            if(linkUrl == false){
                return false;
            }
            //小数位数校验
            this.element.find("[name='pointCount']").trigger("blur");
            var pointCount = Number(this.element.find("[name='pointCount']").val());
            var portletCheck = true;
            if(this.dataPortletGeneratorWgt){
                portletCheck = this.dataPortletGeneratorWgt.submitChecked();
            }
            if (isNaN(pointCount) || pointCount < 0 || pointCount >8 || portletCheck == false){
                return false;
            }
            return true;
        },
        getKeyData : function(){
            var keyData = {};
            keyData.fdTitle =  this.element.find("input[name='fdTitle']").val();
            keyData.fdTitleColor =  this.element.find(".title_color_div").find("input[type='hidden']").val();
            keyData.fdFillColor =  this.element.find(".fill_color_div").find("input[type='hidden']").val();
            keyData.fdBackgroundType =  this.element.find("[name='PorletsBg']:checked").val();
            keyData.fdImgBackground = {};
            keyData.fdImgBackground.fdUrlId=this.element.find(".modelAppSpacePorletsUploadBtnBox").find("input").val();
            keyData.fdImgBackground.fdFillStyle=$(".muiPerformanceDropdownBoxData").children('input').val();
            keyData.fdDisplayType =  this.element.find("[name='display_type']").val() || "1";
            keyData.fdChartConfig =  {};
            keyData.fdChartConfig.nodeType = this.element.find("[name='nodeType']").val();
            keyData.fdChartConfig.nodeTypeName = this.element.find("[name='nodeTypeName']").val();
            keyData.fdChartConfig.value = this.element.find("[name='chartId']").val();
            keyData.fdChartConfig.name = this.element.find("[name='chartName']").val();
            if(this.dataViewPortletWgt){
                keyData.fdViewConfig =  this.dataViewPortletWgt.getKeyData();
            }
            if(this.dataPortletGeneratorWgt){
                keyData.fdPortletConfig =  this.dataPortletGeneratorWgt.getKeyData();
            }
            keyData.fdNumberConfig = {};
            keyData.fdNumberConfig.fdApplicationId = this.element.find("[name='fdNumberApplicationId']").val();
            keyData.fdNumberConfig.fdModelId = this.element.find("[name='fdNumberModelId']").val();
            keyData.fdNumberConfig.fdModelName = this.element.find(".numberSelectModel .item-content-element-label").text();
            keyData.fdNumberConfig.fdFieldId = this.element.find("[name='fdFieldId']").val();
            keyData.fdNumberConfig.fdFieldName = this.element.find(".number .muiPerformanceDropdownBox input[type='text']").val();
            keyData.fdNumberConfig.fdFieldType = this.element.find("[name='fdFieldType']").val();
            keyData.fdNumberConfig.whereType = this.element.find("[name='whereRule']").val() || "0";
            keyData.fdNumberConfig.whereTypeName = this.element.find(".numberWhere input[type='text']").val();
            keyData.fdNumberConfig.whereOperName = this.element.find(".whereOper input[type='text']").val();
            keyData.fdNumberConfig.whereOper = this.element.find("[name='whereOper']").val() || "0";
            keyData.fdNumberConfig.statisticsRule = this.saveWhereRule();
            keyData.fdNumberConfig.numUnitValName = this.element.find(".numUtilValueBox input[type='text']").val();
            keyData.fdNumberConfig.numUnitVal = this.element.find("[name='numUnitVal']").val();
            keyData.fdNumberConfig.pointCount = this.element.find("[name='pointCount']").val();
            keyData.fdNumberConfig.isShowUnit = this.element.find("input[type='checkbox'][name='isShowUnit']").is(':checked');
            keyData.fdNumberConfig.thousandsChecked = this.element.find("input[type='checkbox'][name='thousandsChecked']").is(':checked');

            keyData.fdDisplayHeight = this.element.find("[name='fdDisplayHeight']").val()+"px";
            if(this.element.find("[name='display_type']").val() == "7"){
                keyData.fdOverstep = this.element.find("input[type='checkbox'][name='fdOverstep']").is(':checked');
            }
            return keyData;
        },
        reDrawNumberWhere : function(statisticsRule){
            var self = this;
            var $whereContent = this.element.find(".open_rule_setting_where_content");
            for(var i=0;i<statisticsRule.length;i++){
                var $whereRule = $("<div class='open_rule_setting_where_rule'></div>");
                var rowIndex = i+1;
                $whereRule.attr("index", rowIndex - 1);
                var statisticsRuleSettingWhereWgt = new statisticsRuleSettingWhere({container: $whereRule,storeData:statisticsRule[i],modelDict: self.numberModelDict || {},spaceType:"space",parent:self});
                statisticsRuleSettingWhereWgt.draw();
                this.statisticsRuleSettingWhereWgts.push(statisticsRuleSettingWhereWgt);
                $whereContent.append($whereRule);
            }
        },
        initByStoreData : function(storeData){
            if(JSON.stringify(storeData) == "{}"){
                return;
            }
            this.element.find("input[name='fdTitle']").val(storeData.fdTitle);
            this.element.find(".modelAppSpacePorletsTitleNameContent>input").trigger('propertychange');
            window.SpectrumColorPicker.setColor("titleColorSelectValue",storeData.fdTitleColor);
            if(storeData.fdBackgroundType == "0"){
                //背景颜色
                $(".modelAppSpacePorletsBgOnColor").show();
                $(".modelAppSpacePorletsBgOnPicture").hide();
                this.element.find("[name='PorletsBg'][value='0']").attr("checked",true);
                window.SpectrumColorPicker.setColor("fillColorSelectValue",storeData.fdFillColor);
            }else{
                $(".modelAppSpacePorletsBgOnColor").hide();
                $(".modelAppSpacePorletsBgOnPicture").show();
                this.element.find("[name='PorletsBg'][value='1']").attr("checked",true);
                var fdImgBackground=storeData.fdImgBackground;
                //背景图片
                if(fdImgBackground.fdUrlId){
                    var url =  Com_Parameter.ContextPath + "sys/attachment/sys_att_main/sysAttMain.do?method=download&open=1&fdId="+fdImgBackground.fdUrlId;
                    $(".modelAppSpacePorletsUploadBtn").hide();
                    $(".modelAppSpacePorletsCheckedImginfo").show();
                    $(".modelAppSpacePorletsCheckedImginfo img").attr("src",url);
                    $(".modelAppSpacePorletsUploadBtnBox input").val(fdImgBackground.fdUrlId);
                }else{
                    $(".modelAppSpacePorletsUploadBtn").show();
                    $(".modelAppSpacePorletsCheckedImginfo").hide();
                }
                //图片填充形式
                var fdFillStyle=fdImgBackground.fdFillStyle;
                var selectText=this.element.find("[data-data-select='"+fdFillStyle+"']").text();
                this.element.find('.muiPerformanceDropdownBoxData').children('span').text(selectText);
                this.element.find('.muiPerformanceDropdownBoxData').children('input').val(fdFillStyle);
            }

            this.element.find("[name='display_type']").val(storeData.fdDisplayType);
            if(storeData.fdDisplayType == "1"){
                //数字
                this.element.find(".modelAppSpacePorletsDisplayList .modelAppSpacePorletsDisplayItemNum").trigger("click");
                var fdNumberConfig = storeData.fdNumberConfig;
                this.element.find(".numberSelectModel .item-content-element-label").text(fdNumberConfig.fdModelName);
                this.element.find("[name='fdNumberApplicationId']").val(fdNumberConfig.fdApplicationId);
                this.element.find("[name='fdNumberModelId']").val(fdNumberConfig.fdModelId);
                this.element.find("[name='fdFieldId']").val(fdNumberConfig.fdFieldId);
                this.getFieldOption(fdNumberConfig.fdApplicationId,fdNumberConfig.fdModelId);
                //数字查询规则
                if(fdNumberConfig.fdModelId){
                    this.element.find(".statistics_rule_button").addClass("statistics_rule_button_enable");
                }
                this.numberRuleSettingEvent();
                this.element.find(".number .numberFieldBox input[type='text']").val(fdNumberConfig.fdFieldName);
                this.element.find("[name='fdFieldType']").val(fdNumberConfig.fdFieldType);
                this.element.find("[name='whereRule']").val(fdNumberConfig.whereType);
                this.element.find("[name='whereOper']").val(fdNumberConfig.whereOper);
                this.element.find(".numberWhere input[type='text']").val(fdNumberConfig.whereTypeName);
                this.element.find(".whereOper input[type='text']").val(fdNumberConfig.whereOperName);
                this.reDrawNumberWhere(fdNumberConfig.statisticsRule);
                this.element.find(".numUtilValueBox input[type='text']").val(fdNumberConfig.numUnitValName);
                this.element.find("[name='numUnitVal']").val(fdNumberConfig.numUnitVal);
                this.element.find("[name='pointCount']").val(fdNumberConfig.pointCount);
                this.element.find("[name='pointCount']").trigger("blur");
                if(fdNumberConfig.isShowUnit){
                    this.element.find("[name='isShowUnit']").prop("checked",true);
                }else{
                    this.element.find("[name='isShowUnit']").prop("checked",false);
                }
                if(fdNumberConfig.thousandsChecked){
                    this.element.find("[name='thousandsChecked']").prop("checked",true);
                }else{
                    this.element.find("[name='thousandsChecked']").prop("checked",false);
                }

            }else if(storeData.fdDisplayType == "2"){
                //图表
                this.element.find(".modelAppSpacePorletsDisplayList .modelAppSpacePorletsDisplayItemChart").trigger("click");
                var fdChartConfig = storeData.fdChartConfig;
                this.element.find("[name='nodeType']").val(fdChartConfig.nodeType);
                this.element.find("[name='nodeTypeName']").val(fdChartConfig.nodeTypeName);
                this.element.find("[name='chartId']").val(fdChartConfig.value);
                this.element.find("[name='chartName']").val(fdChartConfig.name);
            }else {
                //其余视图类
                this.viewStoreData = storeData.fdViewConfig;
                this.fdDisplayType = storeData.fdDisplayType;
                this.viewPorletCfg = storeData.fdPortletConfig;
                switch(storeData.fdDisplayType){
                    case "3":
                        //列表视图
                        this.element.find(".modelAppSpacePorletsDisplayList .modelAppSpacePorletsDisplayItemList").trigger("click");
                        break;
                    case "4":
                        //日历视图
                        this.element.find(".modelAppSpacePorletsDisplayList .modelAppSpacePorletsDisplayItemCalendar").trigger("click");
                        break;
                    case "5":
                        //甘特视图
                        this.element.find(".modelAppSpacePorletsDisplayList .modelAppSpacePorletsDisplayItemGantt").trigger("click");
                        break;
                    case "6":
                        //资源面板视图
                        this.element.find(".modelAppSpacePorletsDisplayList .modelAppSpacePorletsDisplayItemResource").trigger("click");
                        break;
                    case "7":
                        //门户视图
                        this.element.find(".modelAppSpacePorletsDisplayList .modelAppSpacePorletsDisplayItemPorlets").trigger("click");
                        break;
                    default:
                        break;
                }
            }
            this.element.find("[name='fdDisplayHeight']").val(storeData.fdDisplayHeight.split("px")[0]);
            if(storeData.fdOverstep){
                this.element.find("[name='fdOverstep']").prop("checked",true);
            }else{
                this.element.find("[name='fdOverstep']").prop("checked",false);
            }
        },
        //实现左右互联
        setLeftShow : function() {
            LUI("incStyleGenerator").toSetDataPortletStyle(this.getKeyData().fdDisplayHeight,this.getKeyData().fdPortletConfig.portletType);
        }
    });

    exports.DataPortlet = DataPortlet;
})