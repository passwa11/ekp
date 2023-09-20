package com.landray.kmss.fssc.budget.event;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.fssc.config.model.FsscConfigScoreDetail;
import com.landray.kmss.fssc.config.service.IFsscConfigScoreDetailService;
import com.landray.kmss.fssc.config.service.IFsscConfigScoreService;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * H27起草人驳回删除数据关联
 * @author wangjinman
 *
 */
public class KmReviewJfRefushEvent implements IEventListener{
	private IFsscConfigScoreDetailService fsscConfigScoreDetailService;
	
	public IFsscConfigScoreDetailService getFsscConfigScoreDetailService() {
		if (fsscConfigScoreDetailService == null) {
			fsscConfigScoreDetailService = (IFsscConfigScoreDetailService) SpringBeanUtil.getBean("fsscConfigScoreDetailService");
        }
		return fsscConfigScoreDetailService;
	}
	
	private ISysOrgPersonService sysOrgPersonService;
	    
	    public ISysOrgPersonService getSysOrgPersonService() {
	    	if(sysOrgPersonService==null){
	    		sysOrgPersonService=(ISysOrgPersonService) SpringBeanUtil.getBean("sysOrgPersonService");
	    	}
			return sysOrgPersonService;
		}
	
	@Override
	public void handleEvent(EventExecutionContext execution, String parameter) throws Exception {
		if (execution.getMainModel() instanceof KmReviewMain) {
			KmReviewMain main=(KmReviewMain) execution.getMainModel();
			String fdModelId=main.getFdId();
			HQLInfo hqlInfo=new HQLInfo();
			hqlInfo.setWhereBlock("fdModelId=:fdModelId");
			hqlInfo.setParameter("fdModelId", fdModelId);
			List<FsscConfigScoreDetail> fsscConfigScoreDetails=getFsscConfigScoreDetailService().findList(hqlInfo);
			for (FsscConfigScoreDetail fsscConfigScoreDetail : fsscConfigScoreDetails) {
				getFsscConfigScoreDetailService().delete(fsscConfigScoreDetail);
			}
			System.out.println("废弃删除");
		}
	}
}
