package com.landray.kmss.km.comminfo.service.spring;

import java.util.Date;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.km.comminfo.model.KmComminfoAltInfo;
import com.landray.kmss.km.comminfo.model.KmComminfoMain;
import com.landray.kmss.km.comminfo.service.IKmComminfoAltInfoService;
import com.landray.kmss.util.UserUtil;

/**
 * 创建日期 2010-五月-10
 * 
 * @author 徐乃瑞 资料修改信息业务接口实现
 */
public class KmComminfoAltInfoServiceImp extends BaseServiceImp implements
		IKmComminfoAltInfoService {

	public void setKmComminfoAltInfoService(
			IKmComminfoAltInfoService kmComminfoAltInfoService) {
	}

	/**
	 * 保存修改信息
	 */
	@Override
    public String addComminfoAltInfo(KmComminfoMain kmComminfoMain)
			throws Exception {
		KmComminfoAltInfo kmComminfoAltInfo = new KmComminfoAltInfo();
		kmComminfoAltInfo.setDocAlteror(UserUtil.getUser());
		kmComminfoAltInfo.setDocAlterTime(new Date());
		kmComminfoAltInfo.setComminfoMain(kmComminfoMain);
		return super.add(kmComminfoAltInfo);
	}
}
