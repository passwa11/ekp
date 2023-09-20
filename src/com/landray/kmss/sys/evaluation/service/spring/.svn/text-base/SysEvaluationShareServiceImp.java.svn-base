package com.landray.kmss.sys.evaluation.service.spring;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import com.landray.kmss.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.evaluation.model.SysEvaluationShare;
import com.landray.kmss.sys.evaluation.service.ISysEvaluationShareService;
import com.landray.kmss.sys.evaluation.util.SysEvaluationUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class SysEvaluationShareServiceImp extends ExtendDataServiceImp
        implements ISysEvaluationShareService, IXMLDataBean {
    /**
     * 分享给个人
     */
    public static final int SHARE_MODE_PERSON = 1;
    /**
     * 分享给群组
     */
    public static final int SHARE_MODE_GROUP = 3;

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof SysEvaluationShare) {
            SysEvaluationShare sysEvaluationShare = (SysEvaluationShare) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        SysEvaluationShare sysEvaluationShare = new SysEvaluationShare();
        sysEvaluationShare.setFdShareTime(new Date());
        sysEvaluationShare.setDocCreator(UserUtil.getUser());
        SysEvaluationUtil.initModelFromRequest(sysEvaluationShare, requestContext);
        return sysEvaluationShare;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        SysEvaluationShare sysEvaluationShare = (SysEvaluationShare) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

    /**
     * @param requestInfo 数据请求信息
     * @return 数据列表
     */
    @Override
    public List<Map<String, String>> getDataList(RequestContext requestInfo)
            throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        // 只获取激活状态的圈子
        hqlInfo.setWhereBlock("snsGroupMain.fdStatus=:fdStatus");
        hqlInfo.setParameter("fdStatus", "1");

        IBaseService snsGroupMainService = (IBaseService) SpringBeanUtil
                .getBean("snsGroupMainService");

        List<Map<String, String>> rtnList = new ArrayList<>();

        // 圈子模块有可能不存在
        if (null != snsGroupMainService) {
            List result = snsGroupMainService.findList(hqlInfo);
            for (int i = 0; i < result.size(); i++) {
                IBaseModel baseModel = (IBaseModel) result.get(i);
                Map<String, String> node = new HashMap<>();
                node.put("value", baseModel.getFdId());
                node.put("text", ModelUtil.getModelPropertyString(baseModel,
                        "docSubject", null, null));
                rtnList.add(node);
            }
        }

        return rtnList;
    }

    /**
     * 分享
     *
     * @param form
     */
    @Override
    public void saveEvalShare(IExtendForm form, RequestContext requestContext) throws Exception {
        SysEvaluationShare evaluationShare = (SysEvaluationShare) convertFormToModel(form, null, requestContext);

        // 分享到个人
        if (evaluationShare.getFdShareMode() == SHARE_MODE_PERSON) {
            // 发送待办给创建者和推荐对象
            addShareNotify(evaluationShare, SHARE_MODE_PERSON, requestContext);
        } else if (evaluationShare.getFdShareMode() == SHARE_MODE_GROUP) { // 分享到圈子
            // 发表圈子话题
            IBaseService snsTopicMainService = (IBaseService) SpringBeanUtil
                    .getBean("snsTopicMainService");
            // 话题模块有可能不存在
            if (null != snsTopicMainService) {
                IBaseModel baseModel = null;
                try {
                    Class baseModelClass = com.landray.kmss.util.ClassUtils.forName(
                            "com.landray.kmss.sns.topic.model.SnsTopicMain");
                    baseModel = (IBaseModel) baseModelClass.newInstance();

                    // 圈子id
                    Field fdGroupIdField = ReflectionUtils.findField(baseModel.getClass(), "fdGroupId");
                    ReflectionUtils.makeAccessible(fdGroupIdField);
                    ReflectionUtils.setField(fdGroupIdField, baseModel, evaluationShare.getFdGroupId());

                    // 话题标题
                    Field docSubjectField = ReflectionUtils.findField(baseModel.getClass(), "docSubject");
                    ReflectionUtils.makeAccessible(docSubjectField);
                    ReflectionUtils.setField(docSubjectField, baseModel, evaluationShare.getFdTopic());

                    // 内容文本
                    Field fdContentTextField = ReflectionUtils.findField(baseModel.getClass(), "fdContentText");
                    ReflectionUtils.makeAccessible(fdContentTextField);
                    ReflectionUtils.setField(fdContentTextField, baseModel, evaluationShare.getDocSubject());

                    // 内容
                    Field fdContentField = ReflectionUtils.findField(baseModel.getClass(), "docContent");
                    ReflectionUtils.makeAccessible(fdContentField);
                    ReflectionUtils.setField(fdContentField, baseModel, evaluationShare.getDocSubject());

					// 回复数
					Field fdReplyNumField = ReflectionUtils.findField(baseModel.getClass(), "fdReplyNum");
					ReflectionUtils.makeAccessible(fdReplyNumField);
					ReflectionUtils.setField(fdReplyNumField, baseModel, 0L);

                    // 创建人
                    Field docCreatorField = ReflectionUtils.findField(baseModel.getClass(), "docCreator");
                    ReflectionUtils.makeAccessible(docCreatorField);
                    ReflectionUtils.setField(docCreatorField, baseModel,
                            UserUtil.getUser());

                    snsTopicMainService.add(baseModel);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            // 发送待阅给创建者
            addShareNotify(evaluationShare, SHARE_MODE_GROUP, requestContext);
        }
        getBaseDao().add(evaluationShare);
    }

    private void addShareNotify(SysEvaluationShare evaluationShare, int shareMode, RequestContext requestContext) throws Exception {
        String fdTitleName = null;
        String goalPersonIds = evaluationShare.getGoalPersonids();
        String fdShareType = evaluationShare.getFdShareType();
        String fdIsNotify = requestContext.getParameter("fdIsNotify");

        String fdModelId = evaluationShare.getFdModelId();
        String fdModelName = evaluationShare.getFdModelName();
        String serviceBean = SysDataDict.getInstance().getModel(fdModelName).getServiceBean();
        // 主model
        IBaseService service = (IBaseService) SpringBeanUtil.getBean(serviceBean);
        IBaseModel docbase = service.findByPrimaryKey(fdModelId);

        // 分享对象
        ISysOrgCoreService sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil
                .getBean("sysOrgCoreService");
        // List personList = sysOrgCoreService.expandToPerson(orgList);
        // List sendList = new ArrayList();
        // sendList.addAll(orgList);

        ISysNotifyModel mainModel = (ISysNotifyModel) service.findByPrimaryKey(docbase.getFdId(), fdModelName, true);

        // 分享给个人才需要发通知给个人
        if (StringUtil.isNotNull(goalPersonIds) && shareMode == SHARE_MODE_PERSON) {
			List orgList = sysOrgCoreService.findByPrimaryKeys(
					ArrayUtil.toStringArray(goalPersonIds.split(";")));

            NotifyContext notifyContext = getSysNotifyMainCoreService()
                    .getContext("sys-evaluation:sysEvaluationShare.notify");
            notifyContext.setNotifyType(fdShareType);
            notifyContext.setNotifyTarget(orgList);
            HashMap replaceMap = new HashMap();
            replaceMap.put("fdIntroducer", UserUtil.getUser().getFdName());
            fdTitleName = StringUtil.isNull(fdTitleName) ? "docSubject" : fdTitleName;
            replaceMap.put("docSubject", (String) PropertyUtils.getProperty(docbase, fdTitleName));

            getSysNotifyMainCoreService().send(mainModel, notifyContext, replaceMap);
        }

        // 是否通知文档创建者
        if ("true".equals(fdIsNotify)) {
            SysOrgElement creator = (SysOrgElement) PropertyUtils.getProperty(docbase, "docCreator");
            if (creator == null) {
                String docCreatorId = "";
                if (PropertyUtils.isReadable(docbase, "docCreatorId")) {
                    Object value = PropertyUtils.getProperty(docbase, "docCreatorId");
                    if (value != null) {
                        docCreatorId = value.toString();
                        ISysOrgElementService sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
                        SysOrgElement obj = (SysOrgElement)sysOrgElementService.findFirstOne(" fdId='" + docCreatorId + "' ", "");
                        if (obj != null) {
                            creator = obj;
                        }
                    }
                }
            }
            if (creator != null) {
				NotifyContext notifyCreator = null;
				HashMap contentMap = new HashMap();

                List sendToCreator = new ArrayList();
                sendToCreator.add(creator);
				// 分享到圈子
				if (shareMode == SHARE_MODE_GROUP) {
					notifyCreator = getSysNotifyMainCoreService().getContext(
							"sys-evaluation:sysEvaluationShare.notify.group");

					contentMap.put("fdGroupName", evaluationShare.getFdTopic());
				} else {
					notifyCreator = getSysNotifyMainCoreService().getContext(
							"sys-evaluation:sysEvaluationShare.notify.me");
				}

                notifyCreator.setNotifyType(fdShareType);
                notifyCreator.setNotifyTarget(sendToCreator);

                fdTitleName = StringUtil.isNull(fdTitleName) ? "docSubject" : fdTitleName;
                contentMap.put("docSubject", PropertyUtils.getProperty(docbase, fdTitleName));
                contentMap.put("fdIntroducer", UserUtil.getUser().getFdName());
                getSysNotifyMainCoreService().send(mainModel, notifyCreator, contentMap);

            }
        }
    }
}
