/**
 * 移动列表视图的名称输入控件
 */

define(function(require, exports, module) {

    var $ = require('lui/jquery'),
        topic = require('lui/topic'),
        statisticsRuleSettingWhere = require('sys/modeling/base/mobile/resources/js/statisticsRuleSettingWhere'),
        base = require('lui/base');
    var modelingLang = require("lang!sys-modeling-base");

    var cusUnitErrTip = '<div class="validation-advice" id="cusUnitErrTip" _reminder="true">' +
                            '<table class="validation-table">' +
                                '<tbody>' +
                                    '<tr>' +
                                        '<td><div class="lui_icon_s lui_icon_s_icon_validator"></div></td>' +
                                        '<td class="validation-advice-msg"><span class="validation-advice-title">'+modelingLang['listview.top.statistics.show.cus.unit.errTip']+'</span></td>' +
                                    '</tr>' +
                                '</tbody>' +
                            '</table>' +
                        '</div>';
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
    var StatisticsRuleSetting = base.Component.extend({

        initProps: function($super, cfg) {
            $super(cfg);
            this.randomName = "fd_" + parseInt(((new Date().getTime()+Math.random())*10000)).toString(16);
            this.container = cfg.container || null;
            this.storeData = cfg.storeData || {};
            this.field = cfg.field;
            if(cfg.channel){
                this.channel = cfg.channel;
            }else{
                //默认为pc
                this.channel = 'pc';
            }

            this.modelDict = cfg.modelDict;
            this.statisticsRuleSettingWhereWgts = [];
            this.oldData = {};
            topic.channel(this.channel).subscribe("statisticsRuleSettingWhereWgts.delete",this.deleteStatisticsRuleSettingWhere, this);
        },

        draw : function($super, cfg){
            $super(cfg);
            var self = this;
            //先判断统计字段是否时数字类型，若不是，统计类型只有计数一种
            var fieldObject ;
            for(var i=0;i < this.modelDict.length;i++){
                if(this.field == this.modelDict[i].field){
                    fieldObject = this.modelDict[i];
                }
            }
            if(!fieldObject){
                return;
            }

            var $statisticsItem =  this.container;

            var $openRuleSetting = $("<div class='open_rule_setting'></div>");
            var x = $statisticsItem.offset().top;
            var y = $statisticsItem.offset().left;
            $openRuleSetting.css({
                "top": x - 200,
                "left": y - 548
            });
            var $openRuleSettingToRight = $("<div class='open_rule_setting_to_right'></div>");
            $openRuleSettingToRight.css({
                "top": x + 8,
                "left": y - 10
            });

            //标题
            var $openRuleSettingTitle = $("<div class='open_rule_setting_title' title="+fieldObject.fieldText+">"+modelingLang['listview.statistics.field']+":"+fieldObject.fieldText+"</div>");

            $openRuleSetting.append($openRuleSettingTitle);
            //数据范围类型
            var $whereType = $("<div class='open_rule_setting_where_type'></div>");
            var $whereTypeLeft = $("<div class='open_rule_setting_where_type_left'>"+modelingLang['listview.meet.following']+"</div>");
            var $whereTypeSelect = $("<div class='open_rule_setting_where_type_select'><select " +
                " class='inputsgl' style='width:100%'><option value='0'>"+modelingLang['listview.all']+"</option><option value='1'>"+modelingLang['listview.any']+"</option></select></div>");
            if(this.storeData.whereType){
                $whereTypeSelect.find('select').val(this.storeData.whereType)
            }
            var $whereTypeRight = $("<div class='open_rule_setting_where_type_right'>"+modelingLang['listview.condition']+"</div>");
            $whereType.append($whereTypeLeft);
            $whereType.append($whereTypeSelect);
            $whereType.append($whereTypeRight);

            $openRuleSetting.append($whereType);
            //数据规则
            var $whereContent = $("<div class='open_rule_setting_where_content'></div>");
            $openRuleSetting.append($whereContent);
            if(self.storeData.statisticsRule && self.storeData.statisticsRule.length){
                for (var i = 0; i < self.storeData.statisticsRule.length; i++) {
                    var $whereRule = $("<div class='open_rule_setting_where_rule'></div>");
                    var rowIndex = $whereContent.find(".open_rule_setting_where_rule").length+1;
                    $whereRule.attr("index", rowIndex - 1);
                    var statisticsRuleSettingWhereWgt = new statisticsRuleSettingWhere({parent:self,container: $whereRule,storeData:self.storeData.statisticsRule[i] });
                    statisticsRuleSettingWhereWgt.draw();
                    self.statisticsRuleSettingWhereWgts.push(statisticsRuleSettingWhereWgt);
                    $whereContent.append($whereRule);
                }
            }

            var $whereCreate = $("<div class='open_rule_setting_where_create'><span>"+modelingLang['button.add']+"</span></div>");
            $whereCreate.on("click",function () {
                var $whereRule = $("<div class='open_rule_setting_where_rule'></div>");
                var rowIndex = $whereContent.find(".open_rule_setting_where_rule").length+1;
                $whereRule.attr("index", rowIndex - 1);
                var statisticsRuleSettingWhereWgt = new statisticsRuleSettingWhere({parent:self,container: $whereRule,storeData:{} });
                statisticsRuleSettingWhereWgt.draw();
                self.statisticsRuleSettingWhereWgts.push(statisticsRuleSettingWhereWgt);
                $whereContent.append($whereRule);
            });
            $openRuleSetting.append($whereCreate);

            //执行动作标题
            var $actionTitle = $("<div class='open_rule_setting_action_title'>"+modelingLang['listview.do.following']+"</div>");

            $openRuleSetting.append($actionTitle);
            //执行动作下拉框
            var $actionType = $("<div class='open_rule_setting_action_type'></div>");
            var $actionTypeSelectDiv = $("<div class='open_rule_setting_action_type_select'></div>");
            var $actionTypeSelect = $("<select class='inputsgl' style='width:100%'></select>");
            $actionTypeSelect.append($("<option value='0'>"+modelingLang['listview.count']+"</option>"))
            if(fieldObject.fieldType == "Double" || fieldObject.fieldType == "BigDecimal" ){
                $actionTypeSelect.append($("<option value='1'>"+modelingLang['listview.data.sum']+"</option>"))
                $actionTypeSelect.append($("<option value='2'>"+modelingLang['listview.data.average']+"</option>"))
                $actionTypeSelect.append($("<option value='3'>"+modelingLang['listview.find.the.minimum']+"</option>"))
                $actionTypeSelect.append($("<option value='4'>"+modelingLang['listview.find.the.maximum']+"</option>"))
            }
            if(this.storeData.statisticsType){
                $actionTypeSelect.val(this.storeData.statisticsType);
            }
            $actionTypeSelectDiv.append($actionTypeSelect);
            $actionType.append($actionTypeSelectDiv);

            //统计模式  当前页/总计
            var $open_rule_setting_current_page = $("<div id='open_rule_setting_current_page' name='statisticsModelSet' value='1' popupobj='pagingset_1' " +
                "class='view_flag_radio_yes' style='display: inline-block;cursor: pointer; padding-left: 20px;'>"+
                "<i class='view_flag_no'></i> "+modelingLang['listview.current.page']+" </div>");
            $open_rule_setting_current_page.on("click",function () {
                $(self.container).find("[name=statisticsModelSet]").find("i").removeClass("view_flag_yes");
                $(this).find("i").addClass("view_flag_yes");
            })
            var $open_rule_setting_total = $("<div id='open_rule_setting_total' name='statisticsModelSet' value='2' popupobj='pagingset_1' " +
                "class='view_flag_radio_yes' style='display: inline-block;cursor: pointer; padding-left: 20px;'>"+
                "<i class='view_flag_no'></i> "+modelingLang['listview.total']+" </div>");
            $open_rule_setting_total.on("click",function () {
                $(self.container).find("[name=statisticsModelSet]").find("i").removeClass("view_flag_yes");
                $(this).find("i").addClass("view_flag_yes");
            })

            if(this.storeData.statisticsModel && this.storeData.statisticsModel == "2"){
                $open_rule_setting_total.find("i").addClass("view_flag_yes");
            }else{
                $open_rule_setting_current_page.find("i").addClass("view_flag_yes");
            }
            //暂时屏蔽移动端
            if("pc" == this.channel) {
                $actionType.append($open_rule_setting_current_page);
                $actionType.append($open_rule_setting_total);
            }
            $openRuleSetting.append($actionType);
                // 数据格式
                this.drawShowDom($openRuleSetting);

            //按钮
            var $footButtonDiv = $("<div class='open_rule_setting_foot_button'></div>");
            var $footButtonSure = $("<div class='open_rule_setting_foot_button_sure'>"+modelingLang['modeling.button.ok']+"</div>");
            $footButtonDiv.append($footButtonSure);
            $footButtonSure.on("click",function () {
                if("pc" == this.channel  &&  false == self.checkData()){
                    return;
                }
                self.updateOldData();
                $openRuleSetting.css("display","none");
            });
            var $footButtonCancel = $("<div class='open_rule_setting_foot_button_cancel'>"+modelingLang['modeling.Cancel']+"</div>");
            $footButtonCancel.on("click",function () {
                //将旧数据覆盖回滚
                self.rollBackOldData();
                $openRuleSetting.css("display","none");
            });
            $footButtonDiv.append($footButtonCancel);
            $openRuleSetting.append($footButtonDiv);

            $statisticsItem.append($openRuleSettingToRight);
            $statisticsItem.append($openRuleSetting);
                var $showTypeInput = $(this.container).find(".open_rule_setting_show_type").find("input");
                if ("1" == $showTypeInput.val()) {
                    this.updateShowPreviewContentWithNum();
                } else if ("2" == $showTypeInput.val()) {
                    this.updateShowPreviewContentWithPercent();
                }
                if ("1" == $showTypeInput.val()) {
                    $(this.container).find(".open_rule_setting_show_type_num").addClass("active");
                    $(this.container).find(".open_rule_setting_show_type_percent").removeClass("active");
                } else {
                    $(this.container).find(".open_rule_setting_show_num_unit").css("display", "none");
                    $(this.container).find(".open_rule_setting_show_cus_unit").css("display", "none");
                    $(this.container).find(".open_rule_setting_show_thousands").css("display", "none");
                    $(this.container).find(".open_rule_setting_show_type_num").removeClass("active");
                    $(this.container).find(".open_rule_setting_show_type_percent").addClass("active");
                }
            self.updateOldData();
        },



        drawShowDom : function ($openRuleSetting){
            var self = this;
            //数据格式
            var $showTitle = $("<div class='open_rule_setting_show_title'>"+modelingLang['listview.top.statistics.show.title']+"</div>");
            $openRuleSetting.append($showTitle);

            var $showContent = $("<div class='open_rule_setting_show_content'></div>");
            var showText = "999999";
            var $showPreview = $("<div class='open_rule_setting_show_preview'></div>");
            var $showPreviewTitle = $("<div class='open_rule_setting_show_preview_title'>"+modelingLang['listview.top.statistics.show.preview.title']+"</div>");
            var $showPreviewContent = $("<div class='open_rule_setting_show_preview_content'></div>");
            var $showPreviewContentNum = $("<div class='open_rule_setting_show_preview_content_num'>"+showText+"</div>");
            var $showPreviewContentUnit = $("<div class='open_rule_setting_show_preview_content_unit'></div>");
            $showPreviewContent.append($showPreviewContentNum);
            $showPreviewContent.append($showPreviewContentUnit);
            $showPreview.append($showPreviewTitle);
            $showPreview.append($showPreviewContent);
            $showContent.append($showPreview);

            //数据类型
            var $showType = $("<div class='open_rule_setting_show_type'></div>");
            var $showTypeInput = $("<input type='hidden' class='inputsgl' value='1' ></input>");
            var $showTypeNum = $("<div class='open_rule_setting_show_type_num'>"+modelingLang['listview.top.statistics.show.type.num']+"</div>");
            var $showTypePercent = $("<div class='open_rule_setting_show_type_percent'>"+modelingLang['listview.top.statistics.show.type.percent']+"</div>");
            $showTypeNum.on("click",function (){
                $(self.container).find(".open_rule_setting_show_num_unit").css("display","");
                $(self.container).find(".open_rule_setting_show_cus_unit").css("display","");
                $(self.container).find(".open_rule_setting_show_thousands").css("display","");
                $showTypeNum.addClass("active");
                $showTypePercent.removeClass("active");
                $showTypeInput.val("1");
                self.updateShowPreviewContentWithNum();
            });
            $showTypePercent.on("click",function (){
                $(self.container).find(".open_rule_setting_show_num_unit").css("display","none");
                $(self.container).find(".open_rule_setting_show_cus_unit").css("display","none");
                $(self.container).find(".open_rule_setting_show_thousands").css("display","none");
                $showTypeNum.removeClass("active");
                $showTypePercent.addClass("active");
                $showTypeInput.val("2");
                self.updateShowPreviewContentWithPercent();
            });
            if(this.storeData.showType){
                $showTypeInput.val(this.storeData.showType);
            }

            $showType.append($showTypeInput);
            $showType.append($showTypeNum);
            $showType.append($showTypePercent);
            $showContent.append($showType);
            //数量单位
            var $showNumUnitDiv = $("<div class='open_rule_setting_show_num_unit'>"+modelingLang['listview.top.statistics.show.num.unit']+"</div>");
            var $showNumUnitSelect = $("<select class='inputsgl'></select>");
            $showNumUnitSelect.append($("<option value='0'>"+modelingLang['listview.top.statistics.show.num.unit.null']+"</option>"))
            $showNumUnitSelect.append($("<option value='1'>"+modelingLang['listview.top.statistics.show.num.unit.thousand']+"</option>"))
            $showNumUnitSelect.append($("<option value='2'>"+modelingLang['listview.top.statistics.show.num.unit.tenThousand']+"</option>"))
            $showNumUnitSelect.append($("<option value='3'>"+modelingLang['listview.top.statistics.show.num.unit.million']+"</option>"))
            $showNumUnitSelect.append($("<option value='4'>"+modelingLang['listview.top.statistics.show.num.unit.hundredMillion']+"</option>"))
            $showNumUnitSelect.on("change",function (){
                self.updateShowPreviewContentWithNum();
            })
            if(this.storeData.numUnitVal){
                $showNumUnitSelect.val(this.storeData.numUnitVal);
            }
            $showNumUnitDiv.append($showNumUnitSelect);
            $showContent.append($showNumUnitDiv);
            //自定义单位
            var $showCusUnitDiv = $("<div class='open_rule_setting_show_cus_unit'>"+modelingLang['listview.top.statistics.show.cus.unit']+"</div>");
            var $showCusUnitInput = $("<input class='inputsgl' placeholder='"+modelingLang['listview.top.statistics.show.cus.unit.tip']+"' '></input>");
            var $showCusUnitErrTip = $(cusUnitErrTip);
            $showCusUnitErrTip.css("display","none");
            $showCusUnitInput.on("blur",function () {
                var val =  $(this).val();
                if(self.bytesLength(val) >24){
                    $showCusUnitErrTip.css("display","");
                    return;
                }else{
                    $showCusUnitErrTip.css("display","none");
                }
                self.updateShowPreviewContentWithNum();
            })
            $showCusUnitInput.bind("keyup",function() {
                var val =$(this).val();
                if(self.bytesLength(val) >24){
                    $showCusUnitErrTip.css("display","");
                    return;
                }else{
                    $showCusUnitErrTip.css("display","none");
                }
                self.updateShowPreviewContentWithNum();
            })
            if(this.storeData.cusUnit){
                $showCusUnitInput.val(this.storeData.cusUnit);
            }
            $showCusUnitDiv.append($showCusUnitInput);
            $showCusUnitDiv.append($showCusUnitErrTip);
            $showContent.append($showCusUnitDiv);

            //小数点位数
            var $showPointCountDiv = $("<div class='open_rule_setting_show_point_count'>"+modelingLang['listview.top.statistics.show.point.count']+"</div>");
            var $showPointCountInput = $("<input class='inputsgl' placeholder='"+modelingLang['listview.top.statistics.show.point.count.tip']+"'></input>");
            var $showPointCountErrTip = $(pointCountErrTip);
            $showPointCountErrTip.css("display","none");
            $showPointCountInput.on("blur",function () {
                var pointCount = Number($(this).val());
                if (isNaN(pointCount)){
                    $showPointCountErrTip.css("display","");
                    return;
                }else if(pointCount < 0 || pointCount >8){
                    $showPointCountErrTip.css("display","");
                    return;
                }
                $showPointCountErrTip.css("display","none");
                if("1" == $showTypeInput.val()){
                   self.updateShowPreviewContentWithNum();
                }else if("2" == $showTypeInput.val()){
                    self.updateShowPreviewContentWithPercent();
                }
            })
            $showPointCountInput.bind("keyup",function() {
                var pointCount = Number($(this).val());
                if (isNaN(pointCount)){
                    $showPointCountErrTip.css("display","");
                    return;
                }else if(pointCount < 0 || pointCount >8){
                    $showPointCountErrTip.css("display","");
                    return;
                }
                $showPointCountErrTip.css("display","none");
                if("1" == $showTypeInput.val()){
                    self.updateShowPreviewContentWithNum();
                }else if("2" == $showTypeInput.val()){
                    self.updateShowPreviewContentWithPercent();
                }
            })
            if(this.storeData.pointCount){
                $showPointCountInput.val(this.storeData.pointCount);
            }

            $showPointCountDiv.append($showPointCountInput);
            $showPointCountDiv.append($showPointCountErrTip);
            $showContent.append($showPointCountDiv);
            //千分符
            var $showThousandsDiv = $("<div class='open_rule_setting_show_thousands'>"+modelingLang['listview.top.statistics.show.thousands']+"</div>");
            var $showThousandsRadio = $("<input type='checkbox' class=''></input>");
            $showThousandsRadio.on("change",function () {
                self.updateShowPreviewContentWithNum();
            })
            if(this.storeData.thousandsChecked){
                $showThousandsRadio.prop('checked',this.storeData.thousandsChecked);
            }
            $showThousandsDiv.append($showThousandsRadio);
            $showContent.append($showThousandsDiv);

            $openRuleSetting.append($showContent);

        },

        updateShowPreviewContentWithNum : function (){
            var self = this;
            var numUnitVal = $(self.container).find(".open_rule_setting_show_num_unit").find("select").find("option:selected").val();
            var numUnitText = $(self.container).find(".open_rule_setting_show_num_unit").find("select").find("option:selected").text();
            var cusUnit = $(self.container).find(".open_rule_setting_show_cus_unit").find("input").val();
            var pointCount = $(self.container).find(".open_rule_setting_show_point_count").find("input").val();
            var thousandsChecked = $(self.container).find(".open_rule_setting_show_thousands").find("input").get(0).checked;
            var showText = "";
            if(true == thousandsChecked){
                showText = "999,999";
            }else{
                showText = "999999";
            }
            if(pointCount > 0){
                showText= showText + ".";
                for(var i=0;i<pointCount;i++){
                    showText= showText + "0";
                }
            }

            var unitText = '';
            if("0" != numUnitVal){
                unitText = numUnitText;
            }
            unitText= unitText + cusUnit;

            var $showPreviewContentNum = $(self.container).find(".open_rule_setting_show_preview_content_num");
            $showPreviewContentNum.text(showText);

            var $showPreviewContentUnit = $(self.container).find(".open_rule_setting_show_preview_content_unit");
            $showPreviewContentUnit.text(unitText);
        },

        updateShowPreviewContentWithPercent : function (){
            var self = this;
            var pointCount = $(self.container).find(".open_rule_setting_show_point_count").find("input").val();
            var showText = "99";
            if(pointCount > 0){
                showText= showText + ".";
                for(var i=0;i<pointCount;i++){
                    showText= showText + "0";
                }
            }
            showText= showText + "%";
            var $showPreviewContentNum = $(self.container).find(".open_rule_setting_show_preview_content_num");
            $showPreviewContentNum.text(showText);

            var $showPreviewContentUnit = $(self.container).find(".open_rule_setting_show_preview_content_unit");
            $showPreviewContentUnit.text("");
        },

        hidden : function (){
            $(".open_rule_setting").each(function () {
                if($(this).css("display") == "block"){
                    $(this).css("display", "none");
                }
            });

            $(".open_rule_setting_to_right").each(function () {
                if($(this).css("display") == "block"){
                    $(this).css("display", "none");
                }
            });
        },

        refreshStatisticsRuleSettingWhereIndex : function() {
            $(".open_rule_setting_where_content").find(".open_rule_setting_where_rule").each(function(index, item){
                $(item).attr("index", index);
            });
        },

        deleteStatisticsRuleSettingWhere : function(argu){
            for(var i = 0;i < this.statisticsRuleSettingWhereWgts.length;i++){
                if(argu.wgt === this.statisticsRuleSettingWhereWgts[i]){
                    this.statisticsRuleSettingWhereWgts.splice(i,1);
                    break;
                }
            }
            this.refreshStatisticsRuleSettingWhereIndex();
        },

        rollBackOldData : function(){
            var keyData = this.oldData;
            $(this.container).find('.open_rule_setting_where_type').find('select').val( keyData.whereType );
            this.statisticsRuleSettingWhereWgts = [];
            var $statisticsItem =  this.container;
            var $whereContent =  $statisticsItem.find('.open_rule_setting_where_content');
            $whereContent.empty();
            for(var i=0;i<keyData.statisticsRule.length;i++){
                var $whereRule = $("<div class='open_rule_setting_where_rule'></div>");
                var rowIndex = i+1;
                $whereRule.attr("index", rowIndex - 1);
                var statisticsRuleSettingWhereWgt = new statisticsRuleSettingWhere({parent:self,container: $whereRule,storeData:keyData.statisticsRule[i] });
                statisticsRuleSettingWhereWgt.draw();
                this.statisticsRuleSettingWhereWgts.push(statisticsRuleSettingWhereWgt);
                $whereContent.append($whereRule);
            }

            $(this.container).find('.open_rule_setting_action_type_select').find('select').val(keyData.statisticsType );
            $(this.container).find("[name='statisticsModelSet']").each(function () {
                $(this).find("i").removeClass("view_flag_yes");
                if(!keyData.statisticsModel){
                    keyData.statisticsModel = "1";
                }
                if($(this).attr("value") == keyData.statisticsModel){
                    $(this).find("i").addClass("view_flag_yes")
                }
            });
                $(this.container).find(".open_rule_setting_show_type").find("input").val(keyData.showType);

                $(this.container).find(".open_rule_setting_show_num_unit").find("select").find("option:selected").val(keyData.numUnitVal);

                $(this.container).find(".open_rule_setting_show_cus_unit").find("input").val(keyData.cusUnit);

                $(this.container).find(".open_rule_setting_show_thousands").find("input").prop('checked',keyData.thousandsChecked);

                $(this.container).find(".open_rule_setting_show_point_count").find("input").val(keyData.numUnitVal);

                var $showTypeInput = $(this.container).find(".open_rule_setting_show_type").find("input");
                if ("1" == $showTypeInput.val()) {
                    this.updateShowPreviewContentWithNum();
                } else if ("2" == $showTypeInput.val()) {
                    this.updateShowPreviewContentWithPercent();
                }
                if ("1" == $showTypeInput.val()) {
                    $(this.container).find(".open_rule_setting_show_num_unit").css("display", "");
                    $(this.container).find(".open_rule_setting_show_cus_unit").css("display", "");
                    $(this.container).find(".open_rule_setting_show_thousands").css("display", "");
                    $(this.container).find(".open_rule_setting_show_type_num").addClass("active");
                    $(this.container).find(".open_rule_setting_show_type_percent").removeClass("active");
                } else {
                    $(this.container).find(".open_rule_setting_show_num_unit").css("display", "none");
                    $(this.container).find(".open_rule_setting_show_cus_unit").css("display", "none");
                    $(this.container).find(".open_rule_setting_show_thousands").css("display", "none");
                    $(this.container).find(".open_rule_setting_show_type_num").removeClass("active");
                    $(this.container).find(".open_rule_setting_show_type_percent").addClass("active");
                }
            return keyData;
        },

        updateOldData : function(){
            var self = this;
            var keyData = {};
            var whereType = $(self.container).find('.open_rule_setting_where_type').find('select').val();
            keyData.whereType = whereType;
            var statisticsRule = [];
            for(var i=0;i<this.statisticsRuleSettingWhereWgts.length;i++){
                statisticsRule.push(self.statisticsRuleSettingWhereWgts[i].getKeyData());
            }
            keyData.statisticsRule = statisticsRule;
            keyData.statisticsType =  $(self.container).find('.open_rule_setting_action_type_select').find('select').val();
            var statisticsModel =  "1";
            $(this.container).find("[name='statisticsModelSet']").each(function () {
                if($(this).find("i").hasClass("view_flag_yes")){
                    statisticsModel = $(this).attr("value");
                }
            });
            keyData.statisticsModel = statisticsModel;

                var showType = $(self.container).find(".open_rule_setting_show_type").find("input").val();
                keyData.showType = showType;

                if ("1" == showType) {
                    var numUnitVal = $(self.container).find(".open_rule_setting_show_num_unit").find("select").find("option:selected").val();
                    keyData.numUnitVal = numUnitVal;

                    var cusUnit = $(self.container).find(".open_rule_setting_show_cus_unit").find("input").val();
                    keyData.cusUnit = cusUnit;

                    var thousandsChecked = $(self.container).find(".open_rule_setting_show_thousands").find("input").get(0).checked;
                    keyData.thousandsChecked = thousandsChecked;
                }

                var pointCount = $(self.container).find(".open_rule_setting_show_point_count").find("input").val();
                keyData.pointCount = pointCount;
            this.oldData = keyData;
        },

        checkData : function (){
            var self = this;
            if("none" != $(self.container).find("#cusUnitErrTip").css("display")){
                return false;
            }
            if("none" != $(self.container).find("#pointCountErrTip").css("display")){
                return false;
            }
            return true;
        },

        bytesLength : function (str){
            var count=0;
            for(var i=0;i<str.length;i++){
                if(str.charCodeAt(i)>255){
                    count+=3;
                }else{
                    count++;
                }
            }
            return count;
        },

        getKeyData : function (){
            return this.oldData;
        },


    })

    module.exports = StatisticsRuleSetting;
})