package com.landray.kmss.sys.mportal.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.mportal.dao.ISysMportalImgSourceDao;
import com.landray.kmss.sys.mportal.model.SysMportalImgSource;
import com.landray.kmss.util.UserUtil;


/*
 * 
 * @author zhuhq
 * @version 1.0 2017-01-13
 */
public class SysMportalImgSourceDaoImp extends BaseDaoImp implements ISysMportalImgSourceDao{
	
	@Override
    public String add(IBaseModel modelObj) throws Exception {
		SysMportalImgSource xmodel = (SysMportalImgSource) modelObj;
		if (xmodel.getDocCreator() == null) {
			xmodel.setDocCreator(UserUtil.getUser());
		}
		if (xmodel.getDocCreateTime() == null) {
			xmodel.setDocCreateTime(new Date());
		}
		return super.add(xmodel);
	}

	@Override
    public void update(IBaseModel modelObj) throws Exception {
		SysMportalImgSource xmodel = (SysMportalImgSource) modelObj;
		xmodel.setDocAlteror(UserUtil.getUser());
		xmodel.setDocAlterTime(new Date());
		super.update(modelObj);
	}

}
