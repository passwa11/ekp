<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit" sidebar="no">
	<template:replace name="head">
		<script>
			var dialogWin = window.top;
		</script>
		<script src="${LUI_ContextPath}/sys/ui/js/var.js"></script>
	</template:replace>
	<template:replace name="title">
	<c:choose>
		<c:when test="${ sysMportalMainForm.method_GET == 'add' }">
			新建 - 移动门户配置选项
		</c:when>
		<c:otherwise>
			<c:out value="${sysMportalMainForm.fdName}"/> - 编辑
		</c:otherwise>
	</c:choose>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav" >
			<ui:menu-item text="${lfn:message('home.home')}" href="/index.jsp" icon="lui_icon_s_home" />
			<ui:menu-item text="公共组件" />
			<ui:menu-item text="移动门户配置" />
			<ui:menu-item text="门户选配置" />
		</ui:menu>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
			<c:choose>
				<c:when test="${ sysMportalMainForm.method_GET == 'add' }">
					<ui:button text="${lfn:message('button.submit') }" 
							   order="2" 
							   onclick="submit('save')">
					</ui:button>
				</c:when>
				<c:when test="${ sysMportalMainForm.method_GET == 'edit' }">
					<ui:button text="${lfn:message('button.submit') }" 
							   order="2" 
							   onclick="submit('update');">
					</ui:button>
				</c:when>
			</c:choose>
			<ui:button text="${lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content"> 
		<html:form action="/sys/mportal/sys_mportal_main/sysMportalMain.do">
			<div class="lui_content_form_frame" style="padding:20px 0px">
				<table class="tb_normal" width=95% >
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-mportal" key="sysMportalMain.fdName"/>
						</td>
						<td width="85%" colspan="3">
							<xform:text property="fdName" style="width:85%" />
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15% >
							<bean:message bundle="sys-mportal" key="sysMportalMain.fdEnabled"/>
						</td>
						<td width="35%">
							<xform:radio property="fdEnabled">
								<xform:enumsDataSource enumsType="common_yesno" />
							</xform:radio>
						</td>
						<td class="td_normal_title" width=15% >
							<bean:message bundle="sys-mportal" key="sysMportalMain.fdOrder"/>
						</td>
						<td width="35%">
							<xform:text property="fdOrder" style="width:98%"/>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15% >
							内容
						</td>
						<td width="85%" colspan="3">
							<html:hidden property="fdPortletId"/>
							<html:text property="fdPortletName" style="width:80%" ></html:text>
							<a href="javascript:;" class="com_btn_link" onclick="selectPortlet();">
								选择
							</a>
							<div class="lui_mportlet_options" id="mportletOptions" style="padding-top:10px;">
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15% >
							<bean:message bundle="sys-mportal" key="sysMportalMain.docCreator"/>
						</td>
						<td width="35%">
							<xform:text property="docCreatorName" showStatus="view"/>
						</td>
						<td class="td_normal_title" width=15% >
							<bean:message bundle="sys-mportal" key="sysMportalMain.docCreateTime"/>
						</td>
						<td width="35%">
							<xform:text property="docCreateTime"  showStatus="view"/>
						</td>
					</tr>
				</table>
			</div>
			<html:hidden property="docCreatorId"/>
			<html:hidden property="docCreateTime"/>
			<html:hidden property="fdId" />
			<html:hidden property="fdPortletVars"/>
			<html:hidden property="method_GET" />
		</html:form>
		<script>
			$KMSSValidation(document.forms[0]);
			var lodingImg = "<img src='${LUI_ContextPath}/sys/ui/js/ajax.gif'/>";
			var  jsname;
			function selectPortlet() {
				seajs.use(['lui/dialog','lui/jquery'],function(dialog){
					dialog.iframe("/sys/mportal/sysMportal_dialog.jsp", 
							"${ lfn:message('sys-mportal:sysMportalMain.dialog.select') }", 
							function(val){
						if(!val){
							return;
						} else {
							$("[name='fdPortletId']").val(val.fdPortletId);
							$("[name='fdPortletName']").val(val.fdPortletName);
							var __jsname = val.uuid + "_mportlet";
							jsname = __jsname;
							$("#mportletOptions").html(lodingImg).show()
								.load("${LUI_ContextPath}/sys/mportal/mportlet_vars.jsp?x=" + (new Date().getTime()),
										{"fdId":val.fdPortletId,"jsname":jsname});
						}
						
					}, {width:750,height:550});
				});
			}
			function parseVars() {
				if(window[jsname] && window[jsname].VarSet) {
					var _varset = window[jsname].VarSet;
					var length =_varset.length;
					var fdVars = {};
					for( var i = 0; i < length; i ++ ) {
						fdVars[_varset[i].name] = _varset[i].getter();
					}
					var fdVarsString = JSON.stringify(fdVars);
					if(fdVarsString) {
						$("[name='fdPortletVars']").val(fdVarsString);
					}
				}
			};
		
			function submit(method) {
				parseVars();
				Com_Submit(document.sysMportalMainForm, method);
			}
			if('${sysMportalMainForm.method_GET}' == 'edit') {
				jsname = "_mportlet";
				seajs.use(['lui/jquery'], function($) {
					$(function() {
						var mid = $("[name='fdPortletId']").val();
						var _fdPortletVars = $("[name='fdPortletVars']").val();
						if(mid) {
							$("#mportletOptions").html(lodingImg).show()
							.load("${LUI_ContextPath}/sys/mportal/mportlet_vars.jsp?x=" + (new Date().getTime()),
									{"fdId":mid,"jsname":jsname}
									, function() {
										window[jsname].setValue(JSON.parse(_fdPortletVars));
									});
						}
					});
				});
			}
		</script>
	</template:replace>
</template:include>