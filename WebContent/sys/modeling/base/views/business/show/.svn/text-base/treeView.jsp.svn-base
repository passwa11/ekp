<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.simple">
    <template:replace name="head">
        <link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/views/business/res/common.css">
        <link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/views/business/res/jquery.treeview.css">
        <link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/views/business/res/template.css">
        <link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/views/business/res/treeViewMissing.css">
    </template:replace>
    <%-- 右侧内容区域 --%>
    <template:replace name="body">
        <script>
            Com_IncludeFile("jquery.js");
        </script>
        <!-- 内容区 starts -->
        <div id="treeviewMain">
            <span class="treeviewMainBtn"><i></i></span>
            <div class="treeviewMainContent">
            </div>
        </div>
        <div id="treeviewContent">
            <iframe src="" id="treeViewFrame" height="100%" width="100%" style="border: none"></iframe>
        </div>
        <script src="${LUI_ContextPath}/sys/modeling/base/views/business/res/jquery.treeview.js"></script>
        <script>
            const result =${result};
            const data = result.treeData;
            const businessId = result.fdId;
            var dataArr = [];
            dataArr.push(data);
            var extendLevel =result.extendLevel || "-1";

            //递归生成li
            function createTree(data,level) {
                var str = '<ul>';
                var level = level || 0;
                level +=1;
                for (var i = 0; i < data.length; i++) {
                    str += '<li lui-extend-leve="'+level+'"><span class="file" title="'+data[i].name+'"> <div style="margin-left: 8px">'  + data[i].name + '</div></span>';
                    str +="<input type='hidden' name='modelId' value='"+data[i].modelId+"'>";
                    str +="<input type='hidden' name='viewModelId' value='"+data[i].viewModelId+"'>";
                    str +="<input type='hidden' name='viewModelType' value='"+data[i].viewModelType+"'>";
                    str +="<input type='hidden' name='viewFdId' value='"+data[i].viewFdId+"'>";
                    str +="<input type='hidden' name='link' value='"+data[i].link+"'>";
                    str +="<input type='hidden' name='viewType' value='"+data[i].viewType+"'>";
                    str +="<input type='hidden' name='incFdId' value='"+data[i].id+"'>";
                    if (data[i].children.length > 0) {
                        str += createTree(data[i].children,level);
                    }
                    str += '</li>';
                };
                str += '</ul>';
                return str;
            };
            //渲染
            $(".treeviewMainContent").html(createTree(dataArr));
            $(".treeviewMainContent").treeview();

            // 侧边栏展开收起
            $('.treeviewMainBtn').click(function(){
                $('#treeviewMain').toggleClass("hide");
            });

            $("#treeviewMain li span").click(function (event) {
                event.stopPropagation(); //阻止事件冒泡
                $("#treeviewMain li").removeClass("selected");
                $(this).parent().addClass("selected");
                var viewType = $(this).parent().find("[name='viewType']").val();
                if(viewType == "0"){
                    //视图穿透
                    var viewModelId = $(this).parent().find("[name='viewModelId']").val();
                    var modelId = $(this).parent().find("[name='modelId']").val();
                    var fdId = $(this).parent().find("[name='viewFdId']").val();
                    var viewModelType = $(this).parent().find("[name='viewModelType']").val();
                    var incFdId = $(this).parent().find("[name='incFdId']").val();
                    var url = Com_Parameter.ContextPath + "sys/modeling/main/modelingAppModelMain.do?method=getIsFlowEnableByModel&modelId=" + viewModelId;
                    $.ajax({
                        url: url,
                        type: "get",
                        async: false,
                        success: function (data) {
                            if (data && typeof (data.isFlow) != "undefined" ) {
                                var isFlow = data.isFlow;
                                var link = "";
                                if(viewModelType == "collection"){
                                    link = "${LUI_ContextPath}"+"/sys/modeling/main/collectionView.do?method=index&canClose=false&isNew=true&listviewId="+fdId+"&fdAppModelId="+viewModelId+"&incFdId="+incFdId+"&treeViewId="+businessId+"&isFlow="+isFlow+"&targetModelId="+modelId;
                                }else if(viewModelType == "listView"){
                                    link = "${LUI_ContextPath}"+"/sys/modeling/main/listview.do?method=index&canClose=false&isNew=true&listviewId="+fdId+"&fdAppModelId="+viewModelId+"&incFdId="+incFdId+"&treeViewId="+businessId+"&isFlow="+isFlow+"&targetModelId="+modelId;
                                }else{
                                    link = "${LUI_ContextPath}"+"/sys/modeling/main/business.do?method=index&modelId="+viewModelId+"&businessId="+fdId+"&type="+viewModelType+"&isFlow="+isFlow+"&targetModelId="+modelId+"&incFdId="+incFdId+"&treeViewId="+businessId;
                                }
                                $('#treeViewFrame').attr("src",link);
                            }
                        }
                    });
                }else{
                    //自定义链接
                    var link = $(this).parent().find("[name='link']").val();
                    if (link){
                        if (link.indexOf("/")==0){
                            link = "${LUI_ContextPath}"+link;
                        }
                    }
                    $('#treeViewFrame').attr("src",link);
                }
            });

            if(data.isShowRoot == "1"){
                //根节点不展示
                $("#treeviewMain li div:eq(0)").css("display","none");
                $("#treeviewMain li span:eq(0)").css("display","none");
                $("#treeviewMain li ul li:eq(0)").find("span:eq(0)").trigger("click");
                //145995只有根节点又不展示根节点显示缺省页面
                if(dataArr.length == 1 && dataArr[0].children.length == 0){
                    var html = "<center><div class=\"listview_no_content\">\n" +
                        "                   <div class=\"treeviewMainContent\" style='top: 35%;\n" +
                        "    position: absolute;\n" +
                        "    height: auto;'>\n" +
                        "                         <div class=\"listview_default_images\">\n" +
                        "                        </div>\n" +
                        "                        <div class=\"treeview_missing\">${ lfn:message('sys-modeling-main:modelingCalendar.on.content') }</div>\n" +
                        "                    </div>\n" +
                        "                </div></center>"
                    $(".treeviewMainContent").append(html);
                    var link = "${LUI_ContextPath}"+"/sys/modeling/base/views/business/show/treeViewMissing.jsp";
                    $('#treeViewFrame').attr("src",link);
                }
                if (extendLevel){
                    $($(".treeviewMainContent").find("[lui-extend-leve='"+(parseInt(extendLevel)+1)+"'] .hitarea").toArray().reverse()).trigger($.Event("click"));
                }
            }else{
                if (extendLevel){
                    $($(".treeviewMainContent").find("[lui-extend-leve='"+extendLevel+"'] .hitarea").toArray().reverse()).trigger($.Event("click"));
                }
                $("#treeviewMain li:eq(0)").find("span:eq(0)").trigger("click");
            }

        </script>
    </template:replace>
</template:include>