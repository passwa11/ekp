package com.landray.kmss.sys.oms.in;

import java.util.Iterator;

import com.landray.kmss.component.locker.interfaces.ConcurrencyException;
import com.landray.kmss.component.locker.interfaces.IComponentLockService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.oms.OMSPlugin;
import com.landray.kmss.sys.oms.in.interfaces.IOMSSynchroInIncrementProvider;
import com.landray.kmss.sys.oms.in.interfaces.IOMSSynchroInIteratorProvider;
import com.landray.kmss.sys.oms.in.interfaces.IOMSSynchroInOtherProvider;
import com.landray.kmss.sys.oms.in.interfaces.IOMSSynchroInProvider;
import com.landray.kmss.sys.oms.in.interfaces.OMSSynchroInProvider;
import com.landray.kmss.sys.oms.notify.service.ISynchroOrgNotify;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * 创建日期 2006-11-30
 *
 * @author 吴兵 接入第三方平台定时同步服务
 */

public class OMSSynchroInService implements IOMSSynchroInService {

	private static boolean locked = false;

	private IOMSSynchroInProviderRunner providerRunner;

	private IOMSSynchroInIteratorProviderRunner secondProviderRunner;

	private IOMSSynchroInIncrementProviderRunner incrementProviderRunner;

	private ISynchroOrgNotify synchroOrgNotify;

	private IOMSSynchroInOtherProviderRunner otherProviderRunner;

	public void setOtherProviderRunner(IOMSSynchroInOtherProviderRunner otherProviderRunner) {
		this.otherProviderRunner = otherProviderRunner;
	}

	public void setProviderRunner(IOMSSynchroInProviderRunner providerRunner) {
		this.providerRunner = providerRunner;
	}

	public void setSecondProviderRunner(
			IOMSSynchroInIteratorProviderRunner secondProviderRunner) {
		this.secondProviderRunner = secondProviderRunner;
	}

	public void setSynchroOrgNotify(ISynchroOrgNotify synchroOrgNotify) {
		this.synchroOrgNotify = synchroOrgNotify;
	}

	IComponentLockService componentLockService = null;

	private IComponentLockService getComponentLockService() {
		if (componentLockService == null) {
			componentLockService = (IComponentLockService) SpringBeanUtil
					.getBean("componentLockService");
		}
		return componentLockService;
	}

	@Override
	public void synchro(SysQuartzJobContext context) {
		OMSSynchroInModel model = new OMSSynchroInModel();
		try {
			getComponentLockService().tryLock(model, "omsIn");
			doSynchro(context);

			getComponentLockService().unLock(model);
		} catch (ConcurrencyException e) {
			context.logError(
					"已经有同步任务正在执行，如果是由于同步过程中重启等原因导致定时任务被锁，需到“后台配置”->“应用中心”->“机制”->“锁机制”中释放锁",
					e);
		} catch (Exception e) {
			getComponentLockService().unLock(model);
		} finally {
			// getComponentLockService().unLock(model);
		}
	}

	private void doSynchro(SysQuartzJobContext context) {
		try {
			boolean hasError = false;
			for (Iterator<OMSSynchroInProvider> iter = OMSPlugin
					.getInExtensionMap().values().iterator(); iter.hasNext();) {
				try {
					OMSSynchroInProvider provider = iter.next();
					if(!provider.isSynchroInEnable()){
						continue;
					}
					if (provider instanceof IOMSSynchroInProvider) {
						// 旧的代码逻辑
						providerRunner.synchro(
								(IOMSSynchroInProvider) provider, context);
					} else if (provider instanceof IOMSSynchroInIteratorProvider) {
						// 新的代码处理逻辑
						secondProviderRunner.synchro(
								(IOMSSynchroInIteratorProvider) provider,
								context);
					} else if (provider instanceof IOMSSynchroInIncrementProvider) {
						incrementProviderRunner.synchro(
								(IOMSSynchroInIncrementProvider) provider,
								context);
					} else if (provider instanceof IOMSSynchroInOtherProvider) {
						otherProviderRunner.synchro((IOMSSynchroInOtherProvider) provider, context);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					hasError = true;
					if (context != null) {
						context.logError(ex);
					}
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
					if (context != null) {
						context.logError("组织架构接入通知发送失败", ex);
					}
				}
			}
		} catch (Exception e) {
			context.logError(e);
		}
	}

	public IOMSSynchroInIncrementProviderRunner getIncrementProviderRunner() {
		return incrementProviderRunner;
	}

	public void setIncrementProviderRunner(IOMSSynchroInIncrementProviderRunner incrementProviderRunner) {
		this.incrementProviderRunner = incrementProviderRunner;
	}
}
