<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
    <template:replace name="content">
        <html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do" onsubmit="return validateAppConfigForm(this);">
            <div style="margin-top:25px">
                <p class="configtitle"><bean:message key="sys.sysAdminConfig" bundle="sys-admin" /></p>
                <center>
                    <table class="tb_normal" width=90%>
                        <tr>
                            <td class="td_normal_title" width="35%">${lfn:message('sys-admin:sys.sysAdminConfig.debugEnable')}</td>
                            <td>
                                <ui:switch property="value(debugEnable)" checked="${debugEnable}" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
                                <br>
                                <div>
                                        ${lfn:message('sys-admin:sys.sysAdminConfig.debugEnable.tips')}
                                </div>
                            </td>
                        </tr>
                    </table>
                    <div style="margin-bottom: 10px;margin-top:25px">
                        <ui:button text="${lfn:message('button.save')}" suspend="bottom" height="35" width="120" onclick="commitMethod();" order="1" ></ui:button>
                    </div>
                </center>
            </div>
        </html:form>
        <script>
            Com_IncludeFile("jquery.js");
        </script>
        <script>
            $KMSSValidation();

            function validateAppConfigForm(thisObj){
                return true;
            }

            function commitMethod(value){
                Com_Submit(document.sysAppConfigForm, 'update');
            }

            LUI.ready(function() {

            });
        </script>
    </template:replace>
</template:include>