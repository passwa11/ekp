<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" spa="true">
	<template:replace name="body">
	<script type="text/javascript">
			seajs.use(['theme!list','theme!module']);	
		</script>  
	<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/hr/staff/resource/css/hr_staff.css?s_cache=${LUI_Cache}"/>
		<!-- 员工信息一览图 Starts -->
		<div class="lui_hr_staff_overview_card_frame_out">
		<table width="100%" class="lui_hr_staff_overview_card_frame">
			<tr>
				<td colspan="2"> <!-- 最近生日 -->
					<div class="lui_hr_staff_panel_overview_frame">
					<div class="lui_hr_staff_panel_title"><span class="panel_title">${ lfn:message('hr-staff:hrStaff.overview.lastBirthday') }</span>
						<kmss:authShow roles="ROLE_HRSTAFF_WARNING">
						<div class="panel_operation">
							<a href="javascript:openUrl('/birthday')"  class="panel_btn_more" title="${ lfn:message('operation.more') }"></a>
						</div>
						</kmss:authShow>
					</div>
					<ui:dataview>
						<ui:source type="AjaxJson">
							{url:'/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=overview&type=1'}
						</ui:source>
						<ui:render type="Template">
							{$
							<div class="lui_hr_staff_panel_content">
								<div class="lui_hr_staff_info_list">
									<ul>
							$}
							if(data.length < 1) {
								{$<li style="text-align: center;" class="status_nodata"><span>${ lfn:message('hr-staff:message.noRecord') }</span></li>$}
							}
							for(var i=0; i<data.length; i++) {
								{$
								<li>
									<a href="javascript:openUrl('${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId={% data[i].fdId %}');" style="cursor:pointer;">
										<span class="staff_info_photo">
											<span class="staff_portrait"><img src="${LUI_ContextPath}/sys/person/image.jsp?personId={% data[i].fdId %}&size=s&s_time=${LUI_Cache}"/></span>
										<span class="staff_name">{% data[i].fdName %}</span>
										</span>
										<span class="staff_info_date">
											$}
											if(data[i].fdIsToday == "true") {
												{$<i class="lui_icon_s icon_brith"></i>$}
											}
											{$
											<span class="info_date">{% data[i].fdDate %}</span>
										</span>
									</a>
								</li>
								$}
							}
							{$
									</ul>
								</div>
							</div>
							$}
						</ui:render>
					</ui:dataview>
					</div>
				</td>
				<td colspan="2"> <!-- 合同到期 -->
					<div class="lui_hr_staff_panel_overview_frame">
					<div class="lui_hr_staff_panel_title"><span class="panel_title">${ lfn:message('hr-staff:hrStaff.overview.contractExpiration') }</span>
						<kmss:authShow roles="ROLE_HRSTAFF_WARNING">
						<div class="panel_operation">
							<a href="javascript:openUrl('/contractExpiration')" class="panel_btn_more" title="${ lfn:message('operation.more') }"></a>
						</div>
						</kmss:authShow>
					</div>
					<ui:dataview>
						<ui:source type="AjaxJson">
							{url:'/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=overview&type=2'}
						</ui:source>
						<ui:render type="Template">
							{$
							<div class="lui_hr_staff_panel_content">
								<div class="lui_hr_staff_info_list">
									<ul>
							$}
							if(data.length < 1) {
								{$<li style="text-align: center;" class="status_nodata"><span>${ lfn:message('hr-staff:message.noRecord') }</span></li>$}
							}
							for(var i=0; i<data.length; i++) {
								{$
								<li>
									<a href="javascript:openUrl('${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId={% data[i].fdId %}&anchor=experienceContract');"  style="cursor:pointer;">
										<span class="staff_info_photo">
											<span class="staff_portrait"><img src="${LUI_ContextPath}/sys/person/image.jsp?personId={% data[i].fdId %}&size=s&s_time=${LUI_Cache}"/></span>
										<span class="staff_name">{% data[i].fdName %}</span>
										</span>
										<span class="staff_info_date">
											$}
											if(data[i].fdIsToday == "true") {
												{$<i class="lui_icon_s icon_quest"></i>$}
											}
											{$
											<span class="info_date">{% data[i].fdDate %}</span>
										</span>
									</a>
								</li>
								$}
							}
							{$
									</ul>
								</div>
							</div>
							$}
						</ui:render>
					</ui:dataview>
					</div>
				</td>
				<td colspan="2"> <!-- 试用到期 -->
					<div class="lui_hr_staff_panel_overview_frame">
					<div class="lui_hr_staff_panel_title"><span class="panel_title">${ lfn:message('hr-staff:hrStaff.overview.trialExpiration') }</span>
						<kmss:authShow roles="ROLE_HRSTAFF_WARNING">
						<div class="panel_operation">
							<a href="javascript:openUrl('/trial')" class="panel_btn_more" title="${ lfn:message('operation.more') }"></a>
						</div>
						</kmss:authShow>
					</div>
					<ui:dataview>
						<ui:source type="AjaxJson">
							{url:'/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=overview&type=3'}
						</ui:source>
						<ui:render type="Template">
							{$
							<div class="lui_hr_staff_panel_content">
								<div class="lui_hr_staff_info_list">
									<ul>
							$}
							if(data.length < 1) {
								{$<li style="text-align: center;" class="status_nodata"><span>${ lfn:message('hr-staff:message.noRecord') }</span></li>$}
							}
							for(var i=0; i<data.length; i++) {
								{$
								<li>
									<a href="javascript:openUrl('${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId={% data[i].fdId %}');"  style="cursor:pointer;">
										<span class="staff_info_photo">
											<span class="staff_portrait"><img src="${LUI_ContextPath}/sys/person/image.jsp?personId={% data[i].fdId %}&size=s&s_time=${LUI_Cache}"/></span>
										<span class="staff_name">{% data[i].fdName %}</span>
										</span>
										<span class="staff_info_date">
											$}
											if(data[i].fdIsToday == "true") {
												{$<i class="lui_icon_s icon_quest"></i>$}
											}
											{$
											<span class="info_date">{% data[i].fdDate %}</span>
										</span>
									</a>
								</li>
								$}
							}
							{$
									</ul>
								</div>
							</div>
							$}
						</ui:render>
					</ui:dataview>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="3" align="center"> <!-- 在职员工性别比例 -->
					<div class="lui_hr_staff_panel_overview_frame">
					<div class="lui_hr_staff_panel_title" style="text-align: left;">
						<span class="panel_title">${ lfn:message('hr-staff:hrStaff.overview.report.staffSex') }</span>
					</div>
					<div class="lui_hr_staff_panel_content">
						<ui:chart width="100%" height="300px">
		  					<ui:source type="AjaxJson">
								{"url":"/hr/staff/hr_staff_person_report/hrStaffPersonReport.do?method=overviewChart&type=staffSex"}
		  					</ui:source>
						</ui:chart>
					</div>
					</div>
				</td>
				<td colspan="3" align="center"> <!-- 在职员工学历分布 -->
					<div class="lui_hr_staff_panel_overview_frame">
					<div class="lui_hr_staff_panel_title" style="text-align: left;">
						<span class="panel_title">${ lfn:message('hr-staff:hrStaff.overview.report.education') }</span>
					</div>
					<div class="lui_hr_staff_panel_content">
						<ui:chart width="100%" height="300px">
		  					<ui:source type="AjaxJson">
								{"url":"/hr/staff/hr_staff_person_report/hrStaffPersonReport.do?method=overviewChart&type=education"}
		  					</ui:source>
						</ui:chart>
					</div>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="3" align="center">
					<div class="lui_hr_staff_panel_overview_frame">
					<div class="lui_hr_staff_panel_title" style="text-align: left;">
						<span class="panel_title">${ lfn:message('hr-staff:hrStaff.overview.report.workTime') }</span>
					</div>
					<ui:chart width="100%" height="300px">
	  					<ui:source type="AjaxJson">
							{"url":"/hr/staff/hr_staff_person_report/hrStaffPersonReport.do?method=overviewChart&type=workTime"}
	  					</ui:source>
					</ui:chart>
					</div>
				</td>
				<td colspan="3" align="center">
					<div class="lui_hr_staff_panel_overview_frame">
					<div class="lui_hr_staff_panel_title" style="text-align: left;">
						<span class="panel_title">${ lfn:message('hr-staff:hrStaff.overview.report.staffType') }</span>
					</div>
					<ui:chart width="100%" height="300px">
	  					<ui:source type="AjaxJson">
							{"url":"/hr/staff/hr_staff_person_report/hrStaffPersonReport.do?method=overviewChart&type=staffType"}
	  					</ui:source>
					</ui:chart>
					</div>
				</td>
			</tr>
			<kmss:auth requestURL="/hr/staff/hr_staff_person_info_log/hrStaffPersonInfoLog.do?method=list" requestMethod="GET">
			<tr>
				<td colspan="6"> <!-- 员工日志 -->
					<div class="lui_hr_staff_panel_overview_frame">
						<div class="lui_hr_staff_panel_title">
							<span class="panel_title">${ lfn:message('hr-staff:hrStaff.overview.employeeDynamics') }</span>
							<div class="panel_operation">
								<a href="javascript:openUrl('/person_info_log')" class="panel_btn_more" title="${ lfn:message('operation.more') }"></a>
							</div>
						</div>
						<ui:dataview>
							<ui:source type="AjaxJson">
								{url:'/hr/staff/hr_staff_person_info_log/hrStaffPersonInfoLog.do?method=overview'}
							</ui:source>
							<ui:render type="Template">
								{$
								<div class="lui_hr_staff_panel_content">
									<!-- 动态树 -->
									<div class="hr_staff_trends_tree">
								$}
								if(data.length < 1) {
									{$
									<div style="text-align: center;">
											<span class="trend_info">${ lfn:message('hr-staff:message.noRecord') }</span>
									</div>
									$}
								}
								for(var i=0; i<data.length; i++) {
									var obj = data[i]; // 这里的数据是对象，对象里有2个属性，一个是年月(title)，另一个是数组数据(list)
									{$
									<dl>
										<dt><i class="lui_icon_s icon_clock"></i><span>{% obj.title %}</span></dt>
									$}
									for(var j=0; j<obj.list.length; j++) {
									{$
									<dd>
										<a onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath}/hr/staff/hr_staff_person_info_log/hrStaffPersonInfoLog.do?method=view&fdId={% obj.list[j].fdId %}" target="_blank">
											<span class="trend_date">{% obj.list[j].fdCreateTime %}</span>
											<span class="trend_info"><em>{% obj.list[j].fdCreator %}</em> {% obj.list[j].fdDetails %}</span>
										</a>
									</dd>
									$}
									}
									{$
									</dl>
									$}
								}
								{$
									</div>
								</div>
								$}
							</ui:render>
						</ui:dataview>
					</div>
				</td>
			</tr>
			</kmss:auth>
		</table>
		</div>
		<script type="text/javascript">
		function openUrl(url){
			//LUI.pageOpen(url,"_self");
			//window.open(url);
		}
		
		function openUrl(url){
			
			parent.moduleAPI.hrStaff.openPreview(url);
			
		}
		</script>
	</template:replace>
</template:include>
