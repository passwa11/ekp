/**
 * 移动列表视图的名称输入控件
 */

define(function(require, exports, module) {

    var $ = require('lui/jquery'),
        topic = require('lui/topic'),
        base = require('lui/base'),
        groupCustomRuleSetting = require('sys/modeling/base/mobile/resources/js/collection/board/groupCustomRuleSetting');

    var GroupCustomSettingButton = base.Component.extend({

        initProps: function($super, cfg) {
            $super(cfg);
            this.container = cfg.container || null;
            this.storeData = cfg.storeData || "";
            this.modelDict = cfg.modelDict;
            this.groupCustomRuleSetting = null;
            this.parent = cfg.parent || null;
            this.channel = cfg.channel || null;
        },

        draw : function($super, cfg){
            $super(cfg);
            this.element = $("<li class='model-edit-view-oper-content-item last-item' />").appendTo(this.container);
            var $itemTitle = $("<div class='item-title'>看板规则</div>");
            this.element.append($itemTitle);
            var $itemContent = $("<div class='item-content'></div>");
            var $itemContentButton = $("<div class='group_rule_button'><div>设置</div></div>");
            var $ruleSetting = $('<div class="board-list-view-group-custom-rules" style="display:none;"></div>');

            var self = this;
            $itemContentButton.on("click",function () {
                self.groupCustomRuleSetting.show();
            });
            $itemContent.append($itemContentButton)

            this.element.append($itemTitle);
            this.element.append($itemContent);
            this.parent.element.append($ruleSetting);
            //设置窗口
            this.groupCustomRuleSetting = new groupCustomRuleSetting({
                container: $ruleSetting,
                storeData:this.storeData,
                modelDict : this.modelDict,
                channel: this.channel
            });
            this.groupCustomRuleSetting.startup();
            this.groupCustomRuleSetting.draw();
            this.groupCustomRuleSetting.hidden();
        },


        getKeyData : function (evt){
            return this.groupCustomRuleSetting.getKeyData();
        }
    })

    module.exports = GroupCustomSettingButton;
})