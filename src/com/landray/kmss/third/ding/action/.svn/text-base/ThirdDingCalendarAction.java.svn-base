package com.landray.kmss.third.ding.action;


import com.landray.kmss.km.calendar.cms.interfaces.SyncroCommonCal;
import com.landray.kmss.km.calendar.model.KmCalendarSyncMapping;
import com.landray.kmss.km.calendar.service.IKmCalendarSyncMappingService;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.third.ding.model.ThirdDingCalendar;
import com.landray.kmss.third.ding.forms.ThirdDingCalendarForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.third.ding.provider.DingCalendarApiProviderImpV3;
import com.landray.kmss.third.ding.provider.DingCalendarProvider;
import com.landray.kmss.third.ding.service.IThirdDingCalendarService;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;

import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.util.CollectionUtils;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ThirdDingCalendarAction extends ExtendAction {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingCalendarAction.class);


    private IThirdDingCalendarService thirdDingCalendarService =
            (IThirdDingCalendarService) getBean("thirdDingCalendarService");

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdDingCalendarService == null) {
            thirdDingCalendarService = (IThirdDingCalendarService) getBean("thirdDingCalendarService");
        }
        return thirdDingCalendarService;
    }

    private IKmCalendarSyncMappingService kmCalendarSyncMappingService = null;

    public IKmCalendarSyncMappingService kmCalendarSyncMappingService() {
        if (kmCalendarSyncMappingService == null) {
            kmCalendarSyncMappingService = (IKmCalendarSyncMappingService) SpringBeanUtil.getBean("kmCalendarSyncMappingService");
        }
        return kmCalendarSyncMappingService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdDingCalendar.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        ThirdDingUtil.buildHqlInfoDate(hqlInfo, request, ThirdDingCalendar.class);
        ThirdDingUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdDingCalendarForm thirdDingCalendarForm = (ThirdDingCalendarForm) super.createNewForm(mapping, form, request, response);
        ((IThirdDingCalendarService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdDingCalendarForm;
    }

    /**
     * 旧id 替换成新id
     */
    @GET
    public ActionForward calendarIdConvert(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {

        String calendarInitFlag = getCalendarInitFlag();
        if (StringUtil.isNotNull(calendarInitFlag)
                && "true".equals(calendarInitFlag)) {
            logger.debug("旧id替换新id操作已完成，无需重复操作");
            responseJson("true", response, 200, "旧id替换新id操作已完成，无需重复操作");
            return null;
        }

        HQLInfo hqlInfo = new HQLInfo();
        //hqlInfo.setSelectBlock("kmCalendarSyncMapping.fdAppUuid");
        hqlInfo
                .setWhereBlock("kmCalendarSyncMapping.fdAppKey=:fdAppKey and kmCalendarSyncMapping.fdAppUuid not like :fdAppUuid");
        hqlInfo.setParameter("fdAppKey", "dingCalendar");
        hqlInfo.setParameter("fdAppUuid", DingCalendarApiProviderImpV3.PREFIX_V3_API + "%");

        List<KmCalendarSyncMapping> kmCalendarSyncMappings = kmCalendarSyncMappingService().findList(hqlInfo);
        if (CollectionUtils.isEmpty(kmCalendarSyncMappings)) {
            logger.debug("映射表中没有可替换的数据....");
            responseJson(null, response, 500, "映射表中没有可替换的数据");
            return null;
        }

        //获取一个管理员id
        String dingAdminUinionId = DingUtil.getDingAdminUinionId();
        if (StringUtil.isNull(dingAdminUinionId)) {
            logger.warn("请检查钉钉集成配置....");
            responseJson(null, response, 500, "请检查钉钉集成配置");
            return null;
        }
        boolean flag = thirdDingCalendarService.updateCalendarIdConvert(kmCalendarSyncMappings, dingAdminUinionId);

        if (flag) {
            AddCalendarInitFlag("true");
            responseJson(calendarInitFlag, response, 200, "替换成功");
        } else {
            responseJson(null, response, 500, "替换失败");
        }
        return null;
    }


    public void AddCalendarInitFlag(String flag) throws Exception {
        ISysAppConfigService sysAppConfigService = (ISysAppConfigService) SpringBeanUtil.getBean("sysAppConfigService");
        Map<String, Object> dataMap = new HashMap();
        dataMap.put("calendarInitFlag", flag);
        sysAppConfigService
                .add("com.landray.kmss.third.ding.model.DingConfig", dataMap);

    }

    public String getCalendarInitFlag() throws Exception {
        ISysAppConfigService sysAppConfigService = (ISysAppConfigService) SpringBeanUtil.getBean("sysAppConfigService");
        String initPersonFlag = "false";
        Map orgMap = sysAppConfigService
                .findByKey("com.landray.kmss.third.ding.model.DingConfig");
        if (orgMap != null && orgMap.containsKey("calendarInitFlag")) {
            String noObject = (String) orgMap.get("calendarInitFlag");
            if (noObject != null) {
                initPersonFlag = noObject;
            }
        }
        return initPersonFlag;
    }


    private <T> void responseJson(T obj, HttpServletResponse response, Integer resultCode, String message) {
        JSONObject json = new JSONObject();
        json.put("resultCode", resultCode);
        json.put("data", obj);
        json.put("message", message);
        response.setContentType("text/javascript;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
            PrintWriter out = response.getWriter();
            out.write(json.toString());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
