<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<style>
table.gridtable {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	color: #333333;
	border-width: 1px;
	border-color: #666666;
	border-collapse: collapse;
	text-align: center;
}

table.gridtable th {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: black!important;
	background-color: #dedede;
}

table.gridtable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #ffffff; 
}
</style>
<head>
    <script>
        if(window.parent.document.getElementsByClassName("lui_mask_l")){
            var index=window.parent.document.getElementsByClassName("lui_mask_l").length;
            for(var i=0;i<index;i++){
                window.parent.document.getElementsByClassName("lui_mask_l")[i].style.display="none";
                window.parent.document.getElementsByClassName("lui_dialog_main")[i].style.display="none";
            }
        }
    </script>
</head>
<html:form action="/fssc/config/fssc_config_score/fsscConfigScore.do">
    <c:if test="${queryPage == null || queryPage.totalrows == 0}">
        <%@ include file="/resource/jsp/list_norecord.jsp"%>
    </c:if>
    <c:if test="${queryPage.totalrows > 0}">
    <div id="detailDiv" style="overflow:auto;width:1100px;" >
    <table class="gridtable" style="width: 100%" align="center" tbdraggable="true">
            <tr>
                <sunbor:columnHead htmlTag="td">
                    <th style="width: 25px;">${lfn:message('page.serial')}</th>
                    <th >建单人</th>
                    <th >申请日期</th>
                   <th >详细说明</th>
                   <th >分数</th>
                   <th >被点赞人</th>
                   <th >总分</th>
                </sunbor:columnHead>
            </tr>
            <c:forEach items="${queryPage.list}" var="detail" varStatus="vstatus">
                <tr>
                    <td>${vstatus.index+1}</td>
                    <td>${detail[1]}</td>
                    <td>${detail[2]}</td>
                     <td>${detail[3]}</td>
                     <td>${detail[4]}</td>
                     <c:if test="${detail[0]==1 }">
                     <c:set var="key" value="1${detail[5]}"></c:set>
                     <td rowspan="${rowspanMap[key]}">${detail[5]}</td>
                   <td rowspan="${rowspanMap[key]}">${scoreMap[key]}</td>
                     </c:if>
                </tr>
            </c:forEach>
        </table>
        </div>
        <%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
    </c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>
