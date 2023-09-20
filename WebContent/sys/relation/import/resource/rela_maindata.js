/*压缩类型：标准*/
Com_IncludeFile("rela.css", Com_Parameter.ContextPath + "sys/relation/import/resource/","css",true);
//静态链接配置
function RelationMainDataSetting(params) {
	this.relationType = "7";
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
		//保存按钮事件
		$("#rela_config_save").click(function(){
			self.saveConfig();
		});
		//取消按钮事件
		$("#rela_config_close").click(function(){
			self.closeConfig();
		});
		//加载配置
		self._rela_maindata_load();
		
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
	
	/*********************************内部调用函数************************************************/
	//加载静态配置
	 this._rela_maindata_load = function(){
		if(parent[self.varName]!=null && parent[self.varName].CurrentSetting){
			var setting = parent[self.varName].CurrentSetting;
			if(setting.fdModuleName!=null){
				$("input[name='fdModuleName']").val(setting.fdModuleName);
			}
			if(setting.fdId!=null && setting.fdType==self.relationType){
				self.rela_tmpId = setting.fdId;
			}
			if(setting.mainDatas!=null && setting.mainDatas[setting.fdId]!=null){
				var dataMsgList = setting.mainDatas[setting.fdId];
				for(var i=0;i<dataMsgList.length;i++){
					var fdName = dataMsgList[i].fdName;
					var fdTemplateId = dataMsgList[i].fdTemplateId;
					var fdTemplateModelName = dataMsgList[i].fdTemplateModelName;
					var fdTemplateSubject = dataMsgList[i].fdTemplateSubject;
					var fdMainDataId = dataMsgList[i].fdMainDataId;
					var fdMainDataModelName = dataMsgList[i].fdMainDataModelName;
					var fdMainDataName = dataMsgList[i].fdMainDataName;
					self.addConfig({'fdName':fdName,"fdTemplateId":fdTemplateId,"fdTemplateModelName":fdTemplateModelName
						,"fdTemplateSubject":fdTemplateSubject,"fdMainDataId":fdMainDataId,"fdMainDataModelName":fdMainDataModelName
						,"fdMainDataName":fdMainDataName});
				}
			}else{
				self.addConfig();
			}
		}
	};
	//保存静态链接配置
	this._rela_static_save = function(){
		var setting = {};
		var mainDatas = {};
		var  _tmpId = self.rela_tmpId;
		setting['fdId'] = _tmpId;
		setting['fdModuleName'] = $("input[name='fdModuleName']").val();
		if(!self.S_Valudator.validate()){
			return ;
		}
		var trList = $("#TABLE_DocList").find('tr[rela_dataRow="1"]');
		var fdModelName = parent.parent.$("input[name='fdModelName']").val();
		var fdModelId = parent.parent.$("input[name='fdModelId']").val();
		var docSubject = parent.parent.$("input[name='docSubject']").val();
		if(typeof(docSubject) == "undefined"  || $.trim(docSubject) == ""){
			docSubject = $("input[name='staticSourceDocSubject']").val();
		}
		for(var index=0; index<trList.length; index++){
			var trObj = $(trList.get(index));
			var fdName = trObj.find("input[name='fdName']").val();
			var fdTemplateId = trObj.find("input[name='fdTemplateId']").val();
			var fdTemplateModelName = trObj.find("input[name='fdTemplateModelName']").val();
			var fdTemplateSubject = trObj.find("input[name='fdTemplateSubject']").val();
			var fdMainDataId = trObj.find("input[name='fdMainDataId']").val();
			var fdMainDataModelName = trObj.find("input[name='fdMainDataModelName']").val();
			var fdMainDataName = trObj.find("input[name='fdMainDataName']").val();
			
			if(fdTemplateId == "" || fdMainDataId == "") {
				seajs.use(['lui/dialog'], function(dialog) {
					dialog.alert(self.params['maindata.error1'] + (index+1) + self.params['maindata.error2']);
				});
				return false;
			}
			
			var dataMsg = {fdId:"",
							fdName:"",
							fdTemplateId:"",
							fdTemplateModelName:"",
							fdTemplateSubject:"",
							fdMainDataId:"",
							fdMainDataModelName:"",
							fdMainDataName:"",
							fdIndex: index,
							fdSourceId: fdModelId,
							fdSourceModelName: fdModelName,
							fdSourceDocSubject: docSubject,
							fdEntryId:""
							};
			
			dataMsg["fdName"] = fdName;
			dataMsg["fdTemplateId"] = fdTemplateId;
			dataMsg["fdTemplateModelName"] = fdTemplateModelName;
			dataMsg["fdTemplateSubject"] = fdTemplateSubject;
			dataMsg["fdMainDataId"] = fdMainDataId;
			dataMsg["fdMainDataModelName"] = fdMainDataModelName;
			dataMsg["fdMainDataName"] = fdMainDataName;
			
			mainDatas[index] = dataMsg;
		}
		setting['fdType'] = self.relationType;
		setting['mainDatas'] = {};
		setting['mainDatas'][_tmpId] = mainDatas;
		if(parent[self.varName]!=null){
			parent[self.varName].saveConfig(setting);
		}
		self.resizeFrame();
	};
	
	this._rela_static_validator = function() {
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
	
	//增加一行静态配置
	this._rela_static_addRow = function(data){
		var newRow = $(DocList_AddRow('TABLE_DocList'));
		newRow.attr("rela_dataRow","1");
		if(data!=null){
			newRow.find("input[name='fdName']").val(data['fdName']);
			newRow.find("input[name='fdTemplateId']").val(data['fdTemplateId']);
			newRow.find("input[name='fdTemplateModelName']").val(data['fdTemplateModelName']);
			newRow.find("input[name='fdTemplateSubject']").val(data['fdTemplateSubject']);
			newRow.find("input[name='fdMainDataId']").val(data['fdMainDataId']);
			newRow.find("input[name='fdMainDataModelName']").val(data['fdMainDataModelName']);
			newRow.find("input[name='fdMainDataName']").val(data['fdMainDataName']);
		}else{
			self.resizeFrame();
		}
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

//选择模板
function _select_main_data(self) {
	seajs.use(['lui/dialog'], function(dialog) {
		dialog.iframe("/sys/xform/maindata/dialog/mydata/mainDataDialogSelect.jsp", 
				_params['selectCategoryTitle'], null, {
				width:800,
				height:500,
				buttons:[{
						name : _setting.params['button.back'],
						value : "back",
						fn : function(value, _dialog) {
							// 上一步
							$("[name='__select_main_data']", _dialog.element.find("iframe")[0].contentDocument).click();
						}
					}, {
						name : _setting.params['button.ok'],
						styleClass : "lui_toolbar_btn_def button_ok",
						value : true,
						focus : true,
						fn : function(value, _dialog) {
							var mydata = _dialog.mydata;
							if(mydata) {
								$(self).parents("tr").find("input[name='fdMainDataId']").val(mydata.fdId);
								$(self).parents("tr").find("input[name='fdMainDataModelName']").val(mydata.fdMainDataModelName);
								$(self).parents("tr").find("input[name='fdMainDataName']").val(mydata[mydata.displayName]);
								$(self).parents("tr").find("input[name='fdTemplateId']").val(mydata.fdTemplateId);
								$(self).parents("tr").find("input[name='fdTemplateModelName']").val(mydata.fdTemplateModelName);
								$(self).parents("tr").find("input[name='fdTemplateSubject']").val(mydata.fdTemplateSubject);
								$(self).parents("tr").find("input[name='fdName']").val(mydata.fdTemplateSubject);
								_dialog.hide();
							} else {
								dialog.alert(_params['maindata.null']);
							}
						}
					}, {
						name : _setting.params['button.cancel'],
						styleClass : "lui_toolbar_btn_gray",
						value : false,
						fn : function(value, _dialog) {
							_dialog.hide();
						}
					}]
			});
	});
}
