<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.third.welink.util.ThirdWelinkUtil" %>
    
        <%
            pageContext.setAttribute("currentPerson", UserUtil.getKMSSUser().getUserId());
        pageContext.setAttribute("currentPost", UserUtil.getKMSSUser().getPostIds());
        pageContext.setAttribute("currentDept", UserUtil.getKMSSUser().getDeptId());
        if(UserUtil.getUser().getFdParentOrg() != null) {
            pageContext.setAttribute("currentOrg", UserUtil.getUser().getFdParentOrg().getFdId());
        } else {
            pageContext.setAttribute("currentOrg", "");
        } %>
    
    <template:include ref="default.view">
        <template:replace name="head">
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
                var formInitData = {

                };
                var messageInfo = {

                };
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            </script>
        </template:replace>
        <template:replace name="title">
            <c:out value="${thirdWelinkNotifyQueueErrForm.fdSubject} - " />
            <c:out value="${ lfn:message('third-welink:table.thirdWelinkNotifyQueueErr') }" />
        </template:replace>
        <template:replace name="toolbar">
            <script>
                function deleteDoc(delUrl) {
                    seajs.use(['lui/dialog'], function(dialog) {
                        dialog.confirm('${ lfn:message("page.comfirmDelete") }', function(isOk) {
                            if(isOk) {
                                Com_OpenWindow(delUrl, '_self');
                            }
                        });
                    });
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
                    basePath: '/third/welink/third_welink_notify_queue_err/thirdWelinkNotifyQueueErr.do',
                    customOpts: {

                        ____fork__: 0
                    }
                };

                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
            </script>
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
                <!--delete-->
                <kmss:auth requestURL="/third/welink/third_welink_notify_queue_err/thirdWelinkNotifyQueueErr.do?method=delete&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('thirdWelinkNotifyQueueErr.do?method=delete&fdId=${param.fdId}');" order="4" />
                </kmss:auth>
                <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />


            </ui:toolbar>
        </template:replace>
        <template:replace name="path">
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self" />
                <ui:menu-item text="${ lfn:message('third-welink:table.thirdWelinkNotifyQueueErr') }" href="/third/welink/third_welink_notify_queue_err/" target="_self" />
            </ui:menu>
        </template:replace>
        <template:replace name="content">

                <ui:content title="${ lfn:message('third-welink:py.JiBenXinXi') }" expand="true">
                    <div class='lui_form_title_frame'>
                        <div class='lui_form_subject'>
                            ${lfn:message('third-welink:table.thirdWelinkNotifyQueueErr')}
                        </div>
                        <div class='lui_form_baseinfo'>

                        </div>
                    </div>
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-welink:thirdWelinkNotifyQueueErr.fdSubject')}
                            </td>
                            <td width="35%">
                                <%-- 标题--%>
                                <div id="_xform_fdSubject" _xform_type="text">
                                    <xform:text property="fdSubject" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-welink:thirdWelinkNotifyQueueErr.fdPerson')}
                            </td>
                            <td width="35%">
                                <%-- 所属用户--%>
                                <div id="_xform_fdPersonId" _xform_type="address">
                                    <xform:address propertyId="fdPersonId" propertyName="fdPersonName" orgType="ORG_TYPE_PERSON" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-welink:thirdWelinkNotifyQueueErr.fdNotifyId')}
                            </td>
                            <td width="35%">
                                <%-- 待办ID--%>
                                <div id="_xform_fdNotifyId" _xform_type="text">
                                    <xform:text property="fdNotifyId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-welink:thirdWelinkNotifyQueueErr.fdMd5')}
                            </td>
                            <td width="35%">
                                <%-- 待办MD5--%>
                                <div id="_xform_fdMd5" _xform_type="text">
                                    <xform:text property="fdMd5" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-welink:thirdWelinkNotifyQueueErr.fdMethod')}
                            </td>
                            <td width="35%">
                                <%-- 动作--%>
                                <div id="_xform_fdMethod" _xform_type="text">
                                    <xform:text property="fdMethod" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-welink:thirdWelinkNotifyQueueErr.fdWelinkUserId')}
                            </td>
                            <td width="35%">
                                <%-- welink用户ID--%>
                                <div id="_xform_fdWelinkUserId" _xform_type="text">
                                    <xform:text property="fdWelinkUserId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-welink:thirdWelinkNotifyQueueErr.fdSendType')}
                            </td>
                            <td width="35%">
                                <%-- 推送类型--%>
                                <div id="_xform_fdSendType" _xform_type="select">
                                    <xform:select property="fdSendType" htmlElementProperties="id='fdSendType'" showStatus="view">
                                        <xform:enumsDataSource enumsType="third_welink_notify_target" />
                                    </xform:select>
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-welink:thirdWelinkNotifyQueueErr.fdRepeatHandle')}
                            </td>
                            <td width="35%">
                                <%-- 重复处理次数--%>
                                <div id="_xform_fdRepeatHandle" _xform_type="text">
                                    <xform:text property="fdRepeatHandle" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-welink:thirdWelinkNotifyQueueErr.docCreateTime')}
                            </td>
                            <td width="35%">
                                <%-- 创建时间--%>
                                <div id="_xform_docCreateTime" _xform_type="datetime">
                                    <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-welink:thirdWelinkNotifyQueueErr.docAlterTime')}
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
                                ${lfn:message('third-welink:thirdWelinkNotifyQueueErr.fdData')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 消息内容--%>
                                <div id="_xform_fdData" _xform_type="rtf">
                                    <xform:rtf property="fdData" showStatus="view" width="95%" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-welink:thirdWelinkNotifyQueueErr.fdErrMsg')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 异常信息--%>
                                <div id="_xform_fdErrMsg" _xform_type="rtf">
                                    <xform:rtf property="fdErrMsg" showStatus="view" width="95%" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-welink:thirdWelinkNotifyQueueErr.fdFlag')}
                            </td>
                            <td width="35%">
                                <%-- 处理标识--%>
                                <div id="_xform_fdFlag" _xform_type="select">
                                    <xform:select property="fdFlag" htmlElementProperties="id='fdFlag'" showStatus="view">
                                        <xform:enumsDataSource enumsType="third_welink_handle_flag" />
                                    </xform:select>
                                </div>
                            </td>
                            <td colspan="2">
                            </td>
                        </tr>
                    </table>
                </ui:content>
        </template:replace>

    </template:include>