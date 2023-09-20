package com.landray.kmss.sys.attend.service.spring;

import org.hibernate.query.Query;

import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.sys.attend.dao.SQLInfo;
import com.landray.kmss.sys.attend.service.ISysAttendSynDingBakService;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class SysAttendSynDingReaderValidator implements IAuthenticationValidator {

	private IBaseDao baseDao;

	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}

	private ISysAttendSynDingBakService sysAttendSynDingBakService = (ISysAttendSynDingBakService) SpringBeanUtil
			.getBean(
					"sysAttendSynDingBakService");

	@Override
	public boolean validate(ValidatorRequestContext validatorContext)
			throws Exception {

		SysOrgElement element = validatorContext.getUser().getPerson();
		String id = validatorContext.getValidatorParaValue("recid");
		String yearStr = validatorContext.getValidatorParaValue("year");
		String tableName = validatorContext.getValidatorParaValue("tableName");

		if (StringUtil.isNull(id) || StringUtil.isNull(yearStr)) {
			throw new UnexpectedRequestException();
		}
		int year = Integer.parseInt(yearStr);
		tableName += "_" + year;
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("select * from ").append(tableName);
		stringBuffer.append(" where fd_id=:fdId and fd_person_id=:fdPersonId");
		
		Query query = baseDao.getHibernateSession().createQuery(
				stringBuffer.toString());
		// wanb 优化查询性能
		query.setMaxResults(1);
		query.setParameter("fdId", id);
		query.setParameter("fdPersonId", element.getFdId());
		boolean rtnVal = query.list().size() > 0;
		if (!rtnVal) {
			SQLInfo sqlInfo = new SQLInfo();
			sqlInfo.setTableName(tableName);
			sqlInfo.setWhereBlock(" fd_id=:fdId");
			sqlInfo.setParameter("fdId", id);
			if (!sysAttendSynDingBakService.isExist(sqlInfo)) {
				throw new NoRecordException();
			}
		}
		return rtnVal;
	}

}
