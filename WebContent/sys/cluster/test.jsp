<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%@page import="com.landray.kmss.sys.cluster.message.MessageCenterTester"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.util.ResourceUtil" %>
<%!
	public void formatTime(JSONObject json, String key, long baseTime, long dt){
		Long time = (Long) json.get(key);
		if(time == null){
			// 通讯中，dt = now - baseTime
			if(dt > 65000){
				json.put(key, "连接超时");
			}else{
				json.put(key, "加载中");
			}
			json.put("dt_"+key, "-");
			json.put("status_"+key, "normal");
		}else{
			// 通讯已完成
			long dtSecond = dt / 1000;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String s_time = df.format(new Date(time));
			String s_dt;
			if(Math.abs(time-baseTime)<1000){
				s_dt = "&lt;1 "+ResourceUtil.getString("sysCluster.test.sec","sys-cluster");
			}else{
				s_dt = ((time-baseTime)/1000)+" "+ResourceUtil.getString("sysCluster.test.sec","sys-cluster");
			}
			if(dtSecond > 0){
				s_time += "<br>( ±"+dtSecond+ResourceUtil.getString("sysCluster.test.sec","sys-cluster")+")";
				s_dt += "<br>( ±"+dtSecond+ResourceUtil.getString("sysCluster.test.sec","sys-cluster")+" )";
			}
			json.put(key, s_time);
			json.put("dt_"+key, s_dt);
			// 预估服务器时间差
			long avgTime = Math.abs(time-baseTime) + (dt/2);
			if(avgTime > 60000){
				json.put("status_"+key, "danger");
			}else if(avgTime > 10000){
				json.put("status_"+key, "warn");
			}else{
				json.put("status_"+key, "normal");
			}
		}
	}
%>
<%
	String start = request.getParameter("start");
	JSONObject result = MessageCenterTester.getResult();
	if("1".equals(start) || result==null){
		// 启动
		MessageCenterTester.start();
		request.setAttribute("start", true);
	}else{
		request.setAttribute("start", false);
		// 输出结果
		long now = System.currentTimeMillis();
		long baseTime = result.getLong("svrTime");
		boolean finish = true;
		// 本节点时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		result.put("svrTime", df.format(new Date(baseTime)));
		formatTime(result, "dbTime", baseTime, 0L);
		// 其它节点时间
		JSONArray targets = result.getJSONArray("targets");
		for(int i=0; i<targets.size(); i++){
			JSONObject json = targets.getJSONObject(i);
			// 计算通讯时间
			Object dtObj = json.get("dt");
			long dt;
			if(dtObj == null){
				// 通讯未完成
				finish = false;
				dt = now - baseTime;
				if(dt > 65000){
					json.put("dt", "连接超时");
				}else{
					json.put("dt", "加载中：&gt; "+(dt/1000)+" 秒");
				}
			} else {
				dt = json.getLong("dt");
				if(dt<1000){
					json.put("dt", dt+" 毫秒");
				}else{
					json.put("dt", (dt / 1000)+" 秒");
				}
			}
			if(dt > 60000){
				json.put("status_dt", "danger");
			}else if(dt > 1000){
				json.put("status_dt", "warn");
			}else{
				json.put("status_dt", "normal");
			}
			// 计算节点时间
			formatTime(json, "svrTime", baseTime, dt);
			formatTime(json, "dbTime", baseTime, dt);
		}
		//设置request
		request.setAttribute("result", result);
		if(now-baseTime>65000){
			request.setAttribute("finish", true);
		}else{
			request.setAttribute("finish", finish);
		}
	}
%>

<template:include ref="default.simple">
	<template:replace name="head">
		<template:super/>
		<link rel=stylesheet href="<%= request.getContextPath() %>/sys/ui/extend/theme/default/style/common.css">
		<style>
			.status_normal{
				text-align: center;
			}
			.status_warn{
				text-align: center;
				background-color: #FFEB9C;
				color: #865600;
			}
			.status_danger{
				text-align: center;
				background-color: #FFC7CE;
				color: #9C0006;
			}
		</style>
	</template:replace>
	<template:replace name="title">
		<bean:message key="sysCluster.test" bundle="sys-cluster"/>
	</template:replace>
	<template:replace name="body">
		<br><br>
		<c:choose><c:when test="${start}">
			<script>location.href = "test.jsp";</script>
		</c:when><c:when test="${not empty result}">
			<c:if test="${!finish}">
				<center><span id="SPAN_TIME">10</span>秒后自动刷新</center>
				<script>
				var time = 10;
				function refreshTime(){
					time--;
					document.getElementById('SPAN_TIME').innerHTML = time;
					if(time==0){
						location.href = "test.jsp";
					}
					setTimeout(refreshTime, 1000);
				}
				setTimeout(refreshTime, 1000);
				</script>
			</c:if>
			<table class="tb_normal">
				<tr class="tr_normal_title">
					<td style="width:150px;" rowspan="2"><bean:message key="sysCluster.test.serverFlag" bundle="sys-cluster"/></td>
					<td style="width:150px;" rowspan="2" title="${lfn:message('sys-cluster:sysCluster.test.commuTime.title') }"><bean:message key="sysCluster.test.commuTime" bundle="sys-cluster"/></td>
					<td colspan="2"><bean:message key="sysCluster.test.os" bundle="sys-cluster"/></td>
					<td colspan="2"><bean:message key="sysCluster.test.db" bundle="sys-cluster"/></td>
				</tr>
				<tr class="tr_normal_title">
					<td style="width:180px;"><bean:message key="sysCluster.test.time" bundle="sys-cluster"/></td>
					<td style="width:100px;" title="${lfn:message('sys-cluster:sysCluster.test.timeSub.title') }"><bean:message key="sysCluster.test.timeSub" bundle="sys-cluster"/></td>
					<td style="width:180px;"><bean:message key="sysCluster.test.time" bundle="sys-cluster"/></td>
					<td style="width:100px;" title="${lfn:message('sys-cluster:sysCluster.test.timeSub.title') }"><bean:message key="sysCluster.test.timeSub" bundle="sys-cluster"/></td>
				</tr>
				<tr>
					<td class="status_normal">${result.svr}</td>
					<td class="status_normal"><bean:message key="sysCluster.test.currNode" bundle="sys-cluster"/></td>
					<td class="status_normal">${result.svrTime}</td>
					<td class="status_normal">-</td>
					<td class="status_${result.status_dbTime}">${result.dbTime}</td>
					<td class="status_${result.status_dbTime}">${result.dt_dbTime}</td>
				</tr>
				<c:forEach items="${result.targets}" var="server">
					<tr>
						<td class="status_normal">${server.svr}</td>
						<td class="status_${server.status_dt}">${server.dt}</td>
						<td class="status_${server.status_svrTime}">${server.svrTime}</td>
						<td class="status_${server.status_svrTime}">${server.dt_svrTime}</td>
						<td class="status_${server.status_dbTime}">${server.dbTime}</td>
						<td class="status_${server.status_dbTime}">${server.dt_dbTime}</td>
					</tr>
				</c:forEach>
				<tr>
					<td style="text-align: center;"><bean:message key="sysCluster.test.reference" bundle="sys-cluster"/></td>
					<td style="padding-left: 30px;"><bean:message key="sysCluster.test.normal" bundle="sys-cluster"/>：&lt;1<bean:message key="sysCluster.test.sec" bundle="sys-cluster"/><br><bean:message key="sysCluster.test.warn" bundle="sys-cluster"/>：1<bean:message key="sysCluster.test.sec" bundle="sys-cluster"/>~60<bean:message key="sysCluster.test.sec" bundle="sys-cluster"/><br><bean:message key="sysCluster.test.danger" bundle="sys-cluster"/>：&gt;60<bean:message key="sysCluster.test.sec" bundle="sys-cluster"/></td>
					<td style="padding-left: 60px;" colspan="2"><bean:message key="sysCluster.test.normal" bundle="sys-cluster"/>：<bean:message key="sysCluster.test.timeSub" bundle="sys-cluster"/> &lt;10<bean:message key="sysCluster.test.sec" bundle="sys-cluster"/><br><bean:message key="sysCluster.test.warn" bundle="sys-cluster"/>：<bean:message key="sysCluster.test.timeSub" bundle="sys-cluster"/> 10<bean:message key="sysCluster.test.sec" bundle="sys-cluster"/>~60<bean:message key="sysCluster.test.sec" bundle="sys-cluster"/><br><bean:message key="sysCluster.test.danger" bundle="sys-cluster"/>：<bean:message key="sysCluster.test.timeSub" bundle="sys-cluster"/> &gt;60<bean:message key="sysCluster.test.sec" bundle="sys-cluster"/></td>
					<td style="padding-left: 60px;" colspan="2"><bean:message key="sysCluster.test.normal" bundle="sys-cluster"/>：<bean:message key="sysCluster.test.timeSub" bundle="sys-cluster"/> &lt;10<bean:message key="sysCluster.test.sec" bundle="sys-cluster"/><br><bean:message key="sysCluster.test.warn" bundle="sys-cluster"/>：<bean:message key="sysCluster.test.timeSub" bundle="sys-cluster"/> 10<bean:message key="sysCluster.test.sec" bundle="sys-cluster"/>~60<bean:message key="sysCluster.test.sec" bundle="sys-cluster"/><br><bean:message key="sysCluster.test.danger" bundle="sys-cluster"/>：<bean:message key="sysCluster.test.timeSub" bundle="sys-cluster"/> &gt;60<bean:message key="sysCluster.test.sec" bundle="sys-cluster"/></td>
				</tr>
			</table>
		</c:when></c:choose><br>
		<center><input type="button" value="${lfn:message('sys-cluster:sysCluster.test.button') }" onclick="location.href='test.jsp?start=1';" style="padding:8px 24px; cursor:pointer;"></center>
	</template:replace>
</template:include>

