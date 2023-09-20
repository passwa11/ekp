package com.landray.kmss.eop.basedata.service;

import org.springframework.scheduling.annotation.Async;

/**
*@author yucf
*@date  2021-1-14
*@Description            业务系统导入（同步到）基础数据模块
*
* @param <T>         EOP推送到业务模块的EOP数据
* @param <R>         业务模块推送数据到EOP
**/

public interface IEopBasedataPullAndPushService<T, R> {
	
	/**
	 * 业务模块主数据ID
	 */
	static final String BIZ_ID = "bizId";
	
//	static final String EXTENSION_POINT_ID = "com.landray.kmss.eop.basedata.io.put";
//	
//	static final String EXTENSION_NAME = "svr";
//	
//	static final String PARAM_NAME_FUNCTION = "function";
//	
//	static final String PARAM_NAME_BEAN = "bean";
	
	/**
	 * 操作动作（add/update/delete)
	 *
	 */
	enum DataAction {
		
		/**
		 * 保存
		 */
		add,
		
		/**
		 * 更新
		 */
		update, 
		
		/**
		 * 删除
		 */
		delete,
		
		/**
		 * 开启
		 */
		enable,
		
		/**
		 * 禁用
		 */
		disable;
		
		
		
		public static DataAction getAction(String code) {
			
			for(DataAction dataAction :  DataAction.values()) {
				if(dataAction.name().equals(code)) {
					return dataAction;
				}
			}
			
			return null;
		}
	}
	


	/**
	 *  同步数据到EOP模块,回填EOPID到业务模块
	 * @return           返回EOP需要的数据类型
	 * @throws Exception
	 */
	R syncData2Eop() throws Exception;
	
	/**
	 * EOP异步数据到业务模块
	 * @param action     操作动作： S/U/D（保存/更新/删除）
	 * @param t          
	 * @throws Exception
	 */
	@Async
	void asyncData2BizMod(DataAction action, T t) throws Exception;
}