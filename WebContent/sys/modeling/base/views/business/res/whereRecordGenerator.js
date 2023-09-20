/**
 * 查询条件生成器
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder");
    var relationDiagram = require("sys/modeling/base/mobile/resources/js/relationDiagram");
    var _whereItemHead = "" +
        "<div class=\"model-edit-view-oper-head\">\n" +
        "    <div class=\"model-edit-view-oper-head-title\">\n" +
        "        <div class='changeToOpenOrClose'><i class=\"open\"></i></div>\n" +
        "        <span>ID</span>" +
        "   </div>\n" +
        "    <div class=\"model-edit-view-oper-head-item\">\n" +
        "        <div class=\"del\"><i></i></div>\n" +
        "        <div class=\"down\"><i></i></div>\n" +
        "        <div class=\"up\"> <i></i></div>\n" +
        "    </div>\n" +
        "</div>";
    var _whereItemContent = "" +
        "<div class=\"model-edit-view-oper-content\">\n" +
        "    <ul>\n" +
        "        <li class=\"model-edit-view-oper-content-item first-item\" data-lui-mark=\"whereTypeLi\">\n" +
        "            <div class=\"item-title\">类型</div>\n" +
        "            <div class=\"item-content\">" +
        "                <div class=\"view_flag_radio\" style=\"display: inline-block;font-size: 0;\">\n" +
        "                    <div data-rp-whereType=\"0\" style='margin-right: 10px;'>" +
        "                           <i class=\"view_flag_no view_flag_yes\"></i>自定义查询项\n" +
        "                    </div>\n" +
        "                    <div data-rp-whereType=\"1\" >" +
        "                           <i class=\"view_flag_no \"></i>内置查询项\n" +
        "                    </div>\n" +
        "                </div>\n" +
        "            </div>\n" +
        "        </li>\n" +
        "        <li class=\"model-edit-view-oper-content-item first-item \" data-lui-mark=\"whereFieldLi\">\n" +
        "            <div class=\"item-title\">字段</div>\n" +
        "            <div class=\"item-content\"></div>\n" +
        "        </li>\n" +
        "        <li class=\"model-edit-view-oper-content-item first-item \" data-lui-mark=\"whereFieldLi_insystem\">\n" +
        "            <div class=\"item-title\">内置查询</div>\n" +
        "            <div class=\"item-content\"></div>\n" +
        "        </li>\n" +
        "        <li class=\"model-edit-view-oper-content-item \" >\n" +
        "            <div class=\"item-title\">运算符</div>\n" +
        "            <div class=\"item-content\">\n" +
        "                <div data-lui-mark=\"whereOperation\"></div>\n" +
        "            </div>\n" +
        "        </li>\n" +
        "        <li class=\"model-edit-view-oper-content-item last-item \">\n" +
        "            <div class=\"item-title\">值</div>\n" +
        "            <div class=\"item-content\" >\n" +
        "                <div class=\"xform_main_data_fieldWrap\" style=\"position:relative;margin:0 4px;width:100%\">" +
        "                    <div data-lui-mark=\"whereValue\" class=\" input_radio height28\"\n" +
        "                         style=\"width:100%;\"></div>\n" +
        "            </div>\n" +
        "        </li>\n" +
        "    </ul>\n" +
        "</div>";
    var WhereRecordGenerator = base.Component.extend({

        /**
         * 这里需要初始化如下函数
         * widgets ：字段列表
         * formulaWidgets:用于公式的字段列表
         * @param $super
         * @param cfg
         */
        initProps: function ($super, cfg) {
            $super(cfg);
            this.widgets = cfg.widgets;
            this.fdEnableFlow = cfg.fdEnableFlow;
            this.formulaWidgets = cfg.formulaWidgets;
            this.valueName = "fd_" + parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16);
        },
        insystem: [
            {
                "value": "create",
                "text": "我创建的"
            },
            {
                "isFlow": "true",
                "value": "approved",
                "text": "我已审的"
            },
            {
                "isFlow": "true",
                "value": "approval",
                "text": "待我审的"
            }],
        fixValDrawValueFormula: {
            "com.landray.kmss.sys.organization.model.SysOrgPerson": {
                "fun": "getOrgAddress_style1",
                "type": ORG_TYPE_PERSON
            },
            "com.landray.kmss.sys.organization.model.SysOrgElement": {
                "fun": "getOrgAddress_style1",
                "type": ORG_TYPE_ALLORG
            },
            "Date": {
                "fun": "getDate",
                "type": "Date"
            },
            "DateTime": {
                "fun": "getDate",
                "type": "DateTime"
            },
            "Time": {
                "fun": "getDate",
                "type": "Time"
            },
        },
        draw: function ($c) {
            var self = this;
            self.element = $("<tr class='whereTr'></tr>");
            var $Item = $("<div class=\"model-edit-view-oper\">");
            $("<td></td>").append($Item).appendTo(self.element);

            //头部
            var $head = $(_whereItemHead);
            self.bindEvent_head($head)
            $head.find(".model-edit-view-oper-head-title").text(self.valueName)
            $head.appendTo($Item);
            //内容
            var $content = $(_whereItemContent);
            //-类型
            var $wtLi = $content.find("[data-lui-mark=\"whereTypeLi\"]");
            var wtName = self.valueName + "_type";
            $wtLi.append($("<input type='hidden' name='" + wtName + "' value=0 />"));
            $wtLi.find(".view_flag_radio div").on("click", function () {
                var typeVal = $(this).attr("data-rp-whereType");
                $wtLi.find(".view_flag_radio").each(function () {
                    $(this).find(".view_flag_no").removeClass("view_flag_yes")
                });
                $(this).find(".view_flag_no").addClass("view_flag_yes")
                $wtLi.find("input[name=" + wtName + "]").val(typeVal);
                if (typeVal==="0"){
                    //自定义查询
                    $content.find("li").show();
                    $content.find("[data-lui-mark=\"whereFieldLi_insystem\"]").hide();
                    $head.find(".model-edit-view-oper-head-title").text($content.find("[data-lui-mark=\"whereFieldLi\"]").find(":selected").text())
                }else{
                    $content.find("li").hide();
                    $wtLi.show();
                    $content.find("[data-lui-mark=\"whereFieldLi_insystem\"]").show();
                    $head.find(".model-edit-view-oper-head-title").text($content.find("[data-lui-mark=\"whereFieldLi_insystem\"]").find(":selected").text())
                }
            });
            //字段
            var $wfLi = $content.find("[data-lui-mark=\"whereFieldLi\"]");
            var $fieldSelect = $("<select  class=\"inputsgl whereField\"  style=\"margin:0 4px;width:45%\"></selct>");
            $fieldSelect.attr("name", self.valueName + "_field");
            if (self.widgets) {
                for (var controlId in self.widgets) {
                    var info = self.widgets[controlId];
                    if (info.type === "Attachment") {
                        //附件不处理
                        continue;
                    }
                    if (info.name.indexOf(".") > 0)
                        continue;
                    if (info.name === "fdId")
                        info.label = "ID";
                    var option = "<option value='" + info.name + "' data-property-type='" + info.type + "' data-property-business-type='" + info.businessType +"'>" + info.label + "</option>";
                    $fieldSelect.append(option);
                }
            }
            //字段-内置
            var $wfLi_insystem= $content.find("[data-lui-mark=\"whereFieldLi_insystem\"]");
            var $fieldSelect_insystem = $("<select  class=\"inputsgl whereField\"  style=\"margin:0 4px;width:45%\"></selct>");
            $fieldSelect_insystem.attr("name", self.valueName + "_insystemField");
            for (var controlId in self.insystem) {
                var info = self.insystem[controlId];
                //修复#131325 判断是否有流程逻辑有误
                if ('false' == self.fdEnableFlow) {
                    //排除无流程
                    if(info.isFlow){
                        continue;
                    }
                }
                var option = "<option value='" + info.value + "'>" + info.text + "</option>";
                $fieldSelect_insystem.append(option);
            }
            $fieldSelect_insystem.on("change", function () {
                self.fieldChange(this);
                $head.find(".model-edit-view-oper-head-title").text($(this).find(":selected").text())
                self.head_title = $(this).find(":selected").text();
            });
            $wfLi_insystem.find(".item-content").append($fieldSelect_insystem);
            // 运算符td
            self.$operatorTd = $content.find("[data-lui-mark=\"whereOperation\"]");
            self.$operatorTd.attr("name", self.valueName + "_match");
            $fieldSelect.on("change", function () {
                self.fieldChange(this);
                $head.find(".model-edit-view-oper-head-title").text($(this).find(":selected").text())
                self.head_title = $(this).find(":selected").text();
            });
            // 值类型td
            self.$valueTd = $content.find("[data-lui-mark=\"whereValue\"]");

            $c.append(self.element);
            //第一次主动触发
            $wtLi.find("[data-rp-wheretype=\"0\"]").trigger($.Event("click"));
            $fieldSelect.find("option[value='fdId']").prop("selected", "selected").trigger($.Event("change"));
            $wfLi.find(".item-content").append($fieldSelect);
            $content.appendTo($Item);

            self.parent.addWgt(this, "where");
        },
        bindEvent_head: function ($head) {
            var self = this;
            $head.find(".changeToOpenOrClose").on("click", function () {
                self.changeToOpenOrClose(this);
            });
            $head.find(".del").on("click", function () {
                self.destroy();
            });
            $head.find(".up").on("click", function () {
                self.parent.moveWgt(self, "where", -1);
            });
            $head.find(".down").on("click", function () {
                self.parent.moveWgt(self, "where", 1);
            });
        },

        fieldChange: function (dom) {
            var self = this;
            var $selectedOption = $(dom).find(":selected");
            var propertyType = $selectedOption.attr("data-property-type");
            //对象特殊处理(组织架构)
            $(dom).next(".whereField_sub").remove();
            if(propertyType) {
                if (propertyType.indexOf("SysOrg") > -1) {
                    var $fieldSelect_sub = $("<select  class=\"inputsgl whereField_sub\"  style=\"margin:0 4px;width:45%\"></selct>");
                    $fieldSelect_sub.attr("name", self.valueName + "_field_sub");
                    $fieldSelect_sub.append("<option value='id' selected data-property-type='" + propertyType + "'>ID</option>");
                    $fieldSelect_sub.append("<option value='name' data-property-type='String'>名称</option>");
                    $fieldSelect_sub.on("change", function () {
                        self.updateoperatorTd(this);
                    }).trigger($.Event("change"));
                    $(dom).after($fieldSelect_sub);
                } else {
                    self.updateoperatorTd(dom);
                }
            }
        },
        updateoperatorTd: function (dom) {
            // 更新运算符
            this.$operatorTd.html("");
            var $selectedOption = $(dom).find(":selected");
            this.propertyType = $selectedOption.attr("data-property-type");
            var businessType = $selectedOption.attr("data-property-business-type");
            var propertyEnumType = $selectedOption.attr("data-property-type");
            if(businessType == "inputCheckbox" || businessType == "inputRadio"
                || businessType == "fSelect" || businessType == "select"){
               propertyEnumType = "enum";
            }
            var optionInfo = relationDiagram.getDiagram("where", propertyEnumType);
            if (!optionInfo || !optionInfo.operator) {
                optionInfo = {
                    operator: [
                        {"name": "等于", "value": "!{equal}"}]
                }
            }
            optionInfo = optionInfo.operator;
            var selectHtml = "<select class='inputsgl' style=\"margin:0 4px;width:100%\">";
            for (var i = 0; i < optionInfo.length; i++) {
                selectHtml += "<option value='" + optionInfo[i].value + "'>" + optionInfo[i].name + "</option>";
            }
            selectHtml += "</select>";
            this.$operatorTd.append(selectHtml);
            this.updateValueTd()
        },

        updateValueTd: function () {
            this.$valueTd.html("");
            this.$valueTd.append(this.fixValDraw());
        },

        fixValDraw: function () {
            var type = this.propertyType;
            var html = "";
            if (this.fixValDrawValueFormula.hasOwnProperty(type)) {
                var fun = this.fixValDrawValueFormula[type].fun;
                html = formulaBuilder[fun](false, this.valueName, this.fixValDrawValueFormula[type].type);
            } else {
                html = $("<input type='text' name='" + this.valueName + "' class='inputsgl where_value' style='width:100%'/>");
            }
            return html;
        },

        nullValDraw: function () {
            return "";
        },


        destroy: function ($super, cfg) {
            this.parent.deleteWgt(this, "where");
            $super(cfg);
        },
        changeToOpenOrClose: function (obj) {
            var $parent = $(obj).parents(".model-edit-view-oper").eq(0);
            if ($parent.find(".model-edit-view-oper-content.close")[0]) {
                //关闭状态 - 打开状态
                $(obj).find("i").removeClass("close");
                $parent.find(".model-edit-view-oper-content").removeClass("close");
            } else {
                //开始状态 - 关闭状态
                $(obj).find("i").addClass("close");
                $parent.find(".model-edit-view-oper-content").addClass("close");
            }
        },
        getKeyData: function () {
            var keyData = {};
            keyData.type = {};
            keyData.name = {};
            keyData.match = "";
            keyData.expression = {};

            var self = this;
             var whereType = self.element.find("[name='" + self.valueName + "_type']").val();
            keyData.type.value = whereType;
            keyData.type.text = whereType == 1 ? "内置查询" : "自定义查询";
            //
            if (whereType === "0"){
                var $whereField = self.element.find("[name='" + self.valueName + "_field']");
                var $whereField_sub = self.element.find("[name='" + self.valueName + "_field_sub']");
                var nameType = $whereField.attr("data-property-type");
                if ($whereField_sub.length>0){
                    nameType= $whereField_sub.attr("data-property-type");
                }
                keyData.name = {
                    text: $whereField.find("option:selected").text() + $whereField_sub.find("option:selected").text(),
                    sub: $whereField_sub.find("option:selected").val(),
                    value: $whereField.find("option:selected").val(),
                    type:nameType
                };
                //
                keyData.match = self.element.find("[name='" + self.valueName + "_match']").find("option:selected").val();
                //
                keyData.expression = {
                    value: self.element.find("[name='" + self.valueName + "']").val(),
                    text: self.element.find("[name='" + self.valueName + "_name']").val(),
                };
            }else{
                keyData.insystem=self.element.find("[name='" + self.valueName + "_insystemField']").val();
            }

            return keyData;
        },

        initByStoreData: function (storeData) {
            var self = this;
            var $typeLi = this.element.find("[data-lui-mark=\"whereTypeLi\"]");
            $typeLi.find("[data-rp-wheretype=\"" + storeData.type.value + "\"]").trigger($.Event("click"));
            if (storeData.type.value === "0") {
                var $whereField = self.element.find("[name='" + self.valueName + "_field']");
                $whereField.find("option[value='" + storeData.name.value + "']").attr("selected", "selected");
                $whereField.trigger($.Event("change"));
                if (storeData.name.sub) {
                    var $whereField_sub = self.element.find("[name='" + self.valueName + "_field_sub']");
                    $whereField_sub.find("option[value='" + storeData.name.sub + "']").attr("selected", "selected");
                    $whereField_sub.trigger($.Event("change"));
                }
                self.element.find("[name='" + self.valueName + "_match']").find("option[value='" + storeData.match + "']").attr("selected", "selected");

                self.element.find("[name='" + self.valueName + "']").val(storeData.expression.value);
                self.element.find("[name='" + self.valueName + "_name']").val(storeData.expression.text);
            }else{
               self.element.find("[name='" + self.valueName + "_insystemField']").find("option[value='" + storeData.insystem + "']").attr("selected", "selected");
            }

        }
    });

    exports.WhereRecordGenerator = WhereRecordGenerator;
});
