<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include rwd="true" spa="true"
	ref="expand.list">
	<template:replace name="title">
		<c:out value="${ lfn:message('kms-lservice:kmsLservice.homePage') }"></c:out>-${lfn:message('kms-lservice:lservice.role.admin')}
	</template:replace>
	
	<template:replace name="path">
		<c:import url="/kms/lservice/index/admin/path.jsp"
			charEncoding="UTF-8">
			<c:param name="title"
				value="${lfn:message('kms-lservice:lservice.role.admin') }"></c:param>
		</c:import>
	</template:replace>

	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/kms/lservice/index/common/style/person_common.css?s_cache=${ LUI_Cache }" />
		<link rel="Stylesheet" href="${LUI_ContextPath}/kms/lservice/index/student/calendar/css/calendar_lservice.css" />
		<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/kms/lservice/index/common/style/icon/iconfont.css" />
	</template:replace>
	
	<template:replace name="banner">
		<c:import url="/kms/lservice/index/admin/banner.jsp" charEncoding="UTF-8">
			<c:param name="modelName"
				value="com.landray.kmss.kms.learn.model.KmsLearnMain"></c:param>
		</c:import>
	</template:replace>
	
	<template:replace name="nav">
		<c:import url="/kms/lservice/index/admin/nav.jsp"
			charEncoding="utf-8">
			<c:param name="modelName"
				value="com.landray.kmss.kms.lservice.model.PStudyCenter"></c:param>
			<c:param name="type" value="admin" />
		</c:import>

	</template:replace>

	<template:replace name="content">
		<div>
			<div class="lui_listview_gridtable_summary_box">
				<table class="lui_listview_landrayblue_table">
					<tbody>
						<tr>
							<c:set var="_tdCount" value="0" />
							<kmss:ifModuleExist path="/kms/exam/">
								<c:if test="${_tdCount > 0 && (_tdCount % 3) == 0}" >
									</tr><tr>
								</c:if>
								<c:set var="_tdCount" value="${_tdCount + 1}" />

							<td width="33%">
								<a onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath}/kms/exam/kms_exam_unified_activity/kmsExamUnifiedActivity.do?method=add" target="_blank">
									<div class="lservice_admin_link">
											<div style="width: 112px;height: 131px;">
												<img class="lservice_admin_link_img" src="./img/icon_exam.png">
											</div>
											<div style="width: 208px;height: 131px;display: grid;">
												<span class="lservice_admin_link_title">${lfn:message('kms-lservice:kmsLservice.admin.exam.link') }</span>
												<span class="lservice_admin_link_des">${lfn:message('kms-lservice:kmsLservice.admin.exam.link.desc') }</span>
											</div>
									</div>
								</a>
							</td>
							</kmss:ifModuleExist>
							<kmss:ifModuleExist path="/kms/exam/">
							<c:if test="${_tdCount > 0 && (_tdCount % 3) == 0}" >
								</tr><tr>
							</c:if>
							<c:set var="_tdCount" value="${_tdCount + 1}" />
							<td width="33%">
								<a onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath}/kms/exam/kms_exam_topic/kmsExamTopic.do?method=addTopic" target="_blank">
									<div class="lservice_admin_link">
										<div style="width: 112px;height: 131px;">
											<img class="lservice_admin_link_img" src="./img/icon_add_exam.png">
										</div>
										<div style="width: 208px;height: 131px;display: grid;">
											<span class="lservice_admin_link_title">${lfn:message('kms-lservice:kmsLservice.admin.exam.question.link') }</span>
											<span class="lservice_admin_link_des">${lfn:message('kms-lservice:kmsLservice.admin.exam.question.link.desc') }</span>
										</div>
									</div>
								</a>
							</td>
							</kmss:ifModuleExist>
							<kmss:ifModuleExist path="/kms/learn/">
							<c:if test="${_tdCount > 0 && (_tdCount % 3) == 0}" >
								</tr><tr>
							</c:if>
							<c:set var="_tdCount" value="${_tdCount + 1}" />
							<td width="33%">
								<a onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath}/kms/learn/kms_learn_main/kmsLearnMain.do?method=add" target="_blank">
									<div class="lservice_admin_link">
										<div style="width: 112px;height: 131px;">
											<img class="lservice_admin_link_img" src="./img/icon_learn.png">
										</div>
										<div style="width: 208px;height: 131px;display: grid;">
											<span class="lservice_admin_link_title">${lfn:message('kms-lservice:kmsLservice.admin.learnMain.link') }</span>
											<span class="lservice_admin_link_des">${lfn:message('kms-lservice:kmsLservice.admin.learnMain.link.desc') }</span>
										</div>
									</div>
								</a>
							</td>
							</kmss:ifModuleExist>
							<kmss:ifModuleExist path="/kms/learn/">
							<c:if test="${_tdCount > 0 && (_tdCount % 3) == 0}" >
								</tr><tr>
							</c:if>
							<c:set var="_tdCount" value="${_tdCount + 1}" />
							<td width="33%">
								<a onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath}/kms/learn/kms_learn_activity/kmsLearnActivity.do?method=add" target="_blank">
									<div class="lservice_admin_link">
										<div style="width: 112px;height: 131px;">
											<img class="lservice_admin_link_img" src="./img/icon_learn_mission.png">
										</div>
										<div style="width: 208px;height: 131px;display: grid;">
											<span class="lservice_admin_link_title">${lfn:message('kms-lservice:kmsLservice.admin.learnActivity.link') }</span>
											<span class="lservice_admin_link_des">${lfn:message('kms-lservice:kmsLservice.admin.learnActivity.link.desc') }</span>
										</div>
									</div>
								</a>
							</td>
							</kmss:ifModuleExist>
							<kmss:ifModuleExist path="/kms/train/">
							<c:if test="${_tdCount > 0 && (_tdCount % 3) == 0}" >
								</tr><tr>
							</c:if>
							<c:set var="_tdCount" value="${_tdCount + 1}" />
							<td width="33%">
								<a onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath}/kms/train/kms_train_plan/kmsTrainPlan.do?method=add" target="_blank">
									<div class="lservice_admin_link">
										<div style="width: 112px;height: 131px;">
											<img class="lservice_admin_link_img" src="./img/icon_train.png">
										</div>
										<div style="width: 208px;height: 131px;display: grid;">
											<span class="lservice_admin_link_title">${lfn:message('kms-lservice:kmsLservice.admin.trainActivity.link') }</span>
											<span class="lservice_admin_link_des">${lfn:message('kms-lservice:kmsLservice.admin.trainActivity.link.desc') }</span>
										</div>
									</div>
								</a>
							</td>
							</kmss:ifModuleExist>
							<kmss:ifModuleExist path="/kms/lmap/">
							<c:if test="${_tdCount > 0 && (_tdCount % 3) == 0}" >
								</tr><tr>
							</c:if>
							<c:set var="_tdCount" value="${_tdCount + 1}" />
							<td width="33%">
								<a onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath}/kms/lmap/kms_lmap_main/kmsLmapMain.do?method=add" target="_blank">
									<div class="lservice_admin_link">
										<div style="width: 112px;height: 131px;">
											<img class="lservice_admin_link_img" src="./img/icon_lmap.png">
										</div>
										<div style="width: 208px;height: 131px;display: grid;">
											<span class="lservice_admin_link_title">${lfn:message('kms-lservice:kmsLservice.admin.lmap.link') }</span>
											<span class="lservice_admin_link_des">${lfn:message('kms-lservice:kmsLservice.admin.lmap.link.desc') }</span>
										</div>
									</div>
								</a>
							</td>
							</kmss:ifModuleExist>

							<c:if test="${_tdCount > 0 && (_tdCount % 3) > 0}" >
							<c:forEach begin="${_tdCount % 3}" end="2">
								<td width="33%"></td>
							</c:forEach>
							</c:if>

						</tr>
					</tbody>
				</table>
			<div>
			<div class="lservice_admin_mission" style="padding: 10px 20px 20px 30px;">
				<div class="lservice_student_mission_title"><img style="height: 24px;vertical-align: bottom;margin-right: 7px;" src="${LUI_ContextPath}/kms/lservice/index/common/style/img/pic_1.png">${lfn:message('kms-lservice:kmsLservice.admin.last.month') }
					<kmss:ifModuleExist path="/kms/loperation">
						<a class="lservice_more" onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath}/kms/loperation" target="_blank">
							${lfn:message('kms-lservice:kmsLservice.admin.more.total') }
							<i class="lservice_admin_caret"></i>
						</a>
					</kmss:ifModuleExist>
				</div>
					<c:import url="/kms/lservice/index/admin/month_sum_info.jsp" charEncoding="UTF-8"/>

				<div style="border: 1px solid #F6F6F6;margin: 53px 0px 35px 0px;"></div>
				<div class="lservice_student_mission_title">${lfn:message('kms-lservice:kmsLservice.admin.title.rate') }</div>
				<div id="chart_main" style="display: flex;">
					<!-- 学习任务 -->
					<kmss:ifModuleExist path="/kms/learn/">
						<div id="chart_bar_learn" style="flex:1;">
							<div id="appBarlearn" style="text-align: center; width: 100%;height: 170px;"></div>
							<span style="width: 100%;display: inline-block;text-align: center;position: relative;top: -20px;">${lfn:message('kms-lservice:kmsLservice.admin.learn.rate') }</span>
						</div>
					</kmss:ifModuleExist>

					<!-- 考试管理 -->
					<kmss:ifModuleExist path="/kms/exam/">
						<div id="chart_bar_exam" style="flex:1;">
							<div id="appBarexam" style="text-align: center; width: 100%;height: 170px;"></div>
							<span style="width: 100%;display: inline-block;text-align: center;position: relative;top: -20px;">${lfn:message('kms-lservice:kmsLservice.admin.exam.rate') }</span>
						</div>
					</kmss:ifModuleExist>

					<!-- 培训任务 -->
					<kmss:ifModuleExist path="/kms/train/">
						<div id="chart_bar_train" style="flex:1;">
							<div id="appBartrain" style="text-align: center; width: 100%;height: 170px;"></div>
							<span style="width: 100%;display: inline-block;text-align: center;position: relative;top: -20px;">${lfn:message('kms-lservice:kmsLservice.admin.train.rate') }</span>
						</div>
					</kmss:ifModuleExist>

					<!-- 学习地图 -->
					<kmss:ifModuleExist path="/kms/lmap/">
						<div id="chart_bar_lmap" style="flex:1;">
							<div id="appBarlmap" style="text-align: center; width: 100%;height: 170px;"></div>
							<span style="width: 100%;display: inline-block;text-align: center;position: relative;top: -20px;">${lfn:message('kms-lservice:kmsLservice.admin.lmap.rate') }</span>
						</div>
					</kmss:ifModuleExist>
					<%@ include file="/kms/lservice/index/admin/echart/learn_rate.jsp"%>
				</div>
			</div>
			<div class="lservice_admin_mission" style="margin-top: 20px;padding: 10px 20px 20px 30px;">

				<kmss:ifModuleExist path="/kms/exam/">
				<div class="lservice_student_mission_title"><img style="height: 24px;vertical-align: bottom;margin-right: 7px;" src="${LUI_ContextPath}/kms/lservice/index/common/style/img/pic_1.png">${lfn:message('kms-lservice:kmsLservice.admin.exam.doing') }<a class="lservice_more" onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath}/kms/exam/admin/index.jsp#j_path=%2Fall" target="_blank">${lfn:message('kms-lservice:kmsLservice.admin.exam.more') }<i class="lservice_admin_caret"></i></a></div>
				<ui:dataview style="font-size: 0;margin-top: 25px;margin-bottom: 50px;">
					<ui:source type="AjaxJson">
						{url : "/kms/exam/kms_exam_unified_activity/kmsExamUnifiedActivity.do?method=getDoingInfo"}
					</ui:source>
					<ui:render type="Template">
						if(data.length==0){
							{$
									<c:import url="/kms/lservice/index/admin/noData.jsp" charEncoding="UTF-8"></c:import>
							$}
						}					
						for (var i = 0; i < data.length; i ++) {
						 var info = data[i]
						{$
						<a onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath}/kms/exam/kms_exam_unified_activity/kmsExamUnifiedActivity.do?method=view&fdId={%info.id%}" target="_blank">
						<div class="lservice_admin_doing" style="height: 131px">
							<div class="lservice_admin_doing_head">
								<span class="lservice_admin_doing_head_title">{%info.title%}</span>
								<span class="lservice_admin_doing_head_subTitle">{%info.finishNum%}${lfn:message('kms-lservice:kmsLservice.admin.exam.done') }</span>
							</div>
							<div class="lservice_admin_doing_head_time">{%info.start%} - {%info.end%}</div>
							<div class="lservice_admin_doing_head_detail">${lfn:message('kms-lservice:kmsLservice.admin.exam.score') }：{%info.scoreSum%}  ${lfn:message('kms-lservice:kmsLservice.admin.exam.passing') }：{%info.passScore%}  ${lfn:message('kms-lservice:kmsLservice.admin.exam.num') }：{%info.num%}  ${lfn:message('kms-lservice:kmsLservice.admin.exam.time') }：{%info.time%}</div>
						</div>
						</a>
						$}
						}
					</ui:render>
				</ui:dataview>
				</kmss:ifModuleExist>

				<kmss:ifModuleExist path="/kms/learn/">
				<div class="lservice_student_mission_title"><img style="height: 24px;vertical-align: bottom;margin-right: 7px;" src="${LUI_ContextPath}/kms/lservice/index/common/style/img/pic_2.png">${lfn:message('kms-lservice:kmsLservice.admin.learn.doing') }<a class="lservice_more" href="${LUI_ContextPath}/kms/learn/main/admin/index.jsp#j_path=%2Ftask" target="_blank">${lfn:message('kms-lservice:kmsLservice.admin.learn.more') }<i class="lservice_admin_caret"></i></a></div>
				<ui:dataview style="font-size: 0;margin-top: 25px;margin-bottom: 50px;">
					<ui:source type="AjaxJson">
						{url : "/kms/learn/kms_learn_activity/kmsLearnActivity.do?method=getDoingInfo"}
					</ui:source>
					<ui:render type="Template">
						if(data.length==0){
							{$
									<c:import url="/kms/lservice/index/admin/noData.jsp" charEncoding="UTF-8"></c:import>
							$}
						}
						for (var i = 0; i < data.length; i ++) {
						 var info = data[i]
						{$
						<a onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath}/kms/learn/kms_learn_activity/kmsLearnActivity.do?method=view&fdId={%info.id%}" target="_blank">
						<div class="lservice_admin_doing" style="height: 157px;position: relative;">
							<div class="lservice_admin_doing_head">
								<span class="lservice_admin_doing_head_title">{%info.title%}</span>
							</div>
							<div class="lservice_admin_doing_head_time">{%info.start%} - {%info.end%}</div>
							<div class="lservice_admin_doing_head_detail"><span class="lservice_admin_doing_sum">${lfn:message('kms-lservice:kmsLservice.admin.learn.main') }：{%info.learnName%}</span><span style="width: 48%;float: right;">${lfn:message('kms-lservice:kmsLservice.admin.learn.credit') }：{%info.credit%}</span></div>
							<div class="lservice_admin_doing_head_detail" style="margin-top: 8px;"><span>${lfn:message('kms-lservice:kmsLservice.admin.learn.done') }：{%info.finish%}</span><span style="width: 48%;float: right;">${lfn:message('kms-lservice:kmsLservice.admin.learn.done.Rate') }：{%info.rate%}%</span></div>
							<div style="height: 4px;width: 100%;position: absolute;bottom: 0;">
								<span class="lservice_admin_doing_bar"><em class="bars" style="width: {%info.rate%}%;"></em></span>	
							</div>
						</div>
						</a>
						$}
						}
					</ui:render>
				</ui:dataview>
				</kmss:ifModuleExist>

				<kmss:ifModuleExist path="/kms/train/">
				<div class="lservice_student_mission_title"><img style="height: 24px;vertical-align: bottom;margin-right: 7px;" src="${LUI_ContextPath}/kms/lservice/index/common/style/img/pic_1.png">${lfn:message('kms-lservice:kmsLservice.admin.train.doing') }<a class="lservice_more" href="${LUI_ContextPath}/kms/train/admin/index.jsp#j_path=%2Fallplan&cri.kmsTrainPlanAll_admin_allPlan.q=trainStatus%3A2" target="_blank">${lfn:message('kms-lservice:kmsLservice.admin.train.more') }<i class="lservice_admin_caret"></i></a></div>
				<ui:dataview style="font-size: 0;margin-top: 25px;margin-bottom: 50px;">
					<ui:source type="AjaxJson">
						{url : "/kms/train/kms_train_plan/kmsTrainPlan.do?method=getDoingInfo"}
					</ui:source>
					<ui:render type="Template">
						if(data.length==0){
							{$
									<c:import url="/kms/lservice/index/admin/noData.jsp" charEncoding="UTF-8"></c:import>
							$}
						}					
						for (var i = 0; i < data.length; i ++) {
						 var info = data[i]
						{$
						<a onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath}/kms/train/kms_train_plan/kmsTrainPlan.do?method=view&type=viewToEdit&fdId={%info.id%}&operator=admin" target="_blank">
						<div class="lservice_admin_doing" style="height: 131px">
							<div class="lservice_admin_doing_head">
								<span class="lservice_admin_doing_head_title">{%info.title%}</span>
							</div>
							<div class="lservice_admin_doing_head_time">{%info.start%} - {%info.end%}</div>
							<div class="lservice_admin_doing_head_detail" style="margin-top: 8px;"><span>${lfn:message('kms-lservice:kmsLservice.admin.train.teacher') }：{%info.lecturer[0].name%}</span><span style="width: 33%;float: right;">${lfn:message('kms-lservice:kmsLservice.admin.train.joined') }：{%info.num%}</span><span class="lservice_admin_doing_sum" style="width: 33%;float: right;">${lfn:message('kms-lservice:kmsLservice.admin.train.place') }：{%info.place%}</span></div>
						</div>
						</a>
						$}
						}
					</ui:render>
				</ui:dataview>
				</kmss:ifModuleExist>

				<kmss:ifModuleExist path="/kms/lmap/">
				<div class="lservice_student_mission_title"><img style="height: 24px;vertical-align: bottom;margin-right: 7px;" src="${LUI_ContextPath}/kms/lservice/index/common/style/img/pic_1.png">${lfn:message('kms-lservice:kmsLservice.admin.lmap.doing') }<a class="lservice_more" href="${LUI_ContextPath}/kms/lmap/main/admin/index.jsp#cri.kmsLmapAll_all.q=docStatus%3A30" target="_blank">${lfn:message('kms-lservice:kmsLservice.admin.lmap.more') }<i class="lservice_admin_caret"></i></a></div>
				<ui:dataview style="font-size: 0;margin-top: 25px;margin-bottom: 50px;">
					<ui:source type="AjaxJson">
						{url : "/kms/lmap/kms_lmap_main/kmsLmapMain.do?method=getDoingInfo"}
					</ui:source>
					<ui:render type="Template">
						if(data.length==0){
							{$
									<c:import url="/kms/lservice/index/admin/noData.jsp" charEncoding="UTF-8"></c:import>
							$}
						}
						for (var i = 0; i < data.length; i ++) {
						 var info = data[i]
						{$
						<a onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath}/kms/lmap/kms_lmap_main/kmsLmapMain.do?method=view&fdId={%info.id%}&type=admin" target="_blank">
						<div class="lservice_admin_doing" style="height: 131px">
							<div class="lservice_admin_doing_head">
								<span class="lservice_admin_doing_head_title">{%info.title%}</span>
							</div>
							<div class="lservice_admin_doing_head_time">{%info.start%} -  {%info.end%}</div>
							<div class="lservice_admin_doing_head_detail" style="margin-top: 8px;"><span>${lfn:message('kms-lservice:kmsLservice.admin.lmap.creator') }：{%info.name%}</span><span style="width: 33%;float: left;">${lfn:message('kms-lservice:kmsLservice.admin.lmap.done') }：{%info.num%}</span></div>
						</div>
						</a>
						$}
						}
					</ui:render>
				</ui:dataview>
				</kmss:ifModuleExist>
			</div>
		</div>
	</template:replace>
	
</template:include>

