package com.landray.kmss.third.pda.constant;

public interface PdaModuleConfigConstant {

	/**
	 * 模块配置状态 启用
	 */
	public static final String PDA_MODULE_STATUS_ENABLE = "1";

	/**
	 * 模块配置状态 禁用
	 */
	public static final String PDA_MODULE_STATUS_DISABLE = "0";

	/**
	 * 菜单类型 模块
	 */
	public static final String PDA_MENUS_MODULE = "module";
	/**
	 * 菜单类型 列表标签页
	 */
	public static final String PDA_MENUS_LISTTAB = "listTab";
	/**
	 * 菜单类型 列表页
	 */
	public static final String PDA_MENUS_LIST = "list";
	/**
	 * 菜单类型 分类页
	 */
	public static final String PDA_MENUS_LISTCATEGORY = "listcategory";
	/**
	 * 菜单类型 文档页
	 */
	public static final String PDA_MENUS_DOC = "doc";

	/**
	 * 菜单类型 第三方应用
	 */
	public static final String PDA_MENUS_APP = "app";
	
	/**
	 * 菜单类型 集成系统模块
	 */
	public static final String PDA_MENUS_EKP = "ekp";

	/**
	 * 菜单类型 标签页
	 */
	public static final String PDA_MENUS_TABVIEW = "tabView";

	/**
	 * 菜单类型 搜索
	 */
	public static final String PDA_MENUS_SEARCH = "search";

	/**
	 * 应用类型，苹果应用、安卓应用
	 */
	public static final String PDA_APP_TYPE_APPLE = "0";

	public static final String PDA_APP_TYPE_ANDROID = "1";

	/**
	 * logo图片地址
	 */
	public static final String PDA_LOGO_URL = "/third/pda/resource/images/iphone/logo.png";

	/**
	 * 预加载图片地址
	 */
	public static final String PDA_LOADING_URL = "/third/pda/resource/images/iphone/loading.png";

	/**
	 * 背景图片地址
	 */
	public static final String PDA_BACK_URL = "/third/pda/resource/images/iphone/backgroud.png";

	/**
	 * banner图片地址
	 */
	public static final String PDA_BANNER_URL = "/third/pda/resource/images/iphone/banner.png";
	
	/**
	 * banner背景图片地址
	 */
	public static final String PDA_BANNER_BG_URL = "/third/pda/resource/images/iphone/banner_bg.png";

	/**
	 * iPad logo图片地址
	 */
	public static final String PDA_IPAD_LOGO_URL = "/third/pda/resource/images/ipad/logo.png";

	/**
	 * 预加载图片地址
	 */
	public static final String PDA_IPAD_LOADING_URL = "/third/pda/resource/images/ipad/loading.png";

	/**
	 * 背景图片地址
	 */
	public static final String PDA_IPAD_BACK_URL = "/third/pda/resource/images/ipad/backgroud.png";

	/**
	 * 菜单与图片版本信息URL
	 */
	public static final String PDA_CONFIG_VERSION_URL = "/third/pda/third_pda_config/thirdPdaConfig.do?method=configVersion";

	/**
	 * 菜单与图片版本信息URL
	 */
	public static final String PDA_MENU_DETAIL_URL = "/third/pda/third_pda_config/thirdPdaConfig.do?method=menuDetail";

	/**
	 * 详细菜单信息URL
	 */
	public static final String PDA_ICON_DETAIL_URL = "/third/pda/third_pda_config/thirdPdaConfig.do?method=iconDetail";

	/**
	 * 增加手机信息时，请求url
	 */
	public static final String PDA_MSG_PUSH_ADD_URL = "/third/pda/third_pda_config/pdaMsgPushMem.do?method=collectMsg";

	/**
	 * 修改手机是否接受信息时，请求url
	 */
	public static final String PDA_MSG_PUSH_MODIFY_URL = "/third/pda/third_pda_config/pdaMsgPushMem.do?method=changeMsg";

	/**
	 * ipad主页配置请求链接
	 */
	public static final String PDA_HOME_PAGE_CONFIG = "/third/pda/third_pda_config/thirdPdaConfig.do?method=homeDetail";

	/**
	 * 推送信息请求数据URL
	 */
	public static final String PDA_MSG_PUSH_DATA_URL = "/third/pda/third_pda_config/pdaMsgPushInfo.do?method=list";
	/**
	 * 新建页面时跳转URL
	 */
	public static final String PDA_CREATE_TAINSIT_URL = "/third/pda/pda_module_label_list/pdaModuleLabelList.do?method=tainsit&fdLabelId=";

	/**
	 * 全文检索
	 */
	public static final String FTSEARCH_URL = "/third/pda/pda_ftsearch/pdaFtsearch.do?method=custom";

	/**
	 * 筛选URL
	 */
	public static final String PROPERTYFILTER_URL = "/sys/property/sys_property_filter_pda/sysPropertyFilterPda.do?method=getPropertyFilter";

	/**
	 * 菜单类型为tabview请求数据URL
	 */
	public static final String PDA_TABVIEW_CONFIG_URL = "/third/pda/third_pda_config/thirdPdaConfig.do?method=tabViewDetail";
	
	/**
	 * 菜单类型为EKP请求数据URL
	 */
	public static final String PDA_EKP_Module_CONFIG_URL = "/third/pda/third_pda_config/thirdPdaConfig.do?method=moduleDetail";
}
