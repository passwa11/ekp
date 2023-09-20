package com.landray.kmss.sys.simplecategory.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.CacheMode;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.model.IBaseTreeModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.category.service.ISysCategoryMainService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.IUserUpdateOper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.simplecategory.model.ISysSimpleCategoryModel;
import com.landray.kmss.sys.simplecategory.service.ISysSimpleCategoryService;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;

/**
 * 简单分类数据迁移
 * 
 * @author wubing
 */
public class SysSimpleCategoryDtAction extends ExtendAction

{
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysSimpleCategoryDtAction.class);

	public ActionForward dt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-dt", true, getClass());
		String fdModelName = request.getParameter("fdModelName");
		UserOperHelper.logUpdate(fdModelName);
		String cateFld = request.getParameter("cateFld");
		if (StringUtil.isNull(cateFld)) {
			cateFld = "fd_category_id";
		}
		KmssMessages messages = new KmssMessages();
		try {
			ISysCategoryMainService scm = (ISysCategoryMainService) getBean("sysCategoryMainService");
			
			HQLInfo hqlInfo=new HQLInfo();
			hqlInfo.setWhereBlock(" fdModelName = :fdModelName ");
			hqlInfo.setOrderBy("fdHierarchyId asc");
			hqlInfo.setParameter("fdModelName", fdModelName);
			List scList = scm.findList(hqlInfo);
			logger.debug("需要转移的分类记录数:" + scList.size());
			IBaseDao dao = ((IBaseDao) getBean("KmssBaseDao"));
			SysDictModel model = SysDataDict.getInstance()
					.getModel(fdModelName);
			ISysSimpleCategoryService bs = (ISysSimpleCategoryService) getBean(model
					.getServiceBean());
			for (int i = 0; i < scList.size(); i++) {
				ISysSimpleCategoryModel o = dtObject((SysCategoryMain) scList
						.get(i), fdModelName);
				o.setFdHierarchyId(BaseTreeConstant.HIERARCHY_ID_SPLIT
						+ o.getFdId() + BaseTreeConstant.HIERARCHY_ID_SPLIT);
				bs.update((IBaseModel) o);
				dao.getHibernateSession().refresh(o);
			}
			if(logger.isDebugEnabled()){
			    logger.debug("转移完成的分类记录数:" + scList.size());
	            logger.debug("更新父子关系");    
			}
			
			for (int i = 0; i < scList.size(); i++) {
				SysCategoryMain m = (SysCategoryMain) scList.get(i);
				ISysSimpleCategoryModel t = (ISysSimpleCategoryModel) bs
						.findByPrimaryKey(getFdId(m.getFdId()));
				IBaseTreeModel fdParent = m.getFdParent();
				if (fdParent != null) {
					ISysSimpleCategoryModel p = (ISysSimpleCategoryModel) bs
							.findByPrimaryKey(getFdId(m.getFdParent().getFdId()));
					logger.debug("p.getFdHierarchyId()::"
							+ p.getFdHierarchyId());
					IUserUpdateOper updateOper = UserOperContentHelper.putUpdate(p);
					t.setHbmParent((ISysSimpleCategoryModel) p);
					updateOper.putSimple("hbmParent", fdParent, p);
					bs.update(t);
					dao.getHibernateSession().refresh(t);
					logger.debug("t.getFdHierarchyId()::"
							+ t.getFdHierarchyId());
				}
			}
			logger.debug("更新父子关系完成");
			
			//cateFld 有可能注入,未解决
//			List tList = dao.getHibernateSession().createNativeQuery(
//					"select fd_id," + cateFld + " from " + model.getTable()
//							+ " where " + cateFld + " is not null").list();
			//#168396 优化 更新数据时声明影响范围，避免二级缓存重建
			NativeQuery query = dao.getHibernateSession().createNativeQuery(
					"select fd_id," + cateFld + " from " + model.getTable()
							+ " where " + cateFld + " is not null");
			// 启用二级缓存
			query.setCacheable(true);
			// 设置缓存模式
			query.setCacheMode(CacheMode.NORMAL);
			// 设置缓存区域
			query.setCacheRegion("sys-simplecategory");
			List tList = query.list();
			logger.debug("需要修改的模板记录数:" + tList.size());

			for (int i = 0; i < tList.size(); i++) {
				Object[] arr = (Object[]) tList.get(i);
				String fdId = (String) arr[0];
				String fdCateId = (String) arr[1];
				ISysSimpleCategoryModel t = (ISysSimpleCategoryModel) bs
						.findByPrimaryKey(fdId);
				ISysSimpleCategoryModel c = (ISysSimpleCategoryModel) bs
						.findByPrimaryKey(getFdId(fdCateId));
//				if(c.getFdParent()!=null){
//					//将当前模板上移为分类
//					c = (ISysSimpleCategoryModel)c.getFdParent();
//					t.setFdName(c.getFdName());//将模板名修改为分类名
//				}
				t.setFdIsinheritMaintainer(new Boolean(true));
				IUserUpdateOper updateOper = UserOperContentHelper.putUpdate(t);
				IBaseTreeModel oldHbmParent = t.getHbmParent();
				t.setHbmParent(c);
				updateOper.putSimple("hbmParent", oldHbmParent, c);
				t.setFdHierarchyId(getTreeHierarchyId(t));				
				bs.update(t);
				dao.getHibernateSession().refresh(t);
				logger.debug("t.getFdHierarchyId()::"
						+ t.getFdHierarchyId());
			}
			
			logger.debug("修改完成的模板记录数:" + scList.size());

		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-dt", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	private String getTreeHierarchyId(IBaseTreeModel treeModel) {
		if (treeModel.getFdParent() != null) {
            return treeModel.getFdParent().getFdHierarchyId()
                    + treeModel.getFdId() + BaseTreeConstant.HIERARCHY_ID_SPLIT;
        } else {
            return BaseTreeConstant.HIERARCHY_ID_SPLIT + treeModel.getFdId()
                    + BaseTreeConstant.HIERARCHY_ID_SPLIT;
        }
	}

	private ISysSimpleCategoryModel dtObject(SysCategoryMain cm,
			String fdModelName) throws Exception {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Object o = loader.loadClass(fdModelName).newInstance();
		ISysSimpleCategoryModel m = (ISysSimpleCategoryModel) o;
		m.setFdId(getFdId(cm.getFdId()));
		m.setFdName(cm.getFdName());
		m.setFdOrder(cm.getFdOrder());
		m.setFdIsinheritMaintainer(cm.getFdIsinheritMaintainer());
		m.setFdIsinheritUser(cm.getFdIsinheritUser());
		m.setDocCreator(cm.getDocCreator());
		m.setDocCreateTime(cm.getDocCreateTime());
		m.setDocAlteror(cm.getDocAlteror());
		m.setDocAlterTime(cm.getDocAlterTime());
		m.setAuthReaderFlag(cm.getAuthReaderFlag());
		m.setAuthEditors(getOrgElements(cm.getAuthEditors()));
		m.setAuthReaders(getOrgElements(cm.getAuthReaders()));
		return m;
	}
	
	private String getFdId(String fdId){
		if(fdId.length()<20){
			return "c"+fdId;
		}
		return fdId;
	}
	
	private List getOrgElements(List els) {
		return new ArrayList(els);
	}

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
}
