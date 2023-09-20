/**********************************************************
功能：预订信息展示（携程）
使用：模板配置使用
	
作者：朱国荣
创建时间：2016-9-9
**********************************************************/

Designer_Config.controls['bookinfo'] = {
		type : "bookinfo",
		storeType : 'field',
		inherit    : 'base',
		onDraw : _Designer_Control_BookInfo_OnDraw,
		onDrawEnd : _Designer_Control_BookInfo_OnDrawEnd,
		drawXML : _Designer_Control_BookInfo_DrawXML,
		destroy : _Designer_Control_BookInfo_Destroy,
		_destroy : Designer_Control_Destroy,
		implementDetailsTable : true,
		info : {
			name: Designer_Lang.bookInfo
		},
		attrs : {
			label : Designer_Config.attrs.label,
			width : {
				text: Designer_Lang.controlAttrWidth,
				value: "",
				type: 'text',
				show: true,
				validator: Designer_Control_Attr_Width_Validator,
				checkout: Designer_Control_Attr_Width_Checkout
			}
		}
}

Designer_Config.operations['bookinfo'] = {
		lab : "2",
		imgIndex : 47,
		order : 16,
		title : Designer_Lang.bookInfo,
		run : function (designer) {
			designer.toolBar.selectButton('bookinfo');
		},
		type : 'cmd',
		select: true,
		cursorImg: 'style/cursor/bookInfo.cur',
		isShow: function(){return _ctripIsVisibel;}
	};

Designer_Config.buttons.tool.push("bookinfo");

Designer_Menus.add.menu['bookinfo'] = Designer_Config.operations['bookinfo'];

function _Designer_Control_BookInfo_OnDraw(parentNode, childNode){
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();
	var domElement = _CreateDesignElement('div', this, parentNode, childNode);
	domElement.id = this.options.values.id;	
	domElement.style.display = 'inline-block';
	domElement.label = _Get_Designer_Control_Label(this.options.values, this);
	var img = document.createElement('img');
	img.style.width = '100%';
	img.setAttribute("src","style/img/bookInfo/bookInfoSample.png");
	if (this.options.values.width){
		if(this.options.values.width.toString().indexOf('%') > -1) {
			domElement.style.width = this.options.values.width;
		}
		else{
		    domElement.style.width =parseInt(this.options.values.width) + 'px';
		}
	}
	else{
		this.options.values.width=883;
		 domElement.style.width =parseInt(this.options.values.width) + 'px';
	}
	domElement.appendChild(img);
}

function _Designer_Control_BookInfo_Destroy(){
	var domElement = this.options.domElement;
	var div = document.getElementById(domElement.id);
	if (div != null) {
		div.parentNode.removeChild(div);
	}
	this._destroy();
}

function _Designer_Control_BookInfo_OnDrawEnd(){
	
}

function _Designer_Control_BookInfo_DrawXML(){
	var values = this.options.values;
	var buf = ['<extendSimpleProperty '];
	buf.push('name="', values.id, '" ');
	buf.push('label="', values.label, '" ');
	buf.push('type="', 'String', '" ');
	buf.push('/>');
	return buf.join('');
}