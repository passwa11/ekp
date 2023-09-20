package com.landray.kmss.km.review.rest.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dto.HttpRequestParameterWrapper;
import com.landray.kmss.common.dto.PageVO;
import com.landray.kmss.common.dto.QueryRequest;
import com.landray.kmss.common.rest.controller.ColumnDatasController;
import com.landray.kmss.common.rest.util.ControllerHelper;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.km.review.actions.KmReviewIndexAction;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmProcess;
import com.landray.kmss.sys.lbpmperson.dao.ISysLbpmCreateDao;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.RestResponse;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONObject;

/**
 * @Author: admin
 * @Description: ${description}
 * @Date: 2020-12-11 14:46
 * @Version: 1.0
 */
@Controller
@RequestMapping(value = "/data/km-review/kmReviewIndex")
public class KmReviewIndexController extends ColumnDatasController {

    private KmReviewIndexAction action = new KmReviewIndexAction();

    @ResponseBody
    @RequestMapping(value = "list")
    public RestResponse<?> list(@RequestBody QueryRequest query, HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpRequestParameterWrapper reqWrapper = ControllerHelper.buildRequestParameterWrapper(request, query);
        action.list(emptyMapping, null, reqWrapper, response);
        Page queryPage = (Page)reqWrapper.getAttribute("queryPage");
        if (queryPage == null) {
            return RestResponse.error("请求失败");
        }
        PageVO pageVO = convert(reqWrapper, queryPage, KmReviewMain.class, "list");
        return result(reqWrapper, pageVO);
    }

    /**
     * @Description 统计各种状态文档数量
     * @param: query
     * @param: request
     * @param: response
     * @return com.landray.kmss.web.RestResponse<?> 
     * @throws 
     */
    @ResponseBody
    @RequestMapping(value = "count")
    public RestResponse<?> count(@RequestBody Map requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpRequestParameterWrapper reqWrpper = ControllerHelper.buildRequestParameterWrapper(request, requestBody);
        action.countAllStatus(emptyMapping, null, reqWrpper, response);
        Map<String, String> statuCounts = new HashMap<>();
        List<Object> list = (List<Object>)reqWrpper.getAttribute("lui-source");
        list.forEach((Object obj) -> {
            Object[] counts = (Object[]) obj;
            String count = counts[0].toString();
            String douStatus = counts[1].toString();
            statuCounts.put(douStatus,count);
        });
        return result(request, statuCounts);
    }

    /**
     * @Description 统计待我处理代办、待阅，我已办结，我发起的，流程库数量
     * @param: query
     * @param: request
     * @param: response
     * @return com.landray.kmss.web.RestResponse<?>
     * @throws
     */
    @ResponseBody
    @RequestMapping(value = "countIndex")
    public RestResponse<?> countIndex(@RequestBody Map requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpRequestParameterWrapper reqWrapper = ControllerHelper.buildRequestParameterWrapper(request, requestBody);
        Map<String, Map<String, String>> counts = new HashMap<>();
        //获取待我审的数量
        reqWrapper.putParameter("q.mydoc", "approval");
        Map<String, String> approvalCount = countByDate(reqWrapper, response);
        counts.put("approval", approvalCount);
        //获取我已办结的数据量
        reqWrapper.putParameter("q.mydoc", "approved");
        Map<String, String> approvaledCount = countByDate(reqWrapper, response);
        counts.put("approved", approvaledCount);
        //获取我发起的数量
        reqWrapper.putParameter("q.mydoc", "create");
        Map<String, String> createCount = countByDate(reqWrapper, response);
        counts.put("create", createCount);
        //流程库数量
        reqWrapper.putParameter("q.mydoc", "");
        Map<String, String> allCount = countByDate(reqWrapper, response);
        counts.put("all", allCount);
        return result(reqWrapper, counts);
    }
    
    /**
     * @Description 统计本月、本年流程数量
     * @param: reqWrapper
     * @param: response
     * @return java.util.Map<java.lang.String,java.lang.String> 
     * @throws 
     */
    private Map<String,String> countByDate(HttpRequestParameterWrapper reqWrapper, HttpServletResponse response) throws Exception{
        Map<String, String> count = new HashMap<>();
        //本月开始时间
        String currentMonthStart = getCurrentMonthStart();
        //本月结束时间
        String currentMonthEnd = getCurrentMonthEnd();
        //本年开始时间
        String currentYearStart = getCurrentYearStart();
        //本年结束时间
        String currentYearEnd = getCurrentYearEnd();
        reqWrapper.putParameter("q.docCreateTime", new String[]{currentMonthStart, currentMonthEnd});
        action.count(emptyMapping,null, reqWrapper, response);
        JSONObject monthCount = (JSONObject)reqWrapper.getAttribute("lui-source");
        count.put("month", monthCount.optString("count"));
        reqWrapper.putParameter("q.docCreateTime", new String[]{currentYearStart, currentYearEnd});
        action.count(emptyMapping,null, reqWrapper, response);
        JSONObject yearCount = (JSONObject)reqWrapper.getAttribute("lui-source");
        count.put("year", yearCount.optString("count"));
        return count;
    }

    /**
     * @Description 获取本月的第一天
     * @return java.lang.String
     * @throws 
     */
    private String getCurrentMonthStart(){
        LocalDate localDate = LocalDate.now();
        Date monthStart = Date.from(localDate.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay(ZoneId.systemDefault()).toInstant());
        String monthStartStr = DateUtil.convertDateToString(monthStart, null);
        return monthStartStr;
    }

    /**
     * @Description 获取本月的最后一天
     * @return java.lang.String
     * @throws
     */
    private String getCurrentMonthEnd(){
        LocalDate localDate = LocalDate.now();
        Date monthEnd = Date.from(localDate.plusMonths(1).with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay(ZoneId.systemDefault()).toInstant());
        String monthEndStr = DateUtil.convertDateToString(monthEnd, null);
        return monthEndStr;
    }

    /**
     * @Description 获取本年的第一天
     * @return java.lang.String
     * @throws
     */
    private String getCurrentYearStart(){
        LocalDate localDate = LocalDate.now();
        Date yearStart = Date.from(localDate.with(TemporalAdjusters.firstDayOfYear()).atStartOfDay(ZoneId.systemDefault()).toInstant());
        String yearStartStr = DateUtil.convertDateToString(yearStart, null);
        return yearStartStr;
    }

    /**
     * @Description 获取本年的最后一天
     * @return java.lang.String
     * @throws
     */
    private String getCurrentYearEnd(){
        LocalDate localDate = LocalDate.now();
        Date yearEnd = Date.from(localDate.plusYears(1).with(TemporalAdjusters.firstDayOfYear()).atStartOfDay(ZoneId.systemDefault()).toInstant());
        String yearEndStr = DateUtil.convertDateToString(yearEnd, null);
        return yearEndStr;
    }

    /**
     * @Description 统计一年内每个月我发起的流程数量
     * @param: query
     * @param: request
     * @param: response
     * @return com.landray.kmss.web.RestResponse<?>
     * @throws
     */
    @ResponseBody
    @RequestMapping(value = "listCreateSummary")
    public RestResponse<?> listCreateSummary(@RequestBody Map requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception{
        ISysLbpmCreateDao sysLbpmCreateDaoImp = (ISysLbpmCreateDao) SpringBeanUtil
                .getBean("sysLbpmCreateDaoImp");
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        Map<String, Map<String,Object>> rtnData = new HashMap<>();
        //当前时间前12个月
        HQLInfo hqlInfo = getCreateSummarayHql(false);
        Page page = sysLbpmCreateDaoImp.findPage(hqlInfo);
        List data = page.getList();
        rtnData.put(String.valueOf(year), formatData(data, calendar));
        //上一年当前时间前12个月
        hqlInfo = getCreateSummarayHql(true);
        page = sysLbpmCreateDaoImp.findPage(hqlInfo);
        data = page.getList();
        Calendar lastYearCal = Calendar.getInstance();
        lastYearCal.add(Calendar.YEAR, -1);
        rtnData.put(String.valueOf(year - 1), formatData(data, lastYearCal));
        return result(request, rtnData);
    }

    /**
     * @Description 统计一年内每个月我处理的流程数量
     * @param: query
     * @param: request
     * @param: response
     * @return com.landray.kmss.web.RestResponse<?>
     * @throws
     */
    @ResponseBody
        @RequestMapping(value = "listApprovedSummary")
    public RestResponse<?> listApprovedSummary(@RequestBody Map requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception{
        ISysLbpmCreateDao sysLbpmCreateDaoImp = (ISysLbpmCreateDao) SpringBeanUtil
                .getBean("sysLbpmCreateDaoImp");
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        Map<String, Map<String,Object>> rtnData = new HashMap<>();
        //当前时间前12个月
        HQLInfo hqlInfo = getApprovedSummarayHql(false);
        Page page = sysLbpmCreateDaoImp.findPage(hqlInfo);
        List data = page.getList();
        rtnData.put(String.valueOf(year), formatData(data, calendar));
        //上一年当前时间前12个月
        hqlInfo = getCreateSummarayHql(true);
        page = sysLbpmCreateDaoImp.findPage(hqlInfo);
        data = page.getList();
        Calendar lastYearCal = Calendar.getInstance();
        lastYearCal.add(Calendar.YEAR, -1);
		rtnData.put(String.valueOf(year - 1), formatData(data, lastYearCal));
        return result(request, rtnData);
    }

    private static String fillZero(int i) {
        String str = "";
        if (i > 0 && i < 10) {
            str = "0" + i;
        } else {
            str = "" + i;
        }
        return str;

    }

    private Map<String, Object> formatData(List data, Calendar calendar){
        Map<String, Object> monthNum = new HashMap<>();
        if (data != null) {
            String[] last12Months = new String[12];
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1); // 要先+1,才能把本月的算进去</span>
            for (int i = 0; i < last12Months.length; i++) {
                if (calendar.get(Calendar.MONTH) - i < 1) {
                    last12Months[11 - i] = calendar.get(Calendar.YEAR) - 1 + "-"
                            + fillZero(
                            (calendar.get(Calendar.MONTH) - i + 12 * 1));
                } else {
                    last12Months[11 - i] = calendar.get(Calendar.YEAR) + "-"
                            + fillZero((calendar.get(Calendar.MONTH) - i));
                }
            }
            for (int i = 0; i < last12Months.length; i++) {
                boolean hasIn = false;
                for (Object object : data) {
                    Object[] item = (Object[]) object;
                    if (last12Months[i].equals((String) item[1])) {
                        hasIn = true;
                        Long num = (Long) item[0] == null ? 0 : (Long) item[0];
                        monthNum.put((String)item[1], num);
                        break;
                    }
                }
                // 补齐数据库中缺失的月份
                if (!hasIn) {
                    monthNum.put(last12Months[i], 0);
                }
            }
        }
        return monthNum;
    }

    public static String getMonthEnd(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        Date time = cal.getTime();
        String dateToString = DateUtil.convertDateToString(time, "yyyy-MM");
        return dateToString;
    }

    public static String getMonthBegin(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -11);
        cal.add(Calendar.YEAR, -1);
        Date time = cal.getTime();
        String dateToString = DateUtil.convertDateToString(time, "yyyy-MM");
        return dateToString;
    }
    
    private HQLInfo getCreateSummarayHql(boolean isLastYear) {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setRowSize(12);
        // 设置创建人 都是查当前创建人的流程相关信息
        String whereBlock = "lbpmProcess.fdCreator.fdId=:fdCreatorId and lbpmProcess.fdStatus !='10' and lbpmProcess.docDeleteFlag=0";
        if (isLastYear) {
            whereBlock = StringUtil.linkString(whereBlock, " and ", "lbpmProcess.fdCreateMonth between :startMonth and :endMonth");
            hqlInfo.setParameter("startMonth", getMonthBegin());
            hqlInfo.setParameter("endMonth", getMonthEnd());
        }
        whereBlock = StringUtil.linkString(whereBlock, " ", "group by lbpmProcess.fdCreateMonth order by fdCreateMonth desc");
        hqlInfo.setWhereBlock(whereBlock);
        hqlInfo.setParameter("fdCreatorId", UserUtil.getKMSSUser()
                .getUserId());

        hqlInfo.setSelectBlock("count(lbpmProcess.fdCreateMonth), lbpmProcess.fdCreateMonth");
        hqlInfo.setModelName(LbpmProcess.class.getName());
        return hqlInfo;
    }

    private HQLInfo getApprovedSummarayHql(boolean isLastYear) {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setRowSize(12);
        hqlInfo.setModelName(LbpmProcess.class.getName());
		hqlInfo.setJoinBlock(",LbpmHistoryWorkitem lbpmHistoryWorkitem");
        // 设置创建人 都是查当前创建人的流程相关信息
		String whereBlock = "lbpmProcess.fdId=lbpmHistoryWorkitem.fdProcess.fdId and lbpmProcess.fdStatus !='10' and lbpmHistoryWorkitem.fdHandler.fdId = :myHandlerIds and lbpmHistoryWorkitem.fdActivityType<>'draftWorkitem' and lbpmProcess.docDeleteFlag=0";
        if (isLastYear) {
            whereBlock = StringUtil.linkString(whereBlock, " and ", "lbpmProcess.fdCreateMonth between :startMonth and :endMonth");
            hqlInfo.setParameter("startMonth", getMonthBegin());
            hqlInfo.setParameter("endMonth", getMonthEnd());
        }
        whereBlock = StringUtil.linkString(whereBlock, " ", "group by lbpmProcess.fdCreateMonth order by fdCreateMonth desc");
        hqlInfo.setWhereBlock(whereBlock);
        hqlInfo.setParameter("myHandlerIds", UserUtil
                .getKMSSUser().getUserId());
        hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
                SysAuthConstant.AllCheck.NO);
        hqlInfo.setSelectBlock("count(lbpmProcess.fdCreateMonth), lbpmProcess.fdCreateMonth");
        String joinBlock = hqlInfo.getJoinBlock();
        // 解决多表联合查询无法排序处理
        String orderBy = hqlInfo.getOrderBy();
        if ((joinBlock != null && joinBlock.contains(","))
                && StringUtil.isNotNull(orderBy)) {
            if (orderBy.contains(".")) {
                hqlInfo.setOrderBy(orderBy);
            } else {
                hqlInfo.setOrderBy(" lbpmProcess." + orderBy);
            }
        }
        return hqlInfo;
    }

}
