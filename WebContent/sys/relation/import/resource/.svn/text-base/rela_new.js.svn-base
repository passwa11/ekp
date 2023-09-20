/*压缩类型：标准*/
//关联配置按钮对应动作
function RelationOpt(modelName,modelId,key,params){
	this.modelName = modelName;
	this.modelId = modelId;
	this.key = key;
	this.varName = "_relationCfg";
	this.params = params;
	var self = this;
	window[self.varName] = self;
	if(self.params["varName"]!=null && self.params["varName"]!=''){
		window[self.params["varName"]] = self;
	}
	/*********************外部调用函数****************************/
	//关联机制加载
	this.onload = function(){
		$("#rela_config_btn").click(function(){
			self.editConfig();
		});
		Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = self._rela_bulidFormInfo;
		
	};
	//增加关联配置按钮
	this.addButton = function(){
		
	};
	//关联配置
	//添加关联机制关闭回调函数
	this.editConfig = function(__func){
		self._rela_addCfg(__func);
	};
	//保存关联配置
	this.saveConfig = function(cfgNar){
		self._rela_saveCfg(cfgNar);
	};
	//关闭关联配置
	this.closeConfig = function(){
		self.dialogObj.hide();
	};
	
	this.refreshConfig = function(){
		self._rela_saveCfg();
	};
	/***********************内部调用函数*************************************/
	//构造关联机制表单存储字段
	this._rela_bulidFormInfo = function(){
		
		var formObj = $(document.forms[self.params['rela.mainform.name']]); 
		$("#none_div input[name='fdId']").appendTo(formObj);
		$("#none_div input[name='fdKey']").appendTo(formObj);
		$("#none_div input[name='fdModelName']").appendTo(formObj);
		$("#none_div input[name='fdModelId']").appendTo(formObj);
		$("#none_div input[name='fdParameter']").appendTo(formObj);
		
		if(window.relationEntrys!=null){
			var i = 0;
			var entryPrefix = 'sysRelationEntryFormList';
			for(var tmpKey in window.relationEntrys){
				var entry =  window.relationEntrys[tmpKey];
				for(var tmpField in entry){
					if(tmpField == 'relationConditions'){
						var count = 0;
						var conditions = entry.relationConditions;
						for(var condition in conditions){
							var conditionPrefix = entryPrefix +"[" + i + "].sysRelationConditionFormList";
							for(var condProp in conditions[condition]){
								var condFileName = conditionPrefix+ "[" + count + "]." + condProp;
								var condFiledObj = $("input[name='"+ condFileName +"']");
								if(condFiledObj.length<=0){
									condFiledObj = $("<input type='hidden' name='" + condFileName+ "'/>");
								}
								condFiledObj.val(conditions[condition][condProp]);
								condFiledObj.appendTo(formObj);
							}
							count++;
						}
					}
					else if(tmpField == 'relationTexts'){
						var textCount=0;
						var textInfos = entry.relationTexts;
						
						for(var textInfo in textInfos){
							var textPrefix = entryPrefix +"[" + i + "].sysRelationTextFormList";
							var condFileName = textPrefix+ "[" + textCount + "]." + textInfo;
							var condFiledObj = $("input[name='"+ condFileName +"']");
							if(condFiledObj.length<=0){
								condFiledObj = $("<input type='hidden' name='" + condFileName+ "'/>");
							}
							condFiledObj.val(textInfos[textInfo]);
							condFiledObj.appendTo(formObj);
							textCount++
						}
						
						
					}else if(tmpField == 'staticInfos'){
						var cnt = 0;
						var staticInfos = entry.staticInfos;
						for(var staticInfo in staticInfos){
							var staticPrefix = entryPrefix +"[" + i + "].sysRelationStaticNewFormList";
							for(var index in staticInfos[staticInfo]){
								for(var condProp in staticInfos[staticInfo][index]){
									var condFileName = staticPrefix+ "[" + cnt + "]." + condProp;
									var condFiledObj = $("input[name='"+ condFileName +"']");
									if(condFiledObj.length<=0){
										condFiledObj = $("<input type='hidden' name='" + condFileName+ "'/>");
									}
									condFiledObj.val(staticInfos[staticInfo][index][condProp]);
									condFiledObj.appendTo(formObj);
								}
								cnt++;
							}
						}
						
					}else if(tmpField == 'mainDatas') {
						var cnt = 0;
						var mainDatas = entry.mainDatas;
						for(var mainData in mainDatas){
							var dataPrefix = entryPrefix +"[" + i + "].sysRelationMainDataFormList";
							for(var index in mainDatas[mainData]){
								for(var condProp in mainDatas[mainData][index]){
									var condFileName = dataPrefix+ "[" + cnt + "]." + condProp;
									var condFiledObj = $("input[name='"+ condFileName +"']");
									if(condFiledObj.length<=0){
										condFiledObj = $("<input type='hidden' name='" + condFileName+ "'/>");
									}
									var dataVal = mainDatas[mainData][index][condProp];
									condFiledObj.val(dataVal);
									condFiledObj.appendTo(formObj);
								}
								cnt++;
							}
						}
						
					}else if(tmpField == 'relationPersons'){
						var persons = entry.relationPersons;
						for(var personProp in persons){
							var personPrefix = entryPrefix +"[" + i + "]";
							var personFileName = personPrefix+ "." + personProp ;
							var personFiledObj = $("input[name='"+ personFileName +"']");
							if(personFiledObj.length<=0){
								personFiledObj = $("<input type='hidden' name='" + personFileName+ "'/>");
							}
							personFiledObj.val(persons[personProp]);
							personFiledObj.appendTo(formObj);
						}
					} else{
						var filedName = entryPrefix + "[" + i + "]." + tmpField;
						var filedObj = $("input[name='"+filedName+"']");
						if(filedObj.length<=0){
							filedObj = $("<input type='hidden' name='" + filedName + "'/>");
						}
						filedObj.val(entry[tmpField]);
						filedObj.appendTo(formObj);
					}
				}
				i++;
			}
		}
		return true;
	};
	//打开关联配置
	this._rela_addCfg = function(__func){
		var fmid = "";
		if(window.sysRelationMainForm_param)
			fmid = window.sysRelationMainForm_param.fdModelId;
		seajs.use(['sys/ui/js/dialog'], function(dialog) {
			self.dialogObj = dialog.build({
				config:{
					width: 1080,
					height: 700,
					lock: true,
					cache: false,
					title : rela_params['rela.setting.title'],
					content : {
						id : 'relation_div',
						scroll : false,
						type : "iframe",
						url : '/sys/relation/import/sysRelationMain_setting.jsp?LUIElementId=relation_div&currModelName='+self.modelName+'&currModelId='+fmid+'&key='+self.key
					}
				},
				callback:__func||new Function()
			}).show();			
		});
	};
	this._rela_saveCfg = function(cfgVar){
		
		seajs.use(['lui/topic','lui/view/render','sys/relation/import/resource/relaSource','lui/base'],
				function(topic , render , relaSource , base){
			//先删除现有配置
			if(cfgVar!=null && cfgVar!={}){
				for(var tmpKey in window.relationEntrys){
					var contentId = 'rela_' + tmpKey;
					topic.group("relation").publish('removeContent',
						{"data":{'target':{"id":contentId}}});
				}
				//获取修改后的配置
				window.relationEntrys = cfgVar;
				
				// 用于一页面多关联--暂时知识地图使用
				if(self.key && window.relationMains){
					window.relationMains[self.key] = {};
					window.relationMains[self.key].fdKey = self.key;
					window.relationMains[self.key].fdModelName = self.modelName;
					window.relationMains[self.key].fdModelId = self.modelId;
					window.relationMains[self.key].relationEntrys = window.relationEntrys;
				}
					
			}
			if(!self.key){
				//构建content/dataview预览修改后的配置
				var i = 0;
				for(var tmpKey in window.relationEntrys){
					
					var relationEntry = window.relationEntrys[tmpKey];
					//构造预览参数
					var params = {};
					params['fdId'] = $("input[name='fdId']").val();
					params['fdKey'] = $("input[name='fdKey']").val();
					params['fdModelName'] = $("input[name='fdModelName']").val();
					params['fdModelId'] = $("input[name='fdModelId']").val();
					params['fdParameter'] = $("input[name='fdParameter']").val();
					params['loadIndex'] = i;
					var moduleModelName = null;
					for(var tmpField in relationEntry){
						params['entry['+i+'].'+tmpField] = relationEntry[tmpField];
						if(tmpField == "fdModuleModelName")
							moduleModelName = relationEntry[tmpField];;
					}
					if(relationEntry.relationConditions!=null){
						var count = 0;
						var conditions = relationEntry.relationConditions;
						for(var condition in conditions){
							for(var condProp in conditions[condition]){
								params["entry["+i+"].condition["+(count)+"]."+condProp] = conditions[condition][condProp];
							}
							count++;
						}
						params["entry["+i+"].count"] = count;
					}
					if(relationEntry.relationTexts!=null){
						
						var textCount = 0;
						var texts = relationEntry.relationTexts;
						for(var text in texts){
							
								params["entry["+i+"].text["+(textCount)+"]."+text] = texts[text];
							
								textCount++;
						}
						params["entry["+i+"].textCount"] = textCount
					}
					if(relationEntry.staticInfos!=null){
						var staticCount = 0;
						var staticInfos = relationEntry.staticInfos;
						for(var _staticInfo in staticInfos){
							for(var index in staticInfos[_staticInfo]){
								for(var infoProp in staticInfos[_staticInfo][index]){
									if(infoProp == "fdRelatedName" || infoProp == "fdRelatedUrl"
										|| infoProp == "fdRelatedId" || infoProp == "fdRelatedModelName"  || infoProp == "fdIsCreator" // 增加“文档关联”，文档关联使用静态页面的一些共用逻辑
										){
										params["entry["+i+"].static["+(staticCount)+"]."+infoProp] = staticInfos[_staticInfo][index][infoProp];
									}
								}
								staticCount++;
							}
						}
						params["entry["+i+"].staticCount"] = staticCount;
						params["entry["+i+"].staticInfos"] = null;
					}
					if(relationEntry.mainDatas!=null) {
						var mainDatas = relationEntry.mainDatas;
						var count = 0;
						for(var _mainData in mainDatas){
							for(var index in mainDatas[_mainData]){
								for(var infoProp in mainDatas[_mainData][index]){
										params["entry["+i+"].mainData["+(count)+"]."+infoProp] = mainDatas[_mainData][index][infoProp];
								}
								count++;
							}
						}
						params["entry["+i+"].mainDataCount"] = count;
						params["entry["+i+"].mainDatas"] = null;
					}
					//czk2019
                    if(relationEntry.relationPersons!=null){
						
						var personCount = 0;
						var persons = relationEntry.relationPersons;
						for(var person in persons){
								params["entry["+i+"].person["+(personCount)+"]."+person] = persons[person];
						}
						personCount++;
						params["entry["+i+"].personCount"] = personCount;
					}
					
					
					params["&moduleModelName"] = moduleModelName;
					//构造预览source  
					
					var sourceObj = new relaSource({
						"url":"/sys/relation/sys_relation_main/sysRelationMain.do?method=preview&forward=listUi",
						"params":params,
						"relationEntry":relationEntry
					});
					
					window["relaPage_"+relationEntry['fdId']] = 1;
					// 监听数据请求完成事件，构建分页按钮工具栏
					sourceObj.on('relaDataLoad', function(result){
						var entry = result.relationEntry;
						var data = result.data;
                        if($.isEmptyObject(data)==false){
                        	var totalPage = data[0]["totalPage"]||1; // 总页数
                            self.buildPageBtnToolbar(totalPage,entry);
                        }
					}, self);
					
					//构造预览render
					var renderSrc = "/sys/ui/extend/dataview/render/classic.js";
					if(params["entry["+i+"].fdType"] == "7") {
						renderSrc = "/sys/relation/import/resource/maindata_render.js";
					}
					var renderObj = new render.Javascript({
						"src":renderSrc,
						"vars":{"showCreator":"true","showCreated":"true","ellipsis":"false"},
						"param":{"extend":"tile"}
						});
					
					//构造dataview
					var dataView = new base.DataView({id:"relaView_"+relationEntry['fdId']});
					dataView.addChild(renderObj);
					dataView.addChild(sourceObj);
					renderObj.setParent(dataView);
					renderObj.startup();
					dataView.startup();
					
					//发布事件，新增content展示
					topic.group("relation").publish('addContent',{"data":{
								"id":"rela_"+relationEntry['fdId'],
								"title":relationEntry['fdModuleName'],
								"child":[dataView],
								"isShow": true
							}});
					i++; 
					dataView.draw();

				}
			}
			if(window.Sidebar_Refresh){
				Sidebar_Refresh();
			}
			if(self.dialogObj!=null)
				self.dialogObj.hide();
		});
		
		if(cfgVar!=null && cfgVar!={}){
			 setTimeout(function(){ 
		var obj=	parent.document.getElementById("relaChange");
		obj.click();
			 },100);
		}
		
		
//		seajs.use(['lui/jquery'],function($) {
//			$.ajax(
//					{
//					    type: 'post',
//					    data: {cfgVar: cfgVar},
//						url: Com_Parameter.ContextPath+"sys/relation/sys_relation_main/sysRelationMain.do?method=changeRela",
//						success: function(data) {
//						}
//					}
//				);
//		});
		
	};
	
	/**
	* 构建分页按钮栏
	* @param totalPage      总页数
	* @param relationEntry  关联信息对象
	*/		
	this.buildPageBtnToolbar = function(totalPage,relationEntry){
        var pageBtnToolBar = $("#pageBtn_"+relationEntry['fdId']); 
        if(pageBtnToolBar.length>0){  // 1、分页工具栏已经存在的情况下，重置“上一页”、“下一页”的状态
        	
        	if(totalPage<=1){
        		pageBtnToolBar.remove();
        		return;
        	}
			var relaId = relationEntry['fdId'];
			if(typeof(window["relaPage_"+relaId]) == "undefined" ){
				window["relaPage_"+relaId] = 1;
			}
			var curPageno = window["relaPage_"+relaId];
			if(curPageno && totalPage){
				var preBtn = $("#pageBtn_" + relaId +" #pagePre");
				var nextBtn = $("#pageBtn_" + relaId +" #pageNext");
				
				if(totalPage>1){
					if(curPageno >= totalPage){
						nextBtn.addClass("unable");
					}else{
						nextBtn.removeClass("unable");
					}
					if(curPageno <= 1){
						preBtn.addClass("unable");
					}else{
						preBtn.removeClass("unable");
					}
				}
			}
			
        }else{  // 2、分页工具栏不存在的情况下，创建“上一页”、“下一页”的DOM元素，并绑定点击事件
        	
        	if(totalPage<=1){
        		return;
        	}
    	    var pageBtnHtml = "<div id='pageBtn_"+relationEntry['fdId']+"' lui-main-uid='" + relationEntry['fdId'] + "' class='relaPageBtn'>";
            pageBtnHtml+= "<a id='pagePre' class='arrowL unable' lui-page-type='0'></a>";
            pageBtnHtml+= "<a id='pageNext' class='arrowR' lui-page-type='1'></a>";
            pageBtnHtml+= "</div>";
    		$("#rela_"+relationEntry['fdId']).append(pageBtnHtml);
    		$("#pageBtn_"+relationEntry['fdId']).delegate('a','click',function(event){
				var evt = event.currentTarget;
				var type = $(evt).attr("lui-page-type");
				var entryId = $(evt).parent().attr("lui-main-uid");
				var isable = $(evt).hasClass("unable");
				if(!isable){
					seajs.use(['lui/dialog'],function(dialog){
						$("#relaView_"+entryId).empty();
						var listViewUrl = LUI("relaView_"+entryId).source.url;
						if(typeof(window["relaPage_"+entryId]) == "undefined" ){
							window["relaPage_"+entryId] = 1;
						}
						if(type == "0"){
							window["relaPage_"+entryId] = window["relaPage_"+entryId] - 1;
						}else{
							window["relaPage_"+entryId] = window["relaPage_"+entryId] + 1;
						}
						var pagenoIndex = listViewUrl.indexOf("&pageno=");
						if(pagenoIndex > 0){
							listViewUrl = listViewUrl.substr(0,pagenoIndex);
						}
						LUI("relaView_"+entryId).source.url = listViewUrl + "&pageno=" + window["relaPage_"+entryId];
						LUI("relaView_"+entryId).source.get();
					});
				}
    		});	  
    		
        }
	
	};
	
	//直接绑定，外层已经是在document.ready中调用
	self.onload();
}
