function appendQueryParameter(url){
    if(location.search=="")
        return url;
    var paraList = location.search.substring(1).split("&");
    for(var i=0; i<paraList.length; i++){
        var para = paraList[i];
        var index = para.indexOf("q.");
        if(index!=0){
            continue;
        }
        if(url.indexOf('?')==-1){
            url += '?' + para;
        }else{
            url += '&' + para;
        }
    }
    return url;
}

seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/dialog_common'], function($, dialog , topic, dialogCommon){
    var option = window.listOption;
    function addDoc(){
    	var url = option.contextPath + option.basePath + "?method=add";
    	if(option.categoryId) {
    		url += '&categoryId=' + option.categoryId;
    	}
    	if(option.customCategory) {
    		url += '&customCategory=' + option.customCategory;
    	}
    	
        Com_OpenWindow(url);
    }
    function addTemplateConfig(){
        dialog.categoryForNewFile(option.modelName, option.basePath + '?method=add&i.docCategory=!{id}',
                false,null,null,getValueByHash("docCategory"),null,null,false);
    }
    function addTemplateMain(){
        dialog.categoryForNewFile(option.templateName, option.basePath + '?method=add&i.docTemplate=!{id}',
                false,null,null,getValueByHash("docTemplate"));
    }
    function addSCategoryMain(){
    	dialog.simpleCategoryForNewFile(option.templateName, option.basePath + '?method=add&i.docTemplate=!{id}',
    			false,null,null,getValueByHash("docTemplate"));
    }
    function addOtherTemplateMain(){
    	var currTempId = getValueByHash("docTemplate");
    	if(currTempId!=null && currTempId!=''){
    		window.open(option.basePath + '?method=add&i.docTemplate='+currTempId,"_blank");
    	}else{
    		var context = option.createDialogCtx;
    		var sourceUrl = context.sourceUrl;
    		var params={};
    		if(context.params){
	    		for(var i=0;i<context.params.length;i++){
	    			var argu = context.params[i];
	    			for(var field in argu){
	    				var tmpFieldObj = document.getElementsByName(field);
	    				if(tmpFieldObj.length>0){
	    					params['c.' + argu[field] + '.'+field] = tmpFieldObj[0].value;
	    				}
	    			}
	    		}
    		}
    		dialogCommon.dialogSelectForNewFile(context.modelName, sourceUrl, params, 
    				option.basePath + '?method=add&i.docTemplate=!{id}',null,null,'_blank');
    	}
    }
    
    function deleteAll(){
        var selected = [];
        $("input[name='List_Selected']:checked").each(function(){
            selected.push($(this).val());
        });
        if(selected.length==0){
            dialog.alert(option.lang.noSelect);
            return;
        }
        dialog.confirm(option.lang.comfirmDelete,function(ok){
            if(ok==true){
                var del_load = dialog.loading();
                var param = {"List_Selected":selected};
                var hash = getValueByHash("docCategory");
                if(hash){
                    param.docCategory = hash;
                }
                hash = getValueByHash("docTemplate");
                if(hash){
                    param.docTemplate = hash;
                }
                $.ajax({
                	url:option.contextPath + option.basePath+'?method=deleteall',
                	data:$.param(param,true),
                	dataType:'json',
                	type:'POST',
                	success:function(data){
                		if(del_load!=null){
                            del_load.hide();
                            topic.publish("list.refresh");
                        }
                        dialog.result(data);
                	},
                	error:function(req){
                		if(req.responseJSON){
                			var data = req.responseJSON;
                			dialog.failure(data.title);
                		}else{
                			dialog.failure('操作失败');
                		}
                		del_load.hide();
                	}
                });
            }
        });
    }
    
    function openWindowViaDynamicForm(popurl,params,target){
        var form = document.createElement('form');
        if(form){
            try{
            target = !target?'_blank':target;
            form.style = "display:none;";
            form.method = 'post';
            form.action = popurl;
            form.target = target;
            if(params){
                for(var key in params){
                    var v = params[key];
                    var vt = typeof v;
                    var hdn = document.createElement('input');
                    hdn.type = 'hidden';
                    hdn.name = key;
                    if(vt == 'string' || vt == 'boolean' || vt == 'number'){
                        hdn.value = v+'';
                    }else{
                        if($.isArray(v)){
                            hdn.value = v.join(';');
                        }else{
                            hdn.value = toString(v);
                        }
                    }
                    form.appendChild(hdn);
                }
            }
            document.body.appendChild(form);
            form.submit();
            }finally{
                document.body.removeChild(form);
            }
        }
    }
    
    
    /*自定义操作按钮的click处理*/
    function doCustomOpt(optCode){
        var selected = [];
        $("input[name='List_Selected']:checked").each(function(){
            selected.push($(this).val());
        });
        if(selected.length==0){
            dialog.alert(option.lang.noSelect);
            return;
        }
        var del_load = dialog.loading();
        var param = {"List_Selected":selected};
        if(option.customOpts && option.customOpts[optCode]){
            var argsObject = option.customOpts[optCode];
            for(var arg in argsObject){
                param[arg] = argsObject[arg];    
            }
            if(argsObject.popup=='true'){
                var popurl = option.contextPath+argsObject.popupUrl;
                param['List_Selected_Count'] = selected.length;
                openWindowViaDynamicForm(popurl,param);
                del_load.hide();
                return;
            }
        }
        $.ajax({
            url:option.contextPath + option.basePath+'?method='+optCode,
            data:$.param(param,true),
            dataType:'json',
            type:'POST',
            success:function(data){
                if(del_load!=null){
                    del_load.hide();
                    topic.publish("list.refresh");
                }
                dialog.result(data);
            },
            error:function(req){
                if(req.responseJSON){
                    var data = req.responseJSON;
                    dialog.failure(data.title);
                }else{
                    dialog.failure('操作失败');
                }
                del_load.hide();
            }
        });
    }
    
    function getValueByHash(key){
        var value = Com_GetUrlParameter(location.href, 'q.'+key);
        if(value){
            return value;
        }
        var hash = window.location.hash;
        if(hash.indexOf(key)<0){
            return "";
        }
        var url = hash.split("cri.q=")[1];
            var reg = new RegExp("(^|;)"+ key +":([^;]*)(;|$)");
        var r=url.match(reg);
        if(r!=null){
            return unescape(r[2]);
        }
        return "";
    }
    
    topic.subscribe('criteria.changed',function(evt){
//      if(LUI('del')){ LUI('toolbar').removeButton(LUI('del'));}
        for(var i=0;i<evt['criterions'].length;i++){
            //alert(evt['criterions'][i].key+"="+evt['criterions'][i].value[0]);
        }
    });
    topic.subscribe('successReloadPage', function() {
        topic.publish('list.refresh');
    });
    
    switch(option.mode){
    case 'config_template':
        window.addDoc = addTemplateConfig;
        break;
    case 'main_template':
        window.addDoc = addTemplateMain;
        break;
    case 'main_scategory':
    	window.addDoc = addSCategoryMain;
    	break;
    case 'main_other':
    	window.addDoc = addOtherTemplateMain;
    	break;
    default:
        window.addDoc = addDoc;
    }
    window.deleteAll = deleteAll;
    window.doCustomOpt = doCustomOpt;
});
LUI.ready(function() {
    var option = window.listOption;
    if(option.canDelete==='' && LUI('btnDelete')){
        LUI('btnDelete').setVisible(false);
    }
});
