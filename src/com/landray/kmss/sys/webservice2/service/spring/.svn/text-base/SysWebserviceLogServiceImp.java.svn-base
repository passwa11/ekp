package com.landray.kmss.sys.webservice2.service.spring;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.webservice2.dao.ISysWebserviceLogDao;
import com.landray.kmss.sys.webservice2.model.SysWebserviceLog;
import com.landray.kmss.sys.webservice2.service.ISysWebserviceLogService;
import com.sunbor.web.tag.Page;


/**
 * WebService日志表业务接口实现
 * 
 * @author Jeff
 */
public class SysWebserviceLogServiceImp extends BaseServiceImp implements
		ISysWebserviceLogService {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysWebserviceLogServiceImp.class);
	/**
	 * 查找注册的服务信息
	 */
	@Override
    public SysWebserviceLog findServiceLog(String fdId) throws Exception {
		ISysWebserviceLogDao dao = (ISysWebserviceLogDao) getBaseDao();

		return dao.findServiceLog(fdId);
	}

	
	private ConcurrentHashMap<String,Counter> counters = new ConcurrentHashMap<>(32);
	/**
	 * 检查访问频率的间隔时间，单位ms
	 */
	private int checkAccessFrequencyInterval = 60000;
	
	
	public void setCheckAccessFrequencyInterval(int checkAccessFrequencyInterval) {
        this.checkAccessFrequencyInterval = checkAccessFrequencyInterval;
    }

    /**
	 * 检测客户端的访问频率
	 */
	@Override
    public int countAccessFrequency(String serviceBean, String userName)
			throws Exception {
	    //不使用数据库来查计算频率，改用内存记录的方式
		//ISysWebserviceLogDao dao = (ISysWebserviceLogDao) getBaseDao();
		//return dao.countAccessFrequency(serviceBean, userName);
		String key = serviceBean+"-"+userName;
		long st = System.currentTimeMillis();
		Counter counter = new Counter(st,checkAccessFrequencyInterval);
		counters.putIfAbsent(key, counter);
		int size = counters.get(key).getAndSetCount();
        if(logger.isDebugEnabled()){
            logger.debug("count for key["+key+"] is "+size);
        }
		return size;
	}
	

	/**
	 * 
	 * 简单计数器对象,并不是连续的计数，而是每到指定间隔时间重置一次，所以
	 * 已知存在的误差现象是，第一分钟的计数到99，第二分钟的第一秒就将计数归零，导致计数不准
	 * @author 陈进科
	 * 2019-05-06
	 */
	private static class Counter{
	    private int count = 1;
        private long timestamp = -1;
        private int interval = 60000;
        private Counter(long timestamp,int interval){
            this.timestamp = timestamp;
            this.interval = interval;
        }
        
        /**
         * 获取当前值，并且递增或重置count
         * @return
         */
        public synchronized int getAndSetCount(){
            long currentTimeMillis = System.currentTimeMillis();
            if(timestamp+interval-currentTimeMillis<0){
                //当前时间已经超过指定的统计时间间隔，需要重置计数器
                count = 1;
                timestamp = currentTimeMillis;
            }else{
                count++;
            }
            return count;
        }
	}

	/**
	 * 查询超时预警分页
	 */
	@Override
    public Page findTimeoutPage(String orderBy, int pageno, int rowsize)
			throws Exception {
		ISysWebserviceLogDao dao = (ISysWebserviceLogDao) getBaseDao();

		return dao.findTimeoutPage(orderBy, pageno, rowsize);
	}
}
