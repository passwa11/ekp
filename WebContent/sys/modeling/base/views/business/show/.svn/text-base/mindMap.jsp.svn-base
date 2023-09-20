<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.simple">
    <template:replace name="head">
        <script>
            Com_IncludeFile("g6.4.3.3.min.js", Com_Parameter.ContextPath + 'sys/modeling/base/resources/antv/', 'js', true);
        </script>
        <link type="text/css" rel="stylesheet"
              href="${LUI_ContextPath}/sys/modeling/base/resources/css/override.css?s_cache=${LUI_Cache}"/>
        <link type="text/css" rel="stylesheet"
              href="${LUI_ContextPath}/sys/modeling/base/resources/css/preview.css?s_cache=${LUI_Cache}"/>
        <link type="text/css" rel="stylesheet"
              href="${LUI_ContextPath}/sys/modeling/base/views/business/res/calendar.css?s_cache=${LUI_Cache}"/>
        <link type="text/css" rel="stylesheet"
              href="${LUI_ContextPath}/sys/modeling/base/views/business/res/mindMapRun.css?s_cache=${LUI_Cache}"/>

        <link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/views/business/res/common.css">
        <link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/views/business/res/jquery.treeview.css">
        <link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/views/business/res/template.css">
    </template:replace>
    <%-- 右侧内容区域 --%>
    <template:replace name="body">
        <script>
            Com_IncludeFile("jquery.js");
        </script>
        <div id="treeviewMain">
            <span class="treeviewMainBtn"><i></i></span>
            <div class="treeviewMainContent">
            </div>
        </div>
        <div id="treeviewContent">
            <!-- 内容区 starts -->
            <div class="fullscreen">
                <div class="mind-map-body ">
                    <div class="mind-map-title">
                         ${ lfn:message('sys-modeling-base:modelingMindMap.no.name') }
                    </div>
                    <br>
                    <div class="mind-map-body-content">

                        <div id="container"></div>
                            <%--                    <div id="minimap"></div>--%>
                    </div>
                </div>
                <!-- 内容区 ends -->

                <!-- 右边栏 starts 位置-->
                <div class="mind-map-rightbar">
                    <div class="mind-map-rightbar-btn">
                        <div class="mind-map-rightbar-btn-center">
                            <i></i>
                        </div>
                    </div>
                    <div class="mind-map-right-content">
                        <ul class="mind-map-tab">
                            <li class="active">${ lfn:message('sys-modeling-main:modelingGantt.chart.style') }</li>
                                <%--                        <li>节点样式</li>--%>
                        </ul>
                        <div class="mind-map-tab-body graph-style">
                            <p>${ lfn:message('sys-modeling-base:modelingMindMap.default.layout') }</p>
                            <div class="mind-map-tab-body-panel">
                                <ul class="panel-block">
                                    <li class="LR"></li>
                                    <li class="TB"></li>
                                </ul>
                            </div>
                        </div>
                        <div class="mind-map-tab-body graph-style">
                            <p>${ lfn:message('sys-modeling-base:modelingMindMap.default.theme') }</p>
                            <div class="mindMap_style_box mindMap_Skin">
                                <ul>
                                    <li class="mindMap_style_item mindMap_style_shadow " value="0"
                                        onclick="changeStyle(this);">
                                        <i></i>
                                        <img src="${LUI_ContextPath}/sys/modeling/base/views/business/res/img/mindMap/style-steady@2x.png">
                                        <span>${ lfn:message('sys-modeling-base:modelingMindMap.steady') }</span>
                                    </li>
                                    <li class="mindMap_style_item mindMap_style_shadow "value="1"
                                        onclick="changeStyle(this);">
                                        <i></i>
                                        <img src="${LUI_ContextPath}/sys/modeling/base/views/business/res/img/mindMap/style-simple@2x.png">
                                        <span>${ lfn:message('sys-modeling-base:modelingMindMap.simplicity') }</span>
                                    </li>
                                    <li class="mindMap_style_item mindMap_style_shadow "value="2"
                                        onclick="changeStyle(this);">
                                        <i></i>
                                        <img src="${LUI_ContextPath}/sys/modeling/base/views/business/res/img/mindMap/style-Colorful@2x.png">
                                        <span>${ lfn:message('sys-modeling-base:modelingMindMap.colorful') }</span>
                                    </li>
                                    <li class="mindMap_style_item mindMap_style_shadow "value="3"
                                        onclick="changeStyle(this);">
                                        <i></i>
                                        <img src="${LUI_ContextPath}/sys/modeling/base/views/business/res/img/mindMap/style-blueSky.png">
                                        <span>${ lfn:message('sys-modeling-base:modelingMindMap.blue.sky') }</span>
                                    </li>
                                    <li class="mindMap_style_item mindMap_style_shadow "value="4"
                                        onclick="changeStyle(this);">
                                        <i></i>
                                        <img src="${LUI_ContextPath}/sys/modeling/base/views/business/res/img/mindMap/style-sea.png">
                                        <span>${ lfn:message('sys-modeling-base:modelingMindMap.deep.sea') }</span>
                                    </li>
                                    <li class="mindMap_style_item mindMap_style_shadow "value="5"
                                        onclick="changeStyle(this);">
                                        <i></i>
                                        <img src="${LUI_ContextPath}/sys/modeling/base/views/business/res/img/mindMap/style-shadow@2x.png">
                                        <span>${ lfn:message('sys-modeling-base:modelingMindMap.shadow') }</span>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
        <script src="${LUI_ContextPath}/sys/modeling/base/views/business/res/jquery.treeview.js"></script>
        <script>
            const container = document.getElementById('container');
            const width = $("#container").width();
            const height = $(".mind-map-body").height() || 500;
            const result =${result};
            const data = result.mindTree;
            var defaultSkin ='${defaultSkin}';

            //是否显示树形大纲
            if(data && data.showTree == "1"){
                $("#treeviewMain").css("display","none");
                $('#treeviewMain').toggleClass("hide");
            }

            //构造生成树结构的数据格式
            var dataArr = [];
            dataArr.push(data);
            //树形结构——递归生成li
            function createTree(data) {
                var str = '<ul>';
                for (var i = 0; i < data.length; i++) {
                    str += '<li><span class="file" title='+data[i].name+'><div style="margin-left: 8px">'  + data[i].name + '</div></span>';
                    str +="<input type='hidden' name='id' value='"+data[i].id+"'>";
                    str +="<input type='hidden' name='noteCfg' value='"+JSON.stringify(data[i])+"'>";
                    if (data[i].children.length > 0) {
                        str += createTree(data[i].children);
                    }
                    str += '</li>';
                };
                str += '</ul>';
                return str;
            };
            //渲染
            $(".treeviewMainContent").html(createTree(dataArr));
            $(".treeviewMainContent").treeview({collapsed:false});

            // 侧边栏展开收起
            $('.treeviewMainBtn').click(function(){
                $('#treeviewMain').toggleClass("hide");
                if($("#treeviewMain").hasClass("hide")){
                    $("#container canvas").css("margin-left","2px");
                }else{
                    $("#container canvas").css("margin-left","160px");
                }
            });


            $("#treeviewMain li span").click(function (event) {
                event.stopPropagation(); //阻止事件冒泡
                $("#treeviewMain li").removeClass("selected");
                $(this).parent().addClass("selected");
                $("#treeviewMain ul").css("background-color","");
                // $(this).find("ul").css("background-color","#F8F8F8");
                var noteCfg = $.parseJSON($(this).parent().find("[name='noteCfg']").val());
                if (window.g6g) {
                    window.g6g.read(noteCfg);
                }
            });
            if(data.isShowRoot == "1"){
                //根节点不展示
                $("#treeviewMain li div:eq(0)").css("display","none");
                $("#treeviewMain li span:eq(0)").css("display","none");
                $("#treeviewMain li ul li:eq(0)").find("span:eq(0)").trigger("click");
            }else{
                $("#treeviewMain li:eq(0)").find("span:eq(0)").trigger("click");
            }

            seajs.use(["lui/jquery", "sys/ui/js/dialog",
                "sys/modeling/base/views/business/show/mindMap/G6Graph"], function ($, dialog, G6Graph, rml) {
                //title
                if (result.title) {
                    $(".mind-map-title").html(result.title)
                }

                //layout
                const defLayout = result.showStyle=="1"?"TB":"LR";
                $(".panel-block").find("."+defLayout).addClass("active");
                $(".mindMap_Skin").find("li[value='"+defaultSkin+"']").addClass("active");
                var g6g;
                window.buildGraph = function (layout) {
                    if (g6g) {
                        g6g.switchLayout(layout);
                        return;
                    }
                    g6g = new G6Graph(container, data);
                    var tooltip={
                        type: 'tooltip',
                        formatText: function formatText(model) {
                            var text = model.description || model.name;
                            return text;
                        },

                        shouldUpdate: function shouldUpdate(e) {
                            return true;
                        },
                        updatePosition: function(t) {
                            var clientRect = this.container.getBoundingClientRect();
                            var left  = t.clientX > this.width/2?t.clientX-clientRect.width:t.clientX+12;
                            var top = t.clientY > this.height/2?t.clientY-clientRect.height:t.clientY+12;
                            this.container.style.left = left +"px";
                            this.container.style.top = top +"px";
                        }
                    };
                    g6g.options.layout.direction = layout;
                    g6g.options.modes.default.push(tooltip);
                    g6g.TreeGraph();
                    //展开收缩
                    g6g.graph.on('collapse-icon:click', (e) => {
                        const target = e.target;
                        const id = target.get('nodeId');
                        const item =  g6g.graph.findById(id);
                        const nodeModel = item.getModel();
                        nodeModel.collapsed = !nodeModel.collapsed;
                        g6g.graph.layout();
                        var collapsed = nodeModel.collapsed;
                        g6g.updateItem(item, {
                            collapsed,
                        });
                    });
                    window.g6g = g6g;
                    //视图穿透
                    window.g6g.graph.on('text-shape:click', function (evt) {
                        if (evt.item && evt.item._cfg && evt.item._cfg.model) {
                            var link = evt.item._cfg.model.link
                            if (link){
                                if (link.indexOf("/")==0){
                                    Com_OpenWindow("${LUI_ContextPath}"+link)
                                }else{
                                    Com_OpenWindow(link);
                                }

                            }else{
                                //其他节点
                                var fdId = evt.item._cfg.model.fdId;
                                debugger
                                var viewId = evt.item._cfg.model.viewId;
                                if(fdId != "" && viewId != ""){
                                    var link = "${LUI_ContextPath}"+"/sys/modeling/main/modelingAppView.do?method=modelView&fdId="+fdId+"&viewId="+viewId;
                                    Com_OpenWindow(link);
                                }else{
                                    //默认视图
                                    var modelId = evt.item._cfg.model.modelId;
                                    var url = "${LUI_ContextPath}"+"/sys/modeling/main/modelingAppView.do?method=getDefaultView&modelId="+modelId;
                                    $.ajax({
                                        type:"post",
                                        url:url,
                                        success:function(data){
                                            if(data){
                                                if($.parseJSON(data).viewId && $.parseJSON(data).viewId != ""){
                                                    var link = "${LUI_ContextPath}"+"/sys/modeling/main/modelingAppView.do?method=modelView&fdId="+fdId+"&viewId="+$.parseJSON(data).viewId;
                                                    Com_OpenWindow(link);
                                                }else{
                                                    //未配置默认视图，使用系统默认视图
                                                    var isFlow = evt.item._cfg.model.isFlow;
                                                    if(isFlow == "true"){
                                                        var link = "${LUI_ContextPath}"+"/sys/modeling/main/modelingAppModelMain.do?method=view&fdId="+fdId;
                                                        Com_OpenWindow(link);
                                                    }else if(isFlow == "false"){
                                                        var link = "${LUI_ContextPath}"+"/sys/modeling/main/modelingAppSimpleMain.do?method=view&fdId="+fdId;
                                                        Com_OpenWindow(link);
                                                    }
                                                }
                                            }
                                        }
                                    })
                                }
                            }
                        }
                    });
                }
                window.buildGraph(defLayout);

                // g6g.registerPlugins("Minimap", {
                //     container:document.getElementById('minimap'),
                //     size: [150, 100]
                // })

                //右侧样式区域
                $(".mind-map-rightbar-btn").click(function () {
                    $(".fullscreen").toggleClass("extend")
                    var $b = $(".mind-map-body")
                    var w = $b[0].clientWidth;
                    if ($(".fullscreen").hasClass("extend")) {
                        w -= 300;
                        var width = $(".mind-map-body-content canvas").width();
                        $(".mind-map-body-content canvas").css("width",(width-300) + "px");
                    } else {
                        w += 300;
                        var width = $(".mind-map-body-content canvas").width();
                        $(".mind-map-body-content canvas").css("width",width+300 + "px");
                    }
                    $b.css("width", w + "px");
                })

                $(".panel-block li").click(function () {
                    if ($(this).hasClass("actvie")) {
                        return;
                    }
                    $(".panel-block li").removeClass("active")
                    $(this).addClass("active");
                    if ($(this).hasClass("TB")) {
                        buildGraph("TB")
                    } else if ($(this).hasClass("LR")) {
                        buildGraph("LR")
                    }
                })


                if (typeof window !== 'undefined') {
                    window.onresize = () => {
                        var w = $("#container").width();
                        var h = $(".mind-map-body").height() - 50;
                        if (!g6g) return;
                        g6g.changeSize(w, h);
                    };
                }

                window.changeStyle = function (obj) {
                    $(obj).siblings().removeClass("active");
                    if ($(obj).hasClass("active")) {
                        return;
                    }
                    $(obj).addClass("active");
                    var defaultSkin = $(obj).val();
                    if (g6g) {
                        g6g.changeSkin(defaultSkin);
                        changeTitleBackColor(defaultSkin);
                        return;
                    }
                };

            });
            function changeTitleBackColor (defaultSkin){
                console.log("defaultSkin",defaultSkin);
                if (defaultSkin ==3){
                    $(".mind-map-body ").css("background-color","rgb(245, 252, 255)");
                    $(".mind-map-body ").css("color","rgb(0, 0, 0)");
                }else if (defaultSkin ==4){
                    $(".mind-map-body ").css("background-color","rgb(33, 39, 62)");
                    $(".mind-map-body ").css("color","#FFFFFF");
                }else if (defaultSkin ==5){
                    $(".mind-map-body ").css("background-color","rgb(0, 0, 0)");
                    $(".mind-map-body ").css("color","#FFFFFF");
                }else {
                    $(".mind-map-body ").css("background-color","#FFFFFF");
                    $(".mind-map-body ").css("color","rgb(0, 0, 0)");
                }
            };
            window.onload=function(){
                if($("#treeviewMain").hasClass("hide")){
                $("#container canvas").css("margin-left","2px");
            }else{
                $("#container canvas").css("margin-left","160px");
            }
                changeTitleBackColor(defaultSkin);
            }

        </script>


    </template:replace>
</template:include>