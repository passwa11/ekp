package com.landray.kmss.km.imeeting.synchro;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.km.imeeting.synchro.interfaces.IMeetingSynchroInService;
import com.landray.kmss.km.imeeting.synchro.interfaces.IMeetingSynchroProvider;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.DateUtil;

public class IMeetingSynchroService implements IMeetingSynchroInService {

	private static boolean locked = false;

	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	// 正在执行同步的用户
	private static Set<String> syncroingPersons = new HashSet<String>();

	public static void addSyncroingPerson(String personId) {
		syncroingPersons.add(personId);
	}

	public static void removeSyncroingPerson(String personId) {
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
		context.logMessage("会议同步开始,时间：" + DateUtil.convertDateToString(start,
				DateUtil.TYPE_DATETIME, null));
		List<ImeetingSynchroPluginData> plugins = SynchroPlugin
				.getExtensionList();

		Set<String> personIds_need_sync = new HashSet<String>();

		Map<String, List<String>> sysncroMap = new HashMap<String, List<String>>();
		String appKey;
		try {
			for (ImeetingSynchroPluginData pluginData : plugins) {
				IMeetingSynchroProvider provider = pluginData.getProvider();
				if (!provider.isSynchroEnable()) {
					continue;
				}
				appKey = pluginData.getKey();
				List<String> personIds = pluginData.getProvider()
						.getPersonIdsToSyncro();
				sysncroMap.put(appKey, personIds);
				personIds_need_sync.addAll(personIds);
			}
			Set<String> tempSet = new HashSet<String>();
			for (String temp_appKey : sysncroMap.keySet()) {
				tempSet.add(temp_appKey);
			}
			SynchroInThreadPoolManager manager = SynchroInThreadPoolManager
					.getInstance();
			// KmCalendarBaseConfig config = new KmCalendarBaseConfig();
			// if (StringUtil.isNotNull(config.getThreadPoolSize())) {
			// manager.setTHREAD_POOL_SIZE(Integer.parseInt(config
			// .getThreadPoolSize()));
			// }
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
						IMeetingSynchroInThread thread = new IMeetingSynchroInThread(
								context,
								countDownLatch);
						// thread.setContext(context);
						thread.setPersonId(personId);
						thread.setAppKeys(appKeys);

						manager.submit(thread);

					}
				}
			}
			try {
				// 这里进行同步等待,等所有子线程结束后，执行
				// countdown.await()后面的代码
				countDownLatch.await();
				Date end = new Date();
				context.logMessage("会议同步结束,时间：" + DateUtil
						.convertDateToString(end, DateUtil.TYPE_DATETIME, null)
						+ "   总耗时："
						+ (end.getTime() - start.getTime()) + "ms");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			locked = false;
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
			logger.info("定时任务正在执行该用户的同步");
			result.put("km.imeeting.synchro.quartz.running", null);
		} else {
			List<String> appKeys = new ArrayList<String>();
			appKeys.add(appKey);
			IMeetingSynchroInThread thread = new IMeetingSynchroInThread();
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
		List<ImeetingSynchroPluginData> pluginDatas = SynchroPlugin
				.getExtensionList();
		for (ImeetingSynchroPluginData data : pluginDatas) {
			String appKey = data.getKey();
			IMeetingSynchroProvider provider = data.getProvider();
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
