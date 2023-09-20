/**
 * 左侧栏
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var topic = require("lui/topic");
    var dialog = require('lui/dialog');
    var maintenanceS1 = require("sys/modeling/base/maintenance/resource/listStyle1/maintenanceS1");
    var modelingLang = require("lang!sys-modeling-base");
    var CfgUpgrade = maintenanceS1.MaintenanceS1.extend({

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
        doCheck: function (selected,title) {
            if (!selected || !selected.forms || selected.forms.length == 0) {
                dialog.alert(modelingLang['modeling.select.form.needs.detected'])
                return;
            }
            title+=modelingLang['modeling.configuration.item.upgrade'];
            $.ajax({
                    url: Com_Parameter.ContextPath + "sys/modeling/base/maintenance.do?method=doCfgUpgrade",
                    type: "POST",
                    data: {"selected": JSON.stringify(selected),"name":title},
                    dataType: "json",
                    async: false,
                    success: function (result) {
                        if (result && result.success) {
                            dialog.success(result.msg);
                        } else {
                            dialog.alert(modelingLang['modeling.page.operation.failed']+"【" + result.code + "】" + result.msg);
                        }
                        window.location.reload();
                    }
                }
            );
        },
        triggerByType: function (type) {
            this.element.find("li[data-aside-type='" + type + "']").trigger($.Event("click"));
        }

    })

    exports.CfgUpgrade = CfgUpgrade;
})