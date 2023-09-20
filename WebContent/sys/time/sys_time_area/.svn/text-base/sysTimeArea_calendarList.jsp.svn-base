<%@ include file="/resource/jsp/common.jsp"%>
<script>	
Com_IncludeFile("optbar.js|docutil.js");
function calendar_LoadIframe(){
	Doc_LoadFrame("calendarContent","<c:url value="/sys/time/sys_time_area/sysTimeArea.do?method=calendar&fdId="/>${sysTimeAreaForm.fdId}");
}
</script>
<tr LKS_LabelName="<bean:message  bundle="sys-time" key="calendar.model"/>" style="display:none">
	<td id="calendarContent" onresize="calendar_LoadIframe();">
		<iframe src="" width="100%" height="1300px" frameborder="0" scrolling="no">
		</iframe>
	</td>
</tr>