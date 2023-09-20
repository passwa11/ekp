package com.landray.kmss.sys.oms.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.oms.model.*;
import com.landray.kmss.sys.oms.service.ISysOmsTempTrxSynFailService;
import com.landray.kmss.sys.oms.temp.*;
import com.landray.kmss.sys.oms.util.SysOmsUtil;
import com.landray.kmss.sys.organization.model.SysOrgDept;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SysOmsTempTrxSynFailServiceImp extends SysOmsTempTrxBaseServiceImp implements ISysOmsTempTrxSynFailService{
	private Logger logger = org.slf4j.LoggerFactory.getLogger(SysOmsTempTrxSynFailServiceImp.class);
    private ISysNotifyMainCoreService sysNotifyMainCoreService;
    protected OmsTempSyncThreadExecutor omsTempSyncThreadExecutor;
    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof SysOmsTempTrx) {
            SysOmsTempTrx sysOmsTempTrx = (SysOmsTempTrx) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        SysOmsTempTrx sysOmsTempTrx = new SysOmsTempTrx();
        sysOmsTempTrx.setBeginTime(new Date());
        SysOmsUtil.initModelFromRequest(sysOmsTempTrx, requestContext);
        return sysOmsTempTrx;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        SysOmsTempTrx sysOmsTempTrx = (SysOmsTempTrx) model;
    }

    @Override
    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
    
    /**
	 * 加载数据
	 *	1、 加载临时表数据  2、加载EKP组织架构id
	 * @param tempTrx
	 * @return
	 */
    @Override
	protected SysOmsTempData loadDatas(SysOmsTempTrx tempTrx,SyncLog log) throws Exception{
		SysOmsTempData sysOmsTempData = new SysOmsTempData(tempTrx);
		int fdSynModel = tempTrx.getFdSynModel();
		String fdTrxId = tempTrx.getFdId();
		Map<String, SysOmsTempPerson> tempPersonMap = new HashMap<String, SysOmsTempPerson>();
		Map<String, SysOmsTempDept> tempDeptMap = new HashMap<String, SysOmsTempDept>();
		Map<String, SysOmsTempPost> tempPostMap = new HashMap<String, SysOmsTempPost>();
		long starttime = System.currentTimeMillis();
		log.info("开始加载数据...");
		
		//部门
		List<SysOmsTempDept> tempDeptList = sysOmsTempDeptService.findFailListByTrxId(fdTrxId);
		sysOmsTempData.setTempDeptList(tempDeptList);
		for (SysOmsTempDept sysOmsTempDept : tempDeptList) {
			tempDeptMap.put(sysOmsTempDept.getFdDeptId(), sysOmsTempDept);
			sysOmsTempDept.setFdStatus(SysOmsTempConstants.SYS_OMS_TEMP_DATA_SYN_STATUS_SUCCESS);
			sysOmsTempDept.setFdFailReason(null);
		}
		sysOmsTempData.setTempDeptMap(tempDeptMap);
	
		//人员
		List<SysOmsTempPerson> tempPersonList = sysOmsTempPersonService.findFailListByTrxId(fdTrxId);
		sysOmsTempData.setTempPersonList(tempPersonList);
		for (SysOmsTempPerson sysOmsTempPerson : tempPersonList) {
			tempPersonMap.put(sysOmsTempPerson.getFdPersonId(), sysOmsTempPerson);
			sysOmsTempPerson.setFdStatus(SysOmsTempConstants.SYS_OMS_TEMP_DATA_SYN_STATUS_SUCCESS);
			sysOmsTempPerson.setFdFailReason(null);
		}
		sysOmsTempData.setTempPersonMap(tempPersonMap);
		
		if(fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_300.getValue()
				|| fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_400.getValue()
				|| fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_30.getValue()){
			//岗位
			List<SysOmsTempPost> tempPostList = sysOmsTempPostService.findFailListByTrxId(fdTrxId);
			sysOmsTempData.setTempPostList(tempPostList);
			for (SysOmsTempPost sysOmsTempPost : tempPostList) {
				tempPostMap.put(sysOmsTempPost.getFdPostId(), sysOmsTempPost);
				sysOmsTempPost.setFdStatus(SysOmsTempConstants.SYS_OMS_TEMP_DATA_SYN_STATUS_SUCCESS);
				sysOmsTempPost.setFdFailReason(null);
			}
			sysOmsTempData.setTempPostMap(tempPostMap);
			
			//岗位人员关系
			List<SysOmsTempPp> tempPpList = sysOmsTempPpService.findListByTrxId(fdTrxId);
			sysOmsTempData.setTempPpList(tempPpList);
		}
		
		if(fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_200.getValue()
				|| fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_400.getValue()
				|| fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_20.getValue()
				|| fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_21.getValue()
				|| fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_40.getValue()){
			//部门人员关系
			List<SysOmsTempDp> tempDpList = sysOmsTempDpService.findListByTrxId(fdTrxId);
			sysOmsTempData.setTempDpList(tempDpList);
		}
		
		//加载EKP组织架构数据
		this.loadEKPOrgData(sysOmsTempData);
	
		StringBuffer logStr = new StringBuffer();
		logStr.append("总共获取部门："+sysOmsTempData.getTempDeptList().size()+"个，");
		logStr.append("岗位："+sysOmsTempData.getTempPostList().size()+"个，");
		logStr.append("人员："+sysOmsTempData.getTempPersonList().size()+"个，");
		logStr.append("岗位人员关系："+sysOmsTempData.getTempPpList().size()+"个，");
		logStr.append("部门人员关系："+sysOmsTempData.getTempDpList().size()+"个");
		log.info(logStr.toString());
		log.info("加载数据结束，总共耗时："+(System.currentTimeMillis()-starttime)+"ms");
		
		return sysOmsTempData;
	}

	@Override
	public void doSync(SysOmsTempTrx tempTrx, OmsTempSynResult<Object> result, SyncLog log) throws Exception {
		 log.info("==============重新同步日志===============");
		 super.doSync(tempTrx, result, log);
	 	 if(result.getCode() == SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL){
			updateTrxStatus(tempTrx,SysOmsTempConstants.SYS_OMS_TEMP_SYN_TRX_END_FAIL);
			handFailData(tempTrx.getFdId());
		 }else if(StringUtil.isNull(log.getFdLogError())) {
			updateTrxStatus(tempTrx,SysOmsTempConstants.SYS_OMS_TEMP_SYN_TRX_END_SUCCESS);
		 }else {
			updateTrxStatus(tempTrx,SysOmsTempConstants.SYS_OMS_TEMP_SYN_TRX_END_WARN);
		 }
	 	 
	 	 log.info("同步事务（事务ID："+tempTrx.getFdId()+"）："+result.getMsg());
		 log.info(result.getIllegalDataMsg(SyncLog.NEWLINE_CHAR_RTF));
		
		 saveSynLog(tempTrx, log);
	}

	@Override
	public void syncDept(SysOmsTempDept sysOmsTempDept) throws Exception {
		StringBuffer logStr = new StringBuffer();
		String title = "修改部门";
		TransactionStatus status = null;
		try {
			status = TransactionUtils.beginNewTransaction();
			boolean isAdd = false;
			SysOmsTempTrx tempTrx = this.findByPrimaryKey(sysOmsTempDept.getFdTrxId());
			SysOmsSynConfig synConfig = SysOmsTempUtil.getSynConfig(tempTrx.getFdSynConfigJson());
			SysOrgDept sysOrgDept = findOrgDeptByImportInfo(sysOmsTempDept.getFdDeptId(),null);
			if(sysOrgDept == null){
				isAdd = true;
				sysOrgDept = new SysOrgDept();
				title = "新增部门：";
			}
			logStr.append(title);
			logStr.append(sysOmsTempDept.getFdName());
			logStr.append("，部门ID：" + sysOmsTempDept.getFdDeptId());
			logStr.append("，上级部门ID：" +sysOmsTempDept.getFdParentid());
			if(StringUtil.isNotNull(sysOmsTempDept.getFdParentid())){
				SysOrgDept parent = findOrgDeptByImportInfo(sysOmsTempDept.getFdParentid(),null);
				if(parent == null){
					logStr.append("，失败：找不到上级部门");
					TransactionUtils.commit(status);
					return;
				}
				sysOrgDept.setFdParent(parent);
				logStr.append("，上级部门名称："+parent.getFdName());
			}else{
				sysOrgDept.setFdParent(null);
			}
			sysOrgDept.setFdName(sysOmsTempDept.getFdName());
			sysOrgDept.setFdCreateTime(new Date());
			sysOrgDept.setFdImportInfo(sysOmsTempDept.getFdDeptId());
			sysOrgDept.setFdIsAvailable(sysOmsTempDept.getFdIsAvailable());
			sysOrgDept.setFdOrder(convertOrder(sysOmsTempDept.getFdOrder(),synConfig.getFdDeptIsAsc()));
			if(isAdd){
				sysOrgDeptService.add(sysOrgDept);
			}else {
				sysOrgDeptService.update(sysOrgDept);
			}
			logStr.append("，成功");
			logger.info(logStr.toString());
			sysOmsTempDept.setFdStatus(SysOmsTempConstants.SYS_OMS_TEMP_DATA_SYN_STATUS_SUCCESS);
			sysOmsTempDept.setFdFailReason(null);
			sysOmsTempDeptService.update(sysOmsTempDept);
			TransactionUtils.commit(status);
		} catch (Exception e) {
			logStr.append("，失败："+e.getMessage());
			logger.error(logStr.toString(),e);
			if (status != null) {
				try {
					TransactionUtils.rollback(status);
				} catch (Exception ex) {
					logger.error("---事务回滚出错：{0}---", ex);
				}
			}
			throw e;
		}

		
	}

	@Override
	public void syncPost(SysOmsTempPost sysOmsTempPost) throws Exception {
		StringBuffer logStr = new StringBuffer();
		String title = "修改岗位";
		TransactionStatus status = null;
		try {
			status = TransactionUtils.beginNewTransaction();
			boolean isAdd = false;
			SysOrgPost sysOrgPost = findOrgPostByImportInfo(sysOmsTempPost.getFdPostId(),null);
			if(sysOrgPost == null){
				isAdd = true;
				title = "新增岗位";
				sysOrgPost = new SysOrgPost();
			}
			logStr.append(title);
			logStr.append(sysOmsTempPost.getFdName());
			logStr.append("，岗位ID：" + sysOmsTempPost.getFdPostId());
			logStr.append("，所属部门ID：" +sysOmsTempPost.getFdParentid());
			if(StringUtil.isNotNull(sysOmsTempPost.getFdParentid())){
				SysOrgDept parent = findOrgDeptByImportInfo(sysOmsTempPost.getFdParentid(),null);
				if(parent == null){
					logStr.append("，失败：找不到所属部门");
					logger.warn(logStr.toString());
					return;
				}
				sysOrgPost.setFdParent(parent);
				logStr.append("，所属部门名称："+parent.getFdName());
			}else{
				sysOrgPost.setFdParent(null);
			}
			sysOrgPost.setFdIsAvailable(sysOmsTempPost.getFdIsAvailable());
			sysOrgPost.setFdName(sysOmsTempPost.getFdName());
			sysOrgPost.setFdCreateTime(new Date());
			sysOrgPost.setFdImportInfo(sysOmsTempPost.getFdPostId());
			sysOrgPost.setFdIsAvailable(true);
			sysOrgPost.setFdOrder(sysOmsTempPost.getFdOrder());	
			if(isAdd) {
				sysOrgPostService.add(sysOrgPost);
			}else {
				sysOrgPostService.update(sysOrgPost);
			}
			sysOmsTempPost.setFdStatus(SysOmsTempConstants.SYS_OMS_TEMP_DATA_SYN_STATUS_SUCCESS);
			sysOmsTempPost.setFdFailReason(null);
			sysOmsTempPostService.update(sysOmsTempPost);
			logStr.append("，成功");
			logger.info(logStr.toString());
			TransactionUtils.commit(status);
		} catch (Exception e) {
			logStr.append("，失败："+e.getMessage());
			logger.error(logStr.toString(),e);
			if (status != null) {
				try {
					TransactionUtils.rollback(status);
				} catch (Exception ex) {
					logger.error("---事务回滚出错：{0}---", ex);
				}
			}
			throw e;
		}
	}

	@Override
	public void syncPerson(SysOmsTempPerson sysOmsTempPerson) throws Exception {
	
		StringBuffer logStr = new StringBuffer();
		String title = "修改人员";
		boolean isAdd = false;
		SysOmsTempTrx tempTrx = this.findByPrimaryKey(sysOmsTempPerson.getFdTrxId());
		
		TransactionStatus status = null;
		try {
			status = TransactionUtils.beginNewTransaction();
			List<SysOmsTempDp> dpList = sysOmsTempDpService.findListByTrxId(tempTrx.getFdId());
			List<SysOmsTempPp> ppList = sysOmsTempPpService.findListByTrxId(tempTrx.getFdId());
			sysOmsTempPerson.setPostIdList(ppList);
			sysOmsTempPerson.setDeptIdList(dpList);
			SysOmsSynConfig synConfig = SysOmsTempUtil.getSynConfig(tempTrx.getFdSynConfigJson());
			int fdSynModel = tempTrx.getFdSynModel();
			SysOrgPerson sysOrgPerson = findOrgPersonByImportInfo(sysOmsTempPerson.getFdPersonId(),null);
			if(sysOrgPerson == null){
				sysOrgPerson = new SysOrgPerson();
				isAdd = true;
				title = "新增人员";
			}
			
			logStr.append(title);
			logStr.append(sysOmsTempPerson.getFdName());
			logStr.append("，人员ID：" + sysOmsTempPerson.getFdPersonId());
			logStr.append("，所属部门ID：" +sysOmsTempPerson.getFdParentid());
			
			sysOrgPerson.setFdIsAvailable(sysOmsTempPerson.getFdIsAvailable());
			
			//人员所属主部门
			handPersonMainDept(sysOmsTempPerson,sysOrgPerson,logStr,null);
			
			//人员基本信息
			handPersonBaseInfo(sysOmsTempPerson,sysOrgPerson,synConfig);
			
			//扩展字段
			handPersonExtra(sysOmsTempPerson,sysOrgPerson);
			
			//部门人员关系/岗位人员关系
			handPersonDeptAndPost(sysOmsTempPerson,sysOrgPerson,fdSynModel,synConfig,null);
			if(isAdd) {
				sysOrgPersonService.add(sysOrgPerson);
			}else {
				sysOrgPersonService.update(sysOrgPerson);
			}
			sysOmsTempPerson.setFdStatus(SysOmsTempConstants.SYS_OMS_TEMP_DATA_SYN_STATUS_SUCCESS);
			sysOmsTempPerson.setFdFailReason(null);
			sysOmsTempPersonService.update(sysOmsTempPerson);
			logStr.append("，成功");
			logger.info(logStr.toString());
			TransactionUtils.commit(status);
		} catch (Exception e) {
			logStr.append("，失败："+e.getMessage());
			logger.error(logStr.toString(),e);
			if (status != null) {
				try {
					TransactionUtils.rollback(status);
				} catch (Exception ex) {
					logger.error("---事务回滚出错：{0}---", ex);
				}
			}
			throw e;
		}
	}

	
}
