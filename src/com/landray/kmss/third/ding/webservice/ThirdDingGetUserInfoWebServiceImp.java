package com.landray.kmss.third.ding.webservice;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.webservice.SysOrgWebserviceConstant;
import com.landray.kmss.third.ding.model.OmsRelationModel;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.annotation.RestApi;

@Controller
@RequestMapping(value = "/api/third-ding/thirdDingUserInfo", method = RequestMethod.POST)
@RestApi(docUrl = "/third/ding/rest/third_ding_rest_help.jsp", name = "thirdDingGetUserInfoWebService", resourceKey = "third-ding:thirdDingGetUserInfoWebService.title")
public class ThirdDingGetUserInfoWebServiceImp implements
		IThirdDingGetUserInfoWebService, SysOrgWebserviceConstant,
		SysOrgConstant {

	@Override
	@ResponseBody
	@RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
	public JSONObject getUserInfo(@RequestParam String userId) {
		JSONObject result = new JSONObject();
		result.put("state", 0);
		if (StringUtil.isNull(userId)) {
			result.put("errMsg", "userId不能为空");
			return result;
		}
		IOmsRelationService omsRelationService = (IOmsRelationService) SpringBeanUtil
				.getBean("omsRelationService");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdEkpId");
		hqlInfo.setWhereBlock("omsRelationModel.fdAppPkId = :fdAppPkId");
		hqlInfo.setParameter("fdAppPkId", userId);
		try {
			String ekpid = (String) omsRelationService
					.findFirstOne(hqlInfo);
			if (StringUtils.isNotBlank(ekpid)) {
				ISysOrgPersonService orgPersonService = (ISysOrgPersonService) SpringBeanUtil
						.getBean("sysOrgPersonService");
				SysOrgPerson person = (SysOrgPerson) orgPersonService
						.findByPrimaryKey(ekpid);
				result.put("state", 1);
				JSONObject data = new JSONObject();
				data.put("name", person.getFdName());
				data.put("loginName", person.getFdLoginName());
				data.put("mobile", person.getFdMobileNo());
				data.put("email", person.getFdEmail());
				result.put("data", data);
				return result;
			} else {
				result.put("errMsg", "根据userId获取不到对应的映射关系");
				return result;
			}
		} catch (Exception e) {
			result.put("errMsg", e.getMessage());
			return result;
		}
	}

}
