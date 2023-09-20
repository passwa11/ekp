/**
 * 移动列表视图的排序行组件
 */
define(function(require, exports, module) {

    var $ = require('lui/jquery'),
        base = require('lui/base'),
        topic = require('lui/topic'),
        headGenerator = require('sys/modeling/base/mobile/resources/js/headGenerator'),
        selectUnion = require('sys/modeling/base/mobile/resources/js/selectUnion');
    var modelingLang = require("lang!sys-modeling-base");
    var TreeViewOrderGenerator = headGenerator.extend({

        initProps: function($super, cfg) {
            $super(cfg);
            this.randomName = "fd_" + parseInt(((new Date().getTime()+Math.random())*10000)).toString(16);
            this.container = cfg.container || null;
            this.storeData = cfg.data || {};
            this.channel = cfg.channel;
            this.option = cfg.option;
            this.type = "order";
            this.fieldWgt = null;
        },

        draw : function($super, cfg){
            var rowIndex = this.container.find(".orderItem").length+1;
            var $orderItem = $("<div class='item orderItem sortItem'></div>").appendTo(this.container);
            $orderItem.attr("index", rowIndex - 1);
            this.element = $orderItem;
            var self = this;
            var html = ""
            this.content = $("<div class='model-edit-view-oper' data-lui-position='fdOrderBy-"+(rowIndex-1)+"'></div>");
            $orderItem.append(this.content);
            $super(cfg);
            //内容
            html += "<div class='model-edit-view-oper-content'>";
            html += "<ul class='list-content'>";
            html += "</ul>";
            html += "</div>";
            this.content.append(html);
            //字段
            html = "<li class='model-edit-view-oper-content-item field first-item'><div class='item-title'>"+modelingLang['relation.field']+"</div></li>";
            $orderItem.find("ul.list-content").eq(0).append(html);
            $field = $orderItem.find("ul.list-content").find("li.field").eq(0);
            var $fieldTd = $("<div class='item-content' />").appendTo($field);
            this.fieldWgt = new selectUnion({container: $fieldTd,parent:this,options:this.option,type:"order",value:self.storeData.field, channel: this.channel});
            this.fieldWgt.draw();
            //排序
            html = "<li class='model-edit-view-oper-content-item last-item'><div class='item-title'>"+modelingLang['modelingPortletCfg.fdOutSort']+"</div>";
            html += "<div class='item-content'><select  name='"+ self.randomName +"_fdOrderType' type='checkbox' class='inputsgl' style='width:100%'>";
            html += "<option value='asc'>"+modelingLang['modelingAppListview.fdOrderType.asc']+"</option>";
            html += "<option value='desc'>"+modelingLang['modelingAppListview.fdOrderType.desc']+"</option>";
            html += "</select></div></li>";
            $orderItem.find("ul.list-content").eq(0).append(html);
            this.updateItemAttr($orderItem);
            this.postscript();
        },

        updateItemAttr : function ($super, item) {
            $super();
            // 初始化已有值
            if(this.storeData.orderType){
                item.find("select[name='"+ this.randomName +"_fdOrderType']").val(this.storeData.orderType);
            }
            //修改默认标题
            var fieldId =item.find("div.select_union").find("select").eq(0).val();
            var fieldText = item.find("div.select_union").find("select").eq(0).find("option[value='"+fieldId+"']").text();
            text = fieldText;
            fieldId =item.find("div.select_union").find("select").eq(1).val();
            fieldText = item.find("div.select_union").find("select").eq(1).find("option[value='"+fieldId+"']").text();
            if(fieldText){
                text += "|"+fieldText;
            }
            item.find(".model-edit-view-oper-head-title span").html(text);
        },

        postscript : function() {
            //选择框切换数据后事件
            var self = this;
            topic.channel(this.channel).subscribe("field.change", function (data) {
                var selectDom = data.dom;
                //更新标题
                var $parent = $(selectDom).parents("div.select_union").eq(0);
                var text = "";
                var fieldId = $parent.find("select").eq(0).val();
                var fieldText = $parent.find("select").eq(0).find("option[value='" + fieldId + "']").text();
                text = fieldText;
                fieldId = $parent.find("select").eq(1).val();
                fieldText = $parent.find("select").eq(1).find("option[value='" + fieldId + "']").text();
                if (fieldText) {
                    text += "|" + fieldText;
                }
                $(selectDom).parents(".model-edit-view-oper").eq(0).find(".model-edit-view-oper-head-title span").text(text);
                //刷新预览
                topic.publish("preview.refresh", {key: self.channel});
            });
        },
        delItem : function (dom) {
            var $item = $(dom).closest(".item");
            var curIndex = $item.attr("index");
            var luiId = $item.parents("[data-lui-cid]").eq(0).attr("data-lui-cid");
            var kClass = LUI(luiId);
            if (this.type == 'order') {
                var orderCollection = kClass.otherNodeSettingButton.otherNodeSetting.orderCollection;
                var wgt = orderCollection[curIndex];
            }
            $item.remove();
            topic.channel(this.channel).publish(this.type + ".delete", {"wgt": wgt});
            topic.channel(this.channel).unsubscribe("field.change");
            wgt.destroy();
            return;
        },
        getKeyData : function(){
            var keyData = {};
            keyData.field = this.fieldWgt.getFieldValue();
            keyData.fieldType = this.fieldWgt.getFieldType();
            keyData.text = this.fieldWgt.getFieldText();
            keyData.orderType = this.element.find("select[name='"+ this.randomName +"_fdOrderType'] option:selected").val();
            return keyData;
        },

        // 移动 -1：上移       1：下移
        sort: function (evt) {
            var tb = $(dom).closest("table")[0];
            var $tr = $(dom).closest("tr");
            var curIndex = $tr.index();
            var lastIndex = tb.rows.length - 1;
            var targetIndex = curIndex;
            if (direct == 1) {
                if (curIndex >= lastIndex) {
                    alert(modelingLang['listview.is.the.end']);
                    return;
                }
                $tr.next().after($tr);
                targetIndex = curIndex + 1;
            } else {
                if (curIndex < 1) {
                    alert(modelingLang['listview.moved.to.top']);
                    return;
                }
                $tr.prev().before($tr);
                targetIndex = curIndex - 1;
            }
            if (type && curIndex != targetIndex) {
                var luiId = $(tb).parents("[data-lui-cid]").eq(0).attr("data-lui-cid");
                var kclass = LUI(luiId);
                kclass.moveWgt(type, curIndex, targetIndex);
            }
        }
    })

    module.exports = TreeViewOrderGenerator;

})