<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<c:set var="tiny" value="true" scope="request" />
<template:include ref="mobile.list" canHash="true">
	<template:replace name="title">
		会议审批
	</template:replace>
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/mobile/resource/css/book.css?s_cache=${MUI_Cache}" />
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/mobile/resource/css/meeting.css?s_cache=${MUI_Cache}" />
	</template:replace>
	<template:replace name="content">
		
		<div data-dojo-type="mui/header/Header" data-dojo-props="height:'4.4rem'" class="muiHeaderNav">
			<div
				data-dojo-type="mui/nav/MobileCfgNavBar" 
				data-dojo-props=" defaultUrl:'/km/imeeting/mobile/nav_audit.jsp' ">
			</div>
			
			<%-- 搜索 --%>
			<div data-dojo-type="mui/search/SearchButtonBar"
				 data-dojo-props="modelName:'com.landray.kmss.km.imeeting.model.KmImeetingSummary'">
			</div>
		</div>
		
		<div data-dojo-type="mui/header/NavHeader">
		</div>
		
		<div class="mui_ekp_meeting_cardList" data-dojo-type="mui/list/NavView">
		    <ul data-dojo-type="mui/list/HashJsonStoreList" 
		    	data-dojo-mixins="km/imeeting/mobile/resource/js/list/AuditItemListMixin">
			</ul>
		</div>
		
		
		<script>
		
			require(['dojo/topic', 'mui/dialog/Confirm', 'mui/dialog/Tip', 'dojo/request', 'dojo/dom'], 
					function(topic, Confirm, Tip, request, dom) {
				
				topic.subscribe('km/imeeting/clickbook', function(res) {
					window.open('${LUI_ContextPath}/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=view&fdId=' + res.fdId, '_self');
				});

				topic.subscribe('km/imeeting/agreebook', function(res) {

					Confirm('<span><bean:message key="kmImeeting.btn.agree.tip" bundle="km-imeeting"/></span>', res.fdPlace, function(check, d) {
						if(!check) {
							return;
						}
						
						var processTip = Tip.processing();
						request('${LUI_ContextPath}/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=exam', {
							method: 'post',
							data: {
								bookId: res.fdId, 
								fdHasExam: 'true', 
								fdExamRemark: ''
							}
						}).then(function(res){
							processTip.hide();

							Tip.success({
								text: '<bean:message key="mobile.success.tip" bundle="km-imeeting"/>'
							});
							
							setTimeout(function() {
								location.reload();
							}, 200);
						}, function(err) {
							processTip.hide();
							
							Tip.fail({
								text: '<bean:message key="mobile.fail.tip" bundle="km-imeeting"/>'
							});
							
						});
					}, false, function() {
					
					});
				});
				
				topic.subscribe('km/imeeting/rejectbook', function(res) {
					
					Confirm('<textarea id="rejectReason" placeholder="<bean:message key="kmImeeting.reject.reason" bundle="km-imeeting"/>"></textarea>', 
							res.fdPlace, function(check, d) {
						
						if(!check) {
							return;
						}

						var rejectReasonNode = dom.byId('rejectReason');
						var rejectReason = rejectReasonNode ? rejectReasonNode.value : '';
						if(!rejectReason) {
							Tip.fail({
								text: '<bean:message key="kmImeeting.reject.reason" bundle="km-imeeting"/>'
							});
							return;
						}
								
						var processTip = Tip.processing();
						request( '${LUI_ContextPath}/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=exam', {
							method: 'post',
							data: {
								bookId: res.fdId, 
								fdHasExam: 'false', 
								fdExamRemark: rejectReason
							}
						}).then(function(res){
							processTip.hide();

							Tip.success({
								text: '<bean:message key="mobile.success.tip" bundle="km-imeeting"/>'
							});
							
							setTimeout(function() {
								location.reload();
							}, 200);
						}, function(err) {
							processTip.hide();
							
							Tip.fail({
								text: '<bean:message key="mobile.fail.tip" bundle="km-imeeting"/>'
							});
							
						});
								
					}, true, function() {
						
					}, {
						checkTitle: '<span style="color: #F95A5A;"><bean:message key="kmImeeting.status.reject" bundle="km-imeeting"/></span>',
						checkValidator: function() {
							var rejectReasonNode = dom.byId('rejectReason');
							var rejectReason = rejectReasonNode ? rejectReasonNode.value : '';
							if(!rejectReason) {
								Tip.fail({
									text: '<bean:message key="kmImeeting.reject.reason" bundle="km-imeeting"/>'
								});
								return false;
							}
							
							return true;
						}
					});	
				});
				
			});
		
		</script>
		
	</template:replace>
</template:include>

