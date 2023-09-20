/**
 * 业务建模的树控件
 */
(function(win){
	win.Designer_Config.operations['tree'] = {
		lab : "5",
		imgIndex : 73,
		title : Designer_Lang.tree,
		run : function(designer) {
			designer.toolBar.selectButton('tree');
		},
		type : 'cmd',
		order: 1.6,
		isAdvanced: true,
		select : true,
		cursorImg : 'style/cursor/massdata.cur'
	};
	
	win.Designer_Config.controls.tree = {
		type : "tree",
		storeType : 'field',
		inherit : 'base',
		container : false,
		onDraw : draw,
		drawXML : drawXML,
		drawMobile : drawMobile,
		implementDetailsTable : true,
		onInitialize : OnInitialize,
		onInitializeDict : OnInitializeDict,
		mobileAlign : "left",
		attrs : {
			label : Designer_Config.attrs.label,
			required: {
				text: Designer_Lang.controlAttrRequired,
				value: "true",
				type: 'checkbox',
				checked: false,
				show: true
			},
			width : {
				text: Designer_Lang.controlAttrWidth,
				value: "",
				type: 'text',
				show: true,
				validator: Designer_Control_Attr_Width_Validator,
				checkout: Designer_Control_Attr_Width_Checkout
			},
			encrypt : Designer_Config.attrs.encrypt,
			source : {
				//数据来源
				text: Designer_Lang.tree_attr_source,
				value : '',
				type : 'self',
				draw: Source_Draw,
				show: true
			},
			displayLevel : {
				text: Designer_Lang.tree_attr_displayLevel,
				value: "",
				synchronous: true,
				opts: [{text:Designer_Lang.tree_attr_level1,value:"1"},
						{text:Designer_Lang.tree_attr_level2,value:"2"},
						{text:Designer_Lang.tree_attr_level3,value:"3"},
						{text:Designer_Lang.tree_attr_level4,value:"4"},
						{text:Designer_Lang.tree_attr_level5,value:"5"}],
				type: 'select',
				show: true
			},
			displayType: {
				text: Designer_Lang.tree_attr_displayType,
				value: 'radio',
				type: 'radio',
				opts: [{text:Designer_Lang.tree_attr_radio,value:"radio"},
				       {text:Designer_Lang.tree_attr_checkbox,value:"checkbox"}],
				show: true
			},
			separator: {
				text: Designer_Lang.tree_attr_separator,
				value: 'semicolon',
				type: 'radio',
				opts: [{text:Designer_Lang.tree_attr_semicolonr,value:"semicolon"},
				       {text:Designer_Lang.tree_attr_verticalLine,value:"verticalLine"},
				       {text:Designer_Lang.tree_attr_blankSpace,value:"blankSpace"}],
				show: true
			},
			fullPath: {
				text: Designer_Lang.tree_attr_fullPath,
				value: 'yes',
				type: 'radio',
				opts: [{text:Designer_Lang.tree_attr_yes,value:"yes"},
				       {text:Designer_Lang.tree_attr_no,value:"no"}],
				show: true
			}
		},
		info : {
			name : Designer_Lang.tree
		},
		resizeMode : 'no'
	};
	win.Designer_Config.buttons.tool.push("tree");
	win.Designer_Menus.tool.menu['tree'] = Designer_Config.operations['tree'];
	
	var selfFun = win.Designer_Config.controls.tree.fun = {};
	
	function Source_Draw(name, attr, value, form, attrs, values, control) {
		var buff = [];
		var optoins;
		if (source == null) {
			optoins = getSource();
		} else {
			options = source;
		}
		buff.push("<div id='source'>");
		//应用
		buff.push('<select name="app" onchange="appChange" class="attr_td_select" style="width:95%">');
		buff.push(createOptions(options,values,app));
		buff.push('</select>');
		//表单
		buff.push('<select name="form" onchange="formChange" class="attr_td_select" style="width:95%">');
		buff.push(createOptions(options,values,form));
		buff.push('</select>');
		//字段
		buff.push('<select name="field" onchange="fieldChange" class="attr_td_select" style="width:95%">');
		buff.push(createOptions(options,values,field));
		buff.push('</select>');
		
		buff.push("</div>");
		return Designer_AttrPanel.wrapTitle(name, attr, value, buff.join(''));
	}
	
	win.appChange = function(src) {
		
	};
	
	win.formChange = function(src) {
		var appVal = $("#app").val();
		if (appVal == null || appVal == "") {
			alert(Designer_Lang.tree_attr_selectApp);
		}
		
	};
	
	win.fieldChange = function(src) {
		var formVal = $("#form").val();
		if (formVal == null || formVal == "") {
			alert(Designer_Lang.tree_attr_selectForm);
		}
	};
	
	function createOptions(options,selectText,fdValues,type) {
		var options = [];
		var selectTip;
		var selectedId;
		if (type == "app") {
			selectedId = fdValues.app;
			selectTip = Designer_Lang.tree_attr_selectApp;
		} else if (type == "form") {
			selectedId = fdValues.form;
			selectTip = Designer_Lang.tree_attr_selectForm;
		} else if (type == "field") {
			selectedId = fdValues.field;
			selectTip = Designer_Lang.tree_attr_selectField;
		}
		options.push('<option value=""');
		if (selectedId == null || selectedId == ""){
			options.push(' selected="selected" ');
		}
		options.push('>' + selectTip + '</option>');
		for (var i = 0; i < options.length; i++) {
			options.push('<option value="' + options[i].value + '"');
			if (selectedId == options[i].value){
				options.push(' selected="selected" ');
			}
			options.push('>' + options[i].name + '</option>');
		}
		return options.join("");
	}
	
	function OnInitialize(){
		this.onInitializeDict();
	}
	
	var source;
	function getSource() {
		$.ajax( {
			url : Com_Parameter.ContextPath + "sys/modeling/controls/tree.do?method=source",
			type : 'GET',
			async : false,//同步请求
			success : function(json) {
				source = json;
			},
			dataType : 'json',
			error : function(msg) {
				alert(Designer_Lang.tree_source_error);
			}
		});
		return source;
	};
	
	
	function OnInitializeDict(){
		var dict = [];
		// 实际值
		var realVal = {};
		realVal.id = this.options.values.id;
		realVal.label = this.options.values.label + "(ID)";
		realVal.type = "String";
		
		// 显示值
		var displayTextVal = {};
		displayTextVal.id = this.options.values.id + "_text";
		var textLable = this.options.values.label ? this.options.values.label : this.options.values.label;
		displayTextVal.label = textLable;
		displayTextVal.type = "String";
		
		dict.push(realVal);
		dict.push(displayTextVal);
		this.options.values.__dict__ = dict;
	}
	
	function draw(parentNode, childNode) {
		if (this.options.values.id == null){
			this.options.values.id = "fd_" + Designer.generateID();
		}
		var domElement = _CreateDesignElement('div', this, parentNode, childNode);
		domElement.id = this.options.values.id;
		domElement.style.display="inline-block";
		var _length = 12;
		if (this.options.values.width){
			if(this.options.values.width.toString().indexOf('%') > -1) {
				domElement.style.width = this.options.values.width;
			}else{
				//+5是为了防止设置必填的时候“*”号被换行
			    domElement.style.width = (parseInt(this.options.values.width)+_length) + 'px';
			}
		}else{
			this.options.values.width = 166;
			domElement.style.width =(parseInt(this.options.values.width)+_length) + 'px';
		}
		
		//设置默认与左边文字域绑定
		domElement.label = _Get_Designer_Control_Label(this.options.values, this);
		var values = this.options.values;
		var inputDom = document.createElement('input');
		inputDom.className = "inputsgl";
		$(inputDom).attr("readonly",true);
		inputDom.style.width = ($(domElement).width() - 35)+"px";
		domElement.appendChild(inputDom);
		if (this.options.values.required == "true") {
			$(domElement).append("<span class=txtstrong>*</span>");
		}
	}
	
	//生成框移动端dom对象
	function drawMobile() {
		_Designer_Control_ModifyWidth(this.options.domElement);
		_Designer_Control_hiddenChildElems(this.options.domElement);
		var domElement = _Designer_Control_CreateMobileWrapElement(this.options.domElement,"oldMui muiFormEleWrap");
		$(domElement).append("<div class='muiSelInput' placeholder='" + (this.options.values.label || this.options.values.id) +"'></div>");
		$(domElement).append("<div class='muiSelInputRight'><span class='mui mui-forward'></span></div>");
		return domElement;
	}
	
	function drawXML() {
		var values = this.options.values;
		var buf = [];
		//配置前端需要存储的字段
		buf.push('<extendSimpleProperty ');
		buf.push('name="', values.id, '" ');
		buf.push('label="', values.label + "(ID)", '" ');
		buf.push('type="', 'String', '" ');
		buf.push('length="','200','" ');
		// 控件类型
		buf.push('businessType="', this.type, '" ');
		buf.push('/>');
		
		var customElementProperties = {};
		customElementProperties.isTxt = "true";
		buf.push( '<extendSimpleProperty ');
		buf.push('name="', values.id+"_text", '" ');
		buf.push('label="', values.label , '" ');
		buf.push('type="', 'String', '" ');
		buf.push('customElementProperties="', Designer.HtmlEscape(JSON.stringify(customElementProperties)), '" ');
		buf.push('length="','200','" ');
		// 控件类型
		buf.push('businessType="', this.type, '" ');
		buf.push('/>');
		return buf.join('');
	}

})(window);

