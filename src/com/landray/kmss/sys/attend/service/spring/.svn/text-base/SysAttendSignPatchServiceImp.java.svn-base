package com.landray.kmss.sys.attend.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendMain;
import com.landray.kmss.sys.attend.model.SysAttendSignPatch;
import com.landray.kmss.sys.attend.service.ISysAttendMainService;
import com.landray.kmss.sys.attend.service.ISysAttendSignPatchService;
import com.landray.kmss.sys.attend.util.AttendPersonUtil;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-10-19
 */
public class SysAttendSignPatchServiceImp extends BaseServiceImp
		implements ISysAttendSignPatchService {

	private ISysAttendMainService sysAttendMainService;

	public void
			setSysAttendMainService(
					ISysAttendMainService sysAttendMainService) {
		this.sysAttendMainService = sysAttendMainService;
	}

	private ISysOrgCoreService sysOrgCoreService;

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	@Override
	public void updateMain(SysAttendCategory category,
			Map<String, JSONObject> map, String patchId) throws Exception {
		SysAttendSignPatch patch = null;
		if (StringUtil.isNotNull(patchId)) {
			patch = (SysAttendSignPatch) findByPrimaryKey(patchId);
		}
		HQLInfo hqlInfo = new HQLInfo();
		List<String> personIds = new ArrayList<String>();
		personIds.addAll(map.keySet());
		StringBuilder tempWhere =new StringBuilder();
		if(CategoryUtil.CATEGORY_FD_TYPE_TRUE.equals(category.getFdType())){
			//考勤组
			tempWhere.append(" sysAttendMain.fdHisCategory.fdId=:categoryId ");
		}else{
			//签到组
			tempWhere.append(" sysAttendMain.fdCategory.fdId=:categoryId ");
		}
		tempWhere.append(" and (sysAttendMain.docStatus=0 or sysAttendMain.docStatus is null)");
		tempWhere.append(" and ").append(HQLUtil.buildLogicIN(
				"sysAttendMain.docCreator.fdId",
				personIds));
		hqlInfo.setWhereBlock( tempWhere.toString());
		hqlInfo.setParameter("categoryId", category.getFdId());
		List<SysAttendMain> mainList = sysAttendMainService.findList(hqlInfo);

		boolean isUnlimitOut = Boolean.TRUE.equals(category.getFdUnlimitTarget());
		String targetIds = "";
		for (SysOrgElement ele : category.getFdTargets()) {
			List orgList = new ArrayList();
			orgList.add(ele);
			List ids = AttendPersonUtil.expandToPersonIds(orgList);
			targetIds += StringUtil.join(
					(String[]) ids.toArray(new String[ids.size()]),
					";");
			targetIds += ";";
		}
		for (String personId : map.keySet()) {
			JSONObject json = map.get(personId);
			Date signTime = new Date(json.getLong("signTime"));
			Date lateTime = new Date(json.getLong("lateTime"));
			String str=DateUtil.convertDateToString(lateTime,"yyyy-MM-dd HH:mm");
			int status=1;
			if(!str.contains("00:00")){
				if(signTime.getTime()>lateTime.getTime()) {
					status=2;
				}
			}
			boolean isFind = false;
			for (SysAttendMain main : mainList) {
				String docCreatorId = main.getDocCreator().getFdId();
				if (personId.equals(docCreatorId)) {
					if (targetIds.contains(docCreatorId)) {
						main.setFdStatus(status);
						main.setFdOutTarget(false);
						main.setDocCreateTime(signTime);
						main.setFdSignPatch(patch);
						sysAttendMainService.getBaseDao().update(main);
					} else if (isUnlimitOut) {
						main.setFdStatus(1);
						main.setFdOutTarget(true);
						main.setDocCreateTime(signTime);
						main.setFdSignPatch(patch);
						sysAttendMainService.getBaseDao().update(main);
					}
					isFind = true;
					break;
				}
			}
			if (!isFind && isUnlimitOut) {
				SysAttendMain main = new SysAttendMain();
				main.setFdHisCategory(CategoryUtil.getHisCategoryById(category.getFdId()));
				main.setFdStatus(1);
				main.setFdOutTarget(true);
				main.setDocCreator((SysOrgPerson) sysOrgCoreService
						.findByPrimaryKey(personId, SysOrgPerson.class));
				main.setDocCreateTime(signTime);
				main.setFdSignPatch(patch);
				sysAttendMainService.getBaseDao().add(main);
			}
		}
	}

}
