/**
 * 页面全局对象
 */
define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var FieldMappingMark = require("sys/modeling/base/formlog/res/mark/fieldMappingMark");

    var OperationFMMark = FieldMappingMark.FieldMappingMark.extend({
        localMapping: {
            view: "render_view",
            where: "render_where",
            inc:"render_inc"
        },
        initProps: function ($super, cfg) {
            $super(cfg);
            if (cfg.fdId) {
                this.getNeedModifiedDataUrl = this.baseFMMarkUrl + "&fdComClass=com.landray.kmss.sys.modeling.base.model." +
                    "SysModelingOperation&fdComId=" + cfg.fdId;
            } else {
                console.error("OperationFMMark no fdId")
            }
            //初始化1
        },
        render_view: function (fieldList) {
            console.debug("render_view", fieldList);
            var ele = $("[mdlng-prtn-property=\"fdView\"]");
            this.__renderTitle(ele.find(".td_normal_title"));
            var $table = ele.find(".model-mask-panel-table-base >table");
            var self = this;
            this.__renderTableSelect($table, "newViewInc_widget", fieldList);
            $table.find("tr").each(function (idx, tr) {
                var $tds = $(tr).find("td");
                if ($tds.length > 0) {
                    self.__renderFormulaOfTr(fieldList, $(tr));
                }
            });

        },
        render_inc: function (fieldList) {
            console.debug("render_inc", fieldList);
            var title= $("[mdlng-prtn-property=\"fdTrigger\"]").find(".td_normal_title")[0];
            this.__renderTitle($(title));
            var $table = $("[mdlng-prtn-prprty-value=\"fdTriggerInc\"]").find("table");
            var self = this;
            $table.find("tr").each(function (idx, tr) {
                self.__renderFormulaOfTr(fieldList, $(tr));
            });
        }
    });
    exports.OperationFMMark = OperationFMMark;
});
