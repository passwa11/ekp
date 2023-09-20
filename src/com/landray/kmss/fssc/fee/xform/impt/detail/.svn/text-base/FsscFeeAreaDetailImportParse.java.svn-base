package com.landray.kmss.fssc.fee.xform.impt.detail;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;

import com.landray.kmss.eop.basedata.model.EopBasedataArea;
import com.landray.kmss.eop.basedata.model.EopBasedataCity;
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

public class FsscFeeAreaDetailImportParse implements ISysTransportImportPropertyParse{
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
		String hql = "select city from "+EopBasedataCity.class.getName()+" city left join city.fdCompanyList comp where city.fdIsAvailable=:fdIsAvailable and (comp.fdId=:fdCompanyId or comp.fdId is null) and city.fdName=:fdName";
		List<EopBasedataCity> list = getFsscFeeMainService().getBaseDao().getHibernateSession().createQuery(hql)
				.setParameter("fdIsAvailable", true).setParameter("fdName", cellString).setParameter("fdCompanyId", temp.get("fdCompanyId")).list();
		if(!ArrayUtil.isEmpty(list)) {
			list = EopBasedataFsscUtil.sortByCompany(list);
			temp.put(propertyId, list.get(0).getFdId());
			temp.put(propertyName, list.get(0).getFdName());
			return true;
		}
		KmssMessage message = new KmssMessage(
				"sys-transport:sysTransport.import.dataError.notFoundForeignKey",
				cellString,
				detailsCellContext.getIndex() + 1,
				ResourceUtil.getString("eopBasedataArea.fdCity","eop-basedata")
		);
		contentMessage.addError(message);
		temp.put(propertyId, "");
		temp.put(propertyName, "");
		return false;
	}

}
