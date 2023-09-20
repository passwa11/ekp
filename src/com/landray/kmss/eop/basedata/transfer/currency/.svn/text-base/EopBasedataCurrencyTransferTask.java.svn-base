package com.landray.kmss.eop.basedata.transfer.currency;

import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.eop.basedata.service.IEopBasedataCurrencyService;
import com.landray.kmss.sys.admin.transfer.constant.ISysAdminTransferConstant;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.TransactionUtils;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;

import java.util.Date;
import java.util.List;

/**
 * @author wangwh
 * @description:初始化中文货币
 * @date 2021/5/7
 */
public class EopBasedataCurrencyTransferTask implements ISysAdminTransferTask{

	protected final static Logger logger = org.slf4j.LoggerFactory.getLogger(EopBasedataCurrencyTransferTask.class);
	@Override
	public SysAdminTransferResult
			run(SysAdminTransferContext sysAdminTransferContext) {
		//开启事务
		TransactionStatus status = TransactionUtils.beginNewTransaction();
		try{
			IEopBasedataCurrencyService ser = (IEopBasedataCurrencyService) SpringBeanUtil.getBean("eopBasedataCurrencyService");
			
			String hql = "from EopBasedataCurrency where LOWER(fdCode) like :fdCode";
			Query query = ser.getBaseDao().getHibernateSession().createQuery(hql);
			query.setParameter("fdCode", "cny");
			List list = query.list();
			if (list != null && !list.isEmpty()){
				return SysAdminTransferResult.OK;
			}
			
			EopBasedataCurrency currency = new EopBasedataCurrency();
			currency.setFdName("人民币");
			currency.setDocCreateTime(new Date());
			currency.setFdStatus(0);
			currency.setFdCountry("中国");
			currency.setFdEnglishName("RMB");
			currency.setFdAbbreviation("人民币");
			currency.setFdCode("CNY");
			SysOrgPerson p = new SysOrgPerson();
			p.setFdId("1183b0b84ee4f581bba001c47a78b2d9");
			currency.setDocCreator(p);
			ser.add(currency);
			TransactionUtils.commit(status);
            return SysAdminTransferResult.OK;
	    }catch (Exception e) {
	        logger.error("EopBasedataCurrencyTransferTask error", e);
			//回滚
			TransactionUtils.rollback(status);
	        return new SysAdminTransferResult(
	                ISysAdminTransferConstant.TASK_RESULT_ERROR, "EopBasedataCurrencyTransferTask error!", e);
	    }
	}

}
