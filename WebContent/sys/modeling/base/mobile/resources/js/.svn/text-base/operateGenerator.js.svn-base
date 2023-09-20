    /**
 * 业务操作组件
 */
define(function(require, exports, module) {

    var $ = require('lui/jquery'),
        base = require('lui/base'),
        topic = require('lui/topic'),
        headGenerator = require('sys/modeling/base/mobile/resources/js/headGenerator'),
        operateDialogUnion = require('sys/modeling/base/mobile/resources/js/operateDialogUnion');
    var modelingLang = require("lang!sys-modeling-base");
    var OperateGenerator = headGenerator.extend({

        initProps: function($super, cfg) {
            $super(cfg);
            this.randomName = "fd_" + parseInt(((new Date().getTime()+Math.random())*10000)).toString(16);
            this.container = cfg.container || null;
            this.storeData = cfg.data || {};
            this.text = this.storeData.fdName || "";
            this.type = "operate";
            this.fieldWgt = null;
        },

        draw : function($super, cfg){
            var rowIndex = this.container.find(".operateItem").length+1;
            var $operateItem = $("<div class='item operateItem sortItem'/>").appendTo(this.container);
            this.element = $operateItem;
            var self = this;
            var html = "";
            this.content = $("<div class='model-edit-view-oper' data-lui-position='fdOperation-"+(rowIndex-1)+"' onclick=switchSelectPosition(this,'right')>");
            $operateItem.append(this.content);
            this.rowIndex = (rowIndex - 1);
            $operateItem.attr("index", rowIndex - 1);
            $super(cfg);
            //内容
            html += "<div class='model-edit-view-oper-content'>";
            html += "<ul class='list-content'>";
            html += "</ul>";
            html += "</div>";
            this.content.append(html);
            //字段
            html = "<li class='model-edit-view-oper-content-item field first-item'><div class='item-title'>名称</div></li>";
            $operateItem.find("ul.list-content").eq(0).append(html);
            $field = $operateItem.find("ul.list-content").find("li.field").eq(0);
            var $fieldTd = $("<div class='item-content' />").appendTo($field);
            this.fieldWgt = new operateDialogUnion({container: $fieldTd,parent:this,dialogUrl:'', channel: self.channel ,value:self.storeData.field});
            this.fieldWgt.draw();
            // 初始化已有值
            if(self.storeData){
                $operateItem.find("input[name='operationId']").val(self.storeData.fdId);
                $operateItem.find("input[name='operationName']").val(self.storeData.fdName);
                this.fieldWgt.tipsShow(self.storeData.fdDefType, self.storeData.fdOperationScenario);
            }
            this.updateItemAttr($operateItem);
        },

        updateItemAttr : function ($super, item) {
            $super();
        },

        getKeyData : function(){
            var keyData = {};
            keyData.fdId = this.fieldWgt.getFieldValue();
            keyData.fdName = this.fieldWgt.getFieldText();
            return keyData;
        },

        validate : function() {
            var val = this.fieldWgt.getFieldValue();
            var isPass = true;
            var failMsg = modelingLang['modeling.name.required'];
            var elem = $(this.fieldWgt.element).closest(".model-edit-view-oper");
            var index = $(elem).parent().attr("index");
            elem.attr(KMSSValidation.ValidateConfig.attribute, "oper_validate_" + index);
            var defaultOpt = { where: 'beforeend'};
            var _reminder = new Reminder(elem,failMsg,"",defaultOpt);
            if (!val) {
                isPass = false;
                _reminder.show()
            } else {
                _reminder.hide()
            }
            return isPass;
        }
    })

    module.exports = OperateGenerator;

})