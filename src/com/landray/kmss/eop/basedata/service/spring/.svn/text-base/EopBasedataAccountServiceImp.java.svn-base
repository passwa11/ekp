package com.landray.kmss.eop.basedata.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.eop.basedata.imp.context.EopBasedataImportMessage;
import com.landray.kmss.eop.basedata.model.EopBasedataAccount;
import com.landray.kmss.eop.basedata.service.IEopBasedataAccountService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.upload.FormFile;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EopBasedataAccountServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataAccountService,IXMLDataBean {

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataAccount) {
            EopBasedataAccount eopBasedataAccount = (EopBasedataAccount) model;
            eopBasedataAccount.setDocAlterTime(new Date());
            eopBasedataAccount.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataAccount eopBasedataAccount = new EopBasedataAccount();
        eopBasedataAccount.setFdIsAvailable(Boolean.valueOf("true"));
        eopBasedataAccount.setFdIsDefault(Boolean.valueOf("true"));
        eopBasedataAccount.setDocCreateTime(new Date());
        eopBasedataAccount.setDocAlterTime(new Date());
        SysOrgPerson user=UserUtil.getUser();
        eopBasedataAccount.setDocCreator(user);
        eopBasedataAccount.setDocAlteror(user);
        eopBasedataAccount.setFdPerson(user);
        EopBasedataUtil.initModelFromRequest(eopBasedataAccount, requestContext);
        return eopBasedataAccount;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataAccount eopBasedataAccount = (EopBasedataAccount) model;
    }

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		EopBasedataAccount eopBasedataAccount = (EopBasedataAccount) modelObj;
		//当前账户设置为默认账户，则将该人员的其它账户更新为非默认账户
		if(eopBasedataAccount.getFdIsDefault()){
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("eopBasedataAccount.fdPerson.fdId=:fdPersonId and eopBasedataAccount.fdIsDefault=:fdIsDefault");
			hqlInfo.setParameter("fdPersonId",eopBasedataAccount.getFdPerson()==null?"":eopBasedataAccount.getFdPerson().getFdId());
			hqlInfo.setParameter("fdIsDefault",true);
			List<EopBasedataAccount> accountList = findList(hqlInfo);
			if(!ArrayUtil.isEmpty(accountList)){
				EopBasedataAccount account = accountList.get(0);
				account.setFdIsDefault(false);
				this.update(account);
			}
		}
		return super.add(modelObj);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		EopBasedataAccount eopBasedataAccount = (EopBasedataAccount) modelObj;
		//当前账户设置为默认账户，则将该人员的其它账户更新为非默认账户
		if(eopBasedataAccount.getFdIsDefault()){
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("eopBasedataAccount.fdPerson.fdId=:fdPersonId and eopBasedataAccount.fdId<>:fdId and eopBasedataAccount.fdIsDefault=:fdIsDefault");
			hqlInfo.setParameter("fdPersonId",eopBasedataAccount.getFdPerson()==null?"":eopBasedataAccount.getFdPerson().getFdId());
			hqlInfo.setParameter("fdId",eopBasedataAccount.getFdId());
			hqlInfo.setParameter("fdIsDefault",true);
			List<EopBasedataAccount> accountList = findList(hqlInfo);
			if(!ArrayUtil.isEmpty(accountList)){
				EopBasedataAccount account = accountList.get(0);
				account.setFdIsDefault(false);
				this.update(account);
			}
		}
		super.update(modelObj);
	}
	
	/**
	 * 根据员工id获取员工默认账户
	 * @param fdPersonId
	 * @return
	 * @throws Exception
	 */
	@Override
    @SuppressWarnings("unchecked")
	public EopBasedataAccount getDefaultEopBasedataAccountByPersonId(String fdPersonId) throws Exception {
		if(StringUtil.isNotNull(fdPersonId)){
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("eopBasedataAccount.fdIsAvailable=:fdIsAvailable and eopBasedataAccount.fdPerson.fdId=:fdPersonId and eopBasedataAccount.fdIsDefault=:fdIsDefault");
			hqlInfo.setParameter("fdIsAvailable", true);
			hqlInfo.setParameter("fdPersonId", fdPersonId);
			hqlInfo.setParameter("fdIsDefault", true);
			List<EopBasedataAccount> accountList = findList(hqlInfo);
			if(!ArrayUtil.isEmpty(accountList)){
				return accountList.get(0);
			}
		}
		return null;
	}

	@Override
	public List<EopBasedataImportMessage> saveImport(FormFile fdFile, String modelName) throws Exception {
		List<EopBasedataImportMessage> messages = super.saveImport(fdFile, modelName);
		//如果导入成功，更新所有人的默认账户
		if(messages.size()==0){
			String hql = "select acc.fdPerson.fdId,max(acc.fdId) from "+EopBasedataAccount.class.getName()+" acc where acc.fdIsDefault=:fdIsDefault and acc.fdIsAvailable=:fdIsAvailable group by acc.fdPerson.fdId";
			Query query = getBaseDao().getHibernateSession().createQuery(hql);
			query.setParameter("fdIsDefault", true);
			query.setParameter("fdIsAvailable", true);
		}
		return messages;
	}
	
	@Override
    public List<Map<String, String>> getDataList(RequestContext request) throws Exception {
		 List<Map<String, String>> rtnList=new ArrayList<>();
		 String type = request.getParameter("type");
		 String fdPersonId = request.getParameter("fdPersonId");
		 if("default".equals(type)){
        	EopBasedataAccount acc=getDefaultEopBasedataAccountByPersonId(fdPersonId);
        	if(acc!=null){
        		Map<String,String> map=new HashMap<>();
            	map.put("fdAccountId", acc.getFdId());
            	map.put("fdAccountName", acc.getFdName());
            	map.put("fdBankName", acc.getFdBankName());
            	map.put("fdBankAccount", acc.getFdBankAccount());
				map.put("fdAccountAreaName", acc.getFdAccountArea());
				map.put("fdBankAccountNo", acc.getFdBankNo());
            	rtnList.add(map);
        	}
		 }
		 return rtnList;
    }
}
