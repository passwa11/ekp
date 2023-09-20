<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<template:include ref="default.simple">
	<template:replace name="head">
		<template:super/>
		<script language="JavaScript">
			Com_IncludeFile("dialog.js");
		</script>
		<link href="${LUI_ContextPath}/resource/style/default/doc/document.css" rel="stylesheet" type="text/css" />
		
		<script language="JavaScript">
			seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
				init = function() {
					var opeions = [];
					// 获取矩阵字段
					var data = new KMSSData();
					data.UseCache = false;
					data.AddBeanData("sysOrgMatrixService&rtnType=2&id=${JsParam.fdId}");
					var rtn = data.GetHashMapArray();
					if(rtn.length > 0) {
						for(var i=0; i<rtn.length; i++) {
							var d = rtn[i],
								obj = {
									value: d.value,
									fieldName: d.fieldName,
									text: d.text,
									type: d.type
								};
							opeions.push('<option value="' + d.fieldName + '" data-obj="' + encodeURIComponent(JSON.stringify(obj)) + '">' + d.text + '</option>');
						}
					} else {
						dialog.alert('<bean:message key="sysOrgMatrix.edit.batchReplace.error" bundle="sys-organization"/>');
						opeions.push('<option><bean:message key="sysOrgMatrix.edit.batchReplace.error" bundle="sys-organization"/></option>');
					}
					$("#sysOrgMatrix_field").html(opeions.join(""));
				}
				
				// 选择字段
				changeField = function() {
					var option = $('#sysOrgMatrix_field option:selected')
						val = option.val(),
						obj = JSON.parse(decodeURIComponent(option.data("obj")));
					$("#sysOrgMatrix_ori").html(getContent("ori", obj.type));
					$("#sysOrgMatrix_target").html(getContent("target", obj.type));
				}
				
				getContent = function(model, type) {
					var content = [],
						_id = model + "Id",
						_name = model + "Name";
					if(type == 'person_post') {
						content.push("	<div class=\"inputselectsgl\" style=\"border-bottom:none;width:45%;display:inline-table;\">");
						content.push("		<input type=\"hidden\" name=\"" + _id + "_person\">");
						content.push("		<input type=\"text\" name=\"" + _name + "_person\" style=\"width:90%;min-width:70px;\" readonly=\"true\" class=\"inputsgl\">");
						content.push("		<a onclick=\"Dialog_Address_Cust(false, '" + _id + "_person', '" + _name + "_person', null, ORG_TYPE_PERSON, true);\" class=\"orgelement\"/>");
						content.push("	</div>");
						content.push("	<div class=\"inputselectsgl\" style=\"border-bottom:none;width:45%;display:inline-table;margin-left:10px;\">");
						content.push("		<input type=\"hidden\" name=\"" + _id + "_post\">");
						content.push("		<input type=\"text\" name=\"" + _name + "_post\" style=\"width:90%;min-width:70px;\" readonly=\"true\" class=\"inputsgl\">");
						content.push("		<a onclick=\"Dialog_Address_Cust(false, '" + _id + "_post', '" + _name + "_post', null, ORG_TYPE_POST);\" class=\"orgelement\"/>");
						content.push("	</div>");
					} else {
						var orgType = type == 'post' ? 'ORG_TYPE_POST' : 'ORG_TYPE_PERSON';
						content.push("	<div class=\"inputselectsgl\" style=\"border-bottom:none; width: 100%;\">");
						content.push("		<input type=\"hidden\" name=\"" + _id + "\">");
						content.push("		<input type=\"text\" name=\"" + _name + "\" style=\"width:90%;min-width:70px;\" readonly=\"true\" class=\"inputsgl\">");
						content.push("		<a onclick=\"Dialog_Address_Cust(false, '" + _id + "', '" + _name + "', null, " + orgType + ");\" class=\"orgelement\"/>");
						content.push("	</div>");
					}
					return content.join("");
				}
				
				/* 地址本 */
				window.Dialog_Address_Cust = function(mulSelect, idField, nameField, splitStr, selectType, showPost) {
					Dialog_Address(mulSelect, idField, nameField, splitStr, selectType, function(rtnVal) {
						if(showPost) {
							var data = new KMSSData();
							data.UseCache = false;
							data.AddBeanData("sysOrgMatrixService&type=get_post&person=" + rtnVal.data[0].id);
							var rtn = data.GetHashMapArray();
							var __postId = $("input[name='" + idField.replace(/person/g, "post") + "']"),
								__postName = $("input[name='" + nameField.replace(/person/g, "post") + "']");
							if(rtn.length > 0) {
								__postId.val(rtn[0].postId);
								__postName.val(rtn[0].postName);
							} else {
								__postId.val("");
								__postName.val("");
							}
						}
					});
				}
				
				$(function() {
					init();
					changeField();
				});
			});
		</script>
	</template:replace>
	<template:replace name="body">
		<center>
			<div style="height: 50px;">
				<p style="text-align: center;font-size: 22;margin-top: 10px"><bean:message key="sysOrgMatrix.edit.batchReplace.note" bundle="sys-organization"/><span class="version">（${JsParam.curVersion}）</span></p>
			</div>
			<table class="tb_normal" width=90% style="top:50px">
				<tr>
					<td style="background: #f1f1f1"><bean:message key="sysOrgMatrix.edit.batchReplace.title" bundle="sys-organization"/></td>
					<td style="width: 75%">
						<select id="sysOrgMatrix_field" onchange="changeField()" style="width: 90%;">
							
						</select>
					</td>
				</tr>
				
				<tr>
					<td style="background: #ecf3ff"><bean:message key="sysOrgMatrix.edit.batchReplace.ori" bundle="sys-organization"/></td>
					<td style="width: 75%" id="sysOrgMatrix_ori">
						
					</td>
				</tr>
				
				<tr>
					<td style="background: #ecf3ff"><bean:message key="sysOrgMatrix.edit.batchReplace.target" bundle="sys-organization"/></td>
					<td style="width: 75%" id="sysOrgMatrix_target">
						
					</td>
				</tr>
			</table>
		</center>
	</template:replace>
</template:include>
