define(function (require, exports, module) {
    var $ = require("lui/jquery");
    var base = require("lui/base");
    var topic = require('lui/topic');
    var modelingLang = require("lang!sys-modeling-base");
    var TabSelect = base.Component.extend({
        initProps : function($super, cfg) {
            $super(cfg);
            var self = this;
            self.modelId = cfg.modelId;
            self.defaultValue = cfg.defaultValue;
            self.index = cfg.index;
            self.isMobile = cfg.isMobile;
            self.isNew = cfg.isNew;
        },
        draw:function(){
            var self = this;
            if(self.isMobile == "true"){
                if(self.isNew){
                    self.$select = $("<select name=\"listTabs_mobile["+self.index+"].fdMobileListViewId\" subject=\""+modelingLang['modelingAppViewtab.showData']+"\" onchange=\"onFdMobileListviewChange(this.value, this);\" " +
                        "validate=\"required\" style=\"width:98%\" class=\"inputsgl tab_select\" __validate_serial=\"_validate_1\">");
                }else{
                    self.$select = $("<select name=\"listTabs_Form["+self.index+"].fdMobileListViewId\" subject=\""+modelingLang['modelingAppViewtab.showData']+"\" onchange=\"onFdMobileListviewChange(this.value, this);\" " +
                        "validate=\"required\" style=\"width:95%\" class=\"inputsgl tab_select\" __validate_serial=\"_validate_1\">");
                }
            }else if(self.isMobile == "false"){
                if(self.isNew) {
                    self.$select = $("<select name=\"listTabs_pc[" + self.index + "].fdListviewId\" subject=\""+modelingLang['modelingAppViewtab.showData']+"\" onchange=\"onFdListviewChange(this.value, this);\" " +
                        "validate=\"required\" style=\"width:98%\" class=\"inputsgl tab_select\" __validate_serial=\"_validate_1\">");
                }else{
                    self.$select = $("<select name=\"listTabs_Form["+self.index+"].fdListviewId\" subject=\""+modelingLang['modelingAppViewtab.showData']+"\" onchange=\"onFdListviewChange(this.value, this);\" " +
                        "validate=\"required\" style=\"width:95%\" class=\"inputsgl tab_select\" __validate_serial=\"_validate_1\">");
                }
            }
            if(self.modelId){
                self.getListViewData(self.modelId);
            }
            self.element.append(self.$select);
        },
        getListViewData:function(modelId){
            var self = this;
            var url = Com_Parameter.ContextPath + "sys/modeling/base/modelingAppCollectionView.do?method=getListViewDataByModelId&modelId="+modelId+"&isMobile="+self.isMobile;
            $.ajax({
                type:"post",
                url:url,
                data:modelId,
                success:function(data){
                   self.drawSelect(data);
                }
            });
        },
        drawSelect:function(data){
            var self = this;
            var optionHtml = "<option>"+modelingLang['modeling.page.choose']+"</option>";
            for(var i = 0;i < data.length;i++){
                optionHtml += "<option value='"+data[i].fdId+"'>"+data[i].fdName+"</option>";
            }
            this.$select.append(optionHtml);
                if(self.defaultValue){
                    self.$select.find("option").each(function () {
                        if($(this).attr("value") == self.defaultValue){
                            $(this).attr("selected","selected");
                            self.$select.trigger("change");
                        }
                    })
                }
        },
        startup : function ($super, cfg) {
            $super(cfg);
        },
        getKeyData : function() {

        },
        initByStoreData : function(fdType,fdConfig) {

        }
    })
    exports.TabSelect = TabSelect;
})