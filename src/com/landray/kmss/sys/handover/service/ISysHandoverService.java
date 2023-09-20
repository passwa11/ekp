package com.landray.kmss.sys.handover.service;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.handover.model.SysHandoverConfigMain;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONObject;

/**
 * 工作交接查询、处理接口
 * 
 * @author tanyouhao
 * @date 2014-7-23
 */
public interface ISysHandoverService {

	/**
	 * 查询数据
	 * 
	 * @param org
	 *            交接人
	 * @param module
	 *            模块key
	 * @param context
	 * @return
	 */
	public JSONObject configHandoverSearch(SysOrgElement fromOrgElement,
			SysOrgElement toOrgElement, String module,
			RequestContext context) throws Exception;

	/**
	 * 处理数据
	 * 
	 * @param from
	 * @param to
	 *            接收人
	 * @param type
	 *            模块key
	 * @param context
	 */
	public JSONObject configHandoverExecute(SysOrgElement from, SysOrgElement to,
			String module, RequestContext context) throws Exception;
	
	
	/**
	 *  文档类查询明细
	 * 
	 * @param hqlInfo
	 * @param org
	 * @param module
	 * @param item
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public Page detail(HQLInfo hqlInfo,SysOrgElement org, String module,String item,
			RequestContext context) throws Exception;

	/**
	 * 文档类交接
	 * 返回成功交接文档记录数
	 * 
	 * @param moduleObject
	 * @param handMain
	 * @throws Exception
	 */
	public long docHandoverExecute(JSONObject moduleObject,SysHandoverConfigMain handMain) throws Exception;
	
	/**
	 * 文档类交接
	 * 返回成功交接文档记录数
	 * 
	 * @param moduleObject
	 * @param handMain
	 * @throws Exception
	 */
	public long docHandoverExecute(JSONObject moduleObject,SysHandoverConfigMain handMain, String type) throws Exception;

}
