/**
 * 待办部件
 */
define(function(require, exports, module) {
    var $ = require('lui/jquery'),
        render = require('lui/view/render');
    var dialog = require("lui/dialog");
    var modelingLang = require("lang!sys-modeling-base");
    var basePortlet = require("sys/modeling/base/mobile/design/custom/basePortlet");
    var listAttrBase = require("sys/modeling/base/mobile/design/custom/portlets/listAttrBase");

    var ToDoPortlet =  basePortlet.BasePortlet.extend({
        initProps: function ($super, cfg) {
            $super(cfg);
            this.config = cfg;
            this.container = cfg.container;
            this.type = cfg.type;      //待办类型
            this.parent=cfg.parent;
            this.tmpData = {
                attr : {
                    title:{
                        isHide:"1"
                    },
                    todoType:"",
                    listView : {
                        validate : {},	// 校验器
                        value : {}
                    }
                }
            };
            this.data = $.extend(this.tmpData,cfg.data || {});
        },
        startup : function($super, cfg) {
            var self = this;
            this.setRender(new render.Template({
                src : "/sys/modeling/base/mobile/design/custom/portlets/todoPortletRender.html#",
                parent : this
            }));
            this.render.startup();
            $super(cfg);
        },
        doRender: function($super, cfg){
            var self = this;
            $super(cfg);
            this.element.appendTo(this.viewContainer).addClass("panel-tab-main-view");

            var attrConfig = {parent : this,widgetKey:"todo",area:this.element.find(".mobileAttrPanel")};
            attrConfig.data = this.data.attr.listView.value;
            this.listAttr = new listAttrBase.ListAttrBase(attrConfig);
            this.listAttr.startup();
            this.listAttr.draw();
            //事件
            this.addEvent();
            this.initByStoreData(this.data);
            if(this.parent.wgtPortlet === this){
                this.element.siblings().hide();
            }else{
                this.element.hide();
            }
        },
        triggerActiveWgt:function($super) {
            $super();
        },
        draw : function($super, cfg){
            $super(cfg);
            this.render.get(this.data);
        },
        addEvent : function($super){
            var self = this;
            //待办数据来源按钮增加随机数
            this.element.find("[name='todoType']").each(function () {
                $(this).attr("name","todoType_"+self.randomName);
            })
            //待办数据来源切换事件
            this.element.find("[name*='todoType_"+self.randomName+"']").on("change",function () {
                if($(this).val() == "1"){
                    //自定义
                    self.element.find(".mobileAttrPanel").show();
                    self.element.find(".mobileAttrPanel").parent("tr").prev().show();
                    self.element.find("[name $='_listView']").attr("data-type","validate");
                }else{
                    self.element.find(".mobileAttrPanel").hide();
                    self.element.find(".mobileAttrPanel").parent("tr").prev().hide();
                    self.element.find("[name $='_listView']").attr("data-type","hidden");
                }
                self.validateElement(self.element.find("[name $='_listView']")[0]);
            });
            $super();
        },
        //获取多语言资源
        getModelingLang :function (){
            return modelingLang;
        },

        addValidateElements: function($super,element,validateName){
            $super(element,validateName);
            if(this.data.attr.todoType === "0"
                && $(element).attr("name")  === this.listAttr.data.uuId + "_listView"){
                $(element).attr("data-type","hidden");
            }
        },

        validate:function($super) {
            var isPass = $super();

            return isPass;
        },
        initByStoreData : function(storeData) {
            if (JSON.stringify(storeData) == "{}") {
                return;
            }
            this.element.find("[name='fdTitle']").val(storeData.attr.title.value);
            if(storeData.attr.title.isHide === "1"){
                this.element.find("[name='fdTitleIsHide']").attr("checked","checked");
            }else{
                this.element.find("[name='fdTitleIsHide']").removeAttr("checked");
            }
            storeData.attr.todoType = storeData.attr.todoType || "1";
            this.element.find("[name*='todoType_"+this.randomName+"']").each(function () {
                if($(this).val() === storeData.attr.todoType){
                    $(this).prop("checked",true);
                }else{
                    $(this).removeAttr("checked");
                }
            })
            this.element.find("[name*='todoType_"+this.randomName+"']:checked").trigger($.Event("change"));
            this.setLeftShow();
        },
        getKeyData : function() {
            var keyData = {
                attr : {
                    title:{},
                    todoType:"",
                    listView: {
                        validate : {},	// 校验器
                        value : {}
                    }
                }
            };
            keyData.attr.title.value =  this.element.find("[name='fdTitle']").val();
            keyData.attr.title.isHide =  this.element.find("[name='fdTitleIsHide']").is(":checked") ? "1" : "0";
            keyData.attr.todoType =  this.element.find("[name*='todoType_"+this.randomName+"']:checked").val();
            if(keyData.attr.todoType === "1"){
                keyData.attr.listView.value = this.listAttr.getKeyData();
                keyData.attr.listView.value.sourceType = "custom";
            }else{
                keyData.attr.listView.value.sourceType = "system";
            }
            return keyData;
        },
        //实现左右互联
        setLeftShow : function() {
            var self = this;
            var $currentPreviewWgt = this.parent.element.find("[data-id='"+this.randomName+"']");
            var data = this.getKeyData();
            var $title = $currentPreviewWgt.find(".modelAppSpaceWidgetDemoTypeTitle");
            if(data.attr.title.isHide === "1"){
                $title.hide();
            }else{
                $title.text(data.attr.title.value);
                $title.show();
            }
        }
    })
    module.exports = ToDoPortlet;
})
