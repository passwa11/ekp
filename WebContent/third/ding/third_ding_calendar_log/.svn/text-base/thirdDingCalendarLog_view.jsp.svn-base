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
            <c:out value="${thirdDingCalendarLogForm.fdEkpCalendarId} - " />
            <c:out value="${ lfn:message('third-ding:table.thirdDingCalendarLog') }" />
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
                    basePath: '/third/ding/third_ding_calendar_log/thirdDingCalendarLog.do',
                    customOpts: {

                        ____fork__: 0
                    }
                };

                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
            </script>
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
                <!--delete
                <kmss:auth requestURL="/third/ding/third_ding_calendar_log/thirdDingCalendarLog.do?method=delete&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('thirdDingCalendarLog.do?method=delete&fdId=${param.fdId}');" order="4" />
                </kmss:auth>
                -->
                <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />


            </ui:toolbar>
        </template:replace>
        <template:replace name="content">
                <ui:content title="${ lfn:message('third-ding:py.JiBenXinXi') }" expand="true">
                    <div class='lui_form_title_frame'>
                        <div class='lui_form_subject'>
                                ${lfn:message('third-ding:table.thirdDingCalendarLog')}
                        </div>
                        <div class='lui_form_baseinfo'>

                        </div>
                    </div>
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingCalendarLog.fdName')}
                            </td>
                            <td width="35%">
                                <%-- 日程标题--%>
                                <div id="_xform_fdName" _xform_type="text">
                                    <xform:text property="fdName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingCalendarLog.fdOptType')}
                            </td>
                            <td width="35%">
                                <%-- 操作--%>
                                <div id="_xform_fdOptType" _xform_type="radio">
                                    <xform:radio property="fdOptType" htmlElementProperties="id='fdOptType'" showStatus="view">
                                        <xform:enumsDataSource enumsType="third_ding_calendar_opt" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingCalendarLog.fdEkpCalendarId')}
                            </td>
                            <td width="35%">
                                <%-- EKP日程ID--%>
                                <div id="_xform_fdEkpCalendarId" _xform_type="text">
                                    <xform:text property="fdEkpCalendarId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingCalendarLog.fdDingCalendarId')}
                            </td>
                            <td width="35%">
                                <%-- 钉钉日程ID--%>
                                <div id="_xform_fdDingCalendarId" _xform_type="text">
                                    <xform:text property="fdDingCalendarId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingCalendarLog.fdSynWay')}
                            </td>
                            <td width="35%">
                                <%-- 同步方向--%>
                                <div id="_xform_fdSynWay" _xform_type="text">
                                    <xform:radio property="fdSynWay" htmlElementProperties="id='fdSynWay'" showStatus="view">
                                        <xform:enumsDataSource enumsType="third_ding_syn_way" />
                                    </xform:radio>
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingCalendarLog.fdStatus')}
                            </td>
                            <td width="35%">
                                <%-- 同步状态--%>
                                <div id="_xform_fdStatus" _xform_type="radio">
                                    <xform:radio property="fdStatus" htmlElementProperties="id='fdStatus'" showStatus="view">
                                        <xform:enumsDataSource enumsType="common_yesno" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingCalendarLog.fdApiUrl')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 请求地址--%>
                                <div id="_xform_fdApiUrl" _xform_type="text">
                                    <xform:text property="fdApiUrl" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingCalendarLog.fdReqParam')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 请求报文--%>
                                <div id="_xform_fdReqParam" _xform_type="text">
                                    <xform:text property="fdReqParam" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingCalendarLog.fdResult')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 响应报文--%>
                                <div id="_xform_fdResult" _xform_type="text">
                                    <xform:text property="fdResult" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingCalendarLog.fdErrorMsg')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 错误信息--%>
                                <div id="_xform_fdErrorMsg" _xform_type="text">
                                    <xform:text property="fdErrorMsg" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingCalendarLog.fdReqStartTime')}
                            </td>
                            <td width="35%">
                                <%-- 请求开始时间--%>
                                <div id="_xform_fdReqStartTime" _xform_type="datetime">
                                    <xform:datetime property="fdReqStartTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingCalendarLog.fdResponseStartTime')}
                            </td>
                            <td width="35%">
                                <%-- 请求结束时间--%>
                                <div id="_xform_fdResponseStartTime" _xform_type="datetime">
                                    <xform:datetime property="fdResponseStartTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                    </table>
                </ui:content>
        </template:replace>

    </template:include>