<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.organization.model.SysOrgElementExtProp"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<c:set var="tiny" value="true" scope="request" />
<template:include ref="mobile.edit" compatibleMode="true" isNative="true">
	<template:replace name="title">
		<c:if test="${fdOtgType=='1'}">
			<c:if test="${sysOrgDeptForm.method_GET == 'add'}">
				${ lfn:message('button.create') }${ lfn:message('sys-organization:sysOrgElementExternal.dept') }
			</c:if>
			<c:if test="${sysOrgDeptForm.method_GET == 'edit'}">
				${ lfn:message('button.edit') }${ lfn:message('sys-organization:sysOrgElementExternal.dept') }
			</c:if>
		</c:if>
		<c:if test="${fdOtgType!='1'}">
			<c:if test="${sysOrgDeptForm.method_GET == 'add'}">
				${ lfn:message('button.create') }${ lfn:message('sys-organization:sysOrgElementExternal.subDept') }
			</c:if>
			<c:if test="${sysOrgDeptForm.method_GET == 'edit'}">
				${ lfn:message('button.edit') }${ lfn:message('sys-organization:sysOrgElementExternal.subDept') }
			</c:if>
		</c:if>
	</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/organization/mobile/css/dept/dept.css">
		<script>
			function changeRadioValue(obj){
				require([ "mui/form/validate/Validation", "dojo/query"], function(validation, query) {
					var dom = document.getElementById('authCheckBox');
					if(obj.value == '2'){
						dom.style.display = 'block';
						$form('fdRange.fdViewSubType').required(true);
						var subType = query("input[name='_fdRange_fdViewSubType_single'][value='2']:checked");
						if(subType.length > 0) {
							$form('fdRange.fdOtherIds').required(true);
							var dom = document.getElementById('addressOther');
							dom.style.display = 'block';
						}
					} else {
						dom.style.display = 'none';
						var dom = document.getElementById('addressOther');
						dom.style.display = 'none';
						$form('fdRange.fdOtherIds').required(false);
						$form('fdRange.fdViewSubType').required(false);
					}
				});
			}
			function changeReviewHideType(obj){
				require([ "mui/form/validate/Validation", "dojo/query"], function(validation, query) {
					var dom = document.getElementById('otherReviewHide');
					if(obj.value == '1'){
						dom.style.display = 'block';
						$form('fdHideRange.fdOtherIds').required(true);
						$form('fdHideRange.fdViewSubType').required(true);
					} else {
						dom.style.display = 'none';
						$form('fdHideRange.fdOtherIds').required(false);
						$form('fdHideRange.fdViewSubType').required(false);
					}
				});
			}
			function invalidated(){
				var url = '${LUI_ContextPath}/sys/organization/sys_org_element_external/sysOrgElementExternalDept.do?method=invalidated&fdId=${sysOrgDeptForm.fdId}';
				require(['dojo/request', 'mui/dialog/Confirm', 'mui/dialog/Alert'], function(request, Confirm, Alert){
					var title = '${ lfn:message("return.systemInfo") }';
					var contentHtml = "${ lfn:message('sys-organization:sysOrgElementExternal.comfirmDisable') }";
					var callback = function(bool,dialog){
						if(bool){
							request.get(url, {
								headers: {'Accept': 'application/json'},
								handleAs: 'json'
							}).then(function(data) {
								var callback = function(){
									window.location.href = '${LUI_ContextPath}/sys/organization/mobile/sys_org_eco/list.jsp';
								}
								Alert('${ lfn:message("return.optSuccess") }', '', callback);
							}, function(data){
								Alert("${ lfn:message('sys-organization:sysOrgElementExternal.disable.err') }", '', function(){});
							});
						}
					}
					Confirm(contentHtml, null,callback);
				});
			}
			function save(){
				require(["dijit/registry"], function(registry) {
					var validateObj = registry.byId('scrollView');
					if (validateObj.validate()) {
						var method = '${sysOrgDeptForm.method_GET}';
						if(window.checkSubmit() && window.addressVaildation()) {
							if(method == 'add') {
								Com_Submit(document.forms[0], "save");
							} else {
								// update
								var updateUrl = '${LUI_ContextPath}/sys/organization/sys_org_element_external/sysOrgElementExternalDept.do?method=update&fdId=${sysOrgDeptForm.fdId}&List_Selected=${sysOrgDeptForm.fdId}';
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

			require([ "mui/form/validate/Validation", "dojo/query"], function(validation, query) {
				var validation = new validation();
				validation.addValidator("uniqueName", "${ lfn:message('sys-organization:sysOrgElementExternal.dept.repeat') }", uniqueName);
				validation.addValidator("checkNameUnique", "${ lfn:message('sys-organization:sysOrgElementExternal.disable.dept.repeat') }", checkNameUnique);
				validation.addValidator("addressVaildation", "${ lfn:message('sys-organization:sysOrgElementExternal.address.empty') }", window.addressVaildation);
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
				window.checkSubmit = function () {
					return uniqueName() && checkNameUnique();
				}
				window.addressVaildation = function(){
					var arrViewType = query("input[name=fdRange_fdViewType_group]:checked");
					var arrSubType = query("input[name='_fdRange_fdViewSubType_single'][value='2']:checked");
					if(arrViewType.length > 0) {
						if(arrViewType[0].value == '2') {
							if(arrSubType.length > 0) {
								var addressId = query("input[name='fdRange.fdOtherIds']");
								if(addressId.length == 0) {
									return false;
								} else {
									var value = addressId[0].value;
									if(!value || value == '')
										return false;
								}
							}
						}
					}
					return true;
				}
				window.changeSubValue = function(){
					var dom = document.getElementById('addressOther');
					var arrSubType = query("input[name='_fdRange_fdViewSubType_single'][value='2']:checked");
					if(arrSubType.length > 0){
						$form('fdRange.fdOtherIds').required(true);
						dom.style.display = 'block';
					} else {
						$form('fdRange.fdOtherIds').required(false);
						dom.style.display = 'none';
					}
				}
			});
		</script>
	</template:replace>
	<template:replace name="content">
		<div data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin,mui/form/_AlignMixin" id="scrollView">
			<div class="pageBox">
				<xform:config showStatus="edit">
					<html:form action="/sys/organization/sys_org_element_external/sysOrgElementExternalDept.do">
						<html:hidden property="method_GET"/>
						<html:hidden property="fdId"/>
						<html:hidden property="fdRange.fdId"/>
						<html:hidden property="fdHideRange.fdId"/>
						<input type="hidden" name="cateId" value="${cateId}">
						<div class="propertyGap">${ lfn:message('sys-organization:sysOrgElementExternal.dept.info') }</div>
						<table class="muiSimple" cellpadding="0" cellspacing="0">
							<tr>
								<td class="muiTitle">${ lfn:message('sys-organization:sysOrgElementExternal.parent') }</td>
								<td>
									<xform:address required="true"
												   propertyId="fdParentId"
												   propertyName="fdParentName"
												   orgType="ORG_TYPE_ORGORDEPT"
												   otherProperties="isEco:true,exceptValue:'${sysOrgDeptForm.fdId}',cateId:'${cateId}'"
												   mobile="true"
												   subject="${ lfn:message('sys-organization:sysOrgElementExternal.parent') }" />
								</td>
							</tr>
							<c:if test="${fdOtgType=='1'}">
								<input type="hidden" name="fdParentId" value="${sysOrgDeptForm.fdParentId}"/>
								<tr>
									<td class="muiTitle">${ lfn:message('sys-organization:sysOrgElementExternal.fdName') }</td>
									<td>
										<xform:text property="fdName" mobile="true" subject="${ lfn:message('sys-organization:sysOrgElementExternal.fdName') }" required="true" validators="maxLength(100) uniqueName checkNameUnique"></xform:text>
									</td>
								</tr>
							</c:if>
							<c:if test="${fdOtgType!='1'}">
								<tr>
									<td class="muiTitle">${ lfn:message('sys-organization:sysOrgElementExternal.subDept.fdName') }</td>
									<td>
										<xform:text property="fdName" mobile="true" subject="${ lfn:message('sys-organization:sysOrgElementExternal.subDept.fdName') }" required="true" validators="maxLength(100) uniqueName checkNameUnique"></xform:text>
									</td>
								</tr>
							</c:if>
							<tr>
								<td class="muiTitle">${ lfn:message('sys-organization:sysOrgOrg.fdNo') }</td>
								<td style="text-align: right">
									<xform:text property="fdNo" mobile="true"></xform:text>
								</td>
							</tr>
							<tr>
								<td class="muiTitle">${ lfn:message('sys-organization:sysOrgOrg.fdOrder') }</td>
								<td style="text-align: right">
									<xform:text property="fdOrder" mobile="true"  validators="number"></xform:text>
								</td>
							</tr>
							<tr>
								<td class="muiTitle">${ lfn:message('sys-organization:sysOrgElementExternal.authElementAdmin') }</td>
								<td style="text-align: right">
									<xform:address required="false"
												   propertyId="authElementAdminIds"
												   propertyName="authElementAdminNames"
												   orgType='ORG_TYPE_PERSON'
												   mobile="true"
												   subject="${ lfn:message('sys-organization:sysOrgElementExternal.authElementAdmin') }"
												   mulSelect="true"
												   align="right" />
								</td>
							</tr>
							<c:if test="${sysOrgDeptForm.method_GET == 'edit'}">
								<tr>
									<td class="muiTitle">${ lfn:message('sys-organization:sysOrgElementExternal.ding.url') }</td>
									<td style="text-align: right">
										<xform:textarea property="fdRange.fdInviteUrl"
														subject="${ lfn:message('sys-organization:sysOrgElementExternal.ding.url') }"
														mobile="true"
														validators="maxLength(1000)"></xform:textarea>
									</td>
								</tr>
							</c:if>
						</table>

						<%-- 扩展属性 --%>
						<c:import url="/sys/organization/mobile/sysOrgExternal_common_ext_props_edit.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="sysOrgDeptForm"/>
							<c:param name="infoLable" value="${ lfn:message('sys-organization:sysOrgElementExternal.dept.prop') }"/>
						</c:import>

						<div class="tableGap"></div>
						<input type="hidden" name="fdRange.fdIsOpenLimit" value="true">
						<table class="muiSimple" cellpadding="0" cellspacing="0">
							<tr>
								<td class="muiTitle">
										${ lfn:message('sys-organization:sysOrgElementExternal.view.auth') }
								</td>
							</tr>
						</table>
						<table class="muiSimple" cellpadding="0" cellspacing="0">
							<tr>
								<td>
									<xform:radio property="fdRange.fdViewType" required="true"
												 mobile="true" mobileRenderType="normal"
												 alignment="V" onValueChange="changeRadioValue(this);">
										<xform:simpleDataSource value="0">
											<bean:message bundle="sys-organization" key="sysOrgEco.view.type.myself"/>
										</xform:simpleDataSource>
										<xform:simpleDataSource value="1">
											<bean:message bundle="sys-organization" key="sysOrgEco.view.type.inner"/>
										</xform:simpleDataSource>
										<xform:simpleDataSource value="2">
											<bean:message bundle="sys-organization" key="sysOrgEco.view.type.special"/>
										</xform:simpleDataSource>
									</xform:radio>
									<div id="authCheckBox" >
										<xform:checkbox property="fdRange.fdViewSubType" mobile="true" subject="${ lfn:message('sys-organization:sysOrgEco.view.type.special') }"
														mobileRenderType="normal" onValueChange="window.changeSubValue();">
											<xform:enumsDataSource enumsType="sys_org_eco_sub_view" />
										</xform:checkbox>
									</div>
									<div id="addressOther" style="margin-top:2rem;display:none;">
										<xform:address propertyId="fdRange.fdOtherIds"
													   propertyName="fdRange.fdOtherNames"
													   orgType='ORG_TYPE_ORG|ORG_TYPE_DEPT|ORG_TYPE_PERSON'
													   mobile="true"
													   mulSelect="true"
													   align="right"
													   validators="addressVaildation"
													   showStatus="edit">
										</xform:address>
									</div>
								</td>
							</tr>
						</table>

						<div class="tableGap"></div>
						<table class="muiSimple" cellpadding="0" cellspacing="0">
							<tr>
								<td class="muiTitle">
									${ lfn:message('sys-organization:sysOrgEco.hide.range3') }
								</td>
								<td class="muiTitle">
									<div data-dojo-type="mui/form/Switch" data-dojo-mixins="sys/organization/mobile/js/eco/HideRangeSwitch" data-dojo-props="realValue:'${sysOrgDeptForm.fdHideRange.fdIsOpenLimit}',showStatus:'edit',orient:'horizontal',align:'right',property:'fdHideRange.fdIsOpenLimit',leftLabel:'',rightLabel:''"></div>
								</td>
							</tr>
						</table>
						<table id="fdHideRangeTable" class="muiSimple" cellpadding="0" cellspacing="0">
							<tr>
								<td>
									<xform:radio property="fdHideRange.fdViewType" required="true"
												 mobile="true" mobileRenderType="normal"
												 alignment="V" onValueChange="changeReviewHideType(this);">
										<xform:simpleDataSource value="0"><bean:message bundle="sys-organization" key="sysOrgEco.hide.type.all.eco"/></xform:simpleDataSource>
										<xform:simpleDataSource value="1"><bean:message bundle="sys-organization" key="sysOrgEco.hide.type.special.eco"/></xform:simpleDataSource>
									</xform:radio>
									<div id="otherReviewHide" style="margin-top:2rem;display:none;">
										<xform:address propertyId="fdHideRange.fdOtherIds"
													   propertyName="fdHideRange.fdOtherNames"
													   orgType='ORG_TYPE_ORG|ORG_TYPE_DEPT|ORG_TYPE_PERSON'
													   mobile="true"
													   mulSelect="true"
													   align="right"
													   validators="addressVaildation"
													   showStatus="edit">
										</xform:address>
									</div>
								</td>
							</tr>
						</table>
						<div class="sysOrgDeptRangeGap"></div>
					</html:form>
				</xform:config>
			</div>
			<c:if test="${sysOrgDeptForm.method_GET == 'edit'}">
				<kmss:auth requestURL="/sys/organization/sys_org_element_external/sysOrgElementExternalDept.do?method=invalidated&fdId=${sysOrgDeptForm.fdId}">
					<div class="tableGap"></div>
					<div class="sysOrgDept invalidated" onclick="invalidated();">
							${ lfn:message('sys-organization:sysOrgEco.available.close') }
					</div>
					<div class="invalidatedNone"></div>
				</kmss:auth>
			</c:if>
			<div class=	"sysOrgDept save" onclick="save();">
				<div>
						${ lfn:message('button.save') }
				</div>
			</div>
		</div>
		<script>
			require(["dojo/query", "dojo/ready"], function(query, ready) {
				ready(function(){
					var viewType = '${sysOrgDeptForm.fdRange.fdViewType}';
					if(viewType == '2') {
						var dom = document.getElementById('authCheckBox');
						dom.style.display = 'block';
						var subType = '${sysOrgDeptForm.fdRange.fdViewSubType}';
						if(subType.indexOf('2') > -1) {
							var dom = document.getElementById('addressOther');
							$form('fdRange.fdOtherIds').required(true);
							$form('fdRange.fdViewSubType').required(true);
							dom.style.display = 'block';
						}
					}

					var viewType = '${sysOrgDeptForm.fdHideRange.fdViewType}';
					if(viewType == '1') {
						var dom = document.getElementById('otherReviewHide');
						$form('fdHideRange.fdOtherIds').required(true);
						$form('fdHideRange.fdViewSubType').required(true);
						dom.style.display = 'block';
					}
				})
			});
		</script>
		<script>
			require([ "mui/form/ajax-form!sysOrgDeptForm"]);
		</script>
	</template:replace>
</template:include>
