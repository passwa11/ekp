package com.landray.kmss.third.pda.actions;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.third.pda.forms.PdaRowsPerPageConfigForm;
import com.landray.kmss.third.pda.model.PdaRowsPerPageConfig;
import com.landray.kmss.third.pda.model.PdaVersionConfig;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 创建日期 2011-03-14
 * 
 */
public class PdaRowsPerPageConfigAction extends BaseAction {

	/**
	 * 每页显示行数设置页面。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回edit页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			PdaRowsPerPageConfigForm configForm = (PdaRowsPerPageConfigForm) form;
			PdaRowsPerPageConfig config = new PdaRowsPerPageConfig();
			if (StringUtil.isNull(config.getFdRowsNumber())) {
                configForm.setFdRowsNumber("10");
            } else {
                configForm.setFdRowsNumber(config.getFdRowsNumber());
            }
			String[] extendUrl = config.getFdExtendsUrl();
			StringBuffer sbStr = new StringBuffer();
			if (extendUrl == null) {
                configForm.setFdExtendsUrl(null);
            } else {
				for (int i = 0; i < extendUrl.length; i++) {
					sbStr.append((i == 0 ? "" : "\r\n") + extendUrl[i]);
				}
				configForm.setFdExtendsUrl(sbStr.toString());
			}
			configForm.setFdAttDownLoadEnabled(config.getFdAttDownload());
			String msgRevTimeInterval = config.getMsgRevTimeInterval();
			if (StringUtil.isNotNull(msgRevTimeInterval)) {
				configForm.setMsgRevTimeInterval(msgRevTimeInterval);
			}
			configForm.setBroadcastAndriod(config.getBroadcastAndriod());
			configForm.setApiKeyAndriod(config.getApiKeyAndriod());
			configForm.setUriAndriod(config.getUriAndriod());
			configForm.setPushMsgServerUrlAndriod(config.getPushMsgServerUrlAndriod());
			configForm.setPushMsgServerIpAndriod(config.getPushMsgServerIpAndriod());
			configForm.setPushMsgServerPortAndriod(config.getPushMsgServerPortAndriod());
			if (UserOperHelper.allowLogOper("sysAppConfigEdit", null)) {
				UserOperHelper.setModelNameAndModelDesc(
						PdaRowsPerPageConfig.class.getName(),
						"移动端列表页面每页显示行数配置");
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("edit");
		}
	}

	/**
	 * 保存每页显示行数。<br>
	 * 该操作一般以HTTP的POST方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回success页面，否则返回manage页面
	 * @throws Exception
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			PdaRowsPerPageConfigForm configForm = (PdaRowsPerPageConfigForm) form;
			PdaRowsPerPageConfig config = new PdaRowsPerPageConfig();
			config.setFdRowsNumber(configForm.getFdRowsNumber());
			String extendStr = configForm.getFdExtendsUrl();
			if (StringUtil.isNotNull(extendStr)) {
				config.setFdExtendsUrl(null);
				String[] extendUrls = extendStr.split("\\r\\n");
				config.setFdExtendsUrl(extendUrls);
			} else {
                config.setFdExtendsUrl(null);
            }
			config.setFdAttDownload(configForm.isFdAttDownLoadEnabled());
			config.setMsgRevTimeInterval(configForm.getMsgRevTimeInterval());
			config.setBroadcastAndriod(configForm.getBroadcastAndriod());
			config.setApiKeyAndriod(configForm.getApiKeyAndriod());
			config.setUriAndriod(configForm.getUriAndriod());
			config.setPushMsgServerUrlAndriod(configForm.getPushMsgServerUrlAndriod());
			config.setPushMsgServerIpAndriod(configForm.getPushMsgServerIpAndriod());
			config.setPushMsgServerPortAndriod(configForm.getPushMsgServerPortAndriod());
			config.save();
			// 更新版本号
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			PdaVersionConfig version = new PdaVersionConfig();
			version.setMenuVersion(df.format(new Date()));
			version.save();
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return mapping.findForward("edit");
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return mapping.findForward("success");
		}
	}
}
