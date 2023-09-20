package com.landray.kmss.eop.basedata.service.spring;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCustomerAccount;
import com.landray.kmss.eop.basedata.model.EopBasedataPayWay;
import com.landray.kmss.eop.basedata.model.EopBasedataSupplier;
import com.landray.kmss.eop.basedata.model.EopBasedataSupplierAccount;
import com.landray.kmss.eop.basedata.service.IEopBasedataCustomerAccountService;
import com.landray.kmss.eop.basedata.service.IEopBasedataCustomerService;
import com.landray.kmss.eop.basedata.service.IEopBasedataPayWayService;
import com.landray.kmss.framework.util.PluginConfigLocationsUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class EopBasedataCustomerAccountServiceImp  extends ExtendDataServiceImp implements IEopBasedataCustomerAccountService,IXMLDataBean {

	private IEopBasedataCustomerService eopBasedataCustomerService;
	
	public IEopBasedataCustomerService getEopBasedataCustomerService() {
		if (eopBasedataCustomerService == null) {
			eopBasedataCustomerService = (IEopBasedataCustomerService) SpringBeanUtil.getBean("eopBasedataCustomerService");
        }
		return eopBasedataCustomerService;
	}
	
	private IEopBasedataPayWayService eopBasedataPayWayService;
	
	public IEopBasedataPayWayService getEopBasedataPayWayService() {
		if (eopBasedataPayWayService == null) {
			eopBasedataPayWayService = (IEopBasedataPayWayService) SpringBeanUtil.getBean("eopBasedataPayWayService");
        }
		return eopBasedataPayWayService;
	}
	
	@Override
    public List<Map<String, String>> getDataList(RequestContext requestInfo) throws Exception {
        List<Map<String, String>> rtnList = new ArrayList<Map<String, String>>();
        Map<String, String> rtnMap = new HashMap<String, String>();
        String fdCustomerId = requestInfo.getParameter("fdCustomerId");
        String getCustomerAccount = requestInfo.getParameter("getCustomerAccount");
        String type = requestInfo.getParameter("type");
        String fdCompanyId = requestInfo.getParameter("fdCompanyId");
        HQLInfo hqlInfo = new HQLInfo();
        if ("getCustomerAccount".equals(getCustomerAccount) && StringUtil.isNotNull(fdCustomerId)) {
            StringBuffer whereBlock = new StringBuffer("1=1");
            whereBlock.append(" and eopBasedataCustomerAccount.docMain.fdId=:fdCustomerId");
            hqlInfo.setParameter("fdCustomerId", fdCustomerId);
            hqlInfo.setWhereBlock(whereBlock.toString());
            List<EopBasedataCustomerAccount> customerAccounts = this.findList(hqlInfo);
            if (!ArrayUtil.isEmpty(customerAccounts)) {
                EopBasedataCustomerAccount customerAccount = customerAccounts.get(0);
                rtnMap.put("fdAccountName", customerAccount.getFdAccountName());
                rtnMap.put("fdBankName", customerAccount.getFdBankName());
                rtnMap.put("fdBankAccount", customerAccount.getFdBankAccount());
                rtnMap.put("fdAccountAreaCode", customerAccount.getFdAccountAreaCode());
                rtnMap.put("fdAccountAreaName", customerAccount.getFdAccountAreaName());
                rtnMap.put("fdBankNo", customerAccount.getFdBankNo());    //联行号
            }
            EopBasedataCompany fdCompany = new EopBasedataCompany();
			fdCompany.setFdId(fdCompanyId);
			EopBasedataPayWay payway=getEopBasedataPayWayService().getDefaultPayWay(fdCompany);
			if(payway!=null){
				rtnMap.put("fdPayWayId", payway.getFdId());
				rtnMap.put("fdPayWayName", payway.getFdName());
				rtnMap.put("fdPayBankId", payway.getFdDefaultPayBank()!=null?payway.getFdDefaultPayBank().getFdId():"");
                rtnMap.put("fdIsTransfer", String.valueOf(payway.getFdIsTransfer()));	//是否涉及转账
			}
			rtnList.add(rtnMap);
        }

        //供应商变更选择供应商后，根据供应商编号查找账户信息
        if ("getAccountsByCode".equals(type)) {
            String fdCode = requestInfo.getParameter("fdCode");
            hqlInfo.setJoinBlock(" left join eopBasedataSupplier.fdCompanyList company");
            hqlInfo.setWhereBlock("eopBasedataSupplier.fdCode = :fdCode and (company.fdId=:fdCompanyId or company is null)");
            hqlInfo.setParameter("fdCode", fdCode);
            hqlInfo.setParameter("fdCompanyId", fdCompanyId);
            hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
                    "eopBasedataSupplier.fdIsAvailable=:fdIsAvailable"));
            hqlInfo.setParameter("fdIsAvailable", true);
            List<EopBasedataSupplier> supplier = getEopBasedataCustomerService().findList(hqlInfo);
            if (!supplier.isEmpty()) {
                List<EopBasedataSupplierAccount> accountList = supplier.get(0).getFdAccountList();
                if (!ArrayUtil.isEmpty(accountList)) {
                    for (EopBasedataSupplierAccount detail : accountList) {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("fdSupplierArea", detail.getFdSupplierArea());    //所在区域
                        map.put("fdAccountName", detail.getFdAccountName());    //收款账户名
                        map.put("fdBankName", detail.getFdBankName());    //开户行
                        map.put("fdBankNo", detail.getFdBankNo());    //联行号
                        map.put("fdBankAccount", detail.getFdBankAccount());    //账号
						if (new File(PluginConfigLocationsUtil.getKmssConfigPath()
								+ "/fssc/cmb/").exists()) {
							map.put("fdAccountAreaCode", detail.getFdAccountAreaCode());
							map.put("fdAccountAreaName", detail.getFdAccountAreaName());
						}
                        map.put("fdBankSwift", detail.getFdBankSwift());    //收款银行swift号
                        map.put("fdReceiveCompany", detail.getFdReceiveCompany());    //收款公司名称
                        map.put("fdReceiveBankName", detail.getFdReceiveBankName());    //收款银行名称(境外)
                        map.put("fdReceiveBankAddress", detail.getFdReceiveBankAddress());    //收款银行地址(境外)
                        map.put("fdInfo", detail.getFdInfo());    //其他信息
                        rtnList.add(map);
                    }
                }
            }
        }
        return rtnList;
    }
}
