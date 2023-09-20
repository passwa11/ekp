package com.landray.kmss.sys.oms.in.interfaces;

/**
 * 兼容旧的OMS同步。新加了一个顶层接口 该接口不能直接实现。
 * 请在实际扩展中继承OMSBaseSynchroInProvider或OMSBaseSynchroInIteratorProvider这两个抽象类
 * OMSBaseSynchroInProvider采用旧的同步算法 OMSBaseSynchroInIteratorProvider采用新的同步算法
 */
public interface OMSSynchroInProvider {
	/**
	 *初始化操作
	 */
	public abstract void init() throws Exception;

	/**
	 *销毁操作
	 */
	public abstract void terminate() throws Exception;

	/**
	 * @return 取得实现键值
	 */
	public abstract String getKey();

	/**
	 * @return 密码类型
	 */
	public abstract int getPasswordType();

	/**
	 * @return 帐号更新类型
	 */
	public abstract int getAccountType();

	/**
	 * @return 取得实现键值与关联值的连接符
	 */
	public abstract String getLinkString(String type);
	
	public boolean isSynchroInEnable() throws Exception;

}
