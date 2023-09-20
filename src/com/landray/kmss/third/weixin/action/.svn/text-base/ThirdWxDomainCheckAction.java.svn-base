package com.landray.kmss.third.weixin.action;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.third.weixin.forms.ThirdWxDomainCheckForm;
import com.landray.kmss.third.weixin.service.IThirdWeixinDomainCheckService;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;


public class ThirdWxDomainCheckAction extends BaseAction {

	private IThirdWeixinDomainCheckService thirdWeixinDomainCheckService;

	public ActionForward upload(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InputStream inputStream = null;
		try {
			ThirdWxDomainCheckForm xform = (ThirdWxDomainCheckForm) form;
			byte[] bytes = new byte[0];
			inputStream = xform.getFile().getInputStream();
			bytes = new byte[inputStream.available()];
			//inputStream.read(bytes);
			int count = 0;
			while ((count = inputStream.read(bytes)) > 0) {

			}
			String content = new String(bytes);
			if (StringUtil.isNull(content)) {
				throw new Exception("文件内容不能为空");
			}
			getThirdWeixinDomainCheckService().addOrUpdateFileContent(
					xform.getFile().getFileName(), content);

			request.setAttribute("errorMessage", ResourceUtil
					.getString("ui.help.luiext.upload.success", "sys-ui"));
		} catch (Exception e) {
			request.setAttribute("errorMessage", e.getMessage());
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
		return mapping.findForward("upload");
	}

	public IThirdWeixinDomainCheckService getThirdWeixinDomainCheckService() {
		if (thirdWeixinDomainCheckService == null) {
			thirdWeixinDomainCheckService = (IThirdWeixinDomainCheckService) SpringBeanUtil
					.getBean("thirdWeixinDomainCheckService");
		}
		return thirdWeixinDomainCheckService;
	}

}
