(function(window,undefined){
	/**
	 * 属性面板
	 * @author linxiuxian
	 * @date 2016.5.10
	 **/
	
	var attrPanel = function(designer){
		this.owner = designer || null;//设计器对象
		this.type = 'attrPanel';
		this.control = null;//当前面板对应的control
		this.isControl = true;
		this.domElement = null;
		this.$titleBar = null;
		this.$mainWap = null;
		this.setNoSelectInfo = setNoSelectInfo;
		
		this.drawAttrs = drawAttrs;
		this.drawDomAttrs = drawDomAttrs;
		this.focusPanel = function(){};
		//exports
		this.panelShow = panelShow;
		//init
		this.init();
	};
	attrPanel.prototype = {
		init : function() {
			this.domElement = document.createElement('div');
	
			$('#sysPrintdesignPanel')[0].appendChild(this.domElement);
			with(this.domElement.style) {
				position = 'absolute'; display = 'none'; top = '35px'; left = '5px'; zIndex = '101';
			}
			
			this.initTitle();
			this.initMain();
			this.initBottom();
			this.initFormChange();
			$(this.domElement).draggable({cursor: 'move',containment:'#sys_print_designer_draw'});
		},
		draw:draw,
		initTitle : initTitle,
		
		initMain : initMain,

		initBottom : initBottom,
		initBottomBar:initBottomBar,
		initBottomBarAction:initBottomBarAction,
		initFormChange:initFormChange,//表单变化事件
		hideBottomBar:hideBottomBar,
		showBottomBar:showBottomBar,
		
		setTitle:setTitle,
		setMain:setMain,
		saveValues:saveValues,//保存属性
		hide : function(event) {
			this.domElement.style.display = 'none';
			this.destroy();
		},
		show : function() {
			this.domElement.style.display = 'block';
		},
		destroy:function(){
			this.$mainBox.empty();//销毁属性面板内容
		}
	};
	
	function initTitle(){
		var title = [];
		title.push('<div style="cursor:move;"><div class="panel_title"><div class="panel_title_left"><div class="panel_title_right"></div>');
		title.push('<div class="panel_title_center"><div class="panel_title_text">');
		title.push('<span></span></div></div></div><div class="panel_title_btn_bar">');
		title.push('<a href="javascript:void(0)" onmousedown="sysPrintDesigner.instance.attrPanel.hide()" title="' + DesignerPrint_Lang.panelCloseTitle + '" class="panel_title_close"></a>');
		title.push('</div></div><div>');
		this.$titleBar = $(title.join(''));
		this.domElement.appendChild(this.$titleBar[0]);
	}
	function setTitle(name){
		var html = '<span>' + name + '</span>';
		this.$titleBar.find('.panel_title_text').html(html);
	}
	function initMain(){
		var main = [];
		main.push('<div><form style="margin:0px;padding:0px;">');
		main.push('<div class="panel_main"><div class="panel_main_box"></div></div></form><div>');
		this.$mainWap = $(main.join(''));
		this.$mainBox = this.$mainWap.find('.panel_main_box');
		this.domElement.appendChild(this.$mainWap[0]);
		//注意作用
		this.$mainWap.find('form')[0].designerId = this.owner.id;
	}
	function setMain(html){
		this.$mainWap.find('.panel_main_box').html(html);
	}
	function initBottom(){
		this.initBottomBar();
		
		var bottom_bottom = document.createElement('div');
		bottom_bottom.innerHTML = '<div class="panel_bottom_left"><div class="panel_bottom_right"></div>'
			+ '<div class="panel_bottom_center"></div></div>';
		bottom_bottom.className = 'panel_bottom';
		this.domElement.appendChild(bottom_bottom);
	}
	function initBottomBar(){
		var html = [];
		html.push('<div class="panel_bottom"><div class="panel_bottom_main"><div class="panel_bottom_for_ie6">');
		html.push('<button type="button" class="panel_success" title="' + DesignerPrint_Lang.attrpanelSuccess + '" value="&amp;nbsp;" style="margin: 2px 8px 0px 0px;"></button>');
		html.push('<button type="button" class="panel_apply" title="' + DesignerPrint_Lang.attrpanelApply + '" value="&amp;nbsp;" style="margin: 2px 8px 0px;"></button>');
		html.push('<button type="button" class="panel_cancel" title="' + DesignerPrint_Lang.attrpanelCancel + '" value="&amp;nbsp;" style="margin: 2px 0px 0px 8px;"></button>');
		html.push('</div></div></div>');
		this.$bottomBar = $(html.join(''));
		this.domElement.appendChild(this.$bottomBar[0]);
		
		this.initBottomBarAction();
	}
	function initBottomBarAction(){
		var self = this;
		this.$bottomBar.find('.panel_success')[0].onclick = function() {
			if (self.saveValues()) {
				//增加属性面板成功关闭时的回调函数
				var control = self.control;
				if(control&&control.onAttrSuccess){
					control.onAttrSuccess();
				}
				self.hide();
			}
		};
		this.$bottomBar.find('.panel_apply')[0].onclick = function() {
			if (self.saveValues()) {
				self.hideBottomBar();
			}
		};
		this.$bottomBar.find('.panel_cancel')[0].onclick = function() {
			self.hide();
		};
	}
	function initFormChange(){
		var self = this;
		this._changed = false;
		var panelForm = this.$mainWap.find('form')[0];
		sysPrintUtil.addEvent(panelForm, 'keyup', function(event) {
			event.cancelBubble = true;
			if (self._changed) return;
			var dom = event.srcElement || event.target;
			var tagName = dom.tagName.toLowerCase();
			if (((tagName == 'input' && dom.type == 'text') || tagName == 'textarea')
				&& (dom.defaultValue != dom.value)) {
				self.showBottomBar();
				self._changed = true;
			}
		});
		sysPrintUtil.addEvent(panelForm, 'mouseup', function(event) {
			event.cancelBubble = true;
			if (self._changed) return;
			var dom = event.srcElement || event.target;
			var tagName = dom.tagName.toLowerCase();
			if ((tagName == 'input' && (dom.type == 'checkbox' || dom.type == 'radio')) || tagName == 'a') {
				self.showBottomBar();
				self._changed = true;
			} else if (tagName == 'label' && dom.getAttribute('isfor') == 'true') {
				self.showBottomBar();
				self._changed = true;
			}
		});
		var changeFun = function(event) {
			event = event || window.event;
			self.showBottomBar();
			self._changed = true;
			sysPrintUtil.removeEvent(event.srcElement || event.target, "change", changeFun);
		}
		sysPrintUtil.addEvent(panelForm, 'mousedown', function(event) {
			event.cancelBubble = true;
			if (self._changed) return;
			var dom = event.srcElement || event.target;
			var tagName = dom.tagName.toLowerCase();
			if (tagName == 'select') {
				sysPrintUtil.removeEvent(dom, "change", changeFun);
				sysPrintUtil.addEvent(dom, "change", changeFun);
			}
		});
	}
	function hideBottomBar(){
		this.$bottomBar.hide();
		this._changed=false;
	}
	function showBottomBar(){
		this.$bottomBar.show();
		this._changed=true;
	}
	function setNoSelectInfo() {
		this.setTitle(DesignerPrint_Lang.attrpanelTitle);
		this.setMain(
			'<table class="panel_table_info"><tr><td style="text-align:center;">' + DesignerPrint_Lang.attrpanelNoSelect + '</td></tr></table>'
		);
		//this.hideBottom();
	}
	//属性面板内容绘制
	function draw(){
		var control = this.owner.builder.selectControl;
		if(!control){ 
			this.setNoSelectInfo();
			this.hideBottomBar();
			return;
		}
		//注意:属性面板对应的control
		this.control = control;
		//设置标签属性
		if(control.options.showDomAttr && this.owner.builder.$selectDomArr.length > 0){
			if (control.domAttrs == null) return;
			this.isControl = false;
			this.destroy();
			this.setMain(this.drawDomAttrs());
			return;
		}
		this.isControl = true;
		this.attrs = control.attrs;
		this.showAttr = control.options.showAttr ? control.options.showAttr : 'default';
		this.values = control.options.values || {};
		if (control.info) {
			this.setTitle(DesignerPrint_Lang.attrpanelTitlePrefix + control.info.name);
		}
		this.destroy();
		this.setMain(this.drawAttrs());
		
	}
	
	function drawDomAttrs() {
		var hasAttr = false;
		var $selectDomArr = this.owner.builder.$selectDomArr;
		var dom =  $selectDomArr[0][0];
		var tagName = dom.tagName.toLowerCase();
		this.attrs = this.control.domAttrs[tagName];
		if (this.control.domAttrs.info) {
			this.setTitle(DesignerPrint_Lang.attrpanelTitlePrefix + this.control.domAttrs.info.name + " - " + this.control.domAttrs.info[tagName]);
		}
		var html = '<table class="panel_table">';
		var domValues = {};
		for (var i = 0; i < $selectDomArr.length; i ++) {
			var _dom = $selectDomArr[i][0];
			if (i == 0) {
				for (var _name in this.attrs) {
					var domAttrName = (_name == 'className' ? "oldClassName" : _name);
//					domValues[domAttrName] = _dom[domAttrName] == '' ? this.attrs[_name].value : _dom[domAttrName];
					var _attValue = _dom.getAttribute(domAttrName);
					if(domAttrName=='oldClassName'){
						if(!_attValue && _dom.className){
							_attValue = _dom.className.indexOf('td_normal_title')>-1 ? 'td_normal_title':'';
						}
					}
					domValues[domAttrName] = !_attValue? this.attrs[_name].value : _attValue;
				}
			} else {
				
			}
		}

		for (var name in this.attrs) {
			var attr = this.attrs[name];
			var domAttrName = (name == 'className' ? "oldClassName" : name);
			html += attrPanel[attr.type + 'Draw'](name, attr, domValues[domAttrName]);
			hasAttr = true;
		}
		if (!hasAttr) {
			html = attrPanel.noAttrDraw();
		} else {
			html += '</table>';
		}
		dom = null;
		return html;
	}
	
	function drawAttrs() {
		var html = '<table class="panel_table">';
		var hasAttr = false;
		for (var name in this.attrs) {
			var attr = this.attrs[name];
			var sa = attr.showAttr ? attr.showAttr : 'default';
			if (attr.show && (this.showAttr == 'all' || sa == this.showAttr)) {
				if (attr.type == 'self') {
					html += attr.draw(name, attr, this.values[name], '', this.attrs, this.values, this.control);
				} else {
					html += attrPanel[attr.type + 'Draw'](name, attr, this.values[name]);
				}
				hasAttr = true;
			}
		}
//		if (this.values.id != null) {
//			html += attrPanel.idDraw(this.values.id, this.attrs);
//			hasAttr = true;
//		}
		if (!hasAttr) {
			html = attrPanel.noAttrDraw();
		} else {
			html += '</table>';
		}
		return html;
	}
	//属性值更新
	function saveValues(){
		if (!this._changed) return true; // == 无修改不重设
		var elems = this.$mainWap.find('form')[0].elements;
		var setNewValues = false;
		var newValues = {};
		for (var i=0; i< elems.length;i++) {
			setNewValues = true;
			var elem = elems[i];
			var value = elem.value;
			var controls = [];
			if (elem.name == 'id') {
				continue;
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

		if (!this.isControl) {
			var doms = this.owner.builder.$selectDomArr;
			for (var i = 0; i < doms.length; i ++) {
				var dom = doms[i][0];
				for (var name in newValues) {
					// 空值不更新
					if (newValues[name] != '' && newValues[name] != null) {
						if (name != 'className'){
							if(name.indexOf('style_')>=0){
								if(newValues[name]=='null'){
									$(dom).css(name.replace("style_",""),"");
								}
								else{
									try{
										dom.style[name.replace("style_","")]=newValues[name];
									}catch(e){
										//当属性值写法不规范的时候，上面的写法会报错，例如:dom.style[width]=test;
										dom.setAttribute(name.replace("style_",""),newValues[name]);
									}
								}
							}
							//IE下可以查看设置值不默认选中问题 #24090
							//dom[name] = newValues[name]; 当name是“width”的时候，ie下报参数无效的错误
							dom.setAttribute(name,newValues[name]);
							//兼容下多浏览器
							$(dom).attr(name,newValues[name]);
						}else {
							dom.setAttribute('oldClassName',newValues[name]);
							$(dom).removeClass('td_normal_title td_normal').addClass(newValues[name]);
						}
					}
				}
			}
			this._changed = false;
			return true;
		}

		//修改控件属性
		for (var _name in newValues) {
			this.values[_name] = newValues[_name];
		}
		if (setNewValues) {
			//注意此时的panel与当前选中control的对应关系
			this.control.options.values = this.values;
		}
		this.control.reRender();
		this._changed = false;
		return true;
	}
	
	function panelShow(){
		var self = this;
		this.draw();
		//动态效果
		this.owner.effect.setOptions({onFinish: function(){self.focusPanel();}});
		this.owner.effect.show(this.domElement);
		//this.show();
	}
////////////静态方法///////////////////
	attrPanel.showButtons = function(dom) {
		var designer = (new Function('return ' + dom.form.designerId))();
		designer.attrPanel._changed = true;
		designer.attrPanel.showBottomBar();
	}
	attrPanel.noAttrDraw = function() {
		return '<table class="panel_table_info"><tr><td style="text-align:center;">' + DesignerPrint_Lang.attrpanelNoAttrs + '</td></tr>';
	}
	attrPanel.idDraw = function(value, attrs) {
		return ('<tr><td width="25%" class="panel_td_title">ID</td><td>'
				+'<nobr><input style="width:78%;" class="inputread" readonly type="text" name="id" value="' + value + '" style="width:95%">'
				+'</nobr></td></tr>');
	}
	attrPanel.wrapTitle = function(name, attr, value, html) {
		if (attr.required == true) {
			html += '<span class="txtstrong">*</span>';
		}
		if (attr.hint) {
			html = '<div id="attrHint_' + name + '">' + attr.hint + '</div>' + html;
		}
		return ('<tr><td width="25%" class="panel_td_title">' + attr.text + '</td><td>' + html + '</td></tr>');
	}
	attrPanel.helpDraw = function(name, attr, value) {
		return ('<tr><td width="95%" class="panel_td_title" colspan=\'2\' align=\''+attr.align+'\'>' + attr.text + '</td></tr>');
	}
	attrPanel.textDraw = function(name, attr, value) {
		var html = "<input type='text' style='width:95%' class='attr_td_text' name='" + name;
		if (attr.value != value && value != null) {
			html += "' value='" + value;
		} else {
			html += "' value='" + attr.value;
		}
		html += "'>";
		return attrPanel.wrapTitle(name, attr, value, html);
	}
	attrPanel.radioDraw = function(name, attr, value) {
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
				buff.push(' value="', opt.value, '">', opt.text, '</label><br>');
			}
		}
		return attrPanel.wrapTitle(name, attr, value, buff.join(''));
	}
	
	//对外暴露
	window.sysPrintAttrPanel = attrPanel;
})(window);