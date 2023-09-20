package com.landray.kmss.eop.basedata.dao.hibernate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.dao.IEopBasedataBudgetSchemeDao;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetScheme;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;


public class EopBasedataBudgetSchemeDaoImp extends BaseDaoImp implements IEopBasedataBudgetSchemeDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataBudgetScheme eopBasedataBudgetScheme = (EopBasedataBudgetScheme) modelObj;
        if (eopBasedataBudgetScheme.getDocCreator() == null) {
            eopBasedataBudgetScheme.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataBudgetScheme.getDocCreateTime() == null) {
            eopBasedataBudgetScheme.setDocCreateTime(new Date());
        }
        String[] dims = eopBasedataBudgetScheme.getFdDimension().split(";");
        Arrays.sort(dims,new Comparator<String>(){
			@Override
			public int compare(String o1, String o2) {
				return Integer.parseInt(o1)-Integer.parseInt(o2);
			}
        });
        eopBasedataBudgetScheme.setFdDimension(StringUtil.join(dims, ";"));
        return super.add(eopBasedataBudgetScheme);
    }
    @Override
    public void update(IBaseModel modelObj) throws Exception {
        EopBasedataBudgetScheme eopBasedataBudgetScheme = (EopBasedataBudgetScheme) modelObj;
        if (eopBasedataBudgetScheme.getDocCreator() == null) {
            eopBasedataBudgetScheme.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataBudgetScheme.getDocCreateTime() == null) {
            eopBasedataBudgetScheme.setDocCreateTime(new Date());
        }
        String[] dims = eopBasedataBudgetScheme.getFdDimension().split(";");
        Arrays.sort(dims,new Comparator<String>(){
			@Override
			public int compare(String o1, String o2) {
				return Integer.parseInt(o1)-Integer.parseInt(o2);
			}
        });
        eopBasedataBudgetScheme.setFdDimension(StringUtil.join(dims, ";"));
        super.update(eopBasedataBudgetScheme);
    }
}
