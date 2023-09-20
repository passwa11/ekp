/**
 * 页面全局对象
 */
define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var base = require("lui/base");
    var topic = require("lui/topic");
    var dialog = require('lui/dialog');
    var modelingLang = require("lang!sys-modeling-base");
    //不需要记录修改的key
    var o_name = [
        "_org_dept",
        "_org_org",
        "_org_group",
        "_org_post",
        "_label_bind",
        "new_addressCustomType",
        "businessType",
        "columnIndex"
    ];
    var t_status = {
        "0": modelingLang['modelingLog.add'],
        "1": modelingLang['modelingLog.update'],
        "2": modelingLang['modelingLog.delete']
    };
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
        "fileName": Designer_Lang.controlAttachDomFileName,
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
    var t_comclass = {};
    var t_classHref = {
        "com.landray.kmss.sys.modeling.base.model.SysModelingRelation":
            "/sys/modeling/base/sysModelingRelation.do?method=edit&fdId=!{fdId}",
        "com.landray.kmss.sys.modeling.base.model.SysModelingBehavior":
            "/sys/modeling/base/sysModelingBehavior.do?method=edit&fdId=!{fdId}",
        "com.landray.kmss.sys.modeling.base.model.SysModelingOperation":
            "/sys/modeling/base/sysModelingOperation.do?method=edit&fdId=!{fdId}",
        "com.landray.kmss.sys.modeling.base.model.ModelingAppListview":
            "/sys/modeling/base/modelingAppListview.do?method=edit&isInDialog=true&fdModelId=!{fdModelId}&fdId=!{fdId}",
        "com.landray.kmss.sys.modeling.base.model.ModelingAppView":
            "/sys/modeling/base/modelingAppView.do?method=edit&isInDialog=true&fdModelId=!{fdModelId}&fdId=!{fdId}",
        "com.landray.kmss.sys.modeling.base.model.ModelingPortletCfg":
            "/sys/modeling/base/modelingPortletCfg.do?method=edit&fdId=!{fdId}",
        "com.landray.kmss.sys.modeling.base.mobile.model.ModelingAppMobileListView":
            "/sys/modeling/base/mobile/modelingAppMobileListView.do?method=edit&isInDialog=true&fdModelId=!{fdModelId}&fdId=!{fdId}",
        "com.landray.kmss.sys.modeling.base.model.ModelingAppView_mobile":
            "/sys/modeling/base/modelingAppView.do?method=edit&isInDialog=true&fdModelId=!{fdModelId}&fdId=!{fdId}",
        "com.landray.kmss.sys.modeling.base.business.model.ModelingResourcePanel":
            "/sys/modeling/base/resPanel.do?method=edit&isInDialog=true&fdId=!{fdId}",
        "com.landray.kmss.sys.modeling.base.business.model.ModelingGantt":
            "/sys/modeling/base/gantt.do?method=edit&isInDialog=true&fdId=!{fdId}",
        "com.landray.kmss.sys.modeling.base.views.collection.model.ModelingAppCollectionView":
            "/sys/modeling/base/modelingAppCollectionView.do?method=edit&fdModelId=!{fdModelId}&fdId=!{fdId}",
        "com.landray.kmss.sys.modeling.base.model.ModelingImportConfig":
            "/sys/modeling/main/modelingImportConfig.do?method=edit&fdId=!{fdId}",
        "com.landray.kmss.sys.modeling.base.application.model.ModelingExternalQuery":
            "/sys/modeling/base/externalQuery.do?method=edit&fdModelId=!{fdModelId}"
    }

    var html_swiper = "    " +
        "<div class=\"swiper-slide swiper-slide-visible\">\n" +
        "     <div class=\"model-change-slide-item\">\n" +
        "          <p class='slide-item-title'></p>" +
        "          <span class='slide-item-number'></span>\n" +
        "     </div>\n" +
        " </div>"
    var html_slide = "      <table class=\"tb_normal modeling_form_table\" width=\"100%\">\n" +
        "        <tr>\n" +
        "            <td class='td_normal_title' width='60%'>"+modelingLang['modelingLog.business.name']+"</td>\n" +
        "            <td class='td_normal_title' width='20%'>"+modelingLang['modelingLog.configuration.location']+"</td>\n" +
        "            <td class='td_normal_title' width='20%'>"+modelingLang['modelingLog.operate']+"</td>\n" +
        "        </tr>\n" +
        "</table>"

    var LogTranslator = base.Component.extend({
        initProps: function ($super, cfg) {
            $super(cfg);
            t_comclass = cfg.comClass;
        },
        translator: function (changeLogs) {
            $ele = $(" <div class=\"model-change-list-desc\"></div>");
            if (changeLogs && changeLogs.data){
                var controlType = changeLogs.controlType;
                var fieldId = changeLogs.fieldId;
                if(this.endWith(fieldId,"_text")){
                    return;
                }
                var self = this;
                var config = Designer_Config.controls[controlType];
                if(config){
                    var objTranslator=config.translator;
                    if(objTranslator){//如果控制器本身有翻译器，则不执行具体属性的翻译器
                        changeLogs.data.forEach(function (item, idx) {
                            if (typeof item.oldVal === "undefined" && (item.newVal === "" || item.newVal === "false" || typeof item.newVal === "undefined" || item.newVal === "null")) {
                                return;
                            }
                            var pname = item.name;
                            if (config.text) {
                                pname = config.text;
                            }
                            item.before = item.oldVal || "";
                            item.after = item.newVal || "";
                            let change = item.change || item;
                            if(controlType !== "validatorControl"){
                                change = JSON.stringify(change);
                                change = change.replace(/quot;/g,"\\\"");
                            }else{
                                if(item.name === "varIds"){
                                    return;
                                }
                                item.before = item.before || "（）";
                                item.after = item.after || "（）";
                            }
                            var txt = "<span>【" + pname + "】</span>" + objTranslator(change);

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
                        changeLogs.data.forEach(function (item, idx) {
                            if ((typeof item.oldVal === "undefined")
                                && (item.newVal === "" || item.newVal === "false" || typeof item.newVal === "undefined" || item.newVal === "null")) {
                                return;
                            }
                            var pname = item.name;
                            var iname= item.name;
                            var txt = "";
                            if(iname === "_orgType"){
                                iname = "orgType";
                            }
                            //关联文档特殊处理
                            if(controlType === "relevance"){
                                if(iname === "_docStatus"){
                                    iname = "docStatus";
                                }
                            }
                            if (attrs[iname]) {
                                if(attrs[iname].skipLogChange){
                                    return;
                                }
                                if(o_name.indexOf(iname)>-1){
                                    return;
                                }
                                if (attrs[iname]) {
                                    pname = attrs[iname].translatorText || attrs[iname].displayText || attrs[iname].text;
                                }
                                //判断控件属性是否存在属性的翻译器,审批意见的类型特殊处理
                                if(attrs[iname]["translator"] && iname !== "mould"){
                                    var translator = attrs[iname]["translator"];
                                    item.before = item.oldVal;
                                    item.after = item.newVal;
                                    var change = item.change || item;
                                    //传出参数/传入参数特殊处理
                                    if(iname === "outputParams" || iname === "inputParams"){
                                        item.oldVal = item.oldVal || (iname === "inputParams" ? "":"()");
                                        item.newVal = item.newVal || (iname === "inputParams" ? "":"()");
                                        change = JSON.stringify(change);
                                        change = change.replace(/quot;/g,"\\\"");
                                    }
                                    //属性变更控件的选项属性特殊处理
                                    if(controlType==="relationRule" && iname ==="op"){
                                        if(change.oldVal){
                                            change.oldVal = change.oldVal.replace(/quot;/g,"\"");
                                            change.oldVal = JSON.parse(change.oldVal);
                                        }
                                        if(change.newVal){
                                            change.newVal = change.newVal.replace(/quot;/g,"\"");
                                            change.newVal = JSON.parse(change.newVal);
                                        }
                                        change = JSON.stringify(change);
                                    }
                                    //大数据展示控件，传出参数特殊处理
                                    if(controlType !== "massData" || iname !== "outputParams"){
                                        try {
                                            txt = "<span>【" + pname + "】</span>" + translator(change, attrs[iname]);
                                        }catch (e) {
                                        }
                                    }
                                }else if(attrs[iname].opts){
                                    //附件的显示详情（下载次数）特殊处理
                                    if(iname !== 'isShowDownloadCount'){
                                        txt = self.opts_common_translator_many(item.change || item,attrs[iname],pname);
                                    }
                                }
                            }else if(!t_name[iname]){
                                //如果既不存在控件属性中，也不存在自定义的属性中，则跳过不显示
                                return;
                            }
                            if(!txt){
                                txt = self.defaultTranslate(item,pname);
                            }
                            if(txt){
                                var status = t_status[item.status] || item.status;
                                txt = status + txt;
                            }
                            var $item = $("<p/>");
                            $item.html(txt);
                            $ele.append($item);
                        });
                    }
                }
            }

            return $ele;
        },
        defaultTranslate: function(item,pname) {
            var txt = "";
            if(t_name[pname]){
                pname=t_name[pname];
            }
            if(o_name.indexOf(pname)>-1){
                return;
            }
            let newVal = item.newVal;
            if(t_value[item.oldVal]){
                item.oldVal=t_value[item.oldVal]
            }
            if(item.oldVal){
                item.oldVal = item.oldVal.replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,';').replace(/quot;/ig,"\"");
            }
            if(item.newVal){
                item.newVal = item.newVal.replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,';').replace(/quot;/ig,"\"");
            }
            if(t_value[item.newVal]){
                item.newVal=t_value[item.newVal]
            }
            txt = "【" + pname + "】";
            //修改
            if (item.status == "1") {
                txt += " "+Designer_Lang.from+"  (" + (item.oldVal || '') + ")\&nbsp;\&nbsp;\&nbsp; "+Designer_Lang.to+"\&nbsp;\&nbsp;\&nbsp;(" + item.newVal + ")";
            }
            //新增
            if(item.status == "0"){
                if (item.newVal && newVal !== "false") {
                    txt +=" " + item.newVal;
                }else{
                    txt = "";
                }
            }
            return txt;
        },
        opts_common_translator_many:function(change,obj,pname) {
            var txt = ""
            if (!change) {
                return txt;
            }
            var opts = obj.opts;
            if(undefined==opts){
                return txt;
            }
            var oldVal="";
            var newVal="";
            for (var i = 0; i < opts.length; i++) {
                var opt = opts[i];

                if(change.oldVal === opt.value
                    || (change.oldVal&&opt.value&&change.oldVal.indexOf(opt.value)!=-1)){
                    if(oldVal.length>0){
                        oldVal=oldVal+"|"
                    }
                    oldVal=oldVal+opt.text;
                }

                if(change.newVal === opt.value
                    || (change.newVal && opt.value && change.newVal.indexOf(opt.value)!=-1)){
                    if(newVal.length>0){
                        newVal=newVal+"|"
                    }
                    newVal=newVal+opt.text;
                }
            }
            change.newVal=newVal;
            change.oldVal=oldVal;
            txt = "【" + pname + "】";
            //修改
            if (change.status == "1") {
                txt += " "+Designer_Lang.from+"  (" + (change.oldVal || '') + ")\&nbsp;\&nbsp;\&nbsp; "+Designer_Lang.to+"\&nbsp;\&nbsp;\&nbsp;(" + change.newVal + ")";
            }
            //新增
            if(change.status == "0"){
                if (change.newVal && newVal !== "false") {
                    txt +=" " + change.newVal;
                }else{
                    txt = "";
                }
            }
            return txt;
        },
        translatorMapping: function (fieldId, mapping) {
            var $swiperContainer = $("[ mapping-log-mapping='" + fieldId + "']").find(".swiper-wrapper");
            $swiperContainer.attr("id","swiper-wrapper-"+fieldId)
            var $slideContainer = $("[ mapping-log-mapping='" + fieldId + "']").find(".model-change-slide-content-wrap");
            var firstEvent = false;
            for (var key in mapping) {
                var $swiperItem = $(html_swiper);
                var title = t_comclass[key];
                var number = mapping[key].length || 1;
                $swiperItem.find("p").html(title);
                $swiperItem.find("p").attr("title", title);
                $swiperItem.find("span").html(number);
                $swiperItem.attr("mark-swiper-key", key)
                $swiperContainer.append($swiperItem);
                //列表
                var $slideTable = this.translatorMappingList(mapping[key]);
                $slideTable.attr("mark-swiper-key", key)
                $slideTable.hide();
                //交互
                $swiperItem.on("click", function () {
                    $swiperContainer.find(".model-change-slide-item").removeClass("active");
                    $(this).find(".model-change-slide-item").addClass("active")
                    $slideContainer.find("table").hide();

                    var key = $(this).attr("mark-swiper-key");

                    $slideContainer.find("table[mark-swiper-key='" + key + "']").show();
                });

                $slideContainer.append($slideTable)
                if (!firstEvent) {
                    $swiperItem.trigger($.Event("click"));
                    firstEvent = true;
                }
            }
        },
        translatorMappingList: function (children) {
            var self = this;
            var $table = $(html_slide);
            if (!(children instanceof Array)) {
                children = [children]
            }
            children.forEach(function (item, idx) {
                    var $tr = $("<tr style='cursor: pointer'/>");
                    $("<td>" + item.fdComTitle + "</td>").appendTo($tr);
                    $("<td>" + item.fdComLocalName + "</td>").appendTo($tr);
                    var href = self.buildHref(item.fdComClass, item.fdComId, item.modelMainId);
                    $("<td>" + "<span style='color:#4285f4'> "+modelingLang['modelingLog.jump']+"</span>" + "</td>").appendTo($tr);
                    $tr.attr("mark-href", href);
                    $tr.data("item",item);
                    $tr.on("click", function () {
                        var dialogUrl = $(this).attr("mark-href");
                        var data = $(this).data("item");
                        var width = 1000;
                        var height = 1000;
                        if (data && data.fdComLocal === "import")   {
                            height = 600;
                        }
                        debugger
                        if (dialogUrl) {
                            dialog.iframe(dialogUrl, modelingLang['modelingLog.mapping.modification'], function (value) {
                                topic.channel("modelingFormLog").publish("childrenChange",{data:data, widget:this});
                            }, {
                                width: width,
                                height: height
                            });
                        } else {
                            alert(modelingLang['modelingLog.unrecognized.link'])
                        }

                    });
                    $table.append($tr)
                }
            );
            return $table;
        },
        buildHref: function (className, comId, modelId) {
            var orgHref = t_classHref[className];
            if (!orgHref) {
                console.log(className, comId, modelId, orgHref)
                return orgHref;
            }
            return orgHref.replace("!{fdId}", comId).replace("!{fdModelId}", modelId);
        },
        endWith:function(x,ex){
            if(!x){
                return false;
            }
            var reg=new RegExp(ex+"$");
            return reg.test(x);
        },
        startWith:function(x,ex){
            if(!x){
                return false;
            }
            var reg=new RegExp("^"+ex);
            return reg.test(x);
        }
    });

    exports.LogTranslator = LogTranslator;
})
;
