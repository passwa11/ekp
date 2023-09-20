<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>

<script>
//提交
function sysFtsearchWordStatisticsRankingConfig_Com_Submit(){
    var maxStatisticsWordsNumVal = $("input[name='maxStatisticsWordsNum']").val();

    if(maxStatisticsWordsNumVal == null || maxStatisticsWordsNumVal == ""){
        alert("<bean:message  bundle='sys-ftsearch-expand' key='search_word_ranking_config_max_num_no_tip'/>");
        return false;
    }
    
    Com_Submit(document.sysFtsearchSearchWordsStatisticsForm, 'update');
    return true; 
}
</script>

<html:form action="/sys/ftsearch/expand/sysFtsearchWordStatisticsRankingConfig.do">
<div id="optBarDiv">
        <input type=button value="<bean:message key="button.update"/>"
            onclick="return sysFtsearchWordStatisticsRankingConfig_Com_Submit();">
</div>
<p class="txttitle"><bean:message bundle="sys-ftsearch-expand" key="search_word_ranking_config"/></p>
<center>
<table class="tb_normal" width=95%> 
    <tr>
        <td class="td_normal_title" width=20%>
            <bean:message  bundle="sys-ftsearch-expand" key="search_word_ranking_config_max_num"/>
        </td>
        <td colspan=3>
            <html:text property="maxStatisticsWordsNum" size="10" maxlength="3"/><span class="txtstrong">*&nbsp;<bean:message  bundle="sys-ftsearch-expand" key="search.words.ranking.max.num.description"/></span>
        </td>
    </tr>
</table>
</center> 
<%@ include file="/resource/jsp/view_down.jsp"%>
</html:form>

