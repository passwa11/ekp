/**********************************************************
功能：工具栏对象和操作按钮对象
使用：
	
作者：傅游翔
创建时间：2009-03-01
修改记录：
	1.重新梳理了下工具栏对象和按钮对象
		修改者：龚健，修改日期：2009-03-16
**********************************************************/
function Designer_Toolbar(designer) {
	this.owner = designer || null;
	this.domElement = null;
	this.buttons = [];
	this.selectedButton = null;
	this.isShowAdvancedButton = false;

	//公共方法
	this.addLine = Designer_Toolbar_AddLine;			//添加一条分隔线
	this.addButton = Designer_Toolbar_AddButton;		//添加一个操作按钮
	this.initialize = Designer_Toolbar_Initialize;
	this.initButtons = Designer_Toolbar_initButtons;
	this.cancelSelect = Designer_Toolbar_CancelSelect;
	this.getCursorImgPath = Designer_Toolbar_GetCursorImgPath;
	this.getButton = Designer_Toolbar_GetButton;
	this.selectButton = Designer_Toolbar_SelectButton;
	this.showAdvancedButton = Designer_Toolbar_ShowAdvancedButton;
	this.hideAdvancedButton = Designer_Toolbar_HideAdvancedButton;
	this.onSelectControl = Designer_Toolbar_OnSelectControl;

	this.showGroupButton = Designer_Toolbar_ShowGroupButton;
	this.hideGroupButton = Designer_Toolbar_HideGroupButton;
    this.hideButtonNextEle = Designer_Toolbar_HideNextElement;

	//初始化
	this.initialize();
	this.initButtons(Designer_Config.buttons, Designer_Config.operations);
};

Designer_Toolbar.ICON_SIZE = 24;

/**********************************************************
描述：
	公共函数
功能：
	Designer_Toolbar_Initialize : 初始化工具栏
**********************************************************/
function Designer_Toolbar_Initialize() {
	var domElement, row;
	with(this.owner.toolBarDomElement.appendChild(domElement = document.createElement('div'))) {
		setAttribute('cellSpacing', '1'); setAttribute('cellPadding', '0');
		style.position = 'fixed'; style.top = '0'; style.left = '0'; style.zIndex = '90';
	};
	domElement.style.width = '100%';
	domElement.style.border = '0px';
	domElement.id="toolbar_content_div";
	
	//插入行
	//row = domElement.insertRow(-1);
	//row.className = 'buttonbar_main';
	//生成放入工具栏的单元格
	//this.domElement = row.insertCell(-1);
	this.domElement=$('<div class="edui-test-wrap edui-wrap eui-editor"></div>')[0];
	$(domElement).append('<div class="edui-operation" id="xform_form_head" style="height:auto;">'+
			'</div>');
	$(domElement).append('<div class="edui-toolbarbox" style="background-color: white;">'+
			'<!--选项卡 头部-->'+
			'<div class="edui-tab-head">'+
				'<ul>'+
					'<li class="current">'+
						'<a href="javascript:void(0);">'+Designer_Lang.Toolbar_form+'</a>'+
					'</li>'+
					'<li>'+
						'<a href="javascript:void(0);">'+Designer_Lang.Toolbar_layout+'</a>'+
					'</li>'+
					'<li>'+
						'<a href="javascript:void(0);">'+Designer_Lang.Toolbar_tool+'</a>'+
					'</li>'+
					'<li style="display:none" id="toolbar_extend_tab_head">'+
						'<a href="javascript:void(0);">'+Designer_Lang.Toolbar_extend+'</a>'+
					'</li>'+
				'</ul>'+
			'</div>'+
			
			'<div class="edui-tab-content" style="margin-top: 5px;">'+
				
				'<!--表单元素-->'+
				'<div class="edui-tab-contentItem">'+
					'<div class="edui-btns-group tab-content-from" id="xform_form_element">'+
					'</div>'+
				'</div>'+
				
				'<!--布局-->'+
				'<div class="edui-tab-contentItem" >'+
					'<div class="edui-btns-group" id="xform_form_layout">'+
					'</div>'+
				'</div>'+
				
				'<!--集成&辅助-->'+
				'<div class="edui-tab-contentItem" >'+
					'<!--集成与辅助-->'+
					'<div class="edui-btns-group" id="xform_form_tool">'+
					'</div>'+
				'</div>'+
				
				
				
				'<!--扩展-->'+
				'<div class="edui-tab-contentItem" style="display:none" id="toolbar_extend_tab_content">'+
					'<div class="edui-btns-group" id="xform_form_extend">'+
					'</div>'+
				'</div>'+
			'</div>'+
		'</div>');
	
//	Designer.addEvent(window , 'scroll' , function() {
////		domElement.style.top = document.body.scrollTop;
//		//兼容多浏览器，滚动条向上滚动时,工具栏被遮盖掉
////		$(domElement).css("top",document.documentElement.scrollTop);
//		$(domElement).css("top",document.documentElement.scrollTop || document.body.scrollTop);
//	});
	
	var resetToolBar = function() {
//		if (domElement.style.top != document.body.scrollTop) {
//			domElement.style.top = document.body.scrollTop;
//		}
		//兼容多浏览器
		if ($(domElement).css("top") != document.documentElement.scrollTop || document.body.scrollTop){
			$(domElement).css("top",document.documentElement.scrollTop || document.body.scrollTop);
		}
		setTimeout(resetToolBar, 800);
	}
	
	//作者 曹映辉 #日期 2017年4月12日 增加工具栏 高度变化时，画布高度跟随变化
	$("div[class='edui-tab-head'] ul li").mouseout(function(){
		Designer.instance.adjustBuildArea();
		Designer.instance.builder.resetDashBoxPos();
	});
	
	
	//var child=$('<div class="edui-test-wrap edui-wrap eui-editor">');
	//$(this.owner.toolBarDomElement).append(child);
	
}

/**
 * 初始化函数
 * @param {Object} controls
 */
function Designer_Toolbar_initButtons(buttons, operations) {
	//默认不显示扩展tab，当包含至少一个以上的扩展控件时才显示
	if(buttons['control'].length>0){
		$("#toolbar_extend_tab_head").show();
		$("#toolbar_extend_tab_content").show();
	}
	for (var name in buttons) {
		var btns = buttons[name];
		var groupBtns=[];
		for (var i = 0; i < btns.length; i ++) {
			groupBtns.push({"name":btns[i],"operation":operations[btns[i]]});
			if(name=='head'){
				operations[btns[i]].head=true;
			}
			
		}
		groupBtns.sort(function(a,b){
			if(a.operation.order&&b.operation.order){
				return parseFloat(a.operation.order)-parseFloat(b.operation.order);
			}
			else{
				return 0;
			}
		});
		
		for (var i = 0; i < groupBtns.length; i ++) {
			var groupBtn = groupBtns[i];
			this.addButton(name,groupBtn.name, groupBtn.operation);
		}
	}
	//初始化工具栏
	$(".edui-toolbarbox").slide({
		"titCell":".edui-tab-head ul li",
		"mainCell":".edui-tab-content",
		"titOnClassName":"current"
	});
	//$(document).trigger($.Event("designer-buttons-init"));
}

//功能：添加一条分隔线
function Designer_Toolbar_AddLine(){
	return;
	var newElem = document.createElement("div");
	this.domElement.appendChild(newElem);
	newElem.className = "button_line";
	var childElem = document.createElement("div");
	newElem.appendChild(childElem);
	childElem.className = "button_line_img";
}

//功能：添加一个操作按钮
function Designer_Toolbar_AddButton(groupName,name, config){
	return new Designer_Toolbar_Button(groupName,this, name, config);
}

//功能：取消选中
function Designer_Toolbar_CancelSelect() {
	
	if (this.selectedButton) {
		//属性布局面板处于打开状态时需要重新open刷新 作者 曹映辉 #日期 2014年11月29日
		if(this.owner.fieldPanel&&!this.owner.fieldPanel.isClosed){
			this.owner.fieldPanel.open();
		}
		this.selectedButton.setSelected(false);
	}
}

//功能：或许选择按钮鼠标图片
function Designer_Toolbar_GetCursorImgPath() {
	if (this.selectedButton && this.selectedButton.config.cursorImg != null) {
		if (this.selectedButton.config.cursorImg.indexOf("/") > -1) {
			return this.selectedButton.config.cursorImg;
		} else {
			return Com_Parameter.ContextPath + "sys/xform/designer/" + this.selectedButton.config.cursorImg;
		}
	}
	return null;
}

function Designer_Toolbar_GetButton(name) {
	for (var i = this.buttons.length -1; i >=0; i --) {
		if (this.buttons[i].name == name)
			return this.buttons[i];
	}
}

function Designer_Toolbar_SelectButton(name) {
	var button = this.getButton(name);
	this.cancelSelect();
	if (button)
		button.setSelected(true);
}

function Designer_Toolbar_ShowAdvancedButton() {
	this.isShowAdvancedButton = true;
	for(var i = 0; i < this.buttons.length; i ++) {
		if (this.buttons[i].isAdvanced) {
			this.buttons[i].domElement.style.display = 'inline';
		}
	}
}

function Designer_Toolbar_HideAdvancedButton() {
	this.isShowAdvancedButton = false;
	for(var i = 0; i < this.buttons.length; i ++) {
		if (this.buttons[i].isAdvanced) {
			this.buttons[i].domElement.style.display = 'none';
		}
	}
}

function Designer_Toolbar_OnSelectControl() {
	for(var i = 0; i < this.buttons.length; i ++) {
		if (this.buttons[i].onSelectControl) {
			this.buttons[i].onSelectControl(this.owner);
		}
	}
}

function Designer_Toolbar_ShowGroupButton(name) {
	for(var i = 0; i < this.buttons.length; i ++) {
		if (this.buttons[i].name == name) {
			this.buttons[i].domElement.style.display = 'inline';
		}
	}
}

function Designer_Toolbar_HideGroupButton(name) {
	for(var i = 0; i < this.buttons.length; i ++) {
		if (this.buttons[i].name == name) {
			this.buttons[i].domElement.style.display = 'none';
		}
	}
}

function Designer_Toolbar_HideNextElement(name) {
    for(var i = 0; i < this.buttons.length; i ++) {
        if (this.buttons[i].name == name) {
            $(this.buttons[i].domElement).next().hide();
        }
    }
}

/**********************************************************
描述：
	工具栏按钮
**********************************************************/
function Designer_Toolbar_Button(groupName,parent, name, config){
	this.isAdvanced = config.isAdvanced == true ? true : false;
	this.name = name;
	var imgIndex = config.imgIndex;
	this.toolbar = parent;
	parent.buttons.push(this);

	this.config = config;
	this.selected = false;

	var newElem = document.createElement("div");
	parent.domElement.appendChild(newElem);
	
	if(config.icon_s){
		newElem.className = "edui-btns-item-s";
	}else{
		newElem.className = "edui-btns-item";
	}
	//增加控件端可以控制 工具栏按钮的宽度属性 多浏览器支持
	if(config&&config.domWidth){
		//newElem.style.width=config.domWidth;
	}
	if(config.titleTip){
		newElem.title = config.titleTip;
	}
	else{
		if (config.title && config.sampleImg == null) {
			newElem.title = config.title;
		}
	}
	if (this.isAdvanced) {
		//newElem.style.display = 'none';
	}
	//增加控制表单控件是否显示和隐藏的接口
	if (config.isShow && (config.isShow() ==false)) {
		newElem.style.display = 'none';
	}
	this.domElement = newElem;
	if(groupName=='head'){
		newElem.className = "edui-icon edui-icon-s";
		newElem.style.backgroundPosition = "0 " + (- imgIndex * (16 + 3) ) + "px";
		//全屏按钮靠右对齐
		if(name=='fullSreen'){
			//newElem.style.float="right";
		}
		if(config.childElem != null){
			newElem.appendChild(config.childElem(parent.owner));
		}
	}
	else if (config.childElem == null) {
		var childElem = document.createElement("div");
		newElem.appendChild(childElem);
		if(config.icon_s){
			//每个小图标大小是16 图标间隔是 19
			childElem.className = "edui-icon edui-icon-s";
			childElem.style.backgroundPosition = "0 " + (- imgIndex * (16 + 3) ) + "px";
		}
		else{
			//每个图标大小是24 图标间隔是 27
			childElem.className = "edui-icon edui-icon-m";
			childElem.style.backgroundPosition = "0 " + (- imgIndex * (Designer_Toolbar.ICON_SIZE + 3) ) + "px";
			$(newElem).append("<p class=edui-h4-title>"+config.title+"</p>");
		}
		
	} else {
		newElem.appendChild(config.childElem(parent.owner));
	}
	//如果控件自己定义了宽度，已控件自己的宽度为准
	if(config.domWidth){
		newElem.style.width=config.domWidth;
		$(newElem).css("width",config.domWidth);
	}
	var parentGroup="";
	if(groupName=='start'){
		parentGroup=$("#xform_form_start");
	}
	else if(groupName=='form'){
		parentGroup=$("#xform_form_element");
	}
	else if(groupName=='layout'){
		parentGroup=$("#xform_form_layout");
	}
	else if(groupName=='tool'){
		parentGroup=$("#xform_form_tool");
	}
	else if(groupName=='head'){
		parentGroup=$("#xform_form_head");
	}
	else{
		parentGroup=$("#xform_form_extend");
	}
	//控件前分割线
	if(config.line_splits_font){
		var cls=config.icon_s?"edui-btns-splits_s":"edui-btns-splits";
		parentGroup.append("<div class='"+cls+"'></div>");
	}
	parentGroup.append(newElem);
	//控件前后分割线
	if(config.line_splits_end){
		var cls=config.icon_s?"edui-btns-splits_s":"edui-btns-splits";
		parentGroup.append("<div class='"+cls+"'></div>");
	}
	
	this.onSelectControl = config.onSelectControl ? config.onSelectControl : function() {};

	this.initActions();
}

/**
 * 按钮方法设置
 */
Designer_Toolbar_Button.prototype = {
	// 初始化操作
	initActions : function() {
		var self = this;
		this.domElement.onmouseover = function() {
			if(self.config.head){
				//下拉框类型的控件不需要active样式效果
				if(self.name=='fontStyle'||self.name=='fontSize'){
					this.className = "edui-icon edui-icon-s";
				}
				else{
					this.className = "edui-icon edui-icon-s edui-icon-active";
				}
			
			}
			else if(self.config.icon_s){
				this.className = "edui-btns-item-s edui-btn-active";
			}
			else{
				this.className = "edui-btns-item edui-btn-active";
			}
			//取得定位，并显示示例图片
			var config = self.config;
			if (config.sampleImg) {
				if (self.sampleImgDom == null) {
					var img = document.createElement('img');
					img.src = config.sampleImg;
					self.sampleImgDom = self.createSampleImg(img);
					document.body.appendChild(self.sampleImgDom);
					self.sampleImgDom.style.position = 'absolute';
					self.sampleImgDom.style.zIndex = '100';
					var w = img.offsetWidth;
					if (w > 250) {
						img.width = '250';
						img.height = '' + (img.offsetHeight / w * 250);
					}
				}
				var p = Designer.absPosition(this);
				self.sampleImgDom.style.top = p.y + this.offsetHeight + 'px';
				self.sampleImgDom.style.left = p.x + 'px';
				self.sampleImgDom.style.display = '';
			}
		};

		this.domElement.onmouseout = function() {
			if(self.config.head){
				//下拉框类型的控件不需要active样式效果
				if(self.name=='fontStyle'||self.name=='fontSize'){
					this.className = "edui-icon edui-icon-s";
				}
				else{
					this.className = self.selected ? "edui-icon edui-icon-s edui-icon-active" : "edui-icon edui-icon-s";
				}
			}
			else if(self.config.icon_s){
				this.className = self.selected ? "edui-btns-item-s edui-btn-active" : "edui-btns-item-s";
			}
			else{
				this.className = self.selected ? "edui-btns-item edui-btn-active" : "edui-btns-item";
			}
			//隐藏示例图片
			if (self.sampleImgDom != null) {
				self.sampleImgDom.style.display = 'none';
			}
		};

		this.domElement.onclick = function() {
			if (self.toolbar.selectedButton && self.toolbar.selectedButton != this) {
				self.toolbar.selectedButton.setSelected(false);
			}
			if (self.config.select == true) {
				//self.setSelected(true);
			} else {
				//self.setSelected(false);
			}
			if (self.config.run)
				self.config.run(self.toolbar.owner, self);
			//清除多表单载入功能保存的控件对象
			if(Designer.instance.createSubformControl){
				var sub = $("#subform_"+Designer.instance.createSubformControl.options.values.id,parent.document);
				if(sub.length>0){
					sub.css("color","black");
				}
				Designer.instance.createSubformControl = '';
			}
		};
	},
	setAsSelectd : function(selected) {
		if(this.config.head){
		}
		else if(this.config.icon_s){
			this.domElement.className = selected ? "edui-btns-item-s edui-btn-active" : "edui-btns-item-s";			
		}
		else{
			this.domElement.className = selected ? "edui-btns-item edui-btn-active" : "edui-btns-item";
		}
		this.selected = selected;
	},
	// 设置是否被选择
	setSelected : function(selected) {
		if(this.config.head){
		}
		else if(this.config.icon_s){
			this.domElement.className = selected ? "edui-btns-item-s edui-btn-active" : "edui-btns-item-s";			
		}
		else{
			this.domElement.className = selected ? "edui-btns-item edui-btn-active" : "edui-btns-item";
		}
		this.selected = selected;
		if (selected) {
			this.toolbar.selectedButton = this;
			this.toolbar.owner.toolBarAction = this.name;
		} else {
			this.toolbar.selectedButton = null;
			this.toolbar.owner.toolBarAction = '';
		}
	},
	// 创建预览框
	createSampleImg : function(img) {
		var table = document.createElement('table'), row, cell;
		table.className = "toolbar_preview_table";
		var text = document.createElement('div');
		if (this.config.title)
			text.innerHTML = '<div class="toolbar_preview_main_text">'
				+ this.config.title +'</div>';
		else
			text.innerHTML = '<div class="toolbar_preview_main_text">'+Designer_Lang.toolbarSampleImg+'</div>';
		text.className = 'toolbar_preview_main_text_box';

		row = table.insertRow(-1);
		cell = row.insertCell(-1);
		cell.className = "toolbar_preview_top_left";
		cell = row.insertCell(-1);
		cell.className = "toolbar_preview_top_center";
		cell = row.insertCell(-1);
		cell.className = "toolbar_preview_top_right";

		row = table.insertRow(-1);
		cell = row.insertCell(-1);
		cell.className = "toolbar_preview_main_left";
		cell = row.insertCell(-1);
		cell.className = "toolbar_preview_main_center";
		cell.appendChild(text);
		cell.appendChild(img);
		cell = row.insertCell(-1);
		cell.className = "toolbar_preview_main_right";

		row = table.insertRow(-1);
		cell = row.insertCell(-1);
		cell.className = "toolbar_preview_bottom_left";
		cell = row.insertCell(-1);
		cell.className = "toolbar_preview_bottom_center";
		cell = row.insertCell(-1);
		cell.className = "toolbar_preview_bottom_right";
		
		return table;
	}
};