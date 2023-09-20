package com.landray.kmss.third.wechat.service.spring;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.third.wechat.actions.WechatLoginHelperAction;
import com.landray.kmss.third.wechat.model.WechatConfig;
import com.landray.kmss.third.wechat.service.IWechatConfigService;

/**
 * 新 类0业务接口实现
 * 
 * @author 
 * @version 1.0 2014-05-08
 */
public class WechatConfigServiceImp extends BaseServiceImp implements IWechatConfigService {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(WechatConfigServiceImp.class);
	
	@Override
    public List<WechatConfig> findWechatConfigWithCondition(String param){
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo
		.setWhereBlock("wechatConfig.fdEkpid=:fdEkpid");
		hqlInfo.setParameter("fdEkpid", param);
		List<WechatConfig> result = new ArrayList<WechatConfig>();
		try {
			result=this.findList(hqlInfo);
		} catch (Exception e) {
			logger.error("WechatConfigServiceImp.findWechatConfigWithCondition,异常信息:"+e.getMessage());
		}
		return result;
	}

	
}
