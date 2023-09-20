<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/common.jsp" %>
<%@ include file="/sys/evaluation/sys_evaluation_share/sysEvaluationShareToGroup_js.jsp" %>
<link rel="stylesheet" type="text/css" href="${LUI_ContextPath}/sys/evaluation/resource/evaluation/share/share_group.css">

<script>
    Com_IncludeFile("dialog.js", null, "js");
</script>

<body>
<html:form action="/sys/evaluation/sys_evaluation_share/sysEvaluationShare.do?method=saveGroupShare">
    <div style="padding:10px 20px 15px;">
        <table id="share_group_main" class="tb_simple intr_opt_table" width="100%" border="0" cellspacing="0" cellpadding="0" style="border-bottom: 1px dotted #ccc;">
            <tbody>
            <tr valign="top">
                <td valign="top">
                    <div class="share_group_opt share_bolder share_title_color">${lfn:message('sys-evaluation:sysEvaluationShare.group') }</div>
                    <div class="inputselectmul" style="width:89.5%;height:40px;border: none;">
                        <input type="hidden" id="fdGroupId" name="fdGroupId">
                        <input type="text" id="selGroup" style="width:100%;height:100%" onclick="selectGroup(this);">
                    </div>
                    <span class="txtstrong">&nbsp;*</span>
                    <div class="validation-advice" id="fdGroupIdAdvice" style="display: none;">
                        <table class="validation-table">
                            <tbody>
                            <tr>
                                <td>
                                    <div class="lui_icon_s lui_icon_s_icon_validator"></div>
                                </td>
                                <td class="validation-advice-msg">请选择 <span class="validation-advice-title">分享圈子</span></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </td>
            </tr>
            <tr valign="top">
                <td valign="top">
                    <div class="share_group_opt share_bolder share_title_color">${lfn:message('sys-evaluation:sysEvaluationShare.topic') }</div>
                    <div style="display: inline-block;width: 90.5%;">
                        <!-- <textarea name="fdShareReason" class="share_content"></textarea> -->
                        <iframe name="fdShareGroupTitle" id="shareGroupIframe" class="share_group_content" src="${LUI_ContextPath}/sys/evaluation/sys_evaluation_share/sysEvaluationShareToGroup_content.jsp">
                        </iframe>
                    </div>
                    <span id="fdGroupTitle" class="txtstrong">*</span> <span id="group_share_prompt" class="topic_limit">0/60</span>
                    <div class="validation-advice" id="fdGroupTitleAdvice" style="display: none;">
                        <table class="validation-table">
                            <tbody>
                            <tr>
                                <td>
                                    <div class="lui_icon_s lui_icon_s_icon_validator"></div>
                                </td>
                                <td class="validation-advice-msg"><span class="validation-advice-title">话题标题</span> 不能为空</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>

                    <div class="share_down_msg">
                        <div class="lui_share_notyfy" style="margin-top: 15px;">
                            <label class="intr_notify">
                                <input id="fdGroupIsNotify" name="fdGroupIsNotify" type="checkbox" value="1" checked="checked">
                                <bean:message key="sysEvaluationShare.notify.auth" bundle="sys-evaluation"/>
                            </label>
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <label class="share_notify">
                                <bean:message key="sysEvaluationShare.notifySetting.fdNotifyType" bundle="sys-evaluation"/>：
                                <!--
                                        <kmss:editNotifyType  property="fdGroupNotifyType" value="todo"/>
                                         -->
                                <input id="fdGroupNotifyType" name="fdGroupNotifyType" type="checkbox" value="todo" checked="checked">
                                <bean:message key="sysEvaluationShare.notifyType.toread" bundle="sys-evaluation"/>
                            </label>
                        </div>

                        <input id="share_to_group_button" class="share_button" type="button" value='<bean:message key="button.submit"/>'>
                        <span class="share_prompt"></span>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</html:form>
<!-- 分享记录 -->
<c:import url="/sys/evaluation/sys_evaluation_share/sysEvaluationShare_group_log.jsp" charEncoding="UTF-8">
    <c:param name="fdShareMode" value="3"/>
    <c:param name="fdModelId" value="${param.fdModelId}"/>
    <c:param name="fdModelName" value="${param.fdModelName}"/>
</c:import>
</body>
<script>
    if (window.share_group_opt == null) {
        window.share_group_opt = new shareGroupOpt();
    }
    share_group_opt.onload();

    // 选择圈子(使用旧版弹窗)
    // window.selectGroup = function() {
    //    $("#selGroup").html('<option value="" selected>选择圈子</option>');
    //    Dialog_Tree(false, "fdGroupId", null, null, "sysEvaluationShareService", "选择圈子", null, function (data) {
    //        if (!data) {
    //            return;
    //         }
    //        $("#selGroup").html('<option value="" selected>选中数据</option>');
    //    });
    // };

    //关联流程弹出框
    function selectGroup(obj) {
        seajs.use(["lui/dialog", 'lang!sys-ui'], function (dialog, ui_lang) {
            var url = "/sys/evaluation/sys_evaluation_share/import/selectGroup.jsp?mulSelect=false";

            dialog.iframe(url,
                '${lfn:message("sys-evaluation:sysEvaluationShare.selectGroup")}',
                null, {
                    width: 900,
                    height: 550,
                    buttons: [
                        {
                            name: ui_lang['ui.dialog.button.ok'],
                            value: true,
                            focus: true,
                            fn: function (value, _dialog) {
                                if (_dialog.frame && _dialog.frame.length > 0) {
                                    var _frame = _dialog.frame[0];
                                    var contentWindow = $(_frame).find("iframe")[0].contentWindow;
                                    if (contentWindow.onSubmit()) {
                                        var datas = contentWindow.onSubmit().slice(0);
                                        if (datas.length > 0) {
                                            selctGroupCb(datas);
                                            _dialog.hide(value);
                                        }
                                    }
                                }
                            }
                        },
                        {
                            name: ui_lang['ui.dialog.button.cancel'],
                            value: false,
                            styleClass: 'lui_toolbar_btn_gray',
                            fn: function (value, dialog) {
                                dialog.hide(value);
                            }
                        }]
                });
        });

        // 系统选择回调
        var selctGroupCb = function (data) {
            if(data.length>0){
                var item = data[0];
                $("#fdGroupId").val(item.fdId);
                $("#selGroup").val(item.docSubject);
            }
        }
    }
</script>