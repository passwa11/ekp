<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="tiny" value="true" scope="request" />
<template:include ref="mobile.edit" compatibleMode="true" isNative="true">
	<template:replace name="title">
		<c:if test="${sysOrgPostForm.method_GET == 'add'}">
			${ lfn:message('button.create') }${ lfn:message('sys-organization:sysOrgElement.post') }
		</c:if>
		<c:if test="${sysOrgPostForm.method_GET == 'edit'}">
			${ lfn:message('button.edit') }${ lfn:message('sys-organization:sysOrgElement.post') }
		</c:if>
	</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/organization/mobile/css/person/person.css">
		<script>
			require([ "mui/form/validate/Validation", "mui/dialog/Confirm", "dojo/query"], function(validation, Confirm, query) {
				var validation = new validation();
				validation.addValidator("uniqueName", "${ lfn:message('sys-organization:sysOrgElementExternal.dept.repeat') }", uniqueName);
				validation.addValidator("checkNameUnique", "${ lfn:message('sys-organization:sysOrgElementExternal.disable.dept.repeat') }", checkNameUnique);

				function uniqueName() {
					var arr = query("input[name=fdName]");
					return check(arr[0].value, "unique");
				}
				function checkNameUnique() {
					var arr = query("input[name=fdName]");
					return check(arr[0].value, "invalid");
				}
				function check(fdName, checkType){
					var fdId = "${sysOrgDeptForm.fdId}";
					var url = Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=sysOrgElementService&fdName=" + fdName + "&fdId=" + fdId + "&fdOrgType=1&checkType=" + checkType;
					var xmlHttpRequest = new XMLHttpRequest();
					xmlHttpRequest.open("GET", url, false);
					xmlHttpRequest.send();
					var result = xmlHttpRequest.responseText.replace(/\s/g, "").replace(/;/g, "\n");
					if (result != "") {
						return false;
					}
					return true;
				}
			});

			function save() {
				require(["dijit/registry"], function(registry) {
					var validateObj = registry.byId('scrollView');
					if (validateObj.validate()) {
						var method = '${sysOrgPostForm.method_GET}';
						if(method == 'add') {
							Com_Submit(document.forms[0], "save");
						} else {
							var updateUrl = '${LUI_ContextPath}/sys/organization/sys_org_element_external/sysOrgElementExternalPost.do?method=update&fdId=${sysOrgPostForm.fdId}&List_Selected=${sysOrgPostForm.fdId}';
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
				var url = '${LUI_ContextPath}/sys/organization/sys_org_element_external/sysOrgElementExternalPost.do?method=invalidated&fdId=${sysOrgPostForm.fdId}&List_Selected=${sysOrgPostForm.fdId}';
				require(['dojo/request', 'mui/dialog/Confirm', 'mui/dialog/Alert'], function(request, Confirm, Alert){
					var title = '${ lfn:message("return.systemInfo") }';
					var contentHtml = "${ lfn:message('sys-organization:organization.invalidated.comfirm') }";
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
					<html:form action="/sys/organization/sys_org_element_external/sysOrgElementExternalPost.do">
						<div class="propertyGap">${ lfn:message('sys-organization:sysOrgElementExternal.post.info') }</div>
						<table class="muiSimple" cellpadding="0" cellspacing="0">
							<tr>
								<td class="muiTitle">${ lfn:message('sys-organization:sysOrgElementExternal.fdParent') }</td>
								<td>
									<xform:address required="true"
												   propertyId="fdParentId"
												   propertyName="fdParentName"
												   orgType="ORG_TYPE_DEPT"
												   isExternal="true"
												   mobile="true"
												   otherProperties="cateId:'${cateId}'"
												   showStatus="readOnly"
												   subject="${ lfn:message('sys-organization:sysOrgElementExternal.fdParent') }" />
								</td>
							</tr>
							<tr>
								<td class="muiTitle"><bean:message bundle="sys-organization" key="sysOrgElement.fdName"/></td>
								<td>
									<xform:text property="fdName" mobile="true" align="right" required="true" showStatus="edit" validators="maxLength(200) uniqueName checkNameUnique"></xform:text>
								</td>
							</tr>
							<tr>
								<td class="muiTitle">${ lfn:message('sys-organization:sysOrgPost.fdThisLeader') }</td>
								<td>
									<xform:address
											propertyId="fdThisLeaderId"
											propertyName="fdThisLeaderName"
											orgType="ORG_TYPE_PERSON"
											otherProperties="isEco:true,cateId:'${sysOrgPostForm.fdParentId}',noPath:true"
											deptLimit="${sysOrgPostForm.fdParentId}"
											isExternal="true"
											mobile="true"
											subject="${ lfn:message('sys-organization:sysOrgPost.fdThisLeader') }" />
								</td>
							</tr>
							<tr>
								<td class="muiTitle">${ lfn:message('sys-organization:sysOrgElement.person') }</td>
								<td>
									<xform:address
											propertyId="fdPersonIds"
											propertyName="fdPersonNames"
											orgType="ORG_TYPE_PERSON"
											otherProperties="isEco:true"
											mobile="true"
											mulSelect="true"
											subject="${ lfn:message('sys-organization:sysOrgElement.person') }" />
								</td>
							</tr>
							<tr>
								<td class="muiTitle"><bean:message bundle="sys-organization" key="sysOrgPost.fdNo" /></td>
								<td>
									<xform:text property="fdNo" mobile="true" align="right" showStatus="edit"></xform:text>
								</td>
							</tr>
							<tr>
								<td class="muiTitle"><bean:message bundle="sys-organization" key="sysOrgPost.fdOrder" /></td>
								<td>
									<xform:text property="fdOrder" mobile="true" align="right" showStatus="edit"></xform:text>
								</td>
							</tr>
							<tr>
								<td class="muiTitle"><bean:message bundle="sys-organization" key="sysOrgPost.fdMemo" /></td>
								<td>
									<xform:textarea property="fdMemo" mobile="true" align="right" showStatus="edit"></xform:textarea>
								</td>
							</tr>
						</table>

						<html:hidden property="method_GET"/>
						<html:hidden property="fdId"/>
						<html:hidden property="fdParentId"/>
						<input type="hidden" name="cateId" value="${cateId}">
					</html:form>
				</xform:config>
			</div>
			<c:if test="${sysOrgPostForm.method_GET == 'edit'}">
				<kmss:auth requestURL="/sys/organization/sys_org_element_external/sysOrgElementExternalPost.do?method=invalidated&fdId=${sysOrgPostForm.fdId}">
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
		</div>
		<script>
			require([ "mui/form/ajax-form!sysOrgPostForm"]);
		</script>
	</template:replace>
</template:include>
