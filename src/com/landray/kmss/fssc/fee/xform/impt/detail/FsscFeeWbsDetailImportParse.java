package com.landray.kmss.fssc.fee.xform.impt.detail;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;

import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataWbs;
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

public class FsscFeeWbsDetailImportParse implements ISysTransportImportPropertyParse{
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
		Map<String, String> temp = detailsCellContext.getTemp();
		Cell cell = detailsCellContext.getCell();
		String cellString = ImportUtil.getCellValue(cell);
		String hql = "select wbs from "+EopBasedataWbs.class.getName()+" wbs left join wbs.fdCompanyList comp where wbs.fdIsAvailable=:fdIsAvailable and wbs.fdName=:code and (comp.fdId=:fdCompanyId or comp is null)";
		List<EopBasedataWbs> list = getFsscFeeMainService().getBaseDao().getHibernateSession().createQuery(hql)
				.setParameter("fdIsAvailable", true).setParameter("code", cellString).setParameter("fdCompanyId", temp.get("fdCompanyId")).list();
		if(ArrayUtil.isEmpty(list)) {
			KmssMessage message = new KmssMessage(
					"sys-transport:sysTransport.import.dataError.notFoundForeignKey",
					cellString,
					detailsCellContext.getIndex() + 1,
					ResourceUtil.getString("table.eopBasedataWbs","eop-basedata")
			);
			contentMessage.addError(message);
			temp.put(propertyId, "");
			temp.put(propertyName, "");
			return false;
		}
		list = EopBasedataFsscUtil.sortByCompany(list);
		temp.put(propertyId, list.get(0).getFdId());
		temp.put(propertyName, list.get(0).getFdName());
		return true;
	}

}
