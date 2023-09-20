<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/view_top.jsp" %>
<style type="text/css">
    
    	.lui_paragraph_title{
    		font-size: 15px;
    		color: #15a4fa;
        	padding: 15px 0px 5px 0px;
    	}
    	.lui_paragraph_title span{
    		display: inline-block;
    		margin: -2px 5px 0px 0px;
    	}
    	.inputsgl[readonly], .tb_normal .inputsgl[readonly] {
      		border: 0px;
      		color: #868686
    	}
    
</style>
<script type="text/javascript">
    if("${thirdWeixinChatDataMainForm.fdMsgId}" != "") {
        window.document.title = "${thirdWeixinChatDataMainForm.fdMsgId} - ${ lfn:message('third-weixin:table.thirdWeixinChatDataMain') }";
    }
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>
<div id="optBarDiv">

    <kmss:auth requestURL="/third/weixin/third_weixin_chat_data_main/thirdWeixinChatDataMain.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('thirdWeixinChatDataMain.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth>
    <kmss:auth requestURL="/third/weixin/third_weixin_chat_data_main/thirdWeixinChatDataMain.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('thirdWeixinChatDataMain.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('third-weixin:table.thirdWeixinChatDataMain') }</p>
<center>

    <div style="width:95%;">
        <table class="tb_normal" width="100%">
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdSeq')}
                </td>
                <td width="35%">
                    <%-- 序号--%>
                    <div id="_xform_fdSeq" _xform_type="text">
                        <xform:text property="fdSeq" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdMsgId')}
                </td>
                <td width="35%">
                    <%-- 消息id--%>
                    <div id="_xform_fdMsgId" _xform_type="text">
                        <xform:text property="fdMsgId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdMsgType')}
                </td>
                <td width="35%">
                    <%-- 消息类型--%>
                    <div id="_xform_fdMsgType" _xform_type="text">
                        <xform:text property="fdMsgType" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdMsgAction')}
                </td>
                <td width="35%">
                    <%-- 消息动作--%>
                    <div id="_xform_fdMsgAction" _xform_type="text">
                        <xform:text property="fdMsgAction" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdRoomId')}
                </td>
                <td width="35%">
                    <%-- 群ID--%>
                    <div id="_xform_fdRoomId" _xform_type="text">
                        <xform:text property="fdRoomId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdEncryType')}
                </td>
                <td width="35%">
                    <%-- 加密类型--%>
                    <div id="_xform_fdEncryType" _xform_type="select">
                        <xform:select property="fdEncryType" htmlElementProperties="id='fdEncryType'" showStatus="view">
                            <xform:enumsDataSource enumsType="third_weixin_encry_type" />
                        </xform:select>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdFrom')}
                </td>
                <td width="35%">
                    <%-- 发送方id--%>
                    <div id="_xform_fdFrom" _xform_type="text">
                        <xform:text property="fdFrom" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdFromName')}
                </td>
                <td width="35%">
                    <%-- 发送人名称--%>
                    <div id="_xform_fdFromName" _xform_type="text">
                        <xform:text property="fdFromName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdMsgTime')}
                </td>
                <td width="35%">
                    <%-- 发送时间戳--%>
                    <div id="_xform_fdMsgTime" _xform_type="text">
                        <xform:text property="fdMsgTime" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td colspan="2" width="50.0%">
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdToList')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 接收方列表--%>
                    <div id="_xform_fdToList" _xform_type="rtf">
                        <xform:rtf property="fdToList" showStatus="view" width="95%" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdContent')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 消息内容--%>
                    <div id="_xform_fdContent" _xform_type="rtf">
                        <xform:textarea property="fdContent" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdExtendContent')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 扩展信息--%>
                    <div id="_xform_fdExtendContent" _xform_type="rtf">
                        <xform:textarea property="fdExtendContent" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdFileId')}
                </td>
                <td width="35%">
                    <%-- 文件ID--%>
                    <div id="_xform_fdFileId" _xform_type="text">
                        <xform:text property="fdFileId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdFileMd5')}
                </td>
                <td width="35%">
                    <%-- 文件MD5--%>
                    <div id="_xform_fdFileMd5" _xform_type="text">
                        <xform:text property="fdFileMd5" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdFileSize')}
                </td>
                <td width="35%">
                    <%-- 文件大小--%>
                    <div id="_xform_fdFileSize" _xform_type="text">
                        <xform:text property="fdFileSize" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdPreMsgId')}
                </td>
                <td width="35%">
                    <%-- 撤回消息的ID--%>
                    <div id="_xform_fdPreMsgId" _xform_type="text">
                        <xform:text property="fdPreMsgId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdAgreeUserid')}
                </td>
                <td width="35%">
                    <%-- 同意会话用户ID--%>
                    <div id="_xform_fdAgreeUserid" _xform_type="text">
                        <xform:text property="fdAgreeUserid" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdAgreeTime')}
                </td>
                <td width="35%">
                    <%-- 同意会话时间--%>
                    <div id="_xform_fdAgreeTime" _xform_type="text">
                        <xform:text property="fdAgreeTime" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdPlayLength')}
                </td>
                <td width="35%">
                    <%-- 播放长度--%>
                    <div id="_xform_fdPlayLength" _xform_type="text">
                        <xform:text property="fdPlayLength" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdCorpName')}
                </td>
                <td width="35%">
                    <%-- 企业名称--%>
                    <div id="_xform_fdCorpName" _xform_type="text">
                        <xform:text property="fdCorpName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdUserId')}
                </td>
                <td width="35%">
                    <%-- 用户ID--%>
                    <div id="_xform_fdUserId" _xform_type="text">
                        <xform:text property="fdUserId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdLongitude')}
                </td>
                <td width="35%">
                    <%-- 经度--%>
                    <div id="_xform_fdLongitude" _xform_type="text">
                        <xform:text property="fdLongitude" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdLatitude')}
                </td>
                <td width="35%">
                    <%-- 纬度--%>
                    <div id="_xform_fdLatitude" _xform_type="text">
                        <xform:text property="fdLatitude" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdAddress')}
                </td>
                <td width="35%">
                    <%-- 地址信息--%>
                    <div id="_xform_fdAddress" _xform_type="text">
                        <xform:text property="fdAddress" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdTitle')}
                </td>
                <td width="35%">
                    <%-- 标题（名称）--%>
                    <div id="_xform_fdTitle" _xform_type="text">
                        <xform:text property="fdTitle" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdZoom')}
                </td>
                <td width="35%">
                    <%-- 缩放比例--%>
                    <div id="_xform_fdZoom" _xform_type="text">
                        <xform:text property="fdZoom" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdEmotionType')}
                </td>
                <td width="35%">
                    <%-- 表情类型--%>
                    <div id="_xform_fdEmotionType" _xform_type="text">
                        <xform:text property="fdEmotionType" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdWidth')}
                </td>
                <td width="35%">
                    <%-- 宽度--%>
                    <div id="_xform_fdWidth" _xform_type="text">
                        <xform:text property="fdWidth" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdHeight')}
                </td>
                <td width="35%">
                    <%-- 高度--%>
                    <div id="_xform_fdHeight" _xform_type="text">
                        <xform:text property="fdHeight" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdTo')}
                </td>
                <td width="35%">
                    <%-- 接收方ID--%>
                    <div id="_xform_fdTo" _xform_type="text">
                        <xform:text property="fdTo" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdFileExt')}
                </td>
                <td width="35%">
                    <%-- 文件类型后缀--%>
                    <div id="_xform_fdFileExt" _xform_type="text">
                        <xform:text property="fdFileExt" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdLinkUrl')}
                </td>
                <td width="35%">
                    <%-- 链接--%>
                    <div id="_xform_fdLinkUrl" _xform_type="text">
                        <xform:text property="fdLinkUrl" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdImageUrl')}
                </td>
                <td width="35%">
                    <%-- 链接图片--%>
                    <div id="_xform_fdImageUrl" _xform_type="text">
                        <xform:text property="fdImageUrl" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdUsername')}
                </td>
                <td width="35%">
                    <%-- 用户名称--%>
                    <div id="_xform_fdUsername" _xform_type="text">
                        <xform:text property="fdUsername" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdDisplayName')}
                </td>
                <td width="35%">
                    <%-- 小程序名称--%>
                    <div id="_xform_fdDisplayName" _xform_type="text">
                        <xform:text property="fdDisplayName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdVoteType')}
                </td>
                <td width="35%">
                    <%-- 投票类型--%>
                    <div id="_xform_fdVoteType" _xform_type="text">
                        <xform:text property="fdVoteType" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdVoteId')}
                </td>
                <td width="35%">
                    <%-- 投票ID--%>
                    <div id="_xform_fdVoteId" _xform_type="text">
                        <xform:text property="fdVoteId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdMeetingId')}
                </td>
                <td width="35%">
                    <%-- 会议ID--%>
                    <div id="_xform_fdMeetingId" _xform_type="text">
                        <xform:text property="fdMeetingId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.docCreateTime')}
                </td>
                <td width="35%">
                    <%-- 创建时间--%>
                    <div id="_xform_docCreateTime" _xform_type="datetime">
                        <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdCorpId')}
                </td>
                <td width="35%">
                    <%-- 组织ID--%>
                    <div id="_xform_fdCorpId" _xform_type="text">
                        <xform:text property="fdCorpId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataMain.fdChatGroup')}
                </td>
                <td width="35%">
                    <%-- 所属会话分组--%>
                    <div id="_xform_fdChatGroupId" _xform_type="radio">
                        <xform:radio property="fdChatGroupId" htmlElementProperties="id='fdChatGroupId'" showStatus="view">
                            <xform:beanDataSource serviceBean="thirdWeixinChatGroupService" selectBlock="fdId,fdRelateUserId" />
                        </xform:radio>
                    </div>
                </td>
                <td colspan="2">
                </td>
            </tr>
        </table>
    </div>
    <input type="hidden" name="fdId" value="${thirdWeixinChatDataMainForm.fdId}" />
</center>
<script>
    var formInitData = {

    };

    function confirmDelete(msg) {
        return confirm('${ lfn:message("page.comfirmDelete") }');
    }

    function openWindowViaDynamicForm(popurl, params, target) {
        var form = document.createElement('form');
        if(form) {
            try {
                target = !target ? '_blank' : target;
                form.style = "display:none;";
                form.method = 'post';
                form.action = popurl;
                form.target = target;
                if(params) {
                    for(var key in params) {
                        var
                        v = params[key];
                        var vt = typeof
                        v;
                        var hdn = document.createElement('input');
                        hdn.type = 'hidden';
                        hdn.name = key;
                        if(vt == 'string' || vt == 'boolean' || vt == 'number') {
                            hdn.value =
                            v +'';
                        } else {
                            if($.isArray(
                                v)) {
                                hdn.value =
                                v.join(';');
                            } else {
                                hdn.value = toString(
                                    v);
                            }
                        }
                        form.appendChild(hdn);
                    }
                }
                document.body.appendChild(form);
                form.submit();
            } finally {
                document.body.removeChild(form);
            }
        }
    }

    function doCustomOpt(fdId, optCode) {
        if(!fdId || !optCode) {
            return;
        }

        if(viewOption.customOpts && viewOption.customOpts[optCode]) {
            var param = {
                "List_Selected_Count": 1
            };
            var argsObject = viewOption.customOpts[optCode];
            if(argsObject.popup == 'true') {
                var popurl = viewOption.contextPath + argsObject.popupUrl + '&fdId=' + fdId;
                for(var arg in argsObject) {
                    param[arg] = argsObject[arg];
                }
                openWindowViaDynamicForm(popurl, param, '_self');
                return;
            }
            var optAction = viewOption.contextPath + viewOption.basePath + '?method=' + optCode + '&fdId=' + fdId;
            Com_OpenWindow(optAction, '_self');
        }
    }
    window.doCustomOpt = doCustomOpt;
    var viewOption = {
        contextPath: '${LUI_ContextPath}',
        basePath: '/third/weixin/third_weixin_chat_data_main/thirdWeixinChatDataMain.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>