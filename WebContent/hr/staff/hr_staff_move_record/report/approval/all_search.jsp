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
<html:form action="/hr/staff/hr_staff_move_record/hrStaffMoveRecord.do" method="get" target="searchIframe">
    <center>
        <p class="txttitle">
            流程审批时效统计表
        </p>
        <table class="tb_normal" width=90%>
            <tr>
                <td class="td_normal_title" width="15%">
                    统计开始时间
                </td>
                <td style="">
                    <xform:datetime property="begin" showStatus="edit"  dateTimeType="date" style="width:110px;" />
                    &nbsp;至
                    <xform:datetime property="end" showStatus="edit"  dateTimeType="date" style="width:110px;" />
                </td>
            </tr>
        </table>
        <br>
        <input class="bt" type="button" value="<bean:message key="button.list"/>" onclick="search()"/>
        <input class="bt" type=button value="<bean:message key="button.export"/>" onclick="exportResult();">
        <input type="hidden" name="method_GET">
    </center>
</html:form>
<div style="text-align: center;">
    <iframe src="" name="searchIframe" id="searchIframe" align="top" onload="this.height=searchIframe.document.body.scrollHeight;this.width=searchIframe.document.body.scrollWidth"
            width="92%" Frameborder=No Border=0 Marginwidth=0 Marginheight=0 Scrolling=No>
    </iframe>
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
        return src;
    }

    function exportResult() {
        let url = makeSrc('exportRecruitData');
        window.open(url);
    }

    function search() {
        let src = makeSrc('getApprovalData');
        $('#searchIframe').attr("src", src);
    }

    function exportResult() {
        let url = makeSrc('exportApprovalData');
        window.open(url);
    }
</script>
<%@ include file="/resource/jsp/edit_down.jsp" %>