package com.landray.kmss.third.ding.notify.util;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.sys.notify.service.ISysNotifyTodoService;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.ThirdDingWork;
import com.landray.kmss.third.ding.service.IThirdDingWorkService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.List;

public class NotifyUtil {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(NotifyUtil.class);

    private static ISysNotifyTodoService sysNotifyTodoService = null;

    public static ISysNotifyTodoService getSysNotifyTodoService() {
        if (sysNotifyTodoService == null) {
            sysNotifyTodoService = (ISysNotifyTodoService) SpringBeanUtil.getBean("sysNotifyTodoService");
        }
        return sysNotifyTodoService;
    }

    private static IThirdDingWorkService thirdDingWorkService;
    public static IThirdDingWorkService getThirdDingWorkService() {
        if (thirdDingWorkService == null) {
            thirdDingWorkService= (IThirdDingWorkService) SpringBeanUtil.getBean("thirdDingWorkService");
        }
        return thirdDingWorkService;
    }

    public static Long getAgendId(SysNotifyTodo todo) throws Exception {
        Long agentid = 0L;
        if (StringUtil
                .isNotNull(DingConfig.newInstance().getDingAgentid())) {
            agentid = Long.parseLong(DingConfig.newInstance().getDingAgentid());
            // 查找钉钉应用
            if ("true".equals(DingConfig.newInstance().getSendAsModel())) {
                String app_agentid = getDingWorkByModel(todo);
                if (logger.isDebugEnabled()) {
                    logger.debug("app_agentid:" + app_agentid);
                }
                if (StringUtil.isNotNull(app_agentid)) {
                    agentid = Long.valueOf(app_agentid);
                }
            }
        }
        return agentid;
    }

    private static String getDingWorkByModel(SysNotifyTodo todo) throws Exception {
        String agentId = null;
        HQLInfo hqlInfo = new HQLInfo();
        List<ThirdDingWork> dingWorkList = getThirdDingWorkService().findList(hqlInfo);
        if (null == dingWorkList || dingWorkList.size() <= 0) {
            return null;
        }
        String modelPreUrl = getSysNotifyTodoService().getModelPreUrl(todo);
        if(StringUtil.isNull(modelPreUrl)) {
            return null;
        }
        if(modelPreUrl.startsWith("/")) {
            modelPreUrl=modelPreUrl.substring(1);
        }
        if(modelPreUrl.endsWith("/")) {
            modelPreUrl=modelPreUrl.substring(0,modelPreUrl.length()-1);
        }
        logger.info("modelPreUrl:{}",modelPreUrl);
        agentId =findInList(modelPreUrl,dingWorkList);
        if(agentId == null && modelPreUrl.startsWith("sys/modeling/")){
            agentId =findInList("sys/modeling",dingWorkList);
        }
        return agentId;
    }

    private static String findInList(String modelPreUrl, List<ThirdDingWork> dingWorkList) {
        if(dingWorkList==null) {
            return null;
        }
        for (ThirdDingWork dingWork : dingWorkList) {
            if (StringUtil.isNotNull(dingWork.getFdUrlPrefix())) {
                List<String> appList = Arrays.asList(dingWork.getFdUrlPrefix().split(";"));
                if(appList.contains(modelPreUrl)) {
                    return dingWork.getFdAgentid();
                }
            }
        }
        return null;
    }
}
