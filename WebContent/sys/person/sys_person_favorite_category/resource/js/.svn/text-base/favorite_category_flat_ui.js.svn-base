var favoriteTmp = {};
seajs.use(['lui/jquery','lui/util/str','lui/dialog',"lui/topic","lang!sys-person:ui.commonly"], function($,str,dialog,topic,lang) {
    window.setFavouriteCategory = function(){
        handleSettingFavorite();
    };

    window.handleSettingFavorite = function() {
        // 重新获取常用分类数据
        getFavorite();
        doSettingFavorite(window.favorite_modelName);
    };

    window.favoriteUrlVariableResolver = str.variableResolver;

    window.openCreate = function(id){
        var params = {
            id: id
        };
        var url = window.favorite_contextPath+window.favorite_addUrl;
        url = favoriteUrlVariableResolver(url, params);

        window.open(url,"_blank");
    };

    window.openCategory = function(){
        if(window.favorite_isSimpleCategory == 'false' || window.favorite_cateType == 'globalCategory'){
            dialog.categoryForNewFile(window.favorite_modelName,window.favorite_addUrl,false,null,null,null,null,null,true,null,null,window.favorite_key);
        }else{
            dialog.simpleCategoryForNewFile(window.favorite_modelName,window.favorite_addUrl,false,null,null,null);
        }
    };

    topic.subscribe("favorite.category.flat.change",function(){
        window.refreshFavourite();
    });

    //删除单个常用流程
    window.delOneFavorite = function(evt){
        Com_EventPreventDefault();
        Com_EventStopPropagation();
        var _self = this;
        var modelName = window.favorite_modelName;
        if(!modelName){
           var evt = Com_GetEventObject();
           if(evt && evt.target){
               //从每一项上读取
               modelName = $(evt.target).closest("li[data-item-modelName]").attr("data-item-modelName");
           }
        }
        //进行二次确认
        dialog.confirm(lang['ui.commonly.btn.del.tip']+"?",function(data){
            if(data == true){
                var removeIds = [];
                var removeNames = [];
                var $item = $(_self).parents("li.lui_summary_item").eq(0);
                var id = $item.attr("data-item-fdId");
                var name = $item.attr("data-item-fdName");
                if(id){
                    removeIds.push(id);
                    removeNames.push(name);
                    //从数据库删除
                    quickAddOrRemoveFavorite('remove', modelName, removeIds, removeNames,function(result){
                        //更新当前的总数据
                        if(favoriteTmp){
                            delete favoriteTmp[id];
                        }
                        //更新页面数据
                        window.refreshFavourite(true);
                        if(!(evt && evt.data.type == "content")){
                            //数据更新后，通知全部流程组件更新数据
                            topic.publish("lui.content.reload",{modelName:modelName});
                        }
                    });
                }
            }
        });
    };

    window.moveUpdate = function(){
        var ids = [];
        var names = [];
        $("#favouriteCateDiv").find("[data-item-fdid]").each(function(index,item){
            var id = $(item).attr("data-item-fdid");
            var name = $(item).attr("data-item-fdname");
            ids.push(id);
            names.push(name);
        })
        var modelName = window.favorite_modelName;
        var url = 'sys/person/sys_person_favorite_category/sysPersonFavoriteCategory.do?method=moveUpdate';
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
                // if(callback){
                //     callback(result);
                // }
            },
            error : function(result) {
                console.error(result);
            }
        });
    };

    window.getFavorite = function() {
        $.ajax({
            url: Com_Parameter.ContextPath + 'sys/person/sys_person_favorite_category/sysPersonFavoriteCategory.do?method=favorite&modelName='+window.favorite_modelName,
            async: false,
            success: function(data) {
                var ids = [];
                var names = [];
                favoriteTmp = {};
                $.each(data, function(i, d){

                    favoriteTmp[d.value] = d.text;

                    ids.push(d.value);
                    names.push(d.text);
                });

                $('input[name="fdCategoryIds"]').val(ids.join(';'));
                $('input[name="fdCategoryNames"]').val(names.join(';'));
            },
            error: function(err) {
                console.error(err);
            }
        });
    };

    window.doSettingFavorite = function(modelName) {

        var authType = 0;

        switch(modelName) {
            case 'com.landray.kmss.km.imissive.model.KmImissiveSendTemplate':
            case 'com.landray.kmss.km.imissive.model.KmImissiveReceiveTemplate':
            case 'com.landray.kmss.km.imissive.model.KmImissiveSignTemplate':
            case 'com.landray.kmss.km.review.model.KmReviewTemplate':
                authType = 2;
                break;
            default:
                authType = 0;
                break;
        }

        var opt = {
            modelName: modelName,
            idField: 'fdCategoryIds',
            nameField: 'fdCategoryNames',
            mulSelect: true,
            authType: authType,
            noFavorite: true,
            notNull: false,
            action: function(params) {
                if(!params) {
                    return;
                }

                var ids = (params.id || '').split(';');
                var names = (params.name || '').split(';');
                var _favoriteTmp = {};

                $.each(ids, function(i, id){

                    if(!id || !names[i]){
                        return;
                    }

                    _favoriteTmp[id] = names[i];
                });

                var addIds = [];
                var addNames = [];
                $.each(_favoriteTmp, function(key, value){
                    if(!favoriteTmp[key]){
                        addIds.push(key);
                        addNames.push(value);
                    }
                });

                if(addIds.length > 0) {
                    quickAddOrRemoveFavorite('add', modelName, addIds, addNames);
                }

                var removeIds = [];
                var removeNames = [];
                $.each(favoriteTmp, function(key, value){
                    if(!_favoriteTmp[key]){
                        removeIds.push(key);
                        removeNames.push(value);
                    }
                });

                if(removeIds.length > 0) {
                    quickAddOrRemoveFavorite('remove', modelName, removeIds, removeNames);
                }

                favoriteTmp = _favoriteTmp;
                refreshFavourite();

            }
        };

        if(window.favorite_isSimpleCategory == 'false' || window.favorite_cateType == 'globalCategory') {
            seajs.use(['lui/dialog'], function(dialog) {
                dialog.category(opt);
            });
        } else {
            seajs.use(['lui/dialog'], function(dialog) {
                dialog.simpleCategory(opt);
            });

        }
    };
    window.quickAddOrRemoveFavorite = function(action, modelName, ids, names, callback) {

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
    };
    window.refreshFavourite = function(isDelOpt){
        if(LUI('favouriteCate')){
            //if(isDelOpt){
            LUI('favouriteCate').reload();
            //}else{
            //   LUI('favouriteCate').reload();
            //}
        }
        if(LUI('usualCate')){
            LUI('usualCate').reload();
        }
    };
});