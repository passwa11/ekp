package com.landray.kmss.sys.attend.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryLocation;
import com.landray.kmss.sys.attend.model.SysAttendCategoryWifi;
import com.landray.kmss.sys.attend.model.SysAttendCategoryWorktime;
import com.landray.kmss.sys.attend.model.SysAttendConfig;
import com.landray.kmss.sys.attend.model.SysAttendHisCategory;
import com.landray.kmss.sys.attend.model.SysAttendMain;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendConfigService;
import com.landray.kmss.sys.attend.service.ISysAttendDeviceService;
import com.landray.kmss.sys.attend.service.ISysAttendHisCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendMainService;
import com.landray.kmss.sys.attend.util.AttendConstant;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.sso.client.oracle.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 极速打卡接口
 * 
 * @author linxiuxian
 *
 */
public class SysAttendApiAction extends ExtendAction {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendApiAction.class);
	private ISysAttendCategoryService sysAttendCategoryService;
	private ISysAttendMainService sysAttendMainService;
	private ISysNotifyMainCoreService sysNotifyMainCoreService;
	private ISysAttendConfigService sysAttendConfigService;
	private ISysAttendDeviceService sysAttendDeviceService;



	public ActionForward getAttendInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-getAttendInfo", true, getClass());
		JSONObject json = new JSONObject();
		JSONObject datas = new JSONObject();
		JSONArray fdLocations = new JSONArray();
		JSONArray fdWifis = new JSONArray();

		datas.put("fdCategoryId", "");
		datas.put("fdStatus", 0);
		datas.put("fdLocations", fdLocations);
		datas.put("fdWifis", fdWifis);
		datas.put("fdSignTime", "");
		datas.put("fdStartTime", "");
		datas.put("fdLimit", 0);

		try {
			String fdCategoryId = getAttendCategoryServiceImp()
					.getAttendCategory(UserUtil.getUser(), new Date());
			if (StringUtil.isNotNull(fdCategoryId)) {
				SysAttendCategory category = CategoryUtil.getCategoryById(fdCategoryId);
				if(category ==null) {
					category = (SysAttendCategory) getAttendCategoryServiceImp()
							.findByPrimaryKey(fdCategoryId);
				}
				// 安全打卡校验
				String fdSecurity = category.getFdSecurityMode();
				if (AttendUtil.isEnableKKConfig()
						&& AttendConstant.FD_SECURITY_MODE[0].equals(fdSecurity)
						|| AttendConstant.FD_SECURITY_MODE[1]
								.equals(fdSecurity)) {
					json.put("datas", datas);
					request.setAttribute("lui-source", json);
					logger.warn(
							"kk http request getAttendInfo:安全设置限制,不允许极速打卡 ");
					return mapping.findForward("lui-source");
				}
				// 通用配置
				SysAttendConfig config = this.getSysAttendConfigService()
						.getSysAttendConfig();
				if(Boolean.TRUE.equals(config.getFdClientLimit())){
					if (!AttendConstant.SysAttendConfig.FD_CLIENT[0]
							.equals(config.getFdClient())) {
						json.put("datas", datas);
						request.setAttribute("lui-source", json);
						logger.warn(
								"kk http request getAttendInfo:客户端限制,仅允许kk极速打卡 ");
						return mapping.findForward("lui-source");
					}
				}
				// 考勤时间点
				List<Map<String, Object>> signTimes = getAttendCategoryServiceImp()
						.getAttendSignTimes(category, new Date(),
								UserUtil.getUser());
				Map<String, Object> map = signTimes.get(0);
				if (isSigned(fdCategoryId, signTimes)) {
					datas.put("fdStatus", 1);
				}
				datas.put("fdCategoryId", fdCategoryId);
				datas.put("fdSignTime", DateUtil.convertDateToString(
						(Date) map.get("signTime"), "HH:mm"));
				datas.put("fdStartTime", DateUtil.convertDateToString(
						(Date) map.get("fdStartTime"), "HH:mm"));
				datas.put("fdLimit", map.get("fdLimit") == null ? 0
						: (Integer) map.get("fdLimit"));

				List<SysAttendCategoryLocation> locations = (List<SysAttendCategoryLocation>) map
						.get("fdLocations");
				List<SysAttendCategoryWifi> wifis = (List<SysAttendCategoryWifi>) map
						.get("fdWifiConfigs");

				if (locations != null && !locations.isEmpty()) {
					for (SysAttendCategoryLocation location : locations) {
						JSONObject loc = new JSONObject();
						loc.put("fdLocation", location.getFdLocation());
						loc.put("fdLatLng",
								AttendUtil.formatCoord(location.getFdLatLng()));
						loc.put("fdLimit", location.getFdLimit());
						fdLocations.add(loc);
					}
					datas.put("fdLocations", fdLocations);
				}
				if (wifis != null && !wifis.isEmpty()) {
					for (SysAttendCategoryWifi wifi : wifis) {
						JSONObject wifiJson = new JSONObject();
						wifiJson.put("fdName", wifi.getFdName());
						wifiJson.put("fdMac", wifi.getFdMacIp());
						fdWifis.add(wifiJson);
					}
					datas.put("fdWifis", fdWifis);
				}
			}
			json.put("status", 0);
		}catch (Exception e) {
			json.put("status", 1);
			logger.error(e.getMessage(), e);
		}

		json.put("datas", datas);
		request.setAttribute("lui-source", json);
		TimeCounter.logCurrentTime("Action-getAttendInfo", false, getClass());
		logger.debug("kk http request getAttendInfo,return datas:"
				+ json.toString());
		return mapping.findForward("lui-source");
	}


	/*
	 * kk极速打卡接口
	 */
	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		JSONObject json = new JSONObject();
		json.put("status", 0);
		try {
			String fdCategoryId = request.getParameter("fdCategoryId");
			String fdLocation = request.getParameter("fdLocation");
			String fdLatLng = request.getParameter("fdLatLng");
			String fdWifiName = request.getParameter("fdWifiName");
			String fdWifiMac = request.getParameter("fdWifiMac");
			String fdDevice = request.getParameter("fdDevice");
			if(!validateParam(request,json)){
				throw new Exception(
						"error message:" + json.getString("message"));
			}
			SysAttendHisCategory hisCategory= (SysAttendHisCategory) getSysAttendHisCategoryService().findByPrimaryKey(fdCategoryId);
			SysAttendCategory category = CategoryUtil.getCategoryById(fdCategoryId);
			// 安全打卡校验
			String fdSecurity = category.getFdSecurityMode();
			if (AttendUtil.isEnableKKConfig()
					&& AttendConstant.FD_SECURITY_MODE[0].equals(fdSecurity)
					|| AttendConstant.FD_SECURITY_MODE[1]
							.equals(fdSecurity)) {
				json.put("message", "security not support");
				throw new Exception(
						"error message:" + json.getString("message"));
			}

			// 考勤时间点
			List<Map<String, Object>> signTimes = getAttendCategoryServiceImp()
					.getAttendSignTimes(category, new Date(),
							UserUtil.getUser());
			Map<String, Object> map = signTimes.get(0);
			String fdWorkTimeId = (String) map.get("fdWorkTimeId");
			if (isSigned(category.getFdId(), signTimes)) {
				json.put("status", 1);
				request.setAttribute("lui-source", json);
				logger.warn(
						"kk http request save,用户已打卡无需极速打卡: " + json.toString());
				return mapping.findForward("lui-source");
			}
			if (!validate(request, map, json)) {
				throw new Exception(
						"error message:" + json.getString("message"));
			}

			SysAttendMain main = new SysAttendMain();
			main.setFdHisCategory(hisCategory);
			String fdClientType = AttendUtil.getClientType(new RequestContext(request));
			main.setFdClientInfo(fdClientType);
			if (StringUtil.isNotNull(fdDevice)) {
				String fdDeviceInfo = AttendUtil.getOperatingSystem(new RequestContext(request));
				fdDeviceInfo = fdDeviceInfo + ", " + fdClientType
						+ ResourceUtil.getString(
								"sysAttendMain.export.fdDeviceInfo.fdDeviceId",
								"sys-attend")
						+ ": " + fdDevice;
				main.setFdDeviceInfo(fdDeviceInfo);
			} else {
				main.setFdDeviceInfo(
						AttendUtil.getOperatingSystem(
								new RequestContext(request)));
			}
			if (StringUtil.isNotNull(fdLatLng)) {
				main.setFdLatLng(fdLatLng);
				main.setFdLocation(fdLocation);
				String[] coord = fdLatLng.replace("bd09:", "")
						.replace("gcj02:", "").split(",");
				main.setFdLat(coord[0]);
				main.setFdLng(coord[1]);

			}
			main.setFdDateType(Integer.valueOf(AttendConstant.FD_DATE_TYPE[0]));
			main.setFdWifiName(fdWifiName);
			main.setFdStatus(getFdStatus(map));
			main.setFdWorkType(0);
			main.setWorkTime(this.getAttendCategoryServiceImp()
					.getWorkTime(category, fdWorkTimeId));
			main.setFdWorkKey(fdWorkTimeId);
			main.setFdOutside(false);
			Date fdSignTime = (Date) map.get("signTime");
			fdSignTime = AttendUtil.joinYMDandHMS(new Date(), fdSignTime);
			main.setFdBaseWorkTime(fdSignTime);
			main.setFdSourceType("KK_ATM");// KK极速打卡
			//数据来源为KK
			main.setFdAppName("KK");
			// 设备信息
			getSysAttendDeviceService().saveOrUpdateUserDevice(request, json);
			getAttendMainServiceImp().add(main);
			json.put("status", 0);
			// 发送通知消息
			sendKKNotifyTodo(main);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			json.put("status", 2);
			if (!json.containsKey("message")) {
				json.put("message", "Unknown exception");
			}
			if (json.containsKey("code")
					&& "01".equals(json.getString("code"))) {
				json.put("message", "同一设备号不允许多人使用");
			}
		}
		request.setAttribute("lui-source", json);
		TimeCounter.logCurrentTime("Action-save", false, getClass());
		logger.debug("kk http request save,return datas:"
				+ json.toString());
		return mapping.findForward("lui-source");
	}

	private void sendKKNotifyTodo(SysAttendMain main) {
		try {
			SysAttendCategory category=CategoryUtil.getFdCategoryInfo(main);
			SysAttendCategoryWorktime workTime = null;
			List<SysAttendCategoryWorktime> workTimes = this
					.getAttendCategoryServiceImp().getWorktimes(category, new Date(), UserUtil.getUser());
			if (workTimes.isEmpty()) {
				return;
			}
			workTime = workTimes.get(0);
			String signTime = DateUtil
					.convertDateToString(workTime.getFdStartTime(), "HH:mm");
			NotifyContext notifyContext = getSysNotifyMainCoreService()
					.getContext(null);
			String signedTime = DateUtil.convertDateToString(new Date(),
					"HH:mm");
			String subject = ResourceUtil.getString(
					"sysAttendMain.kk.notify.subject", "sys-attend", null,
					new Object[] { signedTime });
			String signTimeTxt = ResourceUtil.getString(
					"sysAttendMain.kk.notify.fdStartWork", "sys-attend", null,
					new Object[] { signTime });
			String location = StringUtil.isNotNull(main.getFdLocation())
					? main.getFdLocation() : main.getFdWifiName();
			StringBuffer content = new StringBuffer();
			content.append(
					"{fdCategoryName}:" + main.getFdHisCategory().getFdName())
					.append("    {fdWork}:" + signTimeTxt)
					.append("    {fdLocation}:" + location);
			String contents = content.toString();
			contents = contents.replace("{fdCategoryName}",
							ResourceUtil.getString("sysAttendCategory.attend",
									"sys-attend"))
					.replace("{fdWork}", ResourceUtil.getString(
							"sysAttendMain.kk.notify.fdWork", "sys-attend"))
					.replace("{fdLocation}",
							ResourceUtil.getString(
									"sysAttendMain.kk.notify.fdLocation",
									"sys-attend"));

			notifyContext.setSubject(subject);
			notifyContext.setContent(contents);
			notifyContext.setNotifyType("todo");
			notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
			notifyContext.setKey("sysAttendMain_kk");
			notifyContext
					.setLink("/sys/attend/mobile/index_stat.jsp?categoryId="
							+ main.getFdHisCategory().getFdId());
			List<SysOrgElement> list = new ArrayList<SysOrgElement>();
			list.add(UserUtil.getKMSSUser().getPerson());
			notifyContext.setNotifyTarget(list);
			notifyContext.setFdAppType("kk");
			notifyContext.setFdNotifyEKP(false);
			notifyContext.setFdAppReceiver("kk_system");
			getSysNotifyMainCoreService().send(main, notifyContext, null);
		} catch (Exception e) {
			logger.error("极速打卡成功发送kk消息失败:" + e.getMessage(), e);
		}
	}

	private boolean validate(HttpServletRequest request,
			Map<String, Object> map, JSONObject json) {
		Date fdStartTime = (Date) map.get("fdStartTime");
		int startTime = fdStartTime.getHours() * 60 + fdStartTime.getMinutes();
		Date now = new Date();
		int nowTime = now.getHours() * 60 + now.getMinutes();
		if (nowTime < startTime) {
			json.put("message", "invalid invoke method time:" + startTime);
			return false;
		}
		String fdLatLng = request.getParameter("fdLatLng");
		String fdWifiName = request.getParameter("fdWifiName");
		String fdWifiMac = request.getParameter("fdWifiMac");

		if (StringUtil.isNull(fdLatLng) && StringUtil.isNotNull(fdWifiMac)) {
			List<SysAttendCategoryWifi> wifiConfigs = (List<SysAttendCategoryWifi>) map
					.get("fdWifiConfigs");
			if (wifiConfigs == null || wifiConfigs.isEmpty()) {
				json.put("message", "invalid parameter:fdLatLng=" + fdLatLng);
				return false;
			}
			boolean ret = false;
			for (SysAttendCategoryWifi wifi : wifiConfigs) {
				if (wifi.getFdMacIp().equalsIgnoreCase(fdWifiMac)) {
					ret = true;
					break;
				}
			}
			if (!ret) {
				json.put("message", "invalid parameter:fdWifiMac=" + fdWifiMac
						+ " or fdWifiName=" + fdWifiName);
				return false;
			}
		}
		return true;
	}

	private int getFdStatus(Map<String, Object> map) {
		Date fdSignTime = (Date) map.get("signTime");
		Boolean fdIsFlex = (Boolean) map.get("fdIsFlex");
		Integer fdFlexTime = (Integer) map.get("fdFlexTime");
		Integer fdLateTime = (Integer) map.get("fdLateTime");
		Date now = new Date();
		int nowTime = now.getHours() * 60 + now.getMinutes();
		int signTime = fdSignTime.getHours() * 60 + fdSignTime.getMinutes();
		if (fdIsFlex != null && fdIsFlex.booleanValue()) {
			// 弹性上下班
			signTime = signTime + fdFlexTime;
		} else {
			signTime = signTime + fdLateTime;
		}
		if (nowTime > signTime) {
			return AttendConstant.ATTENDMAIN_FDSTATUS[2];
		}
		return AttendConstant.ATTENDMAIN_FDSTATUS[1];
	}

	private boolean isSigned(String fdCategoryId,
			List<Map<String, Object>> signTimes) throws Exception {
		List<Map<String, Object>> recordList = getAttendMainServiceImp()
				.findList(fdCategoryId, new Date());
		// 排班班次渲染
		this.sysAttendCategoryService.doWorkTimesRender(signTimes, recordList);
		Map<String, Object> workTimeMap = signTimes.get(0);
		for (Map<String, Object> m : recordList) {
			if (sysAttendCategoryService.isSameWorkTime(workTimeMap,
					(String) m.get("fdWorkId"),
					(Integer) m.get("fdWorkType"),
					(String) m.get("fdWorkKey"))) {
				return true;
			}
		}
		return false;
	}

	private boolean validateParam(HttpServletRequest request, JSONObject json) {
		String fdCategoryId = request.getParameter("fdCategoryId");
		String fdLocation = request.getParameter("fdLocation");
		String fdLatLng = request.getParameter("fdLatLng");
		String fdWifiName = request.getParameter("fdWifiName");
		String fdWifiMac = request.getParameter("fdWifiMac");

		if (StringUtil.isNull(fdCategoryId)) {
			json.put("message", "invalid parameter:fdCategoryId");
			return false;
		}

		if (StringUtil.isNull(fdLatLng) && (StringUtil.isNull(fdWifiName)
				|| StringUtil.isNull(fdWifiMac))) {
			json.put("message", "invalid parameter:fdLatLng or fdWifiName");
			return false;
		}
		if (StringUtil.isNotNull(fdLatLng) && StringUtil.isNull(fdLocation)) {
			json.put("message", "invalid parameter:fdLocation");
			return false;
		}

		return true;
	}

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		return null;
	}

	private ISysAttendHisCategoryService sysAttendHisCategoryService;
	private ISysAttendHisCategoryService getSysAttendHisCategoryService(){
		if(sysAttendHisCategoryService ==null){
			sysAttendHisCategoryService = (ISysAttendHisCategoryService) SpringBeanUtil.getBean("sysAttendHisCategoryService");
		}
		return sysAttendHisCategoryService;
	}

	protected ISysAttendCategoryService getAttendCategoryServiceImp() {
		if (sysAttendCategoryService == null) {
			sysAttendCategoryService = (ISysAttendCategoryService) getBean(
					"sysAttendCategoryService");
		}
		return sysAttendCategoryService;
	}

	protected ISysAttendMainService getAttendMainServiceImp() {
		if (sysAttendMainService == null) {
			sysAttendMainService = (ISysAttendMainService) getBean(
					"sysAttendMainService");
		}
		return sysAttendMainService;
	}

	protected ISysNotifyMainCoreService getSysNotifyMainCoreService() {
		if (sysNotifyMainCoreService == null) {
			sysNotifyMainCoreService = (ISysNotifyMainCoreService) getBean(
					"sysNotifyMainCoreService");
		}
		return sysNotifyMainCoreService;
	}

	protected ISysAttendConfigService getSysAttendConfigService() {
		if (sysAttendConfigService == null) {
			sysAttendConfigService = (ISysAttendConfigService) getBean(
					"sysAttendConfigService");
		}
		return sysAttendConfigService;
	}
	public ISysAttendDeviceService getSysAttendDeviceService() {
		if (sysAttendDeviceService == null) {
			sysAttendDeviceService = (ISysAttendDeviceService) getBean(
					"sysAttendDeviceService");
		}
		return sysAttendDeviceService;
	}
}
