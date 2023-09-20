/**
 * 页面全局对象
 */
define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var FieldMappingMark = require("sys/modeling/base/formlog/res/mark/fieldMappingMark");

    var ResPanelFMMark = FieldMappingMark.FieldMappingMark.extend({
        localMapping: {
            match_col: "render_matchCol",
            match_row: "render_matchRow",
            table_col: "render_tableCol",
            table_row: "render_tableRow",
            show: "render_show",
            dialog: "render_dialog",
            color: "render_color",
            search: "render_search",
            where: "render_where",
            where_target: "render_whereTarget"
        },
        initProps: function ($super, cfg) {
            $super(cfg);
            if (cfg.fdId) {
                this.getNeedModifiedDataUrl = this.baseFMMarkUrl + "&fdComClass=com.landray.kmss.sys.modeling.base.business.model.ModelingResourcePanel&fdComId=" + cfg.fdId;
            } else {
                console.error("RelationFMMark no fdId")
            }
        },
        startup: function ($super, cfg) {
            setTimeout(function () {
                $super(cfg);
            }, 800);

        },
        render_matchCol: function (fieldList) {
            console.debug("render_matchCol", fieldList);
            this.__renderTitle($("[data-fm-position=\"matchCol\"]").find(".onlineTitle"));
        },
        render_matchRow: function (fieldList) {
            console.debug("render_matchRow", fieldList);
            this.__renderTitle($("[data-fm-position=\"matchRow\"]").find(".onlineTitle"));
        },
        render_show: function (fieldList) {
            console.debug("render_show", fieldList);
            this.__renderTitle($("[data-fm-position=\"show\"]").find(".onlineTitle"));
        },
        render_dialog: function (fieldList) {
            console.debug("render_dialog", fieldList);
            var self = this;
            this.__renderTitle($("[data-fm-position=\"dialog\"]").find(".td_normal_title"));
            var $dlg = $("#source_dialog_table");
            $dlg.find("tbody>tr").each(function () {
                if (self.__judgeIn($(this), fieldList)) {
                    $(this).find("td:first").css(self.markCss.titleRed);
                }
            });
        },
        render_search: function (fieldList) {
            console.debug("render_search", fieldList);
            this.__renderTitle($("[data-lui-local=\"fdCondition_local\"]").find(".td_normal_title"));
        },
        render_tableCol: function (fieldList) {
            console.debug("render_select", fieldList);
            if (this.render_tableCategory($("#_resPanel_table_colTitle"), fieldList)) {
                $("[data-lui-local='tableRow_local']").find(".td_normal_title").css(this.markCss.titleRed);
            }
        },
        render_tableRow: function (fieldList) {
            console.debug("render_select", fieldList);
            if (this.render_tableCategory($("#_resPanel_table_rowTitle"), fieldList)) {
                $("[data-lui-local='tableCol_local']").find(".td_normal_title").css(this.markCss.titleRed);
            }
        },
        render_tableCategory: function ($ele, fieldList) {
            var isMark = false;
            if (this.__judgeIn($ele.find("[rp-table-mark=\"categoryFieldText\"]"), fieldList)) {
                $ele.find("[rp-table-mark=\"categoryFieldText\"]").find(".onlineTitle").css(this.markCss.titleRed);
                isMark = true;
            }
            if (this.__judgeIn($ele.find("[rp-table-mark=\"categoryFieldValue\"]"), fieldList)) {
                $ele.find("[rp-table-mark=\"categoryFieldText\"]").find(".onlineTitle").css(this.markCss.titleRed);
                isMark = true;
            }
            return isMark;
        },
        render_where: function (fieldList) {
            console.debug("render_where", fieldList);
            var $titleBlock = $("[data-lui-local=\"fdTableWhereBlock\"]").find(".td_normal_title");
            this.__renderTitle($titleBlock);
            var $table = $("#table_whereTable");
            this.__renderTableSelect($table, "inputsgl", fieldList);
        },
        render_whereTarget: function (fieldList) {
            console.debug("render_whereTarget", fieldList);
            var $titleBlock = $("[data-lui-local=\"fdSourceWhereBlock\"]").find(".td_normal_title");
            this.__renderTitle($titleBlock);
            var $table = $("#source_whereTable");
            this.__renderTableSelect($table, "inputsgl", fieldList);
        },
        render_color: function (fieldList) {
            console.debug("render_color", fieldList);
            var $titleBlock = $("[data-lui-local=\"fdColor_local\"]").find(".td_normal_title");
            this.__renderTitle($titleBlock);
            var $table = $("#source_colorTable");
            this.__renderTableSelect($table, "inputsgl", fieldList);
        }

    });
    exports.ResPanelFMMark = ResPanelFMMark;
});
