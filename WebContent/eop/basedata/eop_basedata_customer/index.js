seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/dialog_common','lang!eop-basedata','lui/util/env'], function($, dialog , topic, dialogCommon,lang,env){
	//下载模板
	window.downloadTemp = function(){
		var url=env.fn.formatUrl(listOption.basePath+'?method=downloadTemp');
		window.open(url);
	}
	//导出
	window.exportCustomer = function(){
		var fdCompanyId = Com_GetUrlParameter(window.location.href,'fdCompanyId');
		fdCompanyId = fdCompanyId?fdCompanyId:'';
		var url=env.fn.formatUrl(listOption.basePath+'?method=exportCustomer&fdCompanyId='+fdCompanyId);
		window.open(url);
	}
	//导入
	window.importCustomer = function(){
		var formName = listOption.modelName.split(".");
		formName = formName[formName.length-1];
		formName = formName.substring(0,1).toLowerCase()+formName.substring(1);
		window.dia = dialog.iframe(
			'/eop/basedata/eop_basedata_customer/eopBasedataCustomerImport.jsp?modelName='+listOption.modelName+"&tempate="+formName,
			lang['message.import.title'],
			function(data){
				if(data){
					topic.publish("list.refresh")
				}
			},{height:600,width:800}
		);
	}
});