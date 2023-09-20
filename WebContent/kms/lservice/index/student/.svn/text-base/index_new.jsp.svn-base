<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include rwd="true" spa="true"
	ref="expand.list">
	<template:replace name="title">
		<c:out value="${ lfn:message('kms-lservice:kmsLservice.homePage') }"></c:out>-${lfn:message('kms-lservice:lservice.role.student')}
	</template:replace>
	
	<template:replace name="path">
		<c:import url="/kms/lservice/index/student/path.jsp"
			charEncoding="UTF-8">
			<c:param name="title"
				value="${lfn:message('kms-lservice:lservice.role.student') }"></c:param>
		</c:import>
	</template:replace>

	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/kms/lservice/index/common/style/person_common.css?s_cache=${ LUI_Cache }" />
		<link rel="Stylesheet" href="${LUI_ContextPath}/kms/lservice/index/student/calendar/css/calendar_lservice.css" />
		<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/kms/lservice/index/common/style/icon/iconfont.css?s_cache=${ LUI_Cache }" />
	</template:replace>
	
	<template:replace name="banner">
		<c:import url="/kms/lservice/index/student/banner.jsp" charEncoding="UTF-8">
			<c:param name="modelName"
				value="com.landray.kmss.kms.learn.model.KmsLearnMain"></c:param>
		</c:import>
	</template:replace>
	
	<template:replace name="nav">
		<c:import url="/kms/lservice/index/student/nav.jsp"
			charEncoding="utf-8">
			<c:param name="modelName"
				value="com.landray.kmss.kms.lservice.model.PStudyCenter"></c:param>
			<c:param name="type" value="student" />
		</c:import>

	</template:replace>

	<template:replace name="content">
		<div>
			<div style="display: flex;margin-bottom: 20px;">
				<kmss:ifModuleExist path="/kms/credit">
					<kmss:authShow roles="ROLE_KMSCREDIT_DEFAULT">
						<div class="lservice_student_info_credit_rank" style="flex:1;">
							<ui:dataview style="margin-top: 30px;">
								<ui:source type="AjaxJson">
									{url : "/kms/credit/kms_credit_personal/kmsCreditPersonal.do?method=getUserTopCount"}
								</ui:source>
								<ui:render type="Template">
									var sum = 0;
									if(data) {
									sum = data.num;
									}
									{$
									<span class="lservice_student_info_credit_rank_num">{%sum%}</span>
									$}
								</ui:render>
							</ui:dataview>
							<div class="lservice_student_info_credit_rank_word"><img style="height: 20px;vertical-align: -3px;margin-right: 7px;" src="${LUI_ContextPath}/kms/lservice/index/common/style/img/pic_3.png">${lfn:message('kms-lservice:kmsLservice.student.rank.year') }</div>
						</div>
						<div class="lservice_student_info_study_time" style="flex:1.2">
							<div style="margin: 30px 0 0 50px;">
								<ui:dataview>
									<ui:source type="AjaxJson">
										{url : "/kms/credit/kms_credit_sum_personal/kmsCreditSumPersonal.do?method=getUserSum"}
									</ui:source>
									<ui:render type="Template">
										var sum = "0";
										var newStr = "";
										var count = 0;
										if(data && data.fdCreditSum >= 0) {
										sum = data.fdCreditSum;
										}
										if(sum.indexOf(".")==-1){
										for(var i=sum.length-1;i>=0;i--){
										if(count % 3 == 0 && count != 0){
										newStr = sum.charAt(i) + "," + newStr;
										}else{
										newStr = sum.charAt(i) + newStr;
										}
										count++;
										}
										sum = newStr + ".0";
										}else{
										for(var i = sum.indexOf(".")-1;i>=0;i--){
										if(count % 3 == 0 && count != 0){
										newStr = sum.charAt(i) + "," + newStr;
										}else{
										newStr = sum.charAt(i) + newStr;
										}
										count++;
										}
										sum = newStr + (sum + "0").substr((sum + "0").indexOf("."),3);
										}
										{$
										<em class="lservice_student_info_study_time_word">
											<img style="height: 20px;vertical-align: -3px;margin-right: 7px;" src="${LUI_ContextPath}/kms/lservice/index/common/style/img/pic_4.png">
												${lfn:message('kms-lservice:kmsLservice.student.credit.year') }
										</em>
										<a onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath}/kms/credit/student/" target="_blank">
											<span class="lservice_student_info_study_time_num">{%sum%}</span>
										</a>
										<em class="lservice_student_info_study_time_word">${lfn:message('kms-lservice:kmsLservice.student.point') }</em>
										$}
									</ui:render>
								</ui:dataview>

								<kmss:ifModuleExist path="/kms/loperation">
									<kmss:authShow roles="ROLE_KMSLOPERATION_DEFAULT">
										<ui:dataview>
											<ui:source type="AjaxJson" cfg-timeout="3000">
												{
												url : "/kms/loperation/kms_loperation_stu_total/kmsLoperationStuTotal.do?method=getUserLearnTime"}
											</ui:source>
											<ui:render type="Template">
												var dnum = 0
												if(data && data.time >= 0) {
												dnum = data.time;
												}
												{$
												<em class="lservice_student_info_study_time_word"><img style="height: 20px;vertical-align: -3px;margin-right: 7px;" src="${LUI_ContextPath}/kms/lservice/index/common/style/img/pic_5.png">${lfn:message('kms-lservice:kmsLservice.student.time.year') } </em>
												<a onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath}/kms/loperation/student/" target="_blank">
													<span class="lservice_student_info_study_time_num">{%dnum%}</span>
												</a>
												<em class="lservice_student_info_study_time_word">${lfn:message('kms-lservice:kmsLservice.student.min') }</em>
												$}
											</ui:render>
										</ui:dataview>
									</kmss:authShow>
								</kmss:ifModuleExist>
							</div>
						</div>
					</kmss:authShow>
				</kmss:ifModuleExist>
				<c:set var="richShow" value="false"/>
				<c:set var="kmsIntegrlShow" value="false"/>
				<c:set var="kmsDiplomaShow" value="false"/>
				<kmss:ifModuleExist path="/kms/integral">
					<c:set var="richShow" value="true"/>
					<kmss:authShow roles="ROLE_KMSINTEGRAL_DEFAULT">
						<c:set var="kmsIntegrlShow" value="true"/>
					</kmss:authShow>
				</kmss:ifModuleExist>
				<kmss:ifModuleExist path="/kms/diploma">
					<c:set var="richShow" value="true"/>
					<kmss:authShow roles="ROLE_KMSDIPLOMA_DEFAULT">
						<c:set var="kmsDiplomaShow" value="true"/>
					</kmss:authShow>
				</kmss:ifModuleExist>
				<c:if test="${(richShow && kmsIntegrlShow) || (richShow && kmsDiplomaShow)}">
					<div class="lservice_student_info_rich" style="flex:1.2">
						<div style="margin: 30px 0 0 50px;">
							<c:if test="${kmsIntegrlShow}">
								<ui:dataview>
									<ui:source type="AjaxJson">
										{url : "/kms/integral/kms_integral_person_total/kmsIntegralPersonTotal.do?method=getUserTotalRiches"}
									</ui:source>
									<ui:render type="Template">
										var sum = "0";
										var newStr = "";
										var count = 0;
										if(data && data.num >= 0) {
										sum = data.num;
										}
										if(sum.indexOf(".")==-1){
										for(var i=sum.length-1;i>=0;i--){
										if(count % 3 == 0 && count != 0){
										newStr = sum.charAt(i) + "," + newStr;
										}else{
										newStr = sum.charAt(i) + newStr;
										}
										count++;
										}
										sum = newStr + "";
										}else{
										for(var i = sum.indexOf(".")-1;i>=0;i--){
										if(count % 3 == 0 && count != 0){
										newStr = sum.charAt(i) + "," + newStr;
										}else{
										newStr = sum.charAt(i) + newStr;
										}
										count++;
										}
										sum = newStr + (sum + "").substr((sum + "").indexOf("."),3);
										}
										{$
										<em class="lservice_student_info_study_time_word"><img style="height: 20px;vertical-align: -3px;margin-right: 7px;" src="${LUI_ContextPath}/kms/lservice/index/common/style/img/pic_4.png">${lfn:message('kms-lservice:kmsLservice.student.riches') } </em>
										<a onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath}/kms/integral/#cri.q=time:fdTotal;score:fdTotalScore;personIds:<%=UserUtil.getUser().getFdId() %>" target="_blank">
											<span class="lservice_student_info_study_time_num">{%sum%}</span>
										</a>
										<em class="lservice_student_info_study_time_word">${lfn:message('kms-lservice:kmsLservice.student.point') }</em>
										$}
									</ui:render>
								</ui:dataview>
							</c:if>
							<c:if test="${kmsDiplomaShow}">
								<ui:dataview>
									<ui:source type="AjaxJson">
										{url : "/kms/diploma/kms_diploma_ui/kmsDiplomaPerson.do?method=getUserDiplomaNum"}
									</ui:source>
									<ui:render type="Template">
										var dnum = 0
										if(data && data.num >= 0) {
										dnum = data.num;
										}
										{$
										<em class="lservice_student_info_study_time_word"><img style="height: 20px;vertical-align: -3px;margin-right: 7px;" src="${LUI_ContextPath}/kms/lservice/index/common/style/img/pic_5.png">${lfn:message('kms-lservice:kmsLservice.student.diploma') }&nbsp&nbsp&nbsp&nbsp&nbsp</em>
										<a onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath}/kms/diploma/" target="_blank">
											<span class="lservice_student_info_study_time_num">{%dnum%}</span>
										</a>
										$}
									</ui:render>
								</ui:dataview>
							</c:if>
						</div>
					</div>
				</c:if>
			</div>
			<div class="lservice_student_mission">
				<div class="lservice_student_mission_title"><img style="width: 20px;margin-right: 6px;vertical-align: bottom;" src="${LUI_ContextPath}/kms/lservice/index/common/style/img/pic_1.png">${lfn:message('kms-lservice:kmsLservice.student.mission.center') }</div>
		    		<c:import url="/kms/lservice/index/student/missionList.jsp">
			 		</c:import>
			 	<kmss:ifModuleExist path="/kms/learn">
			 	<div class="lservice_student_mission_title" style="margin-top: 30px;"><img style="width: 20px;margin-right: 9px;vertical-align: bottom;" src="${LUI_ContextPath}/kms/lservice/index/common/style/img/pic_2.png">${lfn:message('kms-lservice:kmsLservice.student.my.schedule') }</div>
		    		<kmss:auth requestURL="/kms/learn/kms_learn_acti_personal/kmsLearnActiPersonal.do?method=getLearnCalendar">
						<c:import url="/kms/lservice/index/student/calendar/kms_lservice_calendar.jsp">
						</c:import>
					</kmss:auth>
			 	<div class="lservice_student_mission_title" style="margin-top: 50px;"><img style="width: 25px;margin-right: 6px;vertical-align: bottom;" src="${LUI_ContextPath}/kms/lservice/index/common/style/img/pic_6.png">${lfn:message('kms-lservice:kmsLservice.student.recommended.courses') }<a class="lservice_more" onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath}/kms/learn/#j_path=%2Fall" target="_blank">${lfn:message('kms-lservice:kmsLservice.student.recommended.more') }</a></div>
			 		<ui:accordionpanel layout="sys.ui.accordionpanel.simpletitle" toggle="none"> 
							 	<%--<%@ include file="/kms/learn/lservice/kmsLearnMain_recommend.jsp"%>--%>
						<c:import url="/kms/learn/lservice/kmsLearnMain_recommend.jsp" />
					</ui:accordionpanel>
				</kmss:ifModuleExist>
			</div>
		</div>
	</template:replace>
	
</template:include>

