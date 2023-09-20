package com.landray.kmss.third.ding.action;

import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.third.ding.model.ThirdDingCardConfig;
import com.landray.kmss.third.ding.forms.ThirdDingCardConfigForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.third.ding.service.IThirdDingCardConfigService;
import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.third.ding.util.DingInteractivecardUtil;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.ding.util.ThirdDingUtil;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONObject;

import java.util.*;

public class ThirdDingCardConfigAction extends ExtendAction {

    private IThirdDingCardConfigService thirdDingCardConfigService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (thirdDingCardConfigService == null) {
            thirdDingCardConfigService = (IThirdDingCardConfigService) getBean("thirdDingCardConfigService");
        }
        return thirdDingCardConfigService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, ThirdDingCardConfig.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        ThirdDingUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.third.ding.model.ThirdDingCardConfig.class);
        ThirdDingUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ThirdDingCardConfigForm thirdDingCardConfigForm = (ThirdDingCardConfigForm) super.createNewForm(mapping, form, request, response);
        ((IThirdDingCardConfigService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return thirdDingCardConfigForm;
    }

    // 判断模版是否存在
    public void checkModel(ActionMapping mapping, ActionForm form,
                           HttpServletRequest request,
                           HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");
        String fdModelName = request.getParameter("fdModelName");
        String fdTemplateId = request.getParameter("fdTemplateId");
        logger.debug("fdModelName:" + fdModelName + "  fdTemplateId:" + fdTemplateId);
        try {
            HQLInfo hqlInfo = new HQLInfo();
            if ("com.landray.kmss.km.review.model.KmReviewMain" .equals(fdModelName)) {
                hqlInfo.setWhereBlock(
                        " fdModelName=:fdModelName and fdTemplateId =:fdTemplateId");
                hqlInfo.setParameter("fdModelName", fdModelName);
                hqlInfo.setParameter("fdTemplateId", fdTemplateId);
            } else {
                hqlInfo.setWhereBlock(
                        " fdModelName=:fdModelName");
                hqlInfo.setParameter("fdModelName", fdModelName);
            }

            List<ThirdDingCardConfig> list = getServiceImp(request).findList(hqlInfo);
            if (list.size() > 0) {
                logger.info("==========已存在该模版======");
                response.getWriter().write("1");
            } else {
                logger.info("==========还不存在,可以新建======");
                response.getWriter().write("0");
            }
        } catch (Exception e) {
            response.getWriter().write("fail");
        }
    }

    // 判断模版是否存在
    public void checkCardId(ActionMapping mapping, ActionForm form,
                           HttpServletRequest request,
                           HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");
        String fdCardId = request.getParameter("fdCardId");
        String fdId = request.getParameter("fdId");
        try {
            HQLInfo hqlInfo = new HQLInfo();
            if(StringUtil.isNotNull(fdId)){
                //编辑
                hqlInfo.setWhereBlock(" fdCardId=:fdCardId and fdId != :fdId");
                hqlInfo.setParameter("fdId", fdId);
            }else{
                //新建
                hqlInfo.setWhereBlock(" fdCardId=:fdCardId");
            }
            hqlInfo.setParameter("fdCardId", fdCardId);

            List<ThirdDingCardConfig> list = getServiceImp(request).findList(hqlInfo);
            if (list.size() > 0) {
                response.getWriter().write("1");
            } else {
                response.getWriter().write("0");
            }
        } catch (Exception e) {
            response.getWriter().write("fail");
        }
    }


    // 测试发送卡片
    public ActionForward testSendCard(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        if (!UserUtil.getKMSSUser().isAdmin()) {
            logger.warn("非超级管理员禁止访问！");
            return getActionForward("failure", mapping, form, request,
                    response);
        }
        response.setCharacterEncoding("UTF-8");
        KmssMessages messages = new KmssMessages();
        String fdModelName = request.getParameter("fdModelName");
        String fdModelId = request.getParameter("fdModelId");
        String title = request.getParameter("title");
        String receivers = request.getParameter("receivers");
        String privateData = request.getParameter("privateData");
        String publicData = request.getParameter("publicData");
        String isUpdate = request.getParameter("isUpdate");
        System.out.println("isUpdate:"+isUpdate);

        if(StringUtil.isNull(fdModelName)){
            messages.addError(new RuntimeException("模块不能为空"));
        }
        if(StringUtil.isNull(fdModelId)){
            messages.addError(new RuntimeException("主文档ID不能为空"));
        }
        if(StringUtil.isNull(title)){
            messages.addError(new RuntimeException("标题不能为空"));
        }
        if(StringUtil.isNull(isUpdate)&&StringUtil.isNull(receivers)){
            messages.addError(new RuntimeException("接收人不能为空"));
        }
        System.out.println(fdModelName);
        System.out.println(fdModelId);
        SysDictModel model = SysDataDict.getInstance().getModel(fdModelName);
        IBaseService baseService = (IBaseService)SpringBeanUtil.getBean(model.getServiceBean());
        IBaseModel model2 = baseService.findByPrimaryKey(fdModelId, null, true);
        BaseModel baseModel = (BaseModel)model2;

        if (!messages.hasError()){
            if("true".equals(isUpdate)){
                DingInteractivecardUtil.updateCard(baseModel,title,StringUtil.isNull(privateData)?null: JSONObject.fromObject(privateData),
                        StringUtil.isNull(publicData)?null: JSONObject.fromObject(publicData),null);
            }else {
                DingInteractivecardUtil.sendCardByModel(baseModel,title,StringUtil.isNull(privateData)?null: JSONObject.fromObject(privateData),
                        StringUtil.isNull(publicData)?null: JSONObject.fromObject(publicData),java.util.Arrays.asList(receivers.split(";")),"lbpm");
            }

        }
        KmssReturnPage.getInstance(request).addMessages(messages).addButton(
                KmssReturnPage.BUTTON_CLOSE).save(request);
        TimeCounter.logCurrentTime("Action-delete", false, getClass());
        if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
    }
}
