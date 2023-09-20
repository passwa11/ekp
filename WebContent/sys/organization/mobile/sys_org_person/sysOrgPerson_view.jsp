<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<c:set var="tiny" value="true" scope="request" />
<template:include ref="mobile.view" isNative="true">
	<template:replace name="title">
		${ lfn:message('button.view') }${ lfn:message('sys-organization:sysOrgElement.person') }
	</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/organization/mobile/css/person/person.css">
		<script>
			function edit() {
				window.location.href = '${LUI_ContextPath}/sys/organization/sys_org_element_external/sysOrgElementExternalPerson.do?method=edit&fdId=${sysOrgPersonForm.fdId}'
			}
		</script>
    </template:replace>
	<template:replace name="content">
		<div data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin,mui/form/_AlignMixin" id="scrollView">
		
			<xform:config showStatus="readOnly">
				<div class="muiSysOrgEcoPersonHeader">
					<div class="muiSysOrgEcoPersonHeaderContainer">
						<div class="personImg">
							<img src="${LUI_ContextPath}/sys/person/image.jsp?personId=${sysOrgPersonForm.fdId}&amp;size=m&amp;s_time=1567737721000">
						</div>
						<div class="fdName">
							${sysOrgPersonForm.fdName}
						</div>
						<div class="ecoType">
							${sysOrgEcoName}
						</div>
					</div>
				</div>
				
				<div class="muiSysOrgEcoPersonDetail">
					<xform:config showStatus="readOnly">
						<xform:config orient="vertical" >
							<table class="muiSimple" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										<xform:text property="fdMobileNo" mobile="true" align="right" required="true"></xform:text>
									</td>
								</tr>
								<%--
								<tr>
									<td>
										<xform:text property="fdLoginName" mobile="true" align="right" required="true"></xform:text>
									</td>
								</tr>
								--%>
								<tr>
									<td>
										<xform:text property="fdEmail" mobile="true" align="right" required="true"></xform:text>
									</td>
								</tr>
								<tr>
									<td>
										<div class="muiFormEleTitle">${ lfn:message('sys-organization:sysOrgPerson.fdCanLogin') }</div>
										<sunbor:enumsShow value="${sysOrgPersonForm.fdCanLogin}" enumsType="common_yesno" />
									</td>
								</tr>
								<tr>
									<td>
										<div class="muiFormEleTitle">${ lfn:message('sys-organization:sysOrgElementExternal.fdParent') }</div>
										${sysOrgPersonForm.fdParentName}
									</td>
								</tr>
								<tr>
									<td>
										<div class="muiFormEleTitle">${ lfn:message('sys-organization:sysOrgElement.post') }</div>
										${sysOrgPersonForm.fdPostNames}
									</td>
								</tr>
								<%-- 扩展属性 --%>
								<c:import url="/sys/organization/mobile/sysOrgExternal_common_ext_props_view.jsp" charEncoding="UTF-8">
									<c:param name="formName" value="sysOrgPersonForm"/>
									<c:param name="infoLable" value="${ lfn:message('sys-organization:sysOrgElementExternal.person.prop') }"/>
								</c:import>
							</table>
						</xform:config>
					</xform:config>
				</div>
			</xform:config>
			<kmss:auth requestURL="/sys/organization/sys_org_element_external/sysOrgElementExternalPerson.do?method=edit&fdId=${sysOrgPersonForm.fdId}">
				<div class="tableGap"></div>
				<div class="muiSysOrgEcoPersonBtn" onclick="javascript:edit();">
					${ lfn:message('button.edit') }
				</div>
			</kmss:auth>
			<div class="tableGap"></div>
			
			<div class="sysOrgPersonDDBtn" 
				 data-dojo-type="sys/organization/mobile/js/person/muiOrgPersonDD"
				 data-dojo-props="userDDId:'${userDDId}',corpId:'${corpId}',ekpId:'${ekpId}'"></div>
			
		</div>
	</template:replace>
</template:include>
