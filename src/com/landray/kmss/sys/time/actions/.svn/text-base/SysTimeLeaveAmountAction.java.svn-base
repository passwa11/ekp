package com.landray.kmss.sys.time.actions;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.fssc.common.util.FsscNumberUtil;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaForm;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.time.forms.SysTimeLeaveAmountForm;
import com.landray.kmss.sys.time.forms.SysTimeLeaveAmountItemForm;
import com.landray.kmss.sys.time.interfaces.ISysTimeCountService;
import com.landray.kmss.sys.time.model.SysTimeLeaveAmount;
import com.landray.kmss.sys.time.model.SysTimeLeaveAmountItem;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountItemService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveRuleService;
import com.landray.kmss.sys.time.util.SysTimePersonUtil;
import com.landray.kmss.sys.time.util.SysTimeUtil;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-12-12
 */
public class SysTimeLeaveAmountAction extends SysTimeImportAction {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysTimeLeaveAmountAction.class);

	private ISysTimeLeaveAmountService sysTimeLeaveAmountService;
	private ISysTimeLeaveRuleService sysTimeLeaveRuleService;
	private ISysOrgCoreService sysOrgCoreService;
	private IHrStaffPersonInfoService hrStaffPersonInfoService;
	private ISysOrgElementService sysOrgElementService;
	private ISysTimeCountService sysTimeCountService;

	@Override
	protected ISysTimeLeaveAmountService
			getServiceImp(HttpServletRequest request) {
		if (sysTimeLeaveAmountService == null) {
			sysTimeLeaveAmountService = (ISysTimeLeaveAmountService) getBean(
					"sysTimeLeaveAmountService");
		}
		return sysTimeLeaveAmountService;
	}

	public ISysTimeLeaveRuleService getSysTimeLeaveRuleService() {
		if (sysTimeLeaveRuleService == null) {
			sysTimeLeaveRuleService = (ISysTimeLeaveRuleService) getBean(
					"sysTimeLeaveRuleService");
		}
		return sysTimeLeaveRuleService;
	}

	public ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) getBean(
					"sysOrgCoreService");
		}
		return sysOrgCoreService;
	}

	public IHrStaffPersonInfoService getHrStaffPersonInfoService() {
		if (hrStaffPersonInfoService == null) {
			hrStaffPersonInfoService = (IHrStaffPersonInfoService) getBean(
					"hrStaffPersonInfoService");
		}
		return hrStaffPersonInfoService;
	}

	public ISysOrgElementService getSysOrgElementService() {
		if (sysOrgElementService == null) {
			sysOrgElementService = (ISysOrgElementService) getBean(
					"sysOrgElementService");
		}
		return sysOrgElementService;
	}

	public ISysTimeCountService getSysTimeCountService() {
		if (sysTimeCountService == null) {
			sysTimeCountService = (ISysTimeCountService) getBean(
					"sysTimeCountService");
		}
		return sysTimeCountService;
	}

	public ISysTimeLeaveAmountItemService sysTimeLeaveAmountItemService;

	public ISysTimeLeaveAmountItemService getSysTimeLeaveAmountItemService(){
		if(sysTimeLeaveAmountItemService == null){
			sysTimeLeaveAmountItemService = (ISysTimeLeaveAmountItemService)SpringBeanUtil.getBean("sysTimeLeaveAmountItemService");
		}
		return sysTimeLeaveAmountItemService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);

		// 以下筛选属性需要手工定义筛选范围
		String _fdKey = cv.poll("_fdKey");
		String[] _fdDept = cv.polls("_fdDept");

		String hqlWhere = hqlInfo.getWhereBlock();
		StringBuffer whereBlock = null;
		if (StringUtil.isNotNull(hqlWhere)) {
			whereBlock = new StringBuffer(hqlWhere);
		} else {
			whereBlock = new StringBuffer("1 = 1");
		}
		String personId = request.getParameter("personId");
		if (StringUtil.isNotNull(personId)) {
			whereBlock
					.append(" and sysTimeLeaveAmount.fdPerson.fdId=:personId");
			hqlInfo.setParameter("personId", personId);
		}
		// 姓名、登录名、手机号或邮箱
		if (StringUtil.isNotNull(_fdKey)) {
			whereBlock
					.append(" and (sysTimeLeaveAmount.fdPerson.fdName like :fdKey");
			whereBlock
					.append(" or sysTimeLeaveAmount.fdPerson.fdLoginName like :fdKey");
			whereBlock
					.append(" or sysTimeLeaveAmount.fdPerson.fdMobileNo like :fdKey");
			whereBlock
					.append(" or sysTimeLeaveAmount.fdPerson.fdEmail like :fdKey)");
			hqlInfo.setParameter("fdKey", "%" + _fdKey + "%");
		}
		// 部门
		if (_fdDept != null) {
			List<String> tmpIds = Arrays.asList(_fdDept);
			List orgIds = SysTimePersonUtil.expandToPersonIds(tmpIds);
			if (orgIds != null && !orgIds.isEmpty()) {
				whereBlock.append(" and " + HQLUtil.buildLogicIN(
						"sysTimeLeaveAmount.fdPerson.fdId", orgIds));
			} else {
				whereBlock.append(" and 1=2");
			}
		}
		// 员工状态
		String[] _fdStatus = cv.polls("_fdStatus");
		String fromModule = request.getParameter("fromModule");
		if (_fdStatus != null && _fdStatus.length > 0 && "hr".equals(fromModule)
				&& SysTimeUtil.moduleExist("/hr/staff")) {
			List<String> fdStatus = new ArrayList<String>();
			boolean isNull = false;
			for (String _fdStatu : _fdStatus) {
				if ("official".equals(_fdStatu)) {
					isNull = true;
				}
				fdStatus.add(_fdStatu);
			}
			String where = "";
			if (isNull) {
				where += " and (hrStaffPersonInfo.fdStatus in (:hrStatus) or hrStaffPersonInfo.fdStatus is null)";
			} else {
				where += " and hrStaffPersonInfo.fdStatus in (:hrStatus)";
			}
			hqlInfo.setFromBlock(
					"com.landray.kmss.sys.time.model.SysTimeLeaveAmount sysTimeLeaveAmount"
							+ ",com.landray.kmss.hr.staff.model.HrStaffPersonInfo as hrStaffPersonInfo");
			whereBlock.append(
					" and hrStaffPersonInfo.fdOrgPerson.fdId=sysTimeLeaveAmount.fdPerson.fdId"
							+ where);
			hqlInfo.setParameter("hrStatus", fdStatus);
		}
		// 人员
		String _fdPerson = cv.poll("_fdPerson");
		if (StringUtil.isNotNull(_fdPerson)) {
			whereBlock
					.append(" and sysTimeLeaveAmount.fdPerson.fdId=:fdPersonId");
			hqlInfo.setParameter("fdPersonId", _fdPerson);
		}
		// 年份
		String _fdYear = cv.poll("_fdYear");
		if (StringUtil.isNotNull(_fdYear)) {
			whereBlock.append(" and sysTimeLeaveAmount.fdYear=:year");
			hqlInfo.setParameter("year", Integer.parseInt(_fdYear));
		}
		hqlInfo.setWhereBlock(whereBlock.toString());
	}

	@Override
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-edit", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
			SysTimeLeaveAmountForm amountForm = (SysTimeLeaveAmountForm) request
					.getAttribute("sysTimeLeaveAmountForm");
			List<SysTimeLeaveRule> leaveRuleList = getServiceImp(null)
					.getAllLeaveRule();
			List<SysTimeLeaveAmountItemForm> itemList = amountForm
					.getFdAmountItems();
			AutoArrayList tmpList = new AutoArrayList(
					SysTimeLeaveAmountItemForm.class);
			// 加载最新的配置
			for (SysTimeLeaveRule leaveRule : leaveRuleList) {
				boolean isFind = false;
				for (SysTimeLeaveAmountItemForm itemForm : itemList) {
					if (leaveRule.getFdSerialNo().equals(itemForm.getFdLeaveType())) {
						isFind = true;
						if (Boolean.TRUE.equals(leaveRule.getFdIsAmount())
								&& Boolean.TRUE.equals(leaveRule.getFdIsAvailable()) ) {
							Integer fdAmountType = leaveRule.getFdAmountType();
							itemForm.setFdOrder(leaveRule.getFdOrder());
							itemForm.setRuleCreateTime(leaveRule.getDocCreateTime());
							if (fdAmountType.equals(1)) {
								// 手动发放
								itemForm.setFdIsAuto("false");
								itemForm.setFdIsAccumulate("false");
							} else {
								// 自动发放
								itemForm.setFdIsAuto("true");
								Float usedDay = itemForm.getFdUsedDay() == null
										? 0f
										: Float.parseFloat(
												itemForm.getFdUsedDay());
								if (fdAmountType==3||fdAmountType == 4) {
									String fdTotalDay = itemForm.getFdTotalDay();
									if (StringUtil.isNull(fdTotalDay)) {
										fdTotalDay="0";
									}
									BigDecimal bigDecimal =new BigDecimal(Float.parseFloat(fdTotalDay));
									int bigComPareTo = bigDecimal.compareTo(new BigDecimal(0));
									Float autoTotalItem = bigComPareTo== 0 ? 0f: Float.parseFloat(fdTotalDay);
									itemForm.setFdTotalDay(fdTotalDay + "");
									itemForm.setFdRestDay((autoTotalItem - usedDay) + "");
								}else{
									Float autoTotal = leaveRule.getFdAutoAmount() == null ? 0f: leaveRule.getFdAutoAmount();
									itemForm.setFdTotalDay(autoTotal + "");
									itemForm.setFdRestDay(
											(autoTotal - usedDay) + "");
								}
								
								Integer calType = leaveRule
										.getFdAmountCalType();
								if (calType == null) {
									continue;
								}
								Integer year = Integer
										.parseInt(amountForm.getFdYear());
								Date fdValidDate =  null;
								if(StringUtil.isNotNull(itemForm.getFdValidDate())) {
									fdValidDate = DateUtil.convertStringToDate(itemForm.getFdValidDate(), DateUtil.TYPE_DATE,null);
								}
								if (calType == 1) {
									// 到期清零
									itemForm.setFdIsAccumulate("false");
									Date validDate = getValidDate(year,fdValidDate, 0);
									itemForm.setFdValidDate(DateUtil
											.convertDateToString(validDate,
													DateUtil.TYPE_DATE, null));
									itemForm.setFdIsAvail(
											getIsAfterToday(validDate) + "");
								} else if (calType == 2) {
									// 到期不清零
									itemForm.setFdIsAccumulate("true");
									itemForm.setFdValidDate(null);
									itemForm.setFdIsAvail("true");
								} else if (calType == 3) {
									// 到期清零，延长有效期
									itemForm.setFdIsAccumulate("false");
									Date validDate = getValidDate(year,fdValidDate, 0);
									itemForm.setFdValidDate(
											DateUtil.convertDateToString(
													validDate,
													DateUtil.TYPE_DATE, null));
									itemForm.setFdIsAvail(
											getIsAfterToday(validDate) + "");
								}
							}
							tmpList.add(itemForm);
						}
					}
				}
				if(!isFind){
					SysTimeLeaveAmountItemForm itemForm = genAmountItem(
							amountForm, leaveRule);
					if (itemForm != null) {
						tmpList.add(itemForm);
					}
				}
			}
			
			amountForm.setFdAmountItems(tmpList);
			request.setAttribute("sysTimeLeaveAmountForm", amountForm);

			//是否是最后一年
			boolean lastYear = false;
			if(StringUtil.isNotNull(amountForm.getFdYear()) && StringUtil.isNotNull(amountForm.getFdPersonId())) {
				lastYear = getServiceImp(request).isLastYear(Integer.parseInt(amountForm.getFdYear()), amountForm.getFdPersonId());
			}
			request.setAttribute("lastYear", lastYear);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-edit", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("edit", mapping, form, request, response);
		}
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		IBaseModel model = null;
		IExtendForm rtnForm = null;
		String id = request.getParameter("fdId");
		if (!StringUtil.isNull(id)) {
			model = getServiceImp(request).findByPrimaryKey(id, null, true);
			if (model != null) {
				rtnForm = getServiceImp(request).convertModelToForm(
						(IExtendForm) form, model, new RequestContext(request));
			}
		}
		if (rtnForm == null) {
			throw new NoRecordException();
		}
		List<SysTimeLeaveRule> leaveRuleList = getServiceImp(request)
				.getAllLeaveRule();
		SysTimeLeaveAmountForm amountForm = (SysTimeLeaveAmountForm) rtnForm;
		AutoArrayList itemFormList = amountForm.getFdAmountItems();
		for (int k = 0; k < itemFormList.size(); k++) {
			SysTimeLeaveAmountItemForm itemForm = (SysTimeLeaveAmountItemForm) itemFormList.get(k);
			SysTimeLeaveRule leaveRule = getLeaveRule(leaveRuleList,itemForm.getFdLeaveType());
			if (leaveRule != null) {
				itemForm.setFdStatType(leaveRule.getFdStatType() + "");
				itemForm.setFdLeaveName(leaveRule.getFdName());
				itemForm.setFdOrder(leaveRule.getFdOrder());
				itemForm.setRuleCreateTime(leaveRule.getDocCreateTime());
			}
		}
		Collections.sort(itemFormList,
				new Comparator<SysTimeLeaveAmountItemForm>() {
					@Override
					public int compare(SysTimeLeaveAmountItemForm o1,
							SysTimeLeaveAmountItemForm o2) {
						Integer order1 = o1.getFdOrder();
						Integer order2 = o2.getFdOrder();
						Date createTime1 = o1.getRuleCreateTime();
						Date createTime2 = o2.getRuleCreateTime();
						if (order1 == null && order2 != null) {
							return 1;
						} else if (order1 != null && order2 == null) {
							return -1;
						} else if (order1 !=null && !order1.equals(order2)) {
							return order1 > order2 ? 1 : -1;
						} else {
							return createTime1 != null && createTime2 != null
									? createTime2.compareTo(createTime1) : 0;
						}
					}
				});
		amountForm.setFdAmountItems(itemFormList);
		request.setAttribute(getFormName(rtnForm, request), amountForm);
		// 总天数计算，view页面展示
		SysTimeLeaveAmount amount = (SysTimeLeaveAmount) model;
		List itemList = amount.getFdAmountItems();
		Float total = 0f, restTotal = 0f, usedTotal = 0f, lastTotal = 0f,
				lastRestTotal = 0f, lastUsedTotal = 0f, restAndRest = 0f;
		Float convertTime = SysTimeUtil.getConvertTime();
		boolean isLeaveTypeHour = false;
		// 是否存在假期类型为小时单位的假期
		if (itemList != null && !itemList.isEmpty()) {
			for (int i = 0; i < itemList.size(); i++) {
				SysTimeLeaveAmountItem item = (SysTimeLeaveAmountItem) itemList
						.get(i);
				SysTimeLeaveRule leaveRule = getLeaveRule(leaveRuleList,item.getFdLeaveType());
				Float _total = item.getFdTotalDay() == null ? 0
						: item.getFdTotalDay();
				Float _restTotal = item.getFdRestDay() != null
						&& Boolean.TRUE.equals(item.getFdIsAvail())
								? item.getFdRestDay()
								: 0;
				Float _usedTotal = item.getFdUsedDay() == null ? 0
						: item.getFdUsedDay();
				Float _lastTotal = item.getFdLastTotalDay() == null ? 0
						: item.getFdLastTotalDay();
				Float _lastRestTotal = item.getFdLastRestDay() != null
						&& Boolean.TRUE.equals(item.getFdIsLastAvail())
								? item.getFdLastRestDay()
								: 0;
				Float _lastUsedTotal = item.getFdLastUsedDay() == null ? 0
						: item.getFdLastUsedDay();
				if (leaveRule != null && Integer.valueOf(3)
						.equals(leaveRule.getFdStatType())) {
					isLeaveTypeHour = true;
				}
				total += _total;
				restTotal += _restTotal;
				usedTotal += _usedTotal;
				lastTotal += _lastTotal;
				lastRestTotal += _lastRestTotal;
				lastUsedTotal += _lastUsedTotal;
			}
			restAndRest = restTotal + lastRestTotal;
		}

		request.setAttribute("total", NumberUtil.roundDecimal(total,3));
		request.setAttribute("usedTotal", NumberUtil.roundDecimal(usedTotal,3));
		request.setAttribute("restTotal", NumberUtil.roundDecimal(restTotal,3));
		request.setAttribute("lastTotal", NumberUtil.roundDecimal(lastTotal,3));
		request.setAttribute("lastUsedTotal", NumberUtil.roundDecimal(lastUsedTotal,3));
		request.setAttribute("lastRestTotal",NumberUtil.roundDecimal(lastRestTotal,3));
		request.setAttribute("restAndRest", NumberUtil.roundDecimal(restAndRest,3));

//		request.setAttribute("total", SysTimeUtil.formatDecimal(total,1));
//		request.setAttribute("usedTotal", isLeaveTypeHour
//				? SysTimeUtil.formatLeaveTimeStr(usedTotal, 0f) : SysTimeUtil.formatDecimal(usedTotal,1)
//		);
//		request.setAttribute("restTotal", isLeaveTypeHour
//				? SysTimeUtil.formatLeaveTimeStr(restTotal, 0f) : SysTimeUtil.formatDecimal(restTotal,1)
//		);
//		request.setAttribute("lastTotal", isLeaveTypeHour
//				? SysTimeUtil.formatLeaveTimeStr(lastTotal, 0f) : SysTimeUtil.formatDecimal(lastTotal,1)
//		);
//		request.setAttribute("lastUsedTotal", isLeaveTypeHour ? SysTimeUtil
//				.formatLeaveTimeStr(lastUsedTotal, 0f) : SysTimeUtil.formatDecimal(lastUsedTotal,1)
//		);
//		request.setAttribute("lastRestTotal", isLeaveTypeHour ? SysTimeUtil
//				.formatLeaveTimeStr(lastRestTotal, 0f) : SysTimeUtil.formatDecimal(lastRestTotal,1)
//		);
//		request.setAttribute("restAndRest", isLeaveTypeHour ? SysTimeUtil
//				.formatLeaveTimeStr(restAndRest, 0f) : SysTimeUtil.formatDecimal(restAndRest,1)
//		);
	}

	/**
	 * 类型跟规则匹配
	 * @param leaveRuleList
	 * @param leaveType
	 * @return
	 */
	private SysTimeLeaveRule getLeaveRule(List<SysTimeLeaveRule> leaveRuleList, String leaveType) {
		if (StringUtil.isNull(leaveType)) {
			return null;
		}
		for (SysTimeLeaveRule leaveRule : leaveRuleList) {
			if (leaveType.equals(leaveRule.getFdSerialNo()) ) {
				return leaveRule;
			}

			/*
			假期类型的编号是不允许重复，如果存在
			初始化编号重复，就立马对假期类型进行修改以后，在来操作假期额度。这里用名称判断，会引发其他问题。
			2021-11-25 修改
			// 本来这里用编号来取假期类型是没问题的，但是由于导入初始数据时会引入重复的编号，所以这里用名称和编号来取
			if (leaveType.equals(leaveRule.getFdSerialNo())
					&& leaveName.equals(leaveRule.getFdName())) {
				return leaveRule;
			} */

		}
		return null;
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.createNewForm(mapping, form, request, response);
		SysTimeLeaveAmountForm sysTimeLeaveAmountForm = (SysTimeLeaveAmountForm) form;
		String year = request.getParameter("year");
		String personId = request.getParameter("personId");
		if (StringUtil.isNotNull(year) && StringUtil.isNotNull(personId)) {
			SysOrgElement person = getSysOrgCoreService()
					.findByPrimaryKey(personId);
			if (person != null) {
				sysTimeLeaveAmountForm.setFdYear(year);
				sysTimeLeaveAmountForm.setFdPersonId(personId);
				sysTimeLeaveAmountForm.setFdPersonName(person.getFdName());
				request.setAttribute("_years", getAmountYears(personId));
			}
		} else {
			Calendar cal = Calendar.getInstance();
			sysTimeLeaveAmountForm.setFdYear(cal.get(Calendar.YEAR) + "");
		}
		AutoArrayList itemList = genAmountItems(sysTimeLeaveAmountForm);
		
		sysTimeLeaveAmountForm.setFdAmountItems(itemList);
		// 设置场所
		if (form instanceof ISysAuthAreaForm) {
			ISysAuthAreaForm sysAuthAreaForm = (ISysAuthAreaForm) form;
			KMSSUser user = UserUtil.getKMSSUser();
			sysAuthAreaForm.setAuthAreaId(user.getAuthAreaId());
			sysAuthAreaForm.setAuthAreaName(user.getAuthAreaName());
		}
		return sysTimeLeaveAmountForm;
	}

	private AutoArrayList genAmountItems(SysTimeLeaveAmountForm amountForm)
			throws Exception {
		AutoArrayList itemList = new AutoArrayList(
				SysTimeLeaveAmountItemForm.class);
		List<SysTimeLeaveRule> leaveRuleList = getServiceImp(null)
				.getAllLeaveRule();
		for (SysTimeLeaveRule leaveRule : leaveRuleList) {
			SysTimeLeaveAmountItemForm itemForm = genAmountItem(amountForm,
					leaveRule);
			if (itemForm != null) {
				itemList.add(itemForm);
			}
		}
		return itemList;
	}

	private SysTimeLeaveAmountItemForm genAmountItem(
			SysTimeLeaveAmountForm amountForm, SysTimeLeaveRule leaveRule) {
		Integer year = Integer.parseInt(amountForm.getFdYear());
		Date today = SysTimeUtil.getDate(new Date(), 0);
		String personId = amountForm.getFdPersonId();
		SysTimeLeaveAmountItemForm itemForm = new SysTimeLeaveAmountItemForm();
		itemForm.setFdId(IDGenerator.generateID());
		itemForm.setFdAmountId(amountForm.getFdId());
		itemForm.setFdLeaveName(leaveRule.getFdName());
		itemForm.setFdLeaveType(leaveRule.getFdSerialNo());
		itemForm.setFdOrder(leaveRule.getFdOrder());
		itemForm.setRuleCreateTime(leaveRule.getDocCreateTime());
		if (Boolean.TRUE.equals(leaveRule.getFdIsAmount())) {
			Integer fdAmountType = leaveRule.getFdAmountType();// 发放方式
			Integer fdAmountCalType = leaveRule.getFdAmountCalType();// 结算方式
			if (fdAmountType == null) {
				throw new UnexpectedRequestException();
			}
			if (fdAmountType == 2||fdAmountType == 3||fdAmountType == 4) {// 自动发放
				//自动发放假期类型，在新增假期额度时，不生成自动发放假期类型额度数据
				return null;
			} else if (fdAmountType == 1) {
				// 手动发放，（到期清零）
				itemForm.setFdIsAuto("false");
				itemForm.setFdIsAccumulate("false");
				itemForm.setFdIsAvail("true");
				itemForm.setFdUsedDay("0");
			}
			// 上一年的数据
			if (StringUtil.isNotNull(personId)) {
				SysTimeLeaveAmountItem lastYearItem = getServiceImp(null)
						.getLeaveAmountItem(year - 1, personId,
								leaveRule.getFdSerialNo());
				if (lastYearItem != null) {// 有上一年的数据
					if (Boolean.TRUE.equals(lastYearItem.getFdIsAccumulate()) && fdAmountCalType == 2) {
						// 是否累加
						Float restDay = lastYearItem.getFdRestDay() == null
								? 0 : lastYearItem.getFdRestDay();
						Float lastRestDay = lastYearItem
								.getFdLastRestDay() == null ? 0
										: lastYearItem.getFdLastRestDay();
						itemForm.setFdLastTotalDay(
								(restDay + lastRestDay) + "");
						itemForm.setFdLastRestDay(
								(restDay + lastRestDay) + "");
						itemForm.setFdIsLastAvail("true");
					} else if (lastYearItem.getFdValidDate() != null) {
						Float restDay = lastYearItem.getFdRestDay() == null
								? 0 : lastYearItem.getFdRestDay();
						if (lastYearItem.getFdValidDate()
								.compareTo(today) >= 0) {// 未过期
							itemForm.setFdLastTotalDay(restDay + "");
							itemForm.setFdLastRestDay(restDay + "");
							itemForm.setFdIsLastAvail("true");
						} else {// 已过期
							itemForm.setFdLastTotalDay(restDay + "");
							itemForm.setFdLastRestDay(restDay + "");
							itemForm.setFdIsLastAvail("false");
						}
						itemForm.setFdLastValidDate(
								DateUtil.convertDateToString(
										lastYearItem.getFdValidDate(),
										DateUtil.TYPE_DATE, null));
						itemForm.setFdLastUsedDay("0");
					}
				}
			}
			return itemForm;
		} else {
			return null;
		}
	}

	/**
	 * 是否今天或今天后
	 * 
	 * @param date
	 * @return
	 */
	private Boolean getIsAfterToday(Date date) {
		Date today = SysTimeUtil.getDate(new Date(), 0);
		return SysTimeUtil.getDate(date, 0).compareTo(today) >= 0;
	}

	/**
	 * 获取有效日期
	 * 
	 * @param year
	 *            年份
	 * @param delta
	 *            延长天数
	 * @return
	 */
	private Date getValidDate(Integer year, Integer delta) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.YEAR, year);
		cal.roll(Calendar.DAY_OF_YEAR, -1);
		cal.add(Calendar.DATE, delta);
		return cal.getTime();
	}
	
	/**
	 * 获取有效日期
	 * @param year
	 * @param date
	 * @param delta
	 * @return
	 */
	private Date getValidDate(Integer year, Date date,Integer delta){
		if (date == null && year!=null && year > 0) {
			return getValidDate(year,delta);
		}
		Calendar cal = Calendar.getInstance();
		if (date!=null) {
			cal.setTime(date);//设置起时间
			cal.add(Calendar.DATE, delta);
			return cal.getTime();
		}
		return null;
	}

	/**
	 * 获取某个人假期数据已有的年份
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getAmountYears(String personId) throws Exception {
		String yearStr = "";
		if (StringUtil.isNotNull(personId)) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("sysTimeLeaveAmount.fdYear");
			hqlInfo.setWhereBlock(
					"sysTimeLeaveAmount.fdPerson.fdId = :personId");
			hqlInfo.setParameter("personId", personId);
			List<Integer> yearList = getServiceImp(null).findList(hqlInfo);
			if (!yearList.isEmpty()) {
				for (Integer year : yearList) {
					yearStr += year + ";";
				}
			}
		}
		return yearStr;
	}

	@Override
	public String getTempletName() {
		return ResourceUtil
				.getString("sys-time:sysTimeLeaveAmount.import.template");
	}

	public ActionForward getLeaveRules(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-getLeaveRules", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			JSONArray jsonArr = new JSONArray();
			List<SysTimeLeaveRule> leaveRuleList = getServiceImp(request)
					.getAllLeaveRule();
			for (SysTimeLeaveRule leaveRule : leaveRuleList) {
				JSONObject json = new JSONObject();
				json.put("fdId", leaveRule.getFdId());
				json.put("fdName", leaveRule.getFdName());
				jsonArr.add(json);
			}
			request.setAttribute("lui-source", jsonArr);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-getLeaveRules", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

	public ActionForward getYearCriterion(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-getYearCriterion", true,
				getClass());
		KmssMessages messages = new KmssMessages();
		try {
			JSONArray jsonArr = new JSONArray();
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("distinct sysTimeLeaveAmount.fdYear");
			hqlInfo.setWhereBlock("sysTimeLeaveAmount.fdYear is not null");
			hqlInfo.setOrderBy("sysTimeLeaveAmount.fdYear asc");
			List<Integer> yearList = getServiceImp(request).findValue(hqlInfo);
			for (Integer year : yearList) {
				JSONObject json = new JSONObject();
				json.put("text", year + "");
				json.put("value", year + "");
				jsonArr.add(json);
			}
			request.setAttribute("lui-source", jsonArr);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-getYearCriterion", false,
				getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

	public ActionForward showStat(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-showStat", true, getClass());
		JSONArray jsonArray = new JSONArray();
		JSONObject statInfo = new JSONObject();
		statInfo.put("text", ResourceUtil.getString("sys-time:sysTimeLeaveAmount.myAmount"));
		statInfo.put("count", getRestDayCount(request));
		jsonArray.add(statInfo);
		request.setAttribute("lui-source", jsonArray);
		TimeCounter.logCurrentTime("Action-showStat", false, getClass());
		return mapping.findForward("lui-source");
	}

	/**
	 * 获取剩余假期天数
	 * @description:
	 * @param
	 * @return: int
	 * @author: wangjf
	 * @time: 2021/11/23 4:47 下午
	 */
	private int getRestDayCount(HttpServletRequest request) throws Exception {

		int count = 0;
		HQLInfo hqlInfo = new HQLInfo();
		String hql = " fdPerson.fdId =:userId";
		hqlInfo.setWhereBlock(hql);
		hqlInfo.setParameter("userId",UserUtil.getUser().getFdId());
		List<SysTimeLeaveAmount> list = getServiceImp(request).findList(hqlInfo);
		if(CollectionUtils.isEmpty(list)){
			return count;
		}
		for (SysTimeLeaveAmount sysTimeLeaveAmount : list) {
			if(CollectionUtils.isEmpty(sysTimeLeaveAmount.getFdAmountItems())){
				continue;
			}
			for (SysTimeLeaveAmountItem fdAmountItem : sysTimeLeaveAmount.getFdAmountItems()) {
				if(BooleanUtils.isTrue(fdAmountItem.getFdIsAvail()) && fdAmountItem.getFdRestDay() != null){
					count = count + fdAmountItem.getFdRestDay().intValue();
				}
			}
		}
		return count;
	}

	/**
	 * 批量导出
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward batchExport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String exportName = ResourceUtil.getString(
					"sysTimeLeaveDetail.remainder.exportFileName", "sys-time");
			HSSFWorkbook workbook = getServiceImp(request).buildWorkBook(new RequestContext(request));
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition",
					"attachment;fileName=\"" + new String(exportName.getBytes("GBK"),
							"ISO-8859-1") + "\"");
			OutputStream out = (OutputStream) response.getOutputStream();
			workbook.write(out);
			return null;
		} catch (Exception e) {
			messages.addError((Throwable)e);
            if (messages.hasError()) {
                KmssReturnPage.getInstance(request).addMessages(messages).save(request);
                return this.getActionForward("failure", mapping, form, request, response);
            }
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(0).save(request);
            return this.getActionForward("success", mapping, form, request, response);
		}
	}
	
	public ActionForward userHoliday(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-userHoliday", true,
				getClass());
		KmssMessages messages = new KmssMessages();
		try {
			JSONArray jsonArr = new JSONArray();
			int year= Calendar.getInstance().get(Calendar.YEAR);
			SysTimeLeaveAmount sysTimeLeaveAmount = getServiceImp(request).getLeaveAmount(year, UserUtil.getUser().getFdId());
			if(sysTimeLeaveAmount!=null) {
				List<SysTimeLeaveAmountItem> itemList=sysTimeLeaveAmount.getFdAmountItems();
				if(itemList!=null && !itemList.isEmpty()) {
					for (SysTimeLeaveAmountItem item : itemList) {
						JSONObject json = new JSONObject();
						json.put("fdYear", sysTimeLeaveAmount.getFdYear()+"");// 年份
						json.put("fdExpirationDate",
								DateUtil.convertDateToString(item.getFdValidDate(),
										DateUtil.TYPE_DATE, request.getLocale()));// 失效日期
						json.put("fdLeaveName", item.getFdLeaveName());//假期名称
						json.put("fdRestDay",item.getFdRestDay());//剩余天数
						jsonArr.add(json);
					}
				}
			}
			request.setAttribute("lui-source", jsonArr);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-userHoliday", false,
				getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("lui-source", mapping, form, request,
					response);
		}
	}

	/**
	 * 重写查看页面，因为需要根据是否是最新年度的数据进行页面的不同的显示
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return: com.landray.kmss.web.action.ActionForward
	 * @author: wangjf
	 * @time: 2022/4/13 4:06 下午
	 */
	@Override
	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		//是否可以编辑判断传递给jsp前端
		TimeCounter.logCurrentTime("Action-view", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}
		SysTimeLeaveAmountForm sysTimeLeaveAmountForm = (SysTimeLeaveAmountForm)form;

		//是否是最后一年
		boolean lastYear = false;
		if(StringUtil.isNotNull(sysTimeLeaveAmountForm.getFdYear()) && StringUtil.isNotNull(sysTimeLeaveAmountForm.getFdPersonId())) {
			lastYear = getServiceImp(request).isLastYear(Integer.parseInt(sysTimeLeaveAmountForm.getFdYear()), sysTimeLeaveAmountForm.getFdPersonId());
		}
		request.setAttribute("lastYear", lastYear);

		TimeCounter.logCurrentTime("Action-view", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("view", mapping, form, request, response);
		}
	}

	/**
	 * 在list列表中批量删除选定的多条记录。<br>
	 * 表单中，复选框的域名必须为“List_Selected”，其值为记录id。<br>
	 * 该操作一般以HTTP的POST方式触发。
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回success页面，否则返回failure页面
	 * @throws Exception
	 */
	@Override
	public ActionForward deleteall(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-deleteall", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String[] ids = request.getParameterValues("List_Selected");
			if (!ArrayUtils.isEmpty(ids)) {
				getServiceImp(request).deleteIds(ids);
			}

		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-deleteall", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 员工离职时获取年假数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getRemainingAmount(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject rtnInfo = new JSONObject();
		//申请人ID
		String fdPersonId = request.getParameter("fdPersonId");
		String fdLeaveTime = request.getParameter("fdLeaveTime");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = dateFormat.parse(fdLeaveTime+" 00:00:00");
		if(StringUtil.isNull(fdPersonId)){
			throw new Exception("没有获取到人员id");
		}
		if(StringUtil.isNull(fdLeaveTime)){
			throw new Exception("没有获取到离职日期");
		}
		HrStaffPersonInfo hrStaffPersonInfo = (HrStaffPersonInfo) getHrStaffPersonInfoService().findByPrimaryKey(fdPersonId);
		SysOrgElement element = (SysOrgElement) getSysOrgElementService().findByPrimaryKey(fdPersonId);
		if(hrStaffPersonInfo != null && element != null){
			Double remainingAmount = getServiceImp(request).getRemainingAmount(hrStaffPersonInfo,fdLeaveTime);
			Double fdRestDay = getSysTimeCountService().getUserLeaveAmount(element,"1");
			Double fdTotalDays = remainingAmount;
//			Double fdTotalDays = FsscNumberUtil.getAddition(remainingAmount,fdRestDay);
			SysTimeLeaveAmountItem sysTimeLeaveAmountItem =(SysTimeLeaveAmountItem)getSysTimeLeaveAmountItemService().getAmountItem(fdPersonId,Integer.parseInt(fdLeaveTime.split("-")[0]),"1");
			if(date.compareTo(sysTimeLeaveAmountItem.getFdLastValidDate())==-1)
				fdTotalDays=fdTotalDays+sysTimeLeaveAmountItem.getFdLastRestDay()-sysTimeLeaveAmountItem.getFdUsedDay();
			if(date.compareTo(sysTimeLeaveAmountItem.getFdLastValidDate())==1)
				fdTotalDays=fdTotalDays-sysTimeLeaveAmountItem.getFdUsedDay();
			rtnInfo.put("fdRemainingAmount",remainingAmount);
			rtnInfo.put("fdRestDay",fdRestDay);
			rtnInfo.put("fdTotalDays",fdTotalDays);
		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(rtnInfo.toString());
		return null;
	}
}
