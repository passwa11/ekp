<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.SpringBeanUtil,net.sf.json.JSONObject,java.util.Map" %>
<%@ page import="com.landray.kmss.sys.attend.service.ISysAttendCategoryService,com.landray.kmss.sys.attend.service.ISysAttendStatService,com.landray.kmss.sys.attend.service.ISysAttendReportService" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	ISysAttendCategoryService sysAttendCategoryService = (ISysAttendCategoryService) SpringBeanUtil
		.getBean("sysAttendCategoryService");
	Map map = sysAttendCategoryService.getSignExtend();
	String meetingModelName = "com.landray.kmss.km.imeeting.model.KmImeetingMain";
	if(map.containsKey(meetingModelName)){
		JSONObject obj = (JSONObject)map.get(meetingModelName);
		request.setAttribute("meeting_modelKey", obj.get("modelKey"));
		request.setAttribute("meeting_moduleName", java.net.URLEncoder.encode((String)obj.get("moduleName"),"UTF-8"));
	}
	
	ISysAttendStatService statService = (ISysAttendStatService)SpringBeanUtil
			.getBean("sysAttendStatService");
	request.setAttribute("isStatReader", statService.isStatReader());
	ISysAttendReportService sysAttendReportService = (ISysAttendReportService)SpringBeanUtil
			.getBean("sysAttendReportService");
	request.setAttribute("isReportReader", sysAttendReportService.isReportReader());
%>

<c:set var="key" value="${param.key}"/>
<c:set var="criteria" value="${param.criteria}"/>
	<ui:content  title="${ lfn:message('sys-attend:sysAttend.nav.title.attend') }" expand="true">
		<ui:combin ref="menu.nav.simple">
		  	<ui:varParam name="source">
		  		<ui:source type="Static">
		  			[{
		  				"text" : "${ lfn:message('sys-attend:sysAttend.nav.all.record') }",
  						"href" :  "/listAll",
						"router" : true,		  					
	  					"icon" : "lui_iconfont_navleft_com_all"
		  			},
		  			{
		  				"text" : "${ lfn:message('sys-attend:sysAttend.nav.original.record') }",
  						"href" :  "/listOrgin",
						"router" : true,		  					
	  					"icon" : "lui_iconfont_navleft_com_all"
		  			}
		  			<c:if test="${isStatReader }">
		  				,{
			  				"text" : "${ lfn:message('sys-attend:table.sysAttendStatDetail') }",
	  						"href" :  "/statDetail",
							"router" : true,		  					
		  					"icon" : "lui_iconfont_navleft_attend_daysum"
			  			},{
			  				"text" : "${ lfn:message('sys-attend:sysAttend.nav.month.stat') }",
	  						"href" :  "/statReport",
							"router" : true,		  					
		  					"icon" : "lui_iconfont_navleft_attend_monsun"
			  			},{
			  				"text" : "${ lfn:message('sys-attend:sysAttendReport.report') }",
	  						"href" :  "/listReport",
							"router" : true,		  					
		  					"icon" : "lui_iconfont_navleft_com_statistics"
			  			},{
							"text" : "加班流转",
							"href" :  "/statOverReport",
							"router" : true,
							"icon" : "lui_iconfont_navleft_com_statistics"
						},{
							"text" : "异常考勤预警名单",
							"href" :  "/hrStaffAttendReport",
							"router" : true,
							"icon" : "lui_iconfont_navleft_com_statistics"
						},{
							"text" : "加班明细表",
							"href" :  "/overDetailReport",
							"router" : true,
							"icon" : "lui_iconfont_navleft_com_statistics"
						},{
							"text" : "同步考勤数据到OA",
							"href" :  "/syncAttendDatabase",
							"router" : true,
							"icon" : "lui_iconfont_navleft_com_statistics"
						},{
							"text" : "流程审批时效统计表",
							"href" :  "/hrStaffApprovalReport",
							"router" : true,
							"icon" : "lui_iconfont_navleft_com_statistics"
						}
		  			</c:if>
		  			<c:if test="${!isStatReader && isReportReader}">
		  				,{
			  				"text" : "${ lfn:message('sys-attend:sysAttendReport.report') }",
	  						"href" :  "/listReport",
							"router" : true,		  					
		  					"icon" : "lui_iconfont_navleft_com_statistics"
			  			}
			  			
		  			</c:if>
		  			<kmss:authShow roles="ROLE_SYSATTEND_DATA_CHECK">
		  			,{
			  				"text" : "${lfn:message('sys-attend:sysAttendMain.data.check') }",
	  						"href" :  "/listcheck",
							"router" : true,		  					
		  					"icon" : "lui_iconfont_navleft_com_statistics"
			  			}
			  			</kmss:authShow>
			  			<kmss:authShow roles="ROLE_SYSATTEND_DATA_CHECK">
		  			,{
			  				"text" : "${lfn:message('sys-attend:sysAttendMain.data.summary') }",
	  						"href" :  "/listsummary",
							"router" : true,		  					
		  					"icon" : "lui_iconfont_navleft_com_statistics"
			  			}
			  			</kmss:authShow>
			  				<kmss:authShow roles="ROLE_SYSATTEND_DATA_CHECK">
		  			,{
			  				"text" : "${lfn:message('sys-attend:sysAttendMain.data.month.dept.report') }",
	  						"href" :  "/listMonthDeptReport",
							"router" : true,		  					
		  					"icon" : "lui_iconfont_navleft_com_statistics"
			  			}
			  			</kmss:authShow>
			  				<kmss:authShow roles="ROLE_SYSATTEND_DATA_CHECK">
		  			,{
			  				"text" : "${lfn:message('sys-attend:sysAttendMain.data.month.dept.over40.report') }",
	  						"href" :  "/listMonthDeptOver40Report",
							"router" : true,		  					
		  					"icon" : "lui_iconfont_navleft_com_statistics"
			  			}
			  			</kmss:authShow>
		  			]
		  		</ui:source>
		  	</ui:varParam>
		</ui:combin>
	</ui:content>
	
	
	<ui:content title="${ lfn:message('sys-attend:sysAttend.nav.title.sign') }" expand="false">
		<ui:combin ref="menu.nav.simple">
		  	<ui:varParam name="source">
		  		<ui:source type="Static">
		  			[
		  				{
		  					"text" : "${ lfn:message('sys-attend:sysAttend.nav.mySignRecord') }",
	  						"href" :  "/listMySign",
							"router" : true,		  					
		  					"icon" : "lui_iconfont_navleft_com_all"
		  				},{
		  					"text" : "${ lfn:message('sys-attend:sysAttend.nav.allSignRecord') }",
	  						"href" :  "/listAllSign",
							"router" : true,		  					
		  					"icon" : "lui_iconfont_navleft_com_all"
		  				}
		  			]
		  		</ui:source>
		  	</ui:varParam>
		</ui:combin>
	</ui:content>
	<ui:content title="${ lfn:message('sys-attend:sysAttend.nav.title.meetingSign') }" expand="false">
		<ui:combin ref="menu.nav.simple">
		  	<ui:varParam name="source">
		  		<ui:source type="Static">
		  			[
		  				{
		  					"text" : "${ lfn:message('sys-attend:sysAttend.nav.myMeetingSign') }",
	  						"href" :  "/listMyMeetingSign",
							"router" : true,		  					
		  					"icon" : "lui_iconfont_navleft_attend_mySign"
		  				},{
		  					"text" : "${ lfn:message('sys-attend:sysAttend.nav.ImeetingSign') }",
	  						"href" :  "/listAllMeetingSign",
							"router" : true,		  					
		  					"icon" : "lui_iconfont_navleft_attend_sign"
		  				}
		  			]
		  		</ui:source>
		  	</ui:varParam>
		</ui:combin>
	</ui:content>
	<ui:content title="${ lfn:message('sys-attend:sysAttend.nav.setting') }" expand="false">
		<ui:combin ref="menu.nav.simple">
		  	<ui:varParam name="source">
		  		<ui:source type="Static">
		  			[
		  				{
		  					"text" : "${ lfn:message('sys-attend:sysAttendCategory.attend') }",
	  						"href" :  "/listAttendCate",
							"router" : true,		  					
		  					"icon" : "lui_iconfont_navleft_attend_group"
		  				},{
		  					"text" : "${ lfn:message('sys-attend:sysAttendCategory.custom') }",
	  						"href" :  "/listSignCate",
							"router" : true,		  					
		  					"icon" : "lui_iconfont_navleft_attend_signGroup"
		  				}
		  				<kmss:authShow roles="ROLE_SYSATTEND_BACKSTAGE_MANAGER">
			  				,{
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
<script type="text/javascript">
		seajs.use(['lui/jquery', 'lui/util/str', 'lui/dialog'], function($, strutil, dialog){
			window.setUrl= function (key,mykey,type){
				//打开新页面
				if(key!="${key}"){
					if(key=="sysAttendMain"){
						if(type==""){
							openUrl('${LUI_ContextPath}/sys/attend/sys_attend_main/index.jsp','');
						}
						else{
							openUrl('${LUI_ContextPath}/sys/attend/sys_attend_main/index.jsp','cri.q='+mykey+':'+type);
						}
					}
					if(key=="sysAttendMainMyExc"){
						if(type==""){
							openUrl('${LUI_ContextPath}/sys/attend/sys_attend_main/index_exc.jsp','');
						}
						else{
							openUrl('${LUI_ContextPath}/sys/attend/sys_attend_main/index_exc.jsp','cri.q='+mykey+':'+type);
						}
					}
					if(key=="sysAttendMainExc"){
						if(type==""){
							openUrl('${LUI_ContextPath}/sys/attend/sys_attend_main_exc/index.jsp','');
						}
						else{
							openUrl('${LUI_ContextPath}/sys/attend/sys_attend_main_exc/index.jsp','cri.q='+mykey+':'+type);
						}
					}
					if(key=='sysAttendCategory') {
						if(type==""){
							openUrl('${LUI_ContextPath}/sys/attend/sys_attend_category/index.jsp','');
						}
						else{
							openUrl('${LUI_ContextPath}/sys/attend/sys_attend_category/index.jsp','cri.q='+mykey+':'+type);
						}
					}
					if(key=='sysAttendSynDing') {
						if(type==""){
							openUrl('${LUI_ContextPath}/sys/attend/sys_attend_syn_ding/index.jsp','');
						}
						else{
							openUrl('${LUI_ContextPath}/sys/attend/sys_attend_syn_ding/index.jsp','cri.q='+mykey+':'+type);
						}
					}
				}
				//只更新list列表
			 	else{
			 		openQuery();
			 		 LUI('${criteria}').setValue(mykey, type);
				}
			};
			window.openUrl = function(srcUrl,hash){
				if(hash!=""){
					srcUrl+="#"+hash;
			    }
				window.open(srcUrl,"_self");
			};
			
			window.switchAttendPage = function(url){
				url = Com_SetUrlParameter(url,'j_iframe','true');
				url = Com_SetUrlParameter(url,'j_aside','false');
				LUI.pageOpen(url,'_rIframe');
			}
			
		});
</script>