/**
 * 新建规则生成器
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var topic = require("lui/topic");
    var dialog = require('lui/dialog');
    var formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder");
    var whereRecordGenerator = require("sys/modeling/base/relation/trigger/behavior/js/whereRecordGenerator");
    var modelingLang = require("lang!sys-modeling-base");

    var PreModelWhereGenerator = base.Component.extend({

        initProps: function ($super, cfg) {
            $super(cfg);
            this.parent = cfg.parent;
            this.isShow = true;
            this.appId= cfg.parent.appId;
            this.whereWgtCollection = [];
            this.preModelWhereTmpStr = cfg.preModelWhereTmpStr;
            this.modelId = cfg.modelId;
            this.modelName = cfg.modelName;
            this.$ruleTr = cfg.$ruleTr;
            this.draw();
        },
        draw: function () {
            this.initTargetInfo();
            this.buildPreModelWhere();
        },
        buildPreModelWhere : function() {
            var self = this;
            var $tr = $(this.preModelWhereTmpStr);

            //查询条件切换事件
            $tr.find(".preModelWhereTypediv input[type='radio']").on("change", function () {
                var $whereBlockDom = $tr.find(".view_field_pre_model_where_div");
                if (this.value === "3") {
                    $whereBlockDom.hide();
                } else {
                    $whereBlockDom.show();
                }
            });
            var $whereDiv = $tr.find(".view_field_pre_model_where_div");
            $tr.find("[name='fdPreModelWhereType']").attr("name", "fdPreModelWhereType" + this.valueName);
            // 添加事件
            $whereDiv.find(".table_opera").on("click", function (e) {
                e.stopPropagation();
                if ($.isEmptyObject(self.getTargetData())) {
                    dialog.alert(modelingLang['behavior.select.pre.form.first']);
                    return;
                }
                var whereWgt = new whereRecordGenerator.WhereRecordGenerator({parent: self});
                var $whereTable = $tr.find(".view_field_pre_model_where_table");
                whereWgt.draw($whereTable);
            });
            self.element = $tr;
            self.parent.addWgt(this, "preModelWhere");
            this.$ruleTr.after($tr);
        },

        initTargetInfo:function () {
            var self = this;
            var url = Com_Parameter.ContextPath + "sys/modeling/base/modeling.do?method=getModelWidget&modelId=" + this.modelId;
            $.ajax({
                url: url,
                type: "get",
                async: false,
                success: function (data, status) {
                    data = data?JSON.parse(data):null;
                    self.formatTargetData(data);
                    var preModelData = [];
                    if(data && data.data){
                        for (var key in data.data) {
                            var field = data.data[key];
                            //拷贝对象值
                            var ele = $.extend({}, field);
                            ele.name = self.modelId + "#" + ele.name;
                            ele.useLabel = self.modelName + "#" + (ele.fullLabel || ele.label);
                            preModelData.push(ele);
                        }
                    }
                    topic.channel("modelingBehavior").publish("preModelData.load", {
                        "data": preModelData,
                        "modelId": self.modelId,
                        "modelName":self.modelName
                    });
                }
            });
        },
        getTargetData:function (){
            return this.targetData;
        },

        formatTargetData : function(data){
            this.targetData = data;
            this.targetData.modelId = this.modelId;
            this.targetData.modelName= this.modelName;
        },
        addWgt: function (wgt, type) {
            if (type === "where") {
                this.whereWgtCollection.push(wgt);
            }
        },

        deleteWgt: function (wgt, type) {
            var collect = [];
            if (type === "where") {
                collect = this.whereWgtCollection;
            }
            for (var i = 0; i < collect.length; i++) {
                if (collect[i] === wgt) {
                    collect.splice(i, 1);
                    break;
                }
            }
        },
        destroy : function($super) {
            this.parent.deleteWgt(this,"preModelWhere");
            this.clear();
            topic.channel("modelingBehavior").publish("preModelData.remove", {
                "modelId": this.modelId,
                "modelName":this.modelName
            });
            $super();
        },
        /**
         * 整个模块清理
         */
        clear: function () {
            for (var i = 0; i < this.whereWgtCollection.length; i++) {
                this.whereWgtCollection[i].destroy();
            }
            this.element.remove();
        },
        hide: function () {
            var $validate= this.$table.find(".modeling-validate-create");
            $validate.attr("modeling-validation","");
            this.element.hide();
            this.isShow = false;
        },
        show: function () {
            var $validate= this.$table.find(".modeling-validate-create");
            $validate.attr("modeling-validation",this.creator_validation );
            this.element.show();
            this.isShow = true;
        },
        getKeyData: function () {
            var self = this;
            if (!self.isShow) {
                return null;
            }

            var keyData = {};
            keyData.id = this.modelId;
            keyData.name= this.modelName;
            keyData.preModelWhere = [];
            //明细表查询条件类型
            keyData.preModelWhereType = $("input[name='fdPreModelWhereType" + this.valueName+"']:checked").val();
            for (var i = 0; i < this.whereWgtCollection.length; i++) {
                var whereWgt = this.whereWgtCollection[i];
                var whereWgtKeyData = whereWgt.getKeyData();
                if (!whereWgtKeyData || keyData.preModelWhereType == "2") {
                    continue;
                }
                // 索引，用来进来记录排序，暂无用
                whereWgtKeyData.idx = i;
                keyData.preModelWhere.push(whereWgtKeyData);
            }
            return keyData;
        },

        initByStoreData: function (storeData) {
            var self = this;
            if(storeData && storeData.hasOwnProperty("preModelWhere")){
                var storeWhere = storeData.preModelWhere;
                for (var i = 0; i < storeWhere.length; i++) {
                    var whereWgt = new whereRecordGenerator.WhereRecordGenerator({parent: self});
                    var $whereTable = self.element.find(".view_field_pre_model_where_table");
                    whereWgt.draw($whereTable);
                    whereWgt.initByStoreData(storeWhere[i]);
                }
            }
        },

    })
    exports.PreModelWhereGenerator = PreModelWhereGenerator;

})
