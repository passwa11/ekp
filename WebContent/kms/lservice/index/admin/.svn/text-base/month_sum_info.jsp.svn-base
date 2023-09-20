<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<div style="display: flex;flex:1;margin-top: 40px;">
	<ui:dataview style="display: flex;width: 100%;text-align: center;">
		<ui:source type="AjaxJson">
			{url : "/kms/lservice/kmsLservicePortletAction.do?method=getPieInfo"}
		</ui:source>
		<ui:render type="Template">
			{$
			<kmss:ifModuleExist path="/kms/learn/">
				<div style="flex:1;">

					<a onclick="getLastMonth('learn');"  target="_blank">
						<span class="lservice_student_info_credit_rank_num">{%data.learnActiSum%}</span>
					</a>
					<div class="lservice_admin_sum_title">${lfn:message('kms-lservice:kmsLservice.admin.learn.mission') }</div>

				</div>
			</kmss:ifModuleExist>
			<kmss:ifModuleExist path="/kms/train/">
				<div  style="flex:1;">

					<a onclick="getLastMonth('train');"  target="_blank">
						<span class="lservice_student_info_credit_rank_num">{%data.trainPlanSum%}</span>
					</a>
					<div class="lservice_admin_sum_title">${lfn:message('kms-lservice:kmsLservice.admin.train.mission') }</div>

				</div>
			</kmss:ifModuleExist>
			<kmss:ifModuleExist path="/kms/exam/">
				<div  style="flex:1;">

					<a onclick="getLastMonth('exam');"  target="_blank">
						<span class="lservice_student_info_credit_rank_num">{%data.examActiSum%}</span>
					</a>
					<div class="lservice_admin_sum_title">${lfn:message('kms-lservice:kmsLservice.admin.exam.mission') }</div>

				</div>
			</kmss:ifModuleExist>
			<kmss:ifModuleExist path="/kms/lmap/">
				<div  style="flex:1;">

					<a onclick="getLastMonth('lmap');"  target="_blank">
						<span class="lservice_student_info_credit_rank_num">{%data.lmapMainSum%}</span>
					</a>
					<div class="lservice_admin_sum_title">${lfn:message('kms-lservice:kmsLservice.admin.lmap.mission') }</div>

				</div>
			</kmss:ifModuleExist>
			$}
		</ui:render>
	</ui:dataview>
</div>
<div class="lservice_admin_content">
	<ui:dataview style="display:flex;">
		<ui:source type="AjaxJson">
			{url : "/kms/lservice/kmsLservicePortletAction.do?method=getPieInfo"}
		</ui:source>
		<ui:render type="Template">
			{$
			<kmss:ifModuleExist path="/kms/learn/">
				<div style="flex:1;">

					<em class="lservice_admin_sum_word">${lfn:message('kms-lservice:kmsLservice.admin.learn.mission.add') }</em>
					<a onclick="getLastMonth('learnMain');"  target="_blank">
						<span class="lservice_admin_sum_num">{%data.courseMainSum%}</span>
					</a>

				</div>
			</kmss:ifModuleExist>
			<kmss:ifModuleExist path="/kms/learn/">
				<div style="flex:1;">

					<em class="lservice_admin_sum_word">${lfn:message('kms-lservice:kmsLservice.admin.learn.courseware.mission.add') }</em>
					<a onclick="getLastMonth('learnCourse');"  target="_blank">
						<span class="lservice_admin_sum_num">{%data.coursewareSum%}</span>
					</a>

				</div>
			</kmss:ifModuleExist>
			<kmss:ifModuleExist path="/kms/exam/">
				<div style="flex:1;">

					<em class="lservice_admin_sum_word">${lfn:message('kms-lservice:kmsLservice.admin.exam.topic.add') }</em>
					<a onclick="getLastMonth('examTopic');"  target="_blank">
						<span class="lservice_admin_sum_num">{%data.examTopicSum%}</span>
					</a>

				</div>
			</kmss:ifModuleExist>
			<kmss:ifModuleExist path="/kms/exam/">
				<div style="flex:1;">

					<em class="lservice_admin_sum_word">${lfn:message('kms-lservice:kmsLservice.admin.exam.tmpl.add') }</em>
					<a onclick="getLastMonth('examUnified');"  target="_blank">
						<span class="lservice_admin_sum_num">{%data.examTmplSum%}</span>
					</a>

				</div>
			</kmss:ifModuleExist>
			<kmss:ifModuleExist path="/kms/train/">
				<div style="flex:1;">

					<em class="lservice_admin_sum_word">${lfn:message('kms-lservice:kmsLservice.admin.train.add') }</em>
					<a onclick="getLastMonth('trainMain');"  target="_blank">
						<span class="lservice_admin_sum_num">{%data.trainMainSum%}</span>
					</a>

				</div>
			</kmss:ifModuleExist>
			$}
		</ui:render>
	</ui:dataview>	
</div>
<script>
function getLastMonth(type) {
	var modelType = type;
    var now = new Date();
    var year = now.getFullYear();
    var month = now.getMonth() + 1;//0-11表示1-12月
    var day = now.getDate();
    var dateObj = {};
    dateObj.now = year + '-' + month + '-' + day; 
    var nowMonthDay = new Date(year, month, 0).getDate();    //当前月的总天数
    if(month - 1 <= 0){ //如果是1月，年数往前推一年<br>　　　　 
        dateObj.last = (year - 1) + '-' + 12 + '-' + day;
    }else{
        var lastMonthDay = new Date(year, (parseInt(month) - 1), 0).getDate();  
        if(lastMonthDay < day){    // 1个月前所在月的总天数小于现在的天日期
            if(day < nowMonthDay){        //当前天日期小于当前月总天数
                dateObj.last = year + '-' + (month - 1) + '-' + (lastMonthDay - (nowMonthDay - day));
            }else{
                dateObj.last = year + '-' + (month - 1) + '-' + lastMonthDay;
            }
        }else{
            dateObj.last = year + '-' + (month - 1) + '-' + day;
        }
    }
    if(modelType=='learn'){
    	Com_OpenWindow("${LUI_ContextPath}/kms/learn/main/admin/index.jsp#j_path=%2Ftask&cri.learn_adm_task.q=docCreateTime%3A"+dateObj.last+"%3BdocCreateTime%3A"+dateObj.now)
    }
    if(modelType=='train'){
    	Com_OpenWindow("${LUI_ContextPath}/kms/train/admin/index.jsp#j_path=%2Fallplan&cri.kmsTrainPlanAll_admin_allPlan.q=docCreateTime%3A"+dateObj.last+"%3BdocCreateTime%3A"+dateObj.now)
    }
    if(modelType=='exam'){
    	Com_OpenWindow("${LUI_ContextPath}/kms/exam/admin/index.jsp#j_path=%2Fall&cri.kmsExamActi_all.q=docCreateTime%3A"+dateObj.last+"%3BdocCreateTime%3A"+dateObj.now)
    }
    if(modelType=='lmap'){
    	Com_OpenWindow("${LUI_ContextPath}/kms/lmap/main/admin/index.jsp#cri.kmsLmapAll_all.q=docCreateTime%3A"+dateObj.last+"%3BdocCreateTime%3A"+dateObj.now)
    }
    if(modelType=='learnMain'){
    	Com_OpenWindow("${LUI_ContextPath}/kms/learn/main/admin/index.jsp#j_path=%2Fall&cri.learn_adm_main.q=docPublishTime%3A"+dateObj.last+"%3BdocPublishTime%3A"+dateObj.now)
    }
    if(modelType=='learnCourse'){
    	Com_OpenWindow("${LUI_ContextPath}/kms/learn/courseware/admin/index.jsp#j_path=%2Fall&cri.kmsLearnCourseware_all.q=docCreateTime%3A"+dateObj.last+"%3BdocCreateTime%3A"+dateObj.now)
    }
    if(modelType=='examTopic'){
    	Com_OpenWindow("${LUI_ContextPath}/kms/exam/admin/index.jsp#j_path=%2Ftopic&cri.exam_adm_topic.q=fdIsAvailable%3A1%3BdocCreateTime%3A"+dateObj.last+"%3BdocCreateTime%3A"+dateObj.now)
    }
    if(modelType=='examUnified'){
    	Com_OpenWindow("${LUI_ContextPath}/kms/exam/admin/index.jsp#j_path=%2Ftemplate&cri.exam_adm_template.q=docCreateTime%3A"+dateObj.last+"%3BdocCreateTime%3A"+dateObj.now)
    }
    if(modelType=='trainMain'){
    	Com_OpenWindow("${LUI_ContextPath}/kms/train/admin/index.jsp#j_path=%2Fallmain&cri.kmsTrainMainAll_admin_null.q=docCreateTime%3A"+dateObj.last+"%3BdocCreateTime%3A"+dateObj.now)
    }
}
</script>