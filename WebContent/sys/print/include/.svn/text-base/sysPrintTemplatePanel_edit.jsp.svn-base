<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script type="text/javascript" >
 Com_IncludeFile("jquery.ui.js",Com_Parameter.ContextPath + "resource/js/jquery-ui/","js",true);
 Com_IncludeFile("validator.js|util.js|font.js|shortcuts.js|rightmenu.js|attrpanel.js|effect.js|control.js",Com_Parameter.ContextPath + "sys/print/designer/","js",true);
 Com_IncludeFile("label.js|linklabel.js|table.js|field.js|detailstable.js|page.js|process.js|attachment.js|auditshow.js|right.js|jsp.js|brcontrol.js|uploadimg.js|divcontrol.js|bookticket.js|mutitab.js|docimg.js|qrcode.js|voteNode.js|massdata.js|dateformat.js|composite.js|uploadTemplateAttachment.js",Com_Parameter.ContextPath + "sys/print/designer/control/","js",true);
 Com_IncludeFile("buttons.js|common.js|color.js|config.js|undo.js|builder.js|toolbar.js|designer.js",Com_Parameter.ContextPath + "sys/print/designer/","js",true);
</script>		
<div id="sysPrintdesignPanel"></div>
<input type="hidden" name="sysPrintTemplateForm.fdMainModelName"  value="${HtmlParam.modelName}"/>
<input type="hidden" name="sysPrintTemplateForm.fdId"  value="${sysPrintTemplateForm.fdId }"/>
<input type="hidden" name="sysPrintTemplateForm.fdName"  value="${sysPrintTemplateForm.fdName }"/>
<input type="hidden" name="sysPrintTemplateForm.fdTmpXml" value="<c:out value="${sysPrintTemplateForm.fdTmpXml }"/>"/>
<input type="hidden" name="sysPrintTemplateForm.fdCss"/>
<input type="hidden" id="isXForm" value="${_isXForm}"/>
<input type="hidden" id="_method" value="${HtmlParam.method}"/>
<input type="hidden" id="_xformCloneTemplateId" value="${_xformCloneTemplateId}"/>

<div id="_tmp_xform_html" style="display: none;"></div>
<script type="text/javascript" >
	var sysPrintKMSS_Parameter_ContextPath = "${KMSS_Parameter_ContextPath}";
	//系统配置的图片上传最大大小
	var _image_max_size = <%=com.landray.kmss.util.ResourceUtil.getKmssConfigString("sys.att.imageMaxSize")%>?<%=ResourceUtil.getKmssConfigString("sys.att.imageMaxSize")%>:5;
	//系统配置的图片上传宽度最大大小
	var _image_bigImage_width = <%=com.landray.kmss.util.ResourceUtil.getKmssConfigString("sys.att.bigImageWidth")%>?<%=ResourceUtil.getKmssConfigString("sys.att.bigImageWidth")%>:1024;
	
	function sysPrintPanelInit(){
		 if(!sysPrintDesigner.instance.hasInitialized){
				setTimeout(sysPrintPanelInit,500);
		 }else{
			 var h = $(document.body).height()-230;
			 $('#sys_print_designer_draw').css('height',h);
		}
	}
	$(function(){
	     $("#sysPrintdesignPanel").bind("contextmenu",function(){
		  return false;
		});
	     sysPrintPanelInit();
	});
</script>
