<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script>
Com_IncludeFile("doclist.js|dialog.js");
</script>
<script>  
function config_ecalendar_onloadFunc(){
	var tbObj = document.getElementById("calendar.integrate.ecalendar");
	var field = tbObj.rows[0].getElementsByTagName("INPUT")[0];
	//var cms = document.getElementsByName("value(calendar.integrate.gcalendar.enabled)")[0];
	var exchangeMail = document.getElementsByName("value(calendar.integrate.exchange.enabled)")[0];
	for(var i=1; i<tbObj.rows.length; i++){
		tbObj.rows[i].style.display = field.checked?"":"none";

		if(!field.checked){
			exchangeMail.checked = false;
		}
		
		var cfgFields = tbObj.rows[i].getElementsByTagName("INPUT");
		for(var j=0; j<cfgFields.length; j++){
			cfgFields[j].disabled = !field.checked;
		}
	}
}  

function testConnect(){
	var webServiceUrl = document.getElementsByName("value(calendar.integrate.ecalendar.webService.url)")[0].value;
	var domain= document.getElementsByName("value(calendar.integrate.ecalendar.domain)")[0].value;
	var version= document.getElementsByName("value(calendar.integrate.ecalendar.version)")[0].value;
	var type="";
	if(webServiceUrl!="" && domain!=""){
		var style = "dialogWidth:300; dialogHeight:300; status:1; help:0; resizable:1";
		var url = "<c:url value="/resource/jsp/frame.jsp"/>";
		var parameter = "webServiceUrl="+webServiceUrl+"&domain="+domain+"&version="+version;
		url = Com_SetUrlParameter(url, "url","<c:url value='/third/ecalendar/testConnect.jsp?"+parameter+"'/>");
		var rtnVal = window.showModalDialog(url, null, style);
	}else{
		alert("请先设置webService地址和exchange邮箱域名");
	}
}
config_addOnloadFuncList(config_ecalendar_onloadFunc);  


</script>
<table class="tb_normal" width=100% id="calendar.integrate.ecalendar">
	<tr>
		<td class="td_normal_title" colspan=2>
			<b>
				<label> 
					<html:checkbox property="value(calendar.integrate.ecalendar.enabled)" value="true" onclick="config_ecalendar_onloadFunc();"/>集成exchange
				</label>
			</b>
		</td>
	</tr>
		
	<tr>
		<td class="td_normal_title" width="15%">webService地址</td>
		<td>
			<xform:text showStatus="edit" property="value(calendar.integrate.ecalendar.webService.url)" style="width:350px" subject="webService地址" required="true"/><br>
			<br>
			例：https://exchange2.landray.local/EWS/Exchange.asmx
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">exchange邮箱后缀</td>
		<td>
			<xform:text showStatus="edit" property="value(calendar.integrate.ecalendar.domain)" style="width:350px" subject="exchange邮箱域名" required="true"/><br>
			<br>
			例：如果exchange邮箱地址格式为test@landray.com.cn， 则填写：landray.com.cn
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">exchange版本</td>
		<td>
			<xform:select property="value(calendar.integrate.ecalendar.version)" showPleaseSelect="false" showStatus="edit">
				<xform:enumsDataSource enumsType="third_ecalendar_exchange_version" />
			</xform:select>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">日程接入时间调整</td>
		<td>
			<xform:text showStatus="edit" property="value(calendar.integrate.ecalendar.offset)" style="width:350px" subject="日程接入时间调整" />小时<br>
			<span class="txtstrong">
			由于服务器之间的时区不一致等问题，从exchange接入到ekp的日程数据的时间有可能快了8小时（或者其它），所以在这里加入兼容配置，以调整加入数据的时间。这里填正式则表示在原接入时间上加上x小时，负数则减去x小时；请根据实际同步效果配置；不配置的话则不进行调整。
			</span>
		</td>
	</tr>
	
	<tr>
		<td class="td_normal_title" width="15%">是否启用exchange邮箱集成</td>
		<td> 
			<label> 
				<html:checkbox property="value(calendar.integrate.exchange.enabled)" value="true"/>启用
			</label>
		</td>
	</tr>
	
	<tr>
		<td colspan="2" align="center">
			<input type="button" class="btnopt" value="测试连接" onclick="testConnect();">
		</td>
		
	</tr>
	
</table> 