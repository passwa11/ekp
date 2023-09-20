<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include file="/sys/profile/resource/template/edit.jsp" sidebar="auto">
<template:replace name="head"> 
	<link href="${KMSS_Parameter_StylePath}button/button.css" rel="stylesheet" type="text/css" />
	<style>
		.tb_normal.newSearchTb > tbody >tr > td{
			padding: 10px 8px!important;
		}
	</style>
	<link rel="stylesheet" href="${KMSS_Parameter_ContextPath}km/review/km_review_ui/dingSuit/css/dataExport.css" />
</template:replace>
<template:replace name="content"> 
<script language="JavaScript">
	Com_IncludeFile("optbar.js|list.js");
	Com_IncludeFile("dialog.js");
</script>
<script type="text/javascript">
//显示从第三行显示到最后
OptBar_IncludeChildWindow = true;
function setHiddenRow(oTable,iRow)//Writed by luhj
{
	show();
	var table =document.getElementById("TB_Condition");
	var countRow = table.rows.length;
	for(iRow;iRow<countRow;iRow++){
    	oTable.rows[iRow].style.display = "";
	}
	show_one_a();
}

//隐藏从第三到最后行

function hiddenRow()//by luhj
{
	show_one();
	//show();
	var oTable = document.getElementById("TB_Condition");
	var table =document.getElementById("TB_Condition");
	var countRow = table.rows.length;
	//var iRow = 3;
	//alert(countRow);
	for(var iRow=2;iRow<countRow;iRow++){
    oTable.rows[iRow].style.display = "none";
	}
	show_a();
}
// 打开窗口默认显示隐藏  by luhj 
window.onload = function(){
	<c:if test="${param.isShowMore=='true'}">
		show();
	</c:if>
	<c:if test="${param.isShowMore!='true'}">
		hiddenRow();
	</c:if>
}
	function getUrl(){

	var reurl;
	var url = null;
	reurl = countUrl() + "request.getRequestURL()";
	
	return reurl;
		}
//隐藏更多查询条件

function show(){
   document.getElementById("b").style.display = "none";
 }
//显示更多查询条件
 function show_one(){
   document.getElementById("b").style.display = "";
 }
 
//隐藏收回 by luhj
 function show_a(){
	   document.getElementById("a").style.display = "none";
	 }
	//显示收回
	 function show_one_a(){
	   document.getElementById("a").style.display = "";
	 }

//iframe自适应高度 by luhj
window.SetWinHeight = function(obj) 
{ 
	var searchIframe=obj; 
		if (document.getElementById) 
		{ 
			if (searchIframe && !window.opera) 
				{ 
				if (searchIframe.contentDocument && searchIframe.contentDocument.body.offsetHeight) 
				searchIframe.height = searchIframe.contentDocument.body.scrollHeight; 
				else if(searchIframe.Document && searchIframe.Document.body.scrollHeight) 
				searchIframe.height = searchIframe.Document.body.scrollHeight; 
				} 
		} 
} 

function exportResult(){
	window.searchIframe.showExportDialog();
}
seajs.use( [ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
	var exportDialogObj = {exportUrl: "${lfn:escapeJs(exportURL)}", exportNum: "${queryPage.totalrows}"};
	console.log("***"+exportDialogObj.exportUrl);
	window.showExportDialog = function() {
		var selectedData = getSelectIndexs();
		if(!selectedData || 0 == selectedData.length){
			dialog.alert("请选择需要导出的数据");
			return;
		}
		exportDialogObj.exportNum=selectedData.length;
		var hasSelected = selectedData.length > 0 ? true : false;
		var url = '/sys/search/search_result_export.jsp?fdModelName=${JsParam.fdModelName}&searchId=${JsParam.searchId}&hasSelected=' + hasSelected;
		dialog.iframe(url, '<bean:message bundle="sys-search" key="search.export.select.column" />',
				function (value) {
		        	exportCallbak(value);
				},
				{width:460,height:500,params:{exportDialogObj:exportDialogObj}}
		);
	}
 	window.exportCallbak = function(returnValue) {
		if(returnValue==null || returnValue==undefined){
			return;  
		}
		var selectedData = getSelectIndexs();
		document.getElementsByName("fdNum")[0].value = selectedData.length;
		document.getElementsByName("fdNumStart")[0].value = returnValue["fdNumStart"];
		document.getElementsByName("fdNumEnd")[0].value = returnValue["fdNumEnd"];
		document.getElementsByName("fdKeepRtfStyle")[0].checked = returnValue["fdKeepRtfStyle"];
		document.getElementsByName("fdColumns")[0].value = returnValue["fdColumns"];
		document.getElementsByName("checkIdValues")[0].value = selectedData.join("|");
		dialog.confirm('<bean:message bundle="sys-search" key="search.export.confirm" />',function(value){
			if(value == true) {
				//console.log("###"+${lfn:escapeJs(exportURL)});
				console.log("###"+exportDialogObj.exportUrl);
				console.log("###"+exportDialogObj.exportNum);
				exportForm.action = exportDialogObj.exportUrl;
				exportForm.submit();
			}
		});
	} 
	
	window.getSelectIndexs = function() {
		var selectedData = [];
		var searchIframe = $("#searchIframe")[0].contentWindow.document
		var $table = $("#List_ViewTable",searchIframe);
		$table.find("[name='List_Selected']:checkbox").each(function(){	
			if (this.checked){
			    selectedData.push(this.value);
			}
		});
		return selectedData;
	}
	
	window.hid = function(obj) {
		if(obj.checked)
			obj.parentNode.parentNode.style.display= "none ";
	}
});
</script>

<center>
<div style="display:none">
	<form name="exportForm" action="" method="POST">	
		<input type="hidden" name="fdColumns" />
		<input name="fdNum" class="inputsgl" style="width:35px" />
		<input name="fdNumStart" class="inputsgl" style="width:35px" />
		<input name="fdNumEnd" class="inputsgl" style="width:35px" />
		<input type="checkbox" value="true" name="fdKeepRtfStyle" checked="checked"/>
		<input name="checkIdValues" class="inputsgl" />
	</form>
</div>
<form name="searchConditionForm" onsubmit="return false;">
<br>
<table width=95% class="tb_normal newSearchTb" id="TB_Condition" align="center" style="border:  #c0c0c0 1px solid">
			<%@ include file="/km/review/km_review_ui/dingSuit/dataExport_condition_entry.jsp"  %>
			<c:if test="${searchConditionInfo.conditionUrl!=null}">
				<c:import url="${searchConditionInfo.conditionUrl}" charEncoding="UTF-8"/>
			</c:if>
</table>
<table>
	<tr style="height: 5px;">
		<td>
		</td>
	</tr>
</table>
<div >
	<div style="width: 95%" align="right" class="ding-search-opt">
			<a id="a" class="com_btn_link" onclick="hiddenRow()"><img src="${KMSS_Parameter_ContextPath}km/review/km_review_ui/dingSuit/images/ding_data_down.png">收回</a>
			<a id="b" class="com_btn_link" onclick="setHiddenRow(document.getElementById('TB_Condition'),2)"><img src="${KMSS_Parameter_ContextPath}km/review/km_review_ui/dingSuit/images/ding_data_add.png">更多查询条件</a>
	</div>
</div>
<%--搜索  --%>
<div style="width: 100%;" class="ding-button-div">
		<ui:button text="${lfn:message('button.search') }" onclick="CommitSearch();"></ui:button>
		&nbsp;&nbsp;
		<ui:button text="${lfn:message('button.reset') }" onclick="resetDisplay();"></ui:button>
		&nbsp;&nbsp;
		<kmss:auth requestURL="/sys/search/search.do?method=export&fdModelName=${param.fdModelName}">
			<ui:button text="${lfn:message('button.export') }" onclick="showExportDialog();" id="exportBtn" style="display: none"></ui:button>
		</kmss:auth>
</div>
    </form>
<div>
    <iframe src="" name="searchIframe" id="searchIframe" align="top" width="97%" Frameborder=No Border=0 Marginwidth=0 Marginheight=0 Scrolling=No onload="Javascript:SetWinHeight(this);">
    </iframe>
</div>

<script>
function emitResize() {
	
	if (window.dialog)
		dialog.content.emit('resize', {
			height: document.body.scrollHeight
		});
	else if (window.frameElement != null && window.frameElement.tagName == "IFRAME") {
		window.frameElement.style.height = document.getElementsByTagName('center')[0].offsetHeight + 70 + "px";
	} else
		return;
	setTimeout(emitResize, 200);
}
function doSearch(){
	CommitSearch();
	emitResize();
}
Com_AddEventListener(window, 'load', doSearch);
</script>
	</template:replace>
</template:include>