/**
 * 排序生成器
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var modelingLang = require("lang!sys-modeling-base");
    var notSupport=["relevance"];
    var OutRecordGenerator = base.Component.extend({
        //lines,
        initProps: function ($super, cfg) {
            // console.log("OutRecordGenerator", cfg);
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
                    $ele.find(".outParamTip").remove();
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
            var $mainSelect = $("<select class='fdOutParam_main' style='width: 80%'></selct>");
            $mainSelect.append("<option value=''>"+modelingLang['relation.please.choose']+"</option>");
            var mainSource = self.pcfg.widgets.main;

            if (mainSource && mainSource) {
                for (var controlId in mainSource) {
                    if(typeof(String.prototype.endsWith) === "function" && controlId.endsWith("_config") || this.endsWith(controlId,"_config")){
                        //#158168 过滤关联文档 ,关联文档控件有controlId，controlId_config,暂时屏蔽controlId_config
                        continue;
                    }
                    if (controlId ==="docCreator"){
                        //#158078 暂时屏蔽创建者
                        continue;
                    }
                    var info = mainSource[controlId];
                    if(notSupport.indexOf(info.businessType)>-1 || info.type === "RTF"){
                        continue;
                    }
                    var fullLabel = info.fullLabel||info.label;
                    $mainSelect.append("<option title='" + fullLabel + "'  value='" + info.name + "' data-property-type='" + info.type + "'>" + info.label + "</option>");
                }
            }

            $mainTd.append($mainSelect);
            $nline.append($mainTd);
            // 关联表单字段
            var $passiveTd = $("<td class='fdOutParam_td_passive'></td>");
            var $passiveSelect = $("<select class='fdOutParam_passive' style='width: 80%' ></selct>");
            $passiveSelect.append("<option value='' id='title' >"+modelingLang['relation.please.choose']+"</option>");
            var passiveSource = self.pcfg.widgets.passive;
           /* // 暂时屏蔽掉创建者
            if (passiveSource.docCreator){
                delete passiveSource.docCreator;
            }*/
            var fdSourceType = self.pcfg.container.find("[name=fdSourceType]:checked").val();
            var fdTargetDetail = self.pcfg.container.find("[mdlng-rltn-data=\"fdTargetDetail\"]").val();
            if (passiveSource && passiveSource) {
                for (var controlId in passiveSource) {
                    if(typeof(String.prototype.endsWith) === "function" && controlId.endsWith("_config") || this.endsWith(controlId,"_config")){
                        //#158168 过滤关联文档 ,关联文档控件有controlId，controlId_config,暂时屏蔽controlId_config
                        continue;
                    }
                    if (controlId ==="docCreator"){
                        //#158078 暂时屏蔽创建者
                        continue;
                    }
                    var info = passiveSource[controlId];
                    if(notSupport.indexOf(info.businessType)>-1 || info.type === "RTF"){
                        continue;
                    }
                    var fullLabel = info.fullLabel||info.label;
                    if(fdSourceType == "1" && info.name.indexOf(".") > 0  && info.name.indexOf(fdTargetDetail) < 0){
                        continue;
                    }
                    $passiveSelect.append("<option title='" + fullLabel + "'   value='" + info.name + "' data-property-type='" + info.type + "'>" + info.label + "</option>");
                }
            }
            //#97081 业务传出支持附件
            $mainSelect.on("change", function () {
                var type = $(this).find("option:selected").attr("data-property-type");
                var stype =  $passiveSelect.find("option:selected").attr("data-property-type");
                $passiveSelect.find("option[id='title']").text(modelingLang['relation.please.choose']);
                $passiveSelect.css("color","");
                $passiveSelect.removeAttr("disabled");
                if (stype != type){
                    $passiveSelect.find("option:selected").prop("selected","");
                }
                $passiveSelect.find("option").hide();
                var $matching =  $passiveSelect.find("option[data-property-type='"+type+"']");
                if ($matching.length!=0){
                    $matching.show();
                }else {
                    $passiveSelect.find("option[id='title']").text(modelingLang['relation.no.corresponding.field.in.form']);
                    $passiveSelect.css("color","red");
                    $passiveSelect.attr("disabled","disabled");
                }
            });

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
            //console.log(storeData);
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

        },
        endsWith : function(str,pattern){
            var reg = new RegExp(pattern + "$");
            return reg.test(str);
        },
    });

    exports.OutRecordGenerator = OutRecordGenerator;
});
