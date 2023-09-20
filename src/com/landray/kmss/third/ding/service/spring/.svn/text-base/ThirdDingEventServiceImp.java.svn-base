package com.landray.kmss.third.ding.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.query.Query;

import com.dingtalk.api.request.OapiCallBackDeleteCallBackRequest;
import com.dingtalk.api.request.OapiCallBackGetCallBackRequest;
import com.dingtalk.api.request.OapiCallBackUpdateCallBackRequest;
import com.dingtalk.api.response.OapiCallBackDeleteCallBackResponse;
import com.dingtalk.api.response.OapiCallBackGetCallBackResponse;
import com.dingtalk.api.response.OapiCallBackUpdateCallBackResponse;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.ThirdDingEvent;
import com.landray.kmss.third.ding.service.IThirdDingEventService;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.ThirdDingTalkClient;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.UserUtil;
import com.taobao.api.ApiException;

import net.sf.json.JSONObject;

public class ThirdDingEventServiceImp extends ExtendDataServiceImp implements IThirdDingEventService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingEventServiceImp.class);

	@Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context)
			throws Exception {
		model = super.convertBizFormToModel(form, model, context);
		if (model instanceof ThirdDingEvent) {
			ThirdDingEvent thirdDingEvent = (ThirdDingEvent) model;
			thirdDingEvent.setDocAlterTime(new Date());
			thirdDingEvent.setDocAlteror(UserUtil.getUser());
		}
		return model;
	}

	@Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
		ThirdDingEvent thirdDingEvent = new ThirdDingEvent();
		thirdDingEvent.setDocCreateTime(new Date());
		thirdDingEvent.setDocAlterTime(new Date());
		thirdDingEvent.setDocCreator(UserUtil.getUser());
		thirdDingEvent.setDocAlteror(UserUtil.getUser());
		ThirdDingUtil.initModelFromRequest(thirdDingEvent, requestContext);
		return thirdDingEvent;
	}

	@Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext)
			throws Exception {
		ThirdDingEvent thirdDingEvent = (ThirdDingEvent) model;
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		ThirdDingEvent event = (ThirdDingEvent) modelObj;
		if (checkEvent(event)){
			return null;
		}
		String url = DingConstant.DING_PREFIX + "/call_back/register_call_back";
		updateCallBack((ThirdDingEvent) modelObj);
		return super.add(modelObj);
	}

	/**
	 * <p>检查事件是否已经注册</p>
	 * @param event
	 * @return
	 * @author 孙佳
	 * @throws Exception 
	 */
	private boolean checkEvent(ThirdDingEvent event) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(" thirdDingEvent.fdTag = :fdTag");
		hqlInfo.setParameter("fdTag", event.getFdTag());
		List<ThirdDingEvent> list = super.findList(hqlInfo);
		return (null != list && list.size() > 0) ? true : false;
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		/*updateCallBack((ThirdDingEvent) modelObj);*/
		super.update(modelObj);
	}

	@Override
    public JSONObject updateCallBack(ThirdDingEvent event) {
		JSONObject obj = new JSONObject();
		String code = null, errmsg = null;
		String url = DingConstant.DING_PREFIX + "/call_back/update_call_back";
		try {
			List<String> backTag = getCallBack();
			if (ArrayUtil.isEmpty(backTag)) {
				url = DingConstant.DING_PREFIX + "/call_back/register_call_back";
			}
			if (event.getFdIsStatus()) {
				backTag.remove(event.getFdTag());
			} else {
				backTag.add(event.getFdTag());
			}
			//如果backTag为空，执行删除
			if (ArrayUtil.isEmpty(backTag)) {
				url = DingConstant.DING_PREFIX + "/call_back/delete_call_back"
						+ DingUtil.getDingAppKeyByEKPUserId("?", null);
				logger.debug("钉钉接口：" + url);
				ThirdDingTalkClient client = new ThirdDingTalkClient(url);
				OapiCallBackDeleteCallBackRequest delRequest = new OapiCallBackDeleteCallBackRequest();
				delRequest.setTopHttpMethod("GET");
				OapiCallBackDeleteCallBackResponse response = client.execute(delRequest,
						DingUtils.getDingApiService().getAccessToken());
				logger.error(response.getErrorCode() + " " + response.getErrmsg() + " " + response.getBody());
				code = response.getErrorCode();
				errmsg = response.getErrmsg();
			} else {
				DingConfig config = DingConfig.newInstance();
				logger.debug("钉钉接口：" + url
						+ DingUtil.getDingAppKeyByEKPUserId("?", null));
				ThirdDingTalkClient client = new ThirdDingTalkClient(
						url + DingUtil.getDingAppKeyByEKPUserId("?", null));
				OapiCallBackUpdateCallBackRequest request = new OapiCallBackUpdateCallBackRequest();
				request.setUrl(event.getFdCallbackUrl());
				request.setAesKey(config.getDingAeskey());
				request.setToken(config.getDingToken());
				request.setCallBackTag(backTag);
				OapiCallBackUpdateCallBackResponse response = client.execute(request,
						DingUtils.getDingApiService().getAccessToken());
				logger.error(response.getErrorCode() + " " + response.getErrmsg() + " " + response.getBody());
				code = response.getErrorCode();
				errmsg = response.getErrmsg();
				/*if (response.getErrcode() == 0) {
					event.setFdIsAvailable(true);
				} else {
					event.setFdIsAvailable(false);
				}*/
			}
			obj.put("code", code);
			obj.put("msg", errmsg);
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
		}
		return null;
	}


	/*public void delete(IBaseModel modelObj) throws Exception {
		ThirdDingEvent dingEvent = (ThirdDingEvent) modelObj;
		updateCallBack(dingEvent);
		super.delete(modelObj);
	}*/

	/**
	 * <p>查询所有注册的回调事件</p>
	 * @author 孙佳
	 * @throws Exception 
	 * @throws ApiException 
	 */
	private List<String> getCallBack() throws Exception {
		List<String> backTag = new ArrayList<String>();
		String url = DingConstant.DING_PREFIX + "/call_back/get_call_back"
				+ DingUtil.getDingAppKeyByEKPUserId("?", null);
		logger.debug("钉钉接口：" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		OapiCallBackGetCallBackRequest request = new OapiCallBackGetCallBackRequest();
		request.setTopHttpMethod("GET");
		OapiCallBackGetCallBackResponse response = client.execute(request,
				DingUtils.getDingApiService().getAccessToken());
		logger.error("查询回调事件返回：code=" + response.getErrorCode() + "，msg=" + response.getErrmsg() + "，tag=" + response.getCallBackTag());
		if (response.getErrcode() == 0) {
			backTag = response.getCallBackTag();
		}
		return backTag;

	}

	/**
	 * <p>删除所有注册回调事件</p>
	 * @author 孙佳
	 * @throws Exception 
	 * @throws ApiException 
	 */
	private void deleteCallBack() throws ApiException, Exception {
		String url = DingConstant.DING_PREFIX + "/call_back/delete_call_back"
				+ DingUtil.getDingAppKeyByEKPUserId("?", null);
		logger.debug("钉钉接口 url:" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		OapiCallBackDeleteCallBackRequest request = new OapiCallBackDeleteCallBackRequest();
		request.setTopHttpMethod("GET");
		OapiCallBackDeleteCallBackResponse response = client.execute(request,
				DingUtils.getDingApiService().getAccessToken());
		if (response.getErrcode() == 0) {

		} else {
			logger.error("删除失败，详细：" + response.getBody());
		}
	}

	@Override
	public void deleteAll() {
		Query query = this.getBaseDao().getHibernateSession()
				.createQuery("delete from com.landray.kmss.third.ding.model.ThirdDingEvent");
		query.executeUpdate();
	}
}

