<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="head">
		<style type="text/css">
		</style>
	</template:replace>
	<template:replace name="content"> 
		<p class="txttitle" style="margin: 10px 0;">
			${ lfn:message('sys-time:sysTimeLeaveDetail.selectReview') }
		</p>
		<table class="tb_normal" width="98%">
			<tr>
				<td colspan="4">
					<list:criteria id="criteria1">
						<list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${ lfn:message('km-review:kmReviewMain.docSubject') }"></list:cri-ref>
					</list:criteria>
					<div style="max-height: 270px;overflow-y: auto;">
						<%--列表--%>
						<list:listview id="listview" style="">
							<ui:source type="AjaxJson" >
								{url:'/km/review/km_review_index/kmReviewIndex.do?method=list&rowsize=10'}
							</ui:source>
							<list:colTable isDefault="false" layout="sys.ui.listview.listtable" name="columntable" onRowClick="selectReview('!{fdId}');">
								<list:col-radio></list:col-radio>
								<list:col-serial></list:col-serial>
								<list:col-html title="${ lfn:message('km-review:kmReviewMain.docSubject') }">
									{$ <span data-id="{%row['fdId']%}">{%row['docSubject']%}</span> $}
								</list:col-html>
								<list:col-html title="${ lfn:message('km-review:kmReviewMain.docCreateTime') }">
									{$ {%row['docCreateTime_time']%} $}
								</list:col-html>
								<%-- <list:col-auto props="docCreateTime;"></list:col-auto> --%>
							</list:colTable>
							<ui:event topic="list.loaded">
								var reviewId="${JsParam.reviewId}";
								if(reviewId){
									$('[name="List_Selected"][value="'+ reviewId +'"]').prop('checked','checked');
								}
							</ui:event>
						</list:listview>
						<list:paging></list:paging>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="4" style="text-align: center;">
				  	<%--选择--%>
					<ui:button text="${lfn:message('button.select')}"   onclick="doSubmit();"/>
					<%--取消--%>
					<ui:button text="${lfn:message('button.cancel') }"  onclick="cancel();"/>
				</td>
			</tr>
		</table>
	
		<script type="text/javascript">
			seajs.use([
		 	      'lui/jquery',
		 	      'lui/dialog',
		 	      'lui/topic',
		 	      'lui/util/str'
		 	        ],function($,dialog,topic,str){
				window.selectReview = function(reviewId) {
					if($('[name="List_Selected"][value="'+ reviewId +'"]').is(':checked')){
						$('[name="List_Selected"][value="'+ reviewId +'"]').prop('checked', false);
					}else{
						$('[name="List_Selected"][value="'+ reviewId +'"]').prop('checked',true);
					}
				}
				
				window.doSubmit = function() {
					var reviewId = $('[name="List_Selected"]:checked').val();
					if(reviewId) {
						var reviewName = $('span[data-id="'+ reviewId +'"]').text();
						if(reviewName) {
							reviewName = str.decodeHTML(reviewName);
						}
						$dialog.hide({reviewId:reviewId,reviewName:reviewName});
					} else {
						$dialog.hide(null);
					}
				};
				
				window.cancel = function() {
					$dialog.hide(null);
				}
			});
	</script>
	</template:replace>
</template:include>
