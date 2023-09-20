package com.landray.kmss.common.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.recycle.util.SysRecycleUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.filter.security.ValidatorRule;

/**
 * 无权限过滤的HQL语句的拼装器。
 * 
 * @author 叶中奇
 * @version 1.0 2006-04-02
 */
public class HQLBuilderImp implements SysAuthConstant, IHQLBuilder {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(HQLBuilderImp.class);

	@Override
    public HQLWrapper buildQueryHQL(HQLInfo hqlInfo) throws Exception {
		HQLWrapper hqlWrapper = null;
		if (hqlInfo.getDistinctType() == HQLInfo.DISTINCT_YES) {
			hqlWrapper = buildDistinctHQL(hqlInfo, hqlInfo.getWhereBlock());
		} else {
			hqlWrapper = buildNormalHQL(hqlInfo, hqlInfo.getWhereBlock());
		}
		if (logger.isDebugEnabled()) {
			logger.debug("hql:" + hqlWrapper.getHql());
			for (HQLParameter hqlParameter : hqlWrapper.getParameterList()) {
				logger.debug("hql parameter:: name:" + hqlParameter.getName()
						+ " value:" + hqlParameter.getValue() + " type:"
						+ hqlParameter.getType());
			}
		}
		return hqlWrapper;
	}

	private HQLWrapper buildDistinctHQL(HQLInfo hqlInfo, String extendWhereBlock)
			throws Exception {
		StringBuffer hql = new StringBuffer();
		String tempName = "kmss_tmp_" + hqlInfo.getModelTable();
		String softDelete_tempName = tempName;
		if (hqlInfo.isGettingCount()) {
			hql.append("select count(distinct " + hqlInfo.getModelTable()
					+ ".fdId) ");
			hql.append("from " + hqlInfo.getModelName() + " "
					+ hqlInfo.getModelTable() + " ");
			softDelete_tempName = hqlInfo.getModelTable();
		} else {
			if (StringUtil.isNull(hqlInfo.getSelectBlock())) {
				hql.append("select " + tempName + " ");
			} else {
				hql.append("select "
						+ replaceTempName(hqlInfo.getSelectBlock(),
								hqlInfo.getModelTable(), tempName) + " ");
			}
			hql.append("from " + hqlInfo.getModelName() + " " + tempName + " ");
			hql.append(replaceTempName(HQLUtil.getAutoFetchInfo(hqlInfo),
					hqlInfo.getModelTable(), tempName));
			hql.append("where " + tempName + ".fdId in (");
			hql.append("select " + hqlInfo.getModelTable() + ".fdId ");
			if (StringUtil.isNull(hqlInfo.getFromBlock())) {
				hql.append("from " + hqlInfo.getModelName() + " "
						+ hqlInfo.getModelTable() + " ");
			} else {
				hql.append("from " + hqlInfo.getFromBlock() + " ");
			}
		}

		if (!StringUtil.isNull(hqlInfo.getJoinBlock())) {
			hql.append(hqlInfo.getJoinBlock() + " ");
		}

		if (!StringUtil.isNull(extendWhereBlock)) {
			hql.append("where " + extendWhereBlock);
			if (SysRecycleUtil.isEnableSoftDelete(hqlInfo.getModelName())) {
				int docDeleteFlag = hqlInfo.getDocDeleteFlag();
				switch (docDeleteFlag) {
				case 0:
					hql.append(
							" and " + softDelete_tempName
									+ ".docDeleteFlag = 0");
					break;
				case 1:
					hql.append(" and " + softDelete_tempName
							+ ".docDeleteFlag = 1");
					break;
				}
			}
		} else {
			if (SysRecycleUtil.isEnableSoftDelete(hqlInfo.getModelName())) {
				int docDeleteFlag = hqlInfo.getDocDeleteFlag();
				switch (docDeleteFlag) {
				case 0:
					hql.append(
							"where " + softDelete_tempName
									+ ".docDeleteFlag = 0");
					break;
				case 1:
					hql.append("where " + softDelete_tempName
							+ ".docDeleteFlag = 1");
					break;
				}
			}
		}

		if (!hqlInfo.isGettingCount()) {
			hql.append(")");
			if (!StringUtil.isNull(hqlInfo.getOrderBy())) {
				if (!ValidatorRule.validationSQL(hqlInfo.getOrderBy(),
						"orderby")) {
					throw new RuntimeException("SQL参数orderby中包括非法值");
				}
				hql.append(" order by "
						+ replaceTempName(hqlInfo.getOrderBy(),
								hqlInfo.getModelTable(), tempName));
			}
		}
		return new HQLWrapper(hql.toString(), hqlInfo.getParameterList());
	}

	private HQLWrapper buildNormalHQL(HQLInfo hqlInfo, String extendWhereBlock)
			throws Exception {
		StringBuffer hql = new StringBuffer();
		String softDelete_tempName = hqlInfo.getModelTable();
		if (hqlInfo.isGettingCount()) {
			hql.append("select count(*) ");
			if (StringUtil.isNull(hqlInfo.getFromBlock())) {
				hql.append("from " + hqlInfo.getModelName() + " "
						+ hqlInfo.getModelTable() + " ");
			} else {
				hql.append("from " + hqlInfo.getFromBlock() + " ");
			}
		} else {
			if (StringUtil.isNull(hqlInfo.getSelectBlock())) {
				hql.append("select " + hqlInfo.getModelTable() + " ");
			} else {
				hql.append("select " + hqlInfo.getSelectBlock() + " ");
			}

			if (StringUtil.isNull(hqlInfo.getFromBlock())) {
				hql.append("from " + hqlInfo.getModelName() + " "
						+ hqlInfo.getModelTable() + " ");
			} else {
				hql.append("from " + hqlInfo.getFromBlock() + " ");
			}
			hql.append(HQLUtil.getAutoFetchInfo(hqlInfo));
		}

		if (!StringUtil.isNull(hqlInfo.getJoinBlock())) {
			hql.append(hqlInfo.getJoinBlock() + " ");
		}

		if (!StringUtil.isNull(extendWhereBlock)) {
			hql.append("where " + extendWhereBlock + " ");
			if(SysAuthConstant.SoftDeleteCheck.YES != hqlInfo.getCheckParam(SysAuthConstant.CheckType.SoftDeleteCheck)){
				
				if (SysRecycleUtil.isEnableSoftDelete(hqlInfo.getModelName())) {
					int docDeleteFlag = hqlInfo.getDocDeleteFlag();
					switch (docDeleteFlag) {
					case 0:
						hql.append(
								" and " + softDelete_tempName
										+ ".docDeleteFlag = 0");
						break;
					case 1:
						hql.append(" and " + softDelete_tempName
								+ ".docDeleteFlag = 1");
						break;
					}
				}
			}
		} else {
			if(SysAuthConstant.SoftDeleteCheck.YES != hqlInfo.getCheckParam(SysAuthConstant.CheckType.SoftDeleteCheck)){
				if (SysRecycleUtil.isEnableSoftDelete(hqlInfo.getModelName())) {
					int docDeleteFlag = hqlInfo.getDocDeleteFlag();
					switch (docDeleteFlag) {
					case 0:
						hql.append(
								"where " + softDelete_tempName
										+ ".docDeleteFlag = 0");
						break;
					case 1:
						hql.append("where " + softDelete_tempName
								+ ".docDeleteFlag = 1");
						break;
					}
				}
			}
		}

		if (!hqlInfo.isGettingCount()
				&& !StringUtil.isNull(hqlInfo.getOrderBy())) {
			if (!ValidatorRule.validationSQL(hqlInfo.getOrderBy(), "orderby")) {
				throw new RuntimeException("SQL参数orderby中包括非法值");
			}
			hql.append(" order by " + hqlInfo.getOrderBy());
		}
		return new HQLWrapper(hql.toString(), hqlInfo.getParameterList());
	}

	private static String replaceTempName(String srcName, String fromName,
			String toName) {
		return srcName.replaceAll("(^|\\W)" + fromName + "(\\.|\\W)", "$1"
				+ toName + "$2");
	}

}
