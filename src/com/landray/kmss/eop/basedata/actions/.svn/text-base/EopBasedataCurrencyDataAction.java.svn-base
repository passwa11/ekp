package com.landray.kmss.eop.basedata.actions;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.service.IEopBasedataCurrencyService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wangwh
 * @description:货币数据action
 * @date 2021/5/7
 */
public class EopBasedataCurrencyDataAction extends BaseAction {

    private IEopBasedataCurrencyService eopBasedataCurrencyService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataCurrencyService == null) {
            eopBasedataCurrencyService = (IEopBasedataCurrencyService) getBean("eopBasedataCurrencyService");
        }
        return eopBasedataCurrencyService;
    }

    public ActionForward fdCurrency(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("fdCurrency", true, getClass());
        KmssMessages messages = new KmssMessages();
        try {
            String s_pageno = request.getParameter("pageno");
            String s_rowsize = request.getParameter("rowsize");
            String keyWord = request.getParameter("q._keyword");
            int pageno = 0;
            int rowsize = SysConfigParameters.getRowSize();
            if (s_pageno != null && s_pageno.length() > 0) {
                pageno = Integer.parseInt(s_pageno);
            }
            if (s_rowsize != null && s_rowsize.length() > 0) {
                rowsize = Integer.parseInt(s_rowsize);
            }
            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setPageNo(pageno);
            hqlInfo.setRowSize(rowsize);
            if (StringUtil.isNotNull(keyWord)) {
                String where = "";
                where += "(eopBasedataCurrency.fdName like :fdName";
                hqlInfo.setParameter("fdName", "%" + keyWord + "%");
                where += " or eopBasedataCurrency.fdEnglishName like :fdNameEn";
                hqlInfo.setParameter("fdNameEn", "%" + keyWord + "%");
                where += " or eopBasedataCurrency.fdSymbol like :fdSymbol";
                hqlInfo.setParameter("fdSymbol", "%" + keyWord + "%");
                where += " or eopBasedataCurrency.fdCountry like :fdCountry";
                hqlInfo.setParameter("fdCountry", "%" + keyWord + "%");
                where += ")";
                hqlInfo.setWhereBlock(where);
            }
            String fdCompanyId = request.getParameter("fdCompanyId");
            EopBasedataCompany comp = (EopBasedataCompany) getServiceImp(request).findByPrimaryKey(fdCompanyId, EopBasedataCompany.class, true);
            String existCurrency = request.getParameter("existCurrency");
            String fdPayCurrencyType = request.getParameter("fdPayCurrencyType");
            String fdCurrencyId = request.getParameter("fdCurrencyId");
            List<String> existCurrencys = new ArrayList();
            if(comp!=null){
            	existCurrencys.add(comp.getFdAccountCurrency().getFdId());
            }
            if(StringUtil.isNotNull(fdCurrencyId)){
            	existCurrencys.add(fdCurrencyId);
            }
            if(StringUtil.isNotNull(existCurrency)){
            	List<String> clist = Arrays.asList(existCurrency.split(";"));
            	for(String c:clist){
            		if(!existCurrencys.contains(c)){
            			existCurrencys.add(c);
            		}
            	}
			}
            if(existCurrencys.size()>1||StringUtil.isNotNull(fdPayCurrencyType)){
				hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "eopBasedataCurrency.fdId in(:ids)"));
				hqlInfo.setParameter("ids",existCurrencys);
			}
            
			String fdModelCurrencyIds = request.getParameter("fdModelCurrencyIds");
            if(StringUtil.isNotNull(fdModelCurrencyIds)){
                String[] currencyIds = fdModelCurrencyIds.substring(0,fdModelCurrencyIds.length()-1).split(";");
                StringBuilder currencyIdWhereBu = new StringBuilder();
                currencyIdWhereBu.append(" eopBasedataCurrency.fdId in (");
                for(int i=0;i<currencyIds.length;i++){
                    currencyIdWhereBu.append("'");
                    currencyIdWhereBu.append(currencyIds[i]);
                    currencyIdWhereBu.append("',");
                }
                String currencyIdWhere = currencyIdWhereBu.toString().substring(0,currencyIdWhereBu.toString().length()-1);
                hqlInfo.setWhereBlock(
                        StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", currencyIdWhere+= ")"));
            }
            
            String fdPaymentCurrencyId = request.getParameter("fdPaymentCurrencyId"); 
            if(StringUtil.isNotNull(fdPaymentCurrencyId)){
				hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "eopBasedataCurrency.fdId in(:ids)"));
				hqlInfo.setParameter("ids",fdPaymentCurrencyId);
			}

            hqlInfo.setWhereBlock(
					StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", " eopBasedataCurrency.fdStatus=:fdStatus "));
			String valid = request.getParameter("valid");
			if(StringUtil.isNotNull(valid)){
				hqlInfo.setParameter("fdStatus",1);
			}else{
				hqlInfo.setParameter("fdStatus",0);
			}
			hqlInfo.setOrderBy("eopBasedataCurrency.fdOrder");
            //HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataCurrency.class);
            Page page = getServiceImp(request).findPage(hqlInfo);
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("fdCurrency", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("fdCurrency");
        }
    }
}
