package com.landray.kmss.fssc.fee.service.spring;

import java.util.ArrayList;
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
import com.landray.kmss.eop.basedata.service.spring.EopBasedataBusinessServiceImp;
import com.landray.kmss.fssc.fee.model.FsscFeeExpenseItem;
import com.landray.kmss.fssc.fee.model.FsscFeeTemplate;
import com.landray.kmss.fssc.fee.service.IFsscFeeExpenseItemService;
import com.landray.kmss.fssc.fee.util.FsscFeeUtil;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class FsscFeeExpenseItemServiceImp extends EopBasedataBusinessServiceImp implements IFsscFeeExpenseItemService,IXMLDataBean {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof FsscFeeExpenseItem) {
            FsscFeeExpenseItem fsscFeeExpenseItem = (FsscFeeExpenseItem) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        FsscFeeExpenseItem fsscFeeExpenseItem = new FsscFeeExpenseItem();
        FsscFeeUtil.initModelFromRequest(fsscFeeExpenseItem, requestContext);
        return fsscFeeExpenseItem;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        FsscFeeExpenseItem fsscFeeExpenseItem = (FsscFeeExpenseItem) model;
    }

    @Override
    public List<FsscFeeExpenseItem> findByFdTemplate(FsscFeeTemplate fdTemplate) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscFeeExpenseItem.fdTemplate.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdTemplate.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<FsscFeeExpenseItem> findByFdCompany(EopBasedataCompany fdCompany) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscFeeExpenseItem.fdCompany.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCompany.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<FsscFeeExpenseItem> findByFdItemList(EopBasedataExpenseItem fdItemList) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscFeeExpenseItem.fdItemList.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdItemList.getFdId());
        return this.findList(hqlInfo);
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		List<Map<String,Object>> rtn = new ArrayList<Map<String,Object>>();
		String fdExpenseItemId = requestInfo.getParameter("fdExpenseItemId");
		String docTemplateId = requestInfo.getParameter("docTemplateId");
		String fdCompanyId = requestInfo.getParameter("fdCompanyId");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fsscFeeExpenseItem.fdCompany.fdId=:fdCompanyId and fsscFeeExpenseItem.fdTemplate.fdId=:docTemplateId and fsscFeeExpenseItem.fdItemList.fdId=:fdExpenseItemId");
		hqlInfo.setParameter("fdCompanyId",fdCompanyId);
		hqlInfo.setParameter("docTemplateId",docTemplateId);
		hqlInfo.setParameter("fdExpenseItemId",fdExpenseItemId);
		List<FsscFeeExpenseItem> list = findList(hqlInfo);
		if(ArrayUtil.isEmpty(list)){
			Map<String,Object> node = new HashMap<String,Object>();
			node.put("result", "true");
			rtn.add(node);
		}else{
			FsscFeeExpenseItem item = list.get(0);
			Map<String,Object> node = new HashMap<String,Object>();
			node.put("result", item.getFdIsNeedBudget()==null?"false":item.getFdIsNeedBudget().toString());
			rtn.add(node);
		}
		return rtn;
	}
}
