package com.landray.kmss.fssc.expense.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.fssc.expense.forms.FsscExpenseShareMainForm;
import com.landray.kmss.fssc.expense.model.FsscExpenseMain;
import com.landray.kmss.fssc.expense.model.FsscExpenseShareCategory;
import com.landray.kmss.fssc.expense.model.FsscExpenseShareMain;
import com.landray.kmss.fssc.expense.service.IFsscExpenseShareMainService;
import com.landray.kmss.fssc.expense.util.FsscExpenseUtil;
import com.landray.kmss.sys.formula.parser.FormulaParser;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessCoreService;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.number.interfaces.ISysNumberFlowService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class FsscExpenseShareMainServiceImp extends ExtendDataServiceImp implements IFsscExpenseShareMainService {

    private ILbpmProcessCoreService lbpmProcessCoreService;

    private ISysNumberFlowService sysNumberFlowService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof FsscExpenseShareMain) {
            FsscExpenseShareMain fsscExpenseShareMain = (FsscExpenseShareMain) model;
            FsscExpenseShareMainForm fsscExpenseShareMainForm = (FsscExpenseShareMainForm) form;
            if (fsscExpenseShareMain.getDocStatus() == null || fsscExpenseShareMain.getDocStatus().startsWith("1")) {
                if (fsscExpenseShareMainForm.getDocStatus() != null && (fsscExpenseShareMainForm.getDocStatus().startsWith("1") || fsscExpenseShareMainForm.getDocStatus().startsWith("2"))) {
                    fsscExpenseShareMain.setDocStatus(fsscExpenseShareMainForm.getDocStatus());
                }
            }
            if (fsscExpenseShareMain.getFdNumber() == null && (fsscExpenseShareMain.getDocStatus().startsWith("2") || fsscExpenseShareMain.getDocStatus().startsWith("3"))) {
                fsscExpenseShareMain.setFdNumber(sysNumberFlowService.generateFlowNumber(fsscExpenseShareMain));
            }
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        FsscExpenseShareMain fsscExpenseShareMain = new FsscExpenseShareMain();
        fsscExpenseShareMain.setDocCreateTime(new Date());
        fsscExpenseShareMain.setFdOperateDate(new Date());
        fsscExpenseShareMain.setFdOperator(UserUtil.getUser());
        fsscExpenseShareMain.setDocCreator(fsscExpenseShareMain.getFdOperator());
        fsscExpenseShareMain.setFdOperatorDept(fsscExpenseShareMain.getFdOperator().getFdParent());
        FsscExpenseUtil.initModelFromRequest(fsscExpenseShareMain, requestContext);
        if (fsscExpenseShareMain.getDocTemplate() != null) {
        	requestContext.setAttribute("docTemplate", fsscExpenseShareMain.getDocTemplate());
        }
        return fsscExpenseShareMain;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        FsscExpenseShareMain fsscExpenseShareMain = (FsscExpenseShareMain) model;
        if (fsscExpenseShareMain.getDocTemplate() != null) {
            dispatchCoreService.initFormSetting(form, "fsscExpenseShareMain", fsscExpenseShareMain.getDocTemplate(), "fsscExpenseShareMain", requestContext);
        }
        //getLbpmProcessCoreService().initFormDefaultSetting(form, "fsscExpenseShareMain", "fsscExpenseShareMain", requestContext);
    }
    
    @Override
   	public String add(IBaseModel modelObj) throws Exception {
   		FsscExpenseShareMain main = (FsscExpenseShareMain) modelObj;
   		FsscExpenseShareCategory cate = main.getDocTemplate();
           if(cate!=null&&"2".equals(cate.getFdSubjectType())&&StringUtil.isNull(main.getDocSubject())){
           	FormulaParser parser = FormulaParser.getInstance(main);
           	main.setDocSubject((String) parser.parseValueScript(cate.getFdSubjectRule()));
           }
   		return super.add(modelObj);
   	}
    
    @Override
	public void update(IBaseModel modelObj) throws Exception {
		FsscExpenseShareMain main = (FsscExpenseShareMain) modelObj;
		FsscExpenseShareCategory cate=main.getDocTemplate();
	    if(cate!=null&&"2".equals(cate.getFdSubjectType())&&StringUtil.isNotNull(main.getDocSubject())){
	        	FormulaParser parser = FormulaParser.getInstance(main);
	        	main.setDocSubject((String) parser.parseValueScript(cate.getFdSubjectRule()));	
	       }
		super.update(modelObj);
	}

    @Override
    public List<FsscExpenseShareMain> findByDocTemplate(FsscExpenseShareCategory docTemplate) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fsscExpenseShareMain.docTemplate.fdId=:fdId");
        hqlInfo.setParameter("fdId", docTemplate.getFdId());
        return this.findList(hqlInfo);
    }

    public ILbpmProcessCoreService getLbpmProcessCoreService() {
        if (lbpmProcessCoreService == null) {
            lbpmProcessCoreService = (ILbpmProcessCoreService) SpringBeanUtil.getBean("lbpmProcessCoreService");
        }
        return lbpmProcessCoreService;
    }

    public void setSysNumberFlowService(ISysNumberFlowService sysNumberFlowService) {
        this.sysNumberFlowService = sysNumberFlowService;
    }
}
