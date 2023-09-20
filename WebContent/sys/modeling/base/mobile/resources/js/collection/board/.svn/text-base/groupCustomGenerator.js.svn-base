/**
 * 移动列表视图的统计行组件
 */
define(function(require, exports, module) {

    var $ = require('lui/jquery'),
        base = require('lui/base'),
        topic = require('lui/topic'),
        headGenerator = require('sys/modeling/base/mobile/resources/js/headGenerator'),
        nameInput = require('sys/modeling/base/mobile/resources/js/nameInput'),
        groupCustomSettingButton = require('sys/modeling/base/mobile/resources/js/collection/board/groupCustomSettingButton');
    var modelingLang = require("lang!sys-modeling-base");

    var GroupCustomGenerator = headGenerator.extend({

        initProps: function($super, cfg) {
            $super(cfg);
            this.randomName = "fd_" + parseInt(((new Date().getTime()+Math.random())*10000)).toString(16);
            this.container = cfg.container || null;
            this.storeData = cfg.data || {};
            this.channel = cfg.channel;
            this.type = "groupCustom";
            this.nameWgt = null;
            this.groupCustomSettingButton = null;
            this.modelDict = listviewOption.baseInfo.modelDict;
        },

        draw : function($super, cfg){
            var rowIndex = this.container.find(".groupCustomItem").length+1;
            var $groupItem = $("<div class='item groupCustomItem sortItem'></div>").appendTo(this.container);
            $groupItem.attr("index", rowIndex - 1);
            this.element = $groupItem;
            var self = this;
            var html = ""
            this.content = $("<div class='model-edit-view-oper' data-lui-position='fdGroup-"+(rowIndex-1)+"' onclick=switchSelectPosition(this,'right')></div>");
            $groupItem.append(this.content);
            $super(cfg);
            //内容
            html += "<div class='model-edit-view-oper-content'>";
            html += "<ul class='list-content'>";
            html += "</ul>";
            html += "</div>";
            this.content.append(html);
            //名称输入
            html = "<li class='model-edit-view-oper-content-item field first-item'><div class='item-title'>名称</div></li>";
            $groupItem.find("ul.list-content").eq(0).append(html);
            var $field = $groupItem.find("ul.list-content").find("li.field").eq(0);
            var $fieldTd = $("<div class='item-content' />").appendTo($field);
            var value = self.storeData.name || modelingLang["listview.category.basis"];
            this.nameWgt = new nameInput({container: $fieldTd,channel :self.channel,value:value,className:"groupCustomItemName" });
            this.nameWgt.draw();
            this.nameWgt.element.find("input").bind("blur",function() {
                var $input = $(this);
                var val = $input.val();
                var validateDom = $(this).parents("li").eq(0).find("#advice-_validate_"+self.randomName)[0];
                var validateRepeatDom = $(this).parents("li").eq(0).find("#advice-_validate_repeat"+self.randomName)[0];
                if(!val){
                    //提示不能为空
                    if(!validateDom){
                        var html = getValidateHtml($input.data("subject")||modelingLang["listview.category.basis"],self.randomName);
                        $(this).parents("li").eq(0).find(".validation-advice").remove();
                        $(this).parents("li").eq(0).append(html);
                    }else{
                        $(validateDom).show();
                    }
                }else{
                    $(validateDom).hide();
                    var flag = false;
                    for (var i = 0; i < self.parent.groupCustomCollection.length; i++) {
                        var groupCustom = self.parent.groupCustomCollection[i];
                        if (groupCustom != self){
                            var nameValue = groupCustom.nameWgt.getKeyData();
                            if (nameValue == val){
                                flag = true;
                                break;
                            }
                        }
                    }
                    if (flag) {
                        if(!validateRepeatDom){
                            var html = getValidateHtml($input.data("subject")||modelingLang["listview.category.basis"],"repeat"+self.randomName,"不能重复");
                            $(this).parents("li").eq(0).find(".validation-advice").remove();
                            $(this).parents("li").eq(0).append(html);
                        }else{
                            $(validateRepeatDom).show();
                        }
                    }else {
                        $(validateRepeatDom).hide();
                    }
                }
            })
            this.defaultItem = $("<div class='model-edit-view-oper-head-default-item'>默认</div>");
            this.head.append(this.defaultItem);
            var $fieldIsDefault = $('<input type="hidden" />');

            $fieldIsDefault.attr("name","fd_"+this.randomName + "_fieldIsDefault");
            $fieldIsDefault.val(this.storeData.isDefault);
            this.head.append($fieldIsDefault);
            if(this.storeData.isDefault == "1"){
                this.defaultItem.addClass("active");
            }
            this.defaultItem.click(function(){
                self.setGroupDefault();
            });
            //设置按钮
            this.groupCustomSettingButton = new groupCustomSettingButton({
                container: $groupItem.find("ul.list-content").eq(0),
                storeData:self.storeData,
                modelDict : this.modelDict,
                parent:self,
                channel: this.channel
            });
            this.groupCustomSettingButton.draw();

        },

        setGroupDefault:function() {
            var groupCollection = this.parent.groupCustomCollection;
            for (var i = 0; i < groupCollection.length; i++) {
                var groupWgt = groupCollection[i];
                if(groupWgt.cid == this.cid){
                    groupWgt.element.find(".model-edit-view-oper-head-default-item").addClass("active");
                    groupWgt.element.find("[name=fd_"+groupWgt.randomName + "_fieldIsDefault]").val("1");
                }else{
                    groupWgt.element.find(".model-edit-view-oper-head-default-item").removeClass("active");
                    groupWgt.element.find("[name*=fd_"+groupWgt.randomName + "_fieldIsDefault]").val("0");
                }
            }

        },

        getKeyData : function(){
            var keyData = {};
            var groupRules = this.groupCustomSettingButton.getKeyData();
            keyData.name = this.nameWgt.getKeyData();
            keyData.groupRules = groupRules;
            keyData.isDefault = this.element.find("[name=fd_"+this.randomName + "_fieldIsDefault]").val();
            return keyData;
        },

    })

    module.exports = GroupCustomGenerator;

})