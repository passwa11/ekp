package com.landray.kmss.fssc.k3.actions;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.fssc.k3.forms.FsscK3SwitchForm;
import com.landray.kmss.fssc.k3.model.FsscK3Switch;
import com.landray.kmss.fssc.k3.service.IFsscK3SwitchService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class FsscK3SwitchAction extends ExtendAction {

    private IFsscK3SwitchService fsscK3SwitchService;

    @Override
    public IFsscK3SwitchService getServiceImp(HttpServletRequest request) {
        if (fsscK3SwitchService == null) {
            fsscK3SwitchService = (IFsscK3SwitchService) getBean("fsscK3SwitchService");
        }
        return fsscK3SwitchService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscK3Switch.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FsscK3SwitchForm fsscK3SwitchForm = (FsscK3SwitchForm) super.createNewForm(mapping, form, request, response);
        ((IFsscK3SwitchService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return fsscK3SwitchForm;
    }
    
    public ActionForward modifySwitch(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-edit", true, getClass());
        KmssMessages messages = new KmssMessages();
        ActionForward forward = new ActionForward();
        try {
        	FsscK3SwitchForm fsscK3SwitchForm = (FsscK3SwitchForm) form;
            // 查找是否已经存在开关设置数据，存在则编辑，不存在则新增
            List<FsscK3Switch> switchList = getServiceImp(request).findList(new HQLInfo());
            if (ArrayUtil.isEmpty(switchList)) {
                fsscK3SwitchForm.setMethod_GET("add");
                forward = super.add(mapping, form, request, response);
            } else {
                fsscK3SwitchForm.setMethod_GET("edit");
                forward.setPath(SysDataDict.getInstance().getModel(FsscK3Switch.class.getName()).getUrl().replace("=view","=edit").replace("${fdId}", switchList.get(0).getFdId()));
            }
            request.setAttribute("fromList", getServiceImp(request).getBaseCostItem()); //获取会计科目的核算属性
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
