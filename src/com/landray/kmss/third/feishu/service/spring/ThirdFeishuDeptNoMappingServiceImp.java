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
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.third.feishu.model.ThirdFeishuDeptMapping;
import com.landray.kmss.third.feishu.model.ThirdFeishuDeptNoMapping;
import com.landray.kmss.third.feishu.service.IThirdFeishuDeptMappingService;
import com.landray.kmss.third.feishu.service.IThirdFeishuDeptNoMappingService;
import com.landray.kmss.third.feishu.service.IThirdFeishuService;
import com.landray.kmss.third.feishu.util.ThirdFeishuUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ThirdFeishuDeptNoMappingServiceImp extends ExtendDataServiceImp implements IThirdFeishuDeptNoMappingService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdFeishuDeptNoMappingServiceImp.class);

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdFeishuDeptNoMapping) {
            ThirdFeishuDeptNoMapping thirdFeishuDeptNoMapping = (ThirdFeishuDeptNoMapping) model;
            thirdFeishuDeptNoMapping.setDocAlterTime(new Date());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdFeishuDeptNoMapping thirdFeishuDeptNoMapping = new ThirdFeishuDeptNoMapping();
        thirdFeishuDeptNoMapping.setDocAlterTime(new Date());
        ThirdFeishuUtil.initModelFromRequest(thirdFeishuDeptNoMapping, requestContext);
        return thirdFeishuDeptNoMapping;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdFeishuDeptNoMapping thirdFeishuDeptNoMapping = (ThirdFeishuDeptNoMapping) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
    public void addNoMapping(String feishuId, String feishuName,
                             String feishuPath) throws Exception {
		ThirdFeishuDeptNoMapping thirdFeishuDeptNoMapping = new ThirdFeishuDeptNoMapping();
		thirdFeishuDeptNoMapping.setDocAlterTime(new Date());
		thirdFeishuDeptNoMapping.setFdFeishuId(feishuId);
		thirdFeishuDeptNoMapping.setFdFeishuName(feishuName);
		thirdFeishuDeptNoMapping.setFdFeishuPath(feishuPath);
		this.add(thirdFeishuDeptNoMapping);
	}

	public ThirdFeishuDeptNoMapping findByFeishuId(String feishuId)
			throws Exception {
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("fdFeishuId = :feishuId");
		info.setParameter("feishuId", feishuId);
		List list = this.findList(info);
		if (list == null || list.isEmpty()) {
			return null;
		} else if (list.size() == 1) {
			return (ThirdFeishuDeptNoMapping) list.get(0);
		} else {
			throw new Exception("映射表数据重复：ekpId" + feishuId);
		}
	}

	private IThirdFeishuService thirdFeishuService;

	@Override
	public void deleteFeishu(String fdId, String feishuId) throws Exception {
		if (StringUtil.isNull(feishuId) || StringUtil.isNull(fdId)) {
			throw new Exception("要删除的部门ID为空");
		}
		try {
			JSONArray users = getThirdFeishuService().getUsers(feishuId, true);
			for (int i = 0; i < users.size(); i++) {
				JSONObject o = users.getJSONObject(i);
				String employee_id = o.getString("open_id");
				logger.warn("更新用户：" + o.toString());
				try {
					// 把部门下的人员更新到根目录
					getThirdFeishuService().updatePersonDepartment(employee_id,
							// "171074bf2d68f52284c4b7a46288f47b"
							"0");
				} catch (Exception e) {
					logger.error(e.getMessage(),e);
				}
			}
			JSONArray depts = getThirdFeishuService().getDepts(feishuId, true);
			for (int i = depts.size() - 1; i >= 0; i--) {
				JSONObject o = depts.getJSONObject(i);
				String id = o.getString("open_department_id");
				if ("0".equals(id)) {
					logger.error("不能删除根部门");
					continue;
				}
				logger.warn("删除子部门：" + o.toString());
				getThirdFeishuService().delDept(id);
				ThirdFeishuDeptNoMapping noMapping = findByFeishuId(id);
				if (noMapping != null) {
					this.delete(noMapping);
				}
			}

			logger.warn("删除部门：" + feishuId);
			getThirdFeishuService().delDept(feishuId);
			ThirdFeishuDeptNoMapping noMapping = findByFeishuId(feishuId);
				if (noMapping != null) {
				this.delete(noMapping);
			}

		} catch (Exception e) {
			logger.debug("飞书处理出错：errorCode=" + e.getMessage());
			throw new KmssRuntimeException(e);
		}
	}

	IThirdFeishuDeptMappingService thirdFeishuDeptMappingService = null;

	public IThirdFeishuDeptMappingService getThirdFeishuDeptMappingService() {
		if (thirdFeishuDeptMappingService == null) {
			thirdFeishuDeptMappingService = (IThirdFeishuDeptMappingService) SpringBeanUtil
					.getBean("thirdFeishuDeptMappingService");
		}
		return thirdFeishuDeptMappingService;
	}

	@Override
	public boolean updateEKP(String fdId, String fdEKPId) throws Exception {
		boolean flag = true;
		ThirdFeishuDeptNoMapping noMapping = (ThirdFeishuDeptNoMapping) findByPrimaryKey(
				fdId);
		ThirdFeishuDeptMapping mapping = getThirdFeishuDeptMappingService()
				.findByEkpId(fdEKPId);
		if (mapping == null) {
			mapping = new ThirdFeishuDeptMapping();
			mapping.setFdFeishuId(noMapping.getFdFeishuId());
			mapping.setFdFeishuName(noMapping.getFdFeishuName());
			mapping.setFdEkp((SysOrgElement) getSysOrgElementService()
					.findByPrimaryKey(fdEKPId,
					null, true));
			getThirdFeishuDeptMappingService().add(mapping);
			// 删除EKP中间异常表的映射数据
			delete(noMapping);
		} else {
			flag = false;
			throw new Exception("已存在映射关系，ekpId:" + fdEKPId + "，飞书ID："
					+ mapping.getFdFeishuId());
		}
		return flag;
	}

	private ISysOrgElementService sysOrgElementService = null;

	public ISysOrgElementService getSysOrgElementService() {
		if (sysOrgElementService == null) {
			sysOrgElementService = (ISysOrgElementService) SpringBeanUtil
					.getBean("sysOrgElementService");
		}
		return sysOrgElementService;
	}

	public IThirdFeishuService getThirdFeishuService() {
		if (thirdFeishuService == null) {
			thirdFeishuService = (IThirdFeishuService) SpringBeanUtil
					.getBean("thirdFeishuService");
		}
		return thirdFeishuService;
	}

}
