package com.landray.kmss.eop.basedata.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetItem;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.service.IEopBasedataBudgetItemService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.hibernate.spi.HibernateWrapper;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class EopBasedataBudgetItemServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataBudgetItemService {

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataBudgetItem) {
            EopBasedataBudgetItem eopBasedataBudgetItem = (EopBasedataBudgetItem) model;
            eopBasedataBudgetItem.setDocAlterTime(new Date());
            eopBasedataBudgetItem.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataBudgetItem eopBasedataBudgetItem = new EopBasedataBudgetItem();
        String parentId = requestContext.getParameter("parentId");
 		if (StringUtil.isNotNull(parentId)) {
 			EopBasedataBudgetItem parent = (EopBasedataBudgetItem) this.findByPrimaryKey(parentId, null, true);
 			if (parent != null) {
 				eopBasedataBudgetItem.setFdParent(parent);
 			}
 		}
 		eopBasedataBudgetItem.setFdIsAvailable(true);
 		eopBasedataBudgetItem.setDocCreateTime(new Date());
 		eopBasedataBudgetItem.setDocAlterTime(new Date());
 		eopBasedataBudgetItem.setDocCreator(UserUtil.getUser());
 		eopBasedataBudgetItem.setDocAlteror(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataBudgetItem, requestContext);
        return eopBasedataBudgetItem;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataBudgetItem eopBasedataBudgetItem = (EopBasedataBudgetItem) model;
    }

    @Override
    public List<EopBasedataBudgetItem> findByDocParent(EopBasedataBudgetItem docParent) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataBudgetItem.docParent.fdId=:fdId");
        hqlInfo.setParameter("fdId", docParent.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<EopBasedataBudgetItem> findByFdCompany(EopBasedataCompany fdCompany) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataBudgetItem.fdCompany.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCompany.getFdId());
        return this.findList(hqlInfo);
    }

	@Override
	public void saveEnable(String ids, String modelName) throws Exception {
		List<EopBasedataBudgetItem> selectedAccountList = findByPrimaryKeys(ids.split(";"));
		//查找所有父级科目 
		List<String> allIds = new ArrayList<String>();
		for(EopBasedataBudgetItem account:selectedAccountList){
			String[] hids = account.getFdHierarchyId().split("x");
			for(String hid:hids){
				if(StringUtil.isNotNull(hid)){
					allIds.add(hid);
				}
			}
		}
		selectedAccountList = findByPrimaryKeys(allIds.toArray(new String[allIds.size()]));
		for(EopBasedataBudgetItem account:selectedAccountList){
			account.setFdIsAvailable(true);
		}
		//更新档案的启用状态
		getBaseDao().saveOrUpdateAll(selectedAccountList);
	}

	@Override
	public void saveDisable(String ids, String modelName) throws Exception {
		List<EopBasedataBudgetItem> selectedAccountList = findByPrimaryKeys(ids.split(";"));
		//查找所有子级科目
		StringBuffer where = new StringBuffer();
		where.append("1=2 ");
		for(EopBasedataBudgetItem account:selectedAccountList){
			where.append("or eopBasedataBudgetItem.fdHierarchyId like '").append(account.getFdHierarchyId()).append("%' ");
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(where.toString());
		selectedAccountList = findList(hqlInfo);
		for(EopBasedataBudgetItem account:selectedAccountList){
			account.setFdIsAvailable(false);
		}
		//更新档案的启用状态
		getBaseDao().saveOrUpdateAll(selectedAccountList);
	}
}
