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
	<table class='hovertable' style="text-align: center; margin: auto;">
		<tr style="background: 0 !important;">
			<th width="5%"><bean:message key="page.serial" /></th>
			<th width="5%">入职时间</th>
			<th width="5%">人员编号</th>
			<th width="10%">姓名</th>
			<th width="5%">人员类别</th>
			<th width="5%">性别</th>
			<th width="10%">岗位名称</th>
			<th width="10%">所属公司</th>
			<th width="5%">职级</th>
			<th width="10%">一级部门</th>
			<th width="10%">二级部门</th>
			<th width="10%">三级部门</th>
		</tr>
		<c:forEach items="${queryPage}" var="obj" varStatus="vstatus">
			<tr>
				<td>${rowIndex1}<c:set var="rowIndex1" value="${rowIndex1+1}"/></td>
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
			</tr>
		</c:forEach>
	</table>
</c:if>
<%@ include file="/resource/jsp/list_down.jsp"%>