<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%@page import="com.landray.kmss.sys.modeling.base.util.SysModelingUtil"%>
<%@ page import="com.landray.kmss.sys.modeling.base.constant.ModelingAppVersionConstant" %>
<template:include ref="default.dialog">
    <template:replace name="head">
        <style type="text/css">
        </style>
    </template:replace>
    <template:replace name="content">
        <p class="txttitle" style="margin: 10px 0;">关系设置-选择表单</p>
        <table class="tb_normal" width="98%">
            <tr>
                <td colspan="4">
                    <list:criteria id="criteria1" expand="true">

                        <list:cri-ref key="_fdName" ref="criterion.sys.string" title="业务表单" />
                        <list:cri-criterion title="所属应用" key="fdApplication" multi="false">
                            <list:box-select>
                                <list:item-select>
                                    <ui:source type="Static">
                                        <%=SysModelingUtil.buildCriteria("modelingApplicationService",
                                                "modelingApplication.fdId,modelingApplication.fdAppName",
                                                "left join modelingApplication.fdVersion fdVersion",
                                                "(fdVersion is null or fdVersion.fdStatus='" + ModelingAppVersionConstant.STATUS_DRAFT + "' " +
                                                        "or fdVersion.fdStatus='" + ModelingAppVersionConstant.STATUS_CURRENT + "')", null)%>
                                    </ui:source>
                                </list:item-select>
                            </list:box-select>
                        </list:cri-criterion>


                    </list:criteria>
                    <div style="max-height: 270px; overflow-y: auto;">
                        <%--列表--%>
                        <list:listview id="listview" style="">
                            <ui:source type="AjaxJson">
                                {url:'/sys/modeling/base/modelingAppModel.do?method=listModelToRealtion'}
                            </ui:source>
                            <list:colTable isDefault="false" onRowClick="selectRdmTask('!{fdId}');" rowHref=""
                                name="columntable">
                                <list:col-radio />
                                <list:col-html title="应用">
                                    {$ <span data-id="{%row['fdId']%}">{%row['fdAppName']%}</span> $}
                                </list:col-html>
                                <list:col-html title="模块">
                                    {$ <span data-id="{%row['fdId']%}_name"
                                        style="max-width: 500px; display: inline-block;">{%row['fdName']%}</span> $}
                                </list:col-html>
                            </list:colTable>
                        </list:listview>
                        <list:paging></list:paging>
                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="4" style="text-align: center;">
                    <%--选择--%>
                    <ui:button text="${lfn:message('button.select')}" onclick="doSubmit();" /> <%--取消--%>
                    <ui:button text="${lfn:message('button.cancel') }" onclick="cancel();" />
                </td>
            </tr>
        </table>

        <script type="text/javascript">
            seajs
                .use(
                    ['lui/jquery', 'lui/dialog', 'lui/topic','lui/util/str'],
                    function ($, dialog, topic, str) {
                        window.selectRdmTask = function (fdId) {
                            if ($('[name="List_Selected"][value="' + fdId + '"]').is(':checked')) {
                                $('[name="List_Selected"][value="' +fdId + '"]').prop('checked', false);
                            } else {
                                $('[name="List_Selected"][value="' +fdId + '"]').prop('checked', true);
                            }
                        }
                        var saveRelation =
                            "${LUI_ContextPath}/sys/modeling/base/sysModelingRelation.do?method=saveRelation&modelId=${param.id}&passiveId=";
                        window.doSubmit = function () {
                            var fdId = $('[name="List_Selected"]:checked').val();
                            if (fdId) {
                                $.post(saveRelation + fdId, function (data, status) {
                                        console.log("Data: " +data +"\n--Status: " + status);
                                        if (status) {
                                            var json = JSON.parse(data)
                                            if (json.success == true) {
                                                dialog.success('<bean:message key="return.optSuccess" />');
                                                $dialog.hide(true);
                                            }else{
                                            	 dialog.failure("操作失败-" +json.msg);
                                            }
                                           
                                        } else {
                                            dialog.failure("操作失败");
                                        }
                                        $dialog.hide(null);
                                    });
                            } else {
                                $dialog.hide(null);
                            }
                        };

                        window.cancel = function () {
                            $dialog.hide(null);
                        }

                    });
        </script>
    </template:replace>
</template:include>