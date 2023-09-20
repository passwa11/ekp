<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="java.util.Map"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.print.model.SysPrintTemplate"%>
<%@page import="com.landray.kmss.util.StringUtil,com.landray.kmss.sys.print.service.ISysPrintTemplateService,com.landray.kmss.sys.print.interfaces.ISysPrintMainForm"%>
<%@page import="com.landray.kmss.common.forms.IExtendForm" %>
<style type="text/css">
		@media print {
			#btnDiv,#top{
				display: none;
			}
			.nextpage {page-break-after:always;}
			.tempTB1{
				width:100% !important;
				max-width:100% !important;
				min-width:100% !important;
			}
			img {
			   max-width: 100% !important;
			}
			ul, img {
			   page-break-inside: avoid;
			}
            .lui_upload_img_box {
                display: block !important;
            }
		}
		body,td,input,select,textarea {font-size: 14px}
		.tb_normal>tbody>tr { border: 0px solid #d2d2d2;}
		.tb_normal>tbody>tr>td {padding: 4px !important; word-break:break-word; border: 1px #9d8f8f solid;}
		td div.xform_inputText,td div.xform_Calculation{display:inline-block !important;}
		td div.xform_Calculation{display:inline-block !important;word-break : break-all;word-wrap : break-word;}
		.lui_form_content{border: 0px;}
		#title {font-size: 22px;color: #000;}
		.lui_form_path_frame{display:none;}
		#lui_validate_message{display:none;}
		.upload_list_tr .upload_list_operation{display:none;}
		.lui_upload_img_box .upload_opt_td{display:none;}
		.upload_list_tr .upload_list_download_count{display:none;}
		#btnDiv .lui_toolbar_content{max-width:1000px !important;}
		.xform_auditshow .upload_list_icon img{height:16px;}
</style>
<script>
	// 自定义表单的全局变量
	var Xform_ObjectInfo = {};
	Xform_ObjectInfo.Xform_Controls = [];
	// 打印模式，针对附件机制样式
	window.isPrintModel = true;
</script>
<%
	if(request.getHeader("User-Agent").toUpperCase().indexOf("MSIE")>-1){
		request.setAttribute("isIE","true");
	}else if(request.getHeader("User-Agent").toUpperCase().indexOf("TRIDENT")>-1){
		request.setAttribute("isIE","true");
	}else{
		request.setAttribute("isIE","false");
	}

	String formBeanName = request.getParameter("formName");
	String mainFormName = null;
	String xformFormName = null;
	int indexOf = formBeanName.indexOf('.');
	if (indexOf > -1) {
		mainFormName = formBeanName.substring(0, indexOf);
		xformFormName = formBeanName.substring(indexOf + 1);
		pageContext.setAttribute("_formName", xformFormName);
	} else {
		mainFormName = formBeanName;
		pageContext.setAttribute("_formName", null);
	}
	Object mainForm = request.getAttribute(mainFormName);
	Object xform = xformFormName == null ? mainForm : PropertyUtils.getProperty(mainForm, xformFormName);
	
	if(xform instanceof IExtendForm){
		IExtendForm extendForm = (IExtendForm)xform;
		String mainModelName = extendForm.getModelClass().getName();
		request.setAttribute("_mainModelName", mainModelName);
	}
	
	String path = "";
	try{
		path = (String) PropertyUtils.getProperty(xform, "extendDataFormInfo.extendFilePath");
	}catch(Exception e){
	}
	if (StringUtil.isNotNull(path)) {
		request.setAttribute("_xformForm", xform);
		request.setAttribute("_xformMainForm", mainForm);
	}
	//控制权限区域显示
	request.setAttribute("SysForm.isPrint", "true");
%>
<c:set var="mainModelForm" value="${requestScope[param.formName]}" scope="request" />
<c:if test="${not empty fdTmpFileName }">
<link rel="stylesheet" type="text/css" href="<c:url value="/${fn:replace(fdTmpFileName,'.jsp','')}.css"/>">
<script>
	Xform_ObjectInfo.mainModelName = "${_mainModelName}";
	Xform_ObjectInfo.mainFormId = "${_xformMainForm.fdId}";
</script>
<%
request.removeAttribute("_xformMainForm");
if(xform instanceof IExtendForm){
	request.removeAttribute("_mainModelName");
}
%>
<script>
Com_IncludeFile("jquery.js|xform.js|data.js|form.js");
Com_IncludeFile("previewdesign.js",Com_Parameter.ContextPath+"sys/print/import/","js",true);
Com_IncludeFile("designer.css",Com_Parameter.ContextPath+"sys/print/designer/style/","css",true);
Com_IncludeFile("sysPrintForm_main.css",Com_Parameter.ContextPath+"sys/print/resource/css/","css",true);
function openDesign(){
	var url = window.location.href + "&design=true"
	window.open(url);
}
</script>
<%--打印按钮--%>
<ui:toolbar id="btnDiv" layout="sys.ui.toolbar.float" count="6">
	<ui:button id="design" text="${ lfn:message('sys-print:sysPrintPage.btn.previewDesign') }"   onclick="openDesign();"></ui:button> 
	<ui:button id="zoomIn" text="${ lfn:message('sys-print:sysPrintPage.btn.zoomIn') }"   onclick="window.printZoomFontSize(1);"></ui:button>
	<ui:button id="zoomOut" text="${ lfn:message('sys-print:sysPrintPage.btn.zoomOut') }"   onclick="window.printZoomFontSize(-1);"></ui:button>
	<ui:button id="printBtn" text="${ lfn:message('button.print') }"   onclick="window.print();"></ui:button>
	<c:if test="${isIE=='true'}">
   		<ui:button style="display:none;" id="printPreview" text="${ lfn:message('sys-print:sysPrintPage.btn.printPreview') }"   onclick="window.printView();"></ui:button>
    </c:if>
    <c:if test="${isShowSwitchBtn=='true'}">
    	<ui:button id="switchBtn" text="${lfn:message('sys-print:sysPrintPage.switchBtn.old')}"   onclick="switchPrintPage();"></ui:button>
    </c:if>
    <c:import url="/sys/common/exportButton.jsp" charEncoding="UTF-8"></c:import>
	<ui:button id="closeBtn" text="${lfn:message('button.close')}"   onclick="Com_CloseWindow();"></ui:button>
</ui:toolbar>
<c:if test="${not empty param.docSubject }">
	<div id="title" class="txttitle">${HtmlParam.docSubject }</div>
</c:if>
<form name="sysPrintTemplateForm">
<div id="sysPrintdesignPanel" style='display:block;padding:0px 14px 10px;' onselectstart="return false;" style="margin-bottom:10px;">
	<c:set var="_fdTmpFileName" value="${fdTmpFileName }" />
	<c:if test="${fn:indexOf(fdTmpFileName,'.jsp')<=0 }">
		<c:set var="_fdTmpFileName" value="${fdTmpFileName }.jsp" />
	</c:if>
	<c:if test="${fn:indexOf(fdTmpFileName,'.jsp_v')>0 }">
		<c:set var="_fdTmpFileName" value="${fn:replace(fdTmpFileName,'.jsp','')}.jsp" />
	</c:if>
	<c:import url="/${_fdTmpFileName}" charEncoding="UTF-8"></c:import>
	<p>&nbsp;</p>
	<script>
	//解析表单view页面存值
	var xform_data_hide={};
    Xform_ObjectInfo.printFormFilePath = "${fdTmpFileName}";
	<%
	        Map<String ,Object> map=(java.util.Map)request.getAttribute("view_xform_value");
			if(map !=null){
				for(Map.Entry<String ,Object> entry : map.entrySet()){
					String val=StringUtil.clearScriptTag((String)entry.getValue());
					val=StringUtil.XMLEscape(val);
					out.println("xform_data_hide[\""+entry.getKey()+"\"]=\""+val+"\";");
				}
			}
	%>
	//将特殊字符转义还原
	for(var temp in  xform_data_hide){
		if(xform_data_hide[temp]==null){
			continue;
		}
		xform_data_hide[temp]=xform_data_hide[temp].replace(/&amp;/g, "&").replace(/&quot;/g, "\"").replace(/&lt;/g, "<").replace(/&gt;/g, ">");
	}
	</script>
</div>
</form>	
<script type="text/javascript">
	var dashDom = {};
	var isIE = '${isIE}';
	$("#sysPrintdesignPanel table[fd_type='table']").mousedown(function(event){
			sysPreviewDesign._mousedown(event);
		}).mouseup(function(event){
			sysPreviewDesign._mouseup(event);
			}).mousemove(function(event){
				sysPreviewDesign._mousemove(event);
			});
	function printView(){
		var isIe = "${isIE}";
		try{
			if(isIe=='true' && document.all.WebBrowser){
				document.all.WebBrowser.ExecWB(7,1);
			}
		}catch(e){
			alert('<bean:message key="button.printPreview.error" bundle="sys-print"/>');
		}
	}
	function resetTableSize(){
		var tables = $("#sysPrintdesignPanel table[fd_type='table']");
		for(var i = 0 ;i < tables.length;i++){
			var table = tables[i];
			//表格宽度调整
			$(table).css('width','100%');
			for (var j = 0; j < table.rows.length; j++) {
				var cells = table.rows[j].cells;
				var cellsCount = cells.length;
				for(var k = 0;k < cellsCount;k++){
					/*157976 新版打印显示的表单样式变形 start 标示列根据文本宽度;内容列根据填写内容自动;*/
					var td_width = $(cells[k]).children(":first").width();
					if(td_width==''||td_width==undefined||td_width==null){
						td_width=$(cells[k]).css("width");
					}
					if(td_width!=''&&td_width!=undefined&&td_width!=null){
						//#168215 如果已经有宽度存在就，不再修改其宽度
						if (!(cells[k].style && cells[k].style.width)){
							$(cells[k]).css('width',td_width);
						}
					}else{
						$(cells[k]).css('width',"auto");
					}
					/*157976 新版打印显示的表单样式变形 end*/
				}
			}
		}
	}
	
	function printZoomFontSize(size){
		var root = document.getElementById("sysPrintdesignPanel");
		var i = 0;
		for (i = 0; i < root.childNodes.length; i++) {
			SetPrintZoomFontSize(root.childNodes[i], size);
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
	function SetPrintZoomFontSize(dom, size){
		if (dom.nodeType == 1 && dom.tagName != 'OBJECT' && dom.tagName != 'SCRIPT' && dom.tagName != 'STYLE' && dom.tagName != 'HTML' && dom.tagName!='LINK') {
			for (var i = 0; i < dom.childNodes.length; i ++) {
				SetPrintZoomFontSize(dom.childNodes[i], size);
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
	
	function domReady(){
		var $designPanel = $('#sysPrintdesignPanel');
		var printWidth = 980;//固定打印页面宽度
		//根据比例重新调整容器宽度
		window._percent = printWidth/$designPanel.width();
		sysPreviewDesign.resetBoxWidth($designPanel[0]);
		//调整页面宽度
		var $table = $('#sysPrintdesignPanel').parents('table').css('width','980px');
		resetTableSize();
		$('.xform_label').each(function (index, dom) {
			var html = dom.innerHTML;
			var reg = new RegExp("&nbsp;","g");
			html = html.replace(reg, " ");
			html = html.replace(/\s*$/g, "");
			dom.innerHTML = html;
		});
	}
	function outputPDF() {
		seajs.use(['lui/jquery','lui/export/export'],function($,exp) {
			exp.exportPdf(document.querySelector('.tempTB'),{title:'${HtmlParam.docSubject}'});
		});
	}
	Com_AddEventListener(window, 'load', domReady);
</script>
</c:if>