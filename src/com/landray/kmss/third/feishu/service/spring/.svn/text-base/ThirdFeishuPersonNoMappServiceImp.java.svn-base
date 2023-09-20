package com.landray.kmss.third.feishu.service.spring;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.third.feishu.model.ThirdFeishuPerson;
import com.landray.kmss.third.feishu.model.ThirdFeishuPersonMapping;
import com.landray.kmss.third.feishu.model.ThirdFeishuPersonNoMapp;
import com.landray.kmss.third.feishu.service.IThirdFeishuPersonMappingService;
import com.landray.kmss.third.feishu.service.IThirdFeishuPersonNoMappService;
import com.landray.kmss.third.feishu.service.IThirdFeishuService;
import com.landray.kmss.third.feishu.util.ThirdFeishuUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class ThirdFeishuPersonNoMappServiceImp extends ExtendDataServiceImp implements IThirdFeishuPersonNoMappService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdFeishuPersonNoMappServiceImp.class);

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdFeishuPersonNoMapp) {
            ThirdFeishuPersonNoMapp thirdFeishuPersonNoMapp = (ThirdFeishuPersonNoMapp) model;
            thirdFeishuPersonNoMapp.setDocAlterTime(new Date());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdFeishuPersonNoMapp thirdFeishuPersonNoMapp = new ThirdFeishuPersonNoMapp();
        thirdFeishuPersonNoMapp.setDocAlterTime(new Date());
        ThirdFeishuUtil.initModelFromRequest(thirdFeishuPersonNoMapp, requestContext);
        return thirdFeishuPersonNoMapp;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdFeishuPersonNoMapp thirdFeishuPersonNoMapp = (ThirdFeishuPersonNoMapp) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
    public void addNoMapping(ThirdFeishuPerson person) throws Exception {
		ThirdFeishuPersonNoMapp thirdFeishuPersonNoMapp = new ThirdFeishuPersonNoMapp();
		thirdFeishuPersonNoMapp.setDocAlterTime(new Date());
		thirdFeishuPersonNoMapp.setFdEmployeeId(person.getEmployee_id());
		thirdFeishuPersonNoMapp.setFdOpenId(person.getOpen_id());
		thirdFeishuPersonNoMapp.setFdFeishuName(person.getName());
		thirdFeishuPersonNoMapp.setFdEmail(person.getEmail());
		thirdFeishuPersonNoMapp.setFdFeishuMobileNo(person.getMobile());
		thirdFeishuPersonNoMapp.setFdFeishuNo(person.getEmployee_no());
		this.add(thirdFeishuPersonNoMapp);
	}

	public ThirdFeishuPersonNoMapp findByEmployeeId(String employeeId)
			throws Exception {
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("fdEmployeeId = :employeeId");
		info.setParameter("employeeId", employeeId);
		List list = this.findList(info);
		if (list == null || list.isEmpty()) {
			return null;
		} else if (list.size() == 1) {
			return (ThirdFeishuPersonNoMapp) list.get(0);
		} else {
			throw new Exception("未匹配数据重复,employeeId:" + employeeId);
		}
	}

	@Override
	public void deleteFeishu(String fdId, String employeeId) throws Exception {
		if (StringUtil.isNull(employeeId) || StringUtil.isNull(fdId)) {
			throw new Exception("要删除的人员ID为空");
		}
		try {
			logger.warn("删除人员：" + employeeId);
			getThirdFeishuService().delPerson(employeeId);
			ThirdFeishuPersonNoMapp noMapping = findByEmployeeId(employeeId);
			if (noMapping != null) {
				this.delete(noMapping);
			}

		} catch (Exception e) {
			logger.debug("飞书处理出错：errorCode=" + e.getMessage());
			throw new KmssRuntimeException(e);
		}
	}

	IThirdFeishuPersonMappingService thirdFeishuPersonMappingService = null;

	public IThirdFeishuPersonMappingService
			getThirdFeishuPersonMappingService() {
		if (thirdFeishuPersonMappingService == null) {
			thirdFeishuPersonMappingService = (IThirdFeishuPersonMappingService) SpringBeanUtil
					.getBean("thirdFeishuPersonMappingService");
		}
		return thirdFeishuPersonMappingService;
	}

	@Override
	public boolean updateEKP(String fdId, String fdEKPId) throws Exception {
		boolean flag = true;
		ThirdFeishuPersonNoMapp noMapping = (ThirdFeishuPersonNoMapp) findByPrimaryKey(
				fdId);
		ThirdFeishuPersonMapping mapping = getThirdFeishuPersonMappingService()
				.findByEkpId(fdEKPId);
		if (mapping == null) {
			mapping = new ThirdFeishuPersonMapping();
			mapping.setFdEmployeeId(noMapping.getFdEmployeeId());
			mapping.setFdOpenId(noMapping.getFdOpenId());
			mapping.setFdMobileNo(noMapping.getFdFeishuMobileNo());
			mapping.setFdEkp((SysOrgPerson) getSysOrgPersonService()
					.findByPrimaryKey(fdEKPId,
							null, true));
			getThirdFeishuPersonMappingService().add(mapping);
			// 删除EKP中间异常表的映射数据
			delete(noMapping);
		} else {
			flag = false;
			throw new Exception("已存在映射关系，ekpId:" + fdEKPId + "，飞书ID："
					+ mapping.getFdEmployeeId());
		}
		return flag;
	}

	private ISysOrgPersonService sysOrgPersonService = null;

	public ISysOrgPersonService getSysOrgPersonService() {
		if (sysOrgPersonService == null) {
			sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil
					.getBean("sysOrgPersonService");
		}
		return sysOrgPersonService;
	}

	private IThirdFeishuService thirdFeishuService = null;

	public IThirdFeishuService getThirdFeishuService() {
		if (thirdFeishuService == null) {
			thirdFeishuService = (IThirdFeishuService) SpringBeanUtil
					.getBean("thirdFeishuService");
		}
		return thirdFeishuService;
	}
}
