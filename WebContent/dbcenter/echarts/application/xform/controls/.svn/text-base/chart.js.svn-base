/**********************************************************
功能：图表控件
使用：
	
by zhugr 2018-09-21
**********************************************************/
Com_IncludeFile("chart.css",Com_Parameter.ContextPath + 'dbcenter/echarts/application/xform/controls/','css',true);
Com_IncludeFile('chartMode.js',Com_Parameter.ContextPath+'dbcenter/echarts/application/common/','js',true);
Com_IncludeFile('DbEchartsApplication_Dialog.js',Com_Parameter.ContextPath+'dbcenter/echarts/application/common/','js',true);
Com_IncludeFile('userInfo.js',Com_Parameter.ContextPath+'dbcenter/echarts/application/common/','js',true);

Designer_Config.controls['dbechart'] = {
		type : "dbechart",
		storeType : 'none',
		inherit    : 'base',
		onDraw : _Designer_Control_Dbechart_OnDraw,
		drawXML : _Designer_Control_Dbechart_DrawXML,
		implementDetailsTable : false,
		info : {
			name: Designer_Lang.dbChartChartControl
		},
		resizeMode : 'all',
		attrs : {
			label : Designer_Config.attrs.label,
			width : {
				text: Designer_Lang.controlAttrWidth,
				value: "",
				type: 'text',
				show: true,
				validator: Designer_Control_Attr_Width_Validator,
				checkout: Designer_Control_Attr_Width_Checkout
			},
			height : {
				text: Designer_Lang.dbChartHeight,
				value: "",
				type: 'text',
				show: true,
				validator: Designer_Control_Attr_Width_Validator,
				checkout: Designer_Control_Attr_Width_Checkout
			},
			source : {
				text: Designer_Lang.dbChartChartType,
				value : '',
				type : 'self',
				draw : _Designer_Control_Attr_Dbechart_Source_Draw,
				show: true
			},
			category : {
				text: Designer_Lang.dbChartChartChoose,
				value : '',
				type : 'self',
				draw : _Designer_Control_Attr_Dbechart_Category_Draw,
				show: true,
				validator : _Designer_Control_Attr_Dbechart_Category_Validator,
				checkout : [_Designer_Control_Attr_Dbechart_Category_Checkout]
			},
			categoryId : {
				text: "图表选择Id",
				value : '',
				show: false
			},
			mainUrl : {
				text: "url",
				value : '',
				show: false
			},
			mobileUrl : {
				text: "mobileUrl",
				value : '',
				show: false
			},
			inputs : {
				text : Designer_Lang.dbChartDynamicInput,
				value : '',
				type : 'self',
				draw : _Designer_Control_Attr_Dbechart_Input_Draw,
				show : true
			}
		}
};

Designer_Config.operations['dbechart'] = {
		lab : "2",
		imgIndex : 70,
		title : Designer_Lang.dbChartChartControl,
		order: 10,
		run : function (designer) {
			designer.toolBar.selectButton('dbechart');
		},
		type : 'cmd',
		select: true,
		cursorImg: 'style/cursor/chart.cur',
		isShow: Designer_Control_Dbechart_IsVisibel
	};

Designer_Config.buttons.tool.push("dbechart");

Designer_Menus.tool.menu['dbechart'] = Designer_Config.operations['dbechart'];


//属性面板确定校验
function _Designer_Control_Attr_Dbechart_Category_Validator(){
	var control = Designer.instance.attrPanel.panel.control;
	var category = $("input[name='category']").val();
	if (!category) {
		alert(Designer_Lang.dbChartCategoryNotNull);
		return false;
	}
	return true;
}

//提交校验
function _Designer_Control_Attr_Dbechart_Category_Checkout(msg, name, attr, value, values,control) {
	var category = values.category;
	if (!category) {
		msg.push(Designer_Lang.dbChartChartControl + ": " + Designer_Lang.dbChartCategoryNotNull);
		return false;
	}
	return true;
}


function Designer_Control_Dbechart_IsVisibel(){
	var flag = false;
	var dbechartData = {};
	dbechartData.role = "ROLE_DBCENTERECHARTS_DEFAULT";
	var url = Com_Parameter.ContextPath + 'sys/xform/sys_form_template/sysFormTemplate.do?method=checkAuth';
	$.ajax({ 
		url: url, 
		data: dbechartData,
		async: false, 
		dataType: "json", 
		cache: false, 
		success: function(rtn){
			if("1" == rtn.status){
				flag = true;
			}
		}
	});
	return flag;
}

function _Designer_Control_Dbechart_OnDraw(parentNode,childNode){
	var domElement = _CreateDesignElement('div', this, parentNode, childNode);
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();
	domElement.id = this.options.values.id;
	domElement.style.width='27px';
	//设置默认与左边文字域绑定
	domElement.label = _Get_Designer_Control_Label(this.options.values, this);
	
	var values = this.options.values;
	
	// inputhidden 保存属性值
	var inputDom = document.createElement('input');
	inputDom.type='hidden';
	domElement.appendChild(inputDom);
	
	inputDom.id = this.options.values.id;
	if(values.label){
		$(inputDom).attr("data-label",values.label);
	}
	if(values.source){
		$(inputDom).attr("data-source",values.source);
	}
	if(values.category){
		$(inputDom).attr("data-category",values.category);
	}
	if(values.categoryId){
		$(inputDom).attr("data-categoryid",values.categoryId);
	}
	if(values.mainUrl){
		$(inputDom).attr("data-mainurl",values.mainUrl);
	}
	if(values.mobileUrl){
		$(inputDom).attr("data-mobileUrl",values.mobileUrl);
	}
	if(values.inputs){
		$(inputDom).attr("data-inputs",values.inputs);
	}
	if(values.chartType){
		$(inputDom).attr("data-charttype",values.chartType);
	}
	
	// 暂存对应图表模板的宽度和高度，至于在流程文档具体用哪个宽高，后台解析决定
	if(values.chartWidth){
		$(inputDom).attr("data-chartWidth",values.chartWidth);
	}
	if(values.chartHeight){
		$(inputDom).attr("data-chartHeight",values.chartHeight);
	}
	
	if(values.width){
		$(inputDom).attr("data-width",values.width);
	}
	if(values.height){
		$(inputDom).attr("data-height",values.height);
	}
	
	// 图标
	var html = document.createElement("label");
	html.style.background = "url(style/img/chart.png) no-repeat";
	html.style.width='24px';
	html.style.height='24px';
	html.style.display="inline-block";
	$(domElement).append(html);
}

function _Designer_Control_Attr_Dbechart_Source_Draw(name, attr, value,form, attrs, values, control){
	var html = "";
	// dbEchartChartMode 来源于chartMode.js
	var options = dbEchartChartMode.getMode("chart;custom");
	html += '<select name="source" class="attr_td_select" style="width: 95%;" onchange="_Designer_Control_Attr_Dbechart_Source_Change(this)">';
	for(var i = 0;i < options.length;i++){
		var option = options[i];
		html += "<option value='"+ option.value + "' ";
		if(value == option.value){
			html += " selected ";
		}
		html += ">" + option.text + "</option>"
	}
	html += "</select>";
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

// 图表类型切换的时候清空已选的相关选项
function _Designer_Control_Attr_Dbechart_Source_Change(dom){
	// 清除图表选择 
	$("[name='category']").val("");
	// 清除入参
	$("#dbEchart_Input_wrap").html("");
	// 同时触发图表选择
	_Designer_Control_Attr_Category_Choose(_Designer_Control_Attr_Dbechart_Category_Cb);
	// 重置默认宽高
	_Designer_Control_Attr_Dbechart_SetInfo(Designer.instance.attrPanel.panel.control.options.values,{"width":"",height:""});
}

// 图表选择
function _Designer_Control_Attr_Dbechart_Category_Draw(name, attr, value,form, attrs, values, control){
	var html = "";
	value = value ? value : "";
	html += "<input type='text' style='width:75%' class='attr_td_text' name='"+ name +"' readonly value='"+ value +"'/>";
	html += "&nbsp;";
	html += "<span class='txtstrong'>*</span>";
	html += "<a href='javascript:void(0)' onclick='_Designer_Control_Attr_Category_Choose(_Designer_Control_Attr_Dbechart_Category_Cb);'>选择</a>";
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

// 选择图表
function _Designer_Control_Attr_Category_Choose(cb){
	var val = document.getElementsByName("source")[0].value;
	if(val == ""){
		alert(Designer_Lang.dbChartPlzChooseChartType);
		return;
	}
	// dbEchartChartMode 来源于chartMode.js
	var item = dbEchartChartMode.getItemByVal(val);
	var url = Com_Parameter.ContextPath + "dbcenter/echarts/application/dbEchartsApplication.do?method=dialog&echartModelName="
				+ item.templateModelName + "&modeType=chart;custom";
	var dialog = new DbEchartsApplication_Dialog(url,item,cb);
	dialog.show();
}

// 选择图表之后的回调	rn : {value: ,text: ,item}
function _Designer_Control_Attr_Dbechart_Category_Cb(rn){
	if(rn){
		var control = Designer.instance.attrPanel.panel.control;
		_Designer_Control_Attr_Dbechart_Category_FillVal(control,rn);
		_Designer_Control_Attr_Dbechart_Category_BuildInput(rn,function(config){
			var insStr = _Designer_Control_Attr_Dbechart_BuildTable(config);
			$("#dbEchart_Input_wrap").html(insStr);
			_Designer_Control_Attr_Dbechart_SetInfo(control.options.values,config);
		});
	}
}

function _Designer_Control_Attr_Dbechart_SetInfo(values,config){
	values.chartType = config.type || "";
	values.chartWidth = config.width || "";
	values.chartHeight = config.height || "";
}

// 塞值
function _Designer_Control_Attr_Dbechart_Category_FillVal(control,rn){
	// 设置业务名称
	document.getElementsByName("category")[0].value = rn.text;
	control.options.values.categoryId = rn.value;
	control.options.values.mainUrl = rn.item.mainUrl;
	control.options.values.mobileUrl = rn.item.mobileUrl;
	control.options.values.inputs = "";
}

// 处理入参
function _Designer_Control_Attr_Dbechart_Category_BuildInput(rn,cb){
	var inputs = [];
	var data = {};// modelName id 
	data.modelName = rn.item.mainModelName;
	data.id = rn.value;
	$.ajax({
		type : "post",
		async : false,//是否异步
		url : Com_Parameter.ContextPath + "dbcenter/echarts/application/dbEchartsApplication.do?method=findDynamic",
		data : data,
		dataType : "json",
		success : function(ajaxRn) {
			inputs = ajaxRn;
		}
	});
	if(cb){
		cb(inputs);
	}
}

// 入参
function _Designer_Control_Attr_Dbechart_Input_Draw(name, attr, value,form, attrs, values, control){
	var val = value;
	html = "";

	if (values.source) {
		// dbEchartChartMode 来源于chartMode.js
		var item = dbEchartChartMode.getItemByVal(values.source);
		if (!val) {
			val = "{}";
		}
		var mapping = JSON.parse(val.replace(/quot;/g,"\""));
		
		// 构造参数，模拟弹窗确定返回的参数
		var rn = {};
		rn.value = values.categoryId;
		rn.text = values.category;
		rn.item = item;
		_Designer_Control_Attr_Dbechart_Category_BuildInput(rn,function(config){
			html += _Designer_Control_Attr_Dbechart_BuildTable(config,mapping);
			_Designer_Control_Attr_Dbechart_SetInfo(values,config);
		 });
	} else {
		html += "<div id='dbEchart_Input_wrap'>"+ Designer_Lang.dbChartPlzChooseDataSource +"</div>";
	}
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

function _Designer_Control_Attr_Dbechart_BuildTable(config,mapping){
	var html = ""; 
	var insStr = "";
	// config:{tables:配置模式)、长宽设置、图表类型（饼图、折线图）、入参（inputs:编程模式）、类型（type：配置模式01、编程模式11、自定义数据00)}
	var type = config.type;
	// 编程模式
	if(type == "11"){
		if(config.inputs){
			insStr += "<div class='chart-source'>";
			for(var i = 0;i < config.inputs.length;i++){
				var inputField = config.inputs[i];
				var input = {};
				input.name = inputField.key;
				if (mapping && mapping[input.name]) {
					input.textVal = mapping[input.name].text;
				}
				input.type = inputField.format;
				input.text = inputField.name;
				insStr += _Designer_Control_Attr_Dbechart_InputParams(input) + "<br/>";
			}
			insStr += "</div>";
		}
	}else{
		// 自定义数据和配置模式一样的处理规则
		if(config.tables){
			var tables = config.tables;
			if(tables.length > 0){
				for ( var i = 0; i < tables.length; i++) {
					var table = tables[i];
					if(table.dynamic && table.dynamic.length > 0){
						insStr += "<div class='chart-source'>";
						if(table.titleTxt){
							insStr += "<div style='text-align:center;'>" + table.titleTxt + "</div>";	
						}
						for(var j = 0;j < table.dynamic.length;j++){
							var dynamic = table.dynamic[j];
							var input = {};
							var fieldName = dynamic.field.name;
							if(table && table.titleVal){
								fieldName = table.titleVal + "." + fieldName;
							}
							input.name = fieldName;
							if (mapping && mapping[fieldName]) {
								input.textVal = mapping[fieldName].text;
							}
							input.type = dynamic.field.type;
							input.text = dynamic.field.text;
							insStr += _Designer_Control_Attr_Dbechart_InputParams(input) + "<br/>";
						}
						insStr += "</div>";
					}
				}
			}
		}
	}
	html += "<div id='dbEchart_Input_wrap'> " + insStr + "</div>";
	return html;
}

// 单个输入项 input:{name:xxx,type:xxx,text:xxx,textVal:xxx}
function _Designer_Control_Attr_Dbechart_InputParams(input){
	var html = [];
	var paramName = input.text;
	html.push("<label>" + paramName + "</label>");
	html.push("<br/>");
	var fieldName = input.name;
	input.textVal = input.textVal ? input.textVal:"";
	// 显示文本
	html.push("<input type='text' id='" + fieldName + "_textVal' style='width:80%;' value='"+ input.textVal +"'/>");
	html.push(" <a href='javascript:void(0)' onclick='_Designer_Control_Attr_Dbechart_Input_Choose(\""+ fieldName +"\",\""+ input.type +"\");'>选择</a>");
	return html.join("");
}

function _Designer_Control_Attr_Dbechart_Input_GetLastFieldBySepa(str,separator){
	var arr = str.split(separator);
	return arr[arr.length - 1];
}

// 获取表单控件的信息
function _Designer_Control_Attr_Dbechart_GetObj(){
	var varInfo = Designer.instance.getObj(true);
	var vars = [];
	for(var i = 0;i < varInfo.length;i++){
		var controlInfo = varInfo[i];
		var item = {};
		item.field = controlInfo.name;
		item.fieldText = controlInfo.label;
		item.fieldType = controlInfo.type;
		item.controlType = controlInfo.controlType;
		vars.push(item);
	}
	return vars;
}

// 选择控件
function _Designer_Control_Attr_Dbechart_Input_Choose(fieldName,inputType){

	var type = _Designer_Control_Attr_Dbechart_Input_GetLastFieldBySepa(inputType,"|");
	var url = Com_Parameter.ContextPath + "dbcenter/echarts/application/common/fields_tree.jsp?inputType="+ type;
	
	var data = [];
	data.push({"text":Designer_Lang.dbChartFormControls,"vars":_Designer_Control_Attr_Dbechart_GetObj(),"braces":false});
	
	var items = userInfo.getItems(type); // 获取人员相关
	data.push({"text":Designer_Lang.dbChartCurrentUser,"vars":items,"braces":true});
	data.push({"text":Designer_Lang.dbChartTimeParamter,"vars":[{'field':'date_datetime' ,'fieldText':Designer_Lang.dbChartCurrentTime,'fieldType':'dateTime'}],"braces":true});
	var dialog = new DbEchartsApplication_Dialog(url,data,function(rtn){
		var control = Designer.instance.attrPanel.panel.control;

		control.options.values.inputs = control.options.values.inputs ? control.options.values.inputs : "{}";
		var inputParamsJSON = JSON.parse(control.options.values.inputs.replace(/quot;/g,"\""));
		if(rtn){
			// 设置显示文字
			document.getElementById(fieldName + "_textVal").value = rtn.text;
			// 添加
			inputParamsJSON[fieldName] = rtn;
		}
		control.options.values.inputs = JSON.stringify(
				inputParamsJSON).replace(/"/g,"quot;");
		
	});
	dialog.setWidth("300");
	dialog.setHeight("380");
	dialog.show();
}

function _Designer_Control_Dbechart_DrawXML(){
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
