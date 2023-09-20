<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="config.list">
    <template:replace name="content">
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
                            <list:sort property="sysHelpConfig.fdStatus" text="${lfn:message('sys-help:sysHelpConfig.fdStatus')}" group="sort.list" />
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
                            <kmss:auth requestURL="/sys/help/sys_help_config/sysHelpConfig.do?method=add">
								<ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/sys/help/sys_help_config/sysHelpConfig.do?method=getAllModule">
								<ui:button text="${lfn:message('sys-help:sysHelpConfig.upload')}" onclick="upload()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/sys/help/sys_help_config/sysHelpConfig.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <!---->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4" id="btnDelete" />

                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/sys/help/sys_help_config/sysHelpConfig.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/sys/help/sys_help_config/sysHelpConfig.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdModuleName;fdModulePath;fdName;fdStatus.name;docCreator.fdName" /></list:colTable>
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
        
        <script>
        	window.canUpdate = true;
        	function upload(){
        		seajs.use(['lui/jquery', 'lui/dialog', 'lang!sys-help', 'lang!sys-ui', 'lui/topic'], function($, dialog, lang, ui_lang, topic){
        			var url = '/sys/help/sys_help_config/sysHelpConfig.do?method=getAllModule';
        			dialog.iframe(url, lang["sysHelpConfig.upload.title"], null, {
        				width : 600,
        				height: 500,
        				buttons : [
        					{
        						name : ui_lang['ui.dialog.button.ok'],
        						fn : function(value,_dialog) {
        							var body = $(_dialog.content.iframeObj[0].contentDocument);
    								var list = body.find('input[type="checkbox"]');
    								var value = '';
    								for(var i = 0;i < list.size();i++){
           								if(list[i].checked)
           									value += list[i].value + ';';
           							}
    								if(value == ''){
           								dialog.alert('<bean:message bundle="sys-help" key="sysHelpConfig.upload.title"/>', function(){
    										return;
    									});
           								return;
    								}
    								if(value.substr(0, 1) == ';'){
	    								value = value.substring(1, value.length -1);
    								}
    								if(value.substring(value.length -1, value.length) == ';'){
	    								value = value.substring(0, value.length -1);
    								}
    								
    								if(window.canUpdate){
									  	_dialog.hide();
	    								window.canUpdate = false;
	    								$.ajax({
											url : "${LUI_ContextPath}/sys/help/sys_help_config/sysHelpConfig.do?method=updateUrlData",
	  									  	data : {
												'moduleName' : value
	  									  	},
	  									  	type : "POST",
	  									  	dataType:"json",
	  									  	success :function(data){
			    								window.canUpdate = true;
												topic.publish('list.refresh');
	  									  	}
										});
    								}
    								
        						}
        					},
        					{
        						name : ui_lang['ui.dialog.button.cancel'],
        						styleClass : 'lui_toolbar_btn_gray',
        						fn : function(value, _dialog) {
        							_dialog.hide(value);
        						}
        					}
        				]
        			});
        		})
        	}
        </script>
    </template:replace>
</template:include>