<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<%@page
	import="com.landray.kmss.hr.staff.report.PersonLeaveLevelMonthReport"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<style type='text/css'>
body {
	background-color: #fff;
	height: 600px;
}
.table-fixed-wrap {
      height: 650PX;
    }
      .fixed-table-main {
      width: 100%
    }
table.hove
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
%>
<form action="hrLeaveLevelMonthReport.jsp" method="post"
	style="display: block; margin: auto 20px; line-height: 30px;">
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
<script>
	function hie() {
		document.getElementById("div_1").style.display = "block";
		document.getElementById("button_1").style.display = "none";
	}
</script>
<div id="div_1"
	style="background-color: #afeeee; font-size: 20; font-weight: bold; text-align: center; display: none;">
	报表数据生成中，请稍后.......</div>
<div class="table-fixed-wrap" column=2>
    <div class="table-fixed-box">
<table class='hovertable fixed-table-main'>
<!-- <table class='hovertable' style="text-align: center; margin: auto;"> -->
<thead>
	<tr>
		<th nowrap rowspan="3">序号</th>
		<th nowrap rowspan="3" id="aaa">部门名称</th>
		<th nowrap colspan="16">当月值</th>
		<th nowrap colspan="8">累计值</th>
		<th nowrap rowspan="3">备注</th>
	</tr>
	<tr>
		<th nowrap colspan="4">M类</th>
		<th nowrap colspan="4">S类</th>
		<th nowrap colspan="4">P类</th>
		<th nowrap colspan="4">O类</th>
		<th nowrap colspan="2">M类</th>
		<th nowrap colspan="2">S类</th>
		<th nowrap colspan="2">P类</th>
		<th nowrap colspan="2">O类</th>
	</tr>
	<tr>
		<th nowrap>月初<br>总数
		</th>
		<th nowrap>月末<br>总数
		</th>
		<th nowrap>流失<br>人数
		</th>
		<th nowrap>流失率<br>%
		</th>
		<th nowrap>月初<br>总数
		</th>
		<th nowrap>月末<br>总数
		</th>
		<th nowrap>流失<br>人数
		</th>
		<th nowrap>流失率%
		</th>
		<th nowrap>月初<br>总数
		</th>
		<th nowrap>月末<br>总数
		</th>
		<th nowrap>流失<br>人数
		</th>
		<th nowrap>流失率%
		</th>
		<th nowrap>月初<br>总数
		</th>
		<th nowrap>月末<br>总数
		</th>
		<th nowrap>流失<br>人数
		</th>
		<th nowrap>流失率%
		</th>
		<th nowrap>累计<br>离职人数
		</th>
		<th nowrap>累计<br>流失率%
		</th>
		<th nowrap>累计<br>离职人数
		</th>
		<th nowrap>累计<br>流失率%
		</th>
		<th nowrap>累计<br>离职人数
		</th>
		<th nowrap>累计<br>流失率%
		</th>
		<th nowrap>累计<br>离职人数
		</th>
		<th nowrap>累计<br>流失率%
		</th>
	</tr>
	</thead>
	<%
		PersonLeaveLevelMonthReport service = new PersonLeaveLevelMonthReport();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = service.leaveLevelMonthReport(y, m);
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
	%>
	<tr onmouseover="this.style.backgroundColor='#ffff66';"
		onmouseout="this.style.backgroundColor='#d4e3e5';">
		<td><%=1+i%></td>
		<td nowrap id="bbb"><%=map.get("fd_dept_name")%></td>
		<td>
			<!--系统人事资料6月30日在职M类人员的人数--><%=map.get("fd_shangyue_work_m")%></td>
		<td>
			<!--系统人事资料6月30日离职M类人员的人数--><%=map.get("fd_benyue_work_m")%></td>
		<td>
			<!--系统人事资料6月30日离职M类人员的人数--><%=map.get("fd_benyue_leave_m")%></td>
		<td>
			<!--员工流失率=员工流失人数/（期初员工人数+本期增加员工人数）*100%--><%=map.get("fd_benyue_liu_lv_m")%>%
		</td>

		<td>
			<!--系统人事资料6月30日在职S类人员的人数--><%=map.get("fd_shangyue_work_s")%></td>
		<td>
			<!--系统人事资料6月30日离职S类人员的人数--><%=map.get("fd_benyue_work_s")%></td>
		<td>
			<!--系统人事资料6月30日离职S类人员的人数--><%=map.get("fd_benyue_leave_s")%></td>
		<td>
			<!--员工流失率=员工流失人数/（期初员工人数+本期增加员工人数）*100%--><%=map.get("fd_benyue_liu_lv_s")%>%</td>
		<td>
			<!--系统人事资料5月31日在职P类人员的人数--><%=map.get("fd_shangyue_work_p")%></td>
		<td>
			<!--系统人事资料6月30日在职P类人员的人数--><%=map.get("fd_benyue_work_p")%></td>
		<td>
			<!--系统人事资料6月30日离职P类人员的人数--><%=map.get("fd_benyue_leave_p")%></td>
		<td>
			<!--员工流失率=员工流失人数/（期初员工人数+本期增加员工人数）*100%--><%=map.get("fd_benyue_liu_lv_p")%>%
		</td>

		<td>
			<!--系统人事资料5月31日在职O类人员的人数--><%=map.get("fd_shangyue_work_o")%></td>
		<td>
			<!--系统人事资料6月30日在职O类人员的人数--><%=map.get("fd_benyue_work_o")%></td>
		<td>
			<!--系统人事资料6月30日离职O类人员的人数--><%=map.get("fd_benyue_leave_o")%></td>
		<td>
			<!--员工流失率=员工流失人数/（期初员工人数+本期增加员工人数）*100%--><%=map.get("fd_benyue_liu_lv_o")%>%
		</td>



		<td>
			<!--系统人事资料1月1-6月30日M类累计离职人数--><%=map.get("fd_all_year_leiji_m")%></td>
		<td>
			<!--累计离职人数/（公司人数+月末总数）/2）*100%--><%=map.get("fd_all_year_leiji_lv_m")%>%
		</td>

		<td>
			<!--系统人事资料1月1-6月30日S类累计离职人数--><%=map.get("fd_all_year_leiji_s")%></td>
		<td>
			<!--累计离职人数/（公司人数+月末总数）/2）*100%--><%=map.get("fd_all_year_leiji_lv_s")%>%
		</td>

		<td>
			<!--系统人事资料1月1-6月30日P类累计离职人数--><%=map.get("fd_all_year_leiji_p")%></td>
		<td>
			<!--累计离职人数/（公司人数+月末总数）/2）*100%--><%=map.get("fd_all_year_leiji_lv_p")%>%
		</td>

		<td>
			<!--系统人事资料1月1-6月30日O类累计离职人数--><%=map.get("fd_all_year_leiji_o")%></td>
		<td>
			<!--员工流失率=员工流失人数/（期初员工人数+本期增加员工人数）*100%--><%=map.get("fd_all_year_leiji_lv_o")%>%
		</td>

		<td></td>
		<%
			}
		%>
	</tr>
	<%
		}
	%>

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
        let url = makeSrc('exportLeaveLevelMonthChange');
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
     var tdWidth1 = $("#bbb").outerWidth();
    $("#aaa").css("width",tdWidth1);
     var tdWidth2 = $("#ddd").outerWidth();
    $("#ccc").css("width",tdWidth2);
     var tdWidth3 = $("#fff").outerWidth();
    $("#eee").css("width",tdWidth3);
    $('.table-fixed-wrap').fixedTable()
    
    function setIframeHeight(){
    	var main = $(window.parent.document).find("iframe");
    	console.log('main'+main);
    	main.height(750);
    }
    setIframeHeight();
  </script>



