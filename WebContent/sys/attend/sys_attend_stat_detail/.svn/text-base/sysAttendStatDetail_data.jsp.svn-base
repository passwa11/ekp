<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysAttendStatDetail" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		
		<list:data-column property="fdSignTime" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdSignTime') }">
		</list:data-column>
		<list:data-column col="docStatus" title="${ lfn:message('sys-attend:sysAttendStatDetail.docStatus') }">
			<sunbor:enumsShow
				value="${sysAttendStatDetail.docStatus}"
				enumsType="common_status" />
		</list:data-column>
		<list:data-column col="fdOutside" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdOutside') }">
			<sunbor:enumsShow
				value="${sysAttendStatDetail.fdOutside}"
				enumsType="common_yesno" />
		</list:data-column>
		<list:data-column property="fdState" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdState') }">
		</list:data-column>
		<list:data-column property="fdSignTime2" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdSignTime2') }">
		</list:data-column>
		<list:data-column property="docStatus2" title="${ lfn:message('sys-attend:sysAttendStatDetail.docStatus2') }">
		</list:data-column>
		<list:data-column col="fdOutside2" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdOutside2') }">
			<sunbor:enumsShow
				value="${sysAttendStatDetail.fdOutside2}"
				enumsType="common_yesno" />
		</list:data-column>
		<list:data-column property="fdState2" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdState2') }">
		</list:data-column>
		<list:data-column  col="fdAttendResult" title="考勤结果">
		<c:if test="${sysAttendStatDetail.fdAttendResult==1 }">
		异常
		</c:if>
		<c:if test="${sysAttendStatDetail.fdAttendResult!=1 }">
		正常
		</c:if>
		</list:data-column>
		<list:data-column property="fdSignTime3" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdSignTime3') }">
		</list:data-column>
		<list:data-column property="docStatus3" title="${ lfn:message('sys-attend:sysAttendStatDetail.docStatus3') }">
		</list:data-column>
		<list:data-column col="fdOutside3" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdOutside3') }">
			<sunbor:enumsShow
				value="${sysAttendStatDetail.fdOutside3}"
				enumsType="common_yesno" />
		</list:data-column>
		<list:data-column property="fdState3" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdState3') }">
		</list:data-column>
		<list:data-column property="fdSignTime4" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdSignTime4') }">
		</list:data-column>
		<list:data-column property="docStatus4" title="${ lfn:message('sys-attend:sysAttendStatDetail.docStatus4') }">
		</list:data-column>
		<list:data-column col="fdOutside4" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdOutside4') }">
			<sunbor:enumsShow
				value="${sysAttendStatDetail.fdOutside4}"
				enumsType="common_yesno" />
		</list:data-column>
		<list:data-column property="fdState4" title="${ lfn:message('sys-attend:sysAttendStatDetail.fdState4') }">
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging page="${queryPage}" />
</list:data>