package com.landray.kmss.fssc.budget.event;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.landray.kmss.fssc.config.model.FsscConfigScore;
import com.landray.kmss.fssc.config.model.FsscConfigScoreDetail;
import com.landray.kmss.fssc.config.service.IFsscConfigScoreService;
import com.landray.kmss.fssc.config.util.FormDataUtil;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.metadata.model.ExtendDataModelInfo;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * H27起草人提交事件,同步点赞积分
 * @author wangjinman
 *
 */
public class KmReviewJfSubmitEvent implements IEventListener{
	private IFsscConfigScoreService fsscConfigScoreService;
	
	public IFsscConfigScoreService getFsscConfigScoreService() {
		if (fsscConfigScoreService == null) {
			fsscConfigScoreService = (IFsscConfigScoreService) SpringBeanUtil.getBean("fsscConfigScoreService");
        }
		return fsscConfigScoreService;
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
			KmReviewMain main=(KmReviewMain)execution.getMainModel();
			ExtendDataModelInfo extendDataModelInfo = main.getExtendDataModelInfo();
			Map<String, Object> map=extendDataModelInfo.getModelData();

			String fdScoreId= (String) FormDataUtil.getValueByKey(map, "fdScoreId");
			FsscConfigScore fsscConfigScore=(FsscConfigScore) getFsscConfigScoreService().findByPrimaryKey(fdScoreId);
			List<FsscConfigScoreDetail> fdDetail=fsscConfigScore.getFdDetail();
			
			List<Map<String, Object>> details=FormDataUtil.getDetailByKey(map, "fdDetail");
			if(details!=null&&details.size()>0){
				for (Map<String, Object> detail : details) {
					FsscConfigScoreDetail fsscConfigScoreDetail=new FsscConfigScoreDetail();
					String fdId=IDGenerator.generateID();
					fsscConfigScoreDetail.setFdId(fdId);
					fsscConfigScoreDetail.setDocMain(fsscConfigScore);
					//点赞人
					Map<String, Object> fdAddScorePerson=(Map<String, Object>) FormDataUtil.getValueByKey(detail, "fdAddScorePerson");
					String fdAddScorePersonId=(String) fdAddScorePerson.get("id");
					fsscConfigScoreDetail.setFdAddScorePerson((SysOrgPerson)getSysOrgPersonService().findByPrimaryKey(fdAddScorePersonId));
					//点赞分数
					double fdScoreUse=(double) FormDataUtil.getValueByKey(detail, "fdScoreUse");
					fsscConfigScoreDetail.setFdScoreUse((int)fdScoreUse);
					//点赞说明
					String fdDesc=(String) FormDataUtil.getValueByKey(detail, "fdDesc");
					fsscConfigScoreDetail.setFdDesc(fdDesc);
					fsscConfigScoreDetail.setDocCreateTime(new Date()); 
					fsscConfigScoreDetail.setDocCreator(main.getDocCreator());
					fsscConfigScoreDetail.setFdModelId(main.getFdId());
					fsscConfigScoreDetail.setFdModelName(KmReviewMain.class.getName());
					fdDetail.add(fsscConfigScoreDetail);
				}
			}
			getFsscConfigScoreService().update(fsscConfigScore);
			
		}
	}
}
