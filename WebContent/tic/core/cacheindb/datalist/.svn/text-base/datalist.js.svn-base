function isMobile() {
   var sUserAgent = navigator.userAgent.toLowerCase(); //浏览器的用户代理设置为小写，再进行匹配
   var isIpad = sUserAgent.match(/ipad/i) == "ipad"; //或者利用indexOf方法来匹配
   var isIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
   var isMidp = sUserAgent.match(/midp/i) == "midp"; //移动信息设备描述MIDP是一套Java应用编程接口，多适用于塞班系统
   var isUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4"; //CVS标签
   var isUc = sUserAgent.match(/ucweb/i) == "ucweb";
   var isAndroid = sUserAgent.match(/android/i) == "android";
   var isCe = sUserAgent.match(/windows ce/i) == "windows ce";
   var isWM = sUserAgent.match(/windows mobil/i) == "windows mobil";
   if (isIpad || isIphoneOs || isMidp || isUc7 || isUc || isAndroid || isCe || isWM) {
     return true;
   } else {
     return false;
   }
}

var _dialogObject=null;
function Dialog_DataList(dialogObject){
	if(!isMobile()){
		var url =  Com_Parameter.ContextPath + "tic/core/cacheindb/datalist/datalist_main.jsp" + "?s_css="+Com_Parameter.Style;
		var width = dialogObject.width==null?800:dialogObject.width;
		var height = dialogObject.height==null?480:dialogObject.height;
		var left = (screen.width-width)/2;
		var top = (screen.height-height)/2-10;
		if(window.showModalDialog){
			var winStyle = "resizable:1;scroll:1;dialogwidth:"+width+"px;dialogheight:"+height+"px;dialogleft:"+left+";dialogtop:"+top;
			window.showModalDialog(url, dialogObject, winStyle);
			_dialogObject = dialogObject;
			if(dialogObject.zoneType=="detail"){
				if(dialogObject.detailId){
					_setDetailItemValuesById(dialogObject);
				}else{
					_setDetailItemValues(dialogObject);
				}
			}else{
				_setMainItemValues(dialogObject);
			}
		}else{
			if(dialogObject.zoneType=="detail"){
				var optTB=document.getElementById("TABLE_DL_"+dialogObject.detailId);
				$(optTB).find("span[class='optStyle opt_del_style']").each(function(){
					var tbInfo = DocList_TableInfo[optTB.id];
					var optTR = DocListFunc_GetParentByTagName("TR",this);
					var rowIndex = Com_ArrayGetIndex(optTB.rows, optTR);
					dialogObject.currRowIndex = rowIndex;
					console.log("rowIndex::"+rowIndex);
				});
			}
			var winStyle = "resizable=1,scrollbars=1,width="+width+",height="+height+",left="+left+",top="+top+",dependent=yes,alwaysRaised=1";
			_dialogObject = dialogObject;
			Com_Parameter.Dialog = dialogObject;
			window.open(url, "_blank", winStyle);
			/*
			_dialogObject = dialogObject;
			TINY.box.show({iframe:Com_Parameter.ContextPath +"/third/common/datalist/g_pageDataList.jsp",width:800,height:500,opacity:10,topsplit:30});
			*/
		}
	}else{

			var srcEl = window.event.srcElement;
			var tbl = $(srcEl).closest('.muiSimple');
			var text = $(tbl).find('.muiDetailTableNo').children('span').text();
			if(text){
				var p1 = "第 ",p2=" 行";
				text = text.substring(p1.length);
				text = text.substring(0,text.length-p2.length);
				//console.log(text);
				dialogObject.currRowIndex = parseInt(text);
			}

		_dialogObject = dialogObject;
		TINY.box.show({iframe:Com_Parameter.ContextPath +"/tic/core/cacheindb/datalist/m_pageDataList.jsp",width:260,height:390,opacity:10,topsplit:30});
	}
	
}

function _closeTinybox(){
	var dialogObject = _dialogObject;
	if(dialogObject.zoneType=="detail"){
		if(dialogObject.detailId){
			_setDetailItemValuesInMobileById(dialogObject);
		}
	}else{
		_setMainItemValues(dialogObject);
	}
	TINY.box.hide();
}

function _closeCallback(rtnData){
	_dialogObject.rtnData=rtnData;
	var dialogObject = _dialogObject;
	if(dialogObject.zoneType=="detail"){
		if(dialogObject.detailId){
			_setDetailItemValuesInMobileById(dialogObject);
		}else{
			_setDetailItemValues(dialogObject);
		}
	}else{
		_setMainItemValues(dialogObject);
	}

}

function _setDetailItemValuesInMobileById(dialogObject){
	var arrValues = dialogObject.rtnData;
	if(!arrValues){
		return;
	}
	if(arrValues && arrValues.length==0){
		//清除
		var emptyO = {};
		for(var i=0;i<dialogObject.flds.length;i++){
			emptyO[dialogObject.flds[i]["xformId"]]="";
		}
		arrValues[0]=emptyO;
	}

	var detailId = "TABLE_DL_"+dialogObject.detailId;
    var optTB = document.getElementById(detailId);
	var tbInfo = DocList_TableInfo[optTB.id];

	var currRowIndex = dialogObject.currRowIndex;
	//console.log("dialogObject.currRowIndex::"+dialogObject.currRowIndex);
	if(typeof currRowIndex=="undefined"){
		currRowIndex = tbInfo.lastIndex-1; 
	}
	
	//console.log("tbInfo.lastIndex::"+tbInfo.lastIndex);
	for(var j=0;j<dialogObject.flds.length;j++){
		if(dialogObject.flds[j]["fldType"]!=null&&dialogObject.flds[j]["format"]!=null){
			if(dialogObject.flds[j]["fldType"]=="Date"){
				for(var k=0;k<arrValues.length;k++){	
					arrValues[k][dialogObject.flds[j]["xformId"]]=arrValues[k][dialogObject.flds[j]["xformId"]].substring(0,10);
				}
			}
		}
	}
	for(var i=0;i<arrValues.length;i++){	
		_setDetailElementValuesInMobile(arrValues[i],(currRowIndex-1)+i,dialogObject.detailId);
	}
}
function _setDetailElementValuesInMobile(fieldValues,index,detailId){
	if(fieldValues!=null){
		for(var name in fieldValues){
			if(name==""||name=="undefined"||name==null){
				continue;
			}
			var value = fieldValues[name];
			var elname="extendDataFormInfo.value("+detailId+"."+index+"."+name+")";
		
			var field =GetXFormFieldById_ext(elname, true);	
			if(!field){
				continue;
			}
			if(!isMobile()){
				setValueByFormId(elname, value,_dialogObject.bindObj);
				if(_dialogObject.callback){
					//特殊定制回调
					if(elname.indexOf("text")!=-1){
						var rowsContext=[{fdId:"name",fdSubject:value}];//context.fdId && context.fdSubject
						var o ={"rowsContext":rowsContext};
						_dialogObject.callback(_dialogObject.bindObj,o);
					}
				}
			}else{
				/*
				var el = document.getElementsByName(elname)[0];
				console.log(el)
				if(!el){
					continue;
				}
				el.value=value;
				*/
			}			
		}
	}
}

function _setDetailItemValuesById(dialogObject){
	var arrValues = dialogObject.rtnData;
	if(!arrValues){
		return;
	}
	if(arrValues && arrValues.length==0){
		//清除
		var emptyO = {};
		for(var i=0;i<dialogObject.flds.length;i++){
			emptyO[dialogObject.flds[i]["xformId"]]="";
		}
		arrValues[0]=emptyO;
	}

	//var currRowIndex = Com_ArrayGetIndex(optTB.rows, optTR); 
	var currRowIndex =0;
	var detailId = "TABLE_DL_"+dialogObject.detailId;
	var optTB=document.getElementById(detailId);
	$(optTB).find("span[class='optStyle opt_del_style']").each(function(){
		var tbInfo = DocList_TableInfo[optTB.id];
		var optTR = DocListFunc_GetParentByTagName("TR",this);
		var rowIndex = Com_ArrayGetIndex(optTB.rows, optTR);
		currRowIndex = rowIndex;
	});
	
	var tbInfo = DocList_TableInfo[optTB.id];
	var addCount = arrValues.length-(tbInfo.lastIndex-1);
	
	if(addCount>0){
		for(var i=0;i<addCount;i++){
			DocList_AddRow(optTB);
		}
	}
	for(var j=0;j<dialogObject.flds.length;j++){
		if(dialogObject.flds[j]["fldType"]!=null&&dialogObject.flds[j]["format"]!=null){
			if(dialogObject.flds[j]["fldType"]=="Date"){
				for(var k=0;k<arrValues.length;k++){	
					arrValues[k][dialogObject.flds[j]["xformId"]]=arrValues[k][dialogObject.flds[j]["xformId"]].substring(0,10);
				}
			}
		}
	}
	for(var i=0;i<arrValues.length;i++){	
		_setDetailElementValuesInMobile(arrValues[i],(currRowIndex-1)+i,dialogObject.detailId);
	}
}

function _setDetailItemValues(dialogObject){
	var arrValues = dialogObject.rtnData;
	if(!arrValues){
		return;
	}
	if(arrValues && arrValues.length==0){
		//清除
		var emptyO = {};
		for(var i=0;i<dialogObject.flds.length;i++){
			emptyO[dialogObject.flds[i]["xformId"]]="";
		}
		arrValues[0]=emptyO;
	}
	//在明细表中
	var optTR = DocListFunc_GetParentByTagName("TR");
	var optTB = DocListFunc_GetParentByTagName("TABLE", optTR);
	var currRowIndex = Com_ArrayGetIndex(optTB.rows, optTR); 
	var tbInfo = DocList_TableInfo[optTB.id];
	var addCount = currRowIndex+arrValues.length-tbInfo.lastIndex;
	if(addCount>0){
		for(var i=0;i<addCount;i++){
			DocList_AddRow(optTB);
		}
	}
	for(var i=0;i<arrValues.length;i++){
		_setElementValues(arrValues[i],(currRowIndex-1)+i,true);
	}
}

function _setMainItemValues(dialogObject){
	var arrValues = dialogObject.rtnData;
	if(!arrValues){
		return;
	}
	if(arrValues && arrValues.length==0){
		//清除
		var emptyO = {};
		for(var i=0;i<dialogObject.flds.length;i++){
			emptyO[dialogObject.flds[i]["xformId"]]="";
		}
		arrValues[0]=emptyO;
	}
	if(arrValues.length==1){
		_setElementValues(arrValues[0],0);
	}else{
		var joinO = {};
		for(var i=0;i<arrValues.length;i++){
			for(var j=0;j<dialogObject.flds.length;j++){
				if(i==0){
					joinO[dialogObject.flds[j]["xformId"]]="";
				}
				if(i>0){
					joinO[dialogObject.flds[j]["xformId"]]+=";";
				}
				joinO[dialogObject.flds[j]["xformId"]]+=arrValues[i][dialogObject.flds[j]["xformId"]];
			}
		}
		arrValues[0] = joinO;
		_setElementValues(arrValues[0],0);
	}

}

function _setElementValues(fieldValues,index,detailflag){
	if(fieldValues!=null){
		for(var name in fieldValues){
			if(name==""||name=="undefined"||name==null){
				continue;
			}
			var value = fieldValues[name];
			//var field = GetXFormFieldById(name,true)[index];
			var field =GetXFormFieldById_ext(name, true);			
			//console.log(field);
			if(!field){
				continue;
			}
			if(!isMobile()){
				setValueByFormId(name, value,_dialogObject.bindObj);
				if(_dialogObject.callback){
					//特殊定制回调
					if(name.indexOf("text")!=-1){
						var rowsContext=[{fdId:"name",fdSubject:value}];//context.fdId && context.fdSubject
						var o ={"rowsContext":rowsContext};
						_dialogObject.callback(_dialogObject.bindObj,o);
					}
				}

				//var elname="extendDataFormInfo.value("+name+")";
				//var el = document.getElementsByName(elname)[0];
				//console.log(elname+":"+el.value)
				/*
				switch(field.tagName){
						case "INPUT":
							if(field.type=="radio"){
								if(detailflag){
									var fie = GetXFormFieldById([index]+"."+name,true).length;
									for(var i=0;i<fie;i++){
										field = GetXFormFieldById([index]+"."+name,true)[i];
										if(field.value==value){
											field.checked = true;
										}
									}
								}else{
									var fie = GetXFormFieldById(name,true).length;
									for(var i=0;i<fie;i++){
										field = GetXFormFieldById(name,true)[i];
										if(field.value==value){
											field.checked = true;
										}
									}
								}
								break;
							}
							if(field.type=="checkbox"){
								field.checked = (field.value==value);
								break;
							}
							if(field.type=="text" || field.type=="hidden"){
								field.value = value;
								break;
							}
						case "TEXTAREA":
							field.value = value;
							break;
						case "SELECT":
							for(var j=0; j<field.options.length; j++){
								if(field.options[j].value==value)
									field.options[j].selected = true;
							}
							break;
					}
					*/
			}else{
				var elname="extendDataFormInfo.value("+name+")";
				var el = document.getElementsByName(elname)[0];
				if(el){
					el.value=value;
				}
			}
		
		}
	}
}

function _getDetailElValue(dId,elName){
	if(isMobile()){
		return _getDetailElValueInMobile(dId,elName);
	}
	var detailId = "TABLE_DL_"+dId;
	var optTR = DocListFunc_GetParentByTagName("TR");
	var optTB = DocListFunc_GetParentByTagName("TABLE", optTR);
	var index = Com_ArrayGetIndex(optTB.rows, optTR); 
	var elname="extendDataFormInfo.value("+dId+"."+(index-1)+"."+elName+")";
	var el = document.getElementsByName(elname)[0];
	if(el){
		return el.value;
	}else{
		return "";
	}
}

function _getDetailElValueInMobile(dId,elName){
	var detailId = "TABLE_DL_"+dId;
    var optTB = document.getElementById(detailId);
	var tbInfo = DocList_TableInfo[optTB.id];
	var currRowIndex = tbInfo.lastIndex-1; 

	var srcEl = window.event.srcElement;
	var tbl = $(srcEl).closest('.muiSimple');
	var text = $(tbl).find('.muiDetailTableNo').children('span').text();
	if(text){
		var p1 = "第 ",p2=" 行";
		text = text.substring(p1.length);
		text = text.substring(0,text.length-p2.length);
		//console.log(text);
		currRowIndex = parseInt(text);
	}

	//console.log("tbInfo.lastIndex::"+tbInfo.lastIndex);
	var elname="extendDataFormInfo.value("+dId+"."+(currRowIndex-1)+"."+elName+")";
	var el = document.getElementsByName(elname)[0];
	if(el){
		return el.value;
	}else{
		return "";
	}

}

function _getMainElValue(elName){
	var name="extendDataFormInfo.value("+elName+")";
	var el = document.getElementsByName(name)[0];
	if(el){
		return el.value;
	}
	return "";
}
