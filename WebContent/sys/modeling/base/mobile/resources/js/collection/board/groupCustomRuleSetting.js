/**
 * 移动列表视图的名称输入控件
 */

define(function(require, exports, module) {

    var $ = require('lui/jquery'),
        topic = require('lui/topic'),
        groupCustomRuleGenerator = require('sys/modeling/base/mobile/resources/js/collection/board/groupCustomRuleGenerator'),
        base = require('lui/base');

    var GroupCustomRuleSetting = base.Component.extend({

        initProps: function($super, cfg) {
            $super(cfg);
            this.randomName = "fd_" + parseInt(((new Date().getTime()+Math.random())*10000)).toString(16);
            this.container = cfg.container || null;
            this.storeData = cfg.storeData || "";
            this.modelDict = cfg.modelDict;
            this.channel = cfg.channel || null;
        },

        startup : function($super, cfg) {
            $super(cfg);
            topic.channel(this.channel).subscribe("groupCustomRule.delete", this.deleteGroupCustomRule, this);
        },

        draw : function($super, cfg){
            $super(cfg);
            var self = this;
            var $ruleItem =  this.container;
            self.groupCustomRuleCollection = [];
            var $boardReBack = $('<div class="board-list-view-group-custom-back"><span></span>返回</div>');
            $ruleItem.append($boardReBack);
            $boardReBack.on("click",function() {
                for(var i = 0;i < self.groupCustomRuleCollection.length;i++){
                    var ruleWgt = self.groupCustomRuleCollection[i];
                    if (ruleWgt.nameWgt){
                        var $input = ruleWgt.nameWgt.element.find("input");
                        $input.focus();
                        $input.blur();
                        var ruleName = $input.val();
                        if (ruleName){
                            for(var j = 0;j < self.groupCustomRuleCollection.length;j++){
                                var ruleItemWgt = self.groupCustomRuleCollection[j];
                                if (ruleWgt != ruleItemWgt && ruleItemWgt.nameWgt){
                                    var $ruleItemInput = ruleItemWgt.nameWgt.element.find("input");
                                    var ruleItemName = $ruleItemInput.val();
                                    if (ruleItemName == ruleName) {
                                        return;
                                    }
                                }
                            }
                        }else{
                            return;
                        }
                    }
                }
                self.container.hide();
            })
            var $boardRulesContent = $('<div class="board-list-view-group-custom-rules-content"></div>');
            var $boardRulesHead = $('<div class="board-list-view-group-custom-rules-head"></div>');
            var $boardRulesTitle = $('<div class="board-list-view-group-custom-rules-title">看板</div>');
            $boardRulesHead.append($boardRulesTitle);
            var $boardRulesCreate = $('<div class="board-list-view-group-custom-rules-create">新增</div>');
            $boardRulesHead.append($boardRulesCreate);
            this.$boardRuleContainer = $('<div class="board-list-view-group-custom-rule-container" id="fd_'+this.channel+'_groupCustomRule" data-table-type="groupCustomRule"></div>');
            $boardRulesContent.append($boardRulesHead);
            $boardRulesContent.append(this.$boardRuleContainer);
            $ruleItem.append($boardRulesContent);
            if(self.storeData.groupRules && self.storeData.groupRules.length){
                for (var i = 0; i < self.storeData.groupRules.length; i++) {
                    var groupWgt = new groupCustomRuleGenerator({
                        container: this.$boardRuleContainer,
                        parent: self,
                        data:self.storeData.groupRules[i],
                        channel: self.channel
                    });
                    groupWgt.startup();
                    groupWgt.draw();
                    self.groupCustomRuleCollection.push(groupWgt);
                    self.sortable("groupCustomRule");
                }
            }

            $boardRulesCreate.on("click",function() {
                var groupWgt = new groupCustomRuleGenerator({
                    container: self.$boardRuleContainer,
                    parent: self,
                    channel: self.channel
                });
                groupWgt.startup();
                groupWgt.draw();
                self.groupCustomRuleCollection.push(groupWgt);
                self.sortable("groupCustomRule");
            });

        },

        hidden : function (){
            this.container.hide();
        },

        show:function() {
            this.container.show();
        },
        /**
         * 将组件从容器中移除，并更新其它组件的索引
         * @param argu {wgt:wgt} 要删除的组件
         */
        deleteGroupCustomRule : function(argu){
            for(var i = 0;i < this.groupCustomRuleCollection.length;i++){
                if(argu.wgt === this.groupCustomRuleCollection[i]){
                    this.groupCustomRuleCollection.splice(i,1);
                    break;
                }
            }
            this.refreshGroupCustomRuleIndex();
        },

        refreshGroupCustomRuleIndex : function() {
            $("#fd_" + this.channel + "_groupCustomRule").find(".sortItem").each(function(index, item){
                $(item).attr("index", index);
                $(item).find("[data-lui-position]").each(function(i,Obj){
                    $(Obj).attr("data-lui-position", "fdGroupCustomRule-" + index);
                });
            });
        },

        sortable : function(type) {
            var list = $("#fd_" + this.channel + "_" + type)[0];
            var self = this;
            Sortable.create(list,{
                sort: true,
                scroll: true,
                touchStartThreshold: 0,
                swapThreshold: 1,
                animation: 150,
                handle:".sortableIcon",
                draggable: ".sortItem",
                onStart: function(evt) {
                    console.log(evt);
                },
                onEnd: function (evt) {
                    self.refreshWhenSort(evt);
                    topic.publish("preview.refresh", {key: self.channel});
                }
            });
        },

        /**
         * 拖动重新排序组件数据跟刷新对应的index
         * @param evt
         */
        refreshWhenSort : function(evt) {
            var sortItem = evt.item;
            var context = $(sortItem).closest("[data-table-type]");
            var type = context.attr("data-table-type");
            var srcWgts = [];
            if (type === "groupCustomRule") {
                srcWgts = this.groupCustomRuleCollection;
            }
            var targetWgts = [];
            context.find(".sortItem").each(function (index, obj){
                var oldIndex = parseInt($(obj).attr("index"));
                targetWgts.push(srcWgts[oldIndex]);
            });
            if (type === "groupCustomRule") {
                this.refreshGroupCustomRuleIndex();
                this.groupCustomRuleCollection = targetWgts;
            }
        },

        getKeyData : function (){
            var keyData = [];
            if(this.groupCustomRuleCollection){
                for (var i = 0; i < this.groupCustomRuleCollection.length; i++) {
                    var ruleWidget = this.groupCustomRuleCollection[i];
                    keyData.push(ruleWidget.getKeyData());
                }
            }
            return keyData;
        },


    })

    module.exports = GroupCustomRuleSetting;
})