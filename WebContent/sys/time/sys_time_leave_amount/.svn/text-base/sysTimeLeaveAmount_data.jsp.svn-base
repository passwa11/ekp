<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.landray.kmss.sys.time.model.SysTimeLeaveAmount,com.landray.kmss.sys.time.model.SysTimeLeaveAmountItem,java.util.List,java.util.ArrayList,java.util.Map,java.util.HashMap" %>
<%@ page import="com.landray.kmss.sys.time.service.ISysTimeLeaveAmountService,com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.sys.time.model.SysTimeLeaveRule,com.landray.kmss.util.ResourceUtil,com.landray.kmss.sys.time.util.SysTimeUtil,com.landray.kmss.sys.time.model.SysTimeLeaveConfig" %>
<%@ page import="com.landray.kmss.util.NumberUtil" %>
<list:data>
	<list:data-columns var="sysTimeLeaveAmount" list="${queryPage.list}" varIndex="status">
		<%
			ISysTimeLeaveAmountService sysTimeLeaveAmountService = (ISysTimeLeaveAmountService) SpringBeanUtil.getBean("sysTimeLeaveAmountService");
			List<SysTimeLeaveRule> leaveRuleList = sysTimeLeaveAmountService.getAllLeaveRule();
			
			Float convertTime = SysTimeUtil.getConvertTime();
			SysTimeLeaveAmount sysTimeLeaveAmount = (SysTimeLeaveAmount) pageContext.getAttribute("sysTimeLeaveAmount");
			List<SysTimeLeaveAmountItem> fdAmountItems = sysTimeLeaveAmount.getFdAmountItems();
			// 是否是最后一年,最后一年的数据可以删除和编辑，过期的假期不允许更新和删除
			boolean lastYear = false;
			if(sysTimeLeaveAmount.getFdPerson() != null){
				lastYear = sysTimeLeaveAmountService.isLastYear(sysTimeLeaveAmount.getFdYear(), sysTimeLeaveAmount.getFdPerson().getFdId());
			}
			List<Map<String, Object>> leaveDataList = new ArrayList<Map<String, Object>>();
			//剩余总天数
			Float totalRest = 0f;
			Float deltaHour = 0f;
			for(SysTimeLeaveRule leaveRule : leaveRuleList) {
				boolean isFind = false;
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("leaveName", leaveRule.getFdName());
				data.put("serialNo", leaveRule.getFdSerialNo());
				for(SysTimeLeaveAmountItem amountItem : fdAmountItems) {
					if(leaveRule.getFdSerialNo().equals(amountItem.getFdLeaveType())){
						Float restDay = amountItem.getFdRestDay() != null && Boolean.TRUE.equals(amountItem.getFdIsAvail())
								? amountItem.getFdRestDay() : 0f;
						Float lastRestDay = amountItem.getFdLastRestDay() != null && Boolean.TRUE.equals(amountItem.getFdIsLastAvail())
								? amountItem.getFdLastRestDay() : 0f;
						Float rest = restDay + lastRestDay;
						totalRest += rest;
						data.put("restDay", NumberUtil.roundDecimal(rest,3));
						isFind = true;
					}
				}
				if(!isFind){
					data.put("restDay", 0);
				}
				leaveDataList.add(data);
			}
			pageContext.setAttribute("totalRest",NumberUtil.roundDecimal(totalRest,3));
			pageContext.setAttribute("leaveDataList", leaveDataList);
			pageContext.setAttribute("lastYear", lastYear);
		%>
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index" >
		 	${status+1}
		</list:data-column>
		<list:data-column col="fdPerson.fdParentsName" escape="false" style="" title="${ lfn:message('sys-time:sysTimeLeaveAmount.fdDept') }">
			<c:out value="${sysTimeLeaveAmount.fdPerson.fdParentsName}"></c:out>
		</list:data-column>
		<list:data-column col="fdPerson.fdName" escape="false" style="" title="${ lfn:message('sys-time:sysTimeLeaveAmount.fdPersonName') }">
			<c:out value="${sysTimeLeaveAmount.fdPerson.fdName}"></c:out>
		</list:data-column>
		<list:data-column col="fdPerson.fdLoginName" escape="false" style="" title="${ lfn:message('sys-time:sysTimeLeaveAmount.loginName') }">
			<c:out value="${sysTimeLeaveAmount.fdPerson.fdLoginName}"></c:out>
		</list:data-column>
		<list:data-column property="fdYear" escape="false" style="" title="${ lfn:message('sys-time:sysTimeLeaveAmount.fdYear') }">
		</list:data-column>
		
		<c:forEach items="${leaveDataList }" var="leaveData">
			<list:data-column col="${leaveData.serialNo}" escape="false" style="" title="${ lfn:message('sys-time:sysTimeLeaveAmount.rest') }${fn:escapeXml(leaveData.leaveName)}">
				${leaveData.restDay }
			</list:data-column>
		</c:forEach>
		
		<c:if test="${not empty totalRest }">
			<list:data-column col="totalRest" escape="false" style="" title="${ lfn:message('sys-time:sysTimeLeaveAmount.totalRestDay') }">
				${totalRest }
			</list:data-column>
		</c:if>


		<c:if test="${not empty sysTimeLeaveAmount.fdAmountItems }">
		<list:data-column col="operation" escape="false" title="${ lfn:message('list.operation') }" style="min-width: 100px;">
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<c:if test="${ not empty lastYear && lastYear }">
					<kmss:auth requestURL="/sys/time/sys_time_leave_amount/sysTimeLeaveAmount.do?method=edit">
						<a class="btn_txt" href="javascript:editDoc('${sysTimeLeaveAmount.fdId}');">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/time/sys_time_leave_amount/sysTimeLeaveAmount.do?method=delete">
						<a class="btn_txt" href="javascript:deleteAll('${sysTimeLeaveAmount.fdId}');">${lfn:message('button.delete')}</a>
					</kmss:auth>
					</c:if>
				</div>
			</div>
		</list:data-column>
		</c:if>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>