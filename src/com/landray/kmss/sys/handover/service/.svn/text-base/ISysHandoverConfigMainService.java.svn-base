package com.landray.kmss.sys.handover.service;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

public interface ISysHandoverConfigMainService extends IBaseService {
	/**
	 * 配置类查询
	 * 
	 */
	public JSONObject search(RequestContext request) throws Exception;

	/**
	 * 配置类处理
	 * 
	 * @param request
	 * @throws Exception
	 */
	public JSONObject execute(RequestContext request) throws Exception;

	/**
	 * 文档类明细表
	 * 
	 * @param request
	 * @param pagedHqlInfo 初始化分页参数之后的hqlInfo
	 * @return
	 * @throws Exception
	 */
    public Page detail(HttpServletRequest request,HQLInfo pagedHqlInfo) throws Exception;
    
    /**
     * 文档类定时作业提交
     * 
     * @param request
     * @throws Exception
     */
    public String submit(RequestContext requestContext) throws Exception;
    
    /**
     *  执行文档交接任务
     * 
     * @param context
     * @throws Exception
     */
    public void executeJob(SysQuartzJobContext context) throws Exception;

	/**
	 * 文档权限交接提交任务
	 * 
	 * @param request
	 * @throws Exception
	 */
	public String submitAuth(RequestContext request) throws Exception;

	/**
	 * 执行文档权限交接任务
	 * 
	 * @throws Exception
	 */
	public void executeAuth() throws Exception;

}
