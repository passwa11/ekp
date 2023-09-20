package com.landray.kmss.fssc.fee.xform.impt.detail;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;

import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.eop.basedata.model.EopBasedataExchangeRate;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.fee.service.IFsscFeeMainService;
import com.landray.kmss.sys.transport.service.ISysTransportImportPropertyParse;
import com.landray.kmss.sys.transport.service.spring.ImportInDetailsCellContext;
import com.landray.kmss.sys.transport.service.spring.ImportUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class FsscFeeCurrencyDetailImportParse implements ISysTransportImportPropertyParse{
	private IFsscFeeMainService fsscFeeMainService;

	public IFsscFeeMainService getFsscFeeMainService() {
		if(fsscFeeMainService==null) {
			fsscFeeMainService = (IFsscFeeMainService) SpringBeanUtil.getBean("fsscFeeMainService");
		}
		return fsscFeeMainService;
	}

	@Override
	public boolean parse(ImportInDetailsCellContext detailsCellContext) throws Exception {
		KmssMessages contentMessage = detailsCellContext.getContentMessage();
		String propertyName = detailsCellContext.getPropertyName();
		String propertyId = propertyName;
		propertyName = propertyName+"_name";
		String fdCostRate = propertyId+"_cost_rate";
		String fdBudgetRate = propertyId+"_budget_rate";
		Map<String, String> temp = detailsCellContext.getTemp();
		Cell cell = detailsCellContext.getCell();
		String cellString = ImportUtil.getCellValue(cell);
		String fdCompanyId = temp.get("fdCompanyId");
		EopBasedataCompany comp =  (EopBasedataCompany) getFsscFeeMainService().findByPrimaryKey(fdCompanyId, EopBasedataCompany.class, true);
		String hql = "from "+EopBasedataCurrency.class.getName()+" where fdStatus=:fdIsAvailable and fdName=:code ";
		List<EopBasedataCurrency> list = getFsscFeeMainService().getBaseDao().getHibernateSession().createQuery(hql)
				.setParameter("fdIsAvailable", 0).setParameter("code", cellString).list();
		if(ArrayUtil.isEmpty(list)) {
			KmssMessage message = new KmssMessage(
					"sys-transport:sysTransport.import.dataError.notFoundForeignKey",
					cellString,
					detailsCellContext.getIndex() + 1,
					ResourceUtil.getString("table.eopBasedataCurrency","eop-basedata")
			);
			contentMessage.addError(message);
			temp.put(propertyId, "");
			temp.put(propertyName, "");
			return false;
		}
		temp.put(propertyId, list.get(0).getFdId());
		temp.put(propertyName, list.get(0).getFdName());
		hql = "select rate from "+EopBasedataExchangeRate.class.getName()+" rate left join rate.fdCompanyList comp where rate.fdSourceCurrency.fdId=:fdSourceCurrencyId and rate.fdTargetCurrency.fdId=:fdTargetCurrencyId and rate.fdIsAvailable=:fdIsAvailable and (comp.fdId=:fdCompanyId or comp is null)";
		List<EopBasedataExchangeRate> rateList = getFsscFeeMainService().getBaseDao().getHibernateSession().createQuery(hql)
				.setParameter("fdSourceCurrencyId", list.get(0).getFdId()).setParameter("fdTargetCurrencyId", comp.getFdAccountCurrency().getFdId())
				.setParameter("fdCompanyId", fdCompanyId).setParameter("fdIsAvailable", true).list();
		if(!ArrayUtil.isEmpty(rateList)) {
			rateList = EopBasedataFsscUtil.sortByCompany(rateList);
			temp.put(fdCostRate, String.valueOf(rateList.get(0).getFdRate()));
		}
		rateList = getFsscFeeMainService().getBaseDao().getHibernateSession().createQuery(hql)
				.setParameter("fdSourceCurrencyId", list.get(0).getFdId()).setParameter("fdTargetCurrencyId", comp.getFdBudgetCurrency().getFdId())
				.setParameter("fdCompanyId", fdCompanyId).setParameter("fdIsAvailable", true).list();
		if(!ArrayUtil.isEmpty(rateList)) {
			rateList = EopBasedataFsscUtil.sortByCompany(rateList);
			temp.put(fdBudgetRate, String.valueOf(rateList.get(0).getFdRate()));
		}
		return true;
	}

}
