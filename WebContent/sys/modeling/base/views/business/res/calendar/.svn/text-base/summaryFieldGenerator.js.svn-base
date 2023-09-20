define(function(require, exports, module) {
    var $ = require("lui/jquery");
    var base = require("lui/base");
    //公式定义器
    var formulaBuilder = require("sys/modeling/base/views/business/res/calendar/formulaBuilder");
    var lang = require("lang!sys-modeling-base");
    var summaryItemNames=[
        {
            text:lang['calendar.time'],
            value : "time"
        },
        {
            text:lang['calendar.content.csummary'],
            value : "summary"
        },
        {
            text:lang['calendar.initiator'],
            value : "person"
        },
        {
            text:lang['calendar.participants'],
            value : "joiner"
        },
        {
            text:lang['calendar.place'],
            value : "address"
        }
    ]
    var SummaryFieldGenerator = base.Component.extend({
        initProps:function($super, cfg) {
            $super(cfg);
            this.randomName = "fd_" + parseInt(((new Date().getTime()+Math.random())*10000)).toString(16);
            this.container = cfg.container || null;
            this.storeData = cfg.data || {};
            this.parent = cfg.parent;
            this.channel = cfg.channel || null;
            this.xformId = cfg.xformId || "";
        },

        startup:function($super, cfg) {
            $super(cfg);
        },
        draw : function($super, cfg){
            $super(cfg);
            this.element = $('<div class="model-mask-panel-table-base"></div>');
            this.element.appendTo(this.container);
            var $table= $("<table class='tb_normal field_table' width='100%'></table>" );
            var headItem ="<tr><td width='30%'>"+lang['respanel.display.field']+"</td><td width='70%'>"+lang['respanel.mapping.field']+"</td></tr>";
            $table.append(headItem);
            this.renderSummaryItem($table);
            this.element.append($table);
            this.initStoreData();
        },

        renderSummaryItem:function($dom) {
            formulaBuilder.initFieldList(this.xformId);
            for (var i = 0; i < summaryItemNames.length; i++) {
                var formulaHtml = formulaBuilder.get_style2(this.randomName+'_'+summaryItemNames[i].value,"String",null,null,null);
                var $tr = $('<tr></tr>');
                var $td = $('<td></td>');
                $td.appendTo($tr);
                $td.append(summaryItemNames[i].text);
                var $tdItem = $('<td></td>');
                var $sourceItem =$('<div class="source_dialog_table_item"></div>')
                $sourceItem.append(formulaHtml);
                $tdItem.append($sourceItem);
                $tr.append($tdItem);
                $dom.append($tr);
            }

        },

        initStoreData : function() {
            for (var i = 0; i < summaryItemNames.length; i++) {
                for (var j = 0; j < this.storeData.length; j++) {
                    if (this.storeData[j].name == summaryItemNames[i].value){
                        this.element.find("[name="+this.randomName+'_'+summaryItemNames[i].value+"]").val(this.storeData[j].value);
                        this.element.find("[name="+this.randomName+'_'+summaryItemNames[i].value+"_name]").val(this.storeData[j].text);
                    }
                }
            }
        },

        getKeyData : function() {
            var keyData = [];
            for (var i = 0; i < summaryItemNames.length; i++) {
                var summaryItem = {};
                summaryItem.value = this.element.find("[name="+this.randomName+'_'+summaryItemNames[i].value+"]").val();
                summaryItem.text = this.element.find("[name="+this.randomName+'_'+summaryItemNames[i].value+"_name]").val();
                summaryItem.name = summaryItemNames[i].value;
                summaryItem.label = summaryItemNames[i].text;
                keyData.push(summaryItem);
            }
            return keyData;
        }
    })

    exports.SummaryFieldGenerator = SummaryFieldGenerator;
})