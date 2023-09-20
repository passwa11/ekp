/**
 * 移动列表视图的下拉组合控件
 */

define(function(require, exports, module) {

    var $ = require('lui/jquery'),
        topic = require('lui/topic'),
        base = require('lui/base');

    var SelectUnion = base.Component.extend({

        initProps: function($super, cfg) {
            $super(cfg);
            this.container = cfg.container || null;
            this.ownerType = cfg.type || "normal";	// 该属性用于下拉选项change时传递的类型
            this.options = cfg.options || [];	// {fieldText:xxx,field:xxx,fieldType:xxxx}
            this.value = cfg.value || "";

            this.tmpOptions = this.options;	// 暂存最后select的选项
            this.lastSelect = null;	// 记录当前最后一个select
            this.channel = cfg.channel;
        },

        draw : function($super, cfg){
            $super(cfg);
            this.element = $("<div class='select_union' />").appendTo(this.container);
            if(this.ownerType === "order"){
                //#123028 排序项创建者屏蔽fdId和fdName,后台ListViewHQLUtil方法拼接orderby的时候也会把后面的fd和fdName去掉
                var valArr = new Array();
                valArr[0] = this.value.split("|")[0];
            }else{
                var valArr = this.value.split("|");
            }
            for(var i = 0;i < valArr.length;i++){
                var val = valArr[i];
                // 选项为空，则重新请求
                if(this.tmpOptions.length === 0){
                    this.updateTmpOptions();
                }
                var $select = this.buildSelect(val,this.tmpOptions);
                this.element.append($select);
                // 清空选项
                this.tmpOptions = [];
            }
            this.lastSelect.trigger($.Event("change"));
        },

        buildSelect : function(val,options){
            var self = this;
            var $select = $("<select class='inputsgl width45 marginWidth selectCover'/>");
            var optionHtml = "";
            for(var i = 0;i < options.length;i++){
                var option = options[i];
                //#129636 移动端排序项屏蔽明细表字段和地址本，137538解除屏蔽
                if(option.isSubTableField == "true" || (option.fieldType.indexOf("com.landray.kmss.sys.organization") > -1 && this.ownerType === "order")){
                    continue;
                }
                optionHtml += "<option value='"+ option.field +"' data-option-type='"+ option.fieldType +"'";
                if(val === option.field){
                    optionHtml += " selected ";
                }
                optionHtml += ">"+ option.fieldText +"</option>";
            }

            $select.append(optionHtml);

            $select.on("change",function(){
                $(this).nextAll().remove();
                self.lastSelect = $(this);
                var fieldType = $(this).find("option:selected").attr("data-option-type");
                if(fieldType && fieldType.indexOf("com.landray.kmss") > -1){
                    self.updateTmpOptions(fieldType);
                    //#123028 排序项创建者屏蔽fdId和fdName，137538解除屏蔽
                    if(this.ownerType != "order"){
                        $select.after(self.buildSelect(null, self.tmpOptions).addClass("positionCover"));
                    }
                }
                var selectedText = self.getFieldText();
                if (self.channel) {
                    topic.channel(self.channel).publish("field.change",{dom:self.lastSelect, type:self.ownerType, wgt:self, text: selectedText});
                } else {
                    topic.channel("modeling").publish("field.change",{dom:self.lastSelect, type:self.ownerType, wgt:self, text: selectedText});
                }

            });

            // 更新最后一个下拉框
            self.lastSelect = $select;
            return $select;
        },

        updateTmpOptions : function(type){
            var self = this;
            if(!type){
                type = self.lastSelect.find("option:selected").attr("data-option-type");
            }
            $.ajax({
                url : Com_Parameter.ContextPath+"sys/modeling/base/modelingAppListview.do?method=getDictAttrByModelName&modelName=" + type,
                type : 'get',
                async : false,//是否异步
                success : function(data){
                    self.tmpOptions = $.parseJSON(data);
                }
            })
        },

        getCurType : function(){
            return this.lastSelect.find("option:selected").attr("data-option-type");
        },

        getFieldValue : function(){
            var fieldValue = [];
            this.element.find("select").each(function(index, dom){
                fieldValue.push($(dom).val());
            });

            return fieldValue.join("|");
        },

        getFieldType : function(){
            var fieldType = [];
            this.element.find("select").each(function(index, dom){
                fieldType.push($(dom).find("option:selected").attr("data-option-type"));
            });

            return fieldType.join("|");
        },

        getFieldText : function(){
            var fieldText = [];
            this.element.find("select").each(function(index, dom){
                fieldText.push($(dom).find("option:selected").text());
            });

            return fieldText.join("|");
        },

        getCurSelectInfo : function(){
            var prevSelect = this.lastSelect;
            // 查找最前面的下拉框
            while(prevSelect){
                if(prevSelect.prev().length > 0){
                    prevSelect = prevSelect.prev();
                }else{
                    break;
                }
            }
            var field = prevSelect.find("option:selected").val();
            return this.getFieldInfoByField(field);
        },

        getFieldInfoByField : function(field){
            var rs = null;
            for(var i = 0;i < this.options.length;i++){
                var option = this.options[i];
                if(option && option.field === field){
                    rs = option;
                    break;
                }
            }
            return rs;
        }
    })
    module.exports = SelectUnion;
})