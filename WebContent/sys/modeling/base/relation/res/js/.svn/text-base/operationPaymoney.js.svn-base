/**
 * 排序生成器
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var modelingLang = require("lang!sys-modeling-base");
    var OperationPaymoney = base.Component.extend({
        //lines,
        initProps: function ($super, cfg) {
            $super(cfg);
            this.feildName = cfg.fieldName;
            this.pcfg = cfg.pcfg;
            this.build();
        },
        build: function () {
            var self = this;
            var $pele = self.pcfg.container;
            var $ele = $pele.find("[mdlng-rltn-property=\"" + self.feildName + "\"]");
            self.element = $ele
            self.initPaymoney();

        },
        initPaymoney:function (){
            var self = this;
            var $fdPayMoneydiv = self.element.find("[mdlng-rltn-prprty-value=\"" + self.feildName + "\"]").find(".model-mask-panel-table-base");
            var $moneySelect = $("<select class='fdPaymoneySelect' onchange='checkPaymoneyValue()'></selct>");
            $moneySelect.append("<option value=''>"+modelingLang['relation.please.choose']+"</option>");
            var mainSource = self.pcfg.sourceData;
            if (mainSource && mainSource) {
                for (var controlId in mainSource) {
                    var info = mainSource[controlId];
                    var fullLabel = info.fullLabel||info.label;
                    $moneySelect.append("<option title='" + fullLabel + "'  value='" + info.name + "' data-property-type='" + info.type + "'>" + info.label + "</option>");
                }
            }
            $fdPayMoneydiv.append($moneySelect);
            return $moneySelect;
        },
        draw: function () {
        },
        destroy: function ($super, cfg) {

        },

        getKeyData: function () {
            var self = this;
            var $fdPayMoneydiv = self.element.find("[mdlng-rltn-prprty-value=\"" + self.feildName + "\"]").find(".model-mask-panel-table-base");
            var keyData = {}
            var $option = $fdPayMoneydiv.find(".fdPaymoneySelect option:selected");
            if ($option.val() === "") {
                return null;
            }
            keyData.type = $option.attr("data-property-type");
            keyData.value = $option.val();
            keyData.text = $option.text();
            return keyData;
        },
        initByStoreData: function (data) {
            if (!data) {
                return
            }
            var self = this;
            var $fdPayMoneydiv = self.element.find("[mdlng-rltn-prprty-value=\"" + self.feildName + "\"]").find(".model-mask-panel-table-base");
            var $fdPayMoneySelect = $fdPayMoneydiv.find(".fdPaymoneySelect");
            $fdPayMoneySelect.val(data.value);

        }
    });

    exports.OperationPaymoney = OperationPaymoney;
});
