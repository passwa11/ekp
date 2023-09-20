package com.landray.kmss.fssc.fee.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.fssc.fee.dao.IFsscFeeMainDao;
import com.landray.kmss.fssc.fee.model.FsscFeeMain;
import com.landray.kmss.fssc.fee.model.FsscFeeTemplate;
import com.landray.kmss.sys.formula.parser.FormulaParser;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataDaoImp;
import com.landray.kmss.util.UserUtil;

public class FsscFeeMainDaoImp extends ExtendDataDaoImp implements IFsscFeeMainDao {

	@Override
    public String add(IBaseModel modelObj) throws Exception {
        FsscFeeMain fsscFeeMain = (FsscFeeMain) modelObj;
        if (fsscFeeMain.getDocCreator() == null) {
            fsscFeeMain.setDocCreator(UserUtil.getUser());
        }
        if (fsscFeeMain.getDocCreateTime() == null) {
            fsscFeeMain.setDocCreateTime(new Date());
        }
        FsscFeeTemplate cate = fsscFeeMain.getDocTemplate();
        if(cate!=null&&"2".equals(cate.getFdSubjectType())){
        	FormulaParser parser = FormulaParser.getInstance(fsscFeeMain);
        	fsscFeeMain.setDocSubject((String) parser.parseValueScript(cate.getFdSubjectRule()));
        }
        return super.add(fsscFeeMain);
    }
    @Override
    public void update(IBaseModel modelObj) throws Exception {
        FsscFeeMain fsscFeeMain = (FsscFeeMain) modelObj;
        if (fsscFeeMain.getDocCreator() == null) {
            fsscFeeMain.setDocCreator(UserUtil.getUser());
        }
        if (fsscFeeMain.getDocCreateTime() == null) {
            fsscFeeMain.setDocCreateTime(new Date());
        }
        FsscFeeTemplate cate = fsscFeeMain.getDocTemplate();
        if(cate!=null&&"2".equals(cate.getFdSubjectType())){
        	FormulaParser parser = FormulaParser.getInstance(fsscFeeMain);
        	fsscFeeMain.setDocSubject((String) parser.parseValueScript(cate.getFdSubjectRule()));
        }
        super.update(fsscFeeMain);
    }
}
