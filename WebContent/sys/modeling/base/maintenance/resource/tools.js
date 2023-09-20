/**
 * 页面全局对象
 */
define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var base = require("lui/base");


    var Tools = base.Container.extend({
        initProps: function ($super, cfg) {
            $super(cfg);
            this.cfg = cfg;
            this.toolMain = cfg.toolMain;
            this.toolGrid = cfg.toolGrid;
            this.toolMainTemp = cfg.toolMainTemp;
            this.source = cfg.source;
            this.build();
            this.bindEvent();
        },
        build: function () {
            var self =this;
            var $e =  self.toolGrid;
            var $t =  self.toolMainTemp.find("[data-lui-mark=\"temp-grid-li\"]");
            var data  = self.source;
            for (var i = 0; i <data.length ; i++) {
                var html = $t.prop("outerHTML");
              /*html= html.replaceAll("{%i%}",i%6+1);
                html= html.replaceAll("{%name%}",data[i].name);
                html=html.replaceAll("{%desc%}",data[i].desc);
                html=html.replaceAll("{%toolName%}",data[i].id);
                html=html.replaceAll("{%icon%}",data[i].icon);*/
               // #129265 搜狗不支持replaceAll()
                html= html.split("{%i%}").join(i%6+1);
                html= html.split("{%name%}").join(data[i].name);
                html= html.split("{%desc%}").join(data[i].desc);
                html= html.split("{%toolName%}").join(data[i].id);
                html= html.split("{%icon%}").join(data[i].icon);
                var $h = $(html);
                $h.attr("data-lui-mark","tool-grid-li");
                $e.append($h);
            }
            this.element = $e;
        },
        bindEvent: function () {
            var $e = this.element;
            var self = this;
             //点击事件
            $e.find("[data-lui-mark=\"tool-grid-li\"]").on("click",function () {
                var $t = $(this);
                var cName = $t.attr("modeling-tool-name");
                if (cName&&self.callback[cName]){
                    self[self.callback[cName]]()
                }else if(self[cName]){
                    self[cName]()
                }
            })
        },
        callback:{
            "cfgUpgrade":"cfgUpgrade",
            "historyFix":"historyFix",
            "multiAddrMigration":"multiAddrMigration",
            "userAssignUpgrade":"userAssignUpgrade",
            "dataBaseCheckTask":"dataBaseCheckTask",
        },
        cfgUpgrade:function () {
            $(window.parent.document).find("#base_iframe").attr("src",Com_Parameter.ContextPath +"sys/modeling/base/maintenance/cfgUpgrade/index.jsp") ;
        },
        historyFix:function () {
            $(window.parent.document).find("#base_iframe").attr("src",Com_Parameter.ContextPath +"sys/modeling/base/maintenance/historyFix/index.jsp") ;
        },
        multiAddrMigration:function () {
            $(window.parent.document).find("#base_iframe").attr("src",Com_Parameter.ContextPath +"sys/modeling/base/maintenance/multiAddrMigration/index.jsp") ;
        },
        userAssignUpgrade:function () {
            $(window.parent.document).find("#base_iframe").attr("src",Com_Parameter.ContextPath +"sys/modeling/base/maintenance/userAssignUpgrade/index.jsp") ;
        },
        dataBaseCheckTask:function () {
            $(window.parent.document).find("#base_iframe").attr("src",Com_Parameter.ContextPath + "sys/modeling/base/modelingDbCheckAction.do?method=select") ;
        },
    })

    exports.Tools = Tools;
});
