<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<div class="quote_tip">${lfn:message('sys-mportal:sysMportal.logo.quote.tip')}</div>
<div class="quote_click" onclick="quote()">${lfn:message('sys-mportal:sysMportal.logo.quote.click')}</div>

<style>
.quote_tip{
    text-align: center;
    margin: 30px;
    width: 320px;
}
.quote_click{
    color:#2574ad;
    margin: 40px;
    text-align: center;
    cursor: pointer;
}
</style>
<script type="text/javascript">
    function quote(){
    	window.open("${LUI_ContextPath }"+"/sys/mportal/sys_mportal_logo/sysMportalLogo.do?method=getLogoQuote"); 
    }
</script>
