<input type="text" name="fdSearchName" class="inputSgl" onkeydown="if(event.keyCode==13) {simpleSearch();return false;}">
<input type="button" class="btnopt" value="<bean:message key="button.list"/>" onclick="simpleSearch();">
<input type="button" class="btnopt" value="<bean:message key="button.reset"/>" onclick="doReset();">
<bean:message bundle="sys-number" key="sysNumberMain.search" />
<script>
Com_Parameter.IsAutoTransferPara = true;
Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
function resetRadio(){
	var fdSearchName = Com_GetUrlParameter(location.href, "fdSearchName");
	if(fdSearchName != "" && fdSearchName != null){
		document.getElementsByName("fdSearchName")[0].value = fdSearchName;
	}
}

resetRadio();

//搜索方法
function simpleSearch(){
	var url = location.href;
	var fdSearchName = document.getElementsByName("fdSearchName")[0].value;
	url = Com_SetUrlParameter(url, "fdSearchName", fdSearchName.replace(/\s*/ig, ''));
	window.location.href = url;
}

//重置方法
function doReset(){
	var url = location.href;
	url = Com_SetUrlParameter(url, "fdSearchName", "");
	window.location.href = url;
}

</script>