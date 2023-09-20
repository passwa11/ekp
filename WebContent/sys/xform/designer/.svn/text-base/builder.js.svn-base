/**********************************************************
功能：绘制区
使用：

备注：
	1. 绘制区中的controls数组，只包含绘制区的直接子对象，并不是全部组件。
	比如：表格是个容器，内部有记录包含的子对象。
作者：龚健
创建时间：2009-02-18
**********************************************************/
function Designer_Builder(designer) {
	//属性
	this.owner = designer || null;                                  //设计器对象
	this.domElement = this.owner.builderDomElement;
	this.controls = [];            //顶层控件
	this.resizeDashBoxs = [];

	//内部属性
	this._actionType = 'default';  //鼠标操作类型
	this._chooseDomElement = null; //待选中控件对象(鼠标经过元素时)
	this._dragDomElement = null;   //拖拽对象

	//调整大小框，虚线框，竖线，横线，实心拖拽框
	this.resizeDashBox = new Designer_Builder_DashBox(this);
	this.chooseDashBox = new Designer_Builder_DashBox(this, 'chooseBox');
	this.vDashLine = new Designer_Builder_DashBox(this, 'vDashLine');
	this.hDashLine = new Designer_Builder_DashBox(this, 'hDashLine');
	this.dragDashBox = new Designer_Builder_DashBox(this, 'dragBox');

	//绘制区相对屏幕的偏移
	this.offset = Designer.absPosition(this.domElement);

	//内部调用方法
	this._mouseDown = _Designer_Builder_DoMouseDown;   //供设计器来调用
	this._mouseMove = _Designer_Builder_DoMouseMove;   //供设计器来调用
	this._mouseUp = _Designer_Builder_DoMouseUp;       //供设计器来调用
	this._dblClick = _Designer_Builder_DoDblClick;     //供设计器来调用

	//公共方法
	this.initialize = Designer_Builder_Initialize;
	this.createControl = Designer_Builder_CreateControl;
	this.parse = Designer_Builder_Parse;
	this.serialize = Designer_Builder_Serialize;
	this.serializeControl = Designer_Builder_SerializeControl;
	this.getControlByDomElement = Designer_Builder_GetControlByDomElement;
	this.getControlByArea = Designer_Builder_GetControlByArea;
	this.getMouseRelativePosition = Designer_Builder_GetMouseRelativePosition;
	this.setProperty = Designer_Builder_SetProperty;
	this.deleteControl = Designer_Builder_DeleteControl;
	this.deleteControls = Designer_Builder_DeleteControls;
	this.cleanControls = Designer_Builder_CleanControls;
	this.moveDashBox = Designer_Builder_MoveDashBox;
	this.destroyDashBox = Designer_Builder_DestroyDashBox;
	this.selectedControl = Designer_Builder_SelectedControl;
	this.addSelectedControl = Designer_Builder_AddSelectedControl;
	this.removeSelectedControl = Designer_Builder_RemoveSelectedControl;
	this.clearSelectedControl = Designer_Builder_ClearSelectedControl;
	this.resetDashBoxPos = Designer_Builder_ResetDashBoxPos;
	this.onSerialize = Designer_Builder_OnSerialize;
	this.onSerialized = Designer_Builder_OnSerialized;

	//对象初始化
	this.initialize();
};

/**********************************************************
描述：
	公共函数
功能：
	Designer_Builder_Initialize               : 初始化绘制区
	Designer_Builder_CreateControl            : 创建控件
	Designer_Builder_GetControlByDomElement   : 根据Dom元素获得相应附着的Control对象
	Designer_Builder_ChooseControl            : 选中控件(参数: control>Designer_Control对象)
	Designer_Builder_GetControlByArea         : 根据区域获得控件对象
	Designer_Builder_GetMouseRelativePosition : 获得鼠标在绘制区的相对位置
	Designer_Builder_SetProperty              : 设置当前控件相应属性
	Designer_Builder_DeleteControl            : 删除当前选中控件
	Designer_Builder_DeleteControls			  : 删除所有控件
	Designer_Builder_CleanControls            : 清除控件一些编辑时才有用的信息
	Designer_Builder_MoveDashBox              : 移动所有虚线框对象
	Designer_Builder_DestroyDashBox           : 销毁掉所有虚线框对象
**********************************************************/
function Designer_Builder_Initialize() {
	var _self = this;
	//注册快捷键
	this.owner.shortcuts.add('esc', function(){
		_self.owner.toolBar.cancelSelect();
	});
};

/*参数：controlType>控件类型 currElement>需插入控件的容器Dom对象(可选)，若没有此参数，则根据当前选中控件*/
function Designer_Builder_CreateControl(controlType, currElement,callback) {
	var control = currElement ? this.getControlByDomElement(currElement) : this.owner.control;
	var created = typeof controlType == 'string' ? new Designer_Control(this, controlType, false) : controlType;
	if(callback){
		callback(created);
	}
	//增加needInsertValidate属性，用于某些控件不是容器控件，但确实需要插入校验
	if (control != null && (control.container || control.needInsertValidate)) {
		switch (control.getTagName()) {
		case 'table':
			var owner = currElement ? currElement : control.selectedDomElement[0];
			if (owner != null) {
				if (owner.tagName == null || owner.tagName.toLowerCase() != 'td') {
					owner = $(owner).closest("td")[0];
				}
				if (control.insertValidate && !control.insertValidate(owner, created)) {break;}
				//若需插入的单元格只有空格，则要清除空格(&nbsp;)
				if (owner.innerHTML == '&nbsp;') owner.innerHTML = '';
				//绘制控件前检查
				if (created.beforeDrawValidate&&!created.beforeDrawValidate(control, owner)){
					return;
				}
				//绘制控件
				created.draw(control, owner);
			}else{
				alert(Designer_Lang.controlTableSelectOneCell);
			}
			break;
		case 'div':
			var owner = control.options.domElement;
			if (owner != null) {
				if (control.insertValidate && !control.insertValidate(owner, created)) {break;}
				if (owner.innerHTML == '&nbsp;') owner.innerHTML = '';
				created.draw(control, owner);
			}
			break;
		}
	} else {
		if (control != null && (created.container || control.needInsertValidate)) { // 处理容器新建时，在在控件上点击时
			var parent = control.parent ? control.parent : null;
			if (parent) {
				if (parent.insertValidate && !parent.insertValidate(currElement, created)) {
					return;
				}
				created.draw(parent, control.options.domElement.parentNode, Designer.getDesignElement(control.options.domElement.nextSibling, 'nextSibling'));
			} else {
				// 传统直接添加
				created.draw(null, this.domElement);
				this.controls.push(created);
			}
			var newParentDom = null;
			try {
				switch (created.getTagName()) {
					case 'table': newParentDom = created.options.domElement.rows[1].cells[1]; break;
					case 'div': newParentDom = created.options.domElement; break;
				}
			} catch (e) {}
			if (newParentDom != null) {
				control.draw(created, newParentDom); // 控件移动到新建容器中
			}
		} else if (control != null && control.parent && control.parent.container) {// 普通点在控件上
			var parent = control.parent ? control.parent : null;
			if (parent && parent.insertValidate && !parent.insertValidate(currElement, created)) {
				return;
			}
			created.draw(parent, control.options.domElement.parentNode, Designer.getDesignElement(control.options.domElement.nextSibling, 'nextSibling'));
		} else {
			// 传统直接添加
			created.draw(null, this.domElement);
			this.controls.push(created);
		}
	}
	//如果控件有执行了draw方法，才算绘画成功
	if(typeof(created.isDoneDraw) == 'boolean' && created.isDoneDraw){
		//选中控件
		this.resizeDashBox.attach(created);


		//清除锁定 
		if(control && control.selectedDomElement && control.onUnLock){
			control.onUnLock();
		}
		this.clearSelectedControl(); // 清空所有选择控件

		this.addSelectedControl(created);
		//记录当前控件
		this.owner.setControl(created);
		//更新控件树
		if (!this.owner.treePanel.isClosed)
			this.owner.treePanel.open();
		//相同则不重绘属性框
		var attrPanel = this.owner.attrPanel;
		if (!attrPanel.isClosed) attrPanel.show();
	}
};

//解析成相应控件对象集
function Designer_Builder_Parse(parentControl, parentNode) {
	try {
		var _parentControl = parentControl || null, _parent = parentNode || null, childCount, node, control;
		if (_parent == null) return;
		//遍历子节点
		childCount = _parent.childNodes.length;
		for (var i = 0; i < childCount; i++) {
			node = _parent.childNodes[i];
			if (node.nodeType != 3) {
				if (Designer.isDesignElement(node)) {
					if (node.getAttribute('fd_type') == null) continue;
					var fdType = node.getAttribute('fd_type');
					var controls = this.owner.controlDefinition;
					//判断是否存在控件定义
					var isExit = false;
					for (var controlType in  controls) {
						if (controlType === fdType) {
							isExit = true;
							break;
						}
					}
					if (!isExit) {
						continue;
					}
					//创建对象
					this.controls.push(control = new Designer_Control(this, node.getAttribute('fd_type'), false));
					//绑定DomElment
					control.options.domElement = node;
					//设置初始值
					try {
						var fdValues = node.getAttribute('fd_values');
						fdValues = Designer_Builder_UnEnscapeFdValues(fdValues);
						control.options.values = eval('(' + fdValues + ')');
						var fd_values = control.options.values;
						if (fd_values && (fd_values.dataType === "Double" ||fd_values.dataType === "BigDecimal"
							|| fd_values.dataType === "BigDecimal_Money")) {
							if (fd_values.decimal && !fd_values.scale) {
								fd_values.scale = fd_values.decimal;
							}
						}
					} catch (e) {
						console.log(e);
						control.options.values = {};
					}
					//更新控件树
					control.updateControlTree(null, _parentControl);
					//初始化控件相关信息
					try{
						if (control.onInitialize) control.onInitialize();
					}catch(event){
						console.log(event);
						//解决表格控件在特殊情况（比如一行存在删除列的时候）下解析失败的问题
						if(control.type = "standardTable" || control.onInitialize == '_Designer_Control_Table_DoInitialize'){
							var table = control.options.domElement, column = [];
							var colCount = 0, row, cell, cellColumn;
							//计算列总数
							row = table.rows[0];
							if(!row){
								return;
							}
							//获取最大列数和对应行
							var maxColCount = 0;
							var maxColRow;
							for(var i=0; i<table.rows.length; i++){
								var temp = table.rows[i];
								if(maxColCount < temp.cells.length){
									maxColCount = temp.cells.length;
									maxColRow = temp;
								}
							}
							for(var i=0; i<maxColCount; i++){
								cell = maxColRow.cells[i];
								cellColumn = cell.getAttribute('column').split(',');
								for (var j = 0; j < cellColumn.length; j++)
									column.push([]);
							}
							var rowCount = table.rows.length;
							//遍历单元格，放入相应列组中
							for (var i = 0; i < rowCount; i++) {
								row = table.rows[i];
								colCount = row.cells.length;
								for (var j = 0; j < colCount; j++) {
									cell = row.cells[j];
									cellColumn = cell.getAttribute('column').split(',');
									column[cellColumn[0]].push(cell);
									var cellWidth = _Designer_Control_Table_GetCellWidth(cell);
									cell.setAttribute('width', cellWidth);
								}
							}
							//记录列对象集
							control.options.column = column;
						}
					}
					//=====绘制移动端dom对象 start ========
					if (control.drawMobile && control.owner.owner.drawMobile) {
						control.drawMobile();
						control.fillMobileValue && control.fillMobileValue();
					}
					//=====绘制移动端dom对象 end ========

					//多表单载入功能所需控件数组start
					if(!Designer.instance.subFormControls){
						Designer.instance.subFormControls = {};
					}
					var myid = '';
					if(Designer.instance.subForm && Designer.instance.subForm.id){
						myid = Designer.instance.subForm.id;
					}else{
						myid = "subform_default";
					}
					if(!Designer.instance.subFormControls[myid]){
						Designer.instance.subFormControls[myid] = [];
					}
					for(var l = 0;l<Designer.instance.subFormControls[myid].length;l++ ){
						if(Designer.instance.subFormControls[myid][l].options.values.id == control.options.values.id){
							Designer.instance.subFormControls[myid].splice(l,1);
							break
						}
					}
					Designer.instance.subFormControls[myid].push(control);
					//end
					//绑定国际化组件
					!Designer.instance.isMobile && control.lang && control.lang();
					//遍历后续子节点
					this.parse(control, node);
				} else {
					this.parse(_parentControl, node);
				}
			}
		}
	} catch (e) {
		console.log(e);
	}
};

function Designer_Builder_UnEnscapeFdValues(fdValues){
	if (fdValues == null || fdValues.trim() =='') return '';
	fdValues = fdValues.replace(/\r\n/ig,'\\r\\n');
	fdValues = fdValues.replace(/&#;/ig,'\\r\\n');
	fdValues = fdValues.replace(/\\quot;/ig,'\\\\quot;');
	fdValues = fdValues.replace(/&#92;/ig,'\\\\');
	return fdValues;
}

//序列化控件中一些信息
function Designer_Builder_Serialize(parentControl) {
	var children = parentControl ? parentControl.children : this.controls, control, buf;
	for (var i = children.length - 1; i >= 0; i--) {
		control = children[i];
		this.serializeControl(control);
		//遍历后续子节点
		this.serialize(control);
	}
};

function Designer_Builder_SerializeControl(control) {
	var buf = [];
	for (var name in control.options.values) {
		// 记录加密属性变更的值不需要设置到dom上面
		if (name == '__dict__' || name == '_encryptChange' || name == '_isMarkChange') {
			continue;
		}
		buf.push(name + ':"' + control.options.values[name] + '"');
	}
	var dbuf = [], dict = control.options.values.__dict__;
	if (dict) {
		for (var i = 0; i < dict.length; i ++) {
			var d = dict[i], dstr = [];
			for (var dname in d) {
				dstr.push(dname + ':"' + d[dname] + '"');
			}
			dbuf.push( '{' + dstr.join(',') + '}');
		}
		if (dbuf.length > 0) {
			buf.push('__dict__:[' + dbuf.join(',') + ']');
		}
	}
	//chrome和ie等浏览器换行符不一致
	var str=buf.join(',').replace(/\r\n/ig,"&#;").replace(/\n/ig,"&#;").replace(/\\/ig,"&#92;");
	control.options.domElement.setAttribute('fd_values', '{' + str + '}');
	//记录控件类型
	control.options.domElement.setAttribute('fd_type', control.type);
}

function Designer_Builder_GetControlByDomElement(domElement) {
	var control, rtnControl, _domElement = domElement || null;
	if (_domElement == null) return null;
	//若不是控件，寻找父节点直到控件
	if (!Designer.isDesignElement(_domElement)) _domElement = Designer.getDesignElement(_domElement);
	//遍历控件数
	for (var i = this.controls.length - 1; i >= 0; i--) {
		control = this.controls[i];
		rtnControl = control.getControlByDomElement(_domElement);
		if (rtnControl != null) return rtnControl;
	}
	return null;
};

function Designer_Builder_GetControlByArea(area, exceptControl) {
	var control;
	for (var i = this.controls.length - 1; i >= 0; i--) {
		control = this.controls[i];
		if (control != exceptControl && Designer.isIntersect(area, control.options.domElement))
			return control.getControlByArea(area, exceptControl);
	}
	return null;
};

function Designer_Builder_GetMouseRelativePosition(event) {
	var mPosition = Designer.getMousePosition(event);
	//return {x: mPosition.x - this.offset.x, y: mPosition.y - this.offset.y};
	//return {x: mPosition.x - this.offset.x, y: mPosition.y};
	return {x: mPosition.x, y: mPosition.y};
};

function Designer_Builder_SetProperty(control) {
	var currNode = control.options.domElement, parentNode = currNode.parentNode;
	var nextNode = null, node, childCount = parentNode.childNodes.length;
	//获得当前控件的同父的后一节点
	for (var i = 0; i < childCount; i++) {
		node = parentNode.childNodes[i];
		if (node.nodeType != 3 && node == currNode) {
			nextNode = parentNode.childNodes[i + 1];
			break;
		}
	}
	//重绘控件
	control.draw(control.parent, parentNode, nextNode);
	//选中框重新附着
	this.resizeDashBox.attach(control);
	//更新控件树
	if (!this.owner.treePanel.isClosed)
		this.owner.treePanel.open();
};

function Designer_Builder_DeleteControl() {
	var control = this.owner.control;
	if (control != null && confirm(Designer_Lang.builderDeleteControlAlter)) control.destroy();
};

function Designer_Builder_DeleteControls(controls){
	controls = controls || this.controls;
	for (var i = controls.length - 1; i >= 0; i--) {
		control = controls[i];
		control.destroy();
	}
}

function Designer_Builder_CleanControls(controls) {
	controls = controls || this.controls;
	for (var i = controls.length - 1; i >= 0; i--) {
		control = controls[i];
		if (control && control.onUnLock) control.onUnLock();
		if (control.children && control.children.length > 0) {
			this.cleanControls(control.children);
		}
	}
};

function Designer_Builder_MoveDashBox(parentDomElement) {
	this.resizeDashBox.moveDomElement(parentDomElement);
	this.chooseDashBox.moveDomElement(parentDomElement);
	this.vDashLine.moveDomElement(parentDomElement);
	this.hDashLine.moveDomElement(parentDomElement);
	this.dragDashBox.moveDomElement(parentDomElement);
	if (this.mobileDragBox) {
		this.mobileDragBox.moveDomElement(parentDomElement);
		if (this.mobileDragBox.deleteIcon) {
			$(parentDomElement).append(this.mobileDragBox.deleteIcon);
		}
	}
	this.clearSelectedControl();
};

function Designer_Builder_DestroyDashBox() {
	this.resizeDashBox.destroy();
	this.chooseDashBox.destroy();
	this.vDashLine.destroy();
	this.hDashLine.destroy();
	this.dragDashBox.destroy();
	if (this.mobileDragBox) {
		this.mobileDragBox.destroy();
	}
};

// 选中指定控件
function Designer_Builder_SelectedControl(control) {
	//选中控件
	this.resizeDashBox.attach(control);
	//记录当前控件
	this.owner.control = control;
	this.clearSelectedControl(); // 清空所有选择控件
	//this.addSelectedControl(currControl);
}
// 多选控件
function Designer_Builder_AddSelectedControl(control) {
	if (this.resizeDashBoxs == null)
		this.resizeDashBoxs = [];
	if (this.owner.controls == null)
		this.owner.controls = [];
	if (control == null) return;
	var box = new Designer_Builder_DashBox(this);
	this.resizeDashBoxs.push(box);
	this.owner.controls.push(control);
	box.attach(control);
	for (var i = 0, l = this.resizeDashBoxs.length; i < l; i ++) {
		this.resizeDashBoxs[i].show();
	}
	box.setAreaDragBox('no');
	box.hide();
}
// 移除多选中的一个
function Designer_Builder_RemoveSelectedControl(control) {
	if (this.owner.controls.length == 0) return false;
	for (var i = 0, l = this.owner.controls.length; i < l; i ++) {
		if (this.owner.controls[i] === control) {
			this.owner.controls.splice(i, 1);
			var box = this.resizeDashBoxs[i];
			this.resizeDashBoxs.splice(i, 1);
			box.hide();
			box.destroy();
			return true;
		}
	}
	return false;
}
// 全部清空
function Designer_Builder_ClearSelectedControl() {
	if (this.owner.controls.length > 0){
		this.owner.controls = [];
	}
	if (this.resizeDashBoxs != null) {
		for (var i = 0, l = this.resizeDashBoxs.length; i < l; i ++) {
			this.resizeDashBoxs[i].hide();
			this.resizeDashBoxs[i].destroy();
		}
	}
	if (this.resizeDashBoxs.length > 0) this.resizeDashBoxs = [];
}

// 调整虚线框位置
function Designer_Builder_ResetDashBoxPos() {
	this.resizeDashBox.attach(this.resizeDashBox.attached);
	var len = this.resizeDashBoxs.length -1;
	for (var i = 0, l = this.owner.controls.length; i < l; i ++) {
		if (i > len) this.resizeDashBoxs.push(new Designer_Builder_DashBox(this));
		var resizeBox = this.resizeDashBoxs[i];
		resizeBox.attach(this.owner.controls[i]);
		resizeBox.setAreaDragBox('no');
		if (this.owner.control === this.owner.controls[i]) {
			resizeBox.hide();
		}
	}
}

function Designer_Builder_OnSerialize(controls) {
	var cs = controls ? controls : this.controls;
	for (var i = 0; i < cs.length; i ++) {
		var c = cs[i];
		if (typeof c.onSerialize == 'function') {
			c.onSerialize(this.owner);
		}
		if (c.children && c.children.length > 0) {
			this.onSerialize(c.children);
		}
	}
}

function Designer_Builder_OnSerialized(controls) {
	var cs = controls ? controls : this.controls;
	for (var i = 0; i < cs.length; i ++) {
		var c = cs[i];
		if (typeof c.onSerialized == 'function') {
			c.onSerialized(this.owner);
		}
		if (c.children && c.children.length > 0) {
			this.onSerialized(c.children);
		}
	}
}

/**********************************************************
描述：
	内部调用函数
功能：
	_Designer_Builder_DoMouseDown      : 鼠标Down事件
	_Designer_Builder_DoMouseMove      : 鼠标Move事件
	_Designer_Builder_DoMouseUp        : 鼠标Up事件
	_Designer_Builder_DoDblClick       : 鼠标DblClick事件
**********************************************************/
function _Designer_Builder_DoMouseDown(event) {
	var currElement = event.srcElement || event.target;
	//处理当前对象焦点事件
	if (!this.owner._doFocus(this)) {return false;}
	//普通模式
	if (this._actionType == 'default' && Designer.eventButton(event) == 1) {
		//若进入resizeBox，则认为是拖拽大小
		if (this.resizeDashBox.isResizeBox(currElement)) {
			// console.info(2);
			this._actionType = 'resize';
			this.resizeDashBox.onDrag(event);
			this.clearSelectedControl(); // 清空所有选择控件
			return;
		}
		// 多选状态
		var isLock = event.ctrlKey;
		//获得当前控件
		var currControl = this.getControlByDomElement(currElement);
		//检测前后选中控件是否相同
		var isSameControl = this.owner.control === currControl;
		// 多选控件的情况
		if (isLock && currControl
				&& (this.owner.control != null && !this.owner.control.container)) {
			var isRemove = this.removeSelectedControl(currControl);
			if (isRemove) {
				var cs = this.owner.controls;
				currControl = cs.length > 0 ? cs[cs.length -1] : null;
				if (currControl && cs.length > 0)
					this.resizeDashBoxs[cs.length -1].hide();
			} else {
				this.addSelectedControl(currControl);
			}
		} else {
			this.clearSelectedControl(); // 清空所有选择控件
			this.addSelectedControl(currControl);
		}
		//记录当前控件
		this.owner.setControl(currControl);
		//移动端鼠标移动
		if (this.isMobile && this.owner.mobileDesigner) {
			this.owner.mobileDesigner.mouseDown(event);
		}

		if (currControl) {
			this._actionType = 'drag';
			//若当前控件为容器，并且选中的子元素不一致也认为是不同
			if (isSameControl && currControl.container) {
				var sDomElement = currControl.selectedDomElement;
				isSameControl = sDomElement && sDomElement.length == 1 && sDomElement[0] == currElement;
			}
			//隐藏待选中框
			this.chooseDashBox.hide();
			//增加按住shift 单击事件 作者 曹映辉 #日期 2014年7月10日
			//增加standardTable类型判断，防止shift+控件双击出现无法操作的问题 作者 曹映辉 #日期 2015年4月1日
			if (event.shiftKey&&('standardTable'==currControl.type||currControl.type=='divcontrol' || currControl.type=="mask")) {
				this._actionType = 'shiftDrag';
				//设置为拖拽样式
				this.domElement.style.cursor = 'move';
				currControl.onShiftDrag(event);
			}
			else{
				// 开始拖拽
				currControl.onDrag(event);
			}

			//片段集禁止编辑属性框
			var isFragmentSet = false;
			for (var current = currControl.parent; current != null; current = current.parent){
				var type = current.type;
				if (type === "fragmentSet"){
					isFragmentSet = true;
				}
			}

			//相同则不重绘属性框
			var attrPanel = this.owner.attrPanel;
			if (!isSameControl && !attrPanel.isClosed && !isFragmentSet && !this.isMobile) attrPanel.open(); // 2009-05-19 取消单击属性框跟随 傅游翔
		} else {
			this.resizeDashBox.hide();
			if (this._dragDomElement && this._dragDomElement.onUnLock) this._dragDomElement.onUnLock();
		}
	}
};

function _Designer_Builder_DoMouseMove(event) {
	var currElement = event.srcElement || event.target, csDomElment, currControl;
	switch (this._actionType) {
	case 'default':
		//工具栏的控件按钮按下状态...
		if (this.owner.toolBarAction != '') {
			this._actionType = 'createControl';
			//设置当前鼠标样式
			this.domElement.style.cursor = 'url(' + this.owner.toolBar.getCursorImgPath() + '),auto';
			//隐藏待选框
			this.chooseDashBox.hide();
			return;
		}
		//恢复当前元素的鼠标样式
		if (!this.resizeDashBox.isResizeBox(currElement)) this.domElement.style.cursor = 'default';

		//移动端鼠标移动
		if (this.isMobile && this.owner.mobileDesigner) {
			this.owner.mobileDesigner.mouseOver(event);
		}

		//若按下Ctrl键，则认为是锁定控件
		if (event.ctrlKey) return;
		currElement = Designer.getDesignElement(currElement);
		csDomElment = this._chooseDomElement;
		if (currElement === csDomElment) {
			//触发当前控件的mouseover
			if (currElement) {
				currControl = this.getControlByDomElement(currElement);
				if (currControl) currControl.mouseOver(event);
			} else this.domElement.style.cursor = 'default';
			return;
		}
		//鼠标样式恢复
		this.domElement.style.cursor = 'default';
		//记录当前元素
		this._chooseDomElement = currElement;
		//设置虚线框
		csDomElment = this.resizeDashBox.attached;
		csDomElment = csDomElment ? csDomElment.options.domElement : null;
		if (currElement && csDomElment !== currElement && !this.owner.isMobile)
			this.chooseDashBox.attach(currElement);
		else
			this.chooseDashBox.hide();
		break;
	case 'shiftDrag':
		this._dragDomElement.onShiftDragMoving(event);
		Designer_AutoYScroll(event, {
			top : this.owner.toolBarDomElement.offsetHeight + 3,
			bottom : 20
		}); // 增加自动滚动
		break;
	case 'drag':
		this._dragDomElement.onDragMoving(event);
		Designer_AutoYScroll(event, {top:this.owner.toolBarDomElement.offsetHeight + 3,bottom:20}); // 增加自动滚动
		break;
	case 'resize':
		this.resizeDashBox.onDragMoving(event);
		break;
	case 'createControl':
		if (this.owner.toolBarAction == '') {
			this._actionType = 'default';
			return;
		}
		//设置当前鼠标样式
		this.domElement.style.cursor = 'url(' + this.owner.toolBar.getCursorImgPath() + '),auto';
		//隐藏待选框
		this.chooseDashBox.hide();
		break;
	}
};

function _Designer_Builder_DoMouseUp(event) {
	var button = Designer.eventButton(event);
	switch (this._actionType) {
	case 'shiftDrag':
		this._dragDomElement.onShiftDragStop(event);
		break;
	case 'drag':
		this._dragDomElement.onDragStop(event);
		break;
	case 'resize':
		this.resizeDashBox.onDragStop(event);
		break;
	case 'createControl':
		//若点击鼠标左键，则创建控件；否则是取消创建状态
		if(button == 1) {
			var tAction = this.owner.toolBarAction;
			var src = event.srcElement || event.target;
			var fragmentSet = $(src).parents("div[fd_type='fragmentSet']");
			if (fragmentSet.length > 0){
				return ;
			}
			//兼容多表单模式载入控件功能
			if(Designer.instance.createSubformControl){
				var srcElement = event.srcElement || event.target;
				if (this.isMobile) {
					 this.owner.mobileDesigner.createControl(Designer.instance.createSubformControl,srcElement);
				} else {
					var selector = "#subform_" + Designer.instance.createSubformControl.options.values.id;
					this.createControl(Designer.instance.createSubformControl, srcElement);
					$(selector,parent.document).remove();
					Designer.instance.createSubformControl = '';
				}
			}else{
				if (tAction != '') {
					this.createControl(tAction, event.srcElement || event.target);
				}
			}
			if (!event.shiftKey) {
				//取消工具栏选中状态
				this.owner.toolBar.cancelSelect();
				//恢复鼠标样式
				this.domElement.style.cursor = 'default';
			}
			return;
		} else if(button == 2) {
			//取消工具栏选中状态
			this.owner.toolBar.cancelSelect();
			//恢复鼠标样式
			this.domElement.style.cursor = 'default';
			if(Designer.instance.createSubformControl){
				if (Designer.instance.isMobile){
					var selector = "#mobileForm_" + Designer.instance.createSubformControl.options.values.id;
					$(selector,parent.document).removeClass("xform_control_selected");
				}else{
					var selector = "#subform_" + Designer.instance.createSubformControl.options.values.id;
					$(selector,parent.document).css("color","black");
				}
				Designer.instance.createSubformControl = '';
			}
		}
		break;
	}
	if(button == 1) {
		if (this.owner.rightMenu)
			this.owner.rightMenu.hide();
		// 获得焦点，为快捷键服务
		var _top = document.body.scrollTop;
		if (this.owner.control) {
			if (this.owner.control.selectedDomElement.length > 0) {
				this.owner.control.selectedDomElement[0].focus();
			}else if (this.owner.control.options.domElement&&this.owner.control.options.domElement.parentNode) {
				//#56544 IE浏览器，选中片段集数据后，再次切换其他片段集数据，不能选中。
				if (this.owner.control.type != "fragmentSet" && this.owner.control.type != "uploadTemplateAttachment")
				this.owner.control.options.domElement.parentNode.focus();
			}
		} else {
			this.domElement.focus();
		}
		if (_top != document.body.scrollTop) {
			document.body.scrollTop = _top;
		}
	}
	else if (button == 2 && this._actionType != 'createControl') {
		if (this.owner.rightMenu && !this.isMobile)
			this.owner.rightMenu.show(event);
	}
	this._actionType = 'default';
};

function _Designer_Builder_DoDblClick(event) {
	// 在designer.js里面的bindEvent方法中，mousedown也会触发dbclick
	if (event.shiftKey || event.type === 'mousedown') {
		return ;
	}
	//  console.info("_Designer_Builder_DoDblClick--");
	var designer = this.owner;
	// console.info((designer.control));

	//片段集禁止编辑属性面板
	if (designer.control){
		for (var current = designer.control.parent; current != null; current = current.parent){
			var type = current.type;
			if (type === "fragmentSet"){
				return ;
			}
			if (type === "eqb") {
				designer.control = current;
				break;
			}
		}
	}
	if (this.owner && this.owner.notAllowDbClick) {
		return;
	}
	if (this.isMobile) {
		var srcElement = event.srcElement || event.target;
		if(srcElement) {
			var table = $(srcElement).closest("table[fd_type='standardTable']")[0];
			var tableControl = this.getControlByDomElement(table);
			if (tableControl) {
				Designer.instance.attrPanel.panel.control = tableControl;
				_Designer_Control_standardTable_style_Set();
			}else{
				//兼容基本信息设置
				var table = $(srcElement).closest("table[id='baseInfoTable']")[0];
				if(table){
					_Designer_Control_baseInfoTable_style_Set();
				}
			}

		}
		return;
	}
	if (designer.control) designer.attrPanel.show(); // 2009-05-19 双击跟随 傅游翔
};

// 让滚动条自动在鼠标移动到某位置是滚动，未采用定时滚动方式,函数名需要修改，不是太好。
function Designer_AutoYScroll(event, opts) {
	if (document.body.scrollHeight > document.body.clientHeight + 10) {
		var max = document.body.scrollHeight - document.body.clientHeight -10;
		var pos = Designer.getMousePosition(event);
		var clientY = pos.y - document.body.scrollTop;
		if (opts.top >= clientY) { // 向上滚动
			var size = document.body.scrollTop - 10;
			document.body.scrollTop = size < 0 ? 0 : size;
		}
		else if (document.body.clientHeight - opts.bottom < clientY) { // 向下滚动
			var size = document.body.scrollTop + 10;
			document.body.scrollTop = size > max ? max : size;
		}
	}
}