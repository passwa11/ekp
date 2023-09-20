/**
 * 页面全局对象
 */
define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var FieldMappingMark = require("sys/modeling/base/formlog/res/mark/fieldMappingMark");

    var RelationFMMark = FieldMappingMark.FieldMappingMark.extend({
        localMapping: {
            select: "render_select",
            where: "render_where",
            return: "render_return",
            relation: "render_relation",
            sort: "render_sort",
            out:"render_out",
            search:"render_search"
        },
        initProps: function ($super, cfg) {
            $super(cfg);
            if (cfg.fdId) {
                this.getNeedModifiedDataUrl = this.baseFMMarkUrl + "&fdComClass=com.landray.kmss.sys.modeling.base.model.SysModelingRelation&fdComId=" + cfg.fdId;
            } else {
                console.error("RelationFMMark no fdId")
            }

        },
        render_select: function (fieldList) {
            console.debug("render_select", fieldList);
            this.__renderTitle($("[mdlng-rltn-property=\"fdOutSelect\"]").find(".td_normal_title"));
        },
        render_where: function (fieldList) {
            console.debug("render_where", fieldList);
            var ele = $("[mdlng-rltn-property=\"fdInWhere\"]");
            this.__renderTitle(ele.find(".td_normal_title"));
            var $table = ele.find(".model-mask-panel-table-base >table");
            this.__renderTableSelect($table, "where_field", fieldList);
        },
        render_return: function (fieldList) {
            console.debug("render_return", fieldList);
            this.__renderTitle($("[mdlng-rltn-property=\"fdReturn\"]").find(".td_normal_title"));
        },
        render_relation: function (fieldList) {
            console.debug("render_relation", fieldList);
            this.__renderTitle($(".modeling_relation_fdRelation"));
        },
        render_sort: function (fieldList) {
            console.debug("render_sort", fieldList);
            var ele = $("[mdlng-rltn-property=\"fdOutSort\"]");
            this.__renderTitle(ele.find(".td_normal_title"));
            var $table = ele.find(".model-mask-panel-table-base >table");
            this.__renderTableSelect($table, "sort_widget", fieldList);
        },
        render_search: function (fieldList) {
            console.debug("render_search", fieldList);
            this.__renderTitle($("[mdlng-rltn-property=\"fdOutSearch\"]").find(".td_normal_title"));
        },
        render_out: function (fieldList) {
            console.debug("render_out", fieldList);
            var ele = $("[mdlng-rltn-property=\"fdOutParam\"]");
            this.__renderTitle(ele.find(".td_normal_title"));
            var $table = ele.find(".model-mask-panel-table-base >table");
            this.__renderTableSelect($table, "fdOutParam_main", fieldList);
            this.__renderTableSelect($table, "fdOutParam_passive", fieldList);
        }
    });
    exports.RelationFMMark = RelationFMMark;
});
