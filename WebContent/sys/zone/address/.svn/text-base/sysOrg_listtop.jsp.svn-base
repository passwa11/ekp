<div style="padding-left: 20px;display:inline-block">
<input type="text" name="fdSearchName" class="inputSgl" onkeydown="if(event.keyCode==13) {simpleSearch();return false;}">
<input type="button" class="btnopt" value="<bean:message key="button.list"/>" onclick="simpleSearch();">
<input type="button" class="btnopt" value="<bean:message key="button.reset"/>" onclick="doReset();">
${lfn:message('button.search')}

</div>
<div style="display:inline-block;font-size:13px"><bean:message bundle="sys-zone" key="sysZoneNavigation.info" /></div>
<script>
Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");

function resetRadio(){
	var url = location.href.toString();
	var fdSearchName = Com_GetUrlParameter(location.href, "fdSearchName");
	if(fdSearchName != "" && fdSearchName != null){
		document.getElementsByName("fdSearchName")[0].value = fdSearchName;
	}
}

function List_ConfirmInvalid(checkName){
	return List_CheckSelect(checkName) && confirm('<bean:message bundle="sys-organization" key="organization.invalidatedAll.comfirm" />');
}

resetRadio();

//搜索方法
function simpleSearch(){
	//debugger;
	var url = location.href;
	var fdSearchName = document.getElementsByName("fdSearchName")[0].value;
	url = Com_SetUrlParameter(url, "fdSearchName", fdSearchName);
	window.location.href = url;
}

//重置方法
function doReset(){
	var url = location.href;
	url = Com_SetUrlParameter(url, "s_path", Com_GetUrlParameter(location.href, "s_path"));
	url = Com_SetUrlParameter(url, "s_css", Com_GetUrlParameter(location.href, "s_css"));
	url = Com_SetUrlParameter(url, "fdSearchName", "");
	window.location.href = url;
}
</script>