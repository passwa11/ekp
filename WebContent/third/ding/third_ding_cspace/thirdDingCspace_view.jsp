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
            <c:out value="${thirdDingCspaceForm.fdMediaId} - " />
            <c:out value="${ lfn:message('third-ding:table.thirdDingCspace') }" />
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
                    basePath: '/third/ding/third_ding_cspace/thirdDingCspace.do',
                    customOpts: {

                        ____fork__: 0
                    }
                };

                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
            </script>
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">

                <!--delete-->
                <kmss:auth requestURL="/third/ding/third_ding_cspace/thirdDingCspace.do?method=delete&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('thirdDingCspace.do?method=delete&fdId=${param.fdId}');" order="4" />
                </kmss:auth>
                <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />


            </ui:toolbar>
        </template:replace>
        <template:replace name="content">

                <ui:content title="${ lfn:message('third-ding:') }" expand="true">
                    <div class='lui_form_title_frame'>
                        <div class='lui_form_subject'>
                            ${lfn:message('third-ding:table.thirdDingCspace')}
                        </div>
                        <div class='lui_form_baseinfo'>

                        </div>
                    </div>
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingCspace.fdName')}
                            </td>
                            <td width="35%">
                                <%-- 名称--%>
                                <div id="_xform_fdName" _xform_type="text">
                                    <xform:text property="fdName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingCspace.fdStatus')}
                            </td>
                            <td width="35%">
                                <%-- 附件状态--%>
                                <div id="_xform_fdStatus" _xform_type="radio">
                                    <xform:radio property="fdStatus" htmlElementProperties="id='fdStatus'" showStatus="view">
                                        <xform:enumsDataSource enumsType="third_ding_cspace_status" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingCspace.fdFileId')}
                            </td>
                            <td width="35%">
                                <%-- FileId--%>
                                <div id="_xform_fdFileId" _xform_type="text">
                                     <xform:text property="fdFileId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingCspace.fdAttId')}
                            </td>
                            <td width="35%">
                                <%-- 附件--%>
                                <div id="_xform_fdAttId" _xform_type="text">
                                    <xform:text property="fdAttId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingCspace.docAlterTime')}
                            </td>
                            <td width="35%">
                                <%-- 更新时间--%>
                                <div id="_xform_docAlterTime" _xform_type="datetime">
                                    <xform:datetime property="docAlterTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingCspace.docCreateTime')}
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
	                                ${lfn:message('third-ding:thirdDingCspace.fdMediaId')}
	                            </td>
	                            <td width="85%" colspan="3">
	                                <%-- mediaId--%>
	                                <div id="_xform_fdMediaId" _xform_type="text">
	                                    <xform:text property="fdMediaId" showStatus="view" style="width:95%;" />
	                                </div>
                            </td>
                        
                        </tr>

                        <c:if test="${thirdDingCspaceForm.fdStatus =='0'}">
                            <tr>
                                <td class="td_normal_title" width="15%">
                                        ${lfn:message('third-ding:thirdDingCspace.fdErrorMsg')}
                                </td>
                                <td width="85%" colspan="3">
                                        <%-- mediaId--%>
                                    <div id="_xform_fdErrorMsg" _xform_type="text">
                                        <xform:text property="fdErrorMsg" showStatus="view" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                        </c:if>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingCspace.fdCreater')}
                            </td>
                            <td width="85%" colspan="3">
                                <%-- 操作者--%>
                                <div id="_xform_fdCreaterId" _xform_type="address">
                                    <xform:address propertyId="fdCreaterId" propertyName="fdCreaterName" orgType="ORG_TYPE_PERSON" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        
                    </table>
                </ui:content>
        </template:replace>

    </template:include>