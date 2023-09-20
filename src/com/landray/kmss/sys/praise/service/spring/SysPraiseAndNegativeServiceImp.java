package com.landray.kmss.sys.praise.service.spring;

import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.service.BaseCoreInnerServiceImp;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.praise.interfaces.ISysPraiseAndNegativeService;
import com.landray.kmss.sys.praise.model.SysPraiseMain;
import com.landray.kmss.sys.praise.service.ISysPraiseMainService;

public class SysPraiseAndNegativeServiceImp extends  BaseCoreInnerServiceImp implements ISysPraiseAndNegativeService{

	private ISysPraiseMainService sysPraiseMainService ;
	

	public void setSysPraiseMainService(ISysPraiseMainService sysPraiseMainService) {
		this.sysPraiseMainService = sysPraiseMainService;
	}

	@Override
	public void addOrDel(String fdModelId, String fdModelName, String fdType,
			RequestContext requestContext) throws Exception {
		sysPraiseMainService.addOrDel(fdModelId, fdModelName, fdType, requestContext);
		
	}

	@Override
	public List<SysPraiseMain> checkPraiseAndNegativeByIds(String fdId,
			String fdModelIds, String fdModelName) throws Exception {
		return sysPraiseMainService.checkPraiseAndNegativeByIds(fdId, fdModelIds, fdModelName);
	}

	@Override
	public Boolean checkPraised(String praiserId, String fdModelId,
			String fdModelName, String fdType) throws Exception {
		return sysPraiseMainService.checkPraised(praiserId, fdModelId, fdModelName, fdType);
	}

	@Override
	public List checkPraisedByIds(String praiserId, String fdModelIds,
			String fdModelName) throws Exception {
		return sysPraiseMainService.checkPraisedByIds(praiserId, fdModelIds, fdModelName);
	}

	@Override
	public Integer getPraiseCount(String fdModelId, String fdModelName,
			String fdType) throws Exception {
		return sysPraiseMainService.getPraiseCount(fdModelId, fdModelName, fdType);
	}

	@Override
	public String getPraiseId(String praiserId, String fdModelId,
			String fdModelName, String fdType) throws Exception {
		return sysPraiseMainService.getPraiseId(praiserId, fdModelId, fdModelName, fdType);
	}

	@Override
	public void updatePraiseCount(IBaseService service, BaseModel docbase,
			String string, String fdType) throws Exception {
		sysPraiseMainService.updatePraiseCount(service, docbase, string, fdType);
	}

}
