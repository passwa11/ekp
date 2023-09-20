<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.fssc.config.util.FsscConfigUtil" %>
    
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

                    'fdDetail': '${lfn:escapeJs(lfn:message("fssc-config:table.fsscConfigScoreDetail"))}'
                };
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            </script>
        </template:replace>
        <template:replace name="title">
            <c:out value="${fsscConfigScoreForm.fdMonth} - " />
            <c:out value="${ lfn:message('fssc-config:table.fsscConfigScore') }" />
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
                    basePath: '/fssc/config/fssc_config_score/fsscConfigScore.do',
                    customOpts: {

                        ____fork__: 0
                    }
                };

                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
            </script>
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">

                <!--edit-->
                <kmss:auth requestURL="/fssc/config/fssc_config_score/fsscConfigScore.do?method=edit&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscConfigScore.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
                </kmss:auth>
                <!--delete-->
                <kmss:auth requestURL="/fssc/config/fssc_config_score/fsscConfigScore.do?method=delete&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('fsscConfigScore.do?method=delete&fdId=${param.fdId}');" order="4" />
                </kmss:auth>
                <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />


            </ui:toolbar>
        </template:replace>
        <template:replace name="path">
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self" />
                <ui:menu-item text="${ lfn:message('fssc-config:table.fsscConfigScore') }" href="/fssc/config/fssc_config_score/" target="_self" />
            </ui:menu>
        </template:replace>
        <template:replace name="content">

            <ui:tabpage expand="false" var-navwidth="90%">
                <ui:content title="${ lfn:message('fssc-config:py.JiBenXinXi') }" expand="true">
                    <div class='lui_form_title_frame'>
                        <div class='lui_form_subject'>
                            ${lfn:message('fssc-config:table.fsscConfigScore')}
                        </div>
                        <div class='lui_form_baseinfo'>

                        </div>
                    </div>
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-config:fsscConfigScore.fdMonth')}
                            </td>
                            <td width="35%">
                                <%-- 月份--%>
                                <div id="_xform_fdMonth" _xform_type="select">
                                    <xform:select property="fdMonth" htmlElementProperties="id='fdMonth'" showStatus="view">
                                        <xform:enumsDataSource enumsType="fssc_config_enums_month" />
                                    </xform:select>
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-config:fsscConfigScore.fdPerson')}
                            </td>
                            <td width="35%">
                                <%-- 岗位--%>
                                <div id="_xform_fdPersonId" _xform_type="address">
                                    <xform:address propertyId="fdPersonId" propertyName="fdPersonName" orgType="ORG_TYPE_PERSON" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-config:fsscConfigScore.fdScoreInit')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 初始积分--%>
                                <div id="_xform_fdScoreInit" _xform_type="text">
                                    <xform:text property="fdScoreInit" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-config:fsscConfigScore.fdScoreRemain')}
                            </td>
                            <td width="35%">
                                <%-- 剩余积分--%>
                                <div id="_xform_fdScoreRemain" _xform_type="text">
                                    <xform:text property="fdScoreRemain" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-config:fsscConfigScore.fdScoreUse')}
                            </td>
                            <td width="35%">
                                <%-- 已使用积分--%>
                                <div id="_xform_fdScoreUse" _xform_type="text">
                                    <xform:text property="fdScoreUse" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-config:fsscConfigScore.docCreateTime')}
                            </td>
                            <td width="35%">
                                <%-- 创建时间--%>
                                <div id="_xform_docCreateTime" _xform_type="datetime">
                                    <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-config:fsscConfigScore.docCreator')}
                            </td>
                            <td width="35%">
                                <%-- 创建人--%>
                                <div id="_xform_docCreatorId" _xform_type="address">
                                    <ui:person personId="${fsscConfigScoreForm.docCreatorId}" personName="${fsscConfigScoreForm.docCreatorName}" />
                                </div>
                            </td>
                        </tr>
                    </table>
                    <br/>
                    <table class="tb_normal" width="100%" id="TABLE_DocList_fdDetail_Form" align="center" tbdraggable="true">
                        <tr align="center" class="tr_normal_title">
                            <td style="width:40px;">
                                ${lfn:message('page.serial')}
                            </td>
                            <td>
                                ${lfn:message('fssc-config:fsscConfigScoreDetail.fdAddScorePerson')}
                            </td>
                            <td>
                                ${lfn:message('fssc-config:fsscConfigScoreDetail.fdScoreUse')}
                            </td>
                            <td>
                                ${lfn:message('fssc-config:fsscConfigScoreDetail.docCreateTime')}
                            </td>
                             <td>
                                ${lfn:message('fssc-config:fsscConfigScoreDetail.fdDesc')}
                            </td>
                        </tr>
                        <c:forEach items="${fsscConfigScoreForm.fdDetail_Form}" var="fdDetail_FormItem" varStatus="vstatus">
                            <tr KMSS_IsContentRow="1" class="docListTr">
                                <td class="docList" align="center">
                                    ${vstatus.index+1}
                                </td>
                                <td class="docList" align="center">
                                    <%-- 被点赞人 --%>
                                    <input type="hidden" name="fdDetail_Form[${vstatus.index}].fdId" value="${fdDetail_FormItem.fdId}" />
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdAddScorePersonName" _xform_type="text">
                                        <xform:text property="fdDetail_Form[${vstatus.index}].fdAddScorePersonName" showStatus="view" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="docList" align="center">
                                    <%-- 使用积分--%>
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdScoreUse" _xform_type="text">
                                        <xform:text property="fdDetail_Form[${vstatus.index}].fdScoreUse" showStatus="view" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="docList" align="center">
                                    <%-- 创建时间--%>
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].docCreateTime" _xform_type="date">
                                        <xform:datetime property="fdDetail_Form[${vstatus.index}].docCreateTime" showStatus="view" dateTimeType="date" style="width:95%;" />
                                    </div>
                                </td>
                                 <td class="docList" align="center">
                                    <%-- 详细说明 --%>
                                    <div id="_xform_fdDetail_Form[${vstatus.index}].fdDesc" _xform_type="text">
                                          <xform:text property="fdDetail_Form[${vstatus.index}].fdDesc" showStatus="view" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </ui:content>
            </ui:tabpage>
        </template:replace>

    </template:include>