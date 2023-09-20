<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="person.home"> 
	<template:replace name="title">${ lfn:message('km-forum:home.nav.kmForum') }</template:replace>
	<template:replace name="content">
		<c:set var="navTitle" value="${lfn:message('km-forum:menu.kmForum.my') }"></c:set>
		<c:if test="${not empty param.navTitle }">
			<c:set var="navTitle" value="${param.navTitle}"></c:set>
		</c:if>
		<ui:tabpanel layout="sys.ui.tabpanel.list">
			<ui:content title="${navTitle }">
				<list:criteria id="criteria1" expand="false">
					<list:tab-criterion title="" key="topic" multi="false">
						<list:box-select>
							<list:item-select  type="lui/criteria/select_panel!TabCriterionSelectDatas"  cfg-defaultValue="create" cfg-required="true">
								<ui:source type="Static">
								    [{text:'${lfn:message('km-forum:menu.kmForum.Create.my')}', value:'create'},
									 {text:'${lfn:message('km-forum:menu.kmForum.Attend.my')}', value:'attend'}]
								</ui:source>
							</list:item-select>
						</list:box-select>
					</list:tab-criterion>
					<list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${lfn:message('km-forum:kmForumTopic.docSubject')}">
					</list:cri-ref>
				    <%--板块--%>  
				    <list:cri-ref ref="criterion.sys.simpleCategory" key="category" multi="false" title="${lfn:message('km-forum:menu.kmForum.manage.nav')}" expand="false">
					  <list:varParams modelName="com.landray.kmss.km.forum.model.KmForumCategory"/>
					</list:cri-ref>
				</list:criteria>
			    <ui:fixed elem=".lui_list_operation"></ui:fixed>
			     <!-- 排序 -->
				<div class="lui_list_operation">
					<table width="100%">
						<tr>
							<td  class="lui_sort">
								${ lfn:message('list.orderType') }：
							</td>
							<td>
								<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" count="6" >
									<list:sortgroup>
										<list:sort property="fdLastPostTime" text="${lfn:message('km-forum:kmForumTopic.order.fdLastPostTime')}" group="sort.list" value="down"></list:sort>
										<list:sort property="docCreateTime" text="${lfn:message('km-forum:kmForumTopic.order.docCreateTime')}" group="sort.list"></list:sort>
										<list:sort property="fdReplyCount;fdHitCount" text="${lfn:message('km-forum:kmForumTopic.order.replyAndHit')}" group="sort.list"></list:sort>
									</list:sortgroup>
								</ui:toolbar>
								<div style="float:left;">	
									<list:paging layout="sys.ui.paging.top" > 		
									</list:paging>
								</div>
							</td>
							<td align="right">
								<ui:toolbar count="3" id="btnToolBar">
									<%-- 收藏 --%>
									<c:import url="/sys/bookmark/import/bookmark_bar_all.jsp" charEncoding="UTF-8">
										<c:param name="fdTitleProName" value="docSubject" />
										<c:param name="fdModelName"	value="com.landray.kmss.km.forum.model.KmForumTopic" />
									</c:import>
									<%-- 发帖 --%>
									<kmss:auth requestURL="/km/forum/km_forum/kmForumPost.do?method=add&owner=true" requestMethod="GET">	
										<ui:button id="add" text="${lfn:message('km-forum:kmForum.button.publish')}" onclick="addDoc()" order="2"></ui:button>	
									</kmss:auth>	
									<%-- 删除 --%>
									<ui:button id="delete" text="${lfn:message('button.deleteall')}" onclick="delDoc()" order="3"></ui:button>
								    <%--转移--%>		
								    <ui:button id="move" text="${lfn:message('km-forum:kmForumCategory.button.changeDirectory')}" onclick="showMove()" order="3"></ui:button>
								    <%-- 批量结贴 --%>
								    <ui:button id="batchConclude" text="${lfn:message('km-forum:kmForumCategory.button.batchConclude')}" onclick="showBatchConclude()" order="3"></ui:button>
								</ui:toolbar>
							</td>
						</tr>
					</table>
				</div>
		    	<list:listview id="listview">
					<ui:source type="AjaxJson">
								{url:'/km/forum/km_forum/kmForumTopic.do?method=listPersonOrZone&type=person'}
					</ui:source>
					<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
						rowHref="/km/forum/km_forum/kmForumPost.do?method=!{method}&fdForumId=!{kmForumCategory.fdId}&fdTopicId=!{fdId}"  name="columntable">
						<list:col-checkbox></list:col-checkbox>
						<list:col-serial></list:col-serial>
						<list:col-auto props=""></list:col-auto>
					</list:colTable>
				</list:listview> 
			 	<list:paging></list:paging>	
			</ui:content>
		</ui:tabpanel>
		
	 	 <script type="text/javascript">
	  		    Com_IncludeFile("dialog.js");
				seajs.use(['lui/topic','lui/dialog','lui/jquery'], function(topic,dialog,$) {

					// 监听新建更新等成功后刷新
					topic.subscribe('successReloadPage', function() {
						topic.publish('list.refresh');
					});
					
					//新建
					var forumId = "";
					window.addDoc = function() {
						if(forumId==null||forumId==''){
						    Com_OpenWindow('<c:url value="/km/forum/km_forum/kmForumPost.do" />?method=add');
						}else{
							Com_OpenWindow('<c:url value="/km/forum/km_forum/kmForumPost.do" />?method=add&fdForumId='+forumId);
						}
					};
					
					LUI.ready(function() {
						window.showButtons(forumId);
					})
					
					window.showButtons = function(forumId) {
					    var url = "${LUI_ContextPath}/km/forum/km_forum/kmForumTopicIndex.do?method=checkAuth";
						var data ={forumId:forumId};
						LUI.$.ajax({
							url: url,
							type: 'get',
							dataType: 'json',
							async: false,
							data: data,
							success: function(data, textStatus, xhr) {
								if(data.canDelete==true){
									 if(LUI('delete')){LUI('delete').setVisible(true);	}
								}else{
									 if(LUI('delete')){LUI('delete').setVisible(false);	}
								}

								if(data.canMove==true){
									 if(LUI('move')){LUI('move').setVisible(true);	}
								}else{
									 if(LUI('move')){LUI('move').setVisible(false);	}
								}
								if(data.canBatchConclude==true){
									if(LUI('batchConclude')){LUI('batchConclude').setVisible(true);  }
								}else{
									if(LUI('batchConclude')){LUI('batchConclude').setVisible(false); }
								}
							}
						});
					};
					
					topic.subscribe('criteria.changed',function(evt){
						if(evt['criterions'].length>0){
	                         for(var i=0;i<evt['criterions'].length;i++){
	                            //类型change
	                       	 	if(evt['criterions'][i].key=="category"){
	     							if(evt['criterions'][i].value.length==1){
	     								forumId=evt['criterions'][i].value[0];
	     								window.showButtons(forumId);
	     							}
	     					    }
	                         }					
					     }
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
								window.del_load = dialog.loading();
								$.post('${LUI_ContextPath}/km/forum/km_forum/kmForumTopic.do?method=deleteall&fdForumId='+forumId,
										$.param({"List_Selected":values},true),delCallback,'json');
							}
						});
					};
	                //板块转移
					window.showMove = function() {
						var values = [];
						$("input[name='List_Selected']:checked").each(function(){
								values.push($(this).val());
							});
						if(values.length==0){
							dialog.alert('<bean:message key="page.noSelect"/>');
							return;
						}
		                dialog.iframe("/km/forum/km_forum/kmForumTopic.do?method=showMove&fdId="+values+"&fdForumId="+forumId+"&type=moveAll",
												"${lfn:message('km-forum:kmForumCategory.button.changeDirectory')}",
											function(value){
												    if(value==""||value==null){
											    		   return;
											    	 }
													window.location.reload();}, {
													width : 500,
													height : 180
										});
				      };
					//批量结贴
					window.showBatchConclude = function() {
						var values = [];
						$("input[name='List_Selected']:checked").each(function(){
							values.push($(this).val())
						});
						if(values.length==0){
							dialog.alert('<bean:message key="page.noSelect"/>');
							return;
						}
						dialog.confirm('<bean:message key="kmForumTopic.batchConclude.tip" bundle="km-forum" />',function(value){
							if(value==true){
								window.con_load = dialog.loading();
								$.post('${LUI_ContextPath}/km/forum/km_forum/kmForumTopic.do?method=batchConclude&fdId='+values,
										$.param({"List_Selected":values},true),conCallback,'json');
								
							}
						});
					};
					
					window.conCallback = function(data){
						if (window.con_load != null)
							window.con_load.hide();
						if (data != null && data == true) {
							topic.publish("list.refresh");
							dialog.success('<bean:message key="return.optSuccess" />');
						} else {
							dialog.failure('<bean:message key="return.optFailure" />');
						}
					};
					
					window.delCallback = function(data) {
						if (window.del_load != null)
							window.del_load.hide();
						if (data != null && data.status == true) {
							topic.publish("list.refresh");
							dialog.success('<bean:message key="return.optSuccess" />');
						} else {
							dialog.failure('<bean:message key="return.optFailure" />');
						}
					};

				});
		 </script>	 
	</template:replace>
</template:include>
