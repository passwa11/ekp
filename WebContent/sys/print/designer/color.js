(function(window, undefined){
	/**
	 * 颜色选择器面板
	 **/
	var ColorPanel = function(id){
		this.id = id;
		this.domElement = null;
		this.colorHex = ['00','33','66','99','CC','FF'];
		this.spColorHex = ['FF0000','00FF00','0000FF','FFFF00','00FFFF','FF00FF'];
		this.current = null;
		this.disColor = null;
		this.hexColor = null;
		//事件
		this._eventListeners={};
		
		
		
		this.init = init;
		
		//this.init();
		
	};
	
	
	
	ColorPanel.prototype = {
		canClose : true,
		addListener : addListener,// 增加监听器
		removeListener : removeListener,// 移除监听器
		fireListener : fireListener,// 通知监听器
		
		open: function(defValue, fn, designer, x, y) {
			this.designer = designer;
			
			this.init();
			this._setColor(defValue);
			
			this.targetFn = fn;
			this.domElement.style.display = '';
			// 修正 x 轴超出问题
			var p_size = 20;
			var right_x_pos = x + this.domElement.offsetWidth;
			if (right_x_pos > document.body.offsetWidth - p_size) {
				x = document.body.offsetWidth - this.domElement.offsetWidth - p_size;
			}
			// 定位
			this.domElement.style.left = (x ? x : 0) + 'px';
			this.domElement.style.top = (y ? y : 0) + 'px';
			
			this.domElement.focus();
		},
		_setColor : function(value) {
			this.disColor.style.backgroundColor = value;
			this.hexColor.value = value.toUpperCase();
		},
		close : function() {
			this.domElement.style.display = 'none';
		},
		toggle:function(){
			if(this.domElement.style.display=='none'){
				this.domElement.style.display='';
			}else{
				this.close();
			}
		},
		onOver : function (event) {
			var obj = event.target ? event.target : event.srcElement;
			if ((obj.tagName == "TD") && (this.current != obj)) {
				if (this.current != null) {
					this.current.style.backgroundColor = this.current._background;
				}
				obj._background = obj.style.backgroundColor;
				this._setColor(obj.style.backgroundColor);
				obj.style.backgroundColor = "white";
				this.current = obj;
			}
		},
		onOut : function() {
			if (this.current!=null)
				this.current.style.backgroundColor = this.current._background.toUpperCase();
		},
		onClick : function(event) {
			var obj = event.target ? event.target : event.srcElement;
			if (obj.tagName == "TD") {
				var clr = obj._background;
				clr = clr.toUpperCase();
				if (this.targetFn) {
					this.targetFn(clr, this.designer);
				}
				this.close();
			}
		}
	}
	
	
	
	function init(){
		this.domElement = document.createElement('div');
		$('#sysPrintdesignPanel')[0].appendChild(this.domElement);
		with(this.domElement.style) {
			position = 'absolute'; display='none'; width = '253px'; height = '177px'; zIndex = '200';
		}
		var panel = [];
		panel.push('<table width=253 border="0" cellspacing="0" cellpadding="0" bordercolor="000000" ');
		panel.push('style="border:1px #000000 solid;border-bottom:none;border-collapse: collapse">');
		panel.push('<tr height=30><td colspan=21 bgcolor=#cccccc>');
		panel.push('<table cellpadding="0" cellspacing="1" border="0" style="border-collapse: collapse">');
		panel.push('<tr><td width="3"><td><input type="text" name="DisColor" size="6" disabled style="border:solid 1px #000000;background-color:#ffff00"></td>');
		panel.push('<td width="3"><td><input type="text" name="HexColor" size="7" style="border:inset 1px;font-family:Arial;" value="#000000"></td>');
		panel.push('</tr></table></td></table>');
		panel.push('<table border="1" cellspacing="0" cellpadding="0" style="border-collapse: collapse;cursor:pointer;" bordercolor="000000"');
		panel.push(' onmouseover="printColorPanelInstance.onOver(event);" onmouseout="printColorPanelInstance.onOut();"');
		panel.push(' onclick="printColorPanelInstance.onClick(event);">');
	
		for (var i = 0; i < 2; i ++) {
			for (var j = 0; j < 6; j ++) {
				panel.push('<tr height=12>');
				panel.push('<td width=11 style="background-color:#000000">');
				if ( i == 0 ){
					panel.push('<td width=11 style="background-color:#', this.colorHex[j], this.colorHex[j], this.colorHex[j], '">');
				} else {
					panel.push('<td width=11 style="background-color:#', this.spColorHex[j], '">');
				}
				panel.push('<td width=11 style="background-color:#000000">');
				for (var k = 0; k < 3; k ++) {
					for ( var l = 0; l < 6; l ++) {
						panel.push('<td width=11 style="background-color:#', this.colorHex[k + i*3], this.colorHex[l], this.colorHex[j], '">');
					}
				}
			}
		}
		panel.push('</table>');
		this.domElement.innerHTML = panel.join('');
		this.disColor = this.domElement.getElementsByTagName('input')[0];
		this.hexColor = this.domElement.getElementsByTagName('input')[1];
		
		var self = this;
		this.addListener('blur',function(){
			if (self.canClose)
				self.close();
		});
		this.addListener('mouseout',function(){
			self.canClose = true;
		});
		this.addListener('mouseover',function(){
			self.canClose = false;
		});
	}
	//增加监听器
	function addListener(name, fun){
		var evt = this._eventListeners[name];
		if (evt == null) {
			evt = [];
			this._eventListeners[name] = evt;
		}
		evt.push(fun);
	}
	
	//移除监听器
	function removeListener(name, fun){
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
	
	//通知监听器
	function fireListener(name) {
		var evt = this._eventListeners[name];
		if (evt != null) {
			for (var i = 0; i < evt.length; i ++) {
				evt[i](this);
			}
		}
	}
	
	window.sysPrintColorPanel = ColorPanel;
	window.printColorPanelInstance=null;
	if(!window.printColorPanelInstance)
		printColorPanelInstance = new ColorPanel('sys.print.colorPanel.instance');
})(window);