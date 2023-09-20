package com.landray.kmss.eop.basedata.service;

import com.landray.kmss.eop.basedata.imp.context.EopBasedataImportMessage;
import com.landray.kmss.eop.basedata.model.EopBasedataSupplier;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.web.upload.FormFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author wangwh
 * @description:供应商业务类
 * @date 2021/5/7
 */
public interface IEopBasedataSupplierService extends IExtendDataService {

	/**
	 * 通过对应登录人fdId查询供应商
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public abstract List<EopBasedataSupplier> findByUserId(String userId) throws Exception;

	/**
	 * 通过名称查询供应商
	 * @param fdName
	 * @return
	 * @throws Exception
	 */
	public abstract List<EopBasedataSupplier> findByName(String fdName) throws Exception;
	
	/**
	 * 根据编码查询供应商
	 * @param fdCode
	 * @return
	 * @throws Exception
	 */
	public EopBasedataSupplier findSupplierByCode(String fdCode, String fdCompanyCode) throws Exception;
	
	public abstract void downloadTemp(HttpServletResponse response) throws Exception;

	public abstract List<EopBasedataImportMessage> saveImport(FormFile fdFile)  throws Exception;

	public abstract void exportSupplier(HttpServletResponse response, String fdCompanyId)  throws Exception;

	public EopBasedataSupplier getEopBasedataSupplierByCode(String fdCompanyId, String fdCode) throws Exception;

	public boolean checkFdCreditCode(String fdId, String fdCreditCode) throws Exception;
}
