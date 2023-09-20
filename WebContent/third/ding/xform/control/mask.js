/**********************************************************
功能：遮罩控件,里面的控件不允许改动
使用：

**********************************************************/
Com_IncludeFile("mask.css",Com_Parameter.ContextPath+'third/ding/xform/control/','css',true);
Designer_Config.controls['mask'] = {
		type : "mask",
		inherit : 'base',
		storeType : 'layout',
		container : true,
		notAllowDbClick : true,
		onDraw : _Designer_Control_Mask_OnDraw,
		drawMobile : _Designer_Control_Mask_DrawMobile,
		drawXML : _Designer_Control_Mask_DrawXML,
		insertValidate: _Designer_Control_Mask_InsertValidate, //插入校验
		onShiftDrag   : _Designer_Control_Base_DoDrag,
		onShiftDragMoving: _Designer_Control_Base_DoDragMoving,
		onShiftDragStop: _Designer_Control_Base_DoDragStop,
		onDrag:function(event){
			var _prevDragDomElement = this.owner._dragDomElement, currElement = event.srcElement || event.target;
			//绑定调整大小框
			this.owner.resizeDashBox.attach(this);
			//若当前拖拽控件与前一个拖拽控件不同，则调用前一控件取消锁定状态
			if (_prevDragDomElement && _prevDragDomElement !== this && _prevDragDomElement.onUnLock)
				_prevDragDomElement.onUnLock();
			//记录当前拖拽控件
			this.owner._dragDomElement = this;
			
		},
		destroy: Designer_Control_Mask_Destroy,
		destroyMessage: Designer_Lang.mask_not_allow_delete,
		info : {
			name: Designer_Lang.control_mask_info_name
		},
};

function _Designer_Control_Mask_OnDraw(parentNode, childNode) {
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();
	var domElement = _CreateDesignElement('div', this, parentNode, childNode);
	domElement.id = this.options.values.id;
	domElement.label = _Get_Designer_Control_Label(this.options.values, this);
	domElement.className = "xform_mask";
	var mask = document.createElement("div");
	domElement.appendChild(mask);
	mask.className = 'lui_pop_mask';
	var content = document.createElement("div");
	domElement.appendChild(content);
	content.className = "content";
}

function  _Designer_Control_Mask_DrawMobile(){
	
}

function _Designer_Control_Mask_DrawXML() {
	var values = this.options.values;
	var xmls = [];
	xmls.push('<extendSimpleProperty ');
	xmls.push('name="' + values.name + '" ');
	xmls.push('store="false" ');
	xmls.push('label="mask" ');
	xmls.push('businessType="', this.type, '" ');
	xmls.push('/>');
	xmls.push(xml, '\r\n');
	if (this.children.length > 0) {
		this.children = this.children.sort(Designer.SortControl);
		for (var i = 0, l = this.children.length; i < l; i ++) {
			var c = this.children[i];
			if (c.drawXML) {
				var xml = c.drawXML();
				if (xml != null)
					xmls.push(xml, '\r\n');
			}
		}
		return xmls.join('');
	}
	return '';
}

/** 不允许在钉钉套件内插入表单控件 */
function _Designer_Control_Mask_InsertValidate(cell, control){
	return false;
}

/** 不允许删除钉钉套件 */
function Designer_Control_Mask_Destroy(){
	return false;
}

/** 不在toolbar显示 */
function _Designer_Control_Mask_IsShow(){
	return false;
}

Designer_Config.operations['mask'] = {
		lab : "2",
		imgIndex : 28,
		title : Designer_Lang.mask,
		run : function (designer) {
			designer.toolBar.selectButton('mask');
		},
		type : 'cmd',
		group: 'layout',
		order: 11,
		select: true,
		isShow: _Designer_Control_Mask_IsShow,
		cursorImg: 'style/cursor/div.cur'
		
};

Designer_Config.buttons.layout.push("mask");

Designer_Menus.layout.menu['mask'] = Designer_Config.operations['mask'];