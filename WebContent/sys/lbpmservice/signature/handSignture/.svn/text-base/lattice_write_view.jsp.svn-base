<%@page import="com.landray.kmss.sys.lbpmservice.handsignture.model.SysLattice"%>
<%@page import="com.landray.kmss.sys.lbpmservice.handsignture.service.ISysLatticeService"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
ISysLatticeService sysLatticeService = (ISysLatticeService)SpringBeanUtil.getBean("sysLatticeService");
String fdId = request.getParameter("auditNoteFdId");
if(sysLatticeService.getBaseDao().isExist(SysLattice.class.getName(), fdId)){
	SysLattice sysLattice = (SysLattice)sysLatticeService.findByPrimaryKey(fdId, SysLattice.class.getName(), false);
%>
<div class="latticeWriteViewDiv" style="height: <%=sysLattice.getFdHeight()%>">
	<img class="latticeImg" style="width:100%;height:100%;" src="<%=sysLattice.getFdValue()%>" />
</div>
<script>
$(function(){
	$(".latticeWriteViewDiv").closest("td").find(".onlyHandwriteDiv").hide();
})
</script>
<%
}
%>