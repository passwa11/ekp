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
    if("${thirdWeixinGroupChatForm.fdRoomName}" != "") {
        window.document.title = "${thirdWeixinGroupChatForm.fdRoomName} - ${ lfn:message('third-weixin:table.thirdWeixinGroupChat') }";
    }
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>
<div id="optBarDiv">

    <kmss:auth requestURL="/third/weixin/third_weixin_group_chat/thirdWeixinGroupChat.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('thirdWeixinGroupChat.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth>
    <kmss:auth requestURL="/third/weixin/third_weixin_group_chat/thirdWeixinGroupChat.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('thirdWeixinGroupChat.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('third-weixin:table.thirdWeixinGroupChat') }</p>
<center>

    <div style="width:95%;">
        <table class="tb_normal" width="100%">
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinGroupChat.fdRoomName')}
                </td>
                <td width="35%">
                    <%-- 群名称--%>
                    <div id="_xform_fdRoomName" _xform_type="text">
                        <xform:text property="fdRoomName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinGroupChat.fdRoomId')}
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
                    ${lfn:message('third-weixin:thirdWeixinGroupChat.fdRoomCreator')}
                </td>
                <td width="35%">
                    <%-- 群创建者--%>
                    <div id="_xform_fdRoomCreator" _xform_type="text">
                        <xform:text property="fdRoomCreator" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinGroupChat.fdRoomCreateTime')}
                </td>
                <td width="35%">
                    <%-- 群创建时间--%>
                    <div id="_xform_fdRoomCreateTime" _xform_type="text">
                        <xform:text property="fdRoomCreateTime" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinGroupChat.fdRoomNotice')}
                </td>
                <td width="35%">
                    <%-- 群公告--%>
                    <div id="_xform_fdRoomNotice" _xform_type="text">
                        <xform:text property="fdRoomNotice" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinGroupChat.fdOwnerFdid')}
                </td>
                <td width="35%">
                    <%-- 群主FdId--%>
                    <div id="_xform_fdOwnerFdid" _xform_type="text">
                        <xform:text property="fdOwnerFdid" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinGroupChat.fdCorpId')}
                </td>
                <td width="35%">
                    <%-- 所属组织--%>
                    <div id="_xform_fdCorpId" _xform_type="text">
                        <xform:text property="fdCorpId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinGroupChat.fdIsDissolve')}
                </td>
                <td width="35%">
                    <%-- 是否解散--%>
                    <div id="_xform_fdIsDissolve" _xform_type="radio">
                        <xform:radio property="fdIsDissolve" htmlElementProperties="id='fdIsDissolve'" showStatus="view">
                            <xform:enumsDataSource enumsType="common_yesno" />
                        </xform:radio>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinGroupChat.docCreateTime')}
                </td>
                <td width="35%">
                    <%-- 创建时间--%>
                    <div id="_xform_docCreateTime" _xform_type="datetime">
                        <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinGroupChat.docAlterTime')}
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
                    ${lfn:message('third-weixin:thirdWeixinGroupChat.fdMember')}
                </td>
                <td width="35%">
                    <%-- 群成员--%>
                    <div id="_xform_fdMemberIds" _xform_type="checkbox">
                        <xform:checkbox property="fdMemberIds" showStatus="view">
                            <xform:beanDataSource serviceBean="thirdWeixinAccountService" selectBlock="fdId,fdAccountId" />
                        </xform:checkbox>
                    </div>
                </td>
                <td colspan="2">
                </td>
            </tr>
        </table>
    </div>
    <input type="hidden" name="fdId" value="${thirdWeixinGroupChatForm.fdId}" />
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
        basePath: '/third/weixin/third_weixin_group_chat/thirdWeixinGroupChat.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>