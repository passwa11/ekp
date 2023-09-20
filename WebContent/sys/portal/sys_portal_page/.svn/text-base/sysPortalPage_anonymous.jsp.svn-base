<%@ page import="com.landray.kmss.sys.portal.xml.model.SysPortalFooter"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="net.sf.json.JSONArray"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.landray.kmss.sys.portal.xml.model.SysPortalHeader"%>
<%@ page import="java.util.Collection"%>
<%@ page import="com.landray.kmss.sys.portal.util.PortalUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld" prefix="portal"%>
<%
request.setAttribute("sys.ui.theme", "sky_blue");
%>
<template:include ref="default.edit" width="95%" sidebar="no">
	<template:replace name="title">
	${ lfn:message('sys-portal:sysPortalPage.msg.title') }
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<c:choose>
				<c:when test="${ sysPortalPageForm.method_GET == 'addAnonymous' }">
					<ui:button text="${lfn:message('button.save') }" order="2" onclick=" Com_Submit(document.sysPortalPageForm, 'save');"></ui:button>
				</c:when>
				<c:when test="${ sysPortalPageForm.method_GET == 'editAnonymous' }">	
				    <ui:button text="${lfn:message('sys-portal:sysPortalPage.submenuMaintenance')}" order="1" onclick="submenuMaintenance();"></ui:button>					
					<ui:button text="${lfn:message('button.update') }" order="2" onclick=" Com_Submit(document.sysPortalPageForm, 'update');">
					</ui:button>	
					<kmss:auth requestURL="/sys/portal/sys_portal_page/sysPortalPage.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
						<ui:button order="3" text="${lfn:message('button.delete') }" onclick="deletePage();"></ui:button>
					</kmss:auth> 						
				</c:when>
			</c:choose> 
			<ui:button text="${lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav" style="height:40px;line-height:40px;">
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home">
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message('sys-portal:module.sys.portal') }">
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message('sys-portal:table.sysPortalPage') }">
			</ui:menu-item>
		</ui:menu>
	</template:replace>	
	<template:replace name="content">
		<html:form action="/sys/portal/sys_portal_page/sysPortalPage.do">
			<script>
			Com_IncludeFile("security.js");
			
			Com_Parameter.event["confirm"].unshift(function() {
				var xobj = document.getElementById("pageDetails[0].docContent");
				xobj.value = base64Encodex(xobj.value);
				return true;
			});
			
			Com_Parameter.event["submit_failure_callback"].unshift(function() {
				var xobj = document.getElementById("pageDetails[0].docContent");
				if(xobj.value.indexOf('\u4645\u5810\u4d40') > -1){
					var vData = {"fdDesignerHtml" : xobj.value};
					xobj.value = Portal_Base64Decodex(vData);
				}
			});
			
			// 解码
			function Portal_Base64Decodex(arg) {
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
			</script>
			<script type="text/javascript">
			    /** 子菜单维护 **/
			    function submenuMaintenance(){
			    	var params = {"fdPageId":"${sysPortalPageForm.fdId}"}
			    	var result = null;
			    	// 根据页面ID查询门户系统导航菜单，如果存在则进入修改页面，如果不存在则进入新增页面
					$.ajax({
						url: Com_Parameter.ContextPath + "sys/portal/sys_portal_nav/sysPortalNav.do?method=getPortletNavByPageId",
						async: false,
						data: params,
						type: "POST",
						dataType: 'json',
						success: function (data) {
						  var url = "";
	                      if(data && data.fdId){
	                    	 url = '<c:url value="/sys/portal/sys_portal_nav/sysPortalNav.do?method=edit&s_css=default"/>&fdId='+data.fdId+'&switchAnonymous=1';
	                      }else{
	          		    	 url = '<c:url value="/sys/portal/sys_portal_nav/sysPortalNav.do?method=add&s_css=default"/>&fdPageId=${sysPortalPageForm.fdId}&switchAnonymous=1';
	                      }
	                      window.open( url, '_blank' );
						},
						error: function (e) {}
					});
			    	
			    }
			    /** 删除  **/
				function deletePage(){
					seajs.use(['lui/dialog'],function(dialog){
						dialog.confirm("${ lfn:message('sys-portal:sysPortalPage.msg.delete') }",function(val){
							if(val==true){
								location.href = "${LUI_ContextPath}/sys/portal/sys_portal_page/sysPortalPage.do?method=delete&fdId=${sysPortalPageForm.fdId}&needReturn=false";
							}
						})
					});
				}
				/** 主题选择  **/
				function selectTheme(){
					seajs.use(['lui/dialog'],function(dialog){
						dialog.build({
							config : {
									width : 700,
									height : 500,  
									title : "${ lfn:message('sys-portal:sysPortalPage.msg.selectTheme') }",
									content : {  
										type : "iframe",
										url : "/sys/portal/designer/jsp/selecttheme.jsp"
									}
							},
							callback : function(value, dia) {
								if(value==null){
									return ;
								}
								$("[name='fdTheme']").val(value.ref);
								$("[name='fdThemeName']").val(value.name);
							}
						}).show(); 
					});
				}
				function selectIcon(){
					seajs.use(['lui/dialog'],function(dialog){
						dialog.build({
							config : {
									width : 750,
									height : 500,
									title : "${ lfn:message('sys-portal:sysPortalPage.msg.selectIcon') }",
									content : {  
										type : "iframe",
										url : "/sys/ui/jsp/icon.jsp"
									}
							},
							callback : function(value, dia) {
								if(value==null){
									return ;
								}
								if(value.url){//素材库
									var imgUrl = value.url;
									if(imgUrl.indexOf("/") == 0){
										imgUrl = imgUrl.substring(1);
									}
									$(".lui_img_l").css("display","block");
									$(".lui_img_l").attr('src',Com_Parameter.ContextPath+imgUrl);
									$("#iconPreview").attr("class", "lui_icon_l " + value);
									$("[name='fdIcon']").val("");
									$("[name='fdImg']").val(value.url);
								}else {//系统
									$("#iconPreview").attr("class", "lui_icon_l " + value);
									$(".lui_img_l").css("display","none")
									$("[name='fdImg']").val("");
									$("[name='fdIcon']").val(value);
								}
							}
						}).show(); 
					});
				}
				LUI.ready(function(){
					window.$ = LUI.$;
					
					if("${sysPortalPageForm.fdType}"!="1"){
						if(LUI("pageEdit").navs.length <= 0){
							LUI("pageEdit").on("layoutDone",function(){
								LUI("pageEdit").navs[1].navFrame.hide();
							});
						}else{
							LUI("pageEdit").navs[1].navFrame.hide();
						}					
					}
				});
				function onFdTypeChange(val,obj){
					if(val=="1"){
						LUI("pageEdit").navs[1].navFrame.show();
						$("#portal_page_url").hide();
					}else{
						LUI("pageEdit").navs[1].navFrame.hide();
						$("#portal_page_url").show();
					} 
				}
			</script>
			<ui:tabpanel id="pageEdit">
				<!-- 基本信息 -->
				<ui:content title="${ lfn:message('sys-portal:sysPortalPage.msg.baseInfo')}">
				 	<table class="tb_normal" width=100%>
						<tr>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="sys-portal" key="sysPortalPage.fdName"/>
							</td>
							<td width="35%">
								<xform:text property="fdName" subject="${ lfn:message('sys-portal:sysPortalPage.docSubject') }" showStatus="edit" required="true" style="width:90%" />
							</td>
							<td class="td_normal_title" width=15% rowspan="2">							
								<bean:message bundle="sys-portal" key="sysPortalPage.fdIcon"/>
							</td>
							<td width="35%" rowspan="2">
								<div class="lui_icon_l lui_icon_on ">
									<c:if test="${not empty sysPortalPageForm.fdIcon }">
										<div style="cursor: pointer;" id='iconPreview' class="lui_icon_l ${ sysPortalPageForm.fdIcon }" onclick="selectIcon()">
											<img class="lui_img_l" src="" width="100%">
										</div>
									</c:if>
									<c:if test="${not empty sysPortalPageForm.fdImg }">
										<div style="cursor: pointer;" id='iconPreview' class="lui_icon_l" onclick="selectIcon()">
											<img class="lui_img_l" src="${LUI_ContextPath}${sysPortalPageForm.fdImg}" width="100%">
										</div>
									</c:if>
								</div>
								<a href="javascript:void(0)" onclick="selectIcon()">${ lfn:message('sys-portal:sysPortalPage.msg.select') }</a>
								<html:hidden property="fdIcon" style="width:90%" />
								<html:hidden property="fdImg" style="width:90%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>									
								<bean:message bundle="sys-portal" key="sysPortalPage.fdTitle"/>
							</td>
							<td width="35%">			 
								<xform:text property="fdTitle" style="width:90%" showStatus="edit" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="sys-portal" key="sysPortalPage.fdType"/>
							</td>
							<td width="85%" colspan="3">
								<div style="float: left;">
									<xform:radio property="fdType" required="true" onValueChange="onFdTypeChange" showStatus="edit">
										<xform:enumsDataSource enumsType="sys_portal_page_type" />
									</xform:radio>
								</div>
								<div id="portal_page_url" style="${sysPortalPageForm.fdType == '2' ? '' :'display:none;' }float:left;width:60%;">
									<xform:text property="fdUrl" style="width: 100%;" showStatus="edit" />
								</div>
							</td> 
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="sys-portal" key="sysPortalPage.fdTheme"/>
							</td>
							<td width="35%" colspan="3">							 
							 	<xform:dialog subject="${ lfn:message('sys-portal:sysPortalPage.fdTheme') }" style="width:90%" required="true" propertyId="fdTheme" propertyName="fdThemeName" showStatus="edit">
								selectTheme()
								</xform:dialog>
							</td>
						</tr>
						 <%-- 所属场所 --%>
						<c:import url="/sys/authorization/sys_auth_area/sysAuthArea_field.jsp" charEncoding="UTF-8">
			                <c:param name="id" value="${sysPortalPageForm.authAreaId}"/>
			            </c:import>
						<tr>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="sys-portal" key="sysPortalPage.msg.uiconfig"/>
							</td>
							<td width="35%" colspan="3">							 
							 	<xform:radio property="fdUsePortal" showStatus="edit">
							 		<xform:simpleDataSource value="true">${ lfn:message('sys-portal:sysPortalPage.msg.useportal') }</xform:simpleDataSource>
							 		<xform:simpleDataSource value="false">${ lfn:message('sys-portal:sysPortalPage.msg.usepage') }</xform:simpleDataSource>
							 	</xform:radio>
							</td>
						</tr>
						<tbody>
							<tr>
								<td class="td_normal_title" width="15%">
									<bean:message bundle="sys-portal" key="sysPortalPage.fdEditor"/>
								</td>
								<td colspan="3">
									<xform:address textarea="true" mulSelect="true" propertyId="authEditorIds" propertyName="authEditorNames" style="width:96%;height:90px;" showStatus="edit" />
									<br><span class="com_help">${ lfn:message('sys-portal:sysPortalMain.msg.nadmin') } </span>
								</td>
							</tr> 
						</tbody>
						<tr>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="sys-portal" key="sysPortalHtml.docCreator"/>
							</td><td width="35%">
								<xform:address propertyId="docCreatorId" propertyName="docCreatorName" orgType="ORG_TYPE_PERSON" showStatus="view" />
							</td>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="sys-portal" key="sysPortalHtml.docCreateTime"/>
							</td><td width="35%">
								<xform:datetime property="docCreateTime" showStatus="view" />
							</td>
						</tr>
						<c:if test="${sysPortalPageForm.method_GET!='addAnonymous'}">
							<tr>
								<td class="td_normal_title" width=15%>
									<bean:message bundle="sys-portal" key="sysPortalHtml.docAlteror"/>
								</td><td width="35%">
									<xform:address propertyId="docAlterorId" propertyName="docAlterorName" orgType="ORG_TYPE_PERSON" />
								</td>
								<td class="td_normal_title" width=15%>
									<bean:message bundle="sys-portal" key="sysPortalHtml.docAlterTime"/>
								</td><td width="35%">
									<xform:datetime property="docAlterTime" />
								</td>
							</tr>
						</c:if>
					</table>
				</ui:content>
				<!-- 页面配置 -->
				<ui:content title="${ lfn:message('sys-portal:sysportal.anonymous') }${lfn:message('sys-portal:sysPortalPage.msg.pageEdit')}" id="anonymousPanel">
					<portal:designer scene="anonymous" ref="template.anonymous.default" style="width:100%;" property="pageDetails[0].docContent" />
				</ui:content>
			</ui:tabpanel> 
			<html:hidden property="fdId" />
			<html:hidden property="method_GET" />
			<script type="text/javascript">	
				Com_IncludeFile("validator.jsp|validation.js|plugin.js|validation.jsp|xform.js", null, "js");
			</script>		
			<script>
				$KMSSValidation(document.forms['sysPortalPageForm']);
			</script>
		</html:form>
	</template:replace>
</template:include>