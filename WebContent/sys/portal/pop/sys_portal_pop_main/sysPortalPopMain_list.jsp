<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<%-- 查询栏 --%>
	<template:replace name="content">
		
		<div style="margin:5px 10px;">
            <!-- 筛选 -->
			<list:criteria id="criteria">
			     <list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${lfn:message('sys-portal:sysPortalPopMain.docSubject') }">
				</list:cri-ref>
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
                            <list:sort property="sysPortalPopMain.docCreateTime" text="${lfn:message('sys-portal:sysPortalPopMain.docCreateTime')}" group="sort.list" value="down" />
                            <list:sort property="sysPortalPopMain.docSubject" text="${lfn:message('sys-portal:sysPortalPopMain.docSubject')}" group="sort.list" />
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
                            <kmss:auth requestURL="/sys/portal/pop/sys_portal_pop_main/sysPortalPopMain.do?method=add">
                                <ui:button text="${lfn:message('sys-portal:sysPortalPopTpl.selectTemplate')}" onclick="addDocWithTpl()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/sys/portal/pop/sys_portal_pop_main/sysPortalPopMain.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/sys/portal/pop/sys_portal_pop_main/sysPortalPopMain.do?method=deleteall">
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
                    {url:appendQueryParameter('/sys/portal/pop/sys_portal_pop_main/sysPortalPopMain.do?method=data&q.fdCustomCategory=${JsParam.customCategory }')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/sys/portal/pop/sys_portal_pop_main/sysPortalPopMain.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="docSubject;fdIsAvailable.name;docCreator.name;docCreateTime,operations" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.sys.portal.model.SysPortalPopMain',
                templateName: '',
                basePath: '/sys/portal/pop/sys_portal_pop_main/sysPortalPopMain.do',
                canDelete: '${canDelete}',
                mode: '',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                },
                categoryId: '${JsParam.categoryId }',
                customCategory: '${JsParam.customCategory }'

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/sys/portal/pop/resource/js/", 'js', true);
            
            seajs.use(['lui/jquery','lui/dialog','lui/dialog_common','lui/util/str','lui/topic'], 
            		function($, dialog, dialogCommon,strutil,topic){
            	
            	var targetWin = window;
            	while(targetWin != targetWin.parent) {
            		targetWin = targetWin.parent;
            	}
            	targetWin['__dialog_fdId_dataSource'] = function(){
                    return strutil.variableResolver('/sys/portal/pop/sys_portal_pop_tpl/sysPortalPopTpl.do?method=selectTpl&q.fdIsAvailable=true', null);
                }
            	
            	/*
	            window.addDocWithTpl = function() {
	            	
	            	dialogCommon.dialogSelect('com.landray.kmss.sys.portal.model.SysPortalPopTpl', false, 
   	    					'/sys/portal/pop/sys_portal_pop_tpl/sysPortalPopTpl.do?method=selectTpl&q.fdIsAvailable=true', 
   	    							null, 'fdId', 'fdName', '选择模板', function(data){
   	    				
   	    				data = data || [];
						if(data.length > 0) {
							
							var tpl = data[0];
							
					    	var url = listOption.contextPath + listOption.basePath + "?method=add&tplId=" + tpl.fdId;
					    	if(listOption.categoryId) {
					    		url += '&categoryId=' + listOption.categoryId;
					    	}
					        Com_OpenWindow(url);
					        
						} else {
							// TODO 未选取数据
						}
   	    						
   	    			});
	            }
            	*/
            	
            	window.addDocWithTpl = function() {
            		
            		dialog.iframe('/sys/portal/pop/template/select.jsp', '${lfn:message('sys-portal:sysPortalPopTpl.selectTemplate')}', function(res) {
            			
            			if(!res) {
            				return;
            			}
            			
            			var url = listOption.contextPath + listOption.basePath + "?method=add";
            			
            			if(res.type == 'sys') {
	            			url += '&sysTplId=' + res.id;
            			} else {
            				url += '&tplId=' + res.id;
            			}
            	        
            			if(listOption.categoryId) {
            	    		url += '&categoryId=' + listOption.categoryId;
            	    	}
            	    	if(listOption.customCategory) {
            	    		url += '&customCategory=' + listOption.customCategory;
            	    	}
            			
            			Com_OpenWindow(url);
            			
            		}, {
            			width: 550,
            			height: 500
            		});
            		
            	}
            	
            	// 编辑
		 		window.editPop = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/sys/portal/pop/sys_portal_pop_main/sysPortalPopMain.do" />?method=edit&fdId=' + id);
		 		};
		 		// 删除
		 		window.deletePop = function(id){
					var values = [];
					if(id){
						values.push(id);
					}else{
						$("input[name='List_Selected']:checked").each(function(){
							values.push($(this).val());
						});
					}
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.post('<c:url value="/sys/portal/pop/sys_portal_pop_main/sysPortalPopMain.do?method=deleteall"/>',
									$.param({"List_Selected":values},true),delCallback,'json');
						}
					});
				};
				window.delCallback = function(data){
					if(window.del_load!=null){
						window.del_load.hide();
						topic.publish("list.refresh");
					}
					dialog.result(data);
				};
            	
            });
            
        </script>
	</template:replace>
</template:include>