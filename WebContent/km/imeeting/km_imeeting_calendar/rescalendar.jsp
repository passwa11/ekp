<%@page import="com.landray.kmss.km.imeeting.synchro.SynchroPlugin"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="java.util.Date"%>
<%@ page import=" com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.km.imeeting.util.KmImeetingConfigUtil"%>
<%
	request.setAttribute("dateFormatter", ResourceUtil.getString("date.format.date"));
	request.setAttribute("dateTimeFormatter",ResourceUtil.getString("date.format.datetime"));
	request.setAttribute("userId", UserUtil.getUser().getFdId());
	request.setAttribute("now", new Date().getTime());
	
	boolean eMeetingSynchroEnable = SynchroPlugin.isExchangeMeetingSynchroEnable();
	request.setAttribute("eMeetingSynchroEnable", eMeetingSynchroEnable);
%>
<template:include ref="default.list" spa="true" rwd="true">
	<%-- 标签页标题 --%>
	<template:replace name="title">
		<c:out value="${lfn:message('km-imeeting:module.km.imeeting')}"></c:out>
	</template:replace>
	<%-- 左侧导航栏 --%>
	<template:replace name="nav">
		<ui:combin ref="menu.nav.title">
			<ui:varParam name="operation">
				<ui:source type="Static">
				[ 
					{
						"text": "${lfn:message('km-imeeting:kmImeeting.tree.meeting.myAttend') }",
						"href": "/myAttend",
						"icon": "lui_iconfont_navleft_imeeting_my_join",
						"router" : true
					},
					{
						"text": "${lfn:message('km-imeeting:kmImeeting.committee') }",
						"href": "/committee",
						"icon": "lui_iconfont_navleft_imeeting_my_host",
						"router" : true
					},
					{
						"text": "${lfn:message('km-imeeting:kmImeeting.Create.my') }",
						"href": "/drafted",
						"icon": "lui_iconfont_navleft_com_my_drafted",
						"router" : true
					},
					{
						"text": "${lfn:message('km-imeeting:kmImeetingSummary.summary.myApproval') }",
						"href": "/approval",
						"icon": "lui_iconfont_navleft_com_my_beapproval",
						"router" : true
					},
					{
						"text": "${lfn:message('km-imeeting:kmImeetingSummary.summary.myApproved') }",
						"href": "/approvaled",
						"icon": "lui_iconfont_navleft_com_my_approvaled",
						"router" : true
					}
					
				]
				</ui:source>
			</ui:varParam>				
		</ui:combin>
		
		<div class="lui_list_nav_frame">
			<ui:accordionpanel>
					<ui:content title="${lfn:message('km-imeeting:kmImeeting.nav.contentTitle') }">
						<ui:combin ref="menu.nav.simple">
		  						<ui:varParam name="source">
		  					<ui:source type="Static">
		  					[{
		  						"text" : "${lfn:message('km-imeeting:workbench.person') }",
		  						"href" :  "/myWorkbench",
		  						"router" : true,
			  					"icon" : "lui_iconfont_navleft_com_homepage"
		  					},{
		  						"text" : "${lfn:message('km-imeeting:kmImeeting.tree.meeting') }",
		  						"href" :  "/kmImeeting_paln",
		  						"router" : true,
			  					"icon" : "lui_iconfont_navleft_supervise_my"
		  					},
		  					
		  					<%if("true".equals(KmImeetingConfigUtil.isTopicMng())){%>
		  					{
		  						"text" : "${lfn:message('km-imeeting:kmImeeting.tree.topic') }",
		  						"href" :  "/kmImeeting_topic",
			  					"router" : true,
			  					"icon" : "lui_iconfont_navleft_imeeting_summary"
		  					},
		  					<% }%>
		  					{
		  						"text" : "${lfn:message('km-imeeting:kmImeeting.tree.query') }",
		  						"href" :  "/kmImeeting_fixed",
			  					"router" : true,
			  					"icon" : "lui_iconfont_navleft_com_query"
		  					},
		  					<%if("true".equals(KmImeetingConfigUtil.isCycle())){%>
		  					{
		  						"text" : "${lfn:message('km-imeeting:kmImeeting.tree.cyclicity.meeting') }",
		  						"href" :  "/kmImeeting_cyclicity",
			  					"router" : true,
			  					"icon" : "lui_iconfont_navleft_imeeting_arrange"
		  					},
		  					<% }%>
		  					<%if("false".equals(KmImeetingConfigUtil.isTopicMng())){%>
		  					{
		  						"text" : "${lfn:message('km-imeeting:kmImeeting.tree.data') }",
		  						"href" :  "/kmImeeting_data",
			  					"router" : true,
			  					"icon" : "lui_iconfont_navleft_imeeting_material"
		  					},
		  					<% }%>
		  					{
		  						"text" : "${lfn:message('km-imeeting:kmImeeting.tree.myHandleSummary') }",
		  						"href" :  "/myHandleSummary",
			  					"router" : true,
			  					"icon" : "lui_iconfont_navleft_credit_my"
		  					}
		  					<% if (com.landray.kmss.sys.subordinate.util.SubordinateUtil.getInstance().getModelByModuleAndUser("km-imeeting:module.km.imeeting").size() > 0) { %>
		  					,{
		  						"text" : "${lfn:message('km-imeeting:subordinate.kmImeeting') }",
		  						"href" :  "/sys/subordinate",
			  					"router" : true,
			  					"icon" : "lui_iconfont_navleft_subordinate"
		  					}
		  					<% } %>
							<kmss:ifModuleExist path="/sys/datamng/">
								<kmss:authShow roles="ROLE_KMIMEETING_DATAMNG">
									,{
									"text" : "${ lfn:message('sys-datamng:module.sys.datamng') }",
									"href" :  "/datamng",
									"router" : true,
									"icon" : "lui_iconfont_navleft_com_statistics"
									}
								</kmss:authShow>
							</kmss:ifModuleExist>
		  					]
		  					</ui:source>
		  				</ui:varParam>
	  		</ui:combin>
				</ui:content>
				<ui:content title="${ lfn:message('km-imeeting:kmImeeting.tree.stat') }">
					<ui:combin ref="menu.nav.simple">
  						<ui:varParam name="source">
		  					<ui:source type="Static">
		  					[{
		  						"text" : "${ lfn:message('km-imeeting:kmImeeting.tree.stat.dept')}",
		  						"href" :  "/dept_stat",
		  						"router" : true,
			  					"icon" : "lui_iconfont_navleft_echarts_form"
		  					},{
		  						"text" : "${ lfn:message('km-imeeting:kmImeeting.tree.stat.person')}",
		  						"href" :  "/person_stat",
		  						"router" : true,
			  					"icon" : "lui_iconfont_navleft_imeeting_reserve"
		  					},{
		  						"text" : "${ lfn:message('km-imeeting:kmImeeting.tree.stat.res')}",
		  						"href" :  "/resource_stat",
		  						"router" : true,
			  					"icon" : " lui_iconfont_navleft_attend_sign"
		  					}
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
		  						"text" : "${lfn:message('km-imeeting:kmImeeting.abandom')}",
		  						"href" :  "/abandom",
		  						"router" : true,
			  					"icon" : "lui_iconfont_navleft_com_discard"
		  					}
		  					<c:if test="${eMeetingSynchroEnable }">
		  						,{
		  						"text" : "${lfn:message('km-imeeting:kmImeeting.exchange')}",
		  						"href" :  "javascript:LUI.pageOpen('${LUI_ContextPath }/third/ecalendar/ecalendar_bind_data/ecalendarBindData.do?method=edit','_blank');",
			  					"icon" : "lui_iconfont_navleft_com_background"
		  						}
		  					</c:if>
		  					<kmss:authShow roles="ROLE_KMIMEETING_BACKSTAGE_MANAGER">
		  					,{
		  						"text" : "${ lfn:message('list.manager') }",
		  						"href" : "/management",
		  						"router" : true,	
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
	
	<%-- 右侧内容区域 --%>
	<template:replace name="content">
	<style>
	.lui_tabpanel_list_navs_item_l{
	    max-width: 20%!important;
	}
	</style> 
		<ui:tabpanel id="kmImeetingPanel"   layout="sys.ui.tabpanel.list"  cfg-router="true">
		<ui:content id="myAttend" title="${lfn:message('km-imeeting:kmImeeting.tree.meeting.myAttend') }" cfg-route="{path:'/myAttend'}">
		 	  <ui:iframe   src="${LUI_ContextPath }/km/imeeting/km_imeeting_main/index_content_myAttend.jsp"></ui:iframe>
		  </ui:content>
		 <ui:content id="myHost" title="${lfn:message('km-imeeting:kmImeeting.committee') }" cfg-route="{path:'/committee'}">
		 	  <ui:iframe  src="${LUI_ContextPath }/km/imeeting/km_imeeting_main/index_content_myHost.jsp"></ui:iframe>
		  </ui:content>
		  <ui:content id="mycalendar" title="${lfn:message('km-imeeting:kmImeeting.tree.calender') }" cfg-route="{path:'/myCalendar'}">
		 	  <ui:iframe   src="${LUI_ContextPath }/km/imeeting/km_imeeting_calendar/index_content_mycalendar.jsp" ></ui:iframe>
		  </ui:content>
		  <ui:content  id="myMeeting" title="${lfn:message('km-imeeting:kmImeeting.tree.meeting') }" cfg-route="{path:'/myMeeting'}">
		 	 <ui:iframe src="${LUI_ContextPath }/km/imeeting/km_imeeting_main/index_content_meeting.jsp"></ui:iframe>
		  </ui:content>
		   <ui:content id="mySummary" title="${lfn:message('km-imeeting:kmImeeting.tree.myHandleSummary') }" cfg-route="{path:'/mySummary'}">
		 	 <ui:iframe  src="${LUI_ContextPath }/km/imeeting/km_imeeting_summary/index_content_summary.jsp"></ui:iframe>
		  </ui:content>
		  <ui:content id="myTopic" title="${lfn:message('km-imeeting:kmImeeting.tree.topic') }" cfg-route="{path:'/myTopic'}">
		 	 <ui:iframe src="${LUI_ContextPath }/km/imeeting/km_imeeting_topic/index_content_myMeetingTopic.jsp"></ui:iframe>
		  </ui:content>
		   <ui:content id="meetingPlace" title="${lfn:message('km-imeeting:kmImeetingRes.fdPlace') }" cfg-route="{path:'/meetingPlace'}">
		 	 <ui:iframe src="${LUI_ContextPath }/km/imeeting/km_imeeting_calendar/index_content_place.jsp" ></ui:iframe>
		  </ui:content>
		   <ui:content id="meetingEquipment" title="${lfn:message('km-imeeting:table.kmImeetingEquipment') }" cfg-route="{path:'/meetingEquipment'}">
		 	 <ui:iframe src="${LUI_ContextPath }/km/imeeting/km_imeeting_calendar/index_content_equipment.jsp" ></ui:iframe>
		  </ui:content>
		  <ui:content id="meetingCyclicity" title="sss${lfn:message('km-imeeting:kmImeeting.tree.cyclicity.meeting') }" cfg-route="{path:'/kmImeeting_cyclicity'}">
		 	 <ui:iframe src="${LUI_ContextPath }/km/imeeting/km_imeeting_main/index_content_meetingCyclicity.jsp"></ui:iframe>
		  </ui:content>
		  <ui:content id="meetingFixed" title="${lfn:message('km-imeeting:kmImeeting.tree.query') }" cfg-route="{path:'/kmImeeting_fixed'}">
		 	 <ui:iframe src="${LUI_ContextPath }/km/imeeting/km_imeeting_main/index_content_meetingFixed.jsp"></ui:iframe>
		  </ui:content>
		  <ui:content id="meetingTopic" title="${lfn:message('km-imeeting:kmImeeting.tree.topic') }" cfg-route="{path:'/kmImeeting_topic'}">
		 	 <ui:iframe src="${LUI_ContextPath }/km/imeeting/km_imeeting_topic/index_content_meetingTopic.jsp"></ui:iframe>
		  </ui:content>
		   <ui:content id="meetingData" title="${lfn:message('km-imeeting:kmImeeting.tree.data') }" cfg-route="{path:'/kmImeeting_data'}">
		 	 <ui:iframe src="${LUI_ContextPath }/km/imeeting/km_imeeting_uploadAtt/index.jsp"></ui:iframe>
		  </ui:content>
		  <ui:content id="myHandleSummary" title="${lfn:message('km-imeeting:kmImeeting.tree.myHandleSummary') }" cfg-route="{path:'/myHandleSummary'}">
		 	 <ui:iframe src="${LUI_ContextPath }/km/imeeting/km_imeeting_summary/index_content_meetingSummary.jsp"></ui:iframe>
		  </ui:content>
		  <ui:content id="myWorkbench" title="${lfn:message('km-imeeting:workbench.person') }" cfg-route="{path:'/myWorkbench'}">
		 	 <ui:iframe src="${LUI_ContextPath }/km/imeeting/km_imeeting_main/kmImeetingMain_workBench.jsp"></ui:iframe>
		  </ui:content>
		  <ui:content id="subordinate" title="${lfn:message('km-imeeting:subordinate.kmImeeting') }" cfg-route="{path:'/subordinate'}">
		 	 <ui:iframe src="${LUI_ContextPath }/sys/subordinate/moduleindex.jsp?moduleMessageKey=km-imeeting:module.km.imeeting"></ui:iframe>
		  </ui:content>
		  <ui:content id="management" title="${lfn:message('list.manager') }" cfg-route="{path:'/management'}">
		 	 <ui:iframe src="${LUI_ContextPath }/sys/profile/moduleindex.jsp?nav=/km/imeeting/tree.jsp"></ui:iframe>
		  </ui:content>
		  
		  <ui:content id="deptStat" title="${lfn:message('list.manager') }" cfg-route="{path:'/dept_stat'}">
		 	 <ui:iframe src="${LUI_ContextPath }/km/imeeting/import/kmImeeting_stat.jsp?stat_key=dept.stat&title=kmImeetingStat.dept.stat"></ui:iframe>
		  </ui:content>
		  <ui:content id="deptStatMon" title="${lfn:message('list.manager') }" cfg-route="{path:'/dept_statMon'}">
		 	 <ui:iframe src="${LUI_ContextPath }/km/imeeting/import/kmImeeting_stat.jsp?stat_key=dept.statMon&title=kmImeetingStat.dept.statMon"></ui:iframe>
		  </ui:content>
		  <ui:content id="personStat" title="${lfn:message('list.manager') }" cfg-route="{path:'/person_stat'}">
		 	 <ui:iframe src="${LUI_ContextPath }/km/imeeting/import/kmImeeting_stat.jsp?stat_key=person.stat&title=kmImeetingStat.person.stat"></ui:iframe>
		  </ui:content>
		  <ui:content id="personStatMon" title="${lfn:message('list.manager') }" cfg-route="{path:'/person_statMon'}">
		 	 <ui:iframe src="${LUI_ContextPath }/km/imeeting/import/kmImeeting_stat.jsp?stat_key=person.statMon&title=kmImeetingStat.person.statMon"></ui:iframe>
		  </ui:content>
		  <ui:content id="resourceStat" title="${lfn:message('list.manager') }" cfg-route="{path:'/resource_stat'}">
		 	 <ui:iframe src="${LUI_ContextPath }/km/imeeting/import/kmImeeting_stat.jsp?stat_key=resource.stat&title=kmImeetingStat.resource.stat"></ui:iframe>
		  </ui:content>
		  <ui:content id="resourceStatMon" title="${lfn:message('list.manager') }" cfg-route="{path:'/resource_statMon'}">
		 	 <ui:iframe src="${LUI_ContextPath }/km/imeeting/import/kmImeeting_stat.jsp?stat_key=resource.statMon&title=kmImeetingStat.resource.statMon"></ui:iframe>
		  </ui:content>
		  <ui:content id="datamngContent" title="${ lfn:message('sys-datamng:module.sys.datamng') }" cfg-route="{path:'/datamng'}">
			 <ui:iframe id="datamng"  src="${LUI_ContextPath }/sys/datamng/sys_datamng_main/sysDatamngData.do?method=modulePolicyList&modulePrefix=/km/imeeting/"></ui:iframe>
		  </ui:content>
	  </ui:tabpanel>
		<%@ include file="/km/imeeting/km_imeeting_calendar/kmImeetingCalendar_edit.jsp"%>
		<%@ include file="/km/imeeting/km_imeeting_calendar/kmImeetingCalendar_view.jsp"%>
	</template:replace>
	   	<template:replace name="script">
   		<%-- JSP中建议只出现·安装模块·的JS代码，其余JS代码采用引入方式 --%>
      	<script type="text/javascript">
      		seajs.use(['lui/framework/module','lui/framework/router/router-utils'],function(Module,routerUtils){
      			Module.install('kmImeeting',{
					//模块变量
					$var : {
						SYS_SEARCH_MODEL_NAME : "com.landray.kmss.km.imeeting.model.KmImeetingMain;com.landray.kmss.km.imeeting.model.KmImeetingSummary"
					},
 					//模块多语言
 					$lang : {
 						pageNoSelect : '${lfn:message("page.noSelect")}',
 						confirmFiled : '${lfn:message("km-archives:confirm.filed")}',
 						optSuccess : '${lfn:message("return.optSuccess")}',
 						optFailure : '${lfn:message("return.optFailure")}',
 						buttonDelete : '{lfn:message("button.delete")}',
 						buttonFiled : '${lfn:message("km-archives:button.filed")}',
 						changeRightBatch : '${lfn:message("sys-right:right.button.changeRightBatch")}'
 					},
 					//搜索标识符
 					$search : ''
  				});
      			
      			window.newPageWithParam = function(href,param){
					var router = routerUtils.getRouter();
					if (router) {
						router.push(href,param);
					}
					//移除导航选中状态
					LUI.$("[data-lui-type*=AccordionPanel] li").removeClass("lui_list_nav_selected");
					LUI.$("[data-path='"+href+"']").addClass('lui_list_nav_selected');
				};
      		});
      	</script>
      	<script type="text/javascript" src="${LUI_ContextPath}/km/imeeting/resource/js/index.js"></script>
	</template:replace>
</template:include>