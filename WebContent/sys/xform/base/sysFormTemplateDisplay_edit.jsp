<%@page import="com.landray.kmss.sys.xform.util.LangUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.xform.base.config.*" %>
<%@ page import="com.landray.kmss.sys.xform.XFormConstant"%>
<html:hidden property="${sysFormTemplateFormPrefix}fdDesignerHtml" />
<html:hidden property="${sysFormTemplateFormPrefix}fdMetadataXml" />
<html:hidden property="${sysFormTemplateFormPrefix}fdIsChanged" />
<html:hidden property="${sysFormTemplateFormPrefix}fdSaveAsNewEdition" />
<html:hidden property="${sysFormTemplateFormPrefix}fdIsUpTab" />
<html:hidden property="${sysFormTemplateFormPrefix}fdCss"/>
<html:hidden property="${sysFormTemplateFormPrefix}fdCssDesigner"/>
<html:hidden property="${sysFormTemplateFormPrefix}fdName"/>
<html:hidden property="${sysFormTemplateFormPrefix}fdId"/>
<html:hidden property="${sysFormTemplateFormPrefix}fdIsDefWebForm" value="false"/>
<html:hidden property="${sysFormTemplateFormPrefix}fdFragmentSetIds"/>
<html:hidden property="${sysFormTemplateFormPrefix}fdMainModelName"/>
<input type="hidden" name="${sysFormTemplateFormPrefix}fdChangeLog"/>
<input type="hidden" name="mainDataCited" value/>
<script>Com_IncludeFile('json2.js');</script>
<%-- 页面类型 --%> 
<!-- add by duf 判断当前集成自定义表单的模板，是否在插件工厂注册了；注册下面隐藏域，不需要，如果没注册，需要。不然会导致没注册的模块视图页面显示不全 -->
<% if(!(LangUtil.isEnableMultiLang(request.getParameter("fdMainModelName"), "model") && LangUtil.isEnableAdminDoMultiLang())) {%>
<input type="hidden" id="uu_FdContent"
	name="${sysFormTemplateFormPrefix}fdMultiLangContent"
	value='${xFormTemplateForm.fdMultiLangContent}' />
<%} %>

<tbody style="display:none;"><%-- 目前隐藏此行显示  --%> 
<tr style="display:none;">
	<td class="td_normal_title" width=15%>
		<bean:message bundle="sys-xform" key="XFormTemplate.desplayType" />
	</td>
	<td>
		<sunbor:enums property="${sysFormTemplateFormPrefix}fdDisplayType"
			enumsType="sysFormTemplate_displayType" 
			elementType="radio" 
			htmlElementProperties="onclick=XForm_DisplayFormRow(this.value);"/>
	</td>
</tr>
</tbody>
<%-- 已定义表单 --%>
<%
request.setAttribute("sysFormList", ConfigModel.getInstance().getFormPages(request.getParameter("fdMainModelName")));
%>
<tr id="XForm_${HtmlParam.fdKey}_DefinedTemplateRow" style="display:none;">
	<td class="td_normal_title" width=15%>
		<bean:message bundle="sys-xform" key="sysFormCommonTemplate.fdFormFileName" />
	</td>
	<td>
		<c:if test="${empty sysFormList}">
			<bean:message bundle="sys-xform" key="xform.page.noPage" />
		</c:if>
		<c:if test="${not empty sysFormList}">
		<html:select property="${sysFormTemplateFormPrefix}fdFormFileName">
			<html:options collection="sysFormList" 
				property="formFileName"
				labelProperty="label" />
		</html:select>
		</c:if>
	</td>
</tr>
<%-- 自设计 --%>
<!-- lbpmTemplate_sub_edit.jsp里面id为flowContentRow的存在iframe的时候，在火狐下公文管理政务版在编辑页面加载不出来表单，此处的隐藏显示由XForm_DisplayFormRow方法决定 by zhugr 2017-11-08 -->
<tr id="XForm_${HtmlParam.fdKey}_CustomTemplateRow">
	<td class="td_normal_title" colspan=4
		id="TD_FormTemplate_${HtmlParam.fdKey}" ${sysFormTemplateFormResizePrefix}onresize="LoadXForm('TD_FormTemplate_${JsParam.fdKey}');">
		<iframe id="IFrame_FormTemplate_${HtmlParam.fdKey}" width="100%" height="100%" scrolling="yes" FRAMEBORDER=0></iframe>
	</td>
</tr>
<%-- 显示高级选项  --%>
<tr id="XForm_${HtmlParam.fdKey}_AdminTemplateRow" style="display:none;">
	<td class="td_normal_title" colspan="2">
		<label>
			<input id="XForm_${HtmlParam.fdKey}_AdminTemplateCheckbox" type="checkbox" onclick="XForm_ShowAdminItems_${JsParam.fdKey}(this.checked);">
			<bean:message bundle="sys-xform" key="XFormTemplate.adminItems" />
		</label>
	</td>
</tr>
<%-- 表单存取定义
<tr id="XForm_${HtmlParam.fdKey}_DBTemplateRow" style="display:none;">
	<td class="td_normal_title" width=15%>
		<bean:message bundle="sys-xform" key="XFormTemplate.dbDefine" />
	</td>
	<td>
		<a href="javascript:void(0);" onclick="XForm_OpenDBDefine();">
			<bean:message bundle="sys-xform" key="XFormTemplate.dbDefineLink" />
		</a>
		<input type="hidden" id="entityName" value="${entityName}">
		<html:hidden styleId="${HtmlParam.fdKey}_formEntityName" property="${sysFormTemplateFormPrefix}fdFormEntityName" />
	</td>
</tr>
--%>
<%
request.setAttribute("sysFormEvents", ModelEventConfig.getEvents(request.getParameter("fdMainModelName")));
%>
<%-- 表单存取事件扩充 --%>
<tr id="XForm_${HtmlParam.fdKey}_EventTemplateRow" style="display:none;">
	<td class="td_normal_title" width=15%>
		<bean:message bundle="sys-xform" key="XFormTemplate.eventDefine" />
	</td>
	<td>
		<html:select property="${sysFormTemplateFormPrefix}fdFormEvent" 
				styleId="extendDaoEventBean" 
				onchange="this.isChanged='true';">
			<html:option value=""><bean:message bundle="sys-xform" key="xform.event.select" /></html:option>
			<html:options collection="sysFormEvents" 
				property="daoBean"
				labelProperty="label" />
		</html:select>
		<div>
		<bean:message bundle="sys-xform" key="XFormTemplate.eventDefineHint" />
		</div>
	</td>
</tr>
<%--全局样式行 --%>
<tr id="XForm_${HtmlParam.fdKey}_GlobalStyleRow" style="display:none;">
	<td class="td_normal_title" width=15%>
		<bean:message bundle="sys-xform-base" key="Designer_Lang.controlTextGlobalStyle" />
		
	</td>
	<td>
		<a id="set_globalTemplateStyle" href="javascript:void(0)" style="text-decoration:underline">
			<bean:message bundle="sys-xform-base" key="Designer_Lang.controlTextGlobalStyleSet" />
		</a>
		<script>
			$("#set_globalTemplateStyle").click(function(){
				var objData={"cssDesigner":document.getElementsByName("${sysFormTemplateFormPrefix}fdCssDesigner")[0].value};
				var url = Com_Parameter.ContextPath+"sys/xform/designer/globalStyle/globalStyleSet.jsp";
				var width = 850;
				var height = 500;
                var left = (screen.width-width)/2;
                var top = (screen.height-height)/2;
				var callback = function(rtnVal){
                    if(!rtnVal || rtnVal.length==0){
                        //window.console.info("dialog没有返回有效数据");
                        return;
                    }

                    document.getElementsByName("${sysFormTemplateFormPrefix}fdCssDesigner")[0].value=rtnVal;
                    var cssjson=JSON.parse(rtnVal);
                    var styleStr = "";
                    for(var i in cssjson){
                        styleStr += i + " {\n"
                        for(var j in cssjson[i]){
                            styleStr += "\t" + j + ":" + cssjson[i][j] + ";\n"
                        }
                        styleStr += "}\n"
                    }
                    document.getElementsByName("${sysFormTemplateFormPrefix}fdCss")[0].value=styleStr;
                    //多表单时设置子表单全局样式
                    if(typeof Form_getModeValue != "undefined" && Form_getModeValue("${HtmlParam.fdKey}")=="<%=XFormConstant.TEMPLATE_SUBFORM%>"){
                        _Designer_SubFormSetCss(rtnVal,styleStr);
                    }
                };
                var flag = (navigator.userAgent.indexOf('MSIE') > -1) || navigator.userAgent.indexOf('Trident') > -1
                if(window.showModalDialog && flag){  //判断是window系统且是IE浏览器
                    var obj={};
                    obj.data=objData;
                    obj.AfterShow=callback;
                    Com_Parameter.Dialog=obj;
                    var winStyle = "resizable:1;scroll:1;dialogwidth:"+width+"px;dialogheight:"+height+"px;dialogleft:"+left+";dialogtop:"+top;
                    window.showModalDialog(url, obj, winStyle);
                } else {
                    new OpenOnlyDialog_Show(url, objData, callback).setWidth(width).setHeight(height).setOtherStyle("scrollbars=no, resizable=no,dependent=yes,alwaysRaised=1").show();
                }
			});
		</script>
	
	</td>
</tr>
<%@include file="template_script.jsp" %>
<script type="text/javascript">Com_IncludeFile("docutil.js|security.js|dialog.js|formula.js");</script>
<script>
function LoadXForm(dom) {
	XForm_Loading_Show();
	Doc_LoadFrame(dom, '<c:url value="/sys/xform/designer/designPanel.jsp?fdKey=" />${JsParam.fdKey}&fdModelName=${sysFormTemplateForm.modelClass.name}&sysFormTemplateFormPrefix=${sysFormTemplateFormPrefix}');
	var frame = document.getElementById('IFrame_FormTemplate_${JsParam.fdKey}');
	Com_AddEventListener(frame, 'load', XForm_Loading_Hide);
	//多表单配置
	if(typeof SUBForm_Loading != "undefined"){
		Com_AddEventListener(frame, 'load', SUBForm_Loading);
	}
}

function XForm_DisplayFormRowSet() {
	var radios = document.getElementsByName("${sysFormTemplateFormPrefix}fdDisplayType");
	for (var i = radios.length - 1; i >= 0; i --) {
		if (radios[i].checked) {
			XForm_DisplayFormRow(radios[i].value);
		}
	}
}
function XForm_ShowAdminItems_${JsParam.fdKey}(show) {
	<%-- var dbTemplateRow = document.getElementById("XForm_${param.fdKey}_DBTemplateRow"); --%>
	var eventTemplateRow = document.getElementById("XForm_${JsParam.fdKey}_EventTemplateRow");
	var globalStyleRow = document.getElementById("XForm_${JsParam.fdKey}_GlobalStyleRow");
	
	if (show) {
		<%-- dbTemplateRow.style.display = ""; --%>
		eventTemplateRow.style.display = "";
		globalStyleRow.style.display = "";
	} else {
		<%-- dbTemplateRow.style.display = "none"; --%>
		eventTemplateRow.style.display = "none";
		globalStyleRow.style.display = "none";
	}
	if(typeof Form_getModeValue != "undefined" && Form_getModeValue("${JsParam.fdKey}")=="<%=XFormConstant.TEMPLATE_SUBFORM%>"){
		var height = document.getElementById("TB_FormTemplate_${JsParam.fdKey}").offsetHeight-2;
		var myhight = height-38-19;
		$("#SubFormDiv").css("height",myhight*0.5);
		$("#controlsDiv").css("height",myhight*0.5);
		var div = document.getElementById("DIV_SubForm_${JsParam.fdKey}");
		$(div).css("height",height);
	}
}
function XForm_callResizeFun(dom) {
	$(dom).find("*[${sysFormTemplateFormResizePrefix}onresize]").each(function(){
		if (!$(this).is(':visible'))
			return;
		var funStr = this.getAttribute("${sysFormTemplateFormResizePrefix}onresize");
		if(funStr!=null && funStr!=""){
			var tmpFunc = new Function(funStr);
			tmpFunc.call();
		}
	});
}
function XForm_DisplayFormRow(type){
	var definedTemplateRow = document.getElementById("XForm_${JsParam.fdKey}_DefinedTemplateRow");
	var customTemplateRow = document.getElementById("XForm_${JsParam.fdKey}_CustomTemplateRow");
	var adminTemplateRow = document.getElementById("XForm_${JsParam.fdKey}_AdminTemplateRow");
	var adminCheckbox = document.getElementById("XForm_${JsParam.fdKey}_AdminTemplateCheckbox");
	if(type=='1'){
		definedTemplateRow.style.display = "";
		var select = definedTemplateRow.getElementsByTagName('select');
		if (select.length > 0) {select[0].disabled = false;}
		customTemplateRow.style.display = "none";
		adminTemplateRow.style.display = "none";
		XForm_ShowAdminItems(false);
		adminCheckbox.checked = false;
	}else{
		definedTemplateRow.style.display = "none";
		var select = definedTemplateRow.getElementsByTagName('select');
		if (select.length > 0) {select[0].disabled = true;}
		customTemplateRow.style.display = "";
		adminTemplateRow.style.display = "";
	}

	XForm_callResizeFun(customTemplateRow);
	XForm_callResizeFun(adminTemplateRow);
	
	var diplayType = document.getElementsByName('${sysFormTemplateFormPrefix}fdDisplayType');
	var select = definedTemplateRow.getElementsByTagName('select');
	if (select.length == 0) {
		for (var i = 0; i < diplayType.length; i ++) {
			if (diplayType[i].value == '1') {
				diplayType[i].disabled = true;
				return;
			}
		}
	}
}

Com_Parameter.event["confirm"][Com_Parameter.event["confirm"].length] = Xform_BuildValueInConfirm;

//提交的时候，需要对一些元素赋值
function Xform_BuildValueInConfirm(){
	var customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'].contentWindow;
	if(!customIframe){
		customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'];
	}
	var fdModeObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdMode")[0];
	$(fdModeObj).removeAttr("disabled");
	var fdModelValue = 0;
	if(fdModeObj && fdModeObj != null){
		fdModelValue = fdModeObj.value;
	}
	//移动端表单提交前处理
	if (typeof MobileForm_BuildValueInConfirm != "undefined") {
		MobileForm_BuildValueInConfirm();
	}
	
	if(fdModelValue=="<%=XFormConstant.TEMPLATE_SUBFORM%>"){
		//多表单保存时，需要先保存选中表单的信息到对应的隐藏域（防止最后一个表单未保存）
		var tr = $("#TABLE_DocList_SubForm").find("tr[ischecked='true']");
		var fdDesignerHtml,fdMetadataXml;
		if(customIframe.Designer != null && customIframe.Designer.instance != null){
			fdDesignerHtml = customIframe.Designer.instance.getHTML();
			fdMetadataXml = customIframe.Designer.instance.getXML();
		}
		var myfdDesignerHtml = tr.find("input[name$='fdDesignerHtml']");
		var myfdMetadataXml = tr.find("input[name$='fdMetadataXml']");
		if(fdDesignerHtml!=myfdDesignerHtml.val()){
			myfdDesignerHtml.val(fdDesignerHtml);
		}
		if(fdMetadataXml!=myfdMetadataXml.val()){
			myfdMetadataXml.val(fdMetadataXml);
		}
		var defaultTr = $("#TABLE_DocList_SubForm").find('tr:eq(0)');
		
		var fdMainDataCitedObj = document.getElementsByName("mainDataCited")[0];
		//保存基础表单内容
		if (customIframe.Designer && customIframe.Designer.instance && customIframe.Designer.instance.hasInitialized) {
			var fdNameObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdName")[0];
			var fdDesignerHtmlObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdDesignerHtml")[0];
			var fdMetadataXmlObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdMetadataXml")[0];
			var fdCssObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdCss")[0];
			var fdCssDesignerObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdCssDesigner")[0];
			var fdChangeLogObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdChangeLog")[0];
			fdNameObj.value = defaultTr.find("input[name$='fdName']").val();
			var myHtml = defaultTr.find("input[name$='fdDesignerHtml']").val();
			fdDesignerHtmlObj.value = base64Encodex(myHtml);
			fdMetadataXmlObj.value = defaultTr.find("input[name$='fdMetadataXml']").val();
			fdCssObj.value = defaultTr.find("input[name$='fdCss']").val();
			fdCssDesignerObj.value = defaultTr.find("input[name$='fdCssDesigner']").val();
			
			//主数据相关控件信息
			var relationInfo = customIframe.Designer.instance.getRelationControlInfo(true);
			fdMainDataCitedObj.value = JSON.stringify(relationInfo);
			//变更日志
			var compareResult = customIframe.Designer.instance.compare();
			if (compareResult){
				fdChangeLogObj.value = compareResult;
			}
		}else{
			var fdDesignerHtmlObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdDesignerHtml")[0];
			fdDesignerHtmlObj.value = base64Encodex(fdDesignerHtmlObj.value);
		}
		//去掉disabled属性，否则无法映射该字段到form
		var select = $("[name='sysFormTemplateForms.${JsParam.fdKey}.fdMode']");
		if(select.length > 0){
			select.prop('disabled',false);
		}
	}else{
		var fdDesignerHtmlObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdDesignerHtml")[0];
		//确认是否更新为新的版本
		var fdIsChangedObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdIsChanged")[0];
		
		//设置片段集id
		var fdFragmentSetObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdFragmentSetIds")[0];
		var fdMetadataXmlObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdMetadataXml")[0];
		var fdChangeLogObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdChangeLog")[0];
		
		var fdMainModelNameObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdMainModelName")[0];
		fdMainModelNameObj.value = window._xform_MainModelName;
		
		var fdMainDataCitedObj = document.getElementsByName("mainDataCited")[0];
		
		if(fdIsChangedObj.value == 'true'){
			var fdDesignerHtml = null;
			if(customIframe.Designer != null && customIframe.Designer.instance != null){
				fdDesignerHtml = customIframe.Designer.instance.getHTML();
				fdDesignerHtmlObj.value = fdDesignerHtml;
			}
		}
		
		//片段集
		var fdFragmentSetIds = [];
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
			//主数据相关控件信息
			var relationInfo = customIframe.Designer.instance.getRelationControlInfo(false);
			fdMainDataCitedObj.value = JSON.stringify(relationInfo);
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
		var defaultTr = $("#TABLE_DocList_SubForm").find('tr:eq(0)');
		defaultTr.find("input[name$='fdDesignerHtml']").val(fdDesignerHtmlObj.value);
		//html内容如果已经加密则不再重复加密 2017-11-20 王祥
		fdDesignerHtmlObj.value = base64Encodex(fdDesignerHtmlObj.value);
		
		//清除多表单信息
		$("#TABLE_DocList_SubForm").find('tr:gt(0)').each(function(){
			DocList_DeleteRow(this);
		});
	}
	//BASE64处理子表单脚本内容	
	$("#TABLE_DocList_SubForm").find('tr[ischecked]').each(function(){
		var subHtml = $(this).find("input[name$='fdDesignerHtml']");
		subHtml.val(Xform_Base64Encodex(subHtml.val(), customIframe, $(this)));
	});
	return true;
}


Com_Parameter.event["submit_failure_callback"][Com_Parameter.event["submit_failure_callback"].length] = function(){
	var customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'].contentWindow;
	if(!customIframe){
		customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'];
	}
	var fdDesignerHtmlObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdDesignerHtml")[0];
	if(customIframe && customIframe.Designer){
		if(customIframe.Designer.instance.designerHtmls){
			$("#TABLE_DocList_SubForm").find('tr[ischecked]').each(function(){
				var subHtml = $(this).find("input[name$='fdDesignerHtml']");
				subHtml.val(customIframe.Designer.instance.designerHtmls[$(this).attr("id")]);
			});
			var defaultTr = $("#TABLE_DocList_SubForm").find('tr:eq(0)');
			fdDesignerHtmlObj.value = defaultTr.find("input[name$='fdDesignerHtml']").val();
		}
	}else{
		$("#TABLE_DocList_SubForm").find('tr[ischecked]').each(function(){
			var subHtml = $(this).find("input[name$='fdDesignerHtml']");
			if(subHtml.val().indexOf('\u4645\u5810\u4d40') > -1){
				var vData = {"fdDesignerHtml":subHtml.val()};
				subHtml.val(XForm_Base64Decodex(vData));	
			}
		});
		var defaultTr = $("#TABLE_DocList_SubForm").find('tr:eq(0)');
		fdDesignerHtmlObj.value = defaultTr.find("input[name$='fdDesignerHtml']").val();
	}
};

//表单HTML解码
function XForm_Base64Decodex(arg) {
	var result = null;
	$.ajax({
		url: Com_Parameter.ContextPath + "sys/lbpm/engine/jsonp.jsp?s_bean=convertBase64ToHtmlService",
		async: false,
		data: arg,
		type: "POST",
		dataType: 'json',
		success: function (data) {
			result = data.html;
		},
		error: function (er) {

		}
	});
	return result;
}

// 表单HTML加密
function Xform_Base64Encodex(val, customIframe, obj){
	if(val.indexOf("\u4645\u5810\u4d40") < 0){
		if(customIframe.Designer){
			// 保存加密前的HTML
			if(obj && obj.length>0){
				if(!customIframe.Designer.instance.designerHtmls){
					customIframe.Designer.instance.designerHtmls = {};
				}
				customIframe.Designer.instance.designerHtmls[obj.attr("id")] = val;
			}
		}
		//BASE64处理脚本内容	
		val = base64Encodex(val);
	}
	return val;
}

//提交前版本确认校验
function XForm_BeforeSubmitForm(callback){
	if(callback){
		// 为兼容历史数据，有可能项目在其他模块部署了表单，但是没有在业务模块进行相应的更改，所以不能直接注释提交时执行的XForm_ConfirmFormChangedEvent方法，这里删除confirm 数组里面的XForm_ConfirmFormChangedEvent方法
		for(var i = 0;i < Com_Parameter.event["confirm"].length ; i++){
			if(Com_Parameter.event["confirm"][i] === XForm_ConfirmFormChangedEvent){
				Com_Parameter.event["confirm"].splice(i,1);
			}
		}
		XForm_ConfirmFormChangedEvent(null,null,null,null, true , callback);	
	}
}

//确认表单被改动时是否存成新版本
function XForm_ConfirmFormChangedFun(isNotSupportModalDailog , callback){
	var isClone = "${requestScope['isCloneAction']}";
	var fdTypeObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdDisplayType");
	var fdTypeValue = "0";
	for(var i = 0; i < fdTypeObj.length; i++){
		if(fdTypeObj[i].checked){
			fdTypeValue = fdTypeObj[i].value;
			break;
		}
	}
	if(fdTypeValue == "2"){
		var fdDesignerHtmlObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdDesignerHtml")[0];
		var fdMetadataXmlObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdMetadataXml")[0];
		var customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'].contentWindow;
		if(!customIframe){
			customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'];
		}
		var fdDesignerHtml = null;
		var fdIsChanged = false;
		var eventChanged = ("true" == document.getElementById('extendDaoEventBean').isChanged);
		if(customIframe.Designer != null && customIframe.Designer.instance != null){
			if(typeof Form_getModeValue != "undefined" && Form_getModeValue("${HtmlParam.fdKey}")=="<%=XFormConstant.TEMPLATE_SUBFORM%>"){
				var checkedTR = $("#TABLE_DocList_SubForm").find("tr[ischecked='true']");
				var ischecked = false;
				SubFormData.needLoad_subform = false;
				$("#TABLE_DocList_SubForm").find("tr").each(function(){
					$(this).find("a[name='subFormText']").click();
					if(!customIframe.Designer.instance.checkoutAll()){
						//表单绘制数据不合法，不允许提交
						ischecked = true;
						return false;
					}
				});
				SubFormData.needLoad_subform = true;
				if(ischecked){
					return false;
				}else{
					checkedTR.find("a[name='subFormText']").click();
				}
				if(!SubForm_checkAll()){
					//多表单同id类型、label校验
					return false;
				}
			}else if(!customIframe.Designer.instance.checkoutAll() ){//!customIframe.Designer.instance.isNeedBreakValidate &&
				//表单绘制数据不合法，不允许提交
				return false;
			}
			fdDesignerHtml = customIframe.Designer.instance.getHTML();
		}
		
		//确认是否更新为新的版本
		var fdIsChangedObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdIsChanged")[0];
		//是否提升
		var fdIsUpTabObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdIsUpTab")[0];
	 	var fdSaveAsNewEditionObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdSaveAsNewEdition")[0];
	 	//保存并新建时无需提示是否保存为新版本 作者 曹映辉 #日期 2015年8月13日
		var method = Com_GetUrlParameter(location.href,"method");
	 	var isSubform = typeof Form_getModeValue != "undefined" && Form_getModeValue("${HtmlParam.fdKey}")=="<%=XFormConstant.TEMPLATE_SUBFORM%>";
	 	var modeDao = "${xFormTemplateForm.fdMode}";
	 	var isAdd = false;
	 	if(fdDesignerHtmlObj.value == null || fdDesignerHtmlObj.value == ''|| method == 'saveadd' || (isSubform && modeDao!="<%=XFormConstant.TEMPLATE_SUBFORM%>")){
			// 新建的时候，直接提交
			fdSaveAsNewEditionObj.value = "true";
			isAdd = true;
			fdIsChanged = true;
		}else if (isClone == 'true') {
			fdIsChanged = true;
		}else if(customIframe.Designer != null ){
			if(XForm_HasEncryptChange(customIframe.Designer.instance,isSubform)){
				if(window.confirm("<bean:message bundle='sys-xform' key='sysFormTemplate.confirm.encryptchange'/>")){
					fdSaveAsNewEditionObj.value = "true";
					fdIsChanged = true;
				}else{
					return false;
				}
			}else if(XForm_HasIsMarkChange(customIframe.Designer.instance,isSubform)){//是否留痕判断
				if(window.confirm("<bean:message bundle='sys-xform' key='sysFormTemplate.confirm.isMarkchange'/>")){
					fdSaveAsNewEditionObj.value = "true";
					fdIsChanged = true;
				}else{
					return false;
				}
			}else if((!isSubform && customIframe.Designer.instance.isChanged) || eventChanged || (isSubform && SubFormData.isChanged)){
				/*
				*增加数据字典变化默认选择新保存为新版本，数据字典未变化默认选择保存为当前版本的功能
				*@作者：曹映辉 @日期：2012年6月4日 
				*/
				var filterXml=function(str){
					if(!str){
						return "";
					}
					//将xml中连续两个以上的空格 替换为 一个
					str=str.replace(/\s{2,}/g," ");
					//替换掉换行
					str=str.replace(/[\r\n]+/g," ");
					//将xml中label 属性去除。（数据字典中label属性的改变不列如入默认作为新模版的条件）
					str=str.replace(/\s+label=\s*[\S]+\s*/gi," ");
					return str;
				}
				
				//false:默认保存为原版本，ture：默认保存为新版本
				var isDefualtNew=false;
				if(typeof Form_getModeValue != "undefined" && Form_getModeValue("${HtmlParam.fdKey}")=="<%=XFormConstant.TEMPLATE_SUBFORM%>"){
					//多表单保存时，校验是否需要保存新版本
					if(SubFormData.saveasNew_subform){
						isDefualtNew=true;
					}
				}else{
					var newXML = customIframe.Designer.instance.getXML();
					var oldXML = fdMetadataXmlObj.value;
					if(oldXML && newXML){
						if(filterXml(oldXML) != filterXml(newXML)){
							isDefualtNew=true;
						}
					}
				}
				
				fdIsChanged = true;
				var url = '<c:url value="/resource/jsp/frame.jsp" />?url=' + encodeURIComponent('<c:url value="/sys/xform/include/sysFormConfirm.jsp?isDefualtNew='+isDefualtNew+'" />');
				//不支持ModalDialog的浏览器 主要是chrome，主要由于chrome下面无法阻塞线程，导致直接提交代码 start
				if(isNotSupportModalDailog && callback){
					if (typeof(seajs) != 'undefined' && typeof( top['seajs'] ) != 'undefined' ) {
						var msg = [];
						msg.push('<table class="tb_normal" style="word-break:break-all">');
						msg.push('<tr><td style="font-size:12px;">');
						msg.push('<bean:message bundle='sys-xform' key='sysFormTemplate.confirm.saveAsANewEdition'/>');
						msg.push('</td></tr></table>');
						msg.push('<div style="margin-top: 10px;text-align: center;">');
						msg.push('<label><input type="radio" name="mouldChoose"  value="NewSubmit" '+(isDefualtNew?'checked=true':'')+'><bean:message bundle='sys-xform' key='sysFormTemplate.confirm.button.new'/></input></label>&nbsp;&nbsp;');
						msg.push('<label><input type="radio" name="mouldChoose"  value="OldSubmit" '+(isDefualtNew?'':'checked=true')+'><bean:message bundle='sys-xform' key='sysFormTemplate.confirm.button.old'/></input></label>');
						msg.push('</div>');
						seajs.use( [ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
							var dialog = dialog.build({
								config : {
									width : 400,
									cahce : false,
									title : "<bean:message bundle='sys-xform' key='sysFormTemplate.confirm.title'/>",
									content : {
										type : "common",
										html : msg.join(''),
										iconType : '',
										buttons : [ {
											name : "${lfn:message('button.ok')}",
											value : true,
											focus : true,
											fn : function(value, dialog) {
												if(document.getElementsByName('mouldChoose')[0].checked) {
													fdSaveAsNewEditionObj.value = "true";
												}else{
													fdSaveAsNewEditionObj.value = "false";
												}
												if (fdIsChanged) {
													//修改表单内容被修改的处理，由于clone的加入，须判断有无加载自定义表单页面，而不是简单的作为已修改表单内容处理 modify by limh 2010年10月21日	
													if(customIframe.Designer){
														fdIsChangedObj.value = "true";
														fdMetadataXmlObj.value = customIframe.Designer.instance.getXML();
														fdIsUpTabObj.value=customIframe.Designer.instance.isUpTab;
													}
												}
												callback();
												dialog.hide(value);
											}
										}, {
											name : "${lfn:message('button.cancel')}",
											value : false,
											styleClass : 'lui_toolbar_btn_gray',
											fn : function(value, dialog) {
												dialog.hide(value);
											}
										} ]
									}
								}
							}).on('show', function() {
								this.element.find(".lui_dialog_common_content_right").css("max-width","100%");
								this.element.find(".lui_dialog_common_content_right").css("margin-left","0px");
							}).show();
						});
					}else{
						var winOpen = XForm_PopupWindow(url, window.screen.width*400/1366,window.screen.height*230/768);
						
						winOpen.beforeunloadFun = function(){
							var returnValue = winOpen;
							if(winOpen && winOpen.returnValue){
								returnValue = winOpen.returnValue;
							}
							if(returnValue.closed == true){
								// 先删除监控，以免callback执行过长，又进入该方法，陷入死循环
								//window.clearInterval(listenWindowClosed);
								// 当submit存在，且为true的时候，才执行以下变更
								if(returnValue.submit){
									if(returnValue.isNew == true) {
										fdSaveAsNewEditionObj.value = "true";
									}else{
										fdSaveAsNewEditionObj.value = "false";
									}
									if (fdIsChanged) {
										//修改表单内容被修改的处理，由于clone的加入，须判断有无加载自定义表单页面，而不是简单的作为已修改表单内容处理 modify by limh 2010年10月21日	
										if(customIframe.Designer){
											fdIsChangedObj.value = "true";
											fdMetadataXmlObj.value = customIframe.Designer.instance.getXML();
											fdIsUpTabObj.value=customIframe.Designer.instance.isUpTab;
										}
									}
									callback();
								}
								return;
							}
						};
					}
					return;
				}
				// end
				if(window.showModalDialog){
					//版本不同，确认是否存为新版本
					var flag = Dialog_PopupWindow(url, window.screen.width*400/1366,window.screen.height*230/768);
					if (flag.submit == false) return false;
					if(flag.isNew == true) {
						fdSaveAsNewEditionObj.value = "true";
					} 
				}else if(isDefualtNew){
					//系统建议默认新版本
					 var rtn= window.confirm("<bean:message bundle='sys-xform' key='sysFormTemplate.confirm.mutibrower.new.title'/>");
					 if(rtn){
					 	fdSaveAsNewEditionObj.value = "true";
					 }
					 else{
						 rtn= window.confirm("<bean:message bundle='sys-xform' key='sysFormTemplate.confirm.mutibrower.old.title'/>");
						 if(!rtn){
							 return false;
						 }
					 }
				}else{
					var rtn= window.confirm("<bean:message bundle='sys-xform' key='sysFormTemplate.confirm.mutibrower.old.title'/>");
					 if(!rtn){
						 rtn= window.confirm("<bean:message bundle='sys-xform' key='sysFormTemplate.confirm.mutibrower.new.title'/>");
						 if(rtn){
						 	fdSaveAsNewEditionObj.value = "true";
						 }
						 else{
							 return false;
						 }
					 }
				}	
			}else if(fdIsUpTabObj.value != customIframe.Designer.instance.isUpTab){
				fdIsChanged = true;
			}
		}
		
		if (fdIsChanged) {
			//修改表单内容被修改的处理，由于clone的加入，须判断有无加载自定义表单页面，而不是简单的作为已修改表单内容处理 modify by limh 2010年10月21日	
			if(customIframe.Designer){
				fdIsChangedObj.value = "true";
				fdMetadataXmlObj.value = customIframe.Designer.instance.getXML();
				fdIsUpTabObj.value = customIframe.Designer.instance.isUpTab;
			} else {
				if (isAdd) {
					fdIsChangedObj.value = "true";
				}
			}
		}
		
		if(isNotSupportModalDailog && callback){
			callback();
		}
	}
	return true;
}

function XForm_PopupWindow(url,width, height, parameter){
	width = width==null?window.screen.width*640/1366:width;
	height = height==null?window.screen.height*820/768:height;
	var left = (screen.width-width)/2;
	var top = (screen.height-height)/2;
	// 模态窗口
	if(window.showModalDialog){
		var winStyle = "resizable:1;scroll:1;dialogwidth:"+width+"px;dialogheight:"+height+"px;dialogleft:"+left+";dialogtop:"+top;
		return window.showModalDialog(url, parameter, winStyle);
	}else{
		var winStyle = "resizable=1,scrollbars=1,width="+width+",height="+height+",left="+left+",top="+top+",dependent=yes,alwaysRaised=1";
		Com_Parameter.Dialog = this;
		var tmpwin = window.open(url, "_blank", winStyle);
		if(tmpwin == undefined || tmpwin == "undefined")
			return window.returnValue;
		else{
			return tmpwin;
		}
	}
}

// 判断表单中是否有加密属性发生了变更
function XForm_HasEncryptChange(desingerInstance,isSubform){
	var hasChange = XForm_HasControlsEncryptChange(desingerInstance.builder.controls);
	return hasChange;
}

function XForm_HasControlsEncryptChange(controls){
	var hasChange = false;
	for(var i = 0;i < controls.length;i++){
		var control = controls[i];
		if(control.children && control.children.length > 0){
			if(XForm_HasControlsEncryptChange(control.children)){
				hasChange = true;
				break;
			}
		}else{
			var _encryptChange = control.options.values["_encryptChange"];
			if(_encryptChange && _encryptChange == 'true'){
				hasChange = true;
				break;
			}
		}
	}
	return hasChange;
}

//判断表单中是否留痕属性发生了变更
function XForm_HasIsMarkChange(desingerInstance,isSubform){
	var hasChange = XForm_HasControlsIsMarkChange(desingerInstance.builder.controls);
	return hasChange;
}

function XForm_HasControlsIsMarkChange(controls){
	var hasChange = false;
	for(var i = 0;i < controls.length;i++){
		var control = controls[i];
		if(control.children && control.children.length > 0){
			if(XForm_HasControlsIsMarkChange(control.children)){
				hasChange = true;
				break;
			}
		}else{
			var _isMarkChange = control.options.values["_isMarkChange"];
			if(_isMarkChange && _isMarkChange == 'true'){
				hasChange = true;
				break;
			}
		}
	}
	return hasChange;
}

</script>
	<link rel="stylesheet" type="text/css" href="<c:url value="/component/locker/resource/jNotify.jquery.css"/>" media="screen" />
	<script type="text/javascript" src="<c:url value="/component/locker/resource/jNotify.jquery.js"/>"></script>
	
<script>

$(document).ready(function() {
	if (typeof(seajs) != 'undefined' && typeof( top['seajs'] ) != 'undefined' ) {
		seajs.use("lui/dialog");	
	}
});
</script>