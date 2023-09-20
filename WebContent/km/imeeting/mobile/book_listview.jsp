<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<template:include ref="mobile.list">
	<template:replace name="title">
		会议室审核
	</template:replace>
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/mobile/resource/css/book.css?s_cache=${MUI_Cache}" />
	</template:replace>
	<template:replace name="content">
		
		<div data-dojo-type="mui/header/Header" data-dojo-props="height:'3.8rem'">
			<div
				data-dojo-type="mui/nav/MobileCfgNavBar" 
				data-dojo-props=" defaultUrl:'/km/imeeting/mobile/book_listview_nav.jsp',modelName:'com.landray.kmss.km.imeeting.model.KmImeetingBook' ">
			</div>
			
			<div data-dojo-type="mui/search/SearchButtonBar"
				 data-dojo-props="modelName:'com.landray.kmss.km.imeeting.model.KmImeetingBook'">
			</div>
		</div>
		
		<div class="meetingBookScrollableView" 
			data-dojo-type="mui/list/NavSwapScrollableView">
		    <ul class="meetingBookList"
		    	data-dojo-type="mui/list/JsonStoreList" 
		    	data-dojo-mixins="km/imeeting/mobile/resource/js/list/MeetingBookItemListMixin">
			</ul>
		</div>
		
		
		<script>
		
			require(['dojo/topic', 'mui/dialog/Confirm', 'mui/dialog/Tip', 'dojo/request', 'dojo/dom'], 
					function(topic, Confirm, Tip, request, dom) {
				
				topic.subscribe('km/imeeting/clickbook', function(res) {
					window.open('${LUI_ContextPath}/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=view&fdId=' + res.fdId, '_self');
				});

				topic.subscribe('km/imeeting/agreebook', function(res) {

					Confirm('<span>是否确认通过审批？</span>', res.fdPlace, function(check, d) {
						if(!check) {
							return;
						}
						
						var processTip = Tip.processing('提交中');
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
								text: '提交成功'
							});
							
							setTimeout(function() {
								location.reload();
							}, 200);
						}, function(err) {
							processTip.hide();
							
							Tip.fail({
								text: '提交失败'
							});
							
						});
					}, false, function() {
					
					});
				});
				
				topic.subscribe('km/imeeting/rejectbook', function(res) {
					
					Confirm('<textarea id="rejectReason" placeholder="请填写驳回理由"></textarea>', 
							res.fdPlace, function(check, d) {
						
						if(!check) {
							return;
						}

						var rejectReasonNode = dom.byId('rejectReason');
						var rejectReason = rejectReasonNode ? rejectReasonNode.value : '';
						if(!rejectReason) {
							Tip.fail({
								text: '请填写驳回理由'
							});
							return;
						}
								
						var processTip = Tip.processing('提交中');
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
								text: '提交成功'
							});
							
							setTimeout(function() {
								location.reload();
							}, 200);
						}, function(err) {
							processTip.hide();
							
							Tip.fail({
								text: '提交失败'
							});
							
						});
								
					}, true, function() {
						
					}, {
						checkTitle: '<span style="color: #F95A5A;">驳回</span>',
						checkValidator: function() {
							var rejectReasonNode = dom.byId('rejectReason');
							var rejectReason = rejectReasonNode ? rejectReasonNode.value : '';
							if(!rejectReason) {
								Tip.fail({
									text: '请填写驳回理由'
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

