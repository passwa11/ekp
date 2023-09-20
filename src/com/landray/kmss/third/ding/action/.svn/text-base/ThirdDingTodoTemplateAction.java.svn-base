package com.landray.kmss.third.ding.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.landray.kmss.third.ding.model.ThirdDingTodoCard;
import com.landray.kmss.third.ding.model.api.TodoCard;
import com.landray.kmss.third.ding.service.IThirdDingTodoCardService;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.util.*;
import org.slf4j.Logger;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.ding.forms.ThirdDingTodoTemplateForm;
import com.landray.kmss.third.ding.model.ThirdDingTodoTemplate;
import com.landray.kmss.third.ding.service.IThirdDingTodoTemplateService;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ThirdDingTodoTemplateAction extends ExtendAction {

    private IThirdDingTodoTemplateService thirdDingTodoTemplateService;

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingTodoTemplateAction.class);

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdDingTodoTemplateService == null) {
            thirdDingTodoTemplateService = (IThirdDingTodoTemplateService) getBean("thirdDingTodoTemplateService");
        }
        return thirdDingTodoTemplateService;
    }

	private IThirdDingTodoCardService thirdDingTodoCardService;

	public IThirdDingTodoCardService getThirdDingTodoCardService() {
		if (thirdDingTodoCardService == null) {
			thirdDingTodoCardService = (IThirdDingTodoCardService) SpringBeanUtil
					.getBean("thirdDingTodoCardService");
		}
		return thirdDingTodoCardService;
	}

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdDingTodoTemplate.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.ding.model.ThirdDingTodoTemplate.class);
        com.landray.kmss.third.ding.util.ThirdDingUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdDingTodoTemplateForm thirdDingTodoTemplateForm = (ThirdDingTodoTemplateForm) super.createNewForm(mapping, form, request, response);
        ((IThirdDingTodoTemplateService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));

		thirdDingTodoTemplateForm.setDocCreateTime(DateUtil
				.convertDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		thirdDingTodoTemplateForm.setFdIscustom("0");
		thirdDingTodoTemplateForm.setFdIsdefault("0");
		thirdDingTodoTemplateForm.setFdType("1"); // 默认选择钉钉待办作为推送方式
		SysOrgPerson currentUser = UserUtil.getUser();
		thirdDingTodoTemplateForm.setDocCreatorId(currentUser.getFdId());
		thirdDingTodoTemplateForm.setDocCreatorName(currentUser.getFdName());
        return thirdDingTodoTemplateForm;
    }

	/**
	 * 将浏览器提交的表单数据添加到数据库中。<br>
	 * 该操作一般以HTTP的POST方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回success页面，否则将将错误信息返回edit页面
	 * @throws Exception
	 */
	@Override
    public ActionForward save(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			ThirdDingTodoTemplateForm thirdDingTodoTemplateForm = (ThirdDingTodoTemplateForm) form;

			String id =getServiceImp(request).add((IExtendForm) form,
					new RequestContext(request));

			if(thirdDingTodoTemplateForm.getFdType().contains("1")){
				logger.error("--------新增id--------"+id);
				String fdDetail = thirdDingTodoTemplateForm.getFdDetail();
				logger.debug("fdDetail:" + fdDetail);
				JSONArray ja = JSONObject.fromObject(fdDetail).getJSONArray("data");
				addCards(getLangDateMap(ja),thirdDingTodoTemplateForm.getFdId(),thirdDingTodoTemplateForm.getFdName());
			}else{
				logger.warn("工作通知不建卡片");
			}

		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request,
					response);
		}
	}

	private void addDingCard(JSONObject result,String name,String tempId,String lang) {

		try {
			ThirdDingTodoCard card = new ThirdDingTodoCard();
			card.setFdCardId(result.getString("id"));
			card.setFdName(name);
			card.setFdTemplateId(tempId);
			card.setFdLang(lang);
			card.setDocCreateTime(new Date());
			card.setFdCardMsg(result.toString());
			getThirdDingTodoCardService().add(card);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	private Map<String, List>  getLangDateMap(JSONArray data) {
		Map<String, List> langMap = new HashMap<String, List>();
		Map<String, String> defaultTitle = new HashMap<String, String>(); //默认主题，多语言
		for (int i = 0; i < data.size(); i++) {
			String key = data.getJSONObject(i).getString("key");
			JSONArray titleJA = data.getJSONObject(i).getJSONArray("title");
			for (int j = 0; j < titleJA.size(); j++) {
				String lang = "";
				if(titleJA.getJSONObject(j).containsKey("lang")){
					lang = titleJA.getJSONObject(j).getString("lang");
				}else{
					lang = DingUtil.getTodoDefaultLang();
				}
				//"lang": "中文|zh-CN"
				if (StringUtil.isNotNull(lang) && lang.contains("|")) {
					lang = lang.substring(lang.indexOf("|") + 1);
				}
				logger.warn("语言：" + lang);
				String name = titleJA.getJSONObject(j).getString("value");
				if (StringUtil.isNotNull(name) && (j == 0 || !defaultTitle.containsKey(key))) { //默认是第一个
					defaultTitle.put(key, name);
				}
				//如果为空，则取默认值
				if (StringUtil.isNull(name) && defaultTitle.containsKey(key)) {
					name = defaultTitle.get(key);
				}
				List tempList = null;
				if (langMap.containsKey(lang)) {
					tempList = langMap.get(lang);
				} else {
					tempList = new ArrayList<String>();
				}
				String[] tempTitle = new String[2];
				tempTitle[0] = key;
				tempTitle[1] = name;
				tempList.add(tempTitle);
				langMap.put(lang, tempList);
			}
		}
		return langMap;
	}


	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//处理模板更新
		ThirdDingTodoTemplateForm thirdDingTodoTemplateForm = (ThirdDingTodoTemplateForm) form;

		if(thirdDingTodoTemplateForm.getFdType().contains("1")){
			JSONArray data = JSONObject.fromObject(thirdDingTodoTemplateForm.getFdDetail()).getJSONArray("data");
			Map<String, List> langMap = getLangDateMap(data);
			List<ThirdDingTodoCard> cardList=getThirdDingTodoCardService().findList("fdTemplateId='"+thirdDingTodoTemplateForm.getFdId()+"'",null);
			if(cardList==null||cardList.size()==0){
				addCards(langMap,thirdDingTodoTemplateForm.getFdId(),thirdDingTodoTemplateForm.getFdName());
			}else{
				//更新
				Map<String, List> addMap = new HashMap<String, List> ();
				for(String lang:langMap.keySet()){
					boolean hadBuildFlag=false;
					for(ThirdDingTodoCard card:cardList){
						if(lang.equals(card.getFdLang())){
							updateCards(thirdDingTodoTemplateForm.getFdId(),thirdDingTodoTemplateForm.getFdName(),
									card.getFdCardId(),card.getFdId(),lang,langMap.get(lang));
							hadBuildFlag=true;
							break;
						}
					}
					if(!hadBuildFlag){
						addMap.put(lang,langMap.get(lang));
					}
				}
				if(!addMap.isEmpty()){
					addCards(addMap,thirdDingTodoTemplateForm.getFdId(),thirdDingTodoTemplateForm.getFdName());
				}

			}
		}else{
			logger.warn("更新--工作通知不建卡片:"+thirdDingTodoTemplateForm.getFdName());
		}

		return super.update(mapping, form, request, response);
	}

	private void updateCards(String tempId, String fdName,String cardId,String ekpCardId,String lang,List items) throws Exception {
		String creatorUnionId = DingUtil.getUnionIdByEkpId(UserUtil.getUser().getFdId());
		if(StringUtil.isNull(creatorUnionId)){
			//创建人的unionId为空，去获取一个管理员的unionId
			creatorUnionId = DingUtil.getDingAdminUinionId();
		}
		//根据语言去创建卡片
		ThirdDingTodoCard todoCard = (ThirdDingTodoCard) getThirdDingTodoCardService().findByPrimaryKey(ekpCardId,ThirdDingTodoCard.class,true);

		TodoCard card = new TodoCard();
		card.setCardType(TodoCard.CARDTYPE_CUSTOM);
		card.setIcon("https://img.alicdn.com/imgextra/i4/O1CN01DL6m7D22fNN3EY3RK_!!6000000007147-2-tps-24-24.png");
		card.setPcDetailUrlOpenMode("PC_SLIDE");
		List<TodoCard.CardField> cardList = new ArrayList<TodoCard.CardField>();
		List<String[]> fieldArray = items;
		for (int k = 0; k < fieldArray.size(); k++) {
			cardList.add(new TodoCard.CardField(fieldArray.get(k)[0], new TodoCard.NameI18n(fieldArray.get(k)[1])));
		}
		card.setContentFieldList(cardList);

		JSONObject addResult = null;
		try {
			addResult = DingUtils.getDingApiService().updateCard(creatorUnionId,cardId,card);
			logger.warn("更新卡片结果：" + addResult);
			logger.debug("卡片内容：" + JSON.toJSONString(card.getContentFieldList()));
			//记录卡片日志
			if(addResult!=null&&addResult.containsKey("result")&&addResult.getBoolean("result")) {
				JSONObject cardMsg =JSONObject.fromObject(todoCard.getFdCardMsg());
				//cardMsg.remove("contentFieldList");
				cardMsg.put("contentFieldList",JSONArray.fromObject(JSON.toJSONString(card.getContentFieldList())));
				todoCard.setFdCardMsg(cardMsg.toString());
				todoCard.setFdName(fdName);
				todoCard.setDocAlterTime(new Date());
				getThirdDingTodoCardService().update(todoCard);
			}else{
				logger.warn("更新卡片识别失败："+addResult);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}

	}

	private void addCards(Map<String, List> langMap, String fdId, String fdName) {
		String creatorUnionId = DingUtil.getUnionIdByEkpId(UserUtil.getUser().getFdId());
		if(StringUtil.isNull(creatorUnionId)){
			//创建人的unionId为空，去获取一个管理员的unionId
			creatorUnionId = DingUtil.getDingAdminUinionId();
		}
		//根据语言去创建卡片
		for (String lang : langMap.keySet()) {
			TodoCard card = new TodoCard();
			card.setCardType(TodoCard.CARDTYPE_CUSTOM);
			card.setIcon("https://img.alicdn.com/imgextra/i4/O1CN01DL6m7D22fNN3EY3RK_!!6000000007147-2-tps-24-24.png");
			card.setPcDetailUrlOpenMode("PC_SLIDE");
			List<TodoCard.CardField> cardList = new ArrayList<TodoCard.CardField>();
			List<String[]> fieldArray = langMap.get(lang);
			for (int k = 0; k < fieldArray.size(); k++) {
				cardList.add(new TodoCard.CardField(fieldArray.get(k)[0], new TodoCard.NameI18n(fieldArray.get(k)[1])));
			}
			card.setContentFieldList(cardList);

			JSONObject addResult = null;
			try {
				addResult = DingUtils.getDingApiService().addCard(creatorUnionId, card);
				logger.warn("新建卡片结果：" + addResult);

				//记录卡片日志
				if(addResult!=null&&addResult.containsKey("id")) {
					addDingCard(addResult,fdName,fdId,lang);
				}else{
					logger.warn("新建卡片识别："+addResult);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
		}
	}

	@Override
    public ActionForward edit(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-edit", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
			ThirdDingTodoTemplateForm thirdDingTodoTemplateForm = (ThirdDingTodoTemplateForm) form;
			thirdDingTodoTemplateForm.setDocAlterTime(DateUtil
					.convertDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
			String fdDetail = thirdDingTodoTemplateForm.getFdDetail();
			logger.debug("==fdDetail:" + fdDetail);

			JSONObject fdDetailJSON = JSONObject.fromObject(fdDetail);
			JSONArray ja = fdDetailJSON.getJSONArray("data");
			List keyList = new ArrayList();
			List langList = new ArrayList();
			for (int i = 0; i < ja.size(); i++) {
				logger.debug("key:" + ja.getJSONObject(i).getString("key"));
				Map map = new HashMap();
				JSONArray titleJA = ja.getJSONObject(i).getJSONArray("title");
				logger.debug("titleJA:" + titleJA.toString());
				Map langMap = new HashMap();
				// langList.add(titleJA);
				for (int j = 0; j < titleJA.size(); j++) {
					Map m = new HashMap();
					m.put("lang",
							titleJA.getJSONObject(j).getString("lang"));
					m.put("value",
							titleJA.getJSONObject(j).getString("value"));
					langList.add(m);

				}
				map.put("key", ja.getJSONObject(i).getString("key"));
				// map.put("lang", langMap);
				// keyList.add(ja.getJSONObject(i).getString("key"));
				keyList.add(map);

			}
			// request.setAttribute("keyList", keyList);
			request.setAttribute("fdDetail_Form_keyList", keyList);
			request.setAttribute("fdDetail_Form_langList", langList);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-edit", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("edit", mapping, form, request, response);
		}
	}

	// 根据数据字典获取表单数据
	public void findFieldDictByModelName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		RequestContext requestInfo = new RequestContext(request);

		List list = ((IThirdDingTodoTemplateService) getServiceImp(request))
				.getDataList(requestInfo);
		logger.debug("========list:" + list);
		JSONArray rs = new JSONArray();

		for (int i = 0; i < list.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			Object[] object = (Object[]) list.get(i);
			try {
				jsonObject.put("key", object[0]);
				jsonObject.put("name", object[1]);
				jsonObject.put("type", object[2]);
				rs.add(jsonObject);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

		}

		logger.debug("========rs:" + rs.toString());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(rs.toString());
	}

	/**
	 * 打开阅读页面。<br>
	 * URL中必须包含fdId参数，该参数为记录id。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回view页面，否则返回failure页面
	 * @throws Exception
	 */
	@Override
    public ActionForward view(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-view", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
			ThirdDingTodoTemplateForm thirdDingTodoTemplateForm = (ThirdDingTodoTemplateForm) form;
			String fdDetail = thirdDingTodoTemplateForm.getFdDetail();
			logger.debug("==fdDetail:" + fdDetail);
			JSONObject fdDetailJSON = JSONObject.fromObject(fdDetail);
			JSONArray ja = fdDetailJSON.getJSONArray("data");
			List keyList = new ArrayList();
			for (int i = 0; i < ja.size(); i++) {
				Map map = new HashMap();
				logger.debug("key:" + ja.getJSONObject(i).getString("key"));
				logger.debug(
						"name:" + ja.getJSONObject(i).getString("name"));
				map.put("key", ja.getJSONObject(i).getString("key"));
				map.put("name", ja.getJSONObject(i).getString("name"));
				keyList.add(map);
			}
			// request.setAttribute("keyList", keyList);
			request.setAttribute("fdDetail_Form_keyList", keyList);

		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-view", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("view", mapping, form, request, response);
		}
	}

	public void delectModel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		String fdId = request.getParameter("fdId");

		try {
			getServiceImp(request).delete(fdId);
		} catch (Exception e) {
			response.getWriter().write("fail");
		}
		response.getWriter().write("success");
	}

	// 判断模版是否存在
	public void checkModel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		String fdModelName = request.getParameter("fdModelName");
		String fdTemplateId = request.getParameter("fdTemplateId");
		logger.debug("fdModelName:" + fdModelName + "  fdTemplateId:"
				+ fdTemplateId);
		try {
			HQLInfo hqlInfo = new HQLInfo();
			if ("com.landray.kmss.km.review.model.KmReviewMain"
					.equals(fdModelName)) {
				hqlInfo.setWhereBlock(
						" fdModelName=:fdModelName and fdTemplateId =:fdTemplateId");
				hqlInfo.setParameter("fdModelName", fdModelName);
				hqlInfo.setParameter("fdTemplateId", fdTemplateId);
			} else {
				hqlInfo.setWhereBlock(
						" fdModelName=:fdModelName");
				hqlInfo.setParameter("fdModelName", fdModelName);
			}

			List<ThirdDingTodoTemplate> list = getServiceImp(request)
					.findList(hqlInfo);
			if (list.size() > 0) {
				logger.debug("==========已存在该模版======");
				response.getWriter().write("1");
			} else {
				logger.debug("==========还不存在,可以新建======");
				response.getWriter().write("0");
			}
		} catch (Exception e) {
			response.getWriter().write("fail");
		}
	}

}
