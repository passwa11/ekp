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
            <c:out value="${thirdWelinkNotifyLogForm.docSubject} - " />
            <c:out value="${ lfn:message('third-welink:table.thirdWelinkNotifyLog') }" />
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
                    basePath: '/third/welink/third_welink_notify_log/thirdWelinkNotifyLog.do',
                    customOpts: {

                        ____fork__: 0
                    }
                };

                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
            </script>
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">

                <!--edit
                <kmss:auth requestURL="/third/welink/third_welink_notify_log/thirdWelinkNotifyLog.do?method=edit&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('thirdWelinkNotifyLog.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
                </kmss:auth>
                -->
                <!--delete-->
                <kmss:auth requestURL="/third/welink/third_welink_notify_log/thirdWelinkNotifyLog.do?method=delete&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('thirdWelinkNotifyLog.do?method=delete&fdId=${param.fdId}');" order="4" />
                </kmss:auth>
                <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />


            </ui:toolbar>
        </template:replace>
        <template:replace name="path">
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self" />
                <ui:menu-item text="${ lfn:message('third-welink:table.thirdWelinkNotifyLog') }" href="/third/welink/third_welink_notify_log/" target="_self" />
            </ui:menu>
        </template:replace>
        <template:replace name="content">

                <ui:content title="${ lfn:message('third-welink:py.JiBenXinXi') }" expand="true">
                    <div class='lui_form_title_frame'>
                        <div class='lui_form_subject'>
                            ${lfn:message('third-welink:table.thirdWelinkNotifyLog')}
                        </div>
                        <div class='lui_form_baseinfo'>

                        </div>
                    </div>
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-welink:thirdWelinkNotifyLog.docSubject')}
                            </td>
                            <td width="35%">
                                <%-- 标题--%>
                                <div id="_xform_docSubject" _xform_type="text">
                                    <xform:text property="docSubject" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-welink:thirdWelinkNotifyLog.fdNotifyId')}
                            </td>
                            <td width="35%">
                                <%-- 待办ID--%>
                                <div id="_xform_fdNotifyId" _xform_type="text">
                                    <xform:text property="fdNotifyId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-welink:thirdWelinkNotifyLog.fdSendType')}
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
                                ${lfn:message('third-welink:thirdWelinkNotifyLog.fdMethod')}
                            </td>
                            <td width="35%">
                                <%-- 动作--%>
                                <div id="_xform_fdMethod" _xform_type="select">
                                    <xform:select property="fdMethod" htmlElementProperties="id='fdMethod'" showStatus="view">
                                        <xform:enumsDataSource enumsType="third_welink_notify_method" />
                                    </xform:select>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-welink:thirdWelinkNotifyLog.fdUser')}
                            </td>
                            <td width="35%">
                                <%-- 所属用户--%>
                                <div id="_xform_fdUserId" _xform_type="address">
                                    <xform:address propertyId="fdUserId" propertyName="fdUserName" orgType="ORG_TYPE_PERSON" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-welink:thirdWelinkNotifyLog.fdUrl')}
                            </td>
                            <td width="35%">
                                <%-- 请求地址--%>
                                <div id="_xform_fdUrl" _xform_type="text">
                                    <xform:text property="fdUrl" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-welink:thirdWelinkNotifyLog.fdResult')}
                            </td>
                            <td width="35%">
                                <%-- 请求结果--%>
                                <div id="_xform_fdResult" _xform_type="select">
                                    <xform:select property="fdResult" htmlElementProperties="id='fdResult'" showStatus="view">
                                        <xform:enumsDataSource enumsType="third_welink_result" />
                                    </xform:select>
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-welink:thirdWelinkNotifyLog.fdExpireTime')}
                            </td>
                            <td width="35%">
                                <%-- 请求耗时--%>
                                <div id="_xform_fdExpireTime" _xform_type="text">
                                    <xform:text property="fdExpireTime" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-welink:thirdWelinkNotifyLog.docCreateTime')}
                            </td>
                            <td width="35%">
                                <%-- 发送时间--%>
                                <div id="_xform_docCreateTime" _xform_type="datetime">
                                    <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-welink:thirdWelinkNotifyLog.fdResTime')}
                            </td>
                            <td width="35%">
                                <%-- 响应时间--%>
                                <div id="_xform_fdResTime" _xform_type="datetime">
                                    <xform:datetime property="fdResTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-welink:thirdWelinkNotifyLog.fdReqData')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 请求数据--%>
                                <div id="_xform_fdReqData" _xform_type="rtf">
                                    <c:out value="${thirdWelinkNotifyLogForm.fdReqData }" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-welink:thirdWelinkNotifyLog.fdResData')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 响应数据--%>
                                <div id="_xform_fdResData" _xform_type="rtf">
                                    <c:out value="${thirdWelinkNotifyLogForm.fdResData }" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-welink:thirdWelinkNotifyLog.fdErrMsg')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 错误信息--%>
                                <div id="_xform_fdErrMsg" _xform_type="rtf">
                                    <c:out value="${thirdWelinkNotifyLogForm.fdErrMsg }" />
                                </div>
                            </td>
                        </tr>
                    </table>
                </ui:content>
        </template:replace>

    </template:include>