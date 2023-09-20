<%@page import="java.util.Set"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.appconfig.service.ISysAppConfigService"%>
<%@page import="java.util.Map"%>
<%@page import="com.landray.kmss.framework.hibernate.extend.SqlPartitionConfig"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<%
String modelName = "com.landray.kmss.km.review.model.KmReviewShowConfig";
ISysAppConfigService sysAppConfigService = (ISysAppConfigService) SpringBeanUtil.getBean("sysAppConfigService");
Map map = sysAppConfigService.findByKey(modelName);
request.setAttribute("showConfig", map);
%>
<template:include ref="default.list" spa="true" rwd="true">
	<template:replace name="title">${ lfn:message('km-review:module.km.review') }</template:replace>
	<template:replace name="nav">
		<%-- 头部导航 --%>
		<ui:combin ref="menu.nav.title">
			<ui:varParam name="title" value="${ lfn:message('km-review:module.km.review') }"></ui:varParam>
			<%-- 数据区 --%>
			<c:if test="${showConfig.countSwitch eq '1' || empty showConfig.countSwitch }">
			<ui:varParam name="info" >
				<ui:source type="Static">
					[
						{
							"text": "${ lfn:message('km-review:kmReview.nav.title.all') }",
							"href": "/listAll",
							"router" : true,
							"count_url": "/km/review/km_review_main/kmReviewMain.do?method=getCount&type=all"
						},
						{
							"text": "${ lfn:message('km-review:kmReview.nav.title.create') }",
							"href": "/listCreate",
							"router" : true,
							"count_url": "/km/review/km_review_main/kmReviewMain.do?method=getCount&type=draft"
						},
						{
							"text": "${ lfn:message('km-review:kmReview.nav.title.approval') }",
							"href": "/listApproved",
							"router" : true,
							"count_url": "/km/review/km_review_main/kmReviewMain.do?method=getCount&type=approved"
						}
					]
				</ui:source>
			</ui:varParam>
			</c:if>
			<%-- 操作区 --%>
			<ui:varParam name="operation">
				<ui:source type="Static">
				[
					{
						"text": "${ lfn:message('km-review:kmReview.nav.create.my') }",
						"href": "/listCreate",
						"router" : true,
						"icon": "lui_iconfont_navleft_com_my_drafted"
					},
					{
						"text": "${ lfn:message('km-review:kmReview.nav.approval.my') }",
						"href": "/listApproval",
						"router" : true,
						"icon": "lui_iconfont_navleft_com_my_beapproval"
					},
					{
						"text": "${ lfn:message('km-review:kmReview.nav.approved.my') }",
						"href": "/listApproved",
						"router" : true,
						"icon": "lui_iconfont_navleft_com_my_approvaled"
					},
					{
						"text": "${lfn:message('km-review:kmReview.nav.overview') }",
						"href": "/overview",
						"router" : true,
						"icon": "lui_iconfont_navleft_com_classify"
					}
				]
				</ui:source>
			</ui:varParam>	
		</ui:combin>
		<div id="menu_nav" class="lui_list_nav_frame">
			<ui:accordionpanel>
				<ui:content title="${ lfn:message('km-review:kmReview.nav') }">
					<ui:combin ref="menu.nav.simple">
		  				<ui:varParam name="source">
		  					<ui:source type="Static">
		  					[{
		  						"text" : "${ lfn:message('km-review:kmReview.nav.all') }",
		  						"href" :  "/listAll",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_com_all"
		  					},
		  					<kmss:authShow roles="ROLE_KMREVIEW_CREATE">
		  					{
		  						"text" : "${ lfn:message('km-review:kmReview.nav.create') }",
		  						"href" :  "/create",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_review_create"
		  					},
		  					</kmss:authShow>
		  					{
		  						"text" : "${ lfn:message('km-review:kmReview.nav.examine') }",
		  						"href" :  "/listExamine",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_review_approval"
		  					},{
		  						"text" : "${ lfn:message('km-review:kmReview.nav.follow') }",
		  						"href" :  "/listFollow",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_review_track"
		  					},{
		  						"text" : "${ lfn:message('km-review:kmReview.nav.feedback') }",
		  						"href" :  "/listFeedback",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_review_feedback"
		  					},{
		  						"text" : "${ lfn:message('km-review:kmReview.nav.search') }",
		  						"href" :  "/search",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_com_query"
		  					}
		  					<kmss:ifModuleExist path="/dbcenter/echarts/">
								<kmss:authShow roles="ROLE_DBCENTERECHARTS_DEFAULT">
									<xform:Show bean="dbEchartsNavTreeService" mainModelName="com.landray.kmss.km.review.model.KmReviewTemplate" fdKey="kmReviewMainDoc">
										,{
					  						"text" : "${ lfn:message('dbcenter-echarts:module.dbcenter.dataChart') }",
					  						"href" :  "/dbNavTree",
											"router" : true,		  					
						  					"icon" : "lui_iconfont_navleft_com_statistics"
					  					}
				  					</xform:Show>
								</kmss:authShow>
							</kmss:ifModuleExist>
							<% if (com.landray.kmss.sys.subordinate.util.SubordinateUtil.getInstance().getModelByModuleAndUser("km-review:module.km.review").size() > 0) { %>
		  					,{
		  						"text" : "${lfn:message('sys-subordinate:module.sys.subordinate') }",
		  						"href" :  "/sys/subordinate",
			  					"router" : true,
			  					"icon" : "lui_iconfont_navleft_subordinate"
		  					}
		  					<% } %>
							]
		  					</ui:source>
		  				</ui:varParam>
	  				</ui:combin>	
				</ui:content>
			
				<ui:content title="${ lfn:message('list.otherOpt') }" expand="false" >
					<ui:combin ref="menu.nav.simple">
		  				<ui:varParam name="source">
		  					<ui:source type="Static">
		  					[{
		  						"text" : "${ lfn:message('km-review:kmReview.nav.filed') }",
		  						"href" :  "/listFiling",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_com_file"
		  					},{
		  						"text" : "${ lfn:message('km-review:kmReview.nav.discard') }",
		  						"href" :  "/listDiscard",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_com_discard"
		  					}
		  					<%-- 关闭回收站功能时，模块首页不显示“回收站” --%>
		  					<% if(com.landray.kmss.sys.recycle.util.SysRecycleUtil.isEnableSoftDelete("com.landray.kmss.km.review.model.KmReviewMain")) { %>
		  					,{
		  						"text" : "${ lfn:message('km-review:kmReview.nav.recycle') }",
		  						"href" :  "/recover",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_com_recycle"
		  					}
		  					<% } %>
		  					<kmss:authShow roles="ROLE_KMREVIEW_BACKSTAGE_MANAGER">
		  					,{
		  						"text" : "${ lfn:message('list.manager') }",
		  						"href" :  "javascript:LUI.pageOpen('${LUI_ContextPath }/sys/profile/index.jsp#app/ekp/km/review','_blank');",
			  					"icon" : "lui_iconfont_navleft_com_background"
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
		<ui:tabpanel id="kmReviewPanel" layout="sys.ui.tabpanel.list"  cfg-router="true">
			<ui:content id="kmReviewContent" title="${ lfn:message('km-review:kmReviewMain.portlet.myFlow.all') }" cfg-route="{path:'/listCreate'}">
			<list:criteria id="criteria1">
			    <list:tab-criterion title="" key="docStatus">
			   		 <list:box-select>
			   		 	<list:item-select type="lui/criteria/select_panel!TabCriterionSelectDatas" cfg-enable="false" cfg-required="true"  cfg-if="param['doctype'] == 'feedback'">
							<ui:source type="Static">
								[{text:'${ lfn:message('km-review:status.unfeedback') }', value:'41'},
								 {text:'${ lfn:message('km-review:status.feedback') }',value:'31'}]
							</ui:source>
						</list:item-select>
			    	</list:box-select>
			    </list:tab-criterion>
			     <list:tab-criterion title="" key="docStatus">
			   		 <list:box-select>
			   		 	<list:item-select type="lui/criteria/select_panel!TabCriterionSelectDatas" cfg-enable="false" cfg-required="true"  cfg-if="(param['doctype'] == 'examine' )&& param['docStatus'] !='00' && param['docStatus'] !='32' ">
							<ui:source type="Static">
								[{text:'${ lfn:message('km-review:status.append') }', value:'20'},
								 {text:'${ lfn:message('km-review:status.refuse') }',value:'11'}]
							</ui:source>
						</list:item-select>
			    	</list:box-select>
			    </list:tab-criterion>
			    <list:tab-criterion title="" key="docStatus"> 
			   		 <list:box-select>
			   		 	<list:item-select type="lui/criteria/select_panel!TabCriterionSelectDatas" cfg-enable="false" cfg-if="(param['mydoc'] == 'all' || param['mydoc'] == 'create' || param['mydoc'] == 'approved' || param['doctype'] == 'follow') && param['docStatus'] !='00' && param['docStatus'] !='32' ">
							<ui:source type="Static">
								[{text:'${ lfn:message('km-review:status.draft') }', value:'10'},
								{text:'${ lfn:message('km-review:status.append') }', value:'20'},
								{text:'${ lfn:message('km-review:status.refuse') }', value:'11'},
								{text:'${ lfn:message('km-review:status.publish') }', value:'30'},
								{text:'${ lfn:message('km-review:status.feedback') }',value:'31'},
								{text:'${ lfn:message('km-review:status.discard') }',value:'00'}]
							</ui:source>
						</list:item-select>
			    	</list:box-select>
			    </list:tab-criterion>
				<list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${ lfn:message('km-review:kmReviewMain.docSubject') }">
				</list:cri-ref>
				<%
					if(SqlPartitionConfig.getInstance().isEnabled("com.landray.kmss.km.review.model.KmReviewMain")){
				%>
				<list:cri-ref title="${ lfn:message('km-review:kmReviewMain.docCreateTime') }" key="partition" ref="criterion.sys.partition" modelName="com.landray.kmss.km.review.model.KmReviewMain" />
				<%
					}
				%>
				<list:cri-ref ref="criterion.sys.category" key="fdTemplate" multi="false" expand="false" title="${ lfn:message('km-review:kmReviewMain.criteria.fdTemplate') }">
				  <list:varParams modelName="com.landray.kmss.km.review.model.KmReviewTemplate"/>
				</list:cri-ref>
				<list:cri-auto modelName="com.landray.kmss.km.review.model.KmReviewMain" property="fdNumber"/>
				<list:cri-ref ref="criterion.sys.person" key="docCreator" multi="false" title="${lfn:message('km-review:kmReviewMain.docCreatorName') }" />
			   <%--当前处理人--%>
				<list:cri-ref ref="criterion.sys.postperson.availableAll"  cfg-if="param['docStatus']!='00' && param['docStatus']!='32'"
							  key="fdCurrentHandler" multi="false"
							  title="${lfn:message('km-review:kmReviewMain.currentHandler') }" />
			   <%--已处理人--%>
				<list:cri-ref ref="criterion.sys.person" 
							  key="fdAlreadyHandler" multi="false"
							  title="${lfn:message('km-review:kmReviewMain.fdAlreadyHandler') }" />
				<list:cri-auto modelName="com.landray.kmss.km.review.model.KmReviewMain" 
					property="fdDepartment" />
				<%
					if(SqlPartitionConfig.getInstance().isEnabled("com.landray.kmss.km.review.model.KmReviewMain") == false){
				%>
				<list:cri-auto modelName="com.landray.kmss.km.review.model.KmReviewMain" cfg-defaultValue="${showConfig.loadDataVolume}" property="docCreateTime"/>
				<list:cri-auto modelName="com.landray.kmss.km.review.model.KmReviewMain" property="docPublishTime"/>
				<%
					}
				%>
				<%if(ISysAuthConstant.IS_AREA_ENABLED) { %> 
					<list:cri-auto modelName="com.landray.kmss.km.review.model.KmReviewMain" property="authArea"/>
				<%} %>
				<list:cri-auto modelName="com.landray.kmss.km.review.model.KmReviewMain" property="docProperties"/>	
				<list:cri-criterion title="${ lfn:message('km-review:kmReviewMain.fdIsFiling')}" key="fdIsFile"> 
					<list:box-select>
						<list:item-select cfg-if="param['j_path']!='/listFiling'">
							<ui:source type="Static">
								[{text:'${ lfn:message('km-review:kmReviewMain.fdIsFiling.have')}', value:'1'},
								{text:'${ lfn:message('km-review:kmReviewMain.fdIsFiling.nothave')}',value:'0'}]
							</ui:source>
						</list:item-select>
					</list:box-select>
				</list:cri-criterion>		
			</list:criteria>
			
			<div class="lui_list_operation">
				<div style='color: #979797;float: left;padding-top:1px;'>
					${ lfn:message('list.orderType') }：
				</div>
				<div style="float:left">
					<div style="display: inline-block;vertical-align: middle;">
						<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
							<list:sortgroup>
								<list:sort property="docCreateTime" text="${lfn:message('km-review:kmReviewMain.docCreateTime') }" group="sort.list" value="down"></list:sort>
								<list:sort property="docPublishTime" text="${lfn:message('km-review:kmReviewMain.docPublishTime') }" group="sort.list"></list:sort>
							</list:sortgroup>
						</ui:toolbar>
					</div>
				</div>
				<div style="float:left;">	
					<%@ include file="/sys/profile/showconfig/showConfig_paging_top.jsp" %>
				</div>
				<div style="float:right">
					<div style="display: inline-block;vertical-align: middle;">
						<ui:toolbar id="Btntoolbar">
							
						  	<kmss:authShow roles="ROLE_KMREVIEW_CREATE">
							 	<ui:button text="${lfn:message('button.add')}" id="add" onclick="window.moduleAPI.kmReview.addDoc();" order="2" cfg-if="param['docStatus'] != '00' && param['docStatus'] != '32'" ></ui:button>
							</kmss:authShow>
							<ui:button text="${lfn:message('sys-archives:button.filed')}" id="fileBtn" onclick="window.moduleAPI.kmReview.file_doc()" order="2"
										cfg-if="param['docStatus'] != '00' && param['docStatus'] != '32' && (param['docStatus'] == '30' || criteria('docStatus')[0]=='30')">
							</ui:button>
							<kmss:authShow roles="ROLE_KMREVIEW_TRANSPORT_EXPORT">
							<ui:button 
									text="${lfn:message('button.export')}" id="export" 
									onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.km.review.model.KmReviewMain')"
									order="2" >
							</ui:button>
							</kmss:authShow>
							<kmss:auth
								requestURL="/km/review/km_review_main/kmReviewMain.do?method=deleteall&categoryId=${param.categoryId}&nodeType=${param.nodeType}"
								requestMethod="GET">
							<ui:button id="del" text="${lfn:message('button.deleteall')}" order="3" onclick="window.moduleAPI.kmReview.delDoc()"></ui:button>
							</kmss:auth>
							<%-- 批量打印 --%>
							<kmss:authShow roles="ROLE_KMREVIEW_PRINTBATCH">
							 	<ui:button text="${lfn:message('km-review:kmReviewMain.printBatch')}" id="batchPrint" onclick="window.moduleAPI.kmReview.batchPrint();" order="4" ></ui:button>
							</kmss:authShow>
							<%-- 收藏 --%>
							<c:import url="/sys/bookmark/import/bookmark_bar_all.jsp" charEncoding="UTF-8">
								<c:param name="fdTitleProName" value="docSubject" />
								<c:param name="fdModelName"	value="com.landray.kmss.km.review.model.KmReviewMain" />
							</c:import>
							<%-- 分类转移 --%>
							<kmss:auth
									requestURL="/km/review/km_review_main/kmReviewMain.do?method=changeTemplate&categoryId=${param.categoryId}&nodeType=${param.nodeType}"
									requestMethod="GET">
								<ui:button id="chgCate" text="${lfn:message('km-review:button.translate')}" order="5" onclick="window.moduleAPI.kmReview.chgSelect();"></ui:button>
							</kmss:auth>
							<%-- 修改权限 --%>
							<c:import url="/sys/right/import/cchange_doc_right_button.jsp" charEncoding="UTF-8">
								<c:param name="modelName" value="com.landray.kmss.km.review.model.KmReviewMain" />
								<c:param name="authReaderNoteFlag" value="2" />
							</c:import>
						</ui:toolbar>
					</div>
				</div>
			</div>
			<ui:fixed elem=".lui_list_operation" id="hack_fix"></ui:fixed>
			
			<list:listview id="listview" cfg-criteriaInit="${empty param.categoryId?'false':'true'}">
				<ui:source type="AjaxJson">
					{url:'/km/review/km_review_index/kmReviewIndex.do?method=list&pagingSetting=${showConfig.pagingSetting }'}
				</ui:source>
				<list:colTable url="${LUI_ContextPath }/sys/profile/listShow/sys_listShow/sysListShow.do?method=getSort&modelName=com.landray.kmss.km.review.model.KmReviewMain" isDefault="true" layout="sys.ui.listview.columntable" rowHref="/km/review/km_review_main/kmReviewMain.do?method=view&fdId=!{fdId}">
					<list:col-checkbox></list:col-checkbox>
					<list:col-serial></list:col-serial>
					<list:col-auto></list:col-auto> 
				</list:colTable>
			</list:listview> 
			<br>
			<%@ include file="/sys/profile/showconfig/showConfig_paging_buttom.jsp"%>
		 	</ui:content>
		 </ui:tabpanel>
	 
	</template:replace>
	<%  request.setAttribute("isAdmin",UserUtil.getKMSSUser().isAdmin()); %>
   	<template:replace name="script">
   		<script type="text/javascript" src="${LUI_ContextPath}/sys/ui/js/criteria/criterion_calendar_init_date.js"></script>
   		<%-- JSP中建议只出现·安装模块·的JS代码，其余JS代码采用引入方式 --%>
      	<script type="text/javascript">
      		var SYS_SEARCH_MODEL_NAME ="com.landray.kmss.km.review.model.KmReviewMain";
      
      		seajs.use(['lui/framework/module','lui/criteria/criterion_calendar_init_date'],function(Module, init_date){
      			var loadDateVolume = '${showConfig.loadDataVolume}';
          		var startAndEndTime = getStartTimeAndEndTime(loadDateVolume,init_date);
          		
      			Module.install('kmReview',{
					//模块变量
					$var : {
						isAdmin : '${isAdmin}',
						startAndEndTime : startAndEndTime
					},
 					//模块多语言
 					$lang : {
 						pageNoSelect : '${lfn:message("page.noSelect")}',
 						confirmFiled : '${lfn:message("sys-archives:confirm.filed")}',
 						optSuccess : '${lfn:message("return.optSuccess")}',
 						optFailure : '${lfn:message("return.optFailure")}',
 						buttonDelete : '${lfn:message("button.deleteall")}',
 						buttonFiled : '${lfn:message("sys-archives:button.filed")}',
 						changeRightBatch : '${lfn:message("sys-right:right.button.changeRightBatch")}'
 					},
 					//搜索标识符
 					$search : ''
  				});
      		});
      		
      		function getStartTimeAndEndTime(value,func){
      			var time = 'docCreateTime:';
      			switch(value){
	      			case '1':
	      				time += func.getLastWeek();
	      				break;
	      			case '2':
	      				time += func.getLastMonth();
	      				break;
	      			case '3':
	      				time += func.getLast3Month();
	      				break;
	      			case '4':
	      				time += func.getLastHalfYear();
	      				break;
	      			case '5':
	      				time += func.getLastYear();
	      				break;
	      			case '6':
	      			default:
	      				return "";
	      		}
      			time = time + ';docCreateTime:'+func.getToday();
      			return time;
      		}
      	</script>
      	<script type="text/javascript" src="${LUI_ContextPath}/km/review/resource/js/index.js"></script>
	</template:replace>
	
</template:include>