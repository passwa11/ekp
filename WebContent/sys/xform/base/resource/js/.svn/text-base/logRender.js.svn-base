/**
 * 页面全局对象
 */
define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var base = require("lui/base");
    var topic = require("lui/topic");
    var logTranslator = require("sys/xform/base/resource/js/logChangeTranslator");
    var LogRender = base.Container.extend({
        initProps: function ($super, cfg) {
            $super(cfg);
            this.fieldMapping = cfg.fieldMapping;
            this.formlogId = cfg.formlogId;
            this.logt = new logTranslator.LogTranslator(cfg);
            this.render();
        },
        render: function () {
            this.renderChangeLog();
        },
        renderChangeLog: function () {
            var self = this;
            $("[mapping-log-mark=\"fdChangeLog\"]").each(function (idx, ele) {
                var changeLog = $(ele).find(".model-change-list-desc-hidden").val();
                var controlType = $(ele).closest("tr").find("[name='fdBussinessType']").val();
                var controlText = Designer_Config.controls[controlType].info.name || controlType;
           	 $(ele).closest("tr").find(".controlType").text(controlText);
                if (changeLog) {
                	 var obj = JSON.parse(changeLog);
                	 obj.controlType = controlType;
                     var $describe = self.logt.translator(obj);
                     $(ele).append($describe);
                }
            })
        }
    });

    exports.LogRender = LogRender;
});
