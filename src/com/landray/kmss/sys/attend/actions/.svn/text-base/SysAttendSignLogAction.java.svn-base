package com.landray.kmss.sys.attend.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.attend.model.SysAttendConfig;
import com.landray.kmss.sys.attend.model.SysAttendSignLog;
import com.landray.kmss.sys.attend.service.ISysAttendConfigService;
import com.landray.kmss.sys.attend.service.ISysAttendSignLogService;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 签到记录历史
 * @author wj
 * @date 2021-10-19
 */
public class SysAttendSignLogAction extends ExtendAction {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendSignLogAction.class);


    private ISysAttendSignLogService sysAttendSignLogService;

    @Override
    public ISysAttendSignLogService getServiceImp(HttpServletRequest request) {
        if (sysAttendSignLogService == null) {
            sysAttendSignLogService = (ISysAttendSignLogService) getBean("sysAttendSignLogService");
        }
        return sysAttendSignLogService;
    }

    private ISysAttendConfigService sysAttendConfigService;

    public ISysAttendConfigService getSysAttendConfigServiceImp() {
        if (sysAttendConfigService == null) {
            sysAttendConfigService = (ISysAttendConfigService) SpringBeanUtil.getBean("sysAttendConfigService");
        }
        return sysAttendConfigService;
    }
    @Override
    protected void changeFindPageHQLInfo(HttpServletRequest request,
                                         HQLInfo hqlInfo) throws Exception {
        super.changeFindPageHQLInfo(request, hqlInfo);
        CriteriaValue cv = new CriteriaValue(request);
        CriteriaUtil.buildHql(cv, hqlInfo, SysAttendSignLog.class);
        String fdOperatorId = request.getParameter("q.fdOperatorId");
        if (StringUtil.isNotNull(fdOperatorId)) {
            String whereBlock = hqlInfo.getWhereBlock();
            whereBlock = StringUtil.linkString(whereBlock, " and ",
                    "sysAttendSignLog.docCreator.fdId=:fdOperatorId2");
            hqlInfo.setParameter("fdOperatorId2", fdOperatorId);
            hqlInfo.setWhereBlock(whereBlock);
        }
    }

    public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("Action-statListDetail", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            //获取配置的转存天数周期
            SysAttendConfig sysAttendConfig =  getSysAttendConfigServiceImp().getSysAttendConfig();
            Integer day=120;
            if(sysAttendConfig !=null) {
                day=sysAttendConfig.getFdSignLogToHisDay();
            }
            request.setAttribute("_info1", String.format("%s%s", day, ResourceUtil.getString("sysAttendSignLog.title.before", "sys-attend")));
            request.setAttribute("_info2", String.format("%s%s", day, ResourceUtil.getString("sysAttendSignLog.title.after", "sys-attend")));

        } catch (Exception e) {
            messages.addError(e);
            logger.error(e.getMessage(), e);
        }
        TimeCounter.logCurrentTime("Action-statListDetail", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
                    .save(request);
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("index", mapping, form, request, response);
        }
    }


}
