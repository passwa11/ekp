package com.landray.kmss.sys.attend.service.spring;

import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.attend.model.SysAttendOutPerson;
import com.landray.kmss.sys.attend.service.ISysAttendOutPersonService;
import com.landray.kmss.util.StringUtil;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-08-17
 */
public class SysAttendOutPersonServiceImp extends BaseServiceImp
		implements ISysAttendOutPersonService {

	@Override
	public SysAttendOutPerson findPersonByNameAndPhone(String name,
			String phoneNum) {
		try {
			HQLInfo hqlInfo = new HQLInfo();
			StringBuffer whereBlock = new StringBuffer("1=1");
			if (StringUtil.isNotNull(name)) {
				whereBlock.append(" and sysAttendOutPerson.fdName=:name");
				hqlInfo.setParameter("name", name);
			}
			if (StringUtil.isNotNull(phoneNum)) {
				whereBlock
						.append(" and sysAttendOutPerson.fdPhoneNum=:phoneNum");
				hqlInfo.setParameter("phoneNum", phoneNum);
			}
			hqlInfo.setOrderBy("sysAttendOutPerson.fdCreateTime desc");
			hqlInfo.setWhereBlock(whereBlock.toString());
			List<SysAttendOutPerson> list = findList(hqlInfo);
			if (list != null && !list.isEmpty()) {
				return list.get(0);
			}
		} catch (Exception e) {
		}
		return null;
	}

}
