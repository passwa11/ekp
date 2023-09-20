// 提交校验
Com_IncludeFile("dialog.js");
Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = function() {
	if(checkHasModule())
		return true;
	else
		return false ;
}
//根据模块名称和模块编码校验是否存在该模块的帮助
function checkHasModule(){
	var fdModuleName = document.getElementsByName("fdModuleName")[0].value ; 
	var fdModuleKey = document.getElementsByName("fdModuleKey")[0].value ; 
	if(fdModuleName != "" && fdModuleName != null){
		var url = "sysHelpMainCheckService&fdModuleName=" + fdModuleName + "&fdModuleKey=" + fdModuleKey;
		var data = new KMSSData(); 
		var isExist = data.AddBeanData(url).GetHashMapArray()[0];
	   	if(isExist["key0"]=='true'){
	   		return true;
	   	}else{
	   		seajs.use([ 'lui/dialog'], function(dialog) {
		   		dialog.alert('<bean:message key="msg.hasExist" bundle="sys-help"/>');
		   	})
	   		return false;
	   	}
	}
}
