package com.landray.kmss.sys.evaluation.transfer;

import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.admin.transfer.constant.ISysAdminTransferConstant;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.sys.evaluation.service.ISysEvaluationReplyService;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;

public class EvaluationTransferTask implements ISysAdminTransferTask{
	
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(EvaluationTransferTask.class);
	
	@Override
	public SysAdminTransferResult run(
			SysAdminTransferContext sysAdminTransferContext) {
		try {
			
			String driverClass = ResourceUtil
					.getKmssConfigString("hibernate.connection.driverClass");
			String subSql = " concat('x',concat(fd_id,'x')) ";
			if ("net.sourceforge.jtds.jdbc.Driver".equals(driverClass)) {
				subSql = " 'x' + fd_id + 'x' ";
			}
			String firstSql = " update sys_evaluation_reply set fd_hierarchy_id = " 
							+ subSql + " where fd_parent_id is null or fd_parent_id = ' '";
			//更新第一级父类层级id
			ISysEvaluationReplyService sysEvaluationReplyService = 
				(ISysEvaluationReplyService)SpringBeanUtil.getBean("sysEvaluationReplyService");
			NativeQuery nativeQuery = sysEvaluationReplyService.getBaseDao().getHibernateSession().createNativeQuery(firstSql);
			nativeQuery.addSynchronizedQuerySpace("sys_evaluation_reply");
			nativeQuery.executeUpdate();
			
			//select * from sys_evaluation_reply --mysql：cannot update a table and select from the same table in a subquery
			String selectSql = "(select r.fd_hierarchy_id from (select * from sys_evaluation_reply) r "+
								"where sys_evaluation_reply.fd_parent_id = r.fd_id " +
								" and r.fd_hierarchy_id != ' ' and r.fd_hierarchy_id is not null)";
			String contSql = " concat(" + selectSql + " ,concat(sys_evaluation_reply.fd_id,'x')) ";
			if ("net.sourceforge.jtds.jdbc.Driver".equals(driverClass)) {
				contSql = selectSql + " + sys_evaluation_reply.fd_id + 'x' ";
			}
			
			String secSql = " update sys_evaluation_reply  set sys_evaluation_reply.fd_hierarchy_id = ( " + contSql + " )" + 
							"where sys_evaluation_reply.fd_hierarchy_id = ' ' or sys_evaluation_reply.fd_hierarchy_id is null";
			
			for(int i=0;i<20;i++){
				nativeQuery = sysEvaluationReplyService.getBaseDao().getHibernateSession().createNativeQuery(secSql);
				nativeQuery.addSynchronizedQuerySpace("sys_evaluation_reply");
				int  count  = nativeQuery.executeUpdate();
				if(count<=0){
					break;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new SysAdminTransferResult(
					ISysAdminTransferConstant.TASK_RESULT_ERROR, "点评回复表迁移层级Id数据失败！", e);
		}
		return SysAdminTransferResult.OK;
	}
}
