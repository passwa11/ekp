function selectPrintTemp(url,fdModelId,fdModelName,formId,isSysPrint){
	var printTemps = getMultiPrintTemp(fdModelId,fdModelName)
	if(printTemps && printTemps.length > 1 && isSysPrint){//多个模板
		var dialogUrl = "/sys/print/include/multi_template/dialog.jsp?fdModelId=" + fdModelId + "&fdModelName="+fdModelName;
		seajs.use( [ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
			dialog.iframe(dialogUrl, "选择打印模板", function(data){
				if(data.type == 'ok'){
					switchPrintTemp(url,formId,data.printId);
				}
			}, {width: 750, height: 500})
		});
	}else{
		Com_OpenWindow(url);
	}
}

function switchPrintTemp(url,formId,printId){
	url = Com_SetUrlParameter(url, "m_print", printId);
	url = Com_SetUrlParameter(url, "s_xform", formId);
	Com_OpenWindow(url);
}

function getMultiPrintTemp(fdModelId, fdModelName){
	var printTemps = [];
	var url = Com_Parameter.ContextPath + "sys/print/sys_print_template/sysPrintTemplate.do?method=printData";
	$.ajax({
		type : "POST",
		data : {
			"fdModelId":fdModelId,
			"fdModelName":fdModelName
		},
		url : url,
		async : false,
		success : function(json){
			if(json){
				printTemps = eval('('+json+')');
			}
		}
	});
	return printTemps;
}