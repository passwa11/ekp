package com.landray.kmss.sys.attachment.actions;

import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.attachment.forms.SysAttWaterMarkForm;
import com.landray.kmss.sys.attachment.io.IOUtil;
import com.landray.kmss.sys.attachment.util.SysAttViewerUtil;
import com.landray.kmss.util.ResourceUtil;

import net.sf.json.JSONObject;

public class SysAttWaterMarkAction extends ExtendAction {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttWaterMarkAction.class);

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		return null;
	}

	public ActionForward getWaterMarkPNG(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		InputStream in = null;
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			in = SysAttViewerUtil.getWaterMarkPNG(SysAttViewerUtil.getWaterMarkConfigInRequest(request, "get"));
			if (in != null) {
				IOUtil.write(in, out);
			}
			return null;
		} catch (Exception e) {
			streamClose(in, out);
			logger.info("error", e);
			return null;
		} finally {
			streamClose(in, out);
		}
	}

	private void streamClose(InputStream in, OutputStream out) {
		try {
			if (in != null) {
				in.close();
				in = null;
			}
			if (out != null) {
				out.close();
				out = null;
			}
		} catch (Exception e) {
			logger.debug("流关闭错误，错误信息", e);
		}
	}

	public ActionForward configWaterMark(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject waterMarkConfig = SysAttViewerUtil.getWaterMarkConfigInDB(false);
		JSONObject waterMarkInfos = SysAttViewerUtil.getWaterMarkInfos(waterMarkConfig, request);
		waterMarkConfig.accumulate("otherInfos", waterMarkInfos);
		request.setAttribute("waterMarkConfig", waterMarkConfig.toString());
		SysAttWaterMarkForm waterMarkForm = (SysAttWaterMarkForm) form;
		copyProperties(waterMarkForm, waterMarkConfig);
		request.setAttribute("sysAttWaterMarkForm", waterMarkForm);
		return mapping.findForward("waterMarkConfig");
	}

	private void copyProperties(SysAttWaterMarkForm waterMarkForm, final JSONObject waterMarkConfig) {
		if (waterMarkConfig.has("showWaterMark")) {
			waterMarkForm.setShowWaterMark(waterMarkConfig.getString("showWaterMark"));
		}
		if (waterMarkConfig.has("markOpacity")) {
			waterMarkForm.setMarkOpacity(waterMarkConfig.getString("markOpacity"));
		}
		if (waterMarkConfig.has("markType")) {
			waterMarkForm.setMarkType(waterMarkConfig.getString("markType"));
		}
		if (waterMarkConfig.has("markWordType")) {
			waterMarkForm.setMarkWordType(waterMarkConfig.getString("markWordType"));
		}
		if (waterMarkConfig.has("markWordVar")) {
			waterMarkForm.setMarkWordVar(waterMarkConfig.getString("markWordVar"));
		}
		if (waterMarkConfig.has("markWordFixed")) {
			waterMarkForm.setMarkWordFixed(waterMarkConfig.getString("markWordFixed"));
		}
		if (waterMarkConfig.has("markRowSpace")) {
			waterMarkForm.setMarkRowSpace(waterMarkConfig.getString("markRowSpace"));
		}
		if (waterMarkConfig.has("markColSpace")) {
			waterMarkForm.setMarkColSpace(waterMarkConfig.getString("markColSpace"));
		}
		if (waterMarkConfig.has("markWordFontFamily")) {
			waterMarkForm.setMarkWordFontFamily(waterMarkConfig.getString("markWordFontFamily"));
		}
		if (waterMarkConfig.has("markWordFontColor")) {
			waterMarkForm.setMarkWordFontColor(waterMarkConfig.getString("markWordFontColor"));
		}
		if (waterMarkConfig.has("markWordFontSize")) {
			waterMarkForm.setMarkWordFontSize(waterMarkConfig.getString("markWordFontSize"));
		}
		if (waterMarkConfig.has("markRotateType")) {
			waterMarkForm.setMarkRotateType(waterMarkConfig.getString("markRotateType"));
		}
		if (waterMarkConfig.has("markRotateAngel")) {
			waterMarkForm.setMarkRotateAngel(waterMarkConfig.getString("markRotateAngel"));
		}
		if (waterMarkConfig.has("markPicFileName")) {
			waterMarkForm.setMarkPicFileName(waterMarkConfig.getString("markPicFileName"));
		}
	}

	public ActionForward saveWaterMark(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SysAttWaterMarkForm waterMarkForm = (SysAttWaterMarkForm) form;
		SysAttViewerUtil.saveWaterMarkConfig(waterMarkForm);
		return configWaterMark(mapping, form, request, response);
	}

	public ActionForward setWaterMarkInfos(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			JSONObject rtnJson = SysAttViewerUtil.getWaterMarkConfigInRequest(request, "post");
			JSONObject waterMarkInfos = SysAttViewerUtil.getWaterMarkInfos(rtnJson, request);
			rtnJson.accumulate("otherInfos", waterMarkInfos);
			request.setAttribute("lui-source", rtnJson);
		} catch (Exception e) {
			request.setAttribute("lui-source",
					new JSONObject().element("msg", ResourceUtil.getString("return.optFailure")));
			return getActionForward("lui-failure", mapping, form, request, response);
		}

		return getActionForward("lui-source", mapping, form, request, response);
	}
}
