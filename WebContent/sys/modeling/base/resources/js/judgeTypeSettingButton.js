/**
 * 条件判断视图的按钮控件
 */

define(function(require, exports, module) {
    var $ = require('lui/jquery'),
        base = require('lui/base');
    var topic = require("lui/topic");
    var dialog = require("lui/dialog");
    var judgeTypeSetting = require('sys/modeling/base/resources/js/judgeTypeSetting');
    var modelingLang = require("lang!sys-modeling-base");

    var JudgeTypeSettingButton = base.Container.extend({

        initProps: function($super, cfg) {
            $super(cfg);
            this.xformId = cfg.xformId;
            this.container = cfg.container || null;
            this.storeData = cfg.storeData || null;
            this.resultInputPrefix = cfg.resultInputPrefix;
            this.resultInputSuffix = cfg.resultInputSuffix;
            this.modelDict = cfg.modelDict;
            this.channel = cfg.channel;
            this.statisticsRuleSetting = null;
        },

        draw : function($super, cfg){
            $super(cfg);
            var buttonDiv = this.container;
            this.element = $("<li class='model-edit-view-oper-content-item last-item' />").appendTo(buttonDiv);
            var $itemContent = $("<div class='item-content'></div>");
            var $itemContentButton = $("<button type='button' class='statistics_rule_button'><i></i>"+modelingLang['listview.set']+"</button>");

            $itemContentButton.on("click",function () {
                var $statisticsItem =  $(this).parent('.item-content');
                closeStatisticsSetting();
                var x = $statisticsItem.offset().top;
                var y = $statisticsItem.offset().left;
                if(0 < $statisticsItem.find('.open_rule_setting').length){
                    var $openRuleSetting = $statisticsItem.find('.open_rule_setting');
                    if ($openRuleSetting.css("display") == "block") {
                        $openRuleSetting.css("display","none");
                    }else{
                        $openRuleSetting.css({
                            "top": x - 200,
                            "left": y - 548
                        });
                        $openRuleSetting.attr("scrollTop",$('.model-edit-view-content-right').scrollTop());
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
                        $openRuleSettingToRight.attr("scrollTop",$('.model-edit-view-content-right').scrollTop());
                        $openRuleSettingToRight.attr("top", x+8);
                        $openRuleSettingToRight.css("display","block");
                    }
                }

                //绑定右边内容区滚动事件，滚动时查询框跟随上下移动
                $('.model-edit-view-content-right').unbind('scroll').bind('scroll', function(){
                    var scrollTop = $(this).scrollTop();
                    var $openRuleSetting = $(".open_rule_setting");
                    if(0<$openRuleSetting.length){
                        $openRuleSetting.each(function () {
                            var firstScrollTop =  $(this).attr("scrollTop");
                            var top = $(this).attr("top");
                            $(this).css({
                                "top": top - scrollTop + Number(firstScrollTop),
                            });
                        });
                    }
                    var $openRuleSettingToRight = $(".open_rule_setting_to_right");
                    if(0<$openRuleSettingToRight.length){
                        $openRuleSettingToRight.each(function () {
                            var firstScrollTop =  $(this).attr("scrollTop");
                            var top = $(this).attr("top");
                            $(this).css({
                                "top": top - scrollTop + Number(firstScrollTop),
                            });
                        });
                    }
                });

            });


            $itemContent.append($itemContentButton);

            this.element.append($itemContent);

            //设置窗口
            this.judgeTypeSetting = new judgeTypeSetting({
                xformId: this.xformId,
                container: $itemContent,
                storeData:this.storeData,
                resultInputPrefix: this.resultInputPrefix,
                resultInputSuffix: this.resultInputSuffix,
                channel:this.channel,
                modelDict : this.modelDict
            });
            this.judgeTypeSetting.draw();
            this.judgeTypeSetting.hidden();

        },


        getKeyData : function (evt){
            return this.judgeTypeSetting.getKeyData();
        }




    });

    exports.JudgeTypeSettingButton = JudgeTypeSettingButton;
});