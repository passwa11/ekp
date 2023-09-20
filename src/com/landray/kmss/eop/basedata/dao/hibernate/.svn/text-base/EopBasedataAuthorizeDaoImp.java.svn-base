package com.landray.kmss.eop.basedata.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.dao.IEopBasedataAuthorizeDao;
import com.landray.kmss.eop.basedata.model.EopBasedataAuthorize;
import com.landray.kmss.util.UserUtil;

public class EopBasedataAuthorizeDaoImp extends BaseDaoImp implements IEopBasedataAuthorizeDao {
	
	@Override
    public String add(IBaseModel modelObj) throws Exception {
		EopBasedataAuthorize eopBasedataAuthorize = (EopBasedataAuthorize) modelObj;
        if (eopBasedataAuthorize.getDocCreator() == null) {
        	eopBasedataAuthorize.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataAuthorize.getDocCreateTime() == null) {
        	eopBasedataAuthorize.setDocCreateTime(new Date());
        }
        if (eopBasedataAuthorize.getDocAlteror() == null) {
        	eopBasedataAuthorize.setDocAlteror(UserUtil.getUser());
        }
        if (eopBasedataAuthorize.getDocAlterTime() == null) {
        	eopBasedataAuthorize.setDocAlterTime(new Date());
        }
        return super.add(eopBasedataAuthorize);
    }
	
	@Override
    public void update(IBaseModel modelObj) throws Exception {
		EopBasedataAuthorize eopBasedataAuthorize = (EopBasedataAuthorize) modelObj;
    	eopBasedataAuthorize.setDocAlteror(UserUtil.getUser());
    	eopBasedataAuthorize.setDocAlterTime(new Date());
    	super.update(eopBasedataAuthorize);
	}
}
