/**
 * 其他节点项
 */
define(function (require, exports, module) {
    var $ = require("lui/jquery"),
        headGenerator = require('sys/modeling/base/mobile/resources/js/headGenerator'),
        otherNodeSettingButton = require('sys/modeling/base/views/business/res/otherNodeSettingButton'),
        topic = require('lui/topic');
    var modelingLang = require("lang!sys-modeling-base");
    var MindMapOtherNodeItem =  headGenerator.extend({
        initProps: function ($super, cfg) {
            $super(cfg);
            this.container = cfg.container;
            this.parent = cfg.parent;
            this.widgets = cfg.widgets;
            this.config = cfg.config;
            this.draw($super,cfg);
        },
        bindEvent: function () {

        },
        draw: function ($super,cfg) {
            var rowIndex = this.container.find(".otherItem").length+1;
            var $otherItem = $("<div class='item otherItem sortItem otherItems'></div>").appendTo(this.container);
            $otherItem.attr("index", rowIndex - 1);
            this.element = $otherItem;
            var self = this;
            var html = "";
            this.content = $("<div class='model-edit-view-oper' data-lui-position='fdOtherNode-"+(rowIndex-1)+"'></div>");
            $otherItem.append(this.content);
            var text = modelingLang['modelingTreeView.node']+rowIndex;
            $super(cfg);
            this.textEle.text(text);
            //内容
            html += "<div class='model-edit-view-oper-content'>";
            html += "<ul class='list-content'>";
            html += "<div class='item-content' />";
            html += "</ul>";
            html += "</div>";
            this.content.append(html);
            $fieldTd = this.content.find(".item-content");
            //设置按钮
            this.otherNodeSettingButton = new otherNodeSettingButton({
                container: $fieldTd,
                itemIndex:rowIndex,
                config:self.config,
                parent : self.parent
            });
            this.otherNodeSettingButton.draw();
        },
        delItem : function (dom) {
            var $item = $(dom).closest(".item");
            var curIndex = $item.attr("index");
            var targetModelId = $item.find("[name='fdTargetModelId']").val();
            var luiId = $item.attr("data-lui-cid");
            var kClass = LUI(luiId);
            if(kClass.otherNodeSettingButton.otherNodeSetting){
                var preNodeSettingCollection = kClass.otherNodeSettingButton.otherNodeSetting.preNodeSettingCollection;
                var sysWhere = kClass.otherNodeSettingButton.otherNodeSetting.sysWhereCollection;
                var where = kClass.otherNodeSettingButton.otherNodeSetting.whereCollection;
                var wgt = preNodeSettingCollection[curIndex];
                var whereWgt = where[curIndex];
                var sysWhereWgt = sysWhere[curIndex];
                $item.remove();
                if(wgt){
                    preNodeSettingCollection.splice(curIndex,1);
                    wgt.destroy();
                }
                if(whereWgt){
                    where.splice(curIndex,1);
                    topic.channel("modeling").unsubscribe("field.change", whereWgt.fieldChange, whereWgt);
                    whereWgt.destroy();
                }
                if(sysWhereWgt){
                    sysWhere.splice(curIndex,1);
                    topic.channel("modeling").unsubscribe("field.change", sysWhereWgt.fieldChange, sysWhereWgt);
                    sysWhereWgt.destroy();
                }
            }
            this.parent.delItem(kClass,targetModelId);
            return;
        },
        startup: function ($super, cfg) {
            $super(cfg);
        },
        //获取传到后台去的参数方法
        getKeyData: function () {
            return this.otherNodeSettingButton.getKeyData();
        }
    });

    exports.MindMapOtherNodeItem = MindMapOtherNodeItem;
})