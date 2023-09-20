package com.landray.kmss.sys.praise.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.util.ClassUtils;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.service.BaseCoreInnerServiceImp;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.praise.model.SysPraiseMain;
import com.landray.kmss.sys.praise.service.ISysPraiseMainService;
import com.landray.kmss.sys.praise.util.HbmColumnUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import org.hibernate.query.NativeQuery;

public class SysPraiseMainServiceImp extends BaseCoreInnerServiceImp
		implements ISysPraiseMainService {

	/**
	 * 获得赞/踩的数量
	 * 
	 * @param request
	 * @param fdModelId
	 * @param fdModelName
	 * @return
	 * @throws Exception
	 */
	@Override
    public Integer getPraiseCount(String fdModelId, String fdModelName,
                                  String fdType) throws Exception {

		HQLInfo hqlInfo = new HQLInfo();
		String where = buildWhereBlock(hqlInfo, fdModelId, fdModelName, fdType);

		hqlInfo.setWhereBlock(where);
		hqlInfo.setSelectBlock(" count(*) ");
		Object count = findValue(hqlInfo).get(0);

		return new Integer(count.toString());
	}

	/**
	 * 该人员是否已 赞/踩 过该文档
	 * 
	 * @param praiserId
	 * @param fdModelId
	 * @param fdModelName
	 * @return
	 * @throws Exception
	 */
	@Override
    public Boolean checkPraised(String praiserId, String fdModelId,
                                String fdModelName, String fdType) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String where = buildWhereBlock(hqlInfo, fdModelId, fdModelName, fdType);

		if (StringUtil.isNotNull(praiserId)) {
			where += " and sysPraiseMain.fdPraisePerson.fdId=:praiserId";
			hqlInfo.setParameter("praiserId", praiserId);
		}
		hqlInfo.setWhereBlock(where);
		hqlInfo.setSelectBlock(" count(*) ");
		Object count = findValue(hqlInfo).get(0);
		Integer praiseCount = new Integer(count.toString());

		if (praiseCount > 0) {
			return true;
		}
		return false;
	}

	/**
	 * @param praiserId
	 * @param fdModelId
	 * @param fdModelName
	 * @return
	 * @throws Exception
	 */
	@Override
    public List checkPraisedByIds(String praiserId, String ids,
                                  String fdModelName) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		List praiseIds = new ArrayList();
		String where = "1=1";

		if (StringUtil.isNotNull(ids) && StringUtil.isNotNull(fdModelName)
				&& StringUtil.isNotNull(praiserId)) {
			where += " and sysPraiseMain.fdModelId in(:fdModelIds) and sysPraiseMain.fdModelName=:fdModelName "
					+ "and sysPraiseMain.fdPraisePerson.fdId=:praiserId and (sysPraiseMain.fdType is null or sysPraiseMain.fdType=:fdType) group by sysPraiseMain.fdModelId";

			String[] fdModelIds = ids.split(",");
			hqlInfo.setParameter("fdModelIds",
					ArrayUtil.convertArrayToList(fdModelIds));
			hqlInfo.setParameter("fdModelName", fdModelName);
			hqlInfo.setParameter("fdType", SysPraiseMain.SYSPRAISEMAIN_PRAISE);
			hqlInfo.setParameter("praiserId", praiserId);

			hqlInfo.setWhereBlock(where);
			String selectBlock = " sysPraiseMain.fdModelId ";
			hqlInfo.setSelectBlock(selectBlock);
			praiseIds = findList(hqlInfo);

		}
		return praiseIds;
	}

	public String buildWhereBlock(HQLInfo hqlInfo, String fdModelId,
			String fdModelName, String fdType) throws Exception {
		String where = "1=1";
		if (StringUtil.isNotNull(fdModelId)) {
			where += " and sysPraiseMain.fdModelId=:fdModelId";
			hqlInfo.setParameter("fdModelId", fdModelId);
		}
		if (StringUtil.isNotNull(fdModelName)) {
			where += " and sysPraiseMain.fdModelName=:fdModelName";
			hqlInfo.setParameter("fdModelName", fdModelName);
		}
		if (StringUtil.isNotNull(fdType)
				&& fdType.equals(SysPraiseMain.SYSPRAISEMAIN_NEGATIVE)) {
			where += " and sysPraiseMain.fdType=:fdType";
			hqlInfo.setParameter("fdType",
					SysPraiseMain.SYSPRAISEMAIN_NEGATIVE);
		} else {
			where += " and (sysPraiseMain.fdType is null or sysPraiseMain.fdType=:fdType)";
			hqlInfo.setParameter("fdType", SysPraiseMain.SYSPRAISEMAIN_PRAISE);
		}

		return where;
	}

	/**
	 * 获取该人员已赞的点赞记录id
	 * 
	 * @param praiserId
	 * @param fdModelId
	 * @param fdModelName
	 * @param fdType
	 * @return
	 * @throws Exception
	 */
	@Override
    public String getPraiseId(String praiserId, String fdModelId,
                              String fdModelName, String fdType) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String where = buildWhereBlock(hqlInfo, fdModelId, fdModelName, fdType);

		if (StringUtil.isNotNull(praiserId)) {
			where += " and sysPraiseMain.fdPraisePerson.fdId=:praiserId ";
			hqlInfo.setParameter("praiserId", praiserId);
		}
		hqlInfo.setWhereBlock(where);
		hqlInfo.setSelectBlock(" sysPraiseMain.fdId ");
		List hqlList = findValue(hqlInfo);
		if (hqlList.size() > 0) {
			Object count = findValue(hqlInfo).get(0);
			return count.toString();
		}

		return null;
	}

	/**
	 * 点赞(踩)或取消点赞(踩)
	 * 
	 * @param fdModelId
	 * @param fdModelName
	 * @return
	 * @throws Exception
	 */
	@Override
    public void addOrDel(String fdModelId, String fdModelName, String fdType,
                         RequestContext requestContext) throws Exception {
		String plusOrMinus = "-";
		String serviceBean = SysDataDict.getInstance().getModel(fdModelName)
				.getServiceBean();
		// 主model
		IBaseService service =
				(IBaseService) SpringBeanUtil.getBean(serviceBean);
		BaseModel docbase = (BaseModel) service.findByPrimaryKey(fdModelId);

		String praiseId = getPraiseId(UserUtil.getUser().getFdId(), fdModelId,
				fdModelName, fdType);

		if (StringUtil.isNotNull(praiseId)) {
			super.delete(praiseId);
			plusOrMinus = "-";
		} else {
			// 保存点赞信息
			SysPraiseMain sysPraiseMainMain = new SysPraiseMain();
			sysPraiseMainMain.setFdPraisePerson(UserUtil.getUser());
			sysPraiseMainMain.setFdPraiseTime(new Date());
			sysPraiseMainMain.setFdModelId(fdModelId);
			sysPraiseMainMain.setFdModelName(fdModelName);
			sysPraiseMainMain.setFdType(fdType);
			sysPraiseMainMain.setFdIp(Plugin.currentRequest().getRemoteAddr());
			add(sysPraiseMainMain);

			if (UserOperHelper.allowLogOper("executePraise", getModelName())) {
				UserOperContentHelper.putAdd(sysPraiseMainMain,
						"fdPraisePerson", "fdPraiseTime", "fdModelId",
						"fdModelName", "fdType");
			}

			plusOrMinus = "+";
		}

		// 记录日志
		if (UserOperHelper.allowLogOper("executePraise", getModelName())) {
			// 取消点赞
			if ("-".equals(plusOrMinus)) {
				UserOperHelper.setEventType(ResourceUtil
						.getString("sysPraiseMain.cancelPraise", "sys-praise"));
			}
		}

		// 更新点赞(踩)数
		updatePraiseCount(service, docbase, plusOrMinus, fdType);

	}

	/**
	 * 更新点赞数
	 * 
	 * @param service
	 * @param docbase
	 * @param fdType
	 * @param plusOrDel
	 * @throws Exception
	 */
	@Override
    public void updatePraiseCount(IBaseService service, BaseModel docbase,
                                  String plusOrMinus, String fdType) throws Exception {
		Class modelClazz = com.landray.kmss.util.ClassUtils.forName(service.getModelName());
		String prarseCount = "";
		if (SysPraiseMain.SYSPRAISEMAIN_NEGATIVE.equals(fdType)) {
			prarseCount =
					HbmColumnUtil.getColumnName(modelClazz, "docNegativeCount");
		} else {
			prarseCount =
					HbmColumnUtil.getColumnName(modelClazz, "docPraiseCount");
		}
		String tableName = HbmColumnUtil.getTableName(modelClazz);

		String subSql = tableName + "." + prarseCount;
		String sql = "update " + tableName + " set " + subSql + " = (" + subSql
				+ plusOrMinus + "1) where " + tableName + ".fd_id=?";
		NativeQuery nativeQuery = service.getBaseDao().getHibernateSession().createNativeQuery(sql);
		nativeQuery.setParameter(0, docbase.getFdId());
		nativeQuery.addSynchronizedQuerySpace(tableName);
		nativeQuery.executeUpdate();

	}

	@Override
	public List<SysPraiseMain> checkPraiseAndNegativeByIds(String praiserId,
			String ids, String fdModelName) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		List<SysPraiseMain> infoList = new ArrayList<SysPraiseMain>();
		String where = "1=1";

		if (StringUtil.isNotNull(ids) && StringUtil.isNotNull(fdModelName)
				&& StringUtil.isNotNull(praiserId)) {
			where += " and sysPraiseMain.fdModelId in(:fdModelIds) and sysPraiseMain.fdModelName=:fdModelName "
					+ "and sysPraiseMain.fdPraisePerson.fdId=:praiserId";

			String[] fdModelIds = ids.split(",");
			hqlInfo.setParameter("fdModelIds",
					ArrayUtil.convertArrayToList(fdModelIds));
			hqlInfo.setParameter("fdModelName", fdModelName);
			hqlInfo.setParameter("praiserId", praiserId);
			hqlInfo.setWhereBlock(where);
			infoList = findList(hqlInfo);
		}
		return infoList;
	}

}
