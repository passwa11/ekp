<%@ page import="com.landray.kmss.sys.cache.redis.RedisConfig" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<table class="tb_normal" width=100%>
	<tr>
		<td class="td_normal_title" colspan=2>
			<b>
				<xform:text property="value(cache.redis.enabled)" subject="Redis配置是否开启" value="true" showStatus="noShow"/>
				Redis配置
			</b>
		</td>
	</tr>
	<tbody id="tbody_cache_redis">
	<tr>
		<td class="td_normal_title" width="15%">Redis服务部署模式</td>
		<td width="85%">
			<xform:radio
					property="value(cache.redis.mode)"
					showStatus="edit"
					required="true"
					onValueChange="config_att_change(this.value);">
				<xform:simpleDataSource value="<%=RedisConfig.KEY_MODE_STANDALONE%>">单机模式</xform:simpleDataSource>
				<xform:simpleDataSource value="<%=RedisConfig.KEY_MODE_SENTINEL%>">哨兵模式</xform:simpleDataSource>
				<xform:simpleDataSource value="<%=RedisConfig.KEY_MODE_CLUSTER%>">集群模式</xform:simpleDataSource>
			</xform:radio>
			<br/>
			<span class="message">必填，请选择实际redis对应的部署模式进行配置。暂不支持主从模式及云托管模式</span>
			<br>
			<span style="color:red;">注意：必须先启动Redis，再启动EKP。Redis的宕机会引发EKP的宕机。</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">Redis服务器地址</td>
		<td width="85%">
			<xform:text property="value(cache.redis.host)" subject="Redis服务器地址" required="true" style="width:90%" showStatus="edit" /><br>
			<span class="message">单机配置："IP1:PORT1"，如："127.0.0.1:6379"
				<br>哨兵配置（哨兵服务节点的地址）："IP1:PORT1;IP2:PORT2;IP3:PORT3;..."，如："127.0.0.1:26379;127.0.0.1:26389;127.0.0.1:26399"）
				<br>集群配置（redis集群服务节点地址）："IP1:PORT1;IP2:PORT2;IP3:PORT3;..."，如："127.0.0.1:7000;127.0.0.1:7001;127.0.0.1:7002;127.0.0.1:7003;127.0.0.1:7004;127.0.0.1:7005"）</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">主(master)节点名称</td>
		<td width="85%">
			<xform:text property="value(cache.redis.master.name)" subject="master节点(主节点)名称" style="width:150px;" showStatus="edit"/><br>
			<span class="message">仅在哨兵模式时需要指定，默认主节点名称为“master”</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">Redis服务器密码</td>
		<td width="85%">
			<xform:text property="value(cache.redis.password)" subject="Redis服务器密码" style="width:150px;" showStatus="edit" htmlElementProperties="type='password'"/>
			<br>
			<span class="message">为了安全建议请使用密码方式连接</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">Redis database</td>
		<td width="85%">
			<xform:text property="value(cache.redis.database)" subject="Redis database" style="width:150px;" showStatus="edit"/>
			</br>
			<span class="message">Redis database配置,默认为0</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">TLS版本</td>
		<td width="85%">
			<xform:checkbox  property="value(cache.redis.tlsv)" dataType="boolean" subject="TLSv1"  showStatus="edit">
				<xform:simpleDataSource value="TLSv1">TLSv1</xform:simpleDataSource>
			</xform:checkbox>
			<xform:checkbox property="value(cache.redis.tlsv)" subject="TLSv1.1" dataType="boolean" showStatus="edit">
				<xform:simpleDataSource value="TLSv1.1">TLSv1.1</xform:simpleDataSource>
			</xform:checkbox>
			<xform:checkbox property="value(cache.redis.tlsv)" subject="TLSv1.2" dataType="boolean" showStatus="edit">
				<xform:simpleDataSource value="TLSv1.2">TLSv1.2</xform:simpleDataSource>
			</xform:checkbox>
			</br>
			<span class="message">在启用ssl/tls，即服务器地址为rediss://ip:port时此配置项有效，不配置使用jdk默认版本</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">Redis连接超时时间</td>
		<td width="85%">
			<xform:text property="value(cache.redis.connection.timeout)" style="width:150px;" showStatus="edit" validators="number; min(1)"/>ms(毫秒)
			</br>
			<span class="message">Redis连接超时时间,默认为10000ms(毫秒)</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">Redis连接空闲时间</td>
		<td width="85%">
			<xform:text property="value(cache.redis.idle.connect.timeout)" style="width:150px;" showStatus="edit" validators="number; min(1)"/>ms(毫秒)
			</br>
			<span class="message">Redis连接空闲时间,默认为10000ms(毫秒)</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">Redis请求/命令超时时间</td>
		<td width="85%">
			<xform:text property="value(cache.redis.request.timeout)" style="width:150px;" showStatus="edit" validators="number; min(1)"/>ms(毫秒)
			</br>
			<span class="message">Redis请求/命令超时时间,默认为3000ms(毫秒)</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">redis单个节点最大连接数</td>
		<td width="85%">
			<xform:text property="value(cache.redis.max.connect.count)" style="width:150px;" showStatus="edit" validators="number; min(1)"/>
			</br>
			<span class="message">redis单个节点最大连接数,默认为64</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">redis单个节点最小空闲连接数</td>
		<td width="85%">
			<xform:text property="value(cache.redis.min.idle.connect.count)" style="width:150px;" showStatus="edit" validators="number; min(1)"/>
			</br>
			<span class="message">redis单个节点最小空闲连接数,默认为32</span>
		</td>
	</tr>
	<%--<tr>
		<td class="td_normal_title" width="15%">群集最大重定向次数</td>
		<td width="85%">
			<xform:text property="value(cache.redis.max.redict.count)" style="width:150px;" showStatus="edit" validators="number; min(1)"/>次
			</br>
			<span class="message">群集最大重定向次数,默认为5(次)</span>
		</td>
	</tr>--%>
	<%--<tr>
		<td class="td_normal_title" width="15%">缓存是否加密</td>
		<td width="85%">
			<xform:checkbox property="value(cache.redis.encrypt)" showStatus="edit">
				<xform:simpleDataSource value="true">对redis的key-value进行加密</xform:simpleDataSource>
			</xform:checkbox>
		</td>
	</tr>--%>
	<tr>
		<td class="td_normal_title" width="15%">缓存值是否加密</td>
		<td width="85%">
			<xform:checkbox property="value(cache.redis.value.encrypt)" showStatus="edit">
				<xform:simpleDataSource value="true">缓存值是否加密</xform:simpleDataSource>
			</xform:checkbox>
			<br/>
			<span class="message">开启后会有一定的性能损耗，旧数据不兼容的风险</span>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">缓存是否压缩</td>
		<td width="85%">
			<xform:checkbox property="value(cache.redis.compress)" showStatus="edit">
				<xform:simpleDataSource value="true">缓存的内容进行压缩</xform:simpleDataSource>
			</xform:checkbox>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">二级缓存</td>
		<td width="85%">
			<xform:checkbox property="value(cache.redis.hibernate.enabled)" showStatus="edit">
				<xform:simpleDataSource value="true">使用Redis作为Hibernate的二级缓存</xform:simpleDataSource>
			</xform:checkbox>
		</td>
	</tr>
	</tbody>
</table>
