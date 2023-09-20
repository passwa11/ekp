package com.landray.kmss.sys.attachment.ocx;

import com.landray.kmss.sys.attachment.util.SysAttConfigUtil;
import com.landray.kmss.sys.attachment.util.SysAttConstant;
import com.landray.kmss.sys.profile.util.ProfileMenuUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 通过扩展点方式，根据业务模块单独配置的控件类型
 * 附件机制使用相应的控件显示
 * @author czk2019
 *
 */
public class SysAttOcxUtil {

	/**
	 * 默认，根据系统设定
	 */
	public static final String OCX_DEFAULT = "01";
	
	/**
	 * 金格
	 */
	public static final String OCX_JG = "02";
	
	/**
	 * wps私有云
	 */
	public static final String OCX_WPS_CLOUD = "03";
	
	/**
	 * 软航
	 */
	public static final String OCX_NTKO = "04";
	
	/**
	 * wps webOffice
	 */
	public static final String OCX_WPS_WEB_OFFICE = "05";
	
	/**
	 * wps 加载项，OA助手
	 */
	public static final String OCX_WPS_OA_ASSIST_VALUE = "06";

	/**
	 * wps文档中台
	 */
	public static final String OCX_WPS_CENTER = "07";
	
	/**
	 * 查看页
	 */
	public static final String VIEW = "view";
	
	/**
	 * 编辑页
	 */
	public static final String EDIT = "edit";
	
	/**
	 * 根据业务模块所选的控件跳转到对应的控件页
	 * @param fdKey			附件key
	 * @param fdModelName	附件模型名称
	 * @param pageMode		页面模式 view/edit
	 * @return
	 */
	public static String getOcxPageForward(String fdKey,String fdModelName,String pageMode) {
		String forward = "";
		String forwardPrefix = pageMode + "online_";
		String ocxName = SysAttOcxPluginUtil.getConfigOcxName(fdKey, fdModelName);
		if (StringUtil.isNotNull(ocxName)) {
			if (OCX_DEFAULT.equals(ocxName)) {
				try {
					//获取附件机制配置的编辑器类型
					String onlineToolType = SysAttConfigUtil.getOnlineToolType();
					if (SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_JG
							.equals(onlineToolType)) {
						ocxName = OCX_JG;
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

			}

			//附件机制暂时使用这几种控件
			if (OCX_JG.equals(ocxName)) {
				if (SysAttOcxUtil.EDIT.equals(pageMode)) {
					//判断是否加载了条款模块
					boolean hasClause = ProfileMenuUtil.moduleExist("/km/clause");
					if (hasClause) {
						forward = forwardPrefix + "doc_jg";
					} else {
						forward = forwardPrefix + "jg";
					}
				} else {
					forward = forwardPrefix + "jg";
				}
			} else if (OCX_WPS_CLOUD.equals(ocxName)) {
				forward = forwardPrefix + "wps_cloud";
			} else if (OCX_WPS_WEB_OFFICE.equals(ocxName)) {
				forward = forwardPrefix + "wps";
			} else if (OCX_WPS_CENTER.equals(ocxName)) {
				forward = forwardPrefix + "wps_center";
			}
		}

		return forward;
	}
	
	/**
	 * 获取附件使用的控件类型
	 * @param fdKey
	 * @param fdModelName
	 * @return
	 */
	public static String getOcxType(String fdKey,String fdModelName) {
		return SysAttOcxPluginUtil.getConfigOcxName(fdKey, fdModelName);
	}

	/**
	 * 获取业务模块后台所选的控件类型
	 * @param fdKey			附件key
	 * @param fdModelName	附件模型名称
	 * @return
	 */
	public static String getDocOcxName(String fdKey,String fdModelName) {
		String ocxName = SysAttOcxPluginUtil.getConfigOcxName(fdKey, fdModelName);
		return ocxName;
	}
	
}
