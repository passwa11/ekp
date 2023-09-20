package com.landray.kmss.sys.evaluation.actions;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.sys.evaluation.forms.SysEvaluationNotesConfigForm;
import com.landray.kmss.sys.evaluation.model.SysEvaluationNotesConfig;
import com.landray.kmss.sys.evaluation.util.SensitiveWordCheckUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.sso.client.oracle.StringUtil;

import net.sf.json.JSONObject;

/**
 * 点评机制的基础参数配置
 * @author 符伟彬
 *
 */

public class SysEvaluationNotesConfigAction extends BaseAction{
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			SysEvaluationNotesConfigForm configForm = (SysEvaluationNotesConfigForm) form;
			SysEvaluationNotesConfig forumConfig = new SysEvaluationNotesConfig();
			configForm.setFdEnable(forumConfig.getFdEnable());
//			configForm.setIsWordCheck(forumConfig.getIsWordCheck());
//			configForm.setWords(forumConfig.getWords());
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

	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			SysEvaluationNotesConfigForm configForm = (SysEvaluationNotesConfigForm) form;
			SysEvaluationNotesConfig forumConfig = new SysEvaluationNotesConfig();
			forumConfig.setFdEnable(configForm.getFdEnable());
//			forumConfig.setIsWordCheck(configForm.getIsWordCheck());
//			forumConfig.setWords(configForm.getWords());
//			//保存后设置重新获取敏感词
//			if("true".equals(configForm.getIsWordCheck())&&StringUtil.isNotNull(configForm.getWords())){
//				forumConfig.setIsNeedAcquire("true");
//			}
			forumConfig.save();
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
	
	/**
	 * 判断内容是否含有敏感词
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getIsHasSensitiveword(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		String isCheckWord = new SysEvaluationNotesConfig().getIsWordCheck();
		if(!("true".equals(isCheckWord))){
			json.put("flag", false);
			out.print(json.toString());
			return null;
	    }
		String content = URLDecoder.decode(request.getParameter("content"),
				"UTF-8");
		String keysString = new SysEvaluationNotesConfig().getWords();
		// 重新读取敏感词库
		if ("true".equals(new SysEvaluationNotesConfig().getIsNeedAcquire())) {
			SensitiveWordCheckUtil.isNeedAcquire = "true";
		}
		boolean isHas = false;
		Set<String> senWords = null;
		if(StringUtil.isNotNull(content)) {
			senWords = SensitiveWordCheckUtil.getSensitiveWord(
					content,
					keysString.split("\\s*[;；]\\s*"));
			if (senWords.size() > 0) {
				isHas = true;
			}
		}
		// 还原
		if ("true".equals(SensitiveWordCheckUtil.isNeedAcquire)) {
			SysEvaluationNotesConfig config = new SysEvaluationNotesConfig();
			config.setIsNeedAcquire("false");
			config.save();
			SensitiveWordCheckUtil.isNeedAcquire = "false";
		}
		SensitiveWordCheckUtil.isNeedAcquire = "true";
		if (isHas) {
			json.put("flag", true);
			json.put("senWords", senWords);
			out.print(json.toString());
		} else {
			json.put("flag", false);
			out.print(json.toString());
		}
		return null;
	}
}
