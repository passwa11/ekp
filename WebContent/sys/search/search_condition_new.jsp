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
</template:replace>
<template:replace name="content"> 
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

	var loopCount1 = 20;
	//iframe自适应高度 by luhj
	function SetWinHeight(obj)  {
		var searchIframe = obj;
		if (searchIframe && !window.opera) {
			if (searchIframe.contentWindow && searchIframe.contentWindow.document && searchIframe.contentWindow.document.body.scrollHeight) {
				searchIframe.height = searchIframe.contentWindow.document.body.scrollHeight;
			} else if (searchIframe.contentDocument && searchIframe.contentDocument.body.offsetHeight) {
				searchIframe.height = searchIframe.contentDocument.body.scrollHeight;
			} else if (searchIframe.Document && searchIframe.Document.body.scrollHeight) {
				searchIframe.height = searchIframe.Document.body.scrollHeight;
			}
			searchIframe.contentDocument.body.style = "OVERFLOW:SCROLL;OVERFLOW-Y:HIDDEN";
			if ($(searchIframe).height() < 70) {
				loopCount1--;
				if (loopCount1 > 0) {
					setTimeout(SetWinHeight(obj), 200);
				} else {
					// 无法获取高度，使用屏幕高度
					searchIframe.height = top.window.screen.height - 350;
					if(window.console) {
						console.log("无法获取页面高度，使用屏幕高度适配，当前屏幕高度：" + top.window.screen.height + "，适当减少350后的高度为：" + searchIframe.height);
					}
				}
			}
		}
	}

	function exportResult(){
		window.searchIframe.showExportDialog();
	}
</script>

<center>
	
<form name="searchConditionForm" onsubmit="return false;">
<br>
<table width=95% class="tb_normal newSearchTb" id="TB_Condition" align="center" style="border:  #c0c0c0 1px solid">
			<%@ include file="/sys/search/search_condition_entry.jsp"  %>
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
	<div style="width: 95%" align="right" >
			<a id="a" class="com_btn_link" onclick="hiddenRow()"><bean:message bundle="sys-search" key="search.Withdraw" /></a>
			<a id="b" class="com_btn_link" onclick="setHiddenRow(document.getElementById('TB_Condition'),2)"><bean:message bundle="sys-search" key="search.moreSearchCriteria" /></a>
	</div>
</div>
<%--搜索  --%>
<div style="width: 100%;">
		<ui:button text="${lfn:message('button.search') }" onclick="CommitSearch();"></ui:button>
		&nbsp;&nbsp;
		<ui:button text="${lfn:message('button.reset') }" onclick="resetDisplay();"></ui:button>
		&nbsp;&nbsp;
		<kmss:auth requestURL="/sys/search/search.do?method=export&fdModelName=${param.fdModelName}">
			<ui:button text="${lfn:message('button.export') }" onclick="exportResult();" id="exportBtn" style="display: none"></ui:button>
		</kmss:auth>
</div>
    </form>
<div>
    <iframe src="" name="searchIframe" id="searchIframe" align="top" width="97%" Frameborder=No Border=0 Marginwidth=0 Marginheight=0 Scrolling="auto" onload="Javascript:SetWinHeight(this)">
    </iframe>
</div>

<script>
var loopCount2 = 10;
function emitResize() {
	if (window.dialog) {
		dialog.content.emit('resize', {
			height: document.body.scrollHeight
		});
	} else if (window.frameElement != null && window.frameElement.tagName == "IFRAME") {
		window.frameElement.removeAttribute("height");
		window.frameElement.style.height = document.getElementsByTagName('center')[0].offsetHeight + "px";
	} else {
		return;
	}
	if (loopCount2 > 0) {
		setTimeout(emitResize, 200);
	}
	loopCount2--;
}
function doSearch(){
	CommitSearch();
	emitResize();
}
Com_AddEventListener(window, 'load', doSearch);
</script>
	</template:replace>
</template:include>