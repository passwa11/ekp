<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.edit">
	<template:replace name="toolbar">
		<ui:toolbar layout="sys.ui.toolbar.float">
			<kmss:auth requestURL="/sys/zone/sys_zone_private_config/sysZonePrivateConfig.do?method=edit">
				<ui:button onclick="Com_Submit(document.sysAppConfigForm,'update');" 
						   text="${lfn:message('button.submit') }"></ui:button>
			</kmss:auth>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
		<html:form  action="/sys/zone/sys_zone_private_config/sysZonePrivateConfig.do" method="POST">
			<%--
			是否隐藏联系方式（包括电话及邮箱）、汇报链、部门同事或部门岗位信息
			 --%>
			<p class="txttitle">${lfn:message('sys-zone:sysZonePrivate.privacy.setting') }</p>
			<center>
				<table class="tb_normal" width=95%>
					<tr>
						<td width=35% class="td_normal_title">
							${lfn:message('sys-zone:sysZonePrivate.isContactPrivate')}
						</td>
						<td width="65%">
							<label>
								<html:checkbox property="value(isContactPrivate)" value="1" >
								${lfn:message('sys-zone:sysZonePrivate.hide') }
								</html:checkbox>
							</label>
						</td>
					</tr>
					<tr>
						<td width=35% class="td_normal_title">
							${lfn:message('sys-zone:sysZonePrivate.isDepInfoPrivate')}
						</td>
						<td width="65%">
							<label>
								<html:checkbox property="value(isDepInfoPrivate)" value="1"  >
									${lfn:message('sys-zone:sysZonePrivate.hide') }
								</html:checkbox>
							</label>
						</td>
					</tr>
					<tr>
						<td width=35% class="td_normal_title">
							${lfn:message('sys-zone:sysZonePrivate.isRelationshipPrivate')}
						</td>
						<td width="65%">
							<label>
								<html:checkbox property="value(isRelationshipPrivate)" value="1" >
								${lfn:message('sys-zone:sysZonePrivate.hide') }
								</html:checkbox>
							</label>
						</td>
					</tr>
					<tr>
						<td width=35% class="td_normal_title">
							${lfn:message('sys-zone:sysZonePrivate.isWorkmatePrivate')}
						</td>
						<td width="65%">
							<label>
								<html:checkbox property="value(isWorkmatePrivate)" value="1" >
									${lfn:message('sys-zone:sysZonePrivate.hide') }
								</html:checkbox>
							</label>
						</td>
					</tr>
					<tr>
						<td width=35% class="td_normal_title">
							${lfn:message('sys-zone:sysZonePrivate.isQrCodePrivate')}
						</td>
						<td width="65%">
							<label>
								<html:checkbox property="value(isQrCodePrivate)" value="1">
									${lfn:message('sys-zone:sysZonePrivate.hide') }
								</html:checkbox>
							</label>
						</td>
					</tr>
				</table>
			</center>
			<input type="hidden" name="modelName" value="com.landray.kmss.kms.knowledge.model.KmsKnowledgeFilterConfig" />
		    <html:hidden property="method_GET"/>
		</html:form>
	</template:replace>
</template:include>
