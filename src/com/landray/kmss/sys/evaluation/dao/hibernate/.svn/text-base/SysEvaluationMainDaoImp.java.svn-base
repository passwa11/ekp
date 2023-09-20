package com.landray.kmss.sys.evaluation.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.landray.kmss.common.dao.BaseCoreInnerDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.evaluation.dao.ISysEvaluationMainDao;
import com.landray.kmss.sys.evaluation.interfaces.ISysEvaluationModel;
import com.landray.kmss.sys.evaluation.model.SysEvaluationMain;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 创建日期 2006-九月-01
 * 
 * @author 叶中奇 点评机制数据访问接口实现
 */
public class SysEvaluationMainDaoImp extends BaseCoreInnerDaoImp implements
		ISysEvaluationMainDao {
	@Override
    public String add(IBaseModel modelObj) throws Exception {
		SysEvaluationMain sysEvaluationMain = (SysEvaluationMain) modelObj;
		sysEvaluationMain.setFdEvaluator(UserUtil.getUser());
		sysEvaluationMain.setFdEvaluationTime(new Date());
		return super.add(sysEvaluationMain);
	}

	@Override
    public double score(String modelName, String modelId) {
		Session session = super.getSession();
		String whereStr = " where sysEvaluationMain.fdModelId =:modelId"; 
		whereStr = StringUtil.linkString(whereStr, " and ",
				"sysEvaluationMain.fdModelName=:modelName group by sysEvaluationMain.fdModelId");
		List scoreInfo= session.createQuery(
						"select avg(5.0-sysEvaluationMain.fdEvaluationScore) " +
						"from com.landray.kmss.sys.evaluation.model.SysEvaluationMain"
								+ " sysEvaluationMain " + whereStr)
				.setParameter("modelId", modelId)
				.setParameter("modelName", modelName)
				.list();
		if(scoreInfo.size()>0){
			return (Double)scoreInfo.get(0);
		}
		return 0d;
	}

	@Override
    public int getRecordCountByModel(ISysEvaluationModel sysEvaluationModel) {
		String modelName = ModelUtil.getModelClassName(sysEvaluationModel);
		String whereStr = " where sysEvaluationMain.fdModelId = '"
				+ sysEvaluationModel.getFdId() + "'";
		whereStr = StringUtil.linkString(whereStr, " and ",
				"sysEvaluationMain.fdModelName='" + modelName + "' and sysEvaluationMain.fdParentId is null");
		Session session = super.getSession();
		int total = ((Long) session.createQuery(
				"select count(*) from com.landray.kmss.sys.evaluation.model.SysEvaluationMain"
						+ " sysEvaluationMain " + whereStr).iterate().next())
				.intValue();
		return total;
	}

	@Override
	public List getEvalStarDetail(String modelName, String modelId) {
		String whereStr = " where sysEvaluationMain.fd_model_id =:modelId";
		whereStr = StringUtil.linkString(whereStr, " and ",
				"sysEvaluationMain.fd_model_name=:modelName and sysEvaluationMain.fd_parent_id is null");
		Session session = super.getSession();
		
		String sql = "select count(*) times , sysEvaluationMain.fd_evaluation_score  from sys_evaluation_main sysEvaluationMain"
					 +	whereStr + " group by sysEvaluationMain.fd_evaluation_score";
		List list = session.createNativeQuery(sql)
					.setParameter("modelId", modelId)
					.setParameter("modelName", modelName).list();		
		return list; 
	}
	
	@Override
    public void deleteByParentId(String fdParentId) throws Exception {
		if(StringUtil.isNotNull(fdParentId)) {
			String hql = "delete from SysEvaluationMain where fdParentId=:fdParentId";
			super.getSession().createQuery(hql).setString("fdParentId", fdParentId).executeUpdate();
		}
	}
}
