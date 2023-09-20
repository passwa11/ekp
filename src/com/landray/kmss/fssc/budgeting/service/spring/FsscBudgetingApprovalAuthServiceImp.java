package com.landray.kmss.fssc.budgeting.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetItem;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.fssc.budgeting.forms.FsscBudgetingApprovalAuthForm;
import com.landray.kmss.fssc.budgeting.model.FsscBudgetingApprovalAuth;
import com.landray.kmss.fssc.budgeting.service.IFsscBudgetingApprovalAuthService;
import com.landray.kmss.fssc.budgeting.util.FsscBudgetingUtil;
import com.landray.kmss.fssc.common.util.ExcelUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.upload.FormFile;

public class FsscBudgetingApprovalAuthServiceImp extends ExtendDataServiceImp implements IFsscBudgetingApprovalAuthService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof FsscBudgetingApprovalAuth) {
            FsscBudgetingApprovalAuth fsscBudgetingApprovalAuth = (FsscBudgetingApprovalAuth) model;
            fsscBudgetingApprovalAuth.setDocAlterTime(new Date());
            fsscBudgetingApprovalAuth.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        FsscBudgetingApprovalAuth fsscBudgetingApprovalAuth = new FsscBudgetingApprovalAuth();
        fsscBudgetingApprovalAuth.setFdIsAvailable(Boolean.valueOf("true"));
        fsscBudgetingApprovalAuth.setDocCreateTime(new Date());
        fsscBudgetingApprovalAuth.setDocAlterTime(new Date());
        fsscBudgetingApprovalAuth.setDocCreator(UserUtil.getUser());
        fsscBudgetingApprovalAuth.setDocAlteror(UserUtil.getUser());
        FsscBudgetingUtil.initModelFromRequest(fsscBudgetingApprovalAuth, requestContext);
        return fsscBudgetingApprovalAuth;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        FsscBudgetingApprovalAuth fsscBudgetingApprovalAuth = (FsscBudgetingApprovalAuth) model;
    }

    @Override
    public List<FsscBudgetingApprovalAuth> findByFdCostCenterList(EopBasedataCostCenter fdCostCenterList) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscBudgetingApprovalAuth.fdCostCenterList.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCostCenterList.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<FsscBudgetingApprovalAuth> findByFdBudgetItemList(EopBasedataBudgetItem fdBudgetItemList) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscBudgetingApprovalAuth.fdBudgetItemList.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdBudgetItemList.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<FsscBudgetingApprovalAuth> findByFdProjectList(EopBasedataProject fdProjectList) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscBudgetingApprovalAuth.fdProjectList.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdProjectList.getFdId());
        return this.findList(hqlInfo);
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
	public void saveImport(FsscBudgetingApprovalAuthForm mainForm,HttpServletRequest request) throws Exception {
		List<List<String>> validateInfoList=new ArrayList<List<String>>();  //全部预算校验信息
		Workbook wb=null;
		FormFile file = mainForm.getFdFile();
		try {
			wb = WorkbookFactory.create(file.getInputStream());
			Sheet sheet = wb.getSheetAt(0);
			List<FsscBudgetingApprovalAuth> auhList=new ArrayList<FsscBudgetingApprovalAuth>();  //缓存excel数据对象
			String where="";
			// 取到工作表里面的数据
			for (int i = 1,shLen=sheet.getLastRowNum(); i <=shLen ; i++) {
				List<String> validateInfo=new ArrayList<String>();  //一行的校验信息
				Row row = sheet.getRow(i);
				// 判断是否空行
				if (ExcelUtil.isBlankRow(row)) {
					validateInfo.add(ResourceUtil.getString("message.import.excel.blank", "fssc-budgeting").replaceAll("{0}", String.valueOf(i)));
					continue;
				}
				//判断同一行成本中心和部门不能同时为空
				String fdDeptNo=ExcelUtil.getCellValue(row.getCell(3));  //部门编码
				String fdCode=ExcelUtil.getCellValue(row.getCell(5));  //成本中心编码
				if(StringUtil.isNull(fdCode)&&StringUtil.isNull(fdDeptNo)){
					validateInfo.add(ResourceUtil.getString("message.import.excel.deptAndCenter.isNull", "fssc-budgeting"));
				}
				FsscBudgetingApprovalAuth auth=new FsscBudgetingApprovalAuth();
				auth.setFdIsAvailable(true);
				String docSubject=ExcelUtil.getCellValue(row.getCell(0));
				if(StringUtil.isNull(docSubject)){
					validateInfo.add(ResourceUtil.getString("message.import.excel.docSubject.isNull", "fssc-budgeting"));
				}else{
					auth.setFdName(docSubject);//名称
				}
				String fdNos=ExcelUtil.getCellValue(row.getCell(1));  //人员编号
				if(StringUtil.isNotNull(fdNos)){
					String[] fdNoArr=fdNos.split(";");
					where="select t from SysOrgPerson t where "+HQLUtil.buildLogicIN("t.fdNo", ArrayUtil.convertArrayToList(fdNoArr));
					where+=" and t.fdIsAvailable=:fdIsAvailable";
					List<SysOrgPerson> personList=this.getBaseDao().getHibernateSession().createQuery(where)
					.setParameter("fdIsAvailable", true).list();
					auth.setFdPersonList(personList);
				}else{
					validateInfo.add(ResourceUtil.getString("message.import.excel.person.isNull", "fssc-budgeting"));
				}
				auth.setFdDesc(ExcelUtil.getCellValue(row.getCell(2)));  //说明
				fdNos=ExcelUtil.getCellValue(row.getCell(3));  //部门编号
				if(StringUtil.isNotNull(fdNos)){
					String[] fdNoArr=fdNos.split(";");
					where="select t from SysOrgElement t where "+HQLUtil.buildLogicIN("t.fdNo", ArrayUtil.convertArrayToList(fdNoArr));
					where+=" and t.fdIsAvailable=:fdIsAvailable and (t.fdOrgType=:dept or t.fdOrgType=:org)";
					List<SysOrgElement> deptList=this.getBaseDao().getHibernateSession().createQuery(where)
					.setParameter("fdIsAvailable", true)
					.setParameter("dept", SysOrgConstant.ORG_TYPE_DEPT)
					.setParameter("org", SysOrgConstant.ORG_TYPE_ORG).list();
					auth.setFdOrgList(deptList);
				}
				fdNos=ExcelUtil.getCellValue(row.getCell(4));  //公司编号
				if(StringUtil.isNotNull(fdNos)){
					String[] fdNoArr=fdNos.split(";");
					where="select t from EopBasedataCompany t where "+HQLUtil.buildLogicIN("t.fdCode", ArrayUtil.convertArrayToList(fdNoArr));
					where+=" and t.fdIsAvailable=:fdIsAvailable";
					List<EopBasedataCompany> comList=this.getBaseDao().getHibernateSession().createQuery(where)
							.setParameter("fdIsAvailable", true).list();
					auth.setFdCompanyList(comList);
				}
				fdNos=ExcelUtil.getCellValue(row.getCell(5));  //成本中心编号
				if(StringUtil.isNotNull(fdNos)){
					String[] fdNoArr=fdNos.split(";");
					where="select t from EopBasedataCostCenter t where "+HQLUtil.buildLogicIN("t.fdCode", ArrayUtil.convertArrayToList(fdNoArr));
					where+=" and t.fdIsAvailable=:fdIsAvailable";
					List<EopBasedataCostCenter> centerList=this.getBaseDao().getHibernateSession().createQuery(where)
					.setParameter("fdIsAvailable", true).list();
					auth.setFdCostCenterList(centerList);
				}
				fdNos=ExcelUtil.getCellValue(row.getCell(6));  //预算科目编码
				if(StringUtil.isNotNull(fdNos)){
					String[] fdNoArr=fdNos.split(";");
					where="select t from EopBasedataBudgetItem t where "+HQLUtil.buildLogicIN("t.fdCode", ArrayUtil.convertArrayToList(fdNoArr));
					where+=" and t.fdIsAvailable=:fdIsAvailable";
					List<EopBasedataBudgetItem> budgetItemList=this.getBaseDao().getHibernateSession().createQuery(where)
					.setParameter("fdIsAvailable", true).list();
					auth.setFdBudgetItemList(budgetItemList);
				}
				fdNos=ExcelUtil.getCellValue(row.getCell(7));  //项目编码
				if(StringUtil.isNotNull(fdNos)){
					String[] fdNoArr=fdNos.split(";");
					where="select t from EopBasedataProject t where "+HQLUtil.buildLogicIN("t.fdCode", ArrayUtil.convertArrayToList(fdNoArr));
					where+=" and t.fdIsAvailable=:fdIsAvailable";
					List<EopBasedataProject> projectList=this.getBaseDao().getHibernateSession().createQuery(where)
					.setParameter("fdIsAvailable", true).list();
					auth.setFdProjectList(projectList);
				}
				if(!ArrayUtil.isEmpty(validateInfo)){
					validateInfoList.add(validateInfo);
				}else{
					auhList.add(auth);
				}
			}
			if(ArrayUtil.isEmpty(validateInfoList)){//说明校验通过,保存所有的对象
				for (FsscBudgetingApprovalAuth auth : auhList) {
					this.add(auth);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			wb.close();
		}
		if(!ArrayUtil.isEmpty(validateInfoList)){
			request.setAttribute("validateInfoList", validateInfoList);
		}
	}
}
