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
<input type="hidden" name="mainDataCited" value/>
<script>Com_IncludeFile('json2.js');</script>
<%-- 页面类型 --%>
<!-- add by duf 判断当前集成自定义表单的模板，是否在插件工厂注册了；注册下面隐藏域，不需要，如果没注册，需要。不然会导致没注册的模块视图页面显示不全 -->
<% if(!(LangUtil.isEnableMultiLang(request.getParameter("fdMainModelName"), "model") && LangUtil.isEnableAdminDoMultiLang())) {%>
<input type="hidden" id="uu_FdContent"
	name="${sysFormTemplateFormPrefix}fdMultiLangContent"
	value='${xFormTemplateForm.fdMultiLangContent}' />
<%} %>

<%-- 已定义表单 --%>
<%
request.setAttribute("sysFormList", ConfigModel.getInstance().getFormPages(request.getParameter("fdMainModelName")));
%>
<%-- 自设计 --%>
<!-- lbpmTemplate_sub_edit.jsp里面id为flowContentRow的存在iframe的时候，在火狐下公文管理政务版在编辑页面加载不出来表单，此处的隐藏显示由XForm_DisplayFormRow方法决定 by zhugr 2017-11-08 -->
<!-- 多表单 -->
<table style="width:100%" id="SubFormTable_${JsParam.fdKey}">
	<!-- 移动端设计 -->
	<tr  id="XForm_${HtmlParam.fdKey}_CustomTemplateMobileRow">
		<td colspan=3>
			<%@include file="/sys/modeling/base/form/xform/sysFormTemplate_mobile_designer.jsp"%>
		</td>
	</tr>
	<tr id="XForm_${HtmlParam.fdKey}_CustomTemplateRow">
		<td style="width: 20%;display:none;" id="SubForm_main_tr">
			<div id="DIV_SubForm_${JsParam.fdKey}" style="border:1px #d2d2d2 solid;">
			<div id="Sub_title_div" style="height:36px;border-bottom:1px solid #d2d2d2;">
				<table class="subTable" style="width:100%">
					<tr>
						<td style="width: 38%;padding:8px"><div style="margin-left:4px;font-size:12px;font-weight:bold;"><bean:message bundle="sys-xform" key="sysSubFormTemplate.multiform_configuration" /></div></td>
						<td style="width: 32%;padding:8px"><a id="operationA2" style="cursor:pointer;color:#47b5e6;font-weight:bold;font-size:12px;" onclick="Designer_SubForm_Xml();"><bean:message bundle="sys-xform" key="sysSubFormTemplate.view_xml" /></a></td>
						<td style="width: 30%;padding:8px" align="right">
							<a id="newDefaultWebForm" style="cursor:pointer;" onclick="addMobileDefaultRow();">
								<img src="${KMSS_Parameter_ContextPath}sys/xform/base/resource/icon/default_mobile.png" border="0" title="<bean:message bundle="sys-xform" key="sysSubFormTemplate.webform_config" />"/>
							</a>&nbsp;
							<a href="javascript:void(0)" style="cursor:pointer;" onclick="SubForm_AddRow(this,false);">
								<img src="${KMSS_Parameter_ContextPath}sys/xform/base/resource/icon/add.png" border="0" title="<bean:message bundle="sys-xform" key="sysSubFormTemplate.add" />"/>
							</a>
						</td>
					</tr>
				</table>
			</div>
			<c:set var="mobile" value="true" />
			<div id="SubFormDiv" style="overflow-x:hidden;overflow-y:auto;text-align:center;">
				<%@include file="/sys/xform/base/sysSubFormTemplate_edit.jsp"%>
			</div>
			<div id="SubFormLoadMsg" style="font-weight:bold;display:none;color:rgb(153, 153, 153);margin-left: 10px;"><bean:message bundle="sys-xform" key="sysSubFormTemplate.loadMsg" /></div>
			<div id="controlsDiv" style="overflow-x:hidden;overflow-y:auto;">
			</div>
			</div>
		</td>
		<td style="width:7px;display:none" id="SubForm_operation_tr">
			<image id="Subform_operation" style="cursor:pointer;" src="${KMSS_Parameter_ContextPath}sys/xform/base/resource/icon/varrowleft.gif" onclick="_Designer_SubFormAddHide(this);">
		</td>
		<td class="td_normal_title"
			id="TD_FormTemplate_${HtmlParam.fdKey}" ${sysFormTemplateFormResizePrefix}onresize="LoadXForm('TD_FormTemplate_${JsParam.fdKey}');" colspan="3">
			<iframe id="IFrame_FormTemplate_${HtmlParam.fdKey}" width="100%" height="100%" scrolling="yes" FRAMEBORDER=0></iframe>
		</td>
	</tr>
</table>


<%-- 显示高级选项  --%>
<tr id="XForm_${HtmlParam.fdKey}_AdminTemplateRow" style="display:none;">
	<td class="td_normal_title" colspan="2">
		<label>
			<input id="XForm_${HtmlParam.fdKey}_AdminTemplateCheckbox" type="checkbox" onclick="XForm_ShowAdminItems_${JsParam.fdKey}(this.checked);">
			<bean:message bundle="sys-xform" key="XFormTemplate.adminItems" />
		</label>
	</td>
</tr>
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
<tr id="XForm_${param.fdKey}_GlobalStyleRow" style="display:none;">
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
				 new OpenOnlyDialog_Show(Com_Parameter.ContextPath+"sys/xform/designer/globalStyle/globalStyleSet.jsp",objData,function(rtnVal){
				    	if(!rtnVal || rtnVal.length==0){
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
				 }).setWidth(850).setHeight(500).setOtherStyle("scrollbars=no, resizable=no,dependent=yes,alwaysRaised=1").show();


			});
		</script>

	</td>
</tr>
<%@include file="/sys/xform/base/template_script.jsp" %>
<script type="text/javascript">Com_IncludeFile("docutil.js|security.js|dialog.js|formula.js");</script>
<script>
function LoadXForm(dom) {
	XForm_Loading_Show();
	Doc_LoadFrame(dom, '<c:url value="/sys/xform/designer/designPanel.jsp?fdKey=" />${JsParam.fdKey}&fdModelName=${sysFormTemplateForm.modelClass.name}&sysFormTemplateFormPrefix=${sysFormTemplateFormPrefix}&encrypt=false');
	var frame = document.getElementById('IFrame_FormTemplate_${JsParam.fdKey}');
	Com_AddEventListener(frame, 'load', XForm_Loading_Hide);
	//多表单配置
	if(typeof SUBForm_Loading != "undefined"){
		Com_AddEventListener(frame, 'load', function(){
		    setTimeout(function (
            ){SUBForm_Loading()}, 500);
        });
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

	if(!XForm_ConfirmFormChangedFun()){
		return false;
	}

	var customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'].contentWindow;
	if(!customIframe){
		customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'];
	}
	var fdModeObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdMode")[0];
	var fdModelValue = 0;
	if(fdModeObj && fdModeObj != null){
		fdModelValue = fdModeObj.value;
	}

	//移动端表单提交前处理
	MobileForm_BuildValueInConfirm();

	var fdMainModelNameObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdMainModelName")[0];
	fdMainModelNameObj.value = window._xform_MainModelName;

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
			fdNameObj.value = defaultTr.find("input[name$='fdName']").val();
			var myHtml = defaultTr.find("input[name$='fdDesignerHtml']").val();
			fdDesignerHtmlObj.value = base64Encodex(myHtml);
			fdMetadataXmlObj.value = defaultTr.find("input[name$='fdMetadataXml']").val();
			fdCssObj.value = defaultTr.find("input[name$='fdCss']").val();
			fdCssDesignerObj.value = defaultTr.find("input[name$='fdCssDesigner']").val();

			//主数据相关控件信息
			var relationInfo = customIframe.Designer.instance.getRelationControlInfo(true);
			fdMainDataCitedObj.value = JSON.stringify(relationInfo);
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
			fdDesignerHtml = customIframe.Designer.instance.getHTML();
			fdDesignerHtmlObj.value = fdDesignerHtml;
			fdMetadataXmlObj.value = customIframe.Designer.instance.getXML();
			fdFragmentSetObj.value = arr.join(";");
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
	//fdDesignerHtmlObj.value = base64Encodex(fdDesignerHtmlObj.value);
	//BASE64处理子表单脚本内容
	$("#TABLE_DocList_SubForm").find('tr[ischecked]').each(function(){
		var subHtml = $(this).find("input[name$='fdDesignerHtml']");
		subHtml.val(Xform_Base64Encodex(subHtml.val(), customIframe, $(this)));
	});

	return true;
}

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
function XForm_ConfirmFormChangedFun(){
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
		if(!customIframe.Designer.instance.checkoutAll() ){//!customIframe.Designer.instance.isNeedBreakValidate &&
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
	//var method = Com_GetUrlParameter(location.href,"method");
 	var modeDao = "${xFormTemplateForm.fdMode}";
 	if(customIframe.Designer != null ){
		if((customIframe.Designer.instance.isChanged) || eventChanged){
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
			fdIsChanged = true;
			// 不做版本控制，默认保存为旧版本--业务建模
			fdSaveAsNewEditionObj.value = "false";
			fdMetadataXmlObj.value = customIframe.Designer.instance.getXML();
		}else if(fdIsUpTabObj.value != customIframe.Designer.instance.isUpTab){
			fdIsChanged = true;
		}
	}

	if (fdIsChanged) {
		fdIsChangedObj.value = "true";
		//修改表单内容被修改的处理，由于clone的加入，须判断有无加载自定义表单页面，而不是简单的作为已修改表单内容处理 modify by limh 2010年10月21日
		if(customIframe.Designer){
			fdMetadataXmlObj.value = customIframe.Designer.instance.getXML();

		}
	}

	/* if(isNotSupportModalDailog && callback){
		callback();
	} */

	return true;
}

//比对变更前后的数据 暂不需要
function XForm_CompareModifiedData(config){
	var url = Com_Parameter.ContextPath + "sys/modeling/modelingAppCategory.do?method=compareForm2DbTable";
	$.ajax({
        url:url,
        type:"post",
        dataType : 'text',
        data:JSON.stringify(config),
        cache : false,
        async : false, // 同步
        contentType: "application/json",
        success:function(data){
            if(data){
            	console.log(data);
            }
        },
        error: function(err) {

        }
    });
}

// 判断表单中是否有加密属性发生了变更
function XForm_HasEncryptChange(desingerInstance){
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

function SubForm_TextBlur(self){
	var value = self.value;
	//校验表单名称是否为空
	if(!value){
		$(self).attr("onblur","");
		seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
			dialog.alert("<bean:message bundle="sys-xform" key="sysSubFormTemplate.notnull" />",function(){
				$(self).attr("onblur","SubForm_TextBlur(this)");
				$(self).select();
			},null,null,null,$('.lui_app_moduleMain_frame'));
		});
		return
	}
	//校验表单名称是否存在
	var input = $("#TABLE_DocList_SubForm").find("[name$='fdName']");
	for(var i =0;i<input.length;i++){
		if(input[i]!=self && input[i].value==value){
			$(self).attr("onblur","");
			seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
				dialog.alert("<bean:message bundle="sys-xform" key="sysSubFormTemplate.check_name" />",function(){
					$(self).attr("onblur","SubForm_TextBlur(this)");
					$(self).select();
				},null,null,null,$('.lui_app_moduleMain_frame'));
			});
			return
		}
	}
	var a = $(self).parent().find("a[name='subFormText']");
	a.attr("title",value);
	if(value.length>10){
		value = value.substring(0,10)+"..."
	}
	a.text(value);
	a.show();
	$(self).hide();
}


</script>

<script>

$(document).ready(function() {
	if (typeof(seajs) != 'undefined' && typeof( top['seajs'] ) != 'undefined' ) {
		seajs.use("lui/dialog");
	}
});
</script>