<%@page import="java.util.Map"%>
<%@page import="com.landray.kmss.sys.xform.service.DictLoadService"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="com.landray.kmss.web.taglib.xform.TagUtils" %>
<%@page import="com.landray.kmss.sys.xform.service.SysFormFileMannager"%>
<%@page import="com.landray.kmss.sys.xform.base.model.BaseFormTemplateHistory"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.xform.base.service.SysFormUtil"%>
<%@page import="com.landray.kmss.sys.xform.base.dao.ISysFormTemplateHistoryDao"%>
<%@page import="com.landray.kmss.sys.xform.base.dao.ISysFormCommonTempHistoryDao"%>
<%@page import="com.landray.kmss.sys.xform.base.model.AbstractFormTemplate"%>
<%@page import="com.landray.kmss.common.forms.IExtendForm" %>
<%@page import="com.landray.kmss.sys.xform.service.SysFormDictTreeVarService"%>
<%@page import="com.landray.kmss.sys.workflow.interfaces.ISysWfMainForm"%>
<%@page import="javax.servlet.jsp.PageContext" %>
<%@ page import="com.landray.kmss.sys.ui.util.PcJsOptimizeUtil" %>
<script>
	Com_IncludeFile('sysForm_main.css','${KMSS_Parameter_ContextPath}sys/xform/include/css/','css',true);
	// 创建自定义标签，兼容IE8 by zhugr 2017-08-26
	document.createElement("xformflag");
</script>
<script>
	// 自定义表单的全局变量
	var Xform_ObjectInfo = {};
	Xform_ObjectInfo.Xform_Controls = [];
</script>
<c:choose>
	<c:when test="${compressSwitch eq 'true' && lfn:jsCompressEnabled('sysFormCompressExecutor', 'sys_form_comJs_combined')}">
		<script src="<%= PcJsOptimizeUtil.getScriptSrcByExtension("sysFormCompressExecutor","sys_form_comJs_combined") %>?s_cache=${ LUI_Cache }">
		</script>
	</c:when>
</c:choose>
<script>Com_IncludeFile("swf_attachment.js","${KMSS_Parameter_ContextPath}sys/attachment/js/","js",true);</script>
<script>Com_IncludeFile('select2.js|select2.css','../sys/xform/designer/relation_select/select2/');</script>
<script>Com_IncludeFile("form.js","${LUI_ContextPath}/resource/js/","js",true);</script>
<script>Com_IncludeFile("validation_extend.js","${KMSS_Parameter_ContextPath}sys/xform/validate/","js",true);</script>
<!-- 引入各种控件主文档处需要用的多语言信息 -->
<%@ include file="/sys/xform/include/sysForm_lang.jsp"%>
<%
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
	//兼容add时候的公式解析,无mainModel需要手动构建mainModel
	out.println("<input type=\"hidden\" name=\"" + (xformFormName == null ? "" : xformFormName + ".") + "extendDataFormInfo.mainModelName\" value=\"" + mainModelName + "\" />");
}

boolean xform_isPrint = "true".equals(request.getParameter("isPrint")); // 是否为打印,同时为优化打印做准备
// SysForm.showStatus 代表了，控件为只读情况下，只读方式为 view,还是readOnly
if (xform_isPrint) {
	request.setAttribute("SysForm.isPrint", "true");
	request.setAttribute("SysForm.showStatus", "view");
	request.setAttribute("SysForm.base.showStatus", "view");
} else {
	if (mainForm instanceof  com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmMainFormAdapter
			&& "view".equals(pageContext.getAttribute("SysForm.importType"))) {
        com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmMainFormAdapter wfForm = (com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmMainFormAdapter) mainForm;
		if (wfForm.getDocStatus().charAt(0) < '3') {
			//request.setAttribute("SysForm.showStatus", "readOnly");
			request.setAttribute("SysForm.base.showStatus", "readOnly");
		}
	}
}
DictLoadService dictService=(DictLoadService)SpringBeanUtil.getBean("sysFormDictLoadService");
SysFormDictTreeVarService formVarService=(SysFormDictTreeVarService)SpringBeanUtil.getBean("sysFormDictVarTree");

//String path = (String) PropertyUtils.getProperty(xform, "extendDataFormInfo.extendFilePath");
//path=dictService.findExtendFileFullPath(path);
String path = "";
	String fdFormId = "";
	String formPath = (String)PropertyUtils.getProperty(xform, "extendDataFormInfo.extendFilePath");
	if(xform instanceof IExtendForm){
		IExtendForm extendForm = (IExtendForm)xform;
		path=dictService.findExtendFileFullPath(extendForm);
		if (StringUtil.isNotNull(formPath)) {
            fdFormId = dictService.getFormId(extendForm);
        }
}else{
	path = (String) PropertyUtils.getProperty(xform, "extendDataFormInfo.extendFilePath");
	path=dictService.findExtendFileFullPath(path);
}

if (StringUtil.isNotNull(path)) {
	if (path.startsWith("/")) {
		path = path.substring(1);
	}
	request.setAttribute("_xformMainForm", mainForm);
	request.setAttribute("_xformForm", xform);
	request.setAttribute("fdFormId", fdFormId);
	request.setAttribute("formPath", formPath);
	pageContext.setAttribute("_formFilePath", path);
	String properties = formVarService.getDictByPath(path);
	pageContext.setAttribute("_formProperties", properties);
	// 兼容EKP3.1代码
	//out.println("<!-- 执行兼容EKP3.01代码  -->");
	Object originFormBean = pageContext.getAttribute("com.landray.kmss.web.taglib.FormBean", PageContext.REQUEST_SCOPE);
	pageContext.setAttribute("com.landray.kmss.web.taglib.FormBean", mainForm, PageContext.REQUEST_SCOPE);
	
	boolean isUseTab = !"false".equals(request.getParameter("useTab"));
	boolean isUseScript = !"false".equals(request.getParameter("useScript"));
	out.println("<input type=\"hidden\" name=\"" + (xformFormName == null ? "" : xformFormName + ".") + "extendDataFormInfo.extendFilePath\" value=\"" + path + "\" />");
	
	Boolean isUpTab = false;
	
	
%>
<% if (isUseScript) {%>
<%@ include file="/sys/xform/include/sysForm_script.jsp" %>
<%}%>
<% if (isUseTab) {%>
<tr
	LKS_LabelName="<kmss:message key="${(not empty param.messageKey) ? (param.messageKey) : 'sys-xform:sysForm.tab.label'}" />"
	style="display: none">
	<td>
<%}%>
	
	<script type="text/javascript">
		Com_AddEventListener(window,"load",function(){
			$("div.xform_auditshow img").each(function(index,obj){
				if ($(obj).attr("width") && $(obj).attr("width").indexOf("%") > 0) {//初始化话完成是要修改手写签批图片的宽度
					var $imgObj = $(obj);
					//获取原生图片宽度
					var img = new Image();
					img.src = $imgObj.attr("src");
					var orgWidth = img.width;
					//获取48%的宽度
					//$imgObj.attr("width","48%");
					//var maxWidth = $imgObj.width();
					var maxWidth = 100;
					if(orgWidth < maxWidth) {
						$imgObj.attr("width",orgWidth+"px");
					}else{
						$imgObj.attr("width",maxWidth+"px");
					}
				}else{//兼容导出PDF
					var $imgObj = $(obj);
					$imgObj.attr("crossorigin","anonymous");
				}
			});
			$(".muiLbpmserviceSignature img").each(function(index,obj){//兼容导出PDF
				var $imgObj = $(obj);
				$imgObj.attr("crossorigin","anonymous");
			});
		});
	</script>
	<script>Com_IncludeFile("xform.js|calendar.js");</script>
	<link rel="stylesheet" type="text/css" href="<c:url value="/${_formFilePath}.css"/>">
	<script>
	Xform_ObjectInfo.mainModelName = "${_mainModelName}";
	Xform_ObjectInfo.fdKey = "${param.fdKey}";
	Xform_ObjectInfo.formFilePath = "${_formFilePath}";
			Xform_ObjectInfo.formPath = "${formPath}";
			Xform_ObjectInfo.mainDocStatus = "<%=TagUtils.getDocStatus(request)%>";
			Xform_ObjectInfo.mainFormId = "${_xformMainForm.fdId}";
			Xform_ObjectInfo.properties = ${_formProperties};
			Xform_ObjectInfo.fdFormId = "${fdFormId}";
			Xform_ObjectInfo.showStatus = "${requestScope["SysForm.base.showStatus"]}";
			// 设置日期格式
			Xform_ObjectInfo.calendarLang = {
				format : {
					date : "${lfn:message('date.format.date')}",
					time : "${lfn:message('date.format.time')}",
					datetime : "${lfn:message('date.format.datetime')}"
				}
			};

			// 当前用户是否是起草人
			Xform_ObjectInfo.isDrafter = <%=SysFormUtil.isDraft((String)PropertyUtils.getProperty(mainForm, "fdId"))%>;

		</script>
		<xform:config formName="${_formName}" >
			<div class="sysDefineXform">
				<c:import url="/${_formFilePath}.jsp" charEncoding="UTF-8">
					<c:param name="method" value="${param.method}" />
					<c:param name="fdKey" value="${param.fdKey}" />
					<c:param name="formFilePath" value="${_formFilePath}" />
					<c:param name="mainModelName" value="${_mainModelName}" />
					<c:param name="uploadAfterSelect" value="true" />
				</c:import>
			</div>
		</xform:config>
		<script>
			//解析表单view页面存值 作者 曹映辉 #日期 2017年5月18日
			var xform_data_hide={};
			<%
                    Map<String ,Object> map=(java.util.Map)request.getAttribute("view_xform_value");
                    if(map !=null){
                        for(Map.Entry<String ,Object> entry : map.entrySet()){
                            if (entry.getValue() instanceof String) {
                                String val=StringUtil.clearScriptTag((String)entry.getValue());
                                val=StringUtil.XMLEscape(val);
                                /*157079 属性变更换行、回车替换 start*/
                                if(StringUtil.isNotNull(val)&&(val.contains("\n")||val.contains("\r"))){
                                	val = val.replaceAll("\n", "&nquot;");
									val = val.replaceAll("\r", "&rquot;");
                                }
								/*157079 属性变更换行、回车替换 end*/
                                out.println("xform_data_hide[\""+entry.getKey()+"\"]=\""+val+"\";");
                            } else {
                                out.println("xform_data_hide[\""+entry.getKey()+"\"]=\""+entry.getValue()+"\";");
                            }
                        }
                    }
            %>
			//将特殊字符转义还原
			for(var temp in  xform_data_hide){
				if(xform_data_hide[temp]==null){
					continue;
				}
				/*157079 .replace(/&nquot;/g, "\n").replace(/&rquot;/g, "\r")*/
				xform_data_hide[temp]=xform_data_hide[temp].replace(/&amp;/g, "&").replace(/&quot;/g, "\"").replace(/&lt;/g, "<").replace(/&gt;/g, ">").replace(/&nquot;/g, "\n").replace(/&rquot;/g, "\r");
			}
		</script>
		<script>Com_IncludeFile("sysForm_main.js",Com_Parameter.ContextPath+'sys/xform/include/','js',true);</script>
		<% if (isUseTab) {%>
	</td>
	</tr>
<%}%>
<%
	//兼容EKP3.1代码
	pageContext.setAttribute("com.landray.kmss.web.taglib.FormBean", originFormBean, PageContext.REQUEST_SCOPE);
}
pageContext.removeAttribute("_formName",PageContext.REQUEST_SCOPE);
pageContext.removeAttribute("_formFilePath",PageContext.REQUEST_SCOPE);
request.removeAttribute("SysForm.base.showStatus");
request.removeAttribute("SysForm.isPrint");
request.removeAttribute("SysForm.showStatus");
request.removeAttribute("_xformMainForm");
request.removeAttribute("_xformForm");
	request.removeAttribute("fdFormId");
	request.removeAttribute("_formProperties");
if(xform instanceof IExtendForm){
	request.removeAttribute("_mainModelName");
}

%>