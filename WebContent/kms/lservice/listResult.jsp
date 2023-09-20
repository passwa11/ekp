<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<list:data>
	<list:data-columns var="pStudyCenter" list="${queryPage.list }">
		<list:data-column property="fdId">	
		</list:data-column>
		<list:data-column col="href" escape="false">
			<c:if test="${pStudyCenter.fdModel == 'learn'}">/kms/learn/kms_learn_activity/kmsLearnActivity.do?method=todo&fdId=${pStudyCenter.fdModelId}</c:if>
			<c:if test="${pStudyCenter.fdModel == 'exam'}">/kms/exam/kms_exam_unified_activity/kmsExamUnifiedActivity.do?method=entryExam&&fdId=${pStudyCenter.fdModelId}</c:if>
	      	<c:if test="${pStudyCenter.fdModel == 'lmap'}">/kms/lmap/kms_lmap_main/kmsLmapMain.do?method=view&fdId=${pStudyCenter.fdModelId}&forward=startLearn&type=student</c:if>
		</list:data-column>
		<list:data-column col="docSubject" title="标题">
			<c:out value="${pStudyCenter.docSubject}"></c:out>
		</list:data-column>
		<list:data-column col="fdModel" title="类型">
			<c:if test="${pStudyCenter.fdModel == 'lmap'}">${lfn:message('kms-lservice:kmsLservice.student.lmap') }</c:if>
			<c:if test="${pStudyCenter.fdModel == 'exam'}">${lfn:message('kms-lservice:kmsLservice.student.exam') }</c:if>
			<c:if test="${pStudyCenter.fdModel == 'learn'}">${lfn:message('kms-lservice:kmsLservice.student.learn.task') }</c:if>
		</list:data-column>
		<list:data-column col="score" title="分数">
			<c:choose>
				<c:when test="${pStudyCenter.fdModel == 'exam'}">
					<c:out value="${lfn:message('kms-lservice:kmsLservice.student.score') }:${pStudyCenter.score}"></c:out>
				</c:when>
				<c:otherwise>
					<c:out value="${lfn:message('kms-lservice:kmsLservice.student.credit') }:${pStudyCenter.score}"></c:out>
				</c:otherwise>
			</c:choose>
		</list:data-column>	
		<list:data-column col="process" title="进度">
			<c:out value="${pStudyCenter.process}"></c:out>
		</list:data-column>
		<list:data-column col="fdName" title="名称">
			<c:if test="${pStudyCenter.fdModel == 'lmap'}"><c:out value="${lfn:message('kms-lservice:kmsLservice.student.appoint') }:${pStudyCenter.fdName}"></c:out></c:if>
			<c:if test="${pStudyCenter.fdModel == 'exam'}"><c:out value="${lfn:message('kms-lservice:kmsLservice.student.arranger') }:${pStudyCenter.fdName}"></c:out></c:if>
			<c:if test="${pStudyCenter.fdModel == 'learn'}"><c:out value="${lfn:message('kms-lservice:kmsLservice.student.appoint') }:${pStudyCenter.fdName}"></c:out></c:if>
		</list:data-column>	
		<list:data-column col="time" title="时间" >
			<c:choose>
				<c:when test="${pStudyCenter.fdModel == 'exam'}">
					${lfn:message('kms-lservice:kmsLservice.student.exam.time') }: <kmss:showDate value="${pStudyCenter.btime}" type="datetime"/>~<kmss:showDate value="${pStudyCenter.etime}" type="datetime"/>
				</c:when>
				<c:when test="${pStudyCenter.fdModel == 'learn'}">
					${lfn:message('kms-lservice:kmsLservice.student.assigned.time') }: <kmss:showDate value="${pStudyCenter.btime}" type="date"/>
				</c:when>
				<c:otherwise>
					<c:if test="${pStudyCenter.etime==null}">
						${lfn:message('kms-lservice:kmsLservice.student.end.time') }: ${lfn:message('kms-lservice:kmsLservice.student.end.time.unlimited') }
					</c:if>
					<c:if test="${pStudyCenter.etime!=null}">
						${lfn:message('kms-lservice:kmsLservice.student.end.time') }: <kmss:showDate value="${pStudyCenter.etime}" type="date"/>
					</c:if>
				</c:otherwise>
			</c:choose>			
		</list:data-column>
		<list:data-column col="button" title="按钮名称">
			<c:choose>
				<c:when test="${pStudyCenter.fdModel == 'exam'}">
					<c:out value="${lfn:message('kms-lservice:kmsLservice.student.exam.begin') }"></c:out>
				</c:when>
				<c:otherwise>
					<c:out value="${lfn:message('kms-lservice:kmsLservice.student.learn.begin') }"></c:out>
				</c:otherwise>
			</c:choose>
		</list:data-column>			
	</list:data-columns>

	<list:data-paging page="${queryPage }">
	</list:data-paging>
</list:data>
