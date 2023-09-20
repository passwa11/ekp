
function commJs(){
	if((typeof XML_GetNodes=='undefined')|| !(XML_GetNodes instanceof Function)){
		var sapEkpPath = Com_Parameter.ContextPath+"tic/core/resource/js/sapEkp.js";
		document.writeln("<script src="+sapEkpPath+"></script>");
	}

	if(typeof EkpCommonFormEvent=='undefined'|| !(EkpCommonFormEvent instanceof Function)){
		var jcommonFormEventPath = Com_Parameter.ContextPath+"tic/core/resource/js/commonFormEvent.js";
		document.writeln("<script src="+jcommonFormEventPath+"></script>");
	}
}