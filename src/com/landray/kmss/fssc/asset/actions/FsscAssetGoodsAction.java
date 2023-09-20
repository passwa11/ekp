package com.landray.kmss.fssc.asset.actions;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import com.landray.kmss.fssc.asset.model.FsscAssetGoods;
import com.landray.kmss.fssc.asset.forms.FsscAssetGoodsForm;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.fssc.asset.service.IFsscAssetGoodsService;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.web.action.ActionForm;
import javax.servlet.http.HttpServletResponse;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.fssc.asset.util.FsscAssetUtil;
import com.landray.kmss.fssc.expense.service.IFsscExpenseMainService;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

/**
  * 资产物资 Action
  */
public class FsscAssetGoodsAction extends ExtendAction {

    private IFsscAssetGoodsService fsscAssetGoodsService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (fsscAssetGoodsService == null) {
            fsscAssetGoodsService = (IFsscAssetGoodsService) getBean("fsscAssetGoodsService");
        }
        return fsscAssetGoodsService;
    }

    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscAssetGoods.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.fssc.asset.util.FsscAssetUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.fssc.asset.model.FsscAssetGoods.class);
        com.landray.kmss.fssc.asset.util.FsscAssetUtil.buildHqlInfoModel(hqlInfo, request);
    }

    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FsscAssetGoodsForm fsscAssetGoodsForm = (FsscAssetGoodsForm) super.createNewForm(mapping, form, request, response);
        ((IFsscAssetGoodsService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return fsscAssetGoodsForm;
    }
    
    public ActionForm checkGoodsNum(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	try {
			JSONObject rtn = ((IFsscAssetGoodsService)getServiceImp(request)).checkGoodsNum(request);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(String.valueOf(rtn));
		} catch (Exception e) {
			response.getWriter().write("");
			e.printStackTrace();
		}
		return null;
    }
}
