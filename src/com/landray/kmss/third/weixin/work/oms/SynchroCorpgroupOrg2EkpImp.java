package com.landray.kmss.third.weixin.work.oms;

import com.landray.kmss.component.locker.interfaces.IComponentLockService;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.weixin.work.api.CorpGroupAppShareInfo;
import com.landray.kmss.third.weixin.work.api.WxworkApiService;
import com.landray.kmss.third.weixin.work.constant.WxworkConstant;
import com.landray.kmss.third.weixin.work.model.WeixinWorkConfig;
import com.landray.kmss.third.weixin.work.util.WxworkUtils;
import com.landray.kmss.util.SpringBeanUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;


public class SynchroCorpgroupOrg2EkpImp
		implements SynchroCorpgroupOrg2Ekp, WxworkConstant, SysOrgConstant {

	private static final Log logger = LogFactory
			.getLog(SynchroCorpgroupOrg2EkpImp.class);

	private SysQuartzJobContext jobContext = null;

	private IComponentLockService componentLockService = null;

	private IComponentLockService getComponentLockService() {
		if (componentLockService == null) {
			componentLockService = (IComponentLockService) SpringBeanUtil
					.getBean("componentLockService");
		}
		return componentLockService;
	}

	@Override
	public void triggerSynchro(SysQuartzJobContext context){
		String temp = "存在运行中的企业微信上下游组织架构同步任务，当前任务中断...";
		this.jobContext = context;
		WxSynchroInModel model = new WxSynchroInModel();
		try {
			if (!"true".equals(WeixinWorkConfig.newInstance().getWxEnabled())) {
				logger.info("企业微信集成已经关闭，故不同步下游组织数据");
				context.logMessage("企业微信集成已经关闭，故不同步下游组织数据");
				return;
			}
			if (!"true".equals(WeixinWorkConfig.newInstance().getCorpGroupIntegrateEnable())) {
				logger.info("企业微信上下游集成功能已经关闭，故不同步下游组织数据");
				context.logMessage("企业微信上下游集成功能已经关闭，故不同步下游组织数据");
				return;
			}
			if (!"true".equals(WeixinWorkConfig.newInstance().getSyncCorpGroupOrgEnable())) {
				logger.info("上下游组织数据自动同步功能已经关闭，故不同步下游组织数据");
				context.logMessage("上下游组织数据自动同步功能已经关闭，故不同步下游组织数据");
				return;
			}
			getComponentLockService().tryLock(model, "corpgroupSyncIn");
			doSynchro();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			getComponentLockService().unLock(model);
		}
	}


	public void doSynchro() throws Exception {
		jobContext.logMessage("开始同步上下游组织");
		Long start = System.currentTimeMillis();
		WxworkApiService wxworkApiService = WxworkUtils.getWxworkApiService();
		wxworkApiService.resetAppShareInfoMap();
		//获取应用共享信息
		Map<String, CorpGroupAppShareInfo> appShareInfoMap = wxworkApiService.getAppShareInfoMap();
		jobContext.logMessage("有"+appShareInfoMap.size()+"个下游组织需要同步");
		for(String corpId:appShareInfoMap.keySet()){
			CorpGroupAppShareInfo shareInfo = appShareInfoMap.get(corpId);
			new CorpgroupOrg2EkpSynchronizer(shareInfo,jobContext).doSynchro();
		}
		jobContext.logMessage("所有上下游组织完成，耗时："+(System.currentTimeMillis()-start)+"毫秒");
	}



}
