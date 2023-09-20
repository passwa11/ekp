package com.landray.kmss.sys.oms.actions;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.component.dbop.ds.DSAction;
import com.landray.kmss.component.dbop.ds.DSTemplate;
import com.landray.kmss.component.dbop.ds.DataSet;
import com.landray.kmss.component.dbop.model.CompDbcp;
import com.landray.kmss.component.dbop.service.ICompDbcpService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.oms.forms.SysOmsTempTrxForm;
import com.landray.kmss.sys.oms.model.SysOmsTempDept;
import com.landray.kmss.sys.oms.model.SysOmsTempPerson;
import com.landray.kmss.sys.oms.model.SysOmsTempPost;
import com.landray.kmss.sys.oms.model.SysOmsTempTrx;
import com.landray.kmss.sys.oms.service.ISysOmsTempDeptService;
import com.landray.kmss.sys.oms.service.ISysOmsTempPersonService;
import com.landray.kmss.sys.oms.service.ISysOmsTempPostService;
import com.landray.kmss.sys.oms.service.ISysOmsTempTrxService;
import com.landray.kmss.sys.oms.service.ISysOmsTempTrxSynFailService;
import com.landray.kmss.sys.oms.temp.OmsTempSynResult;
import com.landray.kmss.sys.oms.temp.SyncLog;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

public class SysOmsTempTrxAction extends ExtendAction {

    private ISysOmsTempTrxService sysOmsTempTrxService;

    @Override
	public IBaseService getServiceImp(HttpServletRequest request) {
        if (sysOmsTempTrxService == null) {
            sysOmsTempTrxService = (ISysOmsTempTrxService) getBean("sysOmsTempTrxService");
        }
        return sysOmsTempTrxService;  
    }
    
    private ISysOmsTempTrxSynFailService sysOmsTempTrxSynFailService;

    public ISysOmsTempTrxSynFailService getSysOmsTempTrxSynFailService() {
        if (sysOmsTempTrxSynFailService == null) {
        	sysOmsTempTrxSynFailService = (ISysOmsTempTrxSynFailService) getBean("sysOmsTempTrxSynFailService");
        }
        return sysOmsTempTrxSynFailService;  
    }
    
    private ISysOmsTempDeptService sysOmsTempDeptService;
    public ISysOmsTempDeptService getSysOmsTempDeptService() {
        if (sysOmsTempDeptService == null) {
        	sysOmsTempDeptService = (ISysOmsTempDeptService) getBean("sysOmsTempDeptService");
        }
        return sysOmsTempDeptService;  
    }
    
    private ISysOmsTempPersonService sysOmsTempPersonService;
    public ISysOmsTempPersonService getSysOmsTempPersonService() {
        if (sysOmsTempPersonService == null) {
        	sysOmsTempPersonService = (ISysOmsTempPersonService) getBean("sysOmsTempPersonService");
        }
        return sysOmsTempPersonService;  
    }
    
    private ISysOmsTempPostService sysOmsTempPostService;
    public ISysOmsTempPostService getSysOmsTempPostService() {
        if (sysOmsTempPostService == null) {
        	sysOmsTempPostService = (ISysOmsTempPostService) getBean("sysOmsTempPostService");
        }
        return sysOmsTempPostService;  
    }
    
    protected ICompDbcpService compDbcpService;

	protected ICompDbcpService getCompDbcpService() {
		if (compDbcpService == null) {
            compDbcpService = (ICompDbcpService) getBean("compDbcpService");
        }
		return compDbcpService;
	}
	
    @Override
	public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, SysOmsTempTrx.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        String where = hqlInfo.getWhereBlock();
		//之所以重写ekp的 搜索条件，是为了兼容 sqlserver 问题
		if(where != null && where.contains("like")){
			String[] wherearr = where.split("like");
			String where2 = " sysOmsTempTrx.fdLogDetail like" + wherearr[1];
			hqlInfo.setWhereBlock(where2);
		}
		where = StringUtil.linkString(hqlInfo.getWhereBlock(), "",
				"");
		hqlInfo.setWhereBlock(where);
        com.landray.kmss.sys.oms.util.SysOmsUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.sys.oms.model.SysOmsTempTrx.class);
        com.landray.kmss.sys.oms.util.SysOmsUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
	public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        SysOmsTempTrxForm sysOmsTempTrxForm = (SysOmsTempTrxForm) super.createNewForm(mapping, form, request, response);
        ((ISysOmsTempTrxService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return sysOmsTempTrxForm;
    }

	/**
     * 数据源表名
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
   	public void getDataSourceTableName(ActionMapping mapping, ActionForm form,
   			HttpServletRequest request, HttpServletResponse response)
   			throws Exception {
   		response.setCharacterEncoding("UTF-8");	
  		String fdSourceName = request.getParameter("fdSourceName");
   	   JSONObject jsonObject = new JSONObject();
   	   PrintWriter printWriter = response.getWriter();
		try {
			List<String> list = getDataSourceTableName(fdSourceName);
			jsonObject.put("success", true);
	   	    jsonObject.put("data", list);
	   	    jsonObject.put("message", "");  
	   	    printWriter.print(jsonObject);
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("data", null);
			jsonObject.put("message", e.getMessage());  
			printWriter.print(jsonObject);
			log.error(e.toString());
		}finally{
			printWriter.close();
		}
		
   	}
    
    /**
     * 数据源表字段名
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
   	public void getTableColumName(ActionMapping mapping, ActionForm form,
   			HttpServletRequest request, HttpServletResponse response)
   			throws Exception {
   		response.setCharacterEncoding("UTF-8");	
   		String fdSourceName = request.getParameter("fdSourceName");
   		String fdTableName = request.getParameter("fdTableName");
   	   JSONObject jsonObject = new JSONObject();
   	   PrintWriter printWriter = response.getWriter();
		try {
			List<String> list = getTableColumName(fdSourceName,fdTableName);
			jsonObject.put("success", true);
	   	    jsonObject.put("data", list);
	   	    jsonObject.put("message", "");  
	   	    printWriter.print(jsonObject);
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("data", null);
			jsonObject.put("message", e.getMessage());  
			printWriter.print(jsonObject);
			log.error(e.toString());
		}finally{
			printWriter.close();
		}
		
   	}
   	
   	private String getDbName(CompDbcp dbcp) throws Exception{
   		String dbName = null;
		String[] urlArr = dbcp.getFdUrl().split("/");
		if(urlArr != null && urlArr.length > 0){
			dbName = urlArr[urlArr.length-1];
		}
		if(!dbName.contains("?")) {
            return dbName;
        }
		
		urlArr = dbName.split("\\?");
		if(urlArr != null && urlArr.length > 0){
			dbName = urlArr[0];
		}
		return dbName;
   	}
   	
    private List<String> getTableColumName(String fdSourceName,String tableName) throws Exception{
    	CompDbcp dbcp = (CompDbcp) getCompDbcpService().getCompDbcpByName(fdSourceName);
		@SuppressWarnings("unchecked")
		List<String> result = (List<String>) DSTemplate.execute(fdSourceName, new DSAction<String>(tableName+";"+dbcp.getFdType()) {
			@Override
			public List<String> doAction(DataSet ds, String type) throws Exception {
				String[] schemas = type.split(";");
				String schema = null;
				if("MS SQL Server".equals(schemas[1])){
					schema = "dbo";
				}else if("Oracle".equals(schemas[1])){
					schema = ds.getConnection().getSchema();
				}
				List<String> tableNameList = new ArrayList<String>();
				DatabaseMetaData meta = ds.getConnection()
						.getMetaData();
				ResultSet rs = meta.getColumns(null, schema, schemas[0], null);
				while(rs.next()){
			        String name = rs.getString("COLUMN_NAME");
			        tableNameList.add(name);
				}
				return tableNameList;
			}
		});
		
		return result;
	}
	
	private List<String> getDataSourceTableName(String fdSourceName) throws Exception{
   		CompDbcp dbcp = (CompDbcp) getCompDbcpService().getCompDbcpByName(fdSourceName);
		@SuppressWarnings("unchecked")
		List<String> result = (List<String>) DSTemplate.execute(fdSourceName, new DSAction<String>(dbcp.getFdType()) {
			@Override
			public List<String> doAction(DataSet ds, String type) throws Exception {
				String schema = null;
				if("MS SQL Server".equals(type)){
					schema = "dbo";
				}else if("Oracle".equals(type)){
					schema = ds.getConnection().getSchema();
				}
				List<String> tableNameList = new ArrayList<String>();
				DatabaseMetaData meta = ds.getConnection()
						.getMetaData();
				ResultSet rs = meta.getTables(null, schema, "%",
						new String[] { "TABLE","VIEW"});
				while(rs.next()){
			        tableNameList.add(rs.getString("table_name"));
				}
				return tableNameList;
			}
		});
		
		return result;
	}
	
	
	/**
	 * 查询列表JSON页面。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回data页面，否则返回failure页面
	 * @throws Exception
	 */
	@Override
	public ActionForward data(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0 && Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0 && Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			changeFindPageHQLInfo(request, hqlInfo);
//			String fdSynStatus = request.getParameter("q.fdSynStatus");
//			if (StringUtil.isNotNull(fdSynStatus) && !"00".equals(fdSynStatus)) {
//				String where = StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
//						"fdSynStatus=:fdSynStatus");
//	        	hqlInfo.setWhereBlock(where);
//	        	hqlInfo.setParameter("fdSynStatus",Integer.valueOf(fdSynStatus));
//			}
			Page page = getServiceImp(request).findPage(hqlInfo);
			UserOperHelper.logFindAll(page.getList(), getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("data", mapping, form, request, response);
		}
	}
	
    /**
     * 同步部门
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
   	public void synDept(ActionMapping mapping, ActionForm form,
   			HttpServletRequest request, HttpServletResponse response)
   			throws Exception {
   	   response.setCharacterEncoding("UTF-8");	
   	   String fdDeptId = request.getParameter("fdDeptId");
   	   JSONObject jsonObject = new JSONObject();
   	   PrintWriter printWriter = response.getWriter();
	   try {
		   SysOmsTempDept sysOmsTempDept = (SysOmsTempDept) getSysOmsTempDeptService().findByPrimaryKey(fdDeptId);
		   getSysOmsTempTrxSynFailService().syncDept(sysOmsTempDept);
			jsonObject.put("success", true);
	   	    jsonObject.put("data", null);
	   	    jsonObject.put("message", "同步成功");  
	   	    printWriter.print(jsonObject);
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("data", null);
			jsonObject.put("message", "同步失败："+e.getMessage());  
			printWriter.print(jsonObject);
			log.error(e.toString());
		}finally{
			printWriter.close();
		}
		
   	}
   	
   	/**
     * 同步人员
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
   	public void synPerson(ActionMapping mapping, ActionForm form,
   			HttpServletRequest request, HttpServletResponse response)
   			throws Exception {
   	   response.setCharacterEncoding("UTF-8");	
   	   String fdPersonId = request.getParameter("fdPersonId");
   	   JSONObject jsonObject = new JSONObject();
   	   PrintWriter printWriter = response.getWriter();
	   try {
		   SysOmsTempPerson sysOmsTempPerson = (SysOmsTempPerson) getSysOmsTempPersonService().findByPrimaryKey(fdPersonId);
		   getSysOmsTempTrxSynFailService().syncPerson(sysOmsTempPerson);
			jsonObject.put("success", true);
	   	    jsonObject.put("data", null);
	   	    jsonObject.put("message", "同步成功");  
	   	    printWriter.print(jsonObject);
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("data", null);
			jsonObject.put("message","同步失败："+ e.getMessage());  
			printWriter.print(jsonObject);
			log.error(e.toString());
		}finally{
			printWriter.close();
		}
		
   	}
   	
 	/**
     * 同步岗位
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
   	public void synPost(ActionMapping mapping, ActionForm form,
   			HttpServletRequest request, HttpServletResponse response)
   			throws Exception {
   	   response.setCharacterEncoding("UTF-8");	
   	   String fdPostId = request.getParameter("fdPostId");
   	   JSONObject jsonObject = new JSONObject();
   	   PrintWriter printWriter = response.getWriter();
	   try {
		   SysOmsTempPost sysOmsTempPost = (SysOmsTempPost) getSysOmsTempPostService().findByPrimaryKey(fdPostId);
		   getSysOmsTempTrxSynFailService().syncPost(sysOmsTempPost);
			jsonObject.put("success", true);
	   	    jsonObject.put("data", null);
	   	    jsonObject.put("message", "同步成功");  
	   	    printWriter.print(jsonObject);
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("data", null);
			jsonObject.put("message","同步失败："+ e.getMessage());  
			printWriter.print(jsonObject);
			log.error(e.toString());
		}finally{
			printWriter.close();
		}
		
   	}
   	
 	/**
     * 再次同步
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
   	public void synAgin(ActionMapping mapping, ActionForm form,
   			HttpServletRequest request, HttpServletResponse response)
   			throws Exception {
   	   response.setCharacterEncoding("UTF-8");	
   	   String fdTrxId = request.getParameter("fdTrxId");
   	   JSONObject jsonObject = new JSONObject();
   	   PrintWriter printWriter = response.getWriter();
	   try {
		   SysOmsTempTrx sysOmsTempTrx = (SysOmsTempTrx) getServiceImp(request).findByPrimaryKey(fdTrxId);
			OmsTempSynResult<Object> result = new OmsTempSynResult<Object>();
			SyncLog log = new SyncLog();//日志对象
		    getSysOmsTempTrxSynFailService().doSync(sysOmsTempTrx, result, log);
			jsonObject.put("success", true);
	   	    jsonObject.put("data", null);
	   	    jsonObject.put("message", "同步成功");  
	   	    printWriter.print(jsonObject);
		} catch (Exception e) {
			jsonObject.put("success", false);
			jsonObject.put("data", null);
			jsonObject.put("message","同步失败："+ e.getMessage());  
			printWriter.print(jsonObject);
			log.error(e.toString());
		}finally{
			printWriter.close();
		}
		
   	}
   	
   	/**
     * 详细日志下载
     */
    public ActionForward downloadLog(ActionMapping mapping, ActionForm form, HttpServletRequest request,
    		HttpServletResponse response) throws Exception {
    	String fdId = request.getParameter("fdId");
    	String type = request.getParameter("type"); 
    	if(StringUtil.isNull(fdId)) {
            throw new IllegalArgumentException("required argument:fdId");
        }
    	if(StringUtil.isNull(type)) {
            throw new IllegalArgumentException("required argument:type");
        }
    	
		SysOmsTempTrx model = (SysOmsTempTrx) getServiceImp(request)
				.findByPrimaryKey(fdId);
		byte[] bytes = null;
		String fileType = null; 
		if ("detail".equals(type)) {
			fileType = "详细日志";
			bytes = model.getFdLogDetail().getBytes("UTF-8");
			
		} else if ("error".equals(type)) {
			fileType = "错误日志";
			bytes = model.getFdLogError().getBytes("UTF-8");
			
		} else {
			throw new IllegalArgumentException("invalid argument:type (expect detail or error)");
		}
		//文件名
		String fileName = model.getFdId() + fileType;
		String agent = request.getHeader("User-Agent").toLowerCase();
		if (agent.indexOf("msie") > 0 || (agent.indexOf("gecko") > 0 && agent.indexOf("rv:11") > 0)) {
		    fileName = URLEncoder.encode(fileName, "UTF-8");
		} else {
		    fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
		}
		response.addHeader("Content-Disposition", "attachment;filename=" + fileName + ".log");
		//内容
		response.setContentType("application/octet-stream;charset=UTF-8");
		IOUtils.write(bytes, response.getOutputStream());
		response.getOutputStream().flush();
		response.getOutputStream().close();
		return null;
    }
}
