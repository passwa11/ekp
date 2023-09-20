package com.landray.kmss.third.ding.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.landray.kmss.km.calendar.model.KmCalendarSyncMapping;
import com.landray.kmss.km.calendar.service.IKmCalendarSyncMappingService;
import com.landray.kmss.third.ding.action.ThirdDingCalendarAction;
import com.landray.kmss.third.ding.provider.DingCalendarApiProviderImpV3;
import com.landray.kmss.third.ding.service.IThirdDingCalendarLogService;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.third.ding.service.IThirdDingCalendarService;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.ding.model.ThirdDingCalendar;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


public class ThirdDingCalendarServiceImp extends ExtendDataServiceImp implements IThirdDingCalendarService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingCalendarServiceImp.class);


    private IKmCalendarSyncMappingService kmCalendarSyncMappingService = null;

    public IKmCalendarSyncMappingService kmCalendarSyncMappingService() {
        if (kmCalendarSyncMappingService == null) {
            kmCalendarSyncMappingService = (IKmCalendarSyncMappingService) SpringBeanUtil.getBean("kmCalendarSyncMappingService");
        }
        return kmCalendarSyncMappingService;
    }


    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof ThirdDingCalendar) {
            ThirdDingCalendar thirdDingCalendar = (ThirdDingCalendar) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        ThirdDingCalendar thirdDingCalendar = new ThirdDingCalendar();
        thirdDingCalendar.setDocCreateTime(new Date());
        ThirdDingUtil.initModelFromRequest(thirdDingCalendar, requestContext);
        return thirdDingCalendar;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        ThirdDingCalendar thirdDingCalendar = (ThirdDingCalendar) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

    private IThirdDingCalendarService thirdDingCalendarService = null;

    public IThirdDingCalendarService getThirdDingCalendarLogService() {
        if (thirdDingCalendarService == null) {
            thirdDingCalendarService = (IThirdDingCalendarService) SpringBeanUtil
                    .getBean("thirdDingCalendarService");
        }
        return thirdDingCalendarService;
    }

    @Override
    public boolean updateCalendarIdConvert(List<KmCalendarSyncMapping> kmCalendarSyncMappings, String unionId) {

        logger.debug("旧id替换新id开始...");
        List<String> oldIds = null;
        try {
            //得到v1 v2旧id
            oldIds = kmCalendarSyncMappings
                    .stream()
                    .map(KmCalendarSyncMapping::getFdAppUuid)
                    .collect(Collectors.toList());

            if(CollectionUtils.isEmpty(oldIds)){
                return false;
            }
        } catch (Exception e) {
            logger.error("根据fdAppKey查询fdAppUuid失败" + e.getMessage());
            return false;
        }

        /** 封装参数 钉钉接口入参格式
         *  {
         *
         *    "legacyEventIds" : ["4C0D45BF273A77F9C3B5A6C9C9028014","B8C86C7C8CA51E3BF4209B1C163A9526"]
         *  }
         *  路径参数：unionId
         *  返回值
         *  {
         *     "legacyEventIdMap": {
         *         "0CB025F452918308001207FD49FBCAC4": "QW1PdjRRYU9TalZTMUhNd3RyTFBFZz09",
         *         "4C0D45BF273A77F9C3B5A6C9C9028014": "U2ZNUGRjb1NLaUJiUittSzFPUmdldz09",
         *         "B8C86C7C8CA51E3BF4209B1C163A9526": ""
         *     },
         *     "requestId": "05118F56-5012-4CB9-84BC-2440305C5B7C"
         * }
         */
        LinkedHashMap<String, List<String>> oldIdsMap = new LinkedHashMap<>();
        oldIdsMap.put("legacyEventIds", oldIds);
        JSONObject newIdsObject = null;

        try {
            //旧id 替换成新id
            newIdsObject = DingUtils.getDingApiService().calendarIdConvert(unionId, oldIdsMap);
        } catch (Exception e) {
            logger.error("旧id 替换成新id失败" + e.getMessage());
            return false;
        }

        assert newIdsObject != null;

        JSONObject legacyEventIdMap = newIdsObject.getJSONObject("legacyEventIdMap");

        List<String> newIds = new ArrayList<>();
        List<String> ids = new ArrayList<>();

        for (String oldId : oldIds) {
            String newId = legacyEventIdMap.getString(oldId);
            if (StringUtil.isNotNull(newId)) {
                //置换后的id
                newIds.add(DingCalendarApiProviderImpV3.PREFIX_V3_API+ newId);
                //得到v2的id
                ids.add(oldId);
            }
            logger.debug("旧id为:{},替换后的新id为:{}", oldId, newId);
        }
        //找到所有所需要替换id的实体
        List<KmCalendarSyncMapping> mappings = kmCalendarSyncMappings
                .stream()
                .filter(s -> ids
                        .stream()
                        .anyMatch(e -> e.equals(s.getFdAppUuid())))
                .collect(Collectors.toList());

        try {
            //把新id加上  $V3API$ 前缀后保存
            for (int i = 0; i < newIds.size(); i++) {
                mappings.get(i).setFdAppUuid(newIds.get(i));
                kmCalendarSyncMappingService().update(mappings.get(i));
            }
            return true;
        } catch (Exception e) {
            logger.error("更新失败" + e.getMessage());
            return false;
        }

    }
}
