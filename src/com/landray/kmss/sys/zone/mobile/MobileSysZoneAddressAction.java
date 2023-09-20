package com.landray.kmss.sys.zone.mobile;

import com.landray.kmss.sys.organization.mobile.IMobileAddressService;
import com.landray.kmss.sys.organization.mobile.MobileAddressAction;
import com.landray.kmss.util.SpringBeanUtil;

public class MobileSysZoneAddressAction 
			extends MobileAddressAction {
	
	@Override
	public IMobileAddressService getMobileAddressService() {
		if (mobileAddressService == null) {
            mobileAddressService = (IMobileAddressService) SpringBeanUtil
                    .getBean("mobileSysZoneAddressService");
        }
		return mobileAddressService;
	}
}
