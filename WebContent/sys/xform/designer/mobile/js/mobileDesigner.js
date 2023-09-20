/**
 * 移动端设计器
 * 生成pc的dom结构基础上,再绘制移动端dom,容器类控件的移动端请求后台生成
 * @param id
 * var config = {
			win : window,
			fdKey : '${JsParam.fdKey}',
			defineForm : '<%=XFormConstant.TEMPLATE_DEFINE%>',
			subForm : '<%=XFormConstant.TEMPLATE_SUBFORM%>',
			formPrefix : '${sysFormTemplateFormPrefix}',
			mobileIFrame : 'IFrame_MobileFormTemplate_${JsParam.fdKey}',
			pcIFrame : 'IFrame_FormTemplate_${JsParam.fdKey}',
			templateModelName : '${sysFormTemplateForm.modelClass.name}'
	};
 */
Com_IncludeFile("jquery.ui.js",Com_Parameter.ContextPath + "resource/js/jquery-ui/","js",true);
(function(config){
	
	config.designerTd = '#TD_MobileFormTemplate_' + config.fdKey;
	
	vChar = "\u4645\u5810\u4d40";
	
	generateMobileHtmlUrl = Com_Parameter.ContextPath + "sys/xform/sys_xform/sysFormTemplateHtml.do?method=getHtml";
	
	generateIDUrl = Com_Parameter.ContextPath + "sys/xform/sys_form_template/sysFormTemplate.do";
	
	defaultMobileFormName = config.lang.defaultForm;
	
	pcDefaultSubFormKey = "subform_default";
	
	RESETVALUE_EVENT = "resetValue";
	
	UPDATE_EVENT = "update";
	
	DELETE_EVENT = "delete";
	
	ADD_EVENT = "add";
	
	//移动端iframe
	var mobileIFrame;
	
	//pc端iframe
	var pcIFrame;
	
	var __$control = $("#xform_" + config.fdKey + "_control");
	
	var __xform = $("#xform_" + config.fdKey +"_form");
	
	function MobileDesigner(){
		
		//移动端designer
		this.mobileDesigner;
		
		//pc端designer
		this.pcDesigner;
		
		//是否初始化了
		this.hasInitialized = false;
		
		//是否有变动
		this.isChanged = false;
		
		//是否需要加载
		this.needLoad = true;
		//所有的移动端表单,key>>表单Id,value>>MobileForm
		
		this.mobileForms = {};
		
		this.delFormIds = [];
		
		//当前选中的移动表单
		this.currentSelectedMobileForm = {};
		
		//获取
		this.getMobileDesigner = getMobileDesigner;
		
		this.getPcDesigner = getPcDesigner;
		
		this.getPcXFormByMobileId = getPcXFormByMobileId;
		
		this.getSubFormTrs = getSubFormTrs;
		
		this.getFormNameByTrId = getFormNameByTrId;
		
		this.getPcCheckedXForm = getPcCheckedXForm;
		
		this.getAllFormControlsByDesigner = getAllFormControlsByDesigner;
		
		this.getPcControlById = getPcControlById;
		
		this.getMobileControlById = getMobileControlById;
		
		this.findTableControl = findTableControl;
		
		//判断
		this.mobileFormIsLoaded = mobileFormIsLoaded;
		
		//动作
		this.load = load;
		
		this.checkedFormByPc = checkedFormByPc;
		
		this.checkedForm = checkedForm;
		
		this.loadForm = loadForm;
		
		this.loadMobileFormObj = loadMobileFormObj;
		
		this.loadControls = loadControls;
		
		this.addMobileForm = addMobileForm;
		
		this.copyMobileForm = copyMobileForm;
		
		this.addMobileFormByPc = addMobileFormByPc;
		
		this.copyFormByPc = copyFormByPc;
		
		this.copyPcFormToMobile = copyPcFormToMobile;
		
		this.bindEvent = bindEvent;
		
		this.preview = preview;
		
		this.setHTML = setHTML;
		
		this.expandOrHide = expandOrHide;
		
		this.formTypeClick = formTypeClick;
		
		this.formClick = formClick;
		
		this.controlClick = controlClick;
		
		this.initialize = initialize;
		
		this.removeRepeatForm = removeRepeatForm;
		
		this.formMouseOver = formMouseOver;
		
		this.formMouseOut = formMouseOut;
		
		this.deleteMobileForm = deleteMobileForm;
		
		this.removeInvalidElement = removeInvalidElement;
		
		this.removeTdWidth = removeTdWidth;
		
		this.createControl = createControl;
		
		this.mouseOver = mouseOver;
		
		this.mouseDown = mouseDown;
		
		this.attach = attach;
		
		this.deleteRow = deleteRow;
		
		this.editFormName = editFormName;
		
		this.recoveryJSPContent = recoveryJSPContent;
		
		//pc删除控件的时候,如果此控件唯一,则在移动视图中删除这个控件
		this.destroyControlIfNecessary = destroyControlIfNecessary;
		
		this.synchronize = synchronize;
		
		this.__addEventListener = __addEventListener;
		
		this.setHtmlBeforeSubmit = setHtmlBeforeSubmit;
		
		this.switchMutiTab = switchMutiTab;
		
	}
	
	/**
	 * 初始化移动端表单
	 * @returns
	 */
	function initialize(){
		var mobileIFrame = getMobileIFrame();
		var mobileDesigner = this.getMobileDesigner();
		mobileDesigner.mobileIFrame = mobileIFrame;
		var pcDesigner = this.getPcDesigner();
		this.context = document;
		if (!this.hasInitialized) {
			this.removeRepeatForm();
			mobileDesigner.fdKey = config.fdKey;
			var builder = mobileDesigner.builder;
			builder.isMobile = true;
			mobileIFrame.MobileDesigner = MobileDesigner;
			mobileIFrame.MobileDesigner.instance = this;
			var toolbar = new Designer_Mobile_Toolbar(mobileDesigner);
			this.toolbar = toolbar;
			//mobileDesigner.setHTML("");
			mobileDesigner.control = null;
			mobileDesigner.isMobile = true;
			mobileDesigner.drawMobile = true;
			mobileDesigner.mobileDesigner = this;
			pcDesigner.mobileDesigner = this;
			mobileDesigner.pcDesigner = pcDesigner;
			this.hasInitialized = true;
			//隐藏工具栏
			$(mobileDesigner.toolBarDomElement).hide();
			//隐藏添加移动端按钮
			__hideAddButtonIfNecessary();
			// 处理单表单下左侧面板
			__processMobileForm();
			//调整绘制面板高度
			mobileDesigner.adjustBuildArea();
			this.__addEventListener();
			_AdjustDrawPanelHeight();
			var self = this;
			if (typeof(seajs) != 'undefined') {
				seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog,topic) {
					topic.subscribe("subForm_del",function(evt){
						//删除对应的移动表单
						var pcFormId = evt.id;
						var $mobileTr = getMobileTrByPcId(pcFormId);
						if ($mobileTr.length == 1) {
							var delId = $mobileTr.find("[name$='fdPcFormId']").val();
							self.delFormIds.push(delId);
							DocList_DeleteRow($mobileTr[0]);
							var tr = getMobileCheckedTr();
							if(tr.length == 0){
								var defaultTr = getMobileFormTable().find('tr:eq(0)');
								self.checkedForm(defaultTr);
								$('#MobileFormDesignerDiv').scrollTop(0);
							}
						}
					});
				});
			}
		}
	}
	
	/**
	 * 
	 * @param designer
	 * @returns
	 */
	function __addEventListener() {
		var pcDesigner = this.getPcDesigner();
		var self = this;
		pcDesigner.addListener(RESETVALUE_EVENT,function(pcControl){
			var originalId = pcControl.originalId;
			//self.synchronize(originalId);
		});
		pcDesigner.addListener(UPDATE_EVENT,function(pcControl){
			var originalId = pcControl.originalId;
			self.synchronize(originalId);
		});
		pcDesigner.addListener(DELETE_EVENT,function(pcControl){
			var originalId = pcControl.originalId;
			self.synchronize(originalId);
		});
		pcDesigner.addListener(ADD_EVENT,function(pcControl){
			var originalId = pcControl.originalId;
			self.synchronize(originalId);
		});
	}
	
	/**
	 * 表单信息obj
	 * @returns
	 */
	function XForm(){
		//表单id
		this.id = '',
		//表单名称
		this.fdName = '',
		//移动端html
		this.designerHtml = '',
		//移动端xml
		this.metadataXml = '',
		//表单对应的控件
		this.controls = []
	}
	
	window.MobileDesigner = MobileDesigner;
	
	/**
	 * 移除重复的默认表单
	 * @returns
	 */
	function removeRepeatForm(){
		var mobileDesigner = this.getMobileDesigner();
		var formControls = mobileDesigner.subFormControls;
		for (var key in formControls) {
			if (pcDefaultSubFormKey === key) {
				delete formControls[key];
			}
		}
	}
	
	function getUseDefaultLayout() {
		var $useDefaultLayout = $("[name='" + config.formPrefix + "fdUseDefaultLayout']");
		return $useDefaultLayout.val() === "true";
	}
	
	//加载表单引擎
	$(document).ready(function() {
		var funStr = $(config.designerTd).attr("onresize");
		var tmpFunc = new Function(funStr);
		tmpFunc.call(this, Com_GetEventObject());
		MobileDesigner.instance = new MobileDesigner();
		var self = MobileDesigner.instance;
		var _$useDefaultLayout = $("[name='_" + config.formPrefix + "fdUseDefaultLayout']");
		var useDefaultLayout = getUseDefaultLayout();
		self.useDefaultLayout = useDefaultLayout;
		if (!useDefaultLayout) {
			var size = getAllMobileFormTr().length;
			if (size == 0) {
				_$useDefaultLayout.click();
			}
		}
		//删除行
		$(document).bind("setHtml-finish",function(e,argus){
			if (config.fdKey === argus.fdKey) {
				
				if (self.hasInitialized) {
					return;
				}
				if (!argus.designer || argus.designer.isMobile) {
					return;
				}
				if (argus.designer.subFormAddRow) {
					return;
				}
				var mobileDesigner = self.getMobileDesigner();
				var pcDesigner = self.getPcDesigner();
				if (mobileDesigner && pcDesigner 
						&& pcDesigner.builder && mobileDesigner.builder) {
					//已经有值了，则清除停止
			    	self.initialize();
			    	//#138560非默认布局才加载表单
			    	//if (!useDefaultLayout) {
			    		self.load();
			    	//}
			    } 
			}
		});
		__adjustLayout();
	});
	
	//调整整个页面的宽度,边距等
	function __adjustLayout(){
		$("#newDefaultWebForm").hide();
	}
	
	/**
	 * 获取表单模式
	 */
	function getModeValue(){
        if (config.history == "true") {
            return config.mode;
        }
        if (config.mode != "1" && config.fdKey == "modelingApp") {
            return config.mode;
        }
		var modeObj = document.getElementsByName(config.formPrefix + "fdMode")[0];
		var modeValue = null;
		if(modeObj && modeObj != null){
			modeValue = modeObj.value;
		}
		return modeValue;
	}
	
	/**
	 * 获取pc端多表单表格
	 * @returns
	 */
	function getPcSubFormTable(){
		return $("#TABLE_DocList_SubForm");
	}
	
	/**
	 * 获取移动端表单表格
	 * @returns
	 */
	function getMobileFormTable(){
		return $("#table_docList_" + config.fdKey + "_form");
	}
	
	/**
	 * 获取移动端表单设计器designer
	 */
	function getMobileDesigner(){
		if (this.mobileDesigner) {
			return this.mobileDesigner;
		}
		var mobileIFrame = getMobileIFrame();
		if(mobileIFrame.Designer != null && mobileIFrame.Designer.instance != null){
			this.mobileDesigner = mobileIFrame.Designer.instance;
		}
		return this.mobileDesigner;
	}
	
	/**
	 * 获取pc端表单设计器designer
	 */
	function getPcDesigner(){
		if (this.pcDesigner) {
			return this.pcDesigner;
		}
		var pcIFrame = getPcIFrame();
		if(pcIFrame.Designer != null && pcIFrame.Designer.instance != null){
			this.pcDesigner = pcIFrame.Designer.instance;
		}
		return this.pcDesigner;
	}
	
	/**
	 * 获取pc端表单iframe
	 */
	function getPcIFrame(){
		if (pcIFrame) {
			return pcIFrame;
		}
		pcIFrame = window.frames[config.pcIFrame].contentWindow;
		if(!pcIFrame){
			pcIFrame = window.frames[config.pcIFrame];
		}
		return pcIFrame;
	}
	
	/**
	 * 获取移动表单iframe
	 */
	function getMobileIFrame(){
		if (mobileIFrame) {
			return mobileIFrame;
		}
		mobileIFrame = window.frames[config.mobileIFrame].contentWindow;
		if(!mobileIFrame){
			mobileIFrame = window.frames[config.mobileIFrame];
		}
		return mobileIFrame;
	}
	
	/**
	 * 获取移动端选中的表单
	 * @returns
	 */
	window.getMobileCheckedTr = function(){
		return getMobileFormTable().find("tr[ischecked='true']");
	}
	
	/**
	 * 获取移动端所有的表单
	 * @returns
	 */
	function getAllMobileFormTr(){
		return getMobileFormTable().find("tr[ischecked]");
	}
	
	
	/**
	 * 根据pc表单id获取对应的移动表单
	 * @param pcId
	 * @returns
	 */
	 window.getMobileTrByPcId = function(pcFormId) {
		var $input = getMobileFormTable().find("input[name$='fdPcFormId'][value='" + pcFormId + "']");
		return $input.closest("tr");
	}
	
	/**
	 * 获取所有的pc表单
	 * @returns
	 */
	function getSubFormTrs(){
		if (isUseSubForm()) {
			return getPcSubFormTable().find("tr[ischecked]");
		}
	}
	
	/**
	 * 获取多表单pc选中的tr
	 * @returns
	 */
	window.getSubFormCheckedTr  = function (){
		if (isUseSubForm()) {
			return getPcSubFormTable().find("tr[ischecked='true']");
		}
	}
	
	/**
	 * 
	 * @param 根据表单id获取表单名称
	 * @returns
	 */
	function getFormNameByTrId(id){
		var name = "";
		if (isUseSubForm()) {
			name = getPcSubFormTable().find("tr#"+id).find("input[name$='fdName']").val();
		}
		if (isUseDefineForm()) {
			name = defaultMobileFormName;
		}
		return name;
	}
	
	window.getPcFormTrByMobileTr = function($mobileFormTr){
		var pcFormId = $mobileFormTr.find("input[name$='fdPcFormId']").val();
		var $subFormTable =  getPcSubFormTable();
		var defaultId = getPcDefaultId();
		var $pcFormTr;
		if (isUseSubForm()) {
			if (defaultId === pcFormId) {
				var $pcFormTr = $subFormTable.find("tr[id='subform_default']");
			} else {
				var $pcFormTr = $subFormTable.find("input[name$='fdId'][value='" + pcFormId + "']").closest("tr");
			}
		}
		if (isUseDefineForm()) {
			if (pcFormId == defaultId) {
				var $pcFormTr = $subFormTable.find("tr[id='subform_default']");
			}
		}
		return $pcFormTr;
	}
	
	/**
	 * 根据移动端表单id获取pc端XForm
	 * @param mobileFormId
	 * @returns
	 */
	function getPcXFormByMobileId(mobileFormId) {
		var xform = new XForm();
		var pcDesigner = this.getPcDesigner();
		var $subFormTable =  getPcSubFormTable();
		var $mobileFormTr = $("#" + mobileFormId);
		var pcFormId = $mobileFormTr.find("input[name$='fdPcFormId']").val();
		xform.id = pcFormId;
		var defaultId = getPcDefaultId();
		if (isUseSubForm()) {
			if (defaultId === pcFormId) {
				xform.name =  "";
				var $defaultTr = $subFormTable.find("tr[id='subform_default']");
				xform.designerHtml =  $defaultTr.find("input[name='defaultfdDesignerHtml']").val();
				xform.metadataXml =  $defaultTr.find("input[name='defaultfdMetadataXml']").val();
			} else {
				var $pcFormTr = $subFormTable.find("input[name$='fdId'][value='" + pcFormId + "']").closest("tr");
				xform.name =  $pcFormTr.find("input[name$='fdName']").val();
				xform.designerHtml = $pcFormTr.find("input[name$='fdDesignerHtml']").val();
				xform.metadataXml =  $pcFormTr.find("input[name$='fdMetadataXml']").val();
			}
		} 
		if (isUseDefineForm()) {
			xform.name =  "pc表单";
			if (pcFormId == defaultId) { //区别是通过pc生成的表单还是添加空的移动端表单
				xform.designerHtml = pcDesigner.getHTML();
				xform.metadataXml =  pcDesigner.getXML();
			} else {
				xform.designerHtml = "";
				xform.metadataXml =  "";
			}
		}
		return xform;
	}
	
	/**
	 * 获取配pc默认的表单id
	 */
	 window.getPcDefaultId = function(){
		return document.getElementsByName(config.formPrefix + "fdId")[0].value;
	}
	
	/**
	 * 获取所有的控件(不重复)
	 * @returns
	 */
	function getAllFormControlsByDesigner(designer,currFormId){
		var allPcUniqueControl = [];
		if (isUseSubForm()) {
			//所有的pc表单控件
			var allPcControls = designer.subFormControls || [];
			for (var key in allPcControls) {
				var formControls = allPcControls[key];
				for (var i = 0;i < formControls.length; i++) {
					var isExit = __isExit(formControls[i],allPcUniqueControl);
					var isDefault = formControls[i].options.values.isDefault;
					if (!isExit && !isDefault) {
						if (formControls[i].isDetailsTable && currFormId && currFormId != key) {
							continue;
						}
						allPcUniqueControl.push(formControls[i]);
					}
				}
			}
		} 
		if (isUseDefineForm()) {
			allPcUniqueControl = concat(allPcUniqueControl,getControls(designer));
		}
		
		return allPcUniqueControl;
	}
	
	function concat(target,src) {
		target = target || [];
		src = src || [];
		for (var i = 0;i < target.length; i++) {
			src.push(target[i]);
		}
		return src;
	}
	
	/**
	 * 根据指定id获取pc控件
	 * @param id
	 * @returns
	 */
	function getPcControlById(id) {
		var pcDesigner = this.getPcDesigner();
		var allPcControls = this.getAllFormControlsByDesigner(pcDesigner);
		for (var i = 0; i < allPcControls.length; i++) {
			if (id === allPcControls[i].options.values.id) {
				return allPcControls[i];
			}
		}
	}
	/*
	 * 获取指定表单id下的控件
	 * @param designer
	 * @returns
	 */
	function getFormControlsByDesigner(designer,formId){
		var controls = [];
		if (isUseSubForm()) {
			var allFormControls = designer.subFormControls || [];
			for (var key in allFormControls) {
				if (formId === key) {
					var formControls = allFormControls[key];
					for (var i = 0;i < formControls.length; i++) {
						controls.push(formControls[i]);
					}
				}
			}
		} 
		if (isUseDefineForm()) {
			controls = concat(controls,getControls(designer));
		}
		
		return controls;
	}

	
	
	/**
	 * 根据指定id获取pc控件
	 * @param id
	 * @returns
	 */
	function getMobileControlById(id) {
		var mobileDesigner = this.getMobileDesigner();
		var rtnControls = [];
		var allMobileControls = this.getAllFormControlsByDesigner(mobileDesigner);
		for (var i = 0; i < allMobileControls.length; i++) {
			if (id === allMobileControls[i].options.values.id) {
				rtnControls.push(allMobileControls[i]);
			}
		}
		return rtnControls;
	}
	
	
	/**
	 * 获取单表单内的所有控件
	 * @param designer
	 * @returns
	 */
	window.getControls = function(designer) {
		var objs = [];
		var mobileIframe = getMobileIFrame();
		var controls = designer.builder.controls.sort(mobileIframe.Designer.SortControl);
		_getControls(controls, objs);
		return objs;
	}

	function _getControls(controls, objs,sort) {
		for (var i = 0, l = controls.length; i < l; i ++) {
			var control = controls[i];
			objs.push(control);
			_getControls(sort ? control.children.sort(sort) : control.children.sort(),objs,sort);
		}
	}
	
	/**
	 * 根据pc表单id判断移动端是否已经加载了
	 * @param pcFormId
	 * @returns
	 */
	function mobileFormIsLoaded(pcFormId){
		//已经存在不需要添加
		var $mobileForm = getMobileTrByPcId(pcFormId);
		var $allMobileForm = getAllMobileFormTr();
		if (isUseDefineForm()) {
			if ($mobileForm.length == 1 && $allMobileForm.length === 1) {
				return true;
			}
		} else {
			if ($mobileForm.length == 1 ) {
				return true;
			}
		}
		//处理单表单下，通过导入表单重复生成记录
		if (isUseDefineForm()) {
			var originalPcFormId = $($allMobileForm[0]).find("input[name$='fdPcFormId']").val();
			if($allMobileForm.length == 1 ) {
				if (pcFormId != originalPcFormId) {
					$($allMobileForm[0]).find("input[name$='fdPcFormId']").val(pcFormId);
					return true;
				}
			} else {
				var isExit = false;
				$allMobileForm.each(function(index,tr){
					var originalPcFormId = $(tr).find("input[name$='fdPcFormId']").val(); 
					if (pcFormId != originalPcFormId) {
						setTimeout(function(){
							DocList_DeleteRow(tr);
						},100)
					} else {
						if (index != 0) { //删除多余的行
							setTimeout(function(){
								DocList_DeleteRow(tr);
							},100)
						}
						isExit = true;
					}
				});
				return isExit;
			}
		}
		return false;
	}
	
	/**
	 * 是否使用表单
	 */
	function isUseForm(){
		var modeValue = getModeValue();
		if (modeValue == config.defineForm || 
				modeValue == config.subForm) {
			return true;
		}
		return false;
	}

	/**
	 * 是否使用了自定义表单
	 */
	window.isUseDefineForm = function (){
		var modeValue = getModeValue();
		return modeValue == config.defineForm;
	}

	/**
	 * 是否使用多表单
	 */
	window.isUseSubForm = function (){
		var modeValue = getModeValue();
		return modeValue == config.subForm;
	}
	
	//根据pc选中的表单设置移动选中的表单
	function checkedFormByPc(){
		//多表单先切换每个表单
		if (isUseSubForm()) {
			var $trObjs = getSubFormTrs();
			var $pcCheckedTr = getSubFormCheckedTr();
			$trObjs.each(function(index,dom){
				SubForm_SetChecked($(dom),false);
			});
			SubForm_SetChecked($pcCheckedTr,false);
		}
		var xform = this.getPcCheckedXForm();
		var moblieFormTr = getMobileTrByPcId(xform.id);
		this.checkedForm(moblieFormTr);
	}
	
	/**
	 * 切换表单,列出pc新增控件或者其它表单控件,如果还没解析成移动端的html则请求后台进行解析
	 */
	function checkedForm(dom,isCopy,copyDom){
		var mobileDesigner = this.getMobileDesigner();
		var pcDesigner = this.getPcDesigner();
		//切换前选中的表单行
		var currentCheckedTr = getMobileCheckedTr();
		var $allTr = getMobileFormTable().find("tr");
		$allTr.each(function(){
			if(this != dom[0]){
				$(this).attr("ischecked","false");
				$(this).find("a").css("color","");
				$(this).css("background","");
			}else{
				$(this).find("a").css("color","#4285fa");
				$(this).attr("ischecked","true");
				$(this).css("background","#D6EAFF");
			}
		});
		//切换后选中的表单行
		var targetCheckedTr = getMobileCheckedTr();
		if (mobileDesigner != null && pcDesigner != null) {
			if (currentCheckedTr[0] == targetCheckedTr[0]) {
				//加载控件
				this.loadControls();
			} else {
				//设置当前表单
				var mobileForm = {};
				mobileForm.id = targetCheckedTr.attr("id");
				mobileForm.link = targetCheckedTr.find("[name='mobileFormText']");
				mobileForm.fdDesignerHtmlObj = targetCheckedTr.find("input[name$='fdDesignerHtml']");
				mobileForm.isMobile = true;
				mobileForm.tr = targetCheckedTr;
				mobileDesigner.subForm = mobileForm;
				//切换前保存上一个表单信息,有可能是从pc切过来的，此时可能还不存在上一个选中的表单
				if (currentCheckedTr.length == 1) {
					var latestDesignerHtml = mobileDesigner.getHTML();
					var latestMetadataXml = mobileDesigner.getXML();
					var currentDesignerHtml = currentCheckedTr.find("input[name$='fdDesignerHtml']");
					var currentMetadataXml = currentCheckedTr.find("input[name$='fdMetadataXml']");
					if(latestDesignerHtml != currentDesignerHtml.val()){
						currentDesignerHtml.val(latestDesignerHtml);
						//SubFormData.isChanged = true;
					}
					if(latestDesignerHtml != currentMetadataXml.val()){
						//是否保存新版本
						if (MobileForm_filterXml(latestMetadataXml) != MobileForm_filterXml(currentMetadataXml.val())) {
							//SubFormData.saveasNew_subform = true;
						}
						currentMetadataXml.val(latestMetadataXml);
					}
				}
				
				var newHtml = "";
				//是否请求后台生成移动端配置的html
				var isRequestMoblieHtml = false;
				if (isCopy) {
					mobileDesigner.mobileForms.push(mobileForm);
					var tr3 = $(copyDom).parents("tr[ischecked]");
					newHtml = tr3.find("input[name$='fdDesignerHtml']").val();
				} else {
					//目标选中行的表单html
					newHtml = targetCheckedTr.find("input[name$='fdDesignerHtml']").val();
					//未初始化,用pc的designerHtml
					if (!newHtml) {
						isRequestMoblieHtml = true;
						newHtml = this.getPcXFormByMobileId(mobileForm.id).designerHtml;
						if (newHtml) {
							//未初始化添加到表单数组中
							mobileDesigner.subForms.push(mobileForm);
						}
					}
				}
				if (newHtml) {
					var mobileHtml = this.setHTML(newHtml,isRequestMoblieHtml);
					targetCheckedTr.find("input[name$='fdDesignerHtml']").val(mobileHtml);
				} else {
					this.setHTML("",false);
					mobileDesigner.subForms.push(mobileForm);
					__createMobileStandardTable(mobileDesigner,pcDesigner);
				}
				MobileForm_afterSwitch(mobileDesigner);
				//加载控件
				this.loadControls();
				_hideAttach(mobileDesigner);
				__showOperation(targetCheckedTr);
			}
		}
	}
	
	function __showOperation(targetCheckedTr){
		if (targetCheckedTr) {
			targetCheckedTr.find(".xform_operation").show();
			getAllMobileFormTr().each(function(index,tr){
				if (tr != targetCheckedTr[0]) {
					$(tr).find(".xform_operation").hide();
				}
			});
		}
	}
	
	window.editName = function(self){
		var parent = $(self).parents("tr[ischecked]");
		parent.find("a[name='mobileFormText']").hide();
		parent.find("input[name$='fdName']").show();
		parent.find("input[name$='fdName']").select();
		checkedForm.call(MobileDesigner.instance,parent,false);
	}

	window.textBlur = function(self){
		var value = self.value;
		//校验表单名称是否为空
		if(!value){
			$(self).attr("onblur","");
			if (typeof(seajs) != 'undefined') {
				seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
					dialog.alert(config.lang.formName,function(){
						$(self).attr("onblur","textBlur(this)");
						$(self).select();
					});
				});
			}
			return
		}
		//校验表单名称是否存在
		var input = getMobileFormTable().find("[name$='fdName']");
		for(var i = 0; i<input.length; i++){
			if(input[i] != self && input[i].value == value){
				$(self).attr("onblur","");
				if (typeof(seajs) != 'undefined') {
					seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
						dialog.alert(config.lang.duplicateName,function(){
							$(self).attr("onblur","textBlur(this)");
							$(self).select();
						});
					});
				}
				return
			}
		}
		var a = $(self).parent().find("a[name='mobileFormText']");
		a.attr("title",value);
		if(value.length>10){
			value = value.substring(0,10) + "...";
		}
		a.text(value);
		a.show();
		$(self).hide();
	}

	/**
	 *	创建移动端主表,默认一行两列
	 * @param mobileDesigner designer.instance
	 * @param pcDesigner
	 * @returns
	 */
	function __createMobileStandardTable(mobileDesigner,pcDesigner){
		mobileDesigner.control = null;
		var mobileIFrame = getMobileIFrame();
		//避免待选控件列表出现主表
		var control = new mobileIFrame.Designer_Control(mobileDesigner.builder, 'standardTable',false);
		if (pcDesigner && pcDesigner.builder.controls[0]
			&& pcDesigner.builder.controls[0].type === "standardTable") {
			control.options.values = pcDesigner.builder.controls[0].options.values;
		}
		control.attrs.row.value = 1;
		control.attrs.cell.value = 2;
		control.attrs.cell.style = "muiTitle";
		mobileDesigner.builder.createControl(control);
		$(control.options.domElement).addClass("muiTable");
		modifyStandTableWidth(mobileDesigner);
	}

	/**
	 * 获取pc当前选中的表单
	 * @returns
	 */
	function getPcCheckedXForm(){
		var xform = new XForm();
		var pcDesigner = this.getPcDesigner();
		var defaultId = getPcDefaultId();
		if (isUseSubForm()) {
			var $tr = getSubFormCheckedTr();
			xform.id = $tr.attr("id");
			if (xform.id === pcDefaultSubFormKey) {
				xform.id = defaultId;
			}
			xform.name = this.getFormNameByTrId(xform.id);
		}
		if (isUseDefineForm()) {
			xform.id = defaultId
		}
		xform.designerHtml = pcDesigner.getHTML();
		xform.metadataXml = pcDesigner.getXML();
		return xform;
	}

	//外部事件绑定
	function bindEvent(event, eventHandle){
		var _event = event || window.event, _eventHandle = eventHandle || null;
		if (_eventHandle == null) return;
		var currElement = event.srcElement || event.target;
		this[eventHandle](currElement);
	}

	/**
	 * 预览
	 */
	function preview() {
		var clientType = $(".xform_client_type").find(".is-active").attr("val");
		var fdModelId = Com_GetUrlParameter(window.top.location.href, "fdId");
		var hasSeaJs = (typeof(seajs) != 'undefined' && typeof( top['seajs'] ) != 'undefined');
		if (clientType == "pc") {
			if (!fdModelId) {
				console.log("从url中获取不到模板id！");
				if (hasSeaJs) {
					seajs.use( [ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
						dialog.alert(config.lang.templateFirst);
					});
				} else {
					_dialog_alert(config.lang.templateFirst);
				}
				return;
			}
			if (typeof XForm_GetFlowModels != "undefined") {
				var flowModels = XForm_GetFlowModels();
				if (flowModels.length == 0) {
					if (hasSeaJs) {
						seajs.use( [ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
							dialog.alert(config.lang.designFirst);
						});
					} else {
						_dialog_alert(config.lang.designFirst);
					}
					return;
				}
			}
		}
		if (clientType == "pc") {
			var height = document.documentElement.clientHeight * 0.9;
			var width = document.documentElement.clientWidth * 0.8;
			var pcCheckedForm = this.getPcCheckedXForm();
			var pcDesigner = this.getMobileDesigner();
			var currentXFormId = pcCheckedForm.id;
			var fdModelName = pcDesigner.fdModelName;
			var history = false;
			if (config.history == "true") {
                fdModelName = _xform_templateModelName;
                fdModelId = _xform_templateId;
                history = true;
            }
			var url = "";
			url = "/sys/xform/sys_form_template/sysFormTemplatePreview.do?method=preview";
			url += "&fdKey=" + config.fdKey;
			url += "&fdMainModelName=" + _xform_MainModelName;
			url += "&fdModelName=" + fdModelName;
			url += "&fdFormId=" + currentXFormId;
			url += "&fdAppModelId=" + fdModelId;
            url += "&history=" + history;
			if (typeof(seajs) != 'undefined' && typeof( top['seajs'] ) != 'undefined' ) {
				seajs.use( [ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
					dialog.iframe(url,config.lang.preview,null,{width : width,height : height});
				});
			}
		} else if (clientType == "mobile") {
			var mobileDesigner = this.getMobileDesigner();
			var mobileHtml = mobileDesigner.getHTML();
			_dialogPreview(mobileHtml);
		}
	}


	function _dialogPreview(designerHtml){
		var previewHtml = "";
		var mobileIFrame = getMobileIFrame();
		var baseInfoTbHtml = $("#baseInfoTable",mobileIFrame.document).prop("outerHTML") || "";
		designerHtml = baseInfoTbHtml + designerHtml;
		previewHtml += designerHtml;
		if (typeof(seajs) != 'undefined' && typeof( top['seajs'] ) != 'undefined' ) {
			seajs.use( [ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
				 var url = "/sys/xform/designer/sysFormTemplate_mobile_preview.jsp";
                 dialog.iframe(url, config.lang.preview, function (rt) {

                 }, {width: 638, height: 674, topWin: window.top}).on('show', function() {
                	 var iframe = this.element.find("iframe")[0];
                	 this.element.css("z-index","10000");
                	 if (iframe) {
                		 var context = iframe.contentWindow || iframe;
                		 setTimeout(function(){
                    		 $(context.document).find("[name='mobileDesigerHtml']").html(designerHtml);
                    		 $(context.document).find("[removeInPreview='true']").remove();
                    		 $(context.document).find(".mobileDeleteRowIcon").remove();
                    	 },500);
                	 }
 				});
			});
		}
	}

	//pc or mobile switch
	function formTypeClick(src) {
		var viewPlats = $("[name='sysFormTemplateForms." + config.fdKey + ".type']");
		var useDefaultLayout = getUseDefaultLayout();
		var val = $(src).attr("val");
		if (useDefaultLayout && val == "mobile") {
		    if (config.history == "true") {
				_dialog_alert(config.lang.mobileNotAllowedDesign);
            } else {
				_dialog_alert(config.lang.mobileAllowedDesign);
            }
			return;
		}
		var viewType = viewPlats.find("span[class*='xform_client_btn']");
		var $customTemplateRow = $("[id='XForm_" + config.fdKey + "_CustomTemplateRow']");
		var $ViewPlatRow = $("[id='xform_" + config.fdKey + "_design']");
		var $pcFormTr = $("[id='pc_form_" + config.fdKey + "']");
		var pcDesigner = this.getPcDesigner();
		if (!pcDesigner) {
			_dialog_alert(config.lang.pcNotLoad);
			 return;
		}
		if ($(src).hasClass("is-active")){

		} else {
			$(src).addClass("is-active");
			if (val  == "mobile") {
				//隐藏pc
				//__loading();
				this.__isShowTip = true;
				$customTemplateRow.hide();
				$pcFormTr.hide();
				$ViewPlatRow.show();
				__processMobileForm();
				var self = this;
				var mobileDesigner = self.getMobileDesigner();
				var pcDesigner = self.getPcDesigner();
				if (mobileDesigner && pcDesigner && mobileDesigner.builder) {
					//已经有值了，则清除停止
			    	self.initialize();
			    	//加载表单
					self.load();
					//标识页签是否切换过
					if (typeof self.__clicked === "undefined") {
						self.__clicked = true;
					}
					//调整左侧面板高度
					_AdjustFormPanelHeight();
					var $operationObj = $(".xform_mobile_operation ");
					self.toolbar.originalTop = $operationObj.offset().top;
			    }
				//__hide();
			} else {
				$customTemplateRow.show();
				$pcFormTr.show();
				$ViewPlatRow.hide();
				if (typeof SubForm_AdjustEditHeight != "undefined" ){
					SubForm_AdjustEditHeight();
				}
			}
			viewType.each(function(index,obj){
				if (obj != src) {
					$(obj).removeClass("is-active");
				}
			});
			return false;
		}
	}

	function mouseScroll(e) {
		e = e || window.event;
		var mobileIFrame = getMobileIFrame();
		var $designerDraw = $("[id='designer_draw']",mobileIFrame.document);
	    if (e.wheelDelta) {  //判断浏览器IE，谷歌滑轮事件
	        if (e.wheelDelta > 0) { //当滑轮向上滚动时
	        	$designerDraw.css("margin-top","-100px");
	        }
	        if (e.wheelDelta < 0) { //当滑轮向下滚动时
	        }
	    } else if (e.detail) {  //Firefox滑轮事件
	        if (e.detail> 0) { //当滑轮向上滚动时
	        	$designerDraw.css("margin-top","-100px");
	        }
	        if (e.detail< 0) { //当滑轮向下滚动时
	        }
	    }
    }

    function bindMouseScorll(win,document){
    	 //给页面绑定滑轮滚动事件
        if (document.addEventListener) {
            document.addEventListener('DOMMouseScroll', mouseScroll, false);
        }
        //滚动滑轮触发scrollFunc方法
        win.onmousewheel = document.onmousewheel = mouseScroll;
    }

	/**
	 * 表单点击事件
	 */
	function formClick(dom){
		this.checkedForm($(dom).parents("tr[ischecked]"),false);
		var $currentTr = $(dom).closest("tr[ischecked]");
		//$currentTr.find(".xform_operation").show();
	}

	/**
	 * 判断控件是否允许添加到移动端表单中
	 * @param myControl
	 * @param mobileDesigner
	 * @returns
	 */
	function __isAllowAdd(control,mobileDesigner) {
		//控件已存在当前移动端表单中,不允许添加
		var isExit = __isExitInCurrentMobileForm(control.options.values.id,mobileDesigner);
		if (isExit) {
			return false;
		}
		//如果父控件是明细表,且明细表还未被添加到移动端,则此控件不允许添加
		if (control.parent && control.parent.isDetailsTable) {
			var parentId = control.parent.options.values.id ;
			isExit = __isExitInCurrentMobileForm(parentId,mobileDesigner);
			if (!isExit) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 是否在当前的移动端表单中存在
	 * @param controlId
	 * @param mobileDesigner
	 * @returns
	 */
	function __isExitInCurrentMobileForm(controlId,mobileDesigner) {
		var currentMobileFormControls = mobileDesigner.subFormControls[mobileDesigner.subForm.id];
		if(currentMobileFormControls){
			for (var i = 0; i < currentMobileFormControls.length; i++) {
				if (controlId === currentMobileFormControls[i].options.values.id) {
					return true;
				}
			}
		}else{
			if(console && console.log){
				console.log("【移动端配置】判断控件ID('"+ controlId +"')是否在当前的移动端表单中存在失败，找不到当前移动控件！");
			}
		}
		return false;
	}

	/**
	 * 删除行
	 * @param currElement
	 * @returns
	 */
	function deleteRow(currElement) {
		var mobileDesigner = this.getMobileDesigner();
		var currentRow = mobileDesigner.builder.mobileDragBox.attached;
		var currControl = mobileDesigner.builder.getControlByDomElement(currentRow);
		if (!currControl) {
			_dialog_alert(config.lang.selectRowFirst);
			return;
		}
		if (__isAllowDelete(mobileDesigner,currControl)) {
			currControl.selectedDomElement = currentRow.cells;
			currControl.deleteRow();
			var deleteIcon = $(mobileDesigner.builder.domElement).find(".mobileDeleteRowIcon");
			mobileDesigner.builder.mobileDragBox.hide();
			deleteIcon.hide();
			this.loadControls();
		}
	}

	/**
	 * 只能删除主表的行,并且必须保留一行
	 * @param mobileDesigner
	 * @param attachControl
	 * @returns
	 */
	function __isAllowDelete(mobileDesigner,attachControl) {
		var controls = getControls(mobileDesigner);
		if (controls.length == 1 && controls[0].type == "standardTable"
			&& attachControl && attachControl.type == "standardTable") {
			var table = controls[0].options.domElement;
			if (table.rows && table.rows.length == 1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 控件点击事件
	 * @param self
	 * @returns
	 */
	function controlClick(self){
		var mobileDesigner = this.getMobileDesigner();
		var pcDesigner = this.getPcDesigner();
		var mobileIFrame = getMobileIFrame();
		if (mobileDesigner) {
			var pcFormControl = null;
			var $mobileFormTr = getMobileCheckedTr();
			var pcFormId = getPcFormTrByMobileTr($mobileFormTr).attr("id");
			var pcFormControls = this.getAllFormControlsByDesigner(pcDesigner,pcFormId);
			for (var i = 0; i < pcFormControls.length; i++) {
				if ($(self).closest("div").attr("id") == "mobileForm_" + pcFormControls[i].options.values.id) {
					pcFormControl = pcFormControls[i];
					break;
				}
			}
			if (pcFormControl) {
				var tdElement = $(config.designerTd)[0];
				var builder = mobileDesigner.builder;
				var isAllowAdd = __isAllowAdd(pcFormControl,mobileDesigner);
				var selector = "#mobileForm_" + pcFormControl.options.values.id;
				if (!isAllowAdd) {
					if (pcFormControl.parent && pcFormControl.parent.isDetailsTable) {
						if (!$(selector).attr("onclick")) {
							var parentControlId = pcFormControl.parent.options.values.id;
							selector = "#mobileForm_" + parentControlId;
							var isExit = __isExitInCurrentMobileForm(parentControlId,mobileDesigner);
							if (!isExit) {
								__removeOtherClass($(selector)[0]);
								$(selector).toggleClass("xform_control_selected");
							}
						}
					}
					return;
				}
				var pcValues = pcFormControl.options.values;
				var addOneColumnRow = isAddOneColumnRow(pcFormControl,mobileDesigner);
				builder.isAddRow = addOneColumnRow;
				var mobileFormControl = constructMobileControlByPc(pcFormControl,builder,mobileIFrame);
				var selectedDom = mobileIFrame._Designer_FieldPanel_GetSelectedDom();
				builder.owner.toolBar.selectButton(pcFormControl.type);
				if (mobileDesigner.createSubformControl) {
					if (mobileDesigner.createSubformControl.options.values.id == mobileFormControl.options.values.id) {
						$(selector).removeClass("xform_control_selected");
						mobileDesigner.createSubformControl = '';
						builder.owner.toolBar.cancelSelect();
					} else {
						removeSelectedClass();
						mobileDesigner.createSubformControl = mobileFormControl;
						$(selector).addClass("xform_control_selected");
					}
				} else {
					mobileDesigner.createSubformControl = mobileFormControl;
					removeSelectedClass();
					$(selector).addClass("xform_control_selected");
				}
			}
		}
	}

	function isAddOneColumnRow(pcFormControl,mobileDesigner) {
		var addOneColumnRow = false;
		var pcValues = pcFormControl.options.values;
		var isLabelBindId = pcValues._label_bind == "true"
			&& pcValues._label_bind_id != "" && pcValues._label_bind_id != null;
		var isContainer = pcFormControl.container;
		var controls = getControls(mobileDesigner) || [];
		var isLabelUsed = false;
		for (var i = 0; i < controls.length; i++) {
			if (controls[i].type == "textLabel"
				&& controls[i].options.values.id == pcValues._label_bind_id) {
				isLabelUsed = true;
			}
		}
		addOneColumnRow = (isLabelBindId && !isLabelUsed) || isContainer;
		return addOneColumnRow;
	}

	/**
	 * 根据pc控件构造一个移动配置的控件
	 * @param pcControl
	 * @param builder
	 * @param mobileIFrame
	 * @returns
	 */
	function constructMobileControlByPc(pcControl,builder,mobileIFrame){
		var mobileFormControl = new mobileIFrame.Designer_Control(builder, pcControl.type, false);
		mobileFormControl.options.values = pcControl.options.values;
		mobileFormControl.__pcOptions = pcControl.options;
		mobileFormControl.options.__pcDomElementOuterHtml =  pcControl.options.domElement.outerHTML.toString();
		updateTree(pcControl,mobileFormControl,builder,mobileIFrame);
		return mobileFormControl;
	}

	/**
	 * 更新子控件,容器类控件创建时,一起创建子控件
	 * @param pcControl
	 * @param mobileControl
	 * @param builder
	 * @param mobileIFrame
	 * @returns
	 */
	function updateTree(pcControl,mobileControl,builder,mobileIFrame){
		if (pcControl.children &&  pcControl.children.length != 0) {
			for (var i = 0; i < pcControl.children.length; i++) {
				var pcChild = pcControl.children[i];
				var mobileChild = constructMobileControlByPc(pcChild,builder,mobileIFrame);
				if (!mobileControl.__children) {
					mobileControl.__children = [];
				}
				mobileControl.__children.push(mobileChild);
				updateTree(pcChild,mobileChild,builder,mobileIFrame);
			}
		}
	}

	/**
	 * 移除待选控件选中样式
	 * @returns
	 */
	function removeSelectedClass(){
		__$control.find(".xform_control_selected").each(function(index,obj){
			$(obj).removeClass("xform_control_selected");
		});
	}

	/**
	 * 移除其它控件的选中样式
	 * @param current
	 * @returns
	 */
	function __removeOtherClass(current){
		__$control.find(".xform_control_selected").each(function(index,obj){
			if (obj != current) {
				$(obj).removeClass("xform_control_selected");
			}
		});
	}

	/**
	 * 折叠按钮点击事件
	 */
	function expandOrHide(dom){
		var val = $(dom).attr("val");
		var $rightBtn = $(dom).closest("[val]");
		var $textDiv = $rightBtn.find(".model-rightbar-btn-tips");
		if (!val) {
			val = $rightBtn.attr("val");
		}
		var $mobileFormLeftTd = $("#td_xform_" + + config.fdKey + "_left");
		var $mobileFormRightTd = $("#xform_" + config.fdKey + "_right");
		if (val == "toLeft") {
			$mobileFormTd = $mobileFormLeftTd;
		} else {
			$mobileFormTd = $mobileFormRightTd;
		}

		if($mobileFormTd.is(":hidden")){
			$mobileFormTd.show();
			$rightBtn.addClass("active");
			$textDiv.text(config.lang.putAway);
			if (val == "toRight") {
				$rightBtn.removeClass("hide");
			}
		}else{
			$mobileFormTd.hide();
			$rightBtn.removeClass("active");

			if (val == "toRight") {
				$rightBtn.addClass("hide");
				$textDiv.text(config.lang.basicSet);
			} else {
				$textDiv.text(config.lang.control);
			}
		}

		if($mobileFormLeftTd.is(":hidden")
				&& $mobileFormRightTd.is(":hidden")) {
			$(config.designerTd).css("width","98%");
		} else {
			$(config.designerTd).css("width","");
		}

	}

	/**
	 * 设置html
	 * isRequestMoblieHtml 是否请求后台生成移动端配置的html,如果已经是移动端的html,则为false
	 * @returns
	 */
	 function setHTML(html,isRequestMoblieHtml){
		var mobileDesigner = this.getMobileDesigner();
		var mobileIFrame = getMobileIFrame();
		//表单源码解密
		if(html.indexOf(vChar) >= 0){
			var vData = {"fdDesignerHtml":html};
			html = requestDecodeHtml(vData).html;
		}
		//请求后台生成移动布局的html
		var data = {"html":html,modelName:config.templateModelName};
		if (isRequestMoblieHtml) {
		    if (data.html && data.html.indexOf(vChar) < 0) {
                data.html = base64Encodex(data.html);
            }
			var result = requestGenerateMobileHtml(data);
			if (!result) {
			    return;
            }
			html = result.html;
		}
		mobileDesigner.setHTML(html);
		//移除移动端生成的多余元素
		this.removeInvalidElement();
		//修正属性
		__AdjustAttr("formDesign",mobileDesigner);
		//再次调用setHTML
		mobileDesigner.setHTML(mobileDesigner.getHTML());
		modifyStandTableWidth(mobileDesigner);
		this.removeTdWidth();
		//调整绘制面板高度
		_AdjustDrawPanelHeight();

		_afterSetHTML(mobileDesigner,this);

		return mobileDesigner.getHTML();
	}

	 function _afterSetHTML(mobileDesigner,self) {
		 var builder = mobileDesigner.builder;
		 $(mobileDesigner.builderDomElement).find("[fd_type='composite']").each(function(index,obj){
			 var control = builder.getControlByDomElement(obj);
			if (typeof control.drawMobile != "undefined") {
				control.drawMobile();
			}
		 });
		transfer(mobileDesigner,self);
		 setTableStyleAfterSetHTML(builder.controls);
		 __initBaseInfoTableStyle(builder);
	 }

	 function __initBaseInfoTableStyle(builder){
		var container = getMobileIFrame().document;
		//不存在基本信息配置的时候跳过
		if($("#baseInfoTable",container).length <= 0)
			return;
		//初始化，获取class，并设置
		var $table = $("table[fd_type=standardTable][tableStyle]",container).eq(0);
		var baseInfoTableStyle = $table.attr("baseInfoTableStyle");
		if(baseInfoTableStyle){
			baseInfoTableStyle = eval('('+baseInfoTableStyle.replace(/quot;/g,"\"")+')')
			var className = baseInfoTableStyle.tbClassName;
			$("#baseInfoTable",container).attr("class",className);
		} else {
            $("#baseInfoTable",container).attr("class","muiSimple");
        }
//		else{
//			var tableStyle = $table.attr("tableStyle");
//			if(tableStyle){
//				tableStyle = eval('('+tableStyle.replace(/quot;/g,"\"")+')')
//				var className = tableStyle.tbClassName;
//				$("#baseInfoTable",container).attr("class",className);
//
//				var tableControl = builder.getControlByDomElement($table[0]);
//				tableControl.options.values.baseInfoTableStyle=tableControl.options.values.tableStyle;
//				$(tableControl.options.domElement).prev("[name='dynamicBaseInfoTableStyle']").remove();
//				$(tableControl.options.domElement).before("<link rel='stylesheet' name='dynamicBaseInfoTableStyle' type='text/css' href='"+Com_Parameter.ContextPath+tableStyle.pathProfix+"/standardtable.css'/>");
//				$(tableControl.options.domElement).attr("setBaseInfoTableStyle",true);
//				$(tableControl.options.domElement).attr("baseInfoTableStyle",tableControl.options.values.tableStyle)
//			}
//		}
	}

	 function setTableStyleAfterSetHTML(controls){
		 //进行递归
		 controls.forEach(function(control,index){
			 _setTableStyle(control);
			 if(control.children && control.children.length > 0){
				 setTableStyleAfterSetHTML(control.children);
			 }
		 })
	 }

	 function _setTableStyle(control){
		 var values = control.options.values;
		 var element = control.options.domElement;

		 if(control.type == "standardTable"
			 && element.nodeName && element.nodeName.toLowerCase() == "table"){//是表格
			 var tableStyle = null;
			 if($(element).attr("tableStyle")){//带有表格样式
				 //解析得到tbClassName
				 tableStyle = eval('('+$(element).attr("tableStyle").replace(/quot;/g,'"')+')');
			 }else{
				 var layout2col = $(element).attr("layout2col");
				 //对于移动端 未设置表格样式 并且以桌面形式展示，则使用默认样式
				 if( layout2col === "desktop"){
					 tableStyle = {
						 pathProfix: "sys/xform/designer/standardtable/tablestyle/default",
						 tbClassName: "tb_normal"
					 };
				 }
			 }
			 if(tableStyle){
				 var _pathProfix = tableStyle.pathProfix;
				 var _tbClassName = tableStyle.tbClassName;
				 if (_tbClassName === "tb_normal_noanyborder") {
					 _tbClassName = "tb_normal " + _tbClassName;
				 }
				 $(element).attr("class",_tbClassName);
				 $(element).attr("setTableStyle",true);
				 $(element).prev("[name='dynamicTableStyle']").remove();
				 $(element).before("<link rel='stylesheet' name='dynamicTableStyle' type='text/css' href='"+Com_Parameter.ContextPath+_pathProfix+"/standardtable.css'/>");
				 values.tableStyle=JSON.stringify(jQuery.extend({}, tableStyle)).replace(/"/g,"quot;");
			 }
		 }
	 }

	 /**
		 * 获取需要转换成上下布局的控件
		 * @param designer
		 * @returns
		 */
	function getNeedTransferControl(designer) {
		var allControls = getControls(designer);
		var needTransferControls = [];
		for (var i = 0; i < allControls.length; i++) {
			var control = allControls[i];
			if (control.needTransfer) {
				needTransferControls.push(control);
			}
		}
		return needTransferControls;
	}

	function transfer(designer,self) {
		var needTransferControls = getNeedTransferControl(designer);
		var mobileIFrame = getMobileIFrame();
		for (var i = 0; i < needTransferControls.length; i++) {
			if(shouldTransfer(needTransferControls[i],self)) {
				var currElement = needTransferControls[i].options.domElement;
				var tableControl = self.findTableControl(currElement);
				var textControlId = needTransferControls[i].options.values._label_bind_id;
				var _bindLabelText = textControlId ? true : false;
				var mobileTextControl;
				var mobileTextControl1;
				if (_bindLabelText) {
					mobileTextControl = self.getMobileControlById(textControlId)[0];
					if (!mobileTextControl){
						continue;
					}
					mobileTextControl1 =  constructMobileControlByPc(mobileTextControl,designer.builder,mobileIFrame);
					mobileTextControl.destroy();
				}
				var transferControl =  constructMobileControlByPc(needTransferControls[i],designer.builder,mobileIFrame);
				var rowIndex = $(currElement).closest("td").attr("row");
				tableControl.selectedDomElement = $(currElement).closest("td");
				var oldTdElement = tableControl.selectedDomElement;
				var table = tableControl.options.domElement;
				var currentRow = table.rows[parseInt(rowIndex)];
				self.attach(currentRow);
				var newRow = Designer_Mobile_AddRow({rowNum:1,columnNum:1});
				currElement = newRow.cells[0];
				if (_bindLabelText) {
					designer.builder.createControl(mobileTextControl1, currElement);
				}
				designer.builder.createControl(transferControl, currElement);
				needTransferControls[i].destroy();
				tableControl.selectedDomElement = oldTdElement;
				tableControl.isNeedConfirm = false;
				tableControl.deleteRow();
				tableControl.isNeedConfirm = true;
				designer.builder.clearSelectedControl();
			}
		}
	}

	function shouldTransfer(transferControl,designer) {
		if (!transferControl){
			return false;
		}
		var labelBindId = transferControl.options.values._label_bind_id;
		if (!labelBindId) {
			return false;
		}
		var currElement = transferControl.options.domElement;
		var tableControl = designer.findTableControl(currElement);
		if (!tableControl || tableControl.isDetailsTable) {
				return false;
		}
		if (tableControl) {
			var layout = tableControl.options.values.layout2col;
			if (layout == "desktop") {
				return false;
			}
		}

		var $currentTr = $(currElement).closest("tr");
		var controls = $currentTr.find("[formdesign='landray']");
		if (controls.length != 2) {
			return false;
		}
		var builder = designer.getMobileDesigner().builder;
		var shouldTransfer = false;
		controls.each(function(index,obj) {
			var fdType = $(obj).attr("fd_type");
			if (fdType== "textLabel") {
				var control = builder.getControlByDomElement(obj);
				if (control && control.options.values.id == labelBindId) {
					shouldTransfer = true;
				}
			}
		});
		return shouldTransfer;
	}

	/**
	 * 提交表单前做处理
	 * @returns
	 */
	window.MobileForm_BuildValueInConfirm = function (){
		//移动表单保存时，需要先保存选中表单的信息到对应的隐藏域（防止最后一个表单未保存）
		var useDefaultLayout = getUseDefaultLayout();
		if (useDefaultLayout) {
		/*	getAllMobileFormTr().each(function(){
				$(this).remove();
			});
			return;*/
		} else {
			//使用非默认布局,如果移动端表单为0,则先加载移动端表单
			if (getAllMobileFormTr().length === 0) {
				MobileDesigner.instance.loadForm();
			}
		}
		var tr = getMobileCheckedTr();
		var mobileDesigner = MobileDesigner.instance.mobileDesigner;
		var fdDesignerHtml,fdMetadataXml;
		if(mobileDesigner != null){
			fdDesignerHtml = mobileDesigner.getHTML();
			fdMetadataXml = mobileDesigner.getXML();
		}
		var mobileFdDesignerHtml = tr.find("input[name$='fdDesignerHtml']");
		var mobileFdMetadataXml = tr.find("input[name$='fdMetadataXml']");
		if(fdDesignerHtml != mobileFdDesignerHtml.val()){
			mobileFdDesignerHtml.val(fdDesignerHtml);
		}
		if(fdMetadataXml != mobileFdMetadataXml.val()){
			mobileFdMetadataXml.val(fdMetadataXml);
		}
		MobileDesigner.instance.setHtmlBeforeSubmit();
		//移除移动端的dom
		removeMobileElement();
		var mobileIFrame = getMobileIFrame();
		//加密表单源码
		getAllMobileFormTr().each(function(){
			var mobileHtml = $(this).find("input[name$='fdDesignerHtml']");
			mobileHtml.val(Xform_Base64Encodex(mobileHtml.val(), mobileIFrame, $(this)));
		});
	}

	/**
	 * 查找当前元素所在的表格控件(主表或者明细表)
	 * @param srcElement
	 * @returns
	 */
	function findTableControl(srcElement){
		var $table = $(srcElement).closest("table[fd_type]");
		var mobileDesigner = this.getMobileDesigner();
		var controls = getControls(mobileDesigner);
		var tableId = $table.attr("id");
		for (var i = 0; i < controls.length; i++) {
			if (tableId == controls[i].options.values.id) {
				return controls[i];
			}
		}
	}

	window.getTableDefinition = function(designer) {
		var controlDefinition = designer.controlDefinition;
		if (controlDefinition) {
			for (var type in controlDefinition) {
				if (type === "table") {
					return controlDefinition[type];
				}
			}
		}
	}

	/**
	 * 如果控件绑定了文本则自动添加一行,没有绑定则调用原来的创建控件的方法
	 * @param selectedControl 当前需要创建的控件
	 * @param currElement 当前插入的dom对象
	 * @param callback
	 * @returns
	 */
	function createControl(selectedControl, currElement,callback) {
		var mobileDesigner = this.getMobileDesigner();
		var mobileIFrame = getMobileIFrame();
		var control =  this.getPcControlById(selectedControl.options.values.id);
		if (!_isInSameScope(control,currElement)) return;
		//如果控件绑定了文本则自动添加一行
		var isAddRow = mobileDesigner.builder.isAddRow;

		var currentRow;
		if (mobileDesigner.builder.isAddRow) {
			var tableControl = this.findTableControl(currElement);
			if (tableControl) {
				var rowIndex = $(currElement).closest("td").attr("row");
				var rowIndexInt =parseInt(rowIndex);
				tableControl.selectedDomElement = $(currElement).closest("td");
				var table = tableControl.options.domElement;
				currentRow = table.rows[rowIndexInt];
				if (tableControl.isDetailsTable) {
					var tableDefi  = getTableDefinition(mobileDesigner);
					tableControl.insertRow = tableDefi.insertRow;
				}
				//容器类自动一行一个单元格
				if (selectedControl.container) {
					this.attach(currentRow);
					currentRow = Designer_Mobile_AddRow({rowNum:1,columnNum:1});
					currElement = currentRow.cells[0];
				} else {
					tableControl.insertRow();
					currentRow = table.rows[rowIndexInt==0?0:rowIndexInt];
					currElement = currentRow.cells[0];
					$(currElement).empty();
					var control =  this.getPcControlById(selectedControl.options.values._label_bind_id);
					var textControl = new mobileIFrame.Designer_Control(mobileDesigner.builder,"textLabel", false);
					textControl.options.values = control.options.values;
					mobileDesigner.builder.createControl(textControl, currElement);
					currElement = currentRow.cells[1] || currentRow.cells[0];
					$(currentRow.cells[1]).empty();
				}
				if (tableControl.isDetailsTable) {
					$(currentRow).attr("data-celltr","true");
				}
				mobileDesigner.builder.isAddRow = false;
			} else { //如果在表格外,暂时不支持
				var selector = "#mobileForm_" + selectedControl.options.values.id;
				$(selector).removeClass("xform_control_selected");
				_dialog_alert(config.lang.notSupportInsert);
				return;
			}
		}
		mobileDesigner.builder.createControl(selectedControl, currElement);
		if (selectedControl.type == "composite") {
			var childrens = selectedControl.__children || [];
			for (var i = 0; i < childrens.length; i++) {
				mobileDesigner.builder.createControl(childrens[i], selectedControl.options.domElement);
			}
			if (selectedControl.drawMobile) {
				selectedControl.drawMobile();
			}
		}
		mobileDesigner.builder.clearSelectedControl();
		if (isAddRow) {
			this.attach(currentRow || selectedControl.options.domElement);
		}
		//移除待选控件列表,并置待创建控件为空
		var selector = "#mobileForm_" + selectedControl.options.values.id;
		$(selector).remove();
		mobileDesigner.createSubformControl = '';
		//重新加载待选控件列表
		//this.loadControls();
	}

	/**
	 * 鼠标悬停事件,在鼠标移动的时候触发
	 * @param event
	 * @returns
	 */
	function mouseOver(event){
		var currElement = event.srcElement || event.target;
		var mobileDesigner = this.getMobileDesigner();
		if ($(currElement).closest("table[fd_type]").length > 0) {
			var currentRow = _findStandardTableRow(currElement);
			var dragBox = mobileDesigner.builder.mobileDragBox;
			if (!dragBox || !dragBox.attached) {
				if (currentRow) {
					__removeSelectedClass(mobileDesigner.builder.domElement);
					$(currentRow).addClass("xform_row_selected");
				}
			}
		}
	}

	/**
	 * 鼠标按下事件,选中行
	 * @param event
	 * @returns
	 */
	function mouseDown(event){
		var currElement = event.srcElement || event.target;
		var mobileDesigner = this.getMobileDesigner();
		if ($(currElement).closest("table[fd_type]").length > 0) {
			this.attach(currElement);
		}
	}

	/**
	 * 根据元素获取所在的主表行
	 * @param currentElement
	 * @returns
	 */
	function _findStandardTableRow(currentElement) {
		var currentRow = $(currentElement).closest("tr")[0];
		var $currentTable = $(currentElement).closest("table[formdesign='landray']");4
        var fdType = $currentTable.attr("fd_type");
		var isDetailTable =  (fdType == "detailsTable" || fdType == "seniorDetailsTable");
		if (isDetailTable) {
			currentRow = $currentTable.closest("tr.mobileTr")[0];
		}
		if (!currentRow) {
			currentRow = currentElement;
		}
		if($(currentRow).prop("tagName") != "TR"){
			currentRow = null;
		}
		if ($(currentRow).find("[id='designer_draw']").length > 0) {
			currentRow = null;
		}
		return currentRow;
	}

	/**
	 * 选中元素
	 * @param mobileDesigner
	 * @param mobileIFrame
	 * @param attachElement
	 * @returns
	 */
	function attach(attachElement) {
		var mobileDesigner = this.getMobileDesigner();
		var mobileIFrame = getMobileIFrame();
		var currentRow = _findStandardTableRow(attachElement);
		if (currentRow) {
			if (!mobileDesigner.builder.mobileDragBox) {
				mobileDesigner.builder.mobileDragBox = new mobileIFrame.Designer_Builder_DashBox(mobileDesigner.builder,'mobileDragBox');
			}
			var deleteIcon = $(mobileDesigner.builder.domElement).find(".mobileDeleteRowIcon");
			if(deleteIcon.length == 0) {
				deleteIcon = document.createElement("div");
				deleteIcon.title = config.lang.delete;
				deleteIcon.className = "mobileDeleteRowIcon";
				$(deleteIcon).attr("onclick","MobileDesigner.instance.bindEvent(event,'deleteRow')");
				$(mobileDesigner.builder.domElement).append(deleteIcon);
				mobileDesigner.builder.mobileDragBox.deleteIcon = deleteIcon;
			}
			$(deleteIcon).mouseover(function(){
				__removeSelectedClass(mobileDesigner.builder.domElement);
				if ($(currentRow).closest("table[fd_type='standardTable']").length > 0) {
					$(currentRow).addClass("xform_row_selected");
				}
			}).mouseout(function(){
				__removeSelectedClass(mobileDesigner.builder.domElement);
			});
			//附在行上
			mobileDesigner.builder.mobileDragBox.attach(currentRow);
			var area = mobileIFrame.Designer.absPosition(currentRow);
			$(deleteIcon).css("left",area.x + currentRow.offsetWidth - 33 + "px");
			$(deleteIcon).css("top",area.y - 2 + "px");
			_showAttach(mobileDesigner);
			__removeSelectedClass(mobileDesigner.builder.domElement);
			$(currentRow).addClass("xform_row_selected");
		}
	}

	function __removeSelectedClass(context) {
		$(context).find(".xform_row_selected").removeClass("xform_row_selected");
	}

	function _showAttach(mobileDesigner){
		if (mobileDesigner.builder.mobileDragBox) {
			mobileDesigner.builder.mobileDragBox.show();
		}
		$(mobileDesigner.builder.domElement).find(".mobileDeleteRowIcon").show();
	}

	function _hideAttach(mobileDesigner) {
		if (mobileDesigner.builder.mobileDragBox) {
			mobileDesigner.builder.mobileDragBox.hide();
		}
		$(mobileDesigner.builder.domElement).find(".mobileDeleteRowIcon").hide();
	}

	/**
	 * 添加控件时,判断控件插入的范围是否跟pc的一致,不一致则不允许添加
	 * @param pcControl 对应pc端的控件
	 * @param currentElement 当前移动端控件要插入的dom
	 * @returns
	 */
	function _isInSameScope(pcControl,currentElement) {
		var detailTableTypes = ["detailsTable", "advancedDetailsTable"];
		var standardTableType = "standardTable";
		if(pcControl.isDetailsTable) {
			return true;
		}
		var tableControlSelector = "table[fd_type]";
		var typeProperty = "fd_type";
		var idProperty = "id";
		var $pcControlClosestTable = $(pcControl.options.domElement).closest(tableControlSelector);
		var pcTableType = $pcControlClosestTable.attr(typeProperty);
		var pcTableId = $pcControlClosestTable.attr(idProperty);
		var $mobileControlClosestTable =  $(currentElement).closest(tableControlSelector);
		var mobileTableType = $mobileControlClosestTable.attr(typeProperty);
		var mobileTableId = $mobileControlClosestTable.attr(idProperty);
		if (detailTableTypes.indexOf(pcTableType) > -1) {
            //pc明细表内的控件不允许添加到移动明细表外
			if (detailTableTypes.indexOf(mobileTableType) < 0) {
				return false;
			} else {
                //pc明细表内的控件不允许添加到移动其它明细表内
				if (pcTableId != mobileTableId) {
					return false;
				} else {
					var $mobileTr = $(currentElement).closest("tr");
					var $mobileTd = $(currentElement).closest("td");
					var mobileTdType = $mobileTd.attr("type");
					var mobileTrType = $mobileTr.attr("type");
					var $pcTr = $(pcControl.options.domElement).closest("tr");
					var pcTrType = $pcTr.attr("type");
					//明细表标题行或者静态行的控件,不允许添加到内容行
					if (pcTrType === "titleRow" || pcTrType === "statisticRow") {
						if (mobileTrType != "statisticRow" && mobileTdType != "titleTd") {
							return false;
						}
					} else { //pc端的控件在内容行,不允许添加到静态行或者统计行
						if (mobileTrType == "statisticRow" || mobileTdType == "titleTd") {
							return false;
						}
					}
				}
			}
		}
		//pc是主表, 移动是明细表
		if (pcTableType === standardTableType) {
			if (detailTableTypes.indexOf(mobileTableType) > -1) { //pc明细表外的控件不允许添加到移动明细表内
				return false;
			}
		}
		return true;
	}

	/**
	 * pc端删除了控件,如果此控件唯一,则在移动端移除此控件
	 * @param pcControl 当前要删除的pc端控件
	 * @returns
	 */
	function destroyControlIfNecessary(pcControl){
		var pcDesigner = this.getPcDesigner();
		var mobileDesigner = this.getMobileDesigner();
		var unqiue = isUnique(pcControl,mobileDesigner,isUseSubForm());
		if (unqiue) {
			var mobileForms = findFormsByControl(pcControl,mobileDesigner);
			var currentCheckedTr = getMobileCheckedTr();
			for (var i = 0; i < mobileForms.length; i++) {
				this.checkedForm(mobileForms[i].form.link.closest("tr"));
				mobileForms[i].control.destroy();
			}
			this.checkedForm(currentCheckedTr);
		}
	}

	/**
	 * 根据控件获取所在的表单
	 * @param pcControl
	 * @returns {"control" : control,"form" : form}
	 */
	function findFormsByControl(control,designer) {
		var forms = [];
		var targetId = control.options.values.id;
		var cancidateForms = designer.subFormControls;
		for (var formId in cancidateForms) {
			var controls = cancidateForms[formId];
			for (var i = 0; i < controls.length; i++) {
				if(targetId === controls[i].options.values.id) {
					var form = findFormById(formId,designer);
					forms.push({"control":controls[i],"form":form});
				}
			}
		}
		return forms;
	}

	/**
	 * 根据表单id获取Form
	 * @param formId
	 * @param designer
	 * @returns
	 */
	function findFormById(formId,designer) {
		var subForms = designer.subForms || [];
		for (var i = 0;i < subForms.length; i++) {
			if (formId === subForms[i].id){
				return subForms[i];
			}
		}
	}

	/**
	 * 判断指定控件在pc中是否只存在一个表单中
	 * @param control
	 * @returns
	 */
	function isUnique(control,designer,isUseSubForm) {
		if (isUseSubForm) {
			//所有的pc表单控件
			var count = 0;
			var allPcForms = designer.subFormControls || [];
			for (var key in allPcForms) {
				var formControls = allPcForms[key];
				if(__isExit(control,formControls)) {
					count++;
				}
				if (count == 2) {
					return false;
				}
			}
		}
		return true;
	}


	/**
	 * 提交前,移除移动端元素
	 * @returns
	 */
	function removeMobileElement(){
		var $mobileTrs = getMobileFormTable().find("tr[ischecked]");
		var $mobileHtmlDiv = $("#xform_" + config.fdKey + "_preview");
		$mobileTrs.each(function(index,tr){
			var mobileDesignerHtml = $(tr).find("input[name$='fdDesignerHtml']");
			$mobileHtmlDiv.html(mobileDesignerHtml.val());
			$mobileHtmlDiv.find("div[mobileEle='true']").remove();
			$mobileHtmlDiv.find("[__hiddenInMobile='true']").show();
			$mobileHtmlDiv.find(".mobileDeleteRowIcon").remove();
			recoveryJSPContent($mobileHtmlDiv);
			mobileDesignerHtml.val($mobileHtmlDiv.html());
		});
	}

	/**
	 * 恢复移动端jsp控件的内容
	 */
	function recoveryJSPContent($mobileHtmlDiv){
		var pcDesigner =  MobileDesigner.instance.pcDesigner;
		if (!pcDesigner) {
			return;
		}
		var $jspDoms = $mobileHtmlDiv.find("div[fd_type='jsp']");
		$jspDoms.each(function(index,jspDom){
			var jspControl = MobileDesigner.instance.getPcControlById($(jspDom).attr("id"));
			if(!jspControl || jspControl.length == 0){
				jspControl = MobileDesigner.instance.getMobileControlById($(jspDom).attr("id"));
			}
			var oJspDom;
			if(jspControl && typeof jspControl == 'object'){
				if(jspControl.options){
					oJspDom = jspControl.options.domElement;
				}
			}else if(jspControl && jspControl.length > 0){
				oJspDom = jspControl[0].options.domElement;
			}
			if(oJspDom){
				var oContent = $(oJspDom).find("input").val();
				$(jspDom).find("input").val(oContent);
			}
		});
	}

	 /**
	  * 移除一些无用的元素
	  * @returns
	  */
	 function removeInvalidElement(){
		 var mobileDesigner = this.getMobileDesigner();
		 $("table[fd_type='detailsTable']",mobileDesigner.builderDomElement).find("tr[kmss_iscontentrow='1']").remove();
         $("table[fd_type='seniorDetailsTable']",mobileDesigner.builderDomElement).find("tr[kmss_iscontentrow='1']").remove();
		 this.removeTdWidth();
	 }

	 function modifyStandTableWidth(mobileDesigner) {
		 var context = mobileDesigner.builderDomElement;
		 var pcDesigner = mobileDesigner.pcDesigner;
		 if (pcDesigner) {
			 var pcContext = pcDesigner.builderDomElement;
			 var $standardTable = $("table[fd_type='standardTable']",context);
			 $standardTable.css("width","100%");
			 $standardTable.each(function(index,obj){
				 var layout2col = $(obj).attr("layout2col");
				 if (layout2col == "desktop") {
					 var tableStyle = obj.getAttribute('tableStyle');
					 if (tableStyle) {
						 tableStyle = tableStyle.replace(/quot;/ig,"\"");
						 tableStyle = eval('(' + tableStyle + ')');
						 var pathProfix = tableStyle.pathProfix;
						 var cssUrl = Com_Parameter.ContextPath + pathProfix + "/standardtable.css";
						 $(context).append("<link rel='stylesheet' name='dynamicTableStyle' type='text/css' href='" + cssUrl +  "'/>");
					 }
				 }
			 });
		 }
	 }

	 function _UnEnscapeFdValues(fdValues){
			if (fdValues == null || fdValues.trim() =='') return '';
			fdValues = fdValues.replace(/\r\n/ig,'\\r\\n');
			fdValues = fdValues.replace(/&#;/ig,'\\r\\n');
			fdValues = fdValues.replace(/\\quot;/ig,'\\\\quot;');
			fdValues = fdValues.replace(/&#92;/ig,'\\\\');
			return fdValues;
		}

	 /**
	  * 移除宽度
	  * @returns
	  */
	 function removeTdWidth(){
		 var mobileDesigner = this.getMobileDesigner();
		$(mobileDesigner.builderDomElement).find("td").removeAttr("width").css("width","");
	 }

	/**
	 * 将pc端的designerHtml转成移动端布局的designerHtml
	 */
	window.requestGenerateMobileHtml = function(arg) {
		var result = null;
		$.ajax({
			url: generateMobileHtmlUrl,
			async: false,
			data: arg,
			type: "POST",
			dataType: 'json',
			success: function (data) {
				result = data;
			},
			error: function (er) {
				console.error("generate mobile form html error : " + er)
			}
		});
		return result;
	}

	/**
	 * 调整属性,后台解析html时会移除掉formdesign,故加上__formdesign来标识
	 * @param attr
	 * @param designer
	 * @returns
	 */
	function __AdjustAttr(attr,designer){
		var context = designer.builder.domElement;
		$(context).find("[__" + attr + "]").each(function(index,obj){
			var val = $(obj).attr("__" + attr);
			$(obj).attr(attr,val);
		});
	}

	/**
	 * 表单源码解密
	 * @param arg
	 * @returns
	 */
	function requestDecodeHtml(arg) {
		var result = null;
		$.ajax({
			url: Com_Parameter.ContextPath + "sys/xform/sys_xform/sysFormTemplateHtml.do?method=deCodeHtml",
			async: false,
			data: arg,
			type: "POST",
			dataType: 'json',
			success: function (data) {
				result = data;
			},
			error: function (er) {
				console.error("表单源码解密异常 :  " + er);
			}
		});
		return result;
	}

	function setHtmlBeforeSubmit(){
		var pcDesigner = this.getPcDesigner();
		if (pcDesigner == null) {
			return;
		}
		var useDefaultLayout = getUseDefaultLayout();
		var mobileDesigner = this.getMobileDesigner();
		this.__isShowTip = false;
		if (isUseDefineForm()) {
			var pcFormId =  document.getElementsByName(config.formPrefix + "fdId")[0].value;
			if (!useDefaultLayout) return;
			if (isSkipAutoLoad(this.delFormIds,pcFormId)) return;
			if (this.mobileFormIsLoaded(pcFormId)) {
				var $mobileTr = getMobileTrByPcId(pcFormId);
				var mobileId = $mobileTr.attr("id");
				var mobileFormControls = getFormControlsByDesigner(mobileDesigner,mobileId);
				if (mobileFormControls.length <= 1 || useDefaultLayout) {
					var $pcTr = getPcFormTrByMobileTr($mobileTr);
					this.copyFormByPc($pcTr);
					return;
				} else {
					return;
				}
			} else {
				this.addMobileFormByPc();
			}

		}
		if (isUseSubForm()) {
			var forms = this.getSubFormTrs();
			var self = this;
			var url = generateIDUrl + "?method=batchGenerateId&size=" + forms.length;
			$.ajax({
				type : "GET",
				url : url,
				async : false,
				success : function(data){
					if(data){
						var ids = data.split(";");
						for (var i = 0; i < forms.length; i++) {
							var fieldValues = {};
							var trId = $(forms[i]).attr("id");
							var pcFormId = trId;
							if (pcFormId === pcDefaultSubFormKey) {
								pcFormId = getPcDefaultId();
							}
							if (!useDefaultLayout) continue;
							if (isSkipAutoLoad(self.delFormIds,pcFormId)) continue;

							if (self.mobileFormIsLoaded(pcFormId)) {
								var $mobileTr = getMobileTrByPcId(pcFormId);
								var mobileId = $mobileTr.attr("id");
								var mobileFormControls = getFormControlsByDesigner(mobileDesigner,mobileId);
								if (mobileFormControls.length <= 1 || useDefaultLayout) {
									var $pcTr = getPcFormTrByMobileTr($mobileTr);
									self.copyFormByPc($pcTr);
								}
								continue;
							}
							fieldValues[config.formPrefix + "fdMobileForms[!{index}].fdId"] = ids[i];
							var name = self.getFormNameByTrId(trId);
							fieldValues[config.formPrefix +  "fdMobileForms[!{index}].fdName"] = name;
							fieldValues[config.formPrefix + "fdMobileForms[!{index}].fdIsDefWebForm"] = true;
							fieldValues[config.formPrefix + "fdMobileForms[!{index}].fdPcFormId"] = pcFormId;
							var newRow = DocList_AddRow("table_docList_" + config.fdKey + "_form",null,fieldValues);
							$(newRow).find("a[name='mobileFormText']").text(name);
							$(newRow).attr("id",ids[i]);
							var $pcTr = getPcFormTrByMobileTr($(newRow));
							self.copyFormByPc($pcTr);
						}
					}
				}
			});
		}
	}

	function isSkipAutoLoad(delFormIds,pcFormId) {
		if (!delFormIds) {
			return false;
		}
		for(var i = 0; i < delFormIds.length; i++) {
	      if (pcFormId === delFormIds[i]) {
	        return true;
	      }
	    }
		return false;
	}

	/**
	 * 加载表单
	 */
	function load(){
		//已切换过页签不加载
		if(!isLoadForm(this)) {
			this.loadMobileFormObj();
			//加载控件
			this.loadControls();
			return;
		}
		this.loadForm();
	}

	function isLoadForm(designer) {
		if (designer.__clicked) {
			return false;
		} else {
			//第一次切换
			var method = Com_GetUrlParameter(location.href,"method");
			//编辑页面且非默认布局不加载
			var mobileFormTrs = getAllMobileFormTr();
			if (method.indexOf("edit") > -1 && !designer.useDefaultLayout
					&& mobileFormTrs.length != 0) {
				//设置选中的表单
				designer.checkedFormByPc();
				//将表单存在数组中
				return false;
			}
		}
		return true;
	}

	/**
	 * 加载左侧表单
	 */
	function loadForm(){
		var pcDesigner = this.getPcDesigner();
		var mobileDesigner = this.getMobileDesigner();
		var useDefaultLayout = getUseDefaultLayout();
		if (isUseDefineForm()) {
			var pcFormId =  document.getElementsByName(config.formPrefix + "fdId")[0].value;
			if (isSkipAutoLoad(this.delFormIds,pcFormId)) return;
			if (this.mobileFormIsLoaded(pcFormId)) {
				var $mobileTr = getMobileTrByPcId(pcFormId);
				var designerHtml = $mobileTr.find("input[name$='fdDesignerHtml']").val();
				var metaDataXml = $mobileTr.find("input[name$='fdMetadataXml']").val();
				var mobileId = $mobileTr.attr("id");
				var mobileFormControls = getFormControlsByDesigner(mobileDesigner,mobileId);
				if (mobileFormControls.length <= 1 && (metaDataXml == ""
					|| metaDataXml.replace(/\r\n|\n/g,"") == "<model ></model>")) {
					var $pcTr = getPcFormTrByMobileTr($mobileTr);
					this.copyFormByPc($pcTr);
				}
				this.checkedFormByPc();
				this.loadMobileFormObj();
				return;
			}
			this.addMobileFormByPc();
			this.loadMobileFormObj();
		}
		if (isUseSubForm()) {
			var forms = this.getSubFormTrs();
			var self = this;
			var url = generateIDUrl + "?method=batchGenerateId&size=" + forms.length;
			$.ajax({
				type : "GET",
				url : url,
				success : function(data){
					if(data){
						var ids = data.split(";");
						for (var i = 0; i < forms.length; i++) {
							var fieldValues = {};
							var trId = $(forms[i]).attr("id");
							var pcFormId = trId;
							if (pcFormId === pcDefaultSubFormKey) {
								pcFormId = getPcDefaultId();
							}
							if (isSkipAutoLoad(self.delFormIds,pcFormId)) continue;
							if (self.mobileFormIsLoaded(pcFormId)) {
								var $mobileTr = getMobileTrByPcId(pcFormId);
								var mobileId = $mobileTr.attr("id");
								var metaDataXml = $mobileTr.find("input[name$='fdMetadataXml']").val();
								var mobileFormControls = getFormControlsByDesigner(mobileDesigner,mobileId);
								if (mobileFormControls.length <= 1 && (metaDataXml == ""
									|| metaDataXml.replace(/\r\n|\n/g,"") == "<model ></model>")) {
									var $pcTr = getPcFormTrByMobileTr($mobileTr);
									self.copyFormByPc($pcTr);
								}

							} else {
								self.copyFormByPc($(forms[i]));
                                if(__xform.scrollHeight > __xform.clientHeight){
                                    $(__xform).scrollTop( __xform.scrollHeight );
                                }
                            }
						}
						//设置选中的表单
						self.checkedFormByPc();
						//将表单存在数组中
						self.loadMobileFormObj();
					}
				}
			});
		}
	}

	/**
	 * 将移动端表单存到数组中
	 * @returns
	 */
	function loadMobileFormObj(){
		var $mobileCheckedTr = getMobileCheckedTr();
		var $mobileFormTable = getMobileFormTable();
		var mobileDesigner = this.getMobileDesigner();
		for (var i = 0;i < mobileDesigner.subForms.length; i++) {
			if (!mobileDesigner.subForms[i].isMobile) {
				mobileDesigner.subForms.splice(i, 1);
			}
		}
		if($mobileFormTable.find('tr[ischecked]').length >= 1){
			$mobileFormTable.find('tr').each(function(){
				$(this).find("a[name='mobileFormText']").click();
				var mySubform = {};
				mySubform.id = $(this).attr("id");
				var isExit = false;
				for (var i = 0;i < mobileDesigner.subForms.length; i++) {
					if (mySubform.id == mobileDesigner.subForms[i].id) {
						isExit = true;
						mySubform = mobileDesigner.subForms[i];
						break;
					}
				}
				mySubform.link = $(this).find("[name='mobileFormText']");
				mySubform.fdDesignerHtmlObj = $(this).find("input[name$='fdDesignerHtml']");
				mySubform.isMobile = true;
				if (!isExit) {
					mobileDesigner.subForms.push(mySubform);
				}
			});
			$mobileCheckedTr.find("a[name='mobileFormText']").click();
		}
	}

	function __hideAddButtonIfNecessary(){
		if (isUseDefineForm()) {
			$("#xform_" + config.fdKey + "_addMobileForm").hide();
		}
	}

	function __processMobileForm() {
		if (isUseDefineForm()) {
			$("#xform_" + config.fdKey + "_title").text(config.lang.control);
			__xform.hide();
			$controlTitle.css("margin-top","10px");
		}
		if (isUseSubForm()) {
			$("#xform_" + config.fdKey + "_title").text(config.lang.form);
			__xform.show();
		}

	}

	/**
	 * 调整左侧面板高度
	 * @returns
	 */
	function _AdjustFormPanelHeight(){
		var height = document.getElementById("TD_MobileFormTemplate_" + config.fdKey).offsetHeight - 2;
		var myhight = height - 120;
		var div = document.getElementById("xform_" + config.fdKey + "_left");
		if (isUseSubForm()) {
			__xform.css("height",myhight*0.4);
			__$control.css("height",myhight*0.6);
			$(div).css("height",height);
		}
		if (isUseDefineForm()) {
			__$control.css("height","98%");
			$(div).css("height",810);
		}
	}


	/**
	 * 调整绘制面板高度
	 */
	function _AdjustDrawPanelHeight(){
		var mobileIFrame = getMobileIFrame();
		if(mobileIFrame.XForm_AdjustViewHeight) {
			mobileIFrame.XForm_AdjustViewHeight(mobileIFrame);
			 mobileIFrame.height = 564;
//			$(mobileIFrame.document).find("div[id='designPanel']").css("overflow-x","hidden");
			$(mobileIFrame.document.body).css("font-size","10px");
			$(mobileIFrame.document.body).css("border","1px solid #DDDDDD");
			$(mobileIFrame.document).find("div[id='designPanel']").attr("style","");
		}
	}

	function MobileForm_filterXml(str){
		if(!str){
			return "";
		}
		//将xml中连续两个以上的空格 替换为 一个
		str=str.replace(/\s{2,}/g," ");
		//替换掉换行
		str=str.replace(/[\r\n]+/g," ");
		//将xml中label 属性去除。（数据字典中label属性的改变不列如入默认作为新模版的条件）
		str=str.replace(/\s+label=\s*[\S]+\s*/gi," ");
		return str;
	}

	/**
	 * 切换表单后,调整
	 * @param designer
	 * @returns
	 */
	function MobileForm_afterSwitch(designer){
		// 切换表单后调整虚线框位置
		designer.builder.resetDashBoxPos();
		//取消工具栏选中状态
		designer.builder.owner.toolBar.cancelSelect();
		//恢复鼠标样式
		designer.builder.domElement.style.cursor = 'default';
		//清掉载入功能待选控件对象
		designer.createSubformControl = "";
		//关闭开着的属性面板
		var attrPanel = designer.attrPanel;
		var treePanel = designer.treePanel;
		if (!treePanel.isClosed){
			treePanel.close();
			//关闭权限区段
			getMobileIFrame().Designer_Control_Rigth_LoopRightControls(designer.builder.controls, function(c) {
				c.showRightConfig(false);
				c.showRightDefaultCell();
			});
		}
		if (!attrPanel.isClosed){
			attrPanel.close();
		}
	}

	/**
	 * 添加空的移动端表单
	 * @returns
	 */
	var mobileFormItem = 1;
	function addMobileForm(){
		var url = generateIDUrl + "?method=generateID";
		var self = this;
		$.ajax({
			type : "GET",
			url : url,
			success : function(data){
				if(data){
					var xform = {};
					xform.id = data;
					xform.name = config.lang.mobileForm + mobileFormItem;
					xform.designerHtml = "";
					xform.metadataXml = "";
					xform.pcFormId = "";
					__addRow(xform,self,false);
					mobileFormItem++;
					__bindMouseFun(xform.id);
				}
			}
		});
	}

	function __bindMouseFun(id) {
//		var $tr = getMobileFormTable().find("tr[id='" + id + "']");
//		$tr.attr("onmouseover","MobileDesigner.instance.bindEvent(event,'formMouseOver')");
//		$tr.attr("onmouseout","MobileDesigner.instance.bindEvent(event,'formMouseOut')");
	}

	function formMouseOver(src) {
		var $currentTr = $(src).closest("tr[ischecked]");
		$currentTr.find(".xform_operation").show();
	}

	function formMouseOut(src) {
		var $currentTr = $(src).closest("tr[ischecked]");
		$currentTr.find(".xform_operation").hide();
	}

	/**
	 * 复制移动端表单
	 * @returns
	 */
	function copyMobileForm(src){
		var $currentTr = $(src).closest("tr[ischecked]");
		var pcDesigner = this.getPcDesigner();
		this.checkedForm($currentTr);
		__copyForm.call(this,$currentTr[0]);
	}

	function __copyForm(copyDom){
		var url = generateIDUrl + "?method=generateID";
		var self = this;
		$.ajax({
			type : "GET",
			url : url,
			success : function(data){
				if(data){
					var xform = {};
					xform.id = data;
					xform.name = config.lang.mobileForm + mobileFormItem;
					xform.designerHtml = $(copyDom).find("[name$=fdDesignerHtml]").val();
					xform.metadataXml = $(copyDom).find("[name$=fdMetadataXml]").val();
					xform.pcFormId = "";
					__addRow(xform,self,false);
					mobileFormItem++;
					__bindMouseFun(xform.id);
				}
			}
		});
	}

	/**
	 * 导入pc表单
	 * @param src
	 * @returns
	 */
	function copyFormByPc(src){
		var $currentTr = $(src).closest("tr[ischecked]");
		var pcDesigner = this.getPcDesigner();
		if (SubForm_SetChecked) {
			if (isUseSubForm()) {
				var $trObjs = getSubFormTrs();
				if ($trObjs.length > 1) {
					var $pcCheckedTr = getSubFormCheckedTr();
					$trObjs.each(function(index,dom){
						SubForm_SetChecked($(dom),false);
					});
					SubForm_SetChecked($pcCheckedTr,false);
				}
			}
		}
		var xform = {};
		xform.id = $currentTr.attr("id");
		if(xform.id === pcDefaultSubFormKey) {
			xform.id = getPcDefaultId();
		}
		xform.pcFormId = xform.id;
		xform.name =  $currentTr.find("input[name$='fdName']").val();
		xform.designerHtml = $currentTr.find("input[name$='fdDesignerHtml']").val();
		xform.metadataXml =  $currentTr.find("input[name$='fdMetadataXml']").val();
		if (isUseDefineForm() || (isUseSubForm() && getSubFormTrs().length == 1)) { //单表单,从设计器里面获取更可靠
			xform.designerHtml = pcDesigner.getHTML();
			xform.metadataXml = pcDesigner.getXML();
		}
		if (this.mobileFormIsLoaded(xform.id)) {
			var moblieFormTr = getMobileTrByPcId(xform.id);
			this.checkedForm(moblieFormTr);
			var mobileHtml = this.setHTML(xform.designerHtml,true);
			moblieFormTr.find("input[name$='fdDesignerHtml']").val(mobileHtml);
			moblieFormTr.find("input[name$='fdMetadataXml']").val(xform.metadataXml);
		} else {
			var url = generateIDUrl + "?method=generateID";
			var self = this;
			$.ajax({
				type : "GET",
				url : url,
				async : false,
				success : function(data){
					if(data){
						xform.id = data;
						__addRow(xform,self,true);
						__bindMouseFun(xform.id);
					}
				}
			});
		}
	}

	function copyPcFormToMobile(src){
		this.copyFormByPc(src);
		_dialog_alert(config.lang.copySuccess);
	}

	function _dialog_alert(msg){
		seajs.use(['lui/dialog'], function(dialog) {
			dialog.alert(msg);
		});
	}
	
	//更改移动端名字
	function editFormName(src) {
		var parent = $(src).parents("tr[ischecked]");
		parent.find("a[name='mobileFormText']").hide();
		parent.find("input[name$='fdName']").show();
		parent.find("input[name$='fdName']").select();
		this.checkedForm(parent,false,null);
	}
	
	/**
	 * 自定义表单添加默认表单(新建按钮点击事件)
	 * @param dom
	 * @param isCopy
	 * @returns
	 */
	function addMobileFormByPc(){
		var url = generateIDUrl + "?method=generateID";
		var self = this;
		$.ajax({
			type : "GET",
			url : url,
			async : false,
			success : function(data){
				if(data){
					var xform = {};
					var pcDesigner = self.getPcDesigner();
					xform.id = data;
					xform.name  = defaultMobileFormName;
					xform.designerHtml = pcDesigner.getHTML();
					xform.metadataXml = pcDesigner.getXML();
					xform.pcFormId =  document.getElementsByName(config.formPrefix + "fdId")[0].value;
					__addRow(xform,self,true);
				}
			}
		});
	}
	
	function __loading(){
		if (typeof(seajs) != 'undefined') {
			seajs.use(['lui/dialog'], function(dialog) {
				window._load = dialog.loading();
			});
		}
	}
	
	function __hide() {
		if (typeof window._load != "undefined") {
			window._load.hide(); 
		}
	}
	
	function __addRow(xform,self,isRequestMobile){
		var fieldValues = {};
		fieldValues[config.formPrefix + "fdMobileForms[!{index}].fdId"] = xform.id;
		fieldValues[config.formPrefix + "fdMobileForms[!{index}].fdName"] = xform.name;
		fieldValues[config.formPrefix + "fdMobileForms[!{index}].fdIsDefWebForm"] = true;
		fieldValues[config.formPrefix + "fdMobileForms[!{index}].fdDesignerHtml"] = xform.designerHtml;
		fieldValues[config.formPrefix + "fdMobileForms[!{index}].fdMetadataXml"] = xform.metadataXml;
		fieldValues[config.formPrefix + "fdMobileForms[!{index}].fdPcFormId"] = xform.pcFormId;
		var tbId = "table_docList_" + config.fdKey + "_form";
		var tbInfo = DocList_TableInfo[tbId];
		if (!tbInfo) return;
		var newRow = DocList_AddRow(tbId,null,fieldValues);
		$(newRow).find("a[name='mobileFormText']").text(xform.name);
		$(newRow).attr("id", xform.id);
		var $input = $(newRow).find("input[name$='fdName']").hide();
		self.checkedForm($(newRow),false,null);
		if (xform.designerHtml) {
			if (isRequestMobile) {
				__loading();
			}
			var mobileHtml = self.setHTML(xform.designerHtml,isRequestMobile);
			__hide();
			if (isRequestMobile) {
				$(newRow).find("[name$='fdDesignerHtml']").val(mobileHtml);
				$(newRow).find("[name$='fdMetadataXml']").val(xform.metadataXml);
				if (typeof self.__isShowTip != "undefined" && self.__isShowTip) {
					if (typeof(seajs) != 'undefined') {
						seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
							if (mobileHtml) {
								dialog.success(config.lang.loadSuccess);
							} else {
								dialog.failure(config.lang.loadFailed);
							}
							self.__isShowTip = false;
						});
					}
				}
			}
			//重新设置待选控件列表
			self.loadControls();
			//加载表单对象
			self.loadMobileFormObj();
		}
		self.removeInvalidElement();
		if(__xform.scrollHeight > __xform.clientHeight){
			__xform.scrollTop( __xform.scrollHeight );
		}
	}
	
	/**
	 * 删除移动端表单
	 * @param dom
	 * @returns
	 */
	 function deleteMobileForm(dom){
		var self = this;
		if (typeof(seajs) != 'undefined') {
			seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
				dialog.confirm(config.lang.confirmDelete,function(data){
					if (data){
						var mobileDesigner = self.getMobileDesigner();
						//删除行
						var parent = $(dom).parents("tr[ischecked]");
						var delId = parent.find("[name$='fdPcFormId']").val();
						self.delFormIds.push(delId);
						DocList_DeleteRow(parent[0]);
						var tr = getMobileCheckedTr();
						if(tr.length == 0){
							var defaultTr = getMobileFormTable().find('tr:eq(0)');
							self.checkedForm(defaultTr);
							$('#MobileFormDesignerDiv').scrollTop(0);
						}
					}
				});
			});
		}
	}
	
	/**
	 * 判断控件是否已经存在
	 * @param control
	 * @param allPcUniqueControl
	 * @returns
	 */
	function __isExit(control,allPcUniqueControl){
		for (var i = 0; i < allPcUniqueControl.length; i++) {
			if (control.options.values.id === allPcUniqueControl[i].options.values.id){
				return true;
			}
		}
		return false;
	}

	/**
	 * 加载不存在当前表单的控件
	 * @returns
	 */
	var $controlTitle = $("#xform_" + config.fdKey + "_control_title");
	function loadControls(){
		var mobileDesigner = this.getMobileDesigner();
		var pcDesigner = this.getPcDesigner();
		var mobileIFrame = getMobileIFrame();
		var $mobileFormTable = getMobileFormTable();
		//清空控件div中信息
		__$control.html("");
		var $tr = getMobileCheckedTr();
		var curMobileFormId = $tr.attr("id");
		if (!curMobileFormId) return;
		//todo
		var controlsObj = getCancidateControl(this,curMobileFormId);
		var size = controlsObj.size;
		var otherFormControls = controlsObj.cancidateControls
		if(size > 0 && isUseSubForm()){
			$controlTitle.show();
		}else{
			$controlTitle.hide();
		}
		for (var key in otherFormControls) {
			var control = otherFormControls[key];
			if (!__isAllowShow(control,otherFormControls)) continue;
			var text = __getControlText(control);
			var pControl;
			if (otherFormControls[key].parent && (otherFormControls[key].parent.isDetailsTable ||
					"composite" === otherFormControls[key].parent.type)) {
				var $detaileTable = __$control.find("[id='mobileForm_" + control.parent.options.values.id + "']");
				if ($detaileTable.length == 0) {
					__$control.append(__buildControlHtml(control.parent,mobileDesigner));
				}
				$detaileTable = __$control.find("[id='mobileForm_" + control.parent.options.values.id + "']");
				$detaileTable.append(__buildControlHtml(control,mobileDesigner));
			} else if(pControl = __isControlInMutiTab(otherFormControls[key])){
				var $mutiTab = __$control.find("[id='mobileForm_" + pControl.options.values.id + "']");
				if ($mutiTab.length == 0) {
					__$control.append(__buildControlHtml(pControl,mobileDesigner));
				}
				$mutiTab = __$control.find("[id='mobileForm_" + pControl.options.values.id + "']");
				$mutiTab.append(__buildControlHtml(control,mobileDesigner));
			} else {
				__$control.append(__buildControlHtml(otherFormControls[key],mobileDesigner));
			}
		}
		
		//绑定展开折叠事件
		__$control.find(".model-sidebar-desc").click(function(){
			var $parent = $(this).parent(".xform_parent_control");
			var isHide = $parent.hasClass("item-expansion");
			if (isHide) {
				$parent.removeClass("item-expansion");
				$parent.find(".xform_child_control").show();
			} else {
				$parent.addClass("item-expansion");
				$parent.find(".xform_child_control").hide();
			}
		});
		
		//拖动
		/*var context = $("#MobileFormTemplate_" + config.fdKey + "_wrap");
		var $designerDraw = $("[id='designer_draw']",context);
		__$control.sortable({
		    cursor : "move",  
			items : "div",
			connectWith : $("[id='designer_draw']",getMobileIFrame().document), //,
			opacity : 0.6,
			placeholder: "ui-state-highlight"
	     });*/
	}
	
	//获取控件待选列表
	function getCancidateControl(designer,curMobileFormId) {
		var mobileDesigner = designer.getMobileDesigner();
		var pcDesigner = designer.getPcDesigner();
		var allPcFormControls = designer.getAllFormControlsByDesigner(pcDesigner);
		//当前选中表单有的控件
		var curFormControls = [];
		if (isUseSubForm()) {
			curFormControls = mobileDesigner.subFormControls[curMobileFormId] ;
		}
		if (isUseDefineForm()) {
			curFormControls = concat(curFormControls,getControls(mobileDesigner));
		}
		//其他表单有的控件 id,control
		var otherFormControls = {};
		//当前选中表单未有的控件ids
		var needControlIds = [];
        curFormControls = curFormControls || [];
		for(var i = 0;i < allPcFormControls.length; i++){
			var isExit = false;
			var pcFormControlId = allPcFormControls[i].options.values.id;
			if (!pcFormControlId) continue;
			if (pcFormControlId.indexOf(".") > 0) {
				pcFormControlId = pcFormControlId.substring(pcFormControlId.indexOf(".") + 1)
			}
			for (var j = 0; j < curFormControls.length; j++) {
				var curFormControlId = curFormControls[j].options.values.id;
				if (!curFormControlId) continue;
				if (curFormControlId.indexOf(".") > 0) {
					curFormControlId = curFormControlId.substring(curFormControlId.indexOf(".") + 1)
				}
				if (pcFormControlId === curFormControlId){
					isExit = true;
					break;
				}
			}
			if(!isExit) {
				otherFormControls[pcFormControlId] = allPcFormControls[i];
				needControlIds.push(pcFormControlId);
			}
		}
		return {cancidateControls: otherFormControls, size: needControlIds.length};
	}
	
	/**
	 * 获取控件显示文本
	 * @param control
	 * @returns
	 */
	function __getControlText(control) {
		var text = "";
		if (control) {
			text = control.options.values.label || control.options.values.content;
			if (!text) {
				//没有文本值,则用id显示
				text = control.options.values.id;
			}
		}
		return text;
	}
	
	/**
	 * 判断控件是否出现带待选控件列表中
	 * @returns
	 */
	function __isAllowShow(control,otherFormControls){
		var isAllowShow = true;
		//如果文本控件被其它控件绑定了，则不显示在控件列表中
		if (__isLabelBindControl(control,otherFormControls)) {
			isAllowShow = false;
		}
		//如果是多标签中的主表,则不显示在控件列表中
		if (__isTableInMutiTab(control)) {
			isAllowShow = false;
		}
		//如果是div控件，并且设置为移动端无效，则不出现在控件列表中
		if (control && control.type === "divcontrol" && (
				!control.options.values.isSupportMobile 
				|| control.options.values.isSupportMobile == "disable")) {
			isAllowShow = false;
		}
		return isAllowShow;
	}
	
	/**
	 * 判断表格是否在多标签中
	 * @param control
	 * @returns
	 */
	function __isTableInMutiTab(control) {
		if (control && control.type === "standardTable") {
			if (control.parent && control.parent.type === "mutiTab") {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断组件是否在多页签中，并获取父元素（多页签）
	 * @param control
	 * @returns
	 */
	function __isControlInMutiTab(control){
		var parent = control.parent;
		while(parent && parent.type){
			if(parent.type === 'mutiTab'){
				return parent;
			}
			parent = parent.parent;
		}
		return null;
	}
	
	/**
	 * 构建控件待选控件html
	 * @param style
	 * @param control
	 * @param mobileDesigner
	 * @returns
	 */
	function __buildControlHtml(control,mobileDesigner){
		var key = control.options.values.id;
		var text = control.options.values.label || control.options.values.content;
		if (control.type == "prompt") {
			text = "提示控件";
		}
		if (!text) {
			//没有文本值,则用id显示
			text = control.options.values.id;
		}
		var isAllowAdd = __isAllowAdd(control,mobileDesigner);
		var conDefn = mobileIFrame.Designer_Config.controls[control.type];
		if (!conDefn) return;
		var type = conDefn.info.name;
		var controlCla;
		var labelCla = "xform_control_label";
		var hasParent = false;
		if (control.parent && (control.parent.isDetailsTable
			|| "composite" === control.parent.type || __isControlInMutiTab(control))) {
			controlCla = "xform_child_control";
			labelCla += " xform_child_control_label";
		} else {
			controlCla= "xform_parent_control";
		}
		if (control.isDetailsTable || control.type === "composite"
			|| control.type === "fragmentSet" ||  control.type === "mutiTab") {
			controlCla += " model-item-expansion";
			labelCla  += " model-sidebar-desc";
		}
		var controlInfo = '<div id="mobileForm_' + key +'" class="' + controlCla + '" controlId="' + key  + '"' 
		if (isAllowAdd) {
			controlInfo += ' onclick="MobileDesigner.instance.bindEvent(event,\'controlClick\');"' +
				' onmouseover="controlMouseover(this);" onmouseout="controlMouseout(this);" ' 
		}
		controlInfo += "><span class='" + labelCla + "' title='" + text + "'>"  + text +  '</span><span class="xform_control_type">[' + type + ']</span></div>';
		
		return controlInfo;
	}
	
	window.controlMouseover = function (self){
		$(self).addClass("xform_control_mouseOver");
	}

	window.controlMouseout = function (self){
		$(self).removeClass("xform_control_mouseOver");
	}
	
	/**
	 * 判断文本控件是否绑定了控件
	 * @param control
	 * @returns
	 */
	function __isLabelBindControl(control,otherControls){
		if (control && control.type === "textLabel") {
			if(isBindControl(control.options.values.id,otherControls)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断文本控件是否被其它控件绑定了
	 * @param id
	 * @param controls
	 * @returns
	 */
	function isBindControl(id,controls){
		controls = controls || [];
		for (var key in controls) {
			if (controls[key].options.values._label_bind == "true"
					&& id === controls[key].options.values._label_bind_id) {
				return true;
			}
		}
		return false;
	}
	
	function synchronize(pcControlId,pcControl){
		var __designer = this.getMobileDesigner();
		var pcDesigner = this.getPcDesigner();
		var mobileControls = this.getMobileControlById(pcControlId);
		for (var i = 0; i < mobileControls.length; i++) {
			var oldVals = pcControl.options.values;
			var newVals = {};
			if (oldVals) {
				for (var key in oldVals) {
					newVals[key] = oldVals[key];
				}
			}
			if (pcControl.options.__oldValues) {
				mobileControls[i].options.__oldValues = pcControl.options.__oldValues;
				if (pcControl.options.domElement) {
					mobileControls[i].options.__pcDomElementOuterHtml = pcControl.options.domElement.outerHTML.toString();
				}
			}
			mobileControls[i].options.values = newVals;
			__designer.builder.serializeControl(mobileControls[i]);
			__designer.builder.setProperty(mobileControls[i]);
			__designer.builder.serializeControl(mobileControls[i]);
		}
	}
	
	//多页签切换
	function switchMutiTab(dom){
		//var event = this.mobileDesigner.mobileIFrame.event || this.mobileDesigner.mobileIFrame.document.event;
		//event.stopPropagation(); 
		//event.preventDefault();
		
		var name = $(dom).attr("name");
		var id = $(dom).attr("id");
		if(!name || !id)
			return;
		
		var doc = this.mobileDesigner.mobileIFrame.document;
		//切换页签
		$("li[name='"+name+"']",doc).removeClass("muiNavitemSelected");
		$(dom).addClass("muiNavitemSelected");
		//所有相关页签内容隐藏
		$("div[name='"+name+"']",doc).hide();
		//显示选择的页签内容
		$("div[labelId='"+id+"']",doc).show();
		//设置当前页签值
		var currentLabelIndex = $(dom).attr("lks_labelindex");
		var suffix = "_mobile";
		var mutiTabId = name.substring(0,name.length-suffix.length);
		if(mutiTabId)
			$("#"+mutiTabId,doc).attr("lks_currentlabel",currentLabelIndex);
	}
	
})(config);
