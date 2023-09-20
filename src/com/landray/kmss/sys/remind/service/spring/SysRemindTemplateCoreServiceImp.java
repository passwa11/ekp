package com.landray.kmss.sys.remind.service.spring;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseCoreOuterServiceImp;
import com.landray.kmss.common.service.ICoreOuterService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.remind.constant.SysRemindConstant;
import com.landray.kmss.sys.remind.forms.SysRemindMainForm;
import com.landray.kmss.sys.remind.forms.SysRemindMainReceiverForm;
import com.landray.kmss.sys.remind.forms.SysRemindMainTriggerForm;
import com.landray.kmss.sys.remind.forms.SysRemindTemplateForm;
import com.landray.kmss.sys.remind.model.SysRemindMain;
import com.landray.kmss.sys.remind.model.SysRemindMainReceiver;
import com.landray.kmss.sys.remind.model.SysRemindMainTrigger;
import com.landray.kmss.sys.remind.model.SysRemindTemplate;
import com.landray.kmss.sys.remind.model.SysRemindTemplateRelation;
import com.landray.kmss.sys.remind.service.ISysRemindTemplateRelationService;
import com.landray.kmss.sys.remind.service.ISysRemindTemplateService;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 提醒中心模板
 *
 * @author panyh
 * @date Jun 23, 2020
 */
public class SysRemindTemplateCoreServiceImp extends BaseCoreOuterServiceImp
        implements ICoreOuterService, SysRemindConstant {
    private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    private ISysRemindTemplateService sysRemindTemplateService;

    private ISysRemindTemplateRelationService sysRemindTemplateRelationService;

    public void setSysRemindTemplateService(ISysRemindTemplateService sysRemindTemplateService) {
        this.sysRemindTemplateService = sysRemindTemplateService;
    }

    public void setSysRemindTemplateRelationService(
            ISysRemindTemplateRelationService sysRemindTemplateRelationService) {
        this.sysRemindTemplateRelationService = sysRemindTemplateRelationService;
    }

    /**
     * 从业务模块中获取提醒中心
     *
     * @param bean
     * @return
     */
    private Map<String, Object> getSysRemind(Object bean) {
        try {
            if (bean != null && PropertyUtils.isReadable(bean, MODULE_KEY)) {
                Object property = PropertyUtils.getProperty(bean, MODULE_KEY);
                if (property instanceof Map) {
                    return (Map<String, Object>) property;
                }
            }
        } catch (Exception e) {
            logger.warn("获取提醒设置失败：", e);
        }
        return null;
    }

    /**
     * 从提交的form中获取提醒设置
     *
     * @param form
     * @return
     */
    private SysRemindTemplateForm support(IExtendForm form) {
        Map<String, Object> map = getSysRemind(form);
        if (map != null) {
            try {
                Object object = map.get(TEMPLATE_KEY);
                JSONObject json = null;
                if (object != null) {
                    if (object instanceof String) {
                        json = JSONObject.parseObject(object.toString());
                    } else if (object instanceof String[]) {
                        json = JSONObject.parseObject(((String[]) object)[0].toString());
                    }
                }
                if (json != null) {
                    SysRemindTemplateForm tmplForm = new SysRemindTemplateForm();
                    tmplForm.setFdKey(getString(json, "fdKey"));
                    String fdModuleUrl = getString(json, "fdModuleUrl");
                    if (StringUtil.isNotNull(fdModuleUrl)) {
                        if (!fdModuleUrl.startsWith("/")) {
                            fdModuleUrl = "/" + fdModuleUrl;
                        }
                        if (!fdModuleUrl.endsWith("/")) {
                            fdModuleUrl = fdModuleUrl + "/";
                        }
                    }
                    tmplForm.setFdModuleUrl(fdModuleUrl);
                    tmplForm.setFdTemplateProperty(getString(json, "fdTemplateProperty"));
                    tmplForm.setFdTemplateName(getString(json, "fdTemplateName"));
                    tmplForm.setFdModelName(getString(json, "fdModelName"));
                    if (!json.containsKey("fdMains")) {
                        // 没有提醒配置
                        return null;
                    }
                    JSONArray mains = json.getJSONArray("fdMains");
                    for (int i = 0; i < mains.size(); i++) {
                        JSONObject mainJson = mains.getJSONObject(i);
                        //如果前端是删除状态则直接跳过
                        if(mainJson.getString("deleteFlag") !=null && "1".equals(mainJson.getString("deleteFlag"))){
                            continue;
                        }
                        SysRemindMainForm mainForm = new SysRemindMainForm();
                        mainForm.setFdOrder(i + "");
                        mainForm.setFdId(getString(mainJson, "fdId"));
                        mainForm.setFdTemplateId(form.getFdId());
                        String fdIsEnable = getString(mainJson, "fdIsEnable");
                        if (StringUtil.isNull(fdIsEnable)) {
                            fdIsEnable = "true";
                        }
                        mainForm.setFdIsEnable(fdIsEnable);
                        mainForm.setFdName(getString(mainJson, "fdName"));
                        mainForm.setFdIsFilter(getString(mainJson, "fdIsFilter"));
                        mainForm.setFdConditionId(getString(mainJson, "fdConditionId"));
                        mainForm.setFdConditionName(getString(mainJson, "fdConditionName"));
                        mainForm.setFdNotifyType(getString(mainJson, "fdNotifyType"));
                        mainForm.setFdSenderType(getString(mainJson, "fdSenderType"));
                        mainForm.setFdSenderId(getString(mainJson, "fdSenderId"));
                        mainForm.setFdSenderName(getString(mainJson, "fdSenderName"));
                        mainForm.setFdSubjectId(getString(mainJson, "fdSubjectId"));
                        mainForm.setFdSubjectName(getString(mainJson, "fdSubjectName"));
                        mainForm.setDeleteFlag(getString(mainJson, "deleteFlag"));
                        JSONArray receivers = mainJson.getJSONArray("fdReceivers");
                        if (!mainJson.containsKey("fdReceivers")) {
                            // 没有接收人
                            return null;
                        }
                        for (int j = 0; j < receivers.size(); j++) {
                            JSONObject receiverJson = receivers.getJSONObject(j);
                            SysRemindMainReceiverForm receiverForm = new SysRemindMainReceiverForm();
                            receiverForm.setFdOrder(j + "");
                            receiverForm.setFdId(getString(receiverJson, "fdId"));
                            receiverForm.setFdType(getString(receiverJson, "fdType"));
                            receiverForm.setFdReceiverId(getString(receiverJson, "fdReceiverId"));
                            receiverForm.setFdReceiverName(getString(receiverJson, "fdReceiverName"));
                            receiverForm.setFdReceiverOrgIds(getString(receiverJson, "fdReceiverOrgIds"));
                            receiverForm.setFdReceiverOrgNames(getString(receiverJson, "fdReceiverOrgNames"));
                            receiverForm.setFdRemindId(getString(receiverJson, "fdRemindId"));
                            mainForm.getFdReceivers().add(receiverForm);
                        }
                        JSONArray triggers = mainJson.getJSONArray("fdTriggers");
                        if (!mainJson.containsKey("fdTriggers")) {
                            // 没有触发时间
                            return null;
                        }
                        for (int j = 0; j < triggers.size(); j++) {
                            JSONObject triggerJson = triggers.getJSONObject(j);
                            SysRemindMainTriggerForm triggerForm = new SysRemindMainTriggerForm();
                            triggerForm.setFdOrder(j + "");
                            triggerForm.setFdId(getString(triggerJson, "fdId"));
                            triggerForm.setFdFieldId(getString(triggerJson, "fdFieldId"));
                            triggerForm.setFdFieldName(getString(triggerJson, "fdFieldName"));
                            triggerForm.setFdMode(getString(triggerJson, "fdMode"));
                            triggerForm.setFdDay(getString(triggerJson, "fdDay"));
                            triggerForm.setFdHour(getString(triggerJson, "fdHour"));
                            triggerForm.setFdMinute(getString(triggerJson, "fdMinute"));
                            triggerForm.setFdTime(getString(triggerJson, "fdTime"));
                            triggerForm.setFdRemindId(getString(triggerJson, "fdRemindId"));
                            mainForm.getFdTriggers().add(triggerForm);
                        }
                        tmplForm.getFdMains().add(mainForm);
                    }
                    return tmplForm;
                }
            } catch (Exception e) {
                logger.warn("转换提醒设置失败：", e);
            }
        }
        return null;
    }

    private String getString(JSONObject json, String key) {
        if (json.containsKey(key)) {
            return json.get(key).toString();
        }
        return null;
    }

    private SysRemindTemplate support(IBaseModel model) {
        try {
            Map<String, Object> map = getSysRemind(model);
            if (map != null) {
                Object object = map.get(TEMPLATE_KEY);
                if (object instanceof SysRemindTemplate) {
                    return (SysRemindTemplate) object;
                } else {
                    String fdTemplateId = model.getFdId();
                    String fdTemplateName = ModelUtil.getModelClassName(model);
                    return sysRemindTemplateService.findByTemplateNameAndId(fdTemplateId, fdTemplateName);
                }
            }
        } catch (Exception e) {
            logger.warn("获取提醒设置失败：", e);
        }
        return null;
    }

    @Override
    public void convertFormToModel(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        SysRemindTemplateForm tmplForm = support(form);
        if (tmplForm == null) {
            return;
        }

        tmplForm.setFdId(form.getFdId());
        tmplForm.setFdTemplateId(form.getFdId());
        tmplForm.setFdTemplateName(ModelUtil.getModelClassName(model));
        SysRemindTemplate tmplModel = new SysRemindTemplate();
        tmplModel.setFdId(form.getFdId());
        SysRemindTemplate _tmplModel = (SysRemindTemplate) sysRemindTemplateService.convertFormToModel(tmplForm, tmplModel, requestContext);
        Map<String, Object> sysRemind = (Map<String, Object>) PropertyUtils.getProperty(model, MODULE_KEY);
        sysRemind.put(TEMPLATE_KEY, _tmplModel);
    }

    @Override
    public void convertModelToForm(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        SysRemindTemplate tmplModel = support(model);
        if (tmplModel == null) {
            return;
        }
        SysRemindTemplateForm tmplForm = new SysRemindTemplateForm();
        tmplForm = (SysRemindTemplateForm) sysRemindTemplateService.convertModelToForm(tmplForm, tmplModel, requestContext);
        Map<String, Object> sysRemind = (Map<String, Object>) PropertyUtils.getProperty(form, MODULE_KEY);
        sysRemind.put(TEMPLATE_KEY, tmplForm);
    }

    @Override
    public void cloneModelToForm(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        SysRemindTemplate tmplModel = support(model);
        if (tmplModel == null) {
            return;
        }
        SysRemindTemplateForm tmplForm = new SysRemindTemplateForm();
        tmplForm.setFdId(IDGenerator.generateID());
        tmplForm.setFdTemplateId(tmplModel.getFdTemplateId());
        tmplForm.setFdTemplateName(tmplModel.getFdTemplateName());
        tmplForm.setFdModuleUrl(tmplModel.getFdModuleUrl());
        tmplForm.setFdTemplateProperty(tmplModel.getFdTemplateProperty());
        tmplForm.setFdModelName(tmplModel.getFdModelName());
        tmplForm.setFdModelId(tmplModel.getFdModelId());
        tmplForm.setFdKey(tmplModel.getFdKey());
        List<SysRemindMain> mains = tmplModel.getFdMains();
        if (CollectionUtils.isNotEmpty(mains)) {
            AutoArrayList listMain = new AutoArrayList(SysRemindMainForm.class);
            for (SysRemindMain main : mains) {
                SysRemindMainForm mainForm = new SysRemindMainForm();
                mainForm.setFdId(IDGenerator.generateID());
                mainForm.setCloneId(main.getFdId());
                mainForm.setFdName(main.getFdName());
                mainForm.setFdIsFilter(String.valueOf(main.getFdIsFilter()));
                mainForm.setFdIsEnable(String.valueOf(main.getFdIsEnable()));
                mainForm.setFdConditionId(main.getFdConditionId());
                mainForm.setFdConditionName(main.getFdConditionName());
                mainForm.setFdNotifyType(main.getFdNotifyType());
                mainForm.setFdSenderType(main.getFdSenderType());
                mainForm.setFdSenderId(main.getFdSenderId());
                mainForm.setFdSenderName(main.getFdSenderName());
                mainForm.setFdSubjectId(main.getFdSubjectId());
                mainForm.setFdSubjectName(main.getFdSubjectName());
                mainForm.setFdOrder(String.valueOf(main.getFdOrder()));
                List<SysRemindMainReceiver> receivers = main.getFdReceivers();
                if (CollectionUtils.isNotEmpty(receivers)) {
                    AutoArrayList listReceiver = new AutoArrayList(SysRemindMainReceiverForm.class);
                    for (SysRemindMainReceiver receiver : receivers) {
                        SysRemindMainReceiverForm receiverForm = new SysRemindMainReceiverForm();
                        receiverForm.setFdId(IDGenerator.generateID());
                        receiverForm.setFdType(receiver.getFdType());
                        receiverForm.setFdReceiverId(receiver.getFdReceiverId());
                        receiverForm.setFdReceiverName(receiver.getFdReceiverName());
                        String[] tempReceiver = getIdNames(receiver.getFdReceiverOrgs());
                        receiverForm.setFdReceiverOrgIds(tempReceiver[0]);
                        receiverForm.setFdReceiverOrgNames(tempReceiver[1]);
                        receiverForm.setFdOrder(String.valueOf(receiver.getFdOrder()));
                        listReceiver.add(receiverForm);
                    }
                    mainForm.setFdReceivers(listReceiver);
                }
                List<SysRemindMainTrigger> triggers = main.getFdTriggers();
                if (CollectionUtils.isNotEmpty(triggers)) {
                    AutoArrayList listTrigger = new AutoArrayList(SysRemindMainTriggerForm.class);
                    for (SysRemindMainTrigger trigger : triggers) {
                        SysRemindMainTriggerForm triggerForm = new SysRemindMainTriggerForm();
                        triggerForm.setFdId(IDGenerator.generateID());
                        triggerForm.setFdFieldId(trigger.getFdFieldId());
                        triggerForm.setFdFieldName(trigger.getFdFieldName());
                        triggerForm.setFdMode(trigger.getFdMode());
                        triggerForm.setFdDay(String.valueOf(trigger.getFdDay()));
                        triggerForm.setFdHour(String.valueOf(trigger.getFdHour()));
                        triggerForm.setFdMinute(String.valueOf(trigger.getFdMinute()));
                        triggerForm.setFdTime(trigger.getFdTime());
                        triggerForm.setFdOrder(String.valueOf(trigger.getFdOrder()));
                        listTrigger.add(triggerForm);
                    }
                    mainForm.setFdTriggers(listTrigger);
                }
                listMain.add(mainForm);
            }
            tmplForm.setFdMains(listMain);
        }
        Map<String, Object> sysRemind = (Map<String, Object>) PropertyUtils.getProperty(form, MODULE_KEY);
        sysRemind.put(TEMPLATE_KEY, tmplForm);
    }

    private String[] getIdNames(List<SysOrgElement> receiverOrgs) {
        String[] result = new String[2];
        if (CollectionUtils.isNotEmpty(receiverOrgs)) {
            StringBuffer ids = new StringBuffer();
            StringBuffer names = new StringBuffer();
            for (SysOrgElement org : receiverOrgs) {
                if (ids.length() > 0) {
                    ids.append(";");
                    names.append(";");
                }
                ids.append(org.getFdId());
                names.append(org.getFdName());
            }
            result[0] = ids.toString();
            result[1] = names.toString();
        }
        return result;
    }

    @Override
    public void add(IBaseModel model) throws Exception {
        try {
            // 判断是否增加主文档
            String modelName = ModelUtil.getModelClassName(model);
            SysRemindTemplateRelation relation = sysRemindTemplateRelationService.getRelationByModel(modelName);
            if (relation != null) {
                if (relation.getFdModelName().equals(modelName)
                        && PropertyUtils.isReadable(model, relation.getFdTemplateProperty())) {
                    String templateId = BeanUtils.getProperty(model, relation.getFdTemplateProperty() + ".fdId");
                    SysRemindTemplate tmplModel = sysRemindTemplateService.findByTemplateNameAndId(templateId,
                            relation.getFdTemplateName());
                    if (tmplModel != null) {
                        // 找到提醒中心模板，创建提醒任务
                        sysRemindTemplateService.createRemindTask(tmplModel, model);
                        return;
                    }
                }
            }
        } catch (Exception e) {
            logger.warn("创建提醒任务失败：", e);
            return;
        }

        // 判断是否增加模板
        SysRemindTemplate tmplModel = support(model);
        if (tmplModel == null) {
            return;
        }
        // 提醒模板ID跟随业务模板ID
        tmplModel.setFdId(model.getFdId());
        sysRemindTemplateService.add(tmplModel);
    }

    @Override
    public void update(IBaseModel model) throws Exception {
        SysRemindTemplate tmplModel = support(model);
        if (tmplModel == null) {
            return;
        }
        // 提醒模板ID跟随业务模板ID
        tmplModel.setFdId(model.getFdId());
        if (!org.springframework.util.CollectionUtils.isEmpty(tmplModel.getFdMains())) {
            List<SysRemindMain> collect = tmplModel.getFdMains().stream().filter(item -> {
                if (StringUtil.isNotNull(item.getDeleteFlag()) && "1".equals(item.getDeleteFlag())){
                    return false;
                }else{
                	return true;
				}
            }).collect(Collectors.toList());
            if (org.springframework.util.CollectionUtils.isEmpty(collect)) {
                tmplModel.setFdMains(new ArrayList<>());
            } else {
                tmplModel.setFdMains(collect);
            }
        }
        sysRemindTemplateService.update(tmplModel);
    }

    @Override
    public void delete(IBaseModel model) throws Exception {
        SysRemindTemplate tmplModel = support(model);
        if (tmplModel == null) {
            return;
        }
        sysRemindTemplateService.deleteByTemplateNameAndId(model.getFdId(), ModelUtil.getModelClassName(model));
    }

}
