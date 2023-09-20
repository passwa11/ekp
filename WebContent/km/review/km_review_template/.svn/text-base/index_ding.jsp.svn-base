<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.xform.util.SysFormDingUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<script type="text/javascript">
		Com_IncludeFile("dialog.js");
	</script>

	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/km/review/km_review_ui/dingSuit/css/template.css?s_cache=${LUI_Cache }"/>
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/ui/extend/theme/default/style/ding_list.css?s_cache=${LUI_Cache }"/>
	</template:replace>
	<template:replace name="path">
	</template:replace>
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria">
		     <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('km-review:kmReviewTemplate.fdName') }">
			</list:cri-ref>
			<list:cri-ref ref="criterion.sys.person" key="docCreator" multi="false" title="${lfn:message('km-review:kmReviewTemplate.docCreatorId') }" />
			<%-- 搜索条件:是否有效 --%>
			<list:cri-criterion title="${ lfn:message('km-review:kmReviewTemplate.fdStatus')}" key="fdIsAvailable" multi="false" >
				<list:box-select>
					<list:item-select cfg-defaultValue="1">
						<ui:source type="Static">
							[{text:'${ lfn:message('km-review:kmReviewTemplate.fdIsAvailable.true')}', value:'1'},
							{text:'${ lfn:message('km-review:kmReviewTemplate.fdIsAvailable.false')}',value:'0'}]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<list:cri-auto modelName="com.landray.kmss.km.review.model.KmReviewTemplate" property="docCreateTime"/>
		</list:criteria>
		<!-- 操作栏 -->
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
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
					    <list:sortgroup>
						    <list:sort property="fdOrder" text="${lfn:message('km-review:kmReviewTemplate.fdOrder') }" group="sort.list" value="up"></list:sort>
							<list:sort property="docCreateTime" text="${lfn:message('km-review:kmReviewTemplate.docCreateTime') }" group="sort.list"></list:sort>
							<list:sort property="fdName" text="${lfn:message('km-review:kmReviewTemplate.fdName') }" group="sort.list"></list:sort>
						</list:sortgroup>
					</ui:toolbar>
				</div>
			</div>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
			<!-- 操作按钮 -->
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar id="Btntoolbar" count="2">
					    <kmss:auth requestURL="/km/review/km_review_template/kmReviewTemplate.do?method=add&parentId=${param.parentId}" requestMethod="GET">
							<ui:button text="${lfn:message('button.add')}"  onclick="addNew();" order="1" ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/km/review/km_review_template/kmReviewTemplate.do?method=deleteall&parentId=${param.parentId}" requestMethod="GET">
							<ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll();" order="2" ></ui:button>
						</kmss:auth>
						
						<%-- <c:import url="/sys/right/cchange_tmp_right/cchange_tmp_right_button_new.jsp" charEncoding="UTF-8">
							<c:param name="mainModelName" value="com.landray.kmss.km.review.model.KmReviewMain" />
							<c:param name="tmpModelName" value="com.landray.kmss.km.review.model.KmReviewTemplate" />
							<c:param name="templateName" value="fdTemplate" />
							<c:param name="categoryId" value="${param.parentId}" />
							<c:param name="authReaderNoteFlag" value="2" />
						</c:import>
						<c:import url="/sys/workflow/import/sysWfTemplate_auditorBtn.jsp" charEncoding="UTF-8">
							<c:param name="fdModelName" value="com.landray.kmss.km.review.model.KmReviewTemplate"/>
							<c:param name="cateid" value="${param.parentId}"/>
						</c:import>
						<kmss:auth requestURL="/km/review/km_review_template/kmReviewTemplate.do?method=deleteall&parentId=${param.parentId}" requestMethod="GET">
							<!-- 快速排序 -->
							<c:import url="/sys/profile/common/change_order_num.jsp" charEncoding="UTF-8">
								<c:param name="modelName" value="com.landray.kmss.km.review.model.KmReviewTemplate"></c:param>
								<c:param name="property" value="fdOrder"></c:param>
							</c:import>
						</kmss:auth>
						<c:import url="/sys/xform/lang/include/sysFormCommonMultiLang_button_new.jsp" charEncoding="UTF-8">
							<c:param name="fdModelName" value="com.landray.kmss.km.review.model.KmReviewTemplate" />
							<c:param name="isCommonTemplate" value ="false"/>
						</c:import>
						
						<kmss:auth
							requestURL="/km/review/km_review_template/kmReviewTemplate.do?method=batchClone&parentId=${param.parentId}"
							requestMethod="GET">
							<ui:button text="${lfn:message('button.batchcopy')}" onclick="batchCopyTemplate();" order="9" ></ui:button>
						</kmss:auth> --%>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/km/review/km_review_template/kmReviewTemplate.do?method=listChildren&parentId=${param.parentId}&ower=1'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
			    rowHref="/km/review/km_review_template/kmReviewTemplate.do?method=view&fdId=!{fdId}">
				<list:col-checkbox></list:col-checkbox>
				<list:col-auto props="fdOrder,fdName,fdIsAvailable,docCreator.fdName,docCreateTime,operations"></list:col-auto>
			</list:colTable>
			<ui:event topic="list.loaded">
				Dropdown.init();
			</ui:event>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		
		 		// 新建申请
		 		window.addDoc = function(fdId) {
		 			Com_OpenWindow('<c:url value="/km/review/km_review_main/kmReviewMain.do" />?method=add&fdTemplateId='+fdId);
		 		};
		 		
		 		window.addNew = function() {
		 			var url = '/km/review/km_review_template/kmReviewTemplate.do?method=openTemplate' +
		 					  '&fdModelName=com.landray.kmss.km.review.model.KmReviewTemplate&parentId=${param.parentId}' +
							 '&fdKey=reviewMainDoc&fdMainModelName=com.landray.kmss.km.review.model.KmReviewMain&addOptionType=';
					url += encodeURIComponent("km-review:kmReviewDocumentLableName.wordStype|5");
		 			dialog.iframe(url,'<bean:message key="km-review:kmReviewTemplate.create"/>',function(value) {
		 								}, {
					    					"width" : 920,
					    					"height" : 680
					    				});
		 		}
		 		
			 	// 增加
		 		window.add = function() {
		 			Com_OpenWindow('<c:url value="/km/review/km_review_template/kmReviewTemplate.do" />?method=add&parentId=${param.parentId}');
		 		};
		 	    // 编辑
		 		window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/km/review/km_review_template/kmReviewTemplate.do" />?method=edit&fdId=' + id);
		 		};
		 		 // 搜索设置
		 		window.setSearch = function(fdTemplateId,fdTemplateName) {
		 			 
		 			 Com_OpenWindow('<c:url value="/sys/search/sys_search_main/sysSearchMain.do" />?method=add&fdModelName=com.landray.kmss.km.review.model.KmReviewMain&fdKey=reviewMainDoc&fdTemplateId=' + fdTemplateId+'&fdTemplateName='+encodeURIComponent(fdTemplateName));
		 			 
		 		};
		 		
		 		
		 		
		 		//调整属性验证
				function editProValidate(_winObj) {
					
					//验证必填项
				 	if(_winObj != null  &&_winObj.Com_Parameter.event["submit"].length>0) {
				 		for(var i=0;i<_winObj.Com_Parameter.event["submit"].length;i++){
				 			if(!_winObj.Com_Parameter.event["submit"][i]()){
				 				return false;
				 			}
			 			}
				 	}
				 	
					return true;
				}
				
				//调整属性异步处理
				  function editProSubmit(_obj,ops) {
					    if(ops){
					    	var cloneModelIds="";
		            		for(var i=0;i<ops.length;i++){
		            			if(i==0){
		            				cloneModelIds=cloneModelIds+ops[i];
		            			}else{
		            				cloneModelIds=cloneModelIds+","+ops[i];
		            			}
		            		}
		            		$(_obj.cloneModelIds).val(cloneModelIds);
		            	}
					 	
					 	var editFlag;
					 	LUI.$.ajax({
							url: '${ LUI_ContextPath}/km/review/km_review_template/kmReviewTemplate.do?method=batchClone&parentId=${param.parentId}',
							type: 'POST',
							dataType: 'json',
							async: false,
							data: LUI.$(_obj).serialize(),
							success: function(data, textStatus, xhr) {
								if (data && data['status'] === true) {
									//调整成功
									editFlag = 'yes';
								}
							},
							error: function(data, textStatus, errorThrown) {
								if(data.responseJSON.status==403){
									editFlag="权限不足!"
									return;
								}
								//调整失败
								var resultCode =data.responseJSON.message;
								var resultMessage="";
								for(var i = 0; i<resultCode.length; i++){
									resultMessage+=resultCode[i].msg;
								}
								editFlag = data.responseJSON.title+resultMessage;
							}
						});
					 	
						if(editFlag != null) 
							return editFlag;
						
						
				}
				
				
		 		//打开消息自定义窗口
				  window.batchCopyTemplate=function(){
						var values = [];
		     			$("input[name='List_Selected']:checked").each(function(){
		     				values.push($(this).val());
		     			});
		     			
						if(values.length==0){
							dialog.alert('<bean:message key="page.noSelect"/>');
		     				return;
		     			}
						
						if(values.length>50){
							dialog.alert("${lfn:message('km-review:kmReviewTemplate.message.batchCopyTemplate.selectMoreFifty')}");
		     				return;
		     			}
						
						console.log(values);
						 var isSubmitClick = true;
						 
						dialog.iframe('/km/review/km_review_template/batch_copy_template.jsp',
					            "${lfn:message('km-review:kmReviewTemplate.message.batchCopyTemplate.copyTemplate')}" ,null, { width: window.screen.width*872/1366, height: window.screen.height*616/768,params:values,buttons:[
									{
										name : "${lfn:message('button.ok')}",
										value : true,
										focus : true,
										fn : function(value,_dialog) {
											
											 if (isSubmitClick) {
												 isSubmitClick = false;
												//获取弹出窗口的window对象
												var winObj = LUI.$('[id="dialog_iframe"]',_dialog.element).find('iframe')[0].contentWindow; 
												//验证
												if(!editProValidate(winObj)) {
													return;
												}
												
												var loading = dialog.loading();
												//获取弹出窗口的document对象里面的form
												var proObj =LUI.$('[id="dialog_iframe"]',_dialog.element).find('iframe')[0].contentDocument.getElementsByName('kmReviewTemplateForm')[0];
												var eFlag = editProSubmit(proObj,values);
												if(eFlag != null && eFlag=='yes'){
													_dialog.hide();
													loading.hide();
													dialog.success("${lfn:message('km-review:kmReviewTemplate.message.batchCopyTemplate.sucess')}");
													topic.publish('list.refresh');											
												} else {
													_dialog.hide();
													loading.hide();
													dialog.success(eFlag);
												}
													
													
												 setTimeout(function () {
									                    isSubmitClick = true;
									             }, 10000);
												 
											 }
										}
									}, {
										name : "${lfn:message('button.cancel')}",
										value : false,
										styleClass : 'lui_toolbar_btn_gray',
										fn : function(value, _dialog) {
											_dialog.hide();
										}
									} 
								] }
					     );
						
						
						/*var kmssDialog = new KMSSDialog();
						var valueData = new KMSSData();
						valueData.AddHashMap(values);
						kmssDialog.AddDefaultValue(valueData);
						kmssDialog.URL = Com_Parameter.ContextPath + "km/review/km_review_template/batch_copy_template.jsp";

						kmssDialog.Show(window.screen.width*872/1366,window.screen.height*616/768);*/
					}
		 		
		 		window.deleteDoc = function(id){
		 			var url = '<c:url value="/km/review/km_review_template/kmReviewTemplate.do?method=delete"/>';
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
					var url = '<c:url value="/km/review/km_review_template/kmReviewTemplate.do?method=deleteall"/>&parentId=${param.parentId}';
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
