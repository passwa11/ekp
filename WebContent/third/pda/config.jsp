<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<table class="tb_normal" width=100%>
	<tr>
		<td class="td_normal_title" colspan=2><b>移动组件扩展配置</b></td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">扩展支持的UA信息</td>
		<td><xform:text property="value(third.phone.summary.flag)"
			subject="手机标识列表" required="false" style="width:85%" showStatus="edit" /><br />
		<span class="message">
		1、默认支持iPhone、Android、Nokia几款，如需扩展其他型号移动设备访问，请用“;”间隔，如：mobile;iphone;ipad;android<br/> 
		2、可通过查看UA信息设置该值，在移动设备浏览器中访问../third/pda/ua.jsp地址可获取UA信息。
		</span>
	</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">移动终端访问的域名</td>
		<td><xform:text property="value(third.phone.summary.domain)"
			subject="手机访问域名" required="false" style="width:85%" showStatus="edit" /><br />
		<span class="message">1、当在移动设备浏览器中使用以上域名访问，系统将以移动终端的界面展现，不再辨识UA信息 <br/>
							  2、系统内置访问链接：域名URL+“/pda” 
		</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">仅允许移动终端访问本服务器</td>
		<td>
			<html:checkbox property="value(third.phone.summary.access.enabled)" value="true"/>启用<br />
			<span class="message">仅适用于多服务器<b>集群</b>前提下,专属的移动办公服务器部署配置</span><br>
			<span class="message">为了配置方便，建议在JVM的参数中通过：-DLandray.third.phone.summary.access.enabled=true 进行设置。</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">禁止本服务器的登录功能</td>
		<td>
			<html:checkbox property="value(third.phone.login.enabled)" value="true"/>启用<br />
			<span class="message">本服务器只适用于通过SSO方式认证访问，禁止PC或移动端的直接用户名密码的登录方式访问</span><br>
			<span class="message">为了配置方便，建议在JVM的参数中通过：-DLandray.third.phone.login.enabled=true 进行设置。</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">代理服务器</td>
		<td>
			<xform:text property="value(third.phone.proxy.server)"
				subject="代理服务器" required="false" style="width:85%" showStatus="edit" /><br/>
			<span class="message">适用于EKP服务器无法直接访问微信服务器的系统,地址格式为:host:port</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">移动端开发模式</td>
		<td>
			<html:checkbox property="value(third.phone.dev.enabled)" value="true"/>启用<br />
			<span class="message">移动端开发模式下资源将不被压缩合并</span>
		</td>
	</tr>
	
</table>
