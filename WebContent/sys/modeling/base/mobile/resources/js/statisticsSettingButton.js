/**
 * 移动列表视图的名称输入控件
 */

define(function(require, exports, module) {

    var $ = require('lui/jquery'),
        topic = require('lui/topic'),
        base = require('lui/base'),
        statisticsRuleSetting = require('sys/modeling/base/mobile/resources/js/statisticsRuleSetting');
    var modelingLang = require("lang!sys-modeling-base");
    var StatisticsSettingButton = base.Component.extend({

        initProps: function($super, cfg) {
            $super(cfg);
            this.container = cfg.container || null;
            this.storeData = cfg.storeData || "";
            this.field = cfg.field;
            this.modelDict = cfg.modelDict;
            this.channel = cfg.channel;
            this.statisticsRuleSetting = null;
        },

        draw : function($super, cfg){
            $super(cfg);
            this.element = $("<li class='model-edit-view-oper-content-item last-item' />").appendTo(this.container);
            var $itemTitle = $("<div class='item-title'>"+modelingLang['listview.statistical.rules']+"</div>");
            this.element.append($itemTitle);
            var $itemContent = $("<div class='item-content'></div>");
            var $itemContentButton = $("<button type='button' class='statistics_rule_button'><i></i>"+modelingLang['listview.set']+"</button>");

            $itemContentButton.on("click",function () {
                var $statisticsItem =  $(this).parent('.item-content');
                closeStatisticsSetting();
                var x = $statisticsItem.offset().top;
                var y = $statisticsItem.offset().left;
                if(0 < $statisticsItem.find('.open_rule_setting').length){
                    var $openRuleSetting = $statisticsItem.find('.open_rule_setting')
                    if ($openRuleSetting.css("display") == "block") {
                        $openRuleSetting.css("display","none");
                    }else{
                        $openRuleSetting.css({
                            "top": x - 200,
                            "left": y - 548
                        });
                        $openRuleSetting.attr("scrollTop",$('.model-edit-right-wrap').scrollTop());
                        $openRuleSetting.attr("top", x - 200);
                        $openRuleSetting.css("display","block");
                    }
                }
                if(0 < $statisticsItem.find('.open_rule_setting_to_right').length){
                    var $openRuleSettingToRight = $statisticsItem.find('.open_rule_setting_to_right');
                    if ($openRuleSettingToRight.css("display") == "block") {
                        $openRuleSettingToRight.css("display","none");
                    }else {
                        $openRuleSettingToRight.css({
                            "top":x+8,
                            "left":y-10
                        });
                        $openRuleSettingToRight.attr("scrollTop",$('.model-edit-right-wrap').scrollTop());
                        $openRuleSettingToRight.attr("top", x+8);
                        $openRuleSettingToRight.css("display","block");
                    }
                }
            });
            $itemContent.append($itemContentButton)

            this.element.append($itemTitle);
            this.element.append($itemContent);

            //设置窗口
            this.statisticsRuleSetting = new statisticsRuleSetting({
                container: $itemContent,
                storeData:this.storeData,
                field : this.field,
                channel:this.channel,
                modelDict : this.modelDict
            });
            this.statisticsRuleSetting.draw();
            this.statisticsRuleSetting.hidden();
        },


        getKeyData : function (evt){
            return this.statisticsRuleSetting.getKeyData();
        }
    })

    module.exports = StatisticsSettingButton;
})