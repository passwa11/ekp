<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysPraiseInfoPersonal" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<list:data-column  col="fdPerson" title="${ lfn:message('sys-praise:sysPraiseInfo.calculate.person') }">
			<c:out value="${sysPraiseInfoPersonal.fdPerson.fdName }"></c:out>
		</list:data-column>
		<list:data-column  col="fdPraiseNum" escape="false" title="${ lfn:message('sys-praise:sysPraiseInfoDetailBase.fdPraiseNum') }">
		<a class="praiseItemBtn" onclick = "showItemDetail('${sysPraiseInfoPersonal.fdPerson.fdId}','${fdTimeType}','1','operator')">
			<c:choose>
				<c:when test="${fdTimeType eq 'week' }">
					<c:out value="${sysPraiseInfoPersonal.fdWeek.fdPraiseNum}"/>
				</c:when>
				<c:when test="${fdTimeType eq 'month' }">
					<c:out value="${sysPraiseInfoPersonal.fdMonth.fdPraiseNum}"/>
				</c:when>
				<c:when test="${fdTimeType eq 'year' }">
					<c:out value="${sysPraiseInfoPersonal.fdYear.fdPraiseNum}"/>
				</c:when>
				<c:otherwise>
					<c:out value="${sysPraiseInfoPersonal.fdTotal.fdPraiseNum}"/>
				</c:otherwise>
			</c:choose>
		</a>
		</list:data-column>
		<list:data-column  col="fdPraisedNum" escape="false" title="${ lfn:message('sys-praise:sysPraiseInfoDetailBase.fdPraisedNum') }">
			<a class="praiseItemBtn" onclick = "showItemDetail('${sysPraiseInfoPersonal.fdPerson.fdId}','${fdTimeType}','1','target')">
			<c:choose>
				<c:when test="${fdTimeType eq 'week' }">
					<c:out value="${sysPraiseInfoPersonal.fdWeek.fdPraisedNum}"/>
				</c:when>
				<c:when test="${fdTimeType eq 'month' }">
					<c:out value="${sysPraiseInfoPersonal.fdMonth.fdPraisedNum}"/>
				</c:when>
				<c:when test="${fdTimeType eq 'year' }">
					<c:out value="${sysPraiseInfoPersonal.fdYear.fdPraisedNum}"/>
				</c:when>
				<c:otherwise>
					<c:out value="${sysPraiseInfoPersonal.fdTotal.fdPraisedNum}"/>
				</c:otherwise>
			</c:choose>
			</a>
		</list:data-column>
		<list:data-column  col="fdOpposeNum" escape="false" title="${ lfn:message('sys-praise:sysPraiseInfoDetailBase.fdOpposeNum') }">
			<a class="praiseItemBtn" onclick = "showItemDetail('${sysPraiseInfoPersonal.fdPerson.fdId}','${fdTimeType}','3','operator')">
			<c:choose>
				<c:when test="${fdTimeType eq 'week' }">
					<c:out value="${sysPraiseInfoPersonal.fdWeek.fdOpposeNum}"/>
				</c:when>
				<c:when test="${fdTimeType eq 'month' }">
					<c:out value="${sysPraiseInfoPersonal.fdMonth.fdOpposeNum}"/>
				</c:when>
				<c:when test="${fdTimeType eq 'year' }">
					<c:out value="${sysPraiseInfoPersonal.fdYear.fdOpposeNum}"/>
				</c:when>
				<c:otherwise>
					<c:out value="${sysPraiseInfoPersonal.fdTotal.fdOpposeNum}"/>
				</c:otherwise>
			</c:choose>
			</a>
		</list:data-column>
		<list:data-column  col="fdOpposedNum" escape="false" title="${ lfn:message('sys-praise:sysPraiseInfoDetailBase.fdOpposedNum') }">
			<a class="praiseItemBtn" onclick = "showItemDetail('${sysPraiseInfoPersonal.fdPerson.fdId}','${fdTimeType}','3','target')">
			<c:choose>
				<c:when test="${fdTimeType eq 'week' }">
					<c:out value="${sysPraiseInfoPersonal.fdWeek.fdOpposedNum}"/>
				</c:when>
				<c:when test="${fdTimeType eq 'month' }">
					<c:out value="${sysPraiseInfoPersonal.fdMonth.fdOpposedNum}"/>
				</c:when>
				<c:when test="${fdTimeType eq 'year' }">
					<c:out value="${sysPraiseInfoPersonal.fdYear.fdOpposedNum}"/>
				</c:when>
				<c:otherwise>
					<c:out value="${sysPraiseInfoPersonal.fdTotal.fdOpposedNum}"/>
				</c:otherwise>
			</c:choose>
			</a>
		</list:data-column>
		<list:data-column  col="fdPayNum" escape="false" title="${ lfn:message('sys-praise:sysPraiseInfoDetailBase.fdPayNum') }">
			<a class="praiseItemBtn" onclick = "showItemDetail('${sysPraiseInfoPersonal.fdPerson.fdId}','${fdTimeType}','2','operator')">
			<c:choose>
				<c:when test="${fdTimeType eq 'week' }">
					<c:out value="${sysPraiseInfoPersonal.fdWeek.fdPayNum}"/>
				</c:when>
				<c:when test="${fdTimeType eq 'month' }">
					<c:out value="${sysPraiseInfoPersonal.fdMonth.fdPayNum}"/>
				</c:when>
				<c:when test="${fdTimeType eq 'year' }">
					<c:out value="${sysPraiseInfoPersonal.fdYear.fdPayNum}"/>
				</c:when>
				<c:otherwise>
					<c:out value="${sysPraiseInfoPersonal.fdTotal.fdPayNum}"/>
				</c:otherwise>
			</c:choose>
			</a>
		</list:data-column>
		<list:data-column  col="fdReceiveNum" escape="false" title="${ lfn:message('sys-praise:sysPraiseInfoDetailBase.fdReceiveNum') }">
			<a class="praiseItemBtn" onclick = "showItemDetail('${sysPraiseInfoPersonal.fdPerson.fdId}','${fdTimeType}','2','target')">
			<c:choose>
				<c:when test="${fdTimeType eq 'week' }">
					<c:out value="${sysPraiseInfoPersonal.fdWeek.fdReceiveNum}"/>
				</c:when>
				<c:when test="${fdTimeType eq 'month' }">
					<c:out value="${sysPraiseInfoPersonal.fdMonth.fdReceiveNum}"/>
				</c:when>
				<c:when test="${fdTimeType eq 'year' }">
					<c:out value="${sysPraiseInfoPersonal.fdYear.fdReceiveNum}"/>
				</c:when>
				<c:otherwise>
					<c:out value="${sysPraiseInfoPersonal.fdTotal.fdReceiveNum}"/>
				</c:otherwise>
			</c:choose>
			</a>
		</list:data-column>
		<list:data-column  col="fdRichPay" escape="false" title="${ lfn:message('sys-praise:sysPraiseInfoDetailBase.fdRichPay') }">
			<a class="praiseItemBtn" onclick = "showItemDetail('${sysPraiseInfoPersonal.fdPerson.fdId}','${fdTimeType}','2','operator')">
			<c:choose>
				<c:when test="${fdTimeType eq 'week' }">
					<c:out value="${sysPraiseInfoPersonal.fdWeek.fdRichPay}"/>
				</c:when>
				<c:when test="${fdTimeType eq 'month' }">
					<c:out value="${sysPraiseInfoPersonal.fdMonth.fdRichPay}"/>
				</c:when>
				<c:when test="${fdTimeType eq 'year' }">
					<c:out value="${sysPraiseInfoPersonal.fdYear.fdRichPay}"/>
				</c:when>
				<c:otherwise>
					<c:out value="${sysPraiseInfoPersonal.fdTotal.fdRichPay}"/>
				</c:otherwise>
			</c:choose>
			</a>
		</list:data-column>
		<list:data-column  col="fdRichGet" escape="false" title="${ lfn:message('sys-praise:sysPraiseInfoDetailBase.fdRichGet') }">
			<a class="praiseItemBtn" onclick = "showItemDetail('${sysPraiseInfoPersonal.fdPerson.fdId}','${fdTimeType}','2','target')">
			<c:choose>
				<c:when test="${fdTimeType eq 'week' }">
					<c:out value="${sysPraiseInfoPersonal.fdWeek.fdRichGet}"/>
				</c:when>
				<c:when test="${fdTimeType eq 'month' }">
					<c:out value="${sysPraiseInfoPersonal.fdMonth.fdRichGet}"/>
				</c:when>
				<c:when test="${fdTimeType eq 'year' }">
					<c:out value="${sysPraiseInfoPersonal.fdYear.fdRichGet}"/>
				</c:when>
				<c:otherwise>
					<c:out value="${sysPraiseInfoPersonal.fdTotal.fdRichGet}"/>
				</c:otherwise>
			</c:choose>
			</a>
		</list:data-column>
		<list:data-column  col="fdSumCount" escape="false" title="${ lfn:message('sys-praise:sysPraiseInfo.portlet.count') }">
			<c:choose>
				<c:when test="${fdTimeType eq 'week' }">
					<c:out value="${sysPraiseInfoPersonal.fdWeek.fdReceiveNum+sysPraiseInfoPersonal.fdWeek.fdPraisedNum}"/>
				</c:when>
				<c:when test="${fdTimeType eq 'month' }">
					<c:out value="${sysPraiseInfoPersonal.fdMonth.fdReceiveNum + sysPraiseInfoPersonal.fdMonth.fdPraisedNum}"/>
				</c:when>
				<c:when test="${fdTimeType eq 'year' }">
					<c:out value="${sysPraiseInfoPersonal.fdYear.fdReceiveNum + sysPraiseInfoPersonal.fdYear.fdPraisedNum}"/>
				</c:when>
				<c:otherwise>
					<c:out value="${sysPraiseInfoPersonal.fdTotal.fdReceiveNum + sysPraiseInfoPersonal.fdTotal.fdPraisedNum}"/>
				</c:otherwise>
			</c:choose>
		</list:data-column>
	</list:data-columns>
	<list:data-paging page="${queryPage}" />
</list:data>