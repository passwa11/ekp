package com.landray.kmss.km.imeeting.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.model.KmImeetingRes;
import com.landray.kmss.km.imeeting.model.KmImeetingSeatPlan;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainService;
import com.landray.kmss.km.imeeting.service.IKmImeetingSeatPlanService;
import com.landray.kmss.sys.notify.constant.SysNotifyConstants;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.sso.client.oracle.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class KmImeetingSeatPlanServiceImp extends BaseServiceImp
		implements IKmImeetingSeatPlanService {

	private IKmImeetingMainService kmImeetingMainService;

	public void
			setKmImeetingMainService(
					IKmImeetingMainService kmImeetingMainService) {
		this.kmImeetingMainService = kmImeetingMainService;
	}

	private ISysNotifyMainCoreService sysNotifyMainCoreService;

	public void setSysNotifyMainCoreService(
			ISysNotifyMainCoreService sysNotifyMainCoreService) {
		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		KmImeetingSeatPlan seatPlan = (KmImeetingSeatPlan) modelObj;
		String fdId = super.add(modelObj);
		KmImeetingMain main = seatPlan.getFdImeetingMain();
		main.setFdSeatPlanId(fdId);
		main.setFdIsSeatPlan(true);
		kmImeetingMainService.update(main);
		// 发送待阅给会议室保管员
		sendNotifyToKeeper(seatPlan);
		return fdId;
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		KmImeetingSeatPlan seatPlan = (KmImeetingSeatPlan) modelObj;
		// 发送待阅给会议室保管员
		sendNotifyToKeeper(seatPlan);
		super.update(modelObj);
	}

	private void sendNotifyToKeeper(KmImeetingSeatPlan seatPlan)
			throws Exception {
		KmImeetingMain main = seatPlan.getFdImeetingMain();
		// 获取临时座位数
		int count = getTemporaryCount(seatPlan.getFdSeatDetail(),
				seatPlan.getFdIsTopicPlan());
		if (count > 0) {
			NotifyContext notifyContext = sysNotifyMainCoreService
					.getContext(
							"km-imeeting:kmImeetingSeatPlan.send.notify.add.seat");
			// 通知人
			List<SysOrgElement> tempListNotify = new ArrayList<SysOrgElement>();
			KmImeetingRes res = main.getFdPlace();
			if (res != null) {
				SysOrgElement keeper = res.getDocKeeper();
				if (keeper != null) {
					tempListNotify.add(keeper);
				}
				notifyContext.setNotifyTarget(tempListNotify);
				// 通知方式
				notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);// 待阅
				notifyContext
						.setNotifyType(
								SysNotifyConstants.NOTIFY_QUEUE_TYPE_TODO);
				// 发起人
				notifyContext.setDocCreator(UserUtil.getUser());
				// 替换内容中的可变文本
				HashMap replaceMap = new HashMap();
				replaceMap.put("km-imeeting:kmImeetingMain.fdPlace",
						res.getFdName());
				replaceMap.put("km-imeeting:kmImeeting.count", count + "");
				replaceMap.put("km-imeeting:kmImeetingMain.fdName",
						main.getFdName());
				String fdDate = DateUtil.convertDateToString(
						main.getFdHoldDate(),
						DateUtil.PATTERN_DATETIME);
				replaceMap.put("km-imeeting:kmImeetingMain.fdDate", fdDate);
				sysNotifyMainCoreService.send(main, notifyContext,
						replaceMap);
			}
		}
	}

	/**
	 * 获取临时座位数
	 * 
	 * @return
	 */
	private static int getTemporaryCount(String fdSeatDetail,
			boolean fdIsTopicPlan) {
		int count = 0;
		if (StringUtil.isNotNull(fdSeatDetail)) {
			if (fdIsTopicPlan) {
				JSONArray array = JSONArray.fromObject(fdSeatDetail);
				for (int i = 0; i < array.size(); i++) {
					int num = 0;
					JSONObject obj = array.getJSONObject(i);
					if (!obj.isEmpty()) {
						JSONArray arr = (JSONArray) obj.get("data");
						for (int j = 0; j < arr.size(); j++) {
							JSONObject object = arr.getJSONObject(j);
							if (!object.isEmpty()) {
								JSONObject ob = (JSONObject) object
										.get("clazz");
								String type = ob.getString("type");
								if ("0".equals(type)) {
									num++;
								}
							}
						}
					}
					if (num > count) {
						count = num;
					}
				}
			} else {
				JSONArray array = JSONArray.fromObject(fdSeatDetail);
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					if (!obj.isEmpty()) {
						JSONObject ob = (JSONObject) obj.get("clazz");
						String type = ob.getString("type");
						if ("0".equals(type)) {
							count++;
						}
					}
				}
			}

		}
		return count;
	}

	@Override
	public void deleteAllSeatPlan(String imeetingId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("kmImeetingSeatPlan.fdId");
		hqlInfo.setWhereBlock(" kmImeetingSeatPlan.fdImeetingMain.fdId=:fdId ");
		hqlInfo.setParameter("fdId", imeetingId);
		List<String> list = this.findList(hqlInfo);
		if (null != list && list.size() > 0) {
			this.delete(list.toArray(new String[0]));
		}
	}

}
