<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page
	import="com.landray.kmss.hr.staff.actions.HrStaffMoveRecordAction"%>
<%@page	import="com.landray.kmss.hr.staff.service.IHrStaffMoveRecordService"%>
<%@page import="com.landray.kmss.hr.staff.report.PersonInfoReport"%>
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
</style>
<%
	Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
	ca.setTime(new Date()); // 设置时间为当前时间
	String y = "";

	String fdTransDept = "";
	if (request.getParameter("fdTransDept") == null) {
		fdTransDept = "";
	} else {
		fdTransDept = request.getParameter("fdTransDept");
	}
	if (request.getParameter("y") == null) {
		y = "";
	} else {
		y = request.getParameter("y");
	}
	String No = "";
	if (request.getParameter("No") == null) {
		No = "";
	} else {
		No = request.getParameter("No");
	}
	String m = "";
	if (request.getParameter("m") == null) {
		m = (ca.get(Calendar.MONTH) + 1) + "";
	} else {
		m = request.getParameter("m");
	}
%>
<form action="hrStaffMoveMonthReport.jsp" method="post"
	style="display: block; margin: auto 20px; line-height: 70px;">
	       是否跨一级部门
                    <xform:radio property="fdTransDept" htmlElementProperties="id='fdTransDept'"  value="<%=fdTransDept%>" showStatus="edit">
                        <xform:enumsDataSource enumsType="hr_staff_move_trans" />
                    </xform:radio>
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
	</select>
	
	<span>月</span>
	
        <input class="bt" name="No" placeholder="请输入工号">
	 <input type="submit" id="button_1" value="查询"
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
 <div class="table-fixed-wrap" column=3>
    <div class="table-fixed-box">
<table class='hovertable fixed-table-main'>
<thead>
	<tr>
		<th nowrap rowspan="2" id="eee">序号</th>
		<th nowrap rowspan="2" id="aaa">工号</th>	
		
		<th nowrap rowspan="2" id="ccc">姓名</th>	
		<th nowrap rowspan="2">是否考察</th>	
		<th nowrap rowspan="2">见习开始日期</th>	
		<th nowrap rowspan="2">见习结束日期</th>	
			<th nowrap rowspan="2" >是否跨一级部门异动</th>
			<th nowrap rowspan="2">异动类型</th>
			<th nowrap rowspan="2">变动生效日期</th>
			<th nowrap colspan="6">异动前</th>
			<th nowrap colspan="6">异动后</th>
	</tr>
	<tr>
			<th nowrap>一级部门</th>
			<th nowrap>二级部门</th>
			<th nowrap >三级部门</th>
			<th  nowrap>职级</th>
			<th nowrap>岗位</th>
			<th nowrap>直接上级</th>
			<th nowrap>一级部门</th>
			<th nowrap>二级部门</th>
			<th nowrap>三级部门</th>
			<th nowrap>职级</th>
			<th nowrap>岗位</th>
			<th  nowrap>直接上级</th>
	</tr>
        </thead>
        <tbody >
	<%
	HrStaffMoveRecordAction hrStaffMoveRecordAction = new HrStaffMoveRecordAction();
	IHrStaffMoveRecordService service = hrStaffMoveRecordAction.getServiceImp(null);
	PersonInfoReport service1 = new PersonInfoReport();
		List<String[]> list = new ArrayList<>();
		list = service.findMoveMonthData1(fdTransDept,y, m,No);
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				String[]str = list.get(i);
	%>
	<tr onmouseover="this.style.backgroundColor='#ffff66';"
		onmouseout="this.style.backgroundColor='#d4e3e5';">
		<td id="fff">
			<!--序号--><%=1+i%></td>
		<td id="bbb">
			<!--部门名称--><%=str[1]!=null?str[1]:""%></td>
		<td id="ddd">
			<!--部门名称--><%=str[2]!=null?str[2]:""%></td>
		<td>
			<!--部门名称--><%=str[3]!=null?str[3]:""%></td>
		<td>
			<!--部门名称--><%=str[4]!=null?str[4]:""%></td>
		<td>
			<!--部门名称--><%=str[5]!=null?str[5]:""%></td>
		<td>
			<!--部门名称--><%=str[6]%></td>
		<td>
			<!--部门名称--><%=str[7]!=null?str[7]:""%></td>
		<td>
			<!--部门名称--><%=service1.dateToString(str[8])%></td>
		<td>
			<!--部门名称--><%=str[9]!=null?str[9]:""%></td>
		<td>
			<!--部门名称--><%=str[10]!=null?str[10]:""%></td>
		<td>
			<!--部门名称--><%=str[11]!=null?str[11]:""%></td>
		<td>
			<!--部门名称--><%=str[12]!=null?str[12]:""%></td>
		<td>
			<!--部门名称--><%=str[13]%></td>
		<td>
			<!--部门名称--><%=str[14]%></td>
		<td>
			<!--部门名称--><%=str[15]!=null?str[15]:""%></td>
		<td>
			<!--部门名称--><%=str[16]!=null?str[16]:""%></td>
		<td>
			<!--部门名称--><%=str[17]!=null?str[17]:""%></td>
		<td>
			<!--部门名称--><%=str[18]!=null?str[18]:""%></td>
		<td>
			<!--部门名称--><%=str[19]%></td>
		<td>
			<!--部门名称--><%=str[20]%></td>
		
		<%
			}
		%>
	</tr>
	<%
		}
	%>
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
        let url = makeSrc('exportPersonalMonthChange');
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

