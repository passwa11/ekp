<%@page import="com.landray.kmss.sys.xform.util.LangUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit" />
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/ui/jsp/jshead.jsp" %>
<%@ page import="com.landray.kmss.sys.xform.XFormConstant"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
 </head>
 <script>Com_IncludeFile("jquery.js");</script>
<script type="text/javascript">
	Com_IncludeFile("dialog.js|calendar.js|formula.js|doclist.js|docutil.js");
	var colorChooserHintInfo={
			chooseText : '确定',
			cancelText : '取消'
		};
	Com_IncludeFile("spectrum.js",Com_Parameter.ContextPath+'resource/js/colorpicker/','js',true);
	Com_IncludeFile("spectrum.css",Com_Parameter.ContextPath+'resource/js/colorpicker/css/','css',true);
<%@ include file="lang.jsp" %>
</script>
<%@ page import="com.landray.kmss.sys.xform.base.service.spring.SysFormTemplateControlUtils"%>

<script>
	//系统配置的图片上传容量最大大小
	var _image_max_size = <%=ResourceUtil.getKmssConfigString("sys.att.imageMaxSize")%>?<%=ResourceUtil.getKmssConfigString("sys.att.imageMaxSize")%>:5;
	//系统配置的图片上传宽度最大大小
	var _image_bigImage_width = <%=ResourceUtil.getKmssConfigString("sys.att.bigImageWidth")%>?<%=ResourceUtil.getKmssConfigString("sys.att.bigImageWidth")%>:1024;
</script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/dtree/dtree.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/builder.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/panel.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/control.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/dash.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/mobile/js/config_mobile.js""></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/mobile/js/control_ext.js""></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/config_ext.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/config.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/attachment.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/jspcontrol.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/buttons.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/toolbar.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/effect.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/treepanel.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/attrpanel.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/shortcuts.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/cache.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/rightmenu.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/relation/relation.js"></script>
	<script type="text/javascript" charset="utf-8" src='<c:url value="/sys/xform/designer/style/js/lib/jquery.SuperSlide.2.1.1.js"/>'></script>


	<!-- 扩展 -->
	<%--script type="text/javascript" src="detailstable.js"></script--%>
	<%
	pageContext.setAttribute("jsFiles", SysFormTemplateControlUtils.getAllControlJsFiles(request.getParameter("fdModelName")));
	%>
	<c:forEach items="${jsFiles}" var="jsFile">
	<script type="text/javascript" src="<c:url value="${jsFile}" />"></script>
	</c:forEach>
	
	<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/hidden.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/right.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/srceditor.js"></script>
	<!-- 一键排版 -->
	<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/autoformat.js"></script>
	<!-- 表格样式 -->
	<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/tableStyle.js"></script>
	<%
	// 单独的js嵌入
	pageContext.setAttribute("jsFiles", SysFormTemplateControlUtils.getDesignJsFiltes());
	%>
	<c:forEach items="${jsFiles}" var="jsFile">
	<script type="text/javascript" src="<c:url value="${jsFile}" />"></script>
	</c:forEach>
	<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/designer.js"></script>
	
	<link href="<%=request.getContextPath() %>/sys/xform/designer/style/designer.css" type="text/css" rel="stylesheet" />
	<link href="<%=request.getContextPath() %>/resource/style/default/doc/document.css" type="text/css" rel="stylesheet" />
	<!--[if IE 6]>
	<link href="style/designer_ie6.css" type="text/css" rel="stylesheet" />
	<![endif]-->
	<link href="<%=request.getContextPath() %>/sys/xform/designer/dtree/dtree.css" type="text/css" rel="stylesheet" />
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/sys/xform/designer/style/css/edui-editor-common.css">
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/sys/xform/designer/style/css/edui-editor-icon.css" />
	<c:if test="${JsParam.mobile == 'true'}">
		<c:set var="tiny" value="true" scope="request" />
		<mui:cache-file name="common-tiny.css" cacheType="md5"/>
		<mui:cache-file name="view-tiny.css" cacheType="md5"/>
		<link href="<%=request.getContextPath() %>/sys/xform/designer/mobile/css/mobileDesigner.css" type="text/css" rel="stylesheet" />
	</c:if>
	<c:set var="sysFormTemplateFormPrefix" value="${param.sysFormTemplateFormPrefix}" />
<script>
//resource
var uu_lang_arr = '<%=ResourceUtil.getKmssConfigString("kmss.multi.lang")%>';
//如果在配置文件没找到kmss.multi.lang，那么给个空字符串值，以免json转换报错
if(uu_lang_arr == 'null'){
	uu_lang_arr = '';
}
if(uu_lang_arr.charAt(uu_lang_arr.length - 1) == ','){
	uu_lang_arr = uu_lang_arr.substr(0, uu_lang_arr.length - 1);
}
//key
var _langLanguage = uu_lang_arr.split(",");
//value
var _langLanguage_value = eval('<%=LangUtil.getLangValue()%>');

</script>
<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/underscore.js"></script>	
<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/backbone.js"></script>		
<script type="text/javascript" src="<%=request.getContextPath() %>/sys/xform/designer/uu_lang.js"></script>	
	
<script type="text/javascript">
var XForm_Design_Has_Init = false;
var parentIframe = null;
/**
 * 异步请求服务
 * arg：请求参数
*/
function postRequestServers(arg) {
	var result = null;
	$.ajax({
		url: Com_Parameter.ContextPath + "sys/lbpm/engine/jsonp.jsp?s_bean=convertBase64ToHtmlService",
		async: false,
		data: arg,
		type: "POST",
		dataType: 'json',
		success: function (data) {
			result = data;
		},
		error: function (er) {

		}
	});
	return result;
}

function XForm_DesignGetCurrentTabWidth(){
	var currentTabWidth;
	var tableId = "${param.lableTableId}";
	if(tableId == null){
		tableId = "Label_Tabel";
	}
	var $labelTable = $(parent.document.getElementById("Label_Tabel"));
	var $currentTab = $labelTable.find("tr[lks_labelindex='"+ $labelTable.attr("lks_currentlabel") +"']");
	if($currentTab.length > 0){
		currentTabWidth = $currentTab.width();	
	}
	return currentTabWidth;
}

//移动端
function XForm_MobileDesignOnLoad(){
	var td_template = parent.document.getElementById("TD_MobileFormTemplate_${param.fdKey}");
	var iframe = td_template.getElementsByTagName("IFRAME")[0];
	Designer.instance.fdModelName = '${JsParam.fdModelName}';
	parentIframe = iframe;
	iframe.height = screen.height - 300;
	var fdModel = parent.document.getElementsByName("${sysFormTemplateFormPrefix}fdMode")[0];
	var fdDesignerHtmlObj;
	fdDesignerHtmlObj = parent.document.getElementsByName("${sysFormTemplateFormPrefix}fdDesignerHtml")[0];
	if(fdModel){
		Designer.instance.fdModel = true;
	}else{
		Designer.instance.fdModel = false;
	}
	Designer.instance.isMobile = true;
	Designer.instance.initialize(document.getElementById('designPanel'));
	Designer.instance.fdKey = '${param.fdKey}';
	Designer.instance.parentWindow = window.parent;
	Designer.instance.hasInitialized = true;
	Designer.instance.template_subform = '<%=XFormConstant.TEMPLATE_SUBFORM%>';
	$(Designer.instance.toolBarDomElement).hide();
	$("body").css("overflow-x","hidden");
}

function XForm_DesignOnLoad(){
	if (XForm_Design_Has_Init) return;
	XForm_Design_Has_Init = true;
	var mobile = '${JsParam.mobile}';
	if (mobile == "true") {
		XForm_MobileDesignOnLoad();
		return ;
	}
	var td_template = parent.document.getElementById("TD_FormTemplate_${param.fdKey}");
	var iframe = td_template.getElementsByTagName("IFRAME")[0];
	parentIframe = iframe;
	iframe.height = screen.height - 350;
	var fdDesignerHtmlObj;
	fdDesignerHtmlObj = parent.document.getElementsByName("${sysFormTemplateFormPrefix}fdDesignerHtml")[0];
	Designer.instance.fdModelName = '${JsParam.fdModelName}';
	if ('${param.method}' == 'view' || '${param.method}' == 'viewHistory') {
		$(document.getElementById('designPanel')).html(fdDesignerHtmlObj.value);
		//若为多行输入框，则修改display样式为inline
		$(".xform_textArea").css("display","inline");
		setTimeout(function(){XForm_AdjustViewHeight(iframe);new UU_lang().showView();}, 200);
	} else {
		var fdModel = parent.document.getElementsByName("${sysFormTemplateFormPrefix}fdMode")[0];
		if(fdModel){
			Designer.instance.fdModel = true;
		}else{
			Designer.instance.fdModel = false;
		}
		
		if(fdDesignerHtmlObj.value != ''){
			Designer.instance.maxWidth = XForm_DesignGetCurrentTabWidth();
			Designer.instance.initialize(document.getElementById('designPanel'));
			Designer.instance.hasInitialized = false;
	
			//加载时表单内容为加密内容时将内容进行解密操作 2017-11-20 王祥
			var vChar="\u4645\u5810\u4d40";
			if(fdDesignerHtmlObj.value.indexOf(vChar)>=0){
				var vData={"fdDesignerHtml":fdDesignerHtmlObj.value};
				var vHtml=postRequestServers(vData);
				fdDesignerHtmlObj.value=vHtml.html;
			}
			
			//编辑时，初始化多表单当前选中的表单信息为默认表单
			var defaultTr = $("#TABLE_DocList_SubForm",window.parent.document).find('tr:eq(0)');
			if(defaultTr.length>0){
				var mySubform = {};
				mySubform.id = defaultTr.attr("id");
				mySubform.link = defaultTr.find("[name='subFormText']");
				mySubform.fdDesignerHtmlObj = defaultTr.find("input[name$='fdDesignerHtml']");
                var fdCssObj = window.parent.document.getElementsByName("${sysFormTemplateFormPrefix}fdCss")[0];
                var fdCssDesignerObj = window.parent.document.getElementsByName("${sysFormTemplateFormPrefix}fdCssDesigner")[0];
                if (fdCssObj) {
                    defaultTr.find("input[name$='fdCss']").val(fdCssObj.value);
                }
                if (fdCssDesignerObj) {
                    defaultTr.find("input[name$='fdCssDesigner']").val(fdCssDesignerObj.value);
                }
				Designer.instance.subForm = mySubform;
				Designer.instance.subForms.push(mySubform);
			}
			
			Designer.instance.fdKey = '${param.fdKey}';
			Designer.instance.template_subform = '<%=XFormConstant.TEMPLATE_SUBFORM%>';
			Designer.instance.parentWindow = window.parent;
			Designer.instance.setHTML(fdDesignerHtmlObj.value, true);
			Designer.instance.hasInitialized = true;
			Designer.instance.storeOldFdValues();
		}else{
			Designer.instance.maxWidth = XForm_DesignGetCurrentTabWidth();
			Designer.instance.initialize(document.getElementById('designPanel'));
			
			//新建时，初始化多表单当前选中的表单信息为默认表单
			var defaultTr = $("#TABLE_DocList_SubForm",window.parent.document).find('tr:eq(0)');
			if(defaultTr.length>0){
				var mySubform = {};
				mySubform.id = defaultTr.attr("id");
				mySubform.link = defaultTr.find("[name='subFormText']");
				mySubform.fdDesignerHtmlObj = defaultTr.find("input[name$='fdDesignerHtml']");
				Designer.instance.subForm = mySubform;
				Designer.instance.subForms.push(mySubform);
			}
			
			Designer.instance.fdKey = '${param.fdKey}';
			Designer.instance.template_subform = '<%=XFormConstant.TEMPLATE_SUBFORM%>';
			Designer.instance.parentWindow = window.parent;
			Designer.instance.hasInitialized = true;
			Designer.instance.builder.createControl('standardTable'); // 初始化新建一个表格
			XForm_SetTableDefault();
			var modelVal = $(fdModel).val();
			if (modelVal === "3") {
				$(fdModel).removeAttr("disabled");
				$(fdModel).removeClass("removeSelectAppearance");
			}
		}
		if(parent.XForm_CustomDesigner){
			parent.XForm_CustomDesigner(Designer.instance,window);			
		}
		if(parent.isModelShow == true){//用于对模块开发某些属性（目前针对是否拼接明细表索引的属性）
			window.isModelShow = true;
		}
		if(parent.XForm_GetWfAuditNodes_Extend){
			window.XForm_IsExtendOpt = true;
			window.XForm_GetWfAuditNodes_Extend = parent.XForm_GetWfAuditNodes_Extend;
		}
	}
	//窗口发生变化是调整虚线选择框
	$(window).resize(function(){
		if(Designer.instance.builder)
			Designer.instance.builder.resetDashBoxPos();
	});
}

function XForm_SetTableDefault(){
	var currentFormId = Designer.instance.subForm.id;
	var controls = Designer.instance.subFormControls[currentFormId];
	if(controls!=null&&controls.length>0){
		var tableControl = controls[0];
		if (tableControl && tableControl.type == "standardTable") {
			tableControl.options.values.isDefault = true;
		}	
	}
	
}

function XForm_AdjustViewHeight(iframe) {
	_height = document.getElementById("designPanel").offsetHeight;
	var _width = document.getElementById("designPanel").offsetWidth;
	var isIE = true;
	if(!(window.attachEvent && navigator.userAgent.indexOf('Opera') === -1)){
		isIE = false;
	}
	var widht = Designer.getDocumentAttr("scrollWidth");
	if (widht > _width){
		$("#designPanel").css("overflow-x","scroll");
	}
	if(isIE){
		var height = document.body.scrollHeight + 17;
		if (height < 50) {
			iframe.height = 100;
			setTimeout(function(){XForm_AdjustViewHeight(iframe);}, 200);
			return;
		} else {
			iframe.height = height;
		}	
		if (height < _height) {
			iframe.height = _height + 17;
		}
	}else{
		iframe.height = _height + 17;
	}
	//多表单div高度调整
	if(!$("#DIV_SubForm_View",parent.document).is(":hidden")) {
		var myHeight = $("#DIV_SubForm_View",parent.document).parent().outerHeight(false);
		$("#DIV_SubForm_View",parent.document).css("height",myHeight-9);
	}
}
var XForm_GetWfAuditNodes = null;
var XForm_GetWfAuditNodes_Extend = null;
Com_AddEventListener(window, 'load', XForm_DesignOnLoad);

//禁用chrome等其他浏览器自己的右键菜单 作者 曹映辉 #日期 2015年3月26日
$(function(){
     $("#designPanel").bind("contextmenu",function(){
	  return false;
	});
});

</script>

<style>
	body, input, textarea, select, div, a, table, tr, td, th {
		font-size: 12px !important;
	}
</style>
 
 <BODY leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" style='overflow:auto;background-color: #fff;'>
  <c:if test="${JsParam.mobile == 'true' && JsParam.openBaseInfoDesign == 'true'}">
  	<c:import url="/sys/xform/designer/standardtable/baseInfoDisplay.jsp" charEncoding="UTF-8">
  	</c:import>
  </c:if>
  <div id="designPanel">
  </div>
  
 </BODY>
</HTML>
<script type="text/javascript">
$(document).on("designer-buttons-init",function(){
	
});
</script>


