package com.landray.kmss.third.ding.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.OmsRelationModel;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.service.IThirdDingLdingORGService;
import com.landray.kmss.third.ding.util.DingHttpClientUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ThirdDingLdingORGServiceImp extends ExtendDataServiceImp
		implements IThirdDingLdingORGService, DingConstant {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingLdingORGServiceImp.class);

	private static boolean locked = false;
	private Map<String, OmsRelationModel> relationMap = null;
	private Map<String, String> elementMap = null;
	private Map<String, String> loginNameMap = null;
	private Map<String, String> mobileMap = null;
	private DingConfig config = null;
	private List<OmsRelationModel> addUpdateModels = null;
	private List<OmsRelationModel> delModels = null;
	private long synchTime = 0;
	private SysQuartzJobContext ctx = null;

	@Override
	public void synchro(SysQuartzJobContext context) throws Exception {
		String temp = "";
		long alltime = System.currentTimeMillis();
		try {
			if (locked) {
				temp = "异常任务同步已经在运行，当前任务中断...";
				logger.info(temp);
				context.logMessage(temp);
				return;
			}

			locked = true;
			config = DingConfig.newInstance();
			if ((StringUtil.isNotNull(config.getSyncSelection()) && "4".equals(config.getSyncSelection())) 
					|| "true".equals(config.getLdingEnabled())) {
				ctx = context;
				init();

				long time = System.currentTimeMillis();
				updateDept("dept");
				temp = "获取蓝钉部门映射信息总耗时(秒)：" + (System.currentTimeMillis() - time) / 1000;
				logger.debug(temp);
				context.logMessage(temp);

				time = System.currentTimeMillis();
				updateDept("person");
				temp = "获取蓝钉人员映射信息总耗时(秒)：" + (System.currentTimeMillis() - time) / 1000;
				logger.debug(temp);
				context.logMessage(temp);

				time = System.currentTimeMillis();
				handleDatas();
				if (synchTime != 0) {
					config.setLdingSynTime(synchTime + "");
					config.save();
				}
				temp = "处理蓝钉部门人员映射信息总耗时(秒)：" + (System.currentTimeMillis() - time) / 1000;
				logger.debug(temp);
				context.logMessage(temp);
			} else {
				temp = "钉钉集成-通讯录配置-同步选择-从蓝钉管理台获取人员对应关系未开启，不执行蓝钉映射数据的获取";
				logger.info(temp);
				context.logMessage(temp);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("同步蓝钉部门和人员映射信息发生异常：", ex);
		} finally {
			locked = false;
			relationMap = null;
			elementMap = null;
			loginNameMap = null;
			mobileMap = null;
			addUpdateModels = null;
			delModels = null;
			temp = "整个任务总耗时(秒)：" + (System.currentTimeMillis() - alltime) / 1000;
			logger.debug(temp);
			context.logMessage(temp);
		}
	}

	private IOmsRelationService omsRelationService;

	public IOmsRelationService getOmsRelationService() {
		if (omsRelationService == null) {
			omsRelationService = (IOmsRelationService) SpringBeanUtil.getBean("omsRelationService");
		}
		return omsRelationService;
	}

	private ISysOrgElementService sysOrgElementService;

	public ISysOrgElementService getSysOrgElementService() {
		if (sysOrgElementService == null) {
			sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
		}
		return sysOrgElementService;
	}
	
	private ISysOrgPersonService sysOrgPersonService;

	public ISysOrgPersonService getSysOrgPersonService() {
		if (sysOrgPersonService == null) {
			sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil.getBean("sysOrgPersonService");
		}
		return sysOrgPersonService;
	}

	private void init() throws Exception {
		elementMap = new HashMap<String, String>(3000);
		loginNameMap = new HashMap<String, String>(3000);
		mobileMap = new HashMap<String, String>(3000);
		relationMap = new HashMap<String, OmsRelationModel>(2000);
		addUpdateModels = new ArrayList<OmsRelationModel>(2000);
		delModels = new ArrayList<OmsRelationModel>(100);
		List list = getOmsRelationService().findList("1=1", null);
		for (int i = 0; i < list.size(); i++) {
			OmsRelationModel model = (OmsRelationModel) list.get(i);
			relationMap.put(model.getFdEkpId(), model);
		}
		List<SysOrgElement> eles = getSysOrgElementService().findList("fdOrgType in (1,2,8) and fdIsAvailable=1", null);
		for (SysOrgElement ele : eles) {
			elementMap.put(ele.getFdId(), ele.getFdId());
		}
		List<SysOrgPerson> pers = getSysOrgPersonService().findList("fdIsAvailable=1", null);
		for (SysOrgPerson per : pers) {
			loginNameMap.put(per.getFdLoginName(), per.getFdId());
			mobileMap.put(per.getFdMobileNo(), per.getFdId());
		}
	}

	private void updateDept(String type) throws Exception {
		String orgType = "dept".equals(type) ? "2" : "8";
		String timeStr = config.getLdingSynTime();
		if (StringUtil.isNull(timeStr)) {
			timeStr = "0";
			synchTime = 0;
		}
		long time = Long.parseLong(timeStr);
		String auth = config.getLdingUser() + ":" + config.getLdingPwd();
		String deptUrl = config.getLdingSynAddress();
		if (deptUrl.endsWith("/")) {
            deptUrl = deptUrl.substring(0, deptUrl.length() - 1);
        }
		if ("dept".equals(type)) {
			deptUrl += "/api/lding-console/ldingOrgRestService/dept?timestamp=" + time;
		} else {
			deptUrl += "/api/lding-console/ldingOrgRestService/person?timestamp=" + time;
		}
		JSONObject ret = DingHttpClientUtil.httpPostAuth(deptUrl, null, null, JSONObject.class, auth);
		if (ret != null && ret.getInt("errcode") == 0) {
			logger.debug("返回的数据为：" + ret.toString());
			if (ret.containsKey("datas")) {
				JSONArray jas = ret.getJSONArray("datas");
				JSONObject json = null;
				String dingId = null;
				String ekpId = null;
				String fdId = null;
				OmsRelationModel model = null;
				boolean dingIsDel = false;
				long count = 0;
				for (int i = 0; i < jas.size(); i++) {
					json = jas.getJSONObject(i);
					if (json.containsKey("time")) {
						if (synchTime < json.getLong("time")) {
							synchTime = json.getLong("time");
						}
					}
					if (json.containsKey("dingId")) {
						dingId = json.getString("dingId");
					}else{
						dingId = null;
					}
					if (json.containsKey("businessId")) {
						ekpId = json.getString("businessId");
					}else{
						ekpId = null;
					}
					if (json.containsKey("dingIsDel")) {
						dingIsDel = json.getBoolean("dingIsDel");
					}else{
						dingIsDel = false;
					}
					fdId = getLoginName(json);
					if (StringUtil.isNotNull(dingId) && StringUtil.isNotNull(ekpId) && (elementMap.containsKey(ekpId)||StringUtil.isNotNull(fdId))) {
						if(StringUtil.isNotNull(fdId)){
							ekpId = fdId;
						}
						if (relationMap.containsKey(ekpId)) {
							model = relationMap.get(ekpId);
							if (dingIsDel) {
								// 删除
								delModels.add(model);
							} else {
								// 更新
								model.setFdAppPkId(dingId);
								model.setFdType(orgType);
								addUpdateModels.add(model);
							}
						} else {
							// 新增
							model = new OmsRelationModel();
							model.setFdAppKey(getAppKey());
							model.setFdAppPkId(dingId);
							model.setFdEkpId(ekpId);
							model.setFdType(orgType);
							addUpdateModels.add(model);
						}
					} else {
						logger.warn(
								"非法数据，dingId:{},ekpId:{},elementMap.containsKey(ekpId):{},fdId:{},数据详情:{}",
								dingId, ekpId, elementMap.containsKey(ekpId),
								fdId, json.toString());
						++count;
					}
				}
				if ("dept".equals(type)) {
					logger.debug(
							"获取蓝钉映部门射信息总条数：" + jas.size() + ",非法数据条数：" + count + ",合法数据条数：" + (jas.size() - count));
					ctx.logMessage(
							"获取蓝钉映部门射信息总条数：" + jas.size() + ",非法数据条数：" + count + ",合法数据条数：" + (jas.size() - count));
				} else {
					logger.debug(
							"获取蓝钉映人员射信息总条数：" + jas.size() + ",非法数据条数：" + count + ",合法数据条数：" + (jas.size() - count));
					ctx.logMessage(
							"获取蓝钉映人员射信息总条数：" + jas.size() + ",非法数据条数：" + count + ",合法数据条数：" + (jas.size() - count));
				}
			}
		} else {
			logger.info("获取蓝钉映射信息异常,具体请查看日志信息");
			ctx.logMessage("获取蓝钉映射信息异常,具体请查看日志信息");
		}
	}

	private void handleDatas() throws Exception {
		if (addUpdateModels != null && addUpdateModels.size() > 0) {
			for (OmsRelationModel model : addUpdateModels) {
				getOmsRelationService().update(model);
			}
		}
		if (delModels != null && delModels.size() > 0) {
			for (OmsRelationModel model : delModels) {
				getOmsRelationService().delete(model);
			}
		}
	}

	private String getAppKey() {
		return StringUtil.isNull(DING_OMS_APP_KEY) ? "default" : DING_OMS_APP_KEY;
	}
	
	private String getLoginName(JSONObject json) {
		String fdId = null;
		if (json.containsKey("loginName")) {
			String loginName = json.getString("loginName");
			if (StringUtil.isNotNull(loginName) && loginNameMap.containsKey(loginName)) {
				fdId = loginNameMap.get(loginName);
			}
		} else if(json.containsKey("mobileNo")){
			String mobileNo = json.getString("mobileNo");
			if (StringUtil.isNotNull(mobileNo) && mobileMap.containsKey(mobileNo)) {
				fdId = mobileMap.get(mobileNo);
			}
		}
		return fdId;
	}
}
