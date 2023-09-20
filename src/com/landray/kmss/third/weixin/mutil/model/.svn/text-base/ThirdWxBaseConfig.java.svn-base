package com.landray.kmss.third.weixin.mutil.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.weixin.mutil.service.IThirdWxWorkConfigService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

/**
 * <P>多企业微信配置</P>
 * @author 孙佳
 * 2019年1月2日
 */
public class ThirdWxBaseConfig {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdWxBaseConfig.class);

	private IThirdWxWorkConfigService thirdMutilWxWorkConfigService;

	public IThirdWxWorkConfigService getServiceImp() {
		if (thirdMutilWxWorkConfigService == null) {
			thirdMutilWxWorkConfigService = (IThirdWxWorkConfigService) SpringBeanUtil.getBean("thirdMutilWxWorkConfigService");
		}
		return thirdMutilWxWorkConfigService;
	}

	/**
	 * 所有企业微信集成配置
	 */
	private static Map<String, Map<String, String>> dataMap = new HashMap<String, Map<String, String>>();


	/**
	 * 当前操作的企业微信
	 */
	protected String key = null;

	public ThirdWxBaseConfig(String key) throws Exception {
		wxCofigCache(false);
		//如果key为空，则获取第一条配置信息
		if (StringUtil.isNull(key)) {
			for (String value : dataMap.keySet()) {
				if (StringUtil.isNull(value)) {
					this.key = value;
					break;
				}
			}
		}else{
			this.key = key;
		}
	}

	public ThirdWxBaseConfig() {

	}

	public static ThirdWxBaseConfig newInstance() {
		ThirdWxBaseConfig config = null;
		try {
			config = new ThirdWxBaseConfig();
		} catch (Exception e) {
			logger.error("", e);
		}
		return config;
	}


	protected String getValue(String name) {
		if (StringUtil.isNull(key)) {
			return null;
		}
		return dataMap.get(key).get(name);
	}

	protected void setValue(String name, String value) {
		dataMap.get(key).put(name, value);
	}

	public Map<String, Map<String, String>> getDataMap() {
		return dataMap;
	}

	public Set<String> getDataKey() {
		return dataMap.keySet();
	}

	public void save(String key, Map<String, String> map) throws Exception {
		getServiceImp().save(key, map);
		updateWxConfigCache();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * <p>更新缓存</p>
	 * @author 孙佳
	 */
	public void updateWxConfigCache(){
		try {
			wxCofigCache(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void wxCofigCache(boolean forceRefresh) throws Exception {
		boolean flag = false;
		if(null == dataMap || dataMap.isEmpty()){
			flag = true;
		}
		if (flag || forceRefresh) {
			Map<String, String> wxConfigMap = null;
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock(" thirdWxWorkMutilConfig.fdKey ");
			hqlInfo.setWhereBlock(" 1=1 group by fdKey ");
			List<String> list = getServiceImp().findList(hqlInfo);
			if (list == null || list.size() <= 0) {
				return;
			}
			dataMap.clear();
			for (String key : list) {
				hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock(" thirdWxWorkMutilConfig.fdKey =:key");
				hqlInfo.setParameter("key", key);
				List<ThirdWxWorkMutilConfig> listConfig = getServiceImp().findList(hqlInfo);
				wxConfigMap = new HashMap<String, String>();
				for (ThirdWxWorkMutilConfig form : listConfig) {
					wxConfigMap.put(form.getFdField(), form.getFdValue());
				}
				dataMap.put(key, wxConfigMap);
			}
		}
	}

}
