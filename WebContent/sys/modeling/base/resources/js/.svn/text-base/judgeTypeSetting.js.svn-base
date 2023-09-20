/**
 * 条件判断视图的控件
 */

define(function(require, exports, module) {

    var $ = require('lui/jquery'),
        topic = require('lui/topic'),
        judgeTypeSettingWhere = require('sys/modeling/base/resources/js/judgeTypeSettingWhere'),
        base = require('lui/base');
    var modelingLang = require("lang!sys-modeling-base");

    var JudgeTypeSetting = base.Component.extend({

        initProps: function($super, cfg) {
            $super(cfg);
            this.xformId = cfg.xformId;
            this.randomName = "fd_" + parseInt(((new Date().getTime()+Math.random())*10000)).toString(16);
            this.container = cfg.container || null;
            this.storeData = cfg.storeData || {};
            this.resultInputPrefix = cfg.resultInputPrefix;
            this.resultInputSuffix = cfg.resultInputSuffix;
            if(cfg.channel){
                this.channel = cfg.channel;
            }else{
                //默认为pc
                this.channel = 'pc';
            }

            this.modelDict = cfg.modelDict;
            this.judgeTypeSettingWhereWgts = [];
            this.oldData = {};
            topic.channel(this.channel).subscribe("judgeTypeSettingWhereWgts.delete",this.deleteJudgeTypeSettingWhere, this);
        },

        draw : function($super, cfg){
            $super(cfg);
            var self = this;

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

            //数据范围类型
            var $whereType = $("<div class='open_rule_setting_where_type'></div>");
            var $whereTypeLeft = $("<div class='open_rule_setting_where_type_left'>"+modelingLang['listview.meet.following']+"</div>");
            var $whereTypeSelect = $("<div class='open_rule_setting_where_type_select'><select " +
                " class='inputsgl' style='width:100%'><option value='0'>"+modelingLang['listview.all']+"</option><option value='1'>"+modelingLang['listview.any']+"</option></select></div>");
            if(this.storeData.whereType){
                $whereTypeSelect.find('select').val(this.storeData.whereType);
            }
            var $whereTypeRight = $("<div class='open_rule_setting_where_type_right'>"+modelingLang['listview.condition']+"</div>");
            $whereType.append($whereTypeLeft);
            $whereType.append($whereTypeSelect);
            $whereType.append($whereTypeRight);

            $openRuleSetting.append($whereType);
            //数据规则
            var $whereContent = $("<div class='open_rule_setting_where_content' style='min-height: 20px'></div>");
            $openRuleSetting.append($whereContent);
            if(self.storeData.statisticsRule && self.storeData.statisticsRule.length){
                for (var i = 0; i < self.storeData.statisticsRule.length; i++) {
                    var $whereRule = $("<div class='open_rule_setting_where_rule'></div>");
                    var rowIndex = $whereContent.find(".open_rule_setting_where_rule").length+1;
                    $whereRule.attr("index", rowIndex - 1);
                    var judgeTypeSettingWhereWgt = new judgeTypeSettingWhere({parent:self,container: $whereRule,storeData:self.storeData.statisticsRule[i], modelDict:self.modelDict, xformId:self.xformId });
                    judgeTypeSettingWhereWgt.draw();
                    self.judgeTypeSettingWhereWgts.push(judgeTypeSettingWhereWgt);
                    $whereContent.append($whereRule);
                }
            }

            var $whereCreate = $("<div class='open_rule_setting_where_create'><span>"+modelingLang['button.add']+"</span></div>");
            $whereCreate.on("click",function () {
                var $whereRule = $("<div class='open_rule_setting_where_rule'></div>");
                var rowIndex = $whereContent.find(".open_rule_setting_where_rule").length+1;
                $whereRule.attr("index", rowIndex - 1);
                var judgeTypeSettingWhereWgt = new judgeTypeSettingWhere({parent:self,container: $whereRule,storeData:{}, modelDict:self.modelDict, xformId:self.xformId});
                judgeTypeSettingWhereWgt.draw();
                self.judgeTypeSettingWhereWgts.push(judgeTypeSettingWhereWgt);
                $whereContent.append($whereRule);
            });
            $openRuleSetting.append($whereCreate);

            //确定按钮
            var $footButtonDiv = $("<div class='open_rule_setting_foot_button'></div>");
            var $footButtonSure = $("<div class='open_rule_setting_foot_button_sure'>"+modelingLang['modeling.button.ok']+"</div>");
            $footButtonDiv.append($footButtonSure);
            $footButtonSure.on("click",function () {
                self.updateOldData($footButtonDiv,true);
                $openRuleSetting.css("display","none");
            });
            //取消按钮
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


            self.updateOldData(self,false);
        },

        hidden : function (foot){
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

        refreshJudgeTypeSettingWhereIndex : function() {
            $(".open_rule_setting_where_content").find(".open_rule_setting_where_rule").each(function(index, item){
                $(item).attr("index", index);
            });
        },

        deleteJudgeTypeSettingWhere : function(argu){
            for(var i = 0;i < this.judgeTypeSettingWhereWgts.length;i++){
                if(argu.wgt === this.judgeTypeSettingWhereWgts[i]){
                    this.judgeTypeSettingWhereWgts.splice(i,1);
                    break;
                }
            }
            this.refreshJudgeTypeSettingWhereIndex();
        },

        rollBackOldData : function(){
            var keyData = this.oldData;
            $(this.container).find('.open_rule_setting_where_type').find('select').val( keyData.whereType );
            this.judgeTypeSettingWhereWgts = [];
            var $statisticsItem =  this.container;
            var $whereContent =  $statisticsItem.find('.open_rule_setting_where_content');
            $whereContent.empty();
            for(var i=0;i<keyData.statisticsRule.length;i++){
                var $whereRule = $("<div class='open_rule_setting_where_rule'></div>");
                var rowIndex = i+1;
                $whereRule.attr("index", rowIndex - 1);
                var judgeTypeSettingWhereWgt = new judgeTypeSettingWhere({parent:self,container: $whereRule,storeData:keyData.statisticsRule[i],modelDict:this.modelDict, xformId:this.xformId });
                judgeTypeSettingWhereWgt.draw();
                this.judgeTypeSettingWhereWgts.push(judgeTypeSettingWhereWgt);
                $whereContent.append($whereRule);
            }

            $(this.container).find('.open_rule_setting_action_type_select').find('select').val(keyData.statisticsType );
            $(this.container).find("[name='statisticsModelSet']").each(function () {
                $(this).find("i").removeClass("view_flag_yes");
                if(!keyData.statisticsModel){
                    keyData.statisticsModel = "1";
                }
                if($(this).attr("value") == keyData.statisticsModel){
                    $(this).find("i").addClass("view_flag_yes");
                }
            });

            return keyData;
        },

        updateOldData : function(footButton,flag){
            var self = this;
            var keyData = {};
            var whereType = $(self.container).find('.open_rule_setting_where_type').find('select').val();
            keyData.whereType = whereType;
            var statisticsRule = [];
            for(var i=0;i<this.judgeTypeSettingWhereWgts.length;i++){
                statisticsRule.push(self.judgeTypeSettingWhereWgts[i].getKeyData());
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
            keyData.channel = self.channel;

            this.oldData = keyData;
            //点击的是<确定>按钮，则更新前页面配置的数据到form表单
            if(flag){
                //获取设置按钮的信息
                var settingButtonElement = $(footButton).parents('div[data-lui-type="sys/modeling/base/resources/js/judgeTypeSettingButton!JudgeTypeSettingButton"]');
                if(settingButtonElement){
                    //获取位置下标
                    var id = settingButtonElement[0].getAttribute("id");
                    var index = id.substring(id.indexOf("["), id.indexOf("]") + 1);
                    //赋值
                    $("input[name='"+this.resultInputPrefix+index+this.resultInputSuffix+"'][type='hidden']").val(JSON.stringify(this.oldData));
                }
            }
        },

        getKeyData : function (){
            return this.oldData;
        },

    });

    module.exports = JudgeTypeSetting;
})