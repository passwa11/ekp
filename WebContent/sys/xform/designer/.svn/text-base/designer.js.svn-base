/**********************************************************
功能：设计器对象定义
使用：

备注：
	要调用Designer对象，返回对象必须和参数id同名。
	示例：var test = new Designer('test');

作者：龚健
创建时间：2009-02-18
**********************************************************/
function Designer(id) {
	//属性
	this.useType = '表单自定义';									// 用途
	this.version = '1.0';
	this.id = id;
	this.fdKey = '';

	this.controlDefinition = null;
	this.control = null;										// 当前控件对象
	this.controls = [];

	this.subForm = {};                                         //多表单当前选中的表单信息
	this.subForms = [];                                        //多表单所有表单信息
	this.subFormControls = {};                                 //多表单所有表单控件信息
	this.subItems = {};                                        //多表单选项(item)多语言信息
	this.synchronousInfos = [];                                //多表单需要同步的控件信息
	
	this.fragmentSetIds = [];									//片段集id集合
	
	this.isUpTab = false;                                      //是否将选项卡提升
	this.isChanged = false;
	this.hasInitialized = false;

	this.toolBar = null;										// 工具栏
	this.toolBarDomElement = null;
	this.toolBarAction = '';									// 当前工具栏选中的操作类型

	this.builder = null;										// 绘制区
	this.builderDomElement = null;

	this.previewBar = null;
	this.attrPanel = null;
	this.hiddenDomElement = null;

	this.effect = null;											// 动画对象
	this.shortcuts = null;										// 快捷键对象
	this.cache = null;											// 缓存对象
	
	// 事件
	this._eventListeners = {};

	//内部属性
	this._currentFocus = null;									// 当前焦点对象
	this._modelName = null;										// model名
	this._dblClickTime = 0;

	//内部调用方法
	this._drawFrame = _Designer_DrawFrame;						// 绘制设计器框架
	this._initControl = _Designer_InitControl;
	this._doFocus = _Designer_DoFocus;							// 处理当前焦点对象焦点事件
	this._getSysObj = _Designer_GetSysObj;						// 获取系统内置数据字典对象

	//公共方法
	this.setControl = Designer_SetControl;
	this.setModel = Designer_SetModel;							// 记录当前使用的model名
	this.initialize = Designer_Initialize;
	this.adjustBuildArea = Designer_AdjustBuildArea;			// 调整绘制区的宽度和高度
	this.bindEvent = Designer_BindEvent;
	this.getHTML = Designer_GetHTML;
	this.setHTML = Designer_SetHTML;
	this.getXML = Designer_GetXML;
	this.checkoutAll = Designer_Attr_ALL_Checkout;				// 校验所有控件
	this.getObj = Designer_GetObj;								// 获取控件属性对象
	this.addListener = Designer_AddListener;                    // 增加监听器
	this.removeListener = Designer_RemoveListener;              // 移除监听器
	this.fireListener = Designer_FireListener;                  // 通知监听器

	this.storeOldFdValues = Designer_StoreOldFdValues;
	this.getFdValues = Designer_GetFdValues;
	this.compare = Designer_Compare;
	
	//初始化控件相关定义
	this._initControl();
};

/**********************************************************
描述：
	公共函数
功能：
	Designer_Initialize : 初始化设计器
	Designer_AdjustBuildArea : 调整绘制区的宽度和高度
**********************************************************/
function Designer_Initialize(parentElement) {
	this._drawFrame(parentElement);
	//整个页面不能选择
	this.builderDomElement.onselectstart = function(){return false;};
	//屏蔽右键
	Designer.addEvent(document, "contextmenu", function(event){return false;});
	//让快捷键生效
	//this.builderDomElement.focus();
	var _self = this;
	Designer.addEvent(window, "resize", function() {
		_self.adjustBuildArea();
	});
	this.hasInitialized = true;
};

function Designer_AdjustBuildArea() {
	with(this.builderDomElement) {
		style.width = '100%';
		style.height = '100%';
	}
	//debugger;
	if (this.toolBar){
		this.toolBarDomElement.style.height = document.getElementById("toolbar_content_div").clientHeight + 'px';
		document.getElementById("designer_draw").style.height=(Designer.getWindowSize().height-document.getElementById("toolbar_content_div").clientHeight)+"px";
	}
};

function Designer_BindEvent(event, ownerName, eventHandle) {
	var _event = event || window.event, _ownerName = ownerName || null, _eventHandle = eventHandle || null;
	var _Owner = null;
	if (_ownerName == null || _eventHandle == null) return;
	//事件主体
	if (_ownerName == 'builder') _Owner = this.builder;
	if (_Owner == null) return;
	//触发相应的事件
	switch (eventHandle) {
	case 'mousedown':
		// 使用自定义的双击事件，主要针对出现拖拽遮罩时，导致非IE下无法触发双击
		var t = (new Date()).getTime();
		if (t - this._dblClickTime < 500) {
			this.bindEvent(event, ownerName, 'dblclick');
			return;
		}
		this._dblClickTime = t;
		if (_Owner._mouseDown) _Owner._mouseDown(event);
		break;
	case 'mousemove':
		if (_Owner._mouseMove) _Owner._mouseMove(event);
		break;
	case 'mouseup':
		if (_Owner._mouseUp) _Owner._mouseUp(event);
		break;
	case 'dblclick':
		this._dblClickTime = 0;
		if (_Owner._dblClick) _Owner._dblClick(event);
		break;
	}
};

function Designer_GetHTML() {
	var builder = this.builder;
	//序列化相关信息
	builder.onSerialize();
	builder.serialize();
	//清除控件编辑时才有用的信息
	builder.cleanControls();
	//虚线框移到临时存储区
	builder.moveDashBox(this.hiddenDomElement);
	//记录输出的HTML
	var rtnHTML = this.builderDomElement.innerHTML;
	// 将&#13;转换成\r\n换行符
	rtnHTML = rtnHTML.replace(/&#13;/g,"\r\n");
	//#4497 解决IE 下innerHTML带img src属性带绝对路径问题 #曹映辉 2014.8.21
	var absolutePath=location.href || "";
	absolutePath=absolutePath.substring(0,(absolutePath.lastIndexOf("\/")+1));
	rtnHTML=rtnHTML.replace(new RegExp(absolutePath,"gm"),"");
//	rtnHTML=rtnHTML.replace(new RegExp("http://[!/]*"+Com_Parameter.ContextPath,"gm"),"/ekp/");
	//去掉图片路径中的 http://localhost:8080 部分 作者 曹映辉 #日期 2015年8月11日
	var profix=absolutePath.substring(0,absolutePath.indexOf(Com_Parameter.ContextPath));
//	rtnHTML=rtnHTML.replace(new RegExp(profix,"gm"),"");
	rtnHTML = rtnHTML.replace(new RegExp("<img\s*[^>]*src=[\'\"](" + profix + ").+[\'\"]+[^>]*>","gm"), function (match, capture) {
		return match.replace(capture,"");
    });
	
	//虚线框移回到绘制区
	builder.moveDashBox(this.builderDomElement);
	builder.onSerialized();

	this.isChanged = (this._oldHTML != rtnHTML);
	return rtnHTML;
};

function Designer_SetHTML(html, setOld) {
	var builder = this.builder;
	// 清除控件
	builder.controls = [];
	//虚线框移到临时存储区
	builder.moveDashBox(this.hiddenDomElement);
	//载入HTML
	this.builderDomElement.innerHTML = html;
	//$(this.builderDomElement).html(html);
	//虚线框移回到绘制区
	builder.moveDashBox(this.builderDomElement);
	this.fireListener("setHtml");
	//初始化对象集
	builder.parse(null, this.builderDomElement);
	if (setOld == true)
		this._oldHTML = this.getHTML();
	//by duf 在初始化控件之前，触发 setHtml 事件
	//this.fireListener("setHtml");
	__FixTableStyle(this);
	__AfterSetHTML(this);
	this.parentWindow.$(this.parentWindow.document).trigger($.Event("setHtml-finish"),{'fdKey':this.fdKey,"designer":this});
};

function __AfterSetHTML(designer){
	/** 钉钉高级审批 */
	var maskControl = designer.getControlByType(["mask"],false);
	if (!maskControl || maskControl.length == 0) {
		var $fdMode = $("[name='sysFormTemplateForms."+designer.fdKey+".fdMode']",window.top.document);
		var modelVal = $fdMode.val();
		if (modelVal === "3") {
			$fdMode.removeAttr("disabled");
			$fdMode.removeClass("removeSelectAppearance");
		}
	}
	var isSubForm = Designer_GetModeValue() == "4";
	var tableControls = designer.getControlByType(["standardTable"],isSubForm)||[];
	for (var i = 0; i < tableControls.length; i++) {
		var tableControl = tableControls[i];
		_Designer_Control_StandardTable_Set_Default_Style(tableControl);
	}
	/** 钉钉高级审批 */
}

function __FixTableStyle(designer) {
	$("[name='dynamicTableStyle']",designer.buildDomElement).each(function(index,obj) {
		var href = $(obj).attr("href");
		var result = /\/(\w+)\//.exec(href);
		if (result.length > 1) {
			var contextPath = result[0];
			var path = result[1];
			if (contextPath != Com_Parameter.ContextPath) {
				var realPath = Com_Parameter.ContextPath;
				if (path === "sys" && (Com_Parameter.ContextPath != "/" || Com_Parameter.ContextPath != "")) {
					href = Com_Parameter.ContextPath + href.substring(1);
				} else {
					href = href.replace(/\/(\w+)\//,Com_Parameter.ContextPath);
				}
				$(obj).attr("href",href);
			}
		}
	});
}

function Designer_GetXML() {
	var builder = this.builder;
	var buf = ['<model '];
	if (window.parent) {
		var e = window.parent.document.getElementById('extendDaoEventBean');
		if (e && e.value != null && e.value != '') {
			buf.push('extendDaoEventBean="', e.value, '" ');
		}
		var entityName = window.parent.document.getElementById('formEntityName');
		if (entityName && entityName.value != null && entityName.value != '') {
			buf.push('entityName="', entityName.value, '" ');
		}
	}
	buf.push('>\r\n');
//		buf.push('name="', this.options.domElement.id, '" ');
//		buf.push('label="', values.label, '" ');
//		buf.push('type="main">');
	if (builder.controls.length > 0) {
		builder.controls = builder.controls.sort(Designer.SortControl);
		for (var i = 0, l = builder.controls.length; i < l; i ++) {
			var c = builder.controls[i];
			if (c.drawXML) {
				buf.push(c.drawXML());
			}
		}
	}
	buf.push('</model>');
	return buf.join('');
}

/**
 * isGlobal : 是否需要获取全部的变量，包括主文档的字段属性
 * 获取公式变量对象 (局部混乱中)
 * isNeedFindAll 多表单是否获取所有表单中的obj,默认false
 */
function Designer_GetObj(isGlobal, isStore, isNeedFindAll,isGetFieldlaylout) {
	var objs = [];
	//var obj = {name : 'fd_124',label:'主表', children : objs};
	if (isGlobal && window.parent) {
		var callFun = new Function('return window.parent._XForm_GetSysDictObj_' + this.fdKey + '();');
		var sysObjs = callFun();
		if (sysObjs != null) {
			objs = objs.concat(sysObjs);
		}
	}
	var isNeed = false;
	if(isNeedFindAll){
		isNeed = true;
	}
	//是否为多表单
	if(typeof Designer.instance.parentWindow.Form_getModeValue != "undefined" && Designer.instance.parentWindow.Form_getModeValue(Designer.instance.fdKey)==Designer.instance.template_subform && isNeed){
		var subForm = Designer.instance.subForm;
		Designer.instance.parentWindow.SubFormData.needLoad_subform = false;
		var subForms = Designer.instance.subForms;
		for(var s = 0;s<subForms.length;s++){
			subForms[s].link.click();
			var controls = Designer.instance.builder.controls.sort(Designer.SortControl);
			_Designer_GetObj(controls, objs, isStore ? true : false,isGetFieldlaylout);
		}
		Designer.instance.parentWindow.SubFormData.needLoad_subform = true;
		subForm.link.click();
		for (var i = 0; i < objs.length; i++){
			for (var j = i+1; j < objs.length; j++){
				if (objs[i].name == objs[j].name) {
					objs.splice(j, 1);
					j--;
				}
			}
		}
	}else{
		var controls = this.builder.controls.sort(Designer.SortControl);
		_Designer_GetObj(controls, objs, isStore ? true : false,isGetFieldlaylout);
	}
	return objs;
}

function _Designer_GetObj(controls, objs, isStore, isGetFieldlaylout) {
	for (var i = 0, l = controls.length; i < l; i ++) {
		var control = controls[i];
		if (control.skipObj) {
		    continue;
        }
		if (control.storeType == 'none'){
			if(isGetFieldlaylout && control.type == "fieldlaylout"){//对属性列表的兼容
				if(control.children && control.children.length > 0){
					_Designer_GetObj(control.children.sort(Designer.SortControl), objs, isStore,isGetFieldlaylout);
				}else{
					var rowDom = Designer_GetObj_GetParentDom(function(parent) {
						return (Designer.checkTagName(parent, 'tr') && parent.getAttribute('type') == 'templateRow')
					}, control.options.domElement);
					var obj={},isTempRow = (rowDom != null && rowDom.getAttribute('type') == 'templateRow');
					obj.name = control.options.values.fieldIds;
					obj.label = Designer.HtmlUnEscape(control.options.values.fieldNames);
					obj.type = _Designer_GetObj_GetType(control,true);
					obj.controlType = control.type;
					obj.isTemplateRow = isTempRow;
					objs.push(obj);
				}
			}
			continue;
		}
		if (control.storeType == 'layout' && !isStore) {
			_Designer_GetObj(control.children.sort(Designer.SortControl), objs, isStore,isGetFieldlaylout);
			if(control.type != 'eqb'){
				continue;
			}
		}
		var rowDom = Designer_GetObj_GetParentDom(function(parent) {
			return (Designer.checkTagName(parent, 'tr') && parent.getAttribute('type') == 'templateRow')
		}, control.options.domElement);
		
		var obj = {}, isTempRow = (rowDom != null && rowDom.getAttribute('type') == 'templateRow');
		if (control.options.values.__dict__) {
			var dict = control.options.values.__dict__;
			for (var di = 0; di < dict.length; di ++) {
				
				var _dict = dict[di];
				objs.push({
					name: _dict.id,
					label: _dict.label,
					type: _dict.type,
					controlType: control.type,
					isTemplateRow: isTempRow
				});
			}
		} else {
			obj.name = control.options.values.id;
			if(control.options.values.dimension)
				obj.dimension = control.options.values.dimension;
			obj.label = Designer.HtmlUnEscape(control.options.values.label);
			obj.type = _Designer_GetObj_GetType(control);
			obj.controlType = control.type;
			if (obj.controlType == "address"){
				obj.orgType = control.options.values._orgType;
			}
			if (obj.controlType == "new_address"){
				obj.orgType = control.options.values._orgType;
			}
			if (obj.type == "Date"){
				obj.orgType = control.options.values.businessType
			}
			obj.isTemplateRow = isTempRow;
			objs.push(obj);
		}
		if(control.type != 'eqb'){
			var childrenObj = [];
			_Designer_GetObj(control.children.sort(Designer.SortControl), childrenObj, isStore,isGetFieldlaylout);
			for (var j = 0; j < childrenObj.length; j ++) {
				var chc = childrenObj[j];
				if (chc.isTemplateRow) {
					chc.name = obj.name + '.' + chc.name;
					chc.label = obj.label + '.' + chc.label;
					if (chc.type.indexOf("[]") < 0) {
                        chc.type = chc.type + '[]';
                    }
				}
				objs.push(chc);
			}
		}
	}
}
function Designer_GetObj_GetParentDom(tagName, dom) {
	var parent = dom;
	while((parent = parent.parentNode) != null) {
		if (typeof tagName == 'function') {
			if (tagName(parent))
				return parent;
		}
		else if (Designer.checkTagName(parent, tagName)) {
			return parent;
		}
	}
	return parent;
}
// 根据控件类型，判断后传回持久化对话框要用的数据类型
function _Designer_GetObj_GetType(control,isFieldlaylout) {
	var values = control.options.values;
	if (control.type == 'rtf') {
		return 'RTF';
	}
	if (control.type == 'address' || control.type == 'new_address') {
		var orgType = 'com.landray.kmss.sys.organization.model.SysOrgElement';
		if(values._orgType=="ORG_TYPE_PERSON"){
			orgType = 'com.landray.kmss.sys.organization.model.SysOrgPerson';
		}
		return values.multiSelect == 'true' ? orgType + '[]' : orgType;
	}
	if (control.type == 'datetime') {
		if (values.businessType == 'timeDialog') {
			return 'Time';
		}else if (values.businessType == 'datetimeDialog'){
			return 'DateTime';
		}else if (values.businessType == 'dateDialog7'){
			return 'Date7';
		}
		return 'Date';
	}
	if(control.type=='attachment' || control.type=='uploadimg' || control.type=='docimg'){
		return 'Attachment';
	}
	if(isFieldlaylout && values.__type){
		return values.__type;
	}
	return (values.dataType ? values.dataType : 'String');
}

// 设置当前操作对象，并触发其他控件onSelectControl事件
function Designer_SetControl(control) {
	this.control = control;
	this.toolBar.onSelectControl();
}

function _Designer_GetSysObj() {
	if (window.parent) {
		return (new Function('return window.parent._XForm_GetSysDictObj_' + this.fdKey + '();'))();
	} else {
		return [];
	}
}

function Designer_AddListener(name, fun) {
	var evt = this._eventListeners[name];
	if (evt == null) {
		evt = [];
		this._eventListeners[name] = evt;
	}
	evt.push(fun);
}

function Designer_RemoveListener(name, fun) {
	var evt = this._eventListeners[name];
	if (evt != null) {
		for (var i = 0; i < evt.length; i ++) {
			if (fun === evt[i]) {
				evt.splice(i, 1);
				return;
			}
		}
	}
}

function Designer_FireListener(name) {
	var evt = this._eventListeners[name];
	if (evt != null) {
		for (var i = 0; i < evt.length; i ++) {
			evt[i](this);
		}
	}
}

function Designer_SetModel(modelName) {
	this._modelName = modelName || null;
}

/**********************************************************
描述：
	内部调用函数
功能：
	_Designer_DrawFrame   : 绘制设计器框架
	_Designer_InitControl : 初始化控件相关定义
	_Designer_DoFocus     : 处理当前焦点对象焦点事件
**********************************************************/
function _Designer_DrawFrame(parentElement) {
	var _parentElement = parentElement || null, buf = new Array();
	buf.push('<table width="100%" height="100%" cellspacing="0" cellpadding="0" border="0">');
	buf.push('<tr><td colspan="3" id="designer_toolbar" height="30" valign="top"></td></tr>');
	buf.push('<tr><td width="0" valign="top" id="designer_draw_left"></td><td valign="top">');
	// 在非IE浏览器中，div无法监控对非输入元素的事件（例如：keydown），添加tabindex可以解决这个焦点问题
	buf.push('<div id="designer_draw" style="height:100%" tabindex="5"');
	buf.push(' onmousedown="' + this.id + '.bindEvent(event, \'builder\', \'mousedown\');"');
	buf.push(' onmousemove="' + this.id + '.bindEvent(event, \'builder\', \'mousemove\');"');
	buf.push(' onmouseup="' + this.id + '.bindEvent(event, \'builder\', \'mouseup\');"');
	buf.push(' ondblclick="' + this.id + '.bindEvent(event, \'builder\', \'dblclick\');"');
	buf.push('>&nbsp;</div></td><td width="0" valign="top" id="designer_draw_right"></td></tr>');
	buf.push('</table>');
	buf.push('<div id="designer_hidden" style="display:none"></div>');
	if (_parentElement == null) _parentElement = document.body || document.documentElement;
	_parentElement.innerHTML = buf.join('');

	this.toolBarDomElement = Designer.$('designer_toolbar');
	this.builderDomElement = Designer.$('designer_draw');
	this.hiddenDomElement = Designer.$('designer_hidden');
	this.builderLeftDomElement = Designer.$('designer_draw_left');
	this.builderRightDomElement = Designer.$('designer_draw_right');

	this.adjustBuildArea();

	//快捷键对象
	if (typeof(Designer_Shortcut) != 'undefined') this.shortcuts = new Designer_Shortcut();
	//动画对象
	if (typeof(Designer_Animation) != 'undefined') this.effect = new Designer_Animation(this);
	//缓存对象
	if (typeof(Designer_Cache) != 'undefined') this.cache = new Designer_Cache('landray');
	//绘制区
	if (typeof(Designer_Builder) != 'undefined') this.builder = new Designer_Builder(this);
	//工具栏
	if (typeof(Designer_Toolbar) != 'undefined') this.toolBar = new Designer_Toolbar(this);
	//属性框
	if (typeof(Designer_AttrPanel) != 'undefined') this.attrPanel = new Designer_Panel(this, new Designer_AttrPanel());
	//控件树
	if (typeof(Designer_TreePanel) != 'undefined') this.treePanel = new Designer_Panel(this, new Designer_TreePanel());
	//基本属性列表
	if (typeof(Designer_FieldPanel) != 'undefined') this.fieldPanel = new Designer_Panel(this, new Designer_FieldPanel());
	//右键菜单
	if (typeof(Designer_RightMenu) != 'undefined') this.rightMenu = new Designer_RightMenu(this, null, Designer_Menus);
	
	this.adjustBuildArea();
};

function _Designer_InitControl() {
	var definition = null;
	//控件定义
	if (typeof(Designer_Control_Definition) == 'undefined') return;
	definition = new Designer_Control_Definition();
	this.controlDefinition = definition.controls;
};

function _Designer_DoFocus(currentFocus) {
	if (this._currentFocus === currentFocus) return true;
	if (this._currentFocus && this._currentFocus.onLeave) {
		if (!this._currentFocus.onLeave()) {
			return false;
		}
	}
	this._currentFocus = currentFocus;
	return true;
};


/**********************************************************
描述：
	以下所有函数为设计器公用函数。
功能：
	$                 : 获得Dom对象，根据ID。
		  参数: id或id集合
		  返回: 一个或多个对象

	absPosition       : 获得Dom对象的绝对位置。

	getDesignElement  : 获得设计元素

	isDesignElement   : 是否是设计元素

	extend            : 复制对象
		  参数:
			  destination : 目标对象
			  source      : 源对象
			  mode        : 复制模式 注：可选，值为：onlyMethod(只复制方法)

	$A                : 参数转换成数组。

	getBrowser        : 获得浏览器信息

	getBrowserVersion : 获得浏览器版本信息

	addEvent          : 追加事件
		  参数:
			  element     : 源对象
			  eventHandle : 源对象的事件句柄，比如click
			  method      : 绑定的方法句柄
		  示例:
		      function ceshi(event, p1, p2){};
			  var p = ['1', '2'];
			  addEvent(element, 'click', ceshi);

	removeEvent       : 删除事件

	bindEvent         : 绑定事件
		  参数: (按顺序)
			  1. element: 源对象
			  2. eventHandle: 源对象事件句柄
			  3. method: 绑定方法句柄
			  4. 绑定方法参数
			  5. 绑定方法参数...
		  示例:
		      function ceshi(event, p1, p2){};
			  =>bindEvent(element, 'click', ceshi, p1, p2);

	leaveEvent        : 解除事件(与bindEvent方法相反)

	getWindowSize     : 获得当前窗口的宽和高

	getMousePosition  : 获得当前鼠标坐标

	isIntersect       : 检测坐标是否在Dom对象区域中
		  参数:
			  position : (X坐标，Y坐标)
			  element  : Dom对象

	spliceArray       : 从一个数组中移除指定的元素

	getElementsByClassName : 获得Dom对象集，根据className

	checkTagName      : 校验Dom元素的tagName
**********************************************************/
Designer.$ = function() {
	var results = [], element;
	for (var i = 0; i < arguments.length; i++) {
		element = arguments[i];
		if (typeof element == 'string') element = document.getElementById(element);
		results.push(element);
	}
	return results.length < 2 ? results[0] : results;
};

Designer.absPosition = function(node, stopNode) {
	var x = y = 0;
	//for (var pNode = node; pNode != null && pNode !== stopNode; pNode = pNode.offsetParent) {
		//x += pNode.offsetLeft - pNode.scrollLeft; y += pNode.offsetTop - pNode.scrollTop;
//		if ( pNode.style.position == 'absolute' || pNode.style.position == 'relative'  
//            || ( pNode.style.overflow != 'visible' && pNode.style.overflow != '' ) ) {
//            break;
//        }
		//x += pNode.offsetLeft; y += pNode.offsetTop;
	//}
	//x = x + document.body.scrollLeft;
	//y = y + document.body.scrollTop;
//	if (window.console)
//		console.info('absPosition:', {'x':x, 'y':y}, node.tagName);
	var offset = $(node).offset();
	return {'x':offset.left, 'y':offset.top};
};

Designer.getDesignElement = function(node, attr) {
	if (attr == null) attr = 'parentNode';
	for (var findNode = node; findNode && findNode.tagName && findNode.tagName.toLowerCase() != 'body'; findNode = findNode[attr]) {
		if (Designer.isDesignElement(findNode)) return findNode;
	}
	return null;
};

Designer.isDesignElement = function(node) {
	//换行控件第二个div因添加了formDesign属性，导致其他控件添加到换行控件上不换行
	if(node.getAttribute("fd_type")=="brcontrol" && node.getAttribute("isDesignElement")=="false"){
		return false;
	}
	return node && node.getAttribute('formDesign') && node.getAttribute('formDesign') == 'landray';
};

Designer.extend = function() {
	var target = arguments[0] || {}, i = 1, length = arguments.length, deep = false, options;
	if ( target.constructor == Boolean ) {
		deep = target;
		target = arguments[1] || {};
		i = 2;
	}
	if ( typeof target != "object" && typeof target != "function" ) target = {};
	if ( length == i ) {
		target = this;
		--i;
	}

	for ( ; i < length; i++ )
		if ( (options = arguments[ i ]) != null )
			for ( var name in options ) {
				var src = target[ name ], copy = options[ name ];
				if ( target === copy ) continue;
				if ( deep && copy && typeof copy == "object" && !copy.nodeType )
					target[ name ] = Designer.extend( deep, src || ( copy.length != null ? [ ] : { } ), copy );
				else if ( copy !== undefined )
					target[ name ] = copy;
			}
	return target;
};

Designer.$A = function(iterable) {
	if (!iterable) return [];
	if (iterable.toArray) {
		return iterable.toArray();
	} else {
		var results = [];
		for (var i = 0; i < iterable.length; i++)
			results.push(iterable[i]);
		return results;
	}
};

Designer.getBrowser = function() {
	var userAgent = navigator.userAgent.toLowerCase();
	if (/msie/.test( userAgent ) && !/opera/.test( userAgent )) return 'msie';
	if (/mozilla/.test( userAgent ) && !/(compatible|webkit)/.test( userAgent )) return 'mozilla';
	if (/webkit/.test( userAgent )) return 'safari';
	if (/opera/.test( userAgent )) return 'opera';
};

Designer.getBrowserVersion = function() {
	var userAgent = navigator.userAgent.toLowerCase();
	return (userAgent.match( /.+(?:rv|it|ra|ie)[\/: ]([\d.]+)/ ) || [])[1];
};

Designer.UserAgent = Designer.getBrowser();

Designer.instance = new Designer('Designer.instance');

Designer.addEvent = function(element, eventHandle, method) {
	if(element.attachEvent){
		element.attachEvent("on" + eventHandle, method);
	}else{
		element.addEventListener(eventHandle, method, false);
	}
};

Designer.removeEvent = function(element, eventHandle, method) {
	if(Designer.UserAgent == 'msie')
		element.detachEvent("on" + eventHandle, method);
	else
		element.removeEventListener(eventHandle, method, false);
};

Designer.bindEvent = function(element, eventHandle, method) {
	var args = Designer.$A(arguments), args = args.slice(3);
	element[eventHandle + method] = function(event) {
		return method.apply(element, [event||window.event].concat(args));
	};
	Designer.addEvent(element, eventHandle, element[eventHandle + method]);
};

Designer.leaveEvent = function(element, eventHandle, method) {
	Designer.removeEvent(element, eventHandle, element[eventHandle + method]);
	element[eventHandle + method] = null;
};

Designer.getWindowSize = function() {
	var width = Math.max(document.documentElement.clientWidth, document.body.clientWidth);
	var height = Math.max(document.documentElement.clientHeight, document.body.clientHeight);
	return {
		width  : Math.max(width, document.body.scrollWidth),
		height : Math.max(height, document.body.scrollHeight)
	};
};

Designer.getMousePosition = function(event) {
	var pos = null;
	if (event.clientX || event.clientY) {
		pos = {
			x:event.clientX + Designer.getDocumentAttr("scrollLeft") - Designer.getDocumentAttr("clientLeft"),
			y:event.clientY + Designer.getDocumentAttr("scrollTop")  - Designer.getDocumentAttr("clientTop")
		};
	}
	else {
		//console.info('use page x y');
		pos = {x:event.pageX, y:event.pageY};
	}
	//console.info("pos x = " + pos.x + ", y = " + pos.y);
	return pos;
};

// 兼容多浏览器
Designer.getDocumentAttr = function(attr,win){
	if(win){
		return win.document.documentElement[attr]||win.document.body[attr];
	}else{
		return document.documentElement[attr]||document.body[attr];
	}
}

Designer.eventButton = function(event) {
	var button = event.button;
	if (event.which != null) { // 用which确定按键
		if (1 == event.which) {
			return 1;
		}
		if (2 == event.which) {
			return 4;
		}
		if (3 == event.which) {
			return 2;
		}
	}
	if ('msie' == Designer.UserAgent) {
		return button;
	}

	if (0 == button) {
		return 1;
	}
	if (1 == button) {
		return 4;
	}
	return button;
};

Designer.isIntersect = function(position, element) {
	if (!element) return false;
	var area = Designer.absPosition(element);
	return !(position.x < area.x || position.y < area.y ||
		position.x > (area.x + element.offsetWidth) || position.y > (area.y + element.offsetHeight));
};

Designer.spliceArray = function(array, spliced) {
	for (var i = 0; i < array.length; i++)	{
		if (array[i] == spliced) {
			array.splice(i, 1);
			break;
		}
	}
};

Designer.getElementsByClassName = function(className, parent) {
	var _parent = parent || document, matches = [], nodes = _parent.getElementsByTagName('*');
	for (var i = nodes.length - 1; i >= 0; i--) {
		if (nodes[i].className == className || nodes[i].className.indexOf(className) + 1 ||
			nodes[i].className.indexOf(className + ' ') + 1 || nodes[i].className.indexOf(' ' + className) + 1)
			matches.push(nodes[i]);
	}
	return matches;
};

Designer.checkTagName = function(element, tagName) {
	return element && element.tagName && element.tagName.toLowerCase() == tagName.toLowerCase();
};

Designer.generateID = function (){
	return parseInt(((new Date().getTime()+Math.random())*10000)).toString(16);
};

Designer.HtmlEscape = function (s){
	if (s == null || s ==' ') return '';
	s = s.replace(/&/g, "&amp;");
	s = s.replace(/\"/g, "&quot;");
	s = s.replace(/</g, "&lt;");
	return s.replace(/>/g, "&gt;");
};

Designer.HtmlUnEscape = function (s){
	if (s == null || s ==' ') return '';
	s = s.replace(/&amp;/g,"\&");
	s = s.replace(/&quot;/g,"\"");
	s = s.replace(/&lt;/g,"\<");
	s = s.replace(/&#39;/g,"\'");
	return s.replace(/&gt;/g,"\>");
};

//清除字符串的敏感字符，包括换行符、空格、单双引号
Designer.ClearStrSensiChar = function (s){
	if(s){
		//特殊处理英文
		var result = formatEnglishWords(s);
		if(!result.isNew){
			s = (s + "").replace(/\r\n|\n|\s|\"|&quot;|\'|\./g,"");
		}else{
			s = result.str.replace(/^\s*(.*)/,"$1").replace(/(.*?)\s*$/,"$1");//去除前后空格
			s = (s + "").replace(/\r\n|\n|\"|&quot;|\'|\./g,"");
		}
	}
	return s;
}

Designer.GetDetailsTable = function(dom) {
    var tableObj = $(dom).closest("table[fd_type='detailsTable']")[0];
    if (!tableObj) {
        tableObj = $(dom).closest("table[fd_type='seniorDetailsTable']")[0];
    }
    return tableObj;
}

Designer.getClosestDetailsTable = function(control) {
    if (control) {
        if (control.isDetailsTable) {
            return control;
        }
        var parent = control.parent;
        while (parent != null) {
            if (parent.isDetailsTable) {
                return parent;
            }
            parent = parent.parent;
        }
    }
    return null;
}

Designer.IsDetailsTableControl = function(control) {
    return control && control.isDetailsTable;
}

Designer.IsDetailsTableControlObj = function(controlObj) {
    return controlObj && (controlObj.controlType == 'detailsTable' || controlObj.controlType == 'seniorDetailsTable');
}


//特殊处理英文
function formatEnglishWords(s){
	var list = s.replace(/^\s*(.*)/,"$1").replace(/(.*?)\s*$/,"$1").split(/\s+/);//去除前后空格并切割
	var re = /^[A-Za-z]+$/;
	var num = /^\d+$/;
	var str = "";
	var isNew = false;
	for(var i=0; i<list.length-1; i++){
		if((!re.test(list[i]) || num.test(list[i])) && (!re.test(list[i+1]) || num.test(list[i+1]))){
			str += list[i];
		}else{
			isNew = true;
			str += list[i] + " ";
		}
	}
	if(list.length > 0){
		str += list[list.length-1];
	}
	var result = {
		'isNew':isNew,
		'str':str
	}
	return result;
}

//控件按位置排序
Designer.SortControl = function (a, b) {
	var aElem = a.options.domElement;
	var bElem = b.options.domElement;
	if (aElem && bElem && aElem.parentNode && bElem.parentNode){
		if (aElem.parentNode.tagName != 'TD' || bElem.parentNode.tagName != 'TD') {
			return Designer.SortCompareByAbs(aElem, bElem);
		}
		var aTd = aElem.parentNode, aTr = aTd.parentNode;
		var bTd = bElem.parentNode, bTr = bTd.parentNode;
		var result = aTr.rowIndex - bTr.rowIndex;
		if (result != 0) return result;
		result = aTd.cellIndex - bTd.cellIndex;
		if (result != 0) return result;
		return Designer.SortCompareByAbs(aElem, bElem);
	}
}
// 没在表格中，直接比较坐标
Designer.SortCompareByAbs = function (aElem, bElem) {
	var aPos = Designer.absPosition(aElem);
	var bPos = Designer.absPosition(bElem);
	//先判断y轴的，再判断x轴的 （问题单号：#29496 流程表单在配置时，如果增加了权限区段，在权限区段内无法利用公式获取其他字段的值） by朱国荣 2016-07-22 
	result = aPos.y - bPos.y;
	if ( result > 5 || result < -5) return result;
	var result = aPos.x - bPos.x;
	if ( result > 5 || result < -5) return result;	
	
	return 0;
}

//====================::::::::::数据初始化::::::::::====================
Designer.Initialize = function () {
	//等待页面载入后再执行
	if(document.body == null) {
		setTimeout("Designer.Initialize();", 100);
		return;
	}
	if(document.body.clientWidth == 0) {
		document.body.onresize = function() {
			document.body.onresize = null;
			Designer.Initialize();
		};
		return;
	}
	document.body.style.cursor="default";
	//初始化设计器
	Designer.instance.initialize();
};

//获取指定类型的控件
Designer.instance.getControlByType = function (type,isSubFormMode){
	var objs = [];
	type = type || [];
	var controls = [];
	if (isSubFormMode){
		for (var key in Designer.instance.subFormControls){
			var subControls =  Designer.instance.subFormControls[key];
			controls = controls.concat(subControls);
		}
	}else{
		controls = Designer.instance.builder.controls.sort(Designer.SortControl);
	}
	_Designer__GetControlByType(type,controls,objs);
	return objs;
}

//获取指定类型的控件
Designer.instance._getControlByType = function (type,isSubFormMode){
	var objs = [];
	type = type || [];
	var controls = [];
	if (isSubFormMode){
		for (var key in Designer.instance.subFormControls){
			var subControls =  Designer.instance.subFormControls[key];
			for(var i = 0, l = subControls.length; i < l; i ++){
				$(subControls).attr("source_form_id",key);
			}
			controls = controls.concat(subControls);
		}
	}else{
		controls = Designer.instance.builder.controls.sort(Designer.SortControl);
	}
	_Designer__GetControlByType(type,controls,objs);
	return objs;
}

function _Designer__GetControlByType(type,controls,objs){
	for (var i = 0, l = controls.length; i < l; i ++) {
		var control = controls[i];
		if ($.inArray(control.type, type) > -1){
			objs.push(control);
		}
		var childrenObj = [];
		_Designer__GetControlByType(type,control.children.sort(Designer.SortControl), childrenObj);
		for (var j = 0; j < childrenObj.length; j ++) {
			var chc = childrenObj[j];
			if ($.inArray(chc.type, type) > -1){
				objs.push(chc);
			}
		}
	}
}

//获取动态控件引用的主数据类型和主数据id,格式{"MAINDATACUSTOM",[fd_xxxx,fd_xxxxx],"JDBCXFORM",[fd_xxxxx,fd_xxxxx]};
Designer.instance.getRelationControlInfo = function(isSubFormMode){
	var relationTypeControl = ["relationSelect","relationChoose","relationCheckbox","relationRadio","relationEvent","massData"];
	var relationControls = Designer.instance._getControlByType(relationTypeControl,isSubFormMode);
	var sourceArr = ["MAINDATAINSYSTEM","MAINDATACUSTOM","JDBCXFORM"];
	var jsonstr="[]";
    var jsonarray = eval('('+jsonstr+')');
	for(var i = 0; i < relationControls.length; i++){
        var info = {};
		var control = relationControls[i];
		var values = control.options.values;
		if (values){
			var source = values.source;
			var funKey = values.funKey;
			if ($.inArray(source, sourceArr) > -1){
				if (source && funKey){
					funKey = funKey.replace(source + "_","");
					if (info[source]){
						if ($.inArray(funKey, info[source]) < 0){
							info[source]=funKey;
						}
					}else{
						info[source] = [];
						info[source]=funKey;
					}
				}
			}
		}
		info["sourceForm"]=relationControls[i].source_form_id==undefined?"subform_default":relationControls[i].source_form_id;
        //加个主文档全类名，查看历史模板需要用
        info["fdMainModelName"] = window.parent._xform_MainModelName;
        jsonarray.push(info);
	}

	return jsonarray;
}

//============================= 表单日志 start ==========================================
function Designer_GetModeValue(){
	var modeValue = $("[name='sysFormTemplateForms." + Designer.instance.fdKey + ".fdMode']",window.parent.document).val();
	return modeValue;
}

/**
 * 存旧版本的数据fd_values
 * [{表单id,表单名称,控件值}]
 * @returns
 */
function Designer_GetFdValues(){
	var fdValues = [];
	var subForms = this.subForms || [];
	var $comForm= $("[name='sysFormCommonTemplateForm']",window.parent.document);
	if ($comForm.length == 0) {
		$comForm = $("[name='sysFormTemplateHistoryForm']",window.parent.document);
	}
	//处理通用表单
	if (subForms.length == 0 && $comForm.length > 0) {
		var fdName = $("[name='fdName']",window.parent.document).val();
		var formObj = {id:"subform_default",fdName:fdName};
		subForms.push(formObj);
	}
	for (var i =0; i < subForms.length; i++) {
		var controls = this.subFormControls[subForms[i].id];
        var controlVal = {};
		if (controls) {
            var text = $(subForms[i].link).text() || subForms[i].fdName;
            for (var j = 0; j < controls.length; j++) {
                if (controls[j]) {
                    _Designer_GetFdValue(controls[j],controlVal);
                }
            }
        }
        fdValues.push({id:subForms[i].id,name:text,controlVal:controlVal});
	}
	return fdValues;
}

/**
 *  单个控件的fd_values
 * @param control
 * @param controlValues
 * @returns
 */
function _Designer_GetFdValue(control,controlValues){
	var fdValues = control.getFdValues();
	if (fdValues) {
		var id = fdValues.id;
		fdValues.controlType = control.type;
		controlValues[id] = fdValues;
		var children = control.children;
		if (children) {
			for (var i = 0;i < children.length; i++){
				_Designer_GetFdValue(children[i],controlValues);
			}
		}
	}
}

function Designer_StoreOldFdValues() {
	var controlValues = this.getFdValues();
	this.oldFdValues = controlValues;
}

/**
 * 跳过需要记录变更的字段
 * @param compareContext
 * @returns
 */
function Designer_SkipCompare(compareContext){
	var key = compareContext.key;
	var config = compareContext.config || {};
	if (/^_/.test(key) || /^fm_/.test(key)) {
		return true;
	}
	var skipChangeLogAttrs = config.skipChangeLogAttrs || [];
	for(var i = 0; i < skipChangeLogAttrs.length; i++) {
		var skipAttr = skipChangeLogAttrs[i];
		if (key.indexOf(skipAttr) > -1){
			return true;
		}
	}
	var unLogChangeAttrs = ["scale"];
	if (unLogChangeAttrs.indexOf(key) > -1) {
		return true;
	}
	var newFdVals = compareContext.newVal;
	var oldFdVals = compareContext.oldVal;
	var controlType = newFdVals.controlType || oldFdVals.controlType;
	var controlConfig = Designer_Config.controls[controlType];
	if (!controlConfig) return false;
	var attrKey = controlConfig.attrs && controlConfig.attrs[key];
	if (!attrKey) {
		return false;
	}
	if(attrKey["skipLogChange"]) {
		return true;
	}
//	if (typeof oldFdVals[key] == "undefined") {
//		return true;
//	}
	if (Designer_IsNull(newFdVals,key) && Designer_IsNull(oldFdVals,key)) {
		return true;
	}
	
	return false;
}

function Designer_IsNull(fdVals,key){
	var val = fdVals[key];
	if (val == "" || val == "null" || val == "false") {
		return true;
	}
	return false;
}

function Designer_DoCompare(oldFdForm,newFdForm){
	var change = []; //单个表单的控件变更列表
	var isSubForm = Designer_GetModeValue() == "4";
	var newFdValues = newFdForm.controlVal || {};
	var oldFdValues = oldFdForm.controlVal || {};
	var newFormName = newFdForm.name;
	var oldFormName = oldFdForm.name;
	for (var key in newFdValues) {
		var newControlVal = newFdValues[key];
		var oldControlVal = oldFdValues[key];
		var changeInfo = {};
		//新增或者变更了控件id
		changeInfo.fdId = newControlVal.id;
		if (!oldControlVal) {
			changeInfo.fdModifiedType = 0;
			changeInfo.fdFieldLabel = newControlVal.label;
			if (!changeInfo.fdFieldLabel) {
				changeInfo.fdFieldLabel = newControlVal.content;
			}
			if (isSubForm) {
				// 新增控件的时候如果没有名称，则设置为 空
				if(changeInfo.fdFieldLabel == undefined){
					changeInfo.fdFieldLabel=="";
				}else{
				    changeInfo.fdFieldLabel = newFormName + "-" + changeInfo.fdFieldLabel;	
				}
			}
			changeInfo.fdBusinessType = newControlVal.controlType;
			change.push(changeInfo);
		} else { //修改属性
			var changeDetailInfos = []; //控件的属性变更列表
			changeInfo.fdModifiedType = 1;
			changeInfo.fdFieldLabel = newControlVal.label;
			if (!changeInfo.fdFieldLabel) {
				changeInfo.fdFieldLabel = newControlVal.content;
			}
			//多表单,控件名称加上 表单名称
			if (isSubForm) {
				if(changeInfo.fdFieldLabel == undefined){
					changeInfo.fdFieldLabel = "";
				}else{
					changeInfo.fdFieldLabel = newFormName + "-" +changeInfo.fdFieldLabel;
				}
			}
			var hasChange = false;
			var controlConfig = Designer_Config.controls[newControlVal.controlType];
			for (var attrKey in newControlVal) {
				var skip = Designer_SkipCompare({key:attrKey,newVal:newControlVal,oldVal:oldControlVal,config:controlConfig});
				if (skip) {
					continue;
				}
				var newAttrVal = newControlVal[attrKey];
				var oldAttrVal = oldControlVal[attrKey];
				var changeDetailInfo = {}; //每个属性的变更明细
				changeDetailInfo.name = attrKey;
				changeInfo.fdBusinessType = newControlVal.controlType;
				var attrs = controlConfig && controlConfig.attrs;
				changeDetailInfo.status = 1;
				if (controlConfig && attrs && !attrs[attrKey]) { //当前控件获取不到属性配置,则从父类中获取
					var parentConfig = Designer_Config.controls[controlConfig.inherit];
					if (parentConfig) {
						var parentAttrs = parentConfig.attrs;
						if (parentAttrs) {
							var parentAttr = parentAttrs[attrKey];
							attrs[attrKey] = parentAttr;
						}
					}
				}
				//如果属性配置比较器,则用属性的比较器,返回比较结果
				if (controlConfig && attrs && attrs[attrKey] && attrs[attrKey]["compareChange"]) {
					var compareResult = attrs[attrKey]["compareChange"](attrKey,attrs[attrKey],oldAttrVal,newAttrVal);
					if (compareResult) {
						hasChange = true;
						changeDetailInfo.change = compareResult;
						changeDetailInfos.push(changeDetailInfo);
					}
				} else if (newAttrVal != oldAttrVal) {
					hasChange = true;
					changeDetailInfo.before = oldAttrVal;
					changeDetailInfo.after = newAttrVal;
					changeDetailInfos.push(changeDetailInfo);
				}
			}
			if (hasChange) {
				changeInfo.changeDetailInfo = changeDetailInfos;
				change.push(changeInfo);
			}
		}
		
	}
	for (var key in oldFdValues) {
		var newControlVal = newFdValues[key];
		var oldControlVal = oldFdValues[key];
		var changeInfo = {};
		//删除了控件
		if (!newControlVal) {
			changeInfo.fdId = oldControlVal.id;
			changeInfo.fdModifiedType = 2;
			var text = oldControlVal.label || oldControlVal.content;
			if (isSubForm) {
				// 删除控件的时候如果没有名称，则设置为 空
				if(text==undefined){
				   text = "";
				}else{
				   text = oldFormName + "-" + text;					
				}
			}
			changeInfo.fdFieldLabel = text;
			changeInfo.fdBusinessType = oldControlVal.controlType;
			change.push(changeInfo);
		}
	}
	return change;
}

function Designer_Compare(){
	var oldFdValues = this.oldFdValues;
	if (!oldFdValues) {
		return;
	}
	var newFdValues = this.getFdValues();
	var change = [];
	for (var i = 0; i < newFdValues.length; i++) {
		var newFdValue = newFdValues[i];
		var formId = newFdValue.id;
		var isExit = false;
		var relativeFdValue;
		for (var j = 0; j < oldFdValues.length; j++) {
			var oldFdValue = oldFdValues[j];
			if (formId == oldFdValue.id) {
				relativeFdValue = oldFdValue;
				isExit = true;
				break;
			}
		}
		if (isExit) {
			change = change.concat(Designer_DoCompare(relativeFdValue,newFdValue));
		} else {
			change = change.concat(Designer_DoCompare({},newFdValue));
		}
	}
	for (var i = 0; i < oldFdValues.length; i++) {
		var oldFdValue = oldFdValues[i];
		var formId = oldFdValue.id;
		var isExit = false;
		for (var j = 0; j < newFdValues; j++) {
			var newFdValue = newFdValues[j];
			if (formId == newFdValue.id) {
				isExit = true;
				break;
			}
		}
		if (!isExit) {
			change = change.concat(Designer_DoCompare(oldFdValues,{}));
		}
	}
	if(change.length == 0) {
		return;
	}
	return JSON.stringify(change);
}
//============================= 表单日志 end ==========================================