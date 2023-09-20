/**********************************************************
功能：工具栏对象和操作按钮对象
使用：

**********************************************************/

var Designer_Mobile_Config = {};
var Designer_AttrPanel = {};
Designer_Mobile_Config.buttons = {
		head:['bold', 'italic', 'underline', 'deleteElem','fontColor','importTmp','addRow','fontStyle','fontSize']
};
var colorChooserHintInfo={
	chooseText : '确定',
	cancelText : '取消'
};
Com_IncludeFile("alignPanel.js",Com_Parameter.ContextPath+'sys/xform/designer/mobile/js/','js',true);
Com_IncludeFile("colorPanel.js",Com_Parameter.ContextPath+'sys/xform/designer/mobile/js/','js',true);
Com_IncludeFile("addRowPanel.js",Com_Parameter.ContextPath+'sys/xform/designer/mobile/js/','js',true);

function Designer_OptionRun_Alert(msg) {
	alert(msg);
}

function Designer_OptionRun_CallFunction(designer, fn, noControlMessage) {
	if (designer.controls && designer.controls.length > 0) {
		var noCallControl = true;
		for (var i = designer.controls.length - 1; i >= 0; i--) {
			if (fn(designer.controls[i])) {
				noCallControl = false;
			}
		}
		if (noCallControl) {
			Designer_OptionRun_Alert(noControlMessage ? noControlMessage : "请先选择控件!");
		}
	} else if (designer.control) {
		if (!fn(designer.control)) {
			Designer_OptionRun_Alert(noControlMessage ? noControlMessage : "请先选择控件!");
		}
	} else {
		Designer_OptionRun_Alert(noControlMessage ? noControlMessage : "请先选择可执行的控件!");
	}
}

function Designer_Mobile_ImportTmp() {
	var mobileDesigner = MobileDesigner.instance;
	if (isUseSubForm()) {
		var checkedTr = getMobileCheckedTr();
		var $pcFormTr = getPcFormTrByMobileTr(checkedTr);
		if ($pcFormTr.length == 1) {
			mobileDesigner.copyFormByPc($pcFormTr[0]);
		} else {
			alert("此表单不存在对应的移动端表单,导入无效!");
		}
		
	}
	if (isUseDefineForm()) {
		var pcDesigner = mobileDesigner.getPcDesigner();
		var designerHtml = pcDesigner.getHTML();
		var fdMetadataXml = pcDesigner.getXML();
		var mobileHtml = mobileDesigner.setHTML(designerHtml,true);
		seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
			if (mobileHtml) {
				dialog.success("导入成功!");
			} else {
				dialog.failure("导入失败!");
			}
		});
		var pcFormId = getPcDefaultId();
		var $pcFormTr = $(getMobileTrByPcId(pcFormId));
		$pcFormTr.find("input[name$='fdDesignerHtml']").val(mobileHtml);
		$pcFormTr.find("input[name$='fdMetadataXml']").val(fdMetadataXml);
	}
	//重新设置待选控件列表
	mobileDesigner.loadControls();
}

function Designer_Mobile_Toolbar(designer) {
	this.owner = designer || null;
	this.domElement = null;
	this.buttons = [];
	this.selectedButton = null;
	this.isShowAdvancedButton = false;

	//公共方法
	this.addLine = Designer_Mobile_Toolbar_AddLine;			//添加一条分隔线
	this.addButton = Designer_Mobile_Toolbar_AddButton;		//添加一个操作按钮
	this.initialize = Designer_Mobile_Toolbar_Initialize;
	this.initButtons = Designer_Mobile_Toolbar_initButtons;
	this.cancelSelect = Designer_Mobile_Toolbar_CancelSelect;
	this.getCursorImgPath = Designer_Mobile_Toolbar_GetCursorImgPath;
	this.getButton = Designer_Mobile_Toolbar_GetButton;
	this.selectButton = Designer_Mobile_Toolbar_SelectButton;
	this.showAdvancedButton = Designer_Mobile_Toolbar_ShowAdvancedButton;
	this.hideAdvancedButton = Designer_Mobile_Toolbar_HideAdvancedButton;
	this.onSelectControl = Designer_Mobile_Toolbar_OnSelectControl;

	this.showGroupButton = Designer_Mobile_Toolbar_ShowGroupButton;
	this.hideGroupButton = Designer_Mobile_Toolbar_HideGroupButton;
	
	var mobileIFrame = designer.mobileIFrame;
	
	Designer_Mobile_InitOperations(mobileIFrame);
	
	//初始化
	this.initialize();
	this.initButtons(Designer_Mobile_Config.buttons, mobileIFrame.Designer_Config.operations);
	
	var $operationObj = $(".xform_mobile_operation ");
	var originalTop = $operationObj.offset().top;
	var self = this;
	$(document).scroll(function() {
		var clientType = "pc";
		$(".xform_client_btn").each(function(index,obj){
			if ($(obj).hasClass("is-active")) {
				clientType = $(obj).attr("val");
			}
		});
		if (clientType == "mobile") {
			if (originalTop == 0) {
				originalTop = self.originalTop;
			}
			        var scroH = $(document).scrollTop();  //滚动高度
			var __height = $operationObj.height()/2;
			if (scroH + __height > originalTop) {
				$operationObj.offset({top:scroH + __height});
			}
			if (scroH + __height < originalTop) {
				$operationObj.offset({top:originalTop});
			}
		}
    });
};

function Designer_Mobile_InitOperations(mobileIFrame){
	
	mobileIFrame.Designer_Config.operations.fontColor =  {
			title : '字体颜色',
			run : Designer_OptionRun_OpenFontColor,
			imgIndex : 10,
			type:'cmd'	
	};
	
	mobileIFrame.Designer_Config.operations.importTmp =  {
			title : '执行一键载入操作将重新载入表单字段，您之前配置的样式将还原到默认样式，请谨慎执行!',
			run : Designer_Mobile_ImportTmp,
			imgIndex : 23,
			type:'cmd'	
	};
	
	mobileIFrame.Designer_Config.operations.addRow =  {
			title : '新增行列',
			run : Designer_Mobile_OpenAddRowPanel,
			imgIndex : 24,
			type:'cmd'	
	};
	
	/*mobileIFrame.Designer_Config.operations.align =  {
			title : '控件对齐方式',
			run : Designer_Mobile_OpenAlignPanel,
			imgIndex : 25,
			type:'cmd'	
	};*/
}



//移动端iframe
var mobileIFrame;
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

Designer_Mobile_Config.ICON_SIZE = 24;

/**********************************************************
描述：
	公共函数
功能：
	Designer_Toolbar_Initialize : 初始化工具栏
**********************************************************/
function Designer_Mobile_Toolbar_Initialize() {
	var fdKey = this.owner.fdKey;
	this.domElement = $('#mobileForm_right_' + fdKey + '_content')[0];
}

/**
 * 初始化函数
 * @param {Object} controls
 */
function Designer_Mobile_Toolbar_initButtons(buttons, operations) {
	for (var name in buttons) {
		var btns = buttons[name];
		var groupBtns=[];
		for (var i = 0; i < btns.length; i ++) {
			groupBtns.push({"name":btns[i],"operation":operations[btns[i]]});
			if(name=='head'){
				operations[btns[i]].head=true;
			}
			
		}
		
		for (var i = 0; i < groupBtns.length; i ++) {
			var groupBtn = groupBtns[i];
			this.addButton(name,groupBtn.name, groupBtn.operation);
		}
	}
}

//功能：添加一条分隔线
function Designer_Mobile_Toolbar_AddLine(){
	return;
	var newElem = document.createElement("div");
	this.domElement.appendChild(newElem);
	newElem.className = "button_line";
	var childElem = document.createElement("div");
	newElem.appendChild(childElem);
	childElem.className = "button_line_img";
}

//功能：添加一个操作按钮
function Designer_Mobile_Toolbar_AddButton(groupName,name, config){
	return new Designer_Mobile_Toolbar_Button(groupName,this, name, config);
}

//功能：取消选中
function Designer_Mobile_Toolbar_CancelSelect() {
	
	if (this.selectedButton) {
		//属性布局面板处于打开状态时需要重新open刷新 作者 曹映辉 #日期 2014年11月29日
		if(this.owner.fieldPanel&&!this.owner.fieldPanel.isClosed){
			this.owner.fieldPanel.open();
		}
		this.selectedButton.setSelected(false);
	}
}

//功能：或许选择按钮鼠标图片
function Designer_Mobile_Toolbar_GetCursorImgPath() {
	if (this.selectedButton && this.selectedButton.config.cursorImg != null) {
		return this.selectedButton.config.cursorImg;
	}
	return null;
}

function Designer_Mobile_Toolbar_GetButton(name) {
	for (var i = this.buttons.length -1; i >=0; i --) {
		if (this.buttons[i].name == name)
			return this.buttons[i];
	}
}

function Designer_Mobile_Toolbar_SelectButton(name) {
	var button = this.getButton(name);
	this.cancelSelect();
	if (button)
		button.setSelected(true);
}

function Designer_Mobile_Toolbar_ShowAdvancedButton() {
	this.isShowAdvancedButton = true;
	for(var i = 0; i < this.buttons.length; i ++) {
		if (this.buttons[i].isAdvanced) {
			this.buttons[i].domElement.style.display = 'inline';
		}
	}
}

function Designer_Mobile_Toolbar_HideAdvancedButton() {
	this.isShowAdvancedButton = false;
	for(var i = 0; i < this.buttons.length; i ++) {
		if (this.buttons[i].isAdvanced) {
			this.buttons[i].domElement.style.display = 'none';
		}
	}
}

function Designer_Mobile_Toolbar_OnSelectControl() {
	for(var i = 0; i < this.buttons.length; i ++) {
		if (this.buttons[i].onSelectControl) {
			this.buttons[i].onSelectControl(this.owner);
		}
	}
}

function Designer_Mobile_Toolbar_ShowGroupButton(name) {
	for(var i = 0; i < this.buttons.length; i ++) {
		if (this.buttons[i].name == name) {
			this.buttons[i].domElement.style.display = 'inline';
		}
	}
}

function Designer_Mobile_Toolbar_HideGroupButton(name) {
	for(var i = 0; i < this.buttons.length; i ++) {
		if (this.buttons[i].name == name) {
			this.buttons[i].domElement.style.display = 'none';
		}
	}
}

/**********************************************************
描述：
	工具栏按钮
**********************************************************/
function Designer_Mobile_Toolbar_Button(groupName,parent, name, config){
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
	
	this.onSelectControl = config.onSelectControl ? config.onSelectControl : function() {};

	this.initActions();
}

/**
 * 按钮方法设置
 */
Designer_Mobile_Toolbar_Button.prototype = {
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
			var mobileIFrame = self.toolbar.owner.mobileIFrame;
			if (self.config.run)
				self.config.run(self.toolbar.owner, self);
			//清除多表单载入功能保存的控件对象
			if(mobileIFrame.Designer.instance.createSubformControl){
				var sub = $("#subform_" + mobileIFrame.Designer.instance.createSubformControl.options.values.id,parent.document);
				if(sub.length>0){
					sub.css("color","black");
				}
				mobileIFrame.Designer.instance.createSubformControl = '';
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