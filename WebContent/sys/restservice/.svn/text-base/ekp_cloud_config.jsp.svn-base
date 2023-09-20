<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.sys.notify.util.SysNotifyPluginUtil"%>
<%@page import="com.landray.kmss.sys.notify.provider.ISysNotifyTodoProviderExtend"%>
<%@page import="com.landray.kmss.framework.plugin.core.config.IExtension"%>
<%@page import="com.landray.kmss.framework.service.plugin.Plugin"%>
<%@page import="com.landray.kmss.framework.plugin.core.config.Extension"%>

<table class="tb_normal" width=100%>
	<tr>
		<td class="td_normal_title" colspan=2>
			<b>
				<xform:checkbox property="value(ekp.cloud.accessable)" onValueChange="config_ekpCloud_chgEnabled" showStatus="edit">
					<xform:simpleDataSource value="true">启用EKP Cloud</xform:simpleDataSource>
				</xform:checkbox>
			</b>
		</td>
	</tr>
	<%-- EKP Cloud 配置 --%>
	<tbody id="tbody_ekp_cloud" style="display:none;">
	<tr>
		<td class="td_normal_title" width="15%">发现中心http地址</td>
		<td width="85%">
			<xform:text property="value(ekp.cloud.discovercenter.http.host)" subject="发现中心http地址" required="true" style="width:50%" showStatus="edit" /><br>
			<span class="message">发现中心的http地址，格式为http://hostname:port/，多个以英文逗号','分隔</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">发现中心服务名称</td>
		<td width="85%">
			<xform:text property="value(ekp.cloud.discovercenter.appname)" subject="发现中心服务名称" style="width:50%" showStatus="edit" /><br>
			<span class="message">发现中心的服务名称，默认值为discovery-center</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">注册到发现中心的服务名称</td>
		<td width="85%">
			<xform:text property="value(ekp.cloud.expose.appname)" subject="注册到发现中心的服务名称" style="width:15%" showStatus="edit" /><br>
			<span class="message">注册到发现中心的服务名称，在EKP集群中，所有EKP服务器都应配置相同的服务名，留空自动取默认值origin-ekp</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">加密认证密钥</td>
		<td width="85%">
			<xform:text property="value(ekp.cloud.security.secretkey)" subject="认证密钥" required="true" style="width:25%" showStatus="edit" /><br>
			<span class="message">加密认证的密钥</span>
		</td>
	</tr>
	<%--
	<tr>
		<td class="td_normal_title" width="15%">服务器DNS</td>
		<td width="85%">
			<xform:text property="value(ekp.cloud.server.urlPrefix)" subject="服务器DNS" required="true" style="width:12.5%" showStatus="edit" /><br>
			<span class="message">
				cloud系统访问本系统的URL，例：http://ekp.landray.com.cn/ekp
			</span>
		</td>
	</tr>
	--%>
	<tr>
		<td class="td_normal_title" width="15%">服务器IP</td>
		<td width="85%">
			<xform:text property="value(ekp.cloud.expose.hostname)" subject="服务器IP" required="false" style="width:50%" showStatus="edit" /><br>
			<span class="message">
				注册到eureka的物理主机名或DNS，用于构造http请求的hostname部分，所以请确定它能在网络中使用。<br>
				建议填写本机用于通信的IP地址，如果不填系统会默认选一个非本地环的IP<br/>
				由于集群环境每台服务器中的admin.do配置相同，而每个节点的IP可能不一致，为了方便配置，建议此处配置留空，通过在JVM的参数中添加：
				<strong>-DLandray.ekp.cloud.expose.hostname=服务器IP</strong>，以指定每台服务器的IP（JVM参数值覆盖此处的IP配置）。
			</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">端口</td>
		<td width="85%">
			<xform:text property="value(ekp.cloud.expose.port)" subject="端口" required="false" style="width:12.5%" showStatus="edit" /><br>
			<span class="message">
				提供服务的端口，一般与web服务器配置的http端口保持一致，比如80。<br/>
				由于集群环境每台服务器中的admin.do配置相同，而每个节点的端口可能不一致，为了方便配置，建议此处配置留空，通过在JVM的参数中添加：
				<strong>-DLandray.ekp.cloud.expose.port=端口号</strong>，以指定每台服务器的端口号（JVM参数值覆盖此处的端口配置）。
			</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">允许同步组织架构</td>
		<td width="85%">
			<xform:radio property="value(ekp.cloud.org.sync.enabled)" showStatus="edit">
  				<xform:simpleDataSource value="false">关闭</xform:simpleDataSource>
				<xform:simpleDataSource value="true">开启</xform:simpleDataSource> 
			</xform:radio>
			<br>
			<span class="message">
				当EKP与KMS同时存在时，只需要开启某一应用的同步即可，如果所有应用都开启，可能会存在重复同步的情况
			</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">启用融合MK-Paas待办</td>
		<td width="85%" colspan="2">
			<table class="tb_normal" width=100%>
				<tr>
					<td class="td_normal_title" colspan="3">
							<label>
								<xform:checkbox property="value(ekp.used.mk.todo.able)" onValueChange="config_todo_chgEnabled()" showStatus="edit">
									<xform:simpleDataSource value="true">启用</xform:simpleDataSource>
								</xform:checkbox>(当启用EKP与MK-Paas融合后，EKP不存在待办数据，所有待办数据由MK管理，该开关不能关闭/开启来回切换)
							</label>
							
					</td>
				</tr>
				<tbody id="todo_tab"> 
					<tr>
						<td class="td_normal_title" width="15%">待办消息扩展点</td>
						<td colspan="2">
							<%
								IExtension[] extensions = Plugin.getExtensions("com.landray.kmss.sys.notify", "*", "todoExtend");
								if (extensions != null) {
									for (int i = 0; i < extensions.length; i++) {
										Extension extension = (Extension)extensions[i];
										String providerName = Plugin.getParamValue(extension,"name");
										ISysNotifyTodoProviderExtend eProvider = (ISysNotifyTodoProviderExtend) Plugin.getParamValue(extension,"bean");
										if(eProvider!=null){
										String providerClass = eProvider.getClass().getName();
										if(null == providerName){
											providerName = providerClass;
										}
										request.setAttribute("providerName", providerName);
										request.setAttribute("providerClass", providerClass);
										%>
									    <xform:checkbox property="value(ekp.sys.notify.todo.provider.extend.able.${providerClass})" showStatus="edit">
											<xform:simpleDataSource value="true">启用(<%=providerName %>)</xform:simpleDataSource>
										</xform:checkbox><br>
									    <%
										}
									}
								}
							%>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%">请求MK成功的日志保留待办记录表</td>
						<td width="85%">
							<xform:checkbox property="value(ekp.used.mk.todo.record)" showStatus="edit">
									<xform:simpleDataSource value="true">启用</xform:simpleDataSource>
							</xform:checkbox>
						</td>
					</tr>
				</tbody>
			</table>
		</td>
	</tr>
	</tbody>
</table>
<script>
/* 
 * 启用EKP Cloud
 */
function config_ekpCloud_chgEnabled(){
	var checked = document.getElementsByName("_value(ekp.cloud.accessable)")[0].checked;
	hideTb("tbody_ekp_cloud", !checked);
}

/*
 * 1.对id对应的dom进行隐藏/显示
 * 2.对该dom下的INPUT框禁用/启用
 */
function hideTb(id, disabled){
	var tb = document.getElementById(id);
	tb.style.display = disabled?"none":"";
	var fields = tb.getElementsByTagName("INPUT");
	for(var i=0; i<fields.length; i++){
		fields[i].disabled = disabled;
	}
}

//进入页面初始化
config_ekpCloud_chgEnabled();

function config_todo_chgEnabled() {
	var tbObj = document.getElementById("todo_tab");
	var field = document.getElementsByName("_value(ekp.used.mk.todo.able)")[0];
	for (var i = 0; i < tbObj.rows.length; i++) {
		tbObj.rows[i].style.display = field.checked ? "" : "none";
		var cfgFields = tbObj.rows[i].getElementsByTagName("INPUT");
		for (var j = 0; j < cfgFields.length; j++) {
			cfgFields[j].disabled = !field.checked;
		}
	}
	$(document).trigger("ekp.used.mk.todo.able", field.checked);
}

config_todo_chgEnabled();
</script>
