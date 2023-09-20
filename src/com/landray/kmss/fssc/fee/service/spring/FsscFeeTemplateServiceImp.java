package com.landray.kmss.fssc.fee.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.fssc.fee.model.FsscFeeMobileConfig;
import com.landray.kmss.fssc.fee.model.FsscFeeTemplate;
import com.landray.kmss.fssc.fee.service.IFsscFeeTemplateService;
import com.landray.kmss.fssc.fee.util.FsscFeeUtil;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.category.model.SysCategoryProperty;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.simplecategory.model.ISysSimpleCategoryModel;
import com.landray.kmss.sys.simplecategory.service.ISysSimpleCategoryService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;

public class FsscFeeTemplateServiceImp extends ExtendDataServiceImp implements IFsscFeeTemplateService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof FsscFeeTemplate) {
            FsscFeeTemplate fsscFeeTemplate = (FsscFeeTemplate) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        FsscFeeTemplate fsscFeeTemplate = new FsscFeeTemplate();
        fsscFeeTemplate.setDocCreateTime(new Date());
        fsscFeeTemplate.setDocCreator(UserUtil.getUser());
        FsscFeeUtil.initModelFromRequest(fsscFeeTemplate, requestContext);
        return fsscFeeTemplate;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        FsscFeeTemplate fsscFeeTemplate = (FsscFeeTemplate) model;
    }

    @Override
    public List<FsscFeeTemplate> findByDocCategory(SysCategoryMain docCategory) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscFeeTemplate.docCategory.fdId=:fdId");
        hqlInfo.setParameter("fdId", docCategory.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<FsscFeeTemplate> findByDocProperties(SysCategoryProperty docProperties) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscFeeTemplate.docProperties.fdId=:fdId");
        hqlInfo.setParameter("fdId", docProperties.getFdId());
        return this.findList(hqlInfo);
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		FsscFeeTemplate temp = (FsscFeeTemplate) modelObj;
		List<FsscFeeMobileConfig> list = temp.getFdConfig();
		for(int i=0;i<list.size();i++){
			list.get(i).setFdOrder(i);
		}
		return super.add(modelObj);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		FsscFeeTemplate temp = (FsscFeeTemplate) modelObj;
		List<FsscFeeMobileConfig> list = temp.getFdConfig();
		for(int i=0;i<list.size();i++){
			list.get(i).setFdOrder(i);
		}
		super.update(modelObj);
	}


}
