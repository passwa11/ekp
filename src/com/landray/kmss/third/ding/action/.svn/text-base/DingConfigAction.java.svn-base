package com.landray.kmss.third.ding.action;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.appconfig.actions.SysAppConfigAction;
import com.landray.kmss.sys.property.custom.DynamicAttributeConfig;
import com.landray.kmss.sys.property.custom.DynamicAttributeField;
import com.landray.kmss.sys.property.custom.DynamicAttributeUtil;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.ThirdDingTodoTemplate;
import com.landray.kmss.third.ding.service.IThirdDingTodoTemplateService;
import com.landray.kmss.third.ding.service.spring.SynDingDirService;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DingConfigAction extends SysAppConfigAction {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DingConfigAction.class);

	@Override
    public ActionForward update(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			logger.debug(
					"=====================钉钉集成配置保存========================");
			super.update(mapping, form, request, response);

			if ("true".equals(DingConfig.newInstance().getDingEnabled())) {
				try {
					// 新接口通用待办推送模版构建
					IThirdDingTodoTemplateService thirdDingTodoTemplateService = (IThirdDingTodoTemplateService) SpringBeanUtil
							.getBean("thirdDingTodoTemplateService");
					HQLInfo hqlInfo = new HQLInfo();
					hqlInfo.setWhereBlock(
							"thirdDingTodoTemplate.fdIsdefault=:fdIsdefault");
					hqlInfo.setParameter("fdIsdefault", "1");
					ThirdDingTodoTemplate thirdDingTodoTemplate = (ThirdDingTodoTemplate) thirdDingTodoTemplateService.findFirstOne(hqlInfo);
					if (thirdDingTodoTemplate != null) {
						if (StringUtil.isNull(
								thirdDingTodoTemplate.getFdModelName())) {
							thirdDingTodoTemplate.setFdModelName(
									"com.landray.kmss.sys.notify.model.SysNotifyTodo");
						}
					} else {
						logger.warn("创建钉钉待办推送默认模版！");
						thirdDingTodoTemplate = new ThirdDingTodoTemplate();
						thirdDingTodoTemplate.setFdName("通用待办模版（默认）");
						thirdDingTodoTemplate.setFdModelName(
								"com.landray.kmss.sys.notify.model.SysNotifyTodo");
						thirdDingTodoTemplate.setFdIscustom("0");
						thirdDingTodoTemplate.setFdIsdefault("1");
						thirdDingTodoTemplate.setDocCreateTime(new Date());
						thirdDingTodoTemplate.setDocAlterTime(new Date());
						thirdDingTodoTemplate.setDocCreator(UserUtil.getUser());
						String defLang = ResourceUtil
								.getKmssConfigString("kmss.lang.official");
						logger.debug("官方lang:" + defLang);
						if (StringUtil.isNull(defLang)) {
							defLang = "中文|zh-CN";
						}
						String defaultLangSort = defLang.split("\\|")[1];

						String subject = DingUtil.getValueByLang(
								"sysNotifyTodo.fdSubject", "sys-notify",
								defaultLangSort); // 主题
						String creator = DingUtil.getValueByLang(
								"sysNotifyCategory.fdCreatorName", "sys-notify",
								defaultLangSort); // 创建人
						String createTime = DingUtil.getValueByLang(
								"sysNotifyTodo.fdCreateTime", "sys-notify",
								defaultLangSort); // 创建时间
						String detail = "{'data':[{'key':'fdSubject','name':'"
								+ subject
								+ "','fromForm':'false','title':[{'lang':'"
								+ defLang + "','value':'" + subject
								+ "'}]},{'key':'docCreator.fdName','name':'"
								+ creator
								+ "','fromForm':'false','title':[{'lang':'"
								+ defLang + "','value':'" + creator
								+ "'}]},{'key':'fdCreateTime','name':'"
								+ createTime
								+ "','fromForm':'false','title':[{'lang':'"
								+ defLang + "','value':'" + createTime
								+ "'}]}]}";

						thirdDingTodoTemplate
								.setFdDetail(detail.replaceAll("\'", "\""));
						thirdDingTodoTemplateService.add(thirdDingTodoTemplate);
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("", e);
				}

				try {
					// 同步字段旧数据迁移（ding_config.jsp已经做了兼容，现在要把旧数据备份并置为空）
					DingConfig config = new DingConfig();
					Map<String, String> map = config.getDataMap();
					if (map != null) {

						String value = map.get("dingOmsCreateDeptGroup");
						if (StringUtil.isNotNull(value)) {
							map.put("temp.dingOmsCreateDeptGroup", value);
							map.put("dingOmsCreateDeptGroup", "");
						}
						//System.out.println("value1:" + value);
						value = map.get("dingWorkPhoneEnabled");
						if (StringUtil.isNotNull(value)) {
							map.put("temp.dingWorkPhoneEnabled", value);
							map.put("dingWorkPhoneEnabled", "");
						}
						//System.out.println("value2:" + value);
						value = map.get("dingNoEnabled");
						if (StringUtil.isNotNull(value)) {
							map.put("temp.dingNoEnabled", value);
							map.put("dingNoEnabled", "");
						}
						//System.out.println("value3:" + value);
						value = map.get("dingPostEnabled");
						if (StringUtil.isNotNull(value)) {
							map.put("temp.dingPostEnabled", value);
							map.put("dingPostEnabled", "");
						}
						//System.out.println("value4:" + value);
						value = map.get("dingPersonOrder");
						if (StringUtil.isNotNull(value)) {
							map.put("temp.dingPersonOrder", value);
							map.put("dingPersonOrder", "");
						}
						//System.out.println("value5:" + value);
						value = map.get("dingPostMulDeptEnabled");
						if (StringUtil.isNotNull(value)) {
							map.put("temp.dingPostMulDeptEnabled", value);
							map.put("dingPostMulDeptEnabled", "");
						}
						//System.out.println("value6:" + value);
						value = map.get("dingDeptLeaderEnabled");
						if (StringUtil.isNotNull(value)) {
							map.put("temp.dingDeptLeaderEnabled", value);
							map.put("dingDeptLeaderEnabled", "");
						}
						//System.out.println("value7:" + value);

						value = map.get("wxLoginName");
						if (StringUtil.isNotNull(value)) {
							map.put("temp.wxLoginName", value);
							map.put("wxLoginName", "");
						}
						//System.out.println("value8:" + value);

						// 钉钉到ekp
						value = map.get("dingOmsInMoreDeptEnabled");
						if (StringUtil.isNotNull(value)) {
							map.put("temp.dingOmsInMoreDeptEnabled", value);
							map.put("dingOmsInMoreDeptEnabled", "");
						}

						value = map.get("dingOmsInDeptManagerEnabled");
						if (StringUtil.isNotNull(value)) {
							map.put("temp.dingOmsInDeptManagerEnabled", value);
							map.put("dingOmsInDeptManagerEnabled", "");
						}
						config.save();
					}

					// 同步钉钉分类
					if ("true".equals(
							DingConfig.newInstance().getAttendanceEnabled())) {

						SynDingDirService.newInstance()
								.synCategoryFromDing(DingUtil.getCorpId(),
										null);
					}

				} catch (Exception e) {
					logger.error("", e);
				}
			}

			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return this.getActionForward("success", mapping, form, request,
					response);
		} catch (Exception e) {
			messages.addError((Throwable) e);
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return this.getActionForward("failure", mapping, form, request,
					response);
		}

	}

	// 获取自定义属性
	public void getCustomList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		try {
			DynamicAttributeConfig config = DynamicAttributeUtil
					.getDynamicAttributeConfig(
							"com.landray.kmss.sys.organization.model.SysOrgPerson");
			if (config != null) {
				List<DynamicAttributeField> list = new ArrayList<DynamicAttributeField>(
						config.getFields());

				JSONArray ja = new JSONArray();
				for (DynamicAttributeField file : list) {
					JSONObject json = new JSONObject();
					if ("false".equals(file.getStatus())) {
						continue;
					}
					json.put("key", file.getFieldName());
					json.put("value", file.getFieldText("def"));

					
					logger.debug("ddddddd" + file.getStatus());
					logger.debug(
							"file.getFieldText:" + file.getFieldText("def"));
					ja.add(json);
				}
				if (ja == null || ja.isEmpty()) {
					logger.warn("没有获取到具体的自定义数据！");
					response.getWriter().write("0");
				} else {
					logger.warn(ja.toString());
					response.getWriter().write(
							"[{\"key\":\"shenfenzhenghaoma2\",\"value\":\"身份证号码2\"}]");
				}
			}

		} catch (Exception e) {
			logger.warn("获取自定义数据发生异常！");
			response.getWriter().write("0");
		}
	}

	protected ActionForward getActionForward(String defaultForward,
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String para = request.getParameter("forward");
		if (!StringUtil.isNull(para) && !"failure".equals(defaultForward)) {
            defaultForward = para;
        }
		return mapping.findForward(defaultForward);
	}

	public ActionForward deleteCustom(ActionMapping mapping, ActionForm form,
									  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		try {
			DingUtil.deleteCustomData();
			json.put("status", 1);
			json.put("msg", "成功");
		} catch (Exception e) {
			json.put("status", 0);
			json.put("msg", "删除自定义配置失败："+e.getMessage());
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json.toString());
		return null;
	}

}
