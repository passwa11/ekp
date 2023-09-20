package com.landray.kmss.third.ding.oms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.third.ding.util.DingUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.organization.webservice.out.ISysSynchroGetOrgWebService;
import com.landray.kmss.sys.organization.webservice.out.SysSynchroGetOrgInfoContext;
import com.landray.kmss.sys.organization.webservice.out.SysSynchroOrgResult;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.OmsRelationModel;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class SynchroWsOrg2DingImp implements SynchroOrg2Ding,DingConstant {
	private static final String TYPE_ORG_KEY = "org";
	private static final String TYPE_DEPT_KEY = "dept";
	private static final String TYPE_PERSON_KEY = "person";

	private static final int DING_ROOT_DEPT_ID = 1;

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SynchroWsOrg2DingImp.class);

	private SysQuartzJobContext jobContext = null;

	private static boolean locked = false;

	private int preCount = 1000;

	private DingOmsConfig dingOmsConfig = null;
	private String lastUpdateTime = null;

	private List<Object> rootOrgChildren = null;
	private Map<String, String> relationMap = new HashMap<String, String>();

	private DingApiService dingApiService = null;

	private ISysSynchroGetOrgWebService sysSynchroGetOrgWebService;

	public ISysSynchroGetOrgWebService getSysSynchroGetOrgWebService() {
		if (sysSynchroGetOrgWebService == null) {
			sysSynchroGetOrgWebService = (ISysSynchroGetOrgWebService) SpringBeanUtil
					.getBean("sysSynchroGetOrgWebService");
		}
		return sysSynchroGetOrgWebService;
	}

	private IOmsRelationService omsRelationService;

	public void setOmsRelationService(IOmsRelationService omsRelationService) {
		this.omsRelationService = omsRelationService;
	}

	@Override
    public void triggerSynchro(SysQuartzJobContext context) {
		this.jobContext = context;

		if (locked) {
            return;
        }
		locked = true;
		try {
			long time = System.currentTimeMillis();
			init();

			List<JSONArray> allElements = getSyncOrgElements();
			logCount(allElements);
			filterOrgChildren(allElements);
			
			handleDept(allElements);
			handlePerson(allElements);

			terminate();
			if (logger.isDebugEnabled()) {
				logger.debug("cost time:"
						+ ((System.currentTimeMillis() - time) / 1000) + " s");
			}
			context.logMessage("cost time:"
					+ ((System.currentTimeMillis() - time) / 1000) + " s");
		} catch (Exception ex) {
			ex.printStackTrace();
			if (context != null) {
				context.logError(ex);
			}
		} finally {
			locked = false;
			relationMap.clear();
			rootOrgChildren = null;
		}
	}

	private boolean exist(JSONObject element) {
		if (rootOrgChildren == null) {
			return true;
		}
		return rootOrgChildren.contains(element.getString("id"));
	}

	private String updateDept(JSONObject element) throws Exception {
		JSONObject dept = new JSONObject();
		dept.accumulate("name", DingUtil.getString(element.getString("name"), 64));
		String parentId = "" + DING_ROOT_DEPT_ID;
		if (element.get("parent") != null) {
			parentId = relationMap.get(element.getString("parent"));
		}
		if (StringUtil.isNull(parentId)) {
			parentId = "" + DING_ROOT_DEPT_ID;
		}
		dept.accumulate("parentid", parentId);
		if (element.get("order") != null) {
			dept.accumulate("order", element.getString("order"));
		}
		dept.accumulate("id", relationMap.get(element.getString("id")));
		return dingApiService.departUpdate(dept);
	}

	private void handleDept(List<JSONArray> allElements) throws Exception {
		// 先增加所有部门，且先挂到根机构下
		int n = 0;
		String logInfo = null;
		for (JSONArray block : allElements) {
			for (int i = 0; i < block.size(); i++) {
				JSONObject element = block.getJSONObject(i);
				if (!exist(element)) {
					continue;
				}
				if (!isOmsRootOrg(element.getString("id"))) {
					continue;
				}
				if (TYPE_ORG_KEY.equals(element.getString("type"))
						|| TYPE_DEPT_KEY.equals(element.getString("type"))) {
					if (element.getBoolean("isAvailable")) {
						if (!relationMap.keySet().contains(
								element.getString("id"))) {

							JSONObject dept = new JSONObject();
							String name = DingUtil.getString(element.getString("name"), 64);
							dept.accumulate("name", name);
							dept.accumulate("parentid", DING_ROOT_DEPT_ID);
							if (element.get("order") != null) {
								dept.accumulate("order", element
										.getString("order"));
							}
							n++;
							JSONObject ret = dingApiService.departCreate(dept);
							if (ret.getInt("errcode") == 0) {
								Integer id = ret.getInt("id");
								logInfo = "增加部门到钉钉 "
										+ element.getString("name") + ", "
										+ element.getString("id") + ",钉钉对应ID:"
										+ id;

								addRelation(element, "" + id.intValue());
							} else {
								logInfo = "增加部门到钉钉 "
										+ element.getString("name") + ", "
										+ element.getString("id") + "失败,出错信息："
										+ ret.getString("errmsg");
							}
							logger.error(logInfo);
							jobContext.logMessage(logInfo);
						}
					}
				}
			}
		}

		logInfo = "增加部门同步到钉钉的个数为:" + n + "条";
		logger.debug(logInfo);
		jobContext.logMessage(logInfo);

		// 更新所有部门，主要是更新关系
		n = 0;
		for (JSONArray block : allElements) {
			for (int i = 0; i < block.size(); i++) {
				JSONObject element = block.getJSONObject(i);
				if (!exist(element)) {
					continue;
				}
				if (!isOmsRootOrg(element.getString("id"))) {
					continue;
				}
				if (TYPE_ORG_KEY.equals(element.getString("type"))
						|| TYPE_DEPT_KEY.equals(element.getString("type"))) {
					if (element.getBoolean("isAvailable")) {
						n++;
						if (element.get("parent") == null) {
							// 无父部们，则挂到根部门上1
							updateDept(element);
							continue;
						}
						if (!relationMap.keySet().contains(
								element.getString("parent"))) {
							logInfo = "警告：从关系中找不到钉钉所对应的父ID，则移到根部门下，当前部门: "
									+ element.getString("name") + " "
									+ element.getString("id") + ",父ID："
									+ element.getString("parent");
							logInfo += " ,retmsg:" + updateDept(element);
							logger.warn(logInfo);
							jobContext.logMessage(logInfo);
						} else {
							logInfo = "新增后或更新钉钉的父部门ID ,"
									+ element.getString("name")
									+ ","
									+ element.getString("id")
									+ ",对应父钉钉ID:"
									+ relationMap.get(element
											.getString("parent"));
							logInfo += " ,retmsg:" + updateDept(element);
							logger.debug(logInfo);
							jobContext.logMessage(logInfo);
						}
					}
				}
			}
		}

		logInfo = "修改部门同步到钉钉的个数为:" + n + "条";
		logger.debug(logInfo);
		jobContext.logMessage(logInfo);

		// 对无效部门进行删除
		n = 0;
		for (JSONArray block : allElements) {
			for (int i = 0; i < block.size(); i++) {
				JSONObject element = block.getJSONObject(i);
				if (TYPE_ORG_KEY.equals(element.getString("type"))
						|| TYPE_DEPT_KEY.equals(element.getString("type"))) {
					if (!element.getBoolean("isAvailable")) {
						n++;
						if (!relationMap.keySet().contains(
								element.getString("id"))) {

							logInfo = "警告：从关系中找不到钉钉对应的ID，当前部门 ："
									+ element.getString("name") + ","
									+ element.getString("id") + ",删除忽略";
							logger.warn(logInfo);
							jobContext.logMessage(logInfo);

						} else {

							logInfo = "删除钉钉中的部门 " + element.getString("name")
									+ ", " + element.getString("id")
									+ ",钉钉中ID："
									+ relationMap.get(element.getString("id"));
							logInfo += " ,retmsg:"
									+ dingApiService.departDelete(relationMap
											.get(element.getString("id")));
							logger.debug(logInfo);
							jobContext.logMessage(logInfo);

							omsRelationService.deleteByKey(element
									.getString("id"), getAppKey());
							relationMap.remove(element.getString("id"));
						}
					}
				}
			}
		}

		logInfo = "删除部门同步到钉钉的个数为:" + n + "条";
		logger.debug(logInfo);
		jobContext.logMessage(logInfo);

	}

	private JSONObject getUser(JSONObject element) {
		JSONObject person = new JSONObject();
		person.accumulate("userid", element.getString("loginName"));
		person.accumulate("name", element.getString("name"));
		JSONArray depts = new JSONArray();
		String parentId = relationMap.get(element.getString("parent"));
		if (StringUtil.isNull(parentId)) {
			parentId = "" + DING_ROOT_DEPT_ID;
		}
		depts.add(parentId);
		person.accumulate("department", depts);
		if (element.get("mobileNo") != null) {
			person.accumulate("mobile", element.getString("mobileNo"));
		}
		if (element.get("email") != null) {
			person.accumulate("email", element.getString("email"));
		}
		return person;
	}

	private void handlePerson(List<JSONArray> allElements) throws Exception {
		int n = 0;
		String logInfo = null;
		for (JSONArray block : allElements) {
			for (int i = 0; i < block.size(); i++) {
				JSONObject element = block.getJSONObject(i);
				if (TYPE_PERSON_KEY.equals(element.getString("type"))) {
					if (element.getBoolean("isAvailable")) {
						if (!exist(element)) {
							continue;
						}
						n++;
						if (element.get("parent") == null) {
							// 无父部们，则挂到根部门上1
							continue;
						}

						if (!relationMap.keySet().contains(
								element.getString("id"))) {
							logInfo = "增加个人到钉钉 "
									+ element.getString("name")
									+ ", "
									+ element.getString("id")
									+ ",对应父钉钉ID:"
									+ relationMap.get(element
											.getString("parent"));
							JSONObject ret = dingApiService
									.userCreate(getUser(element));
							if (ret.getInt("errcode") == 0) {
								logInfo += " ,created";
								addRelation(element, element
										.getString("loginName"));
							} else {
								logInfo += "失败,出错信息：" + ret.getString("errmsg");
							}
							logger.error(logInfo);
							jobContext.logMessage(logInfo);
						} else {
							if (!element.getString("loginName").equals(
									relationMap.get(element.getString("id")))) {
								// 个人loginName有变化
								// 用旧的loginName去删除钉钉中的用户
								String oldLoginName = relationMap.get(element
										.getString("id"));
								logInfo = "删除钉钉中旧的用户 "
										+ element.getString("name") + ","
										+ element.getString("id")
										+ ",loginName:" + oldLoginName;
								logInfo += " ,"
										+ dingApiService
												.userDelete(oldLoginName);
								logInfo += "\n";
								omsRelationService.deleteByKey(oldLoginName,
										getAppKey());
								relationMap.remove(element.getString("id"));
								// 增加新的钉钉用户
								logInfo += "增加新的钉钉用户 "
										+ element.getString("name") + " "
										+ element.getString("id")
										+ ",loginName:"
										+ element.getString("loginName");
								JSONObject ret = dingApiService
										.userCreate(getUser(element));
								if (ret.getInt("errcode") == 0) {
									logInfo += " ,created";
									addRelation(element, element
											.getString("loginName"));
								} else {
									logInfo += "失败,出错信息："
											+ ret.getString("errmsg");
								}
								logger.error(logInfo);
								jobContext.logMessage(logInfo);
							} else {
								logInfo = "更新个人到钉钉 "
										+ element.getString("name")
										+ ",loginName:"
										+ element.getString("loginName")
										+ ", "
										+ element.getString("id")
										+ ",对应父钉钉ID:"
										+ relationMap.get(element
												.getString("parent"));
								logInfo += " ,retmsg:"
										+ dingApiService
												.userUpdate(getUser(element));
								logger.debug(logInfo);
								jobContext.logMessage(logInfo);
							}
						}
					} else {
						if (!relationMap.keySet().contains(
								element.getString("id"))) {
							logInfo = "警告：从关系中找不到钉钉对应的ID，当前个人 "
									+ element.getString("name") + ", "
									+ ",loginName:"
									+ element.getString("loginName") + ",id:"
									+ element.getString("id") + ",删除忽略";
							logger.warn(logInfo);
							jobContext.logMessage(logInfo);
						} else {
							logInfo = "删除钉钉中的个人ID " + element.getString("name")
									+ " " + element.getString("id") + ","
									+ relationMap.get(element.getString("id"));
							logInfo += " ,retMsg:"
									+ dingApiService.userDelete(element
											.getString("id"));
							logger.debug(logInfo);
							jobContext.logMessage(logInfo);

							omsRelationService.deleteByKey(element
									.getString("id"), getAppKey());
							relationMap.remove(element.getString("id"));

						}
					}
				}
			}
		}

		logInfo = "同步个人到钉钉的个数为:" + n + "条";
		logger.debug(logInfo);
		jobContext.logMessage(logInfo);

	}

	private void addRelation(JSONObject element, String appPkId)
			throws Exception {
		OmsRelationModel model = new OmsRelationModel();
		model.setFdEkpId(element.getString("id"));
		model.setFdAppPkId(appPkId);
		model.setFdAppKey(getAppKey());
		omsRelationService.add(model);
		relationMap.put(model.getFdEkpId(), model.getFdAppPkId());
	}

	private String getAppKey() {
		return StringUtil.isNull(DING_OMS_APP_KEY) ? "default"
				: DING_OMS_APP_KEY;
	}

	private boolean isOmsRootOrg(String rootId) {
		if ("true".equals(DingConfig.newInstance().getDingOmsRootFlag())) {
			return true;
		} else if (rootId.equals(DingConfig.newInstance().getDingOrgId())) {
			return false;
		}
		return true;
	}

	private void logCount(List<JSONArray> allElements) {
		int count = 0;
		for (JSONArray block : allElements) {
			count += block.size();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("本次从EKP获取同步组织机构的记录数为 " + count + " 条");
		}
		jobContext.logMessage("本次从EKP获取同步组织机构的记录数为 " + count + " 条");
	}

	private void filterOrgChildren(List<JSONArray> allElements)throws Exception{
		rootOrgChildren = new ArrayList<Object>();
		Map<String,String> pidMap = new HashMap<String,String>();
		List<String> deptIds = new ArrayList<String>();
		for (JSONArray block : allElements) {
			for (int i = 0; i < block.size(); i++) {
				JSONObject element = block.getJSONObject(i);
				if (TYPE_ORG_KEY.equals(element.getString("type"))
						|| TYPE_DEPT_KEY.equals(element.getString("type"))) {
					deptIds.add(element.getString("id"));
					if (element.get("parent") != null) {
						pidMap.put(element.getString("id"),element.getString("parent"));
					}else{
						pidMap.put(element.getString("id"),"");
					}
				}
			}
		}
		
		List<String> avaliableDeptIds = new ArrayList<String>();
		for(String deptId:deptIds){
			if (StringUtil.isNotNull(DingConfig.newInstance().getDingOrgId())) {
				String parentId = pidMap.get(deptId);
				while (StringUtil.isNotNull(parentId)) {
					if (parentId
							.equals(DingConfig.newInstance().getDingOrgId())) {
						avaliableDeptIds.add(deptId);
						break;
					}
					parentId = pidMap.get(parentId);
				}
			}else{
				avaliableDeptIds.add(deptId);
			}
		}
		if ("true".equals(DingConfig.newInstance().getDingOmsRootFlag())) {
			if (!avaliableDeptIds.contains(DingConfig.newInstance()
					.getDingOrgId())) {
				avaliableDeptIds.add(DingConfig.newInstance().getDingOrgId());
			}
		}
		rootOrgChildren.addAll(avaliableDeptIds);
		
		for (JSONArray block : allElements) {
			for (int i = 0; i < block.size(); i++) {
				JSONObject element = block.getJSONObject(i);
				if (TYPE_PERSON_KEY.equals(element.getString("type"))) {
					if (element.get("parent") != null) {
						if(avaliableDeptIds.contains(element.getString("parent"))){
							rootOrgChildren.add(element.getString("id"));
						}
					}
				}
			}
		}	
	}
	
	private void init() throws Exception {
		dingApiService = DingUtils.getDingApiService();
		dingOmsConfig = new DingOmsConfig();
		lastUpdateTime = dingOmsConfig.getLastUpdateTime();
		
		List list = omsRelationService.findList("fdAppKey='" + getAppKey()
				+ "'", null);
		for (int i = 0; i < list.size(); i++) {
			OmsRelationModel model = (OmsRelationModel) list.get(i);
			relationMap.put(model.getFdEkpId(), model.getFdAppPkId());
		}
	}

	private void terminate() throws Exception {
		if (StringUtil.isNotNull(lastUpdateTime)) {
			dingOmsConfig.setLastUpdateTime(lastUpdateTime);
			dingOmsConfig.save();
		}
	}

	private List<JSONArray> getSyncOrgElements() throws Exception {
		SysSynchroGetOrgInfoContext infoContext = new SysSynchroGetOrgInfoContext();
		infoContext
				.setReturnOrgType("[{\"type\":\"org\"},{\"type\":\"dept\"},{\"type\":\"person\"}]");
		infoContext.setCount(preCount);
		List<JSONArray> resultList = new ArrayList<JSONArray>();
		while (true) {
			infoContext.setBeginTimeStamp(lastUpdateTime);
			SysSynchroOrgResult orgResult = getSysSynchroGetOrgWebService()
					.getUpdatedElements(infoContext);
			if (orgResult.getReturnState() == 2) {
				String resultStr = orgResult.getMessage();
				if (StringUtil.isNotNull(resultStr)) {
					resultList.add((JSONArray) JSONArray.fromObject(resultStr));
					lastUpdateTime = orgResult.getTimeStamp();
				}
				if (orgResult.getCount() < preCount) {
                    break;
                }
			} else {
				logger.error("分批获取组织架构所有信息出错,返回状态值为:" + orgResult.getCount()
						+ ",错误信息为:" + orgResult.getMessage());
				break;
			}
		}
		return resultList;
	}

}
