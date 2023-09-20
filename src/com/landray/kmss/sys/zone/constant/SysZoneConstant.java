package com.landray.kmss.sys.zone.constant;

import com.landray.kmss.sys.zone.model.SysZonePersonInfo;

public interface SysZoneConstant {
	/**
	 * own bundle
	 */
	String OWN_BUNDLE = "sys-zone";
	/**
	 * fdModelName
	 */
	String MODEL_NAME = SysZonePersonInfo.class.getName();
	/**
	 * 头像接口大图头像标识
	 */
	String BIG_PHOTO = "b";
	/**
	 * 头像接口中图头像标识
	 */
	String MEDIUM_PHOTO = "m";
	/**
	 * 头像接口小图头像标识
	 */
	String SMALL_PHOTO = "s";
	
	String PHOTO_SRC_KEY = "personPic";
	/**
	 * 大图头像fdKey
	 */
	String BIG_PHOTO_KEY = "personPic_b";
	/**
	 * 中图头像fdKey
	 */
	String MEDIUM_PHOTO_KEY = "personPic_m";
	/**
	 * 小图头像fdKey
	 */
	String SMALL_PHOTO_KEY = "personPic_s";
	/**
	 * 简历的fdkey
	 */
	String RESUME_KEY = "personResume";
	/**
	 * 二维码的fdkey
	 */
	String CODE_KEY = "personCode";
	/**
	 * 关注取消关注标识
	 */
	String TOCARED = "toCared";
	/**
	 * 关注标识
	 */
	String CARED = "cared";
	
	/**
	 * 标签相似，单页显示人员的个数
	 */
	Integer TAGS_PERSON_SIZE = 5;
	/**
	 * 通用编码
	 */
	String COMMON_ENCODING = "UTF-8";
	

	
	/**
	 * 与他人的关系：互不关注
	 */
	Integer ZONE_RELA_NO = 0;
	
	/**
	 * 与他人的关系：只关注
	 */
	Integer ZONE_RELA_ATT = 1;
	
	/**
	 * 与他人的关系：只被关注
	 */
	Integer ZONE_RELA_FAN  = 2;
	
	/**
	 * 与他人的关系：相互关注
	 */
	Integer ZONE_RELA_EACH_OTHER = 3;
	
	/**
	 * 访问记录：我看过谁
	 */
	Integer ZONE_VISITOR_ME = 0;
	
	/**
	 * 访问记录：谁看过我
	 */
	Integer ZONE_VISITOR_OTHER = 1;
	
	/**
	 * 照片墙默认头像 男
	 */
	String DEFAULT_PERSONIMG_M = "/sys/zone/resource/images/headM.jpg";
	
	/**
	 * 照片墙默认头像 女
	 */
	String DEFAULT_PERSONIMG_F = "/sys/zone/resource/images/headF.jpg";
}
