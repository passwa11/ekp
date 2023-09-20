/**
 * 页面全局对象
 */
define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var FieldMappingMark = require("sys/modeling/base/formlog/res/mark/fieldMappingMark");

    var GanttFMMark = FieldMappingMark.FieldMappingMark.extend({
        localMapping: {
            select: "render_select",
            sort: "render_sort",
            where: "render_where",
            search: "render_search",
            start_time: "render_start_time",
            end_time: "render_end_time",
            progress: "render_progress",
            show: "render_show"
        },
        initProps: function ($super, cfg) {
            $super(cfg);
            if (cfg.fdId) {
                this.getNeedModifiedDataUrl = this.baseFMMarkUrl + "&fdComClass=com.landray.kmss.sys.modeling.base.business.model.ModelingGantt&fdComId=" + cfg.fdId;
            } else {
                console.error("RelationFMMark no fdId")
            }
        },
        startup: function ($super, cfg) {
            setTimeout(function () {
                $super(cfg);
            }, 800);

        },
        render_select: function (fieldList) {
            console.debug("render_select", fieldList);
            this.__renderTitle($(".onlineTitle[data-lui-position=\"fdDisplay\"]"));
        },
        render_sort: function (fieldList) {
            console.debug("render_sort", fieldList);
            var $titleBlock = $(".td_normal_title[data-lui-position=\"fdOrderBy\"]");
            this.__renderTitle($titleBlock);
            var $table = $("#xform_main_data_orderbyTable");
            if (!$table || this.isMobile) {
                $table = $("[data-table-type=\"order\"]");
            }
            this.__renderTableSelect($table, "inputsgl", fieldList);
        },
        render_where: function (fieldList) {
            console.debug("render_where", fieldList);
            var $titleBlock = $(".td_normal_title[data-lui-position=\"fdWhereBlock\"]");
            if (!$titleBlock || this.isMobile){
                $titleBlock = $(".td_normal_title[data-lui-position=\"fdWhere\"]");
            }
            this.__renderTitle($titleBlock);
            var $table = $("#xform_main_data_whereTable");
            if (!$table || this.isMobile) {
                $table = $("[data-table-type=\"where\"]");
            }
            this.__renderTableSelect($table, "inputsgl", fieldList);
        },
        render_search: function (fieldList) {
            console.debug("render_search", fieldList);
            this.__renderTitle($(".onlineTitle[data-lui-position=\"fdCondition\"]"));
        },
        render_start_time: function (fieldList) {
            console.debug("render_start_time", fieldList);
            this.__renderTitle($(".onlineTitle[data-lui-position=\"fdStartTime\"]"));
        },
        render_end_time: function (fieldList) {
            console.debug("render_end_time", fieldList);
            this.__renderTitle($(".onlineTitle[data-lui-position=\"fdEndTime\"]"));
        },
        render_progress: function (fieldList) {
            console.debug("render_progress", fieldList);
            this.__renderTitle($(".onlineTitle[data-lui-position=\"fdProgress\"]"));
        },
        render_show: function (fieldList) {
            console.debug("render_show", fieldList);
            this.__renderTitle($(".onlineTitle[data-lui-position=\"fdShow\"]"));
        },
    });
    exports.GanttFMMark = GanttFMMark;
});
