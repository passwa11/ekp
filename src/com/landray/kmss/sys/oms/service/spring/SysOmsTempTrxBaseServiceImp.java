package com.landray.kmss.sys.oms.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.CacheMode;
import org.hibernate.HibernateException;
import org.hibernate.query.NativeQuery;
import org.springframework.transaction.TransactionStatus;

import com.google.common.collect.Sets;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
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
import com.landray.kmss.sys.oms.service.ISysOmsTempTrxBaseService;
import com.landray.kmss.sys.oms.temp.ISysOmsThreadSynchService;
import com.landray.kmss.sys.oms.temp.OmsTempSynFailType;
import com.landray.kmss.sys.oms.temp.OmsTempSynModel;
import com.landray.kmss.sys.oms.temp.OmsTempSynResult;
import com.landray.kmss.sys.oms.temp.OmsTempSyncThreadExecutor;
import com.landray.kmss.sys.oms.temp.SyncLog;
import com.landray.kmss.sys.oms.temp.SysOmsExcelUtil;
import com.landray.kmss.sys.oms.temp.SysOmsSynConfig;
import com.landray.kmss.sys.oms.temp.SysOmsTempConstants;
import com.landray.kmss.sys.oms.temp.SysOmsTempData;
import com.landray.kmss.sys.oms.temp.SysOmsTempUtil;
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

import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

public class SysOmsTempTrxBaseServiceImp extends ExtendDataServiceImp implements ISysOmsTempTrxBaseService,
ISysOmsThreadSynchService ,SysOmsTempConstants{
	public final static String ORG_TYPE_DEPT = "2:";
	public final static String ORG_TYPE_POST = "4:";
	public final static String ORG_TYPE_PERSON = "8:";
	private Log logger = LogFactory.getLog(SysOmsTempTrxBaseServiceImp.class);
	protected ISysNotifyMainCoreService sysNotifyMainCoreService;
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
    protected ISysOmsTempDeptService sysOmsTempDeptService;
    protected ISysOmsTempPersonService sysOmsTempPersonService;
    protected ISysOmsTempPostService sysOmsTempPostService;
    protected ISysOmsTempPpService sysOmsTempPpService;
    protected ISysOmsTempDpService sysOmsTempDpService;
    protected ISysOrgElementService sysOrgElementService;
    protected ISysOrgDeptService sysOrgDeptService;
    protected ISysOrgPostService sysOrgPostService;
    protected ISysOrgPersonService sysOrgPersonService;
    protected ISysOrgDeptPersonRelationService sysOrgDeptPersonRelationService;

    /**
     * @param fdSynModel 枚举 同步模式
     * @param synConfig 同步配置参数
     */
	@Override
	public OmsTempSynResult<Object> begin(OmsTempSynModel fdSynModel,SysOmsSynConfig synConfig) {
		OmsTempSynResult<Object> result = new OmsTempSynResult<Object>();
		result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL);
		try {
			SysOmsTempTrx trx = new SysOmsTempTrx();
			trx.setBeginTime(new Date());
			trx.setFdSynModel(fdSynModel.getValue());
			trx.setFdSynStatus(SysOmsTempConstants.SYS_OMS_TEMP_SYN_TRX_RUNNING);
			//本次同步事务中，同步配置，本次事务中生效
			if(synConfig.getFdFullSynFlag() == 1) {
				//如果配置为全量同步，则部门人员关系和岗位人员关系必须是全量
				synConfig.setFdPersonPostIsFull(true);
				synConfig.setFdPersonDeptIsFull(true);
			}
			trx.setFdSynConfigJson(JSONObject.fromObject(synConfig).toString());
			
			//校验根部门
			if(StringUtil.isNotNull(synConfig.getFdEkpRootId())) {
				SysOrgDept sysOrgDept = (SysOrgDept) sysOrgDeptService.findByPrimaryKey(synConfig.getFdEkpRootId(),null,true);
				if(sysOrgDept == null) {
					throw new Exception("fdEkpRootId（"+synConfig.getFdEkpRootId()+"），在本系统中找不到，请联系系统管理员");
				}
			}
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
	protected boolean isExistRunningTrx(SysOmsTempTrx tempTrx) throws Exception{
		if(tempTrx != null && tempTrx.getEndTime() == null 
				&& tempTrx.getFdSynStatus() == SysOmsTempConstants.SYS_OMS_TEMP_SYN_TRX_RUNNING){
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * 同步结束，修改同步事务状态
	 * @param trxId
	 * @param errMsg
	 */
	protected void updateTrxStatus(SysOmsTempTrx tempTrx,int trxStatus){
		TransactionStatus status = null;
		try {
			if(tempTrx == null) {
                return;
            }
			status = TransactionUtils.beginNewTransaction();
			tempTrx.setEndTime(new Date());
			tempTrx.setFdSynStatus(trxStatus);
			this.update(tempTrx);
			TransactionUtils.commit(status);
		} catch (Exception e) {
			logger.error("修改事务状态错误,trxId="+tempTrx.getFdId(),e);
			if (status != null) {
				try {
					TransactionUtils.rollback(status);
				} catch (Exception ex) {
					logger.error("---事务回滚出错：{0}---", ex);
				}
			}
			
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
		
		List<SysOmsTempDept> deptList = sysOmsTempDeptService.findListByTrxId(fdTrxId);
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
				//同一个事物中部门ID重复的数据，以最新的为主
				SysOmsTempDept tempDeptTemp = null;
				for (SysOmsTempDept sysOmsTempDept : deptList) {
					if(sysOmsTempDept.getFdDeptId().equals(tempDept.getFdDeptId())) {
						tempDeptTemp = sysOmsTempDept;
						break;
					}
				}
				if(tempDeptTemp == null) {
					tempDept.setFdCreateTime(new Date());
					tempDept.setFdTrxId(fdTrxId);
					tempDept.setFdStatus(SYS_OMS_TEMP_DATA_SYN_STATUS_SUCCESS);
					sysOmsTempDeptService.add(tempDept);
					deptList.add(tempDept);
				}else {
					tempDeptTemp.setFdAlterTime(tempDept.getFdAlterTime());
					tempDeptTemp.setFdIsAvailable(tempDept.getFdIsAvailable());
					tempDeptTemp.setFdName(tempDept.getFdName());
					tempDeptTemp.setFdOrder(tempDept.getFdOrder());
					tempDeptTemp.setFdParentid(tempDept.getFdParentid());
					tempDeptTemp.setFdExtend(tempDept.getFdExtend());
					sysOmsTempDeptService.update(tempDeptTemp);
				}

			}
		}
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
		if(fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_300.getValue()
				&& fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_400.getValue()){
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
		
		List<SysOmsTempPost> postList = sysOmsTempPostService.findListByTrxId(fdTrxId);
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
				result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_WARN);
				logger.warn("岗位有效状态不能为空");
			}else{
				//同一个事物中岗位ID重复的数据，以最新的为主
				SysOmsTempPost tempPostTemp = null;
				for (SysOmsTempPost sysOmsTempPost : postList) {
					if(sysOmsTempPost.getFdPostId().equals(tempPost.getFdPostId())) {
						tempPostTemp = sysOmsTempPost;
						break;
					}
				}
				if(tempPostTemp == null) {
					tempPost.setFdCreateTime(new Date());
					tempPost.setFdTrxId(fdTrxId);
					tempPost.setFdStatus(SYS_OMS_TEMP_DATA_SYN_STATUS_SUCCESS);
					sysOmsTempPostService.add(tempPost);
					postList.add(tempPost);
				}else {
					tempPostTemp.setFdAlterTime(tempPost.getFdAlterTime());
					tempPostTemp.setFdIsAvailable(tempPost.getFdIsAvailable());
					tempPostTemp.setFdName(tempPost.getFdName());
					tempPostTemp.setFdOrder(tempPost.getFdOrder());
					tempPostTemp.setFdParentid(tempPost.getFdParentid());
					tempPostTemp.setFdExtend(tempPost.getFdExtend());
					sysOmsTempPostService.update(tempPostTemp);
				}
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
		List<SysOmsTempPerson> personList = sysOmsTempPersonService.findListByTrxId(fdTrxId);
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
			}else if(StringUtil.isNull(tempPerson.getFdLoginName())){
				illegalDataMap.get(OmsTempSynFailType.DATA_ERR_TYPE_PERSON_LOGIN_NAME).add(tempPerson);
				result.setMsg("警告");  
				result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_WARN);
				logger.warn("人员登录名不能为空");
			}else{
				//同一个事物中人员ID重复的数据，以最新的为主
				SysOmsTempPerson tempPersonTemp = null;
				for (SysOmsTempPerson sysOmsTempPerson : personList) {
					if(sysOmsTempPerson.getFdPersonId().equals(tempPerson.getFdPersonId())) {
						tempPersonTemp = sysOmsTempPerson;
						break;
					}
				}
				if(tempPersonTemp == null) {
					tempPerson.setFdCreateTime(new Date());
					tempPerson.setFdTrxId(fdTrxId);
					tempPerson.setFdStatus(SYS_OMS_TEMP_DATA_SYN_STATUS_SUCCESS);
					sysOmsTempPersonService.add(tempPerson);
					personList.add(tempPerson);
				}else {
					tempPersonTemp.setFdAlterTime(tempPerson.getFdAlterTime());
					tempPersonTemp.setFdDesc(tempPerson.getFdDesc());
					tempPersonTemp.setFdEmail(tempPerson.getFdEmail());
					tempPersonTemp.setFdExtra(tempPerson.getFdExtra());
					tempPersonTemp.setFdHireDate(tempPerson.getFdHireDate());
					tempPersonTemp.setFdIsAvailable(tempPerson.getFdIsAvailable());
					tempPersonTemp.setFdLoginName(tempPerson.getFdLoginName());
					tempPersonTemp.setFdMobileNo(tempPerson.getFdMobileNo());
					tempPersonTemp.setFdName(tempPerson.getFdName());
					tempPersonTemp.setFdNo(tempPerson.getFdNo());
					tempPersonTemp.setFdOrder(tempPerson.getFdOrder());
					tempPersonTemp.setFdParentid(tempPerson.getFdParentid());
					tempPersonTemp.setFdPosition(tempPerson.getFdPosition());
					tempPersonTemp.setFdSex(tempPerson.getFdSex());
					tempPersonTemp.setFdWorkPhone(tempPerson.getFdWorkPhone());
					tempPersonTemp.setFdExtend(tempPerson.getFdExtend());
					sysOmsTempPersonService.update(tempPersonTemp);
				}
				
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
		if(fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_300.getValue()
				&& fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_400.getValue()){
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
		SysOmsSynConfig synConfig = SysOmsTempUtil.getSynConfig(tempTrx.getFdSynConfigJson());
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
			}else if (!synConfig.getFdPersonPostIsFull() && tempPp.getFdIsAvailable() == null) {
				illegalDataMap.get(OmsTempSynFailType.DATA_ERR_TYPE_IS_AVAILABLE).add(tempPp);
				result.setMsg("警告");  
				result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_WARN);
				logger.warn("岗位人员关系是否全量配置为否，是否有效字段不能为空");
			}else{
				tempPp.setFdTrxId(fdTrxId);
				tempPp.setFdStatus(SYS_OMS_TEMP_DATA_SYN_STATUS_SUCCESS);
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
		if(fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_200.getValue() 
				&& fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_400.getValue()){
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
		SysOmsSynConfig synConfig = SysOmsTempUtil.getSynConfig(tempTrx.getFdSynConfigJson());
		for (SysOmsTempDp tempDp : tempDpList) {
			if (StringUtil.isNull(tempDp.getFdDeptId())) {
				illegalDataMap.get(OmsTempSynFailType.DATA_ERR_TYPE_DEPT_ID).add(tempDp);
				result.setMsg("警告");  
				result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_WARN);
				logger.warn("部门ID不能为空");
			}else if (StringUtil.isNull(tempDp.getFdPersonId())) {
				illegalDataMap.get(OmsTempSynFailType.DATA_ERR_TYPE_PERSON_ID).add(tempDp);
				result.setMsg("警告");  
				result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_WARN);
				logger.warn("人员ID不能为空");
			}else if (!synConfig.getFdPersonDeptIsFull() && tempDp.getFdIsAvailable() == null) {
				illegalDataMap.get(OmsTempSynFailType.DATA_ERR_TYPE_IS_AVAILABLE).add(tempDp);
				result.setMsg("警告");  
				result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_WARN);
				logger.warn("部门人员关系是否全量配置为否，是否有效字段不能为空");
			}else{
				tempDp.setFdTrxId(fdTrxId);
				tempDp.setFdStatus(SYS_OMS_TEMP_DATA_SYN_STATUS_SUCCESS);
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
			log.error("同步异常",e);
		}
		
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
		
		//保存同步日志
		saveSynLog(tempTrx,log);
		return result;
	}
	
	//保存同步日志
	protected void saveSynLog(SysOmsTempTrx tempTrx, SyncLog log) {
		TransactionStatus status = null;
		try {
			status = TransactionUtils.beginNewTransaction();
			//保存同步日志
			String fdOldLogDetail = tempTrx.getFdLogDetail();
			String fdOldLogErr = tempTrx.getFdLogError();
			String fdLogDetail = log.getFdLogDetail();
			String fdLogErr = log.getFdLogError();
			if(StringUtil.isNotNull(fdOldLogDetail)) {
				fdLogDetail = fdOldLogDetail + fdLogDetail;
			}
			if(StringUtil.isNotNull(fdOldLogErr)) {
				fdLogErr = fdOldLogErr + fdLogErr;
			}
			tempTrx.setFdLogDetail(fdLogDetail);
			tempTrx.setFdLogError(fdLogErr);
			this.update(tempTrx);
			TransactionUtils.commit(status);
		} catch (Exception e) {
			logger.error("保存同步日志错误：trxId="+tempTrx.getFdId(),e);
			if (status != null) {
				try {
					TransactionUtils.rollback(status);
				} catch (Exception ex) {
					logger.error("---事务回滚出错：{0}---", ex);
				}
			}
			
		}
		
	}

	//执行同步
	protected void doSync(SysOmsTempTrx tempTrx,OmsTempSynResult<Object> result,SyncLog log) throws Exception{
		int fdSynModel = tempTrx.getFdSynModel();
		
		//1、先加载数据
		SysOmsTempData sysOmsTempData = loadDatas(tempTrx,log);
		sysOmsTempData.setLog(log);
		sysOmsTempData.setResult(result);
		sysOmsTempData.setTempTrx(tempTrx);
		if(sysOmsTempData == null || sysOmsTempData.isEmpty()) {
            return;
        }
		
		//2、校验数据
		if(!checkDatas(tempTrx,sysOmsTempData,result)){
			result.setCode(SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL); 
			return;
		}
		
		//3、比较数据
		compareData(sysOmsTempData,fdSynModel);
		
		//4、同步数据（人员、岗位后续可考虑多线程优化）
		int total = sysOmsTempData.getAddDeptList().size() + sysOmsTempData.getUpdateDeptList().size() + sysOmsTempData.getDelDeptList().size()
				+ sysOmsTempData.getAddPersonList().size() + sysOmsTempData.getUpdatePersonList().size() + sysOmsTempData.getDelPersonList().size()
				+ sysOmsTempData.getAddPostList().size() + sysOmsTempData.getUpdatePostList().size() + sysOmsTempData.getDelPostList().size();
		if(total > 50000) {
			//如果本次同步数据量大于50000，则同步详细日志不写进数据库，两点原因
			//1、耗性能 2、详细日志太大，数据库字段装不下。
			log.setWriteToDb(false);
		}
		
		//新增部门
		createDept(sysOmsTempData.getAddDeptList(),sysOmsTempData);   
		
		if(fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_300.getValue()
				|| fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_400.getValue()
				|| fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_200.getValue()){
			
			//新增岗位
			createPost(sysOmsTempData.getAddPostList(),sysOmsTempData);
			
			//修改岗位
			modifyPost(sysOmsTempData.getUpdatePostList(),sysOmsTempData);
		}
		
		//新增人员
		createPerson(sysOmsTempData.getAddPersonList(),sysOmsTempData);
		
		//修改人员
		modifyPerson(sysOmsTempData.getUpdatePersonList(),sysOmsTempData);

		//修改部门
		modifyDept(sysOmsTempData.getUpdateDeptList(),sysOmsTempData);
		
		//删除人员 
		delPerson(sysOmsTempData.getDelPersonList(),sysOmsTempData);
		
		//删除岗位
		delPost(sysOmsTempData.getDelPostList(),sysOmsTempData);
		
		//删除部门
		delDept(sysOmsTempData.getDelDeptList(),sysOmsTempData);
		
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
						//只要有1条数据失败，则警告
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
								sysOmsTempDp = (SysOmsTempDp) sysOmsTempDpService.findByPrimaryKey(sysOmsTempDp.getFdId());
								sysOmsTempDp.setFdStatus(SYS_OMS_TEMP_DATA_SYN_STATUS_FAIL);
								sysOmsTempDp.setFdFailReason(key.getValue());
								sysOmsTempPostService.update(sysOmsTempDp);
							}
						}

					}
				}
				TransactionUtils.commit(status);
			} catch (Exception e) {
				logger.error("修改错误数据同步状态失败",e);
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
	 * @throws Exception 
	 */
	protected SysOrgDept findOrgDeptByImportInfo(String fdImportInfo,SysOmsTempData tempData) throws Exception{
		if(tempData != null) {
			Map<String, String> eleMap = tempData.getSysOrgElementIdMap();
			String fdId = eleMap.get(ORG_TYPE_DEPT+fdImportInfo);
			if(StringUtil.isNotNull(fdId)) {
				return (SysOrgDept) sysOrgDeptService.findByPrimaryKey(fdId);
			}
		}
		
		HQLInfo hqlInfo = new HQLInfo();
		String where = "fdImportInfo=:fdImportInfo";
		hqlInfo.setWhereBlock(where);
		hqlInfo.setParameter("fdImportInfo",fdImportInfo);
		SysOrgDept sysOrgDept = null;
		List<SysOrgDept> sysOrgDeptList = sysOrgDeptService.findList(hqlInfo);
		if(sysOrgDeptList != null && !sysOrgDeptList.isEmpty()) {
            sysOrgDept = sysOrgDeptList.get(0);
        }
		return sysOrgDept;
	}
	
	/**
	 * 通过外部系统id获取post
	 * @param fdImportInfo
	 * @return
	 * @throws Exception 
	 */
	protected SysOrgPost findOrgPostByImportInfo(String fdImportInfo,SysOmsTempData tempData) throws Exception{
		if(tempData != null) {
			Map<String, String> eleMap = tempData.getSysOrgElementIdMap();
			String fdId = eleMap.get(ORG_TYPE_POST+fdImportInfo);
			if(StringUtil.isNotNull(fdId)) {
				return (SysOrgPost) sysOrgPostService.findByPrimaryKey(fdId);
			}
		}

		HQLInfo hqlInfo = new HQLInfo();
		String where = "fdImportInfo=:fdImportInfo";
		hqlInfo.setWhereBlock(where);
		hqlInfo.setParameter("fdImportInfo",fdImportInfo);
		SysOrgPost sysOrgPost = null;
		List<SysOrgPost> sysOrgPostList = sysOrgPostService.findList(hqlInfo);
		if(sysOrgPostList != null && !sysOrgPostList.isEmpty()) {
            sysOrgPost = sysOrgPostList.get(0);
        }
		
		return sysOrgPost;
	}
	
	/**
	 * 通过外部系统id获取person
	 * @param fdImportInfo
	 * @return
	 * @throws Exception 
	 */
	protected SysOrgPerson findOrgPersonByImportInfo(String fdImportInfo,SysOmsTempData tempData) throws Exception{
		if(tempData != null) {
			Map<String, String> eleMap = tempData.getSysOrgElementIdMap();
			String fdId = eleMap.get(ORG_TYPE_PERSON+fdImportInfo);
			if(StringUtil.isNotNull(fdId)) {
				return (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(fdId);
			}
		}
		SysOrgPerson sysOrgPerson = null;
		HQLInfo hqlInfo = new HQLInfo();
		String where = "fdImportInfo=:fdImportInfo";
		hqlInfo.setWhereBlock(where);
		hqlInfo.setParameter("fdImportInfo",fdImportInfo);
		List<SysOrgPerson> sysOrgPersonList = sysOrgPersonService.findList(hqlInfo);
		if(sysOrgPersonList != null && !sysOrgPersonList.isEmpty()) {
            sysOrgPerson = sysOrgPersonList.get(0);
        }
		return sysOrgPerson;
	}
	
	/**
	 * 通过fdLoginName获取person
	 * @param fdLoginName
	 * @return
	 * @throws Exception 
	 */
	protected SysOrgPerson findOrgPersonByLoginName(String fdLoginName,SysOmsTempData tempData) throws Exception{
		SysOrgPerson sysOrgPerson = null;
		if(StringUtil.isNull(fdLoginName)) {
            return null;
        }
		Map<String, String> loginNameMap = tempData.getLoginNameMap();
		String fdId = loginNameMap.get(fdLoginName);
		if(StringUtil.isNotNull(fdId)) {
			return (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(fdId);
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
	protected SysOmsTempData loadDatas(SysOmsTempTrx tempTrx,SyncLog log) throws Exception{
		TransactionStatus status = null;
		SysOmsTempData sysOmsTempData = new SysOmsTempData(tempTrx);
		long starttime = System.currentTimeMillis();
		log.info("开始加载数据...");
		try {
			status = TransactionUtils.beginNewTransaction();
			int fdSynModel = tempTrx.getFdSynModel();
			String fdTrxId = tempTrx.getFdId();
			Map<String, SysOmsTempPerson> tempPersonMap = new HashMap<String, SysOmsTempPerson>();
			Map<String, SysOmsTempDept> tempDeptMap = new HashMap<String, SysOmsTempDept>();
			Map<String, SysOmsTempPost> tempPostMap = new HashMap<String, SysOmsTempPost>();

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
			
			if(fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_300.getValue()
					|| fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_400.getValue()){
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
			
			if(fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_200.getValue()
					|| fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_400.getValue()){
				//部门人员关系
				List<SysOmsTempDp> tempDpList = sysOmsTempDpService.findListByTrxId(fdTrxId);
				sysOmsTempData.setTempDpList(tempDpList);
			}
			
			//加载EKP组织架构数据
			loadEKPOrgData(sysOmsTempData);
		
			
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
			
		}finally {
			StringBuffer logStr = new StringBuffer();
			logStr.append("总共获取部门："+sysOmsTempData.getTempDeptList().size()+"个，");
			logStr.append("岗位："+sysOmsTempData.getTempPostList().size()+"个，");
			logStr.append("人员："+sysOmsTempData.getTempPersonList().size()+"个，");
			logStr.append("岗位人员关系："+sysOmsTempData.getTempPpList().size()+"个，");
			logStr.append("部门人员关系："+sysOmsTempData.getTempDpList().size()+"个");
			log.info(logStr.toString());
			log.info("加载数据结束，总共耗时："+(System.currentTimeMillis()-starttime)+"ms");
		}
		return sysOmsTempData;
	}
	
	/**
	 * 加载EKP组织架构数据
	 * @param sysOmsTempData
	 * @throws HibernateException
	 * @throws Exception
	 */
	protected void loadEKPOrgData(SysOmsTempData sysOmsTempData) throws HibernateException, Exception{
		//全部组织数据（包含无效）
		Map<String,String> sysOrgElementIdMap = new HashMap<String,String>();
		//全部有效组织数据
		Map<String,String> availableSysOrgElementIdMap = new HashMap<String,String>();
		List<Object[]> elementList = sysOrgElementService.getBaseDao().getHibernateSession()
				.createSQLQuery("SELECT fd_id,fd_is_available,fd_import_info,fd_org_type from sys_org_element"
						+ " where fd_import_info is not null and (fd_org_type=8 or fd_org_type=2 or fd_org_type=4)").list();
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
			key = fdOrgType+":"+fdImportInfo;
			sysOrgElementIdMap.put(key,fdId);
			if(fdIsAvailable){
				availableSysOrgElementIdMap.put(key,fdId);
			}
		}
		sysOmsTempData.setSysOrgElementIdMap(sysOrgElementIdMap);
		sysOmsTempData.setAvailableSysOrgElementIdMap(availableSysOrgElementIdMap);
		
		//加载人员手机号
		Map<String, String> loginNameMap = sysOmsTempData.getLoginNameMap();
		String sql = "SELECT person.fd_id,person.fd_login_name from sys_org_person person,sys_org_element ele WHERE ele.fd_id=person.fd_id and ele.fd_is_available=1";
//		List<Object[]> personList = sysOrgElementService.getBaseDao().getHibernateSession()
//				.createNativeQuery(sql).list();
		NativeQuery query = sysOrgElementService.getBaseDao().getHibernateSession()
				.createNativeQuery(sql);
		// 启用二级缓存
		query.setCacheable(true);
		// 设置缓存模式
		query.setCacheMode(CacheMode.NORMAL);
		// 设置缓存区域
		query.setCacheRegion("sys-oms");
		List<Object[]> personList =query.list();

		Object fdLoginNameObj = null;
		for (Object[] obj : personList) {
			fdId = (String)obj[0];
			fdLoginNameObj = obj[1];
			if(fdLoginNameObj != null) {
				loginNameMap.put((String)fdLoginNameObj, fdId);
			}
			
		}
	}
	
	/**
	 * 1、校验数据：不合法则直接抛弃，并且通过参数result返回不合法的数据
	 * 规则：部门：父部门id不存在则直接抛弃；人员：所属部门ID不存在则直接抛弃；岗位：所属部门ID不存在则直接抛弃
	 * 岗位人员关系：岗位或者人员ID不存在，则直接抛弃；部门人员关系：部门或者人员ID不存在，则直接抛弃
	 * 2、将部门和岗位人员关系解析成人员的部门和岗位列表属性
	 * @param tempTrx
	 * @param sysOmsTempData
	 */
	private boolean checkDatas(SysOmsTempTrx sysOmsTempTrx,SysOmsTempData sysOmsTempData,OmsTempSynResult<Object> result){
		TransactionStatus status = null;
		long starttime = System.currentTimeMillis();
		logger.warn("开始校验数据...");
		try {
			status = TransactionUtils.beginNewTransaction();
			//校验部门
			checkDept(sysOmsTempData,result);

			//校验人员
			checkPerson(sysOmsTempTrx,sysOmsTempData,result);
		
			//校验岗位
			checkPost(sysOmsTempTrx,sysOmsTempData,result);
			
			//校验岗位人员关系
			checkPostPerson(sysOmsTempTrx,sysOmsTempData,result);
			
			//校验部门人员关系
			checkDeptPerson(sysOmsTempTrx,sysOmsTempData,result);
			
			//将岗位人员关系取出放入人员的岗位列表属性中
			handPostPersonToPerson(sysOmsTempTrx,sysOmsTempData,result);
			
			//将部门人员关系取出放入人员的部门列表属性中
			handDeptPersonToPerson(sysOmsTempTrx,sysOmsTempData,result);
			
			if(!result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_DEPT_ID_DUPLICATE).isEmpty()
					|| !result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_POST_ID_DUPLICATE).isEmpty()
					|| !result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_PERSON_ID_DUPLICATE).isEmpty()){
				logger.warn("本次同步事务中，有重复的数据，同步停止！！！");
				result.setMsg("本次同步事务中，有重复的数据，同步停止！！！");
				return false;
			}
			
			TransactionUtils.commit(status);
		} catch (Exception e) {
			logger.error("校验数据出错",e);
			if (status != null) {
				try {
					TransactionUtils.rollback(status);
				} catch (Exception ex) {
					logger.error("---事务回滚出错：{0}---", ex);
				}
			}
			result.setMsg("校验数据出错："+e.getMessage());
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
	private void checkPerson(SysOmsTempTrx sysOmsTempTrx,SysOmsTempData sysOmsTempData,OmsTempSynResult<Object> result){
		Map<String, SysOmsTempPerson> tempPersonMap = sysOmsTempData.getTempPersonMap();
		Map<String, SysOmsTempDept> tempDeptMap = sysOmsTempData.getTempDeptMap();
		Map<String,String> sysOrgElementIdMap = sysOmsTempData.getSysOrgElementIdMap();
		SysOmsTempDept tempDept = null;
		List<SysOmsTempPerson> tempPersonList = sysOmsTempData.getTempPersonList();
		Set<String> personTempIdSet = new HashSet<String>();
		SysOmsSynConfig synConfig = SysOmsTempUtil.getSynConfig(sysOmsTempTrx.getFdSynConfigJson());
		int synModel = sysOmsTempTrx.getFdSynModel();
		for (Iterator<SysOmsTempPerson> iterator = tempPersonList.iterator(); iterator.hasNext();) {
			SysOmsTempPerson sysOmsTempPerson = iterator.next();
			//1、人员ID重复，同步停止
			if(personTempIdSet.contains(sysOmsTempPerson.getFdPersonId())){
				result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_PERSON_ID_DUPLICATE).add(sysOmsTempPerson);
				logger.warn("本次同步事务中，人员ID重复：fdPersonId："
					+sysOmsTempPerson.getFdPersonId()+"，fdParentid："+sysOmsTempPerson.getFdParentid()
					+"，fdName："+sysOmsTempPerson.getFdName());
				continue;
			}
			
			//模式200下，配置为主部门无效，则忽略所有主部门
			if(synModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_200.getValue() && !synConfig.getFdPersonIsMainDept()){
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
	}
	
	/**
	 * 1、岗位ID重复，同步停止
	 * 2、所属部门ID不为空并且在临时表和EKP组织架构都找不到，则丢掉该岗位，同步继续
	 * 校验岗位
	 * @param sysOmsTempData
	 * @param result
	 */
	private void checkPost(SysOmsTempTrx sysOmsTempTrx,SysOmsTempData sysOmsTempData,OmsTempSynResult<Object> result){
		int fdSynModel = sysOmsTempTrx.getFdSynModel();
		if(fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_300.getValue()
				&& fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_400.getValue()){
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
	private void checkPostPerson(SysOmsTempTrx sysOmsTempTrx,SysOmsTempData sysOmsTempData,OmsTempSynResult<Object> result){
		int fdSynModel = sysOmsTempTrx.getFdSynModel();
		if(fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_300.getValue()
				&& fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_400.getValue()){
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
			
			//1、该关系所关联的人在临时表和EKP组织架构都找不到，则丢掉该关系
			tempPerson = tempPersonMap.get(sysOmsTempPp.getFdPersonId());
			if((tempPerson == null || !tempPerson.getFdIsAvailable()) && !sysOrgElementIdMap.containsKey(ORG_TYPE_PERSON+sysOmsTempPp.getFdPersonId())){
				result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_PERSON_ID_NOT_FOUND).add(sysOmsTempPp);
				logger.warn("该关系所关联的人在临时表和EKP组织架构都找不到，丢弃：fdPostId："+sysOmsTempPp.getFdPostId()+"，fdPersonId："+sysOmsTempPp.getFdPersonId());
				iterator.remove();
				continue;
			}
			
			//2、该关系所关联的岗位在临时表和EKP组织架构都找不到，则丢掉该关系
			tempPost = tempPostMap.get(sysOmsTempPp.getFdPostId());
			if((tempPost == null || !tempPost.getFdIsAvailable()) && !sysOrgElementIdMap.containsKey(ORG_TYPE_POST+sysOmsTempPp.getFdPostId())){
				result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_POST_ID_NOT_FOUND).add(sysOmsTempPp);
				logger.warn("该关系所关联的岗位在临时表和EKP组织架构都找不到，丢弃：fdPostId："+sysOmsTempPp.getFdPostId()+"，fdPersonId："+sysOmsTempPp.getFdPersonId());
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
	private void checkDeptPerson(SysOmsTempTrx sysOmsTempTrx,SysOmsTempData sysOmsTempData,OmsTempSynResult<Object> result) throws Exception{
		int fdSynModel = sysOmsTempTrx.getFdSynModel();
		if(fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_200.getValue()
				&& fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_400.getValue()){
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
			//该关系所关联的人在临时表和EKP组织架构都找不到，则丢掉该关系
			tempPerson = tempPersonMap.get(sysOmsTempDp.getFdPersonId());
			if((tempPerson == null || !tempPerson.getFdIsAvailable()) && !sysOrgElementIdMap.containsKey(ORG_TYPE_PERSON+sysOmsTempDp.getFdPersonId())){
				result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_PERSON_ID_NOT_FOUND).add(sysOmsTempDp);
				logger.warn("该关系所关联的人在临时表和EKP组织架构都找不到，丢弃：fdDeptId："+sysOmsTempDp.getFdDeptId()+"，fdPersonId："+sysOmsTempDp.getFdPersonId());
				iterator.remove();
				continue;
			}
			
			//该关系所关联的部门在临时表和EKP组织架构都找不到，则丢掉该关系
			tempDept = tempDeptMap.get(sysOmsTempDp.getFdDeptId());
			if((tempDept == null || !tempDept.getFdIsAvailable()) && !sysOrgElementIdMap.containsKey(ORG_TYPE_DEPT+sysOmsTempDp.getFdDeptId())){
				result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_DEPT_ID_NOT_FOUND).add(sysOmsTempDp);
				logger.warn("该关系所关联的部门在临时表和EKP组织架构都找不到，丢弃：fdDeptId："+sysOmsTempDp.getFdDeptId()+"，fdPersonId："+sysOmsTempDp.getFdPersonId());
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
	 * @throws Exception 
	 */
	private void handPostPersonToPerson(SysOmsTempTrx sysOmsTempTrx, SysOmsTempData sysOmsTempData,
			OmsTempSynResult<Object> result) throws Exception {
		int fdSynModel = sysOmsTempTrx.getFdSynModel();
		if(fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_300.getValue()
				&& fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_400.getValue()){
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
				tempPerson = getTempPerosnByPersonId(sysOmsTempPp.getFdPersonId(),sysOmsTempData);
				if(tempPerson != null){
					tempPersonList.add(tempPerson);
					tempPersonMap.put(sysOmsTempPp.getFdPersonId(), tempPerson);
				}
			}
			
			//如果该条关系正常，则将该关系加入人员中
			if(tempPerson != null && !tempPerson.getPostIdList().contains(sysOmsTempPp)){
				if(sysOmsTempData.getSynConfig().getFdFullSynFlag()==1) {
					sysOmsTempPp.setFdIsAvailable(true);
				}
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
	private void handDeptPersonToPerson(SysOmsTempTrx sysOmsTempTrx, SysOmsTempData sysOmsTempData, OmsTempSynResult<Object> result) throws Exception {
		int fdSynModel = sysOmsTempTrx.getFdSynModel();
		SysOmsSynConfig synConfig = SysOmsTempUtil.getSynConfig(sysOmsTempTrx.getFdSynConfigJson());
		if(fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_200.getValue()
				&& fdSynModel != OmsTempSynModel.OMS_TEMP_SYN_MODEL_400.getValue()){
			return;
		}
		SysOmsTempPerson tempPerson = null;
		SysOmsTempDept tempDept = null;
		Map<String, SysOmsTempPerson> tempPersonMap = sysOmsTempData.getTempPersonMap();
		Map<String, SysOmsTempDept> tempDeptMap = sysOmsTempData.getTempDeptMap();
		List<SysOmsTempDp> tempDpList = sysOmsTempData.getTempDpList();
		List<SysOmsTempPerson> tempPersonList = sysOmsTempData.getTempPersonList();
		
		//模式200下，主部门无效，则选择其中一个部门为用户主部门
		if(fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_200.getValue() && !synConfig.getFdPersonIsMainDept()){
			for (Iterator<SysOmsTempDp> iterator = tempDpList.iterator(); iterator.hasNext();) {
				SysOmsTempDp sysOmsTempDp = iterator.next();
				tempPerson = tempPersonMap.get(sysOmsTempDp.getFdPersonId());
				//如果本次增量数据中没有人员，则从EKP组织架构中查出该人员
				if(tempPerson == null){
					tempPerson = getTempPerosnByPersonId(sysOmsTempDp.getFdPersonId(),sysOmsTempData);
					if(tempPerson != null){
						tempPersonMap.put(sysOmsTempDp.getFdPersonId(), tempPerson);
					}
				}
				
				tempDept = tempDeptMap.get(sysOmsTempDp.getFdDeptId());
				//如果本次增量数据中没有部门，则从EKP组织架构中查出该部门
				if(tempDept == null){
					tempDept = getTempDeptByDeptId(sysOmsTempDp.getFdDeptId(),sysOmsTempData);
					if(tempDept != null) {
						tempDeptMap.put(sysOmsTempDp.getFdDeptId(), tempDept);
					}
				}
				
				setTempMainDept(synConfig,sysOmsTempData,sysOmsTempDp,tempPerson,tempDept,iterator);
				
			}
		}
	
		for (SysOmsTempDp sysOmsTempDp : tempDpList) {
			tempPerson = tempPersonMap.get(sysOmsTempDp.getFdPersonId());
			//如果本次增量数据中没有人员，则从EKP组织架构中查出该人员
			if(tempPerson == null){
				tempPerson = getTempPerosnByPersonId(sysOmsTempDp.getFdPersonId(),sysOmsTempData);
				if(tempPerson != null){
					tempPersonList.add(tempPerson);
					tempPersonMap.put(sysOmsTempDp.getFdPersonId(), tempPerson);
				}
			}
			
			tempDept = tempDeptMap.get(sysOmsTempDp.getFdDeptId());
			//如果本次增量数据中没有部门，则从EKP组织架构中查出该部门
			if(tempDept == null){
				tempDept = getTempDeptByDeptId(sysOmsTempDp.getFdDeptId(),sysOmsTempData);
				if(tempDept != null) {
					tempDeptMap.put(sysOmsTempDp.getFdDeptId(), tempDept);
				}
			}

			handOmsTempDp(sysOmsTempTrx,sysOmsTempData,sysOmsTempDp,tempPerson,tempDept,result);
	
		}
	}
	
	/**
	 * 200模式下，人员的主部门属性无用，系统自动选择其中一个部门为用户主部门，
	 * 如果果该人员在EKP中主部门是空，或者该人员主部门不在此次增量的
	 * 部门人员关系中，则需要随机选择一个部门为该用户的主部门
	 * @param fdSynModel
	 * @param sysOmsTempData
	 * @param sysOmsTempDp
	 * @param tempPerson
	 * @param tempDept
	 * @throws Exception 
	 */
	private void setTempMainDept(SysOmsSynConfig synConfig,SysOmsTempData sysOmsTempData,
			SysOmsTempDp sysOmsTempDp,SysOmsTempPerson tempPerson,SysOmsTempDept tempDept,Iterator<SysOmsTempDp> iterator) throws Exception{
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
			SysOrgPerson sysOrgPerson = findOrgPersonByImportInfo(tempPerson.getFdPersonId(),sysOmsTempData);
			if(sysOrgPerson != null){
				SysOrgElement fdParent = sysOrgPerson.getFdParent();
				if(fdParent != null){
					if(isContainsTempDeptByDeptId(synConfig,tempDpList,tempPerson.getFdPersonId(),fdParent.getFdImportInfo())){
						tempPerson.setFdParentid(fdParent.getFdImportInfo());
					}
				}
			}
		}
		
		if(StringUtil.isNull(tempPerson.getFdParentid())){
			if(synConfig.getFdPersonDeptIsFull()) {
				tempPerson.setFdParentid(sysOmsTempDp.getFdDeptId());
				tempPerson.setFdOrder(sysOmsTempDp.getFdOrder());
				iterator.remove();
			}else if(sysOmsTempDp.getFdIsAvailable()) {
				tempPerson.setFdParentid(sysOmsTempDp.getFdDeptId());
				tempPerson.setFdOrder(sysOmsTempDp.getFdOrder());
				iterator.remove();
			}
		
		}else if(tempPerson.getFdParentid().equals(sysOmsTempDp.getFdDeptId())){
			tempPerson.setFdOrder(sysOmsTempDp.getFdOrder());
			iterator.remove();
		}

	}

	/**
	 * 处理部门人员关系
	 * 1、将部门人员关系中的排序号加入人员的部门排序号属性中
	 * 2、模式200 将部门转换为岗位
	 * @param fdSynModel
	 * @param sysOmsTempData
	 * @param sysOmsTempDp
	 * @param tempPerson
	 * @param result
	 * @throws Exception
	 */
	private void handOmsTempDp(SysOmsTempTrx sysOmsTempTrx,SysOmsTempData sysOmsTempData,SysOmsTempDp sysOmsTempDp,SysOmsTempPerson tempPerson,SysOmsTempDept tempDept,OmsTempSynResult<Object> result) throws Exception{
		List<SysOmsTempPost> tempPostList = sysOmsTempData.getTempPostList();
		int fdSynModel = sysOmsTempTrx.getFdSynModel();
		
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
			if(sysOmsTempData.getSynConfig().getFdFullSynFlag()==1) {
				sysOmsTempDp.setFdIsAvailable(true);
			}
			tempPerson.getDeptIdList().add(sysOmsTempDp);
		}
		
		//2、如果是模式200则需要将部门转换为岗位
		if(fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_200.getValue()){

			//先创建岗位
			SysOmsTempPost sysOmsTempPost = getTempPostByDeptIdAndPostName(tempPostList,sysOmsTempDp.getFdDeptId(),SysOmsTempConstants.SYS_ORG_POST_NAME);
			if(sysOmsTempPost == null){
				SysOrgPost sysOrgPost = getSysOrgPostByDeptIdAndPostName(sysOmsTempDp.getFdDeptId(),SysOmsTempConstants.SYS_ORG_POST_NAME);
				if(sysOrgPost == null) {
					sysOmsTempPost = new SysOmsTempPost();
					sysOmsTempPost.setFdAlterTime(new Date().getTime());
					sysOmsTempPost.setFdCreateTime(new Date());
					sysOmsTempPost.setFdIsAvailable(true);
					sysOmsTempPost.setFdName(SysOmsTempConstants.SYS_ORG_POST_NAME);
					sysOmsTempPost.setFdParentid(sysOmsTempDp.getFdDeptId());
					sysOmsTempPost.setFdPostId(sysOmsTempPost.getFdId());
					tempPostList.add(sysOmsTempPost);
				}
			}
			
		}
	}

	/**
	 * 通过部门ID判断tempDpList是否存在该部门
	 * @param tempDpList
	 * @param fdDeptId
	 * @return
	 */
	private boolean isContainsTempDeptByDeptId(SysOmsSynConfig synConfig,List<SysOmsTempDp> tempDpList,String fdPersonId,String fdDeptId){
		if(synConfig.getFdPersonDeptIsFull()) {
			for (SysOmsTempDp sysOmsTempDp : tempDpList) {
				if(fdPersonId.equals(sysOmsTempDp.getFdPersonId()) && fdDeptId.equals(sysOmsTempDp.getFdDeptId())){
					return true;
				}
			}
			return false;
		}else {
			for (SysOmsTempDp sysOmsTempDp : tempDpList) {
				if(fdPersonId.equals(sysOmsTempDp.getFdPersonId()) && fdDeptId.equals(sysOmsTempDp.getFdDeptId()) && !sysOmsTempDp.getFdIsAvailable()){
					return false;
				}
			}
			return true;
		}
	

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
	
	private SysOmsTempPerson getTempPerosnByPersonId(String personId,SysOmsTempData tempData) throws Exception{
		SysOmsTempPerson tempPerson = null;
		SysOrgPerson sysOrgPerson = findOrgPersonByImportInfo(personId,tempData);
		if(sysOrgPerson != null){
			tempPerson = new SysOmsTempPerson();
			tempPerson.setFdPersonId(personId);
			tempPerson.setFdAlterTime(new Date().getTime());
			tempPerson.setFdEmail(sysOrgPerson.getFdEmail());
			if(sysOrgPerson.getCustomPropMap() != null){
				tempPerson.setFdExtra(JSONObject.fromObject(sysOrgPerson.getCustomPropMap()).toString());
			}
			tempPerson.setFdIsAvailable(sysOrgPerson.getFdIsAvailable());
			tempPerson.setFdMobileNo(sysOrgPerson.getFdMobileNo());
			tempPerson.setFdName(sysOrgPerson.getFdName());
			tempPerson.setFdOrder(sysOrgPerson.getFdOrder() == null?null:sysOrgPerson.getFdOrder());
			if(sysOrgPerson.getFdParent() != null) {
				tempPerson.setFdParentid(sysOrgPerson.getFdParent().getFdImportInfo());
			}
			tempPerson.setFdSex(sysOrgPerson.getFdSex());
			tempPerson.setFdLoginName(sysOrgPerson.getFdLoginName());
			tempPerson.setFdNo(sysOrgPerson.getFdNo());
			tempPerson.setFdDesc(sysOrgPerson.getFdMemo());
			tempPerson.setFdHireDate(sysOrgPerson.getFdHiredate()==null?null:sysOrgPerson.getFdHiredate().getTime());
			tempPerson.setFdWorkPhone(sysOrgPerson.getFdWorkPhone());
		}
		return tempPerson;
	}
	
	private SysOmsTempDept getTempDeptByDeptId(String deptId,SysOmsTempData tempData) throws Exception{
		SysOmsTempDept sysOmsTempDept = null;
		SysOrgDept sysOrgDept = findOrgDeptByImportInfo(deptId,tempData);
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
	
	/**
	 * 比较数据，获取对应的增删改的列表
	 * @param sysOmsTempData
	 */
	private void compareData(SysOmsTempData sysOmsTempData,int fdSynModel) {
		long starttime = System.currentTimeMillis();
		SyncLog log = sysOmsTempData.getLog();
		log.info("开始对比数据...");
		Map<String,String> sysOrgElementIdMap = sysOmsTempData.getSysOrgElementIdMap();
		//1、比较部门
		List<SysOmsTempDept> tempDeptList = sysOmsTempData.getTempDeptList();
		List<SysOmsTempDept> addDeptList = new ArrayList<SysOmsTempDept>();
		List<SysOmsTempDept> updateDeptList = new ArrayList<SysOmsTempDept>();
		List<SysOmsTempDept> delDeptList = new ArrayList<SysOmsTempDept>();
		sysOmsTempData.setAddDeptList(addDeptList);
		sysOmsTempData.setUpdateDeptList(updateDeptList);
		sysOmsTempData.setDelDeptList(delDeptList);
		if(sysOmsTempData.getSynConfig().getFdFullSynFlag()==0) {
			for (Iterator<SysOmsTempDept> iterator = tempDeptList.iterator(); iterator.hasNext();) {
				SysOmsTempDept sysOmsTempDept = (SysOmsTempDept) iterator.next();
				if (!sysOrgElementIdMap.containsKey(ORG_TYPE_DEPT+sysOmsTempDept.getFdDeptId())) {
					if(sysOmsTempDept.getFdIsAvailable()) {
						addDeptList.add(sysOmsTempDept);
					}else {
						logger.warn("部门："+sysOmsTempDept.getFdName()+"，部门ID"+sysOmsTempDept.getFdDeptId()+"，无效，并且不在组织架构中，丢弃。");
					}
				}else if(sysOmsTempDept.getFdIsAvailable()) {
					updateDeptList.add(sysOmsTempDept);
				}else {
					delDeptList.add(sysOmsTempDept);
				}
				iterator.remove();
			}
		}else {  
			//EKP所有有效部门ID集合
			Set<String> ekpDeptIdSet = new HashSet<String>();
			//数据源所有有效部门ID结合
			Set<String> tempDeptIdSet = new HashSet<String>();
			//fdDeptDelStrategy==0 则所有部门均为有效
			for (Iterator<SysOmsTempDept> iterator = tempDeptList.iterator(); iterator.hasNext();) {
				SysOmsTempDept sysOmsTempDept = (SysOmsTempDept) iterator.next();
				if (!sysOrgElementIdMap.containsKey(ORG_TYPE_DEPT+sysOmsTempDept.getFdDeptId())) {
					addDeptList.add(sysOmsTempDept);
				}else {
					updateDeptList.add(sysOmsTempDept);
					tempDeptIdSet.add(sysOmsTempDept.getFdDeptId());
				}
				
			}
			Map<String,String> availableSysOrgElementIdMap = sysOmsTempData.getAvailableSysOrgElementIdMap();
			for (String importInfo : availableSysOrgElementIdMap.keySet()) {
				if(importInfo.startsWith(ORG_TYPE_DEPT)) {
					ekpDeptIdSet.add(importInfo.split(":")[1]);
				}
			}
			//找出EKP要删除的
			Set<String> ekpDeptDel = Sets.difference(ekpDeptIdSet, tempDeptIdSet);
			for (String fdDeptId : ekpDeptDel) {
				SysOmsTempDept sysOmsTempDept = new SysOmsTempDept();
				sysOmsTempDept.setFdDeptId(fdDeptId);
				sysOmsTempDept.setFdIsAvailable(false);
				delDeptList.add(sysOmsTempDept);
			}
		}
		
		
		log.info("待新增部门："+addDeptList.size()+"个，待修改部门："+updateDeptList.size()+"个，待删除部门："+delDeptList.size()+"个");
		
		//2、比较岗位
		if(fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_300.getValue()
				|| fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_400.getValue()
				|| fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_200.getValue()){
			List<SysOmsTempPost> tempPostList = sysOmsTempData.getTempPostList();
			List<SysOmsTempPost> addPostList = new ArrayList<SysOmsTempPost>();
			List<SysOmsTempPost> updatePostList = new ArrayList<SysOmsTempPost>();
			List<SysOmsTempPost> delPostList = new ArrayList<SysOmsTempPost>();
			sysOmsTempData.setAddPostList(addPostList);
			sysOmsTempData.setUpdatePostList(updatePostList);
			sysOmsTempData.setDelPostList(delPostList);
			
			if(sysOmsTempData.getSynConfig().getFdFullSynFlag()==0
					//模式200岗位只有“成员”岗，不需要全量对比删除
			 || fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_200.getValue() ) {
				for (Iterator<SysOmsTempPost> iterator = tempPostList.iterator(); iterator.hasNext();) {
					SysOmsTempPost sysOmsTempPost = (SysOmsTempPost) iterator.next();
					if (!sysOrgElementIdMap.containsKey(ORG_TYPE_POST+sysOmsTempPost.getFdPostId())) {
						if(sysOmsTempPost.getFdIsAvailable()) {
							addPostList.add(sysOmsTempPost);
						}else {
							logger.warn("岗位："+sysOmsTempPost.getFdName()+"，岗位ID"+sysOmsTempPost.getFdPostId()+"，无效，并且不在组织架构中，丢弃。");
						}
					}else if(sysOmsTempPost.getFdIsAvailable()) {
						updatePostList.add(sysOmsTempPost);
					}else {
						delPostList.add(sysOmsTempPost);
					}
					iterator.remove();
				}
			}else {
				//EKP所有有效部门ID集合
				Set<String> ekpPostIdSet = new HashSet<String>();
				//数据源所有有效部门ID结合
				Set<String> tempPostIdSet = new HashSet<String>();
				//fdDeptPersonStrategy==0 则所有部门均为有效
				for (Iterator<SysOmsTempPost> iterator = tempPostList.iterator(); iterator.hasNext();) {
					SysOmsTempPost sysOmsTempPost = (SysOmsTempPost) iterator.next();
					if (!sysOrgElementIdMap.containsKey(ORG_TYPE_POST+sysOmsTempPost.getFdPostId())) {
						addPostList.add(sysOmsTempPost);
					}else {
						updatePostList.add(sysOmsTempPost);
						tempPostIdSet.add(sysOmsTempPost.getFdPostId());
					}
					iterator.remove();
				}
				Map<String,String> availableSysOrgElementIdMap = sysOmsTempData.getAvailableSysOrgElementIdMap();
				for (String importInfo : availableSysOrgElementIdMap.keySet()) {
					if(importInfo.startsWith(ORG_TYPE_POST)) {
						ekpPostIdSet.add(importInfo.split(":")[1]);
					}
				}
				//找出EKP要删除的
				Set<String> ekpPostDel = Sets.difference(ekpPostIdSet, tempPostIdSet);
				for (String fdPostId : ekpPostDel) {
					SysOmsTempPost sysOmsTempPost = new SysOmsTempPost();
					sysOmsTempPost.setFdPostId(fdPostId);;
					sysOmsTempPost.setFdIsAvailable(false);
					delPostList.add(sysOmsTempPost);
				}
			}
			
			log.info("待新增岗位："+addPostList.size()+"个，待修改岗位："+updatePostList.size()+"个，待删除岗位："+delPostList.size()+"个");
		}

		//3、比较人员
		List<SysOmsTempPerson> tempPersonList = sysOmsTempData.getTempPersonList();
		List<SysOmsTempPerson> addPersonList = new ArrayList<SysOmsTempPerson>();
		List<SysOmsTempPerson> updatePersonList = new ArrayList<SysOmsTempPerson>();
		List<SysOmsTempPerson> delPersonList = new ArrayList<SysOmsTempPerson>();
		sysOmsTempData.setAddPersonList(addPersonList);
		sysOmsTempData.setUpdatePersonList(updatePersonList);
		sysOmsTempData.setDelPersonList(delPersonList);
		if(sysOmsTempData.getSynConfig().getFdFullSynFlag()==0) {
			for (Iterator<SysOmsTempPerson> iterator = tempPersonList.iterator(); iterator.hasNext();) {
				SysOmsTempPerson sysOmsTempPerson = (SysOmsTempPerson) iterator.next();
				if (!sysOrgElementIdMap.containsKey(ORG_TYPE_PERSON+sysOmsTempPerson.getFdPersonId())) {
					if(sysOmsTempPerson.getFdIsAvailable()) {
						addPersonList.add(sysOmsTempPerson);
					}else {
						logger.warn("人员："+sysOmsTempPerson.getFdName()+"，人员ID"+sysOmsTempPerson.getFdPersonId()+"，无效，并且不在组织架构中，丢弃。");
					}
				}else if(sysOmsTempPerson.getFdIsAvailable()) {
					updatePersonList.add(sysOmsTempPerson);
				}else {
					delPersonList.add(sysOmsTempPerson);
				}
				iterator.remove();
			}
		}else {
			//EKP所有有效部门ID集合
			Set<String> ekpPersonIdSet = new HashSet<String>();
			//数据源所有有效部门ID结合
			Set<String> tempPersonIdSet = new HashSet<String>();
			//fdDeptPersonStrategy==0 则所有部门均为有效
			for (Iterator<SysOmsTempPerson> iterator = tempPersonList.iterator(); iterator.hasNext();) {
				SysOmsTempPerson sysOmsTempPerson = (SysOmsTempPerson) iterator.next();
				if (!sysOrgElementIdMap.containsKey(ORG_TYPE_PERSON+sysOmsTempPerson.getFdPersonId())) {
					addPersonList.add(sysOmsTempPerson);
				}else {
					updatePersonList.add(sysOmsTempPerson);
					tempPersonIdSet.add(sysOmsTempPerson.getFdPersonId());
				}
				iterator.remove();
			}
			Map<String,String> availableSysOrgElementIdMap = sysOmsTempData.getAvailableSysOrgElementIdMap();
			for (String importInfo : availableSysOrgElementIdMap.keySet()) {
				if(importInfo.startsWith(ORG_TYPE_PERSON)) {
					ekpPersonIdSet.add(importInfo.split(":")[1]);
				}
			}
			//找出EKP要删除的
			Set<String> ekpPersonDel = Sets.difference(ekpPersonIdSet, tempPersonIdSet);
			for (String fdPersonId : ekpPersonDel) {
				SysOmsTempPerson sysOmsTempPerson = new SysOmsTempPerson();
				sysOmsTempPerson.setFdPersonId(fdPersonId);;
				sysOmsTempPerson.setFdIsAvailable(false);
				delPersonList.add(sysOmsTempPerson);
			}
		}
		
		log.info("待新增人员："+addPersonList.size()+"个，待修改人员："+updatePersonList.size()+"个，待删除人员："+delPersonList.size()+"个");
		
		log.info("对比数据结束，总共耗时："+(System.currentTimeMillis()-starttime)+"ms");
	}
	
	/**
	 * 递归创建部门
	 * @param addDeptList
	 * @param sysOrgElementIdMap
	 */
	private void createDept(List<SysOmsTempDept> addDeptList,SysOmsTempData sysOmsTempData){
		Map<String, SysOmsTempDept> deptMap = new HashMap<String, SysOmsTempDept>();
		for (SysOmsTempDept sysOmsTempDept : addDeptList) {
			deptMap.put(sysOmsTempDept.getFdDeptId(), sysOmsTempDept);
		}
		for (SysOmsTempDept sysOmsTempDept : addDeptList) {
			createDept(sysOmsTempDept,sysOmsTempData,deptMap);
		}
	}
	
	private void createDept(SysOmsTempDept sysOmsTempDept,SysOmsTempData sysOmsTempData,Map<String,SysOmsTempDept> deptMap){
		if(sysOmsTempDept == null) {
            return;
        }
		
		Map<String, String> sysOrgElementIdMap = sysOmsTempData.getSysOrgElementIdMap();
		String fdParentId = sysOmsTempDept.getFdParentid();
		if(StringUtil.isNotNull(fdParentId)){
			if(!sysOrgElementIdMap.containsKey(ORG_TYPE_DEPT+fdParentId)){
				createDept(deptMap.get(fdParentId),sysOmsTempData,deptMap);
			}
		}
		
		if(!sysOrgElementIdMap.containsKey(ORG_TYPE_DEPT+sysOmsTempDept.getFdDeptId())){
			addDept(sysOmsTempDept,sysOmsTempData);
		}

	}

	protected void addDept(SysOmsTempDept sysOmsTempDept,SysOmsTempData sysOmsTempData){
		Map<String,String> sysOrgElementIdMap = sysOmsTempData.getSysOrgElementIdMap();
		SysOmsTempTrx tempTrx = sysOmsTempData.getTempTrx();
		OmsTempSynResult<Object> result = sysOmsTempData.getResult();
		SyncLog log = sysOmsTempData.getLog();
		StringBuffer logStr = new StringBuffer();
		logStr.append("新增部门：");
		logStr.append(sysOmsTempDept.getFdName());
		logStr.append("，部门ID：" + sysOmsTempDept.getFdDeptId());
		logStr.append("，上级部门ID：" +sysOmsTempDept.getFdParentid());
		logStr.append("，部门排序号：" +sysOmsTempDept.getFdOrder());
		TransactionStatus status = null;
		try {
			SysOmsSynConfig synConfig = SysOmsTempUtil.getSynConfig(tempTrx.getFdSynConfigJson());
			status = TransactionUtils.beginNewTransaction();
			SysOrgDept sysOrgDept = findOrgDeptByImportInfo(sysOmsTempDept.getFdDeptId(),sysOmsTempData);
			if(sysOrgDept != null){
				logStr.append("，失败：该部门已经存在");
				log.warn(logStr.toString());
				sysOrgElementIdMap.put(ORG_TYPE_DEPT+sysOmsTempDept.getFdDeptId(),sysOrgDept.getFdId());
				TransactionUtils.commit(status);
				return;
			}
			
			//转换排序号
			sysOmsTempDept.setFdOrder(convertOrder(sysOmsTempDept.getFdOrder(),synConfig.getFdDeptIsAsc()));
			
			sysOrgDept = new SysOrgDept();
			if(StringUtil.isNotNull(sysOmsTempDept.getFdParentid())){
				SysOrgDept parent = findOrgDeptByImportInfo(sysOmsTempDept.getFdParentid(),sysOmsTempData);
				if(parent == null){
					logStr.append("，失败：找不到上级部门");
					TransactionUtils.commit(status);
					return;
				}
				sysOrgDept.setFdParent(parent);
				logStr.append("，上级部门名称："+parent.getFdName());
			}else{
				if(StringUtil.isNotNull(sysOmsTempData.getSynConfig().getFdEkpRootId())) {
					sysOrgDept.setFdParent((SysOrgElement) sysOrgDeptService.findByPrimaryKey(sysOmsTempData.getSynConfig().getFdEkpRootId()));
				}else {
					sysOrgDept.setFdParent(null);
				}
			}
			sysOrgDept.setFdName(sysOmsTempDept.getFdName());
			sysOrgDept.setFdCreateTime(new Date());
			sysOrgDept.setFdImportInfo(sysOmsTempDept.getFdDeptId());
			sysOrgDept.setFdIsAvailable(sysOmsTempDept.getFdIsAvailable());
			sysOrgDept.setFdOrder(sysOmsTempDept.getFdOrder());	
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
	
	private void modifyDept(List<SysOmsTempDept> updateDeptList,SysOmsTempData sysOmsTempData){
		omsTempSyncThreadExecutor.listThreadExecute("updateDept", updateDeptList, 1, this,sysOmsTempData);
	}
	
	protected void updateDept(SysOmsTempDept sysOmsTempDept,SysOmsTempData sysOmsTempData){
		Map<String,String> sysOrgElementIdMap = sysOmsTempData.getSysOrgElementIdMap();
		SysOmsTempTrx tempTrx = sysOmsTempData.getTempTrx();
		OmsTempSynResult<Object> result = sysOmsTempData.getResult();
		SyncLog log = sysOmsTempData.getLog();
		StringBuffer logStr = new StringBuffer();
		logStr.append("修改部门：");
		logStr.append(sysOmsTempDept.getFdName());
		logStr.append("，部门ID：" + sysOmsTempDept.getFdDeptId());
		logStr.append("，上级部门ID：" +sysOmsTempDept.getFdParentid());
		logStr.append("，部门排序号：" +sysOmsTempDept.getFdOrder());
		try {
			SysOmsSynConfig synConfig = SysOmsTempUtil.getSynConfig(tempTrx.getFdSynConfigJson());
			SysOrgDept sysOrgDept = findOrgDeptByImportInfo(sysOmsTempDept.getFdDeptId(),sysOmsTempData);
			if(sysOrgDept == null){
				logStr.append("，失败：组织架构中找不到该部门");
				log.warn(logStr.toString());
				return;
			}
			
			//转换排序号
			sysOmsTempDept.setFdOrder(convertOrder(sysOmsTempDept.getFdOrder(),synConfig.getFdDeptIsAsc()));
			
			//如果配置为数据源不存在更新时间戳，则通过属性对比判断是否更新
			if(synConfig.getFdFullSynFlag()==1 && !isChangeDept(sysOrgDept,sysOmsTempDept,sysOmsTempData.getSynConfig().getFdEkpRootId())) {
				if(logger.isDebugEnabled()) {
					logStr.append("，该部门没有变化，不更新");
					log.info(logStr.toString());
				}
				return;
			}
			
			if(StringUtil.isNotNull(sysOmsTempDept.getFdParentid())){
				SysOrgDept parent = findOrgDeptByImportInfo(sysOmsTempDept.getFdParentid(),sysOmsTempData);
				if(parent == null){
					logStr.append("，失败：组织架构中找不到上级部门");
					log.warn(logStr.toString());
					return;
				}
				sysOrgDept.setFdParent(parent);
				logStr.append("，上级部门名称："+parent.getFdName());
			}else{
				if(StringUtil.isNotNull(sysOmsTempData.getSynConfig().getFdEkpRootId())) {
					sysOrgDept.setFdParent((SysOrgElement) sysOrgDeptService.findByPrimaryKey(sysOmsTempData.getSynConfig().getFdEkpRootId()));
				}else {
					sysOrgDept.setFdParent(null);
				}
			}
			
			sysOrgDept.setFdName(sysOmsTempDept.getFdName());
			sysOrgDept.setFdOrder(sysOmsTempDept.getFdOrder());	
			sysOrgDept.setFdIsAvailable(sysOmsTempDept.getFdIsAvailable());
			sysOrgDeptService.update(sysOrgDept);
			sysOrgElementIdMap.put(ORG_TYPE_DEPT+sysOmsTempDept.getFdDeptId(),sysOrgDept.getFdId());
			logStr.append("，成功");
			log.info(logStr.toString());
		} catch (Exception e) {
			logStr.append("，失败："+e.getMessage());
			log.error(logStr.toString(),e);
			result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_SYN_FAIL).add(sysOmsTempDept);
		}
	}
	
	protected Integer convertOrder(Integer order,boolean isAsc){
		try {
			if(order == null) {
                return null;
            }
			if(isAsc) {
                return order;
            }
			
			Integer maxOrder = 1_000_000_000;
			if(order > maxOrder) {
                return null;
            }
			return maxOrder - order;
		} catch (Exception e) {
			logger.warn("转换排序号错误",e);
		}
		
		return null;
	}
	
	private void createPost(List<SysOmsTempPost> addPostList,SysOmsTempData sysOmsTempData){
		omsTempSyncThreadExecutor.listThreadExecute("addPost", addPostList, 2, this,sysOmsTempData);
	}

	private void addPost(SysOmsTempPost sysOmsTempPost,SysOmsTempData sysOmsTempData) {
		OmsTempSynResult<Object> result = sysOmsTempData.getResult();
		SyncLog log = sysOmsTempData.getLog();
		StringBuffer logStr = new StringBuffer();
		logStr.append("新增岗位：");
		logStr.append(sysOmsTempPost.getFdName());
		logStr.append("，岗位ID：" + sysOmsTempPost.getFdPostId());
		logStr.append("，所属部门ID：" +sysOmsTempPost.getFdParentid());
		try {
			SysOrgPost sysOrgPost = findOrgPostByImportInfo(sysOmsTempPost.getFdPostId(),sysOmsTempData);
			if(sysOrgPost != null){
				logStr.append("，失败：该岗位已经存在");
				log.warn(logStr.toString());
				return;
			}
			
			sysOrgPost = new SysOrgPost();
			if(StringUtil.isNotNull(sysOmsTempPost.getFdParentid())){
				SysOrgDept parent = findOrgDeptByImportInfo(sysOmsTempPost.getFdParentid(),sysOmsTempData);
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
			sysOrgPost.setFdIsAvailable(sysOmsTempPost.getFdIsAvailable());
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
	
	private void modifyPost(List<SysOmsTempPost> updatePostList,SysOmsTempData sysOmsTempData){
		omsTempSyncThreadExecutor.listThreadExecute("updatePost", updatePostList, 2, this,sysOmsTempData);
	}

	private void updatePost(SysOmsTempPost sysOmsTempPost,SysOmsTempData sysOmsTempData) {
		OmsTempSynResult<Object> result = sysOmsTempData.getResult();
		SyncLog log = sysOmsTempData.getLog();
		StringBuffer logStr = new StringBuffer();
		logStr.append("修改岗位：");
		logStr.append(sysOmsTempPost.getFdName());
		logStr.append("，岗位ID：" + sysOmsTempPost.getFdPostId());
		logStr.append("，所属部门ID：" +sysOmsTempPost.getFdParentid());

		try {
			SysOrgPost sysOrgPost = findOrgPostByImportInfo(sysOmsTempPost.getFdPostId(),sysOmsTempData);
			if(sysOrgPost == null){
				logStr.append("，失败：组织架构中找不到该岗位");
				log.warn(logStr.toString());
				return;
			}
			
			//如果配置为数据源不存在更新时间戳，则通过属性对比判断是否更新
			SysOmsTempTrx tempTrx = sysOmsTempData.getTempTrx();
			SysOmsSynConfig synConfig = SysOmsTempUtil.getSynConfig(tempTrx.getFdSynConfigJson());
			if(synConfig.getFdFullSynFlag()==1 && !isChangePost(sysOrgPost,sysOmsTempPost)) {
				if(logger.isDebugEnabled()) {
					logStr.append("，该岗位没有变化，不更新");
					log.info(logStr.toString());
				}
				return;
			}
			
			if(StringUtil.isNotNull(sysOmsTempPost.getFdParentid())){
				SysOrgDept parent = findOrgDeptByImportInfo(sysOmsTempPost.getFdParentid(),sysOmsTempData);
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
			sysOrgPost.setFdIsAvailable(sysOmsTempPost.getFdIsAvailable());
			logStr.append("，成功");
			sysOrgPostService.update(sysOrgPost);
			log.info(logStr.toString());
		} catch (Exception e) {
			logStr.append("，失败："+e.getMessage());
			log.error(logStr.toString(),e);
			result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_SYN_FAIL).add(sysOmsTempPost);
		}
		
	}
	
	private void createPerson(List<SysOmsTempPerson> addPersonList,SysOmsTempData sysOmsTempData) {
		omsTempSyncThreadExecutor.listThreadExecute("addPerson", addPersonList, 1, this,sysOmsTempData);
	}
	
	private void addPerson(SysOmsTempPerson sysOmsTempPerson,SysOmsTempData sysOmsTempData) {
		Map<String,String> sysOrgElementIdMap = sysOmsTempData.getSysOrgElementIdMap();
		Map<String, String> loginNameMap = sysOmsTempData.getLoginNameMap();
		SysOmsTempTrx tempTrx = sysOmsTempData.getTempTrx();
		OmsTempSynResult<Object> result = sysOmsTempData.getResult();
		SyncLog log = sysOmsTempData.getLog();
		int fdSynModel = sysOmsTempData.getTempTrx().getFdSynModel();
		StringBuffer logStr = new StringBuffer();
		logStr.append("新增人员：");
		logStr.append(sysOmsTempPerson.getFdName());
		logStr.append("，人员ID：" + sysOmsTempPerson.getFdPersonId());
		logStr.append("，登录名：" + sysOmsTempPerson.getFdLoginName());
		logStr.append("，所属部门ID：" +sysOmsTempPerson.getFdParentid());
		logStr.append("，主部门排序号：" +sysOmsTempPerson.getFdOrder());
		try {
			long starttime = System.currentTimeMillis();
			SysOmsSynConfig synConfig = SysOmsTempUtil.getSynConfig(tempTrx.getFdSynConfigJson());
			SysOrgPerson sysOrgPerson = findOrgPersonByImportInfo(sysOmsTempPerson.getFdPersonId(),sysOmsTempData);
			if(logger.isDebugEnabled()) {
				logger.debug("通过人员ID获取人员信息耗时："+(System.currentTimeMillis()-starttime)+"ms");
			}
			if(sysOrgPerson != null){
				logStr.append("，失败：该人员已经存在");
				log.warn(logStr.toString());
				return;
			}
			
			//转换排序号
			sysOmsTempPerson.setFdOrder(this.convertOrder(sysOmsTempPerson.getFdOrder(), synConfig.getFdPersonIsAsc()));
			
			//判断登录名重复
			starttime = System.currentTimeMillis();
			SysOrgPerson orgPerson = findOrgPersonByLoginName(sysOmsTempPerson.getFdLoginName(),sysOmsTempData);
			//log.warn("总共耗时0:"+(System.currentTimeMillis()-starttime)+"ms");
			if(orgPerson != null && !sysOmsTempPerson.getFdPersonId().equals(orgPerson.getFdImportInfo())) {
				logStr.append("，该登录名已经存在，直接置为无效");
				sysOmsTempPerson.setFdIsAvailable(false);
			}
			if(logger.isDebugEnabled()) {
				logger.debug("通过人员登录名获取人员信息耗时："+(System.currentTimeMillis()-starttime)+"ms");
			}		
			sysOrgPerson = new SysOrgPerson();
			
			//人员所属主部门		
			handPersonMainDept(sysOmsTempPerson,sysOrgPerson,logStr,sysOmsTempData);
			//人员基本信息
			handPersonBaseInfo(sysOmsTempPerson,sysOrgPerson,synConfig);
			//扩展字段
			handPersonExtra(sysOmsTempPerson,sysOrgPerson);
			starttime = System.currentTimeMillis();
			//部门人员关系/岗位人员关系
			handPersonDeptAndPost(sysOmsTempPerson,sysOrgPerson,fdSynModel,synConfig,sysOmsTempData);
			if(logger.isDebugEnabled()) {
				logger.debug("处理部门人员关系和岗位人员关系耗时："+(System.currentTimeMillis()-starttime)+"ms");
			}
			//校验总数
			starttime = System.currentTimeMillis();
			//SysOmsTempUtil.checkCount();
			if(logger.isDebugEnabled()) {
				logger.debug("检测人员数量耗时："+(System.currentTimeMillis()-starttime)+"ms");
			}
			starttime = System.currentTimeMillis();
			sysOrgPersonService.getBaseDao().add(sysOrgPerson);
			if(logger.isDebugEnabled()) {
				logger.debug("插入人员信息耗时："+(System.currentTimeMillis()-starttime)+"ms");
			}
			if(!loginNameMap.containsKey(sysOmsTempPerson.getFdLoginName())
					&& sysOmsTempPerson.getFdIsAvailable()) {
				loginNameMap.put(sysOmsTempPerson.getFdLoginName(), sysOrgPerson.getFdId());
			}
			sysOrgElementIdMap.put(ORG_TYPE_PERSON+sysOmsTempPerson.getFdPersonId(),sysOrgPerson.getFdId());
			logStr.append("，成功");
			starttime = System.currentTimeMillis();
			log.info(logStr.toString());
			if(logger.isDebugEnabled()) {
				logger.debug("打印人员新增日志耗时："+(System.currentTimeMillis()-starttime)+"ms");
			}
		} catch (Exception e) {
			logStr.append("，失败："+e.getMessage());
			log.error(logStr.toString(),e);
			result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_SYN_FAIL).add(sysOmsTempPerson);
		}
		
	}
	
	/**
	 * 处理人员基本信息
	 * @param sysOmsTempPerson
	 * @param sysOrgPerson
	 * @param tempTrx
	 */
	protected void handPersonBaseInfo(SysOmsTempPerson sysOmsTempPerson,SysOrgPerson sysOrgPerson,SysOmsSynConfig synConfig) {
		sysOrgPerson.setFdIsAvailable(sysOmsTempPerson.getFdIsAvailable());
		sysOrgPerson.setFdName(sysOmsTempPerson.getFdName());
		sysOrgPerson.setFdCreateTime(new Date());
		sysOrgPerson.setFdImportInfo(sysOmsTempPerson.getFdPersonId());
		sysOrgPerson.setFdSex(SysOmsTempUtil.convertSex(sysOmsTempPerson.getFdSex()));
		sysOrgPerson.setFdLoginName(sysOmsTempPerson.getFdLoginName());
		sysOrgPerson.setFdOrder(sysOmsTempPerson.getFdOrder() == null?null:sysOmsTempPerson.getFdOrder().intValue());	
		sysOrgPerson.setFdMobileNo(sysOmsTempPerson.getFdMobileNo());
		sysOrgPerson.setFdEmail(sysOmsTempPerson.getFdEmail());
		sysOrgPerson.setFdNo(sysOmsTempPerson.getFdNo());
		sysOrgPerson.setFdWorkPhone(sysOmsTempPerson.getFdWorkPhone());
		sysOrgPerson.setFdHiredate(SysOmsTempUtil.convertDate(sysOmsTempPerson.getFdHireDate()));
		sysOrgPerson.setFdMemo(sysOmsTempPerson.getFdDesc());
	}
	
	/**
	 * 处理人员主部门
	 * @param sysOmsTempPerson
	 * @param sysOrgPerson
	 * @param logStr
	 * @throws Exception
	 */
	protected void handPersonMainDept(SysOmsTempPerson sysOmsTempPerson,SysOrgPerson sysOrgPerson,StringBuffer logStr,SysOmsTempData sysOmsTempData) throws Exception {
		if(StringUtil.isNotNull(sysOmsTempPerson.getFdParentid())){
			SysOrgDept parent = findOrgDeptByImportInfo(sysOmsTempPerson.getFdParentid(),sysOmsTempData);
			if(parent == null){
				throw new Exception("找不到所属部门");
			}
			logStr.append("，所属部门名称：");
			sysOrgPerson.setFdParent(parent);
			logStr.append(parent.getFdName());
		}else{
			sysOrgPerson.setFdParent(null);
		}
	}
	
	/**
	 * 处理人员扩展字段
	 * @param sysOmsTempPerson
	 * @param sysOrgPerson
	 */
	protected void handPersonExtra(SysOmsTempPerson sysOmsTempPerson,SysOrgPerson sysOrgPerson) {
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
	}
	
	/**
	 * 处理部门人员关系和岗位人员关系
	 * 其中将部门人员关系转化为岗位人员关系（在该部门下找到一个“成员”岗位，并且将该人与该岗位关联起来）
	 * @param sysOmsTempPerson
	 * @param sysOrgPerson
	 * @param fdSynModel
	 * @param sysOrgElementIdMap
	 * @throws Exception
	 */
	protected void handPersonDeptAndPost(SysOmsTempPerson sysOmsTempPerson,SysOrgPerson sysOrgPerson,int fdSynModel,SysOmsSynConfig synConfig,SysOmsTempData sysOmsTempData) throws Exception {

		//人员所属岗位
		List<SysOrgPost> pPostList = sysOrgPerson.getFdPosts();
		//人员除成员岗位之外的岗位
		List<SysOrgPost> ppList = new ArrayList<SysOrgPost>();
		//人员成员岗位
		List<SysOrgPost> personPostList = new ArrayList<SysOrgPost>();
		for (SysOrgPost sysOrgPost : pPostList) {
			if(SysOmsTempConstants.SYS_ORG_POST_NAME.equals(sysOrgPost.getFdName())) {
				personPostList.add(sysOrgPost);
			}else {
				ppList.add(sysOrgPost);
			}
		}
		if(fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_200.getValue()
				|| fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_400.getValue()){
			if(synConfig.getFdPersonDeptIsFull()){  
				//清除原来的部门
				
				List<SysOrgDeptPersonRelation> deptPersonRList = sysOrgDeptPersonRelationService
						.findRelationList(sysOrgPerson.getFdId());
				if (deptPersonRList != null) {
					for (SysOrgDeptPersonRelation sysOrgDeptPersonRelation : deptPersonRList) {
						sysOrgDeptPersonRelationService.delete(sysOrgDeptPersonRelation);
					}
				}
				//用这个方法批量删除，多线程下 ，sqlserver会产生死锁，具体原因还未查明
				//sysOrgDeptPersonRelationService.delRelation(sysOrgPerson.getFdId());
				//清除原来的成员岗位
				personPostList.clear();
				
				//人员所在部门排序号
				SysOrgDeptPersonRelation deptPersonRelation = null;
			  	List<SysOmsTempDp> dpList = sysOmsTempPerson.getDeptIdList();
				for (SysOmsTempDp sysOmsTempDp : dpList) {
					
					//创建岗位人员关系（人员部门关系所属部门和用户主部门一致，则不用将该用户加入此部门所属“成员岗位”）
					if(sysOmsTempDp.getFdDeptId().equals(sysOmsTempPerson.getFdParentid())) {
						continue;
					}
					
					SysOrgDept sysOrgDept = findOrgDeptByImportInfo(sysOmsTempDp.getFdDeptId(), sysOmsTempData);
					if(sysOrgDept == null) {
						continue;
					}
					
					if(sysOmsTempDp.getFdIsAvailable() != null && !sysOmsTempDp.getFdIsAvailable()){
						continue;
					}
					
					//“成员”岗位
					if(fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_200.getValue()) {
						SysOrgPost sysOrgPost = getSysOrgPostByDeptIdAndPostName(sysOmsTempDp.getFdDeptId(),SysOmsTempConstants.SYS_ORG_POST_NAME);
						if(!personPostList.contains(sysOrgPost)) {
							personPostList.add(sysOrgPost);
						}
					}

					if(sysOmsTempDp.getFdOrder() != null) {
						deptPersonRelation = new SysOrgDeptPersonRelation();
						deptPersonRelation.setFdPersonId(sysOrgPerson.getFdId());
						deptPersonRelation.setFdDeptId(sysOrgDept.getFdId());
						deptPersonRelation.setFdOrder(sysOmsTempDp.getFdOrder().intValue());
						sysOrgDeptPersonRelationService.add(deptPersonRelation);
					}
				}
			}else{
				List<SysOmsTempDp> dpList = sysOmsTempPerson.getDeptIdList();
				for (SysOmsTempDp sysOmsTempDp : dpList) {
					
					//创建岗位人员关系（人员部门关系所属部门和用户主部门一致，则不用将该用户加入此部门所属“成员岗位”）
					if(sysOmsTempDp.getFdDeptId().equals(sysOmsTempPerson.getFdParentid())) {
						continue;
					}
					
					SysOrgDept sysOrgDept = findOrgDeptByImportInfo(sysOmsTempDp.getFdDeptId(), sysOmsTempData);
					if(sysOrgDept == null) {
						logger.warn("部门人员关系找不到部门，抛弃该关系，fdDeptId:"+sysOmsTempDp.getFdDeptId()+"，fdPersonId:"+sysOmsTempDp.getFdPersonId());
						continue;
					}
					//先处理“成员”岗位
					if(fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_200.getValue()) {
						SysOrgPost sysOrgPost = getSysOrgPostByDeptIdAndPostName(sysOmsTempDp.getFdDeptId(),SysOmsTempConstants.SYS_ORG_POST_NAME);
						if(personPostList.contains(sysOrgPost)) {
							if(!sysOmsTempDp.getFdIsAvailable()) {
								personPostList.remove(sysOrgPost);
							}
						}else if(sysOmsTempDp.getFdIsAvailable()){
							personPostList.add(sysOrgPost);
						}
					}
					
					//再处理部门人员关系
					SysOrgDeptPersonRelation deptPersonRelation = sysOrgDeptPersonRelationService.findRelation(sysOrgDept.getFdId(), sysOrgPerson.getFdId());
					if(deptPersonRelation == null) {
						if(sysOmsTempDp.getFdIsAvailable() && sysOmsTempDp.getFdOrder() != null) {
							deptPersonRelation = new SysOrgDeptPersonRelation();
							deptPersonRelation.setFdPersonId(sysOrgPerson.getFdId());
							deptPersonRelation.setFdDeptId(sysOrgDept.getFdId());
							deptPersonRelation.setFdOrder(sysOmsTempDp.getFdOrder().intValue());
							sysOrgDeptPersonRelationService.add(deptPersonRelation);
						}
					}else if(!sysOmsTempDp.getFdIsAvailable()) {
							sysOrgDeptPersonRelationService.delete(deptPersonRelation);
					}else if(sysOmsTempDp.getFdOrder() != null) {
						deptPersonRelation.setFdOrder(sysOmsTempDp.getFdOrder().intValue());
						sysOrgDeptPersonRelationService.update(deptPersonRelation);
					}
				}
			}
		}
		

		//人员所属岗位
		if(fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_300.getValue()
				|| fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_400.getValue()){
			List<SysOmsTempPp> postIdList = sysOmsTempPerson.getPostIdList();
			if(synConfig.getFdPersonPostIsFull()) {
				ppList.clear();
				for (SysOmsTempPp pp : postIdList) {
					if(pp.getFdIsAvailable() != null && !pp.getFdIsAvailable()){
						continue;
					}
					
					SysOrgPost sysOrgPost = findOrgPostByImportInfo(pp.getFdPostId(),sysOmsTempData);
					if(sysOrgPost == null) {
						logger.warn("岗位人员关系找不到岗位，抛弃该关系，fdPostId:"+pp.getFdPostId()+"，fdPersonId:"+pp.getFdPersonId());
						continue;
					}
					
					if(!ppList.contains(sysOrgPost)) {
						ppList.add(sysOrgPost);
					}	
				}
			}else {
				for (SysOmsTempPp pp : postIdList) {
					if(pp.getFdIsAvailable() == null){
						continue;
					}
					
					SysOrgPost sysOrgPost = findOrgPostByImportInfo(pp.getFdPostId(),sysOmsTempData);
					if(sysOrgPost == null) {
						logger.warn("岗位人员关系找不到岗位，抛弃该关系，fdPostId:"+pp.getFdPostId()+"，fdPersonId:"+pp.getFdPersonId());
						continue;
					}
					
					if(!ppList.contains(sysOrgPost)) {
						if(pp.getFdIsAvailable()) {
                            ppList.add(sysOrgPost);
                        }
					}else if(!pp.getFdIsAvailable()) {
						ppList.remove(sysOrgPost);
					}
				}
			}
			
		}
		ppList.addAll(personPostList);
		sysOrgPerson.setFdPosts(ppList);
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


	
	private void modifyPerson(List<SysOmsTempPerson> updatePersonList,SysOmsTempData sysOmsTempData) {
		omsTempSyncThreadExecutor.listThreadExecute("updatePerson", updatePersonList, 2, this,sysOmsTempData);
	}
	private void updatePerson(SysOmsTempPerson sysOmsTempPerson,SysOmsTempData sysOmsTempData) {
		SysOmsTempTrx tempTrx = sysOmsTempData.getTempTrx();
		Map<String, String> loginNameMap = sysOmsTempData.getLoginNameMap();
		OmsTempSynResult<Object> result = sysOmsTempData.getResult();
		SyncLog log = sysOmsTempData.getLog();
		int fdSynModel = sysOmsTempData.getTempTrx().getFdSynModel();
		StringBuffer logStr = new StringBuffer();
		logStr.append("修改人员：");
		logStr.append(sysOmsTempPerson.getFdName());
		logStr.append("，人员ID：" + sysOmsTempPerson.getFdPersonId());
		logStr.append("，登录名：" + sysOmsTempPerson.getFdLoginName());
		logStr.append("，所属部门ID：" +sysOmsTempPerson.getFdParentid());
		logStr.append("，主部门排序号：" +sysOmsTempPerson.getFdOrder());
		try {
			SysOrgPerson sysOrgPerson = findOrgPersonByImportInfo(sysOmsTempPerson.getFdPersonId(),sysOmsTempData);
			SysOmsSynConfig synConfig = SysOmsTempUtil.getSynConfig(tempTrx.getFdSynConfigJson());
			if(sysOrgPerson == null){
				logStr.append("，失败：组织架构中找不到该人员");
				log.warn(logStr.toString());
				return;
			}
			
			//转换排序号
			sysOmsTempPerson.setFdOrder(this.convertOrder(sysOmsTempPerson.getFdOrder(), synConfig.getFdPersonIsAsc()));
			
			//如果配置为数据源不存在更新时间戳，则通过属性对比判断是否更新
			if(synConfig.getFdFullSynFlag()==1 && !isChangePerson(sysOrgPerson,sysOmsTempPerson,synConfig,fdSynModel)) {
				if(logger.isDebugEnabled()) {
					logStr.append("，该人员没有变化，不更新");
					log.info(logStr.toString());
				}
				return;
			}
			
			//判断登录名重复
			SysOrgPerson orgPerson = findOrgPersonByLoginName(sysOmsTempPerson.getFdLoginName(),sysOmsTempData);
			if(orgPerson != null && !sysOmsTempPerson.getFdPersonId().equals(orgPerson.getFdImportInfo())) {
				logStr.append("，该登录名已经存在，直接置为无效");
				sysOmsTempPerson.setFdIsAvailable(false);
			}
			
			//人员所属主部门
			handPersonMainDept(sysOmsTempPerson,sysOrgPerson,logStr,sysOmsTempData);

			//人员基本信息
			handPersonBaseInfo(sysOmsTempPerson,sysOrgPerson,synConfig);		
			
			//扩展字段
			handPersonExtra(sysOmsTempPerson,sysOrgPerson);

			//部门人员关系/岗位人员关系
			handPersonDeptAndPost(sysOmsTempPerson,sysOrgPerson,fdSynModel,synConfig,sysOmsTempData);
			
			sysOrgPersonService.update(sysOrgPerson);
			logStr.append("，成功");
			log.info(logStr.toString());
			if(!loginNameMap.containsKey(sysOmsTempPerson.getFdLoginName())
					&& sysOmsTempPerson.getFdIsAvailable()) {
				loginNameMap.put(sysOmsTempPerson.getFdLoginName(), sysOrgPerson.getFdId());
			}
		
		} catch (Exception e) {
			logStr.append("，失败："+e.getMessage());
			log.error(logStr.toString(),e);
			result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_SYN_FAIL).add(sysOmsTempPerson);
		}

	}
	
	private void delPerson(List<SysOmsTempPerson> delPersonList,SysOmsTempData sysOmsTempData) {
		omsTempSyncThreadExecutor.listThreadExecute("deletePerson", delPersonList, 5, this,sysOmsTempData);
	}
	
	private void deletePerson(SysOmsTempPerson sysOmsTempPerson,SysOmsTempData sysOmsTempData) {
		OmsTempSynResult<Object> result = sysOmsTempData.getResult();
		SyncLog log = sysOmsTempData.getLog();
		StringBuffer logStr = new StringBuffer();
		try {
			SysOrgPerson sysOrgPerson = findOrgPersonByImportInfo(sysOmsTempPerson.getFdPersonId(),sysOmsTempData);
			logStr.append("删除人员：");
			logStr.append(sysOrgPerson.getFdName());
			logStr.append("，人员ID：" + sysOmsTempPerson.getFdPersonId());
			
			//如果配置为数据源不存在更新时间戳，则通过属性对比判断是否更新
			SysOmsSynConfig synConfig = SysOmsTempUtil.getSynConfig(sysOmsTempData.getTempTrx().getFdSynConfigJson());
			if(synConfig.getFdFullSynFlag()==1 && !sysOrgPerson.getFdIsAvailable()) {
				if(logger.isDebugEnabled()) {
	  				logStr.append("，该人员没有变化，不删除");
					log.warn(logStr.toString());
				}
				return;
			}
			
			sysOrgPerson.setFdIsAvailable(sysOmsTempPerson.getFdIsAvailable());
			sysOrgPerson.setFdAlterTime(new Date());
			sysOrgPersonService.update(sysOrgPerson);
			logStr.append("，成功");
			log.info(logStr.toString());
		} catch (Exception e) {
			logStr.append("，失败："+e.getMessage());
			log.error(logStr.toString(),e);
			result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_SYN_FAIL).add(sysOmsTempPerson);
		}
		
	}

	private void delPost(List<SysOmsTempPost> delPostList,SysOmsTempData sysOmsTempData) {
		omsTempSyncThreadExecutor.listThreadExecute("deletePost", delPostList, 5, this,sysOmsTempData);
	}
	
	private void deletePost(SysOmsTempPost sysOmsTempPost,SysOmsTempData sysOmsTempData) {
		OmsTempSynResult<Object> result = sysOmsTempData.getResult();
		SyncLog log = sysOmsTempData.getLog();
		StringBuffer logStr = new StringBuffer();
		
		try {
			SysOrgPost sysOrgPost = findOrgPostByImportInfo(sysOmsTempPost.getFdPostId(),sysOmsTempData);
			logStr.append("删除岗位：");
			logStr.append(sysOrgPost.getFdName());
			logStr.append("，岗位ID：" + sysOmsTempPost.getFdPostId());
			//如果配置为数据源不存在更新时间戳，则通过属性对比判断是否更新
			SysOmsSynConfig synConfig = SysOmsTempUtil.getSynConfig(sysOmsTempData.getTempTrx().getFdSynConfigJson());
			if(synConfig.getFdFullSynFlag()==1 && !sysOrgPost.getFdIsAvailable()) {
				if(logger.isDebugEnabled()) {
					logStr.append("，该岗位没有变化，不删除");
					log.warn(logStr.toString());
				}
				return;
			}
			
			sysOrgPost.setFdIsAvailable(sysOmsTempPost.getFdIsAvailable());
			sysOrgPost.setFdAlterTime(new Date());
			sysOrgPostService.update(sysOrgPost);
			logStr.append("，成功");
			log.info(logStr.toString());
		} catch (Exception e) {
			logStr.append("，失败："+e.getMessage());
			log.error(logStr.toString(),e);
			result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_SYN_FAIL).add(sysOmsTempPost);
		}
		
	}
	
	private void delDept(List<SysOmsTempDept> delDeptList,SysOmsTempData sysOmsTempData) {
		omsTempSyncThreadExecutor.listThreadExecute("deleteDept", delDeptList, 3, this,sysOmsTempData);
	}

	private void deleteDept(SysOmsTempDept sysOmsTempDept,SysOmsTempData sysOmsTempData) {
		OmsTempSynResult<Object> result = sysOmsTempData.getResult();
		SyncLog log = sysOmsTempData.getLog();
		StringBuffer logStr = new StringBuffer();
		try {
			
			SysOrgDept sysOrgDept = findOrgDeptByImportInfo(sysOmsTempDept.getFdDeptId(),sysOmsTempData);
			
			logStr.append("删除部门：");
			logStr.append(sysOrgDept.getFdName());
			logStr.append("，部门ID：" + sysOmsTempDept.getFdDeptId());
			
			//如果配置为数据源不存在更新时间戳，则通过属性对比判断是否更新
			SysOmsSynConfig synConfig = SysOmsTempUtil.getSynConfig(sysOmsTempData.getTempTrx().getFdSynConfigJson());
			if(synConfig.getFdFullSynFlag()==1 && !sysOrgDept.getFdIsAvailable()) {
				if(logger.isDebugEnabled()) {
					logStr.append("，该部门没有变化，不删除");
					log.warn(logStr.toString());
				}
				return;
			}

			sysOrgDept.setFdIsAvailable(false);
			sysOrgDept.setFdAlterTime(new Date());
			sysOrgDeptService.update(sysOrgDept);
			logStr.append("，成功");
			log.info(logStr.toString());
		} catch (Exception e) {
			logStr.append("，失败："+e.getMessage());
			log.error(logStr.toString(),e);
			result.getIllegalData().get(OmsTempSynFailType.DATA_ERR_TYPE_SYN_FAIL).add(sysOmsTempDept);
		}
	}
	
	/**
	 * 部门是否变更：只要有一个属性变更了，则表示变更
	 * @param sysOrgDept
	 * @param tempDept
	 * @return
	 */
	private boolean isChangeDept(SysOrgDept sysOrgDept,SysOmsTempDept tempDept,String fdEkpRootId) {
		if(StringUtil.isNull(tempDept.getFdName())) {
			if(StringUtil.isNotNull(sysOrgDept.getFdName())) {
				if(logger.isDebugEnabled()) {
					logger.debug("部门名称变更，部门ID："+tempDept.getFdDeptId()+"，新部门名称："+tempDept.getFdName()+"，旧父部门ID："+sysOrgDept.getFdName());
				}
				return true;
			}
				
		}else {
			if(!tempDept.getFdName().equals(sysOrgDept.getFdName())) {
				if(logger.isDebugEnabled()) {
					logger.debug("部门名称变更，部门ID："+tempDept.getFdDeptId()+"，新部门名称："+tempDept.getFdName()+"，旧父部门ID："+sysOrgDept.getFdName());
				}
				return true;
			}	
		}
		
		if(StringUtil.isNull(tempDept.getFdParentid())) {
			if(StringUtil.isNotNull(fdEkpRootId)) {
				if(sysOrgDept.getFdParent() == null || !fdEkpRootId.equals(sysOrgDept.getFdParent().getFdId())) {
					if(logger.isDebugEnabled()) {
						logger.debug("部门父部门变更，部门ID："+tempDept.getFdDeptId()+"，新父部门ID："+tempDept.getFdParentid()+"，旧父部门ID："+sysOrgDept.getFdParent().getFdImportInfo());
					}
					return true;
				}
			}else {
				if(sysOrgDept.getFdParent() != null) {
					if(logger.isDebugEnabled()) {
						logger.debug("部门父部门变更，部门ID："+tempDept.getFdDeptId()+"，新父部门ID："+tempDept.getFdParentid()+"，旧父部门ID："+sysOrgDept.getFdParent().getFdImportInfo());
					}
					return true;
				}
			}
		}else {
			if(sysOrgDept.getFdParent() == null) {
				if(logger.isDebugEnabled()) {
					logger.debug("部门父部门变更，部门ID："+tempDept.getFdDeptId()+"，新父部门ID："+tempDept.getFdParentid()+"，旧父部门ID：null");
				}
				return true;
			}
			if(!tempDept.getFdParentid().equals(sysOrgDept.getFdParent().getFdImportInfo())) {
				if(logger.isDebugEnabled()) {
					logger.debug("部门父部门变更，部门ID："+tempDept.getFdDeptId()+"，新父部门ID："+tempDept.getFdParentid()+"，旧父部门ID："+sysOrgDept.getFdParent().getFdImportInfo());
				}
				return true;
			}	
		}
		
		if(tempDept.getFdOrder() == null) {
			if(sysOrgDept.getFdOrder() != null) {
				if(logger.isDebugEnabled()) {
					logger.debug("部门排序号变更，部门ID："+tempDept.getFdDeptId()+"，新部门排序号："+tempDept.getFdOrder()+"，旧部门排序号："+sysOrgDept.getFdOrder());
				}
				return true;
			}
				
		}else {
			if(!tempDept.getFdOrder().equals(sysOrgDept.getFdOrder())) {
				if(logger.isDebugEnabled()) {
					logger.debug("部门排序号变更，部门ID："+tempDept.getFdDeptId()+"，新部门排序号："+tempDept.getFdOrder()+"，旧部门排序号："+sysOrgDept.getFdOrder());
				}
				return true;
			}	
		}

		if(!tempDept.getFdIsAvailable().equals(sysOrgDept.getFdIsAvailable())) {
			if(logger.isDebugEnabled()) {
				logger.debug("部门有效状态变更，部门ID："+tempDept.getFdDeptId()+"，新部门有效状态："+tempDept.getFdIsAvailable()+"，旧部门排序号："+sysOrgDept.getFdIsAvailable());
			}
			return true;
		}
		
		return false;
	}
	
	/**
	 * 岗位是否变更
	 * @param sysOrgDept
	 * @param tempDept
	 * @return
	 */
	private boolean isChangePost(SysOrgPost sysOrgPost,SysOmsTempPost tempPost) {
		if(StringUtil.isNull(tempPost.getFdName())) {
			if(StringUtil.isNotNull(sysOrgPost.getFdName())) {
				if(logger.isDebugEnabled()) {
					logger.debug("岗位名称变更，岗位ID："+tempPost.getFdPostId()+"，新岗位名称："+tempPost.getFdName()+"，旧岗位名称："+sysOrgPost.getFdName());
				}
				return true;
			}
				
		}else {
			if(!tempPost.getFdName().equals(sysOrgPost.getFdName())) {
				if(logger.isDebugEnabled()) {
					logger.debug("岗位名称变更，岗位ID："+tempPost.getFdPostId()+"，新岗位名称："+tempPost.getFdName()+"，旧岗位名称："+sysOrgPost.getFdName());
				}
				return true;
			}
		}
		
		if(tempPost.getFdOrder() == null) {
			if(sysOrgPost.getFdOrder() != null) {
				if(logger.isDebugEnabled()) {
					logger.debug("岗位排序号变更，岗位ID："+tempPost.getFdPostId()+"，新岗位排序号："+tempPost.getFdOrder()+"，旧岗位排序号："+sysOrgPost.getFdOrder());
				}
				return true;
			}
				
		}else {
			if(!tempPost.getFdOrder().equals(sysOrgPost.getFdOrder())) {
				if(logger.isDebugEnabled()) {
					logger.debug("岗位排序号变更，岗位ID："+tempPost.getFdPostId()+"，新岗位排序号："+tempPost.getFdOrder()+"，旧岗位排序号："+sysOrgPost.getFdOrder());
				}
				return true;
			}	
		}
		  
		if(!tempPost.getFdIsAvailable().equals(sysOrgPost.getFdIsAvailable())) {
			if(logger.isDebugEnabled()) {
				logger.debug("岗位有效状态变更，岗位ID："+tempPost.getFdPostId()+"，新有效状态："+tempPost.getFdIsAvailable()+"，旧有效状态："+sysOrgPost.getFdIsAvailable());
			}
			return true;
		}
			
		if(StringUtil.isNull(tempPost.getFdParentid())) {
			if(sysOrgPost.getFdParent() != null) {
				if(logger.isDebugEnabled()) {
					logger.debug("岗位父部门变更，岗位ID："+tempPost.getFdPostId()+"，新父部门ID："+tempPost.getFdParentid()+"，旧父部门ID："+sysOrgPost.getFdParent().getFdImportInfo());
				}
				return true;
			}
		}else {
			if(sysOrgPost.getFdParent() == null) {
				if(logger.isDebugEnabled()) {
					logger.debug("岗位父部门变更，岗位ID："+tempPost.getFdPostId()+"，新父部门ID："+tempPost.getFdParentid()+"，旧父部门ID：null");
				}
				return true;
			}
			if(!tempPost.getFdParentid().equals(sysOrgPost.getFdParent().getFdImportInfo())) {
				if(logger.isDebugEnabled()) {
					logger.debug("岗位父部门变更，岗位ID："+tempPost.getFdPostId()+"，新父部门ID："+tempPost.getFdParentid()+"，旧父部门ID："+sysOrgPost.getFdParent().getFdImportInfo());
				}
				return true;
			}	
		}
		
		
		return false;
	}
	
	/**
	 * 人员是否变更
	 * @param sysOrgPerson
	 * @param tempPerson
	 * @return
	 * @throws Exception 
	 */
	private boolean isChangePerson(SysOrgPerson sysOrgPerson,SysOmsTempPerson tempPerson,SysOmsSynConfig synConfig,int fdSynModel) throws Exception {
		
		if(StringUtil.isNull(tempPerson.getFdName())) {
			if(StringUtil.isNotNull(sysOrgPerson.getFdName())) {
				if(logger.isDebugEnabled()) {
					logger.debug("人员名称变更，人员ID："+tempPerson.getFdPersonId()+"，新名称："+tempPerson.getFdName()+"，旧名称："+sysOrgPerson.getFdName());
				}
				return true;
			}
		}else {
			if(!tempPerson.getFdName().equals(sysOrgPerson.getFdName())) { 
				if(logger.isDebugEnabled()) {
					logger.debug("人员名称变更，人员ID："+tempPerson.getFdPersonId()+"，新名称："+tempPerson.getFdName()+"，旧名称："+sysOrgPerson.getFdName());
				}
				return true;
			}
				
		}
		
		if(StringUtil.isNull(tempPerson.getFdMobileNo())) {
			if(StringUtil.isNotNull(sysOrgPerson.getFdMobileNo())) {
				if(logger.isDebugEnabled()) {
					logger.debug("人员名手机号变更，人员ID："+tempPerson.getFdPersonId()+"，新手机号："+tempPerson.getFdMobileNo()+"，旧手机号："+sysOrgPerson.getFdMobileNo());
				}
				return true;
			}	
		}else {
			if(!tempPerson.getFdMobileNo().equals(sysOrgPerson.getFdMobileNo())) {
				if(logger.isDebugEnabled()) {
					logger.debug("人员手机号变更，人员ID："+tempPerson.getFdPersonId()+"，新手机号："+tempPerson.getFdMobileNo()+"，旧手机号："+sysOrgPerson.getFdMobileNo());
				}
				return true;
			}	
		}

		if(!tempPerson.getFdIsAvailable().equals(sysOrgPerson.getFdIsAvailable())) {
			if(logger.isDebugEnabled()) {
				logger.debug("人员有效状态变更，人员ID："+tempPerson.getFdPersonId()+"，新有效状态："+tempPerson.getFdIsAvailable()+"，旧有效状态："+sysOrgPerson.getFdIsAvailable());
			}
			return true;
		}
			
		if(StringUtil.isNull(tempPerson.getFdParentid())) {
			if(sysOrgPerson.getFdParent() != null) {
				if(logger.isDebugEnabled()) {
					logger.debug("人员父部门变更，人员ID："+tempPerson.getFdPersonId()+"，新父部门ID："+tempPerson.getFdParentid()+"，旧父部门ID："+sysOrgPerson.getFdParent().getFdImportInfo());
				}
				return true;
			}
		}else {
			if(sysOrgPerson.getFdParent() == null) {
				if(logger.isDebugEnabled()) {
					logger.debug("人员父部门变更，人员ID："+tempPerson.getFdPersonId()+"，新父部门ID："+tempPerson.getFdParentid()+"，旧父部门ID：null");
				}
				return true;
			}
			if(!tempPerson.getFdParentid().equals(sysOrgPerson.getFdParent().getFdImportInfo())) {
				if(logger.isDebugEnabled()) {
					logger.debug("人员父部门变更，人员ID："+tempPerson.getFdPersonId()+"，新父部门ID："+tempPerson.getFdParentid()+"，旧父部门ID："+sysOrgPerson.getFdParent().getFdImportInfo());
				}
				return true;
			}	
		}
		
		if(StringUtil.isNull(tempPerson.getFdEmail())) {
			if(StringUtil.isNotNull(sysOrgPerson.getFdEmail())) {
				if(logger.isDebugEnabled()) {
					logger.debug("人员邮件变更，人员ID："+tempPerson.getFdPersonId()+"，新邮件："+tempPerson.getFdEmail()+"，旧邮件："+sysOrgPerson.getFdEmail());
				}
				return true;
			}	
		}else {
			if(!tempPerson.getFdEmail().equals(sysOrgPerson.getFdEmail())) {
				if(logger.isDebugEnabled()) {
					logger.debug("人员邮件变更，人员ID："+tempPerson.getFdPersonId()+"，新邮件："+tempPerson.getFdEmail()+"，旧邮件："+sysOrgPerson.getFdEmail());
				}
				return true;
			}	
		}
		
		if(StringUtil.isNull(tempPerson.getFdLoginName())) {
			if(StringUtil.isNotNull(sysOrgPerson.getFdLoginName())) {
				if(logger.isDebugEnabled()) {
					logger.debug("人员登录名变更，人员ID："+tempPerson.getFdPersonId()+"，新登录名："+tempPerson.getFdLoginName()+"，旧登录名："+sysOrgPerson.getFdLoginName());
				}
				return true;
			}				
		}else {
			if(!tempPerson.getFdLoginName().equals(sysOrgPerson.getFdLoginName())) {
				if(logger.isDebugEnabled()) {
					logger.debug("人员登录名变更，人员ID："+tempPerson.getFdPersonId()+"，新登录名："+tempPerson.getFdLoginName()+"，旧登录名："+sysOrgPerson.getFdLoginName());
				}
				return true;
			}	
		}
		
		if(tempPerson.getFdOrder() == null) {
			if(sysOrgPerson.getFdOrder() != null) {
				if(logger.isDebugEnabled()) {
					logger.debug("人员排序号变更，人员ID："+tempPerson.getFdPersonId()+"，新排序号："+tempPerson.getFdOrder()+"，旧排序号："+sysOrgPerson.getFdOrder());
				}
				return true;
			}
				
		}else {
			if(!new Integer(tempPerson.getFdOrder().intValue()).equals(sysOrgPerson.getFdOrder())) {
				if(logger.isDebugEnabled()) {
					logger.debug("人员排序号变更，人员ID："+tempPerson.getFdPersonId()+"，新排序号："+tempPerson.getFdOrder()+"，旧排序号："+sysOrgPerson.getFdOrder());
				}
				return true;
			}	
		}
	
		if(StringUtil.isNull(tempPerson.getFdNo())) {
			if(StringUtil.isNotNull(sysOrgPerson.getFdNo())) {
				if(logger.isDebugEnabled()) {
					logger.debug("人员工号变更，人员ID："+tempPerson.getFdPersonId()+"，新工号："+tempPerson.getFdNo()+"，旧工号："+sysOrgPerson.getFdNo());
				}
				return true;
			}
				
		}else {
			if(!tempPerson.getFdNo().equals(sysOrgPerson.getFdNo())) {
				if(logger.isDebugEnabled()) {
					logger.debug("人员工号变更，人员ID："+tempPerson.getFdPersonId()+"，新工号："+tempPerson.getFdNo()+"，旧工号："+sysOrgPerson.getFdNo());
				}
				return true;
			}
				
		}
			
		if(StringUtil.isNull(tempPerson.getFdDesc())) {
			if(StringUtil.isNotNull(sysOrgPerson.getFdMemo())) {
				if(logger.isDebugEnabled()) {
					logger.debug("人员备注变更，人员ID："+tempPerson.getFdPersonId()+"，新备注："+tempPerson.getFdDesc()+"，旧备注："+sysOrgPerson.getFdMemo());
				}
				return true;
			}
				
		}else {
			if(!tempPerson.getFdDesc().equals(sysOrgPerson.getFdMemo())) {
				if(logger.isDebugEnabled()) {
					logger.debug("人员备注变更，人员ID："+tempPerson.getFdPersonId()+"，新备注："+tempPerson.getFdDesc()+"，旧备注："+sysOrgPerson.getFdMemo());
				}
				return true;
			}	
		}

		String fdSex = SysOmsTempUtil.convertSex(tempPerson.getFdSex());
		if(StringUtil.isNull(fdSex)) {
			if(StringUtil.isNotNull(sysOrgPerson.getFdSex())) {
				if(logger.isDebugEnabled()) {
					logger.debug("人员性别变更，人员ID："+tempPerson.getFdPersonId()+"，新性别："+tempPerson.getFdSex()+"，旧性别："+sysOrgPerson.getFdSex());
				}
				return true;
			}
				
		}else {
			if(!fdSex.equals(sysOrgPerson.getFdSex())) {
				if(logger.isDebugEnabled()) {
					logger.debug("人员性别变更，人员ID："+tempPerson.getFdPersonId()+"，新性别："+tempPerson.getFdSex()+"，旧性别："+sysOrgPerson.getFdSex());
				}
				return true;
			}	
		}
		
		if(sysOrgPerson.getFdHiredate() == null) {
			if(tempPerson.getFdHireDate() != null) {
				if(logger.isDebugEnabled()) {
					logger.debug("人员入职时间变更，人员ID："+tempPerson.getFdPersonId()+"，新入职时间："+tempPerson.getFdHireDate()+"，旧性别："+sysOrgPerson.getFdHiredate());
				}
				return true;
			}
				
		}else {
			if(tempPerson.getFdHireDate().longValue() != sysOrgPerson.getFdHiredate().getTime()) {
				if(logger.isDebugEnabled()) {
					logger.debug("人员入职时间变更，人员ID："+tempPerson.getFdPersonId()+"，新入职时间："+tempPerson.getFdHireDate()+"，旧性别："+sysOrgPerson.getFdHiredate());
				}
				return true;
			}	
		}
		
		if(StringUtil.isNull(tempPerson.getFdWorkPhone())) {
			if(StringUtil.isNotNull(sysOrgPerson.getFdWorkPhone())) {
				if(logger.isDebugEnabled()) {
					logger.debug("人员办公电话变更，人员ID："+tempPerson.getFdPersonId()+"，新办公电话："+tempPerson.getFdWorkPhone()+"，旧办公电话："+sysOrgPerson.getFdWorkPhone());
				}
				return true;
			}
				
		}else {
			if(!tempPerson.getFdWorkPhone().equals(sysOrgPerson.getFdWorkPhone())) {
				if(logger.isDebugEnabled()) {
					logger.debug("人员办公电话变更，人员ID："+tempPerson.getFdPersonId()+"，新办公电话："+tempPerson.getFdWorkPhone()+"，旧办公电话："+sysOrgPerson.getFdWorkPhone());
				}
				return true;
			}
		}
		
		//比较扩展字段是否变更
		Map<String,Object> customMap = new HashMap<String, Object>();
		for (String key : sysOrgPerson.getCustomPropMap().keySet()) {
			Object val = sysOrgPerson.getCustomPropMap().get(key);
			if(val != null && !"".equals(val)) {
                customMap.put(key, val);
            }
		}
		if(StringUtil.isNull(tempPerson.getFdExtra())) {
			if(customMap.size() != 0) {
				if(logger.isDebugEnabled()) {
					logger.debug("人员扩展字段变更，人员ID："+tempPerson.getFdPersonId()+"，新扩展字段："+tempPerson.getFdExtra()+"，旧扩展字段："+JSONObject.fromObject(sysOrgPerson.getCustomPropMap()));
				}
				return true;
			}
		}else {
			JSONObject extraJson = JSONObject.fromObject(tempPerson.getFdExtra());
			Map<String,Object> extraMap = new HashMap<String, Object>();
			for(Object okey:extraJson.keySet()){
				String key = (String) okey;
				Object val = extraJson.get(key);
				if(!(val instanceof JSONNull) && val != null && !"".equals(val)) {
                    extraMap.put(key, val);
                }
			}
			if(!extraMap.equals(customMap)) {
				if(logger.isDebugEnabled()) {
					logger.debug("人员扩展字段变更，人员ID："+tempPerson.getFdPersonId()+"，新扩展字段："+tempPerson.getFdExtra()+"，旧扩展字段："+JSONObject.fromObject(sysOrgPerson.getCustomPropMap()));
				}
				return true;
			}
		}

		//岗位人员关系是否变更
		if(fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_300.getValue()
				|| fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_400.getValue()) {
			List<SysOrgPost> sysOrgPostList = sysOrgPerson.getFdPosts();
			List<SysOmsTempPp> omsTempPpList = tempPerson.getPostIdList();
			
			if(!equalPostList(sysOrgPostList,omsTempPpList)) {
				if(logger.isDebugEnabled()) {
					logger.debug("人员岗位关系变更，人员ID："+tempPerson.getFdPersonId());
				}
				return true;
			}
		}
		
		//部门人员关系是否变更
		if(fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_200.getValue()
				|| fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_400.getValue()) {
			List<SysOrgDeptPersonRelation> dPRelationList =  sysOrgDeptPersonRelationService.findRelationList(sysOrgPerson.getFdId());
			List<SysOmsTempDp> tempDpList = tempPerson.getDeptIdList();
			if(!equalDeptList(dPRelationList, tempDpList)) {
				if(logger.isDebugEnabled()) {
					logger.debug("人员部门关系变更，人员ID："+tempPerson.getFdPersonId());
				}
				return true;
			}	
		}
		
		return false;
	}
	
	/**
	 * 比较该人员岗位是否变更
	 * @param sysOrgPostList
	 * @param sysOmsTempPostList
	 * @return
	 */
   public static boolean equalPostList(List<SysOrgPost> sysOrgPostList, List<SysOmsTempPp> omsTempPpList) {
	    //如果最新的岗位人员关系为空，默认认为岗位人员关系没有变更
	    if(omsTempPpList == null) {
            return true;
        }
	    
	    List<SysOmsTempPp> ppList = new ArrayList<SysOmsTempPp>();
	    for (SysOmsTempPp sysOmsTempPp : omsTempPpList) {
			if(sysOmsTempPp.getFdIsAvailable() != null && sysOmsTempPp.getFdIsAvailable()) {
				ppList.add(sysOmsTempPp);
			}
		}
	    
	    //如果最新的岗位人员关系为空，默认认为岗位人员关系没有变更
	    if(ppList.isEmpty()) {
            return true;
        }
	    
	    //如果EKP中之前的岗位人员关系为空，则表示岗位人员关系有变更
	    if(sysOrgPostList == null || sysOrgPostList.size() == 0) {
            return false;
        }
	    
        if (sysOrgPostList.size() != omsTempPpList.size()) {
            return false;
        }
        
        TreeSet<String> postIdTreeSet = new TreeSet<String>();
        TreeSet<String> imortInfoTreeSet = new TreeSet<String>();
        for (SysOmsTempPp sysOmsTempPp : omsTempPpList) {
        	if(StringUtil.isNotNull(sysOmsTempPp.getFdPostId())) {
                postIdTreeSet.add(sysOmsTempPp.getFdPostId());
            }
		}
        
        for (SysOrgPost sysOrgPost : sysOrgPostList) {
			if(StringUtil.isNotNull(sysOrgPost.getFdImportInfo())) {
                imortInfoTreeSet.add(sysOrgPost.getFdImportInfo());
            }
		}

        return postIdTreeSet.equals(imortInfoTreeSet);
    }
   
   /**
	 * 比较该人员部门  是否变更
	 * @param sysOrgPostList
	 * @param sysOmsTempPostList
	 * @return
 * @throws Exception 
	 */
  public boolean equalDeptList(List<SysOrgDeptPersonRelation> dPRelationList,List<SysOmsTempDp> omsTempDpList) throws Exception {

	    //如果最新的岗位人员关系为空，默认认为岗位人员关系没有变更
	    if(omsTempDpList == null) {
            return true;
        }
	    
	    List<SysOmsTempDp> dpList = new ArrayList<SysOmsTempDp>();
	    for (SysOmsTempDp sysOmsTempDp : omsTempDpList) {
			if(sysOmsTempDp.getFdIsAvailable() != null && sysOmsTempDp.getFdIsAvailable()) {
				dpList.add(sysOmsTempDp);
			}
		}
	    
	    //如果最新的岗位人员关系为空，默认认为岗位人员关系没有变更
	    if(dpList.isEmpty()) {
            return true;
        }
	    
	    //如果EKP中之前的岗位人员关系为空，则表示岗位人员关系有变更
	    if(dPRelationList == null || dPRelationList.isEmpty()) {
            return false;
        }

       if (dPRelationList.size() != dpList.size()) {
           return false;
       }
       
       TreeSet<String> tempDeptIdTreeSet = new TreeSet<String>();
       TreeSet<String> deptIdTreeSet = new TreeSet<String>();
       for (SysOmsTempDp sysOmsTempDp : dpList) {
       	if(StringUtil.isNotNull(sysOmsTempDp.getFdDeptId())) {
            if(sysOmsTempDp.getFdOrder() != null) {
                tempDeptIdTreeSet.add(sysOmsTempDp.getFdDeptId() + sysOmsTempDp.getFdOrder());
            }
        }
		}
       
       for (SysOrgDeptPersonRelation dPersonRelation : dPRelationList) {
			String fdDeptId = dPersonRelation.getFdDeptId();
			if(StringUtil.isNotNull(fdDeptId)) {
				SysOrgDept sysOrgDept = (SysOrgDept) sysOrgDeptService.findByPrimaryKey(fdDeptId);
				deptIdTreeSet.add(sysOrgDept.getFdImportInfo()+dPersonRelation.getFdOrder());
			}
			
	   }
       
       return tempDeptIdTreeSet.equals(deptIdTreeSet);
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
			updateDept((SysOmsTempDept) bean,(SysOmsTempData)otherParams[0]);
			break;
		//新增岗位
		case "addPost":
			addPost((SysOmsTempPost)bean,(SysOmsTempData)otherParams[0]);
			break;
		//修改岗位
		case "updatePost":
			updatePost((SysOmsTempPost)bean,(SysOmsTempData)otherParams[0]);
			break;
		//新增人员
		case "addPerson":
			addPerson((SysOmsTempPerson) bean,(SysOmsTempData)otherParams[0]);
			break;
		//修改人员
		case "updatePerson":
			updatePerson((SysOmsTempPerson) bean,(SysOmsTempData)otherParams[0]);
			break;
		//删除人员
		case "deletePerson":
			deletePerson((SysOmsTempPerson) bean,(SysOmsTempData)otherParams[0]);
			break;
		//删除岗位
		case "deletePost":
			deletePost((SysOmsTempPost) bean,(SysOmsTempData)otherParams[0]);
			break;
		case "deleteDept":
			deleteDept((SysOmsTempDept)bean,(SysOmsTempData)otherParams[0]);
			break;
		default:
			logger.warn("警告！未找到该类型的批处理逻辑：" + type);
			break;
		}
		
	}

	
}
