package com.landray.kmss.eop.basedata.dao.hibernate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import com.landray.kmss.common.dao.BaseTreeDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.dao.IEopBasedataStandardSchemeDao;
import com.landray.kmss.eop.basedata.model.EopBasedataStandardScheme;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class EopBasedataStandardSchemeDaoImp extends BaseTreeDaoImp implements IEopBasedataStandardSchemeDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataStandardScheme eopBasedataStandardScheme = (EopBasedataStandardScheme) modelObj;
        if (eopBasedataStandardScheme.getDocCreator() == null) {
            eopBasedataStandardScheme.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataStandardScheme.getDocCreateTime() == null) {
            eopBasedataStandardScheme.setDocCreateTime(new Date());
        }
        String[] dims = eopBasedataStandardScheme.getFdDimension().split(";");
        Arrays.sort(dims,new Comparator<String>(){
			@Override
			public int compare(String o1, String o2) {
				return Integer.parseInt(o1)-Integer.parseInt(o2);
			}
        });
        eopBasedataStandardScheme.setFdDimension(StringUtil.join(dims, ";"));
        return super.add(eopBasedataStandardScheme);
    }
    @Override
    public void update(IBaseModel modelObj) throws Exception {
        EopBasedataStandardScheme eopBasedataStandardScheme = (EopBasedataStandardScheme) modelObj;
        if (eopBasedataStandardScheme.getDocCreator() == null) {
            eopBasedataStandardScheme.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataStandardScheme.getDocCreateTime() == null) {
            eopBasedataStandardScheme.setDocCreateTime(new Date());
        }
        String[] dims = eopBasedataStandardScheme.getFdDimension().split(";");
        Arrays.sort(dims,new Comparator<String>(){
			@Override
			public int compare(String o1, String o2) {
				return Integer.parseInt(o1)-Integer.parseInt(o2);
			}
        });
        eopBasedataStandardScheme.setFdDimension(StringUtil.join(dims, ";"));
        super.add(eopBasedataStandardScheme);
    }
}
