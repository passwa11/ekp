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
    if("${thirdWeixinChatDataTempForm.fdMsgId}" != "") {
        window.document.title = "${thirdWeixinChatDataTempForm.fdMsgId} - ${ lfn:message('third-weixin:table.thirdWeixinChatDataTemp') }";
    }
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>
<div id="optBarDiv">

    <kmss:auth requestURL="/third/weixin/third_weixin_chat_data_temp/thirdWeixinChatDataTemp.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('thirdWeixinChatDataTemp.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth>
    <kmss:auth requestURL="/third/weixin/third_weixin_chat_data_temp/thirdWeixinChatDataTemp.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('thirdWeixinChatDataTemp.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('third-weixin:table.thirdWeixinChatDataTemp') }</p>
<center>

    <div style="width:95%;">
        <table class="tb_normal" width="100%">
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataTemp.fdSeq')}
                </td>
                <td width="35%">
                    <%-- 序号--%>
                    <div id="_xform_fdSeq" _xform_type="text">
                        <xform:text property="fdSeq" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataTemp.fdMsgId')}
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
                    ${lfn:message('third-weixin:thirdWeixinChatDataTemp.fdHandleStatus')}
                </td>
                <td width="35%">
                    <%-- 处理状态--%>
                    <div id="_xform_fdHandleStatus" _xform_type="select">
                        <xform:select property="fdHandleStatus" htmlElementProperties="id='fdHandleStatus'" showStatus="view">
                            <xform:enumsDataSource enumsType="third_weixin_chat_data_handle" />
                        </xform:select>
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataTemp.fdErrTimes')}
                </td>
                <td width="35%">
                    <%-- 错误次数--%>
                    <div id="_xform_fdErrTimes" _xform_type="text">
                        <xform:text property="fdErrTimes" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataTemp.fdPublickeyVer')}
                </td>
                <td width="35%">
                    <%-- 公钥版本--%>
                    <div id="_xform_fdPublickeyVer" _xform_type="text">
                        <xform:text property="fdPublickeyVer" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataTemp.fdEncryptRandomKey')}
                </td>
                <td width="35%">
                    <%-- 秘钥--%>
                    <div id="_xform_fdEncryptRandomKey" _xform_type="text">
                        <xform:text property="fdEncryptRandomKey" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataTemp.fdEncryptChatMsg')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 密文--%>
                    <div id="_xform_fdEncryptChatMsg" _xform_type="rtf">
                        <xform:rtf property="fdEncryptChatMsg" showStatus="view" width="95%" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataTemp.fdErrMsg')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 错误信息--%>
                    <div id="_xform_fdErrMsg" _xform_type="rtf">
                        <xform:rtf property="fdErrMsg" showStatus="view" width="95%" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataTemp.docCreateTime')}
                </td>
                <td width="35%">
                    <%-- 创建时间--%>
                    <div id="_xform_docCreateTime" _xform_type="datetime">
                        <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataTemp.docAlterTime')}
                </td>
                <td width="35%">
                    <%-- 更新时间--%>
                    <div id="_xform_docAlterTime" _xform_type="datetime">
                        <xform:datetime property="docAlterTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatDataTemp.fdCorpId')}
                </td>
                <td width="35%">
                    <%-- 组织ID--%>
                    <div id="_xform_fdCorpId" _xform_type="text">
                        <xform:text property="fdCorpId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td colspan="2">
                </td>
            </tr>
        </table>
    </div>
    <input type="hidden" name="fdId" value="${thirdWeixinChatDataTempForm.fdId}" />
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
        basePath: '/third/weixin/third_weixin_chat_data_temp/thirdWeixinChatDataTemp.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>