<%@ page import="com.landray.kmss.sys.authentication.user.KMSSUserAuthInfoCache" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title"><bean:message bundle="sys-organization" key="sysOrgConfig" /></template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span class="profile_config_title"><bean:message bundle="sys-organization" key="sysOrgPerson.config.title" /></span>
			<% if(!new com.landray.kmss.sys.organization.transfer.SysOrganizationConfigChecker().isRuned()) { %>
			<span style="color: red;"><bean:message bundle="sys-organization" key="organization.config.transfer" /></span>
			<% } %>
		</h2>
		
		<html:form action="/sys/organization/sys_organization_config/sysOrganizationConfig.do" onsubmit="return validateAppConfigForm(this);">
			<center>
				<table class="tb_normal" width=95%>
					<tr>
					  <td class="td_normal_title" width=15%>
						 <bean:message bundle="sys-organization" key="sysOrganizationSearch.config.realTimeSeach"/>
						 <br>
						 <font color="red"><bean:message bundle="sys-organization" key="sysOrgPerson.config.realTimeSeach.alert"/></font>
					  </td><td colspan="3">
							<ui:switch property="value(realTimeSearch)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
					  </td>
					</tr>
					<tr>
					  <td class="td_normal_title" width=35%>
						 <bean:message  bundle="sys-organization" key="sysOrganizationRelation.config.isRelation"/>
						  <br>
						 <font color="red"><bean:message bundle="sys-organization" key="sysOrgPerson.config.isRelation.alert"/></font>
					  </td><td colspan="3">
							<ui:switch property="value(isRelation)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
					  </td>
					</tr>
					<tr>
					  <td class="td_normal_title" width=35%>
						 <bean:message  bundle="sys-organization" key="organization.keepGroupUnique" />
					  </td><td colspan="3">
							<ui:switch property="value(keepGroupUnique)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
					  </td>
					</tr>
					<tr>
					  <td class="td_normal_title" width=35%>
						 <bean:message  bundle="sys-organization" key="sysOrgElement.deptLevelNames"/>
					  </td><td colspan="3">
						<xform:radio alignment="V" property="value(kmssOrgDeptLevelDisplay)" value="${passwordSecurityConfig.kmssOrgDeptLevelDisplay}" showStatus="edit" onValueChange="kmssOrgDeptLevelDisplayChange();">
						 	<xform:simpleDataSource value="1"><bean:message  bundle="sys-organization" key="sysOrgElement.deptLevelNames.type1"/></xform:simpleDataSource>
						 	<xform:simpleDataSource value="2"><bean:message  bundle="sys-organization" key="sysOrgElement.deptLevelNames.type2"/></xform:simpleDataSource>
						 	<xform:simpleDataSource value="3"><bean:message  bundle="sys-organization" key="sysOrgElement.deptLevelNames.type3.part1"/><xform:text property="value(kmssOrgDeptLevelDisplayLength)" value="${passwordSecurityConfig.kmssOrgDeptLevelDisplayLength}" style="width:50px;" showStatus="edit" required="true" validators="digits min(0)"/><bean:message  bundle="sys-organization" key="sysOrgElement.deptLevelNames.type3.part2"/></xform:simpleDataSource>
						 </xform:radio>
					  </td>
					</tr>
					<tr>
					  <td class="td_normal_title" width=35%>
						 <bean:message  bundle="sys-organization" key="sysOrgElement.addressDeptLevelNames"/>
					  </td><td colspan="3">
						<xform:radio alignment="V" property="value(kmssOrgAddressDeptLevelDisplay)" value="${passwordSecurityConfig.kmssOrgAddressDeptLevelDisplay}" showStatus="edit" onValueChange="kmssOrgAddressDeptLevelDisplayChange();">
						 	<xform:simpleDataSource value="1"><bean:message  bundle="sys-organization" key="sysOrgElement.deptLevelNames.type1"/></xform:simpleDataSource>
						 	<xform:simpleDataSource value="2"><bean:message  bundle="sys-organization" key="sysOrgElement.deptLevelNames.type2"/></xform:simpleDataSource>
						 	<xform:simpleDataSource value="3"><bean:message  bundle="sys-organization" key="sysOrgElement.deptLevelNames.type3.part1"/><xform:text property="value(kmssOrgAddressDeptLevelDisplayLength)" value="${passwordSecurityConfig.kmssOrgAddressDeptLevelDisplayLength}" style="width:50px;" showStatus="edit" required="true" validators="digits min(0)"/><bean:message  bundle="sys-organization" key="sysOrgElement.deptLevelNames.type3.part2"/></xform:simpleDataSource>
						 </xform:radio>
					  </td>
					</tr>
					<tr>
					  <td class="td_normal_title" width=35%>
						 <bean:message  bundle="sys-organization" key="organization.showStaffingLevel" />
					  </td><td colspan="3">
							<ui:switch property="value(showStaffingLevel)" checked="true" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
					  </td>
					</tr>
					<tr>
					  <td class="td_normal_title" width=35%>
					  	<bean:message bundle="sys-organization" key="sysOrgPersonConfig.special.char.pass" />
					  </td><td colspan="3">
							<ui:switch property="value(isLoginSpecialChar)" checked="false" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
					  </td>
					</tr>											
					<tr>
					  <td class="td_normal_title" width=35%>
						 <bean:message  bundle="sys-organization" key="organization.isNoRequired" />
						 <br>
						 <font color="red"><bean:message bundle="sys-organization" key="organization.isNoRequired.desc"/></font>
					  </td><td colspan="3">
							<ui:switch property="value(isNoRequired)" checked="true" onValueChange="kmssNoRequiredChange()" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
					  </td>
					</tr>
					
					<tr>
					  <td class="td_normal_title" width=35%>
						 <bean:message  bundle="sys-organization" key="sysOrganizationRelation.config.orderGroupPerson"/> 
					  </td><td colspan="3">
							<ui:switch property="value(orderGroupPerson)" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
					  </td>
					</tr>

					<%-- 登录时登录名大小写敏感 --%>
					<tr>
						<td class="td_normal_title" width=35%>
							<bean:message  bundle="sys-organization" key="sysOrganizationRelation.config.loginNameCase"/>
						</td><td colspan="3">
						<xform:radio alignment="V" property="value(loginNameCase)" showStatus="edit" onValueChange="loginNameCaseChange">
							<xform:simpleDataSource value="1"><bean:message  bundle="sys-organization" key="sysOrganizationRelation.config.loginNameCase.type1"/></xform:simpleDataSource>
							<xform:simpleDataSource value="2"><bean:message  bundle="sys-organization" key="sysOrganizationRelation.config.loginNameCase.type2"/></xform:simpleDataSource>
							<xform:simpleDataSource value="3"><bean:message  bundle="sys-organization" key="sysOrganizationRelation.config.loginNameCase.type3"/></xform:simpleDataSource>
						</xform:radio>
					</td>
					</tr>

                    <%--用户权限预缓存--%>
                    <c:if test="<%=KMSSUserAuthInfoCache.ENABLED%>">
                        <tr>
                            <td class="td_normal_title" width=35%>
                                <bean:message bundle="sys-organization" key="sysOrganizationConfig.userAuth.cache.enable"/>
                                <br/>
                                <bean:message bundle="sys-organization" key="sysOrganizationConfig.userAuth.cache.desc.part1"/>
                                <bean:message bundle="sys-organization" key="sysOrganizationConfig.userAuth.quartz.name"/>
                                <bean:message bundle="sys-organization" key="sysOrganizationConfig.userAuth.cache.desc.part2"/>
                            </td>
                            <td colspan="3">
                                <ui:switch property="value(isUserAuthCacheEnable)"
                                           enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
                                           disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"
                                           onValueChange="isUserAuthCacheEnableChange()"/>
                                <div name="userAuthCacheDetails">
                                        <%--过期时间--%>
                                    <bean:message bundle="sys-organization"
                                                  key="sysOrganizationConfig.userAuth.cache.expire"/>
                                    <xform:text property="value(userAuthCacheExpire)"
                                                style="width:50px;" showStatus="edit" required="true"
                                                validators="digits min(1)"
                                                subject="${lfn:message('sys-organization:sysOrganizationConfig.userAuth.cache.expire')}"/>
                                    <bean:message bundle="sys-organization"
                                                  key="sysOrganizationConfig.userAuth.cache.expireUnit"/>
                                    <br/>
                                        <%--缓存范围--%>
                                    <bean:message bundle="sys-organization"
                                                  key="sysOrganizationConfig.userAuth.cache.limits"/>
                                    <xform:address propertyId="value(userAuthCacheLimitIds)"
                                                   propertyName="value(userAuthCacheLimitNames)"
                                                   required="true"
                                                   orgType="ORG_TYPE_DEPT|ORG_TYPE_PERSON" textarea="true"
                                                   mulSelect="true" style="width: 90%"
                                                   subject="${lfn:message('sys-organization:sysOrganizationConfig.userAuth.cache.limits')}"/>
                                    <%--清理缓存--%>
                                    <div style="display: none">
                                    <br/>
                                        <bean:message bundle="sys-organization"
                                                      key="sysOrganizationConfig.userAuth.cache.clearTitle"/>
                                            <%--清理全部用户--%>
                                        <ui:button
                                                text="${lfn:message('sys-organization:sysOrganizationConfig.userAuth.cache.clearAll')}"
                                                height="20" width="95" onclick="userAuthCacheClearAll();"/>
                                    </div>
                                    <br/>
                                        <%--清理所选用户--%>
                                    <xform:address propertyId="userAuthCacheClearIds"
                                                   propertyName="userAuthCacheClearNames"
                                                   orgType="ORG_TYPE_PERSON"
                                                   mulSelect="true" style="width: 50%"/>
                                    <ui:button
                                            text="${lfn:message('sys-organization:sysOrganizationConfig.userAuth.cache.clear')}"
                                            height="20" width="95" onclick="userAuthCacheClear();"/>
                                </div>
                            </td>
                        </tr>
                    </c:if>

				</table>
			</center>
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName" value="com.landray.kmss.sys.organization.model.SysOrganizationConfig" />
			
			<center style="margin-top: 10px;">
			<!-- 保存 -->
			<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.sysAppConfigForm, 'update');"></ui:button>
			</center>
		</html:form>
		
	 	<script type="text/javascript">
	 		$KMSSValidation();
		 	function validateAppConfigForm(thisObj){
		 		return true;
		 	}
		 	
		 	function kmssOrgDeptLevelDisplayChange() {
		 		var displayVal = $("input[name='value\\\(kmssOrgDeptLevelDisplay\\\)']:checked").val();
		 		var displayLength = $("input[name='value\\\(kmssOrgDeptLevelDisplayLength\\\)']");
		 		if(displayVal == '3') {
		 			displayLength.attr("disabled", false);
		 		} else {
		 			displayLength.attr("disabled", true);
		 		}
		 	}
		 	
		 	function kmssOrgAddressDeptLevelDisplayChange() {
		 		var displayVal = $("input[name='value\\\(kmssOrgAddressDeptLevelDisplay\\\)']:checked").val();
		 		var displayLength = $("input[name='value\\\(kmssOrgAddressDeptLevelDisplayLength\\\)']");
		 		if(displayVal == '3') {
		 			displayLength.attr("disabled", false);
		 		} else {
		 			displayLength.attr("disabled", true);
		 		}
		 	}
		 	
		 	function kmssNoRequiredChange() {
		 		var displayVal = $("input[name='value\\\(isNoRequired\\\)']").val();
		 		if('true'== displayVal){
		 			alert('<bean:message bundle="sys-organization" key="organization.isNoRequired.alert"/>');
		 		}
		 	}

            /**
             * 用户权限缓存开关
             */
            function isUserAuthCacheEnableChange() {
                var isUserAuthCacheEnable = $("input[name='value\\\(isUserAuthCacheEnable\\\)']").val();
                var userAuthCacheDetails = $("div[name='userAuthCacheDetails']");
                var userAuthCacheExpire = $("input[name='value\\\(userAuthCacheExpire\\\)']");
                var userAuthCacheLimitIds = $("input[name='value\\\(userAuthCacheLimitIds\\\)']");
                var userAuthCacheLimitNames = $("textarea[name='value\\\(userAuthCacheLimitNames\\\)']");
                if (isUserAuthCacheEnable == 'true') {
                    userAuthCacheDetails.show();
                    userAuthCacheExpire.attr("validate", "required digits min(1)");
                    userAuthCacheLimitIds.attr("validate", "required");
                    userAuthCacheLimitNames.attr("validate", "required");
                } else {
                    userAuthCacheDetails.hide();
                    userAuthCacheExpire.attr("validate", "");
                    userAuthCacheLimitIds.attr("validate", "");
                    userAuthCacheLimitNames.attr("validate", "");
                }
            }

            /**
             * 用户权限缓存手动清理，全部清理
             */
            function userAuthCacheClearAll() {
                seajs.use(['lui/dialog'], function (dialog) {
                    dialog.confirm('<bean:message bundle="sys-organization" key="sysOrganizationConfig.userAuth.cache.clearAll.confirm"/>', function (value) {
                        if (value == true) {
                            userAuthCacheClearAjax("all");
                        }
                    });
                });
            }

            /**
             * 用户权限缓存手动清理，选定清理
             */
            function userAuthCacheClear() {
                seajs.use(['lui/dialog'], function (dialog) {
                    var clearIds = $('input[name="userAuthCacheClearIds"]').val();
                    if (!clearIds) {
                        dialog.alert('<bean:message bundle="sys-organization" key="sysOrganizationConfig.userAuth.cache.clear.choose"/>');
                        return;
                    }
                    dialog.confirm('<bean:message bundle="sys-organization" key="sysOrganizationConfig.userAuth.cache.clear.confirm"/>', function (value) {
                        if (value == true) {
                            userAuthCacheClearAjax(clearIds);
                        }
                    });
                });
            }

            function userAuthCacheClearAjax(clearIds) {
                seajs.use(['lui/dialog'], function (dialog) {
                    window.del_load = dialog.loading();
                    $.ajax({
                        type: 'POST',
                        url: '<c:url value="/sys/organization/sys_organization_config/sysOrganizationConfig.do" />?method=clearUserAuthInfo&clearIds=' + clearIds,
                        contentType: "application/json",
                        dataType: "json",
                        success: function (data) {
                            if (window.del_load != null) {
                                window.del_load.hide();
                            }
                            dialog.result(data);
                        },
                    });
                });
            }

            function loginNameCaseChange(val) {
            	if(val === '2' || val === '3') {
            		// 需要检查是否已经做了数据迁移
					seajs.use(['lui/dialog'], function (dialog) {
						window.del_load = dialog.loading();
						$.ajax({
							type: 'POST',
							url: '<c:url value="/sys/organization/sys_organization_config/sysOrganizationConfig.do" />?method=checkLoginNameTask',
							contentType: "application/json",
							dataType: "json",
							success: function (data) {
								if (window.del_load != null) {
									window.del_load.hide();
								}
								if(!data.status) {
									// 没有做迁移，需要先做迁移
									dialog.failure('<bean:message bundle="sys-organization" key="sysOrganizationRelation.config.loginNameCase.fail"/>');
									$("input[name='value\\\(loginNameCase\\\)']").removeAttr("checked");
									$("input[name='value\\\(loginNameCase\\\)']")[0].checked = true;
								}
							},
						});
					});
				}
			}

			LUI.ready(function() {
		 		kmssOrgDeptLevelDisplayChange();
				isUserAuthCacheEnableChange();
			});
	 	</script>
	</template:replace>
</template:include>
