package com.landray.kmss.sys.portal.auth;

import org.hibernate.query.Query;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.HQLWrapper;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.dao.IHQLBuilder;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.sys.authentication.intercept.AuthFieldValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.portal.model.SysPortalMain;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.StringUtil;

public class AuthPortalValidator extends AuthFieldValidator {
	private IBaseDao baseDao;

	private IHQLBuilder hqlBuilder;

	@Override
	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
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
            throw new UnexpectedRequestException();
        }
		hqlInfo.setModelName(validatorContext.getValidatorPara("model"));
		hqlInfo.setAuthCheckType(validatorContext.getValidatorPara("type"));
		String tableName = hqlInfo.getModelTable();
		hqlInfo.setSelectBlock(tableName + ".fdId");
		hqlInfo.setWhereBlock(tableName + ".sysPortalMain.fdId=:fdId");
		hqlInfo.setParameter("fdId", id);
		hqlInfo.setDistinctType(HQLInfo.DISTINCT_NO);
		HQLWrapper hqlWrapper = hqlBuilder.buildQueryHQL(hqlInfo);
		Query query = baseDao.getHibernateSession().createQuery(
				hqlWrapper.getHql());
		HQLUtil.setParameters(query, hqlWrapper.getParameterList());
		boolean rtnVal = query.list().size() > 0;
		if (!rtnVal) {
			if (!baseDao.isExist(SysPortalMain.class.getName(), id)) {
				throw new NoRecordException();
			}
		}
		return rtnVal;
	}
}
