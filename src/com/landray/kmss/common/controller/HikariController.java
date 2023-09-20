package com.landray.kmss.common.controller;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.web.RestResponse;
import com.zaxxer.hikari.HikariConfigMXBean;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;

/**
 * 获取Hikari数据源详情
 * @author huangzz
 *
 */
@Controller
@RequestMapping(value = "/hikari")
public class HikariController {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(HikariController.class);

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public RestResponse<Object> hikariMonitor(HttpServletRequest request, String hql) {

		Map<String, Object> poolMap = new HashMap<String, Object>();
		poolMap.putAll(hikariConfig());
		poolMap.putAll(poolInfo());
		return RestResponse.ok(poolMap, "hikari 数据源信息");
	}

	private Map<String, Object> hikariConfig() {
		Map<String, Object> configMap = new HashMap<String, Object>();
		HikariDataSource hikariDataSource = (HikariDataSource) SpringBeanUtil.getBean("hikariDataSource");
		try {
			MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
			ObjectName poolName = new ObjectName("com.zaxxer.hikari:type=PoolConfig (" + hikariDataSource.getPoolName() + ")");
			HikariConfigMXBean hkariConfigMXBean = JMX.newMXBeanProxy(mBeanServer, poolName, HikariConfigMXBean.class);
			if (hkariConfigMXBean == null) {
				logger.info("Hikari not initialized,please wait...");
			} else {
				configMap.put("池中最大连接数,包括闲置和使用中的连接:", hkariConfigMXBean.getMaximumPoolSize());
				configMap.put("池中连接最长生命周期 :", hkariConfigMXBean.getMaxLifetime());
				configMap.put("池中维护的最小空闲连接数:", hkariConfigMXBean.getMinimumIdle());
				configMap.put("等待来自池的连接的最大毫秒数 :", hkariConfigMXBean.getConnectionTimeout());
				configMap.put("连接允许在池中闲置的最长时间:", hkariConfigMXBean.getIdleTimeout());
				configMap.put("连接将被测试活动的最大时间量:", hkariConfigMXBean.getValidationTimeout());
				configMap.put("记录消息之前连接可能离开池的时间量,表示可能的连接泄漏:", hkariConfigMXBean.getLeakDetectionThreshold());
			}
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
		}
		return configMap;
	}

	private Map<String, Object> poolInfo() {
		Map<String, Object> poolMap = new HashMap<String, Object>();
		HikariDataSource hikariDataSource = (HikariDataSource) SpringBeanUtil.getBean("hikariDataSource");
		try {
			MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
			ObjectName poolName = new ObjectName("com.zaxxer.hikari:type=Pool (" + hikariDataSource.getPoolName() + ")");
			HikariPoolMXBean poolProxy = JMX.newMXBeanProxy(mBeanServer, poolName, HikariPoolMXBean.class);
			if (poolProxy == null) {
				logger.info("Hikari not initialized,please wait...");
			} else {

				poolMap.put("激活数量:", poolProxy.getActiveConnections());
				poolMap.put("空闲数量:", poolProxy.getIdleConnections());
				poolMap.put("等待数量:", poolProxy.getThreadsAwaitingConnection());
				poolMap.put("链接总数量:", poolProxy.getTotalConnections());
			}
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
		}
		return poolMap;
	}

}
