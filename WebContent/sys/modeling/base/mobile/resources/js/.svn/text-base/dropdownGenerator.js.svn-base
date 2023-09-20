/**
 * 下拉选择组件
 */
define(function(require, exports, module) {

    var $ = require('lui/jquery'),
        base = require('lui/base'),
        topic = require('lui/topic');

    var DropdownGenerator = base.Component.extend({

        initProps: function($super, cfg) {
            $super(cfg);
            this.container = cfg.container || null;
            this.storeData = cfg.data || {};
            this.type = "dropdown";
            this.channel = cfg.channel || null;
            this.name = cfg.name || "";
            this.required = cfg.required || "false";
            this.buildEvents();
        },

        draw : function($super, cfg){
            $super(cfg);
        },

        buildEvents : function() {
            this.element = this.container.find(".model-mask-panel-table-select");
            // 标题下拉框样式
            var self = this;
            if(this.element.find("[name*='"+self.name+"']").val() != ""){
                this.element.find(".model-mask-panel-table-select-val").addClass("active");
            }

            this.element.on("click", function (event) {
                event.stopPropagation();
                $(this).toggleClass("active");
                if(self.required == 'true'){
                    var $input = $(this).find("input[name*='"+self.name+"']");
                    var val = $input.val();
                    if (typeof self.channel != "undefined") {
                        topic.channel(self.channel).publish("modeling."+self.name+".click");
                    }
                    var validateDom = $(this).parents("td").eq(0).find("#advice-_validate_"+self.name)[0];
                    if(!val){
                        //提示不能为空
                        if(!validateDom){
                            var html = getValidateHtml($input.data("subject")||"",self.name);
                            $(this).parents("td").eq(0).find(".validation-advice").remove();
                            $(this).parents("td").eq(0).append(html);
                        }else{
                            $(validateDom).show();
                        }
                    }else{
                        $(validateDom).hide();
                    }
                }
            });

            this.element.find(".model-mask-panel-table-option div").on("click", function () {
                var $tableSelect = $(this).parent().parent();
                var $p = $tableSelect.find(".model-mask-panel-table-select-val");
                $p.html($(this).html());

                var selectVal = $(this).attr("option-value");
                $tableSelect.find("input").val(selectVal);
                self.element.find(".model-mask-panel-table-select-val").addClass("active");
                //刷新预览
                topic.publish("preview.refresh", {"key": self.channel});
            });

            $(document).on("click", function() {
                $(self.element).removeClass("active");
            })
        },

        showElement : function() {
            this.element.show();
        },

        hideElement : function() {
            this.element.hide();
        },

        getKeyData : function(){
            var keyData = {};
            var $subject = this.element.find("input[name*='"+this.name+"']");
            var $divOption = $subject.closest(".model-mask-panel-table-select").find("[option-value='"+ $subject.val() +"']");
            keyData.text = $divOption.html();
            keyData.field = $subject.val();
            keyData.type = $divOption.attr("data-field-type");
            return keyData;
        }
    });

    exports.DropdownGenerator = DropdownGenerator;
})