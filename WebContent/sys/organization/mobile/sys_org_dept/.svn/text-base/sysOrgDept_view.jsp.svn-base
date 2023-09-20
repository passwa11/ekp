<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="tiny" value="true" scope="request" />
<template:include ref="mobile.view" isNative="true">
	<template:replace name="title">
		${ lfn:message('button.view') }${ lfn:message('sys-organization:sysOrgElementExternal.dept') }
	</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/organization/mobile/css/dept/dept.css">
		<script>
			function edit(){
				var fdId = '${sysOrgDeptForm.fdId}';
				if(fdId && fdId != '')
					window.location.href = '${LUI_ContextPath}/sys/organization/sys_org_element_external/sysOrgElementExternalDept.do?method=edit&fdId=' + fdId;
			}
		</script>
	</template:replace>
	<template:replace name="content">
		<div data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin,mui/form/_AlignMixin" id="scrollView">
			<div class="pageBox">
				<div class="muiSysOrgDeptTitle">
					<div class="fdName">${sysOrgDeptForm.fdName}</div>
					<div class="tag">${sysOrgDeptForm.fdParentName}</div>
				</div>
				<xform:config showStatus="readOnly">
					<xform:config orient="vertical">

						<div class="tableGap"></div>
						<table class="muiSimple" cellpadding="0" cellspacing="0">
							<tr>
								<td>
									<xform:address
											propertyId="authElementAdminIds"
											propertyName="authElementAdminNames"
											orgType='ORG_TYPE_ALL'
											mobile="true"
											subject="${ lfn:message('sys-organization:sysOrgElementExternal.authElementAdmin') }" />
								</td>
							</tr>

								<%-- 扩展属性 --%>
							<c:import url="/sys/organization/mobile/sysOrgExternal_common_ext_props_view.jsp" charEncoding="UTF-8">
								<c:param name="formName" value="sysOrgDeptForm"/>
								<c:param name="infoLable" value="${ lfn:message('sys-organization:sysOrgElementExternal.dept.prop') }"/>
							</c:import>

							<tr>
								<td>
									<xform:radio property="fdRange.fdViewType"
												 subject="${ lfn:message('sys-organization:sysOrgElementExternal.view.auth') }"
												 value="${sysOrgDeptForm.fdRange.fdViewType}"
												 mobile="true" alignment="V">
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
										<xform:checkbox property="fdRange.fdViewSubType"
														value="${sysOrgDeptForm.fdRange.fdViewSubType}"
														mobile="true"
														isArrayValue="false" alignment="V">
											<xform:enumsDataSource enumsType="sys_org_eco_sub_view" />
										</xform:checkbox>
									</div>
									<div id="addressOther" style="margin-top:2rem;display:none;">
										<xform:address propertyId="fdRange.fdOtherIds"
													   propertyName="fdRange.fdOtherNames"
													   orgType='ORG_TYPE_ORG|ORG_TYPE_DEPT|ORG_TYPE_PERSON'
													   mobile="true"
													   align="right">
										</xform:address>
									</div>
								</td>
							</tr>
							<c:if test="${'true' eq sysOrgDeptForm.fdHideRange.fdIsOpenLimit}">
							<tr>
								<td>
									<xform:radio property="fdHideRange.fdViewType"
												 subject="${ lfn:message('sys-organization:sysOrgEco.hide.range2') }"
												 value="${sysOrgDeptForm.fdHideRange.fdViewType}"
												 mobile="true" alignment="V">
										<xform:simpleDataSource value="0"><bean:message bundle="sys-organization" key="sysOrgEco.hide.type.all.in"/></xform:simpleDataSource>
										<xform:simpleDataSource value="1"><bean:message bundle="sys-organization" key="sysOrgEco.hide.type.special.in"/></xform:simpleDataSource>
									</xform:radio>
									<div id="otherReviewHide" style="margin-top:2rem;display:none;">
										<xform:address propertyId="fdHideRange.fdOtherIds"
													   propertyName="fdHideRange.fdOtherNames"
													   orgType='ORG_TYPE_ORG|ORG_TYPE_DEPT|ORG_TYPE_PERSON'
													   mobile="true"
													   align="right">
										</xform:address>
									</div>
								</td>
							</tr>
							</c:if>
						</table>
					</xform:config>
				</xform:config>

			</div>
		</div>
		<div class="tableGap"></div>
		<kmss:auth requestURL="/sys/organization/sys_org_element_external/sysOrgElementExternalDept.do?method=edit&fdId=${sysOrgDeptForm.fdId}" requestMethod="GET">
			<div class="editBtn" onclick="edit();">
					${ lfn:message('button.edit') }
			</div>
		</kmss:auth>
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
							dom.style.display = 'block';
						}
					}

					var viewType = '${sysOrgDeptForm.fdHideRange.fdViewType}';
					if(viewType == '1') {
						var dom = document.getElementById('otherReviewHide');
						dom.style.display = 'block';
					}
				})
			});
		</script>
	</template:replace>
</template:include>
