<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<%@page import="com.landray.kmss.hr.staff.report.PersonLeaveMonthReport"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<style type='text/css'>
body {
	background-color: #fff;
	height:600px;
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
<form action="hrLeaveMonthReport.jsp" method="post"
	style="display: block; margin: auto 20px;line-height:30px;">
	<select name="y" style="margin-right: 20px 100px;">
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
	</select><span>月</span> <input type="submit" value="查询">
        <input class="bt" type=button value="<bean:message key="button.export"/>" onclick="exportResult();">
</form>
<table class='hovertable' style="text-align: center; margin: auto;">
	<tr>
		<th rowspan="2">类别</th>
		<th colspan="2">人数</th>
		<th rowspan="2">差异</th>
		<th colspan="2">离职率</th>
		<th rowspan="2">年至今累计离职率</th>
		<th rowspan="2">年度目标值（离职率）</th>
		<th rowspan="2">截止本月离职率目标</th>
		<th rowspan="2">离职目标达成率</th>
	</tr>
	<tr>
		<th nowrap>本月</th>
		<th nowrap>上月</th>
		<th nowrap>本月</th>
		<th nowrap>上月</th>
	</tr>
	<%
		PersonLeaveMonthReport service = new PersonLeaveMonthReport();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = service.leaveMonthReport(y,m);
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
	%>
	<tr onmouseover="this.style.backgroundColor='#ffff66';"
		onmouseout="this.style.backgroundColor='#d4e3e5';">
		<td>
			<!--类别 OPSM人员--><%=map.get("fd_type")%></td>
		<td>
			<!--人数本月 本月在职人数--><%=map.get("benyue_31_work")%></td>
		<td>
			<!--人数上月 5月31日在职人数--><%=map.get("shangyue_31_work")%>
		</td>
		<td>
			<!--差异 本月-上月人数--><%=map.get("chayi_size")%></td>
		<td>
			<!--离职率本月 离职率=离职人数/（离职人数+期末数/2）×100%。--><%=map.get("benyue_leave_lv")%>%
		</td>
		<td>
			<!--离职率上月 5月31日离职率--><%=map.get("shangyue_leave_lv")%>%</td>
		<td>
			<!--年至今累计离职率 年至今离职率=年度累计离职人数/(年度在岗人数+年度离职人数/2)*100%-->
			<%=map.get("leiji_leave_lv")%>%</td>
		<td>
			<!--年度目标值（离职率）公司目标值--><%=map.get("mubiao_year")%>%
		</td>
		<td>
			<!--截止本月离职率目标 年度目标值/12*8--><%=map.get("mubiao_lv")%>%</td>
		<td>
			<!--离职目标达成率 截止本月离职率目标/年至今累计离职率--><%=map.get("mubiao_leave_lv")%>%</td>
		<%
			}
		%>
	</tr>
	<%
		}
	%>

</table>

<script language="JavaScript">
    function makeSrc(method) {
        let src = '${LUI_ContextPath}/hr/staff/hr_staff_move_record/hrStaffMoveRecord.do?method=' + method;
        let y = $("[name='y']").val();
        let m = $("[name='m']").val();
        if(y != ''){
        	debugger;
            src = Com_SetUrlParameter(src, "y", y);
            src = Com_SetUrlParameter(src, "m", m);
        }
        return src;
    }

    function exportResult() {
        let url = makeSrc('exportLeaveMonthChange');
        window.open(url);
    }

    function search() {
        let src = makeSrc('getMoveMonthData');
        $('#searchIframe').attr("src", src);
    }
</script>