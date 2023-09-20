package com.landray.kmss.sys.attachment.borrow.service;

import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.attachment.borrow.model.SysAttBorrow;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 借阅权限校验器
 * 
 * @author
 *
 */
public abstract class ISysAttBorrowValidator {

	/**
	 * 权限类型
	 * 
	 * @return
	 */
	public abstract String getType();

	/**
	 * 字段
	 * 
	 * @return
	 */
	public abstract String getProp();

	/**
	 * 权限校验
	 * 
	 * @param fdAttId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Boolean auth(String fdAttId) throws Exception {

		HQLInfo hqlInfo = new HQLInfo();

		hqlInfo.setSelectBlock("count(sysAttBorrow.fdId)");
		hqlInfo.setWhereBlock("sysAttBorrow." + getProp() + " = :"+getProp()
				+ " and sysAttBorrow.fdAttId = :fdAttId and sysAttBorrow.fdStatus = :fdStatus "
				+ " and sysAttBorrow.fdBorrowers.fdId in (:orgIds)");
		hqlInfo.setParameter(getProp(), true);
		List<String> orgIds =
				UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds();
		hqlInfo.setParameter("fdAttId", fdAttId);
		hqlInfo.setParameter("fdStatus", SysAttBorrow.STATUS_DOING);
		hqlInfo.setParameter("orgIds", orgIds);
		List<Object> counts =
				((IBaseService) SpringBeanUtil.getBean("sysAttBorrowService"))
						.findValue(hqlInfo);

		if (counts.size() > 0) {
			return Integer.parseInt(counts.get(0).toString()) > 0 ? true
					: false;
		}

		return false;
	}

}
