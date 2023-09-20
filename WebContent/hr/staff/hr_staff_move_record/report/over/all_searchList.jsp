<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<script>
	Com_IncludeFile("table.css", Com_Parameter.ContextPath + "hr/staff/hr_staff_move_record/report/css/","css",true);
</script>
<c:if test="${fn:length(queryPage) == 0}">
	<%@ include file="/resource/jsp/list_norecord.jsp"%>
</c:if>
<c:if test="${fn:length(queryPage)>0}">	
	<%@ include file="/resource/jsp/list_pagenav_top.jsp"%>
	<c:set var="rowIndex1" value="1" />
	<div class="table-fixed-wrap" column=1>
    <div class="table-fixed-box">
	<table class='hovertable fixed-table-main'>
<!-- 	<table class='hovertable' style="text-align: center; margin: auto;"> -->
		<tr style="background: 0 !important;">
			<th width="30px"><bean:message key="page.serial" /></th>
			<th width="50px">工号</th>
			<th width="50px">姓名</th>
			<th width="80px">一级部门</th>
			<th width="80px">二级部门</th>
			<th width="80px">三级部门</th>

			<th width="50px">加班类型</th>
			<th width="100px">加班日期</th>
			<th width="100px">申请加班开始时间</th>
			<th width="100px">实际开始时间</th>
			<th width="100px">申请加班结束时间</th>

			<th width="100px">实际结束时间</th>
			<th width="50px">加班计划时长</th>
			<th width="50px">加班实际时长</th>
			<th width="120px">加班原因</th>
			<th width="50px">用餐时间(分钟)</th>
			<th width="50px">是否转加班费</th>
		</tr>
		<c:forEach items="${queryPage}" var="obj" varStatus="vstatus">
			<tr>
				<td>${rowIndex1}<c:set var="rowIndex1" value="${rowIndex1+1}"/></td>
				<td>${obj[0] }</td>
				<td>${obj[1] }</td>
				<td>${obj[2] }</td>
				<td>${obj[3] }</td>
				<td>${obj[4] }</td>
				<td>${obj[5] }</td>
				<td>${obj[6] }</td>
				<td>${obj[7] }</td>
				<td>${obj[8] }</td>
				<td>${obj[9] }</td>
				<td>${obj[10] }</td>
				<td>${obj[11] }</td>
                <td>${obj[12] }</td>
                <td>${obj[13] }</td>
                <td>${obj[14] }</td>
				<td>${obj[15] }</td>
			</tr>
		</c:forEach>
	</table>
    </div>
  </div>
</c:if>
<%@ include file="/resource/jsp/list_down.jsp"%>

