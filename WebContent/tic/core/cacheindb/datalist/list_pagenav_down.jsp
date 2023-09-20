<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link href="${KMSS_Parameter_ContextPath}resource/style/common/list/list.css" rel="stylesheet" type="text/css" />
<script>Com_IncludeFile("jquery.js");</script>
<div class="pageBox">
	<sunbor:page name="queryPage" rowsizeText="rowsizeText2" pagenoText="pagenoText2" pageListSize="5" pageListSplit="" textStyleClass="pagenav_input" >
	<table width="100%" height="22" cellspacing="0" cellpadding="0" border="0" class="pageNav_tb">
		<tr>
			<td width="2%" valign="top">&nbsp;</td>
			<td width="55%" valign="top">
				<div class="page_box">
				<sunbor:leftPaging><bean:message key="page.thePrev"/></sunbor:leftPaging>
				{11}
				<sunbor:rightPaging><bean:message key="page.theNext"/></sunbor:rightPaging>
				</div>
			</td>
			<td width="15%" valign="top">
				
			</td>
			<td width="10%" valign="top">
				<bean:message key="page.total"/>&nbsp;{3}&nbsp;<bean:message key="page.row"/>
			</td>
			<td width="10%" valign="top">
				
			</td>
			<td width="2%" valign="top">&nbsp;</td>
		</tr>
	</table>
	</sunbor:page>
</div>
<script>
(function($){
	$("div.pageBox a").each(function(i,n){
		var pageno = Com_GetUrlParameter(n.href,"pageno");
		var rowsize = Com_GetUrlParameter(n.href,"rowsize");
		n.href="#";
		if(!!rowsize && !!pageno){
			$(n).click(function(){
				document.getElementsByName("pageno")[0].value=pageno;
				document.getElementsByName("rowsize")[0].value=rowsize;
				document.forms[0].submit();
			});
		}else{
			
		}		
	});
})(jQuery);
</script>