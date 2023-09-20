package com.landray.kmss.third.ding.provider;

import java.util.*;

import net.sf.json.JSONObject;
import org.slf4j.Logger;

import com.landray.kmss.km.calendar.cms.interfaces.ICMSProvider;
import com.landray.kmss.km.calendar.cms.interfaces.SyncroCommonCal;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.OmsRelationModel;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;


public class DingCalendarProvider implements ICMSProvider {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DingCalendarProvider.class);

	private IOmsRelationService omsRelationService = null;

	public IOmsRelationService getOmsRelationService() {
		if (omsRelationService == null) {
			omsRelationService = (IOmsRelationService) SpringBeanUtil.getBean("omsRelationService");
		}
		return omsRelationService;
	}

	private DingCalendarProvider dingCalendarProvider = null;

	public DingCalendarProvider getDingCalendarProvider() {
		if (dingCalendarProvider == null) {
			dingCalendarProvider = (DingCalendarProvider) SpringBeanUtil.getBean("dingCalendarProvider");
		}
		return dingCalendarProvider;
	}

	private IDingCalendarApiProvider getDingCalendarApiProviderImp() {
		String version = DingConfig.newInstance().getCalendarApiVersion();
		if ("V2".equals(version)) {
			return (IDingCalendarApiProvider) SpringBeanUtil
				.getBean("dingCalendarApiProviderImpV2");
		} else if("V3".equals(version)){
			return (IDingCalendarApiProvider) SpringBeanUtil
					.getBean("dingCalendarApiProviderImpV3");
		}else {
			return (IDingCalendarApiProvider) SpringBeanUtil
					.getBean("dingCalendarApiProviderImpV1");
		}
	}

	private boolean isV3version(){
		return "V3".equals(DingConfig.newInstance().getCalendarApiVersion());
	}

	private static Map<String, String> omsMap = new HashMap<String, String>();

	private void init() throws Exception {
		omsMap.clear();
		List<OmsRelationModel> omsList = getOmsRelationService().findList(null, null);
		for (OmsRelationModel main : omsList) {
			omsMap.put(main.getFdEkpId(), main.getFdAppPkId());
		}
	}

	@Override
	public ICMSProvider getNewInstance(String personId) throws Exception {
		return getDingCalendarProvider();
	}

	@Override
	public String addCalElement(String personId, SyncroCommonCal syncroCommonCal) throws Exception {
		String rtn = null;
		if(StringUtil.isNotNull(syncroCommonCal.getRecurrentStr())){
			logger.debug("重复日程不进行同步...");
			return rtn;
		}
		if(isV3version()){
			return getDingCalendarApiProviderImp().addCalendar(null, personId,
					syncroCommonCal);
		}

		if (StringUtil.isNull(personId)) {
            personId = syncroCommonCal.getPersonId();
        }
		String duserid = omsMap.get(personId);
		if (StringUtil.isNotNull(duserid)) {
			String token = DingUtils.getDingApiService().getAccessToken();
			rtn = getDingCalendarApiProviderImp().addCalendar(token, duserid,
					syncroCommonCal);
		}
		return rtn;
	}

	@Override
	public boolean updateCalElement(String personId,
			SyncroCommonCal syncroCommonCal) {
		if(StringUtil.isNotNull(syncroCommonCal.getRecurrentStr())){
			logger.debug("重复日程不进行同步...");
			return true;
		}
		if(isV3version()){
			try {
				return getDingCalendarApiProviderImp().updateCalElement(null,
						personId, null, syncroCommonCal);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}else{
			if (StringUtil.isNull(personId)) {
                personId = syncroCommonCal.getPersonId();
            }
			String duserid = omsMap.get(personId);
			if (StringUtil.isNotNull(duserid)) {
				try {
					String token = DingUtils.getDingApiService().getAccessToken();
					return getDingCalendarApiProviderImp().updateCalElement(token,
							personId, duserid, syncroCommonCal);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			} else {
				logger.error("找不到该用户的映射关系，" + personId);
			}
		}
		return false;
	}

	@Override
	public boolean deleteCalElement(String personId, String uuid) throws Exception {
		if(isV3version()){
			return getDingCalendarApiProviderImp().delCalendar(null, personId,
					uuid);
		}
		boolean rtn = true;
		String duserid = omsMap.get(personId);
		if (StringUtil.isNotNull(duserid)) {
			String token = DingUtils.getDingApiService().getAccessToken();
			rtn = getDingCalendarApiProviderImp().delCalendar(token, duserid,
					uuid);
		}
		return rtn;
	}

	public void a() throws Exception {
		/*MultiValueMap<String, String> headers=new LinkedMultiValueMap<>();
		headers.set("Content-Type","application/json");
		headers.set("x-acs-dingtalk-access-token","9aaee0f4f68335c19a0fea83a8f750a5");*/
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("isDone",false);
		paramMap.put("isRecycled",false);
		JSONObject result = DingUtils.getDingApiService().newTodo("uy85nETCHfRkXdyFwDztvAiEiE", paramMap);
		System.out.println(result.toString());
	}

	@Override
	public List<SyncroCommonCal> getCalElements(String personId, Date date) throws Exception {
		List<SyncroCommonCal> syncroCommonCals = new ArrayList<>();
		return syncroCommonCals;
	}

	@Override
	public List<SyncroCommonCal> getAddedCalElements(String personId, Date date) throws Exception {
		// 从EKP同步到钉钉，暂时返回为空(增量/新增)
		if(isV3version()){
			return getDingCalendarApiProviderImp().getAddedCalElements(personId,date);
		}
		return new ArrayList<SyncroCommonCal>();

	}

	@Override
	public List<SyncroCommonCal> getDeletedCalElements(String personId, Date date) throws Exception {
		// 从EKP同步到钉钉，暂时返回为空(增量/删除)
		if(isV3version()){
			return getDingCalendarApiProviderImp().getDeletedCalElements(personId,date);
		}
		return new ArrayList<SyncroCommonCal>();
	}

	@Override
	public List<SyncroCommonCal> getUpdatedCalElements(String personId, Date date) throws Exception {
		// 从EKP同步到钉钉，暂时返回为空(增量/更新)
		List<SyncroCommonCal> syncroCommonCals = new ArrayList<SyncroCommonCal>();
		return syncroCommonCals;
	}

	@Override
	public String getCalType() {
		return "event";
	}

	@Override
	public List<String> getPersonIdsToSyncro() {
		String sql = "select fd_id from sys_org_element e where fd_is_available = 1 and fd_org_type = '8' and "
				+ "fd_id in (select distinct fd_ekp_id from oms_relation_model m where m.fd_ekp_id = e.fd_id)";
		List<String> fdIds = new ArrayList<String>();
		try {
			fdIds = getOmsRelationService().getBaseDao().getHibernateSession().createNativeQuery(sql).list();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return fdIds;
	}

	@Override
	public boolean isNeedSyncro(String personId) {
		return true;
	}

	@Override
	public boolean isSynchroEnable() throws Exception {
		DingConfig config = new DingConfig();
		String enable = config.getDingScheduleEnabled();
		if ("true".equals(enable)) {
			if(!isV3version()){
				init();
			}
			return true;
		}
		return false;
	}





}
