<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<div style="width:100%;overflow: hidden;">
				<ui:toolbar style="float:right;">
					<kmss:auth requestURL="/sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize.do?method=add" requestMethod="GET">
						<ui:button text="${lfn:message('button.add')}" 
							onclick="Com_OpenWindow('${LUI_ContextPath}/sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize.do?method=add');">
						</ui:button>
					</kmss:auth>
				
				</ui:toolbar>
				</div>
				<list:listview>
					<ui:source type="AjaxJson">
							{url:'/sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize.do?method=list&orderby=fdStartTime&ordertype=down&forward=listUi'}
					</ui:source>
					  <!-- 列表视图 -->	
					<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
						rowHref="/sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize.do?method=view&fdId=!{fdId}"  name="columntable">
						<list:col-serial></list:col-serial> 
						<list:col-auto props="fdStartTime;fdEndTime;fdAuthorizer.fdName;fdAuthorizedPerson.fdName;fdAuthorizeStatus;fdAuthorizeType"></list:col-auto>
					</list:colTable>
					<ui:event topic="list.loaded">
				if(typeof(Dropdown) != 'undefined'){
					Dropdown.init();
				}
				 seajs.use(['lui/jquery'],function($){
											if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
											
												setTimeout(function(){
													window.frameElement.style.height =  $(document.body).height() +10+ "px";
												},100);
											}
				});
			</ui:event>
				</list:listview>
				<list:paging></list:paging>	
		<script type="text/javascript">
			domain.autoResize();
			seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic) {

				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});
				
				//删除
				window.delDoc = function(){
					var values = [];
					$("input[name='List_Selected']:checked").each(function(){
							values.push($(this).val());
						});
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
						if(value==true){
							var del_load = dialog.loading();
							$.post('<c:url value="/sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize.do?method=deleteall"/>',
									$.param({"List_Selected":values},true),function(data){
									if(del_load!=null)
										del_load.hide();
									if(data!=null && data.status==true){
										topic.publish("list.refresh");
										dialog.success('<bean:message key="return.optSuccess" />');
									}else{
										dialog.failure('<bean:message key="return.optFailure" />');
									}
								},'json').error(function(){
									dialog.failure('<bean:message key="errors.noRecord" />');
									if(del_load!=null)
										del_load.hide();
									topic.publish("list.refresh");
								});
						}
					});
				};
			});
		</script>
