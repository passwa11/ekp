<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
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
            每月异动信息
        </p>
        <table class="tb_normal" width=90%>
            <tr>
                <td class="td_normal_title" width="15%">
                    是否跨一级部门
                </td>
                <td style="">
                    <xform:radio property="fdTransDept" htmlElementProperties="id='fdTransDept'" showStatus="edit">
                        <xform:enumsDataSource enumsType="hr_staff_move_trans" />
                    </xform:radio>
                </td>
            </tr>
            <tr> <td class="td_normal_title" width="15%">
                    异动时间
                </td>
            <td>
            <select id="y" style="margin-right: 20px 100px;" >
		<option value="" >请选择</option>
		<option value="2022" >2022</option>
		<option value="2023" >2023</option>
		<option value="2024" >2024</option>
		<option value="2025" >2025</option>
		<option value="2026" >2026</option>
		<option value="2027" >2027</option>
		<option value="2028" >2028</option>
		<option value="2029" >2029</option>
		<option value="2030" >2030</option>
		<option value="2031" >2031</option>
		<option value="2032" >2032</option>
		<option value="2033" >2033</option>
	</select> <span>年</span> <select id="m" style="margin-right: 20px 100px;">
		<option value="1" >1</option>
		<option value="2" >2</option>
		<option value="3" >3</option>
		<option value="4" >4</option>
		<option value="5" >5</option>
		<option value="6" >6</option>
		<option value="7" >7</option>
		<option value="8" >8</option>
		<option value="9" >9</option>
		<option value="10" >10</option>
		<option value="11" >11</option>
		<option value="12" >12</option>
	</select><span>月</span>
	</tr>
        </table>
        <br>
        <input class="bt" type="button" value="<bean:message key="button.list"/>" onclick="search()"/>
        <input class="bt" type=button value="<bean:message key="button.export"/>" onclick="exportResult();">
        <input type="hidden" name="method_GET">
    </center>
</html:form>
<div style="text-align: center;">
    <iframe src="" name="searchIframe" id="searchIframe" align="top" onload="this.height=750;this.width=searchIframe.document.body.scrollWidth"
            width="92%" Frameborder=No Border=0 Marginwidth=0 Marginheight=0>
            

    </iframe>
</div>
<script language="JavaScript">
    function makeSrc(method) {
        let src = '${LUI_ContextPath}/hr/staff/hr_staff_move_record/hrStaffMoveRecord.do?method=' + method;
        let fdTransDept = $("[name='fdTransDept']:checked").val();
        let y = $("#y option:selected").text();
        let m = $("#m option:selected").text();
        console.log(y);
        if(fdTransDept != ''){
            src = Com_SetUrlParameter(src, "fdTransDept", fdTransDept);
        }
        if(y != ''){
            src = Com_SetUrlParameter(src, "y", y);
        }
        if(m != ''){
            src = Com_SetUrlParameter(src, "m", m);
        }
        return src;
    }

    function exportResult() {
        let url = makeSrc('exportMonthChange');
        window.open(url);
    }

    function search() {
        let src = makeSrc('getMoveMonthData');
        $('#searchIframe').attr("src", src);
        
        let iframeDocument = $(document).find("iframe")[0].contentWindow.document;
        $($(document).find("iframe")[0]).height(450);
        $(document).find("iframe")[0].onload = function(){
        	$(window.frames["searchIframe"].document).find('.table-fixed-wrap').fixedTable(3); 
        }
        
    }
</script>
  <script src="../../../resource/table-fixed/src/lib/jq.js"></script>
  <script src="../../../resource/table-fixed/tablefix.min.js"></script>
  <script>
    /**
     * 引入tablefix.min.js
     * 初始化
     */
    
    
    function setIframeHeight(){
    	var main = $(window.parent.document).find("iframe");
    	console.log('main'+main);
    	$(main).height(750);
    }

    </script>
<%@ include file="/resource/jsp/edit_down.jsp" %>