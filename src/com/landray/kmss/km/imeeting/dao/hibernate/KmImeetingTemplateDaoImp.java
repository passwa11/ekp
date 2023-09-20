package com.landray.kmss.km.imeeting.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.imeeting.dao.IKmImeetingTemplateDao;
import com.landray.kmss.km.imeeting.model.KmImeetingTemplate;
import com.landray.kmss.util.UserUtil;

/**
 * 会议模板数据访问接口实现
 */
public class KmImeetingTemplateDaoImp extends BaseDaoImp implements
		IKmImeetingTemplateDao {

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		KmImeetingTemplate kmImeetingTemplate = (KmImeetingTemplate) modelObj;
		if (kmImeetingTemplate.getDocCreator() == null) {
			kmImeetingTemplate.setDocCreator(UserUtil.getUser());
		}
		if (kmImeetingTemplate.getDocCreateTime() == null) {
			kmImeetingTemplate.setDocCreateTime(new Date());
		}
		return super.add(modelObj);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		KmImeetingTemplate kmImeetingTemplate = (KmImeetingTemplate) modelObj;
		kmImeetingTemplate.setDocAlteror(UserUtil.getUser());
		kmImeetingTemplate.setDocAlterTime(new Date());
		super.update(modelObj);
	}
	
	

}
