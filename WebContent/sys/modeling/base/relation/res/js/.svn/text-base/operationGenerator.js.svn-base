/**
 * 排序生成器
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var modelingLang = require("lang!sys-modeling-base");
    var OperationGenerator = base.Component.extend({
        //lines,
        initProps: function ($super, cfg) {
            $super(cfg);
            this.feildName = cfg.fieldName;
            this.pcfg = cfg.pcfg;
            this.build();
            this.lines = [];
        },
        build: function () {
            var self = this;
            var $pele = self.pcfg.container;
            var $ele = $pele.find("[mdlng-rltn-property=\"" + self.feildName + "\"]");
            $ele.find("[prprty-click=\"create\"]").each(function (idx, dom) {
                $(dom).on("click", function () {
                    self.createNewLine();
                })
            });
            self.element = $ele

        },
        createNewLine: function () {
            var self = this;
            var $valueTable = self.element.find("[mdlng-rltn-prprty-value=\"" + self.feildName + "\"]").find("tbody");
            var lineId = parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16);
            var $nline = $("<tr></tr>");
            $nline.attr("out-tr-id", lineId);
            $nline.attr("mdlng-prprty-mark", "fdOutParam");
            // 主表单字段
            var $mainTd = $("<td class='fdOutParam_td_main'></td>");
            var $mainSelect = $("<select class='fdOutParam_main' onchange='checkOutParam()'></selct>");
            $mainSelect.append("<option value=''>"+modelingLang['relation.please.choose']+"</option>");
            var mainSource = self.pcfg.sourceData;
            if (mainSource && mainSource) {
                for (var controlId in mainSource) {
                    var info = mainSource[controlId];
                    var fullLabel = info.fullLabel||info.label;
                    $mainSelect.append("<option title='" + fullLabel + "'  value='" + info.name + "' data-property-type='" + info.type + "'>" + info.label + "</option>");
                }
            }

            $mainTd.append($mainSelect);
            $nline.append($mainTd);
            // 关联参数字段
            var $passiveTd = $("<td class='fdOutParam_td_passive'></td>");
            var $passiveSelect = $("<select class='fdOutParam_passive' onchange='checkOutParam()'></selct>");
            $passiveSelect.append("<option value='' id='title' >"+modelingLang['relation.please.choose']+"</option>");
            var passiveSource = self.pcfg.paramSourceData;
            if (passiveSource && passiveSource) {
                for (var controlId in passiveSource) {
                    var info = passiveSource[controlId];
                    var fullLabel = info.label;
                    $passiveSelect.append("<option title='" + fullLabel + "'   value='" + info.name + "' data-property-type='" + info.type + "'>" + info.label + "</option>");
                }
            }
            $passiveTd.append($passiveSelect);
            $nline.append($passiveTd);
            // 操作td
            var $delTd = $("<td class=\"model-mask-panel-table-opt\"></td>");
            var $delSpan = $(" <p>"+modelingLang['modeling.page.delete']+"</p>");
            $delSpan.on("click", function () {
                self.destroyLine(lineId);
            });
            $delTd.append($delSpan);
            $nline.append($delTd);
            $valueTable.append($nline);
            return $nline;
        },
        destroyLine: function (id) {
            var self = this;
            var $valueTable = self.element.find("[mdlng-rltn-prprty-value=\"" + self.feildName + "\"]").find("tbody");
            $valueTable.find("[out-tr-id='" + id + "']").remove();
        },
        draw: function () {
        },
        destroy: function ($super, cfg) {

        },

        getKeyData: function () {
            var self = this;
            var $valueTable = self.element.find("[mdlng-rltn-prprty-value=\"" + self.feildName + "\"]").find("tbody");
            var keyData = []
            $valueTable.find("[mdlng-prprty-mark='fdOutParam']").each(function (idx, nl) {
                var lineData = self.getLineKeyData($(nl));
                if (lineData) {
                    keyData.push(lineData);
                }
            });
            return keyData;
        },
        getLineKeyData: function ($nline) {
            var keyData = {};
            keyData.mainParam = {};
            keyData.passiveParam = {};
            var $option = $nline.find(".fdOutParam_main option:selected");
            if ($option.val() === "") {
                return null;
            }
            keyData.mainParam.type = $option.attr("data-property-type");
            keyData.mainParam.value = $option.val();
            keyData.mainParam.text = $option.text();

            var $option_pass = $nline.find(".fdOutParam_passive option:selected");
            if ($option_pass.val() === "") {
                return null;
            }
            keyData.passiveParam.type = $option_pass.attr("data-property-type");
            keyData.passiveParam.value = $option_pass.val();
            keyData.passiveParam.text = $option_pass.text();
            return keyData;
        },
        initByStoreData: function (storeData) {
            if (!storeData) {
                return
            }
            var self = this;
            for (var i in storeData) {
                var data = storeData[i];
                var $nl = self.createNewLine();
                var $widgetSelect = $nl.find(".fdOutParam_main");
                $widgetSelect.val(data.mainParam.value);
                var $sortSelect = $nl.find(".fdOutParam_passive");
                $sortSelect.val(data.passiveParam.value);
            }

        }
    });

    exports.OperationGenerator = OperationGenerator;
});
