package com.landray.kmss.tic.jdbc.service.spring;

import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.tic.jdbc.service.ITicJdbcQueryService;
import com.landray.kmss.util.ArrayUtil;

/**
 * 函数查询业务接口实现
 * 
 * @author 
 * @version 1.0 2012-08-28
 */
public class TicJdbcQueryServiceImp extends BaseServiceImp
		implements ITicJdbcQueryService {
	@Override
    public boolean validateQuery(String fdId) throws Exception {
		HQLInfo hql = new HQLInfo();
		hql.setWhereBlock("ticJdbcQuery.ticJdbcDataSet.fdId =:fdId");
		hql.setParameter("fdId", fdId);
		List list = this.findList(hql);
		return ArrayUtil.isEmpty(list);
	}
}
