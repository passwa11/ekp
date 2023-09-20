<%@page import="com.landray.kmss.sys.xform.util.LangUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.xform.base.config.*" %>
<%@ page import="com.landray.kmss.sys.xform.XFormConstant"%>
<%@ include file="/resource/jsp/common.jsp"%>
<c:set var="sysFormTemplateFormPrefix" value="${param.sysFormTemplateFormPrefix }" />
<c:set var="xFormTemplateForm" value="${requestScope[param.formName]}" />
<html:hidden property="${sysFormTemplateFormPrefix}fdDesignerHtml" />
<html:hidden property="${sysFormTemplateFormPrefix}fdMetadataXml" />
<html:hidden property="${sysFormTemplateFormPrefix}fdCss"/>
<html:hidden property="${sysFormTemplateFormPrefix}fdCssDesigner"/>
<html:hidden property="${sysFormTemplateFormPrefix}fdFragmentSetIds"/>
<html:hidden property="${sysFormTemplateFormPrefix}fdMainModelName"/>
<input type="hidden" name="${sysFormTemplateFormPrefix}fdChangeLog"/>
<script>Com_IncludeFile('json2.js');</script>
<%-- 页面类型 --%> 
<!-- add by duf 判断当前集成自定义表单的模板，是否在插件工厂注册了；注册下面隐藏域，不需要，如果没注册，需要。不然会导致没注册的模块视图页面显示不全 -->
<% if(!(LangUtil.isEnableMultiLang(request.getParameter("fdMainModelName"), "model") && LangUtil.isEnableAdminDoMultiLang())) {%>
<input type="hidden" id="uu_FdContent"
	name="${sysFormTemplateFormPrefix}fdMultiLangContent"
	value='${xFormTemplateForm.fdMultiLangContent}' />
<%} %>

<%-- 自设计 --%>
<tr id="XForm_${xFormTemplateForm.fdKey}_CustomTemplateRow">
	<td class="td_normal_title" colspan=4
		id="TD_FormTemplate_${xFormTemplateForm.fdKey}" ${sysFormTemplateFormResizePrefix}onresize="LoadXForm('TD_FormTemplate_${xFormTemplateForm.fdKey}');">
		<iframe id="IFrame_FormTemplate_${xFormTemplateForm.fdKey}" width="100%" height="100%" scrolling="yes" FRAMEBORDER=0></iframe>
	</td>
</tr>

<%@include file="template_script.jsp" %>
<script type="text/javascript">Com_IncludeFile("docutil.js|security.js|dialog.js|formula.js");</script>
<script>
//表单不需要有关流程的控件的标志
if("${param.noProcessFlow}" != null && "${param.noProcessFlow}" == 'true'){
	sys_xform_noProcessFlow = 'true';
}

function LoadXForm(dom) {
	XForm_Loading_Show();
	Doc_LoadFrame(dom, '<c:url value="/sys/xform/designer/designPanel.jsp?fdKey=" />${xFormTemplateForm.fdKey}&fdModelName=${xFormTemplateForm.fdModelName}&sysFormTemplateFormPrefix=${sysFormTemplateFormPrefix}');
	var frame = document.getElementById('IFrame_FormTemplate_${xFormTemplateForm.fdKey}');
	Com_AddEventListener(frame, 'load', XForm_Loading_Hide);
    //多表单配置
    if(typeof SUBForm_Loading != "undefined"){
        Com_AddEventListener(frame, 'load', function(){
            setTimeout(function (){SUBForm_Loading()}, 500);
        });
    }
}

function XForm_ConfirmFormChangedFun(){
	var fdDesignerHtmlObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdDesignerHtml")[0];
	var fdMetadataXmlObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdMetadataXml")[0];
	var customIframe = window.frames['IFrame_FormTemplate_${xFormTemplateForm.fdKey}'].contentWindow;
	if(!customIframe){
		customIframe = window.frames['IFrame_FormTemplate_${xFormTemplateForm.fdKey}'];
	}
	//设置片段集id
	var fdFragmentSetObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdFragmentSetIds")[0];
	var fdChangeLogObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdChangeLog")[0];

	//主文档全类名
	var fdMainModelNameObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdMainModelName")[0];
	fdMainModelNameObj.value = window._xform_MainModelName;
	//片段集
	var fdFragmentSetIds = [];
	var fdDesignerHtml = null;
	if(customIframe.Designer != null && customIframe.Designer.instance != null){
		if(!customIframe.Designer.instance.checkoutAll()){
			//表单绘制数据不合法，不允许提交
			return false;
		}
		fdDesignerHtml = customIframe.Designer.instance.getHTML();
	}
	fdDesignerHtmlObj.value = fdDesignerHtml;
	fdMetadataXmlObj.value = customIframe.Designer.instance.getXML();
    //移动端表单提交前处理
    if (typeof MobileForm_BuildValueInConfirm != "undefined") {
        MobileForm_BuildValueInConfirm();
    }
	if (customIframe.Designer != null && customIframe.Designer.instance != null){
		if (customIframe.Designer.instance.fragmentSetIds.length > 0){
			fdFragmentSetIds =  customIframe.Designer.instance.fragmentSetIds;
		}
		var arr = new Array();
		$.each(fdFragmentSetIds, function(i, obj){
			 arr.push(obj["fragmentSetId"]);
			 var data = new KMSSData();
			 //获取最新的片段集html
			 data.AddBeanData('sysFormFragmentSetTreeService&fdId='+obj["fragmentSetId"]);
			 var returnData = data.GetHashMapArray()[0];
			 var html = returnData['fdDesignerHtml'];
			 //获取片段集控件dom元素
			 var dom = $("#" + obj["controlId"],customIframe.document);
			 dom.find("span[controlid='" + obj["controlId"] + "']").html(html);
			 var control = customIframe.Designer.instance.builder.getControlByDomElement(dom[0]);
			 //生成最新的片段集子控件对象,以便获取数据字典
			fdDesignerHtml = customIframe.Designer.instance.getHTML();
			customIframe.Designer.instance.setHTML(fdDesignerHtml);
		});
		fdDesignerHtml = customIframe.Designer.instance.getHTML();
		fdDesignerHtmlObj.value = fdDesignerHtml;
		fdMetadataXmlObj.value = customIframe.Designer.instance.getXML();
		fdFragmentSetObj.value = arr.join(";");
		var compareResult = customIframe.Designer.instance.compare();
		if (compareResult){
			fdChangeLogObj.value = compareResult;
		}
		customIframe.resetLangInfo();
	}
	//BASE64处理脚本内容	
	fdDesignerHtmlObj.value = base64Encodex(fdDesignerHtmlObj.value);
    if("${xFormTemplateForm.fdMode}" == "<%=XFormConstant.TEMPLATE_SUBFORM%>"){
        //BASE64处理子表单脚本内容
        $("#TABLE_DocList_SubForm").find('tr[ischecked]').each(function(){
            var subHtml = $(this).find("input[name$='fdDesignerHtml']");
            subHtml.val(base64Encodex(subHtml.val(), customIframe, $(this)));
        });
    } else {
        //清除多表单信息
        $("#TABLE_DocList_SubForm").find('tr:gt(0)').each(function(){
            DocList_DeleteRow(this);
        });
    }
	return true;
}

</script>
	<link rel="stylesheet" type="text/css" href="<c:url value="/component/locker/resource/jNotify.jquery.css"/>" media="screen" />
	<script type="text/javascript" src="<c:url value="/component/locker/resource/jNotify.jquery.js"/>"></script>
	
<script>

$(document).ready(function() {
	
});
</script>