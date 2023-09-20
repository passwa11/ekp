package com.landray.kmss.sys.transport.service;

import org.apache.poi.ss.usermodel.Row;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.transport.service.spring.ImportContext;
import com.landray.kmss.sys.transport.service.spring.ImportProperty;

/**
 * 
 * @author 潘永辉 2017年3月10日
 *         <p>
 * 
 *         导入机制额外提示的处理器，用于在导入时修改HQL语句及参数值
 *         <p>
 * 
 *         导入机制大概的逻辑如下：<br>
 *         <li>1、拼接“主数据关键字属性查询条件”</li>
 *         <li>2、拼接“主数据关键字属性查询参数”</li> <br>
 *         通过以上2步，可以判断导入的数据是否存在<br>
 *         <li>3、设置导入的数据</li>
 *         <li>4、保存数据</li>
 * 
 *         <p>
 *         现在有一个问题就是在导入组织机构时有一个“全路径”的主数据关键字属性，这个“全路径”并不是数据库对应的字段，<br>
 *         而是model里的一个方法，所以按以前的逻辑会有问题，为了解决这个问题，这里扩展出此类，方便业务模块去处理这些非数据库字段的属性。
 *         <p>
 *         具体实现可以参考：{@link com.landray.kmss.sys.organization.service.spring.SysOrgElementServiceImp}
 *
 */
public interface ISysTransportProvider extends ISysTransportNoticeProvider{
	/**
	 * 主数据关键字属性查询条件
	 * 
	 * @param commonProperty
	 * @return
	 */
	public String
			handlePrimaryKeyPropertyName(SysDictCommonProperty commonProperty);

	/**
	 * 主数据关键字属性查询参数
	 * 
	 * @param commonProperty
	 * @return
	 */
	public Object handlePrimaryKeyPropertyValue(
			SysDictCommonProperty commonProperty,
			Object value);

	/**
	 * 导入数据时的具体数据
	 * 
	 * @param commonProperty
	 * @return
	 */
	public IBaseModel
			getModelByImportProperty(
					ImportContext context, ImportProperty importProperty,
					Row row, HQLInfo hqlInfo) throws Exception;

	/**
	 * 获取编辑页面的扩展的操作文件，如无需操作，返回null
	 * 
	 * @return 返回操作文件的JSP路径
	 */
	public String getEditExtendPath();
}
