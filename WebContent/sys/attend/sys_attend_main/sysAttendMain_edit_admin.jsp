<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit">
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-attend:module.sys.attend') }"></c:out>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
			<ui:button text="${ lfn:message('button.update') }" onclick="Com_Submit(document.forms['sysAttendMainForm'], 'updateByAdmin')"></ui:button>
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
		<html:form action="/sys/attend/sys_attend_main/sysAttendMain.do?method=save">
			<html:hidden property="fdId" />
			<html:hidden property="method_GET" />
			<html:hidden property="fdOldStatus" value="${sysAttendMainForm.fdStatus }"/>
			<html:hidden property="fdState" />
			<html:hidden property="fdOutside" />
			
			<table class="tb_normal" width=100%>
				<tr>
					<td class="td_normal_title" width=15%>
						<c:if test="${sysAttendCategory.fdType eq 1}">
							${ lfn:message('sys-attend:sysAttendCategory.attend.fdName') }
						</c:if>
						<c:if test="${sysAttendCategory.fdType eq 2}">
							${ lfn:message('sys-attend:sysAttendCategory.custom.fdName') }
						</c:if>
					</td>
					<td width="85%" colspan="3">
						<c:out value="${sysAttendMainForm.fdCategoryName }" />
					</td>
				</tr>
				<c:if test="${not empty sysAttendCategory.fdAppName }">
				<tr>
					<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-attend" key="sysAttendCategory.fdAppName"/>
					</td>
					<td width="85%" colspan="3">
						<c:out value="${sysAttendCategory.fdAppName }" />
					</td>
				</tr>
				</c:if>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-attend" key="sysAttendMain.docCreator"/>
					</td>
					<td width="35%">
						<c:out value="${sysAttendMainForm.docCreatorName}" />
					</td>
					<td class="td_normal_title" width=15%>
						${ lfn:message('sys-attend:sysAttendMain.export.dept') }
					</td>
					<td width="35%">
						<c:out value="${sysAttendMainForm.docCreatorDept}" />
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-attend" key="sysAttendMain.docCreateTime"/>
					</td>
					<td width="35%">
						<c:out value="${sysAttendMainForm.docCreateTime }" />
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-attend" key="sysAttendMain.fdLocation"/>
					</td>
					<td width="35%">
						<c:if test="${not empty sysAttendMainForm.fdLocation }">
							<%@ taglib uri="/WEB-INF/KmssConfig/sys/attend/map.tld" prefix="map"%>
							<c:set var="fdLocationCoordinate" value="${sysAttendMainForm.fdLat}${','}${sysAttendMainForm.fdLng}"/>
							<map:location propertyName="fdLocation" nameValue="${sysAttendMainForm.fdLocation }"
								propertyCoordinate="fdLocationCoordinate" coordinateValue="${fdLocationCoordinate }" 
								showStatus="view"></map:location>
						</c:if>
						
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-attend" key="sysAttendMain.fdStatus"/>
					</td>
					<td width="85%" colspan="3">
						<c:if test="${sysAttendCategory.fdType eq 1 }">
							<c:if test="${sysAttendMainForm.fdState !='2' && (empty sysAttendMainExcForm || sysAttendMainExcForm.fdStatus!=2)}">
								<c:set var="canEdit" value="true"></c:set>
							</c:if>
							<xform:select property="fdStatus" showPleaseSelect="false" showStatus="${canEdit ? 'edit' :'readOnly' }" required="true" value="${__fdStatus }" style="width: 100px" onValueChange="changeFdOffType" subject="${ lfn:message('sys-attend:sysAttendMain.fdStatus') }">
								<xform:simpleDataSource value="1">${ lfn:message('sys-attend:sysAttendMain.fdStatus.ok') }</xform:simpleDataSource>
								<xform:simpleDataSource value="11">${ lfn:message('sys-attend:sysAttendMain.outside') }</xform:simpleDataSource>
								<xform:simpleDataSource value="0">${ lfn:message('sys-attend:sysAttendMain.fdStatus.unSign') }</xform:simpleDataSource>
								<xform:simpleDataSource value="2">${ lfn:message('sys-attend:sysAttendMain.fdStatus.late') }</xform:simpleDataSource>
								<xform:simpleDataSource value="3">${ lfn:message('sys-attend:sysAttendMain.fdStatus.left') }</xform:simpleDataSource>
								<%-- <xform:simpleDataSource value="4">${ lfn:message('sys-attend:sysAttendMain.fdStatus.business') }</xform:simpleDataSource>
								<xform:simpleDataSource value="5">${ lfn:message('sys-attend:sysAttendMain.fdStatus.askforleave') }</xform:simpleDataSource>
								<xform:simpleDataSource value="6">${ lfn:message('sys-attend:sysAttendMain.fdStatus.outgoing') }</xform:simpleDataSource> --%>
							</xform:select>
							
							<div id="offTypeDiv" style="display: inline-block;margin-left: 20px">
								${ lfn:message('sys-attend:sysAttendConfig.fdOffType') }：
								<xform:select property="fdOffType" showPleaseSelect="false" showStatus="${canEdit ? 'edit' :'readOnly' }" style="width: 100px">
									<xform:simpleDataSource value="">缺省</xform:simpleDataSource>
									<c:forEach items="${leaveRuleList }" var="item">
										<xform:simpleDataSource value="${item.fdSerialNo }">${item.fdName }</xform:simpleDataSource>
									</c:forEach>
								</xform:select>
							</div>
						</c:if>
						<c:if test="${sysAttendCategory.fdType eq 2 }">
							<sunbor:enumsShow value="${sysAttendMainForm.fdStatus}" enumsType="sysAttendMain_fdStatus" />
						</c:if>
					</td>
				</tr>

				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-attend" key="sysAttendMain.fdDesc"/>
					</td>
					<td width="85%" colspan="3">
						<xform:textarea property="fdDesc" style="width:85%" showStatus="view" />
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						${ lfn:message('sys-attend:sysAttendMainExc.fdDesc.picture') }
					</td>
					<td width="85%" colspan="3">
						<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
							<c:param name="formBeanName" value="sysAttendMainForm" />
							<c:param name="fdKey" value="attachment"/>
							<c:param name="fdModelName" value="com.landray.kmss.sys.attend.model.SysAttendMain"></c:param>
							<c:param name="fdModelId" value="${sysAttendMainForm.fdId }"></c:param>
						</c:import>
					</td>
				</tr>
				
				<tr>
					<td class="td_normal_title" width=15%>
						${ lfn:message('sys-attend:sysAttendMain.export.fdDeviceInfo') }
					</td>
					<td width="85%" colspan="3">
						${sysAttendMainForm.fdDeviceInfo }
					</td>
				</tr>
				
				<%-- 异常处理 --%>
				<c:if test="${not empty sysAttendMainExcForm}">
					<tr>
						<td class="td_normal_title" width=15%>${ lfn:message('sys-attend:sysAttendMainExc.fdDesc.setting') }</td>
						<td colspan="3">
							<xform:textarea value="${sysAttendMainExcForm.fdDesc}" showStatus="view" property="fdDesc"></xform:textarea>
							
							<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
								<c:param name="formBeanName" value="sysAttendMainExcForm" />
								<c:param name="fdKey" value="attachment"/>
							</c:import>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>${ lfn:message('sys-attend:sysAttendMainExc.fdStatus') }</td>
						<td>
							【<sunbor:enumsShow value="${sysAttendMainExcForm.docStatus}" enumsType="sysAttendMainExc_docStatus" />】
						</td>
						<td class="td_normal_title" width=15%>${ lfn:message('sys-attend:sysAttendMainExc.fdHandler') }</td>
						<td>
							<kmss:showWfPropertyValues idValue="${sysAttendMainExcForm.fdId}" propertyName="handlerName" />
						</td>
					</tr>
					<c:if test="${sysAttendMainExcForm.fdStatus==1  }">
					<tr>
						<td class="td_normal_title" width=15%>${ lfn:message('sys-attend:sysAttendMainExc.docCreateTime1') }</td>
						<td colspan="3">
							<c:out value="${sysAttendMainExcForm.docCreateTime}" />
						</td>
					</tr>
					</c:if>
					<c:if test="${sysAttendMainExcForm.fdStatus!=1  }">
					<tr>
						<td class="td_normal_title" width=15%>${ lfn:message('sys-attend:sysAttendMainExc.docCreateTime1') }</td>
						<td>
							<c:out value="${sysAttendMainExcForm.docCreateTime}" />
						</td>
						<td class="td_normal_title" width=15%>${ lfn:message('sys-attend:sysAttendMainExc.docHandleTime') }</td>
						<td>
							<c:out value="${sysAttendMainExcForm.docHandleTime}" />
						</td>
					</tr>
					</c:if>
					<tr>
						<td class="td_normal_title" width=15%>${ lfn:message('sys-attend:sysAttendMainExc.exception') }</td>
						<td colspan="3">
							<a href="${LUI_ContextPath }/sys/attend/sys_attend_main_exc/sysAttendMainExc.do?method=view&fdId=${sysAttendMainExcForm.fdId}" target="_blank" class="com_btn_link">${ lfn:message('sys-attend:sysAttendMain.viewException') }</a>
						</td>
					</tr>
				</c:if>
				
			</table>
			</html:form>
		</div>
		<c:if test="${not empty sysAttendMainExcForm }">
			<ui:tabpage expand="false">
				<c:import url="/sys/lbpmservice/import/sysLbpmProcess_view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="sysAttendMainExcForm" />
					<c:param name="fdKey" value="attendMainExc" />
				</c:import>
			</ui:tabpage>
		</c:if> 
		<script>
			LUI.ready(function(){
				changeFdOffType();
			});
			
			window.changeFdOffType = function() {
				if($('[name="fdStatus"]').val() == '5') {
					$('#offTypeDiv').show();
					$('select[name="fdOffType"]').removeAttr('disabled');
				} else {
					$('#offTypeDiv').hide();
					$('select[name="fdOffType"]').prop('disabled','disabled');
				}
			}
		</script>
	</template:replace>
</template:include>