
Designer_Config.controls['map'] = {
		type : "map",
		storeType : 'field',
		inherit    : 'base',
		onDraw : _Designer_Control_Map_OnDraw,
		drawMobile : _Designer_Control_Map_DrawMobile,
		drawXML : _Designer_Control_Map_DrawXML,
		implementDetailsTable : false,
		info : {
			name: Designer_Lang.mapInfoName
		},
		resizeMode : 'all',
		attrs : {
			label : Designer_Config.attrs.label,
			readOnly : Designer_Config.attrs.readOnly,
			required : {
				text: Designer_Lang.mapAttrRequired,
				value: "true",
				type: 'checkbox',
				checked: false,
				show: true
			},
			width : {
				text: Designer_Lang.mapAttrWidth,
				value: "",
				type: 'text',
				show: true,
				validator: Designer_Control_Attr_Width_Validator,
				checkout: Designer_Control_Attr_Width_Checkout
			},
			tipInfo : Designer_Config.attrs.tipInfo,
			radius : {
				text: "地点选择范围",
				value: "",
				type: 'text',
				hintAfter : "米,说明:为空，则可选择任何地址；填写范围后，则只能选择提交人当前定位为中心，以地点范围为半径内的地点",
				show: true,
				validator: _Designer_Control_Map_Radius_Validate
			},
			isModify : {
				text: "显示值",
				value: "",
				checked : true,
				type: 'checkbox',
				checkboxHint : "允许手动修改",
				show: true,
				hintAfter : "说明:勾选则从地图选择后可修改地点名显示值，不勾选则选择地图后，地点显示值不可修改。若控件只读此属性无效。"
			},
			defaultValue : {
				text: "初始值",
				value: "",
				type: 'select',
				show: true,
				opts : [{
					value : "",
					text : "无"
				},{
					value : "PERSON_POSITION",
					text : "提交人当前位置"
				}]
			}
		}
};

Designer_Config.operations['map'] = {
		lab : "2",
		imgIndex : 53,
		order : 10.1,
		title : Designer_Lang.mapTitle,
		run : function (designer) {
			designer.toolBar.selectButton('map');
		},
		type : 'cmd',
		select: true,
		cursorImg: Com_Parameter.ContextPath + 'sys/attend/map/xform/style/cursor/map.cur'
	};

Designer_Config.buttons.tool.push("map");

function _Designer_Control_Map_OnDraw(parentNode, childNode){
	var domElement = _CreateDesignElement('div', this, parentNode, childNode);
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();
	domElement.id = this.options.values.id;
	$(domElement).attr("label",_Get_Designer_Control_Label(this.options.values, this));
	domElement.style.display = 'inline-block';
	var imgUrl = Com_Parameter.ContextPath + 'sys/attend/map/xform/style/img/sys_attend_map.png';
	var html = "<input myId = '"+this.options.values.id+"' type='text' class='inputsgl' style=' BACKGROUND: url(" + imgUrl + ") no-repeat right; DISPLAY: inline-block;width:93%;'";
	var values = this.options.values;
	// 设置宽度
	if(values.width==null || values.width==''){
		values.width = "120";
	}
	if (values.width.toString().indexOf('%') > -1) {
		$(domElement).css('width',values.width);
		//由于地图标签不能兼容display:inline，故另设一个_width，存储宽度
		$(domElement).attr('_width',values.width);
	} else {
		$(domElement).css('width',(parseInt(values.width) + 5) + 'px');
		$(domElement).attr('_width',(parseInt(values.width) + 5) + 'px');
	}
	// 设置只读
	if(values.readOnly == 'true'){
		html += ' _readOnly="true"';
		domElement.setAttribute('_readOnly',values.readOnly);
	}
	//设置提示语
	if(values.tipInfo != null && values.tipInfo != ''){
		html += ' tipInfo="'+values.tipInfo+'"';
		domElement.setAttribute('tipInfo',values.tipInfo);
	}
	// 设置地点范围
	html += ' radius="'+values.radius+'"';
	domElement.setAttribute('radius',values.radius || "");
	
	// 设置显示值
	html += ' ismodify="'+values.isModify+'"';
	domElement.setAttribute('ismodify',values.isModify || "");
	
	// 设置默认值
	html += ' defaultvalue="'+values.defaultValue+'"';
	domElement.setAttribute('defaultvalue',values.defaultValue || "");
	
	// 设置必填
	if(values.required == 'true'){
		html += ' required="true" _required="true"/>';
		domElement.setAttribute('required',values.required);
		domElement.setAttribute('_required',values.required);
		//必填星号
		html += "<span class='txtstrong'>*</span>";
	}else{
		html += '/>';
	}
	domElement.innerHTML = html;
}

function _Designer_Control_Map_DrawXML(){
	var values = this.options.values;
	var buf = [];
	
	var customElementProperties = {};
	customElementProperties.radius = values.radius;
	customElementProperties.isModify = values.isModify;
	
	buf.push('<extendSimpleProperty ');
	buf.push('name="', values.id, '" ');
	buf.push('label="', values.label, '" ');
	buf.push('type="', 'String', '" ');
	buf.push('customElementProperties="', Designer.HtmlEscape(JSON.stringify(customElementProperties)), '" ');
	buf.push('/>');
	buf.push('<extendSimpleProperty ');
	buf.push('name="', values.id + 'Coordinate', '" ');
	buf.push('label="', values.label + Designer_Lang.mapCoordinate, '" ');
	buf.push('type="', 'String', '" ');
	buf.push('/>');
	/**#56902 start**/
	  buf.push('<extendSimpleProperty ');
	  buf.push('name="', values.id + 'Detail', '" ');
	  buf.push('label="', values.label + Designer_Lang.mapDetail, '" ');
	  buf.push('type="', 'String', '" ');
	  buf.push('/>');
	  /**#56902 end**/
	  buf.push('<extendSimpleProperty ');
	  buf.push('name="', values.id + 'Province', '" ');
	  buf.push('label="', values.label + Designer_Lang.mapProvince, '" ');
	  buf.push('type="', 'String', '" ');
	  buf.push('/>');
	  buf.push('<extendSimpleProperty ');
	  buf.push('name="', values.id + 'City', '" ');
	  buf.push('label="', values.label + Designer_Lang.mapCity, '" ');
	  buf.push('type="', 'String', '" ');
	  buf.push('/>');
	  buf.push('<extendSimpleProperty ');
	  buf.push('name="', values.id + 'District', '" ');
	  buf.push('label="', values.label + Designer_Lang.mapDistrict, '" ');
	  buf.push('type="', 'String', '" ');
	  buf.push('/>');
	return buf.join('');
}

function _Designer_Control_Map_Radius_Validate(elem, name, attr, value, values){
	var pass = false;
	// 为空或者为大于等于200的数字则通过
	if (value != null && value != '') {
		if((/^(\d+)$/.test(value.toString()))){
			var val = parseFloat(value.toString());
			if(val >= 200){
				pass = true;
			}
		}else{
			alert("地点选择范围必须为大于等于200的数字!");
			return pass;
		}
	}else{
		pass = true;
	}
	if(!pass){
		alert("地点选择范围不能小于200!");
	}
	return pass;
}