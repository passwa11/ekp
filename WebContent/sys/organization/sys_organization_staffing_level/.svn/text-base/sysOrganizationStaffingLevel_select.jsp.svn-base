<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="/resource/jsp/htmlhead.jsp"%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title><bean:message key="sysOrganizationStaffingLevel.select.title"  bundle="sys-organization"/></title>
  
     <link href="css/staffingLevelSelect.css" rel="stylesheet" type="text/css"/>
</head>
<script type="text/javascript">
Com_IncludeFile("jquery.js");

var sortClasses=new Array("btn-assort","btn-assort assort-Up","btn-assort assort-Dn");
var sortType = 0;

function checkData(){
	var value = $("#staffingLevelValue").val();
	if(value!=''){
		if(isNaN(value)||value<0){
			alert("<bean:message key="sysOrganizationStaffingLevel.fdLevel.validate" bundle="sys-organization"/>");
			return false;
		}else{
			return true;
		}
	}else{
		return true;
	}
}

function doSearch(event){
	var e = event || window.event || arguments.callee.caller.arguments[0];        
    if(e && e.keyCode==13){ // enter 键
    	setStaffingLevelList();
    }
}

function resetStaffingLevelContent(){
	var staffing_level_tbody = $("staffing_level_tbody");
	staffing_level_tbody.clear();
}

function sortStaffingLevelList(){
	sortType = (sortType+1)%3;
	//alert(sortClasses[sortType]);
	$("#a_sort").attr("class", sortClasses[sortType]);
	setStaffingLevelList();
}

function setStaffingLevelList(){
	if(!checkData()){
		return;
	}
	var staffing_level_tbody = $("#staffing_level_tbody");
	staffing_level_tbody.html("");
	var staffingLevelName = $("#staffingLevelName").val();
	staffingLevelName = encodeURI(staffingLevelName);
	var staffingLevelValue = $("#staffingLevelValue").val();
	
	var url = '<c:url value="/sys/organization/sys_organization_staffing_level/sysOrganizationStaffingLevel.do?method=listJson"/>&name='+staffingLevelName+'&level='+staffingLevelValue+'&sortType='+sortType;
	$.ajax(
	{
		url : url,
		dataType : 'json',
		data : '',
		jsonp : 'callback',
		success : function(result) {
			setStaffingLevelTbody(result);
		},
		timeout : 5000
	});
}

function setStaffingLevelTbody(data){
	var staffing_level_tbody = $("#staffing_level_tbody");
	//staffing_level_tbody.add();
	if(data.length == 0){
		$("#div_noData").show();
		$("#div_data").hide();
	}else{
		$("#div_noData").hide();
		$("#div_data").show();
	}

	$.each(data, function(i,item){
		var tr = $("<tr/>"); 
		$("<td></td>").text(item.name).appendTo(tr);
		$("<td></td>").text(item.level).appendTo(tr);

		var returnValue = {"fdId":item.id,"fdName":item.name};
		//tr.click(value,doSetValue);
		tr.bind("click",returnValue,doSetValue);
		tr.appendTo(staffing_level_tbody);
		});
	}


	function windowclose() {
		//debugger;
	    var browserName = navigator.appName;
	    if (browserName=="Netscape") {
	        window.open('', '_self', '');
	        window.close();
	    }
	    else {
	        if (browserName == "Microsoft Internet Explorer"){
	            window.opener = "whocares";
	            window.opener = null;
	            window.open('', '_top'); 
	            window.close();
	        }
	    }
	}

	function doSetValue(event) {
		//debugger;
	    if (navigator.userAgent.indexOf("Chrome") > 0 || navigator.userAgent.indexOf("Firefox") > 0) {
	        parent.window.opener.setValue(window.opener, event.data);
	    }
	    else {
	        top.returnValue = event.data;
	    }
	    Com_CloseWindow();
	}


	function doSubmit(){
		var els = document.getElementsByName("List_Selected");
		var v = "";
		for(var i=0;i<els.length;i++){
			if(els[i].checked){
				v = els[i].value;
			}
		}
		if(v!=""){
			a = v.split("_@_");
			var o = {fdId:a[0],fdName:a[1]};
			returnValue = o;
		}

	    top.returnValue = o;
	    doSetValue(o);
		//Com_CloseWindow();
	}

	function doSelect(tr_obj){
		tr_obj.getElementsByTagName("input")[0].checked=true;
	}
			
	//window.onload = resizeWindowHeight();
	window.onload = function(){
		setStaffingLevelList();
		
	}
</script>

<body>

    <!--地址本职业级别 Starts-->
    <div class="lui-address-prolevel-wrapper">
        <div class="lui-address-prolevel-heading">
            <h4 class="lui-address-prolevel-heading-title"><i class="lui-address-prolevel-icon lui-address-prolevel-icon-info"></i>
            	<bean:message bundle="sys-organization" key="sysOrganizationStaffingLevel.base.info"/></h4>
        </div>
        <div class="lui-address-prolevel-content" style="width:100%">
            <div class="lui-address-prolevel-filterbar" style="width:100%">
                <form class="lui-address-prolevel-form-inline">
                    <span class="lui-address-prolevel-form-group">
                        <label for="exampleInputName1"><bean:message bundle="sys-organization" key="sysOrganizationStaffingLevel.fdName"/></label>
                        <input id="staffingLevelName" type="text" class="lui-address-prolevel-form-control" placeholder="" onkeydown="doSearch(event);"/>
                    </span>
                    <span class="lui-address-prolevel-form-group">
                        <label for="exampleInputName2"><bean:message bundle="sys-organization" key="sysOrganizationStaffingLevel.fdLevel"/></label>
                        <input id="staffingLevelValue" type="text" class="lui-address-prolevel-form-control" placeholder="" onkeydown="doSearch(event);"/>
                    </span>
                    <a href="javascript:void(0);" class="lui-address-prolevel-btn lui-address-prolevel-btn-primary" onclick="setStaffingLevelList();"><bean:message key="button.search"/></a>
                </form>
            </div>

            <div style="width:100%">
                <table class="lui-address-prolevel-table" style="width:100%">
                    <thead>
                        <tr>
                            <th><bean:message bundle="sys-organization" key="sysOrganizationStaffingLevel.fdName"/></th>
                            <th><a id="a_sort" onclick="sortStaffingLevelList();" class="btn-assort" style="cursor: default;"><bean:message bundle="sys-organization" key="sysOrganizationStaffingLevel.fdLevel"/></a></th>
                        </tr>
                    </thead>
                </table>
            </div>
            <div id="div_data" class="lui-address-prolevel-filterBody">
                <table class="lui-address-prolevel-table">
                    
                    <tbody id="staffing_level_tbody">
                        

                    </tbody>
                </table>
            </div>
            
            <div id="div_noData" class="lui-address-prolevel-filterBody" style="display:none;">
                <div class="lui-address-prolevel-noData">
                    <div class="noData-img"></div>
                    <p class="noData-title"><bean:message bundle="sys-organization" key="sysOrganizationStaffingLevel.tips"/></p>
                </div>
            </div>

            <div class="lui-address-prolevel-btn-group-inline lui-address-prolevel-text-center">
                <a href="javascript:void(0);" class="lui-address-prolevel-btn lui-address-prolevel-btn-primary" onclick="doSetValue({data:{}});"><bean:message bundle="sys-organization" key="button.clear" /></a>
                <a href="javascript:void(0);" class="lui-address-prolevel-btn lui-address-prolevel-btn-muted" onclick="Com_CloseWindow();"><bean:message bundle="sys-organization" key="button.cancel" /></a>
            </div>
        </div>
    </div>
    <!--地址本职业级别 Ends-->

</body>
</html>
