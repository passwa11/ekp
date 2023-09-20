package com.landray.kmss.km.imeeting.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.imeeting.dao.IKmImeetingBookDao;
import com.landray.kmss.km.imeeting.model.KmImeetingBook;
import com.landray.kmss.util.UserUtil;

/**
 * 会议室预约数据访问接口实现
 * 
 * @author
 * @version 1.0 2014-07-21
 */
public class KmImeetingBookDaoImp extends BaseDaoImp implements
		IKmImeetingBookDao {

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		KmImeetingBook kmImeetingBook = (KmImeetingBook) modelObj;
		if (kmImeetingBook.getDocCreator() == null) {
			kmImeetingBook.setDocCreator(UserUtil.getUser());
		}
		return super.add(modelObj);
	}

}
