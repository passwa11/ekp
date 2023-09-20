package com.landray.kmss.fssc.expense.dao.hibernate;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.fssc.expense.dao.IFsscExpenseMobileDao;
import com.landray.kmss.fssc.expense.model.FsscExpenseDetail;
import com.landray.kmss.fssc.expense.model.FsscExpenseMain;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataDaoImp;
import com.landray.kmss.util.UserUtil;

public class FsscExpenseMobileDaoImp extends ExtendDataDaoImp implements IFsscExpenseMobileDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        FsscExpenseMain fsscExpenseMain = (FsscExpenseMain) modelObj;
        if (fsscExpenseMain.getDocCreator() == null) {
            fsscExpenseMain.setDocCreator(UserUtil.getUser());
        }
        if (fsscExpenseMain.getDocCreateTime() == null) {
            fsscExpenseMain.setDocCreateTime(new Date());
        }
        List<FsscExpenseDetail> list = fsscExpenseMain.getFdDetailList();
        for(FsscExpenseDetail detail:list){
        	if(detail.getFdApprovedApplyMoney()==null){
        		detail.setFdApprovedApplyMoney(detail.getFdApplyMoney());
        	}
        }
        return super.add(fsscExpenseMain);
    }
    @Override
    public void update(IBaseModel modelObj) throws Exception {
        FsscExpenseMain fsscExpenseMain = (FsscExpenseMain) modelObj;
        if (fsscExpenseMain.getDocCreator() == null) {
            fsscExpenseMain.setDocCreator(UserUtil.getUser());
        }
        if (fsscExpenseMain.getDocCreateTime() == null) {
            fsscExpenseMain.setDocCreateTime(new Date());
        }
        List<FsscExpenseDetail> list = fsscExpenseMain.getFdDetailList();
        for(FsscExpenseDetail detail:list){
        	if(detail.getFdApprovedApplyMoney()==null){
        		detail.setFdApprovedApplyMoney(detail.getFdApplyMoney());
        	}
        }
        super.update(fsscExpenseMain);
    }
}
