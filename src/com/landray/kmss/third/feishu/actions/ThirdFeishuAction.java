package com.landray.kmss.third.feishu.actions;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.third.feishu.oms.FeishuOmsConfig;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ThirdFeishuAction extends BaseAction {

    public ActionForward cleanTime(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-cleanTime", true, getClass());
        KmssMessages messages = new KmssMessages();
        JSONObject json = new JSONObject();
        json.put("status", 1);
        json.put("msg", "成功");
        try {
            FeishuOmsConfig omsConfig = new FeishuOmsConfig();
            omsConfig.setLastSynchroTime(null);
            omsConfig.save();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            messages.addError(e);
            json.put("status", 0);
            json.put("msg", e.getMessage());
        }
        // 记录日志信息
        if (UserOperHelper.allowLogOper("cleanTime", "*")) {
            UserOperHelper.setModelNameAndModelDesc(null, ResourceUtil
                    .getString(
                            "third-feishu:third.feishu.oms.config.setting"));
            UserOperHelper.logMessage(json.toString());
        }
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(json.toString());
        TimeCounter.logCurrentTime("Action-cleanTime", false, getClass());
        return null;
    }

}
