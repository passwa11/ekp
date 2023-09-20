/**
 * 移动列表视图的名称输入控件
 */

define(function(require, exports, module) {

    var $ = require('lui/jquery'),
        topic = require('lui/topic'),
        base = require('lui/base');
    var modelingLang = require("lang!sys-modeling-base");
    var NameInput = base.Component.extend({

        initProps: function($super, cfg) {
            $super(cfg);
            this.container = cfg.container || null;
            this.value = cfg.value || "";
            this.channel = cfg.channel;
            this.className = cfg.className || "";
        },

        draw : function($super, cfg){
            $super(cfg);
            this.element = $("<div class='name_input' />").appendTo(this.container);
            var $input = this.buildInput(this.value);
            this.element.append($input);
            var $txtStrong = this.buildTxtStrong( this.element);
            this.element.append($txtStrong);
            var $inputLengthShowRight = this.buildInputLengthShowRight();
            this.element.append($inputLengthShowRight);
            var $inputLengthShowLeft = this.buildInputLengthShowLeft(this.value);
            this.element.append($inputLengthShowLeft);
            //更新头部
            var $span = this.container.parents('.model-edit-view-oper').find('.model-edit-view-oper-head').find('.model-edit-view-oper-head-title').find('span');
            $span.text(this.value);
        },

        buildInput : function(val){
            var self = this;
            if(!val){
                val = modelingLang['listview.Statistics']+ self.container.parents('.model-edit-right').find(".statisticsItem").length;
                self.value = val;
            }
            var $input = $("<input type='text' class='inputselectsgl' style='text-indent:6px;' />");
            $input.val(val);
            $input.addClass(this.className);

            $input.bind("keyup",function() {
                var val = $input.val();
                var right = 8;
                var left = right - val.length;
                var $lengthShow = $input.parent('.name_input');
                var $lengthShowLeft =  $lengthShow.find('.length_show_left');
                $lengthShowLeft.text(left);
                if(left < 0){
                    $lengthShowLeft.addClass("length_show_left_red");
                }else {
                    $lengthShowLeft.removeClass("length_show_left_red");
                }
                //赋值
                self.value = val;
                //更新头部
                var $span = $input.parents('.model-edit-view-oper').find('.model-edit-view-oper-head').find('.model-edit-view-oper-head-title').find('span');
                $span.text(val);
                topic.publish("preview.refresh", {"key": self.channel});
            })
            return $input;
        },

        buildInputLengthShowLeft : function(val){
            var $lengthShowLeft = $("<div class='length_show_left'/>");
            var right = 8;
            var left = right - val.length;
            $lengthShowLeft.text(left);
            if(left < 0){
                $lengthShowLeft.addClass("length_show_left_red");
            }

            return $lengthShowLeft;
        },

        buildInputLengthShowRight : function(){
            var $lengthShowRight = $("<div class='length_show_right'/>");
            var right = 8;
            $lengthShowRight.text("/"+right);
            return $lengthShowRight;
        },

        buildTxtStrong : function(){
            var $txtStrong = $("<span class='txtstrong'>*</span>");
            $txtStrong.css({
                "position":"relative",
                "float":"right",
                "top":"-36px",
                "right":"-10px"
            })
            return $txtStrong;
        },

        getKeyData : function (){
            return this.value;
        }
    })
    module.exports = NameInput;
})