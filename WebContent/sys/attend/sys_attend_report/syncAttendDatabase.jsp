<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
</style>

<html>
    <center>
        <p class="txttitle">
            同步考勤数据到OA
        </p>
        <table class="tb_normal" width=90%>
            <tr>
                <td class="td_normal_title" width="15%">
                   补录时间
                </td>
                <td style="">
                    <xform:datetime property="begin" showStatus="edit"  dateTimeType="date" style="width:110px;" />
                    &nbsp;至
                    <xform:datetime property="end" showStatus="edit"  dateTimeType="date" style="width:110px;" />
                </td>
            </tr>
        </table>
        <br>
        <input class="bt" type="button" value="<bean:message key="button.submit"/>" onclick="search()"/>
<%--         <input class="bt" type=button value="<bean:message key="button.export"/>" onclick="exportResult();"> --%>
        <input type="hidden" name="method_GET">
    </center>
</html>
<div id="div_1"
	style="background-color: #afeeee; font-size: 20; font-weight: bold; text-align: center; display: none;">
	正在同步人员，请稍后.......</div>
<div style="text-align: center;" id="searchIframe">
</div>
<script language="JavaScript">
    function makeSrc(method) {
        let src = '${LUI_ContextPath}/sys/attend/sys_attend_report/sysAttendReport.do?method=' + method;
        let begin = $("[name='begin']").val();
        console.log(begin);
        if (begin) {
            src = Com_SetUrlParameter(src, "begin", begin);
        }
        let end = $("[name='end']").val();
        if (end) {
            src = Com_SetUrlParameter(src, "end", end);
        }
        return src;
    }

    function exportResult() {
        let url = makeSrc('exportAllData');
        window.open(url);
    }

    function search() {
    	document.getElementById("div_1").style.display = "block";
        let src = makeSrc('syncAttendDatabase');
        let begin = $("[name='begin']").val();
        let end = $("[name='end']").val();
        $.ajax({
			   type: "POST",
			   url: src,
			   data: {"begin":begin,"end":end},
			   dataType: "json",
			   async:true,
			   success: function(data){
				   
					   document.getElementById("div_1").style.display = "none";
					   $("#searchIframe").append("<p>以下是同步的人员</p>");
					   console.log(data);
					   for (let i in data) {
						    console.log("key:" + i + "  value:" + data[i])
						   $("#searchIframe").append("<p>"+i+":"+data[i]+"</p>");
					   }
			   },
			   error : function(e){
				   document.getElementById("div_1").style.display = "none";
				   $("#searchIframe").append("<p>同步出错</p>");
				   console.error(e);
				   openExc();
			   }
			});
    }
</script>
<%@ include file="/resource/jsp/edit_down.jsp" %>