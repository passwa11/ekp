<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/common.jsp"%>
<template:include ref="default.simple">
    <template:replace name="head">
        <template:super />
        <link rel="stylesheet" type="text/css" href="${LUI_ContextPath}/sys/evaluation/resource/evaluation/share/share_person.css">
        <%@ include file="/sys/evaluation/sys_evaluation_share/sysEvaluationShareToPerson_js.jsp"%>
        <script>
            seajs.use(['theme!form']);
            Com_IncludeFile("validation.js|plugin.js|xform.js", null, "js");
        </script>
    </template:replace>
    <template:replace name="body">
        <html:form action="/sys/evaluation/sys_evaluation_share/sysEvaluationShare.do?method=saveEvalShare&type=person">
            <div style="padding:10px 20px 15px;">
                <table id="share_person_main" class="tb_simple intr_opt_table" width="100%" border="0"
                       cellspacing="0" cellpadding="0" style="border-bottom: 1px dotted #ccc;">
                    <tr group="intr_person" valign="top">
                        <td valign="top">
                            <div class="share_person_opt share_bolder share_title_color">${ lfn:message('sys-evaluation:sysEvaluationShare.shareTarget')}</div>
                            <xform:address propertyId="goalPersonIds" style="width:89.5%;height:40px" propertyName="goalPersonNames" subject="${ lfn:message('sys-evaluation:sysEvaluationShare.shareTarget')}"
                                           required="true" showStatus="edit" orgType="ORG_TYPE_ALL" mulSelect="true" textarea="true">
                            </xform:address>
                        </td>
                    </tr>

                    <tr valign="top">
                        <td valign="top">
                            <div class="share_person_opt share_bolder share_title_color">${ lfn:message('sys-evaluation:sysEvaluationShare.fdShareReason')}</div>
                            <div style="display: inline-block;width: 89.5%;">
                                <!-- <textarea name="fdShareReason" class="share_content"></textarea> -->
                                <iframe name="fdShareReason" id="sharePersonIframe" class="share_content"
                                        src="${LUI_ContextPath}/sys/evaluation/sys_evaluation_share/sysEvaluationShareToPerson_content.jsp">
                                </iframe>
                            </div>

                            <div class="share_down_msg">
							<span class='share_icons'>
                                    ${lfn:message('sys-evaluation:sysEvaluationShare.expression') }
                            </span>
                                <div class='share_biaoqing'></div>
                                <input id="share_to_person_button" class="share_button" type="button" value="<bean:message key="button.submit"/>">
                                <span id="person_share_prompt" class="share_prompt"></span>
                            </div>

                            <div class="lui_share_notyfy">
                                <label class="intr_notify">
                                    <input id="fdPersonIsNotify" name="fdPersonIsNotify" type="checkbox" value="1" checked="checked">
                                    <bean:message key="sysEvaluationShare.notify.auth" bundle="sys-evaluation" />
                                </label>
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <label class="share_notify">
                                    <bean:message key="sysEvaluationShare.notifySetting.fdNotifyType" bundle="sys-evaluation" />：
                                    <!--
                                    <kmss:editNotifyType property="fdPersonNotifyType" value="todo"/>
                                    -->
                                    <input id="fdPersonNotifyType" name="fdPersonNotifyType" type="checkbox" value="todo" checked="checked">
                                    <bean:message key="sysEvaluationShare.notifyType.toread" bundle="sys-evaluation" />
                                </label>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
        </html:form>
        <!-- 分享记录 -->
        <c:import url="/sys/evaluation/sys_evaluation_share/sysEvaluationShare_person_log.jsp" charEncoding="UTF-8">
            <c:param name="fdShareMode" value="1" />
            <c:param name="fdModelId" value="${param.fdModelId}" />
            <c:param name="fdModelName" value="${param.fdModelName}" />
        </c:import>

    </template:replace>
</template:include>
<script>
    if(window.share_to_person_opt ==null){
        window.share_to_person_opt = new SharePersonOpt();
    }
    share_to_person_opt.onload();
</script>