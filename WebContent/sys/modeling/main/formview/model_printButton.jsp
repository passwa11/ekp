<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<div id="toolbarContainer">
<ui:toolbar  id="toolbar" layout="sys.ui.toolbar.float" count="9"> 
		<ui:button  id="zoomIn" text="${ lfn:message('sys-modeling-base:button.zoom.in') }"   onclick="ZoomFontSize(2);">
	    </ui:button>
	     <ui:button id="zoomOut" text="${ lfn:message('sys-modeling-base:button.zoom.out') }"   onclick="ZoomFontSize(-2);">
 	   	</ui:button>
 	   	 <ui:button text="${ lfn:message('sys-modeling-base:button.pageBreak') }" id="pageBreakButton">
  		 </ui:button>
  		 <ui:button text="${ lfn:message('button.print') }"   onclick="printorder();">
	    </ui:button>
	     <ui:button style="display:none;"  text="${ lfn:message('sys-modeling-base:button.printPreview') }"   onclick="printView();">
	    </ui:button>
	    <c:import url="/sys/common/exportButton.jsp" charEncoding="UTF-8"></c:import>
	      <ui:button text="${ lfn:message('button.close') }"   onclick="window.close();">
	    </ui:button>
</ui:toolbar>
</div>
<script>
Com_IncludeFile("print.js",Com_Parameter.ContextPath+"sys/modeling/main/resources/js/print/","js",true);
//#161832 分页增加表头字段
Com_IncludeFile("printDLMix.js",Com_Parameter.ContextPath+"km/review/resource/js/","js",true);

seajs.use(['theme!form']);
function outputPDF() {
	seajs.use(['lui/jquery','lui/export/export'],function($,exp) {
		$(".qrcodeArea").hide();
		$("#toolBarDiv").hide();
		exp.exportPdf($(".lui_form_content")[0],{
			pdfSplitePage:"true",
			callback:function() {
				$(".qrcodeArea").show();
				$("#toolBarDiv").show();
			}});
	});
}

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
function printorder()  
{  
	try {
        //PageSetup_temp();//取得默认值  
        //PageSetup_Null();//设置页面  
        //WebBrowser.execwb(6,6);//打印页面  
        //#126319：打印预览不全，先进行缩放
        document.getElementsByTagName('body')[0].style.zoom=0.58;
        window.print();
        //还原页面比例
        document.getElementsByTagName('body')[0].style.zoom=1;
        //PageSetup_Default();//还原页面设置  
	} catch (e) {
		alert("<bean:message key="button.printPreview.error" bundle="sys-modeling-base"/>");
	}
        //factory.execwb(6,6);  
       // window.close();  
} 
function ClearDomWidth(dom) {
	if (dom != null && dom.nodeType == 1 && dom.tagName != 'OBJECT' && dom.tagName != 'SCRIPT' && dom.tagName != 'STYLE' && dom.tagName != 'HTML') {
		//修改打印布局为 百分比布局 #曹映辉 2014.8.7
			/****
			var w = dom.getAttribute("width");
			if (w != '100%')
				dom.removeAttribute("width");
			w = dom.style.width;
			if (w != '100%')
				dom.style.removeAttribute("width");
			****/
			if (dom.style.whiteSpace == 'nowrap') {
				dom.style.whiteSpace = 'normal';
			}
			if (dom.style.display == 'inline') {
				dom.style.wordBreak  = 'break-all';
				dom.style.wordWrap  = 'break-word';
			}
		ClearDomsWidth(dom);
	}
}
function ClearDomsWidth(root) {
	for (var i = 0; i < root.childNodes.length; i ++) {
		ClearDomWidth(root.childNodes[i]);
	}
}
function printView() {
	try {
		//PageSetup_temp();
		//PageSetup_Null();
		document.getElementById('WebBrowser').ExecWB(7,1);
	    //PageSetup_Default();
	} catch (e) {
		alert("<bean:message key="button.printPreview.error" bundle="sys-modeling-base"/>");
	}
}
function expandXformTab(){
	var xformArea = $("#_xform_detail");
	if(xformArea.length>0){
		var tabs = $("#_xform_detail table.tb_label");
		if(tabs.length>0){
			for(var i=0; i<tabs.length; i++){
				var id = $(tabs[i]).prop("id");
				if(id==null || id=='') continue;
				$(tabs[i]).toggleClass("tb_normal");
				tabs[i].deleteRow(0);
				var tmpFun = function(idx,trObj){
					var trObj = $(trObj);
					//trObj.children("td").css({"padding":"0px","margin":"0px"});
					var tmpTitleTr = $("<tr class='tr_normal_title'>");
					var tempTd = $('<td align="left">');
					tempTd.html(trObj.attr("LKS_LabelName"));
					tempTd.appendTo(tmpTitleTr);
					trObj.before(tmpTitleTr);
				};
				var trArr = $("#"+id+" >tbody > tr[LKS_LabelName]");
				if(trArr.length<1){
					trArr = $("#"+id+" > tr[LKS_LabelName]");
				}
				trArr.each(tmpFun).show();
			}
		}
	}
}
function resetTableSize(){
	var tables = $(".sysDefineXform table[fd_type='standardTable']");
	for(var i = 0 ;i < tables.length;i++){
		var table = tables[i];
		//表格宽度调整
		$(table).css('width','100%');
		for (var j = 0; j < table.rows.length; j++) {
			var cells = table.rows[j].cells;
			var cellsCount = cells.length;
			for(var k = 0;k < cellsCount;k++){
				/*161832 标示列根据文本宽度;内容列根据填写内容自动;*/
				$(cells[k]).children(":first").css('width',"auto");
			}
		}
	}
}
Com_AddEventListener(window, "load", function() {
	ClearDomWidth(document.getElementById("info_content"));
	expandXformTab();
	//清除链接样式
	$('#_xform_detail a').css('text-decoration','none');
	$('a[id^=thirdCtripXformPlane_]').removeAttr('onclick');
	$('a[id^=thirdCtripXformHotel_]').removeAttr('onclick');
	resetTableSize();
});

//打印页面点击手写审批意见需要给window.previewImage传值
if(window.seajs){
	seajs.use(['lui/imageP/preview'], function(preview) {
		window.previewImage = preview;
	});
};
</script>

