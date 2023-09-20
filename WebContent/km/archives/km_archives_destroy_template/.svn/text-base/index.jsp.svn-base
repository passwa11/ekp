<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include file="/sys/profile/resource/template/list.jsp">
    <template:replace name="content">
            <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('km-archives:kmArchivesDestroyTemplate.fdName')}" />

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
	                            <list:sort property="docCreateTime" text="${lfn:message('km-archives:kmArchivesDestroyTemplate.docCreateTime')}" group="sort.list" />
	                            <list:sort property="fdName" text="${lfn:message('km-archives:kmArchivesDestroyTemplate.fdName')}" group="sort.list" />
                        	</list:sortgroup>
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
                            <kmss:auth requestURL="/km/archives/km_archives_destroy_template/kmArchivesDestroyTemplate.do?method=add">
                                <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                            </kmss:auth>
                            <kmss:auth requestURL="/km/archives/km_archives_destroy_template/kmArchivesDestroyTemplate.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="del()" order="4" id="btnDelete" />

                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:'/km/archives/km_archives_destroy_template/kmArchivesDestroyTemplate.do?method=data'}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/km/archives/km_archives_destroy_template/kmArchivesDestroyTemplate.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdName;docCreateTime;fdDefaultFlag;docCreator.fdName;operations" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        <script type="text/javascript">
		seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
			
			window.addDoc = function() {
	 			Com_OpenWindow('<c:url value="/km/archives/km_archives_destroy_template/kmArchivesDestroyTemplate.do" />?method=add');
	 		};
	 		
			//编辑
			window.edit = function(id) {
				if(id)
					Com_OpenWindow('<c:url value="/km/archives/km_archives_destroy_template/kmArchivesDestroyTemplate.do" />?method=edit&fdId=' + id);
			};
			
			window.del = function(id){
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
				var url = '<c:url value="/km/archives/km_archives_destroy_template/kmArchivesDestroyTemplate.do?method=deleteall"/>';
				dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
					if(value==true){
						window.del_load = dialog.loading();
						$.ajax({
							url: url,
							type: 'POST',
							data:$.param({"List_Selected":values},true),
							dataType: 'json',
							error: function(data){
								if(window.del_load!=null){
									window.del_load.hide(); 
								}
								dialog.result(data.responseJSON);
							},
							success: delCallback
					   });
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
			topic.subscribe('successReloadPage', function() {
				topic.publish('list.refresh');
			});
		});
		</script>
    </template:replace>
</template:include>
