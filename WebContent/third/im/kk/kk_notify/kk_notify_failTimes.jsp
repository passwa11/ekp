<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
</head>

<body>
<style>
.PromptTB{border: 1px solid #000033;}
.barmsg{border-bottom: 1px solid #000033;}
</style>
<script type="text/javascript">
Com_Parameter.CloseInfo="<bean:message key="message.closeWindow"/>";
Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
Com_IncludeFile("docutil.js|optbar.js|validator.jsp|validation.js|plugin.js|validation.jsp|eventbus.js|xform.js", null, "js");
</script>
<br><br><br><br><br>
<script type="text/javascript">
Com_IncludeFile("data.js|jquery.js");


</script>
<c:choose>
<c:when test="${!empty failTimes}">  
<table id="List_ViewTable">
			<tr class="tr_listfirst">
					<td>连接地址</td>
					<td>失败次数</td>
					<td>执行操作</td>
            	</tr>
		<c:forEach items="${failTimes}" var="item">  
			<tr class="tr_listrow1">
	            	<td>${item.key }</td>
	            	<td>${item.value }</td>
	            	<td><a href='<c:url value="/third/im/kk/kkNotifyLog.do" />?method=resetFailTimes&url=${item.key }' target="_bland">清零</a></td>
            </tr>
		</c:forEach>
	</table>
</c:when>  
<c:otherwise>
	<center>
	<div style="font-size: 18">
		没有发送失败的情况。
	</div>
	</center>
</c:otherwise>
</c:choose>	
	<%-- 
	
			<table bgcolor="#FFFFFF" border=1 cellspacing=0 cellpadding=0 width=100%>
			<tbody>
				<c:if test="${!empty failTimes}">  
				<tr class="tr_listfirst">
					<th>连接地址</th>
					<th>失败次数</th>
					<th>执行操作</th>
            	</tr>
            <c:forEach items="${failTimes}" var="item">  
            	
            	<tr class="tr_listrow1">
	            	<td>${item.key }</td>
	            	<td>${item.value }</td>
	            	<td><a>清零</a></td>
            	</tr>
                
            </c:forEach>  
        	</c:if>  
        	</tbody>
				
			</table>
	--%>	

</body>
</html>
