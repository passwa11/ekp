function bookticketOfPlane(obj){
	var props = $(obj).attr("props");
	var jsonObj = JSON.parse(props.replace(/\'/ig,"\""));
	var detailNo ='0'; //默认在主表
	if(typeof DocListFunc_GetParentByTagName=='function'&&jsonObj["matchType"]=="2"){
		detailNo=DocListFunc_GetParentByTagName("TR",obj).rowIndex-1;
	}
	var fdId = Com_GetUrlParameter(window.location.href,'fdId');
	var planeUrl=Com_Parameter.ContextPath+"fssc/ctrip/fssc_ctrip_app_message/fsscCtripAppMessage.do?method=bookPlaneTicketOfPlane";
	planeUrl+="&docNumberId="+jsonObj["docNumberId"];
	var url=window.location.href;
	var paths=url.split('/');
	var modelType='';
	if(paths.length>0){
		var path=paths[paths.length-1];
		var model=path.substring(0,path.indexOf('.do'));
		modelType=model.substring(0,1).toUpperCase()+model.substring(1);
	}
	window.open(planeUrl+"&modelType="+modelType+"&fdId="+fdId+"&detailNo="+detailNo);
}
function bookticketOfHotel(obj){
	var props = $(obj).attr("props");
	var jsonObj = JSON.parse(props.replace(/\'/ig,"\""));
	var detailNo ='0'; //默认在主表
	if(typeof DocListFunc_GetParentByTagName=='function'&&jsonObj["matchType"]=="2"){
		detailNo=DocListFunc_GetParentByTagName("TR",obj).rowIndex-1;
	}
	var fdId = Com_GetUrlParameter(window.location.href,'fdId');
	var hotelUrl=Com_Parameter.ContextPath+"fssc/ctrip/fssc_ctrip_app_message/fsscCtripAppMessage.do?method=bookTicketOfHotel";
	hotelUrl+="&docNumberId="+jsonObj["docNumberId"];
	var url=window.location.href;
	var paths=url.split('/');
	var modelType='';
	if(paths.length>0){
		var path=paths[paths.length-1];
		var model=path.substring(0,path.indexOf('.do'));
		modelType=model.substring(0,1).toUpperCase()+model.substring(1);
	}
	window.open(hotelUrl+"&modelType="+modelType+"&fdId="+fdId+"&detailNo="+detailNo);
}
function bookticketOfTrain(obj){
	var props = $(obj).attr("props");
	var jsonObj = JSON.parse(props.replace(/\'/ig,"\""));
	var detailNo ='0'; //默认在主表
	if(typeof DocListFunc_GetParentByTagName=='function'&&jsonObj["matchType"]=="2"){
		detailNo=DocListFunc_GetParentByTagName("TR",obj).rowIndex-1;
	}
	var fdId = Com_GetUrlParameter(window.location.href,'fdId');
	var trainUrl=Com_Parameter.ContextPath+"fssc/ctrip/fssc_ctrip_app_message/fsscCtripAppMessage.do?method=bookTicketOfTrain";
	trainUrl+="&docNumberId="+jsonObj["docNumberId"];
	var url=window.location.href;
	var paths=url.split('/');
	var modelType='';
	if(paths.length>0){
		var path=paths[paths.length-1];
		var model=path.substring(0,path.indexOf('.do'));
		modelType=model.substring(0,1).toUpperCase()+model.substring(1);
	}
	window.open(trainUrl+"&modelType="+modelType+"&fdId="+fdId+"&detailNo="+detailNo);
}
//移动端
function bookticketOfPlaneMobile(obj){
	var props = $(obj).attr("props");
	var jsonObj = JSON.parse(props.replace(/\'/ig,"\""));
	var detailNo ='0'; //默认在主表
	if(typeof DocListFunc_GetParentByTagName=='function'&&jsonObj["matchType"]=="2"){
		detailNo=DocListFunc_GetParentByTagName("TR",obj).rowIndex-1;
	}
	var fdId = Com_GetUrlParameter(window.location.href,'fdId');
	var planeUrl=Com_Parameter.ContextPath+"fssc/ctrip/fssc_ctrip_app_message/fsscCtripAppMessage.do?method=bookPlaneTicketOfPlaneMobile";
	planeUrl+="&docNumberId="+jsonObj["docNumberId"];
	var url=window.location.href;
	var paths=url.split('/');
	var modelType='';
	if(paths.length>0){
		var path=paths[paths.length-1];
		var model=path.substring(0,path.indexOf('.do'));
		modelType=model.substring(0,1).toUpperCase()+model.substring(1);
		if("FsscFeeMobile"==modelType){
			modelType="FsscFeeMain";
		}
	}
	window.open(planeUrl+"&modelType="+modelType+"&fdId="+fdId+"&detailNo="+detailNo);
}
function bookticketOfHotelMobile(obj){
	var props = $(obj).attr("props");
	var jsonObj = JSON.parse(props.replace(/\'/ig,"\""));
	var detailNo ='0'; //默认在主表
	if(typeof DocListFunc_GetParentByTagName=='function'&&jsonObj["matchType"]=="2"){
		detailNo=DocListFunc_GetParentByTagName("TR",obj).rowIndex-1;
	}
	var fdId = Com_GetUrlParameter(window.location.href,'fdId');
	var hotelUrl=Com_Parameter.ContextPath+"fssc/ctrip/fssc_ctrip_app_message/fsscCtripAppMessage.do?method=bookTicketOfHotelMobile";
	hotelUrl+="&docNumberId="+jsonObj["docNumberId"];
	var url=window.location.href;
	var paths=url.split('/');
	var modelType='';
	if(paths.length>0){
		var path=paths[paths.length-1];
		var model=path.substring(0,path.indexOf('.do'));
		modelType=model.substring(0,1).toUpperCase()+model.substring(1);
		if("FsscFeeMobile"==modelType){
			modelType="FsscFeeMain";
		}
	}
	window.open(hotelUrl+"&modelType="+modelType+"&fdId="+fdId+"&detailNo="+detailNo);
}
function bookticketOfTrainMobile(obj){
	var props = $(obj).attr("props");
	var jsonObj = JSON.parse(props.replace(/\'/ig,"\""));
	var detailNo ='0'; //默认在主表
	if(typeof DocListFunc_GetParentByTagName=='function'&&jsonObj["matchType"]=="2"){
		detailNo=DocListFunc_GetParentByTagName("TR",obj).rowIndex-1;
	}
	var fdId = Com_GetUrlParameter(window.location.href,'fdId');
	var trainUrl=Com_Parameter.ContextPath+"fssc/ctrip/fssc_ctrip_app_message/fsscCtripAppMessage.do?method=bookTicketOfTrainMobile";
	trainUrl+="&docNumberId="+jsonObj["docNumberId"];
	var url=window.location.href;
	var paths=url.split('/');
	var modelType='';
	if(paths.length>0){
		var path=paths[paths.length-1];
		var model=path.substring(0,path.indexOf('.do'));
		modelType=model.substring(0,1).toUpperCase()+model.substring(1);
		if("FsscFeeMobile"==modelType){
			modelType="FsscFeeMain";
		}
	}
	window.open(trainUrl+"&modelType="+modelType+"&fdId="+fdId+"&detailNo="+detailNo);
}
