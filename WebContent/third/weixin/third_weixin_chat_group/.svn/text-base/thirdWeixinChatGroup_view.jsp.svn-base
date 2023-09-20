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
    if("${thirdWeixinChatGroupForm.fdRelateUserId}" != "") {
        window.document.title = "${thirdWeixinChatGroupForm.fdRelateUserId} - ${ lfn:message('third-weixin:table.thirdWeixinChatGroup') }";
    }
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>
<div id="optBarDiv">

    <kmss:auth requestURL="/third/weixin/third_weixin_chat_group/thirdWeixinChatGroup.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('thirdWeixinChatGroup.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth>
    <kmss:auth requestURL="/third/weixin/third_weixin_chat_group/thirdWeixinChatGroup.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('thirdWeixinChatGroup.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('third-weixin:table.thirdWeixinChatGroup') }</p>
<center>

    <div style="width:95%;">
        <table class="tb_normal" width="100%">
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatGroup.fdRelateUserId')}
                </td>
                <td width="35%">
                    <%-- 相关人ID--%>
                    <div id="_xform_fdRelateUserId" _xform_type="text">
                        <xform:text property="fdRelateUserId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatGroup.fdRoomId')}
                </td>
                <td width="35%">
                    <%-- 群ID--%>
                    <div id="_xform_fdRoomId" _xform_type="text">
                        <xform:text property="fdRoomId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatGroup.fdChatGroupName')}
                </td>
                <td width="35%">
                    <%-- 分组名称--%>
                    <div id="_xform_fdChatGroupName" _xform_type="text">
                        <xform:text property="fdChatGroupName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatGroup.fdMd5')}
                </td>
                <td width="35%">
                    <%-- MD5--%>
                    <div id="_xform_fdMd5" _xform_type="text">
                        <xform:text property="fdMd5" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatGroup.fdIsOut')}
                </td>
                <td width="35%">
                    <%-- 是否外部--%>
                    <div id="_xform_fdIsOut" _xform_type="radio">
                        <xform:radio property="fdIsOut" htmlElementProperties="id='fdIsOut'" showStatus="view">
                            <xform:enumsDataSource enumsType="common_yesno" />
                        </xform:radio>
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatGroup.fdNewestMsgId')}
                </td>
                <td width="35%">
                    <%-- 最新消息ID--%>
                    <div id="_xform_fdNewestMsgId" _xform_type="text">
                        <xform:text property="fdNewestMsgId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatGroup.fdNewestMsgTime')}
                </td>
                <td width="35%">
                    <%-- 最新消息时间--%>
                    <div id="_xform_fdNewestMsgTime" _xform_type="text">
                        <xform:text property="fdNewestMsgTime" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatGroup.docCreateTime')}
                </td>
                <td width="35%">
                    <%-- 创建时间--%>
                    <div id="_xform_docCreateTime" _xform_type="datetime">
                        <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinChatGroup.docAlterTime')}
                </td>
                <td width="35%">
                    <%-- 更新时间--%>
                    <div id="_xform_docAlterTime" _xform_type="datetime">
                        <xform:datetime property="docAlterTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                    </div>
                </td>
                <td colspan="2">
                </td>
            </tr>
        </table>
    </div>
    <input type="hidden" name="fdId" value="${thirdWeixinChatGroupForm.fdId}" />
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
        basePath: '/third/weixin/third_weixin_chat_group/thirdWeixinChatGroup.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>