package com.landray.kmss.sys.oms.temp;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public interface ISysOmsThreadSynchService{
	/**
	 * 执行多线程事务分批中的每一条数据的业务逻辑分发
	 * @param type
	 * @param allList
	 * @param bean
	 * @param otherParams
	 */
	public abstract <T> void listThreadHandler(String type, List<T> allList, T bean, Object...otherParams);


}
