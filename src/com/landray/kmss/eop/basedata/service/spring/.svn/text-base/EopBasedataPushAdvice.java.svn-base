package com.landray.kmss.eop.basedata.service.spring;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;

import com.landray.kmss.eop.basedata.service.IEopBasedataPullAndPushService;
import com.landray.kmss.eop.basedata.service.IEopBasedataPullAndPushService.DataAction;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;

/**
*@author yucf
*@date  2021-1-18
*@Description                  基础数据新建/更新/删除拦截处理，同步推送处理
**/

public class EopBasedataPushAdvice implements AfterReturningAdvice {
	
	private Logger logger = LoggerFactory.getLogger(EopBasedataPushAdvice.class);

	@Override
	public void afterReturning(Object rtnVal, Method method, Object[] args, Object target) throws Throwable {
		
		String methodName = method.getName();
		
		if(args != null && args.length > 0) {
			
			//可能是个代理类
			String clzName = args[0].getClass().getName().split("\\$")[0];
			DataAction dataAction = DataAction.getAction(methodName);
			
//			AopUtils.getTargetClass(args[0]).getName();
			
			logger.info("clzName:{}", clzName);
			
			if(dataAction == null) {
				logger.info("动作不匹配，不处理！");
				return;
			}
			
			List<IEopBasedataPullAndPushService> ppServiceList = EopBasedataUtil.getPullAndPushService(clzName);
			if(CollectionUtils.isEmpty(ppServiceList)) {
				return;
			}
			
			for(IEopBasedataPullAndPushService ppService : ppServiceList) {
				ppService.asyncData2BizMod(dataAction, args[0]);
			}
		}
	}
}