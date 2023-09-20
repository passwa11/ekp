/*压缩类型：标准*/
Com_IncludeFile("rela.css", Com_Parameter.ContextPath + "sys/relation/import/resource/","css",true);
//静态链接配置
function RelationDocSetting(params){
	this.relationType = "5";
	this.params = params;
	this.config = getConfig();
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
		//保存按钮事件
		$("#rela_config_save").click(function(){
			self.saveConfig();
		});
		//取消按钮事件
		$("#rela_config_close").click(function(){
			self.closeConfig();
		});
		
		//加载配置
		self._rela_doc_load();
		
		//iframe
		self.resizeFrame();
		
		//validator
		self._rela_static_validator();
	};
	//iframe高度
	this.resizeFrame = function(){
		try {
			if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
				var iframe = document.getElementById("dataShowList")
				var d_self = iframe;
				setTimeout(function() {
					var height = $(d_self).height();
					var div = iframe.contentDocument.getElementById("listtable_box");
					if (div){
						if(div.offsetHeight > height){
							iframe.height = div.offsetHeight + "px";
							height = div.offsetHeight + 230 ;
						}else{
							height = height + 230;
						}			
					}
					window.frameElement.style.height = height +"px";
					if(parent[self.varName] != null)
						parent[self.varName].resizeFrame();
				}, 100);
			}
		} catch(e) {
		}
	};
	//保存配置
	this.saveConfig = function(){
		self._rela_static_save();
	};
	//关闭配置
	this.closeConfig = function(){
		if(parent[self.varName]!=null){
			parent[self.varName].closeConfig();
		}
		self.resizeFrame();
	};
	
	/*********************************内部调用函数************************************************/
	//加载静态配置
	 this._rela_doc_load = function(){
		 if(parent[self.varName]!=null && parent[self.varName].CurrentSetting){
				var setting = parent[self.varName].CurrentSetting;
				if(setting.fdModuleName!=null){
					$("input[name='fdModuleName']").val(setting.fdModuleName);
				}
				if(setting.fdId!=null && setting.fdType==self.relationType){
					self.rela_tmpId = setting.fdId;
				}
				var staticInfos = setting.staticInfos;
				for(var key in staticInfos) {
					for(var i in staticInfos[key]) {
						var _d = staticInfos[key][i];
						// #87741 静态网页这两个参数为空，防止从静态网页切换到关联文档，原有关联未清除
						if(_d.fdRelatedId!="" && _d.fdRelatedModelName!=""){
                            relevance_dialog_selectedData.push({docId:_d.fdRelatedId, fdModelName:_d.fdRelatedModelName, subject:_d.fdRelatedName, isCreator:_d.fdIsCreator});
                        }
					}
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
		var checked = true;
		var fdModelName = parent.parent.$("input[name='fdModelName']").val();
		var fdModelId = parent.parent.$("input[name='fdModelId']").val();
		var docSubject = parent.parent.$("input[name='docSubject']").val();
		if(typeof(docSubject) == "undefined"  || $.trim(docSubject) == ""){
			docSubject = $("input[name='staticSourceDocSubject']").val();
		}
		var __datas = getSelectedData();
		for(var i=0; i<__datas.length; i++) {
			var data = __datas[i];
			var staticInfo = {fdId:"",fdSourceId:"",fdSourceModelName:"",fdSourceDocSubject:"",
					fdRelatedId:"",fdRelatedModelName:"",fdRelatedUrl:"",fdRelatedName:"",
					fdRelatedType:"",fdEntryId:"",fdIndex:"",fdIsCreator:""};
			staticInfo["fdSourceId"] = fdModelId;
			staticInfo["fdSourceModelName"] = fdModelName;
			staticInfo["fdSourceDocSubject"] = docSubject;
			staticInfo["fdIndex"] = i;
			
			staticInfo["fdRelatedId"] = data.docId;
			staticInfo["fdRelatedModelName"] = data.fdModelName;
			staticInfo["fdRelatedName"] = data.subject;
			staticInfo["fdIsCreator"] = data.isCreator;
			
			staticInfos[i] = staticInfo;
		}
		setting['fdType'] = self.relationType;
		setting['fdIsTemplate'] = "false";
		setting['fdDiffusionAuth'] = self.config.fdDiffusionAuth;
		setting['staticInfos'] = {};
		setting['staticInfos'][_tmpId] = staticInfos;
		if(parent[self.varName]!=null){
			parent[self.varName].saveConfig(setting);
		}
		self.resizeFrame();
	};
	// 校验
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
		 }
		 $("input[name='fdModuleName']").each(function(){ 
			 self.S_Valudator.addElements(this);
		 });
	};
	
	Com_AddEventListener(window, "load", function(){
		self.onload();
	});
}


/***************************************以下为XXXjs********************************************/

// 获取模板配置
function getConfig() {
	var top = Com_Parameter.top || window.top;
	var relationEntrys = top.relationEntrys;
	if (relationEntrys) {
		for ( var key in relationEntrys) {
			if (relationEntrys[key].fdType == '5' && relationEntrys[key].fdIsTemplate == 'true')
				return relationEntrys[key];
		}
	}
	return {};
}

// 存储当前关联控件的已选文档信息全局变量
var relevance_dialog_selectedData = [];

// 记录当前fdKey和id，主要用于搜索
var relevance_dialog_fdKey;
var relevacen_dialog_fdTemplateId;

// 选择框的iframe
var selectedDataShowIframe;

function setSelectedData(selected) {
	if (selected) {
		relevance_dialog_selectedData = selected;
	}
}

function getSelectedData() {
	return relevance_dialog_selectedData;
}

// 当前路径 更新路径
function updatePath(modelPath) {
	$("#modelPath").text(modelPath);
}

// 数据展示区 刷新list列表
function updateList(fdTemplateId, modelPath, fdKey, mainModelName, isBase,fdHierarchyId) {
	mainModelName = (typeof mainModelName == 'undefined' ? "" : mainModelName);
	// 含有子model的，不需要查询 [sysRelationMain_setting_doc页面选项无子模型-需要查询模块数据 #169078]
	if(isBase && isBase == "true" && mainModelName != "sysRelationMain_setting_doc"){
		return;
	}
	var modelRange = getConfig().fdSearchScope ? getConfig().fdSearchScope : "";
	if(fdKey != "" && modelRange.trim().length > 0){
		var flag = modelRangeFilter(modelRange,fdTemplateId,fdHierarchyId,fdKey);
		if(!flag){
			return;
		}
	}
	var docStatus = getConfig().docStatus ? getConfig().docStatus : "";
	relevance_dialog_fdKey = fdKey;
	relevacen_dialog_fdTemplateId = fdTemplateId;
	relevanceLoadingShow();
	modelRange = encodeURIComponent(modelRange);
	docStatus = encodeURIComponent(docStatus);
	//#106914项目协作模块没有docStatus
	if(fdKey == 'kmCoprojectMain'){
		docStatus = "";
	} else if(fdKey == 'kmInstitution' || modelRange.indexOf('kmInstitution') > -1){
		//规范制度查询过期文档，加上50-失效文档类型
		if(docStatus.indexOf('40') > -1){
			docStatus += "50";
		}
	}
	var url = Com_Parameter.ContextPath + 'sys/relation/relationDoc.do?method=updateList&fdKey=' + fdKey + '&fdTemplateId=' + fdTemplateId + '&modelPath='
			+ encodeURI(modelPath) + '&orderby=fdId&ordertype=down&modelRange=' + modelRange + '&mainModelName=' + mainModelName + '&docStatus=' + docStatus ;
	var iframe = document.getElementById('dataShowList');
	iframe.setAttribute('src', url);
	Com_AddEventListener(iframe, 'load', relevanceLoadinghide);
}

function modelRangeFilter(modelRange,fdTemplateId,fdHierarchyId,fdKey){
	var hierarchyIds;
	if(fdHierarchyId != null && typeof(fdHierarchyId) != "undefined"){
		hierarchyIds = fdHierarchyId.split("x");
	}
	if(fdKey.trim().length > 0 ){
		var base = fdKey + "|" + fdKey;	
		var baseIndex = modelRange.indexOf(base);
		if(baseIndex < 0){
			var index = fdKey.indexOf("-");
			if( index > -1){
				var baseKey = fdKey.substring(0,index);
				baseKey = baseKey + "|" + baseKey;	
				var baseKeyIndex = modelRange.indexOf(baseKey);
				if(baseKeyIndex < 0){
					if(fdTemplateId.trim().length > 0){
						var key = fdKey + "|" + fdTemplateId;
						var keyIndex =  modelRange.indexOf(key);
						var flag = false;
						if(keyIndex < 0){
							for(var i = 1;i < hierarchyIds.length - 1 ; i++){
								var temp = fdKey + "|" + hierarchyIds[i];
								if(modelRange.indexOf(temp) != -1){
									flag = true;
								}
							}
							if(!flag){
									return false;
							}else {
									return true;
							}
						}
						return true;
					}else{
						return false;
					}
				}
				return true;
			}else{
				if(fdTemplateId.trim().length > 0){
					var key = fdKey + "|" + fdTemplateId;
					var keyIndex =  modelRange.indexOf(key);
					var flag = false;
					if(keyIndex < 0){
						for(var i = 1;i < hierarchyIds.length - 1 ; i++){
							var temp = fdKey + "|" + hierarchyIds[i];
							if(modelRange.indexOf(temp) != -1){
								flag = true;
							}
						}
						if(!flag){
							return false;
						}else{
							return true;
						}
					}
					return true;
				}else{
					return false;
				}
			}
		}
		return true;
	}
	return true;
}

// 数据展示区 加载列表图标
function relevanceLoadingShow() {
	var loadingDom = document.getElementById('relevance_dialog_loading');
	if (loadingDom) {
		loadingDom.style.display = '';
	}
}

// 数据展示区 隐藏图标
function relevanceLoadinghide() {
	var loadingDom = document.getElementById('relevance_dialog_loading');
	if (loadingDom) {
		loadingDom.style.display = 'none';
	}
}

// 搜索框按enter即可触发搜索
function enterTrigleSelect(event, self) {
	if (self.value != '') {
		$('.relevance_dislog_moduleSelect_delWord').css('display', 'inline');
	} else {
		$('.relevance_dislog_moduleSelect_delWord').hide();
	}
	if (event && event.keyCode == '13') {
		relevance_dialog_moduleSelect();
	}
}

// 清除搜索框里面的内容
function relevance_dialog_delWord() {
	$('.relevance_dislog_moduleSelect_input').val('');
	$('.relevance_dislog_moduleSelect_delWord').hide();
}

function relevace_dialog_setFdKey(fdKey) {
	relevance_dialog_fdKey = fdKey;
}

var relevance_validtion = $KMSSValidation();
// 模块搜索 模块搜索
function relevance_dialog_moduleSelect() {
	// 对字符串编码
	var selectField = encodeURIComponent($('.relevance_dislog_moduleSelect_input').val());
	var docStatus = getConfig().docStatus ? getConfig().docStatus : ""; 
	// 不支持全模块搜索
	if (relevance_dialog_fdKey == null || relevance_dialog_fdKey == '') {
		alert('不支持全模块搜索...请先选择分类！');// 请先选择分类！
		return false;
	}
	var selectType = $(".relevance_search_select").val();
	var url = Com_Parameter.ContextPath + 'sys/relation/relationDoc.do?method=selectModuleWithField&fdKey=' + relevance_dialog_fdKey + '&fdTemplateId='
			+ relevacen_dialog_fdTemplateId + '&selectType='+ selectType + '&rowsize=9&orderby=fdId&ordertype=down' + '&docStatus=' + docStatus;
	if(selectType=="createTime"){
		var startTime = $("input[name='startTime']");
		var endTime = $("input[name='endTime']");
		//调用校验框架校验是否为时间类型
		var bool = relevance_validtion.getValidator(startTime.attr("validate")).test(startTime.val()) && relevance_validtion.getValidator(endTime.attr("validate")).test(endTime.val());
		if(bool){
			url += '&startTime='+startTime.val()+'&endTime='+endTime.val();
		}else{
			return;
		}
	}else{
		url += '&selectField='+selectField;
	}
	relevanceLoadingShow();
	var iframe = document.getElementById('dataShowList');
	iframe.setAttribute('src', url);
	Com_AddEventListener(iframe, 'load', relevanceLoadinghide);
}

// 数据展示区 初始化
function relevance_dialog_init() {
	var modelRange = getConfig().fdSearchScope ? getConfig().fdSearchScope : "";
	modelRange = encodeURIComponent(modelRange);
	var url = "/sys/relation/relationDoc.do?method=portlet&parentId=!{value}&fdKey=!{fdKey}&modelRange=" + modelRange + "&modelPath=!{modelPath}";
	LUI("__categoryNavigationMenu").source.setUrl(url);
	updateList('', '', '', mainModelName);
}

LUI.ready(relevance_dialog_init);


