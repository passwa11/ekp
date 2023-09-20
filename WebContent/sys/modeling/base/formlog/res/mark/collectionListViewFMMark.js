/**
 * 列表编辑视图的字段校验js
 */
define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var FieldMappingMark = require("sys/modeling/base/formlog/res/mark/fieldMappingMark");
    var CollectionListViewFMMark = FieldMappingMark.FieldMappingMark.extend({
        localMapping: {
            select: "render_select",
            search: "render_search"
        },
        initProps: function ($super, cfg) {
            $super(cfg);
            if (cfg.fdId) {
                this.getNeedModifiedDataUrl = this.baseFMMarkUrl + "&fdComClass=";
                this.getNeedModifiedDataUrl += "com.landray.kmss.sys.modeling.base.views.collection.model.ModelingAppCollectionView&fdComId=";
                this.getNeedModifiedDataUrl += cfg.fdId;
            } else {
                console.error("CollectionListViewFMMark no fdId");
            }
        },
        startup: function ($super, cfg) {
            setTimeout(function () {
                $super(cfg);
            }, 800);

        },
        render_select: function (fieldList) {
          /* this.__renderTitle($(".td_normal_title[data-lui-position=\"fdDisplay\"]"));*/
            var $titleBlock = $("div[data-lui-position=\"fdDisplay\"]");
            this.__renderTitle($titleBlock);
        },
    });
    exports.CollectionListViewFMMark = CollectionListViewFMMark;
});
