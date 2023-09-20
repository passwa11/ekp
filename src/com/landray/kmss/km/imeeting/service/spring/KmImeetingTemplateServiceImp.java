package com.landray.kmss.km.imeeting.service.spring;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.km.imeeting.model.KmImeetingMapping;
import com.landray.kmss.km.imeeting.model.KmImeetingTemplate;
import com.landray.kmss.km.imeeting.service.IKmImeetingMappingService;
import com.landray.kmss.km.imeeting.service.IKmImeetingTemplateService;
import com.landray.kmss.km.imeeting.util.BoenUtil;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.interfaces.NotifyReplace;
import com.landray.kmss.sys.notify.util.SysNotifyConfigUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;

/**
 * 会议模板业务接口实现
 */
public class KmImeetingTemplateServiceImp extends BaseServiceImp implements
		IKmImeetingTemplateService {

	private static boolean locked = false;

	private ISysNotifyMainCoreService sysNotifyMainCoreService;

	public void setSysNotifyMainCoreService(
			ISysNotifyMainCoreService sysNotifyMainCoreService) {
		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
	}

	private IKmImeetingMappingService kmImeetingMappingService;

	public void setKmImeetingMappingService(IKmImeetingMappingService kmImeetingMappingService) {
		this.kmImeetingMappingService = kmImeetingMappingService;
	}

	private JSONObject buildTemplateJson(KmImeetingTemplate kmImeetingTemplate) throws Exception {
		JSONObject json = new JSONObject();
		json.put("thirdSystemUuid", kmImeetingTemplate.getFdId());
		json.put("typeName", kmImeetingTemplate.getFdName());
		json.put("typeCode", kmImeetingTemplate.getFdId());
		json.put("dataFrom", "02");
		JSONObject topOrg = BoenUtil.getTopOrg();
		json.put("organizationId", topOrg.get("topOrgId"));
		return json;
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		KmImeetingTemplate kmImeetingTemplate = (KmImeetingTemplate) modelObj;
		String fdId = super.add(modelObj);
		// 如果铂恩会议集成开启，则同步到铂恩会议系统
		if (BoenUtil.isBoenEnable()) {
			addBoen(kmImeetingTemplate, true);
		}
		return fdId;
	}

	public void addBoen(KmImeetingTemplate kmImeetingTemplate, Boolean isAddOpt) throws Exception {
		try {
			JSONObject tempJson = buildTemplateJson(kmImeetingTemplate);
			String url = BoenUtil.getBoenUrl() + "/openapi/meetTypes/";
			String result = BoenUtil.sendPost(url, tempJson.toString());
			if (StringUtil.isNotNull(result)) {
				JSONObject res = JSONObject.fromObject(result);
				if (res.getInt("status") == 201) {
					String fdTypeId = res.get("data").toString();
					KmImeetingMapping kim = new KmImeetingMapping();
					kim.setFdId(kmImeetingTemplate.getFdId());
					kim.setFdModelId(kmImeetingTemplate.getFdId());
					kim.setFdModelName(KmImeetingTemplate.class.getName());
					kim.setFdThirdSysId(fdTypeId);
					kmImeetingMappingService.add(kim);
				} else {
					if (isAddOpt) {
						int status = res.getInt("status");
						String message = (String) res.get("message");
						throw new RuntimeException("code:" + status + ",msg:" + message);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	@Override
	public void update(IBaseModel modelObj) throws Exception {
		try {
			KmImeetingTemplate kmImeetingTemplate = (KmImeetingTemplate) modelObj;
			super.update(modelObj);
			// 如果铂恩会议集成开启，则同步到铂恩会议系统
			if (BoenUtil.isBoenEnable()) {
				JSONObject tempJson = buildTemplateJson(kmImeetingTemplate);
				KmImeetingMapping kim = (KmImeetingMapping) kmImeetingMappingService
						.findByPrimaryKey(kmImeetingTemplate.getFdId(), KmImeetingMapping.class.getName(), true);
				if (kim == null) {
					kim = kmImeetingMappingService.findByModelId(kmImeetingTemplate.getFdId(),
							KmImeetingTemplate.class.getName());
				}
				if (kim != null) {
					if (kmImeetingTemplate.getFdIsAvailable()) {
						String url = BoenUtil.getBoenUrl() + "/openapi/meetTypes/"
								+ kmImeetingTemplate.getFdId();
						String result = BoenUtil.sendPut(url, tempJson.toString());
						if (StringUtil.isNotNull(result)) {
							JSONObject res = JSONObject.fromObject(result);
							if (res.getInt("status") == 200) {
								String fdTypeId = res.get("data").toString();
								// 如果存在且id不一致，则更新
								if (!fdTypeId.equals(kim.getFdThirdSysId())) {
									kim.setFdThirdSysId(fdTypeId);
									kmImeetingMappingService.update(kim);
								}
							} else {
								int status = res.getInt("status");
								String message = (String) res.get("message");
								throw new RuntimeException("code:" + status + ",msg:" + message);
							}
						}
					} else {
						deleteBoen(kmImeetingTemplate, kim);
					}
				} else {
					addBoen(kmImeetingTemplate, false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
    public void delete(IBaseModel modelObj) throws Exception {
		KmImeetingTemplate kmImeetingTemplate = (KmImeetingTemplate) modelObj;
		try {
			super.delete(kmImeetingTemplate);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		KmImeetingMapping kmImeetingMapping = (KmImeetingMapping) kmImeetingMappingService
				.findByPrimaryKey(kmImeetingTemplate.getFdId(), KmImeetingMapping.class.getName(), true);
		if (kmImeetingMapping == null) {
			kmImeetingMapping = kmImeetingMappingService.findByModelId(kmImeetingTemplate.getFdId(),
					KmImeetingTemplate.class.getName());
		}
		// 开启了铂恩对接
		if (BoenUtil.isBoenEnable()) {
			if (kmImeetingMapping != null) {
				deleteBoen(kmImeetingTemplate, kmImeetingMapping);
			}
		}
	}

	public void deleteBoen(KmImeetingTemplate kmImeetingTemplate, KmImeetingMapping kmImeetingMapping)
			throws Exception {
		try {
			String url = BoenUtil.getBoenUrl() + "/openapi/meetTypes/" + kmImeetingTemplate.getFdId()
					+ "?dataFrom=02";
			String result = BoenUtil.sendDelete(url);
			if(StringUtil.isNotNull(result)){
				JSONObject res = JSONObject.fromObject(result);
				if (res.getInt("status") == 200) {
					kmImeetingMappingService.delete(kmImeetingMapping);
				} else {
					int status = res.getInt("status");
					String message = (String) res.get("message");
					throw new RuntimeException("code:" + status + ",msg:" + message);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 催办会议组织人召开会议定时任务
	 */
	@Override
	public void doNotifyEmceeToDo(SysQuartzJobContext context) throws Exception {
		if (locked) {
			return;
		}
		locked = true;
		try {
			Date currentDate = new Date();
			KmImeetingTemplate kmImeetingTemplate = null;
			// 周期类型为不定期不催办
			List<?> results = this
					.findList(
							"kmImeetingTemplate.fdPeriodType <> 5 and kmImeetingTemplate.fdHoldTime is not null",
							"");
			for (int i = 0; i < results.size(); i++) {
				kmImeetingTemplate = (KmImeetingTemplate) results.get(i);
				// 没有会议组织人不催办
				if (kmImeetingTemplate.getFdEmcee() == null) {
					continue;
				}
				if (isTodyNotify(currentDate,
						kmImeetingTemplate.getFdPeriodType(),
						kmImeetingTemplate.getFdHoldTime(),
						kmImeetingTemplate.getFdNotifyDay())) {
					sendNotifyEmceeTodo(kmImeetingTemplate);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			locked = false;
		}
	}

	private boolean isTodyNotify(Date currentDate, int periodType,
			String holdTime, int notifyDay) {
		// 周期类型为不定期不催办
		if (periodType == 5 || StringUtil.isNull(holdTime)) {
			return false;
		}
		String[] arrStr = holdTime.split("-");
		int a0 = Integer.parseInt(arrStr[0]);
		int a1 = Integer.parseInt(arrStr[1]);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.DATE, notifyDay);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		switch (periodType) {
		case 1:
			a1 = getMaxDayInMonth(calendar, a1);
			if ((calendar.get(Calendar.MONTH) + 1) == a0
					&& calendar.get(Calendar.DATE) == a1) {
				return true;
			}
			break;
		case 2:
			a1 = getMaxDayInMonth(calendar, a1);
			int[] quarterMonth = getQuarterMonth(a0);
			for (int m : quarterMonth) {
				if ((calendar.get(Calendar.MONTH) + 1) == m
						&& calendar.get(Calendar.DATE) == a1) {
					return true;
				}
			}
			break;
		case 3:
			a1 = getMaxDayInMonth(calendar, a1);
			if (calendar.get(Calendar.DATE) == a1) {
				return true;
			}
			break;
		case 4:
			if (calendar.get(Calendar.DAY_OF_WEEK) == a1) {
				return true;
			}
			break;
		default:
		}
		return false;
	}

	/**
	 * 发送召开会议催办
	 */
	private void sendNotifyEmceeTodo(KmImeetingTemplate kmImeetingTemplate)
			throws Exception {
		// 获取上下文
		NotifyContext notifyContext = sysNotifyMainCoreService
				.getContext("km-imeeting:kmImeetingTemplate.notify.emcee");
		// 设置链接
		notifyContext
				.setLink("/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=add&fdTemplateId="
						+ kmImeetingTemplate.getFdId());
		// 获取通知方式
		notifyContext
				.setNotifyType(SysNotifyConfigUtil.getNotifyDefaultValue());
		// 设置发布类型为"待办"
		notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
		List<SysOrgElement> notifiers = new ArrayList<SysOrgElement>();
		notifiers.add(kmImeetingTemplate.getFdEmcee());
		// 设置发布通知人
		notifyContext.setNotifyTarget(notifiers);
		// HashMap<String, String> replaceMap = new HashMap<String, String>();
		NotifyReplace notifyReplace = new NotifyReplace();
		// replaceMap.put("km-imeeting:kmImeetingTemplate.fdName",
		// kmImeetingTemplate.getFdName());
		// replaceMap.put(
		// "km-imeeting:period.type",
		// getPeriodInfo(kmImeetingTemplate.getFdPeriodType(),
		// kmImeetingTemplate.getFdHoldTime()));
		notifyReplace.addReplaceText("km-imeeting:kmImeetingTemplate.fdName",
				kmImeetingTemplate.getFdName());
		notifyReplace.addReplaceText(
				"km-imeeting:period.type",
				getPeriodInfo(kmImeetingTemplate.getFdPeriodType(),
						kmImeetingTemplate.getFdHoldTime()));
		sysNotifyMainCoreService.sendNotify(kmImeetingTemplate, notifyContext,
				notifyReplace);
	}

	/**
	 * 生成周期性信息
	 */
	private String getPeriodInfo(int periodType, String holdTime) {
		String peroidInfo = "";
		// 周期类型为不定期
		if (periodType == 5 || StringUtil.isNull(holdTime)) {
			return peroidInfo;
		}
		String[] arrStr = holdTime.split("-");
		switch (periodType) {
		case 1:
			peroidInfo += ResourceUtil.getString("calendar.perYear",
					"km-imeeting");
			peroidInfo += ResourceUtil.getString("calendar.day.name")
					.replace("{0}", clearZero(arrStr[0]))
					.replace("{1}", clearZero(arrStr[1]));
			break;
		case 2:
			String[] monthNames = ResourceUtil
					.getString("calendar.quarterMonth.names")
					.split(",");
			peroidInfo += ResourceUtil.getString("calendar.perQuarter",
					"km-imeeting");
			peroidInfo += monthNames[Integer.parseInt(arrStr[0]) - 1]
					+ clearZero(arrStr[1])
					+ ResourceUtil.getString("resource.period.type.day.name");
			break;
		case 3:
			peroidInfo += ResourceUtil.getString("calendar.perMonth",
					"km-imeeting");
			peroidInfo += clearZero(arrStr[1])
					+ ResourceUtil.getString("resource.period.type.day.name");
			break;
		case 4:
			String[] weeks = ResourceUtil.getString("calendar.week.names")
					.split(",");
			peroidInfo += ResourceUtil.getString("calendar.perWeek",
					"km-imeeting");
			peroidInfo += weeks[Integer.parseInt(arrStr[1]) - 1];
			break;
		default:
		}
		return peroidInfo;
	}

	/**
	 * 天数超过月的最大天数，取该月的最大天数
	 * 
	 * @param calendar
	 * 
	 * @param day
	 * 
	 * @return
	 */
	private int getMaxDayInMonth(Calendar calendar, int day) {
		if (day > calendar.getActualMaximum(Calendar.DATE)) {
			return calendar.getActualMaximum(Calendar.DATE);
		}
		return day;
	}

	/**
	 * 获取每季度的第几月 例：quarter为1，返回[1, 4, 7, 10]
	 * 
	 * @param quarter
	 *            第几月
	 * @return
	 */
	private int[] getQuarterMonth(int quarter) {
		int[] tmp = new int[4];
		for (int i = 1; i < tmp.length + 1; i++) {
			tmp[i - 1] = (3 * (i - 1) + 1) + (quarter - 1);
		}
		return tmp;
	}

	private String clearZero(String str) {
		if (StringUtil.isNotNull(str) && "0".equals(str.substring(0, 1))) {
			return str.substring(1);
		}
		return str;
	}

	/**
	 * 新建文档获取模板时，过滤不可用的模板
	 */
	@Override
	public List findValue(HQLInfo hqlInfo) throws Exception {
		if (StringUtil.isNull(hqlInfo.getModelName())
				|| hqlInfo.getModelName().contains("KmImeetingTemplate")) {
			String whereBlock = hqlInfo.getWhereBlock();
			if (StringUtil.isNotNull(whereBlock)) {
				hqlInfo.setWhereBlock(whereBlock
						+ " and (kmImeetingTemplate.fdIsAvailable = :fdIsAvailable or kmImeetingTemplate.fdIsAvailable is null)");
			} else {
				hqlInfo.setWhereBlock(
						"kmImeetingTemplate.fdIsAvailable = :fdIsAvailable or kmImeetingTemplate.fdIsAvailable is null");
			}
			hqlInfo.setParameter("fdIsAvailable", true);
		}
		return super.findValue(hqlInfo);
	}

	@Override
	public void addSyncToBoen() throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("kmImeetingTemplate.fdIsAvailable = :fdIsAvailable");
		hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
		List<KmImeetingTemplate> l = this.findList(hqlInfo);
		for (KmImeetingTemplate kmImeetingTemplate : l) {
			KmImeetingMapping kmImeetingMapping = (KmImeetingMapping) kmImeetingMappingService
					.findByPrimaryKey(kmImeetingTemplate.getFdId(), KmImeetingMapping.class.getName(), true);
			if (kmImeetingMapping == null) {
				kmImeetingMapping = kmImeetingMappingService.findByModelId(kmImeetingTemplate.getFdId(),
						KmImeetingTemplate.class.getName());
			}
			// kmImeetingMapping为空，说明没有同步过
			if (kmImeetingMapping == null) {
				addBoen(kmImeetingTemplate, false);
			}
		}
	}
}
