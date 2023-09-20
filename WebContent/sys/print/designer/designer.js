(function(window, undefined){
	/**
	 * 打印设计器
	 */
	var Designer=function(id){
		this.id = id;
		this.fdKey='';
		this.modelName='';
		this.hasInitialized = false;
		
		this.controlDefinition = null;
		this.control = null;// 当前控件对象
		this.controls = [];
		
		this.toolBar = null;// 工具栏
		this.toolBarDomElement = null;
		this.$toolbarDom=null;
		
		this.toolBarAction = '';// 当前工具栏选中的操作类型

		this.builder = null;// 绘制区
		this.builderDomElement = null;
		this.$builderDom=null;
		
		//快捷对象 
		this.shortCuts = null;
		//动画对象
		this.effect = null;
		
		//内部属性、方法
		this._modelName="";
		this._drawFrame=_drawFrame;
		this._adjustBuildArea=_adjustBuildArea;
		
		
		//事件
		this._eventListeners={};
		
		//exports
		this.init=init;
		this.setModel=setModel;
		
		
		
		
		this.addListener = addListener;// 增加监听器
		this.removeListener = removeListener;// 移除监听器
		this.fireListener = fireListener;// 通知监听器
		
	};
	
	Designer.instance = new Designer('Sys.Print.Designer.instance');
	
	//初始化设计器
	function init(parentElement){
		//绘制设计区
		this._drawFrame(parentElement);
		//整个页面不能选择
		//this.builderDomElement.onselectstart = function(){return false;};
		//屏蔽右键
//		sysPrintUtil.addEvent(document,'contextmenu',function(event){return false;});
		var self=this;
		Com_AddEventListener(window,"resize",function(){
			//self._adjustBuildArea();
		});
		
		this.hasInitialized = true;
	}
	
	//绘制设计器区域
	function _drawFrame(parentElement){
		var _parentElement = parentElement || null, buf = new Array();
		
		buf.push('<table onselectstart="return false" width="100%" height="100%" cellspacing="0" cellpadding="0" border="0">');
		buf.push('<tr><td colspan="3" id="sys_print_designer_toolbar" height="30" valign="top"></td></tr>');
		buf.push('<tr><td valign="top">');
		buf.push('<div id="sys_print_designer_draw" class="printscroll" style="overflow-y:scroll;overflow-x:hidden;height:600px;display:inline-block;width:100%;min-height:400px;padding-bottom:20px;border: 1px solid #C0C0C0;border-right:0px;"');
		buf.push(' onmousedown="sysPrintDesigner.instance.builder.onDrawMouseDown();"');
		buf.push(' onmousemove="sysPrintDesigner.instance.builder.onDrawMouseMove()"');
		buf.push(' onmouseup="sysPrintDesigner.instance.builder.onDrawMouseUp(event)"');
		//buf.push(' onmousemove="' + this.id + '.bindEvent(event, \'builder\', \'mousemove\');"');
		//buf.push(' onmouseup="' + this.id + '.bindEvent(event, \'builder\', \'mouseup\');"');
		//buf.push(' ondblclick="' + this.id + '.bindEvent(event, \'builder\', \'dblclick\');"');
		buf.push('>&nbsp;</div></td></tr>');
		buf.push('</table>');
		buf.push('<div id="sys_print_designer_hidden" style="display:none"></div>');
		
		if (_parentElement == null) _parentElement = document.body || document.documentElement;
		_parentElement.innerHTML = buf.join('');
		
		this.toolBarDomElement = sysPrintUtil.$('sys_print_designer_toolbar');
		this.$toolbarDom=$('#sys_print_designer_toolbar');
		
		
		this.builderDomElement = sysPrintUtil.$('sys_print_designer_draw');
		this.$builderDom=$('#sys_print_designer_draw');
		//this._adjustBuildArea();
		
		//绘制区
		if (typeof(sysPrintDesignerBuilder) != 'undefined') this.builder = new sysPrintDesignerBuilder(this);
		//快捷键
		if (typeof(sysPrintShortCuts) != 'undefined') this.shortCuts = new sysPrintShortCuts();
		//动画对象
		if (typeof(sysPrintAnimation) != 'undefined') this.effect = new sysPrintAnimation(this);
		//工具栏
		if (typeof(sysPrintDesignerToolbar) != 'undefined') this.toolBar = new sysPrintDesignerToolbar(this);
		//属性框
		if (typeof(sysPrintAttrPanel) != 'undefined') this.attrPanel = new sysPrintAttrPanel(this);
		//右键菜单
		if (typeof(sysPrintRightMenu) != 'undefined') this.rightMenu = new sysPrintRightMenu(this, null, sysPrintDesignerConfig.menus);
		//_adjustBuildArea();
		
	}
	
	function _adjustBuildArea(){
		this.builderDomElement.style.width = '100%';
		this.builderDomElement.style.height = '100%';
		if (this.toolBar)
			this.toolBarDomElement.style.height = this.toolBar.domElement.offsetHeight + 'px';
	}
	
	//设置模块
	function setModel(modelName){
		this._modelName = modelName || null;
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

	
	window.sysPrintDesigner=Designer;//打印机制设计器
	
	
})(window);