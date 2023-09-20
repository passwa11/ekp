function _Get_Designer_FieldLayout_Control_Label(values, control) {
	if (control.attrs.fieldlayoutLabel && values._label_bind == null) {
		values._label_bind = 'true';
	}
	if (values._label_bind == 'true') {
		var textLabel = control.getRelatedTextControl();
		values.fieldlayoutLabel= textLabel ? textLabel.options.values.content : '';
		values._label_bind_id = textLabel ? textLabel.options.values.id : '';
	}
	return values.fieldlayoutLabel;
}

Designer_Config.operations['fieldlaylout'] = {
	lab : "5",
	imgIndex : 30,
	title :Designer_Lang.fieldLayout,
	run : function(designer) {
		designer.toolBar.selectButton('fieldlaylout');
	},
	type : 'cmd',
	order: 12,
	shortcut : '',
	isAdvanced: false,
	select : true,
	cursorImg : 'style/cursor/fieldlayout.cur'
};
Designer_Config.controls.fieldlaylout = {
	type : "fieldlaylout",
	storeType : 'none',
	inherit : 'base',
	container : false,
	onDraw : _Designer_Control_fieldlaylout_OnDraw,
	onDrawEnd : _Designer_Control_fieldlaylout_OnDrawEnd,
	drawXML : _Designer_Control_fieldlaylout_DrawXML,
	onInitialize : _Designer_fieldlayout_OnInitialize,
	implementDetailsTable : false,
	destroy: function(){
		//调用系统的销毁方法
		Designer_Control_Destroy.call(this);
		
		if(!this.owner.owner.fieldPanel.isClosed){
			//刷新基本属性布局已选属性面板
			this.owner.owner.fieldPanel.open();
		}
		
	},
	onAttrLoad : function(){
	},
	attrs : {
		fieldlayoutLabel : {
			text : Designer_Lang.controlAttrLabel,
			value : '',
			type : 'fieldlayoutLabel',
			convertor: Designer_Control_Attr_HtmlQuotEscapeConvertor,
			lang: true,
			show : true
		},
		fieldNames : {
			text : Designer_Lang.fieldLayout_basicAttributeField,
			value : '',
			label:'',
			required: true,
			readOnly:true,
			width:"75%",
			type : 'comText',
			operate:'_fieldContorl_Choose',
			checkout:Designer_Layout_Control_Source_Required_Checkout,
			show : true
		},
		fieldIds : {
			text : Designer_Lang.fieldLayout_basicAttributeFieldId,
			value : '',
			type : 'text',
			show : false
		},
		
		paramsJSP : {
			text : Designer_Lang.fieldLayout_parameterJSPPath,
			value : '',
			type : 'text',
			show : false
		}
		,
		fieldParams : {
			text : Designer_Lang.fieldLayout_parameterSetting,
			value : '',
			type : 'self',
			draw : _Designer_Control_Attr_fieldlaylout_params_Self_Draw,
			checkout:Designer_Layout_Control_fieldParams_Required_Checkout,
			show : true
		}
	},
	info : {
		name : Designer_Lang.fieldLayout
	},
	resizeMode : 'no'
};
Designer_Config.buttons.layout.push("fieldlaylout");
Designer_Menus.layout.menu['fieldlaylout'] = Designer_Config.operations['fieldlaylout'];

//修复历史模板自动换行
function _Designer_fieldlayout_OnInitialize(){
	$(this.options.domElement).css("word-wrap","normal");
	$(this.options.domElement).css("word-break","keep-all");
	var values = this.options.values;
	for(var i=0;i<_FieldsDictVarInfoByModelName.length;i++){
		if(values.fieldIds==_FieldsDictVarInfoByModelName[i].name){
			values.__type = _FieldsDictVarInfoByModelName[i].type;
			values.formIds = _FieldsDictVarInfoByModelName[i].formIds;
			break;
		}
	}
}


function _fieldContorl_Choose(a,name){
	var baseObjs =_FieldsDictVarInfoByModelName;
	var objFieldsInDesign=Designer.instance.fieldPanel.panel.getfieldsInDesign();
	var fileds=[];
	for(var i=0;i<baseObjs.length;i++){
		var f=baseObjs[i];
		//已经出现在设计面板中的属性不需要重新选择
		if(!objFieldsInDesign[f.name]){
			fileds.push(f);
		}
	}
	
	RelatoinFormFieldChoose(document.getElementById(name),document.getElementById(name),function(rtn){
		if(rtn&&rtn.data&&rtn.data[0].id){
			var control=Designer.instance.attrPanel.panel.control;
			var values=control.options.values;
			
			document.getElementById(name).value=rtn.data[0].name;
			values.fieldNames=rtn.data[0].name;
			values.fieldIds=rtn.data[0].id;
			
			values.paramsJSP=_FieldsDictVarInfoByModelNameObj[values.fieldIds].jspParams;
		}
	},fileds);
}
function _Designer_Control_fieldlaylout_OnDraw(parentNode, childNode) {
	//修复拖动时label名字+1 by liwc
	var isExit = this.options.values.id ? true : false;
	var labelName = this.options.values.label ? this.options.values.label : '';
	if (this.options.values.id == null){
		this.options.values.id = "fd_" + Designer.generateID();
	}
	if(!_Designer_Index_Object.fieldlayout){
		_Designer_Index_Object.fieldlayout=1;
	}
	var domElement = _CreateDesignElement('div', this, parentNode, childNode);
	var values = this.options.values;
	var inputDom = document.createElement('input');
	inputDom.type='hidden';
	domElement.width="auto";
	domElement.appendChild(inputDom);
	if(values.fontColor){
		domElement.style.color=values.fontColor;
	}
	if(values.fontFamily){
		domElement.style.fontFamily=values.fontFamily;
	}
	if(values.fontSize){
		domElement.style.fontSize=values.fontSize;
	}
	if(values.fontWeight){
		domElement.style.fontWeight=values.fontWeight;
	}
	if(values.fontStyle){
		domElement.style.fontStyle=values.fontStyle;
	}
	if(values.textDecoration){
		domElement.style.textDecoration=values.textDecoration;
	}
	inputDom.id = this.options.values.id;	
	
	var $inputDom = $(inputDom);
	//获取主model的名称 
	$inputDom.attr('modelName',window.parent._xform_MainModelName);
	var label = document.createElement("label");
	var labelText="";
	//判断是否从字段列表中选择
	if(Designer.instance.fieldPanel.chooseField){
		labelText=Designer.instance.fieldPanel.chooseField.label;
		this.options.values.fieldNames=labelText;
		this.options.values.fieldIds=Designer.instance.fieldPanel.chooseField.name;
	}
	else if(this.options.values.fieldNames){
		labelText=this.options.values.fieldNames;
	}
	else{
		//修复拖动时label名字+1 by liwc
		if (labelName){
			labelText = labelName;
		}else{
			labelText=Designer_Lang.fieldLayout_basicAttribute+_Designer_Index_Object.fieldlayout++;
		}
		
	}
	$inputDom.attr('fieldIds',values.fieldIds);
	
	for(var i=0;i<_FieldsDictVarInfoByModelName.length;i++){
		if(values.fieldIds==_FieldsDictVarInfoByModelName[i].name){
			$inputDom.attr('fieldNames',_FieldsDictVarInfoByModelName[i].label);
			$inputDom.attr('formIds',_FieldsDictVarInfoByModelName[i].formIds);
			$inputDom.attr('jspParams',_FieldsDictVarInfoByModelName[i].jspParams);
			//设置参数页面的路径
			values.paramsJSP=_FieldsDictVarInfoByModelName[i].jspParams;
			$inputDom.attr('jspRunProfix',_FieldsDictVarInfoByModelName[i].jspRunProfix);
			values.__type = _FieldsDictVarInfoByModelName[i].type;
			break;
		}
	}
	if(values.fieldParams){
		$inputDom.attr('fieldParams',values.fieldParams);
	}else{
		$inputDom.attr('fieldParams','{}');
	}
	
	$inputDom.attr('fieldlayoutLabel',_Get_Designer_FieldLayout_Control_Label(this.options.values, this));
	
	this.attrs.fieldNames.label=labelText;
	values.label=labelText;
	label.innerHTML="["+labelText+"]";
	label.style.width='auto';
	domElement.appendChild(label);
}
function _Designer_Control_fieldlaylout_OnDrawEnd(){
	//情况字段面板的值
	Designer.instance.fieldPanel.chooseField='';
	
	if(!Designer.instance.fieldPanel.isClosed){
		//刷新基本属性布局已选属性面板
		Designer.instance.fieldPanel.open();
	}
}

function _Designer_Control_fieldlaylout_DrawXML(){
	
}

function Designer_Layout_Control_fieldParams_Required_Checkout(msg, name, attr, value, values, control){
	var params={};
	if(value){
		params=JSON.parse(values.fieldParams.replace(/quot;/g,"\""));
	}
	if(!values.fieldIds){
		return false;
	}
	//判断某些字段中是否存在不能为空的参数
    var objElement = _FieldsDictVarInfoByModelNameObj[values.fieldIds];
	if (objElement) {
        var requiredParams=objElement.requiredParams;
        if(requiredParams){
            var requiredParamsAry= requiredParams.split(",");
            for(var i=0;i<requiredParamsAry.length;i++){
                if(!params[requiredParamsAry[i]]){
                    msg.push(_FieldsDictVarInfoByModelNameObj[values.fieldIds].label,"," + Designer_Lang.fieldLayout_mustSetTheRequiredParameters);
                    break;
                }
            }
            if(msg.length>0){
                return false;
            }
        }
    }
	return true;
}
function Designer_Layout_Control_Source_Required_Checkout(msg, name, attr, value, values, control){
	if(values.fieldNames&&values.fieldIds){
		return true;
	}
	msg.push(control.attrs.fieldNames.label,","+Designer_Lang.fieldLayout_notNull);
	return false;
}
function _Designer_Control_Attr_fieldlaylout_params_Self_Draw(name, attr, value,
		form, attrs, values, control){
	var html="";
	html+="<a href='javascript:void(0)' onclick='_Designer_Control_fieldlaylout_OpenParams(this);' style='text-decoration:underline;margin-left:5px'>"+Designer_Lang.fieldLayout_setParameters+"<a>";
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}
function _Designer_Control_fieldlaylout_OpenParams(obj){
	//避免通过这种Designer.instance.control方式取contorl后焦点丢失是失去对象信息
	var control=Designer.instance.attrPanel.panel.control;
	var values=control.options.values;
	if(!(values.fieldNames&&values.fieldIds)){
		alert(Designer_Lang.fieldLayout_pleaseChooseBaseAttributeControlFirst);
		return;
	}
	var paramRtnVal="";
	if(values.fieldParams){
		paramRtnVal=JSON.parse(values.fieldParams.replace(/quot;/g,"\""));
	}
	var dialog=new ModelDialog_Show(Com_Parameter.ContextPath+values.paramsJSP,paramRtnVal,function(rtnVal){
    	// 没有选择函数
		if(!rtnVal||rtnVal=='undefined'){
			return ;
		}
		//将rtnValue复制一份，防止出现无法解析的js错误 #32750 作者 曹映辉 #日期 2016年11月9日
		values.fieldParams=JSON.stringify(
				jQuery.extend({}, rtnVal)).replace(/"/g,"quot;");
		//保存属性参数
		Designer.instance.attrPanel.panel.resetValues();
		var style=control.options.domElement.style;
		style.width="auto";
		if(rtnVal.control_fontColor) {
			style.color=rtnVal.control_fontColor;
			control.options.values.fontColor=style.color;
		}
		if(rtnVal.control_fontFamily){ 
			style.fontFamily=rtnVal.control_fontFamily;
			control.options.values.fontFamily=style.fontFamily;
		}
		if(rtnVal.control_fontSize){ 
			style.fontSize=rtnVal.control_fontSize;
			control.options.values.fontSize=style.fontSize;
		}
		if(rtnVal.control_fontB){
			style.fontWeight=rtnVal.control_fontB;
			control.options.values.fontWeight=style.fontWeight;
		}
		if(rtnVal.control_fontI){
			style.fontStyle=rtnVal.control_fontI;
			control.options.values.fontStyle=style.fontStyle;
		}
		if(rtnVal.control_fontU){ 
			style.textDecoration=rtnVal.control_fontU;
			control.options.values.textDecoration=style.textDecoration;
		}
		
		Designer.instance.builder.resetDashBoxPos();
    });
	dialog.setWidth(window.screen.width*350/1366+"");
	dialog.show();
}
//监听撤回事件,刷新已选控件列表
DesignerUndoSupport.on("undo",function(control){
	if(!Designer.instance.fieldPanel.isClosed){
		//刷新基本属性布局已选属性面板
		Designer.instance.fieldPanel.open();
	}
});
//监听恢复事件,刷新已选控件列表
DesignerUndoSupport.on("redo",function(control){
	if(!Designer.instance.fieldPanel.isClosed){
		//刷新基本属性布局已选属性面板
		Designer.instance.fieldPanel.open();
	}
});
function ModelDialog_Show(url,data,callback){
	this.AfterShow=callback;
	this.data=data;
	this.width=window.screen.width*600/1366;
	this.height=window.screen.height*400/768;
	this.url=url;
	this.setWidth=function(width){
		this.width=width;
	};
	this.setHeight=function(height){
		this.height=height;
	};
	this.setCallback=function(action){
		this.callback=action;
	};
	this.setData=function(data){
		this.data=data;
	};
	
	this.show=function(){
		var obj={};
		obj.data=this.data;
		obj.AfterShow=this.AfterShow;
		Com_Parameter.Dialog=obj;
		var left = (screen.width-this.width)/2;
		var top = (screen.height-this.height)/2;
		//Safari浏览器下，window.open打开的是一个新页面，宽度大小无效？这里用模态窗口处理
		if(window.showModalDialog){
			var winStyle = "resizable:1;scroll:1;dialogwidth:"+this.width+"px;dialogheight:"+this.height+"px;dialogleft:"+left+";dialogtop:"+top;
			var win = window.showModalDialog(url, obj, winStyle);
		}else{
			var winStyle = "resizable=1,scrollbars=1,width="+this.width+",height="+this.height+",left="+left+",top="+top+",dependent=yes,alwaysRaised=1";
			var win = window.open(url, "_blank", winStyle);
		}
		try{
			win.focus();
		}
		catch(e){
			
		}
		//用window.open 达到模态效果
		/* 当回调函数有alert弹框的时候，窗口会被挡住 by zhugr 2017-08-31
		 window.onfocus=function (){
			try{
				win.focus();
			}catch(e){
				
			}
		};*/
	    window.onclick=function (){
	    	try{
				win.focus();
			}catch(e){
				
			}
	    };
	};
	
}