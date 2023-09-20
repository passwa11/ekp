<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.simple4list" spa="true">
	 <template:replace name="title">${ lfn:message('sys-lbpmext-attention:table.lbpmExtAttention') }</template:replace>
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/profile/resource/css/operations.css?s_cache=${LUI_Cache}"/>
	</template:replace>
	<template:replace name="path">
		<div style="padding: 5px 10px 3px;font-size: 16px;">
				${ lfn:message('sys-lbpmext-attention:table.lbpmExtAttention') }
			</div>
	</template:replace>
	<template:replace name="content">
		<div>
		  <ui:tabpanel id="attentionTabpanel" layout="sys.ui.tabpanel.list">
			 <ui:content id="myAttentionContent" title="${ lfn:message('sys-lbpmext-attention:lbpmExtAttention.myAttention') }" >
			  <!-- 筛选 -->
            <list:criteria id="criteria1" channel="list_toview">
                <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-lbpmext-attention:lbpmExtAttention.fdPerson')}" />
                <list:cri-criterion title="${lfn:message('sys-lbpmext-attention:lbpmExtAttention.fdModuleName') }" expand="false" multi="false" key="fdModuleName">
					<list:box-title>
						<div class="criterion-title-popup-div">
						 <ui:menu layout="sys.ui.menu.nav"> 
							<ui:menu-item text="${lfn:message('sys-lbpmext-attention:lbpmExtAttention.fdModuleName') }">
								<ui:menu-source autoFetch="true" 
									href="javascript:luiCriteriaTitlePopupItemClick('list_toview', 'fdModuleName', '!{value}');">
									<ui:source type="AjaxJson">
										{"url":"/sys/lbpmext/attention/lbpmExtAttention.do?method=criteria&key=${criterionAttrs['key']}&channel=${criteriaAttrs['channel'] }&authType=2&parentId=!{value}"} 
									</ui:source>
								</ui:menu-source>
							</ui:menu-item>
						</ui:menu>
						</div>
					</list:box-title>
					<list:box-select>
						<list:item-select type="lui/criteria!CriterionHierarchyDatas">
							<ui:source type="AjaxJson">
								{url: "/sys/lbpmext/attention/lbpmExtAttention.do?method=criteria&authType=2&parentId=!{value}"}
							</ui:source>
						</list:item-select>
					</list:box-select>
				</list:cri-criterion>
                <list:cri-auto modelName="com.landray.kmss.sys.lbpmext.attention.model.LbpmExtAttention" property="docCreateTime" channel="list_toview"/>

            </list:criteria>
          
            <!-- 操作 -->
            <div class="lui_list_operation">
            	<!-- 全选 -->
					<div class="lui_list_operation_order_btn">
						<list:selectall channel="list_toview"></list:selectall>
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
							    <list:sort property="lbpmExtAttention.docCreateTime" text="${lfn:message('sys-lbpmext-attention:lbpmExtAttention.docCreateTime')}" group="sort.list" channel="list_toview"/>
							</ui:toolbar>
						</div>
					</div>
					<!-- 分页 -->
					<div class="lui_list_operation_page_top">	
						<list:paging layout="sys.ui.paging.top" channel="list_toview"> 		
						</list:paging>
					</div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">
							<ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="4" id="btnAdd" channel="list_toview"/>
                            <kmss:auth requestURL="/sys/lbpmext/attention/lbpmExtAttention.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <!---->
                            <ui:button text="批量取消" onclick="delDoc()" order="4" id="btnDelete" channel="list_toview"/>
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview" channel="list_toview">
                <ui:source type="AjaxJson">
                    {url:'/sys/lbpmext/attention/lbpmExtAttention.do?method=data&dataType=1'}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/sys/lbpmext/attention/lbpmExtAttention.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdPerson.name;fdPerson.deptLevelNames;fdModuleName;docCreateTime;operations" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
			 	<list:paging channel="list_toview"></list:paging>
			  </ui:content>

				<ui:content id="attentionToMeContent" title="${ lfn:message('sys-lbpmext-attention:lbpmExtAttention.attentionToMe') }"  >
				 <!-- 筛选 -->
            <list:criteria id="criteria2" channel="list_attention_to_me">
                <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-lbpmext-attention:lbpmExtAttention.fdPerson')}" />
               <%--  <list:cri-ref key="docCreator" ref="criterion.sys.person" multi="false" title="${lfn:message('sys-lbpmext-attention:lbpmExtAttention.docCreator') }" /> --%>
                 <list:cri-criterion title="${lfn:message('sys-lbpmext-attention:lbpmExtAttention.fdModuleName') }" expand="false" multi="false" key="fdModuleName">
					<list:box-title>
						<div class="criterion-title-popup-div">
						 <ui:menu layout="sys.ui.menu.nav"> 
							<ui:menu-item text="${lfn:message('sys-lbpmext-attention:lbpmExtAttention.fdModuleName') }">
								<ui:menu-source autoFetch="true" 
									href="javascript:luiCriteriaTitlePopupItemClick('list_attention_to_me', 'fdModuleName', '!{value}');">
									<ui:source type="AjaxJson">
										{"url":"/sys/lbpmext/attention/lbpmExtAttention.do?method=criteria&key=${criterionAttrs['key']}&channel=${criteriaAttrs['channel'] }&authType=2&parentId=!{value}"} 
									</ui:source>
								</ui:menu-source>
							</ui:menu-item>
						</ui:menu>
						</div>
					</list:box-title>
					<list:box-select>
						<list:item-select type="lui/criteria!CriterionHierarchyDatas">
							<ui:source type="AjaxJson">
								{url: "/sys/lbpmext/attention/lbpmExtAttention.do?method=criteria&authType=2&parentId=!{value}"}
							</ui:source>
						</list:item-select>
					</list:box-select>
				</list:cri-criterion>
                <list:cri-auto modelName="com.landray.kmss.sys.lbpmext.attention.model.LbpmExtAttention" property="docCreateTime" channel="list_attention_to_me"/>

            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style='color: #979797;float: left;padding-top:1px;'>
                    ${ lfn:message('list.orderType') }：
                </div>
                <div style="float:left">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="lbpmExtAttention.docCreateTime" text="${lfn:message('sys-lbpmext-attention:lbpmExtAttention.docCreateTime')}" group="sort.list" channel="list_attention_to_me"/>
                        </ui:toolbar>
                    </div>
                </div>
                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" channel="list_attention_to_me"/>
            <!-- 列表 -->
            <list:listview id="listviewToMe" channel="list_attention_to_me">
                <ui:source type="AjaxJson">
                    {url:'/sys/lbpmext/attention/lbpmExtAttention.do?method=data&dataType=2'}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/sys/lbpmext/attention/lbpmExtAttention.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-serial/>
                    <list:col-auto props="docCreator.name;docCreator.deptLevelNames;fdModuleName;docCreateTime" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
					<list:paging channel="list_attention_to_me"></list:paging>	
				 </ui:content>
		  	 
			<script type="text/javascript">
				seajs.use(['lui/jquery','lui/dialog','lui/topic', 'lang!sys-ui'], function($, dialog , topic,ui_lang) {
					LUI.ready(function(){
						var dataType = "${JsParam.dataType}";
						if(dataType==""){
							dataType = Com_GetUrlParameter(window.location.href,"dataType");
						}
						if(dataType){
							var tome = dataType && dataType.indexOf('tome')!=-1;
							var index = 0;
							if(tome){
								index = 1;
							}else{
								index = 0;
							}
							LUI('attentionTabpanel').selectedIndex=index;
						}				
					});
					window.addDoc = function(){
						Com_OpenWindow('<c:url value="/sys/lbpmext/attention/lbpmExtAttention.do?method=add"/>');
					}
					window.editDoc = function(id){
						if(id){
							Com_OpenWindow('<c:url value="/sys/lbpmext/attention/lbpmExtAttention.do?method=edit&fdId="/>'+id);
						}
					}
					//删除
					window.delDoc = function(id){
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
						dialog.confirm('<bean:message bundle="sys-lbpmext-attention" key="lbpmExtAttention.confirm.finish"/>',function(value){
							if(value==true){
								window.del_load = dialog.loading();
								$.post('<c:url value="/sys/lbpmext/attention/lbpmExtAttention.do?method=deleteall"/>',
										$.param({"List_Selected":values},true),SdelCallback,'json');
							}
						});
					};
					window.SdelCallback = function(data){
						if(window.del_load!=null)
							window.del_load.hide();
						if(data!=null && data.status==true){
							topic.channel('list_toview').publish('list.refresh');
							dialog.success('<bean:message key="return.optSuccess" />');
						}else{
							dialog.failure('<bean:message key="return.optFailure" />');
						}
					};
					//审批等操作完成后，自动刷新列表
					topic.subscribe('successReloadPage', function() {
						topic.channel('list_toview').publish('list.refresh');
					});
				});
			</script>
		  </ui:tabpanel>
	  </div> 
	</template:replace>
</template:include>