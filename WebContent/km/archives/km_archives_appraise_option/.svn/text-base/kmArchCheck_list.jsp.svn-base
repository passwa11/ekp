<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page errorPage="/resource/jsp/jsperror.jsp" %>
<%@ page import="
	com.landray.kmss.util.KmssMessageWriter,
	com.landray.kmss.util.KmssReturnPage" %>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<script type="text/javascript">
Com_Parameter.CloseInfo="<bean:message key="message.closeWindow"/>";
Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
Com_IncludeFile("docutil.js|optbar.js|validator.jsp|validation.js|plugin.js|validation.jsp|xform.js|jquery.js", null, "js");
</script>
<body>
<br>
<% if(request.getAttribute("KMSS_RETURNPAGE")==null){ %>
<logic:messagesPresent>
	<table align=center><tr><td>
		<font class="txtstrong"><bean:message key="errors.header.vali"/></font>
		<bean:message key="errors.header.correct"/>
		<html:messages id="error">
			<br><img src='${KMSS_Parameter_StylePath}msg/dot.gif'>&nbsp;&nbsp;<bean:write name="error"/>
		</html:messages>
	</td></tr></table>
	<hr />
</logic:messagesPresent>
<% }else{
	KmssMessageWriter msgWriter = new KmssMessageWriter(request, (KmssReturnPage)request.getAttribute("KMSS_RETURNPAGE"));
%>
<script>
Com_IncludeFile("msg.js", "style/"+Com_Parameter.Style+"/msg/");
function showMoreErrInfo(index, srcImg){
	var obj = document.getElementById("moreErrInfo"+index);
	if(obj!=null){
		if(obj.style.display=="none"){
			obj.style.display="block";
			srcImg.src = Com_Parameter.StylePath + "msg/minus.gif";
		}else{
			obj.style.display="none";
			srcImg.src = Com_Parameter.StylePath + "msg/plus.gif";
		}
	}
}
</script>

<% } %>
<center>
	<table class="tb_normal" width="100%" style="table-layout:fixed;">
		<tr>
				<td class="td_normal_title" style="width:6%;text-align:center"><bean:message key="page.serial"/></td>
				<td class="td_normal_title" style="width:10%;text-align:center"> 
					${lfn:message('km-archives:kmArchivesMain.docSubject')}
				</td>
				<td class="td_normal_title" style="width:8%;text-align:center">
					${lfn:message('km-archives:kmArchivesMain.docNumber')}
				</td>
				<td class="td_normal_title" style="width:10%;text-align:center">
					${lfn:message('km-archives:kmArchivesMain.fdFileDate')}
				</td>

		</tr>
		<input id="TotalAmount" type="hidden" value=""/>
		<c:forEach items="${queryPage.list}" var="kmArchivesMain" varStatus="vstatus">
			<tr align="center">
				<td>${vstatus.index+1}</td>
				<td  style="text-overflow:ellipsis;overflow:hidden;white-space: nowrap;"
				 title = "${kmArchivesMain.docSubject}">
					<c:out value="${kmArchivesMain.docSubject}" />
				</td>
				<td  style="text-overflow:ellipsis;overflow:hidden;white-space: nowrap;"
				 title = "${lfn:message('km-archives:kmArchivesMain.docNumber')}">
					<c:out value="${kmArchivesMain.docNumber}" />
				</td>
				<td style="text-overflow:ellipsis;overflow:hidden;white-space: nowrap;"
				title = "${lfn:message('km-archives:kmArchivesMain.fdFileDate')}">
					<kmss:showDate value="${kmArchivesMain.fdFileDate}" type="date"/>
				</td>
				<input class="inputsgl" size="5" type="hidden" name="fdNumber_${kmArchivesMain.fdId}">
			</tr>
		</c:forEach>		
	</table>
	<br/>
	<center>
	    <input type=button class="btnopt" value="<bean:message key="button.ok"/>" onclick="closeDialog();">
	</center>
</center>
 <script>
  function closeDialog(){
	  var rtn = "";
	  var fdNumber = $("input[name^='fdNumber']");
	    for(var i=0;i<fdNumber.length;i++){
	    	var tArray = fdNumber[i].name.split("_") ;
			rtn += tArray[1] + ",";
	    }
    parent.$dialog.hide(rtn.substring(0, rtn.length-1));
  }
</script>

<%@ include file="/resource/jsp/edit_down.jsp"%>