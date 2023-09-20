<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.DateUtil"%>
<%@page import="java.util.Date"%>
<template:include ref="default.print" sidebar="no">
<template:replace name="title">
	<%-- <bean:message bundle="km-archives" key="kmArchivesAppraise.printAppraiseList" /> --%>
</template:replace>
<template:replace name="toolbar">
	<ui:toolbar  id="toolbar" layout="sys.ui.toolbar.float" count="8"> 
			<ui:button  id="zoomIn" text="${ lfn:message('km-archives:button.zoom.in') }"   onclick="ZoomFontSize(2);">
		    </ui:button>
		     <ui:button id="zoomOut" text="${ lfn:message('km-archives:button.zoom.out') }"   onclick="ZoomFontSize(-2);">
	 	   	</ui:button>
	  		 <ui:button text="${ lfn:message('button.print') }"   onclick="printorder();">
		    </ui:button>
			<c:import url="/sys/common/exportButton.jsp" charEncoding="UTF-8">
				<c:param name="showHtml" value="false"></c:param>
			</c:import>
		      <ui:button text="${ lfn:message('button.close') }"   onclick="window.close();">
		    </ui:button>
	</ui:toolbar>
</template:replace>
<template:replace name="content">
<script language="JavaScript">
seajs.use(['theme!form']);
seajs.use(['lui/jquery'],function($) {
	$(document).ready(function() {
		$(".tempTB").css({width:"1120px"});
	})
});
function outputPDF() {
	seajs.use(['lui/export/export'],function(exp) {
		exp.exportPdf(document.getElementById('printTable'));
	});
}
</script>

<script>
Com_IncludeFile("jquery.js|dialog.js|doclist.js");
</script>
<script>
var flag = 0;
function ZoomFontSize(size) {
	//当字体缩小到一定程度时，缩小字体按钮变灰不可点击
	if(flag>=0||flag==-2){
		flag = flag+size;
	}
	if(flag<0){
		$("#zoomOut").prop("disabled",true);
		$("#zoomOut").addClass("status_disabled");
	}else{
		$("#zoomOut").prop("disabled",false);
		$("#zoomOut").removeClass("status_disabled");
	} 
	var root = document.getElementById("printTable");
	var i = 0;
	for (i = 0; i < root.childNodes.length; i++) {
		SetZoomFontSize(root.childNodes[i], size);
	}
	var tag_fontsize;
	if(root.currentStyle){
	    tag_fontsize = root.currentStyle.fontSize;  
	}  
	else{  
	    tag_fontsize = getComputedStyle(root, null).fontSize;  
	}
	root.style.fontSize = parseInt(tag_fontsize) + size + 'px';
}

function SetZoomFontSize(dom, size) {
	if (dom.nodeType == 1 && dom.tagName != 'OBJECT' && dom.tagName != 'SCRIPT' && dom.tagName != 'STYLE' && dom.tagName != 'HTML') {
		for (var i = 0; i < dom.childNodes.length; i ++) {
			SetZoomFontSize(dom.childNodes[i], size);
		}
		var tag_fontsize;
		if(dom.currentStyle){  
		    tag_fontsize = dom.currentStyle.fontSize;  
		}  
		else{  
		    tag_fontsize = getComputedStyle(dom, null).fontSize;  
		} 
		dom.style.fontSize = parseInt(tag_fontsize) + size + 'px';
	}
}
</script>
<script language=javascript>  
function printorder() {  
	try {
        //PageSetup_temp();//取得默认值  
        //PageSetup_Null();//设置页面  
        //WebBrowser.execwb(6,6);//打印页面  
        window.print();
        //PageSetup_Default();//还原页面设置  
	} catch (e) {
		alert('<bean:message key="button.printPreview.error" bundle="km-archives"/>');
	}
}
</script>
<style>
	.clear {
		clear:both;
	}
	.container {
		float:left;
		width:30%;
		margin:0 10px 35px 0;
		padding:10px 0 10px 10px;
		border:1px solid #ccc;
	}
	.container .left {
		float:left;
		width:35%;
	}
	.container .right {
		float:left;
		width:60%;
		line-height:24px;
		text-align:left;
		padding-left:10px;
	}
	/* .container .right p {
		font-size:10px;
	} */
</style>
<OBJECT ID='WebBrowser' NAME="WebBrowser" WIDTH=0 HEIGHT=0 CLASSID='CLSID:8856F961-340A-11D0-A96B-00C04FD705A2'></OBJECT>

<center>
	<div id="printTable" style="border: none;padding-left:30px;">
		<div class="txttitle"> 
	      	<bean:message bundle="km-archives" key="kmArchivesAppraise.details" />
	    </div>
	    <br/>
		<table class="tb_normal" width=100%>
			<!--主题-->
			<tr>
				<td class="td_normal_title" width=5%>
					<bean:message key="page.serial" />
				</td>
				<td class="td_normal_title">
					<bean:message bundle="km-archives" key="kmArchivesAppraise.fdArchivesName" />
				</td>
				<td class="td_normal_title">
					<bean:message bundle="km-archives" key="kmArchivesAppraise.fdArchivesNumber" />
				</td>
				<td class="td_normal_title">
					<bean:message bundle="km-archives" key="kmArchivesAppraise.fdAfterAppraiseDate" />
				</td>
				<td class="td_normal_title">
					<bean:message bundle="km-archives" key="kmArchivesAppraise.docCreator" />
				</td>
				<td class="td_normal_title">
					<bean:message bundle="km-archives" key="kmArchivesAppraise.docCreateTime" />
				</td>
				<td class="td_normal_title" width=30%>
					<bean:message bundle="km-archives" key="kmArchivesAppraise.fdAppraiseIdea" />
				</td>
			</tr>
			<c:forEach items="${appraiseList }" var="appraise" varStatus="varStatus">
				<tr>
					<td>${varStatus.index+1 }</td>
					<td>${appraise.fdArchivesName }</td>
					<td>${appraise.fdArchivesNumber }</td>
					<td><kmss:showDate value="${appraise.fdAfterAppraiseDate }" type="date"></kmss:showDate></td>
					<td>${appraise.docCreator.fdName }</td>
					<td><kmss:showDate value="${appraise.docCreateTime }" type="datetime"></kmss:showDate></td>
					<td>${appraise.fdAppraiseIdea }</td>
				</tr>
			</c:forEach>
	</div>
</center>
</template:replace>
		
</template:include>

