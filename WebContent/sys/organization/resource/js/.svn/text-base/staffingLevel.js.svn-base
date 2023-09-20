/***********************************************/
Com_RegisterFile("staffingLevel.js");
//Com_IncludeFile('keydata_dialog.js','/km/keydata/resources/js/',null,true);
Com_IncludeFile('dialog.js');
/**********************************************************/

var idFieldName,nameFieldName,fn;

function __CallStaffingLevelOnValueChange(idField, nameField, fn) {
	var rtn = [];
	var objs = __GetStaffingLevelFields(idField, nameField);
	for (var i = 0; i < objs.length; i ++) {
		rtn[i] = objs[i].value;
	}
	for (var i = 0; i < objs.length; i ++) {
		objs[i].style.display = 'none';
		objs[i].style.display = '';
	}
	//debugger;
	if (fn) {fn(rtn, objs);}
	
}

function __GetStaffingLevelFields(idField, nameField) {
	var rtn = [];
	rtn[0] = typeof(idField) == 'string' ? document.getElementsByName(idField)[0] : idField;
	rtn[1] = typeof(nameField) == 'string' ? document.getElementsByName(nameField)[0] : nameField;
	return rtn;
}

function __openStaffingLevelWindow(propertyId,propertyName,keydataType,treeTitle,winTitle,fn){
	Dialog_Tree(false, propertyId, propertyName, ',', 'kmStaffingLevelExtendService&parentId=!{value}&keydataType='+keydataType, treeTitle, null, 
			fn, '${param.fdId}', null, null, winTitle);
}

function selectStaffingLevel(idFieldName,nameFieldName,fn){
	this.idFieldName = idFieldName;
	this.nameFieldName = nameFieldName;
	this.fn = fn;
	var url = Com_Parameter.ResPath+"jsp/frame.jsp";
	url = Com_SetUrlParameter(url, "url",Com_Parameter.ContextPath+"sys/organization/sys_organization_staffing_level/sysOrganizationStaffingLevel.do?method=select");
	//var rtnVal = showModalDialog(url, null, "dialogWidth:400px;dialogHeight:400px;status:no;");
	var rtnVal = myShowModalDialog(url, 600, 500);
	
	
	//seajs('lui/dialog',function(dialog){dialog.iframe});
	
}


function setValue(obj,returnValue){
	if(returnValue){
		var idField = document.getElementsByName(idFieldName)[0];
		var nameField = document.getElementsByName(nameFieldName)[0];
		if(returnValue["fdId"]==null){
			idField.value="";
			nameField.value="";
			return ;
		}
		nameField.value=returnValue["fdName"];
		idField.value=returnValue["fdId"];
		if(fn){
			fn();
		}
	}
}

function myShowModalDialog(url, width, height) {
    if (navigator.userAgent.indexOf("Chrome") > 0 && navigator.userAgent.indexOf("Edge") < 0) {
        //window.returnCallBackValue354865588 = fn;
        var paramsChrome = 'height=' + height + ', width=' + width + ', top=' + (((window.screen.height - height) / 2) - 50) +
            ',left=' + ((window.screen.width - width) / 2) + ',toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no';
        window.open(url, "newwindow", paramsChrome);
    }
    else {
        var params=null;
        var tempReturnValue=null;
        if(window.showModalDialog == undefined){
        	params= 'width=' + width + 'px,height=' + height + 'px,sstatus=1,help=0,resizable=1'+ ', top=' + (((window.screen.height - height) / 2) - 50) +
            ',left=' + ((window.screen.width - width) / 2) ;
        	tempReturnValue=window.open(url,"newwindow",'modal=yes,'+params);
        	tempReturnValue.focus();
        	setValue(window, tempReturnValue);
        }else{
        	params= 'dialogWidth:' + width + 'px;dialogHeight:' + height + 'px;sstatus:1; help:0; resizable:1'
        	tempReturnValue=window.showModalDialog(url, params);
        	setValue(window, tempReturnValue);
        }
       
    }
}

function getStaffingLevelDivPos(evt,showObj){
	var sWidth=showObj.width();var sHeight=showObj.height();
	x=evt.pageX;
	y=evt.pageY+10;
	if(y+sHeight>$(window).height()){
		y-=sHeight;
	}
	if(x+sWidth>$(document.body).outerWidth(true)){
		x-=sWidth;
	}
	return {"top":y,"left":x};
}

window.ajax_getStaffingLevelTypes = function(tableObj){
	var dataTypes_select = tableObj.find('select')[0];
	$(tableObj.find('select')[0]).empty();
	$.ajax({
		url : "/km/keydata/base/kmStaffingLevelBase.do?method=getStaffingLevelTypes",
		contentType : false,
		processData : false,
		type : "GET",
		success : function(data) {
			
			$.each($.parseJSON(data), function(i,item){
				$("<option />").attr("value",item.type).text(item.name).appendTo(dataTypes_select);
			});
			
			//alert(tableObj.find('select')[0].value);
			ajax_getStaffingLevels(tableObj);
			}
		});
};

function buildDivContent(divObj,keydataId){
	var html = "";
	$.ajax({
		url : "/km/keydata/base/kmStaffingLevelBase.do?method=getStaffingLevelJson&keydataId="+keydataId,
		contentType : false,
		processData : false,
		type : "GET",
		success : function(data) {
		//debugger;
		//	$.each($.parseJSON(data), function(i,item){
				var dataJson = $.parseJSON(data);
				var detailUrl = dataJson.detailUrl;
				var attrs = dataJson.attrs;
				//debugger;
					$.each(attrs, function(j,att_item){
						html += att_item.title+"："+att_item.value+"<br>";
					}
				);
					html += "<a href='"+detailUrl+"' target='_blank'>更多"+"</a><br>";
					html += "<a href=\"javascript:void(0);\" onclick=\"hideStaffingLevelDiv(this);\">关闭</a>";
		//	});
			$(divObj).html(html);
			//alert(tableObj.find('select')[0].value);
			}
		});
	
}

function showStaffingLevelDiv(obj,keydataId){
	
	var divObj = $(obj).parent().find("div").get(0);
	buildDivContent(divObj,keydataId);
	$(divObj).css(getStaffingLevelDivPos(window.event,$(divObj))).css('display','block');
}


function hideStaffingLevelDiv(obj){
	//alert($(obj).parent().css('display'));
	$(obj).parent().css('display','none');
	
}

