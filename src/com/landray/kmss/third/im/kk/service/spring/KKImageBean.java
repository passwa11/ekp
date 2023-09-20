package com.landray.kmss.third.im.kk.service.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.person.interfaces.PersonImageService;
import com.landray.kmss.third.im.kk.constant.KeyConstants;
import com.landray.kmss.third.im.kk.service.IKkImConfigService;
import com.landray.kmss.util.StringUtil;

public class KKImageBean implements PersonImageService {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(KKImageBean.class);
	
	public static final String GET_USER_FACE_URL = "/api.php/Sns/get_user_face";
	
	private ISysOrgPersonService sysOrgPersonService;

	public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}
	
	protected IKkImConfigService kkImConfigService;

	public void setKkImConfigService(IKkImConfigService kkImConfigService) {
		this.kkImConfigService = kkImConfigService;
	}

	/**
	 * 根据personId和头像大小获取头像地址
	 * 
	 * @param personId
	 *            组织架构中的fdId
	 * @param size
	 *            头像大小
	 * @return 头像地址
	 */
	@Override
    public String getHeadimage(String personId, String size) {
		if("b".equals(size)) {
			size = "120";
		}else if("m".equals(size)) {
			size = "58";
		}else if("s".equals(size)) {
			size = "30";
		}
		try {
			String  headImageUrl =  getImageUrlByPersonId(personId, size);
			if (StringUtil.isNotNull(headImageUrl) && !headImageUrl.startsWith("http://")) {
				return "http://" + headImageUrl;
			}
			return headImageUrl;
		} catch (Exception e) {
			logger.error("获取用户头像出错：", e);
		}
		return null;
	}

	/**
	 * 根据personId获取头像地址，默认为58*58
	 * 
	 * @param personId
	 *            组织架构中的fdId
	 * @return 头像地址
	 */
	@Override
    public String getHeadimage(String personId) {
		return getHeadimage(personId, "58");
	}
	
	
	private String getLoginName(String personId) throws Exception {
		SysOrgPerson person  = (SysOrgPerson) sysOrgPersonService
				.findByPrimaryKey(personId);
		if (person != null) {
			return person.getFdLoginName();
		} else  {
			return null;
		}
	}

	private String getImageUrlByPersonId(String personId, String size) throws Exception {
		return getImageUrlByLoginName(getLoginName(personId), size);
	}

	private String getImageUrlByLoginName(String loginName, String size) throws Exception {
		
		return  kkImConfigService.getValuebyKey(KeyConstants.KK_INNER_DOMAIN) + GET_USER_FACE_URL + "?username=" + loginName
				+ "&width=" + size + "&height=" + size;
	}

	@Override
	public String getHeadimageChangeUrl() {
		return "/sys/person/setting.do?setting=sys_organization_chg_my_headimg_kk";
	}
}