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
	<style>
	 .table-fixed-wrap {
      height: 650PX;
    }
      .fixed-table-main {
      width: 100%
    }
    table.hovertable th{
    white-space:nowrap;
    }
	</style>
 <div class="table-fixed-wrap">
    <div class="table-fixed-box">
<table class='hovertable fixed-table-main'>
<!-- 	<table class='hovertable fixed-table-main' style="text-align: center; margin: auto;"> -->
<thead>
		<tr>
			<th width="5%" rowspan="2"><bean:message key="page.serial" /></>
			<th width="5%" rowspan="2">工号</th>
			<th width="5%" rowspan="2">姓名</th>
			<th width="3%" rowspan="2">是否考察</th>
			<th width="5%"rowspan="2">见习开始日期</th>
			<th width="5%" rowspan="2">见习结束日期</th>
			<th width="3%" rowspan="2" >是否跨一级部门异动</th>
			<th width="3%" rowspan="2">异动类型</th>
			<th width="5%"rowspan="2">变动生效日期</th>
			<th width="30%" colspan="6">异动前</th>
			<th width="30%" colspan="6">异动后</th>
<!-- 			<th width="10%" rowspan="2">异动情形</th> -->
<!-- 			<th width="5%"rowspan="2">是否跨一级部门</th> -->
		</tr>
		<tr>
			<th>一级部门</th>
			<th>二级部门</th>
			<th>三级部门</th>
			<th>职级</th>
			<th>岗位</th>
			<th>直接上级</th>
			<th>一级部门</th>
			<th>二级部门</th>
			<th>三级部门</th>
			<th>职级</th>
			<th>岗位</th>
			<th>直接上级</th>
		</tr>
        </thead>
        <tbody >
		<c:forEach items="${queryPage}" var="obj" varStatus="vstatus">
			<tr>
				<td>${rowIndex1}<c:set var="rowIndex1" value="${rowIndex1+1}"/></td>
				<td>${obj[1] }</td>
				<td>${obj[2]}</td>
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
				<td>${obj[16] }</td>
				<td>${obj[17] }</td>
				<td>${obj[18] }</td>
				<td>${obj[19] }</td>
				<td>${obj[20] }</td>
			</tr>
		</c:forEach>
        </tbody>
	</table>
    </div>
  </div>
</c:if>
<%@ include file="/resource/jsp/list_down.jsp"%>
