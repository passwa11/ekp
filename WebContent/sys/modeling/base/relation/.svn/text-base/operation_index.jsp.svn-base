<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<div class="lui_modeling">
    <div class="lui_modeling_main aside_main">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdName" ref="criterion.sys.docSubject"
                              title="${lfn:message('sys-modeling-base:sysModelingOperation.fdName')}"/>
            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">
                <!-- 全选 -->
                <div class="lui_list_operation_order_btn">
                    <list:selectall></list:selectall>
                </div>
                <!-- 分割线 -->
                <div class="lui_list_operation_line"></div>
                <!-- 排序 -->
                <div class="lui_list_operation_sort_btn">
                    <div class="lui_list_operation_order_text">
                        ${ lfn:message('list.orderType') }：
                    </div>
                    <div class="lui_list_operation_sort_toolbar">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sortgroup>
                                <list:sort property="docCreateTime" text="${lfn:message('model.fdCreateTime') }"
                                           group="sort.list"></list:sort>
                            </list:sortgroup>
                        </ui:toolbar>
                    </div>
                </div>
                <!-- 分页 -->
                <div class="lui_list_operation_page_top">
                    <list:paging layout="sys.ui.paging.top">
                    </list:paging>
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">
                            <kmss:auth requestURL="/sys/modeling/base/sysModelingOperation.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDocById()" order="2"/>
                            </kmss:auth>
                            <kmss:auth requestURL="/sys/modeling/base/sysModelingOperation.do?method=deleteall">
                                <c:set var="canDelete" value="true"/>
                            </kmss:auth>
                            <!---->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4"
                                       id="btnDelete"/>
                        </ui:toolbar>
                    </div>
                </div>
            </div>
        </div>
        <ui:fixed elem=".lui_list_operation"/>
        <!-- 列表 -->
        <list:listview id="listview">
            <ui:source type="AjaxJson">
                {url:appendQueryParameter('/sys/modeling/base/sysModelingOperation.do?method=data&filterDefType=2&modelMain.fdId=${param.fdModelId }')}
            </ui:source>
            <!-- 列表视图 -->
            <list:colTable isDefault="false" onRowClick="toView('!{fdId}')" name="columntable">
                <list:col-checkbox/>
                <list:col-serial/>
                <list:col-auto props="fdName;fdType;docCreateTime;docCreator.name;operations" url=""/></list:colTable>
        </list:listview>
        <!-- 翻页 -->
    </div>
    <list:paging/>

    <script>
        var listOption = {
            contextPath: '${LUI_ContextPath}',
            jPath: 'operation',
            modelName: 'com.landray.kmss.sys.modeling.base.model.SysModelingOperation',
            templateName: '',
            basePath: '/sys/modeling/base/sysModelingOperation.do',
            canDelete: '${canDelete}',
            mode: '',
            templateService: '',
            templateAlert: '${lfn:message("sys-modeling-base:treeModel.alert.templateAlert")}',
            customOpts: {

                ____fork__: 0
            },
            lang: {
                noSelect: '${lfn:message("page.noSelect")}',
                comfirmDelete: '${lfn:message("page.comfirmDelete")}'
            }

        };
        Com_IncludeFile("list.js", "${LUI_ContextPath}/sys/modeling/base/resources/js/", 'js', true);
        seajs.use(["lui/jquery", 'lui/topic', "sys/ui/js/dialog"], function ($, topic, dialog) {
            window.addDocById = function () {
                dialog.iframe("/sys/modeling/base/sysModelingOperation.do?method=add&modelMainId=${param.fdModelId}", "${lfn:message('sys-modeling-base:operation.new.operation')}",
                    function (value) {
                        if(value === undefined){
                            topic.publish("list.refresh");
                        }
                    }, {
                        width: 1010,
                        height: 600
                    });
            }

            window.toView = function (fdId) {
                dialog.iframe("/sys/modeling/base/sysModelingOperation.do?method=edit&fdId=" + fdId, " ",
                    function (value) {
                        if(value === undefined){
                            topic.publish("list.refresh");
                        }
                    }, {
                        width: 1010,
                        height: 600
                    });
            }
            topic.subscribe("list.changed", hideDefOperationCheckbox, this);
            topic.subscribe("list.loaded", hideDefOperationCheckbox, this);

            function hideDefOperationCheckbox() {
                $(".conf_show_more_w").each(function (idx, ele) {
                    var $ele = $(ele);
                    if ($ele.attr("mark-fdtype") === "0") {
                        var id = $ele.attr("mark-fdId");
                        var $tr = $("tr[kmss_fdid=" + id + "]");
                        $tr.find("input[name='List_Selected']").remove();


                        // $tr.find("input[name='List_Selected']").attr("disabled",true);
                        // $tr.find("input[name='List_Selected']").attr("title","内置操作不允许删除");
                        // $tr.find("input[name='List_Selected']").attr("data-lui-mark","table.content.disabled");
                        // $tr.find("input[name='List_Selected']").attr("name","List_Selected_Def");
                    }
                })

            }
        });
    </script>
</div>
</div>
<c:import url="/sys/profile/i18n/quicklyMultiLangEdit.jsp"></c:import>
