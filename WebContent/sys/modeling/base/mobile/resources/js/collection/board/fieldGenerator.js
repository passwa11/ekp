define(function(require, exports, module) {

    var $ = require('lui/jquery'),
        base = require('lui/base'),
        topic = require("lui/topic");

    var FieldGenerator = base.Component.extend({

        initProps : function($super,cfg) {
            $super(cfg);
            this.randomName = "fd_" + parseInt(((new Date().getTime()+Math.random())*10000)).toString(16);
            this.container = cfg.container || null;
            this.storeData = cfg.storeData || null;
            this.field = cfg.field;
            this.fieldInfos = cfg.fieldInfos;
            this.channel = cfg.channel || "";
            this.parent = cfg.parent || null;
        },

        startup:function($super, cfg) {
            $super(cfg);
        },

        draw:function($super,cfg) {
            $super(cfg);
            this.element = $('<div class="board-list-view-group-field-detail"></div>');
            this.fieldInfo = this.storeData || this.getFieldInfo();
            this.drawFieldInfo();
            this.drawOperator();
            this.container.append(this.element);
        },

        drawFieldInfo:function (){
            var $fieldDesc = $('<div class="board-list-view-group-field-desc"></div>')
            var $fieldInfo = $("<div class='board-list-view-group-field-fieldInfo'></div>");
            var $fieldText = $('<div class="board-list-view-group-field-fieldText" />');
            var $fieldValue = $('<input type="hidden" />');

            $fieldText.html(this.fieldInfo.text);
            $fieldValue.attr("name","fd_"+this.randomName + "_fieldValue");
            $fieldValue.val(JSON.stringify(this.fieldInfo));
            $fieldInfo.append($fieldText);
            $fieldInfo.append($fieldValue);
            $fieldDesc.append($fieldInfo);

            var $fieldDefault = $("<div class='board-list-view-group-field-default'></div>");
            var $fieldSpan = $('<span>默认</span>');
            var $fieldIsDefault = $('<input type="hidden" />');

            $fieldIsDefault.attr("name","fd_"+this.randomName + "_fieldIsDefault");
            $fieldIsDefault.val(this.fieldInfo.isDefault);
            $fieldDefault.append($fieldSpan);
            $fieldDefault.append($fieldIsDefault);
            if(this.fieldInfo.isDefault == "1"){
                $fieldDesc.addClass("active");
            }

            var self = this;
            //设置默认分类
            $fieldDefault.on("click",function(e) {
                self.setGroupDefault();
            })
            $fieldDesc.append($fieldDefault);

            this.element.append($fieldDesc);
        },

        drawOperator:function() {
            var $fieldOperator = $("<div class='board-list-view-group-field-del'></div>");
            var self = this;
            $fieldOperator.on("click",function() {
                self.element.remove();
                self.field = {};
                self.delItem();
            })
            this.element.append($fieldOperator);
        },

        delItem : function () {
            var groupCollection = this.parent.groupCollection;
            var curIndex = groupCollection.indexOf(this);
            groupCollection.splice(curIndex, 1);
            this.destroy();
        },

        setGroupDefault:function() {
            var groupCollection = this.parent.groupCollection;
            for (var i = 0; i < groupCollection.length; i++) {
                var groupWgt = groupCollection[i];
                if(groupWgt.cid == this.cid){
                    groupWgt.element.find(".board-list-view-group-field-desc").addClass("active");
                    groupWgt.element.find("[name=fd_"+groupWgt.randomName + "_fieldIsDefault]").val("1");
                }else{
                    groupWgt.element.find(".board-list-view-group-field-desc").removeClass("active");
                    groupWgt.element.find("[name*=fd_"+groupWgt.randomName + "_fieldIsDefault]").val("0");
                }
            }

        },

        getFieldInfo:function() {
          if(this.fieldInfos){
              for (var i = 0; i < this.fieldInfos.length; i++) {
                if (this.fieldInfos[i].field == this.field){
                    return this.fieldInfos[i];
                }
              }
          }
          return null;
        },

        getKeyData : function() {
            this.fieldInfo.isDefault = this.element.find("[name=fd_"+this.randomName + "_fieldIsDefault]").val();
            return this.fieldInfo;
        }
    })

    module.exports = FieldGenerator;
})