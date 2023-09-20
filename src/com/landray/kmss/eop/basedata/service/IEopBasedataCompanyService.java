package com.landray.kmss.eop.basedata.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCompanyGroup;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.sunbor.web.tag.Page;

public interface IEopBasedataCompanyService extends IEopBasedataBusinessService {

    public abstract List<EopBasedataCompany> findByFdBudgetCurrency(EopBasedataCurrency fdBudgetCurrency) throws Exception;

    public abstract List<EopBasedataCompany> findByFdAccountCurrency(EopBasedataCurrency fdAccountCurrency) throws Exception;

    public abstract List<EopBasedataCompany> findByFdGroup(EopBasedataCompanyGroup fdGroup) throws Exception;

    public EopBasedataCompany getEopBasedataCompanyByCode(String fdCode) throws Exception;

	public abstract List<EopBasedataCompany> findCompanyByUserId(String fdUserId) throws Exception;

    public void updateCompanyGroup() throws Exception;

	public abstract void fillContactorInfo(JSONObject rtnData, String contactorId) throws Exception;

	public abstract Page findPageForCase(HttpServletRequest request, HQLInfo hqlInfo) throws Exception;
}
