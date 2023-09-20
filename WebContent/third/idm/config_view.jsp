<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/sys/config/resource/edit_top.jsp"%>
<script>
Com_IncludeFile("doclist.js|dialog.js");
</script>
<script>
function config_idm_changeEnable(){
	var tbObj = document.getElementById("kmss.integrate.idm");
	for(var i=0; i<tbObj.rows.length; i++){
		var cfgFields = tbObj.rows[i].getElementsByTagName("INPUT");
		for(var j=0; j<cfgFields.length; j++){
			cfgFields[j].disabled = "disabled";
		}
	}
}
function config_idm_checkFunc(){
	var oms = document.getElementsByName("value(kmss.oms.in.idm.enabled)")[0];
	if(oms.checked){
		config_addUniqueParameter("com.landray.kmss.sys.oms:in","集成IDM组织架构数据同步(接入)");
	}
	return true;
}
window.onload = config_idm_changeEnable;

</script>
<html:form action="/third/idm/config.do?method=config">
<table class="tb_normal" width=95% id="kmss.integrate.idm">
	<tr>
		<td class="td_normal_title" colspan=2>
			<b>
				<label>
					<html:checkbox property="value(kmss.integrate.idm.enabled)" value="true" onclick="config_idm_changeEnable()"/>集成IDM
				</label>
			</b>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">组织架构数据同步(接入)</td>
		<td>
			<label>
				<html:checkbox property="value(kmss.oms.in.idm.enabled)" value="true"/>启用
			</label>
			<span class="message">（从IDM中接入组织架构信息）</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">IDM系统key值</td>
		<td>
			<xform:text property="value(kmss.idm.key)" subject="IDM系统key值" 
				required="true" style="width:85%" showStatus="edit" htmlElementProperties="disabled='true'"/><br>
			<span class="message">
			IDM系统标识，可自定义，接入数据后不可修改该值。例：IDM</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">部门数据webservice地址</td>
		<td>
			<xform:text property="value(kmss.idm.webservice.url.organization)" subject="获取部门信息的webservice地址" 
				required="true" style="width:85%" showStatus="edit" htmlElementProperties="disabled='true'"/><br>
			<span class="message">从IDM获取部门数据对应的webservice地址。
			例：http://192.168.2.130:5080/sim/services/organizationService</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">人员数据webservice地址</td>
		<td>
			<xform:text property="value(kmss.idm.webservice.url.user)" subject="获取人员信息的webservice地址" 
				required="true" style="width:85%" showStatus="edit" htmlElementProperties="disabled='true'"/><br>
			<span class="message">从IDM获取用户数据对应的webservice地址。
			例：http://192.168.2.130:5080/sim/services/userService</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">目标服务器webservice访问用户名</td>
		<td>
			<xform:text property="value(kmss.idm.webservice.userName)" subject="目标服务器webservice访问用户名" 
			  	required="false" style="width:85%" showStatus="edit" htmlElementProperties="disabled='true'"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">目标服务器webservice访问用密码</td>
		<td>
			<xform:text property="value(kmss.idm.webservice.password)" subject="目标服务器webservice访问用密码" 
			     required="false" style="width:85%" showStatus="edit" htmlElementProperties="disabled='true'"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">部门基准DN</td>
		<td>
			<xform:text property="value(kmss.idm.ldap.base.organization)" subject="部门基准DN" 
				style="width:85%" showStatus="edit" htmlElementProperties="disabled='true'"/><br>
			<span class="message">只有在IDM使用LDAP存储的情况下才需要设置该值，不设置该值则表示需要同步所有数据</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">部门过滤条件</td>
		<td>
			<xform:text property="value(kmss.idm.ldap.filter.organization)" subject="部门过滤条件" 
				style="width:85%" showStatus="edit" htmlElementProperties="disabled='true'"/><br>
			<span class="message">只有在IDM使用LDAP存储的情况下才需要设置该值，默认可以设置为“(objectclass=*)”</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">人员基准DN</td>
		<td>
			<xform:text property="value(kmss.idm.ldap.base.user)" subject="人员基准DN" 
				style="width:85%" showStatus="edit" htmlElementProperties="disabled='true'"/><br>
			<span class="message">只有在IDM使用LDAP存储的情况下才需要设置该值，不设置该值则表示需要同步所有数据</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">人员过滤条件</td>
		<td>
			<xform:text property="value(kmss.idm.ldap.filter.user)" subject="人员过滤条件" 
				style="width:85%" showStatus="edit" htmlElementProperties="disabled='true'"/><br>
			<span class="message">只有在IDM使用LDAP存储的情况下才需要设置该值，默认可以设置为“(objectclass=*)”</span>
		</td>
	</tr>
</table>
</html:form>
<br>
<div align="center">
<b>这里只是查看页面，如果要修改配置的话，请进入“admin.do->集成配置”中进行配置</b>
</div>