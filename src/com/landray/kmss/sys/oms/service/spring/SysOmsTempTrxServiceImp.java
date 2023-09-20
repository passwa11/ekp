package com.landray.kmss.sys.oms.service.spring;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.CacheMode;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.HibernateException;
import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.oms.model.SysOmsTempDept;
import com.landray.kmss.sys.oms.model.SysOmsTempDp;
import com.landray.kmss.sys.oms.model.SysOmsTempPerson;
import com.landray.kmss.sys.oms.model.SysOmsTempPost;
import com.landray.kmss.sys.oms.model.SysOmsTempPp;
import com.landray.kmss.sys.oms.model.SysOmsTempTrx;
import com.landray.kmss.sys.oms.service.ISysOmsTempDeptService;
import com.landray.kmss.sys.oms.service.ISysOmsTempDpService;
import com.landray.kmss.sys.oms.service.ISysOmsTempPersonService;
import com.landray.kmss.sys.oms.service.ISysOmsTempPostService;
import com.landray.kmss.sys.oms.service.ISysOmsTempPpService;
import com.landray.kmss.sys.oms.service.ISysOmsTempTrxService;
import com.landray.kmss.sys.oms.temp.ISysOmsThreadSynchService;
import com.landray.kmss.sys.oms.temp.OmsTempSynFailType;
import com.landray.kmss.sys.oms.temp.OmsTempSynModel;
import com.landray.kmss.sys.oms.temp.OmsTempSynResult;
import com.landray.kmss.sys.oms.temp.OmsTempSyncThreadExecutor;
import com.landray.kmss.sys.oms.temp.SyncLog;
import com.landray.kmss.sys.oms.temp.SysOmsExcelUtil;
import com.landray.kmss.sys.oms.temp.SysOmsTempConstants;
import com.landray.kmss.sys.oms.temp.SysOmsTempData;
import com.landray.kmss.sys.oms.util.SysOmsUtil;
import com.landray.kmss.sys.organization.model.SysOrgDept;
import com.landray.kmss.sys.organization.model.SysOrgDeptPersonRelation;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.service.ISysOrgDeptPersonRelationService;
import com.landray.kmss.sys.organization.service.ISysOrgDeptService;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.service.ISysOrgPostService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;

import net.sf.json.JSONObject;

/**
 * 该类只修复bug，不迭代新功能，新功能迭代在SysOmsTempTrxBaseServiceImp类
 * @author yuliang
 *
 */
public class SysOmsTempTrxServiceImp extends ExtendDataServiceImp implements ISysOmsTempTrxService,
ISysOmsThreadSynchService,SysOrgConstant ,SysOmsTempConstants{
	private Logger logger = org.slf4j.LoggerFactory.getLogger(SysOmsTempTrxServiceImp.class);
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

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
    private ISysOmsTempDeptService sysOmsTempDeptService;
    private ISysOmsTempPersonService sysOmsTempPersonService;
    private ISysOmsTempPostService sysOmsTempPostService;
    private ISysOmsTempPpService sysOmsTempPpService;
    private ISysOmsTempDpService sysOmsTempDpService;
    private ISysOrgElementService sysOrgElementService;
    private ISysOrgDeptService sysOrgDeptService;
    private ISysOrgPostService sysOrgPostService;
    private ISysOrgPersonService sysOrgPersonService;
    private ISysOrgDeptPersonRelationService sysOrgDeptPersonRelationService;

    /**
     * @param fdSynModel 枚举 同步模式
     * @param fdDeptIsAsc 部门排序号是否正序
     * @param fdPersonIsAsc 人员排序号是否正序
     */
	@Override
	public OmsTempSynResult<Object> begin(OmsTempSynModel fdSynModel,boolean fdDeptIsAsc,boolean fdPersonIsAsc) {
		OmsTempSynResult<Object> result = new OmsTempSynResult<Object>();
		result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL);
		try {
			SysOmsTempTrx trx = new SysOmsTempTrx();
			trx.setBeginTime(new Date());
			trx.setFdSynModel(fdSynModel.getValue());
			trx.setFdSynStatus(SysOmsTempConstants.SYS_OMS_TEMP_SYN_TRX_RUNNING);
			trx.setFdDeptIsAsc(fdDeptIsAsc);
			trx.setFdPersonIsAsc(fdPersonIsAsc);
			super.add(trx);

			//本次同步请求发起结果
			result.setTrxId(trx.getFdId());
			result.setMsg("成功");
			result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_SUCCESS);
		}  catch (Exception e){
			logger.error("开始失败",e);
			result.setMsg(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 是否存在进行中的事务
	 * @param trxId
	 * @return
	 * @throws Exception
	 */
	private boolean isExistRunningTrx(SysOmsTempTrx tempTrx) throws Exception{
		if(tempTrx != null && tempTrx.getEndTime() == null 
				&& tempTrx.getFdSynStatus() == SysOmsTempConstants.SYS_OMS_TEMP_SYN_TRX_RUNNING){
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * 同步失败，修改同步事务状态
	 * @param trxId
	 * @param errMsg
	 */
	private void updateFailTrx(SysOmsTempTrx tempTrx){
		try {
			if(tempTrx == null) {
                return;
            }
			
			tempTrx.setEndTime(new Date());
			tempTrx.setFdSynStatus(SysOmsTempConstants.SYS_OMS_TEMP_SYN_TRX_END_FAIL);
			this.update(tempTrx);
		} catch (Exception e) {
			logger.error("修改事务状态错误,trxId="+tempTrx.getFdId(),e);
		} 
		
	}
	
	/**
	 * 同步成功，修改同步事务状态 
	 * @param trxId
	 * @param errMsg
	 */
	private void updateSuccessTrx(SysOmsTempTrx tempTrx){
		try {
			//1、修改同步事务状态 
			tempTrx.setEndTime(new Date());
			tempTrx.setFdSynStatus(SysOmsTempConstants.SYS_OMS_TEMP_SYN_TRX_END_SUCCESS);
			this.update(tempTrx);
		} catch (Exception e) {
			logger.error("修改事务状态错误事务 trxId="+tempTrx.getFdId(),e);
		} 
		
	}
	
	@Override
	public OmsTempSynResult<SysOmsTempDept> addTempDept(String fdTrxId, List<SysOmsTempDept> tempDeptList) throws Exception {
		OmsTempSynResult<SysOmsTempDept> result = new OmsTempSynResult<SysOmsTempDept>();
		result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_SUCCESS);
		result.setMsg("成功");
		Map<OmsTempSynFailType,List<SysOmsTempDept>> illegalDataMap = result.getIllegalData();
		SysOmsTempTrx tempTrx = (SysOmsTempTrx) this.findByPrimaryKey(fdTrxId);
		if(!isExistRunningTrx(tempTrx)){
			result.setMsg("找不到正在进行中的事务");
			result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL);
			return result;
		}else if(tempDeptList == null){
			throw new Exception("tempDeptList is not null");
		}
		for (SysOmsTempDept tempDept : tempDeptList) {
			if(StringUtil.isNull(tempDept.getFdDeptId())){
				illegalDataMap.get(OmsTempSynFailType.DATA_ERR_TYPE_DEPT_ID).add(tempDept);
				result.setMsg("警告");
				result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_WARN);
				logger.warn("部门ID不能为空");
			}else if(StringUtil.isNull(tempDept.getFdName())){
				illegalDataMap.get(OmsTempSynFailType.DATA_ERR_TYPE_NAME).add(tempDept);
				result.setMsg("警告");
				result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_WARN);
				logger.warn("部门fdName不能为空");
			}else if(tempDept.getFdIsAvailable() == null){
				illegalDataMap.get(OmsTempSynFailType.DATA_ERR_TYPE_IS_AVAILABLE).add(tempDept);
				result.setMsg("警告");
				result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_WARN);
				logger.warn("部门fdIsAvailable不能为空");
			}else{
				tempDept.setFdCreateTime(new Date());
				tempDept.setFdTrxId(fdTrxId);
				sysOmsTempDeptService.add(tempDept);
			}
		}
		sysOmsTempDeptService.getBaseDao().getHibernateSession().flush();
		return result;
	}

	@Override
	public OmsTempSynResult<SysOmsTempPost> addTempPost(String fdTrxId,List<SysOmsTempPost> tempPostList) throws Exception{
		OmsTempSynResult<SysOmsTempPost> result = new OmsTempSynResult<SysOmsTempPost>();
		result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_SUCCESS);
		result.setMsg("成功");
		Map<OmsTempSynFailType,List<SysOmsTempPost>> illegalDataMap = result.getIllegalData();
		//模式为3 和 4才会同步岗位
		SysOmsTempTrx tempTrx = (SysOmsTempTrx) this.findByPrimaryKey(fdTrxId);
		int fdSynModel = tempTrx.getFdSynModel();
		if(fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_30.getValue()
				&& fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_40.getValue()){
			result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL);
			result.setMsg("该同步事务中选中的模式为"+fdSynModel+"，将不同步岗位");
			logger.warn("该同步事务中选中的模式为"+fdSynModel+"，将不同步岗位");
			return result;
		}else if(!isExistRunningTrx(tempTrx)){
			result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL);
			result.setMsg("找不到正在进行中的事务");
			logger.warn("找不到正在进行中的事务");
			return result;
		}else if(tempPostList == null){
			throw new Exception("tempPostList is not null");
		}
		
		for (SysOmsTempPost tempPost : tempPostList) {
			if(StringUtil.isNull(tempPost.getFdPostId())){
				illegalDataMap.get(OmsTempSynFailType.DATA_ERR_TYPE_POST_ID).add(tempPost);
				result.setMsg("警告");
				result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_WARN);
				logger.warn("岗位ID不能为空");
			} else if(StringUtil.isNull(tempPost.getFdName())){
				illegalDataMap.get(OmsTempSynFailType.DATA_ERR_TYPE_NAME).add(tempPost);
				result.setMsg("警告");
				result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_WARN);
				logger.warn("岗位名称不能为空");
			}else if(tempPost.getFdIsAvailable() == null){
				illegalDataMap.get(OmsTempSynFailType.DATA_ERR_TYPE_IS_AVAILABLE).add(tempPost);
				result.setMsg("警告");  
				logger.warn("岗位有效状态不能为空");
			}else{
				tempPost.setFdCreateTime(new Date());
				tempPost.setFdTrxId(fdTrxId);
				sysOmsTempPostService.add(tempPost);
				result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_SUCCESS);
			}
		}
		
		return result;
	}

	@Override
	public OmsTempSynResult<SysOmsTempPerson> addTempPerson(String fdTrxId,List<SysOmsTempPerson> tempPersonList) throws Exception {
		OmsTempSynResult<SysOmsTempPerson> result = new OmsTempSynResult<SysOmsTempPerson>();
		result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_SUCCESS);
		result.setMsg("成功");
		Map<OmsTempSynFailType,List<SysOmsTempPerson>> illegalDataMap = result.getIllegalData();
		SysOmsTempTrx tempTrx = (SysOmsTempTrx) this.findByPrimaryKey(fdTrxId);
		if(!isExistRunningTrx(tempTrx)){
			result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL);
			result.setMsg("找不到正在进行中的事务");
			logger.warn("找不到正在进行中的事务");
			return result;
		}else if(tempPersonList == null){
			throw new Exception("tempPersonList is not null");
		}
		
		for (SysOmsTempPerson tempPerson : tempPersonList) {
			if(StringUtil.isNull(tempPerson.getFdPersonId())){
				illegalDataMap.get(OmsTempSynFailType.DATA_ERR_TYPE_PERSON_ID).add(tempPerson);
				result.setMsg("警告");
				result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_WARN);
				logger.warn("人员ID不能为空");
			} else if(StringUtil.isNull(tempPerson.getFdName())){
				illegalDataMap.get(OmsTempSynFailType.DATA_ERR_TYPE_NAME).add(tempPerson);
				result.setMsg("警告");
				result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_WARN);
				logger.warn("人员名称不能为空");
			}else if(tempPerson.getFdIsAvailable() == null){
				illegalDataMap.get(OmsTempSynFailType.DATA_ERR_TYPE_IS_AVAILABLE).add(tempPerson);
				result.setMsg("警告");  
				result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_WARN);
				logger.warn("人员有效状态不能为空");
			}else{
				tempPerson.setFdCreateTime(new Date());
				tempPerson.setFdTrxId(fdTrxId);
				sysOmsTempPersonService.add(tempPerson);
				result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_SUCCESS);
				result.setMsg("成功");
			}

		}
		return result;
	}

	@Override
	public OmsTempSynResult<SysOmsTempPp> addTempPostPerson(String fdTrxId,List<SysOmsTempPp> tempPpList) throws Exception {
		OmsTempSynResult<SysOmsTempPp> result = new OmsTempSynResult<SysOmsTempPp>();
		result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_SUCCESS);
		result.setMsg("成功");
		Map<OmsTempSynFailType,List<SysOmsTempPp>> illegalDataMap = result.getIllegalData();
		SysOmsTempTrx tempTrx = (SysOmsTempTrx) this.findByPrimaryKey(fdTrxId);
		int fdSynModel = tempTrx.getFdSynModel();
		if(fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_30.getValue()
				&& fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_40.getValue()){
			result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL);
			result.setMsg("该同步事务中选中的模式为"+fdSynModel+"，将不同步岗位人员关系");
			logger.warn("该同步事务中选中的模式为"+fdSynModel+"，将不同步岗位人员关系");
			return result;
		}else if(!isExistRunningTrx(tempTrx)){
			result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL);
			result.setMsg("找不到正在进行中的事务");
			logger.warn("找不到正在进行中的事务");
			return result;
		}else if(tempPpList == null){
			throw new Exception("tempPpList is not null");
		}
		
		for (SysOmsTempPp tempPp : tempPpList) {
			if (StringUtil.isNull(tempPp.getFdPostId())) {
				illegalDataMap.get(OmsTempSynFailType.DATA_ERR_TYPE_POST_ID).add(tempPp);
				result.setMsg("警告");  
				result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_WARN);
				logger.warn("岗位ID不能为空");
			}else if (StringUtil.isNull(tempPp.getFdPersonId())) {
				illegalDataMap.get(OmsTempSynFailType.DATA_ERR_TYPE_PERSON_ID).add(tempPp);
				result.setMsg("警告");  
				result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_WARN);
				logger.warn("人员ID不能为空");
			}else{
				tempPp.setFdTrxId(fdTrxId);
				sysOmsTempPpService.add(tempPp);
			}
		}
	
		return result;
	}

	@Override
	public OmsTempSynResult<SysOmsTempDp> addTempDeptPerson(String fdTrxId,List<SysOmsTempDp> tempDpList) throws Exception{
		OmsTempSynResult<SysOmsTempDp> result = new OmsTempSynResult<SysOmsTempDp>();
		result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_SUCCESS);
		result.setMsg("成功");
		Map<OmsTempSynFailType,List<SysOmsTempDp>> illegalDataMap = result.getIllegalData();
		SysOmsTempTrx tempTrx = (SysOmsTempTrx) this.findByPrimaryKey(fdTrxId);
		int fdSynModel = tempTrx.getFdSynModel();
		if(fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_20.getValue() 
				&& fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_21.getValue()
				&& fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_40.getValue()){
			result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL);
			result.setMsg("该同步事务中选中的模式为"+fdSynModel+"，将不同步部门人员关系");
			logger.warn("该同步事务中选中的模式为"+fdSynModel+"，将不同步部门人员关系");
			return result;
		}else if(!isExistRunningTrx(tempTrx)){
			result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL);
			result.setMsg("找不到正在进行中的事务");
			logger.warn("找不到正在进行中的事务");
			return result;
		}else if(tempDpList == null){
			throw new Exception("tempDpList is not null");
		}
		
		for (SysOmsTempDp tempDp : tempDpList) {
			if (StringUtil.isNull(tempDp.getFdDeptId())) {
				illegalDataMap.get(OmsTempSynFailType.DATA_ERR_TYPE_DEPT_ID).add(tempDp);
				result.setMsg("警告");  
				logger.warn("部门ID不能为空");
			}else if (StringUtil.isNull(tempDp.getFdPersonId())) {
				illegalDataMap.get(OmsTempSynFailType.DATA_ERR_TYPE_PERSON_ID).add(tempDp);
				result.setMsg("警告");  
				result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_WARN);
				logger.warn("人员ID不能为空");
			}else{
				tempDp.setFdTrxId(fdTrxId);
				sysOmsTempPpService.add(tempDp);
			}
		}
		return result;
	}
	
	@Override
	public SysOmsTempTrx findByPrimaryKey(String id){
		SysOmsTempTrx tempTrx = null;
		try{
			tempTrx = (SysOmsTempTrx) super.findByPrimaryKey(id);
		}catch (Exception e) {
			logger.error("找不到事务",e);
		}
		return tempTrx;
	}

	@Override
	public OmsTempSynResult<Object> end(String trxId){
		long starttime = System.currentTimeMillis();
		OmsTempSynResult<Object> result = new OmsTempSynResult<Object>();
		SyncLog log = new SyncLog();//日志对象
		result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_SUCCESS);
		SysOmsTempTrx tempTrx = null;
		
		try {
			tempTrx = this.findByPrimaryKey(trxId);
			if(!isExistRunningTrx(tempTrx)){
				result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL);
				result.setMsg("找不到正在进行中的事务");
			}else if(tempTrx != null){
				//同步
				doSync(tempTrx,result,log);
				if(result.getCode() == SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_SUCCESS){
					result.setMsg("同步成功，总共耗时"+(System.currentTimeMillis() - starttime) + "ms");
				}else{
					result.setMsg("同步失败，总共耗时"+(System.currentTimeMillis() - starttime) + "ms，原因："+result.getMsg());
				}
			}
			
		} catch (Exception e) {
			result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL);
			result.setMsg(e.getMessage());
			logger.error("同步异常",e);
		}
		
		if(result.getCode() == SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL){
			updateTrxStatus(tempTrx,SysOmsTempConstants.SYS_OMS_TEMP_SYN_TRX_END_FAIL);
			handFailData(tempTrx.getFdId());
		}else if(StringUtil.isNull(log.getFdLogError())) {
			updateTrxStatus(tempTrx,SysOmsTempConstants.SYS_OMS_TEMP_SYN_TRX_END_SUCCESS);
		}else {
			updateTrxStatus(tempTrx,SysOmsTempConstants.SYS_OMS_TEMP_SYN_TRX_END_WARN);
		}
		String msg = result.getMsg();
		if(StringUtil.isNotNull(log.getFdLogError())) {
			msg+= SyncLog.NEWLINE_CHAR_TEXTAREA +log.getFdLogError();
		}
		result.setMsg(msg);
		logger.warn("同步事务（事务ID："+tempTrx.getFdId()+"）："+result.getMsg());
		
		//保存同步日志
		saveSynLog(tempTrx,log);
		return result;
	}
	
	/**
	 * 同步结束，修改同步事务状态
	 * @param trxId
	 * @param errMsg
	 */
	protected void updateTrxStatus(SysOmsTempTrx tempTrx,int trxStatus){
		try {
			if(tempTrx == null) {
                return;
            }
			
			tempTrx.setEndTime(new Date());
			tempTrx.setFdSynStatus(trxStatus);
			this.update(tempTrx);
		} catch (Exception e) {
			logger.error("修改事务状态错误,trxId="+tempTrx.getFdId(),e);
		} 
		
	}
	//保存同步日志
	private void saveSynLog(SysOmsTempTrx tempTrx, SyncLog log) {
		try {
			//保存同步日志
			tempTrx.setFdLogDetail(log.getFdLogDetail());
			tempTrx.setFdLogError(log.getFdLogError());
			this.update(tempTrx);
		} catch (Exception e) {
			logger.error("保存同步日志错误：trxId="+tempTrx.getFdId(),e);
		} 
		
	}
	
	//执行同步
	private void doSync(SysOmsTempTrx tempTrx,OmsTempSynResult<Object> result,SyncLog log) throws Exception{
		int fdSynModel = tempTrx.getFdSynModel();
		
		//1、先加载数据
		SysOmsTempData sysOmsTempData = loadDatas(tempTrx);
		if(sysOmsTempData == null || sysOmsTempData.isEmpty()) {
            return;
        }
		
		//2、校验数据
		if(!checkDatas(fdSynModel,sysOmsTempData,result)){
			result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL);
			return;
		}
		
		//3、比较数据
		compareData(sysOmsTempData,fdSynModel);
		
		//4、同步数据（人员、岗位后续可考虑多线程优化）
		
		//新增部门
		createDept(sysOmsTempData.getAddDeptList(),sysOmsTempData.getAvailableSysOrgElementIdMap(),tempTrx,result,log);   
		
		if(fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_30.getValue()
				|| fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_40.getValue()
				|| fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_20.getValue()
				|| fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_21.getValue()){
			
			//新增岗位
			createPost(sysOmsTempData.getAddPostList(),result,log);
			
			//修改岗位
			modifyPost(sysOmsTempData.getUpdatePostList(),result,log);
		}
		
		//新增人员
		createPerson(sysOmsTempData.getAddPersonList(),sysOmsTempData.getSysOrgElementIdMap(),fdSynModel,tempTrx,result,log);
		
		//修改人员
		modifyPerson(sysOmsTempData.getUpdatePersonList(),sysOmsTempData.getSysOrgElementIdMap(),fdSynModel,tempTrx,result,log);

		//修改部门
		modifyDept(sysOmsTempData.getUpdateDeptList(),tempTrx,result,log);
		
		//删除人员
		delPerson(sysOmsTempData.getDelPersonList(),result,log);
		
		//删除岗位
		delPost(sysOmsTempData.getDelPostList(),result,log);
		
		//删除部门
		delDept(sysOmsTempData.getDelDeptList(),result,log);
		//给失败的临时表数据打标记
		handFailData(result);
	}
	
	 protected void handFailData(OmsTempSynResult<Object> result) {
	    	TransactionStatus status = null;
			try {
				status = TransactionUtils.beginNewTransaction();
				Map<OmsTempSynFailType,List<Object>> illegalData = result.getIllegalData();
				for (OmsTempSynFailType key : illegalData.keySet()) {
					List<Object> list = illegalData.get(key);
					if(!list.isEmpty()){
						result.setCode(SYS_OMS_TEMP_SYN_CODE_WARN);
						for (Object obj : list) {
							if(obj instanceof SysOmsTempDept){
								SysOmsTempDept sysOmsTempDept = (SysOmsTempDept)obj;
								sysOmsTempDept = (SysOmsTempDept) sysOmsTempDeptService.findByPrimaryKey(sysOmsTempDept.getFdId());
								sysOmsTempDept.setFdStatus(SYS_OMS_TEMP_DATA_SYN_STATUS_FAIL);
								sysOmsTempDept.setFdFailReason(key.getValue());
								sysOmsTempDeptService.update(sysOmsTempDept);
							}else if (obj instanceof SysOmsTempPerson) {
								SysOmsTempPerson sysOmsTempPerson = (SysOmsTempPerson)obj;
								sysOmsTempPerson = (SysOmsTempPerson) sysOmsTempPersonService.findByPrimaryKey(sysOmsTempPerson.getFdId());
								sysOmsTempPerson.setFdStatus(SYS_OMS_TEMP_DATA_SYN_STATUS_FAIL);
								sysOmsTempPerson.setFdFailReason(key.getValue());
								sysOmsTempPersonService.update(sysOmsTempPerson);
							}else if(obj instanceof SysOmsTempPost){
								SysOmsTempPost sysOmsTempPost = (SysOmsTempPost)obj;
								sysOmsTempPost = (SysOmsTempPost) sysOmsTempPostService.findByPrimaryKey(sysOmsTempPost.getFdId());
								sysOmsTempPost.setFdStatus(SYS_OMS_TEMP_DATA_SYN_STATUS_FAIL);
								sysOmsTempPost.setFdFailReason(key.getValue());
								sysOmsTempPostService.update(sysOmsTempPost);
							}else if (obj instanceof SysOmsTempPp) {
								SysOmsTempPp sysOmsTempPp = (SysOmsTempPp)obj;
								sysOmsTempPp = (SysOmsTempPp) sysOmsTempPpService.findByPrimaryKey(sysOmsTempPp.getFdId());
								sysOmsTempPp.setFdStatus(SYS_OMS_TEMP_DATA_SYN_STATUS_FAIL);
								sysOmsTempPp.setFdFailReason(key.getValue());
								sysOmsTempPostService.update(sysOmsTempPp);
							}else if (obj instanceof SysOmsTempDp) {
								SysOmsTempDp sysOmsTempDp = (SysOmsTempDp)obj;
								sysOmsTempDp = (SysOmsTempDp) sysOmsTempPostService.findByPrimaryKey(sysOmsTempDp.getFdId());
								sysOmsTempDp.setFdStatus(SYS_OMS_TEMP_DATA_SYN_STATUS_FAIL);
								sysOmsTempDp.setFdFailReason(key.getValue());
								sysOmsTempPostService.update(sysOmsTempDp);
							}
						}

					}
				}
				TransactionUtils.commit(status);
			} catch (Exception e) {
				if (status != null) {
					try {
						TransactionUtils.rollback(status);
					} catch (Exception ex) {
						logger.error("---事务回滚出错：{0}---", ex);
					}
				}
				
			}
	}
	
	/**
	 * 同步失败，设置所有数据为失败
	 * @param fdTrxId
	 */
    protected void handFailData(String fdTrxId) {
	    	TransactionStatus status = null;
			try {
				status = TransactionUtils.beginNewTransaction();
				String errStatus = OmsTempSynFailType.DATA_ERR_TYPE_SYN_FAIL.getValue();
				String hql = "update SysOmsTempPost set fdStatus = "+SYS_OMS_TEMP_DATA_SYN_STATUS_FAIL+",fdFailReason='"+errStatus+"' where fdTrxId='"+fdTrxId+"'";
				sysOmsTempPostService.getBaseDao().getHibernateTemplate().bulkUpdate(hql);
				
				hql = "update SysOmsTempDept set fdStatus = "+SYS_OMS_TEMP_DATA_SYN_STATUS_FAIL+",fdFailReason='"+errStatus+"' where fdTrxId='"+fdTrxId+"'";
				sysOmsTempDeptService.getBaseDao().getHibernateTemplate().bulkUpdate(hql);
				
				hql = "update SysOmsTempPerson set fdStatus = "+SYS_OMS_TEMP_DATA_SYN_STATUS_FAIL+",fdFailReason='"+errStatus+"' where fdTrxId='"+fdTrxId+"'";
				sysOmsTempPersonService.getBaseDao().getHibernateTemplate().bulkUpdate(hql);
				
				hql = "update SysOmsTempPp set fdStatus = "+SYS_OMS_TEMP_DATA_SYN_STATUS_FAIL+",fdFailReason='"+errStatus+"' where fdTrxId='"+fdTrxId+"'";
				sysOmsTempPpService.getBaseDao().getHibernateTemplate().bulkUpdate(hql);
				
				hql = "update SysOmsTempDp set fdStatus = "+SYS_OMS_TEMP_DATA_SYN_STATUS_FAIL+",fdFailReason='"+errStatus+"' where fdTrxId='"+fdTrxId+"'";
				sysOmsTempDpService.getBaseDao().getHibernateTemplate().bulkUpdate(hql);

				TransactionUtils.commit(status);
			} catch (Exception e) {
				logger.error("修改数据同步状态失败",e);
				if (status != null) {
					try {
						TransactionUtils.rollback(status);
					} catch (Exception ex) {
						logger.error("---事务回滚出错：{0}---", ex);
					}
				}
				
			}
	}
	
	/**
	 * 通过外部系统id获取dept
	 * @param fdImportInfo
	 * @return
	 */
	private SysOrgDept findOrgDeptByImportInfo(String fdImportInfo,Boolean fdIsAvailable){
		HQLInfo hqlInfo = new HQLInfo();
		String where = "fdImportInfo=:fdImportInfo";
		if(fdIsAvailable != null){
			where += " and fdIsAvailable=:fdIsAvailable";
			hqlInfo.setParameter("fdIsAvailable", fdIsAvailable);
		}
		hqlInfo.setWhereBlock(where);
		hqlInfo.setParameter("fdImportInfo",fdImportInfo);
		SysOrgDept sysOrgDept = null;
		try {
			List<SysOrgDept> sysOrgDeptList = sysOrgDeptService.findList(hqlInfo);
			if(sysOrgDeptList != null && !sysOrgDeptList.isEmpty()) {
                sysOrgDept = sysOrgDeptList.get(0);
            }
		} catch (Exception e) {
			logger.error("通过外部系统id获取dept 错误",e);
		}
		
		return sysOrgDept;
	}
	
	/**
	 * 通过外部系统id获取post
	 * @param fdImportInfo
	 * @return
	 */
	private SysOrgPost findOrgPostByImportInfo(String fdImportInfo,Boolean fdIsAvailable){
		HQLInfo hqlInfo = new HQLInfo();
		String where = "fdImportInfo=:fdImportInfo";
		if(fdIsAvailable != null){
			where += " and fdIsAvailable=:fdIsAvailable";
			hqlInfo.setParameter("fdIsAvailable", fdIsAvailable);
		}
		hqlInfo.setWhereBlock(where);
		hqlInfo.setParameter("fdImportInfo",fdImportInfo);
		SysOrgPost sysOrgPost = null;
		try {
			List<SysOrgPost> sysOrgPostList = sysOrgPostService.findList(hqlInfo);
			if(sysOrgPostList != null && !sysOrgPostList.isEmpty()) {
                sysOrgPost = sysOrgPostList.get(0);
            }
		} catch (Exception e) {
			logger.error("通过外部系统id获取post 错误",e);
		}
		
		return sysOrgPost;
	}
	
	/**
	 * 通过外部系统id获取person
	 * @param fdImportInfo
	 * @return
	 */
	private SysOrgPerson findOrgPersonByImportInfo(String fdImportInfo,Boolean fdIsAvailable){
		HQLInfo hqlInfo = new HQLInfo();
		String where = "fdImportInfo=:fdImportInfo";
		if(fdIsAvailable != null){
			where += " and fdIsAvailable=:fdIsAvailable";
			hqlInfo.setParameter("fdIsAvailable", fdIsAvailable);
		}
		hqlInfo.setWhereBlock(where);
		hqlInfo.setParameter("fdImportInfo",fdImportInfo);
		SysOrgPerson sysOrgPerson = null;
		try {
			List<SysOrgPerson> sysOrgPersonList = sysOrgPersonService.findList(hqlInfo);
			if(sysOrgPersonList != null && !sysOrgPersonList.isEmpty()) {
                sysOrgPerson = sysOrgPersonList.get(0);
            }
		} catch (Exception e) {
			logger.error("通过外部系统id获取person 错误",e);
		}
		
		return sysOrgPerson;
	}
	
	/**
	 * 通过手机号获取person
	 * @param fdImportInfo
	 * @return
	 */
	private SysOrgPerson findOrgPersonByMobile(String fdMobileNo,String fdImportInfo){
		HQLInfo hqlInfo = new HQLInfo();
		String where = "fdIsAvailable is true and fdMobileNo=:fdMobileNo and fdImportInfo !=:fdImportInfo";
		hqlInfo.setWhereBlock(where);
		hqlInfo.setParameter("fdMobileNo",fdMobileNo);
		hqlInfo.setParameter("fdImportInfo",fdImportInfo);
		SysOrgPerson sysOrgPerson = null;
		try {
			List<SysOrgPerson> sysOrgPersonList = sysOrgPersonService.findList(hqlInfo);
			if(sysOrgPersonList != null && !sysOrgPersonList.isEmpty()) {
                sysOrgPerson = sysOrgPersonList.get(0);
            }
		} catch (Exception e) {
			logger.error("通过fdMobileNo获取person 错误",e);
		}
		
		return sysOrgPerson;
	}
	
	/**
	 * 加载数据
	 *	1、 加载临时表数据  2、加载EKP组织架构id
	 * @param tempTrx
	 * @return
	 */
	private SysOmsTempData loadDatas(SysOmsTempTrx tempTrx) throws Exception{
		SysOmsTempData sysOmsTempData = new SysOmsTempData(tempTrx);
		int fdSynModel = tempTrx.getFdSynModel();
		String fdTrxId = tempTrx.getFdId();
		Map<String, SysOmsTempPerson> tempPersonMap = new HashMap<String, SysOmsTempPerson>();
		Map<String, SysOmsTempDept> tempDeptMap = new HashMap<String, SysOmsTempDept>();
		Map<String, SysOmsTempPost> tempPostMap = new HashMap<String, SysOmsTempPost>();
		long starttime = System.currentTimeMillis();
		logger.warn("开始加载数据...");
		
		//部门
		List<SysOmsTempDept> tempDeptList = sysOmsTempDeptService.findListByTrxId(fdTrxId);
		sysOmsTempData.setTempDeptList(tempDeptList);
		for (SysOmsTempDept sysOmsTempDept : tempDeptList) {
			tempDeptMap.put(sysOmsTempDept.getFdDeptId(), sysOmsTempDept);
		}
		sysOmsTempData.setTempDeptMap(tempDeptMap);
	
		//人员
		List<SysOmsTempPerson> tempPersonList = sysOmsTempPersonService.findListByTrxId(fdTrxId);
		sysOmsTempData.setTempPersonList(tempPersonList);
		for (SysOmsTempPerson sysOmsTempPerson : tempPersonList) {
			tempPersonMap.put(sysOmsTempPerson.getFdPersonId(), sysOmsTempPerson);
		}
		sysOmsTempData.setTempPersonMap(tempPersonMap);
		
		if(fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_30.getValue()
				|| fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_40.getValue()){
			//岗位
			List<SysOmsTempPost> tempPostList = sysOmsTempPostService.findListByTrxId(fdTrxId);
			sysOmsTempData.setTempPostList(tempPostList);
			for (SysOmsTempPost sysOmsTempPost : tempPostList) {
				tempPostMap.put(sysOmsTempPost.getFdPostId(), sysOmsTempPost);
			}
			sysOmsTempData.setTempPostMap(tempPostMap);
			
			//岗位人员关系
			List<SysOmsTempPp> tempPpList = sysOmsTempPpService.findListByTrxId(fdTrxId);
			sysOmsTempData.setTempPpList(tempPpList);
		}
		
		if(fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_20.getValue()
				|| fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_21.getValue()
				|| fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_40.getValue()){
			//部门人员关系
			List<SysOmsTempDp> tempDpList = sysOmsTempDpService.findListByTrxId(fdTrxId);
			sysOmsTempData.setTempDpList(tempDpList);
		}
		
		//加载EKP组织架构数据
		loadEKPOrgData(sysOmsTempData);
	
		StringBuffer logStr = new StringBuffer();
		logStr.append("总共获取部门："+sysOmsTempData.getTempDeptList().size()+"个，");
		logStr.append("岗位："+sysOmsTempData.getTempPostList().size()+"个，");
		logStr.append("人员："+sysOmsTempData.getTempPersonList().size()+"个，");
		logStr.append("岗位人员关系："+sysOmsTempData.getTempPpList().size()+"个，");
		logStr.append("部门人员关系："+sysOmsTempData.getTempDpList().size()+"个");
		logger.warn("加载EKP组织架构数据{}",logStr);
		logger.warn("加载数据结束，总共耗时："+(System.currentTimeMillis()-starttime)+"ms");
		
		return sysOmsTempData;
	}
	
	/**
	 * 加载EKP组织架构数据
	 * @param sysOmsTempData
	 * @throws HibernateException
	 * @throws Exception
	 */
	private void loadEKPOrgData(SysOmsTempData sysOmsTempData) throws HibernateException, Exception{
		//全部组织数据（包含无效）
		Map<String,String> sysOrgElementIdMap = new HashMap<String,String>();
		//全部有效组织数据
		Map<String,String> availableSysOrgElementIdMap = new HashMap<String,String>();
//		List<Object[]> elementList = sysOrgElementService.getBaseDao().getHibernateSession().createNativeQuery("SELECT fd_id,fd_is_available,fd_import_info,fd_org_type from sys_org_element"
//						+ " where fd_import_info is not null and (fd_org_type=8 or fd_org_type=2 or fd_org_type=4)").list();
		NativeQuery query = sysOrgElementService.getBaseDao().getHibernateSession().createNativeQuery("SELECT fd_id,fd_is_available,fd_import_info,fd_org_type from sys_org_element"
				+ " where fd_import_info is not null and (fd_org_type=8 or fd_org_type=2 or fd_org_type=4)");
		// 启用二级缓存
		query.setCacheable(true);
		// 设置缓存模式
		query.setCacheMode(CacheMode.NORMAL);
		// 设置缓存区域
		query.setCacheRegion("sys-oms");
		List<Object[]> elementList =query.list();

				String fdId = "",fdImportInfo="",key="";
		boolean fdIsAvailable = true;
		Object fdIsAvailableObj = null;
		int fdOrgType = 0;
		Object fdOrgTypeObj = null;
		for (Object[] obj : elementList) {
			fdId = (String)obj[0];
			fdIsAvailableObj = obj[1];
			if(fdIsAvailableObj == null){
				fdIsAvailable = false;
			}else if(fdIsAvailableObj instanceof Number){
				fdIsAvailable = ((Number)fdIsAvailableObj).byteValue()==0?false:true;
			}else if(fdIsAvailableObj instanceof Boolean){
				fdIsAvailable = (Boolean)fdIsAvailableObj;
			}
			fdImportInfo = (String)obj[2];
			fdOrgTypeObj = obj[3];
			if(fdOrgTypeObj != null && fdOrgTypeObj instanceof Number){
				fdOrgType = ((Number)fdOrgTypeObj).intValue();
			}
			key = fdOrgType+fdImportInfo;
			sysOrgElementIdMap.put(key,fdId);
			if(fdIsAvailable){
				availableSysOrgElementIdMap.put(key,fdId);
			}
		}
		sysOmsTempData.setSysOrgElementIdMap(sysOrgElementIdMap);
		sysOmsTempData.setAvailableSysOrgElementIdMap(availableSysOrgElementIdMap);
	}
	
	/**
	 * 1、校验数据：不合法则直接抛弃，并且通过参数result返回不合法的数据
	 * 规则：部门：父部门id不存在则直接抛弃；人员：所属部门ID不存在则直接抛弃；岗位：所属部门ID不存在则直接抛弃
	 * 岗位人员关系：岗位或者人员ID不存在，则直接抛弃；部门人员关系：部门或者人员ID不存在，则直接抛弃
	 * 2、获取本次增量数据最新的时间戳
	 * 3、将部门和岗位人员关系解析成人员的部门和岗位列表属性
	 * @param tempTrx
	 * @param sysOmsTempData
	 */
	private boolean checkDatas(int fdSynModel,SysOmsTempData sysOmsTempData,OmsTempSynResult<Object> result){
		long starttime = System.currentTimeMillis();
		logger.warn("开始校验数据...");
		try {
			
			//校验部门
			checkDept(sysOmsTempData,result);

			//校验人员
			checkPerson(fdSynModel,sysOmsTempData,result);
		
			//校验岗位
			checkPost(fdSynModel,sysOmsTempData,result);
			
			//校验岗位人员关系
			checkPostPerson(fdSynModel,sysOmsTempData,result);
			
			//校验部门人员关系
			checkDeptPerson(fdSynModel,sysOmsTempData,result);
			
			//将岗位人员关系取出放入人员的岗位列表属性中
			handPostPersonToPerson(fdSynModel,sysOmsTempData,result);
			
			//将部门人员关系取出放入人员的部门列表属性中
			handDeptPersonToPerson(fdSynModel,sysOmsTempData,result);
			
			if(!result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_DEPT_ID_DUPLICATE).isEmpty()
					|| !result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_POST_ID_DUPLICATE).isEmpty()
					|| !result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_PERSON_ID_DUPLICATE).isEmpty()){
				logger.warn("本次同步事务中，有重复的数据，同步停止！！！");
				result.setMsg("本次同步事务中，有重复的数据，同步停止！！！");
				result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL);
				return false;
			}
				
		} catch (Exception e) {
			logger.error("校验数据出错",e);
			result.setMsg("校验数据出错："+e.getMessage());
			result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL);
			return false;
		}finally {
			logger.warn("校验数据结束，总共耗时："+(System.currentTimeMillis()-starttime)+"ms");
		}
		return true;
	}
	
	
	/**
	 * 校验部门数据
	 * 1、部门ID重复，同步停止
	 * 2、父部门ID不为空并且在临时表和EKP组织架构（包括无效的）都找不到，则丢掉该部门，同步继续
	 * 3、父部门ID和本部门ID一样，部门不丢弃，并且将该部门的父部门ID置为空，同步继续
	 * @param sysOmsTempData
	 * @param result
	 */
	private void checkDept(SysOmsTempData sysOmsTempData,OmsTempSynResult<Object> result){
		List<SysOmsTempDept> tempDeptList = sysOmsTempData.getTempDeptList();
		SysOmsTempDept tempDept = null;
		Map<String, SysOmsTempDept> tempDeptMap = sysOmsTempData.getTempDeptMap();
		Map<String,String> sysOrgElementIdMap = sysOmsTempData.getSysOrgElementIdMap();
		Long fdSynTimestamp = sysOmsTempData.getFdSynTimestamp();
		Set<String> deptTempIdSet = new HashSet<String>();
		for (Iterator<SysOmsTempDept> iterator = tempDeptList.iterator(); iterator.hasNext();) {
			SysOmsTempDept sysOmsTempDept = iterator.next();
			fdSynTimestamp = compareToSynTimestamp(sysOmsTempDept.getFdAlterTime(),fdSynTimestamp);
			//1、部门ID重复，同步停止
			if(deptTempIdSet.contains(sysOmsTempDept.getFdDeptId())){
				result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_DEPT_ID_DUPLICATE).add(sysOmsTempDept);
				logger.warn("本次同步事务中，部门ID重复：fdDeptId："
					+sysOmsTempDept.getFdDeptId()+"，fdParentid："+sysOmsTempDept.getFdParentid()
					+"，fdName："+sysOmsTempDept.getFdName());
				continue;
			}
				
			//2、父部门ID不为空并且在临时表和EKP组织架构（包括无效的）都找不到，则丢掉该部门
			if(StringUtil.isNotNull(sysOmsTempDept.getFdParentid())){
				tempDept = tempDeptMap.get(sysOmsTempDept.getFdParentid());
				if((tempDept == null || !tempDept.getFdIsAvailable()) && !sysOrgElementIdMap.containsKey(ORG_TYPE_DEPT+sysOmsTempDept.getFdParentid())){
					iterator.remove();
					tempDeptMap.remove(sysOmsTempDept.getFdDeptId());
					result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_PARENT_ID_NOT_FOUND).add(sysOmsTempDept);
					logger.warn("该部门父部门ID在临时表和EKP组织架构都找不到，丢弃：fdDeptId："
						+sysOmsTempDept.getFdDeptId()+"，fdParentid："+sysOmsTempDept.getFdParentid()
						+"，fdName："+sysOmsTempDept.getFdName());
					continue;
				}
			
			}
			
			//3、父部门ID和本部门ID一样，则将该部门的父部门ID置为空
			if(sysOmsTempDept.getFdDeptId().equals(sysOmsTempDept.getFdParentid())){
				sysOmsTempDept.setFdParentid(null);
				logger.warn("该部门ID和父部门ID一样，将该部门父部门ID置为空：fdDeptId："
						+sysOmsTempDept.getFdDeptId()+"，fdParentid："+sysOmsTempDept.getFdParentid()
						+"，fdName："+sysOmsTempDept.getFdName());
			}
			
			if(StringUtil.isNotNull(sysOmsTempDept.getFdDeptId())) {
                deptTempIdSet.add(sysOmsTempDept.getFdDeptId());
            }
			
		}
		sysOmsTempData.setFdSynTimestamp(fdSynTimestamp);
	}
	
	/**
	 * 校验人员
	 * 1、人员ID重复，同步停止
	 * 2、主部门ID不为空并且在临时表和EKP组织架构都找不到，则丢掉该人员，同步继续
	 * @param sysOmsTempData
	 * @param result
	 */
	private void checkPerson(int fdSynModel,SysOmsTempData sysOmsTempData,OmsTempSynResult<Object> result){
		Map<String, SysOmsTempPerson> tempPersonMap = sysOmsTempData.getTempPersonMap();
		Map<String, SysOmsTempDept> tempDeptMap = sysOmsTempData.getTempDeptMap();
		Map<String,String> sysOrgElementIdMap = sysOmsTempData.getSysOrgElementIdMap();
		SysOmsTempDept tempDept = null;
		Long fdSynTimestamp = sysOmsTempData.getFdSynTimestamp();
		List<SysOmsTempPerson> tempPersonList = sysOmsTempData.getTempPersonList();
		Set<String> personTempIdSet = new HashSet<String>();
		for (Iterator<SysOmsTempPerson> iterator = tempPersonList.iterator(); iterator.hasNext();) {
			SysOmsTempPerson sysOmsTempPerson = iterator.next();
			fdSynTimestamp = compareToSynTimestamp(sysOmsTempPerson.getFdAlterTime(),fdSynTimestamp);
			
			//1、人员ID重复，同步停止
			if(personTempIdSet.contains(sysOmsTempPerson.getFdPersonId())){
				result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_PERSON_ID_DUPLICATE).add(sysOmsTempPerson);
				logger.warn("本次同步事务中，人员ID重复：fdPersonId："
					+sysOmsTempPerson.getFdPersonId()+"，fdParentid："+sysOmsTempPerson.getFdParentid()
					+"，fdName："+sysOmsTempPerson.getFdName());
				continue;
			}

			if(fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_21.getValue()){
				sysOmsTempPerson.setFdParentid(null);
			}
			
			//2、主部门ID不为空并且在临时表和EKP组织架构都找不到，则丢掉该人员
			if(StringUtil.isNotNull(sysOmsTempPerson.getFdParentid())){
				tempDept = tempDeptMap.get(sysOmsTempPerson.getFdParentid());
				if((tempDept == null || !tempDept.getFdIsAvailable()) && !sysOrgElementIdMap.containsKey(ORG_TYPE_DEPT+sysOmsTempPerson.getFdParentid())){
					iterator.remove();
					tempPersonMap.remove(sysOmsTempPerson.getFdPersonId());
					result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_PARENT_ID_NOT_FOUND).add(sysOmsTempPerson);
					logger.warn("该人员所在部门ID在临时表和EKP组织架构都找不到，丢弃：fdPersonId："
							+sysOmsTempPerson.getFdPersonId()+"，fdParentid："+sysOmsTempPerson.getFdParentid()
							+"，fdName："+sysOmsTempPerson.getFdName());
				}
			}
			
			if(StringUtil.isNotNull(sysOmsTempPerson.getFdPersonId())){
				personTempIdSet.add(sysOmsTempPerson.getFdPersonId());
			}
		}
		sysOmsTempData.setFdSynTimestamp(fdSynTimestamp);
	}
	
	/**
	 * 1、岗位ID重复，同步停止
	 * 2、所属部门ID不为空并且在临时表和EKP组织架构都找不到，则丢掉该岗位，同步继续
	 * 校验岗位
	 * @param sysOmsTempData
	 * @param result
	 */
	private void checkPost(int fdSynModel,SysOmsTempData sysOmsTempData,OmsTempSynResult<Object> result){
		if(fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_30.getValue()
				&& fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_40.getValue()){
			return;
		}
		
		Map<String, SysOmsTempPost> tempPostMap = sysOmsTempData.getTempPostMap();
		Map<String, SysOmsTempDept> tempDeptMap = sysOmsTempData.getTempDeptMap();
		Map<String,String> sysOrgElementIdMap = sysOmsTempData.getSysOrgElementIdMap();
		SysOmsTempDept tempDept = null;
		Long fdSynTimestamp = sysOmsTempData.getFdSynTimestamp();
		List<SysOmsTempPost> tempPostList = sysOmsTempData.getTempPostList();
		Set<String> postTempIdSet = new HashSet<String>();
		//校验岗位
		for (Iterator<SysOmsTempPost> iterator = tempPostList.iterator(); iterator.hasNext();) {
			SysOmsTempPost sysOmsTempPost = iterator.next();
			fdSynTimestamp = compareToSynTimestamp(sysOmsTempPost.getFdAlterTime(),fdSynTimestamp);
			
			//1、岗位ID重复，同步停止
			if(postTempIdSet.contains(sysOmsTempPost.getFdPostId())){
				result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_POST_ID_DUPLICATE).add(sysOmsTempPost);
				logger.warn("本次同步事务中，岗位ID重复：fdPostId："
					+sysOmsTempPost.getFdPostId()+"，fdParentid："+sysOmsTempPost.getFdParentid()
					+"，fdName："+sysOmsTempPost.getFdName());
				continue;
			}

			//2、所属部门ID不为空并且在临时表和EKP组织架构都找不到，则丢掉该岗位
			if(StringUtil.isNotNull(sysOmsTempPost.getFdParentid())){
				tempDept = tempDeptMap.get(sysOmsTempPost.getFdParentid());
				if((tempDept == null || !tempDept.getFdIsAvailable()) && !sysOrgElementIdMap.containsKey(ORG_TYPE_DEPT+sysOmsTempPost.getFdParentid())){
					iterator.remove();
					tempPostMap.remove(sysOmsTempPost.getFdPostId());
					result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_PARENT_ID_NOT_FOUND).add(sysOmsTempPost);
					logger.warn("该岗位所在部门ID在临时表和EKP组织架构都找不到，丢弃：fdPostId："
							+sysOmsTempPost.getFdPostId()+"，fdParentid："+sysOmsTempPost.getFdParentid()
							+"，fdName："+sysOmsTempPost.getFdName());
				}
			}
			
			if(StringUtil.isNotNull(sysOmsTempPost.getFdPostId())) {
                postTempIdSet.add(sysOmsTempPost.getFdPostId());
            }
		}
		sysOmsTempData.setFdSynTimestamp(fdSynTimestamp);
	}
	
	/**
	 * 校验岗位人员关系
	 * 1、该关系所关联的人在临时表和EKP组织架构都找不到，则丢掉该关系 、同步继续
	 * 2、该关系所关联的岗位在临时表和EKP组织架构都找不到，则丢掉该关系、同步继续
	 * @param sysOmsTempData
	 * @param result
	 */
	private void checkPostPerson(int fdSynModel,SysOmsTempData sysOmsTempData,OmsTempSynResult<Object> result){
		if(fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_30.getValue()
				&& fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_40.getValue()){
			return;
		}
		
		Map<String, SysOmsTempPerson> tempPersonMap = sysOmsTempData.getTempPersonMap();
		Map<String, SysOmsTempPost> tempPostMap = sysOmsTempData.getTempPostMap();
		Map<String,String> sysOrgElementIdMap = sysOmsTempData.getSysOrgElementIdMap();
		SysOmsTempPerson tempPerson = null;
		SysOmsTempPost tempPost = null;
		Long fdSynTimestamp = sysOmsTempData.getFdSynTimestamp();
		List<SysOmsTempPp> tempPpList = sysOmsTempData.getTempPpList();
		for (Iterator<SysOmsTempPp> iterator = tempPpList.iterator(); iterator.hasNext();) {
			SysOmsTempPp sysOmsTempPp = iterator.next();
			fdSynTimestamp = compareToSynTimestamp(sysOmsTempPp.getFdAlterTime(),fdSynTimestamp);
			
			//1、该关系所关联的人在临时表和EKP组织架构都找不到，则丢掉该关系
			tempPerson = tempPersonMap.get(sysOmsTempPp.getFdPersonId());
			if((tempPerson == null || !tempPerson.getFdIsAvailable()) && !sysOrgElementIdMap.containsKey(ORG_TYPE_PERSON+sysOmsTempPp.getFdPersonId())){
				result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_PERSON_ID_NOT_FOUND).add(sysOmsTempPp);
				logger.warn("该关系所关联的人在临时表和EKP组织架构都找不到，丢弃：fdPostId"+sysOmsTempPp.getFdPostId()+"，fdPersonId："+sysOmsTempPp.getFdPersonId());
				iterator.remove();
				continue;
			}
			
			//2、该关系所关联的岗位在临时表和EKP组织架构都找不到，则丢掉该关系
			tempPost = tempPostMap.get(sysOmsTempPp.getFdPostId());
			if((tempPost == null || !tempPost.getFdIsAvailable()) && !sysOrgElementIdMap.containsKey(ORG_TYPE_POST+sysOmsTempPp.getFdPostId())){
				result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_POST_ID_NOT_FOUND).add(sysOmsTempPp);
				logger.warn("该关系所关联的岗位在临时表和EKP组织架构都找不到，丢弃：fdPostId"+sysOmsTempPp.getFdPostId()+"，fdPersonId："+sysOmsTempPp.getFdPersonId());
				iterator.remove();
				continue;
			}
		}
		sysOmsTempData.setFdSynTimestamp(fdSynTimestamp);
	}
	
	/**
	 * 校验部门人员关系
	 * 1、该关系所关联的人在临时表和EKP组织架构都找不到，则丢掉该关系
	 * 2、该关系所关联的部门在临时表和EKP组织架构都找不到，则丢掉该关系
	 * @param fdSynModel
	 * @param sysOmsTempData
	 * @param result
	 * @throws Exception
	 */
	private void checkDeptPerson(int fdSynModel,SysOmsTempData sysOmsTempData,OmsTempSynResult<Object> result) throws Exception{
		if(fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_20.getValue()
				&& fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_21.getValue()
				&& fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_40.getValue()){
			return;
		}
		
		Map<String, SysOmsTempPerson> tempPersonMap = sysOmsTempData.getTempPersonMap();
		Map<String, SysOmsTempDept> tempDeptMap = sysOmsTempData.getTempDeptMap();
		Map<String,String> sysOrgElementIdMap = sysOmsTempData.getSysOrgElementIdMap();
		SysOmsTempPerson tempPerson = null;
		SysOmsTempDept tempDept = null;
		Long fdSynTimestamp = sysOmsTempData.getFdSynTimestamp();
		List<SysOmsTempDp> tempDpList = sysOmsTempData.getTempDpList();
		for (Iterator<SysOmsTempDp> iterator = tempDpList.iterator(); iterator.hasNext();) {
			SysOmsTempDp sysOmsTempDp = iterator.next();
			fdSynTimestamp = compareToSynTimestamp(sysOmsTempDp.getFdAlterTime(),fdSynTimestamp);
			//该关系所关联的人在临时表和EKP组织架构都找不到，则丢掉该关系
			tempPerson = tempPersonMap.get(sysOmsTempDp.getFdPersonId());
			if((tempPerson == null || !tempPerson.getFdIsAvailable()) && !sysOrgElementIdMap.containsKey(ORG_TYPE_PERSON+sysOmsTempDp.getFdPersonId())){
				result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_PERSON_ID_NOT_FOUND).add(sysOmsTempDp);
				logger.warn("该关系所关联的人在临时表和EKP组织架构都找不到，丢弃：fdDeptId"+sysOmsTempDp.getFdDeptId()+"，fdPersonId："+sysOmsTempDp.getFdPersonId());
				iterator.remove();
				continue;
			}
			
			//该关系所关联的部门在临时表和EKP组织架构都找不到，则丢掉该关系
			tempDept = tempDeptMap.get(sysOmsTempDp.getFdDeptId());
			if((tempDept == null || !tempDept.getFdIsAvailable()) && !sysOrgElementIdMap.containsKey(ORG_TYPE_DEPT+sysOmsTempDp.getFdDeptId())){
				result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_DEPT_ID_NOT_FOUND).add(sysOmsTempDp);
				logger.warn("该关系所关联的部门在临时表和EKP组织架构都找不到，丢弃：fdDeptId"+sysOmsTempDp.getFdDeptId()+"，fdPersonId："+sysOmsTempDp.getFdPersonId());
				iterator.remove();
				continue;
			}
		}

		sysOmsTempData.setFdSynTimestamp(fdSynTimestamp);
	}
	
	/**
	 * 将岗位人员关系取出放入人员的岗位列表属性中
	 * @param fdSynModel
	 * @param sysOmsTempData
	 * @param result
	 */
	private void handPostPersonToPerson(int fdSynModel, SysOmsTempData sysOmsTempData,
			OmsTempSynResult<Object> result) {
		if(fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_30.getValue()
				&& fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_40.getValue()){
			return;
		}
		
		SysOmsTempPerson tempPerson = null;
		Map<String, SysOmsTempPerson> tempPersonMap = sysOmsTempData.getTempPersonMap();
		List<SysOmsTempPp> tempPpList = sysOmsTempData.getTempPpList();
		List<SysOmsTempPerson> tempPersonList = sysOmsTempData.getTempPersonList();
		for (SysOmsTempPp sysOmsTempPp : tempPpList) {
			//如果本次增量数据中没有人员，则从EKP组织架构中查出该人员
			tempPerson = tempPersonMap.get(sysOmsTempPp.getFdPersonId());
			if(tempPerson == null){
				tempPerson = getTempPerosnByPersonId(sysOmsTempPp.getFdPersonId());
				if(tempPerson != null){
					tempPersonList.add(tempPerson);
				}
			}
			
			//如果该条关系正常，则将该关系加入人员中
			if(tempPerson != null && !tempPerson.getPostIdList().contains(sysOmsTempPp)){
				tempPerson.getPostIdList().add(sysOmsTempPp);
			}
		}
	}

	
	/**
	 * 将部门人员关系取出放入人部门列表属性中
	 * @param fdSynModel
	 * @param sysOmsTempData
	 * @param result
	 * @throws Exception
	 */
	private void handDeptPersonToPerson(int fdSynModel, SysOmsTempData sysOmsTempData,
			OmsTempSynResult<Object> result) throws Exception {
		if(fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_20.getValue()
				&& fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_21.getValue()
				&& fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_40.getValue()){
			return;
		}
		SysOmsTempPerson tempPerson = null;
		SysOmsTempDept tempDept = null;
		Map<String, SysOmsTempPerson> tempPersonMap = sysOmsTempData.getTempPersonMap();
		Map<String, SysOmsTempDept> tempDeptMap = sysOmsTempData.getTempDeptMap();
		List<SysOmsTempDp> tempDpList = sysOmsTempData.getTempDpList();
		List<SysOmsTempPerson> tempPersonList = sysOmsTempData.getTempPersonList();
		
		//模式21，选择其中一个部门为用户主部门
		if(fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_21.getValue()){
			for (Iterator<SysOmsTempDp> iterator = tempDpList.iterator(); iterator.hasNext();) {
				SysOmsTempDp sysOmsTempDp = iterator.next();
				tempPerson = tempPersonMap.get(sysOmsTempDp.getFdPersonId());
				//如果本次增量数据中没有人员，则从EKP组织架构中查出该人员
				if(tempPerson == null){
					tempPerson = getTempPerosnByPersonId(sysOmsTempDp.getFdPersonId());
				}
				
				tempDept = tempDeptMap.get(sysOmsTempDp.getFdDeptId());
				//如果本次增量数据中没有部门，则从EKP组织架构中查出该部门
				if(tempDept == null){
					tempDept = getTempDeptByDeptId(sysOmsTempDp.getFdDeptId());
				}
				
				setMainDept(fdSynModel,sysOmsTempData,sysOmsTempDp,tempPerson,tempDept,iterator);
				
			}
		}
	
		for (SysOmsTempDp sysOmsTempDp : tempDpList) {
			tempPerson = tempPersonMap.get(sysOmsTempDp.getFdPersonId());
			//如果本次增量数据中没有人员，则从EKP组织架构中查出该人员
			if(tempPerson == null){
				tempPerson = getTempPerosnByPersonId(sysOmsTempDp.getFdPersonId());
				if(tempPerson != null){
					tempPersonList.add(tempPerson);
				}
			}
			
			tempDept = tempDeptMap.get(sysOmsTempDp.getFdDeptId());
			//如果本次增量数据中没有部门，则从EKP组织架构中查出该部门
			if(tempDept == null){
				tempDept = getTempDeptByDeptId(sysOmsTempDp.getFdDeptId());
			}

			handOmsTempDp(fdSynModel,sysOmsTempData,sysOmsTempDp,tempPerson,tempDept,result);
	
		}
	}
	
	/**
	 * 21模式下，人员的部门属性无用，系统自动选择其中一个部门为用户主部门，
	 * 如果果该人员在EKP中主部门是空，或者该人员主部门不在此次增量的
	 * 部门人员关系中，则需要随机选择一个部门为该用户的主部门
	 * @param fdSynModel
	 * @param sysOmsTempData
	 * @param sysOmsTempDp
	 * @param tempPerson
	 * @param tempDept
	 */
	private void setMainDept(int fdSynModel,SysOmsTempData sysOmsTempData,
			SysOmsTempDp sysOmsTempDp,SysOmsTempPerson tempPerson,SysOmsTempDept tempDept,Iterator<SysOmsTempDp> iterator){
		List<SysOmsTempDp> tempDpList = sysOmsTempData.getTempDpList();
		if(tempPerson == null || !tempPerson.getFdIsAvailable()){
			logger.warn("setMainDept：该关系所关联的人在临时表和EKP组织架构都找不到，丢弃：fdDeptId"+sysOmsTempDp.getFdDeptId()+"，fdPersonId："+sysOmsTempDp.getFdPersonId());
			iterator.remove();
			return;
		}
			
		if(tempDept == null || !tempDept.getFdIsAvailable()){
			logger.warn("setMainDept：该关系所关联的部门在临时表和EKP组织架构都找不到，丢弃：fdDeptId"+sysOmsTempDp.getFdDeptId()+"，fdPersonId："+sysOmsTempDp.getFdPersonId());
			iterator.remove();
			return;
		}
		
		if(StringUtil.isNull(tempPerson.getFdParentid())){
			SysOrgPerson sysOrgPerson = findOrgPersonByImportInfo(tempPerson.getFdPersonId(),true);
			if(sysOrgPerson != null){
				SysOrgElement fdParent = sysOrgPerson.getFdParent();
				if(fdParent != null){
					if(isContainsTempDeptByDeptId(tempDpList,tempPerson.getFdPersonId(),fdParent.getFdImportInfo())){
						tempPerson.setFdParentid(fdParent.getFdImportInfo());
					}
				}
			}
		}
		
		if(StringUtil.isNull(tempPerson.getFdParentid())){
			tempPerson.setFdParentid(sysOmsTempDp.getFdDeptId());
			tempPerson.setFdOrder(sysOmsTempDp.getFdOrder());
			iterator.remove();
		}else if(tempPerson.getFdParentid().equals(sysOmsTempDp.getFdDeptId())){
			tempPerson.setFdOrder(sysOmsTempDp.getFdOrder());
			iterator.remove();
		}

	}

	/**
	 * 处理部门人员关系
	 * 1、将部门人员关系中的排序号加入人员的部门排序号属性中
	 * 2、模式20、21 将部门转换为岗位
	 * @param fdSynModel
	 * @param sysOmsTempData
	 * @param sysOmsTempDp
	 * @param tempPerson
	 * @param result
	 * @throws Exception
	 */
	private void handOmsTempDp(int fdSynModel,SysOmsTempData sysOmsTempData,SysOmsTempDp sysOmsTempDp,SysOmsTempPerson tempPerson,SysOmsTempDept tempDept,OmsTempSynResult<Object> result) throws Exception{
		List<SysOmsTempPost> tempPostList = sysOmsTempData.getTempPostList();
		if(tempPerson == null || !tempPerson.getFdIsAvailable()){
			logger.warn("handOmsTempDp：该关系所关联的人在临时表和EKP组织架构都找不到，丢弃：fdDeptId"+sysOmsTempDp.getFdDeptId()+"，fdPersonId："+sysOmsTempDp.getFdPersonId());
			return;
		}
			
		if(tempDept == null || !tempDept.getFdIsAvailable()){
			logger.warn("handOmsTempDp：该关系所关联的部门在临时表和EKP组织架构都找不到，丢弃：fdDeptId"+sysOmsTempDp.getFdDeptId()+"，fdPersonId："+sysOmsTempDp.getFdPersonId());
			return;
		}
		
		//部门人员关系  fdDeptId和 人员主部门一致，将该关系中的排序号设置到人上面，
		//排序号选择原则：人和关系都有排序号， 以人为准，否则以非0为准
		if(sysOmsTempDp.getFdDeptId().equals(tempPerson.getFdParentid())){
			if((tempPerson.getFdOrder() == null || tempPerson.getFdOrder() == 0)
					&& (sysOmsTempDp.getFdOrder() != null && sysOmsTempDp.getFdOrder() != 0)){
				tempPerson.setFdOrder(sysOmsTempDp.getFdOrder());  
			}
			logger.warn("该关系所关联的部门和人员主部门一致，丢弃：fdDeptId："+sysOmsTempDp.getFdDeptId()+"，fdPersonId："+sysOmsTempDp.getFdPersonId());
			return;
		}

		//1、人员在部门中的排序号
		if(!tempPerson.getDeptIdList().contains(sysOmsTempDp)){
			tempPerson.getDeptIdList().add(sysOmsTempDp);
		}
		
		//2、如果是模式20、21 则需要将部门转换为岗位
		if(fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_20.getValue()
				|| fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_21.getValue()){

			//再创建岗位人员关系（人员部门关系所属部门和用户主部门一致，则不用将该用户加入此部门所属“成员岗位”）
			//先创建岗位
			SysOmsTempPost sysOmsTempPost = getTempPostByDeptIdAndPostName(tempPostList,sysOmsTempDp.getFdDeptId(),SysOmsTempConstants.SYS_ORG_POST_NAME);
			if(sysOmsTempPost == null){
				SysOrgPost sysOrgPost = getSysOrgPostByDeptIdAndPostName(sysOmsTempDp.getFdDeptId(),SysOmsTempConstants.SYS_ORG_POST_NAME);
				sysOmsTempPost = new SysOmsTempPost();
				sysOmsTempPost.setFdAlterTime(new Date().getTime());
				sysOmsTempPost.setFdCreateTime(new Date());
				sysOmsTempPost.setFdIsAvailable(true);
				sysOmsTempPost.setFdName(SysOmsTempConstants.SYS_ORG_POST_NAME);
				sysOmsTempPost.setFdParentid(sysOmsTempDp.getFdDeptId());
				if(sysOrgPost == null){
					sysOmsTempPost.setFdPostId(sysOmsTempPost.getFdId());
				}else{
					sysOmsTempPost.setFdPostId(sysOrgPost.getFdImportInfo());
				}
				
				tempPostList.add(sysOmsTempPost);
			}
			
			if(!isContains(tempPerson.getPostIdList(),sysOmsTempPost)){
				SysOmsTempPp pp = new SysOmsTempPp();
				pp.setFdIsAvailable(sysOmsTempDp.getFdIsAvailable());
				pp.setFdPersonId(tempPerson.getFdPersonId());
				pp.setFdPostId(sysOmsTempPost.getFdPostId());
				tempPerson.getPostIdList().add(pp);
			}
			
		}
	}
	
	private boolean isContains(List<SysOmsTempPp> ppList,SysOmsTempPost sysOmsTempPost) {
		for (SysOmsTempPp sysOmsTempPp : ppList) {
			if(sysOmsTempPp.getFdPostId().equals(sysOmsTempPost.getFdPostId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 通过部门ID判断tempDpList是否存在该部门
	 * @param tempDpList
	 * @param fdDeptId
	 * @return
	 */
	private boolean isContainsTempDeptByDeptId(List<SysOmsTempDp> tempDpList,String fdPersonId,String fdDeptId){
		for (SysOmsTempDp sysOmsTempDp : tempDpList) {
			if(fdPersonId.equals(sysOmsTempDp.getFdPersonId()) && fdDeptId.equals(sysOmsTempDp.getFdDeptId())){
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 通过部门ID和岗位名称在tempPostList找出岗位
	 * @param tempPostList
	 * @param fdDeptId
	 * @return
	 */
	private SysOmsTempPost getTempPostByDeptIdAndPostName(List<SysOmsTempPost> tempPostList,String fdParentId,String fdName){
		for (SysOmsTempPost sysOmsTempPost : tempPostList) {
			if(sysOmsTempPost.getFdName().equals(fdName)
					&& sysOmsTempPost.getFdIsAvailable()
					&& ((sysOmsTempPost.getFdParentid() != null && sysOmsTempPost.getFdParentid() .equals(fdParentId))
							|| (sysOmsTempPost.getFdParentid() == null && fdParentId == null))){
				return sysOmsTempPost;
			}
		}
		return null;
	}
	
	/**
	 * 通过部门ID和岗位名称获取EKP岗位
	 * @param fdName
	 * @param fdParentId
	 * @return
	 * @throws Exception
	 */
	private SysOrgPost getSysOrgPostByDeptIdAndPostName(String fdParentId,String fdName) throws Exception{
		SysOrgPost sysOrgPost = null;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdName=:fdName and hbmParent.fdImportInfo=:fdParentId and fdIsAvailable=1");
		hqlInfo.setParameter("fdName", fdName);
		hqlInfo.setParameter("fdParentId", fdParentId);
		List<SysOrgPost> sysOrgPostList = sysOrgPostService.findList(hqlInfo);
		if(sysOrgPostList != null && !sysOrgPostList.isEmpty()) {
            sysOrgPost = sysOrgPostList.get(0);
        }
		return sysOrgPost;
	}
	
	private SysOmsTempPerson getTempPerosnByPersonId(String personId){
		SysOmsTempPerson tempPerson = null;
		SysOrgPerson sysOrgPerson = findOrgPersonByImportInfo(personId,true);
		if(sysOrgPerson != null){
			tempPerson = new SysOmsTempPerson();
			tempPerson.setFdPersonId(personId);
			tempPerson.setFdAlterTime(sysOrgPerson.getFdAlterTime().getTime());
			tempPerson.setFdEmail(sysOrgPerson.getFdEmail());
			if(sysOrgPerson.getCustomPropMap() != null){
				tempPerson.setFdExtra(JSONObject.fromObject(sysOrgPerson.getCustomPropMap()).toString());
			}
			tempPerson.setFdIsAvailable(sysOrgPerson.getFdIsAvailable());
			tempPerson.setFdMobileNo(sysOrgPerson.getFdMobileNo());
			tempPerson.setFdName(sysOrgPerson.getFdName());
			tempPerson.setFdOrder(sysOrgPerson.getFdOrder());
			tempPerson.setFdParentid(sysOrgPerson.getFdParent().getFdImportInfo());
			tempPerson.setFdSex(sysOrgPerson.getFdSex());
			tempPerson.setFdLoginName(sysOrgPerson.getFdLoginName());
			tempPerson.setFdNo(sysOrgPerson.getFdNo());
		}
		return tempPerson;
	}
	
	private SysOmsTempDept getTempDeptByDeptId(String deptId){
		SysOmsTempDept sysOmsTempDept = null;
		SysOrgDept sysOrgDept = findOrgDeptByImportInfo(deptId,true);
		if(sysOrgDept != null){
			sysOmsTempDept = new SysOmsTempDept();
			sysOmsTempDept.setFdDeptId(deptId);
			sysOmsTempDept.setFdAlterTime(sysOrgDept.getFdAlterTime().getTime());
			sysOmsTempDept.setFdIsAvailable(sysOrgDept.getFdIsAvailable());
			sysOmsTempDept.setFdName(sysOrgDept.getFdName());
			sysOmsTempDept.setFdOrder(sysOrgDept.getFdOrder());
			if(sysOrgDept.getFdParent() != null) {
                sysOmsTempDept.setFdParentid(sysOrgDept.getFdParent().getFdImportInfo());
            }
		}
		return sysOmsTempDept;
	}
	
	private Long compareToSynTimestamp(Long fdAlterTime,Long fdSynTimestamp){
		if(fdAlterTime != null && (fdSynTimestamp == null || fdAlterTime > fdSynTimestamp)){
			fdSynTimestamp = fdAlterTime;
		}
		return fdSynTimestamp;
	}
	
	/**
	 * 比较数据，获取对应的增删改的列表
	 * @param sysOmsTempData
	 */
	private void compareData(SysOmsTempData sysOmsTempData,int fdSynModel) {
		long starttime = System.currentTimeMillis();
		logger.warn("开始对比数据...");
		Map<String,String> sysOrgElementIdMap = sysOmsTempData.getSysOrgElementIdMap();
		Map<String,String> availableSysOrgElementIdMap = sysOmsTempData.getAvailableSysOrgElementIdMap();
		//1、比较部门
		List<SysOmsTempDept> tempDeptList = sysOmsTempData.getTempDeptList();
		List<SysOmsTempDept> addDeptList = new ArrayList<SysOmsTempDept>();
		List<SysOmsTempDept> updateDeptList = new ArrayList<SysOmsTempDept>();
		List<SysOmsTempDept> delDeptList = new ArrayList<SysOmsTempDept>();
		sysOmsTempData.setAddDeptList(addDeptList);
		sysOmsTempData.setUpdateDeptList(updateDeptList);
		sysOmsTempData.setDelDeptList(delDeptList);
		for (SysOmsTempDept sysOmsTempDept : tempDeptList) {
			if(availableSysOrgElementIdMap.containsKey(ORG_TYPE_DEPT+sysOmsTempDept.getFdDeptId())){
				if(sysOmsTempDept.getFdIsAvailable()){
					updateDeptList.add(sysOmsTempDept);
				}else{
					delDeptList.add(sysOmsTempDept);
				}
				
			}else if(sysOmsTempDept.getFdIsAvailable()){
				addDeptList.add(sysOmsTempDept);
			}else{
				logger.warn(sysOmsTempDept.getFdName()+"（部门ID："+sysOmsTempDept.getFdDeptId()+"）无效并且在EKP组织架构中不存在，抛弃该条数据");
			}
		}
		tempDeptList.clear();	
		logger.warn("新增部门："+addDeptList.size()+"个，修改部门："+updateDeptList.size()+"个，删除部门："+delDeptList.size()+"个");
		
		//2、比较岗位
		if(fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_30.getValue()
				|| fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_40.getValue()
				|| fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_20.getValue()
				|| fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_21.getValue()){
			List<SysOmsTempPost> tempPostList = sysOmsTempData.getTempPostList();
			List<SysOmsTempPost> addPostList = new ArrayList<SysOmsTempPost>();
			List<SysOmsTempPost> updatePostList = new ArrayList<SysOmsTempPost>();
			List<SysOmsTempPost> delPostList = new ArrayList<SysOmsTempPost>();
			sysOmsTempData.setAddPostList(addPostList);
			sysOmsTempData.setUpdatePostList(updatePostList);
			sysOmsTempData.setDelPostList(delPostList);
			for (SysOmsTempPost sysOmsTempPost : tempPostList) {
				if(sysOrgElementIdMap.containsKey(ORG_TYPE_POST+sysOmsTempPost.getFdPostId())){
					if(sysOmsTempPost.getFdIsAvailable()){
						updatePostList.add(sysOmsTempPost);
					}else if(!sysOmsTempPost.getFdIsAvailable() && availableSysOrgElementIdMap.containsKey(ORG_TYPE_POST+sysOmsTempPost.getFdPostId())){
						delPostList.add(sysOmsTempPost);
					}else{
						logger.warn(sysOmsTempPost.getFdName()+"（岗位ID："+sysOmsTempPost.getFdPostId()+"）无效并且在EKP组织架构中无效，抛弃该条数据");
					}
				}else if(sysOmsTempPost.getFdIsAvailable()){
					addPostList.add(sysOmsTempPost);
				}else{
					logger.warn(sysOmsTempPost.getFdName()+"（岗位ID："+sysOmsTempPost.getFdPostId()+"）无效并且在EKP组织架构中不存在，抛弃该条数据");
				}
			}
			tempPostList.clear();
			logger.warn("新增岗位："+addPostList.size()+"个，修改岗位："+updatePostList.size()+"个，删除岗位："+delPostList.size()+"个");
		}

		//3、比较人员
		List<SysOmsTempPerson> tempPersonList = sysOmsTempData.getTempPersonList();
		List<SysOmsTempPerson> addPersonList = new ArrayList<SysOmsTempPerson>();
		List<SysOmsTempPerson> updatePersonList = new ArrayList<SysOmsTempPerson>();
		List<SysOmsTempPerson> delPersonList = new ArrayList<SysOmsTempPerson>();
		sysOmsTempData.setAddPersonList(addPersonList);
		sysOmsTempData.setUpdatePersonList(updatePersonList);
		sysOmsTempData.setDelPersonList(delPersonList);
		for (SysOmsTempPerson sysOmsTempPerson : tempPersonList) {
			if(sysOrgElementIdMap.containsKey(ORG_TYPE_PERSON+sysOmsTempPerson.getFdPersonId())){
				if(sysOmsTempPerson.getFdIsAvailable()){
					updatePersonList.add(sysOmsTempPerson);
				}else if(!sysOmsTempPerson.getFdIsAvailable() && availableSysOrgElementIdMap.containsKey(ORG_TYPE_PERSON+sysOmsTempPerson.getFdPersonId())){
					delPersonList.add(sysOmsTempPerson);
				}else{
					logger.warn(sysOmsTempPerson.getFdName()+"（人员ID："+sysOmsTempPerson.getFdPersonId()+"）无效并且在EKP组织架构中无效，抛弃该条数据");
				}
			}else if(sysOmsTempPerson.getFdIsAvailable()){
				addPersonList.add(sysOmsTempPerson);
			}else{
				logger.warn(sysOmsTempPerson.getFdName()+"（人员ID："+sysOmsTempPerson.getFdPersonId()+"）无效并且在EKP组织架构中不存在，抛弃该条数据");
			}
		}
		tempPersonList.clear();
		logger.warn("新增人员："+addPersonList.size()+"个，修改人员："+updatePersonList.size()+"个，删除人员："+delPersonList.size()+"个");
		
		logger.warn("对比数据结束，总共耗时："+(System.currentTimeMillis()-starttime)+"ms");
	}
	
	/**
	 * 递归创建部门
	 * @param addDeptList
	 * @param sysOrgElementIdMap
	 */
	private void createDept(List<SysOmsTempDept> addDeptList,Map<String,String> sysOrgElementIdMap,SysOmsTempTrx tempTrx,OmsTempSynResult<Object> result,SyncLog log){
		Map<String, SysOmsTempDept> deptMap = new HashMap<String, SysOmsTempDept>();
		for (SysOmsTempDept sysOmsTempDept : addDeptList) {
			deptMap.put(sysOmsTempDept.getFdDeptId(), sysOmsTempDept);
		}
		for (SysOmsTempDept sysOmsTempDept : addDeptList) {
			createDept(sysOmsTempDept,sysOrgElementIdMap,deptMap,tempTrx,result,log);
		}
	}
	
	private void createDept(SysOmsTempDept sysOmsTempDept,Map<String,String> sysOrgElementIdMap,Map<String,SysOmsTempDept> deptMap,
			SysOmsTempTrx tempTrx,OmsTempSynResult<Object> result,SyncLog log){
		if(sysOmsTempDept == null) {
            return;
        }
		
		String fdParentId = sysOmsTempDept.getFdParentid();
		if(StringUtil.isNotNull(fdParentId)){
			if(!sysOrgElementIdMap.containsKey(ORG_TYPE_DEPT+fdParentId)){
				createDept(deptMap.get(fdParentId),sysOrgElementIdMap,deptMap,tempTrx,result,log);
			}
		}
		
		if(!sysOrgElementIdMap.containsKey(ORG_TYPE_DEPT+sysOmsTempDept.getFdDeptId())){
			addDept(sysOmsTempDept,sysOrgElementIdMap,tempTrx,result,log);
		}

	}
		
	private void addDept(SysOmsTempDept sysOmsTempDept,Map<String,String> sysOrgElementIdMap,SysOmsTempTrx tempTrx,OmsTempSynResult<Object> result,SyncLog log){
		StringBuffer logStr = new StringBuffer();
		logStr.append("新增部门：");
		logStr.append(sysOmsTempDept.getFdName());
		logStr.append("，部门ID：" + sysOmsTempDept.getFdDeptId());
		logStr.append("，上级部门ID：" +sysOmsTempDept.getFdParentid());
		TransactionStatus status = null;
		try {
			status = TransactionUtils.beginNewTransaction();
			SysOrgDept sysOrgDept = findOrgDeptByImportInfo(sysOmsTempDept.getFdDeptId(),true);
			if(sysOrgDept != null){
				logStr.append("，失败：该部门已经存在");
				log.warn(logStr.toString());
				sysOrgElementIdMap.put(ORG_TYPE_DEPT+sysOmsTempDept.getFdDeptId(),sysOrgDept.getFdId());
				TransactionUtils.commit(status);
				return;
			}else{
				sysOrgDept = findOrgDeptByImportInfo(sysOmsTempDept.getFdDeptId(),false);
				if(sysOrgDept != null){
					updateDept(sysOmsTempDept, tempTrx, result,sysOrgElementIdMap,log);
					TransactionUtils.commit(status);
					return;
				}
			}
			
			sysOrgDept = new SysOrgDept();
			if(StringUtil.isNotNull(sysOmsTempDept.getFdParentid())){
				SysOrgDept parent = findOrgDeptByImportInfo(sysOmsTempDept.getFdParentid(),true);
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
			sysOrgDept.setFdIsAvailable(true);
			sysOrgDept.setFdOrder(convertOrder(sysOmsTempDept.getFdOrder(),tempTrx.getFdDeptIsAsc()));	
			sysOrgDeptService.add(sysOrgDept);
			sysOrgElementIdMap.put(ORG_TYPE_DEPT+sysOmsTempDept.getFdDeptId(),sysOrgDept.getFdId());
			logStr.append("，成功");
			log.info(logStr.toString());
			TransactionUtils.commit(status);
		} catch (Exception e) {
			logStr.append("，失败："+e.getMessage());
			log.error(logStr.toString(),e);
			result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_SYN_FAIL).add(sysOmsTempDept);
			if (status != null) {
				try {
					TransactionUtils.rollback(status);
				} catch (Exception ex) {
					logger.error("---事务回滚出错：{0}---", ex);
				}
			}
			
		}

	}
	
	private void modifyDept(List<SysOmsTempDept> updateDeptList,SysOmsTempTrx tempTrx,OmsTempSynResult<Object> result,SyncLog log){
		omsTempSyncThreadExecutor.listThreadExecute("updateDept", updateDeptList, 3, this,tempTrx,result,log);
	}
	
	private void updateDept(SysOmsTempDept sysOmsTempDept,SysOmsTempTrx tempTrx,OmsTempSynResult<Object> result,Map<String,String> sysOrgElementIdMap,SyncLog log){
		StringBuffer logStr = new StringBuffer();
		logStr.append("修改部门：");
		logStr.append(sysOmsTempDept.getFdName());
		logStr.append("，部门ID：" + sysOmsTempDept.getFdDeptId());
		logStr.append("，上级部门ID：" +sysOmsTempDept.getFdParentid());
		try {
			SysOrgDept sysOrgDept = findOrgDeptByImportInfo(sysOmsTempDept.getFdDeptId(),true);
			if(sysOrgDept == null){
				//如果有效部门不存在，则可能是新增该部门，再看该部门是否曾经存在
				sysOrgDept = findOrgDeptByImportInfo(sysOmsTempDept.getFdDeptId(),null);
				if(sysOrgDept == null){
					logStr.append("，失败：组织架构中找不到该部门");
					log.warn(logStr.toString());
					return;
				}
			}
			
			if(StringUtil.isNotNull(sysOmsTempDept.getFdParentid())){
				SysOrgDept parent = findOrgDeptByImportInfo(sysOmsTempDept.getFdParentid(),true);
				if(parent == null){
					logStr.append("，失败：组织架构中找不到上级部门");
					log.warn(logStr.toString());
					return;
				}
				sysOrgDept.setFdParent(parent);
				logStr.append("，上级部门名称："+parent.getFdName());
			}else{
				sysOrgDept.setFdParent(null);
			}
			
			sysOrgDept.setFdName(sysOmsTempDept.getFdName());
			sysOrgDept.setFdOrder(convertOrder(sysOmsTempDept.getFdOrder(),tempTrx.getFdDeptIsAsc()));	
			sysOrgDept.setFdIsAvailable(true);
			sysOrgDeptService.update(sysOrgDept);
			if(sysOrgElementIdMap != null) {
                sysOrgElementIdMap.put(ORG_TYPE_DEPT+sysOmsTempDept.getFdDeptId(),sysOrgDept.getFdId());
            }
			logStr.append("，成功");
			log.info(logStr.toString());
		} catch (Exception e) {
			logStr.append("，失败："+e.getMessage());
			log.error(logStr.toString(),e);
			result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_SYN_FAIL).add(sysOmsTempDept);
		}
	}
	
	private Integer convertOrder(Integer order,boolean isAsc){
		try {
			if(order == null) {
                return null;
            }
			if(isAsc) {
                return order;
            }
			
			Integer maxOrder = 1000000000;
			return maxOrder - order;
		} catch (Exception e) {
			logger.warn("转换排序号错误",e);
		}
		
		return null;
	}
	
	private void createPost(List<SysOmsTempPost> addPostList,OmsTempSynResult<Object> result,SyncLog log){
		omsTempSyncThreadExecutor.listThreadExecute("addPost", addPostList, 2, this,result,log);
	}

	private void addPost(SysOmsTempPost sysOmsTempPost,OmsTempSynResult<Object> result,SyncLog log) {
		
		StringBuffer logStr = new StringBuffer();
		logStr.append("新增岗位：");
		logStr.append(sysOmsTempPost.getFdName());
		logStr.append("，岗位ID：" + sysOmsTempPost.getFdPostId());
		logStr.append("，所属部门ID：" +sysOmsTempPost.getFdParentid());
		try {
			SysOrgPost sysOrgPost = findOrgPostByImportInfo(sysOmsTempPost.getFdPostId(),true);
			if(sysOrgPost != null){
				logStr.append("，失败：该岗位已经存在");
				log.warn(logStr.toString());
				return;
			}
			
			sysOrgPost = new SysOrgPost();
			if(StringUtil.isNotNull(sysOmsTempPost.getFdParentid())){
				SysOrgDept parent = findOrgDeptByImportInfo(sysOmsTempPost.getFdParentid(),true);
				if(parent == null){
					logStr.append("，失败：找不到所属部门");
					log.warn(logStr.toString());
					return;
				}
				sysOrgPost.setFdParent(parent);
				logStr.append("，所属部门名称："+parent.getFdName());
			}else{
				sysOrgPost.setFdParent(null);
			}
			sysOrgPost.setFdName(sysOmsTempPost.getFdName());
			sysOrgPost.setFdCreateTime(new Date());
			sysOrgPost.setFdImportInfo(sysOmsTempPost.getFdPostId());
			sysOrgPost.setFdIsAvailable(true);
			sysOrgPost.setFdOrder(sysOmsTempPost.getFdOrder());	
			sysOrgPostService.add(sysOrgPost);
			logStr.append("，成功");
			log.info(logStr.toString());
		} catch (Exception e) {
			logStr.append("，失败："+e.getMessage());
			log.error(logStr.toString(),e);
			result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_SYN_FAIL).add(sysOmsTempPost);
		}
	}
	
	private void modifyPost(List<SysOmsTempPost> updatePostList,OmsTempSynResult<Object> result,SyncLog log){
		omsTempSyncThreadExecutor.listThreadExecute("updatePost", updatePostList, 2, this,result,log);
	}

	private void updatePost(SysOmsTempPost sysOmsTempPost,OmsTempSynResult<Object> result,SyncLog log) {
		StringBuffer logStr = new StringBuffer();
		logStr.append("修改岗位：");
		logStr.append(sysOmsTempPost.getFdName());
		logStr.append("，岗位ID：" + sysOmsTempPost.getFdPostId());
		logStr.append("，所属部门ID：" +sysOmsTempPost.getFdParentid());

		try {
			SysOrgPost sysOrgPost = findOrgPostByImportInfo(sysOmsTempPost.getFdPostId(),true);
			if(sysOrgPost == null){
				//如果有效岗位不存在，则可能是新增该岗位，再看该岗位是否曾经存在
				sysOrgPost = findOrgPostByImportInfo(sysOmsTempPost.getFdPostId(),null);
				if(sysOrgPost == null){
					logStr.append("，失败：组织架构中找不到该岗位");
					log.warn(logStr.toString());
					return;
				}
			}
			
			if(StringUtil.isNotNull(sysOmsTempPost.getFdParentid())){
				SysOrgDept parent = findOrgDeptByImportInfo(sysOmsTempPost.getFdParentid(),true);
				if(parent == null){
					logStr.append("，失败：找不到所属部门");
					log.warn(logStr.toString());
					return;
				}
				sysOrgPost.setFdParent(parent);
				logStr.append("，所属部门名称："+parent.getFdName());
			}else{
				sysOrgPost.setFdParent(null);
			}
			
			sysOrgPost.setFdName(sysOmsTempPost.getFdName());
			sysOrgPost.setFdOrder(sysOmsTempPost.getFdOrder());	
			sysOrgPost.setFdIsAvailable(true);
			logStr.append("，成功");
			sysOrgPostService.update(sysOrgPost);
			log.info(logStr.toString());
		} catch (Exception e) {
			logStr.append("，失败："+e.getMessage());
			log.error(logStr.toString(),e);
			result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_SYN_FAIL).add(sysOmsTempPost);
		}
		
	}
	
	private void createPerson(List<SysOmsTempPerson> addPersonList,Map<String,String> sysOrgElementIdMap,int fdSynModel,SysOmsTempTrx tempTrx,OmsTempSynResult<Object> result,SyncLog log) {
		omsTempSyncThreadExecutor.listThreadExecute("addPerson", addPersonList, 1, this,sysOrgElementIdMap,fdSynModel,tempTrx,result,log);
	}
	
	private void addPerson(SysOmsTempPerson sysOmsTempPerson,Map<String,String> sysOrgElementIdMap,int fdSynModel,SysOmsTempTrx tempTrx,OmsTempSynResult<Object> result,SyncLog log) {
		StringBuffer logStr = new StringBuffer();
		logStr.append("新增人员：");
		logStr.append(sysOmsTempPerson.getFdName());
		logStr.append("，人员ID：" + sysOmsTempPerson.getFdPersonId());
		logStr.append("，所属部门ID：" +sysOmsTempPerson.getFdParentid());
		try {
			SysOrgPerson sysOrgPerson = findOrgPersonByImportInfo(sysOmsTempPerson.getFdPersonId(),true);
			if(sysOrgPerson != null){
				logStr.append("，失败：该人员已经存在");
				log.warn(logStr.toString());
				return;
			}
					
			sysOrgPerson = new SysOrgPerson();
			
			//人员所属主部门
			if(StringUtil.isNotNull(sysOmsTempPerson.getFdParentid())){
				SysOrgDept parent = findOrgDeptByImportInfo(sysOmsTempPerson.getFdParentid(),true);
				if(parent == null){
					logStr.append("，失败：找不到所属部门");
					log.warn(logStr.toString());
					return;
				}
				logStr.append("，所属部门名称：");
				sysOrgPerson.setFdParent(parent);
				logStr.append(parent.getFdName());
			}else{
				sysOrgPerson.setFdParent(null);
			}
			sysOrgPerson.setFdName(sysOmsTempPerson.getFdName());
			sysOrgPerson.setFdCreateTime(new Date());
			sysOrgPerson.setFdImportInfo(sysOmsTempPerson.getFdPersonId());
			sysOrgPerson.setFdSex(convertSex(sysOmsTempPerson.getFdSex()));
			sysOrgPerson.setFdLoginName(sysOmsTempPerson.getFdLoginName());
			sysOrgPerson.setFdOrder(convertOrder(sysOmsTempPerson.getFdOrder() == null?null:sysOmsTempPerson.getFdOrder().intValue(),tempTrx.getFdPersonIsAsc()));	
			sysOrgPerson.setFdMobileNo(sysOmsTempPerson.getFdMobileNo());
			sysOrgPerson.setFdEmail(sysOmsTempPerson.getFdEmail());
			sysOrgPerson.setFdNo(sysOmsTempPerson.getFdNo());
			sysOrgPerson.setFdWorkPhone(sysOmsTempPerson.getFdWorkPhone());
			sysOrgPerson.setFdHiredate(convertDate(sysOmsTempPerson.getFdHireDate()));
			sysOrgPerson.setFdMemo(sysOmsTempPerson.getFdDesc());
			
			//扩展字段
			String fdExtra = sysOmsTempPerson.getFdExtra();
			if(StringUtil.isNotNull(fdExtra)){
				JSONObject extraJson = JSONObject.fromObject(fdExtra);
				if(!extraJson.isEmpty()){
					Map<String, String> ekpMap = SysOmsExcelUtil.getEKPDynamicAttribute();
					Map<String,Object> customPropMap = sysOrgPerson.getCustomPropMap();
					for(Object okey:extraJson.keySet()){
						String key = (String) okey;
						Object obj = extraJson.get(key);
						if(obj!=null){
							putExtandField(key,obj,ekpMap,customPropMap);
						}
					}
					sysOrgPerson.setCustomPropMap(customPropMap);
				}
			}

			//人员所属岗位
			if(fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_1.getValue()){
				List<SysOmsTempPp> postIdList = sysOmsTempPerson.getPostIdList();
				List<SysOrgPost> sysOrgPostList = new ArrayList<SysOrgPost>();
				for (SysOmsTempPp pp : postIdList) {
					if(pp.getFdIsAvailable() == null || pp.getFdIsAvailable()){
						SysOrgPost sysOrgPost = findOrgPostByImportInfo(pp.getFdPostId(),true);
						if(sysOrgPost != null) {
                            sysOrgPostList.add(sysOrgPost);
                        }
					}
				}
				sysOrgPerson.setFdPosts(sysOrgPostList);
			}
			sysOrgPersonService.add(sysOrgPerson);
			
			//人员所属部门
			if(fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_20.getValue()
					|| fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_21.getValue()
					|| fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_40.getValue()){  
				sysOrgDeptPersonRelationService.delRelation(sysOrgPerson.getFdId());
				//人员所在部门排序号
				SysOrgDeptPersonRelation deptPersonRelation = null;
				List<SysOmsTempDp> fdOrderInDepts = sysOmsTempPerson.getDeptIdList();
				for (SysOmsTempDp sysOmsTempDp : fdOrderInDepts) {
					if(sysOmsTempDp.getFdIsAvailable() == null || sysOmsTempDp.getFdIsAvailable()){
						SysOrgDept sysOrgDept = findOrgDeptByImportInfo(sysOmsTempDp.getFdDeptId(), true);
						if(sysOrgDept == null) {
							continue;
						}
						deptPersonRelation = new SysOrgDeptPersonRelation();
						deptPersonRelation.setFdPersonId(sysOrgPerson.getFdId());
						deptPersonRelation.setFdDeptId(sysOrgDept.getFdId());
						deptPersonRelation.setFdOrder(sysOmsTempDp.getFdOrder()==null?0:sysOmsTempDp.getFdOrder().intValue());
						sysOrgDeptPersonRelationService.add(deptPersonRelation);
					}
				}
			}
			
			sysOrgElementIdMap.put(ORG_TYPE_PERSON+sysOmsTempPerson.getFdPersonId(),sysOrgPerson.getFdId());
			logStr.append("，成功");
			log.info(logStr.toString());
		} catch (Exception e) {
			logStr.append("，失败："+e.getMessage());
			log.error(logStr.toString(),e);
			result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_SYN_FAIL).add(sysOmsTempPerson);
		}
		
	}
	
	/**
	 * 扩展字段转换
	 * @param key
	 * @param obj
	 * @param ekpMap
	 * @param customPropMap
	 */
	private void putExtandField(String key,Object obj,Map<String, String> ekpMap,Map<String,Object> customPropMap) {
		try {
			if(!"null".equalsIgnoreCase(obj.toString()) && ekpMap.containsKey(key) && StringUtil.isNotNull(obj.toString())){
				if("String".equals(ekpMap.get(key))){
					customPropMap.put(key,obj.toString());
				}else if("Integer".equals(ekpMap.get(key))){
					customPropMap.put(key,Integer.parseInt(obj.toString()));
				}else if("Long".equals(ekpMap.get(key))){
					customPropMap.put(key,Long.parseLong(obj.toString()));
				}else if("Double".equals(ekpMap.get(key))){
					customPropMap.put(key,Double.parseDouble(obj.toString()));
				}else if("Date".equals(ekpMap.get(key))){
					customPropMap.put(key,DateUtil.convertStringToDate(obj.toString(), DateUtil.PATTERN_DATE));
				}else {
					customPropMap.put(key,obj.toString());
				}
			}
		} catch (Exception e) {
			logger.error("参数："+obj.toString()+"转换成："+ekpMap.get(key),e);
		}
	}

	/**
	 * 性别转换
	 * @param sex
	 * @return
	 */
	private String convertSex(String sex){
		if(StringUtil.isNull(sex)) {
            return null;
        }
		if("男".equals(sex) || "1".equals(sex)){
			return "M";
		}else if("女".equals(sex)  || "0".equals(sex)){
			return "F";
		}
		return null;
	}
	
	/**
	 * 日期转换
	 * @param date
	 * @return
	 */
	private Date convertDate(Long date){
		if(date == null) {
            return null;
        }
		try {
			return new Date(date);
		} catch (Exception e) {
			logger.warn("日期转换失败");
		}
		return null;
	}
	
	private void modifyPerson(List<SysOmsTempPerson> updatePersonList,Map<String,String> sysOrgElementIdMap,int fdSynModel,SysOmsTempTrx tempTrx,OmsTempSynResult<Object> result,SyncLog log) {
		omsTempSyncThreadExecutor.listThreadExecute("updatePerson", updatePersonList, 1, this,sysOrgElementIdMap,fdSynModel,tempTrx,result,log);
	}
	private void updatePerson(SysOmsTempPerson sysOmsTempPerson,Map<String,String> sysOrgElementIdMap,int fdSynModel,SysOmsTempTrx tempTrx,OmsTempSynResult<Object> result,SyncLog log) {
		StringBuffer logStr = new StringBuffer();
		logStr.append("修改人员：");
		logStr.append(sysOmsTempPerson.getFdName());
		logStr.append("，人员ID：" + sysOmsTempPerson.getFdPersonId());
		logStr.append("，所属部门ID：" +sysOmsTempPerson.getFdParentid());
		try {
			SysOrgPerson sysOrgPerson = findOrgPersonByImportInfo(sysOmsTempPerson.getFdPersonId(),true);
			if(sysOrgPerson == null){
				//如果有效人员不存在，则可能是新增该人员，再看该人员是否曾经存在
				sysOrgPerson = findOrgPersonByImportInfo(sysOmsTempPerson.getFdPersonId(),null);
				if(sysOrgPerson == null){
					logStr.append("，失败：组织架构中找不到该人员");
					log.warn(logStr.toString());
					return;
				}
			}
			
			//人员所属主部门
			if(StringUtil.isNotNull(sysOmsTempPerson.getFdParentid())){
				SysOrgDept parent = findOrgDeptByImportInfo(sysOmsTempPerson.getFdParentid(),true);
				if(parent == null){
					logStr.append("，失败：找不到所属部门");
					log.warn(logStr.toString());
					return;
				}
				sysOrgPerson.setFdParent(parent);
				logStr.append("，所属部门名称："+parent.getFdName());
			}else{
				sysOrgPerson.setFdParent(null);
			}
			
			sysOrgPerson.setFdIsAvailable(true);
			sysOrgPerson.setFdName(sysOmsTempPerson.getFdName());
			sysOrgPerson.setFdSex(convertSex(sysOmsTempPerson.getFdSex()));
			sysOrgPerson.setFdMobileNo(sysOmsTempPerson.getFdMobileNo());
			sysOrgPerson.setFdLoginName(sysOmsTempPerson.getFdLoginName());
			sysOrgPerson.setFdEmail(sysOmsTempPerson.getFdEmail());
			sysOrgPerson.setFdOrder(convertOrder(sysOmsTempPerson.getFdOrder() == null?null:sysOmsTempPerson.getFdOrder().intValue(),tempTrx.getFdPersonIsAsc()));	
			sysOrgPerson.setFdNo(sysOmsTempPerson.getFdNo());
			sysOrgPerson.setFdWorkPhone(sysOmsTempPerson.getFdWorkPhone());
			sysOrgPerson.setFdHiredate(convertDate(sysOmsTempPerson.getFdHireDate()));
			sysOrgPerson.setFdMemo(sysOmsTempPerson.getFdDesc());			
			
			//扩展字段
			String fdExtra = sysOmsTempPerson.getFdExtra();
			if(StringUtil.isNotNull(fdExtra)){
				JSONObject extraJson = JSONObject.fromObject(fdExtra);
				if(!extraJson.isEmpty()){
					Map<String, String> ekpMap = SysOmsExcelUtil.getEKPDynamicAttribute();
					Map<String,Object> customPropMap = sysOrgPerson.getCustomPropMap();
					for(Object okey:extraJson.keySet()){
						String key = (String) okey;
						Object obj = extraJson.get(key);
						if(obj!=null){
							putExtandField(key,obj,ekpMap,customPropMap);
						}
					}
					sysOrgPerson.setCustomPropMap(customPropMap);
				}
			}

			//人员所属岗位
			if(fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_1.getValue()){
				List<SysOmsTempPp> postIdList = sysOmsTempPerson.getPostIdList();
				List<SysOrgPost> sysOrgPostList = new ArrayList<SysOrgPost>();
				for (SysOmsTempPp pp : postIdList) {
					if(pp.getFdIsAvailable() == null || pp.getFdIsAvailable()){
						SysOrgPost sysOrgPost = findOrgPostByImportInfo(pp.getFdPostId(),true);
						if(sysOrgPost != null) {
                            sysOrgPostList.add(sysOrgPost);
                        }
					}
				}
				sysOrgPerson.setFdPosts(sysOrgPostList);
			}
			sysOrgPersonService.update(sysOrgPerson);
			
			//人员所属部门
			if(fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_20.getValue()
					|| fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_21.getValue()
					|| fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_40.getValue()){  
				sysOrgDeptPersonRelationService.delRelation(sysOrgPerson.getFdId());
				//人员所在部门排序号
				SysOrgDeptPersonRelation deptPersonRelation = null;
				List<SysOmsTempDp> fdOrderInDepts = sysOmsTempPerson.getDeptIdList();
				for (SysOmsTempDp sysOmsTempDp : fdOrderInDepts) {
					if(sysOmsTempDp.getFdIsAvailable() == null || sysOmsTempDp.getFdIsAvailable()){
						SysOrgDept sysOrgDept = findOrgDeptByImportInfo(sysOmsTempDp.getFdDeptId(), true);
						if(sysOrgDept == null) {
							continue;
						}
						deptPersonRelation = new SysOrgDeptPersonRelation();
						deptPersonRelation.setFdPersonId(sysOrgPerson.getFdId());
						deptPersonRelation.setFdDeptId(sysOrgDept.getFdId());
						deptPersonRelation.setFdOrder(sysOmsTempDp.getFdOrder()==null?0:sysOmsTempDp.getFdOrder().intValue());
						sysOrgDeptPersonRelationService.add(deptPersonRelation);
					}
				}
			}
			logStr.append("，成功");
			log.info(logStr.toString());
		} catch (Exception e) {
			logStr.append("，失败："+e.getMessage());
			log.error(logStr.toString(),e);
			result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_SYN_FAIL).add(sysOmsTempPerson);
		}

	}
	
	private void delPerson(List<SysOmsTempPerson> delPersonList,OmsTempSynResult<Object> result,SyncLog log) {
		omsTempSyncThreadExecutor.listThreadExecute("deletePerson", delPersonList, 5, this,result,log);
	}
	
	private void deletePerson(SysOmsTempPerson sysOmsTempPerson,OmsTempSynResult<Object> result,SyncLog log) {
		StringBuffer logStr = new StringBuffer();
		logStr.append("删除人员：");
		logStr.append(sysOmsTempPerson.getFdName());
		logStr.append("，人员ID：" + sysOmsTempPerson.getFdPersonId());
		try {
			SysOrgPerson sysOrgPerson = findOrgPersonByImportInfo(sysOmsTempPerson.getFdPersonId(),true);
			if(sysOrgPerson != null){
				sysOrgPerson.setFdIsAvailable(false);
				sysOrgPersonService.update(sysOrgPerson);
			}
			logStr.append("，成功");
			log.info(logStr.toString());
		} catch (Exception e) {
			logStr.append("，失败："+e.getMessage());
			log.error(logStr.toString(),e);
			result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_SYN_FAIL).add(sysOmsTempPerson);
		}
		
	}

	private void delPost(List<SysOmsTempPost> delPostList,OmsTempSynResult<Object> result,SyncLog log) {
		omsTempSyncThreadExecutor.listThreadExecute("deletePost", delPostList, 5, this,log);
	}
	
	private void deletePost(SysOmsTempPost sysOmsTempPost,OmsTempSynResult<Object> result,SyncLog log) {
		StringBuffer logStr = new StringBuffer();
		logStr.append("删除岗位：");
		logStr.append(sysOmsTempPost.getFdName());
		logStr.append("，岗位ID：" + sysOmsTempPost.getFdPostId());
		try {
			SysOrgPost sysOrgPost = findOrgPostByImportInfo(sysOmsTempPost.getFdPostId(),true);
			if(sysOrgPost != null){
				sysOrgPost.setFdIsAvailable(false);
				sysOrgPostService.update(sysOrgPost);
			}
			logStr.append("，成功");
			log.info(logStr.toString());
		} catch (Exception e) {
			logStr.append("，失败："+e.getMessage());
			log.error(logStr.toString(),e);
			result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_SYN_FAIL).add(sysOmsTempPost);
		}
		
	}
	
	private void delDept(List<SysOmsTempDept> delDeptList,OmsTempSynResult<Object> result,SyncLog log) {
		omsTempSyncThreadExecutor.listThreadExecute("deleteDept", delDeptList, 3, this,result,log);
	}

	private void deleteDept(SysOmsTempDept sysOmsTempDept,OmsTempSynResult<Object> result,SyncLog log) {
		StringBuffer logStr = new StringBuffer();
		logStr.append("删除部门：");
		logStr.append(sysOmsTempDept.getFdName());
		logStr.append("，部门ID：" + sysOmsTempDept.getFdDeptId());
		try {
			SysOrgDept sysOrgDept = findOrgDeptByImportInfo(sysOmsTempDept.getFdDeptId(),true);
			if(sysOrgDept != null){
				sysOrgDept.setFdIsAvailable(false);
				sysOrgDeptService.update(sysOrgDept);
			}
			logStr.append("，成功");
			log.info(logStr.toString());
		} catch (Exception e) {
			logStr.append("，失败："+e.getMessage());
			log.error(logStr.toString(),e);
			result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_SYN_FAIL).add(sysOmsTempDept);
		}
	}

	public void setSysNotifyMainCoreService(ISysNotifyMainCoreService sysNotifyMainCoreService) {
		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
	}

	public void setSysOmsTempDeptService(ISysOmsTempDeptService sysOmsTempDeptService) {
		this.sysOmsTempDeptService = sysOmsTempDeptService;
	}

	public void setSysOmsTempPersonService(ISysOmsTempPersonService sysOmsTempPersonService) {
		this.sysOmsTempPersonService = sysOmsTempPersonService;
	}

	public void setSysOmsTempPostService(ISysOmsTempPostService sysOmsTempPostService) {
		this.sysOmsTempPostService = sysOmsTempPostService;
	}

	public void setSysOmsTempPpService(ISysOmsTempPpService sysOmsTempPpService) {
		this.sysOmsTempPpService = sysOmsTempPpService;
	}

	public void setSysOmsTempDpService(ISysOmsTempDpService sysOmsTempDpService) {
		this.sysOmsTempDpService = sysOmsTempDpService;
	}

	public void setSysOrgElementService(ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	public void setSysOrgPostService(ISysOrgPostService sysOrgPostService) {
		this.sysOrgPostService = sysOrgPostService;
	}

	public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	public void setSysOrgDeptService(ISysOrgDeptService sysOrgDeptService) {
		this.sysOrgDeptService = sysOrgDeptService;
	}

	public void setSysOrgDeptPersonRelationService(ISysOrgDeptPersonRelationService sysOrgDeptPersonRelationService) {
		this.sysOrgDeptPersonRelationService = sysOrgDeptPersonRelationService;
	}

	

	public void setOmsTempSyncThreadExecutor(OmsTempSyncThreadExecutor omsTempSyncThreadExecutor) {
		this.omsTempSyncThreadExecutor = omsTempSyncThreadExecutor;
	}

	@Override
	public <T> void listThreadHandler(String type, List<T> allList, T bean, Object... otherParams) {
		switch (type) {
		//修改部门
		case "updateDept":
			updateDept((SysOmsTempDept) bean,(SysOmsTempTrx)otherParams[0],(OmsTempSynResult)otherParams[1],null,(SyncLog)otherParams[2]);
			break;
		//新增岗位
		case "addPost":
			addPost((SysOmsTempPost)bean,(OmsTempSynResult)otherParams[0],(SyncLog)otherParams[1]);
			break;
		//修改岗位
		case "updatePost":
			updatePost((SysOmsTempPost)bean,(OmsTempSynResult)otherParams[0],(SyncLog)otherParams[1]);
			break;
		//新增人员
		case "addPerson":
			addPerson((SysOmsTempPerson) bean,(Map<String, String>)otherParams[0],(Integer)otherParams[1],(SysOmsTempTrx)otherParams[2],(OmsTempSynResult)otherParams[3],(SyncLog)otherParams[4]);
			break;
		//修改人员
		case "updatePerson":
			updatePerson((SysOmsTempPerson) bean,(Map<String, String>)otherParams[0],(Integer)otherParams[1],(SysOmsTempTrx)otherParams[2],(OmsTempSynResult)otherParams[3],(SyncLog)otherParams[4]);
			break;
		//删除人员
		case "deletePerson":
			deletePerson((SysOmsTempPerson) bean,(OmsTempSynResult)otherParams[0],(SyncLog)otherParams[1]);
			break;
		//删除岗位
		case "deletePost":
			deletePost((SysOmsTempPost) bean,(OmsTempSynResult)otherParams[0],(SyncLog)otherParams[1]);
			break;
		case "deleteDept":
			deleteDept((SysOmsTempDept)bean,(OmsTempSynResult)otherParams[0],(SyncLog)otherParams[1]);
			break;
		default:
			logger.warn("警告！未找到该类型的批处理逻辑：" + type);
			break;
		}
		
	}

	/**
	 * 定时任务批量删除临时表数据
	 * @throws Exception 
	 */
	@Override
	public void deleteTempData() throws Exception {

		
		HQLInfo hqlInfo = new HQLInfo();
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -365);
		date = calendar.getTime();
		hqlInfo.setWhereBlock("beginTime<=:starttime");
		hqlInfo.setParameter("starttime",date);
		List<SysOmsTempTrx> trxList = this.findList(hqlInfo);
		for (SysOmsTempTrx sysOmsTempTrx : trxList) {
			hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("fdTrxId=:fdTrxId");
			hqlInfo.setParameter("fdTrxId",sysOmsTempTrx.getFdId());
			List<SysOmsTempDept> deptList = sysOmsTempDeptService.findList(hqlInfo);
			for (SysOmsTempDept dept : deptList) {
				sysOmsTempDeptService.delete(dept);
			}
			
			List<SysOmsTempPerson> personList = sysOmsTempPersonService.findList(hqlInfo);
			for (SysOmsTempPerson person : personList) {
				sysOmsTempPersonService.delete(person);
			}
			
			List<SysOmsTempPost> postList = sysOmsTempPostService.findList(hqlInfo);
			for (SysOmsTempPost post : postList) {
				sysOmsTempPostService.delete(post);
			}
			
			List<SysOmsTempDp> dpList = sysOmsTempDpService.findList(hqlInfo);
			for (SysOmsTempDp dp : dpList) {
				sysOmsTempDpService.delete(dp);
			}
			
			List<SysOmsTempPp> ppList = sysOmsTempPpService.findList(hqlInfo);
			for (SysOmsTempPp pp : ppList) {
				sysOmsTempPpService.delete(pp);
			}
			
			this.delete(sysOmsTempTrx);
		}
		
	}

	
}
