<%@page import="com.landray.kmss.sys.xform.util.LangUtil"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script>
</script>
<script>
	Com_IncludeFile("dialog.js");
	function List_ConfirmImport(checkName){
		if(window.List_CheckSelect){
			return List_CheckSelect(checkName);
		}
	}
	
	function importLanguageKey(){
		Dialog_Tree(false,'languageKey',null,',','sysFormLangkeyData&key=!{value}','语种','',function (rtnVal){
			if(rtnVal==null || rtnVal.data.length == 0){
				return false;
			}else{
				if(confirm("<bean:message bundle="sys-xform" key="sysFormCommonMultiLang.isImport"/>")){
					var key = "";
					for(var i=0;i<rtnVal.data.length;i++){
						key+=rtnVal.data[i].id;
						if(i != rtnVal.data.length-1){
							key+=',';
						}
					}
					mutilangExport(key);
				}
			}
		},'','',false);
	}
	
	
	function mutilangExport(key){
		
		var fdModelId = "${JsParam.fdModelId}";
		var url = Com_Parameter.ContextPath + "sys/mutillang/import.do?method=excelExport&isCommonTemplate=${JsParam.isCommonTemplate}&fdModelName=${JsParam.fdModelName}";
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
<input type="button" value="<bean:message bundle="sys-xform" key="sysFormMultiLang.import"/>"
			onclick="Com_OpenWindow(Com_Parameter.ContextPath + 'sys/xform/lang/sysFormMultilang_upload.jsp')">
<input type="button" value="<bean:message bundle="sys-xform" key="sysFormMultiLang.export"/>"
			onclick="if(!List_ConfirmImport())return;importLanguageKey()">
<%}%>