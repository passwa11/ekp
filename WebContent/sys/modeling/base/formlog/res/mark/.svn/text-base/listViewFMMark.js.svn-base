/**
 * 页面全局对象
 */
define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var FieldMappingMark = require("sys/modeling/base/formlog/res/mark/fieldMappingMark");

    var ListViewFMMark = FieldMappingMark.FieldMappingMark.extend({
        localMapping: {
            select: "render_select",
            search: "render_search",
            sort: "render_sort",
            where: "render_where",
            subject:"render_subject",
            summary:"render_select"
        },
        initProps: function ($super, cfg) {
            $super(cfg);
            if (cfg.fdId) {
                this.getNeedModifiedDataUrl = this.baseFMMarkUrl + "&fdComClass=";
                if (cfg.isMobile) {
                    this.isMobile = cfg.isMobile;
                    this.getNeedModifiedDataUrl += "com.landray.kmss.sys.modeling.base.mobile.model.ModelingAppMobileListView&fdComId=";
                } else {
                    this.getNeedModifiedDataUrl += "com.landray.kmss.sys.modeling.base.model.ModelingAppListview&fdComId=";
                }
                this.getNeedModifiedDataUrl += cfg.fdId;
            } else {
                console.error("ListViewFMMark no fdId")
            }
        },
        startup: function ($super, cfg) {
            setTimeout(function () {
                $super(cfg);
            }, 800);

        },
        render_search: function (fieldList) {
            console.debug("render_search", fieldList);
            this.__renderTitle($(".td_normal_title[data-lui-position=\"fdCondition\"]"));
        },
        render_select: function (fieldList) {
            console.debug("render_select", fieldList);
            this.__renderTitle($(".td_normal_title[data-lui-position=\"fdDisplay\"]"));
            this.__renderTitle($(".td_normal_title[data-lui-position=\"fdDisplayCss\"]"));
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
        render_subject: function (fieldList) {
            console.debug("render_subject", fieldList);
            this.__renderTitle($(".td_normal_title[data-lui-position=\"fdSubject\"]"));
        }
    });
    exports.ListViewFMMark = ListViewFMMark;
});
