<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.util.ModelUtil"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDataDict"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDictModel"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%
	String modelName = request.getParameter("modelName");
	SysDictModel dict = SysDataDict.getInstance().getModel(ModelUtil.getModelClassName(modelName));
	String messageKey = dict.getMessageKey();
	if(StringUtil.isNotNull(messageKey)){
		messageKey = ResourceUtil.getString(messageKey, request.getLocale());
	}
	request.setAttribute("modelMessage", messageKey);
%>
<input onclick="file_importFields();" class="lui_form_button" type="button" value="${lfn:message('sys-archives:sysArchivesFileTemplate.importFields') }" style="float:right;cursor:pointer;"/>
<script>
//获得自定义表单的属性
	function file_getExtObj() {
		var customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'].contentWindow;
		var extObj = [];
		if(!customIframe){
			customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'];
		}
		if (customIframe.Designer && customIframe.Designer.instance && customIframe.Designer.instance.hasInitialized) {
			var designer = customIframe.Designer.instance;
			extObj = designer.getObj();
		}else {
			var name = "sysFormTemplateForms.${JsParam.fdKey}.fdMetadataXml";
			extObjXml = document.getElementsByName(name)[0].value;
			$(extObjXml).find('extendSimpleProperty').each(function() {
				var $item = $(this);
				extObj.push({
					name : $item.attr('name'),
					label : $item.attr('label'),
					type : $item.attr('type')
				});
			});
		}
		// 处理多model时自定义表单的属性
		var otherModelName = "${JsParam.otherModelName}";
		if (otherModelName) {
			for (var i in extObj) {
				extObj[i].name = "${JsParam.modelName}:" + extObj[i].name;
				extObj[i].label = "${modelMessage } - " + extObj[i].label;
			}
		}
		return extObj;
	}
	//一键导入表单字段
	function file_importFields() {
		//判断某个字段是否是表单字段
		var isFormField = function(id) {
			var bool = false;
			var extObj = file_getExtObj();
			console.log(1)
			for (var i = 0; i < extObj.length; i++) {
				if(extObj[i].name == id) {
					bool = true;
					break;
				}
			}
			return bool;
		};
		//自定义选择器
		$.expr[':'].Contains = function(a, i, m) {
			if(a.value == null || a.value == '') 
				return false;
			return isFormField(a.value);
		};
		var extObj = file_getExtObj();
		//将表单字段选项删除
		$("select[ftype]").find('option:Contains').remove();
		for (var i = 0; i < extObj.length; i++) {
			$("select[ftype*='"+extObj[i].type+"']").append($("<option value=\""+extObj[i].name+"\">"+extObj[i].label+"</option>"));
		}
		seajs.use(['lui/dialog'],function(dialog) {
			dialog.alert("${lfn:message('return.optSuccess')}")
		});
	}
</script>