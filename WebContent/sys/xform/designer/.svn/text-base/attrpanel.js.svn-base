/**
 * 属性面板
 */

var Designer_AttrPanel = function() {
	this.domElement = document.createElement('div');
}

Designer_AttrPanel.prototype = {
	
	init : function(panel) {
		this.textIndex = 1;
		this.isControl = true;
		this.panel = panel;
		this.initTitle();
		this.initMain();
		this.initBottom();
		this.initFormChange();
		
		this.setNoSelectInfo();
		var self = this;
		this.panel.draw = function(c) {
			return self.draw(c);
			
		};
		
		this.panel.focusPanel = function() {
			self.focusPanel();
		};
		Designer.addEvent(this.panel.domElement, 'mousedown', function(event) {
			event = event ? event : window.event;
			event.cancelBubble = true; // ==========防止传递到设计区
			self.panel.owner._doFocus(self);
		});
	},

	initTitle : Designer_Panel_Default_TitleDraw,
	
	initMain : function() {
		var self = this;
		this.mainWrap = document.createElement('div');
		this.mainWrap.className = 'panel_main';
		this.mainBox = document.createElement('div');
		this.mainBox.className = 'panel_main_box';
		with((this.panelForm = document.createElement('form')).style) {
			margin = '0'; padding = '0';
		}
		this.panelForm.designerId = this.panel.owner.id;
		this.mainWrap.appendChild(this.mainBox);
		this.panelForm.appendChild(this.mainWrap);
		this.domElement.appendChild(this.panelForm);

		Designer.addEvent(this.panelForm, 'keyup', function(event) {
			event.cancelBubble = true;
			var dom = event.srcElement || event.target;
			if (event.keyCode == 13 && dom.tagName.toLowerCase() != 'textarea') {
				if (self.resetValues()) {
					self.hideBottom();
				}
			}
		});
	},

	initBottom : function() {
		var bottom = document.createElement('div');
		bottom.className = 'panel_bottom';
		var bottom_top = document.createElement('div');
		this.bottomBar = bottom_top;
		bottom_top.className = "panel_bottom_main";

		this.success = document.createElement('button');
		this.success.className = 'panel_success';
		this.success.style.margin = '2px 8px 0 0';
		this.success.title = Designer_Lang.attrpanelSuccess;
		this.success.value = "&nbsp;";
		
		this.apply = document.createElement('button');
		this.apply.className = 'panel_apply';
		this.apply.style.margin = '2px 8px 0 8px';
		this.apply.title = Designer_Lang.attrpanelApply;
		this.apply.value = "&nbsp;";

		this.cancel = document.createElement('button');
		this.cancel.className = 'panel_cancel';
		this.cancel.style.margin = '2px 0 0 8px';
		this.cancel.title = Designer_Lang.attrpanelCancel;
		this.cancel.value = "&nbsp;";

		var warpBox = document.createElement('div');
		warpBox.className = "panel_bottom_for_ie6"; // in ie6.css
		warpBox.appendChild(this.success);
		warpBox.appendChild(this.apply);
		warpBox.appendChild(this.cancel);
		bottom_top.appendChild(warpBox);

		bottom.appendChild(bottom_top);
		this.domElement.appendChild(bottom);
		this.initBottomBorder();
		this.initButtons();
	},

	initBottomBorder : Designer_Panel_Default_BottomDraw,

	initButtons : function() {
		var self = this;
		this.success.onclick = function() {
			
			if (self.resetValues()) {
				//增加属性面板成功关闭时的回调函数 作者 曹映辉 #日期 2014年5月15日
				var control = self.panel.owner.control;
				if(control&&control.onAttrSuccess){
					control.onAttrSuccess();
				}
				self.panel.close();
			}
		};

		this.apply.onclick = function() {
			if (self.resetValues()) {
				var control = self.panel.owner.control;
				if(control && control.onAttrApply){
					control.onAttrApply();
				}
				self.hideBottom();
			}
		};

		this.cancel.onclick = function() {
			self.reDraw();
		};
	},

	initFormChange : function() {
		var self = this;
		this._changed = false;
		Designer.addEvent(this.panelForm, 'keyup', function(event) {
			event.cancelBubble = true;
			if (self._changed) return;
			var dom = event.srcElement || event.target;
			var tagName = dom.tagName.toLowerCase();
			if (((tagName == 'input' && (dom.type == 'text' || dom.type == 'number')) || tagName == 'textarea')
				&& (dom.defaultValue != dom.value)) {
				self.showBottom();
				self._changed = true;
			}
		});
		Designer.addEvent(this.panelForm, 'mouseup', function(event) {
			event.cancelBubble = true;
			if (self._changed) return;
			var dom = event.srcElement || event.target;
			var tagName = dom.tagName.toLowerCase();
			if ((tagName == 'input' && (dom.type == 'checkbox' || dom.type == 'radio')) || tagName == 'a') {
				self.showBottom();
				self._changed = true;
			} else if (tagName == 'label' && $(dom).attr('isfor') == 'true') {
				self.showBottom();
				self._changed = true;
			}
		});
		var changeFun = function(event) {
			event = event || window.event;
			self.showBottom();
			self._changed = true;
			Designer.removeEvent(event.srcElement || event.target, "change", changeFun);
		}
		Designer.addEvent(this.panelForm, 'mousedown', function(event) {
//			event.cancelBubble = true;
			if (self._changed) return;
			var dom = event.srcElement || event.target;
			var tagName = dom.tagName.toLowerCase();
			if (tagName == 'select') {
				Designer.removeEvent(dom, "change", changeFun);
				Designer.addEvent(dom, "change", changeFun);
			}
		});
	},

	showMain : function() {
		this.domElement.style.display = '';
	},

	hideMain : function() {
		this.domElement.style.display = 'none';
	},

	showBottom : function() {
		this.bottomBar.style.display = '';
	},

	hideBottom : function() {
		this._changed = false;
		this.bottomBar.style.display = 'none';
		Designer_AttrPanel.colorPanelClose();
		Designer_AttrPanel.autoFormatPanelClose();//关闭一键排版
	},

	setMainInfo : function(info) {
		this.mainBox.innerHTML = '<div>' + info + '</div>';
		if($){
			var inputCheckbox = $(this.mainBox).find("input[type='checkbox']");
			for(var i = 0 ; i<inputCheckbox.length;i++){
				$(inputCheckbox[i]).css({"vertical-align": "middle","margin-bottom":" 1.4px"});
			}
			var inputRadio = $(this.mainBox).find("input[type='radio']");
			for(var i = 0 ; i<inputRadio.length;i++){
				$(inputRadio[i]).css({"vertical-align": "middle","margin-bottom":" 1.4px"});
			}
		}
		//未开放标签表格前暂时屏蔽
		//增加 明细表初始化 add by limh 2011年1月24日
		DocListFunc_Init();
	},
	
	setTitle : Designer_Panel_Default_SetTitle,

	setNoSelectInfo : function() {
		this.setTitle(Designer_Lang.attrpanelTitle);
		this.setMainInfo(
			'<table class="panel_table_info"><tr><td style="text-align:center;">'+Designer_Lang.attrpanelNoSelect+'</td></tr></table>'
		);
		this.hideBottom();
	},

	setSuccessInfo : function() {
		this.setMainInfo(
			'<table class="panel_table_info"><tr><td style="text-align:center;">'+Designer_Lang.attrpanelSuccessUpdate+'</td></tr></table>'
		);
	},

	focusPanel : function() {
		var elems = this.panelForm.elements;
		if (elems && elems.length > 0) elems[0].focus();
	},
	
	onClosed: function() {
		Designer_AttrPanel.colorPanelClose();
		Designer_AttrPanel.autoFormatPanelClose();
	},

	reDraw : function() {
		this.draw(this.control);
	},

	draw : function() {
		var control = this.panel.owner.control;
		if (this.panel.isClosed) return; // 不执行操作
		if (control == null) {
			this.setNoSelectInfo();
		}
		else if (control.options.showAttr == null && control.selectedDomElement.length > 0) {
			if (control.domAttrs == null) return;
			this.isControl = false;
			this.setMainInfo(this.drawDomAttrs(control));
		}
		else {
			//当控件没有属性的时候（例如jsp控件），就不需再展示属性框 by zhugr 2016-11-9
			if (control.attrs == null) return false;
			this.isControl = true;
			this.control = control;
			this.attrs = control.attrs;
			this.values = control.options.values || {};
			this.showAttr = control.options.showAttr ? control.options.showAttr : 'default';
			if (control.info) {
				this.setTitle(Designer_Lang.attrpanelTitlePrefix + control.info.name);
			}
			if (this.attrs.label && (this.values.label == null)) {
				this.values.label = this.textLabel ? this.textLabel : (control.info ? control.info.name + this.textIndex++ : "");
				this.panel.owner.treePanel.draw();
			}
			this.setMainInfo(this.drawAttrs());
			
		}
		if (control && control.onAttrLoad) control.onAttrLoad(this.panelForm, control);
		this.hideBottom();
		
	},

	drawDomAttrs : function(control) {
		var hasAttr = false;
		this.control = control;
		var dom = control.selectedDomElement[0];
		var tagName = dom.tagName.toLowerCase();
		this.attrs = control.domAttrs[tagName];
		if (control.info) {
			this.setTitle(Designer_Lang.attrpanelTitlePrefix + control.info.name + " - " + control.info[tagName]);
		}
		var html = '<table class="panel_table">';
		
		var domValues = {};
		for (var i = 0; i < control.selectedDomElement.length; i ++) {
			var _dom = control.selectedDomElement[i];
			if (i == 0) {
				for (var _name in this.attrs) {
					var domAttrName = (_name == 'className' ? "oldClassName" : _name);
					//_dom[domAttrName]在非IE浏览器取不到值？换为$(_dom).attr(domAttrName)
					domValues[domAttrName] = $(_dom).attr(domAttrName) == '' ? this.attrs[_name].value : $(_dom).attr(domAttrName);
				}
			} else {
				for (var _name in this.attrs) {
					var domAttrName = (_name == 'className' ? "oldClassName" : _name);
					//_dom[domAttrName]在非IE浏览器取不到值？换为$(_dom).attr(domAttrName)
					var _value = $(_dom).attr(domAttrName) == '' ? this.attrs[_name].value : $(_dom).attr(domAttrName);
					if (domValues[domAttrName] != _value) {
						domValues[domAttrName] = "$M$";
					}
				}
			}
		}

		for (var name in this.attrs) {
			var attr = this.attrs[name];
			var domAttrName = (name == 'className' ? "oldClassName" : name);
			html += Designer_AttrPanel[attr.type + 'Draw'](name, attr, domValues[domAttrName]);
			hasAttr = true;
		}
		if (!hasAttr) {
			html = Designer_AttrPanel.noAttrDraw();
		} else {
			html += '</table>';
		}
		dom = null;
		return html;
	},

	drawAttrs : function() {
		var html = '<table class="panel_table">';
		var hasAttr = false;
		for (var name in this.attrs) {
			var attr = this.attrs[name];
			this.changeShow(attr);
			var sa = attr.showAttr ? attr.showAttr : 'default';
			if (attr.show && (this.showAttr == 'all' || sa == this.showAttr)) {
				if (attr.type == 'self') {
					html += attr.draw(name, attr, this.values[name], this.panelForm, this.attrs, this.values, this.control);
				} else {
					html += Designer_AttrPanel[attr.type + 'Draw'](name, attr, this.values[name], this.panelForm, this.attrs, this.values, this.control);
				}
				hasAttr = true;
			}
		}
		if (this.values.id != null) {
			html += Designer_AttrPanel.idDraw(this.values.id, this.attrs);
			hasAttr = true;
		}
		if (!hasAttr) {
			html = Designer_AttrPanel.noAttrDraw();
		} else {
			html += '</table>';
		}
		return html;
	},
	
	changeShow : function(attr){
		 if(this.control && attr && attr.onlyShowInSomeModel && attr.onlyShowInDetailTable){//限制模块和明细表
			var pControl = this.control;
			var isShow = false;
			while(pControl){
				if(pControl.isDetailsTable && !pControl.isAdvancedDetailsTable){
					isShow = true;
					break;
				}
				pControl = pControl.parent;
			}
			if(isShow && window.isModelShow == true){
				attr.show = true;
			}else{
				attr.show = false;
			}
		}else if(this.control && attr && attr.onlyShowInDetailTable){//只在明细表中显示
			var pControl = this.control;
			var isShow = false;
			while(pControl){
				if(pControl.isDetailsTable && !pControl.isAdvancedDetailsTable){
					isShow = true;
					break;
				}
				pControl = pControl.parent;
			}
			if(isShow){
				attr.show = true;
			}else{
				attr.show = false;
			}
		}else if(this.control && attr && attr.onlyShowInSomeModel){//限制模块显示
			if(window.isModelShow == true){
				attr.show = true;
			}else{
				attr.show = false;
			}
		}else if(this.control && attr && attr.onlyShowInSeniorDetail){ // 限制导入模式只在高级明细表显示
			 var pControl = this.control;
			 var isShow = false;
			 while(pControl){
				 if(pControl.isAdvancedDetailsTable){
					 isShow = true;
					 break;
				 }
				 pControl = pControl.parent;
			 }
			 if(isShow){
				 attr.show = true;
			 }else{
				 attr.show = false;
			 }
		 }
		
	},

	onLeave : function() {
		return this.resetValues();
	},

	resetValues : function() {
		if (!this._changed) return true; // == 无修改不重设
		var elems = this.panelForm.elements;
		var setNewValues = false;
		var newValues = {};
		for (var i=0; i< elems.length;i++) {
			setNewValues = true;
			var elem = elems[i];
			var value = elem.value;
			var controls = [];
			if (elem.name == 'id') {
				if (typeof _Designer_Attr_AddAll_Controls != 'undefined' 
					&& typeof Designer_Attr_Id_ValidateValid != 'undefined') {
					_Designer_Attr_AddAll_Controls(this.panel.owner.builder.controls, controls);
	
					if (!Designer_Attr_Id_ValidateValid(elem.value, this.control, controls)) {						
						return false;
					}
					newValues[elem.name] = value;
					continue;
				} else {
					continue;
				}
			}
			if (elem.type == 'checkbox' && !elem.checked) {
				value = "false";
			}
			if (elem.type == 'radio' && !elem.checked) {
				continue;
			}
			else if (elem.multiple == true) {
				var _values = [];
				for (var j = 0; j < elem.options.length; j ++) {
					if (elem.options[j].selected) {
						_values.push(elem.options[j].value);
					}
				}
				if (_values.length > 0) {
					var splitStr = this.attrs[elem.name].splitStr;
					splitStr = splitStr ? splitStr : ';';
					value = _values.join(splitStr);
				}
			}
			if (elem.name != null && elem.name != "" && !elem.disabled) {
				if (elem.name.substr(0,1) == '_') {
					newValues[elem.name] = value;
					continue;
				}
				var attr = this.attrs[elem.name];
				if (attr && attr.validator) {
					if (attr.validator instanceof Array) {
						for (var ii = 0; ii < attr.validator.length; ii ++) {
							if (attr.validator[ii](elem, elem.name, attr, value, newValues, this.control)) {
								newValues[elem.name] = (attr.convertor) ?
									attr.convertor(elem.name, attr, value, newValues) : value;
							} else {
								return false; // === 不允许设值
							}
						}
					} else {
						if (attr.validator(elem, elem.name, attr, value, newValues, this.control)) {
							newValues[elem.name] = (attr.convertor) ?
								attr.convertor(elem.name, attr, value, newValues) : value;
						} else {
							return false; // === 不允许设值
						}
					}
				} else if(attr&&attr.convertor){
					newValues[elem.name] = attr.convertor(elem.name, attr, value, newValues) ;
				}
				else{
					newValues[elem.name] = value;
				}
			}
		}
		
		if(newValues){
			//多表单校验type、label、length是否一致
			if(typeof Designer.instance.parentWindow.Form_getModeValue != "undefined" && Designer.instance.parentWindow.Form_getModeValue(Designer.instance.fdKey)==Designer.instance.template_subform&&Designer.instance.subFormControls){
				var number = Designer_GetExitNumsById(newValues.id);
				if(number>0){
					var bool = false;
					var mycontrol = Designer_GetControlById(newValues.id);
					var attrs = mycontrol.attrs;
					for(var mykey in attrs){
						if((!attrs[mykey]["synchronous"] && !attrs[mykey]["lang"]) || newValues[mykey] === undefined){
							continue;
						}
						if(newValues[mykey] != mycontrol.options.values[mykey]){
							bool = true;
							break;
						}
					}
					if(bool){
						var del = confirm(Designer_Lang.sunform_check);
						if(!del){
							return false;
						}
						this.control.isNeedSynchronous = true;
					}
				}
			}
		}
		if (!this.isControl) {
			var doms = this.control.selectedDomElement;
			for (var i = 0; i < doms.length; i ++) {
				var dom = doms[i];
				for (var name in newValues) {
					// 空值不更新
					if (newValues[name] != '' && newValues[name] != null) {
						if (name != 'className'){
                            var tagName = dom.tagName.toLowerCase();
                            var domAttrs;
                            if (this.control.domAttrs) {
                                domAttrs = this.control.domAttrs[tagName]
                            }
                            domAttrs = domAttrs || {};
                            var hasDomAttrStyle = (domAttrs[name] && domAttrs[name]["styleName"]);
                            if(name.indexOf('style_')>=0 || hasDomAttrStyle){
								if(newValues[name]=='null'){
									$(dom).css(name.replace("style_",""),"");
									if (hasDomAttrStyle) {
                                        $(dom).css(domAttrs[name]["styleName"], "");
                                    }
								}
								else{
									try{
										dom.style[name.replace("style_","")]=newValues[name];
										//兼容下多浏览器
										$(dom).css(name.replace("style_",""),newValues[name]);
                                        if (hasDomAttrStyle) {
                                            $(dom).css(domAttrs[name]["styleName"], newValues[name]);
                                        }
									}catch(e){
										//当属性值写法不规范的时候，上面的写法会报错，例如:dom.style[width]=test;所以此处用try catch捕获 by zhugr 2017-01-05
										dom.setAttribute(name.replace("style_",""),newValues[name]);
									}
								}
							}
							//IE下可以查看设置值不默认选中问题 #24090
							//dom[name] = newValues[name]; 当name是“width”的时候，ie下报参数无效的错误，即使是用下面的jq做兼容都无法赋值进去，故应用原生的方法赋值 by 朱国荣 2016-8-15
							dom.setAttribute(name,newValues[name]);
							//兼容下多浏览器
							$(dom).attr(name,newValues[name]);
						}
						else {
							dom["oldClassName"] = newValues[name];
							//兼容下多浏览器
							$(dom).attr("oldClassName",newValues[name]);
						}
					}
				}
				// 处理表格样式跟单元格宽度（主题对border设置了!important，导致border样式不生效） start
				if (dom) {
					var borderWidth = dom.style["border-width"];
					var style = $(dom).attr("style");
					if (borderWidth) {
						if (!(/;$/.test(style))) {
							style+=";"
						}
						style += "border-width:" + borderWidth + " !important;";
						$(dom).css("cssText",style);
					} else {
						if (style) {
							var styleArr = style.split(";");
							var newStyle = "";
							for (var k = 0; k < styleArr.length; k++) {
								var entry = styleArr[k].split(":");
								if (/border-/.test(entry[0])){
									newStyle += entry[0] + ":" + entry[1] + " !important;";
								} else {
									newStyle += styleArr[k];
									if (!(/;$/.test(styleArr[k]))){
										newStyle += ";";
									}
								}
							}
							$(dom).css("cssText",newStyle);
						}
					}
				}
				// 处理表格样式跟单元格宽度 end
			}
			this._changed = false;
			return true;
		}
		var __oldValues={};
		if (this.control != null) {
			if (this.control.options.values) {
				__oldValues = Designer.extend(__oldValues,this.control.options.values,false);
				this.control.options.__oldValues = __oldValues;
			}
		}

		//  === 修改控件属性
		for (var _name in newValues) {
			// 记录加密属性的变更记录
			Designer_Attr_RecordEncryptChange(_name,this.values[_name],newValues[_name],this);
			//记录是否留痕属性的变更记录
			Designer_Attr_RecordIsMarkChange(_name,this.values[_name],newValues[_name],this);
			this.values[_name] = newValues[_name];
		}

		if (this.control != null && this.control.type == 'textLabel') {
			this.textLabel = this.values.content;
		}
		if (setNewValues) {
			var oldId = this.control.options.values.id;
			this.control.options.values = this.values;
			this.panel.owner.builder.setProperty(this.control);
			//更新移动端控件的值
			if (Designer.instance.mobileDesigner) {
				if (Designer.IsDetailsTableControl(this.control)) {
					this.panel.owner.builder.serializeControl(this.control);
				}
				var mobileDesigner = Designer.instance.mobileDesigner;
				mobileDesigner.synchronize(oldId,this.control);
			}
		}
		this._changed = false;
		return true;
	}
}

// 记录加密属性的变更记录
function Designer_Attr_RecordEncryptChange(name,oldValue,newValue,panel){
	if(name == 'encrypt'){
		if(typeof(oldValue) == 'undefined'){
			oldValue = 'false';
		}		
		if(oldValue != newValue){
			var _encryptChange = panel.values['_encryptChange'];
			// 只要值做了变更，则把encryptChange置为相反
			if(_encryptChange){
				if(_encryptChange == 'true'){
					panel.values['_encryptChange'] = "false";
				}else{
					panel.values['_encryptChange'] = "true";
				}
			}else{
				panel.values['_encryptChange'] = "true";
			}			
		}
	}
}

//记录是否留痕属性的变更记录
function Designer_Attr_RecordIsMarkChange(name,oldValue,newValue,panel){
	if(name == 'isMark'){
		if(typeof(oldValue) == 'undefined'){
			oldValue = 'false';
		}		
		if(oldValue != newValue){
			var _isMarkChange = panel.values['_isMarkChange'];
			// 只要值做了变更，则把_isMarkChange置为相反
			if(_isMarkChange){
				if(_isMarkChange == 'true'){
					panel.values['_isMarkChange'] = "false";
				}else{
					panel.values['_isMarkChange'] = "true";
				}
			}else{
				panel.values['_isMarkChange'] = "true";
			}			
		}
	}
}

//将移动端表单加到mycontrols中
function Designer_AddMobileFormControls(pcFormControls) {
	if (Designer.instance.mobileDesigner && Designer.instance.mobileDesigner.getMobileDesigner) {
		var mobileDesigner = Designer.instance.mobileDesigner.getMobileDesigner();
		var mobileFormControls = mobileDesigner.subFormControls;
		if (mobileFormControls) {
			for (var key in mobileFormControls) {
				pcFormControls[key] = mobileFormControls[key];
			}
		}
	}
}

//将移动端表单从mycontrols移除掉
function Designer_RemoveMobileFormControls(pcFormControls){
	if (Designer.instance.mobileDesigner && Designer.instance.mobileDesigner.getMobileDesigner) {
		var mobileDesigner = Designer.instance.mobileDesigner.getMobileDesigner();
		var mobileFormControls = mobileDesigner.subFormControls;
		if (mobileFormControls) {
			for (var key in mobileFormControls) {
				delete pcFormControls[key];
			}
		}
	}
}

function Designer_GetExitNumsById(id) {
	var mycontrols = Designer.instance.subFormControls;
	Designer_AddMobileFormControls(mycontrols);
	var number = 0;
	var myid = '';
	if(Designer.instance.subForm && Designer.instance.subForm.id){
		myid = Designer.instance.subForm.id;
	}else{
		myid = "subform_default";
	}
	for(var key in mycontrols){
		if(key != myid){
			for(var p = 0;p<mycontrols[key].length;p++){
				if(id == mycontrols[key][p].options.values.id){
					number++;
					break;
				}
			}
		}
	}
	Designer_RemoveMobileFormControls(mycontrols);
	return number;
}

function Designer_GetControlById(id) {
	var mycontrols = Designer.instance.subFormControls;
	Designer_AddMobileFormControls(mycontrols);
	var control = null;
	var myid = '';
	if(Designer.instance.subForm && Designer.instance.subForm.id){
		myid = Designer.instance.subForm.id;
	}else{
		myid = "subform_default";
	}
	for(var key in mycontrols){
		if(key != myid){
			for(var p = 0;p<mycontrols[key].length;p++){
				if(id == mycontrols[key][p].options.values.id){
					control = mycontrols[key][p];
					Designer_RemoveMobileFormControls(mycontrols);
					return control;
				}
			}
		}
	}
	Designer_RemoveMobileFormControls(mycontrols);
	return control;
}

// ************* 属性静态绘制方法 ************

Designer_AttrPanel.idDraw = function(value, attrs) {
	return ('<tr><td width="55" class="panel_td_title">ID</td><td>'
			//+'<label class="id_label" style="width:80%;background:#fff;height:20px;line-height:20px">'+ value + '</label>'
			+'<nobr><input style="width:78%;" class="inputread" readonly type="text" name="id" value="' + value + '" style="width:95%">'
			+'<input type="button" class="btnopt" onclick="Designer_AttrPanel_ShowIdEdit(this);" value="..."></nobr></td></tr>');
}

function Designer_AttrPanel_ShowIdEdit(dom) {
    var modifyTxt = Designer_Lang.modifyAttr;
    if (Designer.instance.control && Designer.instance.control.type == "relationChoose") {
        modifyTxt = Designer_Lang.modifyRelationAttr;
    }
	if (confirm(modifyTxt)) {
		dom.style.display = 'none';
		var input = dom.previousSibling;
		input.style.width = '95%';
		input.className = '';
		input.readOnly = false;
	}
}

Designer_AttrPanel.noAttrDraw = function() {
	return '<table class="panel_table_info"><tr><td style="text-align:center;">'+Designer_Lang.attrpanelNoAttrs+'</td></tr>';
}

Designer_AttrPanel.wrapTitle = function(name, attr, value, html) {
	if (attr.required == true) {
		html += '<span class="txtstrong">*</span>';
	}
	if (attr.hint) {
		html = '<div id="attrHint_' + name + '">' + attr.hint + '</div>' + html;
	}else if(attr.hintAfter){
		html = html + '<div id="attrHint_' + name + '">' + attr.hintAfter + '</div>';
	}
	return ('<tr><td width="55" class="panel_td_title">' + attr.text + '</td><td>' + html + '</td></tr>');
}
//扩增帮助
Designer_AttrPanel.helpDraw = function(name, attr, value) {
	return ('<tr><td width="95%" class="panel_td_title" colspan=\'2\' align=\''+attr.align+'\'>' + attr.text + '</td></tr>');
}
Designer_AttrPanel.textDraw = function(name, attr, value) {
	var html = "<input type='text' class='attr_td_text' name='" + name;
	if (attr.value != value && value != null) {
		html += "' value='" + value;
	} else {
		html += "' value='" + attr.value;
	}
	//当form里面只有一个input type=‘text’时，ie下默认按enter即提交内容，解决方案：1.设置keypress方法；2、增加多一个input type=‘text’，设为隐藏，此处多做一个隐藏
	if(typeof(attr.show) == 'boolean' && attr.show == false){
		html += "' style='width:95%;display:none ";
	}else{
		html += "' style='width:95%; ";
	}
	html += "'>";
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}


Designer_AttrPanel.checkboxDraw = function(name, attr, value) {
	var html = "";
	if(attr.checkboxHint){
		html += '<label isfor="true">';
	}
	html += "<input class='attr_td_checkbox' type='checkbox' name='" + name;
	html += "' value='true";
	if (value == null && attr.checked) {
			html += "' checked='checked";
	} else if (value == "true") {
			html += "' checked='checked";
	}
	if (attr.onclick) {
		html += "' onclick='" + attr.onclick;
	}
	html += "'/>";
	if(attr.checkboxHint){
		html += attr.checkboxHint + '</labe>';
	}
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

Designer_AttrPanel.checkGroupDraw = function(name, attr, value, form, attrs, values) {
	var html = '<input type="hidden" value="" name="' + name + '">';
	for (var i = 0, l = attr.opts.length; i < l; i ++) {
		var opt = attr.opts[i];
		if(typeof opt.isShow != 'undefined' && typeof opt.isShow == 'function' && opt.isShow(value) == false){
			//如果判定一个属性为方法
			 html += '<label style = "display: none;" isfor="true"><input type="checkbox" value="true" name="' + opt.name + '" ';
		}else{
			html += '<label isfor="true"><input type="checkbox" value="true" name="' + opt.name + '"';
		}
		if ((values[opt.name] == null && opt.checked) || values[opt.name] == "true") {
			html += ' checked="checked"';
		}
		if (opt.onclick != null) {
			html += ' onclick="' + opt.onclick + '"';
		}
		html += '>' + attr.opts[i].text + '</label>';
		if(typeof opt.isShow != 'undefined' && typeof opt.isShow == 'function' && opt.isShow(value) == false){
			html +='<br style = "display: none;">';
		}else{
			html +='<br>';
		}
	}
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

Designer_AttrPanel.selectDraw = function(name, attr, value) {
	var html = "<select name='" + name +"'";
	if (attr.multi == true) {
		html += "' multiple='multiple' size='" + attr.size +"'";
	}
	if (attr.onchange) {
		html += " onchange=\"" + attr.onchange + "\"";
	}
	html += " class='attr_td_select' style='width:95%'>";
	if (attr.opts) {
		for (var i = 0; i < attr.opts.length; i ++) {
			var opt = attr.opts[i];
			html += "<option value='" + opt.value;
			if (opt.style) {
				html += "' style='" + opt.style;
			}
			if (opt.value == value) {
				html += "' selected='selected";
			} else if (value == null && attr.value == opt.value) {
				html += "' selected='selected";
			}
			html += "'>" + opt.text + "</option>";
		}
	}
	html += "</select>";
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

Designer_AttrPanel.radioDraw = function(name, attr, value) {
	var buff = [];
	if (attr.opts) {
		for (var i = 0; i < attr.opts.length; i ++) {
			var opt = attr.opts[i];
			buff.push('<label isfor="true"><input type="radio" name="', name, '"');
			if (opt.value == value) {
				buff.push(' checked="checked" ');
			} else if ((value == null || value == '') && attr.value == opt.value) {
				buff.push(' checked="checked" ');
			}
			if (opt.onclick != null) {
				buff.push(' onclick="' + opt.onclick + '"');
			}
			buff.push(' value="', opt.value, '">', opt.text, '</label><br>');
		}
	}
	return Designer_AttrPanel.wrapTitle(name, attr, value, buff.join(''));
}

Designer_AttrPanel.textareaDraw = function(name, attr, value) {
	var html = "<textarea style='width:93%;' name='" + name + "' title='" + (attr.hint ? attr.hint.replace(/<br\/>/g, "\n") : "")
		+ "' class='attr_td_textarea'>";
	if (value != null && value.length > 0) {
		html += value;
	}
	html += "</textarea>";
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

Designer_AttrPanel.hiddenDraw = function(name, attr, value) {
	return "<input type='hidden' name='" + name + "' value='" + (value ? value : attr&&attr.value?attr.value:'') + "'>";
}

Designer_AttrPanel.dateDraw = function(name, attr, value) {
	var html = (attr.hint ? (attr.hint + "<br>") : "");
	html += "<input type='text' style='width:85%' class='attr_td_text' readonly name='" + name;
	if (attr.value != value && value != null) {
		html += "' value='" + value;
	} else {
		html += "' value='";
	}
	html += "'><a href='#' onclick='selectDate(\"" + name + "\");return false;'>"+Designer_Lang.attrpanelSelect+"</a>";
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

Designer_AttrPanel.labelDraw = function(name, attr, value, form, attrs, values, control) {
	attr.required = false;
	var buff = [];
	buff.push('<input type="text" style="width:93%" class="attr_td_text" name="', name, '"');
	if (attr.value != value && value != null) {
		buff.push(' value="' , value, '"');
	}
	if (value == null || value == '') { // 为空时，取消绑定
		values._label_bind = 'false';
	}
	if (values._label_bind == 'true') {
		buff.push(' readonly ');
	}
	buff.push( '><span class="txtstrong">*</span><br><label onclick="Designer_AttrPanel.Label_Onclick(this);" isfor="true">',
			'<input type="checkbox" name="_label_bind" value="true" ');
	if (values._label_bind == 'true') {
		buff.push(' checked ');
	}
	var labelText = Designer_AttrPanel.GetParentRelateWay(control);
	switch (labelText) {
		case 'left' : labelText = Designer_Lang.attrpanelLeftLabel; break;
		case 'up' : labelText = Designer_Lang.attrpanelUpLabel; break;
		case 'right' : labelText = Designer_Lang.attrpanelRightLabel; break;
		case 'down' : labelText = Designer_Lang.attrpanelDownLabel; break;
		case 'self' : labelText = Designer_Lang.attrpanelSelfLabel; break;
	}
	buff.push('>',labelText,'</label>');
	return Designer_AttrPanel.wrapTitle(name, attr, value, buff.join(''));
}

//新增明细表类型属性绘画方法 lmh 2011年3月1日 
Designer_AttrPanel.tabledoclistDraw = function(name, attr, value,panelForm,attrs,values,control) {
	var tableId = "TABLE_DocList_XForm"+control.options.domElement.id;
	var isFoundTable = false;
	var i = DocList_Info.length;
	while (i--) {
        if (DocList_Info[i] === tableId) {
        	isFoundTable =  true;
        }
    }
	if(!isFoundTable){
		DocList_Info[DocList_Info.length] = tableId;
	}
	var html = "";	
	html += "<table id=\""+tableId+"\" class=\"tb_normal\" width=\"90%\">";
	html += "<tr>";
	html += "<td>"+Designer_Lang.attrPanelSerialNumber+"</td>";
	html += "<td>"+Designer_Lang.attrPanelInputTypeText+"</td>";
	html += "<td>";
	html += "<a href=\"#\" onclick=\"DocList_AddRow();\">"+Designer_Lang.attrPanelAddBtn+"</a>";
	html += "</td>";
	html += "</tr>";
	html += "<!--基准行-->";
	html += "<tr KMSS_IsReferRow=\"1\" style=\"display:none\">";
	html += "<td KMSS_IsRowIndex=\"1\"></td>";
	html += "<td><input class=\"inputSgl\"  name=\"labelTextField"+control.options.domElement.id+"\" value=\"\"><label id=\"LKS_LabelIndexField"+control.options.domElement.id+"\" style=\"display:none\">new</label></td>";
	html += "<td>";
	html += "<a href=\"#\" onclick=\"DocList_DeleteRow();\">"+Designer_Lang.attrPanelDelBtn+"</a>";
	html += "<a href=\"#\" onclick=\"DocList_MoveRow(-1);\">"+Designer_Lang.attrPanelMoveUp+"</a>";
	html += "<a href=\"#\" onclick=\"DocList_MoveRow(1);\">"+Designer_Lang.attrPanelMoveDown+"</a>";
	html += "</td>";
	html += "</tr>";
	html += "<!--内容行-->";
		for(var i=0;i<control.labelTextArray.length;i++){
			
			html += "<tr KMSS_IsContentRow=\"1\">";
			html += "<td KMSS_IsRowIndex=\"1\">"+ (i+1) +"</td>";
			html += "<td><input class=\"inputSgl\" name=\"labelTextField"+control.options.domElement.id+"\" value=\""+control.labelTextArray[i]+"\"><label id=\"LKS_LabelIndexField"+control.options.domElement.id+"\" style=\"display:none\">"+control.labelIndexArray[i]+"</label></td>";
			html += "<td>";
			html += "<a href=\"#\" onclick=\"DocList_DeleteRow();\">"+Designer_Lang.attrPanelDelBtn+"</a>";
			html += "	<a href=\"#\" onclick=\"DocList_MoveRow(-1);\">"+Designer_Lang.attrPanelMoveUp+"</a>";
			html += "<a href=\"#\" onclick=\"DocList_MoveRow(1);\">"+Designer_Lang.attrPanelMoveDown+"</a>";
			html += "</td>";
			html += "</tr>";
		}
	html += "</table>";
			//标明开始用属性面板修改标签页 limh 2011年3月3日 
	control.isAttrPanel = true;
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

Designer_AttrPanel.GetParentRelateWay = function(control) {
	var parent = control.parent;
	if (parent && parent.getTagName() == 'table') {
		return parent.getRelateWay ? parent.getRelateWay(control) : (parent.relatedWay ? parent.relatedWay : 'left');
	} else if (parent && parent.getTagName() == 'div') {
		return Designer_AttrPanel.GetParentRelateWay(parent);
	}
	return 'self';
};

Designer_AttrPanel.showButtons = function(dom) {
	var designer = (new Function('return ' + dom.form.designerId))();
	designer.attrPanel.panel._changed = true;
	designer.attrPanel.panel.showBottom();
}
Designer_AttrPanel.resetValues = function(dom) {
	var designer = (new Function('return ' + dom.form.designerId))();
	return designer.attrPanel.panel.resetValues();
}

Designer_AttrPanel.Label_Onclick = function(dom) {
	if (dom.firstChild.checked) {
		dom.parentNode.firstChild.readOnly = true;
		var text = "";
		var designer = (new Function('return ' + dom.form.designerId))();
		if (designer && designer.attrPanel && designer.attrPanel.panel.control) {
			var textLabel = designer.attrPanel.panel.control.getRelatedTextControl();
			text = textLabel ? Designer.HtmlUnEscape(textLabel.options.values.content) : '';
		}
		dom.parentNode.firstChild.value = Designer.ClearStrSensiChar(text);
	} else {
		dom.parentNode.firstChild.readOnly = false;
	}
}

Designer_AttrPanel.colorDraw = function(name, attr, value, form, attrs, values) {
	var buff = [];
	buff.push('<input type="text" disabled style="width:78%;border:solid 1px #000000;background-color:', value ? value : attr&&attr.value?attr.value:'#000','">');
	buff.push('<input type="hidden" name="', name, '" value="', value ? value : attr&&attr.value?attr.value:'#000','" >');
	buff.push('<input type="button" class="btnopt" value="..." onclick="Designer_AttrPanel.colorPanelOpen(event);Designer_AttrPanel.resetPosition(event);">');
	return Designer_AttrPanel.wrapTitle(name, attr, value, buff.join(''));
}
Designer_AttrPanel.colorPanel = null;
Designer_AttrPanel.colorPanelInit = function() {
	if (Designer_AttrPanel.colorPanel == null) {
		Designer_AttrPanel.colorPanel = new Designer_AttrPanel.Color_Panel();
	}
}
Designer_AttrPanel.colorPanelOpen = function(event) {
	var obj = event.target ? event.target : event.srcElement;
	var ps = Designer.absPosition(obj);
	Designer_AttrPanel.colorPanelInit();
	Designer_AttrPanel.colorPanel.open(
			obj.previousSibling.value,
			Designer_AttrPanel.colorPanelCallBack,
			obj.previousSibling, ps.x , ps.y + obj.offsetHeight
	);
}
Designer_AttrPanel.colorPanelClose = function() {
	if (Designer_AttrPanel.colorPanel) {
		Designer_AttrPanel.colorPanel.close();
	}
}
Designer_AttrPanel.colorPanelCallBack = function(spectrumColor,args) {
	if(args && args.type == 'choose'){
		var colorDom = this._arg;
		if (colorDom.form && colorDom.previousSibling){
			colorDom.value = spectrumColor.toHexString();
			colorDom.previousSibling.style.backgroundColor = spectrumColor.toHexString();
			Designer_AttrPanel.showButtons(colorDom);
		}
	}
}
//重置颜色面板的定位，目前再属性面板打开颜色面板时top定位不正确，进行重置
Designer_AttrPanel.resetPosition = function(event){
	var obj = event.target ? event.target : event.srcElement;
	var ps = Designer.absPosition(obj);
	Designer_AttrPanel.colorPanel.resetPosition(ps.x,ps.y + obj.offsetHeight);
}
Designer_AttrPanel.Color_Panel = function() {
	var self = this;
	self.domElement = document.createElement('div');
	document.body.appendChild(self.domElement);
	with(self.domElement.style) {
		position = 'fixed'; display='none';
	}
	self.spectrum = $(self.domElement).spectrum({
        clickoutFiresChange: false,
        showInput: true,
        className: "full-spectrum",
        showInitial: true,
        showPalette: true,
        showSelectionPalette: true,
        maxPaletteSize: 10,
        preferredFormat: "hex",
        localStorageKey: "spectrum.xform",
        move: function (color) {
        },
        show: function () {

        },
        beforeShow: function () {

        },
        hide: function (color,args) {
        	self.callback.call(self,color,args);
        },

        palette: [
            ["rgb(0, 0, 0)", "rgb(67, 67, 67)", "rgb(102, 102, 102)", /*"rgb(153, 153, 153)","rgb(183, 183, 183)",*/
            "rgb(204, 204, 204)", "rgb(217, 217, 217)", /*"rgb(239, 239, 239)", "rgb(243, 243, 243)",*/ "rgb(255, 255, 255)"],
            ["rgb(152, 0, 0)", "rgb(255, 0, 0)", "rgb(255, 153, 0)", "rgb(255, 255, 0)", "rgb(0, 255, 0)",
            "rgb(0, 255, 255)", "rgb(74, 134, 232)", "rgb(0, 0, 255)", "rgb(153, 0, 255)", "rgb(255, 0, 255)"],
            ["rgb(230, 184, 175)", "rgb(244, 204, 204)", "rgb(252, 229, 205)", "rgb(255, 242, 204)", "rgb(217, 234, 211)",
            "rgb(208, 224, 227)", "rgb(201, 218, 248)", "rgb(207, 226, 243)", "rgb(217, 210, 233)", "rgb(234, 209, 220)",
            "rgb(221, 126, 107)", "rgb(234, 153, 153)", "rgb(249, 203, 156)", "rgb(255, 229, 153)", "rgb(182, 215, 168)",
            "rgb(162, 196, 201)", "rgb(164, 194, 244)", "rgb(159, 197, 232)", "rgb(180, 167, 214)", "rgb(213, 166, 189)",
            "rgb(204, 65, 37)", "rgb(224, 102, 102)", "rgb(246, 178, 107)", "rgb(255, 217, 102)", "rgb(147, 196, 125)",
            "rgb(118, 165, 175)", "rgb(109, 158, 235)", "rgb(111, 168, 220)", "rgb(142, 124, 195)", "rgb(194, 123, 160)",
            "rgb(166, 28, 0)", "rgb(204, 0, 0)", "rgb(230, 145, 56)", "rgb(241, 194, 50)", "rgb(106, 168, 79)",
            "rgb(69, 129, 142)", "rgb(60, 120, 216)", "rgb(61, 133, 198)", "rgb(103, 78, 167)", "rgb(166, 77, 121)",
            /*"rgb(133, 32, 12)", "rgb(153, 0, 0)", "rgb(180, 95, 6)", "rgb(191, 144, 0)", "rgb(56, 118, 29)",
            "rgb(19, 79, 92)", "rgb(17, 85, 204)", "rgb(11, 83, 148)", "rgb(53, 28, 117)", "rgb(116, 27, 71)",*/
            "rgb(91, 15, 0)", "rgb(102, 0, 0)", "rgb(120, 63, 4)", "rgb(127, 96, 0)", "rgb(39, 78, 19)",
            "rgb(12, 52, 61)", "rgb(28, 69, 135)", "rgb(7, 55, 99)", "rgb(32, 18, 77)", "rgb(76, 17, 48)"]
        ]		    
	});
	self.spectrumContainer = $(self.domElement).spectrum('container')[0];
	self.spectrumContainer.style.position = "absolute";
	self.spectrumContainer.style['z-index'] = "200";

}
Designer_AttrPanel.Color_Panel.prototype = {
	canClose : true,
	callback : function(){},
	open: function(defValue, fn, arg, x, y) {
		var self = this;
		$(self.domElement).spectrum('set',defValue);
		// 修正 x 轴超出问题
		var p_size = 20;
		var right_x_pos = x + (self.domElement.offsetWidth||436);
		if (right_x_pos > Designer.getDocumentAttr("offsetWidth") - p_size) {
			x = Designer.getDocumentAttr("offsetWidth") - (self.domElement.offsetWidth||436) - p_size;
		}
		// 定位
		var left = x ? (x+21.67+(self.domElement.offsetWidth||436)-Designer.getDocumentAttr("scrollLeft")>Designer.getDocumentAttr("clientWidth")?Designer.getDocumentAttr("clientWidth")-(self.domElement.offsetWidth||436)-21.67:x+21.67-Designer.getDocumentAttr("scrollLeft")) : 0 + 'px';
		var top = y ? (y+self.domElement.offsetHeight-Designer.getDocumentAttr("scrollTop")>Designer.getDocumentAttr("clientHeight")?Designer.getDocumentAttr("clientHeight")-self.domElement.offsetHeight:y-Designer.getDocumentAttr("scrollTop")) : 0 + 'px';
		$(self.domElement).spectrum('option','offset',{'left':left,'top':top});
		$(self.domElement).spectrum('show');
		self.callback = fn;
		self._arg = arg;
	},
	resetPosition: function(x,y){
		var self = this;
		$(self.domElement).spectrum('option','offset',{'top':y});
	},
	close : function() {
		var self = this;
		$(self.domElement).spectrum('hide');
	}
}

Designer_AttrPanel.defaultValueDraw = function(name, attr, value, form, attrs, values, control) {
	var buff = [];
	var isFormula = (values.formula != null && values.formula != '');
	if (attr.defaultValueHint) {
		buff.push('<div id="attrDefaultValueHint" ', 
			(isFormula ? 'style="display:none"' : ''),'>', attr.defaultValueHint, '</div>');
	}
	buff.push('<input type="hidden" name="formula"');
	if (isFormula) {
		buff.push(' value="' , values.formula, '"');
	}
	buff.push('><input type="text" style="width:95%" name="defaultValue"');
	if (value != null) {
		buff.push(' value="' , value, '"');
	}
	if (isFormula) {
		buff.push(' class="inputread"');
		buff.push(' readOnly ');
	} else {
		buff.push(' class="attr_td_text"');
	}
	buff.push('><br>');
	// 不用button元素，某些情况下，该元素（在form）在点击的时候，会重新加载form
	buff.push('<input type="button" onclick="Designer_AttrPanel.openFormulaDialog(this,\'', control.owner.owner._modelName, '\');" class="btnopt" value="');
	buff.push(Designer_Lang.attrpanelDefaultValueFormula);
	buff.push('" />');
	buff.push('<div id="reCalculate_field" ',
		(isFormula ? '' : 'style="display:none"')
		,'><label isfor="true"><input type="checkbox" value="true" name="reCalculate" ', 
		(values.reCalculate == 'true' ? 'checked' : ''),'>',Designer_Lang.attrpanelDefaultValueReCalculate,'</label><div>');
	return Designer_AttrPanel.wrapTitle(name, attr, value, buff.join(''));
}

// 带有说明信息的复选框
Designer_AttrPanel.msgCheckBoxDraw = function(name,value,checked,msg){
	var html = '';
	value = value ? value : "true";
	html += "<label isfor='true'><input type='checkbox' name='"+ name +"' value='"+ value +"'";
	if(checked == true){
		html += " checked='checked'";		
	}
	html += "/>";
	if(msg){
		html += msg;
	}
	html += "</label>";
	return html;
}

Designer_AttrPanel.openFormulaDialog = function(dom, modelName) {
	var defValue = null;
	if (dom.form['formula'].value == '') {
		defValue = dom.form['defaultValue'].value;
	}
	if (Designer_AttrPanel.resetValues(dom)) {
		//兼容金额类型(BigDecimal_Money) by 朱国荣 2016-8-18 start
		var dataType = dom.form['dataType'].value;
		if(dataType && dataType.indexOf('BigDecimal_') > -1){
			dataType = 'BigDecimal';
		}
		//end
		var oldValue = dom.form['formula'].value;
		var objs = Designer.instance.getObj(true);
		for (var i = 0; i < objs.length; i++) {
			var obj = objs[i];
			if (obj.name && obj.name.indexOf(".") > -1) {
				var name = obj.name.substring(obj.name.indexOf(".") + 1);
				if (name === dom.form['id'].value) {
					dataType += '[]';
					break;
				}
			}
		}
		Formula_Dialog('formula', 'defaultValue',objs , dataType, function() {
			if (dom.form['formula'].value != '') {
				dom.form['defaultValue'].readOnly = true;
				dom.form['defaultValue'].className = 'inputread';
				document.getElementById('reCalculate_field').style.display = '';
				var hint = document.getElementById('attrDefaultValueHint');
				if (hint) hint.style.display = 'none';
			} else {
				dom.form['defaultValue'].readOnly = false;
				dom.form['defaultValue'].className = 'attr_td_text';
				dom.form['reCalculate'].checked = false;
				document.getElementById('reCalculate_field').style.display = 'none';
				if (defValue != null) {dom.form['defaultValue'].value = defValue;}
				var hint = document.getElementById('attrDefaultValueHint');
				if (hint) hint.style.display = '';
			}
//			if (dom.form['formula'].value != dom.form['formula'].defaultValue) {
//				Designer_AttrPanel.showButtons(dom);
//			}
			if (dom.form['formula'].value != oldValue) {
				Designer_AttrPanel.showButtons(dom);
			}
		}, null, modelName);
	}
}
Designer_AttrPanel.fieldlayoutLabelDraw = function(name, attr, value, form, attrs, values, control) {
	attr.required = false;
	var buff = [];
	buff.push('<input type="text" style="width:93%" class="attr_td_text" name="', name, '"');
	if (attr.value != value && value != null) {
		buff.push(' value="' , value, '"');
	}
	if (value == null || value == '') { // 为空时，取消绑定
		values._label_bind = 'false';
	}
	if (values._label_bind == 'true') {
		buff.push(' readonly ');
	}
	buff.push( '><br><label onclick="Designer_AttrPanel.Label_Onclick(this);" isfor="true">',
			'<input type="checkbox" name="_label_bind" value="true" ');
	if (values._label_bind == 'true') {
		buff.push(' checked ');
	}
	var labelText = Designer_AttrPanel.GetParentRelateWay(control);
	switch (labelText) {
		case 'left' : labelText = Designer_Lang.attrpanelLeftLabel; break;
		case 'up' : labelText = Designer_Lang.attrpanelUpLabel; break;
		case 'right' : labelText = Designer_Lang.attrpanelRightLabel; break;
		case 'down' : labelText = Designer_Lang.attrpanelDownLabel; break;
		case 'self' : labelText = Designer_Lang.attrpanelSelfLabel; break;
	}
	buff.push('>',labelText,'</label>');
	return Designer_AttrPanel.wrapTitle(name, attr, value, buff.join(''));
}