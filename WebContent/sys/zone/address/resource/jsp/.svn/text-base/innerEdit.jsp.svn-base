<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<%
	boolean isZoneAdmin = UserUtil.checkRole("ROLE_SYSZONE_ADMIN");
	request.setAttribute("isZoneAdmin", isZoneAdmin);
%>
<template:include ref="default.edit" width="95%" sidebar="no">
	<template:replace name="head">
		<link rel="stylesheet" type="text/css"
			href="${ LUI_ContextPath}/sys/zone/address/resource/css/zoneAddress_common.css?s_cache=${LUI_Cache }" />
			<link rel="stylesheet" type="text/css"
			href="${ LUI_ContextPath}/sys/zone/address/resource/css/innerEdit.css" />
		<script type="text/javascript" src="${ LUI_ContextPath}/sys/zone/address/resource/js/innerEdit.js"></script>
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
						<td class="td_normal_title" style="width: 15%"><bean:message bundle="sys-zone" key="zoneAddress.itemName" /></td>
						<td colspan="3"><xform:text showStatus="edit" required="true"
								property="fdName" style="width:85%" validators="required maxLength(200)" subject="${lfn:message('sys-zone:zoneAddress.itemName') }" /></td>
					</tr>
					
					<c:if test="${ sysZoneAddressCateForm.method_GET == 'add' }">
						<c:choose>
							<c:when test="${ isZoneAdmin eq 'true' }">
								<c:import url="/sys/zone/address/resource/jsp/innerItemTypeEdit.jsp"
								charEncoding="UTF-8"></c:import>
							</c:when>
							<c:otherwise>
								<c:import url="/sys/zone/address/resource/jsp/innerItemTypeReadOnly.jsp"
								charEncoding="UTF-8"></c:import>
							</c:otherwise>
						</c:choose>
					</c:if>
					
					<c:if test="${ sysZoneAddressCateForm.method_GET == 'edit' }">
						<c:import url="/sys/zone/address/resource/jsp/innerItemTypeReadOnly.jsp"
						charEncoding="UTF-8"></c:import>
					</c:if>
					<c:import url="/sys/zone/address/resource/jsp/innerPersonAdd.jsp"
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
					<tr id="authEditorTr" style="display:none">
						<td class="td_normal_title" width="15%"><bean:message
								key="model.docEditorName" /></td>
						<td colspan="3"><xform:address showStatus="edit"
								textarea="true" mulSelect="true" propertyId="authEditorIds" orgType="ORG_TYPE_ALL"
								propertyName="authEditorNames" style="width:100%;height:90px;"></xform:address>
							<p class="com_help"><bean:message bundle="sys-zone" key="zoneAddress.editorTip" /></p></td>
					</tr>
					<tr id="authReaderTr" style="display:none">
						<td class="td_normal_title" width="15%"><bean:message
								key="model.docReaderName" /></td>
						<td colspan="3"><xform:address showStatus="edit"
								textarea="true" mulSelect="true" propertyId="authReaderIds" orgType="ORG_TYPE_ALL"
								propertyName="authReaderNames" style="width:100%;height:90px;"></xform:address>
							<p class="com_help"><bean:message bundle="sys-zone" key="zoneAddress.userTip" /></p></td>
					</tr>
				</table>
				<ui:button style="margin-top:16px;"
					text="${lfn:message('button.save')}" height="35" width="120"
					onclick="saveZoneAddressCate();"></ui:button>
			</center>
	
	         <!-- 批量添加页面展示 -->
			   <table class="popBox" id="popBox" width=95%>
					<tr style=" border-bottom: 1px solid #d2d2d2">
						<td width="15%" colspan="4">
						 	<div class=tr_normal_title>
							      <bean:message bundle="sys-zone" key="zoneAddress.bulkAdd" />
							 </div>
						</td>
					</tr>
                    <tr><td width="15%" colspan="4" style="border-bottom-style:none"></td></tr>
					<tr>
					 <td width="1%"></td>
						<td class="td_normal_title" width="15%" style="border: 1px #d2d2d2 solid;">
						 	<div class=tr_normal_title style="text-align:center">
							      <bean:message bundle="sys-zone" key="zoneAddress.itemPeople" />
							 </div>
						</td>
						<td width="45%" style="border: 1px #d2d2d2 solid;">
							<xform:address showStatus="edit" textarea="true" mulSelect="true" orgType="8" propertyId="cateRelationsFdOrgIds"
							 propertyName="cateRelationsFdOrgName" style="width:100%;height:90px;">
							</xform:address>
	
						</td>
						<td width="1%" ></td>
					</tr>
					 <div id="popBoxTable">

						<ui:button style="margin-top:25px;"
										text="${lfn:message('button.ok')}" height="35" width="120"
										onclick="addMultiPerson();"></ui:button>
										<ui:button style="margin-top:16px;"
										text="${lfn:message('button.cancel')}" height="35" width="120"
										onclick="closeBox();"></ui:button>
                    </div> 
				</table>
				
			<html:hidden property="fdId" />
			<html:hidden property="fdModelName" />
			<html:hidden property="method_GET" />
			<script>
				$KMSSValidation(document.forms['sysZoneAddressCateForm']);
				window.onload = function(){
					var itemType = "${sysZoneAddressCateForm.fdItemType}";
					onItemTypeChange(itemType);
				}
				
				function onItemTypeChange(value){
					if(value == "private"){
						$("#authEditorTr").hide();
						$("#authReaderTr").hide();
					}else if(value == "public"){
						$("#authEditorTr").show();
						$("#authReaderTr").show();
						
					}
				}
			</script>
		</html:form>
	</template:replace>
</template:include>