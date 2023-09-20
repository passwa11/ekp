<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,
				com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.sys.config.util.LicenseUtil"%>
<%@ page import="java.util.ServiceLoader"%>
<%@ page import="com.landray.kmss.sys.hibernate.IDataBaseAdapter"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
String editionNational = LicenseUtil.get("license-edition-national");
boolean isEditionNational = false;
if (StringUtil.isNotNull(editionNational)) {
	isEditionNational = Boolean.valueOf(editionNational);
}

ServiceLoader<IDataBaseAdapter> loaders = ServiceLoader.load(IDataBaseAdapter.class);
%>
<script>
String.prototype.trim=function(){
    return this.replace(/(^\s*)|(\s*$)/g, "");
}

Com_IncludeFile("security.js");
var dbVO = [
	{fdType:"Oracle",fdDriver:"oracle.jdbc.driver.OracleDriver",fdUrl:"jdbc:oracle:thin:@192.168.0.30:1521:test"},
	{fdType:"MS SQL Server",fdDriver:"net.sourceforge.jtds.jdbc.Driver",fdUrl:"jdbc:jtds:sqlserver://localhost:1433/test"},
	{fdType:"DB2",fdDriver:"com.ibm.db2.jcc.DB2Driver",fdUrl:"jdbc:db2://localhost:50000/test"},
	{fdType:"My SQL",fdDriver:"com.mysql.jdbc.Driver",fdUrl:"jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8"},
	{fdType:"My SQL 8.0",fdDriver:"com.mysql.cj.jdbc.Driver",fdUrl:"jdbc:mysql://localhost:3306/ekp?userSSL=true&autoReconnect=true&useUnicode=true&characterEncoding=UTF8&serverTimezone=Asia/Shanghai"},	
	{fdType:"Sybase",fdDriver:"com.sybase.jdbc.SybDriver",fdUrl:"jdbc:sybase:Tds:localhost:5007/test"},
	{fdType:"Informix",fdDriver:"com.informix.jdbc.IfxDriver",fdUrl:"jdbc:informix-sqli://localhost:1533/test:INFORMIXSERVER=myserver"},
	{fdType:"PostgreSQL",fdDriver:"org.postgresql.Driver",fdUrl:"jdbc:postgresql://localhost/test"},
	{fdType:"ODBC",fdDriver:"sun.jdbc.odbc.JdbcOdbcDriver",fdUrl:"jdbc:odbc:DatabaseDSN"},
	<%
	if(isEditionNational){
		for (IDataBaseAdapter dbAdapter : loaders) {
			if(dbAdapter.isEditionNational()){
				if(!"".equals(dbAdapter.version())){
					%>		
					{fdType:"<%=dbAdapter.dbType()+"-"+dbAdapter.version()%>",fdDriver:"<%=dbAdapter.driver()%>",fdUrl:"<%=dbAdapter.url()%>"},
					<%
				}else{
					%>		
					{fdType:"<%=dbAdapter.dbType()%>",fdDriver:"<%=dbAdapter.driver()%>",fdUrl:"<%=dbAdapter.url()%>"},
					<%
				}
			}
		}
	}
	%>
	
	{fdType:"Other",fdDriver:"",fdUrl:""}
]

function typeChange(el){
	for(var i=0;i<dbVO.length;i++){
		if(dbVO[i]["fdType"]==el.value){
			document.getElementById("fdDriver").value=dbVO[i]["fdDriver"];
			document.getElementById("fdUrl").value=dbVO[i]["fdUrl"];
			break;
		}
		
	}
}
// 密码加密
function encryptPassword() {
	if(document.compDbcpForm.fdPassword!=undefined){
		var password = document.compDbcpForm.fdPassword.value.trim();
		document.compDbcpForm.fdPassword.value = password;
		if(password.length > 0)
			document.compDbcpForm.fdPassword.value = desEncrypt(document.compDbcpForm.fdPassword.value);
	}
	
}
// 提交操作
function submitForm(action) {
	encryptPassword();
/*	<c:if test="${'add' eq JsParam.method}">
	var password = document.compDbcpForm.fdPassword.value.trim();
	if(password.length < 1) {
		var msg1 = '<bean:message key="errors.required"/>';
		var msg2 = '<bean:message key="compDbcp.fdPassword" bundle="component-dbop"/>';
		alert(msg1.replace('{0}', msg2));
		return false;
	}
	</c:if> */
	
	Com_Submit(document.compDbcpForm, action);
}
</script>
<%

		List types = new ArrayList();
		String[] values = new String[] {"Oracle", "MS SQL Server", "DB2",
				"My SQL", "My SQL 8.0","Sybase","Informix","PostgreSQL","ODBC"};
		String[] texts = new String[] { "Oracle", "MS SQL Server", "DB2",
				"My SQL", "My SQL 8.0", "Sybase","Informix","PostgreSQL","ODBC" };
		for (int i = 0; i < values.length; i++) {
			Map names = new HashMap();
			names.put("value", values[i]);
			names.put("text", texts[i]);
			types.add(names);
		}

%>

<%

	if(isEditionNational){
		for (IDataBaseAdapter dbAdapter : loaders) {
			if(dbAdapter.isEditionNational()){
				Map names = new HashMap();
				if(!"".equals(dbAdapter.version())){
					names.put("value", dbAdapter.dbType() + "-" + dbAdapter.version());
					names.put("text", dbAdapter.dbType() + "-" + dbAdapter.version());
				}else{
					names.put("value", dbAdapter.dbType());
					names.put("text", dbAdapter.dbType());
				}
				types.add(names);
			}
		}
	}
	
	Map names = new HashMap();
	names.put("value", "Other");
	names.put("text", ResourceUtil.getString("component-dbop:compDbcp.fdType.other", request.getLocale()));
	types.add(names);
	
	request.setAttribute("types", types);
%>

<html:messages id="messages" message="true">
	<table style="margin:0 auto" align="center">
	<tr>
	   <td>
	       <img src='${KMSS_Parameter_ContextPath}resource/style/default/msg/dot.gif'>&nbsp;&nbsp;<font color='red'>
           <bean:write name="messages" /></font>
	 </td>
	 </tr>
	 </table><hr />
</html:messages> 

<html:form action="/component/dbop/compDbcp.do" onsubmit="return validateCompDbcpForm(this);">
<div id="optBarDiv">
<input type=button value="<bean:message  bundle="component-dbop" key="compDbcp.help"/>" onclick="window.open('${LUI_ContextPath }/component/dbop/help/dataSource_setting.html');">
		<input type=button value="<bean:message  bundle="component-dbop" key="compDbcp.button.test"/>"
			onclick="Com_Submit(document.compDbcpForm, 'test');">
	<c:if test="${compDbcpForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="submitForm('update');">
	</c:if>
	<c:if test="${compDbcpForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="submitForm('save');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="submitForm('saveadd');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>
<html:hidden property="fdId"/>
<p class="txttitle"><bean:message  bundle="component-dbop" key="table.compDbcp"/></p>
<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="component-dbop" key="compDbcp.fdName"/>
		</td><td width="85%">
			<xform:text property="fdName"  style="width:90%;" required="true"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="component-dbop" key="compDbcp.fdType"/>
		</td><td width="85%">
			<xform:select property="fdType" showStatus="edit" onValueChange="typeChange(this);" required="true">
				<c:forEach items="${types}" var="type">
					<xform:simpleDataSource value="${type.value}">${type.text}</xform:simpleDataSource>
				</c:forEach>
			</xform:select>
			<bean:message  bundle="component-dbop" key="compDbcp.fdType.note"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15% id="fdDriverTitle">
			<bean:message  bundle="component-dbop" key="compDbcp.fdDriver"/>
		</td><td width="85%">
			<xform:text property="fdDriver" htmlElementProperties="id='fdDriver'"  style="width:90%;" required="true"/>
		</td>
	</tr>
	<tr id = "fdUrl_id">
		<td class="td_normal_title" width=15% >
			<bean:message  bundle="component-dbop" key="compDbcp.fdUrl"/>
		</td><td width="85%">
			<xform:text property="fdUrl" htmlElementProperties="id='fdUrl'" style="width:90%;" required="true"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="component-dbop" key="compDbcp.fdUsername"/>
		</td><td width="85%">
			<xform:text property="fdUsername"  style="width:90%;" required="true"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="component-dbop" key="compDbcp.fdPassword"/>
		</td><td width="85%">
			<c:if test="${'add' eq JsParam.method || 'add' eq method_GET}">
				<xform:text property="fdPassword" required="true" htmlElementProperties="type='password'" showStatus="edit" style="width:90%"></xform:text>
			</c:if>
			<c:if test="${'edit' eq JsParam.method || 'edit' eq method_GET}">
				<xform:text property="fdPassword" htmlElementProperties="type='password'" showStatus="edit" style="width:90%"></xform:text><bean:message  bundle="component-dbop" key="compDbcp.fdPassword.desc"/></span>
			</c:if>
		</td>
	</tr>
	<!-- 可使用者 -->
	<tr>
		<td class="td_normal_title" width=15%><bean:message
			bundle="component-dbop" key="table.compDbcpUser" /></td>
		<td  width=85% colspan="3">
			<xform:address textarea="true" mulSelect="true" propertyId="authReaderIds" propertyName="authReaderNames" orgType="ORG_TYPE_ALL" style="width:97%;height:90px;" ></xform:address>
			<br>
	   </td>
	</tr>	
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="component-dbop" key="compDbcp.fdDescription"/>
		</td>
		<td colspan=3 width=85%>
			<xform:textarea property="fdDescription"  style="width:95%;" required="true"/>
		</td>
	</tr>
</table>
</center>
<html:hidden property="method_GET"/>
</html:form>
<html:javascript formName="compDbcpForm"  cdata="false"
      dynamicJavascript="true" staticJavascript="false"/>
<%@ include file="/resource/jsp/edit_down.jsp"%>