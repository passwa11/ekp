<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.third.ding.util.ThirdDingUtil" %>
    
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
            <c:out value="${thirdDingNotifyQueueErrorForm.fdSubject} - " />
            <c:out value="${ lfn:message('third-ding-notify:table.thirdDingNotifyQueueError') }" />
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
                    basePath: '/third/ding/third_ding_notify_queue_error/thirdDingNotifyQueueError.do',
                    customOpts: {

                        ____fork__: 0
                    }
                };

                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
            </script>
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">

                <!--delete-->
                <kmss:auth requestURL="/third/ding/third_ding_notify_queue_error/thirdDingNotifyQueueError.do?method=delete&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('thirdDingNotifyQueueError.do?method=delete&fdId=${param.fdId}');" order="4" />
                </kmss:auth>
                <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />


            </ui:toolbar>
        </template:replace>
        <template:replace name="path">
        	<%-- 
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self" />
                <ui:menu-item text="${ lfn:message('third-ding-notify:table.thirdDingNotifyQueueError') }" href="/third/ding/third_ding_notify_queue_error/" target="_self" />
            </ui:menu>
            --%>
        </template:replace>
        <template:replace name="content">
			<%-- 
            <ui:tabpage expand="false" var-navwidth="90%">
            --%>
                <ui:content title="${ lfn:message('third-ding-notify:py.JiBenXinXi') }" expand="true">
                    <div class='lui_form_title_frame'>
                        <div class='lui_form_subject'>
                            ${lfn:message('third-ding-notify:table.thirdDingNotifyQueueError')}
                        </div>
                        <div class='lui_form_baseinfo'>

                        </div>
                    </div>
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding-notify:thirdDingNotifyQueueError.fdSubject')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 待办标题--%>
                                <div id="_xform_fdSubject" _xform_type="text">
                                    <xform:text property="fdSubject" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding-notify:thirdDingNotifyQueueError.fdTodoId')}
                            </td>
                            <td width="35%">
                                <%-- 待办ID--%>
                                <div id="_xform_fdTodoId" _xform_type="text">
                                    <xform:text property="fdTodoId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding-notify:thirdDingNotifyQueueError.fdMd5')}
                            </td>
                            <td width="35%">
                                <%-- MD5值--%>
                                <div id="_xform_fdMd5" _xform_type="text">
                                    <xform:text property="fdMd5" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding-notify:thirdDingNotifyQueueError.fdAppName')}
                            </td>
                            <td width="35%">
                                <%-- 所属应用--%>
                                <div id="_xform_fdAppName" _xform_type="text">
                                    <xform:text property="fdAppName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding-notify:thirdDingNotifyQueueError.fdFlag')}
                            </td>
                            <td width="35%">
                                <%-- 处理标识--%>
                                <div id="_xform_fdFlag" _xform_type="text">
                                    <xform:text property="fdFlag" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding-notify:thirdDingNotifyQueueError.fdDingUserId')}
                            </td>
                            <td width="35%">
                                <%-- 钉钉用户ID--%>
                                <div id="_xform_fdDingUserId" _xform_type="text">
                                    <xform:text property="fdDingUserId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding-notify:thirdDingNotifyQueueError.fdCreateTime')}
                            </td>
                            <td width="35%">
                                <%-- 创建时间--%>
                                <div id="_xform_fdCreateTime" _xform_type="datetime">
                                    <xform:datetime property="fdCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding-notify:thirdDingNotifyQueueError.fdSendTime')}
                            </td>
                            <td width="35%">
                                <%-- 发送时间--%>
                                <div id="_xform_fdSendTime" _xform_type="datetime">
                                    <xform:datetime property="fdSendTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding-notify:thirdDingNotifyQueueError.fdMethod')}
                            </td>
                            <td width="35%">
                                <%-- 推送方法--%>
                                <div id="_xform_fdMethod" _xform_type="text">
                                    <xform:text property="fdMethod" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding-notify:thirdDingNotifyQueueError.fdRepeatHandle')}
                            </td>
                            <td width="35%">
                                <%-- 重复处理次数--%>
                                <div id="_xform_fdRepeatHandle" _xform_type="text">
                                    <xform:text property="fdRepeatHandle" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td colspan="2" width="50.0%">
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding-notify:thirdDingNotifyQueueError.fdJson')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 消息内容--%>
                                <div id="_xform_fdJson" _xform_type="textarea">
                                    <xform:textarea property="fdJson" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding-notify:thirdDingNotifyQueueError.fdErrorMsg')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 错误信息--%>
                                <div id="_xform_fdErrorMsg" _xform_type="text">
                                    <xform:text property="fdErrorMsg" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                    </table>
                </ui:content>
                <%-- 
            </ui:tabpage>
            --%>
        </template:replace>

    </template:include>