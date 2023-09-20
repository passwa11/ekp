<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit" sidebar="no" showQrcode="false">
	<template:replace name="head">
		<style>
			@font-face {
				font-family: 'FontMui';
				src:url("<%=request.getContextPath()%>/sys/mobile/css/font/fontmui.woff");
			}
			.icon {
				background-color: #1d9d74;
				color:#fff;
				cursor: pointer;
				font-size: 36px;
			}
		</style>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/mobile/css/themes/default/font-mui.css"></link>
		<link rel="stylesheet" type="text/css"
			  href="<%=request.getContextPath()%>/sys/mportal/sys_mportal_menu/css/iconList.css"></link>
	</template:replace>
	<template:replace name="title">
		<c:choose>
			<c:when test="${ sysPersonMlinkCategoryForm.method_GET == 'add' }">
				新建 - 移动端个人设置
			</c:when>
			<c:otherwise>
				<c:out value="${sysPersonMlinkCategoryForm.fdName}"/> - 编辑
			</c:otherwise>
		</c:choose>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav">
			<ui:menu-item text="${lfn:message('home.home')}" href="/index.jsp" icon="lui_icon_s_home" />
			<ui:menu-item text="${lfn:message('sys-mportal:sysMportalCard.nav.path.item1') }" />
			<ui:menu-item text="${lfn:message('sys-mportal:sysMportalCard.nav.path.item2') }" />
			<ui:menu-item text="${lfn:message('sys-mportal:sysMportal.profile.maintain') }" />
			<ui:menu-item text="${lfn:message('sys-mportal:sysMportal.profile.category') }" />
		</ui:menu>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<c:choose>
				<c:when test="${ sysPersonMlinkCategoryForm.method_GET == 'edit' }">
					<ui:button text="${ lfn:message('button.update') }"
					 onclick="Com_Submit(document.sysPersonMlinkCategoryForm, 'update');">
					</ui:button>
				</c:when>
				<c:when test="${ sysPersonMlinkCategoryForm.method_GET == 'add' }">	
					<ui:button text="${ lfn:message('button.save') }" 
						onclick="Com_Submit(document.sysPersonMlinkCategoryForm, 'save');">
					</ui:button>
					<ui:button text="${ lfn:message('button.saveadd') }" 
						onclick="Com_Submit(document.sysPersonMlinkCategoryForm, 'saveadd');">
					</ui:button>
				</c:when>
			</c:choose>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
			</ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
	  <html:form action="/sys/person/sys_person_mlink_category/sysPersonMlinkCategory.do">
 		<div class="lui_content_form_frame" style="padding: 20px 0px">
		  	<table class="tb_normal"  width="95%">
				<tr>
					<td class="td_normal_title" width="15%">
						<bean:message bundle="sys-person" key="sysPersonMlinkCategory.fdName"/>
					</td>
					<td width="85%" colspan="3">
						<xform:text property="fdName" style="width:85%" required="true"/>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">
						<bean:message bundle="sys-person" key="sysPersonMlinkCategory.fdEnabled"/>
					</td>
					<td width="35%">
						<xform:radio property="fdEnabled" value="${sysPersonMlinkCategoryForm.method_GET == 'add' ? 'true' : sysPersonMlinkCategoryForm.fdEnabled}">
							<xform:enumsDataSource enumsType="common_yesno"  />
						</xform:radio>
					</td>
					<td class="td_normal_title" width="15%">
						<bean:message bundle="sys-person" key="sysPersonMlinkCategory.fdOrder"/>
					</td><td width="35%">
						<xform:text property="fdOrder" style="width:85%" />
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<table  class="tb_normal" width="100%" id="TABLE_DocList" style="TABLE-LAYOUT: fixed;WORD-BREAK: break-all;">
							<tr>
								<td width="5%" KMSS_IsRowIndex="1" class="td_normal_title">
									<bean:message key="page.serial" />
								</td>
								<td width="50px" class="td_normal_title" align="center"><bean:message key="sysPersonSysNavLink.fdIcon" bundle="sys-person"/></td>
								<td width="25%" class="td_normal_title"><bean:message key="sysPersonSysNavLink.fdName" bundle="sys-person"/></td>
								<td width="50%" class="td_normal_title"><bean:message key="sysPersonSysNavLink.fdUrl" bundle="sys-person"/></td>
								<td width="110px;" align="center" class="td_normal_title">
									<a href="javascript:void(0);" onclick="selectLinks();" class="com_btn_link ">
										<bean:message key="sysPersonSysNavLink.fromSys" bundle="sys-person"/>
									</a>&nbsp
									<a href="javascript:void(0);" onclick="addCustom();" class="com_btn_link">
										<bean:message key="sysPersonSysNavLink.fromInput" bundle="sys-person"/>
									</a>
								</td>
							</tr>
							
							<!--基准行-->
							<tr KMSS_IsReferRow="1" style="display:none">
								<td KMSS_IsRowIndex="1">
									!{index}
								</td>
								<td width="50px" align="center" valign="middle" class="icon">
									<div class="mui mui-approval" data-claz="mui-approval">
										<input name="fdLinks[!{index}].fdIcon" class="inputsgl" type="hidden" value="mui-approval" />
										<input name="fdLinks[!{index}].fdImg" class="inputsgl" type="hidden" value="" />
									</div>
								</td>
								<td width="25%">
									<input type="hidden" name="fdLinks[!{index}].fdOrder" value="!{index}"/>
									<input type="hidden" name="fdLinks[!{index}].fdId"/>
									<xform:text property="fdLinks[!{index}].fdName" style="width:95%" subject="${lfn:message('sys-person:sysPersonMlink.fdName') }" required="true" />
									<!-- <input name="fdLinks[!{index}].fdName" validate="required" class="inputsgl" style="width: 95%" /> -->
									<span class="txtstrong">*</span>
								</td>
								<td width="50%">
									<input name="fdLinks[!{index}].fdUrl" validate="required" class="inputsgl" style="width: 95%" /><span class="txtstrong">*</span>
								</td>
								<td align="center" width="85px;">
									<a href="javascript:void(0);" onclick="DocList_DeleteRow();" style="text-decoration: none">&nbsp;<img src="${KMSS_Parameter_StylePath}icons/delete.gif" border="0"  title="<bean:message key="button.delete"/>"/></a>
									<a href="javascript:void(0);" onclick="DocList_MoveRow(-1);" style="text-decoration: none">&nbsp;<img src="${KMSS_Parameter_StylePath}icons/up.gif" border="0"  title="<bean:message key="button.moveup"/>"/></a>
									<a href="javascript:void(0);" onclick="DocList_MoveRow(1);" style="text-decoration: none">&nbsp;<img src="${KMSS_Parameter_StylePath}icons/down.gif" border="0"  title="<bean:message key="button.movedown"/>"/></a>
								</td>
							</tr>
							
							<!--内容行-->
							<c:if test="${sysPersonMlinkCategoryForm.method_GET=='edit'}">
								<c:forEach items="${sysPersonMlinkCategoryForm.fdLinks}" var="itemForm" varStatus="vstatus">
									<tr KMSS_IsContentRow="1">
										<td width="10%" KMSS_IsRowIndex="1" id="KMSS_IsRowIndex_Edit">${vstatus.index+1}</td>
										<td width="50px"  align="center" valign="middle" class="icon">
										<c:if test="${not empty itemForm.fdIcon}">
											<div class="mui ${itemForm.fdIcon}" data-claz="${itemForm.fdIcon}">
												<input name="fdLinks[${vstatus.index}].fdIcon" type="hidden" value="${itemForm.fdIcon}"/>
												<input name="fdLinks[${vstatus.index}].fdImg" type="hidden" value=""/>
											</div>
										</c:if>
										<c:if test="${not empty itemForm.fdImg}">
											<div class="mui imgBox" data-claz="" style="background: url('${LUI_ContextPath}${itemForm.fdImg}') no-repeat center;background-size: contain">
												<input name="fdLinks[${vstatus.index}].fdIcon" type="hidden" value=""/>
												<input name="fdLinks[${vstatus.index}].fdImg" type="hidden" value="${itemForm.fdImg}"/>
											</div>
										</c:if>

										</td>
										<td width="25%">
											<input type="hidden" name="fdLinks[${vstatus.index}].fdOrder" value="${itemForm.fdOrder}"/>
											<input type="hidden" name="fdLinks[${vstatus.index}].fdId" value="${itemForm.fdId}"/>
											<xform:text property="fdLinks[${vstatus.index}].fdName" style="width:95%" subject="${lfn:message('sys-person:sysPersonMlink.fdName') }" required="true" />
											<%-- <input name="fdLinks[${vstatus.index}].fdName" validate="required" class="inputsgl" style="width: 95%" value=""/> --%>
											<span class="txtstrong">*</span>
										</td>
										<td width="50%">
											<input name="fdLinks[${vstatus.index}].fdUrl" validate="required" class="inputsgl" style="width: 95%" value="${itemForm.fdUrl}"/><span class="txtstrong">*</span>
										</td>
										<td align="center" width="85px;">
											<a href="javascript:void(0);" onclick="DocList_DeleteRow();" style="text-decoration: none">&nbsp;<img src="${KMSS_Parameter_StylePath}icons/delete.gif" border="0" title="<bean:message key="button.delete"/>" /></a>
											<a href="javascript:void(0);" onclick="DocList_MoveRow(-1);" style="text-decoration: none">&nbsp;<img src="${KMSS_Parameter_StylePath}icons/up.gif" border="0" title="<bean:message key="button.moveup"/>" /></a>
											<a href="javascript:void(0);" onclick="DocList_MoveRow(1);" style="text-decoration: none">&nbsp;<img src="${KMSS_Parameter_StylePath}icons/down.gif" border="0" title="<bean:message key="button.movedown"/>" /></a>
										</td>
									</tr>
								</c:forEach>
							</c:if>
						</table>
					</td>
				</tr>
				
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-person" key="sysPersonMlinkCategory.authEditors"/>
					</td>
					<td width="85%" colspan="3">
						<xform:address 
							propertyId="authEditorIds"
							propertyName="authEditorNames"
						    mulSelect="true" 
						    orgType="ORG_TYPE_ALL" textarea="true"
						    style="width:95%" />
					</td>
				</tr>
			</table> 
		</div>
		<html:hidden property="docCreatorId"/>
		<html:hidden property="docCreateTime"/>
		<html:hidden property="fdId" />
		<html:hidden property="method_GET" />
		<script>
			Com_IncludeFile("dialog.js|doclist.js");
			$KMSSValidation();
			function addCustom() {
				DocList_AddRow(document.getElementById("TABLE_DocList"));
			}

			seajs.use(["lui/jquery","lui/dialog"], function($, dialog) {
				function icon($target) {
					dialog.iframe("/sys/mportal/sys_mportal_menu/sysMportalMenu.do?method=icon&iconTypeRange=2", "${ lfn:message('sys-person:sysPersonMyNavLink.icon.select') }",	
						function(returnData) {
							if (!returnData)
								return;
							else {
								console.log(returnData);
								if(returnData.type){ //素材库
									var tUrl = returnData.url;
									if(tUrl.indexOf("/") == 0){
										tUrl = tUrl.substring(1);
									}
									tUrl = Com_Parameter.ContextPath + tUrl;
									$target.css({
										"background": "url('"+tUrl+"') no-repeat center",
										"background-size": "contain"
									})
									var claz1 = $target.attr('data-claz');
									$target.removeClass(claz1);
									$target.addClass('mui');
									$target.addClass('imgBox');
									$target.parent().find("input[type='hidden']").eq(0).val("");  //icon 图标
									$target.parent().find("input[type='hidden']").eq(1).val(returnData.url); // 素材库
								}else {
									var iconType = returnData.iconType; // 1、图片图标      2、字体图标     3、文字
									var claz1 = $target.attr('data-claz');
									var claz2 = (iconType == 2) ? returnData.className : "";
									var text = (iconType == 3) ? returnData.text : "";
									$target.removeAttr("style");
									$target.removeClass(claz1);
									$target.addClass(claz2);
									$target.addClass('mui');
									$target.attr('data-claz', 'mui ' + claz2);
									$target.removeClass('imgBox');//移除素材库
									$target.parent().find("input[type='hidden']").eq(0).val((iconType == 3) ? text : claz2);  //icon 图标
									$target.parent().find("input[type='hidden']").eq(1).val(""); // 素材库
								}
							}
						}, {
							width : 600,
							height : 550
						});
				}
				
				$('#TABLE_DocList').on('click',
						function(evt) {
							var $target = $(evt.target);
							if ($target.hasClass('mui')) {
								icon( $target);
						}
				});
			});


			function selectLinks() {
				seajs.use(['lui/dialog', 'lui/jquery' ],
						function(dialog) {
							dialog.iframe("/sys/person/sys_person_mlink/sysPersonMlink_dalog.jsp","${ lfn:message('sys-person:sysPersonMyNavLink.fdUrl.select') }",function(val) {
								if (!val) {
									return;
								} else {
									for (var i = 0; i < val.length; i ++) {
										var _val = val[i];
										var rowData = {
												"fdLinks[!{index}].fdName" : _val.name,
												"fdLinks[!{index}].fdUrl" : _val.link
										};
										DocList_AddRow('TABLE_DocList', null, rowData);
									}
								}
							}, {
								width : 600,
								height : 550
							});
						});
			}
		</script>
	</html:form>
	</template:replace>
</template:include>