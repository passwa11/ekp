<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%><%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<%@page import="com.landray.kmss.hr.staff.report.PersonInfoReport"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<script>
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    Com_IncludeFile("form.js");
</script>
<style type='text/css'>
body {
	background-color: #fff;
	height:600px;
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
<form action="hrEntryMonthReport.jsp" method="post"
	style="display: block; margin: auto 20px; line-height: 30px;">
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
<div class="table-fixed-wrap" column=1>
    <div class="table-fixed-box">
<table class='hovertable fixed-table-main'>
<!-- <table class='hovertable' style="text-align: center; margin: auto;"> -->
	<thead>
	<tr>
		<th nowrap>序号</th>
		<th nowrap>一级部门</th>
		<th nowrap>二级部门</th>
		<th nowrap>三级部门</th>
		<th nowrap>人员类别</th>
		<th nowrap>所属公司</th>
		<th nowrap>工号</th>
		<th nowrap>姓名</th>
		<th nowrap>岗位名称</th>
		<th nowrap>职务</th>
		<th nowrap>职类</th>
		<th nowrap>职级</th>
		<th nowrap>入职日期</th>
		<th nowrap>拟转正日期</th>
		<th nowrap>转正日期</th>
		<th nowrap>本企业司龄</th>
		<th nowrap>最高学历</th>
		<th nowrap>合同开始日期</th>
		<th nowrap>合同结束日期</th>
		<th nowrap>合同期限</th>
		<th nowrap>身份证号</th>
		<th nowrap>出生日期</th>
		<th nowrap>年龄</th>
		<th nowrap>性别</th>
		<th nowrap>婚姻状况</th>
	</tr>
	</thead>
	<%
		PersonInfoReport service = new PersonInfoReport();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = service.entryMonthReport(y, m);
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
	%>
	<tr onmouseover="this.style.backgroundColor='#ffff66';"
		onmouseout="this.style.backgroundColor='#d4e3e5';">
		<td>
			<!--序号--><%=1 + i%></td>
		<td>
			<!--一级部门--><%=map.get("fd_first_level_department")%></td>
		<td>
			<!--二级部门--><%=map.get("fd_second_level_department")%></td>
		<td>
			<!--三级部门--><%=map.get("fd_third_level_department")%>
		</td>
		<td>
			<!--人员类别--><%=map.get("fd_staff_type")%></td>
		<td>
			<!--所属公司--><%=map.get("fd_affiliated_company")%>
		</td>
		<td>
			<!--人员编号--><%=map.get("fd_staff_no")%></td>
		<td>
			<!--姓名--><%=map.get("fd_name")%></td>
		<td>
			<!--岗位名称--><%=map.get("fd_post_name")%>
		</td>
		<td>
			<!--职务--><%=map.get("fd_staffinglevel_name")%></td>
		<td>
			<!--职类--><%=map.get("fd_category")%></td>
		<td>
			<!--职级--><%=map.get("fd_rank_name")%></td>
		<td>
			<!--入职日期--><%=service.dateToString(map.get("fd_entry_time"))%></td>
		<td>
			<!--拟转正日期--><%=service.dateToString(map.get("fd_proposed_employment_confirmation_date"))%></td>
		<td>
			<!--转正日期--><%=service.dateToString(map.get("fd_positive_time"))%></td>

		<td>
			<!--本企业司龄--><%=map.get("fd_work_in_this_company")%></td>
		<td>
			<!--最高学历--><%=map.get("fd_highest_education")%></td>
		<td>
			<!--合同开始日期--><%=service.dateToString(map.get("cont_fd_begin_date"))%></td>
		<td>
			<!--合同结束日期--><%=service.dateToString(map.get("cont_fd_end_date"))%></td>
		<td>
			<!--合同期限--><%=Integer.valueOf(map.get("fd_contract_year")+"")==0 ?  "&nbsp;&nbsp;&nbsp;&nbsp;": map.get("fd_contract_year") + "年"%>
			 <%=Integer.valueOf(map.get("fd_contract_month")+"")==0 ?  "": map.get("fd_contract_month") + "月"%>
		</td>
		<td>
			<!--身份证号--><%=map.get("fd_id_card")%></td>
		<td>
			<!--出生日期--><%=service.dateToString(map.get("fd_date_of_birth"))%></td>
		<td>
			<!--年龄--><%=map.get("fd_age")%></td>
		<td>
			<!--性别--><%=map.get("fd_sex").equals("F") ? '男' : '女'%></td>
		<td>
			<!--婚姻状况--><%=map.get("fd_marital_status")%></td>
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
        let url = makeSrc('exportEntryMonthChange');
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


