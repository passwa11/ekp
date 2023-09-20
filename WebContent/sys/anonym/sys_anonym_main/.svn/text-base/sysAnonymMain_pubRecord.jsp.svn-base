<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.simple">
    <template:replace name="body">
    	<table width="100%"> 
		<tr>
			<td colspan="2">
				<div class="lui_list_operation">
	                <div>
	                    <div style="display: inline-block;vertical-align: middle;">
	                        <ui:toolbar count="3">
	                            <kmss:authShow roles="ROLE_SYSANONYM_DELETE">
	                                <c:set var="canDelete" value="true" />
	                                 <!--deleteall-->
	                            	<ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4" id="btnDelete" />
	                            </kmss:authShow>
	                            <ui:button text="${lfn:message('sys-anonym:sysAnonymMain.btn.batchcancel')}" onclick="batchAnonymCancel('${lfn:escapeHtml(fdModelName)}')" order="4" id="batchAnonymCancel" />
	                            <ui:button text="${lfn:message('sys-anonym:sysAnonymMain.btn.batchpub')}" onclick="batchAnonymPublish('${lfn:escapeHtml(fdModelName)}')" order="4" id="batchAnonymPublish" />
	                        </ui:toolbar>
	                    </div>
	                </div>
	            </div>
	            <ui:fixed elem=".lui_list_operation" />
	            <!-- 列表 -->
	            <list:listview id="listview">
	                <ui:source type="AjaxJson">
	                    {url:'/sys/anonym/sys_anonym_main/sysAnonymMain.do?method=data&fdModelName=${lfn:escapeHtml(fdModelName)}&fdModelId=${lfn:escapeHtml(fdModelId)}&fdKey=${lfn:escapeHtml(fdKey)}'}
	                </ui:source>
	                <!-- 列表视图 -->
	                <list:colTable isDefault="false" rowHref="/sys/anonym/sys_anonym_main/sysAnonymMain.do?method=view&fdId=!{fdId}" name="columntable">
	                    <list:col-checkbox />
	                    <list:col-serial/>
	                    <list:col-auto props="fdName;docCreateTime;docPublishTime;fdCategory.fdName;fdStatus;operations" url="" />
	                 </list:colTable>
	                 <ui:event topic="list.loaded">  
					   seajs.use(['lui/jquery'],function($){
							if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
								if($(document.body).height() > 0){
									window.frameElement.style.height =  $(document.body).height() +10+ "px";
								}
							}
						});
					</ui:event>	
	            </list:listview>
	            <!-- 翻页 -->
	            <list:paging layout="sys.ui.paging.simple"  />
			</td>
			
		</tr>
		</table>
		<script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'main',
                modelName: 'com.landray.kmss.sys.anonym.model.SysAnonymMain',
                templateName: '',
                basePath: '/sys/anonym/sys_anonym_main/sysAnonymMain.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("sys-anonym:treeModel.alert.templateAlert")}',
                customOpts: {
                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}',
                    anonymPublish: '${lfn:message("sys-anonym:dialog.anonymPublish")}',
                    anonymCancel: '${lfn:message("sys-anonym:dialog.anonymCancel")}',
                    batchAnonymPublish: '${lfn:message("sys-anonym:dialog.batchAnonymPublish")}',
                    batchAnonymCancel: '${lfn:message("sys-anonym:dialog.batchAnonymCancel")}',
                }

            };
            Com_IncludeFile("pub_list.js", "${LUI_ContextPath}/sys/anonym/resource/js/", 'js', true);
        </script>
    </template:replace>
</template:include>