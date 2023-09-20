<%@ page import="java.util.List" %>
<%@ page import="com.landray.kmss.km.cogroup.util.CogroupUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%

    boolean weixinModuleExist =  CogroupUtil.moduleExist("/third/weixin");
    boolean weixinChatDataEnable = CogroupUtil.isWeixinChatDataEnable();
    request.setAttribute("weixinChatDataEnable",weixinChatDataEnable==true?"true":"false");
    request.setAttribute("weixinConfigUrl",request.getContextPath()+"/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.third.weixin.work.model.WeixinWorkConfig");
%>

<template:include ref="config.profile.edit" sidebar="no">
    <template:replace name="title">会话记录存档配置</template:replace>
    <template:replace name="head">
        <script type="text/javascript" src="${KMSS_Parameter_ResPath}js/common.js?s_cache=${LUI_Cache}"></script>
        <script type="text/javascript">
            Com_IncludeFile("validator.jsp|validation.js|plugin.js|validation.jsp|xform.js", null, "js");
        </script>
        <style type="text/css">
            .tb_normal td {
            //padding: 5px;
            //border: 1px #d2d2d2 solid;
            //word-break: break-all;
            }
        </style>
    </template:replace>
    <template:replace name="content">
        <h2 align="center" style="margin: 10px 0">
            <span class="profile_config_title">会话记录存档配置</span>
        </h2>

            <center>
                <table class="tb_normal" width=95% >
                    <kmss:ifModuleExist path="/third/weixin">
                    <tr>
                        <td class="td_normal_title" width=15%  rowSpan="1">
                                所属应用
                        </td>

                           <td>
                                <div style="height: 20px;padding: 5px;">

                                    <div style="float:left;margin-left: 10px;">
                                        企业微信
                                    </div>
                                    <div style="float:right;width: 100px;">
                                        <div style="float:left;margin-top: 1px;">
                                            <a target="_blank" href="${weixinConfigUrl}" style="color:blue;">配置</a>
                                        </div>
                                        <div style="float:right;">
                                            <ui:switch property="wxpay" showType="show" showText="false" checked="${weixinChatDataEnable}"  enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
                                        </div>
                                    </div>
                                </div>
                            </td>

                    </tr>
                    </kmss:ifModuleExist>
                </table>

            </center>




        <script type="text/javascript">

        </script>
    </template:replace>
</template:include>

