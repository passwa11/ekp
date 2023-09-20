/*压缩类型：标准*/
//关联配置按钮对应动作
function RelationOpt(modelName,modelId,key,params){
	this.modelName = modelName;
	this.modelId = modelId;
	this.key = key;
	this.varName = "_relationCfg";  
	this.params = params;
	this.optType = "edit";
	var self = this; 
	window[self.varName] = self;
	if(self.params["varName"]!=null && self.params["varName"]!=''){
		window[self.params["varName"]] = self;
	}
	/*********************外部调用函数****************************/
	//关联机制加载,知识地图中的多关联不会调用此函数
	this.onload = function(){
		$("#rela_config_btn").click(function(){
			self.editConfig();
		});
		$("#rela_config_btn_view").click(function(){
			Com_EventStopPropagation();
			self.optType = "view"; // View页面中的编辑
			self.editConfig();
		});
		
		Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = self._rela_submit;
		
	};
	//增加关联配置按钮
	this.addButton = function(){
		
	};
	//关联配置
	//添加关联机制关闭回调函数 selectedTr:默认选中的行
	this.editConfig = function(__func,selectedTr){
		self._rela_addCfg(__func,selectedTr);
	};
	//保存关联配置
	this.saveConfig = function(cfgNar, cfgDes){
		if(window.isRightModel && cfgNar!=null && !$.isEmptyObject(cfgNar)){
			if(needTitle){
				var relContent = $("i.lui-fm-icon-1").closest(".lui_tabpanel_vertical_icon_navs_item_l");
				if(relContent.is(':hidden')){
					relContent.show();
				}
				relContent.click();
			}else{
				var relationMainContent = LUI("sysRelationMainContent");
				if(relationMainContent.nav.is(':hidden')){
					relationMainContent.nav.show();
					relationMainContent.element.closest(".lui_accordionpanel_content_frame").show();
					$(".lui_accordionpanel_frame:eq(0)").children(".lui_accordionpanel_content_frame:visible").find(".lui_accordionpanel_content_c:first").css("min-height","");
					var $farmes = $(".lui_accordionpanel_frame:eq(0)").children(".lui_accordionpanel_content_frame:visible:last").find(".lui_accordionpanel_content_c:first");
		        	if($farmes.length > 0) {
						var h = document.documentElement.clientHeight || document.body.clientHeight;
			        	var height = $farmes.outerHeight();
			        	var top = $farmes.offset().top;
			        	if(height+top<h){
			        		$farmes.css("min-height",h-top-30);
			        	}
		        	}
				}
			}
		}
		setTimeout(function(){
			self._rela_saveCfg(cfgNar, cfgDes, function() {
				// 如果是view完成，需要提交数据
				if(self.optType == "view") {
					var formObj = $("<form name='"+self.params['rela.mainform.name']+"_sysRelationMainForm'/>");
					
					$(document.body).append(formObj);
					// 构建数据表单
					self._rela_bulidFormInfo("", formObj);
					seajs.use(['sys/ui/js/dialog'], function(dialog) {
						window.chg_load = dialog.loading();
						$.ajax({
							url: Com_Parameter.ContextPath + "sys/relation/sys_relation_main/sysRelationMain.do?method=updateRelation",
							type: "post",
							data: formObj.serialize(),
							dataType: "json",
							success: function(data) {
								if(data.state) {
									dialog.success(data.msg);
								} else {
									dialog.failure(data.msg);
								}
							},
							error: function(a,b,c) {
								dialog.failure(c);
							},
							complete: function() {
								if(window.chg_load != null)
									window.chg_load.hide();
								formObj.remove();
							}
						});
					});
				}
			});
		},100);
	};
	//关闭关联配置
	this.closeConfig = function(){
		self.dialogObj.hide();
	};
	
	this.refreshConfig = function(){
		self._rela_saveCfg(window.relationEntrys);
	};
	this._rela_submit = function(formObj, method, clearParameter, moreOptions) {
		return self._rela_bulidFormInfo(undefined, formObj);
	};
	/***********************内部调用函数*************************************/
	//构造关联机制表单存储字段
	this._rela_bulidFormInfo = function(formName, formObj){
		formName = typeof(formName) == "undefined" ? "sysRelationMainForm" : formName;
		if(formName.length > 0)
			formName += ".";
		formObj = formObj || $(document.forms[self.params['rela.mainform.name']]); 
		if(window.sysRelationMainForm_param) {
			for(var i in window.sysRelationMainForm_param) {
				var __field_name = i;
				if(formName.length > 0)
					__field_name = formName + i;
				$("<input type='hidden' name='"+__field_name+"' value='"+window.sysRelationMainForm_param[i]+"' />").appendTo(formObj);
			}
		} else if(self.optType != "view") {
			$("input[name='"+formName+"fdId']").appendTo(formObj);
			$("input[name='"+formName+"fdKey']").appendTo(formObj);
			$("input[name='"+formName+"fdModelName']").appendTo(formObj);
			$("input[name='"+formName+"fdModelId']").appendTo(formObj);
			$("input[name='"+formName+"fdParameter']").appendTo(formObj);
			
			$("input[name='"+formName+"fdDesSubject']").appendTo(formObj);
			$("input[name='"+formName+"fdDesContent']").appendTo(formObj);
		}
		
		if(window.relationEntrys!=null){
			var i = 0;
			var entryPrefix = formName+'sysRelationEntryFormList';
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
					}else if(tmpField == 'relationTexts'){
						var texts = entry.relationTexts;
						for(var textProp in texts){
							var textPrefix = entryPrefix +"[" + i + "].sysRelationTextFormList";
							
								var textFileName = textPrefix+ "[0]." +textProp ;
								var textFiledObj = $("input[name='"+ textFileName +"']");
								if(textFiledObj.length<=0){
									textFiledObj = $("<input type='hidden' name='" + textFileName+ "'/>");
								}
								textFiledObj.val(texts[textProp]);
								textFiledObj.appendTo(formObj);
							
							
						}
						
					}
					else if(tmpField == 'staticInfos'){
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
									var staticVal = staticInfos[staticInfo][index][condProp];
									//源文档名为空时
									if("fdSourceDocSubject" == condProp && (staticVal==''||staticVal==null)){
										staticVal = $("input[name='docSubject']").val();
									}
									condFiledObj.val(staticVal);
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
							// for(var index in mainDatas[mainData]){
							var propKeys = Object.keys(mainDatas[mainData]);
							for(var ids=0; ids<propKeys.length; ids++){
								var prop = propKeys[ids];
								for(var condProp in mainDatas[mainData][prop]){
									var condFileName = dataPrefix+ "[" + cnt + "]." + condProp;
									var condFiledObj = $("input[name='"+ condFileName +"']");
									if(condFiledObj.length<=0){
										condFiledObj = $("<input type='hidden' name='" + condFileName+ "'/>");
									}
									var dataVal = mainDatas[mainData][prop][condProp];
									condFiledObj.val(dataVal);
									condFiledObj.appendTo(formObj);
								}
								cnt++;
							}
						}
						
					}else if(tmpField == 'relationPersons'){ //czk2019
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
					}else{
						var filedName = entryPrefix + "[" + i + "]." + tmpField;
						var filedObj = $("input[name='"+filedName+"']");
						if(filedObj.length<=0){
							filedObj = $("<input type='hidden' name='" + filedName + "'/>");
						}
						if("fdIndex" == tmpField){
							filedObj.val(i);
						}else{
							filedObj.val(entry[tmpField]);
						}
						filedObj.appendTo(formObj);
					}
				}
				i++;
			}
		}
		
		return true;
	};
	//打开关联配置
	this._rela_addCfg = function(__func,selectedTr){
		seajs.use(['sys/ui/js/dialog'], function(dialog) {
			var fmid = "";
			if(window.sysRelationMainForm_param)
				fmid = window.sysRelationMainForm_param.fdModelId;
			var url = '/sys/relation/import/sysRelationMain_setting.jsp?LUIElementId=relation_div&currModelName='+self.modelName+'&key='+self.key;
			if(fmid=="" && Com_GetUrlParameter(location.href,"method")=="add"){
				fmid = Com_GetUrlParameter(location.href,"fdTemplateId");
				url += '&currModelId='+fmid+'&pro=fdTemplateId';
			}else{
				url += '&currModelId='+fmid;
			}
			if(selectedTr){
				url += '&selectedTr='+selectedTr;
			}
			if(self.openAddUrl){
				url += '&openAddUrl=true';
				self.openAddUrl = false;
			}
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
						url : url
					}
				},
				callback:__func||new Function()
			}).show();
		});
	};
	this._rela_saveCfg = function(cfgVar, cfgDes, callback){
		seajs.use(['lui/jquery', 'lui/topic','lui/view/render','sys/relation/import/resource/relaSource','lui/base'],
			function($, topic , render , relaSource , base){
				//先删除现有配置
			if(cfgVar!=null && cfgVar!={}){
				for(var tmpKey in window.relationEntrys){
					var contentId = 'rela_' + tmpKey;
					var __content = document.getElementById(contentId);
					if(__content)
					topic.group("relation").publish('removeContent',
						{"data":{'target':{"id":contentId}}});
				}
				//发布关联信息改变事件
				topic.publish("relationChange",cfgVar);
				//获取修改后的配置
				window.relationEntrys = cfgVar;
				
				// 用于一页面多关联--暂时知识地图使用
				if(self.key && window.relationMains){
					window.relationMains[self.key] = {};
					window.relationMains[self.key].fdKey = self.key;
					window.relationMains[self.key].fdModelName = self.modelName;
					window.relationMains[self.key].fdModelId = self.modelId;
					if(cfgDes) {//关联配置说明的标题和内容
						window.relationMains[self.key].fdDesSubject = cfgDes.fdDesSubject
								|| '';
						window.relationMains[self.key].fdDesContent = cfgDes.fdDesContent
								|| '';
					}
					
					//更新顺序
					var relaEntry = window.relationEntrys;
					var entryIndex = 0;
					for(var tmp in relaEntry){
						relaEntry[tmp].fdIndex = entryIndex + "";
						entryIndex++;
					}
				
					window.relationMains[self.key].relationEntrys = relaEntry;
				}
					
			}
			
			if(!self.key){
				if(cfgDes) {
					$("input[name='sysRelationMainForm.fdDesSubject']").val(cfgDes.fdDesSubject);
					$("input[name='sysRelationMainForm.fdDesContent']").val(cfgDes.fdDesContent);
				}
				
				
				//构建content/dataview预览修改后的配置
				var i = 0;
				for(var tmpKey in window.relationEntrys){
					var relationEntry = window.relationEntrys[tmpKey];
					// 不显示后台配置的“文档关联”
					if("true" == relationEntry.fdIsTemplate) {
						continue;
					}
					//构造预览参数
					var params = {};
					params['fdId'] = $("input[name='sysRelationMainForm.fdId']").val();
					params['fdKey'] = $("input[name='sysRelationMainForm.fdKey']").val();
					params['fdModelName'] = $("input[name='sysRelationMainForm.fdModelName']").val();
					params['fdModelId'] = $("input[name='sysRelationMainForm.fdModelId']").val();
					params['fdParameter'] = $("input[name='sysRelationMainForm.fdParameter']").val();
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
										if(infoProp == "fdRelatedUrl"){
											var fdRelatedUrl = staticInfos[_staticInfo][index][infoProp];
											//判断URL地址的正则表达式为:http(s)?://([\w-]+\.)+[\w-]+(/[\w- ./?%&=]*)?
											//下面的代码中应用了转义字符"\"输出一个字符"/"
											var expression=/http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/;
											var urlExp=new RegExp(expression);
											if(fdRelatedUrl && urlExp.test(fdRelatedUrl)==false && fdRelatedUrl.indexOf("/")!=0){
												params["entry["+i+"].static["+(staticCount)+"]."+infoProp] = "http://"+fdRelatedUrl;
											}
										}
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
							// for(var index in mainDatas[_mainData]){
							var propKeys = Object.keys(mainDatas[_mainData]);
							for(var idx=0; idx<propKeys.length; idx++){
								var prop = propKeys[idx];
								for(var infoProp in mainDatas[_mainData][prop]){
										params["entry["+i+"].mainData["+(count)+"]."+infoProp] = mainDatas[_mainData][prop][infoProp];
								}
								count++;
							}
						}
						params["entry["+i+"].mainDataCount"] = count;
						params["entry["+i+"].mainDatas"] = null;
					}
					if(relationEntry.relationPersons!=null){ //czk2019
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
						//没有关联文档时会报错
                        if($.isEmptyObject(data)==false && data.length > 0){
                        	var totalPage = data[0]["totalPage"]||1; // 总页数
                            self.buildPageBtnToolbar(totalPage,entry);
                        }
					}, self);
					
					//构造预览render
					var renderSrc = "/sys/ui/extend/dataview/render/classic.js";
					if(params["entry["+i+"].fdType"] == "7") {
						renderSrc = "/sys/relation/import/resource/maindata_render.js";
					}
					if(params["entry["+i+"].fdType"] == "9") {
						renderSrc = "/sys/relation/import/resource/chart_render.js";
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
					
					var data = {
							"id":"rela_"+relationEntry['fdId'],
							"title":relationEntry['fdModuleName'],
							"child":[dataView],
							"isShow":true};
					if(window.isRightModel){
						data["isNeedBtn"] = true;
						data["btnText"] = Data_GetResourceString("button.edit");
						data["btnFun"] = self.openEditDiolog;
					}
					//发布事件，新增content展示
					topic.group("relation").publish('addContent',{"data":data});
					i++;
					dataView.draw();
				
				}
			}
			if(window.Sidebar_Refresh){
				window.setTimeout(function(){
					window.Sidebar_Refresh();
				},300);
			}
			if(self.dialogObj!=null)
				self.dialogObj.hide();
			
			if(typeof(callback) == "function") {
				callback();
			}
		});
		
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
	
	this.openEditDiolog = function(event){
		event = event || window.event;
		event.cancelBubble = true;
		if (event.stopPropagation) {event.stopPropagation();}
		if(event&&event.data&&event.data.id){
			self.optType = "view";
			self.editConfig(null,event.data.id.replace("rela_",""));
		}
	};
	
	var interval, intervalCount = 0;
	Com_AddEventListener(window, "load", function() {
		self.onload();
		if(window.Sidebar_Refresh){
			interval = setInterval(function() {
				window.Sidebar_Refresh();
				if(intervalCount >= 3) {
					clearInterval(interval);
				}
				intervalCount += 1;
			}, 500);
		}
	});
}
