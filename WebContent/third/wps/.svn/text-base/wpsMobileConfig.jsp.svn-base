<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">

    <template:replace name="title">
        ${lfn:message('third-wps:thirdWps.mobile.config')}
    </template:replace>

    <template:replace name="content">

        <h2 align="center" style="margin: 10px 0">

			<span style="color: #35a1d0;">
                    ${lfn:message('third-wps:thirdWps.mobile.config')} </span>

        </h2>

        <html:form action="/third/wps/thirdWpsConfig.do">

            <center>

                <table class="tb_normal" width=95%>

                    <tr class="thirdWps" >
                        <td class="td_normal_title" width=17%>${lfn:message('third-wps:thirdWps.mobile.serNum')}</td>
                        <td>
                            <html:text property="value(wpsSerNum)" style="width:80%"/>
                            <div>
                                <font size="0.5" color="#C0C0C0">
                                        ${lfn:message('third-wps:thirdWps.mobile.tip')}
                                </font>
                            </div>
                        </td>
                    </tr>

                </table>

            </center>
            <html:hidden property="method_GET" />
            <%-- <html:hidden property="value(thirdWpsAppValue)" /> --%>

            <input type="hidden" name="modelName"
                   value="com.landray.kmss.third.wps.model.ThirdWpsConfig" />
            <center style="margin-top: 10px;">

                <kmss:authShow roles="ROLE_THIRDWPS_SETTING">
                    <ui:button text="${lfn:message('button.save')}" height="35"
                               width="120"
                               onclick="configChange();"></ui:button>
                </kmss:authShow>
            </center>
        </html:form>

        <script type="text/javascript">
            function configChange() {
                Com_Submit(document.sysAppConfigForm, 'update');
            }
        </script>
    </template:replace>
</template:include>
