package com.landray.kmss.hr.staff.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.staff.dao.IHrStaffPayrollIssuanceDao;
import org.hibernate.query.Query;

public class HrStaffPayrollIssuanceDaoImp extends BaseDaoImp implements
		IHrStaffPayrollIssuanceDao {

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		String hql = "delete from HrStaffSalaryInfo hrStaffSalaryInfo where hrStaffSalaryInfo.fdPayrollIssuanceId = :fdPayrollIssuanceId";
		Query query = getHibernateSession().createQuery(hql);
		query.setString("fdPayrollIssuanceId", modelObj.getFdId());
		query.executeUpdate();
		super.delete(modelObj);
	}
}
