/**
 * 打印机制从业务模块分离
 * 对打印机制的js代码进行覆盖和扩展
 */
function XForm_Util_UnitArray(array, sysArray, extArray) {
	array = array.concat(sysArray);
	if (extArray != null) {
		array = array.concat(extArray);
	}
	return array;
}

function XForm_getXFormDesignerObj_modelingApp(){
	var obj = [];

	// 1 加载主文档的字典
	var sysObj = _XForm_GetSysDictObj("com.landray.kmss.sys.modeling.main.model.ModelingAppModelMain");
	var extObj = null;

	// 2加载扩展表单的字典
	var xformId = $("#_xFormTemplateId").val();
	extObj = _XForm_GetTempExtDictObj(xformId);

	return XForm_Util_UnitArray(obj, sysObj, extObj);
}

// 查询modelName的属性信息
function _XForm_GetSysDictObj(modelName){
	return Formula_GetVarInfoByModelName(modelName);
}

// 查找自定义表单的数据字典
function _XForm_GetTempExtDictObj(tempId) {
	return new KMSSData().AddBeanData("sysFormDictVarTree&tempType=template&tempId="+tempId).GetHashMapArray();
}
function _hideAndClick(){
	$("[name='_sysPrintTemplateForm.fdPrintTemplate']").click();
	$("[name='sysPrintTemplateForm.fdPrintTemplate']").val("true");
}
function _appendDom(){
}
function _changeStyle(){
	$("#sys_print_designer_draw").css({
		"overflow":"hidden!important"
	})
}
//默认模版，自定义模版切换
seajs.use(['lui/jquery', 'lui/topic'], function ($, topic) {
	topic.subscribe("modeling.print.changeMode", function (obj) {
		var value = obj.value;
		if (value == "custom") {
			sysPrint_OnLoad();

		}
	});
});
//查找自定义表单的数据字典
function _XForm_GetTempExtDictObj(tempId) {
	return new KMSSData().AddBeanData("sysFormDictVarTree&tempType=template&tempId="+tempId).GetHashMapArray();
}

//标签切换事件
function sysPrint_OnLoad(){
	//根据是否支持流程屏蔽流程
	if(enableFlow == 'false' && sysPrintDesignerConfig && sysPrintDesignerConfig.buttons){
		var control = sysPrintDesignerConfig.buttons.control || [];
		var newControl = [];
		for(var i=0; i<control.length; i++){
			if(control[i] != 'proccess'){
				newControl.push(control[i]);
			}
		}
		sysPrintDesignerConfig.buttons.control = newControl;
	}
	initDefineTemplate();
	if(IS_PRINT_SUB_TEMPLATE){
		init();
		//多表单模式
		setTimeout(function (){
			if(IS_PRINT_SUB_TEMPLATE || PRINT_OPER_TYPE !='templateHistory' && sysPrintButtons.getXFormMode("${JsParam.fdKey}")==PRINT_XFORM_TEMPLATE_SUBFORM){
				var height = $("#SubForm_Print_table").outerHeight(false)-5;
				$("#DIV_SubForm_Print").css("height",height);
				$("#SubPrintDiv").css("height",height*0.45);
				$("#SubPrintControlsDiv").css("height",height*0.55);
				//清空控件div中信息
				$("#SubPrintControlsDiv").html("");
			}
		},0);
	}
	setTimeout(function(){
		init();
		//多表单模式
		setTimeout(function (){
			if(IS_PRINT_SUB_TEMPLATE || PRINT_OPER_TYPE !='templateHistory' && sysPrintButtons.getXFormMode(printKey)==PRINT_XFORM_TEMPLATE_SUBFORM){
				var height = $("#SubForm_Print_table").outerHeight(false)-5;
				$("#DIV_SubForm_Print").css("height",height);
				$("#SubPrintDiv").css("height",height*0.45);
				$("#SubPrintControlsDiv").css("height",height*0.55);
				//清空控件div中信息
				$("#SubPrintControlsDiv").html("");
				if(needLoad_subprint){
					SubPrint_Load();
				}
			}
		},0);
	}, 500);
}

function _setPrintXFromHtmExt(){
	var xForm = $('#isXForm').val();
	if(xForm=='false'){
		return false;
	}

	var key = printKey;
	var fdModeValue = sysPrintButtons.getXFormMode(key);
	
	if(fdModeValue=='3'){
		//默认编辑
		var name = "sysFormTemplateForms." + key+ ".fdDesignerHtml";
		var html = document.getElementsByName(name)[0].value;
		$('#_tmp_xform_html').html(html);
		return true;
	}else{
		return false;
	}
}

function SubForm_filterXml(str){
	if(!str){
		return "";
	}
	//将xml中连续两个以上的空格 替换为 一个
	str=str.replace(/\s{2,}/g," ");
	//替换掉换行
	str=str.replace(/[\r\n]+/g," ");
	//将xml中label 属性去除。（数据字典中label属性的改变不列如入默认作为新模版的条件）
	str=str.replace(/\s+label=\s*[\S]+\s*/gi," ");
	return str;
}

function SubForm_LoadInfo_Mouseout(self){
	$(self).attr("class","subform_panel_normal");
}

function SubForm_LoadInfo_Mouseover(self){
	$(self).attr("class","subform_panel_mouseover");
}

Com_AddEventListener(window,'load',function(){
	//隐藏一些不必要的内容和点击
	_hideAndClick();
	//添加一些dom
	_appendDom();
	//修改一些样式
	_changeStyle();
	setTimeout(function(){
		var bodyHeight = $(parent.document).height();
        $("body",parent.document).find('#cfg_iframe').animate({
        	height : bodyHeight
        },"fast");
	     $("body").css({
	    	 "overflow":'hidden'
	     })
	}, 300);
	//按钮js覆盖
	if(sysPrintButtons){
		sysPrintButtons.importXFormTemp = function(designer,isTip){
			if(!sysPrintButtons.isXFormSupport()){
				alert(DesignerPrint_Lang.moduleNoXFormAlert);
				return;
			}
			var key = designer.fdKey;
			var fdModeValue = sysPrintButtons.getXFormMode(key);
			if(fdModeValue=='3' || fdModeValue=='4'){
				if(isTip){
					if(!confirm(DesignerPrint_Lang.overrideCurrentTemptByImportAlert)){
						return;
					}
				}
				var method = $("#_method").val() == 'print' ? 'edit' : 'add';
				var html = "";
				var name = "sysFormTemplateForms." + key+ ".fdDesignerHtml";
				html = document.getElementsByName(name)[0].value;
				if(method=='add' || method=='edit' || method=='clone'){
					if(html){
						$('#_tmp_xform_html').html(html);
						designer.builder.parseCtrl($('#_tmp_xform_html')[0]);
						designer.builder.setHTML($('#_tmp_xform_html').html());
					}
					//增加对撤销功能的支持
					if(typeof (sysPrintUndoSupport)  != 'undefined'){
						sysPrintUndoSupport.saveOperation();
					}
				}
			}else if(fdModeValue=='-1'){
				//控件解释转换
				designer.builder.parseCtrl($('#_tmp_history_xform_html')[0]);
				designer.builder.setHTML($('#_tmp_history_xform_html').html());
				//增加对撤销功能的支持
				if(typeof (sysPrintUndoSupport)  != 'undefined'){
					sysPrintUndoSupport.saveOperation();
				}
			}else{
				alert(DesignerPrint_Lang.onlyXFormImportAlert);
			}
		}
	}
	//打印机制公共的内容覆盖
	if(sysPrintCommon){
		//获取文档数据字典 xformTemplateId(表单模板id,流程拷贝时必须传该属性参数)
		sysPrintCommon.getDocDict = function(fdKey,modelName,_xformCloneTemplateId){
			//文档数据字典
			var _cmd = "window._XForm_GetSysDictObj ? _XForm_GetSysDictObj('"+ modelName +"'):Formula_GetVarInfoByModelName('" + modelName + "')";

			var sysObj = eval(_cmd) || [];
			_xformCloneTemplateId = _xformCloneTemplateId ? _xformCloneTemplateId:"";
			var xFormMethod = "window.XForm_getXFormDesignerObj_" + fdKey;
			var tempId = document.getElementById("_xFormTemplateId").value;
			var cmd = xFormMethod + "? " + xFormMethod + "('" + _xformCloneTemplateId + "'):_XForm_GetTempExtDictObj('" + tempId + "')";
			var baseObjs = eval(cmd) || [];
			for(var i=sysObj.length-1; i>0; i--){
				var isExit = false;
				for(var j=0; j<baseObjs.length; j++){
					if(baseObjs[j].name == sysObj[i].name){
						isExit = true;
						break;
					}
				}
				if(!isExit){
					baseObjs.unshift(sysObj[i]);
				}
			}
			//baseObjs = baseObjs.concat(sysObj);
			if(PRINT_OPER_TYPE=='templateHistory'){//历史模板编辑场景
				//获取表单某个模板对应的数据字典
				var fileNameValue = $('input[name="fdFormFileNames"]').val();
				var fileNames = fileNameValue.split(';');
				for(var i=0;i<fileNames.length;i++){
					if(!fileNames[i]){
						continue;
					}
					var tmpbaseObjs = new KMSSData().AddBeanData("sysFormDictVarTree&tempType=file&fileName="+fileNames[i]).GetHashMapArray();
					baseObjs = baseObjs.concat(tmpbaseObjs);
				}
				
			}
			//自定义属性
			var data = new Array();
			data = new KMSSData().AddBeanData("sysPrintPropertyDictService&categoryId=&modelName="+modelName).GetHashMapArray();
			baseObjs = baseObjs.concat(data);
			if(!baseObjs||baseObjs.length==0){
				return baseObjs;
			}
			//文档属性、自定义表单数据字典区分标识
			sysPrintCommon._setXFormDictFlag(sysObj,baseObjs);
			return baseObjs;
		}
	}
	
	//替换多表单的代码
	if(window.SubPrint_Load){
		window.SubPrint_LoadInfo_Click = function(self,otherData,controls){
			var id = $(self).attr("id").replace("subprint_","");
			var name = "";
			for(var i=0;i<otherData.length;i++){
				if(id==otherData[i].id){
					name = otherData[i].label;
					break;
				}
			}
			if(controls){
				for(var key in controls){
					var state = false;
					for(var p = 0;p<controls[key].length;p++){
						if(id==controls[key][p].options.values.id){
							$("#print_control_load").html(controls[key][p].options.domElement.outerHTML);
							state = true;
							break;
						}
					}
					if(state){
						break;
					}
				}
			}
			//var baseObjs = sysPrintDesigner.instance.builder._getDatas();
			sysPrintDesigner.instance.builder.parseCtrl($('#print_control_load')[0]);
			if(sysPrintDesigner.instance.builder.$selectDomArr.length>0){
				var selectedDom = sysPrintDesigner.instance.builder.$selectDomArr[0][0];
				if(selectedDom && selectedDom.tagName && selectedDom.tagName == 'TD' && $(selectedDom).hasClass('table_select')){
					if(!controls){
						for(var key in window.SubPrint_SubPrintControls){
							var state = false;
							var subFormControls = window.SubPrint_SubPrintControls[key];
							for(var i=0; i<subFormControls.length; i++){
								if(id == subFormControls[i].$domElement[0].id){
									$("#print_control_load").html(subFormControls[i].$domElement[0].outerHTML);
									state = true;
									break;
								}
							}
							if(state){
								break;
							}
						}
					}
					var next = $(selectedDom).next();
					if($(selectedDom).hasClass('td_normal_title') && next.length>0){
						//单元格为标题栏时，附带插入名称
						var label=new sysPrintLabelControl(sysPrintDesigner.instance.builder,'label',{text:name});
						label.draw();
						//数据
						if(next.html()=="&nbsp;"){
							next.html($("#print_control_load").html());
						}else{
							next.append($("#print_control_load").html());
						}
						sysPrintDesigner.instance.builder.parse(null, next[0], true);
					}else{
						if($(selectedDom).html()=="&nbsp;"){
							$(selectedDom).html($("#print_control_load").html());
						}else{
							$(selectedDom).append($("#print_control_load").html());
						}
						sysPrintDesigner.instance.builder.parse(null, selectedDom);
					}
					$(self).remove();
				}else{
					alert(Data_GetResourceString("sys-print:sysPrint.load_msg"));
				}
			}else{
				alert(Data_GetResourceString("sys-print:sysPrint.load_msg"));
			}
		};
		window.SubPrint_Load_Info = function(data,win){
			//当前模板没有的控件
			var otherData = [];
			//当前模板中含有的控件转换的dom结构
			var nodes = $('#sys_print_designer_draw').find('[printcontrol="true"]');
			//所有控件（去掉重复）
			var allData = [];
			for(var i=0;i<data.length;i++){
				var isRepeat = false;
				for(var j=0;j<allData.length;j++){
					if(data[i].id==allData[j].id){
						isRepeat = true;
						break;
					}
				}
				if(!isRepeat){
					allData.push(data[i]);
				}
			}
			for(var k=0;k<allData.length;k++){
				var isExit = false;
				for(var l=0;l<nodes.length;l++){
					if($(nodes[l]).attr("id")==allData[k].id){
						isExit = true;
						break;
					}
				}
				if(!isExit){
					otherData.push(allData[k])
				}
			}
			var SubPrintControlsDiv = $("#SubPrintControlsDiv");
			//清空控件div中信息
			SubPrintControlsDiv.html("");
			if(otherData.length>0){
				$("#SubPrintLoadMsg").show();
			}else{
				$("#SubPrintLoadMsg").hide();
			}
			//所有控件对象
			if(win){
				for(var m = 0;m<otherData.length;m++){
					var mycontrols = win.Designer.instance.subFormControls;
					for(var key in mycontrols){
						var state = false;
						for(var p = 0;p<mycontrols[key].length;p++){
							if(otherData[m].id==mycontrols[key][p].options.values.id){
								SubPrintControlsDiv.append('<div class="subform_panel_normal" onmouseover="SubForm_LoadInfo_Mouseover(this);" onmouseout="SubForm_LoadInfo_Mouseout(this);" id="subprint_'+otherData[m].id+'">'+mycontrols[key][p].options.values.label+'('+win.Designer_Config.controls[mycontrols[key][p].type].info.name+')</div>');
								$("#subprint_"+otherData[m].id).click(function(){
									SubPrint_LoadInfo_Click(this,otherData,mycontrols);
								});
								state = true;
								break;
							}
						}
						if(state){
							break;
						}
					}
				}
			}else{
				//解析子表单的控件信息，保存下来，提供给后续多表单操作使用，注意，或许多表单操作就不需要再进一步对内容进行解析了
				SubPrint_SubPrintControlsInit();
				var baseObjs = sysPrintDesigner.instance.builder._getDatas();
				for(var m = 0;m<otherData.length;m++){
					for(var i=0; i<baseObjs.length; i++){
						var baseObj = baseObjs[i];
						if(otherData[m].id==baseObj.name){
							var controlType = baseObj.controlType || baseObj.businessType;
							if(!controlType){
								continue;
							}
							SubPrintControlsDiv.append('<div class="subform_panel_normal" onmouseover="SubForm_LoadInfo_Mouseover(this);" onmouseout="SubForm_LoadInfo_Mouseout(this);" id="subprint_'+otherData[m].id+'">'+baseObj.label+'('+sysPrintDesignerConfig.controls[controlType].name+')</div>');
							$("#subprint_"+otherData[m].id).click(function(){
								SubPrint_LoadInfo_Click(this,otherData,null);
							});
							break;
						}
					}
				}
			}
		};
		window.SubPrint_SubPrintControlsInit = function(){
			if(window.SubPrint_SubPrintControls && window.SubPrint_SubPrintControls.length > 0){
				return;
			}
			window.SubPrint_SubPrintControls={};
			$("#TABLE_DocList_SubForm").find("tr").each(function(){
				var fdId = $(this).attr("id");
				window.SubPrint_SubPrintControls[fdId] = [];
				var fdPrintDesignerHtml = $(this).find("input[name$='fdPrintDesignerHtml']").val();
				$("#printContainer").html(fdPrintDesignerHtml);
				sysPrintDesigner.instance.builder.parseCtrl($("#printContainer")[0]);
				var action = function(control){
					window.SubPrint_SubPrintControls[fdId].push(control);
				}
				sysPrintDesigner.instance.builder.parse(null,$("#printContainer")[0],null,action);
				
				//清空一些内容
				$("#printContainer").html("");
			});
		};
		window.SubPrint_Load = function(){
			if(IS_PRINT_SUB_TEMPLATE){
				//编辑历史打印模板版本
				SubPrint_History_Load();
				return;
			}
			//先保存当前选中的表单信息,防止切换时未保存操作报错
			var name = "sysFormTemplateForms." + printKey + ".fdDesignerHtml";
			var fdDesignerHtml = document.getElementsByName(name)[0].value;
			name = "sysFormTemplateForms." + printKey + ".fdMetadataXml";
			var fdMetadataXml = document.getElementsByName(name)[0].value;
			
			var fdId = [];
			var fdXml = [];
			$("#TABLE_DocList_SubForm").find("tr").each(function(){
				fdId.push($(this).attr("id"));
				var myfdMetadataXml = $(this).find("input[name$='fdMetadataXml']");
				fdXml.push(encodeURIComponent(myfdMetadataXml.val()));
			});
			var url = Com_Parameter.ContextPath + "sys/xform/sys_form_template/sysFormTemplate.do?method=parseXML";
			var data = {"fdId":fdId.join("&&"),"fdMetadataXml":fdXml.join("&&")};
			$.ajax({
				type : "POST",
				data : data,
				url : url,
				success : function(json){
					if(json){
						SubPrint_Load_Info(json,null);
					}
				},
				dataType: 'json'
			});
		};
		window.SubPrint_Copy_Ok = function(){
			var isCopy = $("input[name='subPrint_copy']:checked").val();
			if(!isCopy){
				//iframe域问题，如果取不到，从顶层取
				var topDoc = window.top.document;
				isCopy = $(topDoc).find("input[name='subPrint_copy']:checked").val();
			}
			if(SubPring_CopyDom != null){
				if(isCopy=='copy'){
					addRow_print(SubPring_CopyDom,true);
				}else{
					var tr = $("#TABLE_DocList_Print").find("tr[ischecked='true']");
					var mytr = $(SubPring_CopyDom).parents("tr[ischecked]");
					if(tr[0]!=mytr[0]){
						var newHtml = mytr.find("input[name$='fdTmpXml']").val();
						sysPrintDesigner.instance.builder.setHTML(newHtml);
						if(needLoad_subprint){
							SubPrint_Load();
						}
					}
				}
			}
		}
	}
	//属性字段覆盖
	if(sysPrintDesignerControl){
		sysPrintDesignerControl.extend({
			_setPrintXFromHtm:_setPrintXFromHtmExt,
		})
	}
	
	//添加打印机制配置
	sysPrint_AddConfig();
	
	//初始化
	sysPrint_OnLoad();
});

/**
 * 控件信息，如果打印机制有增加控件，需要在这里也补充
 */
function sysPrint_AddConfig(){
	sysPrintDesignerConfig.controls={
		"new_address":{
			name: Designer_Lang.controlNew_AddressInfoName,
			preview: "address.bmp"
		},
		"calculation":{
			name: Designer_Lang.controlCalculation_info_name
		},
		"chinaValue":{
			name: Designer_Lang.controlChinaValue_info_controlName // 控件描述
		},
		"circulationOpinionShow":{
			name:Designer_Lang.circulationOpinionShow_name,
			preview: "mutiTab.png"
		},
		"dateFormat":{
			name: Designer_Lang.controlDateFormat_info_controlName // 控件描述
		},
		"detailSummary":{
	        name: Designer_Lang.detailSummary_title
	    },
		"fieldlaylout":{
			name : Designer_Lang.fieldLayout
		},
		"formula_calculation":{
			name: Designer_Lang.controlFormulaCalculation_info_name
		},
		"fragmentSet":{
			name: Designer_Lang.controlFragmentSet_info_name
		},
		"fSelect":{
			name: Designer_Lang.controlFSelectInfoName,
			preview: "select.bmp"
		},
		"massData": {
			name : Designer_Lang.massdata
		},
		"prompt":{
			name: Designer_Lang.controlPromptInfoName
		},
		"relationCheckbox":{
			name : Designer_Lang.relation_checkbox_name
		},
		"relationChoose":{
			name : Designer_Lang.relation_choose_name
		},
		"relationEvent":{
			name : Designer_Lang.relation_event_name
		},
		"relationRadio": {
			name : Designer_Lang.relation_radio_name
		},
		"relationRule":{
			name : Designer_Lang.relation_rule_name
		},
		"relationSelect":{
			name : Designer_Lang.relation_select_name
		},
		"stageDiagram":{
			name : Designer_Lang.stage_diagram_name
		},
		"uploadTemplateAttachment":{
			name: Designer_Lang.controlUploadTemplateAttachment_info_name
		},
		"voteNode":{
			name: Designer_Lang.controlVoteNodeInfoName
		},
		"approvalnode":{
			name: Designer_Lang.paperSignature
		},
		"attachment":{
			name: Designer_Lang.controlAttachInfoName
		},
		"auditNote":{
			name:Designer_Lang.auditNote,
			preview: "mutiTab.png"
		},
		"auditShow":{
			name:Designer_Lang.auditshow_name,
			preview: "mutiTab.png"
		},
		"brcontrol":{
			name: Designer_Lang.controlBrcontrol_info_name
		},
		"composite":{
			name : Designer_Lang.composite
		},
		"textLabel": {
			name: Designer_Lang.controlTextLabelInfoName
		},
		"linkLabel":{
			name: Designer_Lang.controlLinkLabelInfoName
		},
		"detailsTable":{
			name: Designer_Lang.controlDetailsTable_info_name,
			td: Designer_Lang.controlDetailsTable_info_td
		},
		"divcontrol": {
			name: Designer_Lang.controlDivcontrol_info_name
		},
		"docimg":{
			name: Designer_Lang.controlDocImg_info_name
		},
		"hidden":{
			name: Designer_Lang.controlHiddenInfoName
		},
		"jsp":{
			name: Designer_Lang.controlJspInfoName
		},
		"newAuditNote":{
			name:Designer_Lang.newAuditNote,
			preview: "mutiTab.png"
		},
		"qrCode":{
			name: Designer_Lang.controlQRCodeInfoName
		},
		"relevance":{
			name: Designer_Lang.relevance
		},
		"right":{
			name: Designer_Lang.controlRightInfoName
		},
		"mutiTab":{
			name:Designer_Lang.controlMutiTab_name,
			preview: "mutiTab.png"
		},
		"uploadimg":{
			name: Designer_Lang.controlUploadImg_info_name
		},
		"validatorControl":{
			name: Designer_Lang.controlValidatorControl_info_name
		},
		"inputText": {
			name: Designer_Lang.controlInputTextInfoName,
			preview: "input.bmp"
		},
		"textarea":{
			name: Designer_Lang.controlTextareaInfoName,
			preview: "textarea.bmp"
		},
		"inputCheckbox":{
			name: Designer_Lang.controlInputCheckboxInfoName,
			preview: "checkbox.bmp"
		},
		"inputRadio":{
			name: Designer_Lang.controlInputRadioInfoName,
			preview: "radio.bmp"
		},
		"select":{
			name: Designer_Lang.controlSelectInfoName,
			preview: "select.bmp"
		},
		"rtf": {
			name: Designer_Lang.controlRtfInfoName,
			preview: "rtf.bmp"
		},
		"address":{
			name: Designer_Lang.controlAddressInfoName,
			preview: "address.bmp"
		},
		"datetime":{
			name: Designer_Lang.controlDatetimeInfoName,
			preview: "date.bmp"
		},
		"standardTable":{
			name: Designer_Lang.controlStandardTableInfoName,
			td: Designer_Lang.controlStandardTableInfoTd
		},
		"placeholder":{
			name: Designer_Lang.placeholder,
		}
	}
}