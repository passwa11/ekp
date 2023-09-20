package com.landray.kmss.sys.zone.transfer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Session;

import com.landray.kmss.sys.admin.transfer.constant.ISysAdminTransferConstant;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.sys.attachment.util.ImageCropUtil;
import com.landray.kmss.sys.zone.constant.SysZoneConstant;
import com.landray.kmss.sys.zone.service.ISysZonePersonInfoService;
import com.landray.kmss.util.SpringBeanUtil;

public class SysZoneTransferPicTask implements ISysAdminTransferTask {
	
	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
	
	private ISysZonePersonInfoService sysZonePersonInfoService = null;
	
	public ISysZonePersonInfoService getSysZonePersonInfoService() {
		if (sysZonePersonInfoService == null) {
			sysZonePersonInfoService = (ISysZonePersonInfoService) SpringBeanUtil.getBean("sysZonePersonInfoService");
		}
		return sysZonePersonInfoService;
	}

	@Override
	public SysAdminTransferResult run(SysAdminTransferContext sysAdminTransferContext) {
		String hql = "UPDATE com.landray.kmss.sys.attachment.model.SysAttMain sysAttMain SET sysAttMain.fdKey =:fdKey1 WHERE sysAttMain.fdKey =:fdKey2 AND "
				+ "sysAttMain.fdModelName = 'com.landray.kmss.sys.zone.model.SysZonePersonInfo'";
		try {
			Session session = getSysZonePersonInfoService().getBaseDao().getHibernateSession();
			session.createQuery(hql).setParameter("fdKey1", SysZoneConstant.PHOTO_SRC_KEY + ImageCropUtil.CROP_KEYS[0])
					.setParameter("fdKey2", SysZoneConstant.BIG_PHOTO_KEY).executeUpdate();
			session.createQuery(hql).setParameter("fdKey1", SysZoneConstant.PHOTO_SRC_KEY + ImageCropUtil.CROP_KEYS[1])
					.setParameter("fdKey2", SysZoneConstant.MEDIUM_PHOTO_KEY).executeUpdate();
			session.createQuery(hql).setParameter("fdKey1", SysZoneConstant.PHOTO_SRC_KEY + ImageCropUtil.CROP_KEYS[2])
					.setParameter("fdKey2", SysZoneConstant.SMALL_PHOTO_KEY).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
			return new SysAdminTransferResult(ISysAdminTransferConstant.TASK_RESULT_ERROR, "");
		}
		return SysAdminTransferResult.OK;
	}

}
