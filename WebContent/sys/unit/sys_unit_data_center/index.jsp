<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.simple" spa="true">
    <template:replace name="body">
        <script type="text/javascript">
            seajs.use(['theme!list']);
        </script>
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-unit:sysUnitDataCenter.fdName')}"/>
                <list:cri-auto modelName="com.landray.kmss.sys.unit.model.SysUnitDataCenter" property="fdAppkey"/>
                <list:cri-auto modelName="com.landray.kmss.sys.unit.model.SysUnitDataCenter" property="fdName"/>
                <list:cri-auto modelName="com.landray.kmss.sys.unit.model.SysUnitDataCenter" property="fdIsAvailable"/>
            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top"/>
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">
                            <kmss:auth requestURL="/sys/unit/sys_unit_data_center/sysUnitDataCenter.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2"/>
                            </kmss:auth>
                            <kmss:auth requestURL="/sys/unit/sys_unit_data_center/sysUnitDataCenter.do?method=deleteall">
                                <c:set var="canDelete" value="true"/>
                            </kmss:auth>
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4" id="btnDelete"/>
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation"/>
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:'/sys/unit/sys_unit_data_center/sysUnitDataCenter.do?method=list'}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false"
                               rowHref="/sys/unit/sys_unit_data_center/sysUnitDataCenter.do?method=view&fdId=!{fdId}"
                               name="columntable">
                    <list:col-checkbox/>
                    <list:col-serial/>
                    <list:col-auto props="fdAppkey;fdName;fdIsAvailable.name;docCreateTime;operations"
                                   url=""/></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging/>
        </div>
        <script>
            seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function ($, dialog, topic) {
                // 增加
                window.addDoc = function () {
                    Com_OpenWindow('<c:url value="/sys/unit/sys_unit_data_center/sysUnitDataCenter.do" />?method=add');
                };
                // 编辑
                window.edit = function (id) {
                    if (id) {
                        Com_OpenWindow('<c:url value="/sys/unit/sys_unit_data_center/sysUnitDataCenter.do" />?method=edit&fdId=' + id);
                    }
                };

                window.deleteAll = function (id) {
                    var values = [];
                    if (id) {
                        values.push(id);
                    } else {
                        $("input[name='List_Selected']:checked").each(function () {
                            values.push($(this).val());
                        });
                    }
                    if (values.length == 0) {
                        dialog.alert('<bean:message key="page.noSelect"/>');
                        return;
                    }

                    window.del_load = dialog.loading();
                    var delUrl = '<c:url value="/sys/unit/sys_unit_data_center/sysUnitDataCenter.do?method=deleteall"/>';
                    var config = {
                        url: delUrl, // 删除数据的URL
                        data: $.param({"List_Selected": values}, true), // 要删除的数据
                        modelName: "com.landray.kmss.sys.unit.model.SysUnitDataCenter" // 主要是判断此文档是否有部署软删除
                    };

                    // 通用删除回调方法
                    function delCallback(data) {
                        topic.publish("list.refresh");
                        dialog.result(data);
                    }

                    Com_Delete(config, delCallback);
                };

                topic.subscribe('successReloadPage', function () {
                    topic.publish('list.refresh');
                });
            });
        </script>
    </template:replace>
</template:include>