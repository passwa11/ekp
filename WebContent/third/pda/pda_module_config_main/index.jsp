<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	String wxEnabled= ResourceUtil.getKmssConfigString("kmss.third.wx.enabled");
	if("true".equals(wxEnabled)){
		String title = ResourceUtil.getString(request,"third.wx.menu.cfg.btn.publish","third-weixin");
		request.setAttribute("wxEnabled", true);
		request.setAttribute("wxTitle", title);
	}else{
		request.setAttribute("wxEnabled", false);
	}	
%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria">
			<list:cri-ref style="width:145px;" key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('third-pda:pdaMoudleCategoryList.fdName') }"></list:cri-ref>
			<!-- 分类筛选器 -->
			<list:cri-criterion title="${lfn:message('third-pda:module.third.tree.moduleCategory') }" key="fdModuleCate">
				<list:box-title>
					<div style="line-height: 30px">
						${lfn:message('third-pda:module.third.tree.moduleCategory') }
					</div>
					<div class="person">
						<list:item-search width="50px" height="22px">
							<ui:event event="search.changed" args="evt">
								var se = this.parent.parent.selectBox.criterionSelectElement;
								var source = se.source;
								if(evt.searchText){
									evt.searchText = encodeURIComponent(evt.searchText);
								}
								source.resolveUrl(evt);
								source.get();
							</ui:event>
						</list:item-search>
					</div>
				</list:box-title>
				<list:box-select style="min-height:60px">
					<list:item-select type="lui/criteria/select_panel!CriterionSelectDatas">
						<ui:source type="AjaxJson">
							{url: "/third/pda/pda_module_cate/pdaModuleCate.do?method=criteria&q.fdName=!{searchText}"}
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
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
						<list:sort property="fdOrder" text="${lfn:message('third-pda:pdaModuleConfigMain.fdOrder') }" group="sort.list" value="up"></list:sort>
						<list:sort property="fdCreateTime" text="${lfn:message('third-pda:pdaModuleConfigMain.fdCreateTime') }" group="sort.list" ></list:sort>
						<list:sort property="fdName" text="${lfn:message('third-pda:pdaModuleConfigMain.fdName') }" group="sort.list"></list:sort>
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
					<ui:toolbar id="Btntoolbar" count="4">
						<kmss:auth requestURL="/third/pda/pda_module_config_main/pdaModuleConfigMain.do?method=add">
						    <ui:button text="${lfn:message('button.add')}" onclick="add();" order="1" ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/third/pda/pda_module_config_main/pdaModuleConfigMain.do?method=deleteall">
						    <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll();" order="2" ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/third/pda/pda_module_config_main/pdaModuleConfigMain.do?method=update">
							<ui:button text="${lfn:message('third-pda:pdaModuleConfigMain.status.enable')}" onclick="updateEnable(true);" order="3" ></ui:button>
							<ui:button text="${lfn:message('third-pda:pdaModuleConfigMain.status.disable')}" onclick="updateEnable(false);" order="3" ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/third/pda/pda_module_config_main/pdaModuleConfigMain.do?method=deleteall">
							<!-- 快速排序 -->
							<c:import url="/sys/profile/common/change_order_num.jsp" charEncoding="UTF-8">
								<c:param name="modelName" value="com.landray.kmss.third.pda.model.PdaModuleConfigMain"></c:param>
								<c:param name="property" value="fdOrder"></c:param>
							</c:import>
						</kmss:auth>	
						<kmss:auth requestURL="/third/pda/pda_module_config_main/pdaModuleConfigMain.do?method=updateIconVersion">
							<ui:button text="${lfn:message('third-pda:pdaModuleConfigMain.updateVersion')}" onclick="updateVersion();" order="5" ></ui:button>				
							<c:if test="${ wxEnabled == true }">
								<ui:button text="${wxTitle}" onclick="wxMenuEdit();" order="4" ></ui:button>	
							</c:if>
						</kmss:auth>
						<!-- 推送应用到钉钉 -->
						<kmss:ifModuleExist  path = "/third/ding/">
							<c:import url="/third/ding/common/syn_app_button.jsp" charEncoding="UTF-8">
								<c:param name="buttonType" value="1"></c:param>
							</c:import>
						</kmss:ifModuleExist>
					</ui:toolbar>
				</div>
			</div>
		</div>
	
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/third/pda/pda_module_config_main/pdaModuleConfigMain.do?method=list&contentType=json'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable"
				rowHref="/third/pda/pda_module_config_main/pdaModuleConfigMain.do?method=view&fdId=!{fdId}" name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-auto props="fdOrder,fdName,fdModuleCate.fdName,fdUrlPrefix,fdIconUrl,fdStatus,docCreator.fdName,fdCreateTime,operations"></list:col-auto>
			</list:colTable>
			<ui:event topic="list.loaded">
				Dropdown.init();
			</ui:event>
		</list:listview>
		<!-- 分页 -->
	 	<list:paging/>
	
		<script type="text/javascript">
			seajs.use(['lui/jquery','lui/topic','lui/dialog'],function($,topic,dialog){
				
				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});
				
				//新建
				window.add = function(){
					Com_OpenWindow('<c:url value="/third/pda/pda_module_config_main/pdaModuleConfigMain.do" />?method=add');
				};

		 	    // 编辑
		 		window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/third/pda/pda_module_config_main/pdaModuleConfigMain.do" />?method=edit&fdId=' + id);
		 		};
		 		
				//删除
				window.deleteAll = function(id){
		 			var values = [];
					if(id) {
		 				values.push(id);
			 		} else {
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
							$.post('<c:url value="/third/pda/pda_module_config_main/pdaModuleConfigMain.do?method=deleteall"/>',
									$.param({"List_Selected":values},true),function(data){
								if(window.del_load!=null)
									window.del_load.hide();
								if(data!=null && data.status==true){
									topic.publish("list.refresh");
									dialog.success('<bean:message key="return.optSuccess" />');
								}else{
									dialog.failure('<bean:message key="return.optFailure" />');
								}
							},'json');
						}
					});
				};
				
				//启用or禁用
				window.updateEnable = function(isEnable,id){
		 			var values = [];
					if(id) {
		 				values.push(id);
			 		} else {
						$("input[name='List_Selected']:checked").each(function(){
							values.push($(this).val());
						});
			 		}
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var confirmMsg = '${lfn:message("third-pda:pdaModuleConfigMain.opt.enable.desc")}'
						fdEnabled = 1;
					if(!isEnable){
						confirmMsg = '${lfn:message("third-pda:pdaModuleConfigMain.opt.disable.desc")}';
						fdEnabled = 0;
					}
					dialog.confirm(confirmMsg,function(confirm){
						if(confirm){
							window._loading = dialog.loading();
							$.post('<c:url value="/third/pda/pda_module_config_main/pdaModuleConfigMain.do?method=updateStatus"/>',
									$.param({"fdEnabled":fdEnabled,"List_Selected":values},true),function(data){
								if(window._loading!=null)
									window._loading.hide();
								if(data!=null && data.status==true){
									topic.publish("list.refresh");
									dialog.success('<bean:message key="return.optSuccess" />');
								}else{
									dialog.failure('<bean:message key="return.optFailure" />');
								}
							},'json');
						}
					});
				};
				
				window.updateVersion = function(){
					Com_OpenWindow('<c:url value="/third/pda/pda_module_config_main/pdaModuleConfigMain.do" />?method=updateIconVersion','_self');
				};
				
				window.wxMenuEdit = function(){
					Com_OpenWindow('<c:url value="/third/wx/menu/wxMenu.do" />?method=edit');
				};
				
			});
		</script>
	
	</template:replace>
</template:include>