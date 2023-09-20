/**
 * 新建规则生成器
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var dialog = require('lui/dialog');
    var formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder");
    var modelingLang = require("lang!sys-modeling-base");
    var TEMP_behaviorRule = "" +
        "<tr>" +
        "   <td class=\"td_normal_title\">"+modelingLang['behavior.trigger.rule']+"</td>" +
        "   <td>  " +
        "       <div class=\"model-mask-panel-table-base\">" +
        "           <div class=\"view_flow_creator_radio\" _xform_type=\"radio\" style=\"margin-left: 0\"> " +
        "           <label class=\"lui-lbpm-radio\">" +
        "               <input type=\"checkbox\" name=\"behaviorRule\" value=\"skipDraftNode\">" +
        "               <span class=\"radio-label\">"+modelingLang['behavior.skip.drafting.node']+"</span>" +
        "           </label>" +
        "           </div>" +
        "       </div>" +
        "   </td>" +
        "</tr>";
    var CreatorGenerator = base.Component.extend({

        initProps: function ($super, cfg) {
            $super(cfg);
            this.prefix = cfg.parent.prefix;
            this.parent = cfg.parent;
            this.$table = cfg.$table;
            this.isShow = true;
            this.draw();
        },
        draw: function () {
            var self = this;
            var $table = this.$table;
            self.element = $("<div/>");
            this.buildBehaviorRule(self.element);
            this.buildCreator(self.element);
            self.element = self.element.children();
            $table.append(self.element);
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
        //触发规则
        buildBehaviorRule: function ($table) {
            var self = this;
            var targetData = self.parent.getTargetData();
            if (targetData.flowInfo && targetData.flowInfo.fdEnableFlow === "true") {
                //规则
                var $ruleTr = $(TEMP_behaviorRule);
                $ruleTr.find("[name='behaviorRule']").attr("name", self.prefix + "behaviorRule");
                $table.append($ruleTr);
            }

        },
        //画创建人
        buildCreator: function ($table) {
            var self = this;
            // 目标表单流程
            var targetData = self.parent.getTargetData();
            if (!targetData)
                return;
            var $targetFlowTr = $("<tr/>");
            $targetFlowTr.append("<td class='td_normal_title'>"+modelingLang['behavior.target.form.flow']+"</td>");
            var $targetFlowTd = $("<td></td>");
            if (targetData.flowInfo && targetData.flowInfo.fdEnableFlow === "true") {
                self.buildFlow(targetData, $targetFlowTr, $targetFlowTd, $table);
            } else {
                self.buildNoFlow(targetData, $targetFlowTr, $targetFlowTd, $table);
            }
        },
        //有流程
        buildFlow: function (targetData, $targetFlowTr, $targetFlowTd, $table) {

            var self = this;
            //todo 仅当流程之后一条的时候，直接选定流程，不需再选择
            $targetFlowTd.append(self.buildFlowSelect(self.prefix + "flowRecord", targetData.modelId));
            $targetFlowTr.append($targetFlowTd);
            $table.append($targetFlowTr);

            // 目标流程创建者
            var $creatorTr = $("<tr class='modeling-validate-create'/>");
            $creatorTr.append("<td class='td_normal_title '>"+modelingLang['behavior.target.process.creator']+"</td>");
            var $creatorTd = $("<td></td>");
            var $msgDiv = $("<div class=\"model-mask-panel-table-base\" />");
            //校验
            var flowCreator_id = self.prefix + "flowCreator_id";
            var flowCreator_name = self.prefix + "flowCreator_name";
            var modeling_validation = flowCreator_name + ";目标流程创建者;requiredNullable;name:" + flowCreator_name + ";id:" + flowCreator_id;
            $creatorTr.attr("modeling-validation", modeling_validation);
            $msgDiv.attr("id", flowCreator_id);
            self.creator_validation = modeling_validation;
            $msgDiv.append(this.parent.parent.flowTypeTmpStr);
            $creatorTd.append($msgDiv);
            var $targetCreatorContainer = $creatorTd.find(".view_flow_creator");
            $creatorTd.find("[name='flowCreatorType']").attr("name", self.prefix + "flowCreatorType");
            $creatorTd.find("[name='" + self.prefix + "flowCreatorType']").on("change", function (e) {
                $targetCreatorContainer.html("");
                if (this.checked) {
                    // 3(地址本)|4(公式定义器)
                    if (this.value === "3") {
                        $targetCreatorContainer.append(
                            formulaBuilder.getOrgAddress_style1(false,
                                self.prefix + "flowCreator", ORG_TYPE_ALL));
                    } else if (this.value === "4") {
                        $targetCreatorContainer.append(
                            formulaBuilder.get_style1(self.prefix + "flowCreator",
                                "com.landray.kmss.sys.organization.model.SysOrgPerson"));
                    }
                }
            }).trigger($.Event("change"));

            $creatorTr.append($creatorTd);
            $table.append($creatorTr);

        },
        //无流程
        buildNoFlow: function (targetData, $targetFlowTr, $targetFlowTd, $table) {
            var self = this;
            // 目标流程创建者
            var $creatorTr = $("<tr class='modeling-validate-create main_create'/>");
            $creatorTr.append("<td class='td_normal_title'>"+modelingLang['behavior.designated.creator']+"</td>");
            var $creatorTd = $("<td></td>");
            var $msgDiv = $("<div class=\"model-mask-panel-table-base\" />")
            //校验
            var flowCreator_id = self.prefix + "noFlowCreator_id";
            var flowCreator_name = self.prefix + "noFlowCreator_name";
            var modeling_validation = flowCreator_name + ";目标文档创建者;requiredNullable;name:" + flowCreator_name + ";id:" + flowCreator_id;
            $creatorTr.attr("modeling-validation", modeling_validation);
            $msgDiv.attr("id", flowCreator_id);
            self.creator_validation = modeling_validation;
            $msgDiv.append(this.parent.parent.noflowTypeTmpStr);
            $creatorTd.append($msgDiv);
            var $targetCreatorContainer = $creatorTd.find(".view_no_flow_creator");
            $creatorTd.find("[name='noFlowCreatorType']").attr("name", self.prefix + "noFlowCreatorType");
            $creatorTd.find("[name='" + self.prefix + "noFlowCreatorType']").on("change", function (e) {
                if (this.checked) {
                    $targetCreatorContainer.html("");
                    // 3(地址本)|4(公式定义器)
                    if (this.value === "3") {
                        $targetCreatorContainer.append(
                            formulaBuilder.getOrgAddress_style1(true, self.prefix + "noFlowCreator",
                                ORG_TYPE_ALL));
                    } else if (this.value === "4") {
                        $targetCreatorContainer.append(
                            formulaBuilder.get_style1(self.prefix + "noFlowCreator",
                                "com.landray.kmss.sys.organization.model.SysOrgPerson"));
                    }
                }
            }).trigger($.Event("change"));
            $creatorTr.append($creatorTd);
            $table.append($creatorTr);
        },
        //流程选择
        buildFlowSelect: function (name, modelId) {
            var html = $("<div _xform_type=\"text\" class='modeling_flow_record'/>");
            html.append("<input type='hidden' name='" + name + "' />");

            var $flowRecord = $("<div  class=\"model-mask-panel-table-show highLight\"\>")
            $flowRecord.append("<input type='text' name='" + name + "_name' class='inputsgl' style='width:150px' readonly/>");
            $flowRecord.on("click", function (e) {
                Dialog_List(false, name, name + "_name", "", "modelingAppFlowService&fdAppModelId=" + modelId);
            });
            html.append($flowRecord);
            // html.append("<span class='txtstrong'>*</span>");
            return html;
        },

        getKeyData: function () {
            var self = this;
            if (!self.isShow) {
                return null;
            }

            var keyData = {};
            // 设置流程
            var $flowRecord = this.element.find("[name='" + self.prefix + "flowRecord']");
            if ($flowRecord.length > 0) {
                keyData.flow = {};
                keyData.flow.flowId = $flowRecord.val();
                keyData.flow.flowName = this.element.find("[name='" + self.prefix + "flowRecord_name']").val();
                keyData.flow.targetElement = {};
                keyData.flow.targetElement.text = this.element.find("[name='" + self.prefix + "flowCreator_name']").val();
                keyData.flow.targetElement.value = this.element.find("[name='" + self.prefix + "flowCreator']").val();
                keyData.flow.targetElement.type = this.element.find("[name='" + self.prefix + "flowCreatorType']:checked").val();
            }
            var $noFlowCreatorType = this.element.find("[name='" + self.prefix + "noFlowCreatorType']");
            if ($noFlowCreatorType && $noFlowCreatorType.val()) {
                keyData.noflow = {};
                keyData.noflow.targetElement = {};
                keyData.noflow.targetElement.text = this.element.find("[name='" + self.prefix + "noFlowCreator_name']").val();
                keyData.noflow.targetElement.value = this.element.find("[name='" + self.prefix + "noFlowCreator']").val();
                keyData.noflow.targetElement.type = this.element.find("[name='" + self.prefix + "noFlowCreatorType']:checked").val();
            }
            //触发规则
            keyData.behaviorRule = {};
            var $behaviorRules = this.element.find("[name='" + self.prefix + "behaviorRule']");
            if ($behaviorRules.length > 0) {
                $behaviorRules.each(function (idx, dom) {
                    keyData.behaviorRule[this.value] = $(dom).prop("checked");
                });
            }
            return keyData;
        },

        initByStoreData: function (storeData) {
            var self = this;
            // 设置流程
            var flowData = storeData.flow;
            var $flowRecord = this.element.find("[name='" + self.prefix + "flowRecord']");
            if (flowData && $flowRecord.length > 0) {
                $flowRecord.val(flowData.flowId);
                this.element.find("[name='" + self.prefix + "flowRecord_name']").val(flowData.flowName);
                this.element.find("[name='" + self.prefix + "flowCreatorType'][value='" + flowData.targetElement.type + "']").attr("checked", true).trigger($.Event("change"));
                this.element.find("[name='" + self.prefix + "flowCreator_name']").val(flowData.targetElement.text);
                this.element.find("[name='" + self.prefix + "flowCreator']").val(flowData.targetElement.value);
            }
            var noFlowData = storeData.noflow;
            if (noFlowData) {
                this.element.find("[name='" + self.prefix + "noFlowCreatorType'][value='" + noFlowData.targetElement.type + "']").attr("checked", true).trigger($.Event("change"));
                this.element.find("[name='" + self.prefix + "noFlowCreator_name']").val(noFlowData.targetElement.text);
                this.element.find("[name='" + self.prefix + "noFlowCreator']").val(noFlowData.targetElement.value);
            }
            //触发规则
            var $behaviorRules = this.element.find("[name='" + self.prefix + "behaviorRule']");
            if ($behaviorRules.length > 0 && storeData.behaviorRule) {
                $behaviorRules.each(function (idx, dom) {
                    if (storeData.behaviorRule[this.value])
                        $(dom).prop("checked", true);
                });
            }
        },

    })
    exports.CreatorGenerator = CreatorGenerator;

})
