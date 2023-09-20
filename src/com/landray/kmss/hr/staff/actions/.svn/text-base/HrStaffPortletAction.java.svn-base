package com.landray.kmss.hr.staff.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.hr.staff.model.HrStaffEntry;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceContract;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.model.HrStaffRatifyLog;
import com.landray.kmss.hr.staff.service.IHrStaffEntryService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceContractService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.hr.staff.service.IHrStaffRatifyLogService;
import com.landray.kmss.hr.staff.util.HrStaffPortletUtil;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class HrStaffPortletAction extends ExtendAction {
    @Override
    protected IBaseService getServiceImp(HttpServletRequest request) {
        // TODO Auto-generated method stub
        return null;
    }

    private IHrStaffEntryService hrStaffEntryService;

    protected IHrStaffEntryService
    getHrStaffEntryServiceImp() {
        if (hrStaffEntryService == null) {
            hrStaffEntryService = (IHrStaffEntryService) getBean(
                    "hrStaffEntryService");
        }
        return hrStaffEntryService;
    }

    private IHrStaffPersonInfoService hrStaffPersonInfoService;

    protected IHrStaffPersonInfoService
    getHrStaffPersonInfoServiceImp(HttpServletRequest request) {
        if (hrStaffPersonInfoService == null) {
            hrStaffPersonInfoService = (IHrStaffPersonInfoService) getBean(
                    "hrStaffPersonInfoService");
        }
        return hrStaffPersonInfoService;
    }

    private IHrStaffPersonExperienceContractService hrStaffPersonExperienceContractService;

    protected IBaseService getHrStaffPersonExperienceContractServiceImp() {
        if (hrStaffPersonExperienceContractService == null) {
            hrStaffPersonExperienceContractService = (IHrStaffPersonExperienceContractService) getBean(
                    "hrStaffPersonExperienceContractService");
        }
        return hrStaffPersonExperienceContractService;
    }

    private IHrStaffRatifyLogService hrStaffRatifyLogService;

    protected IHrStaffRatifyLogService
    getHrStaffRatifyLogServiceImp() {
        if (hrStaffRatifyLogService == null) {
            hrStaffRatifyLogService = (IHrStaffRatifyLogService) getBean(
                    "hrStaffRatifyLogService");
        }
        return hrStaffRatifyLogService;
    }

    // 面试注释
    // private IHrRecruitViewPlanService hrRecruitViewPlanService;
    //
    // public IHrRecruitViewPlanService
    // getHrRecruitViewPlanServiceImp() {
    // if (hrRecruitViewPlanService == null) {
    // hrRecruitViewPlanService = (IHrRecruitViewPlanService) getBean(
    // "hrRecruitViewPlanService");
    // }
    // return hrRecruitViewPlanService;
    // }


    /**
     * #0 调岗弹出列表
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward listTransfer(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-list", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            String s_pageno = request.getParameter("pageno");
            String s_rowsize = request.getParameter("rowsize");
            String orderby = request.getParameter("orderby");
            String ordertype = request.getParameter("ordertype");
            boolean isReserve = false;
            if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
                isReserve = true;
            }
            int pageno = 0;
            int rowsize = SysConfigParameters.getRowSize();
            if (s_pageno != null && s_pageno.length() > 0
                    && Integer.parseInt(s_pageno) > 0) {
                pageno = Integer.parseInt(s_pageno);
            }
            if (s_rowsize != null && s_rowsize.length() > 0
                    && Integer.parseInt(s_rowsize) > 0) {
                rowsize = Integer.parseInt(s_rowsize);
            }
            CriteriaValue cv = new CriteriaValue(request);
            HQLInfo hqlInfo = new HQLInfo();
            String whereBlock = "hrStaffRatifyLog.fdRatifyType ='transfer'";
            hqlInfo.setWhereBlock(whereBlock);
            hqlInfo.setPageNo(pageno);
            hqlInfo.setRowSize(rowsize);
            CriteriaUtil.buildHql(cv, hqlInfo, HrStaffRatifyLog.class);
            Page page = getHrStaffRatifyLogServiceImp()
                    .findPage(hqlInfo);

            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }

        TimeCounter.logCurrentTime("Action-list", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages)
                    .addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("transferData", mapping, form, request,
                    response);
        }
    }

    // 招聘、人事流程、员工档案、培训、考勤、绩效、薪酬
    private static final String[] NAV_MODULE = {"recruit", "ratify", "staff",
            "exam", "attend", "okr","salary"};
    private static final String[] CLASS_MODULE = {
            "com.landray.kmss.hr.recruit.actions.HrRecruitApplyAction",
            "com.landray.kmss.hr.ratify.actions.HrRatifyMainAction",
            "com.landray.kmss.hr.staff.actions.HrStaffPersonInfoAction",
            "com.landray.kmss.kms.exam.actions.KmsExamMainAction",
            "com.landray.kmss.sys.attend.actions.SysAttendMainAction",
            "com.landray.kmss.hr.okr.actions.HrOkrMainAction",
            "com.landray.kmss.hr.salary.actions.HrSalaryMainAction"};

    /**
     * #1 导航 portlet
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward moduleNavs(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        try {

            JSONObject json = new JSONObject();
            for (int i = 0; i < NAV_MODULE.length; i++) {
                try {
                    Class c = com.landray.kmss.util.ClassUtils.forName(CLASS_MODULE[i]);
                    json.put(NAV_MODULE[i], true);
                } catch (java.lang.ClassNotFoundException e) {
                    json.put(NAV_MODULE[i], false);
                }
            }
            request.setAttribute("nav", json);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapping.findForward("moduleNav");
    }

    /**
     * #2 日历 portlet
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward manageCalend(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        try {
            String type = request.getParameter("type");
            Date[] dateArray = HrStaffPortletUtil.getStartAndEndDayOfMonth();
            JSONObject json = manageCalend(dateArray, request);
            request.setAttribute("data", json);
            request.setAttribute("type", type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapping.findForward("manageCalend");
    }

    /**
     * #2.2 日历 异步访问 portlet
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward calendAjax(ActionMapping mapping,
                                    ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        try {
            String dateTxt = request.getParameter("date");
            String idx = request.getParameter("idx");
            Date date = DateUtil.convertStringToDate(dateTxt);
            Calendar cale = null;
            cale = Calendar.getInstance();
            if ("-1".equals(idx)) {
                // 前一月
                cale.setTime(date);
                cale.add(Calendar.MONTH, 0);
            } else if ("1".equals(idx)) {
                // 后一月
                cale.setTime(date);
                cale.add(Calendar.MONTH, 2);
            } else if ("0".equals(idx)) {
                // 刷新
                cale.setTime(date);
                cale.add(Calendar.MONTH, 1);
            }
            Date[] dateArray = HrStaffPortletUtil.getStartAndEndDayOfMonth(cale.getTime());
            JSONObject json = manageCalend(dateArray, request);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * #3 <!-- 月度统计概览 --> portlet
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward monthOverview(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        try {
            Date[] dateArray = HrStaffPortletUtil.getStartAndEndDayOfMonth();
            HQLInfo hqlInfo = new HQLInfo();
//            HrStaffPortletUtil.buildPersonListSql(hqlInfo, dateArray);
            List<HrStaffPersonInfo> persons = getHrStaffPersonInfoServiceImp(
                    request).findList(hqlInfo);
            Map<String, Integer> monthMap = getMonthMap(persons, dateArray);
            JSONObject json = new JSONObject();
            json.put("monthMap", monthMap);
            json.put("dateArray", dateArray);
            request.setAttribute("data", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapping.findForward("monthOverview");
    }

    /**
     * #4 <!-- 人事档案统计 图表--> portlet
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    private final static String ECHARS_GENTER = "gender";
    private final static String ECHARS_AGE = "age";
    private final static String ECHARS_EDU = "education";
    private final static String ECHARS_ENTRY = "entryAndleave";

    public ActionForward echarts(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String type = request.getParameter("type");
        String path = "";
        try {

            Date[] dateArray = HrStaffPortletUtil.getStartAndEndDayOfMonth();
            JSONObject json = new JSONObject();
            switch (type) {
                case ECHARS_GENTER:
                    path = createEchartsGenderData(json);
                    break;
                case ECHARS_AGE:
                    path = createEchartsAgeData(json, dateArray);
                    break;
                case ECHARS_EDU:
                    path = createEchartsEducationData(json, dateArray);
                    break;
                case ECHARS_ENTRY:
                    path = createEchartsEntryData(json, dateArray);
                    break;
                default:
                    path = createEchartsGenderData(json);
                    break;
            }
            json.put("dateArray", dateArray);
            request.setAttribute("data", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapping.findForward(path);
    }

    /**
     * ==================================================================================================================
     */
    /**
     * 功能-获取日历数据
     *
     * @param dateArray
     * @param request
     * @return
     * @throws Exception
     */
    private JSONObject manageCalend(Date[] dateArray,
                                    HttpServletRequest request) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        HrStaffPortletUtil.buildPersonListSql(hqlInfo, dateArray);
        List<HrStaffPersonInfo> persons = getHrStaffPersonInfoServiceImp(request).findList(hqlInfo);

        JSONObject json = new JSONObject();
        // 日历标题
        json.put("calendFormat", HrStaffPortletUtil.getCalendYMWD(dateArray[0]));
        // 格式化数据
        String type = request.getParameter("type");
        Map<String, Map> calendMap = getCalendMap(persons, dateArray, type);
        //不知道干嘛的~
        Map test_map = calendMap.get("test");
        calendMap.remove("test");

        // 日历数据
        json.put("calendMap", calendMap);

        //时间索引
        json.put("dateIdx", HrStaffPortletUtil.getIdxOfCalendMap(dateArray, calendMap));
        return json;
    }


    /**
     * 功能- 月度统计map
     *
     * @param persons
     * @param period
     * @return
     */
    private Map<String, Integer> getMonthMap(List<HrStaffPersonInfo> persons,
                                             Date[] period) {

        Map<String, Integer> monthMap = new HashMap<String, Integer>();
        int onTheJob = 0, entry = 0, positive = 0, leave = 0, birthday = 0, annual = 0;
        for (HrStaffPersonInfo pInfo : persons) {
        	//当月在职，与当月离职数据有可能重复，在职的数量通过sql直接查
            boolean isOnTheJob = HrStaffPortletUtil.judge(pInfo, period, HrStaffPortletUtil.TYPE_ONTHEJOB);
            if (HrStaffPortletUtil.judge(pInfo, period, HrStaffPortletUtil.TYPE_LEAVE)) {
            	leave++;
            	isOnTheJob = false;
            }
            if (isOnTheJob) {
                onTheJob++;
            }
            if (HrStaffPortletUtil.judge(pInfo, period, HrStaffPortletUtil.TYPE_ENTRY)) {
                entry++;
            }
            if (HrStaffPortletUtil.judge(pInfo, period, HrStaffPortletUtil.TYPE_POSITIVE)) {
                positive++;
            }
            if (isOnTheJob && HrStaffPortletUtil.judge(pInfo, period, HrStaffPortletUtil.TYPE_BIRTHDAY)) {
                birthday++;
            }
            if (isOnTheJob && HrStaffPortletUtil.judge(pInfo, period, HrStaffPortletUtil.TYPE_ANNUAL)) {
                annual++;
            }
        }
        //调岗和合同需要另外处理
        int contract = 0;
        try {
            HQLInfo contractHql = HrStaffPortletUtil.createTimeHql("hrStaffPersonExperienceContract.fdEndDate", period);
            contractHql.setSelectBlock("hrStaffPersonExperienceContract.fdId");
            List<HrStaffPersonExperienceContract> contractList = getHrStaffPersonExperienceContractServiceImp().findList(contractHql);
            contract = contractList.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int transfer = 0;
        try {
            HQLInfo transferHql = HrStaffPortletUtil.createTimeHql("hrStaffRatifyLog.fdRatifyDate", period);
            transferHql.setSelectBlock("hrStaffRatifyLog.fdId");
            String whereblock = transferHql.getWhereBlock()
                    + " and hrStaffRatifyLog.fdRatifyType ='transfer'";
            transferHql.setWhereBlock(whereblock);
            List<HrStaffRatifyLog> transferList = getHrStaffRatifyLogServiceImp().findList(transferHql);
            transfer = transferList.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
		/*
		 * try { HQLInfo onTheJobHql=HrStaffPortletUtil.createOnJobHql();
		 * onTheJobHql.setSelectBlock("hrStaffPersonInfo.fdId"); List onTheJobList =
		 * getHrStaffPersonInfoServiceImp(null).findList(onTheJobHql); onTheJob =
		 * onTheJobList.size(); } catch (Exception e) { e.printStackTrace(); }
		 */


        monthMap.put(HrStaffPortletUtil.TYPE_ONTHEJOB, onTheJob);
        monthMap.put(HrStaffPortletUtil.TYPE_ENTRY, entry);
        monthMap.put(HrStaffPortletUtil.TYPE_POSITIVE, positive);
        monthMap.put(HrStaffPortletUtil.TYPE_LEAVE, leave);
        monthMap.put(HrStaffPortletUtil.TYPE_BIRTHDAY, birthday);
        monthMap.put(HrStaffPortletUtil.TYPE_ANNUAL, annual);
        monthMap.put(HrStaffPortletUtil.TYPE_CONTRACT, contract);
        monthMap.put(HrStaffPortletUtil.TYPE_TRANSFER, transfer);
        // 面试注释
        // JSONObject viewplan = getViewPlan(period);
        // monthMap.put("viewplan", viewplan.getInt("count"));

        return monthMap;
    }

    /**
     * 功能 获取日历数据
     *
     * @param persons
     * @param period
     * @return
     */


    private Map<String, Map> getCalendMap(List<HrStaffPersonInfo> persons,
                                          Date[] period, String type) {
        Map<String, Object> test_Map = new HashMap<>();
        boolean f_positive = false, f_entry = false, f_leave = false,
                f_birthday = false, f_contract = false, f_transfer = false,
                f_annual = false;

        Map<String, Map> calendMap = new HashMap<>(); 
        if (StringUtil.isNotNull(type)) {
            String[] types = type.trim().split(";");
            if (types.length > 0) {
                List<String> typeList = Arrays.asList(types);
                f_positive = typeList.contains(HrStaffPortletUtil.TYPE_POSITIVE);
                f_entry = typeList.contains(HrStaffPortletUtil.TYPE_ENTRY);
                f_leave = typeList.contains(HrStaffPortletUtil.TYPE_LEAVE);
                f_birthday = typeList.contains(HrStaffPortletUtil.TYPE_BIRTHDAY);
                f_contract = typeList.contains(HrStaffPortletUtil.TYPE_CONTRACT);
                f_transfer = typeList.contains(HrStaffPortletUtil.TYPE_TRANSFER);
                f_annual = typeList.contains(HrStaffPortletUtil.TYPE_ANNUAL);
            }
        }

        for (HrStaffPersonInfo pInfo : persons) {
            boolean isOnTheJob = HrStaffPortletUtil.judge(pInfo, period, HrStaffPortletUtil.TYPE_ONTHEJOB);
            if (f_positive && HrStaffPortletUtil.judge(pInfo, period, HrStaffPortletUtil.TYPE_POSITIVE)) {
                String key = DateUtil.convertDateToString(
                        pInfo.getFdPositiveTime(), DateUtil.PATTERN_DATE);
                HrStaffPortletUtil.formatCalendMap(key, HrStaffPortletUtil.TYPE_POSITIVE, calendMap);
            }
            if (f_leave && HrStaffPortletUtil.judge(pInfo, period, HrStaffPortletUtil.TYPE_LEAVE)) {
                String key = DateUtil.convertDateToString(
                        pInfo.getFdLeaveTime(), DateUtil.PATTERN_DATE);
                test_Map.put("LEA" + key, pInfo.getFdId());
                HrStaffPortletUtil.formatCalendMap(key, HrStaffPortletUtil.TYPE_LEAVE, calendMap);
            }
            if (f_birthday && isOnTheJob && HrStaffPortletUtil.judge(pInfo, period, HrStaffPortletUtil.TYPE_BIRTHDAY)) {
                Date thisYearBirth = new Date();
                Calendar cale = Calendar.getInstance();
                cale.setTime(thisYearBirth);
                String yy = String.valueOf(cale.get(Calendar.YEAR));

                cale.setTime(pInfo.getFdDateOfBirth());
                String mm = String.valueOf(cale.get(Calendar.MONTH) + 1);
                if (mm.length() != 2) {
                    mm = "0" + mm;
                }
                String dd = String.valueOf(cale.get(Calendar.DATE));
                if (dd.length() != 2) {
                    dd = "0" + dd;
                }
                String key = yy + "-" + mm + "-" + dd;
                HrStaffPortletUtil.formatCalendMap(key, HrStaffPortletUtil.TYPE_BIRTHDAY, calendMap);
            }
            if (f_annual && isOnTheJob && HrStaffPortletUtil.judge(pInfo, period, HrStaffPortletUtil.TYPE_ANNUAL)) {
            	//周年取入职日期的月，日
            	Calendar entryTime = Calendar.getInstance();
            	entryTime.setTime(pInfo.getFdEntryTime());
                //取查询日期的年，组合成key
            	Calendar searchEndData = Calendar.getInstance();
            	searchEndData.setTime(period[1]); 
				String yy = String.valueOf(searchEndData.get(Calendar.YEAR)); 
				String mm = String.valueOf(entryTime.get(Calendar.MONTH) + 1);
				if (mm.length() != 2) {
				    mm = "0" + mm;
				}
				String dd = String.valueOf(entryTime.get(Calendar.DATE));
				if (dd.length() != 2) {
				    dd = "0" + dd;
				}
				String key = String.format("%s-%s-%s", yy,mm,dd);
                HrStaffPortletUtil.formatCalendMap(key, HrStaffPortletUtil.TYPE_ANNUAL, calendMap);
            }

        }
        //合同，待入职，调岗需要另外处理
        if (f_entry) {
            try {
                HQLInfo entryHql = HrStaffPortletUtil.createTimeHql("hrStaffEntry.fdPlanEntryTime", period);
                entryHql.setWhereBlock(StringUtil.linkString(entryHql.getWhereBlock(), " and "," hrStaffEntry.fdStatus =:fdStatus"));
                entryHql.setParameter("fdStatus","1");
                List<HrStaffEntry> list = getHrStaffEntryServiceImp().findList(entryHql);
                addJsonToCalendMap(HrStaffPortletUtil.TYPE_ENTRY, HrStaffPortletUtil.getEntry(list), calendMap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (f_contract) {
            try {
                HQLInfo contractHql = HrStaffPortletUtil.createTimeHql("hrStaffPersonExperienceContract.fdEndDate", period);
                List<HrStaffPersonExperienceContract> contractList = getHrStaffPersonExperienceContractServiceImp().findList(contractHql);
                addJsonToCalendMap(HrStaffPortletUtil.TYPE_CONTRACT, HrStaffPortletUtil.getContract(contractList), calendMap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (f_transfer) {
            try {
                HQLInfo transferHql = HrStaffPortletUtil.createTimeHql("hrStaffRatifyLog.fdRatifyDate", period);
                String whereblock = transferHql.getWhereBlock()
                        + " and hrStaffRatifyLog.fdRatifyType ='transfer'";
                transferHql.setWhereBlock(whereblock);
                List<HrStaffRatifyLog> transferList = getHrStaffRatifyLogServiceImp().findList(transferHql);
                addJsonToCalendMap(HrStaffPortletUtil.TYPE_TRANSFER, HrStaffPortletUtil.getTransfer(transferList), calendMap);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        // 注释面试
        // addJsonToCalendMap("viewplan", getViewPlan(period), calendMap);

        calendMap.put("test", test_Map);
        return calendMap;

    }

    private void addJsonToCalendMap(String mapKey, JSONObject json,
                                    Map<String, Map> calendMap) {
        if (json.get("map") != null) {
            Map<String, Map> dataMap = (Map) json.get("map");
            for (String key : dataMap.keySet()) {
                Map<String, Integer> calCon = calendMap.get(key);
                if (calCon == null) {
                    calCon = new HashMap<>();
                }
                calCon.put(mapKey,
                        (Integer) dataMap.get(key).get(mapKey));
                calendMap.put(key, calCon);

            }
        }
    }


    /**
     * 功能-echrts 性别
     *
     * @param json
     * @param
     * @return
     */
    private String createEchartsGenderData(JSONObject json) throws Exception {
        // 性别
        long male = 0;
        long female = 0;
        long unknown = 0;
        // 获取性别
        List<String> sexList = new ArrayList<String>();
        sexList.add("M"); // 男性
        sexList.add("F"); // 女性
        sexList.add(null); // 未知
        // 获取人数
        HQLInfo hqlInfo = null;
        List<Long> list = null;
        for (String sex : sexList) {
            long count = 0L;
            hqlInfo = getBaseHQLInfoForOverview();
            String whereBlock = hqlInfo.getWhereBlock();
            if (sex == null) {
                // 统计组织机构人员
                hqlInfo.setWhereBlock(whereBlock
                        + " and hrStaffPersonInfo.fdOrgPerson is not null and hrStaffPersonInfo.fdOrgPerson.fdSex is null");
                list = getHrStaffPersonInfoServiceImp(null).findValue(hqlInfo);
                count += list.get(0);
                // 统计人事档案人员
                hqlInfo.setWhereBlock(whereBlock
                        + " and hrStaffPersonInfo.fdOrgPerson is null and hrStaffPersonInfo.fdSex is null");
                list = getHrStaffPersonInfoServiceImp(null).findValue(hqlInfo);
                count += list.get(0);
                unknown = count;
            } else {
                hqlInfo.setParameter("fdSex", sex);
                // 统计组织机构人员
                hqlInfo.setWhereBlock(whereBlock
                        + " and hrStaffPersonInfo.fdOrgPerson is not null and hrStaffPersonInfo.fdOrgPerson.fdSex = :fdSex");
                list = getHrStaffPersonInfoServiceImp(null).findValue(hqlInfo);
                count += list.get(0);
                // 统计人事档案人员
                hqlInfo.setWhereBlock(whereBlock
                        + " and hrStaffPersonInfo.fdOrgPerson is null and hrStaffPersonInfo.fdSex = :fdSex");
                list = getHrStaffPersonInfoServiceImp(null).findValue(hqlInfo);
                count += list.get(0);
                if ("F".equals(sex)) {
                    female = count;
                } else {
                    male = count;
                }
            }
        }
        Double sum = 0.0 + male + female + unknown;
        Map<String, Double> sexMap = new HashMap<>();
        sum = sum == 0 ? 1 : sum;
        sexMap.put("M", male / sum * 100);
        sexMap.put("F", female / sum * 100);
        sexMap.put("unknown", unknown / sum * 100);
        json.put("sex", sexMap);
        return "echartsGender";
    }

    /**
     * 概况统计报表只统计在职状态的员工
     *
     * @return
     */
    private HQLInfo getBaseHQLInfoForOverview() {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setSelectBlock("count(*)");
        // 只统计“试用、正式、试用延期、临时、实习生”状态的员工
        List<String> status = new ArrayList<String>();
        status.add("trial");
        status.add("official");
        status.add("temporary");
        status.add("trialDelay");
        status.add("practice");

        hqlInfo.setWhereBlock("hrStaffPersonInfo.fdStatus in (:fdStatus)");
        hqlInfo.setParameter("fdStatus", status);
        return hqlInfo;
    }

    /**
     * 功能-echrts 年龄
     *
     * @param json
     * @param
     * @return
     */
    private String createEchartsAgeData(JSONObject json, Date[] dateArray)
            throws Exception {
        // 年龄分布
        int age30 = 0;
        int age40 = 0;
        int age50 = 0;
        int age60 = 0;
        int ageA = 0;
        List<HrStaffPersonInfo> persons = (List<HrStaffPersonInfo>) getHrStaffPersonInfoServiceImp(
                null).findList(new HQLInfo());
        for (HrStaffPersonInfo info : persons) {
            if (!HrStaffPortletUtil.judge(info, dateArray, HrStaffPortletUtil.TYPE_ONTHEJOB)) {
                continue;
            }
            int age = info.getFdAge();
            if (age < 30) {
                age30++;
            } else if (age < 40) {
                age40++;
            } else if (age < 50) {
                age50++;
            } else if (age < 60) {
                age60++;
            } else {
                ageA++;
            }
        }
        Map<String, Integer> ageMap = new HashMap<>();
        ageMap.put("age30", age30);
        ageMap.put("age40", age40);
        ageMap.put("age50", age50);
        ageMap.put("age60", age60);
        ageMap.put("age30", age30);
        ageMap.put("ageA", ageA);
        json.put("age", ageMap);
        return "echartsAge";
    }

    /**
     * 功能-echrts 教育
     *
     * @param json
     * @param
     * @return
     */
    private String createEchartsEducationData(JSONObject json, Date[] dateArray)
            throws Exception {
        String[] EDU_COLOR = {"#FFAA8A", "#69A0FD", "#2270F1",
                "#F17474", "#6672FF", "#CBCBCB"};
        Map<String, Integer> eduMap = new HashMap<>();
        List<String> eduColor = new ArrayList<>();
        int i = 0;
        List<HrStaffPersonInfo> persons = (List<HrStaffPersonInfo>) getHrStaffPersonInfoServiceImp(
                null).findList(new HQLInfo());
        for (HrStaffPersonInfo info : persons) {
            if (!HrStaffPortletUtil.judge(info, dateArray, HrStaffPortletUtil.TYPE_ONTHEJOB)) {
                continue;
            }
            String eduKey = info.getFdHighestEducation();
            if (StringUtil.isNull(eduKey)) {
                continue;
            }
            Integer eduVal = eduMap.get(eduKey);
            if (eduVal == null) {
                eduVal = 1;
                i++;
                eduColor.add(EDU_COLOR[i % EDU_COLOR.length]);
            } else {
                eduVal++;
            }
            eduMap.put(eduKey, eduVal);
        }
        if (eduMap != null) {

        }
        json.put("edu", eduMap);
        json.put("eduColor", eduColor);
        return "echartsEducation";
    }

    /**
     * 功能-echrts 状态
     *
     * @param json
     * @param
     * @return
     */
    private String createEchartsEntryData(JSONObject json, Date[] dateArray)
            throws Exception {
        // 状态
        int leave = 0;
        int onthejob = 0;
        int entry = 0;
        List<HrStaffPersonInfo> persons = (List<HrStaffPersonInfo>) getHrStaffPersonInfoServiceImp(
                null).findList(new HQLInfo());
        for (HrStaffPersonInfo info : persons) {
            if (HrStaffPortletUtil.judge(info, dateArray, HrStaffPortletUtil.TYPE_ENTRY)) {
                entry++;
            }
            if (HrStaffPortletUtil.judge(info, dateArray, HrStaffPortletUtil.TYPE_LEAVE)) {
                leave++;
            }
            if (HrStaffPortletUtil.judge(info, dateArray, HrStaffPortletUtil.TYPE_ONTHEJOB)) {
                onthejob++;
            }

        }
        Map<String, Integer> statusMap = new HashMap<>();
        statusMap.put("leave", leave);
        statusMap.put("entry", entry);
        statusMap.put("onthejob", onthejob);
        json.put("status", statusMap);
        return "echartsEntryAndLeave";
    }

}
