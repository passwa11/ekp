package com.landray.kmss.hr.staff.service.spring;

import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.staff.forms.HrStaffPersonExperienceBaseForm;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceBase;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfoLog;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceBaseService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoLogService;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * 个人经历基类
 * 
 * @author 潘永辉 2016-12-30
 * 
 */
public abstract class HrStaffPersonExperienceBaseServiceImp extends
		HrStaffImportServiceImp implements IHrStaffPersonExperienceBaseService {
	private IHrStaffPersonInfoLogService hrStaffPersonInfoLogService;

	@Override
    public IHrStaffPersonInfoLogService getHrStaffPersonInfoLogService() {
		if (hrStaffPersonInfoLogService == null) {
			hrStaffPersonInfoLogService = (IHrStaffPersonInfoLogService) SpringBeanUtil
					.getBean("hrStaffPersonInfoLogService");
		}
		return hrStaffPersonInfoLogService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HrStaffPersonExperienceBase> getHrStaffPersonExperiences(
			String personInfoId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setRowSize(Integer.MAX_VALUE);
		hqlInfo.setWhereBlock("fdPersonInfo.fdId = :personInfoId");
		hqlInfo.setParameter("personInfoId", personInfoId);

		return super.findPage(hqlInfo).getList();
	}

	@Override
	public IBaseModel convertFormToModel(IExtendForm form, IBaseModel model,
			RequestContext requestContext) throws Exception {

		// 获取数据库旧数据
		HrStaffPersonExperienceBase oldExperienceBase = (HrStaffPersonExperienceBase) findByPrimaryKey(
				form.getFdId(), ((HrStaffPersonExperienceBaseForm) form)
						.getModelClass(), true);
		HrStaffPersonExperienceBaseForm oldExperienceBaseForm = null;
		if (oldExperienceBase != null) {
			oldExperienceBaseForm = (HrStaffPersonExperienceBaseForm) super
					.cloneModelToForm(null, oldExperienceBase, requestContext);
		}

		HrStaffPersonExperienceBase newExperienceBase = (HrStaffPersonExperienceBase) super
				.convertFormToModel(form, model, requestContext);
		// 构建日志信息
		buildExperienceLog(oldExperienceBaseForm,
				(HrStaffPersonExperienceBaseForm) form, requestContext);

		return newExperienceBase;
	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		super.delete(modelObj);
		HrStaffPersonExperienceBase experienceBase = (HrStaffPersonExperienceBase) modelObj;
		// 删除日志
		String fdDetails = "删除“" + experienceBase.getFdPersonInfo().getFdName()
				+ "”的个人经历(" + getTypeString() + ")。";
		HrStaffPersonInfoLog log = getHrStaffPersonInfoLogService()
				.buildPersonInfoLog("delete", fdDetails);
		log.getFdTargets().add(experienceBase.getFdPersonInfo());
		getHrStaffPersonInfoLogService().add(log);
	}

	private void buildExperienceLog(HrStaffPersonExperienceBaseForm oldForm,
			HrStaffPersonExperienceBaseForm newForm,
			RequestContext requestContext) throws Exception {
		String fdParaMethod = requestContext.getParameter("method");
		String fdDetails = null;
		HrStaffPersonInfo personInfo = (HrStaffPersonInfo) findByPrimaryKey(
				newForm.getFdPersonInfoId(), HrStaffPersonInfo.class, true);
		if ("save".equalsIgnoreCase(fdParaMethod) || fdParaMethod.startsWith("save")) {
			fdDetails = "新增员工“" + personInfo.getFdName() + "”的个人经历("
					+ getTypeString() + ")。";
		} else {
			fdDetails = "修改了“" + oldForm.getFdPersonInfoName() + "”的个人经历("
					+ getTypeString() + ")。";
		}
		HrStaffPersonInfoLog log = getHrStaffPersonInfoLogService()
				.buildPersonInfoLog(fdParaMethod, fdDetails);
		log.getFdTargets().add(personInfo);
		getHrStaffPersonInfoLogService().add(log);
	}

}
