/**
 * 更新视图
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var dialog = require('lui/dialog');
    var crudBaseView = require("sys/modeling/base/relation/trigger/behavior/js/view/crudBaseView");
    var formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder");
    var modelingLang = require("lang!sys-modeling-base");
    var NotifyView = crudBaseView.CrudBaseView.extend({

        build: function () {
            this.prefix = "notify";
        	if($("[mdlng-bhvr-data='precfg']").find(".behavior_update_preview").length != 1){
        		this.buildPreWhereView();
        	}
            if($("[mdlng-bhvr-data='precfg']").find(".behavior_detail_query_preview").length != 1){
                this.buildDetailQueryView();
            }
            var $element = $("<div class='behavior_notify_view'/>");
            var $table = $("<table class='tb_simple modeling_form_table view_table' width='100%'/>");
            // 通知方法,目标人群,消息内容
            $table.append(this.parent.notifyTmpStr);
            //明细表
            this.buildDetailRule($table, this.parent.detailRuleTmpStr, this.parent.detailTmpStr);
            $table.find(".detail_target").hide();
            // 添加公式定义器事件到消息内容元素上面-获取目标表单的字段
            var xformId ;
            if (this.getTargetData()) {
                xformId =  this.getTargetData().xformId;
            }
            formulaBuilder.addFormulaEventToElement($table.find(".view_notify_msg .highLight"),
                "click", "msgFormula", "String",xformId,null);

            this.buildWhere($table, this.parent.whereTmpStr);
            this.drawFdIdWhereTr($table);
            var self = this;

            //修改查询条件类型选项
            $table.find(".WhereTypeinput4").hide();
            //查询条件切换事件
            $table.find(".WhereTypediv input[type='radio']").on("change",function(){
            	 var $whereBlockDom = $table.find(".view_field_where_div");
            	 if(this.value === "2"){
            		 $whereBlockDom.hide();
            	 }else{
            		 $whereBlockDom.show();
            	 }
            });
            var whereType = $table.find(".WhereTypediv input[type='radio']:checked").val();
            if(whereType === "3"){
                $table.find(".WhereTypediv input[type='radio'][value='0']").prop("checked","checked");
            }
            //136619,默认选中第一个明细表
            $table.find("[name=\"notifydetailOrMain\"]").on("change", function () {
                if(this.value === "2"){
                  var firstRadio =  $table.find("[name=\"notifydetailChecked\"]").first();
                    firstRadio.attr("checked",true);
                    firstRadio.trigger("click");
                }
            });
            $table.find(".WhereTypediv input[type='radio']").filter(':checked').trigger($.Event("change"));
            // 添加选择明细表切换事件
            $table.find("[name=\"notifydetailChecked\"]").on("change", function () {
                var allRadio = $table.find("[name=\"notifydetailChecked\"]");
                for (var i =0;i<allRadio.length;i++){
                    var checked = $(allRadio[i]).prop("checked");
                    var detailId = $(allRadio[i]).val();
                    if (checked) {
                        self.buildDetail($table, self.parent.detailTmpStr, detailId);
                    } else {
                        self.removeDetail(detailId);
                    }
                }
                var detailId = $(this).val();
                formulaBuilder.addFormulaEventToElement($table.find(".view_notify_msg .highLight"),
                    "click", "msgFormula", "String",xformId,detailId);
            });
            //添加使用类型切换事件
            $table.find("[name='fdUserType']").on("change", function () {
                var $whereDom = $table.find(".view_field_where_div");
                var $whereFdIdDom = $table.find(".view_fdId_where_table");
                $whereFdIdDom.hide();
                $whereDom.hide();
                if (this.value === "1") {//scenes,场景消息
                    $whereDom.show();
                    $table.find(".WhereTypediv input[type='radio']").filter(':checked').trigger($.Event("change"));
                    $table.find(".WhereTypediv").show();
                    $table.find(".type").show();
                    $table.find("[name=\"notifydetailOrMain\"]").filter(':checked').trigger($.Event("click"));
                    $table.find("[name=\"notifydetailChecked\"]").filter(':checked').trigger($.Event("change"));
                } else {
                    formulaBuilder.addFormulaEventToElement($table.find(".view_notify_msg .highLight"),
                        "click", "msgFormula", "String",xformId,null);
                	$table.find(".WhereTypediv").hide();
                    $whereFdIdDom.show();
                    $table.find(".type").hide();
                    $table.find(".detailChecked_Tr").hide();
                    $table.find(".detail_where").hide();
                }

            });
            $table.find("[name='fdUserType']").filter(':checked').trigger($.Event("change"));
            //添加通知类型切换事件
            $table.find("[name='fdNotifyType']").on("change", function () {
                var $span = $("#_xform_fdNotifyWay").find("[value=\"todo\"]").next("span");
                if (this.value === "0") {
                    $span.html(modelingLang['behavior.upcoming'])
                    $table.find(".fdMsgKeyTr").show();
                } else {
                    $span.html(modelingLang['behavior.to.be.read'])
                    $table.find(".fdMsgKeyTr").hide();
                }
            }).trigger($.Event("change"));
            // 添加目标人群单选切换事件
            var $notifyTargetContainer = $table.find(".view_notify_target");
            $table.find("[name='targetElementsType']").on("change", function () {
                $notifyTargetContainer.html("");
                if (this.checked) {
                    // 3(地址本)|4(公式定义器)
                    if (this.value === "3") {
                        $notifyTargetContainer.append(formulaBuilder.getOrgAddress_style1(true, "notifyTarget", ORG_TYPE_ALL));
                    } else if (this.value === "4") {
                        $notifyTargetContainer.append(formulaBuilder.get_style1("notifyTarget", "com.landray.kmss.sys.organization.model.SysOrgElement[]",null,null,xformId));
                    }
                }
            }).trigger($.Event("change"));

            // 添加消息链接切换事件
            $table.find("[name='notifyLinkType']").on("change", function () {
                var $LinkView = $table.find("#_xform_notifyLinkView");
                var $LinkUrl = $table.find("#_xform_notifyLinkUrl");
                if (this.checked) {
                    $LinkView.hide();
                    $LinkUrl.hide();
                    // 0|查看视图。2|url，1|编辑视图
                    if (this.value === "0") {
                        $LinkView.show();
                    } else if (this.value === "2") {
                        $LinkUrl.show();
                    }
                }
            }).trigger($.Event("change"));

            var modelId = this.parent.targetData.modelId;
            $table.find("#_xform_notifyLinkView").on("click", function () {
                var selectedId = $("[name='link_view_id']").val();
                dialog.iframe("/sys/modeling/base/relation/import/model_view_select.jsp?method=none&fdModelId=" + modelId + "&fdId=" + selectedId, "选择视图", function (value) {
                    $("[name='link_view_name']").val(value.fdName);
                    $("[name='link_view_id']").val(value.fdId);
                    $("[name='link_view_type']").val(value.fdType);
                }, {
                    width: 1010,
                    height: 600
                });
            });
            $element.append($table);

            this.config.viewContainer.append($element);
            return $element;
        },
        drawFdIdWhereTr: function ($table) {
            var self = this;
            var $whereTable = $table.find(".view_fdId_where_table");
            // 添加事件
            $whereTable.find(".where_style").on("change", function (e) {
                e.stopPropagation();
                if ($.isEmptyObject(self.getTargetData())) {
                    dialog.alert(modelingLang['behavior.select.target.form.first']);
                    return;
                }
                var $valueTd = $whereTable.find(".view_fdId_input_td");
                $valueTd.html("");
                var $ele = "";
                switch (this.value) {
                    case "2":
                        $ele = "";
                        break;
                    case "4":
                        $ele = formulaBuilder.get("view_fdId_input", "String");
                        break;
                    default:
                        $ele = $("<input type='text' name='" + this.valueName + "' class='inputsgl where_value' style='width:150px'/>");
                        break
                }
                $valueTd.append($ele);
            });
        },

        getKeyData: function ($super, cfg) {
            var keyData = $super(cfg);
            keyData.msg = {};

            keyData.msg.targetElement = {};
            keyData.msg.targetElement.type = this.element.find("[name='targetElementsType']:checked").val();
            keyData.msg.targetElement.text = this.element.find("[name='notifyTarget_name']").val();
            keyData.msg.targetElement.value = this.element.find("[name='notifyTarget']").val();

            keyData.msg.expression = {};
            keyData.msg.expression.text = this.element.find("[name='msgFormula_name']").val();
            //keyData.msg.expression.value = this.element.find("[name='msgFormula']").val();
            keyData.msg.expression.value = encodeURIComponent(this.element.find("[name='msgFormula']").val());

            //新增
            keyData.msg.type = this.element.find("[name='fdUserType']:checked").val();
            keyData.msg.notify = {};
            keyData.msg.notify.type = this.element.find("[name='fdNotifyType']:checked").val();
            keyData.msg.notify.key = this.element.find("[name='fdMsgKey']").val();
            keyData.msg.notify.ways = this.element.find("[name='fdNotifyWay']").val();
            //触发消息的查询
            if (keyData.msg.type === "2") {
                var where = [];
                var view_fdId_tr = this.element.find(".view_fdId_tr");
                var fdIdWhere = {
                    "name": {
                        type: "String",
                        value: "fdId",
                        text: "fdId"
                    },
                    "match": "=",
                    "type": {
                        text: view_fdId_tr.find(".where_style option:selected").text(),
                        value: view_fdId_tr.find(".where_style option:selected").val()
                    },
                    "expression": {
                        text: view_fdId_tr.find("[name='view_fdId_input_name']").val(),
                        value: view_fdId_tr.find("[name='view_fdId_input']").val(),
                    }
                }
                where.push(fdIdWhere);
                keyData.where = where;
            }
            //消息链接：
            keyData.msg.link = {};
            var linkType = this.element.find("[name='notifyLinkType']:checked").val();
            keyData.msg.link.type = linkType;
            if (linkType == "0") {
                keyData.msg.link.view = {};
                keyData.msg.link.view.id = this.element.find("[name='link_view_id']").val();
                keyData.msg.link.view.name = this.element.find("[name='link_view_name']").val();
                keyData.msg.link.view.type = this.element.find("[name='link_view_type']").val();
            } else if (linkType == "2") {
                keyData.msg.link.url = this.element.find("[name='notifyLinkUrl']").val();
            }
            return keyData;
        },

        initByStoreData: function ($super, storeData) {
            $super(storeData);
            var msg = storeData.msg;

            this.element.find("[name='targetElementsType'][value='" + msg.targetElement.type + "']").attr("checked", true).trigger($.Event("change"));
            this.element.find("[name='notifyTarget_name']").val(msg.targetElement.text);
            this.element.find("[name='notifyTarget']").val(msg.targetElement.value);

            //this.element.find("[name='msgFormula']").val(msg.expression.value);
            this.element.find("[name='msgFormula']").val(decodeURIComponent(msg.expression.value));
            this.element.find("[name='msgFormula_name']").val(msg.expression.text);

            //新增
            this.element.find("[name='fdUserType'][value='" + msg.type + "']").attr("checked", true).trigger($.Event("change"));
            this.element.find("[name='fdNotifyType'][value='" + msg.notify.type + "']").attr("checked", true).trigger($.Event("change"));
            this.element.find("[name='fdMsgKey']").val(msg.notify.key);
            if (msg.type === "2") {
                var where = storeData.where[0];
                var view_fdId_tr = this.element.find(".view_fdId_tr");
                view_fdId_tr.find(".where_style").val(where.type.value);
                view_fdId_tr.find(".where_style").trigger($.Event("change"));
                view_fdId_tr.find("[name='view_fdId_input_name']").val(where.expression.text);
                view_fdId_tr.find("[name='view_fdId_input']").val(where.expression.value);
            }

            //消息链接：
            var linkType = msg.link.type;
            if (!linkType)
                linkType = "0"
            this.element.find("[name='notifyLinkType'][value='" + linkType + "']").attr("checked", true).trigger($.Event("change"));
            if (linkType == "0") {
                this.element.find("[name='link_view_id']").val(msg.link.view.id);
                this.element.find("[name='link_view_name']").val(msg.link.view.name);
                this.element.find("[name='link_view_type']").val(msg.link.view.type);
            } else if (linkType == "2") {
                this.element.find("[name='notifyLinkUrl']").val(msg.link.url);
            }

        }

    });

    exports.NotifyView = NotifyView;
});