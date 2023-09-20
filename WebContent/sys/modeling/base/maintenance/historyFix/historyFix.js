/**
 * 左侧栏
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var topic = require("lui/topic");
    var dialog = require('lui/dialog');
    var maintenanceS1 = require("sys/modeling/base/maintenance/resource/listStyle1/maintenanceS1");

    var HistoryFix = maintenanceS1.MaintenanceS1.extend({

        startup: function ($super, cfg) {
            $super(cfg);

        },

        // 渲染完毕之后添加事件
        doRender: function ($super, html) {
            $super(html);
        },

        reRender: function () {
            this.source.get();
        },
        doCheck: function (selected, title) {
            if (!selected || !selected.forms || selected.forms.length == 0) {
                dialog.alert("请选中需要检测表单")
                return;
            }
            title += "表单的历史数据修复";
            dialog.iframe("/sys/modeling/base/maintenance/resource/dialog/selectItem.jsp", "请选择升级项", null, {
                width: 800,
                height: 700,
                params: {
                    "selected": JSON.stringify(selected),
                    "name": title,
                    "url": "sys/modeling/base/maintenance.do?method=doHistoryFix"
                }
            });
        },
        triggerByType: function (type) {
            this.element.find("li[data-aside-type='" + type + "']").trigger($.Event("click"));
        }

    })

    exports.HistoryFix = HistoryFix;
})