<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit" width="95%" sidebar="no">
	<template:replace name="title"> 
		<c:choose>
			<c:when test="${ sysPortalLinkForm.fdType == '1' }">
				${ lfn:message('sys-portal:sys_portal_link_type_1') }
			</c:when>
			<c:when test="${ sysPortalLinkForm.fdType == '2' }">					
				${ lfn:message('sys-portal:sys_portal_link_type_2') }	
			</c:when>
		</c:choose>	
	</template:replace>
 	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav" style="height:40px;line-height:40px;">
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" >
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message('sys-portal:module.sys.portal') }">
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message('sys-portal:nav.sys.portal.portlet') }">
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message(fn:replace('sys-portal:sys_portal_link_type_***','***',sysPortalLinkForm.fdType)) }">
			</ui:menu-item>
		</ui:menu>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
				<c:choose>
					<c:when test="${ sysPortalLinkForm.method_GET == 'add' }">
						<ui:button text="${lfn:message('button.save') }" order="2" onclick=" Com_Submit(document.sysPortalLinkForm, 'save');">
						</ui:button>
					</c:when>
					<c:when test="${ sysPortalLinkForm.method_GET == 'edit' }">					
						<ui:button text="${lfn:message('button.update') }" order="2" onclick=" Com_Submit(document.sysPortalLinkForm, 'update');">
						</ui:button>	
						<kmss:auth requestURL="/sys/portal/sys_portal_link/sysPortalLink.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
							<ui:button order="3" text="${lfn:message('button.delete') }" onclick="deleteLink();">
							</ui:button>
						</kmss:auth>			
					</c:when>
				</c:choose>
				<ui:button text="${lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();">
				</ui:button>
		</ui:toolbar>
	</template:replace>	
	<template:replace name="content">
		<script>
		Com_IncludeFile("doclist.js");
		</script>
		<script>
		function deleteLink(){
			seajs.use(['lui/dialog'],function(dialog){
				dialog.confirm("${ lfn:message('sys-portal:sysPortalPage.msg.delete') }",function(val){
					if(val==true){
						location.href = "${LUI_ContextPath}/sys/portal/sys_portal_link/sysPortalLink.do?method=delete&fdId=${sysPortalLinkForm.fdId}";
					}
				})
			});
		}
		
		function selectIcon(xi){
			seajs.use(['lui/dialog','lui/jquery'],function(dialog,$){
				dialog.build({
					config : {
							width : 750,
							height : 500,
							title : "${ lfn:message('sys-portal:sysPortalLinkDetail.msg.select') }",
							content : {  
								type : "iframe",
								url : "/sys/ui/jsp/icon.jsp?type=l&status=false"
							}
					},
					callback : function(value, dia) {
						if(value==null){
							return ;
						}
						$("#iconPreview_"+xi).attr("class","lui_icon_l "+value);
						document.getElementsByName("linkDetails["+xi+"].fdIcon")[0].value = value;
					}
				}).show(); 
			});
		}
		</script>
		<html:form action="/sys/portal/sys_portal_link/sysPortalLink.do">
		<p class="txttitle">
		<xform:select property="fdType" showStatus="view">
			<xform:enumsDataSource enumsType="sys_portal_link_type" />
		</xform:select>
		</p> 
		<center>
		<table class="tb_normal" width=95%>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-portal" key="sysPortalLink.fdName"/>
					<html:hidden property="fdType" /> 
				</td>
				<td width="85%" colspan="3">
					<xform:text property="fdName" style="width:85%" />
				</td> 
			</tr>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-portal" key="sysportal.switch.anonymous"/>
				</td>
				<td colspan="3" >
				 	<c:import
						url="/sys/portal/designer/jsp/sys_anonym_edit.jsp"
						charEncoding="UTF-8">
						<c:param name="formName" value="sysPortalLinkForm" />
					</c:import>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message  bundle="sys-portal" key="sysHomeNav.display"/>
				</td>
				<td width="85%" colspan="3">
					<label>
						<input type=radio name="fdDisplay" value="table" onclick="refreshDisplay();" checked> <bean:message key="list.columnTable"/>
					</label>
					<label>
						<input type=radio name="fdDisplay" value="data" onclick="refreshDisplay();"><bean:message bundle="sys-portal" key="sysHomeNav.display.data"/>
					</label>
				</td>
			</tr>
			<tr id="show-data-row" style="display:none;">
				<td colspan="4">
					<div class="txtstrong"><bean:message  bundle="sys-portal" key="sysHomeNav.msg.warning"/></div>
					<textarea id="show-data-content" style="width:100%;height:200px"></textarea>
				</td>
			</tr>
			<c:import url="/sys/person/sys_person_link/include/links.jsp" charEncoding="UTF-8" >
				<%-- 需要识别form，主要是用了属性 form.fdLinks，罗列链接 --%>
				<c:param name="formName" value="sysPortalLinkForm" />
				<%-- 链接类型（shortcut, hotlink） --%>
				<c:param name="linkType" value="${ sysPortalLinkForm.fdType == '1' ? 'hotlink' : 'shortcut' }" />
			</c:import>	
			<%-- 所属场所 --%>
			<c:import url="/sys/authorization/sys_auth_area/sysAuthArea_field.jsp" charEncoding="UTF-8">
		        <c:param name="id" value="${sysPortalLinkForm.authAreaId}"/>
		    </c:import>
			<tr>
				<td class="td_normal_title" width="15%">${ lfn:message('sys-portal:common.msg.editors') }</td>
				<td colspan="3">
					<xform:address textarea="true" mulSelect="true" propertyId="fdEditorIds" propertyName="fdEditorNames" style="width:100%;height:90px;" ></xform:address>
				</td>
			</tr> 
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-portal" key="sysPortalLink.docCreator"/>
				</td><td width="35%">
					<xform:address propertyId="docCreatorId" propertyName="docCreatorName" orgType="ORG_TYPE_PERSON" showStatus="view" />
				</td>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-portal" key="sysPortalLink.docCreateTime"/>
				</td><td width="35%">
					<xform:datetime property="docCreateTime" showStatus="view" />
				</td>
			</tr>
			<c:if test="${ sysPortalLinkForm.method_GET != 'add' }">
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-portal" key="sysPortalLink.docAlteror"/>
					</td><td width="35%">
						<xform:address propertyId="docAlterorId" propertyName="docAlterorName" orgType="ORG_TYPE_PERSON" showStatus="view" />
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-portal" key="sysPortalLink.docAlterTime"/>
					</td><td width="35%">
						<xform:datetime property="docAlterTime" showStatus="view" />
					</td>
				</tr>
			</c:if>
		</table>
		</center>
		<html:hidden property="fdId" />
		<html:hidden property="method_GET" />
		<script type="text/javascript">	
		Com_IncludeFile("validator.jsp|validation.js|plugin.js|validation.jsp|xform.js|jquery.js", null, "js");
		function refreshDisplay() {
			var value = $("[name='fdDisplay']:checked").val();
			if(value == 'data') {
				tableToData();
			} else if(value == 'table') {
				dataToTable();
			}
		}
		var linkDatas = new Array();
		function tableToData() {
			// 去掉头部的tr
			var len = $("#linksTable").find("tr").length - 1;
			linkDatas = new Array();
			if(length > 0) {
				for(var i = 0;i < len;i++) {
					var linkData = new Object();
					linkData.id = $("[name='fdLinks["+i+"].fdId']").val();
					linkData.name = $("[name='fdLinks["+i+"].fdName']").val();
					linkData.url = $("[name='fdLinks["+i+"].fdUrl']").val();
					linkData.icon = $("[name='fdLinks["+i+"].fdIcon']").val();
					linkData.img = $("[name='fdLinks["+i+"].fdImg']").val();
					var langNameInputs = $("[name^='fdLinks["+i+"].dynamicMap']");
					if(langNameInputs.length > 0) {
						var langNames = "";
						langNameInputs.each(function() {
							var reg = new RegExp("\\(fdName([A-Z]{2})\\)");
							var arr = reg.exec(this.name);
							var val = $(this).val();
							if(val != null && val != '') {
								langNames += arr[1]+"##"+val+"$$";
							}
						});
						if(langNames != '') {
							linkData.langNames = langNames;
						}
					}
					linkDatas.push(linkData);
				}
			}
			$("#show-data-content").val(JSON.stringify(linkDatas));
			$("#show-data-row").show();
			$("#linksTable").parent().parent().hide();
		}
		
		function dataToTable() {
			// 删除原来的
			$("#linksTable tr td [alt='del']").click();
			var jsonData = JSON.parse($("#show-data-content").val());
			// 增加数据
			AddSelectedNavLink(jsonData);
			$("#show-data-row").hide();
			$("#linksTable").parent().parent().show();
		}
		
		function switchChange(flag){
			$("input[name='fdAnonymous']").val(flag);
		}
		
		</script>		
		<script>
		$KMSSValidation(document.forms['sysPortalPageForm']);
		</script>
		</html:form>
		<br>
		<br>
	</template:replace>
</template:include>