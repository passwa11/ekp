<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<template:include ref="default.list" spa="true" spa-groups="[ ['fdIsTop','mydoc','docCategory' ,'docStatus'] ]">
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-news:news.moduleName') }"></c:out>
	</template:replace>
	<template:replace name="path">
		<ui:combin ref="menu.path.simplecategory">
			<ui:varParams 
				id="simplecategoryId"
				moduleTitle="${ lfn:message('sys-news:news.moduleName') }" 
				modelName="com.landray.kmss.sys.news.model.SysNewsTemplate" 
				extkey="mydoc;fdIsTop;docStatus" 
				criProps="{'cri.q':'docStatus:30'}"/>
		</ui:combin>
	</template:replace>
	<template:replace name="nav">
	<style>
			.lui_listview_rowtable_summary_content_box:hover{background-color:#f4f4f4}
		</style>
		<ui:combin ref="menu.nav.title"> 
			<ui:varParam name="operation">
				<ui:source type="Static">
					[
					{ 
					  "text":"${ lfn:message('sys-news:sysNewsMain.allNews') }", 
					  "router" : true,
					  "href":"/allNews", 
					  "icon":"lui_iconfont_navleft_news_all"
					},
					{ 
					  "text":"${ lfn:message('sys-news:sysNewsMain.fdIsTopNews') }", 
					  "router" : true,
					  "href":"/topNews", 
					  "icon":"lui_iconfont_navleft_news_top"
					},
					{ 
					  "text":"${ lfn:message('list.approval') }", 
					  "router" : true,
					  "href":"/approval", 
					  "icon":"lui_iconfont_navleft_com_my_beapproval"
					},
					{ 
					  "text":"${ lfn:message('list.approved') }", 
					  "router" : true,
					  "href":"/approved", 
					  "icon":"lui_iconfont_navleft_com_my_approvaled"
					},
					{ 
					  "text":"${ lfn:message('sys-news:sysNewsMain.myNews') }", 
					  "router" : true,
					  "href":"/myNews", 
					  "icon":"lui_iconfont_navleft_com_my_drafted"
					}
				    ]
				</ui:source>
			</ui:varParam>
		</ui:combin>
		<div class="lui_list_nav_frame">
			<ui:accordionpanel>
				 <!--常用分类 -->
			 	<ui:combin ref="menu.nav.favorite.category">
					<ui:varParams 
						href="/sys/news/?categoryId=!{value}"
						modelName="com.landray.kmss.sys.news.model.SysNewsTemplate" 
						criProps="{'cri.q':'docStatus:30'}"/>
				</ui:combin>
			    <!-- 分类索引 -->
			    <ui:content
					title="${lfn:message('sys-simplecategory:menu.sysSimpleCategory.index') }">
					<ui:combin ref="menu.nav.simplecategory.flat.all">
						<ui:varParams
							modelName="com.landray.kmss.sys.news.model.SysNewsTemplate"
							spa="true" criProps="{'cri.q':'docStatus:30'}"/>
					</ui:combin>
				</ui:content>
				<ui:content title="${ lfn:message('list.otherOpt') }"  expand="false" >
					<ui:combin ref="menu.nav.simple">
		  				<ui:varParam name="source">
		  					<ui:source type="Static">
			  					[
				  				<%-- 关闭回收站功能时，模块首页不显示“回收站” --%>
		  						<% if(com.landray.kmss.sys.recycle.util.SysRecycleUtil.isEnableSoftDelete("com.landray.kmss.sys.news.model.SysNewsMain")) { %>
				  					{
					  					"text" : "${ lfn:message('sys-recycle:module.sys.recycle') }",
										"href" :  "/recover",
										"icon" : "lui_iconfont_navleft_com_recycle",
										"router" : true
					  				},
				  				<%} %>
				  				<kmss:authShow roles="ROLE_SYSNEWS_BACKSTAGE_MANAGER">
				  					{
					  					"text" : "${ lfn:message('list.manager') }",
					  					"icon" : "lui_iconfont_navleft_com_background",
										"router" : true,
										"href" : "/management"
					  				}
			  					</kmss:authShow>
			  					]
		  					</ui:source>
		  				</ui:varParam>	
					</ui:combin>
				</ui:content>
			</ui:accordionpanel>
		</div>
	</template:replace>
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
			<%if(ISysAuthConstant.IS_AREA_ENABLED) { %> 
				<list:cri-auto modelName="com.landray.kmss.sys.news.model.SysNewsMain" property="authArea"/>
			<%} %>
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
				<list:paging layout="sys.ui.paging.top">
				</list:paging>
			</div>
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar count="3" id="btnToolBar">
							<%-- 视图切换 --%>
							<ui:togglegroup order="0">
									 <ui:toggle icon="lui_icon_s_zaiyao" title="${ lfn:message('list.rowTable') }" 
										selected="true"  group="tg_1" text="${ lfn:message('list.rowTable') }" value="rowtable"
										onclick="hideExport(this.value);LUI('listview').switchType(this.value);">
									</ui:toggle>
									<ui:toggle icon="lui_icon_s_liebiao" title="${ lfn:message('list.columnTable') }" 
										value="columntable"	group="tg_1" text="${ lfn:message('list.columnTable') }" 
										onclick="hideExport(this.value);LUI('listview').switchType(this.value);">
									</ui:toggle>
							 </ui:togglegroup>
						
							<%-- 收藏 --%>
							<c:import url="/sys/bookmark/import/bookmark_bar_all.jsp" charEncoding="UTF-8">
								<c:param name="fdTitleProName" value="docSubject" />
								<c:param name="fdModelName"	value="com.landray.kmss.sys.news.model.SysNewsMain" />
							</c:import>
							
							<kmss:authShow roles="ROLE_SYSNEWS_CREATE">
								<ui:button text="${lfn:message('button.add')}" onclick="window.moduleAPI.sysNews.addDoc()" order="2"></ui:button>	
							</kmss:authShow>
							<kmss:authShow roles="ROLE_SYSNEWS_TRANSPORT_EXPORT">
							<ui:button text="${lfn:message('button.export')}" id="export" onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.sys.news.model.SysNewsMain')" order="2" ></ui:button>								
							</kmss:authShow>
							<%-- ------删除----- --%>
							<ui:button id="del" text="${lfn:message('button.deleteall')}" onclick="window.moduleAPI.sysNews.delDoc()" order="3"
							    cfg-map="{\"docStatus\":\"criteria('docStatus')\",\"docCategory\":\"criteria('docCategory')\"}"
							    cfg-auth="/sys/news/sys_news_main/sysNewsMain.do?method=deleteall&status=!{docStatus}&categoryId=!{docCategory}&nodeType=${param.nodeType}"
							></ui:button>
							<%-- 修改权限 --%>
							<c:import url="/sys/right/import/doc_right_change_button.jsp" charEncoding="UTF-8">
								<c:param name="modelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
								<c:param name="spa" value="true"/>
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
								<c:param name="spa" value="true"/>
							</c:import>
							<!-- 在“发布文档”和“文档维护”目录树下需要显示以下按钮 -->
								<%-- ------置顶-----  --%>
									<ui:button id="setTop"  text="${lfn:message('sys-news:news.button.setTop')}" onclick="setTop(true)" ></ui:button>	
									<ui:button id="unSetTop" text="${lfn:message('sys-news:news.button.unSetTop')}" onclick="setTop(false)"></ui:button>	
								<%-- ------取消发布		 --%>			
									<ui:button id="unPublish" text="${lfn:message('sys-news:news.button.unPublish')}" onclick="op(false)"></ui:button>
							<%-- ------重新发布----- --%>
									<ui:button id="publish" text="${lfn:message('sys-news:news.button.publish')}" onclick="op(true)"></ui:button>
						</ui:toolbar>
				</div>
			</div>
		</div>
		
	    <ui:fixed elem=".lui_list_operation"></ui:fixed>
		<list:listview id="listview">
			<ui:source type="AjaxJson">
					{url:'/sys/news/sys_news_main/sysNewsMainIndex.do?method=listChildren'}
			</ui:source>

			<list:rowTable
				rowHref="!{url}" name="rowtable" >
				<list:row-template>
				  {$
					 <div class="clearfloat lui_listview_rowtable_summary_content_box">
						<dl>
							<dt>
								<input type="checkbox" data-lui-mark="table.content.checkbox" value="{%row.fdId%}" name="List_Selected"/>
								<span class="lui_listview_rowtable_summary_content_serial">{%row.index%}</span>
							</dt>	
						</dl>
			          	<dl>	
			                <dt>
			                	<a onclick="Com_OpenNewWindow(this)"  data-href="${LUI_ContextPath}{%row.url%}" class="textEllipsis com_subject" target="_blank" data-lui-mark-id="{%row.rowId%}">{%row.docSubject_row%}</a>
			                </dt>	
							<dd>
							    <span>{%str.textEllipsis(row['fdDescription_row'],150)%}</span>
							</dd>
							<dd class="lui_listview_rowtable_summary_content_box_foot_info">
					         	${lfn:message('sys-news:sysNewsMain.publisher') }：<em style="font-style: normal" class="com_author">{%row['fdWriterName_row']%}</em>
								<span>${lfn:message('sys-news:sysNewsMain.publishUnit') }：
									{%row['fdDepartment.fdName']%}
								</span>
								<%-- <%if(ISysAuthConstant.IS_AREA_ENABLED) { %> 
									<span>${ lfn:message('sys-authorization:sysAuthArea.authArea') }：{%row['authArea.fdName']%}</span>
								<%} %> --%>
								<span>{%row['docPublishTime_row']%}</span>
								<span>${lfn:message('sys-news:sysNewsPublishMain.fdImportance') }：{%row['fdImportance']%}</span>
								<span>{%row['docHits_row']%}</span>
								<span>{%row['sysTagMain_row']%}</span>
							</dd>
						</dl>
					</div>	
				    $}		      
				</list:row-template>
			</list:rowTable>			
			
			<list:colTable url="${LUI_ContextPath }/sys/profile/listShow/sys_listShow/sysListShow.do?method=getSort&modelName=com.landray.kmss.sys.news.model.SysNewsMain" isDefault="false" layout="sys.ui.listview.columntable"
				rowHref="!{url}"  name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto ></list:col-auto>
			</list:colTable>

			
		</list:listview> 
	 	<list:paging></list:paging>	 
	 	<script type="text/javascript">
    	    Com_IncludeFile("dialog.js");
    	    Com_IncludeFile("fileIcon.js", Com_Parameter.ResPath
					+ "style/common/fileIcon/", "js", true);
     </script>
	</template:replace>
		<template:replace name="script">
		<!-- JSP中建议只出现·安装模块·的JS代码，其余JS代码采用引入方式 -->
		<script type="text/javascript">
			seajs.use(['lui/framework/module','lui/dialog','lui/topic','lui/spa/const'],function(Module,dialog,topic,spaConst){
				// 点了回收站，再点分类导航切不回来，需要手动监听事件处理
				topic.subscribe(spaConst.SPA_CHANGE_VALUES, function(evt) {
					if(evt.value.j_path != "/recover") {
						LUI.pageHide('_rIframe');
					}
				});
				Module.install('sysNews',{
					//模块变量
					$var : {
						SYS_SEARCH_MODEL_NAME:"com.landray.kmss.sys.news.model.SysNewsMain"
						},
					//模块多语言
					$lang : {
						isTopNews : '${ lfn:message("sys-news:sysNewsMain.fdIsTopNews") }',
						approvalNews : '${ lfn:message("list.approval") }',
						approvedNews : '${ lfn:message("list.approved") }',
						myNews : '${ lfn:message("sys-news:sysNewsMain.myNews") }',
						pageNoSelect : '${lfn:message("page.noSelect")}',
 						confirmFiled : '${lfn:message("km-archives:confirm.filed")}',
 						optSuccess : '${lfn:message("return.optSuccess")}',
 						optFailure : '${lfn:message("return.optFailure")}',
 						buttonDelete : '{lfn:message("button.delete")}',
 						buttonFiled : '${lfn:message("km-archives:button.filed")}',
 						comfirmDelete :'${lfn:message("page.comfirmDelete")}',
 						topTime :"${lfn:message('sys-news:sysNewsMain.fdTopTime')}",
 						confirmCancel:"${lfn:message('sys-news:news.publish.confirmCancel')}",
 						setTopConfirmCancel:"${lfn:message('sys-news:news.setTop.confirmCancel')}",
					},
					//搜索标识符
					$search : ''
				});
				
				window.setTop=function(isTop){
					var values = [];
					$("input[name='List_Selected']:checked").each(function(){
						values.push($(this).val());
					});
				    if(values.length==0){
					dialog.alert('${lfn:message("page.noSelect")}');
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
												url: Com_Parameter.ContextPath+'sys/news/sys_news_main/sysNewsMain.do?method=setTop',
												type: 'POST',
												data:$.param({"List_Selected":values,"fdIsTop":isTop,"fdDays":days,"categoryId":categoryId},true),
												dataType: 'json',
												error: function(data){
													if(window.del_load!=null){
														window.del_load.hide(); 
													}
													dialog.failure('${lfn:message("return.optFailure")}');
												},
												success: topCallback
											});									
							}
							},{width:400,height : 200});
					}else{		
						    days=0;		
						    dialog.confirm("${lfn:message('sys-news:news.setTop.confirmCancel')}",function(value){
							if(value==true){
								window.del_load = dialog.loading();
							//	$.post('<c:url value="/sys/news/sys_news_main/sysNewsMain.do?method=setTop"/>',
							//			$.param({"List_Selected":values,"fdIsTop":isTop,"fdDays":days},true),topCallback,'json');
								$.ajax({
									url: Com_Parameter.ContextPath+'sys/news/sys_news_main/sysNewsMain.do?method=setTop',
									type: 'POST',
									data:$.param({"List_Selected":values,"fdIsTop":isTop,"fdDays":days,"categoryId":categoryId},true),
									dataType: 'json',
									error: function(data){
										if(window.del_load!=null){
											window.del_load.hide(); 
										}
										dialog.failure('${lfn:message("return.optFailure")}');
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
					   dialog.alert('${lfn:message("page.noSelect")}');
					   return;
				    }
                 	//取消发布确认框
					if(optype==false){
						dialog.confirm("${lfn:message('sys-news:news.publish.confirmCancel')}",function(value){
							if(value==true){
								window.del_load = dialog.loading();
								$.post(Com_Parameter.ContextPath+'sys/news/sys_news_main/sysNewsMain.do?method=setPublish',
										$.param({"List_Selected":values,"op":optype,"categoryId":categoryId},true),publishCallback,'json')
										.error(function(data){
											if(window.del_load!=null){
												window.del_load.hide(); 
											}
											dialog.failure('${lfn:message("return.optFailure")}');
										 });		
							}
						});
				    }else{
				    	
						dialog.iframe("/sys/news/sys_news_main/sysNewsMain_expired.jsp","${lfn:message('sys-news:sysNewsMain.docOverdueTime')}",function (value){
	                      	
							if(value=='cancel'){
								return;
							}
							window.del_load = dialog.loading();
							$.post(Com_Parameter.ContextPath+'sys/news/sys_news_main/sysNewsMain.do?method=setPublish',
								$.param({"List_Selected":values,"op":optype,"categoryId":categoryId,"expiredDate":value,},true),publishCallback,'json')
								.error(function(data){
									if(window.del_load!=null){
										window.del_load.hide(); 
									}
									dialog.failure('${lfn:message("return.optFailure")}');
							 });										
						},{width:600,height : 400,close:false});
				    }	  
				};


            	//置顶、取消置顶回调
				function topCallback (data){
					if(window.del_load!=null)
						window.del_load.hide();
					if(data!=null && data.status==true){
						topic.publish("list.refresh");
						dialog.success('${lfn:message("return.optSuccess")}');
					}else{
						dialog.failure('${lfn:message("return.optFailure")}');
					}
				};

           		//发布、取消发布回调
				function publishCallback(data){
					if(window.del_load!=null)
						window.del_load.hide();
					if(data!=null && data.status==true){
						topic.publish("list.refresh");
						dialog.success('${lfn:message("return.optSuccess")}');
					}else{
						dialog.failure('${lfn:message("return.optFailure")}');
					}
				};
			});
		</script>
		<!-- 引入JS -->
		<script type="text/javascript" src="${LUI_ContextPath}/sys/news/resource/js/index.js"></script>	 
	</template:replace>
</template:include>
