/**
 * 页面全局对象
 */
define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var base = require("lui/base");
    var topic = require("lui/topic");
    var dialog = require('lui/dialog');
    var t_name = {
        "notNull": Designer_Lang.notNull,
        "readOnly": Designer_Lang.readOnly,
        "label": Designer_Lang.label,
        "defaultValue": Designer_Lang.defaultValue,
        "enumValues": Designer_Lang.enumValues,
	    "b":Designer_Lang.b,
        "i":Designer_Lang.i,
        "underline":Designer_Lang.underline,
        "zeroFill":Designer_Lang.zeroFill,
        "fileName": Designer_Lang.controlAttachDomFileName  
    };
    var t_value = {
        "true": Designer_Lang.truee,
        "false": Designer_Lang.falsee,  
        "V":Designer_Lang.vv,
        "H":Designer_Lang.hh,
        "zeroFill":Designer_Lang.zeroFill,
        "String":Designer_Lang.strr,
        "Double":Designer_Lang.douu,
        "normal":Designer_Lang.controlAttrMobileRenderTypeNormal,
        "block":Designer_Lang.controlAttrMobileRenderTypeBlock,
        "default":Designer_Lang.controlAttrAlignmentDefault,
        "left":Designer_Lang.controlAttrAlignmentLeft,
        "right":Designer_Lang.controlAttrAlignmentRight,
    };
    var t_status = {
        "0": "【新增】",
        "1": "【修改】",
        "2": "【删除】",
        "3": "调整样式或者调整控件位置"
    };
    var t_comclass = {};

    var LogTranslator = base.Component.extend({
        initProps: function ($super, cfg) {
            $super(cfg);
            t_comclass = cfg.comClass;
        },
        translator: function (changeLogs) {
            $ele = $(" <div class=\"model-change-list-desc\"></div>");
            if (changeLogs){
                var controlType = changeLogs.controlType;
                var self = this;
                var config = Designer_Config.controls[controlType];
                var objTranslator=config.translator;
                if(objTranslator){//如果控制器本身有翻译器，则不执行具体属性的翻译器
                    changeLogs.forEach(function (item, idx) {
                        if (typeof item.before === "undefined" && (item.after === "" || item.after === "false")) {
                            return;
                        }
                        var pname = item.name;
                        if (config.text) {
                            pname = config.text;
                        }
                        var txt = "<span>【" + pname + "】</span>" + objTranslator(item.change||item);

                        var $item = $("<p/>");
                        $item.html(txt);
                        $ele.append($item);
                    });
                } else {
                    var parentConfig = Designer_Config.controls[config.inherit];
                    var attrs = config.attrs || {};
                    if (parentConfig) {
                        var parentAttrs = parentConfig.attrs || {};
                        if (parentAttrs) {
                            for (var key in parentAttrs) {
                                if (!attrs[key]) {
                                    attrs[key] = parentAttrs[key];
                                }
                            }
                        }
                    }
                    changeLogs.forEach(function (item, idx) {
                        if (typeof item.before === "undefined" && (item.after === "" || item.after === "false")) {
                            return;
                        }
                        var pname = item.name;
                        if (!attrs[item.name]) {
                            return;
                        }
                        if(attrs[item.name].skipLogChange){
                            return;
                        }
                        if (attrs[item.name]) {
                            pname = attrs[item.name].translatorText || attrs[item.name].displayText || attrs[item.name].text;
                            if(attrs[item.name]["tipTranslator"]){
                                pname = attrs[item.name]["tipTranslator"](item.change || item,attrs[item.name],pname);
                            }
                        }
                        var txt = "";
                        if (attrs[item.name]
                            && attrs[item.name]["translator"]) {
                            var translator = attrs[item.name]["translator"];
                            txt = "<span>【" + pname + "】</span>" + translator(item.change || item, attrs[item.name]);
                        } else {
                            txt = self.defaultTranslate(item,pname);
                        }
                        var $item = $("<p/>");
                        $item.html(txt);
                        $ele.append($item);
                    });
                }
            } else {
                $ele.append(t_status[describe.type]);
            }

            return $ele;
        },
        defaultTranslate: function(item,pname) {
        	 var txt = "";
             if(t_name[pname]){
	            pname=t_name[pname];
             }
             if(t_value[item.before]){
	            item.before=t_value[item.before]
             }
             if(t_value[item.after]){
	            item.after=t_value[item.after]
             }
             txt = "【" + pname + "】";
             //修改
             if (item.status == "1") {
                 txt += " "+Designer_Lang.from+"  (" + (item.before || '') + ")\&nbsp;\&nbsp;\&nbsp; "+Designer_Lang.to+"\&nbsp;\&nbsp;\&nbsp;(" + item.after + ")";
             }
             return txt;
        }
    });

    exports.LogTranslator = LogTranslator;
})
;
