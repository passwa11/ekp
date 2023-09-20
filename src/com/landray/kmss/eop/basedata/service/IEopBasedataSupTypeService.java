package com.landray.kmss.eop.basedata.service;

import java.util.List;

import com.landray.kmss.eop.basedata.model.EopBasedataSupType;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

/**
 * @author wangwh
 * @description:供应商类别业务类
 * @date 2021/5/7
 */
public interface IEopBasedataSupTypeService extends IExtendDataService {

	public abstract List<EopBasedataSupType> findByFdParent(EopBasedataSupType fdParent) throws Exception;

	public boolean checkUnique(String fdId, String fdName) throws Exception;
}
