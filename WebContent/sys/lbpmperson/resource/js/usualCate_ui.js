seajs.use(['lui/jquery','lui/dialog',"lui/topic"], function($,dialog,topic) {
    window.usualCateAddOrDelToFavorite = function(evt){
        Com_EventPreventDefault();
        Com_EventStopPropagation();
        var _self = this;
        var isActive = $(this).hasClass("active");
        var type = "add";
        if(isActive){
            type = "remove";
        }
        var modelName = window.usualCate_modelName;
        if(!modelName){
            var evt = Com_GetEventObject();
            if(evt && evt.target){
                //从每一项上读取
                modelName = $(evt.target).closest("li[data-item-modelName]").attr("data-item-modelName");
            }
        }
        var ids = [];
        var names = [];
        var $item = $(this).parents("li.lui_summary_item").eq(0);
        var id = $item.attr("data-item-fdId");
        var name = $item.attr("data-item-fdName");
        if(id) {
            ids.push(id);
            names.push(name);
            //这里直接调用处理方法，保证数据正常删除，
            window.usualCateQuickAddOrRemoveFavorite(type,modelName,ids,names,function(result){
                //数据更新后，通知所有的常用流程列表更新数据
                topic.publish("favorite.category.flat.change");
                if(!(evt && evt.data.type == "content")){
                    //数据更新后，通知全部流程组件更新数据
                    topic.publish("lui.content.reload",{modelName:modelName});
                }
                $(_self).toggleClass("active");
                var tip = "添加成功";
                if(type=="remove"){
                    tip = "删除成功";
                }
                dialog.success(tip);
            });
        }
    }
    window.usualCateQuickAddOrRemoveFavorite = function(action, modelName, ids, names, callback) {
        var url = null;
        if(action == 'add') {
            url = 'sys/person/sys_person_favorite_category/sysPersonFavoriteCategory.do?method=quickAdd';
        } else if(action == 'remove') {
            url = 'sys/person/sys_person_favorite_category/sysPersonFavoriteCategory.do?method=quickRemove';
        }
        if(!url) {
            return;
        }
        var data = {
            modelName: modelName,
            ids: ids,
            names: names
        };
        $.ajax({
            type : "POST",
            url : Com_Parameter.ContextPath + url,
            data : $.param(data, true),
            dataType : 'json',
            async: false,
            success : function(result) {
                if(callback){
                    callback(result);
                }
            },
            error : function(result) {
                console.error(result);
            }
        });
    }
});