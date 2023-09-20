/**
 * 左侧栏
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var topic = require("lui/topic");
    var dialog = require('lui/dialog');
    var modelingLang = require("lang!sys-modeling-base");
    var cfgUpgrade = require("sys/modeling/base/maintenance/cfgUpgrade/cfgUpgrade");
    var MultiAddrMigration = cfgUpgrade.CfgUpgrade.extend({

        doCheck: function (selected,title) {
            if (!selected || !selected.forms || selected.forms.length == 0) {
                dialog.alert(modelingLang['modeling.select.form.needs.detected'])
                return;
            }
            title+=modelingLang['modeling.Multi-choice.address.forms'];
            $.ajax({
                    url: Com_Parameter.ContextPath + "sys/modeling/base/maintenance.do?method=doMultiAddrMigration",
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
    })

    exports.MultiAddrMigration = MultiAddrMigration;
})