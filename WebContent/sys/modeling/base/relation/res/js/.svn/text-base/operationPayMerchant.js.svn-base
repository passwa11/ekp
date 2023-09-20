/**
 * 排序生成器
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var modelingLang = require("lang!sys-modeling-base");
    var OperationPayMerchant = base.Component.extend({
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
            self.element = $ele;
            self.initPayMerchant();

        },

        initPayMerchant:function (){
            var self = this;
            var $fdPayMerchantdiv = self.element.find("[mdlng-rltn-prprty-value=\"" + self.feildName + "\"]").find(".model-mask-panel-table-base");
            var $merchantSelect = $("<select class='fdPayMerchantSelect' onchange='checkPayMerchantValue()' ></selct>");
            $merchantSelect.append("<option value=''>"+modelingLang['relation.please.choose']+"</option>");
            var mainSource = self.pcfg.payMerchantsData;
            if (mainSource && mainSource) {
                for (var controlId in mainSource) {
                    var info = mainSource[controlId];
                    var fdMerchName = info.fdMerchName;
                    $merchantSelect.append("<option title='" + fdMerchName + "'  value='" + info.fdMerchId + "'>" + info.fdMerchName + "</option>");
                }
            }
            $fdPayMerchantdiv.append($merchantSelect);
            return $merchantSelect;
        },
        draw: function () {
        },
        destroy: function ($super, cfg) {

        },

        getKeyData: function () {
            var self = this;
            var $fdPayMerchantdiv = self.element.find("[mdlng-rltn-prprty-value=\"" + self.feildName + "\"]").find(".model-mask-panel-table-base");
            var keyData = {}
            var $option = $fdPayMerchantdiv.find(".fdPayMerchantSelect option:selected");
            if ($option.val() === "") {
                return null;
            }
            keyData.value = $option.val();
            keyData.text = $option.text();
            return keyData;
        },
        initByStoreData: function (data) {
            if (!data) {
                return
            }
            var self = this;
            var $fdPayMerchantdiv = self.element.find("[mdlng-rltn-prprty-value=\"" + self.feildName + "\"]").find(".model-mask-panel-table-base");
            var $merchantSelect = $fdPayMerchantdiv.find(".fdPayMerchantSelect");
            $merchantSelect.val(data.value);

        }
    });

    exports.OperationPayMerchant = OperationPayMerchant;
});
