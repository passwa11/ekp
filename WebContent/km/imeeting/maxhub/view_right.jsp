<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<section class="mhui-template-moduleR" style="position: relative; top: 0.8rem;">
	
	<div class="currentTime">
		<div class="currentTimeWrapper">
			<span>当前时间</span>		
			<div id="currentTime">-</div>
		</div>
	</div>

	<ul data-dojo-type="mhui/nav/Nav"
		data-dojo-props="data:[
			{text:'会议信息',viewId:'indexView'}
			<c:if test="${kmImeetingMainForm.isFace == false}">
				<kmss:ifModuleExist path="/sys/attend">
					,{text:'会议签到',viewId:'sysAttendView_${kmImeetingMainForm.fdId}'}
				</kmss:ifModuleExist>
			</c:if>
			<kmss:ifModuleExist path="/sys/task">
				,{text:'任务信息',viewId:'taskView_${kmImeetingMainForm.fdId}'}
			</kmss:ifModuleExist>
			<kmss:ifModuleExist path="/km/vote">
				,{text:'会议投票',viewId:'kmVoteView__${kmImeetingMainForm.fdId}'}
			</kmss:ifModuleExist>

		]">
	</ul>
</section>
