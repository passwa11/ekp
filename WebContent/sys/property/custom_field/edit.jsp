<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.language.utils.SysLangUtil" %>
<%@ page import="com.landray.kmss.sys.property.custom.DynamicAttributeField" %>
<%@ page import="com.landray.kmss.sys.property.custom.DynamicAttributeFieldEnum" %>
<%@ page import="com.landray.kmss.util.ResourceUtil" %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>
<%@ page import="java.util.Map" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.edit" sidebar="auto">
	<%-- 按钮栏 --%>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
			<c:choose>
				<c:when test="${ method_GET == 'add' }">
					<ui:button text="${lfn:message('button.save')}" order="1" onclick="saveForm('save');"></ui:button>
					<ui:button text="${lfn:message('button.saveadd')}" order="2" onclick="saveForm('saveadd');"></ui:button>
				</c:when>
				<c:when test="${ method_GET=='edit' }">
					<ui:button text="${lfn:message('button.save')}" order="1" onclick="saveForm('save');"></ui:button>	
				</c:when>
			</c:choose>		
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<%-- 路径 --%>
	<template:replace name="path">
		<div class="lui_form_path_frame" style="margin:0px auto;">
			<div class="lui_menu_frame_nav">
				<div class="lui_menu_left">
					<div class="lui_menu_right">
						<div class="lui_menu_content">
							<div class="lui_menu_item">
								<div class="lui_item_left"><div class="lui_item_right"><div class="lui_item_content"><div class="lui_item_txt" style="max-width:100%">${lfn:message('home.homeAnother')}${HtmlParam.s_path}</div></div></div></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</template:replace>
	<%-- 内容 --%>
	<template:replace name="content">
		<script>
			Com_IncludeFile('calendar.js');
			Com_IncludeFile('doclist.js|jquery.js|plugin.js|dialog.js');
			
			// 原始的字符长度
			var __fieldLength = '${dynamicAttributeField.fieldLength}';
			var __fieldType = '${dynamicAttributeField.fieldType}';
			function fieldTypeChange(val) {
				if(!val) val = __fieldType;
				
				// 显示或隐藏“字符长度”
				$($("td [name=fieldLength_tr]").parent().children()[1]).removeAttr("colspan");
				$("td [name=fieldLength_tr]").show();
				$("input[name=fieldLength]").removeAttr("disabled");
				$("input[name=fieldLength]").val(__fieldLength);
				if("java.util.Date" == val || "com.landray.kmss.sys.organization.model.SysOrgElement" == val) {
					$($("td [name=fieldLength_tr]").parent().children()[1]).attr("colspan", "3");
					$("td [name=fieldLength_tr]").hide();
					$("input[name=fieldLength]").attr("disabled", "disabled");
				}
				
				// 显示或隐藏“精度”
				$("#scale_span").hide();
				$("#scale_span input").attr("disabled", "disabled");
				if("java.lang.Double" == val) {
					$("#scale_span").show();
					$("#scale_span input").removeAttr("disabled");
				}
				
				// 显示模式
				var _mode = "";
				if(val =="com.landray.kmss.sys.organization.model.SysOrgElement"){
					_mode = "displayMode_SysOrg";
				}else{
					_mode = "displayMode_" + val.split(".")[2]
				}
				
				$("#displayMode").html($("#" + _mode).html());
				var _mode_val = "${dynamicAttributeField.displayType}";
				// 选中已勾选的项
				if(val == "com.landray.kmss.sys.organization.model.SysOrgElement"){
					if(__fieldType == val && _mode_val){
						var mode_vals = _mode_val.split(";");
						for(var i = 0;i <mode_vals.length;i++){
							$("#displayMode :checkbox[value="+mode_vals[i]+"]").click();
						}
					}
					
					var isMulti = "${dynamicAttributeField.isMulti}"; 
					if(__fieldType == val && isMulti){
						$("#displayMode :radio[value="+isMulti+"]").click();
						$("#displayMode :radio[value=false]").attr("disabled", "disabled");
						$("#displayMode :radio[value=true]").attr("disabled", "disabled");
					}
						
				}else{
					if(__fieldType == val && _mode_val)
						$("#displayMode :radio[value="+_mode_val+"]").click();
					else
						$("#displayMode :radio").first().click();
				}
				
				// “整数”和“浮点”类型时，字符长度和枚举值要修改校验
				var fieldLength = $("input[name=fieldLength]");
				var __validate = fieldLength.attr("validate").split(" ");
				for(var i=0; i<__validate.length; i++) {
					if(__validate[i].indexOf("max") != -1) {
						__validate.splice(i, 1);
					}
				}
				if("java.lang.Integer" == val || "java.lang.Double" == val) {
					var __max;
					if("java.lang.Integer" == val) {
						if(val == __fieldType)
							__max = __fieldLength;
						else
							__max = 10;
						__validate.push("max(10)");
					} else {
						if(val == __fieldType)
							__max = __fieldLength;
						else
							__max = 12;
						__validate.push("max(12)");
					}
					fieldLength.val(__max);
				}
				fieldLength.attr("validate", __validate.join(" "));
				
				updateLength(__fieldLength);
			}
			
			function formatText(str){
				if (str==null || str.length == 0)
					return "";
				return str.replace(/&/g, "&amp;")
					.replace(/</g, "&lt;")
					.replace(/>/g, "&gt;")
					.replace(/ /g, "&nbsp;")
					.replace(/\'/g,"&#39;")
					.replace(/\"/g, "&quot;")
					.replace(/\n/g, "<br>")
					.replace(/¹/g, "&sup1;")
					.replace(/²/g, "&sup2;")
					.replace(/³/g, "&sup3;");
			}
			
			function displayTypeChange(val) {
				$("#fieldEnums_tr").hide();
				// 是否需要显示枚举
				if(val == "radio" || val == "checkbox"  || val == "select" ) {
					$("#fieldEnums_tr").show();
					DocListFunc_Init();
					// 检查是否有枚举选项，如果没有。则自动增加一行
					if($("#fieldEnums tr[class!=tr_normal_title]").length < 1) {
						$("#__DocList_AddRow").click();
					}
				}
				if(val.indexOf("ORG_TYPE_PERSON") > -1 || val.indexOf("ORG_TYPE_ORGORDEPT") > -1 || val.indexOf("ORG_TYPE_POST") > -1){
					$("#displayType_preview_td").html($("div[name=displayType_preview_sys_org]").html());
					showOrg();
					//默认值
					$("#displayType_default_td").html($("div[name=displayType_default_sys_org]").html());
					showDefaultOrg();
				}else{
					// 预览
					$("#displayType_preview_td").html($("div[name=displayType_preview_" + val + "]").html());
					//默认值
					$("#displayType_default_td").html($("div[name=displayType_default_" + val + "]").html());
				}
				showPreview();
			}
			
			function showOrg(){
				var val = $("input[name='displayType']").val();
				var orgType = getOrgType(val,"|");
				if(orgType!=""){
					var multi = $("input[name='isMulti']:checked").val();
					$("#address_preview").html('<input name="param(__test_preview__id)" type="hidden" /><input class="inputsgl" name="param(__test_preview__name)" type="text" readOnly style="width:85%" />'
							+ '<a href="#" onclick="Dialog_Address('+multi+',\'param(__test_preview__id)\',\'param(__test_preview__name)\',\';\','+orgType+');return false;"><%=ResourceUtil.getString("button.select")%></a>');
				}else{
					$("#address_preview").html('<input name="__test_preview__" class="inputsgl" type="text" style="width:80%">');
				}
			}
			
			function showDefaultOrg(){
				var val = $("input[name='displayType']").val();
				var orgType = getOrgType(val,"|");
				if(orgType!=""){
					var multi = $("input[name='isMulti']:checked").val();
					$("#address_default").html('<input name="defaultIds" type="hidden" value="${dynamicAttributeField.defaultIds}" /><input class="inputsgl" name="defaultNames" value="${dynamicAttributeField.defaultNames}" type="text" readOnly style="width:85%" />'
							+ '<a href="#" onclick="Dialog_Address('+multi+',\'defaultIds\',\'defaultNames\',\';\','+orgType+');return false;"><%=ResourceUtil.getString("button.select")%></a>');
				}else{
					$("#address_default").html('<input name="defaultValues" class="inputsgl" type="text" style="width:80%">');
				}
			}
			
			function multiChange(){
				var multi = $("input[name='isMulti']:checked").val();
				if("true" == multi){
					$("td [name=tableNameTr]").show();
					$("td [name=tableName]").removeAttr("disabled");
					$("td [name=columnNameTr]").hide();
					$("input[name=columnName]").attr("disabled", "disabled");
				}else{
					$("td [name=columnNameTr]").show();
					$("td [name=columnName]").removeAttr("disabled");
					$("td [name=tableNameTr]").hide();
					$("input[name=tableName]").attr("disabled", "disabled");
				}
				showOrg();
				showDefaultOrg();
			}
			
			function getOrgType(val,split){
				var vals = val.split(";");
				var tmp = "";
				for(var i = 0; i < vals.length;i++){
					tmp += split+vals[i];
				}
				if(tmp!=""){
					tmp = tmp.substring(split.length);
				}
				return tmp;
			};
			
			// 预览枚举类型
			function showPreview() {
				var displayType = $("input[name=displayType]:checked").val();
				if(displayType == "radio" || displayType == "checkbox"  || displayType == "select" ) {
					var datas = [];
					$.each($("#fieldEnums tr"), function(i, n) {
						if(i > 0) {
							var texts = $(n).find("input[name^=fieldEnums][name*=texts]");
							var val =  $(n).find("input[name^=fieldEnums][name$=value]").val();
							var text = $(texts[0]).val();
							if(val && val.length > 0 && text && text.length > 0)
								datas.push({val:val, text:text});
						}
					});
					if(datas.length > 0)
						$("#displayType_preview_td").html(buildEnumHtml(datas, displayType));
				}
				
				showDefault();
			}
			
			function showDefault() {
				var displayType = $("input[name=displayType]:checked").val();
				if(displayType == "radio" || displayType == "checkbox"  || displayType == "select" ) {
					var datas = [];
					$.each($("#fieldEnums tr"), function(i, n) {
						if(i > 0) {
							var texts = $(n).find("input[name^=fieldEnums][name*=texts]");
							var val =  $(n).find("input[name^=fieldEnums][name$=value]").val();
							var text = $(texts[0]).val();
							if(val && val.length > 0 && text && text.length > 0)
								datas.push({val:val, text:text});
						}
					});
					if(datas.length > 0)
						$("#displayType_default_td").html(buildDefaultEnumHtml(datas, displayType));
				}
			}
			
			function buildEnumHtml(datas, displayType) {
				var html = [];
				if(displayType == "select") {
					html.push('<select name="__test_preview__" class="inputsgl">');
					html.push('<option value="">==请选择==</option>');
					$.each(datas, function(i, n) {
						html.push('<option value="'+formatText(n.val)+'">'+formatText(n.text)+'</option>');
					});
					html.push('</select>');
				} else {
					$.each(datas, function(i, n) {
						html.push('<label><input type="'+displayType+'" name="__test_preview__" value="'+formatText(n.val)+'">'+formatText(n.text)+'</label>&nbsp;&nbsp;&nbsp;');
					});
				}
				return html.join("");
			}
			
			function buildDefaultEnumHtml(datas, displayType) {
				var defValue="${dynamicAttributeField.defaultValues}";
				var html = [];
				if(displayType == "select") {
					html.push('<select name="defaultValues" class="inputsgl">');
					html.push('<option value="">==请选择==</option>');
					$.each(datas, function(i, n) {
						if(defValue == n.val){
							html.push('<option selected value="'+formatText(n.val)+'">'+formatText(n.text)+'</option>');
						}else{
							html.push('<option value="'+formatText(n.val)+'">'+formatText(n.text)+'</option>');
						}
					});
					html.push('</select>');
				}else if(displayType == "checkbox"){
					var defValues = defValue.split(";");
					html.push('<input type="hidden" name="defaultValues">');
					$.each(datas, function(i, n) {
						if(defValues.indexOf(n.val) > -1){
							html.push('<label><input type="'+displayType+'" name="defValues" checked value="'+formatText(n.val)+'">'+formatText(n.text)+'</label>&nbsp;&nbsp;&nbsp;');
						}else{
							html.push('<label><input type="'+displayType+'" name="defValues" value="'+formatText(n.val)+'">'+formatText(n.text)+'</label>&nbsp;&nbsp;&nbsp;');
						}
						
					});
				} else {
					var defValues = defValue.split(";");
					$.each(datas, function(i, n) {
						if(defValues.indexOf(n.val) > -1){
							html.push('<label><input type="'+displayType+'" name="defaultValues" checked value="'+formatText(n.val)+'">'+formatText(n.text)+'</label>&nbsp;&nbsp;&nbsp;');
						}else{
							html.push('<label><input type="'+displayType+'" name="defaultValues" value="'+formatText(n.val)+'">'+formatText(n.text)+'</label>&nbsp;&nbsp;&nbsp;');
						}
					});
				}
				return html.join("");
			}
			
			function saveForm(param) {
				// 如果不是枚举类型，需要删除枚举值
				var displayType = $("input[name=displayType]:checked").val();
				if(displayType != "radio" && displayType != "checkbox"  && displayType != "select" ) {
					$.each($("#fieldEnums tr"), function(i, n) {
						if(i > 0){
							$(n).remove();
						}
					});
				}
				
				if(displayType == "checkbox"){
					var values = [];
					var defValues = document.getElementsByName("defValues");
					for (var i = 0; i < defValues.length; i++) {
						if(defValues[i].checked)
							values.push(defValues[i].value);
					}
					document.getElementsByName('defaultValues')[0].value = values.join(';');
				}
				Com_Submit(document.dynamicAttributeFieldForm, param);
			}
			
			// 长度字段修改后，需要修改枚举字段的长度校验
			function updateLength(val) {
				if(!val) {
					val = $("input[name=fieldLength]").val();
				}
				var fieldType = $("[name=fieldType]").val();
				$.each($("input[name^=fieldEnums][name$=value]"), function(i, n) {
					var fieldEnums = $(n);
					var __validate = fieldEnums.attr("validate").split(" ");
					for(var i=0; i<__validate.length; i++) {
						if(__validate[i].indexOf("maxLength") != -1
								|| __validate[i].indexOf("digits") != -1
								|| __validate[i].indexOf("number") != -1) {
							__validate.splice(i, 1);
						}
					}
					if("java.lang.Integer" == fieldType) {
						__validate.push("digits");
					} else if("java.lang.Double" == fieldType) {
						__validate.push("number");
					}
					__validate.push("maxLength(" + val + ")");
					fieldEnums.attr("validate", __validate.join(" "));
				});
				
			}
			
			LUI.ready(function() {
				DocList_Info.push('fieldEnums');
				fieldTypeChange();
				showPreview();
				multiChange();
				if("${dynamicAttributeField.fieldName}" != "") {
					// 下面的属性不能编辑
					var disableds = ["fieldName", "columnName", "fieldType","tableName"];
					$.each(disableds, function(i, n) {
						$("form [name=" + n + "]").attr("readonly","readonly");
						if("fieldType"==n)
							$("form [name=" + n + "]").attr("disabled","disabled");
					});
				}
			});
		</script>
		
		<form name="dynamicAttributeFieldForm" action="${LUI_ContextPath}/sys/property/custom_field/customField.do" method="post" style="padding: 20px 0;">
			<input type="hidden" name="modelName" value="${HtmlParam.modelName}">
			<input type="hidden" name="method_GET" value="${method_GET}">
			<p class="txttitle">${ lfn:message('sys-property:custom.field.settings.edit') }</p>
			<table class="tb_normal" width=95%>
				<tr>
					<td class="td_normal_title" width=15%>
						${ lfn:message('sys-property:custom.field.fieldTexts') }(${ lfn:message('sys-property:custom.field.fieldTexts.info') })
					</td>
					<td width=35%>
						<%
							// 所有支持的语言
							boolean isLangEnabled = SysLangUtil.isLangEnabled();
							Map<String, String> supportedLangs = SysLangUtil.getSupportedLangs();
							// 官方语言
							String official = SysLangUtil.getOfficialLang();
							// 先显示官方语言，并且是必填
							DynamicAttributeField field = (DynamicAttributeField)request.getAttribute("dynamicAttributeField");
							String value = "";
							
							if(isLangEnabled) { // 开启多语言
								value = field.getFieldText(official);
							%>
								<input name="fieldTexts(<%=official%>)" subject="${ lfn:message('sys-property:custom.field.fieldTexts') }" class="inputsgl" validate="required" value="<%=StringEscapeUtils.escapeHtml(value)%>" onblur="genFieldName(this.value)" style="width:80%">
								(<%=supportedLangs.get(official)%>)
								<span class="txtstrong">*</span>
							<%
								// 显示其它语言，可选
								for(String key : supportedLangs.keySet()) {
									if(key.equals(official)) continue; // 跳过官方语言，因为上面已经显示了
									value = field.getFieldText(key);
								%>
									<input name="fieldTexts(<%=key%>)" subject="${ lfn:message('sys-property:custom.field.fieldTexts') }" class="inputsgl" value="<%=StringEscapeUtils.escapeHtml(value)%>" style="width:80%">
									(<%=supportedLangs.get(key)%>)
								<%
								}
							} else { // 无多语言
								// 在model对象中，fieldTexts是一个MAP类型，所以当没有开启多语言里，这里还是要指定一个key
								value = field.getFieldText("def");
							%>
								<input name="fieldTexts(def)" class="inputsgl" subject="${ lfn:message('sys-property:custom.field.fieldTexts') }" validate="required" value="<%=StringEscapeUtils.escapeHtml(value)%>" onblur="genFieldName(this.value)" style="width:80%">
								<span class="txtstrong">*</span>
							<%
							}
						%>
					</td>
					<td name="columnNameTr" class="td_normal_title" width=15%>
						${ lfn:message('sys-property:custom.field.columnName') }
					</td>
					<td name="columnNameTr" width=35%>
						<input name="columnName" class="inputsgl" subject="${ lfn:message('sys-property:custom.field.columnName') }" validate="required maxLength(30) normalName uniqueColumnName" value="${dynamicAttributeField.columnName}" style="width:90%">
						<span class="txtstrong">*</span>
					</td>
					
					<td name="tableNameTr" class="td_normal_title" width=15% style="display:none">
						${ lfn:message('sys-property:custom.field.tableName') }
					</td>
					<td name="tableNameTr" width=35% style="display:none">
						<input name="tableName" class="inputsgl" validate="required maxLength(30) normalName uniqueTableName" value="${dynamicAttributeField.tableName}" style="width:90%">
						<span class="txtstrong">*</span>
						<br>
						<span class="com_help">${ lfn:message('sys-property:custom.field.tableName.desc') }</span>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						${ lfn:message('sys-property:custom.field.fieldName') }(${ lfn:message('sys-property:custom.field.fieldName.info') })
					</td>
					<td width=35%>
						<input name="fieldName" class="inputsgl" subject="${ lfn:message('sys-property:custom.field.fieldName') }" validate="required normalName uniqueFieldName" value="${dynamicAttributeField.fieldName}" style="width:50%">
						<span class="txtstrong">*</span>
						<br>
						<span class="com_help">${ lfn:message('sys-property:custom.field.fieldName.desc') }</span>
					</td>
					<td class="td_normal_title" width=15%>
						${ lfn:message('sys-property:custom.field.required') }
					</td>
					<td width=35%>
						<xform:radio property="required" value="${dynamicAttributeField.required}" showStatus="edit">
						 	<xform:simpleDataSource value="true">${ lfn:message('sys-property:sysPropertyDefine.fdStatus.true') }</xform:simpleDataSource>
						 	<xform:simpleDataSource value="false">${ lfn:message('sys-property:sysPropertyDefine.fdStatus.false') }</xform:simpleDataSource>
						 </xform:radio>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						${ lfn:message('sys-property:custom.field.fieldType') }
					</td>
					<td>
						<xform:select property="fieldType" value="${dynamicAttributeField.fieldType}" validators="required" showStatus="edit" onValueChange="fieldTypeChange">
						 	<xform:simpleDataSource value="java.lang.String">${ lfn:message('sys-property:custom.field.fieldType.string') }</xform:simpleDataSource>
						 	<xform:simpleDataSource value="java.lang.Integer">${ lfn:message('sys-property:custom.field.fieldType.integer') }</xform:simpleDataSource>
						 	<xform:simpleDataSource value="java.lang.Double">${ lfn:message('sys-property:custom.field.fieldType.double') }</xform:simpleDataSource>
						 	<xform:simpleDataSource value="java.util.Date">${ lfn:message('sys-property:custom.field.fieldType.date') }</xform:simpleDataSource>
						 	<c:if test="${!fn:contains(JsParam.modelName, 'com.landray.kmss.sys.organization.model.SysOrg')}">
						 	<xform:simpleDataSource value="com.landray.kmss.sys.organization.model.SysOrgElement">${ lfn:message('sys-property:custom.field.fieldType.sys.org') }</xform:simpleDataSource>
							</c:if>
						 </xform:select>
						 <span class="txtstrong">*</span>
					</td>
					<td class="td_normal_title" width=15%>
						${ lfn:message('sys-property:custom.field.status') }
					</td>
					<td>
						<xform:radio property="status" value="${dynamicAttributeField.status}" showStatus="edit">
						 	<xform:simpleDataSource value="true">${ lfn:message('sys-property:custom.field.status.true') }</xform:simpleDataSource>
						 	<xform:simpleDataSource value="false">${ lfn:message('sys-property:custom.field.status.false') }</xform:simpleDataSource>
						 </xform:radio>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						${ lfn:message('sys-property:custom.field.displayType') }
					</td>
					<td colspan="3">
						<div id="displayMode" style="vertical-align: top;">
						</div>
						<hr>
						${ lfn:message('sys-property:custom.field.preview') }
						<br>
						<div id="displayType_preview_td" style="margin-top: 5px;"></div>
						
						<%-- ${ lfn:message('sys-property:custom.field.default') } --%>
						默认值:
						<br>
						<div id="displayType_default_td" style="margin-top: 5px;"></div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						${ lfn:message('sys-property:custom.field.order') }
					</td>
					<td colspan="3">
						<input name="order" class="inputsgl" validate="digits min(0)" value="${dynamicAttributeField.order}" style="width:90%">
					</td>
					<td name="fieldLength_tr" class="td_normal_title" width=15% style="display: none;">
						${ lfn:message('sys-property:custom.field.fieldLength') }
					</td>
					<td name="fieldLength_tr" width=15% style="display: none;">
						<input name="fieldLength" class="inputsgl" validate="required digits min(1) changeLength" value="${dynamicAttributeField.fieldLength}" style="width:45%" onchange="updateLength(this.value);">
						<span class="txtstrong">*</span>
						<span id="scale_span" style="display: none;">
							(${ lfn:message('sys-property:custom.field.scale') }
							<input name="scale" class="inputsgl" validate="required digits min(0) max(8)" value="${dynamicAttributeField.scale}" style="width:30%">
							<span class="txtstrong">*</span>
							)
						</span>
					</td>
				</tr>
				<tr id="fieldEnums_tr">
					<td class="td_normal_title" width=15%>
						${ lfn:message('sys-property:custom.field.fieldEnums') }
					</td>
					<td colspan="3">
						<table id="fieldEnums" class="tb_normal" width="100%">
							<tr class="tr_normal_title">
								<td width="20px;">
									<span style="white-space:nowrap;"><bean:message key="page.serial"/></span>
								</td>
								<td width="50%">${ lfn:message('sys-property:custom.field.fieldEnums.texts') }</td>
								<td width="30%">${ lfn:message('sys-property:custom.field.fieldEnums.value') }</td>
								<td>
									<a id="__DocList_AddRow" href="javascript:;" class="com_btn_link" onclick="DocList_AddRow('fieldEnums');updateLength();">${ lfn:message('button.insert') }</a>
								</td>
							</tr>
							<%-- 模版行 --%>
							<tr style="display:none;" KMSS_IsReferRow="1">
								<td KMSS_IsRowIndex="1">
									!{index}
								</td>
								<td>
								<%
									if(isLangEnabled) { // 开启多语言
								%>
										<input name="fieldEnums[!{index}].texts(<%=official%>)" class="inputsgl" validate="required" value="" style="width:80%" onblur="showPreview();">
										(<%=supportedLangs.get(official)%>)
										<span class="txtstrong">*</span>
										<%
										// 显示其它语言，可选
										for(String key : supportedLangs.keySet()) {
											if(key.equals(official)) continue; // 跳过官方语言，因为上面已经显示了
											value = field.getFieldText(key);
										%>
											<input name="fieldEnums[!{index}].texts(<%=key%>)" class="inputsgl" value="" style="width:80%">
											(<%=supportedLangs.get(key)%>)
								<%
										}
									} else {
								%>
										<input name="fieldEnums[!{index}].texts(def)" class="inputsgl" validate="required" value="" style="width:80%" onblur="showPreview();">
										<span class="txtstrong">*</span>
								<%
									}
								%>
								</td>
								<td>
									<input name="fieldEnums[!{index}].value" class="inputsgl" validate="required normalNameEnum maxLength(${dynamicAttributeField.fieldLength})" value="" style="width:90%" onblur="showPreview();">
									<span class="txtstrong">*</span>
								</td>
								<td>
									<div style="text-align:center">
									<img src="${KMSS_Parameter_StylePath}icons/delete.gif" alt="del" onclick="DocList_DeleteRow(this.parentNode.parentNode.parentNode);" style="cursor:pointer">&nbsp;&nbsp;
									<img src="${KMSS_Parameter_StylePath}icons/up.gif" alt="up" onclick="DocList_MoveRow(-1, this.parentNode.parentNode.parentNode);" style="cursor:pointer">&nbsp;&nbsp;
									<img src="${KMSS_Parameter_StylePath}icons/down.gif" alt="down" onclick="DocList_MoveRow(1, this.parentNode.parentNode.parentNode);" style="cursor:pointer">
									</div>
								</td>
							</tr>
							<%-- 内容行 --%>
							<c:forEach items="${dynamicAttributeField.fieldEnums}" var="enums" varStatus="vstatus">
							<tr KMSS_IsContentRow="1">
								<td>
									${vstatus.index + 1}
								</td>
								<td>
								<%
									DynamicAttributeFieldEnum _enum = (DynamicAttributeFieldEnum)pageContext.getAttribute("enums");
									String __value = "";
									if(isLangEnabled) { // 开启多语言
										// 先显示官方语言，并且是必填
										__value = _enum.getText(official);
										%>
										<input name="fieldEnums[${vstatus.index}].texts(<%=official%>)" class="inputsgl" validate="required maxLength(200)" value="<%=__value%>" style="width:80%" onblur="showPreview();">
										(<%=supportedLangs.get(official)%>)
										<span class="txtstrong">*</span>
										<%
										// 显示其它语言，可选
										for(String key : supportedLangs.keySet()) {
											if(key.equals(official)) continue; // 跳过官方语言，因为上面已经显示了
											__value = _enum.getText(key);
										%>
											<input name="fieldEnums[${vstatus.index}].texts(<%=key%>)" class="inputsgl" value="<%=__value%>" style="width:80%">
											(<%=supportedLangs.get(key)%>)
										<%
										}
									} else {
										__value = _enum.getText("def");
								%>
										<input name="fieldEnums[${vstatus.index}].texts(def)" class="inputsgl" validate="required maxLength(200)" value="<%=__value%>" style="width:80%" onblur="showPreview();">
										<span class="txtstrong">*</span>
								<%
									}
								%>
								</td>
								<td>
									<input name="fieldEnums[${vstatus.index}].value" class="inputsgl" validate="required normalNameEnum maxLength(${dynamicAttributeField.fieldLength})" value="${enums.value}" style="width:90%" onblur="showPreview();">
									<span class="txtstrong">*</span>
								</td>
								<td>
									<div style="text-align:center">
									<img src="../../../resource/style/default/icons/delete.gif" alt="del" onclick="DocList_DeleteRow(this.parentNode.parentNode.parentNode);showPreview();" style="cursor:pointer">&nbsp;&nbsp;
									<img src="../../../resource/style/default/icons/up.gif" alt="up" onclick="DocList_MoveRow(-1, this.parentNode.parentNode.parentNode);" style="cursor:pointer">&nbsp;&nbsp;
									<img src="../../../resource/style/default/icons/down.gif" alt="down" onclick="DocList_MoveRow(1, this.parentNode.parentNode.parentNode);" style="cursor:pointer">
									</div>
								</td>
							</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			</table>
			<p style="margin-top: 10px;margin-left: 30px;"><font color="red">${ lfn:message('sys-property:custom.field.updateMapping.tips') }</font></p>
		</form>
		
		<!-- 以下为模板代码 -->
		<div style="display: none;">
			<div id="displayMode_String">
				<xform:radio property="displayType" showStatus="edit" onValueChange="displayTypeChange">
					<xform:simpleDataSource value="text">${ lfn:message('sys-property:custom.field.displayType.text') }</xform:simpleDataSource>
					<xform:simpleDataSource value="textarea">${ lfn:message('sys-property:custom.field.displayType.textarea') }</xform:simpleDataSource>
					<xform:simpleDataSource value="radio">${ lfn:message('sys-property:custom.field.displayType.radio') }</xform:simpleDataSource>
					<xform:simpleDataSource value="checkbox">${ lfn:message('sys-property:custom.field.displayType.checkbox') }</xform:simpleDataSource>
					<xform:simpleDataSource value="select">${ lfn:message('sys-property:custom.field.displayType.select') }</xform:simpleDataSource>
				</xform:radio>
			</div>
			<div id="displayMode_Integer">
				<xform:radio property="displayType" showStatus="edit" onValueChange="displayTypeChange">
					<xform:simpleDataSource value="text">${ lfn:message('sys-property:custom.field.displayType.text') }</xform:simpleDataSource>
					<xform:simpleDataSource value="radio">${ lfn:message('sys-property:custom.field.displayType.radio') }</xform:simpleDataSource>
					<xform:simpleDataSource value="select">${ lfn:message('sys-property:custom.field.displayType.select') }</xform:simpleDataSource>
				</xform:radio>
			</div>
			<div id="displayMode_Double">
				<xform:radio property="displayType" showStatus="edit" onValueChange="displayTypeChange">
					<xform:simpleDataSource value="text">${ lfn:message('sys-property:custom.field.displayType.text') }</xform:simpleDataSource>
				</xform:radio>
			</div>
			<div id="displayMode_Date">
				<xform:radio property="displayType" showStatus="edit" onValueChange="displayTypeChange">
					<xform:simpleDataSource value="datetime">${ lfn:message('sys-property:custom.field.displayType.datetime') }</xform:simpleDataSource>
					<xform:simpleDataSource value="date">${ lfn:message('sys-property:custom.field.displayType.date') }</xform:simpleDataSource>
					<xform:simpleDataSource value="time">${ lfn:message('sys-property:custom.field.displayType.time') }</xform:simpleDataSource>
				</xform:radio>
			</div>
			<div id="displayMode_SysOrg">
				<xform:checkbox property="displayType" showStatus="edit" required="true" onValueChange="displayTypeChange">
					<xform:simpleDataSource value="ORG_TYPE_PERSON">${ lfn:message('sys-property:custom.field.displayType.person') }</xform:simpleDataSource>
					<xform:simpleDataSource value="ORG_TYPE_ORGORDEPT">${ lfn:message('sys-property:custom.field.displayType.orgOrDept') }</xform:simpleDataSource>
					<xform:simpleDataSource value="ORG_TYPE_POST">${ lfn:message('sys-property:custom.field.displayType.post') }</xform:simpleDataSource>
				</xform:checkbox>
				<br/>
				<xform:radio property="isMulti" showStatus="edit" required="true" onValueChange="multiChange">
					<xform:simpleDataSource value="false">${ lfn:message('sys-property:custom.field.multi.no') }</xform:simpleDataSource>
					<xform:simpleDataSource value="true">${ lfn:message('sys-property:custom.field.multi.yes') }</xform:simpleDataSource>
				</xform:radio>
			</div>
			
			<!-- 以下为即时预览效果 -->
			<div name="displayType_preview_text">
				<input name="__test_preview__" class="inputsgl" type="text" style="width:80%">
			</div>
			<div name="displayType_preview_textarea">
				<textarea name="__test_preview__" style="width:95%"></textarea>
			</div>
			<div name="displayType_preview_radio">
				<label><input type="radio" name="___test_preview__" value="1">${ lfn:message('sys-property:custom.field.select.a') }</label>&nbsp;&nbsp;&nbsp;
				<label><input type="radio" name="___test_preview__" value="2">${ lfn:message('sys-property:custom.field.select.b') }</label>&nbsp;&nbsp;&nbsp;
			</div>
			<div name="displayType_preview_checkbox">
				<label><input type="checkbox" name="___test_preview__" value="1">${ lfn:message('sys-property:custom.field.select.a') }</label>&nbsp;&nbsp;&nbsp;
				<label><input type="checkbox" name="___test_preview__" value="2">${ lfn:message('sys-property:custom.field.select.b') }</label>&nbsp;&nbsp;&nbsp;
			</div>
			<div name="displayType_preview_select">
				<select name="__test_preview__" class="inputsgl">
					<option value="">${ lfn:message('sys-property:custom.field.select.please') }</option>
					<option value="1">${ lfn:message('sys-property:custom.field.select.a') }</option>
					<option value="2">${ lfn:message('sys-property:custom.field.select.b') }</option>
				</select>
			</div>
			<div name="displayType_preview_datetime">
				<div class='inputselectsgl' onclick="selectDateTime('__test_preview__')"  style="width:150px;"><div class="input"><input type="text" name="__test_preview__" value="" validate="__datetime" /></div><div  class="inputdatetime" ></div></div>
			</div>
			<div name="displayType_preview_date">
				<div class='inputselectsgl' onclick="selectDate('__test_preview__')"  style="width:150px;"><div class="input"><input type="text" name="__test_preview__" value="" validate="__date" /></div><div  class="inputdatetime" ></div></div>
			</div>
			<div name="displayType_preview_time">
				<div class='inputselectsgl' onclick="selectTime('__test_preview__')"  style="width:150px;"><div class="input"><input type="text" name="__test_preview__" value="" validate="__time" /></div><div  class="inputtime" ></div></div>
			</div>
			
			<div name="displayType_preview_sys_org">
				<span id="address_preview">
				</span>
			</div>
			
			<!-- 默认值设置 -->
			<div name="displayType_default_text">
				<input name="defaultValues" class="inputsgl" type="text" style="width:80%" value="${dynamicAttributeField.defaultValues}">
			</div>
			<div name="displayType_default_textarea">
				<textarea name="defaultValues" style="width:95%" value="${dynamicAttributeField.defaultValues}"><c:out value="${dynamicAttributeField.defaultValues}"/></textarea>
			</div>
			<div name="displayType_default_radio">
				<label><input type="radio" name="defaultValues" value="1">${ lfn:message('sys-property:custom.field.select.a') }</label>&nbsp;&nbsp;&nbsp;
				<label><input type="radio" name="defaultValues" value="2">${ lfn:message('sys-property:custom.field.select.b') }</label>&nbsp;&nbsp;&nbsp;
			</div>
			<div name="displayType_default_checkbox">
				<label><input type="checkbox" name="defaultValues" value="1">${ lfn:message('sys-property:custom.field.select.a') }</label>&nbsp;&nbsp;&nbsp;
				<label><input type="checkbox" name="defaultValues" value="2">${ lfn:message('sys-property:custom.field.select.b') }</label>&nbsp;&nbsp;&nbsp;
			</div>
			<div name="displayType_default_select">
				<select name="defaultValues" class="inputsgl">
					<option value="">${ lfn:message('sys-property:custom.field.select.please') }</option>
					<option value="1">${ lfn:message('sys-property:custom.field.select.a') }</option>
					<option value="2">${ lfn:message('sys-property:custom.field.select.b') }</option>
				</select>
			</div>
			<div name="displayType_default_datetime">
				<div class='inputselectsgl' onclick="selectDateTime('defaultValues')"  style="width:150px;"><div class="input"><input type="text" name="defaultValues" value="${dynamicAttributeField.defaultValues}" validate="__datetime"  /></div><div  class="inputdatetime" ></div></div>
			</div>
			<div name="displayType_default_date">
				<div class='inputselectsgl' onclick="selectDate('defaultValues')"  style="width:150px;"><div class="input"><input type="text" name="defaultValues" value="${dynamicAttributeField.defaultValues}" validate="__date" /></div><div  class="inputdatetime" ></div></div>
			</div>
			<div name="displayType_default_time">
				<div class='inputselectsgl' onclick="selectTime('defaultValues')"  style="width:150px;"><div class="input"><input type="text" name="defaultValues" value="${dynamicAttributeField.defaultValues}" validate="__time" /></div><div  class="inputtime" ></div></div>
			</div>
			
			<div name="displayType_default_sys_org">
				<span id="address_default">
				</span>
			</div>
		</div>
		
		<script type="text/javascript">
			var _validator = $KMSSValidation();
			var CustomValidators = {
					'normalName' : {
						error : "${ lfn:message('sys-property:custom.field.normalName') }",
						test : function(value) {
							var pattern = new RegExp("^[a-zA-Z_][a-zA-Z0-9_]{1,30}$");
							if (pattern.test(value)) {
								return true;
							} else {
								return false;
							}
						}
					},
					'normalNameEnum' : {
						error : "${ lfn:message('sys-property:custom.field.normalName.enum') }",
						test : function(value) {
							var pattern = new RegExp("^[a-zA-Z0-9_]*$");
							if (pattern.test(value)) {
								return true;
							} else {
								return false;
							}
						}
					},						
					'uniqueColumnName' : {
						error : "${ lfn:message('sys-property:custom.field.uniqueName') }",
						test : function(value) {
							if($("input[name=method_GET]").val() == "edit")
								return true;
							var modelName = document.getElementsByName("modelName")[0].value;
							var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=customFieldService&modelName="
									+ modelName + "&columnName=" + value + "&type=3&_=" + new Date().getTime());
							return checkUrl(url);
					    }
					},
					'changeLength' : {
						error : "${ lfn:message('sys-property:custom.field.fieldLength.err') }",
						test : function(value) {
							var modelName = document.getElementsByName("modelName")[0].value;
							var fieldName = document.getElementsByName("fieldName")[0].value;
							var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=customFieldService&modelName="
									+ modelName + "&fieldName=" + fieldName + "&fieldLength=" + value + "&type=2&_=" + new Date().getTime());
							return checkUrl(url);
					    }
					},
					'uniqueFieldName' : {
						error : "${ lfn:message('sys-property:custom.field.uniqueName') }",
						test : function(value) {
							if($("input[name=method_GET]").val() == "edit")
								return true;
							var modelName = document.getElementsByName("modelName")[0].value;
							var columnName = document.getElementsByName("columnName")[0].value;
							var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=customFieldService&modelName="
									+ modelName + "&fieldName=" + value + "&columnName=" + columnName + "&type=1&_=" + new Date().getTime());
							return checkUrl(url);
					    }
					},
					'uniqueTableName' : {
						error : "${ lfn:message('sys-property:custom.field.uniqueTableName') }",
						test : function(value) {
							if($("input[name=method_GET]").val() == "edit")
								return true;
							var modelName = document.getElementsByName("modelName")[0].value;
							var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=customFieldService&modelName="
									+ modelName + "&tableName=" + value + "&type=4&_=" + new Date().getTime());
							return checkUrl(url);
					    }
					}
					
				};
			_validator.addValidators(CustomValidators);
			
			//校验登录名是否与系统中失效的登录名一致
			function checkUrl(url) {
				var xmlHttpRequest;
				if (window.XMLHttpRequest) { // Non-IE browsers
					xmlHttpRequest = new XMLHttpRequest();
				} else if (window.ActiveXObject) { // IE
					try {
						xmlHttpRequest = new ActiveXObject("Msxml2.XMLHTTP");
					} catch (othermicrosoft) {
						try {
							xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
						} catch (failed) {
							xmlHttpRequest = false;
						}
					}
				}
				if (xmlHttpRequest) {
					xmlHttpRequest.open("GET", url, false);
					xmlHttpRequest.send();
					var result = xmlHttpRequest.responseText.replace(/\s/g, "").replace(/;/g, "\n");
					if (result != "") {
						return false;
					}
				}
				return true;
			}
			
			// 根据显示名称生成程序名称
			function genFieldName(value) {
				if($("input[name=method_GET]").val() == "add") {
					// 程序属性名：根据显示的字段名来转换
					if(value && value.length > 0) {
						var data = new KMSSData();
						value = encodeURIComponent(encodeURIComponent(value));
						data.SendToBean('customFieldService&type=1&fieldText='+value, function(rtnData) {
							var _data = rtnData.GetHashMapArray()[0];
							document.getElementsByName("fieldName")[0].value = _data.fieldName;
						});
					}
				}
			}
			
		</script>
	</template:replace>
</template:include>