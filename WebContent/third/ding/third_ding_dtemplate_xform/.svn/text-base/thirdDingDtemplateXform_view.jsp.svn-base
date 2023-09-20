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

                    'fdDetail': '${lfn:escapeJs(lfn:message("third-ding:table.thirdDingTemplateXDetail"))}'
                };
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            </script>
        </template:replace>
        <template:replace name="title">
            <c:out value="${thirdDingDtemplateXformForm.fdName} - " />
            <c:out value="${ lfn:message('third-ding:table.thirdDingDtemplateXform') }" />
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
                    basePath: '/third/ding/third_ding_dtemplate_xform/thirdDingDtemplateXform.do',
                    customOpts: {

                        ____fork__: 0
                    }
                };

                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
            </script>
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">

               <%--  <!--edit-->
                <kmss:auth requestURL="/third/ding/third_ding_dtemplate_xform/thirdDingDtemplateXform.do?method=edit&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('thirdDingDtemplateXform.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
                </kmss:auth>
                <!--delete-->
                <kmss:auth requestURL="/third/ding/third_ding_dtemplate_xform/thirdDingDtemplateXform.do?method=delete&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('thirdDingDtemplateXform.do?method=delete&fdId=${param.fdId}');" order="4" />
                </kmss:auth> --%>
                <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />


            </ui:toolbar>
        </template:replace>
       
        <template:replace name="content">

          
                <ui:content title="${ lfn:message('third-ding:') }" expand="true">
                    <div class='lui_form_title_frame'>
                        <div class='lui_form_subject'>
                            ${lfn:message('third-ding:table.thirdDingDtemplateXform')}
                        </div>
                        <div class='lui_form_baseinfo'>

                        </div>
                    </div>
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingDtemplateXform.fdName')}
                            </td>
                            <td width="35%">
                                <%-- 名称--%>
                                <div id="_xform_fdName" _xform_type="text">
                                    <xform:text property="fdName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingDtemplateXform.fdIsAvailable')}
                            </td>
                            <td width="35%">
                                <%-- 是否有效--%>
                                <div id="_xform_fdIsAvailable" _xform_type="radio">
                                    <xform:radio property="fdIsAvailable" htmlElementProperties="id='fdIsAvailable'" showStatus="view">
                                        <xform:enumsDataSource enumsType="common_yesno" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingDtemplateXform.fdProcessCode')}
                            </td>
                            <td width="35%">
                                <%-- 模板code--%>
                                <div id="_xform_fdProcessCode" _xform_type="text">
                                    <xform:text property="fdProcessCode" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingDtemplateXform.fdAgentId')}
                            </td>
                            <td width="35%">
                                <%-- 应用ID--%>
                                <div id="_xform_fdAgentId" _xform_type="text">
                                    <xform:text property="fdAgentId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingDtemplateXform.fdType')}
                            </td>
                            <td width="35%">
                                <%-- 类型--%>
                                <div id="_xform_fdType" _xform_type="text">
                                    <xform:text property="fdType" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                            		   流程模板
                            </td>
                            <td width="35%">
                                <%-- 非流程模板--%>
                                <div id="_xform_fdFlow" _xform_type="radio">
                                    <%-- <xform:radio property="fdFlow" htmlElementProperties="id='fdFlow'" showStatus="view">
                                        <xform:enumsDataSource enumsType="third_ding_dtemplate_xform_flow" />
                                    </xform:radio> --%>
                                    <xform:select property="fdFlow" htmlElementProperties="id='fdFlow'" showStatus="view">
                                                        <xform:enumsDataSource enumsType="third_ding_dtemplate_xform_flow" />
                                                    </xform:select>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingDtemplateXform.fdDisableFormEdit')}
                            </td>
                            <td width="35%">
                                <%-- 不可编辑表单--%>
                                <div id="_xform_fdDisableFormEdit" _xform_type="radio">
                                    <xform:radio property="fdDisableFormEdit" htmlElementProperties="id='fdDisableFormEdit'" showStatus="view">
                                        <xform:enumsDataSource enumsType="third_ding_dtemplate_yes_no" />
                                    </xform:radio>
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingDtemplateXform.fdCorpId')}
                            </td>
                            <td width="35%">
                                <%-- CorpId--%>
                                <div id="_xform_fdCorpId" _xform_type="text">
                                    <xform:text property="fdCorpId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingDtemplateXform.docCreateTime')}
                            </td>
                            <td width="35%">
                                <%-- 创建时间--%>
                                <div id="_xform_docCreateTime" _xform_type="datetime">
                                    <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingDtemplateXform.fdTemplateId')}
                            </td>
                            <td width="35%">
                                <%-- 流程模板ID--%>
                                <div id="_xform_fdTemplateId" _xform_type="text">
                                    <xform:text property="fdTemplateId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingDtemplateXform.fdDirid')}
                            </td>
                            <td width="35%">
                                <%-- 分组Id--%>
                                <div id="_xform_fdDirid" _xform_type="text">
                                    <xform:text property="fdDirid" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingDtemplateXform.fdOriginDirid')}
                            </td>
                            <td width="35%">
                                <%-- 原分组id--%>
                                <div id="_xform_fdOriginDirid" _xform_type="text">
                                    <xform:text property="fdOriginDirid" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingDtemplateXform.fdIcon')}
                            </td>
                            <td width="35%">
                                <%-- 图标--%>
                                <div id="_xform_fdIcon" _xform_type="text">
                                    <xform:text property="fdIcon" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td colspan="2" width="50.0%">
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingDtemplateXform.fdPcUrl')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- pc地址--%>
                                <div id="_xform_fdPcUrl" _xform_type="text">
                                    <xform:text property="fdPcUrl" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingDtemplateXform.fdMobileUrl')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 移动地址--%>
                                <div id="_xform_fdMobileUrl" _xform_type="text">
                                    <xform:text property="fdMobileUrl" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingDtemplateXform.fdProcessConfig')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 流程配置--%>
                                <div id="_xform_fdProcessConfig" _xform_type="text">
                                    <xform:text property="fdProcessConfig" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                         		       模版可见者
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 流程配置--%>
                                <div id="_xform_fdAllReaders" _xform_type="text">
                                    <xform:text property="fdAllReaders" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingDtemplateXform.fdDesc')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 描述--%>
                                <div id="_xform_fdDesc" _xform_type="textarea">
                                    <xform:textarea property="fdDesc" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <c:if test="${not empty thirdDingDtemplateXformForm.fdErrMsg }">
                           <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingDtemplateXform.fdErrMsg')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 描述--%>
                                <div id="_xform_fdDesc" _xform_type="textarea">
                                    <xform:textarea property="fdErrMsg" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        </c:if>
                        
                       
                        <tr>
                            <td colspan="4" width="100%">
                                <table class="tb_normal" width="100%" id="TABLE_DocList_fdDetail_Form" align="center" tbdraggable="true">
                                    <tr align="center" class="tr_normal_title">
                                        <td style="width:40px;">
                                            ${lfn:message('page.serial')}
                                        </td>
                                        <td>
                                            ${lfn:message('third-ding:thirdDingTemplateXDetail.fdName')}
                                        </td>
                                        <td>
                                            ${lfn:message('third-ding:thirdDingTemplateXDetail.fdType')}
                                        </td>
                                    </tr>
                                    <c:forEach items="${thirdDingDtemplateXformForm.fdDetail_Form}" var="fdDetail_FormItem" varStatus="vstatus">
                                        <tr KMSS_IsContentRow="1" class="docListTr">
                                            <td class="docList" align="center">
                                                ${vstatus.index+1}
                                            </td>
                                            <td class="docList" align="center">
                                                <%-- 名称--%>
                                                <input type="hidden" name="fdDetail_Form[${vstatus.index}].fdId" value="${fdDetail_FormItem.fdId}" />
                                                <div id="_xform_fdDetail_Form[${vstatus.index}].fdName" _xform_type="text">
                                                    <xform:text property="fdDetail_Form[${vstatus.index}].fdName" showStatus="view" style="width:95%;" />
                                                </div>
                                            </td>
                                            <td class="docList" align="center">
                                                <%-- 类型--%>
                                                <div id="_xform_fdDetail_Form[${vstatus.index}].fdType" _xform_type="select">
                                                    <xform:select property="fdDetail_Form[${vstatus.index}].fdType" htmlElementProperties="id='fdDetail_Form[${vstatus.index}].fdType'" showStatus="view">
                                                        <xform:enumsDataSource enumsType="third_ding_field_type" />
                                                    </xform:select>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </td>
                        </tr>
                    </table>
                </ui:content>
          
        </template:replace>

    </template:include>