<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<c:set var="tiny" value="true" scope="request" />
<template:include ref="mobile.view" isNative="true">
	<template:replace name="title">
		${ lfn:message('button.view') }${ lfn:message('sys-organization:sysOrgElement.post') }
	</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/organization/mobile/css/person/person.css">
		<script>
			function edit() {
				window.location.href = '${LUI_ContextPath}/sys/organization/sys_org_element_external/sysOrgElementExternalPost.do?method=edit&fdId=${sysOrgPostForm.fdId}'
			}
		</script>
    </template:replace>
	<template:replace name="content">
		<div data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin,mui/form/_AlignMixin" id="scrollView">
		
			<xform:config showStatus="readOnly">
				<div class="muiSysOrgEcoPersonHeader">
					<div class="muiSysOrgEcoPersonHeaderContainer">
						<div class="personImg">
							<img src="${LUI_ContextPath}/sys/mobile/css/themes/default/images/address-post-external.png">
						</div>
						<div class="fdName">
							${sysOrgPostForm.fdName}
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
										<div class="muiFormEleTitle">${ lfn:message('sys-organization:sysOrgElementExternal.fdParent') }</div>
										${sysOrgPostForm.fdParentName}
									</td>
								</tr>
								<tr>
									<td>
										<div class="muiFormEleTitle">${ lfn:message('sys-organization:sysOrgPost.fdThisLeader') }</div>
										${sysOrgPostForm.fdThisLeaderName}
									</td>
								</tr>
								<tr>
									<td>
										<div class="muiFormEleTitle">${ lfn:message('sys-organization:sysOrgElement.person') }</div>
										${sysOrgPostForm.fdPersonNames}
									</td>
								</tr>
								<tr>
									<td>
										<xform:text property="fdNo" mobile="true" align="right"></xform:text>
									</td>
								</tr>
								<tr>
									<td>
										<xform:text property="fdOrder" mobile="true" align="right"></xform:text>
									</td>
								</tr>
								<tr>
									<td>
										<xform:textarea property="fdMemo" mobile="true" align="right"></xform:textarea>
									</td>
								</tr>
							</table>
						</xform:config>
					</xform:config>
				</div>
			</xform:config>
			<kmss:auth requestURL="/sys/organization/sys_org_element_external/sysOrgElementExternalPost.do?method=edit&fdId=${sysOrgPostForm.fdId}">
				<div class="tableGap"></div>
				<div class="muiSysOrgEcoPersonBtn" onclick="javascript:edit();">
					${ lfn:message('button.edit') }
				</div>
			</kmss:auth>
			
		</div>
	</template:replace>
</template:include>
