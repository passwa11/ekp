<%@page import="com.landray.kmss.sys.xform.util.LangUtil"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>

<script>

	seajs.use(['sys/ui/js/dialog'], function(dialog) {
		window.importLanguageKey = function(){
			dialog.tree("sysFormLangkeyData&key=!{value}","${lfn:message('sys-xform:sysFormMultiLang.multi.language')}",function (rtnVal){
				if(rtnVal==null){
					return false;
				}else{
					var key = "";					
					key+=rtnVal.value;
					var languageKeyDom = document.getElementsByName("languageKey")[0];
					languageKeyDom.value = key; 
					mutilangExport(key);
				}
			});
		}
	});
	
	function List_CheckSelect(){
		var obj = document.getElementsByName("List_Selected");
		for(var i=0; i<obj.length; i++)
			if(obj[i].checked)
				return true;
		alert("<bean:message key="page.noSelect"/>");
		return false;
	}

	
	function mutilangExport(key){
		
		var fdModelId = "${param.fdModelId}";
		var url = Com_Parameter.ContextPath + "sys/mutillang/import.do?method=excelExport&isCommonTemplate=${param.isCommonTemplate}&fdModelName=${param.fdModelName}";
		var param = "";
		if(fdModelId){
			param = fdModelId;
		}else{
			var doms = document.getElementsByName('List_Selected');
			for(var i = 0 ; i < doms.length ; i++){
				if(doms[i].checked == true){
					if (param != ""){
						param += ';';
					}
					param += doms[i].value;
				}
			}			
		}
		url+="&List_Selected="+param;
		if(key!=""){
			url+="&languageKey="+key;
		}
		Com_OpenWindow(url);
	}
	
</script>
<input type="hidden" name="languageKey" value=""/>
<% if(LangUtil.isEnableMultiLang(request.getParameter("fdMainModelName"), "model") && LangUtil.isEnableAdminDoMultiLang()){ %>
 <ui:button text="${lfn:message('sys-xform:sysFormMultiLang.import')}"  onclick="Com_OpenWindow(Com_Parameter.ContextPath + 'sys/xform/lang/sysFormMultilang_upload.jsp');" order="5" ></ui:button>
 <ui:button text="${lfn:message('sys-xform:sysFormMultiLang.export')}"  onclick="if(!List_CheckSelect())return;importLanguageKey()" order="6" ></ui:button>
<%}%>