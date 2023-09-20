package com.landray.kmss.sys.attend.service.spring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.attend.service.ISysAttendAuthSettingService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.HQLUtil;


/**
 *
 * @author cuiwj
 * @version 1.0 2018-08-28
 */
public class SysAttendAuthSettingServiceImp extends BaseServiceImp
		implements ISysAttendAuthSettingService {

	/**
	 * 找出某个人可查看的组织架构
	 * 
	 * @param element
	 * @return
	 */
	@Override
	public List<String> findAuthListByUser(SysOrgElement element,
										   Integer[] orgType) {
		List<String> list = new ArrayList<String>();
		if (element == null) {
			return list;
		}
		try {
			List<String> ids = new ArrayList<String>();
			ids.add(element.getFdId());
			List fdPost = element.getFdPosts();
			if (fdPost != null && !fdPost.isEmpty()) {
				for (int i = 0; i < fdPost.size(); i++) {
					SysOrgElement ele = (SysOrgElement) fdPost.get(i);
					ids.add(ele.getFdId());
				}
			}
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("sysAttendAuthSetting.fdAuthList");
			hqlInfo.setJoinBlock(
					"left join sysAttendAuthSetting.fdElements fdElement");
			hqlInfo.setWhereBlock(HQLUtil.buildLogicIN("fdElement.fdId", ids));
			List resultList = findList(hqlInfo);

			if(resultList!=null && !resultList.isEmpty()){
				List<Integer> orgTypeList = Arrays.asList(orgType);
				for (Object obj : resultList) {
					SysOrgElement org = (SysOrgElement) obj;
					if (orgTypeList.contains(org.getFdOrgType())) {
						list.add(org.getFdId());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


}
