/**
 * 业务空间
 */
define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var base = require("lui/base");
    var dialog = require('lui/dialog');
    var topic = require("lui/topic");
    //公式定义器
    var formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder");
    var modelingLang = require("lang!sys-modeling-base");
    //用于外部调用函数时改变this指向；
    var ___rpThis = undefined;
    var spaceMindMap = base.Container.extend({
        //常量
        cst: {
            $container: $("#mindMapEditTable"),
            $temp: $("#mindMapEdit"),
            rpClickFun: {}
        },
        /**
         * 初始化
         * cfg应包含：
         * data:数据内容：可选
         */
        initProps: function ($super, cfg) {
            this.loading = dialog.loading();
            $super(cfg);
            //初始化1
            cfg.cst = this.cst;
            cfg.parent = this;
            this.bindEvent();
            this.build(cfg);
            //用于外部调用函数时改变this指向；
            ___rpThis = this;
            if (cfg.fdConfig) {
                this.__storeData = JSON.parse(cfg.fdConfig);
                this.initByStoreData(this.__storeData)
            }
            this.loading.hide();
        },
        /**
         * 绑定事件
         */
        bindEvent: function () {
            var self = this;
            //右侧导航
            $(".model-edit-view-bar").find("div").on("click", function (e) {
                e.stopPropagation();
                $(".model-edit-view-bar").find("div").removeClass("barActive");
                var $t = $(this);
                $t.addClass("barActive");
                var mark = $t.attr("resPanel-bar-mark");
                $(".resPanel-bar-content").hide();
                $("[resPanel-bar-content='" + mark + "']").show();
                $(".sp-container").addClass("sp-hidden");
            });
            $(".model-edit-view-bar").find("[resPanel-bar-mark='basic']").trigger($.Event("click"));

            //名称
            $("[name='fdName']").focus(function () {
                $(".mind-map-fdName").closest("td").find(".validation-container").remove();
                $(".mind-map-fdName").closest("td").find(".validation-advice").remove();
            })
            $(".model-mind-map-link").find("[name='fdLink']").on("focus",function () {
                $(this).closest(".model-mind-map-link").find(".validation-container").remove();
            });
        },

        build: function () {
            var cfg = {
                $temp: this.cst.$container.find("#mindMapTableDom"),
                parent: this
            }
        },
        startup: function ($super, cfg) {
            $super(cfg);
        },
        //获取传到后台去的参数方法
        getKeyData: function () {
            var fdRootNode = {};
            var fdRootNodeName = this.cst.$container.find("#mindMapTableDom").find("[name='fdRootName']").val();
            fdRootNode.fdRootNodeName = fdRootNodeName;
            return {
                fdRootNode: fdRootNode
            }
        },
        //后台数据渲染方法
        initByStoreData: function (sd) {
            var $table = this.cst.$container.find("#mindMapTableDom");
        }
    });
    window.MindMapValidate = {
        validate: function (cfg) {
            var fdName = $("[name='fdName']").val();
            if (!fdName) {
                $(".model-edit-view-bar").find("[respanel-bar-mark='basic']").trigger("click");
                $(".validation-container").remove();
                $(".validation-advice").remove();
                $(".mind-map-fdName").append($("<div class='validation-container'><div class=\"validation-advice\" _reminder=\"true\">" +
                    "<table class=\"validation-table\"><tbody><tr><td>" +
                    "<div class=\"lui_icon_s lui_icon_s_icon_validator\"></div></td>" +
                    "<td class=\"validation-advice-msg\"><span class=\"validation-advice-title\">"+modelingLang['modelingMindMap.name']+" </span>" +
                    modelingLang['kmReviewMain.notNull']+"</td></tr></tbody></table></div></div>"));
                $(".model-edit-view-bar").find("[respanel-bar-mark='basic']").trigger("click");
                return modelingLang['respanel.name.cannot.empty'];
            }
            var fdRootName = $("[name='fdRootName']").val();
            if (!fdRootName) {
                $(".validation-container").remove();
                //超长提示框
                $(".root-name").append($("<div class='validation-container'><div class=\"validation-advice\" _reminder=\"true\">" +
                    "<table class=\"validation-table\"><tbody><tr><td>" +
                    "<div class=\"lui_icon_s lui_icon_s_icon_validator\"></div></td>" +
                    "<td class=\"validation-advice-msg\"><span class=\"validation-advice-title\">"+modelingLang['modelingMindMap.name']+"</span>" +
                    modelingLang['kmReviewMain.notNull']+"</td></tr></tbody></table></div></div>"));
                $(".model-edit-view-bar").find("[respanel-bar-mark='frame']").trigger("click");
                return modelingLang['modelingMindMap.root.node.cannot.null'];
            }
            var dataConfig = window.mindMap.getKeyData();
            var data = dataConfig.fdOtherNode;
            var connectRootflag = false;
            for (var i = 0; i < data.length; i++) {
                //目标表单校验
                if(data[i].fdTargetModelId == ""){
                    dialog.alert(modelingLang['modelingMindMap.root.node.not.configured']);
                    return modelingLang['modelingMindMap.select.target.form'];
                }
                //校验目标表单的合法性
                var url = Com_Parameter.ContextPath + "sys/modeling/base/modeling.do?method=getModelWidget&modelId=" + data[i].fdTargetModelId;
                $.ajax({
                    url: url,
                    type: "get",
                    async: false,
                    success: function (data, status) {
                        if (data && JSON.parse(data).code != "200") {
                            dialog.alert(modelingLang['modelingMindMap.target.form.data.error']);
                            return;
                        }
                    }
                });

                if (data[i].fdIsConnectRoot == "1") {
                    connectRootflag = true;
                } else if (data[i].fdIsConnectRoot != "1") {
                    if (data[i].fdPreNode.length == 0) {
                        dialog.alert(modelingLang['modelingMindMap.root.node.notConnected']);
                        return modelingLang['modelingMindMap.root.node.notConnected'];
                    }
                }
            }
            if (!connectRootflag) {
                dialog.alert(modelingLang['modelingMindMap.otherNode.configuration.cannotconnect.rootNote']);
                return modelingLang['modelingMindMap.otherNode.configuration.cannotconnect.rootNote'];
            }
            const linkRe = /^(?:http(s)?:\/\/)?[\w.-]+(?:\.[\w\.-]+)+[\w\-\._~:/?#[\]@!\$&'\(\)\*\+,;=.]+$/
            const linkRe_insys = /^\/[a-zA-Z0-9\w-./?%&=]+/;
            var linkValue = cfg.fdRootNode.fdLink.replace(/(^\s+)|(\s+$)/g,"");
            if (cfg.fdRootNode.fdViewEnable == "1" && !linkRe.test(linkValue) && !linkRe_insys.test(linkValue)) {
                if(linkValue== ""){
                    dialog.alert(modelingLang['modelingMindMap.custom.link.cannot.empty']);
                    return modelingLang['modelingMindMap.custom.link.cannot.empty'];
                }
                dialog.alert(modelingLang['modelingMindMap.custom.link.format.error']);
                return modelingLang['modelingMindMap.custom.link.format.error'];
            }

        },
        validate4TreeView : function (cfg) {
            var fdName = $("[name='fdName']").val();
            if (!fdName) {
                $(".model-edit-view-bar").find("[respanel-bar-mark='basic']").trigger("click");
                $(".validation-container").remove();
                $(".validation-advice").remove();
                $(".mind-map-fdName").append($("<div class='validation-container'><div class=\"validation-advice\" _reminder=\"true\">" +
                    "<table class=\"validation-table\"><tbody><tr><td>" +
                    "<div class=\"lui_icon_s lui_icon_s_icon_validator\"></div></td>" +
                    "<td class=\"validation-advice-msg\"><span class=\"validation-advice-title\">"+modelingLang['modelingMindMap.name']+" </span>" +
                    modelingLang['kmReviewMain.notNull']+"</td></tr></tbody></table></div></div>"));
                $(".model-edit-view-bar").find("[respanel-bar-mark='basic']").trigger("click");
                return modelingLang['respanel.name.cannot.empty'];
            }
        }
    };
    exports.spaceMindMap = spaceMindMap;
});
