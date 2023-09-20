<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit" width="95%" sidebar="no">
	<template:replace name="head">
		<link rel="stylesheet" type="text/css"
			href="${ LUI_ContextPath}/sys/zone/address/resource/css/zoneAddress_common.css?s_cache=${LUI_Cache }" />
		<script type="text/javascript">
			Com_IncludeFile("doclist.js");
			seajs.use([ 'lui/jquery' ], function($) {
				$(document).ready(function() {
					$('body').addClass('lui_zone_iframe_body').css("background","#fff");
					/* $("td a").click(function() {
						debugger;
						var thisDialog = window.$dialog;
						console.log(thisDialog);
						thisDialog.hide({
							p : "test"
						});
					}); */
				});
			});

			function saveZoneAddressCate() {
				Com_Submit(document.sysZoneAddressCateForm, 'save');
			}
		</script>
	</template:replace>
	<template:replace name="content">
		<html:form
			action="/sys/zone/sys_zone_address_cate/sysZoneAddressCate.do">
			<center>
				<table class="tb_normal" width=95%>
					<tr>
						<td class="td_normal_title" style="width: 15%"><bean:message bundle="sys-zone" key="zoneAddress.out.name" /></td>
						<td colspan="3"><xform:text showStatus="edit" required="true"
								property="fdName" style="width:85%" validators="required maxLength(200)" subject="伙伴名称" /></td>
					</tr>
					<c:import url="/sys/zone/address/resource/jsp/outerPersonAdd.jsp"
						charEncoding="UTF-8">
						<c:param name="formName" value="sysZoneAddressCateForm" />
						<c:param name="cateId" value="${sysZoneAddressCateForm.fdId }" />
					</c:import>
					<%-- 所属场所 --%>
					<c:import
						url="/sys/authorization/sys_auth_area/sysAuthArea_field.jsp"
						charEncoding="UTF-8">
						<c:param name="id" value="${sysZoneAddressCateForm.authAreaId}" />
					</c:import>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message
								key="model.docEditorName" /></td>
						<td colspan="3"><xform:address showStatus="edit"
								textarea="true" mulSelect="true" propertyId="authEditorIds"
								propertyName="authEditorNames" style="width:100%;height:90px;"></xform:address>
							<p class="com_help"><bean:message bundle="sys-zone" key="zoneAddress.editorTip" /></p></td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%"><bean:message
								key="model.docReaderName" /></td>
						<td colspan="3"><xform:address showStatus="edit"
								textarea="true" mulSelect="true" propertyId="authReaderIds"
								propertyName="authReaderNames" style="width:100%;height:90px;"></xform:address>
							<p class="com_help"><bean:message bundle="sys-zone" key="zoneAddress.userTip" /></p></td>
					</tr>
				</table>
				<ui:button style="margin-top:16px;"
					text="${lfn:message('button.save')}" height="35" width="120"
					onclick="saveZoneAddressCate();"></ui:button>
			</center>
			<html:hidden property="fdId" />
			<html:hidden property="fdModelName" />
			<html:hidden property="method_GET" />
			<script>
			var _validation=$KMSSValidation(document.forms['sysZoneAddressCateForm']);
				// 增加一个字符串的startsWith方法
				function startsWith(value, prefix) {
					return value.slice(0, prefix.length) === prefix;
				}

				// 校验手机号是否正确
				_validation.addValidator(
					'phone',
					"<bean:message key='sysZoneAddress.error.newMoblieNoError' bundle='sys-zone' />",
					function(v, e, o) {
						if (v == "") {
							return true;
						}
						// 国内手机号可以有+86，但是后面必须是11位数字
						// 国际手机号必须要以+区号开头，后面可以是6~11位数据
						if(startsWith(v, "+")) {
							if(startsWith(v, "+86")) {
								return /^(\+86)(\d{11})$/.test(v);
							} else {
								return /^(\+\d{1,5})(\d{6,11})$/.test(v);
							}
						} else {
							// 没有带+号开头，默认是国内手机号
							return /^(\d{11})$/.test(v);
						}
				});
			</script>
		</html:form>
	</template:replace>
</template:include>