package com.landray.kmss.sys.filestore.state;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.filestore.model.SysFileConvertClient;
import com.landray.kmss.sys.filestore.model.SysFileConvertLog;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.service.ISysFileConvertClientService;
import com.landray.kmss.sys.filestore.service.ISysFileConvertLogService;
import com.landray.kmss.sys.filestore.service.ISysFileConvertQueueService;
import com.landray.kmss.sys.filestore.util.SysFileStoreUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.sunbor.web.tag.Page;

/**
 * 定义各状态通过的模板
 *
 */
public abstract class AbstractConvertQueueState extends BaseServiceImp implements ConvertQueueState {
	
	/**
	 * 客户端组件
	 */
	protected ISysFileConvertClientService clientService = null;
	private ISysFileConvertClientService getClientService() {
		if (clientService == null) {
			clientService = (ISysFileConvertClientService) SpringBeanUtil.getBean("sysFileConvertClientService");
		}
		return clientService;
	}
	
	/**
	 * 日志亡组件
	 */
	protected ISysFileConvertLogService sysFileConvertLogService = null;
	private ISysFileConvertLogService getLogService() {
		if (sysFileConvertLogService == null) {
			sysFileConvertLogService = (ISysFileConvertLogService) SpringBeanUtil.getBean("sysFileConvertLogService");
		}
		return sysFileConvertLogService;
	}
	
	/**
	 * 队列服务
	 */
	protected ISysFileConvertQueueService sysFileConvertBaseService = null;
	
	private ISysFileConvertQueueService getSysFileConvertQueueService() {
		if(sysFileConvertBaseService == null) {
			sysFileConvertBaseService = (ISysFileConvertQueueService) SpringBeanUtil.getBean("sysFileConvertQueueService");
		}
		
		return sysFileConvertBaseService;
	}
	
	/**
	 * 获取转换客户端信息
	 * 
	 * @param convertingQueue
	 * @param clientId
	 * @return
	 * @throws Exception
	 */
	public SysFileConvertClient getQueueClient(SysFileConvertQueue convertingQueue, String clientId) throws Exception {
		SysFileConvertClient resultClient = null;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdId = :clientId");
		hqlInfo.setParameter("clientId", StringUtil.isNotNull(clientId) ? clientId : convertingQueue.getFdClientId());
		SysFileConvertClient client = (SysFileConvertClient)getClientService().findFirstOne(hqlInfo);
		return client;
	}
	
	/**
	 * 获取转换客户端信息 长任务信息
	 * @return
	 * @throws Exception
	 */
	public SysFileConvertClient getQueueLongClient() throws Exception {
		SysFileConvertClient resultClient = null;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("avail = :avail and isLongTask = :isLongTask");
		hqlInfo.setParameter("avail", true);
		hqlInfo.setParameter("isLongTask", true);
		SysFileConvertClient client = (SysFileConvertClient) getClientService().findFirstOne(hqlInfo);
		return client;
	}
	
	/**
	 * 更新队列信息
	 * @param convertQueue
	 * @throws Exception
	 */
	public void updateConvertQueue(SysFileConvertQueue convertQueue) throws Exception {
		//update(convertQueue);
		getSysFileConvertQueueService().update(convertQueue);
	}
	/**
	 * 保存转换日志
	 * 
	 * @param queueClient
	 * @throws Exception
	 */
	public void saveQueueClient(SysFileConvertClient queueClient) throws Exception {
		if (queueClient != null) {
			getClientService().update(queueClient);
		}
		
	}
	
	/**
	 * 保存SysFileConvertLog
	 * @param convertLog
	 * @throws Exception
	 */
	public void saveConvertLog(SysFileConvertLog convertLog) throws Exception {
		if (convertLog != null) {
			Boolean insertData = true;
			HQLInfo hql = new HQLInfo();
		//	hql.setSelectBlock("fdId, fdStatusInfo");
			hql.setWhereBlock("fdQueueId=:queueId");
			hql.setParameter("queueId", convertLog.getFdQueueId());
			hql.setPageNo(1);
			hql.setRowSize(10);
			hql.setCheckParam(SysAuthConstant.CheckType.AuthCheck, SysAuthConstant.AuthCheck.SYS_NONE);
			List<SysFileConvertLog> sysFileConvertLogList = new ArrayList<>();
			Page page = getLogService().findPage(hql);
			if(page != null) {
				sysFileConvertLogList = page.getList();
			}

			if (sysFileConvertLogList != null && !sysFileConvertLogList.isEmpty()) {
				String convertLogMd5 = SysFileStoreUtil.getMD5(convertLog.getFdStatusInfo());
				for (SysFileConvertLog sysFileConvertLog : sysFileConvertLogList) {
					String logDescript = sysFileConvertLog.getFdStatusInfo();
					String logDescriptMd5 = SysFileStoreUtil.getMD5(logDescript);
					// 同样的日志记录只需更新时间
					if (convertLogMd5.equals(logDescriptMd5)) {
						sysFileConvertLog.setFdStatusTime(new Date());
						getLogService().update(sysFileConvertLog);
						insertData = false;
						break;
					}
				}
			}
			
            if(insertData) {
            	getLogService().add(convertLog);
            	
            }
		}
		
	}
	
	
	
}
