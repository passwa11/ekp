package com.landray.kmss.km.imeeting.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.km.imeeting.actions.KmImeetingResAction;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainService;
import com.landray.kmss.sys.authentication.background.IBackgroundAuthService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.Runner;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.annotation.RestApi;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.CacheMode;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/api/km-imeeting/main", method = RequestMethod.POST)
@RestApi(docUrl = "/km/imeeting/rest/kmImeetingRestHelp.jsp", name = "kmImeetingMainDataService", resourceKey = "km-imeeting:kmImeetingMain.job.sync")
public class KmImeetingMainDataServiceImp {

	private IKmImeetingMainService kmImeetingMainService;

	private ISysOrgCoreService sysOrgCoreService;

	private KmImeetingResAction kmImeetingResAction;

	private IBackgroundAuthService backgroundAuthService;

	public IBackgroundAuthService getBackgroundAuthService() {
		if (backgroundAuthService == null) {
			backgroundAuthService = (IBackgroundAuthService) SpringBeanUtil
					.getBean("backgroundAuthService");
		}
		return backgroundAuthService;
	}


	public KmImeetingResAction getKmImeetingResAction() {
		if (kmImeetingResAction == null) {
			kmImeetingResAction = (KmImeetingResAction) SpringBeanUtil
					.getBean("/km/imeeting/km_imeeting_res/kmImeetingRes.do");
		}
		return kmImeetingResAction;
	}

	public void setKmImeetingMainService(IKmImeetingMainService kmImeetingMainService) {
		this.kmImeetingMainService = kmImeetingMainService;
	}

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	@ResponseBody
	@RequestMapping("/get")
	public JSONObject getMeeting(@RequestBody JSONObject paramData) {
		JSONObject result = new JSONObject();
		result.put("success", false);
		try {
			Date beginTime = null;
			if (paramData != null && paramData.containsKey("beginTime")) {
				beginTime = DateUtil.convertStringToDate(paramData.getString("beginTime"));
			}
			// 只获取当天的有效会议
			HQLInfo info = new HQLInfo();
			StringBuffer sb = new StringBuffer();
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			if (beginTime == null || cal.getTime().after(beginTime)) {
				// 如果是当天第一次同步，则同步所有当天有效的会议
				sb.append(
						"kmImeetingMain.docStatus=:docStatus and ((kmImeetingMain.fdHoldDate<:beginTime and kmImeetingMain.fdFinishDate>:beginTime) or (kmImeetingMain.fdHoldDate>:beginTime and kmImeetingMain.fdHoldDate<:endTime))");
			} else {
				info.setParameter("publishTime", beginTime);
				sb.append(
						"kmImeetingMain.docStatus=:docStatus and kmImeetingMain.docPublishTime>:publishTime and ((kmImeetingMain.fdHoldDate<:beginTime and kmImeetingMain.fdFinishDate>:beginTime) or (kmImeetingMain.fdHoldDate>:beginTime and kmImeetingMain.fdHoldDate<:endTime))");
			}
			info.setWhereBlock(sb.toString());
			info.setParameter("beginTime", cal.getTime());
			info.setParameter("docStatus", SysDocConstant.DOC_STATUS_PUBLISH);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 5);
			info.setParameter("endTime", cal.getTime());
			List<?> retVal = kmImeetingMainService.findList(info);
			JSONArray array = new JSONArray();
			for (int i = 0; i < retVal.size(); i++) {
				KmImeetingMain kmImeetingMain = (KmImeetingMain) retVal.get(i);
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("workItemId", kmImeetingMain.getFdId());
				jsonObject.put("workTitle", kmImeetingMain.getDocSubject());
				jsonObject.put("bgTime",
						kmImeetingMain.getFdHoldDate() != null ? kmImeetingMain.getFdHoldDate().getTime() : null);
				jsonObject.put("endTime",
						kmImeetingMain.getFdFinishDate() != null ? kmImeetingMain.getFdFinishDate().getTime() : null);
				if(StringUtil.isNotNull(kmImeetingMain.getFdOtherPlace())){
					jsonObject.put("brief", kmImeetingMain.getFdOtherPlace());
				}else if(kmImeetingMain.getFdPlace()!=null){
					jsonObject.put("brief", kmImeetingMain.getFdPlace().getFdName());
				}else{
					jsonObject.put("brief", kmImeetingMain.getFdRemark());
				}
				jsonObject.put("jobStatus", "UPDATE");
				jsonObject.put("detailUrl", StringUtil.formatUrl(ModelUtil.getModelUrl(kmImeetingMain),true));
				List<SysOrgElement> attend = new ArrayList<>();
				attend.addAll(kmImeetingMain.getFdAttendPersons());
				attend.addAll(kmImeetingMain.getFdParticipantPersons());
				attend.add(kmImeetingMain.getFdHost());
				attend = sysOrgCoreService.expandToPerson(attend);

				List<String> fdLoginName = new ArrayList<String>();
				for (SysOrgElement orgElement : attend) {
					SysOrgPerson person = (SysOrgPerson) orgElement;
					fdLoginName.add(person.getFdLoginName());
				}
				jsonObject.put("loginNames", fdLoginName);

				array.add(jsonObject);
			}
			result.put("datas", array);
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "处理过程中出错：" + e.getMessage());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/checkMeeting")
	public JSONObject checkMeeting(@RequestParam("templateId") String templateId,
			@RequestParam(value = "checkDept", required = false, defaultValue = "false") Boolean checkDept,
			@RequestBody Map<String, Object> body) {
		JSONObject result = new JSONObject();
		result.put("success", false);
		try {
			if (StringUtil.isNotNull(templateId)) {
				if (body != null) {
					if (body.containsKey("beginDate")&&body.containsKey("endDate")) {
						Date bt = null;
						Date et = null;
						Object bd = body.get("beginDate");
						Object ed = body.get("endDate");
						if (bd instanceof Long) {
							bt = new Date((Long) bd);
						} else {
							bt = bd != null ? DateUtil.convertStringToDate(bd.toString()) : null;
						}
						if (ed instanceof Long) {
							et = new Date((Long) ed);
						} else {
							et = ed != null ? DateUtil.convertStringToDate(ed.toString()) : null;
						}
						if (bt != null&&et!=null) {
							List<String> users = (List<String>) body.get("accounts");
							List<String> userIds = getMeetingToDos(users, bt,
									et, templateId, checkDept, false);
							result.put("success", true);
							Calendar calendar = DateUtil.getCalendar(bt);
							JSONArray jsonArray = new JSONArray();
							for (String userId : userIds) {
								JSONObject data = new JSONObject();
								data.put("account", userId);
								data.put("Year", calendar.get(Calendar.YEAR));
								data.put("Month", calendar.get(Calendar.MONTH) + 1);
								data.put("Date",
										DateUtil.convertDateToString(bt, DateUtil.PATTERN_DATE));
								jsonArray.add(data);
							}
							result.put("datas", jsonArray);
						} else {
							result.put("msg", "缺少开始时间参数");
						}
					} else {
						result.put("msg", "缺少开始时间参数");
					}
				} else {
					result.put("msg", "获取内容信息出错，请使用POST提交内容");
				}
			} else {
				result.put("msg", "缺少templateId或者period参数");
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "处理过程中出错：" + e.getMessage());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/checkSummarry")
	public JSONObject checkSummarry(@RequestParam("templateId") String templateId,
			@RequestParam(value = "checkDept", required = false, defaultValue = "false") Boolean checkDept,
			@RequestBody Map<String, Object> body) {
		JSONObject result = new JSONObject();
		result.put("success", false);
		try {
			if (StringUtil.isNotNull(templateId)) {
				if (body != null) {
					if (body.containsKey("beginDate")&&body.containsKey("endDate")) {
						Date bt = null;
						Date et = null;
						Object bd = body.get("beginDate");
						Object ed = body.get("endDate");
						if (bd instanceof Long) {
							bt = new Date((Long) bd);
						} else {
							bt = bd != null ? DateUtil.convertStringToDate(bd.toString()) : null;
						}
						if (ed instanceof Long) {
							et = new Date((Long) ed);
						} else {
							et = ed != null ? DateUtil.convertStringToDate(ed.toString()) : null;
						}
						if (bt != null&&et!=null) {
							List<String> users = (List<String>) body.get("accounts");
							List<String> userIds = getSummaryToDos(users, bt,
									et, templateId, checkDept, false);
							result.put("success", true);
							Calendar calendar = DateUtil.getCalendar(bt);
							JSONArray jsonArray = new JSONArray();
							for (String userId : userIds) {
								JSONObject data = new JSONObject();
								data.put("account", userId);
								data.put("Year", calendar.get(Calendar.YEAR));
								data.put("Month", calendar.get(Calendar.MONTH) + 1);
								data.put("Date",
										DateUtil.convertDateToString(bt, DateUtil.PATTERN_DATE));
								jsonArray.add(data);
							}
							result.put("datas", jsonArray);
						}
					} else {
						result.put("msg", "缺少开始时间参数");
					}
				} else {
					result.put("msg", "获取内容信息出错，请使用POST提交内容");
				}
			} else {
				result.put("msg", "缺少templateId或者period参数");
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "处理过程中出错：" + e.getMessage());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/finishMeeting")
	public JSONObject checkMeetingFinish(@RequestParam("templateId") String templateId,
			@RequestParam(value = "checkDept", required = false, defaultValue = "false") Boolean checkDept,
			@RequestBody Map<String, Object> body) {
		JSONObject result = new JSONObject();
		result.put("success", false);

		try {
			if (StringUtil.isNotNull(templateId)) {
				if (body!=null&&body.containsKey("beginDate")&&body.containsKey("endDate")) {
					Date bt = null;
					Date et = null;
					Object bd = body.get("beginDate");
					Object ed = body.get("endDate");
					if (bd instanceof Long) {
						bt = new Date((Long) bd);
					} else {
						bt = bd != null ? DateUtil.convertStringToDate(bd.toString()) : null;
					}
					if (ed instanceof Long) {
						et = new Date((Long) ed);
					} else {
						et = ed != null ? DateUtil.convertStringToDate(ed.toString()) : null;
					}
					List<String> userIds = new ArrayList<String>();

					List<Map<String, Object>> array = (List<Map<String, Object>>) body.get("datas");
					List<String> retData = new ArrayList<String>();
					for (Map<String, Object> data : array) {
						userIds.add((String) data.get("account"));
					}
					if (!userIds.isEmpty()) {
						List<String> resultIds = getMeetingToDos(userIds, bt,
								et, templateId, checkDept, true);
						if (resultIds != null) {
							retData.addAll(resultIds);
						}
					}
					result.put("accounts", retData);
					result.put("success", true);
				} else {
					result.put("msg", "获取内容信息出错，请使用POST提交内容");
				}
			} else {
				result.put("msg", "缺少templateId参数");
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "处理过程中出错：" + e.getMessage());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/finishSummary")
	public JSONObject getSummaryToDos(@RequestParam("templateId") String templateId,
			@RequestParam(value = "checkDept", required = false, defaultValue = "false") Boolean checkDept,
			@RequestBody Map<String, Object> body) {
		JSONObject result = new JSONObject();
		result.put("success", false);

		try {
			if (StringUtil.isNotNull(templateId)) {
				if (body!=null&&body.containsKey("beginDate")&&body.containsKey("endDate")) {
					Date bt = null;
					Date et = null;
					Object bd = body.get("beginDate");
					Object ed = body.get("endDate");
					if (bd instanceof Long) {
						bt = new Date((Long) bd);
					} else {
						bt = bd != null ? DateUtil.convertStringToDate(bd.toString()) : null;
					}
					if (ed instanceof Long) {
						et = new Date((Long) ed);
					} else {
						et = ed != null ? DateUtil.convertStringToDate(ed.toString()) : null;
					}
					
					List<String> userIds = new ArrayList<String>();

					List<Map<String, Object>> array = (List<Map<String, Object>>) body.get("datas");
					List<String> retData = new ArrayList<String>();
					for (Map<String, Object> data : array) {
						userIds.add((String) data.get("account"));
					}
					if (!userIds.isEmpty()) {
						List<String> resultIds = getMeetingToDos(userIds, bt,
								et, templateId, checkDept, true);
						if (resultIds != null) {
							retData.addAll(resultIds);
						}
					}
					result.put("accounts", retData);
					result.put("success", true);
				} else {
					result.put("msg", "获取内容信息出错，请使用POST提交内容");
				}
			} else {
				result.put("msg", "缺少templateId或者period参数");
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "处理过程中出错：" + e.getMessage());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/getFreeRes", method = {
			RequestMethod.GET, RequestMethod.POST })
	public JSONObject getFreeRes(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("type") String type,
			@RequestParam(value = "fdHoldDate", required = false) String fdHoldDate,
			@RequestParam(value = "fdFinishDate", required = false) String fdFinishDate,
			@RequestParam(value = "fdPlaceId", required = false) String fdPlaceId,
			@RequestParam(value = "bookId", required = false) String bookId,
			@RequestParam(value = "rowsize", required = false) String rowsize) {

		JSONObject retJson = null;
		try {
			Object[] paramaters = new Object[] { request, response };
			SysOrgElement user = sysOrgCoreService.findByLoginName("admin");
			return (JSONObject) getBackgroundAuthService().switchUserById(
					user.getFdId(),
					new Runner() {
						@Override
						public Object run(Object parameter) throws Exception {
							Object[] params = (Object[]) parameter;
							HttpServletRequest request = (HttpServletRequest) params[0];
							HttpServletResponse response = (HttpServletResponse) params[1];
							request.setAttribute("call", "function");
							getKmImeetingResAction().getFreeRes(null, null,
									request, response);
							JSONObject retJson = (JSONObject) request
									.getAttribute("retJson");
							return retJson;
						}
					}, paramaters);

		} catch (Exception e) {
			e.printStackTrace();
			retJson = new JSONObject();
			retJson.put("errcode", -1);
			retJson.put("errmsg", "system error," + e.toString());
		}
		return retJson;
	}

	/**
	 * 检测用户在指定周期内是否有创建会议
	 * 
	 * @param userIds
	 * @param startDate
	 * @param endDate
	 * @param templateId
	 * @param checkDept
	 * @param isExist
	 *            只检测是否存在
	 * @return
	 * @throws Exception
	 */
	private List<String> getMeetingToDos(List<String> userIds, Date startDate, Date endDate, String templateId,
			boolean checkDept, boolean isExist) throws Exception {
		String inIds = "";
		for (String userId : userIds) {
			inIds += ("".equals(inIds) ? "" : ",") + "'" + userId.replaceAll("'", "''") + "'";
		}
		String sql = null;
		if (!checkDept) {
            sql = "select org.fd_login_name from sys_org_person org where org.fd_login_name in(" + inIds + ")" + " and "
                    + (isExist ? "" : "not ")
                    + "exists(select main.fd_id from  km_imeeting_main main where main.fd_template_id=:templateId and main.doc_creator_id=org.fd_id and main.fd_hold_date>:beginDate and main.fd_hold_date<:endDate)";
        } else {
            sql = "select org.fd_login_name from sys_org_person org left join sys_org_element ele on org.fd_id=ele.fd_id where org.fd_login_name in("
                    + inIds + ")" + " and " + (isExist ? "" : "not ")
                    + "exists(select main.fd_id from  km_imeeting_main main where main.fd_template_id=:templateId  and main.doc_dept_id=ele.fd_parentid and main.fd_hold_date>:beginDate and main.fd_hold_date<:endDate)";
        }
		NativeQuery query = kmImeetingMainService.getBaseDao().getHibernateSession().createNativeQuery(sql);
		query.setCacheable(true);
		query.setCacheMode(CacheMode.NORMAL);
		query.setCacheRegion("km-imeeting");
		query.addScalar("fd_login_name", StandardBasicTypes.STRING);
		query.setParameter("templateId", templateId);
		query.setParameter("beginDate", startDate);
		query.setParameter("endDate", endDate);
		return query.list();
	}

	/**
	 * 检测用户在指定周期内是否有创建会议纪要
	 * 
	 * @param userIds
	 * @param startDate
	 * @param endDate
	 * @param templateId
	 * @param checkDept
	 * @param isExist
	 *            只检测是否存在
	 * @return
	 * @throws Exception
	 */
	private List<String> getSummaryToDos(List<String> userIds, Date startDate, Date endDate, String templateId,
			boolean checkDept, boolean isExist) throws Exception {
		String inIds = "";
		for (String userId : userIds) {
			inIds += ("".equals(inIds) ? "" : ",") + "'" + userId.replaceAll("'", "''") + "'";
		}
		String sql = null;
		if (!checkDept) {
            sql = "select org.fd_login_name from sys_org_person org where org.fd_login_name in(" + inIds + ")" + " and "
                    + (isExist ? "" : "not ")
                    + "exists(select main.fd_id from  km_imeeting_summary main where main.fd_template_id=:templateId and main.doc_creator_id=org.fd_id and main.fd_hold_date>:beginDate and main.fd_hold_date<:endDate)";
        } else {
            sql = "select org.fd_login_name from sys_org_person org left join sys_org_element ele on org.fd_id=ele.fd_id where org.fd_login_name in("
                    + inIds + ")" + " and " + (isExist ? "" : "not ")
                    + "exists(select main.fd_id from  km_imeeting_summary main where main.fd_template_id=:templateId  and main.doc_dept_id=ele.fd_parentid and main.fd_hold_date>:beginDate and main.fd_hold_date<:endDate)";
        }
		NativeQuery query = kmImeetingMainService.getBaseDao().getHibernateSession().createNativeQuery(sql);
		query.setCacheable(true);
		query.setCacheMode(CacheMode.NORMAL);
		query.setCacheRegion("km-imeeting");
		query.addScalar("fd_login_name",StandardBasicTypes.STRING);
		query.setParameter("templateId", templateId);
		query.setParameter("beginDate", startDate);
		query.setParameter("endDate", endDate);
		return query.list();
	}
}
