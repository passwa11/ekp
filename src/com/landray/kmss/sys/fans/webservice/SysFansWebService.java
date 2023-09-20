package com.landray.kmss.sys.fans.webservice;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.fans.constant.SysFansConstant;
import com.landray.kmss.sys.fans.service.ISysFansMainService;
import com.landray.kmss.sys.fans.webservice.exception.SysFansFaultException;
import com.landray.kmss.sys.fans.webservice.exception.SysFansFaultUtils;
import com.landray.kmss.sys.fans.webservice.exception.ValidationException;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.annotation.RestApi;

@Controller
@RestApi(docUrl = "/sys/fans/restservice/SysFansRestServiceHelp.jsp",
		name = "sysFansWebService",
		resourceKey = "sys-fans:SysFansRestService.title")
@RequestMapping(value = "/api/sys-fans/sysFansRestService",
		method = RequestMethod.POST)

public class SysFansWebService implements ISysFansWebService {
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(SysFansWebService.class);
	private static String fdModelName =
			"com.landray.kmss.sys.zone.model.SysZonePersonInfo";

	private ISysOrgPersonService sysOrgPersonService;

	public void
			setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	private ISysFansMainService sysFansMainService;

	public void setSysFansMainService(ISysFansMainService sysFansMainService) {
		this.sysFansMainService = sysFansMainService;
	}

	@SuppressWarnings("unchecked")
	private String getPersonId(String userId) throws SysFansFaultException {
		try {
			String personId = null;
			HQLInfo hqlInfo = new HQLInfo();
			String hql = " sysOrgPerson.fdLoginName =:fdLoginName";
			hqlInfo.setParameter("fdLoginName", userId);
			hqlInfo.setWhereBlock(hql);
			hqlInfo.setSelectBlock(" sysOrgPerson.fdId ");
			Object obj = sysOrgPersonService.findFirstOne(hqlInfo);
			if (obj != null) {
				personId = obj.toString();
				return personId;
			}
		} catch (ValidationException e) {
			logger.warn("SysFansWebService execute getPersonId occur wran:"
					+ e.getMessage());
			SysFansFaultUtils.throwValidationException(e.getErrorCode(),
					e.getMessage());
		} catch (Throwable ex) {
			logger.error("SysFansWebService execute getPersonId occur an error:"
					+ ex.getMessage(), ex);
			SysFansFaultUtils.throwApplicationError();
		}
		return null;
	}

	/**
	 * 关注某用户,返回关注结果
	 */
	@Override
    @ResponseBody
	@RequestMapping(value = "/followPerson")
	public boolean followPerson(@RequestParam("userId") String userId,
			@RequestParam("targetUserId") String targetUserId)
			throws SysFansFaultException {
		String originId = "";
		String targetId = "";
		try {
			originId = getPersonId(userId);
			targetId = getPersonId(targetUserId);
			if (StringUtil.isNull(originId) || StringUtil.isNull(targetId)) {
				logger.warn("用户不存在");
				throw new SysFansFaultException("用户不存在");
			}
			Boolean isFollowed = isFollowPerson(userId, targetUserId);
			// 如果已经关注了，直接返回
			if (isFollowed) {
				return true;
			}

			String rtnValue = sysFansMainService.addFansByIds(originId,
					targetId, fdModelName,
					SysFansConstant.RELATION_USER_TYPE_PERSON);
			if (StringUtil.isNotNull(rtnValue)) {
				return true;
			}
		} catch (SysFansFaultException ex) {
			if (StringUtil.isNull(originId)) {
				logger.warn("用户:" + userId + "不存在");
				throw new SysFansFaultException("用户:" + userId + "不存在");
			} else if (StringUtil.isNull(targetId)) {
				logger.warn("用户:" + targetUserId + "不存在");
				throw new SysFansFaultException("用户:" + targetUserId + "不存在");
			}
			if (StringUtil.isNotNull(userId)
					&& StringUtil.isNotNull(targetUserId)
					&& userId.equals(targetUserId)) {
				logger.warn("用户:" + userId + "不能关注自己");
				throw new SysFansFaultException("用户:" + userId + "不能关注自己");
			}
		} catch (ValidationException e) {
			logger.warn("SysFansWebService execute followPerson occur wran:"
					+ e.getMessage());
			SysFansFaultUtils.throwValidationException(e.getErrorCode(),
					e.getMessage());
		} catch (Throwable ex) {
			logger.error(
					"SysFansWebService execute followPerson occur an error:"
							+ ex.getMessage(),
					ex);
			SysFansFaultUtils.throwApplicationError();
		}
		return false;
	}

	/**
	 * 检查是否已关注某用户，已关注返回true
	 */

	@Override
    @ResponseBody
	@RequestMapping(value = "/isFollowPerson")
	public boolean isFollowPerson(@RequestParam("userId") String userId,
			@RequestParam("targetUserId") String targetUserId)
			throws SysFansFaultException {
		Boolean isFollow = false;
		String originId = "";
		String targetId = "";
		try {
			if (StringUtil.isNull(userId) || StringUtil.isNull(targetUserId)) {
				logger.warn("用户名" + userId + "或" + targetUserId + "不能为空");
				throw new SysFansFaultException(
						"用户名" + userId + "或" + targetUserId + "不能为空");
			}
			originId = getPersonId(userId);
			targetId = getPersonId(targetUserId);
			if (StringUtil.isNull(originId) || StringUtil.isNull(targetId)) {
				logger.warn("用户不存在");
				throw new SysFansFaultException("用户不存在");
			}

			isFollow = sysFansMainService.isFollowPerson(originId, targetId,
					fdModelName);

		} catch (SysFansFaultException e) {
			if (StringUtil.isNull(originId)) {
				logger.warn("用户:" + userId + "不存在");
				throw new SysFansFaultException("用户:" + userId + "不存在");
			} else if (StringUtil.isNull(targetId)) {
				logger.warn("用户:" + targetUserId + "不存在");
				throw new SysFansFaultException("用户:" + targetUserId + "不存在");
			}
		} catch (ValidationException e) {
			logger.warn("SysFansWebService execute isFollowPerson occur wran:"
					+ e.getMessage());
			SysFansFaultUtils.throwValidationException(e.getErrorCode(),
					e.getMessage());
		} catch (Throwable ex) {
			logger.error(
					"SysFansWebService execute isFollowPerson occur an error:"
							+ ex.getMessage(),
					ex);
			SysFansFaultUtils.throwApplicationError();
		}
		return isFollow;
	}

	// public static void main(String args[]) {
	//
	// DefaultRestClientBuilder builder = new DefaultRestClientBuilder();
	// builder.init();
	// IRestClient client = builder.buildRestClient();
	//
	// HttpHeaders headers = new HttpHeaders();
	// headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	//
	// MultiValueMap<String, String> map =
	// new LinkedMultiValueMap<String, String>();
	// map.add("userId", "admin");
	// map.add("targetUserId", "hongzq");
	//
	// HttpEntity<MultiValueMap<String, String>> request =
	// new HttpEntity<MultiValueMap<String, String>>(map, headers);
	//
	// // 关注某用户
	// ResponseEntity<Boolean> entity1 = client.postForEntity(
	// "http://127.0.0.1:9090/kms/api/sys-fans/sysFansRestService/followPerson",
	// Boolean.class, request);
	//
	// System.out.println(entity1.getBody());
	//
	// // 是否已关注某用户
	// ResponseEntity<Boolean> entity2 = client.postForEntity(
	// "http://127.0.0.1:9090/kms/api/sys-fans/sysFansRestService/isFollowPerson",
	// Boolean.class, request);
	//
	// System.out.println(entity2.getBody());
	//
	// }

}
