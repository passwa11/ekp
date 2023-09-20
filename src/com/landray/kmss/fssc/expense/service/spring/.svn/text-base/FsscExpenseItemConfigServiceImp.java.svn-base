package com.landray.kmss.fssc.expense.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.service.IEopBasedataDetailImportValidator;
import com.landray.kmss.eop.basedata.service.spring.EopBasedataBusinessServiceImp;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.expense.model.FsscExpenseCategory;
import com.landray.kmss.fssc.expense.model.FsscExpenseItemConfig;
import com.landray.kmss.fssc.expense.service.IFsscExpenseItemConfigService;
import com.landray.kmss.fssc.expense.util.FsscExpenseUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.UserUtil;

public class FsscExpenseItemConfigServiceImp extends EopBasedataBusinessServiceImp implements IFsscExpenseItemConfigService,IXMLDataBean,IEopBasedataDetailImportValidator {

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof FsscExpenseItemConfig) {
            FsscExpenseItemConfig fsscExpenseItemConfig = (FsscExpenseItemConfig) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        FsscExpenseItemConfig fsscExpenseItemConfig = new FsscExpenseItemConfig();
        fsscExpenseItemConfig.setFdIsAvailable(Boolean.valueOf("true"));
        fsscExpenseItemConfig.setDocCreateTime(new Date());
        fsscExpenseItemConfig.setDocCreator(UserUtil.getUser());
        FsscExpenseUtil.initModelFromRequest(fsscExpenseItemConfig, requestContext);
        return fsscExpenseItemConfig;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        FsscExpenseItemConfig fsscExpenseItemConfig = (FsscExpenseItemConfig) model;
    }

    @Override
    public List<FsscExpenseItemConfig> findByFdCompany(EopBasedataCompany fdCompany) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscExpenseItemConfig.fdCompany.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCompany.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<FsscExpenseItemConfig> findByFdCategory(FsscExpenseCategory fdCategory) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscExpenseItemConfig.fdCategory.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCategory.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<FsscExpenseItemConfig> findByFdItemList(EopBasedataExpenseItem fdItemList) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscExpenseItemConfig.fdItemList.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdItemList.getFdId());
        return this.findList(hqlInfo);
    }

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		List<Map<String,Object>> rtn = new ArrayList<Map<String,Object>>();
		String fdExpenseItemId = requestInfo.getParameter("fdExpenseItemId");
		String docTemplateId = requestInfo.getParameter("docTemplateId");
		String fdCompanyId = requestInfo.getParameter("fdCompanyId");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fsscExpenseItemConfig.fdCompany.fdId=:fdCompanyId and fsscExpenseItemConfig.fdCategory.fdId=:docTemplateId and fsscExpenseItemConfig.fdItemList.fdId=:fdExpenseItemId");
		hqlInfo.setParameter("fdCompanyId",fdCompanyId);
		hqlInfo.setParameter("docTemplateId",docTemplateId);
		hqlInfo.setParameter("fdExpenseItemId",fdExpenseItemId);
        String fdIsBudget= EopBasedataFsscUtil.getSwitchValue("fdIsBudget");//获取预算启动开关
        if(!"0".equals(fdIsBudget)) {

            List<FsscExpenseItemConfig> list = findList(hqlInfo);
            if (ArrayUtil.isEmpty(list)) {
                Map<String, Object> node = new HashMap<String, Object>();
                node.put("required", "false");
                rtn.add(node);
            } else {
                FsscExpenseItemConfig item = list.get(0);
                Map<String, Object> node = new HashMap<String, Object>();
                node.put("required", item.getFdIsNeedBudget() == null ? "false" : item.getFdIsNeedBudget().toString());
                rtn.add(node);
            }
        }else{
            Map<String, Object> node = new HashMap<String, Object>();
            node.put("required", "false");
            rtn.add(node);
        }
		return rtn;
	}

	@Override
	public Boolean validate(Map<String, Object> context, Object value) throws Exception {
		HQLInfo hqlInfo =  new HQLInfo();
		hqlInfo.setWhereBlock("fsscExpenseItemConfig.fdCompany.fdId=:fdCompanyId and fsscExpenseItemConfig.fdCategory.fdId=:docTemplateId and fsscExpenseItemConfig.fdItemList.fdCode=:code");
		hqlInfo.setParameter("fdCompanyId",context.get("fdCompanyId"));
		hqlInfo.setParameter("docTemplateId",context.get("docTemplateId"));
		hqlInfo.setParameter("code",value);
		List<FsscExpenseItemConfig> list = findList(hqlInfo);
		return !ArrayUtil.isEmpty(list);
	}
}
