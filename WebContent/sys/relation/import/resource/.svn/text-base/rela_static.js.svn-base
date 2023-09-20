/*压缩类型：标准*/
Com_IncludeFile("rela.css", Com_Parameter.ContextPath + "sys/relation/import/resource/","css",true);
//静态链接配置
function RelationStaticSetting(params){
	this.relationType = "4";
	this.params = params;
	this.varName = "_relationCfg";
	this.rela_tmpId = params.tempId;
	this.validateSelector = params.validateSelector;
	var self = this; 
	window[self.varName] = self;
	if(self.params["varName"]!=null && self.params["varName"]!=''){
		window[self.params["varName"]] = self;
	}
	/*********************************对外调用函数************************************************/
	//加载
	this.onload = function(){
		//添加按钮事件
		$("#rela_config_add").click(function(){
			self.addConfig();
		});
		//搜索按钮事件
		$("#rela_config_search").click(function(){
			self._rela_static_search();
		});
		//保存按钮事件
		$("#rela_config_save").click(function(){
			self.saveConfig();
		});
		//取消按钮事件
		$("#rela_config_close").click(function(){
			self.closeConfig();
		});
		//加载配置
		self._rela_static_load();
		
		//iframe
		self.resizeFrame();
		
		//validator
		self._rela_static_validator();
	};
	//iframe高度
	this.resizeFrame = function(){
		try {
			if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
				window.frameElement.style.height = ($(document.body).height()+10)+ "px";
				if(parent[self.varName] != null)
					parent[self.varName].resizeFrame();
			}
		} catch(e) {
		}
	};
	//保存配置
	this.saveConfig = function(){
		self._rela_static_save();
	};
	//新增一项配置
	this.addConfig = function(cfg){
		self._rela_static_addRow(cfg);
	};
	//删除某项配置
	this.deleteConfig = function(){
		DocList_DeleteRow();
		self.resizeFrame();
	};
	//关闭配置
	this.closeConfig = function(){
		if(parent[self.varName]!=null){
			parent[self.varName].closeConfig();
		}
		self.resizeFrame();
	};
	
	//清除无内容的配置信息（用于搜索后添加数据）
	this.delNullInfoConfig=function(){
		var trList = $("#TABLE_DocList").find('tr[rela_dataRow="1"]');
		for(var i=0;i<trList.length;i++){
			var fdTrObj=$(trList[i]);
			var fdNameItem=fdTrObj.find('input[name="fdName"]').val();
			var fdLinkItem=fdTrObj.find('input[name="fdLink"]').val();
			if(fdNameItem==""&&fdLinkItem==""){
				DocList_DeleteRow(trList[i]);
			}
		}
	};
	
	/*********************************内部调用函数************************************************/
	//加载静态配置
	 this._rela_static_load = function(){
		if(parent[self.varName]!=null && parent[self.varName].CurrentSetting){
			var setting = parent[self.varName].CurrentSetting;
			if(setting.fdModuleName!=null){
				$("input[name='fdModuleName']").val(setting.fdModuleName);
			}
			if(setting.fdId!=null && setting.fdType==self.relationType){
				self.rela_tmpId = setting.fdId;
			}
			if(setting.staticInfos!=null && setting.staticInfos[setting.fdId]!=null){
				var staticInfoList = setting.staticInfos[setting.fdId];
				if(staticInfoList.length > 0){
					//设置静态关联标题
					$("input[name='staticSourceDocSubject']").val(staticInfoList[0].fdSourceDocSubject);
				}
				for(var i=0;i<staticInfoList.length;i++){
					// #99895去掉，87741去掉后原有问题也未复现 
                    // // #87741 静态网页这两个参数为空
                    // if(staticInfoList[i].fdRelatedId!="" && staticInfoList[i].fdRelatedModelName!=""){
                    //     // 如果是关联文档切换过来，跳过
                    //     continue;
                    // }
					var fdRelatedName = staticInfoList[i].fdRelatedName;
					var fdRelatedUrl = staticInfoList[i].fdRelatedUrl;
					self.addConfig({'fdName':fdRelatedName,"fdLink":fdRelatedUrl});
				}
			}else{
				self.addConfig();
			}
		}
	};
	//保存静态链接配置
	this._rela_static_save = function(){
		var setting = {};
		var staticInfos = {};
		var  _tmpId = self.rela_tmpId;
		setting['fdId'] = _tmpId;
		setting['fdModuleName'] = $("input[name='fdModuleName']").val();
		if(!self.S_Valudator.validate()){
			return ;
		}
		var urlInfo = new Array();
		var checked = true;
		var trList = $("#TABLE_DocList").find('tr[rela_dataRow="1"]');
		var fdModelName = parent.parent.$("input[name='fdModelName']").val();
		var fdModelId = parent.parent.$("input[name='fdModelId']").val();
		var docSubject = parent.parent.$("input[name='docSubject']").val();
		if(typeof(docSubject) == "undefined"  || $.trim(docSubject) == ""){
			docSubject = $("input[name='staticSourceDocSubject']").val();
		}
		for(var index=0;index<trList.length;index++){
			var trObj = $(trList.get(index));
			var nameObj = trObj.find("input[name='fdName']");
			var name = nameObj.val();
			var urlObj = trObj.find("input[name='fdLink']");
			var url = urlObj.val();
			if(name!="" && url!=""){
				urlInfo.push(name+"|"+url);
			}else{
				seajs.use(['sys/ui/js/dialog'], function(dialog) {
					dialog.failure(self.params['format.error']);
				});
				return;
			}
			var staticInfo = {fdId:"",fdSourceId:"",fdSourceModelName:"",
								fdSourceDocSubject:"",
								fdRelatedId:"",fdRelatedModelName:"",
								fdRelatedUrl:"",fdRelatedName:"",
								fdRelatedType:"",fdEntryId:"",fdIndex:""};
			staticInfo["fdSourceId"] = fdModelId;
			staticInfo["fdSourceModelName"] = fdModelName;
			staticInfo["fdSourceDocSubject"] = docSubject;
			staticInfo["fdIndex"] = index;
			if(url!=""){
				staticInfo["fdRelatedUrl"] = url;
			}
			if(name!=""){
				staticInfo["fdRelatedName"] = name;
			}
			staticInfos[index] = staticInfo;
		}
		//setting['fdOtherUrl'] = urlInfo.join("<br>");
		setting['fdType'] = self.relationType;
		setting['staticInfos'] = {};
		setting['staticInfos'][_tmpId] = staticInfos;
		if(parent[self.varName]!=null){
			parent[self.varName].saveConfig(setting);
		}
		self.resizeFrame();
	};
	
	this._rela_static_validator = function(){
		if(self.S_Valudator == null) {
			var obj = null;
			if(self.validateSelector) {
				obj = $(self.validateSelector)[0];
			}
			self.S_Valudator = $GetKMSSDefaultValidation(obj,{afterElementValidate:function(){
				self.resizeFrame();
				return true;
			}});
			
			//URL可以用相对路径表示，添加校验器
			self.S_Valudator.addValidator("rela_url", 
					typeof($KMSSValidation_Lang) == 'undefined' ? "请输入有效的url。" : $KMSSValidation_Lang["url"],
					function(v) {   
						return this.getValidator('isEmpty').test(v) || /^(((http|https|ftp):\/\/)|(\/))(([A-Z0-9][A-Z0-9_-]*)(\.[A-Z0-9][A-Z0-9_-]*)*)(:(\d+))?\/?/i.test(v);
					});
		 }
		 $("input[name='fdModuleName']").each(function(){ 
			 self.S_Valudator.addElements(this);
		 });
	};
	
	//增加一行静态配置
	this._rela_static_addRow = function(data){
		var newRow = $(DocList_AddRow('TABLE_DocList'));
		newRow.attr("rela_dataRow","1");
		if(data!=null){
			newRow.find("input[name='fdName']").val(data['fdName']);
			newRow.find("input[name='fdLink']").val(data['fdLink']);
		}else{
			self.resizeFrame();
		}
	};
	//搜索链接配置
	this._rela_static_search = function(){
		var self = this;
		seajs.use(['sys/ui/js/dialog'], function(dialog) {
			var url = "/sys/relation/import/static/sysRelationMain_search.jsp?currModelName=" 
					+ Com_GetUrlParameter(location.href,'currModelName');
			dialog.iframe(url,self.params['search.title'], null,{width:600 , height:350 ,buttons:[{
					name : self.params['button.ok'],
					value : true,
					focus : true,
					fn : function(value, dialog) {
						var iframe = dialog.element.find("iframe").get(0);
						var cfgData = iframe.contentWindow.saveSelect();
						if(cfgData!=null){
							self.delNullInfoConfig();
							for(var tmpKey in cfgData){
								if(cfgData[tmpKey]!=null){
									self.addConfig(cfgData[tmpKey]);
								}
							}
							self.resizeFrame();
							dialog.hide(value);
						}
					}
				},{
					name : self.params['button.cancel'],
					value : false,
					styleClass : 'lui_toolbar_btn_gray',
					fn : function(value, dialog) {
						dialog.hide(value);
					}
				}  
			]});
		});
	};
	
	Com_AddEventListener(window, "load", function(){
		var onloadFun = function(){
			if(window.DocList_TableInfo['TABLE_DocList']!=null){
				self.onload();
				window.clearInterval(window.onloadSaticCalc);
			}
		}; 
		window.onloadSaticCalc = setInterval(onloadFun, 200);
	});
}

