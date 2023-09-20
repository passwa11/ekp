/**
 * 移动列表视图的排序行组件
 */
define(function(require, exports, module) {

    var $ = require('lui/jquery'),
        base = require('lui/base'),
        topic = require('lui/topic'),
        popup = require('lui/popup');

    var PagingSetGenerator = base.Component.extend({

        initProps: function($super, cfg) {
            $super(cfg);
            this.container = cfg.container || null;
            this.channel = cfg.channel;
            this.storeData = cfg.data || "1";
        },

        draw : function($super, cfg){
            $super(cfg);
            this.element = $("#" + this.channel + "_pagingset", this.container);
            if(this.storeData) {
                this.container.find("[name='fdPageSetting']").val(this.storeData);
                var radioObj = this.container.find("#pc_pagingset")[0];
                this.setClass(radioObj, this.storeData);
            }
            var self = this;
            this.element.find("[name='pagingset']").each(function (index, obj){
                var id = $(obj).attr("id");
                var value = $(obj).attr("value");
                var popupObj = $(obj).attr("popupObj");
                var popupConfig ={
                    "borderWidth": 5,
                    "align": "down-right",
                    "positionObject": "#paginationPosition",
                    "triggerObject":  "#" + id,
                    "triggerEvent": "mouseover",
                    "style": "background:white;overflow-x:hidden;",
                    "element": $(this).closest("td").find("[name='" + popupObj + "']")
                }
                var pagingSetPopup = new popup.Popup(popupConfig);
                pagingSetPopup.startup();
                pagingSetPopup.draw();
                var path = Com_Parameter.ContextPath;
                if (value == 1) {
                    path += "sys/modeling/base/resources/images/listview/paging_setting_default.png";
                } else if (value == 2) {
                    path += "sys/modeling/base/resources/images/listview/paging_setting_simple.png";
                } else if (value == 3) {
                    path += "sys/modeling/base/resources/images/listview/paging_setting_can_change.png";
                }
                pagingSetPopup.element.append('<img alt="" src="' + path + '"/>');
                $(obj).on("click",function() {
                    self.changePageSetting(this, 'fdPageSetting' ,$(this).attr("value"));
                });
            });
        },

        changePageSetting : function(obj, name, value){
            var curVal = $("[name='"+name+"']").val();
            if(curVal == value){
                return;
            }
            var radioObj = $(obj).parents(".view_flag_radio")[0];
            this.setClass(radioObj, value);
            $("[name='"+name+"']").val(value);
        },

        setClass: function(radioObj, value) {
            if(value == 1){
                $(radioObj).find(".view_flag_radio_no i").removeClass("view_flag_yes");
                $(radioObj).find(".view_flag_radio_last i").removeClass("view_flag_yes");
                $(radioObj).find(".view_flag_radio_yes i").addClass("view_flag_yes");
            }else if(value == 2){
                $(radioObj).find(".view_flag_radio_no i").addClass("view_flag_yes");
                $(radioObj).find(".view_flag_radio_yes i").removeClass("view_flag_yes");
                $(radioObj).find(".view_flag_radio_last i").removeClass("view_flag_yes");
            }else{
                $(radioObj).find(".view_flag_radio_no i").removeClass("view_flag_yes");
                $(radioObj).find(".view_flag_radio_last i").addClass("view_flag_yes");
                $(radioObj).find(".view_flag_radio_yes i").removeClass("view_flag_yes");
            }
        },

        getKeyData : function(){
            var fdPageSetting =  this.container.find("[name='fdPageSetting']").val();
            return fdPageSetting;
        }
    })

    module.exports = PagingSetGenerator;

})