package com.landray.kmss.sys.remind.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.config.design.SysCfgModule;
import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.remind.model.SysRemindTemplate;
import com.landray.kmss.sys.remind.model.SysRemindTemplateRelation;
import com.landray.kmss.sys.remind.service.ISysRemindMainService;
import com.landray.kmss.sys.remind.service.ISysRemindTemplateRelationService;
import com.landray.kmss.sys.remind.service.ISysRemindTemplateService;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 工具类
 *
 * @author panyh
 * @date Jun 30, 2020
 */
public class SysRemindUtil {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysRemindUtil.class);
    private static ISysRemindTemplateService sysRemindTemplateService;
    private static ISysRemindTemplateRelationService sysRemindTemplateRelationService;
    private static ISysRemindMainService sysRemindMainService;

    public static ISysRemindTemplateService getSysRemindTemplateService() {
        if (sysRemindTemplateService == null) {
            sysRemindTemplateService = (ISysRemindTemplateService) SpringBeanUtil.getBean("sysRemindTemplateService");
        }
        return sysRemindTemplateService;
    }

    public static ISysRemindTemplateRelationService getSysRemindTemplateRelationService() {
        if (sysRemindTemplateRelationService == null) {
            sysRemindTemplateRelationService = (ISysRemindTemplateRelationService) SpringBeanUtil
                    .getBean("sysRemindTemplateRelationService");
        }
        return sysRemindTemplateRelationService;
    }

    public static ISysRemindMainService getSysRemindMainService() {
        if (sysRemindMainService == null) {
            sysRemindMainService = (ISysRemindMainService) SpringBeanUtil.getBean("sysRemindMainService");
        }
        return sysRemindMainService;
    }

    /**
     * 根据待办类型获取名称
     *
     * @param notifyType
     * @return
     */
    public static String getNotifyType(String notifyType) {
        IExtension[] extensions = Plugin.getExtensions("com.landray.kmss.sys.notify", "*", "notifyType");
        for (int i = 0; i < extensions.length; i++) {
            String key = (String) Plugin.getParamValue(extensions[i], "key");
            String name = (String) Plugin.getParamValue(extensions[i], "name");
            if (key.equals(notifyType)) {
                return name;
            }
        }
        return "";
    }

    /**
     * 与当前时间比对
     *
     * @param date
     * @return
     */
    public static boolean isFinish(Date date) {
        if (date == null) {
            return true;
        }
        Calendar cal = Calendar.getInstance();
        return cal.getTime().after(date);
    }

    /**
     * 获取所有部署提醒中心的模块
     *
     * @return
     */
    public static JSONArray getModules() {
        JSONArray array = new JSONArray();
        try {
            List<SysRemindTemplateRelation> list = getSysRemindTemplateRelationService().findList("", "");
            if (CollectionUtils.isNotEmpty(list)) {
                Map<String, List<SysRemindTemplateRelation>> map = new HashMap<String, List<SysRemindTemplateRelation>>();
                for (SysRemindTemplateRelation relation : list) {
                    String moduleUrl = relation.getFdModuleUrl();
                    List<SysRemindTemplateRelation> temp = map.get(moduleUrl);
                    if (temp == null) {
                        temp = new ArrayList<SysRemindTemplateRelation>();
                        map.put(moduleUrl, temp);
                    }
                    temp.add(relation);
                }
                for (String moduleUrl : map.keySet()) {
                    SysCfgModule cfgModule = SysConfigs.getInstance().getModule(moduleUrl);
                    if (cfgModule == null) {
                        continue;
                    }
                    JSONObject module = new JSONObject();
                    module.put("title", ResourceUtil.getString(cfgModule.getMessageKey()));
                    List<SysRemindTemplateRelation> relations = map.get(moduleUrl);
                    JSONArray templates = new JSONArray();
                    Set<String> templateNames = new HashSet<>();
                    for (SysRemindTemplateRelation relation : relations) {
                        String templateName = relation.getFdTemplateName();
                        if (templateNames.contains(templateName)) {
                            // 模板去重
                            continue;
                        }
                        templateNames.add(templateName);
                        SysDictModel dictModel = SysDataDict.getInstance().getModel(templateName);
                        if (dictModel != null) {
                            JSONObject tmpl = new JSONObject();
                            tmpl.put("tmplName", templateName);
                            tmpl.put("title", ResourceUtil.getString(dictModel.getMessageKey()));
                            tmpl.put("tmpls", getTemplates(dictModel, templateName));
                            templates.add(tmpl);
                        }
                    }
                    module.put("templates", templates);
                    array.add(module);
                }
            }
        } catch (Exception e) {
            logger.error("已注册提醒中心获取模块列表错误", e);
        }
        return array;
    }

    private static JSONArray getTemplates(SysDictModel dictModel, String templateName) throws Exception {
        JSONArray templates = new JSONArray();
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fdTemplateName = :templateName");
        hqlInfo.setParameter("templateName", templateName);
        List<SysRemindTemplate> tmplList = getSysRemindTemplateService().findList(hqlInfo);
        if (CollectionUtils.isNotEmpty(tmplList)) {
            for (SysRemindTemplate tmpl : tmplList) {
                IBaseModel templateModel = getTemplateModel(tmpl.getFdTemplateName(), tmpl.getFdTemplateId());
                if (templateModel != null && StringUtil.isNotNull(dictModel.getDisplayProperty())) {
                    JSONObject template = new JSONObject();
                    template.put("title", BeanUtils.getProperty(templateModel, dictModel.getDisplayProperty()));
                    template.put("tmplId", tmpl.getFdId());
                    templates.add(template);
                }
            }
        }
        return templates;
    }

    private static IBaseModel getTemplateModel(String templateName, String templateId) throws Exception {
        return getSysRemindTemplateService().findByPrimaryKey(templateId, templateName, true);
    }

}
