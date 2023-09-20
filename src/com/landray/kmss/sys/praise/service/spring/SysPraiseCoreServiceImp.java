package com.landray.kmss.sys.praise.service.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseCoreOuterServiceImp;
import com.landray.kmss.sys.praise.interfaces.ISysPraiseMain;
import com.landray.kmss.sys.praise.service.ISysPraiseCoreService;
import com.landray.kmss.sys.praise.service.ISysPraiseMainService;

public class SysPraiseCoreServiceImp extends BaseCoreOuterServiceImp
				implements ISysPraiseCoreService {
	private ISysPraiseMainService sysPraiseMainService = null;
	public void setSysPraiseMainService(
			ISysPraiseMainService sysPraiseMainService) {
		this.sysPraiseMainService = sysPraiseMainService;
	}
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysPraiseCoreServiceImp.class);
	
	@Override
	public void delete(IBaseModel model) throws Exception {
		if (!(model instanceof ISysPraiseMain)) {
            return;
        }
		if (logger.isDebugEnabled()) {
            logger.debug("删除相关的点赞记录");
        }
		sysPraiseMainService.deleteCoreModels(model);
	}
}
