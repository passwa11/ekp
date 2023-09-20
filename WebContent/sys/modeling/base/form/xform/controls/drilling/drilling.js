/**
 * 业务建模的占位符
 */
(function(win){
	win.Designer_Config.operations['drilling'] = {
		lab : "5",
		imgIndex : 73,
		title : Designer_Lang.drilling,
		run : function(designer) {
			designer.toolBar.selectButton('drilling');
		},
		type : 'cmd',
		order: 1.6,
		isAdvanced: true,
		select : true,
		cursorImg : 'style/cursor/massdata.cur'
	};
	
	win.Designer_Config.controls.drilling = {
		type : "drilling",
		storeType : 'none',
		inherit : 'base',
		container : false,
		onDraw : draw,
		drawXML : drawXML,
		implementDetailsTable : true,
		attrs : {
			label : Designer_Config.attrs.label,
		},
		info : {
			name : Designer_Lang.drilling
		},
		resizeMode : 'no'
	};
	win.Designer_Config.buttons.tool.push("drilling");
	win.Designer_Menus.tool.menu['drilling'] = Designer_Config.operations['drilling'];
	
	var selfFun = win.Designer_Config.controls.drilling.fun = {};
	
	// 画大数据呈现
	function draw(parentNode, childNode) {
		if (this.options.values.id == null){
			this.options.values.id = "fd_" + Designer.generateID();
		}
		var domElement = _CreateDesignElement('div', this, parentNode, childNode);
		domElement.id = this.options.values.id;
		domElement.style.display="inline";
		//设置默认与左边文字域绑定
		domElement.label = _Get_Designer_Control_Label(this.options.values, this);
		var values = this.options.values;
		var inputDom = document.createElement('input');
		inputDom.className="inputsgl";
		inputDom.style.width = "120px";
		domElement.appendChild(inputDom);
		var a = document.createElement("a");
		a.innerText = "选择";
		domElement.appendChild(a);
	}
	
	
	function drawXML() {
		var values = this.options.values;
		var buf = [];
		//配置前端需要存储的字段
		buf.push('<extendSimpleProperty ');
		buf.push('name="', values.id, '" ');
		buf.push('label="', values.label, '" ');
		buf.push('type="', 'String', '" ');
		buf.push('length="','4000','" ');
		// 控件类型
		buf.push('businessType="', this.type, '" ');
		buf.push('/>');
		return buf.join('');
	}

})(window);

