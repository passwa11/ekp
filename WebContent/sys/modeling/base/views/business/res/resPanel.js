/**
 * 资源面板类
 */
define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var base = require("lui/base");
    var dialog = require('lui/dialog');
    //公式定义器
    var formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder");
    //自定义参数
    var resPanelTable = require("sys/modeling/base/views/business/res/resPanelTable");
    var resPanelSource = require("sys/modeling/base/views/business/res/resPanelSource");
    var modelingLang = require("lang!sys-modeling-base");
    //用于外部调用函数时改变this指向；
    var ___rpThis = undefined;
    var ResPanel = base.Container.extend({

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
            this.resPanelTable = new resPanelTable.ResPanelTable(cfg);
            this.resPanelTable.build();

            this.resPanelSource = new resPanelSource.ResPanelSource(cfg);
            this.resPanelSource.build();
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
                var mark = $t.attr("resPanel-bar-mark");
                $(".resPanel-bar-content").hide();
                $("[resPanel-bar-content='" + mark + "']").show();
            });
            $(".model-edit-view-bar").find("[resPanel-bar-mark='basic']").trigger($.Event("click"))
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
                var tabCst = ___rpThis.resPanelTable.cst;
                var sourceCst = ___rpThis.resPanelSource.cst;
                if (tabCst && tabCst.rpClickFun && tabCst.rpClickFun[type]) {
                    fun = tabCst.rpClickFun[type];
                    if (fun) {
                        ___rpThis.resPanelTable[fun](thisObj, type);
                    }
                } else if (sourceCst && sourceCst.rpClickFun && sourceCst.rpClickFun[type]) {
                    fun = sourceCst.rpClickFun[type];
                    if (fun) {
                        ___rpThis.resPanelSource[fun](thisObj, type);
                    }
                } else {
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
                table: this.resPanelTable.getKeyData(),
                source: this.resPanelSource.getKeyData()
            }
        },

        //后台数据渲染方法
        initByStoreData: function (sd) {
            this.resPanelTable.initByStoreData(sd.table);
            this.resPanelSource.initByStoreData(sd.source);
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
    window.ResPanelValidate = {
        _vTableCategory:function(cfg){
            //时间类型
            if (cfg.section) {
                if (parseInt(cfg.section.start) >= parseInt(cfg.section.end)) {
                    console.debug("cfg.section::",cfg.section)
                    return modelingLang['respanel.period.startTime.cannont.greater.endTime'];
                }
                console.debug("cfg::",cfg)
            }
            if (cfg.split) {
                var n = cfg.split.number;
                var r = /^\+?[1-9][0-9]*$/;　　//正整数
                if (!r.test(n) ) {
                    return modelingLang['respanel.period.positive.integer'];
                }
                n = parseInt(n);
                var u = cfg.split.unit;
                if (u=="h" && (n<1 || n>23)){
                    return modelingLang['respanel.period.allows.1-23.hours'];
                }
            }else if (!cfg.text) {
                return modelingLang['respanel.period.cannot.empty'];
            }
            //字段类型
            if (cfg.text) {
                //显示值
                if (!cfg.text.value) {
                    return modelingLang['respanel.vertical.axis-display.cannot.empty'];
                }
            }
            return;
        },
        __vTable: function (t) {
            if (!t.row || !t.col) {
                console.error("面板框架格式错误", t)
                return modelingLang['respanel.unknown.configuration.error']
            }
            var flag = this._vTableCategory(t.row[t.row.category]);
            if (flag) {
                return flag;
            }
            flag = this._vTableCategory(t.col[t.col.category]);
            if (flag) {
                return flag;
            }

        },
        __vSource: function (s) {
            if (!s.model || !s.model.id) {
                return modelingLang['respanel.configure.Panel.Chart.Target.Form']
            }
            var tips = "";
            if (!s.matchRow) {
                tips += modelingLang['respanel.panel.chart.row.matching.field']+"<br/>"
            }
            if (!s.matchCol) {
                tips += modelingLang['respanel.panel.chart.List.matching.field']+"<br/>"
            }
            var dialogTitle = ["title", "time", "person", "content"];
            var dialogTitleText = ["标题", "占用时间", "提交人", "内容"];
            for (var i = 0; i < dialogTitle.length; i++) {
                var item = dialogTitle[i];
                if (!s.dialog.data[item] || !s.dialog.data[item].value) {
                    tips += "【"+modelingLang['respanel.panel.chart.content-detailed.display']+"-" + dialogTitleText[i] + "】<br/>"
                }
            }
            if (!s.show || !s.show.value) {
                tips += modelingLang['respanel.panel.chart.display.field']+"<br/>"
            }
            if (tips.length > 0) {
                tips = modelingLang['respanel.configure']+"<br/>" + tips;
                return tips
            }
        },
        validate: function (cfg) {
            var fdName = $("[name='fdName']").val();
            if (!fdName) {
                return modelingLang['respanel.name.cannot.empty'];
            }
            //面板框架校验
            if (!cfg || !cfg.table) {
                return modelingLang['respanel.configure.panel.frame'];
            }
            var t = cfg.table;
            var tr = this.__vTable(t);
            if (tr) {
                return tr;
            }
            //面板图表内容
            if (!cfg || !cfg.source) {
                dialog.alert(modelingLang['respanel.configure.panel.Chart.Contents'])
                return false;
            }
            var sr = this.__vSource(cfg.source);
            if (sr) {
                return sr;
            }


        }

    };
    exports.ResPanel = ResPanel;
});
