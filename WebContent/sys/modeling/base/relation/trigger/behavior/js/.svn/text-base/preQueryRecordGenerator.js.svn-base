/**
 * 查询条件生成器
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var dialog = require('lui/dialog');
    var modelingLang = require("lang!sys-modeling-base");
    var formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder");
    var validatorGenerator = require("sys/modeling/base/relation/trigger/behavior/js/validatorGenerator")

    var PreQueryRecordGenerator = base.Component.extend({

        initProps: function ($super, cfg) {
            $super(cfg);
            this.valueName = "fd_" + parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16);
            this.currentData = {};
            this.xformId = cfg.parent.xformId || "";
            this.appId = cfg.parent.appId || "";
            this.parent = cfg.parent;
        },
        fieldTypes: {
            "count": {
                "name": "条目数"
            },
            "firstRow": {
                "name": "首行记录"
            },
            "sum": {
                "name": "总数"
            },
            "max": {
                "name": "最大值"
            },
            "min": {
                "name": "最小值"
            },
            "avg": {
                "name": "平均值"
            }
        },

        draw: function (container) {
            var self = this;
            self.element = $("<tr class='view_field_tr'></tr>");
            // 字段td
            self.$fieldTd = $("<td ></td>");
            var $fieldInput = $("<div class=\"pre_query_content\">\n" +
                "                        <div onclick=\"\"\n" +
                "                             class=\"model-mask-panel-table-show\">\n" +
                "                            <p class=\"preQueryTargetNameBox\"></p>\n" +
                "                        </div>\n" +
                "                    </div>" +
                "<span class=\"txtstrong\">*</span>");

            self.$fieldTd.append($fieldInput);

            $fieldInput.on("click",function (){
                self.setTargetModel();
            })

            // 返回值td
            self.$valueTd = $("<td></td>");

            //
            self.$nickNameTd = $("<td></td>");
            var $nickName = $("<input type='text' name='" + this.valueName + "' class='inputsgl' style='width:130px'/>");
            $nickName.on("blur",function() {
                if(self.validators()){
                    self.buildTarget();
                }
            })
            self.$nickNameTd.append($nickName);
            self.$nickNameTd.append($("<span class=\"txtstrong\">*</span>"));

            // 操作td
            var $delTd = $("<td></td>");
            //设置
            var $setSpan = $("<span class='table_opera'>设置</span>");
            $setSpan.on("click", function () {
                self.setTargetModel();
            });
            $delTd.append($setSpan);
            //删除
            var $delSpan = $("<span class='table_opera'>"+modelingLang['modeling.page.delete']+"</span>");
            $delSpan.on("click", function () {
                self.destroy();
            });
            $delTd.append($delSpan);

            self.element.append(self.$fieldTd);
            self.element.append(self.$nickNameTd);
            self.element.append(self.$valueTd);
            self.element.append($delTd);
            container.append(self.element);
            self.parent.addWgt(this, "preQuery");
        },

        setTargetModel:function () {
            var self = this;
            dialog.iframe("/sys/modeling/base/relation/trigger/behavior/behavior_preQuery_dialog.jsp?appId="+this.appId, modelingLang['behavior.select.form'],
                function (value) {
                    if (value) {
                        self.formatBackValue(value);
                    }
                }, {
                    width: 1010,
                    height: 600,
                    params : {
                        data : self.currentData,
                        xformId:self.xformId,
                        appId:self.appId
                    }
                },);
        },

        initTargetInfo:function () {
            var self = this;
            var url = Com_Parameter.ContextPath + "sys/modeling/base/modeling.do?method=getModelWidget&modelId=" + this.currentData.target.value;
            $.ajax({
                url: url,
                type: "get",
                async: false,
                success: function (data, status) {
                    data = data? $.parseJSON(data):null;
                    self.formatTargetData(data);
                    self.buildTarget();
                }
            });
        },

        formatTargetData : function(data){
            this.currentData.targetData = data;
            this.currentData.targetData.modelId = this.modelId;
        },

        formatBackValue : function(data) {
            this.currentData.backValue = data.backValue;
            this.currentData.preQueryWhere = data.preQueryWhere;
            this.currentData.whereType = data.whereType;
            if(this.currentData && this.currentData.target) {
                var oldTargetId = this.currentData.target.value;
            }
            this.currentData.target = data.target;
            this.currentData.targetData = data.targetData;
            this.buildBackValue();
            if(this.currentData && this.currentData.target) {
                this.element.find(".preQueryTargetNameBox").html(this.currentData.target.text);
                if(!this.currentData.nickName || oldTargetId != this.currentData.target.value){
                    this.element.find("[name='" + this.valueName + "']").val(this.currentData.target.text);
                }

                if(this.validators()){
                    this.buildTarget();
                }
            }
        },

        formulaBuildFields:function(preQueryData) {
            if(preQueryData && preQueryData.length>0){
                this.removeInvalidFieldList(this.currentData.oldNickName);
                formulaBuilder.initOtherFieldList(preQueryData,this.currentData.nickName,"preQuery",this.parent.prefix);
            }
        },

        removeInvalidFieldList: function (delName) {
            var otherFieldList = formulaBuilder.getOtherFieldList();
            for (var i = 0; i < otherFieldList.length; i++) {
                if ("preQuery" === otherFieldList[i].type && this.parent.prefix === otherFieldList[i].prefix) {
                    var varName = otherFieldList[i].varName;
                    if (delName == varName) {
                        otherFieldList.splice(i, 1);
                        i--;
                    }
                }
            }
        },
        destroy: function ($super, cfg) {
            this.parent.deleteWgt(this, "preQuery");
            this.removeInvalidFieldList(this.currentData.nickName);
            $super(cfg);
        },

        getNickName:function() {
            var nickName = this.element.find("[name='"+this.valueName+"']").val();
            return nickName;
        },

        getKeyData: function () {
            var keyData = {};
            keyData.target = {};
            if(this.currentData.target){
                keyData.target = this.currentData.target;
            }
            keyData.where = [];
            if(this.currentData.preQueryWhere){
                keyData.preQueryWhere = this.currentData.preQueryWhere;
            }
            keyData.whereType = "";
            if(this.currentData.whereType){
                keyData.whereType = this.currentData.whereType;
            }
            keyData.backValue = [];
            if(this.currentData.backValue){
                keyData.backValue = this.currentData.backValue;
            }
            keyData.nickName = "";
            var nickName = this.element.find("[name='"+this.valueName+"']").val();
            if(nickName){
                keyData.nickName = nickName;
            }
            return keyData;
        },

        buildBackValue: function () {
            var self = this;
            if (this.currentData.backValue) {
                var backValueStr = "";
                for (var backValueKey in this.currentData.backValue) {
                    var value = this.currentData.backValue[backValueKey];
                    if (!value) {
                        continue;
                    }
                    var name = this.fieldTypes[backValueKey].name;
                    if (typeof value === "string" && value === "1") {
                        backValueStr += name + "、";
                        continue;
                    }
                    if (!$.isEmptyObject(value)) {
                        for (var i = 0; i < value.length; i++) {
                            var item = value[i];
                            backValueStr += name + "(" + item.fullLabel + ")、";
                        }
                        continue;
                    }
                }
                if (backValueStr) {
                    backValueStr = backValueStr.substring(0, backValueStr.length - 1);
                }
                self.$valueTd.text(backValueStr);
            }
        },

        buildTarget:function (){
            if(this.currentData){
                this.currentData.oldNickName = this.currentData.nickName;
                this.currentData.nickName = this.element.find("[name='"+this.valueName+"']").val();
                var preQueryData = [];
                if(this.currentData.backValue){
                    for (var key in this.currentData.backValue) {
                        if(key === "count"){
                            if(this.currentData.backValue[key] === "1"){
                                var ele = {};
                                ele.name = this.currentData.nickName + "*count()";
                                ele.useLabel = this.currentData.nickName + "*" + this.fieldTypes[key].name;
                                ele.type = "Integer";
                                ele.fullLabel = this.fieldTypes[key].name;
                                preQueryData.unshift(ele);
                            }
                        }else if(key === "firstRow"){
                            if(this.currentData.backValue[key] === "1" && this.currentData.targetData && this.currentData.targetData.data){
                                for (var key in this.currentData.targetData.data) {
                                    var field = this.currentData.targetData.data[key];
                                    if(field.name.indexOf(".")>-1){
                                        continue;
                                    }
                                    //拷贝对象值
                                    var ele = $.extend({}, field);
                                    ele.name = this.currentData.nickName + "*" + ele.name;
                                    ele.useLabel = this.currentData.nickName + "*" + (ele.fullLabel || ele.label);
                                    preQueryData.push(ele);
                                }
                            }
                        }else {
                            var fields = this.currentData.backValue[key];
                            if(fields && fields.length > 0){
                                for (var i = 0; i < fields.length; i++) {
                                    var field = fields[i];
                                    var ele = {};
                                    ele.name = this.currentData.nickName + "*"+key + "("+field.value+")";
                                    ele.useLabel = this.currentData.nickName + "*" + this.fieldTypes[key].name +"("+field.fullLabel+")";
                                    ele.type = field.type;
                                    ele.fullLabel = this.fieldTypes[key].name +"("+field.fullLabel+")";
                                    ele.label = field.fullLabel;
                                    preQueryData.unshift(ele);
                                }
                            }
                        }
                    }
                    this.formulaBuildFields(preQueryData);
                }
            }
        },
        initByStoreData: function (storeData) {
            if(!storeData){
                return;
            }
            var self = this;
            if (storeData.hasOwnProperty("target")){
                this.currentData.target = storeData["target"];
                self.element.find(".preQueryTargetNameBox").text(this.currentData.target.text);

            }
            if (storeData.hasOwnProperty("preQueryWhere")){
                this.currentData.preQueryWhere = storeData["preQueryWhere"];
            }
            if (storeData.hasOwnProperty("whereType")){
                this.currentData.whereType = storeData["whereType"];
            }

            if (storeData.hasOwnProperty("backValue")){
                this.currentData.backValue = storeData["backValue"];
                this.buildBackValue();
            }

            if (storeData.hasOwnProperty("nickName")){
                this.currentData.nickName = storeData["nickName"];
                this.element.find("[name='"+this.valueName+"']").val(this.currentData.nickName);
            }
            self.initTargetInfo();
        },
        validators : function() {
            var validator = true;
            //目标表单为空校验
            if(!this.targetValidatorWgt && (!this.currentData.target || !this.currentData.target.value)){
                this.targetValidatorWgt = new validatorGenerator.ValidatorGenerator({
                    title:modelingLang["sysModelingBehavior.modelTarget"],
                    content:modelingLang["modeling.behavior.notEmpty"],
                    container:this.$fieldTd
                });
            }
            this.buildValidator(!this.currentData.target || !this.currentData.target.value,this.targetValidatorWgt);
            if(this.targetValidatorWgt && this.targetValidatorWgt.isShow()){
                validator = false;
            }

            //别名为空校验
            var nickName = this.element.find("[name='"+this.valueName+"']").val();
            if(!this.nickNameWgt && !nickName){
                this.nickNameWgt = new validatorGenerator.ValidatorGenerator({
                    title:modelingLang["modeling.behavior.nickName"],
                    content:modelingLang["modeling.behavior.notEmpty"],
                    container:this.$nickNameTd
                })
            }
            this.buildValidator(!nickName,this.nickNameWgt);
            if(this.nickNameWgt && this.nickNameWgt.isShow()){
                validator = false;
            }
            if(nickName){
                //别名重复校验
                var preQueryCollection = this.parent.preQueryCollection;
                var isRepeat = false;
                if(preQueryCollection && preQueryCollection.length > 0){
                    for (var i = 0; i < preQueryCollection.length; i++) {
                        var preQueryWgt = preQueryCollection[i];
                        if(preQueryWgt != this && nickName === preQueryWgt.getNickName()){
                            isRepeat = true;
                            break;
                        }
                    }
                }
                if(!this.isRepeatWgt && isRepeat){
                    this.isRepeatWgt = new validatorGenerator.ValidatorGenerator({
                        title: modelingLang["modeling.behavior.nickName"],
                        content: modelingLang["modeling.behavior.notRepeat"],
                        container: this.$nickNameTd
                    });
                }
                this.isRepeatNickName(isRepeat,this.isRepeatWgt);
                if(isRepeat){
                    validator =false;
                }
            }

            //别名格式校验，中文、字母、数字
            var re = /^([A-Za-z]|[\u4E00-\u9FA5]|[0-9])+$/;
            if(!this.formatNickNameWgt && nickName && !re.test(nickName)){
                this.formatNickNameWgt = new validatorGenerator.ValidatorGenerator({
                    title:modelingLang["modeling.behavior.nickName"],
                    content:modelingLang["modeling.behavior.formatInvalid"],
                    container:this.$nickNameTd
                })
            }
            this.buildValidator(nickName && !re.test(nickName),this.formatNickNameWgt);
            if(this.formatNickNameWgt && this.formatNickNameWgt.isShow()){
                validator = false;
            }

            return validator;
        },
        buildValidator: function (expr,weiget) {
            if (expr) {
                if(weiget && !weiget.isShow()){
                    weiget.show();
                }
            } else {
                if (weiget) {
                    weiget.hide();
                }
            }
        },
        isRepeatNickName:function (isRepeat,weiget){
            this.buildValidator(isRepeat,weiget);
        }
    });

    exports.PreQueryRecordGenerator = PreQueryRecordGenerator;
});
