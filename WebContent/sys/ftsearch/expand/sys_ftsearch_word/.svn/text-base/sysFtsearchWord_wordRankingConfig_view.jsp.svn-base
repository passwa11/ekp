<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%> 
<div id="optBarDiv">
        <input type="button"
            value="<bean:message key="button.edit"/>"
            onclick="Com_OpenWindow('sysFtsearchWordStatisticsRankingConfig.do?method=edit','_self');">
</div>
<p class="txttitle"><bean:message bundle="sys-ftsearch-expand" key="search_word_ranking_config"/></p>
<center>
<table class="tb_normal" width=95%> 
    <tr>
        <td class="td_normal_title" width=20%>
            <bean:message  bundle="sys-ftsearch-expand" key="search_word_ranking_config_max_num"/>
        </td>
        <td colspan=3>
            <bean:write name="sysFtsearchSearchWordsStatisticsForm" property="maxStatisticsWordsNum"/>
        </td>
    </tr>
</table>
</center> 
<%@ include file="/resource/jsp/view_down.jsp"%>
