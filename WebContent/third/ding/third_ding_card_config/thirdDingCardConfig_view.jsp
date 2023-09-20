<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ page import="com.landray.kmss.util.UserUtil" %>
<%@ page import="com.landray.kmss.third.ding.forms.ThirdDingCardConfigForm" %>
<%@ page import="net.sf.json.JSONObject" %>
<%@ page import="net.sf.json.JSONArray" %>

<%
    pageContext.setAttribute("currentPerson", UserUtil.getKMSSUser().getUserId());
    pageContext.setAttribute("currentPost", UserUtil.getKMSSUser().getPostIds());
    pageContext.setAttribute("currentDept", UserUtil.getKMSSUser().getDeptId());
    if(UserUtil.getUser().getFdParentOrg() != null) {
        pageContext.setAttribute("currentOrg", UserUtil.getUser().getFdParentOrg().getFdId());
    } else {
        pageContext.setAttribute("currentOrg", "");
    }
    ThirdDingCardConfigForm thirdDingCardConfigForm= (ThirdDingCardConfigForm) request.getAttribute("thirdDingCardConfigForm");
    JSONObject fdDetailJSON = JSONObject.fromObject(thirdDingCardConfigForm.getFdConfig());
    request.setAttribute("configData", fdDetailJSON.getJSONArray("data"));
%>

    
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
            <c:out value="${thirdDingCardConfigForm.fdName} - " />
            <c:out value="${ lfn:message('third-ding:table.thirdDingCardConfig') }" />
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
                    basePath: '/third/ding/third_ding_card_config/thirdDingCardConfig.do',
                    customOpts: {

                        ____fork__: 0
                    }
                };

                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
            </script>
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">

                <!--edit-->
                <kmss:auth requestURL="/third/ding/third_ding_card_config/thirdDingCardConfig.do?method=edit&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('thirdDingCardConfig.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
                </kmss:auth>
                <!--delete-->
                <kmss:auth requestURL="/third/ding/third_ding_card_config/thirdDingCardConfig.do?method=delete&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('thirdDingCardConfig.do?method=delete&fdId=${param.fdId}');" order="4" />
                </kmss:auth>
                <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />


            </ui:toolbar>
        </template:replace>
        <template:replace name="content">
                    <div class='lui_form_title_frame'>
                        <div class='lui_form_subject'>
                            ${lfn:message('third-ding:table.thirdDingCardConfig')}
                        </div>
                        <div class='lui_form_baseinfo'>

                        </div>
                    </div>
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingCardConfig.fdName')}
                            </td>
                            <td width="35%">
                                <%-- 名称--%>
                                <div id="_xform_fdName" _xform_type="text">
                                    <xform:text property="fdName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingCardConfig.fdCardId')}
                            </td>
                            <td width="35%">
                                <%-- 卡片ID--%>
                                <div id="_xform_fdCardId" _xform_type="text">
                                    <xform:text property="fdCardId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingCardConfig.fdType')}
                            </td>
                            <td width="35%">
                                <%-- 卡片类型--%>
                                <div id="_xform_fdType" _xform_type="select">
                                    <xform:select property="fdType" htmlElementProperties="id='fdType'" showStatus="view">
                                        <xform:enumsDataSource enumsType="third_ding_card_type" />
                                    </xform:select>
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingCardConfig.fdStatus')}
                            </td>
                            <td width="35%">
                                <%-- 卡片状态--%>
                                <div id="_xform_fdStatus" _xform_type="radio">
                                    <xform:radio property="fdStatus" htmlElementProperties="id='fdStatus'" showStatus="view">
                                        <xform:enumsDataSource enumsType="third_ding_card_status" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingCardConfig.fdModelNameText')}
                            </td>
                            <td width="35%">
                                <%-- 所属模块名称--%>
                                <div id="_xform_fdModelNameText" _xform_type="text">
                                    <xform:text property="fdModelNameText" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingCardConfig.fdTemplateNameText')}
                            </td>
                            <td width="35%">
                                <%-- 表单模板名称--%>
                                <div id="_xform_fdTemplateName" _xform_type="text">
                                    <xform:text property="fdTemplateName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingCardConfig.docCreateTime')}
                            </td>
                            <td width="35%">
                                <%-- 创建时间--%>
                                <div id="_xform_docCreateTime" _xform_type="datetime">
                                    <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingCardConfig.docAlterTime')}
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
                                ${lfn:message('third-ding:thirdDingCardConfig.fdConfig')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 参数配置--%>
                                <span class="txtstrong">后台内置了一些参数：<br>
                                    公有数据(模板可直接使用)：pcUrl(pc端地址), mUrl(移动端地址), agreeText(肯定按钮的文案), refuseText(否定按钮的文案), displayText(状态按钮的文案)<br>
                                    私有数据(由业务传递)：canOperate(Y表示可出现审批按钮), showStatus(Y表示可出现状态按钮)<br>
                                </span>
                                <div>
                                    <table id="card_param_config" style="text-align: center; margin: 0px 0px;" class="tb_normal" width=100% >
                                        <tr>
                                            <td  style="width: 20%">钉钉卡片变量</td>
                                            <td  style="width: 60%">绑定EKP字段</td>
                                        </tr>
                                        <c:forEach items="${configData}" var="formItem" varStatus="vstatus">
                                            <tr>
                                                <td>${formItem.param}</td>
                                                <c:if test="${formItem.key=='$constant$'}">
                                                    <td>${formItem.constant}(常量)</td>
                                                </c:if>
                                                <c:if test="${formItem.key!='$constant$'}">
                                                    <td> ${formItem.name}</td>
                                                </c:if>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                            </td>
                        </tr>
                    </table>
        </template:replace>

    </template:include>