package com.landray.kmss.km.comminfo.service.spring;

import org.hibernate.query.Query;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.HQLWrapper;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.dao.IHQLBuilder;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.StringUtil;

public class AuthDeleteValidatorImp implements IAuthenticationValidator,
		SysAuthConstant {

	private IBaseDao baseDao;

	private IHQLBuilder hqlBuilder;

	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public void setHqlBuilder(IHQLBuilder hqlBuilder) {
		this.hqlBuilder = hqlBuilder;
	}

	// 校验主函数
	@Override
	public boolean validate(ValidatorRequestContext validatorContext)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String id = validatorContext.getValidatorParaValue("recid");
		if (StringUtil.isNull(id)) {
            return false;
        }
		hqlInfo.setModelName(validatorContext.getValidatorPara("model"));
		hqlInfo.setAuthCheckType(validatorContext.getValidatorPara("type"));
		String tableName = hqlInfo.getModelTable();
		hqlInfo.setSelectBlock(tableName + ".fdId");
		hqlInfo.setWhereBlock(tableName + ".fdId=:fdId");
		hqlInfo.setParameter("fdId", id);
		hqlInfo.setDistinctType(HQLInfo.DISTINCT_NO);
		HQLWrapper hqlWrapper = hqlBuilder.buildQueryHQL(hqlInfo);
		Query query = baseDao.getHibernateSession().createQuery(
				hqlWrapper.getHql());
		HQLUtil.setParameters(query, hqlWrapper.getParameterList());
		boolean rtnVal = query.list().size() > 0;
		if (!rtnVal) {
			if (!baseDao.isExist(hqlInfo.getModelName(), id)) {
				throw new NoRecordException();
			}
		}
		return rtnVal;
	}
}