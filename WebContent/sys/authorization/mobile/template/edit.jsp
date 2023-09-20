<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.DateUtil,java.util.Date,com.landray.kmss.util.ResourceUtil" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<template:include ref="mobile.edit" compatibleMode="true" tiny="true">
	<template:replace name="title">
		<bean:message bundle="sys-authorization" key="sysAuthTemplate.use.title" />
	</template:replace>

	<%-- 权限请查看design.xml --%>
	<kmss:authShow baseOrgIds="${sysAuthRoleForm.authEditorIds}">
		<c:set var="isOnlyOrgEditor" value="1"/>
	</kmss:authShow>
	<kmss:authShow roles="ROLE_SYSAUTHROLE_ADMIN;SYSROLE_ADMIN">
		<c:set var="isOnlyOrgEditor" value="0"/>
	</kmss:authShow>
	<c:if test="${not empty sysAuthRoleForm.fdTemplateId}">
		<kmss:authShow baseOrgIds="${sysAuthRoleForm.fdCreatorId}">
			<c:set var="isOnlyOrgEditor" value="0"/>
		</kmss:authShow>
	</c:if>
	<c:if test="${sysAuthRoleForm.fdType == '1'}">
		<kmss:authShow authAreaIds="${sysAuthRoleForm.authAreaId}">
		<c:set var="isOnlyOrgEditor" value="0"/>
		</kmss:authShow>
	</c:if>
	<c:if test="${param.type == '1' && sysAuthRoleForm.fdType == '2'}">
		<kmss:authShow authAreaIds="${sysAuthRoleForm.authAreaId}">
		<c:set var="isOnlyOrgEditor" value="1"/>
		</kmss:authShow>
	</c:if>
	
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/authorization/mobile/resource/css/role.css?s_cache=${MUI_Cache}"></link>
		
	</template:replace>
	<c:set var="_sysAuthAssignMap" value="${sysAuthRoleForm['fdAuthAssignMap']}"/>
	<template:replace name="content"> 
		<html:form action="/sys/authorization/sys_auth_role/sysAuthRole.do">
			<div>
				<html:hidden property="fdAuthAssignIds" />
				<html:hidden property="fdId" />
				<html:hidden property="method_GET" />
				<html:hidden property="fdType"/>
				<html:hidden property="fdTemplateId"/>
				
				<div data-dojo-type="mui/view/DocScrollableView"
					data-dojo-mixins="mui/form/_ValidateMixin" id="scrollView"
					class="muiAuthContent gray">
					<div class="muiFlowInfoW muiFormContent"
						style="background: #fff;">
						<table class="muiSimple" cellpadding="0" cellspacing="0">
							<c:if test="${not empty fdAuthTemplateDesc}">
								<tr>
									<td colspan="2" class="muiTemplateHelp"><c:out value="${fdAuthTemplateDesc}"></c:out></td>
								</tr>
							</c:if>
							<tr>
								<td class="muiTitle"><bean:message bundle="sys-authorization" key="sysAuthRole.fdAuthName" /></td>
								<td>
									<c:choose>
										<c:when test="${isOnlyOrgEditor=='0'}">
											<xform:text property="fdName" align="right" subject="${ lfn:message('sys-authorization:sysAuthRole.fdAuthName')}" mobile="true" required="true"></xform:text>
										</c:when>
										<c:otherwise>
											<xform:text property="fdName" align="right" showStatus="readOnly" mobile="true"></xform:text>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr>
								<td class="muiTitle"><bean:message bundle="sys-authorization" key="table.sysAuthCategory" /></td>
								<td>
									<c:choose>
										<c:when test="${isOnlyOrgEditor=='0'}">
											<xform:select align="right" property="fdCategoryId" mobile="true">
												<xform:beanDataSource serviceBean="sysAuthCategoryService" orderBy="sysAuthCategory.fdOrder"/>
											</xform:select>
										</c:when>
										<c:otherwise>
											<xform:select align="right" property="fdCategoryId" mobile="true" showStatus="readOnly" htmlElementProperties="style='margin-right: 2rem;'">
												<xform:beanDataSource serviceBean="sysAuthCategoryService" orderBy="sysAuthCategory.fdOrder"/>
											</xform:select>
										</c:otherwise>
									</c:choose>
									
								</td>
							</tr>
							<tr>
								<td class="muiTitle"><bean:message bundle="sys-authorization" key="sysAuthRole.fdAuthDesc" /></td>
								<td>
									<c:choose>
										<c:when test="${isOnlyOrgEditor=='0'}">
											<xform:textarea align="right" property="fdDescription" mobile="true" placeholder="${ lfn:message('sys-authorization:mui.form.please.input') }"></xform:textarea>
										</c:when>
										<c:otherwise>
											<xform:textarea align="right" property="fdDescription" mobile="true" showStatus="readOnly"></xform:textarea>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr>
								<td class="muiTitle"><bean:message bundle="sys-authorization" key="sysAuthRole.fdOrgElements"/></td>
								<td>
									<c:choose>
										<c:when test="${isOnlyOrgEditor=='0' || isOnlyOrgEditor=='1'}">
											<xform:address align="right" propertyId="fdOrgElementIds" propertyName="fdOrgElementNames" mobile="true"
												orgType="ORG_TYPE_ALL|ORG_FLAG_BUSINESSYES" textarea="true" mulSelect="true" style="width: 90%">
											</xform:address>
										</c:when>
										<c:otherwise>
											<xform:address align="right" propertyId="fdOrgElementIds" propertyName="fdOrgElementNames" mobile="true" showStatus="readOnly"
												orgType="ORG_TYPE_ALL|ORG_FLAG_BUSINESSYES" textarea="true" mulSelect="true" style="width: 90%">
											</xform:address>
										</c:otherwise>
									</c:choose>
									
								</td>
							</tr>
							<tr>
								<td class="muiTitle"><bean:message bundle="sys-authorization" key="sysAuthRole.all"/></td>
								<td>
									<c:set var="_fdEnableAll" value="off" />
									<c:if test="${fdEnableAll == 'on'}">
										<c:set var="_fdEnableAll" value="on" />
									</c:if>
									<c:choose>
										<c:when test="${isOnlyOrgEditor=='0'}">
											<div class="" data-dojo-type="mui/form/switch/NewSwitch"
												  data-dojo-mixins="sys/authorization/mobile/resource/js/SwitchMixin" 
												  data-dojo-props="align:'right',leftLabel:'',rightLabel:'',value:'${_fdEnableAll}',property:'fdEnableAll'">
											</div>
										</c:when>
										<c:otherwise>
											<div class="" data-dojo-type="mui/form/switch/NewSwitch"
												  data-dojo-mixins="sys/authorization/mobile/resource/js/SwitchMixin" 
												  data-dojo-props="align:'right',leftLabel:'',rightLabel:'',value:'${_fdEnableAll}',property:'fdEnableAll',showStatus:'readOnly'">
											</div>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr>
								<td colspan="2" class="">
									<c:if test="${fn:length(sysAuthRoleForm['fdAuthAssignAllMap'])>0}">
										<c:forEach var="moduleMap" items="${sysAuthRoleForm['fdAuthAssignAllMap']}">
											<div class="muiAuthTemplateCard">
												<div class="muiAuthTitle"><span class="box"><span class="muiAuthIcion muiDetailDown"></span><span class="muiAuthModule"><c:out value="${fn:substringAfter(moduleMap.key,'_')}" /></span></span></div>
												<div class="muiAuthContent">
													<c:forEach var="roleMap" items="${moduleMap.value}">
														<div class="item">
												          <div>
												            <p><c:out value="${roleMap.value.name}" /></p>
												            <span><c:out value="${roleMap.value.desc}" /></span>
												          </div>
												         <div class="btn">
												         	<c:set var="_status" value="off" />
												         	<c:if test="${_sysAuthAssignMap[moduleMap.key]!=null && _sysAuthAssignMap[moduleMap.key][roleMap.key]!=null}">
																<c:set var="_status" value="on" />
															</c:if>
															<c:choose>
																<c:when test="${isOnlyOrgEditor=='0'}">
																	<div class="muiAuthItem" data-dojo-type="mui/form/switch/NewSwitch"
																		  data-dojo-mixins="sys/authorization/mobile/resource/js/SwitchMixin" 
																		  data-dojo-props="align:'right',leftLabel:'',rightLabel:'',value:'${_status }',property:'${roleMap.value.id}'">
																	 </div>
																</c:when>
																<c:otherwise>
																	<div class="muiAuthItem" data-dojo-type="mui/form/switch/NewSwitch"
																		  data-dojo-mixins="sys/authorization/mobile/resource/js/SwitchMixin" 
																		  data-dojo-props="align:'right',leftLabel:'',rightLabel:'',value:'${_status }',property:'${roleMap.value.id}',showStatus:'readOnly'">
																	 </div>
																</c:otherwise>
															</c:choose>
												         </div>
												    	</div>
													</c:forEach>
													
												</div>
											</div>
										</c:forEach>
									</c:if>
								</td>
							</tr>
						</table>
					</div>
					<c:if test="${param.method=='edit'}">
						<kmss:auth requestURL="/sys/authorization/sys_auth_role/sysAuthRole.do?method=delete&fdId=${param.fdId}&type=0" requestMethod="GET">
							<div class="muiAuthEditDelToolbar">
								<button type="button" onclick="window.onAuthDelete()" class="muiAuthEditBtn muiAuthBtnDel">
								  <bean:message bundle="sys-authorization" key="sysAuthRole.delete"/>
								</button>
							 </div>
						 </kmss:auth>
					 </c:if>
				</div>

		</html:form>
		
					<div class="muiAuthEditToolbar">
						 <button type="button" onclick="window.commitMethod()" class="muiAuthEditBtn">
                          <bean:message bundle="sys-authorization" key="sysAuthRole.ok"/>
						 </button>
					 </div>
	</template:replace>
</template:include>
<script type="text/javascript">
	require(["mui/form/ajax-form!sysAttendMainExcForm"]);
	require(['dojo/topic','mui/dialog/Tip',"mui/dialog/Confirm","dojo/dom-class","dojo/query","dojo/dom-style","dijit/registry","dojo/ready","mui/util","dojo/on","dojo/touch","dojo/request","mui/form/ajax-form"],
		function(topic,Tip,Confirm,domClass,query,domStyle,registry,ready,util,on,touch,request,ajaxForm){
		window.commitMethod=function(){
			var validorObj = registry.byId('scrollView');
			if(!validorObj.validate()){
				return false;
			}
			var authIds=document.getElementsByName("fdAuthAssignIds")[0];
			var authIdArr = query('.muiAuthTemplateCard input[type="hidden"][value="true"]');
			var authStr="";
			if(authIdArr.length>0){
				for (var i=0;i<authIdArr.length;i++) {
					authStr+= ";" + authIdArr[i].name;
				}
				if(authStr!="")
					authIds.value=authStr.substring(1);
				else
					authIds.value="";
			} else {
				authIds.value = "";
			}
			// 校验角色唯一
			var fdName = document.getElementsByName("fdName")[0];
			if(fdName != null) {
				var data = new KMSSData();
			    data.AddBeanData("sysAuthRoleService&fdId=${sysAuthRoleForm.fdId}&fdType=${sysAuthRoleForm.fdType}&fdName=" + encodeURIComponent(fdName.value));
			    var selectData = data.GetHashMapArray();
			    if (selectData != null && selectData[0] != null) {
			    	if(selectData[0]['isDuplicate'] == "true") {
			    		Tip.fail({
			                  text: "<bean:message bundle="sys-authorization" key="sysAuthTemplate.fdName.duplicate" />"
			                });
						return false;
					}
				}
			}

			var method = Com_GetUrlParameter(location.href,'method');
			if(method=='add'){
				Com_Submit(document.sysAuthRoleForm,'save','');
			}else{
				Com_Submit(document.sysAuthRoleForm,'update','');
			}
		}
		window.authToggle = function(value){
			var nodes = query('.muiAuthContent .muiAuthItem');
			for(var i=0;i<nodes.length;i++){
				var widget = registry.byNode(nodes[i]);
				widget.set("value", value ?"on":"off");
			}
		}
		window.onAuthDelete = function(){
			var self = this;
			var url = "/sys/authorization/sys_auth_role/sysAuthRole.do?method=delete&fdId=${param.fdId}&type=0";
			Confirm('<bean:message bundle="sys-authorization" key="sysAuthTemplate.delete.tip" />','',function(value){
				if(value){
					request(util.formatUrl(url), {
						handleAs : 'json',
						method : 'get',
						headers: {'Accept': 'application/json'},
						data : {}
					}).then(function(result){
						if(!result.status){
							Tip.fail({
								text : '<bean:message bundle="sys-mobile" key="mui.return.failure" />',
								callback:function(){
									window.toListPage();
								}
							});
							return;
						}
						Tip.success({
							text : '<bean:message bundle="sys-mobile" key="mui.return.success" />',
							callback:function(){
								window.toListPage();
							}
						});
					},function(e){
						Tip.fail({
							text : '<bean:message bundle="sys-mobile" key="mui.return.failure" />',
							callback:function(){
								window.toListPage();
							}
						});
					});
				}
			});
		}
		window.toListPage = function(){
			var url = "/sys/authorization/mobile/template/index.jsp";
			location.href=util.formatUrl(url);
		}
		ready(function(){
			on(query(".muiAuthTitle .box"),touch.press,function(){
				var p = query(this).parent().parent();
				var iconNode = query('.muiAuthIcion',this);
				if(domClass.contains(iconNode[0],'muiDetailDown')){
					domClass.add(iconNode[0], "muiDetailUp");
					domClass.remove(iconNode[0], "muiDetailDown");
				}else{
					domClass.add(iconNode[0], "muiDetailDown");
					domClass.remove(iconNode[0], "muiDetailUp");
				}
				
				domClass.toggle(query('.muiAuthContent',p[0])[0],'muiHide');
			});
			topic.subscribe('sys/authorization/AuthChanged',function(widget,evt){
				if(widget.property=='fdEnableAll'){
					authToggle(evt);
					return;
				}
			});
			ajaxForm.ajaxForm("[name='sysAuthRoleForm']", {
				success:function(result){
					Tip.success({
						text: "${lfn:message('sys-mobile:mui.return.success') }", 
						callback: function(){
							window.location.href=util.formatUrl("/sys/authorization/mobile/template/index.jsp"); 
						}, 
						cover: true
					});
				},
				error:function(result){
					var errTxt = "${lfn:message('sys-mobile:mui.return.failure') }";
					var options = {
						text : errTxt,
						callback : function(){
							window.location.href=util.formatUrl("/sys/authorization/mobile/template/index.jsp");
						}
					};
					Tip.fail(options);
				}
			});
		});
});	


</script>


