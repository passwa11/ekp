<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<%@page
	import="com.landray.kmss.hr.staff.report.PersonStructureMonthReport"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
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
<form action="hrPersonalStructureMonthReport.jsp" method="post"
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

<!-- <table class='hovertable' style="text-align: center; margin: auto;"> -->
 <div class="table-fixed-wrap" column=2>
    <div class="table-fixed-box">
<table class='hovertable fixed-table-main'>
<thead>
	<tr>
		<th nowrap rowspan="3">序号</th>
		<th nowrap rowspan="3" id="aaa">部门名称</th>
		<th nowrap rowspan="3">人数<br>小计
		</th>
		<th nowrap colspan="3">婚姻状况</th>
		<th nowrap colspan="2">性别</th>
		<th nowrap colspan="4">学历</th>
		<th nowrap colspan="12">年龄结构</th>
		<th nowrap colspan="6">司龄</th>
		<th nowrap colspan="4">职务类别（不含实习生临时工）</th>
	</tr>
	<tr>
		<th nowrap rowspan="2">未婚</th>
		<th nowrap rowspan="2">已婚</th>
		<th nowrap rowspan="2">已育</th>
		<th nowrap rowspan="2">男性</th>
		<th nowrap rowspan="2">女性</th>
		<th nowrap rowspan="2">大专<br>以下</th>
		<th nowrap rowspan="2">大专</th>
		<th nowrap rowspan="2">本科</th>
		<th nowrap rowspan="2">硕士</th>
<!-- 		<th nowrap rowspan="2">博士</th> -->
		<th nowrap colspan="2">18-20<br>岁</th>
		<th nowrap colspan="2">21-25<br>岁</th>
		<th nowrap colspan="2">26-35<br>岁</th>
		<th nowrap colspan="2">36-45<br>岁</th>
		<th nowrap colspan="2">46-50<br>岁</th>
		<th nowrap colspan="2">50岁<br>以上</th>
		<th nowrap rowspan="2">1年<br>以内</th>
		<th nowrap rowspan="2">1-3年<br>以下</th>
		<th nowrap rowspan="2">3-5年<br>以下</th>
		<th nowrap rowspan="2">5-8年<br>以下</th>
		<th nowrap rowspan="2">8-10年<br>以下</th>
		<th nowrap rowspan="2">10年<br>以上</th>
		<th nowrap rowspan="2">M类</th>
		<th nowrap rowspan="2">P类</th>
		<th nowrap rowspan="2">O类</th>
		<th nowrap rowspan="2">S类</th>
	</tr>
	<tr>
		<th nowrap>男</th>
		<th nowrap>女</th>
		<th nowrap>男</th>
		<th nowrap>女</th>
		<th nowrap>男</th>
		<th nowrap>女</th>
		<th nowrap>男</th>
		<th nowrap>女</th>
		<th nowrap>男</th>
		<th nowrap>女</th>
		<th nowrap>男</th>
		<th nowrap>女</th>
	</tr>
	</thead>
	<%
		PersonStructureMonthReport service = new PersonStructureMonthReport();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = service.structureMonthReport(y, m);
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
	%>
	<tr onmouseover="this.style.backgroundColor='#ffff66';"
		onmouseout="this.style.backgroundColor='#d4e3e5';">
		<td>
			<!--序号--><%=1+i%></td>
		<td nowrap id="bbb">
			<!--部门名称--><%=map.get("fd_dept_name")%></td>
		<td>
			<!--人数小计--><%=map.get("fd_dept_person_count")%></td>
		<td>
			<!--婚姻状况未婚人数--><%=map.get("fd_marital_status_weihun")%>
		</td>
		<td>
			<!--婚姻状况已婚人数--><%=map.get("fd_marital_status_yihun")%></td>
		<td>
			<!--婚姻状况已育人数--><%=map.get("fd_marital_status_yiyu")%>
		</td>
		<td>
			<!--性别男--><%=map.get("fd_sex_f")%></td>
		<td>
			<!--性别女--><%=map.get("fd_sex_m")%></td>
		<td>
			<!--学历大专以下--><%=map.get("fd_highest_education_dazhuanyixia")%>
		</td>
		<td>
			<!--学历大专--><%=map.get("fd_highest_education_dazhuan")%></td>
		<td>
			<!--学历本科--><%=map.get("fd_highest_education_benke")%></td>
		<td>
			<!--学历硕士--><%=map.get("fd_highest_education_shuoshi")%></td>
<!-- 		<td> -->
<%-- 			<!--学历博士--><%=map.get("fd_highest_education_boshi")%></td> --%>
		<td>
			<!--年龄结构18－20岁男--><%=map.get("fd_age_18_20_f")%></td>
		<td>
			<!--年龄结构18－20岁女--><%=map.get("fd_age_18_20_m")%></td>
		<td>
			<!--年龄结构21－25岁男--><%=map.get("fd_age_21_25_f")%></td>
		<td>
			<!--年龄结构21－25岁女--><%=map.get("fd_age_21_25_m")%></td>
		<td>
			<!--年龄结构26－35岁男--><%=map.get("fd_age_26_35_f")%></td>
		<td>
			<!--年龄结构26－35岁女--><%=map.get("fd_age_26_35_m")%></td>
		<td>
			<!--年龄结构36－45岁男--><%=map.get("fd_age_36_45_f")%></td>
		<td>
			<!--年龄结构36－45岁女--><%=map.get("fd_age_36_45_m")%></td>
		<td>
			<!--年龄结构46－50岁男--><%=map.get("fd_age_46_50_f")%></td>
		<td>
			<!--年龄结构46－50岁女--><%=map.get("fd_age_46_50_m")%></td>
		<td>
			<!--年龄结构50岁以上男--><%=map.get("fd_age_50_100_f")%></td>
		<td>
			<!--年龄结构50岁以上女--><%=map.get("fd_age_50_100_m")%></td>
		<td>
			<!--司龄1年以内--><%=map.get("fd_siling_0_1")%></td>
		<td>
			<!--司龄1年以内 1-3年以下--><%=map.get("fd_siling_1_3")%></td>
		<td>
			<!--司龄3-5年以下--><%=map.get("fd_siling_3_5")%></td>
		<td>
			<!--司龄5-8年以下--><%=map.get("fd_siling_5_8")%></td>
		<td>
			<!--司龄8-10年以下--><%=map.get("fd_siling_8_10")%></td>
		<td>
			<!--司龄10年以上--><%=map.get("fd_siling_10")%></td>
		<td>
			<!-- 职务类别（不含实习生临时工）M类--><%=map.get("fd_rank_m")%></td>
		<td>
			<!--职务类别（不含实习生临时工）P类 --><%=map.get("fd_rank_p")%></td>
		<td>
			<!--职务类别（不含实习生临时工）O类--><%=map.get("fd_rank_o")%></td>
		<td>
			<!-- 职务类别（不含实习生临时工）S类--><%=map.get("fd_rank_s")%></td>

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
        let url = makeSrc('exportPersonalStructureMonthReportChange');
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