package com.landray.kmss.sys.transport.service;

import com.landray.kmss.common.model.IBaseModel;

public interface ISysTransportImport {
	/**
	 * 自定义导入时添加插入数据的方法
	 * 
	 */
  public void addImport(IBaseModel modelObj)throws Exception ;
  /**
	 * 自定义导入时添加更新数据的方法
	 * 
	 */
  public void updateImport(IBaseModel modelObj)throws Exception ;
}
