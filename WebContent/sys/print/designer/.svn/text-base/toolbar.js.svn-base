(function(window, undefined){
	
	var ICON_SIZE = 24;
	
	/**
	 * 打印设计器工具栏
	 */
	var ToolBar=function(designer){
		this.owner = designer || null;
		this.buttons = [];
		
		this.selectedButton = null;
		this.domElement = null;
		
		//exports
		this.init=init;
		this.initButtons=initButtons;
		this.addLine=addLine;
		this.addButton=addButton;
		this.getButton=getButton;
		this.selectButton = selectButton;
		this.cancelSelect = cancelSelect;
		this.getCursorImgPath = getCursorImgPath;
		
		this.init();//初始化
		this.initButtons(sysPrintDesignerConfig.buttons, sysPrintDesignerConfig.operations);
		
	};
	
	//初始化工具栏
	function init(){
		var domElement, row;
		with(this.owner.toolBarDomElement.appendChild(domElement = document.createElement('table'))) {
			setAttribute('cellSpacing', '1'); setAttribute('cellPadding', '0');
			style.top = '0'; style.left = '0'; style.zIndex = '90';
		};
		domElement.style.width = '100%';
		domElement.style.border = '0px';
		//插入行
		row = domElement.insertRow(-1);
		row.className = 'buttonbar_main';
		//生成放入工具栏的单元格
		this.domElement = row.insertCell(-1);
		
		Com_AddEventListener(window,'scroll',function(){
			domElement.style.top = document.body.scrollTop;
		});
		
		var resetToolBar = function() {
			if (domElement.style.top != document.body.scrollTop) {
				domElement.style.top = document.body.scrollTop;
			}
			setTimeout(resetToolBar, 800);
		};
		setTimeout(resetToolBar, 800);
		
	}
	
	//初始化按钮
	function initButtons(buttons, operations){
		for (var name in buttons) {
			var btns = buttons[name];
			if(name!='common'){
				this.addLine();
			}
			for (var i = 0; i < btns.length; i ++) {
				var btn = btns[i];
				if (typeof btn == 'string')
					this.addButton(btn, operations[btn]);
				else
					this.addButton(btn.name, btn);
			}
		}
	}
	
	//添加一条分隔线
	function addLine(){
		var newElem = document.createElement("div");
		this.domElement.appendChild(newElem);
		newElem.className = "edui-btns-splits";
	}

	//添加一个操作按钮
	function addButton(name, config){
		return new Toolbar_Button(this, name, config);
	}
	
	//获得指定按钮
	function getButton(name) {
		for (var i = this.buttons.length -1; i >=0; i --) {
			if (this.buttons[i].name == name)
				return this.buttons[i];
		}
	}
	//功能：或许选择按钮鼠标图片
	function getCursorImgPath() {
		if (this.selectedButton && this.selectedButton.config.cursorImg != null) {
			return this.selectedButton.config.cursorImg;
		}
		return null;
	}
	function selectButton(name) {
		var button = this.getButton(name);
		this.cancelSelect();
		if (button)
			button.setSelected(true);
	}
	//取消选中
	function cancelSelect() {
		if (this.selectedButton) {
			this.selectedButton.setSelected(false);
		}
	}
	
	
	/**
	 * 按钮
	 */
	var Toolbar_Button=function(parent, name, config){
		
		this.name = name;
		var imgIndex = config.imgIndex;
		this.toolbar = parent;
		parent.buttons.push(this);
		
		this.config = config;
		this.selected = false;
		
		var newElem = document.createElement("div");
		parent.domElement.appendChild(newElem);
		newElem.className = "button_nor";
		//增加控件端可以控制 工具栏按钮的宽度属性 多浏览器支持
		if(config&&config.domWidth){
			newElem.style.width=config.domWidth;
		}
		if (config.title && config.sampleImg == null) {
			newElem.title = config.title;
		}
		this.domElement = newElem;
		
		if (config.childElem == null) {
			var childElem = document.createElement("div");
			newElem.appendChild(childElem);
			if(config.image){
				$(childElem).addClass(config.image);
			}else{
				if(config.icon_s){
					childElem.className = "edui-icon edui-icon-s";
					childElem.style.backgroundPosition = "0 " + (- imgIndex * (16 + 3) ) + "px";
				}else{
					childElem.className = "edui-icon edui-icon-m";
					childElem.style.backgroundPosition = "0 " + (- imgIndex * (ICON_SIZE + 3) ) + "px";
				}
			}
		} else {
			newElem.appendChild(config.childElem(parent.owner));
		}
		
		//热键
		if(config.hotkey){
			var self = this;
			this.toolbar.owner.shortCuts.add(config.hotkey,
					function(){config.run(self.toolbar.owner, self);},
					{'target':this.toolbar.owner.builderDomElement});
		}
		
		this.initActions();
		
	};
	
	/**
	 * 按钮方法设置
	 */
	Toolbar_Button.prototype = {
		// 初始化操作
		initActions : function() {
			var self=this;
			this.domElement.onclick = function() {
				if (self.toolbar.selectedButton && self.toolbar.selectedButton != this) {
					self.toolbar.selectedButton.setSelected(false);
				}
				if (self.config.run)
					self.config.run(self.toolbar.owner, self);
			};
			this.domElement.onmouseover = function() {
				this.className = "button_sel";
			};

			this.domElement.onmouseout = function() {
				this.className = self.selected ? "button_sel" : "button_nor";
			};
		},
		// 设置是否被选择
		setSelected : function(selected) {
			this.domElement.className = selected ? "button_sel" : "button_nor";
			this.selected = selected;
			if (selected) {
				this.toolbar.selectedButton = this;
				this.toolbar.owner.toolBarAction = this.name;
				
				this.toolbar.owner.builder._actionType='createControl';
			} else {
				this.toolbar.selectedButton = null;
				this.toolbar.owner.toolBarAction = '';
				
				this.toolbar.owner.builderDomElement.style.cursor="default";
			}
		}
	};
	
	
	
	window.sysPrintDesignerToolbar=ToolBar;
	window.sysPrintDesignerToolbarButton=Toolbar_Button;
	
	
})(window);