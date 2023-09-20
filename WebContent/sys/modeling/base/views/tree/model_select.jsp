<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
    <template:replace name="head">
        <style type="text/css">
            #preNodeBody td{
                border: none;
                text-align: center;
            }
            #preNode thead tr td{
                border-left: none;
                border-right: none;
                text-align: center;
            }
            .listview_default_images {
                width: 220px;
                height: 160px;
                background: url(../business/res/img/mindMap/collection_listview_default@2x.png) no-repeat;
                margin-top: 10%;
                background-size: contain;
            }
            .litview_new_tips {
                width: auto;
                height: 33px;
                font-family: PingFangSC-Semibold;
                font-size: 14px;
                letter-spacing: 0;
                text-align: center;
                margin: 10px;
            }
        </style>
    </template:replace>
    <template:replace name="content">
        <div class="pre-node-content">
            <p class="txttitle" style="margin: 10px 0;">${lfn:message('sys-modeling-base:modelingTreeView.select.superior.form')}</p>
            <table class="tb_normal" width="98%" id="preNode" style="display: none">
               <thead>
                    <tr>
                        <td width="10%" colspan="2">${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperation')}</td>
                        <td width="40%">${lfn:message('sys-modeling-base:modelingDynamicLink.fdApplication')}</td>
                        <td width="40%">${lfn:message('sys-modeling-base:modeling.model.form.name')}</td>
                    </tr>
                </thead>
                <tbody id="preNodeBody">
                    <tr data-record-id="root"
                        onclick="selectRdmTask('root','${lfn:message('sys-modeling-base:modelingMindMap.root.node')}',this)">
                        <td colspan="2">
                            <input type="radio" name="selected" value="1">
                        </td>
                        <td>${lfn:message('sys-modeling-base:modelingMindMap.root.node')}</td>
                        <td>${lfn:message('sys-modeling-base:modelingMindMap.root.node')}</td>
                    </tr>
                </tbody>
                <tr>
                    <td colspan="4" style="text-align: center;">
                        <ui:button text="${lfn:message('button.select')}" onclick="doSubmit();" />
                        <ui:button text="${lfn:message('button.cancel') }" onclick="cancel();" />
                    </td>
                </tr>
            </table>
        </div>
        <script type="text/javascript">
            seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic','lui/util/str'],
                    function ($, dialog, topic, str) {
                    	var selected = {}
                        window.selectRdmTask = function (fdId,fdName,obj) {
                    		selected={
                    				"fdId":fdId,
                    				"fdName":fdName
                    		}
                    		$(obj).find("[name='selected']").prop("checked",true);
                        }
                       
                        window.doSubmit = function () {
                            var fdId = $('[name="List_Selected"]:checked').val();
                            $dialog.hide(selected);
                        };

                        window.cancel = function () {
                            $dialog.hide(null);
                        }

                    });

            LUI.ready(function () {
                var url =  Com_Parameter.ContextPath + "sys/modeling/base/modelingAppModel.do?method=findModelByIdCollection&preNodeId=${param.preNodeId}";
                $.ajax({
                    url: url,
                    type: "get",
                    async: false,
                    success: function (data) {
                        if (data) {
                            $("#preNode").show();
                            $(".listview_no_content").hide();
                            var trHtml = "";
                            for (var i = 0; i < data.length; i++) {
                                trHtml += "<tr data-record-id='" + data[i].fdId + "'";
                                trHtml += "onclick=\"selectRdmTask('" + data[i].fdId + "','" + data[i].fdName + "',this)\">";
                                trHtml += "<td colspan=\"2\"><input type='radio' name='selected' value='1'></td>";
                                trHtml += "<td>" + data[i].fdAppName + "</td>";
                                trHtml += "<td>" + data[i].fdName + "</td>";
                                trHtml += "</tr>";
                            }
                            $("#preNodeBody").append(trHtml);
                        }
                    }
                });

            })
        </script>
    </template:replace>
</template:include>