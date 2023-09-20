/**
 * 甘特图类
 */
define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var base = require("lui/base");
    var dialog = require('lui/dialog');
    //公式定义器
    var formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder");
    //自定义参数
    var ganttTable = require("sys/modeling/base/views/business/res/ganttTable");
    var modelingLang = require("lang!sys-modeling-base");
    //用于外部调用函数时改变this指向；
    var ___rpThis = undefined;
    var Gantt = base.Container.extend({

        //常量
        cst: {
            $container: $("#resPanelEditTable"),
            $temp: $("#resPanelEdit"),
            rpClickFun: {}
        },
        /**
         * 初始化
         * cfg应包含：
         * xformId ：用于指定当前表单的xform模版
         * modelMainId：当前表单id
         * modelTargetId：目标表单id，可选
         * data:数据内容：可选
         */
        initProps: function ($super, cfg) {
            console.debug("ResPanel init cfg::", cfg);
            this.loading = dialog.loading();
            $super(cfg);
            //初始化1
            this.flowInfo = cfg.flowInfo;
            formulaBuilder.initFieldList(cfg.xformId);
            this.widgets = this.config.widgets;
            cfg.cst = this.cst;
            cfg.parent = this;
            //初始化显示设置和数据源设置
            this.ganttTable = new ganttTable.GanttTable(cfg);
            this.ganttTable.build();

            this.bindEvent();
            this.build(cfg);
            //用于外部调用函数时改变this指向；
            ___rpThis = this;

            if (cfg.fdConfig && cfg.fdConfig.length > 2) {
                this.__storeData = JSON.parse(cfg.fdConfig);
                this.initByStoreData(this.__storeData)
            }

            this.loading.hide()

        },
        /**
         * 绑定事件
         */
        bindEvent: function () {

            //绑定逻辑区块间的显示隐藏
            $(".model-edit-view-bar").find("div").on("click", function (e) {
                e.stopPropagation();
                $(".model-edit-view-bar").find("div").removeClass("barActive");
                var $t = $(this);
                $t.addClass("barActive");
                var mark = $t.attr("gantt-bar-mark");
                $(".gantt-bar-content").hide();
                $("[gantt-bar-content='" + mark + "']").show();
            });
            $(".model-edit-view-bar").find("[gantt-bar-mark='basic']").trigger($.Event("click"))
            //查询条件“内置条件”和“自定义条件”切换
            $(".model-query-tab li").on("click", function () {
                $(this).siblings().removeClass("active");
                $(this).addClass("active");
                var index = $(this).index();
                $(".model-query-cont").each(function (i, ele) {
                    if (i == index) {
                        $(this).siblings().css('display', 'none');
                        $(this).css('display', 'block');
                    }
                })
            });
        },
        /**
         * 设置点击事件
         */
        __rpClick: function (thisObj, type) {
            console.debug("__rpClick:", thisObj, type);
            if (!___rpThis) {
                return;
            }
            var fun = undefined;
            if (___rpThis.cst.rpClickFun[type]) {
                fun = ___rpThis.cst.rpClickFun[type];
                ___rpThis[fun](thisObj, type);
            } else {
                var tabCst = ___rpThis.ganttTable.cst;
                // var sourceCst = ___rpThis.ganttSource.cst;
                if (tabCst && tabCst.rpClickFun && tabCst.rpClickFun[type]) {
                    fun = tabCst.rpClickFun[type];
                    if (fun) {
                        ___rpThis.ganttTable[fun](thisObj, type);
                    }
                }
                // else if (sourceCst && sourceCst.rpClickFun && sourceCst.rpClickFun[type]) {
                //     fun = sourceCst.rpClickFun[type];
                //     if (fun) {
                //         ___rpThis.ganttSource[fun](thisObj, type);
                //     }
                // }
                else {
                    console.debug("__rpClick:无效type");
                }

            }
        },
        /**
         * 实际绘制分两块区域显示设置和数据源设置
         *
         */
        build: function () {
        },
        startup: function ($super, cfg) {
            $super(cfg);
        },
        //获取传到后台去的参数方法
        getKeyData: function () {
            return {
                table: this.ganttTable.getKeyData(),
            }
        },

        //后台数据渲染方法
        initByStoreData: function (sd) {
            this.ganttTable.initByStoreData(sd.table);
        },
        /****************一些功能函数********************************/
        /**
         * 用于/sys/modeling/base/listview/config/dialog.jsp中使用
         * 转换前：{docSubject: {name: "docSubject", businessType: "mainModel", label: "主题【规则自动生成】", type: "String"}}
         * 转换后格式为：[{"text":"备注","field":"fd_3896663444f21a","type":"String"}]
         * @param widgets
         * @private
         */
        __formatWidgetsToDialogJsp: function (widgets) {
            if (!widgets) {
                widgets = this.config.widgets
            }
            if (!widgets) {
                return []
            }
            var dlgWidgets = [];
            for (var key in widgets) {
                if (widgets[key].type.indexOf("Attachment") > -1) {
                    //附件过滤
                    continue
                }
                var dlgItem = {
                    "text": widgets[key].label,
                    "field": key,
                    "type": widgets[key].type
                };
                dlgWidgets.push(dlgItem)
            }
            return dlgWidgets;
        }
    });
    window.GanttValidate = {
        validate: function (cfg) {
            var fdName = $("[name='fdName']").val();
            if (!fdName) {
                return modelingLang['respanel.name.cannot.empty'];
            }
            if (!cfg || !cfg.table) {
                return modelingLang['Gantt.configure.display.setFirst'];
            }
            if(!cfg.table.fdDisplayText || cfg.table.fdDisplayText.length == 0){
                return modelingLang['Gantt.display.set.cannot.empty'];
            }
            if(!cfg.table.startTimeField || "请选择" == cfg.table.startTimeField){
                return modelingLang['Gantt.start.time.cannot.empty'];
            }
            if(!cfg.table.endTimeField || "请选择" == cfg.table.endTimeField){
                return modelingLang['Gantt.end.time.cannot.empty'];
            }
        }

    };

    exports.Gantt = Gantt;
});

