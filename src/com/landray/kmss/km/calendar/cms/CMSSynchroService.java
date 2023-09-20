package com.landray.kmss.km.calendar.cms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.landray.kmss.km.calendar.cms.interfaces.ICMSProvider;
import com.landray.kmss.km.calendar.cms.interfaces.ICMSSynchroService;
import com.landray.kmss.km.calendar.model.KmCalendarBaseConfig;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;

public class CMSSynchroService implements ICMSSynchroService {

	private static boolean locked = false;
	
	private static final Log logger = LogFactory.getLog(CMSSynchroService.class);
	

	// 正在执行同步的用户
	private static Set<String> syncroingPersons = Collections.synchronizedSet(new HashSet<String>());

	public static void addSyncroingPerson(String personId) {
		// System.out.println("添加用户：" + personId);
		syncroingPersons.add(personId);
	}

	public static void removeSyncroingPerson(String personId) {
		// System.out.println("移出用户：" + personId);
		syncroingPersons.remove(personId);
	}

	/**
	 * 获取用户所需要同步的所有app应用
	 * 
	 * @param personId
	 * @param appKeysSet
	 *            应用的appKey集成
	 * @param sysncroMap
	 *            所有app应用所需要同步的用户信息
	 * @return
	 */
	public static List<String> getPersonSyncroApps(String personId,
			Set<String> appKeysSet, Map<String, List<String>> sysncroMap) {
		List<String> appKeys = new ArrayList<String>();
		// appKeys.add(appKey);
		// 查找当前personId对应的用户是否需要跟其它的应用同步
		for (String tempkey : appKeysSet) {
			List<String> tempList = sysncroMap.get(tempkey);
			// 判断personId对应的用户是否需要同步tempkey对应的应用
			int pos = binarySearch(sysncroMap.get(tempkey), personId);
			if (pos >= 0) {
				tempList.remove(pos);
				appKeys.add(tempkey);
			}
		}
		return appKeys;
	}

	/*
	 * 获取某个用户所绑定的同步应用
	 * 
	 * public List<CalSyscroBind> getCalSynchroBindData(SysOrgPerson person) {
	 * return null; }
	 */

	/**
	 * 定时任务同步
	 */
	@Override
	public void synchroAll(SysQuartzJobContext context) throws Exception {
		if (locked) {
            return;
        }
		locked = true;
		Date start = new Date();
		context.logMessage("日程同步开始,时间：" + DateUtil.convertDateToString(start, DateUtil.TYPE_DATETIME, null));
		List<CMSPluginData> plugins = CMSPlugin.getExtensionList();

		Set<String> personIds_need_sync = new HashSet<String>();
		Map<String, List<String>> sysncroMap = new HashMap<String, List<String>>();
		String appKey;
		for (CMSPluginData pluginData : plugins) {
			ICMSProvider provider = pluginData.getCmsProvider();
			if (!provider.isSynchroEnable()) {
				continue;
			}
			appKey = pluginData.getAppKey();
			List<String> personIds = pluginData.getCmsProvider()
					.getPersonIdsToSyncro();
			sysncroMap.put(appKey, personIds);
			personIds_need_sync.addAll(personIds);
		}
		Set<String> tempSet = new HashSet<String>();
		for (String temp_appKey : sysncroMap.keySet()) {
			tempSet.add(temp_appKey);
		}
		
		CMSThreadPoolManager manager = CMSThreadPoolManager.getInstance();
		KmCalendarBaseConfig config = new KmCalendarBaseConfig();
		if (StringUtil.isNotNull(config.getThreadPoolSize())) {
			manager.setTHREAD_POOL_SIZE(Integer.parseInt(config
					.getThreadPoolSize()));
		}
		manager.start();

		CountDownLatch countDownLatch = new CountDownLatch(
				personIds_need_sync.size());

		for (Iterator<String> iter = sysncroMap.keySet().iterator(); iter
				.hasNext();) {
			appKey = iter.next();
			tempSet.remove(appKey);

			for (String personId : sysncroMap.get(appKey)) {
				if (!syncroingPersons.contains(personId)) {
					List<String> appKeys = getPersonSyncroApps(personId,
							tempSet, sysncroMap);
					appKeys.add(appKey);
					CMSSynchroThread thread = new CMSSynchroThread(context,
							countDownLatch);
					// thread.setContext(context);
					thread.setPersonId(personId);
					thread.setAppKeys(appKeys);
					submitTask(manager, thread, 0, countDownLatch);
				}
			}
		}
		try {
			// 这里进行同步等待,等所有子线程结束后，执行
			context.logMessage("总处理人数：" + personIds_need_sync.size());
			countDownLatch.await(20, TimeUnit.HOURS);
			// System.out.println("##结束等待------------------------");
			Date end = new Date();
			context.logMessage("日程同步结束,时间：" + DateUtil.convertDateToString(end, DateUtil.TYPE_DATETIME, null)
					+ "   总耗时："
					+ (end.getTime() - start.getTime()) + "ms");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		if(manager != null){
			logThreadPoolInfo(manager);
			manager.shutdown();
		}
		locked = false;
	}

	/**
	 * 提交任务
	 * @param manager
	 * @param thread
	 * @param retryCount
	 * @throws InterruptedException
	 */
	private void submitTask(CMSThreadPoolManager manager, CMSSynchroThread thread, int retryCount, CountDownLatch countDownLatch) throws InterruptedException {
		try {
			manager.submit(thread);
		}
		catch(RejectedExecutionException e)
		{
			if(retryCount < 6){
				++retryCount;
				int delayTime = 500 * retryCount;
				logger.warn("线程池当前队列任务数：" + manager.getCMSThreadPoolExecutor().getQueue().size());
				logger.warn("线程池队列已满，延迟" + delayTime + "毫秒后，第" + retryCount + "次重新提交用户[" + thread.getPersonId() + "]任务!!!");
				Thread.sleep(delayTime);
				submitTask(manager, thread, retryCount, countDownLatch);				
			}
			else{
				countDownLatch.countDown();
				logger.error("用户[" + thread.getPersonId() + "]任务尝试提交" + retryCount + "次，仍失败，放弃该任务!!!");
			}
		}
	}

	/**
	 * 记录线程信息
	 * @param manager
	 */
	private void logThreadPoolInfo(CMSThreadPoolManager manager) {
		if(logger.isDebugEnabled()){			
			StringBuilder threadPoolInfo = new StringBuilder("线程池信息：");
			threadPoolInfo.append("总处理任务数："+manager.getCMSThreadPoolExecutor().getCompletedTaskCount());
			threadPoolInfo.append(",核心线程数："+manager.getCMSThreadPoolExecutor().getCorePoolSize());
			threadPoolInfo.append(",最大线程数："+manager.getCMSThreadPoolExecutor().getMaximumPoolSize());
			threadPoolInfo.append(",线程池队列容量："+manager.getCMSThreadPoolExecutor().getQueue().remainingCapacity());
			threadPoolInfo.append(",线程池拒绝策略类："+manager.getCMSThreadPoolExecutor().getRejectedExecutionHandler().getClass().toString());
			logger.debug(threadPoolInfo.toString());
			
			Thread current = Thread.currentThread();
			StringBuilder threadInfoBuilder = new StringBuilder("当前任务主线程信息：");
			threadInfoBuilder.append("线程id:["+current.getId()+"]");
			threadInfoBuilder.append(",线程名称:["+current.getName()+"]");
			threadInfoBuilder.append(",线程优先级:["+current.getPriority()+"]");
			logger.debug(threadInfoBuilder.toString());
		}
	}

	/**
	 * 手动同步
	 * 
	 * @param personId
	 * @return
	 */
	public Map<String, Object> syncro(String personId, String appKey) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (syncroingPersons.contains(personId)) {
			System.out.println("定时任务正在执行该用户的同步");
			result.put("km.calendar.synchro.quartz.running", null);
		} else {
			List<String> appKeys = new ArrayList<String>();
			try {
				List<CMSPluginData> plugins = CMSPlugin.getExtensionList();
				for (CMSPluginData pluginData : plugins) {
					ICMSProvider provider = pluginData.getCmsProvider();
					if (!provider.isSynchroEnable()) {
						continue;
					}
					appKeys.add(pluginData.getAppKey());
				}
			} catch (Exception e) {
				appKeys.add(appKey);
			}
			CMSSynchroThread thread = new CMSSynchroThread();
			// thread.setContext(null);
			thread.setPersonId(personId);
			thread.setAppKeys(appKeys);
			result = thread.syncro();

		}
		return result;
	}

	public void reset() {
		locked = false;
		syncroingPersons.clear();
	}

	public List<String> getAppKeysForSyncro(String personId) throws Exception {
		List<String> appKeysForSyncro = new ArrayList<String>();
		List<CMSPluginData> CMSPluginDatas = CMSPlugin.getExtensionList();
		for (CMSPluginData data : CMSPluginDatas) {
			String appKey = data.getAppKey();
			ICMSProvider provider = data.getCmsProvider();
			if (!provider.isSynchroEnable()) {
				continue;
			}
			if (provider.isNeedSyncro(personId)) {
				appKeysForSyncro.add(appKey);
			}

		}
		return appKeysForSyncro;
	}

	/**
	 * 使用二分法在list集合中查找str字符串
	 * 
	 * @param list
	 *            传入的数据集合
	 * @param str
	 *            要查询的字符串
	 * @return
	 */
	public static int binarySearch(List<String> list, String str) {
		if (list == null || list.isEmpty()) {
			return -1;
		}
		int curPos;
		int startPos = 0;
		int endPos = list.size() - 1;
		String temp;
		int counts = 0;
		while (true) {
			curPos = (startPos + endPos) / 2;
			temp = list.get(curPos);
			if (str.equals(temp)) {
				return curPos;
			} else if (endPos < startPos) {
				return -1;
			} else {
				if (str.compareTo(temp) > 0) {
					startPos = curPos + 1;
				} else {
					endPos = curPos - 1;
				}
			}
			counts++;
		}
	}

}
