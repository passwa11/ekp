package com.landray.kmss.sys.oms.out;

import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.log.service.spring.KmssJobLogger;
import com.landray.kmss.sys.log.service.spring.KmssJobLoggerFactoryBean;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.oms.OMSPlugin;
import com.landray.kmss.sys.oms.notify.service.ISynchroOrgNotify;
import com.landray.kmss.sys.oms.out.interfaces.IOMSSynchroOutContext;
import com.landray.kmss.sys.oms.out.interfaces.IOMSSynchroOutProvider;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

/**
 * OMS组织机构同步接入工厂
 * 
 * @author 吴兵
 * @version 1.0 2006-11-29
 */
public class OMSSynchroOutService implements IOMSSynchroOutService,
		SysOrgConstant {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(OMSSynchroOutService.class);

	private static boolean locked = false;

	private KmssJobLoggerFactoryBean kmssJobLoggerFactory;

	private ISynchroOrgNotify synchroOrgNotify;

	private IOMSSynchroOutContext synchroOutContext;

	@Override
	public void incSynchro(SysQuartzJobContext context) throws Exception {
		if (locked) {
            return;
        }
		locked = true;
		try {
			boolean hasError = false;
			Map<String, IOMSSynchroOutProvider> providers = OMSPlugin
					.getOutExtensionMap();
			for (Iterator<String> iter = providers.keySet().iterator(); iter
					.hasNext();) {
				String key = iter.next();
				try {
					IOMSSynchroOutProvider provider = providers.get(key);
					if(!provider.isSynchroOutEnable()){
						continue;
					}
					IOMSSynchroOutContext synchroOutContext_new = (IOMSSynchroOutContext)synchroOutContext.clone();
					synchroOutContext_new.setAppName(key);
					synchroOutContext_new.initIncContext();
					
					if(provider.isSynchroOutEnable()){
						providers.get(key).synchro(synchroOutContext_new, context);
					}
				} catch (Exception ex) {
					hasError = true;
					context.logError(ex);
				}
			}
			if (hasError) {
				try {
					NotifyContext notifyContext = synchroOrgNotify
							.getSyncExceptionNotifyContext();
					notifyContext
							.setLink("/sys/log/sys_log_job/index.jsp");
					synchroOrgNotify.send(null, notifyContext, null);
				} catch (Exception ex) {
					context.logError("组织架构接出通知发送失败", ex);
				}
			}
		} finally {
			locked = false;
		}
	}

	@Override
	public void allSynchro(String appName) {
		if (locked) {
            return;
        }
		locked = true;
		KmssJobLogger jobLogger = null;
		try {
			boolean hasError = false;
			IOMSSynchroOutProvider provider = OMSPlugin.getOutExtensionMap()
					.get(appName);
			if (provider == null) {
                return;
            }
			if(!provider.isSynchroOutEnable()){
				return;
			}
			jobLogger = kmssJobLoggerFactory.createInstance(
					OMSSynchroOutService.class,
					"==========OMS接出同步用户和组织机构开始==========");
			SysQuartzJobContext jobContext = new JobContext(jobLogger);
			if (logger.isDebugEnabled()) {
				logger.debug("========" + appName + "=========");
			}
			long time = System.currentTimeMillis();
			try {
				synchroOutContext.setAppName(appName);
				synchroOutContext.initAllContext();
				provider.synchro(synchroOutContext, jobContext);
				
			} catch (Exception ex) {
				hasError = true;
				jobLogger.logError(ex);
			}
			if (hasError) {
				try {
					NotifyContext notifyContext = synchroOrgNotify
							.getSyncExceptionNotifyContext();
					notifyContext
							.setLink("/sys/log/sys_log_error/sysLogError.do?method=list");
					synchroOrgNotify.send(null, notifyContext, null);
				} catch (Exception ex) {
					logger.error("", ex);
				}
			}
			jobLogger.logMessage("==========OMS接出同步用户和组织机构结束==========");
			if (logger.isDebugEnabled()) {
				logger.debug("OMS接出同步用户和组织机构总耗时："
						+ ((System.currentTimeMillis() - time) / 1000) + " s");
			}
			jobLogger.logMessage("OMS接出同步用户和组织机构总耗时："
					+ ((System.currentTimeMillis() - time) / 1000) + " s");
		} catch (Exception ex) {
			logger.error("", ex);
		} finally {
			locked = false;
			if (jobLogger != null) {
                jobLogger.logEnd();
            }
		}
	}

	public void setSynchroOrgNotify(ISynchroOrgNotify synchroOrgNotify) {
		this.synchroOrgNotify = synchroOrgNotify;
	}

	public void setSynchroOutContext(IOMSSynchroOutContext synchroOutContext) {
		this.synchroOutContext = synchroOutContext;
	}

	public void setKmssJobLoggerFactory(
			KmssJobLoggerFactoryBean kmssJobLoggerFactory) {
		this.kmssJobLoggerFactory = kmssJobLoggerFactory;
	}

	private class JobContext implements SysQuartzJobContext {
		private KmssJobLogger jobLogger;

		private JobContext(KmssJobLogger jobLogger) {
			this.jobLogger = jobLogger;
		}

		@Override
		public Logger getLogger() {
			return logger;
		}

		@Override
		public String getParameter() {
			return null;
		}

		@Override
		public void logError(String errMsg) {
			jobLogger.logError(errMsg);
		}

		@Override
		public void logError(Throwable e) {
			jobLogger.logError(e);
		}

		@Override
		public void logError(String errMsg, Throwable e) {
			jobLogger.logError(errMsg);
			jobLogger.logError(e);
		}

		@Override
		public void logMessage(String msg) {
			jobLogger.logMessage(msg);
		}

		@Override
		public void setLogger(Logger logger) {
		}
	}
}
