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

seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/dialog_common','lang!eop-basedata','lui/util/env'], function($, dialog , topic, dialogCommon,lang,env){
    var option = window.listOption;
    function addDoc(){
    	var fdCompanyId = Com_GetUrlParameter(window.location.href,"fdCompanyId");
    	fdCompanyId = fdCompanyId?"&fdCompanyId="+fdCompanyId:"";
        Com_OpenWindow(option.contextPath + option.basePath+"?method=add" + fdCompanyId);
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
							var v1 = data.message;
							var msg = v1[0].msg;
							if("该批次存在被采购需求引用的物料，不允许被删除"==msg){
								dialog.failure("该批次存在被采购需求引用的物料，不允许被删除");
							}else if("删除失败，选择的函件中存在被引用的数据，请去除!"==msg){
								dialog.failure("删除失败，选择的函件中存在被引用的数据，请去除!");
							}else{
								dialog.failure(data.title);
							}
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
    
    function edit(id){
    	if(id){
    		Com_OpenWindow(option.contextPath + option.basePath+"?method=edit&fdId=" + id);
    	}
    }
    function deleteDoc(id){
    	if(id){
 			var url = option.contextPath + option.basePath+"?method=delete&fdId=" + id;
			dialog.confirm(option.lang.comfirmDelete,function(value){
				if(value==true){
					window.del_load = dialog.loading();
					$.ajax({
						url: url,
						type: 'GET',
						data:{fdId:id},
						dataType: 'json',
						error: function(data){
							if(window.del_load!=null){
								window.del_load.hide(); 
							}
							dialog.result(data.responseJSON);
						},
						success: delCallback
				   });
				}
			});
    	}
    }
    
    function delCallback(data){
		if(window.del_load!=null){
			window.del_load.hide();
			topic.publish("list.refresh");
		}
		dialog.result(data);
	};
    
    
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
    window.edit = edit;
    window.deleteDoc = deleteDoc;
    
    //下发
	window.post = function(){
    	var ids = [];
    	$("[name=List_Selected]:checked").each(function(){
    		ids.push(this.value);
    	});
    	if(ids.length==0){
    		dialog.alert(listOption.lang.noSelect);
    		return;
    	}
    	//存在有效性筛选项的情况下必须选择有效
		if(LUI('criteria1').findSelectedValuesByKey("fdIsAvailable")){
			var selectedValues = LUI('criteria1').findSelectedValuesByKey("fdIsAvailable").values;
			if(selectedValues.length!=1||selectedValues[0].value!='true'){
				dialog.alert(lang['message.post.selectAvailable']);
				return false;
			}
		}
    	dialog.iframe(
    		'/eop/basedata/resource/jsp/eopBasedataSelectCompany.jsp',
    		lang["message.table.label.selectCompany"],
    		function(data){
    			if(!data){
    				return;
    			}
    			var param = {"List_Selected":ids};
    			var load = dialog.loading();
            	$.ajax({
            		url:env.fn.formatUrl(listOption.basePath+'?method=post'),
            		data:{ids:$.param(param,true),fdCompanyIds:data.fdCompanyIds},
            		type:'POST',
            		dataType:'json',
            		success:function(rtn){
            			load.hide();
            			dialog.result(rtn);
            			topic.publish("list.refresh");
            		}
            	});
    		},{width:800,height:600}
    	);
    }
	//启用
	window.enable = function(){
		var ids = [];
    	$("[name=List_Selected]:checked").each(function(){
    		ids.push(this.value);
    	});
    	if(ids.length==0){
    		dialog.alert(listOption.lang.noSelect);
    		return;
    	}
    	var param = {"List_Selected":ids};
    	var load = dialog.loading();
    	$.ajax({
       		url:env.fn.formatUrl(listOption.basePath+'?method=enable'),
       		data:{ids:$.param(param,true),modelName:listOption.modelName},
       		type:'POST',
       		dataType:'json',
       		success:function(rtn){
       			load.hide();
       			dialog.result(rtn);
       			topic.publish("list.refresh");
       		},
       		error:function(){
    			dialog.failure(lang['message.post.dialog.failure']);
    			load.hide();
    		}
    	});
	}
	//禁用
	window.disable = function(){
		var ids = [];
    	$("[name=List_Selected]:checked").each(function(){
    		ids.push(this.value);
    	});
    	if(ids.length==0){
    		dialog.alert(listOption.lang.noSelect);
    		return;
    	}
    	var param = {"List_Selected":ids};
    	var load = dialog.loading();
    	$.ajax({
       		url:env.fn.formatUrl(listOption.basePath+'?method=disable'),
       		data:{ids:$.param(param,true),modelName:listOption.modelName},
       		type:'POST',
       		dataType:'json',
       		success:function(rtn){
       			load.hide();
       			dialog.result(rtn);
       			topic.publish("list.refresh");
       		}
    	});
	}
	//复制
	window.copy = function(){
		//必须选择有效的数据进行复制
		if(LUI('criteria1').findSelectedValuesByKey("fdIsAvailable")){
			var values = LUI('criteria1').findSelectedValuesByKey("fdIsAvailable").values;
			if(values.length!=1||values[0].value!='true'){
				dialog.alert(lang['message.criteria.selectAvailable']);
				return false;
			}
		}
		//存在公司筛选项的情况下必须选择一个公司
		if(LUI('criteria1').findSelectedValuesByKey("fdCompany.fdId")){
			var selectedValues = LUI('criteria1').findSelectedValuesByKey("fdCompany.fdId").values;
			if(selectedValues.length!=1){
				dialog.alert(lang['message.criteria.selectCompany']);
				return false;
			}
		}
		var ids = [];
    	$("[name=List_Selected]:checked").each(function(){
    		ids.push(this.value);
    	});
    	if(ids.length==0){
    		dialog.alert(listOption.lang.noSelect);
    		return;
    	}
    	var load = dialog.loading();
    	dialog.iframe(
    		'/eop/basedata/resource/jsp/eopBasedataSelectCompany.jsp',
    		lang["message.table.label.selectCompany"],
    		function(data){
    			if(!data){
    				load.hide();
    				return;
    			}
            	$.ajax({
            		url:env.fn.formatUrl(listOption.basePath+'?method=copy'),
            		data:{ids:ids.join(";"),fdCompanyIds:data.fdCompanyIds,modelName:listOption.modelName},
            		dataType:'json',
            		type:'POST',
            		async:false,
            		success:function(rtn){
            			dialog.result(rtn);
            			topic.publish("list.refresh");
            			load.hide();
            		},
            		error:function(){
            			dialog.failure('操作失败');
            			load.hide();
            		}
            	});
    		},{width:600,height:250}
    	);
	}
	//列表页面切换树形显示
	window.switchTree = function(){
		var s_path = Com_GetUrlParameter(window.location.href,"s_path");
		if(s_path){
			s_path=encodeURI(s_path);
		}
		var url = Com_Parameter.ContextPath+"eop/basedata/resource/jsp/tree.jsp?s_path="+s_path+"&modelName="+listOption.modelName
		window.location.href = url;
	}
	//导出
	window.exportData = function(){
		var fdCompanyId = Com_GetUrlParameter(window.location.href,'fdCompanyId');
		fdCompanyId = fdCompanyId?fdCompanyId:'';
		window.open(Com_Parameter.ContextPath+"eop/basedata/eop_basedata_business/eopBasedataBusiness.do?method=exportData&modelName="+listOption.modelName+'&fdCompanyId='+fdCompanyId);
	}
	//导入
	window.importData = function(){
		var formName = listOption.modelName.split(".");
		formName = formName[formName.length-1];
		formName = formName.substring(0,1).toLowerCase()+formName.substring(1);
		window.dia = dialog.iframe(
			'/eop/basedata/resource/jsp/eopBasedataImport.jsp?modelName='+listOption.modelName+"&tempate="+formName,
			lang['message.import.title'],
			function(data){
				if(data){
					topic.publish("list.refresh")
				}
			},{height:600,width:800}
		);
	}
	//下载模板
	window.downloadTemplate = function(){
		window.open(Com_Parameter.ContextPath+'eop/basedata/eop_basedata_business/eopBasedataBusiness.do?method=downloadTemplate&modelName='+listOption.modelName);
	}
	
	//拉取币种/物料单位数据
	window.syncData = function() {
		window.open(Com_Parameter.ContextPath + 'eop/basedata/eop_basedata_pull_data/eopBasedataPullData.do?method=pullData&f='+listOption.modelName, '_self');
	};
});
LUI.ready(function() {
    var option = window.listOption;
    if(option.canDelete==='' && LUI('btnDelete')){
        LUI('btnDelete').setVisible(false);
    }
    //设置默认的公司，由于组件是异步加载方式，需要做延时处理
    setTimeout("setDefaultCompanyCreteria()",100);
	
	checkSyncExtendPoint(option.modelName);
	
});

//检查是否存在向业务模块同步数据的扩展点
function checkSyncExtendPoint(modelName){
	
	if(modelName == 'com.landray.kmss.eop.basedata.model.EopBasedataCurrency' || modelName == 'com.landray.kmss.eop.basedata.model.EopBasedataMateUnit'){
		
		$.ajax({
			url: Com_Parameter.ContextPath +'eop/basedata/eop_basedata_business/eopBasedataBusiness.do?method=checkSyncExtendPoint',
			data:{'func' : modelName},
			type:'POST',
			async:false,
			success:function(rtn){
				if(rtn.success == false){
					$('#pullBtn').attr("style","display:none;");
				}else{
					$('#pullBtn').attr("style","display:block;");
				}
			},
			error:function(resp){
				console.log(resp);
			}
		});
	}
}

function setDefaultCompanyCreteria(){
	var fdCompany = LUI('criteria1').findSelectedValuesByKey("fdCompany.fdId");
	if(fdCompany&&fdCompany.values&&fdCompany.values.length>0){
		return;
	}
	var fdCompanyId = Com_GetUrlParameter(window.location.href,"companyId");
	if(fdCompany&&fdCompanyId){
		LUI('criteria1').setValue("fdCompany.fdId",fdCompanyId);
	}
	setTimeout("setDefaultCompanyCreteria()",100);
}
