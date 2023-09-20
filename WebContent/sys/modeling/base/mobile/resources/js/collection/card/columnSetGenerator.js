/**
 * 移动列表视图的排序行组件
 */
define(function(require, exports, module) {

    var $ = require('lui/jquery'),
        base = require('lui/base'),
        topic = require('lui/topic'),
        popup = require('lui/popup');

    var ColumnSetGenerator = base.Component.extend({

        initProps: function($super, cfg) {
            $super(cfg);
            this.container = cfg.container || null;
            this.channel = cfg.channel;
            this.storeData = cfg.data || "4";
        },

        draw : function($super, cfg){
            $super(cfg);
            this.element = $("#" + this.channel + "_columnset", this.container);
            if(this.storeData) {
                this.container.find("[name='fdColumnSetting']").val(this.storeData);
                this.setClass(this.element, this.storeData);
            }
            var self = this;
            this.element.find("[name='columnset']").each(function (index, obj){
                $(obj).on("click",function() {
                    self.changeColumnSetting(this, 'fdColumnSetting' ,$(this).attr("value"));
                    topic.publish("preview.refresh", {"key": self.channel});
                    var context = $("#modeling-pam-content-pc");
                    window.changeRightContentView("design");
                    $("[data-lui-position]", context).removeClass("active");
                    $("[data-lui-position='fdDisplay']", context).addClass("active");
                    //topic.publish("switchSelectPosition",{'dom':self.element, key: self.channel});
                });
            });
        },

        changeColumnSetting : function(obj, name, value){
            var curVal = $("[name='"+name+"']").val();
            if(curVal == value){
                return;
            }
            if(!$(obj).hasClass('selected')){
                $(obj).addClass('selected').siblings().removeClass('selected');
            }
           // this.setClass(radioObj, value);
            $("[name='"+name+"']").val(value);
        },

        setClass: function(radioObj, value) {
            if(value == 2){
                $(radioObj).find("li[value='4']").removeClass("selected");
                $(radioObj).find("li[value='5']").removeClass("selected");
                $(radioObj).find("li[value='2']").addClass("selected");
            }else if(value == 4){
                $(radioObj).find("li[value='4']").addClass("selected");
                $(radioObj).find("li[value='5']").removeClass("selected");
                $(radioObj).find("li[value='2']").removeClass("selected");
            }else{
                $(radioObj).find("li[value='4']").removeClass("selected");
                $(radioObj).find("li[value='5']").addClass("selected");
                $(radioObj).find("li[value='2']").removeClass("selected");
            }
        },

        getKeyData : function(){
            var fdColumnSetting =  this.container.find("[name='fdColumnSetting']").val();
            return fdColumnSetting;
        }
    })

    module.exports = ColumnSetGenerator;

})