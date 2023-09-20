<%@page import="org.apache.commons.codec.binary.Base64"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.config.util.LicenseUtil"%>
<%@page import="java.util.ServiceLoader"%>
<%@page import="com.landray.kmss.sys.hibernate.IDataBaseAdapter"%>
<%@page import="com.landray.kmss.sys.config.form.SysConfigAdminForm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script>
JDBC_MAP_URL = new Array();
JDBC_MAP_DRIVER = new Array();
JDBC_MAP_DIALECT = new Array();
JDBC_MAP_MESSAGE = new Array();
JDBC_MAP_DESCRIPTION = new Array();
<%
String editionNational = LicenseUtil.get("license-edition-national");
boolean isEditionNational = false;
if (StringUtil.isNotNull(editionNational)) {
	isEditionNational =  Boolean.valueOf(editionNational);
}

ServiceLoader<IDataBaseAdapter> loaders = ServiceLoader.load(IDataBaseAdapter.class);
for (com.landray.kmss.sys.hibernate.IDataBaseAdapter dbAdapter : loaders) {
%>		
JDBC_MAP_URL["<%=dbAdapter.dbType()+"-"+dbAdapter.version()%>"] = "<%=dbAdapter.url()%>";
JDBC_MAP_DRIVER["<%=dbAdapter.dbType()+"-"+dbAdapter.version()%>"] = "<%=dbAdapter.driver()%>";
JDBC_MAP_DIALECT["<%=dbAdapter.dbType()+"-"+dbAdapter.version()%>"] = "<%=dbAdapter.dialect()%>";
JDBC_MAP_MESSAGE["<%=dbAdapter.dbType()+"-"+dbAdapter.version()%>"] = "<%=dbAdapter.message()%>";
JDBC_MAP_DESCRIPTION["<%=dbAdapter.dbType()+"-"+dbAdapter.version()%>"] = "<%=dbAdapter.description()%>";
<%
}
%>
				
function config_db_selectJdbcType(value){

	var url = document.getElementsByName("value(hibernate.connection.url)")[0];
	var driver = document.getElementsByName("value(hibernate.connection.driverClass)")[0];
	
	if(JDBC_MAP_URL[value]) {
		url.value = JDBC_MAP_URL[value];
	}
	var dialect = document.getElementsByName("value(hibernate.dialect)")[0];
	
	
	if(JDBC_MAP_DIALECT[value]) {
		dialect.value = JDBC_MAP_DIALECT[value];
		driver.value = JDBC_MAP_DRIVER[value];
		document.getElementById("dbdialect").innerText = dialect.value;
		document.getElementById("dbDialectClass").innerText = dialect.value;
		document.getElementById("dbDriverClass").innerText = driver.value;
	}
	
	if(JDBC_MAP_DRIVER[value]) {
		driver.value = JDBC_MAP_DRIVER[value];
	}
	
	config_db_JdbcChange(value);
}
function config_db_JdbcChange(value) {
	var oracle = document.getElementById("oracle");
	var sqlserver = document.getElementById("sqlserver");
	var db2 = document.getElementById("db2");
	var mysql = document.getElementById("mysql");
	var mysql8 = document.getElementById("mysql8");
	var shentong = document.getElementById("shentong");
	var kingbase = document.getElementById("kingbase");
	var gbase = document.getElementById("gbase8t");
	var gbase8s = document.getElementById("gbase8s");
	var gaussDBT = document.getElementById("gaussDBT");
	var polardb4oracle = document.getElementById("polardb4oracle");
	var openGauss = document.getElementById("openGauss");
	var dbmessage = document.getElementById("dbmessage");
	var dbdescription = document.getElementById("dbdescription");

	if(value.indexOf("Oracle")>=0 && JDBC_MAP_MESSAGE[value] != undefined){
		//dbmessage.innerHTML = JDBC_MAP_MESSAGE[value] + '<div style="color: red;">(如果Oracle是11g或更低版本请使用ojdbc5.jar，需要在代码目录\\WEB-INF\\patch\\oracle\\ojdbc\\下运行toOjdbc5.cmd再部署启动)</div>';
	}else{
		if (JDBC_MAP_MESSAGE[value] != undefined)
		{
			dbmessage.innerText = JDBC_MAP_MESSAGE[value];
		}
	}
	if (JDBC_MAP_DESCRIPTION[value] != undefined)
	{
		dbdescription.innerText = JDBC_MAP_DESCRIPTION[value];
	}
	

}
function config_db_selectConnType(value) {
	var tr_url = document.getElementById("tr_url");
	var tr_userName = document.getElementById("tr_userName");
	var tr_password = document.getElementById("tr_password");
	var tr_jdbcstat = document.getElementById("tr_jdbcstat");
	var tr_datasource = document.getElementById("tr_datasource");
	var tr_jdbcsourcetype = document.getElementById("tr_jdbcsourcetype");

	var url = document.getElementsByName("value(hibernate.connection.url)")[0];
	var userName = document.getElementsByName("value(hibernate.connection.userName)")[0];
	var password = document.getElementsByName("value(hibernate.connection.password)")[0];
	var datasource = document.getElementsByName("value(hibernate.connection.datasource)")[0];
	if("jndi" == value) {
		//tr_url.style.display = 'none';
		//tr_userName.style.display = 'none';
		tr_jdbcsourcetype.style.display = 'none';
		tr_jdbcstat.style.display = 'none';
		tr_password.style.display = 'none';
		
		tr_datasource.style.display = '';

		//url.disabled = true;
		//userName.disabled = true;
		password.disabled = true;
		datasource.disabled = false;
	} else {
		tr_url.style.display = '';
		tr_userName.style.display = '';
		tr_password.style.display = '';
		tr_jdbcsourcetype.style.display = '';
		tr_jdbcstat.style.display = '';
		tr_datasource.style.display = 'none';

		url.disabled = false;
		userName.disabled = false;
		password.disabled = false;
		datasource.disabled = true;
	}
}

function config_db_selectJdbcSourceType(obj,value){
	var jdbcstats = document.getElementsByName("value(kmss.jdbc.stat.enabled)");
	for(var i=0;i<jdbcstats.length;i++){
		var jdbcstat = jdbcstats[i];
		if(value=='druidDataSource'){
			jdbcstat.disabled = false;
		}else{
			jdbcstat.disabled = true;
		}
	}
}

function config_db_onloadFunc(){
	var dialect = document.getElementsByName("driverClass")[0];
	config_db_JdbcChange(dialect.value);
	var _type = document.getElementsByName("value(kmss.connection.type)"), type = null;
	for(var i = 0; i < _type.length; i++) {
		if(_type[i].checked) {
			type = _type[i].value;
			break;
		}
	}
	if(type == null) {
		_type[0].checked = true;
		type = "jdbc";
	}
	config_db_selectConnType(type);
}
config_addOnloadFuncList(config_db_onloadFunc);
function dbProcessRequest(request){
	if(warnOracle()){
		alert("当前配置Oracle的连接帐号(sys、system)为系统帐号,请勿使用！");
		return;
	}
	var flag = Com_GetUrlParameter(request.responseText, "flag");
	if(flag){
		alert("数据库连接成功！");
	}else{
		alert("数据库连接失败，请重新配置");
    }
}
function testDbConn(){
	var data = new KMSSData();
	var _type = document.getElementsByName("value(kmss.connection.type)");
	var type = "jdbc";
	for(var i = 0; i < _type.length; i++) {
		if(_type[i].checked) {
			type = _type[i].value;
			break;
		}
	}
	if("jndi"==type) {
		data.AddFromField("datasource", 
			"value(hibernate.connection.datasource)");
	} else {
		data.AddFromField("driver:connurl:username:password", 
			"value(hibernate.connection.driverClass):"+
			"value(hibernate.connection.url):"+
			"value(hibernate.connection.userName):"+
			"value(hibernate.connection.password)");
	}
	data.SendToUrl("admin.do?method=testDbConn", dbProcessRequest);
}
function warnOracle() {
	var dialect = document.getElementsByName("value(hibernate.dialect)")[0];
	var value = document.getElementsByName("value(hibernate.connection.userName)")[0].value;
	var value = value.toLowerCase();
	if('org.hibernate.dialect.Oracle9Dialect' == dialect.value){
		if("sys" == value || "system" == value){
			document.getElementById("oracle_warn").style.display="" ;
			return true;
		}else{
			document.getElementById("oracle_warn").style.display="none" ;
			return false;
		}
	}else{
		document.getElementById("oracle_warn").style.display="none" ;
		return false;
	}
	
}
</script>
<% 
	SysConfigAdminForm configAdminForm = (SysConfigAdminForm) request.getAttribute("sysConfigAdminForm");
	String dbDialect = configAdminForm.getMap().get("hibernate.dialect").toString();
	String dbMessage = null;
	String dbType = null;
	for (com.landray.kmss.sys.hibernate.IDataBaseAdapter dbAdapter : loaders) {
		if(dbDialect.equals(dbAdapter.dialect())){	
			dbMessage = dbAdapter.message();
			dbType = dbAdapter.dbType()+"-"+dbAdapter.version();
			request.setAttribute("dbType",dbType);
		}
	}
%>
					
<table class="tb_normal" width=100%>
	<tr>
		<td class="td_normal_title" colspan=2><b>数据库配置</b></td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">连接类型</td>
		<td>
			<xform:radio property="value(kmss.connection.type)" showStatus="edit" onValueChange="config_db_selectConnType(this.value);" subject="连接类型" required="true">
				<xform:simpleDataSource value="jdbc">JDBC</xform:simpleDataSource>
				<xform:simpleDataSource value="jndi">JNDI</xform:simpleDataSource>
			</xform:radio>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">数据库类型</td>
		<td>
			<xform:select property="driverClass" value="${dbType}" showStatus="edit" onValueChange="config_db_selectJdbcType(this.value);warnOracle();" 
			subject="数据库类型" required="true" showPleaseSelect="true">
				<%
				String dbselect = null;
					for (com.landray.kmss.sys.hibernate.IDataBaseAdapter dbAdapter : loaders) {
						dbselect = dbAdapter.dbType()+"-"+dbAdapter.version();
						if(dbAdapter.isEditionNational()){
							if(isEditionNational){
				%>							
					<xform:simpleDataSource value="<%=dbselect%>"><%=dbAdapter.dbType()+" "+dbAdapter.version()%></xform:simpleDataSource>	
				<%	
							}
						}else{
							if(!dbAdapter.isEditionNational()){
				%>							
					<xform:simpleDataSource value="<%=dbselect%>"><%=dbAdapter.dbType()+" "+dbAdapter.version()%></xform:simpleDataSource>	
				<%
							}
						}
					}
				%>
			</xform:select>
			<html:hidden property="value(hibernate.connection.driverClass)"/>
			<html:hidden property="value(hibernate.dialect)"/>
			<input type="hidden" name="dbDialectClass" id="dbDialectClass" value="${sysConfigAdminForm.map['hibernate.dialect'] }">
			
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">数据库驱动类(DriverClass)</td>
		<td>
			<span id='dbDriverClass' style="display:">
				${sysConfigAdminForm.map['hibernate.connection.driverClass'] }
			</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">数据库方言(Dialect)</td>
		<td>
			<span id='dbdialect' style="display:">
				${sysConfigAdminForm.map['hibernate.dialect'] }
			</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">数据库方言说明</td>
		<td>
			<span id='dbdescription' style="display:"></span>
		</td>
	</tr>
	<tr id="tr_url">
		<td class="td_normal_title" width="15%">数据库连接URL</td>
		<td>
			<xform:text property="value(hibernate.connection.url)" subject="数据库连接URL" required="true" style="width:85%" showStatus="edit"/><br>
			<div style="display:">
				<span id="dbmessage" class="message"></span>
			</div>
		</td>
	</tr>
	<tr id="tr_userName">
		<td class="td_normal_title" width="15%">用户名</td>
		<td>
			<xform:text property="value(hibernate.connection.userName)" subject="用户名" required="true" style="width:150px" showStatus="edit" onValueChange="warnOracle();"/>
			<div id="oracle_warn" style="color: red;display: none">当前配置Oracle的连接帐号为系统帐号,请勿使用！</div>
		</td>
	</tr>
	<tr id="tr_password">
		<td class="td_normal_title" width="15%">密码</td>
		<td>
			<c:set var="_pass" value="${sysConfigAdminForm.map['hibernate.connection.password'] }"/>
			<%
				String pass = (String)pageContext.getAttribute("_pass");
				if(StringUtil.isNotNull(pass)){
					pass = new String(Base64.encodeBase64(pass.getBytes("UTF-8")),"UTF-8");
					pageContext.setAttribute("_pass", "\u4649\u5820\u4d45\u4241\u5345\u3634{" + pass +"}");	
				}
			%>
			<xform:text property="value(hibernate.connection.password)" subject="密码" 
				required="true" style="width:150px" showStatus="edit" htmlElementProperties="type='password'" value="${_pass}"/>
		</td>
	</tr>
	<tr id="tr_jdbcsourcetype">
		<td class="td_normal_title" width="15%">数据库连接池</td>
		<td>
			<xform:radio property="value(kmss.jdbc.datasource)" showStatus="edit" onValueChange="config_db_selectJdbcSourceType(this,this.value);">
				<xform:simpleDataSource value="druidDataSource">Druid连接池</xform:simpleDataSource>
				<xform:simpleDataSource value="hikariDataSource">Hikari连接池</xform:simpleDataSource>
			</xform:radio><br>
			<span class="message">
				Druid连接池具备监控功能，可以很好的协助定位慢sql、链接泄露等问题<br/>
				Hikari连接池拥有更优先的数据库连接性能，暂不具备监控功能。
			</span>
		</td>
	</tr>
	<tr id="tr_jdbcstat">
		<td class="td_normal_title" width="15%">启用JDBC监控</td>
		<td>
			<xform:radio property="value(kmss.jdbc.stat.enabled)" showStatus="edit" >
				<xform:simpleDataSource value="true">是</xform:simpleDataSource>
				<xform:simpleDataSource value="false">否</xform:simpleDataSource>
			</xform:radio><br>
			<span class="message">
				启用JDBC监控后，您可以在“管理员工具箱-请求监控-查看JDBC监控”，查阅监控结果
			</span>
		</td>
	</tr>
	<tr id="tr_datasource">
		<td class="td_normal_title" width="15%">数据源名称</td>
		<td>
			<xform:text property="value(hibernate.connection.datasource)" subject="数据源名称" required="true" style="width:150px" showStatus="edit" /><br />
			样例，数据源名称：jdbc/ekpds
		</td>
	</tr>
	<tr id="tr_hibernateUpdate">
		<td class="td_normal_title" width="15%">Hibernate 更新和验证数据库表结构 </td>
		<td>
			<xform:radio property="value(hibernate.hbm2ddl.auto)" showStatus="edit" >
				<xform:simpleDataSource value="update">update</xform:simpleDataSource>
				<xform:simpleDataSource value="part-update">part-update</xform:simpleDataSource>
				<xform:simpleDataSource value="none">none</xform:simpleDataSource>
			</xform:radio><br>
			<span class="message">
        		update: 系统启动时会检查数据库并决定是新建还是更新表结构，可能占用很长时间<br>
        		part-update: (自定义属性值,不是hibernate自带)，动态检查系统表结构，有变更才更新，否则与none相同，兼容了none的与update，建议在生产环境下使用
				none: 任何时候都不生成表，包括系统的动态建表相关的业务，所以一般在开发阶段使用，能减少启动时间<br>
			</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">测试数据库连接</td>
		<td>	
			<input type="button" class="btnopt" value="测试" onclick="testDbConn()"/>
		</td>
	</tr>
</table>
