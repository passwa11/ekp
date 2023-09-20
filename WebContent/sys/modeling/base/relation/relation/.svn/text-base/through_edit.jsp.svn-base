<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.sys.modeling.base.util.SysModelingUtil" %>

<% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser());
    pageContext.setAttribute("currentPerson", UserUtil.getKMSSUser().getUserId());
    pageContext.setAttribute("currentPost", UserUtil.getKMSSUser().getPostIds());
    pageContext.setAttribute("currentDept", UserUtil.getKMSSUser().getDeptId());
    if (UserUtil.getUser().getFdParentOrg() != null) {
        pageContext.setAttribute("currentOrg", UserUtil.getUser().getFdParentOrg().getFdId());
    } else {
        pageContext.setAttribute("currentOrg", "");
    }

%>

<template:include ref="default.edit">
    <template:replace name="head">
        <script type="text/javascript">
            var formInitData = {};
            var messageInfo = {};

            var initData = {
                contextPath: '${LUI_ContextPath}',
            };
            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
            Com_IncludeFile("form.js");
        </script>
        <link type="text/css" rel="stylesheet"
              href="${LUI_ContextPath}/sys/modeling/base/relation/relation/css/relation.css?s_cache=${LUI_Cache}"/>
        <style>
            tr.viewSet_display,
            #relationHideProperty,
            #relationHideProperty input {
                /*display: none !important;*/
            }
        </style>
    </template:replace>

    <template:replace name="toolbar">
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
            <c:choose>
                <c:when test="${ sysModelingRelationForm.method_GET == 'edit' }">
                    <ui:button text="${ lfn:message('button.update') }"
                               onclick="dosubmit('update')"/>
                </c:when>
                <c:when test="${ sysModelingRelationForm.method_GET == 'add' }">
                    <ui:button text="${ lfn:message('button.save') }"
                               onclick="dosubmit('save')"/>
                </c:when>
            </c:choose>

            <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();"/>
        </ui:toolbar>
    </template:replace>
    <template:replace name="content">
        <p class="txttitle" style="margin: 10px 0;">业务关系设置</p>
        <div id="relationSetMain">
            <table width="98%">
                <tr>
                    <td valign="top" id="relationNav">
                        <div class="relationNavTitle" lui-data-mark="rel">业务联动
                            <div title="新增" class="relation_addLine" onclick="relationAdd(0)"></div>
                        </div>
                        <div class="relationNavItem" lui-data-mark="rel">
                            <ul>
                                <c:forEach var="item" varStatus="vstatus"
                                           items="${relations}">
                                    <c:if test="${item.value.type!='1'}">
                                        <li <c:if
                                                test="${sysModelingRelationForm.fdId == item.key}"> class="selected"</c:if>
                                                title='${item.value.name}'>
                                            <div class="itemTitle">${item.value.name}</div>
                                            <div class="relation_delLine " title="删除"
                                                 onclick="relationDel('${item.key}')"></div>
                                            <div class="relation_editLine" title="编辑"
                                                 onclick="relationEdit('${item.key}')"></div>
                                        </li>
                                    </c:if>
                                </c:forEach>
                            </ul>
                        </div>
                        <br>
                        <div class="relationNavTitle" lui-data-mark="through">业务穿透
                            <div title="新增" class="relation_addLine" onclick="relationAdd(1)"></div>
                        </div>
                        <div class="relationNavItem" lui-data-mark="through">
                            <ul>
                                <c:forEach var="item" varStatus="vstatus"
                                           items="${relations}">
                                    <c:if test="${item.value.type=='1'}">
                                        <li <c:if
                                                test="${sysModelingRelationForm.fdId == item.key}"> class="selected"</c:if>
                                                title='${item.value.name}'>
                                            <div class="itemTitle">${item.value.name}</div>
                                            <div class="relation_delLine " title="删除"
                                                 onclick="relationDel('${item.key}')"></div>
                                            <div class="relation_editLine" title="编辑"
                                                 onclick="relationEdit('${item.key}')"></div>
                                        </li>
                                    </c:if>
                                </c:forEach>
                            </ul>
                        </div>
                    </td>
                    <td valign="top" id="relationMain">
                        <html:form action="/sys/modeling/base/sysModelingRelation.do">
                            <div class='lui_form_title_frame'>
                                <div class='lui_form_subject'>
                                    <c:if test="${ sysModelingRelationForm.method_GET == 'add' }"> 【新建】</c:if>业务穿透
                                </div>
                            </div>
                            <table class="tb_normal relationMainForm" width="700px;">
                                <tr>
                                    <td class="td_normal_title" width="15%">
                                            ${lfn:message('sys-modeling-base:sysModelingRelation.fdName')}
                                    </td>
                                    <td width="85%">
                                            <%-- 名称--%>
                                        <div id="_xform_fdName" _xform_type="text">
                                            <xform:text property="fdName" showStatus="edit" style="width:95%;"/>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td_normal_title" width="15%">
                                            ${lfn:message('sys-modeling-base:sysModelingRelation.modelMain')}
                                    </td>
                                    <td width="85%">
                                            <%-- 关联表单--%>
                                        <div id="_xform_modelMainId" _xform_type="select">
                                            <xform:select property="modelMainId"
                                                          htmlElementProperties="id='modelMainId'"
                                                          showStatus="view">
                                                <xform:beanDataSource serviceBean="modelingAppModelService"
                                                                      selectBlock="fdId,fdName"/>
                                            </xform:select>
                                        </div>
                                    </td>
                                <tr>
                                    <td class="td_normal_title" width="15%">
                                            ${lfn:message('sys-modeling-base:sysModelingRelation.modelPassive')}
                                    </td>
                                    <td width="85%">
                                            <%-- 被关联表单--%>
                                        <div id="_xform_modelPassiveId" _xform_type="select">
                                            <xform:select property="modelPassiveId"
                                                          htmlElementProperties="id='modelPassiveId'"
                                                          showStatus="view">
                                                <xform:beanDataSource serviceBean="modelingAppModelService"
                                                                      selectBlock="fdId,fdName"/>
                                            </xform:select>
                                        </div>
                                    </td>
                                </tr>


                                <tr>
                                    <td class="td_normal_title" width="15%">
                                            ${lfn:message('sys-modeling-base:sysModelingRelation.fdWidgetName')}
                                    </td>
                                    <td width="85%">
                                            <%-- 关联控件名--%>
                                        <div id="_xform_fdWidgetName" _xform_type="select">
                                            <xform:select onValueChange="widgetChange" property="fdWidgetId"
                                                          showStatus="edit"
                                                          showPleaseSelect="false">
                                                <c:forEach var="moduleMap" varStatus="vstatus"
                                                           items="${widgets.drilling}">
                                                    <c:if
                                                            test="${moduleMap.value.relation==sysModelingRelationForm.fdId || moduleMap.value.relation==null}">
                                                        <xform:simpleDataSource
                                                                value="${moduleMap.key}"> ${moduleMap.value.label}
                                                        </xform:simpleDataSource>
                                                    </c:if>
                                                </c:forEach>
                                            </xform:select>
                                        </div>
                                    </td>
                                </tr>

                                <tr id="relationHideProperty">
                                    <input type="hidden" name="modelPassiveId"
                                           value="<c:out value='${sysModelingRelationForm.modelPassiveId}' />"/>
                                    <input type="hidden" name="modelMainId"
                                           value="<c:out value='${sysModelingRelationForm.modelMainId}' />">
                                    <input type="hidden" name="fdWidgetName"
                                           value="<c:out value='${sysModelingRelationForm.fdWidgetName}' />"/>
                                    <input type="hidden" name="throughJson"
                                           value="<c:out value='${throughJson}' />">
                                    <html:hidden property="fdId"/>
                                    <html:hidden property="fdType" value='${empty sysModelingRelationForm.fdType?1:sysModelingRelationForm.fdType}'></html:hidden>
                                    <html:hidden property="method_GET"/>
                                </tr>

                            </table>
                        </html:form>
                    </td>
                </tr>
            </table>
        </div>
        <script type="text/javascript">
            var rHost = "${LUI_ContextPath}/sys/modeling/base/sysModelingRelation.do";
            var relationUrl = {
                "load": rHost + "?method=getModelRelations&modelId=${param.modelId}&passiveId=${param.passiveId}",
                "detail": rHost + "?method=edit&modelId=${param.modelId}&passiveId=${param.passiveId}",
                "create": rHost + "?method=add&modelId=${param.modelId}&passiveId=${param.passiveId}",
                "del": rHost + "?method=delete&fdId=",
                "asynSave": rHost + "?method=asynSave"
            }

            function relationAdd(type) {
                window.location.href = rHost + "?method=add&modelId=${param.modelId}&passiveId=${param.passiveId}&type=" + type;
            }

            function relationDel(id) {
                seajs.use(["lui/dialog"], function (dialog) {
                    dialog.confirm('是否删除当前关系？', function (value) {
                        if (value == true) {
                            window.location.href = rHost + "?method=delete&fdId=" + id;
                        }
                    });
                })


            }

            function relationEdit(id) {
                window.location.href = rHost + "?method=edit&modelId=${param.modelId}&passiveId=${param.passiveId}&fdId=" + id;
            }

            seajs.use(["sys/modeling/base/relation/relation/js/through", "lui/dialog"], function (through, dialog) {

                /*
                左侧标题文字
                 */
                var nameLabel = {
                    "throughJson": "穿透设置"
                };

                function init() {
                    window.relation_init_load = dialog.loading();
                    var cfg = {
                        nameLabel: nameLabel,
                        allTitle: {},
                        widgets:${widgets},
                        xformId: "${xformId}",
                        modelId: "${sysModelingRelationForm.modelMainId}",
                        passiveId: "${sysModelingRelationForm.modelPassiveId}"
                    }
                    window.relationInst = new through.Relation(cfg);
                    relationInst.startup();
                    if (window.relation_init_load != null) {
                        window.relation_init_load.hide();
                    }
                }


                window.dosubmit = function (type) {
                    appendValToHide("fdType", 1)
                    //字段穿透
                    relationInst.getKeyData("throughJson");
                    var throughJson = relationInst.getData("throughJson");
                  //  console.log(throughJson)
                    var viewSet = throughJson.viewSet;
                    if (viewSet) {
                        appendValToHide("fdThroughViewDef", viewSet.def);
                        appendValToHide("fdThroughViewType", viewSet.type);
                        //视图
                        if (viewSet.def === "0") {
                            if (viewSet.type === "0") {
                                appendValToHide("fdThroughListViewId", viewSet.id);
                            } else if (viewSet.type === "1") {
                                appendValToHide("fdThroughViewId", viewSet.id);
                            }
                        }
                        //入参
                        var incval =JSON.stringify(throughJson.view);
                        appendValToHide("fdThroughInc",incval);
                    }

                    Com_Submit(document.sysModelingRelationForm, type);
                };

                function appendValToHide(name, value) {
                    var $hideProp = $("#relationHideProperty");
                    var $ele = $("<input type='text'/>");
                    $ele.attr("name", name);

                    $ele.val(value);
                    $hideProp.append($ele);
                }

                init();
                // LUI.ready(init);
            });

        </script>
    </template:replace>
</template:include>

