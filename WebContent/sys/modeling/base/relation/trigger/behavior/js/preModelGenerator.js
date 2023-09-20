/**
 * 新建规则生成器
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var dialog = require('lui/dialog');
    var formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder");
    var preModelWhereGenerator = require("sys/modeling/base/relation/trigger/behavior/js/preModelWhereGenerator");
    var modelingLang = require("lang!sys-modeling-base");

    var PreModelGenerator = base.Component.extend({

        initProps: function ($super, cfg) {
            $super(cfg);
            this.prefix = cfg.parent.prefix;
            this.parent = cfg.parent;
            this.$table = cfg.$table;
            this.isShow = true;
            this.appId= cfg.parent.parent.appId;
            this.preModelWhereWgtCollection = [];
            this.draw();
        },
        draw: function () {
            this.buildPreModel(this.$table);
        },
        buildPreModel: function ($table) {
            var self = this;
            this.$ruleTr = $(this.parent.parent.preModelTmpStr);
            this.$ruleTr.find(".modelPreNameDiv").on("click",function(e) {
                e.stopPropagation();
                if ($.isEmptyObject(self.parent.getTargetData())) {
                    dialog.alert(modelingLang['behavior.select.target.form.first']);
                    return;
                }
                self.selectModel();
            })
            $table.append(this.$ruleTr);
        },
        selectModel:function () {
            var self = this;
            dialog.iframe("/sys/modeling/base/relation/import/model_select.jsp?appId="+this.appId, "选择表单",
                function (value) {
                    if (value) {
                        self.clearAllWgt();
                        $(".modelPreNameBox").html(value.fdName || "");
                        $("[name='modelPreId']").val(value.fdId || "");
                        if (value.fdId) {
                            var fdName = $(".modelPreNameBox").text();
                            var whereWgt = new preModelWhereGenerator.PreModelWhereGenerator({parent: self,$ruleTr:self.$ruleTr,modelId:value.fdId,modelName:fdName,preModelWhereTmpStr:self.parent.parent.preModelWhereTmpStr});
                        }
                    }
                }, {
                    width: 1010,
                    height: 600
                });
        },
        addWgt: function (wgt, type) {
            if (type === "preModelWhere") {
                this.preModelWhereWgtCollection.push(wgt);
            }
        },

        deleteWgt: function (wgt, type) {
            var collect = [];
            if (type === "preModelWhere") {
                collect = this.preModelWhereWgtCollection;
            }
            for (var i = 0; i < collect.length; i++) {
                if (collect[i] === wgt) {
                    collect.splice(i, 1);
                    break;
                }
            }
        },
        /**
         * 整个模块清理
         */
        clear: function () {
            for (var i = 0; i < this.preModelWhereWgtCollection.length; i++) {
                this.preModelWhereWgtCollection[i].destroy();
            }
            this.element.remove();
        },
        clearAllWgt:function (){
            for (var i = 0; i < this.preModelWhereWgtCollection.length; i++) {
                this.preModelWhereWgtCollection[i].destroy();
            }
        },
        hide: function () {
            this.element.hide();
            this.isShow = false;
        },
        show: function () {
            this.element.show();
            this.isShow = true;
        },
        getKeyData: function () {
            var self = this;
            if (!self.isShow) {
                return null;
            }

            var keyData = [];
            for (var i = 0; i < this.preModelWhereWgtCollection.length; i++) {
                var preModelWhereWgt = this.preModelWhereWgtCollection[i];
                var preModelWhereWgtKeyData = preModelWhereWgt.getKeyData();
                if (!preModelWhereWgtKeyData) {
                    continue;
                }
                // 索引，用来进来记录排序，暂无用
                preModelWhereWgtKeyData.idx = i;
                keyData.push(preModelWhereWgtKeyData);
            }
            return keyData;
        },

        initByStoreData: function (storeData) {
            var self = this;
            if (storeData && storeData.hasOwnProperty("preModel")) {
                var preModelData = storeData.preModel;
                for (var i = 0; i < preModelData.length; i++) {
                    var whereData = preModelData[i];
                    var whereWgt = new preModelWhereGenerator.PreModelWhereGenerator({
                        parent: self,
                        $ruleTr:self.$ruleTr,
                        modelId:whereData.id,
                        modelName:whereData.name,
                        preModelWhereTmpStr:self.parent.parent.preModelWhereTmpStr
                    });
                    whereWgt.initByStoreData(whereData);
                }
            }
        },

    })
    exports.PreModelGenerator = PreModelGenerator;

})
