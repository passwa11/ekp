/**
 * 排序生成器
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var modelingLang = require("lang!sys-modeling-base");
    var SortRecordGenerator = base.Component.extend({
        //lines,
        initProps: function ($super, cfg) {
            //console.log("SortRecordGenerator", cfg);
            $super(cfg);
            this.feildName = cfg.fieldName;
            this.pcfg = cfg.pcfg;
            // this.valueName = "fd_" + parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16);
            this.build();
            this.lines = [];
        },

        sortTypes: {
            "asc": {
                name: modelingLang['modelingAppListview.fdOrderType.asc'],
                value: "asc"
            },
            "desc": {
                name: modelingLang['modelingAppListview.fdOrderType.desc'],
                value: "desc"
            }
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
        createNewLine: function (portlet) {
            var self = this;
            var $valueTable = self.element.find("[mdlng-rltn-prprty-value=\"" + self.feildName + "\"]").find("tbody");
            var lineId = parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16);
            var $nline = $("<tr></tr>");
            $nline.attr("sort-tr-id", lineId);
            $nline.attr("mdlng-prprty-mark", "fdOutSort");
            // 字段td
            var $widgetTd = $("<td></td>");
            var $widgetSelect = $("<select class='sort_widget' style='text-overflow:ellipsis;white-space: nowrap;max-width: 150px;'></selct>");
            $widgetSelect.append("<option value=''>"+modelingLang['relation.please.choose']+"</option>");
            var sourceData = this.pcfg.widgets.passive;
            if (sourceData && sourceData) {
                for (var controlId in sourceData) {
                    var info = sourceData[controlId];
                    //过滤组织架构\附件 \明细表\CLOB
                    if (this.filterFiled(info)) {
                        continue
                    }
                    var fullLabel = info.fullLabel||info.label;
                    $widgetSelect.append("<option  title='" + fullLabel + "' value='" + info.name + "' data-property-type='" + info.type + "'><div>" + info.label + "</div></option>");
                }
            }

            $widgetSelect.on("change",function () {
                $(this).attr("title",$(this).children('option:selected').attr("title"));
            })

            $widgetTd.append($widgetSelect);
            $nline.append($widgetTd);
            // 排序类型
            var $sortTd = $("<td></td>");
            var $sortSelect = $("<select class='sort_sort'></selct>");

            for (var t in self.sortTypes) {
                $sortSelect.append("<option value='" + t + "' >" + self.sortTypes[t].name + "</option>");
            }
            $sortTd.append($sortSelect);
            $nline.append($sortTd);
            if(!portlet){
                // 操作td
                var $delTd = $("<td class=\"model-mask-panel-table-opt\"></td>");
                var $delSpan = $(" <p>"+modelingLang['modeling.page.delete']+"</p>");
                $delSpan.on("click", function () {
                    self.destroyLine(lineId);
                });
                $delTd.append($delSpan);
                $nline.append($delTd);
            }
            $valueTable.append($nline);
            return $nline;
        },
        destroyLine: function (id) {
            var self = this;
            var $valueTable = self.element.find("[mdlng-rltn-prprty-value=\"" + self.feildName + "\"]").find("tbody");
            $valueTable.find("[sort-tr-id='" + id + "']").remove();
        },
        draw: function () {
        },
        destroy: function ($super, cfg) {

        },
        filterFiled: function (info) {
            if (info.isClob){
                //Clob不处理
              return  true;
            }
            return info.type.indexOf("Attachment") > -1 || info.name.indexOf(".") > -1 || info.type.indexOf("com.landray.kmss.sys.org") > -1;
        },
        getKeyData: function () {
            var self = this;
            var $valueTable = self.element.find("[mdlng-rltn-prprty-value=\"" + self.feildName + "\"]").find("tbody");
            var keyData = []
            $valueTable.find("[mdlng-prprty-mark='fdOutSort']").each(function (idx, nl) {
                var lineData = self.getLineKeyData($(nl));
                if (lineData) {
                    keyData.push(lineData);
                }

            });
            return keyData;
        },
        getLineKeyData: function ($nline) {
            var keyData = {};
            keyData.name = {};
            keyData.expression = {};

            var $option = $nline.find(".sort_widget option:selected");
            if ($option.val() === "") {
                return null;
            }
            keyData.name.type = $option.attr("data-property-type");
            keyData.name.value = $option.val();
            keyData.name.text = $option.attr("title");

            var $option_sort = $nline.find(".sort_sort option:selected");
            if ($option_sort.val() === "") {
                return null;
            }
            keyData.expression.value = $option_sort.val();
            keyData.expression.text = $option_sort.text();
            return keyData;
        },

        initByStoreData: function (storeData,portlet) {
            //console.log(storeData);
            if (!storeData) {
                return
            }
            var self = this;
            for (var i in storeData) {
                var data = storeData[i];
                if (!data || !data.name || !data.name.value) {
                    continue;
                }

                if (this.filterFiled({
                    "name": data.name.value,
                    "type": data.name.type
                })) {
                    continue
                }
                var $nl = self.createNewLine(portlet);
                var $widgetSelect = $nl.find(".sort_widget");
                $widgetSelect.val(data.name.value);
                $widgetSelect.attr("title",data.name.text);
                var $sortSelect = $nl.find(".sort_sort");
                $sortSelect.val(data.expression.value);
            }
        }
    });

    exports.SortRecordGenerator = SortRecordGenerator;
});
