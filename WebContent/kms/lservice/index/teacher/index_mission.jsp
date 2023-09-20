<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<style>
.pageContainer{
	font-family: PingFangSC-Medium;
	font-size: 20px;
	color: #262626;
}
.pageContainer span{
	cursor: pointer;
	margin-right: 50px;
	position: relative;
}
.pageContainer .selected{
    border-bottom: 3px solid #3F6CEF;
    padding-bottom: 3px;
}
.pageContainer span em{
	font-style: normal;
}
.pageMission.unselected{
	display: none;
}
.pageMission{
	margin-top: 26px;
}
.pageMission.selected{
	display: block;
}
.countNum{
    position: absolute;
    font-family: DINAlternate-Bold;
    font-size: 14px;
    color: #FF3333;
    min-width: 30px;
    top: -1px;
    text-align: left;
    right: -32px;
}
</style>
	<ui:dataview style="display: flex;width: 100%;text-align: center;">
		<ui:source type="AjaxJson">
			{url : "/kms/lservice/Lservice.do?method=getCountNum"}
		</ui:source>
		<ui:render type="Template">
		{$
			<div class="pageContainer">
				<kmss:ifModuleExist path="/kms/train">
					<kmss:auth requestURL="/kms/train">
					<span value="train" onclick="window.selectPage('train')" class="selected"><em>${lfn:message('kms-lservice:kmsLservice.teacher.lecture.course') }</em><em class="countNum">{%data.train%}</em></span>
					</kmss:auth>
				</kmss:ifModuleExist>
				<kmss:ifModuleExist path="/kms/exam">
					<kmss:auth requestURL="/kms/exam">
					<span value="exam" onclick="window.selectPage('exam')"><em>${lfn:message('kms-lservice:kmsLservice.teacher.marking.papers') }</em><em class="countNum">{%data.exam%}</em></span>
					</kmss:auth>
				</kmss:ifModuleExist>
				<kmss:ifModuleExist path="/kms/homework">
					<kmss:auth requestURL="/kms/homework">
					<span value="homework" onclick="window.selectPage('homework')"><em>${lfn:message('kms-lservice:kmsLservice.teacher.marking.homework') }</em><em class="countNum">{%data.homework%}</em></span>
					</kmss:auth>
				</kmss:ifModuleExist>
				<kmss:ifModuleExist path="/kms/lmap">
					<kmss:auth requestURL="/kms/lmap">
					<span value="lmap" onclick="window.selectPage('lmap')"><em>${lfn:message('kms-lservice:kmsLservice.teacher.learning.map.assessment') }</em><em class="countNum">{%data.lmap%}</em></span>
					</kmss:auth>
				</kmss:ifModuleExist>
				<kmss:ifModuleExist path="/kms/tutor">
					<kmss:auth requestURL="/kms/tutor">
					<span value="tutor" onclick="window.selectPage('tutor')"><em>${lfn:message('kms-lservice:kmsLservice.teacher.teaching.task') }</em><em class="countNum">{%data.tutor%}</em></span>
					</kmss:auth>
				</kmss:ifModuleExist>
			</div>
		$}
		</ui:render>
		<ui:event event="load" args="vt">
			<%--默认展示第一页--%>
			var fristPage=$(".pageContainer span")[0];
			if(fristPage){
			    $(fristPage).click();
			}
		</ui:event>
</ui:dataview>
<kmss:ifModuleExist path="/kms/train">
	<kmss:auth requestURL="/kms/train">
	<div id="train" class="pageMission selected" >
		<c:import url="/kms/train/teacher/lservice/index_all_class.jsp"  charEncoding="utf-8"/>
	</div>
	</kmss:auth>
</kmss:ifModuleExist>
<kmss:ifModuleExist path="/kms/exam">
	<kmss:auth requestURL="/kms/exam">
	<div id="exam" class="pageMission unselected" >
		<c:import url="/kms/exam/teacher/lservice/index_check_acti.jsp" charEncoding="UTF-8"/>
	</div>
	</kmss:auth>
</kmss:ifModuleExist>
<kmss:ifModuleExist path="/kms/homework">
	<kmss:auth requestURL="/kms/homework">
	<div id="homework" class="pageMission unselected" >
		<c:import url="/kms/homework/teacher/lservice/lservice_teacher_homework.jsp" charEncoding="UTF-8"/>
	</div>
	</kmss:auth>
</kmss:ifModuleExist>
<kmss:ifModuleExist path="/kms/lmap">
	<kmss:auth requestURL="/kms/lmap">
	<div id="lmap" class="pageMission unselected" >
		<c:import url="/kms/lmap/main/teacher/lservice/index_check.jsp" charEncoding="utf-8"/>
	</div>
	</kmss:auth>
</kmss:ifModuleExist>
<kmss:ifModuleExist path="/kms/tutor">
	<kmss:auth requestURL="/kms/tutor">
	<div id="tutor" class="pageMission unselected" >
		<c:import url="/kms/tutor/main/teacher/lservice/index_myCoach.jsp" charEncoding="UTF-8"/>
	</div>
	</kmss:auth>
</kmss:ifModuleExist>
<script>
window.selectPage = function(value) {
	var domNodeArr = $('.pageContainer>span');
	domNodeArr.each(function(index, item){
		var domNodeArrValue = item.getAttribute("value")
		if(value == domNodeArrValue)
			item.classList.add("selected");
		else 
			item.classList.remove("selected")
	})
	var pageMsgContainer = $('.pageMission');
	pageMsgContainer.each(function(index, item){
		item.classList.add("selected")
		if(item.id == value) {
			item.classList.add("selected")
			item.classList.remove("unselected")
		} else {
			item.classList.add("unselected")
			item.classList.remove("selected")
		}
	});
}
</script>