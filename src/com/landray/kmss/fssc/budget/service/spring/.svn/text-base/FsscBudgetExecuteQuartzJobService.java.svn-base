package com.landray.kmss.fssc.budget.service.spring;


import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.fssc.budget.constant.FsscBudgetConstant;
import com.landray.kmss.fssc.budget.service.IFsscBudgetExecuteService;
import com.landray.kmss.fssc.budget.service.IFsscBudgetMainService;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;


import java.util.*;


public class FsscBudgetExecuteQuartzJobService extends BaseServiceImp {

    IFsscBudgetExecuteService fsscBudgetExecuteService;

    public void setFsscBudgetExecuteService(IFsscBudgetExecuteService fsscBudgetExecuteService) {
        this.fsscBudgetExecuteService = fsscBudgetExecuteService;
    }

    public void delBudgetExecute() throws Exception {
        List<String> modelName = Arrays.asList("com.landray.kmss.fssc.expense.model.FsscExpenseMain","com.landray.kmss.fssc.fee.model.FsscFeeMain"
                ,"com.landray.kmss.fssc.payment.model.FsscPaymentMain","com.landray.kmss.fssc.proapp.model.FsscProappMain");
        for (int i=0;i<modelName.size();i++) {
            SysDataDict dataDict = SysDataDict.getInstance();
            String tableName = dataDict.getModel(modelName.get(i)).getTable();
            String hSql = "select fd_id from "+tableName+" where (doc_status = :status1) or (doc_status = :status2)";
            List<String> ids = fsscBudgetExecuteService.getBaseDao().getHibernateSession().createSQLQuery(hSql).setParameter("status1", SysDocConstant.DOC_STATUS_EXAMINE).setParameter("status2",SysDocConstant.DOC_STATUS_PUBLISH).list();
            if(!ArrayUtil.isEmpty(ids)) {
                StringBuffer sql = new StringBuffer();
                sql.append("delete from fssc_budget_execute where ((fd_type = :type1) or (fd_type = :type2)) and (fd_model_name = :modelName) and ");
                sql.append(FsscCommonUtil.buildLogicNotIN("fd_model_id", ids));
                fsscBudgetExecuteService.getBaseDao().getHibernateSession().createSQLQuery(sql.toString()).setParameter("type1", FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_OCCU).setParameter("type2",FsscBudgetConstant.FSSC_BUDGET_EXECUTE_TYPE_INUSE).setParameter("modelName",modelName.get(i)).executeUpdate();
            }
        }
    }
}
