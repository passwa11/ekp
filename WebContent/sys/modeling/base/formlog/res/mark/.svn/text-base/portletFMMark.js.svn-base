/**
 * 页面全局对象
 */
define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var FieldMappingMark = require("sys/modeling/base/formlog/res/mark/fieldMappingMark");

    var  PortletFMMark = FieldMappingMark.FieldMappingMark.extend({
        localMapping: {
            inc: "render_inc"
        },
        initProps: function ($super, cfg) {
            $super(cfg);
            if (cfg.fdId) {
                this.getNeedModifiedDataUrl = this.baseFMMarkUrl + "&fdComClass=com.landray.kmss.sys.modeling.base.model." +
                    "ModelingPortletCfg&fdComId=" + cfg.fdId;
            } else {
                console.error("PortletFMMark no fdId")
            }
            //初始化1
        },
        render_inc: function (fieldList) {
            console.debug("render_inc", fieldList);
            var title= $("[data-portlet-area=\"dataMapping\"]").find(".td_normal_title")[0];
            this.__renderTitle($(title));
            var $table = $("[data-portlet-area=\"dataMapping-val-table\"]");
            var self = this;
            $table.find("tr").each(function (idx, tr) {
                self.__renderFormulaOfTr(fieldList, $(tr));
            });
        }
    });
    exports.PortletFMMark = PortletFMMark;
});
