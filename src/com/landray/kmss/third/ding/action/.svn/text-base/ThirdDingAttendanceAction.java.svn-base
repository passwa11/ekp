package com.landray.kmss.third.ding.action;

import com.dingtalk.api.response.OapiProcessWorkrecordCreateResponse;
import com.dingtalk.api.response.OapiProcessWorkrecordUpdateResponse;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.third.ding.model.*;
import com.landray.kmss.third.ding.oms.DingApiService;
import com.landray.kmss.third.ding.provider.DingNotifyUtil;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.service.IThirdDingDinstanceXformService;
import com.landray.kmss.third.ding.service.IThirdDingDtemplateXformService;
import com.landray.kmss.third.ding.service.IThirdDingLeavelogService;
import com.landray.kmss.third.ding.util.BizsuiteUtil;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import edu.emory.mathcs.backport.java.util.Arrays;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

public class ThirdDingAttendanceAction extends ExtendAction {

	private static final Log logger = LogFactory
			.getLog(ThirdDingAttendanceAction.class);

	protected ISysOrgElementService sysOrgElementService;

	protected DingApiService dingApiService = DingUtils.getDingApiService();

	private IThirdDingDinstanceXformService thirdDingDinstanceXformService;

	public IThirdDingDinstanceXformService getThirdDingDinstanceXformService() {
		if (thirdDingDinstanceXformService == null) {
			thirdDingDinstanceXformService = (IThirdDingDinstanceXformService) SpringBeanUtil
					.getBean("thirdDingDinstanceXformService");
		}
		return thirdDingDinstanceXformService;
	}

	protected ISysOrgElementService
			getSysOrgElementService(HttpServletRequest request) {
		if (sysOrgElementService == null) {
			sysOrgElementService = (ISysOrgElementService) getBean(
					"sysOrgElementService");
		}
		return sysOrgElementService;
	}

	private IThirdDingDtemplateXformService thirdDingDtemplateXformService;

	public IThirdDingDtemplateXformService getThirdDingDtemplateXformService() {
		if (thirdDingDtemplateXformService == null) {
			thirdDingDtemplateXformService = (IThirdDingDtemplateXformService) SpringBeanUtil
					.getBean("thirdDingDtemplateXformService");
		}
		return thirdDingDtemplateXformService;
	}

	private ILbpmProcessService lbpmProcessService;

	public ILbpmProcessService getLbpmProcessService() {
		if (lbpmProcessService == null) {
			lbpmProcessService = (ILbpmProcessService) SpringBeanUtil
					.getBean("lbpmProcessService");
		}
		return lbpmProcessService;
	}

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		return null;
	}

	private IOmsRelationService omsRelationService;

	public IOmsRelationService getOmsRelationService() {
		if (omsRelationService == null) {
			omsRelationService = (IOmsRelationService) SpringBeanUtil
					.getBean("omsRelationService");
		}
		return omsRelationService;
	}

	private IThirdDingLeavelogService thirdDingLeavelogService;

	public IThirdDingLeavelogService getThirdDingLeavelogService() {
		if (thirdDingLeavelogService == null) {
			thirdDingLeavelogService = (IThirdDingLeavelogService) SpringBeanUtil
					.getBean("thirdDingLeavelogService");
		}
		return thirdDingLeavelogService;
	}
	/**
	 * 预计算请假时长 (出差)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward preCalcuateTripTime(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		String userId = request.getParameter("fdId"); // 用户UserId
		String from_time = request.getParameter("from_time"); // 开始时间
		String to_time = request.getParameter("to_time"); // 结束时间
		String duration_unit = request.getParameter("duration_unit");// 时长单位
		String calculate_model = request.getParameter("calculate_model"); // 计算方法

		logger.debug("userId:" + userId + "  from_time:" + from_time
				+ "  to_time:" + to_time + "  duration_unit:" + duration_unit
				+ "  calculate_model:" + calculate_model);
		if (StringUtil.isNull(userId)) {
			logger.error("fdId不能为空");
		}
		String ding_id = getOmsRelationService()
				.getDingUserIdByEkpUserId(userId);
		logger.debug("ding_id:" + ding_id);

		JSONObject param = new JSONObject();
		param.put("userid", ding_id);
		param.put("biz_type", 2);
		param.put("from_time", from_time);
		param.put("to_time", to_time);
		param.put("duration_unit", duration_unit);
		param.put("calculate_model", calculate_model);

		JSONObject rs = DingUtils.getDingApiService()
				.preCalcuateDate(param, userId);
		logger.debug("预计算请假:" + rs);
		response.getWriter().write(rs.toString());
		return null;
	}

	/**
	 * 预计算请假时长 (外出)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward preCalcuateBusinessTime(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		String userId = request.getParameter("fdId"); // 用户UserId
		String from_time = request.getParameter("from_time"); // 开始时间
		String to_time = request.getParameter("to_time"); // 结束时间
		String duration_unit = request.getParameter("duration_unit");// 时长单位
		String calculate_model = request.getParameter("calculate_model"); // 计算方法

		logger.debug("userId:" + userId + "  from_time:" + from_time
				+ "  to_time:" + to_time + "  duration_unit:" + duration_unit
				+ "  calculate_model:" + calculate_model);
		if (StringUtil.isNull(userId)) {
			logger.error("fdId不能为空");
		}
		String ding_id = getOmsRelationService()
				.getDingUserIdByEkpUserId(userId);
		logger.debug("ding_id:" + ding_id);

		JSONObject param = new JSONObject();
		param.put("userid", ding_id);
		param.put("biz_type", 2);
		param.put("from_time", from_time);
		param.put("to_time", to_time);
		param.put("duration_unit", duration_unit);
		param.put("calculate_model", calculate_model);

		JSONObject rs = DingUtils.getDingApiService()
				.preCalcuateDate(param, userId);
		logger.debug("预计算请假:" + rs);
		response.getWriter().write(rs.toString());
		return null;
	}

	/**
	 * 预计算请假时长 (加班)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward preCalcuateOvertimeTime(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		String userId = request.getParameter("fdId"); // 用户UserId
		String from_time = request.getParameter("from_time"); // 开始时间
		String to_time = request.getParameter("to_time"); // 结束时间
		String duration_unit = request.getParameter("duration_unit");// 时长单位
		String calculate_model = request.getParameter("calculate_model"); // 计算方法

		JSONObject rs = preCalcuateOvertimeTime(userId, from_time, to_time,
				duration_unit, calculate_model);

		response.getWriter().write(rs.toString());
		return null;
	}

	private JSONObject preCalcuateOvertimeTime(String userId, String from_time,
			String to_time, String duration_unit, String calculate_model)
			throws Exception {
		logger.debug("userId:" + userId + "  from_time:" + from_time
				+ "  to_time:" + to_time + "  duration_unit:" + duration_unit
				+ "  calculate_model:" + calculate_model);
		if (StringUtil.isNull(userId)) {
			logger.error("fdId不能为空");
		}
		List<String> idList = Arrays.asList(userId.split(";"));
		String ding_id = "";
		if(null != idList && idList.size()>0) {//如果加班人存在多个人的情况，默认取第一个人计算时长
			ding_id = getOmsRelationService().getDingUserIdByEkpUserId(idList.get(0));
		}
		//去掉最后一个;
		logger.debug("ding_id:" + ding_id);

		JSONObject param = new JSONObject();
		param.put("userid", ding_id);
		param.put("biz_type", 1);
		param.put("from_time", from_time);
		param.put("to_time", to_time);
		param.put("duration_unit", "hour"); // biz_type为1时仅支持hour
		param.put("calculate_model", calculate_model);

		JSONObject rs = DingUtils.getDingApiService()
				.preCalcuateDate(param, userId);
		logger.debug("预计算请假:" + rs);
		return rs;
	}



	/**
	 * 获取补卡规则（剩余补卡次数）
	 */
	public ActionForward getMonthReplacementInfoByUser(ActionMapping mapping,
													   ActionForm form,
													   HttpServletRequest request,
													   HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		//TODO DingUtils.getDingApiService().getXX(param, ekp_opUserid)
		JSONObject rs = new JSONObject();
		rs.put("used", "1");
		rs.put("left", "4");

		logger.debug("获取补卡规则（剩余补卡次数）:" + rs);
		response.getWriter().write(rs.toString());
		return null;
	}
	/**
	 * 获取加班规则
	 */
	public ActionForward getWorkOverTimesInfoByUser(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		//TODO DingUtils.getDingApiService().getXX(param, ekp_opUserid)
		
		
		JSONObject rs = new JSONObject();
		rs.put("used", "1");
		rs.put("left", "4");
		
		logger.debug("获取加班规则:" + rs);
		response.getWriter().write(rs.toString());
		return null;
	}

	/**
	 * 获取个人班次信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward scheduleByDay(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		// param包括opUserid(操作者dingId)、user_id(用户userId)、date_time(查询某工作日的数据，unix时间戳)
		String ekp_opUserid = request.getParameter("op_user_id"); // 用户UserId
		String ekp_userId = request.getParameter("user_id"); // 用户UserId
		String date_time = request.getParameter("date_time"); // 用户UserId

		JSONObject rs = scheduleByDay(ekp_opUserid, ekp_userId, date_time);
		response.getWriter().write(rs.toString());
		return null;
	}

	private JSONObject scheduleByDay(String ekp_opUserid, String ekp_userId,
			String date_time) throws Exception {
		JSONObject param = new JSONObject();
		System.out.println("时间戳：" + System.currentTimeMillis());
		if (StringUtil.isNull(ekp_userId)) {
			ekp_userId = UserUtil.getUser().getFdId();
		}

		String ding_userId = getOmsRelationService()
				.getDingUserIdByEkpUserId(ekp_userId);
		if (StringUtil.isNull(ekp_opUserid)) {
			ekp_opUserid = ekp_userId;
		}
		String ding_opUserid = getOmsRelationService()
				.getDingUserIdByEkpUserId(ekp_opUserid);

		// opUserid(操作者dingId)、userids(待查用户dingId)、leaveCode(假期标识)
		logger.debug("ekp_opUserid:" + ekp_opUserid + "  ekp_userId:"
				+ ekp_userId + "  date_time:" + date_time);
		String ding_id = null;

		param.put("op_user_id", ding_opUserid);
		param.put("user_id", ding_userId);
		param.put("date_time", date_time);

		JSONObject rs = DingUtils.getDingApiService().scheduleByDay(param,
				ekp_userId);
		logger.debug("获取个人班次信息:" + rs);
		return rs;
	}

	/**
	 * 获取考勤排班方式
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getUserGroup(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		String ekp_userId = request.getParameter("userid"); // 用户UserId
		String rs = getUserGroup(ekp_userId);
		response.getWriter().write(rs);
		return null;
	}

	private String getUserGroup(String ekp_userId) throws Exception {
		if (StringUtil.isNull(ekp_userId)) {
			logger.error("获取考勤排班方式的人员为空！");
			return null;
		}
		String ding_userId = getOmsRelationService()
				.getDingUserIdByEkpUserId(ekp_userId);
		// opUserid(操作者dingId)、userids(待查用户dingId)、leaveCode(假期标识)
		logger.debug("ding_userId:" + ding_userId);
		JSONObject param = new JSONObject();
		param.put("userid", ding_userId);

		String rs = DingUtils.getDingApiService()
				.getUserGroup(param, ekp_userId);
		logger.debug("获取考勤排班方式:" + rs);
		return rs;
	}

	public String findDingIdsByEkpList(List list) {
		String ids = "";
		if (list == null || list.isEmpty()) {
			return null;
		}
		String ekpId = null;
		for (int i = 0; i < list.size(); i++) {
			ekpId = (String) list.get(i);
			try {
				String did = getOmsRelationService()
						.getDingUserIdByEkpUserId(ekpId);

				if (StringUtil.isNull(did)) {
					log.error("根据ekpId无法找到钉钉对照关系,ekpId:" + ekpId);
				} else {
					if (StringUtil.isNull(ids)) {
						ids = did;
					} else {
						ids = ids + "," + did;
					}
				}
			} catch (Exception e) {
				log.debug(e.toString());
			}
		}
		return ids;
	}
	/**
	 * 加班根据时长校验班次
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkCalcuateOvertimeTime(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		JSONObject obj = new JSONObject();
		String userId = request.getParameter("fdId"); // 用户UserId
		String from_time = request.getParameter("from_time"); // 开始时间
		String to_time = request.getParameter("to_time"); // 结束时间
		String duration_unit = request.getParameter("duration_unit");// 时长单位
		String calculate_model = request.getParameter("calculate_model"); // 计算方法
		
		
		List<String> idList = Arrays.asList(userId.split(";"));
		
		Double firstDuration = getFirstDuration(idList.get(0), from_time, to_time,duration_unit, calculate_model);
		
		List<Map<String,String>> sList = new ArrayList<Map<String,String>>();
		List<Map<String,String>> dList = new ArrayList<Map<String,String>>();
		Map<String,String> submitUser = null;
		Map<String,String>  diffUser = null;
		JSONObject rs =null;
		if(null != idList && idList.size()>0) {
			for (String id : idList) {
				if(StringUtil.isNull(id)) {
					continue;
				}
				rs = preCalcuateOvertimeTime(id, from_time, to_time,duration_unit, calculate_model);
				SysOrgElement user = (SysOrgElement) getSysOrgElementService(request).findByPrimaryKey(id);
				submitUser = new HashMap<String,String>();
				diffUser = new HashMap<String,String>();
				if(null != rs) {
					String errcode = rs.get("errcode")+"";
					if("0".equals(errcode)) {
						JSONObject result = (JSONObject) rs.get("result");
						if(null == result || null == result.get("duration")) {
							diffUser.put("id", id);
							diffUser.put("name", user.getFdName());
							dList.add(diffUser);
							continue;
						}
						String duration = result.get("duration")+"";
						Double durations = Double.parseDouble(duration) ;
						System.out.println("用户id的："+id+"的加班时长是："+durations);
						BigDecimal firstDec = new BigDecimal(firstDuration.doubleValue());
						BigDecimal durationsDec = new BigDecimal(durations.doubleValue());
						if(firstDec.compareTo(durationsDec)==0) {
							//相同班次
							submitUser.put("id", id);
							submitUser.put("name", user.getFdName());
							sList.add(submitUser);
							continue;
						}
					}
				}
				diffUser.put("id", id);
				diffUser.put("name", user.getFdName());
				dList.add(diffUser);
			}
		}
		
		request.getSession().setAttribute("selectCount", sList.size());
		request.getSession().setAttribute("clearCount", dList.size());
		//request.getSession().setAttribute("selectUsers", sList);
		//request.getSession().setAttribute("clearUsers", dList);
		obj.accumulate("firstDuration", firstDuration);
		obj.accumulate("submitUser", sList);
		obj.accumulate("diffUser", dList);
		response.getWriter().write(obj.toString());
		return null;
	}

	private Double getFirstDuration(String firstUserId, String from_time, String to_time, String duration_unit,
			String calculate_model) throws Exception {
		JSONObject results = preCalcuateOvertimeTime(firstUserId, from_time, to_time,duration_unit, calculate_model);//获取第一个人的时长
		if(null != results) {
			String errcode = results.get("errcode")+"";
			if("0".equals(errcode)) {
				JSONObject result = (JSONObject) results.get("result");
				if(null != result) {
					String duration = result.get("duration")+"";
					if(StringUtil.isNull(duration)) {
						return 0d;
					}
					Double s =Double.parseDouble(duration) ;
					return s ;
				}
			}
		}
		return 0d;
	}

	// 请假套件--获取请假类型
	public ActionForward getBizsuiteTypes(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.debug("获取请假类型");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String ekpUserId = null;
		String methodCode = request.getParameter("methodCode");
		String leaveType = request.getParameter("leaveType");
		String userid = request.getParameter("ekpUserid");
		logger.debug("leaveType:" + leaveType + "  ekpUserid:" + userid);
		if (StringUtil.isNotNull(leaveType) && "batch".equals(leaveType)) {
			if (StringUtil.isNull(userid)) {
				logger.error("【钉钉批量请假】参数请假人ekpUserid为空！");
				return null;
			}
			ekpUserId = userid;
		} else {
			String fdId = request.getParameter("fdId");
			logger.debug("methodCode:" + methodCode + "  fdId:" + fdId);
			if ("edit".equals(methodCode)) {
				ekpUserId = getCreatorFdId(fdId);
			}
		}

		String rs = BizsuiteUtil.getBizsuiteTypes(ekpUserId).toString();
		logger.debug("-获取请假类型:" + rs);
		response.getWriter().write(rs);
		return null;
	}

	private String getCreatorFdId(String fdId) {
		if (StringUtil.isNull(fdId)) {
			logger.warn("fdId为空！");
			return null;
		}
		try {
			// 获取主文档创建者信息
			IBaseService obj = (IBaseService) SpringBeanUtil
					.getBean("kmReviewMainService");
			Object kmReviewMainObject = obj
					.findByPrimaryKey(fdId);
			Class clazz = kmReviewMainObject.getClass();
			Method method = clazz.getMethod("getDocCreator");
			SysOrgPerson docCreator = (SysOrgPerson) method
					.invoke(kmReviewMainObject);
			String ekpUserId = docCreator.getFdId();
			logger.debug("创建人fdId:" + ekpUserId);
			return ekpUserId;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	// 请假套件--预计算请假时长
	public ActionForward preCalculate(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.debug("预计算请假时长");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String startTime = request.getParameter("startTime");
		String finishTime = request.getParameter("finishTime");
		String leaveCode = request.getParameter("leaveCode");
		String ekpUserId = null;
		String methodCode = request.getParameter("methodCode");

		String leaveType = request.getParameter("leaveType");
		String userid = request.getParameter("ekpUserid");
		logger.debug("leaveType:" + leaveType + "  ekpUserid:" + userid);
		if (StringUtil.isNotNull(leaveType) && "batch".equals(leaveType)) {
			if (StringUtil.isNull(userid)) {
				logger.error("【钉钉批量请假】参数请假人ekpUserid为空！");
				return null;
			}
			ekpUserId = userid;
		} else {
			logger.debug("----单个请假套件----");
			String fdId = request.getParameter("fdId");
			logger.debug("methodCode:" + methodCode + "  fdId:" + fdId);
			if ("edit".equals(methodCode)) {
				ekpUserId = getCreatorFdId(fdId);
			}
		}

		String rs = BizsuiteUtil
				.preCalculate(startTime, finishTime, leaveCode, ekpUserId)
				.toString();
		logger.debug(rs);
		response.getWriter().write(rs);
		return null;
	}

	// 请假套件--判断是否能请假
	public ActionForward canLeave(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.debug("------------判断是否能请假--------------");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String methodCode = request.getParameter("methodCode");

		String ekpUserId = null;
		String leaveType = request.getParameter("leaveType");
		String userid = request.getParameter("ekpUserid");
		logger.debug("leaveType:" + leaveType + "  ekpUserid:" + userid);
		if (StringUtil.isNotNull(leaveType) && "batch".equals(leaveType)) {
			if (StringUtil.isNull(userid)) {
				logger.error("【钉钉批量请假】参数请假人ekpUserid为空！");
				return null;
			}
			ekpUserId = userid;
		} else {
			logger.debug("----单个请假套件----");
			String fdId = request.getParameter("fdId");
			logger.debug("methodCode:" + methodCode + "  fdId:" + fdId);
			if ("edit".equals(methodCode)) {
				ekpUserId = getCreatorFdId(fdId);
			}
		}

		String startTime = request.getParameter("startTime");
		String unit = request.getParameter("unit");
		logger.debug("unit:" + unit);
		String _startTime = startTime;
		// 创建实例需要:标题 单位 时长
		logger.debug("startTime:" + startTime);
		if (StringUtil.isNotNull(startTime)
				&& (startTime.contains("AM") || startTime.contains("PM"))) {
			startTime = startTime.replace("AM", "00:00");
			startTime = startTime.replace("PM", "12:00");
			logger.debug("startTime:" + startTime);
		}
		String finishTime = request.getParameter("finishTime");
		String _finishTime = finishTime;
		logger.debug("finishTime:" + finishTime);
		if (StringUtil.isNotNull(finishTime)
				&& (finishTime.contains("AM") || finishTime.contains("PM"))) {
			finishTime = finishTime.replace("AM", "11:59");
			finishTime = finishTime.replace("PM", "23:59");
			logger.debug("finishTime:" + finishTime);
		}

		if ("day".equals(unit)) {
			startTime = startTime + " 00:00";
			finishTime = finishTime + " 23:59";
		}
		String leaveCode = request.getParameter("leaveCode");
		String leaveTimeInfo = request.getParameter("leaveTimeInfo");

		if ("edit".equals(methodCode) && !"batch".equals(leaveType)) {
			String fdId = request.getParameter("fdId");
			String docSubject = request.getParameter("docSubject");
			String reason = request.getParameter("reason");
			// 判断是否需要重新提实例
			boolean isRepeatLeave = isRepeatLeave(fdId, _startTime, _finishTime,
					leaveCode, unit);
			logger.debug("isRepeatLeave:" + isRepeatLeave);
			if (!isRepeatLeave) {
				logger.debug("撤销原实例文档        fdId:" + fdId);

				JSONObject rs = new JSONObject();
				if (StringUtil.isNull(fdId)) {
					logger.warn("主文档fdId为空！");
					rs.put("errcode", -1);
					rs.put("success", false);
					rs.put("errmsg", "主文档fdId为空！");
					response.getWriter().write(rs.toString());
					return null;
				}
				IBaseService obj = (IBaseService) SpringBeanUtil
						.getBean("kmReviewMainService");
				BaseModel baseModel = (BaseModel) obj
						.findByPrimaryKey(fdId);
				ThirdDingDinstanceXform thirdDingDinstanceXform = getDingInstanceId(
						fdId);
				String instanceId = thirdDingDinstanceXform == null ? null
						: thirdDingDinstanceXform.getFdInstanceId();
				if (StringUtil.isNull(instanceId)) {
					logger.warn("实例Id为空或者找不到！");
					rs.put("errcode", -1);
					rs.put("success", false);
					rs.put("errmsg", "实例Id为空或者找不到！");
					response.getWriter().write(rs.toString());
					return null;
				}

				// 更新实例状态（终止）
				OapiProcessWorkrecordUpdateResponse update_response = DingNotifyUtil
						.updateInstanceState_TERMINATED(
								DingUtils.dingApiService.getAccessToken(),
								instanceId,
								Long.valueOf(DingConfig.newInstance()
										.getDingAgentid()),
								null);
				logger.debug(
						"update_response body:" + update_response.getBody());
				if (update_response.getErrcode() != 0) {
					rs.put("errcode", update_response.getErrcode());
					rs.put("success", false);
					rs.put("errmsg", update_response.getBody());
					response.getWriter().write(rs.toString());
					return null;
				}
				// 更新状态
				thirdDingDinstanceXform.setFdStatus("10");
				getThirdDingDinstanceXformService()
						.update(thirdDingDinstanceXform);
				JSONObject canLeaveResult = BizsuiteUtil.canLeave(startTime,
						finishTime, leaveCode, leaveTimeInfo, ekpUserId);
				if (canLeaveResult != null
						&& canLeaveResult.getInt("errcode") == 0) {
					if (canLeaveResult.getBoolean("success")) {
						// 创建新的实例
						if (createXformDisdance(baseModel, "attendance",
								_startTime, _finishTime, leaveCode,
								leaveTimeInfo, unit, docSubject, reason)) {
							response.getWriter()
									.write(canLeaveResult.toString());
						} else {
							rs.put("errcode", -1);
							rs.put("success", false);
							rs.put("errmsg", "创建新实例失败！");
							response.getWriter().write(rs.toString());
						}
						return null;
					} else {
						JSONObject restore = StringUtil
								.isNotNull(restoreInstance(baseModel))
										? JSONObject.fromObject(
												restoreInstance(baseModel))
										: null;
						if (restore != null && restore.getInt("errcode") == 0) {
							response.getWriter()
									.write(canLeaveResult.toString());
						} else {
							logger.error("原文档实例已经删除，且还原失败！");
							if (restore == null) {
								rs.put("errcode", -1);
								rs.put("success", false);
								rs.put("errmsg", "原文档实例已经删除，且还原失败！");
							} else {
								rs.put("errcode", restore.getInt("errcode"));
								rs.put("success", false);
								rs.put("errmsg", restore.toString());
							}
							response.getWriter().write(rs.toString());
							return null;
						}
					}
				} else {
					logger.error("判断能否请假  失败！" + canLeaveResult);
					JSONObject restore = StringUtil
							.isNotNull(restoreInstance(baseModel))
									? JSONObject.fromObject(
											restoreInstance(baseModel))
									: null;
					if (restore != null && restore.getInt("errcode") == 0) {
						response.getWriter().write(canLeaveResult.toString());
					} else {
						logger.error("原文档实例已经删除，且还原失败！");
						if (restore == null) {
							rs.put("errcode", -1);
							rs.put("errmsg", "原文档实例已经删除，且还原失败！");
						} else {
							rs.put("errcode", restore.getInt("errcode"));
							rs.put("errmsg", restore.toString());
						}
						response.getWriter().write(rs.toString());
						return null;
					}
				}
			} else {
				logger.warn("请假类型，开始时间，结束时间都没有变化，不需要重新提请假实例");
			}

		}
		response.getWriter().write(BizsuiteUtil
				.canLeave(startTime, finishTime, leaveCode, leaveTimeInfo,
						ekpUserId)
				.toString());
		return null;
	}



	private boolean isRepeatLeave(String fdId, String _startTime,
			String _finishTime, String leaveCode, String unit)
			throws Exception {

		IBaseService obj = (IBaseService) SpringBeanUtil
				.getBean("kmReviewMainService");
		BaseModel baseModel = (BaseModel) obj
				.findByPrimaryKey(fdId);
		Map map = DingUtil.getExtendDataModelInfo(baseModel);
		logger.warn("map:" + map);
		String ori_leaveCode = (String) map.get("leave_code");
		String ori_unit = (String) map.get("unit");
		if (!leaveCode.equals(ori_leaveCode)) {
			logger.debug("---编辑前后假期不一致---");
			return false;
		} else if (!ori_unit.equals(unit)) {
			logger.debug("---编辑前后假期单位不一致---");
			return false;
		} else {
			// 比较开始时间
			Date from_date = (Date) map.get("from_time");
			logger.debug("-----unit:" + unit);
			logger.debug("from_date:" + from_date);
			if ("day".equals(unit)) {
				String from_time = DateUtil.convertDateToString(from_date,
						"yyyy-MM-dd");
				logger.debug("from_date:" + from_date + "  from_time:"
						+ from_time);
				if (!from_time.equals(_startTime)) {
					logger.debug("---编辑前后假期开始时间不一致---");
					return false;
				}
			} else if ("halfDay".equals(unit)) {
				String from_time = DateUtil.convertDateToString(from_date,
						"yyyy-MM-dd HH:mm");
				logger.debug("from_date:" + from_date + "  from_time:"
						+ from_time);
				if (!from_time.equals(_startTime)) {
					logger.debug("---编辑前后假期开始时间不一致---");
					return false;
				}
			} else if ("hour".equals(unit)) {
				String from_time = DateUtil.convertDateToString(from_date,
						"yyyy-MM-dd");
				logger.debug("from_date:" + from_date + "  from_time:"
						+ from_time);
				if (!(_startTime.contains(from_time) && _startTime
						.contains((String) map.get("from_half_day")))) {
					logger.debug("---编辑前后假期开始时间不一致---");
					return false;
				}
			}

			// 结束时间
			Date to_date = (Date) map.get("to_time");
			logger.debug("to_date:" + to_date);
			if ("day".equals(unit)) {
				String to_time = DateUtil.convertDateToString(to_date,
						"yyyy-MM-dd");
				logger.debug("to_date:" + to_date + "  to_time:"
						+ to_time);
				if (!to_time.equals(_finishTime)) {
					logger.debug("---编辑前后假期结束时间不一致---");
					return false;
				}
			} else if ("halfDay".equals(unit)) {
				String to_time = DateUtil.convertDateToString(to_date,
						"yyyy-MM-dd HH:mm");
				logger.debug("to_date:" + to_date + "  to_time:"
						+ to_time);
				if (!to_time.equals(_finishTime)) {
					logger.debug("---编辑前后假期结束时间不一致---");
					return false;
				}
			} else if ("hour".equals(unit)) {
				String to_time = DateUtil.convertDateToString(to_date,
						"yyyy-MM-dd");
				logger.debug("to_date:" + to_date + "  to_time:"
						+ to_time);
				if (!(_finishTime.contains(to_time) && _finishTime
						.contains((String) map.get("to_half_day")))) {
					logger.debug("---编辑前后假期结束时间不一致---");
					return false;
				}
			}

		}
		return true;
	}

	private String restoreInstance(BaseModel baseModel) {
		logger.warn("-----还原原文档实例-----");
		try {
			// 获取流程主题
			String docSubject = (String) DingUtil.getModelPropertyString(
					baseModel,
					"docSubject", "", null);
			logger.warn("流程标题=>" + docSubject);
			String reviewMainId = baseModel.getFdId();
			logger.warn("流程主文档fdId=>" + reviewMainId);
			JSONObject input = new JSONObject();
			// 获取主文档创建者信息
			IBaseService obj = (IBaseService) SpringBeanUtil
					.getBean("kmReviewMainService");
			Object kmReviewMainObject = obj
					.findByPrimaryKey(reviewMainId);
			Class clazz = kmReviewMainObject.getClass();
			Method method = clazz.getMethod("getDocCreator");
			SysOrgPerson docCreator = (SysOrgPerson) method
					.invoke(kmReviewMainObject);
			String ekpUserId = docCreator.getFdId();
			Map map = DingUtil.getExtendDataModelInfo(baseModel);
			logger.warn("map:" + map);

			// 转换成钉钉的userid
			String userid = omsRelationService
					.getDingUserIdByEkpUserId(ekpUserId);
			logger.debug("创建者：" + docCreator.getFdName() + "  fdId:"
					+ ekpUserId + "  dindId:" + userid);

			if (StringUtil.isNull(userid)) {
				logger.warn("-----在钉钉对照表无法找到文档创建者对应的关系，不创建实例！-----");
				return null;
			}
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"fdEkpInstanceId =:fdEkpInstanceId and fdStatus=:fdStatus");
			hqlInfo.setParameter("fdEkpInstanceId", reviewMainId);
			hqlInfo.setParameter("fdStatus", "20");
			List<ThirdDingDinstanceXform> list = getThirdDingDinstanceXformService()
					.findList(hqlInfo);
			if (list != null && list.size() > 0) {
				logger.debug(docSubject + "------已存在实例模板，不再创建实例---------");
			} else {
				logger.debug(docSubject + "--------不存在可使用实例-----------");
				// 获取主文档的模板Id
				IBaseModel tempModel = (IBaseModel) clazz
						.getMethod("getFdTemplate")
						.invoke(kmReviewMainObject);
				logger.warn("-----模板ID-------" + tempModel.getFdId());

				// 文档创建时间
				Date date = (Date) clazz.getMethod("getDocCreateTime")
						.invoke(kmReviewMainObject);
				String createTime = "";
				if (date != null) {
					createTime = DateUtil.convertDateToString(date,
							"yyyy-MM-dd HH:mm");
				}
				logger.warn("--------createTime-------" + createTime);
				hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock(
						"fdTemplateId=:fdTemplateId and fdIsAvailable=:fdIsAvailable");
				hqlInfo.setParameter("fdTemplateId", tempModel.getFdId());
				hqlInfo.setParameter("fdIsAvailable", true);
				ThirdDingDtemplateXform temp = (ThirdDingDtemplateXform) getThirdDingDtemplateXformService()
						.findFirstOne(hqlInfo);
				if (temp != null) {
					logger.debug("钉钉模板名称：" + temp.getFdName());
					JSONObject param = new JSONObject();
					param.put("ekpUserId", ekpUserId);
					param.put("creater", docCreator.getFdName());
					param.put("createTime", createTime);
					String url = "/km/review/km_review_main/kmReviewMain.do?method=view&fdId="
							+ reviewMainId
							+ DingUtil.getDingAppKeyByEKPUserId("&", ekpUserId);
					// url = DingUtil.getDingPcUrl(url);
					String dingDomain = DingConfig.newInstance()
							.getDingDomain();
					if (StringUtil.isNull(dingDomain)) {
						dingDomain = ResourceUtil
								.getKmssConfigString("kmss.urlPrefix");
					}
					if (dingDomain.trim().endsWith("/")) {
						dingDomain = dingDomain.trim().substring(0,
								dingDomain.length() - 1);
					}
					url = dingDomain + url.trim();

					logger.debug("---url:" + url);
					logger.debug("url length:" + url.length());
					param.put("url", url);
					// 请假
					String extend_value = (String) map.get("extend_value");
					logger.warn("extend_value:" + extend_value);
					if (StringUtil.isNull(extend_value)) {
						return null;
					}

					// 请假单位
					String unit = "day";
					if (map.containsKey("unit")) {
						unit = (String) map.get("unit");
					} else {
						JSONObject extendObj = JSONObject
								.fromObject(extend_value);
						unit = extendObj.getString("unit");
					}

					// 开始时间
					String from_time = null;
					Date from_date = (Date) map.get("from_time");
					logger.debug("from_date:" + from_date);
					if ("day".equalsIgnoreCase(unit)) {
						from_time = DateUtil.convertDateToString(from_date,
								"yyyy-MM-dd");
					} else if ("halfDay".equalsIgnoreCase(unit)) {
						String fromHalfDay = (String) map
								.get("from_half_day");
						if ("AM".equalsIgnoreCase(fromHalfDay)) {
							fromHalfDay = "上午";
						} else if ("PM".equalsIgnoreCase(fromHalfDay)) {
							fromHalfDay = "下午";
						}
						from_time = DateUtil.convertDateToString(from_date,
								"yyyy-MM-dd") + " "
								+ fromHalfDay;
					} else if ("hour".equalsIgnoreCase(unit)) {
						from_time = DateUtil.convertDateToString(from_date,
								"yyyy-MM-dd HH:mm");
					}

					// 结束时间
					String to_time = null;
					Date to_date = (Date) map.get("to_time");
					logger.debug("from_date:" + from_date);
					if ("day".equalsIgnoreCase(unit)) {
						to_time = DateUtil.convertDateToString(to_date,
								"yyyy-MM-dd");
					} else if ("halfDay".equalsIgnoreCase(unit)) {
						String toHalfDay = (String) map.get("to_half_day");
						if ("AM".equalsIgnoreCase(toHalfDay)) {
							toHalfDay = "上午";
						} else if ("PM".equalsIgnoreCase(toHalfDay)) {
							toHalfDay = "下午";
						}
						to_time = DateUtil.convertDateToString(to_date,
								"yyyy-MM-dd") + " " + toHalfDay;
					} else if ("hour".equalsIgnoreCase(unit)) {
						to_time = DateUtil.convertDateToString(to_date,
								"yyyy-MM-dd HH:mm");
					}
					JSONObject typeJson = new JSONObject();
					typeJson.put("leaveCode",
							(String) map.get("leave_code"));
					typeJson.put("unit", unit);
					String leave_name = (String) map.get("leave_txt");
					param.put("请假类型", leave_name);
					param.put("type_extend_value", typeJson.toString());
					param.put("开始时间", from_time);
					param.put("结束时间", to_time);
					param.put("时长", (String) map.get("duration"));
					param.put("请假原因", (String) map.get("reason"));
					param.put("extend_value", extend_value);
					param.put("type", "attendance");

					OapiProcessWorkrecordCreateResponse response = DingNotifyUtil
							.createXformDistance(DingUtils.getDingApiService()
									.getAccessToken(),
									userid, temp, docSubject,
									temp.getFdDetail(), param);
					if (response != null && response.getErrcode() == 0) {
						String instanceId = response.getResult()
								.getProcessInstanceId();

						ThirdDingDinstanceXform distance = new ThirdDingDinstanceXform();
						distance.setFdName(docSubject);
						distance.setDocCreateTime(new Date());
						distance.setFdInstanceId(instanceId);
						distance.setFdDingUserId(userid);
						distance.setFdEkpInstanceId(reviewMainId);
						distance.setFdUrl(url);
						distance.setFdTemplate(temp);
						distance.setFdEkpUser(docCreator);
						distance.setFdStatus("20");

						List<ThirdDingIndanceXDetail> fdDetail = new ArrayList<ThirdDingIndanceXDetail>();
						ThirdDingIndanceXDetail detail = null;
						for (ThirdDingTemplateXDetail det : temp
								.getFdDetail()) {
							detail = new ThirdDingIndanceXDetail();
							detail.setFdName(det.getFdName());
							detail.setFdType("TextField");
							if (StringUtil.isNotNull(det.getFdName())
									&& param.containsKey(det.getFdName())) {
								detail.setFdValue(det.getFdName());
							} else if ("标题".equals(detail.getFdName())) {
								detail.setFdValue(docSubject);
							} else if ("创建者".equals(detail.getFdName())) {
								if (!param.containsKey("creater")) {
									detail.setFdValue("");
								} else {
									detail.setFdValue(
											param.getString("creater"));
								}
							} else if ("创建时间".equals(detail.getFdName())) {
								if (!param.containsKey("createTime")) {
									detail.setFdValue("");
								} else {
									detail.setFdValue(
											param.getString("createTime"));
								}
							} else {
								detail.setFdValue("");
							}
							fdDetail.add(detail);
						}
						distance.setFdDetail(fdDetail);
						getThirdDingDinstanceXformService().add(distance);
						return response.getBody();
					} else {
						logger.warn("创建实例失败：" + response == null ? "返回结果为null"
								: response.getBody());
						// sengDingErrorNotify(baseModel, "创建实例失败,请及时处理");
						return response == null ? null : response.getBody();
					}

				} else {
					logger.warn("主文档  " + docSubject
							+ " 对应的模板没有同步到钉钉，请检查模板情况!templateId:"
							+ tempModel.getFdId());
					return null;
				}

			}

		} catch (Exception e) {
			logger.error("创建实例失败", e);
			// sengDingErrorNotify(baseModel, "创建实例失败,请及时处理");
		}
		return null;
	}

	private ThirdDingDinstanceXform getDingInstanceId(String fdId) {

		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"fdEkpInstanceId=:fdEkpInstanceId and fdStatus=:fdStatus");
			hqlInfo.setParameter("fdEkpInstanceId", fdId);
			hqlInfo.setParameter("fdStatus", "20");
			return (ThirdDingDinstanceXform) getThirdDingDinstanceXformService()
					.findFirstOne(hqlInfo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public boolean createXformDisdance(BaseModel baseModel, String type,
			String _startTime, String _finishTime, String leaveCode,
			String leaveTimeInfo, String _unit, String docSubject,
			String reason) {
		try {
			// 获取流程主题
			logger.warn("流程标题=>" + docSubject);
			String reviewMainId = baseModel.getFdId();
			logger.warn("流程主文档fdId=>" + reviewMainId);
			JSONObject input = new JSONObject();
			// 获取主文档创建者信息
			IBaseService obj = (IBaseService) SpringBeanUtil
					.getBean("kmReviewMainService");
			Object kmReviewMainObject = obj
					.findByPrimaryKey(reviewMainId);
			Class clazz = kmReviewMainObject.getClass();
			Method method = clazz.getMethod("getDocCreator");
			SysOrgPerson docCreator = (SysOrgPerson) method
					.invoke(kmReviewMainObject);
			String ekpUserId = docCreator.getFdId();

			// 转换成钉钉的userid
			String userid = getOmsRelationService()
					.getDingUserIdByEkpUserId(ekpUserId);
			logger.debug("创建者：" + docCreator.getFdName() + "  fdId:"
					+ ekpUserId + "  dindId:" + userid);

			if (StringUtil.isNull(userid)) {
				logger.warn("-----在钉钉对照表无法找到文档创建者对应的关系，不创建实例！-----");
				return false;
			}
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"fdEkpInstanceId =:fdEkpInstanceId and fdStatus=:fdStatus");
			hqlInfo.setParameter("fdEkpInstanceId", reviewMainId);
			hqlInfo.setParameter("fdStatus", "20");
			List<ThirdDingDinstanceXform> list = getThirdDingDinstanceXformService()
					.findList(hqlInfo);
			if (list != null && list.size() > 0) {
				logger.debug(docSubject + "------已存在实例模板，不再创建实例---------");
			} else {
				logger.debug(docSubject + "--------不存在可使用实例-----------");
				// 获取主文档的模板Id
				IBaseModel tempModel = (IBaseModel) clazz
						.getMethod("getFdTemplate")
						.invoke(kmReviewMainObject);
				logger.warn("-----模板ID-------" + tempModel.getFdId());

				// 文档创建时间
				Date date = (Date) clazz.getMethod("getDocCreateTime")
						.invoke(kmReviewMainObject);
				String createTime = "";
				if (date != null) {
					createTime = DateUtil.convertDateToString(date,
							"yyyy-MM-dd HH:mm");
				}
				logger.warn("--------createTime-------" + createTime);
				hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock(
						"fdTemplateId=:fdTemplateId and fdIsAvailable=:fdIsAvailable");
				hqlInfo.setParameter("fdTemplateId", tempModel.getFdId());
				hqlInfo.setParameter("fdIsAvailable", true);
				ThirdDingDtemplateXform temp = (ThirdDingDtemplateXform) getThirdDingDtemplateXformService()
						.findFirstOne(hqlInfo);
				if (temp != null) {
					logger.debug("钉钉模板名称：" + temp.getFdName());

					JSONObject param = new JSONObject();
					param.put("ekpUserId", ekpUserId);
					param.put("creater", docCreator.getFdName());
					param.put("createTime", createTime);
					String url = "/km/review/km_review_main/kmReviewMain.do?method=view&fdId="
							+ reviewMainId
							+ DingUtil.getDingAppKeyByEKPUserId("&", ekpUserId);
					// url = DingUtil.getDingPcUrl(url);
					String dingDomain = DingConfig.newInstance()
							.getDingDomain();
					if (StringUtil.isNull(dingDomain)) {
						dingDomain = ResourceUtil
								.getKmssConfigString("kmss.urlPrefix");
					}
					if (dingDomain.trim().endsWith("/")) {
						dingDomain = dingDomain.trim().substring(0,
								dingDomain.length() - 1);
					}
					url = dingDomain + url.trim();

					logger.debug("---url:" + url);
					logger.debug("url length:" + url.length());
					param.put("url", url);
					if ("attendance".equals(type)) {
						// 请假
						String extend_value = leaveTimeInfo;
						logger.warn("extend_value:" + extend_value);
						if (StringUtil.isNull(extend_value)) {
							return false;
						}

						com.alibaba.fastjson.JSONObject extend_json = com.alibaba.fastjson.JSONObject
								.parseObject(leaveTimeInfo);
						String leave_name = extend_json
								.getJSONObject("extension").getString("tag");

						// 请假单位
						String unit = "day";
						if (StringUtil.isNotNull(_unit)) {
							unit = _unit;
						} else {
							unit = extend_json.getString("unit");
						}
						logger.debug("unit:" + unit);
						// 时长
						float times = 0;
						if ("day".equalsIgnoreCase(unit)
								|| "halfDay".equalsIgnoreCase(unit)) {
							times = extend_json.getFloatValue("durationInDay");
						} else if ("hour".equalsIgnoreCase(unit)) {
							times = extend_json.getFloatValue("durationInHour");
						}
						JSONObject typeJson = new JSONObject();
						typeJson.put("leaveCode", leaveCode);
						typeJson.put("unit", unit);
						param.put("请假类型", leave_name);
						param.put("type_extend_value", typeJson.toString());
						param.put("开始时间", _startTime);
						param.put("结束时间", _finishTime);
						param.put("时长", times);
						param.put("请假原因", reason);
						param.put("extend_value", extend_value);
						param.put("type", type);

					}
					OapiProcessWorkrecordCreateResponse response = DingNotifyUtil
							.createXformDistance(DingUtils.getDingApiService()
									.getAccessToken(),
									userid, temp, docSubject,
									temp.getFdDetail(), param);
					if (response != null && response.getErrcode() == 0) {
						String instanceId = response.getResult()
								.getProcessInstanceId();

						ThirdDingDinstanceXform distance = new ThirdDingDinstanceXform();
						distance.setFdName(docSubject);
						distance.setDocCreateTime(new Date());
						distance.setFdInstanceId(instanceId);
						distance.setFdDingUserId(userid);
						distance.setFdEkpInstanceId(reviewMainId);
						distance.setFdUrl(url);
						distance.setFdTemplate(temp);
						distance.setFdEkpUser(docCreator);
						distance.setFdStatus("20");

						List<ThirdDingIndanceXDetail> fdDetail = new ArrayList<ThirdDingIndanceXDetail>();
						ThirdDingIndanceXDetail detail = null;
						for (ThirdDingTemplateXDetail det : temp
								.getFdDetail()) {
							detail = new ThirdDingIndanceXDetail();
							detail.setFdName(det.getFdName());
							detail.setFdType("TextField");
							if (StringUtil.isNotNull(det.getFdName())
									&& param.containsKey(det.getFdName())) {
								detail.setFdValue(String
										.valueOf(param.get(det.getFdName())));
							} else if ("标题".equals(detail.getFdName())) {
								detail.setFdValue(docSubject);
							} else if ("创建者".equals(detail.getFdName())) {
								if (!param.containsKey("creater")) {
									detail.setFdValue("");
								} else {
									detail.setFdValue(
											param.getString("creater"));
								}
							} else if ("创建时间".equals(detail.getFdName())) {
								if (!param.containsKey("createTime")) {
									detail.setFdValue("");
								} else {
									detail.setFdValue(
											param.getString("createTime"));
								}
							} else {
								detail.setFdValue("");
							}
							fdDetail.add(detail);
						}
						distance.setFdDetail(fdDetail);
						getThirdDingDinstanceXformService().add(distance);
						return true;
					} else {
						logger.warn("创建实例失败：" + response == null ? "返回结果为null"
								: response.getBody());
						return false;
						// sengDingErrorNotify(baseModel, "创建实例失败,请及时处理");
					}

				} else {
					logger.warn("主文档  " + docSubject
							+ " 对应的模板没有同步到钉钉，请检查模板情况!templateId:"
							+ tempModel.getFdId());
					return false;
				}

			}

		} catch (Exception e) {
			logger.error("创建实例失败", e);
			// sengDingErrorNotify(baseModel, "创建实例失败,请及时处理");
		}
		return false;
	}

	public ActionForward cancelLeave(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String ekpUserId = request.getParameter("ekpUserId");
		if (StringUtil.isNull(ekpUserId)) {
			ekpUserId = UserUtil.getUser().getFdId();
		}
		JSONArray result = BizsuiteUtil.getCancelInfo(ekpUserId,
				"请假");
		logger.debug(result);
		response.getWriter().write(result.toString());
		return null;
	}

	/*
	 * 判断是否超过了销假的时间 30天
	 */
	public ActionForward cancelLeaveExpire(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json;charset=UTF-8");
		String fd_ekp_instance_id = request.getParameter("fd_ekp_instance_id");
		if (StringUtil.isNull(fd_ekp_instance_id)) {
			logger.warn("【销假审批是否过期】fd_ekp_instance_id不能为空");
			return null;
		}
		JSONObject rsp = new JSONObject();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"fdApproveId=:fdApproveId and fdIstrue=:fdIstrue");
		hqlInfo.setParameter("fdApproveId", fd_ekp_instance_id);
		hqlInfo.setParameter("fdIstrue", "1");
		ThirdDingLeavelog leaveLog = (ThirdDingLeavelog) getThirdDingLeavelogService()
				.findFirstOne(hqlInfo);
		if (leaveLog != null) {
			rsp.put("errcode", 0);
			rsp.put("errmsg", "ok");
			rsp.put("leaveEndTime", DateUtil.convertDateToString(
					leaveLog.getDocCreateTime(), "yyyy-MM-dd HH:mm"));
			Date time = leaveLog.getDocCreateTime();
			Long curTime = System.currentTimeMillis();
			if (curTime - time.getTime() > 30 * 24 * 60 * 60 * 1000L) {
				rsp.put("cancel", false);
			} else {
				rsp.put("cancel", true);
			}
		} else {
			rsp.put("errcode", -1);
			rsp.put("errmsg", "无法找到请假主文档记录！！！");
		}
		response.getWriter().write(rsp.toString());
		return null;
	}

	// 获取用户班次信息
	public ActionForward getUserAttendenceClassInfo(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String ekpUserId = request.getParameter("ekpUserId");
		if (StringUtil.isNull(ekpUserId)) {
			ekpUserId = UserUtil.getUser().getFdId();
		}
		// 转换为钉钉id
		String dingUserId = getOmsRelationService()
				.getDingUserIdByEkpUserId(ekpUserId);
		if (StringUtil.isNull(dingUserId)) {
			logger.error("根据用户id:" + ekpUserId + "无法找到对应的钉钉id,请检查对照表信息！");
			return null;
		}
		String date = request.getParameter("dateList"); // 日期时间格式,中间用;隔开
		logger.debug("date:" + date);
		if (StringUtil.isNull(date)) {
			logger.error("日期为空！");
			return null;
		}
		String[] date_array = date.split(";");
		Map<String, Long> date_classId = new HashMap<String, Long>();
		DingApiService dingApiService = DingUtils.getDingApiService();
		Map<Long, Object> classId_info = new HashMap<Long, Object>();
		JSONObject param;
		for (String d : date_array) {
			Long time = DateUtil.convertStringToDate(d).getTime();
			param = new JSONObject();
			param.put("op_user_id", dingUserId);
			param.put("user_id", dingUserId);
			param.put("date_time", time);
			logger.debug("param:" + param);
			JSONObject rs = dingApiService.scheduleByDay(param, ekpUserId);
			logger.debug("rs:" + rs);
			if (rs.getInt("errcode") == 0) {

				if (!rs.containsKey("result")) {
					date_classId.put(d, 0L);
				} else {
					JSONArray result = rs.getJSONArray("result");
					if (result == null || result.isEmpty()) {
						logger.error("result为空！");
						return null;
					}
					Long classId = result.getJSONObject(0).getLong("class_id");
					date_classId.put(d, classId);
					classId_info.put(classId, classId);
				}

			} else {
				logger.error("查询排班信息失败！" + rs);
				return null;
			}
		}
		for (Long cid : classId_info.keySet()) {
			String classInfo = dingApiService.queryShift(dingUserId, cid);
			logger.debug("classInfo:" + classInfo);
			classId_info.put(cid, JSONObject.fromObject(classInfo));
		}

		JSONObject rsp = new JSONObject();
		for (String d : date_array) {
			if(date_classId.get(d) == 0){
				rsp.put(d, "");
			}else{
				rsp.put(d, classId_info.get(date_classId.get(d)));
			}
		}

		logger.debug("查询结果：" + rsp);
		response.getWriter().write(rsp.toString());
		return null;
	}

	// 个人补卡次数获取
	public ActionForward getCheckInfo(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String ekpUserId = request.getParameter("ekpUserId");
		logger.debug("个人补卡次数获取---ekpUserId:" + ekpUserId);
		if (StringUtil.isNull(ekpUserId)) {
			ekpUserId = UserUtil.getUser().getFdId();
		}
		JSONObject result = BizsuiteUtil.getCheckInfo(ekpUserId);
		logger.debug(result);
		response.getWriter().write(result.toString());
		return null;
	}

	// 获取用户异常考勤信息
	public ActionForward getSupplyDates(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String ekpUserId = request.getParameter("ekpUserId");
		if (StringUtil.isNull(ekpUserId)) {
			ekpUserId = UserUtil.getUser().getFdId();
		}
		String date = request.getParameter("date"); // 日期时间格式
		logger.debug("date:" + date);
		JSONObject result = BizsuiteUtil.getSupplyDates(ekpUserId, date);
		logger.debug(result);
		response.getWriter().write(result.toString());
		return null;
	}

	// 判断能否补卡
	public ActionForward canSupply(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.warn("---------判断能否补卡------------");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String ekpUserId = request.getParameter("ekpUserId");
		if (StringUtil.isNull(ekpUserId)) {
			ekpUserId = UserUtil.getUser().getFdId();
		}
		String date = request.getParameter("date"); // 日期时间格式，补卡时间
		logger.warn("date:" + date);

		String extendValue = request.getParameter("extendValue");
		logger.warn("extendValue:" + extendValue);
		if (StringUtil.isNull(extendValue)) {
			logger.error("extendValue参数不能为空");
			return null;
		}
		// JSONObject extendValueJSON = JSONObject.fromObject(extendValue);
		JSONObject result = BizsuiteUtil.canSupply(ekpUserId, date,
				extendValue);
		logger.debug(result);
		if (result != null) {
			response.getWriter().write(result.toString());
		}
		return null;
	}

	// 判断能否换班，换班许可后返回换班信息
	public ActionForward canRelieveCheck(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.warn("---------判断能否换班，换班许可后返回换班信息------------");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		JSONObject error = new JSONObject();
		// 测试数据
		String test = request.getParameter("test");
		if ("true".equalsIgnoreCase(test)) {
			String flag = request.getParameter("flag");
			boolean canflag = false;
			if ("true".equalsIgnoreCase(flag)) {
				canflag = true;
			}
			JSONObject result = canRelieveCheck(canflag);
			if (result != null && !result.isEmpty()
					&& result.getInt("errcode") == 0) {
				try {
					String value = result.getJSONObject("result")
							.getJSONArray("form_data_list").getJSONObject(0)
							.getString("value");
					JSONObject canInfo = JSONObject.fromObject(value);
					boolean canRelieveFlag = canInfo.getJSONObject("result")
							.getBoolean("success");
					if (canRelieveFlag) {
						logger.debug("----可以换班----");
						// 获取换班信息
						JSONObject relieveInfo = getRelieveInfo();
						logger.debug("换班信息:" + relieveInfo);
						response.getWriter()
								.write(relieveInfo.toString());
						return null;
					} else {
						logger.debug("----不允许换班----");
						String reason = canInfo.getJSONObject("result")
								.getString("reason");
						// 构造返回的数据
						error.put("errcode", -1);
						error.put("errmsg", reason);
						error.put("canRelieve", false);
						response.getWriter().write(error.toString());
						return null;
					}
				} catch (Exception e) {
					logger.error("获取是否能换班标识失败：" + e.getMessage(), e);
				}
			}

			return null;
		}

		// 换班人
		String applicantStaff_ekpid = request
				.getParameter("applicantStaff_ekpid");
		if (StringUtil.isNull(applicantStaff_ekpid)) {
			logger.warn("换班人参数applicantStaff_ekpid为空");
		}
		// 替班人
		String reliefStaff_ekpid = request.getParameter("reliefStaff_ekpid");
		if (StringUtil.isNull(reliefStaff_ekpid)) {
			logger.warn("替班人参数reliefStaff_ekpid为空");
		}

		// 换班日期
		String relieveDatetime = request.getParameter("relieveDatetime"); // 日期时间格式，年-月-日
		logger.warn("relieveDatetime:" + relieveDatetime);

		// 还班日期
		String backDatetime = request.getParameter("backDatetime"); // 日期时间格式，年-月-日
		logger.warn("backDatetime:" + backDatetime);

		JSONObject result = BizsuiteUtil.canRelieveCheck(applicantStaff_ekpid,
				reliefStaff_ekpid, relieveDatetime, backDatetime);
		logger.debug(result);

		if (result != null && !result.isEmpty()
				&& result.getInt("errcode") == 0) {
			try {
				String value = result.getJSONObject("result")
						.getJSONArray("form_data_list").getJSONObject(0)
						.getString("value");
				JSONObject canInfo = JSONObject.fromObject(value);
				boolean canRelieveFlag = canInfo.getJSONObject("result")
						.getBoolean("success");
				if (canRelieveFlag) {
					logger.debug("----可以换班----");
					// 获取换班信息
					JSONObject relieveInfo = BizsuiteUtil.getRelieveInfo(
							applicantStaff_ekpid,
							reliefStaff_ekpid, relieveDatetime, backDatetime);
					logger.debug("换班信息:" + relieveInfo);
					response.getWriter()
							.write(relieveInfo == null ? null
									: relieveInfo.toString());
					return null;
				} else {
					logger.debug("----不允许换班----");
					String reason = canInfo.getJSONObject("result")
							.getString("reason");
					// 构造返回的数据
					error.put("errcode", -1);
					error.put("errmsg", reason);
					error.put("canRelieve", false);
					response.getWriter().write(error.toString());
					return null;
				}
			} catch (Exception e) {
				logger.error("获取是否能换班标识失败：" + e.getMessage(), e);
			}
		} else {
			logger.warn("换班判断不通过：" + result);
			response.getWriter()
					.write(result == null ? null : result.toString());
		}
		return null;
	}

	// 测试接口--判断能否换班，flag为true -> 可以换班
	private JSONObject canRelieveCheck(Boolean flag) {
		JSONObject info = new JSONObject();
		info.put("errcode", 0);
		JSONObject rs = new JSONObject();
		JSONArray array = new JSONArray();
		JSONObject value = new JSONObject();
		String valueRs = "{\"code\":\"0\",\"message\":\"success\",\"result\":{\"reason\":\"还班日期必须晚于换班日期，请重新选择\",\"success\":"
				+ flag + "},\"success\":true}";
		value.put("value", valueRs);
		array.add(value);
		rs.put("form_data_list", array);
		info.put("result", rs);
		return info;
	}

	// 测试接口--判断能否换班，flag为true -> 可以换班
	private JSONObject getRelieveInfo() {
		JSONObject info = new JSONObject();
		info.put("errcode", 0);
		JSONObject rs = new JSONObject();
		JSONArray array = new JSONArray();
		JSONObject value = new JSONObject();
		String extend_value = "{\"code\":\"0\",\"message\":\"success\",\"result\":[{\"classDesc\":\"chw班次\",\"classId\":699395035,\"className\":\"chw班次\",\"userId\":\"13415813508\",\"workDate\":1614614400000},{\"classDesc\":\"晚班考勤\",\"classId\":597441126,\"className\":\"晚班考勤\",\"userId\":\"141160342728828812\",\"workDate\":1614614400000},{\"classDesc\":\"chw班次\",\"classId\":699395035,\"className\":\"chw班次\",\"userId\":\"13415813508\",\"workDate\":1614614400000},{\"classDesc\":\"晚班考勤\",\"classId\":597441126,\"className\":\"晚班考勤\",\"userId\":\"141160342728828812\",\"workDate\":1614614400000}],\"success\":true}";
		String valueRs = "{\"backInfo\":\"火旺从\\\"chw班次\\\"换为\\\"晚班考勤\\\",熊浩淋从\\\"晚班考勤\\\"换为\\\"chw班次\\\"\",\"success\":\"true\",\"relieveInfo\":\"火旺从\\\"chw班次\\\"换为\\\"晚班考勤\\\",熊浩淋从\\\"晚班考勤\\\"换为\\\"chw班次\\\"\"}";
		value.put("value", valueRs);
		value.put("extend_value", extend_value);
		array.add(value);
		rs.put("form_data_list", array);
		info.put("result", rs);
		return info;
	}

	/*
	 * 计算加班时长
	 */
	public ActionForward getOvertimeDuration(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.debug("---------计算加班时长------------");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json;charset=UTF-8");
		String ekpUserIds = request.getParameter("ekpUserIds"); // 多人则用;隔开
		if (StringUtil.isNull(ekpUserIds)) {
			logger.warn("加班人不能为空！");
			return null;
		}
		String startTime = request.getParameter("startTime"); // 开始时间：2021-02-25
																// 16:00
		logger.debug("startTime:" + startTime);

		String finishTime = request.getParameter("finishTime"); // 结束时间
		logger.debug("finishTime:" + finishTime);

		JSONObject result = BizsuiteUtil.getOvertimeDuration(ekpUserIds,
				startTime,
				finishTime);
		logger.debug("加班计算时长返回结果：" + result);
		JSONObject rsp = new JSONObject();
		logger.debug(result);
		if (result != null && !result.isEmpty()) {
			if (result.containsKey("errcode")
					&& result.getInt("errcode") == 0) {
				try {
					String extendValue = result.getJSONObject("result")
							.getJSONArray("form_data_list").getJSONObject(0)
							.getString("value");

					JSONObject valObj = JSONObject.fromObject(extendValue);
					boolean isCanOvertime = valObj.getBoolean("isCanOvertime");
					logger.debug("isCanOvertime:" + isCanOvertime);
					rsp.put("errcode", 0);
					rsp.put("isCanOvertime", isCanOvertime);
					if (isCanOvertime) {
						// 能加班
						rsp.put("durationData", valObj
								.getJSONObject("durationData").toString());
						rsp.put("durationInHour",
								valObj.getJSONObject("durationData")
										.getDouble("durationInHour"));
						rsp.put("errmsg", "允许加班");
					} else {
						// 不能加班
						rsp.put("errmsg", "加班人班次信息不一致，不允许同一批次加班申请");
						rsp.put("differentStaffIds",
								valObj.getJSONArray("differentStaffIds"));

						JSONArray differentStaffIds = valObj
								.getJSONArray("differentStaffIds");
						String differentEkpfdId = "";
						if (differentStaffIds != null
								&& !differentStaffIds.isEmpty()) {
							for (int i = 0; i < differentStaffIds.size(); i++) {
								String fdId = getOmsRelationService()
										.getEkpUserIdByDingUserId(
												differentStaffIds.getString(i));
								differentEkpfdId += fdId + ";";
							}
						}
						if (StringUtil.isNotNull(differentEkpfdId)) {
							if (differentEkpfdId.endsWith(";")) {
								differentEkpfdId = differentEkpfdId.substring(0,
										differentEkpfdId.length() - 1);
							}
							rsp.put("differentEkpfdId", differentEkpfdId);
						}
					}
					response.getWriter().write(rsp.toString());
				} catch (Exception e) {
					rsp.put("errcode", -1);
					rsp.put("errmsg", e.getMessage());
					response.getWriter().write(rsp.toString());
					logger.warn(e.getMessage(), e);
				}
			} else {
				logger.warn("计算加班时长异常：" + result);
				response.getWriter().write(result.toString());
			}

		} else {
			rsp.put("errcode", -1);
			rsp.put("errmsg", "钉钉接口返回为空");
			response.getWriter().write(rsp.toString());
		}
		return null;
	}
}
