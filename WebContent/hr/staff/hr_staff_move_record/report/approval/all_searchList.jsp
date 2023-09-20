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
			<th width="5%" rowspan="2"><bean:message key="page.serial" /></th>
			<th width="5%" rowspan="2">一级部门</th>
			<th width="5%" rowspan="2">二级部门</th>
			<th width="8%" rowspan="2">三级部门</th>
			<th width="3%" rowspan="2">工号</th>
			<th width="5%" rowspan="2">审批人</th>
			<th width="5%" rowspan="2">岗位</th>
			<th width="10%" colspan="2">差旅流程</th>
			<th width="10%" colspan="2">假期流程</th>
			<th width="10%" colspan="2">签卡流程</th>
			<th width="10%" colspan="2">外出流程</th>
			<th width="10%" colspan="2">加班流程</th>
			<th width="10%" colspan="2">合计</th>
		</tr>
		<tr>
			<th>审批次数</th>
			<th>平均用时</th>
			<th>审批次数</th>
			<th>平均用时</th>
			<th>审批次数</th>
			<th>平均用时</th>
			<th>审批次数</th>
			<th>平均用时</th>
			<th>审批次数</th>
			<th>平均用时</th>
			<th>审批次数</th>
			<th>平均用时</th>
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
				<td>${obj[16] }</td>
				<td>${obj[17] }</td>
			</tr>
		</c:forEach>
	</table>
    </div>
  </div>
</c:if>
<%@ include file="/resource/jsp/list_down.jsp"%>
<script src="src/lib/jq.js"></script>
  <script src="tablefix.min.js"></script>
  <script>
    /**
     * 引入tablefix.min.js
     * 初始化
     */
//      var tdWidth1 = $("#bbb").outerWidth();
//     $("#aaa").css("width",tdWidth1);
//      var tdWidth2 = $("#ddd").outerWidth();
//     $("#ccc").css("width",tdWidth2);
//      var tdWidth3 = $("#fff").outerWidth();
//     $("#eee").css("width",tdWidth3);
    $('.table-fixed-wrap').fixedTable()
    
    function setIframeHeight(){
    	var main = $(window.parent.document).find("iframe");
    	console.log('main'+main);
    	main.height(750);
    }
    setIframeHeight();
  </script>

