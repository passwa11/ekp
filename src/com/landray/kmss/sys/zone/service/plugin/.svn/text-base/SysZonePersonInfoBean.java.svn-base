/**
 * 
 */
package com.landray.kmss.sys.zone.service.plugin;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.sys.fans.service.ISysFansMainService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.person.interfaces.PersonInfoService;
import com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter;
import com.landray.kmss.sys.zone.model.SysZonePersonInfo;
import com.landray.kmss.sys.zone.service.ISysZonePersonInfoService;
import com.landray.kmss.sys.zone.util.SysZoneConfigUtil;
import com.landray.kmss.sys.zone.util.SysZonePrivateUtil;
import com.landray.kmss.util.*;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 傅游翔
 * 
 */
public class SysZonePersonInfoBean implements PersonInfoService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysZonePersonInfoBean.class);

	
	private  ISysZonePersonInfoService sysZonePersonInfoService;
	
	public void setSysZonePersonInfoService(
			ISysZonePersonInfoService sysZonePersonInfoService) {
		this.sysZonePersonInfoService = sysZonePersonInfoService;
	}
	
	private ISysOrgPersonService sysOrgPersonService;
	
	public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	@Override
    public String getHomepage(String personId) {
		return "/sys/zone/index.do?userid=" + personId;
	}
	
	@Override
	public String getPersonInfo(String personId) throws Exception {
		JSONObject personInfo = new JSONObject();
		SysOrgPerson person = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(personId);
		personInfo.accumulate("fdId", personId);
		personInfo.accumulate("fdName", StringUtil
				.XMLEscape(person.getFdName()));
		//personInfo.accumulate("fdLoginName", person.getFdLoginName());
		personInfo.accumulate("fdIsAvailable", person.getFdIsAvailable());
		// 机构名称
		personInfo.accumulate("fdOrgName", StringUtil
				.XMLEscape(sysZonePersonInfoService.getoOrganization(person)));
		
		if(!SysZonePrivateUtil.isDepInfoPrivate(personId)){
			// 部门名称
			personInfo.accumulate("fdDeptName", person.getFdParent() == null ? "" : person.getFdParent().getDeptLevelNames());
			// 岗位名称
			personInfo.accumulate(
					"fdPostName",
					ArrayUtil.isEmpty(person.getFdPosts())
							? ""
							: StringUtil
									.XMLEscape(ArrayUtil.joinProperty(
											person.getFdPosts(), "fdName",
											";")[0]));
			
		} else {
			personInfo.accumulate("fdDeptName", ResourceUtil
					.getString("sysZonePerson.undisclosed2", "sys-zone"));
			// 岗位名称
			personInfo.accumulate("fdPostName", ResourceUtil
					.getString("sysZonePerson.undisclosed2", "sys-zone"));
		}
		
		if(!SysZonePrivateUtil.isContactPrivate(personId)){
			personInfo.accumulate("fdMobileNo",
					StringUtil.isNull(person.getFdMobileNo()) ? ""
							: StringUtil.XMLEscape(person
					.getFdMobileNo()));
			personInfo.accumulate(
					"fdWorkPhone",
					StringUtil.isNull(person.getFdWorkPhone()) ? ""
							: StringUtil
									.XMLEscape(person.getFdWorkPhone()));
			personInfo.accumulate(
					"fdEmail",
					StringUtil.isNull(person.getFdEmail()) ? ""
							: StringUtil
							.XMLEscape(person.getFdEmail()));
			personInfo.accumulate("fdShortNo",
					StringUtil.isNull(person.getFdShortNo()) ? ""
							: StringUtil.XMLEscape(person
					.getFdShortNo()));
		} else {
			personInfo.accumulate("fdMobileNo", ResourceUtil
					.getString("sysZonePerson.undisclosed2", "sys-zone"));
			personInfo.accumulate("fdWorkPhone", ResourceUtil
					.getString("sysZonePerson.undisclosed2", "sys-zone"));
			personInfo.accumulate("fdShortNo", ResourceUtil
					.getString("sysZonePerson.undisclosed2", "sys-zone"));
			// 岗位名称
			personInfo.accumulate("fdEmail", ResourceUtil
					.getString("sysZonePerson.undisclosed2", "sys-zone"));
		}
		
		String fdSex = person.getFdSex();
		personInfo.accumulate("fdSex", fdSex);
		personInfo.accumulate("fdSexText",
				StringUtil.isNull(fdSex) ? ""
						: EnumerationTypeUtil.getColumnEnumsLabel("sys_org_person_sex",fdSex));
		// 员工黄页信息
		SysZonePersonInfo zonePerson = (SysZonePersonInfo) 
				sysZonePersonInfoService.findByPrimaryKey(personId, null, true);
		if(null == zonePerson){
			zonePerson = new SysZonePersonInfo();
		}
		personInfo.accumulate("fdSignature", StringUtil.XMLEscape(zonePerson
				.getFdSignature()));	 	
		// 关注数
		personInfo.accumulate(
				"fdAttentionNum",
				zonePerson.getFdAttentionNum() == null ? 0
						: zonePerson.getFdAttentionNum());
		// 粉丝数
		personInfo.accumulate("fdFansNum", zonePerson.getFdFansNum() == null
				? 0 : zonePerson.getFdFansNum());
		personInfo.accumulate("fdTags", sysZonePersonInfoService.getTagNamesByPersonId(personId));
		
		personInfo.accumulate("isSelf", UserUtil.getUser().getFdId().equals(personId));
		
		String imgPath = PersonInfoServiceGetter.getPersonHeadimageUrl(personId, "b");
		personInfo.accumulate("imgUrl", imgPath);
		personInfo.accumulate("isFullPath", PersonInfoServiceGetter
				.isFullPath(imgPath));
		
		//关注关系
		ISysFansMainService service = (ISysFansMainService)SpringBeanUtil.getBean("sysFansMainService");
		if(service != null && !(personId).equals(UserUtil.getUser().getFdId())) {
			Integer rela = service.getRelation(UserUtil.getUser().getFdId(),personId);
			personInfo.accumulate("rela", rela);
		}
		
		personInfo.accumulate("isContactPrivate", zonePerson.getIsContactPrivate());
		personInfo.accumulate("isDepInfoPrivate", zonePerson.getIsDepInfoPrivate());
		
		// 如果钉钉集成或者蓝桥集成开启，需要返回钉钉用户ID和cropid，否则
		Map<String, String> checkMap = checkDing( personId);
		if (checkMap.size() >= 2) {
			personInfo.accumulate("dingUserid", checkMap.get("dingUserid"));
			personInfo.accumulate("ldingUserid", checkMap.get("ldingUserid"));
			personInfo.accumulate("dingCropid", checkMap.get("dingCropid"));
		} else {
			personInfo.accumulate("dingUserid", "");
			personInfo.accumulate("ldingUserid", "");
			personInfo.accumulate("dingCropid", "");
			logger.warn("蓝桥或钉钉组件不存在");
		}

		return personInfo.toString();
	}

	/**
	 * 检测钉钉集成或者蓝桥集成是否开启公共方法
	 * 
	 * @return
	 */
	public static Map<String, String> checkDing(String personId) {
		Map<String, String> resultMap = new HashMap<>();

		String dingModelName = "com.landray.kmss.third.ding.model.DingConfig";
		String ldingModelName = "com.landray.kmss.third.lding.model.LdingConfig";

		Map<String, String> dingAppConfigMap = new HashMap<>();
		try {
			if (SysZoneConfigUtil.moduleExist("/third/ding")) {
				BaseAppConfig dingAppConfig = (BaseAppConfig) com.landray.kmss.util.ClassUtils.forName(dingModelName).newInstance();
				dingAppConfigMap = dingAppConfig.getDataMap();
			}

		} catch (Exception e) {
			logger.debug("钉钉组件不存在");
		}

		Map<String, String> ldingAppConfigMap = new HashMap<>();
		try {
			if (SysZoneConfigUtil.moduleExist("/third/lding")) {
				BaseAppConfig ldingAppConfig = (BaseAppConfig) com.landray.kmss.util.ClassUtils.forName(ldingModelName).newInstance();
				ldingAppConfigMap = ldingAppConfig.getDataMap();
			}
		} catch (Exception e) {
			logger.debug("蓝桥组件不存在");
		}

		// 先检测钉钉，后检测蓝桥
		if ((dingAppConfigMap.containsKey("dingEnabled")
				&& "true".equals(dingAppConfigMap.get("dingEnabled")))) {
			resultMap.put("dingCropid", dingAppConfigMap.get("dingCorpid"));

			// 在钉钉映射表查询钉钉用户ID
			try {
				IBaseService omsRelationService = (IBaseService) SpringBeanUtil
						.getBean("omsRelationService");
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock("fdEkpId=:fdEkpId");
				hqlInfo.setParameter("fdEkpId", personId);

				Object obj = omsRelationService
						.findFirstOne(hqlInfo);
				if (obj!=null) {
					String dingUserid = ModelUtil.getModelPropertyString(
							obj, "fdAppPkId", "", null);
					resultMap.put("dingUserid", dingUserid);
				}
			} catch (Exception e) {
				logger.error("该用户在钉钉不存在", e);
			}

		} else if ((ldingAppConfigMap.containsKey("ldingEnabled")
				&& "true".equals(ldingAppConfigMap.get("ldingEnabled")))) {
			resultMap.put("dingCropid", ldingAppConfigMap.get("corpID"));

			// 在蓝桥映射表查询钉钉用户ID
			try {
				IBaseService thirdLdingIdmapService = (IBaseService) SpringBeanUtil
						.getBean("thirdLdingIdmapService");
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock("fdEkpId=:fdEkpId");
				hqlInfo.setParameter("fdEkpId", personId);

				Object obj = thirdLdingIdmapService.findFirstOne(hqlInfo);
				if (obj!=null) {
					String ldingUserid = ModelUtil.getModelPropertyString(
							obj, "fdDtId", "", null);
					resultMap.put("ldingUserid", ldingUserid);
				}
			} catch (Exception e) {
				logger.error("该用户在钉钉不存在", e);
			}
		}

		return resultMap;
	}
	
	
}
