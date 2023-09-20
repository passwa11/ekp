package com.landray.kmss.third.weixin.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.third.weixin.model.ThirdWeixinDomainCheck;
import com.landray.kmss.third.weixin.service.IThirdWeixinDomainCheckService;
import com.landray.kmss.util.SpringBeanUtil;

public class ThirdWeixinDomainCheckServiceImp extends ExtendDataServiceImp implements IThirdWeixinDomainCheckService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;


    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
	public ThirdWeixinDomainCheck findByFileName(String fileName)
			throws Exception {
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("fdFileName=:fileName");
		info.setParameter("fileName", fileName);
		return (ThirdWeixinDomainCheck) findFirstOne(info);
	}

	@Override
	public void addOrUpdateFileContent(String fileName, String fileContent)
			throws Exception {
		ThirdWeixinDomainCheck check = findByFileName(fileName);
		if (check == null) {
			check = new ThirdWeixinDomainCheck();
			check.setFdFileName(fileName);
			check.setFdFileContent(fileContent);
			add(check);
		} else {
			check.setFdFileContent(fileContent);
			update(check);
		}

	}
}
