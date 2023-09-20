<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="config.list">
    <template:replace name="content">
        <div style="margin:5px 10px;">
            <!-- 筛选 -->

            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">
							<kmss:auth requestURL="/fssc/fee/fssc_fee_template/fsscFeeTemplate.do?method=add&parentId=${param.parentId}" requestMethod="GET">
							<ui:button text="${lfn:message('button.add')}"  onclick="add();" order="1" ></ui:button>
							</kmss:auth>
							<kmss:auth requestURL="/fssc/fee/fssc_fee_template/fsscFeeTemplate.do?method=deleteall&parentId=${param.parentId}" requestMethod="GET">
								<ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll();" order="2" ></ui:button>
							</kmss:auth>
							<c:import url="/sys/right/cchange_tmp_right/cchange_tmp_right_button_new.jsp" charEncoding="UTF-8">
								<c:param name="mainModelName" value="com.landray.kmss.fssc.fee.model.FsscFeeMain" />
								<c:param name="tmpModelName" value="com.landray.kmss.fssc.fee.model.FsscFeeTemplate" />
								<c:param name="templateName" value="fdTemplate" />
								<c:param name="authReaderNoteFlag" value="2" />
							</c:import>
							<c:import url="/sys/workflow/import/sysWfTemplate_auditorBtn.jsp" charEncoding="UTF-8">
								<c:param name="fdModelName" value="com.landray.kmss.fssc.fee.model.FsscFeeTemplate"/>
								<c:param name="cateid" value="${param.parentId}"/>
							</c:import>
							<kmss:auth requestURL="/fssc/fee/fssc_fee_template/fsscFeeTemplate.do?method=deleteall&parentId=${param.parentId}" requestMethod="GET">
								<!-- 快速排序 -->
								<c:import url="/sys/profile/common/change_order_num.jsp" charEncoding="UTF-8">
									<c:param name="modelName" value="com.landray.kmss.fssc.fee.model.FsscFeeTemplate"></c:param>
									<c:param name="property" value="fdOrder"></c:param>
								</c:import>
							</kmss:auth>
							<c:import url="/sys/xform/lang/include/sysFormCommonMultiLang_button_new.jsp" charEncoding="UTF-8">
								<c:param name="fdModelName" value="com.landray.kmss.fssc.fee.model.FsscFeeTemplate" />
								<c:param name="isCommonTemplate" value ="false"/>
							</c:import>

                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/fssc/fee/fssc_fee_template/fsscFeeTemplate.do?method=data&parentId=${param.parentId }')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/fssc/fee/fssc_fee_template/fsscFeeTemplate.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdName;docCategory" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.fssc.fee.model.FsscFeeTemplate',
                templateName: '',
                basePath: '/fssc/fee/fssc_fee_template/fsscFeeTemplate.do',
                canDelete: '${canDelete}',
                mode: 'config_template',
                templateService: '',
                templateAlert: '${lfn:message("fssc-fee:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/fssc/fee/resource/js/", 'js', true);
seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		
		 		// 新建申请
		 		window.addDoc = function(fdId) {
		 			Com_OpenWindow('<c:url value="/km/review/km_review_main/kmReviewMain.do" />?method=add&fdTemplateId='+fdId);
		 		};
		 		
			 	// 增加
		 		window.add = function() {
		 			Com_OpenWindow('<c:url value="/fssc/fee/fssc_fee_template/fsscFeeTemplate.do" />?method=add&parentId=${param.parentId}');
		 		};
		 	    // 编辑
		 		window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/fssc/fee/fssc_fee_template/fsscFeeTemplate.do" />?method=edit&fdId=' + id);
		 		};
		 		 // 搜索设置
		 		window.setSearch = function(fdTemplateId,fdTemplateName) {
		 			 
		 			 Com_OpenWindow('<c:url value="/sys/search/sys_search_main/sysSearchMain.do" />?method=add&fdModelName=com.landray.kmss.fssc.fee.model.FsscFeeMain&fdKey=fsscFeeMain&fdTemplateId=' + fdTemplateId+'&fdTemplateName='+encodeURIComponent(fdTemplateName));
		 			 
		 		};
		 		
		 		window.deleteDoc = function(id){
		 			var url = '<c:url value="/fssc/fee/fssc_fee_template/fsscFeeTemplate.do?method=delete"/>';
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.ajax({
								url: url,
								type: 'GET',
								data:{fdId:id},
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
		 		
		 		
		 		window.deleteAll = function(id){
					var values = [];
					if(id) {
		 				values.push(id);
			 		} else {
						$("input[name='List_Selected']:checked").each(function() {
							values.push($(this).val());
						});
			 		}
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var url = '<c:url value="/fssc/fee/fssc_fee_template/fsscFeeTemplate.do?method=deleteall"/>&parentId=${param.parentId}';
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
				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});
		 	});
        </script>
    </template:replace>
</template:include>
