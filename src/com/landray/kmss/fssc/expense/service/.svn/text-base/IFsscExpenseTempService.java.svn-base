package com.landray.kmss.fssc.expense.service;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.fssc.expense.forms.FsscExpenseTempForm;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

public interface IFsscExpenseTempService extends IExtendDataService {

	public void getTempInvoiceInfo(FsscExpenseTempForm fsscExpenseTempForm, HttpServletRequest request) throws Exception;

	public void getNewDetailTempInvoiceInfo(FsscExpenseTempForm fsscExpenseTempForm, HttpServletRequest request) throws Exception;

    public String  getIsCurrent (String fdCompanyId,String fdTaxNumber,String fdPurchName,String fdInvoiceType) throws Exception;
}
