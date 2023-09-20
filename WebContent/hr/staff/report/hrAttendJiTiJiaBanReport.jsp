<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/ui/jsp/jshead.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<link href="${KMSS_Parameter_ResPath}style/common/list/list.css" rel="stylesheet" type="text/css"/>
<script>
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    Com_IncludeFile("form.js");
</script>
<%@page
	import="com.landray.kmss.hr.staff.report.HrAttendJiTiJiaBanReport"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<style type='text/css'>
body {
	background-color: #fff;
	height:700px;
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
<script language="JavaScript">
    function makeSrc(method) {
        let src = '${LUI_ContextPath}/sys/attend/sys_attend_main/sysAttendMain.do?method=' + method;
        let begin = $("[name='y']").val();
        if (begin) {
            src = Com_SetUrlParameter(src, "y", begin);
        }
        let end = $("[name='m']").val();
        if (end) {
            src = Com_SetUrlParameter(src, "m", end);
        }
        return src;
    }

    function exportResult() {
        let url = makeSrc('exportHrAttendJiTiJiaBanReport');
      //aalert(url)
        window.open(url);
    }
</script>
<h3 style="text-align: center">
	集体加班
</h3>
<%
	Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
	ca.setTime(new Date()); // 设置时间为当前时间
	String y = "";

	if (request.getParameter("y") == null) {
		y = ca.get(Calendar.YEAR) + "";
	} else {
		y = request.getParameter("y");
	}

	String m = "";
	if (request.getParameter("m") == null) {
		m = (ca.get(Calendar.MONTH) + 1) + "";
	} else {
		m = request.getParameter("m");
	}
%>
<form action="hrAttendJiTiJiaBanReport.jsp" method="post"
	style="display: block; margin: auto 20px; line-height: 30px;">
	<select name="y" style="margin-right: 20px 100px;">
		<!--  <option value="" <%=y.equals("") ? "selected=selected" : ""%>>请选择</option>-->
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
	</select><span>月</span> <input type="submit" id="button_1" value="查询" style="margin-right: 20px 100px;">
		<input type="submit" id="button_2" value="导出"
		onclick="exportResult()">
</form>

<table class='hovertable' style="text-align: center; margin: auto;">
	<tr>
		<th nowrap>序号</th>
		<th nowrap>人员编号</th>	
		<th nowrap>姓名</th>	
		<th nowrap>加班名称</th>	
		<th nowrap>平日加班</th>	
		<th nowrap>周末加班</th>	
		<th nowrap>法定加班</th>	
		<th nowrap>是否转加班费</th>	
	</tr>
	<%
	HrAttendJiTiJiaBanReport service = new HrAttendJiTiJiaBanReport();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = service.monthReport(y, m);
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
	%>
	<tr onmouseover="this.style.backgroundColor='#ffff66';"
		onmouseout="this.style.backgroundColor='#d4e3e5';">
		<td>
			<!--序号--><%=1+i%></td>
		<td>
			<!--人员编号--><%=map.get("fd_dept_name")%></td>
		<td>
			<!--姓名--><%=map.get("fd_begin_month_count")%></td>
		<td>
			<!--平日加班--><%=map.get("fd_end_month_count")%></td>
		<td>
			<!--周末加班--><%=map.get("fd_new_month_count")%></td>
		<td>
			<!--法定加班--><%=map.get("fd_leave_month_count")%></td>
		<td>
			<!--是否转加班费--><%=map.get("fd_in_month_count")%></td>
		<%
			}
		%>
	</tr>
	<%
		}
	%>

</table>
