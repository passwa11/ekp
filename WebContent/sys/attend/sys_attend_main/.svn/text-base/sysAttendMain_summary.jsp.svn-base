<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page
	import="com.landray.kmss.sys.attend.report.AttendMonthReport"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<style type='text/css'>
body {
	background-color: #fff;
	height:90%;
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
white-space: nowrap;
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
.line-rt{ width:0; height: 0; border: 40px #000 solid; 
        border-color: #c3dde0 transparent transparent;  
        border-width:74px 0 0 120px;
        position: absolute;
        right:0; top: 0;
    }
    .txt-rt{ 
        position: absolute; right: 5px; top: 5px; z-index: 10; text-align: center;
    }
    .txt-lb{ 
        position: absolute; left: 3px; bottom: 4px; z-index: 10; width: 60px; text-align: center;
    }
    .line-lb{
        width:0; height: 0; border:  #c3dde0 solid; 
        border-color:  transparent  transparent  #c3dde0 transparent;  
        border-width: 0 120px 74px 0;
        position: absolute;
        left:0; bottom: 0;
    }
    .tdBg{ position: relative; width: 200px; height: 86px; display: inline-block;  solid; margin:0;}
</style>
<%
	Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
	ca.setTime(new Date()); // 设置时间为当前时间
	String y = "";

	if (request.getParameter("y") == null) {
		y = "";
	} else {
		y = request.getParameter("y");
	}

	String m = "";
	if (request.getParameter("m") == null) {
		m = (ca.get(Calendar.MONTH) + 1) + "";
	} else {
		m = request.getParameter("m");
	}

	AttendMonthReport service = new AttendMonthReport();
		List<List<Map<String, Object>>> list = new ArrayList();
		list = service.monthReport(y, m);
%>
<form action="sysAttendMain_summary.jsp" method="post"
	style="display: block; margin: auto 20px; line-height: 70px;">
	<select name="y" style="margin-right: 20px 100px;">
		<option value="" <%=y.equals("") ? "selected=selected" : ""%>>请选择</option>
		<option value="2022" <%=y.equals("2022") ? "selected=selected" : ""%>>2022</option>
		<option value="2023" <%=y.equals("2023") ? "selected=selected" : ""%>>2023</option>
		<option value="2024" <%=y.equals("2024") ? "selected=selected" : ""%>>2024</option>
		<option value="2025" <%=y.equals("2025") ? "selected=selected" : ""%>>2025</option>
		<option value="2026" <%=y.equals("2026") ? "selected=selected" : ""%>>2026</option>
		<option value="2027" <%=y.equals("2027") ? "selected=selected" : ""%>>2027</option>
		<option value="2028" <%=y.equals("2028") ? "selected=selected" : ""%>>2028</option>
		<option value="2029" <%=y.equals("2029") ? "selected=selected" : ""%>>2029</option>
		<option value="2030" <%=y.equals("2030") ? "selected=selected" : ""%>>2030</option>
		<option value="2031" <%=y.equals("2031") ? "selected=selected" : ""%>>2031</option>
		<option value="2032" <%=y.equals("2032") ? "selected=selected" : ""%>>2032</option>
		<option value="2033" <%=y.equals("2033") ? "selected=selected" : ""%>>2033</option>
	</select> <span>年</span> <select name="m" style="margin-right: 20px 100px;">
		<option value="1" <%=m.equals("1") ? "selected=selected" : ""%>>1</option>
		<option value="2" <%=m.equals("2") ? "selected=selected" : ""%>>2</option>
		<option value="3" <%=m.equals("3") ? "selected=selected" : ""%>>3</option>
		<option value="4" <%=m.equals("4") ? "selected=selected" : ""%>>4</option>
		<option value="5" <%=m.equals("5") ? "selected=selected" : ""%>>5</option>
		<option value="6" <%=m.equals("6") ? "selected=selected" : ""%>>6</option>
		<option value="7" <%=m.equals("7") ? "selected=selected" : ""%>>7</option>
		<option value="8" <%=m.equals("8") ? "selected=selected" : ""%>>8</option>
		<option value="9" <%=m.equals("9") ? "selected=selected" : ""%>>9</option>
		<option value="10" <%=m.equals("10") ? "selected=selected" : ""%>>10</option>
		<option value="11" <%=m.equals("11") ? "selected=selected" : ""%>>11</option>
		<option value="12" <%=m.equals("12") ? "selected=selected" : ""%>>12</option>
	</select><span>月</span> <input type="submit" id="button_1" value="查询"
		onclick="hie()">
        <input class="bt" type=button value="<bean:message key="button.export"/>" onclick="exportResult();">
</form>
<script type="text/javascript" src="${LUI_ContextPath}/resource/js/common.js?s_cache=${ LUI_Cache }"></script>
<script>
	function hie() {
		document.getElementById("div_1").style.display = "block";
		document.getElementById("button_1").style.display = "none";
	}
</script>
<div id="div_1"
	style="background-color: #afeeee; font-size: 20; font-weight: bold; text-align: center; display: none;">
	报表数据生成中，请稍后.......</div>
 <div class="table-fixed-wrap" >
    <div class="table-fixed-box">
<table class='hovertable fixed-table-main'>
<thead>
	<tr>
		<th nowrap rowspan="2">序号</th>
		<th nowrap rowspan="2" id="aaa" style="margin:0; padding:0;">
		<div>
		 <div class="tdBg">
        <div class="txt-rt">部门</div>
        <div class="txt-lb">项目</div>
        <div class="line-rt"></div>
        <div class="line-lb"></div>
    </div>
    </div>
		</th>	
		
		<th nowrap colspan="3">合计</th>
		<%
		if (list.size() > 0) {
			for (int i = 1; i < list.get(0).size(); i++) {
				Map<String, Object> map = list.get(0).get(i);
	%>
		<th nowrap colspan="3"><%=map.get("fd_dept_name")%></th>	
		<%
			}
		}
		%>
	</tr>
	<tr>
	
		<th nowrap >本月实际值</th>
		<th nowrap >上月实际值</th>
		<th nowrap >环比</th>
	<%
	if (list.size() > 0) {
		for (int i = 1; i < list.get(0).size(); i++) {
			
		%>
		<th nowrap >本月实际值</th>
		<th nowrap >上月实际值</th>
		<th nowrap >环比</th>
	<%}}%>
	</tr>
        </thead>
        <tbody >	<tr onmouseover="this.style.backgroundColor='#ffff66';"
		onmouseout="this.style.backgroundColor='#d4e3e5';">
   
		<td>
			<!--序号--><%=1%></td>
		<td id="bbb">
			<!--部门名称--><%=m+"月加班时间/小时"%></td>
		     <%
	if (list.size() > 0) {
		for (int i = 0; i < list.get(0).size(); i++) {
			Map<String, Object> map = list.get(0).get(i);
		%>
		<td>
			<!--人数小计--><%=map.get("hourTxt")%></td>
		<td>
			<!--人数小计--><%=map.get("shangyueHourTxt")%></td>
		<td>
			<!--人数小计--><%=map.get("huanbi")%></td>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_leave_month_count")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_in_month_count")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_out_month_count")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_flow_month_rate")%>%</td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_leave_month_rate")%>%</td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_leave_count")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_leave_rate")%>%</td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_begin_month_count1")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_end_month_count1")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_new_month_count1")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_leave_month_count1")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_in_month_count1")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_out_month_count1")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_flow_month_rate1")%>%</td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_leave_month_rate1")%>%</td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_leave_count1")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_leave_rate1")%>%</td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_begin_month_count2")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_end_month_count2")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_new_month_count2")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_leave_month_count2")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_in_month_count2")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_out_month_count2")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_flow_month_rate2")%>%</td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_leave_month_rate2")%>%</td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_leave_count2")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_leave_rate2")%>%</td> --%>

<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_begin_month_count3")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_end_month_count3")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_new_month_count3")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_leave_month_count3")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_in_month_count3")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_out_month_count3")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_flow_month_rate3")%>%</td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_leave_month_rate3")%>%</td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_leave_count3")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_leave_rate3")%>%</td> --%>
		
	<%}}else{%>
	<td></td>
			<td></td>
			<td></td>
			<%} %>
	</tr>
	<tr onmouseover="this.style.backgroundColor='#ffff66';"
		onmouseout="this.style.backgroundColor='#d4e3e5';">
   
		<td>
			<!--序号--><%=2%></td>
		<td id="bbb">
			<!--部门名称--><%=m+"人均加班时间/小时"%></td>
			
		     <%
	if (list.size() > 0) {
		for (int i = 0; i < list.get(1).size(); i++) {
			Map<String, Object> map = list.get(1).get(i);
		%>
		<td>
			<!--人数小计--><%=map.get("hourTxt")%></td>
		<td>
			<!--人数小计--><%=map.get("shangyueHourTxt")%></td>
		<td>
			<!--人数小计--><%=map.get("huanbi")%></td>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_leave_month_count")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_in_month_count")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_out_month_count")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_flow_month_rate")%>%</td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_leave_month_rate")%>%</td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_leave_count")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_leave_rate")%>%</td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_begin_month_count1")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_end_month_count1")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_new_month_count1")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_leave_month_count1")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_in_month_count1")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_out_month_count1")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_flow_month_rate1")%>%</td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_leave_month_rate1")%>%</td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_leave_count1")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_leave_rate1")%>%</td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_begin_month_count2")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_end_month_count2")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_new_month_count2")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_leave_month_count2")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_in_month_count2")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_out_month_count2")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_flow_month_rate2")%>%</td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_leave_month_rate2")%>%</td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_leave_count2")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_leave_rate2")%>%</td> --%>

<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_begin_month_count3")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_end_month_count3")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_new_month_count3")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_leave_month_count3")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_in_month_count3")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_out_month_count3")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_flow_month_rate3")%>%</td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_leave_month_rate3")%>%</td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_leave_count3")%></td> --%>
<!-- 		<td> -->
<%-- 			<!--人数小计--><%=map.get("fd_leave_rate3")%>%</td> --%>
		
	<%}}else{%>
	<td></td>
			<td></td>
			<td></td>
			<%} %>
	</tr>
        </tbody>
</table>
    </div>
  </div>
<script language="JavaScript">
    function makeSrc(method) {
        let src = '${LUI_ContextPath}/hr/staff/hr_staff_move_record/hrStaffMoveRecord.do?method=' + method;
        let y = $("[name='y']").val();
        let m = $("[name='m']").val();
        if(y != ''){
            src = Com_SetUrlParameter(src, "y", y);
            src = Com_SetUrlParameter(src, "m", m);
        }
        return src;
    }

    function exportResult() {
        let url = makeSrc('exportLeaveMonth1Change');
        window.open(url);
    }

    function search() {
        let src = makeSrc('getMoveMonthData');
        $('#searchIframe').attr("src", src);
    }
</script>
  <script src="src/lib/jq.js"></script>
  <script src="tablefix.min.js"></script>
  <script>
    /**
     * 引入tablefix.min.js
     * 初始化
     */
     var tdWidth = $("#bbb").outerWidth();
    $("#aaa").css("width",tdWidth);
    $('.table-fixed-wrap').fixedTable()
    
    function setIframeHeight(){
    	var main = $(window.parent.document).find("iframe");
    	console.log('main'+main);
    	main.height(750);
    }
    setIframeHeight();
  </script>

