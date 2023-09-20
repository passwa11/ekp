package com.landray.kmss.fssc.fee.xform.impt.detail;

import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;

import com.landray.kmss.fssc.fee.service.IFsscFeeMainService;
import com.landray.kmss.sys.transport.service.ISysTransportImportPropertyParse;
import com.landray.kmss.sys.transport.service.spring.ImportInDetailsCellContext;
import com.landray.kmss.sys.transport.service.spring.ImportUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class FsscFeeAccountMoneyDetailImportParse implements ISysTransportImportPropertyParse{
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
		Map<String, String> temp = detailsCellContext.getTemp();
		Cell cell = detailsCellContext.getCell();
		String cellString = ImportUtil.getCellValue(cell);
		try {
			Double.parseDouble(cellString);
		}catch(Exception e) {
			KmssMessage message = new KmssMessage(
					"sys-transport:sysTransport.import.dataError.notNum",
					detailsCellContext.getIndex() + 1,
					cellString,
					ResourceUtil.getString("control.accountMoney.title","fssc-fee")
			);
			contentMessage.addError(message);
			temp.put(propertyName, "");
			return false;
		}
		temp.put(propertyName, cellString);
		return true;
	}

}
