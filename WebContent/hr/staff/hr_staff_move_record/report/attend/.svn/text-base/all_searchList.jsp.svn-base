<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp" %>

<style type='text/css'>
body {
	background-color: #fff;
	height:700px;
}
.table-fixed-wrap {
      height: 650PX;
    }
      .fixed-table-main {
      width: 100%
    }
table.hovertable {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	color: #333333;
	border-width: 1px;
	border-color: #999999;
	border-collapse: collapse;
}

table.hovertable th {
	background-color: #c3dde0;
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #333333;
}

table.hovertable tr {
	background-color: #d4e3e5;
}

table.hovertable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #333333;
}

input {
	outline-style: none;
	border: 1px solid #ccc;
	border-radius: 3px;
}

span {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
}
</style>
<%-- <c:if test="${fn:length(queryPage) == 0}"> --%>
<%-- 	<%@ include file="/resource/jsp/list_norecord.jsp"%> --%>
<%-- </c:if> --%>
<%-- <c:if test="${fn:length(queryPage)>0}">	 --%>
<%-- 	<%@ include file="/resource/jsp/list_pagenav_top.jsp"%> --%>
	<c:set var="rowIndex1" value="1" />
	 <div class="table-fixed-wrap" column=1>
    <div class="table-fixed-box">
<table class='hovertable fixed-table-main'>
<!-- 	<table class='hovertable' style="text-align: center; margin: auto; width: 97%">  -->
<thead>
		<tr>
			<th ><bean:message key="page.serial" /></th>
			<th >一级部门</th>
			<th >二级部门</th>
			<th >三级部门</th>
			<th >姓名</th>
			<th >工号</th>
			<th >缺卡天数</th>
			<th >无刷卡次数</th>
			<th >事假天数</th>
			<th >病假天数</th>
		</tr>
		</thead>
		<tbody>
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
			</tr>
		</c:forEach>
		</tbody>
	</table>
    </div>
  </div>
<%-- </c:if> --%>
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
    	console.log('main'+main.height);
    	main.height(750);
    }
    setIframeHeight();
  </script>

