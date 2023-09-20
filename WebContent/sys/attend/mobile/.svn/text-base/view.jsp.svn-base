<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.attend.util.DateTimeFormatUtil,com.landray.kmss.sys.attend.forms.SysAttendMainForm" %>
<%@ page import="java.util.Date,java.util.List,com.landray.kmss.util.DateUtil,com.landray.kmss.util.EnumerationTypeUtil" %>
<%@ page import="com.landray.kmss.sys.attend.model.SysAttendMain, com.landray.kmss.sys.attachment.model.SysAttMain" %>
<%@ page import="com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService"%>
<%@ page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@ page import="com.landray.kmss.util.ModelUtil"%>
<%@ page import="net.sf.json.JSONArray"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/attend/map/mobile/resource/css/location.css?s_cache=${MUI_Cache}"></link>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/attend/mobile/resource/css/attend.css?s_cache=${MUI_Cache}"></link>
		<script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=cnG6G1wW70lQ36H693uVOyOXiwvMaph3&s=1"></script>
	</template:replace>
	<template:replace name="title">
		签到详情
	</template:replace>
	<template:replace name="content">
		<%
			SysAttendMainForm mainForm = (SysAttendMainForm)request.getAttribute("sysAttendMainForm");
			DateTimeFormatUtil df = new DateTimeFormatUtil();
			Date signTime = DateUtil.convertStringToDate(mainForm.getDocCreateTime(), DateUtil.TYPE_DATETIME,request.getLocale());
			String _signTime = df.getDateTime(signTime,"yyyy'年'M'月'd'日'");	
			String week = df.getDateTime(signTime,"E");	
			
			String fdStatusText = EnumerationTypeUtil.getColumnEnumsLabel("sysAttendMain_fdStatus", mainForm.getFdStatus());
			pageContext.setAttribute("_signTime", DateUtil.convertDateToString(signTime, "HH:mm"));
			pageContext.setAttribute("_signDate", DateUtil.convertDateToString(signTime, "yyyy-MM-dd"));
			pageContext.setAttribute("_fdStatusText", fdStatusText);
			
			ISysAttMainCoreInnerService sysAttMainCoreInnerService=(ISysAttMainCoreInnerService)
					SpringBeanUtil.getBean("sysAttMainService");
			SysAttendMain sysAttendMain = (SysAttendMain)request.getAttribute("sysAttendMain");
			List<SysAttMain> list=sysAttMainCoreInnerService.findByModelKey(ModelUtil.getModelClassName(sysAttendMain),
					sysAttendMain.getFdId(),"Attachment");
			JSONArray array = new JSONArray();
			for(SysAttMain sysAttMain : list) {
				String attHref = request.getContextPath()
						+ "/sys/attachment/sys_att_main/sysAttMain.do?method=view&picthumb=small&fdId="
						+ sysAttMain.getFdId();
				array.add(attHref);
			}
			pageContext.setAttribute("fdAttachments", array);
		%>
		<ul class="muiSignInList muiSignInView muiAttendMainHead">
		      <li>
		        <a class="row" href="#">
		        	<div class="contentLeft">
		          	<span class="muipersonHead">
		          		<img class="muiImg" src='<person:headimageUrl contextPath="true" personId="${sysAttendMainForm.docCreatorId}" size="m" />'/>
		          	</span>
		          	<span class="muiPersonInfo">
		          		 <span>${sysAttendMainForm.docCreatorName}</span>
		          		 <span> <%=_signTime %> <%=week %></span>
		          	</span>
		          </div>
		        </a>
		      </li>

		</ul>
		<ul class="attendTabBar" data-dojo-type="mui/tabbar/TabBar" fixed="bottom" style="z-index:1050">
				  <li data-dojo-type="mui/back/BackButton"></li>
				    <li data-dojo-type="mui/tabbar/TabBarButtonGroup" data-dojo-props="icon1:'mui mui-more'">
				    	<div data-dojo-type="mui/back/HomeButton"></div>
				    </li>
				</ul>
		<script type="text/javascript" >
			require(['dojo/topic','dojo/ready','dojo/dom-construct','sys/attend/mobile/resource/js/list/LocationDialog',"sys/attend/map/mobile/resource/js/common/MapUtil"],
					function(topic,ready,domConstruct,LocationDialog,MapUtil){
				ready(function(){
					openLocationDialog();
				});
				
				window.openLocationDialog = function(){
					var self = this;
					if(window['muiLocationDialogIsUsing']){
						return;
					}
					window['muiLocationDialogIsUsing'] = true;
					if(!window['muiLocationDialog']){
						var dialog = window['muiLocationDialog'] = new LocationDialog({
							showStatus : 'view',
							isShowList : true
						});
						dialog.startup();
						domConstruct.place(dialog.domNode,document.body,'last');
					}
					var datas=[];
					if(!!'${sysAttendMainForm.fdLatLng}'){
						datas=[{
							fdLat : '${sysAttendMainForm.fdLat}',
							fdLng : '${sysAttendMainForm.fdLng}',
							coordType : MapUtil.getCoordType('${sysAttendMainForm.fdLatLng}')
						}];
					}
					var evt = {
						showStatus : 'view',
						value : '${sysAttendMainForm.fdLocation}',
						datas : datas
					};
					window['muiLocationDialog'].show(evt);
					topic.publish('/attend/location/info/datas',this,[{
						signedTime:'${_signTime}',
						fdLocation:'${sysAttendMainForm.fdLocation}',
						fdAddress:'${sysAttendMainForm.fdAddress}',
						fdDesc:'${sysAttendMainForm.fdDesc}',
						fdStatusText:'${_fdStatusText}',
						fdStatus:'${sysAttendMainForm.fdStatus}',
						categoryName:'${sysAttendMainForm.fdCategoryName}',
						categoryType:'${sysAttendCategory.fdType}',
						fdSignedDate:'${_signDate}',
						docCreatorName:'${sysAttendMainForm.docCreatorName}',
						dept:'${sysAttendMainForm.docCreatorDept}',
						docCreatorImg:'<person:headimageUrl contextPath="true" personId="${sysAttendMainForm.docCreatorId}" size="m" />',
						signTime:'',
						fdAttachments:${fdAttachments},
						fdAttrs:${fdAttachments},
						fdSignTime:'${_signTime}',
						fdType:'${sysAttendCategory.fdType}'
					}]);
				}
			});
				
		</script>
	</template:replace>
</template:include>
