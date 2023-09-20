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

                    'fdDetail': '${lfn:escapeJs(lfn:message("third-ding:table.thirdDingIndanceXDetail"))}'
                };
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            </script>
        </template:replace>
        <template:replace name="title">
            <c:out value="${thirdDingDinstanceXformForm.fdName} - " />
            <c:out value="${ lfn:message('third-ding:table.thirdDingDinstanceXform') }" />
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
                    basePath: '/third/ding/third_ding_dinstance_xform/thirdDingDinstanceXform.do',
                    customOpts: {

                        ____fork__: 0
                    }
                };

                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
            </script>
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">

               <%--  <!--edit-->
                <kmss:auth requestURL="/third/ding/third_ding_dinstance_xform/thirdDingDinstanceXform.do?method=edit&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('thirdDingDinstanceXform.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
                </kmss:auth>
                <!--delete-->
                <kmss:auth requestURL="/third/ding/third_ding_dinstance_xform/thirdDingDinstanceXform.do?method=delete&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('thirdDingDinstanceXform.do?method=delete&fdId=${param.fdId}');" order="4" />
                </kmss:auth> --%>
                <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />


            </ui:toolbar>
        </template:replace>
        <template:replace name="content">

                <ui:content title="${ lfn:message('third-ding:') }" expand="true">
                    <div class='lui_form_title_frame'>
                        <div class='lui_form_subject'>
                            ${lfn:message('third-ding:table.thirdDingDinstanceXform')}
                        </div>
                        <div class='lui_form_baseinfo'>

                        </div>
                    </div>
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingDinstanceXform.fdName')}
                            </td>
                            <td width="35%">
                                <%-- 名称--%>
                                <div id="_xform_fdName" _xform_type="text">
                                    <xform:text property="fdName" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingDinstanceXform.fdInstanceId')}
                            </td>
                            <td width="35%">
                                <%-- 实例Id--%>
                                <div id="_xform_fdInstanceId" _xform_type="text">
                                    <xform:text property="fdInstanceId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingDinstanceXform.fdDingUserId')}
                            </td>
                            <td width="35%">
                                <%-- 发起人dingId--%>
                                <div id="_xform_fdDingUserId" _xform_type="text">
                                    <xform:text property="fdDingUserId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingDinstanceXform.fdEkpInstanceId')}
                            </td>
                            <td width="35%">
                                <%-- 文档Id--%>
                                <div id="_xform_fdEkpInstanceId" _xform_type="text">
                                    <xform:text property="fdEkpInstanceId" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingDinstanceXform.docCreateTime')}
                            </td>
                            <td width="35%">
                                <%-- 创建时间--%>
                                <div id="_xform_docCreateTime" _xform_type="datetime">
                                    <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingDinstanceXform.fdEkpUser')}
                            </td>
                            <td width="35%">
                                <%-- 发起人--%>
                                <div id="_xform_fdEkpUserId" _xform_type="address">
                                    <xform:address propertyId="fdEkpUserId" propertyName="fdEkpUserName" orgType="ORG_TYPE_PERSON" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingDinstanceXform.fdTemplate')}
                            </td>
                            <td width="35%">
                                <%-- 所属模板--%>
                                 <div id="_xform_fdTemplateId" _xform_type="radio">
		                                <xform:select property="fdTemplateId" htmlElementProperties="id='fdTemplateId'" showStatus="view">
				                           <xform:beanDataSource serviceBean="thirdDingDtemplateXformService" selectBlock="fdId,fdName" />
				                        </xform:select>
		                        </div>   
                              
                            </td>
                            <td colspan="2" width="50.0%">
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingDinstanceXform.fdUrl')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 文档地址--%>
                                <div id="_xform_fdUrl" _xform_type="text">
                                    <xform:text property="fdUrl" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <%-- <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('third-ding:thirdDingDinstanceXform.fdConfig')}
                            </td>
                            <td colspan="3" width="85.0%">
                                配置信息
                                <div id="_xform_fdConfig" _xform_type="text">
                                    <xform:text property="fdConfig" showStatus="view" style="width:95%;" />
                                    <br><span class="com_help">${lfn:message('third-ding:thirdDingDinstanceXform.fdConfig.tips')}</span>
                                </div>
                            </td>
                        </tr> --%>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                                                                       实例状态
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 配置信息--%>
                                <div id="_xform_fdStatus" _xform_type="text">
                                   <%--  <xform:text property="fdStatus" showStatus="view" style="width:95%;" /> --%>
                                   <xform:radio property="fdStatus" htmlElementProperties="id='fdIsAvailable'" showStatus="view">
                                        <xform:enumsDataSource enumsType="third_ding_instadance_status" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="4" width="100%">
                                <table class="tb_normal" width="100%" id="TABLE_DocList_fdDetail_Form" align="center" tbdraggable="true">
                                    <tr align="center" class="tr_normal_title">
                                        <td style="width:40px;">
                                            ${lfn:message('page.serial')}
                                        </td>
                                        <td>
                                            ${lfn:message('third-ding:thirdDingIndanceXDetail.fdName')}
                                        </td>
                                        <td>
                                            ${lfn:message('third-ding:thirdDingIndanceXDetail.fdValue')}
                                        </td>
                                    </tr>
                                    <c:forEach items="${thirdDingDinstanceXformForm.fdDetail_Form}" var="fdDetail_FormItem" varStatus="vstatus">
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
                                                <%-- 值--%>
                                                <div id="_xform_fdDetail_Form[${vstatus.index}].fdValue" _xform_type="text">
                                                    <xform:text property="fdDetail_Form[${vstatus.index}].fdValue" showStatus="view" style="width:95%;" />
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