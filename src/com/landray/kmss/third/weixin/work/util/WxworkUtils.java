package com.landray.kmss.third.weixin.work.util;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.weixin.work.api.CorpGroupAppShareInfo;
import com.landray.kmss.third.weixin.work.api.WxworkApiService;
import com.landray.kmss.third.weixin.work.api.WxworkApiServiceImpl;
import com.landray.kmss.third.weixin.work.constant.WxworkConstant;
import com.landray.kmss.third.weixin.work.model.WeixinWorkConfig;
import com.landray.kmss.third.weixin.work.service.IThirdWeixinCgUserMappService;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 企业微信工具类
 * 
 * @author 唐有炜
 *
 */
public class WxworkUtils {

	private static final Logger logger = LoggerFactory
			.getLogger(WxworkUtils.class);

	private static WxworkApiService wxworkApiService;

	static {
		wxworkApiService = new WxworkApiServiceImpl();
	}

	public static WxworkApiService getWxworkApiService() {
		return wxworkApiService;
	}

	public static String getWxworkApiUrl() {
		String apiUrl = WxworkConstant.WXWORK_PREFIX;
		String prefix_url = WeixinWorkConfig.newInstance().getWxApiUrl();
		if (StringUtil.isNotNull(prefix_url)) {
			// logger.debug("云端企业微信的接口地址取自私有化地址："+prefix_url);
			apiUrl = prefix_url;
		}
		return apiUrl;
	}

	public static String getWxworkDomain() {
		String domainName = WeixinWorkConfig.newInstance().getWxDomain();
		if(StringUtil.isNull(domainName)) {
            domainName = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
        }
		if(domainName.endsWith("/")) {
            domainName = domainName.substring(0, domainName.length()-1);
        }
		return domainName;
	}

	public static List<String> getWxUserIds(String corpId, List<String> ekpIds) throws Exception {
		IThirdWeixinCgUserMappService thirdWeixinCgUserMappService = (IThirdWeixinCgUserMappService) SpringBeanUtil.getBean("thirdWeixinCgUserMappService");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdUserId");
		hqlInfo.setWhereBlock("fdCorpId=:corpId and "+ HQLUtil.buildLogicIN("fdEkpId", ekpIds));
		hqlInfo.setParameter("corpId",corpId);
		List<String> fdUserIds = thirdWeixinCgUserMappService
				.findValue(hqlInfo);
		return fdUserIds;
	}

	public static Map<String,List<String>> getCorpgroupUserIdsMap(List<String> ekpIds) throws Exception {
		String corpGroupIntegrateEnable = WeixinWorkConfig.newInstance().getCorpGroupIntegrateEnable();
		if(!"true".equals(corpGroupIntegrateEnable)){
			return null;
		}
		Map<String, CorpGroupAppShareInfo> appShareInfoMap = wxworkApiService.getAppShareInfoMap();
		if(appShareInfoMap==null || appShareInfoMap.isEmpty()){
			logger.warn("获取应用共享信息失败，请检查上下游配置以及后台日志");
			return null;
		}
		Map<String,List<String>> corpgroupUserIdsMap = new HashMap<>();
		for(String corpId:appShareInfoMap.keySet()){
			List<String> fdUserIds = getWxUserIds(corpId,ekpIds);
			if (fdUserIds == null || fdUserIds.size() == 0) {
				logger.debug("通过EKP的fdId查找中间映射表发现找不到对应的微信人员("+ekpIds+")，所属组织："+corpId+"，请先维护中间映射表数据");
				continue;
			}
			corpgroupUserIdsMap.put(corpId,fdUserIds);
		}
		return corpgroupUserIdsMap;
	}

	/**
	 * 截取字符串长度，包括处理中文
	 * @param str
	 * @param limit
	 * @return
	 */
	public static String getString(String str, int limit) {
		if (StringUtil.isNull(str)) {
			return str;
		}
		String regEx = "[\u0391-\uFFE5]";
		// 判断是否存在中文字符
		if (str.getBytes().length == str.length()) {
			if(str.length() <  limit){
				return str;
			}
			return str.substring(0, limit);
		} else {
			int length = 0;
			StringBuilder newStr = new StringBuilder();
			for (int i = 0; i < str.length(); i++) {
				String c = str.substring(i, i + 1);
				if (c.matches(regEx)) {
					length += 2;
				} else {
					length += 1;
				}
				if(length < limit){
					newStr.append(c);
				}
				else{
					logger.warn("原字符[" + str + "]长度已经超过" + limit + "长度限制!截取后："+ newStr);
					return newStr.toString();
				}
			}
			return str;
		}
	}
}
