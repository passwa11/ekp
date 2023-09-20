<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view">
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-attend:module.sys.attend') }"></c:out>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="5">
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav"> 
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home">
			</ui:menu-item>	
			<ui:menu-item text="${ lfn:message('sys-attend:module.sys.attend') }">
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message('sys-attend:table.sysAttendMain') }">
			</ui:menu-item>
		</ui:menu>
	</template:replace>
	<template:replace name="content">
		<div class='lui_form_title_frame'>
			<div class='lui_form_subject'>
				${ lfn:message('sys-attend:table.sysAttendMain') }
			</div>
			<div class='lui_form_baseinfo'>
			</div>
		</div>
		<div class="lui_form_content_frame">
			<table class="tb_normal" width=100%>
				<tr>
					<td class="td_normal_title" width=15%>
						${ lfn:message('sys-attend:sysAttendCategory.attend.fdName') }
					</td>
					<%-- 考勤组名 --%>
					<td width="85%" colspan="3">
						<c:out value="${sysAttendCategory.fdName }" />
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-attend" key="sysAttendMain.docCreator"/>
					</td>
					<%-- 签到人 --%>
					<td width="35%">
						<c:out value="${docCreator.fdName}" />
					</td>
					<td class="td_normal_title" width=15%>
						${ lfn:message('sys-attend:sysAttendMain.export.dept') }
					</td>
					<%-- 部门 --%>
					<td width="35%">
						<c:out value="${docCreator.fdParentsName}" />
					</td>
				</tr>
				<tr>
					<c:set var="hasLocation" value="${not empty sysAttendMainBakForm.fdWifiName || not empty sysAttendMainBakForm.fdLocation}"></c:set>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-attend" key="sysAttendMain.docCreateTime"/>
					</td>
					<%-- 打卡时间 --%>
					<td <c:if test="${!hasLocation }">colspan="3"</c:if>>
						<c:out value="${sysAttendMainBakForm.docCreateTime }" />
					</td>
					<%-- 打卡方式 --%>
					<c:if test="${hasLocation }">
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-attend" key="sysAttendCategoryRule.fdMode"/>
					</td>
					<td width="35%">
						<c:choose>
						    <c:when test="${not empty sysAttendMain.fdAppName}">
                                ${ lfn:message('sys-attend:sysAttendMain.export.fdSignType.mechine') }
                            </c:when>
							<c:when test="${not empty sysAttendMainBakForm.fdWifiName}">
								${ lfn:message('sys-attend:sysAttendMain.export.fdSignType.wifi') }
							</c:when>
							<c:when test="${not empty sysAttendMainBakForm.fdLocation}">
								${ lfn:message('sys-attend:sysAttendMain.export.fdSignType.map') }
							</c:when>
						</c:choose>
					</td>
					</c:if>
				</tr>
				<%-- 打卡地点 --%>
				<c:choose>
					<c:when test="${not empty sysAttendMainBakForm.fdWifiName }">
						<tr>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="sys-attend" key="sysAttendMain.fdWifiName"/>
							</td>
							<td width="35%">
								${sysAttendMainBakForm.fdWifiName}
							</td>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="sys-attend" key="sysAttendMain.fdWifiMacIp"/>
							</td>
							<td width="35%">
								${sysAttendMainBakForm.fdWifiMacIp}
							</td>
						</tr>
					</c:when>
					<c:when test="${not empty sysAttendMainBakForm.fdLocation }">
						<tr>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="sys-attend" key="sysAttendMain.fdLocation"/>
							</td>
							<td colspan="3">
								<c:choose>
                                    <c:when test="${sysAttendMain.fdAppName=='dingding'}">
                                        ${sysAttendMainForm.fdLocation}
                                    </c:when>
                                    <c:otherwise>
                                        <%@ taglib uri="/WEB-INF/KmssConfig/sys/attend/map.tld" prefix="map"%>
                                        <c:set var="fdLocationCoordinate" value="${sysAttendMainForm.fdLat}${','}${sysAttendMainForm.fdLng}"/>
                                        <map:location propertyName="fdLocation" nameValue="${sysAttendMainForm.fdLocation }"
                                            propertyCoordinate="fdLocationCoordinate" coordinateValue="${fdLocationCoordinate }" 
                                            showStatus="view"></map:location>
                                    </c:otherwise>
                                </c:choose>
							</td>
						</tr>
					</c:when>
					<c:otherwise>
					</c:otherwise>
				</c:choose>
				<%-- 打卡状态 --%>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-attend" key="sysAttendMain.fdStatus"/>
					</td>
					<td width="85%" colspan="3">
						<c:choose>
							<c:when test="${(sysAttendMainBakForm.fdStatus=='0' || sysAttendMainBakForm.fdStatus=='2' || sysAttendMainBakForm.fdStatus=='3') && sysAttendMainBakForm.fdState =='2'}">
								${ lfn:message('sys-attend:sysAttendMain.fdStatus.ok') }
							</c:when>
							<c:when test="${sysAttendMainBakForm.fdStatus==1 && sysAttendMainBakForm.fdOutside}">
								${ lfn:message('sys-attend:sysAttendMain.outside')}
							</c:when>
							<c:otherwise>
								<sunbor:enumsShow value="${sysAttendMainBakForm.fdStatus}" enumsType="sysAttendMain_fdStatus" />
							</c:otherwise>
						</c:choose>
						<c:if test="${sysAttendMainBakForm.fdStatus=='5' && not empty sysAttendMainBakForm.fdOffType && not empty fdOffTypeText}">
							（${fdOffTypeText }）
						</c:if>
						<c:if test="${sysAttendMainBakForm.fdStatus=='4' && not empty fdBussiness.docUrl}">
							<a href="${LUI_ContextPath }${fdBussiness.docUrl}" target="_blank" style="color: red">${ lfn:message('sys-attend:sysAttendMain.business.seeLBPM') }</a>
						</c:if>
						<c:if test="${sysAttendMainBakForm.fdStatus=='5' && not empty fdBussiness.docUrl}">
							<a href="${LUI_ContextPath }${fdBussiness.docUrl}" target="_blank" style="color: red">${ lfn:message('sys-attend:sysAttendMain.leave.seeLBPM') }</a>
						</c:if>
						<c:if test="${sysAttendMainBakForm.fdStatus=='6' && not empty fdBussiness.docUrl}">
							<a href="${LUI_ContextPath }${fdBussiness.docUrl}" target="_blank" style="color: red">${ lfn:message('sys-attend:sysAttendMain.outgoing.seeLBPM') }</a>
						</c:if>
					</td>
				</tr>
				<%-- 备注 --%>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-attend" key="sysAttendMain.fdDesc"/>
					</td>
					<td width="85%" colspan="3">
						<xform:textarea property="fdDesc" style="width:85%" showStatus="view" />
					</td>
				</tr>
				<%-- 图片 --%>
				<%-- <tr>
					<td class="td_normal_title" width=15%>
						${ lfn:message('sys-attend:sysAttendMainExc.fdDesc.picture') }
					</td>
					<td width="85%" colspan="3">
						<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
							<c:param name="formBeanName" value="sysAttendMainBakForm" />
							<c:param name="fdKey" value="attachment"/>
							<c:param name="fdModelName" value="com.landray.kmss.sys.attend.model.SysAttendMainBak"></c:param>
							<c:param name="fdModelId" value="${sysAttendMainBakForm.fdId }"></c:param>
						</c:import>
					</td>
				</tr> --%>
				<%-- 设备信息 --%>
				<tr>
					<td class="td_normal_title" width=15%>
						${ lfn:message('sys-attend:sysAttendMain.export.fdDeviceInfo') }
					</td>
					<td width="85%" colspan="3">
						${sysAttendMainBakForm.fdDeviceInfo }
					</td>
				</tr>
				<%-- 修改打卡记录 --%>
				<c:if test="${sysAttendMainBakForm.docAlterorId!=null || sysAttendMainBakForm.docAlterTime!=null || sysAttendMainBakForm.fdAlterRecord!=null}">
				<tr>
					<td class="td_normal_title" width=15%>
						${ lfn:message('sys-attend:sysAttendMain.docAlteror') }
					</td>
					<td width="35%">
						${docAlteror.fdName }
					</td>
					<td class="td_normal_title" width=15%>
						${ lfn:message('sys-attend:sysAttendMain.docAlterTime') }
					</td>
					<td width="35%">
						${sysAttendMainBakForm.docAlterTime }
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						${ lfn:message('sys-attend:sysAttendMain.fdAlterRecord') }
					</td>
					<td colspan="3">
						${sysAttendMainBakForm.fdAlterRecord }
					</td>
				</tr>
				</c:if>
			</table> 
		</div>
		<script>
		</script>
	</template:replace>
</template:include>