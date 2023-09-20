<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple4list">
<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/sys/attend/resource/css/attend.css" />
		<style type="text/css">
			td.orgTd {position: relative;}
			td.orgTd label{position: absolute;top: 10px;margin-left: 5px;}
			.btn_txt {margin: 0px 2px;color: #2574ad;border-bottom: 1px solid transparent;}
			.btn_txt:hover {color: #123a6b;border-bottom-color: #123a6b;}
			.sign_status {color: #2574ad}
			.sign_status:hover {border-bottom:1px solid #2574ad;}
			.sign_status_exc {color: #f00;font-weight: bold;}
			.sign_status_exc:hover {border-bottom:1px solid #f00;}
		</style>
	</template:replace>
<template:replace name="content">
<div class="lui-attend-stat-table">
	<list:listview id="attendStatListview" cfg-needMinHeight="false">
						<ui:source type="AjaxJson">
								{url:"/sys/attend/sys_attend_main/sysAttendMain.do?method=statListDetail&fdEndTime=${fdEndTime}&fdTargetId=${fdTargetId}"}
						</ui:source>
					<!-- 列表视图 -->	
					<list:colTable isDefault="false" layout="sys.attend.listview.stat" 
						rowHref="">
						<list:col-serial></list:col-serial>
						<list:col-auto props="docCreator.fdName;docCreator.fdDept;fdCategoryName;"></list:col-auto>
						<list:col-html id="fdDate" headerStyle="min-width: 130px;" sort="fdDate" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdDate') }">{$ {%row['fdDate']%} $}</list:col-html>
						<list:col-auto props="fdDateType"></list:col-auto>
						<list:col-auto props="fdSignTime;docStatus;fdState;fdSignTime2;docStatus2;fdState2;"></list:col-auto>
						<list:col-auto props="fdSignTime3;docStatus3;fdState3;fdSignTime4;docStatus4;fdState4;"></list:col-auto>
						<list:col-auto props="fdSignTime5;docStatus5;fdState5;fdSignTime6;docStatus6;fdState6;"></list:col-auto>
						<list:col-auto props="fdSignTime7;docStatus7;fdState7;fdSignTime8;docStatus8;fdState8;"></list:col-auto>
						<list:col-auto props="fdSignTime9;docStatus9;fdState9;fdSignTime10;docStatus10;fdState10;"></list:col-auto>
						<list:col-html sort="fdTotalTime" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdTotalTime') }">{$ {%row['fdTotalTime']%} $}</list:col-html>
						<list:col-html sort="fdOverTime" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdOverTime') }">{$ {%row['fdOverTime']%} $}</list:col-html>
						<list:col-html sort="fdOutgoingTime" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdOutgoingTime') }">{$ {%row['fdOutgoingTime']%} $}</list:col-html>
						<list:col-html sort="fdLateTime" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdLateTime') }">{$ {%row['fdLateTime']%} $}</list:col-html>
						<list:col-html sort="fdLeftTime" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdLeftTime') }">{$ {%row['fdLeftTime']%} $}</list:col-html>
						<list:col-html sort="fdAbsentDays" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdAbsentDays') }">{$ {%row['fdAbsentDays']%} $}</list:col-html>
						<list:col-html sort="fdPersonalLeaveDays" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdPersonalLeaveDays') }">{$ {%row['fdPersonalLeaveDays']%} $}</list:col-html>
						<list:col-html sort="fdOffDays" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdOffDays') }">{$ {%row['fdOffDays']%} $}</list:col-html>
						<list:col-html sort="fdTripDays" headerClass="lui_thead" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdTripDays') }">{$ {%row['fdTripDays']%} $}</list:col-html>
						
					</list:colTable>
					<ui:event topic="list.loaded">
						onResize();
						
					</ui:event>
					</list:listview> 
					
<list:paging></list:paging>
</div>
<script>
	seajs.use(['lui/jquery'],function($){
		window.onResize=function(){
			$(window.parent.document).find('iframe#statIframe').height($(document.body).height());
		}
	})
</script>
</template:replace>
</template:include>