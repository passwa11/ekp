package com.landray.kmss.sys.zone.service.plugin;

import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.attachment.util.ImageCropUtil;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.person.interfaces.PersonImageService;
import com.landray.kmss.sys.zone.constant.SysZoneConstant;
import com.landray.kmss.sys.zone.model.SysZonePersonInfo;
import com.landray.kmss.util.ArrayUtil;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;

import java.io.File;
import java.util.List;

public class SysZonePersonImageBean implements PersonImageService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysZonePersonImageBean.class);

	/**
	 * 新头像文件是否存在
	 */
	private Boolean imgFileExists;

	private ISysAttMainCoreInnerService sysAttMainCoreInnerService;

	public void setSysAttMainCoreInnerService(
			ISysAttMainCoreInnerService sysAttMainCoreInnerService) {
		this.sysAttMainCoreInnerService = sysAttMainCoreInnerService;
	}

	@Override
    public String getHeadimage(String personId) {
		return getHeadimage(personId, null);
	}

	@Override
    public String getHeadimage(String personId, String size) {
		if (imgFileExists == null) {
			// 这里不需要每次请求都读取文件，只需要读取一次就行
			File imgFile = new File(ConfigLocationsUtil.getWebContentPath() + "/sys/person/image.jsp");
			imgFileExists = imgFile.exists();
		}
		if (BooleanUtils.isTrue(imgFileExists)) {//版本兼容处理，避免模块关联
			Long s_time = null;
			try {
				List list = this.getAttByKey(personId, size);
				if (!ArrayUtil.isEmpty(list)) {
					SysAttMain attMain = (SysAttMain) list.get(0);
					s_time = attMain.getDocCreateTime().getTime();
				}
			} catch (Exception e) {
				logger.error("获取用户头像出错：", e);
			}
			return "/sys/person/image.jsp?personId=" + personId + "&size="
					+ size + (s_time != null ? "&s_time=" + s_time : "");
		} else {
			return getHeadUrl(personId, size);
		}
	}

	private String getHeadUrl(String personId, String size) {
		try {
			List list = this.getAttByKey(personId, size);
			if (ArrayUtil.isEmpty(list)) {
				return null;
			}
			SysAttMain attMain = (SysAttMain) list.get(0);
			return "/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId="
					+ attMain.getFdId();
		} catch (Exception e) {
			logger.error("获取用户头像出错：", e);
		}
		return null;
	}

	@Override
    public String getHeadimageChangeUrl() {
		return "/sys/person/setting.do?setting=sys_zone_person_photo";
	}
	
	private List getAttByKey(String personId, String size) throws Exception {
		String fdKey = SysZoneConstant.PHOTO_SRC_KEY
				+ ImageCropUtil.CROP_KEYS[0];
		if (SysZoneConstant.MEDIUM_PHOTO.equals(size)) {
			fdKey = SysZoneConstant.PHOTO_SRC_KEY
					+ ImageCropUtil.CROP_KEYS[1];
		} else if (SysZoneConstant.SMALL_PHOTO.equals(size)) {
			fdKey = SysZoneConstant.PHOTO_SRC_KEY
					+ ImageCropUtil.CROP_KEYS[2];
		}
		String modelName = SysZonePersonInfo.class.getName();
		List list = sysAttMainCoreInnerService.findByModelKey(modelName,
				personId, fdKey);

		// 旧的key值
		if (ArrayUtil.isEmpty(list)) {
			list = sysAttMainCoreInnerService.findByModelKey(modelName,
					personId, "zonePersonInfo");
		}
		return list;
	}
	
}
