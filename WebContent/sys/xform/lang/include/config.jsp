<%@page import="com.landray.kmss.framework.plugin.core.config.IExtension"%>
<%@page import="com.landray.kmss.framework.service.plugin.Plugin"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%
IExtension[] extensions = Plugin.getExtensions("com.landray.kmss.sys.formMultiLang", "*", "item");
%>
<xform:text property="value(kmss.multi.lang)" showStatus="edit"></xform:text>
<script>
Com_IncludeFile("jquery.js");
</script>
<script>
var uuconfigKey = {};

<%
for (IExtension extension : extensions) {
%>		
uuconfigKey['<%=Plugin.getParamValueString(extension, "uuid") %>'] = true;
<% 		 
}
%>
var uu_temp = document.getElementsByName('value(kmss.multi.lang)')[0];
var $dom = $('input[name ="kmss.multi.lang"]');
//初始化
$(document).ready(function(){
	var array=[];
	uu_temp.style.display ='none';
	uu_temp.value && (array =uu_temp.value.split(","));
	for(var i =0 ; i<array.length ; i++){
			if(array[i] == ''){
				continue;
			}
			for(var j=0;j<$dom.length;j++){
				 if ($dom[j].value == array[i]){
					 $dom[j].checked = true;
					 break;
				 }
			}
	}
})

Com_Parameter.event["submit"].unshift(function(){
	var array = [];
	var arrayValue = [];
	var valueTemp = "";
	var uu_support = document.getElementsByName('value(kmss.lang.support)')[0];
	if(uu_support == null){
		return true;
	}
	if(uu_support.value){
		//如果存在进行赋值，不存在不修改，取配置
		array = uu_support.value.split(";")
		for(var i =0 ; i<array.length ; i++){
			if(array[i] == ''){
				continue;
			}
			arrayValue = array[i].split("|")
			//如果 arrayValue 长度为2，通过校验1
			if(arrayValue.length == 2){
				//如果它属于配置列表
				if(uuconfigKey[arrayValue[1]] == true){
					valueTemp += arrayValue[1] + ","
				}
			}
		}
	}
	uu_temp.value = valueTemp;
	return true;
});
</script>