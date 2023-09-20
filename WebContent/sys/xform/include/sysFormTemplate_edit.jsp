<%@page import="com.landray.kmss.sys.xform.util.LangUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.xform.XFormConstant"%>
<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCloudUtil"%>
<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsWebOfficeUtil,com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCenterUtil"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%@ page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsoaassistUtil" %>
<c:set var="wpsoaassistEmbed" value="<%=SysAttWpsoaassistUtil.isWPSOAassistEmbed()%>"/>
<c:set var="sysFormTemplateForm" value="${requestScope[param.formName]}" />
<c:set var="xFormTemplateForm" value="${sysFormTemplateForm.sysFormTemplateForms[param.fdKey]}" />
<c:set var="sysFormTemplateFormPrefix" value="sysFormTemplateForms.${param.fdKey}." />
<c:set var="entityName" value="${param.fdMainModelName}_${sysFormTemplateForm.fdId}" />


<style>
	.removeSelectAppearance {
		appearance:none;
		-moz-appearance:none;
		-webkit-appearance:none;
		border:0px!important;
		width: 85px;
	}
	select.removeSelectAppearance::-ms-expand { display: none; }
</style>

<script>
Com_IncludeFile("dialog.js");
Com_Parameter.event["confirm"][Com_Parameter.event["confirm"].length] = XForm_ConfirmFormChangedEvent;

window.XForm_SupportFieldForRelationEvent = '${param.supportField}';

window.XForm_Param_MainModelName = '${param.fdMainModelName}';

window.XForm_Param_default_html = '';

//确认表单被改动时是否存成新版本 ，前面四个参数是confirm里面传进来的
function XForm_ConfirmFormChangedEvent(formObj, method, clearParameter, moreOptions, isNotSupportModalDailog , callback){
	var fdModeObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdMode")[0];
	var fdModelValue = 0;
	if(fdModeObj && fdModeObj != null){
		fdModelValue = fdModeObj.value;
	}

	if((fdModelValue == "<%=XFormConstant.TEMPLATE_DEFINE%>") || (fdModelValue == "<%=XFormConstant.TEMPLATE_SUBFORM%>")){
		return XForm_ConfirmFormChangedFun(isNotSupportModalDailog , callback);
	}else if(fdModelValue != "<%=XFormConstant.TEMPLATE_DEFINE%>"){
		//即使编辑的是富文本模式或者引用其他模板，表单的数据fdDesignerHtml也需要清空或者编码，不然有可能编码校验的时候不通过，因为提交的时候fdDesignerHtml也需要被校验 by朱国荣 2016-7-12
		var fdDesignerHtmlObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdDesignerHtml")[0];
		if(fdDesignerHtmlObj && fdDesignerHtmlObj.value != ''){
			fdDesignerHtmlObj.value = "";
		}	
	}
	// 非自定义表单的，调用回调
	if(isNotSupportModalDailog && callback){
		callback();
	}
	return true;
}

<%-- 0 引用方式； 1 富文本模式； 2 引用其他类型；3 已定义表单；4 自设计 --%>
function Form_ChgDisplay(key, value){
	var $mobileTr = $("#XForm_" + key + "_CustomTemplateMobileRow");
	if((value == "<%=XFormConstant.TEMPLATE_DEFINE%>") || (value == "<%=XFormConstant.TEMPLATE_SUBFORM%>")){  // 只有自定义表单需要多语言页签
		if(window.currentDisplay == "none"){
			Doc_ShowLabelById("Label_Tabel","tr_uu_lang");
		}
		$mobileTr.show();
	}else{
		Doc_HideLabelById("Label_Tabel","tr_uu_lang");
		window.currentDisplay = "none";
		$mobileTr.hide();
	}
	var tbObj = document.getElementById("TB_FormTemplate_"+key);
	var loadLink = document.getElementById("A_FormTemplate_"+key);
	var divSubForm = document.getElementById("DIV_SubForm_"+key);
	if((value == "<%=XFormConstant.TEMPLATE_DEFINE%>") || (value == "<%=XFormConstant.TEMPLATE_SUBFORM%>")){
		document.getElementById("Form_OtherTemplate_"+key).style.display = "none";
		loadLink.style.display = "";
		tbObj.rows[1].style.display = "none";
		tbObj.rows[2].style.display = "";
		XForm_DisplayFormRowSet();
		if ($) {
			$(tbObj).find("tr:visible *[onresize]").each(function(){
				var funStr = this.getAttribute("onresize");
				if(funStr!=null && funStr!=""){
					var tmpFunc = new Function(funStr);
					tmpFunc.call(this);
				}
			});
		}
		if(value == "<%=XFormConstant.TEMPLATE_SUBFORM%>"){
			loadLink.style.display = "none";
			$("#Subform_operation").attr("src","${KMSS_Parameter_ContextPath}sys/xform/base/resource/icon/varrowleft.gif");
			$("#SubForm_main_tr").show();
			$("#SubForm_operation_tr").show();
			$("span[name='subFormSpan']").show();
			$("span[name='subFormSpan']").text("<kmss:message key='sys-xform:sysSubFormTemplate.change_msg'/>");
		}else{
			$("#SubForm_main_tr").hide();
			$("#SubForm_operation_tr").hide();
			$("span[name='subFormSpan']").hide();
		}
		$("#FormTemplate_" + key + "_Layout").css("display","inline-block");
	}else{
		// 引用其他模板的时候，把模板选择和表单的内容隐藏 start
		document.getElementById("Form_OtherTemplate_"+key).style.display = (value=="<%=XFormConstant.TEMPLATE_OTHER%>")?"inline":"none";
		tbObj.rows[1].style.display = (value=="" || value != "<%=XFormConstant.TEMPLATE_OTHER%>")?"none":"";
		// end
		loadLink.style.display = "none";
		$("#SubForm_main_tr").hide();
		$("#SubForm_operation_tr").hide();
		$("span[name='subFormSpan']").hide();
		for(var i=2; i<tbObj.rows.length; i++){
			tbObj.rows[i].style.display = "none";
		}
		$("#FormTemplate_" + key + "_Layout").hide();
	}
	if(value=="5"){
		
		<%
			pageContext.setAttribute("_isWpsWebOfficeEnable", new Boolean(SysAttWpsWebOfficeUtil.isEnable()));
			pageContext.setAttribute("_isWpsCloudEnable", new Boolean(SysAttWpsCloudUtil.isEnable()));
			pageContext.setAttribute("_isWpsCenterEnable", new Boolean(SysAttWpsCenterUtil.isEnable()));
			
			//加载项 
 			pageContext.setAttribute("_isWpsAddonsEnable", new Boolean(SysAttWpsCloudUtil.isEnableWpsWebOffice()));
			Boolean isWindows = Boolean.FALSE;
			if("windows".equals(JgWebOffice.getOSType(request))){
				isWindows = Boolean.TRUE;
			}
			pageContext.setAttribute("isWindowsInOAassist", isWindows);
		%>
		var wps_CloudEnable='${pageScope._isWpsCloudEnable}';
		if(wps_CloudEnable=='true'){
			//wps私有云开启
			if(typeof(wps_cloud_mainContent) != 'undefined'){
				wps_cloud_mainContent.load();
			}
		}

		var wps_CenterEnable='${pageScope._isWpsCenterEnable}';
		if(wps_CenterEnable=='true'){
			//wps私有云开启
			if(typeof(wps_center_mainContent) != 'undefined'){
				wps_center_mainContent.load();
			}
		}
		
		var wps_WpsWebOffice='${pageScope._isWpsWebOfficeEnable}';
		if(wps_WpsWebOffice=='true'){
			//wpsg公有云
			if(typeof(wps_mainContent) != 'undefined'){
				wps_mainContent.load();
			}
		}
		
		var wps_WpsAddonsEnable='${pageScope._isWpsAddonsEnable}';
		var wpsoaassistEmbed = "${wpsoaassistEmbed}";
		var isWindowsInOAassist = "${isWindowsInOAassist}";
		//内嵌加载项
		if(wps_WpsAddonsEnable == "true"&&wpsoaassistEmbed=="true"&&isWindowsInOAassist=="false"){
			curTabWordIsEmbeddedAddons = true;
			setTimeout(function(){
				wps_linux_mainContent.load();
			},500);
		}
		
		$("#wordView").show();
	}else{
		$("#wordView").hide();
		//内嵌加载项切换页签时保存当前内容
		if('${pageScope._isWpsAddonsEnable}' == "true"&&"${wpsoaassistEmbed}"=="true"&&"${isWindowsInOAassist}"=="false"&&curTabWordIsEmbeddedAddons == true){
			curTabWordIsEmbeddedAddons = false;
			wps_linux_mainContent.setTmpFileByAttKey();
			wps_linux_mainContent.isCurrent=false;
		}
	}
	if (typeof XForm_Mode_Listener != 'undefined') {
		XForm_Mode_Listener(key, value);
	}
}
function Form_GetModeElements(key) {
	return document.getElementsByName('sysFormTemplateForms.'+key+'.fdMode');
}
<%-- 选取通用模板 --%>
function Form_SelectTemplate(key, callback){
	//  #5589 修复chrome浏览器引用其他模板链接无效问题 作者 曹映辉 #日期 2014年9月22日
	var attrs = {oldId:$('#sysFormTemplateForms.'+key+'.fdCommonTemplateId').val()
		,oldName:$('#sysFormTemplateForms.'+key+'.fdCommonTemplateName').val()};
	var action = null;
	if(callback){
		var idField = null;
		var nameField = null;
		action = function(rtnVal){
			if(rtnVal==null)
				return;

			//},function(){console.log(1)});
			$.ajax({
				type:"post",
				url: Com_Parameter.ContextPath + "sys/xform/sys_form_common_template/sysFormCommonTemplate.do?method=selectTemplate",
				data:{"fdId":rtnVal.data[0].id},
				async:false ,    //用同步方式
				dataType: "json",
				success:function(data){
					if(data &&data.length>0&&data[0]&& data[0].result){
						var iframe = document.getElementById('IFrame_FormTemplate_' + key);
						iframe.contentWindow.Designer.instance.setHTML(data[0].result);
						//解决加载新模板后默认被选中问题 #33478 作者 曹映辉 #日期 2017年4月13日
						iframe.contentWindow.Designer.instance.builder.resetDashBoxPos();
					}
				}
			})
		};
	}else{
		var idField = 'sysFormTemplateForms.'+key+'.fdCommonTemplateId';
		var nameField = 'sysFormTemplateForms.'+key+'.fdCommonTemplateName';
		action = function(rtnVal) {
			if(rtnVal==null)
				return;
			var data = new KMSSData();
			data.AddBeanData('sysFormCommonTemplateTreeService&fdId='+rtnVal.data[0].id);
			var returnData = data.GetHashMapArray()[0];
			var html = returnData['fdDesignerHtml'];
			var iframe = document.getElementById('sysFormTemplateForms.' + key + '.fdCommonTemplateView');
			iframe.contentWindow.document.body.innerHTML = html;
			//构造URL
			var url = Com_Parameter.ContextPath + "sys/xform/sys_form_common_template/sysFormCommonTemplate.do?method=view&fdId="+returnData['fdId']+"&fdModelName="+returnData['fdModelName']+"&fdKey="+returnData['fdKey']+"&fdMainModelName=${param.fdMainModelName}";
			var OtherTemplateViewDom = document.getElementById("Frorm_OtherTemplate_${JsParam.fdKey}_view");
			if(OtherTemplateViewDom && OtherTemplateViewDom != null){
				OtherTemplateViewDom.style.display = "inline";
				OtherTemplateViewDom.setAttribute("href",url);
			}
		};
	};
	var dialog = new KMSSDialog(false);
	dialog.BindingField(idField, nameField, null, null);
	dialog.SetAfterShow(action);
	dialog.URL = Com_Parameter.ContextPath + "sys/xform/sys_form_common_template_new/sysFormCommonTemplate_select.jsp?fdModelName=${sysFormTemplateForm.modelClass.name}&fdKey=${JsParam.fdKey}";
	dialog.Show(window.screen.width*710/1366,window.screen.height*550/768);
}

function Form_ShowCommonTemplatePreview(key) {
	var id = document.getElementById('sysFormTemplateForms.'+key+'.fdCommonTemplateId');
	//修复多浏览器通用流程模板不显示问题
	if(!id){
		id=document.getElementsByName('sysFormTemplateForms.'+key+'.fdCommonTemplateId')[0];
	}
	if (id != null && id.value != '') {
		var data = new KMSSData();
		data.AddBeanData('sysFormCommonTemplateTreeService&fdId=' + id.value);
		var html = data.GetHashMapArray()[0]['fdDesignerHtml'];
		var iframe = document.getElementById('${sysFormTemplateFormPrefix}fdCommonTemplateView');
		iframe.contentWindow.document.body.innerHTML = html;
	}
}
Com_AddEventListener(window, "load", function(){
	// 表单引用方式 选项初始化
	Form_Template_OptionInit("${JsParam.fdKey}");
	// 由于标签解析的时候，可能会不存在扩展的选项（通过addOptionType扩展的），所以此处再做一层处理
	var modeDao = "${xFormTemplateForm.fdMode}"; // 数据库存储的引用方式
	var mode = Form_getModeValue("${JsParam.fdKey}"); // 当前选中的引用方式
	var method = Com_GetUrlParameter(location.href,"method");
	var __externalMode = Com_GetUrlParameter(location.href,"mode");
	var _externalForm = Com_GetUrlParameter(location.href,"fdThirdTemplateId");
	var select = $("[name='sysFormTemplateForms.${JsParam.fdKey}.fdMode']");
	if (method && method.indexOf("add") >= 0) {
		if (__externalMode){
			modeDao = __externalMode;
			if (__externalMode != "<%=XFormConstant.TEMPLATE_DEFINE%>") {
				select.attr("disabled","disabled");
				select.addClass("removeSelectAppearance");
			}
		}
		var sourceFrom = Com_GetUrlParameter(location.href,"sourceFrom");
		if (sourceFrom === "ding") {
			select.attr("disabled","disabled");
			select.addClass("removeSelectAppearance");
			if (!XForm_XformIframeIsLoad(window)) {
				LoadXForm("TD_FormTemplate_${JsParam.fdKey}");
			}
		}
	}
	
	if((method != "add" && modeDao && modeDao != '' && modeDao != mode) 
			|| (method && method.indexOf("add") >= 0 && __externalMode) ){
		if(select.length > 0){
			$(select[0]).val(modeDao);
			mode = modeDao;
		}
	}
	Form_ChgDisplay("${JsParam.fdKey}", mode);
	if(method == 'edit'){
		if (mode == "<%=XFormConstant.TEMPLATE_SUBFORM%>"){
			//多表单编辑时不允许切换表单模式
			if(select.length > 0){
				select.prop('disabled','false');
			}
			$("span[name='subFormSpan']").show();
			$("span[name='subFormSpan']").text("<kmss:message key='sys-xform:sysSubFormTemplate.edit_msg'/>");
		} else if (mode == "<%=XFormConstant.TEMPLATE_DEFINE%>")  {
			//编辑页面先不能编辑,如果没有钉钉套件再允许编辑
			select.attr("disabled","disabled");
			select.addClass("removeSelectAppearance");
		}
	}
	Form_ShowCommonTemplatePreview("${JsParam.fdKey}");
	if (method && method.indexOf("add") >= 0) {
		if (_externalForm) {
			select.attr("disabled","disabled");
			select.addClass("removeSelectAppearance");
			$("#A_FormTemplate_${JsParam.fdKey}").hide();
		}
	}
    //根据模板初始化表单内容
	if(method=='add'){
		var sourceFrom =  "${param.sourceFrom}";
		if(sourceFrom=='ding'||sourceFrom=='Reuse'){
			return;
		}
		Form_template_initParam();
	}
});
//获得表单初始化值
function Form_template_initParam(){
	var formName = "${JsParam.formName}";
	var fdKey = "${JsParam.fdKey}";
	var fdMainModelName = "${JsParam.fdMainModelName}";
	var data = new KMSSData();
	data.AddBeanData('sysFormCommonTemplateDeafaultService&formName='+formName+'&fdKey='+fdKey+'&fdMainModelName='+fdMainModelName);
	window.XForm_Param_default_html = data.GetHashMapArray()[0]['fdDesignerHtml'];
	if(window.XForm_Param_default_html!=null&&window.XForm_Param_default_html!=''&&window.XForm_Param_default_html!=undefined) {
		var iframeT = document.getElementById('IFrame_FormTemplate_' + fdKey);
		//查找默认模板初始化表单内容
		Com_AddEventListener(iframeT, 'load', function (){
			iframeT.contentWindow.Designer.instance.setHTML(window.XForm_Param_default_html);
		});
		//Form_template_initContent(window.XForm_Param_default_html,fdKey);
	}
}

// 选项初始化
// addOptionType: 显示值|实际值;显示值2|实际值2 ,多个选项以分号分开，显示值和实际值以|分开，如果没有|，默认显示值和实际值一样，扩展实际值不能选择0-4，从5开始
// removeOptionType: 1;2 ，只需要填写实际值，以分号分开
function Form_Template_OptionInit(fdKey){
	// 先判断是否有需要变更的传入参数
	var addOptionType = "${JsParam.addOptionType}";
	var removeOptionType = "${JsParam.removeOptionType}";
	if(addOptionType || removeOptionType){
		var select = $("select[name='sysFormTemplateForms." + fdKey + ".fdMode']");
		if(select.length > 0){
			if(addOptionType){
				Form_Template_AddOption(addOptionType,select[0]);
			}
			if(removeOptionType){
				Form_Template_RemoveOption(removeOptionType,select[0]);
			}
		}
	}
	
}

// 初始化的时候，根据业务模块传进来的参数，动态增加表单引用方式的选项
function Form_Template_AddOption(optionType,dom){
	var options = optionType.split(";");
	for(var i = 0;i < options.length;i++){
		var option = options[i];
		var val, text;
		if(option.indexOf("|") > -1){
			var array = option.split("|");
			text = Data_GetResourceString(array[0]);
			if(!text){
				text = array[0];
			}
			val = array[1];
		}else{
			text = option;
			val = option;
		}
		if(val == '' || parseInt(val) < 4){
			continue;
		}
		$(dom).append("<option value='"+ val +"'>" + text + "</option>");
	}
}

//初始化的时候，根据业务模块传进来的参数，动态删除表单引用方式的选项
function Form_Template_RemoveOption(optionType,dom){
	var selectOptions = $(dom).find("option");
	var removeVals = optionType.split(";");
	for(var i = 0;i < selectOptions.length;i++){
		var option = selectOptions[i];
		if($.inArray(option.value,removeVals) > -1){
			$(option).remove();
		}
	}
}

Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = Form_TemplateValidate_${JsParam.fdKey};
function Form_TemplateValidate_${JsParam.fdKey}(){
	var typeVe = Form_getModeValue("${JsParam.fdKey}");
	var commonTemplateEl = document.getElementById('${sysFormTemplateFormPrefix}fdCommonTemplateId');
	if(!commonTemplateEl){
		commonTemplateEl = document.getElementsByName('${sysFormTemplateFormPrefix}fdCommonTemplateId')[0];
	}
	if(typeVe == "<%=XFormConstant.TEMPLATE_OTHER%>" 
			&& commonTemplateEl.value == ""){
		alert("<bean:message bundle="sys-xform" key="sysFormTemplate.fdCommonName" /><bean:message bundle="sys-xform" key="validate.isNull" />");
		return false;
	}
	return true;
}

function Form_getModeValue(key){
	var typeEl = document.getElementsByName('sysFormTemplateForms.'+key+'.fdMode')[0];
	var typeVe = "";
	if(typeEl && typeEl != null){
		typeVe = typeEl.value;
	}
	return typeVe;
}

function XForm_loadXFormCustomXML(){
	var fdMetadataXmlObj = document.getElementById("${sysFormTemplateFormPrefix}fdMetadataXml");
	var customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'].contentWindow;
	if(!customIframe){
		customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'];
	}
	if(customIframe.Designer != null && customIframe.Designer.instance != null){
		fdMetadataXmlObj.value = customIframe.Designer.instance.getXML();
	}
}
<%-- 
=====================================
 数据字典加载相关
===================================== 
--%>
var _xform_MainModelName = '${JsParam.fdMainModelName}';

function XForm_Util_GetRadioValue(objs) {
	for(var i = 0; i < objs.length; i++){
		if(objs[i].checked){
			return objs[i].value;
		}
	}
	return null;
}

function XForm_Util_UnitArray(array, sysArray, extArray) {
	<%-- // 合并 --%>
	array = array.concat(sysArray);
	if (extArray != null) {
		array = array.concat(extArray);
	}
	<%-- // 结果 --%>
	return array;
}

function XForm_getXFormDesignerObj_${JsParam.fdKey}(xformTemplateId) {
	var obj = [];
	
	<%-- // 1 加载系统字典 --%>
	var sysObj = _XForm_GetSysDictObj(_xform_MainModelName);
	var extObj = null;
	
	<%-- // 2 判断是否启用表单 --%>
	var modeObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdMode")[0];
	var modeValue = null;
	if(modeObj && modeObj != null){
		modeValue = modeObj.value;
	}
	
	if (modeValue == '1') { return sysObj; }
	
	if (modeValue == '2') { // 加载通用模板
		var tempIdObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdCommonTemplateId");
		if (tempIdObj.length > 0 && tempIdObj[0].value != '') {
			extObj = _XForm_GetCommonExtDictObj(tempIdObj[0].value);
			return XForm_Util_UnitArray(obj, sysObj, extObj);
		}
		return sysObj;
	}
	
	<%-- //if (modeValue == '3') {} // 自定义情况下 --%>
	
	<%-- // 3 判断页面类型 --%>
	var fdTypeObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdDisplayType");
	var fdTypeValue = XForm_Util_GetRadioValue(fdTypeObj);
	
	if (fdTypeValue == '1') {
		var exitFileObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdFormFileName")[0];
		var exitFileValue = exitFileObj.value;
		if (exitFileValue == '') {
			return sysObj;
		}
		extObj = _XForm_GetExitFileDictObj(exitFileValue);
		return XForm_Util_UnitArray(obj, sysObj, extObj);
	}
	
	<%-- //if (fdTypeValue == '2') {} // 数据类型 --%>
	var customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'].contentWindow;
	if(!customIframe){
		customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'];
	}
	if (customIframe.Designer && customIframe.Designer.instance && customIframe.Designer.instance.hasInitialized) {
		var designer = customIframe.Designer.instance;
		extObj = designer.getObj(null,null,true);
	} else {
		xformTemplateId = xformTemplateId ? xformTemplateId:"${xFormTemplateForm.fdId}";
		extObj = _XForm_GetTempExtDictObj(xformTemplateId);
	}
	return XForm_Util_UnitArray(obj, sysObj, extObj);
}
//只获取自定义表单的字段信息
function XForm_getXFormExtDesignerObj_${JsParam.fdKey}(xformTemplateId) {
	var obj = [];
	
	<%-- // 2 判断是否启用表单 --%>
	var modeObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdMode")[0];
	var modeValue = null;
	if(modeObj && modeObj != null){
		modeValue = modeObj.value;
	}
	
	if (modeValue == '2') { // 加载通用模板
		var tempIdObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdCommonTemplateId");
		if (tempIdObj.length > 0 && tempIdObj[0].value != '') {
			return _XForm_GetCommonExtDictObj(tempIdObj[0].value);
			
		}
	}
	
	<%-- //if (modeValue == '3') {} // 自定义情况下 --%>
	
	<%-- // 3 判断页面类型 --%>
	var fdTypeObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdDisplayType");
	var fdTypeValue = XForm_Util_GetRadioValue(fdTypeObj);
	
	if (fdTypeValue == '1') {
		var exitFileObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdFormFileName")[0];
		var exitFileValue = exitFileObj.value;
		if (exitFileValue == '') {
			return null;
		}
		return _XForm_GetExitFileDictObj(exitFileValue);
	}
	
	<%-- //if (fdTypeValue == '2') {} // 数据类型 --%>
	var customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'].contentWindow;
	if(!customIframe){
		customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'];
	}
	if (customIframe.Designer && customIframe.Designer.instance && customIframe.Designer.instance.hasInitialized) {
		var designer = customIframe.Designer.instance;
		return designer.getObj(null,null,true);
	} else {
		xformTemplateId = xformTemplateId ? xformTemplateId:"${xFormTemplateForm.fdId}";
		return _XForm_GetTempExtDictObj(xformTemplateId);
	}
	return null;
}
function XForm_getXFormDesignerInstance_${JsParam.fdKey}(xformTemplateId) {
	<%-- //if (fdTypeValue == '2') {} // 数据类型 --%>
	var obj = {};
	var customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'].contentWindow;
	if(!customIframe){
		customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'];
	}
	if (customIframe.Designer && customIframe.Designer.instance && customIframe.Designer.instance.hasInitialized) {
		obj["designer"] = customIframe.Designer.instance;
		obj["modelName"] = customIframe.parent._xform_MainModelName;
		return obj;
	}
	return null;
}

function _XForm_GetTempExtDictObj(tempId) {
	return new KMSSData().AddBeanData("sysFormDictVarTree&tempType=template&tempId="+tempId).GetHashMapArray();
}
function _XForm_GetCommonExtDictObj(tempId) {
	return new KMSSData().AddBeanData("sysFormDictVarTree&tempType=common&tempId="+tempId).GetHashMapArray();
}
function _XForm_GetExitFileDictObj(fileName) {
	return new KMSSData().AddBeanData("sysFormDictVarTree&tempType=file&fileName="+fileName).GetHashMapArray();
}
function _XForm_GetSysDictObj(modelName){
	return Formula_GetVarInfoByModelName(modelName);
}
function _XForm_GetSysDictObj_${JsParam.fdKey}() {
	return _XForm_GetSysDictObj(_xform_MainModelName);
}

function XForm_OnLabelSwitch_${JsParam.fdKey}(tableName,index) {
	var customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'].contentWindow;
	if(!customIframe){
		customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'];
	}
	if (customIframe.Designer && customIframe.Designer.instance && customIframe.Designer.instance.hasInitialized) {
		customIframe.Designer.instance.fireListener('designerBlur');
	}
	SubForm_OnLabelSwitch(tableName,index);
}

// 判断表单页签是否又被加载
function XForm_XformIframeIsLoad(win){
	if(win){
		//先判断点击的页签是否是多语言页签
		var customIframe = win.frames['IFrame_FormTemplate_${JsParam.fdKey}'].contentWindow;
		if(!customIframe){
			customIframe = win.frames['IFrame_FormTemplate_${JsParam.fdKey}'];
		}
		//表单没有加载，先让它加载，不然无法初始化
		if (!(customIframe.Designer && customIframe.Designer.instance && customIframe.Designer.instance.hasInitialized)) {
			return false;
		}	
	}
	return true;
}

/**
 * 移动开关提示
 */
function XForm_UseDefaultLayout() {
	var $tip = $(".lui_prompt_tooltip");
   var $menu = $(".lui_dropdown_tooltip_menu");
   $tip.mouseover(function(){
	   $menu.show();
   });
   $tip.mouseout(function(){
	   $menu.hide();
   });
}

// 设置获取流程节点函数
Com_AddEventListener(window, 'load', function () {
	if (window.WorkFlow_getWfNodes_${JsParam.fdKey} != null) {
		var iframe = document.getElementById('IFrame_FormTemplate_${JsParam.fdKey}');
		if (iframe) {
			Com_AddEventListener(iframe, 'load', function () {
				iframe.contentWindow.XForm_GetWfAuditNodes = WorkFlow_getWfNodes_${JsParam.fdKey};
				iframe.contentWindow.PrefixKey = "${JsParam.fdKey}";
				iframe.contentWindow.SignatureModelName = "${xFormTemplateForm.fdModelName}";
				iframe.contentWindow.SignatureProjectPath = "${KMSS_Parameter_ContextPath}";
			});
		}
	}
	var table = document.getElementById("SubFormTable_${JsParam.fdKey}").parentNode;
	while((table!=null) && (table.tagName!="TABLE")){
		table = table.parentNode;
	}
	if(table!=null){
		Doc_AddLabelSwitchEvent(table, "XForm_OnLabelSwitch_${JsParam.fdKey}");
	}
	XForm_UseDefaultLayout();
});
</script>
<c:if test="${param.useLabel != 'false'}">
<tr LKS_LabelName="<kmss:message key="${(not empty param.messageKey) ? (param.messageKey) : 'sys-xform:sysForm.tab.label'}" />" 
	style="display:none" 
	<%--onreadystatechange="XForm_loadXFormCustomXML();"--%>
	>
	<td>
</c:if>
<table style="width:100%" id="SubFormTable_${JsParam.fdKey}">
	<!-- 移动端设计 -->
	<tr  id="XForm_${JsParam.fdKey}_CustomTemplateMobileRow">
		<td colspan=3>
			<%@include file="/sys/xform/designer/mobile/sysFormTemplate_mobile_designer.jsp"%>
		</td>
	</tr>
	<tr id="pc_form_${JsParam.fdKey}">
		<td style="width: 20%;display:none;" id="SubForm_main_tr">
			<div id="DIV_SubForm_${JsParam.fdKey}" style="border:1px #d2d2d2 solid;">
			<div id="Sub_title_div" style="height:36px;border-bottom:1px solid #d2d2d2;">
				<table class="subTable" style="width:100%">
					<tr>
						<td style="width: 38%;padding:8px"><div style="margin-left:4px;font-size:12px;font-weight:bold;"><bean:message bundle="sys-xform" key="sysSubFormTemplate.multiform_configuration" /></div></td>
						<td style="width: 32%;padding:8px"><a id="operationA2" style="cursor:pointer;color:#47b5e6;font-weight:bold;font-size:12px;" onclick="Designer_SubForm_Xml();"><bean:message bundle="sys-xform" key="sysSubFormTemplate.view_xml" /></a></td>
						<td style="width: 30%;padding:8px" align="right">
							<%-- <a id="newDefaultWebForm" style="cursor:pointer;" onclick="addMobileDefaultRow();">
								<img src="${KMSS_Parameter_ContextPath}sys/xform/base/resource/icon/default_mobile.png" border="0" title="<bean:message bundle="sys-xform" key="sysSubFormTemplate.webform_config" />"/>
							</a>&nbsp; --%>
							<a href="javascript:void(0)" style="cursor:pointer;" onclick="SubForm_AddRow(this,false);">
								<img src="${KMSS_Parameter_ContextPath}sys/xform/base/resource/icon/add.png" border="0" title="<bean:message bundle="sys-xform" key="sysSubFormTemplate.add" />"/>
							</a>
						</td>
					</tr>
				</table>
			</div>
			<c:set var="mobile" value="true" />
			<div id="SubFormDiv" style="overflow-x:hidden;overflow-y:auto;text-align:center;"> 
				<%@include file="/sys/xform/base/sysSubFormTemplate_edit.jsp"%>
			</div>
			<div id="SubFormLoadMsg" style="font-weight:bold;display:none;color:rgb(153, 153, 153);margin-left: 10px;"><bean:message bundle="sys-xform" key="sysSubFormTemplate.loadMsg" /></div>
			<div id="controlsDiv" style="overflow-x:hidden;overflow-y:auto;">
			</div>
			</div>
		</td>
		<td style="width:7px;display:none" id="SubForm_operation_tr">
			<image id="Subform_operation" style="cursor:pointer;" src="${KMSS_Parameter_ContextPath}sys/xform/base/resource/icon/varrowleft.gif" onclick="_Designer_SubFormAddHide(this);">
		</td>
		<td>
			<table class="tb_normal" width="100%" id="TB_FormTemplate_${JsParam.fdKey}">
			<%-- 引用方式 --%>
			<tr>
				<td width="15%" class="td_normal_title" valign="top">
					<bean:message bundle="sys-xform" key="sysFormTemplate.fdMode" />
				</td>
				<td width="85%">
					<xform:select property="${sysFormTemplateFormPrefix}fdMode" onValueChange="Form_ChgDisplay('${JsParam.fdKey}',this.value);" showPleaseSelect="false">
						<xform:customizeDataSource className="com.landray.kmss.sys.xform.base.service.spring.SysFormModeDataSource"></xform:customizeDataSource>
					</xform:select>
					
					<a href="#" id="A_FormTemplate_${HtmlParam.fdKey}" style="padding-left:20px;"
						onclick="Form_SelectTemplate('${JsParam.fdKey}', true);">
						<bean:message bundle="sys-xform" key="sysFormTemplate.from.button" />
					</a>
					<span name="subFormSpan" class="txtstrong" style="display:none;margin-left:10px;"><kmss:message key='sys-xform:sysSubFormTemplate.edit_msg'/></span>
					
					<!-- 移动配置开关start -->
					<div class="layoutMobileWrap" id="FormTemplate_${HtmlParam.fdKey}_Layout">
						<div class="layoutMobileSwitch">
							<xform:checkbox property="${sysFormTemplateFormPrefix}fdUseDefaultLayout" >
								<xform:simpleDataSource value="true"><bean:message bundle="sys-xform" key="sysFormTemplate.fdUseDefaultLayoutDesc"/></xform:simpleDataSource>
							</xform:checkbox>
						</div>
						<div class="lui_prompt_tooltip">
					    	<label class="lui_prompt_tooltip_drop">
					    		<img src="${KMSS_Parameter_ContextPath}sys/xform/designer/style/img/promptControl.png">
			    			</label>
					    	<div class="lui_dropdown_tooltip_menu" name="useDefaultLayoutTip" style="display: none;"><bean:message bundle="sys-xform" key="sysFormTemplate.fdUseDefaultLayoutTip"/></div>
					    </div>
					</div>
					<!-- 移动配置开关end -->
					<div style="display:none;line-height:25px;height:25px;width:80%;" id="Form_OtherTemplate_${param.fdKey}">
						<html:text property="${sysFormTemplateFormPrefix}fdCommonTemplateName" readonly="true" styleClass="inputsgl" style="width:30%;text-align:center;"/>				
						<html:hidden property="${sysFormTemplateFormPrefix}fdCommonTemplateId" />
						<span class="txtstrong">*</span>&nbsp;&nbsp;&nbsp;<a
						href="#" title="<kmss:message key='sys-xform:XFormTemplate.selectTemplate' />"
						onclick="Form_SelectTemplate('${JsParam.fdKey}');"><bean:message key="dialog.selectOther" /> </a>
						<%-- 查看模板 为避免阅读困难，用c:choose分开两条--%>
						<c:choose>
							<c:when test="${empty xFormTemplateForm.fdCommonTemplateId}">
								<a href="#" id="Frorm_OtherTemplate_${HtmlParam.fdKey}_view" title="<kmss:message key='sys-xform:XFormTemplate.viewTemplate'/>" style="display:none" target="_blank"><bean:message key="button.view" />
								</a>
							</c:when>
							<c:otherwise>
								<a title="<kmss:message key='sys-xform:XFormTemplate.viewTemplate' />" href="<c:url value="/sys/xform/sys_form_common_template/sysFormCommonTemplate.do"/>?method=view&fdId=${xFormTemplateForm.fdCommonTemplateId}&fdModelName=${xFormTemplateForm.fdModelName}&fdKey=${xFormTemplateForm.fdKey}&fdMainModelName=${param.fdMainModelName}" id="Frorm_OtherTemplate_${param.fdKey}_view" style="display:inline;" target="_blank">
									<bean:message key="button.view" />
								</a>
							</c:otherwise>
						</c:choose>
																					
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<iframe width="100%" height="500" FRAMEBORDER=0 id="${sysFormTemplateFormPrefix}fdCommonTemplateView"
					src="<c:url value="/sys/xform/designer/designPreview.jsp"/>"></iframe>
				</td>
			</tr>
			<%@ include file="/sys/xform/base/sysFormTemplateDisplay_edit.jsp"%>
			</table>
		</td>
	</tr>
</table>	
		
<c:if test="${param.useLabel != 'false'}">
	</td>
</tr>
</c:if>
