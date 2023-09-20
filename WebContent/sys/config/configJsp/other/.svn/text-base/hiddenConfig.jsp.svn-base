<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script>
Com_IncludeFile("doclist.js|dialog.js");
</script>
<!-- 全文检索 -->
<html:hidden property="value(index.all.node.name)"/>
<html:hidden property="value(index.this.node.name)"/>
<!-- #cko/flowstat取日志的时间间隔类型（单位可为day,hour,minute）和间隔数字 -->
<html:hidden property="value(km.cko.domino.data.type)"/>
<html:hidden property="value(km.cko.domino.data.times)"/>
<html:hidden property="value(kmss.flowstatcko.data.count)"/>
<!-- 工作流设置 -->
<html:hidden property="value(kmss.workflow.descriptor.type.value)"/>
<html:hidden property="value(kmss.workflow.descriptor.type.oa.fileName)"/>
<!-- 组织架构信息来源 -->
<html:hidden property="value(kmss.flowstat.omsProvider)"/>
<!-- 系统密码加密算法 -->
<html:hidden property="value(kmss.org.encoder.algorithm)"/>
<!-- 代码安全执行检查开关默认值 -->
<html:hidden property="value(kmss.securityController.disabled)" value="true"/>

<html:hidden property="value(kmss.security.sm2.pubkey)"/>
<html:hidden property="value(kmss.security.sm2.prikey)" />

<!-- 是否开启请求监控方法堆栈 -->
<html:hidden property="value(kmss.performance.tuning.web.enabled)" value="false"/>

<!-- 是否为第三方服务提供文件直链 -->
<html:hidden property="value(kmss.third.att.direct.download.url.disabled)" value="true"/>


<!-- key是否加密 -->
<html:hidden property="value(cache.redis.key.encrypt)" value="false"/>
<!-- value是否加密 -->
<html:hidden property="value(cache.redis.value.encrypt)" value="false"/>
<!-- 是否启用内容压缩 -->
<html:hidden property="value(cache.redis.compress)" value="false"/>
<!-- 压缩算法 SM4，AES -->
<html:hidden property="value(cache.redis.encrypt.algorithm)" value="SM4"/>
<!--#redis部署模式standalone（单机）,sentinel（哨兵）,cluster（集群）-->
<%--<html:hidden property="value(cache.redis.mode)"/>--%>
<%--redis 命令失败重试次数--%>
<html:hidden property="value(cache.redis.retry.attempts)"/>
<%--redis 命令重试发送时间间隔 单位：毫秒 --%>
<html:hidden property="value(cache.redis.retry.interval)"/>
<%--主(master)节点名称 --%>
<html:hidden property="value(cache.redis.master.name)"/>
<%--Redis连接超时时间 --%>
<html:hidden property="value(cache.redis.connection.timeout)"/>
<%--Redis连接空闲时间 --%>
<html:hidden property="value(cache.redis.idle.connect.timeout)"/>
<%--Redis请求/命令超时时间 --%>
<html:hidden property="value(cache.redis.request.timeout)"/>
<%--redis单个节点最大连接数 --%>
<html:hidden property="value(cache.redis.max.connect.count)"/>
<%--redis单个节点最小空闲连接数 --%>
<html:hidden property="value(cache.redis.min.idle.connect.count)"/>


