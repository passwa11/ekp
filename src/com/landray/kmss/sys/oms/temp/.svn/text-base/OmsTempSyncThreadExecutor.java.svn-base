package com.landray.kmss.sys.oms.temp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.TransactionUtils;

public class OmsTempSyncThreadExecutor {
	private static final Log logger = LogFactory.getLog(OmsTempSyncThreadExecutor.class);
	
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	public void setThreadPoolTaskExecutor(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
		this.threadPoolTaskExecutor = threadPoolTaskExecutor;
	}

	/**
	 * 
	 * 执行多线程且事务分批处理
	 * @param type 传递到listThreadHandler中的type
	 * @param allList 循环执行的list，将本list传递到listThreadHandler中的allList，将list元素传递到listThreadHandler中的bean
	 * @param threadSize 线程数量
	 * @param transSize 事务大小
	 * @param service 当前同步功能的serviceBean
	 * @param log 日志
	 * @param otherParams 其它透传参数
	 */
	public <T> void listThreadExecute(String type, List<T> allList, int threadSize, int transSize, ISysOmsThreadSynchService service,Object...otherParams){
		if(ArrayUtil.isEmpty(allList)) {
            return;
        }
		if(threadSize <= 0) {
            threadSize = 1;
        }
		
		int total = allList.size();
		//总数等于或少于线程数，则不执行多线程
		if (total <= threadSize || total <= 1000) {
            threadSize = 1;
        }
		//每个线程执行几条
		int each = total / threadSize;
		//余数(最大值为线程数-1)
		int left = total % threadSize;
		if (logger.isDebugEnabled()) {
            logger.debug("["+type+"]总数据:"+total+"条,将分批执行"+transSize+"次同步,每次大约"+each+"条");
        }
		CountDownLatch countDownLatch = new CountDownLatch(threadSize);
		List<T> temp = null;
		for (int i = 0; i < threadSize; i++) {
			int begin = each * i;
			int end = begin + each;
			//首批把余数加上一起执行
			if (i == 0) {
				end += left;
			}
			//非首批从余数之后开始
			else {
				begin += left;
				end += left;
			}
			temp = allList.subList(begin, end);
			if (logger.isDebugEnabled()) {
                logger.debug("执行第"+(i + 1)+"/"+threadSize+"批，共"+ (end - begin)+"条");
            }
			threadPoolTaskExecutor.execute(new ThransRunner<T>(type, temp, transSize, service, countDownLatch, otherParams));
		}
		try {
			countDownLatch.await(12, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			logger.warn("批处理等待时间超长：",e);
		}
	}
	
	/**
	 * 
	 * 执行多线程且事务分批处理
	 * @param type 传递到listThreadHandler中的type
	 * @param allList 循环执行的list，将本list传递到listThreadHandler中的allList，将list元素传递到listThreadHandler中的bean
	 * @param threadSize 线程数量
	 * @param transSize 事务大小
	 * @param service 当前同步功能的serviceBean
	 * @param log 日志
	 * @param otherParams 其它透传参数
	 */
	public <T> void listThreadExecute(String type, List<T> allList, int threadSize,ISysOmsThreadSynchService service,Object...otherParams){
		this.listThreadExecute(type, allList, threadSize,50, service, otherParams);
	}
	
	/**
	 * 
	 * 执行多线程且事务分批处理
	 * @param type 传递到listThreadHandler中的type
	 * @param allList 循环执行的list，将本list传递到listThreadHandler中的allList，将list元素传递到listThreadHandler中的bean
	 * @param threadSize 线程数量
	 * @param transSize 事务大小
	 * @param service 当前同步功能的serviceBean
	 * @param log 日志
	 * @param otherParams 其它透传参数
	 */
	public <T> void listThreadExecute(String type, List<T> allList,ISysOmsThreadSynchService service,Object...otherParams){
		this.listThreadExecute(type, allList, 10,50, service, otherParams);
	}
	
	/**
	 * 将allList根据size切分成多个事务分别执行，并将每批次中的每一条数据的业务逻辑通过service.listThreadHandler执行
	 * @param type
	 * @param allList
	 * @param transSize 一批事务的大小
	 */
	protected <T> void listTransExecute(String type, List<T> allList, int transSize, ISysOmsThreadSynchService service, Object...otherParams) {
		if (ArrayUtil.isEmpty(allList)) {
			if (logger.isDebugEnabled()) {
                logger.debug("列表为空，不执行");
            }
			return;
		}
		List<List<T>> batchs = splitListBatch(allList, transSize);
		TransactionStatus status = null;
		StopWatch stopWatch = null;
		for (List<T> batch : batchs) {
			try {
				stopWatch = new StopWatch();
				stopWatch.start();
				status = TransactionUtils.beginNewTransaction();
				for (T t : batch) {
					service.listThreadHandler(type, allList, t, otherParams);
				}
				TransactionUtils.commit(status);
			} catch (Exception e) {
				logger.error("---事务出错---"+type,e);
				if (status != null) {
					try {
						TransactionUtils.rollback(status);
					} catch (Exception ex) {
						logger.error("---事务回滚出错---"+type,ex);
					}
				}
			} finally {
				long time = -1;
				if (stopWatch != null) {
					stopWatch.stop();
					time = stopWatch.getTime();
				}
				if (logger.isDebugEnabled()) {
                    logger.debug(status.hashCode()+" "+type+"事务完成，耗时"+time+"ms---");
                }
			}
		}
	}
	
	/**
	 * 将allList根据size切分成多个事务分别执行，并将每批次中的每一条数据的业务逻辑通过service.listThreadHandler执行
	 * @param type
	 * @param allList
	 * @param transSize 一批事务的大小
	 */
	protected <T> void listTransExecute(String type, List<T> allList, ISysOmsThreadSynchService service,Object...otherParams) {
		if (ArrayUtil.isEmpty(allList)) {
			if (logger.isDebugEnabled()) {
                logger.debug("列表为空，不执行");
            }
			return;
		}
		TransactionStatus status = null;
		StopWatch stopWatch = null;
		try {
			stopWatch = new StopWatch();
			stopWatch.start();
			status = TransactionUtils.beginNewTransaction();
			service.listThreadHandler(type, allList,null, otherParams);
			TransactionUtils.commit(status);
		} catch (Exception e) {
			logger.error("---事务出错---"+type,e);
			if (status != null) {
				try {
					TransactionUtils.rollback(status);
				} catch (Exception ex) {
					logger.error("---事务回滚出错---"+type,ex);
				}
			}
		} finally {
			long time = -1;
			if (stopWatch != null) {
				stopWatch.stop();
				time = stopWatch.getTime();
			}
			if (logger.isDebugEnabled()) {
                logger.debug(status.hashCode()+" "+type+"事务完成，耗时"+time+"ms---");
            }
		}
	}
	
	/**
	 * 多线程处理
	 */
	class ThransRunner<T> implements Runnable {
		private String type;
		private final List<T> allList;
		private int transSize;
		private ISysOmsThreadSynchService service;
		private CountDownLatch countDownLatch;
		private Object[] otherParams;

		public ThransRunner(String type, List<T> allList, int transSize, ISysOmsThreadSynchService service,
				CountDownLatch countDownLatch, Object...otherParams) {
			this.allList = allList;
			this.type = type;
			this.transSize = transSize;
			this.service = service;
			this.countDownLatch = countDownLatch;
			this.otherParams = otherParams;
		}

		@Override
		public void run() {
			try {
				if(transSize == 0){
					listTransExecute(type, allList, service,otherParams);
				}else{
					listTransExecute(type, allList, transSize, service, otherParams);
				}
			
			} catch (Exception e) {
				logger.error("分批处理时发生异常：", e);
			} finally {
				countDownLatch.countDown();
			}
		}
	}
	
	private static <T> List<List<T>> splitListBatch(List<T> list, int size) {
		List<List<T>> result = new ArrayList<>();
		int totle = list.size();
		int count = totle % size == 0 ? totle / size : totle / size + 1;
		if (logger.isDebugEnabled()) {
            logger.debug("总共" + totle + "条,分为" + count + "批,每批最多" + size + "条");
        }
		List<T> temp = null;
		for (int i = 0; i < count; i++) {
			if (totle > size * (i + 1)) {
				temp = list.subList(size * i, size * (i + 1));
			} else {
				temp = list.subList(size * i, totle);
			}
			result.add(temp);
		}
		return result;
	}
}
