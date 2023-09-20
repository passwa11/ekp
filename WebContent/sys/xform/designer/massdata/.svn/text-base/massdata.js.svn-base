// 大数据呈现
(function(win){
	win.Designer_Config.operations['massData'] = {
		lab : "5",
		imgIndex : 73,
		title : Designer_Lang.massdata,
		run : function(designer) {
			designer.toolBar.selectButton('massData');
		},
		type : 'cmd',
		order: 2.2,
		shortcut : '',
		isAdvanced: true,
		select : true,
		cursorImg : 'style/cursor/massdata.cur'
	};
	
	win.Designer_Config.controls.massData = {
		type : "massData",
		storeType : 'none',
		inherit : 'base',
		container : false,
		onDraw : _Designer_Control_MassData_OnDraw,
		drawXML : _Designer_Control_MassData_DrawXML,
		implementDetailsTable : false,
		onAttrSuccess : setPanelValues,
		onAttrApply : setPanelValues,
		onAttrLoad : onPanelAttrLoad,
		attrs : {
			label : Designer_Config.attrs.label,
			source : {
				// 数据源
				text: Designer_Lang.relation_select_source,
				value : '',
				type : 'select',
				opts: getSourceOptions(),
				validator : [massDataValidation],
				required: true,
				onchange:'Designer_Config.controls.massData.fun.sourceChange(this)',
				show: true,
				translator: opts_common_translator
			},
			funName : {
				text : Designer_Lang.relation_select_busiName,//'业务名称',
				value : '',
				type : 'comText',
				readOnly : true,
				required: true,
				operate:"Designer_Config.controls.massData.fun.chooseService",
				show : true
			},
			funKey : {
				text : '函数key',
				value : '',
				type : 'text',
				skipLogChange:true,
				show : false
			},
			source_oldValue : {
				text : '数据梨园',
				value : '',
				type : 'text',
				skipLogChange:true,
				show : false
			},
			outputParams : {
				text : "<span style='cursor:pointer' >" + Designer_Lang.massdata_output + 
					"&nbsp;&nbsp;<img src='relation_event/style/icons/addshow.gif' onclick='Designer_Config.controls.massData.fun.buildOutputElement(this,0);' title='" + 
					Designer_Lang.massdata_add_show_out + "' style='margin-top:5px;'></img></span>",//传出参数
				value : '',
				type : 'self',
				draw : ouputAttrDraw,
				show : true,
				getVal:relationCommonOutputParams_getVal,
				translator: relationCommonOutputParams_translator,
				compareChange:relationCommonOutputParams_compareChange,
				displayText: Designer_Lang.massdata_output + Designer_Lang.auditshow_preferenceParam
			},
		
			excelColumns : {
				text : Designer_Lang.massdata_colDefine, 
				value : '',
				type : 'self',
				draw : excelColumnsDraw,
				show : true
			},
			inputParams : {
				//传入参数
				text : Designer_Lang.relation_select_inputParams,//'传入参数',
				value : '',
				checkout: Designer_MassData_Control_InputParams_Required_Checkout,
				type : 'self',
				draw : inputAttrDraw,
				show : true,
				displayText: Designer_Lang.relation_common_base_inputParams,
				getVal : relationCommonOutputParams_getVal,
			    compareChange:relationCommonInputParams_compareChange,
			    translator: relationCommonInputParams_translator
			
			},
			outerSearchParams : {
				//传入参数
				text : Designer_Lang.relation_select_inputParams,//'传入参数',
				value : '',
				show : false,
				skipLogChange:true
			},
			inputParamsRequired : {
				text : '传入参数必填字段',
				value : '',
				type : 'text',
				show : false
			},
			template : {
				text : '模板',
				value : '',
				varInfo : '',
				isLock:false,
				type : 'text',
				show : false
			}
		},
		info : {
			name : Designer_Lang.massdata
		},
		resizeMode : 'no'
	};
	win.Designer_Config.buttons.tool.push("massData");
	win.Designer_Menus.tool.menu['massData'] = Designer_Config.operations['massData'];
	
	var selfFun = win.Designer_Config.controls.massData.fun = {};
	
	

function relationCommonInputParams_translator(change) {
	if (!change) {
		return "";
	}
	var change = JSON.parse(change);
	var html = "<span> 由 (" + change.oldVal + ") 变更为 (" + change.newVal + ")</span>";
	return html; 
}	
function relationCommonInputParams_compareChange(name,attr,oldValue,newValue) {
	var oldValueArr = [];
	var newValueArr = [];
	if (oldValue != ""){
		for (var key in oldValue) {
			var oldText = oldValue[key].fieldNameForm;
			oldValueArr.push(oldText);
		}
	}
	if (newValue != "") {
		for (var key in newValue) {
			var newText = newValue[key].fieldNameForm;
			newValueArr.push(newText);
		}
	}
	var oldValText = oldValueArr.join(";");
	var newValText = newValueArr.join(";")
	if (oldValText != newValText) {
		var textValChange = {};
		textValChange.oldVal = oldValText;
		textValChange.newVal  = newValText;
		textValChange.name = "textValue";
		
		return JSON.stringify(textValChange); 
	}
}
function relationCommonOutputParams_translator(change) {
	if (!change) {
		return "";
	}
	var change = JSON.parse(change);
	var html =[];
	html.push("<span> "+Designer_Lang.from);
	for (var i = 0;i < change.length; i++) {
		var paramChange = change[i];
		if (paramChange.name === "textValue") {
			html.push("("+paramChange.oldVal + ")\&nbsp;\&nbsp;\&nbsp;"+Designer_Lang.to + "\&nbsp;\&nbsp;\&nbsp;("+ paramChange.newVal +")");
		} if (paramChange.name === "hiddenValue") {
			html.push(" ;实际值 " + paramChange.oldVal + Designer_Lang.to + paramChange.newVal);
		}
	}
	html.push("</span>");
	return html.join("");
}
function relationCommonOutputParams_compareChange(name,attr,oldValue,newValue) {
	var changeResult = [];
	var oldTextValue = [];
    for(fm in oldValue) {
	   if(fm.indexOf("fm")!=-1){
		 var fieldName = oldValue[fm].fieldName;
         var fieldTitle = oldValue[fm].fieldTitle;
          oldTextValue.push(fieldName+"#"+fieldTitle);
	   }
    }
    oldTextValue = oldTextValue.join(";");
    
	var newTextValue = [];
    for(fm in newValue) {
	   if(fm.indexOf("fm")!=-1){
		  var fieldName = newValue[fm].fieldName;
          var fieldTitle = newValue[fm].fieldTitle;
          newTextValue.push(fieldName+"#"+fieldTitle);
	   }
    }
    newTextValue = newTextValue.join(";");

	if(oldTextValue!=newTextValue){
		var textValChange = {};
		textValChange.oldVal = oldTextValue;
		textValChange.newVal  = newTextValue;
		textValChange.name = "textValue";
		changeResult.push(textValChange);
	}
		

	
	if (changeResult.length == 0) {
		return "";
	}
	return JSON.stringify(changeResult); 
}
	function relationCommonOutputParams_getVal(name,attr,value,controlValue){
	    var val = value || "";
	    val = val.replace(/quot;/g,"\"");
	    if (val === "") {
	       	return;
	    }
	    val = JSON.parse(val);
	    controlValue[name] = val;
    }
	function getSourceOptions(){
		var options = relationSource.GetOptionsArray();
		options.push({text:Designer_Lang.massdata_excelImport,value:"EXCEL"});
		return options;
	}
	
	// 数据源的必填校验
	function Designer_MassData_Control_Source_Required_Checkout(msg, name, attr, value, values, control){
		if(values.source){
			return true;
		}
		msg.push(values.label,","+Designer_Lang.relation_select_sourceNotNull);
		return false;
	}
	
	// 必填传入参数的校验
	function Designer_MassData_Control_InputParams_Required_Checkout(msg, name, attr, value, values, control){
		var val=value?value:"{}";
		var inputParamsMapping = JSON.parse(val.replace(/quot;/g,"\""));
		if(values.inputParamsRequired){
			var inputRequireds=values.inputParamsRequired.substring(0,values.inputParamsRequired.length-1).split(",");
			for(var i=0;i<inputRequireds.length;i++){
				var hasIn=false;
				for(var field in inputParamsMapping){
					if(field==inputRequireds[i]){
						if(!inputParamsMapping[field].fieldIdForm||!inputParamsMapping[field].fieldNameForm){
							msg.push(Designer_Lang.massdata_event+control.options.values.id,Designer_Lang.massdata_inputParamsNotNull);
							return false;
						}
						hasIn=true;
					}
				}
				if(!hasIn){
					msg.push(Designer_Lang.massdata_event+control.options.values.id,Designer_Lang.massdata_inputParamsNotNull);
					return false; 
				}
			}
		}
		return true;
	}

	// 画大数据呈现
	function _Designer_Control_MassData_OnDraw(parentNode, childNode) {
		if (this.options.values.id == null){
			this.options.values.id = "fd_" + Designer.generateID();
		}
		var domElement = _CreateDesignElement('div', this, parentNode, childNode);
		domElement.style.width='100%';
		//设置默认与左边文字域绑定
		domElement.label = _Get_Designer_Control_Label(this.options.values, this);
		var values = this.options.values;
		var inputDom = document.createElement('input');
		inputDom.type='hidden';
		domElement.appendChild(inputDom);
		inputDom.id = this.options.values.id;
		
		$(inputDom).attr("label", values.label);
		
		if (values.outputParams) {
			$(inputDom).attr("outputParams", values.outputParams);
		}
		
		if (values.inputParams) {
			$(inputDom).attr("inputParams",values.inputParams);
		}
		
		if (values.excelColumns) {
			$(inputDom).attr("excelColumns",values.excelColumns);
		}
		
		if (values.source) {
			$(inputDom).attr("source", values.source);
		}
		if (values.funKey) {
			$(inputDom).attr("funKey" , values.funKey);
		}
		
		var img = document.createElement("img");
		img.src = "massdata/icons/preview.png";
		img.style.width ="100%";
		domElement.appendChild(img);
	}
	
	selfFun.chooseService = function(){
		selfFun.sourceChange(document.getElementsByName("source")[0],true);
	}
	
	function onPanelAttrLoad(panelForm, control){
		var val = $("[name='source']").val();
		changeTrDisplayByType(val || "HIDEALL");
	}
	
	selfFun.sourceChange = function(obj,choose) {
		var control = Designer.instance.attrPanel.panel.control;
		_Clear_Mass_Data_Attrs(control);
		// 选择“--请选择---” 后无需处理业务
		if (!obj.value) {
			changeTrDisplayByType("HIDEALL");
			control.options.values.source_oldValue='';
			control.options.values.source='';
			return;
		}
		
		changeTrDisplayByType(obj.value);
		
		// 如果是excel导入，需要重新绘制
		if(obj.value == "EXCEL"){
			initExcelPanel();
			return ;
		}

		var source = relationSource.GetSourceByUUID(obj.value);
		
		// 如果扩展点中 paramsURL为空，数据源即业务
		var rtnVal = {
			"_source" : obj.value,
			"_key" : obj.value,
			"_keyName" : source.sourceName
		};
		
		if (source.paramsURL) {
		    new ModelDialog_Show(Com_Parameter.ContextPath+source.paramsURL,rtnVal,function(rtnVal){
		    	// 没有选择函数
				if(!rtnVal||!rtnVal._key||rtnVal._key=='undefined'){
					//新数据源没有选择业务函数时 回退选择源数据源
					$("#massdata_source").val(control.options.values.source_oldValue);
					return ;
				}
				
				control.options.values.source_oldValue=obj.value;
				// 设置业务名称
				document.getElementsByName("funName")[0].value = rtnVal._keyName;
				control.options.values.funName = rtnVal._keyName;
				control.options.values.funKey = rtnVal._key;
				control.options.values.source = obj.value;
				loadInputs(control, obj.value, rtnVal._key );
		    }).show();
		}
		//直接选择业务 
		else{
			if(choose){
				alert(Designer_Lang.massdata_notOptions);
				return;
			}
			control.options.values.source_oldValue=obj.value;
			// 设置业务名称
			document.getElementsByName("funName")[0].value = rtnVal._keyName;
			control.options.values.funName = rtnVal._keyName;
			control.options.values.funKey = rtnVal._key;
			control.options.values.source = obj.value;
			
			loadInputs(control, obj.value, rtnVal._key );
		}
	}
	
	// 画excel模板列定义
	function excelColumnsDraw(name, attr, value, form, attrs, values, control){
		var html = "";
		html += "<tr><td colspan='2'>";
		html += "<div id='massdata_excel_columns' style='background-color:#fff;'>";
		html += "<div class='columns-content'>";

		var val = value || "{}";
		var excelColumnsMapping = JSON.parse(val.replace(/quot;/g,"\""));
		var i = 0;
		for(var fid in excelColumnsMapping){
			var param = excelColumnsMapping[fid];
			param.fid = fid;
			html += buildExcelColumnHtml(param);
		}
		html += "</div>";
		// 增加行
		html += "<div class='columns-add' style='text-align:center;'>";
		html += "<div class='columns-addCol' onclick='Designer_Config.controls.massData.fun.addExcelColumn();'><span>" +
				Designer_Lang.massdata_addColDefine +"</span><img src='massdata/icons/add.gif' ></img></div>";
		html += "</div>";
		
		html += "</div>";
		
		html += "</td></tr>";
		return html;
	}
	
	// 增加一行列定义
	selfFun.addExcelColumn = function(){
		//显示按钮面板
		Relation_ShowButton();

		var column = {
				"title" : "",
				"type" : "String"
			}; 
		//随机产生一个唯一标识
		column.fid = "column_" + Designer.generateID();
		$("#massdata_excel_columns").find(".columns-content").append(buildExcelColumnHtml(column));
	}
	
	function buildExcelColumnHtml(column){
		var html = [];
		var uuid = column.fid;
		html.push("<div id='" + uuid + "' class='columns-content-item'>");
		
		// 列标题
		html.push(" <input type='text' placeholder='"+ Designer_Lang.massdata_colTitle +"' value='"+ column.title +"' name='" + uuid + "_title' " +
				"class='columns-title' />");
		// 类型
		var typeArr = [{text:Designer_Lang.massdata_string,value:"String"},{text:Designer_Lang.massdata_dateTime,value:"DateTime"}];
		typeArr.push({text:Designer_Lang.massdata_date,value:"Date"});
		typeArr.push({text:Designer_Lang.massdata_time,value:"Time"});
		html.push("<select name='"+ uuid +"_type' class='columns-type'>");
		for(var i = 0;i < typeArr.length;i++){
			html.push("<option value='" + typeArr[i].value + "'");
			if(typeArr[i].value == column.type){
				html.push(" selected ");
			}
			html.push(">" + typeArr[i].text + "</option>");
		}
		html.push("</select>");
		//Designer_Config.controls.massData.fun.deleteExcelColumn(this);
		// 图标
		html.push('<div class="columns-opera">');
		html.push('<img src="massdata/icons/delete.gif" onclick="Designer_Config.controls.massData.fun.deleteExcelColumn(this);"></img>');
		html.push('<img src="massdata/icons/up.gif" onclick="Designer_Config.controls.massData.fun.upExcelColumn(this);"></img>');
		html.push('<img src="massdata/icons/down.gif" onclick="Designer_Config.controls.massData.fun.downExcelColumn(this);"></img>');				
		html.push('</div>');
		
		html.push("</div>");
		
		return html.join("");
	}
	
	selfFun.deleteExcelColumn = function(dom){
		//显示按钮面板
		Relation_ShowButton();
		// 删除警告
		if(confirm(Designer_Lang.massdata_delColWarning)){
			$(dom).closest(".columns-content-item").remove();			
		}
	}
	
	selfFun.upExcelColumn = function(dom){
		//显示按钮面板
		Relation_ShowButton();
		var $div = $(dom).closest(".columns-content-item");
		$div.prev().before($div);
	}
	
	selfFun.downExcelColumn = function(dom){
		//显示按钮面板
		Relation_ShowButton();
		var $div = $(dom).closest(".columns-content-item");
		$div.next().after($div);
	}

	// 清空面板上面的属性值
	function _Clear_Mass_Data_Attrs(control){
		$('#massdata_inputs').html(Designer_Lang.relation_select_chooseSource);
		$('#massdata_outputs').html(Designer_Lang.commonNoOutputParams);
		$('#massdata_excel_columns').find(".columns-content").html("");
		// 清空输出
		control.options.values.outputParams = "";
		control.options.values.inputParams = "";
		control.options.values.excelColumns = "";

		// 清空业务
		document.getElementsByName("funName")[0].value = "";
		control.options.values.funName = "";
		control.options.values.funKey = "";
	}
	
	// 初始化excel导入的面板
	function initExcelPanel(){

	}
	
	// 根据类型更改面板行显示
	function changeTrDisplayByType(type){
		if(type == 'EXCEL'){
			Designer_Control_Attr_AcquireParentTr("funName").hide();
			$('#massdata_outputs').closest("tr").hide();
			$('#massdata_inputs').closest("tr").hide();
			$("#massdata_excel_columns").closest("tr").show();
		}else if(type == 'HIDEALL'){
			Designer_Control_Attr_AcquireParentTr("funName").hide();
			$('#massdata_outputs').closest("tr").hide();
			$('#massdata_inputs').closest("tr").hide();
			$("#massdata_excel_columns").closest("tr").hide();
		}else{
			Designer_Control_Attr_AcquireParentTr("funName").show();
			$('#massdata_outputs').closest("tr").show();
			$('#massdata_inputs').closest("tr").show();
			$("#massdata_excel_columns").closest("tr").hide();
		}
	}
	
	/**
	 * 画传入
	 * @param control
	 * @param source 选择的数据源，如TIB 或是其他
	 * @param key    函数key
	 * @return
	 */
	function loadInputs(control, source, key) {
		// loadTemplate 定义在relation.js里面的方法
		 loadTemplate(key,source,function(data){
				var insStr = "";
				if(data&&data.ins){
					control.options.values.inputParamsRequired="";
					for ( var i = 0; i < data.ins.length; i++) {
						var field = data.ins[i];
						insStr += buildInputElement(field) + "<br/>";
						if("1"==field._required){
							control.options.values.inputParamsRequired+=(field.uuId?field.uuId:field.fieldId)+",";
						}
					}
				}
				if(data&&data.searchs){
					control.options.values.outerSearchParams=JSON.stringify(data.outerSearchs).replace(/"/g,"quot;");
				}
				$("#massdata_inputs").html(insStr);
			
		 },true);
	}

	//构建每个传入参数项
	function buildInputElement(field) {
		var html = [];
		var paramName = field.fieldName ? field.fieldName : field.fieldId;
			paramName=paramName?paramName:field.uuId;
		html.push("<label>" + paramName + "</lable>");
		// 必填
		if(field._required=="1"){
			html.push("<span class='txtstrong'>*</span>");
		}
		html.push("<br/>");
		
		html.push("<input type='hidden' id='" + field.uuId + "_required' value='"
				+ field._required + "' />");
		html.push("<input type='hidden' id='" + field.uuId + "_formId' value='"
				+ field.fieldIdForm + "' />");
		html.push("<input id='" + field.uuId + "_formName' value='"
				+ field.fieldNameForm + "' onchange='Relation_Fiexed_InputParam_Change(this,\""
				+ field.uuId + "\");' style='width:80%;'/>");
		if (field.showSelect){
			html.push(" <a href='javascript:void(0)' onclick='Designer_Config.controls.massData.fun.chooseInputFieldDialog(this,\""
					+ field.uuId + "\");'>"+Designer_Lang.relation_select_choose+"</a>");
		}
		return html.join("");
	}
	
	// 构建传出参数项
	selfFun.buildOutputElement = function(obj,hiddenFlag){
		if(!Designer.instance.attrPanel.panel.control.options.values.funKey){
			alert(Designer_Lang.massdata_sourceRequire);
			return ;
		}
		//显示按钮面板
		Relation_ShowButton();

		var field={
				"fieldTitle" : "",
				"fieldName" : "",
				"fieldId" : "",
				"_required":false
			}; 
		//随机产生一个唯一标识
		field.fid="fm_" + Designer.generateID();
		var isFirst = false;
		//清空初始值                          
		if($("#massdata_outputs").html() == Designer_Lang.commonNoOutputParams){
			$("#massdata_outputs").html("");
			isFirst = true;
		}
		$("#massdata_outputs").append(buildOutputHtml(field,isFirst,hiddenFlag));                
	}
	
	// 删除输出项
	selfFun.deleteOutputItem = function(obj){
		//显示按钮面板
		Relation_ShowButton();
		var control = Designer.instance.attrPanel.panel.control;
		
		var fid=$(obj).parent().attr("id");
		
		control.options.values.outputParams = control.options.values.outputParams || "{}";
		var outputParamsJSON = JSON
				.parse(control.options.values.outputParams.replace(/quot;/g,"\""));
		if(outputParamsJSON[fid]){
			//删除掉 这个对象的映射值 
			delete outputParamsJSON[fid];
		}
		control.options.values.outputParams=JSON.stringify(
				outputParamsJSON).replace(/"/g,"quot;");
		$(obj).parent().remove();
		//清空到最后一条的时候需要 加上初始值
		if(!$("#massdata_outputs").html()){
			$("#massdata_outputs").html(Designer_Lang.commonNoOutputParams);
		}
	}
	
	// 画输出项
	function buildOutputHtml(field,isFirst,hiddenFlag) {
		var html = [];

		var fid=field.fid;
		html.push("<span id='"+fid+"' class='outputItem'>");
		//第一个元素不需要分隔符
		if(!isFirst){
			html.push("<hr />");
		}
		
		html.push(" <img src='relation_event/style/icons/delete.gif' onclick='Designer_Config.controls.massData.fun.deleteOutputItem(this);'" +
				" style='cursor:pointer;vertical-align:middle;'></img>");
		
		html.push(" <input type='text' value='"+ field.fieldTitle +"' name='" + fid + "_fieldTitle' " +
				"style='width:58px;margin-right:8px;vertical-align:middle;' placeholder='"+ Designer_Lang.massdata_colTitle +"'/>");
		
		var uuid = field.uuId ? field.uuId : field.fieldId;
		html.push("<input type='hidden' id='" + fid
				+ "_fieldId' value='" + uuid + "' />");
		html.push("<input id='" + fid + "_fieldName' placeholder='"+ Designer_Lang.commonTemplateField +"' value='"
				+ field.fieldName + "' readOnly=true  style='width:58px;vertical-align:middle;color:"+(hiddenFlag=='1'?'gray':'')+"'/>");
		html.push(" <img src='relation_event/style/icons/edit.gif' onclick='Designer_Config.controls.massData.fun.openTemplateFieldsDialog(this,\""
				+ fid + "\");' style='cursor:pointer;vertical-align:middle;'></img>");
		
		html.push("</span>");
		return html.join("");
	}
	
	// 画属性面板上的输入
	function inputAttrDraw(name, attr, value,
			form, attrs, values, control) {
		var val = value;

		html = "";

		if (values.funKey) {
			
			if (!val) {
				val = "{}";
			}
			var mapping = JSON.parse(val.replace(/quot;/g,"\""));
			 
			 loadTemplate(values.funKey,values.source,function(data){
				 
				 var insStr = "";
				if(data&&data.ins){
					values.inputParamsRequired="";
					for ( var i = 0; i < data.ins.length; i++) {
						var fieldTemp = data.ins[i];
						var field={};
						// 克隆一个对象，防止模板被修改
						$.extend(true,field, fieldTemp);
						if (mapping && mapping[field.uuId]) {
							field.fieldIdForm = mapping[field.uuId].fieldIdForm;
							field.fieldNameForm = mapping[field.uuId].fieldNameForm;
						}
						if("1"==field._required){
							values.inputParamsRequired+=(field.uuId?field.uuId:field.fieldId)+",";
						}
						insStr += buildInputElement(field) + "<br/>";
					}
				}
				html += "<div id='massdata_inputs'> " + insStr + "</div>";
				if(data&&data.searchs){
					values.outerSearchParams=JSON.stringify(data.outerSearchs).replace(/"/g,"quot;");
				}
			 });
		} else {
			html += "<div id='massdata_inputs'>"+Designer_Lang.relation_select_chooseSource+"</div>";
		}
		
		return Designer_AttrPanel.wrapTitle(name, attr, value, html);
	}
	
	// 画属性面板的输出参数
	function ouputAttrDraw(name, attr,
			value, form, attrs, values, control) {
		if (!control.options.values.funKey) {
			var html = "<div id='massdata_outputs'>"+Designer_Lang.commonNoOutputParams+"</div>";
			return Designer_AttrPanel.wrapTitle(name, attr, value, html);
		}
		var html = "<div id='massdata_outputs'>";

		var val = value ? value : "{}";
		var outputParamsMapping = JSON.parse(val.replace(/quot;/g,"\""));
		var i = 0;
		for(var fid in outputParamsMapping){
			var param = outputParamsMapping[fid];
			param.fid=fid;
			html += buildOutputHtml(param,i==0,param.hiddenFlag?param.hiddenFlag:"0");
			i++;
		}
		html += "</div>";
		return Designer_AttrPanel.wrapTitle(name, attr, value, html);
	}
	
	function _Designer_Control_MassData_DrawXML() {
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

	// 打开当前模板字段的弹窗
	selfFun.openTemplateFieldsDialog = function(dom, fid) {

		var control = Designer.instance.attrPanel.panel.control;
	    var values=control.options.values;
		if (!control.options.values.funKey) {
			alert(Designer_Lang.relation_select_chooseSource);
			return;
		}	
		loadTemplate(values.funKey,values.source,function(data,varInfo){
			
			Open_Relation_Fields_Tree(document.getElementById(fid + "_fieldId"),
					document.getElementById(fid + "_fieldName"),
					varInfo,function(rtn){
				if(!rtn){
					return;
				}
				Relation_ShowButton();
				var control = Designer.instance.attrPanel.panel.control;
				control.options.values.outputParams = control.options.values.outputParams || "{}";
				var outputParamsJSON = JSON
						.parse(control.options.values.outputParams.replace(/quot;/g,"\""));
				if(!outputParamsJSON[fid]){
					outputParamsJSON[fid]={};
				}
				outputParamsJSON[fid].fieldId = rtn.data[0].id;
				outputParamsJSON[fid].fieldName = rtn.data[0].name;
				outputParamsJSON[fid].fieldTitle = rtn.data[0].name;
				// 不回填数据
				//$("input[name='"+ fid +"_fieldTitle']").val(rtn.data[0].name);
				
				if(data && data.outs){
					for ( var i = 0; i < data.outs.length; i++) {
						var field = data.outs[i];
						var uuid = field.uuId ? field.uuId : field.fieldId;
						if(uuid==rtn.data[0].id){
							outputParamsJSON[fid].canSearch=field.canSearch;
							break;
						}
					}
				}
				control.options.values.outputParams = JSON.stringify(
						outputParamsJSON).replace(/"/g,"quot;");
			});
		 });
	}
	
	// 选择传入参数
	selfFun.chooseInputFieldDialog = function(obj, uuid, action) {
		var idField, nameField;
		idField = document.getElementById(uuid + "_formId");
		nameField = document.getElementById(uuid + "_formName");
		var _requiredValue="";
		if(document.getElementById(uuid + "_required")){
			_requiredValue=document.getElementById(uuid + "_required").value;			
		}
		
		var varInfo = filterControls(Designer.instance.getObj(false));
		
		RelatoinFormFieldChoose(idField,nameField, function(rtn){
			
			Relation_ShowButton();
			
			var control = Designer.instance.attrPanel.panel.control;

			control.options.values.inputParams = control.options.values.inputParams || "{}";
			var inputParamsJSON = JSON
					.parse(control.options.values.inputParams.replace(/quot;/g,"\""));
			if(rtn && rtn.data && rtn.data[0].id){
				var t = {};
				t.fieldIdForm = rtn.data[0].id;
				t.fieldNameForm = rtn.data[0].name;
				t._required=_requiredValue;
				inputParamsJSON[uuid] = t;
				control.options.values.inputParams = JSON.stringify(
						inputParamsJSON).replace(/"/g,"quot;");
			}
			else{
				//清空选择时，需要取消映射
				delete inputParamsJSON[uuid];
				
				control.options.values.inputParams = JSON.stringify(
						inputParamsJSON).replace(/"/g,"quot;");
			}
		 } ,  varInfo);
	};
	
	// 过滤控件
	function filterControls(varInfos){
		console.log(varInfos);
		var temp = [];
		for(var i = 0;i < varInfos.length;i++){
			var controlInfo = varInfos[i];
			// 过滤明细表
			if(Designer.IsDetailsTableControlObj(controlInfo)){
				continue;
			}
			// 过滤明细表内的组件
			if(controlInfo.isTemplateRow === true){
				continue;
			}
			temp.push(controlInfo);
		}
		return temp;
	}
	
	// 把面板上面的属性值设置到对象里面，按照设计，理论上，面板上的属性值应该在确定的时候，才设置到对象里面，然而由于历史代码（参考relation_event）的原因，不做深究
	function setPanelValues(){
		var values = this.options.values;
		// 设置传出的title
		var out = values.outputParams || "{}";
		out = JSON.parse(out.replace(/quot;/g,"\""));
		for(var key in out){
			out[key].fieldTitle = $("input[name='"+ key +"_fieldTitle']").val();
		}
		values.outputParams = JSON.stringify(out).replace(/"/g,"quot;");
		// 设置excel字段
		var items = $("#massdata_excel_columns").find(".columns-content .columns-content-item");
		var excelColumns = {};
		for(var i = 0;i < items.length;i++){
			var item = items[i];
			var uuid = $(item).attr("id");
			excelColumns[uuid] = {};
			excelColumns[uuid]["title"] = $(item).find("[name='"+ uuid +"_title']").val();
			excelColumns[uuid]["type"] = $(item).find("[name='"+ uuid +"_type']").val();
		}
		values.excelColumns = JSON.stringify(excelColumns).replace(/"/g,"quot;");
		// 控件draw在onAttrSuccess执行之前，故此处需要重新设置outputParams
		$(this.options.domElement).find("input").attr("outputParams",values.outputParams);
		$(this.options.domElement).find("input").attr("excelColumns",values.excelColumns);
	}
	
	// 校验各字段必填情况
	function massDataValidation(elem, name, attr, value, values){
		if(value == ""){
			alert(Designer_Lang.massdata_sourceRequire);
			return false;
		}
		if(value === "EXCEL"){
			// 校验列标题是否为空
			var columns = $("input[name^='column_']");
			if(columns.length == 0){
				alert(Designer_Lang.massdata_colDefineNotEmpty);
				return false;
			}
			for(var i = 0;i < columns.length;i++){
				var col = columns[i];
				if(col.value == null || col.value == ''){
					alert(Designer_Lang.GetMessage(Designer_Lang.controlAttrValRequired,Designer_Lang.massdata_colTitle));
					col.focus();
					return false;
				}
			}
		}else{
			// 校验输出
			var items = $(".outputItem");
			if(items.length == 0){
				alert(Designer_Lang.massdata_outputNotEmpty);
				return false;
			}
			for(var i = 0;i < items.length;i++){
				var fieldTitle = $(items[i]).find("input[name$='_fieldTitle']")[0];
				if(fieldTitle.value == null || fieldTitle.value == ''){
					alert(Designer_Lang.GetMessage(Designer_Lang.controlAttrValRequired,Designer_Lang.massdata_colTitle));
					fieldTitle.focus();
					return false;
				}
			}
		}
		
		return true;
	}
})(window);

