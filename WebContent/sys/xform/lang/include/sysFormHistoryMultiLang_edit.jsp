<%@page import="com.landray.kmss.util.UserUtil"%>
<%@page import="com.landray.kmss.sys.xform.util.LangUtil"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script src='<c:url value="/sys/xform/designer/underscore.js"/>'></script>
<c:set var="xFormTemplateForm"
	value="${requestScope[param.formName]}" />

<input type="hidden" id="uu_FdContent"
	name="${param.sysFormTemplateFormPrefix}fdMultiLangContent"
	value="<c:out value='${xFormTemplateForm.fdMultiLangContent}' />" />

<script type="text/template" id="detailRow_extend">
<td colspan="5" style="border-width: 0px;padding: 0px;">
<table width="100%">
<tr>
 	<td  width="15%"><@=c_id@></td>
 	<td  width="10%"><@=c_lang["control"]@></td>
 	<@
		var temp = 0;
		var temp1 = 0;	
		_.each(c_option,function(optionValue,optionKey){
		if(temp > 0){
	@>
			<tr>
			<td><@=c_id@></td>
 			<td><@=c_lang["control"]@></td>
	<@
		}
	@>
		
		<td  width="10%"><@=c_lang[optionKey]["text"]@></td>
		<@ 
			
			var optionDefaultArr = [];
			var optionValueArr = [];
			if(optionKey != 'items'){
					optionDefaultArr =  optionDefaultArr.push(optionValue["default"]||"");
					optionValueArr = optionValueArr.push(optionValue[uukey]||"");
			}else{
					optionDefaultArr = (optionValue["default"]||"").split(/\r\n|\n/);
					optionValueArr = (optionValue[uukey]||"").split(/\r\n|\n/);
			}
			var index = 0;
			if(optionDefaultArr.length > 1){
		@>
			<@ _.each(optionDefaultArr,function(optionDefaultValue,index){
				if(index > 0){
			@>
					<tr>
					<td width="15%"><@=c_id@></td>
 					<td width="10%"><@=c_lang["control"]@></td>
					<td width="10%"><@=c_lang[optionKey]["text"]@></td>
			<@
				}			
			@>
					<td width="5%"><@=optionDefaultValue@></td>
					<td width="30%"><input class="inputsgl" style="width:80%;" type="text" data-uu-order="<@=index@>" data-uu-optionkey="<@=optionKey@>" data-uu-langkey="<@=uukey@>"  value="<@=(optionValueArr[index]||"")@>"></td>	
			</tr>
			<@})@>
		<@
			}else{
		@>
			<td width="5%"><@=optionValue["default"]@></td>
			<td width="30%"><input class="inputsgl" style="width:80%;" type="text" data-uu-order="<@=index@>" data-uu-optionkey="<@=optionKey@>" data-uu-langkey="<@=uukey@>"  value="<@=optionValue[uukey]@>"></td>
			</tr>
		<@}@>
	<@
		temp++;
	})
	@>
</table>
</td>
</script>


<script type="type/template" id="Lable_UU_Lang_title">
<tr LKS_LabelName="<@= lang @>">
				<td>
						<table class="tb_normal" width=100% id="container_<@=key@>">
							<tr>
								<td class="td_normal_title" width="15%"><bean:message bundle="sys-xform" key="sysFormCommonMultiLang.control.name"/></td>
								<td class="td_normal_title" width="10%"><bean:message bundle="sys-xform" key="sysFormCommonMultiLang.control.type"/></td>
								<td class="td_normal_title" width="10%"><bean:message bundle="sys-xform" key="sysFormCommonMultiLang.control.att"/></td>
								<td class="td_normal_title" width="5%"><bean:message bundle="sys-xform" key="sysFormCommonMultiLang.control.content"/></td>
								<td class="td_normal_title" width="30%"><bean:message bundle="sys-xform" key="sysFormCommonMultiLang.control.translate"/></td>
							</tr>
						</table>
				</td>
</tr>
</script>

<%--多语言 --%>
<tr LKS_LabelName="多语言" LKS_LabelId="tr_uu_lang">
	<td>
		<table class="tb_normal" width=100% id="Lable_UU_Lang">
			<tbody>
			</tbody>
		</table>
	</td>
</tr>
<script>

_.templateSettings = {
        interpolate: /\<\@\=(.+?)\@\>/gim,
        evaluate: /\<\@([\s\S]+?)\@\>/gim,
        escape: /\<\@\-(.+?)\@\>/gim
    };
    
//resource
var uu_lang_arr = '<%=ResourceUtil.getKmssConfigString("kmss.multi.lang")%>';
var defaultLang = '<%=LangUtil.Default_Lang%>';
var currentLang = '<%=UserUtil.getKMSSUser().getLocale().toString()%>'.replace("_","-");

if(currentLang == 'null'){
	currentLang = defaultLang;
}

if(uu_lang_arr.charAt(uu_lang_arr.length - 1) == ','){
	uu_lang_arr = uu_lang_arr.substr(0, uu_lang_arr.length - 1);
}
//key
var _langLanguage = uu_lang_arr.split(",");
//value
var _langLanguage_value = eval('<%=LangUtil.getLangValue()%>');
//模板
var template_Lable_UU_Lang_title = _.template($("#Lable_UU_Lang_title").html());

var temp_content = "";
var _uu_result = {};

for(var i =0 ; i<_langLanguage.length ; i++){
		if(_langLanguage[i].toUpperCase() == currentLang.toUpperCase()){
			continue;
		}
		_uu_result.key = _langLanguage[i];
		_uu_result.lang = _langLanguage_value[i];
		temp_content += template_Lable_UU_Lang_title(_uu_result);
}
$('tbody',"#Lable_UU_Lang").html(temp_content);

Doc_ShowLabelTable("Lable_UU_Lang");


function _getUParam(key){
	var locurl = window.location.href;
	var myregexp = new RegExp('\\?method=edit.*?&'+key+'=([^&]*)',"m");
	var match = myregexp.exec(locurl);
	if (match != null) {
		return match[1];
	} else {
		return null;
	}
}

if(_getUParam("uukey")!=undefined){
	setTimeout(function(){
		setTimeout(function(){
			$("#languageUpdate").click();
		},1000)
		
	},4000);
}

function hideMultiLang(){
	try{
		Doc_HideLabelById("Label_Tabel","tr_uu_lang");
	}catch(e){
		setTimeout(function(){
			hideMultiLang();
		},100);
	}
}

(function(){
	var flag = <%=LangUtil.isEnableMultiLang(request.getParameter("fdMainModelName"),"model")%>;
	if(flag == false){
		hideMultiLang();
	}
}());

</script>