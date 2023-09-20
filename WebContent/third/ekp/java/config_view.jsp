<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/sys/config/resource/edit_top.jsp"%>
<script>
Com_IncludeFile("doclist.js|dialog.js");
</script>
<script>
function config_java_changeEnable(){
	var tbObj = document.getElementById("kmss.integrate.java");
	for(var i=0; i<tbObj.rows.length; i++){
		var cfgFields = tbObj.rows[i].getElementsByTagName("INPUT");
		for(var j=0; j<cfgFields.length; j++){
			cfgFields[j].disabled = "disabled";
		}
	}
}

function cnofig_java_init(){
	config_java_changeEnable();
	var systemName = document.getElementsByName("value(kmss.java.system.name)")[0];
	if(systemName.value==''){
		systemName.value='EKP';
	}
}
function updateOmsIn(){
	var oms = document.getElementsByName("value(kmss.oms.in.java.enabled)")[0];
	var tr_roleLine = document.getElementById("tr_roleLine");
	if(oms.checked){
		tr_roleLine.style.display = "";
	}else{
		tr_roleLine.style.display = "none";
	}
}
window.onload = function(){
	cnofig_java_init();
}

</script>
<html:form action="/third/ekp/java/config.do?method=config">
<table class="tb_normal" width=95% id="kmss.integrate.java">
	<tr>
		<td class="td_normal_title" colspan=2>
			<b>
				<label>
					<html:checkbox property="value(kmss.integrate.java.enabled)" value="true" onclick="config_java_changeEnable()"/>集成其它EKP-JAVA
				</label>
			</b>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">本系统名称</td>
		<td>
			<label>
				<xform:text property="value(kmss.java.system.name)" subject="定义本系统名称" 
					style="width:85%" showStatus="edit" htmlElementProperties="disabled='true'" /><br>
			</label>
			<span class="message">用于和其他系统EKP集成时系统间的区分，如："EKP"、"KMS"、"企业办公系统"、"企业知识管理系统"</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">组织架构数据同步(接入)</td>
		<td>
			<label>
				<html:checkbox property="value(kmss.oms.in.java.enabled)" value="true" onclick="updateOmsIn();"/>启用
			</label>
			<span class="message">（从其它EKP-JAVA中读取组织架构信息）</span>
		</td>
	</tr>
	<tr id="tr_roleLine">
		<td class="td_normal_title" width="15%">角色线接入</td>
		<td>
			<label>
				<html:checkbox property="value(kmss.oms.in.java.synchro.roleLine)" value="true"/>启用
			</label>
			<span class="message">（同步组织架构的同时同步角色线信息，注意：需跟组织架构同步一起使用）</span>
		</td>
	</tr>
	<tr id="tr_roleLine">
		<td class="td_normal_title" width="15%">同步角色线分类</td>
		<td>
			<label>
				<html:checkbox property="value(kmss.oms.in.java.synchro.roleConfCate)" value="true"/>启用
			</label>
			<span class="message">（如果接出系统中组织架构没有角色线分类功能，请不要启用该选项）</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">待办集成</td>
		<td>
			<label>
				<html:checkbox property="value(kmss.notify.todoExtend.java.enabled)" value="true"/>启用
			</label>
			<span class="message">（将待办发送到目标EKP-JAVA系统中，目标系统中必须包含代办的webservice服务）</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">目标服务器url前缀</td>
		<td>
			<xform:text property="value(kmss.java.webservice.urlPrefix)" subject="目标服务器url前缀" 
				required="true" style="width:85%" showStatus="edit" htmlElementProperties="disabled='true'"/><br>
			<span class="message">例：http://ekp-j.landray.com.cn:8080/ekp，其中，
				访问地址：http://ekp-j.landray.com.cn，访问端口：8080,访问应用：/ekp</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">目标服务器webservice访问用户名</td>
		<td>
			<xform:text property="value(kmss.java.webservice.userName)" subject="目标服务器webservice访问用户名" 
			  	required="false" style="width:85%" showStatus="edit" htmlElementProperties="disabled='true'"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">目标服务器webservice访问用密码</td>
		<td>
			<xform:text property="value(kmss.java.webservice.password)" subject="目标服务器webservice访问用密码" 
			     required="false" style="width:85%" showStatus="edit" htmlElementProperties="disabled='true'"/>
		</td>
	</tr>

	<tr>
		<td class="td_normal_title" width="15%">待办发送与MQ集成</td>
		<td>
			<label>
				<html:checkbox property="value(kmss.notify.mq.send.enabled)" value="true"/>启用
			</label>
			<span class="message">（将待办以MQ队列发送到目标EKP-JAVA系统中，本系统必须先启用MQ ）</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">待办接收与MQ集成</td>
		<td>
			<label>
				<html:checkbox property="value(kmss.notify.mq.receive.enabled)" value="true"/>启用
			</label>
			<span class="message">（将待办从MQ队列获取发送到本系统中，本系统必须先启用MQ，<span class="txtstrong">注意：一个系统不能同时启用发送和接收</span> ）</span>
		</td>
	</tr>

</table>
</html:form>
<br>
<div align="center">
<b>这里只是查看页面，如果要修改配置的话，请进入“admin.do->集成配置”中进行配置</b>
</div>