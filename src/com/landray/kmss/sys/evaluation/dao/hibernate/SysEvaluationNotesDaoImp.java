package com.landray.kmss.sys.evaluation.dao.hibernate;

import org.hibernate.Session;

import com.landray.kmss.common.dao.BaseCoreInnerDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.evaluation.dao.ISysEvaluationNotesDao;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.StringUtil;

public class SysEvaluationNotesDaoImp extends BaseCoreInnerDaoImp 
					implements ISysEvaluationNotesDao{
	/*
	 * 获得段落点评的数量
	 */
	@Override
    public int getNotesCountByModel(IBaseModel model) {
		String modelName = ModelUtil.getModelClassName(model);
		String whereStr = " where sysEvaluationNotes.fdModelId = '"
				+ model.getFdId() + "'";
		whereStr = StringUtil.linkString(whereStr, " and ",
				"sysEvaluationNotes.fdModelName='" + modelName + "'");
		Session session = super.getSession();
		int total = ((Long) session.createQuery(
				"select count(*) from com.landray.kmss.sys.evaluation.model.SysEvaluationNotes"
						+ " sysEvaluationNotes " + whereStr).iterate().next())
				.intValue();
		return total;
	}
}
