/**
 * 资源面板类
 */
define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var base = require("lui/base");
    var dialog = require('lui/dialog');
    var listViewEditCommon = require("sys/modeling/base/resources/js/views/collection/listViewEditCommon");
    var ModelingCalendar = listViewEditCommon.ListViewEditCommon.extend({

        /**
         * 初始化
         * cfg应包含：
         * xformId ：用于指定当前表单的xform模版
         * modelMainId：当前表单id
         * modelTargetId：目标表单id，可选
         * data:数据内容：可选
         */
        initProps: function ($super, cfg) {
            this.loading = dialog.loading();
            $super(cfg);
            //初始化1
            this.flowInfo = cfg.flowInfo;
            this.widgets = this.config.widgets;
            cfg.parent = this;
            this.xformId = this.config.xformId;

            this.loading.hide()
        } ,
        startup: function ($super, cfg) {
            $super(cfg);
        },
        //获取传到后台去的参数方法
        getKeyData: function ($super) {
            return $super();
        },
    });
    exports.ModelingCalendar = ModelingCalendar;
});
