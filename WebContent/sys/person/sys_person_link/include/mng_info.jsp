<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<c:set var="navForm" value="${requestScope[param.formName] }" scope="page" />
				<tr>
					<td class="td_normal_title">
						<bean:message bundle="sys-person" key="sysPersonSysNavCategory.pushTo"/>
					</td>
					<td colspan="3">
						<div>
							<xform:radio property="fdPushType" onValueChange="switchSelectPushType();" required="true">
								<xform:enumsDataSource enumsType="sysPerson_fdPushType" />
							</xform:radio>
						</div>
						<div id="pushElementsSeletor" style="padding-top:8px; <c:if test="${navForm.fdPushType != '3'}" >display:none;</c:if>">
						<xform:address orgType="ORG_TYPE_ALL" propertyId="fdPushElementIds" propertyName="fdPushElementNames"
							textarea="true" style="width:99%" mulSelect="true" required="true" />
						</div>
						
						<script>
							var validation=$KMSSValidation();
							function switchSelectPushType() {
								var type = $('[name="fdPushType"]:checked').val();
								var ele = $('[name="fdPushElementNames"]')[0];
								if (type == '3') {
									$('#pushElementsSeletor').show();
									validation.addElements(ele,"required");
								} else {
									$('#pushElementsSeletor').hide();
									validation.removeElements(ele,"required");
									KMSSValidation_HideWarnHint(ele);
								}
							}
							$(function(){
								switchSelectPushType();
							});
						</script>
					</td>
				</tr>
				<%-- 所属场所 --%>
				<c:import url="/sys/authorization/sys_auth_area/sysAuthArea_field.jsp" charEncoding="UTF-8">
			        <c:param name="id" value="${sysPortalMainForm.authAreaId}"/>
			    </c:import>
				<tr>
					<td class="td_normal_title">
						<bean:message bundle="sys-person" key="sysPersonSysNavCategory.authEditors"/>
					</td>
					<td colspan="3">
						<xform:address propertyId="authEditorIds" propertyName="authEditorNames"
							textarea="true" style="width:100%" mulSelect="true" />
					</td>
				</tr>
