var isShow = false;
seajs.use([ 'sys/ui/js/dialog' ,'lui/topic'],function(dialog,topic) {
    //更新引动后的行属性
    window.updateRowAttr = function(direct,type,thisObj){
        if(!direct && direct != 0){
            //删除
            lastSelectPostionObj = null;
            lastSelectPostionDirect = null;
            //刷新预览
            topic.publish("preview.refresh");
            return;
        }
        var optTR = $(thisObj).parents("tr")[0] || DocListFunc_GetParentByTagName("TR");
        var optTB = DocListFunc_GetParentByTagName("TABLE", optTR);
        var rowIndex = Com_ArrayGetIndex(optTB.rows, optTR);
        if(direct == 0){
            Com_EventStopPropagation();
            //更新图标
            if(rowIndex == 0){
                $(optTB).find(">tbody>tr").eq(rowIndex+1).find("div.down").show();
                $(optTB).find(">tbody>tr").eq(rowIndex+1).find("div.up").hide();
            }else if(rowIndex == $(optTB).find(">tbody>tr").length-1){
                $(optTB).find(">tbody>tr").eq(rowIndex-1).find("div.down").hide();
                $(optTB).find(">tbody>tr").eq(rowIndex-1).find("div.up").show();
            }
            if(type != 'no-position'){
                //更新标题和更新位置
                var trs = $(optTB).find(">tbody>tr");
                for(var i=rowIndex+1; i<trs.length; i++){
                    $(trs[i]).find("span.title-index").text(i);
                    var position = $(trs[i]).find(".model-edit-view-oper").attr("data-lui-position");
                    var prefix = position.split("-")[0];
                    var newPosition = prefix +"-"+ (i-1);
                    $(trs[i]).find(".model-edit-view-oper").attr("data-lui-position", newPosition);
                }
            }
            return;
        }
        var tagIndex = rowIndex - direct;
        //更新图标
        var len = $(optTB).find(">tbody>tr").length;
        if(rowIndex == 0){
            $(optTR).find("div.down").show();
            $(optTR).find("div.up").hide();
        }else if(rowIndex == len-1){
            $(optTR).find("div.down").hide();
            $(optTR).find("div.up").show();
        }else{
            $(optTR).find("div.down").show();
            $(optTR).find("div.up").show();
        }
        if(tagIndex == 0){
            $(optTB).find(">tbody>tr").eq(tagIndex).find("div.down").show();
            $(optTB).find(">tbody>tr").eq(tagIndex).find("div.up").hide();
        }else if(tagIndex == len-1){
            $(optTB).find(">tbody>tr").eq(tagIndex).find("div.down").hide();
            $(optTB).find(">tbody>tr").eq(tagIndex).find("div.up").show();
        }else{
            $(optTB).find(">tbody>tr").eq(tagIndex).find("div.down").show();
            $(optTB).find(">tbody>tr").eq(tagIndex).find("div.up").show();
        }
        //更新标题
        $(optTR).find("span.title-index").text(rowIndex+1);
        $(optTB).find(">tbody>tr").eq(tagIndex).find("span.title-index").text(tagIndex+1);
        //更新位置
        if(type != "no-position"){
            var position = $(optTR).find(".model-edit-view-oper").attr("data-lui-position");
            var prefix = position.split("-")[0];
            var newPosition = prefix +"-"+ rowIndex;
            $(optTR).find(".model-edit-view-oper").attr("data-lui-position", newPosition);
            $(optTB).find(">tbody>tr").eq(tagIndex).find(".model-edit-view-oper").attr("data-lui-position",prefix+"-"+tagIndex);
            //刷新预览
            topic.publish("preview.refresh");
        }
    }

    window.updateItemAttr = function(direct,type,thisObj){
    }

    window.changeToOpenOrClose = function(obj){
        var $parent = $(obj).parents(".model-edit-view-oper").eq(0);
        if($parent.find(".model-edit-view-oper-content.close")[0]){
            //关闭状态 - 打开状态
            $(obj).find("i").removeClass("close");
            $parent.find(".model-edit-view-oper-content").removeClass("close");
        }else{
            //开始状态 - 关闭状态
            $(obj).find("i").addClass("close");
            $parent.find(".model-edit-view-oper-content").addClass("close");
        }
    }

    var lastDotsObj;
    window.showMoreItem = function(thisObj,moreId,isUpward){
        Com_EventStopPropagation();
        //构建一个下拉列表
        $("p[data-lui-position]").removeClass('active');
        $("div.model-edit-view-btn-item").removeClass("active");
        $(thisObj).addClass("active");
        var html = $("#"+moreId).html();
        var container = $("#moreList");
        /*var div = $("<div class='triangle'>");
        if(isUpward){
            div = $("<div class='triangle down'>");
        }
        container.append(div);*/
        container.html("");
        container.append(html);
        var offset = $(thisObj).offset();
        var left = offset.left;
        var top = offset.top;
        container.css({
            "max-height": "250px",
            "overflow": "auto",
            "padding":"0",
            "border":"0",
            "margin":"0"
        });
        container.show();
        container.offset({left:(left-30),top:(top+26+10)});
        if(isUpward){
            container.offset({left:(left-30),top:(top-container.outerHeight(true))});
        }

        $("#moreList").find("ul").eq(0).bind("mouseleave",function(){
            Com_EventStopPropagation();
            //if(isShow){
            $("#moreList").hide();
            isShow = false;
            return false;
        });
        lastDotsObj = thisObj;
        return false;
    }

    window.showMoreItemMobile = function(thisObj,moreId){
        Com_EventStopPropagation();
        //构建一个下拉列表
        $("p[data-lui-position]").removeClass('active');
        $("div.model-edit-view-oper").removeClass("active");
        $(thisObj).addClass("active");
        var container = $("#"+moreId);
        var offset = $(thisObj).offset();
        var left = offset.left;
        var top = offset.top;
        container.show();
        container.offset({left:(left-39),top:(top+26+10)});
        container.css({
            "padding":"0",
            "border":"0",
            "margin":"0",
            "max-height": "250px",
            "overflow": "auto"
        })
        container.find("ul").eq(0).bind("mouseleave",function(){
            Com_EventStopPropagation();
            container.hide();
            return false;
        });
        lastDotsObj = thisObj;
        return false;
    }

    window.hideMoreItem = function(moreId){
        $("#"+moreId).hide();
        if(lastDotsObj){
            $(lastDotsObj).addClass("active");
        }
    }

    //预览加载完毕事件
    topic.subscribe('preview_load_finish', function (ctx) {
        //监听滚动事件，用于处理一些内容
        $('.model-edit-view-content').on('scroll',function(){
            $("#moreList").hide();
        });
    });

    window.delItem = function (dom, type) {
        var $tr = $(dom).closest(".item");
        var curIndex = $tr.attr("index");
        var luiId = $tr.parents("[data-lui-cid]").eq(0).attr("data-lui-cid");
        var kclass = LUI(luiId);
        if (type == 'orderby') {
            var orderCollection = kclass.orderCollection;
            var wgt = orderCollection[curIndex];
            topic.channel(kclass.channel).publish("order.delete", {"wgt": wgt});
            topic.channel(kclass.channel).unsubscribe("field.change", wgt.fieldChange, wgt);
            wgt.destroy();
            return;
        } else if (type == 'where') {
            var name = $(dom).closest("table").attr("name");
            var wgt = null;
            if(name == "sys_query"){
                var syswhereCollection = kclass.syswhereCollection;
                wgt = syswhereCollection[curIndex];
                topic.channel("modeling").publish("where.delete", {"wgt": wgt,"name":name});
            }else if(name == "custom_query"){
                var whereCollection = kclass.whereCollection;
                wgt = whereCollection[curIndex];
                topic.channel("modeling").publish("where.delete", {"wgt": wgt,"name":name});
            }
            topic.channel("modeling").unsubscribe("field.change", wgt.fieldChange, wgt);
            wgt.destroy();
            return;
        } else if (type == 'operate') {
            var operateCollection = kclass.operateCollection;
            var wgt = operateCollection[curIndex];
            topic.channel(kclass.channel).publish("operate.delete", {"wgt": wgt});
            topic.channel(kclass.channel).unsubscribe("field.change", wgt.fieldChange, wgt);
            wgt.destroy();
            return;
        }
        $tr.remove();
    };

});