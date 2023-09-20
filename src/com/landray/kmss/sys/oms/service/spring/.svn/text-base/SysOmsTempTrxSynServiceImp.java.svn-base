package com.landray.kmss.sys.oms.service.spring;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
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
import com.landray.kmss.sys.oms.service.ISysOmsTempTrxBaseService;
import com.landray.kmss.sys.oms.service.ISysOmsTempTrxNewService;
import com.landray.kmss.sys.oms.service.ISysOmsTempTrxService;
import com.landray.kmss.sys.oms.service.ISysOmsTempTrxSynService;
import com.landray.kmss.sys.oms.temp.OmsTempSynModel;
import com.landray.kmss.sys.oms.temp.OmsTempSynResult;
import com.landray.kmss.sys.oms.temp.SysOmsSynConfig;
import com.landray.kmss.sys.oms.util.SysOmsUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.TransactionUtils;

public class SysOmsTempTrxSynServiceImp implements ISysOmsTempTrxSynService{
	private Logger logger = org.slf4j.LoggerFactory.getLogger(SysOmsTempTrxSynServiceImp.class);
	protected ISysNotifyMainCoreService sysNotifyMainCoreService;
	private ISysOmsTempTrxNewService sysOmsTempTrxNewService;
	private ISysOmsTempTrxService sysOmsTempTrxService;

    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        SysOmsTempTrx sysOmsTempTrx = new SysOmsTempTrx();
        sysOmsTempTrx.setBeginTime(new Date());
        SysOmsUtil.initModelFromRequest(sysOmsTempTrx, requestContext);
        return sysOmsTempTrx;
    }

    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        SysOmsTempTrx sysOmsTempTrx = (SysOmsTempTrx) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
    
    private ISysOmsTempTrxBaseService getServiceBySynModel(OmsTempSynModel fdSynModel) {
    	//未来考虑不同的模式不同的实现类
    	if(fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_100) {
			return sysOmsTempTrxNewService;
		}else if(fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_200) {
			return sysOmsTempTrxNewService;
		}else if(fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_300) {
			return sysOmsTempTrxNewService;
		}else if(fdSynModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_400) {
			return sysOmsTempTrxNewService;
		}
    	return null;
    }
    /**
     * @param fdSynModel 枚举 同步模式
     * @param synConfig 同步配置参数
     */
	@Override
	public OmsTempSynResult<Object> begin(OmsTempSynModel fdSynModel,SysOmsSynConfig synConfig) {
		return getServiceBySynModel(fdSynModel).begin(fdSynModel, synConfig);
	}
	
	@Override
	public OmsTempSynResult<SysOmsTempDept> addTempDept(String fdTrxId, List<SysOmsTempDept> tempDeptList) throws Exception {
		OmsTempSynModel fdSynModel = getSynModelByTrxId(fdTrxId);
		return getServiceBySynModel(fdSynModel).addTempDept(fdTrxId, tempDeptList);
	}

	@Override
	public OmsTempSynResult<SysOmsTempPost> addTempPost(String fdTrxId,List<SysOmsTempPost> tempPostList) throws Exception{
		OmsTempSynModel fdSynModel = getSynModelByTrxId(fdTrxId);
		return getServiceBySynModel(fdSynModel).addTempPost(fdTrxId, tempPostList);
	}

	@Override
	public OmsTempSynResult<SysOmsTempPerson> addTempPerson(String fdTrxId,List<SysOmsTempPerson> tempPersonList) throws Exception {
		return getServiceBySynModel(getSynModelByTrxId(fdTrxId)).addTempPerson(fdTrxId, tempPersonList);
	}

	private OmsTempSynModel getSynModelByTrxId(String fdTrxId) throws Exception {
		TransactionStatus status = null;
		OmsTempSynModel fdSynModel = null;
		try {
			status = TransactionUtils.beginNewTransaction();
			SysOmsTempTrx sysOmsTempTrx = (SysOmsTempTrx) sysOmsTempTrxService.findByPrimaryKey(fdTrxId);
			fdSynModel = OmsTempSynModel.getEnumByValue(sysOmsTempTrx.getFdSynModel());
			TransactionUtils.commit(status);
		} catch (Exception e) {
			logger.error("获取错误,trxId="+fdTrxId,e);
			if (status != null) {
				try {
					TransactionUtils.rollback(status);
				} catch (Exception ex) {
					logger.error("---事务回滚出错：{0}---", ex);
				}
			}
			
		}
		return fdSynModel;
	}

	@Override
	public OmsTempSynResult<SysOmsTempPp> addTempPostPerson(String fdTrxId,List<SysOmsTempPp> tempPpList) throws Exception {
		OmsTempSynModel fdSynModel = getSynModelByTrxId(fdTrxId);
		return getServiceBySynModel(fdSynModel).addTempPostPerson(fdTrxId, tempPpList);
	}

	@Override
	public OmsTempSynResult<SysOmsTempDp> addTempDeptPerson(String fdTrxId,List<SysOmsTempDp> tempDpList) throws Exception{
		OmsTempSynModel fdSynModel = getSynModelByTrxId(fdTrxId);
		return getServiceBySynModel(fdSynModel).addTempDeptPerson(fdTrxId, tempDpList);
	}

	@Override
	public OmsTempSynResult<Object> end(String fdTrxId) throws Exception{
		OmsTempSynModel fdSynModel = getSynModelByTrxId(fdTrxId);
		return getServiceBySynModel(fdSynModel).end(fdTrxId);
	}

	public ISysOmsTempTrxNewService getSysOmsTempTrxNewService() {
		return sysOmsTempTrxNewService;
	}

	public void setSysOmsTempTrxNewService(ISysOmsTempTrxNewService sysOmsTempTrxNewService) {
		this.sysOmsTempTrxNewService = sysOmsTempTrxNewService;
	}

	public ISysOmsTempTrxService getSysOmsTempTrxService() {
		return sysOmsTempTrxService;
	}

	public void setSysOmsTempTrxService(ISysOmsTempTrxService sysOmsTempTrxService) {
		this.sysOmsTempTrxService = sysOmsTempTrxService;
	}

	
	
	
	
}
