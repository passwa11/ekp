package com.landray.kmss.sys.ui.actions;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.cluster.interfaces.MessageCenter;
import com.landray.kmss.sys.ui.compressor.PcCompressService;
import com.landray.kmss.sys.ui.model.SysUiConfig;
import com.landray.kmss.sys.ui.service.spring.message.SysUiMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SysUiCompressAction extends BaseAction {
	/**
	 * 切换使用或关闭使用压缩文件的请求
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward useCompressResource(ActionMapping mapping, ActionForm form,
                                             HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-useCompressResource", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject rtnData = new JSONObject();
		try {
			//根据开关值切换资源
			String isUseCompressResource = request.getParameter("isUseCompressResource");
			boolean value = "true".equals(isUseCompressResource);
			String compress = ResourceUtil
					.getKmssConfigString("sys.ui.pc.compress.task.enabled");
			SysUiConfig sysUiConfig = new SysUiConfig();
			//更新配置
			sysUiConfig.setIsUseCompressResource(value ? "true" : "false");
			sysUiConfig.save();
			if ("true".equals(compress)) {
				PcCompressService.channelCompressRecource(value);
				//发送集群消息通知其他节点
				MessageCenter.getInstance().sendToOther(new SysUiMessage(value));
				//MessageCenter.getInstance().sendToAll(new SysUiMessage(value));
			} else {
				//不执行切换操作
			}
		} catch (Exception e) {
			messages.addError(e);
			log.error("error", e);
			rtnData.put("error", e.getMessage());
		}
		TimeCounter.logCurrentTime("Action-useCompressResource", false, getClass());
		if (messages.hasError()) {
			rtnData.put("success", false);
		} else {
			rtnData.put("success", true);
		}
		request.setAttribute("lui-source", rtnData);
		return mapping.findForward("lui-source");
	}

}
