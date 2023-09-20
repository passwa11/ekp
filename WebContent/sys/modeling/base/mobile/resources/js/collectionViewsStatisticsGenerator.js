/**
 * 移动列表视图的统计行组件
 */
define(function(require, exports, module) {

    var $ = require('lui/jquery'),
        base = require('lui/base'),
        topic = require('lui/topic'),
        headGenerator = require('sys/modeling/base/mobile/resources/js/headGenerator'),
        nameInput = require('sys/modeling/base/mobile/resources/js/nameInput'),
        statisticsSettingButton = require('sys/modeling/base/mobile/resources/js/statisticsSettingButton');
    var modelingLang = require("lang!sys-modeling-base");
    var CollectionViewsStatisticsGenerator = headGenerator.extend({

        initProps: function($super, cfg) {
            $super(cfg);
            this.randomName = "fd_" + parseInt(((new Date().getTime()+Math.random())*10000)).toString(16);
            this.container = cfg.container || null;
            this.storeData = cfg.data || {};
            this.channel = cfg.channel;
            this.type = "statistics";
            this.nameWgt = null;
            this.statisticsSettingButton = null;
            this.field = cfg.field;
            this.modelDict = listviewOption.baseInfo.modelDict;
        },

        draw : function($super, cfg){
            var rowIndex = this.container.find(".statisticsItem").length+1;
            var $statisticsItem = $("<div class='item statisticsItem sortItem'></div>").appendTo(this.container);
            $statisticsItem.attr("index", rowIndex - 1);
            this.element = $statisticsItem;
            var self = this;
            var html = ""
            this.content = $("<div class='model-edit-view-oper' data-lui-position='fdStatistics-"+(rowIndex-1)+"' onclick=switchSelectPosition(this,'right')></div>");
            $statisticsItem.append(this.content);
            $super(cfg);
            //内容
            html += "<div class='model-edit-view-oper-content'>";
            html += "<ul class='list-content'>";
            html += "</ul>";
            html += "</div>";
            this.content.append(html);
            //名称输入
            html = "<li class='model-edit-view-oper-content-item field first-item'><div class='item-title'>"+modelingLang['modelingAppCollecctionView.fdName']+"</div></li>";
            $statisticsItem.find("ul.list-content").eq(0).append(html);
            var $field = $statisticsItem.find("ul.list-content").find("li.field").eq(0);
            var $fieldTd = $("<div class='item-content' />").appendTo($field);
            this.nameWgt = new nameInput({container: $fieldTd,channel :self.channel,value:self.storeData.name });
            this.nameWgt.draw();
            //设置按钮
            this.statisticsSettingButton = new statisticsSettingButton({
                container: $fieldTd,
                storeData:self.storeData,
                field : this.field,
                channel:this.channel,
                modelDict : this.modelDict
            });
            this.statisticsSettingButton.draw();

        },

        getKeyData : function(){
            var keyData = this.statisticsSettingButton.getKeyData();
            keyData.name = this.nameWgt.getKeyData();
            keyData.field = this.field;
            return keyData;
        },

    })

    module.exports = CollectionViewsStatisticsGenerator;

})