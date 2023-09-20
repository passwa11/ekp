package com.landray.kmss.km.comminfo.service.spring;

import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.km.comminfo.model.KmComminfoMain;
import com.landray.kmss.km.comminfo.service.IKmComminfoMainService;
import com.landray.kmss.sys.doc.model.SysDocBaseInfo;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.util.UserUtil;

/**
 * 创建日期 2010-五月-04
 * 
 * @author 徐乃瑞 常用资料主表业务接口实现
 */
public class KmComminfoMainServiceImp extends BaseServiceImp implements
		IKmComminfoMainService {

	private IKmComminfoMainService kmComminfoMainService;

	public IKmComminfoMainService getKmComminfoMainService() {
		return kmComminfoMainService;
	}

	public void setKmComminfoMainService(
			IKmComminfoMainService kmComminfoMainService) {
		this.kmComminfoMainService = kmComminfoMainService;
	}

	@Override
	public String add(IExtendForm form, RequestContext requestContext)
			throws Exception {
		UserOperHelper.logAdd(getModelName());
		IBaseModel model = convertFormToModel(form, null, requestContext);
		if (model == null) {
            throw new NoRecordException();
        }
		SysDocBaseInfo sysDocBaseInfo = (SysDocBaseInfo) model;
		sysDocBaseInfo.setDocCreator(UserUtil.getUser());
		sysDocBaseInfo.setDocCreateTime(new Date());
		sysDocBaseInfo.setDocCreatorClientIp(requestContext.getRemoteAddr());
		return add(model);
	}

	/**
	 * 更新
	 */
	@Override
	public void update(IExtendForm form, RequestContext requestContext)
			throws Exception {
		UserOperHelper.logUpdate(getModelName());
		IBaseModel model = convertFormToModel(form, null, requestContext);
		if (model == null) {
            throw new NoRecordException();
        }
		KmComminfoMain kmComminfoMain = (KmComminfoMain) model;
		// 修改人
		kmComminfoMain.setDocAlteror(UserUtil.getUser());
		// 修改时间
		kmComminfoMain.setDocAlterTime(new Date());

		super.update(kmComminfoMain);
	}

	@Override
	public String deleteMainAltInfo(final String[] ids) throws Exception {
		getBaseDao().getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException {
				for (int i = 0; i < ids.length; i++) {
					String sourceMainId = ids[i];
					// 删除修改记录
					String hql1 = "delete from KmComminfoAltInfo m where m.comminfoMain.fdId=:sourceMainId";
					Query query1 = session.createQuery(hql1);
					query1.setParameter("sourceMainId", sourceMainId);
					query1.executeUpdate();
				}
				return null;
			}
		});
		List kmComminfoMains = this.findByPrimaryKeys(ids);
		for (Iterator iterator = kmComminfoMains.iterator(); iterator.hasNext();) {
			delete((IBaseModel) iterator.next());
		}

		return null;
	}

}
