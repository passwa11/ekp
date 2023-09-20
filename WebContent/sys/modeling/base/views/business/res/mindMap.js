/**
 * 思维导图
 */
define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var base = require("lui/base");
    var dialog = require('lui/dialog');
    var topic = require("lui/topic");
    //公式定义器
    var formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder");
    var mindMapOtherNode = require("sys/modeling/base/views/business/res/mindMapOtherNode");
    var modelingLang = require("lang!sys-modeling-base");
    //用于外部调用函数时改变this指向；
    var ___rpThis = undefined;
    var MindMap = base.Container.extend({
        //常量
        cst: {
            $container: $("#mindMapEditTable"),
            $temp: $("#mindMapEdit"),
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
            this.loading = dialog.loading();
            $super(cfg);
            //初始化1
            this.flowInfo = cfg.flowInfo;
            // formulaBuilder.initFieldList(cfg.xformId);
            this.widgets = this.config.widgets;
            cfg.cst = this.cst;
            cfg.parent = this;
            this.type = cfg.type;       //树形或者思维导图
            this.bindEvent();
            this.build(cfg);
            //用于外部调用函数时改变this指向；
            ___rpThis = this;
            if (cfg.fdConfig/* && cfg.fdConfig.length > 2*/) {
                this.__storeData = JSON.parse(cfg.fdConfig);
                this.initByStoreData(this.__storeData)
            }else{
                $(".mind-map-tree ").find("[name='showTree'][value='0']").trigger("click");
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
            });
            $(".model-edit-view-bar").find("[resPanel-bar-mark='basic']").trigger($.Event("click"))

            //绑定根节点名称
           /* $("[name='fdRootName']").keyup(function () {
                $(".mind-map-root-cfg .root-name-title").html($(this).val())
            })*/

            //名称
            $("[name='fdName']").focus(function () {
                $(".mind-map-fdName").closest("td").find(".validation-container").remove();
                $(".mind-map-fdName").closest("td").find(".validation-advice").remove();
            })

            //绑定根节点的视图穿透
            $(".item-content-view").find("[type='checkbox']").on("click", function () {
                if ($(".item-content-view").find("[name='fdViewEnable']").val() == "1") {
                    $(".model-mind-map-link").removeAttr("style");
                } else {
                    $(".model-mind-map-link").css("display", "none");
                }
            });

            //绑定展示设置是否显示大纲
            $(".mind-map-tree").find("[name='showTree']").on("click",function () {
                if($(this).val() == "1"){
                    if(!$('#treeviewMain').hasClass("hide")){
                        $('#treeviewMain').toggleClass("hide");
                    }
                    $("#treeviewMain").css("display","none");
                    $("#container canvas").css("width",$("#container canvas").width() + 220 +"px");
                    // $(".model-edit-left .model-edit-tree-container").css("display","none");
                    // $(".model-source-wrap").css("width","100%");
                    // $("#mindMap canvas").width("95%");
                }else{
                    if($('#treeviewMain').hasClass("hide")){
                        $('#treeviewMain').toggleClass("hide");
                    }
                    $("#treeviewMain").css("display","block");
                    // $(".model-edit-left .model-edit-tree-container").css("display","block");
                    $(".model-source-wrap").css("width","100%");
                }
            })

            //树形视图——绑定根节点的切换事件
            $(".choice-root-view").find("li").on("click",function () {
                var type = $(this).attr("name");
                if(type == "view"){
                    $(this).addClass("active");
                    $(this).siblings().removeClass("active");
                    $(this).closest(".list-content").find(".model-mind-map-view").css("display","block");
                    $(this).closest(".list-content").find(".model-mind-map-link").css("display","none");
                }else if(type == "link"){
                    $(this).addClass("active");
                    $(this).siblings().removeClass("active");
                    $(this).closest(".list-content").find(".model-mind-map-view").css("display","none");
                    $(this).closest(".list-content").find(".model-mind-map-link").css("display","block");
                }
            })

            //树形视图——选择视图点击事件
            $(".model-mind-map-view").find(".listVieElement").on("click",function(){
                var url = "/sys/modeling/base/resources/js/dialog/leftNav/leftNavDialog.jsp?isTodo=false";
                var appId = listviewOption.flowInfo.appId;          //appId在treeView_edit.jsp页面定义
                dialog.iframe(url,modelingLang['modelingMindMap.select.root.node.view'],function(rtn){
                    if(rtn && rtn.data){
                        self.renderRootView(rtn);
                    }
                },{
                    width : 800,
                    height : 500,
                    params : {
                        "cateBean" : "modelingAppModelService",
                        "fdAppId" : appId,
                        "dataBean" : "modelingTreeViewService&modelId=!{value}"
                    }
                });
            });

            //根节点视图穿透——自定义链接格式校验start
            $(".model-mind-map-link").find("[name='fdLink']").on("blur", function () {
                $(".validation-container").remove();
                const linkRe = /^(?:http(s)?:\/\/)?[\w.-]+(?:\.[\w\.-]+)+[\w\-\._~:/?#[\]@!\$&'\(\)\*\+,;=.]+$/;
                const linkRe_insys = /^\/[a-zA-Z0-9\w-./?%&=]+/;
                var linkValue = $(this).val().replace(/(^\s+)|(\s+$)/g,"");
                if (linkValue != "" && !linkRe.test(linkValue) && !linkRe_insys.test(linkValue)) {
                    $(".validation-container").remove();
                    //超长提示框
                    $(this).closest(".model-mind-map-link").append($("<div class='validation-container'><div class=\"validation-advice\" _reminder=\"true\">" +
                        "<table class=\"validation-table\"><tbody><tr><td>" +
                        "<div class=\"lui_icon_s lui_icon_s_icon_validator\"></div></td>" +
                        "<td class=\"validation-advice-msg\"><span class=\"validation-advice-title\">"+modelingLang['modelingAppViewtab.fdLinkParams']+"</span>" +
                        modelingLang['modelingMindMap.format.error.fill.again']+"</td></tr></tbody></table></div></div>"));
                }
            });
            $(".model-mind-map-link").find("[name='fdLink']").on("focus",function () {
                $(this).closest(".model-mind-map-link").find(".validation-container").remove();
            });
            //根节点视图穿透——自定义链接格式校验end
        },
        /**
         * 树形视图——根节点，视图穿透
         * @param rtn
         */
        renderRootView : function(rtn){
            var self = this;
            $(".mind-map-root-cfg").find(".validation-container").remove();
            $(".mind-map-root-cfg .listViewText").text(rtn.data.text);
            $(".mind-map-root-cfg .listViewText").attr("title",rtn.data.text);
            $(".mind-map-root-cfg .listViewText").css("color","#333333");
            $(".mind-map-root-cfg").find("[name='fdRootViewAppId']").val(rtn.appId);
            $(".mind-map-root-cfg").find("[name='fdRootViewFdId']").val(rtn.data.value);
            $(".mind-map-root-cfg").find("[name='fdRootViewModelId']").val(rtn.leftValue);
            $(".mind-map-root-cfg").find("[name='fdRootViewModelName']").val(rtn.data.text);
            if(rtn.data.text){
                let regex = /\((.+?)\)/g;
                let options = (rtn.data.text).match(regex);
                switch (options[0]) {
                    case "(思维导图)":
                        $(".mind-map-root-cfg").find("[name='fdRootViewModelType']").val("mindMap");
                        break;
                    case "(甘特图)":
                        $(".mind-map-root-cfg").find("[name='fdRootViewModelType']").val("gantt");
                        break;
                    case "(资源面板)":
                        $(".mind-map-root-cfg").find("[name='fdRootViewModelType']").val("resPanel");
                        break;
                    case "(旧版列表)":
                        $(".mind-map-root-cfg").find("[name='fdRootViewModelType']").val("listView");
                        break;
                    case "(日历视图)":
                        $(".mind-map-root-cfg").find("[name='fdRootViewModelType']").val("calendar");
                        break;
                    default:
                        //普通列表
                        $(".mind-map-root-cfg").find("[name='fdRootViewModelType']").val("collection");
                }
            }
        },
        build: function () {
            var cfg = {
                $temp: this.cst.$container.find("#mindMapTableDom"),
                parent: this
            }
            this.mindMapOtherNode = new mindMapOtherNode.MindMapOtherNode(cfg);
            this.mindMapOtherNode.startup();

        },
        startup: function ($super, cfg) {
            $super(cfg);
        },
        //获取传到后台去的参数方法
        getKeyData: function () {
            var fdRootNode = {};
            var fdRootNodeName = this.cst.$container.find("#mindMapTableDom").find("[name='fdRootName']").val();
            fdRootNode.fdRootNodeName = fdRootNodeName;
            if(this.type == "treeView"){
                //树形视图
                var viewType = this.cst.$container.find(".choice-root-view ul li.active").attr("value");
                var fdRootViewAppId = this.cst.$container.find("#mindMapTableDom").find("[name='fdRootViewAppId']").val();
                var fdRootViewModelId = this.cst.$container.find("#mindMapTableDom").find("[name='fdRootViewModelId']").val();
                var fdRootViewFdId = this.cst.$container.find("#mindMapTableDom").find("[name='fdRootViewFdId']").val();
                var fdRootViewModelName = this.cst.$container.find("#mindMapTableDom").find("[name='fdRootViewModelName']").val();
                var fdRootViewModelType = this.cst.$container.find("#mindMapTableDom").find("[name='fdRootViewModelType']").val();
                var fdLink = this.cst.$container.find("#mindMapTableDom").find("[name='fdLink']").val();
                fdLink = fdLink.replace(/(^\s+)|(\s+$)/g,"");
                fdRootNode.fdRootViewType = viewType;
                fdRootNode.fdRootViewAppId = fdRootViewAppId;
                fdRootNode.fdRootViewModelId = fdRootViewModelId;
                fdRootNode.fdRootViewModelType = fdRootViewModelType;
                fdRootNode.fdRootViewFdId = fdRootViewFdId;
                fdRootNode.fdRootViewModelName = fdRootViewModelName;
                fdRootNode.fdLink = fdLink;
                fdRootNode.isShowRoot = $("[name='isShowRoot']:checked").val();
                fdRootNode.extendLevel = $("#mind-map-extend option:selected").val();
            }else{
                var fdViewEnable = this.cst.$container.find("#mindMapTableDom").find("[name='fdViewEnable']").val();
                var fdLink = this.cst.$container.find("#mindMapTableDom").find("[name='fdLink']").val();
                fdLink = fdLink.replace(/(^\s+)|(\s+$)/g,"");
                fdRootNode.fdViewEnable = fdViewEnable;
                fdRootNode.fdLink = fdLink;
                fdRootNode.defaultShowStyle = $(".mind-map-option ul").find("li.active").attr("value") || "0";
                fdRootNode.defaultSkin = $(".defaule-skin-value").attr("value") || "0";
                fdRootNode.defaultSkinSrc = $(".defaule-skin").attr("src");
                fdRootNode.showTree = $(".mind-map-tree ").find("[name='showTree']:checked").attr("value") || "0";
            }
            var fdOtherNode = this.mindMapOtherNode.getKeyData();
            return {
                fdRootNode: fdRootNode,
                fdOtherNode: fdOtherNode
            }
        },
        //后台数据渲染方法
        initByStoreData: function (sd) {
            var $table = this.cst.$container.find("#mindMapTableDom");
            if (sd.hasOwnProperty("fdRootNode")) {
                if (this.type == "treeView") {
                    $(".root-name-title").text(sd.fdRootNode.fdRootNodeName);
                    $table.find(".choice-root-view ul ").find("li[value='" + sd.fdRootNode.fdRootViewType + "']").trigger("click");
                    $table.find(".mind-map-root-cfg").find(".listViewText").text(sd.fdRootNode.fdRootViewModelName);
                    $table.find(".mind-map-root-cfg").find(".listViewText").attr("title",sd.fdRootNode.fdRootViewModelName);
                    $table.find(".mind-map-root-cfg").find(".listViewText").css("color", "#333333");
                    $table.find(".mind-map-root-cfg").find("[name='fdRootViewAppId']").val(sd.fdRootNode.fdRootViewAppId);
                    $table.find(".mind-map-root-cfg").find("[name='fdRootViewModelId']").val(sd.fdRootNode.fdRootViewModelId);
                    $table.find(".mind-map-root-cfg").find("[name='fdRootViewFdId']").val(sd.fdRootNode.fdRootViewFdId);
                    $table.find(".mind-map-root-cfg").find("[name='fdRootViewModelName']").val(sd.fdRootNode.fdRootViewModelName);
                    $table.find(".mind-map-root-cfg").find("[name='fdRootViewModelType']").val(sd.fdRootNode.fdRootViewModelType);
                    $("[name='isShowRoot'][value='" + sd.fdRootNode.isShowRoot + "']").prop("checked", true);
                    $("#mind-map-extend").find("option[value='"+sd.fdRootNode.extendLevel+"']").prop("selected",true)
                }
                if (sd.hasOwnProperty("fdRootNode")) {
                    $table.find("[name='fdRootName']").val(sd.fdRootNode.fdRootNodeName);
                    if (sd.fdRootNode.fdViewEnable != $table.find(".root-node-view").find("[name='fdViewEnable']").val()) {
                        $table.find(".root-node-view").find("[type='checkbox']").trigger("click");
                    }
                    $table.find("[name='fdLink']").val(sd.fdRootNode.fdLink);
                }
                if (sd.hasOwnProperty("fdOtherNode")) {
                    this.mindMapOtherNode.initByStoreData(sd.fdOtherNode);
                }
                $(".mind-map-option ul").find("li").each(function () {
                    if (sd.fdRootNode.defaultShowStyle == $(this).attr("value")) {
                        $(this).addClass("active");
                        $(this).siblings().removeClass("active");
                    }
                })
                $(".defaule-skin-value").attr("value",sd.fdRootNode.defaultSkin );
                $(".defaule-skin").attr("src",sd.fdRootNode.defaultSkinSrc);
                var showTree = sd.fdRootNode.showTree || "0";
                $(".mind-map-tree ").find("[name='showTree'][value='"+showTree+"']").trigger("click");
            }
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

            //成环校验
            if(!window.mindMap.mindMapOtherNode.verifyLoop(dataConfig)){
                dialog.alert(modelingLang['modelingMindMap.other.nodes.configured.loops']);
                return modelingLang['modelingMindMap.other.nodes.configured.loops'];
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
            var fdRootName = $("[name='fdRootName']").val();
            if(!fdRootName){
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
            //根节点视图穿透model-data-create
            var valueType = $(".choice-root-view ul li.active").attr("value");
            if(valueType == "0" && $("[name='fdRootViewModelName']").val() == ""){
                $(".validation-container").remove();
                $(".mind-map-root-cfg .list-content").append($("<div class='validation-container'><div class=\"validation-advice\" _reminder=\"true\">" +
                    "<table class=\"validation-table\"><tbody><tr><td>" +
                    "<div class=\"lui_icon_s lui_icon_s_icon_validator\"></div></td>" +
                    "<td class=\"validation-advice-msg\"><span class=\"validation-advice-title\">"+modelingLang['listview.view.penetration']+"</span>" +
                    modelingLang['kmReviewMain.notNull']+"</td></tr></tbody></table></div></div>"));
                $(".model-edit-view-bar").find("[respanel-bar-mark='frame']").trigger("click");
                return modelingLang['modelingMindMap.content.settings-Root.connot.null'];
            }
            if(valueType == "1"){
                if(cfg.fdRootNode.fdLink == ""){
                    $(".validation-container").remove();
                    $(".mind-map-root-cfg .list-content").append($("<div class='validation-container'><div class=\"validation-advice\" _reminder=\"true\">" +
                        "<table class=\"validation-table\"><tbody><tr><td>" +
                        "<div class=\"lui_icon_s lui_icon_s_icon_validator\"></div></td>" +
                        "<td class=\"validation-advice-msg\"><span class=\"validation-advice-title\">"+modelingLang['modelingAppViewtab.fdLinkParams']+"</span>" +
                        modelingLang['kmReviewMain.notNull']+"</td></tr></tbody></table></div></div>"));
                    $(".model-edit-view-bar").find("[respanel-bar-mark='frame']").trigger("click");
                    return modelingLang['modelingMindMap.custom.link.cannot.empty'];
                }
                //根节点自定义链接
                const linkRe = /^(?:http(s)?:\/\/)?[\w.-]+(?:\.[\w\.-]+)+[\w\-\._~:/?#[\]@!\$&'\(\)\*\+,;=.]+$/;
                const linkRe_insys = /^\/[a-zA-Z0-9\w-./?%&=]+/;
                var linkValue = cfg.fdRootNode.fdLink.replace(/(^\s+)|(\s+$)/g,"");
                if (linkValue != "" && !linkRe.test(linkValue) && !linkRe_insys.test(linkValue)){
                    $(".validation-container").remove();
                    $(".mind-map-root-cfg .list-content .model-mind-map-link").append($("<div class='validation-container'><div class=\"validation-advice\" _reminder=\"true\">" +
                        "<table class=\"validation-table\"><tbody><tr><td>" +
                        "<div class=\"lui_icon_s lui_icon_s_icon_validator\"></div></td>" +
                        "<td class=\"validation-advice-msg\"><span class=\"validation-advice-title\">"+modelingLang['modelingDynamicLink.linkKey']+"</span>" +
                        modelingLang['modelingMindMap.format.error']+"</td></tr></tbody></table></div></div>"));
                    $(".model-edit-view-bar").find("[respanel-bar-mark='frame']").trigger("click");
                    return modelingLang['modelingMindMap.custom.link.format.error'];
                }
            }

            //树节点——视图穿透
            /*var dataConfig = window.mindMap.getKeyData();
            var data = dataConfig.fdOtherNode;
            for(var i = 0;i < data.length;i++){
                if((data[i].fdTreeViewType == "0" && data[i].fdTreeViewModelId == "") || (data[i].fdTreeViewType == "1" && data[i].fdTreeViewLink == "")){
                    dialog.alert("【内容设置-树节点-视图穿透】不能为空");
                    return "【内容设置-树节点-视图穿透】不能为空";
                }
            }*/

            //树节点——匹配关系，防止树节点的目标表单更改之后，上级节点的目标表单更改导致匹配关系没填就提交
            var dataConfig = window.mindMap.getKeyData();
            var data = dataConfig.fdOtherNode;
            var notPassFlag = false;
            var tipInfo = "";
            for(var i = 0;i < data.length;i++){
                //目标表单的合法性
                var url = Com_Parameter.ContextPath + "sys/modeling/base/modeling.do?method=getModelWidget&modelId=" + data[i].fdTargetModelId;
                $.ajax({
                    url: url,
                    type: "get",
                    async: false,
                    success: function (data, status) {
                        if (data && JSON.parse(data).code != "200") {
                            notPassFlag = true;
                            dialog.alert(modelingLang['modelingMindMap.error.data.source.tree.node']);
                            tipInfo = modelingLang['modelingMindMap.error.data.source.tree.node'];
                            // return "有树节点的数据来源错误，请返回检查";
                        }
                    }
                });
                if(data[i].fdPreModelId == ""){
                    dialog.alert(modelingLang['modelingMindMap.tree.node.cannot.null']);
                    return modelingLang['modelingMindMap.tree.node.cannot.null'];
                }else{
                    if(data[i].fdPreModelId != "0"){
                        if(data[i].fdCurModelField == "" || data[i].fdTargetModelField == ""){
                            dialog.alert(modelingLang['modelingMindMap.node.relationship.cannot.null']);
                            return modelingLang['modelingMindMap.node.relationship.cannot.null'];
                        }
                    }
                }
            }
            if(notPassFlag){
                return tipInfo;
            }
        }
    };
    exports.MindMap = MindMap;
});
