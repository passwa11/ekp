package com.landray.kmss.fssc.eas.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.fssc.eas.forms.FsscEasSwitchForm;
import com.landray.kmss.fssc.eas.model.FsscEasSwitch;
import com.landray.kmss.fssc.eas.service.IFsscEasSwitchService;
import com.landray.kmss.fssc.eas.util.FsscEasUtil;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

public class FsscEasSwitchAction extends ExtendAction {

    private IFsscEasSwitchService fsscEasSwitchService;

    @Override
    public IFsscEasSwitchService getServiceImp(HttpServletRequest request) {
        if (fsscEasSwitchService == null) {
            fsscEasSwitchService = (IFsscEasSwitchService) getBean("fsscEasSwitchService");
        }
        return fsscEasSwitchService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscEasSwitch.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FsscEasSwitchForm fsscEasSwitchForm = (FsscEasSwitchForm) super.createNewForm(mapping, form, request, response);
        ((IFsscEasSwitchService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return fsscEasSwitchForm;
    }
    public ActionForward modifySwitch(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-edit", true, getClass());
        KmssMessages messages = new KmssMessages();
        ActionForward forward = new ActionForward();
        try {
            FsscEasSwitchForm fsscEasSwitchForm = (FsscEasSwitchForm) form;
            // 查找是否已经存在开关设置数据，存在则编辑，不存在则新增
            List<FsscEasSwitch> switchList = getServiceImp(request).findList(new HQLInfo());
            if (ArrayUtil.isEmpty(switchList)) {
                fsscEasSwitchForm.setMethod_GET("add");
                forward = super.add(mapping, form, request, response);
            } else {
                fsscEasSwitchForm.setMethod_GET("edit");
                request.setAttribute("fromName", FsscEasUtil.getSwitchValue(null));
                forward.setPath(SysDataDict.getInstance().getModel(FsscEasSwitch.class.getName()).getUrl().replace("=view","=edit").replace("${fdId}", switchList.get(0).getFdId()));
            }
        } catch (Exception e) {
            messages.addError(e);
        }

        TimeCounter.logCurrentTime("Action-edit", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages)
                    .addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return forward;
        }
    }

    /**
     * 将浏览器提交的表单开关数据更新到数据库中。<br>
     * 该操作一般以HTTP的POST方式触发。
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return 执行成功，返回success页面，否则将将错误信息返回edit页面
     * @throws Exception
     */
    public ActionForward updateSwitch(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-save", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
            getServiceImp(request).updateSwitch((IExtendForm) form,
                    new RequestContext(request));
        } catch (Exception e) {
            messages.addError(e);
        }

        TimeCounter.logCurrentTime("Action-save", false, getClass());
        PrintWriter out = response.getWriter();
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).save(request);
            out.print("false");
        } else {
            out.print("true");
        }
        return null;
    }
}
