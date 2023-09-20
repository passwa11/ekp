/**
 * 资源面板类
 */
define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var base = require("lui/base");
    var dialog = require('lui/dialog');
    var whereRecordGenerator = require("sys/modeling/base/views/business/res/whereRecordGenerator");
    var colorRecordGenerator = require("sys/modeling/base/views/business/res/colorRecordGenerator");
    var ResPanelBase = base.Container.extend({
        /**
         * 初始化
         * cfg应包含参考引用方
         */
        initProps: function ($super, cfg) {
            $super(cfg);
            //简化变量名
            this.parent = cfg.parent;
            this.widgets = cfg.widgets;
            this.$c = cfg.cst.$container;
            this.$tempHtml = cfg.cst.$temp;
            this.whereWgtCollection = [];
            this.colorWgtCollection = [];
        },
        /**
         * 实际绘制显示设置区域
         *
         */
        build: function (cfg) {
        },
        startup: function ($super, cfg) {
            $super(cfg);
        },
        //获取传到后台去的参数方法
        getKeyData: function () {
        },

        initByStoreData: function () {
        },

        /************************************数组类方法*********************/
        // type : where|
        addWgt: function (wgt, type) {
            if (type === "where") {
                this.whereWgtCollection.push(wgt);
                this.setWgtMoveBtnStyle(this.whereWgtCollection);
            }else if (type==="color"){
                this.colorWgtCollection.push(wgt);
                this.setWgtMoveBtnStyle(this.colorWgtCollection);
            }
        },
        // type : where|
        deleteWgt: function (wgt, type) {
            var collect = [];
            if (type === "where") {
                collect = this.whereWgtCollection;
            }else if (type==="color"){
                collect = this.colorWgtCollection;
            }
            for (var i = 0; i < collect.length; i++) {
                if (collect[i] === wgt) {
                    collect.splice(i, 1);
                    break;
                }
            }
            this.setWgtMoveBtnStyle(collect);
        },
        // type : where|
        moveWgt: function (wgt, type, step) {
            if (!step || step == 0) {
                return;
            }
            var collect = [];
            if (type === "where") {
                collect = this.whereWgtCollection;
            }else if (type==="color"){
                collect = this.colorWgtCollection;
            }
            var idx;
            for (var i = 0; i < collect.length; i++) {
                if (collect[i] === wgt) {
                    idx = i;
                    break;
                }
            }
            var newIdx = idx + step;
            if (newIdx == -1 || newIdx > (collect.length - 1)) {
                return;
            }
            //数据转换
            var otherWgt = collect[newIdx];
            collect[idx] = otherWgt;
            collect[newIdx] = wgt;

            //dom转换，单步转换,-1 上移，1 下移
            if (step < 0) {
                wgt.element.prev().insertAfter(wgt.element);
            } else {
                wgt.element.next().insertBefore(wgt.element);
            }
            this.setWgtMoveBtnStyle(collect);

        },
        setWgtMoveBtnStyle: function (collect) {
            if (collect && collect.length > 0) {
                for (var i = 0; i < collect.length; i++) {
                    collect[i].element.find(".up").show();
                    collect[i].element.find(".down").show();
                }
                collect[0].element.find(".up").hide();
                collect[collect.length - 1].element.find(".down").hide();
            }
        },
        getWhereKeyData:function(){
            var where = [];
            if ( this.whereWgtCollection){
                for (var i = 0; i < this.whereWgtCollection.length; i++) {
                    var whereWgt = this.whereWgtCollection[i];
                    var whereWgtKeyData = whereWgt.getKeyData();
                    if (!whereWgtKeyData) {
                        continue;
                    }
                    // 索引，用来进来记录排序，暂无用
                    whereWgtKeyData.idx = i;
                    where.push(whereWgtKeyData);
                }
            }
            return where
        },
        getColorKeyData:function(){
            var color = [];
            if ( this.colorWgtCollection){
                for (var i = 0; i < this.colorWgtCollection.length; i++) {
                    var colorWgt = this.colorWgtCollection[i];
                    var colorKeyData = colorWgt.getKeyData();
                    if (!colorKeyData) {
                        continue;
                    }
                    // 索引，用来进来记录排序，暂无用
                    colorKeyData.idx = i;
                    color.push(colorKeyData);
                }
            }
            return color
        },
        /****************一些功能函数********************************/
        __getWidgetsOfDialogJsp: function () {
            if (!this.cst.widgetsODJ) {
                this.cst.widgetsODJ = this.parent.__formatWidgetsToDialogJsp();
            }
            return this.cst.widgetsODJ;
        },
        /**
         * 绑定自定义样式radio,需要满足：
         * 1、整个radio组的dom需要以".view_flag_radio"包裹
         * 2、单个值需要以".view_flag_radio_item"包裹
         * 3、值以view-flag-radio-value="" 形式设置，设置在radio组中为默认值
         *
         * @param $ele
         * @private
         */
        _buildViewFlagRadio: function ($ele, changFun) {
            $ele.find(".view_flag_radio").each(function (i, radio) {
                var $radio = $(radio);
                $(radio).find("i").removeClass("view_flag_yes");
                $radio.find(".view_flag_radio_item").each(function (i, radioItem) {
                    //默认值
                    var val_o = $(radioItem).attr("view-flag-radio-value");
                    var radioVal_o = $(radio).attr("view-flag-radio-value");
                    if (val_o === radioVal_o) {
                        $(radioItem).find("i").addClass("view_flag_yes");
                    }
                    //点击事件绑定
                    $(radioItem).on("click", function () {
                        $(radio).find("i").removeClass("view_flag_yes");
                        var val_t = $(this).attr("view-flag-radio-value");
                        var radioVal_t = $(radio).attr("view-flag-radio-value");
                        //值不改变
                        $(this).find("i").addClass("view_flag_yes");
                        if (val_t === radioVal_t) {
                            return
                        }
                        //值改变
                        $(radio).attr("view-flag-radio-value", val_t);
                        if (changFun) {
                            changFun(this, val_t, radioVal_t)
                        }
                    })
                })
            })
        },

        _whereItemAddClick: function (thisObj, whereCfg) {
            // if(!insystemContext.hasDictData()){
            //     alert(listviewOption.lang.chooseModuleFirst);
            //     return;
            // }
            var whereTabId = $(thisObj).attr("data-lui-mark");
            var $whereTable = $("#" + whereTabId);
            var whereWgt = new whereRecordGenerator.WhereRecordGenerator(whereCfg);
            whereWgt.draw($whereTable);
        },
        _initWgtCollection:function (storeData,type,whereCfg,$table) {
               // 136657
            if (typeof storeData == 'undefined'){
                storeData = [];
            }
            for (var i = 0; i < storeData.length; i++) {
                var data = storeData[i];
                if ("where" === type){
                    var whereWgt = new whereRecordGenerator.WhereRecordGenerator(whereCfg);
                    whereWgt.draw($table);
                    whereWgt.initByStoreData(data);
                }else if ("color" === type){
                    var colorWgt = new  colorRecordGenerator.ColorRecordGenerator(whereCfg);
                    colorWgt.draw($table);
                    colorWgt.initByStoreData(data);

                }

            }
        }
    });

    exports.ResPanelBase = ResPanelBase;
});
