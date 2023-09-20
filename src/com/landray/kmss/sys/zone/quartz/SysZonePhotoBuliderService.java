package com.landray.kmss.sys.zone.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.zone.service.ISysZonePhotoMainService;
import com.landray.kmss.util.SpringBeanUtil;


public class SysZonePhotoBuliderService {
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	public void rebuildPhoto() {
		ISysZonePhotoMainService service 
				= (ISysZonePhotoMainService)SpringBeanUtil.getBean("sysZonePhotoMainService");
		if(service != null) {
			try {
				service.buildPhotoMap();
			}catch (Exception e) {
				logger.error(e.toString());
				e.printStackTrace();
			}
		}
	}
}
