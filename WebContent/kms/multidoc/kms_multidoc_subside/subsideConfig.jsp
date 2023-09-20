<%@ page import="com.landray.kmss.util.SpringBeanUtil" %>
<%@ page import="com.landray.kmss.sys.filestore.service.ISysFileConvertConfigService" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
    <template:replace name="content">
        <style>
            .line-gap{
                margin-top: 5px;
            }
            .msg-tips{
                color: #cfcfcf;
            }
            .msg-note{
                color: #ff6060;
            }
        </style>
        <%
            ISysFileConvertConfigService sysFileConvertConfigService = (ISysFileConvertConfigService)SpringBeanUtil.getBean("sysFileConvertConfigService");
            String wpsCenterEnable = sysFileConvertConfigService.findAppConfigValue("fdKey ='attconvert.converter.type.wpsCenter'", "false");
            String wpsPreviewEnable = sysFileConvertConfigService.findAppConfigValue("fdKey ='attconvert.converter.type.wps'", "false");
            String dianjuEnable = sysFileConvertConfigService.findAppConfigValue("fdKey ='attconvert.converter.type.dianju'", "false");
            String fuxinEnable = sysFileConvertConfigService.findAppConfigValue("fdKey ='attconvert.converter.type.foxit'", "false");
            pageContext.setAttribute("wpsCenterEnable", "true".equals(wpsCenterEnable) ? "true" : "false");
            pageContext.setAttribute("wpsPreviewEnable", "true".equals(wpsPreviewEnable) ? "true" : "false");
            pageContext.setAttribute("dianjuEnable", "true".equals(dianjuEnable) ? "true" : "false");
            pageContext.setAttribute("fuxinEnable", "true".equals(fuxinEnable) ? "true" : "false");
        %>
        <html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do" onsubmit="return validateAppConfigForm(this);">
            ${appConfigForm}
            <div style="margin-top:25px">
                <p class="configtitle"><bean:message key="kmsMultidoc.config.title" bundle="kms-multidoc" /></p>
                <center>
                    <table class="tb_normal" width=90%>
                        <tr>
                            <td class="td_normal_title" width="15%">${lfn:message('kms-multidoc:kmsMultidoc.config.convertType')}</td>
                            <td>
                                <div>
                                    <input type="radio" name="value(convertType)" value="aspose" checked onclick="changeConvertType(this)"/>
                                    <span>${lfn:message('kms-multidoc:kmsMultidoc.config.convertType.aspose')}</span>
                                    <c:if test="${wpsCenterEnable == 'true'}">
                                        <input type="radio" name="value(convertType)" value="wpsCenter" onclick="changeConvertType(this)"/>
                                        <span>${lfn:message('kms-multidoc:kmsMultidoc.config.convertType.wpsCenter')}</span>
                                    </c:if>
                                    <c:if test="${wpsPreviewEnable == 'true'}">
                                        <input type="radio" name="value(convertType)" value="wps" onclick="changeConvertType(this)" />
                                        <span>${lfn:message('kms-multidoc:kmsMultidoc.config.convertType.wpsPreview')}</span>
                                    </c:if>
                                    <c:if test="${dianjuEnable == 'true'}">
                                        <input type="radio" name="value(convertType)" value="dianju" onclick="changeConvertType(this)"/>
                                        <span>${lfn:message('kms-multidoc:kmsMultidoc.config.convertType.dianju')}</span>
                                    </c:if>
                                    <c:if test="${fuxinEnable == 'true'}">
                                        <input type="radio" name="value(convertType)" value="foxit" onclick="changeConvertType(this)"/>
                                        <span>${lfn:message('kms-multidoc:kmsMultidoc.config.convertType.fuxin')}</span>
                                    </c:if>
                                </div>
                                <div class="line-gap">
                                    <p class="msg-tips">${lfn:message('kms-multidoc:kmsMultidoc.config.convertType.tips')}</p>
                                    <p class="msg-note">${lfn:message('kms-multidoc:kmsMultidoc.config.convertType.note')}</p>
                                </div>
                            </td>
                        </tr>
                        <tr id="subsideType" style="display: none;">
                            <td class="td_normal_title" width="15%">${lfn:message('kms-multidoc:kmsMultidoc.config.subsideType')}</td>
                            <td>
                                <div>
                                    <input type="radio" name="value(subsideType)" value="none" onclick="changeSubsideType(this)"/>
                                    <span>${lfn:message('kms-multidoc:kmsMultidoc.config.subsideType.none')}</span>
                                    <input type="radio" name="value(subsideType)" value="toPDF" onclick="changeSubsideType(this)"/>
                                    <span>${lfn:message('kms-multidoc:kmsMultidoc.config.subsideType.2pdf')}</span>
                                    <input type="radio" name="value(subsideType)" value="toOFD" onclick="changeSubsideType(this)"/>
                                    <span>${lfn:message('kms-multidoc:kmsMultidoc.config.subsideType.2ofd')}</span>
                                </div>
                                <div id="mainDoc" class="line-gap">
                                    <span>${lfn:message('kms-multidoc:kmsMultidoc.config.subsideType.mainDoc')}</span>：
                                    <input type="radio" name="value(mainDoc)" value="toPDF" />
                                    <span>${lfn:message('kms-multidoc:kmsMultidoc.config.subsideType.mainDoc.pdf')}</span>
                                    <input type="radio" name="value(mainDoc)" value="toOFD" />
                                    <span>${lfn:message('kms-multidoc:kmsMultidoc.config.subsideType.mainDoc.ofd')}</span>
                                </div>
                                <div class="line-gap">
                                    <p class="msg-tips">${lfn:message('kms-multidoc:kmsMultidoc.config.subsideType.tips')}</p>
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

            function commitMethod(){
                Com_Submit(document.sysAppConfigForm, 'update');
            }

            LUI.ready(function() {
                var convertType = '${sysAppConfigForm.map.convertType}';
                var subsideType = '${sysAppConfigForm.map.subsideType}';
                var mainDoc = '${sysAppConfigForm.map.mainDoc}';
                if (convertType && convertType != null) {
                    var els = $("input[name='value(convertType)']");
                    for (var i = 0; i < els.length; i++) {
                        if ($(els[i]).val() == convertType) {
                            $(els[i]).click();
                            break;
                        }
                    }
                }
                if (subsideType && subsideType != null) {
                    var els = $("input[name='value(subsideType)']");
                    for (var i = 0; i < els.length; i++) {
                        if ($(els[i]).val() == subsideType) {
                            $(els[i]).click();
                            break;
                        }
                    }
                }
                if (mainDoc && mainDoc != null) {
                    var els = $("input[name='value(mainDoc)']");
                    for (var i = 0; i < els.length; i++) {
                        if ($(els[i]).val() == mainDoc) {
                            $(els[i]).click();
                            break;
                        }
                    }
                }
            });

            function changeConvertType(el) {
                $("#subsideType").hide();
                if ($(el).val() != 'aspose') {
                    $("#subsideType").show();
                }
            }

            function changeSubsideType(el) {
                $("#mainDoc").hide();
                if ($(el).val() == 'none') {
                    $("#mainDoc").show();
                }
            }

        </script>
    </template:replace>
</template:include>