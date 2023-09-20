package com.landray.kmss.km.forum.service.spring;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.km.forum.interfaces.IKmForumScoreCommunicationChangeEvent;
import com.landray.kmss.km.forum.model.KmForumScore;
import com.landray.kmss.km.forum.service.IKmForumScoreService;
import com.landray.kmss.plugins.interfaces.IKmssScoreInfoBean;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.util.ResourceUtil;

/**
 * 创建日期 2006-Sep-07
 * 
 * @author 吴兵 用户积分业务接口实现
 */
public class KmForumScoreServiceImp extends BaseServiceImp implements
		IKmForumScoreService, ApplicationListener, ApplicationContextAware,
		IKmssScoreInfoBean {
	private ApplicationContext applicationContext;

	private ISysAttMainCoreInnerService sysAttMainService;

	public void setSysAttMainService(
			ISysAttMainCoreInnerService sysAttMainService) {
		this.sysAttMainService = sysAttMainService;
	}

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(KmForumScoreServiceImp.class);

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		String rtnVal = super.add(modelObj);
		applicationContext
				.publishEvent(new KmForumScoreCommunicationChangeEvent(
						(KmForumScore) modelObj));
		return rtnVal;
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event == null
				|| (event instanceof KmForumScoreCommunicationChangeEvent)
				|| !(event instanceof IKmForumScoreCommunicationChangeEvent)) {
            return;
        }
		if (logger.isDebugEnabled()) {
            logger.debug("处理事件通知：" + event.getClass().getName());
        }
		try {
			IKmForumScoreCommunicationChangeEvent scoreEvent = (IKmForumScoreCommunicationChangeEvent) event;
			KmForumScore score = (KmForumScore) findByPrimaryKey(scoreEvent
					.getKmForumScoreId());
			score.setFdNickName(scoreEvent.getKmForumScoreNickName());
			score.setFdSign(scoreEvent.getKmForumScoreSign());
			getBaseDao().update(score);
		} catch (Exception e) {
			throw new KmssRuntimeException(e);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public String getScoreInfoTitle() {
		return ResourceUtil.getString("home.nav.kmForum", "km-forum");
	}

	@Override
	public String getScoreInfoValue(String id) {
		try {
			KmForumScore score = (KmForumScore) findByPrimaryKey(id, null, true);
			if (score != null) {
				return "" + score.getFdScore();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "0";
	}

	@Override
	public String getScoreInfoLink(String id) {
		return "/km/forum/km_forum_score/kmForumScore.do?method=view&fdId="
				+ id;
	}

	@Override
	public void updateAllUserAvatar(RequestContext requestContext)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		StringBuffer whereBlock = new StringBuffer();
		whereBlock
				.append("sysAttMain.fdModelName = 'com.landray.kmss.km.forum.model.KmForumScore' ");
		whereBlock.append("and sysAttMain.fdKey = 'spic'");
		hqlInfo.setWhereBlock(whereBlock.toString());
		hqlInfo.setSelectBlock("sysAttMain.fdId");
		List sysAttMainIds = sysAttMainService.findValue(hqlInfo);
		if (logger.isDebugEnabled()) {
            logger.debug("开始更新压缩" + sysAttMainIds.size() + "张头像！");
        }

		List sysAttMains = sysAttMainService.findModelsByIds((String[]) (sysAttMainIds
				.toArray(new String[sysAttMainIds.size()])));

		for (Object tmpSysAtt : sysAttMains) {
			SysAttMain sysAttMain = (SysAttMain) tmpSysAtt;
			sysAttMain.setWidth(250);// 设置图片宽度为250
			sysAttMain.setHeight(250);// 设置图片高度为250
			sysAttMainService.update(sysAttMain);// 更新压缩
			// 添加日志信息
			if (UserOperHelper.allowLogOper("updateAllUserAvatar",
					getModelName())) {
				UserOperContentHelper.putUpdate(sysAttMain);
			}
		}
	}
}
