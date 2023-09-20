package com.landray.kmss.eop.basedata.service;

import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataPayWay;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

import java.util.List;

/**
 * @author wangwh
 * @description:付款方式业务类
 * @date 2021/5/7
 */
public interface IEopBasedataPayWayService extends IExtendDataService {

	public EopBasedataPayWay getDefaultPayWay(EopBasedataCompany fdCompany) throws Exception;

	/**
	 * 查询公司下或公共的有效付款方式
	 * @param companyId
	 * @return
	 * @throws Exception
	 */
	public List<EopBasedataPayWay> getEopBasedataPayWayAvailable(String companyId)throws Exception;
}
