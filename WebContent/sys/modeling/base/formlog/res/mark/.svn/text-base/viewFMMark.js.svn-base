/**
 * 页面全局对象
 */
define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var FieldMappingMark = require("sys/modeling/base/formlog/res/mark/fieldMappingMark");

    var ViewFMMark = FieldMappingMark.FieldMappingMark.extend({
        localMapping: {
            operation: "render_operation"
        },
        initProps: function ($super, cfg) {
            $super(cfg);
            if (cfg.fdId) {
                this.getNeedModifiedDataUrl = this.baseFMMarkUrl + "&fdComClass=";
                this.getNeedModifiedDataUrl += "com.landray.kmss.sys.modeling.base.model.ModelingAppView&fdComId=";
                this.getNeedModifiedDataUrl += cfg.fdId;
                if (cfg.isMobile) {
                    this.isMobile = cfg.isMobile;
                    this.getNeedModifiedDataUrl += "&fdComName=sys-modeling-base:table.modelingAppMobileview";
                }
            } else {
                console.error("ViewFMMark no fdId")
            }
        },
        startup: function ($super, cfg) {
            setTimeout(function () {
                $super(cfg);
            }, 800);

        },
        render_operation: function (fieldList) {
            console.debug("render_operation", fieldList);
            var $titleBlock = $(".td_normal_title[data-lui-position=\"fdOperation\"]");
            this.__renderTitle($titleBlock);
            var $contianer = $(".model-edit-view-oper-content-item");
            this.__renderContainerSelect($contianer, "inputsgl", fieldList);
        }
    });
    exports.ViewFMMark = ViewFMMark;
});
