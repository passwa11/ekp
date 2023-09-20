/**
 * 业务建模的占位符
 */
(function(win){
	win.Designer_Config.operations['placeholder'] = {
		lab : "5",
		imgIndex : 73,
		title : Designer_Lang.placeholder,
		run : function(designer) {
			designer.toolBar.selectButton('placeholder');
		},
		type : 'cmd',
		order: 1.65,
		isAdvanced: true,
		select : true,
		cursorImg : 'style/cursor/massdata.cur'
	};
	
	win.Designer_Config.controls.placeholder = {
		type : "placeholder",
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
			maxlength: {
				text: Designer_Lang.controlTextareaMaxlength,
				show: true,
				synchronous: true,
				type: 'text',
				value: '',
				hint: Designer_Lang.controlTextareaMaxlength_hint,
				validator: Designer_Control_Attr_Int_Validator,
				checkout: Designer_Control_Attr_Int_Checkout
			},
			help:{
				text: "帮助:</br>该控件详细的配置需要到\"业务关系 >> 关系设置\"里面进行配置",
				type: 'help',
				align:'left',
				show: true
			},
			defaultLength: {//默认长度，若未设置长度则取改长度
				value : '1000',
				show : false
			}
		},
		info : {
			name : Designer_Lang.placeholder
		},
		resizeMode : 'no'
	};
	win.Designer_Config.buttons.tool.push("placeholder");
	win.Designer_Menus.tool.menu['placeholder'] = Designer_Config.operations['placeholder'];
	
	var selfFun = win.Designer_Config.controls.placeholder.fun = {};
	
	function OnInitialize(){
		this.onInitializeDict();
	}
	
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
		//设置长度限制
		if (this.options.values.maxlength != null && this.options.values.maxlength != '') {
			$(inputDom).attr("maxlength",this.options.values.maxlength);
		}else{
			$(inputDom).attr("maxlength",this.attrs.defaultLength.value);
			$(domElement).attr("defaultLength",this.attrs.defaultLength.value)
		}
		inputDom.style.width = ($(domElement).width() - 35)+"px";
		domElement.appendChild(inputDom);
		var a = document.createElement("a");
		a.innerText = "选择";
		domElement.appendChild(a);
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
		if (values.maxlength != null && values.maxlength != '') {
			buf.push('length="',values.maxlength,'" ');
		} else {
			buf.push('length="',this.attrs.defaultLength.value,'" ');
		}
		//buf.push('length="','200','" ');
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
		if (values.maxlength != null && values.maxlength != '') {
			buf.push('length="',values.maxlength,'" ');
			buf.push('maxlength="',values.maxlength,'" ');
		} else {
			buf.push('length="',this.attrs.defaultLength.value,'" ');
			buf.push('maxlength="',this.attrs.defaultLength.value,'" ');
		}
		//buf.push('length="','200','" ');
		// 控件类型
		buf.push('businessType="', this.type, '" ');
		buf.push('/>');
		return buf.join('');
	}

})(window);

