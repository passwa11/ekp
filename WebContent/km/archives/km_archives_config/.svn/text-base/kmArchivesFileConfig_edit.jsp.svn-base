<%@page import="com.landray.kmss.km.archives.service.spring.KmArchivesModuleSelectService" %>
<%@page import="com.landray.kmss.sys.profile.util.ProfileMenuUtil" %>
<%@page import="com.landray.kmss.util.ClassUtils" %>
<%@page import="java.lang.reflect.Method" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%
    KmArchivesModuleSelectService moduleSelectService = new KmArchivesModuleSelectService();
    boolean hasKmAgreement = ProfileMenuUtil.moduleExist("/km/agreement");
    boolean agreementVersion = false;
    if (hasKmAgreement) {
        //测试环境通过配置表控制，生产环境通过license控制
        //所以此处直接反射获取值
        try {
            Class agreementUtil = ClassUtils.forName("com.landray.kmss.km.agreement.util.KmAgreementUtil");
            Method getVersionMethod = agreementUtil.getDeclaredMethod("getVersion");
            getVersionMethod.setAccessible(true);
            String version = (String) getVersionMethod.invoke(agreementUtil);
            if ("professional".equals(version)) {
                agreementVersion = true;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

    }
    pageContext.setAttribute("agreementVersion", agreementVersion);
%>
<template:include ref="config.profile.edit" sidebar="no">
    <template:replace name="title"><bean:message bundle="sys-organization" key="sysOrgConfig"/></template:replace>
    <template:replace name="head">
        <script>
            Com_IncludeFile("validator.jsp|validation.js|plugin.js|validation.jsp|dialog.js", null, "js");
        </script>
        <script>$KMSSValidation();</script>
        <script type="text/javascript">
            function submitConfig() {

                Com_Submit(document.sysAppConfigForm, 'update');
            }

            LUI.ready(function () {
                changeSelect();
                /* var modules = $("[name='value(fdFileModels)']").val();
                if(modules != null && modules != ''){
                    $.get('

                ${LUI_ContextPath}/km/archives/km_archives_main/kmArchivesMain.do?method=getModuleNames',{modules:modules},function(data) {
						$("[name='fdFileModelNames']").val(data);
					});
				} */
            })
            seajs.use(['lui/jquery'], function ($) {
                $(document).ready(function () {
                    $("[name='fdFileModels']").click(function () {
                        var fdFileModels = [];
                        $("[name='fdFileModels']").each(function () {
                            var item = this;
                            if (item.checked) {
                                fdFileModels.push(item.value);
                            }
                        });
                        $("[name='value(fdFileModels)']").val(fdFileModels.join(';'));
                    });
                    var fdFileModels = $("[name='value(fdFileModels)']").val();
                    fdFileModels = fdFileModels.split(';');
                    for (var i = 0; i < fdFileModels.length; i++) {
                        $("[value='" + fdFileModels[i] + "']").prop('checked', true);
                    }
                });
            });

            //选择模块
            function selectModule() {
                Dialog_List(true, "value(fdFileModels)", "fdFileModelNames", null, "kmArchivesModuleSelectService", afterModuleSelect, null, null, null,
                    "<bean:message bundle='km-archives' key='kmArchivesFileConfig.moduleSelectDilog'/>");
            }

            function afterModuleSelect() {

            }

            function changeSelect() {
                var isChecked = "true" == $("input[name='value(fdStartFile)']").val();
                if (isChecked) $("#modulesTr").show();
                else $("#modulesTr").hide()
            }
        </script>
    </template:replace>
    <template:replace name="content">
        <html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do">
            <div style="margin-top:25px">
                <p class="configtitle">
                    <bean:message bundle="km-archives" key="table.kmArchivesFileConfig"/>
                </p>
                <center>
                    <table class="tb_normal" width=90%>

                        <tr>
                            <td class="td_normal_title" width=15%>
                                <bean:message bundle="km-archives" key="kmArchivesFileConfig.fdStartFile"/>
                            </td>
                            <td colspan=3>
                                <ui:switch property="value(fdStartFile)"
                                           enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
                                           disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"
                                           onValueChange="changeSelect();"></ui:switch>
                            </td>
                        </tr>
                        <tr id="modulesTr">
                            <td class="td_normal_title" width=15%>
                                <bean:message bundle="km-archives" key="kmArchivesFileConfig.fdFileModels"/>
                            </td>
                            <td colspan="3">
                                <html:hidden property="value(fdFileModels)"/>
                                    <%-- <input name="fdFileModelNames" type="text" class="inputsgl" style="width:95%"/>
                                    <a onclick="selectModule();return false;" href=""><bean:message key="dialog.selectOther" /></a> --%>
                                <kmss:ifModuleExist path="/km/review/">
                                    <label>
                                        <input name="fdFileModels" type="checkbox" value="km/review"/>
                                        <%=moduleSelectService.getModuleNames("km/review") %>
                                    </label>
                                </kmss:ifModuleExist>
                                <kmss:ifModuleExist path="/km/contract/">
                                    <label>
                                        <input name="fdFileModels" type="checkbox" value="km/contract"/>
                                        <%=moduleSelectService.getModuleNames("km/contract") %>
                                    </label>
                                </kmss:ifModuleExist>
                                <kmss:ifModuleExist path="/km/imissive/">
                                    <label>
                                        <input name="fdFileModels" type="checkbox" value="km/imissive"/>
                                        <%=moduleSelectService.getModuleNames("km/imissive") %>
                                    </label>
                                </kmss:ifModuleExist>
                                <c:if test="${agreementVersion eq true}">
                                    <kmss:ifModuleExist path="/km/agreement/">
                                        <label>
                                            <input name="fdFileModels" type="checkbox" value="km/agreement"/>
                                            <%=moduleSelectService.getModuleNames("km/agreement") %>
                                        </label>
                                    </kmss:ifModuleExist>
                                </c:if>
                                <kmss:ifModuleExist path="/hr/ratify/">
                                    <label>
                                        <input name="fdFileModels" type="checkbox" value="hr/ratify"/>
                                        <%=moduleSelectService.getModuleNames("hr/ratify") %>
                                    </label>
                                </kmss:ifModuleExist>
                                <kmss:ifModuleExist path="/km/supervise/">
                                    <label>
                                        <input name="fdFileModels" type="checkbox" value="km/supervise"/>
                                        <%=moduleSelectService.getModuleNames("km/supervise") %>
                                    </label>
                                </kmss:ifModuleExist>
                                <%--<kmss:ifModuleExist path="/km/reception/">
                                    <label>
                                        <input name="fdFileModels" type="checkbox" value="km/reception"/>
                                        <%=moduleSelectService.getModuleNames("km/reception") %>
                                    </label>
                                </kmss:ifModuleExist>--%>
                            </td>
                        </tr>
                    </table>
                    <div style="margin-bottom: 10px;margin-top:25px">
                        <ui:button text="${lfn:message('button.save')}" height="35" width="120"
                                   onclick="submitConfig();" order="1"></ui:button>
                    </div>
                </center>
            </div>
            <html:hidden property="method_GET"/>
            <html:hidden property="modelName" value="com.landray.kmss.km.archives.model.KmArchivesFileConfig"/>
        </html:form>
    </template:replace>
</template:include>
