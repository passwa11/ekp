<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
Com_IncludeFile("calendar.js|jquery.js");
</script>
<script type="text/javascript">
  function reloadFrame(){
			var fdStartTime1 =$("#fdStartTime1").val();
			var fdStartTime2 =$("#fdStartTime2").val();
			var fdEndTime1 =$("#fdEndTime1").val();
			var fdEndTime2 =$("#fdEndTime2").val();
			var funcName=$("#funcName").val();
			var path="${KMSS_Parameter_ContextPath}tic/core/log/tic_core_log_main/ticCoreLogMain.do?method=list&&isError=${param.isError}&fdType=${param.fdType}";
			var params=[];
			if(fdStartTime1){
			params.push("fdStartTime1="+fdStartTime1);
			}
			if(fdStartTime2){
				params.push("fdStartTime2="+fdStartTime2);
				}
			if(fdEndTime1){
				params.push("fdEndTime1="+fdEndTime1);
				}
			if(fdEndTime2){
				params.push("fdEndTime2="+fdEndTime2);
				}
			if(funcName){
				params.push("funcName="+funcName);
				}
			if(params.length>0){
				path=path+"&"+params.join("&");
				}
			$("#listframe").attr("src",path);
	  }
</script>

<html:form action="/tic/core/log/tic_core_log_main/ticCoreLogMain.do">
	<center>
	<p><h3><bean:message bundle="tic-core-log" key="ticCoreLogMain.look"/></h3></p>
<table class="tb_normal" width="98%">
		<!-- 选择..到..之间  -->
		<tr>
			<td class="td_normal_title"><strong>
				<bean:message bundle="tic-core-log" key="ticCoreLogMain.fdStartTime"/></strong></td>
			<td colspan="3"><input type="text" id="fdStartTime1" name="fdStartTime1" value="" class="inputsgl" subject="<bean:message bundle="tic-core-log" key="ticCoreLogMain.fdStartTime"/>" readOnly /> <a href="javascript:void(0)" onclick="selectDateTime('fdStartTime1');">
				<bean:message key="dialog.selectOther"/></a>
				<bean:message bundle="tic-core-log" key="ticCoreLogMain.go"/>
				<input type="text" id="fdStartTime2" name="fdStartTime2" value="" class="inputsgl" subject="<bean:message bundle="tic-core-log" key="ticCoreLogMain.fdStartTime"/>" readOnly /> <a href="javascript:void(0)" onclick="selectDateTime('fdStartTime2');"><bean:message key="dialog.selectOther"/></a>
				<bean:message bundle="tic-core-log" key="ticCoreLogMain.whiles"/>
			</td>
		</tr>
		<tr>
		<td class="td_normal_title"><strong><bean:message bundle="tic-core-log" key="ticCoreLogMain.fdEndTime"/></strong></td>
			<td colspan="3"><input type="text" id="fdEndTime1" name="fdEndTime1" value="" class="inputsgl" subject="<bean:message bundle="tic-core-log" key="ticCoreLogMain.fdEndTime"/>" readOnly /> <a href="javascript:void(0)" onclick="selectDateTime('fdEndTime1');"><bean:message key="dialog.selectOther"/></a>
				<bean:message bundle="tic-core-log" key="ticCoreLogMain.go"/>
				<input type="text" id="fdEndTime2" name="fdEndTime2" value="" class="inputsgl" subject="<bean:message bundle="tic-core-log" key="ticCoreLogMain.fdEndTime"/>" readOnly /> <a href="javascript:void(0)" onclick="selectDateTime('fdEndTime2');"><bean:message key="dialog.selectOther"/></a>
				<bean:message bundle="tic-core-log" key="ticCoreLogMain.whiles"/>
			</td>
		</tr>
		<tr>
		<td colspan="3" class="td_normal_title"><strong><bean:message bundle="tic-core-log" key="ticCoreLogMain.funcName"/></strong></td>
			<td>
				<input type="text" id="funcName" name="funcName" class="inputsgl"  />
			</td>
		</tr>
		
		<tr>
			<td class="td_normal_title" colspan="4">
				<center><input type=button value="<bean:message bundle="tic-core-log" key="ticCoreLogMain.query"/>" 
					onclick="reloadFrame()"></center>
			</td>
		</tr>
	</table>
	</center>
	<div style="padding-top: 20px"><iframe id="listframe"
		src="${KMSS_Parameter_ContextPath}tic/core/log/tic_core_log_main/ticCoreLogMain.do?method=list&&isError=${param.isError}&fdType=${param.fdType}"
		width=100% height=100% frameborder=0 scrolling=no> </iframe>
		</div>

</html:form>
<%@ include file="/resource/jsp/view_down.jsp"%>