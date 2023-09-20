<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<title><bean:message bundle="sys-config" key="sys.systemDebug.title" /></title>
<script>
	function openDebug(){
		var calssName = document.getElementsByName("className")[0].value;
		var level = document.getElementsByName("level")[0].value;
		if(calssName == ""){
			alert('<bean:message bundle="sys-config" key="sys.systemDebug.className.empty" />');
			return;
		}
		if(level == ""){
			alert('<bean:message bundle="sys-config" key="sys.systemDebug.level.empty" />');
			return;
		}
		calssName = Com_Trim(calssName);
		Com_OpenWindow(Com_Parameter.ContextPath+'sys/common/config.do?method=chgLog&class='+calssName+'&level='+level,'_self');
	}
	function resetForm(){
		var calssName = document.getElementsByName("className")[0];
		var level = document.getElementsByName("level")[0];
		calssName.value = '';
		level.value = '';
	}
</script>
<div id="optBarDiv">
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>
<p class="txttitle"><bean:message bundle="sys-config" key="sys.systemDebug.title" /></p>
<center>
<table class="tb_normal" width=85%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-config" key="sys.systemDebug.className" />
		</td><td width="85%">
			<input type="text" name="className" style="width:90%" class="inputsgl"/>
			<span class="txtstrong">*</span><br>
			<span class="message">
					<bean:message bundle="sys-config" key="sys.systemDebug.className.example" />com.landray.kmss.km.review.service.spring.KmReviewMainServiceImp
			</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-config" key="sys.systemDebug.level" />
		</td><td width="85%">
			<select name="level">
				<option value=""><bean:message bundle="sys-config" key="sys.systemDebug.level.select" /></option>
				<option value="TRACE">TRACE</option>
				<option value="DEBUG">DEBUG</option>
				<option value="ERROR">ERROR</option>
				<option value="INFO">INFO</option>
				<option value="WARN">WARN</option>
			</select>
		</td>
	</tr>	
</table>
<br/>
<table class="tb_nobrder" width=85%>
	<tr>
		<td align="center">
			<input type=button class="btnopt" value="<bean:message bundle="sys-config" key="sys.systemDebug.execute"/>" onclick="openDebug();">
			&nbsp;&nbsp;&nbsp;
			<input type=button class="btnopt" value="<bean:message bundle="sys-config" key="sys.systemDebug.reset"/>" onclick="resetForm();">
		</td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/edit_down.jsp"%>