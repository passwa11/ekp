package com.landray.kmss.third.im.kk.service;

import com.landray.kmss.common.service.IBaseService;

import net.sf.json.JSONObject;
/**
 * 文档类业务对象接口
 * 
 * @author 
 * @version 1.0 2017-08-16
 */
public interface IKkImConfigService extends IBaseService {

	/**
	 * <p>根据key修改value</p>
	 * @author 孙佳
	 */
	public void updateValueBykey(String key, String value);

	/**
	 * 通过key查询value
	 */
	public String getValuebyKey(String key);

	/**
	 * <p>删除所有记录</p>
	 * @author 孙佳
	 */
	public void deleteAll();

	/**
	 * 判断kk集成是否开启
	 * 
	 * @param key
	 * @return
	 */
	public boolean isEnableKKConfig();
	
	

	/**
	 * 判断kk新版扫码是否开启
	 * 
	 * @param key
	 * @return
	 */
	public boolean isChangeKKqrcodeEnabled();



	/**
	 * 获取kk群应用首页是否开启
	 * 
	 * @return
	 */
	public JSONObject getKKGroupAppConfig();

	/**
	 * 获取业务应用appcode
	 * 
	 * @param urlPrefix
	 * @return
	 */
	public String getAppCode(String urlPrefix);
}
