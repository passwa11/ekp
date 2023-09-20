package com.landray.kmss.eop.basedata.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.model.EopBasedataAccounts;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.service.IEopBasedataAccountsService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class EopBasedataAccountsServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataAccountsService {

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataAccounts) {
            EopBasedataAccounts eopBasedataAccounts = (EopBasedataAccounts) model;
            eopBasedataAccounts.setDocAlterTime(new Date());
            eopBasedataAccounts.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataAccounts eopBasedataAccounts = new EopBasedataAccounts();
        String parentId = requestContext.getParameter("parentId");
 		if (StringUtil.isNotNull(parentId)) {
 			EopBasedataAccounts parent = (EopBasedataAccounts) this.findByPrimaryKey(parentId, null, true);
 			if (parent != null) {
 				eopBasedataAccounts.setFdParent(parent);
 			}
 		}
 		eopBasedataAccounts.setFdIsAvailable(true);
 		eopBasedataAccounts.setDocCreateTime(new Date());
 		eopBasedataAccounts.setDocAlterTime(new Date());
 		eopBasedataAccounts.setDocCreator(UserUtil.getUser());
 		eopBasedataAccounts.setDocAlteror(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataAccounts, requestContext);
        return eopBasedataAccounts;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataAccounts eopBasedataAccounts = (EopBasedataAccounts) model;
    }

    @Override
    public List<EopBasedataAccounts> findByDocParent(EopBasedataAccounts docParent) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataAccounts.docParent.fdId=:fdId");
        hqlInfo.setParameter("fdId", docParent.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<EopBasedataAccounts> findByFdCompany(EopBasedataCompany fdCompany) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataAccounts.fdCompany.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCompany.getFdId());
        return this.findList(hqlInfo);
    }

	@Override
	public void saveEnable(String ids, String modelName) throws Exception {
		List<EopBasedataAccounts> selectedAccountList = findByPrimaryKeys(ids.split(";"));
		//查找所有父级科目 
		List<String> allIds = new ArrayList<String>();
		for(EopBasedataAccounts account:selectedAccountList){
			String[] hids = account.getFdHierarchyId().split("x");
			for(String hid:hids){
				if(StringUtil.isNotNull(hid)){
					allIds.add(hid);
				}
			}
		}
		selectedAccountList = findByPrimaryKeys(allIds.toArray(new String[allIds.size()]));
		for(EopBasedataAccounts account:selectedAccountList){
			account.setFdIsAvailable(true);
		}
		//更新档案为有效
		getBaseDao().saveOrUpdateAll(selectedAccountList);
	}

	@Override
	public void saveDisable(String ids, String modelName) throws Exception {
		List<EopBasedataAccounts> selectedAccountList = findByPrimaryKeys(ids.split(";"));
		//查找所有子级科目
		StringBuffer where = new StringBuffer();
		where.append("1=2 ");
		for(EopBasedataAccounts account:selectedAccountList){
			where.append("or eopBasedataAccounts.fdHierarchyId like '").append(account.getFdHierarchyId()).append("%' ");
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(where.toString());
		selectedAccountList = findList(hqlInfo);
		for(EopBasedataAccounts account:selectedAccountList){
			account.setFdIsAvailable(false);
		}
		//更新档案为无效
		getBaseDao().saveOrUpdateAll(selectedAccountList);
	}

	@Override
    public EopBasedataAccounts getEopBasedataAccountsByCode(String fdCode) throws Exception{
		if(StringUtil.isNull(fdCode)){
			return null;
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(" UPPER(eopBasedataBudgetItem.fdCode) = :fdCode ");
		hqlInfo.setParameter("fdCode", fdCode.toUpperCase());
		List<EopBasedataAccounts> list = this.findList(hqlInfo);
		return ArrayUtil.isEmpty(list)?null:list.get(0);
	}
}
