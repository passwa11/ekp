<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page	import="com.landray.kmss.hr.staff.service.IHrStaffMoveRecordService"%>
<%@ include file="/sys/ui/jsp/jshead.jsp" %>
<%@page
	import="com.landray.kmss.hr.staff.actions.HrStaffMoveRecordAction"%>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%-- <link href="${KMSS_Parameter_ResPath}style/common/list/list.css" rel="stylesheet" type="text/css"/> --%>
<script>
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    Com_IncludeFile("form.js");
</script>
<style>
    .bt {
        background: none repeat scroll 0 0 #47b5e6;
        border: 1px solid #35a1d0;
        color: #fff;
        cursor: pointer;
        font-size: 14px;
        height: 25px;
        line-height: 22px;
        margin-right: 14px;
        text-align: center;
        width: 60px;
    }
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
	background-color: #c3dde0 !important;
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
//开始时间
String begin = "";
		if (request.getParameter("begin") == null) {
			begin = "";
		} else {
			begin = request.getParameter("begin");
		}	
//结束时间
String end = "";
if (request.getParameter("end") == null) {
	end = "";
} else {
	end = request.getParameter("end");
}
//查询对象
String fdTargetId = "";
if (request.getParameter("fdDeptIds") == null) {
	fdTargetId = "";
} else {
	fdTargetId = request.getParameter("fdDeptIds");
}
int aa;
%>
<form action="all_search.jsp" method="post"
	style="display: block; margin: auto 20px; line-height: 70px;">
    <center>
        <p class="txttitle">
            异常考勤名单
        </p>
        <table class="tb_normal" width=90%>
            <tr>
                <td class="td_normal_title" width="15%">
                    统计开始时间
                </td>
                <td style="">
                    <xform:datetime property="begin" showStatus="edit"  dateTimeType="date" value="<%=begin%>" style="width:110px;" />
                    &nbsp;至
                    <xform:datetime property="end" showStatus="edit"  dateTimeType="date" value="<%=end%>" style="width:110px;" />
                </td>
            </tr>
            <td>
                统计范围
            </td>
            <td>
                <div id='targetDept'>
                    <xform:address orgType="ORG_FLAG_AVAILABLEALL" showStatus="edit" mulSelect="true"  propertyName="fdDeptNames" propertyId="fdDeptIds"  required="true"   style="width:80%"></xform:address>
                </div>
            </td>
        </table>
        <br>
        <input class="bt" type="submit" value="<bean:message key="button.list"/>" onclick="search()"/>
        <input class="bt" type=button value="<bean:message key="button.export"/>" onclick="exportResult();">
        <input type="hidden" name="method_GET">
    </center>
</form>
<!-- <div style="text-align: center;"> -->
<!--     <iframe src="" name="searchIframe" id="searchIframe" align="top" onload="this.height=searchIframe.document.body.scrollHeight;this.width=searchIframe.document.body.scrollWidth" -->
<!--             width="92%" Frameborder=No Border=0 Marginwidth=0 Marginheight=0 Scrolling=No> -->
<!--     </iframe> -->
<!-- </div> -->
	 <div class="table-fixed-wrap" column=1>
    <div class="table-fixed-box">
<!-- <table > -->
	<table class='hovertable fixed-table-main'>
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
		<%

// if (StringUtil.isNull(end) && StringUtil.isNull(fdTargetId) && StringUtil.isNull(end)) {
//     throw new Exception("开始时间或者结束时间未填写");
// }

Date beginTime = DateUtil.convertStringToDate(begin,"yyyy-MM-dd");
Date endTime = DateUtil.convertStringToDate(end,"yyyy-MM-dd");
HrStaffMoveRecordAction hrStaffMoveRecordAction = new HrStaffMoveRecordAction();
IHrStaffMoveRecordService service = hrStaffMoveRecordAction.getServiceImp(null);
List<String[]> list = new ArrayList<>();
list = service.findStatListDetail(beginTime,endTime, Arrays.asList(fdTargetId.split(";")));
if (list.size() > 0) {
	for (int i = 0; i < list.size(); i++) {
		String[]str = list.get(i);
%>
			<tr>
				<td><%=1+i%></td>
				<td><%=str[0]%></td>
				<td><%=str[1]%></td>
				<td><%=str[2]%></td>
				<td><%=str[3]%></td>
				<td><%=str[4]!=null?str[4]:""%></td>
				<td><%=str[5]%></td>
				<td><%=str[6]%></td>
				<td><%=str[7]!=null?str[7]:""%></td>
				<td><%=str[8]!=null?str[8]:""%></td>
			</tr>
			<%
	}}
			%>
		</tbody>
	</table>
    </div>
  </div>
<script language="JavaScript">
    function makeSrc(method) {
        let src = '${LUI_ContextPath}/hr/staff/hr_staff_move_record/hrStaffMoveRecord.do?method=' + method;
        let begin = $("[name='begin']").val();
        if (begin) {
            src = Com_SetUrlParameter(src, "begin", begin);
        }
        let end = $("[name='end']").val();
        if (end) {
            src = Com_SetUrlParameter(src, "end", end);
        }

        let fdDeptIds = $("[name='fdDeptIds']").val();
        if(fdDeptIds){
            src = Com_SetUrlParameter(src, "fdTargetId", fdDeptIds);
        }
        return src;
    }

    function search() {
        let src = makeSrc('statListDetail');
        $('#searchIframe').attr("src", src);
    }

    function exportResult() {
        let url = makeSrc('exportAttendData');
        window.open(url);
    }
</script>
<!--  <script src="src/lib/jq.js"></script> -->
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
<%@ include file="/resource/jsp/edit_down.jsp" %>