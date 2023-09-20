package com.landray.kmss.fssc.fee.xform.impt.detail;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;

import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.fssc.fee.model.FsscFeeExpenseItem;
import com.landray.kmss.fssc.fee.service.IFsscFeeMainService;
import com.landray.kmss.sys.transport.service.ISysTransportImportPropertyParse;
import com.landray.kmss.sys.transport.service.spring.ImportInDetailsCellContext;
import com.landray.kmss.sys.transport.service.spring.ImportUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class FsscFeeExpenseItemDetailImportParse implements ISysTransportImportPropertyParse{
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
		String hql = "select item from "+FsscFeeExpenseItem.class.getName()+" item left join item.fdItemList itemList where item.fdTemplate.fdId=:fdTemplateId and itemList.fdName=:code and item.fdCompany.fdId=:fdCompanyId";
		List<FsscFeeExpenseItem> list = getFsscFeeMainService().getBaseDao().getHibernateSession().createQuery(hql)
				.setParameter("fdTemplateId", temp.get("fdTemplateId")).setParameter("code", cellString).setParameter("fdCompanyId", temp.get("fdCompanyId")).list();
		if(ArrayUtil.isEmpty(list)) {
			KmssMessage message = new KmssMessage(
					"sys-transport:sysTransport.import.dataError.notFoundForeignKey",
					cellString,
					detailsCellContext.getIndex() + 1,
					ResourceUtil.getString("table.eopBasedataExpenseItem","eop-basedata")
			);
			contentMessage.addError(message);
			temp.put(propertyId, "");
			temp.put(propertyName, "");
			return false;
		}
		List<EopBasedataExpenseItem> fdItemList = list.get(0).getFdItemList();
		for(EopBasedataExpenseItem item:fdItemList) {
			if(item.getFdName().equals(cellString)) {
				temp.put(propertyId, item.getFdId());
				temp.put(propertyName, item.getFdName());
			}
		}
		return true;
	}

}
