package com.landray.kmss.eop.basedata.actions;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetScheme;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class EopBasedataCompanyDataAction extends BaseAction {

    private IEopBasedataCompanyService eopBasedataCompanyService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataCompanyService == null) {
            eopBasedataCompanyService = (IEopBasedataCompanyService) getBean("eopBasedataCompanyService");
        }
        return eopBasedataCompanyService;
    }

    private ISysOrgCoreService sysOrgCoreService;

    public ISysOrgCoreService getSysOrgCoreService() {
        if (sysOrgCoreService == null) {
            sysOrgCoreService = (ISysOrgCoreService) getBean("sysOrgCoreService");
        }
        return sysOrgCoreService;
    }

    private ISysOrgElementService sysOrgElementService;

    public ISysOrgElementService getSysOrgElementService() {
        if (sysOrgElementService == null) {
            sysOrgElementService = (ISysOrgElementService) getBean("sysOrgElementService");
        }
        return sysOrgElementService;
    }

    public ActionForward fdCompany(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("fdCompany", true, getClass());
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
            String where = hqlInfo.getWhereBlock();
            where = StringUtil.linkString(where, " and ", "eopBasedataCompany.fdIsAvailable=:fdIsAvaliable");
            if (StringUtil.isNotNull(keyWord)) {
            	where = StringUtil.linkString(where, " and ", "(eopBasedataCompany.fdName like :fdName or eopBasedataCompany.fdCode like :fdCode)");
                hqlInfo.setParameter("fdName", "%" + keyWord + "%");
                hqlInfo.setParameter("fdCode", "%" + keyWord + "%");
            }
            String valid = request.getParameter("valid");
            if(StringUtil.isNull(valid)){
            	hqlInfo.setParameter("fdIsAvaliable",true);
            }else{
            	hqlInfo.setParameter("fdIsAvaliable",Boolean.valueOf(valid));
            }
            //如果带有人员参数，需要根据人员筛选对应公司
            String fdPersonId =request.getParameter("fdPersonId"); 
            if(StringUtil.isNull(fdPersonId)) {
            	fdPersonId = UserUtil.getUser().getFdId();
            }
            if(StringUtil.isNotNull(fdPersonId)&&!UserUtil.checkRole("SYSROLE_ADMIN")){
            	List<EopBasedataCompany> list = ((IEopBasedataCompanyService)getServiceImp(request)).findCompanyByUserId(fdPersonId);
            	if(ArrayUtil.isEmpty(list)){
            		where = StringUtil.linkString(where, " and ", "1=2");
            	}else{
            		where = StringUtil.linkString(where, " and ", "eopBasedataCompany.fdId in(:ids)");
            		List<String> ids = new ArrayList<String>();
            		for(EopBasedataCompany comp:list){
            			ids.add(comp.getFdId());
            		}
            		hqlInfo.setParameter("ids",ids);
            	}
            }
            //如果带有模块参数，需要根据模块开关帐设置筛选对应公司
            boolean auth=true;  //默认有权限
            List<EopBasedataCompany> comList = new ArrayList<EopBasedataCompany>();
            String fdModelName = request.getParameter("fdModelName");   //若是需要判断公司是否开关账，关账不让选择，则传对应的modelName
            if(StringUtil.isNotNull(fdModelName)){
            	JSONObject authObj = EopBasedataFsscUtil.getAccountAuth(fdModelName,fdPersonId);
        		auth=authObj.optBoolean("auth", Boolean.TRUE);
        		String fdCompanyIds=authObj.optString("fdCompanyIds", "");
        		if(StringUtil.isNotNull(fdCompanyIds)){
        			comList = ((IEopBasedataCompanyService)getServiceImp(request)).findByPrimaryKeys(fdCompanyIds.split(";"));
        		}
            }
            if(auth) {	//开账期间，允许新建
  				if(!ArrayUtil.isEmpty(comList)){	//开关帐公司不为空，取交集
  					where=StringUtil.linkString(where, " and ", HQLUtil.buildLogicIN("eopBasedataCompany.fdId", 
          					ArrayUtil.convertArrayToList(ArrayUtil.joinProperty(comList, "fdId", ";")[0].split(";"))));
  				}
  			} else {	//关帐期间，不允许新建
  				if(!ArrayUtil.isEmpty(comList)){	//开关帐公司不为空
  					where = StringUtil.linkString(where, " and ", EopBasedataFsscUtil.buildLogicNotIN("eopBasedataCompany.fdId", ArrayUtil.convertArrayToList(ArrayUtil.joinProperty(comList, "fdId", ";")[0].split(";"))));
  				} else {	//开关帐公司为空
  					where = StringUtil.linkString(where, " and ", "1=2");
  				}
  			}
            if(("base_auth".equals(request.getParameter("type"))&&!UserUtil.checkRole("ROLE_EOPBASEDATA_COMPANY"))
            		||"loanSearch".equals(request.getParameter("type"))&&!UserUtil.checkRole("ROLE_FSSCLOAN_SEARCHLIST")){
        		//基础数据模块，没有公司权限，只能看到有权限看到的公司
        		List<EopBasedataCompany> companyList=((IEopBasedataCompanyService)getServiceImp(request)).findCompanyByUserId(fdPersonId);
        		if(!ArrayUtil.isEmpty(companyList)){
        			where=StringUtil.linkString(where, " and ", HQLUtil.buildLogicIN("eopBasedataCompany.fdId", 
        					ArrayUtil.convertArrayToList(ArrayUtil.joinProperty(companyList, "fdId", ";")[0].split(";"))));
        		}
			}
			String choosedIds = request.getParameter("choosedIds");
			if (StringUtil.isNotNull(choosedIds)) {
				where = StringUtil.linkString(where, " and ", "eopBasedataCompany.fdId not in (:choosedIds)");
				hqlInfo.setParameter("choosedIds", ArrayUtil.convertArrayToList(choosedIds.split(";")));
			}
			//收单柜品牌
            String fdCabinetType = request.getParameter("fdCabinetType");
            if (StringUtil.isNotNull(fdCabinetType)) {
                where = StringUtil.linkString(where, " and ", "eopBasedataCompany.fdCabinetType =:fdCabinetType");
                hqlInfo.setParameter("fdCabinetType", fdCabinetType);
            }
            hqlInfo.setWhereBlock(where);
            HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataCompany.class);
            Page page = getServiceImp(request).findPage(hqlInfo);
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("fdCompany", false, getClass());
		String forward = request.getParameter("forward");
		if (StringUtil.isNull(forward)) {
			forward = "fdCompany";
		}
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
			return mapping.findForward(forward);
        }
    }
    
    public ActionForward getCompanyByPerson(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TimeCounter.logCurrentTime("fdCompany", true, getClass());
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
            String where = hqlInfo.getWhereBlock();
            where = StringUtil.linkString(where, " and ", "eopBasedataCompany.fdIsAvailable=:fdIsAvaliable");
            if (StringUtil.isNotNull(keyWord)) {
            	where = StringUtil.linkString(where, " and ", "(eopBasedataCompany.fdName like :fdName or eopBasedataCompany.fdCode like :fdCode)");
                hqlInfo.setParameter("fdName", "%" + keyWord + "%");
                hqlInfo.setParameter("fdCode", "%" + keyWord + "%");
            }
            String valid = request.getParameter("valid");
            if(StringUtil.isNull(valid)){
            	hqlInfo.setParameter("fdIsAvaliable",true);
            }else{
            	hqlInfo.setParameter("fdIsAvaliable",Boolean.valueOf(valid));
            }
            String fdPersonId = request.getParameter("fdPersonId");
            SysOrgPerson person = null;
            if(StringUtil.isNotNull(fdPersonId)){
                person = (SysOrgPerson) getServiceImp(request).findByPrimaryKey(fdPersonId, SysOrgPerson.class, true);
            }
            if(person == null){
                person = UserUtil.getUser();
            }
            List<String> authIds = getSysOrgCoreService().getOrgsUserAuthInfo(person).getAuthOrgIds();//人员相关组织架构信息
            HQLInfo hql = new HQLInfo();
            //查找对应的机构信息，若是机构还有上级，需将上级加入到列表，因为记账公司可能只配置了最顶级机构
            hql.setWhereBlock(HQLUtil.buildLogicIN("sysOrgElement.fdId", authIds)+" and sysOrgElement.fdOrgType=:fdOrgType");
            hql.setParameter("fdOrgType", SysOrgConstant.ORG_TYPE_ORG);
            List<SysOrgElement> orgList= getSysOrgElementService().findList(hql);
            if(!ArrayUtil.isEmpty(orgList)){
                SysOrgElement parent=orgList.get(0).getFdParent();
                while(parent!=null){
                    authIds.add(parent.getFdId());
                    parent=parent.getFdParent();
                }
            }
            if(StringUtil.isNotNull(person.getFdHierarchyId())&&!UserUtil.checkRole("SYSROLE_ADMIN")){
                where = StringUtil.linkString(where, " and ", HQLUtil.buildLogicIN("org.fdId", authIds));
                hqlInfo.setJoinBlock("left join  eopBasedataCompany.fdEkpOrg org");
            }
            String type=request.getParameter("type");
            if("transfer".equals(type)&&!UserUtil.getKMSSUser().isAdmin()){
            	String transferWhere="(eopBasedataCompany.fdFinancialManager.fdId=:fdFinancialManagerId ";
            	if(UserUtil.checkRole("ROLE_FSSCBUDGET_TRANSFER")){
            		List<EopBasedataCompany> compantList=((IEopBasedataCompanyService)getServiceImp(request)).findCompanyByUserId(person.getFdId());
            		if(!ArrayUtil.isEmpty(compantList)){
            			transferWhere+=" or  "+HQLUtil.buildLogicIN("eopBasedataCompany.fdId", ArrayUtil.convertArrayToList(ArrayUtil.joinProperty(compantList, "fdId", ";")[0].split(";")));
            		}
            	}
            	transferWhere+=")";
            	 where = StringUtil.linkString(where, " and ", transferWhere);
                 hqlInfo.setParameter("fdFinancialManagerId", person.getFdId());
            }else if("byScheme".equals(type)){
            	String fdBudgetSchemeId=request.getParameter("fdBudgetSchemeId");
            	if(StringUtil.isNotNull(fdBudgetSchemeId)){
            		EopBasedataBudgetScheme scheme=(EopBasedataBudgetScheme) getServiceImp(request).findByPrimaryKey(fdBudgetSchemeId, EopBasedataBudgetScheme.class, true);
            		List<EopBasedataCompany> comList=scheme.getFdCompanys();
            		if(scheme!=null&&!ArrayUtil.isEmpty(comList)){
            			where = StringUtil.linkString(where, " and ", HQLUtil.buildLogicIN("eopBasedataCompany.fdId", 
            					ArrayUtil.convertArrayToList(ArrayUtil.joinProperty(comList, "fdId", ";")[0].split(";"))));
            		}
            	}
            }
            hqlInfo.setWhereBlock(where);
            hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
            HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataCompany.class);
            Page page = getServiceImp(request).findPage(hqlInfo);
            request.setAttribute("queryPage", page);
        } catch (Exception e) {
            messages.addError(e);
        }
        TimeCounter.logCurrentTime("fdCompany", false, getClass());
        if (messages.hasError()) {
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("fdCompany");
        }
    }
}
