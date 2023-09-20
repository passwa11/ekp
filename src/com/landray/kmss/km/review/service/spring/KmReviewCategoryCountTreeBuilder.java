package com.landray.kmss.km.review.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.HQLParameter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.category.rest.api.bo.SQLInfo;
import com.landray.kmss.sys.workflow.interfaces.SysFlowUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 实现changeFindPageHQLInfo相同的逻辑，使用SqlInfo封装
 *
 * @Author 严明镜
 * @create 2020年12月25日
 */
public class KmReviewCategoryCountTreeBuilder {

	/**
	 * 拼接分类树的查询条件，后续查询中固定已经有了查询条件，所以whereBlock需要以' and '开头
	 *
	 * @param request
	 * @return
	 */
	public SQLInfo buildFindPageSQLInfo(HttpServletRequest request) {
		SQLInfo sqlInfo = new SQLInfo();

		String mydoc = request.getParameter("mydoc");
		String doctype = request.getParameter("doctype");
		StringBuilder sql = new StringBuilder(sqlInfo.getWhereBlock());
		String fdIsFile = request.getParameter("fdIsFile");
		//归档箱
		if (StringUtil.isNotNull(fdIsFile)) {
			if ("1".equals(fdIsFile)) {
				sql.append(" and kmReviewMain.fd_is_filing =:fdIsFiling");
				sqlInfo.setParameter("fdIsFiling", true);
			} else if ("0".equals(fdIsFile)) {
				sql.append(" and (kmReviewMain.fd_is_filing =:fdIsFiling or kmReviewMain.fd_is_filing is null)");
				sqlInfo.setParameter("fdIsFiling", false);
			}
		}
		//废弃箱
		String docStatus = request.getParameter("docStatus");
		if (docStatus != null && !("41".equals(docStatus))) {
			sql.append(" and kmReviewMain.doc_status =:docStatus");
			sqlInfo.setParameter("docStatus", docStatus);
			if ("10".equals(docStatus) && "true".equals(request.getParameter("owner"))) {
				sql.append(" and kmReviewMain.doc_creator_id=:docCreator");
				sqlInfo.setParameter("docCreator", UserUtil.getUser().getFdId());
			}
		}
		if (StringUtil.isNotNull(mydoc)) {
			mydoc = mydoc.trim().toLowerCase();
			if ("create".equals(mydoc)) {
				//我启动的流程,不包含废弃和归档
				sql.append(" and kmReviewMain.doc_creator_id=:docCreator ");
				sqlInfo.setParameter("docCreator", UserUtil.getUser().getFdId());
				sqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
			} else if ("approval".equals(mydoc)) {
				// 待我审的流程
				sql.append(buildLimitBlockForMyApproval(sqlInfo));
				sqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
			} else if ("approved".equals(mydoc)) {
				// 我已审的流程
				sql.append(buildLimitBlockForMyApproved(sqlInfo));
				sqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
			}
		}
		if (StringUtil.isNotNull(doctype)) {
			if ("examine".equals(doctype)) {
				// 流程审核
				if (StringUtil.isNotNull(docStatus)) {
					sql.append(" and kmReviewMain.doc_status =:docStatus");
					sqlInfo.setParameter("docStatus", docStatus);
				} else {
					sql.append(" and (kmReviewMain.doc_status = '20' or kmReviewMain.doc_status = '11')");
				}
			} else if ("follow".equals(doctype)) {
				// 流程跟踪
				StringBuilder buff = new StringBuilder(sqlInfo.getJoinBlock());
				buff.append(" left join lbpm_follow lbpmFollow on lbpmFollow.fd_process_id = kmReviewMain.fd_id ");
				sqlInfo.setJoinBlock(buff.toString());
				sql.append(" and lbpmFollow.fd_watcher_id=:fdWatcher");
				sqlInfo.setParameter("fdWatcher", UserUtil.getUser().getFdId());
			} else if ("feedback".equals(doctype)) {
				// 流程反馈
				if ("41".equals(docStatus)) {
					// 未反馈
					sql.append(" and kmReviewMain.doc_status = '30'");
				}
			}
		}

		sqlInfo.setWhereBlock(sql.toString());
		return sqlInfo;
	}

	/**
	 * 我已审的流程 HQL转SQL
	 *
	 * @param sqlInfo
	 */
	private String buildLimitBlockForMyApproved(SQLInfo sqlInfo) {
		HQLInfo hqlInfo = new HQLInfo();
		SysFlowUtil.buildLimitBlockForMyApproved("kmReviewMain", hqlInfo);
		//HQL",LbpmHistoryWorkitem myWorkitem" 转为sql写法
		String joinBlock = hqlInfo.getJoinBlock();
		joinBlock = joinBlock.replace(",LbpmHistoryWorkitem", " left join lbpm_history_workitem ");
		joinBlock += " on myWorkitem.fd_process_id = kmReviewMain.fd_id ";
		sqlInfo.setJoinBlock(StringUtil.linkString(sqlInfo.getJoinBlock(), "", joinBlock));

		String whereBlock = hqlInfo.getWhereBlock();
		//删除原交叉查询的where条件
		whereBlock = whereBlock.replace("kmReviewMain.fdId = myWorkitem.fdProcess.fdId", "");
		//如果以and开头
		if (whereBlock.indexOf(" and ") != 0) {
			whereBlock = " and " + whereBlock;
		}
		//字段名替换为列名
		whereBlock = whereBlock.replaceAll("fdHandler.fdId", "fd_handler_id");
		whereBlock = whereBlock.replaceAll("fdActivityType", "fd_activity_type");

		//参数
		List<HQLParameter> parameterList = hqlInfo.getParameterList();
		if (!parameterList.isEmpty()) {
			for (HQLParameter hqlParameter : parameterList) {
				sqlInfo.setParameter(hqlParameter.getName(), hqlParameter.getValue());
			}
		}
		return whereBlock;
	}

	/**
	 * 待我审的流程 HQL转SQL
	 *
	 * @param sqlInfo 在此方法中只用来存joinBlock和setParameter
	 * @return whereBlock
	 */
	private String buildLimitBlockForMyApproval(SQLInfo sqlInfo) {
		HQLInfo hqlInfo = new HQLInfo();
		SysFlowUtil.buildLimitBlockForMyApproval("kmReviewMain", hqlInfo);
		//HQL",LbpmExpecterLog myLog" 转为sql写法
		String joinBlock = hqlInfo.getJoinBlock();
		joinBlock = joinBlock.replace(",LbpmExpecterLog", " left join lbpm_expecter_log ");
		joinBlock += " on myLog.fd_process_id = kmReviewMain.fd_id ";
		sqlInfo.setJoinBlock(StringUtil.linkString(sqlInfo.getJoinBlock(), "", joinBlock));

		String whereBlock = hqlInfo.getWhereBlock();
		//删除原交叉查询的where条件
		whereBlock = whereBlock.replace("kmReviewMain.fdId = myLog.fdProcessId", "");
		//如果以and开头
		if (whereBlock.indexOf(" and ") != 0) {
			whereBlock = " and " + whereBlock;
		}
		//字段名替换为列名
		whereBlock = whereBlock.replaceAll("fdHandler.fdId", "fd_handler_id");
		whereBlock = whereBlock.replaceAll("fdTaskType", "fd_task_type");
		whereBlock = whereBlock.replaceAll("fdIsActive", "fd_is_active");
		whereBlock = whereBlock.replaceAll("canFastReview", "can_fast_review");

		//参数
		List<HQLParameter> parameterList = hqlInfo.getParameterList();
		if (!parameterList.isEmpty()) {
			for (HQLParameter hqlParameter : parameterList) {
				sqlInfo.setParameter(hqlParameter.getName(), hqlParameter.getValue());
			}
		}
		return whereBlock;
	}
}
