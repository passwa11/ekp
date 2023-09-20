<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.edit">
	<template:replace name="title">
		${lfn:message('sys-zone:sysZonePrivate.privacy.setting')}
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar layout="sys.ui.toolbar.float">
			<kmss:auth requestURL="/sys/zone/sys_zone_private_change/sysZonePrivateChange.do?method=editPrivate">
				<ui:button onclick="Com_Submit(document.sysZonePrivateChangeForm,'updatePrivate');" 
						   text="${lfn:message('button.submit') }"></ui:button>
			</kmss:auth>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
		<html:form  action="/sys/zone/sys_zone_private_change/sysZonePrivateChange.do" method="POST">
			<%--
			是否隐藏联系方式（包括电话及邮箱）、汇报链、部门同事或部门岗位信息
			 --%>
			<p class="txttitle">${lfn:message('sys-zone:sysZonePrivate.privacy.setting') }</p>
			<center>
				<table class="tb_normal" width=95%>
					<tr>
						<td width="20%" class="td_normal_title">
							${lfn:message('sys-zone:sysZonePrivate.privacy.modify.person') }
						</td>
						<td width="80%">
							<c:out value="${sysZonePrivateChangeForm.fdNames}"></c:out>
							<html:hidden property="fdIds"/>
							<html:hidden property="fdNames"/>
						</td>
					</tr>
					<tr>
						<td width=20% class="td_normal_title">
							${lfn:message('sys-zone:sysZonePrivate.isContactPrivate')}
						</td>
						<td width="80%">
							<label>
								<html:checkbox property="isContactPrivate" value="1" >
								${lfn:message('sys-zone:sysZonePrivate.hide') }
								</html:checkbox>
							</label>
						</td>
					</tr>
					<tr>
						<td width=20% class="td_normal_title">
							${lfn:message('sys-zone:sysZonePrivate.isDepInfoPrivate')}
						</td>
						<td width="80%">
							<label>
								<html:checkbox property="isDepInfoPrivate" value="1"  >
									${lfn:message('sys-zone:sysZonePrivate.hide') }
								</html:checkbox>
							</label>
						</td>
					</tr>
					<tr>
						<td width=20% class="td_normal_title">
							${lfn:message('sys-zone:sysZonePrivate.isRelationshipPrivate')}
						</td>
						<td width="80%">
							<label>
								<html:checkbox property="isRelationshipPrivate" value="1" >
								${lfn:message('sys-zone:sysZonePrivate.hide') }
								</html:checkbox>
							</label>
						</td>
					</tr>
					<tr>
						<td width=20% class="td_normal_title">
							${lfn:message('sys-zone:sysZonePrivate.isWorkmatePrivate')}
						</td>
						<td width="80%">
							<label>
								<html:checkbox property="isWorkmatePrivate" value="1" >
									${lfn:message('sys-zone:sysZonePrivate.hide') }
								</html:checkbox>
							</label>
						</td>
					</tr>
				</table>
			</center>
		</html:form>
	</template:replace>
</template:include>
