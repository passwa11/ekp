window.isOuterHeight = true;//保证iframe设置的高度不包括滚动高度
Com_AddEventListener(window,"load",function(){
    //初始化计算高度
    var clientHeight = top.document.documentElement.clientHeight;
    var iframeTop = 81;//默认71
    var temp = 0;
    if(parent.document.all("mainIframe")) {
        var iframeDom = parent.document.all("mainIframe");
        var $bodyFrameDom = $(parent.document.all("mainIframe")).parents(".lui_list_body_frame:eq(0)");
        if($bodyFrameDom && $bodyFrameDom.length>0){
            temp = $bodyFrameDom.offset().top;
            if(temp > iframeTop){
                iframeTop = temp;
            }
        }
    }
    var contentPositionTop = 47;
    temp = $("#allProcessContent").position().top;
    if(temp > contentPositionTop){
        contentPositionTop = temp;
    }
    $("#allProcessContent").height(clientHeight - iframeTop - contentPositionTop - 10);
    // $(".lui_all_process .liu_content_list").bind("DOMNodeInserted",function(event) {
    //     Com_EventPreventDefault();
    //     Com_EventStopPropagation();
    //     if(parent.document.all("mainIframe")) {
    //         parent.document.all("mainIframe").style.height = document.body.offsetHeight + "px";
    //     }
    // })
    var enterPressKey = true; //回车键标识，等数据加载完成再回应
    $("#searchInput").keyup(function(event){
        if(event.keyCode == 13 && enterPressKey){//回车
            enterPressKey = false;
            var keyword = $(this).val();
            var treeView = LUI("treeView");
            if(!keyword && treeView.initAllModules == true){
                window.adviceTreeView();
            }else{
                treeView.setActiveStatus("");
                LUI("treeViewContent").search(keyword);
            }

        }
    });
    var enterPressKeyTree = true; //回车键标识，等数据加载完成再回应
    LUI("treeView").searchInputNode.unbind("keyup");//首先移除原先的事件,后面再绑定指定的事件
    LUI("treeView").searchInputNode.keyup(function(event){
        if(event.keyCode == 13 && enterPressKeyTree){//回车
            enterPressKeyTree = false;
            var keyword = $(this).val();
            LUI("treeView").doSearch(keyword);
        }
    });
    seajs.use(["lui/topic"], function(topic) {
        topic.subscribe("lui.treeview.content.render.finish",function(data){
            enterPressKey = true; //数据渲染完成，再进行数据的回车
            enterPressKeyTree = true;
        });
    });
    $("#searchInput").bind('input propertychange', function(){
        if($(this).val()){
            $("#searchRestBtn").show();
        }else{
            $("#searchRestBtn").hide();
        }
    })

    // $(document.body).click(function(event){
    //     var target = event.target;
    //     if(target == $("#searchInput")[0] || target == $("#searchRestBtn")[0]){
    //         return;
    //     }else{
    //         $("#searchRestBtn").hide();
    //     }
    // });
    $("#searchRestBtn").click(function(event){
        $("#searchInput").val("");
        var treeView = LUI("treeView");
        if(treeView.initAllModules == true){
            window.adviceTreeView();
        }else{
            treeView.setActiveStatus("");
            LUI("treeViewContent").search("");
        }
        $(this).hide();
    });
})
seajs.use(["lui/topic"], function(topic) {
    window.adviceTreeView = function(){
        topic.publish("lui.content.reload",{doLast:false})
    }
    //监听树节点点击事件，进行对应节点赋值
    topic.subscribe("lui.treeview.node.click",function(data){
        if(data.parentKey){
            var parentDom = $("[data-key='"+data.parentKey+"']");
            if(parentDom){
                for(var i = 0 ; i<parentDom.length;i++){
                    if(parentDom.eq(i).hasClass("expandable")){
                        if(LUI("treeView")){
                            if(LUI("treeView").lastClickSpanNode && !$(LUI("treeView").lastClickSpanNode).hasClass("active")){
                                LUI("treeView").lastClickSpanNode = parentDom.eq(i).find("span").eq(0);
                            }
                        }
                    }
                }
            }
        }
    });
});