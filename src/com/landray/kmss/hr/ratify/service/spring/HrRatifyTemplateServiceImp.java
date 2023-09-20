package com.landray.kmss.hr.ratify.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.ratify.model.HrRatifyTemplate;
import com.landray.kmss.hr.ratify.service.IHrRatifyTemplateService;
import com.landray.kmss.hr.ratify.util.HrRatifyUtil;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.category.model.SysCategoryProperty;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class HrRatifyTemplateServiceImp extends ExtendDataServiceImp implements IHrRatifyTemplateService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof HrRatifyTemplate) {
            HrRatifyTemplate hrRatifyTemplate = (HrRatifyTemplate) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        HrRatifyTemplate hrRatifyTemplate = new HrRatifyTemplate();
        hrRatifyTemplate.setDocCreateTime(new Date());
        hrRatifyTemplate.setFdIsAvailable(Boolean.valueOf("true"));
        hrRatifyTemplate.setDocCreator(UserUtil.getUser());
        HrRatifyUtil.initModelFromRequest(hrRatifyTemplate, requestContext);
        return hrRatifyTemplate;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        HrRatifyTemplate hrRatifyTemplate = (HrRatifyTemplate) model;
    }

    @Override
    public List<HrRatifyTemplate> findByDocCategory(SysCategoryMain docCategory) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("hrRatifyTemplate.docCategory.fdId=:fdId");
        hqlInfo.setParameter("fdId", docCategory.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<HrRatifyTemplate> findByDocProperties(SysCategoryProperty docProperties) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("hrRatifyTemplate.docProperties.fdId=:fdId");
        hqlInfo.setParameter("fdId", docProperties.getFdId());
        return this.findList(hqlInfo);
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	/**
	 * 新建文档获取模板时，过滤不可用的模板
	 */
	@Override
	public List findValue(HQLInfo hqlInfo) throws Exception {
		if (StringUtil.isNull(hqlInfo.getModelName())
				|| hqlInfo.getModelName().contains("HrRatifyTemplate")) {
			String whereBlock = hqlInfo.getWhereBlock();
			if (StringUtil.isNotNull(whereBlock)) {
				hqlInfo.setWhereBlock(whereBlock
						+ " and (hrRatifyTemplate.fdIsAvailable = :fdIsAvailable or hrRatifyTemplate.fdIsAvailable is null)");
			} else {
				hqlInfo.setWhereBlock(
						"hrRatifyTemplate.fdIsAvailable = :fdIsAvailable or hrRatifyTemplate.fdIsAvailable is null");
			}
			hqlInfo.setParameter("fdIsAvailable", true);
		}
		return super.findValue(hqlInfo);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		if (UserUtil.getUser() != null) {
			HrRatifyTemplate template = (HrRatifyTemplate) modelObj;
			template.setDocAlteror(UserUtil.getUser());
			template.setDocAlterTime(new Date());
		}
		super.update(modelObj);
	}
}
