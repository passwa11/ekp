<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include file="/sys/profile/resource/template/list.jsp">
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1" expand="true">
                <list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${lfn:message('sys-help:sysHelpMain.docSubject')}" />
				<list:cri-criterion title="${lfn:message('sys-help:sysHelpMain.fdModuleName') }" key="fdModulePath" multi="false"> 
					<list:box-select>
						<list:item-select type="lui/criteria!CriterionSelectDatas">
							<ui:source type="AjaxJson">
								{url:'/sys/help/sys_help_main/sysHelpMain.do?method=getModulesCri'}
							</ui:source>
						</list:item-select>
					</list:box-select>
				</list:cri-criterion>
                <list:cri-criterion title="${lfn:message('sys-help:sysHelpMain.docStatus')}" key="docStatus" multi="false">
						<list:box-select>
							<list:item-select id="_docStatus">
								<ui:source type="Static">
									[{text:'${ lfn:message('status.draft') }', value:'10'},
									{text:'${ lfn:message('status.publish') }',value:'30'}]
								</ui:source>
							</list:item-select>
						</list:box-select>
				</list:cri-criterion>
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
                            <list:sort property="sysHelpMain.docCreateTime" text="${lfn:message('sys-help:sysHelpMain.docCreateTime')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
               	<!-- 分页 -->
				<div class="lui_list_operation_page_top">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">

                            <kmss:auth requestURL="/sys/help/sys_help_main/sysHelpMain.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                                <ui:button text="${lfn:message('sys-help:sysHelpMain.analyze.upload')}" onclick="Com_OpenWindow('${LUI_ContextPath}/sys/help/sys_help_main/sysHelpMain.do?method=analyzePage')" order="3" />
                            </kmss:auth>
                            <kmss:auth requestURL="/sys/help/sys_help_main/sysHelpMain.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4" id="btnDelete" />
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/sys/help/sys_help_main/sysHelpMain.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/sys/help/sys_help_main/sysHelpMain.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="docSubject;fdModuleName;docStatus;docCreator.name;docCreateTime" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.sys.help.model.SysHelpMain',
                templateName: '',
                basePath: '/sys/help/sys_help_main/sysHelpMain.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("sys-help:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/sys/help/resource/js/", 'js', true);
        </script>
    </template:replace>
</template:include>