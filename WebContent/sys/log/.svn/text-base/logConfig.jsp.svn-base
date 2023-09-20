<%@page import="org.apache.commons.codec.binary.Base64"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script>
Com_IncludeFile("doclist.js|dialog.js");

var kmss_tripartite_admin_enabled = false;

function config_sysLog_location_onloadFunc() {
	var field = $("#kmss_isLogLocationEnabled").find("input[type=radio]:checked");
	// 默认选中“开启”
	if(field.length < 1) {
		$("#kmss_isLogLocationEnabled").find("input[type=radio]").first().attr("checked", "checked");
	}
	
	// 接收三员管理开关事件
	$(document).on("kmss.tripartite.admin.enabled", function(evn, val){
		kmss_tripartite_admin_enabled = val;
		$("input:radio[name='value(kmss.oper.log.store.pattern)'][value='DB']").attr("disabled", val);
		config_tripartite_operLog_chgEnabled();
	});
	
	var val=$("input:radio[name='value(kmss.oper.log.store.pattern)']:checked").val();
	if(!val){
	   $("input:radio[name='value(kmss.oper.log.store.pattern)'][value='DB']").attr("checked", true);
	}
}

function config_tripartite_operLog_chgEnabled() {
	if(kmss_tripartite_admin_enabled) {
		$("input:radio[name='value(kmss.oper.log.store.pattern)'][value='']").attr("checked", true);
	}
	
	var val=$("input:radio[name='value(kmss.oper.log.store.pattern)']:checked").val();
	
	var isNotDB=("DB"!=val);
	change_table_style(isNotDB ? "none" :"");
	
	// 如果开启了本地存储日志，还需要配置日志服务
	var table = document.getElementById("log_openLogService_table");
	table.style.display = isNotDB ? "" : "none";
	config_tripartite_openLogService_chgEnabled();
}

function config_tripartite_openLogService_chgEnabled() {
	
	
	var tbObj = document.getElementById("openLogService");
	var field = document.getElementsByName("_value(log.openLogService)")[0];
	for (var i = 0; i < tbObj.rows.length; i++) {
		tbObj.rows[i].style.display = field.checked ? "" : "none";
		var cfgFields = tbObj.rows[i].getElementsByTagName("INPUT");
		for (var j = 0; j < cfgFields.length; j++) {
			cfgFields[j].disabled = !field.checked;
		}
	}

	if(field.checked) {
		document.getElementsByName("value(log.openLogService)")[0].value = "true";
        var back=document.getElementsByName("value(log.elastic.back.cycle)")[0].value ;
        if(back==null || back=="" ){
			 document.getElementsByName("value(log.elastic.back.cycle)")[0].value = "3";  
		 }
		var clean=document.getElementsByName("value(log.elastic.clean.cycle)")[0].value ;
		if(clean==null || clean==""){
			document.getElementsByName("value(log.elastic.clean.cycle)")[0].value = "3";
		} 
		
	} else {
		document.getElementsByName("value(log.openLogService)")[0].value = "";
	}
	
	
	
}

function change_table_style(val) {
	var table = document.getElementById("kmss_isLogAppEnabled_table");
	for (var i = 0; i < table.rows.length; i++) {
		if(i==0 || i==2  || i==4 || i==5) continue;
		table.rows[i].style.display = val;
	}
	
		if(val) {
			table.rows[0].style.display = "none";
		} else {
			table.rows[0].style.display = "";
		}
	
}

// 提交前处理
Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = function() {
	var val=$("input:radio[name='value(kmss.oper.log.store.pattern)']:checked").val();
	if("DB"!=val) {
		// 如果启用操作日志（用于elastic），这里的有些数据需要处理
		// 操作日志 禁用
		document.getElementsByName("value(kmss.isLogAppEnabled)")[1].checked = false;
		// 所有备份的天数设置为1
		document.getElementsByName("value(kmss.logAppBackupBefore)")[0].value = 1;
		document.getElementsByName("value(kmss.logErrorBackupBefore)")[0].value = 1;
		document.getElementsByName("value(kmss.logJobBackupBefore)")[0].value = 1;
		document.getElementsByName("value(kmss.logLoginBackupBefore)")[0].value = 1;
		document.getElementsByName("value(kmss.logAppDeleteBackBefore)")[0].value = 1;
		document.getElementsByName("value(kmss.logErrorDeleteBackBefore)")[0].value = 1;
		document.getElementsByName("value(kmss.logJobDeleteBackBefore)")[0].value = 1;
		document.getElementsByName("value(kmss.logLoginDeleteBackBefore)")[0].value = 1; 
	}
	return true;
}

config_addOnloadFuncList(function() {
	config_sysLog_location_onloadFunc();
	config_tripartite_operLog_chgEnabled();
	config_tripartite_openLogService_chgEnabled();
});

</script>

<table class="tb_normal" width=100%>
	<tr>
		<td class="td_normal_title" colspan=2><b>日志管理</b></td>
	</tr>
	
	<tr>
		<td class="td_normal_title" width="15%">日志类型</td>
		<td >
			<b>
				<label>
					<xform:radio property="value(kmss.oper.log.store.pattern)" showStatus="edit" onValueChange="config_tripartite_operLog_chgEnabled()">
						<xform:simpleDataSource value="LocalFile">本地文件存储</xform:simpleDataSource>
					<%-- 	<xform:simpleDataSource value="MQ">MQ队列存储</xform:simpleDataSource> --%>
						<xform:simpleDataSource value="DB">数据库存储</xform:simpleDataSource>
					</xform:radio>
				</label>
			</b>
			<hr>
			<table class="tb_normal" width=100% id="kmss_isLogAppEnabled_table">
				<tr>
					<td class="td_normal_title" colspan=2><b>注：此功能的日志信息保存到EKP的数据库</b></td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">是否启用操作日志</td>
					<td >
						<label>
							<html:radio property="value(kmss.isLogAppEnabled)" value="true"/>启用
							<html:radio property="value(kmss.isLogAppEnabled)" value="false"/>禁用
						</label>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">是否启用登录(登出)日志</td>
					<td>
						<label>
							<html:radio property="value(kmss.isLogLoginEnabled)" value="true"/>启用
							<html:radio property="value(kmss.isLogLoginEnabled)" value="false"/>禁用
						</label>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">是否启用显示IP地点</td>
					<td id="kmss_isLogLocationEnabled">
						<label>
							<html:radio property="value(kmss.isLogLocationEnabled)" value="true"/>启用
							<html:radio property="value(kmss.isLogLocationEnabled)" value="false"/>禁用
						</label>
						<br>
						<span class="message">注意：该功能需要服务器连接外网(使用百度API获取IP地理位置)，如果服务器不能连接外网，则禁用此功能，同时禁用“【日志管理】更新IP地址对应的地点”定时任务。</span>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">内网IP地址段</td>
					<td>
						<xform:text property="value(kmss.logWLANip)" subject="内网IP地址段" style="width:500px" showStatus="edit"/><br>
						<span class="message">例：192.168.*.*，说明：所有的日志都会记录IP地址对应的地点，此处设置的内网地址段将不会查询地点。多个地IP段用;号隔开。</span>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">应用日志备份天数</td>
					<td>
						<xform:text property="value(kmss.logAppBackupBefore)" subject="应用日志备份天数" required="true" style="width:150px" showStatus="edit"/>天<br>
						<span class="message">例：7，说明：当前日志保存7天内容，7天前的自动备份到另外一个历史日志表</span>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">错误日志备份天数</td>
					<td>
						<xform:text property="value(kmss.logErrorBackupBefore)" subject="错误日志备份天数" required="true" style="width:150px" showStatus="edit"/>天<br>
						<span class="message">例：7，说明：当前日志保存7天内容，7天前的自动备份到另外一个历史日志表</span>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">后台日志备份天数</td>
					<td>
						<xform:text property="value(kmss.logJobBackupBefore)" subject="后台日志备份天数" required="true" style="width:150px" showStatus="edit"/>天<br>
						<span class="message">例：7，说明：当前日志保存7天内容，7天前的自动备份到另外一个历史日志表</span>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">登录(登出)日志备份天数</td>
					<td>
						<xform:text property="value(kmss.logLoginBackupBefore)" subject="登录(登出)日志备份天数" required="true" style="width:150px" showStatus="edit"/>天<br>
						<span class="message">例：7，说明：当前日志保存7天内容，7天前的自动备份到另外一个历史日志表</span>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">清除应用日志备份天数</td>
					<td>
						<xform:text property="value(kmss.logAppDeleteBackBefore)" subject="清除应用日志备份天数" required="true" style="width:150px" showStatus="edit"/>天<br>
						<span class="message">例：90，说明：清除备份日志表中90天前的数据</span>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">清除错误日志备份天数</td>
					<td>
						<xform:text property="value(kmss.logErrorDeleteBackBefore)" subject="清除错误日志备份天数" required="true" style="width:150px" showStatus="edit"/>天<br>
						<span class="message">例：90，说明：清除备份日志表中90天前的数据</span>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">清除后台日志备份天数</td>
					<td>
						<xform:text property="value(kmss.logJobDeleteBackBefore)" subject="清除后台日志备份天数" required="true" style="width:150px" showStatus="edit"/>天<br>
						<span class="message">例：90，说明：清除备份日志表中90天前的数据</span>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">清除登录(登出)日志备份天数</td>
					<td>
						<xform:text property="value(kmss.logLoginDeleteBackBefore)" subject="清除登录(登出)日志备份天数" required="true" style="width:150px" showStatus="edit"/>天<br>
						<span class="message">例：90，说明：清除备份日志表中90天前的数据</span>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">记录错误日志条数</td>
					<td>
						<xform:text property="value(kmss.logRecodeCount)" subject="数据库记录错误日志条数"  style="width:150px" showStatus="edit"/>条<br>
						<span class="message">例：500，说明：每天错误日志超过指定数量不记录到数据库中</span>
					</td>
				</tr>
			</table>
			<!-- 日志服务 -->
			<table class="tb_normal" width=100% id="log_openLogService_table">
				<tr>
					<td class="td_normal_title" colspan="3">
						<b>
							<label>
								<xform:checkbox property="value(log.openLogService)" showStatus="edit" onValueChange="config_tripartite_openLogService_chgEnabled()">
									<xform:simpleDataSource value="true">日志服务</xform:simpleDataSource>
								</xform:checkbox>
							</label>
							<br>
							<span class="message">注： 启用日志服务，需开启定时任务： 【日志管理】扫描本地日志文件</span>
					</td>
				</tr>
				<tbody id="openLogService">
					<tr>
						<td class="td_normal_title" width="15%">Elastic服务访问地址</td>
						<td>
							<xform:text property="value(log.elastic.urls)" subject="elastic url" style="width:85%" required="true" showStatus="edit"/><br>
						</td>
					</tr>
					<tr>
					   <td class="td_normal_title" width="15%">Elastic 账号</td>
					   <td>
					   <xform:text property="value(log.elastic.user)" subject="Elastic 账号" style="width:150px" required="false" showStatus="edit"/><br> 
					   </td>
					</tr>
					<tr>
					   <td class="td_normal_title" width="15%">Elastic 密码</td>
					   <td>
					   <c:set var="_espass" value="${sysConfigAdminForm.map['log.elastic.password'] }"/>
						<%
							String espass = (String)pageContext.getAttribute("_espass");
							if(StringUtil.isNotNull(espass)){
								espass = new String(Base64.encodeBase64(espass.getBytes("UTF-8")),"UTF-8");
								pageContext.setAttribute("_espass", "\u4649\u5820\u4d45\u4241\u5345\u3634{" + espass +"}");	
							}
						%>
					   	<xform:text property="value(log.elastic.password)" subject="Elastic 密码" style="width:150px" required="false" showStatus="edit"
					   		htmlElementProperties="type='password'" value="${_espass}" /><br>
					   	<span class="message">注意：如果配置elastic 账号与密码，elastic 需要开启安全设置。</span>
					   </td>
					</tr>

					<tr>
						<td class="td_normal_title" width="15%">备份/恢复 文件路径</td>
						<td>
							<xform:text property="value(log.elastic.path.repo)" subject="备份/恢复目录" style="width:150px" required="true" showStatus="edit"/><br>
						    <span class="message">注：必须与elastic 配置文件中备份文件路径一致</span>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%">自动备份周期</td>
						<td>
							<xform:text property="value(log.elastic.back.cycle)" subject="自动备份周期" style="width:150px" validators="digits min(1)" required="true" showStatus="edit"/>个月<br>
						    <span class="message">注：该功能需要开启定时任务： 【日志管理】系统自动备份ElasticSearch日志  </span>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%">自动清理周期</td>
						<td>
							<xform:text property="value(log.elastic.clean.cycle)" subject="清理间隔周期" style="width:150px" validators="digits min(1)" required="true" showStatus="edit"/>个月<br>
						      <span class="message">注意：该功能需要开启定时任务： 【日志管理】系统自动备份ElasticSearch日志</span>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%">日志来源配置</td>
						<td>
							<xform:text property="value(log.elastic.source)" subject="日志来源配置" style="width:85%"  showStatus="edit"/><br>
						    <span class="message">注意：如果ES日志集成了多个版本的EKP系统，需要配置好多个EKP日志来源，多个来源使用;号分隔；如：V10;V13</span>
						</td>
					</tr>
				</tbody>
			</table>
		</td>
	</tr>
</table>

