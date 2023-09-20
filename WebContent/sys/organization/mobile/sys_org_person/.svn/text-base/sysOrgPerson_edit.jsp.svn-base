<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.organization.model.SysOrgElementExtProp"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page import="com.landray.kmss.sys.organization.forms.SysOrgPersonForm,com.landray.kmss.sys.organization.model.SysOrganizationConfig"%>
<c:set var="tiny" value="true" scope="request" />
<template:include ref="mobile.edit" compatibleMode="true" isNative="true">
	<template:replace name="title">
		<c:if test="${sysOrgPersonForm.method_GET == 'add'}">
			${ lfn:message('button.create') }${ lfn:message('sys-organization:sysOrgElement.person') }
		</c:if>
		<c:if test="${sysOrgPersonForm.method_GET == 'edit'}">
			${ lfn:message('button.edit') }${ lfn:message('sys-organization:sysOrgElement.person') }
		</c:if>
	</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/organization/mobile/css/person/person.css">
		<script>
			require([ "mui/form/validate/Validation", "mui/dialog/Confirm", "dojo/query"], function(validation, Confirm, query) {
				var validation = new validation();
				
				// 登录名校验
				var LoginNameValidators = {};
				validation.addValidator("uniqueLoginName", "<bean:message key='sysOrgPerson.error.loginName.mustUnique' bundle='sys-organization' />", uniqueLoginName);
				validation.addValidator("invalidLoginName", "<bean:message key='sysOrgPerson.error.newLoginNameSameOldName' bundle='sys-organization' />", invalidLoginName);
				var errorMsg ="<bean:message key='sysOrgPerson.error.loginName.abnormal' bundle='sys-organization'/>";
				<% 
					SysOrganizationConfig sysOrganizationConfig = new SysOrganizationConfig(); 
				   	String isLoginSpecialChar = sysOrganizationConfig.getIsLoginSpecialChar();
				%>
				var isLoginSpecialChar = <%=isLoginSpecialChar%>;
				<%  if("true".equals(isLoginSpecialChar)){%>
					errorMsg = "${ lfn:message('sys-organization:sysOrgElementExternal.person.loginSpecialChar') }";
				<% } %>
				validation.addValidator("normalLoginName", errorMsg, normalLoginName);
				function loginNameCheckUnique(bean, loginName,fdId,checkType) {
					var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=" 
							+ bean + "&loginName=" + loginName+"&fdId="+fdId+"&checkType="+checkType+"&date="+new Date());
					var xmlHttpRequest = new XMLHttpRequest();
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
				function uniqueLoginName(value) {
					var fdId = document.getElementsByName("fdId")[0].value;
					var result = loginNameCheckUnique("sysOrgPersonService", value, fdId, "unique");
					if (!result) 
						return false;
					return true;
				}
				function invalidLoginName(value){
					if (LoginNameValidators["lgName"] && (LoginNameValidators["lgName"]==value)){
					    return true;
				    }
				    LoginNameValidators["lgName"]=null;
					var fdId = document.getElementsByName("fdId")[0].value;
					var result = loginNameCheckUnique("sysOrgPersonService", value, fdId,"invalid");
					if (!result){ 
						var contentHTML = "<bean:message key='sysOrgPerson.newLoginName.ConfirmMsg' bundle='sys-organization' />";
						var callback = function(bool,dialog){
					    	if(bool){
					    		LoginNameValidators["lgName"]=value;
								return true;
					    	} else {
					    		return false;
					    	}
					    }  
						Confirm(contentHTML, null,callback);
					}
					return true;
				}
				function normalLoginName(value){
					var pattern;
					<% if("true".equals(isLoginSpecialChar)){%>
						pattern = new RegExp("^[A-Za-z0-9_@#$%^&()={}:;\'?/<>,.\"\\[\\]|\\-\\+ ]+$");
					<% }else{ %>
						pattern = new RegExp("^[A-Za-z0-9_]+$");
					<% }%>
					
					if(pattern.test(value)){
						return true;
					}else{
						return false;
					}
					return true;
				}
				
				// 手机校验
				validation.addValidator("uniqueMobileNo", "<bean:message key='sysOrgPerson.error.newMoblieNoSameOldName' bundle='sys-organization' />", uniqueMobileNo);
				validation.addValidator("phone", "<bean:message key='sysOrgPerson.error.newMoblieNoError' bundle='sys-organization' />", phone);
				function startsWith(value, prefix) {
					return value.slice(0, prefix.length) === prefix;
				}
				function phone(value){
					if (value == "") {
						return true;
					}
					// 国内手机号可以有+86，但是后面必须是11位数字
					// 国际手机号必须要以+区号开头，后面可以是6~11位数据
					if(startsWith(value, "+")) {
						if(startsWith(value, "+86")) {
							return /^(\+86)(-)?(\d{11})$/.test(value);
						} else {
							return /^(\+\d{1,5})(-)?(\d{6,11})$/.test(value);
						}
					} else {
						// 没有带+号开头，默认是国内手机号
						return /^(\d{11})$/.test(value);
					}					
				}
				function uniqueMobileNo(value){
					if(startsWith(value, "+86")) {
						// 如果是+86开头的手机号，保存到数据库时强制去掉+86前缀
						value = value.slice(3, value.length)
					}
					if(startsWith(value, "+")) {
						value = value.replace("+", "x");
					}
					var fdId = document.getElementsByName("fdId")[0].value;
					var result = mobileNoCheckUnique("sysOrgPersonService", value, fdId, "unique");
					if (!result)
						return false;
					return true;
				}
				function mobileNoCheckUnique(bean, mobileNo, fdId, checkType) {
					var url = encodeURI(Com_Parameter.ResPath
							+ "jsp/ajax.jsp?&serviceName=" + bean + "&mobileNo=" + mobileNo
							+ "&fdId=" + fdId + "&checkType=" + checkType + "&date="
							+ new Date());
					var xmlHttpRequest = new XMLHttpRequest();
					if (xmlHttpRequest) {
						xmlHttpRequest.open("GET", url, false);
						xmlHttpRequest.send();
						var result = xmlHttpRequest.responseText.replace(/\s/g, "")
								.replace(/;/g, "\n");
						if (result != "") {
							return false;
						}
					}
					return true;
				}
			});
			function save() {
				require(["dijit/registry"], function(registry) {
					var validateObj = registry.byId('scrollView');
					if (validateObj.validate()) {
						var method = '${sysOrgPersonForm.method_GET}';
						if(method == 'add') {
							Com_Submit(document.forms[0], "save");
						} else {
							var updateUrl = '${LUI_ContextPath}/sys/organization/sys_org_element_external/sysOrgElementExternalPerson.do?method=update&fdId=${sysOrgPersonForm.fdId}&List_Selected=${sysOrgPersonForm.fdId}';
							require(["dijit/registry", 'dojo/request', "dojo/query", 'mui/dialog/Tip'], function(registry, request, query, tip) {
								var validateObj = registry.byId('scrollView');
								if (validateObj.validate()) {
									var data = {};
									query("input", this.textNode).forEach(function(inputDom) {
										data[inputDom.name] = inputDom.value
								     });
									query("textarea", this.textNode).forEach(function(inputDom) {
										data[inputDom.name] = inputDom.value
								     });
									var processing = tip.processing();
									processing.show();
									request.post(updateUrl, {
										data: data
									}).then(function(result) {
										processing.hide(false);
										if (isJsonString(result)) {
											var json = JSON.parse(result);
											tip.fail({text: json.message});
										} else {
											tip.success({text: '${ lfn:message("return.optSuccess") }'});
											setTimeout(function() {
												history.back(-1);
											}, 2000)
										}
									});
								}
						    });
						}
					}
				});
			}
			
			function isJsonString(str) {
		        try {
		            if (typeof JSON.parse(str) == "object") {
		                return true;
		            }
		        } catch(e) {
		        }
		        return false;
		    }
			
			function invalidated() {
				var url = '${LUI_ContextPath}/sys/organization/sys_org_element_external/sysOrgElementExternalPerson.do?method=invalidated&fdId=${sysOrgPersonForm.fdId}&List_Selected=${sysOrgPersonForm.fdId}';
				require(['dojo/request', 'mui/dialog/Confirm', 'mui/dialog/Alert'], function(request, Confirm, Alert){
					var title = '${ lfn:message("return.systemInfo") }';
					var contentHtml = "${ lfn:message('sys-organization:sysOrgElementExternal.person.invalidated.tips') }";
					var callback = function(bool,dialog){
				    	if(bool){
				    		request.get(url, {
					            headers: {'Accept': 'application/json'},
					            handleAs: 'json'
							}).then(function() {
								var callback = function(){
					    			window.location.href = '${LUI_ContextPath}/sys/organization/mobile/sys_org_eco/list.jsp';
							    }
								Alert('${ lfn:message("return.optSuccess") }', '', callback);
							});
				    	}
				    }
					Confirm(contentHtml, null,callback);
				});
			}
		</script>
    </template:replace>
	<template:replace name="content">
		<div data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin,mui/form/_AlignMixin" id="scrollView">
			<div class="muiSysOrgEcoPersonDetail muiSysOrgEcoPersonDetail_noHeader">
				<xform:config showStatus="edit">
					<html:form action="/sys/organization/sys_org_element_external/sysOrgElementExternalPerson.do">
						<div class="propertyGap">${ lfn:message('sys-organization:sysOrgElementExternal.person.info') }</div>
						<table class="muiSimple" cellpadding="0" cellspacing="0">
							<tr>
								<td class="muiTitle"><bean:message bundle="sys-organization" key="sysOrgElement.fdName"/></td>
								<td>
									<xform:text property="fdName" mobile="true" align="right" required="true" showStatus="edit" validators="maxLength(200)"></xform:text>
								</td>
							</tr>
							<tr>
								<td class="muiTitle"><bean:message bundle="sys-organization" key="sysOrgPerson.fdMobileNo" /></td>
								<td>
									<xform:text property="fdMobileNo" mobile="true" align="right" required="true" showStatus="edit" validators="phone uniqueMobileNo"></xform:text>
								</td>
							</tr>
							<tr>
								<td class="muiTitle"><bean:message bundle="sys-organization" key="sysOrgPerson.fdLoginName" /></td>
								<td>
									<xform:text property="fdLoginName" mobile="true" align="right" required="true" showStatus="edit" validators="uniqueLoginName invalidLoginName normalLoginName"></xform:text>
								</td>
							</tr>
							<c:if test="${sysOrgPersonForm.method_GET=='add'}">
							<tr>
								<td class="muiTitle"><bean:message bundle="sys-organization" key="sysOrgPerson.fdPassword" /></td>
								<td>
									<xform:text property="fdNewPassword" mobile="true" align="right" required="true" showStatus="edit"></xform:text>
								</td>
							</tr>
							</c:if>
							<tr>
								<td class="muiTitle"><bean:message bundle="sys-organization" key="sysOrgPerson.fdEmail" /></td>
								<td>
									<xform:text property="fdEmail" mobile="true" align="right" showStatus="edit" validators="email"></xform:text>
								</td>
							</tr>
							<tr>
								<td class="muiTitle"><bean:message bundle="sys-organization" key="sysOrgPerson.fdCanLogin" /></td>
								<td>
									<xform:radio property="fdCanLogin" required="true"
												 mobile="true" mobileRenderType="normal">
										<xform:simpleDataSource value="true">
											<bean:message key="message.yes"/>
										</xform:simpleDataSource>
										<xform:simpleDataSource value="false">
											<bean:message key="message.no"/>
										</xform:simpleDataSource>
									</xform:radio>
								</td>
							</tr>
							<tr>
								<td class="muiTitle">${ lfn:message('sys-organization:sysOrgElementExternal.fdParent') }</td>
								<td>
									<xform:address required="true"
												propertyId="fdParentId" 
												propertyName="fdParentName" 
												orgType="ORG_TYPE_DEPT"
												otherProperties="isEco:true,cateId:'${cateId}'"
												isExternal="true"
												mobile="true"
												subject="${ lfn:message('sys-organization:sysOrgElementExternal.fdParent') }" />
								</td>
							</tr>
							<tr>
								<td class="muiTitle">${ lfn:message('sys-organization:sysOrgElement.post') }</td>
								<td>
									<xform:address 
												propertyId="fdPostIds" 
												propertyName="fdPostNames" 
												orgType="ORG_TYPE_POST"
												mobile="true"
												mulSelect="true"
												isExternal="true"
												otherProperties="isEco:true"
												subject="${ lfn:message('sys-organization:sysOrgElement.post') }" />
								</td>
							</tr>
						</table>
						
						<%-- 扩展属性 --%>
						<c:import url="/sys/organization/mobile/sysOrgExternal_common_ext_props_edit.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="sysOrgPersonForm"/>
							<c:param name="infoLable" value="${ lfn:message('sys-organization:sysOrgElementExternal.person.prop') }"/>
						</c:import>
						
						<html:hidden property="method_GET"/>
						<html:hidden property="fdId"/>
						<html:hidden property="fdParentId"/>
						<input type="hidden" name="cateId" value="${cateId}">
					</html:form>
				</xform:config>
			</div>
			<c:if test="${sysOrgPersonForm.method_GET == 'edit'}">
			<kmss:auth requestURL="/sys/organization/sys_org_element_external/sysOrgElementExternalPerson.do?method=invalidated&fdId=${sysOrgPersonForm.fdId}">
				<div class="tableGap"></div>
				<div class="muiSysOrgEcoPersonBtn" onclick="javascript:invalidated();">
					${ lfn:message('sys-organization:sysOrgEco.available.close') }
				</div>
				<div class="invalidatedNone"></div>
			</kmss:auth>
			</c:if>
			<div class="muiSysOrgEcoPersonBtn save">
				<div class="personSaveBtnDom" onclick="javascript:save();">
					<div>
						${ lfn:message('button.save') }
					</div>
				</div>
			</div>
			<div class="tableGap"></div>
		</div>
		<script>
			require([ "mui/form/ajax-form!sysOrgPersonForm"]);
		</script>
	</template:replace>
</template:include>
