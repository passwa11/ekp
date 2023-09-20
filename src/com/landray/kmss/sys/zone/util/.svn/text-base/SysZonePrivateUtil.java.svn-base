package com.landray.kmss.sys.zone.util;

import java.util.List;
import java.util.Map;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.zone.model.SysZonePersonInfo;
import com.landray.kmss.sys.zone.model.SysZonePrivateConfig;
import com.landray.kmss.sys.zone.service.ISysZonePersonInfoService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class SysZonePrivateUtil {

	public static Map<String, String> getPrivateConfig() throws Exception {
		return new SysZonePrivateConfig().getDataMap();
	}

	private static ISysZonePersonInfoService sysZonePersonInfoService;

	public static ISysZonePersonInfoService getSysZonePersonInfoService() {
		if (sysZonePersonInfoService == null) {
            sysZonePersonInfoService = (ISysZonePersonInfoService) SpringBeanUtil
                    .getBean("sysZonePersonInfoService");
        }
		return sysZonePersonInfoService;
	}

	private static SysZonePersonInfo getPersonInfo(String fdPersonId)
			throws Exception {
		SysZonePersonInfo personInfo = (SysZonePersonInfo) getSysZonePersonInfoService()
				.findByPrimaryKey(fdPersonId, null, true);
		if (personInfo == null) {
			personInfo = getSysZonePersonInfoService().updateGetPerson(fdPersonId);
		}
		return personInfo;
	}

	public static Boolean isALlContactPrivate() throws Exception {
		return "1".equals(getPrivateConfig().get("isContactPrivate"));
	}

	public static Boolean isAllDepInfoPrivate() throws Exception {
		return "1".equals(getPrivateConfig().get("isDepInfoPrivate"));
	}

	public static Boolean isAllRelationshipPrivate() throws Exception {
		return "1".equals(getPrivateConfig().get("isRelationshipPrivate"));
	}

	public static Boolean isAllWorkmatePrivate() throws Exception {
		return "1".equals(getPrivateConfig().get("isWorkmatePrivate"));
	}

	public static Boolean isContactPrivate(String fdPersonId) throws Exception {
		Boolean flag = getPersonInfo(fdPersonId).getIsContactPrivate();
		return isALlContactPrivate() ||  (flag == null ? false : flag);
	}

	public static Boolean isDepInfoPrivate(String fdPersonId) throws Exception {
		Boolean flag = getPersonInfo(fdPersonId).getIsDepInfoPrivate();
		return isAllDepInfoPrivate() || (flag == null ? false : flag);
	}

	public static Boolean isRelationshipPrivate(String fdPersonId)
			throws Exception {
		Boolean flag =  getPersonInfo(fdPersonId).getIsRelationshipPrivate();
		return isAllRelationshipPrivate() || (flag == null ? false : flag);
	}

	public static Boolean isWorkmatePrivate(String fdPersonId) throws Exception {
		Boolean flag = getPersonInfo(fdPersonId).getIsWorkmatePrivate();
		return isAllWorkmatePrivate() || (flag == null ? false : flag);
	}
	
	
	/**
	 * 获取联系方式为隐藏的人
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public static List<String> getContactPrivateByIds(List<String> ids, String proName)  throws Exception {
		if(ArrayUtil.isEmpty(ids) || StringUtil.isNull(proName))  {
			return null;
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("sysZonePersonInfo.fdId");
		String whereBlock = "sysZonePersonInfo." + proName + " =:private";
		hqlInfo.setParameter("private", Boolean.TRUE);
		if(!ArrayUtil.isEmpty(ids)) {
			whereBlock = StringUtil.linkString(whereBlock, 
				" and ", HQLUtil.buildLogicIN("sysZonePersonInfo.fdId", ids));
		}
		hqlInfo.setWhereBlock(whereBlock);
		ISysZonePersonInfoService service = ((ISysZonePersonInfoService) SpringBeanUtil
				.getBean("sysZonePersonInfoService"));
		List<String> rtn = (List<String>)service.findList(hqlInfo);
		return rtn;
	}
}
