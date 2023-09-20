package com.landray.kmss.third.im.kk.webservice;

import javax.jws.WebService;

import com.landray.kmss.sys.notify.webservice.NotifyTodoAppResult;
import com.landray.kmss.sys.notify.webservice.NotifyTodoGetContext;
import com.landray.kmss.sys.notify.webservice.NotifyTodoGetCountContext;
import com.landray.kmss.sys.webservice2.interfaces.ISysWebservice;

/**
 * 提供对kk的接口
 * @author 孙佳
 * @date 2017年8月14日
 */
@WebService
public interface IThirdImSyncForKKWebService extends ISysWebservice {

	/**
	 * 4.1 ekp握手接口（say hello）
	 */
	public ThirdImKKSyncResult sayHello();

	/**
	 * 4.3 PC应用导出接口
	 */
	public ThirdImKKSyncResult getEkpAppInfo();

	/**
	 * 4.4 移动应用导出接口
	 */
	public ThirdImKKSyncResult getEkpMobileInfo();

	/**
	 * 4.5 SSO配置导出接口
	 */
	public ThirdImKKSyncResult getSSOInfo();

	/**
	 * 4.6 可见性导出接口
	 */
	public ThirdImKKSyncResult getOrgSyncCfgInfo();

	/**
	 * 4.7 人员联系方式修改接口
	 */
	public ThirdImKKSyncResult updateOrgInfo(ThirdImKKOrgSyncContext thirdImKKOrgSyncContext);

	/**
	 * 4.8 获取待办信息(消息接口增加锁字段，如果有锁表示移动端无法打开，获取模块是否配置了移动端)
	 */
	public NotifyTodoAppResult getTodo(NotifyTodoGetContext todoContext) throws Exception;

	/**
	 * 获取用户待办数
	 */
	public NotifyTodoAppResult getTodoCount(NotifyTodoGetCountContext todoContext) throws Exception;

	/**
	 * <p>获取ekp智能助手配置</p>
	 * @return
	 * @author 孙佳
	 */
	public ThirdImKKSyncResult getEkpRobot();

	/**
	 * <p>获取其它应用参数</p>
	 * @return
	 * @author 孙佳
	 */
	public ThirdImKKSyncResult getExtendApp();
}
