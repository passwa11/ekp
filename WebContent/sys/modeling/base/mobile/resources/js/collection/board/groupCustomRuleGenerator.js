/**
 * 移动列表视图的统计行组件
 */
define(function(require, exports, module) {

    var $ = require('lui/jquery'),
        base = require('lui/base'),
        topic = require('lui/topic'),
        headGenerator = require('sys/modeling/base/mobile/resources/js/headGenerator'),
        nameInput = require('sys/modeling/base/mobile/resources/js/nameInput'),
        groupCustomRuleSettingWhere = require('sys/modeling/base/mobile/resources/js/collection/board/groupCustomRuleSettingWhere');
    var modelingLang = require("lang!sys-modeling-base");
    var GroupCustomRuleGenerator = headGenerator.extend({

        initProps: function($super, cfg) {
            $super(cfg);
            this.randomName = "fd_" + parseInt(((new Date().getTime()+Math.random())*10000)).toString(16);
            this.container = cfg.container || null;
            this.storeData = cfg.data || {};
            this.channel = cfg.channel;
            this.type = "groupCustomRule";
            this.nameWgt = null;
            this.modelDict = listviewOption.baseInfo.modelDict;
        },

        draw : function($super, cfg){
            var rowIndex = this.container.find(".groupCustomRuleItem").length+1;
            var $groupItem = $("<div class='item groupCustomRuleItem sortItem'></div>").appendTo(this.container);
            $groupItem.attr("index", rowIndex - 1);
            this.element = $groupItem;
            this.groupCustomRuleSettingWhereWgts = [];
            var self = this;
            var html = ""
            this.content = $("<div class='model-edit-view-oper' data-lui-position='fdGroup-"+(rowIndex-1)+"' onclick=switchSelectPosition(this,'right')></div>");
            $groupItem.append(this.content);
            $super(cfg);
            //内容
            var $operContent = $("<div class='model-edit-view-oper-content'>");
            var $listContent = $("<ul class='list-content'>");
            $operContent.append($listContent);
            this.content.append($operContent);
            //名称输入
            html = "<li class='model-edit-view-oper-content-item field first-item'><div class='item-title'>看板名称</div></li>";
            $groupItem.find("ul.list-content").eq(0).append(html);
            var $field = $groupItem.find("ul.list-content").find("li.field").eq(0);
            var $fieldTd = $("<div class='item-content' />").appendTo($field);
            var value = self.storeData.name || modelingLang["listview.category.item"];
            this.nameWgt = new nameInput({container: $fieldTd,channel :self.channel,value:value,className:"groupCustomRuleItemName" });
            this.nameWgt.draw();
            this.nameWgt.element.find("input").bind("blur",function() {
                var $input = $(this);
                var val = $input.val();
                var validateDom = $(this).parents("li").eq(0).find("#advice-_validate_"+self.randomName)[0];
                var validateRepeatDom = $(this).parents("li").eq(0).find("#advice-_validate_repeat"+self.randomName)[0];
                if(!val){
                    //提示不能为空
                    if(!validateDom){
                        var html = getValidateHtml($input.data("subject")||"看板名称",self.randomName);
                        $(this).parents("li").eq(0).find(".validation-advice").remove();
                        $(this).parents("li").eq(0).append(html);
                    }else{
                        $(validateDom).show();
                    }
                }else{
                    $(validateDom).hide();
                    var flag = false;
                    for (var i = 0; i < self.parent.groupCustomRuleCollection.length; i++) {
                        var groupCustom = self.parent.groupCustomRuleCollection[i];
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
                            var html = getValidateHtml($input.data("subject")||modelingLang["listview.category.item"],"repeat"+self.randomName,"不能重复");
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

            var $openRuleSetting = $("<div class='open_board_rule_setting'></div>");
            var $openRuleSettingToRight = $("<div class='open_rule_setting_to_right'></div>");
            //标题
            var $openRuleSettingTitle = $("<div class='open_rule_setting_title' title=''>显示条件</div>");

            $openRuleSetting.append($openRuleSettingTitle);
            //数据范围类型
            var $whereType = $("<div class='open_rule_setting_where_type'></div>");
            var $whereTypeLeft = $("<div class='open_rule_setting_where_type_left'>满足以下</div>");
            var $whereTypeSelect = $("<div class='open_rule_setting_where_type_select'><select " +
                " class='inputsgl' style='width:100%'><option value='0'>所有</option><option value='1'>任意</option></select></div>");
            if(this.storeData.whereType){
                $whereTypeSelect.find('select').val(this.storeData.whereType)
            }
            var $whereTypeRight = $("<div class='open_rule_setting_where_type_right'>条件的卡片显示在当前看板</div>");
            $whereType.append($whereTypeLeft);
            $whereType.append($whereTypeSelect);
            $whereType.append($whereTypeRight);

            $openRuleSetting.append($whereType);
            //数据规则
            var $whereContent = $("<div class='open_rule_setting_where_content'></div>");
            $openRuleSetting.append($whereContent);
            if(self.storeData.whereBlocks && self.storeData.whereBlocks.length){
                for (var i = 0; i < self.storeData.whereBlocks.length; i++) {
                    var $whereRule = $("<div class='open_rule_setting_where_rule'></div>");
                    var rowIndex = $whereContent.find(".open_rule_setting_where_rule").length+1;
                    $whereRule.attr("index", rowIndex - 1);
                    var groupCustomRuleSettingWhereWgt = new groupCustomRuleSettingWhere({container: $whereRule,storeData:self.storeData.whereBlocks[i],parent:self });
                    groupCustomRuleSettingWhereWgt.draw();
                    self.groupCustomRuleSettingWhereWgts.push(groupCustomRuleSettingWhereWgt);
                    $whereContent.append($whereRule);
                }
            }

            var $whereCreate = $("<div class='open_rule_setting_where_create'><span>新增</span></div>");
            $whereCreate.on("click",function () {
                var $whereRule = $("<div class='open_rule_setting_where_rule'></div>");
                var rowIndex = $whereContent.find(".open_rule_setting_where_rule").length+1;
                $whereRule.attr("index", rowIndex - 1);
                var groupCustomRuleSettingWhereWgt = new groupCustomRuleSettingWhere({container: $whereRule,storeData:{},parent:self });
                groupCustomRuleSettingWhereWgt.draw();
                self.groupCustomRuleSettingWhereWgts.push(groupCustomRuleSettingWhereWgt);
                $whereContent.append($whereRule);
            });
            $openRuleSetting.append($whereCreate);
            $operContent.append($openRuleSetting);
        },

        refreshgroupCustomRuleSettingWhereIndex : function() {
            $(".open_rule_setting_where_content").find(".open_rule_setting_where_rule").each(function(index, item){
                $(item).attr("index", index);
            });
        },

        deletegroupCustomRuleSettingWhere : function(argu){
            for(var i = 0;i < this.groupCustomRuleSettingWhereWgts.length;i++){
                if(argu.wgt === this.groupCustomRuleSettingWhereWgts[i]){
                    this.groupCustomRuleSettingWhereWgts.splice(i,1);
                    break;
                }
            }
            this.refreshgroupCustomRuleSettingWhereIndex();
        },

        delItem : function (dom) {
            var $item = $(dom).closest(".item");
            var curIndex = $item.attr("index");
            var kClass = this.parent;
            if (this.type == 'groupCustomRule') {
                var groupCustomRuleCollection = kClass.groupCustomRuleCollection;
                var wgt = groupCustomRuleCollection[curIndex];
            }else{
                return;
            }
            $item.remove();
            topic.channel(this.channel).publish(this.type + ".delete", {"wgt": wgt});
            topic.channel(this.channel).unsubscribe("field.change");
            wgt.destroy();
            //刷新预览
            if (this.isShowIcon()){
                $(this.container).find(".sortableIcon").removeClass("hide");
            } else {
                $(this.container).find(".sortableIcon").addClass("hide");
            }
            topic.publish("preview.refresh",{key: this.channel});
            return;
        },

        getKeyData : function (){
            var keyData={};
            var whereType = $(this.container).find('.open_rule_setting_where_type').find('select').val();
            keyData.whereType = whereType;
            keyData.name = this.nameWgt.getKeyData();
            var whereBlocks = [];
            for(var i=0;i<this.groupCustomRuleSettingWhereWgts.length;i++){
                whereBlocks.push(this.groupCustomRuleSettingWhereWgts[i].getKeyData());
            }
            keyData.whereBlocks = whereBlocks;
            return keyData;
        },

    })

    module.exports = GroupCustomRuleGenerator;

})