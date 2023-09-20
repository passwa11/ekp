<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%@ include file="/resource/jsp/common.jsp"%>
<style type="text/css">
 .redText{color:red;}
</style>
<div id="optBarDiv">
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>
<p class="txttitle"><bean:message  bundle="sys-print" key="sysPrintEdit.bookmark.title"/></p>
<center>
<table class="tb_normal" width=95%>
	<tr>
		<td colspan="2">
			<b><bean:message  bundle="sys-print" key="sysPrintEdit.bookmark.description1" arg0="</b>" />
		          <br/><bean:message  bundle="sys-print" key="sysPrintEdit.bookmark.description2" arg0='<span class="redText">' arg1="</span>" />
			<bean:message  bundle="sys-print" key="sysPrintEdit.bookmark.description3" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=50%>
			<center>
				<bean:message  bundle="sys-print" key="sysPrintEdit.bookmark.name"/>
			</center>
		</td>
		<td class="td_normal_title" width=50%>
			<center>
				<bean:message  bundle="sys-print" key="sysPrintEdit.bookmark.value"/>
			</center>
		</td>
	</tr>
	
	<c:forEach var="record" items="${bookmarks}">
		<tr>
		<td width=50%>
			<center>
				${record.label }
			</center>
		</td>
		<td width=50%>
			<center>
				${record.name }
			</center>
		</td>
	</tr>
	</c:forEach>
	
	
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>