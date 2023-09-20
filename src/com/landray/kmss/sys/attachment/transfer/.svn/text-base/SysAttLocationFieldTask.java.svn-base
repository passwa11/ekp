package com.landray.kmss.sys.attachment.transfer;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.admin.transfer.constant.ISysAdminTransferConstant;
import com.landray.kmss.sys.admin.transfer.model.SysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTaskService;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import java.util.List;

/**
 * 附件SysAttMain表的location字段迁移到SysAttFile表中
 * @author peng
 */
public class SysAttLocationFieldTask extends SysAttTransferChecker implements ISysAdminTransferTask {

	@SuppressWarnings({ "rawtypes"})
	@Override
	public SysAdminTransferResult run(SysAdminTransferContext sysAdminTransferContext) {
		String uuid = sysAdminTransferContext.getUUID();
		ISysAdminTransferTaskService sysAdminTransferTaskService=(ISysAdminTransferTaskService)
				SpringBeanUtil.getBean("sysAdminTransferTaskService");
		ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
				.getBean("sysAttMainService");
		try {
			HQLInfo info = new HQLInfo();
			info.setWhereBlock("sysAdminTransferTask.fdUuid=:uuid");
			info.setParameter("uuid", uuid);
			List list = sysAdminTransferTaskService.getBaseDao().findValue(info);		
			if (ArrayUtil.isEmpty(list)){
				String error = "sysAdminTransferTask记录不存在，uuid:" + uuid;
				return new SysAdminTransferResult(ISysAdminTransferConstant.TASK_RESULT_ERROR
						,error,new Exception(error));
			}
			SysAdminTransferTask sysAdminTransferTask=(SysAdminTransferTask)list.get(0);
			if (sysAdminTransferTask.getFdStatus() == 1) {
				return SysAdminTransferResult.OK;
			}
			
			Session session = sysAttMainService.getBaseDao().getHibernateSession();
			String sql = "select count(fd_id) from sys_att_file f where f.fd_att_location='' or fd_att_location is null";
			long count =((Number) session.createNativeQuery(sql).uniqueResult()).longValue();
			if (count == 0){
				return SysAdminTransferResult.OK;
			}
			
			
			String dialect = ResourceUtil.getKmssConfigString("hibernate.dialect");
			
			// 迁移sys_att_main表对应的sys_att_file表记录　 
			StringBuilder updateSql = new StringBuilder();
			if (dialect.toLowerCase().indexOf("sqlserver") > -1){
				updateSql.append("update f set f.fd_att_location=(")
	               .append("select min(m1.fd_att_location) from sys_att_main m1 where m1.fd_file_id=f.fd_id and m1.fd_att_location is not null")
	               .append(" group by m1.fd_file_id ) from sys_att_file f")
	               .append(" where exists(select fd_id from sys_att_main m where m.fd_file_id=f.fd_id and m.fd_att_location is not null) ")
	               .append("and f.fd_att_location='' or f.fd_att_location is null");
			}else{
				updateSql.append("update sys_att_file f set f.fd_att_location=(")
	               .append("select min(m1.fd_att_location) from sys_att_main m1 where m1.fd_file_id=f.fd_id and m1.fd_att_location is not null")
	               .append(" group by m1.fd_file_id ")
	               .append(") where exists(select fd_id from sys_att_main m where m.fd_file_id=f.fd_id and m.fd_att_location is not null) ")
	               .append("and f.fd_att_location='' or f.fd_att_location is null");
			}
			NativeQuery nativeQuery = session.createNativeQuery(updateSql.toString());
			nativeQuery.addSynchronizedQuerySpace("sys_att_file").executeUpdate();
	
			// 迁移sys_att_rtf_data表对应的sys_att_file表记录　 
			updateSql.setLength(0);
			if (dialect.toLowerCase().indexOf("sqlserver") > -1){
				updateSql.append("update f set f.fd_att_location=(")
	               .append("select min(r1.fd_att_location) from sys_att_rtf_data r1 where r1.fd_file_id=f.fd_id and r1.fd_att_location is not null")
	               .append(" group by r1.fd_file_id ) from sys_att_file f")
	               .append(" where exists(select fd_id from sys_att_rtf_data r where r.fd_file_id=f.fd_id and r.fd_att_location is not null) ")
	               .append("and f.fd_att_location='' or f.fd_att_location is null");
			}else{
				updateSql.append("update sys_att_file f set f.fd_att_location=(")
	               .append("select min(r1.fd_att_location) from sys_att_rtf_data r1 where r1.fd_file_id=f.fd_id and r1.fd_att_location is not null")
	               .append(" group by r1.fd_file_id ")
	               .append(") where exists(select fd_id from sys_att_rtf_data r where r.fd_file_id=f.fd_id and r.fd_att_location is not null) ")
	               .append("and f.fd_att_location='' or f.fd_att_location is null");
			}
			nativeQuery = session.createNativeQuery(updateSql.toString());
			nativeQuery.addSynchronizedQuerySpace("sys_att_file").executeUpdate();
			
			// location为空的默认改为server
			nativeQuery = session.createNativeQuery("update sys_att_file set fd_att_location='server' where fd_att_location='' or fd_att_location is null");
			nativeQuery.addSynchronizedQuerySpace("sys_att_file").executeUpdate();
		} catch (Exception e) {
			logger.error("附件表的location字段迁移异常", e);
			return new SysAdminTransferResult(ISysAdminTransferConstant.TASK_STATUS_NOT_RUNED
					, e.getLocalizedMessage(),e);
		}
		return SysAdminTransferResult.OK;
	}
}
