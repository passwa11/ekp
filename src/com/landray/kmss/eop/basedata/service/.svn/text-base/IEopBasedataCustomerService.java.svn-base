package com.landray.kmss.eop.basedata.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.eop.basedata.imp.context.EopBasedataImportMessage;
import com.landray.kmss.eop.basedata.model.EopBasedataCustomer;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.web.upload.FormFile;

public interface IEopBasedataCustomerService extends IExtendDataService {

	/**
	 * 通过对应登录人fdId查询客户
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public abstract List<EopBasedataCustomer> findByUserId(String userId) throws Exception;

	/**
	 * 通过名称查询客户
	 * @param fdName
	 * @return
	 * @throws Exception
	 */
	public abstract List<EopBasedataCustomer> findByName(String fdName) throws Exception;
	
	public abstract void downloadTemp(HttpServletResponse response) throws Exception;

	public abstract List<EopBasedataImportMessage> saveImport(FormFile fdFile)  throws Exception;

	public abstract void exportCustomer(HttpServletResponse response, String fdCompanyId)  throws Exception;
	
	/**
	 * 根据编码查询客户
	 * @param fdCode
	 * @return
	 * @throws Exception
	 */
	public EopBasedataCustomer findCustomerByCode(String fdCode, String fdCompanyCode) throws Exception;

	public EopBasedataCustomer getEopBasedataCustomerByCode(String fdCompanyId, String fdCode) throws Exception;
}
