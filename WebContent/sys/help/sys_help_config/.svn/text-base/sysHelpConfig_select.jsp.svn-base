<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="body">
		<div style="margin:5px 10px;">
            <!-- 筛选 -->
            <list:criteria id="criteria1" expand="true">
                <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-help:sysHelpConfig.fdName')}" />
                <list:cri-criterion title="${lfn:message('sys-help:sysHelpConfig.fdModuleName') }" key="fdModulePath" multi="false"> 
					<list:box-select>
						<list:item-select type="lui/criteria!CriterionSelectDatas">
							<ui:source type="AjaxJson">
								{url:'/sys/help/sys_help_config/sysHelpConfig.do?method=getModulesCri'}
							</ui:source>
						</list:item-select>
					</list:box-select>
				</list:cri-criterion>
                <list:cri-auto property="fdStatus" modelName="com.landray.kmss.sys.help.model.SysHelpConfig" />
                <list:cri-auto property="docCreator" modelName="com.landray.kmss.sys.help.model.SysHelpConfig" />
            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">
                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="sysHelpConfig.fdStatus" text="${lfn:message('sys-help:sysHelpConfig.fdStatus')}" group="sort.list" />
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/sys/help/sys_help_config/sysHelpConfig.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="" name="columntable">
                    <list:col-radio />
                    <list:col-serial/>
                    <list:col-auto props="fdModuleName;fdModulePath;fdName;fdStatus.name" />
                    <list:col-html>
                    	{$
                    		<div class="fdUrl" style="display:none;">{%row['fdId']%}</div>
                    	$}
                    </list:col-html>
                </list:colTable>
                <ui:event topic="list.loaded" args="vt">
                	$('#listview .lui_listview_columntable_table tbody tr').each(function(){
                		var value =this.children[5].textContent;
                		if(value=='是'){
                			$(this).css("color","#787878");
                			$(this).find("[name='List_Selected']").css("display","none");
                		}
                	});
                </ui:event>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.sys.help.model.SysHelpConfig',
                templateName: '',
                basePath: '/sys/help/sys_help_config/sysHelpConfig.do',
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
