package com.landray.kmss.eop.basedata.actions;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.eop.basedata.forms.EopBasedataMateCateForm;
import com.landray.kmss.eop.basedata.model.EopBasedataMateCate;
import com.landray.kmss.eop.basedata.service.IEopBasedataMateCateService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import net.sf.json.JSONObject;
import org.apache.commons.lang.ArrayUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wangwh
 * @description:物料类别action
 * @date 2021/5/7
 */
public class EopBasedataMateCateAction extends ExtendAction {

    private IEopBasedataMateCateService eopBasedataMateCateService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataMateCateService == null) {
            eopBasedataMateCateService = (IEopBasedataMateCateService) getBean("eopBasedataMateCateService");
        }
        return eopBasedataMateCateService;
    }

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataMateCate.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        EopBasedataUtil.buildHqlInfoDate(hqlInfo, request, EopBasedataMateCate.class);
        EopBasedataUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EopBasedataMateCateForm eopBasedataMateCateForm = (EopBasedataMateCateForm) super.createNewForm(mapping, form, request, response);
        ((IEopBasedataMateCateService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
//新建时如果存在父类别名称则注入
		String parentId = request.getParameter("nowId"); //当前勾选作为父类
		EopBasedataMateCate eopBasedataMateCateParent = null;
		if(StringUtil.isNotNull(parentId)) {
			eopBasedataMateCateParent = (EopBasedataMateCate)((IEopBasedataMateCateService)getServiceImp(request)).findByPrimaryKey(parentId);
		}
		if(null != eopBasedataMateCateParent) {
			eopBasedataMateCateForm.setFdParentId(parentId);
			eopBasedataMateCateForm.setFdParentName(eopBasedataMateCateParent.getFdName());
		}
        return eopBasedataMateCateForm;
    }
    
	/**
     * 判断除本条数据外，物料编码或名称是否已存在
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * 
     * @throws Exception
     */
    public void isSetup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	String fdId = request.getParameter("fdId");
    	String fdName = request.getParameter("fdName");
    	String fdCode = request.getParameter("fdCode");
		List<EopBasedataMateCate> eopBasedataMateCateList = eopBasedataMateCateService.findByNameIgnoreSelf(fdName,fdId);
        List<EopBasedataMateCate> eopBasedataMateCateList2 = null;
		if(!fdCode.isEmpty()){
		    eopBasedataMateCateList2 = eopBasedataMateCateService.findByCodeIgnoreSelf(fdCode,fdId);
        }
		boolean isSetup = false;
		if((null != eopBasedataMateCateList && eopBasedataMateCateList.size()>0) || (null != eopBasedataMateCateList2 && eopBasedataMateCateList2.size()>0)) {
			isSetup = true;
		}
		JSONObject json = new JSONObject();
        json.put("isSetup", isSetup);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().print(json);
        response.getWriter().flush();
        response.getWriter().close();
    }

    @Override
    public ActionForward deleteall(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("EopBasedataMateCateAction-deleteall", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
            String[] ids = request.getParameterValues("List_Selected");
            if (ISysAuthConstant.IS_AREA_ENABLED) {
                String queryString = "method=delete&fdId=${id}";
                String fdModelName = request.getParameter("fdModelName");
                if(fdModelName != null && !"".equals(fdModelName))
                {
                    queryString += "&fdModelName=" + fdModelName;
                }
                String[] authIds = SysAuthAreaUtils.removeNoAuthIds(ids,
                        request, queryString);
                int noAuthIdNum = ids.length - authIds.length;
                if (noAuthIdNum > 0) {
                    messages.addMsg(new KmssMessage(
                            "sys-authorization:area.batch.operation.info",
                            noAuthIdNum));
                }

                if (!ArrayUtils.isEmpty(authIds)) {
                    getServiceImp(request).delete(authIds);
                }
            } else if (ids != null) {
                getServiceImp(request).delete(ids);
            }

        } catch (Exception e) {
            if (e instanceof DataIntegrityViolationException || e instanceof ConstraintViolationException) {
                messages.addError(new KmssMessage("eop-basedata:error.delete.1"),e);
            }else {
                messages.addError(new KmssMessage("eop-basedata:error.delete.0"),e);
            }
        }
        KmssReturnPage.getInstance(request).addMessages(messages).addButton(
                KmssReturnPage.BUTTON_RETURN).save(request);
        TimeCounter.logCurrentTime("EopBasedataMateCateAction-deleteall", false, getClass());
        if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        }else {
            return getActionForward("success", mapping, form, request, response);
        }
    }

    @Override
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("EopBasedataMateCateAction-delete", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
            String id = request.getParameter("fdId");
            if (StringUtil.isNull(id)) {
                messages.addError(new NoRecordException());
            } else {
                getServiceImp(request).delete(id);
            }
        }catch (DataIntegrityViolationException e) {
            messages.addError(new KmssMessage("eop-basedata:error.delete.2"));
        } catch (Exception e) {
            messages.addError(e);
        }

        KmssReturnPage.getInstance(request).addMessages(messages).save(request);
        TimeCounter.logCurrentTime("EopBasedataMateCateAction-delete", false, getClass());
        if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
    }
    
	@Override
    public ActionForward update(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EopBasedataMateCateForm eopBasedataMateCateForm = (EopBasedataMateCateForm)form;
		((IEopBasedataMateCateService)getServiceImp(request)).updatePre(eopBasedataMateCateForm);
		return super.update(mapping, form, request, response);
	}
	/**
     * 根据物料编码查采购需求，查是否存在该物料类别
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * 
     * @throws Exception
     */
    public void isExist(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean isExist = false;
    	String nowId = request.getParameter("nowId");
    	IBaseModel iBaseModel = ((IEopBasedataMateCateService)getServiceImp(request)).findByPrimaryKey(nowId, null, true);
    	if(null != iBaseModel && iBaseModel instanceof EopBasedataMateCate) {
    		EopBasedataMateCate eopBasedataMateCate = (EopBasedataMateCate)iBaseModel;
            if(null != eopBasedataMateCate && StringUtil.isNotNull(eopBasedataMateCate.getFdName())) {
            	isExist = true;
            }
    	}
        JSONObject json = new JSONObject();
        json.put("isExist", isExist);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().print(json);
        response.getWriter().flush();
        response.getWriter().close();
    }
}
