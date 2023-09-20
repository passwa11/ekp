package com.landray.kmss.third.im.kk.service.spring;

import java.net.URLEncoder;

import com.landray.kmss.third.im.kk.constant.KeyConstants;
import com.landray.kmss.third.im.kk.constant.KkQrcodeConstants;
import com.landray.kmss.third.im.kk.service.IKkImConfigService;
import com.landray.kmss.third.im.kk.service.IKkUserService;
import com.landray.kmss.third.im.kk.util.AES;
import com.landray.kmss.util.ResourceUtil;

import net.sf.json.JSONObject;

/**
 * <P>kk二维码登录</P>
 * @author 孙佳
 */
public class KkUserServiceImp implements IKkUserService {

	protected IKkImConfigService kkImConfigService;

	public void setKkImConfigService(IKkImConfigService kkImConfigService) {
		this.kkImConfigService = kkImConfigService;
	}


	@Override
    public JSONObject getSign() {
		JSONObject json = new JSONObject();

		try {
			//签名
			String secretkey = kkImConfigService.getValuebyKey(KeyConstants.EKP_SECRETKEY);
			String sign = AES.encrypt2str(String.valueOf(System.currentTimeMillis() / 1000), secretkey);

			String pre = getNotifyUrlPrefix();

			json.put("sign", sign);
			json.put("redirectUrl", URLEncoder.encode(pre + KkQrcodeConstants.EKP_USER_LOGIN));
			json.put("outerDomain", kkImConfigService.getValuebyKey(KeyConstants.KK_OUTER_DOMAIN));
			json.put("lang", "zh-CN");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return json;
	}
	
	public String getNotifyUrlPrefix() {
		return ResourceUtil.getKmssConfigString("kmss.urlPrefix");
	}


}
