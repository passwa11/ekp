package com.landray.kmss.sys.evaluation.rest.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landray.kmss.common.rest.controller.BaseController;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyConfigService;
import com.landray.kmss.web.RestResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @ClassName: SysEvaluationNotifyTypeController
 * @Author: admin
 * @Description: ${description}
 * @Date: 2020-11-19 23:49
 * @Version: 1.0
 */
@Controller
@RequestMapping(value = "/data/sys-evaluation/sysEvaluationNotifyType", method = RequestMethod.POST)
public class SysEvaluationNotifyTypeController extends BaseController {

    private static final String EXTENSION_POINT = "com.landray.kmss.sys.notify";

    private static final String ITEM_NAME = "notifyType";

    private static final String PARAM_KEY = "key";

    private static final String PARAM_NAME = "name";

    private static final String PARAM_SERVICE = "service";

    protected static String[] optionKeys = null;

    protected static String[] optionValues = null;

    @ResponseBody
    @RequestMapping("getNotifyTypes")
	public RestResponse<?> getNotifyTypes(HttpServletRequest request,
			HttpServletResponse response) {
        IExtension[] extensions = Plugin.getExtensions(EXTENSION_POINT, "*",
                ITEM_NAME);
        JSONArray notifyTypes = new JSONArray();
        for (int i = 0; i < extensions.length; i++) {
            //获取配置中心的配置值，过滤已开启的扩展点
            ISysNotifyConfigService configService = Plugin.getParamValue(extensions[i],
                    PARAM_SERVICE);
            if (configService != null) {
                boolean enable = configService.getNotifyType();
                if (!enable) {
                    continue;
                }
            }
            JSONObject notifyType = new JSONObject();
            // 获得通知方式的key与对应名称
            String name = (String) Plugin.getParamValue(extensions[i],
                    PARAM_NAME);
            notifyType.accumulate("text",name);
            String key = (String) Plugin
                    .getParamValue(extensions[i], PARAM_KEY);
            notifyType.accumulate("value",key);
            notifyTypes.add(notifyType);
        }
        return RestResponse.ok(notifyTypes, "请求成功");
    }
}
