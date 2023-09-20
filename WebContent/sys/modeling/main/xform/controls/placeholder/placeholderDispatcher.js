/**
 * 占位符控件
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var strutil = require('lui/util/str');
    var env = require('lui/util/env');
    var placeholderWgts = require("sys/modeling/main/xform/controls/placeholder/placeholderWgts");
    var relationUtil = require("sys/modeling/main/xform/controls/placeholder/relationUtil");

    var PlaceholderDispatcher = base.Container.extend({

        tmpWgtType: {
            "0": "dialog",
            "1": "multiDialog",
            "2": "select",
            "3": "radio",
            "4": "checkbox",
            "5": "select",
            "5": "radioTree",
            "6": "multiTree",
            "7": "fillingDialog",
            "8": "fillingMultiDialog"
        },

        initialize: function ($super, cfg) {
            $super(cfg);
            // 控件值, 状态, 获取配置信息
            var cfg = this.config;
            this.controlIdWithIndex = cfg.controlId;
            cfg.controlId = XForm_FormatControlIdWithNoIndex(cfg.controlId);
            var controlIdWithNoPrefix = XForm_FormatControlIdWithNoPrefix(cfg.controlId);
            var inputNode = this.element.parent("xformflag").find("input[name$='" + controlIdWithNoPrefix + ")']");
            var textNode = this.element.parent("xformflag").find("input[name*='" + controlIdWithNoPrefix + "_text']");
            this.element.css("display", "inline-block");
            // 根据配置项设置宽度
            if (cfg.width) {
                if ((cfg.width == "0" || cfg.width == "0%") && cfg.status == "view") {
                    //#122023 宽度为0时 查看视图应隐藏
                    this.element.css("display", "none");
                } else {
                    if (cfg.width.indexOf("%") > -1) {
                        this.element.css("width", cfg.width);
                    } else {
                        this.element.css("width", cfg.width + 'px');
                    }
                }

            }
            // 获取配置信息
            var fdModelId = $("input[name='fdModelId']").val();
            cfg.fdModelId = fdModelId;
            var relationCfg = this.getRelationCfg(fdModelId);
            if (cfg.status === "edit") {
                this.buildEdit(relationCfg, cfg, inputNode, textNode);
            } else {
                this.buildView(relationCfg, cfg, inputNode, textNode);
            }
        },

        startup: function ($super, cfg) {
            $super(cfg);
            if (this.config.status === "edit" && this.placeholderWgt) {
                this.placeholderWgt.on("html", this.doRender, this);
                this.placeholderWgt.startup();
            }
        },

        // 构建编辑页面的元素
        buildEdit: function (relationCfg, cfg, inputNode, textNode) {
            if (relationCfg.status === '00') {
                var placeHolderWgtType = this.tmpWgtType[relationCfg["showType"]];
                var isMulti = false;
                if (placeHolderWgtType == 'radioTree') {
                    placeHolderWgtType = "tree";
                } else if (placeHolderWgtType == 'multiTree') {
                    placeHolderWgtType = "tree";
                    isMulti = true;
                }
                if(this.config.fillingType == "filling"){
                    if(relationCfg["showType"] == "0"){
                        placeHolderWgtType = this.tmpWgtType[7];
                    }else{
                        placeHolderWgtType = this.tmpWgtType[8];
                    }
                }
                this.placeholderWgt = new placeholderWgts[strutil.upperFirst(placeHolderWgtType) + "Placeholder"]({
                    parent: this,
                    envInfo: relationCfg,
                    isMultiTree: isMulti,
                    fillingType: cfg.fillingType
                });
                // 绑定值
                this.placeholderWgt.inputNode = inputNode;
                this.placeholderWgt.value = inputNode.val() || "";
                this.placeholderWgt.textNode = textNode;
                this.placeholderWgt.maxWidth = this.element.width();
                this.placeholderWgt.required = cfg.required;
                this.placeholderWgt.controlId = cfg.controlId;

                this.addChild(this.placeholderWgt);

            } else if (relationCfg.status === '01') {
                console.warn("业务关联控件(" + cfg.controlId + ")没有在后台配置业务关联！");
            }
        },

        // 构建查看页面的元素
        buildView: function (relationCfg, cfg, inputNode, textNode) {
            // 查看视图
            if (relationCfg.status === '00') {
                var through = relationCfg.through || {};
                if (through.isThrough) {
                    var values = inputNode.val() || "";
                    var texts = textNode.val() || "";
                    this.buildDrillingDom(values, texts, through);
                } else {
                    //#167406 防止html代码注入
                    var tmpDom = $("<span>").text(textNode.val());
                    this.element.append(tmpDom);
                }
            }
        },

        buildDrillingDom: function (values, texts, info) {
            var self = this;
            var valArr = values.split(";");
            var txtArr = texts.split("&&");
            if(valArr.length != txtArr.length && valArr.length > 0){
                //#150644 兼容多选树穿透
                txtArr = texts.split(",");
            }
            if (valArr.length === txtArr.length && valArr.length > 0) {
                var isExitVals = this.isExitView(valArr, info.url);
                for (var i = 0; i < valArr.length; i++) {
                    var val = valArr[i];
                    var txt = txtArr[i];
                    if (val) {
                        if (isExitVals && isExitVals.indexOf(val) >= 0) {
                            //防止html代码注入
                            var $drilling = $("<div class='lui-placeholder-drilling' fd_model_id='"+ val +"'></div>").text(txt).appendTo(this.element);
                            $drilling.on("click", function () {
                                var modelIdVar =  $(this).attr("fd_model_id");
                                var targetUrl = self.getDrillingUrl(info.url, modelIdVar);
                                Com_OpenWindow(targetUrl, "_blank");
                            });
                        } else {
                            //防止html代码注入
                            var tmpDom = $("<span>").text(txt);
                            this.element.append(tmpDom);
                        }
                    }else{
                        //#130752 当id为空时 不做穿透
                        //防止html代码注入
                        var tmpDom = $("<span>").text(txt);
                        this.element.append(tmpDom);
                    }
                }
            } else {
                //防止html代码注入
                var tmpDom = $("<span>").text(texts);
                this.element.append(tmpDom);
                console.error("【业务关联控件】实际值长度和显示值长度不一致,没法穿透!");
            }
        },

        isExitView: function (valArr, url) {
            var self = this;
            var isExitVals = [];
            if (valArr && url) {
                url = env.fn.formatUrl(url.replace(/\:fdId/g, "").replace(/\method=\w+/g, "method=isExitView"));
                $.ajax({
                    url: url,
                    data: {
                        "ids": valArr.join(";")
                    },
                    async: false,
                    dataType: "json",
                    cache: false,
                    success: function (rtn) {
                        if (rtn) {
                            for (var i = 0; i < rtn.length; i++) {
                                if (rtn[i].isExit && rtn[i].isExit == "true") {
                                    isExitVals.push(rtn[i].fdId);
                                }
                            }
                        }
                    }
                });
            }
            return isExitVals;
        },

        // 获取穿透链接
        getDrillingUrl: function (tmpUrl, value) {
            var url = "";
            if (value && tmpUrl) {
                url = env.fn.formatUrl(tmpUrl.replace(/\:fdId/g, value));
            } else {
                console.error("【业务关联控件】获取穿透跳转链接失败!跳转的模板链接:" + tmpUrl + ";fdId:" + idInfo.value);
            }
            return url;
        },

        getRelationCfg: function (fdAppModelId) {
            var cfg = relationUtil.get(fdAppModelId);
            var rs = {};
            if (cfg.hasOwnProperty(this.config.controlId)) {
                rs = cfg[this.config.controlId];
            }
            return rs;
        },

        doRender: function (html) {
            this.element.append(html);
            if (this.config.status === "edit" && this.placeholderWgt) {
                this.placeholderWgt.emit("render_finish");
            }
        },

        updateTextView: function () {
            this.placeholderWgt.updateTextView();
        }
    });

    exports.PlaceholderDispatcher = PlaceholderDispatcher;
})