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
        <p class="txttitle" style="margin: 10px 0;">${lfn:message('sys-modeling-base:behavior.select.form')}</p>
        <table class="tb_normal" width="98%">
            <tr>
                <td colspan="4">
                    <list:criteria id="criteria1" expand="true">

                        <list:cri-ref key="_fdName" ref="criterion.sys.string" title="${lfn:message('sys-modeling-base:modeling.business.form')}" />
                        <list:cri-criterion title="${lfn:message('sys-modeling-base:modeling.model.appName')}" key="fdApplication" multi="false">
                            <list:box-select>
                            	<c:choose>
                            		<c:when test="${not empty JsParam.appId}">
                            			<list:item-select cfg-defaultValue ="${JsParam.appId}">
		                                    <ui:source type="Static">
		                                        <%=SysModelingUtil.buildCriteria("modelingApplicationService",
		                                                "modelingApplication.fdId,modelingApplication.fdAppName",
		                                                "left join modelingApplication.fdVersion fdVersion",
		                                                "(fdVersion is null or fdVersion.fdStatus='" + ModelingAppVersionConstant.STATUS_DRAFT + "' " +
		                                                        "or fdVersion.fdStatus='" + ModelingAppVersionConstant.STATUS_CURRENT + "')", null)%>
		                                    </ui:source>
		                                </list:item-select>
                            		</c:when>
                            		<c:otherwise>
                            			<list:item-select>
		                                    <ui:source type="Static">
		                                        <%=SysModelingUtil.buildCriteria("modelingApplicationService",
		                                                "modelingApplication.fdId,modelingApplication.fdAppName",
		                                                "left join modelingApplication.fdVersion fdVersion",
		                                                "(fdVersion is null or fdVersion.fdStatus='" + ModelingAppVersionConstant.STATUS_DRAFT + "' " +
		                                                        "or fdVersion.fdStatus='" + ModelingAppVersionConstant.STATUS_CURRENT + "')", null)%>
		                                    </ui:source>
		                                </list:item-select>
                            		</c:otherwise>
                            	</c:choose>
                            	
                                
                            </list:box-select>
                        </list:cri-criterion>
                    </list:criteria>
                    <div style="max-height: 270px; overflow-y: auto;">
                        <%--列表--%>
                        <list:listview id="listview" style="">
                            <ui:source type="AjaxJson">
                                {url:'/sys/modeling/base/modelingAppModel.do?method=listModelToRealtion'}
                            </ui:source>
                            <list:colTable isDefault="false" onRowClick="selectRdmTask('!{fdId}','!{fdName}');" rowHref=""
                                name="columntable">
                                <list:col-radio />
                                <list:col-html title="${lfn:message('sys-modeling-base:table.modelingApplication')}">
                                    {$ <span data-id="{%row['fdId']%}">{%row['fdAppName']%}</span> $}
                                </list:col-html>
                                <list:col-html title="${lfn:message('sys-modeling-base:table.modelingAppModel')}">
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
                    	var selected = {}
                        window.selectRdmTask = function (fdId,fdName) {
                    		selected={
                    				"fdId":fdId,
                    				"fdName":fdName
                    		}
                            if ($('[name="List_Selected"][value="' + fdId + '"]').is(':checked')) {
                                $('[name="List_Selected"][value="' +fdId + '"]').prop('checked', false);
                            } else {
                                $('[name="List_Selected"][value="' +fdId + '"]').prop('checked', true);
                            }
                        }
                       
                        window.doSubmit = function () {
                            if (!selected.hasOwnProperty("fdId")){
                                var $ListSelected = $('[name="List_Selected"]:checked');
                                if ($ListSelected){
                                    var fdId = $ListSelected.val();
                                    var fdName = $ListSelected.closest("tr").find("span[data-id="+fdId+"_name]").html();
                                    selected={
                                        "fdId":fdId,
                                        "fdName":fdName
                                    }
                                }
                            }
                            $dialog.hide(selected);
                        };

                        window.cancel = function () {
                            $dialog.hide(null);
                        }

                    });
        </script>
    </template:replace>
</template:include>