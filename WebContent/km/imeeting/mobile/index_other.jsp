<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="tiny" value="true" scope="request" />
<template:include ref="mobile.list" canHash="true"> 
	<template:replace name="title">
		<c:if test="${JsParam.moduleName!=null && JsParam.moduleName!=''}">
			${HtmlParam.moduleName}
		</c:if>
		<c:if test="${JsParam.moduleName==null or JsParam.moduleName==''}">
			<bean:message bundle="km-imeeting" key="module.km.imeeting"/>
		</c:if>
	</template:replace>
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/mobile/resource/css/list.css?s_cache=${MUI_Cache}" />
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/mobile/resource/css/meeting.css?s_cache=${MUI_Cache}" />
	</template:replace>
	<template:replace name="content">
		
		<div id="other" data-dojo-type="mui/tabbar/TabBarView">

			<ul class="otherMenuList">
				<!-- 会议审批 -->
				<li onclick="openPlaceAudit()">
					<i class="mui_meeting_other_audit"></i>
					<span><bean:message bundle="km-imeeting" key="mobile.audit.metting" /></span>
					<i class="mui mui-forward"></i>
					<i class="mui"><span id="placeAuditBadge" style="display:none;">0</span></i>
				</li>
				<!-- 会议查询 -->
				<li onclick="openMeetingList()">
					<i class="mui_meeting_other_search"></i>
					<span><bean:message bundle="km-imeeting" key="mobile.meetingSearch" /></span>
					<i class="mui mui-forward"></i>
				</li>
				<!-- 会议统计 -->
				<li onclick="openMeetingStatistics()">
					<i class="mui_meeting_other_stat"></i>
					<span><bean:message bundle="km-imeeting" key="mobile.meetingStatistics" /></span>
					<i class="mui mui-forward"></i>
				</li>
				
			</ul>

			<script>
				
				require(['dojo/request', 'dojo/dom', 'dojo/html', 'dojo/dom-style'], function(request, dom, html, domStyle) {
					
					
					window.openPlaceAudit = function() {
						window.open('${LUI_ContextPath}/km/imeeting/mobile/audit_list.jsp', '_self');
					}
					
					window.openMeetingList = function() {
						window.open('${LUI_ContextPath}/km/imeeting/mobile/index_list.jsp', '_self');
					}
					
					window.openMeetingStatistics = function() {
						window.open('${LUI_ContextPath}/km/imeeting/mobile/statistics_list.jsp', '_self');
					}
					
					
					request('${LUI_ContextPath}/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=getAuditCount', {
						method: 'get',
						handleAs: 'json'
					}).then(function(res){
						
						var count = res ? (res.count || 0) : 0;
						var placeAuditBadgeNode = dom.byId('placeAuditBadge');
						html.set(placeAuditBadgeNode, count);
						if(count <= 0) {
							domStyle.set(placeAuditBadgeNode, 'display', 'none');
						} else {
							domStyle.set(placeAuditBadgeNode, 'display', 'inline-block');
						}
						
					}, function(err) {
						console.error(err);
					});
					
				});
			
			</script>
			
			<div class="lui_db_footer">
			    <div class="lui_db_footer_item" onclick="navigate2View(0,'${JsParam.showList}')">
			      <div class="lui_db_footer_item_img my"></div>
			      <p><bean:message bundle="km-imeeting" key="mobile.myMeeting" /></p>
			    </div>
			    <div class="lui_db_footer_item" onclick="navigate2View(1,'${JsParam.showList}')">
			      <div class="lui_db_footer_item_img place"></div>
			      <p><bean:message bundle="km-imeeting" key="mobile.meetingRoom" /></p>
			    </div>
			    <div class="lui_db_footer_item " onclick="navigate2View(2,'${JsParam.showList}')">
			        <div class="lui_db_footer_item_img summary"></div>
			        <p><bean:message bundle="km-imeeting" key="mobile.meetingSummary" /></p>
			    </div>
			    <div class="lui_db_footer_item active" onclick="navigate2View(3,'${JsParam.showList}')">
			        <div class="lui_db_footer_item_img more"></div>
			        <p><bean:message bundle="km-imeeting" key="mobile.other" /></p>
			    </div>
			</div>
			<script>
				window.navigate2View = function(view,showList) {
					var url ="${LUI_ContextPath}/km/imeeting/mobile/";
					if(0 == view){
						if(showList == 'true'){
							url += "index_list_my.jsp"
						}else{
							url += "index.jsp"
						}
					}else if(1 == view){
						url += "index_place_new.jsp"
					}else if(2 == view){
						url += "index_summary.jsp"
					}else if(3 == view){
						url += "index_other.jsp"
					}
					if(showList == 'true'){
						url += "?showList="+showList;
					}
					window.open(url, '_self');
				}
			</script>
		
		</div>
		
		
	</template:replace>
</template:include>