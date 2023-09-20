<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
<template:replace name="content">  
	   <%-- 筛选器 --%>	
	    <list:criteria id="criteria1">
	         <list:cri-ref key="docSubject" ref="criterion.sys.docSubject">
			</list:cri-ref>
		   	<!--重要度、发布时间 -->
			<list:cri-auto modelName="com.landray.kmss.sys.news.model.SysNewsMain" property="fdImportance;docPublishTime" expand="false"/>
			<!--文档状态 -->
			<list:cri-criterion title="${lfn:message('sys-news:sysNewsMain.docStatus')}" key="docStatus" expand="false">
				<list:box-select>
					<list:item-select>
						<ui:source type="Static">
							[{text:'${ lfn:message('sys-news:status.draft') }', value:'10'},
							{text:'${ lfn:message('sys-news:status.examine') }',value:'20'},
							{text:'${ lfn:message('sys-news:status.refuse') }', value: '11'},
							{text:'${ lfn:message('sys-news:status.discard') }',value:'00'},
							{text:'${ lfn:message('sys-news:status.publish') }',value:'30'},
							{text:'${ lfn:message('sys-news:status.cancle') }',value:'40'}]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
		    <!--作者、部门 -->
			<list:cri-auto modelName="com.landray.kmss.sys.news.model.SysNewsMain" property="fdAuthor;fdDepartment" />
			<!-- 我上传的查询器 -->
			<list:cri-criterion title="${lfn:message('sys-news:sysNews.sysNewsMain.my')}" key="mydoc" expand="false" multi="false">
				<list:box-select>
					<list:item-select>
						<ui:source type="Static">
							[{text:'${ lfn:message('sys-news:sysNewsMain.myNews') }', value:'create'},{text:'${ lfn:message('list.approval') }',value:'approval'}, {text:'${ lfn:message('list.approved') }', value: 'approved'}]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<!-- 其他查询 -->
			<list:cri-criterion title="${lfn:message('sys-news:sysNewsMain.otherSearch') }" key="fdIsTop" expand="false"  >
				<list:box-select>
					<list:item-select>
						<ui:source type="Static">
							[{text:'${ lfn:message('sys-news:sysNewsMain.fdIsTopNews') }',value:'1'}]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>	
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
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" count="6" >
						<list:sortgroup>
							<list:sort property="fdIsTop;fdTopTime;docAlterTime" text="${lfn:message('sys-news:sysNewsMain.new') }" group="sort.list" value="down"></list:sort>
							<list:sort property="fdIsTop;fdTopTime;docPublishTime;docAlterTime;docCreateTime" text="${lfn:message('sys-news:sysNewsMain.docPublishTime') }" group="sort.list" ></list:sort>
							<list:sort property="docReadCount" text="${lfn:message('sys-news:sysNewsMain.docHits') }" group="sort.list"></list:sort>
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
					<ui:toolbar count="3" id="btnToolBar">
						
							<%-- 收藏 --%>
							<c:import url="/sys/bookmark/import/bookmark_bar_all.jsp" charEncoding="UTF-8">
								<c:param name="fdTitleProName" value="docSubject" />
								<c:param name="fdModelName"	value="com.landray.kmss.sys.news.model.SysNewsMain" />
							</c:import>
							
							<kmss:authShow roles="ROLE_SYSNEWS_CREATE">
								<ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="1"></ui:button>	
							</kmss:authShow>
															
							<%-- ------删除----- --%>
							<kmss:auth requestURL="/sys/news/sys_news_main/sysNewsMain.do?method=deleteall&status=${param.status}&categoryId=${param.categoryId}&nodeType=${param.nodeType}" requestMethod="GET">						
							    <ui:button text="${lfn:message('button.deleteall')}" onclick="delDoc()" order="7"></ui:button>
							</kmss:auth>
							
							<%-- 修改权限 --%>
							<c:import url="/sys/right/import/doc_right_change_button.jsp" charEncoding="UTF-8">
								<c:param name="modelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
							</c:import>							
							<%-- 分类转移 --%>
							<%-- 如果用户有“分类权限扩充”角色，则允许转移到所有的分类 --%>
							<% 
								if(com.landray.kmss.util.UserUtil.checkRole("ROLE_SYSNEWS_OPTALL")) {
									request.setAttribute("authType", "00");
								} 
							%>
							<c:import url="/sys/simplecategory/import/doc_cate_change_button.jsp" charEncoding="UTF-8">
								<c:param name="modelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
								<c:param name="docFkName" value="fdTemplate" />
								<c:param name="cateModelName" value="com.landray.kmss.sys.news.model.SysNewsTemplate" />
								<c:param name="authType" value="${authType}" />
							</c:import>
							
							
							<!-- 在“发布文档”和“文档维护”目录树下需要显示以下按钮 -->
		
								<%-- ------置顶-----  --%>
								<kmss:auth requestURL="/sys/news/sys_news_main/sysNewsMain.do?method=setTop&categoryId=${param.categoryId}&nodeType=${param.nodeType}" requestMethod="GET">
									<ui:button id="setTop"  text="${lfn:message('sys-news:news.button.setTop')}" order="4" onclick="setTop(true)" ></ui:button>	
									<ui:button id="unSetTop" text="${lfn:message('sys-news:news.button.unSetTop')}" order="4" onclick="setTop(false)"></ui:button>	
								</kmss:auth>
								
					
								<%-- ------取消发布		 --%>			
				    			<kmss:auth requestURL="/sys/news/sys_news_main/sysNewsMain.do?method=setPublish&categoryId=${param.categoryId}&nodeType=${param.nodeType}" requestMethod="GET">
									<ui:button id="unPublish" text="${lfn:message('sys-news:news.button.unPublish')}" order="4" onclick="op(false)"></ui:button>
								</kmss:auth>
												
							<%-- ------重新发布----- --%>
								<kmss:auth requestURL="/sys/news/sys_news_main/sysNewsMain.do?method=setPublish&categoryId=${param.categoryId}&nodeType=${param.nodeType}" requestMethod="GET">
									<ui:button id="publish" text="${lfn:message('sys-news:news.button.publish')}" order="4" onclick="op(true)" ></ui:button>
								</kmss:auth>
						</ui:toolbar>
				</div>
			</div>
		</div>
		
	    <ui:fixed elem=".lui_list_operation"></ui:fixed>
		<list:listview id="listview">
			<ui:source type="AjaxJson">
					{url:'/sys/news/sys_news_main/sysNewsMainIndex.do?method=manageList&categoryId=${JsParam.categoryId}&status=${JsParam.status}&showDocStatus=${JsParam.showDocStatus}&type=${JsParam.type}'}
			</ui:source>
			<list:colTable isDefault="false" layout="sys.ui.listview.columntable" 
				rowHref="!{url}"  name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="docSubject;fdImportance_doc;docCreator.fdName;docPublishTime;docAlterTime_time;docHits;fdTopDays;authArea.fdName"></list:col-auto>
			</list:colTable>
		</list:listview> 
	 	<list:paging></list:paging>	 
	 	<script type="text/javascript">
		    var SYS_SEARCH_MODEL_NAME ="com.landray.kmss.sys.news.model.SysNewsMain";
    	    Com_IncludeFile("dialog.js");
			seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/toolbar'], function($, dialog , topic,toolbar) {

				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});
				
				//新建
				window.addDoc = function() {
						dialog.simpleCategoryForNewFile(
								'com.landray.kmss.sys.news.model.SysNewsTemplate',
								'/sys/news/sys_news_main/sysNewsMain.do?method=add&fdTemplateId=!{id}',false,null,null,'${JsParam.categoryId}');
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
							url : '<c:url value="/sys/news/sys_news_main/sysNewsMain.do?method=deleteall"/>&categoryId=${JsParam.categoryId}',
							data : $.param({"List_Selected":values},true), // 要删除的数据
							modelName : "com.landray.kmss.sys.news.model.SysNewsMain" // 主要是判断此文档是否有部署软删除
						};
					// 通用删除方法
					Com_Delete(config, delCallback);
				};
			
				LUI.ready(function(){
					/* if(LUI('setTop')){LUI('setTop').setVisible(true);	}
					if(LUI('unSetTop')){LUI('unSetTop').setVisible(true);}
					if(LUI('publish')){	LUI('publish').setVisible(false);	}
					if(LUI('unPublish')){LUI('unPublish').setVisible(true);} */
				//	openQuery();
					//LUI('criteria1').setValue('docStatus', '30');
// 					setTimeout("initMenuNav()", 300);
				});
				
				 window.initMenuNav = function(){
					 var fdIsTop = getValueByHash("fdIsTop");
			 		 var key = getValueByHash("mydoc");
			 		 if(fdIsTop == "1") {
			 			key="fdIsTop";
			 		 }else if(!key) {
			 			key="all";
			 		 }
			 		resetMenuNavStyle($("#_"+key));
				 };
				 topic.subscribe('criteria.changed',function(evt){
						if(LUI('setTop')){LUI('setTop').setVisible(false);}
						if(LUI('unSetTop')){LUI('unSetTop').setVisible(false);}
						if(LUI('publish')){LUI('publish').setVisible(false);}
						if(LUI('unPublish')){LUI('unPublish').setVisible(false);}
						if(evt['criterions'].length>0){
                          for(var i=0;i<evt['criterions'].length;i++){
                              //发布状态显示【置顶】、【取消发布】按钮
                        	  if(evt['criterions'][i].key=="docStatus"){
      							if(evt['criterions'][i].value.length==1){
      								if(evt['criterions'][i].value[0]=="30"){
      									if(LUI('setTop')){LUI('setTop').setVisible(true);}
      									if(LUI('unSetTop')){LUI('unSetTop').setVisible(true);}
      									if(LUI('unPublish')){LUI('unPublish').setVisible(true);}
      								}
      								if(evt['criterions'][i].value[0]=="40"){
      									if(LUI('publish')){LUI('publish').setVisible(true);}
      								}							
      							}
      					       }
                              //置顶查询显示【取消置顶】按钮
                              if(evt['criterions'][i].key=="fdIsTop"){
        							if(evt['criterions'][i].value.length==1){
        								if(evt['criterions'][i].value[0]=="1"){
        									if(LUI('setTop')){LUI('setTop').setVisible(true);}
        									if(LUI('unSetTop')){LUI('unSetTop').setVisible(true);}
        								}
        							}
        					   }
        						
        						
                          }					
					  }
					});

				 window.setTop=function(isTop){
						var values = [];
						$("input[name='List_Selected']:checked").each(function(){
							values.push($(this).val());
						});
					    if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					   }
					  var days=null;
						if(isTop){
						dialog.iframe("/sys/news/sys_news_main/sysNewsMain_topday.jsp","${lfn:message('sys-news:sysNewsMain.fdTopTime')}",function (value){
                                 days=value;
                             	if(days==null){
    								return;
    							}else{
    											window.del_load = dialog.loading();
    									//		$.post('<c:url value="/sys/news/sys_news_main/sysNewsMain.do?method=setTop"/>',
    									//				$.param({"List_Selected":values,"fdIsTop":isTop,"fdDays":days},true),topCallback,'json');	
    											$.ajax({
    												url: '<c:url value="/sys/news/sys_news_main/sysNewsMain.do?method=setTop"/>',
    												type: 'POST',
    												data:$.param({"List_Selected":values,"fdIsTop":isTop,"fdDays":days,categoryId:'${JsParam.categoryId}'},true),
    												dataType: 'json',
    												error: function(data){
    													if(window.del_load!=null){
    														window.del_load.hide(); 
    													}
    													dialog.failure('<bean:message key="return.optFailure" />');
    												},
    												success: topCallback
    											});									
    							}
								},{width:400,height : 200});
						}else{		
							    days=0;		
							    dialog.confirm('<bean:message bundle="sys-news" key="news.setTop.confirmCancel"/>',function(value){
								if(value==true){
									window.del_load = dialog.loading();
								//	$.post('<c:url value="/sys/news/sys_news_main/sysNewsMain.do?method=setTop"/>',
								//			$.param({"List_Selected":values,"fdIsTop":isTop,"fdDays":days},true),topCallback,'json');
									$.ajax({
										url: '<c:url value="/sys/news/sys_news_main/sysNewsMain.do?method=setTop"/>',
										type: 'POST',
										data:$.param({"List_Selected":values,"fdIsTop":isTop,"fdDays":days,categoryId:'${JsParam.categoryId}'},true),
										dataType: 'json',
										error: function(data){
											if(window.del_load!=null){
												window.del_load.hide(); 
											}
											dialog.failure('<bean:message key="return.optFailure" />');
										},
										success: topCallback
									});		
								}
							});					
						}
					};
					
				 window.op=function(optype) {
						var values = [];
						$("input[name='List_Selected']:checked").each(function(){
							values.push($(this).val());
						});
					    if(values.length==0){
						   dialog.alert('<bean:message key="page.noSelect"/>');
						   return;
					    }
                        //取消发布确认框
						if(optype==false){
							dialog.confirm('<bean:message bundle="sys-news" key="news.publish.confirmCancel"/>',function(value){
								if(value==true){
									window.del_load = dialog.loading();
									$.post('<c:url value="/sys/news/sys_news_main/sysNewsMain.do?method=setPublish"/>',
											$.param({"List_Selected":values,"op":optype,categoryId:'${JsParam.categoryId}'},true),publishCallback,'json')
											.error(function(data){
												if(window.del_load!=null){
													window.del_load.hide(); 
												}
												dialog.failure('<bean:message key="return.optFailure" />');
											 });		
								}
							});
					    }else{
							window.del_load = dialog.loading();
							$.post('<c:url value="/sys/news/sys_news_main/sysNewsMain.do?method=setPublish"/>',
									$.param({"List_Selected":values,"op":optype,categoryId:'${JsParam.categoryId}'},true),publishCallback,'json')
									.error(function(data){
										if(window.del_load!=null){
											window.del_load.hide(); 
										}
										dialog.failure('<bean:message key="return.optFailure" />');
									 });		
					    }	  
					};


                   //置顶、取消置顶回调
					window.topCallback = function(data){
						if(window.del_load!=null)
							window.del_load.hide();
						if(data!=null && data.status==true){
							topic.publish("list.refresh");
							dialog.success('<bean:message key="return.optSuccess" />');
						}else{
							dialog.failure('<bean:message key="return.optFailure" />');
						}
					};

                  //发布、取消发布回调
					window.publishCallback = function(data){
						if(window.del_load!=null)
							window.del_load.hide();
						if(data!=null && data.status==true){
							topic.publish("list.refresh");
							dialog.success('<bean:message key="return.optSuccess" />');
						}else{
							dialog.failure('<bean:message key="return.optFailure" />');
						}
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
			});
     </script>	 
	</template:replace>
</template:include>