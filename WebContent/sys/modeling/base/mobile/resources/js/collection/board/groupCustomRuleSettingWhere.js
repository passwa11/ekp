/**
 * 移动列表视图的名称输入控件
 */

define(function(require, exports, module) {

    var $ = require('lui/jquery'),
        topic = require('lui/topic'),
        formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder"),
        relationDiagram = require('sys/modeling/base/mobile/resources/js/relationDiagram'),
        statisticsRuleSettingWhere = require('sys/modeling/base/mobile/resources/js/statisticsRuleSettingWhere'),
        base = require('lui/base');

    var GroupCustomRuleSettingWhere = statisticsRuleSettingWhere.extend({

        initProps: function($super, cfg) {
            $super(cfg);
            this.parent= cfg.parent || null;
            this.randomName = "fd_" + parseInt(((new Date().getTime()+Math.random())*10000)).toString(16);
            this.container = cfg.container || null;
            this.storeData = cfg.storeData || "";
            this.field =  this.storeData.field;
            this.fieldType = 'String';
            this.fieldOperator = this.storeData.fieldOperator;
            this.fieldValueType = this.storeData.fieldValue;
            this.fieldValue = this.storeData.fieldInputValue;
            this.fieldValueText = this.storeData.fieldValueText || "";
        },

        draw : function($super, cfg){
            $super(cfg);
        },

        delItem : function ($super,dom) {
            var $item = $(dom).parent(".open_rule_setting_where_rule");
            var curIndex = $item.attr("index");
            var kClassParent = this.parent;
            var groupCustomRuleSettingWhereWgts = kClassParent.groupCustomRuleSettingWhereWgts;
            var wgt = groupCustomRuleSettingWhereWgts[curIndex];
            $item.remove();
            wgt.destroy();
            return;
        },


        getKeyData : function (){
            var keyData = {};
            var $whereRule  = $(this.container);
            var field = $whereRule.find('.open_rule_setting_where_rule_field').find('select').val();
            keyData.field = field;
            keyData.fieldType= this.fieldType;
            var fieldOperator = $whereRule.find('.open_rule_setting_where_rule_field_operator').find('select').val();
            keyData.fieldOperator = fieldOperator;
            var fieldValueType = $whereRule.find('.open_rule_setting_where_rule_field_value_type').find('select').val();
            keyData.fieldValueType = fieldValueType;
            keyData.fieldValue = fieldValueType;
            if('!{empty}' == fieldValueType){
                keyData.fieldInputValue = null;
                keyData.fieldValueText = null;
            }else{
                var $input = $whereRule.find('.open_rule_setting_where_rule_field_value').find('input');
                if("radio" == $input.attr("type")){
                    $input.each(function () {
                       if(true == $(this).prop("checked")){
                           keyData.fieldInputValue = $(this).val();
                           keyData.fieldValueText = $(this).val();
                       }
                    })
                }else if("checkbox" == $input.attr("type")){
                    var val = '';
                    var text = '';
                    $input.each(function () {
                        if(true == $(this).prop("checked")){
                            val = val + $(this).val() + ";";
                            text = text + $(this).val() + ";";
                        }
                    })
                    keyData.fieldInputValue = val;
                    keyData.fieldValueText = text;
                }else if("hidden" == $input.attr("type")){
                    keyData.fieldInputValue = $input.eq(0).val();
                    keyData.fieldValueText = $input.eq(1).val();
                }else{
                    keyData.fieldInputValue = $input.val();
                    keyData.fieldValueText = $input.val();
                }
            }
            return keyData;
        }
    })

    module.exports = GroupCustomRuleSettingWhere;
})