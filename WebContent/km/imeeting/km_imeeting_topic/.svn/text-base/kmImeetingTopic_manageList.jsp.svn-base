<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<script type="text/javascript">
			seajs.use(['theme!list']);	
		</script>
	  <list:criteria id="sendCriteria">
	 		<list:cri-ref key="docSubject" ref="criterion.sys.docSubject"> 
			</list:cri-ref>
			<%-- 状态 --%>
			<list:cri-criterion title="${ lfn:message('km-imeeting:kmImeetingSummary.docStatus') }" key="docStatus">
				<list:box-select>
					<list:item-select>
						<ui:source type="Static">
							[{text:'${ lfn:message('status.draft')}', value:'10'},
							 {text:'${ lfn:message('status.examine')}',value:'20'},
							 {text:'${ lfn:message('status.refuse')}',value:'11'},
							 {text:'${ lfn:message('status.publish')}',value:'30'}]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<list:cri-auto modelName="com.landray.kmss.km.imeeting.model.KmImeetingTopic" property="docCreateTime;fdIsAccept" />
		</list:criteria>
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
									<list:sort property="docCreateTime" text="${lfn:message('km-imeeting:kmImeetingTopic.docCreateTime')}" group="sort.list" value="down"></list:sort>
								    <list:sort property="fdNo" text="${lfn:message('km-imeeting:kmImeetingTopic.fdNo')}" group="sort.list"></list:sort>
							    </list:sortgroup>
							</ui:toolbar>
						</div>
					</div>
					<!-- 分页 -->
					<div class="lui_list_operation_page_top">	
						<list:paging layout="sys.ui.paging.top" > 		
						</list:paging>
					</div>	
					<div style="float:right">
						<div style="display: inline-block;vertical-align: middle;"> 
						<ui:toolbar count="3" id="Btntoolbar">
							<c:if test="${not empty JsParam.fdTemplateId}">
								<kmss:auth requestURL="/km/imeeting/km_imeeting_topic/kmImeetingTopic.do?method=add&fdTemplateId=${JsParam.fdTemplateId}">
								   <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="1"></ui:button>
								</kmss:auth>
							</c:if>
							<c:if test="${empty JsParam.docCategoryId}">
								<kmss:auth requestURL="/km/imeeting/km_imeeting_topic/kmImeetingTopic.do?method=add">
							    	<ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="1"></ui:button>
								</kmss:auth>
							</c:if>
							<kmss:auth requestURL="/km/imeeting/km_imeeting_topic/kmImeetingTopic.do?method=deleteall&categoryId=${JsParam.categoryId}&nodeType=${JsParam.nodeType}">
							  <ui:button id="del" text="${lfn:message('button.deleteall')}" order="3" onclick="delDoc();"></ui:button>
							</kmss:auth>
						</ui:toolbar>
				  </div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<list:listview id="listview_send">
			<ui:source type="AjaxJson">
					{url:'/km/imeeting/km_imeeting_topic/kmImeetingTopic.do?method=manageList&categoryId=${JsParam.categoryId}'}
			</ui:source>
			<list:colTable  isDefault="false" layout="sys.ui.listview.columntable" 
				rowHref="/km/imeeting/km_imeeting_topic/kmImeetingTopic.do?method=view&fdId=!{fdId}"  name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props=""></list:col-auto>
			</list:colTable>
		</list:listview> 
	 	<list:paging></list:paging>	 
	 	<script type="text/javascript">
	 	var SYS_SEARCH_MODEL_NAME ="com.landray.kmss.km.imeeting.model.KmImeetingMain;com.landray.kmss.km.imeeting.model.KmImeetingSummary;com.landray.kmss.km.imeeting.model.KmImeetingTopic";
		
		seajs.use(['lui/jquery', 'lui/util/str', 'lui/dialog','lui/topic','lui/toolbar'], function($, strutil, dialog , topic ,toolbar) {
			
			// 监听新建更新等成功后刷新
			topic.subscribe('successReloadPage', function() {
				topic.publish('list.refresh');
			});
			
			
			var docCategoryId = '${JsParam.categoryId}';
	 		//新建
			window.addDoc = function() {
				dialog.simpleCategoryForNewFile(
						'com.landray.kmss.km.imeeting.model.KmImeetingTopicCategory',
						'/km/imeeting/km_imeeting_topic/kmImeetingTopic.do?method=add&fdTemplateId=!{id}',false,null,null,null);
			};
			//删除回调	
			window.delCallback = function(data){
				if(window.del_load!=null)
					window.del_load.hide();
				if(data!=null && data.status==true){
					topic.publish("list.refresh");
					dialog.success('<bean:message key="return.optSuccess" />');
				}else{
					dialog.failure('<bean:message key="return.optFailure" />');
				}
			};
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
				var config = {
						url : '${LUI_ContextPath}/km/imeeting/km_imeeting_topic/kmImeetingTopic.do?method=deleteall&categoryId=${JsParam.categoryId}', // 删除数据的URL
						data : $.param({"List_Selected":values},true), // 要删除的数据
						modelName : "com.landray.kmss.km.imeeting.model.KmImeetingTopic" 
					};
				// 通用删除方法
				Com_Delete(config, delCallback);
			};
		});
		</script>
	</template:replace>	 
</template:include>