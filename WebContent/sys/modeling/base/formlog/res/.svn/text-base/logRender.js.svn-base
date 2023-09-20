/**
 * 页面全局对象
 */
define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var base = require("lui/base");
    var topic = require("lui/topic");
    var logTranslator = require("sys/modeling/base/formlog/res/logChangeTranslator");
    var modelingLang = require("lang!sys-modeling-base");
    var LogRender = base.Container.extend({
        initProps: function ($super, cfg) {
            $super(cfg);
            this.fieldMapping = cfg.fieldMapping;
            this.formlogId = cfg.formlogId;
            this.mySwiper = cfg.mySwiper,
                this.logt = new logTranslator.LogTranslator(cfg);
            this.render();
            this.subscribe();
        },
        render: function () {
            this.renderChangeLog();
            this.renderMapping();
        },
        renderChangeLog: function () {
            var self = this;
            $("[mapping-log-mark=\"fdChangeLog\"]").each(function (idx, ele) {
                var changeLog = $(ele).find(".model-change-list-desc-hidden").val();
                var controlType = $(ele).find("[name=fdBussinessType]").val();
                controlType = controlType === "addressDialog"? "new_address":controlType;
                var controlText = controlType;
                if(Designer_Config.controls[controlType]){
                    controlText = Designer_Config.controls[controlType].info.name || controlType;
                    controlText = "(" + controlText + ")";
                }
                $(ele).parent().find(".fieldType").text(controlText);
                let fieldId = $(ele).closest(".model-change-content-item").attr("mapping-log-field");
                var obj = JSON.parse(changeLog);
                if(obj.describe){
                    obj.describe.controlType = controlType;
                    obj.describe.fieldId = fieldId;
                }
                var $describe = self.logt.translator(obj.describe);
                $(ele).append($describe);
            })
        },
        renderMapping: function () {
            var self = this;
            $(".model-change-content-item[mapping-log-field]").each(function (idx, ele) {
                var fieldId = $(ele).attr("mapping-log-field");
                if (fieldId && self.fieldMapping[fieldId]) {
                    self.logt.translatorMapping(fieldId, self.fieldMapping[fieldId]);
                    self.bindSwiper(fieldId)
                } else {
                    $("[ mapping-log-mapping='" + fieldId + "']").find(".model-change-slide-content").html("无关系映射");
                    $("[ mapping-log-mapping='" + fieldId + "']").find(".model-change-slide-header").hide();
                    var modifiedType = $(ele).find("[name='fdModifiedType_"+fieldId+"']").val();
                    //类型为修改
                    if (modifiedType === "1") {
                        var fdChangeLog = $(ele).find("[mapping-log-mark='fdChangeLog'] .model-change-list-desc").text();
                        //修改操作时，如果修改内容为空，映射关系为空，则不显示
                        if(!fdChangeLog){
                            $(ele).hide();
                        }
                    }
                }
            })
        },
        bindSwiper: function (id) {
            var swiper = new this.mySwiper(".swiper-container-" + id, {
                calculateHeight: true,
                // autoResize:false,
                slidesPerView: 'auto',
                // slidesPerGroup : 1,

            });
        },
        subscribe: function () {
            var self = this;
            // 监听移动组件激活时间
            topic.channel("modelingFormLog").subscribe("childrenChange", function (arg) {
                var fieldId = arg.data.fdFieldId;
                var url = Com_Parameter.ContextPath + "sys/modeling/base/modelingFormModified.do?method=getFieldMapping";
                url = url + "&formId=" + self.formlogId;
                url = url + "&fieldId=" + fieldId;
                $.ajax({
                    url: url,
                    method: 'GET',
                    async: true
                }).success(function (resultStr) {
                    console.log(resultStr);
                    if (resultStr) {
                        var obj = JSON.parse(resultStr);
                        if (obj.status === "error" && !obj.fieldMapping) {
                            console.error(obj)
                            return;
                        }
                        //重置
                        var $mapplingLine = $("[ mapping-log-mapping='" + fieldId + "']");
                        var $slideContainer = $mapplingLine.find(".model-change-slide-content-wrap");
                        var $swiperContainer = $mapplingLine.find(".swiper-wrapper");
                        $slideContainer.empty()
                        $swiperContainer.empty()
                        //重绘
                        if (fieldId && obj.fieldMapping[fieldId]) {
                            self.logt.translatorMapping(fieldId, obj.fieldMapping[fieldId])
                        } else {
                            $("[ mapping-log-mapping='" + fieldId + "']").find(".model-change-slide-content").html(modelingLang['modelingLog.unrelated.mapping']);
                            $("[ mapping-log-mapping='" + fieldId + "']").find(".model-change-slide-header").hide();
                            $swiperContainer.hide();
                        }
                    }

                });

            }, this);
        }
    });

    exports.LogRender = LogRender;
});
