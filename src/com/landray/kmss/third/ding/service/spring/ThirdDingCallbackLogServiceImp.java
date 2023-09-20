package com.landray.kmss.third.ding.service.spring;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.landray.kmss.third.ding.dao.IThirdDingCallbackLogDao;
import com.landray.kmss.third.ding.notify.dao.IThirdDingNotifyLogDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;

import com.dingtalk.api.response.OapiCallBackGetCallBackFailedResultResponse;
import com.dingtalk.api.response.OapiCallBackGetCallBackFailedResultResponse.Failed;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.third.ding.model.ThirdDingCallbackLog;
import com.landray.kmss.third.ding.oms.DingApiService;
import com.landray.kmss.third.ding.oms.SynchroOrgDing2Ekp;
import com.landray.kmss.third.ding.service.IThirdDingCallbackLogService;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ThirdDingCallbackLogServiceImp extends ExtendDataServiceImp implements IThirdDingCallbackLogService {
	
	private static Map<String, String> callBackFiledDataMaps = new HashMap<String, String>(3000);
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingCallbackLogServiceImp.class);
    private ISysNotifyMainCoreService sysNotifyMainCoreService;
	private int requestCount = 0;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdDingCallbackLog) {
            ThirdDingCallbackLog thirdDingCallbackLog = (ThirdDingCallbackLog) model;
        }
        return model;
    }
    protected SynchroOrgDing2Ekp synchroOrgDing2Ekp;
    public SynchroOrgDing2Ekp getSynchroOrgDing2Ekp() {
		if (synchroOrgDing2Ekp == null) {
			synchroOrgDing2Ekp = (SynchroOrgDing2Ekp) SpringBeanUtil.getBean("synchroOrgDing2Ekp");
		}
		return synchroOrgDing2Ekp;
	}
    
    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdDingCallbackLog thirdDingCallbackLog = new ThirdDingCallbackLog();
        thirdDingCallbackLog.setFdIsSuccess(Boolean.valueOf("true"));
        thirdDingCallbackLog.setDocCreateTime(new Date());
        ThirdDingUtil.initModelFromRequest(thirdDingCallbackLog, requestContext);
        return thirdDingCallbackLog;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdDingCallbackLog thirdDingCallbackLog = (ThirdDingCallbackLog) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
	public String saveOrUpdateCallbackAgain(String fdId){
		TransactionStatus status = null;
		String errorMsg=""; 
		try {
			status = TransactionUtils.beginNewTransaction();
			ThirdDingCallbackLog log =  (ThirdDingCallbackLog)this.findByPrimaryKey(fdId);
			String docContent = log.getDocContent();
			JSONObject plainTextJson = JSONObject.fromObject(docContent);
			String eventType = plainTextJson.getString("EventType");
			DingApiService dingApiService = DingUtils.getDingApiService();
			JSONObject element = null;
			JSONArray deptIds = null;
			JSONArray userIds = null;
			if ("user_add_org".equals(eventType)) {// 创建人员
				userIds = plainTextJson.getJSONArray("UserId");
				for (Object object : userIds) {
					String userid = (String) object;
					element = dingApiService.userGet(userid, null);
					if (element != null) {
						try {
							getSynchroOrgDing2Ekp().saveOrUpdateCallbackUser(element, true);
						}catch (NullPointerException e) {
							logger.error("重新执行-类型：user_add_org--"+"userid:"+userid+"----失败",e);
							errorMsg = "DingEndpointAction.saveOrUpdateCallbackUser()----NullPointerException";
						} catch (Exception e) {
							logger.error("重新执行-类型：user_add_org--"+"userid:"+userid+"----失败",e);
							errorMsg = e.getMessage();
						}
					}
				}
			} else if ("user_modify_org".equals(eventType) || "user_dept_change".equals(eventType)
					|| "user_role_change".equals(eventType) || "user_active_org".equals(eventType)) {// 更新人员
				userIds = plainTextJson.getJSONArray("UserId");
				for (Object object : userIds) {
					String userid = (String) object;
					element = dingApiService.userGet(userid, null);
					if (element != null) {
						try {
							getSynchroOrgDing2Ekp().saveOrUpdateCallbackUser(element, false);
						}catch (NullPointerException e) {
							logger.error("重新执行-类型："+eventType +"--"+"userid:"+element.getString("userid")+"----失败",e);
							errorMsg = "DingEndpointAction.saveOrUpdateCallbackUser()----NullPointerException";
						} catch (Exception e) {
							logger.error("重新执行-类型："+eventType +"--"+"userid:"+element.getString("userid")+"----失败",e);
							errorMsg =e.getMessage();
						}
						
					}
				}
			} else if ("user_leave_org".equals(eventType)) {// 删除人员
				userIds = plainTextJson.getJSONArray("UserId");
				for (Object object : userIds) {
					String userid = (String) object;
					try {
						getSynchroOrgDing2Ekp().deleteCallbackUser(userid);
					}catch (NullPointerException e) {
						logger.error("重新执行-类型：user_leave_org--"+"userid:"+userid+"----失败",e);
						errorMsg = "DingEndpointAction.deleteCallbackUser()----NullPointerException";
					} catch (Exception e) {
						logger.error("重新执行-类型：user_leave_org--"+"userid:"+userid+"----失败",e);
						errorMsg =e.getMessage();
					}
					
				}
			} else if ("org_dept_create".equals(eventType)) {// 创建部门
				deptIds = plainTextJson.getJSONArray("DeptId");
				for (Object object : deptIds) {
					Long deptId = Long.valueOf(object.toString());
					element = dingApiService.departGet(deptId);
					if (element != null) {
						try {
							getSynchroOrgDing2Ekp().saveOrUpdateCallbackDept(
									element,true);
						}catch (NullPointerException e) {
							logger.error("重新执行-类型：org_dept_create--"+"deptId:"+deptId+"----失败",e);
							errorMsg = "DingEndpointAction.saveOrUpdateCallbackDept()----NullPointerException";
						} catch (Exception e) {
							logger.error("重新执行-类型：org_dept_create--"+"deptId:"+deptId+"----失败",e);
							errorMsg =e.getMessage();
						}
						
					}
				}
			} else if ("org_dept_modify".equals(eventType)) {// 更新部门
				deptIds = plainTextJson.getJSONArray("DeptId");
				for (Object object : deptIds) {
					Long deptId = Long.valueOf(object.toString());
					element = dingApiService.departGet(deptId);
					if (element != null) {
						try {
							getSynchroOrgDing2Ekp().saveOrUpdateCallbackDept(
									element,false);
						}catch (NullPointerException e) {
							logger.error("重新执行-类型：org_dept_modify--"+"deptId:"+ element.getString("id")+"----失败",e);
							errorMsg = "DingEndpointAction.saveOrUpdateCallbackDept()----NullPointerException";
						} catch (Exception e) {
							logger.error("重新执行-类型：org_dept_modify--"+"deptId:"+ element.getString("id")+"----失败",e);
							errorMsg =e.getMessage();
						}
						
					}
				}
			} else if ("org_dept_remove".equals(eventType)) {// 删除部门
				deptIds = plainTextJson.getJSONArray("DeptId");
				for (Object object : deptIds) {
					Long deptId = Long.valueOf(object.toString());
					try {
						getSynchroOrgDing2Ekp().deleteCallbackDept(deptId);
					}catch (NullPointerException e) {
						logger.error("重新执行-类型：org_dept_remove--"+"deptId:"+ deptId+"---失败",e);
						errorMsg = "DingEndpointAction.deleteCallbackDept()----NullPointerException";
					} catch (Exception e) {
						logger.error("重新执行-类型：org_dept_remove--"+"deptId:"+ deptId+"---失败",e);
						errorMsg =e.getMessage();
					}
					
				}
			}
			
			if (StringUtil.isNull(errorMsg) || "".equals(errorMsg)) {
				log.setFdIsSuccess(true);
			}else {
				log.setFdIsSuccess(false);
				log.setFdErrorInfo(errorMsg);
			}
			this.update(log);
			TransactionUtils.commit(status);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("处理钉钉回调事件---EKP端处理数据失败！",e);
			if (status != null) {
		        try {
		          TransactionUtils.rollback(status);
		        } catch (Exception ex) {
		          logger.error("---事务回滚出错---", ex);
		        }
		      }
		}
		return errorMsg;
		
	}
	
	/**
     * 定时任务处理钉钉回调事件
     * 1:调用钉钉接口-获取回调失败的结果 数据 
     * 2:把数据写入到钉钉回调日志表，状态为false
     * 3:查出回调日志表所有状态为false的数据 逐条处理
     */
	@Override
	public void saveOrUpdateDingCallBack(){
		try {
			logger.info("==================定时任务--处理钉钉回调事件开始==================");
			requestCount = 0;
			//处理钉钉回调失败数据--写入日志表 状态-- false
			callBackDataByOpi();
			//处理map数据 存入日志表 ：map中回调失败接口可能会有数据，也可能不存在数据 
			saveCallBackDataToLog();
			//查询日志表中状态为false的数据ID -进行处理
			executeCallBackLogStatusIsFalse();
		} catch (Exception e) {
			logger.error("钉钉回调事件异常：", e);
			e.printStackTrace();
		} finally {
			callBackFiledDataMaps.clear();
		}
	}

	@Override
	public void clear(int days) throws Exception {
		((IThirdDingCallbackLogDao) getBaseDao()).clear(30);
	}


	private void executeCallBackLogStatusIsFalse() {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime( new Date());
			cal.add(Calendar.DAY_OF_MONTH, -3);
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("thirdDingCallbackLog.fdIsSuccess=:suc and docCreateTime<=:date");
			hqlInfo.setParameter("date", cal.getTime());
			hqlInfo.setParameter("suc", false);
			hqlInfo.setOrderBy("thirdDingCallbackLog.fdEventTime ");
			List<ThirdDingCallbackLog> logs = this.findList(hqlInfo);
			if (logs != null && !logs.isEmpty()) {
				for (ThirdDingCallbackLog thirdDingCallbackLog : logs) {
					//逐条处理数据
					saveOrUpdateCallbackAgain(thirdDingCallbackLog.getFdId());
				}
			}
		} catch (Exception e) {
			logger.error("处理钉钉所有回调失败的事件：", e);
			e.printStackTrace();
		}
	}

	/**
	 * 钉钉回调接口查询失败数据写入日志表
	 */
	private void callBackDataByOpi() {
		try {
			requestCount++;
			logger.warn("----------第" + requestCount + "次执行------------");
			String token = DingUtils.getDingApiService().getAccessToken();
			OapiCallBackGetCallBackFailedResultResponse response = DingUtils.getDingApiService().getCallBackFailedResult(token);
			if (response.getErrcode() != 0) {
				logger.info("获取回调失败的结果接口失败！"+response.getErrmsg());
			} else {
				//获取回调失败的数据
				JSONObject jsonData = new JSONObject();
				String callBackTag,eventTime,dataStr="";
				List<Failed> failedlist =  response.getFailedList();
				Boolean isHasMore = response.getHasMore() ==null?false:response.getHasMore();
				if (failedlist.size() >0 && !failedlist.isEmpty() ) {
					for (Failed failed : failedlist) {
						callBackTag = failed.getCallBackTag();
						eventTime = failed.getEventTime().toString();
						logger.debug("callBackTag:" + callBackTag
								+ " eventTime:" + eventTime);
						//人员
						if ("user_add_org".equals(callBackTag)) {
							dataStr = failed.getUserAddOrg();
						}else if ("user_modify_org".equals(callBackTag)) {
							dataStr = failed.getUserModifyOrg();
						}else if ("user_leave_org".equals(callBackTag)) {
							dataStr = failed.getUserLeaveOrg();
						//部门	
						}else if ("org_dept_create".equals(callBackTag)) {
							dataStr = failed.getOrgDeptCreate();
						}else if ("org_dept_modify".equals(callBackTag)) {
							dataStr = failed.getOrgDeptModify();
						}else if ("org_dept_remove".equals(callBackTag)) {
							dataStr = failed.getOrgDeptRemove();
						}else {
							logger.info("处理钉钉回调事件：事件类型不匹配---"+callBackTag+"--事件内容："+response.getBody());
						}
						logger.debug("dataStr:" + dataStr);
						try {
							jsonData = strToJson(dataStr,callBackTag,eventTime);
							logger.debug("jsonData:" + jsonData);
							callBackFiledDataMaps.put(jsonData.toString(), eventTime);
						} catch (Exception e) {
							e.printStackTrace();
							logger.error("处理钉钉回调失败接口数据时发生异常----返回数据：事件类型："+callBackTag+"事件内容："+dataStr,e);
						}
						
						
					}
					if(isHasMore){
						if (requestCount >= 30) {
							Thread.sleep(700L);
							requestCount = 0;
						}
						callBackDataByOpi();
					}
					//最终map 中的数据需要存到日志表
					
				}else{
					logger.info("获取钉钉回调失败接口的结果数据为空！");
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("调用钉钉回调失败接口异常！",e);
		}
		
		
	}
	/**
	 * 获取map 中的数据 存入日志表
	 */
	private void saveCallBackDataToLog() {
		TransactionStatus status = null;
		if (!callBackFiledDataMaps.isEmpty()) { //map 中有数据
			logger.debug("callBackFiledDataMaps size:"
					+ callBackFiledDataMaps.size());
			Set<String> callBackDataSet = callBackFiledDataMaps.keySet();
			IThirdDingCallbackLogService thirdDingCallbackLogService = (IThirdDingCallbackLogService)SpringBeanUtil.getBean("thirdDingCallbackLogService");
			for (String callBackData : callBackDataSet) { 
				try {
					status = TransactionUtils.beginNewTransaction();
					ThirdDingCallbackLog log = new ThirdDingCallbackLog();
					JSONObject jsonObject = JSONObject.fromObject(callBackData);
					log.setFdId(IDGenerator.generateID());
					log.setFdEventType(jsonObject.getString("EventType"));
					log.setFdEventTypeTip(ThirdDingUtil.geFdEventTypeTipt(jsonObject.getString("EventType")));
					log.setDocContent(callBackData);
					log.setFdIsSuccess(false);
					log.setFdEventTime(Long.parseLong(callBackFiledDataMaps.get(callBackData)));
					thirdDingCallbackLogService.add(log);
					logger.debug(
							"FdEventType:" + jsonObject.getString("EventType")
									+ " FdEventTypeTip:"
									+ ThirdDingUtil.geFdEventTypeTipt(
											jsonObject.getString("EventType"))
									+ " callBackData:" + callBackData);
					TransactionUtils.commit(status);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("调用钉钉回调失败接口往回调日志表添加记录失败：事件内容" + callBackData + ",时间戳："
							+ callBackFiledDataMaps.get(callBackData),e);
					if (status != null) {
				        try {
				          TransactionUtils.rollback(status);
				        } catch (Exception ex) {
				          logger.error("---事务回滚出错---", ex);
				        }
				      }
				}

			}
		}
		
	}
	
	
	//返回数据转为json格式----{deptid=[145526666], corpid=ding8d834506f63651ab35c2f4657eb6378f}
	//{corpid=ding8d834506f63651ab35c2f4657eb6378f, userid=[3151300802774889]}
	private JSONObject strToJson(String dataStr,String callBackTag,String eventTime) throws Exception{
		JSONObject jsonObject = new JSONObject();
		String newDataStr = dataStr.replaceAll("\\{", "");
		newDataStr =newDataStr.replace("}", "");
		String corpidStr = newDataStr.split(",")[0].trim();
		String idStr = newDataStr.split(",")[1].trim();
		if ("userid".equals(idStr.split("=")[0])) {
			jsonObject.put("CorpId", corpidStr.split("=")[1]);
			jsonObject.put("EventType", callBackTag);
			JSONArray userJsonArray = new JSONArray();
			String userid = idStr.split("=")[1].replace("[", "");
			userid = userid.replace("]", "");
			String [] userIdArray = new String[1];
			userIdArray[0] = userid;
			userJsonArray = JSONArray.fromObject(userIdArray); 
			jsonObject.put("UserId", userJsonArray);
		}else if ("deptid".equals(corpidStr.split("=")[0])) {
			jsonObject.put("CorpId", idStr.split("=")[1]);
			jsonObject.put("EventType", callBackTag);
			String deptid = corpidStr.split("=")[1].replace("[", "");
			deptid = deptid.replace("]", "");
			JSONArray deptJsonArray = new JSONArray();
			long [] deptarray= new long[1];
			deptarray[0] = Long.parseLong(deptid);
			deptJsonArray = JSONArray.fromObject(deptarray);
			jsonObject.put("DeptId", deptJsonArray);
		}	
		return jsonObject;
	}
}
